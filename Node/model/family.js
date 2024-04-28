const mongoose = require('mongoose');

const familySchema = new mongoose.Schema({
    mother: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    members: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    }]
});

module.exports = mongoose.model('Family', familySchema);