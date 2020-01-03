const joi = require('@hapi/joi');

/**
 * Validates expenseWarningLimit from request object
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const validateExpenseWarningLimit = async (req, res, next) => {
  // Schema for expenseWarningLimit
  const expenseWarningLimitSchema = joi.object({
    expenseWarningLimit: joi.number()
      .min(0)
      .required(),
  });

  // validating request body for theme
  const validationResult = await expenseWarningLimitSchema.validate(req.body);

  // Throwing error if theme is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = validateExpenseWarningLimit;
