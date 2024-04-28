const mongoose = require('mongoose');

const taskSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    enum: ['Clean the kitchen', 'Vacuum the living room', 'Water the plants'], // Predefined list of task names
  },
  room: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Room',
    required: true
  }, 
  // Add any other fields you need for a task
});

const Task = mongoose.model('Task', taskSchema);

module.exports = Task;
