'use strict';

import LocalMongoDAO from "./LocalMongoDAO"; 	
import {ObjectID} from "mongodb"; 			

class InterparkDAO extends LocalMongoDAO {
	constructor() {
		super("practice")
	}

	insertMany(document) {
		return this.dbResult(
			this.collection.insertMany(document)
		);
	}

	selectOne(shopId, itemId) {
		return this.dbResult(
			this.collection.findOne(
				{"shopId" : shopId, "itemId" : itemId}
			)
		);
	}

	update(shopId, itemId, itemData) {
		return this.dbResult(
			this.collection.update(
				{"shopId": shopId, "itemId": itemId},
				itemData
			)
		);
	}

	deleteOne(itemId) {
		return this.dbResult(
			this.collection.deleteOne(
				{"itemId": itemId}
			)
		);
	}
}

const interparkDAO = new InterparkDAO();
export default interparkDAO;