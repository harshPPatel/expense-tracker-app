const express = require('express');

const userValidation = require('../validators/user');
const signup = require('./signup');
const login = require('./login');

const router = express.Router();

// Signup Route
router.post('/signup', userValidation.singup, signup);

// Login Route
router.post('/login', userValidation.login, login);

module.exports = router;
