package ext.ket.sysif.solr.entity;

import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.shared.dto.BaseDTO;

public class IndexSearchDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String queryString;
    private String searchCategory;
    private String searchKeyword;
    private String number;
    private String title;
    private String state;
    private String version;
    private String createStamp;
    private String createBy;
    private String type;
    private String iteration;

    public IndexSearchDTO() {
    }

    public IndexSearchDTO(Persistable persistable) {
	super.setOid(CommonUtil.getOIDString(persistable));
	if (persistable instanceof E3PSProject) {
	    E3PSProject e3psProject = (E3PSProject) persistable;

	    if (e3psProject instanceof ReviewProject) {
		this.number = e3psProject.getPjtNo();
		// 일정변경으로 인해 Project revision이 발생되어 결과에 최신버전만 반영되도록 title이 나타나게 변경
		if (!e3psProject.isCheckOut() && e3psProject.isLastest()) {
		    this.title = e3psProject.getPjtName();
		}
		this.createStamp = DateUtil.getDateString(e3psProject.getCreateTimestamp(), "all");
		this.createBy = e3psProject.getCreator().getFullName();
		this.version = "-";
		this.state = e3psProject.getLifeCycleState().getDisplay();
		this.iteration = "-";
		this.type = "프로젝트(검토)";
	    } else if (e3psProject instanceof ProductProject) {
		this.number = e3psProject.getPjtNo();
		// 일정변경으로 인해 Project revision이 발생되어 결과에 최신버전만 반영되도록 title이 나타나게 변경
		if (e3psProject.isLastest()) {
		    this.title = e3psProject.getPjtName();
		}
		ExtendScheduleData extendScheduleData = (ExtendScheduleData) e3psProject.getPjtSchedule().getObject();
		if (extendScheduleData != null) {
		    this.createStamp = DateUtil.getDateString(extendScheduleData.getPlanStartDate(), "all");
		}
		WTUser pm = ProjectUserHelper.manager.getPM(e3psProject);
		if (pm != null) {
		    this.createBy = ProjectUserHelper.manager.getPM(e3psProject).getFullName();
		}
		this.version = "-";
		this.state = e3psProject.getLifeCycleState().getDisplay();
		this.iteration = "-";
		this.type = "프로젝트(제품)";
	    } else if (e3psProject instanceof MoldProject) {
		MoldProject moldProject = (MoldProject) e3psProject;
		MoldItemInfo moldInfo = moldProject.getMoldInfo();
		if (moldInfo != null) {
		    this.number = moldInfo.getDieNo();
		    // 일정변경으로 인해 Project revision이 발생되어 결과에 최신버전만 반영되도록 title이 나타나게 변경
		    if (moldProject.isLastest()) {
			this.title = e3psProject.getPjtName();
		    }
		}
		this.createStamp = DateUtil.getDateString(moldProject.getCreateTimestamp(), "all");
		WTUser pm = ProjectUserHelper.manager.getPM(moldProject);
		if (pm != null) {
		    this.createBy = ProjectUserHelper.manager.getPM(moldProject).getFullName();
		}
		this.version = "-";
		this.state = moldProject.getLifeCycleState().getDisplay();
		this.iteration = "-";
		this.type = "프로젝트(금형)";
	    }
	} else if (persistable instanceof KETProjectDocument) {
	    KETProjectDocument ketProjectDocument = (KETProjectDocument) persistable;
	    this.number = ketProjectDocument.getNumber();
	    this.title = ketProjectDocument.getTitle();
	    this.createStamp = DateUtil.getDateString(ketProjectDocument.getCreateTimestamp(), "all");
	    this.createBy = ketProjectDocument.getCreator().getFullName();
	    this.version = ketProjectDocument.getVersionIdentifier().getValue();
	    this.state = ketProjectDocument.getLifeCycleState().getDisplay();
	    this.iteration = ketProjectDocument.getIterationIdentifier().getValue();
	    this.type = "개발산출물";
	} else if (persistable instanceof EPMDocument) {
	    EPMDocument epmDocument = (EPMDocument) persistable;
	    this.number = epmDocument.getNumber();
	    this.title = epmDocument.getName();
	    this.createStamp = DateUtil.getDateString(epmDocument.getCreateTimestamp(), "all");
	    this.createBy = epmDocument.getCreator().getFullName();
	    this.version = epmDocument.getVersionIdentifier().getValue();
	    this.state = epmDocument.getLifeCycleState().getDisplay();
	    this.iteration = epmDocument.getIterationIdentifier().getValue();
	    this.type = "도면";
	} else if (persistable instanceof WTPart) {
	    WTPart wtPart = (WTPart) persistable;
	    this.number = wtPart.getNumber();
	    this.title = wtPart.getName();
	    this.createStamp = DateUtil.getDateString(wtPart.getCreateTimestamp(), "all");
	    this.createBy = wtPart.getCreator().getFullName();
	    this.version = wtPart.getVersionIdentifier().getValue();
	    this.state = wtPart.getLifeCycleState().getDisplay();
	    this.iteration = wtPart.getIterationIdentifier().getValue();
	    this.type = "부품";
	} else if (persistable instanceof KETProdChangeOrder) {
	    KETProdChangeOrder prodChangeOrder = (KETProdChangeOrder) persistable;
	    this.number = prodChangeOrder.getNumber();
	    this.title = prodChangeOrder.getEcoName();
	    this.createStamp = DateUtil.getDateString(prodChangeOrder.getCreateTimestamp(), "all");
	    this.createBy = prodChangeOrder.getCreatorFullName();
	    this.version = "-";
	    this.state = prodChangeOrder.getLifeCycleState().getDisplay();
	    this.iteration = prodChangeOrder.getIterationIdentifier().getValue();
	    this.type = "설계변경";
	} else if (persistable instanceof KETProdChangeRequest) {
	    KETProdChangeRequest prodChangeRequest = (KETProdChangeRequest) persistable;
	    this.number = prodChangeRequest.getNumber();
	    this.title = prodChangeRequest.getEcrName();
	    this.createStamp = DateUtil.getDateString(prodChangeRequest.getCreateTimestamp(), "all");
	    this.createBy = prodChangeRequest.getCreatorFullName();
	    this.version = "-";
	    this.state = prodChangeRequest.getLifeCycleState().getDisplay();
	    this.iteration = prodChangeRequest.getIterationIdentifier().getValue();
	    this.type = "설계변경";
	} else if (persistable instanceof KETDqmRaise) {
	    KETDqmRaise dqmRaise = (KETDqmRaise) persistable;
	    KETDqmHeader dqmHeader = null;
	    try {
		QuerySpec query = new QuerySpec();
		int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
		SearchCondition sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(dqmRaise));
		query.appendWhere(sc, new int[] { idxHeaer });
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    dqmHeader = (KETDqmHeader) tempObj[0];
		}
	    } catch (Exception e) {
	    }
	    this.number = dqmHeader.getProblemNo();
	    this.title = dqmHeader.getProblem();
	    this.createStamp = DateUtil.getDateString(dqmHeader.getPersistInfo().getCreateStamp(), "all");
	    this.createBy = dqmHeader.getRaise().getUser().getFullName();
	    this.version = "-";
	    this.state = dqmHeader.getRaise().getLifeCycleState().getDisplay();
	    this.iteration = "-";
	    this.type = "개발품질문제";
	} else if (persistable instanceof KETSampleRequest) {
	    KETSampleRequest sampleRequest = (KETSampleRequest) persistable;
	    this.number = sampleRequest.getRequestNo();
	    this.title = sampleRequest.getRequestTitle();
	    this.createStamp = DateUtil.getDateString(sampleRequest.getPersistInfo().getCreateStamp(), "all");
	    this.createBy = sampleRequest.getCreatorFullName();
	    this.version = "-";
	    this.state = sampleRequest.getLifeCycleState().getDisplay();
	    this.iteration = "-";
	    this.type = "Sample요청";
	}
    }

    public String getNumber() {
	return number;
    }

    public String getNumberHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getNumberHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getCreateBy() {
	return createBy;
    }

    public void setCreateBy(String createBy) {
	this.createBy = createBy;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getIteration() {
	return iteration;
    }

    public void setIteration(String iteration) {
	this.iteration = iteration;
    }

    public String getSearchCategory() {
	return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
	this.searchCategory = searchCategory;
    }

    public String getSearchKeyword() {
	return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
	this.searchKeyword = searchKeyword;
    }

    /**
     * @return the queryString
     */
    public String getQueryString() {
	return queryString;
    }

    /**
     * @param queryString
     *            the queryString to set
     */
    public void setQueryString(String queryString) {
	this.queryString = queryString;
    }

}
