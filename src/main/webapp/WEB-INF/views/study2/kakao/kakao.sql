show tables;

create table kakaoAddress (
  idx       int not null auto_increment primary key,
  address   varchar(50) not null,			/* 지점명 */
  latitude  double not null,					/* 위도 */
  longitude double not null 					/* 경도 */
);

desc kakaoAddress;
