const mongoose = require('mongoose');
const TASK_NAMES = require('../config/tasks'); // Adjust path as necessary

const roomSchema = new mongoose.Schema({
  nom: { type: String, required: true },
 tasks: [{ type: String, enum: TASK_NAMES }] // Ensure only tasks from the predefined list can be added
});

const Room = mongoose.model('Room', roomSchema);

module.exports = Room;
