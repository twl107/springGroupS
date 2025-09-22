show tables;

create table pds2 (
  idx  int not null auto_increment,	/* 자료실 고유번호 */
  mid  varchar(20) not null,				/* 자료 올린이 아이디 */
  nickName varchar(20) not null,		/* 자료 올린이 닉네임 */
  fName		varchar(200) not null,		/* 업로드시의 파일이름 */
  fSName	varchar(200) not null,		/* 실제 서버에 저장되는 파일이름 */
  fSize		int not null,							/* 파일 총 사이즈 */
  part		varchar(20)	 not null,		/* 파일분류(학습/여행/음식/__/기타) */
  title   varchar(100) not null,		/* 업로드 되는 파일의 간단 제목 */
  content text,											/* 업로드 되는 파일의 상세 제목 */
  openSw	char(3)	default '공개',		/* 파일 공개여부(공개/비공개) */
  /* pwd     varchar(100),	*/						/* 비밀번호(SHA256) */
  hostIp	varchar(30) not null,			/* 파일 업로드한 PC IP */
  fDate		datetime default now(),		/* 파일 업로드한 날짜 */
  downNum	int default 0,						/* 다운 받은 횟수 */
  primary key(idx),
  foreign key(mid) references member(mid)
);

desc pds;


