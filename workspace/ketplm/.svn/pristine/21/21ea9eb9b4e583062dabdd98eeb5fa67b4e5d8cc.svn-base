/**
 * @(#)	EcmUtil
 * Copyright (c) jerred. All rights reserverd
 *
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 3.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc
 */

package e3ps.ecm.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import wt.change2.WTChangeOrder2;
import wt.enterprise.Master;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECADocLink;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETMoldProdEcoLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;
import e3ps.groupware.company.PeopleData;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.shared.log.Kogger;

public final class EcmUtil {
    /**
     * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
     */
    private EcmUtil() {
    }

    private static Config config = null;
    private static String defaultDateFormat;
    static {
	config = ConfigImpl.getInstance();
	defaultDateFormat = config.getString("date.format");
    }

    /*
     * 배열에서 문자열 찾기
     */
    public static int getMatchArrIndex(String str, String[] arr) {
	int size = arr.length;
	int matchIndex = -1;
	for (int i = 0; i < size; i++) {
	    if (str.equals(arr[i])) {
		matchIndex = i;
		break;
	    }
	}
	return matchIndex;
    }

    // 문자열 절사(KETStringUtil.getTruncatedText()로 참조
    public static String getTruncStr(String str, int len) {
	if (str.length() > len) {
	    str = str.substring(0, len) + "...";
	}
	return str;
    }

    public static void logging(String msg) {
	Kogger.debug(EcmUtil.class, msg);
    }

    // 화면날짜형식 변환(yyyyMMdd ==> yyyy-MM-dd)
    public static String getDefaultDateFormat(String str) {
	try {
	    if (str.length() > 0) {
		str = getServerDateFormat(str);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		str = DateUtil.getDateString(DateUtil.parseDateStr(sdFormat, str), sdFormat2);
	    }
	} catch (ParseException e) {
	    str = "";
	}
	return str;
    }

    // 화면날짜형식 변환(yyyyMMdd ==> yyyy/MM/dd)
    public static String getClientDateFormat(String str) {
	try {
	    if (str.length() > 0) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdFormat2 = new SimpleDateFormat(defaultDateFormat);
		str = DateUtil.getDateString(DateUtil.parseDateStr(sdFormat, str), sdFormat2);
	    }
	} catch (ParseException e) {
	    str = "";
	}
	return str;
    }

    // 서버날짜형식 변환(yyyy-MM-dd ==> yyyyMMdd)
    public static String getServerDateFormat(String str) {
	if (str == null) str = "";
	str = str.replace("-", "");
	str = str.replace("/", "");
	return str;
    }

    // 서버날짜형식 리턴
    public static String getDateFormatString() {
	return defaultDateFormat;
    }

    // Oid Value 추출
    @SuppressWarnings("rawtypes")
    public static String getOidStringValue(String oid) {
	if (oid == null) {
	    return "";
	}
	ArrayList arrOId = KETStringUtil.getToken(oid, ":");
	if (arrOId.size() > 1) {
	    oid = (String) arrOId.get(1);
	}
	return oid;
    }

    /**
     * 함수명 : getChangeActivities 설명 : ECO에 포함된 Activity 객체 리스트를 얻는 함수
     * 
     * @param ecoObj
     *            - ECO 객체
     * @return Object[] - ECO Activity 배열 작성자 : 오승연 작성일자 : 2010. 10. 27.
     */
    public static Object[] getChangeActivities(Persistable ecoObj) {
	QueryResult activityRs = null;
	int activityCnt = 0;
	Object[] activityList = null;

	try {
	    // 제품 ECO
	    if (ecoObj instanceof KETProdChangeOrder) {
		activityRs = PersistenceHelper.manager.navigate(ecoObj, "prodECA", KETProdChangeActivityLink.class);

		if (activityRs != null) {
		    activityList = new Object[activityRs.size()];
		    KETProdChangeActivity prodECA = null;

		    while (activityRs.hasMoreElements()) {
			prodECA = (KETProdChangeActivity) activityRs.nextElement();
			activityList[activityCnt++] = prodECA;
		    }
		}

	    }
	    // 금형 ECO
	    else if (ecoObj instanceof KETMoldChangeOrder) {
		activityRs = PersistenceHelper.manager.navigate(ecoObj, "moldECA", KETMoldChangeActivityLink.class);

		if (activityRs != null) {
		    activityList = new Object[activityRs.size()];
		    KETMoldChangeActivity moldECA = null;

		    while (activityRs.hasMoreElements()) {
			moldECA = (KETMoldChangeActivity) activityRs.nextElement();
			activityList[activityCnt++] = moldECA;
		    }
		}
	    }
	} catch (WTException e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return activityList;
    }

    /**
     * 함수명 : getLastApproverName 설명 : 객체의 결재 최종승인자 이름을 가져오는 함수
     * 
     * @param obj
     *            - Persistable 객체( ECR, ECO 객체 )
     * @return 사용자 이름 작성자 : 오승연 작성일자 : 2010. 12. 8.
     */
    public static String getLastApproverName(Persistable obj) {
	String userName = "";
	try {
	    WTUser approveUser = WorkflowSearchHelper.manager.getLastApprover(obj);

	    if (approveUser != null) {
		userName = approveUser.getFullName();
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return userName;
    }

    /**
     * 함수명 : getLastApproveDate 설명 : 객체의 결재 최종승인일자를 가져오는 함수
     * 
     * @param obj
     *            - Persistable 객체( ECR, ECO 객체 )
     * @return 최종승인일자 String( yyyy-MM-dd ) 작성자 : 오승연 작성일자 : 2010. 12. 8.
     */
    public static String getLastApproveDate(Persistable obj) {
	String approveDateStr = "";

	try {
	    approveDateStr = WorkflowSearchHelper.manager.getLastApprovalDate(obj);

	    if (!approveDateStr.equals("&nbsp;")) {
		Date date = DateUtil.parseDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), approveDateStr);
		approveDateStr = DateUtil.getDateString(date, new SimpleDateFormat("yyyy-MM-dd"));
	    }
	    else {
		approveDateStr = "";
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return approveDateStr;
    }

    /**
     * 함수명 : existMoldEcaEpmDocLink 설명 : ECO에 포함된 도면 중복 여부 확인하는 함수
     * 
     * @param epmObj
     *            - 도면 객체
     * @return existYn - ECO에 관련된 도면 중복 여부 작성자 : 안수학 작성일자 : 2010. 12. 23.
     */
    public static boolean existEcaEpmDocLink(Persistable epmObj) {
	QueryResult qr = null;
	boolean existYn = false;
	boolean isWorking = false;

	try {

	    EPMDocument epm = (EPMDocument) epmObj;
	    String majorVersion = VersionUtil.getMajorVersion(epm);
	    String stateStr = epm.getLifeCycleState().getDisplay();
	    
	    Kogger.debug(EcmUtil.class, "existEcaEpmDocLink(Persistable epmObj)", epm.getNumber(), CommonUtil.getOIDLongValue2Str(epm), "==> 확인할 도면 : " + epm.getNumber() + " , 버전 : " + majorVersion);
	    
	    if (Integer.parseInt(majorVersion) > 0 && !stateStr.equalsIgnoreCase("승인완료")) {
		Kogger.debug(EcmUtil.class, "existEcaEpmDocLink(Persistable epmObj)", epm.getNumber(), CommonUtil.getOIDLongValue2Str(epm), "==> 해당도면은 작업중입니다.");
		isWorking = true;
	    }

	    if (!isWorking) {
		qr = PersistenceHelper.manager.navigate(epmObj, "moldECA", KETMoldECAEpmDocLink.class);

		if (qr.hasMoreElements()) {
		    Kogger.debug(EcmUtil.class, "existEcaEpmDocLink(Persistable epmObj)", epm.getNumber(), CommonUtil.getOIDLongValue2Str(epm), "==> 해당도면은 현재 금형 ECO에 있습니다.");
		    existYn = true;
		}

		if (!existYn) {
		    qr = PersistenceHelper.manager.navigate(epmObj, "prodECA", KETProdECAEpmDocLink.class);

		    if (qr.hasMoreElements()) {
			Kogger.debug(EcmUtil.class, "existEcaEpmDocLink(Persistable epmObj)", epm.getNumber(), CommonUtil.getOIDLongValue2Str(epm), "==> 해당도면은 현재 제품 ECO에 있습니다.");
			existYn = true;
		    }
		}
	    }
	    // else
	    // {
	    // int preVersion = Integer.parseInt(majorVersion) -1 ;
	    // EPMDocument preEpmDoc = (EPMDocument) VersionUtil.getVersionObject( (Master)epm.getMaster(), Integer.toString( preVersion )
	    // );
	    //
	    // qr = PersistenceHelper.manager.navigate(preEpmDoc, "moldECA", KETMoldECAEpmDocLink.class );
	    //
	    // if(qr.hasMoreElements())
	    // {
	    // existYn = true;
	    // }
	    //
	    // if( !existYn )
	    // {
	    // qr = PersistenceHelper.manager.navigate( preEpmDoc, "prodECA", KETProdECAEpmDocLink.class );
	    //
	    // if(qr.hasMoreElements())
	    // {
	    // existYn = true;
	    // }
	    // }
	    // }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}
	
	Kogger.debug(EcmUtil.class, "existEcaEpmDocLink(Persistable epmObj)", null, null, "==> 해당도면은 현재 결과 : " + existYn);
	return existYn;
    }

    /**
     * 
     * 연관된 ECO 객체를 가져온다.
     * 
     * @param epmObj
     * @return
     * @메소드명 : getECOexistEcaEpmDocLink
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static ArrayList<WTChangeOrder2> getECOexistEcaEpmDocLink(EPMDocument epm) {
	ArrayList<WTChangeOrder2> relatedEcoList = null;


	try {

	    String majorVersion = VersionUtil.getMajorVersion(epm);
	    String stateStr = epm.getLifeCycleState().getDisplay();

	    QueryResult qr = PersistenceHelper.manager.navigate(epm, "moldECA", KETMoldECAEpmDocLink.class, false);
	    while (qr.hasMoreElements()) {
		KETMoldECAEpmDocLink ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) qr.nextElement();
		KETMoldChangeActivity eca = ketMoldECAEpmDocLink.getMoldECA();

		QueryResult qr1 = PersistenceHelper.manager.navigate(eca, "moldECO", KETMoldChangeActivityLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr1.nextElement();
		    KETMoldChangeOrder eco = ketMoldChangeActivityLink.getMoldECO();

		    String state = eco.getLifeCycleState().toString();
		    if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
		    }
		    else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
		    }
		}
	    }

	    qr = PersistenceHelper.manager.navigate(epm, "prodECA", KETProdECAEpmDocLink.class, false);
	    while (qr.hasMoreElements()) {
		KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) qr.nextElement();
		KETProdChangeActivity eca = ketProdECAEpmDocLink.getProdECA();

		QueryResult qr1 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr1.nextElement();
		    KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

		    String state = eco.getLifeCycleState().toString();
		    if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
		    }
		    else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
		    }
		}
	    }

	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}


	return relatedEcoList;
    }

    /**
     * 함수명 : existEcaBomLink 설명 : ECO에 포함된 BOM 중복 여부 확인하는 함수
     * 
     * @param bomObj
     *            - BOM 객체
     * @return existYn - ECO에 관련된 BOM 중복 여부 작성자 : 안수학 작성일자 : 2010. 12. 23.
     */
    @SuppressWarnings({ "deprecation" })
    // public static boolean existMoldEcaBomLink(String moldEcaOid, String beforePartOid)
    public static String existEcaBomLink(String partNo, String partVersion) {
	String ALERT_MESSAGE = "";


	QueryResult qr = null;
	QueryResult ecaQr = null;
	QueryResult ecoQr = null;
	boolean isExist = false;
	boolean hasBomHeader = false;

	KETBomHeader bomHeader = null;
	KETBomEcoHeader ecoHeader = null;
	KETProdChangeActivity prodECA = null;
	KETMoldChangeActivity moldECA = null;

	KETProdChangeOrder prodECO = null;
	KETMoldChangeOrder moldECO = null;

	try {
	    QuerySpec spec = new QuerySpec(KETBomHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.NEW_BOMCODE, partNo, 0);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.BOMVERSION, partVersion, 0);

	    qr = PersistenceHelper.manager.find(spec);
	    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", null, null, "============> 확인할 PartNo : " + partNo + " , partVersion : " + partVersion);
	    if (qr != null && qr.hasMoreElements()) {
		bomHeader = (KETBomHeader) qr.nextElement();
		Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", partNo, CommonUtil.getOIDString(bomHeader), "=============> BOM Header 가 있습니다.");
		if (bomHeader.getLifeCycleState().getDisplay().equals("승인완료")) {
		    hasBomHeader = true;
		}
	    }

	    if (hasBomHeader) {
		spec = new QuerySpec(KETBomEcoHeader.class);
		SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);
		SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.BOMVERSION, partVersion, 0);

		qr = PersistenceHelper.manager.find(spec);
		Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", null, null, "============> 확인할 PartNo : " + partNo + " , partVersion : " + partVersion);
		if (qr != null && qr.hasMoreElements()) {
		    ecoHeader = (KETBomEcoHeader) qr.nextElement();
		    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", partNo, CommonUtil.getOIDString(ecoHeader), "=============> BOM Header 가 있습니다.");
		    isExist = true;
		}

		if (isExist) {
		    ecaQr = PersistenceHelper.manager.navigate(ecoHeader, "prodECA", KETProdECABomLink.class);
		    while (ecaQr.hasMoreElements()) {
			prodECA = (KETProdChangeActivity) ecaQr.nextElement();
		    }

		    if (prodECA == null) {
			ecaQr = PersistenceHelper.manager.navigate(ecoHeader, "moldECA", KETMoldECABomLink.class);
			while (ecaQr.hasMoreElements()) {
			    moldECA = (KETMoldChangeActivity) ecaQr.nextElement();
			}

			if (moldECA != null) {
			    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", null, CommonUtil.getOIDString(moldECA), "=============> BOM Eco Header 가 있습니다->Mold ECA 존재.");
			    ecoQr = PersistenceHelper.manager.navigate(moldECA, "moldECO", KETMoldChangeActivityLink.class);
			    while (ecoQr.hasMoreElements()) {
				moldECO = (KETMoldChangeOrder) ecoQr.nextElement();
			    }

			    if (moldECO.getLifeCycleState().getDisplay().equals("승인완료")) {
				Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", moldECO.getEcoId(), CommonUtil.getOIDString(moldECO), "=============> BOM Eco Header 가 있습니다->Mold ECA 존재.-->MoldECO 승인완료");
				isExist = false;
			    }
			    else {
				ALERT_MESSAGE += partNo + "[" + partVersion + "]" + " 는 " + moldECO.getEcoId() + " 에서 작업중입니다.";

			    }
			}
			else {
			    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)", null, null, "=============> BOM Eco Header 가 있습니다->Mold ECA 존재안함.");
			    isExist = false;
			}
		    }
		    else {
			Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",null, null, "=============> BOM Eco Header 가 있습니다->Prod ECA 존재");
			ecoQr = PersistenceHelper.manager.navigate(prodECA, "prodECO", KETProdChangeActivityLink.class);
			while (ecoQr.hasMoreElements()) {
			    prodECO = (KETProdChangeOrder) ecoQr.nextElement();
			}

			if (prodECO.getLifeCycleState().getDisplay().equals("승인완료")) {
			    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",prodECO.getEcoId(), CommonUtil.getOIDString(prodECO), "=============> BOM Eco Header 가 있습니다->Prod ECA 존재-->ProdECO 승인완료");
			    isExist = false;
			}
			else {
			    ALERT_MESSAGE += partNo + "[" + partVersion + "]" + " 는 " + prodECO.getEcoId() + " 에서 작업중입니다.";

			}
		    }
		}
		else {
		    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",partNo, CommonUtil.getOIDString(bomHeader), "=============> BOM Eco Header 가 없습니다. 추가할 수 있는 BOM ");
		}
	    }
	    else {
		hasBomHeader = BOMSearchUtilDao.isBomChildExistInPartUsageLink(partNo);

		if (hasBomHeader) {
		    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",partNo, null, "=============> 마이그레이션 데이터입니다.");
		    spec = new QuerySpec(KETBomEcoHeader.class);
		    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);
		    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.BOMVERSION, partVersion, 0);

		    qr = PersistenceHelper.manager.find(spec);
		    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",partNo, null, "============> 확인할 PartNo : " + partNo + " , partVersion : " + partVersion);
		    if (qr != null && qr.hasMoreElements()) {
			ecoHeader = (KETBomEcoHeader) qr.nextElement();
			Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",ecoHeader.getEcoHeaderNumber(), CommonUtil.getOIDString(ecoHeader), "=============> BOM Eco Header 가 있습니다.");
			isExist = true;
		    }

		    if (isExist) {
			ecaQr = PersistenceHelper.manager.navigate(ecoHeader, "prodECA", KETProdECABomLink.class);
			while (ecaQr.hasMoreElements()) {
			    prodECA = (KETProdChangeActivity) ecaQr.nextElement();
			}

			if (prodECA == null) {
			    ecaQr = PersistenceHelper.manager.navigate(ecoHeader, "moldECA", KETMoldECABomLink.class);
			    while (ecaQr.hasMoreElements()) {
				moldECA = (KETMoldChangeActivity) ecaQr.nextElement();
			    }

			    if (moldECA != null) {
				Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",ecoHeader.getEcoHeaderNumber(), CommonUtil.getOIDString(moldECA), "=============> BOM Eco Header 가 있습니다->Mold ECA 존재.");
				ecoQr = PersistenceHelper.manager.navigate(moldECA, "moldECO", KETMoldChangeActivityLink.class);
				while (ecoQr.hasMoreElements()) {
				    moldECO = (KETMoldChangeOrder) ecoQr.nextElement();
				}

				if (moldECO.getLifeCycleState().getDisplay().equals("승인완료")) {
				    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",moldECO.getEcoId(), CommonUtil.getOIDString(moldECO), "=============> BOM Eco Header 가 있습니다->Mold ECA 존재.-->MoldECO 승인완료");
				    isExist = false;
				}
				else {
				    ALERT_MESSAGE += partNo + "[" + partVersion + "]" + " 는 " + moldECO.getEcoId() + " 에서 작업중입니다.";

				}
			    }
			    else {
				Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",ecoHeader.getEcoHeaderNumber(), CommonUtil.getOIDString(ecoHeader), "=============> BOM Eco Header 가 있습니다->Mold ECA 존재안함.");
				isExist = false;
			    }
			}
			else {
			    ecoQr = PersistenceHelper.manager.navigate(prodECA, "prodECO", KETProdChangeActivityLink.class);
			    Kogger.debug(EcmUtil.class, "existEcaBomLink(String partNo, String partVersion)",ecoHeader.getEcoHeaderNumber(), CommonUtil.getOIDString(ecoHeader), "=============> BOM Eco Header 가 있습니다->Prod ECA 존재");
			    while (ecoQr.hasMoreElements()) {
				prodECO = (KETProdChangeOrder) ecoQr.nextElement();
			    }

			    if (prodECO.getLifeCycleState().getDisplay().equals("승인완료")) {
				Kogger.debug(EcmUtil.class, "=============> BOM Eco Header 가 있습니다->Prod ECA 존재-->ProdECO 승인완료");
				isExist = false;
			    }
			    else {
				ALERT_MESSAGE += partNo + "[" + partVersion + "]" + " 는 " + prodECO.getEcoId() + " 에서 작업중입니다.";

			    }
			}
		    }
		}
		else {
		    Kogger.debug(EcmUtil.class, "=============> BOM이 존재하지 않습니다. 추가할 수 없습니다");
		    isExist = true;

		    //ALERT_MESSAGE += partNo + "[" + partVersion + "]" + " 는 BOM이 존재하지 않습니다. 추가할 수 없습니다.";

		}
	    }
	} catch (WTException e) {
	    Kogger.error(EcmUtil.class, e);
	}
	Kogger.debug(EcmUtil.class, "=============> 결과 RTN ==> " + isExist);


	return ALERT_MESSAGE;
    }

    public static ArrayList<wt.change2.WTChangeOrder2> getECOexistEcaBomLink(String partNo, String partVersion) {
	ArrayList<WTChangeOrder2> relatedEcoList = null;


	try {

	    // I. 초도일 경우
	    QuerySpec spec = new QuerySpec(KETBomHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.ECO_ITEM_CODE, partNo, 0);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.BOMVERSION, partVersion, 0);

	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		KETBomHeader bomHeader = (KETBomHeader) qr.nextElement();

		QueryResult qr1 = PersistenceHelper.manager.navigate(bomHeader, "moldECA", KETMoldECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qr1.nextElement();
		    KETMoldChangeActivity eca = ketMoldECABomLink.getMoldECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "moldECO", KETMoldChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr2.nextElement();
			KETMoldChangeOrder eco = ketMoldChangeActivityLink.getMoldECO();

			/*
			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
			}
			else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
			}
			*/
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
		    }
		}

		qr1 = PersistenceHelper.manager.navigate(bomHeader, "prodECA", KETProdECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qr1.nextElement();
		    KETProdChangeActivity eca = ketProdECABomLink.getProdECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr2.nextElement();
			KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

			/*
			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
			}
			else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
			}
			*/
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
		    }
		}

	    }

	    // II. 초도가 아닐 경우
	    spec = new QuerySpec(KETBomEcoHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.BOMVERSION, partVersion, 0);

	    qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		KETBomEcoHeader bomHeader = (KETBomEcoHeader) qr.nextElement();

		QueryResult qr1 = PersistenceHelper.manager.navigate(bomHeader, "moldECA", KETMoldECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qr1.nextElement();
		    KETMoldChangeActivity eca = ketMoldECABomLink.getMoldECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "moldECO", KETMoldChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr2.nextElement();
			KETMoldChangeOrder eco = ketMoldChangeActivityLink.getMoldECO();

			/*
			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
			}
			else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
			}
			*/
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
		    }
		}

		qr1 = PersistenceHelper.manager.navigate(bomHeader, "prodECA", KETProdECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qr1.nextElement();
		    KETProdChangeActivity eca = ketProdECABomLink.getProdECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr2.nextElement();
			KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

			/*
			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			// Do nothing..!!
			}
			else {
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			relatedEcoList.add(eco);
			}
			*/
			if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
		    }
		}

	    }


	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}


	return relatedEcoList;
    }

    public static ArrayList<wt.change2.WTChangeOrder2> getWorkingECOexistEcaBomLink(String partNo, String partVersion) {
	ArrayList<WTChangeOrder2> relatedEcoList = null;


	try {

	    // I. 초도일 경우
	    QuerySpec spec = new QuerySpec(KETBomHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.ECO_ITEM_CODE, partNo, 0);
	    SearchUtil.appendEQUAL(spec, KETBomHeader.class, KETBomHeader.BOMVERSION, partVersion, 0);

	    QueryResult qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		KETBomHeader bomHeader = (KETBomHeader) qr.nextElement();

		QueryResult qr1 = PersistenceHelper.manager.navigate(bomHeader, "moldECA", KETMoldECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qr1.nextElement();
		    KETMoldChangeActivity eca = ketMoldECABomLink.getMoldECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "moldECO", KETMoldChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr2.nextElement();
			KETMoldChangeOrder eco = ketMoldChangeActivityLink.getMoldECO();

			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			    // Do nothing..!!
			}
			else {
			    if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			    if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
			}

		    }
		}

		qr1 = PersistenceHelper.manager.navigate(bomHeader, "prodECA", KETProdECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qr1.nextElement();
		    KETProdChangeActivity eca = ketProdECABomLink.getProdECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr2.nextElement();
			KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			    // Do nothing..!!
			}
			else {
			    if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			    if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
			}

		    }
		}

	    }

	    // II. 초도가 아닐 경우
	    spec = new QuerySpec(KETBomEcoHeader.class);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, partNo, 0);
	    SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class, KETBomEcoHeader.BOMVERSION, partVersion, 0);

	    qr = PersistenceHelper.manager.find(spec);
	    while (qr.hasMoreElements()) {
		KETBomEcoHeader bomHeader = (KETBomEcoHeader) qr.nextElement();

		QueryResult qr1 = PersistenceHelper.manager.navigate(bomHeader, "moldECA", KETMoldECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qr1.nextElement();
		    KETMoldChangeActivity eca = ketMoldECABomLink.getMoldECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "moldECO", KETMoldChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr2.nextElement();
			KETMoldChangeOrder eco = ketMoldChangeActivityLink.getMoldECO();

			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			    // Do nothing..!!
			}
			else {
			    if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			    if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
			}

		    }
		}

		qr1 = PersistenceHelper.manager.navigate(bomHeader, "prodECA", KETProdECABomLink.class, false);
		while (qr1.hasMoreElements()) {
		    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qr1.nextElement();
		    KETProdChangeActivity eca = ketProdECABomLink.getProdECA();

		    QueryResult qr2 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		    while (qr2.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr2.nextElement();
			KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

			String state = eco.getLifeCycleState().toString();
			if (state != null && state.equals("APPROVED")) {
			    // Do nothing..!!
			}
			else {
			    if (relatedEcoList == null) relatedEcoList = new ArrayList<WTChangeOrder2>();
			    if (!relatedEcoList.contains(eco)) relatedEcoList.add(eco);
			}

		    }
		}

	    }


	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}


	return relatedEcoList;
    }

    /**
     * 함수명 : existMoldEcaDocLink 설명 : ECO에 포함된 문서 중복 여부 확인하는 함수
     * 
     * @param bomObj
     *            - 문서 객체
     * @return existYn - ECO에 관련된 문서 중복 여부 작성자 : 안수학 작성일자 : 2010. 12. 23.
     */
    public static boolean existEcaDocLink(Persistable docObj) {
	QueryResult qr = null;
	boolean existYn = false;
	boolean isWorking = false;

	try {

	    KETProjectDocument doc = (KETProjectDocument) docObj;
	    KETMoldChangeActivity moldECA = null;
	    KETProdChangeActivity prodECA = null;

	    String majorVersion = VersionUtil.getMajorVersion(doc);
	    String stateStr = doc.getLifeCycleState().getDisplay();
	    Kogger.debug(EcmUtil.class, "==> 확인할 문서 : " + doc.getNumber() + " , 버전 : " + majorVersion);
	    if (Integer.parseInt(majorVersion) > 0 && !stateStr.equalsIgnoreCase("승인완료")) {
		Kogger.debug(EcmUtil.class, "==> 해당도면은 작업중입니다.");
		isWorking = true;
	    }

	    if (!isWorking) {
		qr = PersistenceHelper.manager.navigate(docObj, "moldECA", KETMoldECADocLink.class);

		if (qr.hasMoreElements()) {
		    Kogger.debug(EcmUtil.class, "==> 해당문서는 현재 금형 ECO에 있습니다.");
		    moldECA = (KETMoldChangeActivity) qr.nextElement();
		    if (!(moldECA.getLifeCycleState().getDisplay().equals("승인완료"))) {
			existYn = true;
		    }
		}

		if (!existYn) {
		    qr = PersistenceHelper.manager.navigate(docObj, "prodECA", KETProdECADocLink.class);

		    if (qr.hasMoreElements()) {
			Kogger.debug(EcmUtil.class, "==> 해당문서는 현재 제품 ECO에 있습니다.");
			prodECA = (KETProdChangeActivity) qr.nextElement();
			if (!(prodECA.getLifeCycleState().getDisplay().equals("승인완료"))) {
			    existYn = true;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}
	Kogger.debug(EcmUtil.class, "==> 해당문서 현재 결과 : " + existYn);
	return existYn;
    }

    public static Hashtable<String, String> getCoWorkerInfo(String ecoId, String partNo, String workerOid) {
	Hashtable<String, String> coWorkerHash = new Hashtable<String, String>();
	try {
	    WTUser worker = (WTUser) KETObjectUtil.getObject(workerOid);
	    PeopleData pData = new PeopleData(worker);

	    if (worker != null) {
		coWorkerHash.put("part_no", partNo);
		coWorkerHash.put("eco_id", ecoId);
		coWorkerHash.put("worker_id", worker.getName());
		coWorkerHash.put("worker_name", worker.getFullName());
		coWorkerHash.put("worker_dept", pData.departmentName);
		coWorkerHash.put("worker_email", worker.getEMail());
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return coWorkerHash;
    }

    public static String getEpmDocAfterOid(String preOid, String version) {
	String afterOid = "";

	try {
	    EPMDocument epmDoc = (EPMDocument) KETObjectUtil.getObject(preOid);

	    Master master = (Master) epmDoc.getMaster();
	    EPMDocument afterEpmDoc = (EPMDocument) VersionUtil.getVersionObject(master, version);

	    afterOid = KETObjectUtil.getOidString(afterEpmDoc);
	} catch (WTException e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return afterOid;
    }

    public static WTConnection getWTConnection() throws Exception {
	MethodContext mContext = null;
	WTConnection wtConn = null;

	mContext = MethodContext.getContext();
	wtConn = (WTConnection) mContext.getConnection();

	return wtConn;
    }

    public static void freeWTConnection(WTConnection wtConn) throws Exception {
	if (DBProperties.FREE_CONNECTION_IMMEDIATE && !wtConn.isTransactionActive()) {
	    MethodContext.getContext().freeConnection();
	}
    }

    /**
     * 함수명 : isExistRelationMoldEco 설명 : 제품 ECO에 연결된 금형 ECO가 존재하는지 확인하는 함수
     * 
     * @param prodEcoOid
     * @return 작성자 : 오승연 작성일자 : 2011. 1. 13.
     */
    public static boolean isExistRelationMoldEco(String prodEcoOid) {
	boolean isExist = false;

	try {
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);

	    QueryResult qr = null;
	    qr = PersistenceHelper.manager.navigate(prodEco, "moldECO", KETMoldProdEcoLink.class);

	    while (qr.hasMoreElements()) {
		isExist = true;
		break;
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return isExist;
    }

    /**
     * 함수명 : isExistProdEco 설명 : 제품 ECR에 연결된 제품 ECO가 있는지 확인하는 함수
     * 
     * @param prodEcrOid
     * @return 작성자 : 오승연 작성일자 : 2011. 1. 13.
     */
    public static boolean isExistProdEco(String prodEcrOid) {
	boolean isExist = false;

	try {
	    KETProdChangeRequest prodEcr = (KETProdChangeRequest) KETObjectUtil.getObject(prodEcrOid);

	    QueryResult qr = null;
	    qr = PersistenceHelper.manager.navigate(prodEcr, "prodECO", KETProdChangeLink.class);

	    while (qr.hasMoreElements()) {
		isExist = true;
		break;
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return isExist;
    }

    /**
     * 함수명 : isExistMoldEco 설명 : 금형 ECR에 연결된 금형 ECO가 있는지 확인하는 함수
     * 
     * @param moldEcrOid
     * @return 작성자 : 오승연 작성일자 : 2011. 1. 13.
     */
    public static boolean isExistMoldEco(String moldEcrOid) {
	boolean isExist = false;

	try {
	    KETMoldChangeRequest moldEcr = (KETMoldChangeRequest) KETObjectUtil.getObject(moldEcrOid);

	    QueryResult qr = null;
	    qr = PersistenceHelper.manager.navigate(moldEcr, "moldECO", KETMoldChangeLink.class);

	    while (qr.hasMoreElements()) {
		isExist = true;
		break;
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return isExist;
    }

    /**
     * 함수명 : getEPMDocumentOid 설명 : 도번과 버전으로 해당 도면의 Oid를 가져오는 함수
     * 
     * @param drawingNo
     * @param version
     * @return 작성자 : 오승연 작성일자 : 2011. 2. 7.
     */
    public static String getEPMDocumentOid(String drawingNo, String version) {
	String epmDocOid = "";
	EPMDocument epmDoc = null;

	try {
	    QuerySpec qs = new QuerySpec(EPMDocument.class);

	    qs.appendWhere(VersionControlHelper.getSearchCondition(EPMDocument.class, true), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(EPMDocument.class, "master>number", "=", drawingNo), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(EPMDocument.class, "versionInfo.identifier.versionId", "=", version), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    if (qr.hasMoreElements()) {
		epmDoc = (EPMDocument) qr.nextElement();
		epmDocOid = KETObjectUtil.getOidString(epmDoc);
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return epmDocOid;
    }

    /**
     * 함수명 : getPartOid 설명 : Part Number와 version으로 해당 Part의 Oid를 가져오는 함수
     * 
     * @param partNo
     * @param version
     * @return 작성자 : 오승연 작성일자 : 2011. 2. 7.
     */
    public static String getPartOid(String partNo, String version) {
	String partOid = "";

	KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	try {
	    partOid = ketBOMQueryBean.getPartOid(partNo, version);
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	/*
	WTPart part = null;
	try {
	    QuerySpec qs = new QuerySpec(WTPart.class);

	    qs.appendWhere(VersionControlHelper.getSearchCondition(WTPart.class, true), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "master>number", "=", partNo), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(WTPart.class, "versionInfo.identifier.versionId", "=", version), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    if (qr.hasMoreElements()) {
		part = (WTPart) qr.nextElement();
		partOid = KETObjectUtil.getOidString(part);
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}
	*/

	return partOid;
    }

    /**
     * 함수명 : getDocumentOid 설명 : 문서 ID와 버전으로 해당 Oid 가져오는 함수
     * 
     * @param docNo
     * @param version
     * @return 작성자 : 오승연 작성일자 : 2011. 2. 28.
     */
    public static String getDocumentOid(String docNo, String version) {
	String docOid = "";
	KETProjectDocument document = null;

	try {
	    QuerySpec qs = new QuerySpec(KETProjectDocument.class);

	    qs.appendWhere(VersionControlHelper.getSearchCondition(KETProjectDocument.class, true), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETProjectDocument.class, "master>number", "=", docNo), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(KETProjectDocument.class, "versionInfo.identifier.versionId", "=", version), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    if (qr.hasMoreElements()) {
		document = (KETProjectDocument) qr.nextElement();
		docOid = KETObjectUtil.getOidString(document);
	    }
	} catch (Exception e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return docOid;
    }

    public static String getLastestPartOid(String partNo) {
	String partOid = "";
	WTPart part = null;

	try {
	    part = KETPartHelper.service.getPart(partNo);

	    if (part != null) {
		partOid = KETObjectUtil.getOidString(part);
	    }
	} catch (WTException e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return partOid;
    }

    /**
     * 함수명 : getDocumentAfterOid 설명 : 문서 OID 와 버전으로 해당 Oid 가져오는 함수
     */
    public static String getDocumentAfterOid(String preOid, String version) {
	String afterOid = "";

	try {
	    KETProjectDocument pre = (KETProjectDocument) KETObjectUtil.getObject(preOid);

	    Master master = (Master) pre.getMaster();
	    KETProjectDocument after = (KETProjectDocument) VersionUtil.getVersionObject(master, version);

	    afterOid = KETObjectUtil.getOidString(after);
	} catch (WTException e) {
	    Kogger.error(EcmUtil.class, e);
	}

	return afterOid;
    }

}
