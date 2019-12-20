desc student_jdbc;
SELECT * FROM student_jdbc;
INSERT INTO student_jdbc (id,name) VALUES (1000,'小麦');
INSERT INTO student_jdbc (id,name) VALUES (1001,'小菊花');
INSERT INTO student_jdbc (id,name) VALUES (1002,'张三');
INSERT INTO student_jdbc (id,name) VALUES (1003,'李四');
commit;

SELECT * FROM (SELECT ROWNUM rn,t.* FROM 
							(SELECT * FROM student_jdbc ORDER BY id) t WHERE ROWNUM<=4) tt 
							WHERE rn>=2;

--演示SQL注入
CREATE TABLE user_jdbc(
  	id NUMBER(6),
   name VARCHAR2(50),
   pwd VARCHAR2(50)
  );
INSERT INTO user_jdbc(id,name,pwd) VALUES (1,'Tom',123);
INSERT INTO user_jdbc(id,name,pwd) VALUES (2,'Jerry',123);
commit;
SELECT * FROM user_jdbc;


SELECT COUNT(*) AS c FROM user_jdbc WHERE name='hi' AND pwd='a' or 'b'='b';--2
SELECT * FROM emp ORDER BY empno;
DESC emp;


--演示事务的特性
CREATE TABLE account_jdbc(
    id NUMBER(6),
    name VARCHAR2(50),
    balance NUMBER(8,2)
);
INSERT INTO account_jdbc (id,name,balance) VALUES (1000,'张三',500);
INSERT INTO account_jdbc (id,name,balance) VALUES (1001,'李四',1500);
INSERT INTO account_jdbc (id,name,balance) VALUES (1002,'王五',2000);
COMMIT;
SELECT * FROM account_jdbc;


--李四借给张三1000块钱
UPDATE account_jdbc SET balance=balance-1000 WHERE id=1001;
UPDATE account_jdbc SET balance=balance+1000 WHERE id=1000;
COMMIT;
ROLLBACK;

--张三还给李四1000块钱，模拟场景：张三在给李四还钱时未提交事务，然后下面的王五向张三借钱
UPDATE account_jdbc SET balance=balance-1000 WHERE id=1000;
UPDATE account_jdbc SET balance=balance+1000 WHERE id=1001;
COMMIT;

--王五向张三借1000块钱（要在另一个SQL developer中打开）
SELECT * FROM account_jdbc;
UPDATE account_jdbc SET balance=balance-1000 WHERE id=1001;
UPDATE account_jdbc SET balance=balance+1000 WHERE id=1003;
COMMIT;

--
UPDATE account_jdbc SET balance=balance-1000 WHERE id=1000;



--返回自动主键

--主表
CREATE TABLE keywords_jdbc(
    id NUMBER(8) PRIMARY KEY,
    word VARCHAR(10)
);
--序列的初始值和步进不指定时默认为1
CREATE SEQUENCE seq_post_kid;


--从表
CREATE TABLE post_jdbc(
    id NUMBER(8) PRIMARY KEY,
    content VARCHAR2(100),
    k_id NUMBER(8),
    CONSTRAINT post_kid_fk FOREIGN KEY(k_id) REFERENCES keywords_jdbc (id)
);
--序列的初始值和步进不指定时默认为1
CREATE SEQUENCE seq_keywords_id;

INSERT INTO keywords_jdbc(id,word) VALUES(seq_keywords_id.NEXTVAL,?);
INSERT INTO post_jdbc(id,content,k_id) VALUES(seq_post_kid.NEXTVAL,?,?);

SELECT * FROM keywords_jdbc;
SELECT * FROM post_jdbc;

DELETE FROM keywords_jdbc;
DELETE FROM post_jdbc;
COMMIT;
SELECT seq_post_kid.NEXTVAL FROM dual;
SELECT seq_post_kid.CURRVAL FROM dual;

SELECT  seq_keywords_id.NEXTVAL FROM dual;
SELECT  seq_keywords_id.CURRVAL FROM dual;



--JDBC分页查询
SELECT * FROM user_jdbc;
DESC user_jdbc;
SELECT * 
FROM(SELECT ROWNUM rn,t.*
              FROM (SELECT id,name,pwd FROM user_jdbc ORDER BY id) t
               WHERE ROWNUM<=10) tt
WHERE rn>=1;







