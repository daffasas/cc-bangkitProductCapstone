const voiceNoteRepository = require('../repositories/voiceNoteRepository');


const createVoiceNote = async (data) => {
    return await voiceNoteRepository.createVoiceNote(data);
};

const getAllVoiceNotes = async () => {
    return await voiceNoteRepository.getAllVoiceNotes();
};

const deleteVoiceNote = async (id) => {
    return await voiceNoteRepository.deleteVoiceNote(id);
};

module.exports = {
    createVoiceNote,
    getAllVoiceNotes,
    deleteVoiceNote,
};
