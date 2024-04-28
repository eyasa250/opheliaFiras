// routes/userRouter.js
const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');

// Register a new user
router.post('/register', userController.registerUser);

// Get user by ID
router.get('/:id', userController.getUserById);

// Update user
router.put('/:id', userController.updateUser);

// Delete user
router.delete('/:id', userController.deleteUser);

// List family members (only for admin (mother))
router.get('/family-members', userController.listFamilyMembers);

// Delete family member (only for admin (mother))
router.delete('/family-members', userController.deleteFamilyMember);

module.exports = router;
