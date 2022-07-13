package ext.ket.edm.stamping.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.edm.util.EDMProperties;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.stamping.util.StampingConstants;

public class StampingQueueInputer {

    public List<KETDrawingDistRequest> getInputQueueDrawingDistReq() throws Exception {

	List<KETDrawingDistRequest> distReqList = new ArrayList<KETDrawingDistRequest>();

	List<String> oidList = getInputQueueDrawingDistReqInner();
	for (String oid : oidList) {
	    distReqList.add((KETDrawingDistRequest) CommonUtil.getObject(oid));
	}

	return distReqList;
    }

    private List<String> getInputQueueDrawingDistReqInner() throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<String> inputQueueList = new ArrayList<String>();

	try {

	    String limitHourAboutReInputQueue = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_LIMITHOUR_RE_INPUT_QUEUE); // "epm.stamping.limitHourAboutReInputQueue"
	    String query = getInputQueueDrawingDistReqQuery(limitHourAboutReInputQueue);

	    inputQueueList = oDaoManager.queryForList(query, new RowSetStrategy<String>() {
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

	return inputQueueList;
    }

    private String getInputQueueDrawingDistReqQuery(String limitHourAboutReInputQueue) throws Exception {

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT R.CLASSNAMEA2A2||':'||R.IDA2A2 OID \n");
	sb.append(" FROM KETDrawingDistRequest R \n");
	sb.append(" WHERE STATESTATE = 'APPROVED' \n");
	sb.append(" MINUS \n");
	sb.append(" SELECT R.CLASSNAMEA2A2||':'||R.IDA2A2 OID \n");
	sb.append(" FROM KETDrawingDistRequest R, KETStampDistLink L, KETStamping S \n");
	sb.append(" WHERE L.IDA3A5 = S.IDA2A2 \n");
	sb.append(" AND L.IDA3B5 = R.IDA2A2 \n");
	sb.append(" AND (  \n");
	sb.append("         S.STAMPSTATUS IN ('SUCCESS')  \n");
	// 테스트 시 주석을 넣어준다.
	sb.append("         OR S.STAMPSTATUS IN ('FAIL')  \n");
	sb.append("         OR ( S.STAMPSTATUS IN 'QUEUE' AND S.QUEUEINPUTDATE IS NOT NULL  \n");
	sb.append("              AND SYSDATE - S.QUEUEINPUTDATE < " + limitHourAboutReInputQueue + "/24  ) \n");
	sb.append("     ) \n");

	return sb.toString();
    }

}
