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

--1�ܰ�. JAVA JDBC����̹� ����
--NATIVE PROTOCOL PURE JAVA DRIVER ����� �̿��Ͽ� ����Ѵ�.
--2�ܰ�. ������ ȣ��
--3�ܰ�. ���� ���� �ᱣ���� �̿� 