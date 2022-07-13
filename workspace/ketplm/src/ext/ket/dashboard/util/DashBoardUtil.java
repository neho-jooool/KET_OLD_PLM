package ext.ket.dashboard.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.session.SessionHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.common.files.util.DownloadView;
import ext.ket.shared.util.EjsConverUtil;

public class DashBoardUtil {

    private static final Logger LOGGER = Logger.getLogger(DashBoardUtil.class);

    public static final DashBoardUtil manager = new DashBoardUtil();

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오전 10:50:02
     * @method getColumnMap
     * @return Map<String,String>
     * </pre>
     */
    private Map<String, String> getColumnMap(Map<String, Object> reqMap) {
	String roleExportYN = (String) reqMap.get("roleExportYN");
	Map<String, String> columnMap = new HashMap<String, String>();

	columnMap.put("Project_No", "PJTNO");
	columnMap.put("Project_Name", "PJTNAME");
	columnMap.put("상태", "STATESTATE");
	columnMap.put("개발단계", "DEVSTEP");
	columnMap.put("개발구분", "DEVGB");
	columnMap.put("설계구분", "DESIGNGB");
	columnMap.put("조립구분", "ASSYGB");
	columnMap.put("Rank", "RANK");
	columnMap.put("개발목적1", "DEVGOAL1");
	columnMap.put("개발목적2", "DEVGOAL2");
	columnMap.put("제품군", "PRODUCTGB");
	columnMap.put("PM", "USER1");
	columnMap.put("PM부서", "DEPT1");
	columnMap.put("초류관리", "USER2");
	columnMap.put("초류관리부서", "DEPT2");
	columnMap.put("개발", "USER3");
	columnMap.put("개발부서", "DEPT3");
	columnMap.put("영업", "USER4");
	columnMap.put("영업부서", "DEPT4");
	columnMap.put("최종사용처", "CUSTOMER");
	columnMap.put("접점고객", "SUBCONTRACTOR");
	columnMap.put("대표차종", "CARNAME");
	columnMap.put("연간수량", "AVG");
	columnMap.put("전체수량", "TOTAL");
	columnMap.put("SOP~EOP(YEARS)", "YEARS");
	columnMap.put("PROTO_최종사용처", "PROTO");
	columnMap.put("P1_최종사용처", "P1");
	columnMap.put("P2_최종사용처", "P2");
	columnMap.put("SOP_최종사용처", "SOP");
	columnMap.put("PROTO_접점고객", "PGMP0");
	columnMap.put("P1_접점고객", "PGMP1");
	columnMap.put("P2_접점고객", "PGMP2");
	columnMap.put("SOP_접점고객", "PGMSOP");
	columnMap.put("개발시작일", "EXECSTARTDATE");
	columnMap.put("개발착수의뢰_계획일", "REQCREATEDATE");
	columnMap.put("개발착수의뢰_완료일", "REQAPPDATE");
	columnMap.put("개발시작회의(DR1)_계획완료일", "planEndDate1");
	columnMap.put("개발시작회의(DR1)_실제완료일", "execEndDate1");
	columnMap.put("투자품의_계획완료일", "planEndDate2");
	columnMap.put("투자품의_실제완료일", "execEndDate2");
	columnMap.put("DESIGN_REVIEW(DR2)_계획완료일", "planEndDate3");
	columnMap.put("DESIGN_REVIEW(DR2)_실제완료일", "execEndDate3");
	columnMap.put("도면출도_계획완료일", "planEndDate4");
	columnMap.put("도면출도_실제완료일", "execEndDate4");
	columnMap.put("금형제작(PROTO)_계획완료일", "planEndDate5");
	columnMap.put("금형제작(PROTO)_실제완료일", "execEndDate5");
	columnMap.put("설비제작(PROTO)_계획완료일", "planEndDate6");
	columnMap.put("설비제작(PROTO)_실제완료일", "execEndDate6");
	columnMap.put("초도품제작(PROTO)_계획완료일", "planEndDate7");
	columnMap.put("초도품제작(PROTO)_실제완료일", "execEndDate7");
	columnMap.put("초도품평가(PROTO)_계획완료일", "planEndDate8");
	columnMap.put("초도품평가(PROTO)_실제완료일", "execEndDate8");
	columnMap.put("초도품납품(PROTO)_계획완료일", "planEndDate9");
	columnMap.put("초도품납품(PROTO)_실제완료일", "execEndDate9");
	columnMap.put("PROTO_샘플제작_계획완료일", "planEndDate10");
	columnMap.put("PROTO_샘플제작_실제완료일", "execEndDate10");
	columnMap.put("PROTO_샘플납품_계획완료일", "planEndDate11");
	columnMap.put("PROTO_샘플납품_실제완료일", "execEndDate11");
	columnMap.put("PROTO_신뢰성평가(DV)_계획완료일", "planEndDate12");
	columnMap.put("PROTO_신뢰성평가(DV)_실제완료일", "execEndDate12");
	columnMap.put("GATE1_계획완료일", "planEndDate13");
	columnMap.put("GATE1_실제완료일", "execEndDate13");
	columnMap.put("GATE2_계획완료일", "planEndDate14");
	columnMap.put("GATE2_실제완료일", "execEndDate14");
	columnMap.put("양산계획(DR3)_계획완료일", "planEndDate15");
	columnMap.put("양산계획(DR3)_실제완료일", "execEndDate15");
	columnMap.put("금형제작_계획완료일", "planEndDate16");
	columnMap.put("금형제작_실제완료일", "execEndDate16");
	columnMap.put("설비입고_및_설치_계획완료일", "planEndDate17");
	columnMap.put("설비입고_및_설치_실제완료일", "execEndDate17");
	columnMap.put("초도품제작(양산)_계획완료일", "planEndDate18");
	columnMap.put("초도품제작(양산)_실제완료일", "execEndDate18");
	columnMap.put("초도품평가(양산)_계획완료일", "planEndDate19");
	columnMap.put("초도품평가(양산)_실제완료일", "execEndDate19");
	columnMap.put("초도품납품_계획완료일", "planEndDate45");
	columnMap.put("초도품납품_실제완료일", "execEndDate45");
	columnMap.put("제품유효성평가(DR4)_계획완료일", "planEndDate20");
	columnMap.put("제품유효성평가(DR4)_실제완료일", "execEndDate20");
	columnMap.put("신뢰성평가(DV)_계획완료일", "planEndDate21");
	columnMap.put("신뢰성평가(DV)_실제완료일", "execEndDate21");
	columnMap.put("All-TOOL_준비_계획완료일", "planEndDate22");
	columnMap.put("All-TOOL_준비_실제완료일", "execEndDate22");
	columnMap.put("All-TOOL_준비회의_계획완료일", "planEndDate23");
	columnMap.put("All-TOOL_준비회의_실제완료일", "execEndDate23");
	columnMap.put("All-TOOL_검증_계획완료일", "planEndDate24");
	columnMap.put("All-TOOL_검증_실제완료일", "execEndDate24");
	columnMap.put("All-TOOL_점검_계획완료일", "planEndDate25");
	columnMap.put("All-TOOL_점검_실제완료일", "execEndDate25");
	columnMap.put("GATE3_계획완료일", "planEndDate26");
	columnMap.put("GATE3_실제완료일", "execEndDate26");
	columnMap.put("P1_납품_계획완료일", "planEndDate27");
	columnMap.put("P1_납품_실제완료일", "execEndDate27");
	columnMap.put("신뢰성평가(PV)_계획완료일", "planEndDate28");
	columnMap.put("신뢰성평가(PV)_실제완료일", "execEndDate28");
	columnMap.put("Full-TOOL_준비회의_계획완료일", "planEndDate29");
	columnMap.put("Full-TOOL_준비회의_실제완료일", "execEndDate29");
	columnMap.put("Full-TOOL_검증_계획완료일", "planEndDate30");
	columnMap.put("Full-TOOL_검증_실제완료일", "execEndDate30");
	columnMap.put("양산유효성평가(DR5)_계획완료일", "planEndDate31");
	columnMap.put("양산유효성평가(DR5)_실제완료일", "execEndDate31");
	columnMap.put("제품합격_계획완료일", "planEndDate32");
	columnMap.put("제품합격_실제완료일", "execEndDate32");
	columnMap.put("협력사ISIR승인_계획완료일", "planEndDate33");
	columnMap.put("협력사ISIR승인_실제완료일", "execEndDate33");
	columnMap.put("FULL-TOOL점검_계획완료일", "planEndDate34");
	columnMap.put("FULL-TOOL점검_실제완료일", "execEndDate34");
	columnMap.put("GATE4_계획완료일", "planEndDate35");
	columnMap.put("GATE4_실제완료일", "execEndDate35");
	columnMap.put("P2_납품_계획완료일", "planEndDate36");
	columnMap.put("P2_납품_실제완료일", "execEndDate36");
	columnMap.put("ISIR/PPAP_제출_계획완료일", "planEndDate37");
	columnMap.put("ISIR/PPAP_제출_실제완료일", "execEndDate37");
	columnMap.put("양산이관(금형)_계획완료일", "planEndDate38");
	columnMap.put("양산이관(금형)_실제완료일", "execEndDate38");
	columnMap.put("양산이관(설비)_계획완료일", "planEndDate39");
	columnMap.put("양산이관(설비)_실제완료일", "execEndDate39");
	columnMap.put("양산이관_계획완료일", "planEndDate40");
	columnMap.put("양산이관_실제완료일", "execEndDate40");
	columnMap.put("초도양산_계획완료일", "planEndDate41");
	columnMap.put("초도양산_실제완료일", "execEndDate41");
	columnMap.put("양산최종평가(DR6)_계획완료일", "planEndDate42");
	columnMap.put("양산최종평가(DR6)_실제완료일", "execEndDate42");
	columnMap.put("GATE5_계획완료일", "planEndDate43");
	columnMap.put("GATE5_실제완료일", "execEndDate43");
	columnMap.put("프로젝트완료_계획완료일", "planEndDate44");
	columnMap.put("프로젝트완료_실제완료일", "execEndDate44");
	if ("Y".equals(roleExportYN)) {
	    columnMap.put("금형개발(M)", "DIE_MOLD_U");
	    columnMap.put("금형개발(M)부서", "DIE_MOLD_D");
	    columnMap.put("금형개발(P)", "DIE_PRESS_U");
	    columnMap.put("금형개발(P)부서", "DIE_PRESS_D");
	    columnMap.put("금형개발(中)", "DIE_CHINA_U");
	    columnMap.put("금형개발(中)부서", "DIE_CHINA_D");
	    columnMap.put("선행QA", "ADV_QA_U");
	    columnMap.put("선행QA부서", "ADV_QA_D");
	    columnMap.put("선행QA(中)", "ADV_QA_CHINA_U");
	    columnMap.put("선행QA(中)부서", "ADV_QA_CHINA_D");
	    columnMap.put("선행QC", "ADV_QC_U");
	    columnMap.put("선행QC부서", "ADV_QC_D");
	    columnMap.put("QC(조립)", "QC_ASSY_U");
	    columnMap.put("QC(조립)부서", "QC_ASSY_D");
	    columnMap.put("QC(中)", "QC_CHINA_U");
	    columnMap.put("QC(中)부서", "QC_CHINA_D");
	    columnMap.put("초류관리(中)", "PROD_TECH_CHINA_U");
	    columnMap.put("초류관리(中)부서", "PROD_TECH_CHINA_D");
	    columnMap.put("생산(조립)", "PRODUCTION_ASSY_U");
	    columnMap.put("생산(조립)부서", "PRODUCTION_ASSY_D");
	    columnMap.put("생산(M)", "PRODUCTION_MOLD_U");
	    columnMap.put("생산(M)부서", "PRODUCTION_MOLD_D");
	    columnMap.put("생산(中)", "PRODUCTION_CHINA_U");
	    columnMap.put("생산(中)부서", "PRODUCTION_CHINA_D");
	}
	return columnMap;

    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오전 10:49:55
     * @method getPMScheduleQuery
     * @param reqMap
     * @param isCount
     * @return StringBuffer
     * </pre>
     */
    public StringBuffer getPMScheduleQuery(Map<String, Object> reqMap, boolean isCount, boolean isPaging) {

	StringBuffer sql = new StringBuffer();

	String roleExportYN = (String) reqMap.get("roleExportYN");

	if (isCount) {
	    sql.append("SELECT COUNT(*) AS TOTALCNT FROM ");
	} else {
	    sql.append("SELECT RDATA.* FROM(SELECT ROWNUM AS RNUM, ODATA.* FROM (SELECT DATA.* FROM ");
	}

	sql.append("(SELECT ");
	if (isCount) {
	    sql.append("      PJT.IDA2A2");
	} else {
	    sql.append("       PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2               AS OID");
	    sql.append("      ,E3PSPJTMST.PJTNO                                 AS PJTNO");
	    sql.append("      ,E3PSPJTMST.PJTNAME                               AS PJTNAME");
	    sql.append("      ,PJT.STATESTATE                                   AS STATESTATE");
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3F9) AS DEVSTEP"); // 개발단계
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3D9) AS DEVGB"); // 개발구분
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3G9) AS DESIGNGB"); // 설계구분
	    sql.append("      ,PROINFO.ASSYGB"); // 조립구분
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3B9) AS RANK");
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3M9) AS DEVGOAL1"); // 개발목적1
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3N9) AS DEVGOAL2"); // 개발목적2
	    sql.append("      ,(SELECT NAME FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3O9) AS PRODUCTGB"); // 제품군
	    sql.append("      ,PDATA.USERNM AS USER1"); // PM
	    sql.append("      ,PDATA.DEPT   AS DEPT1"); // PM부서
	    sql.append("      ,PDATA2.USERNM AS USER2"); // 초류관리
	    sql.append("      ,PDATA2.DEPT   AS DEPT2"); // 초류관리부서
	    sql.append("      ,PDATA3.USERNM AS USER3"); // 개발
	    sql.append("      ,PDATA3.DEPT   AS DEPT3"); // 개발부서
	    sql.append("      ,PDATA4.USERNM AS USER4"); // 영업
	    sql.append("      ,PDATA4.DEPT   AS DEPT4"); // 영업부서
	    sql.append("      ,(SELECT LISTAGG (B.NAME, ',') WITHIN GROUP (ORDER BY B.NAME)");
	    sql.append("          FROM PROJECTCUSTOMEREVENTLINK A, NUMBERCODE B");
	    sql.append("         WHERE PJT.IDA2A2 = A.IDA3B5");
	    sql.append("           AND B.CODETYPE = 'CUSTOMEREVENT'");
	    sql.append("           AND A.IDA3A5 = B.IDA2A2)                                     AS CUSTOMER"); // 최종사용처
	    sql.append("      ,(SELECT LISTAGG (B.NAME, ',') WITHIN GROUP (ORDER BY B.NAME)");
	    sql.append("          FROM PROJECTSUBCONTRACTORLINK A, NUMBERCODE B");
	    sql.append("         WHERE PJT.IDA2A2 = A.IDA3B5");
	    sql.append("           AND B.CODETYPE = 'SUBCONTRACTOR'");
	    sql.append("           AND A.IDA3A5 = B.IDA2A2)                                     AS SUBCONTRACTOR"); // 접점고객
	    sql.append("      ,CAR.NAME                                                         AS CARNAME"); // 대표차종
	    sql.append("      ,ROUND(NVL(REGEXP_SUBSTR(FN_GET_PRODUCTINFO(PJT.IDA2A2,CAR.IDA2A2), '[^|]+', 1, 2),0),0) AS AVG"); // 연간수량
	    sql.append("      ,NVL(REGEXP_SUBSTR(FN_GET_PRODUCTINFO(PJT.IDA2A2,CAR.IDA2A2), '[^|]+', 1, 1),0) AS TOTAL"); // 전체수량
	    sql.append("      ,DECODE(NVL(YIELD1,0),0,0,1)+DECODE(NVL(YIELD2,0),0,0,1)+DECODE(NVL(YIELD3,0),0,0,1)+DECODE(NVL(YIELD4,0),0,0,1)+DECODE(NVL(YIELD5,0),0,0,1)+");
	    sql.append("       DECODE(NVL(YIELD6,0),0,0,1)+DECODE(NVL(YIELD7,0),0,0,1)+DECODE(NVL(YIELD8,0),0,0,1)+DECODE(NVL(YIELD9,0),0,0,1)+DECODE(NVL(YIELD10,0),0,0,1)");
	    sql.append("       AS YEARS"); // SOP~EOP(YEARS)
	    sql.append("      ,(SELECT TO_CHAR(OEMENDDATE,'YYYY-MM-DD')");
	    sql.append("          FROM OEMPLAN");
	    sql.append("         WHERE IDA3B3 = MP.IDA2A2");
	    sql.append("           AND VIEWTYPE = '2')                             AS PROTO"); // PROTO 최종사용처
	    sql.append("      ,(SELECT TO_CHAR(OEMENDDATE,'YYYY-MM-DD')");
	    sql.append("          FROM OEMPLAN");
	    sql.append("         WHERE IDA3B3 = MP.IDA2A2");
	    sql.append("           AND VIEWTYPE = '3')                             AS P1"); // P1 최종사용처
	    sql.append("      ,(SELECT TO_CHAR(OEMENDDATE,'YYYY-MM-DD')");
	    sql.append("          FROM OEMPLAN");
	    sql.append("         WHERE IDA3B3 = MP.IDA2A2");
	    sql.append("           AND VIEWTYPE = '4')                             AS P2"); // P2 최종사용처
	    sql.append("      ,(SELECT TO_CHAR(OP.OEMENDDATE,'YYYY-MM-DD')");
	    sql.append("          FROM OEMPLAN        OP");
	    sql.append("              ,OEMPROJECTTYPE OEM");
	    sql.append("         WHERE OP.IDA3A3 = OEM.IDA2A2(+)");
	    sql.append("           AND OEM.NAME = 'SOP'");
	    sql.append("           AND IDA3B3 = MP.IDA2A2)                         AS SOP"); // SOP 최종사용처
	    sql.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '2', '')        AS PGMP0"); // PROTO 접점고객
	    sql.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '3', '')        AS PGMP1"); // P1 접점고객
	    sql.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '4', '')        AS PGMP2"); // P2 접점고객
	    sql.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '0', 'SOP')     AS PGMSOP"); // SOP 접점고객
	    sql.append("      ,TO_CHAR(SCHEDULE.EXECSTARTDATE,'YYYY-MM-DD')        AS EXECSTARTDATE"); // 개발시작일
	    sql.append("      ,DVLREQ.CREATEDATE                                   AS REQCREATEDATE"); // 개발착수의뢰 계획일
	    sql.append("      ,DVLREQ2.APPROVEDATE                                 AS REQAPPDATE"); // 개발착수의뢰 완료일
	    sql.append("      ,planEndDate1"); // 개발시작회의(DR1)_계획완료일
	    sql.append("      ,execEndDate1"); // 개발시작회의(DR1)_실제완료일
	    sql.append("      ,planEndDate2"); // 투자품의_계획완료일
	    sql.append("      ,execEndDate2"); // 투자품의_실제완료일
	    sql.append("      ,planEndDate3"); // Design Review(DR2)_계획완료일
	    sql.append("      ,execEndDate3"); // Design Review(DR2)_실제완료일
	    sql.append("      ,planEndDate4"); // 도면출도_계획완료일
	    sql.append("      ,execEndDate4"); // 도면출도_실제완료일
	    sql.append("      ,planEndDate5"); // 금형제작_계획완료일
	    sql.append("      ,execEndDate5"); // 금형제작_실제완료일
	    sql.append("      ,planEndDate6"); // 설비제작_계획완료일
	    sql.append("      ,execEndDate6"); // 설비제작_실제완료일
	    sql.append("      ,planEndDate7"); // 초도품제작(proto)_계획완료일
	    sql.append("      ,execEndDate7"); // 초도품제작(proto)_실제완료일
	    sql.append("      ,planEndDate8"); // 초도품평가(proto)_계획완료일
	    sql.append("      ,execEndDate8"); // 초도품평가(proto)_실제완료일
	    sql.append("      ,planEndDate9"); // 초도품납품(proto)_계획완료일
	    sql.append("      ,execEndDate9"); // 초도품납품(proto)_실제완료일
	    sql.append("      ,planEndDate10"); // PROTO샘플제작_계획완료일
	    sql.append("      ,execEndDate10"); // PROTO샘플제작_실제완료일
	    sql.append("      ,planEndDate11"); // PROTO샘플납품_계획완료일
	    sql.append("      ,execEndDate11"); // PROTO샘플납품_실제완료일
	    sql.append("      ,planEndDate12"); // 신뢰성평가(DV)_계획완료일
	    sql.append("      ,execEndDate12"); // 신뢰성평가(DV)_실제완료일
	    sql.append("      ,planEndDate13"); // GATE1_계획완료일
	    sql.append("      ,execEndDate13"); // GATE1_실제완료일
	    sql.append("      ,planEndDate14"); // GATE2_계획완료일
	    sql.append("      ,execEndDate14"); // GATE2_실제완료일
	    sql.append("      ,planEndDate15"); // 양산계획(DR3)_계획완료일
	    sql.append("      ,execEndDate15"); // 양산계획(DR3)_실제완료일
	    sql.append("      ,planEndDate16"); // 금형제작_계획완료일
	    sql.append("      ,execEndDate16"); // 금형제작_실제완료일
	    sql.append("      ,planEndDate17"); // 설비제작_계획완료일
	    sql.append("      ,execEndDate17"); // 설비제작_실제완료일
	    sql.append("      ,planEndDate18"); // 초도품제작(양산)_계획완료일
	    sql.append("      ,execEndDate18"); // 초도품제작(양산)_실제완료일
	    sql.append("      ,planEndDate19"); // 초도품평가(양산)_계획완료일
	    sql.append("      ,execEndDate19"); // 초도품평가(양산)_실제완료일
	    sql.append("      ,planEndDate45"); // 초도품납품_계획완료일
	    sql.append("      ,execEndDate45"); // 초도품납품_실제완료일
	    sql.append("      ,planEndDate20"); // 제품유효성평가(DR4)_계획완료일
	    sql.append("      ,execEndDate20"); // 제품유효성평가(DR4)_실제완료일
	    sql.append("      ,planEndDate21"); // 신뢰성평가(DV)_계획완료일
	    sql.append("      ,execEndDate21"); // 신뢰성평가(DV)_실제완료일
	    sql.append("      ,planEndDate22"); // ALL-TOOL 준비_계획완료일
	    sql.append("      ,execEndDate22"); // ALL-TOOL 준비_실제완료일
	    sql.append("      ,planEndDate23"); // ALL-TOOL 준비회의_계획완료일
	    sql.append("      ,execEndDate23"); // ALL-TOOL 준비회의_실제완료일
	    sql.append("      ,planEndDate24"); // ALL-TOOL 검증_계획완료일
	    sql.append("      ,execEndDate24"); // ALL-TOOL 검증_실제완료일
	    sql.append("      ,planEndDate25"); // ALL-TOOL 점검_계획완료일
	    sql.append("      ,execEndDate25"); // ALL-TOOL 점검_실제완료일
	    sql.append("      ,planEndDate26"); // GATE3_계획완료일
	    sql.append("      ,execEndDate26"); // GATE3_실제완료일
	    sql.append("      ,planEndDate27"); // P1납품_계획완료일
	    sql.append("      ,execEndDate27"); // P1납품_실제완료일
	    sql.append("      ,planEndDate28"); // 신뢰성평가(PV)_계획완료일
	    sql.append("      ,execEndDate28"); // 신뢰성평가(PV)_실제완료일
	    sql.append("      ,planEndDate29"); // FULL-TOOL 준비회의_계획완료일
	    sql.append("      ,execEndDate29"); // FULL-TOOL 준비회의_실제완료일
	    sql.append("      ,planEndDate30"); // FULL-TOOL 검증_계획완료일
	    sql.append("      ,execEndDate30"); // FULL-TOOL 검증_실제완료일
	    sql.append("      ,planEndDate31"); // 양산유효성평가(DR5)_계획완료일
	    sql.append("      ,execEndDate31"); // 양산유효성평가(DR5)_실제완료일
	    sql.append("      ,planEndDate32"); // 제품합격_계획완료일
	    sql.append("      ,execEndDate32"); // 제품합격_실제완료일
	    sql.append("      ,planEndDate33"); // 협력사 ISIR승인_계획완료일
	    sql.append("      ,execEndDate33"); // 협력사 ISIR승인_실제완료일
	    sql.append("      ,planEndDate34"); // FULL-TOOL 점검_계획완료일
	    sql.append("      ,execEndDate34"); // FULL-TOOL 점검_실제완료일
	    sql.append("      ,planEndDate35"); // GATE4_계획완료일
	    sql.append("      ,execEndDate35"); // GATE4_실제완료일
	    sql.append("      ,planEndDate36"); // P2납품_계획완료일
	    sql.append("      ,execEndDate36"); // P2납품_실제완료일
	    sql.append("      ,planEndDate37"); // ISIR/PPAP제출_계획완료일
	    sql.append("      ,execEndDate37"); // ISIR/PPAP제출_실제완료일
	    sql.append("      ,planEndDate38"); // 양산이관(금형)_계획완료일
	    sql.append("      ,execEndDate38"); // 양산이관(금형)_실제완료일
	    sql.append("      ,planEndDate39"); // 양산이관(설비)_계획완료일
	    sql.append("      ,execEndDate39"); // 양산이관(설비)_실제완료일
	    sql.append("      ,planEndDate40"); // 양산이관_계획완료일
	    sql.append("      ,execEndDate40"); // 양산이관_실제완료일
	    sql.append("      ,planEndDate41"); // 초도양산_계획완료일
	    sql.append("      ,execEndDate41"); // 초도양산_실제완료일
	    sql.append("      ,planEndDate42"); // 양산최종평가(DR6)_계획완료일
	    sql.append("      ,execEndDate42"); // 양산최종평가(DR6)_실제완료일
	    sql.append("      ,planEndDate43"); // GATE5_계획완료일
	    sql.append("      ,execEndDate43"); // GATE5_실제완료일
	    sql.append("      ,planEndDate44"); // 프로젝트완료_계획완료일
	    sql.append("      ,execEndDate44"); // 프로젝트완료_실제완료일
	    if ("Y".equals(roleExportYN)) {
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT04','U') AS DIE_MOLD_U"); // -- 금형개발(M)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT04','D') AS DIE_MOLD_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT05','U') AS DIE_PRESS_U"); // -- 금형개발(P)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT05','D') AS DIE_PRESS_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT40','U') AS DIE_CHINA_U");// -- 금형개발(中)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT40','D') AS DIE_CHINA_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT15','U') AS ADV_QA_U");// -- 선행품질보증
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT15','D') AS ADV_QA_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT37','U') AS ADV_QA_CHINA_U");// -- 선행품질보증(중)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT37','D') AS ADV_QA_CHINA_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT49','U') AS ADV_QC_U");// -- 선행품질관리
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT49','D') AS ADV_QC_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT12','U') AS QC_ASSY_U");// -- QC(조립)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT12','D') AS QC_ASSY_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT39','U') AS QC_CHINA_U");// -- QC(중)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT39','D') AS QC_CHINA_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT38','U') AS PROD_TECH_CHINA_U");// -- 초류/제조기술(중)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT38','D') AS PROD_TECH_CHINA_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT27','U') AS PRODUCTION_ASSY_U");// --생산(조립)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT27','D') AS PRODUCTION_ASSY_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT25','U') AS PRODUCTION_MOLD_U");// --생산(M)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT25','D') AS PRODUCTION_MOLD_D");
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT33','U') AS PRODUCTION_CHINA_U");// --생산(中)
		sql.append(" ,FN_GET_PJT_ROLE_USER(PJT.IDA2A2,'Team_PRODUCT33','D') AS PRODUCTION_CHINA_D");
	    }
	}

	sql.append("   FROM PRODUCTPROJECT      PJT");
	sql.append("       ,E3PSPROJECTMASTER   E3PSPJTMST");
	sql.append("       ,EXTENDSCHEDULEDATA  SCHEDULE");
	sql.append("       ,OEMPROJECTTYPE      CAR");
	sql.append("       ,MODELPLAN MP");
	sql.append("       ,(SELECT DISTINCT WT.FULLNAME AS USERNM, WT.CLASSNAMEA2A2||':'||WT.IDA2A2 AS USEROID,");
	sql.append("            DEPT.NAME DEPT, DEPT.CLASSNAMEA2A2||':'||DEPT.IDA2A2 AS DEPTOID,  PML.IDA3A5");
	sql.append("           FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT");
	sql.append("          WHERE PML.IDA3B5 = WT.IDA2A2(+)");
	sql.append("            AND WT.IDA2A2 = PE.IDA3B4");
	sql.append("            AND PE.IDA3C4 = DEPT.IDA2A2");
	sql.append("            AND PML.PJTMEMBERTYPE = 0) PDATA");
	sql.append("       ,(SELECT DISTINCT WT.FULLNAME AS USERNM, DEPT.NAME DEPT, PML.IDA3A5");
	sql.append("           FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT");
	sql.append("          WHERE PML.IDA3B5 = WT.IDA2A2(+)");
	sql.append("            AND WT.IDA2A2 = PE.IDA3B4");
	sql.append("            AND PE.IDA3C4 = DEPT.IDA2A2");
	sql.append("            AND PJTROLE = 'Team_PRODUCT22') PDATA2");
	sql.append("        ,(SELECT DISTINCT WT.FULLNAME AS USERNM, DEPT.NAME DEPT, PML.IDA3A5");
	sql.append("           FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT");
	sql.append("          WHERE PML.IDA3B5 = WT.IDA2A2(+)");
	sql.append("            AND WT.IDA2A2 = PE.IDA3B4");
	sql.append("            AND PE.IDA3C4 = DEPT.IDA2A2");
	sql.append("            AND PJTROLE = 'Team_PRODUCT01') PDATA3");
	sql.append("        ,(SELECT DISTINCT WT.FULLNAME AS USERNM, WT.CLASSNAMEA2A2||':'||WT.IDA2A2 AS USEROID,");
	sql.append("            DEPT.NAME DEPT, DEPT.CLASSNAMEA2A2||':'||DEPT.IDA2A2 AS DEPTOID,  PML.IDA3A5");
	sql.append("            FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT");
	sql.append("           WHERE PML.IDA3B5 = WT.IDA2A2(+)");
	sql.append("             AND WT.IDA2A2 = PE.IDA3B4");
	sql.append("             AND PE.IDA3C4 = DEPT.IDA2A2");
	sql.append("             AND PJTROLE = 'Team_PRODUCT09') PDATA4");
	sql.append("        ,(SELECT TO_CHAR(CREATESTAMPA2,'YYYY-MM-DD') AS CREATEDATE,");
	sql.append("                 IDA2A2 ");
	sql.append("            FROM KETDEVELOPMENTREQUEST) DVLREQ");
	sql.append("        ,(SELECT TO_CHAR(COMPLETEDDATE,'YYYY-MM-DD') AS APPROVEDATE,");
	sql.append("                 A.IDA2A2 ");
	sql.append("            FROM KETDEVELOPMENTREQUEST A, KETWFMAPPROVALMASTER B");
	sql.append("           WHERE A.IDA2A2 = B.IDA3B4 OR 'VR:e3ps.dms.entity.KETDevelopmentRequest:'||A.BRANCHIDITERATIONINFO = B.CLASSNAMEKEYA4 AND STATESTATE = 'APPROVED') DVLREQ2");
	sql.append("        ,(SELECT LISTAGG (NAME, ',') WITHIN GROUP (ORDER BY NAME) AS ASSYGB,IDA3A3 ");
	sql.append("            FROM (  SELECT NAME,A.IDA3A3");
	sql.append("                      FROM PRODUCTINFO A, NUMBERCODE B");
	sql.append("                     WHERE A.IDA3B3 = B.IDA2A2");
	sql.append("                       AND B.CODETYPE = 'ASSEMBLEDTYPE'");
	sql.append("                  GROUP BY A.IDA3A3,NAME");
	sql.append("                 ) ");
	sql.append("        GROUP BY IDA3A3) PROINFO");
	sql.append("  WHERE 1=1");
	sql.append("    AND PJT.LASTEST       = 1");
	sql.append("    AND PJT.CHECKOUTSTATE <> 'c/o'");
	sql.append("    AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2");
	sql.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2");
	sql.append("    AND PJT.IDA3D8        = CAR.IDA2A2(+)");
	sql.append("    AND CAR.IDA2A2 = MP.IDA3B3(+)");
	sql.append("    AND PJT.IDA2A2 = PDATA.IDA3A5(+)");
	sql.append("    AND PJT.IDA2A2 = PDATA2.IDA3A5(+)");
	sql.append("    AND PJT.IDA2A2 = PDATA3.IDA3A5(+)");
	sql.append("    AND PJT.IDA2A2 = PDATA4.IDA3A5(+)");
	sql.append("    AND PJT.IDA3I9 = DVLREQ.IDA2A2(+)");
	sql.append("    AND PJT.IDA3I9 = DVLREQ2.IDA2A2(+)");
	sql.append("    AND PJT.IDA2A2 = PROINFO.IDA3A3(+)");
	sql.append("    AND ( PJT.PJTTYPE = '2' )");

	// 검색 PARAMETER
	String[] states = (String[]) reqMap.get("state"); // 상태
	String[] processes = (String[]) reqMap.get("process"); // 개발단계
	String[] devPurposes1 = (String[]) reqMap.get("devPurpose1"); // 개발목적1
	String[] devPurposes2 = (String[]) reqMap.get("devPurpose2"); // 개발목적2
	String pmDeptOid = (String) reqMap.get("pmDeptOid"); // PM담당부서
	String salesDeptOid = (String) reqMap.get("salesDeptOid"); // 영업부서
	String carTypeOid = (String) reqMap.get("carTypeOid"); // 차종
	String pmOid = (String) reqMap.get("pmOid"); // PM
	String salesManagerOid = (String) reqMap.get("salesManagerOid"); // 영업담당자
	String subContractor = (String) reqMap.get("subContractor"); // 접점고객
	String projectNo = (String) reqMap.get("projectNo"); // Project No
	String[] devDateTypes = (String[]) reqMap.get("devDateType"); // 개발시작일//완료일
	String selectStartDate = (String) reqMap.get("selectStartDate"); // 일자(선택)_시작일
	String selectEndDate = (String) reqMap.get("selectEndDate"); // 일자(선택)_종료일

	// 상태
	if (states != null) {
	    String state = StringUtils.join(states, ',');
	    state = state.trim().replaceAll(",", "','");
	    sql.append("    AND STATESTATE IN ('" + state + "')");
	}

	// 개발단계
	if (processes != null) {
	    String process = StringUtils.join(processes, ',');
	    process = process.trim().replaceAll(",", "','");
	    sql.append("    AND (SELECT CODE FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3F9) IN ('" + process + "')");
	}

	// 개발목적1
	if (devPurposes1 != null) {
	    String devPurpose1 = StringUtils.join(devPurposes1, ',');
	    devPurpose1 = devPurpose1.trim().replaceAll(",", "','");
	    sql.append("    AND (SELECT CODE FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3M9) IN ('" + devPurpose1 + "')");
	}

	// 개발목적2
	if (devPurposes2 != null) {
	    String devPurpose2 = StringUtils.join(devPurposes2, ',');
	    devPurpose2 = devPurpose2.trim().replaceAll(",", "','");
	    sql.append("    AND (SELECT CODE FROM NUMBERCODE WHERE IDA2A2 = PJT.IDA3N9) IN ('" + devPurpose2 + "')");
	}

	// PM담당부서
	if (StringUtil.checkString(pmDeptOid)) {
	    pmDeptOid = pmDeptOid.trim().replaceAll(",", "','");
	    sql.append("    AND PDATA.DEPTOID IN ('" + pmDeptOid + "')");
	}

	// 영업부서
	if (StringUtil.checkString(salesDeptOid)) {
	    salesDeptOid = salesDeptOid.trim().replaceAll(",", "','");
	    sql.append("    AND PDATA4.DEPTOID IN ('" + salesDeptOid + "')");
	}

	// 대표차종
	if (StringUtil.checkString(carTypeOid)) {
	    carTypeOid = carTypeOid.trim().replaceAll(",", "','");
	    sql.append("    AND (CAR.CLASSNAMEA2A2||':'||CAR.IDA2A2) IN ('" + carTypeOid + "')");
	}

	// PM
	if (StringUtil.checkString(pmOid)) {
	    pmOid = pmOid.trim().replaceAll(",", "','");
	    sql.append("    AND PDATA.USEROID IN ('" + pmOid + "')");
	}

	// 영업담당자
	if (StringUtil.checkString(salesManagerOid)) {
	    salesManagerOid = salesManagerOid.trim().replaceAll(",", "','");
	    sql.append("    AND PDATA4.USEROID IN ('" + salesManagerOid + "')");
	}
	// 접점고객
	if (StringUtil.checkString(subContractor)) {
	    subContractor = subContractor.trim().replaceAll(",", "','");
	    subContractor = subContractor.trim().replaceAll("e3ps.common.code.NumberCode:", "");
	    sql.append(" AND PJT.IDA2A2 IN (SELECT A.IDA3B5 FROM PROJECTSUBCONTRACTORLINK A WHERE A.IDA3A5 IN ('" + subContractor + "')) ");
	}

	// Project No
	if (StringUtil.checkString(projectNo)) {
	    sql.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJTNO", projectNo, true));
	}

	if (devDateTypes != null) {
	    String devDateType = StringUtils.join(devDateTypes, ',');

	    // 개발시작일 TYPE
	    if (devDateType.indexOf("DEVELOPDATESTART") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS')");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.PLANSTARTDATE <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS')");
		}
	    }

	    if (devDateType.indexOf("DEVELOPDATEEND") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS')");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.PLANENDDATE <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS')");
		}
	    }

	    // 실제시작일 TYPE
	    if (devDateType.indexOf("STARTDATE") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.EXECSTARTDATE >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS')");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.EXECSTARTDATE <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS')");
		}
	    }

	    if (devDateType.indexOf("ENDDATE") >= 0) {

		if (StringUtil.checkString(selectStartDate)) {
		    String startDate = selectStartDate.substring(0, 4) + selectStartDate.substring(5, 7) + selectStartDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.EXECENDDATE >= TO_DATE('" + startDate + "' || '000000','YYYYMMDDHH24MISS')");
		}
		if (StringUtil.checkString(selectEndDate)) {
		    String endDate = selectEndDate.substring(0, 4) + selectEndDate.substring(5, 7) + selectEndDate.substring(8, 10);
		    sql.append("    AND SCHEDULE.EXECENDDATE <  TO_DATE('" + endDate + "' || '235959','YYYYMMDDHH24MISS')");
		}
	    }
	}

	sql.append(") DATA");

	if (!isCount) {
	    // ###################################### 소팅처리 ###########################################
	    Map<String, String> columnMap = getColumnMap(reqMap);

	    String sortKey = StringUtil.checkReplaceStr((String) reqMap.get("sortKey"), "Project_No");
	    String sortName = columnMap.get(sortKey);

	    if (sortKey.startsWith("-")) {
		sortName = columnMap.get(sortKey.substring(1)) + " DESC";
	    } else {
		sortName += " ASC";
	    }

	    sql.append(" ORDER BY " + sortName + " ) ODATA) RDATA");

	    // ################################## 페이징 처리 #####################################################

	    if (isPaging) {
		int formPage = StringUtil.getIntParameter((String) reqMap.get("formPage"), PageControl.FORMPAGE);
		int currentPage = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("page"), "0"));

		int startPos = (((currentPage + 1) - 1) * formPage) + 1;
		String startPosStr = String.valueOf(startPos);

		int endPos = (currentPage + 1) * formPage;
		String endPosStr = String.valueOf(endPos);

		sql.append(" WHERE RDATA.RNUM BETWEEN ").append(startPosStr).append(" AND ").append(endPosStr);
	    }
	}

	return sql;

    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오전 10:49:42
     * @method findPMSchedulePagingList
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> findPMSchedulePagingList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	Map<String, String> columnMap = getColumnMap(reqMap);

	int formPage = StringUtil.getIntParameter((String) reqMap.get("formPage"), PageControl.FORMPAGE);
	int currentPage = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("page"), "0"));
	int totalCount = 0;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();

	    StringBuffer sql = new StringBuffer();

	    // ####################### TOTAL COUNT 조회 ###################################################################
	    sql = getPMScheduleQuery(reqMap, true, false);
	    stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    rs = stat.executeQuery(sql.toString());
	    if (rs.next())
		totalCount = rs.getInt("TOTALCNT");
	    rs.close();

	    // ################################# 페이징 결과값 조회 ########################################################
	    sql = getPMScheduleQuery(reqMap, false, true);

	    rs = stat.executeQuery(sql.toString());

	    KETMessageService ms = KETMessageService.getMessageService();

	    while (rs.next()) {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("oid", rs.getString("OID"));

		Set<String> st = columnMap.keySet();
		Iterator<String> it = st.iterator();

		while (it.hasNext()) {
		    String name = it.next();
		    String key = columnMap.get(name);

		    if ("STATESTATE".equals(key)) {
			String stateKey = rs.getString(key);
			String stateDisplay = State.toState(stateKey).getDisplay(ms.getLocale());
			dataMap.put(name, stateDisplay);
		    } else {
			dataMap.put(name, rs.getString(key));
		    }
		}

		dataList.add(dataMap);
	    }

	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
	PageControl pageControl = new PageControl(currentPage + 1, dataList, formPage, formPage, totalCount);

	return EjsConverUtil.convertToDto(dataList, pageControl);
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 9. 7. 오후 4:38:26
     * @method createPMScheduleListExcel
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> createPMScheduleListExcel(Map<String, Object> reqMap) throws Exception {

	reqMap.put("roleExportYN", (String) reqMap.get("isRole"));
	String colsStr = (String) reqMap.get("cols");
	String protoColsStr = (String) reqMap.get("protoCols");
	boolean isNotProto = Boolean.parseBoolean((String) reqMap.get("isNotProto"));

	ObjectMapper mapper = new ObjectMapper();
	List<Map<String, Object>> cols = mapper.readValue(colsStr, new TypeReference<List<Map<String, Object>>>() {
	});

	if (isNotProto) {
	    List<String> protoCols = mapper.readValue(protoColsStr, new TypeReference<List<String>>() {
	    });

	    for (int i = 0; i < cols.size(); i++) {
		Map<String, Object> col = cols.get(i);
		String key = (String) col.get("key");
		if (protoCols.contains(key))
		    cols.remove(i);
	    }
	}

	Map<String, Object> resMap = new HashMap<String, Object>();

	XSSFWorkbook wb = new XSSFWorkbook();

	Font font = wb.createFont();
	font.setBoldweight((short) 1000);
	CellStyle style = wb.createCellStyle();
	style.setFont(font);
	style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
	style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

	XSSFSheet sheet = wb.createSheet("PROJECT_MAIN_SCHEDULES");
	sheet.setDefaultColumnWidth(12);

	int rowCnt = 0;
	XSSFRow header1 = sheet.createRow(rowCnt++);
	XSSFRow header2 = sheet.createRow(rowCnt++);

	for (int i = 0; i < cols.size(); i++) {

	    Map<String, Object> col = cols.get(i);

	    String label = (String) col.get("label");
	    int colspan = (int) col.get("colspan");
	    if (label.indexOf("연간수량") >= 0)
		label = "연간수량\n(K/1Year)";
	    if (label.indexOf("전체수량") >= 0)
		label = "전체수량\n(K)";
	    if (label.indexOf("SOP-EOP") >= 0)
		label = "SOP-EOP\n(Years)";

	    if (colspan > 0) {
		int spanIdx = i + colspan - 1;

		XSSFCell header1Cell = header1.createCell(i);
		sheet.addMergedRegion(new CellRangeAddress(header1.getRowNum(), header1.getRowNum(), i, spanIdx));
		header1Cell.setCellValue(label);
		header1Cell.setCellStyle(style);

		for (int j = i; j <= spanIdx; j++) {

		    XSSFCell cell = header2.createCell(j);
		    col = cols.get(j + 1);

		    label = (String) col.get("label");

		    cell.setCellValue(label);
		    cell.setCellStyle(style);
		}

		cols.remove(i);
		i = spanIdx;

	    } else {
		XSSFCell cell = header1.createCell(i);
		cell.setCellValue(label);
		cell.setCellStyle(style);
		cell = header2.createCell(i);
		cell.setCellStyle(style);

		sheet.addMergedRegion(new CellRangeAddress(header1.getRowNum(), header2.getRowNum(), i, i));
	    }
	}

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();

	    StringBuffer sql = new StringBuffer();

	    sql = getPMScheduleQuery(reqMap, false, false);
	    stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    rs = stat.executeQuery(sql.toString());

	    Locale loc = SessionHelper.getLocale();
	    DataFormat poiFormat = wb.createDataFormat();

	    style = wb.createCellStyle();
	    style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

	    CellStyle dateStyle = wb.createCellStyle();
	    dateStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    dateStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    dateStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    dateStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);

	    CellStyle numStyle = wb.createCellStyle();
	    numStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    numStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    numStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    numStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);

	    Map<String, String> columnMap = getColumnMap(reqMap);

	    while (rs.next()) {

		XSSFRow row = sheet.createRow(rowCnt++);

		for (int i = 0; i < cols.size(); i++) {

		    Map<String, Object> col = cols.get(i);

		    String name = (String) col.get("key");
		    String key = columnMap.get(name);

		    String value = rs.getString(key);

		    if ("STATESTATE".equals(key)) {
			String stateKey = rs.getString(key);
			value = State.toState(stateKey).getDisplay(loc);
		    }

		    XSSFCell cell = row.createCell(i);
		    cell.setCellStyle(style);

		    if (StringUtil.checkString(value)) {

			try {

			    int num = Integer.parseInt(value);
			    numStyle.setDataFormat(poiFormat.getFormat("0"));
			    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			    cell.setCellStyle(numStyle);
			    cell.setCellValue(num);

			} catch (NumberFormatException ex) {

			    try {

				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
				dateStyle.setDataFormat(poiFormat.getFormat("yyyy-MM-dd"));
				cell.setCellStyle(dateStyle);
				cell.setCellValue(date);

			    } catch (ParseException e) {

				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellStyle(style);
				cell.setCellValue(value);

			    }
			}

		    }
		}
	    }

	    rs.close();

	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	Calendar cal = Calendar.getInstance();
	String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());

	String fileName = "PROJECT_MAIN_SCHEDULE_LIST_" + currentTime + ".xlsx";
	FileOutputStream fos = new FileOutputStream(DownloadView.TEMPPATH + File.separator + fileName);
	wb.write(fos);
	fos.close();

	String downloadLink = "/plm/ext/download?path=/TEMP/" + fileName;
	resMap.put("downloadLink", downloadLink);

	return resMap;
    }

}
