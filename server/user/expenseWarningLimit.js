const { Router } = require('express');

const User = require('../models/User');
const validateExpenseWarningLimit = require('../validators/expenseWarningLimit');

const router = Router();

/**
 * Handles the get request of user's expenseWarningLimit
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

  // Returning the expenseWarningLimit to the user
  res.status(200);
  res.json({
    username: user.username,
    expenseWarningLimit: user.expenseWarningLimit,
  });
});

/**
 * Handles the update(put) request of user's expenseWarningLimit
 */
router.put('/update', validateExpenseWarningLimit, async (req, res, next) => {
  // Finding the user in database
  const user = await User.findOne({ username: req.username }).exec();

  // Throwing the error if the user does not exists
  if (!user) {
    res.status(404);
    next(new Error('User not Found'));
    return;
  }

  // Updating the expenseWarningLimit
  user.expenseWarningLimit = Number(req.body.expenseWarningLimit);

  // Saving user in database
  user.save()
    .then(() => {
      // responsing back to the user
      res.status(200);
      res.json({
        username: req.username,
        message: 'Quote has updated successfully!',
        updatedExpenseWarningLimit: user.expenseWarningLimit,
      });
    })
    .catch((err) => {
      next(new Error(err.message));
    });
});

module.exports = router;
