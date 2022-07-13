/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProdEcoBeans.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 29.
 */
package e3ps.ecm.beans;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.Mastered;
import wt.vc.Versioned;
import wt.workflow.work.WorkItem;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.common.util.WCUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.dao.EcmComDao;
import e3ps.ecm.dao.ProdEcoDao;
import e3ps.ecm.entity.KETChangeActivityExpansion;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;
import e3ps.ecm.entity.KETProdECOPartLink;
import e3ps.ecm.entity.KETProdECOPlanLink;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;
import ext.ket.wfm.service.KETWorkflowHelper;

/**
 * 클래스명 : ProdEcoBeans 설명 : 작성자 : 오승연 작성일자 : 2010. 10. 29.
 */
public class ProdEcoBeans {

	/**
	 * 함수명 : getInitCodeList 설명 : 초기화 Code List Init
	 * 
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 26.
	 */
	public Hashtable<String, ArrayList<Hashtable<String, String>>> getInitCodeList(Map<String, Object> parameter) throws Exception {
		Connection conn = null;
		EcmComDao cDao = null;

		Hashtable<String, ArrayList<Hashtable<String, String>>> codeHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();

		try {
			conn = PlmDBUtil.getConnection();

			cDao = new EcmComDao(conn);

			ArrayList<Hashtable<String, String>> divList = cDao.getCodeList("DIVISIONFLAG", "ko");
			ArrayList<Hashtable<String, String>> devFlagList = cDao.getCodeList("DEVYN", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> chgReasonList = cDao.getCodeList("PRODECOREASON", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> custChkList = cDao.getCodeList("CUSTOMCHECK", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> changeFact = cDao.getCodeList("CHANGEFACT", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> effectiveDate = cDao.getCodeList("EFFECTIVEDATE", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> invProcess = cDao.getCodeList("INVPROCESS", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> docTypeList = cDao.getCodeList("PRODECODOCTYPE", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> defectDivList = cDao.getCodeList("PROBLEMDIVTYPE", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> costChangeList = cDao.getCodeList("COSTCHANGE", parameter.get("locale").toString());
			

			codeHash.put("division", divList);
			codeHash.put("devYn", devFlagList);
			codeHash.put("changeReason", chgReasonList);
			codeHash.put("custChk", custChkList);
			codeHash.put("changeFact", changeFact);
			codeHash.put("effectiveDate", effectiveDate);
			codeHash.put("invProcess", invProcess);
			codeHash.put("docTypeList", docTypeList);
			codeHash.put("defectDivList", defectDivList);
			codeHash.put("costChangeList", costChangeList);

			// 설계변경 상세사유
			ArrayList<Hashtable<String, String>> changeDetailReasonList = cDao.getCodeList("CHANGEDETAILREASON", parameter.get("locale").toString());
			codeHash.put("changeDetailReasonList", changeDetailReasonList);

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return codeHash;
	}

	/**
	 * 함수명 : getCarMakerList 설명 : 고객사(차량 최종 Maker) 정보 얻는 함수
	 * 
	 * @param domesticFlag
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 26.
	 */
	public ArrayList<Hashtable<String, String>> getCarMakerList(String domesticFlag) throws Exception {
		Connection conn = null;
		EcmComDao cDao = null;
		ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();

		try {
			conn = PlmDBUtil.getConnection();

			cDao = new EcmComDao(conn);

			codeList = cDao.getCarMaker(domesticFlag);
		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return codeList;
	}

	/**
	 * 함수명 : getCarCategory 설명 : 고객사(차량 Maker)별 차종을 가져오는 함수
	 * 
	 * @param carMakerId
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 26.
	 */
	public ArrayList<Hashtable<String, String>> getCarCategory(String carMakerId) throws Exception {
		Connection conn = null;
		EcmComDao cDao = null;
		ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();

		try {
			conn = PlmDBUtil.getConnection();

			cDao = new EcmComDao(conn);

			codeList = cDao.getCarCategory(carMakerId);
		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return codeList;
	}

	/**
	 * 함수명 : getRefPartList 설명 :관련부품 정보 얻어호는 함수
	 * 
	 * @param partList
	 * @param expectCostList
	 * @param secureBudgetYn
	 * @return 작성자 : 오승연 작성일자 : 2010. 11. 26.
	 */
	public ArrayList<Hashtable<String, String>> getRefPartList(String[] partList, String[] dieNoList, String[] expectCostList, String[] secureBudgetYn) {
		ArrayList<Hashtable<String, String>> refPartList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> refPart = null;

		if (partList != null && partList.length > 0) {
			for (int pCnt = 0; pCnt < partList.length; pCnt++) {
				refPart = new Hashtable<String, String>();

				refPart.put("partOid", partList[pCnt]);

				if (dieNoList != null && dieNoList.length > 0) {
					refPart.put("dieNo", StringUtil.checkNull(dieNoList[pCnt]));
				}

				if (expectCostList != null && expectCostList.length > 0) {
					refPart.put("expectCost", StringUtil.checkNull(expectCostList[pCnt]));
				}

				refPart.put("secureBudgetYn", StringUtil.checkReplaceStr(secureBudgetYn[pCnt], "N"));

				refPartList.add(refPart);
			}
		}

		return refPartList;
	}

	/**
	 * 함수명 : getRefObjList 설명 : 변경할 도면/BOM 리스트 Request 정보를 가져오는 함수
	 * 
	 * @param actTypeList
	 *            도면/BOM 구분 List
	 * @param refOjbOidList
	 *            EPMDoc/WTPart Oid List
	 * @param refPreVerList
	 *            EPMDoc/WTPart 변경할 버전 List
	 * @param planDateList
	 *            EPMDoc/WTPart 변경예정일 List
	 * @param commentList
	 *            EPMDoc/WTPart 변경할 내용 List
	 * @param dieNoList
	 *            EPMDoc와 연계된 금형일정과 관련된 Die No
	 * @param scheduleIdList
	 *            EPMDoc와 연계된 금형일정 Id
	 * @return 작성자 : 오승연 작성일자 : 2010. 11. 26.
	 * 
	 * @deprecated
	 */
	public Hashtable<String, ArrayList<Hashtable<String, String>>> getRefObjList(String[] actTypeList, String[] refOjbOidList, String[] refPreVerList, String[] workerOidList,
			String[] massChgYnList, String[] paretPartList, String[] planDateList, String[] commentList, String[] dieNoList, String[] scheduleIdList, String[] epmDocTypeList) {
		Hashtable<String, ArrayList<Hashtable<String, String>>> objHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();
		ArrayList<Hashtable<String, String>> epmDocList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> epmDoc = null;
		Hashtable<String, String> bom = null;

		if (actTypeList != null && actTypeList.length > 0) {
			for (int oCnt = 0; oCnt < actTypeList.length; oCnt++) {
				// 도면
				if (actTypeList[oCnt].equals("1")) {
					epmDoc = new Hashtable<String, String>();
					if (refOjbOidList != null && refOjbOidList.length > 0)
						epmDoc.put("epmDocOid", refOjbOidList[oCnt]);
					if (refPreVerList != null && refPreVerList.length > 0)
						epmDoc.put("preVersion", refPreVerList[oCnt]);
					if (workerOidList != null && workerOidList.length > 0)
						epmDoc.put("workerOid", workerOidList[oCnt]);
					if (planDateList != null && planDateList.length > 0)
						epmDoc.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					if (commentList != null && commentList.length > 0)
						epmDoc.put("comment", StringUtil.checkNull(commentList[oCnt]));
					if (dieNoList != null && dieNoList.length > 0)
						epmDoc.put("dieNo", StringUtil.checkNull(dieNoList[oCnt]));
					if (scheduleIdList != null && scheduleIdList.length > 0)
						epmDoc.put("scheduleId", StringUtil.checkNull(scheduleIdList[oCnt]));
					if (epmDocTypeList != null && epmDocTypeList.length > 0)
						epmDoc.put("epmDocType", StringUtil.checkNull(epmDocTypeList[oCnt]));

					epmDocList.add(epmDoc);
				}
				// BOM
				else {
					bom = new Hashtable<String, String>();
					if (refOjbOidList != null && refOjbOidList.length > 0)
						bom.put("partOid", refOjbOidList[oCnt]);
					if (refPreVerList != null && refPreVerList.length > 0)
						bom.put("preVersion", refPreVerList[oCnt]);
					if (workerOidList != null && workerOidList.length > 0)
						bom.put("workerOid", workerOidList[oCnt]);
					if (massChgYnList != null && massChgYnList.length > 0)
						bom.put("massChgYn", massChgYnList[oCnt]);
					if (paretPartList != null && paretPartList.length > 0)
						bom.put("parentPartList", paretPartList[oCnt]);
					if (planDateList != null && planDateList.length > 0)
						bom.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					if (commentList != null && commentList.length > 0)
						bom.put("comment", StringUtil.checkNull(commentList[oCnt]));
					if (refOjbOidList != null && refOjbOidList.length > 0)
						bom.put("beforPartOid", refOjbOidList[oCnt]);

					bom.put("linkOid", "");

					bomList.add(bom);
				}
			}

			objHash.put("refEpmDocList", epmDocList);
			objHash.put("refBomList", bomList);
		}

		return objHash;
	}

	/**
	 * 함수명 : getRefDocList 설명 : 후속 변경활동 할 KETProjectDocument 객체 정보를 얻는 함수
	 * 
	 * @param strDocList
	 * @return 작성자 : 오승연 작성일자 : 2010. 11. 26.
	 */
	public ArrayList<Hashtable<String, String>> getRefDocList(String strDocList) {
		ArrayList<Hashtable<String, String>> refDocList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> refDoc = null;
		ArrayList<String> strDocToken = new ArrayList<String>();

		StringTokenizer st = new StringTokenizer(strDocList, "↕");

		while (st.hasMoreTokens()) {
			strDocToken.add(st.nextToken());
		}

		for (int sCnt = 0; sCnt < strDocToken.size(); sCnt++) {
			st = new StringTokenizer(strDocToken.get(sCnt), "|");

			refDoc = new Hashtable<String, String>();
			refDocList.add(refDoc);

			int tokenCnt = 0;
			while (st.hasMoreTokens()) {

				String strDocArr = st.nextToken();

				if (tokenCnt == 0) {
					refDoc.put("docType", strDocArr);

				} else if (tokenCnt == 1) {
					refDoc.put("docOid", strDocArr);

				} else if (tokenCnt == 2) {
					refDoc.put("preVersion", strDocArr);

				} else if (tokenCnt == 3) {
					if (StringUtil.checkNull(strDocArr).equals("-")) {
						refDoc.put("otherDocType", "");
					} else {
						refDoc.put("otherDocType", StringUtil.checkNull(strDocArr));
					}

				} else if (tokenCnt == 4) {
					refDoc.put("afterVersion", StringUtil.changeString(strDocArr, "-", ""));

				} else if (tokenCnt == 5) {
					if (StringUtil.checkNull(strDocArr).equals("-")) {
						// refDoc.put("workerOid", "");
					} else {
						refDoc.put("workerOid", strDocArr);
					}

				}
				// 타입
				else if (tokenCnt == 6) {
					refDoc.put("docActFlag", strDocArr);

				}
				// 사용자 입력 후속업무
				else if (tokenCnt == 7) {
					if (StringUtil.checkNull(strDocArr).equals("-")) {
						// refDoc.put("customName", "");
					} else {
						refDoc.put("customName", strDocArr);
					}

				}
				// 완료요청일
				else if (tokenCnt == 8) {
					// 기존에 클라이언트에서 변환시 값이 ""이면 "-"로 컨버팅하여 서버에 보내고 있다.
					if (StringUtil.checkNull(strDocArr).equals("-")) {
						// refDoc.put("completeRequestDate", "");
					} else {
						refDoc.put("completeRequestDate", strDocArr);
					}

				}
				// KETProdChangeActivity OID
				else if (tokenCnt == 9) {
					// 기존에 클라이언트에서 변환시 값이 ""이면 "-"로 컨버팅하여 서버에 보내고 있다.
					if (StringUtil.checkNull(strDocArr).equals("-")) {
						// refDoc.put("ecaUniqueKey", "");
					} else {
						refDoc.put("ecaUniqueKey", strDocArr);
					}

				}

				tokenCnt++;
			}
		}

		return refDocList;
	}

	/**
	 * 함수명 : getNewProdEcoId 설명 :
	 * 
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 29.
	 */
	public String getNewProdEcoId() throws Exception {
		String ecoId = "";
		Connection conn = null;
		ProdEcoDao ecrDao = null;

		try {
			conn = PlmDBUtil.getConnection();

			ecrDao = new ProdEcoDao(conn);

			ecoId = ecrDao.getProdEcoId();
		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return ecoId;
	}

	/**
	 * 함수명 : createProdEpmDocEcaLink 설명 : 도면활동 Link를 생성하는 함수
	 * 
	 * @param prodEco
	 * @param refEpmDocList
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 29.
	 */
	public ArrayList<KETProdChangeActivity> createProdEpmDocEcaLink(KETProdChangeOrder prodEco, ArrayList<Hashtable<String, String>> refEpmDocList) throws Exception {
		ArrayList<KETProdChangeActivity> ecaList = null;

		try {
			if (refEpmDocList != null) {
				boolean isNewEpmDoc = false;
				for (int dCnt = 0; dCnt < refEpmDocList.size(); dCnt++) {
					Hashtable<String, String> refEpmDocHash = refEpmDocList.get(dCnt);

					// KETProdChangeActivity 객체화
					KETProdChangeActivity eca = null;
					String prodEcaOid = refEpmDocHash.get("prodEcaOid");
					if (prodEcaOid == null || prodEcaOid.equals("")) {
						// 없을 경우 생성한다.
						eca = createProdEca(prodEco, refEpmDocHash, "1");

					} else {
						// 있을 경우 가져온다.
						eca = (KETProdChangeActivity) CommonUtil.getObject(prodEcaOid);
						eca.setWorkerId(refEpmDocHash.get("workerOid"));

						PersistenceHelper.manager.save(eca);
						eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);

					}

					if (ecaList == null)
						ecaList = new ArrayList<KETProdChangeActivity>();
					ecaList.add(eca);

					EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(refEpmDocHash.get("epmDocOid"));

					KETProdECAEpmDocLink epmDocLink = null;
					// epmDocLink = EcmSearchHelper.manager.getProdEcaEpmDocLink(epmDoc);

					try {
						QueryResult qr = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
						while (qr.hasMoreElements()) {
							epmDocLink = (KETProdECAEpmDocLink) qr.nextElement();
						}
					} catch (WTException e) {
						Kogger.error(getClass(), e);
					}

					if (epmDocLink == null) {
						epmDocLink = KETProdECAEpmDocLink.newKETProdECAEpmDocLink(epmDoc, eca);
						isNewEpmDoc = true;
					}

					epmDocLink.setActivityComment(refEpmDocHash.get("comment"));
					epmDocLink.setPreVersion(refEpmDocHash.get("preVersion"));
					epmDocLink.setPlanDate(DateUtil.convertDate2(refEpmDocHash.get("planDate")));
					epmDocLink.setDieNo(refEpmDocHash.get("dieNo"));
					epmDocLink.setScheduleId(refEpmDocHash.get("scheduleId"));

					if (StringUtil.checkNull(refEpmDocHash.get("scheduleId")).length() > 0) {
						KETMoldChangePlan plan = getMoldPlan(refEpmDocHash.get("scheduleId"));

						KETProdECOPlanLink planLink = getProdEcoPlanLink(prodEco);

						if (planLink != null) {
							planLink.setMoldPlan(plan);
							PersistenceHelper.manager.modify(planLink);

						} else {
							planLink = KETProdECOPlanLink.newKETProdECOPlanLink(prodEco, plan);
							PersistenceHelper.manager.save(planLink);
						}
					}
					
					String epmDocType = refEpmDocHash.get("epmDocType");
					
					EPMAuthoringAppType cadAppTypeEnum = epmDoc.getAuthoringApplication();
					
					String cadAppType = cadAppTypeEnum.toString(); // PROE

					if ("PROE".equals(cadAppType)) {
					    epmDocType = "3D";
					}
					
					epmDocLink.setEpmDocType(epmDocType);

					if (isNewEpmDoc) {
						PersistenceHelper.manager.save(epmDocLink);
					} else {
						PersistenceHelper.manager.modify(epmDocLink);
					}

					isNewEpmDoc = false;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return ecaList;
	}

	/**
	 * 함수명 : createProdBomHeaderEcaLink 설명 : BOM 변경활동 Link를 생성하는 함수
	 * 
	 * @param prodEco
	 * @param refBomList
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 30.
	 */
	public ArrayList<KETProdChangeActivity> createProdBomHeaderEcaLink(KETProdChangeOrder prodEco, ArrayList<Hashtable<String, String>> refBomList, Connection conn)
			throws Exception {
		ArrayList<KETProdChangeActivity> ecaList = null;

		Hashtable<String, String> refBomHash = null;
		WTPart part = null;
		KETProdECABomLink bomLink = null;
		KETBomEcoHeader bomHeader = null;
		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		try {
			String folderPath = ECMProperties.getInstance().getString("bom.folder");
			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.bom");

			Kogger.debug(getClass(), "======================= LifeCycle Name 1 " + lcName);

			conn.setAutoCommit(false);
			EcmComDao comDao = new EcmComDao(conn);
			KETBOMHeaderQueryBean bomBean = new KETBOMHeaderQueryBean();
			KETBomHeader newHeader = null;
			Hashtable<String, String> coWokerHash = null;

			String boxQty = "";

			int successCnt = 0;
			boolean isSuccess = false;
			boolean isTodo = false;
			if (refBomList != null && refBomList.size() > 0) {
			    
			        isTodo = "ToDo".equalsIgnoreCase(StringUtil.checkNull(refBomList.get(0).get("prePageFlag")));
				for (int dCnt = 0; dCnt < refBomList.size(); dCnt++) {
					refBomHash = refBomList.get(dCnt);

					/*
					 * prodEca = createProdEca(prodEco, refBomHash, "2");
					 * 
					 * String parentPartNo = ""; Kogger.debug(getClass(), "createProdBomHeaderEcaLink ---------------------1 > ");
					 * 
					 * Kogger.debug(getClass(), "parentPartList" + refBomHash.get("parentPartList")); Kogger.debug(getClass(), "massChgYn" + refBomHash.get("massChgYn"));
					 * 
					 * if (refBomHash.get("parentPartList") != null && refBomHash.get("parentPartList").length() > 0 && refBomHash.get("massChgYn") != null &&
					 * refBomHash.get("massChgYn").equals("Y")) {
					 * 
					 * Kogger.debug(getClass(), "createProdBomHeaderEcaLink ---------------------2 > ");
					 * 
					 * StringTokenizer st = new StringTokenizer(refBomHash.get("parentPartList"), ",");
					 * 
					 * while (st.hasMoreTokens()) { parentPartNo = st.nextToken();
					 * 
					 * part = getLastestPart(parentPartNo, conn);
					 * 
					 * // Create KETBOMEcoHeader bomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), part.getNumber());
					 * 
					 * if (bomHeader == null) { bomHeader = KETBomEcoHeader.newKETBomEcoHeader();
					 * 
					 * bomHeader.setContainerReference(containerRef); bomHeader.setContainer(containerRef.getReferencedContainerReadOnly());
					 * Kogger.debug(getClass(), "======================= LifeCycle Name 2 " + lcName); LifeCycleHelper.setLifeCycle(bomHeader,
					 * LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));
					 * 
					 * Folder folder = FolderHelper.service.getFolder(folderPath, containerRef); FolderHelper.assignLocation((FolderEntry) bomHeader, folder);
					 * 
					 * bomHeader.setEcoHeaderNumber(prodEco.getEcoId()); bomHeader.setEcoItemCode(part.getNumber()); bomHeader.setDescription(part.getName());
					 * bomHeader.setBOMVersion(VersionUtil.getMajorVersion(part)); bomHeader.setIsMultiple(refBomHash.get("massChgYn")); bomHeader.setTransferFlag("1");
					 * bomHeader.setOrgCode("000"); bomHeader.setUnitOfQuantity(String.valueOf(part.getDefaultUnit())); // 기본단위 셋팅
					 * 
					 * boxQty = BOMSearchUtilDao.getEcoHeaderBoxQuantity(part.getNumber(), conn);
					 * 
					 * if (boxQty.length() > 0) { bomHeader.setBoxQuantity(boxQty); bomHeader.setAttribute2(boxQty); } else { newHeader = bomBean.getBOMHeader(part.getNumber()); if
					 * (newHeader != null) { bomHeader.setBoxQuantity(newHeader.getBoxQuantity()); bomHeader.setAttribute2(newHeader.getBoxQuantity()); } else { //
					 * bomHeader.setBoxQuantity("1"); // bomHeader.setAttribute2( "1" ); boxQty = BOMSearchUtilDao.getBoxQuantityPartUsageLink(part.getNumber(), conn);
					 * bomHeader.setBoxQuantity(boxQty); bomHeader.setAttribute2(boxQty); } }
					 * 
					 * bomHeader.setQuantity("1.0"); // 기준수량 '1'로 셋팅 // bomHeader.setBoxQuantity((bomBean.getBOMHeader(part.getNumber())).getBoxQuantity());
					 * bomHeader.setBOMUse("2"); bomHeader.setBOMText(comDao.getChangeReasonName(prodEco.getChangeReason(), "PRODECOREASON"));
					 * 
					 * bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);
					 * 
					 * coWokerHash = EcmUtil.getCoWorkerInfo(prodEco.getEcoId(), part.getNumber(), refBomHash.get("workerOid"));
					 * 
					 * isSuccess = comDao.insertBomCoWoker(coWokerHash);
					 * 
					 * if (isSuccess) { successCnt++; }
					 * 
					 * // Create KETProdECABomLink bomLink = KETProdECABomLink.newKETProdECABomLink(bomHeader, prodEca); bomLink.setPreVersion(bomHeader.getBOMVersion());
					 * bomLink.setPlanDate(DateUtil.convertDate2(refBomHash.get("planDate"))); bomLink.setActivityComment(refBomHash.get("comment"));
					 * bomLink.setMassChangeYn(refBomHash.get("massChgYn")); bomLink.setBeforePartOid(refBomHash.get("beforPartOid"));
					 * bomLink.setRelatedParentPartList(refBomHash.get("parentPartList"));
					 * 
					 * PersistenceHelper.manager.save(bomLink); } else { bomLink = EcmSearchHelper.manager.getProdEcaBomLink(bomHeader);
					 * 
					 * bomLink.setPreVersion(refBomHash.get("preVersion")); bomLink.setPlanDate(DateUtil.convertDate2(refBomHash.get("planDate")));
					 * bomLink.setActivityComment(refBomHash.get("comment")); bomLink.setMassChangeYn(refBomHash.get("massChgYn"));
					 * bomLink.setBeforePartOid(refBomHash.get("beforPartOid")); bomLink.setRelatedParentPartList(refBomHash.get("parentPartList"));
					 * 
					 * PersistenceHelper.manager.modify(bomLink);
					 * 
					 * successCnt++; }
					 * 
					 * isSuccess = false; } } else {
					 */

					Kogger.debug(getClass(), "createProdBomHeaderEcaLink ---------------------3 > ");
					part = (WTPart) CommonUtil.getObject(refBomHash.get("partOid"));

					if (part == null) {
						Kogger.debug(getClass(), "Part 없음");
					}

					// KETProdChangeActivity 객체화
					KETProdChangeActivity eca = null;
					String old_worker_oid = "";
					String prodEcaOid = refBomHash.get("prodEcaOid");
					if (prodEcaOid == null || prodEcaOid.equals("")) {
						// 없을 경우 생성한다.
						eca = createProdEca(prodEco, refBomHash, "2");

					} else {
						// 있을 경우 가져온다.
						eca = (KETProdChangeActivity) CommonUtil.getObject(prodEcaOid);

						old_worker_oid = eca.getWorkerId();
						eca.setWorkerId(refBomHash.get("workerOid"));

						PersistenceHelper.manager.save(eca);
						eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);

					}

					if (ecaList == null)
						ecaList = new ArrayList<KETProdChangeActivity>();
					ecaList.add(eca);

					// Create KETBOMEcoHeader
					bomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), part.getNumber());
					if (bomHeader == null) {

						// 부품이 초도일 경우 KETBomHeader, 아닐 경우 KETEcoHeader에 set 한다.
						/*
						 * String bomflag = PartSpecGetter.getPartSpec((WTPart) part, PartSpecEnum.SpBOMFlag); if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {
						 */
						// 초도인지 아닌지
						String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
						boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
						if (!isTheFirst) {
							bomHeader = KETBomEcoHeader.newKETBomEcoHeader();
						} else {
							bomHeader = KETBomHeader.newKETBomHeader();

							KETBomHeader ketBomHeader = (KETBomHeader) bomHeader;
							ketBomHeader.setNewBOMCode(part.getNumber());
							bomHeader.setAttribute1("Y"); // 수정가능하게 한다.

							bomHeader = (KETBomEcoHeader) ketBomHeader;
						}

						bomHeader.setEcoHeaderNumber(prodEco.getEcoId());
						// Start 설변사유가 초도가 아니지만 실제부품의 버전이 초도이면, 개정을 할 수 없으므로 이 시점에서 BOM수정이 가능하게 해줘야한다 2014.12.31 황정태 수정
						if ("0".equals(part.getVersionIdentifier().getValue()) && State.INWORK == part.getLifeCycleState()) {
							bomHeader.setAttribute1("Y"); // 수정가능하게 한다.
						}
						// End

						bomHeader.setEcoItemCode(part.getNumber());
						bomHeader.setDescription(part.getName());

						bomHeader.setBOMVersion(VersionUtil.getMajorVersion(part));
						bomHeader.setContainerReference(containerRef);
						bomHeader.setIsMultiple(refBomHash.get("massChgYn"));
						bomHeader.setTransferFlag("1");
						bomHeader.setOrgCode("000");
						bomHeader.setUnitOfQuantity(String.valueOf(part.getDefaultUnit())); // 기본단위 셋팅
						bomHeader.setQuantity("1.0"); // 기준수량 '1'로 셋팅

						boxQty = BOMSearchUtilDao.getEcoHeaderBoxQuantity(part.getNumber(), conn);

						if (boxQty.length() > 0) {
							bomHeader.setBoxQuantity(boxQty);
							bomHeader.setAttribute2(boxQty);
						} else {
							newHeader = bomBean.getBOMHeader(part.getNumber());
							if (newHeader != null) {
								bomHeader.setBoxQuantity(newHeader.getBoxQuantity());
								bomHeader.setAttribute2(newHeader.getBoxQuantity());
							} else {
								// bomHeader.setBoxQuantity("1");
								// bomHeader.setAttribute2( "1" );
								boxQty = BOMSearchUtilDao.getBoxQuantityPartUsageLink(part.getNumber(), conn);
								bomHeader.setBoxQuantity(boxQty);
								bomHeader.setAttribute2(boxQty);
							}
						}

						bomHeader.setBOMUse("2");
						bomHeader.setBOMText(comDao.getChangeReasonName(prodEco.getChangeReason(), "PRODECOREASON"));

						bomHeader.setContainerReference(containerRef);
						bomHeader.setContainer(containerRef.getReferencedContainerReadOnly());
						Kogger.debug(getClass(), "======================= LifeCycle Name 2 " + lcName);
						LifeCycleHelper.setLifeCycle(bomHeader, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

						Folder folder = FolderHelper.service.getFolder(folderPath, containerRef);
						FolderHelper.assignLocation((FolderEntry) bomHeader, folder);
						bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);

						coWokerHash = EcmUtil.getCoWorkerInfo(prodEco.getEcoId(), part.getNumber(), refBomHash.get("workerOid"));
						isSuccess = comDao.insertBomCoWoker(coWokerHash);

						if (isSuccess) {
							successCnt++;
						}

						// Create KETProdECABomLink
						bomLink = KETProdECABomLink.newKETProdECABomLink(bomHeader, eca);
						bomLink.setPreVersion(refBomHash.get("preVersion"));
						bomLink.setPlanDate(DateUtil.convertDate2(refBomHash.get("planDate")));
						bomLink.setActivityComment(refBomHash.get("comment"));
						bomLink.setMassChangeYn(refBomHash.get("massChgYn"));
						bomLink.setBeforePartOid(refBomHash.get("beforPartOid"));

						// Die No
						bomLink.setRelatedDieNo(refBomHash.get("relDieNoEca"));
						bomLink.setExpectCost(refBomHash.get("expectCostEca"));
						bomLink.setSecureBudgetYn(refBomHash.get("secureBudgetYnEca"));

						PersistenceHelper.manager.save(bomLink);

						isSuccess = false;
					} else {
						Kogger.debug(getClass(), "createProdBomHeaderEcaLink ---------------------4 > ");
						bomLink = EcmSearchHelper.manager.getProdEcaBomLink(bomHeader);
						
						WorkItem item = KETWorkflowHelper.service.getWorkItem(bomLink.getProdECA() , sessionUser);
						if (bomLink == null) {
							Kogger.debug(getClass(), "Part 없음");
						}

						bomLink.setPreVersion(refBomHash.get("preVersion"));
						bomLink.setPlanDate(DateUtil.convertDate2(refBomHash.get("planDate")));
						bomLink.setActivityComment(refBomHash.get("comment"));
						bomLink.setMassChangeYn(refBomHash.get("massChgYn"));
						bomLink.setBeforePartOid(refBomHash.get("beforPartOid"));

						// Die No
						if(!isTodo){
						    bomLink.setRelatedDieNo(refBomHash.get("relDieNoEca"));
						    bomLink.setExpectCost(refBomHash.get("expectCostEca"));
						    bomLink.setSecureBudgetYn(refBomHash.get("secureBudgetYnEca"));    
						}
						

						PersistenceHelper.manager.modify(bomLink);

						coWokerHash = EcmUtil.getCoWorkerInfo(prodEco.getEcoId(), part.getNumber(), refBomHash.get("workerOid"));
						String old_worker_id = "";
						try {
							WTUser worker = (WTUser) KETObjectUtil.getObject(old_worker_oid);
							PeopleData pData = new PeopleData(worker);
							if (worker != null) {
								old_worker_id = worker.getName();
							}
						} catch (Exception e) {
							Kogger.error(getClass(), e);
						}
						coWokerHash.put("old_worker_id", old_worker_id);
						isSuccess = comDao.updateBomCoWoker(coWokerHash);

						if (isSuccess) {
							successCnt++;
						}

						successCnt++;
					}

					/*
					 * } // if (refBomHash.get("parentPartList") != null && refBomHash.get("parentPartList").length() > 0 && refBomHash.get("massChgYn") != null &&
					 * refBomHash.get("massChgYn").equals("Y")) {
					 */
					/*
					 * if (successCnt == refBomList.size()) { isCreated = true; }
					 */
				}
			}
		} catch (WTException e) {
			throw new Exception(e);
		}

		return ecaList;
	}

	/**
	 * 함수명 : createProdPjtDocEcaLink 설명 : 문서 변경활동(후속) Link를 생성하는 함수
	 * 
	 * @param prodEco
	 * @param refDocList
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 30.
	 */
	public ArrayList<KETProdChangeActivity> createProdPjtDocEcaLink(KETProdChangeOrder prodEco, ArrayList<Hashtable<String, String>> refDocList) throws Exception {
		ArrayList<KETProdChangeActivity> ecaList = null;

		Hashtable<String, String> refDocHash = null;
		KETProjectDocument pjtDoc = null;
		KETProdECADocLink pjtDocLink = null;

		try {
			if (refDocList != null) {
				for (int dCnt = 0; dCnt < refDocList.size(); dCnt++) {
					refDocHash = refDocList.get(dCnt);

					String docActFlag = refDocHash.get("docActFlag");
					String activityType = (docActFlag == null || docActFlag.equalsIgnoreCase("-") || docActFlag.equalsIgnoreCase("DOC")) ? "3" : "4";

					// ECN 저장
					KETProdChangeActivity eca = createProdEca(prodEco, refDocHash, activityType);

					/*
					 * // KETProdChangeActivity 객체화 KETProdChangeActivity eca = null; String prodEcaOid = refEpmDocHash.get("prodEcaOid"); if (prodEcaOid == null ||
					 * prodEcaOid.equals("")) { // 없을 경우 생성한다. eca = createProdEca(prodEco, refEpmDocHash, "1");
					 * 
					 * } else { // 있을 경우 가져온다. eca = (KETProdChangeActivity) CommonUtil.getObject(prodEcaOid);
					 * 
					 * }
					 */

					if (ecaList == null)
						ecaList = new ArrayList<KETProdChangeActivity>();
					ecaList.add(eca);

					// ECN 에 문서 연결
					Kogger.debug(getClass(), "docOid --> " + refDocHash.get("docOid"));
					if (refDocHash.get("docOid") != null && !refDocHash.get("docOid").equals("-")) {

						pjtDoc = (KETProjectDocument) CommonUtil.getObject(refDocHash.get("docOid"));

						pjtDocLink = EcmSearchHelper.manager.getProdEcaDocLink(prodEco, pjtDoc);

						if (pjtDocLink == null) {
							pjtDocLink = KETProdECADocLink.newKETProdECADocLink(pjtDoc, eca);
						} else {
							pjtDocLink.setProdECA(eca);
						}

						Kogger.debug(getClass(), "docType --> " + refDocHash.get("docType"));
						pjtDocLink.setDocType(refDocHash.get("docType"));
						Kogger.debug(getClass(), "preVersion --> " + refDocHash.get("preVersion"));
						pjtDocLink.setPreVersion(refDocHash.get("preVersion"));
						Kogger.debug(getClass(), "afterVersion --> " + refDocHash.get("afterVersion"));
						pjtDocLink.setAfterVersion(refDocHash.get("afterVersion"));

						// 상세구분(내용)
						Kogger.debug(getClass(), "otherDocType --> " + refDocHash.get("otherDocType"));
						pjtDocLink.setDocTypeDesc(StringUtil.checkNull(refDocHash.get("otherDocType")));

						PersistenceHelper.manager.save(pjtDocLink);
					}

				}
			}
		} catch (Exception e) {
			throw e;
		}

		return ecaList;
	}

	public void modifyNotOwnerProdPjtDocEcaLink(KETProdChangeOrder prodEco, ArrayList<Hashtable<String, String>> refDocList) throws Exception {

		this.modifyNotOwnerProdPjtDocEcaLink2(prodEco, refDocList, null);
	}

	public String modifyNotOwnerProdPjtDocEcaLink2(KETProdChangeOrder prodEco, ArrayList<Hashtable<String, String>> refDocList, KETParamMapUtil paramMap) throws Exception {
		KETMessageService messageService = KETMessageService.getMessageService();
		String ALERT_MESSAGE = "";

		try {

			// 문서 처리
			int refDocListSize = (refDocList != null) ? refDocList.size() : 0;
			
			String[] activityTemps = paramMap.getStringArray("activityTemp");
			
			for (int dCnt = 0; dCnt < refDocListSize; dCnt++) {
			    	
			    	boolean isTypeChange = false;

				Hashtable<String, String> refDocHash = refDocList.get(dCnt);

				KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(refDocHash.get("ecaUniqueKey"));
				KETProjectDocument doc = null;
				if (!refDocHash.get("docOid").equals("-")) {
					doc = (KETProjectDocument) CommonUtil.getObject(refDocHash.get("docOid"));

				}

				if(!eca.getActivityType().equals(activityTemps[dCnt])){//후속활동 유형이 변경되었을 경우 KETProdECADocLink를 삭제하고 타입을 변경한다 2018-08-16 by 황정태
				    isTypeChange = true;
				}

				QueryResult linkResult = PersistenceHelper.manager.navigate(eca, "doc", KETProdECADocLink.class, false);
				
				if(isTypeChange){
				    while (linkResult.hasMoreElements()) {
					KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) linkResult.nextElement();
					KETProjectDocument savedDoc = ketProdECADocLink.getDoc();
					
					if(savedDoc != null){
					    
					    State StateFlag = savedDoc.getState().getState();
					    
					    String afterVersion = StringUtils.stripToEmpty(ketProdECADocLink.getAfterVersion()); 
					    if (!afterVersion.equals("") || !StateFlag.equals(State.toState("APPROVED"))) { // 문서를 개정하였을 경우 삭제할 수 없습니다.
						ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "문서를 개정하였을 경우 삭제할 수 없습니다.");
					    }
					    
					    
					}
					
					
					PersistenceHelper.manager.delete(ketProdECADocLink);
				    }
				    eca.setActivityType(activityTemps[dCnt]);
				    eca = (KETProdChangeActivity) PersistenceHelper.manager.save(eca);
				}else{
				    	int linkResultSize = (linkResult != null) ? linkResult.size() : 0;
					if (linkResultSize == 0) {
						// 기존 연결도 없고 넘겨받은 문서도 없을 경우
						if (doc == null) {
							// Do nothing..!!
						}
						// 기존 연결은 없으나 넘겨받은 문서가 있을 경우
						else {

							// ECN 생성
							String docActFlag = refDocHash.get("docActFlag");
							String activityType = (docActFlag == null || docActFlag.equalsIgnoreCase("-") || docActFlag.equalsIgnoreCase("DOC")) ? "3" : "4";

							KETProdECADocLink ketProdECADocLink = KETProdECADocLink.newKETProdECADocLink(doc, eca);

							Kogger.debug(getClass(), "docType --> " + refDocHash.get("docType"));
							ketProdECADocLink.setDocType(refDocHash.get("docType"));
							Kogger.debug(getClass(), "preVersion --> " + refDocHash.get("preVersion"));
							ketProdECADocLink.setPreVersion(refDocHash.get("preVersion"));
							Kogger.debug(getClass(), "afterVersion --> " + refDocHash.get("afterVersion"));
							ketProdECADocLink.setAfterVersion(refDocHash.get("afterVersion"));

							// 상세구분(내용)
							Kogger.debug(getClass(), "otherDocType --> " + refDocHash.get("otherDocType"));
							ketProdECADocLink.setDocTypeDesc(StringUtil.checkNull(refDocHash.get("otherDocType")));

							PersistenceHelper.manager.save(ketProdECADocLink);

						}

					} else {
						while (linkResult.hasMoreElements()) {
							KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) linkResult.nextElement();

							// 기존 연결은 있으나 넘겨받은 문서가 없을 경우
							if (doc == null) {
								/*
								 * String afterVersion = StringUtils.stripToEmpty(ketProdECADocLink.getAfterVersion()); if (!afterVersion.equals("")) { // 문서를 개정하였을 경우 삭제할 수 없습니다.
								 * ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "문서를 개정하였을 경우 삭제할 수 없습니다.");
								 * 
								 * } else { PersistenceHelper.manager.delete(ketProdECADocLink);
								 * 
								 * ObjectToObjectLink 는 한 쪽에 객체가 없을 경우 에러를 던진다. 그렇다고 위처럼 삭제를 하면 클라이언트에서 문서를 추가할 수 있는 UI도 사라진다. 따라서 문서를 삭제를 하지 못하도록 사용자에게 알려주도록 한다.
								 * 
								 * // 개정될 문서는 삭제할 수 없습니다. // ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04270"); }
								 */

								// 개정될 문서는 삭제할 수 없습니다.
								ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04270");
							}
							// 기존 연결도 있고 넘겨받은 문서도 있을 경우
							else {

								KETProjectDocument savedDoc = ketProdECADocLink.getDoc();
								// 저장되어있었던 문서와 넘겨받은 문서가 다를 경우
								if (!savedDoc.equals(doc)) {
									ketProdECADocLink.setDoc(doc);
									Kogger.debug(getClass(), "preVersion --> " + refDocHash.get("preVersion"));
									ketProdECADocLink.setPreVersion(refDocHash.get("preVersion"));
									Kogger.debug(getClass(), "afterVersion --> " + refDocHash.get("afterVersion"));
									ketProdECADocLink.setAfterVersion(refDocHash.get("afterVersion"));

									ketProdECADocLink = (KETProdECADocLink) PersistenceHelper.manager.save(ketProdECADocLink);
								}
								//
								else {
									// Do nothing..!!

								}

							}

						}

					}
				}
				

			}

			// 문서외 처리
			// 내용(ToDo 에서 사용자 입력 정보)
			String[] ecaUniqueKeyArr = paramMap.getStringArray("ecaUniqueKey");
			String[] activityBackDescArr = paramMap.getStringArray("activityBackDesc");

			int ecaUniqueKeyArrLength = (ecaUniqueKeyArr != null) ? ecaUniqueKeyArr.length : 0;
			for (int i = 0; i < ecaUniqueKeyArrLength; i++) {
				KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(ecaUniqueKeyArr[i]);

				eca.setActivityTypeDesc(activityBackDescArr[i]);
				eca.setActivityBackDesc(activityBackDescArr[i]);
				eca = (KETProdChangeActivity) PersistenceHelper.manager.save(eca);

			}

			// ECA 확장팩
			String[] ecaExpansionOids = paramMap.getStringArray("ecaExpansionOid");
			String[] someOids = paramMap.getStringArray("someOid");
			String[] subContractorCodes = paramMap.getStringArray("subContractorCode");
			String[] subContractorApprovalDates = paramMap.getStringArray("subContractorApprovalDate");
			String[] erpFlags = paramMap.getStringArray("erpFlag");

			String[] ecaOids = paramMap.getStringArray("ecaOid");
			int ecaOidsLength = (ecaOids != null) ? ecaOids.length : 0;
			for (int i = 0; i < ecaOidsLength; i++) {
				String ecaOid = ecaOids[i];
				if (ecaOid != null && !ecaOid.equals("")) {
					KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(ecaOid);

					// Delete
					QueryResult linkResult = PersistenceHelper.manager.navigate(eca, "some", KETChangeActivityExpansion.class, false);
					while (linkResult.hasMoreElements()) {
						KETChangeActivityExpansion ketChangeActivityExpansion = (KETChangeActivityExpansion) linkResult.nextElement();
						PersistenceHelper.manager.delete(ketChangeActivityExpansion);

					}

					// Insert
					String someOid = someOids[i];
					if (someOid != null && !someOid.equals("")) {
						Persistable some = (Persistable) CommonUtil.getObject(someOid);

						KETChangeActivityExpansion ecaExpansion = KETChangeActivityExpansion.newKETChangeActivityExpansion(some, eca);
						ecaExpansion.setSubContractorCode(subContractorCodes[i]);
						ecaExpansion.setSubContractorApprovalDate(DateUtil.convertStartDate2(subContractorApprovalDates[i]));
						// ecaExpansion.setErpFlag(erpFlag);

						ecaExpansion = (KETChangeActivityExpansion) PersistenceHelper.manager.save(ecaExpansion);

					}

				}
			}

		} catch (Exception e) {
			throw e;
		}

		return ALERT_MESSAGE;
	}

	/**
	 * 함수명 : createProdEca 설명 : ECO 객체에 WorkerId 가 담당자인 KETProdChangeActivity 객체를 만드는 함수 이미 객체가 존재하는 경우, 존재하는 객체를 가져온다
	 * 
	 * @param prodEco
	 * @param actObjHash
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 29.
	 */
	public KETProdChangeActivity createProdEca(KETProdChangeOrder prodEco, Hashtable<String, String> actObjHash, String activityType) throws Exception {
		KETProdChangeActivity prodEca = null;

		try {

			// 하나의 ECO의 여러 후속활동계획에 같은 담당자를 존재하지 못하게 하고 있는데, 클라이언트(자바스크립트)로 유효성 검사도 하지 않고 있다.
			// 사용자가 자신이 입력한 데이터가 저장되지 않는다고 생각할 것이다. 그리고 한 담당자가
			// 여러 후속업무도 할 수 있으므로 아래 로직은 제거한다.

			// prodEca = getProdEca(Long.parseLong(KETObjectUtil.getIda2a2(prodEco)), actObjHash.get("workerOid"), activityType);
			// if (prodEca == null) {
			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			String folderPath = "";
			if (prodEco.getDivisionFlag().equals("C")) {
				folderPath = ECMProperties.getInstance().getString("ecm.folder.car");// 자동차사업부
			} else if (prodEco.getDivisionFlag().equals("K")) {
				folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");// KETS
			} else {
				;
				folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");// 전자사업부
			}
			folderPath += DateUtil.getThisYear();
			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);

			prodEca = KETProdChangeActivity.newKETProdChangeActivity();
			prodEca.setProdECO(prodEco);
			prodEca.setActivityType(activityType);
			prodEca.setWorkerId(actObjHash.get("workerOid"));
			prodEca.setActivityStatus("PLANNING");

			// 후속업무, 사용자 입력시 유니크 코드
			prodEca.setCustomCode(actObjHash.get("docType"));
			// 사용자 입력 후속업무
			prodEca.setCustomName(actObjHash.get("customName"));
			// 후속 활동 기타사항---
			/*
			 * if (activityType.equals("4")) { prodEca.setActivityTypeDesc(actObjHash.get("otherDocType")); }
			 */
			// -- 현재는 사용하지 않음
			// 상세구분(내용)
			prodEca.setActivityTypeDesc(actObjHash.get("otherDocType"));
			// 완료요청일
			String completeRequestDate = actObjHash.get("completeRequestDate");
			prodEca.setCompleteRequestDate(DateUtil.convertStartDate2(completeRequestDate));

			prodEca.setContainerReference(containerRef);

			FolderHelper.assignLocation((FolderEntry) prodEca, folder);

			// 1. 결재적용 <- 잘못된 주석 : 그냥 단순히 저장일 뿐이다.
			String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.eca");
			LifeCycleHelper.setLifeCycle(prodEca, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			prodEca = (KETProdChangeActivity) PersistenceHelper.manager.save(prodEca);
			// }

		} catch (Exception e) {
			throw e;
		}

		return prodEca;
	}

	/**
	 * 함수명 : getProdEca 설명 : 해당 ECO Oid에 특정 WorkerId가 담당자인 기 존재하는 ECA 객체를 가져오는 함수
	 * 
	 * @param ecoIda2a2
	 * @param workerId
	 * @param activityType
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2010. 11. 30.
	 */
	public KETProdChangeActivity getProdEca2(long ecoIda2a2, String workerId, String activityType) throws Exception {
		KETProdChangeActivity prodEca = null;

		try {
			QuerySpec spec = new QuerySpec(KETProdChangeActivity.class);
			SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, KETProdChangeActivity.WORKER_ID, workerId, 0);

			// activityType 가 null 일 경우에는 activityType 데이터와 상관없이 가져온다.
			if (activityType != null) {
				SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, KETProdChangeActivity.ACTIVITY_TYPE, activityType, 0);
			}

			SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, "prodECOReference.key.id", ecoIda2a2, 0);

			QueryResult result = PersistenceHelper.manager.find(spec);

			if (result != null && result.hasMoreElements()) {
				prodEca = (KETProdChangeActivity) result.nextElement();
			}
		} catch (WTException e) {
			throw new Exception(e);
		}

		return prodEca;
	}

	/**
	 * 함수명 : getProdEca 설명 : 해당 ECO Oid에 특정 WorkerId가 담당자인 기 존재하는 ECA 객체를 가져오는 함수
	 * 
	 * @param ecoIda2a2
	 * @param workerId
	 * @param activityType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<KETProdChangeActivity> getProdEcaList(long ecoIda2a2, String workerId, String[] activityTypes) throws Exception {
		ArrayList<KETProdChangeActivity> prodEcaList = null;

		try {
			QuerySpec spec = new QuerySpec(KETProdChangeActivity.class);
			SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, KETProdChangeActivity.WORKER_ID, workerId, 0);

			// SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, KETProdChangeActivity.ACTIVITY_TYPE, activityTypes[i], 0);
			int activityTypesLength = (activityTypes != null) ? activityTypes.length : 0;
			for (int i = 0; i < activityTypesLength; i++) {

				if (i == 0) {
					spec.appendAnd();
					spec.appendOpenParen();
				}

				spec.appendWhere(new SearchCondition(KETProdChangeActivity.class, KETProdChangeActivity.ACTIVITY_TYPE, "=", activityTypes[i]), new int[] { 0 });

				if (i != (activityTypesLength - 1)) {
					spec.appendOr();
				}

				if (i == (activityTypesLength - 1)) {
					spec.appendCloseParen();
				}

			}

			SearchUtil.appendEQUAL(spec, KETProdChangeActivity.class, "prodECOReference.key.id", ecoIda2a2, 0);

			QueryResult result = PersistenceHelper.manager.find(spec);

			while (result != null && result.hasMoreElements()) {
				KETProdChangeActivity prodEca = (KETProdChangeActivity) result.nextElement();
				if (prodEcaList == null)
					prodEcaList = new ArrayList<KETProdChangeActivity>();

				prodEcaList.add(prodEca);

			}
		} catch (WTException e) {
			throw new Exception(e);
		}

		return prodEcaList;
	}

	public Hashtable<String, Object> getProdEco(KETProdChangeOrder ecoObj, String action, String locale) throws Exception {
		Connection conn = null;
		ProdEcoDao ecoDao = null;
		Hashtable<String, Object> prodEco = null;
		ArrayList<Hashtable<String, String>> ecrList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> epmDocList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> parentItemList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> docList = new ArrayList<Hashtable<String, String>>();

		String ida2a2 = KETObjectUtil.getIda2a2(ecoObj);
		String ecoStatus = "";

		try {
			conn = PlmDBUtil.getConnection();

			ecoDao = new ProdEcoDao(conn);
			prodEco = ecoDao.getProdEcoInfo(ida2a2);

			String strChgReasonName = ecoDao.getChangeReasonName(prodEco.get("chg_reason").toString(), "PRODECOREASON", locale);
			String strCustomChkName = ecoDao.getChangeReasonName(prodEco.get("cust_chk").toString(), "CUSTOMCHECK", locale);
			String strChgFactName = ecoDao.getChangeReasonName(prodEco.get("chg_fact").toString(), "CHANGEFACT", locale);

			// 설계변경 상세사유
			String strChgTypeName = "";
			String changeType = ecoObj.getChangeType();
			if (changeType != null) {
				strChgTypeName = ecoDao.getChangeReasonName(ecoObj.getChangeType(), "CHANGEDETAILREASON", locale);

				if (strChgTypeName.endsWith("/")) {
					strChgTypeName = strChgTypeName.substring(0, strChgTypeName.length() - 1);
				}

				// 보여주는 새로운 방식
				HashMap<String, ArrayList<String>> chg_type_hashmap = new HashMap<String, ArrayList<String>>();

				String[] changeTypes = StringUtils.splitPreserveAllTokens(changeType, "|");
				int changeTypesLength = (changeTypes != null) ? changeTypes.length : 0;
				for (int k = 0; k < changeTypesLength; k++) {

					String[] changes = StringUtils.splitPreserveAllTokens(changeTypes[k], "^");
					String chgReason = changes[1];
					String chgReasonName = ecoDao.getChangeReasonName(chgReason, "PRODECOREASON", locale);

					String chgTypeName = ecoDao.getChangeReasonName(changeTypes[k], "CHANGEDETAILREASON", locale);

					if (chg_type_hashmap.containsKey(chgReasonName)) {
						ArrayList<String> chgTypeNames = chg_type_hashmap.get(chgReasonName);
						if (!chgTypeNames.contains(chgTypeName))
							chgTypeNames.add(chgTypeName);

					} else {
						ArrayList<String> chgTypeNames = new ArrayList<String>();
						chgTypeNames.add(chgTypeName);
						chg_type_hashmap.put(chgReasonName, chgTypeNames);

					}

				}
				prodEco.put("chg_type_hashmap", chg_type_hashmap);

			}
			strChgTypeName = strChgTypeName.replaceAll("<br>", "");
			prodEco.put("chg_type_name", strChgTypeName);

			if (strChgReasonName.endsWith("/")) {
				strChgReasonName = strChgReasonName.substring(0, strChgReasonName.length() - 1);
			}

			if (strCustomChkName.endsWith("/")) {
				strCustomChkName = strCustomChkName.substring(0, strCustomChkName.length() - 1);
			}

			if (strChgFactName.endsWith("/")) {
				strChgFactName = strChgFactName.substring(0, strChgFactName.length() - 1);
			}

			prodEco.put("chg_reason_name", strChgReasonName);
			prodEco.put("cust_chk_name", strCustomChkName);
			prodEco.put("chg_fact_name", strChgFactName);

			// 설계변경 유형
			prodEco.put("chg_type", StringUtil.checkNull(ecoObj.getChangeType()));
			// 검토결과
			prodEco.put("review_result", StringUtil.checkNull(ecoObj.getReviewResult()));
			// 변경 전
			prodEco.put("web_editor", (ecoObj.getWebEditor() != null) ? ecoObj.getWebEditor() : "");
			prodEco.put("web_editor_text", (ecoObj.getWebEditorText() != null) ? ecoObj.getWebEditorText() : "");
			// 변경 후
			prodEco.put("web_editor_1", (ecoObj.getWebEditor1() != null) ? ecoObj.getWebEditor1() : "");
			prodEco.put("web_editor_text_1", (ecoObj.getWebEditorText1() != null) ? ecoObj.getWebEditorText1() : "");

			ecrList = ecoDao.getRefEcrList(ida2a2);
			partList = getRefPartList(ecoObj);

			// bomList = ecoDao.getProdEcaList( ida2a2, "BOM" );

			ecoStatus = ecoObj.getLifeCycleState().getDisplay();

			if (action.equalsIgnoreCase("View") || action.equalsIgnoreCase("UpdateView") || action.equalsIgnoreCase("PopupView") || action.equalsIgnoreCase("CompleteModifyView")
					|| action.equalsIgnoreCase("PrintView")) {

				// BOM List
				if (action.equalsIgnoreCase("View") || action.equalsIgnoreCase("PopupView") || action.equalsIgnoreCase("CompleteModifyView")
						|| action.equalsIgnoreCase("PrintView")) {

					// 아래는 BOM 에디팅하였고 작업완료한 것을 가져오고 있다.
					bomList = ecoDao.getProdEcaBomCompList(ida2a2, bomList); // BOM
					if (bomList == null || bomList.size() == 0) {
						// 아래는 BOM 에디팅하지는 않았지만 작업완료한 것을 가져오고 있다.
						bomList = ecoDao.getProdEcaBomHeaderList(ida2a2);
					}

				}

				// 도면/문서 List
				if (!action.equalsIgnoreCase("UpdateView")) {
					epmDocList = ecoDao.getProdEcaList(ida2a2, "도면");
					parentItemList = ecoDao.getProdEcaList(ida2a2, "BOM");
					docList = ecoDao.getProdEcaList(ida2a2, "문서");

				}
				// Todo 리스트에서 열 경우 action은 updateview 이다.
				else {
					
				    	WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
				    	
				    	if(!ecoObj.getState().getState().equals(State.toState("APPROVED")) && (loginUser.getName().equals(ecoObj.getCreatorName()) || action.equalsIgnoreCase("UpdateView"))){
				    	    epmDocList = ecoDao.getProdEcaList(ida2a2, "도면", KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()));
				    	    parentItemList = ecoDao.getProdEcaList(ida2a2, "BOM", KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()));    
				    	}else{
				    	    epmDocList = ecoDao.getProdEcaList(ida2a2, "도면");
				    	    // 아래는 BOM 에디팅하였고 작업완료한 것을 가져오고 있다.
				    	    bomList = ecoDao.getProdEcaBomCompList(ida2a2, bomList); // BOM
				    	    
				    	    if (bomList == null || bomList.size() == 0) {
						// 아래는 BOM 에디팅하지는 않았지만 작업완료한 것을 가져오고 있다.
						bomList = ecoDao.getProdEcaBomHeaderList(ida2a2);
				    	    }    
				    	}
				    	
					docList = ecoDao.getProdEcaList(ida2a2, "문서", KETObjectUtil.getOidString(KETObjectUtil.getLoginUser()));
				}

			} else {
				epmDocList = ecoDao.getProdEcaList(ida2a2, "도면/BOM");
				docList = ecoDao.getProdEcaList(ida2a2, "문서");
			}

			prodEco.put("ecrList", ecrList);
			prodEco.put("partList", partList);
			prodEco.put("epmDocList", epmDocList);
			prodEco.put("bomList", bomList);
			prodEco.put("parentItemList", parentItemList);
			prodEco.put("docList", docList);

			// SAP I/F의 성공여부(재전송 버튼을 보이게 하느냐 마느냐)
			boolean isSucessSapInterface = ecoDao.isSucessSapInterface(ecoObj.getEcoId());
			prodEco.put("isSucessSapInterface", isSucessSapInterface);

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return prodEco;
	}

	public ArrayList<Hashtable<String, String>> getRefPartList(KETProdChangeOrder prodEco) throws Exception {
		ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> part = null;
		QueryResult qr = null;
		KETProdECOPartLink partLink = null;

		try {
			qr = PersistenceHelper.manager.navigate(prodEco, "part", KETProdECOPartLink.class, false);

			while (qr.hasMoreElements()) {
				partLink = (KETProdECOPartLink) qr.nextElement();

				part = new Hashtable<String, String>();

				String part_oid = KETObjectUtil.getOidString(partLink.getPart());

				part.put("part_oid", part_oid);
				part.put("part_no", partLink.getPart().getNumber());
				part.put("part_name", partLink.getPart().getName());
				part.put("die_no", StringUtil.checkNull(partLink.getRelatedDieNo()));
				part.put("part_ver", VersionUtil.getMajorVersion(partLink.getPart()));
				part.put("expect_cost", StringUtil.checkNull(partLink.getExpectCost()));
				part.put("secure_budget_yn", partLink.getSecureBudgetYn());
				if (partLink.getSecureBudgetYn().equalsIgnoreCase("Y")) {
					part.put("budget_yn", "확보");
				} else {
					part.put("budget_yn", "미확보");
				}

				WTPart wtPart = (WTPart) CommonUtil.getObject(part_oid);
				String revDis = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpPartRevision));
				part.put("revDis", revDis);

				partList.add(part);

			}
		} catch (Exception e) {
			throw e;
		}

		return partList;
	}

	public Hashtable<String, ArrayList<String>> getAllDelObjLinkOidListHash(String ecoIda2a2, String delBomListStr, String delEpmDocListStr, String deleteRelDocListStr,
			boolean isCanDeleteDoc) throws Exception {
		Hashtable<String, ArrayList<String>> delObjLinkOidListHash = null;

		ArrayList<String> bomLinkOidList = new ArrayList<String>();
		ArrayList<String> epmDocLinkOidList = new ArrayList<String>();
		ArrayList<String> docLinkOidList = new ArrayList<String>();

		Connection conn = null;
		ProdEcoDao ecoDao = null;

		try {
			conn = PlmDBUtil.getConnection();
			ecoDao = new ProdEcoDao(conn);

			// 관련 ECR과 관련 제품, 관련품질문제는 Delete+Insert 방식을 쓰기 때문에 ECO에 붙은 모든 ECR과 제품을 삭제대상으로 컨버팅한다.
			ArrayList<String> ecrLinkOidList = ecoDao.getAllEcrLinkOidList(ecoIda2a2);
			ArrayList<String> dqmLinkOidList = ecoDao.getAllDqmLinkOidList(ecoIda2a2);
			ArrayList<String> partLinkOidList = ecoDao.getAllPartLinkOidList(ecoIda2a2);

			// 설계부품/도면과 ECN은 클라이언트에서 사용자가 직접 삭제한 것만을 삭제대상으로 컨버팅한다.
			// bomLinkOidList = ecoDao.getAllBomLinkOidList( ecoIda2a2 );
			// epmDocLinkOidList = ecoDao.getAllEpmDocLinkOidList( ecoIda2a2 );

			StringTokenizer st = null;
			if (delEpmDocListStr != null) {
				st = new StringTokenizer(delEpmDocListStr, "*");
				while (st.hasMoreTokens()) {
					/*
					 * String tempStr = st.nextToken();
					 * 
					 * if (tempStr.startsWith("1")) { epmDocLinkOidList.add(tempStr.substring(2)); } else { bomLinkOidList.add(tempStr.substring(2)); }
					 */
					epmDocLinkOidList.add(st.nextToken());

				}
			}

			if (delBomListStr != null) {
				st = new StringTokenizer(delBomListStr, "*");
				while (st.hasMoreTokens()) {
					bomLinkOidList.add(st.nextToken());
				}
			}

			if (isCanDeleteDoc) {
				docLinkOidList = ecoDao.getAllDocLinkOidList(ecoIda2a2);
			} else { // if (!isCanDeleteDoc) {
				if (deleteRelDocListStr != null) {
					st = new StringTokenizer(deleteRelDocListStr, "*");
					while (st.hasMoreTokens()) {
						docLinkOidList.add(st.nextToken());
					}
				}
			}

			delObjLinkOidListHash = new Hashtable<String, ArrayList<String>>();
			delObjLinkOidListHash.put("ecrLinkOidList", ecrLinkOidList);
			delObjLinkOidListHash.put("dqmLinkOidList", dqmLinkOidList);
			delObjLinkOidListHash.put("partLinkOidList", partLinkOidList);
			delObjLinkOidListHash.put("bomLinkOidList", bomLinkOidList);
			delObjLinkOidListHash.put("epmDocLinkOidList", epmDocLinkOidList);
			delObjLinkOidListHash.put("docLinkOidList", docLinkOidList);

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return delObjLinkOidListHash;
	}

	public Hashtable<String, ArrayList<Hashtable<String, String>>> getAddRefObjList(KETParamMapUtil paramMap) {

		// 도면,BOM 활동계획
		String[] actTypeList = paramMap.getStringArray("relEcaActivityType"); // 활동계획구분(1:도면, 2:BOM)
		String[] refObjLinkOidList = paramMap.getStringArray("relEcaLinkOid"); // 관련Link Oid
		String[] refObjOidList = paramMap.getStringArray("relObjOid"); // 관련 BOM/EPMDoc Oid
		String[] refPreVerList = paramMap.getStringArray("relObjPreRev"); // 관련 도면/BOM 변경전 버전

		// 안보낸다.
		/*
		 * String[] refObjAfterVersionList = paramMap.getStringArray("relObjAfterRev"); // 관련 도면/BOM/문서 변경후 버전
		 */

		String[] workerOidList = paramMap.getStringArray("relEcaWorkerOid"); // 활동 도면/BOM담당자 oid
		String[] massChgYnList = paramMap.getStringArray("masschange_yn"); // BOM 일괄변경 여부
		String[] parentPartList = paramMap.getStringArray("parentPart"); // BOM 일괄변경 모부품
		String[] planDateList = paramMap.getStringArray("relEcaPlanDate"); // 관련 활동 변경예정일
		String[] commentList = paramMap.getStringArray("relEcaActivityComment"); // 관련활동변경내용
		String[] dieNoList = paramMap.getStringArray("dieNo"); // 금형 변경일정 Die No
		String[] scheduleIdList = paramMap.getStringArray("scheduleId"); // 금형변경일정 Id
		String[] epmDocTypeList = paramMap.getStringArray("relEcaEpmDocType"); // 도면 Type

		String[] prodEcaOidList = paramMap.getStringArray("prodEcaOid"); // KETProdChangeActivity OID

		// Die No
		String[] relDieNoEcaList = paramMap.getStringArray("relDieNo_eca");
		String[] expectCostEcaList = paramMap.getStringArray("expectCost_eca");
		String[] secureBudgetYnEcaList = paramMap.getStringArray("secureBudgetYn_eca");
		String[] budgetEcaList = paramMap.getStringArray("budget_eca");

		return this.getAddRefObjList2(actTypeList, refObjLinkOidList, refObjOidList, refPreVerList, workerOidList, massChgYnList, parentPartList, planDateList, commentList,
				dieNoList, scheduleIdList, epmDocTypeList, prodEcaOidList, relDieNoEcaList, expectCostEcaList, secureBudgetYnEcaList);

	}

	public Hashtable<String, ArrayList<Hashtable<String, String>>> getAddRefObjList2(String[] actTypeList, String[] refObjLinkOidList, String[] refObjOidList,
			String[] refPreVerList, String[] workerOidList, String[] massChgYnList, String[] parentPartList, String[] planDateList, String[] commentList, String[] dieNoList,
			String[] scheduleIdList, String[] epmDocTypeList, String[] prodEcaOidList, String[] relDieNoEcaList, String[] expectCostEcaList, String[] secureBudgetYnEcaList) {
		Hashtable<String, ArrayList<Hashtable<String, String>>> objHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();
		ArrayList<Hashtable<String, String>> epmDocList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> epmDoc = null;
		Hashtable<String, String> bom = null;

		if (actTypeList != null && actTypeList.length > 0) {
			for (int oCnt = 0; oCnt < actTypeList.length; oCnt++) {
				// 도면
				if (actTypeList[oCnt].equals("1")) {
					epmDoc = new Hashtable<String, String>();
					if (refObjOidList != null && refObjOidList.length > 0)
						epmDoc.put("epmDocOid", refObjOidList[oCnt]);
					if (refPreVerList != null && refPreVerList.length > 0)
						epmDoc.put("preVersion", refPreVerList[oCnt]);
					if (workerOidList != null && workerOidList.length > 0)
						epmDoc.put("workerOid", workerOidList[oCnt]);
					if (planDateList != null && planDateList.length > 0)
						epmDoc.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					if (commentList != null && commentList.length > 0)
						epmDoc.put("comment", StringUtil.checkNull(commentList[oCnt]));
					if (dieNoList != null && dieNoList.length > 0)
						epmDoc.put("dieNo", StringUtil.checkNull(dieNoList[oCnt]));
					if (scheduleIdList != null && scheduleIdList.length > 0)
						epmDoc.put("scheduleId", StringUtil.checkNull(scheduleIdList[oCnt]));
					if (epmDocTypeList != null && epmDocTypeList.length > 0)
						epmDoc.put("epmDocType", StringUtil.checkNull(epmDocTypeList[oCnt]));

					if (prodEcaOidList != null && prodEcaOidList.length > 0)
						epmDoc.put("prodEcaOid", StringUtil.checkNull(prodEcaOidList[oCnt]));

					epmDocList.add(epmDoc);
				}
				// BOM
				else if (actTypeList[oCnt].equals("2")) {
					bom = new Hashtable<String, String>();
					if (refObjOidList != null && refObjOidList.length > 0)
						bom.put("partOid", refObjOidList[oCnt]);
					if (refPreVerList != null && refPreVerList.length > 0)
						bom.put("preVersion", refPreVerList[oCnt]);
					if (workerOidList != null && workerOidList.length > 0)
						bom.put("workerOid", workerOidList[oCnt]);
					/*
					 * if (massChgYnList != null && massChgYnList.length > 0) bom.put("massChgYn", massChgYnList[oCnt]); if (parentPartList != null && parentPartList.length > 0)
					 * bom.put("parentPartList", StringUtil.checkNull(parentPartList[oCnt]));
					 */
					if (planDateList != null && planDateList.length > 0)
						bom.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					if (commentList != null && commentList.length > 0)
						bom.put("comment", StringUtil.checkNull(commentList[oCnt]));
					if (refObjOidList != null && refObjOidList.length > 0)
						bom.put("beforPartOid", refObjOidList[oCnt]);
					if (refObjLinkOidList != null && refObjLinkOidList.length > 0)
						bom.put("linkOid", StringUtil.checkNull(refObjLinkOidList[oCnt]));

					if (prodEcaOidList != null && prodEcaOidList.length > 0)
						bom.put("prodEcaOid", StringUtil.checkNull(prodEcaOidList[oCnt]));

					if (relDieNoEcaList != null && relDieNoEcaList.length > 0)
						bom.put("relDieNoEca", StringUtil.checkNull(relDieNoEcaList[oCnt]));
					if (expectCostEcaList != null && expectCostEcaList.length > 0)
						bom.put("expectCostEca", expectCostEcaList[oCnt]);
					if (secureBudgetYnEcaList != null && secureBudgetYnEcaList.length > 0)
						bom.put("secureBudgetYnEca", StringUtil.checkNull(secureBudgetYnEcaList[oCnt]));

					bomList.add(bom);
				}
			}

			objHash.put("refEpmDocList", epmDocList);
			objHash.put("refBomList", bomList);

			Kogger.debug(getClass(), "refEpmDocList is \n" + epmDocList.toString());
			Kogger.debug(getClass(), "refBomList is \n" + bomList.toString());

		}

		return objHash;
	}

	/**
	 * 
	 * @param actTypeList
	 * @param refObjLinkOidList
	 * @param refObjOidList
	 * @param refPreVerList
	 * @param refAfterVerList
	 * @param workerOidList
	 * @param massChgYnList
	 * @param parentPartList
	 * @param planDateList
	 * @param commentList
	 * @param dieNoList
	 * @param scheduleIdList
	 * @param epmDocTypeList
	 * @return
	 * @메소드명 : getAddRefObjList
	 * @작성자 : kimtaehyun
	 * @작성일 : 2014. 9. 7.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 * @deprecated
	 * 
	 */
	public Hashtable<String, ArrayList<Hashtable<String, String>>> getAddRefObjList(String[] actTypeList, String[] refObjLinkOidList, String[] refObjOidList,
			String[] refPreVerList, String[] refAfterVerList, String[] workerOidList, String[] massChgYnList, String[] parentPartList, String[] planDateList, String[] commentList,
			String[] dieNoList, String[] scheduleIdList, String[] epmDocTypeList) {
		Hashtable<String, ArrayList<Hashtable<String, String>>> objHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();
		ArrayList<Hashtable<String, String>> epmDocList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> bomList = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> docList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> epmDoc = null;
		Hashtable<String, String> bom = null;
		Hashtable<String, String> doc = null;

		if (actTypeList != null && actTypeList.length > 0) {
			for (int oCnt = 0; oCnt < actTypeList.length; oCnt++) {
				// 도면
				if (actTypeList[oCnt].equals("1")) {
					epmDoc = new Hashtable<String, String>();
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 1 ");
					if (refObjOidList != null && refObjOidList.length > 0)
						epmDoc.put("epmDocOid", refObjOidList[oCnt]);
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 2 ");
					if (refPreVerList != null && refPreVerList.length > 0)
						epmDoc.put("preVersion", refPreVerList[oCnt]);
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 3 ");
					if (workerOidList != null && workerOidList.length > 0)
						epmDoc.put("workerOid", workerOidList[oCnt]);
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 4 ");
					if (planDateList != null && planDateList.length > 0)
						epmDoc.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 5 ");
					if (commentList != null && commentList.length > 0)
						epmDoc.put("comment", StringUtil.checkNull(commentList[oCnt]));
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 6 ");
					if (dieNoList != null && dieNoList.length > 0)
						epmDoc.put("dieNo", StringUtil.checkNull(dieNoList[oCnt]));
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 7 ");
					if (scheduleIdList != null && scheduleIdList.length > 0)
						epmDoc.put("scheduleId", StringUtil.checkNull(scheduleIdList[oCnt]));
					Kogger.debug(getClass(), "EPM Doc ADD -------------------> 8 ");
					if (epmDocTypeList != null && epmDocTypeList.length > 0)
						epmDoc.put("epmDocType", StringUtil.checkNull(epmDocTypeList[oCnt]));

					epmDocList.add(epmDoc);
				}
				// BOM
				else if (actTypeList[oCnt].equals("2")) {
					bom = new Hashtable<String, String>();
					Kogger.debug(getClass(), "BOM ADD -------------------> 1 ");
					if (refObjOidList != null && refObjOidList.length > 0)
						bom.put("partOid", refObjOidList[oCnt]);
					Kogger.debug(getClass(), "BOM ADD -------------------> 2 ");
					if (refPreVerList != null && refPreVerList.length > 0)
						bom.put("preVersion", refPreVerList[oCnt]);
					Kogger.debug(getClass(), "BOM ADD -------------------> 3 ");
					if (workerOidList != null && workerOidList.length > 0)
						bom.put("workerOid", workerOidList[oCnt]);
					Kogger.debug(getClass(), "BOM ADD -------------------> 4 ");
					if (massChgYnList != null && massChgYnList.length > 0)
						bom.put("massChgYn", massChgYnList[oCnt]);
					Kogger.debug(getClass(), "BOM ADD -------------------> 5 ");
					if (parentPartList != null && parentPartList.length > 0)
						bom.put("parentPartList", StringUtil.checkNull(parentPartList[oCnt]));
					Kogger.debug(getClass(), "BOM ADD -------------------> 6 ");
					if (planDateList != null && planDateList.length > 0)
						bom.put("planDate", StringUtil.checkNull(planDateList[oCnt]));
					Kogger.debug(getClass(), "BOM ADD -------------------> 7 ");
					if (commentList != null && commentList.length > 0)
						bom.put("comment", StringUtil.checkNull(commentList[oCnt]));
					Kogger.debug(getClass(), "BOM ADD -------------------> 8 ");
					if (refObjOidList != null && refObjOidList.length > 0)
						bom.put("beforPartOid", refObjOidList[oCnt]);
					Kogger.debug(getClass(), "BOM ADD -------------------> 9 ");
					if (refObjLinkOidList != null && refObjLinkOidList.length > 0)
						bom.put("linkOid", StringUtil.checkNull(refObjLinkOidList[oCnt]));

					bomList.add(bom);
				} else if (actTypeList[oCnt].equals("3")) {
					doc = new Hashtable<String, String>();
					if (refObjOidList != null && refObjOidList.length > 0)
						doc.put("docOid", refObjOidList[oCnt]);
					if (refPreVerList != null && refPreVerList.length > 0)
						doc.put("preVersion", refPreVerList[oCnt]);
					if (refAfterVerList != null && refAfterVerList.length > 0)
						doc.put("afterVersion", StringUtil.checkNull(refAfterVerList[oCnt]));
					if (workerOidList != null && workerOidList.length > 0)
						doc.put("workerOid", workerOidList[oCnt]);
					if (epmDocTypeList != null && epmDocTypeList.length > 0)
						doc.put("docType", StringUtil.checkNull(epmDocTypeList[oCnt]));

					docList.add(doc);
				}
			}

			objHash.put("refEpmDocList", epmDocList);
			objHash.put("refBomList", bomList);
			objHash.put("refDocList", docList);
		}

		return objHash;
	}

	public String reviseEpmDoc(String epmDocOid) throws Exception {
		String afterRev = "";

		try {
			EPMDocument epm = (EPMDocument) WCUtil.getObject(epmDocOid);

			if (epm == null) {
				return afterRev;
			}

			// 관련 모델/2D
			ArrayList revisedObjs = e3ps.edm.beans.EDMHelper.getAssociateDocsBy(epm, true);

			ReferenceFactory rf = new ReferenceFactory();

			ArrayList arr = new ArrayList();
			arr.add(epm);
			for (int i = 0; i < revisedObjs.size(); i++) {
				if (!(revisedObjs.get(i) instanceof wt.epm.EPMDocument)) {
					continue;
				}
				arr.add(revisedObjs.get(i));
			}

			String roids[] = new String[arr.size()];
			for (int i = 0; i < arr.size(); i++) {
				String oid = rf.getReferenceString((Persistable) arr.get(i));
				roids[i] = oid;
			}

			// 개정
			EDMServiceHelper.batchRevise(roids);

			RevisionControlled latest = null;
			latest = VersionUtil.getLatestObject((Master) epm.getMaster());
			afterRev = VersionUtil.getMajorVersion(latest);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw e;
		}
		return afterRev;
	}

	public String reviseBom(WTPart part) throws Exception {
		String afterRev = "";
		WTPart latest = null;
		Versioned versioned = null;

		try {
			if (part != null) {
				ECMProperties ecmProperties = ECMProperties.getInstance();
				ReferenceFactory rf = new ReferenceFactory();

				versioned = (Versioned) rf.getReference(part).getObject();

				if (!VersionHelper.isLatestRevision(versioned)) {
					throw new WTException("최신버전이 아닙니다.");
				}

				String location = null;
				String lifecycle = null;

				if (versioned instanceof WTPart) {
					location = ecmProperties.getString("part.folder");
					lifecycle = ecmProperties.getString("ecm.lifecyle.part");
				}

				VersionUtil.doRevise(versioned, null, lifecycle, location, null, null, null);

				QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered) part.getMaster());

				if (qr.hasMoreElements()) {
					latest = (WTPart) qr.nextElement();
				}

				afterRev = latest.getVersionIdentifier().getSeries().getValue();
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw e;
		}

		return afterRev;
	}

	public boolean deleteBomSubComponent(String ecoId, String partNo, Connection conn) throws Exception {
		boolean isSuccess = false;
		boolean isDeleteCoWorker = false;
		boolean isDeleteComp = false;
		boolean isDeleteTmpComp = false;
		try {
			conn.setAutoCommit(false);
			EcmComDao comDao = new EcmComDao(conn);

			isDeleteCoWorker = comDao.deleteBomCoWorker(ecoId, partNo);
			isDeleteComp = comDao.deleteBomComponent(ecoId, partNo);
			isDeleteTmpComp = comDao.deleteBomTempComponent(ecoId, partNo);

			if (isDeleteCoWorker && isDeleteComp && isDeleteTmpComp) {
				isSuccess = true;
			}

		} catch (Exception e) {
			throw e;
		}

		return isSuccess;
	}

	/**
	 * 함수명 : getNotRelatedActivityList 설명 : 링크객체(EPMDoc, BOMHeader, Document)가 없는 ECA 가져오는 함수
	 * 
	 * @param prodEco
	 * @return
	 * @throws Exception
	 *             작성자 : 오승연 작성일자 : 2011. 1. 26.
	 * 
	 * @deprecated
	 * 
	 */
	public ArrayList<KETProdChangeActivity> getNotRelatedActivityList(KETProdChangeOrder prodEco) throws Exception {
		QueryResult qr = null;
		QueryResult subQr = null;
		ArrayList<KETProdChangeActivity> activityList = new ArrayList<KETProdChangeActivity>();
		KETProdChangeActivity prodEca = null;
		try {
			qr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class);

			while (qr.hasMoreElements()) {
				prodEca = (KETProdChangeActivity) qr.nextElement();

				subQr = PersistenceHelper.manager.navigate(prodEca, "epmDoc", KETProdECAEpmDocLink.class);

				if (!subQr.hasMoreElements()) {
					subQr = PersistenceHelper.manager.navigate(prodEca, "bom", KETProdECABomLink.class);

					if (!subQr.hasMoreElements()) {
						subQr = PersistenceHelper.manager.navigate(prodEca, "doc", KETProdECADocLink.class);

						if (!subQr.hasMoreElements()) {
							activityList.add(prodEca);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return activityList;
	}

	public WTPart getLastestPart(String partNumber, Connection conn) throws Exception {
		WTPart part = null;
		EcmComDao comDao = null;

		try {
			comDao = new EcmComDao(conn);
			String partOid = comDao.getLastestPart(partNumber);

			part = (WTPart) KETObjectUtil.getObject(partOid);
		} catch (Exception e) {
			throw e;
		}

		return part;
	}

	public KETMoldChangeOrderVO getBasicMoldEcoInfo(String prodEcoOid) throws Exception {
		KETMoldChangeOrderVO moldEcoVo = new KETMoldChangeOrderVO();

		KETMoldChangeOrder moldEco = null;
		moldEco = KETEcmHelper.service.createMoldEcoByProdEco(prodEcoOid);

		KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);

		ArrayList<KETMoldEcoEcrLinkVO> prodEcoLinkVoList = new ArrayList<KETMoldEcoEcrLinkVO>();
		KETMoldEcoEcrLinkVO ecoLinkVo = new KETMoldEcoEcrLinkVO();
		ecoLinkVo.setRelEcrLinkOid("");
		ecoLinkVo.setRelEcrId(prodEco.getEcoId());
		ecoLinkVo.setRelEcrOid(KETObjectUtil.getOidString(prodEco));
		ecoLinkVo.setRelEcrName(prodEco.getEcoName());
		ecoLinkVo.setCreateDeptName(prodEco.getDeptName());
		ecoLinkVo.setCreatorName(prodEco.getCreatorFullName());
		ecoLinkVo.setApproveDate(EcmUtil.getLastApproveDate(prodEco));
		if (EcmSearchHelper.manager.isSecureBudget(prodEcoOid)) {
			ecoLinkVo.setSecureBudgetYn("확보");
		} else {
			ecoLinkVo.setSecureBudgetYn("미확보");
		}

		prodEcoLinkVoList.add(ecoLinkVo);

		ArrayList<KETMoldECOPartLinkVO> dieNoList = getRelDieNoFromECO(getDieNoFromProdECO(prodEco));

		moldEcoVo.setKetMoldChangeOrder(moldEco);
		moldEcoVo.setKetMoldEcoProdEcoLinkVOList(prodEcoLinkVoList);
		moldEcoVo.setKetMoldECOPartLinkVOList(dieNoList);
		moldEcoVo.setOid("");
		moldEcoVo.setTotalCount(1);
		moldEcoVo.setProgressState("PLANNING");
		return moldEcoVo;
	}

	public ArrayList<KETProdECOPartLink> getDieNoFromProdECO(KETProdChangeOrder prodEco) throws WTException {
		ArrayList<KETProdECOPartLink> partLinkList = new ArrayList<KETProdECOPartLink>();
		KETProdECOPartLink partLink = null;
		QueryResult qr = PersistenceHelper.manager.navigate(prodEco, "part", KETProdECOPartLink.class, false);
		while (qr.hasMoreElements()) {
			partLink = (KETProdECOPartLink) qr.nextElement();
			partLinkList.add(partLink);
		}

		return partLinkList;
	}

	public ArrayList<KETMoldECOPartLinkVO> getRelDieNoFromECO(ArrayList<KETProdECOPartLink> partLinkList) throws Exception {
		ArrayList<KETMoldECOPartLinkVO> moldEcoPartLinkList = new ArrayList<KETMoldECOPartLinkVO>();
		KETMoldECOPartLinkVO moldPartLinkVo = null;
		KETProdECOPartLink partLink = null;
		WTPart moldPart = null;
		Connection conn = null;
		try {
			conn = PlmDBUtil.getConnection();
			EcmComDao comDao = new EcmComDao(conn);
			String partOid = "";

			if (partLinkList != null) {
				for (int partCnt = 0; partCnt < partLinkList.size(); partCnt++) {
					partLink = partLinkList.get(partCnt);
					if (partLink.getRelatedDieNo() != null && partLink.getRelatedDieNo().length() > 0) {
						partOid = comDao.getLastestPart(partLink.getRelatedDieNo());
						moldPart = (WTPart) KETObjectUtil.getObject(partOid);

						moldPartLinkVo = new KETMoldECOPartLinkVO();
						moldPartLinkVo.setRelPartOid(KETObjectUtil.getOidString(moldPart));
						moldPartLinkVo.setRelPartNumber(moldPart.getNumber());
						moldPartLinkVo.setRelPartName(moldPart.getName());
						moldPartLinkVo.setRelPartRev(VersionUtil.getMajorVersion(moldPart));
						moldPartLinkVo.setChangeReqComment("");
						moldPartLinkVo.setRelPartLinkOid("");
						if (partLink.getSecureBudgetYn().equals("Y")) {
							moldPartLinkVo.setSecureBudgetYn("Y");
							moldPartLinkVo.setSecureBudgetYnName("확보");
						} else {
							moldPartLinkVo.setSecureBudgetYn("N");
							moldPartLinkVo.setSecureBudgetYnName("미확보");
						}

						moldPartLinkVo.setExpectCost(KETStringUtil.getFormattedNumber("0", "###,##0"));

						moldEcoPartLinkList.add(moldPartLinkVo);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return moldEcoPartLinkList;
	}

	public ArrayList<Hashtable<String, String>> getCodeList(String type) throws Exception {
		Connection conn = null;
		EcmComDao cDao = null;

		ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();

		try {
			conn = PlmDBUtil.getConnection();

			cDao = new EcmComDao(conn);
			codeList = cDao.getCodeList(type, "ko");

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return codeList;
	}

	public String cancelRevEpm(String cancelRevEpmDocOid) throws Exception {
		String afterRev = "";
		try {
			EPMDocument epm = (EPMDocument) WCUtil.getObject(cancelRevEpmDocOid);

			if (epm == null) {
				return afterRev;
			}
			// 관련 모델/2D
			ArrayList revisedObjs = e3ps.edm.beans.EDMHelper.getAssociateDocsBy(epm, true);

			ReferenceFactory rf = new ReferenceFactory();
			ArrayList arr = new ArrayList();
			arr.add(epm);

			for (int i = 0; i < revisedObjs.size(); i++) {
				if (revisedObjs.get(i) instanceof EPMDocument) {
					arr.add(revisedObjs.get(i));
				}
			}

			String roids[] = new String[arr.size()];

			for (int i = 0; i < arr.size(); i++) {
				String oid = rf.getReferenceString((Persistable) arr.get(i));
				roids[i] = oid;

				Kogger.debug(getClass(), "=========> ProdEcoBeans.java :: cancelRevEpm() :: Deleted EPM oid " + i + " :: " + oid);
			}

			// 개정취소
			EDMServiceHelper.deleteObjects(roids);
			afterRev = "";
		} catch (Exception e1) {
			Kogger.error(getClass(), e1);
			throw new Exception(e1);
		}

		return afterRev;
	}// end-of-cancelRevEpm

	public EPMDocument getLastestEPMDoc(EPMDocument epmDoc) {
		EPMDocument lastestEPMDoc = null;

		try {
			QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered) epmDoc.getMaster());

			if (qr.hasMoreElements()) {
				lastestEPMDoc = (EPMDocument) qr.nextElement();
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return lastestEPMDoc;
	}

	public KETMoldChangePlan getMoldPlan(String scheduleId) throws Exception {
		KETMoldChangePlan plan = null;

		try {
			QuerySpec spec = new QuerySpec(KETMoldChangePlan.class);
			SearchUtil.appendEQUAL(spec, KETMoldChangePlan.class, KETMoldChangePlan.SCHEDULE_ID, scheduleId, 0);

			QueryResult result = PersistenceHelper.manager.find(spec);

			if (result != null && result.hasMoreElements()) {
				plan = (KETMoldChangePlan) result.nextElement();
			}
		} catch (WTException e) {
			throw new Exception(e);
		}

		return plan;
	}

	public KETProdECOPlanLink getProdEcoPlanLink(KETProdChangeOrder prodEco) throws Exception {
		KETProdECOPlanLink planLink = null;
		QueryResult qr = null;

		try {
			qr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class, false);

			while (qr.hasMoreElements()) {
				planLink = (KETProdECOPlanLink) qr.nextElement();
			}
		} catch (WTException e) {
			throw new Exception(e);
		}

		return planLink;
	}

	public void modifyMoldPlanLink(KETProdChangeOrder prodEco) throws Exception {
		KETMoldChangePlan plan = null;
		QueryResult qr = null;

		try {
			qr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class);

			while (qr.hasMoreElements()) {
				plan = (KETMoldChangePlan) qr.nextElement();

				plan.setProdEcoOwner((WTUser) prodEco.getCreator().getPrincipal());

				PersistenceHelper.manager.modify(plan);
			}
		} catch (WTException e) {
			throw new Exception(e);
		}

	}

	public void updateMoldPlan(String prodEcoOid) {
		QueryResult qr = null;
		KETProdChangeOrder prodEco = null;
		KETMoldChangePlan plan = null;

		try {
			prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);

			qr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class);

			while (qr.hasMoreElements()) {
				plan = (KETMoldChangePlan) qr.nextElement();
				plan.setProdDwgActualDate(DateUtil.getCurrentTimestamp());
				PersistenceHelper.manager.modify(plan);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	public WTChangeOrder2 getEcoByNo(String ecoId) {
		WTChangeOrder2 eco = null;

		try {
			QuerySpec spec = new QuerySpec(KETProdChangeOrder.class);
			SearchUtil.appendLIKE(spec, KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID, ecoId, 0);
			QueryResult result = PersistenceHelper.manager.find(spec);
			if (result != null && result.hasMoreElements()) {
				eco = (WTChangeOrder2) result.nextElement();
			} else {
				spec = new QuerySpec(KETMoldChangeOrder.class);
				SearchUtil.appendLIKE(spec, KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID, ecoId, 0);
				result = PersistenceHelper.manager.find(spec);
				if (result != null && result.hasMoreElements()) {
					eco = (WTChangeOrder2) result.nextElement();
				}
			}

		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}

		return eco;
	}

	public ArrayList<WTChangeOrder2> getEcoListByProjectOid(String projectOid) {
		ArrayList<WTChangeOrder2> ecoList = null;

		if (projectOid == null)
			return ecoList;
		try {
			QuerySpec spec = new QuerySpec();
			int idxEco = spec.appendClassList(KETProdChangeOrder.class, true);

			// 로그인 사용자가 KETS 일 경우
			if (CommonUtil.isKETSUser()) {
				int idxUser = spec.appendClassList(WTUser.class, false);

				ClassAttribute ecoUserRef = new ClassAttribute(KETProdChangeOrder.class, "creator.key.id");
				ClassAttribute ecoUser = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
				SearchCondition sc = new SearchCondition(ecoUserRef, SearchCondition.EQUAL, ecoUser);
				sc.setFromIndicies(new int[] { idxEco, idxUser }, 0);
				sc.setOuterJoin(SearchCondition.NO_OUTER_JOIN);
				spec.appendWhere(sc, new int[] { idxEco, idxUser });

				SearchUtil.appendEQUAL(spec, KETProdChangeOrder.class, KETProdChangeOrder.PROJECT_OID, projectOid, 0);
				CommonUtil.ketsUserListWhereStrQs(spec, WTUser.class, idxUser, "thePersistInfo.theObjectIdentifier.id", true);
			} else {
				SearchUtil.appendEQUAL(spec, KETProdChangeOrder.class, KETProdChangeOrder.PROJECT_OID, projectOid, 0);

			}

			QueryResult result = PersistenceHelper.manager.find(spec);
			while (result != null && result.hasMoreElements()) {
				WTChangeOrder2 eco = (WTChangeOrder2) result.nextElement();

				if (ecoList == null)
					ecoList = new ArrayList<WTChangeOrder2>();
				ecoList.add(eco);
			}

		} catch (WTException wte) {
			Kogger.error(getClass(), wte);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return ecoList;
	}

	/**
	 * 함수명 : 해당 ECO, Part, Revision에 로그인 유저가 담당자로 되어있는지 없는지
	 */
	public boolean isWorkerEcoPart(String ecoId, String partNumber, String rev) {
		boolean isWorkerEcoPart = false;

		try {

			WTUser loginUser = KETObjectUtil.getLoginUser();

			WTChangeOrder2 wtChangeOrder2 = this.getEcoByNo(ecoId);
			if (wtChangeOrder2 instanceof KETProdChangeOrder) {

				QueryResult qr = PersistenceHelper.manager.navigate(wtChangeOrder2, "prodECA", KETProdChangeActivityLink.class, false);
				while (qr.hasMoreElements()) {
					KETProdChangeActivityLink ecaLink = (KETProdChangeActivityLink) qr.nextElement();
					KETProdChangeActivity eca = ecaLink.getProdECA();
					String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
					WTUser worker = (!workerId.equals("")) ? (WTUser) CommonUtil.getObject(workerId) : null;
					if (loginUser.equals(worker)) {

						QueryResult qr1 = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
						while (qr1.hasMoreElements()) {
							KETProdECABomLink bomLink = (KETProdECABomLink) qr1.nextElement();

							String preVersion = StringUtils.stripToEmpty(bomLink.getPreVersion());
							String afterVersion = StringUtils.stripToEmpty(bomLink.getAfterVersion());

							String ecoItemCode = "";
							KETBomEcoHeader bomHeader = bomLink.getBom();
							if (bomHeader instanceof KETBomHeader) {
								KETBomHeader bHeader = (KETBomHeader) bomHeader;
								ecoItemCode = StringUtils.stripToEmpty(bHeader.getNewBOMCode());
							} else {
								ecoItemCode = StringUtils.stripToEmpty(bomHeader.getEcoItemCode());
							}

							if (ecoItemCode.equals(partNumber)) {
								if (preVersion.equals(rev) || afterVersion.equals(rev)) {
									isWorkerEcoPart = true;
									break;
								}
							}

						}

					}

					if (isWorkerEcoPart)
						break;

				}

			} else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {

				QueryResult qr = PersistenceHelper.manager.navigate(wtChangeOrder2, "moldECA", KETMoldChangeActivityLink.class, false);
				while (qr.hasMoreElements()) {
					KETMoldChangeActivityLink ecaLink = (KETMoldChangeActivityLink) qr.nextElement();
					KETMoldChangeActivity eca = ecaLink.getMoldECA();
					String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
					WTUser worker = (!workerId.equals("")) ? (WTUser) CommonUtil.getObject(workerId) : null;
					if (loginUser.equals(worker)) {

						QueryResult qr1 = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
						while (qr1.hasMoreElements()) {
							KETMoldECABomLink bomLink = (KETMoldECABomLink) qr1.nextElement();

							String preVersion = StringUtils.stripToEmpty(bomLink.getPreVersion());
							String afterVersion = StringUtils.stripToEmpty(bomLink.getAfterVersion());

							String ecoItemCode = "";
							KETBomEcoHeader bomHeader = bomLink.getBom();
							if (bomHeader instanceof KETBomHeader) {
								KETBomHeader bHeader = (KETBomHeader) bomHeader;
								ecoItemCode = StringUtils.stripToEmpty(bHeader.getNewBOMCode());
							} else {
								ecoItemCode = StringUtils.stripToEmpty(bomHeader.getEcoItemCode());
							}

							if (ecoItemCode.equals(partNumber)) {
								if (preVersion.equals(rev) || afterVersion.equals(rev)) {
									isWorkerEcoPart = true;
									break;
								}
							}

						}

					}

					if (isWorkerEcoPart)
						break;

				}

			}

		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}

		return isWorkerEcoPart;
	}

	public boolean isSucessSapInterface(String ecoId) throws Exception {
		boolean isSucessSapInterface = false;

		Connection conn = null;
		try {
			conn = PlmDBUtil.getConnection();

			ProdEcoDao ecoDao = new ProdEcoDao(conn);

			// SAP I/F의 성공여부
			isSucessSapInterface = ecoDao.isSucessSapInterface(ecoId);

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return isSucessSapInterface;
	}
}
