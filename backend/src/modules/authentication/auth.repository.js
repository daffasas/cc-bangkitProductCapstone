const { PrismaClient } = require('@prisma/client');
const CreateUserDto = require('./dto/create-user.dto');

const prisma = new PrismaClient();

class AuthRepository {
    async addUser(data) {
        const createUserDto = CreateUserDto.fromRequest(data);
        createUserDto.validate();

        return await prisma.users.create({
            data: {
                email: createUserDto.email,
                password: createUserDto.password,
                role: createUserDto.role
            }
        });
    }

    async findUserByEmail() {
        return await prisma.users.findUnique({
            where: { email }
        });
    };
}

module.exports = new AuthRepository();