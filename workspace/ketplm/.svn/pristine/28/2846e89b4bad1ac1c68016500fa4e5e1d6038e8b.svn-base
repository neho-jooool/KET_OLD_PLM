package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class MigPartClazConnector implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigPartClazConnector manager = new MigPartClazConnector();

    public MigPartClazConnector() {

    }

    // windchill ext.ket.part.migration.base.MigPartClazConnector
    public static void main(String[] args) {

	try {

	    Kogger.debug(MigPartClazConnector.class, "@start");
	    MigPartClazConnector.manager.saveFromExcel();
	    Kogger.debug(MigPartClazConnector.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigPartClazConnector.class, e);
	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

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
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();
	    
	    KETPartClassification claz1 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950023").getObject();  // 차-Connector (저전압)/Female HSG/W to W               
	    KETPartClassification claz2 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897961635").getObject();  // 차 Connector (고전압)/Female HSG                      
	    KETPartClassification claz3 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950026").getObject();  // 제품 ? Terminal/Ring                                  
	    KETPartClassification claz4 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950027").getObject();  // 제품 ? 자동차모듈/자동차모듈 ASSY/J/B(JUNCTION BOX    
	    KETPartClassification claz5 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950028").getObject();  // 제품 ? 전장모듈/전장모듈 ASSY/ICB/Sensing Module      
	    KETPartClassification claz6 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950029").getObject();  // 제품 ? IT모듈/GNSS                                    
	    KETPartClassification claz7 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950030").getObject();  // 제품 - W/H                                            
	    KETPartClassification claz8 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950031").getObject();  // 제품? 표준구매품/PCB 구매품                           
	    KETPartClassification claz9 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950032").getObject();  // 제품? 표준구매품/전자소자류                           
	    KETPartClassification claz10 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950033").getObject(); // 제품 ? 생활가전 커넥터/커넥터류/Female                
	    KETPartClassification claz11 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950034").getObject(); // 제품 ? Mobile/Mobile/Card Socket                      
	    KETPartClassification claz12 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950035").getObject(); // 제품 ? Display/FPC/FFC/Recep                          
	    KETPartClassification claz13 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950036").getObject(); // 제품 ? 커넥터류(전장파인피치그룹)/B-to-B/REC ASSY     
	    KETPartClassification claz14 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950037").getObject(); // 제품 ? 커넥터류(전장멀티그룹)/LVDS Socket/Socket      
	    KETPartClassification claz15 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950038").getObject(); // 금형? 금형(SET)/프레스/자동차/생활가전                
	    KETPartClassification claz16 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897950039").getObject(); // 금형? 금형부품                                        
	    KETPartClassification claz17 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897961516").getObject(); // 기타(원재료/포장)                                     
	    KETPartClassification claz18 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:897961517").getObject(); // Test-Else                                             

//	    KETPartClassification claz1 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446407").getObject();  // 차-Connector (저전압)/Female HSG/W to W               
//	    KETPartClassification claz2 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446409").getObject();  // 차 Connector (고전압)/Female HSG                      
//	    KETPartClassification claz3 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446410").getObject();  // 제품 ? Terminal/Ring                                  
//	    KETPartClassification claz4 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446411").getObject();  // 제품 ? 자동차모듈/자동차모듈 ASSY/J/B(JUNCTION BOX   
//	    
//	    KETPartClassification claz5 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446412").getObject();  // 제품 ? 전장모듈/전장모듈 ASSY/ICB/Sensing Module      
//	    KETPartClassification claz6 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446413").getObject();  // 제품 ? IT모듈/GNSS                                    
//	    KETPartClassification claz7 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446414").getObject();  // 제품 - W/H                                            
//	    KETPartClassification claz8 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446415").getObject();  // 제품? 표준구매품/PCB 구매품     
//	    
//	    KETPartClassification claz9 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446416").getObject();  // 제품? 표준구매품/전자소자류                           
//	    KETPartClassification claz10 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446417").getObject(); // 제품 ? 생활가전 커넥터/커넥터류/Female                
//	    KETPartClassification claz11 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446418").getObject(); // 제품 ? Mobile/Mobile/Card Socket                      
//	    KETPartClassification claz12 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446419").getObject(); // 제품 ? Display/FPC/FFC/Recep                          
//	    KETPartClassification claz13 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446421").getObject(); // 제품 ? 커넥터류(전장파인피치그룹)/B-to-B/REC ASSY     
//	    KETPartClassification claz14 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446422").getObject(); // 제품 ? 커넥터류(전장멀티그룹)/LVDS Socket/Socket      
//	    
//	    KETPartClassification claz15 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446405").getObject(); // 금형? 금형(SET)/프레스/자동차/생활가전                
//	    KETPartClassification claz16 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446501").getObject(); // 금형? 금형부품                                        
//	    KETPartClassification claz17 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446423").getObject(); // 기타(원재료/포장)                                     
//	    KETPartClassification claz18 = (KETPartClassification) rf.getReference("ext.ket.part.entity.KETPartClassification:899446404").getObject(); // Test-Else                                             
	    
//	    ext.ket.part.entity.KETPartClassification:899446407	1	Female HSG
//	    ext.ket.part.entity.KETPartClassification:899446409	1	W to W
//	    ext.ket.part.entity.KETPartClassification:899446410	1	Clip & Holder
//	    ext.ket.part.entity.KETPartClassification:899446411	1	Female HSG Assy
	    
//	    ext.ket.part.entity.KETPartClassification:899446412	1	기타 Assy
//	    ext.ket.part.entity.KETPartClassification:899446413	1	Connector(고전압)
//	    ext.ket.part.entity.KETPartClassification:899446414	1	Terminal
//	    ext.ket.part.entity.KETPartClassification:899446415	1	자동차모듈
	    
//	    ext.ket.part.entity.KETPartClassification:899446416	1	전장모듈
//	    ext.ket.part.entity.KETPartClassification:899446417	1	W/H
//	    ext.ket.part.entity.KETPartClassification:899446418	1	생활가전 커넥터
//	    ext.ket.part.entity.KETPartClassification:899446419	1	Mobile
//	    ext.ket.part.entity.KETPartClassification:899446420	1	Display
//	    ext.ket.part.entity.KETPartClassification:899446421	1	Connector(전장파인피치그룹)
//	    ext.ket.part.entity.KETPartClassification:899446422	1	Connector(전장멀티그룹)
	    
//	    ext.ket.part.entity.KETPartClassification:899446423	1	원자재
//	    ext.ket.part.entity.KETPartClassification:899446404	1	기타
//	    ext.ket.part.entity.KETPartClassification:899446405	1	금형Set
//	    ext.ket.part.entity.KETPartClassification:899446501	1	금형부품
	    
//	    ext.ket.part.entity.KETPartClassification:897950023	1	65	차-Connector (저전압)/Female HSG/W to W
//	    ext.ket.part.entity.KETPartClassification:897961635	1	61	차 Connector (고전압)/Female HSG
//	    ext.ket.part.entity.KETPartClassification:897950026	1	71	제품 ? Terminal/Ring
//	    ext.ket.part.entity.KETPartClassification:897950027	1	52	제품 ? 자동차모듈/자동차모듈 ASSY/J/B(JUNCTION BOX
//	    ext.ket.part.entity.KETPartClassification:897950028	1	45	제품 ? 전장모듈/전장모듈 ASSY/ICB/Sensing Module
//	    ext.ket.part.entity.KETPartClassification:897950029	1	42	제품 ? IT모듈/GNSS
//	    ext.ket.part.entity.KETPartClassification:897950030	1	31	제품 - W/H
//	    ext.ket.part.entity.KETPartClassification:897950031	1	44	제품? 표준구매품/PCB 구매품
//	    ext.ket.part.entity.KETPartClassification:897950032	1	44	제품? 표준구매품/전자소자류
//	    ext.ket.part.entity.KETPartClassification:897950033	1	61	제품 ? 생활가전 커넥터/커넥터류/Female
//	    ext.ket.part.entity.KETPartClassification:897950034	1	K50	제품 ? Mobile/Mobile/Card Socket
//	    ext.ket.part.entity.KETPartClassification:897950035	1	K50	제품 ? Display/FPC/FFC/Recep
//	    ext.ket.part.entity.KETPartClassification:897950036	1	64	제품 ? 커넥터류(전장파인피치그룹)/B-to-B/REC ASSY
//	    ext.ket.part.entity.KETPartClassification:897950037	1	64	제품 ? 커넥터류(전장멀티그룹)/LVDS Socket/Socket
//	    ext.ket.part.entity.KETPartClassification:897950038	1	1	금형? 금형(SET)/프레스/자동차/생활가전
//	    ext.ket.part.entity.KETPartClassification:897950039	1		금형? 금형부품
//	    ext.ket.part.entity.KETPartClassification:897961516	1	R11	기타(원재료/포장)
//	    ext.ket.part.entity.KETPartClassification:897961517	1		Test-Else
	    
	    int pageCount = 100;
	    for (int k = 1; k < 4590; k++) {

		trx = new Transaction();
		trx.start();

		List<String> oidList = searchStringValSomeList(100, k);

		for (String oid : oidList) {
		    WTPartMaster wtPartMast = (WTPartMaster) rf.getReference(oid).getObject();
		    
		    KETPartClassification classific = null;
		    String partNo = wtPartMast.getNumber();
		    // 'H','S','R' 'T'/'W'  'K'/'P' 'F'/'4'~8
		    // WHERE PTC_STR_3TYPEINFOWTPART NOT IN ('F',,'W','K','D','M');
		    if(partNo.startsWith("R")){
			classific = claz17;
		    }else if(partNo.startsWith("65") || partNo.startsWith("H65") ){
			classific = claz1;
		    }else if(partNo.startsWith("61") || partNo.startsWith("H61") ){
			if(k%1 == 0) classific = claz2;
			else classific = claz10;
		    }else if(partNo.startsWith("71") || partNo.startsWith("H71") ){
			classific = claz3;
		    }else if(partNo.startsWith("52") || partNo.startsWith("H52") ){
			classific = claz4;
		    }else if(partNo.startsWith("45") || partNo.startsWith("H45") ){
			classific = claz5;
		    }else if(partNo.startsWith("42") || partNo.startsWith("H42") ){
			classific = claz6;
		    }else if(partNo.startsWith("31") || partNo.startsWith("H31") ){
			classific = claz7;
		    }else if(partNo.startsWith("44") || partNo.startsWith("H44") ){
			if(k%1 == 0) classific = claz8;
			else classific = claz9;
		    }else if(partNo.startsWith("K50") || partNo.startsWith("HK50") ){
			    if(k%1 == 0) classific = claz11;
				else classific = claz12;
		    }else if(partNo.startsWith("64") || partNo.startsWith("H64") ){
			if(k%1 == 0) classific = claz13;
			else classific = claz14;
		    }else if(partNo.startsWith("K")){
			classific = claz11;
		    }else{
			String type = getType(wtPartMast);
			if("D".equals(type)){
			    classific = claz15;
			}else if("M".equals(type)){
			    classific = claz16;
			}else if("F".equals(type)||"H".equals(type)){
			    if(partNo.startsWith("6") || partNo.startsWith("H6") ){
				classific = claz1;
			    }else if(partNo.startsWith("4") || partNo.startsWith("H4") ){
				classific = claz8;
			    }else if(partNo.startsWith("5") || partNo.startsWith("H5") ){
				classific = claz4;
			    }else if(partNo.startsWith("7") || partNo.startsWith("H7") ){
				classific = claz3;
			    }else if(partNo.startsWith("8") || partNo.startsWith("H8") ){
				classific = claz3;
			    }else{
				classific = claz1;
			    }
			}else if("W".equals(type)){
			    classific = claz18;
			}else if("K".equals(type)){
			    classific = claz17;
			}else{
			    classific = claz1;
			}
		    }
		    
		    if(classific != null){
			KETPartClassLink clazLink = KETPartClassLink.newKETPartClassLink(classific, wtPartMast);
			clazLink = (KETPartClassLink) PersistenceHelper.manager.save(clazLink);
		    }
		}

		trx.commit();
		oidList = null;
	    }

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
    
    private String getStringValue(int pageCount, int pageNo) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" select oid \n");
	sb.append(" from ( \n");
	sb.append("     select FLOOR((ROWNUM - 1)/" + pageCount + " + 1) page, A.OID \n");
	sb.append("     from ( \n");
	sb.append("         select M.CLASSNAMEA2A2||':'||M.IDA2A2 OID from WTPARTMASTER M, KETPartClassLink CL WHERE M.IDA2A2 = CL.IDA3B5(+) AND CL.IDA3B5 IS NULL \n");
	sb.append("     ) A         \n");
	sb.append(" )     \n");
	sb.append(" where page = ? \n");

	return sb.toString();
    }

    private List<String> searchStringValSomeList(int pageCount, int pageNo) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> oidList = new ArrayList<String>();

	try {

	    String query = getStringValue(pageCount, pageNo);
	    List<String> alBindSql = new ArrayList<String>();
	    alBindSql.add(String.valueOf(pageNo));

	    oidList = oDaoManager.queryForList(query, alBindSql, new StampRowSetStrategy<String>() {
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

	return oidList;
    }
    
    private String getType(WTPartMaster wtpartMaster) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	String oidList = StringUtils.EMPTY;
	
	try {
	    
		StringBuffer sb = new StringBuffer();
		sb.append(" select PTC_STR_3TYPEINFOWTPART type from wtpart where ida3masterreference = ? \n");
		
	    String query = sb.toString();
	    List<String> alBindSql = new ArrayList<String>();
	    alBindSql.add( CommonUtil.getOIDLongValue2Str( wtpartMaster ));
	    
	    oidList = oDaoManager.queryForObject(query, alBindSql, new StampRowSetStrategy<String>() {
		@Override
		public String mapRow(ResultSet rs) throws SQLException {
		    String oid = rs.getString("type");
		    return oid;
		}
	    });
	    
	} catch (Exception e) {
	    throw e;
	} finally {
	    
	}
	
	return oidList;
    }

}
