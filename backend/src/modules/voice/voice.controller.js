const voiceService = require("./voice.service");

class VoiceController {
    async createVoice(req, res) {
        try {
            const data = await voiceService.createVoice(req.body);

            res.status(201).json({
                message: 'Voice created successfully',
                data: data
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async getAllVoices(req, res) {
        try {
            const data = await voiceService.getAllVoices();

            res.status(200).json({
                data: data
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async getVoiceById(req, res) {
        try {
            const { id } = req.params;
            const data = await voiceService.getVoiceById(id);

            if (!data) {
                res.status(404).json({
                    message: 'Voice not found'
                });
            }

            res.status(200).json({
                data: data
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async updateVoice(req, res) {
        try {
            const { id } = req.params;
            const data = await voiceService.updateVoice(id, req.body);

            if (!data) {
                res.status(404).json({
                    message: 'Voice not found'
                });
            }

            res.status(200).json({
                message: 'Voice updated successfully',
                data: data
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async deleteVoice(req, res) {
        try {
            const { id } = req.params;
            const data = await voiceService.deleteVoice(id);

            if (!data) {
                res.status(404).json({
                    message: 'Voice not found'
                });
            }

            res.status(200).json({
                message: 'Voice deleted successfully'
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }
}

module.exports = new VoiceController();