// routes/events.js
const express = require('express');
const router = express.Router();
const eventController = require('../controller/eventController');

router.post('/', eventController.createEvent);

module.exports = router;
