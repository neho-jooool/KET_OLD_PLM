package ext.ket.part.migration.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.material.MoldMaterial;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.migration.ExcelFactory;
import ext.ket.part.migration.ExcelHandle;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartInfoUpdateForHompage implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartInfoUpdateForHompage manager = new PartInfoUpdateForHompage();

    public PartInfoUpdateForHompage() {

    }
    
    // 파라미터 1이면 시트명을 분류체계명으로 부여하며 분류체계 oid를 세팅 , 3이면 부서별로 엑셀파일을 쪼갠다 , 2이면 wtpart 업데이트
    // windchill ext.ket.part.migration.base.PartInfoUpdateForHompage D:\ptc\Windchill_10.2\Windchill\loadFiles\ket\migration\partInfo\delete_wtpart_20171212.xlsx 1
    

    public static void main(String[] args) {

	try {

	    String filePath = null;
	    String gubun = "";

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		filePath = args[0];
		gubun = args[1];
	    }
	    
	    String toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	
	    Kogger.debug(PartInfoUpdateForHompage.class, "@start:" + toDayTime);
	    PartInfoUpdateForHompage.manager.saveFromExcel(filePath, gubun);
	    
	    toDayTime = "";
	    try {
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(PartInfoUpdateForHompage.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(PartInfoUpdateForHompage.class, e);
	}
    }

    public void saveFromExcel(String filePath, String gubun) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { filePath, gubun };

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
	    executeMigration(filePath, gubun);
	}
    }
    
    public void setHiddenColumn(List<KETPartClassAttrLink> linkList, Map<Integer, String> headerMap, Sheet sheet){
	Set<Integer> st = headerMap.keySet();
	Iterator<Integer> it = st.iterator();
	
	while (it.hasNext()) {
	    
	    int index = (Integer)it.next();
	    
	    if (index > 1) {
		String value = (String) headerMap.get(index);

		boolean isHidden = true;

		for (KETPartClassAttrLink link : linkList) {
		    KETPartAttribute attr = link.getAttr();
		    String columnName = attr.getColumnName();
		    
		    if (StringUtils.isNotEmpty(columnName) && columnName.equals(value) && link.isHpYn()) {
			isHidden = false;
			break;
		    }
		}

		if (isHidden) {
		    try{
			
			Row row = sheet.getRow(0);
			
			if(row.getCell(index) != null){
			    
			    String columnName = row.getCell(index).getStringCellValue();
			    
			    if("PTC_STR_174TYPEINFOWTPART".equals(columnName) || "PTC_STR_175TYPEINFOWTPART".equals(columnName) || "PTC_STR_176TYPEINFOWTPART".equals(columnName)){//홈페이지 등록 여부 관련 필드는 삭제안함
				continue;
			    }
			    
			    row.getCell(index).setCellValue("삭제대상열");    
			}
			
		    }catch(Exception e){
			throw e;
		    }
		    
		    //sheet.setColumnHidden(index, true); //cell 숨기기
		}
	    }
	}
    }
    
    public void updateWtpart(Map<Integer, String> headerMap, WTPart wtpart, ExcelHandle excel, String renameFileName) throws Exception {
	
	CellStyle styleOfColor = excel.getCellStyle();
	styleOfColor.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	styleOfColor.setFillForegroundColor(HSSFColor.RED.index);
	styleOfColor.setBorderRight(HSSFCellStyle.BORDER_THIN);
	styleOfColor.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	styleOfColor.setBorderTop(HSSFCellStyle.BORDER_THIN);
	styleOfColor.setBorderBottom(HSSFCellStyle.BORDER_THIN);

	CellStyle styleOfColor2 = excel.getCellStyle();
	styleOfColor2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	styleOfColor2.setFillForegroundColor(HSSFColor.WHITE.index);
	styleOfColor2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	styleOfColor2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	styleOfColor2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	styleOfColor2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	
	String logFlie = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo"+ File.separator + renameFileName+".log";
	
	KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpart);
	
	boolean isErr = false;
	
	WTPartTypeInterface wtPartTypeInterface = null;
	WTPartTypeInfo wtPartTypeInfo = null;
	
	if (claz != null) {

	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    
	    List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, claz);
	    
	    List<Map<String, Object>> codeListAll = this.loadNumberCode(linkList);
	    
	    for (KETPartClassAttrLink link : linkList) {
		
		boolean isUpdate = false;
		
		KETPartAttribute attr = link.getAttr();
		String columnName = attr.getColumnName();
		String codeType = attr.getAttrCodeType();
		String inputType = attr.getAttrInputType();
		String attrName = attr.getAttrName();
		
		if("PTC_STR_174TYPEINFOWTPART".equals(columnName) || "PTC_STR_175TYPEINFOWTPART".equals(columnName) || "PTC_STR_176TYPEINFOWTPART".equals(columnName)){//홈페이지 등록 여부 관련 필드는 무조건 업데이트 대상
		    isUpdate = true;
		}else{
		    isUpdate = link.isHpYn();
		}
		if (!isUpdate) {
		    continue;
		}

		if (StringUtils.isEmpty(columnName)) {
		    continue;
		}

		Set<Integer> st = headerMap.keySet();
		Iterator<Integer> it = st.iterator();

		while (it.hasNext()) {
		    
		    boolean isAttrOk = true;
		    
		    int index = (Integer) it.next();
		    String value = (String) headerMap.get(index);
		    if (columnName.equals(value)) {
			// if ("1".equals(gubun)) {//cell 배경색 지정
			// excel.getCell(index).setCellStyle(styleOfColor);
			// }
			try{
			    excel.getCell(index).setCellStyle(styleOfColor2);    
			}catch(Exception e){
			    continue;
			}
			
			String val = excel.getStrValue(index);
			String oldVal = val;
			
/*			if(StringUtils.isEmpty(val)){
			    if("PTC_STR_174TYPEINFOWTPART".equals(columnName) || "PTC_STR_175TYPEINFOWTPART".equals(columnName) || "PTC_STR_176TYPEINFOWTPART".equals(columnName)){//홈페이지 등록 여부 관련 필드는 무조건 업데이트 대상
				isErr = true;
				this.log(wtpart.getNumber() +" 의 "+ columnName + " 이 입력되지 않았습니다.", logFlie);
			    }
			    continue;
			}*/
			
			if (".".equals(val) || "-".equals(val)) {
			    val = "";
			}
			
			if (StringUtils.isNotEmpty(val)) {
			    
			    if ("SELECT".equals(inputType)) {

				if ("PTC_STR_49TYPEINFOWTPART".equals(value)) {

				    if (StringUtils.isNotEmpty(val)) {

					String temp[] = val.split(" / ");
					val = this.getMaterialOidByName(temp[2]);
					MoldMaterial material = (MoldMaterial) CommonUtil.getObject("e3ps.project.material.MoldMaterial:"
					        + val);

					if (material == null) {
					    isErr = true;
					    isAttrOk = false;
					    this.log(wtpart.getNumber() + " 의 재질(수지) : " + oldVal + " 은 존재하지 않는 코드입니다.", logFlie);
					    excel.getCell(index).setCellStyle(styleOfColor);
					}
				    }

				} else {
				    
				    if ("PTC_STR_45TYPEINFOWTPART".equals(value) || "PTC_STR_46TYPEINFOWTPART".equals(value)) { //TPA , CPA 적용 .. 엑셀을 잘못 배포해서 어쩔수없이 하드코딩
					val = val.toUpperCase();
				    }
				    String code = "";
				    for (Map<String, Object> map : codeListAll) {
					if (codeType.equals(map.get("codeType")) && val.equals(map.get("value"))) {
					    val = (String) map.get("code");
					    code = (String) map.get("code");
					    break;
					}
				    }

				    if (StringUtils.isEmpty(code)) {
					isErr = true;
					isAttrOk = false;
					this.log(wtpart.getNumber() + " 오류 발생 -> " + attr.getAttrName() + " 의 값 " + oldVal+ " 은 존재하지 않는 코드입니다.", logFlie);
					excel.getCell(index).setCellStyle(styleOfColor);
					// throw new Exception(wtpart.getNumber() + " 오류 발생 -> "+ attr.getAttrName() + " 의 값 " +oldVal +
					// " 은 존재하지 않는 코드입니다.");
				    }

				}

			    } else if (StringUtils.contains(attrName, "매칭")) {
				String partNos[] = val.split(",");

				for (String partNo : partNos) {
				    if(StringUtils.isEmpty(partNo)){
					continue;
				    }
				    WTPart matchPart = PartBaseHelper.service.getLatestPart(partNo);
				    if (matchPart == null) {
					isErr = true;
					isAttrOk = false;
					
					String likePartNos = getPartNos(partNo);
					
					this.log(wtpart.getNumber() + " 오류 발생 -> " + attr.getAttrName() + " 의 값 " + partNo+ " 은 존재하지 않는 PART입니다. 유사품번추천 : " + likePartNos, logFlie);
					excel.getCell(index).setCellStyle(styleOfColor);
					// throw new Exception(wtpart.getNumber() + " 오류 발생 -> "+ attr.getAttrName() + " 의 값 " +partNo +
					// " 은 존재하지 않는 PART입니다.");
				    }
				}
			    } 
			}
			
			
			if (isAttrOk) {
			    wtPartTypeInterface = (WTPartTypeInterface) wtpart;
			    wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();
			    BeanUtils.setProperty(wtPartTypeInfo, value.replace("TYPEINFOWTPART", "").toLowerCase(), val);
			}
			
		    }
		}

	    }
	    
//	    Set<Integer> st = headerMap.keySet();
//	    Iterator<Integer> it = st.iterator();
//
//	    while (it.hasNext()) {
//		int index = (Integer) it.next();
//		String value = (String) headerMap.get(index);
//		if("PTC_STR_174TYPEINFOWTPART".equals(value) || "PTC_STR_175TYPEINFOWTPART".equals(value) || "PTC_STR_176TYPEINFOWTPART".equals(value)){//홈페이지 등록 여부 관련 필드는 무조건 업데이트 대상
//		    String val = excel.getStrValue(index);
//		    if (StringUtils.isEmpty(val)) {
//			this.log(wtpart.getNumber() + " 홈페이지 공개 관련 항목이 입력되지 않았습니다.", logFlie);
//			isErr = true;
//			break;
//		    }else{
//			wtPartTypeInterface = (WTPartTypeInterface) wtpart;
//			wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();
//			BeanUtils.setProperty(wtPartTypeInfo, value.replace("TYPEINFOWTPART", "").toLowerCase(), val);
//		    }
//		}
//	    }
	    
	    //if (!isErr) {
//에러가 발생한 항목을 제외하고 일단 update는 진행한다
		PersistenceServerHelper.manager.update(wtpart);

	    //}
		if (!isErr) {
		    excel.getCurrentRow().getCell(0).setCellValue("검증완료");
		}
	    
	}
    }
    
    public List<Map<String, Object>> setTargetPartList() throws Exception{
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	
	List<Map<String, Object>> partList = new ArrayList<Map<String,Object>>();
	Map<String, Object> partMap = null;
	
	String logFlie = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo"+ File.separator + "createExcelErr.log";
	boolean isErr = false;
	
	try{
	    conn = (WTConnection) mContext.getConnection();
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT REAL_WTPARTNUMBER AS partNo FROM KETPART_HP_MIG WHERE REAL_WTPARTNUMBER IS NOT NULL");
	    
	    stat = conn.getConnection().createStatement();

	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {
		String partNo = rs.getString("partNo");
		WTPart part = PartBaseHelper.service.getLatestPart(partNo);
		
		if(part == null){
		    this.log(partNo +" 해당 Part가 존재하지 않습니다.", logFlie);
		    isErr = true;
		    continue;
		    
		}

		KETPartClassification targetClaz = PartClazHelper.service.getPartClassification(part);
		if (targetClaz == null) {
		    this.log(part.getNumber() + " 분류체계가 없습니다.", logFlie);
		    continue;
		}
		String clCode = targetClaz.getCatalogueCode();
		if(StringUtils.isEmpty(clCode)){
		    this.log(part.getNumber() + " 분류체계 " + targetClaz.getClassKrName() + " 은 카달로그코드가 없습니다." , logFlie);
		    isErr = true;
		    
		}
		partMap = new HashMap<String, Object>();
		partMap.put("wtPart", part);
		partMap.put("targetClaz", targetClaz);
		
		partList.add(partMap);
	    }
	    
	}catch(Exception e){
	    e.printStackTrace();
	    throw e;
	}finally{
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	    
	    if(isErr){
		throw new Exception("존재하지 않는 품번 또는 홈페이지 이관 대상 품번 중 카달로그 코드가 존재하지 않는 분류체계가 있습니다. 로그 파일을 확인하세요.");
	    }
	}
	
	return partList;
    }
    
    public String getMaterialOidByName(String grade) throws Exception{
	
	QuerySpec qs = new QuerySpec();
	
	
	int idx = qs.addClassList(MoldMaterial.class, true);
	qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", "수지") , new int[]{idx});
	if(qs.getConditionCount() > 0){
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.GRADE, "=", grade) , new int[]{idx});
	}
	
	QueryResult rs = PersistenceHelper.manager.find(qs);
	String oid = "";
	while(rs.hasMoreElements()){
	    Object[] obj = (Object[]) rs.nextElement();
	    MoldMaterial material = (MoldMaterial)obj[0];
	    oid = CommonUtil.getOIDLongValue2Str(material);
	}
	return oid;
    }
    
    public void createExcelPartList(List<Map<String, Object>> partList, KETPartClassification claz, ExcelHandle excel, List<KETPartClassAttrLink> linkList, Map<Integer, String> headerMap) throws Exception{
	
	int startRow = 2;
	
	CellStyle styleOfBorder = excel.getCellStyle();

	styleOfBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
	styleOfBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	styleOfBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
	styleOfBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	
	List<Map<String, Object>> codeListAll = this.loadNumberCode(linkList);
	
	for (Map<String, Object> obj : partList) {

	    WTPart part = (WTPart) obj.get("wtPart");
	    KETPartClassification targetClaz = (KETPartClassification) obj.get("targetClaz");

	    if (CommonUtil.getOIDString(targetClaz).equals(CommonUtil.getOIDString(claz))) {

		Row row = excel.getCurrentSheet().createRow(startRow);
		excel.setRow(startRow);
		Cell cell = row.createCell(0);
		cell.setCellStyle(styleOfBorder);
		cell.setCellValue(part.getNumber());

		cell = row.createCell(1);
		cell.setCellStyle(styleOfBorder);
		cell.setCellValue(part.getName());

		for (KETPartClassAttrLink link : linkList) {

		    KETPartAttribute attr = link.getAttr();
		    String columnName = attr.getColumnName();
		    String codeType = attr.getAttrCodeType();
		    String inputType = attr.getAttrInputType();

		    boolean isUpdate = false;

		    if ("PTC_STR_174TYPEINFOWTPART".equals(columnName) || "PTC_STR_175TYPEINFOWTPART".equals(columnName)
			    || "PTC_STR_176TYPEINFOWTPART".equals(columnName)) {// 홈페이지 등록 여부 관련 필드는 무조건 업데이트 대상
			isUpdate = true;
		    } else {
			isUpdate = link.isHpYn();
		    }

		    if (!isUpdate) {
			continue;
		    }

		    if (StringUtils.isEmpty(columnName)) {
			continue;
		    }

		    Set<Integer> st = headerMap.keySet();
		    Iterator<Integer> it = st.iterator();

		    while (it.hasNext()) {
			int index = (Integer) it.next();
			String value = (String) headerMap.get(index);
			if (columnName.equals(value)) {

			    WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) part;
			    WTPartTypeInfo wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

			    String val = StringUtil.checkNull(BeanUtils.getProperty(wtPartTypeInfo, value.replace("TYPEINFOWTPART", "")
				    .toLowerCase()));

			    if ("SELECT".equals(inputType)) {

				if ("PTC_STR_49TYPEINFOWTPART".equals(value)) {

				    if (StringUtils.isNotEmpty(val)) {
					NumberCode num = null;

					String maker = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterMaker);
					num = NumberCodeHelper.manager.getNumberCode("MATERIALMAKER", maker);
					if (num != null) {
					    maker = num.getName();
					}

					String type = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpMaterType);

					num = NumberCodeHelper.manager.getNumberCode("MATERIALTYPE", type);
					if (num != null) {
					    type = num.getName();
					}

					String grade = PartBaseHelper.service.getMaterialInfo(val);
					val = maker + " / " + type + " / " + grade;
				    }

				} else {
				    for (Map<String, Object> map : codeListAll) {
					if (codeType.equals(map.get("codeType")) && val.equals(map.get("code"))) {
					    val = (String) map.get("value");
					    break;
					}
				    }
				}

			    }
			    cell = row.createCell(index);
			    cell.setCellStyle(styleOfBorder);
			    cell.setCellValue(val);
			}
		    }

		}
		startRow++;
	    }
	    
	}
    }
    
    public List<Map<String, Object>> loadNumberCode(List<KETPartClassAttrLink> linkList) throws Exception{
	
	List<Map<String, Object>> codeListAll = new ArrayList<Map<String,Object>>();
	
	for (KETPartClassAttrLink link : linkList) {
	    
	    String columnName = link.getAttr().getColumnName();
	    
	    boolean goFlag = false;
	    
	    if("PTC_STR_174TYPEINFOWTPART".equals(columnName) || "PTC_STR_175TYPEINFOWTPART".equals(columnName) || "PTC_STR_176TYPEINFOWTPART".equals(columnName)){//홈페이지 등록 여부 관련 필드는 무조건 업데이트 대상
		goFlag = true;
	    }else{
		goFlag = link.isHpYn();
	    }
	    
	    if (goFlag) {
		KETPartAttribute attr = link.getAttr();
		if (StringUtils.isNotEmpty(attr.getColumnName()) && "SELECT".equals(attr.getAttrInputType())) {
		    Map<String, Object> parameter = new HashMap<String, Object>();
		    String codeType = attr.getAttrCodeType();
		    parameter.put("codeType", codeType);
		    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
		    
		    for (Map<String, Object> map : codeList) {
			codeListAll.add(map);
		    }
		}
	    }
	}
	
	return codeListAll;
    }
    
    
    public void getPartClazList(List<Map<String, String>> clazList) throws Exception{
	
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	
	try{
	    conn = (WTConnection) mContext.getConnection();
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT CLASSCODE,CLASSKRNAME,SUBSTR(SYS_CONNECT_BY_PATH(CLASSKRNAME,'|'), (SELECT LENGTH(C.CLASSKRNAME)  FROM KETPartClassification C WHERE IDA3A3 = 0) + 3) AS TREEFULLPATH,'ext.ket.part.entity.KETPartClassification:'||ida2a2 as oid ");
	    sql.append("FROM KETPARTCLASSIFICATION WHERE CATALOGUECODE IS NOT NULL AND USECLAZ = 1 AND CONNECT_BY_ISLEAF = 1                                                                                                                                                               ");
	    sql.append("START WITH IDA3A3 = 0                                                                                                                                                                                                                    ");
	    sql.append("CONNECT BY PRIOR IDA2A2 = IDA3A3                                                                                                                                                                                                         ");
	    sql.append("ORDER SIBLINGS BY SORTNO "); 
	    
	    stat = conn.getConnection().createStatement();

	    rs = stat.executeQuery(sql.toString());
	    Map<String, String> clzMap = null;
	    while (rs.next()) {
		clzMap = new HashMap<String, String>();
		clzMap.put("CLASSCODE", rs.getString("CLASSCODE"));
		clzMap.put("OID", rs.getString("OID"));
		clazList.add(clzMap);
	    }
	    
	}catch(Exception e){
	    
	}finally{
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
    }
    
    
    public String getPartNos(String partNo) throws Exception{
	
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	String partNos = "";
	try{
	    conn = (WTConnection) mContext.getConnection();
	    
	    StringBuffer sql = new StringBuffer();
	    sql.append(" SELECT WTPARTNUMBER                                                  ");
	    sql.append(" FROM WTPARTMASTER M                                                  ");
	    sql.append("    , WTPART P                                                        ");
	    sql.append(" ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO ");
	    sql.append("    FROM WTPART I, WTPARTMASTER J                                     ");
	    sql.append("    WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                            ");
	    sql.append("         AND I.LATESTITERATIONINFO = 1                                ");
	    sql.append("      AND I.STATECHECKOUTINFO != 'wrk'                                ");
	    sql.append("      AND PTC_STR_3TYPEINFOWTPART NOT IN ('D','M')                    ");
	    sql.append("    GROUP BY J.IDA2A2                                                 ");
	    sql.append(" ) MAXVERPART                                                         ");
	    sql.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                               ");
	    sql.append(" AND P.LATESTITERATIONINFO = 1                                        ");
	    sql.append(" AND P.STATECHECKOUTINFO NOT IN ('wrk')                               ");
	    sql.append(" AND M.IDA2A2 = MAXVERPART.IDA2A2                                     ");
	    sql.append(" AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO       ");
	    sql.append(" AND PTC_STR_3TYPEINFOWTPART IN ('H','F','W')                         ");
	    sql.append(" AND WTPARTNUMBER LIKE '%" + partNo + "%'");
	   
	    
	    stat = conn.getConnection().createStatement();

	    rs = stat.executeQuery(sql.toString());
	    
	    while (rs.next()) {
		partNos += rs.getString("WTPARTNUMBER")+",";
	    }
	    
	    partNos = StringUtils.removeEnd(partNos, ",");
	    
	}catch(Exception e){
	    
	}finally{
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
	
	return partNos;
    }
    
    public void SetSheetName(ExcelHandle excel) throws Exception

    {
	List<Map<String, String>> clazList = new ArrayList<Map<String,String>>();
	this.getPartClazList(clazList);
	
	int cnt = 0;
	Workbook xlWorkBook = excel.getWorkBook();
	
	for(Map<String, String> map : clazList){
	    String classCode = map.get("CLASSCODE");
	    String oid = map.get("OID");
	    
	    
	    excel.setSheet(cnt);
	    excel.setRow(0);
	    Cell cell = excel.getCurrentRow().createCell(0);
	    cell.setCellValue(oid);
	    xlWorkBook.setSheetName(cnt, classCode);
	    cnt++;
	}
    }
    
    public void removeOtherSheets(String sheetName, Workbook book) {       
        for(int i=book.getNumberOfSheets()-1;i>=0;i--){
            Sheet tmpSheet =book.getSheetAt(i);
            if(tmpSheet.getSheetName().equals(sheetName)){
                book.removeSheetAt(i);
            }
        }       
    }
    
    
    public static void log(String msg, String logFlie) {
	try {
	    LogToFile logger = new LogToFile(logFlie, true);
	    logger.log(msg);
	} catch (IOException e) {
	    Kogger.error(PartInfoUpdateForHompage.class, e);
	}
    }

    public void executeMigration(String filePath, String gubun) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	
	String logFlie = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo"+ File.separator + "updateWtPartErr.log";
	
	try {

	    SessionHelper.manager.setAdministrator();
	    
	    trx = new Transaction();
	    trx.start();
	    
	    File dataFile = new File(filePath);
	    if(DRMHelper.useDrm){
		dataFile = DRMHelper.Decryptupload(dataFile, dataFile.getName());
	    }
	    
	    ExcelHandle excel = ExcelFactory.getInstance().getExcelManager(dataFile);
	    
	    List<Map<String, Object>> partList = null;
	    if("1".equals(gubun)){
		partList = this.setTargetPartList();
	    }
	    
	    
	    if("3".equals(gubun)){//분류체계를 담당부서 기준으로 그룹핑하여 부서별 엑셀 파일로 쪼갠다
		String deptFilePath = "D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo\\부서.xlsx";
		
		File DeptDataFile = new File(deptFilePath);
		if (DRMHelper.useDrm) {
		    DeptDataFile = DRMHelper.Decryptupload(DeptDataFile, DeptDataFile.getName());
		}
		
		ExcelHandle deptExcel = ExcelFactory.getInstance().getExcelManager(DeptDataFile);
		
		deptExcel.setSheet(0);
		
		int rowSize = deptExcel.getSheetLastRowNum();
		String deptNames = "";
		for (int i = 0; i <= rowSize; i++) {
		    deptExcel.setRow(i);
		    deptNames += deptExcel.getStrOrgValue(1)+",";
		}
		deptNames = StringUtils.removeEnd(deptNames, ",");
		deptNames = StringUtil.removeDuplicateStringToken(deptNames, ",");
		
		String targetDeptNames[] = deptNames.split(",");
		
		for(String deptName : targetDeptNames){
		    
		    excel = ExcelFactory.getInstance().getExcelManager(dataFile);
		    
		    for (int i = 0; i <= rowSize; i++) {
			deptExcel.setRow(i);
			
			String deleteSheetName = deptExcel.getStrOrgValue(0);
			String deleteDept = deptExcel.getStrOrgValue(1);
			
			if(!deptName.equals(deleteDept)){
			    this.removeOtherSheets(deleteSheetName, excel.getWorkBook());
			}
		    }
		    
		    FileOutputStream fos = new FileOutputStream("D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo"+ File.separator + deptName + ".xlsx");
		    excel.getWorkBook().write(fos);
		    fos.close();
		    
		}

	    } else {
		if ("1".equals(gubun)) {
		    this.SetSheetName(excel); // 시트명 변경 및 oid 세팅
		}


		int j = dataFile.getName().lastIndexOf(".");
		String renameFileName = dataFile.getName().substring(0, j);

		int sheetRow = excel.getWorkBook().getNumberOfSheets() - 1;

		if (sheetRow == 0) {
		    sheetRow = 1;
		}

		for (int k = 0; k < sheetRow; k++) {

		    excel.setSheet(k);

		    int startRowNo = 2;
		    excel.setRow(startRowNo);
		    int rowSize = excel.getSheetLastRowNum();
		    String partNo = "";

		    Map<Integer, String> headerMap = new HashMap<Integer, String>();

		    excel.setRow(0);

		    int cellCnt = excel.getRow(0).getPhysicalNumberOfCells() + 1;

		    for (int r = 0; r <= cellCnt; r++) {

			headerMap.put(r, excel.getStrValue(r));
		    }
		    
		    String sheetName = excel.getSheet(k).getSheetName();
		    
		    if ("1".equals(gubun)) {

			KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(excel.getStrOrgValue(0));
			
			if (claz != null) {
			    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
			    List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class,KETPartClassification.class, claz);

			    Kogger.debug(PartInfoUpdateForHompage.class, "Create ExcelSheet Start .. ClassCode :" + sheetName);
			    
			    this.createExcelPartList(partList, claz, excel, linkList, headerMap);
			    this.setHiddenColumn(linkList, headerMap, excel.getSheet(k));
			    
			    Kogger.debug(PartInfoUpdateForHompage.class, "Create ExcelSheet End .. ClassCode :" + sheetName);
			}
			

		    } else if ("2".equals(gubun)) {
			
			for (int i = startRowNo; i <= rowSize; i++) {
			    excel.setRow(i);
			    
			    
			    Kogger.debug(PartInfoUpdateForHompage.class, "Update WtPart Start .. ClassCode :" + sheetName);
			    
			    try{
				partNo = excel.getStrOrgValue(0);
			    }catch(Exception e){
				this.log("partNo가 입력되지 않은 cell입니다. " + sheetName + " 의 "+ i +" 번째 행" , logFlie);
			    }
			    
			    if (StringUtils.isEmpty(partNo))
				continue;

			    WTPart wtpart = PartBaseHelper.service.getLatestPart(partNo);
			    
			    if(wtpart == null){
				this.log(partNo + " 는 존재하지 않습니다.", logFlie);
				continue;
			    }
			    
			    this.updateWtpart(headerMap, wtpart, excel, renameFileName);

			    Kogger.debug(PartInfoUpdateForHompage.class, "Update WtPart End .. ClassCode :" + sheetName);
			}
			
//			for (int i = startRowNo; i <= rowSize; i++) {
//			    
//			    excel.setRow(i);
//			    
//			    try{
//				String removeRow = excel.getStrOrgValue(0);
//				if("삭제대상".equals(removeRow)){
//				    excel.getCurrentSheet().removeRow(excel.getCurrentRow());
//				}
//			    }catch(Exception e){
//				
//			    }
//			}
//			System.out.println("해당 sheet의 row를 재정렬 Start "+excel.getCurrentSheet().getSheetName());
//			deleteEmptyRow(excel.getCurrentSheet());
//			System.out.println("해당 sheet의 row를 재정렬 End "+excel.getCurrentSheet().getSheetName());

		    }
		}

		if ("1".equals(gubun) || "2".equals(gubun)) {
		    FileOutputStream fos = new FileOutputStream("D:\\ptc\\Windchill_10.2\\Windchill\\loadFiles\\ket\\migration\\partInfo"+ File.separator + renameFileName + "_.xlsx");
		    excel.getWorkBook().write(fos);
		    fos.close();
		}
	    }
	    
	    
	    
	    trx.commit();
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    throw e;
	} finally {
	    
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    public void deleteEmptyRow(Sheet sheet) {
	for (int i = 0; i < sheet.getLastRowNum(); i++) {
	    boolean isRowEmpty = false;
	    if (sheet.getRow(i) == null) {
		isRowEmpty = true;
		sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
		i--;
		continue;
	    }
	    for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
		if (sheet.getRow(i) != null && sheet.getRow(i).getCell(j) != null) {
		    if (sheet.getRow(i).getCell(j).toString().trim().equals("")) {
			isRowEmpty = true;
		    } else {
			isRowEmpty = false;
			break;
		    }
		}
	    }
	    if (isRowEmpty == true) {
		sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
		i--;
	    }
	}
    }
    
    

}
