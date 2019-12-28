const { hash, compareSync } = require('bcrypt');

const User = require('../models/User');
const blacklistToken = require('../utils/blacklistToken');

/**
 * Handles the change password request of user
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const changePassword = async (req, res, next) => {
  // finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not found'));
    return;
  }

  // validating the current_password
  const isValidCurrentPassword = compareSync(req.body.current_password, user.password);

  if (!isValidCurrentPassword) {
    res.status(400);
    next(new Error('Invalid Current Password'));
    return;
  }

  // hashing new password
  await hash(req.body.new_password, Number(process.env.HASH_SALT_ROUNDS) || 10)
    .then((hashed) => {
      // updating the password
      user.password = hashed;
    })
    .catch((err) => {
      next(new Error(err.message));
    });

  // Getting token from database
  const token = req.headers.authorization.split(' ')[1];

  // Saving the user
  user.updateOne()
    .then(() => {
      // Black listing the current token
      blacklistToken(token)
        .then(() => {
          // Sending the response to the user
          res.status(200);
          return res.json({
            username: req.username,
            message: 'Your password has changed successfully!',
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

module.exports = changePassword;
