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
},
status: { type: String, enum: ['pending', 'completed'], default: 'pending' } // Add status field

});



eventSchema.methods.completeEvent = async function() {
  this.status = 'completed';
  await this.save();
};

module.exports = mongoose.model('Event', eventSchema);
