'use strict';

//본게임으로 넘어간다. 간략하게 여기서 해야하는 활동을 정리해본다.
//일단, 데이터소스(그니까 mongo 연결은 저쪽 클래스에서 했고, 여기서는 이제 실질적인 쿼리를 날리는 거다. 가자.)

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _LocalMongoDAO2 = require("./LocalMongoDAO");

var _LocalMongoDAO3 = _interopRequireDefault(_LocalMongoDAO2);

var _mongodb = require("mongodb");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } //상대경로로 바로 클라스 가져와보리기.


//아니 저 monogdb 모듈에 ObjectID 가 있는건 어캐 아는거지.

// shopingMallDAO로 바꾸고, 만약에 쇼핑몰 여러개인 경우 생각하고, 쇼핑몰 많아지면 쇼핑몰 수 만큼 만들 수 없다. Id로 구
var InterparkDAO = function (_LocalMongoDAO) {
	_inherits(InterparkDAO, _LocalMongoDAO);

	function InterparkDAO() {
		_classCallCheck(this, InterparkDAO);

		return _possibleConstructorReturn(this, (InterparkDAO.__proto__ || Object.getPrototypeOf(InterparkDAO)).call(this, "practice")); //상속 계층에서 뽑아오는거
	}

	_createClass(InterparkDAO, [{
		key: "insert",
		value: function insert(document) {
			return this.dbResult(this.collection.insertMany(document) //mongo query
			);
		}

		//select AND

	}, {
		key: "selectAnd",
		value: function selectAnd() {
			return this.dbResult(this.collection.find({ $and: [{ 'shopId': '2018' }, { 'shopName': '인터파크' }, { 'itemId': '5381842452' }]
			}));
		}
	}, {
		key: "select",
		value: function select() {
			return this.dbResult(this.collection.find({ $enable: true }));
		}
	}]);

	return InterparkDAO;
}(_LocalMongoDAO3.default);

var interparkDAO = new InterparkDAO();
exports.default = interparkDAO;