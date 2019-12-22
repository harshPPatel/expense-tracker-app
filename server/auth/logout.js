const BlacklistToken = require('../models/BlacklistToken');

const logout = (req, res, next) => {
  // Getting token from request
  const token = req.headers.authorization.split(' ')[1];

  // Saving blacklisted token to database
  const dbToken = new BlacklistToken({ token });

  dbToken.save()
    .then((response) => {
      console.log(`Blacklisted the token: '${response.token}' for user: '${req.username}'`);

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
