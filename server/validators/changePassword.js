const joi = require('@hapi/joi');

/**
 * Validates the change password object from request body
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const validateChangePassword = async (req, res, next) => {
  // Throwing error if repeat_password property does not exists
  if (!req.body.repeat_password) {
    res.status(422);
    next(new Error('repeat_password field is required for the signup.'));
  }

  // Schema for quote
  const quoteSchema = joi.object({
    current_password: joi.string()
      .trim()
      .pattern(/(^[a-zA-Z0-9_@]+$)/, { name: 'passwordValidation' })
      .min(6)
      .max(30)
      .required(),
    new_password: joi.string()
      .trim()
      .pattern(/(^[a-zA-Z0-9_@]+$)/, { name: 'passwordValidation' })
      .min(6)
      .max(30)
      .required(),
    repeat_password: joi.ref('new_password'),
  });

  // validating request body for quote
  const validationResult = await quoteSchema.validate(req.body);

  // Throwing error if quote is invalid
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
    return;
  }

  // Continuing the app
  next();
};

module.exports = validateChangePassword;
