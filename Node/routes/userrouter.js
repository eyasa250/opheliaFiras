const express = require('express');
const router = express.Router();
const userController = require('../controller/userController');

router.post("/register", userController.registerUser);
router.post("/login", userController.loginUser);
router.get("/userinfo", userController.getUserInfo);
router.get("/", (req, res) => {
    res.send("Test route is working");
});
module.exports = router;
