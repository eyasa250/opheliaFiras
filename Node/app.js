const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const dotenv = require('dotenv');
const cors = require('cors');
const app = express();
const authenticate = require('./config/authentificate');

app.use(express.json());
app.use(bodyParser.json());
dotenv.config();
app.use(cors());

// Routers
const userRouter = require('./routes/userrouter');
const roomRouter = require('./routes/roomRouter');
const taskRouter = require('./routes/taskrouter');
const familyRoutes = require('./routes/familyroute');
const eventRouter= require('./routes/eventrouter');

// Apply authentication middleware to specific routes
app.use('/room', authenticate, roomRouter);
app.use('/task', authenticate, taskRouter);
app.use('/family', authenticate, familyRoutes);
app.use('/events',authenticate, eventRouter);


// User router without authentication on signup
app.use('/user', userRouter);

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