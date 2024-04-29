

const express = require('express');
const router = express.Router();
const eventController = require('../controller/eventController');

// Créer un événement
router.post('/create', eventController.createEvent);

// Obtenir tous les événements
router.get('/', eventController.getAllEvents);

// Obtenir un événement par son ID
router.get('/:id', eventController.getEventById);

// Mettre à jour un événement par son ID
router.put('/:id', eventController.updateEventById);

// Supprimer un événement par son ID
router.delete('/:id', eventController.deleteEventById);

// Partager un événement de manière aléatoire entre tous les membres de la famille
router.post('/shareAllRandomly', eventController.shareAllEventsRandomly);

module.exports = router;