package ext.ket.part.sap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.shared.log.Kogger;

public class ErpPartSampleUtil {

    private String getQuery(String partType) throws Exception {

	StringBuffer sb = new StringBuffer();
	sb.append(" select P.CLASSNAMEA2A2||':'||P.IDA2A2 OID \n");
	sb.append(" from wtpartmaster m, wtpart p, KETPartClassLink cl \n");
	sb.append(" , ( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO \n");
	sb.append("     FROM WTPART I, WTPARTMASTER J \n");
	sb.append("     WHERE I.IDA3MASTERREFERENCE = J.IDA2A2 \n");
	sb.append("          AND I.LATESTITERATIONINFO = 1   \n");
	sb.append("          AND I.STATECHECKOUTINFO != 'wrk' \n");
	sb.append("     GROUP BY J.IDA2A2  \n");
	sb.append("  ) MAXVERPART  \n");
	sb.append(" where 1=1 \n");
	sb.append(" and m.ida2a2 = P.IDA3MASTERREFERENCE \n");
	sb.append(" and m.ida2a2 = cl.IDA3B5 \n");
	sb.append(" and P.LATESTITERATIONINFO = 1 \n");
	sb.append(" and P.STATECHECKOUTINFO != 'wrk' \n");
	sb.append(" and P.STATESTATE = 'APPROVED' \n");
	sb.append(" and M.IDA2A2 = MAXVERPART.IDA2A2 \n");
	sb.append(" AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO \n");
	sb.append(" AND P.PTC_STR_3TYPEINFOWTPART = '" + partType + "' \n");

	return sb.toString();
    }

    public List<String> getOidList(String partType) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List<String> drwList = new ArrayList<String>();

	try {

	    String query = getQuery(partType);

	    drwList = oDaoManager.queryForList(query, new StampRowSetStrategy<String>() {
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

	return drwList;
    }
    
    public static void main(String[] args){
	
	String st = "H793280-3";
	st = st.replaceAll("-", "A-");
	Kogger.debug(ErpPartSampleUtil.class, st);
	
	st = "wt.part.WTPart:123,wt.part.WTPart:456,wt.part.WTPart:789"; 
	st = st.replaceAll("wt.part.WTPart:", "");
	Kogger.debug(ErpPartSampleUtil.class, st);
    }
    
}
