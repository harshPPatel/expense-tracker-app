const joi = require('@hapi/joi');

// Common User Scema for Joi Validation
const userSchema = {
  username: joi.string()
    .pattern(/(^[a-zA-Z0-9_]+$)/, { name: 'usernameValidation' })
    .min(3)
    .max(15)
    .required(),
  password: joi.string()
    .pattern(/(^[a-zA-Z0-9_@]+$)/, { name: 'passwordValidation' })
    .min(6)
    .max(30)
    .required(),
};

/**
 * Validates the input data for singup user
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const singup = async (req, res, next) => {
  // Throwing error if repeat_password property does not exists
  if (!req.body.repeat_password) {
    res.status(422);
    next(new Error('repeat_password field is required for the signup.'));
  }

  // Creating Joi object
  const singupUserSchema = joi.object({
    ...userSchema,
    repeat_password: joi.ref('password'),
  });

  // Validating the input
  const validationResult = await singupUserSchema.validate(req.body);

  // Throwing error
  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
  }

  // Continuing the app
  next();
};

module.exports = {
  singup,
};
