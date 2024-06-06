const express = require('express');
const multer = require('multer');
const path = require('path');
const voiceNoteController = require('../controllers/voiceNoteController');
const { authenticateToken, authorizeRole } = require('../authentication/auth.middleware');

const router = express.Router();

// Konfigurasi multer untuk penyimpanan file
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
        cb(null, Date.now() + path.extname(file.originalname));
    }
});

// Fungsi untuk memeriksa apakah file adalah audio
const audioFileFilter = (req, file, cb) => {
    if (file.mimetype.startsWith('audio/')) {
        cb(null, true);
    } else {
        cb(new Error('File bukan audio!'), false);
    }
};

const upload = multer({ 
    storage: storage,
    fileFilter: audioFileFilter
});

// Endpoint untuk mengunggah voice note
router.post('/', upload.single('voiceNote'), authenticateToken, authorizeRole(1), voiceNoteController.createVoiceNote);

// Endpoint untuk mendapatkan semua voice note
router.get('/', authenticateToken, voiceNoteController.getAllVoiceNotes);

// Endpoint untuk menghapus voice note by ID
router.delete('/:id', authenticateToken, voiceNoteController.deleteVoiceNote);


module.exports = router;
