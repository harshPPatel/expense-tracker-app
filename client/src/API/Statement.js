import config from '../config';
import store from '../store';

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
 * Makes Statement call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const getStatementsByMonth = async (token) => {
  let promise;
  const { month } = store.state.UI.statements;
  const year = store.state.UI.statements.year
    ? store.state.UI.statements.year
    : new Date().getFullYear();
  const url = `${config.API_URL}/statement/?month=${month}&year=${year}`;
  await fetch(url, {
    method: 'GET',
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

export default {
  getCurrent,
  getStatementsByMonth,
};
