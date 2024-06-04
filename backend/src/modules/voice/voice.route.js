const express =  require('express');
const { createVoice, getAllVoices, getVoiceById, updateVoice, deleteVoice } = require('./voice.controller');
const { authenticateToken, authorizeRole } = require('../authentication/auth.middleware');

const router = express.Router();

router.post('/', authenticateToken, authorizeRole(1), createVoice);
router.get('/', authenticateToken, getAllVoices);
router.get('/:id', authenticateToken, getVoiceById);
router.put('/:id', authenticateToken, updateVoice);
router.delete('/:id', authenticateToken, deleteVoice);

module.exports = router;