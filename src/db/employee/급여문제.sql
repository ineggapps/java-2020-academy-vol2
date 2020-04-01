CREATE TABLE employee(
    sabeon VARCHAR2(30) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    birth DATE NOT NULL,
    tel VARCHAR2(30)
);

CREATE TABLE salary(
    salaryNum NUMBER PRIMARY KEY,
    sabeon VARCHAR2(30) NOT NULL,
    payDate VARCHAR2(30) NOT NULL,
    paymentDate DATE NOT NULL,
    pay NUMBER(8),
    sudang NUMBER(8),
    tax NUMBER(8),
    memo VARCHAR2(1000),
    CONSTRAINT FK_SALARY_SABEON FOREIGN KEY (sabeon) REFERENCES employee(sabeon)
);

drop table salary;

CREATE SEQUENCE salary_seq
    INCREMENT BY 1
    START WITH 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

SELECT * FROM seq;
    
desc employee;
desc salary;

select * from employee;
select * from salary;

commit;

SELECT salarynum, sabeon, paydate, paymentdate, pay, sudang, tax, memo FROM salary WHERE paydate='202004';