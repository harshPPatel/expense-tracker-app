const jwt = require('jsonwebtoken');

const getToken = async (username) => {
  // Options for JWT
  const options = {
    expiresIn: '4h',
  };

  // Creating the token and returning it
  const token = await jwt.sign({ username }, `${process.env.AUTH_SECRET_KEY}`, options);
  return token;
};

module.exports = { getToken };
