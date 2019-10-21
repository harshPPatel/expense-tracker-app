const Joi = require('@hapi/joi');

const userSchema = Joi.object({
  username: Joi.string()
    .pattern(/(^[a-zA-Z0-9_]+$)/, { name: 'usernameValidation' })
    .min(3)
    .max(15)
    .required(),
  password: Joi.string()
    .pattern(/(^[a-zA-Z0-9_@]+$)/, { name: 'passwordValidation' })
    .min(6)
    .max(30)
    .required(),
  repeat_password: Joi.ref('password'),
});

module.exports = (req, res, next) => {
  // validating the user
  const validationResult = userSchema.validate(req.body);

  if (validationResult.error) {
    res.status(422);
    next(new Error(validationResult.error));
  }

  next();
};
