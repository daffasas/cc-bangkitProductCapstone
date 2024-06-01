const { login, register } = require("./auth.service");

class AuthController {
    async registerUser(req, res) {
        try {
            const users = await register(req.body);

            res.status(201).json({
                message: 'User created successfully',
                data: users
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }

    async loginUser(req, res) {
        const { email, password } = req.body;
        
        try {
            const token = await login(email, password);

            res.status(200).json({
                message: 'Login successfully',
                token: token
            });
        } catch (error) {
            res.status(500).json({ message: error.message });
        }
    }
}

module.exports = new AuthController();