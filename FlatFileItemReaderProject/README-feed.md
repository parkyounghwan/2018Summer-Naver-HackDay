# feed - FlatFileItemReader 적용

## tools and library
- sts 3.8.4
- jdk 1.8
- spring-core 5.0.6 RELEASE
- spring-batch-core 4.0.1 RELEASE
- spring-batch-infrastructure 4.0.1 RELEASE
- spring-web 5.0.6 RELEASE
- commons-lang3 3.7
- httpclient 4.5.5
- httpcore 4.4.9

## comments
- 기존에 웹 어플리케이션으로 사용자의 입력을 받아 실행되는 구조에서, 자바 main 메소드에서 실행되는 구조로 바꿨습니다.
- EP 다운로드 및 FlatFileItemReader 로 파싱하는 과정 이외의 등록, 수정, 삭제 과정은 앞의 프로젝트와 동일 합니다.
