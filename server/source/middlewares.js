/**
 * Function to log the error
 * @param {*} err Error got from middleware
 */
/* eslint-disable no-console */
function logError(err) {
  console.error({
    message: err.message,
    stack: err.stack,
  });
}
/* eslint-enable no-console */

/**
 * Middleware to handle not found URL for the API
 */
function notFound(req, res, next) {
  res.status(404);
  const error = new Error(`Not Found - ${req.originalUrl}`);
  next(error);
}

/**
 * Error Handler for API
 */
/* eslint-disable no-unused-vars */
function errorHandler(err, req, res, next) {
  /* eslint-enable no-unused-vars */
  const statusCode = res.statusCode !== 200 ? res.statusCode : 500;
  res.status(statusCode);

  logError(err);

  return res.json({
    message: err.message,
    stack: process.env.NODE_ENV === 'production' ? '' : err.stack,
  });
}

module.exports = {
  notFound,
  errorHandler,
};
