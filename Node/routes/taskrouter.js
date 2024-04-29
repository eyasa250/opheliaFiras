const express = require('express');
const router = express.Router();
const taskController = require('../controller/taskController');

// Route pour créer une nouvelle tâche
router.post('/addTaskToRoom', taskController.createTask);

// Route pour obtenir toutes les tâches
router.get('/', taskController.getAllTasks);

// Route pour obtenir une tâche par ID
router.get('/:id', taskController.getTaskById);

// Route pour mettre à jour une tâche par ID
router.put('/:id', taskController.updateTaskById);

// Route pour supprimer une tâche par ID
router.delete('/:id', taskController.deleteTaskById);

// Route pour obtenir les tâches d'une chambre spécifique
router.get('/room/:idroom', taskController.findTasksByRoom);

// Route pour distribuer les tâches aléatoirement entre les membres de la famille
router.post('/distributeTasksRandomly', taskController.distributeTasksRandomly);

module.exports = router;
