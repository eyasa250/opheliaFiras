const mongoose = require('mongoose');

const roomSchema = new mongoose.Schema({
  nom: { type: String, required: true },
  admin: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true,
  },
});

const Room = mongoose.model('Room', roomSchema);

module.exports = Room;
