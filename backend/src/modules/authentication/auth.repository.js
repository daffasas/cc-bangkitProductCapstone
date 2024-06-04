const { PrismaClient } = require('@prisma/client');
const CreateUserDTO = require('./dto/create-user.dto');

const prisma = new PrismaClient();

class AuthRepository {
    async addUser(data) {
        const createUserDto = CreateUserDTO.fromRequest(data);
        createUserDto.validate();

        return await prisma.users.create({
            data: {
                email: createUserDto.email,
                password: createUserDto.password,
                role: createUserDto.role
            }
        });
    }

    async findUserByEmail(email) {
        return await prisma.users.findUnique({
            where: { email }
        });
    };
}

module.exports = new AuthRepository();