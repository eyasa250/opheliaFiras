const express = require('express');
const router = express.Router();
const taskController = require('../controller/taskController');
const authenticate = require('../config/authentificate');

// Create a new task
router.post('/', authenticate, taskController.createTask);

// Get all tasks
router.get('/', authenticate, taskController.getAllTasks);

// Get a task by ID
router.get('/:id', authenticate, taskController.getTaskById);

// Update a task by ID
router.put('/:id', authenticate, taskController.updateTask);

// Delete a task by ID
router.delete('/:id', authenticate, taskController.deleteTask);


module.exports = router;
