

유저 로그인 테이블
---
---
- DB : postgresql
- 테이블 명 : user_table


| 필드 명            | 데이터타입     | 기본값   | 특이사항                                    |
|-----------------|-----------|-------|-----------------------------------------|
| u_id            | integer   |       | PRIMARY_KEY, GENERATE ALWAYS AS IDENTITY |
| user_name       | varchar   |       | UNIQUE, NOT NULL                        |
| user_password   | varchar   |       | NOT NULL                                |
| user_salt       | varchar   |       |                                         |
| user_createdate | timestamp | now() | NOT NULL                                |
| user_lastlogin  | timestamp |       |                                         |


유저 계정 정보를 저장하기 위한 테이블

오직 로그인 관련 정보만 저장되는 테이블로 유저의 개인 데이터 항목은 저장 x 예정

개인정보항목은 user_private_data 테이블 이용할 예정

---

테이블 생성 SQL

```postgresql
CREATE TABLE IF NOT EXISTS public.user_table
(
    u_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    user_name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    user_password character varying(250) COLLATE pg_catalog."default" NOT NULL,
    user_salt character varying(100) COLLATE pg_catalog."default",
    user_createdate timestamp without time zone NOT NULL DEFAULT now(),
    user_lastlogin timestamp without time zone,
    CONSTRAINT user_table_pkey PRIMARY KEY (u_id),
    CONSTRAINT user_table_user_name_key UNIQUE (user_name)
)
```

---

테이블 테스트 데이터 삽입 SQL
user_table만
```postgresql
INSERT INTO user_table(user_name, user_password, user_salt)
VALUES ('testuser', 'testpassword', 'testsalt')
```


user_private_data 포함 생성
```postgresql
WITH new_user AS (
INSERT INTO user_table (user_name, user_password)
VALUES ('testuser', 'testpassword')
RETURNING u_id
)
INSERT INTO user_private_data (u_id)
SELECT u_id
FROM new_user;
```

