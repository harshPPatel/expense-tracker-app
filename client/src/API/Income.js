import config from "../config";
import store from "../store";

/**
 * Makes Get All Income call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const getAll = async () => {
  let promise;
  const url = `${config.API_URL}/income/`;
  await fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: store.state.User.token,
    },
  })
    .then((res) => res.json())
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
 * Makes Add Income call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const addIncome = async (income) => {
  let promise;
  const url = `${config.API_URL}/income/create`;
  await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: store.state.User.token,
    },
    body: JSON.stringify(income),
  })
    .then((res) => res.json())
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
 * Makes Update Income call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const updateIncome = async (income) => {
  let promise;
  const url = `${config.API_URL}/income/update`;
  await fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: store.state.User.token,
    },
    body: JSON.stringify(income),
  })
    .then((res) => res.json())
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
 * Makes Delete Income call to the API server
 * @param {*} user User object
 * @returns {Promise} Returns promise
 */
const removeIncome = async (id) => {
  let promise;
  const url = `${config.API_URL}/income/${id}`;
  await fetch(url, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: store.state.User.token,
    },
  })
    .then((res) => res.json())
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
  addIncome,
  updateIncome,
  removeIncome,
};
