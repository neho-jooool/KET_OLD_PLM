package ext.ket.edm.approval.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wt.epm.EPMDocument;
import wt.fc.Persistable;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;

public class EpmApprovalDao {

    /**
     * 결재완료된 ECO PBO 찾기
     * 
     * @param epmDoc
     * @return
     * @throws Exception
     * @메소드명 : getEcoPboOid
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getEcoPboOid(EPMDocument epmDoc) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT DISTINCT ecoOid \n");
	buffer.append(" FROM \n");
	buffer.append(" ( \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.STATESTATE = 'APPROVED' \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.STATESTATE = 'APPROVED' \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.STATESTATE = 'APPROVED' \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.STATESTATE = 'APPROVED' \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append(" ) \n");

	long ida2a2 = CommonUtil.getOIDLongValue(epmDoc);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);

	List<String> eoOidList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("ecoOid");
	    }
	});

	if (eoOidList.size() == 0) {
	    return null;
	}
	else {
	    return eoOidList.get(0);
	}
    }

    /**
     * 도면에 연결된 모든 ECO 찾기
     * 
     * @param epmDoc
     * @return
     * @throws Exception
     * @메소드명 : getRelatedEcoList
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<String> getRelatedEcoList(EPMDocument epmDoc) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT DISTINCT ecoOid \n");
	buffer.append(" FROM \n");
	buffer.append(" ( \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select A.CLASSNAMEKEYA8||':'||A.IDA3A8 ecoOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND NE.IDA2A2 = ? \n");
	buffer.append(" ) \n");

	long ida2a2 = CommonUtil.getOIDLongValue(epmDoc);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);

	List<String> eoOidList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("ecoOid");
	    }
	});

	return eoOidList;
    }

    /**
     * EPM 찾기 BY ECO
     * 
     * @param changeOrder2
     * @return
     * @throws Exception
     * @메소드명 : getEpmOidByEco
     * @작성자 : yjlee
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<String> getEpmOidByEco(Persistable changeOrder2) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT DISTINCT epmVerOid \n");
	buffer.append(" FROM \n");
	buffer.append(" ( \n");
	buffer.append("  select 'VR:'||OE.CLASSNAMEA2A2||':'||OE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select 'VR:'||OE.CLASSNAMEA2A2||':'||OE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NULL \n");
	buffer.append("  AND OE.VERSIONIDA2VERSIONINFO = L.PREVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select 'VR:'||NE.CLASSNAMEA2A2||':'||NE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select 'VR:'||NE.CLASSNAMEA2A2||':'||NE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append(" ) \n");

	long ida2a2 = CommonUtil.getOIDLongValue(changeOrder2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);

	List<String> epmOidList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("epmVerOid");
	    }
	});

	return epmOidList;
    }

    public List<EPMDocument> getEpmDocByEco(String ecoOid) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT DISTINCT epmVerOid \n");
	buffer.append(" FROM \n");
	buffer.append(" ( \n");
	buffer.append("  select 'VR:'||NE.CLASSNAMEA2A2||':'||NE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append("  UNION ALL \n");
	buffer.append("  select 'VR:'||NE.CLASSNAMEA2A2||':'||NE.BRANCHIDITERATIONINFO epmVerOid \n");
	buffer.append("  from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND L.IDA3A5 = OE.IDA2A2 \n");
	buffer.append("  AND L.IDA3B5 = A.IDA2A2 \n");
	buffer.append("  AND AFTERVERSION IS NOT NULL \n");
	buffer.append("  AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE \n");
	buffer.append("  AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION \n");
	buffer.append("  AND A.IDA3A8 = ? \n");
	buffer.append(" ) \n");

	Persistable changeOrder2 = (Persistable) CommonUtil.getObject(ecoOid);
	long ida2a2 = CommonUtil.getOIDLongValue(changeOrder2);
	aBindList.add(ida2a2);
	aBindList.add(ida2a2);

	List<String> epmOidList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("epmVerOid");
	    }
	});

	List<EPMDocument> retList = new ArrayList<EPMDocument>();

	for (String epmVerOid : epmOidList) {
	    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(epmVerOid);
	    retList.add(epmDoc);
	}

	return retList;
    }
}
