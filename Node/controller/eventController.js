// controllers/eventController.js
const User = require('../models/User');
const Event = require('../models/Event');

exports.createEvent = async (req, res) => {
  try {
    const { adminId, roomId, taskId, endTime } = req.body;
    
    // Create new event
    const event = await Event.create({ adminId, roomId, taskId, endTime });

    // Find admin's list of members
    const admin = await User.findById(adminId).populate('members');

    // Assign event to a member
    await event.assignEventToMember(admin.members);

    res.status(201).json({ message: 'Event created successfully', event });
  } catch (error) {
    console.error('Error creating event:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};
