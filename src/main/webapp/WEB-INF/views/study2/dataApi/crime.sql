show tables;

create table crime (
	idx int not null auto_increment primary key,
	year int not null,							/* 발생년도 */
	police varchar(20) not null,		/* 경찰서명 */
	robbery int,										/* 강도 건수 */
	theft int,											/* 절도 건수 */
	murder int,											/* 살인 건수 */
	violence int										/* 폭력 건수 */
);

desc crime;

select * from crime order by year, police;

select year,
	sum(robbery) as totRobbery,sum(murder) as totMurder,sum(theft) as totTheft,sum(violence) as totViolence 
	avg(robbery) as totRobbery,avg(murder) as totMurder,avg(theft) as totTheft,avg(violence) as totViolence 
	from crime
	where year=2024 and police like '서울%'
	order by year, police;

