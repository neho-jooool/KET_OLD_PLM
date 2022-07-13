package ext.ket.project.work.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import e3ps.common.code.NumberCode;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.ExtendScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.WorkProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectScheduleHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.project.Role;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;


public class StandardWorkProjectService extends StandardManager implements WorkProjectService {
    private static final long serialVersionUID = 1L;
    
    public static StandardWorkProjectService newStandardWorkProjectService() throws WTException {
	StandardWorkProjectService instance = new StandardWorkProjectService();
	instance.initialize();
	return instance;
    }

    @Override
    public Map<String, Object> saveWorkProject(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	
	String oid = (String)reqMap.get("oid");
	
	WorkProject project = (WorkProject)CommonUtil.getObject(oid);
	
	String projectName = (String) reqMap.get("pjtName");
	
	String rank = (String) reqMap.get("rank");
	String workType = (String) reqMap.get("workType");
	
        String devUser = (String) reqMap.get("devUser");
        
        WTUser wtdevUser = (WTUser) CommonUtil.getObject(devUser);
        
        if(project != null){
        
            if (StringUtil.checkString(projectName.trim())) {
                project.setPjtName(StringUtil.checkNull(projectName.trim()));
                E3PSProjectMaster em = (E3PSProjectMaster) project.getMaster();
                em.setPjtName(projectName);
                PersistenceHelper.manager.modify(em);
            }
    	
            if (rank != null && rank.length() > 0) {
                NumberCode n4 = (NumberCode) CommonUtil.getObject(rank);
                project.setRank(n4);
            }
            
            if (workType != null && workType.length() > 0) {
                NumberCode n4 = (NumberCode) CommonUtil.getObject(workType);
                project.setWorkType(n4);
            }
            
            
            WTUser oldPm = ProjectUserHelper.manager.getPM(project);
            

            PeopleData peo = new PeopleData(wtdevUser);
            project.setDevDept(peo.department);
            
            
            project = (WorkProject)PersistenceHelper.manager.modify(project);
    
            if(!oldPm.getName().equals(wtdevUser.getName())){
        	ProjectUserHelper.manager.replacePM(project, wtdevUser);
            }
            return reqMap;
        }
        
        String prepix = "";
        String numberingRule = "";
        
        NumberCode workTypeNumberCode = (NumberCode) CommonUtil.getObject(workType);
        String workTypeCode = "";
        
        if (workTypeNumberCode != null) {
            workTypeCode = workTypeNumberCode.getCode();
        }
        
        switch(workTypeCode) {
            
        case "CODE002" : //양산제품개선
            prepix = "R";
            numberingRule = "A";
            break;
            
        case "CODE001" : //설비이관
            prepix = "T";
            numberingRule = "B";
            break;
        
        case "CODE003" : //금형이관
            prepix = "T";
            numberingRule = "B";
            break;
            
        case "CODE004" : //설비/금형이관
            prepix = "T";
            numberingRule = "B";
            break;
            
        default :
            prepix = "W";
            numberingRule = "B";
            
        }
	
	String tempDate = DateUtil.getDateString(new Date(), "all");
	
	
	String projectNo = prepix + tempDate.substring(2, 4) + numberingRule;
	
        projectNo = projectNo + ManageSequence.getSeqNo(projectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);
        
        String planStartDate = (String) reqMap.get("planStartDate");
        String planEndDate = "";

        // Template
        String tempid = (String) reqMap.get("templateOid");
        
        String[] category = (String[]) reqMap.get("category"); // category
        String lifecycle = "KET_PRODUCT_PMS_LC";

        

        Calendar tempCal = Calendar.getInstance();
        Transaction transaction = new Transaction();
        try {
            transaction.start();
            project = WorkProject.newWorkProject();

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
            FolderHelper.assignLocation((FolderEntry) project,
                    FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef()));
            LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
            LifeCycleHelper.setLifeCycle(project, lct);


            project.setPjtNo(projectNo.trim());
            project.setPjtSeq(0);
            project.setPjtHistory(0);
            project.setPjtIteration(0);
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
           
            if (rank != null && rank.length() > 0) {
                NumberCode n4 = (NumberCode) CommonUtil.getObject(rank);
                project.setRank(n4);
            }
            
            if (workTypeNumberCode != null) {
                project.setWorkType(workTypeNumberCode);
            }
            WTUser devPm = (WTUser) SessionHelper.getPrincipal();
            
         // PM 등록
            if (devUser != null && devUser.length() != 0) {
                wtdevUser = (WTUser) CommonUtil.getObject(devUser);
            }

            if (wtdevUser != null){
        	PeopleData peo = new PeopleData(wtdevUser);
        	project.setDevDept(peo.department);
            }

            project = (WorkProject) PersistenceHelper.manager.save(project);

            
            if (wtdevUser != null){
                ProjectUserHelper.manager.replacePM(project, wtdevUser);
            }

            Role role = null;
            WTUser wtuser = null;
            TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
            Vector vecTeamStd = tempTeam.getRoles();
            
            for (int i = 0; i < vecTeamStd.size(); i++) {
        	role = (Role) vecTeamStd.get(i);
        	String desc = role.getShortDescription();
        	System.out.println("desc : " + desc);
        	
            }
//
//            for (int i = 0; i < vecTeamStd.size(); i++) {
//                role = (Role) vecTeamStd.get(i);
//                String userOid = (String) hash.get(role.toString());
//
//                if (userOid != null && userOid.length() > 0) {
//                    wtuser = (WTUser) CommonUtil.getObject((String) hash.get(role.toString()));
//                    if (wtuser != null) {
//                        ProjectUserHelper.manager.setRoleMember(project, role.toString(), wtuser, 0);
//                    }
//                }
//            }

            // Member Setting
//            Vector projectMember = (Vector) hash.get("USERMEMBER");
//            if (projectMember != null && projectMember.size() > 0) {
//                for (int i = 0; i < projectMember.size(); i++) {
//                    wtuser = (WTUser) CommonUtil.getObject((String) projectMember.get(i));
//                    if (wtuser != null)
//                        ProjectUserHelper.manager.setMember(project, wtuser, 0);
//                }
//            }
            
            // TemplateProject Link Create
            if (templateProject != null) {
                ProjectHelper.manager.copyProjectInfo(project, templateProject);
                TaskHelper.manager.copyProjectFromTemplateNew(project, templateProject, category);
                ProjectUserHelper.settingRoleTaskMember(project);
            }

            transaction.commit();

            ProjectScheduleHelper.settingProgress(project);
            transaction = null;

            // 프로젝트 PM에게 메일 발송
            List<WTUser> toUser = new ArrayList<WTUser>();
            if (wtdevUser != null) {
                toUser.add(wtdevUser);
                if (toUser.size() > 0) {
                    KETMailHelper.service.sendFormMail("08046", "NoticeMailLine2.html", project,(WTUser) SessionHelper.getPrincipal(), toUser);
                }
            }
            
            reqMap.put("oid", CommonUtil.getOIDString(project));

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            transaction = null;
            Kogger.error(getClass(), e);
        } finally {
            if (transaction != null)
                transaction = null;
        }
        return reqMap;
    }
}