package ext.ket.part.migration.bom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.load.migration.edm.log.LogToFile;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.migration.base.PartInfoUpdateForHompage;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class MultiBomExport implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MultiBomExport manager = new MultiBomExport();

    public MultiBomExport() {

    }

    // windchill ext.ket.part.migration.bom.MultiBomExport D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\migration\partInfo\20170810.xlsx

    public static void main(String[] args) {

	try {

	    String filePath = "";

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		filePath = args[0];
	    }

	    Kogger.debug(MultiBomExport.class, "@start");
	    MultiBomExport.manager.saveFromExcel(filePath);
	    Kogger.debug(MultiBomExport.class, "@end");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	List<Map<String, String>> partList = new ArrayList<Map<String, String>>();
	Map<String, String> partMap = null;
	
	long time = System.currentTimeMillis();

	String logFlie = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo\\multiBom_"+Long.toString(time)+".log";
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    File dataFile = new File(filePath);

	    if (dataFile == null) {
		throw new Exception("@@ 해당 경로에 파일이 존재하지 않습니다 ! [ " + filePath + " ]");
	    }
	    if (DRMHelper.useDrm) {
		dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	    }
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	    int sheetNo = 0;
	    excel.setSheet(sheetNo);
	    int startRowNo = 0;

	    int rowSize = excel.getSheetLastRowNum();

	    System.out.println("partList 추출 Start..");

	    for (int i = startRowNo; i <= rowSize; i++) {
		excel.setRow(i);
		String partNo = excel.getStrValue(0);

		WTPart part = PartBaseHelper.service.getLatestPart(partNo);

		if (part == null) {
		    this.log(partNo + "  는 존재하지 않는  자재번호입니다.", logFlie);
		    continue;
		}
		String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
		String oid = CommonUtil.getOIDLongValue2Str(part);
		partMap = new HashMap<String, String>();
		partMap.put("partType", partType);
		partMap.put("oid", oid);
		partList.add(partMap);
	    }
	    System.out.println("partList 추출 End..");

	    System.out.println("Bom Excel Create Start..");

	    createExcelForBom(partList);

	    System.out.println("Bom Excel Create End..");

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public List<Map<String, Object>> createExcelForBom(List<Map<String, String>> partList) throws Exception {

	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	
	long time = System.currentTimeMillis();
	String filePath = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo\\multiBom_"+Long.toString(time) +".xlsx";

	try {
	    
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("BOM");
	    File file = new File(filePath);
	    FileOutputStream fileOutputStream = new FileOutputStream(file);
	    workbook.write(fileOutputStream);
	    fileOutputStream.close();

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(filePath);

	    int startRow = 0;
	    Row row = excel.getCurrentSheet().createRow(startRow);
	    Cell cell = row.createCell(0);
	    cell.setCellValue("레벨");

	    cell = row.createCell(1);
	    cell.setCellValue("자재번호");

	    cell = row.createCell(2);
	    cell.setCellValue("자재명");

	    cell = row.createCell(3);
	    cell.setCellValue("수량");

	    cell = row.createCell(4);
	    cell.setCellValue("단위");
	    KETBOMQueryBean bean = new KETBOMQueryBean();
	    for (Map<String, String> part : partList) {

		String oid = part.get("oid");
		String partType = part.get("partType");
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM (                                                                                                                                                                                                                      \n");
		sql.append("               SELECT BOM.*,PH.NAME    STATUSKR , '' AS HEADERKEY,  '' AS EONO, '' AS COUTERID, '' AS COUTER FROM                                                                                                                    \n");
		sql.append("               (                                                                                                                                                                                                                     \n");
		sql.append("                SELECT 0 AS NUM,                                                                                                                                                                                                     \n");
		sql.append("                       0 AS LVL,                                                                                                                                                                                                     \n");
		sql.append("                      10 AS ITEMSEQ,                                                                                                                                                                                                 \n");
		sql.append("                    null AS ASSEMBLY_ITEM_OID,                                                                                                                                                                                       \n");
		sql.append("                      '' AS ASSEMBLY_ITEM_CODE,                                                                                                                                                                                      \n");
		sql.append("                      '' AS ASSEMBLY_ITEM_REV,                                                                                                                                                                                       \n");
		sql.append("                      TO_CHAR(A0.IDA2A2) AS COMPONENT_ITEM_OID,                                                                                                                                                                      \n");
		sql.append("                      A0.WTPARTNUMBER AS COMPONENT_ITEM_CODE,                                                                                                                                                                        \n");
		sql.append("                      A0.NAME AS COMPONENT_ITEM_NAME,                                                                                                                                                                                \n");
		sql.append("                      B0.VERSIONIDA2VERSIONINFO AS COMPONENT_ITEM_REV,                                                                                                                      \n");
		sql.append("                     '1.000' AS QTY,  B0.STATESTATE AS STATUS,    B0.IDA3A2STATE,                                                                                                                                                    \n");
		sql.append("                     'KET_EA' AS UNIT, null AS IDA2A2, '' AS STARTDATE, '' AS ENDDATE,  '' AS MATERIAL, '' AS HARDNESSFROM, '' AS HARDNESSTO, '' AS DESIGNDATE, '' AS ICT, '' AS REFTOP, '' AS REFBOTTOM,                            \n");
		sql.append("                      null AS USAGELINKOID,                                                                                                                                                                                          \n");
		sql.append("                     (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO, '' AS PARENTITEMINFO, PTC_STR_150TYPEINFOWTPART AS ECONO, PTC_STR_2TYPEINFOWTPART as NEW_COMPONENT_ITEM_REV                                   \n");
		sql.append("                 FROM WTPARTMASTER A0, WTPART B0                                                                                                                                                                                     \n");
		sql.append("                WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1                                                                                                                                                \n");
		sql.append("                  AND B0.IDA2A2= '"+ oid + "'                                                                                                                                                                                                   \n");
		sql.append("               ) BOM, PHASETEMPLATE PH  ,PHASELINK PL                                                                                                                                                                                \n");
		sql.append("               WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                                                                                                                                                                    \n");
		sql.append("                 AND PL.IDA3B5 = PH.IDA2A2                                                                                                                                                                                           \n");
		sql.append("                 AND PH.PHASESTATE = BOM.STATUS                                                                                                                                                                                      \n");
		sql.append("               UNION                                                                                                                                                                                                                 \n");
		sql.append("              SELECT BOM.*,  PH.NAME    STATUSKR , '' AS HEADERKEY,  '' AS EONO, '' AS COUTERID, '' AS COUTER FROM                                                                                                                   \n");
		sql.append("              (                                                                                                                                                                                                                      \n");
		sql.append("               SELECT ROWNUM AS NUM,                                                                                                                                                                                                 \n");
		sql.append("                      LEVEL AS LVL,                                                                                                                                                                                                  \n");
		sql.append("                      X0.ITEMSEQ,                                                                                                                                                                                                    \n");
		sql.append("                      X0.IDA3A5 AS ASSEMBLY_ITEM_OID,                                                                                                                                                                                \n");
		sql.append("                      X0.PARENTITEMCODE AS ASSEMBLY_ITEM_CODE,                                                                                                                                                                       \n");
		sql.append("                      (SELECT VERSIONIDA2VERSIONINFO FROM WTPART WHERE IDA2A2=X0.IDA3A5) AS ASSEMBLY_ITEM_REV,                                                                                                                       \n");
		sql.append("                      X0.VERSIONITEMCODE AS COMPONENT_ITEM_OID,                                                                                                                                                                      \n");
		sql.append("                      X0.CHILDITEMCODE AS COMPONENT_ITEM_CODE,                                                                                                                                                                       \n");
		sql.append("                      B.NAME AS COMPONENT_ITEM_NAME,                                                                                                                                                                                 \n");
		sql.append("                      A.VERSIONIDA2VERSIONINFO AS COMPONENT_ITEM_REV,                                                                                                                                                                     \n");
		sql.append("                      X0.QUANTITY AS QTY,  A.STATESTATE AS STATUS,    A.IDA3A2STATE AS IDA3A2STATE,                                                                                                                                  \n");
		sql.append("                      X0.UNIT, X0.IDA2A2, X0.STARTDATE, X0.ENDDATE,  X0.MATERIAL, X0.HARDNESSFROM, X0.HARDNESSTO, X0.DESIGNDATE, X0.ICT AS ICT, X0.REFTOP AS REFTOP, X0.REFBOTTOM AS REFBOTTOM,                                      \n");
		sql.append("                      X0.IDA2A2 AS USAGELINKOID,                                                                                                                                                                                     \n");
		sql.append("                     (X0.CHILDITEMCODE || '_' || X0.CHILDITEMVERSION) AS ITEMINFO, '' as PARENTITEMINFO, a.PTC_STR_150TYPEINFOWTPART AS ECONO, PTC_STR_2TYPEINFOWTPART as NEW_COMPONENT_ITEM_REV                                     \n");
		sql.append("                 FROM ( SELECT B.*,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'wrk'  AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID    \n");
		sql.append("                          FROM KETPARTUSAGELINK B ) X0, WTPART A, WTPARTMASTER B                                                                                                                                                     \n");
		sql.append("                         WHERE X0.LAST_WTPART_OID = A.IDA2A2                                                                                                                                                                         \n");
		sql.append("                           AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                                                                                                      \n");
		sql.append("                           START WITH X0.IDA3A5  = '"+ oid + "'                                                                                                                                                                                 \n");
		sql.append("                           CONNECT BY PRIOR X0.LAST_WTPART_OID =  X0.IDA3A5                                                                                                                                                          \n");

		if (!(partType.equals("D") || partType.equals("M")))
		    sql.append("			      ORDER SIBLINGS BY  X0.ITEMSEQ                              															\n");
		// 금형
		else
		    sql.append("                          ORDER SIBLINGS BY  X0.CHILDITEMCODE																		\n");

		sql.append("                              ) BOM , PHASETEMPLATE PH  ,PHASELINK PL                                                                                                                                         \n");
		sql.append("                 WHERE  PL.IDA3A5 = BOM.IDA3A2STATE                                                                                                                                                                                  \n");
		sql.append("                   AND PL.IDA3B5 = PH.IDA2A2                                                                                                                                                                                         \n");
		sql.append("                   AND PH.PHASESTATE = BOM.STATUS                                                                                                                                                                                    \n");
		sql.append(")                                                                                                                                                                                                                                    \n");
		sql.append("   ORDER BY NUM                                                                                                                                                                                                                      \n");

		rs = stat.executeQuery(sql.toString());

		while (rs.next()) {
		    startRow++;
		    row = excel.getCurrentSheet().createRow(startRow);

		    String lvl = rs.getString("LVL") == null ? "" : rs.getString("LVL").trim();
		    String partNo = rs.getString("COMPONENT_ITEM_CODE") == null ? "" : rs.getString("COMPONENT_ITEM_CODE").trim();
		    String partName = rs.getString("COMPONENT_ITEM_NAME") == null ? "" : rs.getString("COMPONENT_ITEM_NAME").trim();
		    String qty = rs.getString("QTY") == null ? "" : rs.getString("QTY").trim();
		    String unit = rs.getString("UNIT") == null ? "" : rs.getString("UNIT").trim();
		    String rev = rs.getString("COMPONENT_ITEM_REV") == null ? "" : rs.getString("COMPONENT_ITEM_REV").trim();

		    unit = StringUtils.removeStart(unit, "KET_");
		    if (lvl.equals("0")) {
			qty = bean.getBoxQty(partNo, rev);
		    }

		    cell = row.createCell(0);
		    cell.setCellValue(lvl);

		    cell = row.createCell(1);
		    cell.setCellValue(partNo);

		    cell = row.createCell(2);
		    cell.setCellValue(partName);

		    cell = row.createCell(3);
		    cell.setCellValue(qty);

		    cell = row.createCell(4);
		    cell.setCellValue(unit);
		}
	    }
	    

	    
	    FileOutputStream fos = new FileOutputStream(filePath);
	    excel.getWorkBook().write(fos);
	    fos.close();

	} catch (Exception e) {
	    throw new Exception(e);
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

	return list;
    }

    public static void log(String msg, String logFlie) {
	try {
	    LogToFile logger = new LogToFile(logFlie, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(PartInfoUpdateForHompage.class, e);
	}
    }
}
