"use strict";

import wrap from "express-async-wrap";
import interparkDAO from "../dao/mongo/shoppingMall/InterparkDAO";

//insert
const insertShoppingItem = wrap(async function (req, res) {

	const doc = req.swagger.params.shoppingItemRequest.value;

	try {
		const result = await interparkDAO.insertMany(doc);

		if(result.insertedCount == 1)
			res.send({isSuccess: true});
		else
			res.send({isSuccess: false});
	} catch (e) {
		console.log(e);
		res.send({isSuccess: false, message: e.message});
	}
});

//select one
const getAllShoppingItem = wrap(async function (req, res) {
	const id = req.swagger.params.id.value;

	try {
		const shoppingItem = await interparkDAO.selectOne(id);

		if(shoppingItem) {
            res.send({isSuccess: true, result: shoppingItem});
        } else {
            res.send({isSuccess: false, errCode: 404});
        }
	} catch (e) {
		console.log(e);
		res.send({isSuccess: false, errCode: 500, message: e.message});
	}
});

//update
const setChangeShoppingItem = wrap(async function (req, res) {
	const shopItem = req.swagger.params.shoppingItemRequest.value;

	try {
		for(var i=0; i<shopItem.length; i++){
			var shoppingItem = await interparkDAO.update(
				shopItem[i].id, shopItem[i]);
		}
		res.send({isSuccess: true, result: shoppingItem});
	} catch (e) {
		console.log(e);
		res.send({isSuccess: false, message: e.message});
	}
})

//delete
const delShoppingItem = wrap(async function (req, res) {
	const itemId = req.swagger.params.shoppingItemRequest.value;
	var resultOk = true;
	try {
		for(var i=0; i<itemId.length; i++){
			const result = await interparkDAO.deleteOne(itemId[i]);
			var resultJson = JSON.parse(result);
			resultOk = resultJson && !resultJson.ok;
			console.log(!resultOk);
		}
		if(!resultOk)
			res.send({isSuccess: true, message: '삭제 성공'});
		else
			res.send({isSuccess: false, message: '삭제 실패'});
	} catch (e) {
		console.log(e);
		res.send({isSuccess: false, message: e.message});
	}
})

module.exports = {
	insertShoppingItem,
	getAllShoppingItem,
	setChangeShoppingItem,
	delShoppingItem
};