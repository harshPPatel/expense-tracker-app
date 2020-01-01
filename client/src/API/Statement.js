import config from '../config';

/**
 * Makes Statement call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const getCurrent = async (token) => {
  let promise;
  const url = `${config.API_URL}/statement/`;
  await fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
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
  getCurrent,
};
