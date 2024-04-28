const express = require('express');
const router = express.Router();
const familyController = require('../controller/familyController');



// Créer une nouvelle famille
router.post('/create', familyController.createFamily);

// Ajouter un membre à la famille
router.post('/add-member', familyController.addFamilyMember);

// Obtenir la liste des membres de la famille
router.get('/members/:familyId', familyController.getFamilyMembers);

// Supprimer un membre de la famille
router.delete('/remove-member', familyController.removeFamilyMember);

module.exports = router;
