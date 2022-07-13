package ext.ket.edm.stamping.service.internal;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocument;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.folder.FolderUtil;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.WCUtil;
import ext.ket.edm.entity.KETDrawingDistDept;
import ext.ket.edm.entity.KETDrawingDistDeptLink;
import ext.ket.edm.entity.KETDrawingDistDocLink;
import ext.ket.edm.entity.KETDrawingDistEpmLink;
import ext.ket.edm.entity.KETDrawingDistFileType;
import ext.ket.edm.entity.KETDrawingDistFileTypeLink;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.KETStampDistLink;
import ext.ket.edm.entity.KETStampEpmLink;
import ext.ket.edm.entity.KETStamping;
import ext.ket.edm.entity.KETStampingItem;
import ext.ket.edm.entity.KETStampingItemLink;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.util.DrawingDistFileTypeEnum;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardTempService extends StandardManager implements TempService {
    private static final long serialVersionUID = 1L;

    public static StandardTempService newStandardTempService() throws WTException {
	StandardTempService instance = new StandardTempService();
	instance.initialize();
	return instance;
    }

    /*
     * KETDrawingDistRequest 가져오기
     */
    @Override
    public KETDrawingDistRequest getDistReq(final String distDocNo) throws Exception {
	return QueryFactory.getInstance().getQuerySpec().queryForFirstByOneClass(KETDrawingDistRequest.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendWhere(new SearchCondition(class1, KETDrawingDistRequest.NUMBER, SearchCondition.EQUAL, distDocNo), new int[] { index });
	    }

	});
    }

    @Override
    public void deleteDistRequest(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    return;

	// delete 배포처
	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	List<KETDrawingDistDept> deptList = query.queryForListAByRoleB(KETDrawingDistDept.class, KETDrawingDistDeptLink.class, KETDrawingDistRequest.class, drawingDistReq);
	ObjectUtil.deletePersistableList(deptList);

	// delete 배포포멧
	List<KETDrawingDistFileType> fileTypeList = query.queryForListAByRoleB(KETDrawingDistFileType.class, KETDrawingDistFileTypeLink.class, KETDrawingDistRequest.class, drawingDistReq);
	ObjectUtil.deletePersistableList(fileTypeList);

	// delete doc list
	List<KETDrawingDistDocLink> docList = query.queryForListLinkByRoleB(KETDrawingDistDocLink.class, KETDrawingDistRequest.class, drawingDistReq);
	ObjectUtil.deletePersistableList(docList);

	// delete stamping
	KETStamping stamping = getStamping(drawingDistReq);
	if (stamping != null) {

	    List<KETStampingItemLink> stampingItemLinkList = query.queryForListLinkByRoleB(KETStampingItemLink.class, KETStamping.class, stamping);
	    ObjectUtil.deletePersistableList(stampingItemLinkList); // delete item link

	    ObjectUtil.deletePersistableWithAdmin(stamping); // delete
	}

	// delte epm list
	List<KETDrawingDistEpmLink> epmDistLinkList = query.queryForListLinkByRoleB(KETDrawingDistEpmLink.class, KETDrawingDistRequest.class, drawingDistReq);

	for (KETDrawingDistEpmLink epmLink : epmDistLinkList) {

	    List<KETStampEpmLink> epmItemLinkList = query.queryForListLinkByRoleB(KETStampEpmLink.class, KETDrawingDistEpmLink.class, epmLink);
	    List<KETStampingItem> stampingItemList = query.queryForListAByRoleB(KETStampingItem.class, KETStampEpmLink.class, KETDrawingDistEpmLink.class, epmLink);

	    ObjectUtil.deletePersistableList(epmItemLinkList); // delete
	    ObjectUtil.deletePersistableList(stampingItemList); // delete item

	    ObjectUtil.deletePersistableWithAdmin(epmLink); // delete epmlink
	}

	// delete eo list

	PersistenceHelper.manager.delete(drawingDistReq);

    }

    /*
     * KETDrawingDistRequest 등록
     */
    public KETDrawingDistRequest createDistReq(KETDrawingDistReqDTO paramObject) throws Exception {

	Kogger.debug(getClass(), paramObject);

	KETDrawingDistRequest drawingDistReq = KETDrawingDistRequest.newKETDrawingDistRequest();

	drawingDistReq.setNumber(paramObject.getNumber());
	drawingDistReq.setName(paramObject.getTitle());
	drawingDistReq.setTitle(paramObject.getTitle());
	// drawingDistReq.setDescription(paramObject.getDescription());

	drawingDistReq.setDistType(paramObject.getDistType());
	drawingDistReq.setDistReason(paramObject.getDistReason());
	// drawingDistReq.setDownloadExpireDate(null);
	drawingDistReq.setDistSubcontractor(paramObject.getDistSubcontractor());
	drawingDistReq.setWriteDeptEnName(paramObject.getWriteDeptEnName());
	drawingDistReq.setWriteDeptKrName(paramObject.getWriteDeptKrName());
	drawingDistReq.setWriteDeptCode(paramObject.getWriteDeptCode());

	Folder folder = FolderUtil.getFolder("/Default/DrawingDist");
	FolderHelper.assignLocation((FolderEntry) drawingDistReq, folder);

	LifeCycleTemplate lcTemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", WCUtil.getWTContainerRef());
	LifeCycleHelper.setLifeCycle(drawingDistReq, lcTemplate);

	drawingDistReq = (KETDrawingDistRequest) PersistenceHelper.manager.save(drawingDistReq);

	// 배포 포멧 : PDF,DWG
	String drawingDistFileTypeArray = paramObject.getDrawingDistFileTypeArray();
	if (!StringUtils.isEmpty(drawingDistFileTypeArray)) {
	    String[] drawingDistFileTypeArrayItems = drawingDistFileTypeArray.split(",");
	    for (String fileTypeItem : drawingDistFileTypeArrayItems) {

		KETDrawingDistFileType fileTypes = KETDrawingDistFileType.newKETDrawingDistFileType();

		DrawingDistFileTypeEnum drawingDistFileType = DrawingDistFileTypeEnum.valueOf(fileTypeItem);

		fileTypes.setDistFileType(drawingDistFileType.toString());
		fileTypes.setDistReq(drawingDistReq);

		PersistenceHelper.manager.save(fileTypes);
	    }
	}

	// epm
	String drawingDisEpmArray = paramObject.getDrawingDisEpmArray();
	String sheetTypeArray = paramObject.getSheetTypeArray();
	if (!StringUtils.isEmpty(drawingDisEpmArray)) {
	    String[] drawingDisEpmArrayItems = drawingDisEpmArray.split(",");
	    String[] sheetTypeArrayItems = sheetTypeArray.split(",");

	    for (int k = 0; k < drawingDisEpmArrayItems.length; k++) {
		String epmItem = drawingDisEpmArrayItems[k];
		String sheetItem = sheetTypeArrayItems[k];
		EPMDocument distEpm = (EPMDocument) CommonUtil.getObject(epmItem);
		KETDrawingDistEpmLink distEpmLink = KETDrawingDistEpmLink.newKETDrawingDistEpmLink(distEpm, drawingDistReq);
		distEpmLink.setSheetType(sheetItem);

		PersistenceHelper.manager.save(distEpmLink);
	    }
	}

	// doc
	String drawingDistDocArray = paramObject.getDrawingDistDocArray();
	if (!StringUtils.isEmpty(drawingDistDocArray)) {
	    String[] drawingDistDocArrayItems = drawingDistDocArray.split(",");
	    for (String docItem : drawingDistDocArrayItems) {

		WTDocument distDoc = (WTDocument) CommonUtil.getObject(docItem);
		KETDrawingDistDocLink distDocLink = KETDrawingDistDocLink.newKETDrawingDistDocLink(distDoc, drawingDistReq);

		PersistenceHelper.manager.save(distDocLink);
	    }
	}

	LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) drawingDistReq, State.toState("APPROVED"));

	return drawingDistReq;
    }

    private KETStamping getStamping(KETDrawingDistRequest drawingDistReq) throws Exception {

	if (drawingDistReq == null)
	    throw new Exception("# KETDrawingDistRequest is null !! : StandardStampingService  getStamping() ");

	SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	KETStamping stamping = query.queryForFirstAByRoleB(KETStamping.class, KETStampDistLink.class, KETDrawingDistRequest.class, drawingDistReq);

	Kogger.debug(getClass(), "stamping:" + stamping);

	return stamping;
    }

    // private void ObjectUtil.deletePersistableList(List<? extends Persistable> pers) throws WTException {
    //
    // WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
    // SessionHelper.manager.setAdministrator();
    //
    // try {
    //
    // if (pers == null)
    // return;
    //
    // for (Persistable per : pers) {
    // PersistenceHelper.manager.delete(per);
    // }
    //
    // } catch (WTException e) {
    // log.error(e);
    // throw e;
    // } finally {
    // SessionHelper.manager.setPrincipal(orgPrincipal.getName());
    // }
    // }
    //
    // private void deletePersisable(Persistable per) throws WTException {
    //
    // WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
    // SessionHelper.manager.setAdministrator();
    //
    // try {
    //
    // if (per == null)
    // return;
    //
    // PersistenceHelper.manager.delete(per);
    //
    // } catch (WTException e) {
    // log.error(e);
    // throw e;
    // } finally {
    // SessionHelper.manager.setPrincipal(orgPrincipal.getName());
    // }
    // }

}