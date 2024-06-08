const { authenticateToken, authorizeRole } = require("../authentication/auth.middleware");
const voiceService = require("./voice.service");
const express = require('express');

const app = express();

app.post('/', authenticateToken, authorizeRole(1), async (req, res) => {
    try {
        const data = await voiceService.createVoice(req.body);

        res.status(201).json({
            message: 'Voice created successfully',
            data: data
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

app.get('/',authenticateToken, async (req, res) =>  {
    try {
        const data = await voiceService.getAllVoices();
        
        res.status(200).json({
            data: data
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

app.get('/:id', async (req, res) => {
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
});

app.put('/', async (req, res) => {
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
});

app.delete('/:id', async (req, res) => {
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
});

module.exports = app