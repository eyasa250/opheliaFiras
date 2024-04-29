const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const eventSchema = new Schema({
  adminId: { type: Schema.Types.ObjectId, ref: 'User', required: true },
  roomId: { type: Schema.Types.ObjectId, ref: 'Room', required: true },
  tasks: [{ type: Schema.Types.ObjectId, ref: 'Task' }], 
  endTime: { type: Date, required: true } ,
  assignedTo: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
}
});

eventSchema.methods.assignEventToMember = async function(members) {
  // Filter members who don't have any events assigned
  const membersWithoutEvents = members.filter(member => member.events.length === 0);

  if (membersWithoutEvents.length > 0) {
    // If there are members without events, assign the event to one of them
    const randomMember = membersWithoutEvents[Math.floor(Math.random() * membersWithoutEvents.length)];
    await randomMember.events.push(this._id);
    await randomMember.save();
  } else {
    // If all members have events, assign the event randomly to one of them
    const randomMember = members[Math.floor(Math.random() * members.length)];
    await randomMember.events.push(this._id);
    await randomMember.save();
  }
};

module.exports = mongoose.model('Event', eventSchema);
