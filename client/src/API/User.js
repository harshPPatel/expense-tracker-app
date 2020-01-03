import config from '../config';
import store from '../store';

/**
 * Makes Update Quote call to the API server
 * @returns {Promise} Returns promise
 */
const updateQuote = async (options, token) => {
  let promise;
  const url = `${config.API_URL}/user/quote/update`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
    },
    body: JSON.stringify(options),
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
 * Makes Update Theme call to the API server
 * @returns {Promise} Returns promise
 */
const updateTheme = async (theme, token) => {
  let promise;
  const url = `${config.API_URL}/user/theme/update`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
    },
    body: JSON.stringify({ theme }),
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
 * Makes Update Currency call to the API server
 * @returns {Promise} Returns promise
 */
const updateCurrency = async (currency, token) => {
  let promise;
  const url = `${config.API_URL}/user/currency/update`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
    },
    body: JSON.stringify({ currency }),
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
 * Makes Update Expense Warning Limit call to the API server
 * @returns {Promise} Returns promise
 */
const updateExpenseWarningLimit = async (expenseWarningLimit, token) => {
  let promise;
  const url = `${config.API_URL}/user/expenseWarningLimit/update`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
    },
    body: JSON.stringify({ expenseWarningLimit }),
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
 * Makes Change Password call to the API server
 * @returns {Promise} Returns promise
 */
const changePassword = async (payload, token) => {
  let promise;
  const url = `${config.API_URL}/user/changePassword`;
  await fetch(url, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token || store.state.User.token,
    },
    body: JSON.stringify(payload),
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
 * Makes Change to Default Settings call to the API server
 * @returns {Promise} Returns promise
 */
const setDefaultSettings = async (token) => {
  let promise;
  const url = `${config.API_URL}/user/default`;
  await fetch(url, {
    method: 'PUT',
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
 * Makes Delete Accpunt call to the API server
 * @returns {Promise} Returns promise
 */
const deleteAccount = async (token) => {
  let promise;
  const url = `${config.API_URL}/user/delete`;
  await fetch(url, {
    method: 'DELETE',
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
  updateQuote,
  updateTheme,
  updateCurrency,
  updateExpenseWarningLimit,
  changePassword,
  setDefaultSettings,
  deleteAccount,
};
