"use strict";

var _expressAsyncWrap = require("express-async-wrap");

var _expressAsyncWrap2 = _interopRequireDefault(_expressAsyncWrap);

var _InterparkDAO = require("../dao/mongo/shoppingMall/InterparkDAO");

var _InterparkDAO2 = _interopRequireDefault(_InterparkDAO);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//insert
var insertShoppingItem = (0, _expressAsyncWrap2.default)(async function (req, res) {
	//파라미터 담는 놈.	yaml에 구현해야한다. spring에서 받아오는 item 하나하나
	var doc = req.swagger.params.shoppingItemRequest.value;

	try {
		var result = await _InterparkDAO2.default.insert(doc);

		if (result.insertedCount == 1) res.send({ isSuccess: true });else res.send({ isSuccess: false });
	} catch (e) {
		console.log(e);
		res.send({ isSuccess: false, message: e.message });
	}
});

//select and
var getShoppingItem = (0, _expressAsyncWrap2.default)(async function (req, res) {
	try {
		var shoppingItems = await _InterparkDAO2.default.selectAnd();

		res.send({ isSuccess: true, result: shoppingItems });
	} catch (e) {
		console.log(e);
		res.send({ idSuccess: false, message: e.message });
	}
});

//select all
var getAllShoppingItem = (0, _expressAsyncWrap2.default)(async function (req, res) {
	try {
		var shoppingItems = await _InterparkDAO2.default.select();

		res.send({ isSuccess: true, result: shoppingItems });
	} catch (e) {
		console.log(e);
		res.send({ idSuccess: false, message: e.message });
	}
});

module.exports = {
	insertShoppingItem: insertShoppingItem,
	getShoppingItem: getShoppingItem,
	getAllShoppingItem: getAllShoppingItem
};