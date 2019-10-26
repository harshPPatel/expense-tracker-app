const User = require('../db/models/User');

module.exports = async (req, res, next) => {
  // finding th user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Blacklisting the user
  user.status = 1;

  // Saving the user to the database
  user.save()
    .then(() => {
      res.json({
        isLoggedOut: true,
        message: 'User is logged out successfully',
      });
    })
    .catch(() => {
      next(new Error('Internale Server Error: Unable to logout the user.'));
    });
};
