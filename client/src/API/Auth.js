import config from '../config';

/**
 * Makes Sign Up call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const signup = async (user) => {
  let promise;
  const url = `${config.API_URL}/auth/signup`;
  await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ ...user }),
  })
    .then(res => res.json())
    .then((data) => {
      promise = new Promise((resolve, reject) => {
        // rejecting promise if response has errorCode
        if (data.errorCode) {
          reject(data);
        } else {
          resolve(data);
        }
      });
    });
  return promise;
};

export default {
  signup,
};
