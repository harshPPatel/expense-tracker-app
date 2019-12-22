const blacklistToken = require('../utils/blacklistToken');

const logout = (req, res, next) => {
  // Getting token from request
  const token = req.headers.authorization.split(' ')[1];

  // Black-listing the token
  blacklistToken(token)
    .then(() => {
      // Sending response to the user
      res.status(200);
      res.json({
        message: 'You logged out successfully!',
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = logout;
