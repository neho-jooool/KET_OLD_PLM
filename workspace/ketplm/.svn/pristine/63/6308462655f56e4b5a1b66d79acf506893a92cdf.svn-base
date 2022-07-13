package e3ps.ecm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.WCUtil;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldECRIssueLink;
import e3ps.ecm.entity.KETMoldECRIssueLinkVO;
import e3ps.ecm.entity.KETMoldECRPartLink;
import e3ps.project.ProjectIssue;
import ext.ket.shared.log.Kogger;

public class MoldEcrBeans extends StandardManager implements Serializable {

    private static final long serialVersionUID = 6744551693766378190L;

    /**
     * 
     * 함수명 : createMoldEcrInfo 설명 : 금형 ECR을 저장한다. 결재상태 및 저장 폴더를 세팅한다. 첨부파일 VO List를 저장한다. 관련부품 VO List를 저장한다. 관련 ISSUE VO List를 저장한다. 금형
     * ECR을 신규 저장한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @return KETMoldChangeRequestVO : 금형ECR VO
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public KETMoldChangeRequestVO createMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	//Attribute Setting
	try {
	    //1. 결재적용
	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecr");
	    LifeCycleHelper.setLifeCycle(ketMoldChangeRequest, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    //1.2 Object Save
	    //Folder Setting
	    String folderPath = "";
	    if (KETObjectUtil.getCurrentUserGroup().equals("자동차사업부") || KETObjectUtil.getCurrentUserGroup().equals("자동차사업부_구매")) {
		//Car Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
	    }
	    else if (KETObjectUtil.getCurrentUserGroup().equals("KETS")) {
		//KETS Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
	    }
	    else if (KETObjectUtil.getCurrentUserGroup().equals("전자사업부")) {
		//Electronic Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
	    }

	    folderPath += DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketMoldChangeRequest, folder);


	    // ECR 전체 상태
	    ketMoldChangeRequest.setEcrStateState(State.toState("INWORK"));


	    ketMoldChangeRequest = (KETMoldChangeRequest) PersistenceHelper.manager.save(ketMoldChangeRequest);
	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);

	    saveFiles(ketMoldChangeRequestVO);//첨부파일 저장
	    saveRelPart(ketMoldChangeRequestVO);//관련부품 저장
	    saveRelIssue(ketMoldChangeRequestVO);//관련Issue 저장
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	}

	return ketMoldChangeRequestVO;
    }

    /**
     * 
     * 함수명 : saveRelPart 설명 : 금형 ECR 관련부품 목록을 삭제 및 저장한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings("rawtypes")
    private void saveRelPart(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	try {
	    KETMoldECRPartLink ketMoldECRPartLink = null;
	    //관련부품삭제
	    String deleteList = ketMoldChangeRequestVO.getDeleteRelPartList();
	    ArrayList arrDeleteList = KETStringUtil.getToken(deleteList, "*");
	    int size = arrDeleteList.size();
	    for (int i = 0; i < size; i++) {
		String oid = (String) arrDeleteList.get(i);
		ketMoldECRPartLink = (KETMoldECRPartLink) CommonUtil.getObject(oid);
		//1.2 Object Delete
		if (ketMoldECRPartLink != null) {
		    PersistenceHelper.manager.delete(ketMoldECRPartLink);
		}
	    }

	    //관련부품 추가
	    KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	    ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeRequestVO.getKetMoldECOPartLinkVOList();//금형ECO관련부품 List
	    size = ketMoldECOPartLinkVOList.size();
	    KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
	    WTPart part = null;
	    for (int i = 0; i < size; i++) {
		ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO) ketMoldECOPartLinkVOList.get(i);
		part = (WTPart) CommonUtil.getObject(ketMoldECOPartLinkVO.getRelPartOid());
		if ("".equals(ketMoldECOPartLinkVO.getRelPartLinkOid())) {//신규
		    ketMoldECRPartLink = KETMoldECRPartLink.newKETMoldECRPartLink(part, ketMoldChangeRequest);
		}
		else {//변경
		    ketMoldECRPartLink = (KETMoldECRPartLink) CommonUtil.getObject(ketMoldECOPartLinkVO.getRelPartLinkOid());
		    ketMoldECRPartLink.setPart(part);
		}
		ketMoldECRPartLink.setChangeReqComment(ketMoldECOPartLinkVO.getChangeReqComment());
		PersistenceHelper.manager.save(ketMoldECRPartLink);
	    }
	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    /**
     * 
     * 함수명 : saveRelIssue 설명 : 금형 ECR 관련 Issue 목록을 삭제 및 저장한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings("rawtypes")
    private void saveRelIssue(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	try {
	    KETMoldECRIssueLink ketMoldECRIssueLink = null;
	    //관련Issue삭제
	    String deleteList = ketMoldChangeRequestVO.getDeleteRelIssueList();
	    ArrayList arrDeleteList = KETStringUtil.getToken(deleteList, "*");
	    int size = arrDeleteList.size();
	    for (int i = 0; i < size; i++) {
		String oid = (String) arrDeleteList.get(i);
		ketMoldECRIssueLink = (KETMoldECRIssueLink) CommonUtil.getObject(oid);
		//1.2 Object Delete
		if (ketMoldECRIssueLink != null) {
		    PersistenceHelper.manager.delete(ketMoldECRIssueLink);
		}
	    }

	    //관련Issue 추가
	    KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	    ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = ketMoldChangeRequestVO.getKetMoldECRIssueLinkVOList();//금형ECR 관련Issue List
	    size = ketMoldECRIssueLinkVOList.size();
	    KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;
	    ProjectIssue projectIssue = null;
	    for (int i = 0; i < size; i++) {
		ketMoldECRIssueLinkVO = (KETMoldECRIssueLinkVO) ketMoldECRIssueLinkVOList.get(i);
		projectIssue = (ProjectIssue) CommonUtil.getObject(ketMoldECRIssueLinkVO.getRelIssueOid());
		if ("".equals(ketMoldECRIssueLinkVO.getRelIssueLinkOid())) {//신규
		    ketMoldECRIssueLink = KETMoldECRIssueLink.newKETMoldECRIssueLink(projectIssue, ketMoldChangeRequest);
		}
		else {//변경
		    ketMoldECRIssueLink = (KETMoldECRIssueLink) CommonUtil.getObject(ketMoldECRIssueLinkVO.getRelIssueLinkOid());
		    if (ketMoldECRIssueLink == null) {
			ketMoldECRIssueLink = KETMoldECRIssueLink.newKETMoldECRIssueLink(projectIssue, ketMoldChangeRequest);
		    }
		    else {
			ketMoldECRIssueLink.setIssue(projectIssue);
		    }
		}
		PersistenceHelper.manager.save(ketMoldECRIssueLink);
	    }
	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    /**
     * 
     * 함수명 : saveFiles 설명 : 금형 ECR 관련 첨부파일 목록을 삭제 및 추가 저장한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void saveFiles(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	try {
	    KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	    //파일 목록 삭제
	    String deleteFileList = ketMoldChangeRequestVO.getDeleteFileList();
	    if (!"".equals(deleteFileList)) {
		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(deleteFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}
		int delFileCnt = delFileList.size();
		QueryResult rs = ContentHelper.service.getContentsByRole(ketMoldChangeRequest, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String ItemString = allObj.toString();
		    for (int i = 0; i < delFileCnt; i++) {
			if (delFileList.get(i).equals(ItemString)) {
			    ketMoldChangeRequest = (KETMoldChangeRequest) E3PSContentHelper.service.delete(ketMoldChangeRequest, allContentItem);
			}
		    }
		}
	    }

	    //첨부파일 추가
	    Vector files = ketMoldChangeRequestVO.getFiles();
	    if (files != null) {
		int size = files.size();
		for (int i = 0; i < size; i++) {
		    ketMoldChangeRequest = (KETMoldChangeRequest) E3PSContentHelper.service.attach((ContentHolder) ketMoldChangeRequestVO.getKetMoldChangeRequest(), (WBFile) files.get(i), "",
			    ContentRoleType.SECONDARY);
		}
	    }
	} catch (Exception e) {
	    throw new WTException(e);
	}
    }

    /**
     * 
     * 함수명 : updateMoldEcrInfo 설명 : 금형 ECR을 변경 저장한다. 첨부파일 VO List를 저장한다. 관련부품 VO List를 저장한다. 관련 ISSUE VO List를 저장한다. 금형 ECO를 변경 저장한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @return KETMoldChangeRequestVO : 금형ECR VO
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    public KETMoldChangeRequestVO updateMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	try {
	    //1. Object Create
	    //			ketMoldChangeRequest2 = KETMoldChangeRequest.newKETMoldChangeRequest();
	    //1.1 Object Setting
	    //			ketMoldChangeRequest.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);
	    //1.2 Object Save
	    KETMoldChangeRequest ketMoldChangeRequest2 = null;
	    ketMoldChangeRequest2 = (KETMoldChangeRequest) PersistenceHelper.manager.modify(ketMoldChangeRequest);
	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest2);

	    saveFiles(ketMoldChangeRequestVO);//첨부파일 저장
	    saveRelPart(ketMoldChangeRequestVO);//관련부품 저장
	    saveRelIssue(ketMoldChangeRequestVO);//관련Issue 저장
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	}
	return ketMoldChangeRequestVO;
    }

    /**
     * @param ketMoldChangeRequestVO
     * @return KETMoldChangeRequestVO
     * @exception wt.util.WTException
     **/

    /**
     * 
     * 함수명 : deleteMoldEcrInfo 설명 : 금형ECR 관련 자료를 삭제한다. 금형ECR 관련 첨부파일을 조회하여 모두 삭제한다. 금형ECR 관련 부품을 조회하여 모두 삭제한다. 금형ECR 관련 ISSUE를 조회하여 모두 삭제한다.
     * 금형ECR 자료를 삭제한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형ECR VO
     * @return 0
     * @throws WTException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public int deleteMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	KETMoldChangeRequest ketMoldChangeRequest2 = null;
	KETMoldECRPartLink partLink = null;
	KETMoldECRIssueLink issueLink = null;
	try {
	    ketMoldChangeRequest2 = KETMoldChangeRequest.newKETMoldChangeRequest();

	    //관련 파일 목록을 삭제한다.
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketMoldChangeRequest, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem allContentItem = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketMoldChangeRequest, allContentItem);
	    }

	    //관련부품 목록을 삭제한다.
	    rs = PersistenceHelper.manager.navigate(ketMoldChangeRequest, "part", KETMoldECRPartLink.class, false);
	    while (rs.hasMoreElements()) {
		partLink = (KETMoldECRPartLink) rs.nextElement();
		PersistenceHelper.manager.delete(partLink);
	    }

	    //관련이슈 목록을 삭제한다.
	    rs = PersistenceHelper.manager.navigate(ketMoldChangeRequest, "issue", KETMoldECRIssueLink.class, false);
	    while (rs.hasMoreElements()) {
		issueLink = (KETMoldECRIssueLink) rs.nextElement();
		PersistenceHelper.manager.delete(issueLink);
	    }

	    //금형 ECR을 삭제한다.
	    ketMoldChangeRequest2 = (KETMoldChangeRequest) PersistenceHelper.manager.delete(ketMoldChangeRequest);
	    ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest2);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	}
	return 0;
    }

}
