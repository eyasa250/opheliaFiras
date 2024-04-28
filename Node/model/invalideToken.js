// invalidToken.js
const mongoose = require('mongoose');

const invalidTokenSchema = new mongoose.Schema({
    token: {
        type: String,
        required: true,
        unique: true
    }
}, { timestamps: true });

const InvalidToken = mongoose.model('InvalidToken', invalidTokenSchema);

module.exports = InvalidToken;