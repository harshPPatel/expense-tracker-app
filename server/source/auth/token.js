const jwt = require('jsonwebtoken');

const getToken = async (username) => {
  const options = {
    expiresIn: '4h',
  };
  const token = await jwt.sign({ username }, `${process.env.AUTH_SECRET_KEY}`, options);
  return token;
};

module.exports = { getToken };
