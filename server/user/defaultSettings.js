const User = require('../models/User');

const defaultSettings = async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({
    username: req.username,
  }).exec();

  // Throwing the error if user does not exists
  if (!user) {
    res.status(400);
    next(new Error('User not found.'));
    return;
  }

  // Setting default settings
  user.theme = 0;
  user.currency = '$';
  user.expenseWarningLimit = 1000;

  // Saving user in database
  user.updateOne()
    .then(() => {
      // responsing back to the user
      res.status(200);
      res.json({
        username: req.username,
        message: 'Settings are set to the default.',
        defaultSettings: {
          theme: 0,
          currency: '$',
          expenseWarningLimit: 1000,
        },
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
};

module.exports = defaultSettings;
