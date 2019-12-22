const express = require('express');

const userValidation = require('../validators/user');
const signup = require('./signup');

const router = express.Router();

// Singup Route
router.post('/signup', userValidation.singup, signup);

module.exports = router;
