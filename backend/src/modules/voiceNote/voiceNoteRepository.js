const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const createVoiceNote = async (data) => {
    return await prisma.voiceNote.create({
        data,
    });
};

const getAllVoiceNotes = async () => {
    return await prisma.voiceNote.findMany({
        orderBy: {
            createdAt: 'desc',
        },
    });
};

const deleteVoiceNote = async (id) => {
    return await prisma.voiceNote.delete({
        where: { id: parseInt(id) },
    });
};

module.exports = {
    createVoiceNote,
    getAllVoiceNotes,
    deleteVoiceNote,
};
