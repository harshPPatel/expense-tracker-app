const { Router } = require('express');

const validateTheme = require('../validators/theme');
const User = require('../models/User');

const router = Router();

/**
 * Handles the get request for theme of the user
 */
router.get('/', async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Returning the theme to the user
  res.status(200);
  res.json({
    username: user.username,
    theme: user.theme,
  });
});

/**
 * Handles the update request for theme of users
 */
router.put('/update', validateTheme, async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Updating the quote
  user.theme = Number(req.body.theme);

  // Saving user in database
  user.updateOne()
    .then(() => {
      // responsing back to the user
      res.status(200);
      res.json({
        username: req.username,
        message: 'Theme has updated successfully!',
        updatedTheme: user.theme,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
});

module.exports = router;
