const bcrypt = require('bcrypt');

const { getToken } = require('./token');
const User = require('../db/models/User');

module.exports = async (req, res, next) => {
  // Hashing the password
  bcrypt
    .hash(req.body.password, 8)
    .then((hashedPassword) => {
      // Creating new User
      const user = new User({
        username: req.body.username,
        password: hashedPassword,
      });

      // Saving new user to database
      user.save()
        .then(async (response) => {
          // generating the token for user
          const authToken = await getToken(response.username);

          // Creating client reply
          const reply = {
            username: response.username,
            token: `Bearer ${authToken}`,
          };

          /* eslint-disable no-console */
          // Logging the response
          console.log(reply);
          /* eslint-enable no-console */

          return res.send(reply);
        })
        .catch((err) => {
          // Checking the database error code 11000 for duplication
          if (err.code === 11000) {
            res.status(409);
            next(new Error('Username already exists'));
          } else {
            next(new Error(err.message));
          }
        });
    })
    .catch((err) => {
      // throwing the error
      next(new Error(err));
    });
};
