const { verify } = require('jsonwebtoken');
const BlacklistToken = require('../models/BlacklistToken');

// Private key for generating token
const PRIVATE_KEY = process.env.AUTH_SECRET_KEY;

// JWT Options
const options = {
  algorithm: 'HS256',
  expiresIn: '2 days',
};

/**
 * validates the token from request
 * @param {*} req Request object
 * @param {*} res Response object
 * @param {*} next next function
 */
const validateToken = async (req, res, next) => {
  // Throwing error if authorization header does not exists
  if (!req.headers.authorization) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // Getting token from request
  const token = req.headers.authorization.split(' ')[1];

  try {
    // verifying token
    const decoded = await verify(token, PRIVATE_KEY, options);

    // Checking if token is not blacklisted
    const blackListToken = await BlacklistToken.findOne({ token }).exec();
    if (blackListToken) {
      res.status(401);
      next(new Error('Unauthorized Access'));
      return;
    }

    // adding username to request object
    req.username = decoded.username;
    next();
  } catch (err) {
    res.status(401);
    next(new Error('Unauthorized Access'));
  }
};

module.exports = validateToken;
