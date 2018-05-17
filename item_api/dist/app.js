'use strict';

var _swaggerExpressMw = require('swagger-express-mw');

var _swaggerExpressMw2 = _interopRequireDefault(_swaggerExpressMw);

var _express = require('express');

var _express2 = _interopRequireDefault(_express);

var _bodyParser = require('body-parser');

var _bodyParser2 = _interopRequireDefault(_bodyParser);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var app = (0, _express2.default)();

// app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({extended: false}));

module.exports = app; // for testing

var config = {
  appRoot: __dirname // required config
};

var SwaggerUi = require('swagger-tools/middleware/swagger-ui');

_swaggerExpressMw2.default.create(config, function (err, swaggerExpress) {
  if (err) {
    throw err;
  }

  // add swagger-ui (/docs)
  app.use(SwaggerUi(swaggerExpress.runner.swagger));

  // install middleware
  swaggerExpress.register(app);

  var port = process.env.PORT || 10010;
  app.listen(port);

  if (swaggerExpress.runner.swagger.paths['/hello']) {
    console.log('try this:\ncurl http://127.0.0.1:' + port + '/hello?name=Scott');
  } else if (swaggerExpress.runner.swagger.paths['/test']) {
    console.log('try this:\ncurl http://localhost:' + port + '/test?name=Park');
  }
});

var env = process.env.NODE_ENV || 'local';
console.log("execution environment", env);