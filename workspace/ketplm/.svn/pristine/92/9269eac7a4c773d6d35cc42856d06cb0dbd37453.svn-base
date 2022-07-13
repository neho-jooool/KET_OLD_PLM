CREATE OR REPLACE FUNCTION FN_GET_ECM_NUMBERING( OBJECT_TYPE   IN VARCHAR2 )
RETURN VARCHAR2 IS
/*******************************************************************************
*  COPYRIGHT (C) 2010 한국단자 PLM 구축
********************************************************************************
*  FUNCTION NAME
*      FN_GET_NUMBERING
*
*  DESCRIPTION
*     설계변경 ID 의 채번을 위해 사용한다
*
*  HISTORY
********************************************************************************
*   오승연      INITIAL CREATION  2010-10-19
*******************************************************************************/
    RTN_NUMBERING    VARCHAR2(50);
BEGIN
    -- ECR
    IF OBJECT_TYPE = 'ECR' THEN
        SELECT  OBJECT_TYPE||'-'||TO_CHAR(SYSDATE,'YYMM')||'-'||TRIM(TO_CHAR(SEQ_ECR_NUM.NEXTVAL,'000'))
        INTO RTN_NUMBERING
        FROM DUAL;

    -- ECO
    ELSIF OBJECT_TYPE = 'ECO' THEN
        SELECT  OBJECT_TYPE||'-'||TO_CHAR(SYSDATE,'YYMM')||'-'||TRIM(TO_CHAR(SEQ_ECO_NUM.NEXTVAL,'000'))
        INTO RTN_NUMBERING
        FROM DUAL;

    -- Mold Plan

    ELSIF OBJECT_TYPE = 'PLAN' THEN
        SELECT  OBJECT_TYPE||'-'||TO_CHAR(SYSDATE,'YYMM')||'-'||TRIM(TO_CHAR(SEQ_PLAN_NUM.NEXTVAL,'000'))
        INTO RTN_NUMBERING
        FROM DUAL;

    END IF;

    RETURN RTN_NUMBERING;


END FN_GET_ECM_NUMBERING;
/

CREATE OR REPLACE FUNCTION FN_GET_PART_NUMBER_LIST( OBJECT_TYPE   IN VARCHAR2, OID_LVALUE IN NUMBER) 
RETURN VARCHAR2 IS
/*******************************************************************************
*  COPYRIGHT (C) 2010 한국단자 PLM 구축
********************************************************************************
*  FUNCTION NAME
*      FN_GET_PART_NUMBER_LIST
*
*  DESCRIPTION
*     설계변경에 포함된 부품 리스트를 가져오는 함수
*
*  HISTORY
********************************************************************************
*   안수학      INITIAL CREATION  2010-11-04
*******************************************************************************/
    RTN_PART_NUMBER_LIST    VARCHAR2(256);

    CURSOR C_ECR_PART
    (
        P_OID_LVALUE KETMoldECRPartLink.idA3B5%TYPE
    )
    IS
    SELECT ST3.*
    FROM KETMoldECRPartLink ST1
      , WTPart ST2
      , WTPartMaster ST3
    WHERE 1 = 1
    AND ST1.idA3A5 = ST2.idA2A2
    AND ST2.idA3masterReference = ST3.idA2A2
    AND ST1.idA3B5 = P_OID_LVALUE
    ORDER BY WTPartNumber
    ;
 
    CURSOR C_ECO_PART
    (
        P_OID_LVALUE KETMoldECOPartLink.idA3B5%TYPE
    )
    IS
    SELECT ST3.*
    FROM KETMoldECOPartLink ST1
      , WTPart ST2
      , WTPartMaster ST3
    WHERE 1 = 1
    AND ST1.idA3A5 = ST2.idA2A2
    AND ST2.idA3masterReference = ST3.idA2A2
    AND ST1.idA3B5 = P_OID_LVALUE
    ORDER BY WTPartNumber
    ;
 
    PART_INFO WTPartMaster%ROWTYPE;
    CNT NUMBER(2);
BEGIN
       
  RTN_PART_NUMBER_LIST := '';  
  CNT := 0;

    -- ECR
    IF OBJECT_TYPE = 'ECR' THEN
    FOR PART_INFO IN C_ECR_PART(OID_LVALUE) LOOP
      CNT := CNT + 1;
      IF CNT <= 1 THEN
        RTN_PART_NUMBER_LIST := PART_INFO.WTPartNumber;
      ELSE
        RTN_PART_NUMBER_LIST := RTN_PART_NUMBER_LIST || ',' || PART_INFO.WTPartNumber;
      END IF;
          EXIT WHEN C_ECR_PART%NOTFOUND;
      END LOOP;
    -- ECO
    ELSIF OBJECT_TYPE = 'ECO' THEN
    FOR PART_INFO IN C_ECO_PART(OID_LVALUE) LOOP
      CNT := CNT + 1;
      IF CNT <= 1 THEN
        RTN_PART_NUMBER_LIST := PART_INFO.WTPartNumber;
      ELSE
        RTN_PART_NUMBER_LIST := RTN_PART_NUMBER_LIST || ',' || PART_INFO.WTPartNumber;
      END IF;
          EXIT WHEN C_ECO_PART%NOTFOUND;
      END LOOP;
  END IF;

    RETURN RTN_PART_NUMBER_LIST;


END FN_GET_PART_NUMBER_LIST;
/

create or replace procedure PRC_RESET_MONTH_SEQ( p_seq_name in varchar2 )
/*******************************************************************************
*  COPYRIGHT (C) 한국단자공업 PLM 구축 프로젝트
********************************************************************************
*  PROCEDURE NAME
*      PRC_RESET_MONTH_SEQ
*
*  DESCRIPTION
*    SEQ를 RESET 하는 함수
*
*  HISTORY
********************************************************************************
*   오승연     INITIAL CREATION  2011.04.12
*   XXX        PARTIAL MODIFY    YYYY.MM.DD
*******************************************************************************/
  is
      l_val INTEGER;
  BEGIN
      execute immediate
      'select ' || p_seq_name || '.nextval from dual' INTO l_val;
      execute immediate
      'alter sequence ' || p_seq_name || ' increment by -' || l_val || ' minvalue 0';
      execute immediate
      'select ' || p_seq_name || '.nextval from dual' INTO l_val;
      execute immediate
      'alter sequence ' || p_seq_name || ' increment by 1';
      execute immediate
      'select ' || p_seq_name || '.nextval from dual' INTO l_val;
      execute immediate
      'alter sequence ' || p_seq_name || ' minvalue 1';

  END PRC_RESET_MONTH_SEQ;
/
CREATE OR REPLACE FUNCTION FN_GET_VENDORNAME( V_TYPE  IN VARCHAR2, V_CODE IN VARCHAR2 )
RETURN VARCHAR2 IS
/*******************************************************************************
*  COPYRIGHT (C) 2010 한국단자 PLM 구축
********************************************************************************
*  FUNCTION NAME
*      FN_GET_VENDORNAME
*
*  DESCRIPTION
*     생산처 이름을 얻어오는 함수
*
*  HISTORY
********************************************************************************
*   오승연      INITIAL CREATION  2011-06-16
*******************************************************************************/
    RTN_VENDOR_NAME    VARCHAR2(50);
BEGIN
    -- 사내
    IF V_TYPE = 'i' THEN
        SELECT N.NAME
        INTO RTN_VENDOR_NAME
        FROM NUMBERCODE N
       WHERE N.CODETYPE = 'PRODUCTIONDEPT'
          AND N.CODE = V_CODE;

    -- 외주
    ELSIF V_TYPE = 'o' THEN
        SELECT P.PARTNERNAME
        INTO RTN_VENDOR_NAME
        FROM KETPARTNER P
       WHERE P.PARTNERNO = V_CODE;

    END IF;

    RETURN RTN_VENDOR_NAME;
END FN_GET_VENDORNAME;
/
CREATE OR REPLACE FUNCTION FN_STR_BOM_SUBSTITUTE( ECO_HEADER_NUMBER IN VARCHAR2
                                                                        , ECO_ITEM_CODE IN VARCHAR2
                                                                        , PARENT_ITEM_CODE IN VARCHAR2
                                                                        , CHILD_ITEM_CODE IN VARCHAR2
                                                                        , V_FLAG IN VARCHAR2 ) 
RETURN VARCHAR2 IS
/*******************************************************************************
*  COPYRIGHT (C) 2010 한국단자 PLM 구축
********************************************************************************
*  FUNCTION NAME
*      FN_STR_BOM_SUBSTITUTE
*
*  DESCRIPTION
*     설계변경 대체부품 String 가져오는 함수
*
*  HISTORY
********************************************************************************
*   오승연      INITIAL CREATION  2011-05-04
*******************************************************************************/
  V_STR_SUBSTITUTE  VARCHAR2(100); 

BEGIN
   -- 변경 전 대체부품
   IF V_FLAG = 'B' THEN
     
     SELECT substr(max(sys_connect_by_path( t.itemcode, ',' )),2)  before_substitute
       INTO V_STR_SUBSTITUTE
       FROM ( SELECT ROWNUM  rnum
                        , t.substituteitemcode  itemcode
                        , t.ecoheadernumber      eco_id
                  FROM ketbomecosubstitute t 
                 where t.ecoheadernumber = ECO_HEADER_NUMBER
                   AND t.ecoitemcode = ECO_ITEM_CODE                 
                   AND t.parentitemcode = PARENT_ITEM_CODE
                   AND t.childitemcode = CHILD_ITEM_CODE
                   AND ( t.ecocode IS NULL OR t.ecocode <>'Add' ) ) t
        START WITH rnum = 1
        CONNECT by PRIOR rnum = rnum-1  AND PRIOR eco_id = eco_id;
    
    -- 변경 후 대체부품    
    ELSIF V_FLAG = 'A' THEN
    
          SELECT substr(max(sys_connect_by_path( t.itemcode, ',' )),2)  before_substitute
            INTO V_STR_SUBSTITUTE
           FROM ( SELECT ROWNUM  rnum
                   , t.substituteitemcode  itemcode
                   , t.ecoheadernumber      eco_id
            FROM ketbomecosubstitute t 
            where t.ecoheadernumber = ECO_HEADER_NUMBER
              AND t.ecoitemcode = ECO_ITEM_CODE                 
              AND t.parentitemcode = PARENT_ITEM_CODE
              AND t.childitemcode = CHILD_ITEM_CODE
              AND ( t.ecocode IS NULL OR t.ecocode <>'Delete') ) t
           START WITH rnum = 1
           CONNECT by PRIOR rnum = rnum-1  AND PRIOR eco_id = eco_id;
    
    END IF;
  
  RETURN V_STR_SUBSTITUTE;
end FN_STR_BOM_SUBSTITUTE;
/
begin
  sys.dbms_job.submit(job => :job,
                      what => 'PRC_RESET_MONTH_SEQ(''SEQ_ECO_NUM'');
PRC_RESET_MONTH_SEQ(''SEQ_ECR_NUM'');
PRC_RESET_MONTH_SEQ(''SEQ_PLAN_NUM'');',
                      next_date => to_date('31-07-2011 23:59:59', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'LAST_DAY(TRUNC(SYSDATE, ''day''))+0.99999');
  commit;
end;
/
