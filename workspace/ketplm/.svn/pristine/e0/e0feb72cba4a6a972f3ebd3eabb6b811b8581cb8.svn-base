package ext.ket.edm.stamping.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.structure.EPMMemberLink;
import wt.epm.structure.EPMStructureHelper;
import wt.epm.workspaces.EPMAsStoredConfigSpec;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.config.ConfigSpec;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class ModelStrucUtil {

    public List<EPMDocument> getModel(String cad3Oid) throws Exception {

	ReferenceFactory rf = new ReferenceFactory();
	EPMDocument epm3d = (EPMDocument) rf.getReference(cad3Oid).getObject();

	return getModel(epm3d);
    }

    public List<EPMDocument> getModel(EPMDocument epm3d) throws Exception {

	List<EPMDocument> epmDocList = new ArrayList<EPMDocument>();
	epmDocList.add(epm3d);
	getModelStructure(epm3d, epmDocList);

	return epmDocList;
    }

    public void getModelStructure(EPMDocument epm3d, List<EPMDocument> epmDocList) throws Exception {

	ConfigSpec asStoredSpec = EPMAsStoredConfigSpec.newEPMAsStoredConfigSpec((EPMDocument) epm3d);

	QueryResult result = EPMStructureHelper.service.navigateUsesToIteration(epm3d, null, false, asStoredSpec);

	if (result.size() > 0) {

	    while (result.hasMoreElements()) {

		Persistable[] persist = (Persistable[]) result.nextElement();
		EPMMemberLink member = null;
		EPMDocument modelDoc = null;

		member = (EPMMemberLink) persist[0];
		modelDoc = (EPMDocument) persist[1];

		if (!member.isRequired())
		    continue;

		epmDocList.add(modelDoc);

		Kogger.debug(getClass(), " | " + modelDoc.getNumber() + " | " + modelDoc.getVersionIdentifier().getValue() + "." + modelDoc.getIterationIdentifier().getValue() + " | " + modelDoc.isLatestIteration()
		        + " | " + modelDoc.isLatestIteration() + " | Fi");

		getModelStructure(modelDoc, epmDocList);
	    }
	}
    }

    public List<String> searchAsStoredModelList(String cad2dOid) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<String> modelAsStoredList = new ArrayList<String>();

	try {

	    String cad2dIda2a2 = cad2dOid.substring(cad2dOid.lastIndexOf(":") + 1);
	    String query = getSearchAsStoredModelQuery();
	    List<String> paramList = new ArrayList<String>();
	    paramList.add(cad2dIda2a2);
	    paramList.add(cad2dIda2a2);

	    modelAsStoredList = oDaoManager.queryForList(query, paramList, new RowSetStrategy<String>() {
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

	return modelAsStoredList;
    }

    private String getSearchAsStoredModelQuery() throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT A.CLASSNAMEKEYROLEBOBJECTREF||':'||A.IDA3B5 OID \n");
	sb.append(" FROM EPMASSTOREDMEMBER A \n");
	sb.append(" WHERE IDA3A5 IN \n");
	sb.append(" ( \n");
	sb.append("     SELECT IDA3A5 FROM EPMASSTOREDMEMBER \n");
	sb.append("     WHERE 1=1 \n");
	sb.append("     AND IDA3B5 = ? \n");
	sb.append(" ) \n");
	sb.append(" AND IDA3B5 IN \n");
	sb.append(" ( \n");
	sb.append("     SELECT E.IDA2A2 \n");
	sb.append("     FROM EPMREFERENCELINK L, EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	sb.append("     WHERE M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	sb.append("     AND L.IDA3B5 = M.IDA2A2 \n");
	sb.append("     AND L.REFERENCETYPE = 'DRAWING' \n");
	sb.append("     AND IDA3A5 = ? \n");
	sb.append(" ) \n");

	return sb.toString();
    }
    
//    -- 3D 조회
//    select EM.DOCUMENTNUMBER
//    from epmdocumentmaster em
//    where 1=1
//    and EM.AUTHORINGAPPLICATION = 'PROE'
//    and doctype in ('CADASSEMBLY' ,'CADCOMPONENT')
//    and EM.DOCUMENTNUMBER like '%_3D'
//    and em.ida2a2 in
//    ( select ida3b5 from EPMREFERENCELINK l where L.REFERENCETYPE = 'DRAWING' )
    
//    -- 2D 조회
//    select EM.DOCUMENTNUMBER
//    from epmdocumentmaster em, epmdocument e
//    where 1=1
//    and em.ida2a2 = E.IDA3MASTERREFERENCE
//    and EM.AUTHORINGAPPLICATION = 'PROE' 
//    and doctype in ('CADDRAWING')
//    --and EM.DOCUMENTNUMBER like '%_3D'
//    and e.ida2a2 in
//    ( select ida3a5 from EPMREFERENCELINK l where L.REFERENCETYPE = 'DRAWING' )
    
    // PRO|E 관련된 부분만 검색한다.
    public EPMDocument getLatest2DBy3D(String epm3dMastOid, String epm3DNo) throws Exception {

	EPMDocument epm2dFrom3d = null;
	ReferenceFactory rf = new ReferenceFactory();

	List<Map> cad2list = get2DOidList(epm3dMastOid);
	if (cad2list.size() == 0) {
	    
	} else if (cad2list.size() == 1) {
	    
	    Map map = cad2list.get(0);
	    String vrEpm = "VR:wt.epm.EPMDocument:"  +(String) map.get("BRANCHIDITERATIONINFO");
	    epm2dFrom3d = (EPMDocument) rf.getReference(vrEpm).getObject();
	    epm2dFrom3d = (EPMDocument) VersionControlHelper.getLatestIteration(epm2dFrom3d, false);
	    
	} else {
	    
	    for (Map map : cad2list) {
		String vrEpm = "VR:wt.epm.EPMDocument:"  +(String) map.get("BRANCHIDITERATIONINFO");
		EPMDocument epmDoc = (EPMDocument) rf.getReference(vrEpm).getObject();
		epmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(epmDoc, false);
		if (isSame2DNumberWithPart(epm3DNo, epmDoc.getNumber())) {
		    epm2dFrom3d = epmDoc;
		    break;
		}
	    }
	}

	return epm2dFrom3d;
    }
    
    // PRO|E 관련 3D에 붙은 DRW 2D 찾아서 존재하는지만 체크 한다. - CAD2BOM 사용
    public boolean has2DBy3D(String epm3dMastOid, String epm3DNo) throws Exception {

	boolean ret = false;
	ReferenceFactory rf = new ReferenceFactory();

	List<Map> cad2list = get2DOidList(epm3dMastOid);
	if (cad2list.size() == 0) {
	    ret = false;
	} else if (cad2list.size() == 1) {
	    ret = true;
	} else {
	    for (Map map : cad2list) {
		String vrEpm = "VR:wt.epm.EPMDocument:"  +(String) map.get("BRANCHIDITERATIONINFO");
		EPMDocument epmDoc = (EPMDocument) rf.getReference(vrEpm).getObject();
		epmDoc = (EPMDocument) VersionControlHelper.getLatestIteration(epmDoc, false);
		if (isSame2DNumberWithPart(epm3DNo, epmDoc.getNumber())) {
		    ret = true;
		    break;
		}
	    }
	}

	return ret;
    }
    
    private boolean isSame2DNumberWithPart(String _cad3DNumber, String _cad2DNumber) {

	String cad3DNumber = _cad3DNumber;
	String cad2DNumber = _cad2DNumber;
	
	if (cad3DNumber.endsWith("_3D") || cad3DNumber.endsWith("_2D")) {
	    cad3DNumber = cad3DNumber.substring(0, cad3DNumber.length() - 3);
	}
	
	if (cad2DNumber.endsWith("_3D") || cad2DNumber.endsWith("_2D")) {
	    cad2DNumber = cad2DNumber.substring(0, cad2DNumber.length() - 3);
	}
	
	return cad3DNumber.equalsIgnoreCase(cad2DNumber);
    }
    
    //migration 정보 있는지 체크
    private List<Map> get2DOidList(String _epm3dMastOid) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List alBindSql = new ArrayList();
	String sSql = null;
	List<Map> cad2Info = new ArrayList<Map>();
	
	try {
	    
	    String epm3dMastOid = "";
	    if(_epm3dMastOid.indexOf(":") != -1){
		epm3dMastOid = _epm3dMastOid.substring(_epm3dMastOid.lastIndexOf(":")+1);
	    }else{
		epm3dMastOid = _epm3dMastOid;
	    }

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT M.IDA2A2, M.DOCUMENTNUMBER, MAX(E.BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO \n");
	    sb.append(" FROM EPMREFERENCELINK L, EPMDOCUMENT E, EPMDOCUMENTMASTER M \n");
	    sb.append(" WHERE L.IDA3B5 = ? \n");
	    sb.append(" AND L.REFERENCETYPE = 'DRAWING' \n"); 
	    sb.append(" AND L.IDA3A5 = E.IDA2A2 \n"); 
	    sb.append(" AND M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    sb.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    sb.append(" GROUP BY M.IDA2A2, M.DOCUMENTNUMBER \n");

//	    if (withWorking) {
//		sb.append(" AND E.STATECHECKOUTINFO != 'c/o' \n");
//	    } else {
//		sb.append(" AND E.STATECHECKOUTINFO != 'wrk' \n");
//	    }
	    
	    sSql = sb.toString();

	    alBindSql.add(epm3dMastOid);

	    Kogger.debug(getClass(), "get2DOidList : " + sSql);

	    List<Map> listMap = oDaoManager.queryForList(sSql, alBindSql, new RowSetStrategy<Map>() {
		@Override
		public Map mapRow(ResultSet oResultSet) throws SQLException {
		    Map cad2dInfo = new HashMap();
		    cad2dInfo.put("IDA2A2", oResultSet.getString("IDA2A2"));
		    cad2dInfo.put("DOCUMENTNUMBER", oResultSet.getString("DOCUMENTNUMBER"));
		    cad2dInfo.put("BRANCHIDITERATIONINFO", oResultSet.getString("BRANCHIDITERATIONINFO"));
		    return cad2dInfo;
		}
	    });

	    if (listMap != null)
		cad2Info = listMap;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return cad2Info;
    }
    
    private List<Map> get2DOidListWithState(String epm3dMastOid, String revision, String stateState) throws WTException {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List alBindSql = new ArrayList();
	String sSql = null;
	List<Map> cad2Info = new ArrayList<Map>();

	try {

		sSql    = "\n SELECT DISTINCT M.DOCUMENTNUMBER, E.VERSIONIDA2VERSIONINFO REVISION, E.CLASSNAMEA2A2 || ':'|| E.IDA2A2 OID "
			+ "\n FROM EPMREFERENCELINK L, EPMDOCUMENT E, EPMDOCUMENTMASTER M "
			+ "\n WHERE L.IDA3B5 = ? "
			+ "\n AND L.REFERENCETYPE = 'DRAWING' "
			+ "\n AND L.IDA3A5 = E.IDA2A2 "
			+ "\n AND M.IDA2A2 = E.IDA3MASTERREFERENCE "
			+ "\n AND E.VERSIONIDA2VERSIONINFO = ? "
			+ "\n AND E.LATESTITERATIONINFO = 1 "
			+ "\n AND E.STATESTATE = ? "
			+ "\n ";

	    alBindSql.add(epm3dMastOid);
	    alBindSql.add(revision);
	    alBindSql.add(stateState);

	    Kogger.debug(getClass(), "get2DOidListWithState : " + sSql);

	    List<Map> listMap = oDaoManager.queryForList(sSql, alBindSql, new RowSetStrategy<Map>() {

		@Override
                public Map mapRow(ResultSet oResultSet) throws SQLException {
		    Map cad2dInfo = new HashMap();
		    cad2dInfo.put("NUMBER", oResultSet.getString("DOCUMENTNUMBER"));
		    cad2dInfo.put("REVISION", oResultSet.getString("REVISION"));
		    cad2dInfo.put("OID", oResultSet.getString("OID"));
		    return cad2dInfo;
                }

	    });
	    
	    if (listMap != null)
		cad2Info = listMap;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return cad2Info;
    }
    
    public EPMDocument getLatest3DBy2D(String epm2dOid, String epm2DNo) throws Exception {

	EPMDocument epm3dFrom2d = null;
	ReferenceFactory rf = new ReferenceFactory();

	List<Map> cad3list = get3DMasterOidList(epm2dOid);
	if (cad3list.size() == 0) {
	    
	} else if (cad3list.size() == 1) {
	    
	    Map map = cad3list.get(0);
	    String epm = (String) map.get("OID");
	    EPMDocumentMaster cad3dMaster = (EPMDocumentMaster) rf.getReference(epm).getObject();
	    epm3dFrom2d = PartBaseHelper.service.getLatestEPM(cad3dMaster);
	    
	} else {
	    
	    for (Map map : cad3list) {
		String epm = (String) map.get("OID");
		EPMDocumentMaster cad3dMaster = (EPMDocumentMaster) rf.getReference(epm).getObject();
		EPMDocument epmDoc = PartBaseHelper.service.getLatestEPM(cad3dMaster);
		if (isSame2DNumberWithPart(epmDoc.getNumber(), epm2DNo)) {
		    epm3dFrom2d = epmDoc;
		    break;
		}
	    }
	}

	return epm3dFrom2d;
    }
    
    //migration 정보 있는지 체크
    private List<Map> get3DMasterOidList(String epm2dOid) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List alBindSql = new ArrayList();
	String sSql = null;
	List<Map> cad3Info = new ArrayList<Map>();
	
	try {
	    
	    String epm2dIterOid = "";
	    if(epm2dOid.indexOf(":") != -1){
		epm2dIterOid = epm2dOid.substring(epm2dOid.lastIndexOf(":")+1);
	    }else{
		epm2dIterOid = epm2dOid;
	    }

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT DISTINCT L.CLASSNAMEKEYROLEBOBJECTREF||':'||L.IDA3B5 OID \n"); // 3D MASTER OID
	    sb.append(" FROM EPMREFERENCELINK L \n");
	    sb.append(" WHERE 1=1 \n");
	    sb.append(" AND L.IDA3A5 = ? \n");
	    sb.append(" AND L.REFERENCETYPE = 'DRAWING' \n"); 
	    
	    sSql = sb.toString();

	    alBindSql.add(epm2dIterOid);

	    Kogger.debug(getClass(), "get3DMasterOidList : " + sSql);

	    List<Map> listMap = oDaoManager.queryForList(sSql, alBindSql, new RowSetStrategy<Map>() {
		@Override
		public Map mapRow(ResultSet oResultSet) throws SQLException {
		    Map cad3dInfo = new HashMap();
		    cad3dInfo.put("OID", oResultSet.getString("OID"));
		    return cad3dInfo;
		}
	    });

	    if (listMap != null)
		cad3Info = listMap;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return cad3Info;
    }
    
}
