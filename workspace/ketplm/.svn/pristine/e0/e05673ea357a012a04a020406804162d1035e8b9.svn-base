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
  is '협력사 정보';
-- Add comments to the columns 
comment on column KETPARTNER.partnerno
  is '협력사 코드';
comment on column KETPARTNER.partnername
  is '협력사';
comment on column KETPARTNER.partnertype
  is '종목';
comment on column KETPARTNER.address
  is '주소';
comment on column KETPARTNER.representative
  is '대표자성명';
comment on column KETPARTNER.attribute1
  is '기타1';
comment on column KETPARTNER.attribute2
  is '기타2';
comment on column KETPARTNER.attribute3
  is '기타3';
comment on column KETPARTNER.attribute4
  is '기타4';
comment on column KETPARTNER.attribute5
  is '기타5';
comment on column KETPARTNER.attribute6
  is '기타6';
comment on column KETPARTNER.attribute7
  is '기타7';
comment on column KETPARTNER.attribute8
  is '기타8';
comment on column KETPARTNER.attribute9
  is '기타9';
comment on column KETPARTNER.attribute10
  is '기타10';
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
