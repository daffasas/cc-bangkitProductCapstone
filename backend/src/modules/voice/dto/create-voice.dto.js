class CreateVoiceDTO {
    constructor(title, filePath) {
        this.title = title;
        this.filePath = filePath;
    }

    static fromRequest(body, filePath) {
        const { title } = body;
        return new CreateVoiceDTO(title, filePath);
    }

    validate() {
        const { title } = this;

        if (!title) {
            throw new Error('Title is required');
        }

        if (typeof title !== 'string') {
            throw new Error('Invalid data type for title');
        }
    }
}

module.exports = CreateVoiceDTO;
