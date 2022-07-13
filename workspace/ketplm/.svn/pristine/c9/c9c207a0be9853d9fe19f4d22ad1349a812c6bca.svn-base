-- NumberCodeValue 테이블에서 type, code, lang과 일치하는 Value 추출
CREATE OR REPLACE FUNCTION FN_GET_NUMBERCODEVALUE(V_CODETYPE IN VARCHAR2
                                                 ,V_CODE     IN VARCHAR2
                                                 ,V_LANG     IN VARCHAR2)
RETURN  VARCHAR2
IS
    V_RETURN VARCHAR2(200);
   
BEGIN
    SELECT VALUE INTO V_RETURN
      FROM NUMBERCODEVALUE
     WHERE CODETYPE = V_CODETYPE
       AND CODE     = V_CODE
       AND LANG     = V_LANG;
     
     RETURN V_RETURN;
END;

CREATE OR REPLACE FUNCTION FN_GET_NUMBERCODEDESC(V_CODETYPE IN VARCHAR2
                                                 ,V_CODE     IN VARCHAR2
                                                 ,V_LANG     IN VARCHAR2)
RETURN  VARCHAR2
IS
    V_RETURN VARCHAR2(200);
   
BEGIN
    SELECT DESCRIPTION INTO V_RETURN
      FROM NUMBERCODEVALUE
     WHERE CODETYPE = V_CODETYPE
       AND CODE     = V_CODE
       AND LANG     = V_LANG;
     
     RETURN V_RETURN;
END;

-- Numbercodevalue의 상위 가져오기
CREATE OR REPLACE FUNCTION FN_GET_NUMBERCODEVALUE_PARENT(V_CODETYPE IN VARCHAR2
                                                        ,V_CODE     IN VARCHAR2
                                                        ,V_LANG     IN VARCHAR2)
RETURN  VARCHAR2
IS
    V_RETURN VARCHAR2(200);
   
BEGIN
    SELECT NCV.VALUE INTO V_RETURN
      FROM NUMBERCODE      NC
          ,NUMBERCODEVALUE NCV
     WHERE NC.CODETYPE = NCV.CODETYPE
       AND NC.CODE     = NCV.CODE
       AND NCV.LANG    = V_LANG
       AND NC.IDA2A2   = (SELECT IDA3A3
                            FROM NUMBERCODE
                           WHERE CODETYPE = V_CODETYPE
                             AND CODE     = V_CODE);
     RETURN V_RETURN;
END;

-- Number code OID로 찾기
CREATE OR REPLACE FUNCTION FN_GET_NUMBERCODEVALUE_OID(V_CODETYPE IN VARCHAR2
                                                     ,V_OID      IN VARCHAR2
                                                     ,V_LANG     IN VARCHAR2)
RETURN  VARCHAR2
IS
    V_RETURN VARCHAR2(200);
   
BEGIN
    SELECT NCV.VALUE INTO V_RETURN
      FROM NUMBERCODE      NC
          ,NUMBERCODEVALUE NCV
     WHERE NC.CODETYPE = NCV.CODETYPE
       AND NC.CODE     = NCV.CODE
       AND NC.CODETYPE = V_CODETYPE
       AND NC.IDA2A2   = V_OID
       AND NCV.LANG    = V_LANG;
     
     RETURN V_RETURN;
END;