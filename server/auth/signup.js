const { hash } = require('bcrypt');
const User = require('../models/User');

/**
 * Handles the Request to '/singup' route
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const signup = (req, res, next) => {
  const user = {
    username: req.body.username.toString(),
    password: req.body.password.toString(),
  };

  // Hashing the password
  hash(user.password, Number(process.env.HASH_SALT_ROUNDS) || 10)
    .then(async (hashed) => {
      user.password = hashed;

      // Adding user to the database
      const dbUser = new User(user);

      // Saving user
      dbUser.save()
        .then((response) => {
          // responsing with appropriate values
          res.status(200);
          res.json({
            username: response.username,
            message: 'Account created Successfully!',
          });
        })
        .catch((err) => {
          // Checking if the username already exists in the database
          if (err.code === 11000) {
            res.status(409);
            next(new Error('Username already exists.'));
            return;
          }

          next(new Error(err.message));
        });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = signup;
