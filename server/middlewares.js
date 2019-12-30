/**
 * Handles 'No Route Found' error in app
 * @param {*} req Request object
 * @param {*} res Response Object
 * @param {*} next next function
 */
const notFoundHandler = (req, res, next) => {
  res.status(400);
  next(new Error(`Requested Route '${req.originalUrl}' does not exists.`));
};

/**
 * Handles the errors in the app and returns response with useful data
 * @param {*} err Error object
 * @param {*} req Request Object
 * @param {*} res Response Object
 * @param {*} next next function
 */
/* eslint-disable no-unused-vars */
const errorHandler = (err, req, res, next) => {
  /* eslint-enable no-unused-vars */
  // Response Code
  const code = res.statusCode !== 200 ? res.statusCode : 500;
  res.status(code);

  // Response
  return res.json({
    errorCode: code,
    message: err.message,
    requestURI: req.originalUrl,
    stack: process.env.NODE_ENV === 'production'
      ? ''
      : err.stack,
  });
};

module.exports = {
  notFoundHandler,
  errorHandler,
};
