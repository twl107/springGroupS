show tables;

create table schedule (
  idx   int not null auto_increment,	/* 고유번호 */
  mid   varchar(20) not null,					/* 사용자 아이디 */
  sDate datetime not null,						/* 일정 등록일자 */
  part  varchar(20) not null,					/* 모임, 업무, 학습, 여행, 기타... (공지사항:9) */
  content text not null,							/* 일정 상세 내역 */
  primary key(idx),
  foreign key(mid) references member(mid)
);

desc schedule;

insert into schedule values (default, 'hkd1234','2025-09-14', '학습', '프로젝트 설계서 제안..');
insert into schedule values (default, 'hkd1234','2025-09-24', '학습', 'DB설계서 완성하기');
insert into schedule values (default, 'hkd1234','2025-09-24', '학습', '영어단어 100개 외우기');
insert into schedule values (default, 'hkd1234','2025-09-24', '업무', '15시까지 하반기 계획서 제출하기');
insert into schedule values (default, 'hkd1234','2025-09-26', '학습', '프로젝트 계획서 제출');
insert into schedule values (default, 'kms1234','2025-09-29', '학습', '프로젝트 시작~~');
insert into schedule values (default, 'kms1234','2025-09-29', '업무', '업무일지 정리');
insert into schedule values (default, 'kms1234','2025-09-30', '기타', '등산장비 정리');
insert into schedule values (default, 'hkd1234','2025-09-30', '학습', '프로젝트 중간점검');
insert into schedule values (default, 'hkd1234','2025-10-03', '모임', '가족회의, 장소:집, 시간:19시');
insert into schedule values (default, 'hkd1234','2025-10-06', '학습', '프로젝트 발표, 장소:그린컴퓨터 402호, 시간:10시~18시');
insert into schedule values (default, 'hkd1234','2025-10-13', '모임', '초등 동창회, 장소:충북대4거리 커피숍, 시간:13시');
insert into schedule values (default, 'kms1234','2025-10-13', '기타', '집안 청소하기');
insert into schedule values (default, 'kms1234','2025-10-13', '학습', '그린컴퓨터수료식, 시간:12시');
insert into schedule values (default, 'hkd1234','2025-10-19', '기타', '온라인 교육서 제출');
insert into schedule values (default, 'hkd1234','2025-10-21', '모임', '동창회, 장소:사창사거리, 시간:18시');
insert into schedule values (default, 'kms1234','2025-10-23', '업무', '년간일정 회의');
insert into schedule values (default, 'hkd1234','2025-10-26', '학습', 'Java반 9시 입교식, 장소:402호, 시간:9시');

select * from schedule;
select * from schedule where mid='hkd1234' order by sDate;
select * from schedule where mid='hkd1234' and sDate='2025-9' order by sDate; /* X */
select * from schedule where mid='hkd1234' and sDate='2025-09' order by sDate; /* X */
select * from schedule where mid='hkd1234' and sDate='2025-09-24' order by sDate; /* O */
select * from schedule where mid='hkd1234' and sDate=date_format('2025-09', '%Y-%m') order by sDate; /* △ */
select * from schedule where mid='hkd1234' and substring(sDate, 1, 7)='2025-09' order by sDate; /* O : substring(변수,시작주소,갯수) */
select * from schedule where mid='hkd1234' and substring(sDate, 1, 7)='2025-09' group by part order by sDate;
select *, count(part) as partCnt from schedule where mid='hkd1234' and substring(sDate, 1, 7)='2025-09' group by part order by sDate;
select *, count(part) as partCnt from schedule where mid='hkd1234' and substring(sDate, 1, 7)='2025-09' group by sDate,part order by sDate;
select idx,mid,sDate,part,content, count(part) as partCnt from schedule where mid='hkd1234' and substring(sDate, 1, 7)='2025-09' group by sDate,part order by sDate;


