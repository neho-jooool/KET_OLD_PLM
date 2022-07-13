-- NumberCodeValue Table ����
DROP TABLE NUMBERCODEVALUE;

CREATE TABLE NUMBERCODEVALUE(
CODETYPE        VARCHAR2(600) not null,
CODE            VARCHAR2(600),
LANG            VARCHAR2(600),
VALUE           VARCHAR2(600),
DESCRIPTION     VARCHAR2(600),
CREATE_USER     VARCHAR2(100),
CREATE_DATE     DATE,
MODIFY_USER     VARCHAR2(100),
MODIFY_DATE     DATE)

tablespace KET_DATA  pctfree 10  initrans 1  maxtrans 255  storage  (initial 80K    minextents 1    maxextents unlimited);

-- Add comments to the table
comment on table NUMBERCODEVALUE   is 'NumberCodeVAlue';
-- Add comments to the columns
comment on column NUMBERCODEVALUE.CODETYPE  is 'Code Type';
comment on column NUMBERCODEVALUE.CODE  is 'Code';
comment on column NUMBERCODEVALUE.LANG  is 'Language';
comment on column NUMBERCODEVALUE.VALUE  is 'Value';
comment on column NUMBERCODEVALUE.DESCRIPTION  is '����';
comment on column NUMBERCODEVALUE.CREATE_USER  is '�ۼ���';
comment on column NUMBERCODEVALUE.CREATE_DATE  is '�ۼ���';
comment on column NUMBERCODEVALUE.MODIFY_USER  is '������';
comment on column NUMBERCODEVALUE.MODIFY_DATE  is '������';

commit;