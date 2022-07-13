/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProdEcrBeans.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 18.
 */
package e3ps.ecm.beans;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import wt.change2.WTChangeRequest2;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.ecm.dao.EcmComDao;
import e3ps.ecm.dao.ProdEcrDao;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETEcrCompetentLink;
import e3ps.ecm.entity.KETEcrMeetingLink;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETProdECRIssueLink;

/**
 * 클래스명 : ProdEcrBeans 설명 : 작성자 : 오승연 작성일자 : 2010. 10. 18.
 */
public class ProdEcrBeans {

    public Hashtable<String, ArrayList<Hashtable<String, String>>> getInitCodeList(Map<String, Object> parameter) throws Exception {
	Connection conn = null;
	EcmComDao cDao = null;

	Hashtable<String, ArrayList<Hashtable<String, String>>> codeHash = new Hashtable<String, ArrayList<Hashtable<String, String>>>();

	try {
	    conn = PlmDBUtil.getConnection();

	    cDao = new EcmComDao(conn);
	    ArrayList<Hashtable<String, String>> divList = cDao.getCodeList("DIVISIONFLAG", "ko");
	    ArrayList<Hashtable<String, String>> devFlagList = cDao.getCodeList("DEVYN", parameter.get("locale").toString());
	    ArrayList<Hashtable<String, String>> chgTypeList = cDao.getCodeList("PRODCHANGETYPE", parameter.get("locale").toString());
	    ArrayList<Hashtable<String, String>> chgReasonList = cDao.getCodeList("PRODCHAGEREASON", parameter.get("locale").toString());

	    codeHash.put("division", divList);
	    codeHash.put("devYn", devFlagList);
	    codeHash.put("changeType", chgTypeList);
	    codeHash.put("changeReason", chgReasonList);

	} catch (Exception e) {
	    throw e;
	} finally {
	    PlmDBUtil.close(conn);
	}

	return codeHash;
    }

    public String getNewProdEcrId() throws Exception {
	String ecrId = "";
	Connection conn = null;
	ProdEcrDao ecrDao = null;

	try {
	    conn = PlmDBUtil.getConnection();

	    ecrDao = new ProdEcrDao(conn);

	    ecrId = ecrDao.getProdEcrId();
	} catch (Exception e) {
	    throw e;
	} finally {
	    PlmDBUtil.close(conn);
	}

	return ecrId;
    }

    public Hashtable<String, Object> getProdEcr(String ida2a2) throws Exception {
	Connection conn = null;
	ProdEcrDao ecrDao = null;
	Hashtable<String, Object> prodEcr = null;
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
	try {
	    conn = PlmDBUtil.getConnection();

	    ecrDao = new ProdEcrDao(conn);
	    prodEcr = ecrDao.getProdEcrInfo(ida2a2);
	    partList = ecrDao.getRefPartList(ida2a2);

	    prodEcr.put("partList", partList);

	} catch (Exception e) {
	    throw e;
	} finally {
	    PlmDBUtil.close(conn);
	}

	return prodEcr;
    }

    public ArrayList<Hashtable<String, String>> getProdEcrPartList(String ecrOid) throws Exception {
	Connection conn = null;
	ProdEcrDao ecrDao = null;
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();

	try {
	    conn = PlmDBUtil.getConnection();

	    ecrDao = new ProdEcrDao(conn);
	    partList = ecrDao.getRefPartList(KETObjectUtil.getIda2a2(ecrOid));

	} catch (Exception e) {
	    throw e;
	} finally {
	    PlmDBUtil.close(conn);
	}

	return partList;
    }

    public ArrayList<KETProdECRIssueLink> getProdEcrIssueList(String ecrOid) throws Exception {
	ArrayList<KETProdECRIssueLink> issueList = new ArrayList<KETProdECRIssueLink>();
	try {
	    KETProdChangeRequest prodEcr = (KETProdChangeRequest) KETObjectUtil.getObject(ecrOid);

	    QueryResult qr = null;
	    qr = PersistenceHelper.manager.navigate(prodEcr, "issue", KETProdECRIssueLink.class, false);

	    while (qr.hasMoreElements()) {
		issueList.add((KETProdECRIssueLink) qr.nextElement());
	    }

	} catch (Exception e) {
	    throw e;
	}

	return issueList;
    }

    public KETCompetentDepartment getKETCompetentDepartment(String ecrOid) throws Exception {
	KETCompetentDepartment competent = null;
	try {
	    WTChangeRequest2 ecr = (WTChangeRequest2) KETObjectUtil.getObject(ecrOid);

	    QueryResult qr = PersistenceHelper.manager.navigate(ecr, "competent", KETEcrCompetentLink.class, false);
	    while (qr.hasMoreElements()) {
		KETEcrCompetentLink link = (KETEcrCompetentLink) qr.nextElement();
		competent = link.getCompetent();
	    }

	} catch (Exception e) {
	    throw e;
	}

	return competent;
    }

    public KETMeetingMinutes getKETMeetingMinutes(String ecrOid) throws Exception {
	KETMeetingMinutes meeting = null;
	try {
	    WTChangeRequest2 ecr = (WTChangeRequest2) KETObjectUtil.getObject(ecrOid);

	    QueryResult qr = PersistenceHelper.manager.navigate(ecr, "meeting", KETEcrMeetingLink.class, false);
	    while (qr.hasMoreElements()) {
		KETEcrMeetingLink link = (KETEcrMeetingLink) qr.nextElement();
		meeting = link.getMeeting();
	    }

	} catch (Exception e) {
	    throw e;
	}

	return meeting;
    }
}
