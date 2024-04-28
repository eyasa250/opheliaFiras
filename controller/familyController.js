const Family = require('../model/family');
const User = require('../model/User');

// Créer une nouvelle famille
exports.createFamily = async (req, res, next) => {
    const userId = req.user.id;
    const user = await User.findById(userId);

    // Vérifier si l'utilisateur existe et a le rôle de mère
    if (!user || user.role !== 'mere') {
        return res.status(404).json({ error: 'Only mothers can create families' });
    }

    // Vérifier si la mère a déjà créé une famille
    const existingFamily = await Family.findOne({ mother: userId });
    if (existingFamily) {
        return res.status(409).json({ error: 'Mother has already created a family' });
    }

    // Vérifier si les membres à ajouter ne sont pas déjà dans une autre famille
    const membersToAdd = req.body.members;
    const members = await User.find({ _id: { $in: membersToAdd }, familyId: { $ne: null } });
    if (members.length > 0) {
        return res.status(400).json({ error: 'One or more members are already in another family' });
    }

    // Créer la famille avec l'utilisateur spécifié comme mère
    const family = await Family.create({ mother: userId, members: membersToAdd });

    // Mettre à jour le familyId pour chaque membre ajouté
    await User.updateMany({ _id: { $in: membersToAdd } }, { $set: { familyId: family._id } });

    res.status(201).json({ message: 'Family created successfully', familyId: family._id });
};


exports.addFamilyMember = async (req, res, next) => {
    const { familyId, memberId } = req.body;
    const family = await Family.findById(familyId);
    if (!family) {
        return res.status(404).json({ error: 'Family not found' });
    }

    // Assurez-vous que l'utilisateur n'appartient à aucune autre famille
    const existingMember = await User.findOne({ _id: memberId, familyId: { $ne: null } });
    if (existingMember) {
        return res.status(400).json({ error: 'Member already belongs to a family' });
    }

    // Ajouter le membre à la famille
    await Family.findByIdAndUpdate(familyId, { $addToSet: { members: memberId } });
    await User.findByIdAndUpdate(memberId, { $set: { familyId: familyId } });

    res.status(200).json({ message: 'Member added to the family successfully' });
};


// Obtenir la liste des membres de la famille
exports.getFamilyMembers = async (req, res, next) => {
    try {
        const { familyId } = req.params;

        // Vérifier si la famille existe
        const family = await Family.findById(familyId).populate('members', 'username email');

        if (!family) {
            return res.status(404).json({ error: 'Family not found' });
        }

        res.status(200).json({ members: family.members });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'An error occurred while fetching family members' });
    }
};

// Supprimer un membre de la famille
exports.removeFamilyMember = async (req, res, next) => {
    try {
        const { familyId, memberId } = req.body;

        // Vérifier si la famille existe
        const family = await Family.findById(familyId);
        if (!family) {
            return res.status(404).json({ error: 'Family not found' });
        }

        // Retirer le membre de la liste des membres de la famille
        const index = family.members.indexOf(memberId);
        if (index !== -1) {
            family.members.splice(index, 1);
            await family.save();
            res.status(200).json({ message: 'Family member removed successfully' });
        } else {
            res.status(404).json({ message: 'Family member not found' });
        }
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'An error occurred while removing family member' });
    }
};
