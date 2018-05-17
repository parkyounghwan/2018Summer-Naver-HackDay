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
	const itemId = req.swagger.params.itemId.value;
	const shopId = req.swagger.params.shopId.value;

	try {
		const shoppingItem = await interparkDAO.selectOne(shopId, itemId);

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
			var shoppingItem = await interparkDAO.update(shopItem[i].shopId, 
				shopItem[i].itemId, shopItem[i]);
		}
		res.send({isSuccess: true, result: shoppingItem});
	} catch (e) {
		console.log(e);
		res.send({isSuccess: false, message: e.message});
	}
})

//delete
const delShoppingItem = wrap(async function (req, res) {
	const shopItems = req.swagger.params.shoppingItemRequest.value;
	console.log(shopItems[0]["itemId"]);
	console.log(shopItems[0]["shopId"]);
	try {
		for(var i=0; i<shopItems.length; i++){
			const result = await interparkDAO.deleteOne(shopItems[i]["itemId"]);
			console.log(result.ok);
		}
		if(result.ok == 1)
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