-- BOARD TABLE
-- 번호,제목,작성자,내용,날자,조회수

create table "BOARD"(
    "NUM" number primary key,
    "TITLE" varchar2(50) not null,
    "WRITER" varchar2(50) not null,
    "CONTENT" varchar2(1000),
    "REGDATE" date,
    "CONT" number default 0
);

-- BOARD SEQUENCE
-- 1부터 시작 1씩 증가 99999까지
-- BOARD_SEQ.nextval(사용),BOARD_SEQ.currval(확인용)
create sequence "BOARD_SEQ"
start with 1
increment by 1
maxvalue 99999
nocache
nocycle
noorder;

-- 삽입
insert into "BOARD" ("NUM","TITLE","WRITER","CONTENT","REGDATE","CONT")
values ("BOARD_SEQ".nextval,'제목1','작성자1','내용1',sysdate,0);

-- 조희
select "NUM","TITLE","WRITER","CONTENT","REGDATE","CONT" from "BOARD";
select "NUM","TITLE","WRITER","CONTENT","REGDATE","CONT" from "BOARD" where "NUM"=?;

-- 수정
update "BOARD" set "TITLE"='제목수정',"CONTENT"='내용수정' where "NUM"=1;

-- 삭제
delete from "BOARD" where "NUM"=1;