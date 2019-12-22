const { sign } = require('jsonwebtoken');

// Private key for generating token
const PRIVATE_KEY = process.env.AUTH_SECRET_KEY;

/**
 * Generates the token for provided username
 * @param {String} username User's username to add in the token
 */
const generateToken = async (username) => {
  // JWT Options
  const options = {
    algorithm: 'HS256',
    expiresIn: '2 days',
  };

  // JWT Payload
  const payload = { username };

  // Generating and returning the token
  const token = await sign(payload, PRIVATE_KEY, options);
  return token;
};

module.exports = {
  generateToken,
};
