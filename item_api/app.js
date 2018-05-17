'use strict';

import SwaggerExpress from 'swagger-express-mw';
import express from "express";
import bodyParser from "body-parser";

const app = express();

module.exports = app; 

var config = {
  appRoot: __dirname 
};

var SwaggerUi = require('swagger-tools/middleware/swagger-ui');

SwaggerExpress.create(config, function(err, swaggerExpress) {
  if (err) {
    throw err;
  }

  app.use(SwaggerUi(swaggerExpress.runner.swagger));
  app.use(bodyParser.json());
  // app.use(express.json({limit: '50mb'}));
  // app.use(express.urlencoded({limit: '50mb'}));

  swaggerExpress.register(app);

  var port = process.env.PORT || 10010;
  app.listen(port);

  if (swaggerExpress.runner.swagger.paths['/hello']) {
    console.log('try this:\ncurl http://127.0.0.1:' + port + '/hello?name=Scott');
  } else if (swaggerExpress.runner.swagger.paths['/test']) {
    console.log('try this:\ncurl http://localhost:' + port + '/test?name=Park');
  }
});

const env = process.env.NODE_ENV || 'local'
console.log("execution environment", env);