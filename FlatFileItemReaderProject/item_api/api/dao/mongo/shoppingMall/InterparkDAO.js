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

	selectOne(id) {
		return this.dbResult(
			this.collection.findOne(
				{"id" : id}
			)
		);
	}

	update(id, itemData) {
		return this.dbResult(
			this.collection.update(
				{"id": id},
				itemData
			)
		);
	}

	deleteOne(itemId) {
		return this.dbResult(
			this.collection.deleteOne(
				{"id": itemId}
			)
		);
	}
}

const interparkDAO = new InterparkDAO();
export default interparkDAO;