package e3ps.project.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.drools.core.util.StringUtils;

import wt.fc.ObjectToObjectLink;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.PhaseTemplate;
import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.method.RemoteMethodServer;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pds.DatabaseInfoUtilities;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.KeywordExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.query.TableExpression;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.mail.MailHtmlContentTemplate;
import e3ps.common.mail.MailUtil;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.E3PSClassTableExpression;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ews.dao.PartnerDao;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.CheckoutLink;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ModelInfo;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.Performance;
import e3ps.project.ProductInfo;
import e3ps.project.ProductModelLink;
import e3ps.project.ProductProject;
import e3ps.project.ProductProjectMoldInfoLink;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ProjectCostInfoLink;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectIssueLink;
import e3ps.project.ProjectMaster;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewDevelop;
import e3ps.project.ReviewDevelopProjectLink;
import e3ps.project.ReviewProject;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.Weights;
import e3ps.project.WorkProject;
import e3ps.project.cancel.ProjectCancelLink;
import e3ps.project.customerPlan.beans.CustomerPlanHelper;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.machine.MoldMachine;
import e3ps.project.material.MoldMaterial;
import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.sap.ProductPrice;
import e3ps.project.sap.SAPMoldPrice;
import e3ps.project.trySchdule.TrySchdule;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.program.entity.KETProgramProjectLink;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;

@SuppressWarnings("serial")
public class ProjectHelper implements wt.method.RemoteAccess, java.io.Serializable {

    public static final int CAR_REVIEW = 0;
    public static final int ELECTRON_REVIEW = 1;
    public static final int CAR_PRODUCT_PROJECT = 2;
    public static final int MOLD_PROJECT = 3;
    public static final int ELECTRON_PRODUCT_PROJECT = 4;
    public static final int WORK_PROJECT = 5;

    public static final String CAR_REVIEW_TEAM = "Review Project";
    public static final String ELECTRON_REVIEW_TEAM = "Review Project";
    public static final String CAR_PRODUCT_PROJECT_TEAM = "Product Project";
    public static final String MOLD_PROJECT_TEAM = "Mold Project";
    public static final String ELECTRON_PRODUCT_PROJECT_TEAM = "Electron Project";
    public static final String WORK_PROJECT_TEAM = "Work Project";

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    public static final ProjectHelper manager = new ProjectHelper();

    protected ProjectHelper() {
    }

    static boolean isMailSend = false;
    static {
	Config conf = ConfigImpl.getInstance();
	isMailSend = conf.getBoolean("e3ps.mail.enable");
    }

    public static TemplateProject changeState(TemplateProject project, String state) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, String.class };
	    Object args[] = new Object[] { project, state };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("changeState", ProjectHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (TemplateProject) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    project = (E3PSProject) LifeCycleHelper.service.setLifeCycleState(project, State.toState(state), true);

	    // boolean isSuccess = PJTInfoERPInterface.setStateChange((JELProject)project);
	    //
	    // if(!isSuccess && isERPCheck){
	    // throw new Exception("erp error");
	    // }
	    // out.println();

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return project;
    }

    public static String getPlanProjectComplete(E3PSProject jelProject) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { jelProject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getPlanProjectComplete", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (String) obj;
	}

	try {
	    double compIntValue = 0.0D;
	    E3PSProjectData pjtData = new E3PSProjectData(jelProject);
	    int timeDiff = DateUtil.getDaysDiff(DateUtil.getDateString(DateUtil.getCurrentTimestamp(), "d"),
		    DateUtil.getDateString(pjtData.pjtPlanStartDate, "d"));
	    compIntValue = ((double) timeDiff / (double) pjtData.pjtDuration) * (double) 100;
	    if (compIntValue > 99D)
		compIntValue = 100D;
	    return String.valueOf((int) compIntValue);
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}
	return null;
    }

    public static boolean deleteProjectUser(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteProjectUser", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");
	    Vector deleteMember = (Vector) map.get("deleteMember");

	    ReferenceFactory rf = new ReferenceFactory();
	    WTUser member = null;

	    Object obj = rf.getReference(oid).getObject();
	    if (obj instanceof TemplateProject) {
		TemplateProject project = (TemplateProject) obj;
		if (deleteMember != null && deleteMember.size() > 0) {
		    for (int i = 0; i < deleteMember.size(); i++) {
			if (deleteMember.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) deleteMember.get(i)).getObject();
			} else if (deleteMember.get(i) instanceof WTUser) {
			    member = (WTUser) deleteMember.get(i);
			}
			ProjectUserHelper.manager.deleteProjectUser(project, member);
		    }
		}
	    } else if (obj instanceof TemplateTask) {

	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);

	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    public static boolean deleteRefMember(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteRefMember", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();
	    String oid = (String) map.get("oid");
	    Vector deleteMember = (Vector) map.get("member");
	    ReferenceFactory rf = new ReferenceFactory();
	    WTUser member = null;
	    Object obj = rf.getReference(oid).getObject();
	    if (obj instanceof TemplateProject) {
		TemplateProject project = (TemplateProject) obj;
		if (deleteMember != null && deleteMember.size() > 0) {
		    for (int i = 0; i < deleteMember.size(); i++) {
			if (deleteMember.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) deleteMember.get(i)).getObject();
			} else if (deleteMember.get(i) instanceof WTUser) {
			    member = (WTUser) deleteMember.get(i);
			}
			ProjectUserHelper.manager.deleteRefMember(project, member);
		    }
		}
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);

	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    // public static boolean assignProjectMember(HashMap map) throws Exception {
    // if(!SERVER) {
    // Class argTypes[] = new Class[]{HashMap.class};
    // Object args[] = new Object[]{map};
    // Object obj = null;
    // try {
    // obj = wt.method.RemoteMethodServer.getDefault().invoke(
    // "assignProjectMember",
    // "e3ps.project.beans.ProjectHelper",
    // null,
    // argTypes,
    // args);
    // }
    // catch(RemoteException e) {
    // Kogger.error(ProjectHelper.class, e);
    // throw new WTException(e);
    // }
    // catch(InvocationTargetException e) {
    // Kogger.error(ProjectHelper.class, e);
    // throw new WTException(e);
    // }
    // return ((Boolean)obj).booleanValue();
    // }
    // String oid = (String)map.get("oid");
    // ReferenceFactory rf = new ReferenceFactory();
    // Object obj = rf.getReference(oid).getObject();
    // TemplateProject project = (TemplateProject)obj;
    //
    // TeamTemplate teamtemplate = TeamHelper.service.getTeamTemplate("Team_Project");
    // Vector roles = teamtemplate.getRoles();
    // Role role = null;
    // Vector roleVec = null;
    // for (int i = roles.size() - 1; i > -1; i--) {
    // role = (Role) roles.get(i);
    // roleVec = (Vector)map.get(role.toString());
    // if(roleVec == null || roleVec.size() == 0) {
    // QueryResult rs = getRoleMember(project, role.toString());
    // while(rs.hasMoreElements()){
    // ProjectMemberLink link = (ProjectMemberLink)rs.nextElement();
    // ProjectUserHelper.manager.deleteProjectUser(link);
    // }
    // }else{
    // String userOid = (String)roleVec.get(0);
    // WTUser newRoleMember = (WTUser)CommonUtil.getObject(userOid);
    //
    // ProjectMemberLink newLink = ProjectUserHelper.manager.saveProjectMember(project, newRoleMember, role.toString(),
    // ProjectUserHelper.ROLEMEMBER);
    // if(newLink != null) {
    // setOutputOwner(project, newRoleMember, role.toString());
    // }
    // }
    // }
    //
    // return true;
    //
    // }
    //
    public static QueryResult getRoleMember(TemplateProject project, String roleName) throws Exception {

	Class targetClass = ProjectMemberLink.class;
	QuerySpec qs = new QuerySpec(targetClass);
	int targetClassPos = 0;
	SearchCondition sc = null;

	sc = new SearchCondition(ProjectMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, project.getPersistInfo()
	        .getObjectIdentifier().getId());
	qs.appendWhere(sc, new int[] { 0 });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL,
	        ProjectUserHelper.ROLEMEMBER), new int[] { targetClassPos });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_ROLE, SearchCondition.EQUAL, roleName),
	        new int[] { targetClassPos });

	QueryResult result = PersistenceHelper.manager.find(qs);

	return result;

    }

    public static boolean assignProjectMember(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("assignProjectMember", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}
	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");
	    Vector pmVec = (Vector) map.get("PM");
	    Vector plVec = (Vector) map.get("PL");
	    Vector memberVec = (Vector) map.get("MEMBER");
	    Vector refMemberVec = (Vector) map.get("REF_MEMBER");

	    ReferenceFactory rf = new ReferenceFactory();
	    Role role = null;
	    Vector roleVec = null;

	    Object obj = rf.getReference(oid).getObject();
	    if (obj instanceof TemplateProject) {
		TemplateProject project = (TemplateProject) obj;
		// NEW
		if (memberVec != null && memberVec.size() > 0) {
		    // Kogger.debug(ProjectHelper.class, "MEMBER!!!");
		    WTUser member = null;
		    for (int i = 0; i < memberVec.size(); i++) {
			member = null;
			if (memberVec.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) memberVec.get(i)).getObject();
			} else if (memberVec.get(i) instanceof WTUser) {
			    member = (WTUser) memberVec.get(i);
			}

			ProjectUserHelper.manager.setMember(project, member);
		    }
		} else if (plVec != null && plVec.size() > 0) {
		    // Kogger.debug(ProjectHelper.class, "PL");
		    WTUser member = null;
		    for (int i = 0; i < plVec.size(); i++) {
			member = null;
			if (plVec.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) plVec.get(i)).getObject();
			} else if (plVec.get(i) instanceof WTUser) {
			    member = (WTUser) plVec.get(i);
			}
			ProjectUserHelper.manager.setPL(project, member, project.getPjtHistory());
		    }
		} else if (pmVec != null && pmVec.size() > 0) {
		    // Kogger.debug(ProjectHelper.class, "PM");
		    WTUser member = null;
		    for (int i = 0; i < pmVec.size(); i++) {
			member = null;
			if (pmVec.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) pmVec.get(i)).getObject();
			} else if (pmVec.get(i) instanceof WTUser) {
			    member = (WTUser) pmVec.get(i);
			}
			ProjectUserHelper.manager.setPM(project, member, project.getPjtHistory());
		    }
		} else if (refMemberVec != null && refMemberVec.size() > 0) {
		    WTUser member = null;
		    for (int i = 0; i < refMemberVec.size(); i++) {
			member = null;
			if (refMemberVec.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) refMemberVec.get(i)).getObject();
			} else if (refMemberVec.get(i) instanceof WTUser) {
			    member = (WTUser) refMemberVec.get(i);
			}

			QueryResult oldMembers = ProjectUserHelper.manager.getQueryForTeamUsers(project, member, ProjectUserHelper.MEMBER);
			if (oldMembers.size() == 0) {
			    ProjectUserHelper.manager.setViewMember(project, member, project.getPjtHistory());
			}
		    }
		} else {
		    TeamTemplate teamtemplate = null;

		    if (obj instanceof ReviewProject) {
			teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
		    } else if (obj instanceof ProductProject) {

			if ("자동차 사업부".equals(((ProductProject) obj).getTeamType())) {
			    teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
			} else if ("KETS".equals(((ProductProject) obj).getTeamType())
			        || "KETS_PMO".equals(((ProductProject) obj).getTeamType())) {
			    teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
			} else {
			    teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
			}

		    } else if (obj instanceof MoldProject) {
			teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
		    } else if (obj instanceof WorkProject) {
			teamtemplate = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
		    }

		    Vector roles = teamtemplate.getRoles();

		    for (int i = roles.size() - 1; i > -1; i--) {
			role = (Role) roles.get(i);
			roleVec = (Vector) map.get(role.toString());

			QueryResult rs = getRoleMember(project, role.toString());
			while (rs.hasMoreElements()) {
			    ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();
			    ProjectUserHelper.manager.deleteProjectUser(link);
			}

			if ((roleVec == null) || (roleVec.size() == 0)) {
			    continue;
			}

			String userOid = (String) roleVec.get(0);
			WTUser newRoleMember = (WTUser) CommonUtil.getObject(userOid);

			ProjectMemberLink newLink = ProjectUserHelper.manager.saveProjectMember(project, newRoleMember, role.toString(),
			        ProjectUserHelper.ROLEMEMBER);
		    }

		    ProjectUserHelper.syncTaskMemberUser(project);

		}

	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);

	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return true;
    }

    public static boolean setPjtRoleMember(Object object, Vector vector, String roleName) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Object.class, Vector.class, String.class };
	    Object args[] = new Object[] { object, vector, roleName };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setPjtRoleMember", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	try {
	    if (vector == null)
		return false;

	    ReferenceFactory rf = new ReferenceFactory();

	    WTUser newRoleMember = null;
	    HashMap exist = null;
	    ProjectMemberLink link = null;
	    if (object instanceof E3PSProject) {
		TemplateProject project = (TemplateProject) object;

		if (vector.size() == 0) {
		    exist = new HashMap();
		    exist.put("TemplateProject", project);
		    // exist.put("WTUser", member);
		    exist.put("pjtRole", roleName);
		    QueryResult qr = getProjectMember(exist);
		    if (qr.size() > 0) {
			Object obj[] = null;
			while (qr.hasMoreElements()) {
			    obj = (Object[]) qr.nextElement();
			    link = (ProjectMemberLink) obj[0];
			    // 사이즈가 0인 경우에 현재 assign된 role 담당자 삭제.
			    ProjectUserHelper.manager.deleteProjectUser(link);
			    // 산출물의 Owner를 Null 처리.

			    setOutputOwner(project, null, roleName);
			}
		    }
		} else {
		    for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i) instanceof String) {
			    String newWTUserOid = (String) vector.get(i);
			    if (newWTUserOid == null || newWTUserOid.trim().length() == 0) {
				return false;
			    }
			    newRoleMember = (WTUser) rf.getReference(newWTUserOid).getObject();
			} else if (vector.get(i) instanceof WTUser) {
			    newRoleMember = (WTUser) vector.get(i);
			} else {
			    newRoleMember = null;
			}

			if (newRoleMember != null) {
			    ProjectMemberLink newLink = ProjectUserHelper.manager.saveProjectMember(project, newRoleMember, roleName,
				    ProjectUserHelper.ROLEMEMBER);
			    if (newLink != null) {
				setOutputOwner(project, newRoleMember, roleName);
			    }
			}
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    return false;
	}
	return true;
    }

    public static void setOutputOwner(TemplateProject project, WTUser wtuser, String roleName, WTUser oldUser) throws Exception {

	WTPrincipalReference wtreference = null;
	if (wtuser == null) {
	    wtreference = WTPrincipalReference.newWTPrincipalReference();
	} else {
	    wtreference = WTPrincipalReference.newWTPrincipalReference(wtuser);
	}

	ProjectOutput output = null;
	Object[] obj = null;
	ProjectOutputData data = null;
	// ADD(DaeSeung)

	// Kogger.debug(ProjectHelper.class, "oldUser =========== " + oldUser.getFullName());
	QueryResult roleQr = ProjectOutputHelper.manager.getOutputByRole(project, roleName, oldUser);

	while (roleQr != null && roleQr.hasMoreElements()) {
	    obj = (Object[]) roleQr.nextElement();
	    output = (ProjectOutput) obj[0];
	    data = new ProjectOutputData(output);

	    // NEW
	    if (data.document == null) {
		output.setOwner(wtreference);
		ProjectOutputHelper.manager.modifyProjectOutput(output);
	    }
	}
    }

    public static boolean setOutputOwner(TemplateProject project, WTUser wtuser, String roleName) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, WTUser.class, String.class };
	    Object args[] = new Object[] { project, wtuser, roleName };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setOutputOwner", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return ((Boolean) obj).booleanValue();
	}

	try {
	    WTPrincipalReference wtreference = null;
	    if (wtuser == null) {
		wtreference = WTPrincipalReference.newWTPrincipalReference();
	    } else {
		wtreference = WTPrincipalReference.newWTPrincipalReference(wtuser);
	    }

	    ProjectOutput output = null;
	    ProjectOutputData data = null;
	    Object[] obj = null;

	    // ADD(DaeSeung)
	    QueryResult roleQr = ProjectOutputHelper.manager.getOutputByRole(project, roleName);
	    while (roleQr != null && roleQr.hasMoreElements()) {
		obj = (Object[]) roleQr.nextElement();
		output = (ProjectOutput) obj[0];
		data = new ProjectOutputData(output);
		// NEW
		if (data.document == null) {
		    output.setOwner(wtreference);
		    ProjectOutputHelper.manager.modifyProjectOutput(output);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    return false;
	}
	return true;
    }

    public static void setPjtMember(Object object, Vector vector, int projectRole) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Object.class, Vector.class, int.class };
	    Object args[] = new Object[] { object, vector, new Integer(projectRole) };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setPjtMember", "e3ps.project.beans.ProjectHelper", null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return;
	}

	try {
	    ReferenceFactory rf = new ReferenceFactory();

	    WTUser member = null;
	    if (object instanceof TemplateProject) {
		TemplateProject project = (TemplateProject) object;
		if (vector != null && vector.size() > 0) {
		    for (int i = 0; i < vector.size(); i++) {
			member = null;
			if (vector.get(i) instanceof String) {
			    member = (WTUser) rf.getReference((String) vector.get(i)).getObject();
			} else if (vector.get(i) instanceof WTUser) {
			    member = (WTUser) vector.get(i);
			}

			if (member != null) {
			    if (ProjectUserHelper.manager.getProjectMemberLink(project, member) == null) {
				if (projectRole == ProjectUserHelper.PM) {
				    ProjectUserHelper.manager.setPM(project, member, project.getPjtHistory());
				} else if (projectRole == ProjectUserHelper.PL) {
				    ProjectUserHelper.manager.setPL(project, member, project.getPjtHistory());
				} else if (projectRole == ProjectUserHelper.MEMBER) {
				    ProjectUserHelper.manager.setMember(project, member, project.getPjtHistory());
				} else if (projectRole == ProjectUserHelper.ONLY_VIEW_MEMBER) {
				    ProjectUserHelper.manager.setViewMember(project, member, project.getPjtHistory());
				}
			    }
			}
		    }
		}
	    } else if (object instanceof TemplateTask) {

	    }
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}
    }

    public static QueryResult getProjectMember(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getProjectMember", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (QueryResult) obj;
	}

	QueryResult qr = null;
	try {
	    TemplateProject project = (TemplateProject) map.get("TemplateProject");
	    WTUser wtuser = (WTUser) map.get("WTUser");
	    String pjtRole = (String) map.get("pjtRole");
	    int pjtMemberType = -1;

	    ReferenceFactory rf = new ReferenceFactory();
	    if (project == null) {
		String pjtOid = (String) map.get("oid");
		if (pjtOid != null && pjtOid.length() > 0)
		    project = (TemplateProject) rf.getReference(pjtOid).getObject();
	    }

	    if (wtuser == null) {
		String wtuserOid = (String) map.get("wtuserOid");
		if (wtuserOid != null && wtuserOid.length() > 0)
		    wtuser = (WTUser) rf.getReference(wtuserOid).getObject();
	    }

	    if (pjtRole == null)
		pjtRole = "";

	    if (map.get("pjtMemberType") != null) {
		pjtMemberType = ((Integer) map.get("pjtMemberType")).intValue();
	    }

	    Class memberClass = ProjectMemberLink.class;
	    QuerySpec qs = new QuerySpec();
	    int idx = qs.addClassList(memberClass, true);

	    SearchCondition sc = null;
	    ClassAttribute ca = null;

	    if (project != null) {
		sc = new SearchCondition(memberClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, project.getPersistInfo()
		        .getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { idx });

		// qs.appendAnd();
		// sc = new SearchCondition(memberClass, "pjtHistory",
		// SearchCondition.EQUAL,
		// project.getPjtHistory());
		// qs.appendWhere(sc, new int[]{idx});

	    }

	    if (wtuser != null) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(memberClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, wtuser.getPersistInfo()
		        .getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { idx });
	    }

	    if (pjtMemberType > -1) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(memberClass, "pjtMemberType", SearchCondition.EQUAL, pjtMemberType);
		qs.appendWhere(sc, new int[] { idx });
	    }

	    if (pjtRole.length() > 0) {
		if (qs.getConditionCount() > 0) {
		    qs.appendAnd();
		}

		sc = new SearchCondition(memberClass, "pjtRole", SearchCondition.EQUAL, pjtRole);
		qs.appendWhere(sc, new int[] { idx });
	    }

	    qr = PersistenceHelper.manager.find(qs);
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}
	return qr;
    }

    /**
     * ProjectNO Dupulication Check
     * 
     * @param projectNo
     * @return true(ProjectNO Use), false(ProjectNO Non-Use)
     */
    public boolean checkProjectNo(String projectNo) {
	try {
	    QuerySpec spec = new QuerySpec(E3PSProject.class);
	    SearchUtil.appendEQUAL(spec, E3PSProject.class, E3PSProject.PJT_NO, projectNo.toUpperCase(), 0);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null && result.size() > 0) {
		return true;
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return false;
    }

    public QueryResult getProjectAll(String pjtNo) {
	try {
	    QuerySpec spec = new QuerySpec(E3PSProject.class);
	    SearchUtil.appendEQUAL(spec, E3PSProject.class, E3PSProject.PJT_NO, pjtNo, 0);
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result != null && result.size() > 0) {
		return result;
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public void deleteTask(TemplateTask task) {
	try {
	    PersistenceHelper.manager.delete(task);
	    /*
	     * TaskUserHelper.manager.deleteTaskUser(task); TaskDependencyHelper.manager.deleteTaskDependency(task);
	     * ProjectOutputHelper.manager.deleteTaskOutput(task); if (task instanceof TemplateTask) { TemplateTask tempTask =
	     * (TemplateTask) task; ScheduleData tempSchedule = (ScheduleData) tempTask.getTaskSchedule().getObject();
	     * PersistenceHelper.manager.delete(tempSchedule); PersistenceHelper.manager.delete(tempTask); } else if (task instanceof
	     * JELTask) { JELTask jelTask = (JELTask) task; ExtendScheduleData jelSchedule = (ExtendScheduleData)
	     * jelTask.getTaskSchedule().getObject(); PersistenceHelper.manager.delete(jelSchedule);
	     * PersistenceHelper.manager.delete(jelTask); }
	     */
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public E3PSProject setStopProject(E3PSProject project, boolean state) throws Exception {
	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	String subject = "";
	int lowerState = 0;
	if (state) {
	    // HistoryManager history =
	    // ScheduleHistoryHelper.manager.saveHistory(project,"e3ps.project.ScheduleIssueType.PROJECTSTOP",subject);
	    // ScheduleHistoryHelper.manager.saveHistory(project,history);
	    project.setPjtState(ProjectStateFlag.PROJECT_STATE_HOLD);
	} else {
	    // ProjectHistory history =
	    // ScheduleHistoryHelper.manager.saveHistory(project,"e3ps.project.ScheduleIssueType.PROJECTING",subject);
	    // ScheduleHistoryHelper.manager.saveHistory(project,history);
	    project.setPjtState(ProjectStateFlag.PROJECT_STATE_PROGRESS);
	}
	project = (E3PSProject) PersistenceHelper.manager.modify(project);
	return project;
    }

    public E3PSProject setStartProject(E3PSProject project, boolean state) throws Exception {
	if (state) {
	    project.setPjtState(ProjectStateFlag.PROJECT_STATE_PROGRESS);
	}
	project = (E3PSProject) PersistenceHelper.manager.modify(project);
	return project;
    }

    public void completeProject(E3PSProject project, boolean state) {
	if (state) {
	    // 종료
	    try {

		LifeCycleHelper.service.setLifeCycleState(project, State.toState("COMPLETED"), true);

		// String location = "/"+ConfigImpl.getInstance().getString("e3ps.common.workflow.E3PSPBO.cabinet");
		// String lifecycle = ConfigImpl.getInstance().getString("e3ps.drawing.E3PSProject.LifeCycle");
		// project.setPjtState(ProjectStateFlag.PROJECT_STATE_COMPLETE);
		// project = (JELProject)PersistenceHelper.manager.modify(project);

		// LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle);
		// Folder folder = null;
		// try {
		// folder = FolderHelper.service.getFolder(location);
		// } catch( FolderNotFoundException e ) {
		// FolderHelper.service.createSubFolder(location);
		// folder=FolderHelper.service.getFolder(location);
		// }
		// E3PSPBO pbo = E3PSPBO.newE3PSPBO();
		// pbo = (E3PSPBO)LifeCycleHelper.setLifeCycle(pbo, lct);
		// FolderHelper.assignLocation(pbo, folder);
		// pbo.setTarget(project);
		// pbo = (E3PSPBO)PersistenceHelper.manager.save(pbo);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	} else {
	    // 종료 취소
	    try {
		project.setPjtState(ProjectStateFlag.PROJECT_STATE_PROGRESS);
		project = (E3PSProject) PersistenceHelper.manager.modify(project);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
    }

    public static E3PSProject getJELProjectForEChange(String pjtNo) throws WTException, WTPropertyVetoException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class };
	    Object args[] = new Object[] { pjtNo };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("getJELProjectForEChange", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	// select a0.* from jelproject a0 where a0.pjthistory = (select max(a1.pjthistory) from jelproject a1 where a1.pjtno = a0.pjtno) and
	// a0.pjtno = 'SO2006110001'

	try {
	    QuerySpec spec = new QuerySpec();
	    spec.setAdvancedQueryEnabled(true);

	    Class target = E3PSProject.class;
	    int idx_target = spec.addClassList(target, true);

	    QuerySpec subQr = new QuerySpec();
	    subQr.getFromClause().setAliasPrefix("B");
	    int idx_subQr = subQr.addClassList(target, false);

	    ClassAttribute attribute = new ClassAttribute(target, E3PSProject.PJT_HISTORY);
	    SQLFunction maxFunc = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, attribute);
	    subQr.appendSelect(maxFunc, new int[] { idx_subQr }, false);

	    // where
	    TableExpression[] tables = new TableExpression[2];
	    String[] aliases = new String[2];

	    // select
	    tables[0] = spec.getFromClause().getTableExpressionAt(idx_target);
	    aliases[0] = spec.getFromClause().getAliasAt(idx_target);

	    // subselect
	    tables[1] = subQr.getFromClause().getTableExpressionAt(idx_subQr);
	    aliases[1] = subQr.getFromClause().getAliasAt(idx_subQr);
	    SearchCondition corredlatedJoin = new SearchCondition(target, E3PSProject.PJT_NO, target, E3PSProject.PJT_NO);
	    subQr.appendWhere(corredlatedJoin, tables, aliases);

	    // SubQuery End

	    ClassAttribute history = new ClassAttribute(target, E3PSProject.PJT_HISTORY);
	    spec.appendWhere(new SearchCondition(history, SearchCondition.EQUAL, new SubSelectExpression(subQr)), new int[] { idx_target });
	    SearchUtil.appendEQUAL(spec, target, E3PSProject.PJT_NO, pjtNo, idx_target);

	    QueryResult result = PersistenceHelper.manager.find(spec);
	    E3PSProject project = null;
	    while (result != null && result.hasMoreElements()) {
		Object[] obj = (Object[]) result.nextElement();
		project = (E3PSProject) obj[0];
	    }
	    return project;
	} catch (QueryException e) {
	    Kogger.error(ProjectHelper.class, e);
	}
	return null;

    }

    public void copyTemplateProjectInfo(TemplateProject project, TemplateProject template) {
	try {
	    TemplateProjectData targetProject = new TemplateProjectData(template);

	    ScheduleData schedulerData = (ScheduleData) project.getPjtSchedule().getObject();
	    schedulerData.setDuration(targetProject.tempDuration);
	    PersistenceHelper.manager.modify(schedulerData);

	    // 템플릿 프로젝트의 사용자 정보를 새로운 프로젝트의 정보로 복사한다.
	    // 템플릿의 사용자 정보는 프로젝트 구성원만을 가지고 있다.
	    // ProjectUserHelper.manager.copyProjectUserInfo(project, template);
	    // 템플릿 프로젝트의 산출물 정보를 새로운 프로젝트의 산출물 정보로 복사한다.
	    // ProjectOutputHelper.manager.copyProjectOutputInfo(project,template);

	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public static TemplateProject updateTemplateProjectInfo(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("updateTemplateProjectInfo", "e3ps.project.beans.ProjectHelper",
		        null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (TemplateProject) obj;
	}

	TemplateProject template = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = (String) map.get("oid");
	    String name = (String) map.get("name");
	    String number = (String) map.get("number");
	    String description = (String) map.get("description");

	    if (name == null || name.length() == 0)
		return null;

	    if (number == null || number.length() == 0)
		return null;

	    if (description == null)
		description = "";

	    ReferenceFactory rf = new ReferenceFactory();
	    template = (TemplateProject) rf.getReference(oid).getObject();
	    ProjectMaster master = template.getMaster();
	    master.setPjtName(name);
	    master.setPjtNo(number);
	    PersistenceServerHelper.manager.update(master);
	    master = (ProjectMaster) PersistenceHelper.manager.refresh(master);

	    template.setPjtDesc(description);
	    template = (TemplateProject) PersistenceHelper.manager.save(template);
	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return template;
    }

    public TemplateProject modifyProject(TemplateProject project) {
	try {
	    return (TemplateProject) PersistenceHelper.manager.modify(project);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public void copyProjectInfo(E3PSProject project, TemplateProject template) {
	// Kogger.debug(getClass(), "copyProjectInfoCall");
	try {
	    TemplateProjectData targetProject = new TemplateProjectData(template);

	    ExtendScheduleData schedulerData = (ExtendScheduleData) project.getPjtSchedule().getObject();
	    schedulerData.setDuration(targetProject.tempDuration);

	    int duration = targetProject.tempDuration - 1;

	    if (duration < 0) {
		duration = 0;
	    }

	    Timestamp startDate = schedulerData.getPlanStartDate();

	    long oneDay = 24 * 60 * 60 * 1000;
	    long endTime = startDate.getTime() + ((duration) * oneDay);
	    Timestamp est = new Timestamp(endTime);

	    schedulerData.setPlanEndDate(est);

	    // Kogger.debug(getClass(), "change enddata = " + est);
	    // Calendar cal = Calendar.getInstance();
	    // cal.setTime(schedulerData.getPlanStartDate());
	    // cal.add(Calendar.HOUR, 12);
	    // int duration = targetProject.tempDuration-2;
	    // if ( duration > 0 ) cal.add(Calendar.DATE,duration);
	    // schedulerData.setExecEndDate(new java.sql.Timestamp(cal.getTime().getTime()));
	    PersistenceHelper.manager.save(schedulerData);

	    // 템플릿 프로젝트의 사용자 정보를 새로운 프로젝트의 정보로 복사한다.
	    // 템플릿의 사용자 정보는 프로젝트 구성원만을 가지고 있다.
	    ProjectUserHelper.manager.copyProjectUserInfo(project, template);
	    project = (E3PSProject) PersistenceHelper.manager.refresh(project);

	    // 템플릿 프로젝트의 산출물 정보를 새로운 프로젝트의 산출물 정보로 복사한다.
	    ProjectOutputHelper.manager.copyProjectOutputInfo(project, template);
	    // project = (JELProject)PersistenceHelper.manager.refresh(project);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public static E3PSProject SynOEMTypeCheck(ProductProject checkproject) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { ProductProject.class };
	    Object args[] = new Object[] { checkproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("SynOEMTypeCheck", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	E3PSProject project = null;
	Transaction trx = new Transaction();
	try {
	    trx.start();
	    checkproject.setOemType(null);
	    checkproject = (ProductProject) PersistenceHelper.manager.save(checkproject);
	    project = (E3PSProject) checkproject;
	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return project;
    }

    public static E3PSProject SynDevRequestCheck(E3PSProject checkproject) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { checkproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("SynDevRequestCheck", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	E3PSProject project = null;
	Transaction trx = new Transaction();
	try {
	    trx.start();
	    checkproject.setDevRequest(null);
	    project = (ProductProject) PersistenceHelper.manager.save(checkproject);
	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return project;
    }

    /*
     * public static Vector getTargetItemsWithPath(E3PSProject project)throws Exception{ if(!SERVER) { Class argTypes[] = new
     * Class[]{E3PSProject.class}; Object args[] = new Object[]{project}; Object obj = null; obj =
     * wt.method.RemoteMethodServer.getDefault().invoke( "getTargetItemsWithPath", "e3ps.project.beans.ProjectHelper", null, argTypes,
     * args); return (Vector)obj; } ProjectTreeNode node = ProjectTreeModel.getLoadTree(project); Vector datas = new Vector(); HashMap map =
     * getTargetItemMap(project); for(int i = 0; i < node.getChildCount(); i++){ ProjectTreeNode child =
     * (ProjectTreeNode)node.getChildAt(i); for(int j = 0; j < child.getChildCount(); j++){ ProjectTreeNode child2 =
     * (ProjectTreeNode)child.getChildAt(j); //settingTargetItemData(child2, datas, map); } } return datas; }
     */
    // public static Vector getIssuesWithPath(JELProject project)throws Exception{
    //
    // if(!SERVER) {
    // Class argTypes[] = new Class[]{JELProject.class};
    // Object args[] = new Object[]{project};
    // Object obj = null;
    //
    // obj = wt.method.RemoteMethodServer.getDefault().invoke(
    // "getIssuesWithPath",
    // "e3ps.project.beans.ProjectHelper",
    // null,
    // argTypes,
    // args);
    //
    // return (Vector)obj;
    // }
    //
    // ProjectTreeNode node = ProjectTreeModel.getLoadTree(project);
    // Vector datas = new Vector();
    // HashMap map = getIssueMap(project);
    //
    // for(int i = 0; i < node.getChildCount(); i++){
    //
    // ProjectTreeNode child = (ProjectTreeNode)node.getChildAt(i);
    //
    // ProjectTreeNodeData pd = (ProjectTreeNodeData)child.getUserObject();
    // JELTask task = (JELTask)pd.getData();
    //
    // String key = CommonUtil.getOIDString(task);
    // Vector vector = (Vector)map.get(key);
    //
    // if(vector != null){
    // for(int a = 0; a < vector.size(); a++){
    // UnitErrorProcess issue = (UnitErrorProcess)vector.get(a);
    // UnitErrorData data = new UnitErrorData(issue);
    // data.setPath(getPath(child));
    // data.setTask(task);
    // datas.add(data);
    // }
    // }
    // for(int j = 0; j < child.getChildCount(); j++){
    // ProjectTreeNode child2 = (ProjectTreeNode)child.getChildAt(j);
    // settingIssuesData(child2, datas, map);
    // }
    // }
    // return datas;
    // }
    //
    // private static void settingIssuesData(ProjectTreeNode node, Vector datas, HashMap map)throws Exception {
    //
    // ProjectTreeNodeData pd = (ProjectTreeNodeData)node.getUserObject();
    // JELTask task = (JELTask)pd.getData();
    // String key = CommonUtil.getOIDString(task);
    // Vector vector = (Vector)map.get(key);
    // if(vector != null){
    // for(int i = 0; i < vector.size(); i++){
    // UnitErrorProcess issue = (UnitErrorProcess)vector.get(i);
    //
    // UnitErrorData data = new UnitErrorData(issue);
    // data.setPath(getPath(node));
    // data.setTask(task);
    // datas.add(data);
    // }
    // }
    //
    // for(int i = 0; i < node.getChildCount(); i++){
    // ProjectTreeNode child = (ProjectTreeNode)node.getChildAt(i);
    // settingIssuesData(child, datas, map);
    // }
    // }
    //
    /*
     * private static void settingTargetItemData(ProjectTreeNode node, Vector datas, HashMap map) { ProjectTreeNodeData pd =
     * (ProjectTreeNodeData)node.getUserObject(); E3PSTask task = (E3PSTask)pd.getData(); String key = CommonUtil.getOIDString(task);
     * for(int i = 0; i < node.getChildCount(); i++){ ProjectTreeNode child = (ProjectTreeNode)node.getChildAt(i);
     * settingTargetItemData(child, datas, map); } }
     */
    private static String getPath(DefaultMutableTreeNode node) {
	TreeNode nodes[] = node.getPath();
	String path = "";
	for (int i = 1; i < nodes.length; i++) {
	    if (i != 1) {
		path += "/";
	    }
	    ProjectTreeNode pnode = (ProjectTreeNode) nodes[i];
	    ProjectTreeNodeData pd = (ProjectTreeNodeData) pnode.getUserObject();
	    path += pd.getName();

	}

	return path;
    }

    /*
     * public static HashMap getTargetItemMap(TemplateProject project)throws Exception{ QuerySpec spec = new QuerySpec(); int target_indx =
     * spec.appendClassList(TargetItem.class, true); int task_indx = spec.appendClassList(TemplateTask.class, false); ClassAttribute ca0 =
     * new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id"); ClassAttribute ca1 = new
     * ClassAttribute(TargetItem.class, "taskReference.key.id"); SearchCondition sc = new SearchCondition(ca0, "=", ca1);
     * sc.setFromIndicies(new int[]{task_indx, target_indx}, 0); sc.setOuterJoin(0); spec.appendWhere(sc, new int[]{task_indx,
     * target_indx}); spec.appendAnd(); spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=",
     * PersistenceHelper.getObjectIdentifier(project).getId()), new int[]{task_indx}); QueryResult rs =
     * PersistenceHelper.manager.find(spec); HashMap map = new HashMap(); while(rs.hasMoreElements()){ Object o[] =
     * (Object[])rs.nextElement(); TargetItem targetItem = (TargetItem)o[0]; String taskOid = CommonUtil.getOIDString(targetItem.getTask());
     * map.put(taskOid, targetItem); } return map; }
     */
    // public static HashMap getIssueMap(TemplateProject project)throws Exception{
    // QuerySpec spec = new QuerySpec();
    //
    // int target_idx = spec.appendClassList(UnitErrorProcess.class, true);
    // int task_idx = spec.appendClassList(TemplateTask.class, false);
    // int link_idx = spec.appendClassList(ErrorProcessTaskLink.class, false);
    //
    //
    // ClassAttribute ca0 = new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id");
    // ClassAttribute ca1 = new ClassAttribute(ErrorProcessTaskLink.class, "roleAObjectRef.key.id");
    //
    // SearchCondition sc = new SearchCondition(ca0, "=", ca1);
    // sc.setFromIndicies(new int[]{task_idx, link_idx}, 0);
    // sc.setOuterJoin(0);
    // spec.appendWhere(sc, new int[]{task_idx, link_idx});
    //
    // spec.appendAnd();
    //
    // ClassAttribute ca00 = new ClassAttribute(UnitErrorProcess.class, "thePersistInfo.theObjectIdentifier.id");
    // ClassAttribute ca11 = new ClassAttribute(ErrorProcessTaskLink.class, "roleBObjectRef.key.id");
    //
    // SearchCondition sc2 = new SearchCondition(ca00, "=", ca11);
    // sc2.setFromIndicies(new int[]{target_idx, link_idx}, 0);
    // sc2.setOuterJoin(0);
    // spec.appendWhere(sc2, new int[]{target_idx, link_idx});
    //
    //
    // spec.appendAnd();
    // spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=",
    // PersistenceHelper.getObjectIdentifier(project).getId()), new int[]{task_idx});
    //
    //
    // Kogger.debug(getClass(), "getIssueMap  spec  =>" + spec);
    //
    // QueryResult rs = PersistenceHelper.manager.find(spec);
    //
    // Kogger.debug(getClass(), "ErrorProcessTaskLink  rs.size() " + rs.size());
    //
    // HashMap map = new HashMap();
    // while(rs.hasMoreElements()){
    // Object o[] = (Object[])rs.nextElement();
    // UnitErrorProcess issue = (UnitErrorProcess)o[0];
    // String taskOid = CommonUtil.getOIDString(UnitErrorProcessUtil.getUnitTask(issue));
    // Vector v = new Vector();
    // Vector addVector = (Vector)map.get(taskOid);
    //
    // if(addVector != null){
    // v = addVector;
    // }
    // v.add(issue);
    // map.put(taskOid, v);
    // }
    // return map;
    //
    //
    // }
    //
    /*
     * public QueryResult getTargetItems(TemplateProject project)throws Exception{ QuerySpec spec = new QuerySpec(); int target_indx =
     * spec.appendClassList(TargetItem.class, true); int task_indx = spec.appendClassList(TemplateTask.class, false); ClassAttribute ca0 =
     * new ClassAttribute(TemplateTask.class, "thePersistInfo.theObjectIdentifier.id"); ClassAttribute ca1 = new
     * ClassAttribute(TargetItem.class, "taskReference.key.id"); SearchCondition sc = new SearchCondition(ca0, "=", ca1);
     * sc.setFromIndicies(new int[]{task_indx, target_indx}, 0); sc.setOuterJoin(0); spec.appendWhere(sc, new int[]{task_indx,
     * target_indx}); spec.appendAnd(); spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=",
     * PersistenceHelper.getObjectIdentifier(project).getId()), new int[]{task_indx}); return PersistenceHelper.manager.find(spec); }
     */

    public static boolean deleteObjectLink(ArrayList list) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { ArrayList.class };
	    Object args[] = new Object[] { list };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteObjectLink", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    if (list == null || list.size() == 0) {
		return false;
	    }

	    trx.start();

	    ObjectToObjectLink link = null;
	    for (int i = 0; i < list.size(); i++) {
		link = (ObjectToObjectLink) list.get(i);
		if (link instanceof ProjectMemberLink) {
		    ProjectUserHelper.manager.deleteProjectUser((ProjectMemberLink) link);
		} else {
		    PersistenceHelper.manager.delete(link);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static boolean addRefDepartment(TemplateProject project, ArrayList depts) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, ArrayList.class };
	    Object args[] = new Object[] { project, depts };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("addRefDepartment", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    if (depts == null || depts.size() == 0) {
		return false;
	    }

	    trx.start();

	    QueryResult refDepts = null;
	    Department department = null;
	    for (int i = 0; i < depts.size(); i++) {
		department = (Department) depts.get(i);

		refDepts = ProjectUserHelper.manager.getViewDepartmentLink(project, department);
		if (refDepts.hasMoreElements()) {
		    continue;
		}

		ProjectViewDepartmentLink pdlink = ProjectViewDepartmentLink.newProjectViewDepartmentLink(project, department);
		PersistenceHelper.manager.save(pdlink);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static boolean addRefDepartment(TemplateTask task, ArrayList depts) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class, ArrayList.class };
	    Object args[] = new Object[] { task, depts };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("addRefDepartment", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    if (depts == null || depts.size() == 0) {
		return false;
	    }

	    trx.start();

	    QueryResult refDepts = null;
	    Department department = null;
	    for (int i = 0; i < depts.size(); i++) {
		department = (Department) depts.get(i);

		refDepts = TaskUserHelper.manager.getViewDepartmentLink(task, department);
		if (refDepts.hasMoreElements()) {
		    continue;
		}

		// TaskViewDepartmentLink pdlink = TaskViewDepartmentLink.newTaskViewDepartmentLink(task, department);
		// PersistenceHelper.manager.save(pdlink);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static boolean deleteRefDepartment(TemplateProject project, ArrayList depts) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, ArrayList.class };
	    Object args[] = new Object[] { project, depts };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteRefDepartment", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    if (project == null || depts == null || depts.size() == 0) {
		return false;
	    }

	    Department dept = null;
	    trx.start();

	    QuerySpec qs = new QuerySpec();
	    int idx = qs.appendClassList(ProjectViewDepartmentLink.class, true);

	    SearchCondition sc = null;
	    sc = new SearchCondition(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, project
		    .getPersistInfo().getObjectIdentifier().getId());
	    qs.appendWhere(sc, new int[] { idx });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }

	    qs.appendOpenParen();

	    for (int i = 0; i < depts.size(); i++) {
		dept = (Department) depts.get(i);

		if (i > 0) {
		    qs.appendOr();
		}

		sc = new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, dept
		        .getPersistInfo().getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { idx });
	    }

	    qs.appendCloseParen();

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		PersistenceHelper.manager.delete((ProjectViewDepartmentLink) obj[0]);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static boolean deleteRefDepartment(TemplateTask task, ArrayList depts) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class, ArrayList.class };
	    Object args[] = new Object[] { task, depts };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("deleteRefDepartment", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    if (task == null || depts == null || depts.size() == 0) {
		return false;
	    }

	    Department dept = null;
	    trx.start();

	    QuerySpec qs = new QuerySpec();
	    // int idx = qs.appendClassList(TaskViewDepartmentLink.class, true);

	    SearchCondition sc = null;
	    // sc = new SearchCondition(TaskViewDepartmentLink.class,
	    // "roleAObjectRef.key.id",
	    // SearchCondition.EQUAL,
	    // task.getPersistInfo().getObjectIdentifier().getId());
	    // qs.appendWhere(sc, new int[]{idx});

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }

	    qs.appendOpenParen();

	    for (int i = 0; i < depts.size(); i++) {
		dept = (Department) depts.get(i);

		if (i > 0) {
		    qs.appendOr();
		}

		// sc = new SearchCondition(TaskViewDepartmentLink.class,
		// "roleBObjectRef.key.id",
		// SearchCondition.EQUAL,
		// dept.getPersistInfo().getObjectIdentifier().getId());
		// qs.appendWhere(sc, new int[]{idx});
	    }

	    qs.appendCloseParen();

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    Object obj[] = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		PersistenceHelper.manager.delete((ProjectViewDepartmentLink) obj[0]);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static boolean TaskScheduleSave(HashMap map) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { HashMap.class };
	    Object args[] = new Object[] { map };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("TaskScheduleSave", "e3ps.project.beans.ProjectHelper", null,
		        argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    Hashtable taskCheckOid_ht = new Hashtable();
	    Hashtable pmps_ht = new Hashtable();
	    Hashtable pmpe_ht = new Hashtable();

	    taskCheckOid_ht = (Hashtable) map.get("taskCheckOid");
	    pmps_ht = (Hashtable) map.get("pmps");
	    pmpe_ht = (Hashtable) map.get("pmpe");

	    for (int i = 0; i < taskCheckOid_ht.size(); i++) {
		String key0 = "taskCheckOid" + i;
		String key1 = "pmps" + i;
		String key2 = "pmpe" + i;

		Calendar tempCal = Calendar.getInstance();
		// Kogger.debug(getClass(), "##################################### Start" +i);
		// Kogger.debug(getClass(), "===>" +(String)taskCheckOid_ht.get(key0));
		// Kogger.debug(getClass(), "===>" +(String)pmps_ht.get(key1));
		// Kogger.debug(getClass(), "===>" +(String)pmpe_ht.get(key2));
		// Kogger.debug(getClass(), "##################################### End" +i);

		E3PSTask tt = (E3PSTask) CommonUtil.getObject((String) taskCheckOid_ht.get(key0));
		E3PSTaskData taskData2 = new E3PSTaskData(tt);
		ExtendScheduleData schedule = taskData2.schedule;

		// PM 시작일
		tempCal.setTime(DateUtil.parseDateStr((String) pmps_ht.get(key1)));

		// PM 종료일
		tempCal.setTime(DateUtil.parseDateStr((String) pmpe_ht.get(key2)));

		schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return false;
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}

	return true;
    }

    public static E3PSProject getProject(String projectNo) throws Exception {
	if (StringUtils.isEmpty(projectNo)) {
	    return null;
	}
	QuerySpec spec = new QuerySpec(E3PSProject.class);
	// Kogger.debug(getClass(), "pno ===> "+ projectNo.toUpperCase());

	spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.PJT_NO, SearchCondition.EQUAL, projectNo.toUpperCase()),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });
	// Kogger.debug(getClass(), spec);

	QueryResult rs = PersistenceHelper.manager.find(spec);

	E3PSProject project = null;
	if (rs.hasMoreElements()) {
	    project = (E3PSProject) rs.nextElement();
	}

	return project;
    }

    public static QueryResult getProductInfo(String projecOid) throws Exception {

	QuerySpec qs = new QuerySpec();
	int idxpi = qs.appendClassList(ProductInfo.class, true);
	SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(projecOid));
	qs.appendWhere(cs, new int[] { idxpi });
	SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
	QueryResult qrpi = PersistenceHelper.manager.find(qs);

	return qrpi;
    }

    public static TemplateProject getTemplate(String templateCode) throws Exception {
	// Kogger.debug(getClass(), "templateCode" + templateCode);
	QuerySpec spec = new QuerySpec(TemplateProject.class);
	spec.appendWhere(new SearchCondition(TemplateProject.class, TemplateProject.PJT_NO, SearchCondition.EQUAL, templateCode),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(TemplateProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(TemplateProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });
	// Kogger.debug(getClass(), "template spec : " + spec);

	QueryResult rs = PersistenceHelper.manager.find(spec);

	TemplateProject template = null;
	Object obj = null;
	if (rs.hasMoreElements()) {
	    obj = (Object) rs.nextElement();
	    template = (TemplateProject) obj;
	}

	return template;

    }

    /*
     * public static Vector getPjtProcess(JELProject pjt) { Vector result = new Vector(); try { QuerySpec spec = new
     * QuerySpec(ProcessDivisionCodeLink.class); SearchCondition sc = null; sc = new SearchCondition(ProcessDivisionCodeLink.class,
     * "roleAObjectRef.key.id", SearchCondition.EQUAL, pjt.getPersistInfo().getObjectIdentifier().getId()); spec.appendWhere(sc, new
     * int[]{0}); QueryResult qr = PersistenceHelper.manager.find(spec); ProcessDivisionCodeLink link = null; while(qr.hasMoreElements()) {
     * link = (ProcessDivisionCodeLink) qr.nextElement(); result.addElement(link.getProcessDivisionCode()); } } catch(Exception e) {
     * Kogger.error(getClass(), e); } return result; }
     */
    /*
     * public static E3PSProject getReferProject(ProjectMaster orgMaster){ QueryResult rt; ProjectMaster refMaster = null; E3PSProject
     * refProject = null; try { rt = PersistenceHelper.manager.navigate(orgMaster,"reference",ProjectToProjectLink.class,true);
     * while(rt.hasMoreElements()){ refMaster = (JELProjectMaster)rt.nextElement(); Kogger.debug(getClass(), "refMaster = "
     * +refMaster.getPjtName()); refProject = getLastProject(refMaster); } } catch (WTException e) { // TODO Auto-generated catch block
     * Kogger.error(getClass(), e); } return refProject; }
     */
    public static E3PSProject getLastProject(ProjectMaster master) {

	QuerySpec spec = null;
	E3PSProject project = null;
	try {
	    spec = new QuerySpec();
	    Class ps = E3PSProject.class;
	    Class ms = E3PSProjectMaster.class;
	    int ps_idx = spec.addClassList(ps, true);
	    int ms_idx = spec.addClassList(ms, false);

	    SearchCondition sc = null;
	    sc = new SearchCondition(new ClassAttribute(ps, "masterReference.key.id"), "=", new ClassAttribute(ms,
		    "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { ps_idx, ms_idx }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { ps_idx, ms_idx });

	    spec.appendAnd();
	    sc = new SearchCondition(ps, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	    spec.appendWhere(sc, new int[] { ps_idx });

	    spec.appendAnd();
	    sc = new SearchCondition(ps, "checkOutState", SearchCondition.NOT_EQUAL, "c/o");
	    spec.appendWhere(sc, new int[] { ps_idx });

	    spec.appendAnd();
	    sc = new SearchCondition(ps, "masterReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(master));
	    spec.appendWhere(sc, new int[] { ps_idx });

	    // Kogger.debug(getClass(), spec);
	    QueryResult rt = PersistenceHelper.manager.find(spec);

	    while (rt.hasMoreElements()) {
		Object[] obj = (Object[]) rt.nextElement();
		project = (E3PSProject) obj[0];
	    }
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return project;

    }

    public static QuerySpec getWBSSearchQuery(Hashtable hash, Class projectClass, Vector attr) throws Exception {
	String name = (String) hash.get("name");

	String planType = (String) hash.get("planType");
	String assembling = (String) hash.get("assembling");
	String developType = (String) hash.get("developType");
	String makeType = (String) hash.get("makeType");
	String productType = (String) hash.get("productType");
	String projectType = (String) hash.get("projectType");
	String descendantQuery = (String) hash.get("descendantQuery");
	/* 2014.07.15 */
	String clientCompany = (String) hash.get("clientCompany");
	String makeOffice = (String) hash.get("makeOffice");

	/* 2014.07.08 개발구분 조건 추가 */
	Map<String, Object> map = (Map<String, Object>) hash.get("devCategory"); // 개발구분
	String devType = null;
	if (map != null) {
	    devType = (String) map.get("devCategory");
	}

	Map<String, Object> map1 = (Map<String, Object>) hash.get("devStep"); // 개발단계
	String devStep = null;
	if (map1 != null) {
	    devStep = (String) map1.get("devStep");
	}

	Map<String, Object> map2 = (Map<String, Object>) hash.get("type"); // 금형구분
	String moldType = null;
	if (map2 != null) {
	    moldType = (String) map2.get("type");
	}

	Map<String, Object> map3 = (Map<String, Object>) hash.get("making"); // 제작구분
	String making = null;
	if (map3 != null) {
	    making = (String) map3.get("making");
	}
	/* 2014.07.15 */
	Map<String, Object> map4 = (Map<String, Object>) hash.get("activeType"); // 상태
	String activeType = null;
	if (map4 != null) {
	    activeType = (String) map4.get("activeType");
	}

	/* 2014.07.22 */
	Map<String, Object> map5 = (Map<String, Object>) hash.get("division"); // 상태
	String division = null;
	if (map5 != null) {
	    division = (String) map5.get("division");
	}

	QuerySpec query = null;
	try {
	    query = new QuerySpec();
	    int idx = query.appendFrom(new E3PSClassTableExpression(projectClass));

	    if ("true".equals(descendantQuery))
		query.setDescendantQuery(true);
	    else
		query.setDescendantQuery(false);

	    String tableAlias = query.getFromClause().getAliasAt(idx);

	    BaseTableInfo basetableinfo;
	    basetableinfo = (WTIntrospector.getClassInfo(projectClass)).getDatabaseInfo().getBaseTableInfo();
	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(tableAlias + "." + objname + "||':'||" + tableAlias + "." + objid);
	    ke.setColumnAlias("VIEW_OID");
	    query.appendSelect(ke, new int[] { idx }, false);

	    if (query.getConditionCount() > 0)
		query.appendAnd();
	    query.appendWhere(new SearchCondition(projectClass, "lastest", SearchCondition.IS_TRUE, true), new int[] { idx });

	    if (query.getConditionCount() > 0)
		query.appendAnd();
	    query.appendWhere(new SearchCondition(projectClass, "workingCopy", SearchCondition.IS_FALSE, false), new int[] { idx });

	    if (name != null && name.length() > 0) {
		name = name.replace("*", "%");
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, "master>pjtName", SearchCondition.LIKE, name), new int[] { idx });
	    }

	    if (planType != null && planType.length() > 0 && projectClass.equals(ProductTemplateProject.class)) {
		boolean plan = false;

		if (query.getConditionCount() > 0)
		    query.appendAnd();
		if (planType.equals("true")) {
		    plan = true;
		    query.appendWhere(new SearchCondition(projectClass, ProductTemplateProject.PLAN_TYPE, SearchCondition.IS_TRUE, plan),
			    new int[] { idx });
		} else {
		    query.appendWhere(new SearchCondition(projectClass, ProductTemplateProject.PLAN_TYPE, SearchCondition.IS_FALSE, plan),
			    new int[] { idx });
		}
	    }

	    if (assembling != null && assembling.length() > 0 && projectClass.equals(ProductTemplateProject.class)) {
		boolean assem = false;

		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (assembling.equals("true")) {
		    assem = true;
		    query.appendWhere(new SearchCondition(projectClass, ProductTemplateProject.ASSEMBLING, SearchCondition.IS_TRUE, assem),
			    new int[] { idx });
		} else {
		    query.appendWhere(
			    new SearchCondition(projectClass, ProductTemplateProject.ASSEMBLING, SearchCondition.IS_FALSE, assem),
			    new int[] { idx });
		}
	    }
	    if (projectType != null && projectType.length() > 0) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (projectType.length() > 1) {
		    query.appendOpenParen();
		    StringTokenizer projectTypeToken = new StringTokenizer(projectType, ",");
		    while (projectTypeToken.hasMoreTokens()) {
			query.appendWhere(
			        new SearchCondition(projectClass, TemplateProject.PJT_TYPE, "=", Integer.parseInt(projectTypeToken
			                .nextToken())), new int[] { idx });
			if (projectTypeToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.PJT_TYPE, "=", Integer.parseInt(projectType)),
			    new int[] { idx });
		}
	    } else {
		query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, TemplateProject.PJT_TYPE, "<>", Integer.parseInt("5")),
		        new int[] { idx });
		query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, TemplateProject.PJT_TYPE, "<>", Integer.parseInt("6")),
		        new int[] { idx });
	    }

	    if (developType != null && developType.length() > 0 && projectClass.equals(ProductTemplateProject.class)) {
		int develop = Integer.parseInt(developType);
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, ProductTemplateProject.DEVELOP_TYPE, "=", develop), new int[] { idx });
	    }

	    if (makeType != null && makeType.length() > 0 && projectClass.equals(MoldTemplateProject.class)) {
		int make = Integer.parseInt(makeType);
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, MoldTemplateProject.MAKE_TYPE, "=", make), new int[] { idx });
	    }

	    if (productType != null && productType.length() > 0 && projectClass.equals(ElectronTemplateProject.class)) {
		int produc = Integer.parseInt(productType);
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, ElectronTemplateProject.PRODUCT_TYPE, "=", produc), new int[] { idx });
	    }
	    /* 2014.07.15 고객처 조건 추가 */
	    if (clientCompany != null && clientCompany.length() > 0 && !projectClass.equals(MoldTemplateProject.class)) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, TemplateProject.CLIENT_COMPANY, "=", clientCompany), new int[] { idx });
	    }
	    if (makeOffice != null && makeOffice.length() > 0 && projectClass.equals(MoldTemplateProject.class)) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();
		query.appendWhere(new SearchCondition(projectClass, TemplateProject.MAKE_OFFICE, "=", makeOffice), new int[] { idx });
	    }

	    /* 2014.07.08 개발구분 조건 추가 */
	    if (devType != null && devType.length() > 0 && !projectClass.equals(MoldTemplateProject.class)) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (devType.length() > 8) {
		    query.appendOpenParen();
		    StringTokenizer devTypeToken = new StringTokenizer(devType, ",");
		    while (devTypeToken.hasMoreTokens()) {
			query.appendWhere(
			        new SearchCondition(projectClass, TemplateProject.DEV_TYPE, "=", devTypeToken.nextToken().trim()),
			        new int[] { idx });
			if (devTypeToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.DEV_TYPE, "=", devType), new int[] { idx });
		}
	    }

	    if (devStep != null && devStep.length() > 0
		    && (projectClass.equals(ProductTemplateProject.class) || projectClass.equals(ElectronTemplateProject.class))) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (devStep.length() > 8) {
		    query.appendOpenParen();
		    StringTokenizer devStepToken = new StringTokenizer(devStep, ",");
		    while (devStepToken.hasMoreTokens()) {
			query.appendWhere(
			        new SearchCondition(projectClass, TemplateProject.DEV_STEP, "=", devStepToken.nextToken().trim()),
			        new int[] { idx });
			if (devStepToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.DEV_STEP, "=", devStep), new int[] { idx });
		}
	    }

	    if (projectClass.equals(MoldTemplateProject.class)) {

		if (StringUtil.checkString(moldType)) {
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		    if (moldType.length() > 8) {
			query.appendOpenParen();
			StringTokenizer moldTypeToken = new StringTokenizer(moldType, ",");
			while (moldTypeToken.hasMoreTokens()) {
			    query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, "=", moldTypeToken.nextToken()
				    .trim()), new int[] { idx });
			    if (moldTypeToken.hasMoreTokens())
				query.appendOr();
			}
			query.appendCloseParen();
		    } else {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, "=", moldType), new int[] { idx });
		    }
		} else {
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		    if ("purchase".equals((String) hash.get("moldType"))) {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.EQUAL, "purchase"),
			        new int[] { idx });
		    } else {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.NOT_EQUAL,
			        "purchase"), new int[] { idx });
			if (query.getConditionCount() > 0)
			    query.appendOr();
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.IS_NULL, true),
			        new int[] { idx });
		    }
		}
	    }

	    if (projectClass.equals(TemplateProject.class)) {

		if (StringUtil.checkString(moldType)) {
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		    if (moldType.length() > 8) {
			query.appendOpenParen();
			StringTokenizer moldTypeToken = new StringTokenizer(moldType, ",");
			while (moldTypeToken.hasMoreTokens()) {
			    query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, "=", moldTypeToken.nextToken()
				    .trim()), new int[] { idx });
			    if (moldTypeToken.hasMoreTokens())
				query.appendOr();
			}
			query.appendCloseParen();
		    } else {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, "=", moldType), new int[] { idx });
		    }
		} else {
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		    if ("work".equals((String) hash.get("moldType"))) {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.EQUAL, "work"),
			        new int[] { idx });
		    } else {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.NOT_EQUAL, "work"),
			        new int[] { idx });
			if (query.getConditionCount() > 0)
			    query.appendOr();
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MOLD_TYPE, SearchCondition.IS_NULL, true),
			        new int[] { idx });
		    }
		}
	    }

	    if (division != null && division.length() > 0) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (division.length() > 6) {
		    query.appendOpenParen();
		    StringTokenizer divisionToken = new StringTokenizer(division, ",");
		    while (divisionToken.hasMoreTokens()) {
			query.appendWhere(
			        new SearchCondition(projectClass, TemplateProject.DIVISION, "=", divisionToken.nextToken().trim()),
			        new int[] { idx });
			if (divisionToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.DIVISION, "=", division), new int[] { idx });
		}
	    }

	    if (making != null && making.length() > 0 && (projectClass.equals(MoldTemplateProject.class))) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (making.length() > 2) {
		    query.appendOpenParen();
		    StringTokenizer makingToken = new StringTokenizer(making, ",");
		    while (makingToken.hasMoreTokens()) {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.MAKING, "=", makingToken.nextToken().trim()),
			        new int[] { idx });
			if (makingToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.MAKING, "=", making), new int[] { idx });
		}
	    }

	    /* 2014.07.15 상태조건 추가 */
	    if (activeType != null && activeType.length() > 0) {
		if (query.getConditionCount() > 0)
		    query.appendAnd();

		if (activeType.length() > 3) {
		    query.appendOpenParen();
		    StringTokenizer activeTypeToken = new StringTokenizer(activeType, ",");
		    while (activeTypeToken.hasMoreTokens()) {
			query.appendWhere(new SearchCondition(projectClass, TemplateProject.ACTIVE_TYPE, "=", activeTypeToken.nextToken()
			        .trim()), new int[] { idx });
			if (activeTypeToken.hasMoreTokens())
			    query.appendOr();
		    }
		    query.appendCloseParen();
		} else {
		    query.appendWhere(new SearchCondition(projectClass, TemplateProject.ACTIVE_TYPE, "=", activeType), new int[] { idx });
		}
	    }

	    String sort = (String) hash.get("sortKey");

	    if ((sort != null) && (sort.trim().length() > 0)) {

		if (sort.equals("duration")) {
		    if (query.getConditionCount() > 0)
			query.appendAnd();
		    int idx_schedule = query.appendClassList(ScheduleData.class, false);
		    ClassAttribute ca = null;
		    ClassAttribute ca2 = null;

		    ca = new ClassAttribute(projectClass, "pjtSchedule.key.id");
		    ca2 = new ClassAttribute(ScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
		    SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
		    sc2.setFromIndicies(new int[] { idx, idx_schedule }, 0);
		    sc2.setOuterJoin(0);
		    query.appendWhere(sc2, new int[] { idx, idx_schedule });

		} else if (sort.equals("peopleName")) {
		    if (query.getConditionCount() > 0)
			query.appendAnd();

		    int idx_user = query.appendClassList(People.class, false);

		    ClassAttribute ca = null;
		    ClassAttribute ca2 = null;

		    ca = new ClassAttribute(projectClass, "creator.key.id");
		    ca2 = new ClassAttribute(People.class, People.USER_REFERENCE + ".key.id");
		    SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
		    sc2.setFromIndicies(new int[] { idx, idx_user }, 0);
		    sc2.setOuterJoin(0);
		    query.appendWhere(sc2, new int[] { idx, idx_user });
		}

		ClassAttributeData sortCa = null;
		for (int i = 0; i < attr.size(); i++) {
		    ClassAttributeData attrData = (ClassAttributeData) attr.get(i);

		    if (sort.equals(attrData.ca.getColumnAlias())) {
			sortCa = attrData;
			break;
		    }
		}

		if (sortCa != null) {
		    query.appendSelect(sortCa.ca, new int[] { sortCa.index }, false);
		}

	    }

	    // int sortIdx = 0;
	    // String sort =(String)hash.get("sortKey");
	    // String isDesc =(String)hash.get("dsc");
	    // //Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);
	    //
	    // if((sort != null) && (sort.trim().length() > 0)) {
	    // if((isDesc == null) || (isDesc.trim().length() == 0)) {
	    // isDesc = "FALSE";
	    // }
	    //
	    // if( sort.equals("duration") ){
	    // if(query.getConditionCount() > 0) query.appendAnd();
	    //
	    // int idx_schedule = query.appendClassList(ScheduleData.class, false);
	    //
	    // ClassAttribute ca = null;
	    // ClassAttribute ca2 = null;
	    //
	    // ca = new ClassAttribute(projectClass, "pjtSchedule.key.id");
	    // ca2 = new ClassAttribute(ScheduleData.class, "thePersistInfo.theObjectIdentifier.id");
	    // SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
	    // sc2.setFromIndicies(new int[]{idx, idx_schedule}, 0);
	    // sc2.setOuterJoin(0);
	    // query.appendWhere(sc2, new int[]{idx, idx_schedule});
	    //
	    // SearchUtil.setOrderBy(query, ScheduleData.class, idx_schedule, ScheduleData.DURATION, "m_Sort"+ sortIdx++ ,
	    // "TRUE".equals(isDesc.toUpperCase()));
	    // }else if( sort.equals("creator") ){
	    // if(query.getConditionCount() > 0) query.appendAnd();
	    //
	    // int idx_user = query.appendClassList(WTUser.class, false);
	    //
	    // ClassAttribute ca = null;
	    // ClassAttribute ca2 = null;
	    //
	    // ca = new ClassAttribute(projectClass, sort+".key.id");
	    // ca2 = new ClassAttribute(WTUser.class, "thePersistInfo.theObjectIdentifier.id");
	    // SearchCondition sc2 = new SearchCondition(ca, "=", ca2);
	    // sc2.setFromIndicies(new int[]{idx, idx_user}, 0);
	    // sc2.setOuterJoin(0);
	    // query.appendWhere(sc2, new int[]{idx, idx_user});
	    //
	    // SearchUtil.setOrderBy(query, WTUser.class, idx_user, WTUser.NAME, "m_Sort"+ sortIdx++ , "TRUE".equals(isDesc.toUpperCase()));
	    // }else {
	    // SearchUtil.setOrderBy( query, projectClass, idx, sort, "m_Sort"+ sortIdx++ , "TRUE".equals(isDesc.toUpperCase()));
	    // }
	    // }

	} catch (QueryException e) {
	    Kogger.error(ProjectHelper.class, e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(ProjectHelper.class, e);
	} catch (WTIntrospectionException e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return query;
    }

    public static String getProjectTeam(int pjtType) {

	String team = "";

	switch (pjtType) {
	case CAR_REVIEW: {
	    team = CAR_REVIEW_TEAM;
	    break;
	}
	case ELECTRON_REVIEW: {
	    team = ELECTRON_REVIEW_TEAM;
	    break;
	}
	case CAR_PRODUCT_PROJECT: {
	    team = CAR_PRODUCT_PROJECT_TEAM;
	    break;
	}
	case MOLD_PROJECT: {
	    team = MOLD_PROJECT_TEAM;
	    break;
	}
	case ELECTRON_PRODUCT_PROJECT: {
	    team = ELECTRON_PRODUCT_PROJECT_TEAM;
	    break;
	}

	case WORK_PROJECT: {
	    team = WORK_PROJECT_TEAM;
	    break;
	}
	default:
	    break;
	}

	return team;
    }

    /*
     * public static void changeState(ProductProject project)throws Exception{ String state = project.getLifeCycleState().toString();
     * if(state.equals("WITHDRAWN") || state.equals("STOPED")){ QueryResult rs =
     * MoldProjectHelper.getRelationMoldProject((ProductProject)project); while(rs.hasMoreElements()){ Object[] o =
     * (Object[])rs.nextElement(); MoldProject moldProject = (MoldProject)o[0]; if(moldProject.isWorkingCopy()){ moldProject =
     * (MoldProject)HistoryHelper.checkIn(moldProject); } LifeCycleHelper.service.setLifeCycleState(moldProject,
     * wt.lifecycle.State.toState(state), true); } } }
     */

    public static void changeProjectState(E3PSProject project, String state) throws Exception {

	if (state.equals("COMPLETED") && !ProjectTaskHelper.manager.isTaskAllCheckByProject(project)) {
	    throw new Exception("완료되지 않은 Task가 존재합니다.");
	}
	if (project instanceof ProductProject) {

	    chageStateProductProject((ProductProject) project, state);

	} else if (project instanceof MoldProject) {

	    changeStateMoldProject((MoldProject) project, state);

	} else if (project instanceof ReviewProject) {
	    // 중지 //폐기/재시작/완료..

	    // LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState(state), true);
	    ReviewProject Rproject = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState(state),
		    true);

	    Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(Rproject);

	    if (Rproject.getPjtType() != 0) {
		Hashtable pmoH = ProjectUserHelper.manager.getPMO();
		userH.putAll(pmoH);
	    }

	    State lstate = Rproject.getLifeCycleState();

	    String state_str = lstate.getDisplay(Locale.KOREA);
	    if (lstate.toString().equals("PROGRESS")) {
		state_str = "재시작";
	    }

	    // 프로젝트가 {0} 되었습니다. 업무에 참조하시길 바랍니다.
	    String text = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + state_str;

	    /*
	     * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	     */
	    // sendProjectInfoMail(Rproject, userH, text, "ProjectMailNoti.html");
	    // TODO 박차장님 바꾸세요 메일발송 부분
	    // sendProjectInfoMail(Rproject, userH, text, "ProjectMailNoti");
	    // /////////////////////////////////////////////////////////////////

	} else if (project instanceof WorkProject) {

	    chageStateWorkProject((WorkProject) project, state);

	}

    }

    // 2011.05.13 Fri 프로젝트 담당자 변경시 메일 전송
    public static void changeProjectUser(E3PSProject project) throws Exception {

	Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(project);

	// 프로젝트({0})담당자가 되었습니다. 업무에 참조하시길 바랍니다.
	String text = "msg.mail.ProjectHelper.project_person_in_charge_is_assighed_please_refer_to_the_business" + "||"
	        + project.getPjtNo();

	// Enumeration e = userH.keys();
	// String key = (String)e.nextElement();
	// WTUser user = (WTUser)userH.get(key);
	//
	// String emailAddr = user.getEMail();
	// String userId = user.getName();
	//
	// String toUserID = userId;
	// Hashtable contentHash = MailUtil.getMailContent("assign", project, toUserID);
	// String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
	// Hashtable hash = MailUtil.prepareMailInfoHash(project.getCreator(), contents);
	//
	// if( hash != null )
	// MailUtil.sendMail2( hash, (String)contentHash.get("mailTitle") );

	/*
	 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	 */
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti.html");
	// TODO 박차장님 바꾸세요 메일발송 부분
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti");
	// ////////////////////////////////////////////////////////////////
    }

    public static void approveMail(E3PSProject project) throws Exception {
	Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(project);

	// 프로젝트가 승인되어 진행 상태로 변경되었습니다. 업무에 참조하시길 바랍니다.
	String text = "msg.mail.ProjectHelper.the_project_is_approved._please_refer_to_the_your_work";

	/*
	 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	 */
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti.html");
	// TODO 박차장님 바꾸세요 메일발송 부분
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti");
	// ////////////////////////////////////////////////////////////////
    }

    public static void approveMailWhenCompleteChangeProjectSchedule(E3PSProject project) throws Exception {
	Hashtable userH = ProjectUserHelper.manager.getChangedTaskUserForMail(project);

	// 프로젝트 일정이 변경되어 진행 상태로 변경되었습니다. 업무에 참조하시길 바랍니다.
	String text = "msg.mail.ProjectHelper.the_project_schedule_is_changed._please_refer_to_the_your_work";

	/*
	 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	 */
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti.html");
	// TODO 박차장님 바꾸세요 메일발송 부분
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti");
	// ////////////////////////////////////////////////////////////////
    }

    public static void sendProjectInfoMail(final E3PSProject project, final Hashtable userH, final String mailContent,
	    final String templeHtml) throws Exception {

	// Thread thred = new Thread(){
	// public void run(){
	Class argTypes[] = new Class[] { E3PSProject.class, Hashtable.class, String.class, String.class };
	Object args[] = new Object[] { project, userH, mailContent, templeHtml };
	try {
	    Kogger.debug(ProjectHelper.class, "@@@@@@@@sendProjectInfoMail@@@@@@@@@@");
	    // TODO 박차장님 바꾸세요 메일발송 부분
	    if (!SERVER) {
		RemoteMethodServer.getDefault().invoke("_sendProjectInfoMail", ProjectHelper.class.getName(), null, argTypes, args);
	    } else {
		_sendProjectInfoMail(project, userH, mailContent, templeHtml);
	    }
	} catch (RemoteException e) {
	    Kogger.error(ProjectHelper.class, e);
	} catch (InvocationTargetException e) {
	    Kogger.error(ProjectHelper.class, e);
	}
	// }
	// };
	// thred.start();
    }

    public static void _sendProjectInfoMail(E3PSProject project, Hashtable userH, String mailContent, String templeHtml) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class, Hashtable.class, String.class, String.class };
	    Object args[] = new Object[] { project, userH, mailContent, templeHtml };
	    Object obj = RemoteMethodServer.getDefault()
		    .invoke("_sendProjectInfoMail", ProjectHelper.class.getName(), null, argTypes, args);
	    return;
	}
	Kogger.debug(ProjectHelper.class, "##############   _sendProjectInfoMail Setting Start");
	SessionContext sessioncontext = SessionContext.newContext();
	try {

	    SessionHelper.manager.setAdministrator();
	    String pmName = "";
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    if (pm != null) {
		pmName = pm.getFullName();
	    }
	    Enumeration e = userH.keys();
	    WTUser fromUser = (WTUser) SessionHelper.manager.getAdministrator();
	    HashMap fromMap = new HashMap();
	    Kogger.debug(ProjectHelper.class, " 보내는  사람  Setting Mail :" + fromUser.getEMail());
	    fromMap.put("EMAIL", fromUser.getEMail());
	    fromMap.put("NAME", fromUser.getFullName());

	    String oid = CommonUtil.getOIDString(project);
	    String projectNo = project.getPjtNo();
	    String projectName = project.getPjtName();
	    if (projectName == null) {
		projectName = "";
	    }
	    String pjtTypeName = project.getPjtTypeName();
	    if (pjtTypeName == null) {
		pjtTypeName = "";
	    }
	    String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	    String host = "http://" + hostName;

	    while (e.hasMoreElements()) {
		Hashtable mailHash = new Hashtable();
		HashMap toHash = new HashMap();
		String key = (String) e.nextElement();
		WTUser user = (WTUser) userH.get(key);

		People checkP = PeopleHelper.manager.getPeople(user.getName());

		if (!checkP.isIsDisable()) {

		    KETMessageService messageService = new KETMessageService(KETMessageService.getUserLocale(checkP));

		    String emailAddr = user.getEMail();
		    String userId = user.getName();
		    Kogger.debug(ProjectHelper.class, " 받은 사람  Setting Mail :" + emailAddr);
		    if (emailAddr != null && emailAddr.length() > 0) {
			toHash.put(emailAddr, user.getFullName());
		    } else {
			continue;
		    }

		    String pmTitleName = messageService.getString("e3ps.message.mail.message_mail_msg",
			    "msg.mail.ProjectHelper.Development_person"); /* 개발담당자 */
		    if (project instanceof MoldProject) {
			pmTitleName = messageService.getString("e3ps.message.mail.message_mail_msg",
			        "msg.mail.ProjectHelper.Mold development_person"); /* 금형담당자 */
		    }

		    String[] mailContentSplit = mailContent.split("\\|\\|");
		    String contentMail = "";
		    if (mailContentSplit.length == 1) {
			contentMail = messageService.getString("e3ps.message.mail.message_mail_msg", mailContentSplit[0]);
		    } else {
			contentMail = messageService.getString("e3ps.message.mail.message_mail_msg", mailContentSplit[0],
			        mailContentSplit[1]);
		    }

		    mailHash.put("FROM", fromMap);
		    mailHash.put("TO", toHash);
		    mailHash.put("SUBJECT",
			    messageService.getString("e3ps.message.mail.message_mail_msg", "msg.mail.ProjectHelper.PLM.notice.mail")); /*
								                                                                        * PLM
								                                                                        * 알림
								                                                                        * 메일
								                                                                        */

		    Hashtable contentHash = new Hashtable();
		    contentHash.put("subject",
			    messageService.getString("e3ps.message.mail.message_mail_msg", "msg.mail.ProjectHelper.PLM.notice.mail")); /*
								                                                                        * PLM
								                                                                        * 알림
								                                                                        * 메일
								                                                                        */
		    contentHash.put("projectNo", projectNo);
		    contentHash.put("projectName", projectName);
		    contentHash.put("pjtTypeName", pjtTypeName);
		    contentHash.put("pmTitleName", pmTitleName);
		    contentHash.put("pmName", pmName);
		    contentHash.put("text", contentMail);
		    contentHash.put("host", host);
		    contentHash.put("userId", userId);
		    contentHash.put("oid", oid);
		    // contentHash.put("approvalHistory", MailUtil.getApprovalHistory(oid));

		    MailHtmlContentTemplate contentTemplate = MailHtmlContentTemplate.getInstance();

		    /*
	             * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	             */
		    String templateName = CommonUtil.getMailTemplateName(userId, templeHtml);

		    // String content = contentTemplate.htmlContent(contentHash, templeHtml);
		    String content = contentTemplate.htmlContent(contentHash, templateName);
		    // /////////////////////////////////////////////////////////////////////

		    mailHash.put("CONTENT", content);

		    if (isMailSend) {
			MailUtil.sendMail3(mailHash); // 메일 발송
		    }
		    Kogger.debug(ProjectHelper.class, "##############   _sendProjectInfoMail Setting End");
		}
	    }
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public static void changeStateMoldProject(MoldProject project, String state) throws Exception {

	State beforeState = project.getLifeCycleState();
	MoldProject m_project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState(state), true);

	// Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(m_project);
	// MoldItemInfo info = m_project.getMoldInfo();
	// ProductProject productProject = info.getProject();
	// WTUser productPm = ProjectUserHelper.manager.getPM(productProject);
	// if (productPm != null) {
	// userH.put(productPm.getName(), productPm);
	// }
	//
	// if (state.equals("PROGRESS") || state.equals("WITHDRAWN") || state.equals("STOPED")) {
	// Hashtable pmoH = ProjectUserHelper.manager.getPMO();
	// userH.putAll(pmoH);
	// }
	// else if (state.equals("COMPLETED")) {
	// // Hashtable businessH = ProjectUserHelper.manager.getBusiness(); // 경영기획
	// // userH.putAll(businessH);
	// Hashtable pmoH = ProjectUserHelper.manager.getPMO();
	// userH.putAll(pmoH);
	// Hashtable noticeH = ProjectUserHelper.manager.getNotice();
	// userH.putAll(noticeH);
	// }
	// State lstate = m_project.getLifeCycleState();
	//
	// String state_str = lstate.getDisplay(Locale.KOREA);
	// if (lstate.toString().equals("PROGRESS")) {
	// state_str = "재시작";
	// }
	//
	// /* "프로젝트가 " + state_str + " 되었습니다. <br> 업무에 참조하시길 바랍니다." */
	// String text = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + state_str;

	Kogger.debug(ProjectHelper.class, "######################################    금형 프로젝트 메일 공지 ######################################");
	WTUser from = ProjectUserHelper.manager.getPM(m_project);
	List<WTUser> to = getProjectStateChangeMailingList(m_project, state);
	if ("STOPED".equals(state)) {
	    KETMailHelper.service.sendFormMail("08011", "NoticeMailLine2.html", m_project, from, to);
	} else if ("WITHDRAWN".equals(state)) {
	    KETMailHelper.service.sendFormMail("08012", "NoticeMailLine2.html", m_project, from, to);
	} else if ("COMPLETED".equals(state)) {
	    KETMailHelper.service.sendFormMail("08013", "NoticeMailLine2.html", m_project, from, to);
	} else if ("PROGRESS".equals(state)) {
	    // 중지된 프로젝트가 재시작 될 경우에만 재시작메일 발송
	    if ("STOPED".equals(beforeState.toString())) {
		KETMailHelper.service.sendFormMail("08014", "NoticeMailLine3.html", m_project, from, to);
	    }
	}
	// Kogger.debug(ProjectHelper.class, "내용 :" + text);

	/*
	 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	 */
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti.html");
	// sendProjectInfoMail(project, userH, text, "ProjectMailNoti");
	// ////////////////////////////////////////////////////////////////
    }

    public static void chageStateWorkProject(WorkProject project_input, String state) throws Exception {

	State beforeState = project_input.getLifeCycleState();
	WorkProject project = (WorkProject) LifeCycleHelper.service.setLifeCycleState(project_input, wt.lifecycle.State.toState(state),
	        true);

	WTUser from = ProjectUserHelper.manager.getPM(project);
	List<WTUser> to = getProjectStateChangeMailingList(project, state);

	E3PSProjectData projectData = new E3PSProjectData(project);
	String teamType = projectData.teamType; // 사업부

	if (state.equals("COMPLETED")) {

	    KETMailHelper.service.sendFormMail("08013", "NoticeMailLine2.html", project, from, to);
	}

	if (state.equals("PROGRESS") || state.equals("WITHDRAWN") || state.equals("STOPED")) {

	    if ("STOPED".equals(state)) {
		KETMailHelper.service.sendFormMail("08011", "NoticeMailLine2.html", project, from, to);
	    } else if ("WITHDRAWN".equals(state)) {
		KETMailHelper.service.sendFormMail("08012", "NoticeMailLine2.html", project, from, to);
	    } else if ("PROGRESS".equals(state)) {
		// 중지된 프로젝트가 재시작 될 경우에만 재시작메일 발송
		if ("STOPED".equals(beforeState.toString())) {
		    KETMailHelper.service.sendFormMail("08014", "NoticeMailLine3.html", project, from, to);
		}
	    }

	}
    }

    public static void chageStateProductProject(ProductProject project_input, String state) throws Exception {

	State beforeState = project_input.getLifeCycleState();
	ProductProject project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project_input,
	        wt.lifecycle.State.toState(state), true);

	WTUser from = ProjectUserHelper.manager.getPM(project);
	List<WTUser> to = getProjectStateChangeMailingList(project, state);

	E3PSProjectData projectData = new E3PSProjectData(project);
	String teamType = projectData.teamType; // 사업부

	if (state.equals("COMPLETED")) {

	    KETMailHelper.service.sendFormMail("08013", "NoticeMailLine2.html", project, from, to);

	    // Hashtable userH2 = ProjectUserHelper.manager.getProjectUserForMail(project);
	    // // 자동차 사업부 일 경우만...
	    // Kogger.debug(ProjectHelper.class, "teamType == " + teamType);
	    // Kogger.debug(ProjectHelper.class, "userH2=" + userH2);
	    // if (teamType.equals("자동차사업부_KETS_PMO")) {
	    // Hashtable noticeH = ProjectUserHelper.manager.getNotice();
	    // Hashtable pmoH = ProjectUserHelper.manager.getPMO2();
	    // userH2.putAll(noticeH);
	    // userH2.putAll(pmoH);
	    // }
	    // else {
	    // Hashtable noticeH = ProjectUserHelper.manager.getNotice();
	    // Hashtable pmoH = ProjectUserHelper.manager.getPMO();
	    // userH2.putAll(noticeH);
	    // userH2.putAll(pmoH);
	    // }
	    //
	    // State lstate2 = project.getLifeCycleState();
	    //
	    // String state_str2 = lstate2.getDisplay(Locale.KOREA);
	    //
	    // /* "프로젝트가 " + state_str2 + " 되었습니다. <br> 업무에 참조하시길 바랍니다." */
	    // // String text2 = "프로젝트가 " + state_str2 + " 되었습니다. <br> 업무에 참조하시길 바랍니다.";
	    // String text2 = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + state_str2;

	    /*
	     * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	     */
	    // sendProjectInfoMail(project, userH2, text2, "ProjectMailNoti.html");
	    // sendProjectInfoMail(project, userH2, text2, "ProjectMailNoti");
	    // //////////////////////////////////////////////////////////////////
	}

	if (state.equals("PROGRESS") || state.equals("WITHDRAWN") || state.equals("STOPED")) {

	    if ("STOPED".equals(state)) {
		KETMailHelper.service.sendFormMail("08011", "NoticeMailLine2.html", project, from, to);
	    } else if ("WITHDRAWN".equals(state)) {
		KETMailHelper.service.sendFormMail("08012", "NoticeMailLine2.html", project, from, to);
	    } else if ("PROGRESS".equals(state)) {
		// 중지된 프로젝트가 재시작 될 경우에만 재시작메일 발송
		if ("STOPED".equals(beforeState.toString())) {
		    KETMailHelper.service.sendFormMail("08014", "NoticeMailLine3.html", project, from, to);
		}
	    }

	    QueryResult rs = MoldProjectHelper.getRelationMoldProject((ProductProject) project);
	    while (rs.hasMoreElements()) {
		Object[] o = (Object[]) rs.nextElement();
		MoldProject moldProject = (MoldProject) o[0];
		String moldeState = moldProject.getLifeCycleState().toString();

		if (moldeState.equals("WITHDRAWN") || moldeState.equals("COMPLETED")) {
		    continue;
		}

		if (moldProject.isWorkingCopy()) {
		    moldProject = (MoldProject) HistoryHelper.checkIn(moldProject);
		}
		changeStateMoldProject(moldProject, state);

		// LifeCycleHelper.service.setLifeCycleState(moldProject, wt.lifecycle.State.toState(state), true);

		// State lstate = moldProject.getLifeCycleState();
		//
		// String state_str = lstate.getDisplay(Locale.KOREA);

		/*
	         * 진행 중인 프로젝트가 아니면 모두 재시작으로 표기 되게 되어있음..??? if(lstate.toString().equals("PROGRESS")){ state_str = "재시작"; }
	         */

		/* "프로젝트가 " + state_str + " 되었습니다. <br> 업무에 참조하시길 바랍니다." */
		// String text = "프로젝트가 " + state_str + " 되었습니다. <br> 업무에 참조하시길 바랍니다.";
		// String text = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + state_str;

		// Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(moldProject);
		// Kogger.debug(ProjectHelper.class,
		// "######################################    제품 프로젝트 메일 공지 ###################################### Start");
		// Kogger.debug(ProjectHelper.class, "내용 :" + text);

		/*
	         * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	         */
		// sendProjectInfoMail(moldProject, userH, text, "ProjectMailNoti.html");
		// sendProjectInfoMail(moldProject, userH, text, "ProjectMailNoti");
		// ////////////////////////////////////////////////////////////////////
	    }

	} else {

	    // Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(project);
	    // if (project.getPjtType() != 4) {
	    // Hashtable pmoH = ProjectUserHelper.manager.getPMO();
	    // userH.putAll(pmoH);
	    // }
	    // State lstate = project.getLifeCycleState();
	    //
	    // String state_str = lstate.getDisplay(Locale.KOREA);
	    /*
	     * if(lstate.toString().equals("PROGRESS")){ state_str = "재시작"; }
	     */

	    /* "프로젝트가 " + state_str + " 되었습니다. <br> 업무에 참조하시길 바랍니다." */
	    // String text = "프로젝트가 " + state_str + " 되었습니다. <br> 업무에 참조하시길 바랍니다.";
	    // String text = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + state_str;

	    /*
	     * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	     */
	    // sendProjectInfoMail(project, userH, text, "ProjectMailNoti.html");
	    // sendProjectInfoMail(project, userH, text, "ProjectMailNoti");
	    // ////////////////////////////////////////////////////////////////
	}

	// 프로그램의 상태를 업데이트 한다.
	try {
	    ProgramHelper.service.updateProgramState(project_input);
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}
    }

    // CFT 변경 메일 발송
    public HashMap<String, Object> getCFTChangeMailContent(E3PSProject project, HashMap<String, Object> newMap) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	String content = "";
	QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);

	Object obj[] = null;
	HashMap<String, WTUser> oldRoleUsers = new HashMap<String, WTUser>();
	while (qr.hasMoreElements()) {
	    obj = (Object[]) qr.nextElement();
	    ProjectMemberLink mlink = (ProjectMemberLink) obj[0];
	    if (mlink.getPjtRole() != null && mlink.getPjtRole().length() > 0) {
		oldRoleUsers.put(mlink.getPjtRole(), mlink.getMember());
	    }
	}
	Iterator n_iter = newMap.keySet().iterator();
	while (n_iter.hasNext()) {
	    String key = (String) n_iter.next();
	    Object obj2 = newMap.get(key);
	    if (!(obj2 instanceof Vector)) {
		continue;
	    }
	    Vector vec = (Vector) obj2;
	    if (vec.size() > 0) {// 입력 또는 변경
		String newRoleMemberOid = (String) vec.elementAt(0);
		WTUser newRoleMember = (WTUser) CommonUtil.getObject(newRoleMemberOid);
		PeopleData afterUser = new PeopleData(newRoleMember);
		if (oldRoleUsers.containsKey(key)) {
		    WTUser oldRoleUser = oldRoleUsers.get(key);
		    PeopleData beforeUser = new PeopleData(oldRoleUser);
		    if (!beforeUser.id.equalsIgnoreCase(afterUser.id)) {
			content += "<tr>";
			content += "  <td>" + Role.toRole(key).getDisplay(messageService.getLocale()) + "</td>";
			content += "  <td>" + beforeUser.departmentName + " " + beforeUser.name + " " + beforeUser.duty + "</td>";
			content += "  <td>" + afterUser.departmentName + " " + afterUser.name + " " + afterUser.duty + "</td>";
			content += "</tr>";
		    }
		} else {
		    content += "<tr>";
		    content += "  <td>" + Role.toRole(key).getDisplay(messageService.getLocale()) + "</td>";
		    content += "  <td>&nbsp;</td>";
		    content += "  <td>" + afterUser.departmentName + " " + afterUser.name + " " + afterUser.duty + "</td>";
		    content += "</tr>";
		}
	    } else {// 삭제하는 경우
		if (oldRoleUsers.containsKey(key)) {
		    WTUser oldRoleUser = oldRoleUsers.get(key);
		    PeopleData beforeUser = new PeopleData(oldRoleUser);
		    content += "<tr>";
		    content += "  <td>" + Role.toRole(key).getDisplay(messageService.getLocale()) + "</td>";
		    content += "  <td>" + beforeUser.departmentName + " " + beforeUser.name + " " + beforeUser.duty + "</td>";
		    content += "  <td>&nbsp;</td>";
		    content += "</tr>";
		}
	    }
	}

	HashMap<String, Object> map = new HashMap<String, Object>();
	map.put("pbo", project);
	map.put("content", content);
	return map;
    }

    // PM 변경 메일 발송
    public void sendMailPMChange(TemplateProject templateProject, WTUser afterPM) throws Exception {
	// 변경전 PM
	WTUser beforePM = ProjectUserHelper.manager.getPM(templateProject);
	if (beforePM != null && afterPM != null && !beforePM.getName().equals(afterPM.getName())) {
	    PeopleData beforeUser = new PeopleData(beforePM);
	    PeopleData afterUser = new PeopleData(afterPM);

	    // CFT 변경 공지 메일 발송
	    String content = "";
	    content += "<tr>";
	    content += "  <td>PM</td>";
	    content += "  <td>" + beforeUser.departmentName + " " + beforeUser.name + " " + beforeUser.duty + "</td>";
	    content += "  <td>" + afterUser.departmentName + " " + afterUser.name + " " + afterUser.duty + "</td>";
	    content += "</tr>";

	    List<WTUser> to = new ArrayList();
	    to.add(afterPM);// 변경후 PM
	    // CFT 인원
	    to.addAll(ProjectUserHelper.manager.getCFTMemeberList(templateProject));
	    // PMO 추가
	    if ("자동차 사업부".equals(templateProject.getTeamName())) {
		to.addAll(ProjectUserHelper.manager.getPMOListOfCarDiv());
	    } else if ("전자 사업부".equals(templateProject.getTeamName())) {
		to.addAll(ProjectUserHelper.manager.getPMOlistOfEelecDiv());
	    } else if ("KETS".equals(templateProject.getTeamName())) {
		to.addAll(ProjectUserHelper.manager.getPMOlistOfKETSDiv());
	    }
	    HashMap<String, Object> mailMap = new HashMap<String, Object>();
	    mailMap.put("pbo", templateProject); // pbo
	    mailMap.put("from", beforePM); // 발신자(변경전PM)
	    mailMap.put("content", content); // 메일내용
	    mailMap.put("to", to);
	    KETMailHelper.service.sendFormMail("08109", "ProjectCFTChangeNoticeMail.html", mailMap);
	}
    }

    // 일정변경 메일 발송
    public void sendMailPlanChange(E3PSProject project) throws Exception {
	QueryResult rs = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);
	E3PSProject oldProject = null;
	if (rs.hasMoreElements()) {
	    oldProject = (E3PSProject) rs.nextElement();
	}
	DefaultProjectTreeNode root = (DefaultProjectTreeNode) HistoryHelper.getCompareProject(project, oldProject, new HashMap());
	Vector vector = new Vector();
	this.makeVector(root, vector);
	String mainContent = "";
	// 변경된 일정이 없으면 메일 발송하지 않는다.
	if (vector.size() == 0) {
	    return;
	}
	for (int i = 0; i < vector.size(); i++) {
	    DefaultProjectTreeNode node = (DefaultProjectTreeNode) vector.get(i);
	    String taskType = getTaskStateFont(node);
	    E3PSTaskData childData = null;
	    E3PSTaskData compareData = null;
	    TreeNodeData td = (TreeNodeData) node.getUserObject();
	    if (td.getData() instanceof E3PSProject) {
		/* TemplateProject 이다 */
		continue;
	    }
	    E3PSTask childTask = (E3PSTask) td.getData();
	    E3PSTask compareTask = (E3PSTask) node.getCompareTask();
	    TreeNode[] tNode = node.getPath();
	    if ("유지".equals(taskType)) {
		continue;
	    } else if ("추가".equals(taskType)) {
		childData = new E3PSTaskData(childTask);
		mainContent += "<tr>";
		mainContent += "  <td>" + childTask.getTaskName() + "</td>";
		mainContent += "  <td>-</td>";
		mainContent += "  <td>-</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "</tr>";
	    } else if ("삭제".equals(taskType)) {
		childData = new E3PSTaskData(childTask);
		mainContent += "<tr>";
		mainContent += "  <td>" + childTask.getTaskName() + "</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>-</td>";
		mainContent += "  <td>-</td>";
		mainContent += "</tr>";
	    } else if ("수정".equals(taskType)) {
		childData = new E3PSTaskData(childTask);
		compareData = new E3PSTaskData(compareTask);
		mainContent += "<tr>";
		mainContent += "  <td>" + childTask.getTaskName() + "</td>";
		mainContent += "  <td>" + (compareData == null ? "-" : DateUtil.getTimeFormat(compareData.taskPlanStartDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>" + (compareData == null ? "-" : DateUtil.getTimeFormat(compareData.taskPlanEndDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanStartDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "  <td>" + (childData == null ? "-" : DateUtil.getTimeFormat(childData.taskPlanEndDate, "yy/MM/dd"))
		        + "</td>";
		mainContent += "</tr>";
	    }
	}
	List<WTUser> to = new ArrayList();
	// CFT 인원
	to.addAll(ProjectUserHelper.manager.getCFTMemeberList(project));
	// PMO 추가
	if ("자동차 사업부".equals(project.getTeamName())) {
	    to.addAll(ProjectUserHelper.manager.getPMOListOfCarDiv());
	} else if ("전자 사업부".equals(project.getTeamName())) {
	    to.addAll(ProjectUserHelper.manager.getPMOlistOfEelecDiv());
	} else if ("KETS".equals(project.getTeamName())) {
	    to.addAll(ProjectUserHelper.manager.getPMOlistOfEelecDiv());
	}
	HashMap<String, Object> mailMap = new HashMap<String, Object>();
	mailMap.put("pbo", project); // pbo
	mailMap.put("from", ProjectUserHelper.manager.getPM(project)); // 발신자(PM)
	Kogger.debug(getClass(), mainContent);
	mailMap.put("content", mainContent); // 메일내용
	mailMap.put("to", to);
	KETMailHelper.service.sendFormMail("08016", "ProjectScheduleNoticeMail.html", mailMap);
    }

    public void sendMailStopCancelProject(E3PSProject project) throws Exception {
	// CFT 인원
	List<WTUser> to = ProjectUserHelper.manager.getCFTMemeberList(project);

	WTUser from = ProjectUserHelper.manager.getPM(project);
	String state = project.getState().toString();

	if ("STOPED".equals(state)) {
	    KETMailHelper.service.sendFormMail("08011", "NoticeMailLine2.html", project, from, to);
	} else if ("WITHDRAWN".equals(state)) {
	    KETMailHelper.service.sendFormMail("08012", "NoticeMailLine2.html", project, from, to);
	}
    }

    // 일정변경 메일 발송
    public void sendMailStartProject(E3PSProject project) throws Exception {
	// CFT 인원
	List<WTUser> to = ProjectUserHelper.manager.getCFTMemeberList(project);
	WTUser pm = ProjectUserHelper.manager.getPM(project);

	KETMailHelper.service.sendFormMail("08128", "NoticeMailLine2.html", project, pm,
	        ProjectUserHelper.manager.getCFTMemeberList(project));
    }

    private void makeVector(DefaultProjectTreeNode node, Vector vector) {
	vector.add(node);
	for (int i = 0; i < node.getChildCount(); i++) {
	    makeVector((DefaultProjectTreeNode) node.getChildAt(i), vector);
	}
    }

    private String getTaskStateFont(DefaultProjectTreeNode node) {

	int result = node.getCompareResult();

	String fontStr = "";

	switch (result) {
	case DefaultProjectTreeNode.ADD:
	    fontStr = "추가";
	    break;
	case DefaultProjectTreeNode.DELTE:
	    fontStr = "삭제";
	    break;
	case DefaultProjectTreeNode.MODIFY:
	    fontStr = "수정";
	    break;
	case DefaultProjectTreeNode.DEFAULT:
	    fontStr = "유지";
	    break;
	}

	return fontStr;
    }

    // 프로젝트 일정변경에 의한 revision 생성
    public void copyProjectRef(E3PSProject workingCopy, E3PSProject original) throws Exception {
	QueryResult rs = PersistenceHelper.manager.navigate(original, ProjectCustomereventLink.CUSTOMEREVENT_ROLE,
	        ProjectCustomereventLink.class);
	while (rs.hasMoreElements()) {
	    NumberCode processNum = (NumberCode) rs.nextElement();
	    ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(processNum, (E3PSProject) workingCopy);
	    PersistenceHelper.manager.save(plink);
	}
	String orgPjtOid = null;
	Vector subcontractorVec = new Vector();
	Vector projectoidVec = new Vector();
	orgPjtOid = CommonUtil.getOIDString(original);
	projectoidVec.add(CommonUtil.getOIDString(workingCopy));

	QueryResult rs2 = PersistenceHelper.manager.navigate(original, ProjectSubcontractorLink.SUBCONTRACTOR_ROLE,
	        ProjectSubcontractorLink.class);
	while (rs2.hasMoreElements()) {
	    NumberCode processNum = (NumberCode) rs2.nextElement();
	    subcontractorVec.add(CommonUtil.getOIDString(processNum));
	    ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(processNum, (E3PSProject) workingCopy);
	    PersistenceHelper.manager.save(plink);
	}

	if (workingCopy instanceof ReviewProject) {
	    QueryResult rs3 = PersistenceHelper.manager.navigate(original, ReviewDevelopProjectLink.REVIEW_ROLE,
		    ReviewDevelopProjectLink.class);
	    while (rs3.hasMoreElements()) {
		ReviewDevelop reviewDevelop = (ReviewDevelop) rs3.nextElement();
		ReviewDevelop newDevelop = (ReviewDevelop) HistoryHelper.duplicate(reviewDevelop);
		newDevelop.setProject((ReviewProject) workingCopy);
		PersistenceHelper.manager.save(newDevelop);
	    }
	    // 제품정보 복사
	    QueryResult rs4 = PersistenceHelper.manager.navigate(original, ProjectProductInfoLink.PRODUCT_INFO_ROLE,
		    ProjectProductInfoLink.class);
	    while (rs4.hasMoreElements()) {
		ProductInfo productInfo = (ProductInfo) rs4.nextElement();

		QueryResult rr = PersistenceHelper.manager.navigate(productInfo, ProductModelLink.MODEL_ROLE, ProductModelLink.class);

		ProductInfo newProductInfo = (ProductInfo) HistoryHelper.duplicate(productInfo);
		newProductInfo.setProject(workingCopy);
		newProductInfo = (ProductInfo) PersistenceHelper.manager.save(newProductInfo);
	    }
	}

	if (workingCopy instanceof ProductProject) {
	    QueryResult rs3 = PersistenceHelper.manager.navigate(original, ProductProjectMoldInfoLink.MOLD_INFO_ROLE,
		    ProductProjectMoldInfoLink.class);
	    while (rs3.hasMoreElements()) {
		MoldItemInfo moldItemInfo = (MoldItemInfo) rs3.nextElement();
		moldItemInfo.setProject((ProductProject) workingCopy);
		PersistenceHelper.manager.save(moldItemInfo);

		/*
	         * QueryResult rr = PersistenceHelper.manager.navigate(moldItemInfo, MoldProjectMoldInfoLink.MOLD_PROJECT_ROLE,
	         * MoldProjectMoldInfoLink.class); Kogger.debug(getClass(), "rrgggggggggggggggggg = " + rr.size()); MoldItemInfo newMoldInfo
	         * = (MoldItemInfo)HistoryHelper.duplicate(moldItemInfo); newMoldInfo.setProject((ProductProject)workingCopy); newMoldInfo =
	         * (MoldItemInfo)PersistenceHelper.manager.save(newMoldInfo); if(rr.hasMoreElements()){ MoldProject mProject =
	         * (MoldProject)rr.nextElement(); mProject.setMoldInfo(newMoldInfo); PersistenceHelper.manager.save(mProject); }
	         */
	    }

	    QueryResult rs4 = PersistenceHelper.manager.navigate(original, ProjectProductInfoLink.PRODUCT_INFO_ROLE,
		    ProjectProductInfoLink.class);
	    while (rs4.hasMoreElements()) {
		ProductInfo productInfo = (ProductInfo) rs4.nextElement();

		QueryResult rr = PersistenceHelper.manager.navigate(productInfo, ProductModelLink.MODEL_ROLE, ProductModelLink.class);

		ProductInfo newProductInfo = (ProductInfo) HistoryHelper.duplicate(productInfo);
		newProductInfo.setProject(workingCopy);
		newProductInfo = (ProductInfo) PersistenceHelper.manager.save(newProductInfo);

		if (rr.hasMoreElements()) {
		    ModelInfo modelInfo = (ModelInfo) rr.nextElement();
		    modelInfo.setProduct(newProductInfo);
		    PersistenceHelper.manager.save(modelInfo);
		}
	    }

	    QueryResult rs5 = PersistenceHelper.manager.navigate(original, ProjectCostInfoLink.COST_INFO_ROLE, ProjectCostInfoLink.class);
	    while (rs5.hasMoreElements()) {
		CostInfo costInfo = (CostInfo) rs5.nextElement();
		costInfo.setProject((ProductProject) workingCopy);
		costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);
	    }
	    CustomerPlanHelper.copyLinkProject(orgPjtOid, subcontractorVec, projectoidVec);// customerplan 일정 복사

	    // 프로그램 링크 변경
	    QueryResult rs6 = PersistenceHelper.manager.navigate(original, KETProgramProjectLink.PROGRAM_ROLE, KETProgramProjectLink.class,
		    false);
	    while (rs6.hasMoreElements()) {
		KETProgramProjectLink programProjectLink = (KETProgramProjectLink) rs6.nextElement();
		// KETProgramProjectLink newprogramProjectLink =
		// KETProgramProjectLink.newKETProgramProjectLink(programProjectLink.getProject(), programProjectLink.getProgram());
		programProjectLink.setProject((ProductProject) workingCopy);
		PersistenceHelper.manager.save(programProjectLink);
	    }
	    // 프로그램 상태 변경
	    ProgramHelper.service.updateProgramState((ProductProject) workingCopy);

	    // 프로젝트 목표 Gate관리 변경
	    QueryResult rs8 = PersistenceHelper.manager.navigate(original, ProjectAssSheetLink.ASSESS_ROLE, ProjectAssSheetLink.class,
		    false);
	    while (rs8.hasMoreElements()) {
		ProjectAssSheetLink sheetLink = (ProjectAssSheetLink) rs8.nextElement();
		ProjectAssSheetLink newSheetLink = ProjectAssSheetLink.newProjectAssSheetLink(sheetLink.getAssess(),
		        (ProductProject) workingCopy);
		// newSheetLink.setProject((ProductProject) workingCopy);
		PersistenceHelper.manager.save(newSheetLink);
	    }

	}
	// 이슈 링크 복사
	QueryResult rs9 = PersistenceHelper.manager.navigate(original, ProjectIssueLink.ISSUE_ROLE, ProjectIssueLink.class, false);
	while (rs9.hasMoreElements()) {
	    ProjectIssueLink link = (ProjectIssueLink) rs9.nextElement();
	    ProjectIssueLink newLink = ProjectIssueLink.newProjectIssueLink(workingCopy, link.getIssue());
	    // newSheetLink.setProject((ProductProject) workingCopy);
	    PersistenceHelper.manager.save(newLink);
	}

    }

    public static final Hashtable APPROVALSTATE;

    static {
	APPROVALSTATE = new Hashtable();
	APPROVALSTATE.put("INWORK", "INWORK");
	APPROVALSTATE.put("UNDERREVIEW", "UNDERREVIEW");
	APPROVALSTATE.put("APPROVED", "APPROVED");
	APPROVALSTATE.put("REJECTED", "REJECTED");
	APPROVALSTATE.put("REWORK", "REWORK");
    }

    public static final int REVIEWPROJECT_STATE = 1;
    public static final int PRODUCTPROJECT_STATE = 2;
    public static final int MOLDPROJECT_STATE = 3;

    public static Vector getSearchState(int type) throws Exception {

	String lifeCycle = "";
	int index = 0;
	switch (type) {
	case REVIEWPROJECT_STATE: {
	    lifeCycle = "KET_REVIEW_PMS_LC";
	    index = 3;
	    break;
	}
	case PRODUCTPROJECT_STATE: {
	    lifeCycle = "KET_PRODUCT_PMS_LC";
	    index = 3;
	    break;
	}
	case MOLDPROJECT_STATE: {
	    lifeCycle = "KET_MOLD_PMS_LC";
	    index = 0;
	    break;
	}
	}
	;

	LifeCycleTemplate lifecycletemplate = LifeCycleHelper.service.getLifeCycleTemplate(lifeCycle, WCUtil.getWTContainerRef());
	Vector states = LifeCycleHelper.service.getPhaseTemplates(lifecycletemplate);

	Vector data = new Vector();
	for (int i = 0; i < states.size(); i++) {
	    PhaseTemplate pt = (PhaseTemplate) states.get(i);
	    State lcState = pt.getPhaseState();
	    String stateName = lcState.toString();
	    if (APPROVALSTATE.containsKey(stateName)) {
		continue;
	    }
	    data.add(lcState);
	}

	Collections.sort(data, new StateComparator());

	State state = State.toState("UNDERREVIEW");
	data.add(index, state);

	return data;
    }

    /*
     * public static boolean projectSendMail(TemplateProject project, String type) { try { if(project != null && type != null) { HashMap
     * toHash = new HashMap(); String subject = ""; String content = ""; if("setState".equals(type) || "restart".equals(type)) { QueryResult
     * result = ProjectUserHelper.manager.getAllProjectUser(project); while(result.hasMoreElements()){ Object[] objArr =
     * (Object[])result.nextElement(); ProjectMemberLink memberLink = (ProjectMemberLink)objArr[0]; WTUser toUser = memberLink.getMember();
     * toHash.put(toUser.getEMail(), toUser.getFullName()); } boolean pmoCheck = false; if(project instanceof ReviewProject) { String
     * teamType = ((ReviewProject)project).getAttr1(); if(teamType != null && "자동차".equals(teamType)) pmoCheck = true; }else if(project
     * instanceof ProductProject) { String teamType = ((ProductProject)project).getTeamType(); if(teamType != null &&
     * "자동차 사업부".equals(teamType)) pmoCheck = true; }else if(project instanceof MoldProject) { pmoCheck = true; MoldItemInfo moldInfo =
     * ((MoldProject)project).getMoldInfo(); if(moldInfo != null) { ProductProject productProject = moldInfo.getProject(); QueryResult pmRs
     * = ProjectUserHelper.manager.getPMProjectMemberLink(productProject); while(pmRs.hasMoreElements()){ Object[] objArr =
     * (Object[])pmRs.nextElement(); ProjectMemberLink memberLink = (ProjectMemberLink)objArr[0]; WTUser toUser = memberLink.getMember();
     * toHash.put(toUser.getEMail(), toUser.getFullName()); } } } if(pmoCheck) { WTGroup pmoGroup =
     * OrganizationServicesHelper.manager.getGroup("자동차PMO"); Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
     * while( pmoMembers.hasMoreElements() ) { WTPrincipal wtprincipal = (WTPrincipal)pmoMembers.nextElement(); if( wtprincipal instanceof
     * WTUser ) { WTUser memberUser = (WTUser)wtprincipal; toHash.put(memberUser.getEMail(), memberUser.getFullName()); } } }
     * if("setState".equals(type)) { subject = project.getPjtName() + "(" + project.getPjtNo() + ") > 의 상태가 " +
     * project.getState().toString() + "로 변경되었습니다."; content = project.getPjtName() + "(" + project.getPjtNo() + ") > 의 상태가 " +
     * project.getState().toString() + "로 변경되었습니다.\n 업무에 참조하시기 바랍니다."; }else { subject = project.getPjtName() + "(" + project.getPjtNo() +
     * ") > 가 재시작되었습니다."; content = project.getPjtName() + "(" + project.getPjtNo() + ") > 가 재시작되었습니다.\n 업무에 참조하시기 바랍니다."; } }else
     * if("mold".equals(type)) { E3PSProjectData projectData = new E3PSProjectData((E3PSProject)project);
     * toHash.put(projectData.pjtPm.getEMail(), projectData.pjtPm.getFullName()); subject = project.getPjtName() + "(" + project.getPjtNo()
     * + ") > 가 등록되었습니다."; content = project.getPjtName() + "(" + project.getPjtNo() + ") > 가 등록되었습니다.\n 일정변경 및 관련정보를 수정해 주시기 바랍니다."; }
     * Hashtable mailHash = new Hashtable(); mailHash.put("TO", toHash); mailHash.put("SUBJECT", subject); mailHash.put("CONTENT", content);
     * return MailUtil.manager.sendMail2(mailHash); } } catch (Exception e) { // Auto-generated catch block Kogger.error(getClass(), e); }
     * return false; }
     */

    public static WTGroup getWTGroupObj(String groupName) throws Exception {
	WTGroup group = null;
	QuerySpec query = null;
	QueryResult qr = null;

	query = new QuerySpec();
	int idx = query.addClassList(WTGroup.class, true);
	query.appendWhere(new SearchCondition(WTGroup.class, "name", "=", groupName), new int[] { idx });

	qr = PersistenceHelper.manager.find(query);

	Object[] obj = null;
	if (qr.hasMoreElements()) {
	    obj = (Object[]) qr.nextElement();
	    group = (WTGroup) obj[0];
	}

	return group;
    }

    public static HashMap getWTGroupMembershipLink(WTGroup group) throws Exception {

	HashMap map = new HashMap();
	java.util.Enumeration enm = wt.org.OrganizationServicesHelper.manager.members(group);
	while (enm.hasMoreElements()) {
	    WTPrincipal principal = (WTPrincipal) enm.nextElement();
	    if (principal instanceof wt.org.WTUser) {
		WTUser user = (WTUser) principal;
		map.put(user.getEMail(), user.getFullName());
		Kogger.debug(ProjectHelper.class, group.getName() + "의  그룹 사용자 :" + user.getFullName());
	    }
	}
	return map;
    }

    public static boolean projectTrySendMail(TrySchdule trySchdule) {
	try {
	    HashMap toHash = new HashMap();
	    WTUser reqUser = (WTUser) trySchdule.getRequester().getPrincipal();
	    if (reqUser == null) {
		return false;
	    }
	    toHash.put(reqUser.getEMail(), reqUser.getFullName());

	    E3PSProjectMaster pjtMater = trySchdule.getMoldMaster();
	    MoldProject project = MoldProjectHelper.getMoldProject(pjtMater.getPjtNo());
	    String subject = "";
	    String content = "";
	    String userId = reqUser.getName();
	    String pjtNo = pjtMater.getPjtNo();
	    String pjtType = pjtMater.getPjtTypeName();
	    String projectName = pjtMater.getPjtName();
	    String oid = CommonUtil.getOIDString(project);

	    KETMessageService messageService = new KETMessageService(KETMessageService.getUserLocale(reqUser));

	    subject = messageService.getString("e3ps.message.mail.message_mail_msg", "msg.mail.ProjectHelper.project.mold_try_complete"); /*
									                                                                   * 금형
									                                                                   * Try
									                                                                   * 완료
									                                                                   */
	    ;
	    // 상기 Item의 금형Try가 완료되었으니 업무에 참조 하시기 바랍니다.
	    content = messageService.getString("e3ps.message.mail.message_mail_msg",
		    "msg.mail.ProjectHelper.mold_try_of_the_item_is_completed_please_refer_to_your_work");
	    String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	    String host = "http://" + hostName;
	    Hashtable contentHash = new Hashtable();

	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    contentHash.put("subject", subject);
	    contentHash.put("projectNo", pjtNo);
	    contentHash.put("projectName", projectName);
	    contentHash.put("pjtTypeName", pjtType);
	    contentHash.put("pmTitleName",
		    messageService.getString("e3ps.message.mail.message_mail_msg", "msg.mail.ProjectHelper.mold_development_person")); // 금형개발담당자
	    if (pm != null) {
		contentHash.put("pmName", pm.getFullName());
	    } else {
		contentHash.put("pmName", "");
	    }
	    contentHash.put("text", content);
	    contentHash.put("host", host);
	    contentHash.put("userId", userId);
	    contentHash.put("oid", oid);

	    /*
	     * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	     */
	    String templateName = CommonUtil.getMailTemplateName(userId, "ProjectMailNoti");

	    // String mailContent = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, "ProjectMailNoti.html");
	    String mailContent = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
	    // //////////////////////////////////////////////////////////////////////////////////////////////////////////

	    Hashtable mailHash = new Hashtable();
	    mailHash.put("TO", toHash);
	    mailHash.put("SUBJECT", subject);
	    mailHash.put("CONTENT", mailContent);

	    if (isMailSend) {
		return MailUtil.sendMail3(mailHash);
	    } else {
		return false;
	    }

	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return false;

    }

    /**
     * @param issue
     * @param type
     * @return
     */
    public static boolean projectSendMail(ProjectIssue issue, String type) {
	try {
	    if (issue != null && type != null) {
		ProjectIssueData data = new ProjectIssueData(issue);
		WTUser currentUser = (WTUser) SessionHelper.manager.getPrincipal();

		HashMap toHash = new HashMap();
		HashMap fromHash = new HashMap();
		String subject = "";
		String content = "";
		if ("issue".equals(type)) {
		    Kogger.debug(ProjectHelper.class,
			    "############################################이슈 메일 공지 : 이슈 등록###############################");
		    WTPrincipal wtprincipal = issue.getManager().getPrincipal();
		    E3PSProject project = issue.getProject();
		    WTUser pm = null;
		    WTUser CFTUser = null;
		    if (project != null) {
			pm = ProjectUserHelper.manager.getPM(project);
			// PM
			if (pm != null) {
			    // Kogger.debug(getClass(), "##PM :" + pm.getFullName());
			    toHash.put(pm.getEMail(), pm.getFullName());
			}
			// CFT
			QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 2);
			Object[] obj = null;
			while (qr.hasMoreElements()) {
			    obj = (Object[]) qr.nextElement();
			    ProjectMemberLink pl = (ProjectMemberLink) obj[0];
			    WTUser pluser = pl.getMember();
			    if (pluser != null) {
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }
			}
		    }

		    WTUser memberUser = null;
		    PeopleData pd = null;
		    if (wtprincipal instanceof WTUser) {
			memberUser = (WTUser) wtprincipal;
			Kogger.debug(ProjectHelper.class, "##담당자 :" + memberUser.getFullName());
			pd = new PeopleData(memberUser);
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }

		    if (data.importance != null) {
			// data.urgency
			if ("상".equals(data.importance) && "상".equals(data.urgency)) {
			    // 임원
			    WTGroup group = null;
			    if (CommonUtil.isMember("전자사업부")) {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
			    } else {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
			    }

			    toHash.putAll(getWTGroupMembershipLink(group));
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}
			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("상".equals(data.importance) && "중".equals(data.urgency)) {
			    // 임원
			    WTGroup group = null;
			    if (CommonUtil.isMember("전자사업부")) {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
			    } else {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
			    }
			    toHash.putAll(getWTGroupMembershipLink(group));
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}

			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("상".equals(data.importance) && "하".equals(data.urgency)) {
			    // 팀장
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}

			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("중".equals(data.importance) && "상".equals(data.urgency)) {
			    // 임원
			    WTGroup group = null;
			    if (CommonUtil.isMember("전자사업부")) {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR2);
			    } else {
				group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.DIRECTOR);
			    }
			    toHash.putAll(getWTGroupMembershipLink(group));
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}

			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("중".equals(data.importance) && "중".equals(data.urgency)) {
			    // 팀장
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}

			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("중".equals(data.importance) && "하".equals(data.urgency)) {
			    // 팀원(구성원?)
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("하".equals(data.importance) && "상".equals(data.urgency)) {
			    // 팀장
			    if (pd != null) {
				WTUser wtuser = null;
				if (pd.department != null) {
				    wtuser = (WTUser) PeopleHelper.manager.getChiefUser(pd.department);
				    if (wtuser != null) {
					toHash.put(wtuser.getEMail(), wtuser.getFullName());
				    }
				}

			    }
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("하".equals(data.importance) && "중".equals(data.urgency)) {
			    // 팀원(구성원?)
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }

			} else if ("하".equals(data.importance) && "하".equals(data.urgency)) {
			    // 팀원(구성원?)
			    QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, 3);
			    Object[] obj = null;
			    while (qr.hasMoreElements()) {
				obj = (Object[]) qr.nextElement();
				ProjectMemberLink pl = (ProjectMemberLink) obj[0];
				WTUser pluser = pl.getMember();
				toHash.put(pluser.getEMail(), pluser.getFullName());
			    }
			}
		    }

		    // subject = "Issue가 등록되었습니다.";
		    // content = "Project의  Issue(" + data.subject + ")가 등록되었습니다.\n 업무에 참조하시기 바랍니다.";

		    subject = "msg.mail.ProjectHelper.issue_has_been_registered";
		    content = "msg.mail.ProjectHelper.project_issue_has_been_registered._please_refer_to_the_business" + "|" + data.subject;
		} else if ("issueAnswer".equals(type)) {
		    Kogger.debug(ProjectHelper.class,
			    "############################################이슈 메일 공지 : 해결방안 등록###############################");
		    KETMessageService messageService = null;
		    WTPrincipal wtprincipal = issue.getOwner().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			toHash.put(memberUser.getEMail(), memberUser.getFullName());

			messageService = new KETMessageService(KETMessageService.getUserLocale(memberUser));
		    }

		    // subject = "Issue(" + data.subject + ")에 해결방안이 등록되었습니다.";
		    // content = "프로젝트의  Issue(" + data.subject + ")에 해결방안이 등록되었습니다.\n 업무에 참조하시기 바랍니다.";

		    subject = "msg.mail.ProjectHelper.the_solution_has_been_registered_in_issue" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.the_solution_has_been_registered_in_project_issue_please_refer_to_the_business" + "|"
			    + data.subject;

		    // 위임
		} else if ("reAssign".equals(type)) {
		    Kogger.debug(ProjectHelper.class,
			    "############################################이슈 메일 공지 : 위임###############################");
		    WTPrincipal wtprincipal = issue.getManager().getPrincipal();
		    // WTPrincipal wtprincipal2 = issue.getOwner().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			// WTUser ownerUser = (WTUser)wtprincipal2;
			// toHash.put(ownerUser.getEMail(), ownerUser.getFullName());
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }
		    // subject = "Issue(" + data.subject + ")에  담당자로  위임되었습니다.";
		    // content = data.project.getPjtName() + " Project의  Issue(" + data.subject + ")에  담당자로  위임되었습니다.";

		    subject = "msg.mail.ProjectHelper.you_are_assigned_to_issue" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.you_are_assigned_to_project_issue" + "|" + data.project.getPjtName() + "|"
			    + data.subject;

		    // 해결 방안 완료
		} else if ("answerComplate".equals(type)) {
		    Kogger.debug(ProjectHelper.class,
			    "############################################이슈 메일 공지 : 해결 방안 완료###############################");
		    WTPrincipal wtprincipal = issue.getOwner().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }
		    // subject = "Issue(" + data.subject + ")에  해결 방안이 완료 되었습니다.";
		    // content = "Project의  Issue(" + data.subject + ")에  해결 방안이 완료 되었습니다.";

		    subject = "msg.mail.ProjectHelper.the_solution_has_been_completed_in_issue" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.the_solution_has_been_completed_in_project_issue_please_refer_to_the_business" + "|"
			    + data.subject;

		    // 미완료 업무 지정
		} else if ("reCreate".equals(type)) {
		    Kogger.debug(ProjectHelper.class,
			    "############################################이슈 메일 공지 : 미완료 업무 지정###############################");
		    WTPrincipal wtprincipal = issue.getManager().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }
		    // subject = "Issue(" + data.subject + ")에  미완료 업무 지정이 되었습니다.";
		    // content = "Project의  Issue(" + data.subject + ")에  미완료 업무 지정이 되었습니다.";

		    subject = "msg.mail.ProjectHelper.issue_has_been_specified_in_the_unfinished_task" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.project_issue_has_been_specified_in_the_unfinished_task" + "|" + data.subject;

		    // 이슈 완료
		} else if ("issueComplate".equals(type)) {
		    WTPrincipal wtprincipal = issue.getManager().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }
		    // subject = "Issue(" + data.subject + ")가  완료되었습니다.";
		    // content = "Project의  Issue(" + data.subject + ")가  완료되었습니다.";

		    subject = "msg.mail.ProjectHelper.issue_has_been_completed" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.project_issue_has_been_completed" + "|" + data.subject;

		    // 이슈삭제시 공지 추가
		} else if ("issueDelete".equals(type)) {
		    WTPrincipal wtprincipal = issue.getManager().getPrincipal();
		    if (wtprincipal instanceof WTUser) {
			WTUser memberUser = (WTUser) wtprincipal;
			toHash.put(memberUser.getEMail(), memberUser.getFullName());
		    }
		    // subject = "Issue(" + data.subject + ")가  삭제 되었습니다.";
		    // content = "Project의  Issue(" + data.subject + ")가  삭제 되었습니다.";

		    subject = "msg.mail.ProjectHelper.issue_has_been_deleted" + "|" + data.subject;
		    content = "msg.mail.ProjectHelper.project_issue_has_been_deleted" + "|" + data.subject;
		}

		String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
		String host = "http://" + hostName;
		Kogger.debug(ProjectHelper.class, "############################    Mail TO Size :" + toHash.size());
		Kogger.debug(ProjectHelper.class, "############################    subject:" + subject);

		Hashtable contentHash = new Hashtable();
		// contentHash.put("subject", subject);
		contentHash.put("projectNo", data.project.getPjtNo());
		contentHash.put("projectName", data.project.getPjtName());
		// contentHash.put("text", content);
		contentHash.put("host", host);
		// contentHash.put("approvalHistory", MailUtil.getApprovalHistory(issue.getProject().toString())); //issue가 등록된 프로젝트의 oid를
		// 가져온다.

		fromHash.put("EMAIL", currentUser.getEMail()); // 현재 접속자의 이메일
		fromHash.put("NAME", currentUser.getFullName()); // 현재 접속자 의 이름

		Hashtable mailHash = new Hashtable();
		mailHash.put("FROM", fromHash);
		// mailHash.put("SUBJECT", subject);

		Object[] objArr = toHash.keySet().toArray();
		String emails[];
		emails = new String[objArr.length];
		String toname[] = new String[objArr.length];
		;

		for (int i = 0; i < objArr.length; i++) {
		    HashMap toHash2 = new HashMap();
		    emails[i] = (String) objArr[i];
		    toname[i] = (String) toHash.get(emails[i]);
		    // Kogger.debug(ProjectHelper.class, "To name :" + toname);

		    toHash2.put(emails[i], toname[i]);
		    String userId = emails[i].substring(0, emails[i].indexOf("@"));
		    Kogger.debug(ProjectHelper.class, "To Mail :" + emails[i] + " userId = " + userId);
		    mailHash.put("TO", toHash2);
		    contentHash.put("userId", userId);

		    // 메일 다국어 처리 - 제목, 내용
		    WTUser wTUser = OrganizationServicesHelper.manager.getAuthenticatedUser(userId);
		    KETMessageService messageService = new KETMessageService(KETMessageService.getUserLocale(wTUser));

		    String[] subjectSplit = subject.split("\\|");

		    if (subjectSplit.length == 1) {
			contentHash.put("subject", messageService.getString("e3ps.message.mail.message_mail_msg", subjectSplit[0]));
			mailHash.put("SUBJECT", messageService.getString("e3ps.message.mail.message_mail_msg", subjectSplit[0]));
		    } else {
			contentHash.put("subject",
			        messageService.getString("e3ps.message.mail.message_mail_msg", subjectSplit[0], subjectSplit[1]));
			mailHash.put("SUBJECT",
			        messageService.getString("e3ps.message.mail.message_mail_msg", subjectSplit[0], subjectSplit[1]));
		    }

		    String[] contentSplit = content.split("\\|");
		    if (contentSplit.length == 1) {
			contentHash.put("text", messageService.getString("e3ps.message.mail.message_mail_msg", contentSplit[0]));
		    } else if (contentSplit.length == 2) {
			contentHash.put("text",
			        messageService.getString("e3ps.message.mail.message_mail_msg", contentSplit[0], contentSplit[1]));
		    } else if (contentSplit.length == 3) {
			contentHash.put("text", messageService.getString("e3ps.message.mail.message_mail_msg", contentSplit[0],
			        contentSplit[1], contentSplit[2]));
		    }

		    /*
	             * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	             */
		    String templateName = CommonUtil.getMailTemplateName(userId, "ProjectIssueMail");

		    // String mailContent = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, "ProjectIssueMail.html");
		    // String mailContent = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
		    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////

		    // mailHash.put("CONTENT", mailContent);

		    if (isMailSend) {
			MailUtil.sendMail3(mailHash); // 메일 발송
		    }
		    if (emails[i].indexOf("@") < 0) {
			continue;
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return false;
    }

    public static boolean projectSendMailAssign(E3PSProject project, String type, WTUser wtuser, boolean isSend) throws Exception {
	try {

	    String text = "";
	    String moveHtml = "";
	    KETMessageService messageService = null;

	    Hashtable userH = new Hashtable();
	    if (wtuser != null) {
		userH.put(wtuser.getName(), wtuser);

		messageService = new KETMessageService(KETMessageService.getUserLocale(wtuser));
	    }

	    if (type.equals("assign")) {
		// text = "개발 담당자 지정 업무가 작업공간의 결재대기함에 할당 되었습니다.";
		text = messageService.getString("e3ps.message.mail.message_mail_msg",
		        "msg.mail.ProjectHelper.development_task_is_assigned_to_your_approval_list_of_workspace");

		/*
	         * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	         */
		// moveHtml = "ProjectMailAssign.html";
		moveHtml = "ProjectMailAssign";
		// //////////////////////////////////
	    } else if (type.equals("devAssign")) {
		// text = "프로젝트의 개발담당자로 지정되었습니다.";
		text = messageService.getString("e3ps.message.mail.message_mail_msg",
		        "msg.mail.ProjectHelper.you_are_assigned_to_in_charge_of_development");

		/*
	         * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	         */
		// moveHtml = "ProjectMailNoti.html";
		moveHtml = "ProjectMailNoti";
		// ////////////////////////////////
	    }

	    if (isSend) {
		if (isMailSend) {
		    Kogger.debug(ProjectHelper.class, "@@@@@@projectSendMailAssign User=====> " + userH);
		    // TODO 박차장님 바꾸세요 메일발송 부분
		    // sendProjectInfoMail(project, userH, text, moveHtml);
		}
	    }

	    return isMailSend;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return false;
    }

    public static boolean projectSendMailPM(E3PSProject project, WTUser wtuser, WTUser pmUser, boolean isSend) throws Exception {
	try {

	    String text = "";
	    String moveHtml = "";

	    KETMessageService messageService = null;

	    if (wtuser != null) {

		messageService = new KETMessageService(KETMessageService.getUserLocale(wtuser));

		// text = "프로젝트가 생성 되었습니다. <br> 업무에 참조 하십시오.";
		text = messageService.getString("e3ps.message.mail.message_mail_msg",
		        "msg.mail.ProjectHelper.the_project_has_been_created._please_refer_to_your_work");

		/*
	         * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	         */
		// moveHtml = "ProjectMailNoti.html";
		moveHtml = "ProjectMailNoti";
		// ////////////////////////////////

		Hashtable userH = new Hashtable();
		userH.put(wtuser.getName(), wtuser);

		if (isSend) {
		    // TODO 박차장님 바꾸세요 메일발송 부분
		    // sendProjectInfoMail(project, userH, text, moveHtml);
		}
	    }

	    if (project instanceof MoldProject) {
		// text = "프로젝트의 금형개발담당으로 지정되었습니다.";
		text = messageService.getString("e3ps.message.mail.message_mail_msg",
		        "msg.mail.ProjectHelper.you_are_assigned_to_in_charge_of_mold_development");
	    } else {
		// text = "프로젝트의 PM으로 지정되었습니다.";
		text = messageService.getString("e3ps.message.mail.message_mail_msg",
		        "msg.mail.ProjectHelper.you_are_assigned_to_project_pm");
	    }

	    /*
	     * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	     */
	    // moveHtml = "ProjectMailNoti.html";
	    moveHtml = "ProjectMailNoti";
	    // ////////////////////////////////

	    Hashtable userH = new Hashtable();
	    if (pmUser != null) {
		userH.put(pmUser.getName(), pmUser);
	    }

	    if (isSend) {
		if (isMailSend) {
		    List<WTUser> toUser = new ArrayList<WTUser>();
		    toUser.add(pmUser);
		    KETMailHelper.service.sendFormMail("08046", "NoticeMailLine2.html", project, wtuser, toUser);
		}
	    }

	    return isMailSend;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}

	return false;
    }

    /**
     * 프로젝트 상태변경 시 메일링 리스트 (중지/취소/완료/재시작)
     * 
     * @param project
     * @param state
     * @return
     * @throws Exception
     * @메소드명 : getProjectStateChangeMailingList
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static List<WTUser> getProjectStateChangeMailingList(E3PSProject project, String state) throws Exception {

	List<WTUser> to = new ArrayList<WTUser>();
	if (project instanceof MoldProject) {
	    MoldProject moldProject = (MoldProject) project;
	    if ("COMPLETED".equals(state)) {
		// 금형: 금형프로젝트 CFT, 제품프로젝트 PM
		QueryResult qr = ProjectUserHelper.manager.getProjectUser(moldProject, ProjectUserHelper.ROLEMEMBER);
		while (qr.hasMoreElements()) {
		    Object[] objects = (Object[]) qr.nextElement();
		    ProjectMemberLink link = (ProjectMemberLink) objects[0];
		    WTUser user = link.getMember();
		    if (!to.contains(user))
			to.add(user);
		}
		ProductProject productProject = ((MoldProject) moldProject).getMoldInfo().getProject();
		WTUser pm = ProjectUserHelper.manager.getPM(productProject);
		if (!to.contains(pm))
		    to.add(pm);
	    } else {
		// 금형: 금형프로젝트 CFT, 제품프로젝트 CFT
		QueryResult qr = ProjectUserHelper.manager.getProjectUser(moldProject, ProjectUserHelper.ROLEMEMBER);
		while (qr.hasMoreElements()) {
		    Object[] objects = (Object[]) qr.nextElement();
		    ProjectMemberLink link = (ProjectMemberLink) objects[0];
		    WTUser user = link.getMember();
		    if (!to.contains(user))
			to.add(user);
		}
		qr = null;
		ProductProject productProject = moldProject.getMoldInfo().getProject();
		qr = ProjectUserHelper.manager.getProjectUser(productProject, ProjectUserHelper.ROLEMEMBER);
		while (qr.hasMoreElements()) {
		    Object[] objects = (Object[]) qr.nextElement();
		    ProjectMemberLink link = (ProjectMemberLink) objects[0];
		    WTUser user = link.getMember();
		    if (!to.contains(user))
			to.add(user);
		}
	    }
	} else if (project instanceof ReviewProject) {
	    ReviewProject reviewProject = (ReviewProject) project;
	    boolean isCA = "자동차 사업부".equals(reviewProject.getAttr1());
	    // 검토, 제품: CFT 전체, PMO 그룹 전체, 임원그룹
	    QueryResult qr = ProjectUserHelper.manager.getProjectUser(reviewProject, ProjectUserHelper.ROLEMEMBER);
	    while (qr.hasMoreElements()) {
		Object[] objects = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) objects[0];
		WTUser user = link.getMember();
		if (!to.contains(user))
		    to.add(user);
	    }
	    WTGroup pmos = null;
	    WTGroup directors = null;
	    if (isCA) {
		pmos = getWTGroupObj("자동차PMO");
		// directors = getWTGroupObj("자동차사업부_임원");
	    } else {
		pmos = getWTGroupObj("전자PMO");
		directors = getWTGroupObj("전자사업부_임원");
	    }
	    Enumeration enumeration = OrganizationServicesHelper.manager.members(pmos);
	    while (enumeration.hasMoreElements()) {
		WTUser user = (WTUser) enumeration.nextElement();
		if (!to.contains(user))
		    to.add(user);
	    }
	    enumeration = null;
	    enumeration = OrganizationServicesHelper.manager.members(directors);
	    while (enumeration.hasMoreElements()) {
		WTUser user = (WTUser) enumeration.nextElement();
		if (!to.contains(user))
		    to.add(user);
	    }
	} else if (project instanceof ProductProject) {
	    ProductProject productProject = (ProductProject) project;
	    boolean isCA = "자동차 사업부".equals(productProject.getTeamType());
	    boolean isER = "전자 사업부".equals(productProject.getTeamType());
	    // 검토, 제품: CFT 전체, PMO 그룹 전체, 임원그룹
	    QueryResult qr = ProjectUserHelper.manager.getProjectUser(productProject, ProjectUserHelper.ROLEMEMBER);
	    while (qr.hasMoreElements()) {
		Object[] objects = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) objects[0];
		WTUser user = link.getMember();
		if (!to.contains(user))
		    to.add(user);
	    }
	    WTGroup pmos = null;
	    WTGroup directors = null;
	    if (isCA) {
		pmos = getWTGroupObj("자동차PMO");
		// directors = getWTGroupObj("자동차사업부_임원");
	    } else if (isER) {
		pmos = getWTGroupObj("전자PMO");
		directors = getWTGroupObj("전자사업부_임원");
	    }
	    Enumeration enumeration = OrganizationServicesHelper.manager.members(pmos);
	    while (enumeration.hasMoreElements()) {
		WTUser user = (WTUser) enumeration.nextElement();
		if (!to.contains(user))
		    to.add(user);
	    }
	    enumeration = null;

	    if (directors != null) {
		enumeration = OrganizationServicesHelper.manager.members(directors);

		while (enumeration.hasMoreElements()) {
		    WTUser user = (WTUser) enumeration.nextElement();
		    if (!to.contains(user))
			to.add(user);

		}
	    }
	} else if (project instanceof WorkProject) {
	    WorkProject workPjt = (WorkProject) project;

	    QueryResult qr = ProjectUserHelper.manager.getProjectUser(workPjt, ProjectUserHelper.ROLEMEMBER);
	    while (qr.hasMoreElements()) {
		Object[] objects = (Object[]) qr.nextElement();
		ProjectMemberLink link = (ProjectMemberLink) objects[0];
		WTUser user = link.getMember();
		if (!to.contains(user))
		    to.add(user);
	    }

	}
	return to;
    }

    public static void excelDownMy(HashMap map, OutputStream out, WTUser selectUser) {
	WritableWorkbook workbook = null;

	// Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "MyProjectList_" + formatter.format(date) + ".xls";

	try {
	    // workbook = Workbook.createWorkbook(out);
	    workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));

	    WritableSheet sheet = workbook.createSheet("myList", 1);
	    WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	    int row = 0;
	    int column = 0;

	    sheet.setColumnView(0, 12);
	    sheet.setColumnView(1, 12);
	    sheet.setColumnView(2, 30);
	    sheet.setColumnView(3, 20);
	    sheet.setColumnView(4, 12);
	    sheet.setColumnView(5, 12);
	    sheet.setColumnView(6, 15);
	    sheet.setColumnView(7, 15);
	    sheet.setColumnView(8, 15);
	    sheet.setColumnView(9, 15);
	    sheet.setColumnView(10, 10);
	    sheet.setColumnView(11, 12);
	    sheet.setColumnView(12, 12);
	    sheet.setColumnView(13, 70);

	    String titles[] = new String[] { "구분", "Project No", "Project Name", "현재 진행", "고객", "PM", "상태", "계획시작일", "계획완료일", "실제 시작일",
		    "실제완료일", "진행율", "Rank", "현재진행  MyTask" };
	    for (int i = 0; i < titles.length; i++) {
		sheet.addCell(new Label(i, row, titles[i], titleformat));
	    }
	    QueryResult rs = SearchPagingProjectHelper.findMy(map);
	    while (rs.hasMoreElements()) {
		++row;
		int columnIndex = 0;

		Object[] o = (Object[]) rs.nextElement();
		String className = (String) o[0];
		BigDecimal decimal = (BigDecimal) o[1];
		long id = decimal.longValue();

		E3PSProject project = (E3PSProject) CommonUtil.getObject(className + ":" + id);
		E3PSProjectData data = new E3PSProjectData(project);
		String projectTypeNamePrint = project.getPjtTypeName();
		String numberPrint = data.pjtNo;
		String namePrint = data.pjtName;
		String tasking = "현재 진행";
		String customerPrint = data.dependence;
		String pmPrint = data.pjtPmName;
		// String salesPrint = data.salesName;
		String statePrint = data.stateKorea;
		String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");
		String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d");
		String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
		String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
		String completion = String.valueOf(data.pjtCompletion);// 진행율
		String rank = project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName()); // Rank
		// String productType = project.getProductType()==null?"":StringUtil.checkNull(project.getProductType().getName()); //제품구분
		// String developentType = project.getDevelopentType()==null?"":StringUtil.checkNull(project.getDevelopentType().getName());
		// //개발구분
		// String customer = data.customer; //최종사용처
		// String assembledType = project.getAssembledType()==null?"":StringUtil.checkNull(project.getAssembledType().getName());
		// //조립구분

		/****************************************************************************************************************************/
		// my task 2011.06.15 by hooni

		sheet.addCell(new Label(columnIndex++, row, projectTypeNamePrint));
		sheet.addCell(new Label(columnIndex++, row, numberPrint));
		sheet.addCell(new Label(columnIndex++, row, namePrint));
		sheet.addCell(new Label(columnIndex++, row, tasking));
		sheet.addCell(new Label(columnIndex++, row, customerPrint));
		sheet.addCell(new Label(columnIndex++, row, pmPrint));
		sheet.addCell(new Label(columnIndex++, row, statePrint));
		sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
		sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
		sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
		sheet.addCell(new Label(columnIndex++, row, completion));
		sheet.addCell(new Label(columnIndex++, row, rank));

		String myTask = "";
		Hashtable htMy = null;
		try {
		    htMy = ProjectTaskHelper.manager.getMyTaskCondition(project, selectUser);
		} catch (Exception e) {
		    Kogger.error(ProjectHelper.class, e);
		}

		if (htMy != null) {
		    Vector htv = new Vector(htMy.keySet());
		    Collections.sort(htv, new TaskOneLevelSeqComparator(false));
		    for (int i = 0; i < htv.size(); i++) {

			E3PSTask task = (E3PSTask) htv.get(i);
			String task1 = task.getTaskName();
			Vector vtTask = (Vector) htMy.get(task);
			for (int v = 0; v < vtTask.size(); v++) {
			    String oneTask = "";
			    if (v == 0) {
				oneTask = task1;
			    }
			    E3PSTask etask = (E3PSTask) CommonUtil.getObject((String) vtTask.get(v));
			    E3PSTaskData etData = new E3PSTaskData(etask);

			    if (v == 0) {
				myTask = oneTask + "-" + etask.getTaskName() + "-" + DateUtil.getDateString(etData.taskPlanEndDate, "D");
			    }

			    sheet.addCell(new Label(columnIndex++, row, myTask));
			}
		    }
		}
		/****************************************************************************************************************************/

		/*
	         * sheet.addCell(new Label(columnIndex++, row, salesPrint)); sheet.addCell(new Label(columnIndex++, row, productType));
	         * sheet.addCell(new Label(columnIndex++, row, developentType)); sheet.addCell(new Label(columnIndex++, row, customer));
	         * sheet.addCell(new Label(columnIndex++, row, assembledType));
	         */
	    }

	    workbook.write();
	    workbook.close();
	} catch (Exception e) {

	} finally {
	    Kogger.debug(ProjectHelper.class, " excelDown !!!");
	}

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), "");

	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    fin.read(b);
	    out.write(b, 0, ifilesize);

	    out.flush();
	    out.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(ProjectHelper.class, wte);
	}
    }

    public static void excelDown(HashMap map, OutputStream out) {
	WritableWorkbook workbook = null;

	// Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "PMSExcel_" + formatter.format(date) + ".xls";

	try {
	    // workbook = Workbook.createWorkbook(out);
	    workbook = Workbook.createWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));

	    WritableSheet sheet = workbook.createSheet("list", 1);
	    WritableCellFormat titleformat = getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	    String pjtType = (String) map.get("pjtType");
	    if (pjtType == null) {
		return;
	    }
	    int row = 0;
	    int column = 0;

	    // SAP Interface 할지 여부.. Default 는 안하는거로 ..
	    String sap = (String) map.get("sap");

	    // 검토
	    if (pjtType.equals("0") || pjtType.equals("1")) {
		// titles size work
		sheet.setColumnView(0, 15);
		sheet.setColumnView(1, 15);
		sheet.setColumnView(2, 30);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 15);
		sheet.setColumnView(5, 15);
		sheet.setColumnView(6, 15);
		sheet.setColumnView(7, 15);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 30);
		sheet.setColumnView(11, 30);
		sheet.setColumnView(12, 15);
		sheet.setColumnView(13, 15);
		sheet.setColumnView(14, 15);
		sheet.setColumnView(15, 15);
		sheet.setColumnView(16, 15);
		sheet.setColumnView(17, 15);
		sheet.setColumnView(18, 15);
		sheet.setColumnView(19, 15);
		sheet.setColumnView(20, 15);
		sheet.setColumnView(21, 15);
		sheet.setColumnView(22, 15);
		sheet.setColumnView(23, 15);
		sheet.setColumnView(24, 15);
		sheet.setColumnView(25, 15);
		sheet.setColumnView(26, 15);
		sheet.setColumnView(27, 15);
		sheet.setColumnView(28, 15);
		sheet.setColumnView(29, 15);
		sheet.setColumnView(30, 12);

		/*
	         * String titles[] = new String[]{"요청번호", "Project No", "Project Name", "고객", "영업담당", "상태", "계획시작일", "계획완료일", "실제 시작일",
	         * "실제완료일", "진행율", "개발담당자", "사업부", "제품구분", "개발구분", "최종사용처", "조립구분", "Rank" };
	         */
		String titles[] = new String[] { "요청번호", "Project No", "Project Name", "상태", "사업부", "품목", "개발구분", "제품구분", "조립구분", "Rank",
		        "최종사용처", "고객", "차종", "적용부위", "U/S", "영업부서", "영업담당자", "개발부서", "개발담당자", "개발시작일", "계획시작일", "계획완료일", "실제시작일", "실제완료일",
		        "진행율", "DR0일", "개발원가제출일", "개발제안서제출일", "예상가", "목표가", "수익율" };

		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));
		}
		QueryResult rs = SearchPagingProjectHelper.find(map);

		Kogger.debug(ProjectHelper.class, "######### RS SIZE !! ==== " + rs.size());
		while (rs.hasMoreElements()) {
		    ++row;
		    int columnIndex = 0;
		    Object[] obj = (Object[]) rs.nextElement();
		    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
		    ReviewProject project = (ReviewProject) obj[0];

		    String rnumberPrint = "";
		    try {
			if (project.getDevRequest() != null) {
			    rnumberPrint = project.getDevRequest().getNumber();
			}
		    } catch (Exception e) {
			Kogger.error(ProjectHelper.class, e);
		    }

		    String numberPrint = data.pjtNo;
		    String namePrint = data.pjtName;
		    String statePrint = data.stateKorea;
		    String teamType = data.teamType; // 사업부
		    String infoCount = "";// 품목 - 제품정보 갯수
		    String developentType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(project.getDevelopentType()
			    .getName()); // 개발구분
		    String productType = project.getProductType() == null ? "" : StringUtil.checkNull(project.getProductType().getName()); // 제품구분
		    String assembledType = project.getAssembledType() == null ? "" : StringUtil.checkNull(project.getAssembledType()
			    .getName()); // 조립구분
		    String rank = project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName()); // Rank
		    String customer = data.customer; // 최종사용처
		    String customerPrint = data.dependence; // 고객
		    String carInfo = ""; // 차종 A
		    String areas = ""; // 적용부위 1개만
		    String us = "";// us : 최소값
		    String salseDpt = data.salesDept;// 영업부서
		    String salesPrint = data.salesName; // 영업담당자
		    String pmDpt = data.devDept; // 개발부서
		    String pmPrint = data.pjtPmName;// 개발담당자
		    String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d"); // 계획 시작일 / 개발 시작일
		    String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d"); // 계획 종료일
		    String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
		    String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
		    String completion = String.valueOf(data.pjtCompletion);// 진행율
		    String dr0 = ""; // DR0;
		    String estimateDate = DateUtil.getDateString(project.getEstimateDate(), "D");
		    String proposalDate = DateUtil.getDateString(project.getProposalDate(), "D");
		    String price = "";
		    String cost = "";
		    String rate = "";

		    QuerySpec qs = new QuerySpec();
		    int idxpi = qs.appendClassList(ProductInfo.class, true);
		    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=",
			    CommonUtil.getOIDLongValue(data.e3psPjtOID));
		    qs.appendWhere(cs, new int[] { idxpi });
		    SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
		    QueryResult qrpi = PersistenceHelper.manager.find(qs);
		    int productCount = 0;
		    int compareus1 = 0;
		    int compareus2 = 10000;
		    int usCount = 0;
		    int priceInt = 0;
		    int costInt = 0;
		    int rateInt = 0;

		    while (qrpi.hasMoreElements()) {
			Object o[] = (Object[]) qrpi.nextElement();
			ProductInfo pi = (ProductInfo) o[0];
			String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
			String carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
			// 차종

			if (carName.length() > 0) {
			    if (carInfo.length() == 0) {
				carInfo = carName;
			    } else {
				carInfo = carInfo + ", " + carName;
			    }
			} else {
			    if (carInfo.length() == 0) {
				carInfo = carName2;
			    } else {
				carInfo = carInfo + ", " + carName2;
			    }
			}

			productCount++;
			if (pi.getAreas() != null) {
			    if (pi.getAreas().length() > 0) {
				areas = pi.getAreas();
			    }
			}
			// U/S
			if (pi.getUsage() != null) {
			    compareus1 = Integer.parseInt(pi.getUsage());
			    if (compareus1 != 0) {
				if (compareus1 < compareus2) {
				    usCount = compareus1;
				}
				compareus2 = 0;
				if (compareus1 < usCount) {
				    usCount = compareus1;
				}
			    }
			}
			// 예상가, 목표가, 수익율
			if (pi.getPrice() != null) {
			    if (pi.getPrice().length() > 0) {
				priceInt = priceInt + Integer.parseInt(pi.getPrice());
			    }
			}

			if (StringUtil.checkNullZero(pi.getPrice()).length() > 0) {
			    costInt = costInt + Integer.parseInt(StringUtil.checkNullZero(pi.getCost()));
			} else {
			    costInt = 0;
			}

			if (StringUtil.checkNullZero(pi.getPrice()).length() > 0) {
			    rateInt = rateInt + Integer.parseInt(StringUtil.checkNullZero(pi.getRate()));
			} else {
			    rateInt = 0;
			}

		    }

		    E3PSTask et = MoldProjectHelper.getTask((E3PSProject) project, "DR0");
		    if (et != null) {
			E3PSTaskData etd = new E3PSTaskData(et);
			dr0 = DateUtil.getDateString(etd.taskPlanStartDate, "D");
		    }
		    long rateLong = 0;
		    if (rateInt != 0) {
			rateLong = Math.round(rateInt / productCount);
		    }

		    infoCount = "" + productCount;
		    us = "" + usCount;
		    price = "" + priceInt;
		    cost = "" + costInt;
		    rate = "" + rateLong;

		    sheet.addCell(new Label(columnIndex++, row, rnumberPrint));
		    sheet.addCell(new Label(columnIndex++, row, numberPrint));
		    sheet.addCell(new Label(columnIndex++, row, namePrint));
		    sheet.addCell(new Label(columnIndex++, row, statePrint));
		    sheet.addCell(new Label(columnIndex++, row, teamType));
		    sheet.addCell(new Label(columnIndex++, row, infoCount));
		    sheet.addCell(new Label(columnIndex++, row, developentType));
		    sheet.addCell(new Label(columnIndex++, row, productType));
		    sheet.addCell(new Label(columnIndex++, row, assembledType));
		    sheet.addCell(new Label(columnIndex++, row, rank));
		    sheet.addCell(new Label(columnIndex++, row, customer));
		    sheet.addCell(new Label(columnIndex++, row, customerPrint));
		    sheet.addCell(new Label(columnIndex++, row, carInfo));
		    sheet.addCell(new Label(columnIndex++, row, areas));
		    sheet.addCell(new Label(columnIndex++, row, us));
		    sheet.addCell(new Label(columnIndex++, row, salseDpt));
		    sheet.addCell(new Label(columnIndex++, row, salesPrint));
		    sheet.addCell(new Label(columnIndex++, row, pmDpt));
		    sheet.addCell(new Label(columnIndex++, row, pmPrint));
		    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
		    sheet.addCell(new Label(columnIndex++, row, completion));
		    sheet.addCell(new Label(columnIndex++, row, dr0));
		    sheet.addCell(new Label(columnIndex++, row, estimateDate));
		    sheet.addCell(new Label(columnIndex++, row, proposalDate));
		    sheet.addCell(new Label(columnIndex++, row, price));
		    sheet.addCell(new Label(columnIndex++, row, cost));
		    sheet.addCell(new Label(columnIndex++, row, rate));
		}
	    }

	    // 제품
	    if (pjtType.equals("2") || pjtType.equals("4")) {
		sheet.setColumnView(0, 15);
		sheet.setColumnView(1, 30);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 15);
		sheet.setColumnView(5, 15);
		sheet.setColumnView(6, 15);
		sheet.setColumnView(7, 15);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 15);
		sheet.setColumnView(11, 15);
		sheet.setColumnView(12, 15);
		sheet.setColumnView(13, 15);
		sheet.setColumnView(14, 15);
		sheet.setColumnView(15, 15);
		sheet.setColumnView(16, 15);
		sheet.setColumnView(17, 15);
		sheet.setColumnView(18, 15);
		sheet.setColumnView(19, 15);
		sheet.setColumnView(20, 15);
		sheet.setColumnView(21, 15);
		sheet.setColumnView(22, 15);
		sheet.setColumnView(23, 15);
		sheet.setColumnView(24, 15);
		sheet.setColumnView(25, 15);
		sheet.setColumnView(26, 15);
		sheet.setColumnView(27, 15);
		sheet.setColumnView(28, 15);
		sheet.setColumnView(29, 15);
		sheet.setColumnView(30, 15);
		sheet.setColumnView(31, 15);
		sheet.setColumnView(32, 15);
		sheet.setColumnView(33, 15);
		sheet.setColumnView(34, 15);
		sheet.setColumnView(35, 15);
		sheet.setColumnView(36, 15);
		sheet.setColumnView(37, 15);
		sheet.setColumnView(38, 15);
		sheet.setColumnView(39, 15);
		sheet.setColumnView(40, 15);
		sheet.setColumnView(41, 15);
		sheet.setColumnView(42, 15);
		sheet.setColumnView(43, 15);
		sheet.setColumnView(44, 15);
		sheet.setColumnView(45, 15);
		sheet.setColumnView(46, 15);
		sheet.setColumnView(47, 15);
		sheet.setColumnView(48, 15);
		sheet.setColumnView(49, 15);
		sheet.setColumnView(50, 15);
		sheet.setColumnView(51, 15);
		sheet.setColumnView(52, 15);
		sheet.setColumnView(53, 15);
		sheet.setColumnView(54, 15);
		sheet.setColumnView(55, 15);
		sheet.setColumnView(56, 15);
		sheet.setColumnView(57, 15);
		sheet.setColumnView(58, 15);
		sheet.setColumnView(59, 15);
		sheet.setColumnView(60, 15);
		sheet.setColumnView(61, 15);
		sheet.setColumnView(62, 15);
		sheet.setColumnView(63, 15);
		sheet.setColumnView(64, 15);
		sheet.setColumnView(65, 15);

		String titles[] = new String[] { "Project No", "Project Name", "상태", "Part No", "Process", "접수번호", "검토Project No", "사업부",
		        "개발구분", "제품구분", "조립구분", "설계구분", "Rank", "최종사용처", "고객", "대표차종", "Event", "적용부위", "U/S", "영업부서", "영업담당", "개발부서",
		        "개발담당", "생산구분", "조립생산처", "Item수", "금형(P)", "금형(M)", "구매품", "개발시작일", "계획시작일", "계획완료일", "실제시작일", "실제완료일", "진행율",
		        "목표가", "예산", "실적", "금형예산", "설비예산", "지그예산", "경비예산", "금형실적", "설비실적", "지그실적", "경비실적", "DR1실적", "DR2실적", "DR3실적",
		        "DR4실적", "DR5실적", "DR6실적", "Q_점수", "Q_평가", "C_점수", "C_평가", "D_점수", "D_평가", "T_점수", "T_평가", "설변_건수", "총점_점수",
		        "총점_평가", "예상가(원)", "판매가(원)", "수익률" };
		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));
		}
		QueryResult rs = SearchPagingProjectHelper.find(map);
		while (rs.hasMoreElements()) {
		    ++row;
		    int columnIndex = 0;
		    Object[] obj = (Object[]) rs.nextElement();
		    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
		    ProductProject project = (ProductProject) obj[0];
		    String numberPrint = data.pjtNo;
		    String namePrint = data.pjtName;
		    String statePrint = data.stateKorea;
		    String partNo = data.partNo;
		    String process = project.getProcess() == null ? "" : StringUtil.checkNull(project.getProcess().getName());
		    String devRNumber = ""; // 접수번호
		    String devROid = ""; // 접수 OID
		    try {
			if (project.getDevRequest() != null) {
			    devRNumber = project.getDevRequest().getNumber();
			    devROid = CommonUtil.getOIDString(project.getDevRequest());
			}
		    } catch (Exception e) {
			Kogger.error(ProjectHelper.class, e);
		    }
		    String reviewpjtNo = ""; // 검토 프로젝트 No

		    if (devROid != null && devROid.length() > 0) {
			e3ps.dms.entity.KETDevelopmentRequest dr = (e3ps.dms.entity.KETDevelopmentRequest) CommonUtil.getObject(devROid);
			if (dr.getProjectOID() != null && dr.getProjectOID().length() > 0) {
			    Object reviewobj = CommonUtil.getObject(dr.getProjectOID());
			    if (reviewobj instanceof E3PSProject) {
				E3PSProject reviewProject = (E3PSProject) reviewobj;
				reviewpjtNo = reviewProject.getPjtNo();
			    }
			}
		    }

		    String teamType = data.teamType; // 사업부
		    String developentType = project.getDevelopentType() == null ? "" : StringUtil.checkNull(project.getDevelopentType()
			    .getName()); // 개발구분
		    String productType = project.getProductType() == null ? "" : StringUtil.checkNull(project.getProductType().getName()); // 제품구분
		    String assembledType = project.getAssembledType() == null ? "" : StringUtil.checkNull(project.getAssembledType()
			    .getName()); // 조립구분
		    String designType = project.getDesignType() == null ? "" : StringUtil.checkNull(project.getDesignType().getName());// 설계
			                                                                                                               // 구분
		    String rank = project.getRank() == null ? "" : StringUtil.checkNull(project.getRank().getName()); // Rank
		    String customer = data.customer; // 최종사용처
		    String customerPrint = data.dependence; // 고객
		    String representModel = data.representModel; // 대표차종
		    String oemPrint = "";
		    String eventPrint = ""; // 이벤트
		    try {
			if (project.getOemType() != null) {
			    oemPrint = project.getOemType().getName();
			    eventPrint = data.getNowEventName(project.getOemType());
			}
		    } catch (Exception e) {
			Kogger.error(ProjectHelper.class, e);
		    }
		    String areas = ""; // 적용부위 1개만
		    String us = ""; // us
		    String rate = ""; // rate
		    String price = ""; // price
		    String cost = ""; // cost

		    QuerySpec qs = new QuerySpec();
		    int idxpi = qs.appendClassList(ProductInfo.class, true);
		    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=",
			    CommonUtil.getOIDLongValue(data.e3psPjtOID));
		    qs.appendWhere(cs, new int[] { idxpi });
		    SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
		    QueryResult qrpi = PersistenceHelper.manager.find(qs);
		    int compareus1 = 0;
		    int compareus2 = 10000;
		    int usCount = 0;
		    while (qrpi.hasMoreElements()) {
			Object o[] = (Object[]) qrpi.nextElement();
			ProductInfo pi = (ProductInfo) o[0];
			if (pi.getAreas() != null) {
			    if (pi.getAreas().length() > 0) {
				areas = pi.getAreas();
			    }
			}
			// U/S
			if (pi.getUsage() != null) {
			    compareus1 = Integer.parseInt(pi.getUsage());
			    if (compareus1 != 0) {
				if (compareus1 < compareus2) {
				    usCount = compareus1;
				}
				compareus2 = 0;
				if (compareus1 < usCount) {
				    usCount = compareus1;
				}
			    }
			}

			if (!StringUtil.checkNull(pi.getRate()).equals("")) {
			    rate += pi.getRate() + "%,";
			}
			if (!StringUtil.checkNull(pi.getPrice()).equals("")) {
			    price += pi.getPrice() + ",";
			}
			if (!StringUtil.checkNull(pi.getCost()).equals("")) {
			    cost += pi.getCost() + ",";
			}
		    }
		    us = "" + usCount;

		    if (!StringUtil.checkNull(rate).equals("")) {
			rate = rate.substring(0, rate.length() - 1);
		    }

		    if (!StringUtil.checkNull(price).equals("")) {
			price = price.substring(0, price.length() - 1);
		    }

		    if (!StringUtil.checkNull(cost).equals("")) {
			cost = cost.substring(0, cost.length() - 1);
		    }

		    String salseDpt = data.salesDept;// 영업부서
		    String salesPrint = data.salesName; // 영업담당자
		    String pmDpt = data.department; // 개발부서
		    String pmPrint = data.pjtPmName;// 개발담당자

		    String inOutType = ""; // 생산구분
		    String inoutName = ""; // 조립생산처
		    PartnerDao partnerDao = null;
		    if (((ProductProject) project).getPartnerNo() != null && ((ProductProject) project).getPartnerNo().length() > 0) {
			partnerDao = new PartnerDao();
			inOutType = "외주";
			inoutName = partnerDao.ViewPartnerName(((ProductProject) project).getPartnerNo());
		    } else if (project.getAssemblyPlace() != null) {
			inOutType = "사내";
			inoutName = StringUtil.checkNull(project.getAssemblyPlace().getName());
		    }

		    String itemCountStr = ""; // Item수
		    String pressCountStr = ""; // 금형(P)
		    String moldCoutnStr = ""; // 금형(M)
		    String gCountStr = ""; // 구매품

		    int pressCount = 0;
		    int moldCount = 0;
		    int gCount = 0;
		    int itemTotal = 0;
		    QueryResult rt = ProductProjectHelper.manager.getMoldItemInfo((ProductProject) project);
		    while (rt.hasMoreElements()) {
			Object[] objItem = (Object[]) rt.nextElement();
			MoldItemInfo miInfo = (MoldItemInfo) objItem[0];

			if ("Press".equals(miInfo.getItemType()))
			    pressCount++;
			else if ("Mold".equals(miInfo.getItemType()))
			    moldCount++;
			else if ("구매품".equals(miInfo.getItemType()))
			    gCount++;
		    }
		    itemTotal = pressCount + moldCount + gCount;
		    itemCountStr = String.valueOf(itemTotal);
		    pressCountStr = String.valueOf(pressCount);
		    moldCoutnStr = String.valueOf(moldCount);
		    gCountStr = String.valueOf(gCount);

		    String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");// 개발시작일, 계획 시작일
		    String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d");// 게획 종료일
		    String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
		    String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
		    String completion = String.valueOf(data.pjtCompletion);// 진행율

		    String t1 = "";// 목표가 T
		    String t2 = "";// 예산 T
		    String t3 = "";// 실적 T
		    String b1 = "";// 금형예산
		    String b2 = "";// 설비예산
		    String b3 = "";// 지그예산
		    String b4 = "";// 경비예산

		    String r1 = "";// 금형실적
		    String r2 = "";// 설비실적
		    String r3 = "";// 지그실적
		    String r4 = "";// 경비실적

		    long targetCost1 = 0;
		    long targetCost2 = 0;
		    long targetCost3 = 0;
		    long targetCost4 = 0;
		    long budget1 = 0;
		    long budget2 = 0;
		    long budget3 = 0;
		    long budget4 = 0;
		    long resultsCost1 = 0;
		    long resultsCost2 = 0;
		    long resultsCost3 = 0;
		    long resultsCost4 = 0;
		    long balanceCost1 = 0;
		    long balanceCost2 = 0;
		    long balanceCost3 = 0;
		    long balanceCost4 = 0;

		    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		    int count = 0;
		    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
		    if ("sap".equals(sap)) {
			while (rtCost.hasMoreElements()) {
			    Object[] objCost = (Object[]) rtCost.nextElement();
			    CostInfo costInfo = (CostInfo) objCost[0];

			    long budget = 0; // 예산
			    long executionCost = 0; // 초기집행가
			    long editCost = 0; // 추가집행가
			    long totalExpense = 0; // 총집행가
			    long balanceCost = 0; // 잔액

			    if (costInfo.getOrderInvest() != null) {
				Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
				Long totalPriceObj = (Long) datas.get(ProductPrice.TOTALPRICE);
				Long initExpenseObj = (Long) datas.get(ProductPrice.INITEXPENSE);
				Long addExpenseObj = (Long) datas.get(ProductPrice.ADDEXPENSE);
				Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);

				if (totalPriceObj != null)
				    budget = totalPriceObj.longValue(); // 예산
				if (initExpenseObj != null)
				    executionCost = initExpenseObj.longValue(); // 초기집행가
				if (addExpenseObj != null)
				    editCost = addExpenseObj.longValue(); // 추가집행가
				if (totalExpenseObj != null)
				    totalExpense = totalExpenseObj.longValue(); // 총집행가
				balanceCost = budget - totalExpense; // 잔액
			    } else {
				if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    budget = Long.parseLong(costInfo.getTargetCost().replaceAll(",", "")); // 예산

				if (costInfo.getExecutionCost() != null && costInfo.getExecutionCost().length() > 0)
				    executionCost = Integer.parseInt(costInfo.getExecutionCost().replaceAll(",", "")); // 초기집행가
				else if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    executionCost = Integer.parseInt(costInfo.getTargetCost().replaceAll(",", "")); // 초기집행가

				if (costInfo.getEditCost() != null && costInfo.getEditCost().length() > 0)
				    editCost = Integer.parseInt(costInfo.getEditCost().replaceAll(",", "")); // 추가집행가

				totalExpense = executionCost + editCost; // 총집행가
				balanceCost = budget - totalExpense; // 잔액
			    }

			    if ("금형".equals(costInfo.getCostType())) {
				if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    targetCost1 = targetCost1 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				budget1 = budget1 + budget;
				resultsCost1 = resultsCost1 + executionCost + editCost;
				balanceCost1 = balanceCost1 + balanceCost;
			    } else if ("설비".equals(costInfo.getCostType())) {
				if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    targetCost2 = targetCost2 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				budget2 = budget2 + budget;
				resultsCost2 = resultsCost2 + executionCost + editCost;
				balanceCost2 = balanceCost2 + balanceCost;
			    } else if ("JIG".equals(costInfo.getCostType())) {
				if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    targetCost4 = targetCost4 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				budget4 = budget4 + budget;
				resultsCost4 = resultsCost4 + executionCost + editCost;
				balanceCost4 = balanceCost4 + balanceCost;
			    } else {
				if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0)
				    targetCost3 = targetCost3 + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
				budget3 = budget3 + budget;
				resultsCost3 = resultsCost3 + executionCost + editCost;
				balanceCost3 = balanceCost3 + balanceCost;
			    }
			}
			t1 = nf.format(targetCost1 + targetCost2 + targetCost3 + targetCost4);
			t2 = nf.format(budget1 + budget2 + budget3 + budget4);
			t3 = nf.format(resultsCost1 + resultsCost2 + resultsCost3 + resultsCost4);
			b1 = nf.format(budget1);
			b2 = nf.format(budget2);
			b3 = nf.format(budget4);
			b4 = nf.format(budget3);
			r1 = nf.format(resultsCost1);
			r2 = nf.format(resultsCost2);
			r3 = nf.format(resultsCost4);
			r4 = nf.format(resultsCost3);
		    }
		    int ecoCountInt = PerformanceHelper.manager.ecogetMax(project);
		    String dr1 = "";
		    String dr2 = "";
		    String dr3 = "";
		    String dr4 = "";
		    String dr5 = "";
		    String dr6 = "";
		    E3PSTask task = null;
		    for (int i = 1; i < 7; i++) {
			task = MoldProjectHelper.getTask(project, "DR" + i);
			if (task != null) {
			    e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(task);
			    if (drDocument != null) {
				int drPoint = drDocument.getDRCheckPoint();
				if (i == 1) {
				    dr1 = "" + drPoint;
				}
				if (i == 2) {
				    dr2 = "" + drPoint;
				}
				if (i == 3) {
				    dr3 = "" + drPoint;
				}
				if (i == 4) {
				    dr4 = "" + drPoint;
				}
				if (i == 5) {
				    dr5 = "" + drPoint;
				}
				if (i == 6) {
				    dr6 = "" + drPoint;
				}
			    }
			}
		    }
		    String qt = "";
		    String qe = "";
		    String ct = "";
		    String ce = "";
		    String dt = "";
		    String de = "";
		    String tt = "";
		    String te = "";
		    String ecoCount = "" + ecoCountInt;
		    String totalScoreStr = "";
		    String totalevaluate = "";

		    Performance pf = null;
		    QueryResult qrtest = PerformanceHelper.manager.searchPerformance(project.getPjtNo());
		    Object[] pobj = null;
		    if (qrtest.hasMoreElements()) {
			pobj = (Object[]) qrtest.nextElement();
			pf = (Performance) pobj[0];
		    }

		    Weights wg = null;
		    QueryResult wgQr = null;
		    int totalScore = 0;
		    String totalEvaluate = "";
		    wgQr = PerformanceHelper.manager.searchWeights(0, true, project.getPjtNo());
		    Object[] wgobj = null;
		    if (wgQr.hasMoreElements()) {
			wgobj = (Object[]) wgQr.nextElement();
			wg = (Weights) wgobj[0];
		    }
		    if (wg != null) {
			if (pf.getScoreQuality() != null && pf.getScoreCost() != null && pf.getScoreDelivery1() != null
			        && pf.getScoreTechnical() != null && pf.getScoreQuality().length() > 0 && pf.getScoreCost().length() > 0
			        && pf.getScoreDelivery1().length() > 0 && pf.getScoreTechnical().length() > 0) {
			    totalScore = Integer.parseInt(pf.getScoreQuality()) + Integer.parseInt(pf.getScoreCost())
				    + Integer.parseInt(pf.getScoreDelivery1()) + Integer.parseInt(pf.getScoreTechnical());
			    totalScoreStr = "" + totalScore;
			    qt = pf.getScoreQuality();
			    qe = pf.getEvaluateQuality();
			    ct = pf.getScoreCost();
			    ce = pf.getEvaluateCost();
			    dt = pf.getScoreDelivery1();
			    de = pf.getEvaluateDelivery1();
			    tt = pf.getScoreTechnical();
			    te = pf.getEvaluateTechnical();

			}
			if (totalScore != 0) {
			    if (wg.getTotalS() <= totalScore) {
				totalevaluate = "S";
			    } else if (wg.getTotalA() <= totalScore) {
				totalevaluate = "A";
			    } else if (wg.getTotalB() <= totalScore) {
				totalevaluate = "B";
			    } else if (wg.getTotalC() <= totalScore) {
				totalevaluate = "C";
			    } else {
				totalevaluate = "D";
			    }
			}
		    }
		    sheet.addCell(new Label(columnIndex++, row, numberPrint));
		    sheet.addCell(new Label(columnIndex++, row, namePrint));
		    sheet.addCell(new Label(columnIndex++, row, statePrint));
		    sheet.addCell(new Label(columnIndex++, row, partNo));
		    sheet.addCell(new Label(columnIndex++, row, process));
		    sheet.addCell(new Label(columnIndex++, row, devRNumber));
		    sheet.addCell(new Label(columnIndex++, row, reviewpjtNo));
		    sheet.addCell(new Label(columnIndex++, row, teamType));
		    sheet.addCell(new Label(columnIndex++, row, developentType));
		    sheet.addCell(new Label(columnIndex++, row, productType));
		    sheet.addCell(new Label(columnIndex++, row, assembledType));
		    sheet.addCell(new Label(columnIndex++, row, designType));
		    sheet.addCell(new Label(columnIndex++, row, rank));
		    sheet.addCell(new Label(columnIndex++, row, customer));
		    sheet.addCell(new Label(columnIndex++, row, customerPrint));
		    sheet.addCell(new Label(columnIndex++, row, representModel));
		    sheet.addCell(new Label(columnIndex++, row, eventPrint));
		    sheet.addCell(new Label(columnIndex++, row, areas));
		    sheet.addCell(new Label(columnIndex++, row, us));
		    sheet.addCell(new Label(columnIndex++, row, salseDpt));
		    sheet.addCell(new Label(columnIndex++, row, salesPrint));
		    sheet.addCell(new Label(columnIndex++, row, pmDpt));
		    sheet.addCell(new Label(columnIndex++, row, pmPrint));
		    sheet.addCell(new Label(columnIndex++, row, inOutType));
		    sheet.addCell(new Label(columnIndex++, row, inoutName));
		    sheet.addCell(new Label(columnIndex++, row, itemCountStr));
		    sheet.addCell(new Label(columnIndex++, row, pressCountStr));
		    sheet.addCell(new Label(columnIndex++, row, moldCoutnStr));
		    sheet.addCell(new Label(columnIndex++, row, gCountStr));
		    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
		    sheet.addCell(new Label(columnIndex++, row, completion));
		    sheet.addCell(new Label(columnIndex++, row, t1));
		    sheet.addCell(new Label(columnIndex++, row, t2));
		    sheet.addCell(new Label(columnIndex++, row, t3));
		    sheet.addCell(new Label(columnIndex++, row, b1));
		    sheet.addCell(new Label(columnIndex++, row, b2));
		    sheet.addCell(new Label(columnIndex++, row, b3));
		    sheet.addCell(new Label(columnIndex++, row, b4));
		    sheet.addCell(new Label(columnIndex++, row, r1));
		    sheet.addCell(new Label(columnIndex++, row, r2));
		    sheet.addCell(new Label(columnIndex++, row, r3));
		    sheet.addCell(new Label(columnIndex++, row, r4));
		    sheet.addCell(new Label(columnIndex++, row, dr1));
		    sheet.addCell(new Label(columnIndex++, row, dr2));
		    sheet.addCell(new Label(columnIndex++, row, dr3));
		    sheet.addCell(new Label(columnIndex++, row, dr4));
		    sheet.addCell(new Label(columnIndex++, row, dr5));
		    sheet.addCell(new Label(columnIndex++, row, dr6));
		    sheet.addCell(new Label(columnIndex++, row, qt));
		    sheet.addCell(new Label(columnIndex++, row, qe));
		    sheet.addCell(new Label(columnIndex++, row, ct));
		    sheet.addCell(new Label(columnIndex++, row, ce));
		    sheet.addCell(new Label(columnIndex++, row, dt));
		    sheet.addCell(new Label(columnIndex++, row, de));
		    sheet.addCell(new Label(columnIndex++, row, tt));
		    sheet.addCell(new Label(columnIndex++, row, te));
		    sheet.addCell(new Label(columnIndex++, row, ecoCount));
		    sheet.addCell(new Label(columnIndex++, row, totalScoreStr));
		    sheet.addCell(new Label(columnIndex++, row, totalevaluate));
		    sheet.addCell(new Label(columnIndex++, row, price));
		    sheet.addCell(new Label(columnIndex++, row, cost));
		    sheet.addCell(new Label(columnIndex++, row, rate));

		}
	    }

	    // 금형
	    if (pjtType.equals("3")) {
		// titles size work
		sheet.setColumnView(0, 15);
		sheet.setColumnView(1, 15);
		sheet.setColumnView(2, 15);
		sheet.setColumnView(3, 15);
		sheet.setColumnView(4, 30);
		sheet.setColumnView(5, 15);
		sheet.setColumnView(6, 15);
		sheet.setColumnView(7, 15);
		sheet.setColumnView(8, 15);
		sheet.setColumnView(9, 15);
		sheet.setColumnView(10, 15);
		sheet.setColumnView(11, 15);
		sheet.setColumnView(12, 15);
		sheet.setColumnView(13, 15);
		sheet.setColumnView(14, 15);
		sheet.setColumnView(15, 15);
		sheet.setColumnView(16, 15);
		sheet.setColumnView(17, 15);
		sheet.setColumnView(18, 15);
		sheet.setColumnView(19, 15);
		sheet.setColumnView(20, 15);
		sheet.setColumnView(21, 15);
		sheet.setColumnView(22, 15);
		sheet.setColumnView(23, 15);
		sheet.setColumnView(24, 15);
		sheet.setColumnView(25, 15);
		sheet.setColumnView(26, 15);
		sheet.setColumnView(27, 15);
		sheet.setColumnView(28, 15);
		sheet.setColumnView(29, 15);
		sheet.setColumnView(30, 15);
		sheet.setColumnView(31, 15);
		sheet.setColumnView(32, 15);
		sheet.setColumnView(33, 15);
		sheet.setColumnView(34, 15);
		sheet.setColumnView(35, 15);
		sheet.setColumnView(36, 15);
		sheet.setColumnView(37, 15);
		sheet.setColumnView(38, 15);
		sheet.setColumnView(39, 15);
		sheet.setColumnView(40, 15);
		sheet.setColumnView(41, 15);
		sheet.setColumnView(42, 15);
		sheet.setColumnView(43, 15);
		sheet.setColumnView(44, 15);
		sheet.setColumnView(45, 15);
		sheet.setColumnView(46, 15);
		sheet.setColumnView(47, 15);
		sheet.setColumnView(48, 15);
		sheet.setColumnView(49, 15);
		sheet.setColumnView(50, 15);
		sheet.setColumnView(51, 20);
		sheet.setColumnView(52, 15);
		sheet.setColumnView(53, 15);
		sheet.setColumnView(54, 15);
		sheet.setColumnView(55, 15);
		sheet.setColumnView(56, 15);
		sheet.setColumnView(57, 15);
		sheet.setColumnView(58, 15);
		sheet.setColumnView(59, 15);
		sheet.setColumnView(60, 15);
		sheet.setColumnView(61, 15);
		sheet.setColumnView(62, 15);

		String titles[] = new String[] { "Project No", "Die No", "Part No", "Part Name", "고객", "SOP", "대표차종", "개발구분", "금형구분",
		        "금형분류", "금형유형", "금형Rank", "상태", "금형단계", "제작구분", "제작처", "생산구분", "생산처", "Cavity", "설비Ton(형체력)", "원재료Maker",
		        "원재료Type", "원재료Grade", "원재료특성", "원재료특성(1)", "DR1실적", "제품도계획", "제품도실적", "금형설계일정", "금형제작계획", "금형제작실적", "초도검토회의계획",
		        "초도검토회의실적", "디버깅1차기간", "디버깅2차기간", "디버깅3차기간", "디버깅4차기간", "디버깅5차기간", "디버깅6차기간", "디버깅7차기간", "디버깅8차기간", "디버깅9차기간",
		        "디버깅10차기간", "제품합격계획", "제품합격실적", "금형이관계획", "금형이관실적", "계획시작일", "계획완료일", "실제시작일", "실제완료일", "진행율", "개발담당부서", "PM",
		        "제품개발", "금형개발", "금형설계", "금형구매", "투자오더", "금형비예산", "제작비", "디버깅비", "금형비용실적" };
		for (int i = 0; i < titles.length; i++) {
		    sheet.addCell(new Label(i, row, titles[i], titleformat));
		}
		QueryResult rs = SearchPagingProjectHelper.find(map);

		int count = 0;
		while (rs.hasMoreElements()) {
		    ++row;
		    Kogger.debug(ProjectHelper.class, "문제가 발생한 라인.. == " + count++); // 몇번째 줄에서 끊기는지 확인 하기 위해서 찍음...
		    int columnIndex = 0;
		    Object[] obj = (Object[]) rs.nextElement();
		    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);

		    MoldProject moldProject = (MoldProject) data.e3psProject;
		    MoldItemInfo itemInfo = moldProject.getMoldInfo();
		    ProductProject productProject = null;
		    E3PSProjectData productData = null;
		    if (itemInfo != null) {
			productProject = itemInfo.getProject();
			if (moldProject != null) {
			    productData = new E3PSProjectData(productProject);
			}
		    }
		    String pjtNoPrint = productData.pjtNo; // 제품 Pjt NO
		    String dieNoPrint = itemInfo.getDieNo(); // Die No
		    String partNoPrint = itemInfo.getPartNo(); // Part No
		    String partNamePrint = itemInfo.getPartName(); // Part Name
		    String customer = ""; // 고객
		    Kogger.debug(ProjectHelper.class, "다이 넘버 확인.. == " + dieNoPrint);
		    if (productData.dependence != null) {
			customer = productData.dependence;
		    }
		    String sop = "";
		    QueryResult qr = OEMTypeHelper.getCustomerEvent(productProject.getOemType());
		    if (qr != null) {
			while (qr.hasMoreElements()) {
			    Object[] oo = (Object[]) qr.nextElement();
			    OEMPlan op = (OEMPlan) oo[0];
			    sop = DateUtil.getTimeFormat(op.getOemEndDate(), "yyyy-MM-dd");
			}
		    }
		    String carTypePrint = ""; // 대표차종
		    if (productData.representModel != null) {
			carTypePrint = productData.representModel;
		    }
		    String developentType = ""; // 개발구분
		    if (productProject.getDevelopentType() != null) {
			developentType = StringUtil.checkNull(productProject.getDevelopentType().getName());
		    }

		    String itemTypePrint = itemInfo.getItemType(); // 금형구분
		    String productTypePrint = "";
		    if (itemInfo.getProductType() != null) {
			productTypePrint = itemInfo.getProductType().getName(); // 금형분류
		    }
		    String moldTypePrint = ""; // 금형유형
		    if (itemInfo.getMoldType() != null) {
			moldTypePrint = itemInfo.getMoldType().getName();
		    }
		    String moldRankPrint = ""; // 금형 Rank
		    if (moldProject.getRank() != null) {
			moldRankPrint = moldProject.getRank().getName();
		    }
		    String statePrint = data.stateKorea; // 상태
		    String progressMold = data.currentTaskName; // 금형단계 (현재 진행 태스크 )
		    String making = ""; // 제작 구분
		    if (itemInfo.getMaking() != null) {
			making = itemInfo.getMaking();
		    }
		    String outSourcing = ""; // 제작처
		    if (moldProject.getOutSourcing() != null && moldProject.getOutSourcing().length() > 0) {
			outSourcing = moldProject.getOutSourcing();
		    }
		    // 생산처
		    String inoutName1 = ""; // 생산구분
		    String inoutName2 = ""; // 생산처
		    PartnerDao partnerDao2 = null;

		    if (itemInfo.getPartnerNo() != null && itemInfo.getPartnerNo().length() > 0) {
			partnerDao2 = new PartnerDao();
			inoutName1 = "외주";
			inoutName2 = partnerDao2.ViewPartnerName(itemInfo.getPartnerNo());
		    } else if (itemInfo.getPurchasePlace() != null) {
			inoutName1 = "사내";
			inoutName2 = StringUtil.checkNull(itemInfo.getPurchasePlace().getName());
		    }
		    String cavity = ""; // Cavity //Cavity - Line*Pcs
		    boolean isMold = false;
		    if (itemTypePrint.equals("Mold")) {
			isMold = true;
		    }
		    double intCavity = 0;
		    if (itemInfo.getCVPitch() != null) {
			cavity = itemInfo.getCVPitch();
			if (isMold) {
			    try {
				QuerySpec specMoldItem = new QuerySpec();
				int idx = specMoldItem.addClassList(MoldItemInfo.class, true);
				SearchCondition sc = new SearchCondition(MoldItemInfo.class, "projectReference.key.id",
				        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(productProject));
				specMoldItem.appendWhere(sc, new int[] { idx });
				specMoldItem.appendAnd();
				specMoldItem.appendWhere(
				        new SearchCondition(MoldItemInfo.class, "dieNo", SearchCondition.EQUAL, itemInfo.getDieNo()),
				        new int[] { idx });
				QueryResult rt = PersistenceHelper.manager.find(specMoldItem);
				MoldItemInfo mInfo = null;
				Object[] obj2 = null;
				Vector vt = new Vector();
				while (rt.hasMoreElements()) {
				    obj2 = (Object[]) rt.nextElement();
				    mInfo = (MoldItemInfo) obj2[0];

				    if (mInfo.getCVPitch() != null) {
					intCavity = intCavity + Double.parseDouble(mInfo.getCVPitch());
					if (intCavity != 0) {
					    vt.add(mInfo.getCVPitch());
					}
				    }
				}
				if (intCavity != 0) {
				    cavity = "";
				}
				for (int i = 0; i < vt.size(); i++) {
				    if (vt.size() == 1) {
					cavity = (String) vt.get(i);
				    } else {
					if ((vt.size() - 1) == i) {
					    cavity = cavity + (String) vt.get(i);
					} else {
					    cavity = cavity + (String) vt.get(i) + "+";
					}
				    }
				}

			    } catch (Exception e) {
				Kogger.debug(ProjectHelper.class, "cavity = " + cavity);
			    }
			} else {
			    StringTokenizer st = new StringTokenizer(cavity, "*");
			    intCavity = 1;
			    while (st.hasMoreElements()) {
				String value = st.nextToken();
				int value_i = 0;
				try {
				    value_i = Integer.parseInt(value);
				} catch (Exception e) {
				    Kogger.debug(ProjectHelper.class, "value = " + value);
				}
				intCavity *= value_i;
			    }
			}
		    }

		    // 기계설비 정보
		    MoldMachine machine = null;
		    String machineTon = ""; // 설비Ton(형체력)
		    if (moldProject.getMoldMachine() != null) {
			machine = moldProject.getMoldMachine();
			machineTon = machine.getTon().getName();
		    }

		    String maker = ""; // 원재료Maker
		    String type = ""; // 원재료Type
		    String grade = "";
		    String mType = "";
		    MoldMaterial material = null;
		    if (itemInfo.getMaterial() != null) {
			material = itemInfo.getMaterial();
			mType = material.getMaterial();
			maker = material.getMaterialMaker().getName();
			type = material.getMaterialType().getName();
			grade = material.getGrade();
		    }
		    String grade2 = ""; // 원재료Grade
		    if (mType.equals("비철")) {
			grade2 = itemInfo.getThickness() + "t" + "*" + itemInfo.getWidth() + "w";
			grade = grade + "/" + grade2;
		    }

		    String property = "";// 원재료특성
		    if (itemInfo.getProperty() != null) {
			property = itemInfo.getProperty().getName();
		    }
		    String shrinkage = ""; // 원재료특성(1)
		    if (moldProject.getShrinkage() != null && moldProject.getShrinkage().length() > 0) {
			shrinkage = moldProject.getShrinkage();
			if ("Mold".equals(itemInfo.getItemType())) {
			    shrinkage += " %";
			} else {
			    shrinkage += " mm";
			}
		    }

		    String dr1 = ""; // DR1실적
		    E3PSTask task = MoldProjectHelper.getTask(productProject, "DR1");
		    if (task != null) {
			E3PSTaskData ed = new E3PSTaskData(task);
			if (ed.taskExecEndDate != null) {
			    dr1 = DateUtil.getDateString(ed.taskExecEndDate, "D");
			}
		    }

		    // 제품 -> 금형 프로젝트의 제품도출도의 내용 가져오도록 수정...
		    E3PSTask task1 = MoldProjectHelper.getTask(moldProject, "제품도출도");
		    String pdwgPlan = ""; // 제품도계획
		    String pdwgResult = ""; // 제품도실적

		    if (task1 != null) {
			E3PSTaskData ed1 = new E3PSTaskData(task1);
			if (ed1.taskPlanEndDate != null) {
			    pdwgPlan = DateUtil.getDateString(ed1.taskPlanEndDate, "D");
			}
			if (ed1.taskExecEndDate != null) {
			    pdwgResult = DateUtil.getDateString(ed1.taskExecEndDate, "D");
			}
		    }

		    String moldDwgResult = "";
		    E3PSTask taskadd1 = null;
		    if (inoutName1.equals("사내")) {
			taskadd1 = MoldProjectHelper.getTask(moldProject, "금형설계"); // Task 이름..

			if (taskadd1 != null) {
			    Kogger.debug(ProjectHelper.class, "태스크이름?? " + taskadd1.getTaskName());
			    ProjectOutput output = ProjectOutputHelper.manager.getLikeTaskOutput(taskadd1, "금형설계도"); // 산출물 이름이 들어가야함..
				                                                                                     // 06.금혈설계도
			    if (output != null) {
				ProjectOutputData outputdata = new ProjectOutputData(output);
				if (outputdata.document != null) {
				    moldDwgResult = DateUtil.getDateString(outputdata.document.getPersistInfo().getCreateStamp(), "d");
				}
			    }
			}

		    } else if (inoutName1.equals("외주")) {
			taskadd1 = MoldProjectHelper.getTask(moldProject, "외주금형제작");
			if (taskadd1 != null) {
			    ProjectOutput output = ProjectOutputHelper.manager.getLikeTaskOutput(taskadd1, "금형제작시방서");
			    Kogger.debug(ProjectHelper.class, "외주 산출물 == " + output);
			    if (output != null) {
				ProjectOutputData outputdata = new ProjectOutputData(output);
				if (outputdata.document != null) {
				    moldDwgResult = DateUtil.getDateString(outputdata.document.getPersistInfo().getCreateStamp(), "d");
				}
			    }
			}

			/*
		         * taskadd1 = MoldProjectHelper.getTask(moldProject, "외주금형제작"); if(taskadd1 != null){ ProjectOutput output =
		         * ProjectOutputHelper.manager.getLikeTaskOutput(task, "금형설계"); if(output != null){ RevisionControlled doc =
		         * ProjectOutputHelper.manager.getDocMasterForOutput(output); if(doc != null){ moldDwgResult =
		         * DateUtil.getDateString(doc.getPersistInfo().getCreateStamp(),"d"); } } }
		         */
		    }

		    E3PSTask task2 = MoldProjectHelper.getTask(moldProject, "금형제작");
		    String moldMakePlan = ""; // 금형제작계획
		    String moldMakeResult = ""; // 금형제작실적
		    if (task2 != null) {
			E3PSTaskData ed2 = new E3PSTaskData(task2);
			if (ed2.taskPlanEndDate != null) {
			    moldMakePlan = DateUtil.getDateString(ed2.taskPlanEndDate, "D");
			}
			if (ed2.taskExecEndDate != null) {
			    moldMakeResult = DateUtil.getDateString(ed2.taskExecEndDate, "D");
			}
		    }
		    E3PSTask task3 = MoldProjectHelper.getTask(moldProject, "제품검토협의[개발품_초도]");
		    String chodoPlan = ""; // 초도검토회의계획
		    String chodoResult = ""; // 초도검토회의실적
		    if (task3 != null) {
			E3PSTaskData ed3 = new E3PSTaskData(task3);
			if (ed3.taskPlanEndDate != null) {
			    chodoPlan = DateUtil.getDateString(ed3.taskPlanEndDate, "D");
			}
			if (ed3.taskExecEndDate != null) {
			    chodoResult = DateUtil.getDateString(ed3.taskExecEndDate, "D");
			}
		    }

		    String debuging1 = ""; // 1 차 디버깅
		    String debuging2 = ""; // 2 차 디버깅
		    String debuging3 = ""; // 3 차 디버깅
		    String debuging4 = ""; // 4 차 디버깅
		    String debuging5 = ""; // 5 차 디버깅
		    String debuging6 = ""; // 6 차 디버깅
		    String debuging7 = ""; // 7 차 디버깅
		    String debuging8 = ""; // 8 차 디버깅
		    String debuging9 = ""; // 9 차 디버깅
		    String debuging10 = ""; // 10 차 디버깅

		    E3PSTask debugingTask = MoldProjectHelper.getTask(moldProject, "1 차 디버깅");
		    E3PSTaskData debugingTaskData = null;
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging1 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "2 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging2 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "3 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging3 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "4 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging4 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "5 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging5 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "6 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging6 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "7 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging7 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "8 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging8 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "9 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging9 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    debugingTask = MoldProjectHelper.getTask(moldProject, "10 차 디버깅");
		    if (debugingTask != null) {
			debugingTaskData = new E3PSTaskData(debugingTask);
			if (debugingTaskData.taskExecEndDate != null && debugingTaskData.taskExecStartDate != null) {
			    debuging10 = "" + DateUtil.getDuration(debugingTaskData.taskExecStartDate, debugingTaskData.taskExecEndDate);
			}
		    }
		    E3PSTask sTask = MoldProjectHelper.getTask(moldProject, "제품합격");
		    String pSucessPlan = "";
		    String pSucessResult = "";
		    if (sTask != null) {
			E3PSTaskData sTaskData = new E3PSTaskData(sTask);
			if (sTaskData.taskPlanEndDate != null) {
			    pSucessPlan = DateUtil.getDateString(sTaskData.taskPlanEndDate, "D");
			}
			if (sTaskData.taskExecEndDate != null) {
			    pSucessResult = DateUtil.getDateString(sTaskData.taskExecEndDate, "D");
			}
		    }
		    E3PSTask mTask = MoldProjectHelper.getTask(moldProject, "금형양산이관");
		    String moldMgrPlan = "";
		    String moldMgrResult = "";
		    if (mTask != null) {
			E3PSTaskData mTaskData = new E3PSTaskData(mTask);
			if (mTaskData.taskPlanEndDate != null) {
			    moldMgrPlan = DateUtil.getDateString(mTaskData.taskPlanEndDate, "D");
			}
			if (mTaskData.taskExecEndDate != null) {
			    moldMgrResult = DateUtil.getDateString(mTaskData.taskExecEndDate, "D");
			}
		    }
		    String planStartDatePrint = DateUtil.getDateString(data.pjtPlanStartDate, "d");// 계획 시작일
		    String planEndDatePrint = DateUtil.getDateString(data.pjtPlanEndDate, "d"); // 계획 종료일
		    String pjtExecStartDate = DateUtil.getDateString(data.pjtExecStartDate, "d");// 실제 시작일
		    String pjtExecEndDate = DateUtil.getDateString(data.pjtExecEndDate, "d");// 실제 종료일
		    String completion = String.valueOf(data.pjtCompletion);// 진행율
		    String devUserDeptPrint = productData.department;// 개발담당부서
		    String productPmPrint = productData.pjtPmName;// 개발 PM

		    HashMap legacyMap = new HashMap();
		    ProjectMemberLink roleLink = null;
		    QueryResult result = ProjectUserHelper.manager.getQueryForTeamUsers(moldProject, "ROLEMEMBER");
		    while (result.hasMoreElements()) {
			roleLink = (ProjectMemberLink) result.nextElement();
			legacyMap.put(roleLink.getPjtRole(), roleLink);
		    }

		    String roleName1 = ""; // 제품개발
		    String moldPmPrint = ""; // 금형개발
		    WTUser user = null;
		    if (ProjectUserHelper.manager.getPM(moldProject) != null) {
			user = ProjectUserHelper.manager.getPM(moldProject);
			PeopleData pData = new PeopleData(user);
			moldPmPrint = pData.name;
		    }
		    String roleName2 = ""; // 금형설계
		    String roleName3 = ""; // 금형구매
		    Vector vecTeamStd = null;
		    Role role = null;
		    String roleName_en = null;
		    String roleName_ko = null;
		    PeopleData pdata = null;
		    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
		    if (tempTeam != null) {
			vecTeamStd = tempTeam.getRoles();
		    }
		    for (int i = vecTeamStd.size() - 1; i > -1; i--) {
			role = (Role) vecTeamStd.get(i);
			roleName_en = role.toString();
			roleName_ko = role.getDisplay(Locale.KOREA);
			if (legacyMap.get(roleName_en) != null) {
			    roleLink = (ProjectMemberLink) legacyMap.get(roleName_en);
			    pdata = new PeopleData(roleLink.getMember());
			    if ("제품개발".equals(roleName_ko)) {
				roleName1 = pdata.name;
			    }
			    if ("금형설계".equals(roleName_ko)) {
				roleName2 = pdata.name;
			    }
			    if ("금형구매".equals(roleName_ko)) {
				roleName3 = pdata.name;
			    }
			}
		    }
		    java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		    CostInfo costInfo = itemInfo.getCostInfo();
		    Vector debugDatas = MoldProjectHelper.getDebugingTasks(moldProject);

		    String orderNumber = null; // 투자오더
		    String targetCost = "";
		    int initPrice = 0;
		    int totalPrice = 0;
		    String initPlanPrice = "-";
		    String addPlanPrice = "-";
		    String totalPlanPrice = "-";// 금형비예산

		    String initmoldTotalPrice = "-"; // 제작비
		    String debugingTotalPrice = "-"; // 디버깅비
		    String moldTotalPrice = "-"; // 금형비용실적

		    // ERP 비용 가져오기..??

		    if (costInfo != null) {
			orderNumber = costInfo.getOrderInvest();

		    }
		    boolean isTotal = false;
		    // orderNumber = "402937";
		    Vector priceV = new Vector();
		    if (orderNumber != null && orderNumber.length() > 0 && "sap".equals(sap)) {

			Hashtable hash = ProductPrice.getPrice(orderNumber);
			Long longVal = (Long) hash.get(ProductPrice.INITPRICE);
			if (longVal != null) {
			    initPlanPrice = nf.format(longVal.longValue());
			}
			longVal = (Long) hash.get(ProductPrice.ADDPRICE);
			if (longVal != null) {
			    addPlanPrice = nf.format(longVal.longValue());
			}

			longVal = (Long) hash.get(ProductPrice.TOTALPRICE);
			if (longVal != null) {
			    totalPlanPrice = nf.format(longVal.longValue());
			}

			priceV = SAPMoldPrice.getMoldProjectPrice(orderNumber, moldProject, debugDatas);
			int initMold = 0;
			Integer integer = 0;
			if (priceV.size() > 0) {

			    Hashtable hhh = (Hashtable) priceV.get(0);
			    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

			    if (integer != null) {
				initMold = integer.intValue();

			    }
			}

			initmoldTotalPrice = nf.format(initMold);

			int debugingtotal = 0;
			for (int i = 1; i < priceV.size(); i++) {
			    Hashtable hhh = (Hashtable) priceV.get(i);
			    integer = (Integer) hhh.get(SAPMoldPrice.TOTAL);

			    if (integer != null) {
				debugingtotal += integer.intValue();
			    }
			}

			debugingTotalPrice = nf.format(debugingtotal);
			moldTotalPrice = nf.format(initMold + debugingtotal);

			if ((initMold + debugingtotal) > ((Long) hash.get(ProductPrice.TOTALPRICE)).longValue()) {
			    isTotal = true;
			}
		    } else {
			orderNumber = "-";
		    }

		    sheet.addCell(new Label(columnIndex++, row, pjtNoPrint));
		    sheet.addCell(new Label(columnIndex++, row, dieNoPrint));
		    sheet.addCell(new Label(columnIndex++, row, partNoPrint));
		    sheet.addCell(new Label(columnIndex++, row, partNamePrint));
		    sheet.addCell(new Label(columnIndex++, row, customer));
		    sheet.addCell(new Label(columnIndex++, row, sop));
		    sheet.addCell(new Label(columnIndex++, row, carTypePrint));
		    sheet.addCell(new Label(columnIndex++, row, developentType));
		    sheet.addCell(new Label(columnIndex++, row, itemTypePrint));
		    sheet.addCell(new Label(columnIndex++, row, productTypePrint));
		    sheet.addCell(new Label(columnIndex++, row, moldTypePrint));
		    sheet.addCell(new Label(columnIndex++, row, moldRankPrint));
		    sheet.addCell(new Label(columnIndex++, row, statePrint));
		    sheet.addCell(new Label(columnIndex++, row, progressMold));
		    sheet.addCell(new Label(columnIndex++, row, making));
		    sheet.addCell(new Label(columnIndex++, row, outSourcing));
		    sheet.addCell(new Label(columnIndex++, row, inoutName1));
		    sheet.addCell(new Label(columnIndex++, row, inoutName2));
		    sheet.addCell(new Label(columnIndex++, row, cavity));
		    sheet.addCell(new Label(columnIndex++, row, machineTon));
		    sheet.addCell(new Label(columnIndex++, row, maker));
		    sheet.addCell(new Label(columnIndex++, row, type));
		    sheet.addCell(new Label(columnIndex++, row, grade));
		    sheet.addCell(new Label(columnIndex++, row, property));
		    sheet.addCell(new Label(columnIndex++, row, shrinkage));
		    sheet.addCell(new Label(columnIndex++, row, dr1));
		    sheet.addCell(new Label(columnIndex++, row, pdwgPlan));
		    sheet.addCell(new Label(columnIndex++, row, pdwgResult));
		    sheet.addCell(new Label(columnIndex++, row, moldDwgResult)); // 금형설계도실적 추가 // 2011.04.28 by TaeHun
		    sheet.addCell(new Label(columnIndex++, row, moldMakePlan));
		    sheet.addCell(new Label(columnIndex++, row, moldMakeResult));
		    sheet.addCell(new Label(columnIndex++, row, chodoPlan));
		    sheet.addCell(new Label(columnIndex++, row, chodoResult));
		    sheet.addCell(new Label(columnIndex++, row, debuging1));
		    sheet.addCell(new Label(columnIndex++, row, debuging2));
		    sheet.addCell(new Label(columnIndex++, row, debuging3));
		    sheet.addCell(new Label(columnIndex++, row, debuging4));
		    sheet.addCell(new Label(columnIndex++, row, debuging5));
		    sheet.addCell(new Label(columnIndex++, row, debuging6));
		    sheet.addCell(new Label(columnIndex++, row, debuging7));
		    sheet.addCell(new Label(columnIndex++, row, debuging8));
		    sheet.addCell(new Label(columnIndex++, row, debuging9));
		    sheet.addCell(new Label(columnIndex++, row, debuging10));
		    sheet.addCell(new Label(columnIndex++, row, pSucessPlan));
		    sheet.addCell(new Label(columnIndex++, row, pSucessResult));
		    sheet.addCell(new Label(columnIndex++, row, moldMgrPlan));
		    sheet.addCell(new Label(columnIndex++, row, moldMgrResult));
		    sheet.addCell(new Label(columnIndex++, row, planStartDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, planEndDatePrint));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecStartDate));
		    sheet.addCell(new Label(columnIndex++, row, pjtExecEndDate));
		    sheet.addCell(new Label(columnIndex++, row, completion));
		    sheet.addCell(new Label(columnIndex++, row, devUserDeptPrint));
		    sheet.addCell(new Label(columnIndex++, row, productPmPrint));
		    sheet.addCell(new Label(columnIndex++, row, roleName1));
		    sheet.addCell(new Label(columnIndex++, row, moldPmPrint));
		    sheet.addCell(new Label(columnIndex++, row, roleName2));
		    sheet.addCell(new Label(columnIndex++, row, roleName3));
		    sheet.addCell(new Label(columnIndex++, row, orderNumber));
		    sheet.addCell(new Label(columnIndex++, row, totalPlanPrice));
		    sheet.addCell(new Label(columnIndex++, row, initmoldTotalPrice));
		    sheet.addCell(new Label(columnIndex++, row, debugingTotalPrice));
		    sheet.addCell(new Label(columnIndex++, row, moldTotalPrice));
		}
	    }

	    workbook.write();
	    workbook.close();
	} catch (Exception e) {

	} finally {
	    Kogger.debug(ProjectHelper.class, " excelDown !!!");
	}

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), "");

	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    fin.read(b);
	    out.write(b, 0, ifilesize);

	    out.flush();
	    out.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(ProjectHelper.class, wte);
	}

    }

    private static WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
	WritableCellFormat format = null;
	try {
	    format = new WritableCellFormat();
	    if (color != null)
		format.setBackground(color);

	    format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

	    if (alignment != null)
		format.setAlignment(alignment);
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	}
	return format;
    }

    public static int getMoldPartManagerCount(MoldProject project) throws Exception {

	int result = 0;
	QuerySpec qs = new QuerySpec();

	ClassInfo classinfo = WTIntrospector.getClassInfo(MoldPartManager.class);

	String oidC = DatabaseInfoUtilities.getValidColumnName(classinfo, "thePersistInfo.theObjectIdentifier.id");

	int index1 = qs.appendFrom(new E3PSClassTableExpression(MoldPartManager.class));
	int index3 = qs.appendFrom(new E3PSClassTableExpression(MoldProject.class));

	SearchCondition sc = new SearchCondition(new ClassAttribute(MoldPartManager.class, "projectReference.key.id"), "=",
	        new ClassAttribute(MoldProject.class, "thePersistInfo.theObjectIdentifier.id"));

	sc.setOuterJoin(0);

	qs.appendWhere(sc, new int[] { index1, index3 });

	long masterId = project.getMaster().getPersistInfo().getObjectIdentifier().getId();

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(MoldProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId),
	        new int[] { index3 });

	KeywordExpression ke = new KeywordExpression("count(" + qs.getFromClause().getAliasAt(index1) + "." + oidC + ")");
	ke.setColumnAlias("managerC");
	qs.appendSelect(ke, new int[] { 0 }, false);

	QueryResult rs = PersistenceHelper.manager.find(qs);
	Vector v = new Vector();
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    result = number.intValue();
	}

	return result;
    }

    public int getIssueCount(E3PSProject project) throws Exception {

	/*
	 * if (!wt.method.RemoteMethodServer.ServerFlag) { Class argTypes[] = new Class[]{TemplateTask.class, TemplateProject.class}; Object
	 * args[] = new Object[]{parent, project}; try { Object obj =RemoteMethodServer.getDefault().invoke("getMaxSeq",
	 * ProjectTaskHelper.class.getName() , null, argTypes, args); return ((Integer)obj).intValue(); } catch (RemoteException e) {
	 * Kogger.error(getClass(), e); throw new WTException(e); } catch (InvocationTargetException e) { Kogger.error(getClass(), e); throw
	 * new WTException(e); } }
	 */

	int result = 0;
	Class issueClass = ProjectIssue.class;

	ClassInfo classinfo = WTIntrospector.getClassInfo(issueClass);

	String oidC = DatabaseInfoUtilities.getValidColumnName(classinfo, "thePersistInfo.theObjectIdentifier.id");

	QuerySpec spec = new QuerySpec();
	spec.setAdvancedQueryEnabled(true);
	spec.setDescendantQuery(false);
	spec.appendFrom(new E3PSClassTableExpression(issueClass));
	KeywordExpression ke = new KeywordExpression("count(" + oidC + ")");
	ke.setColumnAlias("issueId");
	spec.appendSelect(ke, new int[] { 0 }, false);

	long projectId = project.getPersistInfo().getObjectIdentifier().getId();

	spec.appendWhere(new SearchCondition(issueClass, ProjectIssue.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(issueClass, ProjectIssue.TASK_REFERENCE + ".key.id", "=", 0L), new int[] { 0 });

	spec.appendAnd();
	spec.appendWhere(new SearchCondition(issueClass, ProjectIssue.IS_DONE, SearchCondition.IS_FALSE, false), new int[] { 0 });

	// Kogger.debug(getClass(), "spec = " + spec);
	QueryResult rrrr = PersistenceHelper.manager.find(spec);

	if (rrrr.hasMoreElements()) {
	    Object o[] = (Object[]) rrrr.nextElement();
	    BigDecimal number = (BigDecimal) o[0];
	    result = number.intValue();

	}

	// Kogger.debug(getClass(), "result = " + result);

	return result;
    }

    public static E3PSProject wbsCopyProjectTask(TemplateProject tproject, E3PSProject eproject) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, E3PSProject.class };
	    Object args[] = new Object[] { tproject, eproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("wbsCopyProjectTask", ProjectHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String msg = ProjectHelper.manager.deleteAllTask(eproject);
	    if ("S".equals(msg)) {
		ProjectHelper.manager.copyProjectInfo(eproject, tproject);
		TaskHelper.manager.copyProjectFromTemplate(eproject, tproject);
		ProjectUserHelper.settingRoleTaskMember(eproject);
		// ProductHelper.syncProjectCostIF(eproject);
		eproject.setTemplateCode(tproject.getPjtNo());
		eproject = (E3PSProject) PersistenceHelper.manager.save(eproject);
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return eproject;
    }

    public static E3PSProject wbsCopyProjectTaskByProject(TemplateProject tproject, E3PSProject eproject, WTUser pmUser) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, E3PSProject.class };
	    Object args[] = new Object[] { tproject, eproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("wbsCopyProjectTask", ProjectHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String msg = ProjectHelper.manager.deleteAllTask(eproject);
	    if ("S".equals(msg)) {

		// TaskHelper.manager.copyProjectFromTemplate(eproject, tproject);
		// ProjectUserHelper.settingRoleTaskMember(eproject);
		// ProductHelper.syncProjectCostIF(eproject);

		QueryResult rs = ProjectUserHelper.manager.getPMProjectMemberLink(eproject);
		WTUser toPm = ProjectUserHelper.manager.getPM(tproject);

		if (rs.hasMoreElements()) {

		    ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();
		    link.setMember(toPm);
		    PersistenceHelper.manager.save(link);
		}

		ProjectUserHelper.manager.copyProjectUserInfoNotPm(eproject, tproject);

		TaskHelper.manager.copyProjectFromProject((E3PSProject) eproject, (E3PSProject) tproject);
		// ProjectUserHelper.settingRoleTaskMember(eproject);
		// ProjectHelper.manager.copyProjectRef((E3PSProject)eproject, (E3PSProject)tproject);

		rs.reset();
		rs = ProjectUserHelper.manager.getPMProjectMemberLink(eproject);

		if (rs.hasMoreElements()) {

		    ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();

		    link.setMember(pmUser);
		    PersistenceHelper.manager.save(link);
		}
		//
		// eproject.setTemplateCode(tproject.getPjtNo());
		// eproject = (E3PSProject) PersistenceHelper.manager.save(eproject);

		ExtendScheduleData fromSchedule = (ExtendScheduleData) tproject.getPjtSchedule().getObject();
		ExtendScheduleData toSchedule = (ExtendScheduleData) eproject.getPjtSchedule().getObject();
		toSchedule.setDuration(fromSchedule.getDuration());
		toSchedule.setPlanStartDate(fromSchedule.getPlanStartDate());
		toSchedule.setPlanEndDate(fromSchedule.getPlanEndDate());
		toSchedule.setPlanWork(fromSchedule.getPlanWork());
		toSchedule.setPlanManHour(fromSchedule.getPlanManHour());

		PersistenceHelper.manager.save(toSchedule);
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return eproject;
    }

    public static E3PSProject wbsCopyProjectTaskNew(TemplateProject tproject, E3PSProject eproject, String[] category) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, E3PSProject.class };
	    Object args[] = new Object[] { tproject, eproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("wbsCopyProjectTask", ProjectHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String msg = ProjectHelper.manager.deleteAllTask(eproject);
	    if ("S".equals(msg)) {
		ProjectHelper.manager.copyProjectInfo(eproject, tproject);
		TaskHelper.manager.copyProjectFromTemplateNew(eproject, tproject, category);
		ProjectUserHelper.settingRoleTaskMember(eproject);
		// ProductHelper.syncProjectCostIF(eproject);
		eproject.setTemplateCode(tproject.getPjtNo());
		eproject = (E3PSProject) PersistenceHelper.manager.save(eproject);
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return eproject;
    }

    public static E3PSProject wbsCopyProjectTaskNew(TemplateProject tproject, E3PSProject eproject, String[] category, String productType)
	    throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, E3PSProject.class };
	    Object args[] = new Object[] { tproject, eproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("wbsCopyProjectTask", ProjectHelper.class.getName(), null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (E3PSProject) obj;
	}

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String msg = ProjectHelper.manager.deleteAllTask(eproject);
	    if ("S".equals(msg)) {
		ProjectHelper.manager.copyProjectInfo(eproject, tproject);
		TaskHelper.manager.copyProjectFromTemplateNew(eproject, tproject, category, productType);
		ProjectUserHelper.settingRoleTaskMember(eproject);
		ProductHelper.syncProjectCostIF(eproject);
		eproject.setTemplateCode(tproject.getPjtNo());
		eproject = (E3PSProject) PersistenceHelper.manager.save(eproject);
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return eproject;
    }

    public static String deleteAllTask(E3PSProject eproject) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class };
	    Object args[] = new Object[] { eproject };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault()
		        .invoke("deleteAllTask", ProjectHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectHelper.class, e);
		throw new WTException(e);
	    }
	    return (String) obj;
	}

	Transaction trx = new Transaction();
	E3PSTask task = null;
	QueryResult qr = null;
	try {
	    trx.start();
	    qr = ProjectTaskHelper.manager.getChildList((TemplateProject) eproject);
	    while (qr.hasMoreElements()) {
		task = (E3PSTask) qr.nextElement();
		// Kogger.debug(getClass(), "deleteAllTask==>" + task.getTaskName());
		PersistenceHelper.manager.delete(task);
	    }
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(ProjectHelper.class, e);
	    trx.rollback();
	    return "F";
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return "S";
    }

    public static QueryResult getDevRequestProject(KETDevelopmentRequest ketDr) throws Exception {
	long idxLong = 0;
	if (ketDr != null) {
	    idxLong = CommonUtil.getOIDLongValue(ketDr);
	}
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	// int idx = qs.appendClassList(ReviewProject.class, true);
	int idx = qs.appendClassList(ProductProject.class, true);
	qs.appendWhere(new SearchCondition(ProductProject.class, ProductProject.DEV_REQUEST_NO, "=", ketDr.getNumber()), new int[] { idx });
	// Kogger.debug(getClass(), "qr >> " + qs);
	qr = PersistenceHelper.manager.find(qs);
	return qr;
    }

    public static QueryResult getReviewRequestProject(KETDevelopmentRequest ketDr) throws Exception {
	long idxLong = 0;
	if (ketDr != null) {
	    idxLong = CommonUtil.getOIDLongValue(ketDr);
	}
	QueryResult qr = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.appendClassList(ReviewProject.class, true);
	qs.appendOpenParen();
	qs.appendWhere(new SearchCondition(ReviewProject.class, "attr2", "=", ketDr.getNumber()), new int[] { idx });
	qs.appendOr();
	qs.appendWhere(new SearchCondition(ReviewProject.class, "devRequestReference.key.id", "=", CommonUtil.getOIDLongValue(ketDr)),
	        new int[] { idx });
	qs.appendCloseParen();
	// Kogger.debug(getClass(), "qr >> " + qs);
	qr = PersistenceHelper.manager.find(qs);
	return qr;
    }

    public static QueryResult getCancelProject(E3PSProject project) throws Exception {
	long idLong = CommonUtil.getOIDLongValue(project.getMaster());
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(ProjectCancelLink.class, true);
	qs.appendWhere(new SearchCondition(ProjectCancelLink.class, "roleBObjectRef.key.id", "=", idLong), new int[] { idx });
	SearchUtil.setOrderBy(qs, ProjectCancelLink.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", true);
	QueryResult qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    public static ProjectChangeStop getStopProject(E3PSProject project) throws Exception {

	ProjectChangeStop ps = null;

	long idLong = CommonUtil.getOIDLongValue(project.getMaster());

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(ProjectChangeStop.class, true);
	qs.appendWhere(new SearchCondition(ProjectChangeStop.class, "pcsMasterReference.key.id", "=", idLong), new int[] { idx });
	SearchUtil.setOrderBy(qs, ProjectChangeStop.class, idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "createStamp", false);
	QueryResult qr = PersistenceHelper.manager.find(qs);
	if (qr != null || qr.size() != 0) {
	    Object[] obj = null;
	    while (qr.hasMoreElements()) {
		obj = (Object[]) qr.nextElement();
		ps = (ProjectChangeStop) obj[0];
	    }
	}

	return ps;
    }

    public static boolean isStopProject(ProductProject project) throws Exception {
	if (project == null) {
	    return false;
	}
	ProjectChangeStop ps = ProjectHelper.getStopProject(project);
	if (ps != null && "중지검토".equals(ps.getChangeType())) {
	    return true;
	}

	return false;
    }

    public static QueryResult getProjectByReviewPjt(String reviewProjectOid) throws Exception {

	QuerySpec spec = null;
	E3PSProject project = null;

	spec = new QuerySpec();
	Class ps = E3PSProject.class;
	Class devReq = KETDevelopmentRequest.class;
	int ps_idx = spec.addClassList(ps, true);
	int devReq_idx = spec.addClassList(devReq, false);

	SearchCondition sc = null;
	sc = new SearchCondition(new ClassAttribute(ps, "devRequestReference.key.id"), "=", new ClassAttribute(devReq,
	        "thePersistInfo.theObjectIdentifier.id"));
	sc.setFromIndicies(new int[] { ps_idx, devReq_idx }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { ps_idx, devReq_idx });

	spec.appendAnd();
	sc = new SearchCondition(ps, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true"));
	spec.appendWhere(sc, new int[] { ps_idx });

	spec.appendAnd();
	sc = new SearchCondition(ps, "checkOutState", SearchCondition.NOT_EQUAL, "c/o");
	spec.appendWhere(sc, new int[] { ps_idx });

	spec.appendAnd();
	sc = new SearchCondition(devReq, "projectOID", SearchCondition.EQUAL, reviewProjectOid);
	spec.appendWhere(sc, new int[] { devReq_idx });

	QueryResult rt = PersistenceHelper.manager.find(spec);

	return rt;
    }

    public List<Map<String, Object>> makeProjectStructure(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String pjtNo = (String) reqMap.get("pjtNo");

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT UNIQUE                                                                                                            \n");
	    sql.append("        P.PJTNO AS PARENT_NO                                                                                              \n");
	    sql.append("       ,P.PJTNAME AS PARENT_NAME                                                                                          \n");
	    sql.append("       ,C.PJTNO AS CHILD_NO                                                                                               \n");
	    sql.append("       ,C.PJTNAME AS CHILD_NAME                                                                                           \n");
	    sql.append("   FROM (SELECT PM.PJTNO                                                                                                  \n");
	    sql.append("               ,PM.PJTNAME                                                                                                \n");
	    sql.append("               ,MII.PARTNO                                                                                                \n");
	    sql.append("               ,REPLACE(MII.PARTNO,'H','') AS PROD_PARTNO                                                                 \n");
	    sql.append("           FROM PRODUCTPROJECT PJ                                                                                         \n");
	    sql.append("               ,MOLDITEMINFO  MII                                                                                         \n");
	    sql.append("               ,E3PSPROJECTMASTER PM                                                                                      \n");
	    sql.append("          WHERE MII.IDA3A3 = PJ.IDA2A2                                                                                    \n");
	    sql.append("            AND PM.IDA2A2  = PJ.IDA3B8                                                                                    \n");
	    sql.append("            AND PJ.PJTTYPE = 2                                                                                            \n");
	    sql.append("            AND PJ.LASTEST = 1                                                                                            \n");
	    sql.append("            AND PJ.CHECKOUTSTATE <> 'c/o'                                                                                 \n");
	    sql.append("            AND (PJ.IDA3F9  = 19875 OR (PJ.IDA3F9 = 1077705899 AND PJ.IDA3N9 = 1077498097 )) -- Pilot, 양산 프로젝트 만         \n");
	    sql.append("            AND MII.SHRINKAGE = '신규'                                                                                      \n");
	    sql.append("            AND NVL(MII.ITEMTYPE,'Etc') NOT IN ('Mold', 'Press') -- Mold,Press제외                                          \n");
	    sql.append("            AND MII.PARTNO NOT LIKE '68%' -- 상품제외                                                                         \n");
	    sql.append("            AND TO_CHAR(PM.CREATESTAMPA2, 'YYYY') >= '2017' -- 17년이후 등록건                                                 \n");
	    sql.append("         ) P                                                                                                              \n");
	    sql.append("        ,(SELECT PM.PJTNO                                                                                                 \n");
	    sql.append("                ,PM.PJTNAME                                                                                               \n");
	    sql.append("                ,PD.PNUM AS PARTNO                                                                                        \n");
	    sql.append("           FROM PRODUCTPROJECT PJ                                                                                         \n");
	    sql.append("               ,PRODUCTINFO PD                                                                                            \n");
	    sql.append("               ,E3PSPROJECTMASTER PM                                                                                      \n");
	    sql.append("          WHERE PJ.IDA2A2 = PD.IDA3A3                                                                                     \n");
	    sql.append("            AND PM.IDA2A2  = PJ.IDA3B8                                                                                    \n");
	    sql.append("            AND PJ.PJTTYPE = 2                                                                                            \n");
	    sql.append("            AND PJ.LASTEST = 1                                                                                            \n");
	    sql.append("            AND PJ.CHECKOUTSTATE <> 'c/o'                                                                                 \n");
	    sql.append("            AND (PJ.IDA3F9  = 19875 OR (PJ.IDA3F9 = 1077705899 AND PJ.IDA3N9 = 1077498097 )) -- Pilot, 양산 프로젝트 만         \n");
	    sql.append("            AND TO_CHAR(PM.CREATESTAMPA2, 'YYYY') >= '2017' -- 17년이후 등록건                                                 \n");
	    sql.append("         ) C                                                                                                              \n");
	    sql.append(" WHERE P.PROD_PARTNO = C.PARTNO                                                                                           \n");
	    sql.append("   AND P.PJTNO <> C.PJTNO                                                                                                 \n");
	    sql.append("   AND (P.PJTNO = '" + pjtNo + "' OR C.PJTNO = '" + pjtNo
		    + "' )  --< 해당 프로젝트 No로 조회                                                 \n");
	    sql.append(" ORDER BY P.PJTNO , C.PJTNO                                                                                               \n");

	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {

		String parentNo = rs.getString("PARENT_NO");
		String parentName = rs.getString("PARENT_NAME");
		String childNo = rs.getString("CHILD_NO");
		String childName = rs.getString("CHILD_NAME");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("parentNo", parentNo);
		map.put("parentName", parentName);
		map.put("childNo", childNo);
		map.put("childName", childName);

		list.add(map);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
	return list;
    }

    // 제품프로젝트 취소 이벤트 발생시 해당 메서드를 타게 된다. 관련 금형 프로젝트를 취소 처리한다 (진행 중 인것만)
    public void setCancelRefMoldProjectByProduct(E3PSProject project) {
	try {

	    if (project instanceof ProductProject) {
		QueryResult rs3 = PersistenceHelper.manager.navigate(project, ProductProjectMoldInfoLink.MOLD_INFO_ROLE,
		        ProductProjectMoldInfoLink.class);
		while (rs3.hasMoreElements()) {
		    MoldItemInfo moldItemInfo = (MoldItemInfo) rs3.nextElement();
		    String dieNo = moldItemInfo.getDieNo();

		    E3PSProject moldProject = this.getProject(dieNo);
		    if (moldProject != null && "PROGRESS".equals(moldProject.getState().toString())) {
			moldProject = (E3PSProject) LifeCycleHelper.service.setLifeCycleState(moldProject,
			        wt.lifecycle.State.toState("WITHDRAWN"), true);
			ProjectHelper.manager.sendMailStopCancelProject(moldProject);
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public static void main(String args[]) throws Exception {
	RemoteMethodServer server = RemoteMethodServer.getDefault();
	server.setUserName("wcadmin");
	server.setPassword("wcadmin");
	// ProjectIssue issue = (ProjectIssue)CommonUtil.getObject("e3ps.project.ProjectIssue:390016");
	// boolean aaa = projectSendMail(issue, "issue");

	HashMap map = new HashMap();
	WTGroup group = getWTGroupObj(e3ps.project.beans.WTGroupNameData.PMO);
	Kogger.debug(ProjectHelper.class, group.getName());
	map = getWTGroupMembershipLink(group);

	// FileOutputStream out = new FileOutputStream("c://kkkk.xls");
	// E3PSProject project = (E3PSProject)CommonUtil.getObject("e3ps.project.ReviewProject:154514");
	// ProjectHelper.manager.changeProjectState(project, "STOPED");
    }
}
