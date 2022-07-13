package ext.ket.part.migration.project;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.project.ProjectMaster;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.PartProjectRelation;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.part.migration.erp.service.MigLogUtil;
import ext.ket.part.migration.revision.PartRevisionDTO;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

/**
 * 
 * 부품 마스터와 프로젝트 마스터간의 연결을 만든다. - 전체 리비전의 최신 with Excel
 * 
 */
public class PartProjectRelWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartProjectRelWcLoader manager = new PartProjectRelWcLoader();

    public PartProjectRelWcLoader() {

    }

    // windchill ext.ket.part.migration.project.PartProjectRelWcLoader D:\partProject.xlsx
    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length < 1)
		throw new Exception("@@ args need !");
	    else {
		filePath = args[0];
	    }

	    String toDayTime = "";
	    try {

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());

	    } catch (Exception e) {
		Kogger.error(PartProjectRelWcLoader.class, "Exception : " + e.getMessage());
	    }

	    Kogger.debug(PartProjectRelWcLoader.class, "@start:" + toDayTime);
	    PartProjectRelWcLoader.manager.saveFromExcel(filePath);

	    toDayTime = "";
	    try {

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd:HH-mm-ss");
		toDayTime = sdFormat.format(Calendar.getInstance().getTime());

	    } catch (Exception e) {
		Kogger.error(PartProjectRelWcLoader.class, "Exception : " + e.getMessage());
	    }

	    Kogger.debug(PartProjectRelWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(PartProjectRelWcLoader.class, e);
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

    public void executeMigration(String filePath) throws Exception {

	SessionContext sessioncontext = SessionContext.newContext();
	Transaction trx = null;
	MigLogUtil migLogUtil = new MigLogUtil();
	WTPart logWtPart = null;
	String logPartNo = null;

	try {

	    SessionHelper.manager.setAdministrator();

	    Kogger.debug(getClass(), "## args :" + filePath);

	    PartProjectRelLoader loader = new PartProjectRelLoader();
	    loader.load(filePath);
	    List<Map> list = loader.getList();

	    for (Map map : list) {

		trx = new Transaction();
		trx.start();

		String partNo = (String) map.get(PartProjectRelLoader.PART_NO);
		String projectNo = (String) map.get(PartProjectRelLoader.PROJECT_NO);

		Kogger.debug(getClass(), "### partNo:" + partNo);
		Kogger.debug(getClass(), "### projectNo:" + projectNo);

		WTPart wtPart = PartBaseHelper.service.getLatestPart(partNo.toUpperCase());
		logWtPart = wtPart;
		
//		if(projectNo.length() == 5){
//		    projectNo = "0" + projectNo;
//		}
		
		ProjectMaster project = PartUtil.getProjectMasterByProjectNo(projectNo.toUpperCase());
		logPartNo = partNo + "-" + projectNo;

		if (wtPart == null) {
		    if (project == null) {
			migLogUtil.log("ProjectRel", logWtPart, "PartNonePrjectNone", "부품, 프로젝트 부재", projectNo, partNo);
		    } else {
			migLogUtil.log("ProjectRel", logWtPart, "PartNone", "부품 부재", projectNo, partNo);
		    }
		    
		    trx.commit();
		    trx = null;
		    
		    continue;
		    
		} else if (project == null) {
		    migLogUtil.log("ProjectRel", logWtPart, "PrjectNone", "프로젝트 부재", projectNo, partNo);
		    
		    trx.commit();
		    trx = null;
			
		    continue;
		}

		WTPartMaster wtPartMast = (WTPartMaster) wtPart.getMaster();

		// 전체 Iteration 리스트
		List<PartRevisionDTO> detailMastList = getPartDetailInfo(CommonUtil.getOIDLongValue(wtPartMast));
		updatePartRevision(detailMastList, project);

		trx.commit();
		trx = null;

	    }

	} catch (Exception e) {

	    migLogUtil.log("ProjectRel", logWtPart, "Error", "프로젝트 연결 에러", logPartNo, logPartNo);
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    if (trx != null)
		trx.rollback();

	    SessionContext.setContext(sessioncontext);
	}
    }

    // 부품 정보 업데이트 - 부품 하나당 양산 리비전 처리
    public void updatePartRevision(List<PartRevisionDTO> detailMastList, ProjectMaster project) throws Exception {

	List<List<PartRevisionDTO>> allRevList = new ArrayList<List<PartRevisionDTO>>();

	List<PartRevisionDTO> oneRevList = null;

	long oldRevId = 0;

	// 1. 부품을 리비전별로 나눈다.
	for (int k = 0; k < detailMastList.size(); k++) {

	    PartRevisionDTO dto = detailMastList.get(k);

	    long revId = dto.getRevId();

	    if (k == 0) {
		oldRevId = revId;
		oneRevList = new ArrayList<PartRevisionDTO>();
	    }

	    if (revId == oldRevId) {
		oneRevList.add(dto);
	    } else {
		allRevList.add(oneRevList); // new
		oneRevList = new ArrayList<PartRevisionDTO>();
		oneRevList.add(dto);
	    }

	    if (k == detailMastList.size() - 1) {
		allRevList.add(oneRevList); // new end
	    }

	    // oldRev Change
	    oldRevId = revId;
	}

	// 각 Revision의 최신Iteration에서 개발단계가 있는지 체크한다.
	for (List<PartRevisionDTO> revOneList : allRevList) {
	    for (int k = 0; k < 1; k++) {
		PartRevisionDTO dto = revOneList.get(k);
		String iterationOid = dto.getIterOid();
		WTPart wtPart = (WTPart) CommonUtil.getObject(iterationOid);
		PartProjectRelation partProjectRelation = new PartProjectRelation();
		partProjectRelation.associateCreate(wtPart, project);
	    }
	}
    }

    // 마스터의 상세 부품 리스트
    private List<PartRevisionDTO> getPartDetailInfo(long mid) throws Exception {

	List<PartRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT WTPARTNUMBER DOC_NO, M.NAME \n");
	    buffer.append("      , E.VERSIONIDA2VERSIONINFO VER_NO \n");
	    buffer.append("      , E.VERSIONSORTIDA2VERSIONINFO VER_SORT_NO \n");
	    buffer.append("      , E.ITERATIONIDA2ITERATIONINFO ITER_NO \n");
	    buffer.append("      , M.IDA2A2 MID \n");
	    buffer.append("      , E.STATECHECKOUTINFO STATECHECKOUTINFO \n");
	    buffer.append("      , M.CLASSNAMEA2A2||':'||M.IDA2A2 MOID \n");
	    buffer.append("      , E.BRANCHIDITERATIONINFO VID \n");
	    buffer.append("      , 'VR:'||E.CLASSNAMEA2A2||':'||E.BRANCHIDITERATIONINFO VOID \n");
	    buffer.append("      , E.IDA2A2 EID \n");
	    buffer.append("      , E.CLASSNAMEA2A2||':'||E.IDA2A2 EOID \n");
	    buffer.append(" FROM WTPARTMASTER M, WTPART E \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.IDA2A2 = ? \n");
	    buffer.append(" AND M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    buffer.append(" ORDER BY M.IDA2A2, E.VERSIONSORTIDA2VERSIONINFO , TO_NUMBER(E.ITERATIONIDA2ITERATIONINFO) DESC \n");

	    aBind.add(mid);

	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new StampRowSetStrategy<PartRevisionDTO>() {

		@Override
		public PartRevisionDTO mapRow(ResultSet rs) throws SQLException {

		    PartRevisionDTO dto = new PartRevisionDTO();

		    dto.setEpmNo(rs.getString("DOC_NO"));
		    dto.setEpmName(rs.getString("NAME"));

		    dto.setVerNo(rs.getString("VER_NO"));
		    dto.setVerSortNo(rs.getString("VER_SORT_NO"));

		    dto.setIterNo(rs.getString("ITER_NO"));

		    dto.setStateCheckState(rs.getString("STATECHECKOUTINFO"));

		    dto.setMastOid(rs.getString("MOID"));
		    dto.setIterOid(rs.getString("EOID"));
		    dto.setRevOid(rs.getString("VOID"));

		    dto.setMastId(rs.getLong("MID"));
		    dto.setRevId(rs.getLong("VID"));
		    dto.setIterId(rs.getLong("EID"));

		    return dto;
		}

	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return list;
    }

}
