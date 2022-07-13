package ext.ket.common.dashboard.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.common.dashboard.entity.KETDashBoardMailLink;
import ext.ket.common.dashboard.entity.KETDashBoardMailSetting;
import ext.ket.common.dashboard.entity.dto.KETMailReceiveDTO;
import ext.ket.common.dashboard.service.internal.KETMailReceiveBuilder;
import ext.ket.dashboard.entity.DashBoardDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKETMailReceiveService extends StandardManager implements KETMailReceiveService {

    private static final long serialVersionUID = 1L;

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardKETMailReceiveService newStandardKETMailReceiveService() throws WTException {
	StandardKETMailReceiveService instance = new StandardKETMailReceiveService();
	instance.initialize();
	return instance;
    }

    @Override
    public List<KETMailReceiveDTO> searchFullList() throws Exception {
	// TODO Auto-generated method stub
	List<KETDashBoardMailSetting> mailSetList = query.queryForListByOneClass(KETDashBoardMailSetting.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		// TODO Auto-generated method stub

	    }
	});
	List<KETMailReceiveDTO> list = new ArrayList<KETMailReceiveDTO>();

	KETMailReceiveBuilder builder = new KETMailReceiveBuilder();
	for (KETDashBoardMailSetting mailConfig : mailSetList) {
	    KETMailReceiveDTO ReceiveDto = new KETMailReceiveDTO();

	    ReceiveDto = builder.buildPers2Dto(mailConfig, ReceiveDto);

	    list.add(ReceiveDto);

	}
	return list;
    }

    @Override
    public void save(KETMailReceiveDTO dto) throws Exception {
	// TODO Auto-generated method stub
	Transaction trx = null;

	String peoOidAttr[] = dto.getTargetPeopleOidAttr();
	String deptOidAttr[] = dto.getTargetDeptOidAttr();
	String receiveOidAttr[] = dto.getReceiveOidAttr();
	boolean isDel = true;
	boolean isNew = true;
	try {

	    trx = new Transaction();
	    trx.start();

	    People peo = null;
	    KETDashBoardMailSetting mailset = null;
	    for (int i = 0; i < peoOidAttr.length; i++) {

		String receiveOid = receiveOidAttr[i];
		String peoOid = peoOidAttr[i];
		String deptOid = deptOidAttr[i];
		String deptAttr[] = deptOid.split(", ");

		peo = (People) CommonUtil.getObject(peoOid);

		if (StringUtil.isEmpty(receiveOid)) {

		    mailset = KETDashBoardMailSetting.newKETDashBoardMailSetting();

		    mailset.setMan(peo);
		    mailset.setId(peo.getId());
		    mailset.setName(peo.getName());

		    mailset = (KETDashBoardMailSetting) PersistenceHelper.manager.save(mailset);

		    PersistenceHelper.manager.refresh(mailset);

		    for (int j = 0; j < deptAttr.length; j++) {
			Department dept = (Department) CommonUtil.getObject(deptAttr[j]);
			KETDashBoardMailLink link = KETDashBoardMailLink.newKETDashBoardMailLink(mailset, dept);
			link = (KETDashBoardMailLink) PersistenceHelper.manager.save(link);
			PersistenceHelper.manager.refresh(link);
		    }

		} else {
		    mailset = (KETDashBoardMailSetting) CommonUtil.getObject(receiveOid);

		    List<KETDashBoardMailLink> linkList = query.queryForListLinkByRoleA(KETDashBoardMailSetting.class,
			    KETDashBoardMailLink.class, mailset);

		    for (KETDashBoardMailLink link : linkList) {

			Department orgDept = link.getDept();

			for (int j = 0; j < deptAttr.length; j++) {

			    if (deptAttr[j].equals(CommonUtil.getFullOIDString(orgDept))) {
				isDel = false;
			    }
			}

			if (isDel) {
			    Persistable pers = (Persistable) link;
			    ObjectUtil.deletePersistableWithAdmin(pers);
			}
			isDel = true;
		    }

		    linkList = query.queryForListLinkByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class, mailset);

		    for (int j = 0; j < deptAttr.length; j++) {
			for (KETDashBoardMailLink link : linkList) {
			    Department orgDept = link.getDept();
			    if (deptAttr[j].equals(CommonUtil.getFullOIDString(orgDept))) {
				isNew = false;
			    }
			}
			if (isNew) {
			    Department dept = (Department) CommonUtil.getObject(deptAttr[j]);
			    KETDashBoardMailLink link = KETDashBoardMailLink.newKETDashBoardMailLink(mailset, dept);
			    link = (KETDashBoardMailLink) PersistenceHelper.manager.save(link);
			    PersistenceHelper.manager.refresh(link);
			}
			isNew = true;
		    }

		}
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {

	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    if (trx != null)
		trx.rollback();

	}
    }

    @Override
    public void delete(String oid) throws Exception {
	// TODO Auto-generated method stub
	Persistable pers = CommonUtil.getObject(oid);

	if (pers == null)
	    throw new Exception("@ Object is null ");

	KETDashBoardMailSetting mailset = (KETDashBoardMailSetting) CommonUtil.getObject(oid);

	List<KETDashBoardMailLink> linkList = query.queryForListLinkByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class,
	        mailset);

	for (KETDashBoardMailLink link : linkList) {
	    Persistable per_link = link;
	    ObjectUtil.deletePersistableWithAdmin(per_link);
	}

	ObjectUtil.deletePersistableWithAdmin(pers);
    }

    @Override
    public List<KETMailReceiveDTO> searchFullList(String oid) throws Exception {

	// TODO Auto-generated method stub

	KETDashBoardMailSetting mailConfig = (KETDashBoardMailSetting) CommonUtil.getObject(oid);

	List<KETMailReceiveDTO> list = new ArrayList<KETMailReceiveDTO>();

	KETMailReceiveDTO ReceiveDto = new KETMailReceiveDTO();

	KETMailReceiveBuilder builder = new KETMailReceiveBuilder();

	ReceiveDto = builder.buildPers2Dto(mailConfig, ReceiveDto);

	list.add(ReceiveDto);

	return list;
    }

    public String Formatting(int size) {
	return String.format("%,d", size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, String> getTaskCount(DashBoardDTO dashBoardDTO, SqlSession session) throws Exception {
	HashMap<String, String> TaskCount = new HashMap<String, String>();

	if (dashBoardDTO.getDashboardType().equals("pilot")) {
	    List<DashBoardDTO> taskPlan = session.selectList("mold.teamWorkTimeTaskPlanProductCount", dashBoardDTO);
	    List<DashBoardDTO> taskProcess = session.selectList("mold.teamWorkTimeTaskProcessProductCount", dashBoardDTO);
	    List<DashBoardDTO> taskDelay = session.selectList("mold.teamWorkTimeTaskDelayProductCount", dashBoardDTO);
	    List<DashBoardDTO> taskComplete = session.selectList("mold.teamWorkTimeTaskCompleteProductCount", dashBoardDTO);

	    int taskTotal = taskPlan.size() + taskProcess.size() + taskDelay.size() + taskComplete.size();

	    TaskCount.put("taskPlan", Formatting(taskPlan.size()));
	    TaskCount.put("taskProcess", Formatting(taskProcess.size()));
	    TaskCount.put("taskDelay", Formatting(taskDelay.size()));
	    TaskCount.put("taskComplete", Formatting(taskComplete.size()));
	    TaskCount.put("taskTotal", Formatting(taskTotal));
	} else if (dashBoardDTO.getDashboardType().equals("delay")) {
	    // 14일 이상
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    Calendar c14 = Calendar.getInstance();
	    c14.add(Calendar.DATE, -14);

	    String Day14 = formatter.format(c14.getTime());
	    dashBoardDTO.setCurrentDate(Day14);

	    List<DashBoardDTO> taskDelay14 = session.selectList("mold.teamWorkTimeTaskAfterDelay14ProductCount", dashBoardDTO);

	    /***********************************************************************************************************************/

	    // 현재일-7일
	    Calendar c7 = Calendar.getInstance();
	    c7.add(Calendar.DATE, -7);

	    String Day7 = formatter.format(c7.getTime());
	    dashBoardDTO.setStartDate(Day7);

	    dashBoardDTO.setEndDate(Day14);

	    List<DashBoardDTO> taskDelay7 = session.selectList("mold.teamWorkTimeTaskAfterDelayProductCount", dashBoardDTO);

	    /***********************************************************************************************************************/

	    // 현재일-3일
	    Calendar c3 = Calendar.getInstance();
	    c3.add(Calendar.DATE, -3);

	    String Day3 = formatter.format(c3.getTime());

	    dashBoardDTO.setStartDate(Day3);
	    dashBoardDTO.setEndDate(Day7);

	    List<DashBoardDTO> taskDelay3 = session.selectList("mold.teamWorkTimeTaskAfterDelayProductCount", dashBoardDTO);

	    // 현재일-1일
	    Calendar c1 = Calendar.getInstance();
	    c1.add(Calendar.DATE, -1);

	    String Day1 = formatter.format(c1.getTime());

	    dashBoardDTO.setStartDate(Day1);
	    dashBoardDTO.setEndDate(Day3);

	    List<DashBoardDTO> taskDelay1 = session.selectList("mold.teamWorkTimeTaskAfterDelayProductCount", dashBoardDTO);

	    TaskCount.put("taskDelay14", Formatting(taskDelay14.size()));
	    TaskCount.put("taskDelay7", Formatting(taskDelay7.size()));
	    TaskCount.put("taskDelay3", Formatting(taskDelay3.size()));
	    TaskCount.put("taskDelay1", Formatting(taskDelay1.size()));

	    int taskDelayTotal = taskDelay14.size() + taskDelay7.size() + taskDelay3.size() + taskDelay1.size();

	    TaskCount.put("taskDelayTotal", Formatting(taskDelayTotal));

	} else if (dashBoardDTO.getDashboardType().equals("progress")) {

	    // 1일 이하
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    Calendar c1 = Calendar.getInstance();

	    String currentDate = formatter.format(c1.getTime());

	    c1.add(Calendar.DATE, 1);

	    String Day1 = formatter.format(c1.getTime());

	    dashBoardDTO.setCurrentDate(currentDate);
	    dashBoardDTO.setStartDate(Day1);

	    List<DashBoardDTO> taskProgress1 = session.selectList("mold.teamWorkTimeTaskBefore1ProcessProductCount", dashBoardDTO);

	    /***********************************************************************************************************************/
	    // 3일 이하
	    Calendar c3 = Calendar.getInstance();
	    c3.add(Calendar.DATE, 3);

	    String Day3 = formatter.format(c3.getTime());
	    dashBoardDTO.setStartDate(Day3);
	    dashBoardDTO.setEndDate(Day1);

	    List<DashBoardDTO> taskProgress3 = session.selectList("mold.teamWorkTimeTaskBeforeProcessProductCount", dashBoardDTO);
	    /***********************************************************************************************************************/
	    // 7일 이하
	    Calendar c7 = Calendar.getInstance();
	    c7.add(Calendar.DATE, 7);

	    String Day7 = formatter.format(c7.getTime());
	    dashBoardDTO.setStartDate(Day7);
	    dashBoardDTO.setEndDate(Day3);

	    List<DashBoardDTO> taskProgress7 = session.selectList("mold.teamWorkTimeTaskBeforeProcessProductCount", dashBoardDTO);

	    /***********************************************************************************************************************/

	    Calendar c = Calendar.getInstance();

	    c.add(Calendar.DATE, -10);

	    String startDate = formatter.format(c.getTime());
	    dashBoardDTO.setStartDate(startDate);
	    dashBoardDTO.setCurrentDate(currentDate);
	    c.add(Calendar.DATE, 20);

	    String endDate = formatter.format(c.getTime());
	    dashBoardDTO.setEndDate(endDate);

	    // 7일 이상

	    List<DashBoardDTO> taskProgress7_odd = session.selectList("mold.teamWorkTimeTaskProcessProductCount_7", dashBoardDTO);

	    /***********************************************************************************************************************/

	    // 계획종료일자 초과 (선행지연)

	    List<DashBoardDTO> taskProgress_exceed = session.selectList("mold.teamWorkTimeTaskProcessProductCount_exceed", dashBoardDTO);

	    TaskCount.put("taskProgress1", Formatting(taskProgress1.size()));
	    TaskCount.put("taskProgress3", Formatting(taskProgress3.size()));
	    TaskCount.put("taskProgress7", Formatting(taskProgress7.size()));
	    TaskCount.put("taskProgress7_odd", Formatting(taskProgress7_odd.size()));
	    TaskCount.put("taskProgress_exceed", Formatting(taskProgress_exceed.size()));

	    int taskProgressTotal = taskProgress1.size() + taskProgress3.size() + taskProgress7.size() + taskProgress7_odd.size()
		    + taskProgress_exceed.size();

	    TaskCount.put("taskProgressTotal", Formatting(taskProgressTotal));

	}

	return TaskCount;
    }

    @Override
    public List<WTUser> dashboardSendMail() throws Exception {
	// TODO Auto-generated method stub
	List<KETDashBoardMailSetting> mailSetList = query.queryForListByOneClass(KETDashBoardMailSetting.class, new QueryStrategy() {
	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		// TODO Auto-generated method stub

	    }
	});

	List<WTUser> listToUser = null;

	List<WTUser> exceptList = new ArrayList<WTUser>();

	WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	for (KETDashBoardMailSetting mailSet : mailSetList) {
	    People peo = (People) mailSet.getMan();
	    PeopleData User = new PeopleData(peo);
	    WTUser toUser = User.wtuser;
	    listToUser = new ArrayList<WTUser>();
	    listToUser.add(toUser);
	    exceptList.add(toUser);
	    KETMailHelper.service.sendFormMail("08134", "DashBoardTaskNoticeMail.html", mailSet, wtUserFrom, listToUser);
	}

	return exceptList;
    }

    @Override
    public void pjtMainScheduleSendMail() throws Exception {

	List<Map<String, Object>> mainPjtList = this.getPjtMainScheduleData(2);// 10일 이상 지연 Task가 2개 이상인 프로젝트 목록을 추출한다.

	List<Map<String, Object>> mailReceiveList = new ArrayList<Map<String, Object>>();

	ArrayList OfficerList = null;
	ArrayList childDeptList = null;

	List<WTUser> listToUser = new ArrayList<WTUser>();

	String checkUser = "";// 중복방지용

	for (Map<String, Object> deptMap : mainPjtList) {

	    Iterator<String> it = deptMap.keySet().iterator();
	    while (it.hasNext()) {
		String key = it.next();
		String value = (String) deptMap.get(key);

		Department dept = null;
		if (StringUtils.isNotEmpty(value) && key.endsWith("_DEPT")) {// 해당 프로젝트의 주요 Role (영업,PM, 등등등) 담당자의 부서 추출
		    dept = (Department) CommonUtil.getObject("e3ps.groupware.company.Department:" + value);
		}

		if (dept != null) {

		    Map<String, Object> tempMap = new HashMap<String, Object>();

		    WTUser ChiefUser = PeopleHelper.manager.getChiefUser(dept);// 해당부서의 팀장

		    if (ChiefUser != null && !StringUtils.contains(checkUser, ChiefUser.getName() + ";")) {// 팀장
			tempMap.put("dept", value + ";");
			tempMap.put("level", "2");
			tempMap.put("user", ChiefUser);
			mailReceiveList.add(tempMap);
			checkUser += ChiefUser.getName() + ";";
		    }
		    OfficerList = new ArrayList();
		    OfficerList = DepartmentHelper.manager.getParentsList(dept, OfficerList);

		    if (OfficerList != null && OfficerList.size() > 0) {
			for (int j = 0; j < OfficerList.size(); j++) {
			    Department depart = (Department) OfficerList.get(j);
			    if (depart.getParent() == null) {// 최상위 부서는 스킵
				continue;
			    }
			    if (PeopleHelper.manager.getChiefUser(depart) != null) {
				ChiefUser = PeopleHelper.manager.getChiefUser(depart);// 상위 부서장
				if (ChiefUser != null && !StringUtils.contains(checkUser, ChiefUser.getName() + ";")) {

				    String excludedChief = ChiefUser.getName();
				    if ("ighwang".equals(excludedChief)) {// 황인각 상무 조건없는 메일 수신 대상 제외 요청 2022.02.15 by 파주 커넥터생산관리팀 변승훈
					continue;
				    }

				    tempMap = new HashMap<String, Object>();
				    childDeptList = new ArrayList();
				    // childDeptList = DepartmentHelper.manager.getAllChildList(depart, childDeptList);
				    childDeptList = TreeHelper.manager.getAllChildList(Department.class, (Tree) depart, new ArrayList());
				    String childDepts = "";

				    for (Object obj : childDeptList) {
					Department childDept = (Department) obj;
					childDepts += CommonUtil.getOIDLongValue2Str(childDept) + ";";
				    }

				    tempMap.put("dept", childDepts);
				    tempMap.put("level", "3");
				    tempMap.put("user", ChiefUser);
				    mailReceiveList.add(tempMap);
				    checkUser += ChiefUser.getName() + ";";
				}
			    }
			}
		    }
		}
	    }

	}

	WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

	List<Map<String, Object>> lastTargetPjtList = new ArrayList<Map<String, Object>>();

	for (Map<String, Object> tempMap : mailReceiveList) {

	    for (Map<String, Object> mainMap : mainPjtList) {

		String delayCnt = (String) mainMap.get("DELAY_CNT");

		if (Integer.parseInt(delayCnt) <= 2) {
		    delayCnt = "2";
		} else {
		    delayCnt = "3";
		}

		if (Integer.parseInt(delayCnt) >= Integer.parseInt((String) tempMap.get("level"))) {// 팀장 대상인지 상위부서장 대상인지 판단
		    Set<String> st = mainMap.keySet();
		    Iterator<String> it = st.iterator();

		    while (it.hasNext()) {// 해당 프로젝트의 role에 해당하는 팀인지
			String key = (String) it.next();
			String value = StringUtil.checkNull((String) mainMap.get(key));

			if (StringUtils.isNotEmpty(value) && key.endsWith("_DEPT")
			        && StringUtils.isNotEmpty(StringUtil.checkNull((String) tempMap.get("dept")))
			        && StringUtils.contains(StringUtil.checkNull((String) tempMap.get("dept")), value + ";")) {
			    mainMap.put("delayCnt", (String) tempMap.get("level"));
			    lastTargetPjtList.add(mainMap);
			    break;
			}
		    }
		}
	    }

	    listToUser.add((WTUser) tempMap.get("user"));

	    if (lastTargetPjtList != null && lastTargetPjtList.size() > 0) {
		KETMailHelper.service.sendFormMail("08143", "ProjectMainScheduleMail.html", lastTargetPjtList, wtUserFrom, listToUser);
	    }

	    listToUser = new ArrayList<WTUser>();
	    lastTargetPjtList = new ArrayList<Map<String, Object>>();
	}

	if (mainPjtList != null && mainPjtList.size() > 0) {
	    // 여기서부터는 하드코딩된 인원에게 전체리스트를 보낸다
	    listToUser = new ArrayList<WTUser>();

	    listToUser.add(KETObjectUtil.getUser("jbhong")); // 홍종범 이사
	    listToUser.add(KETObjectUtil.getUser("sspark")); // 박상수 차장
	    mainPjtList.get(0).put("delayCnt", "2");
	    KETMailHelper.service.sendFormMail("08143", "ProjectMainScheduleMail.html", mainPjtList, wtUserFrom, listToUser);
	}

    }

    public List<Map<String, Object>> getPjtMainScheduleData(int cnt) throws Exception {

	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT PJTNO,PJTNAME,CAR_TYPE,PJT_SUBCONTRACTOR,STEP,                                                                              \n");
	    sql.append("        DR1_D,DR2_D,GT2_D,DR3_D,DR4_D,AT_D,GT3_D,PV_D,DR5_D,FT_D,GT4_D,DR6_D,PM_D AS PM_DEPT,SALES_D AS SALES_DEPT,PROD_DEV_D AS PROD_DEV_DEPT,DIE_MOLD_D AS DIE_MOLD_DEPT,DIE_PRESS_D AS DIE_PRESS_DEPT,DIE_CHINA_D AS DIE_CHINA_DEPT,  \n");
	    sql.append("        RELIBILITY_TEST_D AS RELIBILITY_TEST_DEPT,FACILITY_MAKE_D AS FACILITY_MAKE_DEPT,ADV_QA_CHINA_D AS ADV_QA_CHINA_DEPT,ADV_QC_D AS ADV_QC_DEPT,QC_ASSY_D AS QC_ASSY_DEPT,QC_CHINA_D AS QC_CHINA_DEPT,PROD_TECH_D AS PROD_TECH_DEPT,PROD_TECH_CHINA_D AS PROD_TECH_CHINA_DEPT,DELAY_CNT,CUS_SOP,CAR_SOP,ISSUE_CNT,PRODUCTION_ASSY_D,PRODUCTION_MOLD_D,PRODUCTION_CHINA_D                \n");
	    sql.append("   FROM F_PJT_MAIN_SCHEDULE_FOR_MAIL WHERE DELAY_CNT >= " + cnt
		    + "                                                                           \n");

	    rs = stat.executeQuery(sql.toString());

	    list = ext.ket.common.util.ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
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
}