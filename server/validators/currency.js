const joi = require('@hapi/joi');

/**
 * Validates currency from the request body
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const validateCurrency = async (req, res, next) => {
  // Schema for currency
  const currencySchema = joi.object({
    currency: joi.string()
      .trim()
      .min(1)
      .max(1)
      .pattern(/^(\$|€|₹|£|¥)$/, { name: 'Currency' })
      .required(),
  });

  // validating request body for currency
  const validationResult = await currencySchema.validate(req.body);

  // Throwing error if currency is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = validateCurrency;
