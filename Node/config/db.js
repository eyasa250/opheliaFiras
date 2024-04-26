const { Sequelize } = require('sequelize');

// Initialize Sequelize with your MySQL database credentials
const sequelize = new Sequelize('ophelia0', 'root', '', {
  host: 'localhost',
  dialect: 'mysql' ,
  freezeTableName: false,// This should be a string
  logging: false// Log warnings and errors only

});
// Test the database connection
async function testConnection() {
  try {
    await sequelize.authenticate();
    console.log('Connected to MySQL database successfully.');
  } catch (error) {
    console.error('Error connecting to MySQL database:', error);
  }
}

// Synchronize models with the database
async function synchronizeModels() {
  try {
    await sequelize.sync({ alter: true });
    console.log('Models synchronized with the database.');
  } catch (error) {
    console.error('Error synchronizing models with the database:', error);
  }
}

// Test the database connection and synchronize models
async function initializeDatabase() {
  await testConnection();
  await synchronizeModels();
}

initializeDatabase();

module.exports = sequelize;
