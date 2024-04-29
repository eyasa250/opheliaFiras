const mongoose = require('mongoose');
const TASK_NAMES = require('../config/tasks'); // Adjust path as necessary

const roomSchema = new mongoose.Schema({
  nom: { type: String, required: true },
  admin: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true,
  },
  tasks: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Task' }]
});

const Room = mongoose.model('Room', roomSchema);

module.exports = Room;