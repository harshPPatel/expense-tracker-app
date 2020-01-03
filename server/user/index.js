const { Router } = require('express');

const validateChangePassword = require('../validators/changePassword');
const deleteAccount = require('./deleteAccount');
const quote = require('./quote');
const changePassword = require('./changePassword');
const theme = require('./theme');
const currency = require('./currency');
const expenseWarningLimit = require('./expenseWarningLimit');
const defaultSettings = require('./defaultSettings');

const router = Router();

// Delete Account Route
router.delete('/delete', deleteAccount);

// Change Password Route
router.put('/changePassword', validateChangePassword, changePassword);

// Quote Routes
router.use('/quote', quote);

// Theme Routes
router.use('/theme', theme);

// Currency Routes
router.use('/currency', currency);

// Currency Routes
router.use('/expenseWarningLimit', expenseWarningLimit);

// Default settings route
router.put('/default', defaultSettings);

module.exports = router;
