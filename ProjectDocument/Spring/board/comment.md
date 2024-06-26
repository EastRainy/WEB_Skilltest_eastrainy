

## 게시판 댓글기능 구현

-----------

#### 구성사항

 - 댓글 밋 해당 댓글에 대한 답변 댓글까지 구현(2중 구조)
 - 가능하다면 모듈화하여 개발 후 게시판 페이지에 적용

게시글을 먼저 로드한 후 api를 통해 댓글을 로드하는 형식으로 구현  
(댓글 기능 분리, 댓글이 로드되지 않아도 페이지는 로드 될 수 있도록)


---------------------
### 구현기능

 - 해당 게시글에 달린 댓글의 총 갯수 전달 API (제목에 댓글 수 표시 등의 사용처)
 - 해당 게시글에 댓글 및 답변 댓글 내용 전달 API (화면 표시)
 - 해당 게시글에 댓글 작성 기능 API
 - 해당 게시글의 댓글 중 선택된 댓글에 답글 작성 기능 API
 - 댓글 및 답변 댓글에 대한 수정 API
 - 댓글 및 답변 댓글에 대한 삭제 API

---------------------


### 필요 속성 및 데이터베이스 구조

댓글 시스템을 구현하는데 필요할 것으로 생각되는 속성 및 그에 따른 데이터베이스 구성

board_comment_table에 관하여,  

 - 댓글 ID(Primary key) : 각 댓글을 관리하기 위한 번호로써 PK로 이용
 - 게시글 ID(Foreign key) : 해당 댓글이 포함되어 있는 게시글의 id로 FK로 이용
 - 작성자 ID(Foreign key) : 해당 게시글을 작성한 사람의 id
 - 소유 ID : 해당 댓글을 소유하고 있는 ID로 게시글이 종속 id라면 게시글의 댓글, 댓글 ID라면 댓글의 대댓글로 이용
 - 코멘트 내용 : 해당 코멘트의 실제 내용
 - 작성시간 : 해당 코멘트가 생성된 시간

| 필드 명            | 데이터타입     | 기본값   | 특이사항                                     |
|-----------------|-----------|-------|------------------------------------------|
| comment_id      | Serial    |       | Primary Key, GENERATE ALWAYS AS IDENTITY |
| board_id        | Integer   |       | Foreign Key, NOT NULL , CASCADE          |
| user_id         | Integer   |       | Foreign Key, NOT NULL                    |
| master_code     | Varchar   |       | NOT NULL                                 |
| master_id       | Integer   |       | NOT NULL                                 |
| comment_content | Varchar   |       | NOT NULL                                 |
| created_time    | timestamp | now() | NOT NULL                                 |

이때 번호만으로 관리되는 게시글 id와 구분하기 위해 master_code 에 댓글은 C, 게시글은 B로 구분하여 표현 예정  

이때 board_id에 대해서는 삭제 시 CASCADE 옵션 지정 - 해당 게시글이 삭제되면 댓글을 저장할 필요가 없으므로 같이 삭제  
단 master 가 댓글일 경우에는 따로 같이 삭제 필요 x - 삭제된 댓글은 삭제된 댓글이라는 표시

---------------------------

### 컨트롤러 구성

각 구현 기능별로 URL 지정하여 제어


--------------------------

### 기능 구현

#### - 해당 게시글의 총 갯수 전달
DB를 통해 해당 게시글에 종속된 댓글 갯수를 조회하여 전달
```postgresql
/*DB 조회 예시, #{board_id}가 조회할 게시글의 id 입*/
SELECT count(*) FROM board_coment_table
WEHER board_id = #{board_id} 
```
조회한 해당 갯수를 컨트롤러를 통해 요청 API에 전송

#### - 해당 게시글의 댓글 및 답변 댓글을 조회하여 전송 API

해당 게시글에 작성된 댓글 및 답변 댓글을 조회하여 전송하는 기능  
삭제된 댓글에 달린 답글에 대한 처리 포함

#### - 해당 게시글에 댓글 작성 API

해당 게시글에 신규 댓글 작성 기능  
이때 master_code 는 B, master_id는 board_id와 동일한 게시글의 아이디
```postgresql
INSERT INTO board_comment_table(
board_id, user_id, master_code, master_id, comment_content)
VALUES (
#{board_id} ,#{user_id}, #{master_code}, #{master_id}, #{comment_content}
)
```

#### 해당 게시글에 댓글 답글 작성 기능

신규 댓글 작성 기능과 동일하지만 master_code가 C로, master_id가 답글이 달린 댓글의 id가 지정



#### 수정, 삭제 API

각각 댓글에 대한 수정 및 삭제 기능  
수정 및 삭제 기능의 경우 해당 댓글을 작성한 사람과 어드민만이 기능에 접근할 수 있도록 권한 관리

해당 작성된 댓글 표시시 권한 있는 인원에게만 작은 글씨로 수정, 삭제 버튼이 나올 수 있도록 개발 


