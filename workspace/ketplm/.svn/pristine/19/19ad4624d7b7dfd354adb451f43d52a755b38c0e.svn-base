// Generated StandardE3PSProjectService%47C222740025: ? 08/13/13 09:57:25
/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package e3ps.project;

// ##begin user.imports preserve=yes
import java.io.File; // Preserved unmodeled dependency
import java.io.Serializable;
import java.text.ParseException; // Preserved unmodeled dependency
import java.util.ArrayList; // Preserved unmodeled dependency
import java.util.Calendar; // Preserved unmodeled dependency
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer; // Preserved unmodeled dependency
import java.util.Vector; // Preserved unmodeled dependency

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger; // Preserved unmodeled dependency
import org.quartz.JobDetail; // Preserved unmodeled dependency
import org.quartz.Scheduler; // Preserved unmodeled dependency
import org.quartz.SchedulerFactory; // Preserved unmodeled dependency

import wt.events.KeyedEventListener; // Preserved unmodeled dependency
import wt.fc.ObjectReference; // Preserved unmodeled dependency
import wt.fc.PersistenceHelper; // Preserved unmodeled dependency
import wt.fc.PersistenceManagerEvent; // Preserved unmodeled dependency
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult; // Preserved unmodeled dependency
import wt.fc.ReferenceFactory; // Preserved unmodeled dependency
import wt.folder.Folder; // Preserved unmodeled dependency
import wt.folder.FolderEntry; // Preserved unmodeled dependency
import wt.folder.FolderHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleServiceEvent; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleTemplate; // Preserved unmodeled dependency
import wt.org.WTUser; // Preserved unmodeled dependency
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction; // Preserved unmodeled dependency
import wt.project.Role; // Preserved unmodeled dependency
import wt.query.ClassAttribute;
import wt.query.QueryException; // Preserved unmodeled dependency
import wt.query.QuerySpec; // Preserved unmodeled dependency
import wt.query.SQLFunction;
import wt.query.SearchCondition; // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionHelper; // Preserved unmodeled dependency
import wt.team.TeamHelper; // Preserved unmodeled dependency
import wt.team.TeamTemplate; // Preserved unmodeled dependency
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException; // Preserved unmodeled dependency
import e3ps.common.code.NumberCode; // Preserved unmodeled dependency
import e3ps.common.code.NumberCodeHelper; // Preserved unmodeled dependency
import e3ps.common.code.NumberCodeType; // Preserved unmodeled dependency
import e3ps.common.jdf.config.ConfigImpl; // Preserved unmodeled dependency
import e3ps.common.query.SearchUtil; // Preserved unmodeled dependency
import e3ps.common.util.CommonUtil; // Preserved unmodeled dependency
import e3ps.common.util.DateUtil; // Preserved unmodeled dependency
import e3ps.common.util.ManageSequence; // Preserved unmodeled dependency
import e3ps.common.util.StringUtil; // Preserved unmodeled dependency
import e3ps.common.util.WCUtil; // Preserved unmodeled dependency
import e3ps.dms.entity.KETDevelopmentRequest; // Preserved unmodeled dependency
import e3ps.groupware.company.PeopleData; // Preserved unmodeled dependency
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProductHelper; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectHelper; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectScheduleHelper; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectScheduler; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectStateHelper; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectUserHelper; // Preserved unmodeled dependency
import e3ps.project.beans.TaskHelper; // Preserved unmodeled dependency
import e3ps.project.beans.TemplateProjectData; // Preserved unmodeled dependency
import e3ps.project.customerPlan.beans.CustomerPlanHelper; // Preserved unmodeled dependency
import e3ps.project.historyprocess.ProjectModuleEventListener; // Preserved unmodeled dependency
import e3ps.project.material.MoldMaterial; // Preserved unmodeled dependency
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMProjectType; // Preserved unmodeled dependency
import e3ps.project.outputtype.ProjectOutPutType; // Preserved unmodeled dependency
import e3ps.project.wbsupload.WBSUpload; // Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
import ext.ket.dms.service.KETProjectDocumentHelper;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartListItemDTO;
import ext.ket.project.program.entity.ProgramEventDTO;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.project.trycondition.service.TryConditionHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ReflectUtil;
// Preserved unmodeled dependency

// Preserved unmodeled dependency

/**
 * <p>
 * Use the <code>newStandardE3PSProjectService</code> static factory method(s), not the <code>StandardE3PSProjectService</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * @version 1.0
 **/

public class StandardE3PSProjectService extends StandardManager implements E3PSProjectService, Serializable {
    private static final String RESOURCE = "e3ps.project.projectResource";
    private static final String CLASSNAME = StandardE3PSProjectService.class.getName();

    /**
     * Returns the conceptual (modeled) name for the class. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * Dummy method to be overridden by subclasses. Subclasses should override this method and provide startup processing.??
     * 
     * @exception wt.services.ManagerException
     **/
    @Override
    protected synchronized void performStartupProcess() throws ManagerException {

	super.performStartupProcess();
	// if(isERPCheck){
	// startProjectSchedule();
	// }

	// 2015-01-07 쿼츠 스케쥴러 사용안함으로 수정
	// startProjectSchedule();
	// Kogger.debug(getClass(), "registerEvents = " + this.getClass().getName() + "
	// " + "PRE_STORE EVENT");
	// 2015-01-07 쿼츠 스케쥴러 사용안함으로 수정

	KeyedEventListener listener = new ProjectModuleEventListener(getManagerService().getName());

	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.PRE_STORE));

	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_STORE));

	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_MODIFY));

	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.PRE_DELETE));

	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_DELETE));

	getManagerService().addEventListener(listener, LifeCycleServiceEvent.generateEventKey(LifeCycleServiceEvent.SET_STATE));
	getManagerService().addEventListener(listener,
	        wt.vc.VersionControlServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.NEW_VERSION));

	getManagerService().addEventListener(listener,
	        wt.vc.VersionControlServiceEvent.generateEventKey(wt.vc.VersionControlServiceEvent.POST_MERGE));
	getManagerService().addEventListener(listener,
	        wt.content.ContentServiceEvent.generateEventKey(wt.content.ContentServiceEvent.POST_DOWNLOAD));
    }

    /**
     * Default factory for the class.
     * 
     * @return StandardE3PSProjectService
     * @exception wt.util.WTException
     **/
    public static StandardE3PSProjectService newStandardE3PSProjectService() throws WTException {

	StandardE3PSProjectService instance = new StandardE3PSProjectService();
	instance.initialize();
	return instance;
    }

    @Override
    public TemplateProject createTemplateProject(Hashtable hash) throws WTException {
	// Attribute Setting
	String name = (String) hash.get("NAME"); // Template Name
	String durationStr = (String) hash.get("DURATION"); // Template Duration String Value
	int duration = StringUtil.parseInt(durationStr, 1); // Template Duration
	String description = (String) hash.get("DESCRIPTION"); // Template Description
	String tempid = (String) hash.get("TEMPID"); // Copy Template OID
	String ptType = (String) hash.get("PTTYPE");
	String planType = (String) hash.get("PLANTYPE");
	String assembling = (String) hash.get("ASSEMBLING");
	String developType = (String) hash.get("DEVELOPTYPE");
	String makeType = (String) hash.get("MAKETYPE");
	String productType = (String) hash.get("PRODUCTTYPE");
	String devType = (String) hash.get("DEVTYPE");
	String devStep = (String) hash.get("DEVSTEP");
	String clientCompany = (String) hash.get("CLIENTCOMPANY");
	String category = (String) hash.get("CATEGORY");
	String makeOffice = (String) hash.get("MAKEOFFICE");
	String moldType = (String) hash.get("MOLDTYPE");
	String making = (String) hash.get("MAKING");
	String activeType = (String) hash.get("ACTIVETYPE");
	String division = (String) hash.get("DIVISION");

	File file = (File) hash.get("file");

	TemplateProject oldPJT = null;
	TemplateProjectData oldPJTData = null;
	ProductTemplateProject ptProject = null;
	MoldTemplateProject mtProject = null;
	ElectronTemplateProject etProject = null;
	TemplateProject tProject = null;

	Transaction transaction = new Transaction();

	try {
	    transaction.start();

	    if (StringUtil.checkString(tempid)) {
		oldPJT = (TemplateProject) CommonUtil.getObject(tempid);
		oldPJTData = new TemplateProjectData(oldPJT);
	    }

	    // 1. ScheduleData Object Create
	    ScheduleData schedule = ScheduleData.newScheduleData();
	    // 1.1 Duration Setting
	    if (oldPJTData != null) {
		schedule.setDuration(oldPJTData.tempDuration);
	    } else {
		schedule.setDuration(duration);
	    }
	    // 1.2 ScheduleData Object Save
	    schedule = (ScheduleData) PersistenceHelper.manager.save(schedule);

	    // 2. TemplateProject Object Create
	    if ("1".equals(ptType) || "7".equals(ptType)) { // ProductTemplateProject
		ptProject = ProductTemplateProject.newProductTemplateProject();

		// 2.1 Template Sequence Setting
		ptProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

		// 2.2 Template Lifecycle Setting
		Folder folder = null;
		String folderPath = ConfigImpl.getInstance().getString("folder.project");
		Kogger.debug(getClass(), "########## folderPath=" + folderPath);
		folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
		Kogger.debug(getClass(), "########## folder=" + folder);

		// 2.3 FolderHelper.assignFolder(project, folder);
		FolderHelper.assignLocation((FolderEntry) ptProject, folder);

		// 2.4m LifeCycleHelper.setLifeCycle(project,
		// LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

		// 2.5 Name Setting
		ptProject.setPjtName(StringUtil.checkNull(name.trim()));

		// 2.6 Desc Setting
		if (description != null) {
		    ptProject.setPjtDesc(StringUtil.checkNull(description.trim()));
		} else {
		    ptProject.setPjtDesc("");
		}

		// 2.7 ScheduleData Object
		ptProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));
		if ("1".equals(ptType)) {
		    ptProject.setPjtType(2); // 자동차
		} else {
		    ptProject.setPjtType(5); // KETS
		}

		ptProject.setPlanType(Boolean.valueOf(planType).booleanValue());
		ptProject.setAssembling(Boolean.valueOf(assembling).booleanValue());
		if (developType.length() > 0)
		    ptProject.setDevelopType(Integer.parseInt(developType));
		// 추가 컬럼 2014.07.10
		ptProject.setDevType(devType);
		ptProject.setDevStep(devStep);
		ptProject.setClientCompany(clientCompany);
		ptProject.setActiveType(activeType);
		ptProject.setDivision(division);

		/* 2014.07.18 조건에 따른 값 COPY 로직 만들기 */

		// 2.8 TemplateProject Object Save
		tProject = (ProductTemplateProject) PersistenceHelper.manager.save(ptProject);

		// 3. TemplateProject Object Copy & TemplateTask Object Copy
		if (oldPJT != null) {
		    // 3.1 TemplateProject Copy(JELProject Information)
		    ProjectHelper.manager.copyTemplateProjectInfo(tProject, oldPJT);
		    // 3.2 TemplateTask Copy(JELTask Information)
		    // 3.3 TaskHelper.manager.copyTemplateTaskInfo(project, oldPJT);
		    TaskHelper.manager.copyTemplateProjectFromTemplateProject(tProject, oldPJT);
		}

	    } else if ("2".equals(ptType) || "6".equals(ptType) || "8".equals(ptType) || "10".equals(ptType) || "11".equals(ptType)
		    || "12".equals(ptType)) { // MoldTemplateProject
		mtProject = MoldTemplateProject.newMoldTemplateProject();

		// 2.1 Template Sequence Setting
		mtProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

		// 2.2 Template Lifecycle Setting
		Folder folder = null;
		String folderPath = ConfigImpl.getInstance().getString("folder.project");
		Kogger.debug(getClass(), "########## folderPath=" + folderPath);
		folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
		Kogger.debug(getClass(), "########## folder=" + folder);

		// 2.3 FolderHelper.assignFolder(project, folder);
		FolderHelper.assignLocation((FolderEntry) mtProject, folder);
		// 2.4m LifeCycleHelper.setLifeCycle(project,
		// LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

		// 2.5 Name Setting
		mtProject.setPjtName(StringUtil.checkNull(name.trim()));
		// 2.6 Desc Setting
		if (description != null) {
		    mtProject.setPjtDesc(StringUtil.checkNull(description.trim()));
		} else {
		    mtProject.setPjtDesc("");
		}
		// 2.7 ScheduleData Object
		mtProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

		/* 2014.07.11 컬럼추가 */
		mtProject.setMakeOffice(makeOffice);

		// 구매품
		if ("10".equals(ptType) || "11".equals(ptType) || "12".equals(ptType)) {
		    mtProject.setMoldType("purchase");
		} else {
		    mtProject.setMoldType(moldType);
		}

		mtProject.setMaking(making);
		mtProject.setActiveType(activeType);
		mtProject.setDivision(division);

		if ("2".equals(ptType) || "10".equals(ptType)) {
		    mtProject.setPjtType(3); // 자동차
		} else if ("6".equals(ptType) || "11".equals(ptType)) {
		    mtProject.setPjtType(4); // 전자
		} else if ("8".equals(ptType) || "12".equals(ptType)) {
		    mtProject.setPjtType(6); // KETS
		}

		if (makeType.length() > 0)
		    mtProject.setMakeType(Integer.parseInt(makeType));

		// 2.8 TemplateProject Object Save
		tProject = (MoldTemplateProject) PersistenceHelper.manager.save(mtProject);

		// 3. TemplateProject Object Copy & TemplateTask Object Copy
		if (oldPJT != null) {
		    // 3.1 TemplateProject Copy(JELProject Information)
		    ProjectHelper.manager.copyTemplateProjectInfo(tProject, oldPJT);
		    // 3.2 TemplateTask Copy(JELTask Information)
		    // 3.3 TaskHelper.manager.copyTemplateTaskInfo(project, oldPJT);
		    TaskHelper.manager.copyTemplateProjectFromTemplateProject(tProject, oldPJT);
		}

	    } else if ("3".equals(ptType)) { // ElectronTemplateProject
		etProject = ElectronTemplateProject.newElectronTemplateProject();

		// 2.1 Template Sequence Setting
		etProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

		// 2.2 Template Lifecycle Setting
		Folder folder = null;
		String folderPath = ConfigImpl.getInstance().getString("folder.project");
		Kogger.debug(getClass(), "########## folderPath=" + folderPath);
		folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
		Kogger.debug(getClass(), "########## folder=" + folder);

		// 2.3 FolderHelper.assignFolder(project, folder);
		FolderHelper.assignLocation((FolderEntry) etProject, folder);
		// 2.4m LifeCycleHelper.setLifeCycle(project,
		// LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

		// 2.5 Name Setting
		etProject.setPjtName(StringUtil.checkNull(name.trim()));
		// 2.6 Desc Setting
		if (description != null) {
		    etProject.setPjtDesc(StringUtil.checkNull(description.trim()));
		} else {
		    etProject.setPjtDesc("");
		}
		// 2.7 ScheduleData Object
		etProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

		etProject.setPjtType(4); // ??

		if (productType.length() > 0)
		    etProject.setProductType(Integer.parseInt(productType));
		// 추가 컬럼 2014.07.10
		etProject.setDevType(devType);
		etProject.setDevStep(devStep);
		etProject.setClientCompany(clientCompany);
		etProject.setActiveType(activeType);
		etProject.setDivision(division);

		// 2.8 TemplateProject Object Save
		tProject = (ElectronTemplateProject) PersistenceHelper.manager.save(etProject);

		// 3. TemplateProject Object Copy & TemplateTask Object Copy
		if (oldPJT != null) {
		    // 3.1 TemplateProject Copy(JELProject Information)
		    ProjectHelper.manager.copyTemplateProjectInfo(tProject, oldPJT);
		    // 3.2 TemplateTask Copy(JELTask Information)
		    // 3.3 TaskHelper.manager.copyTemplateTaskInfo(project, oldPJT);
		    TaskHelper.manager.copyTemplateProjectFromTemplateProject(tProject, oldPJT);
		}

	    } else if ("4".equals(ptType) || "5".equals(ptType) || "13".equals(ptType) || "14".equals(ptType)) { // TemplateProject
		tProject = TemplateProject.newTemplateProject();

		// 2.1 Template Sequence Setting
		tProject.setPjtNo(ManageSequence.getSeqNo("", "0000", "ProjectMaster", "pjtNo"));

		// 2.2 Template Lifecycle Setting
		Folder folder = null;
		String folderPath = ConfigImpl.getInstance().getString("folder.project");
		// Kogger.debug(getClass(), "########## folderPath="+folderPath);
		folder = FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef());
		// Kogger.debug(getClass(), "########## folder="+folder);

		// 2.3 FolderHelper.assignFolder(project, folder);
		FolderHelper.assignLocation((FolderEntry) tProject, folder);
		// 2.4m LifeCycleHelper.setLifeCycle(project,
		// LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName));

		// 2.5 Name Setting
		tProject.setPjtName(StringUtil.checkNull(name.trim()));
		// 2.6 Desc Setting
		if (description != null) {
		    tProject.setPjtDesc(StringUtil.checkNull(description.trim()));
		} else {
		    tProject.setPjtDesc("");
		}
		// 2.7 ScheduleData Object
		tProject.setPjtSchedule(ObjectReference.newObjectReference(schedule));

		// Kogger.debug(getClass(), "##########
		// folderfolder="+Integer.parseInt(ptType));
		if ("4".equals(ptType) || "13".equals(ptType)) {
		    tProject.setPjtType(1); // ??
		} else {
		    tProject.setPjtType(0); // ??
		}

		if ("13".equals(ptType) || "14".equals(ptType)) {
		    tProject.setMoldType("work");
		}

		// 추가 컬럼 2014.07.10
		tProject.setDevType(devType);
		tProject.setDevStep(devStep);
		tProject.setClientCompany(clientCompany);
		tProject.setActiveType(activeType);
		tProject.setDivision(division);

		// 2.8 TemplateProject Object Save
		tProject = (TemplateProject) PersistenceHelper.manager.save(tProject);
		// Kogger.debug(getClass(), "########## folderfolder="+tProject);

		// 3. TemplateProject Object Copy & TemplateTask Object Copy
		if (oldPJT != null) {
		    // 3.1 TemplateProject Copy(JELProject Information)
		    ProjectHelper.manager.copyTemplateProjectInfo(tProject, oldPJT);
		    // 3.2 TemplateTask Copy(JELTask Information)
		    // 3.3 TaskHelper.manager.copyTemplateTaskInfo(project, oldPJT);
		    TaskHelper.manager.copyTemplateProjectFromTemplateProject(tProject, oldPJT);
		}
	    }

	    if (file != null) {
		WBSUpload.createWBS(tProject, file);
	    }

	    transaction.commit();
	    transaction = null;
	    // WBSUpload.createWBS( , );

	} catch (Exception e) {
	    if (transaction != null) {

		transaction.rollback();
	    }

	    transaction = null;
	    Kogger.error(getClass(), e);

	    throw new WTException(e);
	}
	return tProject;
    }

    @Override
    public TemplateProject updateTemplateProject(Hashtable hash) throws WTException {

	return null;
    }

    @Override
    public String deleteTemplateProject(Hashtable hash) throws WTException {
	String oid = (String) hash.get("OID");
	TemplateProject project = (TemplateProject) CommonUtil.getObject(oid);

	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    PersistenceHelper.manager.delete(project);
	    transaction.commit();
	    transaction = null;
	} catch (WTException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    transaction = null;
	}
	return null;

    }

    @Override
    public TemplateProject loadTemplateProject(Hashtable hash) throws WTException {
	// ##begin loadTemplateProject%47C2238C0151.body preserve=yes

	return null;
	// ##end loadTemplateProject%47C2238C0151.body
    }

    @Override
    public E3PSProject createE3PSProject(Hashtable hash) throws WTException {

	String projectType = (String) hash.get("projectType");
	if (projectType.equals("1") || projectType.equals("0")) {
	    return createReviewProject(hash);
	} else if (projectType.equals("2") || projectType.equals("4")) {
	    return createProductProject(hash);
	} else if (projectType.equals("3")) {
	    return createMoldProject(hash);
	} else {
	    return null;
	}

    }

    @Override
    public E3PSProject updateE3PSProject(Hashtable hash) throws WTException {
	String projectType = (String) hash.get("projectType");
	if (projectType.equals("1") || projectType.equals("0")) {
	    return updateReviewProject(hash);
	} else if (projectType.equals("2")) {
	    return updateProductProject(hash);
	} else if (projectType.equals("3")) {
	    return updateMoldProject(hash);
	} else if (projectType.equals("4")) {

	} else {
	    return null;
	}

	return null;
    }

    @Override
    public String deleteE3PSProject(Hashtable hash) throws WTException {

	return null;
    }

    @Override
    public E3PSProject holdE3PSProject(Hashtable hash) throws WTException {

	return null;
    }

    @Override
    public E3PSProject revocationE3PSProject(Hashtable hash) throws WTException {

	return null;
    }

    /**
     * ?????: createReviewProject ?????? : [PLM System 1????? Project ??? Iteration ??? ???
     * 
     * @param hash
     * @return
     * @throws WTException
     *             ?????: BoLee ?????? : 2013. 7. 4.
     */
    public ReviewProject createReviewProject(Hashtable hash) throws WTException {

	String projectNo = (String) hash.get("projectNo");
	String projectName = (String) hash.get("projectName");
	String planStartDate = (String) hash.get("planStartDate");
	String planEndDate = "";
	String proposalDate = (String) hash.get("proposalDate");
	String estimateDate = (String) hash.get("estimateDate");
	String projectTypeName = (String) hash.get("projectTypeName");

	String drNumber = (String) hash.get("drNumber");
	String drKeyOid = (String) hash.get("drKeyOid");

	// Template
	String tempid = (String) hash.get("TemplateOID");
	String wbsType = (String) hash.get("wbsType"); // wbsType
	String[] category = (String[]) hash.get("category"); // category
	String lifecycle = (String) hash.get("lifecycle");
	if (lifecycle == null || lifecycle.equals("") || lifecycle.length() == 0) {
	    lifecycle = "KET_REVIEW_PMS_LC";
	}
	ReviewProject project = null;

	Calendar tempCal = Calendar.getInstance();
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    project = ReviewProject.newReviewProject();

	    ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
	    if (StringUtil.checkString(planStartDate.trim()) && StringUtil.checkString(planEndDate.trim())) {
		int tempDuration = DateUtil.getDaysFromTo(planEndDate, planStartDate);
		schedule.setDuration(tempDuration);
	    }
	    schedule.setScheduleHistory(0);

	    if (StringUtil.checkString(planStartDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(planStartDate));
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }
	    schedule.setExecWork(0);
	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    FolderHelper.assignLocation((FolderEntry) project, FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef()));
	    LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
	    LifeCycleHelper.setLifeCycle(project, lct);

	    if (StringUtil.checkString(proposalDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(proposalDate));
		project.setProposalDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }
	    if (StringUtil.checkString(estimateDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(estimateDate));
		project.setEstimateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    project.setPjtNo(projectNo.trim());
	    project.setPjtSeq(0);
	    project.setPjtHistory(0);
	    // [START] [PLM System 1????? Project ??? Iteration ??? ???, 2013-07-04, BoLee
	    project.setPjtIteration(0);
	    // [END] [PLM System 1????? Project ??? Iteration ??? ???, 2013-07-04, BoLee
	    project.setPjtCompletion(0);
	    project.setLastest(true);
	    project.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    TemplateProject templateProject = null;
	    if (tempid != null && tempid.length() > 0) {
		templateProject = (TemplateProject) CommonUtil.getObject(tempid);
		project.setTemplateCode(templateProject.getPjtNo());
	    }

	    if (StringUtil.checkString(projectName.trim())) {
		project.setPjtName(StringUtil.checkNull(projectName.trim()));
	    }
	    // project.setPjtState(ProjectStateHelper.manager.PROGRESS);
	    project.setPjtState(1);

	    boolean isType0 = CommonUtil.isMember("전자사업부");
	    boolean isType1 = CommonUtil.isMember("자동차사업부");

	    if (isType0) {
		project.setPjtType(0);
	    }
	    if (isType1) {
		project.setPjtType(1);
	    }

	    // Number Code
	    String developenttype = (String) hash.get("developenttype");
	    String producttype = (String) hash.get("producttype");
	    String productTypeLevel2 = (String) hash.get("productTypeLevel2");
	    String assembledtype = (String) hash.get("assembledtype");
	    String rank = (String) hash.get("rank");

	    if (developenttype != null && developenttype.length() > 0) {
		NumberCode n1 = (NumberCode) NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developenttype);
		project.setDevelopentType(n1);
	    }
	    // if (producttype != null && producttype.length() > 0) {
	    // NumberCode n2 = (NumberCode) CommonUtil.getObject(producttype);
	    // project.setProductType(n2);
	    // }
	    if (productTypeLevel2 != null && productTypeLevel2.length() > 0) {
		project.setProductTypeLevel2(productTypeLevel2);
	    }
	    if (assembledtype != null && assembledtype.length() > 0) {
		NumberCode n3 = (NumberCode) CommonUtil.getObject(assembledtype);
		project.setAssembledType(n3);
	    }
	    if (rank != null && rank.length() > 0) {
		NumberCode n4 = (NumberCode) CommonUtil.getObject(rank);
		project.setRank(n4);
	    }
	    WTUser devPm = (WTUser) SessionHelper.getPrincipal();

	    // if (isType0) {
	    // PeopleData pd = new PeopleData(devPm);
	    // project.setDevDept(pd.department);
	    // }

	    // enterprise Department
	    project.setAttr1(projectTypeName);

	    // salesUser
	    String salesUser = (String) hash.get("salesUser");
	    WTUser wtsalesUser = null;
	    if (salesUser != null && salesUser.length() != 0) {
		wtsalesUser = (WTUser) CommonUtil.getObject(salesUser);
	    }
	    if (wtsalesUser != null) {
		project.setBusiness(wtsalesUser);
	    }

	    // ??? ??? ?????I/F
	    if (drKeyOid != null && drKeyOid.length() > 0) {
		project.setAttr2(drNumber);
		KETDevelopmentRequest dr = (KETDevelopmentRequest) CommonUtil.getObject(drKeyOid);
		project.setDevRequest(dr);
	    }

	    String developePurpose1 = (String) hash.get("developePurpose1");
	    if (developePurpose1 != null && developePurpose1.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL", developePurpose1);
		project.setMainGoalType(code);
	    }
	    String developePurpose2 = (String) hash.get("developePurpose2");

	    if (developePurpose2 != null && developePurpose2.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL", developePurpose2);
		project.setSubGoalType(code);
	    }

	    Kogger.debug(getClass(), "ReviewProject service save");
	    // save
	    project = (ReviewProject) PersistenceHelper.manager.save(project);

	    // PM 등록
	    String devUser = (String) hash.get("devUser");
	    WTUser wtdevUser = null;
	    if (devUser != null && devUser.length() != 0) {
		wtdevUser = (WTUser) CommonUtil.getObject(devUser);
	    }

	    PeopleData peo = new PeopleData(wtdevUser);
	    project.setDevDept(peo.department);

	    // Development Department
	    // String devDeptOid = (String) hash.get("devDeptOid");
	    // Department depart = null;
	    // if (devDeptOid != null && devDeptOid.length() != 0) {
	    // depart = (Department) CommonUtil.getObject(devDeptOid);
	    // }
	    // if (depart != null) {
	    // project.setDevDept(depart);
	    // }

	    if (wtdevUser != null)
		ProjectUserHelper.manager.replacePM(project, wtdevUser);
	    // ??? ??? ????????
	    // String devUser = (String) hash.get("devUser");
	    // if (isType0) {
	    // ProjectUserHelper.manager.setPM(project, devPm, 0);
	    // project = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project,
	    // State.toState("PLANCHANGE"), true);
	    //
	    // }

	    // CUSTOMEREVENT
	    if (hash.get("CUSTOMEREVENT") != null) {
		Vector customereventVec = getParamsVector(hash.get("CUSTOMEREVENT"));
		String customereventoid = null;
		for (int i = 0; i < customereventVec.size(); i++) {
		    customereventoid = (String) customereventVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(customereventoid);
		    if (code != null) {
			ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    // SUBCONTRACTOR
	    if (hash.get("SUBCONTRACTOR") != null) {
		Vector subcontractorVec = getParamsVector(hash.get("SUBCONTRACTOR"));
		String subcontractoroid = null;
		for (int i = 0; i < subcontractorVec.size(); i++) {
		    subcontractoroid = (String) subcontractorVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(subcontractoroid);
		    if (code != null) {
			ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    // nOidVec
	    if (hash.get("nOidVec") != null) {
		Vector nOidVec = getParamsVector(hash.get("nOidVec"));
		String nOid = null;
		for (int i = 0; i < nOidVec.size(); i++) {
		    nOid = (String) nOidVec.get(i);
		    NumberCode mnc = null;
		    NumberCode snc = null;
		    StringTokenizer token = new StringTokenizer(nOid, "@");
		    String nnOid[] = new String[token.countTokens()];
		    int t = 0;
		    while (token.hasMoreTokens()) {
			nnOid[t] = token.nextToken();
			t++;
		    }
		    String masterCode = nnOid[0];
		    String subCode = nnOid[1];
		    mnc = (NumberCode) CommonUtil.getObject(masterCode);
		    snc = (NumberCode) CommonUtil.getObject(subCode);
		    // Kogger.debug(getClass(), "???==>" + mnc.getName() + " ???==>" +
		    // snc.getName());

		    // ProjectSapSubcontractorLink
		    if (snc != null) {
			ProjectSapSubcontractorLink plink = ProjectSapSubcontractorLink.newProjectSapSubcontractorLink(snc, project);
			PersistenceHelper.manager.save(plink);
		    }

		    // ProjectMasterCodeSubCodeLink
		    if (snc != null && mnc != null) {
			ProjectMasterCodeSubCodeLink plink = ProjectMasterCodeSubCodeLink.newProjectMasterCodeSubCodeLink(snc, mnc);
			plink.setProjectId(CommonUtil.getOIDString(project));
			PersistenceHelper.manager.save(plink);

		    }
		}
	    }

	    // Role Member Setting
	    Role role = null;
	    WTUser wtuser = null;
	    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
	    Vector vecTeamStd = tempTeam.getRoles();

	    for (int i = 0; i < vecTeamStd.size(); i++) {
		role = (Role) vecTeamStd.get(i);
		String userOid = (String) hash.get(role.toString());

		if (userOid != null && userOid.length() > 0) {
		    wtuser = (WTUser) CommonUtil.getObject((String) hash.get(role.toString()));
		    if (wtuser != null) {
			ProjectUserHelper.manager.setRoleMember(project, role.toString(), wtuser, 0);
		    }
		}
	    }

	    // Member Setting
	    Vector projectMember = (Vector) hash.get("USERMEMBER");
	    if (projectMember != null && projectMember.size() > 0) {
		for (int i = 0; i < projectMember.size(); i++) {
		    wtuser = (WTUser) CommonUtil.getObject((String) projectMember.get(i));
		    if (wtuser != null)
			ProjectUserHelper.manager.setMember(project, wtuser, 0);
		}
	    }
	    // ########################################################################################################
	    // ??? ???
	    // ProductInfo
	    Vector rowId = (Vector) hash.get("rowIdVec");
	    Vector pNum = (Vector) hash.get("pNumVec");
	    Vector pName = (Vector) hash.get("pNameVec");
	    Vector areas = (Vector) hash.get("areasVec");
	    Vector usageT = (Vector) hash.get("usageTVec");
	    Vector assemblyPlaceType = (Vector) hash.get("assemblyPlaceTypeVec"); // 조립처 구분(사내/외주)
	    Vector assemblyPlace = (Vector) hash.get("assemblyPlaceVec");// 조립처
	    Vector assembledType = (Vector) hash.get("assembledTypeVec");// 조립구분
	    // Vector price = (Vector) hash.get("priceVec");
	    // Vector cost = (Vector) hash.get("costVec");
	    // Vector rate = (Vector) hash.get("rateVec");

	    Vector y1T = (Vector) hash.get("y1TVec");
	    Vector y2T = (Vector) hash.get("y2TVec");
	    Vector y3T = (Vector) hash.get("y3TVec");
	    Vector y4T = (Vector) hash.get("y4TVec");
	    Vector y5T = (Vector) hash.get("y5TVec");
	    Vector y6T = (Vector) hash.get("y6TVec");
	    Vector y7T = (Vector) hash.get("y7TVec");
	    Vector y8T = (Vector) hash.get("y8TVec");
	    Vector y9T = (Vector) hash.get("y9TVec");
	    Vector y10T = (Vector) hash.get("y10TVec");
	    Vector carName = (Vector) hash.get("carNameVec");
	    // Kogger.debug(getClass(), "######################### ProductInfo Start
	    // #################################");
	    if (rowId != null && rowId.size() > 0) {
		for (int i = 0; i < rowId.size(); i++) {
		    // Kogger.debug(getClass(), "################## rowId :" + rowId.get(i));
		    ProductInfo pi = ProductInfo.newProductInfo();
		    // if (pNum.get(i) != null) {
		    // pi.setPNum((String) pNum.get(i));
		    // }
		    pi.setPName((String) pName.get(i));
		    // pi.setAreas((String) areas.get(i));
		    pi.setUsage((String) usageT.get(i));
		    // pi.setPrice((String) price.get(i));
		    // pi.setCost((String) cost.get(i));
		    // pi.setRate((String) rate.get(i));

		    String pOid = (String) hash.get("pOidVec" + rowId.get(i));

		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			// Kogger.debug(getClass(), "################## pOidVec :" +pOid);
			ProductInfo pii = (ProductInfo) CommonUtil.getObject(pOid);
			pi.setYear1(pii.getYear1());
			pi.setYear2(pii.getYear2());
			pi.setYear3(pii.getYear3());
			pi.setYear4(pii.getYear4());
			pi.setYear5(pii.getYear5());
			pi.setYear6(pii.getYear6());
			pi.setYear7(pii.getYear7());
			pi.setYear8(pii.getYear8());
			pi.setYear9(pii.getYear9());
			pi.setYear10(pii.getYear10());
			// ???..
			// pi.setSeqNum(pii.getSeqNum());
			// ??? ??? ?????? ?????? ?????seqNo???????????. -suk
			pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));

		    } else {
			pi.setYear1((String) y1T.get(i));
			pi.setYear2((String) y2T.get(i));
			pi.setYear3((String) y3T.get(i));
			pi.setYear4((String) y4T.get(i));
			pi.setYear5((String) y5T.get(i));
			pi.setYear6((String) y6T.get(i));
			pi.setYear7((String) y7T.get(i));
			pi.setYear8((String) y8T.get(i));
			pi.setYear9((String) y9T.get(i));
			pi.setYear10((String) y10T.get(i));

			/*
		         * ?????? ???..... ??????.... - suk if(pi.getSeqNum() == null){ Kogger.debug(getClass(), "NULL ?????..");
		         * pi.setSeqNum(ManageSequence.getSeqNo2("", "0000", "ProductInfo", "seqNum", i)); }else{ // NULL ????? ??? ??? ???.
		         * }
		         */

			pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));

		    }

		    pi.setProject(project);
		    if (StringUtil.checkString((String) assembledType.get(i))) {
			pi.setAssembledType((NumberCode) CommonUtil.getObject((String) assembledType.get(i)));// 조립구분
		    }
		    if (StringUtil.checkString((String) assemblyPlaceType.get(i))) {
			pi.setAssemblyPlaceType((String) assemblyPlaceType.get(i));
			if (((String) assemblyPlaceType.get(i)).equals("외주")) {
			    pi.setAssemblyPartnerNo((String) assemblyPlace.get(i));
			} else {
			    pi.setAssemblyPlace((NumberCode) CommonUtil.getObject((String) assemblyPlace.get(i)));
			}
		    }
		    pi = (ProductInfo) PersistenceHelper.manager.save(pi);

		    Vector count = (Vector) hash.get("countVec" + rowId.get(i));
		    Vector optOid = (Vector) hash.get("optOidVec" + rowId.get(i));
		    Vector y1 = (Vector) hash.get("y1Vec" + rowId.get(i));
		    Vector y2 = (Vector) hash.get("y2Vec" + rowId.get(i));
		    Vector y3 = (Vector) hash.get("y3Vec" + rowId.get(i));
		    Vector y4 = (Vector) hash.get("y4Vec" + rowId.get(i));
		    Vector y5 = (Vector) hash.get("y5Vec" + rowId.get(i));
		    Vector y6 = (Vector) hash.get("y6Vec" + rowId.get(i));
		    Vector y7 = (Vector) hash.get("y7Vec" + rowId.get(i));
		    Vector y8 = (Vector) hash.get("y8Vec" + rowId.get(i));
		    Vector y9 = (Vector) hash.get("y9Vec" + rowId.get(i));
		    Vector y10 = (Vector) hash.get("y10Vec" + rowId.get(i));
		    Vector usage = (Vector) hash.get("usageVec" + rowId.get(i));
		    Vector optionRate = (Vector) hash.get("optionRateVec" + rowId.get(i));
		    if (optOid != null && optOid.size() > 0) {
			// Kogger.debug(getClass(), "######################### ModelInfo Start
			// ************");
			for (int j = 0; j < optOid.size(); j++) {
			    // Kogger.debug(getClass(), "################## optOid :" +optOid.get(i));
			    if (optOid.get(j) != null && ((String) optOid.get(j)).length() > 0) {
				ModelInfo mi = ModelInfo.newModelInfo();
				mi.setYear1((String) y1.get(j));
				mi.setYear2((String) y2.get(j));
				mi.setYear3((String) y3.get(j));
				mi.setYear4((String) y4.get(j));
				mi.setYear5((String) y5.get(j));
				mi.setYear6((String) y6.get(j));
				mi.setYear7((String) y7.get(j));
				mi.setYear8((String) y8.get(j));
				mi.setYear9((String) y9.get(j));
				mi.setYear10((String) y10.get(j));
				mi.setUsage((String) usage.get(j));
				// mi.setOptionRate((String) optionRate.get(j));
				mi.setProduct(pi);
				// Kogger.debug(getClass(), (String)optOid.get(j));
				if (!"nodata".equals((String) optOid.get(j)) && !"0".equals((String) optOid.get(j))) {
				    OEMProjectType OEM = (OEMProjectType) CommonUtil.getObject((String) optOid.get(j));
				    if (OEM != null) {
					mi.setModel((OEMProjectType) CommonUtil.getObject((String) optOid.get(j)));
				    } else {
					mi.setName((String) carName.get(i));
				    }
				} else {
				    mi.setName((String) carName.get(i));
				}

				mi = (ModelInfo) PersistenceHelper.manager.save(mi);
			    }
			}
			// Kogger.debug(getClass(), "######################### ModelInfo end
			// ************");
		    }
		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			// Kogger.debug(getClass(), "######################### ModelInfo Update");
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(ModelInfo.class, true);

			SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=",
			        CommonUtil.getOIDLongValue(pOid));
			qs.appendWhere(sc, new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(qs);
			while (qr.hasMoreElements()) {
			    Object o[] = (Object[]) qr.nextElement();
			    ModelInfo mi = (ModelInfo) o[0];

			    ModelInfo _mi = ModelInfo.newModelInfo();
			    if (mi.getModel() != null) {
				_mi.setModel((OEMProjectType) CommonUtil.getObject(mi.getModel().getPersistInfo().getObjectIdentifier()
				        .toString()));
			    } else {
				_mi.setName(mi.getName());
			    }
			    _mi.setYear1(mi.getYear1());
			    _mi.setYear2(mi.getYear2());
			    _mi.setYear3(mi.getYear3());
			    _mi.setYear4(mi.getYear4());
			    _mi.setYear5(mi.getYear5());
			    _mi.setYear6(mi.getYear6());
			    _mi.setYear7(mi.getYear7());
			    _mi.setYear8(mi.getYear8());
			    _mi.setYear9(mi.getYear9());
			    _mi.setYear10(mi.getYear10());
			    _mi.setUsage(mi.getUsage());
			    _mi.setOptionRate(mi.getOptionRate());
			    _mi.setProduct(pi);

			    _mi = (ModelInfo) PersistenceHelper.manager.save(_mi);
			}
			ProductInfo oldProductInfo = (ProductInfo) CommonUtil.getObject(pOid);
			if (oldProductInfo != null && oldProductInfo.getProject().getPjtNo().equals(project.getPjtNo())) {
			    PersistenceHelper.manager.delete(oldProductInfo);
			}
		    }
		}
		// Kogger.debug(getClass(), "######################### ProductInfo END
		// #################################");
	    }
	    // ########################################################################################################
	    // ??? ???

	    // TemplateProject Link Create
	    if (templateProject != null) {
		ProjectHelper.manager.copyProjectInfo(project, templateProject);
		TaskHelper.manager.copyProjectFromTemplateNew(project, templateProject, category);// 검토 Project인 경우
		ProjectUserHelper.settingRoleTaskMember(project);
	    }

	    transaction.commit();

	    ProjectScheduleHelper.settingProgress(project);

	    // ProductHelper.syncProjectCostIF(project);

	    transaction = null;

	    // 프로젝트 PM에게 메일 발송
	    List<WTUser> toUser = new ArrayList<WTUser>();
	    if (wtdevUser != null) {
		toUser.add(wtdevUser);
		if (toUser.size() > 0) {
		    KETMailHelper.service.sendFormMail("08046", "NoticeMailLine2.html", project, (WTUser) SessionHelper.getPrincipal(),
			    toUser);
		}
	    } else {
		// PM 지정하지 않고 개발담당부서만 입력한 경우 해당 부서팀장에게 메일 발송
		WTUser chiefUser = (WTUser) PeopleHelper.manager.getChiefUser(project.getDevDept());
		toUser.add(chiefUser);
		if (toUser.size() > 0) {
		    KETMailHelper.service.sendFormMail("08122", "NoticeMailLine1.html", project, (WTUser) SessionHelper.getPrincipal(),
			    toUser);
		}
	    }
	    return project;

	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (ParseException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return null;
    }

    public ReviewProject updateReviewProject(Hashtable hash) throws WTException {
	ReviewProject project = null;
	String oid = (String) hash.get("oid");

	// String projectNo = (String) hash.get("projectNo");
	String projectName = (String) hash.get("projectName");
	// String receiptNumber = (String) hash.get("receiptNumber");
	String proposalDate = (String) hash.get("proposalDate");
	String estimateDate = (String) hash.get("estimateDate");

	if (projectName == null) {
	    projectName = "";
	}
	// if(projectNo == null){
	// projectNo = "";
	// }

	Calendar tempCal = Calendar.getInstance();
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    project = (ReviewProject) CommonUtil.getObject(oid);
	    ProjectMaster em = (ProjectMaster) project.getMaster();

	    if (StringUtil.checkString(projectName)) {
		em.setPjtName(projectName);
		project.setPjtName(StringUtil.checkNull(projectName));
		PersistenceHelper.manager.modify(em);
	    }

	    if (StringUtil.checkString(proposalDate)) {
		tempCal.setTime(DateUtil.parseDateStr(proposalDate));
		project.setProposalDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }
	    if (StringUtil.checkString(estimateDate)) {
		tempCal.setTime(DateUtil.parseDateStr(estimateDate));
		project.setEstimateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    // project.setPjtState(1);
	    // project.setPjtType(1);

	    // ????
	    String developenttype = (String) hash.get("developenttype");
	    String producttype = (String) hash.get("producttype");
	    Kogger.debug(getClass(), "==producttype" + producttype);
	    String productTypeLevel2 = (String) hash.get("productTypeLevel2");
	    // String assembledtype = (String) hash.get("assembledtype");
	    String rank = (String) hash.get("rank");

	    if (developenttype != null && developenttype.length() > 0) {
		NumberCode n1 = (NumberCode) NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developenttype);
		project.setDevelopentType(n1);
	    }
	    if (producttype != null && producttype.length() > 0) {
		NumberCode n2 = (NumberCode) CommonUtil.getObject(producttype);
		project.setProductType(n2);
	    }
	    if (productTypeLevel2 != null && productTypeLevel2.length() > 0) {
		project.setProductTypeLevel2(productTypeLevel2);
	    }
	    // if (assembledtype != null && assembledtype.length() > 0) {
	    // NumberCode n3 = (NumberCode) CommonUtil.getObject(assembledtype);
	    // project.setAssembledType(n3);
	    // }
	    if (rank != null && rank.length() > 0) {
		NumberCode n4 = (NumberCode) CommonUtil.getObject(rank);
		// Rank 수정시 관련 모듈 수정 S일때만 업데이트 하도록 한다.
		this.changeRankByProject(project, project.getRank().getName(), n4.getName());
		project.setRank(n4);
	    }
	    // salesUser
	    String salesUser = (String) hash.get("salesUser");
	    WTUser wtsalesUser = null;
	    if (salesUser != null && salesUser.length() != 0) {
		wtsalesUser = (WTUser) CommonUtil.getObject(salesUser);
	    }
	    if (wtsalesUser != null) {
		project.setBusiness(wtsalesUser);
	    }

	    // Development Department
	    // String devDeptOid = (String) hash.get("devDeptOid");
	    // Department depart = null;
	    // if (devDeptOid != null && devDeptOid.length() != 0) {
	    // depart = (Department) CommonUtil.getObject(devDeptOid);
	    // }
	    // if (depart != null) {
	    // project.setDevDept(depart);
	    // }

	    String drNumber = (String) hash.get("drNumber");
	    String drKeyOid = (String) hash.get("drKeyOid");
	    if (drKeyOid != null && drKeyOid.length() > 0) {
		KETDevelopmentRequest dr = (KETDevelopmentRequest) CommonUtil.getObject(drKeyOid);
		project.setDevRequest(dr);
		project.setAttr2(drNumber);
	    }

	    String reviewResult = (String) hash.get("reviewResult");
	    project.setReviewResult(reviewResult);

	    String devUser = (String) hash.get("devUser");
	    WTUser wtdevUser = null;
	    if (devUser != null && devUser.length() != 0) {
		wtdevUser = (WTUser) CommonUtil.getObject(devUser);
		PeopleData peo = new PeopleData(wtdevUser);
		project.setDevDept(peo.department);
	    }

	    // save
	    project = (ReviewProject) PersistenceHelper.manager.save(project);

	    if (hash.get("CUSTOMEREVENT") != null) {
		QueryResult result = null;
		QuerySpec qs = new QuerySpec();
		int index = qs.appendClassList(ProjectCustomereventLink.class, true);
		qs.appendWhere(
		        new SearchCondition(ProjectCustomereventLink.class, "roleBObjectRef.key.id", "=", CommonUtil
		                .getOIDLongValue(project)), new int[] { index });
		result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
		    Object[] object = (Object[]) result.nextElement();
		    ProjectCustomereventLink link = (ProjectCustomereventLink) object[0];
		    if (link != null) {
			PersistenceHelper.manager.delete(link);
		    }
		}

		Vector customereventVec = getParamsVector(hash.get("CUSTOMEREVENT"));
		String customereventoid = null;
		for (int i = 0; i < customereventVec.size(); i++) {
		    customereventoid = (String) customereventVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(customereventoid);
		    if (code != null) {
			ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    if (hash.get("SUBCONTRACTOR") != null) {
		QueryResult result = null;
		QuerySpec qs = new QuerySpec();
		int index = qs.appendClassList(ProjectSubcontractorLink.class, true);
		qs.appendWhere(
		        new SearchCondition(ProjectSubcontractorLink.class, "roleBObjectRef.key.id", "=", CommonUtil
		                .getOIDLongValue(project)), new int[] { index });
		result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
		    Object[] object = (Object[]) result.nextElement();
		    ProjectSubcontractorLink link = (ProjectSubcontractorLink) object[0];
		    if (link != null) {
			PersistenceHelper.manager.delete(link);
		    }
		}

		Vector subcontractorVec = getParamsVector(hash.get("SUBCONTRACTOR"));
		String subcontractoroid = null;
		for (int i = 0; i < subcontractorVec.size(); i++) {
		    subcontractoroid = (String) subcontractorVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(subcontractoroid);
		    if (code != null) {
			ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    // nOidVec
	    if (hash.get("nOidVec") != null) {
		QueryResult result = null;
		QuerySpec qs = new QuerySpec();
		int index = qs.appendClassList(ProjectMasterCodeSubCodeLink.class, true);
		qs.appendWhere(new SearchCondition(ProjectMasterCodeSubCodeLink.class, "projectId", "=", CommonUtil.getOIDString(project)),
		        new int[] { index });
		result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
		    Object[] object = (Object[]) result.nextElement();
		    ProjectMasterCodeSubCodeLink link = (ProjectMasterCodeSubCodeLink) object[0];
		    if (link != null) {
			PersistenceHelper.manager.delete(link);
		    }
		}

		Vector nOidVec = getParamsVector(hash.get("nOidVec"));
		String nOid = null;
		for (int i = 0; i < nOidVec.size(); i++) {
		    nOid = (String) nOidVec.get(i);
		    NumberCode mnc = null;
		    NumberCode snc = null;
		    StringTokenizer token = new StringTokenizer(nOid, "@");
		    String nnOid[] = new String[token.countTokens()];
		    int t = 0;
		    while (token.hasMoreTokens()) {
			nnOid[t] = token.nextToken();
			t++;
		    }
		    String masterCode = nnOid[0];
		    String subCode = nnOid[1];
		    mnc = (NumberCode) CommonUtil.getObject(masterCode);
		    snc = (NumberCode) CommonUtil.getObject(subCode);

		    if (snc != null) {
			ProjectSapSubcontractorLink plink = ProjectSapSubcontractorLink.newProjectSapSubcontractorLink(snc, project);
			PersistenceHelper.manager.save(plink);
		    }

		    if (snc != null && mnc != null) {
			ProjectMasterCodeSubCodeLink plink = ProjectMasterCodeSubCodeLink.newProjectMasterCodeSubCodeLink(snc, mnc);
			plink.setProjectId(CommonUtil.getOIDString(project));
			PersistenceHelper.manager.save(plink);

		    }
		}
	    }

	    // ProductInfo
	    /*
	     * String deletePOid = (String)hash.get("deletePOid"); if(deletePOid != null){ StringTokenizer deletePOids = new
	     * StringTokenizer(deletePOid, "@"); while(deletePOids.hasMoreTokens()) { String delPOid = deletePOids.nextToken();
	     * if(delPOid.length() > 0) { ProductInfo delProductInfo = (ProductInfo)CommonUtil.getObject(delPOid);
	     * PersistenceHelper.manager.delete(delProductInfo); // ProductHelper.deleteCostIF(delProductInfo); } } }
	     */

	    Vector rowId = (Vector) hash.get("rowIdVec");
	    // Vector pNum = (Vector) hash.get("pNumVec");
	    Vector pName = (Vector) hash.get("pNameVec");
	    // Vector areas = (Vector) hash.get("areasVec");
	    Vector usageT = (Vector) hash.get("usageTVec");
	    Vector price = (Vector) hash.get("priceVec");
	    Vector cost = (Vector) hash.get("costVec");
	    Vector rate = (Vector) hash.get("rateVec");
	    Vector y1T = (Vector) hash.get("y1TVec");
	    Vector y2T = (Vector) hash.get("y2TVec");
	    Vector y3T = (Vector) hash.get("y3TVec");
	    Vector y4T = (Vector) hash.get("y4TVec");
	    Vector y5T = (Vector) hash.get("y5TVec");
	    Vector y6T = (Vector) hash.get("y6TVec");
	    Vector y7T = (Vector) hash.get("y7TVec");
	    Vector y8T = (Vector) hash.get("y8TVec");
	    Vector y9T = (Vector) hash.get("y9TVec");
	    Vector y10T = (Vector) hash.get("y10TVec");
	    Vector carName = (Vector) hash.get("carNameVec");
	    Vector assemblyPlaceType = (Vector) hash.get("assemblyPlaceTypeVec"); // 조립처 구분(사내/외주)
	    Vector assemblyPlace = (Vector) hash.get("assemblyPlaceVec");// 조립처
	    Vector assembledType = (Vector) hash.get("assembledTypeVec");// 조립구분

	    if (rowId != null && rowId.size() > 0) {
		for (int i = 0; i < rowId.size(); i++) {
		    ProductInfo pi = ProductInfo.newProductInfo();
		    // if (pNum.get(i) != null) {
		    // pi.setPNum((String) pNum.get(i));
		    // }
		    pi.setPName((String) pName.get(i));
		    // pi.setAreas((String) areas.get(i));
		    if (usageT != null && usageT.size() > 0) {
			pi.setUsage((String) usageT.get(i));
		    }
		    // pi.setPrice((String) price.get(i));
		    // pi.setCost((String) cost.get(i));
		    // pi.setRate((String) rate.get(i));

		    String pOid = (String) hash.get("pOidVec" + rowId.get(i));

		    Kogger.debug(getClass(), "###########   pOid  ===		//" + pOid + "//	######");

		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			ProductInfo pii = (ProductInfo) CommonUtil.getObject(pOid);

			pi.setYear1(pii.getYear1());
			pi.setYear2(pii.getYear2());
			pi.setYear3(pii.getYear3());
			pi.setYear4(pii.getYear4());
			pi.setYear5(pii.getYear5());
			pi.setYear6(pii.getYear6());
			pi.setYear7(pii.getYear7());
			pi.setYear8(pii.getYear8());
			pi.setYear9(pii.getYear9());
			pi.setYear10(pii.getYear10());

			if (pii.getProject().getPjtNo().equals(project.getPjtNo())) {
			    Kogger.debug(getClass(), "############     ??? ?????? ???????????????????????????????");
			    pi.setSeqNum(pii.getSeqNum());
			} else {
			    Kogger.debug(getClass(), "############     ??? ?????? ???????????????????????????????");
			    pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));
			}

		    } else {
			Kogger.debug(getClass(), "############     ??????????????");
			pi.setYear1((String) y1T.get(i));
			pi.setYear2((String) y2T.get(i));
			pi.setYear3((String) y3T.get(i));
			pi.setYear4((String) y4T.get(i));
			pi.setYear5((String) y5T.get(i));
			pi.setYear6((String) y6T.get(i));
			pi.setYear7((String) y7T.get(i));
			pi.setYear8((String) y8T.get(i));
			pi.setYear9((String) y9T.get(i));
			pi.setYear10((String) y10T.get(i));

			pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));

		    }

		    pi.setProject(project);
		    if (StringUtil.checkString((String) assembledType.get(i))) {
			pi.setAssembledType((NumberCode) CommonUtil.getObject((String) assembledType.get(i)));// 조립구분
		    }
		    if (StringUtil.checkString((String) assemblyPlaceType.get(i))) {
			pi.setAssemblyPlaceType((String) assemblyPlaceType.get(i));
			if (((String) assemblyPlaceType.get(i)).equals("외주")) {
			    pi.setAssemblyPartnerNo((String) assemblyPlace.get(i));
			} else {
			    pi.setAssemblyPlace((NumberCode) CommonUtil.getObject((String) assemblyPlace.get(i)));
			}
		    }
		    pi = (ProductInfo) PersistenceHelper.manager.save(pi);

		    Vector count = (Vector) hash.get("countVec" + rowId.get(i));
		    Vector optOid = (Vector) hash.get("optOidVec" + rowId.get(i));
		    Vector y1 = (Vector) hash.get("y1Vec" + rowId.get(i));
		    Vector y2 = (Vector) hash.get("y2Vec" + rowId.get(i));
		    Vector y3 = (Vector) hash.get("y3Vec" + rowId.get(i));
		    Vector y4 = (Vector) hash.get("y4Vec" + rowId.get(i));
		    Vector y5 = (Vector) hash.get("y5Vec" + rowId.get(i));
		    Vector y6 = (Vector) hash.get("y6Vec" + rowId.get(i));
		    Vector y7 = (Vector) hash.get("y7Vec" + rowId.get(i));
		    Vector y8 = (Vector) hash.get("y8Vec" + rowId.get(i));
		    Vector y9 = (Vector) hash.get("y9Vec" + rowId.get(i));
		    Vector y10 = (Vector) hash.get("y10Vec" + rowId.get(i));
		    Vector usage = (Vector) hash.get("usageVec" + rowId.get(i));
		    Vector optionRate = (Vector) hash.get("optionRateVec" + rowId.get(i));

		    if (optOid != null && optOid.size() > 0) {
			for (int j = 0; j < optOid.size(); j++) {
			    if (optOid.get(j) != null && ((String) optOid.get(j)).length() > 0) {
				ModelInfo mi = ModelInfo.newModelInfo();
				mi.setYear1((String) y1.get(j));
				mi.setYear2((String) y2.get(j));
				mi.setYear3((String) y3.get(j));
				mi.setYear4((String) y4.get(j));
				mi.setYear5((String) y5.get(j));
				mi.setYear6((String) y6.get(j));
				mi.setYear7((String) y7.get(j));
				mi.setYear8((String) y8.get(j));
				mi.setYear9((String) y9.get(j));
				mi.setYear10((String) y10.get(j));
				mi.setUsage((String) usage.get(j));
				// mi.setOptionRate((String) optionRate.get(j));
				mi.setProduct(pi);

				if (!"nodata".equals((String) optOid.get(j)))
				    mi.setModel((OEMProjectType) CommonUtil.getObject((String) optOid.get(j)));
				else
				    mi.setName((String) carName.get(i));

				mi = (ModelInfo) PersistenceHelper.manager.save(mi);
			    }
			}
		    }
		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(ModelInfo.class, true);

			SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=",
			        CommonUtil.getOIDLongValue(pOid));
			qs.appendWhere(sc, new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(qs);
			Kogger.debug(getClass(), "::::::::::::::::::::::: qr size() == " + qr.size());
			/* 20130220 shkim add ??? ?????? ??? ??? ??? ??? */
			if (optOid == null) {
			    while (qr.hasMoreElements()) {
				Object o[] = (Object[]) qr.nextElement();
				ModelInfo mi = (ModelInfo) o[0];

				ModelInfo _mi = ModelInfo.newModelInfo();

				int _miCount = 0;

				if (mi.getModel() != null) {
				    Kogger.debug(getClass(), "::::::::::::::::::::::::::::::: mi get model true ::::::::::::::::::::::");
				    // _mi.setModel((OEMProjectType)CommonUtil.getObject(mi.getModel().getPersistInfo().getObjectIdentifier().toString()));
				    _mi.setModel(mi.getModel());
				} else {
				    Kogger.debug(getClass(), "::::::::::::::::::::::::::::::: mi get model false ::::::::::::::::::");
				    _mi.setName(mi.getName());
				}
				_mi.setYear1(mi.getYear1());
				_mi.setYear2(mi.getYear2());
				_mi.setYear3(mi.getYear3());
				_mi.setYear4(mi.getYear4());
				_mi.setYear5(mi.getYear5());
				_mi.setYear6(mi.getYear6());
				_mi.setYear7(mi.getYear7());
				_mi.setYear8(mi.getYear8());
				_mi.setYear9(mi.getYear9());
				_mi.setYear10(mi.getYear10());
				_mi.setUsage(mi.getUsage());
				_mi.setOptionRate(mi.getOptionRate());
				_mi.setProduct(pi);

				_mi = (ModelInfo) PersistenceHelper.manager.save(_mi);

				_miCount++;

			    }
			}

			ProductInfo oldProductInfo = (ProductInfo) CommonUtil.getObject(pOid);
			if (oldProductInfo != null && oldProductInfo.getProject().getPjtNo().equals(project.getPjtNo())) {
			    PersistenceHelper.manager.delete(oldProductInfo);
			}

		    }
		}
	    }

	    String deletePOid = (String) hash.get("deletePOid");
	    if (deletePOid != null) {
		StringTokenizer deletePOids = new StringTokenizer(deletePOid, "@");
		while (deletePOids.hasMoreTokens()) {
		    String delPOid = deletePOids.nextToken();
		    if (delPOid.length() > 0) {
			ProductInfo delProductInfo = (ProductInfo) CommonUtil.getObject(delPOid);
			if (delProductInfo != null) {
			    PersistenceHelper.manager.delete(delProductInfo);
			}
			// ProductHelper.deleteCostIF(delProductInfo);
		    }
		}
	    }

	    // PM 수정
	    WTUser afterPM = ProjectUserHelper.manager.getPM(project);
	    //
	    String flag = (String) hash.get("prodModifyFlag");

	    if (!"Y".equals(flag)) {
		if (afterPM != wtdevUser) {
		    ProjectUserHelper.manager.replacePM(project, wtdevUser);
		}
	    }
	    // if(wtdevUser != null){
	    // ProjectUserHelper.manager.setDEVELOPMENTMANAGER(project, wtdevUser, 0); }

	    // ProductHelper.syncProjectCostIF(project);

	    transaction.commit();
	    transaction = null;
	    return project;
	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return project;
    }

    /**
     * ?????: createProductProject ?????? :
     * 
     * @param hash
     * @return
     * @throws WTException
     *             ?????: ????? ?????? : 2013. 7. 4.
     */
    public ProductProject createProductProject(Hashtable hash) throws WTException {

	// Project Attribute
	String projectType = (String) hash.get("projectType");
	String projectNo = (String) hash.get("projectNo"); // ???? NO
	String projectName = (String) hash.get("projectName"); // ???? ?
	String planStartDate = (String) hash.get("planStartDate"); // ?? ????
	String planEndDate = (String) hash.get("planEndDate"); // ?? ????
	String projectDesc = (String) hash.get("projectDesc"); // ???? ??
	String projectState = (String) hash.get("projectState"); // ???? ??(?? ?/??? ?)
	String reviewPjtNo[] = (String[]) hash.get("reviewPjtNo");

	// Template
	String tempid = (String) hash.get("TemplateOID"); // Template OID
	String wbsType = (String) hash.get("wbsType"); // wbsType
	String[] category = (String[]) hash.get("category"); // category

	String lifecycle = (String) hash.get("lifecycle");

	if (lifecycle == null || lifecycle.equals("")) {
	    lifecycle = "KET_PRODUCT_PMS_LC";
	}

	String pmoid = (String) hash.get("pmoid");

	Calendar tempCal = Calendar.getInstance();

	ProductProject project = null;
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    project = ProductProject.newProductProject();

	    /*
	     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ExtendScheduleData Object Create begin
	     * 
	     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	     */
	    // 1. ExtendScheduleData Object Create
	    ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();

	    // 1.1 Duration Setting
	    if (StringUtil.checkString(planStartDate.trim()) && StringUtil.checkString(planEndDate.trim())) {
		int tempDuration = DateUtil.getDaysFromTo(planEndDate, planStartDate);
		schedule.setDuration(tempDuration);
	    }
	    // 1.2 ScheduleHistory (0: ????)
	    schedule.setScheduleHistory(0);

	    // 1.3 ?? ???? & ?? ???? Setting
	    if (StringUtil.checkString(planStartDate.trim())) {
		tempCal.setTime(DateUtil.parseDateStr(planStartDate));
		// 1.3.1 ?? ???? [PLM Attribute]
		schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		// 1.3.2 ?? ???? [PLM Attribute]
		// schedule.setExecStartDate(new
		// java.sql.Timestamp(tempCal.getTime().getTime()));
	    }

	    // 1.5 ? ??
	    schedule.setExecWork(0);

	    // 1.6 ExtendScheduleData Object Save
	    schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
	    /*
	     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ExtendScheduleData Object Create end
	     * 
	     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	     */

	    /*
	     * **********************************************************************
	     * Project Object Create begin **********************************************************************
	     */
	    // 2. Project Object Create

	    String teamType = (String) hash.get("teamType"); // ???
	    project.setTeamType(teamType);
	    String itDivision = (String) hash.get("itDivision"); // ???
	    project.setItDivision(itDivision);

	    String partNo = (String) hash.get("partNo"); // Part No
	    project.setPartNo("미입력");

	    String location = e3ps.common.folder.FolderUtil.getPersonalCabinet().getFolderPath();
	    String folderPath = ConfigImpl.getInstance().getString("folder.project");
	    // if(lifecycle.equals("LC_Project")) {
	    FolderHelper.assignLocation((FolderEntry) project, FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef()));
	    LifeCycleHelper.setLifeCycle(project, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef()));
	    // } else {
	    // FolderHelper.assignLocation((FolderEntry)project,
	    // FolderHelper.service.getFolder(location, WCUtil.getWTContainerRef()));
	    // LifeCycleHelper.setLifeCycle(project,
	    // LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,WCUtil.getWTContainerRef()));
	    // }

	    // 2.1 ???? NO Setting
	    project.setPjtNo(projectNo.trim());

	    // 2.2 ???? ?? Setting
	    project.setPjtSeq(0);

	    // 2.3 ???? ?? (0: ????)
	    project.setPjtHistory(0);
	    // [START] [PLM System 1????? Project ??? Iteration ??? ???, 2013-07-04, BoLee
	    project.setPjtIteration(0);
	    // [END] [PLM System 1????? Project ??? Iteration ??? ???, 2013-07-04, BoLee

	    // 2.4 ???? ???
	    project.setPjtCompletion(0);

	    // 2.5 ?? ???? Check
	    project.setLastest(true); // List ? ???? (True)

	    // 2.6 ???? ??
	    project.setPjtSchedule(ObjectReference.newObjectReference(schedule));

	    // 2.7 ???
	    TemplateProject templateProject = null;
	    if (tempid != null && tempid.length() > 0) {
		// 2.7.1 ??? ??
		templateProject = (TemplateProject) CommonUtil.getObject(tempid);
		project.setTemplateCode(templateProject.getPjtNo());
		// TaskHelper.manager.copyTaskInfo(jelProject ,templateProject);
		// 2.7.2 ??? ?? ??
		// Kogger.debug(getClass(), "########### templateProject.getOutputType() = " +
		// templateProject.getOutputType().getName());
		// project.setOutputType(templateProject.getOutputType());
	    }

	    // 2.8 ???? ?
	    if (StringUtil.checkString(projectName.trim()))
		project.setPjtName(StringUtil.checkNull(projectName.trim()));

	    // 2.9 ???? ??
	    if (StringUtil.checkString(projectDesc.trim()))
		project.setPjtDesc(projectDesc.trim());

	    // 2.10 ???? ?? [PLM Attribute]
	    if (StringUtil.checkString(projectState) && projectState.equalsIgnoreCase("READY")) {
		project.setPjtState(ProjectStateHelper.manager.READY);
	    } else {
		project.setPjtState(ProjectStateHelper.manager.PROGRESS);
	    }

	    // 2.11 ???? [PLM Attribute]
	    String productType = (String) hash.get("productType");
	    if (productType != null && productType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("PRODUCTTYPE", productType);
		project.setProductType(code);
	    }

	    // 2.12 ??? [PLM Attribute]
	    String rank = (String) hash.get("rank");
	    if (rank != null && rank.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("RANK", rank);
		project.setRank(code);
	    }

	    String model = (String) hash.get("model");
	    if (model != null && model.length() > 0) {
		OEMProjectType modelType = (OEMProjectType) CommonUtil.getObject((String) model);
		if (modelType != null) {
		    project.setOemType(modelType);
		}
	    }

	    // 2.13 ??? [PLM Attribute]
	    String assembledType = (String) hash.get("assembledType");
	    if (assembledType != null && assembledType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("ASSEMBLEDTYPE", assembledType);
		project.setAssembledType(code);
	    }

	    String process = (String) hash.get("process");
	    if (process != null && process.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("PROCESS", process);
		project.setProcess(code);
	    }

	    // 2.14 ??? [PLM Attribute]
	    String developentType = (String) hash.get("developentType");
	    if (developentType != null && developentType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developentType);
		project.setDevelopentType(code);
	    }

	    // 2.15 ???? [PLM Attribute]
	    String designType = (String) hash.get("designType");
	    if (designType != null && designType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DESIGNTYPE", designType);
		project.setDesignType(code);
	    }

	    String devPattern = (String) hash.get("devPattern");// 개발유형
	    project.setDevelopedType(devPattern);

	    // 2.16 ???? [PLM Attribute]
	    String proteamNo = (String) hash.get("proteamNo");
	    if (proteamNo != null && proteamNo.length() > 0) {
		if (proteamNo.indexOf("NumberCode") > -1) {
		    NumberCode code = (NumberCode) CommonUtil.getObject(proteamNo);
		    project.setAssemblyPlace(code);
		} else {
		    project.setPartnerNo(proteamNo);
		}
	    }

	    // 2.18 ???? ?? [2: ??/??, 1: ??]
	    // Kogger.debug(getClass(), "################Service[projectType]>>>>>
	    // "+projectType+"<<<<<<<########");
	    project.setPjtType(Integer.parseInt(projectType));

	    if (pmoid != null) {
		ProjectManager pm = (ProjectManager) CommonUtil.getObject(pmoid);
		// jelProject.setManager(pm);
	    }

	    String costsDate = (String) hash.get("costsDate");
	    if (costsDate != null && costsDate.length() > 0)
		project.setCostsDate(DateUtil.getTimestampFormat(costsDate, ConfigImpl.getInstance().getString("date.format")));

	    // ??? ??? ?????I/F
	    String drNumber = (String) hash.get("drNumber");
	    String drKeyOid = (String) hash.get("drKeyOid");
	    if (drKeyOid != null && drKeyOid.length() > 0) {
		KETDevelopmentRequest dr = (KETDevelopmentRequest) CommonUtil.getObject(drKeyOid);
		project.setDevRequest(dr);
		project.setDevRequestNo(drNumber);
	    }

	    if (reviewPjtNo != null && reviewPjtNo.length > 0) {
		String reviewPjtNos = "";
		for (String pjtNo : reviewPjtNo) {

		    reviewPjtNos += pjtNo + ",";
		}
		reviewPjtNos = StringUtils.removeEnd(reviewPjtNos, ",");
		project.setReviewPjtNo(reviewPjtNos);
	    }

	    String sales = (String) hash.get("sales"); // ?????
	    if (sales != null && sales.length() > 0) {
		WTUser salesuser = (WTUser) CommonUtil.getObject(sales);
		if (salesuser != null) {
		    project.setBusiness(salesuser);
		}
	    }

	    String developePurpose1 = (String) hash.get("developePurpose1");
	    if (developePurpose1 != null && developePurpose1.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL", developePurpose1);
		project.setMainGoalType(code);
	    }
	    String developePurpose2 = (String) hash.get("developePurpose2");

	    if (developePurpose2 != null && developePurpose2.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPANDREVIEWGOAL", developePurpose2);
		project.setSubGoalType(code);
	    }

	    String manageProduct = (String) hash.get("manageProduct");
	    if (manageProduct != null && manageProduct.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("MANAGEPRODUCTTYPE", manageProduct);
		project.setManageProductType(code);
	    }

	    String obtainType = (String) hash.get("obtainType");
	    if (obtainType != null && obtainType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("OBTAINORDERTYPE", obtainType);
		project.setObtainType(code);
	    }

	    // 2.19 Project Save
	    project = (ProductProject) PersistenceHelper.manager.save(project);

	    project = (ProductProject) PersistenceHelper.manager.refresh(project);

	    String linkpjtoid = (String) hash.get("linkpjtoid");
	    if (linkpjtoid != null && linkpjtoid.length() > 0) {
		E3PSProject linkProject = (E3PSProject) CommonUtil.getObject(linkpjtoid);
		if (linkProject != null) {
		    E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

		    master.setLinkProjectNo(linkProject.getPjtNo());
		    PersistenceHelper.manager.save(master);

		    E3PSProjectMaster linkMaster = (E3PSProjectMaster) linkProject.getMaster();
		    String pjtNos = linkMaster.getLinkProjectNo();
		    if (!StringUtils.contains(pjtNos, master.getPjtNo())) {
			if (StringUtils.isEmpty(pjtNos)) {
			    linkMaster.setLinkProjectNo(master.getPjtNo());
			} else {
			    linkMaster.setLinkProjectNo(pjtNos + "," + master.getPjtNo());
			}

			PersistenceHelper.manager.save(linkMaster);
		    }
		}
	    }

	    Vector customer = (Vector) hash.get("customer");
	    if (customer != null && customer.size() > 0) {
		for (int i = 0; i < customer.size(); i++) {
		    NumberCode code = (NumberCode) CommonUtil.getObject((String) customer.get(i));
		    if (code != null) {
			ProjectCustomereventLink link = ProjectCustomereventLink.newProjectCustomereventLink(code, project);
			link = (ProjectCustomereventLink) PersistenceHelper.manager.save(link);
		    }
		}
	    }

	    // SUBCONTRACTOR
	    if (hash.get("SUBCONTRACTOR") != null) {
		Vector subcontractorVec = getParamsVector(hash.get("SUBCONTRACTOR"));
		String subcontractoroid = null;
		for (int i = 0; i < subcontractorVec.size(); i++) {
		    subcontractoroid = (String) subcontractorVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(subcontractoroid);
		    if (code != null) {
			ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    String productTypeLevel2 = (String) hash.get("productTypeLevel2"); // productTypeLevel2
	    if (productTypeLevel2 != null && productTypeLevel2.length() > 0) {
		project.setProductTypeLevel2(productTypeLevel2);
	    }

	    String sealed = (String) hash.get("sealed"); // Sealed
	    if (sealed != null && sealed.length() > 0) {
		project.setWaterPoof(sealed);
	    }

	    String series = (String) hash.get("series"); // Series
	    if (series != null && series.length() > 0) {
		project.setSeries(series);
	    }

	    // nOidVec
	    if (hash.get("nOidVec") != null) {
		Vector nOidVec = getParamsVector(hash.get("nOidVec"));
		String nOid = null;
		// Kogger.debug(getClass(),
		// "###################################################### ??? ??? ??");
		for (int i = 0; i < nOidVec.size(); i++) {
		    nOid = (String) nOidVec.get(i);
		    NumberCode mnc = null;
		    NumberCode snc = null;
		    StringTokenizer token = new StringTokenizer(nOid, "@");
		    String nnOid[] = new String[token.countTokens()];
		    int t = 0;
		    while (token.hasMoreTokens()) {
			nnOid[t] = token.nextToken();
			t++;
		    }
		    String masterCode = nnOid[0];
		    String subCode = nnOid[1];
		    mnc = (NumberCode) CommonUtil.getObject(masterCode);
		    snc = (NumberCode) CommonUtil.getObject(subCode);
		    // Kogger.debug(getClass(), "???==>" + mnc.getName() + " ???==>" +
		    // snc.getName());

		    // ???
		    if (snc != null) {
			ProjectSapSubcontractorLink plink = ProjectSapSubcontractorLink.newProjectSapSubcontractorLink(snc, project);
			PersistenceHelper.manager.save(plink);
		    }

		    // ??? ??? Link
		    if (snc != null && mnc != null) {
			ProjectMasterCodeSubCodeLink plink = ProjectMasterCodeSubCodeLink.newProjectMasterCodeSubCodeLink(snc, mnc);
			plink.setProjectId(CommonUtil.getOIDString(project));
			PersistenceHelper.manager.save(plink);

		    }
		}
		// Kogger.debug(getClass(),
		// "###################################################### ??? ??? ??");
	    }

	    // Item ??
	    Vector itemType = (Vector) hash.get("itemType");
	    Vector moldProductType = (Vector) hash.get("moldProductType");
	    Vector moldPartNo = (Vector) hash.get("moldPartNo");
	    Vector partName = (Vector) hash.get("partName");
	    Vector dieNo = (Vector) hash.get("dieNo");
	    Vector moldType = (Vector) hash.get("moldType");
	    Vector cVPitch = (Vector) hash.get("cVPitch");
	    Vector cTSPM = (Vector) hash.get("cTSPM");
	    Vector making = (Vector) hash.get("making");
	    Vector productionPlace = (Vector) hash.get("productionPlace");
	    Vector productionPlace2 = (Vector) hash.get("productionPlace2");
	    Vector materials = (Vector) hash.get("materials");
	    Vector poidvalue = (Vector) hash.get("poidvalue");
	    Vector height = (Vector) hash.get("height");
	    Vector wide = (Vector) hash.get("wide");
	    Vector shrinkage = (Vector) hash.get("shrinkage");
	    Vector etc = (Vector) hash.get("etc");

	    if (moldPartNo != null && moldPartNo.size() > 0) {
		for (int i = 0; i < moldPartNo.size(); i++) {
		    MoldItemInfo moldItemInfo = MoldItemInfo.newMoldItemInfo();
		    moldItemInfo.setItemType((String) itemType.get(i));

		    NumberCode code = null;
		    String moldProductTypeValue = (String) moldProductType.get(i);
		    if (moldProductTypeValue.indexOf("NumberCode") > -1) {
			code = (NumberCode) CommonUtil.getObject(moldProductTypeValue);
			if (code != null)
			    moldItemInfo.setProductType(code);
		    }
		    moldItemInfo.setPartNo((String) moldPartNo.get(i));
		    moldItemInfo.setPartName((String) partName.get(i));
		    moldItemInfo.setDieNo((String) dieNo.get(i));
		    code = NumberCodeHelper.manager.getNumberCode("MOLDTYPE", (String) moldType.get(i));
		    if (code != null)
			moldItemInfo.setMoldType(code);
		    moldItemInfo.setCVPitch((String) cVPitch.get(i));
		    moldItemInfo.setCTSPM((String) cTSPM.get(i));
		    moldItemInfo.setMaking((String) making.get(i));
		    if (productionPlace.get(i) != null) {
			moldItemInfo.setProductionPlace((String) productionPlace.get(i));
		    }
		    String productionPlace2Value = (String) productionPlace2.get(i);
		    if (productionPlace2Value != null && productionPlace2Value.length() > 0) {
			if (productionPlace2Value.indexOf("NumberCode") > -1) {
			    code = (NumberCode) CommonUtil.getObject(productionPlace2Value);
			    if (code != null)
				moldItemInfo.setPurchasePlace(code);
			} else {
			    moldItemInfo.setPartnerNo(productionPlace2Value);
			}
		    }

		    if (materials.get(i) != null && ((String) materials.get(i)).length() > 0) {
			MoldMaterial mMaterial = (MoldMaterial) CommonUtil.getObject((String) materials.get(i));
			if (mMaterial != null)
			    moldItemInfo.setMaterial(mMaterial);
		    }
		    if (poidvalue.get(i) != null && ((String) poidvalue.get(i)).length() > 0) {
			code = (NumberCode) CommonUtil.getObject((String) poidvalue.get(i));
			if (code != null)
			    moldItemInfo.setProperty(code);
		    }
		    if (height.get(i) != null && ((String) height.get(i)).length() > 0)
			moldItemInfo.setThickness((String) height.get(i));
		    if (wide.get(i) != null && ((String) wide.get(i)).length() > 0)
			moldItemInfo.setWidth((String) wide.get(i));
		    if (shrinkage.get(i) != null && ((String) shrinkage.get(i)).length() > 0)
			moldItemInfo.setShrinkage((String) shrinkage.get(i));
		    if (etc.get(i) != null && ((String) etc.get(i)).length() > 0)
			moldItemInfo.setEtc((String) etc.get(i));

		    moldItemInfo.setProject(project);
		    moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
		}
	    }

	    // ProductInfo
	    Vector rowId = (Vector) hash.get("rowIdVec");
	    Vector pNum = (Vector) hash.get("pNumVec");
	    Vector reviewProjectNo = (Vector) hash.get("reviewProjectNoVec");
	    Vector reviewSeqNo = (Vector) hash.get("reviewSeqNoVec");
	    Vector pName = (Vector) hash.get("pNameVec");
	    Vector areas = (Vector) hash.get("areasVec");
	    Vector usageT = (Vector) hash.get("usageTVec");
	    Vector price = (Vector) hash.get("priceVec");
	    Vector cost = (Vector) hash.get("costVec");
	    Vector rate = (Vector) hash.get("rateVec");
	    Vector y1T = (Vector) hash.get("y1TVec");
	    Vector y2T = (Vector) hash.get("y2TVec");
	    Vector y3T = (Vector) hash.get("y3TVec");
	    Vector y4T = (Vector) hash.get("y4TVec");
	    Vector y5T = (Vector) hash.get("y5TVec");
	    Vector y6T = (Vector) hash.get("y6TVec");
	    Vector y7T = (Vector) hash.get("y7TVec");
	    Vector y8T = (Vector) hash.get("y8TVec");
	    Vector y9T = (Vector) hash.get("y9TVec");
	    Vector y10T = (Vector) hash.get("y10TVec");
	    Vector carName = (Vector) hash.get("carNameVec");

	    if (rowId != null && rowId.size() > 0) {
		for (int i = 0; i < rowId.size(); i++) {
		    ProductInfo pi = ProductInfo.newProductInfo();
		    if (pNum.get(i) != null) {
			pi.setPNum((String) pNum.get(i));
		    }
		    if (reviewProjectNo != null && reviewProjectNo.get(i) != null && ((String) reviewProjectNo.get(i)).length() > 0)
			pi.setReviewProjectNo((String) reviewProjectNo.get(i));
		    if (reviewSeqNo != null && reviewSeqNo.get(i) != null && ((String) reviewSeqNo.get(i)).length() > 0)
			pi.setReviewSeqNo((String) reviewSeqNo.get(i));
		    pi.setPName((String) pName.get(i));
		    pi.setAreas((String) areas.get(i));
		    pi.setUsage((String) usageT.get(i));
		    pi.setPrice((String) price.get(i));
		    pi.setCost((String) cost.get(i));
		    pi.setRate((String) rate.get(i));

		    String pOid = (String) hash.get("pOidVec" + rowId.get(i));

		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			ProductInfo pii = (ProductInfo) CommonUtil.getObject(pOid);
			pi.setYear1(pii.getYear1());
			pi.setYear2(pii.getYear2());
			pi.setYear3(pii.getYear3());
			pi.setYear4(pii.getYear4());
			pi.setYear5(pii.getYear5());
			pi.setYear6(pii.getYear6());
			pi.setYear7(pii.getYear7());
			pi.setYear8(pii.getYear8());
			pi.setYear9(pii.getYear9());
			pi.setYear10(pii.getYear10());
		    } else {
			pi.setYear1((String) y1T.get(i));
			pi.setYear2((String) y2T.get(i));
			pi.setYear3((String) y3T.get(i));
			pi.setYear4((String) y4T.get(i));
			pi.setYear5((String) y5T.get(i));
			pi.setYear6((String) y6T.get(i));
			pi.setYear7((String) y7T.get(i));
			pi.setYear8((String) y8T.get(i));
			pi.setYear9((String) y9T.get(i));
			pi.setYear10((String) y10T.get(i));
		    }

		    pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));
		    pi.setProject(project);
		    pi = (ProductInfo) PersistenceHelper.manager.save(pi);

		    Vector count = (Vector) hash.get("countVec" + rowId.get(i));
		    Vector optOid = (Vector) hash.get("optOidVec" + rowId.get(i));
		    Vector y1 = (Vector) hash.get("y1Vec" + rowId.get(i));
		    Vector y2 = (Vector) hash.get("y2Vec" + rowId.get(i));
		    Vector y3 = (Vector) hash.get("y3Vec" + rowId.get(i));
		    Vector y4 = (Vector) hash.get("y4Vec" + rowId.get(i));
		    Vector y5 = (Vector) hash.get("y5Vec" + rowId.get(i));
		    Vector y6 = (Vector) hash.get("y6Vec" + rowId.get(i));
		    Vector y7 = (Vector) hash.get("y7Vec" + rowId.get(i));
		    Vector y8 = (Vector) hash.get("y8Vec" + rowId.get(i));
		    Vector y9 = (Vector) hash.get("y9Vec" + rowId.get(i));
		    Vector y10 = (Vector) hash.get("y10Vec" + rowId.get(i));
		    Vector usage = (Vector) hash.get("usageVec" + rowId.get(i));
		    Vector optionRate = (Vector) hash.get("optionRateVec" + rowId.get(i));

		    if (optOid != null && optOid.size() > 0) {
			for (int j = 0; j < optOid.size(); j++) {
			    if (optOid.get(j) != null && ((String) optOid.get(j)).length() > 0) {
				ModelInfo mi = ModelInfo.newModelInfo();
				mi.setYear1((String) y1.get(j));
				mi.setYear2((String) y2.get(j));
				mi.setYear3((String) y3.get(j));
				mi.setYear4((String) y4.get(j));
				mi.setYear5((String) y5.get(j));
				mi.setYear6((String) y6.get(j));
				mi.setYear7((String) y7.get(j));
				mi.setYear8((String) y8.get(j));
				mi.setYear9((String) y9.get(j));
				mi.setYear10((String) y10.get(j));
				mi.setUsage((String) usage.get(j));
				mi.setOptionRate((String) optionRate.get(j));
				mi.setProduct(pi);

				if (!"nodata".equals((String) optOid.get(j)) && !"0".equals((String) optOid.get(j)))
				    mi.setModel((OEMProjectType) CommonUtil.getObject((String) optOid.get(j)));
				else
				    mi.setName((String) carName.get(i));

				mi = (ModelInfo) PersistenceHelper.manager.save(mi);
			    }
			}
		    }
		    if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(ModelInfo.class, true);

			SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=",
			        CommonUtil.getOIDLongValue(pOid));
			qs.appendWhere(sc, new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(qs);
			while (qr.hasMoreElements()) {
			    Object o[] = (Object[]) qr.nextElement();
			    ModelInfo mi = (ModelInfo) o[0];

			    ModelInfo _mi = ModelInfo.newModelInfo();

			    if (mi.getModel() != null)
				_mi.setModel((OEMProjectType) CommonUtil.getObject(mi.getModel().getPersistInfo().getObjectIdentifier()
				        .toString()));
			    else
				_mi.setName(mi.getName());
			    _mi.setYear1(mi.getYear1());
			    _mi.setYear2(mi.getYear2());
			    _mi.setYear3(mi.getYear3());
			    _mi.setYear4(mi.getYear4());
			    _mi.setYear5(mi.getYear5());
			    _mi.setYear6(mi.getYear6());
			    _mi.setYear7(mi.getYear7());
			    _mi.setYear8(mi.getYear8());
			    _mi.setYear9(mi.getYear9());
			    _mi.setYear10(mi.getYear10());
			    _mi.setUsage(mi.getUsage());
			    _mi.setOptionRate(mi.getOptionRate());
			    _mi.setProduct(pi);

			    _mi = (ModelInfo) PersistenceHelper.manager.save(_mi);
			}
		    }
		}
	    }

	    // 관련 Project
	    Vector projectoid = (Vector) hash.get("projectoid");
	    if (projectoid != null && projectoid.size() > 0) {
		for (int i = 0; i < projectoid.size(); i++) {
		    E3PSProject dProject = (E3PSProject) CommonUtil.getObject((String) projectoid.get(i));
		    ProjectDependencyLink link = ProjectDependencyLink.newProjectDependencyLink(project, dProject);
		    link = (ProjectDependencyLink) PersistenceHelper.manager.save(link);
		}
	    }

	    // 파생차종
	    Vector oemOids = (Vector) hash.get("oemOids");
	    if (oemOids != null && oemOids.size() > 0) {
		for (int i = 0; i < oemOids.size(); i++) {
		    OEMProjectType oemType = (OEMProjectType) CommonUtil.getObject((String) oemOids.get(i));
		    ProjectOemTypeLink link = ProjectOemTypeLink.newProjectOemTypeLink(oemType, project);
		    PersistenceHelper.manager.save(link);
		}
	    }

	    /*
	     * **********************************************************************
	     * Project Object Create end **********************************************************************
	     */

	    /*
	     * **********************************************************************
	     * Project Link Create start **********************************************************************
	     */

	    // 4. PM Setting
	    // if ("자동차 사업부".equals(teamType) || "자동차사업부_KETS_PMO".equals(teamType)) {
	    // String isPM = (String) hash.get("isPM");
	    // if ("true".equals(isPM)) {
	    // project.setIsPM(true);
	    // } else {
	    // project.setIsPM(false);
	    // }
	    String devManager = (String) hash.get("devUser"); // 개발담당자(PM) 설정
	    WTUser devManagerUser = null;
	    if (devManager != null && devManager.length() > 0) {
		devManagerUser = (WTUser) CommonUtil.getObject(devManager);
		ProjectUserHelper.manager.setPM(project, devManagerUser, 0);
	    }
	    // } else if ("전자 사업부".equals(teamType)) {
	    // WTUser pmUser = (WTUser) SessionHelper.manager.getPrincipal();
	    // ProjectUserHelper.manager.setPM(project, pmUser, 0);
	    // project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project,
	    // State.toState("PLANCHANGE"), true);
	    // }

	    String department = (String) hash.get("department"); // ??
	    if (department != null && department.length() > 0) {
		ReferenceFactory rf = new ReferenceFactory();
		ArrayList list = new ArrayList();
		list.add(rf.getReference(department).getObject());
		boolean flag = ProjectHelper.addRefDepartment(project, list);
	    }

	    // 6. ETC Member Setting
	    WTUser wtuser = null;

	    Vector projectMember = (Vector) hash.get("USERMEMBER");
	    if (projectMember != null && projectMember.size() > 0) {
		for (int i = 0; i < projectMember.size(); i++) {
		    wtuser = (WTUser) CommonUtil.getObject((String) projectMember.get(i));
		    if (wtuser != null)
			ProjectUserHelper.manager.setMember(project, wtuser, 0);
		}
	    }

	    // Role Member Setting
	    Role role = null;
	    TeamTemplate tempTeam = null;
	    if (CommonUtil.isMember("자동차사업부") || CommonUtil.isMember("KETS_PMO")) {
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
	    } else if (CommonUtil.isMember("전자사업부")) {
		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
	    }
	    Vector vecTeamStd = tempTeam.getRoles();

	    for (int i = 0; i < vecTeamStd.size(); i++) {
		role = (Role) vecTeamStd.get(i);
		String userOid = (String) hash.get(role.toString());

		if (userOid != null && userOid.length() > 0) {
		    wtuser = (WTUser) CommonUtil.getObject((String) hash.get(role.toString()));
		    if (wtuser != null) {
			ProjectUserHelper.manager.setRoleMember(project, role.toString(), wtuser, 0);
		    }
		}
	    }

	    // 7. TemplateProject Link Create
	    if (templateProject != null) {
		// TemplateProject templateProject =
		// (TemplateProject)CommonUtil.getObject(tempid);
		ProjectHelper.manager.copyProjectInfo(project, templateProject);
		// Kogger.debug(getClass(), "copyProjectInfo");

		TaskHelper.manager.copyProjectFromTemplateNew(project, templateProject, category, wbsType);
		ProjectUserHelper.settingRoleTaskMember(project);
		// Kogger.debug(getClass(), "copyProjectFromTemplate");

		// TaskHelper.manager.copyTaskInfo(jelProject ,templateProject);
	    }
	    String pjtno1 = (String) hash.get("pjtno1");
	    ProductHelper.syncProjectCostIF(project, pjtno1);

	    ProjectScheduleHelper.settingProgress(project);
	    transaction.commit();
	    transaction = null;

	    // 프로젝트 PM에게 메일 발송
	    List<WTUser> toUser = new ArrayList<WTUser>();
	    toUser.add(devManagerUser);
	    if (toUser.size() > 0) {
		KETMailHelper.service.sendFormMail("08046", "NoticeMailLine2.html", project, (WTUser) SessionHelper.getPrincipal(), toUser);
	    }

	    return project;
	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return project;
    }

    public ProductProject updateProductProject(Hashtable hash) throws WTException {
	ProductProject project = null;
	Transaction transaction = new Transaction();
	try {
	    transaction.start();

	    String oid = (String) hash.get("oid");
	    project = (ProductProject) CommonUtil.getObject(oid);

	    String ToUser = "";
	    String BeUser = "";

	    String projectName = StringUtil.checkNull((String) hash.get("projectName")); // Project Name
	    if (StringUtil.checkString(projectName.trim())) {
		E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
		master.setPjtName(StringUtil.checkNull(projectName.trim()));
		master = (E3PSProjectMaster) PersistenceHelper.manager.modify(master);

		// QueryResult rs = new QueryResult();
		// if(project != null){
		// rs = MoldProjectHelper.getRelationMoldProject(project);
		// while(rs.hasMoreElements()){
		// Object o[] = (Object[])rs.nextElement();
		// MoldProject p = (MoldProject)o[0];
		// E3PSProjectMaster moldMaster = (E3PSProjectMaster)p.getMaster();
		// try {
		// moldMaster.setPjtName(StringUtil.checkNull(projectName.trim()));
		// } catch (WTPropertyVetoException e) {
		// // TODO Auto-generated catch block
		// Kogger.error(getClass(), e);
		// }
		// moldMaster = (E3PSProjectMaster)PersistenceHelper.manager.modify(moldMaster);
		// }
		// }
	    }

	    String partNo = StringUtil.checkNull((String) hash.get("partNo")); // Part No
	    if (StringUtil.checkString(partNo)) {
		project.setPartNo(partNo);
	    }

	    String productType = StringUtil.checkNull((String) hash.get("productType"));
	    if (productType != null && productType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("PRODUCTTYPE", productType);
		project.setProductType(code);
	    }

	    String rank = (String) hash.get("rank");
	    if (rank != null && rank.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("RANK", rank);
		// Rank 수정시 관련 모듈 수정 S일때만 업데이트 하도록 한다.
		this.changeRankByProject(project, project.getRank().getName(), code.getName());
		project.setRank(code);
	    }

	    String model = (String) hash.get("model");
	    // if (model != null && model.length() > 0) {
	    OEMProjectType modelType = (OEMProjectType) CommonUtil.getObject((String) model);
	    // if (modelType != null)
	    project.setOemType(modelType);
	    // }

	    String assembledType = (String) hash.get("assembledType");
	    if (assembledType != null && assembledType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("ASSEMBLEDTYPE", assembledType);
		project.setAssembledType(code);
	    }

	    String process = (String) hash.get("process");
	    if (process != null && process.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("PROCESS", process);
		project.setProcess(code);
	    }

	    String developentType = (String) hash.get("developentType");
	    if (developentType != null && developentType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developentType);
		project.setDevelopentType(code);
	    }

	    String designType = (String) hash.get("designType");
	    if (designType != null && designType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("DESIGNTYPE", designType);
		project.setDesignType(code);
	    }

	    String developTyped = (String) hash.get("devPattern");
	    project.setDevelopedType(developTyped);

	    String productTypeLevel2 = (String) hash.get("productTypeLevel2");
	    if (productTypeLevel2 != null && productTypeLevel2.length() > 0) {
		project.setProductTypeLevel2(productTypeLevel2);
	    }

	    String sealed = (String) hash.get("sealed"); // sealed
	    if (sealed != null && sealed.length() > 0) {
		project.setWaterPoof(sealed);
	    }

	    String series = (String) hash.get("series"); // series
	    if (series != null && series.length() > 0) {
		project.setSeries(series);
	    }

	    String proteamNo = (String) hash.get("proteamNo");
	    if (proteamNo != null && proteamNo.length() > 0) {
		if (proteamNo.indexOf("NumberCode") > -1) {
		    NumberCode code = (NumberCode) CommonUtil.getObject(proteamNo);
		    project.setAssemblyPlace(code);
		    project.setPartnerNo(null);
		} else {
		    project.setAssemblyPlace(null);
		    project.setPartnerNo(proteamNo);
		}
	    }

	    String isPM = (String) hash.get("isPM");
	    if ("true".equals(isPM)) {
		project.setIsPM(true);
	    } else {
		project.setIsPM(false);
	    }

	    // ??? ??? ?????I/F
	    String drNumber = (String) hash.get("drNumber");
	    String drKeyOid = (String) hash.get("drKeyOid");
	    if (drKeyOid != null && drKeyOid.length() > 0) {
		KETDevelopmentRequest dr = (KETDevelopmentRequest) CommonUtil.getObject(drKeyOid);
		project.setDevRequest(dr);
		project.setDevRequestNo(drNumber);
	    } else {
		project.setDevRequest(null);
		project.setDevRequestNo("");
	    }
	    String reviewPjtNo[] = (String[]) hash.get("reviewPjtNo");
	    if (reviewPjtNo != null && reviewPjtNo.length > 0) {
		String reviewPjtNos = "";
		for (String pjtNo : reviewPjtNo) {

		    reviewPjtNos += pjtNo + ",";
		}
		reviewPjtNos = StringUtils.removeEnd(reviewPjtNos, ",");
		project.setReviewPjtNo(reviewPjtNos);
	    }

	    String costsDate = (String) hash.get("costsDate");
	    if (costsDate != null && costsDate.length() > 0)
		project.setCostsDate(DateUtil.getTimestampFormat(costsDate, ConfigImpl.getInstance().getString("date.format")));

	    String sales = (String) hash.get("sales"); // ?????
	    if (sales != null && !sales.equals("null") && sales.length() > 0) {
		WTUser salesuser = (WTUser) CommonUtil.getObject(sales);
		if (salesuser != null) {
		    project.setBusiness(salesuser);
		}
	    }
	    String itDivision = (String) hash.get("itDivision"); // 전자사업부 특화부서
	    if (itDivision != null) {
		project.setItDivision(itDivision);
	    }

	    String manageProduct = StringUtil.checkNull((String) hash.get("manageProduct")); // 관리제품군
	    if (manageProduct != null && manageProduct.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("MANAGEPRODUCTTYPE", manageProduct);
		project.setManageProductType(code);
	    }

	    String obtainType = StringUtil.checkNull((String) hash.get("obtainType")); // 수주관리
	    if (obtainType != null && obtainType.length() > 0) {
		NumberCode code = NumberCodeHelper.manager.getNumberCode("OBTAINORDERTYPE", obtainType);
		project.setObtainType(code);
	    }

	    String linkpjtoid = (String) hash.get("linkpjtoid");
	    deleteLinkProject(project);

	    if (linkpjtoid != null && linkpjtoid.length() > 0) {
		E3PSProject linkProject = (E3PSProject) CommonUtil.getObject(linkpjtoid);
		if (linkProject != null) {
		    E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

		    master.setLinkProjectNo(linkProject.getPjtNo());
		    PersistenceHelper.manager.save(master);
		    E3PSProjectMaster linkMaster = (E3PSProjectMaster) linkProject.getMaster();
		    String pjtNos = linkMaster.getLinkProjectNo();
		    if (!StringUtils.contains(pjtNos, master.getPjtNo())) {
			if (StringUtils.isEmpty(pjtNos)) {
			    linkMaster.setLinkProjectNo(master.getPjtNo());
			} else {
			    linkMaster.setLinkProjectNo(pjtNos + "," + master.getPjtNo());
			}
			PersistenceHelper.manager.save(linkMaster);
		    }
		}
	    } else {
		E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();
		if (StringUtils.isNotEmpty(master.getLinkProjectNo())) {
		    master.setLinkProjectNo("");
		    PersistenceHelper.manager.save(master);
		}
	    }

	    project = (ProductProject) PersistenceHelper.manager.modify(project);

	    QuerySpec pcspec = new QuerySpec(ProjectCustomereventLink.class);
	    SearchUtil.appendEQUAL(pcspec, ProjectCustomereventLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
	    QueryResult pcresult = PersistenceHelper.manager.find(pcspec);
	    while (pcresult != null && pcresult.hasMoreElements()) {
		ProjectCustomereventLink link = (ProjectCustomereventLink) pcresult.nextElement();
		if (link != null)
		    PersistenceHelper.manager.delete(link);
	    }

	    project = (ProductProject) PersistenceHelper.manager.refresh(project);
	    Vector customer = (Vector) hash.get("customer");
	    if (customer != null && customer.size() > 0) {
		for (int i = 0; i < customer.size(); i++) {
		    NumberCode code = (NumberCode) CommonUtil.getObject((String) customer.get(i));
		    if (code != null) {
			ProjectCustomereventLink link = ProjectCustomereventLink.newProjectCustomereventLink(code, project);
			link = (ProjectCustomereventLink) PersistenceHelper.manager.save(link);
		    }
		}
	    }
	    Vector subcontractorVec = null;
	    if (hash.get("SUBCONTRACTOR") != null) {
		QueryResult result = null;
		QuerySpec qs = new QuerySpec();
		int index = qs.appendClassList(ProjectSubcontractorLink.class, true);
		qs.appendWhere(
		        new SearchCondition(ProjectSubcontractorLink.class, "roleBObjectRef.key.id", "=", CommonUtil
		                .getOIDLongValue(project)), new int[] { index });
		result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
		    Object[] object = (Object[]) result.nextElement();
		    ProjectSubcontractorLink link = (ProjectSubcontractorLink) object[0];
		    if (link != null) {
			PersistenceHelper.manager.delete(link);
		    }
		}

		subcontractorVec = getParamsVector(hash.get("SUBCONTRACTOR"));
		String subcontractoroid = null;
		for (int i = 0; i < subcontractorVec.size(); i++) {
		    subcontractoroid = (String) subcontractorVec.get(i);
		    NumberCode code = (NumberCode) CommonUtil.getObject(subcontractoroid);
		    if (code != null) {
			ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(code, project);
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    // nOidVec
	    if (hash.get("nOidVec") != null) {
		QueryResult result = null;
		QuerySpec qs = new QuerySpec();
		int index = qs.appendClassList(ProjectMasterCodeSubCodeLink.class, true);
		qs.appendWhere(new SearchCondition(ProjectMasterCodeSubCodeLink.class, "projectId", "=", CommonUtil.getOIDString(project)),
		        new int[] { index });
		result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
		    Object[] object = (Object[]) result.nextElement();
		    ProjectMasterCodeSubCodeLink link = (ProjectMasterCodeSubCodeLink) object[0];
		    if (link != null) {
			PersistenceHelper.manager.delete(link);
		    }
		}

		Vector nOidVec = getParamsVector(hash.get("nOidVec"));
		String nOid = null;
		for (int i = 0; i < nOidVec.size(); i++) {
		    nOid = (String) nOidVec.get(i);
		    NumberCode mnc = null;
		    NumberCode snc = null;
		    StringTokenizer token = new StringTokenizer(nOid, "@");
		    String nnOid[] = new String[token.countTokens()];
		    int t = 0;
		    while (token.hasMoreTokens()) {
			nnOid[t] = token.nextToken();
			t++;
		    }
		    String masterCode = nnOid[0];
		    String subCode = nnOid[1];
		    mnc = (NumberCode) CommonUtil.getObject(masterCode);
		    snc = (NumberCode) CommonUtil.getObject(subCode);

		    if (snc != null) {
			ProjectSapSubcontractorLink plink = ProjectSapSubcontractorLink.newProjectSapSubcontractorLink(snc, project);
			PersistenceHelper.manager.save(plink);
		    }

		    if (snc != null && mnc != null) {
			ProjectMasterCodeSubCodeLink plink = ProjectMasterCodeSubCodeLink.newProjectMasterCodeSubCodeLink(snc, mnc);
			plink.setProjectId(CommonUtil.getOIDString(project));
			PersistenceHelper.manager.save(plink);
		    }
		}
	    }

	    String devUser = (String) hash.get("devUser");
	    ToUser = (String) hash.get("ToUser"); // ??? ???????
	    if (devUser != null && devUser.length() > 0 && !"null".equals(devUser)) {
		WTUser devManagerUser = (WTUser) CommonUtil.getObject(devUser);
		ProjectUserHelper.manager.replacePM(project, devManagerUser);
	    }
	    BeUser = devUser;

	    QueryResult departmentQr = ProjectUserHelper.manager.getViewDepartmentLink(project, null);
	    if (departmentQr.hasMoreElements()) {
		Object[] obj = (Object[]) departmentQr.nextElement();
		ProjectViewDepartmentLink link = (ProjectViewDepartmentLink) obj[0];
		PersistenceHelper.manager.delete(link);
	    }
	    String department = (String) hash.get("department");
	    if (department != null && department.length() > 0) {
		ReferenceFactory rf = new ReferenceFactory();
		ArrayList list = new ArrayList();
		list.add(rf.getReference(department).getObject());
		boolean flag = ProjectHelper.addRefDepartment(project, list);
	    }

	    // Hashtable costHash = updateProductInfo(hash, project);
	    // Collection coll = costHash.values();
	    // Iterator it = coll.iterator();
	    // while (it.hasNext()) {
	    // CostInfo costInfo = (CostInfo) it.next();
	    // PersistenceHelper.manager.delete(costInfo);
	    // }

	    // 관련프로젝트 삭제
	    QueryResult projectQr = PersistenceHelper.manager.navigate(project, "dependTarget", ProjectDependencyLink.class, false);
	    while (projectQr != null && projectQr.hasMoreElements()) {
		ProjectDependencyLink link = (ProjectDependencyLink) projectQr.nextElement();
		PersistenceHelper.manager.delete(link);
	    }

	    // 관련프로젝트 저장
	    Vector projectoid = (Vector) hash.get("projectoid");
	    if (projectoid != null && projectoid.size() > 0) {
		for (int i = 0; i < projectoid.size(); i++) {
		    E3PSProject dProject = (E3PSProject) CommonUtil.getObject((String) projectoid.get(i));
		    ProjectDependencyLink link = ProjectDependencyLink.newProjectDependencyLink(project, dProject);
		    link = (ProjectDependencyLink) PersistenceHelper.manager.save(link);
		}
	    }

	    // 파생차종 삭제
	    QueryResult oemTypeQr = PersistenceHelper.manager.navigate(project, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE,
		    ProjectOemTypeLink.class, false);
	    while (oemTypeQr.hasMoreElements()) {
		ProjectOemTypeLink link = (ProjectOemTypeLink) oemTypeQr.nextElement();
		PersistenceHelper.manager.delete(link);
	    }

	    // 파생차종 저장
	    Vector oemOids = (Vector) hash.get("oemOids");
	    if (oemOids != null && oemOids.size() > 0) {
		for (int i = 0; i < oemOids.size(); i++) {
		    OEMProjectType oemType = (OEMProjectType) CommonUtil.getObject((String) oemOids.get(i));
		    ProjectOemTypeLink link = ProjectOemTypeLink.newProjectOemTypeLink(oemType, project);
		    PersistenceHelper.manager.save(link);
		}
	    }

	    // Kogger.debug(getClass(), "copy 시작 !!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~~~~~~~");
	    if (subcontractorVec != null && projectoid != null) {
		// Kogger.debug(getClass(), "2222222222222 copy 시작
		// !!!!!!!!!!!!!!~~~~~~~~~~~~~~~~~~~~~~~");
		CustomerPlanHelper.copyLinkProject(oid, subcontractorVec, projectoid);
	    }
	    // Kogger.debug(getClass(), "333 copy ");

	    String pjtno1 = (String) hash.get("pjtno1");
	    ProductHelper.syncProjectCostIF((E3PSProject) project, pjtno1);
	    transaction.commit();
	    transaction = null;

	    if (ToUser != null && !BeUser.equals(ToUser)) {
		ProjectHelper.changeProjectUser((E3PSProject) project);
	    }

	    return project;
	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return project;
    }

    public Hashtable updateProductInfo(Hashtable hash, ProductProject project) throws WTException, WTPropertyVetoException, QueryException {
	// ProductInfo
	Vector rowId = (Vector) hash.get("rowIdVec");
	Vector pNum = (Vector) hash.get("pNumVec");
	Vector reviewProjectNo = (Vector) hash.get("reviewProjectNoVec");
	Vector reviewSeqNo = (Vector) hash.get("reviewSeqNoVec");
	Vector pName = (Vector) hash.get("pNameVec");
	Vector areas = (Vector) hash.get("areasVec");
	Vector usageT = (Vector) hash.get("usageTVec");
	Vector price = (Vector) hash.get("priceVec");
	Vector cost = (Vector) hash.get("costVec");
	Vector rate = (Vector) hash.get("rateVec");
	Vector y1T = (Vector) hash.get("y1TVec");
	Vector y2T = (Vector) hash.get("y2TVec");
	Vector y3T = (Vector) hash.get("y3TVec");
	Vector y4T = (Vector) hash.get("y4TVec");
	Vector y5T = (Vector) hash.get("y5TVec");
	Vector y6T = (Vector) hash.get("y6TVec");
	Vector y7T = (Vector) hash.get("y7TVec");
	Vector y8T = (Vector) hash.get("y8TVec");
	Vector y9T = (Vector) hash.get("y9TVec");
	Vector y10T = (Vector) hash.get("y10TVec");
	Vector carName = (Vector) hash.get("carNameVec");
	Vector assemblyPlaceType = (Vector) hash.get("assemblyPlaceTypeVec"); // 조립처 구분(사내/외주)
	Vector assemblyPlace = (Vector) hash.get("assemblyPlaceVec");// 조립처
	Vector assembledType = (Vector) hash.get("assembledTypeVec");// 조립구분

	if (rowId != null && rowId.size() > 0) {
	    for (int i = 0; i < rowId.size(); i++) {
		ProductInfo pi = ProductInfo.newProductInfo();
		if (pNum.get(i) != null) {
		    pi.setPNum((String) pNum.get(i));
		}
		if (reviewProjectNo != null && reviewProjectNo.get(i) != null && ((String) reviewProjectNo.get(i)).length() > 0)
		    pi.setReviewProjectNo((String) reviewProjectNo.get(i));
		if (reviewSeqNo != null && reviewSeqNo.get(i) != null && ((String) reviewSeqNo.get(i)).length() > 0)
		    pi.setReviewSeqNo((String) reviewSeqNo.get(i));
		pi.setPName((String) pName.get(i));
		// pi.setAreas((String) areas.get(i));
		if (usageT != null && i < usageT.size()) {
		    pi.setUsage((String) usageT.get(i));
		}
		// pi.setPrice((String) price.get(i));
		// pi.setCost((String) cost.get(i));
		// pi.setRate((String) rate.get(i));

		String pOid = (String) hash.get("pOidVec" + rowId.get(i));

		if (usageT != null && i < usageT.size()) {
		    pi.setYear1((String) y1T.get(i));
		    pi.setYear2((String) y2T.get(i));
		    pi.setYear3((String) y3T.get(i));
		    pi.setYear4((String) y4T.get(i));
		    pi.setYear5((String) y5T.get(i));
		    pi.setYear6((String) y6T.get(i));
		    pi.setYear7((String) y7T.get(i));
		    pi.setYear8((String) y8T.get(i));
		    pi.setYear9((String) y9T.get(i));
		    pi.setYear10((String) y10T.get(i));
		}
		pi.setSeqNum(ManageSequence.getSeqNo2("", "00000", "ProductInfo", "seqNum", i));
		// }

		pi.setProject(project);
		if (StringUtil.checkString((String) assembledType.get(i))) {
		    pi.setAssembledType((NumberCode) CommonUtil.getObject((String) assembledType.get(i)));// 조립구분
		}

		String partAssemblyType = "";
		String partAssemblyPlace = "";
		if (StringUtil.checkString((String) assemblyPlaceType.get(i))) {
		    pi.setAssemblyPlaceType((String) assemblyPlaceType.get(i));
		    if (((String) assemblyPlaceType.get(i)).equals("외주")) {
			pi.setAssemblyPartnerNo((String) assemblyPlace.get(i));
			partAssemblyType = "2";
			partAssemblyPlace = (String) assemblyPlace.get(i);
		    } else {
			NumberCode num = (NumberCode) CommonUtil.getObject((String) assemblyPlace.get(i));
			if (num != null) {
			    pi.setAssemblyPlace(num);
			    partAssemblyType = "1";
			    partAssemblyPlace = num.getCode();
			}

		    }
		}

		pi = (ProductInfo) PersistenceHelper.manager.save(pi);
		// 부품정보 프로젝트에 맵핑
		try {
		    PartBaseHelper.service.makeProjectPartRelationByProject(project.getPjtNo(), pi.getPNum());

		    Map<String, Object> reqMap = new HashMap<String, Object>();
		    reqMap.put("partNo", pi.getPNum());
		    reqMap.put("mfType", partAssemblyType);
		    reqMap.put("place", partAssemblyPlace);
		    PartBaseHelper.service.updateManufacPlaceByProject(reqMap);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

		Vector count = (Vector) hash.get("countVec" + rowId.get(i));
		Vector optOid = (Vector) hash.get("optOidVec" + rowId.get(i));
		Vector y1 = (Vector) hash.get("y1Vec" + rowId.get(i));
		Vector y2 = (Vector) hash.get("y2Vec" + rowId.get(i));
		Vector y3 = (Vector) hash.get("y3Vec" + rowId.get(i));
		Vector y4 = (Vector) hash.get("y4Vec" + rowId.get(i));
		Vector y5 = (Vector) hash.get("y5Vec" + rowId.get(i));
		Vector y6 = (Vector) hash.get("y6Vec" + rowId.get(i));
		Vector y7 = (Vector) hash.get("y7Vec" + rowId.get(i));
		Vector y8 = (Vector) hash.get("y8Vec" + rowId.get(i));
		Vector y9 = (Vector) hash.get("y9Vec" + rowId.get(i));
		Vector y10 = (Vector) hash.get("y10Vec" + rowId.get(i));
		Vector usage = (Vector) hash.get("usageVec" + rowId.get(i));
		Vector optionRate = (Vector) hash.get("optionRateVec" + rowId.get(i));

		if (optOid != null && optOid.size() > 0) {
		    for (int j = 0; j < optOid.size(); j++) {
			if (optOid.get(j) != null && ((String) optOid.get(j)).length() > 0) {
			    ModelInfo mi = ModelInfo.newModelInfo();
			    mi.setYear1((String) y1.get(j));
			    mi.setYear2((String) y2.get(j));
			    mi.setYear3((String) y3.get(j));
			    mi.setYear4((String) y4.get(j));
			    mi.setYear5((String) y5.get(j));
			    mi.setYear6((String) y6.get(j));
			    mi.setYear7((String) y7.get(j));
			    mi.setYear8((String) y8.get(j));
			    mi.setYear9((String) y9.get(j));
			    mi.setYear10((String) y10.get(j));
			    mi.setUsage((String) usage.get(j));
			    mi.setOptionRate((String) optionRate.get(j));
			    mi.setProduct(pi);

			    if (!"nodata".equals((String) optOid.get(j))) {
				mi.setModel((OEMProjectType) CommonUtil.getObject((String) optOid.get(j)));
			    } else {
				mi.setName((String) carName.get(i));
			    }
			    mi = (ModelInfo) PersistenceHelper.manager.save(mi);

			}
		    }
		}

		if (pOid != null && pOid.length() > 0 && !"undefined".equals(pOid)) {
		    QuerySpec qs = new QuerySpec();
		    int idx = qs.appendClassList(ModelInfo.class, true);

		    SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=",
			    CommonUtil.getOIDLongValue(pOid));
		    qs.appendWhere(sc, new int[] { idx });

		    QueryResult qr = PersistenceHelper.manager.find(qs);

		    Kogger.debug(getClass(), "ZZZZZZZZZZZZZZZZZZZZZ qr size() == " + qr.size());

		    /* 20130220 shkim add ????????? ??? ??? ??? */

		    int _miCount = 0;

		    if (optOid == null) {
			while (qr.hasMoreElements()) {
			    Object o[] = (Object[]) qr.nextElement();
			    ModelInfo mi = (ModelInfo) o[0];

			    ModelInfo _mi = ModelInfo.newModelInfo();

			    if (mi.getModel() != null) {
				// _mi.setModel((OEMProjectType)CommonUtil.getObject(mi.getModel().getPersistInfo().getObjectIdentifier().toString()));
				_mi.setModel(mi.getModel());
			    } else {
				_mi.setName(mi.getName());
			    }
			    _mi.setYear1(mi.getYear1());
			    _mi.setYear2(mi.getYear2());
			    _mi.setYear3(mi.getYear3());
			    _mi.setYear4(mi.getYear4());
			    _mi.setYear5(mi.getYear5());
			    _mi.setYear6(mi.getYear6());
			    _mi.setYear7(mi.getYear7());
			    _mi.setYear8(mi.getYear8());
			    _mi.setYear9(mi.getYear9());
			    _mi.setYear10(mi.getYear10());
			    _mi.setUsage(mi.getUsage());
			    _mi.setOptionRate(mi.getOptionRate());
			    _mi.setProduct(pi);

			    _mi = (ModelInfo) PersistenceHelper.manager.save(_mi);

			    _miCount++;
			}
		    }

		    ProductInfo oldProductInfo = (ProductInfo) CommonUtil.getObject(pOid);
		    if (oldProductInfo != null && oldProductInfo.getProject().getPjtNo().equals(project.getPjtNo())) {
			PersistenceHelper.manager.delete(oldProductInfo);
		    }
		}
	    }
	}

	String deletePOid = StringUtil.checkNull((String) hash.get("deletePOid"));
	StringTokenizer deletePOids = new StringTokenizer(deletePOid, "@");
	while (deletePOids.hasMoreTokens()) {
	    String delPOid = deletePOids.nextToken();
	    if (delPOid.length() > 0) {
		ProductInfo delProductInfo = (ProductInfo) CommonUtil.getObject(delPOid);
		if (delProductInfo != null) {
		    PersistenceHelper.manager.delete(delProductInfo);
		}
		// ProductHelper.deleteCostIF(delProductInfo);
	    }
	}
	// PartNo 업데이트
	String partNo = "";
	QuerySpec qs = new QuerySpec();
	int idxpi = qs.appendClassList(ProductInfo.class, true);
	SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
	qs.appendWhere(cs, new int[] { idxpi });
	QueryResult qrpi = PersistenceHelper.manager.find(qs);
	String manufacPlace = "";

	while (qrpi.hasMoreElements()) {
	    Object o[] = (Object[]) qrpi.nextElement();
	    ProductInfo pi = (ProductInfo) o[0];
	    partNo += pi.getPNum() + ",";
	    String assPlaceType = pi.getAssemblyPlaceType();
	    if ("사내".equals(assPlaceType) && pi.getAssemblyPlace() != null) {
		manufacPlace += pi.getAssemblyPlace().getCode() + "|";
	    }
	}
	manufacPlace = StringUtils.removeEnd(manufacPlace, "|");
	manufacPlace = StringUtil.removeDuplicateStringToken(manufacPlace, "|");

	if (partNo.length() > 0) {
	    project.setPartNo(partNo.substring(0, partNo.length() - 1));
	    project.setManufacPlace(manufacPlace);
	    PersistenceHelper.manager.modify(project);
	} else {
	    project.setPartNo("");
	    project.setManufacPlace("");
	    PersistenceHelper.manager.modify(project);
	}

	// Item ??
	Hashtable costHash = new Hashtable();
	QuerySpec specCost = new QuerySpec();
	int idx_Cost = specCost.addClassList(CostInfo.class, true);
	SearchCondition scCost = new SearchCondition(CostInfo.class, "projectReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(project));
	specCost.appendWhere(scCost, new int[] { idx_Cost });
	specCost.appendAnd();
	scCost = new SearchCondition(CostInfo.class, "costType", SearchCondition.EQUAL, "???");
	specCost.appendWhere(scCost, new int[] { idx_Cost });

	QueryResult rtCost = PersistenceHelper.manager.find(specCost);
	while (rtCost.hasMoreElements()) {
	    Object[] costObj = (Object[]) rtCost.nextElement();
	    CostInfo costInfo = (CostInfo) costObj[0];
	    costHash.put(CommonUtil.getOIDString(costInfo), costInfo);
	}

	Vector moldItemOid = (Vector) hash.get("moldItemOid");
	Vector itemType = (Vector) hash.get("itemType");
	Vector moldProductType = (Vector) hash.get("moldProductType");
	Vector moldPartNo = (Vector) hash.get("moldPartNo");
	Vector partName = (Vector) hash.get("partName");
	Vector dieNo = (Vector) hash.get("dieNo");
	Vector costOid = (Vector) hash.get("costOid");
	Vector moldType = (Vector) hash.get("moldType");
	Vector cVPitch = (Vector) hash.get("cVPitch");
	Vector cTSPM = (Vector) hash.get("cTSPM");
	Vector making = (Vector) hash.get("making");
	Vector makingPlace2 = (Vector) hash.get("makingPlace2");
	Vector productionPlace = (Vector) hash.get("productionPlace");
	Vector productionPlace2 = (Vector) hash.get("productionPlace2");
	Vector materials = (Vector) hash.get("materials");
	Vector poidvalue = (Vector) hash.get("poidvalue");
	Vector height = (Vector) hash.get("height");
	Vector wide = (Vector) hash.get("wide");
	Vector shrinkage = (Vector) hash.get("shrinkage");
	Vector etc = (Vector) hash.get("etc");
	String delItemOid = (String) hash.get("delItemOid");
	/*
	 * Kogger.debug(getClass(), "######################################## ?????); Kogger.debug(getClass(), " delItemOid
	 * :" + delItemOid); Kogger.debug(getClass(), "itemType :" + itemType.size()); Kogger.debug(getClass(), "moldPartNo :" +
	 * moldPartNo.size());
	 */

	StringTokenizer delToken = null;
	if (delItemOid != null) {
	    delToken = new StringTokenizer(delItemOid, ",");
	}
	if (delToken != null) {
	    while (delToken.hasMoreTokens()) {
		String delOid = delToken.nextToken();
		Kogger.debug(getClass(), "delOid :" + delOid);
		if (delOid.length() > 0) {
		    MoldItemInfo moldItemInfo = (MoldItemInfo) CommonUtil.getObject(delOid);
		    PersistenceHelper.manager.delete(moldItemInfo);
		}
	    }
	}
	if (moldPartNo != null && moldPartNo.size() > 0) {
	    for (int i = 0; i < moldPartNo.size(); i++) {

		if (StringUtils.isEmpty((String) dieNo.get(i)) && StringUtils.isEmpty((String) moldPartNo.get(i))) {
		    continue;
		}
		MoldItemInfo moldItemInfo = null;

		if ((String) moldItemOid.get(i) != null) {
		    if (moldItemOid.get(i).toString().startsWith("e3ps.project.MoldItemInfo")) {
			moldItemInfo = (MoldItemInfo) CommonUtil.getObject((String) moldItemOid.get(i));
		    } else {
			moldItemInfo = MoldItemInfo.newMoldItemInfo();
		    }
		} else
		    moldItemInfo = MoldItemInfo.newMoldItemInfo();

		Kogger.debug(getClass(), "itemType.get(i) >> " + itemType.get(i));
		moldItemInfo.setItemType((String) itemType.get(i));

		NumberCode code = null;
		String moldProductTypeValue = (String) moldProductType.get(i);
		if (moldProductTypeValue != null && moldProductTypeValue.length() > 0) {
		    code = NumberCodeHelper.manager.getNumberCode("SPECPARTTYPE", moldProductTypeValue);
		    if (code != null)
			moldItemInfo.setProductType(code);
		}
		if (moldPartNo.size() > 0) {
		    moldItemInfo.setPartNo((String) moldPartNo.get(i));
		}
		if (partName.size() > 0) {
		    moldItemInfo.setPartName((String) partName.get(i));
		}
		if (dieNo.size() > 0) {
		    moldItemInfo.setDieNo((String) dieNo.get(i));
		}
		if (StringUtils.isEmpty(moldItemInfo.getItemType())) {
		    if (moldItemInfo.getDieNo().startsWith("1")) {
			moldItemInfo.setItemType("Press");
		    } else if (moldItemInfo.getDieNo().startsWith("2")) {
			moldItemInfo.setItemType("Mold");
		    }
		}

		if (moldItemInfo.getCostInfo() != null) {
		    CostInfo costInfo = moldItemInfo.getCostInfo();
		    costInfo.setDieNo((String) dieNo.get(i));
		    costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);

		    if (costHash.get(CommonUtil.getOIDString(costInfo)) != null)
			costHash.remove(CommonUtil.getOIDString(costInfo));
		}

		code = NumberCodeHelper.manager.getNumberCode("MOLDTYPE", (String) moldType.get(i));
		if (code != null) {
		    moldItemInfo.setMoldType(code);
		}
		if (cVPitch.size() > 0) {
		    moldItemInfo.setCVPitch((String) cVPitch.get(i));
		}
		if (cTSPM.size() > 0) {
		    moldItemInfo.setCTSPM((String) cTSPM.get(i));
		}
		if (making.size() > 0) {
		    moldItemInfo.setMaking((String) making.get(i));
		}
		if (makingPlace2.size() > 0) {
		    String makingPlace2Value = (String) makingPlace2.get(i);
		    Kogger.debug(getClass(), "makingPlace2Value >> " + makingPlace2Value);
		    if (makingPlace2Value != null && makingPlace2Value.length() > 0) {
			if (makingPlace2Value.indexOf("NumberCode") > -1) {
			    code = (NumberCode) CommonUtil.getObject(makingPlace2Value);
			    if (code != null)
				moldItemInfo.setMakingPlace(code);
			} else {
			    moldItemInfo.setMakingPlace(null);
			    moldItemInfo.setMakingPlacePartnerNo(makingPlace2Value);
			}
		    }
		}
		if (productionPlace.size() > 0) {
		    moldItemInfo.setProductionPlace((String) productionPlace.get(i));
		}

		String productionPlace2Value = "";

		if (productionPlace2.size() > 0) {
		    productionPlace2Value = (String) productionPlace2.get(i);
		}

		Kogger.debug(getClass(), "productionPlace2ValueproductionPlace2ValueproductionPlace2Value=" + productionPlace2Value);
		if (productionPlace2Value != null && productionPlace2Value.length() > 0) {
		    if (productionPlace2Value.indexOf("NumberCode") > -1) {
			code = (NumberCode) CommonUtil.getObject(productionPlace2Value);
			if (code != null)
			    moldItemInfo.setPurchasePlace(code);
			moldItemInfo.setPartnerNo("");
		    } else {
			moldItemInfo.setPurchasePlace(null);
			moldItemInfo.setPartnerNo(productionPlace2Value);
		    }
		}

		if (materials.get(i) != null && ((String) materials.get(i)).length() > 0) {
		    MoldMaterial mMaterial = (MoldMaterial) CommonUtil.getObject((String) materials.get(i));
		    if (mMaterial != null) {
			moldItemInfo.setMaterial(mMaterial);
		    }
		}
		if (poidvalue.get(i) != null && ((String) poidvalue.get(i)).length() > 0) {
		    code = (NumberCode) CommonUtil.getObject((String) poidvalue.get(i));
		    if (code != null) {
			moldItemInfo.setProperty(code);
		    }
		}
		if (height.get(i) != null && ((String) height.get(i)).length() > 0) {
		    moldItemInfo.setThickness((String) height.get(i));
		}
		if (wide.get(i) != null && ((String) wide.get(i)).length() > 0) {
		    moldItemInfo.setWidth((String) wide.get(i));
		}
		if (shrinkage.get(i) != null && ((String) shrinkage.get(i)).length() > 0) {
		    moldItemInfo.setShrinkage((String) shrinkage.get(i));
		}
		if (etc.get(i) != null && ((String) etc.get(i)).length() > 0) {
		    moldItemInfo.setEtc((String) etc.get(i));
		}

		moldItemInfo.setProject(project);
		Kogger.debug(getClass(), "등록전 moldItemInfo >> " + moldItemInfo.getItemType());
		moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
		// 부품정보 프로젝트에 맵핑
		try {
		    PartBaseHelper.service.makeProjectPartRelationByProject(project.getPjtNo(), moldItemInfo.getPartNo());
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
	    }
	}
	return costHash;
    }

    public MoldProject createMoldProject(Hashtable hash) throws WTException {
	MoldProject project = null;
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    project = MoldProject.newMoldProject();
	    transaction.commit();
	    transaction = null;
	    return project;
	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return project;
    }

    public MoldProject updateMoldProject(Hashtable hash) throws WTException {
	MoldProject project = null;
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    String oid = (String) hash.get("oid");
	    project = (MoldProject) CommonUtil.getObject(oid);
	    transaction.commit();
	    transaction = null;
	    return project;
	} catch (NumberFormatException e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    if (transaction != null)
		transaction.rollback();
	    transaction = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null)
		transaction = null;
	}
	return project;
    }

    private NumberCode createNumberCodeObject(String codeType, String code) {
	NumberCode customer = null;

	try {
	    customer = NumberCode.newNumberCode();
	    customer.setName(code);
	    Vector vec = NumberCodeHelper.manager.getNumberCodeForQuery(codeType);
	    customer.setCode(CommonUtil.zeroFill(vec.size() + 1, 4));
	    customer.setDescription("");
	    customer.setCodeType(NumberCodeType.toNumberCodeType(codeType));
	    customer = (NumberCode) PersistenceHelper.manager.store(customer);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}
	return customer;
    }

    private E3PSProject E3PSProjectCustomerLink(E3PSProject e3psProject, NumberCode customerNum) {
	/*
	 * try { E3PSProjectCustomerLink link = JELProjectCustomerLink.newJELProjectCustomerLink(e3psProject, customerNum);
	 * PersistenceHelper.manager.save(link); } catch (WTException e) { Kogger.error(getClass(), e); }
	 */
	return e3psProject;
    }

    private Vector getParamsVector(Object obj) {
	Vector vec = new Vector();
	if (obj == null)
	    return vec;
	if (obj instanceof String) {
	    vec.add(obj.toString());
	} else if (obj instanceof String[]) {
	    String[] strs = (String[]) obj;
	    for (int i = 0; i < strs.length; i++) {
		vec.addElement(strs[i]);
	    }
	} else if (obj instanceof Vector) {
	    vec = (Vector) obj;
	}
	return vec;
    }

    public void startProjectSchedule() {
	try {

	    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	    Scheduler sched = schedFact.getScheduler();
	    sched.start();
	    JobDetail jobDetail = new JobDetail("PDFMS", "SCHEDULER", ProjectScheduler.class);

	    CronTrigger trigger = new CronTrigger("PDFMS", "SCHEDULER");

	    trigger.setCronExpression("0 45 " + 5 + " * * ?");
	    sched.scheduleJob(jobDetail, trigger);
	    Kogger.debug(getClass(), "ProjectSchduler registry...");

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    /*
     * public void registerEvents( ManagerService managerService ) { Kogger.debug(getClass(), "registerEvents = " +
     * this.getClass().getName() + " " + "PRE_STORE EVENT"); KeyedEventListener listener = new
     * MasterMakeEventListener(managerService.getName()); managerService.addEventListener( listener,
     * PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.PRE_STORE)); }
     */

    private OEMProjectType getOEMType(String oemType, String oemSubType) {
	// select * from oemprojecttype where path like '%/KMC/4??'

	Kogger.debug(getClass(), "getOEMType Start");

	QuerySpec spec = null;
	QueryResult result = null;
	OEMProjectType oemPjtType = null;

	try {
	    spec = new QuerySpec();

	    Class target = OEMProjectType.class;
	    int idx_target = spec.addClassList(target, true);

	    spec.appendWhere(new SearchCondition(target, "path", SearchCondition.LIKE, "%/" + oemType + "/" + oemSubType),
		    new int[] { idx_target });

	    Kogger.debug(getClass(), "SPEC<<< " + spec);

	    result = PersistenceHelper.manager.find(spec);

	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		oemPjtType = (OEMProjectType) obj[0];

		Kogger.debug(getClass(), "oemPjtType>>> " + oemPjtType.getName());
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return oemPjtType;
    }

    private ProjectOutPutType getOutputType(String outputType) {
	// select * from projectoutputtype where name like '%APQP'

	Kogger.debug(getClass(), "getOutputType Start");

	QuerySpec spec = null;
	QueryResult result = null;
	ProjectOutPutType pjtOutputType = null;

	try {
	    spec = new QuerySpec();

	    Class target = ProjectOutPutType.class;
	    int idx_target = spec.addClassList(target, true);

	    spec.appendWhere(new SearchCondition(target, "name", SearchCondition.LIKE, "%" + outputType), new int[] { idx_target });

	    Kogger.debug(getClass(), "SPEC<<< " + spec);

	    result = PersistenceHelper.manager.find(spec);

	    while (result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		pjtOutputType = (ProjectOutPutType) obj[0];

		Kogger.debug(getClass(), "pjtOutputType>>> " + pjtOutputType.getName());
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return pjtOutputType;
    }

    @Override
    public List<HashMap<String, String>> getProductInfoWTPart(String projectoid) throws Exception {

	List<HashMap<String, String>> list = null;

	E3PSProject e3psProject = (E3PSProject) CommonUtil.getObject(projectoid);
	if (e3psProject instanceof ReviewProject) {
	    return null;
	}

	QuerySpec spec = new QuerySpec();

	Class<WTPartMaster> masterClass = WTPartMaster.class;
	Class<WTPart> partClass = WTPart.class;
	Class<ProductInfo> infoClass = ProductInfo.class;
	int p_idx = spec.appendClassList(partClass, false);
	int m_idx = spec.appendClassList(masterClass, false);
	int d_idx = spec.appendClassList(infoClass, false);

	// TableColumn classnameAttr = new
	// TableColumn(spec.getFromClause().getAliasAt(p_idx), "IDA2A2");
	// TableColumn oidAttr = new TableColumn(spec.getFromClause().getAliasAt(p_idx),
	// "CLASSNAMEA2A2");
	// TableColumn numberAttr = new
	// TableColumn(spec.getFromClause().getAliasAt(m_idx), "WTPARTNUMBER");
	// TableColumn nameAttr = new
	// TableColumn(spec.getFromClause().getAliasAt(m_idx), "NAME");
	// TableColumn versionAttr = new
	// TableColumn(spec.getFromClause().getAliasAt(p_idx),
	// "VERSIONIDA2VERSIONINFO");

	ClassAttribute classnameAttr = new ClassAttribute(partClass, WTAttributeNameIfc.OID_CLASSNAME);
	ClassAttribute oidAttr = new ClassAttribute(partClass, WTAttributeNameIfc.ID_NAME);
	SQLFunction oidMax = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, oidAttr);
	ClassAttribute numberAttr = new ClassAttribute(partClass, WTPart.NUMBER);
	ClassAttribute nameAttr = new ClassAttribute(partClass, WTPart.NAME);
	ClassAttribute versionAttr = new ClassAttribute(partClass, "versionInfo.identifier.versionId");
	SQLFunction verMax = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, versionAttr);

	spec.appendSelect(classnameAttr, new int[] { p_idx }, false);
	spec.appendSelect(oidMax, new int[] { p_idx }, false);
	spec.appendSelect(numberAttr, new int[] { p_idx }, false);
	spec.appendSelect(nameAttr, new int[] { p_idx }, false);
	spec.appendSelect(verMax, new int[] { p_idx }, false);
	if (spec.getGroupByClause() == null) {
	    spec.appendGroupBy(classnameAttr, new int[] { p_idx }, false);
	    spec.appendGroupBy(numberAttr, new int[] { p_idx }, false);
	    spec.appendGroupBy(nameAttr, new int[] { p_idx }, false);
	}

	// join WTPartMaster - WTPart
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(
	        partClass, "masterReference.key.id")), new int[] { m_idx, p_idx });
	// join WTPartMaster - ProductInfo
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(new ClassAttribute(masterClass, WTPartMaster.NUMBER), "=", new ClassAttribute(infoClass,
	        ProductInfo.P_NUM)), new int[] { m_idx, d_idx });
	//
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(infoClass, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(projectoid)),
	        new int[] { d_idx });
	ext.ket.shared.util.SearchUtil.appendBOOLEAN(spec, partClass, "iterationInfo.latest", SearchCondition.IS_TRUE, p_idx);

	if (!spec.isAdvancedQueryEnabled())
	    spec.setAdvancedQueryEnabled(true);
	Kogger.debug(getClass(), "getProductInfoWTPart() Query : " + spec.toString());
	QueryResult qr = PersistenceServerHelper.manager.query(spec);
	if (qr != null) {
	    list = new ArrayList<HashMap<String, String>>();
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("partoid", objArr[0] + ":" + objArr[1]);
		map.put("partnumber", objArr[2].toString());
		map.put("partname", objArr[3].toString());
		list.add(map);
	    }
	}
	return list;
    }

    public String getProjectNameByProjectNo(String projectNo) throws Exception {
	String returnStr = null;
	QuerySpec spec = new QuerySpec();
	Class<E3PSProject> masterClass = E3PSProject.class;
	int idx = spec.appendClassList(masterClass, false);

	ClassAttribute classnameAttr = new ClassAttribute(masterClass, E3PSProject.PJT_NAME);
	spec.appendSelect(classnameAttr, new int[] { idx }, false);
	spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, "=", projectNo), new int[] { idx });
	spec.setDistinct(true);

	QueryResult qr = PersistenceServerHelper.manager.query(spec);
	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		returnStr = (String) objArr[0];
	    }
	}
	return returnStr;
    }

    public E3PSProject getProjectByProjectNo(String projectNo) throws Exception {
	E3PSProject project = null;
	QuerySpec spec = new QuerySpec();
	Class<E3PSProject> masterClass = E3PSProject.class;
	int idx = spec.appendClassList(masterClass, true);
	spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, "=", projectNo), new int[] { idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { idx });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { idx });

	QueryResult qr = PersistenceServerHelper.manager.query(spec);
	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		project = (E3PSProject) objArr[0];
	    }
	}
	return project;
    }

    public void updateMoldItem(Map<String, Object> map) {
	Transaction tx = new Transaction();
	String dieNo = "";
	String partNo = "";
	String partName = "";
	String productionPlace = "";
	NumberCode purchasePlace = null;
	MoldMaterial material = null;
	NumberCode property = null;
	String cavity = "";
	String ctSpm = "";
	String making = "";
	NumberCode makingPlace = null;
	String makingPlacePartnerNo = "";

	try {
	    // 부품 속성 정보 업데이트(부품명,생산처,원재료,원재료특성)
	    if (StringUtil.isEmpty(partNo)) {
		QuerySpec qs = new QuerySpec();
		int idx = qs.addClassList(MoldItemInfo.class, true);
		qs.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.PART_NO, SearchCondition.EQUAL, partNo));
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while (rs.hasMoreElements()) {
		    MoldItemInfo moldItemInfo = (MoldItemInfo) rs.nextElement();
		    moldItemInfo.setPartName(partName);
		    moldItemInfo.setProductionPlace(productionPlace);
		    moldItemInfo.setPurchasePlace(purchasePlace);
		    moldItemInfo.setMaterial(material);
		    moldItemInfo.setProperty(property);
		    moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
		}
	    }

	    // dieNo 속성정보 업데이트(Cavity, C/T, 제작처구분, 제작처)
	    if (StringUtil.isEmpty(dieNo)) {
		QuerySpec qs = new QuerySpec();
		int idx = qs.addClassList(MoldItemInfo.class, true);
		qs.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.DIE_NO, SearchCondition.EQUAL, dieNo));
		QueryResult rs = PersistenceHelper.manager.find(qs);
		while (rs.hasMoreElements()) {
		    MoldItemInfo moldItemInfo = (MoldItemInfo) rs.nextElement();
		    moldItemInfo.setCVPitch(cavity);
		    moldItemInfo.setCTSPM(ctSpm);
		    moldItemInfo.setMaking(making);
		    moldItemInfo.setMakingPlace(makingPlace);
		    moldItemInfo.setMakingPlacePartnerNo(makingPlacePartnerNo);
		    moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
		}
	    }
	    tx.commit();
	} catch (Exception e) {
	    if (tx != null)
		tx.rollback();
	    tx = null;
	    Kogger.error(getClass(), e);
	} finally {
	    if (tx != null)
		tx = null;
	}
    }

    /**
     * RANK(수정시) S이면 관련 모듈의 RANK도 변경 한다.
     * 
     * @param project
     * @param beforeRank
     * @param afterRank
     * @메소드명 : changeRankByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 31.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void changeRankByProject(E3PSProject project, String beforeRank, String afterRank) {
	try {
	    if (project != null && StringUtil.checkString(beforeRank) && StringUtil.checkString(afterRank)) {
		if (!beforeRank.equalsIgnoreCase(afterRank)) {
		    // 개발산출물 Rank 수정
		    KETProjectDocumentHelper.service.changeProjectRankModify(project, afterRank);
		    // 금형 Try조건표(그냥 똑같이 업데이트)
		    TryConditionHelper.service.changeMoldRankByProject(project);
		    // 도면 Rank 수정
		    new EpmInfoUpadator().updateSecurityInfoByProject(project.getPjtNo(), beforeRank, afterRank);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public void updateMoldItemForSync(String partNo, String dieNo) throws Exception {
	MoldProjectHelper.updateMoldItemForSync(partNo, dieNo);
    }

    public PartListDTO findProjectInfo1ForPartlist(String projectNo, PartListDTO partListDTO) throws Exception {
	if (projectNo != null) {
	    ProductProject project = (ProductProject) getProjectByProjectNo(projectNo);
	    if (project == null) {
		return partListDTO;
	    }
	    String carTypeOid = CommonUtil.getOIDString(project.getOemType());
	    ModelPlan modelPlan = ProgramHelper.service.getModelPlanByCarType(carTypeOid);

	    String sopEventDate = "";
	    String divisionRole = "";
	    String roleUserName = "";
	    String roleUserDept = "";
	    String oemPlanOid = CommonUtil.getOIDString(modelPlan);

	    // 적용차종 projectApplyCarType > ex) GM BEV GEN2
	    if (project.getOemType() != null) {
		partListDTO.setProjectApplyCarType(project.getOemType().getName());
		// 연계프로젝트 차종 일정 차종 일정 중 SOP 일정
		List<ProgramEventDTO> findEventByCarType = ProgramHelper.service.findEventByCarType(CommonUtil.getOIDString(project
		        .getOemType()));
		for (ProgramEventDTO programEventDTO : findEventByCarType) {
		    if ("SOP".equals(programEventDTO.getCarEventName())) {
			sopEventDate = programEventDTO.getCarEventDate();
			break;
		    }
		}
		partListDTO.setProjectSOP(sopEventDate);
	    }

	    // 프로젝트 CFT 제품개발 Role(자동차사업부:Team_PRODUCT01,전자사업부:Team_ELECTRON01)
	    if ("자동차 사업부".equals(project.getTeamType())) {
		divisionRole = "Team_PRODUCT01";
	    } else {
		divisionRole = "Team_ELECTRON01";
	    }
	    QueryResult qs = ProjectUserHelper.manager.getProjectRoleMember(project, divisionRole);
	    if (qs != null && qs.size() > 0) {
		ProjectMemberLink memberLink = (ProjectMemberLink) qs.nextElement();
		PeopleData roleUser = new PeopleData(memberLink.getMember());
		roleUserName = roleUser.name;
		roleUserDept = roleUser.departmentName;
	    }
	    partListDTO.setProjectDevOwner(roleUserName);
	    // 프로젝트 CFT 제품개발 Role 개발담당부서
	    partListDTO.setProjectDevDept(roleUserDept);

	    // 프로젝트 CFT 제품개발(H/W) Role --> 자동차사업부만 존재하는 Role임
	    roleUserName = "";
	    roleUserDept = "";
	    if ("자동차 사업부".equals(project.getTeamType())) {
		divisionRole = "Team_PRODUCT28";
		qs = ProjectUserHelper.manager.getProjectRoleMember(project, divisionRole);
		if (qs != null && qs.size() > 0) {
		    ProjectMemberLink memberLink = (ProjectMemberLink) qs.nextElement();
		    PeopleData roleUser = new PeopleData(memberLink.getMember());
		    roleUserName = roleUser.name;
		    roleUserDept = roleUser.departmentName;
		}
	    }
	    partListDTO.setProjectDevHwOwner(roleUserName);
	    // 프로젝트 CFT 제품개발(H/W) ex) 커넥터 개발 2팀
	    partListDTO.setProjectDevHwDept(roleUserDept);

	    // 예상수량 1년
	    if (modelPlan != null) {
		partListDTO.setProjectExpect1Qty(modelPlan.getYield1());
		partListDTO.setProjectExpect2Qty(modelPlan.getYield2());
		partListDTO.setProjectExpect3Qty(modelPlan.getYield3());
		partListDTO.setProjectExpect4Qty(modelPlan.getYield4());
		partListDTO.setProjectExpect5Qty(modelPlan.getYield5());
		partListDTO.setProjectExpect6Qty(modelPlan.getYield6());
		partListDTO.setProjectExpectSumQty(modelPlan.getTotal() + "");
	    }
	    ReflectUtil.toString(partListDTO);
	}
	return partListDTO;
    }

    public PartListItemDTO findProjectInfo2ForPartlist(String partNo, String dieNo, PartListItemDTO partListItemDTO) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostInfo.class, true);
	qs.appendWhere(new SearchCondition(CostInfo.class, CostInfo.PART_NO, SearchCondition.EQUAL, partNo), new int[] { idx });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostInfo.class, CostInfo.DIE_NO, SearchCondition.EQUAL, dieNo), new int[] { idx });
	QueryResult qr = PersistenceServerHelper.manager.query(qs);

	CostInfo costInfo = null;
	while (qr.hasMoreElements()) {
	    Object obj[] = (Object[]) qr.nextElement();
	    costInfo = (CostInfo) obj[0];
	    partListItemDTO.setStartInvestMoney(costInfo.getTargetCost());
	}
	ReflectUtil.toString(partListItemDTO);
	return partListItemDTO;
    }

    public PartListItemDTO findProjectInfo3ForPartlist(String partNo, String dieNo, PartListItemDTO partListItemDTO) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostInfo.class, true);
	qs.appendWhere(new SearchCondition(CostInfo.class, CostInfo.PART_NO, SearchCondition.EQUAL, partNo), new int[] { idx });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostInfo.class, CostInfo.DIE_NO, SearchCondition.EQUAL, dieNo), new int[] { idx });
	QueryResult qr = PersistenceServerHelper.manager.query(qs);

	CostInfo costInfo = null;
	while (qr.hasMoreElements()) {
	    Object obj[] = (Object[]) qr.nextElement();
	    costInfo = (CostInfo) obj[0];
	    partListItemDTO.setProdInvestMoney(costInfo.getTargetCost());
	    break;
	}
	MoldProject moldProject = MoldProjectHelper.getMoldProject(dieNo);
	if (moldProject != null && moldProject.getMoldMachine() != null && moldProject.getMoldMachine().getTon() != null) {

	    partListItemDTO.setPartProdConditionEquipTon(moldProject.getMoldMachine().getTon().getName());
	}
	ReflectUtil.toString(partListItemDTO);
	return partListItemDTO;
    }

    public PartListItemDTO findProjectInfo4ForPartlist(String partNo, PartListItemDTO partListItemDTO) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idxpi = qs.appendClassList(ProductInfo.class, true);
	SearchCondition cs = new SearchCondition(ProductInfo.class, ProductInfo.P_NUM, "=", partNo);
	qs.appendWhere(cs, new int[] { idxpi });
	QueryResult qr = PersistenceHelper.manager.find(qs);
	String partSupplyContract = "";
	while (qr.hasMoreElements()) {
	    Object obj[] = (Object[]) qr.nextElement();
	    ProductInfo pi = (ProductInfo) obj[0];
	    E3PSProjectData projectData = new E3PSProjectData(pi.getProject());
	    if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
		for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
		    NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
		    if (nc != null) {
			String masterName = nc.getName();
			if (i != 0) {
			    partSupplyContract += ",";
			}
			partSupplyContract += masterName;
		    }
		}
	    }
	}
	partListItemDTO.setPartSupplyContract(partSupplyContract);
	ReflectUtil.toString(partListItemDTO);
	return partListItemDTO;
    }

    public void deleteLinkProject(E3PSProject project) throws Exception {
	if (project instanceof ProductProject) {
	    String linkProjectNo = "";
	    if (((ProductProject) project).getProcess() != null) {
		NumberCode num = ((ProductProject) project).getProcess();
		if (num.getCode().equals("PC002") || num.getCode().equals("PC005")) {
		    linkProjectNo = project.getMaster().getLinkProjectNo();
		}
	    }
	    if (StringUtils.isNotEmpty(linkProjectNo) && ProjectHelper.getProject(linkProjectNo) != null) {
		E3PSProjectMaster master = (E3PSProjectMaster) ProjectHelper.getProject(linkProjectNo).getMaster();
		String pjtNos = master.getLinkProjectNo();
		String pjtNosNew = "";

		String target[] = pjtNos.split(",");
		for (String oldPjtNo : target) {
		    if (project.getMaster().getPjtNo().equals(oldPjtNo)) {
			continue;
		    }
		    pjtNosNew += oldPjtNo + ",";
		}
		pjtNosNew = StringUtils.removeEnd(pjtNosNew, ",");
		master.setLinkProjectNo(pjtNosNew);
		PersistenceHelper.manager.save(master);
	    }

	}
    }

}
