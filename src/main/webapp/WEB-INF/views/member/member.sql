show tables;

create table member (
  idx       int not null auto_increment,	/* 회원 고유번호 */
  mid       varchar(30) not null,					/* 회원 아이디(중복불허) */
  pwd       varchar(100) not null,				/* 회원 비번(sha256암호화) */
  nickName  varchar(20) not null,					/* 회원별명(중복불허/수정가능) - 무조건 공개 */
  name		  varchar(20) not null,					/* 회원성명 */
  gender    char(2)	not null default '남자',/* 회원 성별(라디오버튼)(남자/여자) */
  birthday  datetime default now(),				/* 회원 생일(date형식) */
  tel			  varchar(15),									/* 전화번호(콤보상자)(010-1234-5678) */
  address   varchar(100),									/* 주소(다음 API활용) - 우편번호/주소/상세주소/참조주소 */
  email		  varchar(60) not null,					/* 이메일(아이디/비밀번호 분실시에 이메일인증처리)-입력시 이메일형식 필수체크 */
  homePage  varchar(60),						/* 회원 홈페이지(블로그/인스타/페이스북) */
  job			  varchar(20),						/* 직업(콤보상자) */
  hobby		  varchar(100),						/* 취미-체크박스(취미가 2개이상이면 구분자는 '/' 처리 */
  photo		  varchar(100) default 'noimage.jpg',	/* 회원 사진(초기가입시에 사진이 없으면 'noimage.jpg'처리 */
  content   text,										/* 자기(회원) 소개 */
  userInfor char(3) default '공개',	/* 회원정보 '공개/비공개' */
  userDel   char(2)  default 'NO',	/* 회원 탈퇴신청여부(NO:현재 활동중, OK:탈퇴신청중)-탈퇴후1달간 같은아이디로 재가입 불가 */
  point		  int default 100,				/* 회원 누적포인트(가입포인트 100점,1회방문시 10포인트증가, 1일 최대방문 5회까지허용, 물건구매시 100원당 1포인트 증가처리) */
  level     int default 3,					/* 회원등급(0:관리자, 1:우수회원, 2:정회원, 3:준회원), 99:비회원, 999:탈퇴신청회원 */
  visitCnt  int default 0,					/* 총 방문횟수 */
  todayCnt  int default 0,					/* 오늘 방문한 횟수 */
  startDate datetime default now(),	/* 최초 가입일() */
  lastDate  datetime default now(),	/* 마지막 접속일/탈퇴일 */
  primary key (idx),
  unique(mid)
);
desc member;
drop table member;

insert into member (mid,pwd,nickName,name,email) values ('1','1','n1','a1','e1');
insert into member (mid,pwd,nickName,name,email) values ('2','1','n2','a2','e2');
insert into member (mid,pwd,nickName,name,email) values ('3','1','n3','a3','e3');
insert into member (mid,pwd,nickName,name,email) values ('4','1','n4','a4','e4');
insert into member (mid,pwd,nickName,name,email) values ('5','1','n5','a5','e5');
insert into member (mid,pwd,nickName,name,email) values ('6','1','n6','a6','e6');
insert into member (mid,pwd,nickName,name,email) values ('7','1','n7','a7','e7');
insert into member (mid,pwd,nickName,name,email) values ('8','1','n8','a8','e8');
insert into member (mid,pwd,nickName,name,email) values ('9','1','n9','a9','e9');
insert into member (mid,pwd,nickName,name,email) values ('10','1','n10','a10','e10');
insert into member (mid,pwd,nickName,name,email) values ('11','1','n11','a11','e11');
insert into member (mid,pwd,nickName,name,email) values ('12','1','n12','a12','e12');
insert into member (mid,pwd,nickName,name,email) values ('13','1','n13','a13','e13');
insert into member (mid,pwd,nickName,name,email) values ('14','1','n14','a14','e14');
insert into member (mid,pwd,nickName,name,email) values ('15','1','n15','a15','e15');
insert into member (mid,pwd,nickName,name,email) values ('16','1','n16','a16','e16');
insert into member (mid,pwd,nickName,name,email) values ('17','1','n17','a17','e17');
insert into member (mid,pwd,nickName,name,email) values ('18','1','n18','a18','e18');
insert into member (mid,pwd,nickName,name,email) values ('19','1','n19','a19','e19');
insert into member (mid,pwd,nickName,name,email) values ('20','1','n20','a20','e20');

select * from member;
