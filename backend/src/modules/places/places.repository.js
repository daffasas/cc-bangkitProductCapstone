const { PrismaClient } = require("@prisma/client");
const prisma = new PrismaClient();
const CreatePlaceDTO = require('./dto/create-place.dto');

class PlacesRepository {
    async addPlace(data) {
        const createPlaceDTO = CreatePlaceDTO.fromRequest(data);
        createPlaceDTO.validate();

        return await prisma.places.create({
            data: {
                place: createPlaceDTO.place,
                alamat: createPlaceDTO.alamat,
                rating: createPlaceDTO.rating,
                jarakMekkah: createPlaceDTO.jarakMekkah
            }
        });
    }

    async getAllPlaces(skip, take) {
        return await prisma.places.findMany({
            skip: skip,
            take: take,
        });
    }
      
    async getPlacesCount() {
        return await prisma.places.count();
    }

    async getPlaceById(id) {
        return await prisma.places.findUnique({
            where: { id }
        });
    }

    async updatePlace(id, data) {
        const updatePlaceDTO = CreatePlaceDTO.fromRequest(data);
        updatePlaceDTO.validate();

        return await prisma.places.update({
            where: { id },
            data: {
                place: updatePlaceDTO.place,
                alamat: updatePlaceDTO.alamat,
                rating: updatePlaceDTO.rating,
                jarakMekkah: updatePlaceDTO.jarakMekkah
            }
        });
    }

    async deletePlace(id) {
        return await prisma.places.delete({
            where: { id }
        });
    }
}

module.exports = new PlacesRepository();
