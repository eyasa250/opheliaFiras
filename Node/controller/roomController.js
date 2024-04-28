// controllers/roomController.js
const Room = require('../model/room');

// Create a new room
exports.createRoom = async (req, res) => {
  try {
    const { nom } = req.body;
    const adminId = req.user.id; // Extract admin ID from JWT token
    const room = await Room.create({ nom, admin: adminId });
    res.status(201).json(room);
  } catch (error) {
    console.error('Error creating room:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Get all rooms for the admin
exports.getAllRooms = async (req, res) => {
  try {
    const rooms = await Room.find({ admin: req.user.id });
    res.status(200).json(rooms);
  } catch (error) {
    console.error('Error getting rooms:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Get a room by ID for the admin
exports.getRoomById = async (req, res) => {
  try {
    const roomId = req.params.id;
    const room = await Room.findOne({ _id: roomId, admin: req.user.id });
    if (!room) {
      return res.status(404).json({ error: 'Room not found' });
    }
    res.status(200).json(room);
  } catch (error) {
    console.error('Error getting room by ID:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Update a room by ID for the admin
exports.updateRoom = async (req, res) => {
  try {
    const roomId = req.params.id;
    const { nom } = req.body;
    const updatedRoom = await Room.findOneAndUpdate({ _id: roomId, admin: req.user.id }, { nom }, { new: true });
    res.status(200).json(updatedRoom);
  } catch (error) {
    console.error('Error updating room:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Delete a room by ID for the admin
exports.deleteRoom = async (req, res) => {
  try {
    const roomId = req.params.id;
    await Room.findOneAndDelete({ _id: roomId, admin: req.use
