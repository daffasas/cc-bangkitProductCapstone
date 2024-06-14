const placesRepository = require("./places.repository");

class PlacesService {
    async createPlace(data) {
        return await placesRepository.addPlace(data);
    }

    async getAllPlaces() {
        return await placesRepository.getAllPlaces();
    }

    async getPlaceById(id) {
        return await placesRepository.getPlaceById(id);
    }

    async updatePlace(id, data) {
        return await placesRepository.updatePlace(id, data);
    }

    async deletePlace(id) {
        return await placesRepository.deletePlace(id);
    }
}

module.exports = new PlacesService();
