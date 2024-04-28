// controllers/taskController.js
const Task = require('../model/task');

// Create a new task
exports.createTask = async (req, res) => {
  try {
    const { name, difficulty } = req.body;
    const adminId = req.user.id; // Extract admin ID from JWT token
    const task = await Task.create({ name, difficulty, admin: adminId });
    res.status(201).json(task);
  } catch (error) {
    console.error('Error creating task:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Get all tasks
exports.getAllTasks = async (req, res) => {
  try {
    const tasks = await Task.find({ admin: req.user.id }); // Retrieve tasks only for the admin
    res.status(200).json(tasks);
  } catch (error) {
    console.error('Error getting tasks:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Get a task by ID
exports.getTaskById = async (req, res) => {
  try {
    const taskId = req.params.id;
    const task = await Task.findOne({ _id: taskId, admin: req.user.id }); // Find task by ID for the admin
    if (!task) {
      return res.status(404).json({ error: 'Task not found' });
    }
    res.status(200).json(task);
  } catch (error) {
    console.error('Error getting task by ID:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Update a task by ID
exports.updateTask = async (req, res) => {
  try {
    const taskId = req.params.id;
    const { name, difficulty } = req.body;
    const updatedTask = await Task.findOneAndUpdate({ _id: taskId, admin: req.user.id }, { name, difficulty }, { new: true });
    res.status(200).json(updatedTask);
  } catch (error) {
    console.error('Error updating task:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

// Delete a task by ID
exports.deleteTask = async (req, res) => {
  try {
    const taskId = req.params.id;
    await Task.findOneAndDelete({ _id: taskId, admin: req.user.id }); // Find and delete task by ID for the admin
    res.status(200).json({ message: 'Task deleted successfully' });
  } catch (error) {
    console.error('Error deleting task:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};
