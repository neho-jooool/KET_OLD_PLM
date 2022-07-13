package ext.ket.dms.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import wt.content.ContentHelper;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.dms.entity.KETSGDocument;
import ext.ket.dms.entity.KETSGDocumentDTO;
import ext.ket.dms.util.KETDocumentUtil;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.util.SessionUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 10. 1. 오전 10:00:10
 * @Pakage ext.ket.dms.service
 * @class StandardKETDocumentService
 *********************************************************/
public class StandardKETDocumentService extends StandardManager implements KETDocumentService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StandardKETDocumentService.class);

	/**
	 * <pre>
	 * @description 
	 * @author dhkim
	 * @date 2018. 5. 25. 오전 10:08:11
	 * @method newStandardIssueService
	 * @return StandardIssueService
	 * @throws WTException
	 * </pre>
	 */
	public static StandardKETDocumentService newStandardKETDocumentService() throws WTException {
		StandardKETDocumentService instance = new StandardKETDocumentService();
		instance.initialize();
		return instance;
	}

	/**
	 * <pre>
	 * @description 시스템 사용설명서 조회
	 * @author dhkim
	 * @date 2018. 10. 1. 오후 4:19:44
	 * @method findPagingList
	 * @param sgDoc
	 * @return PageControl
	 * @throws Exception
	 * </pre>
	 */
	@Override
	public PageControl findPagingList(KETSGDocumentDTO dto) throws Exception {
		QuerySpec query = getSGDocumentQuery(dto);
		PageControl pageControl = null;
		String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
		if (StringUtil.isEmpty(pageSessionId)) {
			pageControl = CommonSearchHelper.find(dto.getFormPage(), dto.getFormPage(), query);
		} else {
			pageControl = CommonSearchHelper.find(dto.getFormPage(), dto.getFormPage(), dto.getPage() + 1, pageSessionId);
		}
		return pageControl;
	}

	/**
	 * <pre>
	 * @description 시스템 사용설명서 조회 쿼리 
	 * @author dhkim
	 * @date 2018. 10. 1. 오후 4:23:29
	 * @method getSGDocumentQuery
	 * @param dto
	 * @return QuerySpec
	 * @throws Exception
	 * </pre>
	 */
	private QuerySpec getSGDocumentQuery(KETSGDocumentDTO dto) throws Exception {

		QuerySpec qs = new QuerySpec();
		qs.setAdvancedQueryEnabled(true);

		int idx = qs.addClassList(KETSGDocument.class, true);

		// 최신버전
		if (dto.isLastest()) {
			if (qs.getConditionCount() > 0)
				qs.appendAnd();
			qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });
		}

		SearchUtil.appendEQUAL(qs, KETSGDocument.class, KETSGDocument.MODULE_CODE, dto.getModuleCode(), idx);
		SearchUtil.appendLIKE(qs, KETSGDocument.class, KETSGDocument.REF_VIEW, dto.getRefView(), idx);
		SearchUtil.appendLIKE(qs, KETSGDocument.class, KETSGDocument.DOC_NO, dto.getDocNo(), idx);
		SearchUtil.appendLIKE(qs, KETSGDocument.class, KETSGDocument.DOC_NAME, dto.getDocName(), idx);
		SearchUtil.appendLIKE(qs, KETSGDocument.class, KETSGDocument.DESCRIPTION, dto.getDescription(), idx);

		String sortName = dto.getSortName();

		if (!StringUtil.isTrimToEmpty(sortName)) {

			boolean sortOrder = false;

			if (sortName.startsWith("-")) {
				sortName = sortName.substring(1);
				sortOrder = true;
			}

			if (sortName.indexOf("moduleDisplay") >= 0)
				sortName = KETSGDocument.MODULE_CODE;
			if (sortName.indexOf("modifyDate") >= 0)
				sortName = KETSGDocument.MODIFY_TIMESTAMP;

			if (sortName.indexOf("creatorName") >= 0) {

				int idx2 = qs.addClassList(WTUser.class, true);

				ClassAttribute ca0 = new ClassAttribute(KETSGDocument.class, KETSGDocument.CREATOR + "." + WTAttributeNameIfc.REF_OBJECT_ID);
				ClassAttribute ca1 = new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME);
				SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
				sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
				sc0.setOuterJoin(0);

				if (qs.getConditionCount() > 0)
					qs.appendAnd();
				qs.appendWhere(sc0, new int[] { idx, idx2 });

				sortName = WTUser.FULL_NAME;

				SearchUtil.setOrderBy(qs, WTUser.class, idx2, sortName, sortOrder);
			} else {
				SearchUtil.setOrderBy(qs, KETSGDocument.class, idx, sortName, sortOrder);
			}

		} else {
			SearchUtil.setOrderBy(qs, KETSGDocument.class, idx, KETSGDocument.MODIFY_TIMESTAMP, true);
		}

		return qs;
	}

	/**
	 * <pre>
	 * @description 시스템 사용설명서 저장
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:22:45
	 * @method saveSGDocument
	 * @param dto
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@Override
	public Map<String, Object> saveSGDocument(KETSGDocumentDTO dto, Map<String, Object> reqMap) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		Transaction trx = new Transaction();
		try {

			trx.start();

			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef);
			String folderPath = "/Default/SGDocument";
			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);

			KETSGDocument sgDoc = null;

			String oid = dto.getOid();

			if (StringUtil.checkString(oid)) {
				sgDoc = (KETSGDocument) CommonUtil.getObject(oid);
			} else {
				sgDoc = KETSGDocument.newKETSGDocument();
				LifeCycleHelper.setLifeCycle(sgDoc, LCtemplate);
				FolderHelper.assignLocation((FolderEntry) sgDoc, folder);

				String docNo = KETDocumentUtil.manager.getGenrateSGDocNo();
				sgDoc.setDocNo(docNo);
				sgDoc.setLastest(true);
			}

			ObjectUtil.manager.convertMapToObject(reqMap, sgDoc);

			sgDoc = (KETSGDocument) PersistenceHelper.manager.save(sgDoc);

			KETContentHelper.service.updateContent(sgDoc, dto);

			sgDoc = (KETSGDocument) PersistenceHelper.manager.refresh(sgDoc);

			sgDoc.setBranchId(CommonUtil.getOIDLongValue(sgDoc));
			sgDoc = (KETSGDocument) PersistenceHelper.manager.save(sgDoc);
			resMap.put("oid", CommonUtil.getOIDString(sgDoc));

			trx.commit();
			trx = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (trx != null) {
				trx.rollback();
				trx = null;
			}
		}

		return resMap;
	}

	/**
	 * <pre>
	 * @description 시스템 사용설명서 개정
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:23:12
	 * @method reviseSGDocument
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@Override
	public Map<String, Object> reviseSGDocument(Map<String, Object> reqMap) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		Transaction trx = new Transaction();
		try {

			trx.start();

			String oid = (String) reqMap.get("oid");

			KETSGDocument sgDoc = (KETSGDocument) CommonUtil.getObject(oid);
			long branchId = sgDoc.getBranchId();
			if (branchId == 0)
				branchId = CommonUtil.getOIDLongValue(oid);
			int version = sgDoc.getVersion();
			version++;

			sgDoc.setLastest(false);
			sgDoc = (KETSGDocument) PersistenceHelper.manager.save(sgDoc);

			// 개정 처리
			KETSGDocument newSGDoc = (KETSGDocument) HistoryHelper.duplicate(sgDoc);
			newSGDoc.setVersion(version);
			newSGDoc.setLastest(true);
			newSGDoc.setBranchId(branchId);

			newSGDoc = (KETSGDocument) PersistenceHelper.manager.save(newSGDoc);

			// 첨부파일 복사
			ContentHelper.service.copyContent(sgDoc, newSGDoc);

			resMap.put("oid", CommonUtil.getOIDString(newSGDoc));

			trx.commit();
			trx = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (trx != null) {
				trx.rollback();
				trx = null;
			}
		}

		return resMap;
	}

	/**
	 * <pre>
	 * @description 시스템 사용설명서 삭제
	 * @author dhkim
	 * @date 2018. 10. 1. 오전 10:23:23
	 * @method deleteSGDocument
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@Override
	public Map<String, Object> deleteSGDocument(Map<String, Object> reqMap) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		Transaction trx = new Transaction();
		try {

			trx.start();

			String oid = (String) reqMap.get("oid");

			KETSGDocument sgDoc = (KETSGDocument) CommonUtil.getObject(oid);

			List<KETSGDocumentDTO> dtoList = KETDocumentUtil.manager.getBranchSGDocumnent(sgDoc);

			String lastestOid = null;

			for (KETSGDocumentDTO dto : dtoList) {
				if (dtoList.size() > 1 && oid.equals(dto.getOid()))
					continue;
				lastestOid = dto.getOid();
			}

			KETSGDocument sgDoclast = (KETSGDocument) CommonUtil.getObject(lastestOid);

			sgDoclast.setLastest(true);
			sgDoclast = (KETSGDocument) PersistenceHelper.manager.save(sgDoclast);
			resMap.put("oid", CommonUtil.getOIDString(sgDoclast));
			sgDoc = (KETSGDocument)PersistenceHelper.manager.refresh(sgDoc); 
			
			PersistenceHelper.manager.delete(sgDoc);

			trx.commit();
			trx = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (trx != null) {
				trx.rollback();
				trx = null;
			}
		}

		return resMap;
	}
}
