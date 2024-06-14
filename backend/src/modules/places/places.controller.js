const express = require('express');
const { authenticateToken, authorizeRole } = require("../authentication/auth.middleware");
const placesService = require("./places.service");

const router = express.Router();

// Middleware for parsing body
router.use(express.json());
router.use(express.urlencoded({ extended: true }));

// Create Place
router.post('/', authenticateToken, authorizeRole(1), async (req, res) => {
    try {
        const place = await placesService.createPlace(req.body);
        res.status(201).json(place);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Get All Places
router.get('/', authenticateToken, async (req, res) => {
    try {
        const places = await placesService.getAllPlaces();
        res.json(places);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Get Place by ID
router.get('/:id', authenticateToken, async (req, res) => {
    try {
        const place = await placesService.getPlaceById(req.params.id);

        if (!place) {
            return res.status(404).json({ message: 'Place not found' });
        }

        res.status(200).json(place);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Update Place
router.put('/:id', authenticateToken, authorizeRole(1), async (req, res) => {
    try {
        const place = await placesService.updatePlace(req.params.id, req.body);

        if (!place) {
            return res.status(404).json({ message: 'Place not found' });
        }

        res.status(200).json({
            message: 'Place updated successfully',
            data: place
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Delete Place
router.delete('/:id', authenticateToken, authorizeRole(1), async (req, res) => {
    try {
        const place = await placesService.deletePlace(req.params.id);

        if (!place) {
            return res.status(404).json({ error: 'Place not found' });
        }

        res.status(200).json({
            message: 'Place deleted successfully'
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
