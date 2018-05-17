'use strict';
/* 
'use strict'는 필요하지 않지만 프로그램 흐름에서 구문 오류를 실제 오류로 바꾸는 데 유용합니다.
 https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
*/

/* 
모듈을 사용하면 JavaScript 파일을 응용 프로그램으로 가져올 수 있습니다. 모듈 가져 오기
모듈에 대한 참조를 제공하는 'require'문을 사용합니다.

응용 프로그램이 의존하는 모듈을 프로젝트 루트의 package.json에 나열하는 것이 좋습니다.
 */

var util = require('util');

/* 
모듈을 '요구'하면 해당 모듈이 내 보낸 것을 참조 할 수 있습니다. 이들은 module.exports에 정의되어 있습니다.

a127에있는 컨트롤러의 경우, Swagger 문서에서 참조하는 함수를 이름으로 내 보내야합니다.

어느 한 쪽:
 - 해당 작업의 HTTP 동사 (get, put, post, delete 등)
 - 또는 Swagger 문서의 작업과 관련된 작업 ID

 시동기 / 스켈레톤 프로젝트에서 '/hello' 경로의 'get'연산에는 'hello'라는 operationId가 있습니다.
 우리는 이 모듈의 내보내기에서 'hello'가 'hello'라는 함수에 매핑된다는 것을 지정합니다.
*/
module.exports = {
  hello: hello
};

/*
연산에 사용되는 a127 컨트롤러의 함수에는 두 개의 매개 변수가 필요합니다.

  Param 1 : 요청 객체에 대한 핸들
  Param 2 : 응답 객체에 대한 핸들
*/
function hello(req, res) {
  // Swagger 문서에 정의 된 변수는 req.swagger.params. {parameter_name}을 사용하여 참조 할 수 있습니다.
  var name = req.swagger.params.name.value || 'stranger';
  var hello = util.format('Hello, %s!', name);

  // 이것은 단일 문자열 인 JSON 응답을 돌려 보낸다.
  res.json(hello);
}