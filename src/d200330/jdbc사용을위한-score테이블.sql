CREATE TABLE score(
    hak VARCHAR2(30) PRIMARY KEY,
    name VARCHAR2(30) NOT NULL,
    birth DATE NOT NULL,
    kor NUMBER(3) NOT NULL,
    mat NUMBER(3) NOT NULL,
    eng NUMBER(3) NOT NULL
);

desc score;
SELECT * FROM score;
SELECT * FROM tab;
desc score;

--1단계. JAVA JDBC드라이버 연결
--NATIVE PROTOCOL PURE JAVA DRIVER 방식을 이용하여 사용한다.
--2단계. 쿼리문 호출
--3단계. 돌려 받은 결괏값을 이용 