show tables;

create table user (
  idx  int not null auto_increment primary key,
  mid  varchar(20) not null,
  name varchar(20) not null,
  age  int  default 20,
  address varchar(15) default '서울',
  unique key(mid)
);
desc user;
drop table user;

insert into user values (default, 'admin', '관리자', 22, '청주');
insert into user values (default, 'hkd1234', '홍길동', default, default);
insert into user values (default, 'kms1234', '김말숙', 29, '제주');
insert into user values (default, 'lkj1234', '이기자', 42, '광주');
insert into user values (default, 'ohn1234', '오하늘', 32, '청주');

select * from user;

create table user2 (
	mid  varchar(4) not null,
	job  varchar(10),
	foreign key (mid) references user(mid)
);
drop table user2;
desc user2;
