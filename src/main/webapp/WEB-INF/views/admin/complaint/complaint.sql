show tables;

/*
  신고 처리 :
  complaintSw : 
    H(감추기-board테이블의 complaint필드값을 'HI', complaint테이블에서 progress필드값을 처리중('H'))
    S(보이기-신고해제(?)해제시는 board테이블의 complaint필드값을 'NO')
    D(삭제하기-board테이블의 해당글을 삭제처리, complaint테이블에서 progress필드값을 처리중('D')
*/

create table complaint (
  idx  int not null auto_increment,	/* 신고테이블의 고유번호 */
  part varchar(15) not null,				/* 신고분류(게시판:board, 자료실:pds, 방명록:guest, 포토갤러리:photo~~) */
  partIdx int not null,							/* 신고 분류항목 글의 고유번호 */
  cpMid varchar(20) not null,				/* 신고자 아이디 */
  cpContent text not null,					/* 신고 사유 */
  cpDate datetime default now(),		/* 신고한 날짜 */
  progress varchar(10) default '신고접수', /* 진행상황 : '신고접수/처리중/처리완료/문제여부:없음(내용유지-보이기)/가리기/삭제하기' */
  primary key(idx),
  foreign key(cpMid) references member(mid)
);
desc complaint;

