const express = require('express');

const signup = require('./signup');
const login = require('./login');
const logout = require('./logout');
const validateUser = require('../validation/user');
const validateToken = require('../validation/token');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'Welcome to Authentication API endpoint.',
    warning: 'Unauthorized access is prohibited!',
  });
});

// Sign Up Route
router.post('/signup', validateUser, signup);

// Login Route
router.post('/login', validateUser, login);

// Verify Route
router.post('/verify', validateToken, (req, res) => {
  res.json({
    message: 'Token verified successfully.',
    isValid: true,
  });
});

// Logout Route
router.post('/logout', validateToken, logout);

module.exports = router;
