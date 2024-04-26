// routes/room.js

const express = require('express');
const router = express.Router();
const roomController = require('../controller/roomController');

// Define routes for room operations
router.get('/', roomController.getAllRooms);
router.get('/:id', roomController.getRoomById);
router.post('/addroom', roomController.createRoom);
router.put('/:id', roomController.updateRoom);
router.delete('/:id', roomController.deleteRoom);

module.exports = router;
