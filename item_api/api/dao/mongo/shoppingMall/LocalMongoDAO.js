'use strict';

import {MongoClient} from "mongodb";	
import config from "config";			

const mongoDB = config.get('mongoDB');	

class LocalMongoDAO {
	
	constructor(collectionName) {
		let url = `mongodb://${mongoDB.shoppingItem.host}:${mongoDB.shoppingItem.port}/${mongoDB.shoppingItem.db}`;

		this.db = null;		
		var self = this;	
		
		MongoClient.connect(url, mongoDB.shoppingItem.options,
			function (err, db) {
				if(err) {
					return;
				}

				self.db = db;
				self.collection = self.db.collection(collectionName);
			}
		);
	}

	dbResult(dbPromise) {
        return new Promise((resolve, reject) => {
            dbPromise.then(doc => resolve(doc)).catch((err) => reject(err));
        });
    }
}

export default LocalMongoDAO;