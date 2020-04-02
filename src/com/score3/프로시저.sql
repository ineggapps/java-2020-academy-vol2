--저장 프로시저 작성하기

--추가 프로시저
CREATE OR REPLACE PROCEDURE insertScore
(
    pHak IN score.hak%TYPE,
    pName IN score.name%TYPE,
    pBirth IN score.birth%TYPE,
    pkor IN score.kor%TYPE,
    pEng IN score.eng%TYPE,
    pMat IN score.mat%TYPE
)
IS
BEGIN
    INSERT INTO score(hak, name, birth, kor, eng, mat)
    VALUES (pHak, pName, pBirth, pKor, pEng, pMat);
    COMMIT;
END
;
/


EXEC insertScore('9999','나나나','2000-10-10',80,90,70);
select * from score;

--수정 프로시저

CREATE OR REPLACE PROCEDURE updateScore
(
    pHak IN score.hak%TYPE,
    pName IN score.name%TYPE,
    pBirth IN score.birth%TYPE,
    pkor IN score.kor%TYPE,
    pEng IN score.eng%TYPE,
    pMat IN score.mat%TYPE
)
IS
BEGIN
    UPDATE score SET name=pName, birth=pBirth, kor=pKor, eng=pEng, mat=pMat
    WHERE hak=pHak;
    IF SQL%NOTFOUND THEN --NOTFOUND는 COMMIT 전에 해야 한다.
        RAISE_APPLICATION_ERROR(-20100, '등록된 자료가 아닙니다');
    END IF;
    COMMIT;
END;
/

EXEC updateScore('9999','다다다','2000-10-11',90,80,80);
select * from score;

--삭제 프로시저
CREATE OR REPLACE PROCEDURE deleteScore
(
    pHak IN score.hak%TYPE
)
IS
BEGIN
    DELETE FROM score WHERE hak=pHak;
    IF SQL%NOTFOUND THEN --NOTFOUND는 COMMIT 전에 해야 한다.
        RAISE_APPLICATION_ERROR(-20100, '등록된 자료가 아닙니다');
    END IF;
    COMMIT;
END
;
/

EXEC deleteScore('9999');
select * from score;


--전체 리스트 프로시저
CREATE OR REPLACE PROCEDURE listScore
(
    pResult OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN pResult FOR
        SELECT hak, name, birth, kor, eng, mat,
            (kor+eng+mat) tot, (kor+eng+mat)/3 ave,
            RANK() OVER(ORDER BY (kor+eng+mat)/3 DESC) rank
        FROM score;
END
;
/

--이름 검색 프로시저
CREATE OR REPLACE PROCEDURE findByNameScore
(
    pResult OUT SYS_REFCURSOR,
    pName IN VARCHAR2
)
IS
BEGIN
    OPEN pResult FOR
        SELECT hak, name, birth, kor, eng, mat,
            (kor+eng+mat) tot, (kor+eng+mat)/3 ave
        FROM score
        WHERE INSTR(name,pName)>=1;
END
;
/

--학번 검색 프로시저
CREATE OR REPLACE PROCEDURE findByHakScore
(
    pResult OUT SYS_REFCURSOR,
    pHak IN VARCHAR2
)
IS
BEGIN
    OPEN pResult FOR
        SELECT hak, name, birth, kor, eng, mat,
            (kor+eng+mat) tot, (kor+eng+mat)/3 ave
        FROM score
        WHERE hak=pHak;
END
;
/

--학과목별 전체 평균 프로시저
CREATE OR REPLACE PROCEDURE averageScore
(
    pKor OUT NUMBER,
    pEng OUT NUMBER,
    pMat OUT NUMBER
)
IS
BEGIN
    SELECT nvl(AVG(kor), 0), nvl(AVG(eng),0), nvl(AVG(mat),0)
    INTO pKor, pEng, pMat
    FROM score;
END
;
/


select * from USER_PROCEDURES;
