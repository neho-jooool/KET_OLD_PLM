DROP TABLE KETPARTNER;
CREATE TABLE KETPARTNER
(
  partnerno      VARCHAR2(20) not null,
  partnername    VARCHAR2(200),
  partnertype    VARCHAR2(200),
  address        VARCHAR2(4000),
  representative VARCHAR2(200),
  attribute1     VARCHAR2(4000),
  attribute2     VARCHAR2(4000),
  attribute3     VARCHAR2(4000),
  attribute4     VARCHAR2(4000),
  attribute5     VARCHAR2(4000),
  attribute6     VARCHAR2(4000),
  attribute7     VARCHAR2(4000),
  attribute8     VARCHAR2(4000),
  attribute9     VARCHAR2(4000),
  attribute10    VARCHAR2(4000)
)
tablespace KET_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 80K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table KETPARTNER
  is '���»� ����';
-- Add comments to the columns 
comment on column KETPARTNER.partnerno
  is '���»� �ڵ�';
comment on column KETPARTNER.partnername
  is '���»�';
comment on column KETPARTNER.partnertype
  is '����';
comment on column KETPARTNER.address
  is '�ּ�';
comment on column KETPARTNER.representative
  is '��ǥ�ڼ���';
comment on column KETPARTNER.attribute1
  is '��Ÿ1';
comment on column KETPARTNER.attribute2
  is '��Ÿ2';
comment on column KETPARTNER.attribute3
  is '��Ÿ3';
comment on column KETPARTNER.attribute4
  is '��Ÿ4';
comment on column KETPARTNER.attribute5
  is '��Ÿ5';
comment on column KETPARTNER.attribute6
  is '��Ÿ6';
comment on column KETPARTNER.attribute7
  is '��Ÿ7';
comment on column KETPARTNER.attribute8
  is '��Ÿ8';
comment on column KETPARTNER.attribute9
  is '��Ÿ9';
comment on column KETPARTNER.attribute10
  is '��Ÿ10';
-- Create/Recreate primary, unique and foreign key constraints 
alter table KETPARTNER
  add constraint PK_KETPARTNER primary key (PARTNERNO)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 80K
    minextents 1
    maxextents unlimited
  );
