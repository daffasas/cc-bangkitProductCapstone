const { PrismaClient } = require("@prisma/client");
const CreateVoiceDTO = require("./dto/create-voice.dto");
const UpdateVoiceDTO = require("./dto/update-voice.dto");

const prisma = new PrismaClient();

class VoiceRepository {
    async addVoice(data) {
        return await prisma.voiceNote.create({
            data: {
                title: data.title,
                filePath: data.filePath
            }
        });
    }

    async getAllVoices() {
        return await prisma.voices.findMany();
    }

    async getVoiceById(id) {
        return await prisma.voices.findUnique({
            where: { id }
        });
    }

    async updateVoice(id, data) {
        const updateVoiceDTO = UpdateVoiceDTO.fromRequest(data);
        updateVoiceDTO.validate();

        return await prisma.voices.update({
            where: { id },
            data: {
                title: updateVoiceDTO.title,
                voice: updateVoiceDTO.voice
            }
        });
    }

    async deleteVoice(id) {
        const voiceId = parseInt(id);

        return await prisma.voices.delete({
            where: { id: voiceId }
        });
    }
}

module.exports = new VoiceRepository();