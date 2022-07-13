DROP TABLE KETBOMComponent;
CREATE TABLE KETBOMComponent (
	newBomCode			VARCHAR2(40)		NOT NULL,
	bomVersion			VARCHAR2(10),
	parentItemCode		VARCHAR2(40)		NOT NULL,
	childItemCode		VARCHAR2(40)		NOT NULL,
	bomLevel			VARCHAR2(10),
	sequenceNumber		VARCHAR2(100),
	itemSeq				VARCHAR2(10)		NOT NULL,
	quantity			VARCHAR2(10)		NOT NULL,
	boxQuantity			VARCHAR2(10),
	unit				VARCHAR2(10),
	material			VARCHAR2(20),
	hardnessFrom		VARCHAR2(10),
	hardnessTo			VARCHAR2(10),
	designDate			VARCHAR2(10),
	transferFlag		VARCHAR2(1)   default '1',
	newFlag				VARCHAR2(10),
	firstRemark			VARCHAR2(10),
	secondRemark		VARCHAR2(10),
	startDate			VARCHAR2(10),
	endDate				VARCHAR2(10),	
	attribute1			VARCHAR2(2000),
	attribute2			VARCHAR2(2000),
	attribute3			VARCHAR2(2000),
	attribute4			VARCHAR2(2000),
	attribute5			VARCHAR2(2000),
	attribute6			VARCHAR2(2000),
	attribute7			VARCHAR2(2000),
	attribute8			VARCHAR2(2000),
	attribute9			VARCHAR2(2000),
	attribute10			VARCHAR2(2000)
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


DROP TABLE KETBOMSubstitute;
CREATE TABLE KETBOMSubstitute (
	parentItemCode			VARCHAR2(40)		NOT NULL,
	childItemCode			VARCHAR2(40)		NOT NULL,
	substituteItemCode		VARCHAR2(40)		NOT NULL,
	quantity				VARCHAR2(10)		NOT NULL,
	unit					VARCHAR2(10),
	sequenceNumber			VARCHAR2(100),
	newBomCode				VARCHAR2(40)		NOT NULL,
	bomVersion				VARCHAR2(10),
	transferFlag			VARCHAR2(1)  default '1',
	startDate				VARCHAR2(10),
	endDate					VARCHAR2(10),	
	attribute1				VARCHAR2(2000),
	attribute2				VARCHAR2(2000),
	attribute3				VARCHAR2(2000),
	attribute4				VARCHAR2(2000),
	attribute5				VARCHAR2(2000),
	attribute6				VARCHAR2(2000),
	attribute7				VARCHAR2(2000),
	attribute8				VARCHAR2(2000),
	attribute9				VARCHAR2(2000),
	attribute10				VARCHAR2(2000)
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


DROP TABLE KETBOMCoworker;
CREATE TABLE KETBOMCoworker (
	newBOMCode			VARCHAR2(40)		NOT NULL,
	bomVersion			VARCHAR2(10),
	coworkerId			VARCHAR2(30)		NOT NULL,
	coworkerName		VARCHAR2(60),
	coworkerDept		VARCHAR2(60),
	coworkerEmail		VARCHAR2(30),
	endWorkingFlag		VARCHAR2(1)			NOT NULL,
	attribute1			VARCHAR2(2000),
	attribute2			VARCHAR2(2000),
	attribute3			VARCHAR2(2000),
	attribute4			VARCHAR2(2000),
	attribute5			VARCHAR2(2000),
	attribute6			VARCHAR2(2000),
	attribute7			VARCHAR2(2000),
	attribute8			VARCHAR2(2000),
	attribute9			VARCHAR2(2000),
	attribute10			VARCHAR2(2000)
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

DROP TABLE KETBOMECOComponent;
CREATE TABLE KETBOMECOComponent (
	ecoHeaderNumber			VARCHAR2(60)		NOT NULL,
	ecoItemCode				VARCHAR2(20)		NOT NULL,
	appliedProductCode		VARCHAR2(40),
	parentItemCode			VARCHAR2(40)		NOT NULL,
	childItemCode			VARCHAR2(40)		NOT NULL,
	bomLevel				VARCHAR2(10),
	bomVersion				VARCHAR2(10),
	sequenceNumber			VARCHAR2(100),
	itemSeq					VARCHAR2(10)		NOT NULL,
	beforeQuantity			VARCHAR2(10),
	afterQuantity			VARCHAR2(10),
	beforeUnit				VARCHAR2(10),
	afterUnit				VARCHAR2(10),
	beforeMaterial			VARCHAR2(20),
	afterMaterial			VARCHAR2(20),
	beforeHardnessFrom		VARCHAR2(10),
	afterHardnessFrom		VARCHAR2(10),
	beforeHardnessTo		VARCHAR2(10),
	afterHardnessTo			VARCHAR2(10),
	beforeDesignDate		VARCHAR2(10),
	afterDesignDate			VARCHAR2(10),
	beforeStartDate			VARCHAR2(10),
	afterStartDate			VARCHAR2(10),
	beforeEndDate			VARCHAR2(10),
	afterEndDate			VARCHAR2(10),	
	beforeBoxQuantity		VARCHAR2(10),
	afterBoxQuantity		VARCHAR2(10),	
	ecoCode					VARCHAR2(10),
	transferFlag			VARCHAR2(1)  default '1',
	attribute1				VARCHAR2(2000),
	attribute2				VARCHAR2(2000),
	attribute3				VARCHAR2(2000),
	attribute4				VARCHAR2(2000),
	attribute5				VARCHAR2(2000),
	attribute6				VARCHAR2(2000),
	attribute7				VARCHAR2(2000),
	attribute8				VARCHAR2(2000),
	attribute9				VARCHAR2(2000),
	attribute10				VARCHAR2(2000)
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


DROP TABLE KETBOMECOSubstitute;
CREATE TABLE KETBOMECOSubstitute (
	ecoHeaderNumber			VARCHAR2(60)		NOT NULL,
	ecoItemCode				VARCHAR2(20)		NOT NULL,
	parentItemCode			VARCHAR2(40)		NOT NULL,
	childItemCode			VARCHAR2(40)		NOT NULL,
	substituteItemCode		VARCHAR2(40)		NOT NULL,
	beforeQuantity			VARCHAR2(10),
	afterQuantity			VARCHAR2(10),
	beforeUnit				VARCHAR2(10),
	afterUnit				VARCHAR2(10),
	beforeStartDate			VARCHAR2(10),
	afterStartDate			VARCHAR2(10),
	beforeEndDate			VARCHAR2(10),
	afterEndDate			VARCHAR2(10),		
	sequenceNumber			VARCHAR2(100),
	ecoCode					VARCHAR2(10),
	transferFlag			VARCHAR2(1)  default '1',
	attribute1				VARCHAR2(2000),
	attribute2				VARCHAR2(2000),
	attribute3				VARCHAR2(2000),
	attribute4				VARCHAR2(2000),
	attribute5				VARCHAR2(2000),
	attribute6				VARCHAR2(2000),
	attribute7				VARCHAR2(2000),
	attribute8				VARCHAR2(2000),
	attribute9				VARCHAR2(2000),
	attribute10				VARCHAR2(2000)
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


DROP TABLE KETBOMECOCoworker;
CREATE TABLE KETBOMECOCoworker (
	ecoHeaderNumber		VARCHAR2(60)		NOT NULL,
	ecoItemCode			VARCHAR2(20)		NOT NULL,
	coworkerId			VARCHAR2(30)		NOT NULL,
	coworkerName		VARCHAR2(60),
	coworkerDept		VARCHAR2(60),
	coworkerEmail		VARCHAR2(30),
	endWorkingFlag		VARCHAR2(1)			NOT NULL,
	attribute1			VARCHAR2(2000),
	attribute2			VARCHAR2(2000),
	attribute3			VARCHAR2(2000),
	attribute4			VARCHAR2(2000),
	attribute5			VARCHAR2(2000),
	attribute6			VARCHAR2(2000),
	attribute7			VARCHAR2(2000),
	attribute8			VARCHAR2(2000),
	attribute9			VARCHAR2(2000),
	attribute10			VARCHAR2(2000)
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


DROP TABLE KETBOMSubstituteMaster;
CREATE TABLE KETBOMSubstituteMaster (
	parentItem			VARCHAR2(40)		NOT NULL,
	childItem			VARCHAR2(40)		NOT NULL,
	substituteItem		VARCHAR2(40)		NOT NULL,
	quantity			VARCHAR2(10),
	unit				VARCHAR2(10),
	startDate			VARCHAR2(10),
	endDate				VARCHAR2(10),	
	versionItemCode		VARCHAR2(40),
	attribute1			VARCHAR2(2000),
	attribute2			VARCHAR2(2000),
	attribute3			VARCHAR2(2000),
	attribute4			VARCHAR2(2000),
	attribute5			VARCHAR2(2000),
	attribute6			VARCHAR2(2000),
	attribute7			VARCHAR2(2000),
	attribute8			VARCHAR2(2000),
	attribute9			VARCHAR2(2000),
	attribute10			VARCHAR2(2000)
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
alter table KETBOMSubstituteMaster add constraint UK_KETBOMSubstituteMaster unique (parentItem,substituteItem);

DROP TABLE KETBOMECOTempComponent;
CREATE TABLE KETBOMECOTempComponent (
	ecoHeaderNumber			VARCHAR2(60)		NOT NULL,
	ecoItemCode				VARCHAR2(20)		NOT NULL,
	parentItemCode			VARCHAR2(40)		NOT NULL,
	childItemCode			VARCHAR2(40)		NOT NULL,
	afterQuantity			VARCHAR2(10),
	afterBoxQuantity		VARCHAR2(10),
	afterUnit				VARCHAR2(10),
	afterMaterial			VARCHAR2(20),
	afterHardnessFrom		VARCHAR2(10),
	afterHardnessTo			VARCHAR2(10),
	afterDesignDate			VARCHAR2(10),
	afterStartDate			VARCHAR2(10),
	afterEndDate			VARCHAR2(10),
	sequenceNumber			VARCHAR2(100),
	bomversion				VARCHAR2(10),
	attribute1				VARCHAR2(2000),
	attribute2				VARCHAR2(2000),
	attribute3				VARCHAR2(2000),
	attribute4				VARCHAR2(2000),
	attribute5				VARCHAR2(2000),
	attribute6				VARCHAR2(2000),
	attribute7				VARCHAR2(2000),
	attribute8				VARCHAR2(2000),
	attribute9				VARCHAR2(2000),
	attribute10				VARCHAR2(2000)
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


DROP TABLE KETBOMECOTempSubstitute;
CREATE TABLE KETBOMECOTempSubstitute (
	ecoHeaderNumber			VARCHAR2(60)		NOT NULL,
	ecoItemCode				VARCHAR2(20)		NOT NULL,
	parentItemCode			VARCHAR2(40)		NOT NULL,
	childItemCode			VARCHAR2(40)		NOT NULL,
	substituteItemCode		VARCHAR2(40)		NOT NULL,
	afterQuantity			VARCHAR2(10),
	afterUnit				VARCHAR2(10),
	afterStartDate			VARCHAR2(10),
	afterEndDate			VARCHAR2(10),	
	sequenceNumber			VARCHAR2(100),
	attribute1				VARCHAR2(2000),
	attribute2				VARCHAR2(2000),
	attribute3				VARCHAR2(2000),
	attribute4				VARCHAR2(2000),
	attribute5				VARCHAR2(2000),
	attribute6				VARCHAR2(2000),
	attribute7				VARCHAR2(2000),
	attribute8				VARCHAR2(2000),
	attribute9				VARCHAR2(2000),
	attribute10				VARCHAR2(2000)
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
	
	
DROP TABLE KETERPECOHistory;
CREATE TABLE KETERPECOHistory (
	dieNo			VARCHAR2(20)		NOT NULL,
	itemCode		VARCHAR2(20)		NOT NULL,
	mocnt			VARCHAR2(10)		NOT NULL,
	revisiondate	VARCHAR2(20),
	empno			VARCHAR2(30),
	mouser			VARCHAR2(30),
	modesc			VARCHAR2(2000),
	attribute1		VARCHAR2(2000),
	attribute2		VARCHAR2(2000),
	attribute3		VARCHAR2(2000),
	attribute4		VARCHAR2(2000),
	attribute5		VARCHAR2(2000),
	attribute6		VARCHAR2(2000),
	attribute7		VARCHAR2(2000),
	attribute8		VARCHAR2(2000),
	attribute9		VARCHAR2(2000),
	attribute10		VARCHAR2(2000)
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
  
	
DROP TABLE KETBOMCheckOut;
CREATE TABLE KETBOMCheckOut (
	itemCode		VARCHAR2(40)		NOT NULL,
	itemVersion		VARCHAR2(30)		NOT NULL,
	checkOuterID	VARCHAR2(60)		NOT NULL,
	attribute1		VARCHAR2(2000),
	attribute2		VARCHAR2(2000),
	attribute3		VARCHAR2(2000),
	attribute4		VARCHAR2(2000),
	attribute5		VARCHAR2(2000),
	attribute6		VARCHAR2(2000),
	attribute7		VARCHAR2(2000),
	attribute8		VARCHAR2(2000),
	attribute9		VARCHAR2(2000),
	attribute10		VARCHAR2(2000)
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

create sequence KET_BOM_TRX_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

create sequence KET_ECO_TRX_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;

create sequence KET_ITEM_TRX_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
nocache;