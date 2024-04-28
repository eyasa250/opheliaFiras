const User = require("../model/User");
const bcrypt = require("bcrypt");
const jwt = require('jsonwebtoken');

exports.registerUser = async (req, res, next) => {
    try {
        const { username, email, password, role } = req.body;

        // Create a new user based on the specified role
        const newUser = new User({
            username: username,
            email: email,
            password: password,
            role: role // Use the specified role
        });

        await newUser.save();

        res.status(201).json({ message: 'User created successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error creating user' });
    }
};

exports.getUserById = async (req, res, next) => {
    try {
        const userId = req.params.id;
        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }
        res.status(200).json(user);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error retrieving user' });
    }
};

exports.updateUser = async (req, res, next) => {
    try {
        const userId = req.params.id;
        const { username, email, password } = req.body;

        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        // Update user data
        user.username = username;
        user.email = email;
        user.password = password;

        await user.save();

        res.status(200).json({ message: 'User updated successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error updating user' });
    }
};

exports.deleteUser = async (req, res, next) => {
    try {
        const userId = req.params.id;
        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        await user.remove();

        res.status(200).json({ message: 'User deleted successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error deleting user' });
    }
    exports.listFamilyMembers = async (req, res, next) => {
        try {
            const adminId = req.user.id; // Retrieve the ID of the admin (mother) from the JWT token
    
            // Check if the admin (mother) exists
            const admin = await User.findById(adminId);
            if (!admin || admin.role !== 'mere') {
                return res.status(403).json({ message: 'Only admin (mother) can list family members' });
            }
    
            // Find and return the family members of the admin (mother)
            const familyMembers = await User.find({ _id: { $in: admin.familyMembers } });
            res.status(200).json(familyMembers);
        } catch (error) {
            console.error(error);
            res.status(500).json({ message: 'Error listing family members' });
        }
    };
    
    exports.deleteFamilyMember = async (req, res, next) => {
        try {
            const { memberId } = req.body;
            const adminId = req.user.id; // Retrieve the ID of the admin (mother) from the JWT token
    
            // Check if the admin (mother) exists
            const admin = await User.findById(adminId);
            if (!admin || admin.role !== 'mere') {
                return res.status(403).json({ message: 'Only admin (mother) can delete family members' });
            }
    
            // Remove the member ID from the list of family members of the admin (mother)
            const index = admin.familyMembers.indexOf(memberId);
            if (index !== -1) {
                admin.familyMembers.splice(index, 1);
                await admin.save();
                res.status(200).json({ message: 'Family member deleted successfully' });
            } else {
                res.status(404).json({ message: 'Family member not found' });
            }
        } catch (error) {
            console.error(error);
            res.status(500).json({ message: 'Error deleting family member' });
        }
    };   
};
