const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

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
        enum: ['mere', 'member']
    },
    adminId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
  },
  familyId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'family'
}
});

const SALT_ROUNDS = 12;

userSchema.pre('save', async function(next) {
    const user = this;
    if (user.isModified('password')) {
        user.password = await bcrypt.hash(user.password, SALT_ROUNDS);
    }
    
    
    next();
});

module.exports = mongoose.model('User', userSchema);
