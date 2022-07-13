package ext.ket.cost.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.fc.PersistenceHelper;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import e3ps.common.code.NumberCode;
import e3ps.common.util.AuthHandler;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import ext.ket.common.files.util.DownloadView;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.PjtMasterPartListLink;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.service.CostCacheManager;
import ext.ket.cost.service.CostHelper;
import ext.ket.shared.drm.DRMHelper;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 12. 11. 오후 12:04:19
 * @Pakage ext.ket.cost.util
 * @class CostAttrExcelLoadUtil
 *********************************************************/
public class CostBomUploadUtil implements RemoteAccess {

	// windchill ext.ket.cost.util.CostAttrExcelLoadUtil
	// D:\COST_ATTRIBUTE_DEFINITION.xlsx

	private static final Logger LOGGER = Logger.getLogger(CostBomUploadUtil.class);

	public static final CostBomUploadUtil manager = new CostBomUploadUtil();

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2017. 12. 11. 오후 12:04:21
	 * @method load
	 * @param sFilePath
	 * </pre>
	 */
	public void bomExcelUpload(Map<String, Object> reqMap, File uploadFile) throws Exception {
		LOGGER.info("bomExcelUpload #############################################");
		Transaction trx = new Transaction();

		try {

			String projectOid = (String) reqMap.get("projectOid");
			E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);

			E3PSProjectMaster pjtMatser = (E3PSProjectMaster) project.getMaster();

			PartList partList = CostUtil.manager.getPartList(pjtMatser);

			trx.start();
			
			String taskOid = (String) reqMap.get("taskOid");
			E3PSTask task = (E3PSTask)CommonUtil.getObject(taskOid);
			int costVersion = Integer.valueOf(task.getCostVersion());
			
			if (partList != null) {
			    	partList.setLastestVersion(costVersion);
			    	partList = (PartList) PersistenceHelper.manager.save(partList);
			    	
				reqMap.put("partListOid", CommonUtil.getOIDString(partList));
				CostHelper.service.deleteCostPartNonTrx(reqMap);
			}

			if (DRMHelper.useDrm) {
				uploadFile = DRMHelper.Decryptupload(uploadFile, uploadFile.getName());
			}

			String filePathInfo = "sFilePath = " + uploadFile.getAbsolutePath();
			LOGGER.info(filePathInfo);

			String newFileInfo = "newFile = " + uploadFile;
			LOGGER.info(newFileInfo);

			LOGGER.info(uploadFile.getName());
			String fileName = uploadFile.getName();

			String ext = "";
			if (fileName.indexOf(".") >= 0) {
				ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			}

			if (ext.length() == 0) {
				LOGGER.error("정확하지 않은 확장자의 파일");
			} else {

				LOGGER.info(ext);

				FileInputStream fis = new FileInputStream(uploadFile);

				if ("xlsm".equals(ext)) {
					xssfExcelLoad(reqMap, fis);
				} else {
					LOGGER.error("xlsm 확장자의 파일만 업로드 가능 합니다.");
				}
			}

			trx.commit();
			trx = null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getLocalizedMessage());
		} finally {
			if (trx != null) {
				trx.rollback();
				trx = null;
			}
		}
	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2017. 12. 11. 오후 3:09:45
	 * @method xssfExcelLoad
	 * @param fis
	 * @throws Exception
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public void xssfExcelLoad(Map<String, Object> reqMap, FileInputStream fis) throws Exception {

		String projectOid = (String) reqMap.get("projectOid");
		String taskOid = (String) reqMap.get("taskOid");
		
		E3PSTask task = (E3PSTask)CommonUtil.getObject(taskOid);
		int costVersion = Integer.valueOf(task.getCostVersion());

		E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);

		E3PSProjectMaster pjtMatser = (E3PSProjectMaster) project.getMaster();
		
		PartList partList = CostUtil.manager.getPartList(pjtMatser);

		String drNo = CostCodeHelper.service.getDrStepByTask(CommonUtil.getOIDString(project), taskOid);

		String pjtNo = pjtMatser.getPjtNo();
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sheet = wb.getSheetAt(0);
		DataFormatter df = new DataFormatter();

		int rowNum = sheet.getPhysicalNumberOfRows();

		Map<String, CostPart> savePartMap = new HashMap<String, CostPart>();

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(3);

		List<Map<String, Object>> typeList = getCostPartType();

		Map<String, Map<String, Object>> typeMap = new HashMap<String, Map<String, Object>>();

		for (Map<String, Object> type : typeList) {

			String typeName = (String) type.get("TYPENAME");

			if (StringUtil.checkString(typeName)) {
				typeMap.put(typeName, type);
			}
		}

		int partNo = 0;

		Map<String, Object> rows = new LinkedHashMap<String, Object>();

		for (int i = 2; i < rowNum; i++) {

			XSSFRow row = sheet.getRow(i);

			String id = "";
			String pid = "";
			String partType = "";
			String country = "";
			String mftFactory = "";
			String partName = "";
			String us = "";

			int cellCnt = 0;
			XSSFCell cell = row.getCell(cellCnt);
			if (cell != null) {
				id = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				pid = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				partType = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				country = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				mftFactory = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				partName = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			cell = row.getCell(cellCnt);
			if (cell != null) {
				us = df.formatCellValue(cell).trim();
			}
			cellCnt++;

			if (!StringUtil.checkString(id))
				break;

			LOGGER.info("##############################");
			LOGGER.info("ID === " + id.length() + " ===" + id);
			LOGGER.info("PID === " + pid.length() + " ===" + pid);
			LOGGER.info("PARTTYPE === " + partType.length() + " ===" + partType);
			LOGGER.info("COUNTRY === " + country.length() + " ===" + country);
			LOGGER.info("MFTFACTORY === " + mftFactory.length() + " ===" + mftFactory);
			LOGGER.info("PARTNAME === " + partName.length() + " ===" + partName);
			LOGGER.info("US === " + us.length() + " ===" + us);

			CostPart chkPart = savePartMap.get(id);

			if (chkPart != null) {
				throw new Exception("중복되는 PART ID가 있습니다.");
			}

			CostPart part = CostPart.newCostPart();

			String costPartNo = pjtNo + "-" + nf.format(partNo);
			partNo++;

			part.setPartNo(costPartNo);
			part.setPartName(partName);
			part.setSortLocation(partNo);
			part.setDrStep(drNo);
			part.setMetalScenario("SNR002");
			part.setCalcStdMaterial("CALCSTD001");
			part.setCalcStdLabor("CALCSTD004");
			part.setCalcStdExpense("CALCSTD007");
			part.setCalcStdManage("CALCSTD005");
			part.setCalcStdOutPut("CALCSTD006");
			part.setProject(pjtMatser);
			part.setVersion(costVersion);

			Map<String, Object> type = null;

			// 부품구분, 생산국, 생산지 데이터 KEY 변환
			if (StringUtil.checkString(partType)) {
				type = typeMap.get(partType);

				part.setPartType((String) type.get("TYPEID"));
				List<Map<String, Object>> mftList = (List<Map<String, Object>>) type.get("MFTLIST");

				for (Map<String, Object> mft : mftList) {

					String codeOid = (String) mft.get("numberCodeOid");

					NumberCode code = (NumberCode) CommonUtil.getObject(codeOid);
					NumberCode pCode = code.getParent();

					if (pCode != null && StringUtil.checkString(mftFactory)) {
						if (country.equals(pCode.getName()) && mftFactory.equals(code.getName())) {
							part.setMftFactory2(CommonUtil.getOIDLongValue2Str(code));
						}
					} else if (StringUtil.checkString(country) && country.equals(code.getName())) {
						part.setMftFactory1(CommonUtil.getOIDLongValue2Str(code));
					}
				}
			}

			// 부품 구분 레벨 유효성 체크 데이터 수집
			Map<String, Object> rowMap = new HashMap<String, Object>();
			rowMap.put("partType", part.getPartType());
			rowMap.put("partName", partName);
			rowMap.put("hasChild", "false");
			if (StringUtil.checkString(pid)) {
				rowMap.put("parent", pid);
			}
			rows.put(id, rowMap);

			if (StringUtil.checkString(pid)) {
				part.setUs(us);
				CostPart pPart = savePartMap.get(pid);
				if (pPart != null) {
					part.setParent(pPart);

					rowMap = (Map<String, Object>) rows.get(pid);

					if (rowMap != null) {
						rowMap.put("hasChild", "true");
						rows.put(pid, rowMap);

						// BOM TREE LEVEL 계산
						int level = 0;
						String tempPid = "";
						while (rowMap != null) {
							level++;
							tempPid = (String) rowMap.get(pid);
							rowMap = (Map<String, Object>) rows.get(tempPid);
						}

						rowMap = (Map<String, Object>) rows.get(id);
						rowMap.put("level", String.valueOf(level));
						rows.put(id, rowMap);
					}

				} else {
					throw new Exception("모부품이 존재하지 않습니다.");
				}

			} else {

				rowMap = (Map<String, Object>) rows.get(id);
				rowMap.put("level", "0");
				rows.put(id, rowMap);

				part.setUs("1");

				ProductMaster master = ProductMaster.newProductMaster();
				master.setPartList(partList);
				master = (ProductMaster) PersistenceHelper.manager.save(master);
				part.setMaster(master);

			}

			part = (CostPart) PersistenceHelper.manager.save(part);

			savePartMap.put(id, part);
		}
		
		// 부품 구분 레벨 유효성 체크
		Map<String, Object> treeChkParam = new HashMap<String, Object>();
		treeChkParam.put("rows", rows);

		Map<String, Object> result = CostTreeUtil.manager.treePartTypeCheck(treeChkParam);

		if ((boolean) result.get("isError")) {
			throw new Exception((String) result.get("message"));
		}
	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2019. 4. 25. 오전 10:10:36
	 * @method createBomUploadExcel
	 * @param reqMap
	 * @return Map<String,Object>
	 * @throws Exception
	 * </pre>
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<String, Object> createBomUploadExcel(Map<String, Object> reqMap) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		// ########### 엑셀 시트보호 PASSWORD : wcadmin ##################
		File uploadFile = new File(DownloadView.CODEBASEPATH + File.separator + "cost" + File.separator + "COSTBOMLOAD.xlsm");

		FileInputStream fis = new FileInputStream(uploadFile);

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		// ################# 구분,생산국,생산지 데이터 로드 ###################
		XSSFSheet class1 = wb.getSheet("CLASS1");
		XSSFSheet class2 = wb.getSheet("CLASS2");

		int class1Cnt = 0;
		int class2Cnt = 0;

		List<Map<String, Object>> typeList = getCostPartType();

		for (Map<String, Object> type : typeList) {

			String typeName = (String) type.get("TYPENAME");

			XSSFRow row = class1.createRow(class1Cnt++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(typeName);

			List<Map<String, Object>> mftList = (List<Map<String, Object>>) type.get("MFTLIST");

			int mftCnt = 1;

			Map<String, List<String>> childMft = new HashMap<String, List<String>>();

			for (Map<String, Object> mft : mftList) {

				String pOid = (String) mft.get("numberParentOid");

				if (StringUtils.isEmpty(pOid)) {

					cell = row.createCell(mftCnt++);
					cell.setCellValue((String) mft.get("numberCodeName"));

				} else {
					NumberCode code = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + pOid);
					String mftName = (String) mft.get("numberCodeName");

					if (code != null) {

						List<String> childMftlist = childMft.get(typeName + code.getName());

						if (childMftlist == null) {
							childMftlist = new ArrayList<String>();
						}

						childMftlist.add(mftName);
						childMft.put(typeName + code.getName(), childMftlist);
					}
				}
			}

			Set<String> st = childMft.keySet();
			Iterator<String> it = st.iterator();

			while (it.hasNext()) {
				String key = it.next();

				row = class2.createRow(class2Cnt++);
				cell = row.createCell(0);
				cell.setCellValue(key);

				List<String> childMftlist = childMft.get(key);

				int mftCnt2 = 1;

				for (String mftName : childMftlist) {
					cell = row.createCell(mftCnt2++);
					cell.setCellValue(mftName);
				}
			}
		}

		String fileName = "COSTBOMLOAD.xlsm";
		FileOutputStream fos = new FileOutputStream(DownloadView.TEMPPATH + File.separator + fileName);
		wb.write(fos);
		fos.close();

		System.out.println("PATH ### " + DownloadView.TEMPPATH + File.separator + fileName);
		String downloadLink = "/plm/ext/download?path=/TEMP/" + fileName;
		resMap.put("downloadLink", downloadLink);

		return resMap;
	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2019. 4. 25. 오전 10:10:10
	 * @method getCostPartType
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 * </pre>
	 */
	private static List<Map<String, Object>> getCostPartType() throws Exception {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Statement stat = null;
		ResultSet rs = null;
		MethodContext mContext = MethodContext.getContext();
		WTConnection conn = null;

		try {
			conn = (WTConnection) mContext.getConnection();
			stat = conn.getConnection().createStatement();

			StringBuffer sql = new StringBuffer();
			sql.append("	SELECT NAME, TO_CHAR(IDA2A2) AS TYPEID FROM COSTPARTTYPE WHERE IDA3PARENTREFERENCE != 0 ORDER BY SORTLOCATION  \n");

			rs = stat.executeQuery(sql.toString());

			while (rs.next()) {
				String TYPENAME = rs.getString("NAME");
				String TYPEID = rs.getString("TYPEID");

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("TYPENAME", TYPENAME);
				map.put("TYPEID", TYPEID);

				List<Map<String, Object>> mftList = CostCacheManager.getCostPTItem(TYPEID);
				map.put("MFTLIST", mftList);

				list.add(map);
			}

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

	public static void main(String args[]) {

		try {
			RemoteMethodServer server = RemoteMethodServer.getDefault();

			server.setAuthenticator(AuthHandler.getMethodAuthenticator("wcadmin"));

			LOGGER.setLevel(Level.INFO);
			LOGGER.info("############CostBomUploadUtil START####################");
			// createBomUploadExcel(null);
			LOGGER.info("############CostBomUploadUtil COMPLETE####################");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
