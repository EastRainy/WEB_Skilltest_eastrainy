

유저 로그인 테이블
---
---
- DB : postgresql
- 테이블 명 : user_table


| 필드 명            | 데이터타입     | 기본값   | 특이사항                                     |
|-----------------|-----------|-------|------------------------------------------|
| u_id            | integer   |       | PRIMARY_KEY, GENERATE ALWAYS AS IDENTITY |
| user_name       | varchar   |       | UNIQUE, NOT NULL                         |
| user_password   | varchar   |       | NOT NULL                                 |
| user_salt       | varchar   |       | NOT NULL                                 |
| user_createdate | timestamp | now() | NOT NULL                                 |
| user_lastlogin  | timestamp |       |                                          |


유저 계정 정보를 저장하기 위한 테이블

오직 로그인 관련 정보만 저장되는 테이블로 유저의 개인 데이터 항목은 저장 x 예정

개인정보항목은 privdata 테이블 이용할 예정

---

테이블 생성 SQL

```postgresql
CREATE TABLE usertable (
    u_id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_name varchar(40) UNIQUE NOT NULL,
    user_password varchar(250) NOT NULL,
    user_salt varchar(100) NOT NULL,
    user_createdate timestamp NOT NULL DEFAULT now(),
    user_lastlogin timestamp
)
```




---

테이블 테스트 데이터 삽입 SQL

```postgresql
INSERT INTO usertable(user_name, user_password, user_salt)
VALUES ('testuser', 'testpassword', 'testsalt')
```