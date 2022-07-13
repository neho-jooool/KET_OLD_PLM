package ext.ket.edm.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import e3ps.cost.util.HomePageDBUtil;

public class PlmHpIfCtl {

    public boolean savePlmHpIf(List<Map<String, Object>> distReqEpmDocList) {// 개발담당자 결재
	Connection conn = null;
	boolean bSuccess = false;
	try {
	    conn = HomePageDBUtil.getConnection();
	    PlmHpIfSendDao dao = new PlmHpIfSendDao(conn);

	    int i = dao.savePlmHpIf(distReqEpmDocList);
	    if (i > 0)
		bSuccess = true;
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	} finally {
	    HomePageDBUtil.close(conn);
	}
	return bSuccess;
    }

    public boolean savePartAttrIf(List<Map<String, Object>> partAttrList) {// 개발담당자 결재
	Connection conn = null;
	boolean bSuccess = false;
	try {
	    conn = HomePageDBUtil.getConnection();
	    PlmHpIfSendDao dao = new PlmHpIfSendDao(conn);

	    dao.deletePartAttrIf();

	    int i = dao.savePartAttrIf(partAttrList);
	    if (i > 0)
		bSuccess = true;
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	} finally {
	    HomePageDBUtil.close(conn);
	}
	return bSuccess;
    }

    public void savePlmHpIfMig(List<Map<String, Object>> distReqEpmDocList) {// 개발담당자 결재
	Connection conn = null;
	try {
	    conn = HomePageDBUtil.getConnection();
	    PlmHpIfSendDao dao = new PlmHpIfSendDao(conn);
	    dao.savePlmHpIfMig(distReqEpmDocList);
	} catch (Exception e) {
	    e.printStackTrace();
	    e.getMessage();
	} finally {
	    HomePageDBUtil.close(conn);
	}
    }

}
