const express = require('express');

const signup = require('./signup');
const validateUser = require('../validation/user');

const router = express.Router();

router.get('/', (req, res) => {
  res.json({
    message: 'Welcome to Authentication API endpoint.',
    warning: 'Unauthorized access is prohibited!',
  });
});

router.post('/signup', validateUser, signup);

module.exports = router;
