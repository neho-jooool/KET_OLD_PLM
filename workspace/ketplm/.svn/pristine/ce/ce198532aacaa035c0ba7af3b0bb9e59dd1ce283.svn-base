/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : DMSUtil.java
 * 작성일자 : 2010. 12. 22.
 */
package e3ps.dms.beans;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.util.WTException;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.dms.dao.DevRequestDao;
import e3ps.dms.dao.DocumentDao;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import ext.ket.shared.log.Kogger;

public class DMSUtil {

    public static Boolean isDrCheckOfDoc(KETProjectDocument docu) {
	Boolean isDRCheckSheet = null;

	try {
	    if (docu == null)
		return null;

	    KETDocumentCategory docCate = null;
	    QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
	    if (r.hasMoreElements()) {
		docCate = (KETDocumentCategory) r.nextElement();
		isDRCheckSheet = docCate.getIsDRCheckSheet();
	    }
	} catch (WTException e) {
	    Kogger.error(DMSUtil.class, e);
	}
	return isDRCheckSheet;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 미사용 Method 주석처리 수정일자 : 2013. 6. 4 수정자 : 김종호
     */
    /*
     * public static Vector serDocumentList ( Hashtable tempHash ){ Connection conn = null; Vector docuOids = null; try {
     * 
     * conn = PlmDBUtil.getConnection(); DocumentDao documentDao = new DocumentDao(conn); docuOids = documentDao.ViewDocumentList(tempHash);
     * documentDao = null; } catch (IOException e) { Kogger.error(getClass(), e); } catch (Exception e) { Kogger.error(getClass(), e); } finally {
     * PlmDBUtil.close(conn); } return docuOids; }
     */
    /*
     * [PLM System 1차개선] 수정내용 : 기존 Method 변경 수정일자 : 2013. 7. 30 수정자 : 김무준
     */
    public static Vector serDocumentList(Hashtable tempHash) {
	Vector docuOids = new Vector();
	try {
	    DocumentDao dao = new DocumentDao();

	    List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	    conditionList.add(KETParamMapUtil.getMap(tempHash));
	    List<Map<String, Object>> list = dao.searchProjectDocumentList(conditionList);
	    if (list != null && list.size() > 0) {
		for (Map<String, Object> row : list) {
		    String docuOid = (String) row.get("oid");
		    docuOids.add(docuOid);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(DMSUtil.class, e);
	} finally {

	}
	return docuOids;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 미사용 Method 주석처리 수정일자 : 2013. 5. 31 수정자 : 오명재
     */
    /*
     * public static Vector serTechDocumentList ( Hashtable tempHash ){ Connection conn = null; Vector docuOids = null; try {
     * 
     * conn = PlmDBUtil.getConnection(); DocumentDao documentDao = new DocumentDao(conn); docuOids =
     * documentDao.ViewTechDocumentList(tempHash); documentDao = null; } catch (IOException e) { Kogger.error(getClass(), e); } catch (Exception e)
     * { Kogger.error(getClass(), e); } finally { PlmDBUtil.close(conn); } return docuOids; }
     */

    /*
     * 
     * public static Hashtable serDevRequestList ( Map tempHash ){ Connection conn = null; Hashtable devRequestOids = null; try {
     * 
     * conn = PlmDBUtil.getConnection(); DevRequestDao devRequestDao = new DevRequestDao(conn); devRequestOids =
     * devRequestDao.ViewDRList(tempHash); devRequestDao = null; } catch (IOException e) { Kogger.error(getClass(), e); } catch (Exception e) {
     * Kogger.error(getClass(), e); } finally { PlmDBUtil.close(conn); } return devRequestOids; }
     * 
     * public static Hashtable serPopDevRequestList ( Hashtable tempHash ){ Connection conn = null; Hashtable devRequestOids = null; try {
     * 
     * conn = PlmDBUtil.getConnection(); DevRequestDao devRequestDao = new DevRequestDao(conn); devRequestOids =
     * devRequestDao.ViewPopDRList(tempHash); devRequestDao = null; } catch (IOException e) { Kogger.error(getClass(), e); } catch (Exception e) {
     * Kogger.error(getClass(), e); } finally { PlmDBUtil.close(conn); } return devRequestOids; }
     */
    public static String serCustomerNm(String carType) {
	Connection conn = null;
	String customerNm = "";
	try {

	    conn = PlmDBUtil.getConnection();
	    DevRequestDao devRequestDao = new DevRequestDao(conn);
	    customerNm = devRequestDao.CustmerEvName(carType);
	    if (customerNm == null)
		customerNm = "";
	    devRequestDao = null;
	} catch (IOException e) {
	    Kogger.error(DMSUtil.class, e);
	} catch (Exception e) {
	    Kogger.error(DMSUtil.class, e);
	} finally {
	    PlmDBUtil.close(conn);
	}
	return customerNm;
    }

    public static int useDocCategoryCnt(String categoryCode) {
	int useCnt = 0;
	try {

	    DocumentDao documentDao = new DocumentDao();
	    useCnt = documentDao.isUseDocCategory(categoryCode);
	    documentDao = null;

	} catch (IOException e) {
	    Kogger.error(DMSUtil.class, e);
	} catch (Exception e) {
	    Kogger.error(DMSUtil.class, e);
	}

	return useCnt;
    }

    public static List<Map<String, Object>> getDocumentCategory(Map<String, Object> parameter) throws Exception {

	try {
	    DocumentDao dao = new DocumentDao();
	    KETParamMapUtil map = KETParamMapUtil.getMap(parameter);
	    List<Map<String, Object>> list = dao.searchCategoryList(map);
	    return list;
	} finally {

	}
    }
}
