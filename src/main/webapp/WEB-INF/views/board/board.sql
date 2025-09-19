show tables;

create table board2 (
  idx	 int not null auto_increment,	/* 게시글의 고유번호 */
  mid  varchar(20) not null,				/* 게시판 올린이 아이디 */
  nickName varchar(20) not null,		/* 게시판 올린이 닉네임 */
  title varchar(100) not null,			/* 게시글 제목 */
  content text not null,						/* 게시글 내용 */
  hostIp  varchar(30) not null,			/* 게시글 올린 PC IP */
  openSw	char(2) default 'OK',			/* 게시글 공개여부(OK:공개, NO:비공개) */
  readNum int default 0,						/* 글 조회수 */
  wDate datetime default now(),			/* 글 올린 날짜 */
  good  int default 0,							/* 좋아요 클릭수 */
  complaint char(2) default 'NO',		/* 신고글(정상글:NO, 신고당한글:OK, 신고처리중:HI) */
  primary key(idx),
  foreign key(mid) references member(mid)
);
desc board2;
drop table board2;

insert into board2 values (default,'admin','관리맨','게시판 서비스를 시작합니다.','즐거운 게시판 생활 되세요','192.168.50.20',default,default,default,default,default);

select * from board2;

select *,timestampdiff(hour, wDate, now()) as hour_diff from board2 order by idx desc limit 0,10;

select now(), datediff(now(), wDate) from board2 order by idx desc;

select *,
  timestampdiff(hour, wDate, now()) as hour_diff,
  datediff(now(), wDate) as date_diff,
  (select count(*) from board2Reply where board2Idx = b.idx) as replyCnt
  from board2 b order by idx desc limit 0,10;

select * from board2 order by idx desc;

-- 이전글 / 다음글 처리(예: 현재글은 idx 12번이라고 가정한다)
select idx, title from board2 where idx < 12 order by idx desc limit 1;	/* 이전글 */
select idx, title from board2 where idx > 12 order by idx limit 1;	/* 다음글 */

select * from board2 where nickName like '%홍%';


/* -------------------댓 글 처 리---------------------- */

create table board2Reply (
  idx  int not null auto_increment,/* 댓글 고유번호 */
  board2Idx int not null,					/* 부모글(원본글)의 고유번호 */
  ref  int not null,							/* 확장(현재는 원본게시글의 고유번호지정처리) */
  re_step int not null,						/* 레벨에 따른 들여쓰기(계층) 부모댓글은 1, 대댓글의 경우는 부모re_step+1처리 */
  re_order int not null,					/* 댓글의 순서(부모댓글은 1, 대댓글은 부모댓글보다 큰 re_order은 모두 re_order+1처리후 자신은 부모re_order+1한다.) */
  mid varchar(20) not null,				/* 댓글 올린이의 아이디 */
  nickName varchar(20) not null,	/* 댓글 올린이의 닉네임 */
  wDate    datetime default now(),/* 댓글 올린 날짜 */
  hostIp   varchar(30) not null,	/* 댓글 올린 PC의 고유IP */
  content  text not null,					/* 댓글 내용 */
  primary key(idx),
  foreign key(board2Idx) references board2(idx)
  on update cascade
  on delete restrict
);
desc board2Reply;

insert into board2Reply values (default, 27, 27, 1, 1, 'hkd1234','홍장군',default,'192.168.50.20','댓글연습!!!!');
insert into board2Reply values (default, 27, 27, 1, 2, 'kms1234','독야청청',default,'192.168.50.19','수고하십니다.');

select * from board2Reply order by idx desc;
select * from board2Reply where board2Idx=27 order by idx desc;
select count(*) as replyCnt from board2Reply where board2Idx=27;
delete from board2 where idx = 14;
