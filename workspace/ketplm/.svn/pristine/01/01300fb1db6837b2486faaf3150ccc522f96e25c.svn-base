package ext.ket.part.migration.devepm;

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

import org.apache.commons.lang.StringUtils;

import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class DevEpmWcLoader implements RemoteAccess, Serializable {

    private static final long serialVersionUID = 1L;
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static DevEpmWcLoader manager = new DevEpmWcLoader();

    public DevEpmWcLoader() {

    }

    // windchill ext.ket.part.migration.devepm.DevEpmWcLoader Z:\90.개인폴더\이응진\부품사양관리\업로드포멧\업로드작업\CP_EPM.xlsx
    // windchill ext.ket.part.migration.devepm.DevEpmWcLoader d:\ptc\Windchill_10.2\Windchill\loadFiles\ket\part_mater\Migration Data_MATER.xlsx
    public static void main(String[] args) {

	try {
	    
            String filePath = null;
            
            if (args == null || args.length < 1)
            	throw new Exception("@@ args need !");
            else{
            	filePath = args[0];
            }
		    
	    String toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(DevEpmWcLoader.class, "@start:" + toDayTime);
	    DevEpmWcLoader.manager.saveFromExcel(filePath);
	    
	    toDayTime = "";
	    try {
		
	       SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd:HH-mm-ss" );
	       toDayTime = sdFormat.format ( Calendar.getInstance ().getTime () );
	       
	    } catch (Exception e) {
	       Kogger.error ( "Exception : " + e.getMessage () );
	    }
	    
	    Kogger.debug(DevEpmWcLoader.class, "@end:" + toDayTime);

	} catch (Exception e) {
	    Kogger.debug(DevEpmWcLoader.class, e);
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
	 
	try {

	    SessionHelper.manager.setAdministrator();
		
	    Kogger.debug(getClass(), "## args :" + filePath);
	    
	    DevEpmNoLoader loader = new DevEpmNoLoader();
	    loader.load(filePath);
	    List<Map> list = loader.getList();
	    
	    // 먼저 삭제
//	    List<WTPart> delList = find();
//	    for(WTPart del : delList){
//		trx = new Transaction();
//		trx.start();
//		
//		PartDeleteUtil.deleteWTPartMast(del);
//		
//		trx.commit();
//		trx = null;
//	    }
	    
	    for( Map map : list ){
		
		trx = new Transaction();
		trx.start();
		    
		String partNo = (String)map.get(DevEpmNoLoader.PART_NO);
		String partName = (String)map.get(DevEpmNoLoader.PART_NAME);
		String regUser = (String)map.get(DevEpmNoLoader.REG_USER);
		String regDate = (String)map.get(DevEpmNoLoader.REG_DATE);
		String epm2D = (String)map.get(DevEpmNoLoader.EPM_2D);
		String epm3D = (String)map.get(DevEpmNoLoader.EPM_3D);
		
		Kogger.debug(getClass(), "### partNo:" + partNo);
		partNo = partNo.trim();
		
		WTPartMaster existedMaster = PartBaseHelper.service.getMaster(partNo);
		WTPart wtPart = null;
		if(existedMaster != null){
		    wtPart = PartBaseHelper.service.getLatestPart(existedMaster);
		    
		}else{
		
		    PartBaseDTO dto = new PartBaseDTO();
		    // 분류체계
		    KETPartClassification claz = PartClazHelper.service.getPartClassificationByClazCode("U");
		    dto.setPartClazOid(CommonUtil.getOIDString(claz));
		    dto.setPartNumber(partNo);
		    dto.setPartName(partName);
		    // 기본단위
		    dto.setPartDefaultUnit("KET_EA");
		    // 개발단계
		    dto.setSpPartDevLevel("PC003");
		    // 부품유형
		    dto.setSpPartType("O");
		    // 부품 저장
		    wtPart = PartBaseHelper.service.createWTPartByHalb(dto);
		    wtPart = (WTPart) PersistenceHelper.manager.refresh(wtPart);
		    
		    // 마스터 링크
		    if (epm2D != null && !"".equals(epm2D)) {
			EPMDocument epmDocument = PartBaseHelper.service.getLatestEPM(epm2D.toUpperCase());
			if(epmDocument != null){
			    PartEpmRelation ae = new PartEpmRelation();
			    ae.associateCreate(wtPart, epmDocument, false, 1, "RELATED_DRAWING", false);
			}
		    }

		    if (epm3D != null && !"".equals(epm3D)) {
			EPMDocument epmDocument = PartBaseHelper.service.getLatestEPM(epm3D.toUpperCase());
			if(epmDocument != null){
			    PartEpmRelation ae = new PartEpmRelation();
			    ae.associateCreate(wtPart, epmDocument, false, 1, "RELATED_MODEL", false);
			}
		    }
		    
		}
		
		if(StringUtils.isNotEmpty(regDate)){
		    
		    WTPartMaster master = (WTPartMaster)wtPart.getMaster();
//		    master.getPersistInfo().setCreateStamp(DateUtil.convertStartDate2(regDate));
//		    master.getPersistInfo().setModifyStamp(DateUtil.convertStartDate2(regDate));
//		    master.getPersistInfo().setUpdateStamp(DateUtil.convertStartDate2(regDate));
//		    PersistenceServerHelper.manager.update(master);
//		    
//		    wtPart.getPersistInfo().setCreateStamp(DateUtil.convertStartDate2(regDate));
//		    wtPart.getPersistInfo().setModifyStamp(DateUtil.convertStartDate2(regDate));
//		    wtPart.getPersistInfo().setUpdateStamp(DateUtil.convertStartDate2(regDate));
//		    PersistenceServerHelper.manager.update(wtPart);
		    
		    updateUserDate(master, regDate, "WTPartMaster");
		    updateUserDate(wtPart, regDate, "WTPart");
		}
		
		trx.commit();
		trx = null;
		
		List<String> userList = getUserNameList(regUser);
		if(userList != null && userList.size() > 0){
		    updateUserInfo( wtPart, userList.get(0) );
		}
	    }
		
	} catch (Exception e) {
	    
	    Kogger.debug(getClass(), "## 정의되지 않은 에러가 발생했습니다.");
	    Kogger.error(getClass(), e);
	    throw e;
	    
	} finally {
	    
	    if(trx != null)
   		trx.rollback();
	    
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    // 요청서 찾기
    private List<WTPart> find() throws Exception {

	QuerySpec query = getQuery();
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<WTPart> list = new ArrayList<WTPart>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((WTPart) objArr[0]);
	}
	return list;
    }
    
    // 해당부품 찾기 쿼리
    private QuerySpec getQuery() throws Exception {

	Kogger.debug(getClass(), "## WTPart ID : CP");

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;

	int idx = query.appendClassList(WTPart.class, true);
	query.appendWhere(new SearchCondition(WTPart.class, RevisionControlled.LATEST_ITERATION, "TRUE"), idx);
	if (query.getConditionCount() > 0) {
	    query.appendAnd();
	}
	sc = new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.LIKE, "CP%", false);
	query.appendWhere(sc, new int[] { idx });

	return query;
    }
	
    private List<String> getUserNameList(String userName) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> retList = new ArrayList<String>();

	try {

	    StringBuffer sb = new StringBuffer();
	    sb.append(" select ida3b4 as OID from people where name = ? \n");

	    String query = sb.toString();
	    List aBindList = new ArrayList();
	    aBindList.add(userName);

	    retList = oDaoManager.queryForList(query, aBindList, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {

		    String oid = rs.getString("OID");
		    return oid;
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return retList;
    }
    
    private void updateUserInfo(WTPart wtPart, String userId) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" update wtpart set IDA3D2ITERATIONINFO = ?, IDA3B2ITERATIONINFO = ? \n");
	    buffer.append(" where ida2a2 = ? \n");
	    aBind.add(userId);
	    aBind.add(userId);
	    aBind.add(CommonUtil.getOIDLongValue2Str(wtPart));
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind, true);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    private void updateUserDate(Persistable pers, String userDate, String tableName) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" update " + tableName + " set CREATESTAMPA2 = to_date( ?, 'yyyy-MM-dd' )+9/24 \n");
	    buffer.append(" ,MODIFYSTAMPA2 = to_date( ?, 'yyyy-MM-dd' )+9/24 \n");
	    buffer.append(" ,UPDATESTAMPA2 = to_date( ?, 'yyyy-MM-dd' )+9/24 \n");
	    buffer.append(" where ida2a2 = ? \n");
	    
	    aBind.add(userDate);
	    aBind.add(userDate);
	    aBind.add(userDate);
	    aBind.add(CommonUtil.getOIDLongValue2Str(pers));
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind, true);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    

}
