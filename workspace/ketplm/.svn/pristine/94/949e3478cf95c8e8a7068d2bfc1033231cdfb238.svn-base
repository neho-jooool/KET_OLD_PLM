package e3ps.project.beans;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import wt.change2.WTChangeOrder2;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTProperties;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CharUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.dms.entity.KETStandardTemplate;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.project.E3PSProject;
import e3ps.project.KETDqmRaiseOutputLink;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;

/**
 * 프로젝트 산출물과 관련된 정보를 취합하는 Class
 */
public class ProjectOutputData {
    public String oid = "";
    public ProjectOutput output;
    public TemplateProject project;
    public TemplateTask task;

    public String name = "";
    public String description = "";
    // public E3PSDocumentFolderType folderType = null;
    // public E3PSDocumentType docType = null;
    public String location = "";
    public String locationStr = "";
    public String createDate = "";
    public WTUser registryUser;
    public String role_en = "";
    public String role_ko = "";
    public String objType = "";
    public OutputDocumentLink link;

    public Master documentMaster;
    public RevisionControlled document;
    public RevisionControlled currentDocument;
    public RevisionControlled LastDocument;
    public WTChangeOrder2 changeOrder;
    public KETProdChangeOrder prodChangeOrder;
    public KETMoldChangeOrder moldChangeOrder;
    public KETTryCondition tryCondition;
    public KETTryPress tryPress;
    public KETTryMold tryMold;
    public KETDqmRaise dqmRaise;
    public Object ob;
    public String docStateStr = "";
    public boolean canRegistry = false;
    public boolean isRegistry = false;
    public boolean canDelete = true;
    public boolean isWorking = true;
    public boolean isCreator = false;
    public String isPrimary = "-";
    public String subjectType = "";
    public String templateOid = "";
    public String templateName = "";
    public String outputDocOid = "";
    public String outputDocName = "";
    public String totalCost = "";
    public String totalCostFinal = "";
    public String rate = "";
    public String rateFinal = "";
    public String salesTarget = "";
    public String salesTargetFinal = "";
    public String yearAverageQty = "";
    public String yearAverageQtyFinal = "";
    
    public ProjectOutputData(ProjectOutput _output) {
	try {
	    WTUser mine = (WTUser) SessionHelper.manager.getPrincipal();

	    this.oid = CommonUtil.getOIDString(_output);
	    this.output = _output;
	    this.project = _output.getProject();
	    this.task = _output.getTask();
	    this.name = _output.getOutputName();
	    if (_output.getObjType().equals("DOC")) {
		this.objType = "문서";
	    } else if (_output.getObjType().equals("DWG")) {
		this.objType = "도면";
	    } else if (_output.getObjType().equals("TRY")) {
		this.objType = "Try조건표";
	    } else if (_output.getObjType().equals("GATE")) {
		this.objType = "Gate";
	    } else if (_output.getObjType().equals("ECO")) {
		this.objType = "ECO";
	    } else if (_output.getObjType().equals("QLP")) {
		this.objType = "QLP";
	    } else if (_output.getObjType().equals("ETC")) {
		this.objType = "기타";
	    } else if (_output.getObjType().equals("COST")) {
		this.objType = "원가";
	    } else if (_output.getObjType().equals("SALES")) {
		this.objType = "판가";
	    }

	    if (_output.isIsPrimary()) {
		this.isPrimary = "필수";
	    } else {
		this.isPrimary = "-";
	    }

	    this.subjectType = _output.getSubjectType();
	    this.outputDocOid = _output.getOutputDocOid();
	    this.outputDocName = _output.getOutputDocName();

	    if (_output.getOutputDesc() != null && _output.getOutputDesc().length() > 0 && !_output.getOutputDesc().equals("null"))
		this.description = _output.getOutputDesc();
	    else
		this.description = "";

	    if (_output.getOwner().getPrincipal() != null)
		this.isCreator = true;

	    this.location = _output.getOutputLocation();
	    if (this.location != null) {
		// int index = this.location.lastIndexOf(".")+1;
		// String firstIndex = this.location.substring(index,index+1).toUpperCase();
		// folderType =
		// (E3PSDocumentFolderType)EnumeratedTypeUtil.toEnumeratedType("e3ps.document.E3PSDocumentFolderType."+firstIndex);
		// docType = (E3PSDocumentType)EnumeratedTypeUtil.toEnumeratedType(this.location);
		// this.locationStr = folderType.getComment() + " / " + docType.getComment();
		// this.locationStr = StringUtil.getMid(this.location, 5, this.location.length()-5);
		this.locationStr = StringUtil.getMid(this.location, 5, this.location.length() - 5);

	    } else {
		this.locationStr = "";
	    }

	    this.createDate = DateUtil.getDateString(_output.getPersistInfo().getCreateStamp(), "date");
	    this.registryUser = (WTUser) _output.getOwner().getPrincipal();
	    this.role_en = _output.getOutputRole();
	    if (this.role_en != null) {
		wt.team.TeamTemplate tempTeam = wt.team.TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Team_Project");
		Vector vec = tempTeam.getRoles();
		for (int i = vec.size() - 1; i > -1; i--) {
		    wt.project.Role role = (wt.project.Role) vec.get(i);
		    if (role.toString().equalsIgnoreCase(output.getOutputRole())) {
			this.role_ko = role.getDisplay(Locale.KOREA);
		    }
		}
	    }

	    if (project instanceof E3PSProject) {
		this.canRegistry = mine.equals(_output.getOwner().getPrincipal());
		try {
		    this.document = ProjectOutputHelper.manager.getDocMasterForOutput(output);
		} catch (Exception e) {

		    Kogger.error(getClass(), e);
		}

		if (!StringUtil.isEmpty(_output.getOutputDocOid())) {
		    this.outputDocOid = _output.getOutputDocOid();
		    KETStandardTemplate standardTmpl = (KETStandardTemplate) CommonUtil.getObject(_output.getOutputDocOid());
		    if (standardTmpl != null) {
			String tmplFileName = "";
			ContentDTO contentDTO = KETContentHelper.manager.getPrimaryContent(standardTmpl);
			// String primaryConentIconUrl = contentDTO.getIconURLStr();
			// String primaryConentDownUrl = contentDTO.getDownloadURL();
			if (contentDTO == null) {
			    ArrayList<ContentDTO> secondaryContents = KETContentHelper.manager.getSecondaryContents(standardTmpl);
			    if (secondaryContents != null && secondaryContents.size() > 0) {
				for (ContentDTO dto : secondaryContents) {
				    // String contentoid = dto.getContentoid();
				    tmplFileName = dto.getName();
				}
			    }
			} else {
			    tmplFileName = contentDTO.getName();
			}

			this.outputDocName = tmplFileName;
		    }
		}

		if (this.document != null) {
		    this.currentDocument = this.document;// ObjectUtil.getLatestObject((Master)this.document.getMaster());
		    this.LastDocument = (RevisionControlled) ObjectUtil.getLatestObject((Master) this.document.getMaster());
		    this.isRegistry = true;
		    this.createDate = DateUtil.getDateString(this.currentDocument.getPersistInfo().getCreateStamp(), "date");
		    this.docStateStr = this.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
		    if (this.currentDocument.getLifeCycleName().equals(
			    CharUtil.E2K(WTProperties.getServerProperties().getProperty("baseline.lifecycle")))) {
			this.isWorking = false;
		    } else {
			if (this.docStateStr.equals("승인됨"))
			    this.isWorking = false;
			if (this.docStateStr.equals("배포완료"))
			    this.isWorking = false;
		    }
		} else {
		    // ECO인 경우
		    if ("ECO".equals(output.getObjType())) {

			WTChangeOrder2 changeOrderObj = null;
			KETProdChangeOrder prodChangeOrderObj = null;
			KETMoldChangeOrder moldChangeOrderObj = null;
			QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);

			if (qr.hasMoreElements()) {
			    prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
			    this.changeOrder = (WTChangeOrder2) prodChangeOrderObj;
			}

			qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);

			if (qr.hasMoreElements()) {
			    moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
			    this.changeOrder = (WTChangeOrder2) moldChangeOrderObj;
			}

			if (prodChangeOrderObj != null || moldChangeOrderObj != null) {
			    this.isRegistry = true;
			}

		    } else if ("TRY".equals(output.getObjType())) {
			KETTryCondition tryConditionObj = null;
			KETTryMold moldTryConditionObj = null;
			KETTryPress pressTryConditionObj = null;
			QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);

			if (qr.hasMoreElements()) {
			    moldTryConditionObj = (KETTryMold) qr.nextElement();
			    this.tryCondition = (KETTryCondition) moldTryConditionObj;
			}

			qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);

			if (qr.hasMoreElements()) {
			    pressTryConditionObj = (KETTryPress) qr.nextElement();
			    this.tryCondition = (KETTryCondition) pressTryConditionObj;
			}

			if (moldTryConditionObj != null || pressTryConditionObj != null) {
			    this.isRegistry = true;
			}

		    } else if ("QLP".equals(output.getObjType())) {
			KETDqmRaise dqmRaiseLoc = null;
			KETDqmRaiseOutputLink dqmRaiseOutputLink = null;
			QueryResult qr = PersistenceHelper.manager.navigate(output, "dqm", KETDqmRaiseOutputLink.class);

			if (qr.hasMoreElements()) {
			    dqmRaiseLoc = (KETDqmRaise) qr.nextElement();
			    this.dqmRaise = (KETDqmRaise) dqmRaiseLoc;
			}

			if (dqmRaiseLoc != null) {
			    this.isRegistry = true;
			}

		    }

		}

		if (_output.getObjType().equals("ETC") || _output.getObjType().equals("COST") || _output.getObjType().equals("SALES")) {
		    if (_output.getComplete_reason() != null && _output.getComplete_reason().length() > 0) {
			this.isRegistry = true;
		    }
		    totalCost = _output.getTotalCost();
		    totalCostFinal = _output.getTotalCostFinal();
		    rate = _output.getRate();
		    rateFinal =  _output.getRateFinal();
		    salesTarget =  _output.getSalesTarget();
		    salesTargetFinal = _output.getSalesTargetFinal();
		    yearAverageQty =  _output.getYearAverageQty();
		    yearAverageQtyFinal = _output.getYearAverageQtyFinal();
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
