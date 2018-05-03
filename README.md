# 쇼핑 상품 정보 수신

### 주제 선정 배경
쇼핑몰에서 제공하는 상품정보 EP (Engine Page)를 주기적으로 수집하여 변경된 정보를
서비스에 반영 한다.

### 요구사항(필수)
* 쇼핑몰 EP 다운로드 및 저장
* 저장된 이전 상품정보와 비교하여 신상, 변경, 품절 정보를 분류한다.
* 변경된 상품 정보를 찾아낸다.
* No Sql 저장소 활용

### 요구사항(선택)
* Reactive programming(rxjava, spring5, jdk9...) <br>
* 대용량 EP 파일 다운로드 및 데이터 비교

### 기타 사항
* EP (Engine Page) 란? 
  - 상품 정보를 등록/업데이트 하기 위해 고유한 포맷으로 작성된 상품정보 파일<br>
* EP 가이드 
  - http://join.shopping.naver.com/misc/download/ep_guide.nhn
