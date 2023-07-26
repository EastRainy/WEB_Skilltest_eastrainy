
게시판 테이블 
---
---
 - DB : postgresql
 - 테이블 명 : board_table

| 필드 명      | 데이터타입         | 기본값   | 특이사항                                     |
|-----------|---------------|-------|------------------------------------------|
| b_id      | integer       |       | PRIMARY_KEY, GENERATE ALWAYS AS IDENTITY |
| writer    | varchar(20)   |       | NOT NULL, (FOREIGN KEY - userid)         |
| title     | varchar(50)   |       | NOT NULL                                 |
| c_date    | timestamp     | now() | NOT NULL                                 |
| m_date    | timestamp     | now() | NOT NULL                                 |
| content   | varchar(2000) |       |                                          |
| viewcount | integer       | 0     | NOT NULL                                 |



게시판 테이블 명세서

게시판 기능 구현을 위한 테이블로 추후 로그인 지원시 해당 아이디와 연동하여 writer를 id키로 받아올 예정

---

해당 테이블을 만들기 위한 postgre SQL

```postgresql
CREATE TABLE boardtable (
	b_id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	writer varchar(20) NOT NULL,
	title varchar(50) NOT NULL,
	c_date timestamp NOT NULL DEFAULT now(),
	m_date timestamp NOT NULL DEFAULT now(),
	content varchar(2000),
	viewcount integer DEFAULT 0
)
```
---

게시판 CRUD SQL

Primary key b_id 이용

- INSERT
```postgresql
INSERT INTO boardtable(
    writer, title, content
)
VALUES(
    #{writer}, #{title}, #{content}
)
```
- UPDATE
```postgresql
UPDATE boardtable
SET
    writer = #{writer},
    title = #{title},
    content = #{content},
    m_data = now()
WHERE 
    b_id = #{id}
```
- DELETE
```postresql
DELETE FROM boardtable
WHERE b_id = #{id} 
```

---


개발시 테스트를 위한 데이터 기본 입력 데이터 SQL




```postgresql
INSERT INTO boardtable(
		writer,
		title,
		content
	) 
    VALUES(
	    '테스트1',
	    '첫번째 게시물',
	    '테스트용 첫번째 게시물의 내용입니다.'
)
```

```postgresql
INSERT INTO boardtable(
		writer,
		title,
		content
	) 
    VALUES(
	    '테스트2',
	    '게시물 2번째 22222222222',
	    '테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다.
	     테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다.
	     테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다. 테스트용 두번째 게시물의 내용입니다.'
)
```
```postgresql
INSERT INTO boardtable(
		writer,
		title
	) 
    VALUES(
	    '테스트333',
	    'The Empty'
)
```
```postgresql
INSERT INTO boardtable(
		writer,
		title,
		content
	) 
    VALUES(
	    'The Modifier',
	    'Modified_board',
	    '이 게시물은 수정되지 않았었습니다.'
)

-> 해당 실행 이후 b_id 값을 확인 후

UPDATE boardtable SET
    m_date = now(),
    content = '이 게시물은 진짜진짜 수정되었습니다.'
WHERE b_id = 4

```



