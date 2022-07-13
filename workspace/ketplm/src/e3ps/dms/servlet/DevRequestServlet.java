/* 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : DevRequestServlet.java
 * 작성자 : 김경종
 * 작성일자 : 2010. 11
 */
package e3ps.dms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.content.ApplicationData;
import wt.org.WTPrincipalReference;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.dms.dao.DevRequestDao;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.service.KETDmsHelper;
import ext.ket.shared.log.Kogger;

/* 클래스명 : DevRequestServlet
 * 설명 : 개발검토의뢰서생성수정삭제
 * 작성자 : 김경종
 * 작성일자 : 2010. 11
 */
public class DevRequestServlet extends CommonServlet {
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	String cmd = req.getParameter("cmd");

	Kogger.debug(getClass(), "cmd----->" + cmd);

	String msg = null;
	String param = "";
	if (cmd.equals("create")) {

	    msg = create(req, res);
	    if (msg == "f") {
		msg = "개발(검토)의뢰서생성에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewDevRequest.jsp" + param);
	    }
	} else if (cmd.equals("update")) {
	    msg = update(req, res);
	    if (msg == "f") {
		msg = "개발(검토)의뢰서수정에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);

		alertNgo(res, msg, "/plm/jsp/dms/ViewDevRequest.jsp" + param);
	    }
	} else if (cmd.equals("delete")) {
	    msg = delete(req, res);
	    if (msg == "f") {
		msg = "개발(검토)의뢰서삭제에 실패하였습니다.";
		alert(res, msg);
	    } else {
		alertNclose(res, msg);

	    }
	} else if (cmd.equals("search")) {
	    search(req, res);

	}
    }

    /*
     * 함수명 : create 설명 : 개발검토의뢰서생성 작성자 : 김경종 작성일자 : 2010. 11
     */
    private String create(HttpServletRequest req, HttpServletResponse res) {
	KETDevelopmentRequest dr = null;
	String returnTemp = null;
	Hashtable tempHash = new Hashtable();

	String tmpStr = null;
	String sfiles = null;
	FormUploader uploader = null;
	uploader = FormUploader.newFormUploader(req);
	Hashtable param = uploader.getFormParameters();

	Kogger.debug(getClass(), param.get("targetAverageRate"));
	String flag = (String) param.get("DevelopmentStep");
	// Kogger.debug(getClass(), "param----->"+param);
	try {
	    
	    tempHash.put("DivisionCode", new String(StringUtil.checkNull((String) param.get("divCode"))));
	    tempHash.put("DevelopmentStep", new String(StringUtil.checkNull((String) param.get("DevelopmentStep"))));
	    tempHash.put("ProjectOID", new String(StringUtil.checkNull((String) param.get("ProjectOID"))));

	    tempHash.put("reception", new String(StringUtil.checkNull((String) param.get("reception"))));

	    tempHash.put("RequestBuyer", new String(StringUtil.checkNull((String) param.get("RequestBuyerStr"))));
	    tempHash.put("RequestBuyerManager", new String(StringUtil.checkNull((String) param.get("RequestBuyerManager"))));
	    tempHash.put("LastUsingBuyer", new String(StringUtil.checkNull((String) param.get("LastUsingBuyerStr"))));
	    tempHash.put("LastUsingBuyerManager", new String(StringUtil.checkNull((String) param.get("LastUsingBuyerManager"))));

	    tempHash.put("RepCarType", new String(StringUtil.checkNull((String) param.get("RepCarType"))));

	    tempHash.put("IsDRRequest", new String(StringUtil.checkNull((String) param.get("IsDRRequest"))));
	    tempHash.put("DrRequestDate", new String(StringUtil.checkNull((String) param.get("DrRequestDate"))));
	    tempHash.put("IsProposalSubmit", new String(StringUtil.checkNull((String) param.get("IsProposalSubmit"))));
	    tempHash.put("ProposalSubmitDate", new String(StringUtil.checkNull((String) param.get("ProposalSubmitDate"))));
	    tempHash.put("IsCostSubmit", new String(StringUtil.checkNull((String) param.get("IsCostSubmit"))));
	    tempHash.put("CostSubmitDate", new String(StringUtil.checkNull((String) param.get("CostSubmitDate"))));

	    tempHash.put("DevProductName", new String(StringUtil.checkNull((String) param.get("DevProductName"))));
	    tempHash.put("DevReviewTypeCode", new String(StringUtil.checkNull((String) param.get("DevReviewTypeCode"))));
	    tempHash.put("DevReviewTypeEtc", new String(StringUtil.checkNull((String) param.get("DevReviewTypeEtc"))));
	    tempHash.put("DevReviewDetailComment", new String(StringUtil.checkNull((String) param.get("DevReviewDetailComment"))));

	    tempHash.put("ProductSaleFirstYear", new String(StringUtil.checkNull((String) param.get("ProductSaleFirstYear"))));

	    tempHash.put("InitialSampleSummitDate", new String(StringUtil.checkNull((String) param.get("InitialSampleSummitDate"))));
	    tempHash.put("ESIRDate", new String(StringUtil.checkNull((String) param.get("ESIRDate"))));
	    tempHash.put("ISIRDate", new String(StringUtil.checkNull((String) param.get("ISIRDate"))));
	    tempHash.put("KetMassProductionDate", new String(StringUtil.checkNull((String) param.get("KetMassProductionDate"))));

	    tempHash.put("ProductCategoryCode", new String(StringUtil.checkNull((String) param.get("ProductCategoryCode"))));
	    tempHash.put("ProductTypeCode", new String(StringUtil.checkNull((String) param.get("ProductTypeCodes"))));

	    tempHash.put("EtcSpecification", new String(StringUtil.checkNull((String) param.get("EtcSpecification"))));
	    tempHash.put("TabSize", new String(StringUtil.checkNull((String) param.get("TabSize"))));
	    tempHash.put("MaterialSubMaterial", new String(StringUtil.checkNull((String) param.get("MaterialSubMaterial"))));
	    tempHash.put("SurfaceTreatmentCode", new String(StringUtil.checkNull((String) param.get("SurfaceTreatmentCode"))));
	    tempHash.put("ApplyedWire", new String(StringUtil.checkNull((String) param.get("ApplyedWire"))));
	    tempHash.put("PrimaryFunction", new String(StringUtil.checkNull((String) param.get("PrimaryFunction"))));
	    tempHash.put("Outlook", new String(StringUtil.checkNull((String) param.get("Outlook"))));

	    tempHash.put("MoldDepreciationTypeSale", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeSale"))));
	    tempHash.put("MoldDepreciationTypeGeneral", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeGeneral"))));
	    tempHash.put("MoldDepreciationTypePayment", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypePayment"))));
	    tempHash.put("MoldDepreciationTypePeriod", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypePeriod"))));
	    tempHash.put("MoldDepreciationTypeEtcInfo", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeEtcInfo"))));

	    tempHash.put("EquipDepreciationTypeSale", new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypeSale"))));
	    tempHash.put("EquipDepreciationTypePayment",
		    new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypePayment"))));
	    tempHash.put("EquipDepreciationTypePeriod", new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypePeriod"))));
	    tempHash.put("EquipDepreciationTypeEtcInfo",
		    new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypeEtcInfo"))));

	    tempHash.put("DeviceSpecification", new String(StringUtil.checkNull((String) param.get("DeviceSpecification"))));
	    tempHash.put("EnvironmentalRegulationItem", new String(StringUtil.checkNull((String) param.get("EnvironmentalRegulationItem"))));
	    tempHash.put("BuyerEtcRequirement", new String(StringUtil.checkNull((String) param.get("BuyerEtcRequirement"))));
	    tempHash.put("SalesAdditionalRequirement", new String(StringUtil.checkNull((String) param.get("SalesAdditionalRequirement"))));
	    tempHash.put("attribute1", new String(StringUtil.checkNull((String) param.get("attribute1"))));
	    tempHash.put("attribute2", new String(StringUtil.checkNull((String) param.get("attribute2")))); // 차종
	    tempHash.put("attribute3", new String(StringUtil.checkNull((String) param.get("attribute3")))); // 적용부위
	    tempHash.put("attribute4", new String(StringUtil.checkNull((String) param.get("attribute4")))); // 개발부품
	    tempHash.put("attribute5", new String(StringUtil.checkNull((String) param.get("attribute5")))); // 개발일정
	    tempHash.put("attribute6", new String(StringUtil.checkNull((String) param.get("attribute6")))); // 양산투자비
	    tempHash.put("attribute7", new String(StringUtil.checkNull((String) param.get("attribute7")))); // 납입처
	    tempHash.put("attribute8", new String(StringUtil.checkNull((String) param.get("attribute8")))); // 예상매출액
	    tempHash.put("attribute9", new String(StringUtil.checkNull((String) param.get("attribute9")))); // 기타
	    
	    tempHash.put("attribute10", new String(StringUtil.checkNull((String) param.get("attribute10")))); 
	    tempHash.put("attribute11", new String(StringUtil.checkNull((String) param.get("attribute11"))));
	    
	    tempHash.put("InitialSampleSummitDate2", new String(StringUtil.checkNull((String) param.get("InitialSampleSummitDate2"))));
	    tempHash.put("ESIRDate2", new String(StringUtil.checkNull((String) param.get("ESIRDate2"))));
	    tempHash.put("ISIRDate2", new String(StringUtil.checkNull((String) param.get("ISIRDate2"))));
	    tempHash.put("ProductSaleFirstYear2", new String(StringUtil.checkNull((String) param.get("ProductSaleFirstYear2"))));
	    tempHash.put("KetMassProductionDate2", new String(StringUtil.checkNull((String) param.get("KetMassProductionDate2"))));
	    tempHash.put("scheduleName", new String(StringUtil.checkNull((String) param.get("scheduleName"))));

	    Kogger.debug(getClass(), "attribute1 ==== " + StringUtil.checkNull((String) param.get("attribute1")));
	    
	    tempHash.put("relatedSalesProjectOid", new String(StringUtil.checkNull((String) param.get("relatedSalesProjectOid"))));

	    Object tId = param.get("tId");
	    if (tId instanceof Vector) {
		tempHash.put("tId", new Vector((Vector) tId));
		tempHash.put("partName", new Vector((Vector) param.get("partName")));
		tempHash.put("appliedRegion", new Vector((Vector) param.get("appliedRegion")));
		tempHash.put("currentApplyPart", new Vector((Vector) param.get("currentApplyPart")));
		tempHash.put("PackTypeCode", new Vector((Vector) param.get("PackTypeCode")));
		tempHash.put("summitDestination", new Vector((Vector) param.get("summitDestinationOid")));
		tempHash.put("buyerAcceptPrice", new Vector((Vector) param.get("buyerAcceptPrice")));
		tempHash.put("ketTargetPrice", new Vector((Vector) param.get("ketTargetPrice")));
		tempHash.put("targetEarningRate", new Vector((Vector) param.get("targetEarningRate")));
		if ("D".equals(flag)) {
		    tempHash.put("targetAverageRate", new Vector((Vector) param.get("targetAverageRate")));
		}
		tempHash.put("targetInvestmentCost", new Vector((Vector) param.get("targetInvestmentCost")));
		tempHash.put("targetTermRate", new Vector((Vector) param.get("targetTermRate")));

	    } else {
		tempHash.put("tId", new String((String) tId));
		tempHash.put("partName", new String((String) param.get("partName")));
		tempHash.put("appliedRegion", new String((String) param.get("appliedRegion")));
		tempHash.put("currentApplyPart", new String((String) param.get("currentApplyPart")));
		tempHash.put("PackTypeCode", new String((String) param.get("PackTypeCode")));
		tempHash.put("summitDestination", new String((String) param.get("summitDestinationOid")));
		tempHash.put("buyerAcceptPrice", new String((String) param.get("buyerAcceptPrice")));
		tempHash.put("ketTargetPrice", new String((String) param.get("ketTargetPrice")));
		tempHash.put("targetEarningRate", new String((String) param.get("targetEarningRate")));
		if ("D".equals(flag)) {
		    tempHash.put("targetAverageRate", new String((String) param.get("targetAverageRate")));
		}
		tempHash.put("targetInvestmentCost", new String((String) param.get("targetInvestmentCost")));
		tempHash.put("targetTermRate", new String((String) param.get("targetTermRate")));
	    }

	    Object tId1 = param.get("tId1");
	    if (tId1 instanceof Vector) {
		tempHash.put("tId1", new Vector((Vector) tId1));
		tempHash.put("y1", new Vector((Vector) param.get("y1")));
		tempHash.put("y2", new Vector((Vector) param.get("y2")));
		tempHash.put("y3", new Vector((Vector) param.get("y3")));
		tempHash.put("y4", new Vector((Vector) param.get("y4")));
		tempHash.put("y5", new Vector((Vector) param.get("y5")));
		tempHash.put("y6", new Vector((Vector) param.get("y6")));
		tempHash.put("sum", new Vector((Vector) param.get("sum")));
		tempHash.put("usage", new Vector((Vector) param.get("usage")));
		tempHash.put("optRate", new Vector((Vector) param.get("optRate")));
		tempHash.put("carTypeOid", new Vector((Vector) param.get("carTypeOid")));
		tempHash.put("carTypeCode", new Vector((Vector) param.get("carTypeCode")));

	    } else {
		tempHash.put("tId1", new String((String) tId1));
		tempHash.put("y1", new String((String) param.get("y1")));
		tempHash.put("y2", new String((String) param.get("y2")));
		tempHash.put("y3", new String((String) param.get("y3")));
		tempHash.put("y4", new String((String) param.get("y4")));
		tempHash.put("y5", new String((String) param.get("y5")));
		tempHash.put("y6", new String((String) param.get("y6")));
		tempHash.put("sum", new String((String) param.get("sum")));
		tempHash.put("usage", new String((String) param.get("usage")));
		tempHash.put("optRate", new String((String) param.get("optRate")));
		tempHash.put("carTypeOid", new String((String) param.get("carTypeOid")));
		tempHash.put("carTypeCode", new String((String) param.get("carTypeCode")));
	    }

	    Kogger.debug(getClass(), "tempHash----->" + tempHash);

	    dr = KETDmsHelper.service.insertDevRequest(tempHash);

	    Kogger.debug(getClass(), "dr----->" + dr.getDevProductName());

	    if (dr != null) {
		// 첨부파일 정보는 문서저장후 attach시킨다.
		sfiles = (String) param.get("fileContents");
		Kogger.debug(getClass(), "sfiles----->" + sfiles);
		if (sfiles == null || sfiles.equals("0")) {

		} else {

		    sfiles = sfiles.substring(2);
		    String delFile = "";
		    ApplicationData appFile = null;
		    StringTokenizer st = new StringTokenizer(sfiles, "/");
		    while (st.hasMoreTokens()) {
			delFile = st.nextToken();
			Kogger.debug(getClass(), "delFile----->" + delFile);
			appFile = (ApplicationData) CommonUtil.getObject((String) delFile);
			E3PSContentHelper.service.attach(dr, appFile, false);
		    }
		}

		Vector files = uploader.getFiles();
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(dr, file, "", isPrimary);
		    }
		}

		returnTemp = CommonUtil.getOIDString(dr);
		returnTemp = "의뢰서가 생성되었습니다.oid=" + returnTemp;
		Kogger.debug(getClass(), "Servlet Create devRequest=====>" + returnTemp);
	    } else {
		returnTemp = "f";
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    /*
     * 함수명 : update 설명 : 개발검토의뢰수정 작성자 : 김경종 작성일자 : 2010. 11
     */
    private String update(HttpServletRequest req, HttpServletResponse res) {
	KETDevelopmentRequest dr = null;
	String returnTemp = null;
	Hashtable tempHash = new Hashtable();

	String tmpStr = null;
	String drOid = null;
	String sfiles = null;

	try {
	    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	FormUploader uploader = null;
	uploader = FormUploader.newFormUploader(req);
	Hashtable param = uploader.getFormParameters();

	String flag = (String) param.get("DevelopmentStep");
	tempHash.put("DevelopmentStep", flag);

	try {

	    drOid = (String) param.get("drOid");
	    Kogger.debug(getClass(), "upd drOid===>" + drOid);

	    // Param Hashtable에 데이터를 저장용 임시 Hashtable에 저장한다.
	    tempHash.put("drOid", new String(drOid));
	    dr = (KETDevelopmentRequest) CommonUtil.getObject(drOid);

	    tempHash.put("ProjectOID", new String(StringUtil.checkNull((String) param.get("ProjectOID"))));
	    tempHash.put("reception", new String(StringUtil.checkNull((String) param.get("reception"))));

	    tempHash.put("RequestBuyer", new String(StringUtil.checkNull((String) param.get("RequestBuyerStr"))));
	    tempHash.put("RequestBuyerManager", new String(StringUtil.checkNull((String) param.get("RequestBuyerManager"))));
	    tempHash.put("LastUsingBuyer", new String(StringUtil.checkNull((String) param.get("LastUsingBuyerStr"))));
	    tempHash.put("LastUsingBuyerManager", new String(StringUtil.checkNull((String) param.get("LastUsingBuyerManager"))));
	    tempHash.put("RepCarType", new String(StringUtil.checkNull((String) param.get("RepCarType"))));

	    tempHash.put("IsDRRequest", new String(StringUtil.checkNull((String) param.get("IsDRRequest"))));
	    tempHash.put("DrRequestDate", new String(StringUtil.checkNull((String) param.get("DrRequestDate"))));
	    tempHash.put("IsProposalSubmit", new String(StringUtil.checkNull((String) param.get("IsProposalSubmit"))));
	    tempHash.put("ProposalSubmitDate", new String(StringUtil.checkNull((String) param.get("ProposalSubmitDate"))));
	    tempHash.put("IsCostSubmit", new String(StringUtil.checkNull((String) param.get("IsCostSubmit"))));
	    tempHash.put("CostSubmitDate", new String(StringUtil.checkNull((String) param.get("CostSubmitDate"))));

	    tempHash.put("DevProductName", new String(StringUtil.checkNull((String) param.get("DevProductName"))));
	    tempHash.put("DevReviewTypeCode", new String(StringUtil.checkNull((String) param.get("DevReviewTypeCode"))));
	    tempHash.put("DevReviewTypeEtc", new String(StringUtil.checkNull((String) param.get("DevReviewTypeEtc"))));
	    tempHash.put("DevReviewDetailComment", new String(StringUtil.checkNull((String) param.get("DevReviewDetailComment"))));

	    tempHash.put("ProductSaleFirstYear", new String(StringUtil.checkNull((String) param.get("ProductSaleFirstYear"))));
	    tempHash.put("InitialSampleSummitDate", new String(StringUtil.checkNull((String) param.get("InitialSampleSummitDate"))));
	    tempHash.put("ESIRDate", new String(StringUtil.checkNull((String) param.get("ESIRDate"))));
	    tempHash.put("ISIRDate", new String(StringUtil.checkNull((String) param.get("ISIRDate"))));
	    tempHash.put("KetMassProductionDate", new String(StringUtil.checkNull((String) param.get("KetMassProductionDate"))));

	    tempHash.put("ProductCategoryCode", new String(StringUtil.checkNull((String) param.get("ProductCategoryCode"))));
	    tempHash.put("ProductTypeCode", new String(StringUtil.checkNull((String) param.get("ProductTypeCodes"))));

	    tempHash.put("EtcSpecification", new String(StringUtil.checkNull((String) param.get("EtcSpecification"))));
	    tempHash.put("TabSize", new String(StringUtil.checkNull((String) param.get("TabSize"))));
	    tempHash.put("MaterialSubMaterial", new String(StringUtil.checkNull((String) param.get("MaterialSubMaterial"))));
	    tempHash.put("SurfaceTreatmentCode", new String(StringUtil.checkNull((String) param.get("SurfaceTreatmentCode"))));
	    tempHash.put("ApplyedWire", new String(StringUtil.checkNull((String) param.get("ApplyedWire"))));
	    tempHash.put("PrimaryFunction", new String(StringUtil.checkNull((String) param.get("PrimaryFunction"))));
	    tempHash.put("Outlook", new String(StringUtil.checkNull((String) param.get("Outlook"))));
	    tempHash.put("MoldDepreciationTypeSale", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeSale"))));
	    tempHash.put("MoldDepreciationTypeGeneral", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeGeneral"))));
	    tempHash.put("MoldDepreciationTypePayment", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypePayment"))));
	    tempHash.put("MoldDepreciationTypePeriod", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypePeriod"))));
	    tempHash.put("MoldDepreciationTypeEtcInfo", new String(StringUtil.checkNull((String) param.get("MoldDepreciationTypeEtcInfo"))));

	    tempHash.put("EquipDepreciationTypeSale", new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypeSale"))));
	    tempHash.put("EquipDepreciationTypePayment",
		    new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypePayment"))));
	    tempHash.put("EquipDepreciationTypePeriod", new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypePeriod"))));
	    tempHash.put("EquipDepreciationTypeEtcInfo",
		    new String(StringUtil.checkNull((String) param.get("EquipDepreciationTypeEtcInfo"))));

	    tempHash.put("DeviceSpecification", new String(StringUtil.checkNull((String) param.get("DeviceSpecification"))));
	    tempHash.put("EnvironmentalRegulationItem", new String(StringUtil.checkNull((String) param.get("EnvironmentalRegulationItem"))));
	    tempHash.put("BuyerEtcRequirement", new String(StringUtil.checkNull((String) param.get("BuyerEtcRequirement"))));
	    tempHash.put("SalesAdditionalRequirement", new String(StringUtil.checkNull((String) param.get("SalesAdditionalRequirement"))));
	    tempHash.put("attribute2", new String(StringUtil.checkNull((String) param.get("attribute2")))); // 차종
	    tempHash.put("attribute3", new String(StringUtil.checkNull((String) param.get("attribute3")))); // 적용부위
	    tempHash.put("attribute4", new String(StringUtil.checkNull((String) param.get("attribute4")))); // 개발부품
	    tempHash.put("attribute5", new String(StringUtil.checkNull((String) param.get("attribute5")))); // 개발일정
	    tempHash.put("attribute6", new String(StringUtil.checkNull((String) param.get("attribute6")))); // 양산투자비
	    tempHash.put("attribute7", new String(StringUtil.checkNull((String) param.get("attribute7")))); // 납입처
	    tempHash.put("attribute8", new String(StringUtil.checkNull((String) param.get("attribute8")))); // 예상매출액
	    tempHash.put("attribute9", new String(StringUtil.checkNull((String) param.get("attribute9")))); // 기타
	    tempHash.put("attribute10", new String(StringUtil.checkNull((String) param.get("attribute10")))); 
	    tempHash.put("attribute11", new String(StringUtil.checkNull((String) param.get("attribute11")))); 
	    tempHash.put("InitialSampleSummitDate2", new String(StringUtil.checkNull((String) param.get("InitialSampleSummitDate2"))));
	    tempHash.put("ESIRDate2", new String(StringUtil.checkNull((String) param.get("ESIRDate2"))));
	    tempHash.put("ISIRDate2", new String(StringUtil.checkNull((String) param.get("ISIRDate2"))));
	    tempHash.put("ProductSaleFirstYear2", new String(StringUtil.checkNull((String) param.get("ProductSaleFirstYear2"))));
	    tempHash.put("KetMassProductionDate2", new String(StringUtil.checkNull((String) param.get("KetMassProductionDate2"))));
	    tempHash.put("scheduleName", new String(StringUtil.checkNull((String) param.get("scheduleName"))));

	    Object tId = param.get("tId");
	    if (tId instanceof Vector) {
		tempHash.put("tId", new Vector((Vector) tId));
		tempHash.put("partName", new Vector((Vector) param.get("partName")));
		tempHash.put("appliedRegion", new Vector((Vector) param.get("appliedRegion")));
		tempHash.put("currentApplyPart", new Vector((Vector) param.get("currentApplyPart")));
		tempHash.put("PackTypeCode", new Vector((Vector) param.get("PackTypeCode")));
		tempHash.put("summitDestination", new Vector((Vector) param.get("summitDestinationOid")));
		tempHash.put("buyerAcceptPrice", new Vector((Vector) param.get("buyerAcceptPrice")));
		tempHash.put("ketTargetPrice", new Vector((Vector) param.get("ketTargetPrice")));
		tempHash.put("targetEarningRate", new Vector((Vector) param.get("targetEarningRate")));
		if ("D".equals(flag)) {
		    tempHash.put("targetAverageRate", new Vector((Vector) param.get("targetAverageRate")));
		}
		tempHash.put("targetInvestmentCost", new Vector((Vector) param.get("targetInvestmentCost")));
		tempHash.put("targetTermRate", new Vector((Vector) param.get("targetTermRate")));

	    } else {
		tempHash.put("tId", new String((String) tId));
		tempHash.put("partName", new String((String) param.get("partName")));
		tempHash.put("appliedRegion", new String((String) param.get("appliedRegion")));
		tempHash.put("currentApplyPart", new String((String) param.get("currentApplyPart")));
		tempHash.put("PackTypeCode", new String((String) param.get("PackTypeCode")));
		tempHash.put("summitDestination", new String((String) param.get("summitDestinationOid")));
		tempHash.put("buyerAcceptPrice", new String((String) param.get("buyerAcceptPrice")));
		tempHash.put("ketTargetPrice", new String((String) param.get("ketTargetPrice")));
		tempHash.put("targetEarningRate", new String((String) param.get("targetEarningRate")));
		if ("D".equals(flag)) {
		    tempHash.put("targetAverageRate", new String((String) param.get("targetAverageRate")));
		}
		tempHash.put("targetInvestmentCost", new String((String) param.get("targetInvestmentCost")));
		tempHash.put("targetTermRate", new String((String) param.get("targetTermRate")));

	    }

	    Object tId1 = param.get("tId1");
	    if (tId1 instanceof Vector) {
		tempHash.put("tId1", new Vector((Vector) tId1));
		tempHash.put("y1", new Vector((Vector) param.get("y1")));
		tempHash.put("y2", new Vector((Vector) param.get("y2")));
		tempHash.put("y3", new Vector((Vector) param.get("y3")));
		tempHash.put("y4", new Vector((Vector) param.get("y4")));
		tempHash.put("y5", new Vector((Vector) param.get("y5")));
		tempHash.put("y6", new Vector((Vector) param.get("y6")));
		tempHash.put("sum", new Vector((Vector) param.get("sum")));
		tempHash.put("usage", new Vector((Vector) param.get("usage")));
		tempHash.put("optRate", new Vector((Vector) param.get("optRate")));
		tempHash.put("carTypeOid", new Vector((Vector) param.get("carTypeOid")));
		tempHash.put("carTypeCode", new Vector((Vector) param.get("carTypeCode")));

	    } else {
		tempHash.put("tId1", new String((String) tId1));
		tempHash.put("y1", new String((String) param.get("y1")));
		tempHash.put("y2", new String((String) param.get("y2")));
		tempHash.put("y3", new String((String) param.get("y3")));
		tempHash.put("y4", new String((String) param.get("y4")));
		tempHash.put("y5", new String((String) param.get("y5")));
		tempHash.put("y6", new String((String) param.get("y6")));
		tempHash.put("sum", new String((String) param.get("sum")));
		tempHash.put("usage", new String((String) param.get("usage")));
		tempHash.put("optRate", new String((String) param.get("optRate")));
		tempHash.put("carTypeOid", new String((String) param.get("carTypeOid")));
		tempHash.put("carTypeCode", new String((String) param.get("carTypeCode")));

	    }

	    // 첨부파일정보는 추가된것과 삭제해야 할것을 구분하여 처리한다.
	    // 삭제정보를 따로 String형태로 받아 삭제해준다.
	    sfiles = (String) param.get("isFileDel");
	    tempHash.put("isFileDel", new String((String) sfiles));

	    // 추가된 첨부파일정보가 있다면 변경 포함하여 attach시킨다.
	    Vector files = uploader.getFiles();

	    if (files == null) {
		tempHash.put("files", new String("0"));
	    } else {
		tempHash.put("files", new Vector((Vector) files));
	    }

	    // 최종수정을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
	    dr = KETDmsHelper.service.updateDevRequest(tempHash);

	    if (dr != null) {
		returnTemp = CommonUtil.getOIDString(dr);
		Kogger.debug(getClass(), "Servlet Updated DevRequestOID=====>" + returnTemp);
		returnTemp = "수정되었습니다.oid=" + returnTemp;
	    } else {
		returnTemp = "f";
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    private String delete(HttpServletRequest req, HttpServletResponse res) {
	String returnTemp = null;
	String drOid = null;
	KETDevelopmentRequest dr = null;
	String drNo = null;
	try {
	    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	try {
	    drOid = req.getParameter("oid");
	    dr = (KETDevelopmentRequest) CommonUtil.getObject(drOid);
	    drNo = dr.getNumber();

	    returnTemp = KETDmsHelper.service.deleteDevRequest(drOid);
	    if (returnTemp != null) {
		Kogger.debug(getClass(), "Servlet Delete DrOID=====>" + returnTemp);
		returnTemp = drNo + ": 삭제되었습니다.";
	    } else {
		returnTemp = "f";
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    private void search(HttpServletRequest req, HttpServletResponse res) {

	Connection conn = null;

	try {

	    /*
	     * [PLM System 1차개선] 수정내용 : ViewDRList() tempHash , resultHash type 변경됨. 수정일자 : 2013. 6. 28 수정자 : 남현승
	     */
	    List<Map<String, String>> resultHash = new ArrayList<Map<String, String>>();

	    String tmpStr = null;

	    // 화면에서 받은 Parameter Map 저장한다.
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil tempHash = KETParamMapUtil.getMap(uploader.getFormParameters());

	    conn = PlmDBUtil.getConnection();
	    DevRequestDao devRequestDao = new DevRequestDao(conn);

	    resultHash = devRequestDao.ViewDRList(tempHash);

	    // req.setAttribute( "condition", tempHash );
	    // req.setAttribute( "docuOids", docuOids);

	    gotoResult(req, res, "/jsp/dms/SearchDocumentList.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}

    }
}
