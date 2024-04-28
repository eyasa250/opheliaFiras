// routes/events.js
const express = require('express');
const router = express.Router();
const eventController = require('../controller/eventController');
const authenticate = require('../config/authentificate');

// Create event
router.post('/create', authenticate, eventController.createEvent);

// List events for member
router.get('/member', authenticate, eventController.listEventsForMember);

// List events for admin
router.get('/admin', authenticate, eventController.listEventsForAdmin);


module.exports = router;
