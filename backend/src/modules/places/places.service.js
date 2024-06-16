const placesRepository = require("./places.repository");

class PlacesService {
    async createPlace(data) {
        return await placesRepository.addPlace(data);
    }

    async getAllPlaces(page, pageSize) {
        const skip = (page - 1) * pageSize;
        const take = pageSize;
      
        const places = await placesRepository.getAllPlaces(skip, take);
        const totalPlaces = await placesRepository.getPlacesCount();
      
        return {
            data: places,
            currentPage: page,
            totalPages: Math.ceil(totalPlaces / pageSize),
            totalPlaces: totalPlaces,
        };
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
