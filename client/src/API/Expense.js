import config from '../config';
import store from '../store';

/**
 * Makes Get All Expense call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const getAll = async () => {
  let promise;
  const url = `${config.API_URL}/expense/`;
  await fetch(url, {
    method: 'GET',
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

/**
 * Makes Add Expense call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const addExpense = async (expense) => {
  let promise;
  const url = `${config.API_URL}/expense/create`;
  await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: store.state.User.token,
    },
    body: JSON.stringify(expense),
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
 * Makes Update Expense call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const updateExpense = async (expense) => {
  let promise;
  const url = `${config.API_URL}/expense/update`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: store.state.User.token,
    },
    body: JSON.stringify(expense),
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
 * Makes Delete Expense call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const removeExpense = async (id) => {
  let promise;
  const url = `${config.API_URL}/expense/delete`;
  await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: store.state.User.token,
    },
    body: JSON.stringify({ _id: id }),
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
  getAll,
  addExpense,
  updateExpense,
  removeExpense,
};
