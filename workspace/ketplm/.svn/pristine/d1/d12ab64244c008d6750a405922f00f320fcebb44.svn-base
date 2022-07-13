package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.part.WTPartMaster;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.entity.KETPartMassPlan;
import ext.ket.shared.log.Kogger;

public class MigMassPartInfo implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigMassPartInfo manager = new MigMassPartInfo();

    public MigMassPartInfo() {

    }

    // windchill ext.ket.part.migration.base.MigMassPartInfo

    public static void main(String[] args) {

	try {

	    String filePath = "";

	    Kogger.debug(MigMassPartInfo.class, "@start");
	    MigMassPartInfo.manager.saveFromExcel(filePath);
	    Kogger.debug(MigMassPartInfo.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigMassPartInfo.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

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
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	String partNo = "";

	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();

	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();

	    Department userDept = DepartmentHelper.manager.getDepartment(user);

	    List<Map<String, Object>> partList = getResultList(getMigMassDataListQuery());

	    Map<String, Object> paramMap = new HashMap<String, Object>();

	    int totalSize = partList.size();
	    int cnt = 0;

	    for (Map<String, Object> partMap : partList) {
		KETPartMassPlan massPart = null;

		String partOid = (String) partMap.get("PARTOID");
		String mpDate = (String) partMap.get("MPDATE");
		partNo = (String) partMap.get("WTPARTNUMBER");
		String partName = (String) partMap.get("NAME");
		String masterOid = (String) partMap.get("MASTEROID");

		paramMap.put("partNo", partNo);
		paramMap.put("partName", partName);

		WTPartMaster master = (WTPartMaster) CommonUtil.getObject(masterOid);

		if (!":".equals(partOid)) {
		    massPart = (KETPartMassPlan) CommonUtil.getObject(partOid);
		}
		Timestamp masStartDate = DateUtil.convertStartDate2(mpDate);
		if (massPart != null) {
		    massPart.setMasStartDate(masStartDate);
		    PersistenceHelper.manager.save(massPart);
		} else {
		    partMassPlanUpdate(master, user, userDept, masStartDate);
		}
		cnt++;
		System.out.println("총 " + totalSize + " 건 중 " + cnt + " 건 완료 + PartNo : " + partNo);

	    }

	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    System.out.println(partNo + " 처리중 오류 발생!");
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    public void createMassPartObject(Map<String, Object> param) throws Exception {

	KETPartMassPlan massPlanPart = KETPartMassPlan.newKETPartMassPlan();

	ObjectUtil.manager.convertMapToObject(param, massPlanPart);
	WTPartMaster master = (WTPartMaster) param.get("masterObject");
	massPlanPart.setPartMaster(master);
	massPlanPart.setOwner(SessionHelper.manager.getPrincipalReference());

	PersistenceHelper.manager.save(massPlanPart);

    }

    public void partMassPlanUpdate(WTPartMaster master, WTUser user, Department userDept, Timestamp masStartDate) throws Exception {

	if (master != null) {

	    String partNo = master.getNumber();
	    String partName = master.getName();
	    List<Map<String, Object>> pjtInfoList = getResultList(getMassPlanByProjectQuery(partNo));

	    List<Map<String, Object>> ecoInfoList = getResultList(getMassPlanByEcoQuery(partNo));

	    String pjtNo = "";
	    String pjtName = "";
	    String pjtNos = "";
	    String processGb = "";
	    String ecoNo = "";

	    String modifyName = user.getFullName();
	    String modifyCode = user.getName();
	    String modifyDeptName = "";
	    String modifyDeptCode = "";

	    if (userDept != null) {
		modifyDeptName = userDept.getName();
		modifyDeptCode = userDept.getCode();
	    }

	    Timestamp pjtEndDate = null;
	    Timestamp dr6EndDate = null;

	    Timestamp ecoApproveDate = null;

	    if (pjtInfoList != null) {
		for (Map<String, Object> pjtMap : pjtInfoList) {
		    if (StringUtils.isEmpty(pjtNo)) {
			pjtNo = (String) pjtMap.get("PJTNO");
			pjtName = (String) pjtMap.get("PJTNAME");
			if (StringUtils.isNotEmpty((String) pjtMap.get("DR6ENDDATE"))) {
			    dr6EndDate = DateUtil.convertStartDate2((String) pjtMap.get("DR6ENDDATE"));
			}

			pjtEndDate = DateUtil.convertStartDate2((String) pjtMap.get("EXECENDDATE"));

			processGb = (String) pjtMap.get("PROCESS");
		    } else {
			pjtNos = (String) pjtMap.get("PJTNO") + ",";
		    }
		}
	    }
	    pjtNos = StringUtils.removeEnd(pjtNos, ",");

	    if (ecoInfoList != null) {
		for (Map<String, Object> ecoMap : ecoInfoList) {
		    ecoApproveDate = DateUtil.convertStartDate2((String) ecoMap.get("ECO_APPROVED_DATE"));
		    ecoNo = (String) ecoMap.get("ECOID");
		    break;
		}
	    }
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("partNo", partNo);
	    paramMap.put("partName", partName);
	    paramMap.put("pjtNo", pjtNo);
	    paramMap.put("pjtName", pjtName);
	    paramMap.put("pjtNos", pjtNos);
	    paramMap.put("processGb", processGb);
	    paramMap.put("ecoNo", ecoNo);
	    paramMap.put("masStartDate", masStartDate);
	    paramMap.put("pjtEndDate", pjtEndDate);
	    paramMap.put("dr6EndDate", dr6EndDate);
	    paramMap.put("ecoApproveDate", ecoApproveDate);
	    paramMap.put("modifyName", modifyName);
	    paramMap.put("modifyCode", modifyCode);
	    paramMap.put("modifyDeptName", modifyDeptName);
	    paramMap.put("modifyDeptCode", modifyDeptCode);
	    paramMap.put("masterObject", master);

	    createMassPartObject(paramMap);

	}
    }

    public KETPartMassPlan getMassPartPlan(String partNo) throws Exception {
	QuerySpec qs = new QuerySpec();
	int index1 = qs.addClassList(KETPartMassPlan.class, true);
	qs.appendWhere(new SearchCondition(KETPartMassPlan.class, KETPartMassPlan.PART_NO, "=", partNo), new int[] { index1 });
	QueryResult qr = PersistenceHelper.manager.find(qs);
	KETPartMassPlan massPlanPart = null;

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    massPlanPart = (KETPartMassPlan) objArr[0];
	}
	return massPlanPart;
    }

    public String getMigMassDataListQuery() {
	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT A.CLASSNAMEA2A2||':'||A.IDA2A2 AS MASTEROID, WTPARTNUMBER,TO_CHAR(MPDATE,'YYYY-MM-DD') AS MPDATE, B.CLASSNAMEA2A2||':'||B.IDA2A2 AS PARTOID FROM SSP_PM_TMP A, KETPARTMASSPLAN B WHERE A.WTPARTNUMBER = B.PARTNO(+)");
	return sql.toString();
    }

    public String getMassPlanByEcoQuery(String partNo) {
	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT TO_CHAR(BEH.COMPLETEDDATE,'YYYY-MM-DD') AS ECO_APPROVED_DATE,                                                                            \n  ");
	sql.append("        ECOID                                                                                                              \n  ");
	sql.append("   FROM WTPART A, WTPARTMASTER B,                                                                                          \n  ");
	sql.append("       (  SELECT WFM.COMPLETEDDATE, ECOITEMCODE, BOMVERSION,ECOID                                                          \n  ");
	sql.append("           FROM KETPRODCHANGEORDER ECO                                                                                     \n  ");
	sql.append("               ,KETPRODCHANGEACTIVITY ECA                                                                                  \n  ");
	sql.append("               , ( SELECT IDA3B5, IDA3A5, DECODE(NVL(AFTERVERSION, ''), '', PREVERSION, AFTERVERSION) AS AFTERVERSION      \n  ");
	sql.append("                     FROM KETPRODECABOMLINK                                                                                \n  ");
	sql.append("                 ) BOMLINK                                                                                                 \n  ");
	sql.append("               , ( SELECT A.IDA2A2, A.ECOHEADERNUMBER, A.ECOITEMCODE, A.BOMVERSION                                         \n  ");
	sql.append("                     FROM KETBOMECOHEADER A WHERE ECOITEMCODE = '" + partNo
	        + "'                                                  \n  ");
	sql.append("                UNION ALL                                                                                                  \n  ");
	sql.append("                   SELECT B.IDA2A2, B.ECOHEADERNUMBER, B.NEWBOMCODE AS ECOITEMCODE, B.BOMVERSION                           \n  ");
	sql.append("                     FROM KETBOMHEADER B WHERE NEWBOMCODE = '" + partNo
	        + "'                                                     \n  ");
	sql.append("                 )BOMHEADER                                                                                                \n  ");
	sql.append("               , KETWFMAPPROVALMASTER WFM                                                                                  \n  ");
	sql.append("         WHERE ECO.IDA2A2 = ECA.IDA3A8                                                                                     \n  ");
	sql.append("           AND ECA.IDA2A2 = BOMLINK.IDA3B5                                                                                 \n  ");
	sql.append("           AND BOMLINK.IDA3A5 = BOMHEADER.IDA2A2                                                                           \n  ");
	sql.append("           AND ECO.STATESTATE = 'APPROVED'                                                                                 \n  ");
	sql.append("           AND ECO.IDA2A2 = WFM.IDA3B4                                                                                     \n  ");
	sql.append("       ) BEH                                                                                                               \n  ");
	sql.append("  WHERE A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                   \n  ");
	sql.append("    AND LATESTITERATIONINFO = '1'                                                                                          \n  ");
	sql.append("    AND VERSIONIDA2VERSIONINFO||WTPARTNUMBER IN ( SELECT MAX(VERSIONIDA2VERSIONINFO)||WTPARTNUMBER                         \n  ");
	sql.append("                                                    FROM WTPART A, WTPARTMASTER B                                          \n  ");
	sql.append("                                                   WHERE A.IDA3MASTERREFERENCE = B.IDA2A2                                  \n  ");
	sql.append("                                                     AND LATESTITERATIONINFO = '1'                                         \n  ");
	sql.append("                                                     AND A.PTC_STR_2TYPEINFOWTPART = '0'                                   \n  ");
	sql.append("                                                     AND A.PTC_STR_4TYPEINFOWTPART = 'PC004'                               \n  ");
	sql.append("                                                     AND A.PTC_STR_3TYPEINFOWTPART NOT IN ('D', 'M')                 \n  ");
	sql.append("                                                     AND WTPARTNUMBER =  '" + partNo
	        + "'                                        \n  ");
	sql.append("                                                GROUP BY WTPARTNUMBER )                                                    \n  ");
	sql.append("   AND BEH.ECOITEMCODE = B.WTPARTNUMBER                                                                                    \n  ");
	sql.append("   AND BEH.BOMVERSION = A.VERSIONIDA2VERSIONINFO                                                                           \n  ");
	sql.append("   ORDER BY COMPLETEDDATE ASC  \n  ");

	return sql.toString();
    }

    public String getMassPlanByProjectQuery(String partNo) {
	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT PJTNO, PJTNAME, TO_CHAR(DR6ENDDATE,'YYYY-MM-DD')  AS DR6ENDDATE, TO_CHAR(EXECENDDATE,'YYYY-MM-DD') AS  EXECENDDATE,PROCESS           \n");
	sql.append("   FROM (                                                                                                                                \n");
	sql.append(" SELECT PJTNO, PJTNAME,PLANENDDATE42 AS DR6ENDDATE, EXECENDDATE,PI.PNUM AS PARTNO,DECODE(IDA3F9,1077705899,'양산','Pilot') AS PROCESS      \n");
	sql.append("   FROM PRODUCTPROJECT      PJT                                                                                                          \n");
	sql.append("        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                  \n");
	sql.append("        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                    \n");
	sql.append("        ,PRODUCTINFO PI                                                                                                                  \n");
	sql.append("   WHERE 1=1                                                                                                                             \n");
	sql.append("     AND PJT.LASTEST       = 1                                                                                                           \n");
	sql.append("     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                      \n");
	sql.append("     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                           \n");
	sql.append("     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                             \n");
	sql.append("     AND PJT.IDA2A2 = PI.IDA3A3                                                                                                          \n");
	sql.append("     AND PI.PNUM = '" + partNo
	        + "'                                                                                                            \n");
	sql.append("     AND IDA3F9 IN (19875,1077705899)                                                                                                    \n");
	sql.append("     AND EXECENDDATE IS NOT NULL                                                                                                         \n");
	sql.append("     UNION                                                                                                                               \n");
	sql.append(" SELECT PJTNO, PJTNAME, PLANENDDATE42 AS DR6ENDDATE, EXECENDDATE,MIINFO.PARTNO,DECODE(IDA3F9,1077705899,'양산','Pilot') AS PROCESS         \n");
	sql.append("   FROM PRODUCTPROJECT      PJT                                                                                                          \n");
	sql.append("        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                  \n");
	sql.append("        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                    \n");
	sql.append("        ,MOLDITEMINFO MIINFO                                                                                                             \n");
	sql.append("   WHERE 1=1                                                                                                                             \n");
	sql.append("     AND PJT.LASTEST       = 1                                                                                                           \n");
	sql.append("     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                      \n");
	sql.append("     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                           \n");
	sql.append("     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                             \n");
	sql.append("     AND PJT.IDA2A2 = MIINFO.IDA3A3                                                                                                      \n");
	sql.append("     AND SHRINKAGE = '신규'                                                                                                                \n");
	sql.append("     AND IDA3F9 IN (19875,1077705899)                                                                                                    \n");
	sql.append("     AND MIINFO.PARTNO = '" + partNo
	        + "'                                                                                                    \n");
	sql.append("     AND EXECENDDATE IS NOT NULL ) ORDER BY EXECENDDATE                                                                                \n");
	return sql.toString();
    }

    public List<Map<String, Object>> getResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

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

}
