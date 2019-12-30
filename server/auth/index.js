const { Router } = require('express');

const userValidation = require('../validators/user');
const tokenValidation = require('../validators/token');
const signup = require('./signup');
const login = require('./login');
const logout = require('./logout');

const router = Router();

// Signup Route
router.post('/signup', userValidation.signup, signup);

// Login Route
router.post('/login', userValidation.login, login);

// Logout Route
router.post('/logout', tokenValidation, logout);

module.exports = router;
