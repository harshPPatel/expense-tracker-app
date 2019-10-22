const express = require('express');

const signup = require('./signup');
const login = require('./login');
const validateUser = require('../validation/user');
const validateToken = require('../validation/token');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'Welcome to Authentication API endpoint.',
    warning: 'Unauthorized access is prohibited!',
  });
});

router.post('/signup', validateUser, signup);

router.post('/login', validateUser, login);

module.exports = router;
