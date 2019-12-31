import config from '../config';
import store from '../store';

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

/**
 * Makes Login call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const login = async (user) => {
  let promise;
  const url = `${config.API_URL}/auth/login`;
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

/**
 * Makes token validation call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const validateToken = async (token) => {
  let promise;
  const url = `${config.API_URL}/auth/token`;
  await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
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

/**
 * Makes logout call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const logout = async () => {
  let promise;
  const url = `${config.API_URL}/auth/logout`;
  await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: store.state.User.token,
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
  signup,
  login,
  logout,
  validateToken,
};
