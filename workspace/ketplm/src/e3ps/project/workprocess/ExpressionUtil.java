package e3ps.project.workprocess;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.fc.WTReference;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.project.Role;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamException;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.team.TeamReference;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.project.E3PSProject;
import ext.ket.shared.log.Kogger;

public class ExpressionUtil {
	
	
	public static boolean isProjectCheck(Object obj)throws Exception{
		boolean isCheck = false;
		E3PSProject pjt = (E3PSProject)obj;
//		if(pjt.getPjtType() == 1){
			isCheck = true;
//		}
		return isCheck;
	}
	
	public static boolean checkTaskAssign(Object obj)throws Exception{
		boolean isCheck = false;
		E3PSProject pjt = (E3PSProject)obj;
		QuerySpec qs = new QuerySpec();
		Class class_ta = null;//TaskDepartmentProjectAssign.class;
		int idx = qs.addClassList(class_ta, true);
		
//		qs.appendWhere(new SearchCondition(class_ta, "projectReference.key.id", 
//				SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pjt)), new int[] {idx});
		
		qs.appendAnd();
		
		qs.appendWhere(new SearchCondition(class_ta, "state.state", 
				"<>", "COMPLETED"), new int[] {idx});
		
		QueryResult qr = PersistenceHelper.manager.find(qs);
		
		if(qr.size() == 0){
			isCheck = true;
		}
		return isCheck;
		
	}
	
	public static void completedProject(Object obj)throws Exception{
		
		Config conf = ConfigImpl.getInstance();				
		boolean isERPCheck = conf.getBoolean("ERPCHECK");
		
		if(obj instanceof E3PSProject) {
			
				
			E3PSProject jelProject = (E3PSProject)obj;
			
//			if(jelProject.getPjtType() != 2){
				
			
			
				boolean reflag = false;
				//Kogger.debug(getClass(), "#####################@@@@@@@@@@@@@@@ExpressionUtil[completedProject]");
				try{	
					
//					reflag = PJTInfoERPInterface.sendProjectInfoToSap(jelProject, System.out);
					
					//Kogger.debug(getClass(), "##################@@@@@@@@@@@@reflag>>>> "+reflag);
				}catch(Exception ex){
					Kogger.error(ExpressionUtil.class, ex);
					if(isERPCheck){
						throw new Exception(" ERP error");
					}
					//throw new Exception("error");
				}
				
				if(!reflag){
					try{
						//mailSend((E3PSProject)jelProject);
					}catch(Exception ex){
						Kogger.error(ExpressionUtil.class, ex);
					}
					if(isERPCheck){
						throw new Exception(" ERP error");
					}
				}
			
			
//			}
			
		}
		
//		TemplateProject project = (TemplateProject)obj;
		//Kogger.debug(getClass(), "project.isWorkingCopy()[JELProject]>>>>> "+((JELProject)obj).isWorkingCopy());
		//Kogger.debug(getClass(), "project.isWorkingCopy()[Template]>>>>>>> "+project.isWorkingCopy());
//		if(project.isWorkingCopy()){
//			//Kogger.debug(getClass(), "isWorkingCopy()>>>>checkIn");
//			project = HistoryHelper.checkIn(project);
//		}
//		
//		if(project instanceof JELProject){
//			try{
//				mailSend((JELProject)project);
//			}catch(Exception ex){
//				Kogger.error(getClass(), ex);
//			}
//		}
	}
	
	public static void getRoleProject(Object obj){
		
		boolean checkRole = false;
		try{
			wt.fc.Persistable per = (wt.fc.Persistable)obj;
			
	        
			Team team = TeamHelper.service.getTeam((TeamManaged) per);
			Role role = null;
	
			Vector vecRole = team.getRoles();
			for (int i = 0; i < vecRole.size(); i++)
			{
				role = (Role) vecRole.get(i);
				Kogger.debug(ExpressionUtil.class, role.getDisplay(java.util.Locale.KOREAN) + " : ");
				Enumeration r = team.getPrincipalTarget(role);
				
				if(role.getDisplay(java.util.Locale.KOREAN).toString().equals("조정자")){
					
					while (r.hasMoreElements())
					{
						WTPrincipalReference ref = (WTPrincipalReference) r.nextElement();
						System.out.print("######### 남아 있는 일정 조정자   :" +  ref.getPrincipal().getName());
						if(ref.getPrincipal() instanceof WTUser) {
							Kogger.debug(ExpressionUtil.class, "["+((WTUser)ref.getPrincipal()).getFullName()+ "]");
						}
						checkRole = true;
					}
				}
						
			}
			if(!checkRole){
				throw new Exception(" getRoleProject error");
			}
			
		}catch(Exception e){
			Kogger.error(ExpressionUtil.class, e);
		}
		
	}	
	
	
	
	private static void mailSend(E3PSProject project)throws Exception{
		WTUser from = (WTUser)SessionHelper.manager.getPrincipal();
		
//		QueryResult rs = ProjectUserHelper.manager.getMember(project);
		HashMap toHash = new HashMap();
		
//		while(rs.hasMoreElements()){	
//			Object[] objArr = (Object[])rs.nextElement();
//			ProjectMemberLink memberLink = (ProjectMemberLink)objArr[0];
//			wt.org.WTUser toUser = memberLink.getMember();
//			toHash.put(toUser.getEMail(), toUser.getFullName());
//		}
		
		
//		String subject = project.getPjtNo() + " 의 프로젝트가 결재완료 되었습니다."; 
//		
//		String projectNo = project.getPjtNo();

		
		
		String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
        String host = "http://" + hostName;
		String link = host;
		
//		String text = project.getPjtNo() + "["+ project.getPjtName() + "] 프로젝트의 일정변경으로 결재 요청한 프로젝트가  결재완료 되었습니다.<br>확인바랍니다.";
		
//		MailHtmlContentTemplate   contentTemplate = MailHtmlContentTemplate.getInstance();
		
		Hashtable hash = new Hashtable();
//		hash.put("subject", subject);
//		hash.put("text", text);
//		hash.put("link", link);
//		hash.put("projectNo", projectNo);
		
//		String content =  contentTemplate.htmlContent(hash, "ProjectPlanModify.html");
//		Kogger.debug(getClass(), "content = " + content);
		Hashtable mailHash = new Hashtable();
//		mailHash.put("FROM", from);
//		mailHash.put("TO", toHash);
//		mailHash.put("SUBJECT", subject);
		
		
//		mailHash.put("CONTENT", content);
//		boolean result = e3ps.common.mail.MailUtil.manager.sendMail(mailHash);
//		Kogger.debug(getClass(), "Project Task Mail 발송 : " + result);
	}
	
	public static void addPrincialToRole(WTObject obj, String roleName, WTPrincipal lUser)
	{
		if (obj instanceof TeamManaged)
	    {
	        try
	        {
	        	Team team = TeamHelper.service.getTeam((TeamManaged) obj);
				Enumeration userList = team.getPrincipalTarget(Role.toRole(roleName));
				while (userList.hasMoreElements()) {
				   TeamHelper.service.deleteRolePrincipalMap(Role.toRole(roleName),
						   								(wt.org.WTPrincipal)((wt.org.WTPrincipalReference)userList.nextElement()).getObject(),
						   								(wt.team.WTRoleHolder2) team);
				}
				if (lUser != null) {
					wt.team.TeamHelper.service.addRolePrincipalMap(Role.toRole(roleName)
		                                                             , lUser
		                                                             , (wt.team.WTRoleHolder2) team);
		            
		            wt.team.TeamHelper.service.augmentRoles(getLCMObject(team), wt.team.TeamReference.newTeamReference(team));
				}
	        }
	        catch (TeamException e)
	        {
	            Kogger.error(ExpressionUtil.class, e);
	        }
	        catch (WTException e)
	        {
	            Kogger.error(ExpressionUtil.class, e);
	        }
	        catch(Exception e) {
	        	Kogger.error(ExpressionUtil.class, e);
	        }
	    }
	}
	
    public static LifeCycleManaged getLCMObject(Team team) throws Exception
    {
		LifeCycleManaged localLifeCycleManaged = null;
		
		WTReference localWTReference = null;
		Vector localVector = TeamHelper.service.whereUsed(TeamReference.newTeamReference(team));
		if (localVector.size() > 0)
		{
			localWTReference = (WTReference)localVector.elementAt(0);
			if (localWTReference.getObject() instanceof LifeCycleManaged)
			{
				localLifeCycleManaged = (LifeCycleManaged)localWTReference.getObject();
			}
		}
		
		return localLifeCycleManaged;
	}
	
}
