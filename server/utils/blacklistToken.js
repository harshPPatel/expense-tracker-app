const BlacklistToken = require('../models/BlacklistToken');

/**
 * Blacklists the provided token
 * @param {*} token Token to be blacklisted
 * @returns {Promise} promise which resolves if token is blacklisted.
 */
const blacklistToken = (token) => {
  // Creating new token
  const dbToken = new BlacklistToken({ token });

  // returning promise
  return new Promise((resolve, reject) => {
    // Saving token to the database
    dbToken.save()
      .then((response) => {
        console.log(`Blacklisted the token: '${response.token}'.`);
        // resolving the promise
        resolve();
      })
      .catch((err) => {
        // rejecting the promise with error
        reject(err);
      });
  });
};

module.exports = blacklistToken;
