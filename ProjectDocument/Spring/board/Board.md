# 게시판

---

## 요구사항

 - 간단한 문의 응대를 위한 게시판 구현

 1. 게시글 작성, 업데이트, 조회, 삭제 가능할것
 2. 게시글 작성자는 로그인하여 해당 게시물을 작성한 유저와 연동
 3. 댓글 기능 구현

일차적으로 간단한 게시판 구현 후 DB와 연결 확인하여 1번 구현 완료

현재 로그인 기능 개발과 함께 2번 기능 구현중

로그인 기능 개발 완료되면 3 댓글 기능 구현 예정.


---
## 위치

해당 기능의 위치는 프로젝트 디렉터리의 
 
 - ./board/*

을 이용하며, 추가적으로 필요한 statics 파일을 이용한다.

- ./resources/templates/html/board/*
- ./resources/css/board/*
- ./resources/js/board/*

---

각 게시판 화면은 main(목록), new(신규 생성), view(조회), modifying(수정)으로 이루어져 있으며,

각각의 기능들은 BoardController에 의해 접근 관리 및 서비스와 연결된다.

해당 기능을 위한 서비스는 BoardService, PageServiec가 있으며 BoardService는 Dao를 이용하여

DB와 연결, 게시판 CURD작업을 수행한다.

PageService는 게시판 페이징 서비스를 위한 서비스로 따로 분리하여 페이지 검증, 계산, 리스트 전달
등의 역할을 한다.


---

## BoardCondtroller

컨트롤러 메서드 요약

  - 서비스 연결 
  - 잘못된 게시판 페이지 번호 접근시 에러처리
  - 해당 ID의 페이지 조회
  - 신규 게시글 생성 페이지 접근/응답
  - 기존 게시글 수정 페이지 접근/응답
  - 기존 페이지 삭제 응답

컨트롤러 - 추가 기능 필요시 Service에 구현 후 연결, 컨트롤러에서는 서버 작업 하지 않고 페이지
연결

---

## Service

DAO와 연결하여 데이터를 주고받고 가공하여 Controller에 제공


### boardService
 - 게시판 게시글 CURD 작업 서비스

### pageService
 - 게시판 메인페이지의 페이징 처리 서비스 
 - 총 페이지 수 계산, 현제 메인범위 검증, 전시 게시물 범위 등 작업 진행


---

## DTO

게시글에 필요한 속성들의 데이터를 전달하기 위한 가진 객체

 - 게시글 id board_id
 - 글쓴이 writer
 - 제목 title
 - 수정된 시간 modified_time
 - 내용 content
 - 조회수 viewcount

---

## DAO, Mybatis

DAO - SQL 세션을 주입받아 mybatis의 BoardMapper와 대응하는 메서드

Mybatis - SQL xml을 Mapper형태로 구성하여 관리
