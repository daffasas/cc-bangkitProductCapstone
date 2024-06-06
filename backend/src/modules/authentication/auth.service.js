const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const { findUserByEmail, addUser } = require("./auth.repository");

const JWT_SECRET = process.env.JWT_SECRET;
const JWT_EXPIRE = process.env.JWT_EXPIRE;

class AuthService {
    async register(data) {
        const hashedPassword = await bcrypt.hash(data.password, 10);
        data.password = hashedPassword;
        return await addUser(data);
    }

    async login(email, password) {
        const user = await findUserByEmail(email);

        if (!user) {
            throw new Error('Invalid email or password');
        }

        const validPassword = await bcrypt.compare(password, user.password);

        if (!validPassword) {
            throw new Error('Invalid email or password');
        }

        const token = jwt.sign({ email: user.email, role: user.role }, JWT_SECRET, { expiresIn: JWT_EXPIRE });

        return token;
    }
}

module.exports = new AuthService();