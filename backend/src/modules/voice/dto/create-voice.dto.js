class CreateVoiceDTO {
    constructor(title, voice) {
        this.title = title;
        this.voice = voice;
    }

    static fromRequest(body) {
        const { title, voice } = body;
        
        return new CreateVoiceDTO(title, voice);
    }

    validate() {
        const { title, voice } = this;

        if (!title || !voice) {
            throw new Error('All fields are required');
        }

        if (typeof title !== 'string' || typeof voice !== 'string') {
            throw new Error('Invalid data types');
        }
    }
}

module.exports = CreateVoiceDTO;