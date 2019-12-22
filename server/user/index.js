const { Router } = require('express');

const deleteAccount = require('./deleteAccount');
const quote = require('./quote');

const router = Router();

// Delete Account Route
router.delete('/delete', deleteAccount);

// Quote Routes
router.use('/quote', quote);

module.exports = router;
