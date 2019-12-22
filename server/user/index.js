const { Router } = require('express');

const validateToken = require('../validators/token');
const deleteAccount = require('./deleteAccount');

const router = Router();

// Delete Account Route
router.delete('/delete', validateToken, deleteAccount);

module.exports = router;
