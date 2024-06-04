class UpdateVoiceDTO {
    constructor(title, voice) {
        this.title = title;
        this.voice = voice
    }
  
    static fromRequest(body) {
        const { title, voice } = body;
        return new UpdateVoiceDTO(title, voice);
    }
  
    validate() {
        const { title, voice } = this;
        
        if (typeof title !== 'string' || typeof voice !== 'string') {
            throw new Error('Invalid data types');
        }
    }
}
  
module.exports = UpdateVoiceDTO;