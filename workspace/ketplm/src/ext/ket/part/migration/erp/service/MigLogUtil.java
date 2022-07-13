package ext.ket.part.migration.erp.service;

import java.util.ArrayList;
import java.util.List;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.vc.Iterated;
import wt.vc.VersionReference;
import e3ps.common.util.CommonUtil;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.shared.log.Kogger;

final public class MigLogUtil {

    public void log(String type, WTPartMaster wtpartMast,
	    String logCode, String logName, String logShort, String logLong) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" INSERT INTO MIG_LOG (MIG_TYPE, MIG_SRC_NO, MIG_SRC_REV, MIG_SRC_ITER \n");
	    buffer.append(" , MIG_SRC_MAST_OID, MIG_SRC_REV_OID, MIG_SRC_ITER_OID \n");
	    buffer.append(" , MIG_SRC_LOG_CODE, MIG_SRC_LOG_NAME, MIG_SRC_LOG_SHORT, MIG_SRC_LOG_LONG, MIG_DATE ) \n");
	    buffer.append(" VALUES ( ?,?,?,?,?,?,?,?,?,?,?,SYSDATE) \n");
	    aBind.add(type);
	    
	    if(wtpartMast == null){
		
		aBind.add("");
		aBind.add("");
		aBind.add("");
		
		aBind.add("0");
		aBind.add("0");
		aBind.add("0");
		
	    }else{
		
        	aBind.add(wtpartMast.getNumber());
        	aBind.add("");
        	aBind.add("");
        	
        	aBind.add(CommonUtil.getOIDLongValue(wtpartMast));
        	aBind.add("0");
        	aBind.add("0");
	    }
	    
	    aBind.add(logCode==null?"":logCode);
	    aBind.add(logName==null?"":logName);
	    aBind.add(logShort==null?"":logShort);
	    aBind.add(logLong==null?"":logLong);
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    public void log(String type, WTPart wtpart,
	    String logCode, String logName, String logShort, String logLong) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" INSERT INTO MIG_LOG (MIG_TYPE, MIG_SRC_NO, MIG_SRC_REV, MIG_SRC_ITER \n");
	    buffer.append(" , MIG_SRC_MAST_OID, MIG_SRC_REV_OID, MIG_SRC_ITER_OID \n");
	    buffer.append(" , MIG_SRC_LOG_CODE, MIG_SRC_LOG_NAME, MIG_SRC_LOG_SHORT, MIG_SRC_LOG_LONG, MIG_DATE ) \n");
	    buffer.append(" VALUES ( ?,?,?,?,?,?,?,?,?,?,?,SYSDATE) \n");
	    aBind.add(type);
	    
	    if(wtpart == null){
		
		aBind.add("");
		aBind.add("");
		aBind.add("");
		
		aBind.add("0");
		aBind.add("0");
		aBind.add("0");
		
	    }else{
		
        	aBind.add(wtpart.getNumber());
        	aBind.add(wtpart.getVersionIdentifier().getValue());
        	aBind.add(wtpart.getIterationIdentifier().getValue());
        	
        	aBind.add(CommonUtil.getOIDLongValue((WTPartMaster)wtpart.getMaster()));
        	String revOid = VersionReference.newVersionReference((Iterated) wtpart).toString();
        	
        	if(revOid.indexOf(":") != -1)
        	    revOid = revOid.substring(revOid.lastIndexOf(":") + 1);
        	else if(revOid.indexOf(">") != -1)
        	    revOid = revOid.substring(revOid.lastIndexOf(">") + 1);
        	    
        	aBind.add(revOid);
        	aBind.add(CommonUtil.getOIDLongValue(wtpart));
	    }
	    
	    aBind.add(logCode==null?"":logCode);
	    aBind.add(logName==null?"":logName);
	    aBind.add(logShort==null?"":logShort);
	    aBind.add(logLong==null?"":logLong);
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    public void log(String type, EPMDocument epmDoc,
	    String logCode, String logName, String logShort, String logLong) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" INSERT INTO MIG_LOG (MIG_TYPE, MIG_SRC_NO, MIG_SRC_REV, MIG_SRC_ITER \n");
	    buffer.append(" , MIG_SRC_MAST_OID, MIG_SRC_REV_OID, MIG_SRC_ITER_OID \n");
	    buffer.append(" , MIG_SRC_LOG_CODE, MIG_SRC_LOG_NAME, MIG_SRC_LOG_SHORT, MIG_SRC_LOG_LONG, MIG_DATE ) \n");
	    buffer.append(" VALUES ( ?,?,?,?,?,?,?,?,?,?,?,SYSDATE) \n");
	    aBind.add(type);
	    
	    if(epmDoc == null){
		
		aBind.add("");
		aBind.add("");
		aBind.add("");
		
		aBind.add("");
		aBind.add("");
		aBind.add("");
		
	    }else{
		
        	aBind.add(epmDoc.getNumber());
        	aBind.add(epmDoc.getVersionIdentifier().getValue());
        	aBind.add(epmDoc.getIterationIdentifier().getValue());
        	
        	aBind.add(CommonUtil.getOIDLongValue((EPMDocumentMaster)epmDoc.getMaster()));
        	String revOid = VersionReference.newVersionReference((Iterated) epmDoc).toString();
        	
        	if(revOid.indexOf(":") != -1)
        	    revOid = revOid.substring(revOid.lastIndexOf(":") + 1);
        	else if(revOid.indexOf(">") != -1)
        	    revOid = revOid.substring(revOid.lastIndexOf(">") + 1);
        	
        	aBind.add(revOid);
        	aBind.add(CommonUtil.getOIDLongValue(epmDoc));
	    }
	    
	    aBind.add(logCode==null?"":logCode);
	    aBind.add(logName==null?"":logName);
	    aBind.add(logShort==null?"":logShort);
	    aBind.add(logLong==null?"":logLong);
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
}
