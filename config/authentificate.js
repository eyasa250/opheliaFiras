const jwt = require('jsonwebtoken');

const authenticate = (req, res, next) => {
    if (req.path === '/user/signup') {
        return next();
    }
    const authHeader = req.headers.authorization;

    if (authHeader && authHeader.startsWith('Bearer ')) {
        const token = authHeader.split(' ')[1];

        try {
            const decoded = jwt.verify(token, process.env.JWT_SECRET);
            req.user = decoded;
            next();
        } catch (error) {
            return res.status(401).json({ error: 'Invalid token' });
        }
    } else {
        return res.status(401).json({ error: 'Authentication token required' });
    }
};

module.exports = authenticate;
