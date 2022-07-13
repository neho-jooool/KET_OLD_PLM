package ext.ket.bom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.content.uploader.FileUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.ecm.beans.EcmUtil;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class StandardKETBom2Service extends StandardManager implements KETBom2Service, Serializable {

	private static final long serialVersionUID = 1L;
	private static final String RESOURCE = " ext.ket.bom.service.serviceResource";
	private static final String CLASSNAME = StandardKETBom2Service.class.getName();

	/**
	 * Returns the conceptual (modeled) name for the class.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @deprecated
	 * 
	 * @return String
	 **/
	public String getConceptualClassname() {

		return CLASSNAME;
	}

	@Override
	protected void performStartupProcess() throws ManagerException {

		super.performStartupProcess();

	}

	/**
	 * Default factory for the class.
	 * 
	 * @return StandardKETBom2Service
	 * @exception wt.util.WTException
	 **/
	public static StandardKETBom2Service newStandardKETBom2Service() throws WTException {

		StandardKETBom2Service instance = new StandardKETBom2Service();
		instance.initialize();
		return instance;
	}

	@Override
	public Vector searchItem(Hashtable searchAttrHash) throws WTException {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public String savePart(ArrayList arr) throws WTException {
		// TODO Auto-generated method stub

		String result = "";
		KETBOMQueryBean qbean = new KETBOMQueryBean();
		KETBomPartUtil util = new KETBomPartUtil();

		Transaction trx = new Transaction();

		try {
			trx.start();

			// params.put("topPartNo", partNo);
			// params.put("topRev", topRev);
			// params.put("partNo", partNo);
			// params.put("partOid", partOid);
			// params.put("rpartOid", rpartOid);
			// params.put("newPWeight", newPWeight);
			// params.put("newSWeight", newSWeight);
			// params.put("newTWeight", newTWeight);
			// params.put("newMaterial", newMaterial);

			Hashtable params = (Hashtable) arr.get(0);
			String topPartNo = (String) params.get("topPartNo");
			String topRev = (String) params.get("topRev");
			String[] partNo = (String[]) params.get("partNo");
			String[] partOid = (String[]) params.get("partOid");
			String[] rpartOid = (String[]) params.get("rpartOid");
			String[] newPWeight = (String[]) params.get("newPWeight");
			String[] newSWeight = (String[]) params.get("newSWeight");
			String[] newTWeight = (String[]) params.get("newTWeight");
			String[] newMaterial = (String[]) params.get("newMaterial");

			String topPartOid = qbean.getPartOid(topPartNo, topRev);
			WTPart topPart = util.getPart(topPartOid);

			if (partNo != null && partNo.length > 0) {
				for (int i = 0; i < partNo.length; i++) {
					Kogger.debug(getClass(), "------------------------------------------------------------------");
					Kogger.debug(getClass(), "partNo[" + i + "]==>" + partNo[i]);
					Kogger.debug(getClass(), "partOid[" + i + "]==>" + partOid[i]);
					Kogger.debug(getClass(), "rPartOid[" + i + "]==>" + rpartOid[i]);
					Kogger.debug(getClass(), "newPWeight[" + i + "]==>" + newPWeight[i]);
					Kogger.debug(getClass(), "newSWeight[" + i + "]==>" + newSWeight[i]);
					Kogger.debug(getClass(), "newTWeight[" + i + "]==>" + newTWeight[i]);
					Kogger.debug(getClass(), "newMaterial[" + i + "]==>" + newMaterial[i]);

					// if (topPartNo.equals(partNo[i].substring(1))) {
					// // topPart
					// PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpTotalWeight, newTWeight[i]);
					// PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpScrabWeight, newSWeight[i]);
					// PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpNetWeight, newPWeight[i]);
					//
					// if(StringUtils.isNotEmpty(newMaterial[i])){
					//
					// List list = PartClazHelper.service.getMaterialPartSpecEnum(topPart);
					// if (list != null && list.size() > 0) {
					// PartSpecEnum specenum = (PartSpecEnum) list.get(0);
					// PartSpecSetter.setPartSpec(topPart, specenum, newMaterial[i]);
					// }
					// }
					//
					// arr.add(topPart);
					// }

					WTPart part = util.getPart(partOid[i]);
					WTPart rpart = null;
					if (StringUtils.isNotEmpty(rpartOid[i])) {
						rpart = util.getPart(rpartOid[i]);
					}

					PartSpecSetter.setPartSpec(part, PartSpecEnum.SpTotalWeight, newTWeight[i]);
					PartSpecSetter.setPartSpec(part, PartSpecEnum.SpScrabWeight, newSWeight[i]);
					PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, newPWeight[i]);

					List list2 = PartClazHelper.service.getMaterialPartSpecEnum(part);

					if (list2 != null && list2.size() > 0) {
						PartSpecEnum specenum2 = (PartSpecEnum) list2.get(0);
						if (StringUtils.isNotEmpty(newMaterial[i])) {
							PartSpecSetter.setPartSpec(part, specenum2, newMaterial[i]);
						}
					}

					Kogger.debug(getClass(), "getPartSpec==>" + PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));
					Kogger.debug(getClass(), "------------------------------------------------------------------");

					PersistenceServerHelper.manager.update(part);
					part = (WTPart) PersistenceHelper.manager.refresh(part);

					if (rpart != null) {

						PartBaseHelper.service.updateMaterialInfo(part, rpart);
						part = (WTPart) PersistenceHelper.manager.refresh(part);
					}
				}
			}

			// for (int inx = 0; inx < arr.size(); inx++) {

			// WTPart part = (WTPart) arr.get(inx);

			// Part Save
			// part = (WTPart) PersistenceHelper.manager.save(part);
			// wt.fc.PersistenceServerHelper.manager.update(part);

			// }

			trx.commit();
			trx = null;
		} catch (Exception e) {
			result = e.toString();
			throw new WTException(e);
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}

		return result;
	}

	@Override
	public Map<String, Object> excelUploadPart(FileUploader uploader, Hashtable params) throws WTException {
		// TODO Auto-generated method stub

		KETBOMQueryBean butil = new KETBOMQueryBean();
		KETBomPartUtil pUtil = new KETBomPartUtil();
		List resultList = new ArrayList<Map<String, Object>>();
		Hashtable rootHash = new Hashtable();

		Vector files;
		String success = "true";
		String jsondata = "";
		String root = "";
		String errlog = "";

		String gubun = (String) params.get("gubun");
		String partNumber = (String) params.get("partNumber");
		String partRev = (String) params.get("partRev");

		String econoA = (String) params.get("econo");
		String checkoutA = (String) params.get("checkout");
		String checkoutIdA = (String) params.get("checkoutId");
		String stateA = (String) params.get("state");
		boolean partCreateIsPossible = true;
		Transaction trx = new Transaction();

		Map<String, Object> resultHash = new HashMap<String, Object>();

		SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd");

		Hashtable partListCheckH = new Hashtable();

		try {
			trx.start();

			/********************************************************************************************/

			files = uploader.getFiles();
			String fname = "";

			String moldClassCode = "T";
			KETPartClassification claz = PartClazHelper.service.getPartClassificationByClazCode(moldClassCode);
			String partClazOid = CommonUtil.getOIDString(claz);

			WTConnection wtConn = EcmUtil.getWTConnection();

			if (files != null) {
				boolean isPrimary = false;
				for (int i = 0; i < files.size(); i++) {
					WBFile file = (WBFile) files.elementAt(i);

					File excel = file.getFile();
					excel = DRMHelper.Decryptupload(excel, excel.getName());

					List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

					FileInputStream fis = new FileInputStream(excel);
					HSSFWorkbook workBook = new HSSFWorkbook(fis);
					HSSFSheet sheet = workBook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();
					int rowCnt = 0;
					int level = 0;
					int iseq = 0;

					Hashtable lvlH = new Hashtable();
					lvlH.put(partNumber, level);

					Hashtable seqH = new Hashtable();
					WTPart parentPart = null;
					WTPart part = null;

					while (rowIterator.hasNext()) {

						try {
							Row row = rowIterator.next();
							Kogger.debug(getClass(), "-------------------------------------->" + row);

							if (rowCnt > 0)// 헤더 부분 제외
							{

								String partNo = getStringValue(row.getCell(Short.parseShort("1")));
								Kogger.debug(getClass(), "-------------------------------------->" + partNo);

								// partNo Validation Check logic
								if (partNo != null) {
									if (partNo.matches(".*[_|a-z].*"))
										errlog += partNo + " 부품번호에 소문자 또는 _가 존재해서는 안됩니다.";
								}

								String rev = "0";
								String partName = "";
								String newRev = "";

								try {
									part = pUtil.getLatestPart2(partNo, wtConn);
									String bomflag = butil.partNewOld(partNo, wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue());

									Kogger.debug(getClass(), "=================================================");
									Kogger.debug(getClass(), "===>" + bomflag);
									Kogger.debug(getClass(), "=================================================");
									if (bomflag.equals("OLD")) {
										String partOid = butil.getLatestReleasedPart(partNo, wtConn);
										part = pUtil.getPart(partOid);
									}

								} catch (Exception e) {
								}

								if (part != null) {
									if (gubun.equals("P"))// 제품인 경우
									{
										rev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
										partName = part.getName();
										newRev = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision);
									}
								} else {
									if (gubun.equals("P"))// 제품인 경우
									{
										errlog += partNo + ": " + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09439") + " \\n"; // 미등록 부품입니다.
									}
								}

								String lvl = "";
								String seq = "";

								String qty = "1";
								String unit = "";

								if (part != null) {
									unit = part.getDefaultUnit().toString();
									if (unit.indexOf("KET_") == -1) {
										unit = "KET_" + unit;
									}
								}

								if (partNo.startsWith("R10") || partNo.startsWith("R20") || partNo.startsWith("S")) {
									unit = "KET_G";
								}

								String econo = "";
								String checkout = "";
								String checkoutId = "";
								String ict = "";
								String reftop = "";
								String refbtm = "";
								String material = "";
								String hardnessFrom = "";
								String hardnessTo = "";
								String designDate = "";
								String state = "";

								if (part != null) {
									state = part.getLifeCycleState().getDisplay();
								}

								String parentNo = getStringValue(row.getCell(Short.parseShort("0"))).trim();

								if (seqH.containsKey(parentNo)) {
									iseq = (int) seqH.get(parentNo);
									iseq = iseq + 10;
									seq = Integer.toString(iseq);
									seqH.put(parentNo, iseq);
								} else {
									seq = "10";
									iseq = 10;
									seqH.put(parentNo, iseq);
								}

								String pver = "";

								// Hashtable parentH = butil.getPartInfo(parentNo);

								try {
									parentPart = pUtil.getLatestPart2(parentNo, wtConn);
									String bomflag = butil.partNewOld(parentNo, wt.vc.VersionControlHelper.getVersionIdentifier(parentPart).getValue());

									Kogger.debug(getClass(), "=================================================");
									Kogger.debug(getClass(), "===>" + bomflag);
									Kogger.debug(getClass(), "=================================================");
									if (bomflag.equals("OLD")) {
										String partOid = butil.getLatestReleasedPart(parentNo, wtConn);
										parentPart = pUtil.getPart(partOid);
									}

								} catch (Exception e) {

								}

								// 승인된 부품의 BOM을 USAGELINK에서 가져온다.
								if (gubun.equals("P") && parentPart != null && part != null)// 제품인 경우
								{
									if (part.getLifeCycleState().toString().equals("APPROVED")) {
										long id = pUtil.getPartLongId(part);
										String partOid = Long.toString(id);

										Hashtable sub_params = new Hashtable();
										sub_params.put("partOid", partOid);
										sub_params.put("partType", "P");
										sub_params.put("dataType", "DataList");

										// Kogger.debug(getClass(), "##################################################");
										// Kogger.debug(getClass(), treeList.toString());
										// Kogger.debug(getClass(), "##################################################");

										if (!parentPart.getLifeCycleState().toString().equals("APPROVED"))
											treeList = butil.getLatestBOM(sub_params);

									}
								}

								if (gubun.equals("P"))// 제품인 경우
								{
									if (parentPart != null) {
										pver = wt.vc.VersionControlHelper.getVersionIdentifier(parentPart).getValue();
									}
								} else// 금형인 경우
								{
									pver = "0";
								}

								String cadNo = "";
								String mainPart = "";

								if (lvlH.containsKey(parentNo)) {
									int lvl_tmp = ((int) lvlH.get(parentNo)) + 1;
									lvl = Integer.toString(lvl_tmp);
									lvlH.put(partNo, lvl_tmp);
								}

								if (gubun.equals("P"))// 제품인 경우
								{

									if (parentNo == null || parentNo.equals(""))// root
									{
									} else {
										ict = getStringValue(row.getCell(Short.parseShort("2"))).trim();
										qty = getStringValue(row.getCell(Short.parseShort("3"))).trim();
										if (!qty.matches("[0-9]+\\.+[0-9]+|[0-9]+")) {
											errlog += partNo + " 수량은 숫자만 입력해야합니다.\\n";
										}
										reftop = getStringValue(row.getCell(Short.parseShort("4"))).trim();
										refbtm = getStringValue(row.getCell(Short.parseShort("5"))).trim();

										designDate = "";
										material = "";
										hardnessFrom = "";
										hardnessTo = "";

									}
								} else // 금형인 경우
								{
									partCreateIsPossible = true;
									if (parentNo == null || parentNo.equals(""))// root
									{

									} else {

										if (StringUtils.isNotEmpty(partNo)) {
											if (!StringUtils.substringBefore(partNo, "-").equals(parentNo)) {
												errlog += partNo + " 모품번이 존재하지 않습니다.\\n";
												partCreateIsPossible = false;
											}
										}
										partName = getStringValue(row.getCell(Short.parseShort("2"))).trim();
										qty = getStringValue(row.getCell(Short.parseShort("3"))).trim();

										if (!qty.matches("[0-9]+\\.+[0-9]+|[0-9]+")) {
											errlog += partNo + " 수량은 숫자만 입력해야합니다.\\n";
										}
										// designDateCell.getCellType()
										if (row.getCell(Short.parseShort("4")).getCellType() == Cell.CELL_TYPE_NUMERIC) {
											HSSFCell designDateCell = (HSSFCell) row.getCell(Short.parseShort("4"));
											if (DateUtil.isCellDateFormatted(designDateCell)) {
												designDate = fommatter.format(designDateCell.getDateCellValue());
											} else {
												designDate = getStringValue(row.getCell(Short.parseShort("4"))).trim();
											}
										} else {

											designDate = getStringValue(row.getCell(Short.parseShort("4"))).trim();
										}

										Kogger.debug(getClass(), "CellType========>" + Cell.CELL_TYPE_NUMERIC);

										Kogger.debug(getClass(), "designDate========>" + designDate);

										material = getStringValue(row.getCell(Short.parseShort("5"))).trim();
										// material = material.toUpperCase();
										Kogger.debug(getClass(), "material================================>" + material + "<===");

										hardnessFrom = getStringValue(row.getCell(Short.parseShort("6"))).trim();
										hardnessTo = getStringValue(row.getCell(Short.parseShort("7"))).trim();
										cadNo = getStringValue(row.getCell(Short.parseShort("8"))).trim();

										Kogger.debug(getClass(), "CAD No================================>" + cadNo + "<===");

										if (cadNo == null) {
											cadNo = "";
										}
										// mainPart = getStringValue(row.getCell(Short.parseShort("9")));
										ict = "";
										reftop = "";
										refbtm = "";

										if (designDate.length() == 8 && designDate.indexOf("-") == -1) {
											designDate = designDate.substring(0, 4) + "-" + designDate.substring(4, 6) + "-" + designDate.substring(6, 8);
										}

										if (designDate.indexOf("/") != -1) {
											designDate = designDate.replaceAll("/", "-");
										}

										if (designDate.indexOf(".") != -1) {
											designDate = designDate.replaceAll(".", "-");
										}

										if (designDate.indexOf("-") != -1) {
											designDate = designDate.replaceAll("-", "-");
										}

										if (!isValidDate(designDate)) {
											errlog += partNo + " " + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09440") + "\\n"; // 날짜형식이 올바르지
																																									// 않습니다.
										}

										HashMap nCode = new HashMap();
										nCode = (HashMap) pUtil.getNumberCode("SPECMATERIALMOLD");
										// Kogger.debug(getClass(), nCode.values().toString());

										Set keys = nCode.keySet();

										Iterator<String> mkey = keys.iterator();

										String materialCode = "";
										int ncCnt = 0;
										while (mkey.hasNext()) {
											ncCnt++;
											Kogger.debug(getClass(), "mkey======>" + mkey);
											String numCode = (String) mkey.next();
											String numDesc = (String) nCode.get(numCode);

											Kogger.debug(getClass(), "numDesc==>" + numDesc + "##########  material====>" + material);

											if (material.equals(numDesc.trim()))
												materialCode = numCode;
											// materialArr+="[\""+numCode+"\",\""+numDesc+"\"]";

										}

										material = materialCode;

										if (part != null) {
											rev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
											partName = part.getName();
											// newRev = butil.getNewVersionCode(partNo, rev, wtConn);
											newRev = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision);
											state = part.getLifeCycleState().getDisplay();

											WTPart dpart = pUtil.getLatestPart2(parentNo, wtConn);
											boolean cadRelation = PartBaseHelper.service.makeMoldPartToEpmDocRelation(dpart, part, cadNo.trim());
											if (!cadRelation) {
												errlog += partNo + " " + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09441") + " \\n"; // 도면연결 중에
																																										// 오류가
																																										// 발생하였습니다.
											}

										} else if (partCreateIsPossible) {
											// 금형부품 등록로직
											rev = "0";
											newRev = "0";

											// partName = (String)partH.get("partName");
											unit = "KET_EA";

											PartBaseDTO dto = new PartBaseDTO();
											dto.setPartNumber(partNo);
											dto.setPartName(partName);
											dto.setSpPartType("M");
											dto.setPartClazOid(partClazOid);
											dto.setPartDefaultUnit("KET_EA");
											dto.setSpPartDevLevel("PC003"); // 개발
											dto.setSpMaterDie(materialCode); // 개발

											Kogger.debug(getClass(), "-----------------------------금형 부품 등록 시작");

											WTPart mpart = null;
											try {
												mpart = PartBaseHelper.service.createWTPart(dto);
											} catch (Exception e) {
												errlog += partNo + " " + e.getLocalizedMessage() + "\\n ";
												break;
											}
											Kogger.debug(getClass(), "-----------------------------금형 부품 등록 종료");

											// String doid = pUtil.getLatestPart2(parentNo, conn);
											// WTPart dpart = pUtil.getPart(doid);

											if (mpart != null) {
												WTPart dpart = pUtil.getLatestPart2(parentNo, wtConn);

												// boolean cadRelation =
												boolean cadRelation = PartBaseHelper.service.makeMoldPartToEpmDocRelation(dpart, mpart, cadNo.trim());

												if (!cadRelation) {
													errlog += partNo + " " + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09441") + "\\n ";
												} else {
													rev = wt.vc.VersionControlHelper.getVersionIdentifier(mpart).getValue();
													// newRev = butil.getNewVersionCode(partNo, rev, wtConn);
													newRev = PartSpecGetter.getPartSpec(mpart, PartSpecEnum.SpPartRevision);
													state = mpart.getLifeCycleState().getDisplay();
												}

											}

										}

										if (gubun.equals("P"))// 제품인 경우
										{
											if (parentPart != null) {
												pver = wt.vc.VersionControlHelper.getVersionIdentifier(parentPart).getValue();
											}
										} else// 금형인 경우
										{
											pver = "0";
										}
									}
								}

								Map<String, Object> data = new Hashtable();

								if (gubun.equals("P"))// 제품인 경우
								{
									if (parentPart != null)
										if (!parentPart.getState().toString().equals("APPROVED")) {
											data.put("ict", ict);
											data.put("partNo", partNo);
											data.put("rev", rev);
											data.put("newrev", newRev);
											data.put("partName", partName);
											data.put("lvl", lvl);
											data.put("seq", seq);
											data.put("qty", qty);
											data.put("unit", unit);
											data.put("econo", econo);
											data.put("checkout", checkout);
											data.put("checkoutId", checkoutId);
											data.put("reftop", reftop);
											data.put("refbtm", refbtm);
											data.put("material", material);
											data.put("hardnessFrom", hardnessFrom);
											data.put("hardnessTo", hardnessTo);
											data.put("designDate", designDate);
											data.put("state", state);
											data.put("parentNo", parentNo);
											data.put("pver", pver);

											tlist.add(data);

											Hashtable dupCheckH = new Hashtable();

											for (int k = 0; k < treeList.size(); k++) {
												Hashtable partData = (Hashtable) treeList.get(k);
												String pdlvl = (String) partData.get("lvl");
												String pdPartNo = (String) partData.get("partNo");
												String pdParentNo = (String) partData.get("parentNo");

												Kogger.debug(getClass(), "@@@@@@@@@@@@@@@@@@@@@@@@@@@@>>>>>>>>>>>>" + pdlvl);
												if (!pdlvl.equals("0")) {
													tlist.add(partData);
												}

												Kogger.debug(getClass(), "엑셀업로드 무한루핑 체크====>(모:" + pdParentNo + "  자:" + pdPartNo + ")");
												/*
												 * if (pdPartNo.equals(partNo)) { errlog += "승인된 부품 중에 중복된 데이터가 있습니다.( Part No :" + pdPartNo + ")"; break; }
												 */

												// dupCheckH.put(pdPartNo+"↔", value)
											}
										}
								} else {
									data.put("ict", ict);
									data.put("partNo", partNo);
									data.put("rev", rev);
									data.put("newrev", newRev);
									data.put("partName", partName);
									data.put("lvl", lvl);
									data.put("seq", seq);
									data.put("qty", qty);
									data.put("unit", unit);
									data.put("econo", econo);
									data.put("checkout", checkout);
									data.put("checkoutId", checkoutId);
									data.put("reftop", reftop);
									data.put("refbtm", refbtm);
									data.put("material", material);
									data.put("hardnessFrom", hardnessFrom);
									data.put("hardnessTo", hardnessTo);
									data.put("designDate", designDate);
									data.put("state", state);
									data.put("parentNo", parentNo);
									data.put("pver", pver);

									tlist.add(data);
								}

								if (partListCheckH.contains(parentNo + "↔" + partNo)) {
									errlog += KETMessageService.getMessageService().getString("e3ps.message.ket_message", "09412") + "(Parent Part: " + parentNo + " Child Part:"
											+ partNo + ")";// "중복된 데이터입니다.
									break;
								} else {
									partListCheckH.put(parentNo + "↔" + partNo, "");
								}
							}

						} catch (Exception e) {
							errlog += e.getMessage() + "\\n";
						}

						rowCnt++;
					} // while (rowIterator.hasNext()) {

					newlist = butil.treeRecursive(list, tlist, partNumber);
					newlist = butil.getGridBOM(newlist);

					resultList = butil.convertTreeList(newlist, "parentNo", partNumber, "partNo", "parentNo");

					// Kogger.debug(getClass(), "=============================================================================");
					// Kogger.debug(getClass(), "==>"+resultList.toString());
					// Kogger.debug(getClass(), "=============================================================================");

					JSONArray jsonArr = new JSONArray(resultList);
					jsondata = jsonArr.toJSONString();

					Kogger.debug(getClass(), "JSON DATA======>" + jsondata);

				} // for (int i = 0; i < files.size(); i++) {
			}

			if (!errlog.equals("")) {
				JSONArray jsonArr2 = new JSONArray();
				jsondata = jsonArr2.toJSONString();
			}

			resultHash.put("success", success);
			resultHash.put("jsondata", jsondata);
			resultHash.put("errlog", errlog);

			/********************************************************************************************/

			if (!errlog.equals("")) {
				resultHash.put("jsondata", "[]");
				trx.rollback();
				trx = null;
			} else {
				trx.commit();
				trx = null;
			}
		} catch (Exception e) {
			trx.rollback();
			trx = null;

			Kogger.error(getClass(), e);
			throw new WTException(e);
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}

		return resultHash;
	}

	private String getStringValue(Cell cell) {
		String value = "";

		if (cell == null)
			return value;

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = Integer.toString(new Double(cell.getNumericCellValue()).intValue());
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = Boolean.toString(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = "ERROR";
			break;
		default:
		}

		return value.trim();
	}

	private boolean isValidDate(String dateStr) {
		Pattern p = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}

}
