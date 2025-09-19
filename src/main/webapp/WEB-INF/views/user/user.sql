show tables;

create table user (
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(20) not null,
	age int default 20,
	address varchar(15) default '서울'
);

desc user;

insert into user values (default, 'admin', '관리자', 22, '청주');
insert into user values (default, 'hkd1234', '홍길동', default, default);
insert into user values (default, 'kms1234', '김말숙', 29, '제주');
insert into user values (default, 'ikj1234', '이기자', 42, '광주');
insert into user values (default, 'ohn1234', '오하늘', 32, '청주');

select * from user;