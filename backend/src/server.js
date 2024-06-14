const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const authController = require('./modules/authentication/auth.controller');
const voiceController = require('./modules/voice/voice.controller');
const placesController = require('./modules/places/places.controller');

// Load environment variables from .env file
dotenv.config();

// Create the Express application
const app = express();
const PORT = process.env.PORT || 3000;

// Middleware for parsing JSON bodies
app.use(bodyParser.json());

// Middleware for parsing URL-encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

// Serve static files from the uploads directory
app.use('/uploads', express.static('uploads'));

// Endpoint routes
app.use('/auth', authController);
app.use('/voices', voiceController);
app.use('/places', placesController);

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
