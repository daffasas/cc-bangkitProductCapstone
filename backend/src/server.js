const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const authRoutes = require('./modules/authentication/auth.route');
const voiceNoteRoute = require('./modules/voiceNote/voiceNoteRoute');
const voiceRoute = require('./modules/voice/voice.route');


const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());
dotenv.config();

// endpoint
app.use('/auth', authRoutes);
app.use('/voice-notes', voiceNoteRoute);
app.use('/voice', voiceRoute);

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});