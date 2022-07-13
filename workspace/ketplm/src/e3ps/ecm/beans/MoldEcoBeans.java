package e3ps.ecm.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.ChangeNoticeComplexity;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
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
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.WTConnection;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.common.util.WCUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.dao.EcmComDao;
import e3ps.ecm.dao.MoldChangeRequestDao;
import e3ps.ecm.entity.KETChangeNotice;
import e3ps.ecm.entity.KETEcoEcnLink;
import e3ps.ecm.entity.KETEcrEcnLink;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECADocLink;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETMoldECALinkVO;
import e3ps.ecm.entity.KETMoldECOPartLink;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldECOPlanLink;
import e3ps.ecm.entity.KETMoldEcoEcrLinkVO;
import e3ps.ecm.entity.KETMoldProdEcoLink;
import e3ps.ecm.entity.KETMoldStdPartLine;
import e3ps.ecm.entity.KETMoldStdPartLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.edm.beans.EDMServiceHelper;
import e3ps.project.ProjectOutput;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.service.base.EpmBaseHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;

public class MoldEcoBeans extends StandardManager implements Serializable {

	private static final long serialVersionUID = 6744551693766378190L;

	/**
	 * 
	 * 함수명 : createMoldEcoInfo 설명 : 금형 ECO를 저장한다. 결재상태 및 저장 폴더를 세팅한다. 첨부파일 VO List를 저장한다. 연계 ECR VO List를 저장한다. 연계 제품 ECO VO List를 저장한다. 관련부품 VO List를 저장한다. ECO 담당자 활동계획 VO를 저장한다.
	 * 도면 VO List를 저장한다. BOM VO List를 저장한다. 표준품 VO List를 저장한다. 금형 ECO를 변경 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형ECO VO
	 * @return KETMoldChangeOrderVO : 금형ECO VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 30.
	 */
	public KETMoldChangeOrderVO createMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		try {
			// 결재상태 지정
			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			ketMoldChangeOrder.setContainerReference(containerRef);
			String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.eco");
			LifeCycleHelper.setLifeCycle(ketMoldChangeOrder, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			// 저장 폴더를 지정한다.
			String folderPath = "";
			if ("C".equals(ketMoldChangeOrder.getDevYn())) {
				folderPath = ECMProperties.getInstance().getString("ecm.folder.car");// 자동차사업부
			} else if ("K".equals(ketMoldChangeOrder.getDevYn())) {
				folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");// KETS
			} else {
				folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");// 전자사업부
			}
			folderPath += DateUtil.getThisYear();
			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			FolderHelper.assignLocation((FolderEntry) ketMoldChangeOrder, folder);

			// 금형 ECO를 저장한다.
			ketMoldChangeOrder.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);

			KETMoldChangeOrder ketMoldChangeOrder2 = null;

			// [START] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현
			// ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
			ketMoldChangeOrder.setName(ketMoldChangeOrder.getEcoName());
			// [END] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현

			ketMoldChangeOrder2 = (KETMoldChangeOrder) PersistenceHelper.manager.save(ketMoldChangeOrder);

			ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder2);

			saveFileList(ketMoldChangeOrderVO);// 첨부파일 저장
			saveRelEcrList(ketMoldChangeOrderVO);// 연계ECR 저장
			saveRelProdEcoList(ketMoldChangeOrderVO);// 연계제품ECO 저장
			saveRelPartList(ketMoldChangeOrderVO);// 관련부품 저장

			saveRelDocList(ketMoldChangeOrderVO);// 문서목록 저장
			saveRelEpmList(ketMoldChangeOrderVO);// 도면목록 저장
			saveRelBomList(ketMoldChangeOrderVO);// BOM목록 저장

			// ECN
			// ProdECR Relation
			KETMoldChangeRequest mainEcr = null;
			QueryResult qr = PersistenceHelper.manager.navigate(ketMoldChangeOrder2, "moldECR", KETMoldChangeLink.class, false);
			if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) {
				KETMoldChangeLink ketMoldChangeLink = (KETMoldChangeLink) qr.nextElement();
				mainEcr = ketMoldChangeLink.getMoldECR();

			}

			KETChangeNotice ecn = null;
			// ECR에서 먼저 찾는다.
			if (mainEcr != null) {
				QueryResult qr1 = PersistenceHelper.manager.navigate(mainEcr, "ecn", KETEcrEcnLink.class, false);
				if (qr1.hasMoreElements()) { // while (qr.hasMoreElements()) {
					KETEcrEcnLink ketEcrEcnLink = (KETEcrEcnLink) qr1.nextElement();
					ecn = ketEcrEcnLink.getEcn();

				}
			}

			// Link 처리
			KETEcoEcnLink ketEcoEcnLink = null;
			/*
			 * if (ecn == null) { // ECO 로 찾는다. QueryResult qr = PersistenceHelper.manager.navigate(prodEco, "ecn", KETEcoEcnLink.class, false); if (qr.hasMoreElements()) { //
			 * while (qr.hasMoreElements()) { ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement(); ecn = ketEcoEcnLink.getEcn();
			 * 
			 * } }
			 */
			// ECR이나 ECR에 ECN이 없을 경우
			if (!PersistenceHelper.isPersistent(ecn)) {
				ecn = KETChangeNotice.newKETChangeNotice();

				// ECN Number
				try {
					MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
					String ecnNumber = moldChangeRequestDao.getEcnNumber();
					ecn.setEcnNumber(ecnNumber);

				} catch (Exception e) {
					String ecnNumber = String.valueOf(Math.random());
					ecn.setEcnNumber(ecnNumber);

				}
				// 저장
				WTContainerRef ecnContainerRef = WCUtil.getWTContainerRef();
				String ecnLcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecn");
				LifeCycleHelper.setLifeCycle(ecn, LifeCycleHelper.service.getLifeCycleTemplate(ecnLcName, ecnContainerRef));

				ecn.setContainerReference(ecnContainerRef);

				// Folder Setting
				// ECO것을 그대로 사용한다.
				SubFolder ecnfolder = (SubFolder) FolderHelper.service.getFolder(folderPath, ecnContainerRef);
				FolderHelper.assignLocation((FolderEntry) ecn, ecnfolder);

				ecn = (KETChangeNotice) PersistenceHelper.manager.save(ecn);
				ketEcoEcnLink = KETEcoEcnLink.newKETEcoEcnLink(ketMoldChangeOrder2, ecn);
				ketEcoEcnLink = (KETEcoEcnLink) PersistenceHelper.manager.save(ketEcoEcnLink);

			}
			// ECR에 ECN이 있을 경우
			else {
				// 저장
				ketEcoEcnLink = KETEcoEcnLink.newKETEcoEcnLink(ketMoldChangeOrder2, ecn);
				ketEcoEcnLink = (KETEcoEcnLink) PersistenceHelper.manager.save(ketEcoEcnLink);

			}

			// 프로젝트에서 산출물로 ECO 직접작성
			try {
				String projectOutputOid = StringUtils.stripToEmpty(ketMoldChangeOrderVO.getProjectOutputOid()); // 프로젝트 - 산출물 관리 OID
				ProjectOutput output = (!projectOutputOid.equals("")) ? (ProjectOutput) CommonUtil.getObject(projectOutputOid) : null;
				if (output != null) {
					ProjectTaskCompHelper.service.updateEcoProjectOutputLink(ketMoldChangeOrder2, output, "2");
				}
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		} finally {
		}

		return ketMoldChangeOrderVO;
	}

	/**
	 * 
	 * 함수명 : saveRelDocList 설명 : 금형 ECO 관련 문서 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("rawtypes")
	private void saveRelDocList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		KETMoldECADocLink ketMoldECADocLink = null;

		String ecoStatus = ketMoldChangeOrder.getLifeCycleState().getStringValue();

		try {
			// 관련문서삭제(ECO 상태가 계획수립인 경우에만 삭제 가능하다.)
			if (ecoStatus.indexOf("PLANNING") > -1 || ecoStatus.indexOf("EXCUTEACTIVITY") > -1 || ecoStatus.indexOf("REWORK") > -1) {
				String deleteList = ketMoldChangeOrderVO.getDeleteRelDocList();

				KETMoldChangeActivity delEca = null;

				ArrayList arrDeleteList = KETStringUtil.getToken(deleteList, "*");

				QueryResult qr = null;

				for (int i = 0; i < arrDeleteList.size(); i++) {
					String oid = (String) arrDeleteList.get(i);
					String ketMoldECADocLinkOid = KETStringUtil.getLastToken(oid, "^");

					// 표준품 리스트일 경우 데이터가 아래와 같은 구조이다.
					/*
					 * <%=ketMoldECALinkVO.getRelEcaOid() + "^" + ketMoldECALinkVO.getRelEcaObjectLinkOid()%>
					 */
					if (StringUtil.checkNull(ketMoldECADocLinkOid).length() > 0) {
						ketMoldECADocLink = (KETMoldECADocLink) CommonUtil.getObject(ketMoldECADocLinkOid);
						// 1.2 Object Delete
						if (ketMoldECADocLink != null) {

							// 연관 문서의 삭제 처리
							qr = PersistenceHelper.manager.navigate(ketMoldECADocLink, "part", KETMoldStdPartLink.class);

							while (qr.hasMoreElements()) {
								PersistenceHelper.manager.delete((KETMoldStdPartLine) qr.nextElement());
							}

							// 연관 ECA의 삭제 처리
							delEca = ketMoldECADocLink.getMoldECA();

							// 활동수행일 경우
							// 기존 To-Do 제거
							WorkflowSearchHelper.manager.taskComplete(delEca);

							PersistenceHelper.manager.delete(ketMoldECADocLink);
							delEca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(delEca);
							PersistenceHelper.manager.delete(delEca);

						}
					}
					// ECN일 경우 데이터가 아래와 같은 구조이다.
					/*
					 * <%=ketMoldECALinkVO.getRelEcaOid() + "^"
					 */
					else {
						String relEcaOid = oid.substring(0, oid.lastIndexOf("^"));
						KETMoldChangeActivity ketMoldChangeActivity = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);

						QueryResult rs = PersistenceHelper.manager.navigate(ketMoldChangeActivity, "moldECO", KETMoldChangeActivityLink.class, false);
						while (rs.hasMoreElements()) {
							PersistenceHelper.manager.delete((KETMoldChangeActivityLink) rs.nextElement());
						}

						PersistenceHelper.manager.delete(ketMoldChangeActivity);

					}
				}
			}

			KETMoldChangeActivity ketMoldChangeActivity = null;
			KETMoldECALinkVO ketMoldECALinkVO = null;
			KETProjectDocument doc = null;
			String planDate = "";

			// 관련문서 추가
			ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();// 금형ECO관련문서 List

			if (ketMoldECALinkVOList != null) {

				RENEW: for (int i = 0; i < ketMoldECALinkVOList.size(); i++) {
					ketMoldECALinkVO = (KETMoldECALinkVO) ketMoldECALinkVOList.get(i);

					if (StringUtil.checkNull(ketMoldECALinkVO.getActivityType()).equals("3")) {
						// 금형활동계획담당자 저장
						ketMoldChangeActivity = saveMoldChangeActivity(ketMoldChangeOrder, ketMoldECALinkVO);

						// 금형활동계획관련 문서가 있는 경우 다음 자료 처리
						if (StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectOid()).length() > 0) {
							doc = (KETProjectDocument) CommonUtil.getObject(ketMoldECALinkVO.getRelEcaObjectOid());
							if (StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectLinkOid()).length() == 0) {// 신규
								ketMoldECADocLink = KETMoldECADocLink.newKETMoldECADocLink(doc, ketMoldChangeActivity);
							} else {// 변경
								ketMoldECADocLink = (KETMoldECADocLink) CommonUtil.getObject(ketMoldECALinkVO.getRelEcaObjectLinkOid());
								ketMoldECADocLink.setDoc(doc);
							}

							planDate = ketMoldECALinkVO.getRelEcaObjectPlanDate();
							if (StringUtil.checkNull(planDate).length() > 0) {
								planDate = EcmUtil.getDefaultDateFormat(planDate);
								ketMoldECADocLink.setPlanDate(DateUtil.convertDate2(planDate));
							} else {
								ketMoldECADocLink.setPlanDate(null);
							}

							ketMoldECADocLink.setActivityComment(ketMoldECALinkVO.getRelEcaObjectActivityComment());
							ketMoldECADocLink.setPreVersion(ketMoldECALinkVO.getRelEcaObjectPreRev());

							if (StringUtil.checkNull(ketMoldChangeOrderVO.getIsToDO()).equals("Y")) {
								ketMoldECADocLink.setAfterVersion(ketMoldECALinkVO.getRelEcaObjectAfterRev());// 관련활동문서이후버젼
								// ketMoldECADocLink.setTargetPart(ketMoldECALinkVO.getTargetPart());//표준품대상부품번호
								// ketMoldECADocLink.setChangeType(ketMoldECALinkVO.getChangeType());//표준품변경유형
							}

							PersistenceHelper.manager.save(ketMoldECADocLink);
						}
					}
					// ECN
					else if (StringUtil.checkNull(ketMoldECALinkVO.getActivityType()).equals("4")) {

						// 담당자, 완료요청일, 변경내용이 없으면 삭제한다.
						String workerId = StringUtils.stripToEmpty(ketMoldECALinkVO.getWorkerId());
						String completeRequestDate = StringUtils.stripToEmpty(ketMoldECALinkVO.getCompleteRequestDate());
						String activityTypeDesc = StringUtils.stripToEmpty(ketMoldECALinkVO.getActivityTypeDesc());

						String relEcaOid = ketMoldECALinkVO.getRelEcaOid();

						if (workerId.equals("") && completeRequestDate.equals("") && activityTypeDesc.equals("")) {
							if (relEcaOid == null || relEcaOid.equals("")) {
								continue RENEW;

							} else {

								ketMoldChangeActivity = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
								PersistenceHelper.manager.delete(ketMoldChangeActivity);

							}

						} else {

							if (relEcaOid == null || relEcaOid.equals("")) {
								// 결재상태 및 저장 폴더를 세팅한다.
								WTContainerRef containerRef = WCUtil.getWTContainerRef();
								String folderPath = "";
								if ("C".equals(ketMoldChangeOrder.getDevYn())) {
									folderPath = ECMProperties.getInstance().getString("ecm.folder.car");// 자동차사업부
								} else if ("K".equals(ketMoldChangeOrder.getDevYn())) {
									folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");// KETS
								} else {
									folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");// 전자사업부
								}
								folderPath += DateUtil.getThisYear();
								SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
								ketMoldChangeActivity = KETMoldChangeActivity.newKETMoldChangeActivity();
								ketMoldChangeActivity.setMoldECO(ketMoldChangeOrder);
								ketMoldChangeActivity.setActivityType(ketMoldECALinkVO.getActivityType());
								ketMoldChangeActivity.setActivityStatus("PLANNING");
								// 금형ECO 활동계획 저장 폴더를 지정한다.
								FolderHelper.assignLocation((FolderEntry) ketMoldChangeActivity, folder);
								// 금형ECO 활동계획 결재상태를 지정한다.
								WTContainerRef containerRef1 = WCUtil.getWTContainerRef();
								String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.eca");
								LifeCycleHelper.setLifeCycle(ketMoldChangeActivity, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef1));
								// 금형ECO 활동계획을 추가 저장한다.
								ketMoldChangeActivity = (KETMoldChangeActivity) PersistenceHelper.manager.save(ketMoldChangeActivity);

							} else {
								ketMoldChangeActivity = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
							}

							ketMoldChangeActivity.setCustomName(ketMoldECALinkVO.getCustomName());
							ketMoldChangeActivity.setWorkerId(ketMoldECALinkVO.getWorkerId());
							ketMoldChangeActivity.setCompleteRequestDate(DateUtil.convertStartDate2(ketMoldECALinkVO.getCompleteRequestDate()));
							ketMoldChangeActivity.setActivityTypeDesc(ketMoldECALinkVO.getActivityTypeDesc());
							ketMoldChangeActivity.setActivityBackDesc(ketMoldECALinkVO.getActivityBackDesc());

							PersistenceHelper.manager.save(ketMoldChangeActivity);

						}

					}

				}

			}
		} catch (Exception e) {
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : saveRelEpmList 설명 : 금형 ECO 관련 도면 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void saveRelEpmList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		KETMoldECAEpmDocLink ketMoldECAEpmDocLink = null;
		WTGroup group = null;
		try {
			// 관련문서삭제(ECO 상태가 계획수립인 경우에만 삭제 가능하다.)

			// Set temporary Session - Administrator
			WTPrincipal session = null;
			session = SessionHelper.manager.getPrincipal();
			SessionHelper.manager.setAdministrator();

			String ecoStatus = ketMoldChangeOrder.getLifeCycleState().getStringValue();

			Kogger.debug(getClass(), "ecoStatus === " + ecoStatus);

			if (ecoStatus.indexOf("PLANNING") > -1 || ecoStatus.indexOf("EXCUTEACTIVITY") > -1 || ecoStatus.indexOf("REWORK") > -1) {
				String deleteList = ketMoldChangeOrderVO.getDeleteRelEpmList();

				EPMDocument delEpmDoc = null;
				KETMoldChangeActivity delEca = null;

				ArrayList arrDeleteList = KETStringUtil.getToken(deleteList, "*");
				int size = arrDeleteList.size();
				int pos = 0;
				String type = "";
				String oid = "";
				for (int i = 0; i < size; i++) {
					oid = (String) arrDeleteList.get(i);
					type = "";
					pos = oid.indexOf(":");
					if (pos > -1) {
						type = oid.substring(0, pos);
						oid = oid.substring(pos + 1);
					}

					if (StringUtil.checkNull(type).equals("1")) {
						ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) CommonUtil.getObject(oid);
						// 1.2 Object Delete
						if (ketMoldECAEpmDocLink != null) {

							// 연관 도면의 개정 취소 처리
							delEpmDoc = ketMoldECAEpmDocLink.getEpmDoc();
							if (StringUtil.checkNull(ketMoldECAEpmDocLink.getAfterVersion()).length() > 0) {

								throw new WTException("개정된 도면은 삭제할 수 없습니다.[" + delEpmDoc.getName() + ", " + delEpmDoc.getNumber() + " " + delEpmDoc.getVersionInfo().toString()
										+ "]");

							}

							/*
							 * delEpmDoc = (EPMDocument) VersionUtil.getLatestObject((Master) delEpmDoc.getMaster()); if (delEpmDoc.getLifeCycleState().getDisplay().equals("작업중")
							 * || delEpmDoc.getLifeCycleState().getDisplay().equals("재작업")) { cancelRevEpm(KETObjectUtil.getOidString(delEpmDoc)); }
							 */

							// 연관 ECA의 삭제 처리
							delEca = ketMoldECAEpmDocLink.getMoldECA();

							// 활동수행일 경우
							// 기존 To-Do 제거
							/*
							 * WorkflowSearchHelper.manager.taskComplete(delEca);
							 */

							PersistenceHelper.manager.delete(ketMoldECAEpmDocLink);
							delEca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(delEca);
							PersistenceHelper.manager.delete(delEca);
							WorkflowSearchHelper.manager.deleteWorkItem(delEca);

						}
					}

				}
			}

			SessionHelper.manager.setPrincipal(session.getName());

			KETMoldChangeActivity ketMoldChangeActivity = null;
			KETMoldECALinkVO ketMoldECALinkVO = null;
			EPMDocument epmDoc = null;
			String planDate = "";
			String afterVersion = "";

			// 관련도면 추가
			ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();// 금형ECO관련문서 List

			Kogger.debug(getClass(), "KETMoldECAEpmDocLink === " + ketMoldECALinkVOList.size());

			if (ketMoldECALinkVOList != null) {

				for (int i = 0; i < ketMoldECALinkVOList.size(); i++) {
					ketMoldECALinkVO = (KETMoldECALinkVO) ketMoldECALinkVOList.get(i);

					if (StringUtil.checkNull(ketMoldECALinkVO.getActivityType()).equals("1")) {
						// 금형활동계획담당자 저장
						ketMoldChangeActivity = saveMoldChangeActivity(ketMoldChangeOrder, ketMoldECALinkVO);

						// 금형활동계획관련 문서가 없는 경우 다음 자료 처리
						Kogger.debug(getClass(), "==================" + ketMoldECALinkVO.getRelEcaObjectOid().length());
						if (StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectOid()).length() > 0) {
							epmDoc = (EPMDocument) CommonUtil.getObject(ketMoldECALinkVO.getRelEcaObjectOid());
							if ("".equals(ketMoldECALinkVO.getRelEcaObjectLinkOid())) {// 신규
								ketMoldECAEpmDocLink = KETMoldECAEpmDocLink.newKETMoldECAEpmDocLink(epmDoc, ketMoldChangeActivity);
							} else {// 변경
								ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) CommonUtil.getObject(ketMoldECALinkVO.getRelEcaObjectLinkOid());

								// 도면 개정
								if (ketMoldChangeOrderVO.getIsToDO().equals("Y")) {
									if (StringUtil.checkNull(ketMoldECALinkVO.getEpmDocReviseYn()).equals("Y")) {

										/*
										 * 1) 도면 개정 시점에 => 연계 부품이 먼저 개정되지 않으면 Validation 걸어서 Message 전달 파라미터1 : EPMDocument : 개정 전의 리비전 파라미터2 : ecoOid return : String : null 이거나
										 * empty가 아니면 message 뿌려주세요. 호출 api : PartBaseHelper.service.validRelatedPartRevised(EPMDocument epmDoc, String ecoOid);
										 */
										String ecoOid = CommonUtil.getOIDString(ketMoldChangeOrder);
										String ERROR_MESSAGE = PartBaseHelper.service.validRelatedPartRevised(epmDoc, ecoOid);
										if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
											throw new WTException(ERROR_MESSAGE);
										}
										ERROR_MESSAGE = EpmBaseHelper.service.validCheckoutInfoEpm(epmDoc);
										if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
											throw new WTException(ERROR_MESSAGE);
										}

										// Session 변경
										session = SessionHelper.manager.getPrincipal();
										SessionHelper.manager.setAdministrator();
										group = KETObjectUtil.getGroup("Revise Administrators");
										group.addMember(session);
										SessionHelper.manager.setPrincipal(session.getName());

										afterVersion = reviseEpm(ketMoldECALinkVO.getRelEcaObjectOid());

										// Restore Session
										SessionHelper.manager.setAdministrator();
										group.removeMember(session);
										SessionHelper.manager.setPrincipal(session.getName());

										ketMoldECAEpmDocLink.setAfterVersion(afterVersion);

										if (ketMoldECALinkVO.getRelEcaEpmDocType().equals("2D")) {
											ketMoldECAEpmDocLink.setChangeYn("N");
										} else if (ketMoldECALinkVO.getRelEcaEpmDocType().equals("3D")) {
											ketMoldECAEpmDocLink.setChangeYn("Y");
										}
									} else if (StringUtil.checkNull(ketMoldECALinkVO.getEpmDocCancelRevYn()).equals("Y")) {
										afterVersion = cancelRevEpm(ketMoldECALinkVO.getRelEcaAfterEpmOid());
										ketMoldECAEpmDocLink.setAfterVersion(afterVersion);
										ketMoldECAEpmDocLink.setChangeYn("N");
									} else {
										ketMoldECAEpmDocLink.setChangeYn(ketMoldECALinkVO.getRelEcaEpmChangeYn());
									}
								}

								ketMoldECAEpmDocLink.setEpmDoc(epmDoc);
							}

							ketMoldECAEpmDocLink.setActivityComment(ketMoldECALinkVO.getRelEcaObjectActivityComment());

							planDate = ketMoldECALinkVO.getRelEcaObjectPlanDate();
							if (!"".equals(planDate)) {
								planDate = EcmUtil.getDefaultDateFormat(planDate);
								ketMoldECAEpmDocLink.setPlanDate(DateUtil.convertDate2(planDate));
							} else {
								ketMoldECAEpmDocLink.setPlanDate(null);
							}

							ketMoldECAEpmDocLink.setPreVersion(ketMoldECALinkVO.getRelEcaObjectPreRev());
							// ketMoldECAEpmDocLink.setDieNo(ketMoldECALinkVO.getDieNo());

							// if( epmDoc.getNumber().indexOf("-") == 8 )
							// {
							// ketMoldECAEpmDocLink.setDieNo( epmDoc.getNumber().substring( 0, epmDoc.getNumber().indexOf("-") ) );
							// }
							// else
							//
							Kogger.debug(getClass(),
									"PersistenceHelper.getObjectIdentifier(ketMoldChangeOrder).getStringValue()="
											+ PersistenceHelper.getObjectIdentifier(ketMoldChangeOrder).getStringValue());

							// Kogger.debug(getClass(), "ketMoldChangeOrderVO.getOid()==" + CommonUtil.getOIDString(ketMoldChangeOrderVO));
							// ketMoldECAEpmDocLink.setDieNo( ketMoldChangeOrderVO.getOid() );
							ketMoldECAEpmDocLink.setDieNo(PersistenceHelper.getObjectIdentifier(ketMoldChangeOrder).getStringValue());

							// }

							ketMoldECAEpmDocLink.setScheduleId(ketMoldECALinkVO.getScheduleId());
							if (ketMoldChangeOrderVO.getIsToDO().equals("Y") && StringUtil.checkNull(ketMoldECALinkVO.getEpmDocReviseYn()).equals("N")
									&& StringUtil.checkNull(ketMoldECALinkVO.getEpmDocCancelRevYn()).equals("N")) {
								ketMoldECAEpmDocLink.setAfterVersion(ketMoldECALinkVO.getRelEcaObjectAfterRev());
							}

							if (ketMoldChangeOrderVO.getIsToDO().equals("Y")) {
								ketMoldECAEpmDocLink.setChangeType(ketMoldECALinkVO.getChangeType());
							}

							ketMoldECAEpmDocLink.setEpmDocType(ketMoldECALinkVO.getRelEcaEpmDocType());
							PersistenceHelper.manager.save(ketMoldECAEpmDocLink);

							if (!"".equals(ketMoldECALinkVO.getNewMoldChangePlanOid())) {
								saveMoldEcoPlanLink(ketMoldChangeOrder, ketMoldECALinkVO);
							}
						}
					}
				}
			}
		} catch (WTException wte) {
			throw wte;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * 함수명 : saveRelBomList 설명 : 금형 ECO 관련 BOM 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	// 관련BOM 삭제 및 저장
	@SuppressWarnings("rawtypes")
	private void saveRelBomList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		KETMoldECABomLink ketMoldECABomLink = null;
		KETBomEcoHeader bomEcoHeader = null;
		WTConnection wtconnection = null;
		Connection con = null;

		try {
			EcmUtil.getWTConnection();
			wtconnection = EcmUtil.getWTConnection();
			con = wtconnection.getConnection();
			con.setAutoCommit(false);
			EcmComDao ecmComDao = new EcmComDao(con);
			WTPart part = null;

			WTPrincipal session = null;
			// Set temporary Session - Administrator
			session = SessionHelper.manager.getPrincipal();
			SessionHelper.manager.setAdministrator();

			// 관련BOM삭제(ECO 상태가 계획수립인 경우에만 삭제 가능하다.)
			String ecoStatus = ketMoldChangeOrder.getLifeCycleState().getStringValue();
			if (ecoStatus.indexOf("PLANNING") > -1 || ecoStatus.indexOf("EXCUTEACTIVITY") > -1 || ecoStatus.indexOf("REWORK") > -1) {

				String deleteList = ketMoldChangeOrderVO.getDeleteRelBomList();

				KETMoldChangeActivity delEca = null;

				ArrayList arrDeleteList = KETStringUtil.getToken(deleteList, "*");
				int size = arrDeleteList.size();
				int pos = 0;
				String type = "";
				for (int i = 0; i < size; i++) {
					String oid = (String) arrDeleteList.get(i);
					type = "";
					pos = oid.indexOf(":");
					if (pos > -1) {
						type = oid.substring(0, pos);
						oid = oid.substring(pos + 1);
					}

					if (StringUtil.checkNull(type).equals("2")) {
						ketMoldECABomLink = (KETMoldECABomLink) CommonUtil.getObject(oid);
						// 1.2 Object Delete
						if (ketMoldECABomLink != null) {

							// 연관 BOM 개정 취소 처리
							bomEcoHeader = ketMoldECABomLink.getBom();
							// part = (WTPart)WCUtil.getObject( ketMoldECABomLink.getBom() );
							if (StringUtil.checkNull(ketMoldECABomLink.getAfterVersion()).length() > 0) {

								throw new WTException("개정된 부품은 삭제할 수 없습니다.[" + bomEcoHeader.getEcoItemCode() + "]");

								/*
								 * part = (WTPart) KETObjectUtil.getObject(ecmComDao.getLastestPart(bomEcoHeader.getEcoItemCode())); PersistenceHelper.manager.delete(part);
								 */
							}

							ecmComDao.deleteBomCoWorker(ketMoldChangeOrder.getEcoId(), bomEcoHeader.getEcoItemCode());
							ecmComDao.deleteBomComponent(ketMoldChangeOrder.getEcoId(), bomEcoHeader.getEcoItemCode());
							ecmComDao.deleteBomTempComponent(ketMoldChangeOrder.getEcoId(), bomEcoHeader.getEcoItemCode());

							PersistenceHelper.manager.delete(bomEcoHeader);

							// 연관 ECA의 삭제 처리
							delEca = ketMoldECABomLink.getMoldECA();

							// 활동수행일 경우
							// 기존 To-Do 제거
							/*
							 * WorkflowSearchHelper.manager.taskComplete(delEca);
							 */

							PersistenceHelper.manager.delete(ketMoldECABomLink);
							delEca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(delEca);
							PersistenceHelper.manager.delete(delEca);
							WorkflowSearchHelper.manager.deleteWorkItem(delEca);

						}
					}
				}
			}

			SessionHelper.manager.setPrincipal(session.getName());

			KETMoldChangeActivity ketMoldChangeActivity = null;
			KETMoldECALinkVO ketMoldECALinkVO = null;
			KETBomEcoHeader ketBomEcoHeader = null;
			String planDate = "";

			// 관련BOM 추가
			ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = ketMoldChangeOrderVO.getKetMoldECALinkVOList();// 금형ECO관련문서 List

			if (ketMoldECALinkVOList != null) {
				for (int i = 0; i < ketMoldECALinkVOList.size(); i++) {
					ketMoldECALinkVO = (KETMoldECALinkVO) ketMoldECALinkVOList.get(i);

					if (StringUtil.checkNull(ketMoldECALinkVO.getActivityType()).equals("2")) {
						// 금형활동계획담당자 저장
						ketMoldChangeActivity = saveMoldChangeActivity(ketMoldChangeOrder, ketMoldECALinkVO);
						// BomEcoHeader 저장
						ketBomEcoHeader = saveBomEcoHeader(ketMoldChangeOrder, ketMoldECALinkVO,
								ecmComDao.getChangeReasonName(ketMoldChangeOrder.getChangeReason(), "CHANGEREASON"));

						// 금형활동계획관련 문서가 없는 경우 다음 자료 처리
						if (StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectOid()).length() > 0) {
							if ("".equals(ketMoldECALinkVO.getRelEcaObjectLinkOid())) {// 신규
								Hashtable<String, String> coWorkerHash = EcmUtil.getCoWorkerInfo(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo(),
										ketMoldECALinkVO.getWorkerId());
								ecmComDao.insertBomCoWoker(coWorkerHash);
								ketMoldECABomLink = KETMoldECABomLink.newKETMoldECABomLink(ketBomEcoHeader, ketMoldChangeActivity);
							} else {// 변경
								ketMoldECABomLink = (KETMoldECABomLink) CommonUtil.getObject(ketMoldECALinkVO.getRelEcaObjectLinkOid());

								if (StringUtil.checkNull(ketMoldChangeOrderVO.getIsToDO()).equals("Y")) {
									if (StringUtil.checkNull(ketMoldECALinkVO.getRelEcaObjectAfterRev()).equals("")) {
										ketBomEcoHeader.setAttribute1("N");
										ketBomEcoHeader.setBOMVersion(ketMoldECALinkVO.getRelEcaObjectPreRev());
										ketBomEcoHeader.setBoxQuantity(ketBomEcoHeader.getAttribute2());

										ecmComDao.deleteBomComponent(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo());
										ecmComDao.deleteBomTempComponent(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo());
										ecmComDao.updateBomCoWorker(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo());
									} else {
										ketBomEcoHeader.setAttribute1("Y");
										ketBomEcoHeader.setBOMVersion(ketMoldECALinkVO.getRelEcaObjectAfterRev());
									}
								}

								ketBomEcoHeader = (KETBomEcoHeader) PersistenceHelper.manager.modify(ketBomEcoHeader);
								ketMoldECABomLink.setBom(ketBomEcoHeader);
							}
							//Start 0버전이면서 작업중이면 bom수정이 가능하도록 한다 2015.06.18 황정태
							part = (WTPart)CommonUtil.getObject(ketMoldECALinkVO.getBeforePartOid());
							if ("0".equals(VersionUtil.getMajorVersion(part)) && State.INWORK == part.getLifeCycleState()) {
							    ketBomEcoHeader.setAttribute1("Y");
							    ketBomEcoHeader = (KETBomEcoHeader) PersistenceHelper.manager.modify(ketBomEcoHeader);
							    ketMoldECABomLink.setBom(ketBomEcoHeader);
							}
							//End   0버전이면서 작업중이면 bom수정이 가능하도록 한다 2015.06.18 황정태
							
							ketMoldECABomLink.setActivityComment(ketMoldECALinkVO.getRelEcaObjectActivityComment());

							planDate = ketMoldECALinkVO.getRelEcaObjectPlanDate();
							if (!"".equals(planDate)) {
								planDate = EcmUtil.getDefaultDateFormat(planDate);
								ketMoldECABomLink.setPlanDate(DateUtil.convertDate2(planDate));
							} else {
								ketMoldECABomLink.setPlanDate(null);
							}

							ketMoldECABomLink.setPreVersion(ketMoldECALinkVO.getRelEcaObjectPreRev());

							if (StringUtil.checkNull(ketMoldChangeOrderVO.getIsToDO()).equals("Y")) {
								ketMoldECABomLink.setAfterVersion(ketMoldECALinkVO.getRelEcaObjectAfterRev());
								ketMoldECABomLink.setChangeYn(ketMoldECALinkVO.getRelEcaEpmChangeYn());
							}

							ketMoldECABomLink.setBeforePartOid(ketMoldECALinkVO.getBeforePartOid());
							

							// 비용확보
							ketMoldECABomLink.setRelatedDieNo(ketMoldECALinkVO.getRelated_die_no());
							ketMoldECABomLink.setExpectCost(ketMoldECALinkVO.getExpect_cost());
							ketMoldECABomLink.setSecureBudgetYn(ketMoldECALinkVO.getSecure_budget_yn());

							PersistenceHelper.manager.save(ketMoldECABomLink);
						}
					}
				}
			}

			con.commit();

		} catch (WTException wte) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				Kogger.error(getClass(), e1);
			}
			throw wte;
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				Kogger.error(getClass(), e1);
			}
			throw e;
		} finally {
			try {
				EcmUtil.freeWTConnection(wtconnection);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
		}
	}

	/**
	 * 
	 * 함수명 : saveMoldChangeActivity 설명 : 금형 ECO 관련 변경활동 내역을 저장한다.
	 * 
	 * @param ketMoldChangeOrder
	 *            : 금형 ECO 객체
	 * @param ketMoldECALinkVO
	 *            : 금형 ECA 저장용 VO
	 * @return KETMoldChangeActivity 객체
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	public KETMoldChangeActivity saveMoldChangeActivity(KETMoldChangeOrder ketMoldChangeOrder, KETMoldECALinkVO ketMoldECALinkVO) throws WTException {
		try {
			String workerId = ketMoldECALinkVO.getWorkerId();
			KETMoldChangeActivity ketMoldChangeActivity = null;

			/*
			 * 신규냐 수정이냐를 예전에는 아래 쿼리로 가져왔으나 이젠 KETMoldChangeActivity 의 ID를 넘겨받고 있으므로 그것의 유무로 처리한다.
			 */
			String relEcaOid = StringUtils.stripToEmpty(ketMoldECALinkVO.getRelEcaOid());
			ketMoldChangeActivity = (KETMoldChangeActivity) CommonUtil.getObject(relEcaOid);
			/*
			 * //변경활동구분, 변경활동 담당자 OID, 금형 ECO OID에 해당되는 변경활동 자료를 조회한다. ketMoldChangeActivity = getMoldChangeActivity(ketMoldECALinkVO.getActivityType(), workerId,
			 * ketMoldChangeOrder.getPersistInfo().getObjectIdentifier().getId());
			 */

			// 담당자별 해당 ECA가 존재하지 않는 경우 활동계획을 신규 저장한다.
			if (ketMoldChangeActivity == null) {
				ketMoldChangeActivity = KETMoldChangeActivity.newKETMoldChangeActivity();

				// 결재상태 및 저장 폴더를 세팅한다.
				WTContainerRef containerRef = WCUtil.getWTContainerRef();
				String folderPath = "";
				if ("C".equals(ketMoldChangeOrder.getDevYn())) {
					folderPath = ECMProperties.getInstance().getString("ecm.folder.car");// 자동차사업부
				} else if ("K".equals(ketMoldChangeOrder.getDevYn())) {
					folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");// KETS
				} else {
					folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");// 전자사업부
				}
				folderPath += DateUtil.getThisYear();
				SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
				ketMoldChangeActivity.setActivityStatus("PLANNING");
				// 금형ECO 활동계획 저장 폴더를 지정한다.
				FolderHelper.assignLocation((FolderEntry) ketMoldChangeActivity, folder);
				// 금형ECO 활동계획 결재상태를 지정한다.
				WTContainerRef containerRef1 = WCUtil.getWTContainerRef();
				String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.eca");
				LifeCycleHelper.setLifeCycle(ketMoldChangeActivity, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef1));

			}

			ketMoldChangeActivity.setMoldECO(ketMoldChangeOrder);
			ketMoldChangeActivity.setActivityType(ketMoldECALinkVO.getActivityType());
			ketMoldChangeActivity.setWorkerId(workerId);
			// 금형ECO 활동계획을 추가 저장한다.
			ketMoldChangeActivity = (KETMoldChangeActivity) PersistenceHelper.manager.save(ketMoldChangeActivity);

			return ketMoldChangeActivity;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : saveBomEcoHeader 설명 : BOM Header 자료를 신규 저장한다.
	 * 
	 * @param ketMoldChangeOrder
	 *            : 금형 ECO 객체
	 * @param ketMoldECALinkVO
	 *            : 금형 ECA 저장용 VO
	 * @return KETBomEcoHeader 객체
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	private KETBomEcoHeader saveBomEcoHeader(KETMoldChangeOrder ketMoldChangeOrder, KETMoldECALinkVO ketMoldECALinkVO, String chgReason) throws WTException {

		KETBomEcoHeader header = null;

		try {

			/*
			 * header = getBomEcoHeader(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo());
			 */
			header = EcmSearchHelper.manager.getProdEcoBomHeader(ketMoldChangeOrder.getEcoId(), ketMoldECALinkVO.getRelEcaObjectNo());

			// BOM Header가 존재하지 않는 경우
			if (header == null) {
				WTPart part = (WTPart) KETObjectUtil.getObject(ketMoldECALinkVO.getRelEcaObjectOid());

				// 부품이 초도일 경우 KETBomHeader, 아닐 경우 KETEcoHeader에 set 한다.
				/*
				 * String bomflag = PartSpecGetter.getPartSpec((WTPart) part, PartSpecEnum.SpBOMFlag); if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {
				 */
				// 초도인지 아닌지
				String changeReason = StringUtils.stripToEmpty(ketMoldChangeOrder.getChangeReason());
				boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
				if (!isTheFirst) {
					header = KETBomEcoHeader.newKETBomEcoHeader();
				} else {
					header = KETBomHeader.newKETBomHeader();

					KETBomHeader ketBomHeader = (KETBomHeader) header;
					ketBomHeader.setNewBOMCode(part.getNumber());
					header = (KETBomEcoHeader) ketBomHeader;
				}

				WTContainerRef containerRef = WCUtil.getWTContainerRef();
				String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.bom");

				header.setContainerReference(containerRef);
				header.setContainer(containerRef.getReferencedContainerReadOnly());

				LifeCycleHelper.setLifeCycle(header, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

				String folderPath = ECMProperties.getInstance().getString("bom.folder");

				Folder folder = (Folder) FolderHelper.service.getFolder(folderPath, containerRef);
				FolderHelper.assignLocation((FolderEntry) header, folder);
				header.setOrgCode("000"); // 한국단자에서는 사용하지 않음 (임의로 '000'으로 지정)
				header.setEcoHeaderNumber(ketMoldChangeOrder.getEcoId());
				header.setEcoItemCode(ketMoldECALinkVO.getRelEcaObjectNo());
				header.setDescription(ketMoldECALinkVO.getRelEcaObjectName());
				// bomHeader.setBOMVersion( VersionUtil.getMajorVersion(part) );
				header.setIsMultiple("N");
				header.setTransferFlag("1");
				header.setBOMVersion(VersionUtil.getMajorVersion(part));
				header.setUnitOfQuantity(String.valueOf(part.getDefaultUnit())); // 기본단위 셋팅
				header.setQuantity("1.0"); // 기준수량 '1'로 셋팅
				header.setBoxQuantity("1"); // 포장수량 '1'로 셋팅
				header.setAttribute2("1"); // 이전 포장수량 저장
				header.setBOMUse("2"); // Fixed Value '2'(개발)
				header.setBOMText(chgReason);
				header.setAttribute1("N");
				header = (KETBomEcoHeader) PersistenceHelper.manager.save(header);
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}

		return header;
	}

	/**
	 * 
	 * 함수명 : saveMoldEcoPlanLink 설명 : 금형 ECO - 변경계획 LINK를 저장한다.
	 * 
	 * @param ketMoldChangeOrder
	 *            : 금형 ECO 객체
	 * @param ketMoldECALinkVO
	 *            : 금형 ECA 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	private void saveMoldEcoPlanLink(KETMoldChangeOrder ketMoldChangeOrder, KETMoldECALinkVO ketMoldECALinkVO) throws WTException {
		try {
			String oid = ketMoldECALinkVO.getNewMoldChangePlanOid();
			KETMoldChangePlan ketMoldChangePlan = (KETMoldChangePlan) CommonUtil.getObject(oid);// ECO Plan 객체조회
			if (ketMoldChangePlan == null) {
				return;
			}

			ketMoldChangePlan.setMoldECO(ketMoldChangeOrder);
			ketMoldChangePlan.setMoldEcoOwner((WTUser) ketMoldChangeOrder.getCreator().getPrincipal());
			ketMoldChangePlan.setVendorFlag(StringUtil.checkNull(ketMoldChangeOrder.getVendorFlag()));
			ketMoldChangePlan.setVendorCode(StringUtil.checkNull(ketMoldChangeOrder.getProdVendor()));

			PersistenceHelper.manager.save(ketMoldChangePlan);
			return;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : getMoldChangeActivity 설명 : 변경활동구분, 변경활동 담당자 OID, 금형 ECO OID에 해당되는 변경활동 자료를 조회한다.
	 * 
	 * @param activityType
	 *            : 변경활동구분
	 * @param workerId
	 *            : 변경활동 담당자 OID
	 * @param moldEcoOid
	 *            : 금형 ECO OID
	 * @return KETMoldChangeActivity 객체 작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("deprecation")
	public KETMoldChangeActivity getMoldChangeActivity(String activityType, String workerId, long moldEcoOid) {
		KETMoldChangeActivity ketMoldChangeActivity = null;
		try {
			QuerySpec spec = new QuerySpec(KETMoldChangeActivity.class);
			SearchUtil.appendEQUAL(spec, KETMoldChangeActivity.class, KETMoldChangeActivity.ACTIVITY_TYPE, activityType, 0);
			SearchUtil.appendEQUAL(spec, KETMoldChangeActivity.class, KETMoldChangeActivity.WORKER_ID, workerId, 0);
			SearchUtil.appendEQUAL(spec, KETMoldChangeActivity.class, "moldECOReference.key.id", moldEcoOid, 0);
			// 변경활동구분, 변경활동 담당자 OID, 금형 ECO OID에 해당되는 변경활동 자료를 조회한다.
			QueryResult result = PersistenceHelper.manager.find(spec);
			if (result != null && result.hasMoreElements()) {
				ketMoldChangeActivity = (KETMoldChangeActivity) result.nextElement();
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return ketMoldChangeActivity;
	}

	/**
	 * 
	 * 함수명 : getBomEcoHeader 설명 : 금형 ECOID, 부품번호에 해당되는 BomEcoHeader 자료를 조회한다.
	 * 
	 * @param ecoId
	 *            : 금형 ECOID
	 * @param partNo
	 *            : 부품번호
	 * @return KETBomEcoHeader : KETBomEcoHeader 객체 작성자 : 안수학 작성일자 : 2010. 12. 31.
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("deprecation")
	public KETBomEcoHeader getBomEcoHeader(String ecoId, String partNo) {
		KETBomEcoHeader ketBomEcoHeader = null;
		try {
			QuerySpec spec = new QuerySpec(KETBomEcoHeader.class);
			SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, ecoId, 0);
			SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);
			// 금형 ECOID, 부품번호에 해당되는 BomEcoHeader 자료를 조회한다.
			QueryResult result = PersistenceHelper.manager.find(spec);
			if (result != null && result.hasMoreElements()) {
				ketBomEcoHeader = (KETBomEcoHeader) result.nextElement();
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return ketBomEcoHeader;
	}

	/**
	 * 
	 * 함수명 : getMoldEcoPlanLink 설명 : 금형 ECO OID, 금형 ECO 변경계획 OID에 해당되는 금형 ECO 변경계획 LINK 자료를 조회한다.
	 * 
	 * @param moldEcoOid
	 *            : 금형 ECO OID
	 * @param moldEcoPlanOid
	 *            : 금형 ECO 변경계획 OID
	 * @return KETMoldECOPlanLink 객체 작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("deprecation")
	public KETMoldECOPlanLink getMoldEcoPlanLink(long moldEcoOid, long moldEcoPlanOid) {
		KETMoldECOPlanLink ketMoldEcoPlanLink = null;
		try {
			QuerySpec spec = new QuerySpec(KETMoldECOPlanLink.class);
			SearchUtil.appendEQUAL(spec, KETMoldECOPlanLink.class, "roleAObjectRef.key.id", moldEcoOid, 0);
			SearchUtil.appendEQUAL(spec, KETMoldECOPlanLink.class, "roleBObjectRef.key.id", moldEcoPlanOid, 0);
			// 금형 ECO OID, 금형 ECO 변경계획 OID에 해당되는 금형 ECO 변경계획 LINK 자료를 조회한다.
			QueryResult result = PersistenceHelper.manager.find(spec);
			if (result != null && result.hasMoreElements()) {
				ketMoldEcoPlanLink = (KETMoldECOPlanLink) result.nextElement();
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return ketMoldEcoPlanLink;
	}

	/**
	 * 
	 * 함수명 : saveRelEcrList 설명 : 금형 ECO 연계 ECR 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void saveRelEcrList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			KETMoldChangeLink ketMoldChangeLink = null;
			// 연계ECR 삭제
			String deleteList = ketMoldChangeOrderVO.getDeleteRelEcrList();
			ArrayList arrDeleteList = (deleteList != null) ? KETStringUtil.getToken(deleteList, "*") : null;
			int size = (arrDeleteList != null) ? arrDeleteList.size() : 0;
			for (int i = 0; i < size; i++) {
				String oid = (String) arrDeleteList.get(i);
				ketMoldChangeLink = (KETMoldChangeLink) CommonUtil.getObject(oid);
				// 1.2 Object Delete
				if (ketMoldChangeLink != null) {
					PersistenceHelper.manager.delete(ketMoldChangeLink);
				}
			}

			// 연계ECR 추가
			KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
			ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoEcrLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoEcrLinkVOList();// 금형ECO 연계ECR List
			if (ketMoldEcoEcrLinkVOList == null) {
				return;
			} else {
				size = ketMoldEcoEcrLinkVOList.size();
			}
			KETMoldEcoEcrLinkVO ketMoldEcoEcrLinkVO = null;
			KETMoldChangeRequest ketMoldChangeRequest = null;
			for (int i = 0; i < size; i++) {
				ketMoldEcoEcrLinkVO = (KETMoldEcoEcrLinkVO) ketMoldEcoEcrLinkVOList.get(i);
				ketMoldChangeRequest = (KETMoldChangeRequest) CommonUtil.getObject(ketMoldEcoEcrLinkVO.getRelEcrOid());
				if ("".equals(ketMoldEcoEcrLinkVO.getRelEcrLinkOid())) {// 신규
					ketMoldChangeLink = KETMoldChangeLink.newKETMoldChangeLink(ketMoldChangeOrder, ketMoldChangeRequest);
				} else {// 변경
					ketMoldChangeLink = (KETMoldChangeLink) CommonUtil.getObject(ketMoldEcoEcrLinkVO.getRelEcrLinkOid());
					ketMoldChangeLink.setMoldECR(ketMoldChangeRequest);
				}
				PersistenceHelper.manager.save(ketMoldChangeLink);
			}
		} catch (Exception e) {
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : saveRelProdEcoList 설명 : 금형 ECO 연계 제품 ECO 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void saveRelProdEcoList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			KETMoldProdEcoLink ketMoldProdEcoLink = null;
			// 연계제품ECO 삭제
			String deleteList = ketMoldChangeOrderVO.getDeleteRelProdEcoList();
			ArrayList arrDeleteList = (deleteList != null) ? KETStringUtil.getToken(deleteList, "*") : null;
			int size = (arrDeleteList != null) ? arrDeleteList.size() : 0;
			for (int i = 0; i < size; i++) {
				String oid = (String) arrDeleteList.get(i);
				ketMoldProdEcoLink = (KETMoldProdEcoLink) CommonUtil.getObject(oid);
				// 1.2 Object Delete
				if (ketMoldProdEcoLink != null) {
					PersistenceHelper.manager.delete(ketMoldProdEcoLink);
				}
			}

			// 연계ECR 추가
			KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
			ArrayList<KETMoldEcoEcrLinkVO> ketMoldEcoProdEcoLinkVOList = ketMoldChangeOrderVO.getKetMoldEcoProdEcoLinkVOList();// 금형ECO 연계ECR List
			if (ketMoldEcoProdEcoLinkVOList == null) {
				return;
			} else {
				size = ketMoldEcoProdEcoLinkVOList.size();
			}
			KETMoldEcoEcrLinkVO ketMoldEcoProdEcoLinkVO = null;
			KETProdChangeOrder ketProdChangeOrder = null;
			for (int i = 0; i < size; i++) {
				ketMoldEcoProdEcoLinkVO = (KETMoldEcoEcrLinkVO) ketMoldEcoProdEcoLinkVOList.get(i);
				ketProdChangeOrder = (KETProdChangeOrder) CommonUtil.getObject(ketMoldEcoProdEcoLinkVO.getRelEcrOid());
				if ("".equals(ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid())) {// 신규
					ketMoldProdEcoLink = KETMoldProdEcoLink.newKETMoldProdEcoLink(ketProdChangeOrder, ketMoldChangeOrder);
				} else {// 변경
					ketMoldProdEcoLink = (KETMoldProdEcoLink) CommonUtil.getObject(ketMoldEcoProdEcoLinkVO.getRelEcrLinkOid());
					ketMoldProdEcoLink.setProdECO(ketProdChangeOrder);
				}
				PersistenceHelper.manager.save(ketMoldProdEcoLink);
			}
		} catch (Exception e) {
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : saveRelPartList 설명 : 금형 ECO 관련 부품 목록을 삭제 및 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("rawtypes")
	private void saveRelPartList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			KETMoldECOPartLink ketMoldECOPartLink = null;
			// 관련부품삭제
			String deleteList = ketMoldChangeOrderVO.getDeleteRelPartList();
			ArrayList arrDeleteList = (deleteList != null) ? KETStringUtil.getToken(deleteList, "*") : null;
			int size = (arrDeleteList != null) ? arrDeleteList.size() : 0;
			for (int i = 0; i < size; i++) {
				String oid = (String) arrDeleteList.get(i);
				ketMoldECOPartLink = (KETMoldECOPartLink) CommonUtil.getObject(oid);
				// 1.2 Object Delete
				PersistenceHelper.manager.delete(ketMoldECOPartLink);
			}

			// 관련부품 추가
			KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
			ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeOrderVO.getKetMoldECOPartLinkVOList();// 금형ECO관련부품 List
			size = (ketMoldECOPartLinkVOList != null) ? ketMoldECOPartLinkVOList.size() : 0;
			KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
			WTPart part = null;

			for (int i = 0; i < size; i++) {
				ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO) ketMoldECOPartLinkVOList.get(i);
				part = (WTPart) CommonUtil.getObject(ketMoldECOPartLinkVO.getRelPartOid());

				if ("".equals(ketMoldECOPartLinkVO.getRelPartLinkOid())) {// 신규
					ketMoldECOPartLink = KETMoldECOPartLink.newKETMoldECOPartLink(part, ketMoldChangeOrder);
				} else {// 변경
					ketMoldECOPartLink = (KETMoldECOPartLink) CommonUtil.getObject(ketMoldECOPartLinkVO.getRelPartLinkOid());
					ketMoldECOPartLink.setPart(part);
				}

				ketMoldECOPartLink.setSecureBudgetYn(ketMoldECOPartLinkVO.getSecureBudgetYn());
				ketMoldECOPartLink.setExpectCost(ketMoldECOPartLinkVO.getExpectCost());

				PersistenceHelper.manager.save(ketMoldECOPartLink);
			}
		} catch (Exception e) {
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : saveFileList 설명 : 금형 ECO 관련 첨부파일을 삭제 및 추가 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void saveFileList(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
			// 삭제 요청된 파일목록을 삭제한다.
			String deleteFileList = ketMoldChangeOrderVO.getDeleteFileList();
			if (deleteFileList != null && !"".equals(deleteFileList)) {
				Vector delFileList = new Vector();
				StringTokenizer tokens = new StringTokenizer(deleteFileList, "*");
				while (tokens.hasMoreElements()) {
					delFileList.addElement(tokens.nextElement());
				}
				int delFileCnt = delFileList.size();
				QueryResult rs = ContentHelper.service.getContentsByRole(ketMoldChangeOrder, ContentRoleType.SECONDARY);
				while (rs.hasMoreElements()) {
					ContentItem allContentItem = (ContentItem) rs.nextElement();
					Object allObj = allContentItem;
					String ItemString = allObj.toString();
					for (int i = 0; i < delFileCnt; i++) {
						if (delFileList.get(i).equals(ItemString)) {
							ketMoldChangeOrder = (KETMoldChangeOrder) E3PSContentHelper.service.delete(ketMoldChangeOrder, allContentItem);
						}
					}
				}
			}

			// 금형 ECO 관련 첨부파일을 추가 저장한다.
			java.util.Vector files = ketMoldChangeOrderVO.getFiles();
			int size = (files != null) ? files.size() : 0;
			for (int i = 0; i < size; i++) {
				ketMoldChangeOrder = (KETMoldChangeOrder) E3PSContentHelper.service.attach((ContentHolder) ketMoldChangeOrderVO.getKetMoldChangeOrder(), (WBFile) files.get(i), "",
						ContentRoleType.SECONDARY);
			}
		} catch (Exception e) {
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * 함수명 : updateMoldEcoInfo 설명 : 금형 ECO 변경 내역을 저장한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형 ECO 저장용 VO
	 * @return ketMoldChangeOrderVO : 금형 ECO 저장용 VO
	 * @throws Exception
	 */
	public KETMoldChangeOrderVO updateMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws Exception {
		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		KETMoldChangeOrder ketMoldChangeOrder2 = null;
		try {
			// //1. Object Create
			ketMoldChangeOrder2 = KETMoldChangeOrder.newKETMoldChangeOrder();
			// //1.1 Object Setting
			ketMoldChangeOrder.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);
			// //1.2 Object Save
			ketMoldChangeOrder2 = (KETMoldChangeOrder) PersistenceHelper.manager.modify(ketMoldChangeOrder);
			ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

			saveFileList(ketMoldChangeOrderVO);// 첨부파일 저장
			saveRelEcrList(ketMoldChangeOrderVO);// 연계ECR 저장
			saveRelProdEcoList(ketMoldChangeOrderVO);// 연계제품ECO 저장

			saveRelPartList(ketMoldChangeOrderVO);// 관련부품 저장
			if (ketMoldChangeOrderVO.getIsCompleteModify().equals("N")) {
				saveRelDocList(ketMoldChangeOrderVO);// 문서목록 저장
				saveRelEpmList(ketMoldChangeOrderVO);// 도면목록 저장
				saveRelBomList(ketMoldChangeOrderVO);// BOM목록 저장
			}
			// 활동완료 상태의 수정일 경우 ECN(4M변경)은 수정할 수 있어야 한다.
			else {
				saveRelDocList(ketMoldChangeOrderVO);// 문서목록 저장

			}
			// ECO 상태가 활동수행인 경우 결재대기 LIST를 재생성한다.
			if (ketMoldChangeOrder.getLifeCycleState().getStringValue().indexOf("EXCUTEACTIVITY") > -1) {
				KETWfmHelper.service.createWorkItem(ketMoldChangeOrder);
			}
		} catch (WTException wte) {
			Kogger.error(getClass(), wte);
			throw wte;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw e;
		} finally {
		}
		return ketMoldChangeOrderVO;
	}

	/**
	 * 
	 * 함수명 : deleteMoldEcoInfo 설명 : 금형ECO 관련 자료를 삭제한다. 금형ECO 관련 첨부파일을 조회하여 모두 삭제한다. 금형ECO 관련 변경활동 자료를 조회한다. 금형ECO 변경활동과 관련된 도면을 조회하여 모두 삭제한다. 금형ECO 변경활동과 관련된 BOM을 조회하여 모두 삭제한다.
	 * 금형ECO 변경활동과 관련된 문서를 조회하여 모두 삭제한다. 금형ECO 관련 변경활동 자료를 삭제한다. 금형ECO 자료를 삭제한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형ECO VO
	 * @return 0
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("deprecation")
	public int deleteMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO, Connection conn) throws WTException {

		int rtnValue = 0;

		KETMoldChangeOrder ketMoldChangeOrder = ketMoldChangeOrderVO.getKetMoldChangeOrder();
		KETMoldChangeOrder ketMoldChangeOrder2 = null;

		EcmComDao comDao = new EcmComDao(conn);
		ProdEcoBeans prodBeans = new ProdEcoBeans();

		try {
			ketMoldChangeOrder2 = KETMoldChangeOrder.newKETMoldChangeOrder();
			// 금형ECO 관련 첨부파일을 조회하여 모두 삭제한다.
			QueryResult rs = ContentHelper.service.getContentsByRole(ketMoldChangeOrder, ContentRoleType.SECONDARY);
			while (rs.hasMoreElements()) {
				ContentItem allContentItem = (ContentItem) rs.nextElement();
				E3PSContentHelper.service.delete(ketMoldChangeOrder, allContentItem);
			}

			// Delete All Part Link
			rs = PersistenceHelper.manager.navigate(ketMoldChangeOrder, "part", KETMoldECOPartLink.class, false);
			while (rs.hasMoreElements()) {
				PersistenceHelper.manager.delete((KETMoldECOPartLink) rs.nextElement());
			}

			// Delete All ECR Link
			rs = PersistenceHelper.manager.navigate(ketMoldChangeOrder, "moldECR", KETMoldChangeLink.class, false);
			while (rs.hasMoreElements()) {
				PersistenceHelper.manager.delete((KETMoldChangeLink) rs.nextElement());
			}

			// Delete All ProdECO Link
			rs = PersistenceHelper.manager.navigate(ketMoldChangeOrder, "prodECO", KETMoldProdEcoLink.class, false);
			while (rs.hasMoreElements()) {
				PersistenceHelper.manager.delete((KETMoldProdEcoLink) rs.nextElement());
			}

			// 금형ECO 관련 변경활동 자료를 조회한다.
			KETMoldChangeActivity moldECA = null;
			KETMoldECAEpmDocLink epmDocLink = null;
			KETMoldECABomLink bomLink = null;
			KETMoldECADocLink docLink = null;
			EPMDocument epmDoc = null;
			WTPart part = null;
			rs = PersistenceHelper.manager.navigate(ketMoldChangeOrder, "moldECA", KETMoldChangeActivityLink.class);
			long moldLOid = 0;
			while (rs.hasMoreElements()) {
				moldECA = (KETMoldChangeActivity) rs.nextElement();
				moldLOid = WCUtil.getLongPersistable(moldECA);

				// 금형ECO 변경활동과 관련된 도면을 조회하여 모두 삭제한다.
				QuerySpec spec = new QuerySpec(KETMoldECAEpmDocLink.class);
				SearchUtil.appendEQUAL(spec, KETMoldECAEpmDocLink.class, "roleBObjectRef.key.id", moldLOid, 0);
				QueryResult qr = PersistenceHelper.manager.find(spec);
				while (qr != null && qr.hasMoreElements()) {
					epmDocLink = (KETMoldECAEpmDocLink) qr.nextElement();
					/*
					 * if (StringUtil.checkNull(epmDocLink.getAfterVersion()).length() > 0) { epmDoc = prodBeans.getLastestEPMDoc(epmDocLink.getEpmDoc());
					 * cancelRevEpm(KETObjectUtil.getOidString(epmDoc)); }
					 */

					PersistenceHelper.manager.delete(epmDocLink);
				}

				// 금형ECO 변경활동과 관련된 BOM을 조회하여 모두 삭제한다.
				spec = new QuerySpec(KETMoldECABomLink.class);
				SearchUtil.appendEQUAL(spec, KETMoldECABomLink.class, "roleBObjectRef.key.id", moldLOid, 0);
				qr = PersistenceHelper.manager.find(spec);
				while (qr != null && qr.hasMoreElements()) {
					bomLink = (KETMoldECABomLink) qr.nextElement();
					KETBomEcoHeader bomHeader = bomLink.getBom();
					/*
					 * if (StringUtil.checkNull(bomLink.getAfterVersion()).length() > 0) { part = prodBeans.getLastestPart(bomLink.getBom().getEcoItemCode(), conn);
					 * PersistenceHelper.manager.delete(part); }
					 */

					PersistenceHelper.manager.delete(bomLink);
					PersistenceHelper.manager.delete(bomHeader);

				}

				// 금형ECO 변경활동과 관련된 문서를 조회하여 모두 삭제한다.
				spec = new QuerySpec(KETMoldECADocLink.class);
				SearchUtil.appendEQUAL(spec, KETMoldECADocLink.class, "roleBObjectRef.key.id", moldLOid, 0);
				qr = PersistenceHelper.manager.find(spec);
				QueryResult subQr = null;

				while (qr != null && qr.hasMoreElements()) {
					docLink = (KETMoldECADocLink) qr.nextElement();

					subQr = PersistenceHelper.manager.navigate(docLink, "part", KETMoldStdPartLink.class);

					while (subQr.hasMoreElements()) {
						PersistenceHelper.manager.delete((KETMoldStdPartLine) qr.nextElement());
					}

					PersistenceHelper.manager.delete(docLink);
				}

				// 금형ECO 관련 변경활동 자료를 삭제한다.
				PersistenceHelper.manager.delete(moldECA);
				WorkflowSearchHelper.manager.deleteWorkItem(moldECA);
			}

			/*
			 * QuerySpec spec = new QuerySpec(KETBomEcoHeader.class); SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER,
			 * ketMoldChangeOrder.getEcoId(), 0); QueryResult result = PersistenceHelper.manager.find(spec);
			 * 
			 * while (result.hasMoreElements()) { PersistenceHelper.manager.delete((KETBomEcoHeader) result.nextElement()); }
			 */

			boolean isDeleteCoWorker = comDao.deleteAllBomCoWorker(ketMoldChangeOrder.getEcoId());
			boolean isDeleteBomComp = comDao.deleteAllBomComponent(ketMoldChangeOrder.getEcoId());
			boolean isDeleteBomTmpComp = comDao.deleteAllBomTempComponent(ketMoldChangeOrder.getEcoId());

			// 금형ECO 자료를 삭제한다.
			ketMoldChangeOrder2 = (KETMoldChangeOrder) PersistenceHelper.manager.delete(ketMoldChangeOrder);
			WorkflowSearchHelper.manager.deleteWorkItem(ketMoldChangeOrder);

			ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder2);

			if (isDeleteCoWorker && isDeleteBomComp && isDeleteBomTmpComp) {
				rtnValue = 1;
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		} finally {
		}

		return rtnValue;
	}

	/**
	 * 
	 * 함수명 : deleteMoldEcaInfo 설명 : 금형ECO 변경활동 내역을 삭제한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형ECO VO
	 * @return 0
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("rawtypes")
	public int deleteMoldEcaInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			ArrayList deleteRelEcaList = ketMoldChangeOrderVO.getDeleteRelEcaList();
			int delectCnt = deleteRelEcaList.size();
			KETMoldChangeActivity ketMoldChangeActivity = null;
			// 금형ECO 변경활동 내역을 삭제한다.
			for (int i = 0; i < delectCnt; i++) {
				ketMoldChangeActivity = (KETMoldChangeActivity) WCUtil.getObject((String) deleteRelEcaList.get(i));
				if (ketMoldChangeActivity != null) {
					PersistenceHelper.manager.delete(ketMoldChangeActivity);
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		} finally {
		}
		return 0;
	}

	/**
	 * 
	 * 함수명 : deleteMoldPlanInfo 설명 : 금형변경일정을 삭제한다.
	 * 
	 * @param ketMoldChangeOrderVO
	 *            : 금형ECO VO
	 * @return 0
	 * @throws WTException
	 *             작성자 : 안수학 작성일자 : 2010. 12. 31.
	 */
	@SuppressWarnings("rawtypes")
	public int deleteMoldPlanInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
		try {
			ArrayList deleteMoldPlanList = ketMoldChangeOrderVO.getDeleteMoldPlanList();
			int delectCnt = deleteMoldPlanList.size();
			KETMoldChangePlan ketMoldChangePlan = null;
			// 금형변경일정을 삭제한다.
			for (int i = 0; i < delectCnt; i++) {
				ketMoldChangePlan = (KETMoldChangePlan) WCUtil.getObject((String) deleteMoldPlanList.get(i));
				if (ketMoldChangePlan != null) {
					PersistenceHelper.manager.delete(ketMoldChangePlan);
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		} finally {
		}
		return 0;
	}

	public Hashtable<String, ArrayList<Hashtable<String, String>>> getInitCodeList(Map<String, Object> parameter) throws Exception {
		Connection conn = null;
		EcmComDao cDao = null;

		Hashtable<String, ArrayList<Hashtable<String, String>>> codeHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();

		try {
			conn = PlmDBUtil.getConnection();

			cDao = new EcmComDao(conn);

			ArrayList<Hashtable<String, String>> divList = cDao.getCodeList("DIVISIONFLAG", "ko");
			ArrayList<Hashtable<String, String>> devFlagList = cDao.getCodeList("DEVYN", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> chgReasonList = cDao.getCodeList("CHANGEREASON", parameter.get("locale").toString());
			ArrayList<Hashtable<String, String>> incProdTypeList = cDao.getCodeList("INCREASEPRODTYPE", parameter.get("locale").toString());

			codeHash.put("division", divList);
			codeHash.put("devYn", devFlagList);
			codeHash.put("changeReason", chgReasonList);
			codeHash.put("incProdType", incProdTypeList);

		} catch (Exception e) {
			throw e;
		} finally {
			PlmDBUtil.close(conn);
		}

		return codeHash;
	}

	/**
	 * 
	 * 함수명 : reviseEpm 설명 : 도면개정
	 * 
	 * @param reviseEpmDocOid
	 *            : 개정 대상 도면 oid
	 * @return afterRev : 개정후 버전
	 * @throws Exception
	 *             작성자 : 안수학 작성일자 : 2010. 12. 30.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String reviseEpm(String reviseEpmDocOid) throws Exception {
		String afterRev = "";
		try {
			EPMDocument epm = (EPMDocument) WCUtil.getObject(reviseEpmDocOid);
			WTPrincipal session = null;
			WTGroup group = null;
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
				EcmUtil.logging("MoldEcoChangeServlet.createKetMoldECALinkVOForEpm().oid:" + oid);
			}

			Kogger.debug(getClass(), "금형 도면 개정 Size ==>" + roids.length + "==================!!!!!!!!!!!!!!!!!!!!!!!!!");
			// 개정

			// ChangeSession set Administrator
			session = SessionHelper.manager.getPrincipal();
			SessionHelper.manager.setAdministrator();
			group = KETObjectUtil.getGroup("Revise Administrators");
			group.addMember(session);
			SessionHelper.manager.setPrincipal(session.getName());

			EDMServiceHelper.batchRevise(roids);

			// Restore Original Session
			SessionHelper.manager.setAdministrator();
			group.removeMember(session);
			SessionHelper.manager.setPrincipal(session.getName());

			RevisionControlled latest = null;
			latest = VersionUtil.getLatestObject((Master) epm.getMaster());
			afterRev = VersionUtil.getMajorVersion(latest);

			EcmUtil.logging("MoldEcoChangeServlet.createKetMoldECALinkVOForEpm().afterRev:" + afterRev);
		} catch (Exception e1) {
			Kogger.error(getClass(), e1);
			throw new Exception(e1);
		}
		return afterRev;
	}// end-of-reviseEpm

	/**
	 * 
	 * 함수명 : cancelRevEpm 설명 : 도면을 개정취소한다.
	 * 
	 * @param cancelRevEpmDocOid
	 *            : 개정취소 대상 도면 oid
	 * @return afterRev : 개정취소후 버전
	 * @throws Exception
	 *             작성자 : 안수학 작성일자 : 2010. 12. 30.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

				Kogger.debug(getClass(), "=========> MoldEcoBeans.java :: cancelRevEpm() :: Deleted EPM oid " + i + " :: " + oid);
			}

			// 개정취소
			EDMServiceHelper.deleteObjects(roids);
			// EPMDocument latest = null;
			// QueryResult qr = wt.vc.VersionControlHelper.service.allVersionsOf((Mastered)epm.getMaster());
			// if(qr.hasMoreElements()) {
			// latest = (EPMDocument)qr.nextElement();
			// }
			afterRev = "";
			EcmUtil.logging("MoldEcoChangeServlet.createKetMoldECALinkVOForEpm().cancelRevEpm().afterRev:" + afterRev);
		} catch (Exception e1) {
			Kogger.error(getClass(), e1);
			throw new Exception(e1);
		}
		return afterRev;
	}// end-of-cancelRevEpm

	public void updateMoldPlan(String moldEcoOid) {
		QueryResult qr = null;
		KETMoldChangeOrder moldEco = null;
		KETMoldChangePlan plan = null;

		try {
			moldEco = (KETMoldChangeOrder) KETObjectUtil.getObject(moldEcoOid);

			qr = PersistenceHelper.manager.navigate(moldEco, "moldPlan", KETMoldECOPlanLink.class);

			while (qr.hasMoreElements()) {
				plan = (KETMoldChangePlan) qr.nextElement();

				plan.setMoldChangeActualDate(DateUtil.getCurrentTimestamp());
				PersistenceHelper.manager.modify(plan);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
}
