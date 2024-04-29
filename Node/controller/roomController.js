const Room = require('../model/room'); // Import the Room model
const User = require('../model/User'); // Assurez-vous d'importer le modèle User

// Get all rooms
exports.getAllRooms = async (req, res) => {
  try {
    const rooms = await Room.find();
    res.json(rooms);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Server Error" });
  }
};

// Get a single room by ID
exports.getRoomById = async (req, res) => {
  const { id } = req.params;
  try {
    const foundRoom = await Room.findById(id);
    if (!foundRoom) {
      return res.status(404).json({ message: "Room not found" });
    }
    res.json(foundRoom);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Server Error" });
  }
};

// Create a new room
exports.createRoom = async (req, res) => {
  const { nom } = req.body;
  const userId = req.user.id; // Assurez-vous que l'ID de l'utilisateur est bien extrait de la session ou du token JWT
  
  try {
    const user = await User.findById(userId);
    if (!user || user.role !== 'mere') {
      return res.status(404).json({ error: 'Only mothers can create families' });
  }
    const newRoom = await Room.create({ nom ,admin:user});
    res.status(201).json(newRoom);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Server Error" });
  }
};

// Update an existing room
exports.updateRoom = async (req, res) => {
  const { id } = req.params;
  const { nom } = req.body;
  try {
    const foundRoom = await Room.findByPk(id);
    if (!foundRoom) {
      return res.status(404).json({ message: "Room not found" });
    }
    foundRoom.nom = nom;
    await foundRoom.save();
    res.json(foundRoom);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Server Error" });
  }
};

// Delete an existing room
exports.deleteRoom = async (req, res) => {
  const { id } = req.params;
  try {
    const foundRoom = await Room.findByPk(id);
    if (!foundRoom) {
      return res.status(404).json({ message: "Room not found" });
    }
    await foundRoom.destroy();
    res.status(204).end();
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Server Error" });
  }
};