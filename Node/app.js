// app.js
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const cors = require('cors');
const app = express();
const userRouter = require('./routes/userrouter'); // Import user router

dotenv.config();
app.use(cors()); // Enable CORS

app.use(bodyParser.json());

// Use user router for routes related to user operations
app.use('/user', userRouter);

// Additional routers for other resources (room, task, etc.)

app.get("/", (req, res) => {
  res.send("Test route is working");
});

const MONGODB_URI = process.env.MONGODB_URI;
const PORT = process.env.PORT;

mongoose.connect(MONGODB_URI)
    .then(() => {
        console.log('Connected to MongoDB');
        app.listen(PORT, () => {
            console.log(`Server running on port ${PORT}`);
        });
    })
    .catch(err => console.error('Error connecting to MongoDB:', err));

// Global error handler
app.use((err, req, res, next) => {
    err.statusCode = err.statusCode || 500;
    err.status = err.status || 'error';

    res.status(err.statusCode).json({
        status: err.status,
        message: err.message
    });
});

module.exports = app;
