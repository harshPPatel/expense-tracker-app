const jwt = require('jsonwebtoken');
const User = require('../db/models/User');

module.exports = async (req, res, next) => {
  // Checking authorization header on Request
  if (!req.headers.authorization) {
    res.status(400);
    next(new Error('Bad Request'));
    return;
  }

  // Getting token
  const token = req.headers.authorization.split(' ')[1];

  // Validating the token
  try {
    const decoded = jwt.verify(token, `${process.env.AUTH_SECRET_KEY}`);
    req.username = decoded.username;

    // finding the user in database to check if he is not a whitelisted user
    const user = await User.findOne({ username: decoded.username }).exec();
    if (user.status === 0) {
      next();
    } else {
      res.status(401);
      next(new Error('Unauthorized Access'));
      return;
    }
  } catch (err) {
    res.status(401);
    next(new Error('Unauthorized Access'));
  }
};
