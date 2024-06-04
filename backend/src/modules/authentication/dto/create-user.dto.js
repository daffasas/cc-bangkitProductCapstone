class CreateUserDTO {
    constructor(email, password, role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    static fromRequest(body) {
        const { email, password, role } = body;
        
        return new CreateUserDTO(email, password, role);
    }

    validate() {
        const { email, password, role } = this;

        if (!email || !password || !role) {
            throw new Error('All fields are required');
        }

        if (typeof email !== 'string' || typeof password !== 'string' || typeof role !== 'number') {
            throw new Error('Invalid data types');
        }
    }
}

module.exports = CreateUserDTO;