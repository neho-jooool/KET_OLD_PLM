/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EWSHelper.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 12. 14.
 */
package e3ps.ews.beans;

import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.ews.dao.PartnerDao;
import e3ps.ews.entity.EarlyWarningTargetLink;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningTarget;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.sap.PartResultInterface;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EWSHelper 설명 : 작성자 : 김경희 작성일자 : 2010. 12. 14.
 */
public class EWSHelper {

    public static EWSHelper manager = new EWSHelper();

    /**
     * 함수명 : getPartResult 설명 :
     * 
     * @param startDate
     * @param endDate
     * @param partNo
     * @return ArrayList
     * @throws 작성자
     *             : 김경희 작성일자 : 2010. 12. 13.
     */
    public ArrayList getPartResult(Hashtable hash) {

	String startDate = (String) hash.get("startDate");
	String endDate = (String) hash.get("endDate");
	String partNo = (String) hash.get("partNo");
	String partName = (String) hash.get("partName");
	String inout = (String) hash.get("inout");

	ArrayList<Hashtable<String, String>> resultList = new ArrayList<Hashtable<String, String>>();
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();

	Hashtable<String, String> part = new Hashtable<String, String>();
	Hashtable<String, String> result = null;
	Double total = 1.0;
	Double custError = 0.0;
	Double outputCnt = 0.0;
	Object custErrorPPM = null;
	Double custErrorCnt = 0.0;
	Double proError = 0.0;
	Double proCnt = 0.0;
	Object proErrorPPM = null;

	try {
	    resultList = PartResultInterface.getPartResult(startDate, endDate, partNo);

	    for (int inx = 0; inx < resultList.size(); inx++) {
		part = (Hashtable<String, String>) resultList.get(inx);

		total = 1.0;

		if (part.get("facility") != null && !part.get("facility").equals("0")) {
		    total = total * (Double.parseDouble(part.get("facility")) / 100.0);
		}
		if (part.get("perform") != null && !part.get("perform").equals("0")) {
		    total = total * (Double.parseDouble(part.get("perform")) / 100.0);
		}
		if (part.get("good") != null && !part.get("good").equals("0")) {
		    total = total * (Double.parseDouble(part.get("good")) / 100.0);
		}

		total = Math.round(total * 1000) / 10.0;

		if (part.get("custError") != null) {
		    custError = Double.parseDouble(part.get("custError"));
		}
		if (part.get("outputCnt") != null) {
		    outputCnt = Double.parseDouble(part.get("outputCnt"));
		}
		if (part.get("outputCnt") != null && outputCnt != 0) {
		    custErrorPPM = Math.round(custError / outputCnt * 1000000) / 1.0;
		}
		if (part.get("custErrorCnt") != null) {
		    custErrorCnt = Double.parseDouble(part.get("custErrorCnt"));
		}

		if (inout != null && inout.equals("i")) {
		    if (part.get("proError") != null) {
			proError = Double.parseDouble(part.get("proError"));
		    }
		    if (part.get("proCnt") != null) {
			proCnt = Double.parseDouble(part.get("proCnt"));
		    }
		} else if (inout != null && inout.equals("o")) {
		    if (part.get("inspectError") != null) {
			proError = Double.parseDouble(part.get("inspectError"));
		    }
		    if (part.get("inspectCnt") != null) {
			proCnt = Double.parseDouble(part.get("inspectCnt"));
		    }
		}
		if (part.get("proCnt") != null && proCnt != 0) {
		    proErrorPPM = Math.round(proError / proCnt * 1000000) / 1.0;
		}

		result = new Hashtable<String, String>();

		result.put("partNo", StringUtil.checkNull(partNo));
		result.put("partName", StringUtil.checkNull(partName));
		result.put("month", ((String) part.get("month")).substring(4, 6));
		result.put("custError", StringUtil.getDouble(custError, "", "###,###"));
		result.put("outputCnt", StringUtil.getDouble(outputCnt, "", "###,###"));
		if (part.get("custError") == null || part.get("outputCnt") == null || outputCnt == 0) {
		    result.put("custErrorPPM", "");
		} else {
		    result.put("custErrorPPM", StringUtil.getDouble(custErrorPPM, "", "###,###"));
		}
		result.put("custErrorCnt", StringUtil.getDouble(custErrorCnt, "", "###,###"));
		result.put("proError", StringUtil.getDouble(proError, "", "###,###"));
		result.put("proCnt", StringUtil.getDouble(proCnt, "", "###,###"));
		if (part.get("proError") == null || part.get("proCnt") == null || proCnt == 0) {
		    result.put("proErrorPPM", "");
		} else {
		    result.put("proErrorPPM", StringUtil.getDouble(proErrorPPM, "", "###,###"));
		}
		result.put("facility", StringUtil.getDouble((Object) Double.parseDouble(part.get("facility")), "##", "##.#"));
		result.put("perform", StringUtil.getDouble((Object) Double.parseDouble(part.get("perform")), "##", "##.#"));
		result.put("good", StringUtil.getDouble((Object) Double.parseDouble(part.get("good")), "##", "##.#"));
		if ((part.get("facility") != null && !part.get("facility").equals("0"))
		        || (part.get("perform") != null && !part.get("perform").equals("0"))
		        || (part.get("good") != null && !part.get("good").equals("0"))) {
		    result.put("total", StringUtil.getDouble((Object) total, "##", "##.#"));
		} else {
		    result.put("total", "");
		}

		list.add(result);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return list;
    }

    /**
     * 함수명 : getPartTarget 설명 :
     * 
     * @param ketEarlyWarning
     * @return ArrayList
     * @throws 작성자
     *             : 김경희 작성일자 : 2011. 01. 12.
     */
    public ArrayList<Hashtable<String, String>> getPartTarget(KETEarlyWarning ketEarlyWarning) {

	QueryResult result = null;
	EarlyWarningTargetLink link = null;
	KETEarlyWarningTarget ketEarlyWarningTarget = null;
	ProductProject productProject = null;
	ProductInfo pInfo = null;
	MoldItemInfo miInfo = null;
	MoldProject moldProject = null;
	PartnerDao partnerDao = new PartnerDao();
	String productNo = null;
	String partProTeamName = null;
	String dieProTeamName = null;
	Double targetTotal = 1.0;

	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> part = new Hashtable<String, String>();

	try {
	    result = PersistenceHelper.navigate(ketEarlyWarning, EarlyWarningTargetLink.ROLE_BOBJECT_ROLE, EarlyWarningTargetLink.class,
		    false);

	    if (result != null) {
		while (result.hasMoreElements()) {
		    link = (EarlyWarningTargetLink) result.nextElement();
		    ketEarlyWarningTarget = (KETEarlyWarningTarget) link.getRoleBObject();
		    productNo = StringUtil.checkNull(ketEarlyWarningTarget.getProductNo());

		    part = new Hashtable<String, String>();
		    part.put("poid", productNo);
		    if (CommonUtil.getObject(productNo) instanceof ProductProject) {
			productProject = (ProductProject) CommonUtil.getObject(productNo);

			part.put("partNo", StringUtil.checkNull(productProject.getPartNo()));
			part.put("partName", StringUtil.checkNull(productProject.getPjtName()));
			part.put("dieNo", "");
			part.put("type", "Assy");
			if (productProject.getPartnerNo() != null && productProject.getPartnerNo().length() > 0) {
			    partnerDao = new PartnerDao();
			    partProTeamName = partnerDao.ViewPartnerName(productProject.getPartnerNo());
			} else if (productProject.getAssemblyPlace() != null) {
			    partProTeamName = productProject.getAssemblyPlace().getName();
			}
			part.put("proTeamName", StringUtil.checkNull(partProTeamName));
			part.put("dieProTeam", "");
		    } else if (CommonUtil.getObject(productNo) instanceof MoldItemInfo) {
			miInfo = (MoldItemInfo) CommonUtil.getObject(productNo);
			moldProject = MoldProjectHelper.getMoldProject(miInfo);

			part.put("partNo", StringUtil.checkNull(miInfo.getPartNo()));
			part.put("partName", StringUtil.checkNull(miInfo.getPartName()));
			part.put("dieNo", StringUtil.checkNull(miInfo.getDieNo()));
			part.put("type", "Item");
			if (miInfo.getPartnerNo() != null && miInfo.getPartnerNo().length() > 0) {
			    partnerDao = new PartnerDao();
			    partProTeamName = partnerDao.ViewPartnerName(miInfo.getPartnerNo());
			} else if (miInfo.getPurchasePlace() != null) {
			    partProTeamName = miInfo.getPurchasePlace().getName();
			}
			part.put("proTeamName", StringUtil.checkNull(partProTeamName));
			if (moldProject != null) {
			    dieProTeamName = moldProject.getOutSourcing();
			}
			part.put("dieProTeam", StringUtil.checkNull(dieProTeamName));
		    } else if (CommonUtil.getObject(productNo) instanceof ProductInfo) {
			pInfo = (ProductInfo) CommonUtil.getObject(productNo);
			productProject = (ProductProject) pInfo.getProject();
			part.put("partNo", StringUtil.checkNull(pInfo.getPNum()));
			part.put("partName", StringUtil.checkNull(pInfo.getPName()));
			part.put("dieNo", "");
			part.put("type", "Assy");
			if (productProject.getPartnerNo() != null && productProject.getPartnerNo().length() > 0) {
			    partnerDao = new PartnerDao();
			    partProTeamName = partnerDao.ViewPartnerName(productProject.getPartnerNo());
			} else if (productProject.getAssemblyPlace() != null) {
			    partProTeamName = productProject.getAssemblyPlace().getName();
			}
			part.put("proTeamName", StringUtil.checkNull(partProTeamName));
			part.put("dieProTeam", "");
		    }

		    targetTotal = 1.0;

		    if (ketEarlyWarningTarget.getTargetFacility() != null && ketEarlyWarningTarget.getTargetFacility() != 0) {
			targetTotal = targetTotal * (ketEarlyWarningTarget.getTargetFacility() / 100.0);
		    }
		    if (ketEarlyWarningTarget.getTargetPerform() != null && ketEarlyWarningTarget.getTargetPerform() != 0) {
			targetTotal = targetTotal * (ketEarlyWarningTarget.getTargetPerform() / 100.0);
		    }
		    if (ketEarlyWarningTarget.getTargetGood() != null && ketEarlyWarningTarget.getTargetGood() != 0) {
			targetTotal = targetTotal * (ketEarlyWarningTarget.getTargetGood() / 100.0);
		    }

		    targetTotal = Math.round(targetTotal * 1000) / 10.0;

		    part.put("cusError", StringUtil.getDouble(ketEarlyWarningTarget.getTargetCusError(), "###,###", "###,###.#"));
		    part.put("workError", StringUtil.getDouble(ketEarlyWarningTarget.getTargetWorkError(), "###,###", "###,###.#"));
		    part.put("facility", StringUtil.getDouble(ketEarlyWarningTarget.getTargetFacility(), "##", "##.#"));
		    part.put("perform", StringUtil.getDouble(ketEarlyWarningTarget.getTargetPerform(), "##", "##.#"));
		    part.put("good", StringUtil.getDouble(ketEarlyWarningTarget.getTargetGood(), "##", "##.#"));
		    if ((ketEarlyWarningTarget.getTargetFacility() != null && ketEarlyWarningTarget.getTargetFacility() != 0)
			    || (ketEarlyWarningTarget.getTargetPerform() != null && ketEarlyWarningTarget.getTargetPerform() != 0)
			    || (ketEarlyWarningTarget.getTargetGood() != null && ketEarlyWarningTarget.getTargetGood() != 0)) {
			part.put("targetTotal", StringUtil.getDouble((Object) targetTotal, "##", "##.#"));
		    } else {
			part.put("targetTotal", "");
		    }

		    list.add(part);
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return list;
    }

}
