--------------------------------------------------------
--  DDL for Table DOCS
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."DOCS"
   (	"ID" NUMBER,
	"DATA" DATE,
	"TITLE" VARCHAR2(50),
	"DESCRIPTION" VARCHAR2(200),
	"USER_ID" NUMBER
   );

CREATE SEQUENCE docs_seq START WITH 1;

--------------------------------------------------------
--  DDL for Table FILES
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."FILES"
   (	"ID" NUMBER,
	"NAME" VARCHAR2(200),
	"DOC_ID" NUMBER
   );

CREATE SEQUENCE files_seq START WITH 1;

--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."USERS"
   (	"ID" NUMBER,
	"LOGIN" VARCHAR2(20),
	"PASSWORD" VARCHAR2(20),
	"NAME" VARCHAR2(200)
   ) ;


CREATE SEQUENCE user_seq START WITH 1;

--------------------------------------------------------
--  DDL for Trigger USER_BIR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."USER_BIR"
BEFORE INSERT ON users
FOR EACH ROW

BEGIN
  SELECT user_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/
ALTER TRIGGER "SYSTEM"."USER_BIR" ENABLE;

--------------------------------------------------------
--  DDL for Trigger FILES_BIR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."FILES_BIR"
BEFORE INSERT ON files
FOR EACH ROW

BEGIN
  SELECT files_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/
ALTER TRIGGER "SYSTEM"."FILES_BIR" ENABLE;

--------------------------------------------------------
--  DDL for Trigger DOCS_BIR
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."DOCS_BIR"
BEFORE INSERT ON docs
FOR EACH ROW

BEGIN
  SELECT docs_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/
ALTER TRIGGER "SYSTEM"."DOCS_BIR" ENABLE;

--------------------------------------------------------
--  Inserting values
--------------------------------------------------------

-- INSERTING into SYSTEM.DOCS
Insert into SYSTEM.DOCS (DATA,TITLE,DESCRIPTION,USER_ID) values (to_date('27-JUL-18','DD-MON-RR'),'Документ№1','Описание документа № 1',1);
Insert into SYSTEM.DOCS (DATA,TITLE,DESCRIPTION,USER_ID) values (to_date('26-JUL-18','DD-MON-RR'),'Документ№2','Описание документа №2',2);
Insert into SYSTEM.DOCS (DATA,TITLE,DESCRIPTION,USER_ID) values (to_date('27-JUL-18','DD-MON-RR'),'Документ№3','Описание какого-то документа',2);
Insert into SYSTEM.DOCS (DATA,TITLE,DESCRIPTION,USER_ID) values (to_date('24-JUL-18','DD-MON-RR'),'Документ№4','Описание тестового документа',3);
-- INSERTING into SYSTEM.FILES
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('document.txt',1);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('instruction.txt',1);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('doc.txt',2);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('test.txt',3);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('docdoc.txt',3);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('extdoc.txt',3);
Insert into SYSTEM.FILES (NAME,DOC_ID) values ('some_text.txt',4);
-- INSERTING into SYSTEM.USERS
Insert into SYSTEM.USERS (LOGIN,PASSWORD,NAME) values ('ivanov','asdf','Иванов');
Insert into SYSTEM.USERS (LOGIN,PASSWORD,NAME) values ('petrov','zxcv','Петров');
Insert into SYSTEM.USERS (LOGIN,PASSWORD,NAME) values ('sidorov','qwer','Сидоров');
--------------------------------------------------------
--  DDL for Index DOCS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."DOCS_PK" ON "SYSTEM"."DOCS" ("ID") ;
--------------------------------------------------------
--  DDL for Index FILES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."FILES_PK" ON "SYSTEM"."FILES" ("ID");
--------------------------------------------------------
--  DDL for Index USERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."USERS_PK" ON "SYSTEM"."USERS" ("ID");
--------------------------------------------------------
--  Constraints for Table DOCS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."DOCS" ADD CONSTRAINT "DOCS_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SYSTEM"."DOCS" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."DOCS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FILES
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."FILES" ADD CONSTRAINT "FILES_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SYSTEM"."FILES" MODIFY ("DOC_ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."FILES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."USERS" ADD CONSTRAINT "USERS_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SYSTEM"."USERS" MODIFY ("ID" NOT NULL ENABLE);

