package ext.ket.project.gate.entity;

import java.util.Vector;

import wt.content.FormatContentHolder;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.content.ContentInfo;
import e3ps.common.content.ContentUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;

public class RevisedAssessSheetDTO extends BaseDTO {
    private String revNo; // 'Rev',
    private String creator; // '작성자',
    private String reviseCause; // '개정사유',
    private String reviseFile; // '파일'
    private String reviseFileCount; // '파일갯수'
    private String createStamp; // '작성일'
    private String modifyStamp;
    private String pjtOid;

    public RevisedAssessSheetDTO() {
    }

    public RevisedAssessSheetDTO(AssessSheet ass) {
	this.setOid(CommonUtil.getOIDString(ass));
	try {
	    Versioned vs = (Versioned) ass;
	    // parentItem.put("version", item.getVersionIdentifier().getValue() + "." + item.getIterationIdentifier().getValue());
	    this.revNo = vs.getVersionIdentifier().getValue();
	    this.reviseCause = StringUtil.checkNull(VersionControlHelper.getNote(vs));
	    this.creator = vs.getCreatorFullName();

	    if (vs instanceof FormatContentHolder) {
		FormatContentHolder holder = (FormatContentHolder) vs;
		// ContentInfo info = ContentUtil.getPrimaryContent(holder);
		// if (info != null) {
		// this.reviseFile = info.getName();
		// }
		// ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

		Vector arrSecond = ContentUtil.getSecondaryContents(holder);
		if (arrSecond != null) {
		    for (int i = 0; i < arrSecond.size(); i++) {
			ContentInfo subInfo = (ContentInfo) arrSecond.get(i);
			this.reviseFile = subInfo.getName(); // 대표파일명만 추출후 나오기
			break;
		    }
		    if (arrSecond.size() > 0) {
			this.reviseFileCount = "" + arrSecond.size();
		    } else {
			this.reviseFileCount = "-";
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	this.createStamp = DateUtil.getDateString(ass.getPersistInfo().getCreateStamp(), "date");
	this.modifyStamp = DateUtil.getDateString(ass.getPersistInfo().getModifyStamp(), "date");
    }

    public String getReviseFileCount() {
	return reviseFileCount;
    }

    public void setReviseFileCount(String reviseFileCount) {
	this.reviseFileCount = reviseFileCount;
    }

    public String getRevNo() {
	return revNo;
    }

    public void setRevNo(String revNo) {
	this.revNo = revNo;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
    }

    public String getReviseFile() {
	return reviseFile;
    }

    public void setReviseFile(String reviseFile) {
	this.reviseFile = reviseFile;
    }

    public String getPjtOid() {
	return pjtOid;
    }

    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
    }

    public String getReviseCause() {
	return reviseCause;
    }

    public void setReviseCause(String reviseCause) {
	this.reviseCause = reviseCause;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getModifyStamp() {
	return modifyStamp;
    }

    public void setModifyStamp(String modifyStamp) {
	this.modifyStamp = modifyStamp;
    }

}
