package ext.ket.edm.entity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.shared.dto.BaseDTO;

/**
 * 
 * @클래스명 : KETDrawingDistReqDTO
 * @작성자 : user
 * @작성일 : 2016. 3. 2.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class KETDrawingDistReqDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    public KETDrawingDistReqDTO() {
    }

    public KETDrawingDistReqDTO(KETDrawingDistRequest ketDrawingDistRequest) {
	this.setOid(CommonUtil.getOIDString(ketDrawingDistRequest));
	this.number = ketDrawingDistRequest.getNumber();
	this.name = ketDrawingDistRequest.getName();
	this.title = ketDrawingDistRequest.getTitle();
	this.description = ketDrawingDistRequest.getDescription();

	this.distType = ketDrawingDistRequest.getDistType();
	this.distReason = ketDrawingDistRequest.getDistReason();
	this.downloadExpireDate = DateUtil.getDateString(ketDrawingDistRequest.getDownloadExpireDate(), "date");
	// this.approvedDate;
	// this.approvalSubmitDate;
	// 협력업체 배포처
	this.distSubcontractor = ketDrawingDistRequest.getDistSubcontractor();
	// 작성부서 영문
	this.writeDeptEnName = ketDrawingDistRequest.getWriteDeptEnName();
	this.writeDeptKrName = ketDrawingDistRequest.getWriteDeptKrName();
	this.writeDeptCode = ketDrawingDistRequest.getWriteDeptCode();
	this.createStamp = DateUtil.getDateString(ketDrawingDistRequest.getPersistInfo().getCreateStamp(), "date");
	this.creator = ketDrawingDistRequest.getCreator().getObjectId().toString();
	this.state = ketDrawingDistRequest.getState().toString();

	this.distDeptName = ketDrawingDistRequest.getDistDeptName();
	this.etcYn = ketDrawingDistRequest.getEtcYn();
	// 배포 도면
	// this.drawingDisEpmArray;

	// 배포 문서
	// this.drawingDistDocArray;

	// EO 연결
	// this.drawingDistEoMoldArray;
	// this.drawingDistEoProdArray;

	// 배포 부서(사내 배포처)
	// this.drawingDistDeptArray;

	// 배포 포멧
	// this.drawingDistFileTypeArray;
    }

    public List<HashMap<String, String>> drawingDistEoInfoList = new ArrayList<HashMap<String, String>>();

    private List resultMapList;

    public String DownFlag;
    public String approver;
    public String approveStamp;

    public String sortType;
    public String createDeptName;
    public String drawingDistRequestOid;
    public String downloadReason;
    public String fileName;

    public String drawingNo;
    public String drawingName;
    public String documentNo;
    public String documentName;

    public int rnum;
    public String distReqNumber;

    public String distDeptName;
    public String state;
    public String createStamp;
    public String creator;

    // from WTDocument
    public String number;
    public String name;
    public String title;
    public String description;

    public String distType;
    public String distReason;
    public String downloadExpireDate;
    // public String approvedDate;
    // public String approvalSubmitDate;
    // 협력업체 배포처
    public String distSubcontractor;
    // 작성부서 영문
    public String writeDeptEnName;
    public String writeDeptKrName;
    public String writeDeptCode;

    // 배포 도면
    public String drawingDisEpmArray;
    public String sheetTypeArray;

    // 배포 문서
    public String drawingDistDocArray;

    public String ecoNoStr;

    // 배포 부서(사내 배포처)
    public String drawingDistDeptArray = null;

    public String[] drawingDistDeptCodeArray;

    // 배포 부서(사내 배포처)
    public String drawingDistDeptNameArray;

    // 배포 포멧
    public String drawingDistFileTypeArray;

    public String drawingDistEoArrayOid;

    public String drawingDistEoArray;

    public Boolean createUserFlag;

    public String createStartDate;

    public String createEndDate;

    public String searchCheck;

    public String etcYn;

    public String creatorId;

    public String partOid;

    public String direct2Hompage;

    public String partNo;

    public String ecoNo;

    public String stampstatus;

    public String hpstatus;

    public String divisionflag;

    public String igscontentoid;
    public String iFileIgsOrg;

    public String stepcontentoid;
    public String iFileStepOrg;

    public String plane3dcontentoid;
    public String iFile3DOrg;

    public String planecontentoid;
    public String iFile2DOrg;

    public String getIgscontentoid() {
	return igscontentoid;
    }

    public void setIgscontentoid(String igscontentoid) {
	this.igscontentoid = igscontentoid;
    }

    public String getiFileIgsOrg() {
	return iFileIgsOrg;
    }

    public void setiFileIgsOrg(String iFileIgsOrg) {
	this.iFileIgsOrg = iFileIgsOrg;
    }

    public String getStepcontentoid() {
	return stepcontentoid;
    }

    public void setStepcontentoid(String stepcontentoid) {
	this.stepcontentoid = stepcontentoid;
    }

    public String getiFileStepOrg() {
	return iFileStepOrg;
    }

    public void setiFileStepOrg(String iFileStepOrg) {
	this.iFileStepOrg = iFileStepOrg;
    }

    public String getPlane3dcontentoid() {
	return plane3dcontentoid;
    }

    public void setPlane3dcontentoid(String plane3dcontentoid) {
	this.plane3dcontentoid = plane3dcontentoid;
    }

    public String getiFile3DOrg() {
	return iFile3DOrg;
    }

    public void setiFile3DOrg(String iFile3DOrg) {
	this.iFile3DOrg = iFile3DOrg;
    }

    public String getPlanecontentoid() {
	return planecontentoid;
    }

    public void setPlanecontentoid(String planecontentoid) {
	this.planecontentoid = planecontentoid;
    }

    public String getiFile2DOrg() {
	return iFile2DOrg;
    }

    public void setiFile2DOrg(String iFile2DOrg) {
	this.iFile2DOrg = iFile2DOrg;
    }

    public String getImgcontentoid() {
	return imgcontentoid;
    }

    public void setImgcontentoid(String imgcontentoid) {
	this.imgcontentoid = imgcontentoid;
    }

    public String getiFileImgOrg() {
	return iFileImgOrg;
    }

    public void setiFileImgOrg(String iFileImgOrg) {
	this.iFileImgOrg = iFileImgOrg;
    }

    public String imgcontentoid;
    public String iFileImgOrg;

    // public List<String> distFileTypeList;
    // public List<KETDrawingDistDeptDTO> distDeptList;

    public String getDistType() {
	return distType;
    }

    public String getCreatorId() {
	return creatorId;
    }

    public void setCreatorId(String creatorId) {
	this.creatorId = creatorId;
    }

    public String getEtcYn() {
	return etcYn;
    }

    public void setEtcYn(String etcYn) {
	this.etcYn = etcYn;
    }

    public String getCreateStartDate() {
	return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
	this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
	return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
	this.createEndDate = createEndDate;
    }

    public String getSearchCheck() {
	return searchCheck;
    }

    public void setSearchCheck(String searchCheck) {
	this.searchCheck = searchCheck;
    }

    public String getSortType() {
	return sortType;
    }

    public void setSortType(String sortType) {
	this.sortType = sortType;
    }

    public List<HashMap<String, String>> getDrawingDistEoInfoList() {
	return drawingDistEoInfoList;
    }

    public void setDrawingDistEoInfoList(List<HashMap<String, String>> drawingDistEoInfoList) {
	this.drawingDistEoInfoList = drawingDistEoInfoList;
    }

    public String getDownFlag() {
	return DownFlag;
    }

    public void setDownFlag(String downFlag) {
	DownFlag = downFlag;
    }

    public String getApprover() {
	return approver;
    }

    public void setApprover(String approver) {
	this.approver = approver;
    }

    public String getApproveStamp() {
	return approveStamp;
    }

    public void setApproveStamp(String approveStamp) {
	this.approveStamp = approveStamp;
    }

    public String getCreateDeptName() {
	return createDeptName;
    }

    public void setCreateDeptName(String createDeptName) {
	this.createDeptName = createDeptName;
    }

    public Boolean getCreateUserFlag() {
	return createUserFlag;
    }

    public void setCreateUserFlag(Boolean createUserFlag) {
	this.createUserFlag = createUserFlag;
    }

    public String getDrawingDistEoArrayOid() {
	return drawingDistEoArrayOid;
    }

    public void setDrawingDistEoArrayOid(String drawingDistEoArrayOid) {
	this.drawingDistEoArrayOid = drawingDistEoArrayOid;
    }

    public String getDrawingDistEoArray() {
	return drawingDistEoArray;
    }

    public void setDrawingDistEoArray(String drawingDistEoArray) {
	this.drawingDistEoArray = drawingDistEoArray;
    }

    public String getDrawingDistRequestOid() {
	return drawingDistRequestOid;
    }

    public void setDrawingDistRequestOid(String drawingDistRequestOid) {
	this.drawingDistRequestOid = drawingDistRequestOid;
    }

    public String getDownloadReason() {
	return downloadReason;
    }

    public void setDownloadReason(String downloadReason) {
	this.downloadReason = downloadReason;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String[] getDrawingDistDeptCodeArray() {
	return drawingDistDeptCodeArray;
    }

    public void setDrawingDistDeptCodeArray(String[] drawingDistDeptCodeArray) {
	this.drawingDistDeptCodeArray = drawingDistDeptCodeArray;
    }

    public String getDrawingNo() {
	return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
	this.drawingNo = drawingNo;
    }

    public String getDrawingName() {
	return drawingName;
    }

    public void setDrawingName(String drawingName) {
	this.drawingName = drawingName;
    }

    public String getDocumentNo() {
	return documentNo;
    }

    public void setDocumentNo(String documentNo) {
	this.documentNo = documentNo;
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

    public int getRnum() {
	return rnum;
    }

    public void setRnum(int rnum) {
	this.rnum = rnum;
    }

    public String getDistReqNumber() {
	return distReqNumber;
    }

    public void setDistReqNumber(String distReqNumber) {
	this.distReqNumber = distReqNumber;
    }

    public String getDistDeptName() {
	return distDeptName;
    }

    public void setDistDeptName(String distDeptName) {
	this.distDeptName = distDeptName;
    }

    public String getDrawingDistDeptNameArray() {
	return drawingDistDeptNameArray;
    }

    public void setDrawingDistDeptNameArray(String drawingDistDeptNameArray) {
	this.drawingDistDeptNameArray = drawingDistDeptNameArray;
    }

    public String getEcoNoStr() {
	return ecoNoStr;
    }

    public void setEcoNoStr(String ecoNoStr) {
	this.ecoNoStr = ecoNoStr;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setDistType(String distType) {
	this.distType = distType;
    }

    public String getDistReason() {
	return distReason;
    }

    public void setDistReason(String distReason) {
	this.distReason = distReason;
    }

    public String getDownloadExpireDate() {
	return downloadExpireDate;
    }

    public void setDownloadExpireDate(String downloadExpireDate) {
	this.downloadExpireDate = downloadExpireDate;
    }

    public String getDistSubcontractor() {
	return distSubcontractor;
    }

    public void setDistSubcontractor(String distSubcontractor) {
	this.distSubcontractor = distSubcontractor;
    }

    public String getWriteDeptEnName() {
	return writeDeptEnName;
    }

    public void setWriteDeptEnName(String writeDeptEnName) {
	this.writeDeptEnName = writeDeptEnName;
    }

    public String getWriteDeptKrName() {
	return writeDeptKrName;
    }

    public void setWriteDeptKrName(String writeDeptKrName) {
	this.writeDeptKrName = writeDeptKrName;
    }

    public String getWriteDeptCode() {
	return writeDeptCode;
    }

    public void setWriteDeptCode(String writeDeptCode) {
	this.writeDeptCode = writeDeptCode;
    }

    public String getDrawingDisEpmArray() {
	return drawingDisEpmArray;
    }

    public void setDrawingDisEpmArray(String drawingDisEpmArray) {
	this.drawingDisEpmArray = drawingDisEpmArray;
    }

    public String getDrawingDistDocArray() {
	return drawingDistDocArray;
    }

    public void setDrawingDistDocArray(String drawingDistDocArray) {
	this.drawingDistDocArray = drawingDistDocArray;
    }

    public String getDrawingDistDeptArray() {
	return drawingDistDeptArray;
    }

    public void setDrawingDistDeptArray(String drawingDistDeptArray) {
	this.drawingDistDeptArray = drawingDistDeptArray;
    }

    public String getDrawingDistFileTypeArray() {
	return drawingDistFileTypeArray;
    }

    public void setDrawingDistFileTypeArray(String drawingDistFileTypeArray) {
	this.drawingDistFileTypeArray = drawingDistFileTypeArray;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public String getSheetTypeArray() {
	return sheetTypeArray;
    }

    public void setSheetTypeArray(String sheetTypeArray) {
	this.sheetTypeArray = sheetTypeArray;
    }

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getDirect2Hompage() {
	return direct2Hompage;
    }

    public void setDirect2Hompage(String direct2Hompage) {
	this.direct2Hompage = direct2Hompage;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getTitleHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getTitleHtmlPostfix() {
	return "</font>";
    }

    public List getResultMapList() {
	return resultMapList;
    }

    public void setResultMapList(List resultMapList) {
	this.resultMapList = resultMapList;
    }

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getEcoNo() {
	return ecoNo;
    }

    public void setEcoNo(String ecoNo) {
	this.ecoNo = ecoNo;
    }

    public String getStampstatus() {
	return stampstatus;
    }

    public void setStampstatus(String stampstatus) {
	this.stampstatus = stampstatus;
    }

    public String getHpstatus() {
	return hpstatus;
    }

    public void setHpstatus(String hpstatus) {
	this.hpstatus = hpstatus;
    }

    public String getDivisionflag() {
	return divisionflag;
    }

    public void setDivisionflag(String divisionflag) {
	this.divisionflag = divisionflag;
    }

    public void searchCondition() throws Exception {
	if (!StringUtil.checkNull(this.distReqNumber).equals("")) {
	    this.distReqNumber = KETQueryUtil.getSqlQueryForMultiSearch("B.WTDOCUMENTNUMBER", this.distReqNumber, true);
	}

	if (!StringUtil.checkNull(this.drawingNo).equals("")) {
	    this.drawingNo = KETQueryUtil.getSqlQueryForMultiSearch("EM.DOCUMENTNUMBER", this.drawingNo, true);
	}

	if (!StringUtil.checkNull(this.drawingName).equals("")) {
	    this.drawingName = KETQueryUtil.getSqlQueryForMultiSearch("EM.NAME", this.drawingName, true);
	}

	if (!StringUtil.checkNull(this.distType).equals("")) {
	    this.distType = KETQueryUtil.getSqlQueryForMultiSearch("A.DISTTYPE", this.distType, true);
	}

	if (!StringUtil.checkNull(this.creator).equals("")) {
	    this.creator = KETQueryUtil.getSqlQueryForMultiSearch("D.NAME", this.creator, true);
	}

	if (!StringUtil.checkNull(this.title).equals("")) {
	    this.title = KETQueryUtil.getSqlQueryForMultiSearch("A.TITLE", this.title, true);
	}

	if (!StringUtil.checkNull(this.documentName).equals("")) {
	    this.documentName = KETQueryUtil.getSqlQueryForMultiSearch("KPD.TITLE", this.documentName, true);
	}

	if (!StringUtil.checkNull(this.documentNo).equals("")) {
	    this.documentNo = KETQueryUtil.getSqlQueryForMultiSearch("WDM.WTDOCUMENTNUMBER", this.documentNo, true);
	}

	if (!StringUtil.checkNull(this.distDeptName).equals("")) {
	    this.distDeptName = KETQueryUtil.getSqlQueryForMultiSearch("DEPTNAME", this.distDeptName, true);
	}

	if (!StringUtil.checkNull(this.distSubcontractor).equals("")) {
	    this.distSubcontractor = KETQueryUtil.getSqlQueryForMultiSearch("A.distSubcontractor", this.distSubcontractor, true);
	}

	if (!StringUtil.checkNull(this.drawingDistEoArray).equals("")) {
	    this.drawingDistEoArray = KETQueryUtil.getSqlQueryForMultiSearch("SUBSTR(ECO.ECOID,5)", this.drawingDistEoArray, true);
	}

	this.creatorId = CommonUtil.ketsUserListWhereStr("A.idA3D2iterationInfo");

    }
}
