const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const cors = require('cors');
const app = express();
app.use(express.json());

app.use(bodyParser.json());
//const authenticate = require('./middleware/authMiddleware');
dotenv.config();
app.use(cors()); // Enable CORS

//models

//routers
const userrouter = require('./routes/userrouter');
const roomRouter = require('./routes/roomrouter');
const taskRouter = require('./routes/taskrouter');

app.use('/user', userrouter);
app.use('/room', roomRouter);
app.use('/task', taskRouter);

app.get("/", (req, res) => {
  res.send("Test route is working");
});

app.use(bodyParser.json());


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
