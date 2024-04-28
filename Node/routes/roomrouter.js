// routes/room.js

const express = require('express');
const router = express.Router();
const roomController = require('../controller/roomController');
const authenticate = require('../config/authentificate');

// Create a new room
router.post('/', authenticate, roomController.createRoom);

// Get all rooms for the admin
router.get('/', authenticate, roomController.getAllRooms);

// Get a room by ID for the admin
router.get('/:id', authenticate, roomController.getRoomById);

// Update a room by ID for the admin
router.put('/:id', authenticate, roomController.updateRoom);

// Delete a room by ID for the admin
router.delete('/:id', authenticate, roomController.deleteRoom);

module.exports = router;
