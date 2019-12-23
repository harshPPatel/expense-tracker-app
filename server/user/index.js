const { Router } = require('express');

const validateChangePassword = require('../validators/changePassword');
const deleteAccount = require('./deleteAccount');
const quote = require('./quote');
const changePassword = require('./changePassword');
const theme = require('./theme');

const router = Router();

// Delete Account Route
router.delete('/delete', deleteAccount);

// Change Password Route
router.put('/changePassword', validateChangePassword, changePassword);

// Quote Routes
router.use('/quote', quote);

// Theme Routes
router.use('/theme', theme);

module.exports = router;
