유저 개인정보 테이블
---

---
- DB : postgresql
- 테이블 명 : user_private_data


| 필드 명               | 데이터타입   | 기본값   | 특이사항                     |
|--------------------|---------|-------|--------------------------|
| u_id               | Integer |       | PRIMARY_KEY, FOREIGN_KEY |
| user_personname    | Varchar |       |                          |
| user_birthdate     | DATE    |       |                          |
| user_cellphone     | Varchar |       |                          |
| user_postnum       | Varchar |       |                          |
| user_address       | Varchar |       |                          |
| user_addressdetail | Varchar |       |                          |
| user_email         | Varchar |       |                          |



-------

유저 개인정보 항목을 저장하기 위한 테이블

로그인시 필요한 user_table과 분리하여 유저 개인 데이터만 저장하여 관리

해당항목
 
 - 유저 고유번호(기본 정보와 연결되는 외부키)
 - 이름
 - 생년월일
 - 전화번호
 - 우편번호(API get정보)
 - 주소(API get정보)
 - 상세주소(사용자 입력)

-----

### SQL

테이블 생성 SQL
```postgresql
CREATE TABLE IF NOT EXISTS public.user_private_data
(
    u_id integer NOT NULL,
    user_personname character varying(40) COLLATE pg_catalog."default",
    user_birthdate date,
    user_phone character varying(15) COLLATE pg_catalog."default",
    user_postnum character varying(10) COLLATE pg_catalog."default",
    user_address character varying(70) COLLATE pg_catalog."default",
    user_address_detail character varying(70) COLLATE pg_catalog."default",
    user_email character varying(70) COLLATE pg_catalog."default",
    CONSTRAINT pk_u_id PRIMARY KEY (u_id),
    CONSTRAINT "u_id_Foreign" FOREIGN KEY (u_id)
        REFERENCES public.user_table (u_id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE CASCADE
)
```


-------------------




