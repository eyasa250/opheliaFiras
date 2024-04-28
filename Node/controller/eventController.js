const User = require('../models/User');
const Task = require('../models/Task');
const Event = require('../models/Event');

exports.createEvent = async (req, res) => {
  try {
    const { adminId, roomId, taskIds, endTime } = req.body; // assuming taskIds is an array of task Ids
    
    // Create new event with multiple tasks
    const event = new Event({
      adminId,
      roomId,
      tasks: taskIds, // Assign multiple tasks directly
      endTime
    });

    await event.save();

    // Find admin's list of members to assign tasks to
    const admin = await User.findById(adminId).populate('familyMembers');
    if (!admin.familyMembers || admin.familyMembers.length === 0) {
      throw new Error('No family members found');
    }

    // Randomly assign tasks to members
    const tasks = await Task.find({ _id: { $in: taskIds } });
    tasks.forEach(async (task, index) => {
      const randomMemberIndex = Math.floor(Math.random() * admin.familyMembers.length);
      const assignedMember = admin.familyMembers[randomMemberIndex];
      task.userId = assignedMember._id; // Assign a user to the task
      await task.save();
    });

    res.status(201).json({ message: 'Event created and tasks assigned successfully', event });
  } catch (error) {
    console.error('Error creating event:', error);
    res.status(500).json({ error: 'Internal server error', message: error.message });
  }
};



 // Function to list events for members
/*exports.listEventsForMember = async (req, res) => {
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
*/