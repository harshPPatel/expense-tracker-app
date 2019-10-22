const jwt = require('jsonwebtoken');

module.exports = (req, res, next) => {
  // Checking authorization header on Request
  if (!req.header.authorization) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // Getting token
  const token = req.header.authorization.split(' ')[1];

  // Validating the token
  try {
    const decoded = jwt.verify(token, `${process.env.AUTH_SECRET_KEY}`);
    req.username = decoded.username;
    next();
  } catch (err) {
    res.status(400);
    next(new Error('Bad Request'));
  }
};
