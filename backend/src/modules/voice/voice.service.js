const voiceRepository = require("./voice.repository");

class VoiceService {
    async createVoice(data) {
        return await voiceRepository.addVoice(data);
    }

    async getAllVoices() {
        return await voiceRepository.getAllVoices();
    }

    async getVoiceById(id) {
        return await voiceRepository.getVoiceById(id);
    }

    async updateVoice(id, data) {
        return await voiceRepository.updateVoice(id, data);
    }

    async deleteVoice(id) {
        return await voiceRepository.deleteVoice(id);
    }
}

module.exports = new VoiceService();