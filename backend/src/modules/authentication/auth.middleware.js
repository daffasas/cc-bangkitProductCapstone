const jwt = require('jsonwebtoken');

const JWT_SECRET = process.env.JWT_SECRET;

class AuthMiddleware {
    async authenticateToken(req, res, next) {
        const token = req.header('Authorization')?.split(' ')[1];

        if (!token) {
            return res.status(401).json({
                message: 'Access denied, no token provided.'
            });
        }

        try {
            const verified = jwt.verify(token, JWT_SECRET);
            req.user = verified;
            next();
        } catch (error) {
            res.status(400).json({ message: 'Invalid token.' });
        }
    }

    authorizeRole(role) {
        return (req, res, next) => {
            if (req.user.role !== role) {
                return res.status(403).json({
                    message: 'Access denied, your role is not allow to access this API.'
                });
            }

            next();
        }
    }

    // Method to authorize role 1
    async authorizeRole1(req, res, next) {
        this.authorizeRole(1)(req, res, next);
    }
}

module.exports = new AuthMiddleware();