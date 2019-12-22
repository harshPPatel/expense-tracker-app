const User = require('../models/User');
const blacklistToken = require('../utils/blacklistToken');

/**
 * Handles the deleteAccount request
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const deleteAccount = async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if user does not exists
  if (!user) {
    res.status(400);
    next(new Error('User not found.'));
    return;
  }

  // removing the user from database
  user.remove()
    .then(() => {
      console.log(`Account for user: '${req.username}' is deleted successfully`);
      // Blacklisting the token
      const token = req.headers.authorization.split(' ')[1];
      blacklistToken(token)
        .then(() => {
          res.status(200);
          res.json({
            message: 'Account deleted successfully',
          });
        })
        .catch((err) => {
          next(new Error(err.message));
        });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = deleteAccount;
