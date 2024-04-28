const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    role: {
        type: String,
        enum: ['mere', 'member'], // Add more roles if needed
        default: 'member'
    },
    adminId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    familyMembers: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    }]
});

const User = mongoose.model('User', userSchema);

module.exports = User;
