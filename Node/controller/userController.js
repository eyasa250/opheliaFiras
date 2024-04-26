const User = require("../model/User");
const bcrypt = require("bcrypt");
const jwt = require('jsonwebtoken');

exports.registerUser = async(req, res)=> {
    try {
        const { username, email, password } = req.body;
        const hashedPassword = await bcrypt.hash(password, 10);
        const user = await User.create({
            username,
            email,
            password: hashedPassword
        });
        res.status(201).json({ message: "User registered successfully", user });
    } catch (error) {
        console.error("Error registering user:", error);
        res.status(500).json({ message: "Server error" });
    }
}

exports.loginUser=async(req, res) =>{
    try {
        const { email, password } = req.body;
        const user = await User.findOne({ where: { email } });
        if (!user) {
            return res.status(400).json({ message: "Invalid credentials." });
        }
        const isPasswordMatch = await bcrypt.compare(password, user.password);
        if (!isPasswordMatch) {
            return res.status(400).json({ message: "Invalid credentials." });
        }
        const token = jwt.sign({ userId: user.id }, process.env.JWT_SECRET || "secretKey123", { expiresIn: "1h" });
        res.json({ message: 'User logged in successfully', token });
    } catch (error) {
        console.error("Error logging in:", error);
        res.status(500).json({ message: "Server error" });
    }
}

exports.getUserInfo = async (req, res) => {
    // Call verifyToken function
    verifyToken(req, res, async () => {
        try {
            const user = await User.findByPk(req.user.userId);
            if (!user) {
                return res.status(404).json({ message: "User not found." });
            }
            res.json({ user });
        } catch (error) {
            console.error("Error fetching user info:", error);
            res.status(500).json({ message: "Server error" });
        }
    });
};

function verifyToken(req, res, next) {
    const bearerHeader = req.header("Authorization");
    if (!bearerHeader) {
        return res.status(401).json({ message: "Access denied." });
    }
    const token = bearerHeader.split(" ")[1];
    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET || "secretKey123");
        req.user = decoded;
        next();
    } catch (error) {
        console.error('Error verifying token:', error);
        res.status(401).json({ message: "Invalid token." });
    }
}

