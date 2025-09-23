show tables;

/* 리뷰 테이블 */
create table review(
  idx   int not null auto_increment,  /* 리뷰 고유번호 */
  part  varchar(20) not null,					/* 분야(게시판:board, 자료실:pds....) */
  partIdx  int not null,							/* 해당 분야의 고유번호 */
  mid			 varchar(20) not null,			/* 리뷰 올린이 */
  nickName varchar(20) not null,			/* 리뷰 올린이 닉네임 */
  star     int not null default 0,		/* 별점 부여 점수 */
  content	 text,											/* 리뷰 내용 */
  rDate		 datetime default now(),		/* 리뷰 등록일자 */
  primary key(idx),
  foreign key(mid) references member(mid)
);


/* 리뷰에 댓글 달기 */
create table reviewReply(
  replyIdx  int not null auto_increment,/* 댓글의 고유번호 */
  reviewIdx	int not null,								/* 원본글(부모글:리뷰)의 고유번호(외래키로 설정) */
  replyMid			varchar(20) not null,		/* 댓글 올린이의 아이디 */
  replyNickName	varchar(20) not null,		/* 댓글 올린이의 닉네임 */
  replyContent	text not null,					/* 댓글 내용 */
  replyRDate		datetime default now(),	/* 댓글 올린 날짜 */
  primary key(replyIdx),
  foreign key(reviewIdx) references review(idx),
  foreign key(replyMid) references member(mid)
);

/* drop table reviewReply; */

desc review;
desc reviewReply;

select * from review where part='pds' order by idx desc;

select * from reviewReply order by replyIdx;

select * from review v, reviewReply r where part='pds' and v.partIdx=8 and v.idx=r.reviewIdx;

select * from review v inner join reviewReply r on part='pds' and v.partIdx=8 and v.idx=r.reviewIdx;
select * from review v left join reviewReply r on part='pds' and v.partIdx=8 and v.idx=r.reviewIdx;
select * from (select * from review where partIdx=8) v left join reviewReply r 
on part='pds' and v.partIdx=8 and v.idx=r.reviewIdx;






