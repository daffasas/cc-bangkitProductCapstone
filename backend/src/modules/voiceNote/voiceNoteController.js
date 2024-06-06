const voiceNoteService = require('../services/voiceNoteService');

const createVoiceNote = async (req, res) => {
    try {
        const { title } = req.body;
        if (!title) {
            return res.status(400).json({ error: 'Title is required' });
        }
        const filePath = req.file.path;
        const voiceNote = await voiceNoteService.createVoiceNote({ title, filePath });
        res.status(201).json(voiceNote);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const getAllVoiceNotes = async (req, res) => {
    try {
        const voiceNotes = await voiceNoteService.getAllVoiceNotes();
        res.json(voiceNotes);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const deleteVoiceNote = async (req, res) => {
    try {
        const { id } = req.params;
        const voiceNote = await voiceNoteService.deleteVoiceNote(id);

        if (!voiceNote) {
            return res.status(404).json({ error: 'Voice note not found' });
        }

        res.status(200).json({
          message: 'Voice deleted successfully'
      });

    } catch (error) {
      res.status(500).json({ message: error.message });
    }
};


module.exports = {
    createVoiceNote,
    getAllVoiceNotes,
    deleteVoiceNote,
};
