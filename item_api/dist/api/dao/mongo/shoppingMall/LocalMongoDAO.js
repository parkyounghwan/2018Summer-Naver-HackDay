'use strict';

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }(); //mongodb 모듈에서 'MongoClient'만 뽑아온다.


var _mongodb = require("mongodb");

var _config = require("config");

var _config2 = _interopRequireDefault(_config);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

//config 폴더를 전체 import 시킨다.(mongodb 설정이 들어있는 json을 사용할 것이다.)

var mongoDB = _config2.default.get('mongoDB'); //config 폴더에 있는 "mongoDB" 키값을 가져온다.(chaining으로 필요한 값을 가져온다, 저장하는 객체랑 config 설정 정보 이름이 일치하는건 우연의 일치)

//클래스 생성, 몽고 데이터소스 느낌(?)

var LocalMongoDAO = function () {
	//생성자 생성, 이 클래스를 상속받아서 사용할 때 collection은 table 개념이니까, 그니까 변할 수 있으니까 파라미터로 받네
	function LocalMongoDAO(collectionName) {
		_classCallCheck(this, LocalMongoDAO);

		var url = "mongodb://" + mongoDB.shoppingItem.host + ":" + mongoDB.shoppingItem.port + "/" + mongoDB.shoppingItem.db;

		//replicaSet인가 뭔가를 지정해 줬는데 나는 아직 뭔지 조오또모르니까 스무스하고 과감하게 스킵 디스 레벨.

		this.db = null; //뭔 db를 선언도 안했는데 this 붙인 다음에 null을 멕여버린다.
		var self = this; //나에 나를 넣는다. 이걸 하기 전까지는 아직 완전한 내가 아니었나보다.

		//MongoClient가 물리적으로 연결하는 connection을 관리해주는게 이제 poolsize인데, default로 5개라 한다. 명시적으로 써준듯 하니까 바로 배껴버리자. 명.시.성
		_mongodb.MongoClient.connect(url, mongoDB.shoppingItem.options, function (err, db) {
			if (err) {
				return; //db연결하는 놈이니까 에러처리 해줘라. 연결 안되면 애매하잖아.
			}

			self.db = db; //이 색기 뭔가 했더니 그 몽고 디비 쿼리 날리때 'db.collection.~' 이거 할 때 그색기였다. 근성으로 읽고 내려왔으면 깨달음 얻고 간다.
			self.collection = self.db.collection(collectionName);
		});
	}

	//method를 하나 만드는데 뭔가 프라미스 하는 놈을 받는다.


	_createClass(LocalMongoDAO, [{
		key: "dbResult",
		value: function dbResult(dbPromise) {
			return new Promise(function (resolve, reject) {
				dbPromise.then(function (doc) {
					return resolve(doc);
				}).catch(function (err) {
					return reject(err);
				}); //일단 뭔소린지 하나도 모르겠다. 이단 넘어간다.
			});
		}
	}]);

	return LocalMongoDAO;
}();

// 이색길 써줘야 그 모듈로서 이제 세상에 진출할 모양이다. 이런건 검색할 시간도 아깝다. 느낌으로 알아채자.


exports.default = LocalMongoDAO;