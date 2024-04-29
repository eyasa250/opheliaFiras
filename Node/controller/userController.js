const User = require('../model/User');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const InvalidToken = require('../model/invalideToken');
//  une liste noire pour stocker les tokens révoqués
const issuedTokens = [];

// Register user
exports.signup = async (req, res, next) => {
    try {
        const { username, email, password, role } = req.body;

        const userExists = await User.findOne({ email: email });
        if (userExists) {
            return res.status(400).json({ message: 'User already exists!' });
        }
       
        const newUser = new User({
            username: username,
            email: email,
            password: password,
            role: role ? role : ''
        });

        await newUser.save();

       

     

        res.status(201).json({ message: 'User created successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error creating user' });
    }
};

exports.login = async (req, res, next) => {
    try {
        const { email, password } = req.body;

        if (!email || !password) {
            return res.status(400).json({ error: 'Email and password are required' });
        }

        const user = await User.findOne({ email });
        if (!user) {
            return res.status(404).json({ error: 'User not found' });
        }

        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ error: 'Invalid email or password' });
        }

        const token = jwt.sign({ id: user._id, role: user.role }, process.env.JWT_SECRET, {
            expiresIn: '90d',
        });

        res.cookie('token', token, {
            httpOnly: true, // Sécurité améliorée en empêchant l'accès au cookie via JS côté client
            secure: process.env.NODE_ENV === 'production', // En production, envoyez le cookie seulement sur HTTPS
            maxAge: 24 * 60 * 60 * 1000, // Durée de vie du cookie, par exemple 1 jour
            sameSite: 'strict' // Protection contre les attaques CSRF
          });
        res.status(200).json({ 
            token,
            role: user.role
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'An error occurred during the login process' });
    }

      
};

// Logout user
exports.logout = async (req, res, next) => {
    try {
        // Vérifier si l'en-tête Authorization existe dans la demande
        if (!req.headers.authorization) {
            return res.status(400).json({ error: 'Authorization header is missing' });
        }

        // Récupérer le token du header Authorization
        const tokenParts = req.headers.authorization.split(' ');
        if (tokenParts.length !== 2 || tokenParts[0] !== 'Bearer') {
            return res.status(400).json({ error: 'Invalid authorization header format' });
        }
        const token = tokenParts[1];

        // Enregistrez le token actuel dans le modèle InvalidToken
        await InvalidToken.create({ token });

        res.status(200).json({ message: 'Logged out successfully' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'An error occurred during the logout process' });
    }
};


//fonction pour getter tous les users de role user khw 

exports.getAllUsers = async (req, res, next) => {
    try {
        const users = await User.find({ role: 'user' });

        res.status(200).json(users);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'An error occurred while fetching users' });
    }
};