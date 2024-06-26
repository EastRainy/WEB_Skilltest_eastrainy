

# Mypage / MypageUpdate

-------------------

### 요구사항

 - 유저의 계정 관련 개인 정보 수정을 위한 페이지 구현
 - 비밀번호 변경, 휴대폰 번호 변경, 주소 변경 등 세부사항 변경 지원
 - 회원가입시와 동일한 검증 유지


## Mypage

 - 수정기능 없이 유저가 처음 조회했을 때 자신의 정보를 확인 할 수 있는 페이지
 - 조회시 DB에 저장된 유저의 세부 정보를 조회하여 형식에 맞게 초기화 한 후 화면에 표시
 - 구조상으로는 MypageUpdate와 동일하게 설정하여 수정 시 UX 이어지도록 구성

페이지 구성 표시 특성상 input 태그를 이용한 표시 등 업데이트 페이지와 유사하게 구성되었으나, js 함수 등 업데이트 페이지와는 
분리하여 전송기능 없이 단순 조회기능만 사용할 수 있게 조정


## MypageUpdate

 - 유저 데이터 조회 및 수정 요청을 위한 페이지 
 - Frontend 검증 포함 구현
 - fetchAPI를 통해 서버에 수정사항 전송 및 응답 대응
 - 접근 시 기존 정보로 초기화, 이후 전송 시 수정한 항목들을 서버로 전송하여 업데이트

MypageUpdate 의 업데이트 기능 중 비밀번호 변경 기능의 경우 해당 기능 중요성으로 인해 비밀번호 변경 버튼을
 통해 접근 및 별도의 Modal 을 이용한 변경으로 타 변경사항과 분리하여 변경할 수 있도록 적용함.  
  
회원가입 페이지와 동일한 조건의 검증으로 변경사항 검증(계정 정보 통일성을 위함)