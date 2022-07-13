package ext.ket.part.replacePart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartInfo;
import ext.ket.part.entity.RivalPartInfo;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;

public class ReplacePartUtil {
    public static final ReplacePartUtil manager = new ReplacePartUtil();
    
    public void syncKETPartInfo(Map<String, Object> reqMap) throws Exception{
	
	String partNo = (String)reqMap.get("partNo");
	WTPart wtpart = PartBaseHelper.service.getLatestPart(partNo);
	KETPartInfo KETPart = null;
	
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETPartInfo.class, true);

	qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.KET_PART_NO, SearchCondition.EQUAL, partNo),new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    KETPart = (KETPartInfo) o[0];
	    if(wtpart == null){
		continue;
	    }
	    String SpWaterProof = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpWaterProof);// 방수여부
            String SpNoOfPole = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpNoOfPole);// 극수
            String spMTerminal = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMTerminal);// 매칭터미널
            String spMConnector = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMConnector);// 매칭커넥터
            String spSeries = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpSeries);// 시리즈
            NumberCode num = CodeHelper.manager.getNumberCode("SEALED", SpWaterProof);
            String sealed = "";
            if(num != null){
        	sealed = num.getName();
            }
            String KetSeries = "";
            num = CodeHelper.manager.getNumberCode("SPECSERIES", spSeries);
            if(num != null){
        	KetSeries = num.getName();
            }
            
            KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpart);
            
            if(StringUtils.isNotEmpty(spMTerminal)){

        	spMTerminal = spMTerminal.replaceAll(",", System.getProperty("line.separator"));
            }
            
            if(StringUtils.isNotEmpty(spMConnector)){

        	spMConnector = spMConnector.replaceAll(",", System.getProperty("line.separator"));
            }
            
            KETPart.setKetPartName(wtpart.getName());
            KETPart.setKetWaterProof(sealed);
            KETPart.setClassification(claz.getClassKrName());
            KETPart.setKetPole(SpNoOfPole);
            KETPart.setKetMatchConnector(spMConnector);
            KETPart.setKetMatchTerminal(spMTerminal);
            KETPart.setKetSeries(KetSeries);
            KETPart.setOwner(SessionHelper.manager.getPrincipalReference());
            KETPart = (KETPartInfo)PersistenceHelper.manager.save(KETPart);

	}
    }
    
    
    public RivalPartInfo getRivalPart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	String partNo = (String)reqMap.get("partNo");
	
	RivalPartInfo part = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(RivalPartInfo.class, true);

	qs.appendWhere(new SearchCondition(RivalPartInfo.class, RivalPartInfo.PART_NO, SearchCondition.EQUAL, partNo),new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    part = (RivalPartInfo) o[0];
	}
	
	return part;
    }

    
    public KETPartInfo getKETPart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	
	String partNo = (String)reqMap.get("partNo");
	String rivalPartOid = (String)reqMap.get("rivalPartOid");
	
	RivalPartInfo rivalPart = (RivalPartInfo)CommonUtil.getObject(rivalPartOid);
	long longOid = 0;
	if(rivalPart != null){
	    longOid = CommonUtil.getOIDLongValue(rivalPart);    
	}
	
	
	KETPartInfo part = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETPartInfo.class, true);

	qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.KET_PART_NO, SearchCondition.EQUAL, partNo),new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.RIVAL_PART_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, longOid), new int[] { idx });
	
	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    part = (KETPartInfo) o[0];
	}
	
	return part;
    }
    
    public QuerySpec getReplacePartQuery(Map<String, Object> reqMap) throws Exception {
	
	
	String partNoStr = StringUtil.checkNull((String) reqMap.get("partNoList"));
	
	String dataType = (String) reqMap.get("DATATYPE");
	
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> partNoList = new ArrayList<Map<String,Object>>();
        String partNos[] = null;
        
        if(StringUtils.isNotEmpty(partNoStr)){
            partNoList = mapper.readValue(partNoStr, new TypeReference<List<Map<String, Object>>>() {});
            partNos = new String[partNoList.size()];
        }
        
        for(int i = 0; i < partNoList.size(); i++) {
            Map<String, Object> data = partNoList.get(i);
            String partNo = (String) data.get("partNo");
            partNos[i] = partNo;
        }

	QuerySpec qs = new QuerySpec();
	
	int idx = qs.appendClassList(KETPartInfo.class, true);
	int idx2 = qs.appendClassList(RivalPartInfo.class, true);
	qs.setAdvancedQueryEnabled(true);

	ClassAttribute ca0 = new ClassAttribute(KETPartInfo.class, KETPartInfo.RIVAL_PART_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID);
	ClassAttribute ca1 = new ClassAttribute(RivalPartInfo.class, WTAttributeNameIfc.ID_NAME);
	SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	if (!"REPLACEPARTLISTPOPUP".equals(dataType)) {
	    sc0.setOuterJoin(SearchCondition.LEFT_OUTER_JOIN);
	}

	qs.appendWhere(sc0, new int[] { idx, idx2 });

	
	if (partNoList.size() > 0 && qs.getConditionCount() > 0){
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(RivalPartInfo.class, RivalPartInfo.PART_NO, partNos, true, false), new int[] { idx2 });
	}else{
	    
	    String partNo = (String)reqMap.get("partNo");
	    
	    if ("REPLACEPARTLISTPOPUP".equals(dataType)) {
		if(StringUtils.isEmpty(partNo)){
		    partNo = " ";    
		}
		SearchUtil.appendEQUAL(qs, RivalPartInfo.class, RivalPartInfo.PART_NO, partNo, idx2);
	    } else {
		if (StringUtils.isNotEmpty(partNo) && qs.getConditionCount() > 0) {

		    SearchUtil.appendLIKE(qs, RivalPartInfo.class, RivalPartInfo.PART_NO, partNo, idx2);

		}
	    }
	}
	
	String[] rivals = (String[])reqMap.get("rival");
	if (rivals != null) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(RivalPartInfo.class, RivalPartInfo.COMPANY_CODE, rivals, true, false), new int[] { idx2 });
	}
	
	String[] replaceGbs = (String[])reqMap.get("replaceGb");
	
	boolean isNoneRelace = false;
	
	if (replaceGbs != null) {
	    for (String replace : replaceGbs) {
		if ("X".equals(replace)) {
		    isNoneRelace = true;
		}
	    }
	}
	
	if (replaceGbs != null) {
	    if (qs.getConditionCount() > 0){
		qs.appendAnd();
		if(isNoneRelace){
		    qs.appendOpenParen();
		}
		
		qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.REPLACE_GB, replaceGbs, true, false), new int[] { idx });
	    }
	    
	    if (qs.getConditionCount() > 0 && isNoneRelace){
		qs.appendOr();
		qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.REPLACE_GB, SearchCondition.IS_NULL, true), new int[] { idx });
		qs.appendCloseParen();
	    }
	    
	    
	}
	
	    
	setSort(reqMap, qs);
	
	return qs;
    }
    
    public void setSort(Map<String, Object> reqMap, QuerySpec qs) throws Exception{
	if (!StringUtil.isTrimToEmpty((String)reqMap.get("sortName"))) {
	    String checkName = (String) reqMap.get("sortName");
	    int tableNo = 1;
	    Class<?> soringClass = RivalPartInfo.class;

	    if ( checkName.endsWith("replaceGb") || checkName.endsWith("ketPartNo") ) {
		soringClass = KETPartInfo.class;
		tableNo = 0;
	    }
	    
	    if(checkName.endsWith("pole")){
		
		OrderBy orderby = null;
		
		SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, new ClassAttribute(RivalPartInfo.class, "pole"));
		if (checkName.startsWith("-")) {
		    orderby = new OrderBy(sqlfunction, true);    
		}else{
		    orderby = new OrderBy(sqlfunction, false);
		}
		
		qs.appendOrderBy(orderby, new int[] { tableNo });
		
	    }
	    
/*	    else if(checkName.endsWith("replaceGb")){
		
		OrderBy orderby = null;
		
		
		ColumnExpression NULL = ConstantExpression.newExpression("");
		ColumnExpression X = ConstantExpression.newExpression("X");
		SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.DECODE, new ColumnExpression[] { new ClassAttribute(KETPartInfo.class,"replaceGb"), NULL, X , new ClassAttribute(KETPartInfo.class,"replaceGb")});
				
		if (checkName.startsWith("-")) {
		    orderby = new OrderBy(sqlFunction, true);    
		}else{
		    orderby = new OrderBy(sqlFunction, false);
		}
		
		qs.appendOrderBy(orderby, new int[] { tableNo });
	    }*/
	    
	    if (!checkName.endsWith("pole")) {
		// '-'내림차순
		if (checkName.startsWith("-")) {
		    checkName = StringUtils.remove(checkName, "-");
		    SearchUtil.setOrderBy(qs, soringClass, tableNo, checkName, checkName, true);
		} else {
		    SearchUtil.setOrderBy(qs, soringClass, tableNo, checkName, checkName, false);
		}
	    }

	    
	    
	}else{
	    SearchUtil.setOrderBy(qs, RivalPartInfo.class, 1, "partNo", "partNo", true);
	}
    }
    
    public void DataMapSet(Map<String, Object> dataMap, String key, String dataType) throws Exception{
	String imgUrl = "/plm/replacePart/PARTIMG/" + (String)dataMap.get(key) + ".jpg?" + System.currentTimeMillis();
        
        File copyImg = new File(ReplacePartImgUtil.STOREPATH + (String)dataMap.get(key) + ".jpg");
        
        if (!copyImg.exists()) {
            dataMap.put(key+"partForm", "");
        }else{
            dataMap.put(key+"partForm", "<img src='" + imgUrl + "' style='width:80px;height:40px;margin:0;' />");    
        }
        if(key.equals("partNo") && (CommonUtil.isMember("대체품관리") || CommonUtil.isAdmin())){
            dataMap.put("partNoButton", "/plm/portal/images/icon_5.png");
            dataMap.put("partNoOnClickSideButton", "javasciprt:replacePart.viewRivalPart(Row);");    
        }
        
//        if(dataType.equals("REPLACEPARTLISTPOPUP") && key.equals("ketPartNo")){
//            
//            dataMap.put("ketPartNoIcon", "/plm/portal/images/icon_5.png");
//            dataMap.put("ketPartNoIconAlign", "Right");
//            dataMap.put("ketPartNoOnClickSideIcon", "javascript:openPartPopup(Row);");  
//        }
    }
    
    
    
    public Map<String, Object> createReplacePartListExcel(Map<String, Object> reqMap) throws Exception {

	String colsStr = (String) reqMap.get("cols");

	ObjectMapper mapper = new ObjectMapper();
	List<Map<String, Object>> cols = mapper.readValue(colsStr, new TypeReference<List<Map<String, Object>>>() {
	});

	Map<String, Object> resMap = new HashMap<String, Object>();

	XSSFWorkbook wb = new XSSFWorkbook();

	Font font = wb.createFont();
	font.setBoldweight((short) 1000);
	CellStyle style = wb.createCellStyle();
	style.setFont(font);
	style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	style.setWrapText(true);

	XSSFSheet sheet = wb.createSheet("CONNECTOR_REPLACE_LIST");
	sheet.setDefaultColumnWidth(12);

	int rowCnt = 0;
	XSSFRow header1 = sheet.createRow(rowCnt++);
	XSSFRow header2 = sheet.createRow(rowCnt++);

	for (int i = 0; i < cols.size(); i++) {

	    Map<String, Object> col = cols.get(i);

	    String label = (String) col.get("label");
	    
	    if(label.indexOf("Male") >= 0) label = "Male\r\nFemale";
            if(label.indexOf("극수") >= 0) label = "극수";
            if(label.indexOf("Terminal") >= 0) label = "Terminal\r\nPartNo";
            if(label.indexOf("Replace") >= 0) label = "Replace";

	    XSSFCell cell = header1.createCell(i);
	    cell.setCellValue(label);
	    cell.setCellStyle(style);
	    cell = header2.createCell(i);
	    cell.setCellStyle(style);

	    sheet.addMergedRegion(new CellRangeAddress(header1.getRowNum(), header2.getRowNum(), i, i));
	}

	
	DataFormat poiFormat = wb.createDataFormat();

	style = wb.createCellStyle();
	style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	style.setWrapText(true);

	CellStyle dateStyle = wb.createCellStyle();
	dateStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	dateStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	dateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	dateStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

	CellStyle numStyle = wb.createCellStyle();
	numStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	numStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	numStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	numStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	
	QueryResult qr = PersistenceHelper.manager.find(this.getReplacePartQuery(reqMap));
	
	sheet.setColumnWidth(3, 3256);
	sheet.setColumnWidth(12, 3256);
	
	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Persistable obj = null;
		Map<String, Object> dataMap = null;
		Object[] o = (Object[]) qr.nextElement();
		obj = (Persistable) o[0];
		if(obj != null){
		    dataMap = ObjectUtil.manager.converObjectToMap(obj);    
		}
		obj = (Persistable) o[1];
		ObjectUtil.manager.converObjectToMap2(obj, dataMap);

		XSSFRow row = sheet.createRow(rowCnt++);
		String partNo = (String) dataMap.get("partNo");
		String ketPartNo = (String) dataMap.get("ketPartNo");
		
		for (int i = 0; i < cols.size(); i++) {
		    Map<String, Object> col = cols.get(i);
		    String key = (String) col.get("key");
		    String value = (String) dataMap.get(key);
		    
		    if("replaceGb".equals(key) && StringUtils.isEmpty(value)){
			value = "X";
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
		    		    
//		    if (key.equals("partNopartForm")) {
//			InputStream inputStream = new FileInputStream("D:/ptc/Windchill_10.2/Windchill/codebase/replacePart/PARTIMG/187.jpg");
//
//			byte[] bytes = IOUtils.toByteArray(inputStream);
//			int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
//			inputStream.close();
//
//			XSSFCreationHelper helper = wb.getCreationHelper();
//			XSSFDrawing drawing = sheet.createDrawingPatriarch();
//			XSSFClientAnchor anchor = helper.createClientAnchor();
//
//			// 이미지를 출력할 CELL 위치 선정
//			anchor.setCol1(i);
//			anchor.setRow1(row.getRowNum() + pictureIdx);
//
//			// 이미지 그리기
//			XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
//
//			// 이미지 사이즈 비율 설정
//			pict.resize();
//
//			inputStream.close();
//		    }
		    
		}
		row.setHeight((short)1000);
		
		File PartImg = new File(ReplacePartImgUtil.STOREPATH + partNo + ".jpg");
		createImg(PartImg, wb, sheet, 3, row.getRowNum(),742950,600075);
		

		File KetPartImg = new File(ReplacePartImgUtil.STOREPATH + ketPartNo + ".jpg");
		createImg(KetPartImg, wb, sheet, 12, row.getRowNum(),800100,600075);
		
	    }
	}
	
	
	Calendar cal = Calendar.getInstance();
	String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());

	String fileName = "CONNECTOR_REPLACE_LIST" + currentTime + ".xlsx";
	FileOutputStream fos = new FileOutputStream(DownloadView.TEMPPATH + File.separator + fileName);
	wb.write(fos);
	fos.close();

	String downloadLink = "/plm/ext/download?path=/TEMP/" + fileName;
	resMap.put("downloadLink", downloadLink);

	return resMap;
    }
    
    public static void createImg(File file, XSSFWorkbook wb, XSSFSheet sheet, int col, int row, int dx2, int dy2) throws Exception{
	if(!file.exists()){
	    return;
	}
	InputStream inputStream = new FileInputStream(file);
	
	byte[] bytes = IOUtils.toByteArray(inputStream);
	
	XSSFCreationHelper helper = wb.getCreationHelper();
	XSSFDrawing drawing = sheet.createDrawingPatriarch();
	XSSFClientAnchor anchor = helper.createClientAnchor();
	anchor.setAnchorType(ClientAnchor.MOVE_DONT_RESIZE );
	
	int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
	inputStream.close();
	
	// 이미지를 출력할 CELL 위치 선정


	anchor.setCol1(col);
	anchor.setRow1(row);
	anchor.setDx1(180000);//이미지 가로 비율?
	anchor.setDy1(60000);//이미지 세로 비율?
	
	anchor.setCol2(col);
	anchor.setRow2(row);
	anchor.setDx2(dx2);//이미지 가로 위치?
	anchor.setDy2(dy2);//이미지 세로 위치?
	
	// 이미지 그리기
	XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);

	// 이미지 사이즈 비율 설정
	//pict.resize();
	//pict.setLineStyle(XSSFPicture.POINT_DPI);
    }
    
}
