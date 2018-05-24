'use strict';

var util = require('util');

module.exports = {
	hello2: hello
};

function hello(req, res) {
	var name = req.swagger.params.name2.value || 'stranger';
	var hello = util.format('Hello, %s!', name);

	res.json(hello);
}