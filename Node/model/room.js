const mongoose = require('mongoose');

const roomSchema = new mongoose.Schema({
  nom: { type: String, required: true },
  admin: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true,
  },
  tasks: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Task' }]
});

// Check if the model has already been compiled to prevent recompilation
module.exports = mongoose.models.Room || mongoose.model('Room', roomSchema);
