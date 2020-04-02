CREATE TABLE member1(
    id VARCHAR2(30) PRIMARY KEY CONSTRAINT PK_MEMBER1_ID NOT NULL,
    pwd VARCHAR2(50) NOT NULL,
    name VARCHAR2(30) NOT NULL
);

CREATE TABLE member2(
    id VARCHAR2(30) CONSTRAINT PK_MEMBER2_ID PRIMARY KEY,
    birth DATE,
    email VARCHAR2(50),
    tel VARCHAR2(30),
    CONSTRAINT FK_MEMBER2_ID FOREIGN KEY (id) REFERENCES member1(id)
);

select * from member1
JOIN member2 USING(id);

delete from member2;
delete from member1;
commit;