const { PrismaClient } = require("@prisma/client");
const CreateVoiceDTO = require("./dto/create-voice.dto");
const UpdateVoiceDTO = require("./dto/update-voice.dto");

const prisma = new PrismaClient();

class VoiceRepository {
    async addVoice(data) {
        const createVoiceDTO = CreateVoiceDTO.fromRequest(data);
        createVoiceDTO.validate();

        return await prisma.voices.create({
            data: {
                title: createVoiceDTO.title,
                voice: createVoiceDTO.voice
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
        return await prisma.voices.delete({
            where: { id }
        });
    }
}

module.exports = new VoiceRepository();