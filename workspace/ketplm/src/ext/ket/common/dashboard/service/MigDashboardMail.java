package ext.ket.common.dashboard.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.util.KETObjectUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
//import e3ps.project.TaskDelayReason;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskHelper;
import ext.ket.common.dashboard.entity.KETDashBoardMailSetting;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

public class MigDashboardMail implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static MigDashboardMail manager = new MigDashboardMail();

    // windchill ext.ket.part.migration.claz.MigNumberingMinNo Z:\90.개인폴더\이응진\부품사양관리\업로드포멧\업로드작업\제품분류_V1.1_MigrationData.xlsx
    // windchill ext.ket.part.migration.claz.MigNumberingMinNo D:\MigrationData.xlsx

    public static void main(String[] args) {

	try {

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");

	    String toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigDashboardMail.class, "@start:" + toDayTime);
	    MigDashboardMail.manager.saveFromExcel(args);

	    toDayTime = "";
	    try {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());
	    } catch (Exception e) {
		Kogger.error("Exception : " + e.getMessage());
	    }

	    Kogger.debug(MigDashboardMail.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(MigDashboardMail.class, e);
	}
    }

    public void saveFromExcel(String[] args) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String[].class };
		Object aobj[] = { args };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(args);
	}
    }

    public void executeMigration(String[] args) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;

	try {

	    SessionHelper.manager.setAdministrator();

	    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	    List<KETDashBoardMailSetting> mailSetList = query.queryForListByOneClass(KETDashBoardMailSetting.class, new QueryStrategy() {
		@Override
		public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		    // TODO Auto-generated method stub

		}
	    });

	    List<WTUser> listToUser = new ArrayList<WTUser>();
	    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	    for (KETDashBoardMailSetting mailSet : mailSetList) {
		People peo = (People) mailSet.getMan();
		PeopleData User = new PeopleData(peo);
		WTUser toUser = User.wtuser;
		listToUser.add(toUser);
		KETMailHelper.service.sendFormMail("08134", "DashBoardTaskNoticeMail.html", mailSet, wtUserFrom, listToUser);
	    }

	    /*trx = new Transaction();
	    trx.start();

	    TaskDelayReason tdr = TaskDelayReason.newTaskDelayReason();

	    PeopleData User = new PeopleData(wtUserFrom);

	    tdr.setCreator(User.people);

	    String id = args[0];*/

	    /*People peo = PeopleHelper.manager.getPeople(id);

	    tdr.setImputeman(peo);
	    tdr.setProjectName("프로젝트");
	    tdr.setProjectNo("A15B041");
	    tdr.setReason("지연이야");

	    E3PSProject pjt = ProjectHelper.getProject("A15B041");

	    long pId = pjt.getPersistInfo().getObjectIdentifier().getId();

	    QuerySpec qs = new QuerySpec();
	    int idx_task = qs.addClassList(E3PSTask.class, true);
	    qs.appendWhere(new SearchCondition(E3PSTask.class, "projectReference.key.id", "=", pId), new int[] { idx_task });

	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(E3PSTask.class, E3PSTask.TASK_NAME, "=", "DR1"), new int[] { idx_task });

	    Kogger.debug(ProjectTaskHelper.class, "rs >> " + qs);
	    QueryResult rs = PersistenceHelper.manager.find(qs);
	    E3PSTask task = null;
	    if (rs.hasMoreElements()) {
		Object obj[] = (Object[]) rs.nextElement();
		task = (E3PSTask) obj[0];
	    }*/

	    /*
	     * E3PSTask Task = null; if (rs.hasMoreElements()) { Object[] o = (Object[]) rs.nextElement(); Task = (E3PSTask) o[0]; }
	     */
	    //tdr.setTask(task);

	    //PersistenceHelper.manager.save(tdr);

	    /*
	     * Kogger.debug(getClass(), "## args :" + args);
	     * 
	     * String id = args[0];
	     * 
	     * MigDashboardMail loader = new MigDashboardMail();
	     * 
	     * People peo = PeopleHelper.manager.getPeople(id);
	     * 
	     * KETDashBoardMailSetting mailset = KETDashBoardMailSetting.newKETDashBoardMailSetting();
	     * 
	     * mailset.setMan(peo); mailset.setId(peo.getId()); mailset.setName(peo.getName());
	     * 
	     * mailset = (KETDashBoardMailSetting) PersistenceHelper.manager.save(mailset);
	     * 
	     * PersistenceHelper.manager.refresh(mailset);
	     * 
	     * Department dept1 = DepartmentHelper.manager.getDepartment("11711"); Department dept2 =
	     * DepartmentHelper.manager.getDepartment("11451");
	     * 
	     * KETDashBoardMailLink link = KETDashBoardMailLink.newKETDashBoardMailLink(mailset, dept1); link = (KETDashBoardMailLink)
	     * PersistenceHelper.manager.save(link); // KETDashBoardMailLink link2 = KETDashBoardMailLink.newKETDashBoardMailLink(mailset,
	     * dept2); link2 = (KETDashBoardMailLink) PersistenceHelper.manager.save(link2);
	     * 
	     * SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
	     * 
	     * List<KETDashBoardMailLink> linkList = query.queryForListLinkByRoleA(KETDashBoardMailSetting.class,
	     * KETDashBoardMailLink.class, mailset);
	     * 
	     * for (KETDashBoardMailLink linkk : linkList) { KETDashBoardMailSetting aa = (KETDashBoardMailSetting) linkk.getMailset();
	     * People pp = (People) aa.getMan(); System.out.println("ID : " + pp.getName()); Department deptt = (Department)
	     * linkk.getDept(); System.out.println("dept : " + deptt.getCode()); // PersistenceHelper.manager.delete(linkk); }
	     */

	    // loader.load(filePath);
	    //
	    // List<Map> list = loader.getList();
	    //
	    // for( Map map : list ){
	    //
	    // String classCode = (String)map.get(MigNumberingMinNoLoader.CLASSCODE);
	    // Kogger.debug(getClass(), "### CLASSCODE:" + classCode);
	    // classCode = classCode.trim();
	    //
	    // String minNo = (String)map.get(MigNumberingMinNoLoader.MINNO);
	    // minNo = minNo.trim();
	    //
	    // KETPartClassification claz = PartClazHelper.service.getPartClassificationByClazCode(classCode);
	    //
	    // claz.setNumberingMinNo(minNo);
	    //
	    // PersistenceServerHelper.manager.update(claz);
	    // }

	    //trx.commit();
	    //trx = null;

	} catch (Exception e) {

	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    /*if (trx != null)
		trx.rollback();
*/
	    SessionContext.setContext(sessioncontext);
	}
    }

}
