class CreateVoiceDTO {
    constructor(title, voice) {
        this.title = title;
     //   this.voice = voice;
    }

    static fromRequest(body) {
        const { title } = body;
        
        return new CreateVoiceDTO(title);
    }

    validate() {
        const { title } = this;

        if (title = 0) {
            throw new Error('All fields are required');
        }

        if (typeof title !== 'string') {
            throw new Error('Invalid data types');
        }
    }
}

module.exports = CreateVoiceDTO;