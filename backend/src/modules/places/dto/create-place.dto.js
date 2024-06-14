class CreatePlaceDTO {
    constructor(place, alamat, rating, jarakMekkah) {
        this.place = place;
        this.alamat = alamat;
        this.rating = rating;
        this.jarakMekkah = jarakMekkah;
    }

    static fromRequest(body) {
        const { place, alamat, rating, jarakMekkah } = body;
        return new CreatePlaceDTO(place, alamat, parseFloat(rating), parseFloat(jarakMekkah));
    }

    validate() {
        const { place, alamat, rating, jarakMekkah } = this;

        if (!place || !alamat || isNaN(rating) || isNaN(jarakMekkah)) {
            throw new Error('All fields are required and must be valid');
        }

        if (typeof place !== 'string' || typeof alamat !== 'string' || typeof rating !== 'number' || typeof jarakMekkah !== 'number') {
            throw new Error('Invalid data types');
        }
    }
}

module.exports = CreatePlaceDTO;
