const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const authController = require('./modules/authentication/auth.controller');
const voiceController = require('./modules/voice/voice.controller')


const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());
dotenv.config();

// endpoint
app.use('/auth', authController);
// app.use('/voice-notes', voiceNoteRoute);
app.use('/voices', voiceController);

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});