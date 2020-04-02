-- ���� ���ν��� �ۼ��ϱ�
desc member1;
desc member2;
--MEMBER1
--�̸�   ��?       ����           
------ -------- ------------ 
--ID   NOT NULL VARCHAR2(30) 
--PWD  NOT NULL VARCHAR2(50) 
--NAME NOT NULL VARCHAR2(30) 

--MEMBER2
--�̸�    ��?       ����           
------- -------- ------------ 
--ID    NOT NULL VARCHAR2(30) 
--BIRTH          DATE         
--EMAIL          VARCHAR2(50) 
--TEL            VARCHAR2(30) 
select * from member1 join member2 on member1.id=member2.id;

--�߰� ���ν���
CREATE OR REPLACE PROCEDURE insertMember(
    pId IN member1.id%TYPE,
    pPwd IN member1.pwd%TYPE,
    pName IN member1.name%TYPE,
    pBirth IN member2.birth%TYPE,
    pEmail IN member2.email%TYPE,
    pTel IN member2.tel%TYPE
)
IS
BEGIN
    INSERT INTO member1(id, pwd, name)
    VALUES(pId, pPwd, pName);
    INSERT INTO member2(id, birth, email, tel)
    VALUES(pId, pBirth, pEmail, pTel);
    COMMIT;
END
;
/

EXEC insertMember('test010','1234','�׽�Ʈȫ�浿','2020-01-01','test@hong.gil','010-4321-4321');

--���� ���ν���
CREATE OR REPLACE PROCEDURE updateMember(
    pId IN member1.id%TYPE,
    pPwd IN member1.pwd%TYPE,
    pName IN member1.name%TYPE,
    pBirth IN member2.birth%TYPE,
    pEmail IN member2.email%TYPE,
    pTel IN member2.tel%TYPE
)
IS
BEGIN
    UPDATE member1 SET pwd=pPwd, name=pName WHERE id=pId;
    UPDATE member2 SET birth=pBirth, email=pEmail, tel=pTel WHERE id=pId;
    IF SQL%NOTFOUND THEN
        RAISE_APPLICATION_ERROR(-20100,'�����ϴ� ���� �ƴմϴ�';
    END IF;
    COMMIT;
END
;
/

EXEC updateMember('test010','4321','�ڱ浿','1999-09-09','park@gil.dong','019-9999-9999');

--���� ���ν���
CREATE OR REPLACE PROCEDURE deleteMember(
    pId IN member1.id%TYPE
)
IS
BEGIN
    DELETE FROM member2 WHERE id=pId;
    DELETE FROM member1 WHERE id=pId;
    IF SQL%NOTFOUND THEN
        RAISE_APPLICATION_ERROR(-20100,'�����ϴ� ���� �ƴմϴ�');
    END IF;
    COMMIT;
END
;
/

EXEC deleteMember('test010');

--��ü ����Ʈ ���ν���
CREATE OR REPLACE PROCEDURE listMember(
    pResult OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN pResult FOR 
        SELECT m1.id, pwd, name, birth, email, tel
        FROM member1 m1 JOIN member2 m2 ON m1.id=m2.id;
END;
/

--�̸� �˻� ���ν���
CREATE OR REPLACE PROCEDURE findByNameMember(
    pResult OUT SYS_REFCURSOR,
    pName IN VARCHAR2
)
IS
BEGIN
    OPEN pResult FOR 
        SELECT m1.id, pwd, name, birth, email, tel
        FROM member1 m1 JOIN member2 m2 ON m1.id=m2.id
        WHERE INSTR(name, pName)>=1;
END;
/

--���̵� �˻� ���ν���
CREATE OR REPLACE PROCEDURE findByIdMember(
    pResult OUT SYS_REFCURSOR,
    pId IN VARCHAR2
)
IS
BEGIN
    OPEN pResult FOR
        SELECT m1.id, pwd, name, birth, email, tel
        FROM member1 m1 JOIN member2 m2 ON m1.id=m2.id
        WHERE m1.id=pId;
END;
/