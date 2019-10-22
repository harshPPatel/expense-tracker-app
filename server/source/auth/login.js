const bcrypt = require('bcrypt');

const User = require('../db/models/User');
const { getToken } = require('./token');

module.exports = async (req, res, next) => {
  // finding th user in database
  const user = await User.findOne({ username: req.body.username }).exec();

  // Checking the result from database
  if (!user) {
    res.status(404);
    next(new Error('Invalid Username or Password.'));
    return;
  }

  // Validating the password
  const isValidPassword = await bcrypt.compareSync(req.body.password, user.password);

  if (!isValidPassword) {
    res.status(400);
    next(new Error('Invalid Username or Password.'));
    return;
  }

  // Returning the data
  res.json({
    username: req.username,
    token: `Bearer ${await getToken(req.username)}`,
  });
};
