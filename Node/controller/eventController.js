// controllers/eventController.js
const User = require('../models/User');
const Event = require('../model/event');

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

 // Function to list events for members
exports.listEventsForMember = async (req, res) => {
    try {
      // Get memberId from the JWT token payload
      const memberId = req.user.id;
  
      // Find events associated with the member
      const events = await Event.find({ memberId });
  
      res.status(200).json({ events });
    } catch (error) {
      console.error('Error listing events for member:', error);
      res.status(500).json({ error: 'Internal server error' });
    }
  }; 
 // Function to list events for admin
exports.listEventsForAdmin = async (req, res) => {
    try {
      // Get adminId from the JWT token payload
      const adminId = req.user.id;
  
      // Find events created by the admin
      const events = await Event.find({ adminId });
  
      res.status(200).json({ events });
    } catch (error) {
      console.error('Error listing events for admin:', error);
      res.status(500).json({ error: 'Internal server error' });
    } }; 
};
