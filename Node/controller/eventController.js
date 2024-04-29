const User = require('../model/User');
const Room = require('../model/room');
const Task = require('../model/task');
const Event = require('../model/event');
const Family = require('../model/family');


const taskController = require('./taskController');


exports.createEvent = async (req, res) => {
  const { roomName, taskId, endTime } = req.body;
  const userId = req.user.id;

  try {
      // Vérifier si l'utilisateur a le rôle d'admin (mère)
      const user = await User.findById(userId);
      if (user.role !== 'mere') {
          return res.status(403).json({ message: "Seuls les administrateurs peuvent créer des événements." });
      }

      // Vérifier l'existence de la chambre
      const roomExists = await Room.findOne({ nom: roomName });
      if (!roomExists) {
          return res.status(404).json({ message: "Chambre introuvable" });
      }

      // Vérifier l'existence de la tâche
      const taskExists = await Task.findById(taskId);
      if (!taskExists) {
          return res.status(404).json({ message: "Tâche introuvable" });
      }

      // Créer l'événement avec l'admin défini comme l'utilisateur qui crée l'événement
      const event = new Event({
          adminId: userId,
          roomId: roomExists._id,
          tasks: [taskId],
          endTime
      });

      // Sauvegarder l'événement
      await event.save();

      res.status(201).json({ message: "Événement créé avec succès", event });
  } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Erreur du serveur", error: error.message });
  }
};

exports.shareAllEventsRandomly = async (req, res) => {
  const userId = req.user.id;

  try {
      const user = await User.findById(userId);
      if (user.role !== 'mere') {
          return res.status(403).json({ message: "Seuls les administrateurs peuvent partager des événements." });
      }

      const family = await Family.findOne({ mother: userId }).populate('members');
      if (!family) {
          return res.status(404).json({ message: "Famille introuvable" });
      }

      family.members.push(userId); // Ajouter la mère à la liste des membres

      const events = await Event.find();
      if (!events || events.length === 0) {
          return res.status(404).json({ message: "Aucun événement trouvé" });
      }

      const sharedEvents = [];
      for (const event of events) {
          const assignedMemberIndex = Math.floor(Math.random() * family.members.length);
          const assignedMember = family.members[assignedMemberIndex];
          event.assignedTo = assignedMember;
          await event.save();
          sharedEvents.push({
              ...event._doc,
              assignedMemberName: assignedMember.name // Supposant que 'name' est un champ dans le modèle User
          });
      }

      res.status(200).json({ message: 'Tous les événements ont été partagés avec succès.', sharedEvents });
  } catch (error) {
      console.error(error);
      res.status(500).json({ message: "Erreur du serveur", error: error.message });
  }
};


// Obtenir tous les événements
exports.getAllEvents = async (req, res) => {
    try {
        const events = await Event.find();
        res.json(events);
    } catch (error) {
        console.error("Erreur lors de la récupération des événements:", error);
        res.status(500).json({ message: "Erreur du serveur" });
    }
};

// Obtenir un événement par son ID
exports.getEventById = async (req, res) => {
    const eventId = req.params.id;

    try {
        const event = await Event.findById(eventId);
        if (!event) {
            return res.status(404).json({ message: "Événement introuvable" });
        }
        res.json(event);
    } catch (error) {
        console.error("Erreur lors de la récupération de l'événement:", error);
        res.status(500).json({ message: "Erreur du serveur" });
    }
};

// Mettre à jour un événement par son ID
exports.updateEventById = async (req, res) => {
    const eventId = req.params.id;
    try {
        const { endTime } = req.body;
        const updatedEvent = await Event.findByIdAndUpdate(eventId, { endTime }, { new: true });
        if (!updatedEvent) {
            return res.status(404).json({ message: "Événement introuvable" });
        }
        res.json({ message: "Événement mis à jour avec succès", event: updatedEvent });
    } catch (error) {
        console.error("Erreur lors de la mise à jour de l'événement:", error);
        res.status(500).json({ message: "Erreur du serveur" });
    }
};

// Supprimer un événement par son ID
exports.deleteEventById = async (req, res) => {
    const eventId = req.params.id;
    try {
        const deletedEvent = await Event.findByIdAndDelete(eventId);
        if (!deletedEvent) {
            return res.status(404).json({ message: "Événement introuvable" });
        }
        res.json({ message: "Événement supprimé avec succès" });
    } catch (error) {
        console.error("Erreur lors de la suppression de l'événement:", error);
        res.status(500).json({ message: "Erreur du serveur" });
    }
};


