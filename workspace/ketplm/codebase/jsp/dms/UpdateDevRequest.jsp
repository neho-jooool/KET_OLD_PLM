<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="ext.ket.part.classify.service.PartClazHelper"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.HashMap,
                java.util.List,
                java.util.Map,
                e3ps.dms.service.KETDmsHelper,
                e3ps.dms.entity.KETDevelopmentRequest,
                e3ps.dms.entity.KETRequestPartList,
                e3ps.dms.entity.KETCarYearlyAmount,
                e3ps.dms.entity.KETRequestPartLink,
                e3ps.dms.entity.KETPartCarLink,
                e3ps.project.E3PSProject,
                e3ps.project.ProjectOutput,
                e3ps.common.content.*,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.groupware.company.PeopleData,
                java.util.Vector,
                java.util.StringTokenizer,
                java.util.ArrayList,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                wt.doc.WTDocument,
                wt.doc.WTDocumentMaster,
                wt.part.WTPart,
                wt.content.*,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleManaged,
                wt.org.WTUser,
                wt.session.SessionHelper,
                wt.query.QuerySpec,
                wt.query.SearchCondition,
                wt.query.ClassAttribute,
                wt.query.OrderBy"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/multicombo.jsp" %>
<%
Map<String, Object> parameter = new HashMap<String, Object>();
List<Map<String, Object>> numCode = null;

  String SDevelopmentStep = StringUtil.checkNull(request.getParameter("DevelopmentStep"));
  String SDevProductName = StringUtil.checkNull(request.getParameter("DevProductName"));
  String SRequestBuyer = StringUtil.checkNull(request.getParameter("RequestBuyer"));
  String SLastUsingBuyer = StringUtil.checkNull(request.getParameter("LastUsingBuyer"));
  String SuserName = StringUtil.checkNull(request.getParameter("userName"));
  String SRepCarType = StringUtil.checkNull(request.getParameter("RepCarType"));
  String SauthorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
  String SDevReviewTypeCode = StringUtil.checkNull(request.getParameter("DevReviewTypeCode"));
  String SProductCategoryCode = StringUtil.checkNull(request.getParameter("ProductCategoryCode"));
  String Spredate = StringUtil.checkNull(request.getParameter("predate"));
  String Spostdate = StringUtil.checkNull(request.getParameter("postdate"));
  String SsortKey = StringUtil.checkNull(request.getParameter("sortKey"));
  String SsortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
  String SpageCnt = StringUtil.checkNull(request.getParameter("pageCnt"));
  String Spage = StringUtil.checkNull(request.getParameter("page"));
  String SnowBlock = StringUtil.checkNull(request.getParameter("nowBlock"));
  String isSer = StringUtil.checkNull(request.getParameter("isSer"));

  if( (isSer == null) || (isSer.trim().length() == 0)){
    isSer = "F";
  }
  if( (SsortKey == null) || (SsortKey.trim().length() == 0)){
    SsortKey = "0";
  }
  if( (SsortKeyD == null) || (SsortKeyD.trim().length() == 0)){
    SsortKeyD = "";
  }
  if( (SpageCnt == null) || (SpageCnt.trim().length() == 0)){
    SpageCnt = "10";
  }
  if( (Spage == null) || (Spage.trim().length() == 0)){
    Spage = "0";
  }
  if( (SnowBlock == null) || (SnowBlock.trim().length() == 0)){
    SnowBlock = "0";
  }

  WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
  String userName = user.getFullName();
  PeopleData peoData = new PeopleData(user);
  
  String divCode = "";
  String statusCode =  "";

  String projectType = "";
  boolean isReviewResult = true; 
  
  if(CommonUtil.isMember("전자사업부") && !CommonUtil.isMember("전자사업부_전장IT개발팀") && !CommonUtil.isMember("전자사업부_전장IT영업팀")){
    projectType = "전자";
    divCode = "ER";
    
  }else{
    projectType = "자동차";
    divCode = "CA";
  }

  //포장사양코드 리스트 추출 PACKTYPE
  String packArrStr="";
  parameter.clear();
  parameter.put("locale",   messageService.getLocale());
  parameter.put("codeType", "PACKTYPE");
  numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
  for ( int i=0; i < numCode.size(); i++ ) {
      packArrStr = packArrStr + "<option value='" + numCode.get(i).get("oid") + "'>" + numCode.get(i).get("value") + "</option>";
  }
  //포장사양코드 리스트 추출끝 PACKTYPE

  QuerySpec qsPack = new QuerySpec();

  String oid = request.getParameter("oid");

  String reqNo = "";
   String reqName = "";
   String reception = "";
   String receptionName = "";

   String creatorName = "";
   String deptName = "";
   String lifeCycleState = "";
  String divisionCode = "";
  String developmentStep = "";
  String projectOID = "";
  String projectNO = "";


  String requestBuyer = "";
  String requestBuyerManager = "";
  String lastUsingBuyer = "";
  String lastUsingBuyerManager = "";

  String repCarType = "";

  String isDRRequest = "";
  String drRequestDate = "";
  String isProposalSubmit = "";
  String proposalSubmitDate = "";
  String isCostSubmit = "";
  String costSubmitDate = "";

  String devProductName = "";
  String devReviewTypeCode = "";
  String devReviewTypeEtc = "";

  String devReviewDetailComment = "";

  String productSaleFirstYear = "";

  String initialSampleSummitDate = "";
  String eSIRDate = "";
  String iSIRDate = "";
  String ketMassProductionDate = "";

  String productCategoryCode = "";
  String productCategoryName = "";
  String productTypeCode = "";

  String etcSpecification = "";
  String tabSize = "";
  String materialSubMaterial = "";
  String surfaceTreatmentCode = "";
  String applyedWire = "";
  String primaryFunction = "";
  String outlook = "";

  String moldDepreciationTypeSale = "";
  String moldDepreciationTypeGeneral = "";
  String moldDepreciationTypePayment = "";
  String moldDepreciationTypePeriod = "";
  String moldDepreciationTypeEtcInfo = "";

  String equipDepreciationTypeSale = "";
  String equipDepreciationTypePayment = "";
  String equipDepreciationTypePeriod = "";
  String equipDepreciationTypeEtcInfo = "";

  String deviceSpecification = "";
  String environmentalRegulationItem = "";
  String buyerEtcRequirement = "";
  String salesAdditionalRequirement = "";

  String productCategoryCon = "";
  String moldDepreciationType = "";
  String equipDepreciationType = "";
  String attribute2 = "";
  String attribute3 = "";
  String attribute4 = "";
  String attribute5 = "";
  String attribute6 = "";
  String attribute7 = "";
  String attribute8 = "";
  String attribute9 = "";
  String attribute10 = "";
  String attribute11 = "";
  
  String InitialSampleSummitDate2 = "";
  String ESIRDate2 = "";
  String ISIRDate2 = "";
  String ProductSaleFirstYear2 = "";
  String KetMassProductionDate2 = "";
  String scheduleName = "";
   KETDevelopmentRequest dr = null;


  if(oid!=null){
    if(oid.equals("0000")){

    }else{
      //KETDevelopmentRequest oid로 해당 객체의 정보를 화면에 나타낸다.
      dr = (KETDevelopmentRequest)CommonUtil.getObject(oid);
      reqNo = dr.getNumber();

      //creatorName = dr.getCreatorName();
      creatorName = dr.getIterationInfo().getCreator().getFullName();
      deptName = dr.getDeptName();
      lifeCycleState = dr.getLifeCycleState().getDisplay();

      statusCode = StringUtil.checkNull(dr.getDevelopmentStep());

      if(statusCode.equals("D")){
        developmentStep = "개발의뢰";
      }else if(statusCode.equals("R")){
        developmentStep = "개발검토의뢰";
      }else{
        developmentStep = "-";
      }

      reception = StringUtil.checkNull(dr.getReception());
      if(!reception.equals("")){
        WTUser user1 = (WTUser)CommonUtil.getObject(reception);
        receptionName = user1.getFullName();
      }

      projectOID = StringUtil.checkNull(dr.getProjectOID());
      if(!projectOID.equals("")){
	    
	    String objs[] = projectOID.split(",");
	    
	    for(String target : objs){
		    Object obj = CommonUtil.getObject(target);

	        if ( obj instanceof E3PSProject ) {
	          E3PSProject project = (E3PSProject)obj;
	          projectNO += project.getPjtNo()+",";
	          
	        }else if ( obj instanceof KETDevelopmentRequest ) {
	          KETDevelopmentRequest dr1 = (KETDevelopmentRequest)obj;
	          projectNO = dr1.getNumber();

	        }else{
	          projectNO = "";
	          
	        }
	    }
	    projectNO = StringUtils.removeEnd(projectNO, ",");
        
      }

      divisionCode = StringUtil.checkNull(dr.getDivisionCode());
      if(divisionCode.equals("ER") && !CommonUtil.getUserDeptNameFromSession().startsWith("전장IT")){
        projectType = "전자";
        divCode = "ER";
      }else{
        projectType = "자동차";
        divCode = "CA";      
      }
      
      if("ER".equals(divCode)){
	      String checkObjs[] = projectOID.split(",");
	      for(String checkObj : checkObjs){
		      Object obj = CommonUtil.getObject(checkObj);
		      if(obj instanceof ReviewProject){
			      ReviewProject rProject = (ReviewProject)obj;
		          if(!"진행".equals( rProject.getReviewResult() ) ){
		              isReviewResult = false;
		          }
		      }
	      }
      }

      requestBuyer = StringUtil.checkNull(dr.getRequestBuyer());
      requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());
      lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer());
      lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());

      repCarType = StringUtil.checkNull(dr.getRepCarType());

      isDRRequest = StringUtil.checkNull(dr.getIsDRRequest());
      if(isDRRequest.equals("1")){
        drRequestDate = DateUtil.getTimeFormat(dr.getDrRequestDate(),"yyyy-MM-dd");
      }else{
        drRequestDate = "";
      }
      isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
      if(isProposalSubmit.equals("1")){
        proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(),"yyyy-MM-dd");
      }else{
        proposalSubmitDate = "";
      }
      isCostSubmit = StringUtil.checkNull(dr.getIsCostSubmit());
      if(isCostSubmit.equals("1")){
        costSubmitDate = DateUtil.getTimeFormat(dr.getCostSubmitDate(),"yyyy-MM-dd");
      }else{
        costSubmitDate = "";
      }
      devProductName = StringUtil.checkNull(dr.getDevProductName());
      devReviewTypeCode = StringUtil.checkNull(dr.getDevReviewTypeCode());

      devReviewTypeEtc = StringUtil.checkNull(dr.getDevReviewTypeEtc());


      devReviewDetailComment = StringUtil.checkNull(dr.getDevReviewDetailComment());

      productSaleFirstYear = StringUtil.checkNull(dr.getProductSaleFirstYear());

      initialSampleSummitDate = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(),"yyyy-MM-dd");
      eSIRDate = DateUtil.getTimeFormat(dr.getESIRDate(),"yyyy-MM-dd");
      iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(),"yyyy-MM-dd");
      ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(),"yyyy-MM-dd");

      productCategoryCode = StringUtil.checkNull(dr.getProductCategoryCode());
      if(StringUtil.checkString(productCategoryCode) && productCategoryCode.indexOf("KETPartClassification") > 0){
          String[] productCategorys = productCategoryCode.split(",");
          try{
              for(int i=0;i<productCategorys.length;i++){
                  KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(productCategorys[i]);
                  if(i != productCategorys.length - 1){
            	       productCategoryName += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName()+", ";
                  }else{
            	       productCategoryName += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();              
                  }
              }
          }catch(Exception e){ Kogger.error(e);}
      }
      productTypeCode = StringUtil.checkNull(dr.getProductTypeCode());

      etcSpecification = StringUtil.checkNull(dr.getEtcSpecification());
      tabSize = StringUtil.checkNull(dr.getTabSize());
      materialSubMaterial = StringUtil.checkNull(dr.getMaterialSubMaterial());
      surfaceTreatmentCode = StringUtil.checkNull(dr.getSurfaceTreatmentCode());
      applyedWire = StringUtil.checkNull(dr.getApplyedWire());
      primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction());
      //차종
      attribute2 = StringUtil.checkNull(dr.getAttribute2());
      //적용부위
      attribute3 = StringUtil.checkNull(dr.getAttribute3());
      //개발부품
      attribute4 = StringUtil.checkNull(dr.getAttribute4());
      //개발일정
      attribute5 = StringUtil.checkNull(dr.getAttribute5());
      //양산투자비
      attribute6 = StringUtil.checkNull(dr.getAttribute6());
      //납입처
      attribute7 = StringUtil.checkNull(dr.getAttribute7());
      //예상매출액
      attribute8 = StringUtil.checkNull(dr.getAttribute8());
      //기타
      attribute9 = StringUtil.checkNull(dr.getAttribute9());
      outlook = StringUtil.checkNull(dr.getOutlook());

      moldDepreciationTypeSale = StringUtil.checkNull(dr.getMoldDepreciationTypeSale());
      moldDepreciationTypeGeneral = StringUtil.checkNull(dr.getMoldDepreciationTypeGeneral());
      moldDepreciationTypePayment = StringUtil.checkNull(dr.getMoldDepreciationTypePayment());
      moldDepreciationTypePeriod = StringUtil.checkNull(dr.getMoldDepreciationTypePeriod());
      moldDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getMoldDepreciationTypeEtcInfo());

      if(!moldDepreciationTypeSale.equals("")) moldDepreciationType = moldDepreciationType + "/" + moldDepreciationTypeSale;
      if(!moldDepreciationTypeGeneral.equals("")) moldDepreciationType = moldDepreciationType + "/" + moldDepreciationTypeGeneral;
      if(!moldDepreciationTypePayment.equals("")) moldDepreciationType = moldDepreciationType + "/" + moldDepreciationTypePayment;
      if(!moldDepreciationTypePeriod.equals("")) moldDepreciationType = moldDepreciationType + "/" + moldDepreciationTypePeriod;
      if(!moldDepreciationTypeEtcInfo.equals("")) moldDepreciationType = moldDepreciationType + "/" + moldDepreciationTypeEtcInfo;
      if(moldDepreciationType.equals("")||moldDepreciationType.equals("/")) moldDepreciationType="";
      else moldDepreciationType = moldDepreciationType.substring(1);

      equipDepreciationTypeSale = StringUtil.checkNull(dr.getEquipDepreciationTypeSale());
      equipDepreciationTypePayment = StringUtil.checkNull(dr.getEquipDepreciationTypePayment());
      equipDepreciationTypePeriod = StringUtil.checkNull(dr.getEquipDepreciationTypePeriod());
      equipDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getEquipDepreciationTypeEtcInfo());

      if(!equipDepreciationTypeSale.equals("")) equipDepreciationType = equipDepreciationType + "/" + equipDepreciationTypeSale;
      if(!equipDepreciationTypePayment.equals("")) equipDepreciationType = equipDepreciationType + "/" + equipDepreciationTypePayment;
      if(!equipDepreciationTypePeriod.equals("")) equipDepreciationType = equipDepreciationType + "/" + equipDepreciationTypePeriod;
      if(!equipDepreciationTypeEtcInfo.equals("")) equipDepreciationType = equipDepreciationType + "/" + equipDepreciationTypeEtcInfo;
      if(equipDepreciationType.equals("")||equipDepreciationType.equals("/")) equipDepreciationType="";
      else equipDepreciationType = equipDepreciationType.substring(1);

      deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
      environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem());
      buyerEtcRequirement = StringUtil.checkNull(dr.getBuyerEtcRequirement());
      salesAdditionalRequirement = StringUtil.checkNull(dr.getSalesAdditionalRequirement());
      
      attribute10 = StringUtil.checkNull(dr.getAttribute10());
      
      attribute11 = StringUtil.checkNull(dr.getAttribute11());
      
      InitialSampleSummitDate2 = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate2() ,"yyyy-MM-dd");
      ESIRDate2 = DateUtil.getTimeFormat(dr.getESIRDate2() ,"yyyy-MM-dd");
      ISIRDate2 = DateUtil.getTimeFormat(dr.getISIRDate2() ,"yyyy-MM-dd");
      KetMassProductionDate2 = DateUtil.getTimeFormat(dr.getKetMassProductionDate2() ,"yyyy-MM-dd");
      ProductSaleFirstYear2 = DateUtil.getTimeFormat(dr.getProductSaleFirstYear2() ,"yyyy-MM-dd");
      scheduleName = StringUtil.checkNull(dr.getScheduleName());

      KETRequestPartList pl = null;
      QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
      if (r.hasMoreElements()){
        pl = (KETRequestPartList) r.nextElement();
        String partName=pl.getPartName();
      }
     }
  }

  String amsg; // alert 메시지용 변수
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "03389", developmentStep) %><%--{0}서 수정--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=javascript src="../../portal/js/Calendar.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
}
-->
</style>
<script language="JavaScript">
<!--

  function isNull(str) {
    if(str==null||str==""){
      return true;
    }
    return false;
  }

  function isNotDigit(str) {
    var pattern = /^[0-9]+$/;

    str = str.replace(".", "0");
    if(!pattern.test(str)){
      return true;
    }
    return false;
  }

  function sapSubcontractorCallBack(returnValue){
      var subArr;
      var returnValue;
        
      if(typeof returnValue == "undefined" || returnValue == null) {
          return;
      }
      subArr = returnValue[0];

      var cLine = document.form01.currPartLine.value;
      var selLine = cLine - 2;

      $('[name=summitDestinationOid]').eq(selLine).val(subArr[0]);
      $('[name=summitDestination]').eq(selLine).val(subArr[2]);
  
  }
  

  //납입처선택
  function insertCustom() {
      
    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SAPSUBCONTRACTOR&command=select&mode="+mode+"&invokeMethod=sapSubcontractorCallBack";
    window.open(url, "SAPSUBCONTRACTOR", "top=100px, left=100px, height=500px, width=850px");

    var fm = document.form01;
    
        
    /* if(selLine==0){
      if (fm.summitDestination[0]=="[object]"){
        fm.summitDestinationOid[0].value = subArr[0];
        fm.summitDestination[0].value = subArr[2];
      }else{
        fm.summitDestinationOid.value = subArr[0];
        fm.summitDestination.value = subArr[2];
      }
    }else{
      fm.summitDestinationOid[selLine].value = subArr[0];
      fm.summitDestination[selLine].value = subArr[2];
    } */
  }

  function deleteCustom() {

    var fm = document.form01
    var cLine = document.form01.currPartLine.value;
    var selLine = cLine - 2;

    if(selLine==0){
      if (fm.summitDestination[0]=="[object]"){
        fm.summitDestination[0].value = "";
        fm.summitDestinationOid[0].value = "";
      }else{
        fm.summitDestination.value = "";
        fm.summitDestinationOid.value = "";
      }
    }else{
      fm.summitDestination[selLine].value = "";
      fm.summitDestinationOid[selLine].value = "";
    }
  }

  function openCarTypeWindow() {
    var fm = document.form01
    var cLine = document.form01.currPartLine.value;
    var selLine = cLine - 2;
    var body = document.getElementById("partTable");
    var t_checks = fm.iPartChk;
    var cTypes;
    var cTid;
    var url;

    if(selLine==0){
      if (typeof fm.carTypes[0] === 'object'){
        cTypes = fm.carTypes[0].value;
        cTid = fm.tId[0].value;
      }else{
        cTypes = fm.carTypes.value;
        cTid = fm.tId.value;
      }


    }else{
      cTypes = fm.carTypes[selLine].value;
      cTid = fm.tId[selLine].value;
    }

    if(cTypes!=""){

      var tBody = document.getElementById("iCarTable");
      var tLength = tBody.rows.length;
      var arr1 = new Array();
      var tmpIdx = 0;

      for(var i = 0; i < tLength; i++) {
        if (typeof fm.tId1[i] === 'object'){
          if(fm.tId1[i].value == cTid){
             if(typeof fm.y1[i] === 'object'){
              var subarr = new Array();
              subarr[0] = cTid;
              subarr[1] = fm.y1[i].value;
              subarr[2] = fm.y2[i].value;
              subarr[3] = fm.y3[i].value;
              subarr[4] = fm.y4[i].value;
              subarr[5] = fm.y5[i].value;
              subarr[6] = fm.y6[i].value;
              subarr[7] = fm.sum[i].value;
              subarr[8] = fm.usage[i].value;
              subarr[9] = fm.optRate[i].value;
              subarr[10] = fm.carTypeOid[i].value;
              subarr[11] = fm.carTypeCode[i].value;
              arr1[tmpIdx++]=subarr;
            }else{
              var subarr = new Array();
              subarr[0] = cTid;
              subarr[1] = fm.y1.value;
              subarr[2] = fm.y2.value;
              subarr[3] = fm.y3.value;
              subarr[4] = fm.y4.value;
              subarr[5] = fm.y5.value;
              subarr[6] = fm.y6.value;
              subarr[7] = fm.sum.value;
              subarr[8] = fm.usage.value;
              subarr[9] = fm.optRate.value;
              subarr[10] = fm.carTypeOid.value;
              subarr[11] = fm.carTypeCode.value;
              arr1[tmpIdx++]=subarr;
            }
          }
        }else{
          if(fm.tId1.value == cTid){
            var subarr = new Array();
            subarr[0] = cTid;
            subarr[1] = fm.y1.value;
            subarr[2] = fm.y2.value;
            subarr[3] = fm.y3.value;
            subarr[4] = fm.y4.value;
            subarr[5] = fm.y5.value;
            subarr[6] = fm.y6.value;
            subarr[7] = fm.sum.value;
            subarr[8] = fm.usage.value;
            subarr[9] = fm.optRate.value;
            subarr[10] = fm.carTypeOid.value;
            subarr[11] = fm.carTypeCode.value;
            arr1[tmpIdx++]=subarr;
          }
        }
      }

      url="../../jsp/dms/AddDevRequestPart.jsp?cLine="+cLine+"&arr="+arr1+"&divCode=<%=divCode%>";
      
    }else{

      url="../../jsp/dms/AddDevRequestPart.jsp?cLine="+cLine+"&arr=0"+"&divCode=<%=divCode%>";
      
    }
    
    window.open(url, "AddDevRequestPart", "top=100px, left=100px, height=500px, width=730px");

    //returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");

  }

  function fncOnMouseOver(rowIndex) {
    document.form01.currPartLine.value = rowIndex;
  }

  function getCarType(arr, arr1, cLine){

    var fm = document.form01
    var body = document.getElementById("partTable");
    var tBody = document.getElementById("iCarTable");

    if (body.rows.length == 2) return;
    var t_checks = fm.carTypes;

    var tLength = tBody.rows.length;

    if (body.rows.length == 3) {
      if (t_checks[0]=="[object]"){
        fm.carTypes[0].value = arr[0];
        fm.yearAmount1[0].value = arr[1];
        fm.yearAmount2[0].value = arr[2];
        fm.yearAmount3[0].value = arr[3];
        fm.yearAmount4[0].value = arr[4];
        fm.yearAmount5[0].value = arr[5];
        fm.yearAmount6[0].value = arr[6];
        fm.sumAmount[0].value = arr[7];
        fm.applyedUsage[0].value = arr[8];
        fm.tId[0].value = arr[9];

        if(tLength > 0){
          for(var i = 0; i < tLength; i++) {
            tBody.deleteRow(0);
          }
        }

      }else{
        fm.carTypes.value = arr[0];
        fm.yearAmount1.value = arr[1];
        fm.yearAmount2.value = arr[2];
        fm.yearAmount3.value = arr[3];
        fm.yearAmount4.value = arr[4];
        fm.yearAmount5.value = arr[5];
        fm.yearAmount6.value = arr[6];
        fm.sumAmount.value = arr[7];
        fm.applyedUsage.value = arr[8];
        fm.tId.value = arr[9];

        if(tLength > 0){
          for(var i = 0; i < tLength; i++) {
            tBody.deleteRow(0);
          }
        }
      }
    }else{
      fm.carTypes[cLine-2].value = arr[0];
      fm.yearAmount1[cLine-2].value = arr[1];
      fm.yearAmount2[cLine-2].value = arr[2];
      fm.yearAmount3[cLine-2].value = arr[3];
      fm.yearAmount4[cLine-2].value = arr[4];
      fm.yearAmount5[cLine-2].value = arr[5];
      fm.yearAmount6[cLine-2].value = arr[6];
      fm.sumAmount[cLine-2].value = arr[7];
      fm.applyedUsage[cLine-2].value = arr[8];

      if(fm.tId[cLine-2].value=="0"){
        fm.tId[cLine-2].value = arr[9];
      }else{
        for(var j = tLength-1; j >= 0; j--) {
          if(fm.tId[cLine-2].value == fm.tId1[j].value){
            tBody.deleteRow(j);
          }
        }
        fm.tId[cLine-2].value = arr[9];
      }
    }

    for(var i = 0; i < arr1.length; i++) {

      var tBody = document.getElementById("iCarTable");
      var innerRow = tBody.insertRow();

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='tId1' class='txt_fieldRO'   value='" + arr1[i][0] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y1' class='txt_fieldRO'   value='" + removecomma(arr1[i][1]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y2' class='txt_fieldRO'  value='" + removecomma(arr1[i][2]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y3' class='txt_fieldRO'  value='" + removecomma(arr1[i][3]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y4' class='txt_fieldRO'  value='" + removecomma(arr1[i][4]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y5' class='txt_fieldRO'  value='" + removecomma(arr1[i][5]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y6' class='txt_fieldRO'  value='" + removecomma(arr1[i][6]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='sum' class='txt_fieldRO'  value='" + removecomma(arr1[i][7]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='usage' class='txt_fieldRO'  value='" + arr1[i][8] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='optRate' class='txt_fieldRO'  value='" + arr1[i][9] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeOid' class='txt_fieldRO'  value='" + arr1[i][10] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeCode' class='txt_fieldRO'  value='" + arr1[i][11] + "'>";

    }
  }

  function removecomma(s){
    s = s.replace(/\,/, "");
    return s;
  }

  function deletePartLine() {
    var fm = document.form01

    var body = document.getElementById("partTable");
    var tBody = document.getElementById("iCarTable");

    if (body.rows.length == 2) return;
    var t_checks = fm.iPartChk;
    var tId1 = fm.tId1;
    var tId = fm.tId;
    var alength = body.rows.length;
    var tLength = tBody.rows.length;

    if (body.rows.length == 3) {
      if (t_checks[0]=="[object]"){
        if (t_checks[0].checked){
          for(var i = 0; i < tLength; i++) {
            tBody.deleteRow(0);
          }
          body.deleteRow(2);
        }
      }else{
        if (t_checks.checked){
          for(var i = 0; i < tLength; i++) {
            tBody.deleteRow(0);
          }

          body.deleteRow(2);
        }
      }
    } else {
      for (var i = alength-1; i > 1; i--) {
        if (t_checks[i-2].checked){
          for(var j = tLength-1; j >= 0; j--) {

            if (tId1[j]=="[object]"){
              if(tId[i-2].value == tId1[j].value){
                tBody.deleteRow(j);
              }
            }else{
              if(tId[i-2].value == tId1.value){
                tBody.deleteRow(0);
              }
            }
          }
          body.deleteRow(i);
        }
      }
    }
  }

  function cancelDevRequest(oid){
    document.location.href="/plm/jsp/dms/ViewDevRequest.jsp?oid="+oid+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&DevelopmentStep=<%=SDevelopmentStep%>&DevProductName=<%=SDevProductName%>&RequestBuyer=<%=SRequestBuyer%>&LastUsingBuyer=<%=SLastUsingBuyer%>&userName=<%=SuserName%>&RepCarType=<%=SRepCarType%>&authorStatus=<%=SauthorStatus%>&DevReviewTypeCode=<%=SDevReviewTypeCode%>&ProductCategoryCode=<%=SProductCategoryCode%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
  }

  function insertPartLine() {
    var tBody = document.getElementById("partTable");
    var innerRow = tBody.insertRow();
    innerRow.onmouseover=function(){fncOnMouseOver(this.rowIndex)};

    var tLength = tBody.rows.length;

    var innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 40;
    innerCell.innerHTML = "<input type='checkbox' name='iPartChk' id='iPartChk' ><input type='hidden' name='tId' id='tId' value='0'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 150;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:140' name='partName'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 120;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRO' style='width:90' name='carTypes'>&nbsp;<a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 80;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:75' name='appliedRegion'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:55' name='currentApplyPart'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='applyedUsage'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount1'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount2'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount3'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount4'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount5'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount6'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:55' name='sumAmount'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 70;
    innerCell.innerHTML = "<select name='PackTypeCode' class='fm_jmp' style='width:60'><option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>" + "<%=packArrStr%>" + "</select>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='hidden' name='summitDestinationOid'><input type='text' readonly class='txt_fieldRO' style='width:62' name='summitDestination'>&nbsp;<img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'><a href='javascript:deleteCustom()'><img src='../../portal/images/icon_delete.gif' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='buyerAcceptPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='ketTargetPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 40;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40' name='targetEarningRate'>";
    
    
    <%if("D".equals(statusCode)){%>
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
   // innerCell.width = 40;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40' name='targetAverageRate'>";
    <%}%>
    
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40' name='targetTermRate'>";
    
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='targetInvestmentCost'>";
    
    numberFormat();

  }
  
  function numberFormat(){
	  
	  $("input[name='buyerAcceptPrice']").number(true, 1);
	  $("input[name='ketTargetPrice']").number(true, 1);
	  $("input[name='targetEarningRate']").number(true, 1);
	  $("input[name='targetAverageRate']").number(true, 1);
	  $("input[name='targetInvestmentCost']").number(true);
	  $("input[name='targetTermRate']").number(true,1);
  }

  function copyPartLine() {
    var fm = document.form01
    var body = document.getElementById("partTable");
    var pLength = body.rows.length;
    var t_checks = fm.iPartChk;
    var selLine = -1;

    if (pLength == 2){
          alert('<%=messageService.getString("e3ps.message.ket_message", "02607") %><%--제품을 추가하시고 선택하셔야 합니다.--%>');
      return;
    }
    if (pLength == 3) {
      if (t_checks[0]=="[object]"){
        if (t_checks[0].checked){
          selLine = 0;
        }
      }else{
        if (t_checks.checked){
          selLine = 0;
        }
      }
    } else {
      for (var i = pLength-1; i > 1; i--) {
        if (t_checks[i-2].checked){
          if (selLine != -1){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01534") %><%--복사할 제품을 하나만 선택하셔야 합니다--%>');
            return;
          }
          selLine = i-2;
        }
      }
    }

    if (selLine == -1){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02606") %><%--제품을 선택하셔야 합니다--%>');
      return;
    }

    if (fm.partName[selLine]=="[object]"){
      if( isNull(fm.partName[selLine].value)|| isNull(fm.carTypes[selLine].value)) {
<%
  if(divCode.equals("CA")) {
    amsg = messageService.getString("e3ps.message.ket_message", "03390")/*제품명과 차종이 정해진 제품을 선택하셔야 합니다.*/;
  }
  else {
    amsg = messageService.getString("e3ps.message.ket_message", "03391")/*제품명과 적용기기가 정해진 제품을 선택하셔야 합니다.*/;
  }
%>
        alert("<%=amsg %>");
        return;
      }
    }else{
      if( isNull(fm.partName.value)|| isNull(fm.carTypes.value)) {
<%
  if(divCode.equals("CA")) {
    amsg = messageService.getString("e3ps.message.ket_message", "03390")/*제품명과 차종이 정해진 제품을 선택하셔야 합니다.*/;
  }
  else {
    amsg = messageService.getString("e3ps.message.ket_message", "03391")/*제품명과 적용기기가 정해진 제품을 선택하셔야 합니다.*/;
  }
%>
        alert("<%=amsg %>");
        return;
      }
    }

    var innerRow = body.insertRow();
    innerRow.onmouseover=function(){fncOnMouseOver(this.rowIndex)};
    var innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 40;
    innerCell.innerHTML = "<input type='checkbox' name='iPartChk' id='iPartChk' ><input type='hidden' name='tId' id='tId' value='0'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 150;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:140' name='partName'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 120;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRO' style='width:90' name='carTypes'>&nbsp;<a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 80;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:75' name='appliedRegion'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:55' name='currentApplyPart'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='applyedUsage'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount1'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount2'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount3'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount4'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount5'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount6'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdblueM";
    innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:55' name='sumAmount'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 70;
    innerCell.innerHTML = "<select name='PackTypeCode' class='fm_jmp' style='width:60'><option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>" + "<%=packArrStr%>" + "</select>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='hidden' name='summitDestinationOid'><input type='text' readonly class='txt_fieldRO' style='width:62' name='summitDestination'>&nbsp;<img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'><a href='javascript:deleteCustom()'><img src='../../portal/images/icon_delete.gif' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='buyerAcceptPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='ketTargetPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 70;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:60' name='targetEarningRate'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95' name='targetInvestmentCost'>";

    var destLine = pLength-2;

    fm.appliedRegion[destLine].value      = fm.appliedRegion[selLine].value;
    fm.currentApplyPart[destLine].value      = fm.currentApplyPart[selLine].value;
    fm.carTypes[destLine].value      = fm.carTypes[selLine].value;
    fm.yearAmount1[destLine].value   = fm.yearAmount1[selLine].value;
    fm.yearAmount2[destLine].value   = fm.yearAmount2[selLine].value;
    fm.yearAmount3[destLine].value   = fm.yearAmount3[selLine].value;
    fm.yearAmount4[destLine].value   = fm.yearAmount4[selLine].value;
    fm.yearAmount5[destLine].value   = fm.yearAmount5[selLine].value;
    fm.yearAmount6[destLine].value   = fm.yearAmount6[selLine].value;
    fm.sumAmount[destLine].value     = fm.sumAmount[selLine].value;
    fm.applyedUsage[destLine].value  = fm.applyedUsage[selLine].value;
    fm.tId[destLine].value           = (Math.floor(Math.random()*100000))+1;

    fm.PackTypeCode[destLine].value = fm.PackTypeCode[selLine].value;
    fm.summitDestination[destLine].value = fm.summitDestination[selLine].value;
    fm.summitDestinationOid[destLine].value = fm.summitDestinationOid[selLine].value;
    fm.buyerAcceptPrice[destLine].value = fm.buyerAcceptPrice[selLine].value;
    fm.ketTargetPrice[destLine].value = fm.ketTargetPrice[selLine].value;
    fm.targetEarningRate[destLine].value = fm.targetEarningRate[selLine].value;
    fm.targetInvestmentCost[destLine].value = fm.targetInvestmentCost[selLine].value;

    numberFormat();
    
    var tBody = document.getElementById("iCarTable");
    var tLength = tBody.rows.length;
    var arr1 = new Array();
    var tmpIdx = 0;

    for(var i = 0; i < tLength; i++) {
      if (fm.tId1[i]=="[object]"){
        if(fm.tId1[i].value == fm.tId[selLine].value){
          if(fm.y1[i]=="[object]"){
            var subarr = new Array();
            subarr[0] = fm.tId[destLine].value;
            subarr[1] = fm.y1[i].value;
            subarr[2] = fm.y2[i].value;
            subarr[3] = fm.y3[i].value;
            subarr[4] = fm.y4[i].value;
            subarr[5] = fm.y5[i].value;
            subarr[6] = fm.y6[i].value;
            subarr[7] = fm.sum[i].value;
            subarr[8] = fm.usage[i].value;
            subarr[9] = fm.optRate[i].value;
            subarr[10] = fm.carTypeOid[i].value;
            subarr[11] = fm.carTypeCode[i].value;
            arr1[tmpIdx++]=subarr;
          }else{
            var subarr = new Array();
            subarr[0] = fm.tId[destLine].value;
            subarr[1] = fm.y1.value;
            subarr[2] = fm.y2.value;
            subarr[3] = fm.y3.value;
            subarr[4] = fm.y4.value;
            subarr[5] = fm.y5.value;
            subarr[6] = fm.y6.value;
            subarr[7] = fm.sum.value;
            subarr[8] = fm.usage.value;
            subarr[9] = fm.optRate.value;
            subarr[10] = fm.carTypeOid.value;
            subarr[11] = fm.carTypeCode.value;
            arr1[tmpIdx++]=subarr;
          }
        }
      }else{
        if(fm.tId1.value == fm.tId[selLine].value){
          var subarr = new Array();
          subarr[0] = fm.tId[destLine].value;
          subarr[1] = fm.y1.value;
          subarr[2] = fm.y2.value;
          subarr[3] = fm.y3.value;
          subarr[4] = fm.y4.value;
          subarr[5] = fm.y5.value;
          subarr[6] = fm.y6.value;
          subarr[7] = fm.sum.value;
          subarr[8] = fm.usage.value;
          subarr[9] = fm.optRate.value;
          subarr[10] = fm.carTypeOid.value;
          subarr[11] = fm.carTypeCode.value;
          arr1[tmpIdx++]=subarr;
        }
      }
    }

    for(var i = 0; i < arr1.length; i++) {

      var innerRow = tBody.insertRow();

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='tId1' class='txt_fieldRO'   value='" + arr1[i][0] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y1' class='txt_fieldRO'   value='" + arr1[i][1] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y2' class='txt_fieldRO'  value='" + arr1[i][2] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y3' class='txt_fieldRO'  value='" + arr1[i][3] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y4' class='txt_fieldRO'  value='" + arr1[i][4] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y5' class='txt_fieldRO'  value='" + arr1[i][5] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='y6' class='txt_fieldRO'  value='" + arr1[i][6] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='sum' class='txt_fieldRO'  value='" + arr1[i][7] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='usage' class='txt_fieldRO'  value='" + arr1[i][8] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='optRate' class='txt_fieldRO'  value='" + arr1[i][9] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeOid' class='txt_fieldRO'  value='" + arr1[i][10] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeCode' class='txt_fieldRO'  value='" + arr1[i][11] + "'>";

    }
  }

  function insertRequestBuyer(){
      window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=multi&viewType=&disable=&invokeMethod=selectMultiSubContractor", "MultiSubContractor", "top=100px, left=100px, height=800px, width=1200px");
  }
  //의뢰처 검색화면 오픈하여 선택된 의뢰처를 입력한다.
  function selectMultiSubContractor(arrObj) {

    var fm = document.form01
    var pos = fm.RequestBuyer.length;
    var subArr;
    
    if(typeof arrObj == "undefined" || arrObj == null) {
      return;
    }

    for(var i = 0; i < arrObj.length; i++) {
      subArr = arrObj[i];

      for(var j = 0; j < pos; j++) {
        if(fm.RequestBuyer.options[j].value==subArr[0]){
          alert(subArr[2]+"<%=messageService.getString("e3ps.message.ket_message", "00023") %><%--{0}는 이미 존재하는 의뢰처입니다--%>");
          return;
        }
      }

      fm.RequestBuyer.length = pos+i+1;
      fm.RequestBuyer.options[pos+i].value= subArr[0];
      fm.RequestBuyer.options[pos+i].text = subArr[2];
    }
  }

  function deleteRequestBuyer() {

    var fm = document.form01;
    if(fm.RequestBuyer.selectedIndex < 0){
    	
    	$('[name=RequestBuyer] option[value!="0"]').remove();
    	
    }else{
    	while(fm.RequestBuyer.selectedIndex >= 0){
    		if((fm.RequestBuyer.length>0)&&(fm.RequestBuyer.selectedIndex>=0)){
    			var pos = fm.RequestBuyer.selectedIndex;
    	        fm.RequestBuyer.remove(pos);
    	    }
    	}
    }
    
  }
  
  function insertLastUsingBuyer() {
	  window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=multi&designTeam=&invokeMethod=selectMultiCustomer", "CUSTOMEREVENT", "top=100px, left=100px, height=800px, width=1200px");
  }

  function selectMultiCustomer(arrObj){
      
      var fm = document.form01;
      var pos = fm.LastUsingBuyer.length;
      var subArr;
      
      if(typeof arrObj == "undefined" || arrObj == null) {
          return;
      }

      for(var i = 0; i < arrObj.length; i++) {
          subArr = arrObj[i];
          for(var j = 0; j < pos; j++) {
              if(fm.LastUsingBuyer.options[j].value==subArr[0]){
              alert(subArr[2]+"<%=messageService.getString("e3ps.message.ket_message", "00024") %><%--{0}는 이미 존재하는 최종 사용처입니다--%>");
              return;
            }
          }

          fm.LastUsingBuyer.length = pos+i+1;
          fm.LastUsingBuyer.options[pos+i].value = subArr[0];
          fm.LastUsingBuyer.options[pos+i].text = subArr[2];
      }
  }

  function deleteLastUsingBuyer() {
	  var fm = document.form01;
	  
	  if(fm.LastUsingBuyer.selectedIndex < 0){
		  $('[name=LastUsingBuyer] option[value!="0"]').remove();
      }else{
    	  
    	  while(fm.LastUsingBuyer.selectedIndex>=0){
    		  if((fm.LastUsingBuyer.length>0)&&(fm.LastUsingBuyer.selectedIndex>=0)){
    			  var pos = fm.LastUsingBuyer.selectedIndex;
    			  fm.LastUsingBuyer.remove(pos);
    		  }
    	  }
      }
   }

  function show_Cal2(objDate){
    if(document.form01.IsDRRequest[0].checked) showCal(objDate);
  }

  function show_Cal3(objDate){
    if(document.form01.IsProposalSubmit[0].checked) showCal(objDate);
  }

  function show_Cal4(objDate){
    if(document.form01.IsCostSubmit[0].checked) showCal(objDate);
  }

  function KetMassProductionDateMnth(){
    var d = document.forms[0];
    var sDate=d.KetMassProductionDate.value;
    d.KetMassProductionDate.value = sDate.substring(0,7);
  }

  function ProductSaleFirstYearMnth(){
    var d = document.forms[0];
    var sDate=d.ProductSaleFirstYear.value;
    d.ProductSaleFirstYear.value = sDate.substring(0,7);
  }

  function deleteValue(param){
    document.getElementById(param).value = "";
  }

  function deleteValue2(param1,param2){
    document.getElementById(param1).value = "";
    document.getElementById(param2).value = "";
  }

  //Number Code Ajax
  function numCodeAjax(codeType, oid) {
      $.ajax( {
          url : "/plm/servlet/e3ps/NumberCodeAjax",
          type : "POST",
          data : {codeType:codeType, oid:oid},
          dataType : 'json',
          async : false,
          success: function(data) {

              var i = 0;
              $('#iProductTypeTable tr').remove();
              var tBody = document.getElementById("iProductTypeTable");
              var innerRow = tBody.insertRow();
              var innerCell;
              $.each(data.numCode, function() {
                  if ( i%5 == 0 ) {
                      innerRow = tBody.insertRow();
                  }
                  innerCell = innerRow.insertCell();
                  innerCell.width = "130";
                  innerCell.innerHTML = "<input type='checkbox' name='ProductTypeCode' id='"+this.oid+"'>"+ this.value;
                  i++;
              });
          }
      });
  }
  //제품군 변경시 제품타입을 검색한다.
  function changeProductCategory(){
      var fm = document.form01;
      //var code = fm.ProductCategoryCode.options[fm.ProductCategoryCode.selectedIndex].value;

      //numCodeAjax("PRODCATEGORYCODE", code.replace("e3ps.common.code.NumberCode:", ""));
  }

  function insertFileLine() {

	  var innerRow = iFileTableOld.insertRow();
	    innerRow.height = "27";
	    var tBody = document.getElementById("iFileTableOld");
	    //var innerRow = tBody.insertRow();
	    //var innerCell = innerRow.insertCell();
	    var filePath = "filePath"+tBody.rows.length;

	    var filehtml = "";

	    for ( var k = 0; k < 2; k++) {
	        innerCell = innerRow.insertCell();
	        innerCell.height = "23";

	        if (k == 0) {
	            innerCell.className = "tdwhiteM";
	            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
	        } else if (k == 1) {
	            innerCell.className = "tdwhiteL0";
	            innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_fieldRO' style='width: 100%;'>";
	        }
	    }
  }
  
  function deleteFile(contentoid){
	  document.form01.isFileDel.value += "/" + contentoid;
  }

  function deleteFileLine() {//def

    var body = document.getElementById("iFileTable");

    var file_checks = document.forms[0].iFileChk;
    if (body.rows.length == 0){

    }else if (body.rows.length == 1) {
      if (file_checks[0]=="[object]"){
        if (file_checks[0].checked){
          body.deleteRow(0);
        }
      }else{
        if (file_checks.checked){
          body.deleteRow(0);
        }
      }
    } else {
      for (var i = body.rows.length; i > 0; i--) {
        if (file_checks[i-1].checked) body.deleteRow(i - 1);
      }
    }

    body = document.getElementById("iFileTableOld");
    var ContentOid1 = document.getElementById("ContentOid");

    file_checks = document.forms[0].iFileChkOld;
    if (body.rows.length == 0){

    }else if (body.rows.length == 1) {
      if (file_checks[0]=="[object]"){
        if (file_checks[0].checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[0].value;
          body.deleteRow(0);
        }
      }else{
        if (file_checks.checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid.value;
          body.deleteRow(0);
        }
      }
    } else {
      for (var i = body.rows.length; i > 0; i--) {
        if (file_checks[i-1].checked){

          document.form01.isFileDel.value = document.form01.isFileDel.value + "/" + document.form01.ContentOid[i-1].value;
          body.deleteRow(i-1);
        }
      }
    }
  }

  function checkDevRequest(drOid){
      var msg = '';
      
      $.ajax({
          type: 'post',
          url : '/plm/ext/cost/checkDevRequest.do?drOid='+drOid,
          async : false,
          cache:false,
          success : function(result){
              if(result.msg != ''){
                  msg = result.msg;
              }
          }
      });
      
      return msg;
      
  }
  
  
  //필수입력사항 체크
  function valcheck() {
    var d = document.form01;
    
    
    if(d.DevelopmentStep.value=="D"){
    	
    	if(d.ProjectNo.value == ''){
    		alert("검토 Project No를 입력하십시오.");
    		return false;
    	}
    	
    	var oldPjtnoArr = d.oldProjectNo.value.split(",").sort();
        var projectNoArr = d.ProjectNo.value.split(",").sort();
        
        var checkReqSeq = 0;
        
        if(d.oldProjectNo.value != ''){
            
        	for(var i=0; i<oldPjtnoArr.length; i++){
        		for(var j=0; j<projectNoArr.length; j++){
        			if(oldPjtnoArr[i] == projectNoArr[j]){
        				checkReqSeq++;
        			}
                }
        	}

        	if(oldPjtnoArr.length != checkReqSeq){
        		var msg = checkDevRequest("<%=oid%>");
                if(msg != ''){
                    alert(msg);
                    return false;
                }	
        	}
            
        }
    }
    
    
    if( d.RequestBuyer.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02265") %><%--의뢰처는 반드시 입력해야 합니다.--%>');
      return false;
    }
    if (d.RequestBuyerManager.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02263") %><%--의뢰처 담당자는 200자를 초과할 수 없습니다--%>');
      return false;
    }

    //if(d.DivisionCode.value == "ER"){
    //  if( isNull(d.reception.value)) {
    //     alert('<%=messageService.getString("e3ps.message.ket_message", "02260") %><%--의뢰접수자는 반드시 입력해야 합니다--%>'); 
          //return false;
       // }
    //}
        
    if( d.LastUsingBuyer.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02850") %><%--최종사용처는 반드시 입력해야 합니다.--%>');
      return false;
    }

    if (d.LastUsingBuyerManager.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02849") %><%--최종사용처 담당자는 200자를 초과할 수 없습니다--%>');
      return false;
    }

    if( isNull(d.RepCarType.value) ) {
<%
  if(divCode.equals("CA")) {
    amsg = messageService.getString("e3ps.message.ket_message", "01249")/*대표차종은 반드시 입력해야 합니다.*/;
  }
  else {
    amsg = messageService.getString("e3ps.message.ket_message", "03392")/*적용기기는 반드시 입력해야 합니다.*/;
  }
%>
      alert('<%=amsg %>');
      return false;
    }
    if(d.DevelopmentStep.value=="R"){
      <%-- if ( (d.IsDRRequest[0].checked)&&(isNull(d.DrRequestDate.value))) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "00160") %>DR0요구일정이 필요할 경우 DR0요구일정은 반드시 입력해야 합니다.');
        return false;
      } --%>


      	if ( (d.IsProposalSubmit[0].checked)&&(isNull(d.ProposalSubmitDate.value))) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02529") %><%--제안서 제출일정이 필요할 경우 제안서 제출일정은 반드시 입력해야 합니다.--%>');
        	return false;
      	}

        if ( (d.IsCostSubmit[0].checked)&&(isNull(d.CostSubmitDate.value))) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00651") %><%--개발원가 제출일정이 필요할 경우 개발원가 제출일정은 반드시 입력해야 합니다--%>');
            return false;
        }
        if(d.DivisionCode.value == "ER"){
        	
        	if( isNull(d.InitialSampleSummitDate.value) ) {
                alert('개발시작회의는 반드시 입력하셔야 합니다.');
                return false;
            }
            if( isNull(d.ESIRDate.value) ) {
                alert('초도품 제출은 반드시 입력하셔야 합니다.');
                return false;
            }
            
            if( isNull(d.ISIRDate.value) ) {
                alert('승인샘플/승인원 제출은 반드시 입력하셔야 합니다.');
                return false;
            }
            
            if( isNull(d.KetMassProductionDate.value) ) {
                alert('최종고객승인은 반드시 입력하셔야 합니다.');
                return false;
            }
            
         	if( isNull(d.ProductSaleFirstYear.value) ) {
        		alert('고객 SOP는 반드시 입력하셔야 합니다.');
        		return false;
        	}    	
        }      
    }else{
    	if(d.DivisionCode.value == "ER"){
    		
          	if( isNull(d.InitialSampleSummitDate.value) ) {
          		alert('DR0(목표)일은 반드시 입력하셔야 합니다.');
          		return false;
          	}
          	
          	if( isNull(d.ESIRDate.value) ) {
          		alert('초도품 제출 일정은 반드시 입력하셔야 합니다.');
          		return false;
          	}
          	if( isNull(d.ProductSaleFirstYear.value) ) {
          		alert('고객 SOP는 반드시 입력하셔야 합니다.');
          		return false;
          	}    	
          }
      }
    if ( isNull(d.DevProductName.value) ) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "00593") %><%--개발 제품명은 반드시 입력해야 합니다--%>');
      return false;
    }else{
      var DevProductName = d.DevProductName.value;
      if (DevProductName.length > 200){
        alert('<%=messageService.getString("e3ps.message.ket_message", "00592") %><%--개발 제품명은 200자를 초과할 수 없습니다--%>');
        return false;
      }
    }

    if ( d.DevReviewTypeCode.value=="0" ) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "00654") %><%--개발유형은 반드시 선택해야 합니다.--%>');
      return false;
    }

    <%-- if (d.DevReviewDetailComment.value.length > 1333){
      alert('<%if(statusCode.equals("D")){%><%=messageService.getString("e3ps.message.ket_message", "00668") %>개발착수내용<%}else{%><%=messageService.getString("e3ps.message.ket_message", "00612") %>개발검토내용<%}%>은 1333자를 초과할 수 없습니다.');
      return false;
    } --%>

    var tBody = document.getElementById("partTable");
    var tLength = tBody.rows.length;

    if(tLength == 2){
             alert('<%=messageService.getString("e3ps.message.ket_message", "02610") %><%--제품정보는 반드시 입력해야 합니다.--%>');
      return false;
    }else{
      var t_checks = d.iPartChk;
      if (t_checks.length > 1){
        for (var i = 0; i < t_checks.length; i++) {
          if ( isNull(d.partName[i].value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02614") %><%--제품정보의 제품명은 반드시 입력해야 합니다--%>');
            return false;
          }else{
            if (d.partName[i].value.length > 200){
                            alert('<%=messageService.getString("e3ps.message.ket_message", "02613") %><%--제품정보의 제품명은 200자를 초과할 수 없습니다--%>');
              return false;
            }
          }

          if ( isNull(d.carTypes[i].value) ) {
<%
  if(divCode.equals("CA")) {
    amsg = messageService.getString("e3ps.message.ket_message", "02616")/*제품정보의 차종은 반드시 입력해야 합니다.*/;
  }
  else {
    amsg = messageService.getString("e3ps.message.ket_message", "03395")/*제품정보의 적용기기는 반드시 입력해야 합니다.*/;
  }
%>
            alert('<%=amsg %>');
            return false;
          }

          if (d.appliedRegion[i].value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02468") %><%--적용부위는 200자를 초과할 수 없습니다--%>');
            return false;
          }

          if (d.currentApplyPart[i].value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "03209") %><%--현적용품은 200자를 초과할 수 없습니다--%>');
            return false;
          }

<%--           if ( d.PackTypeCode[i].value == "0" ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02618") %>제품정보의 포장사양은 반드시 입력해야 합니다.');
            return false;
          }

          if ( isNull(d.buyerAcceptPrice[i].value) ) {
          }else{
            if (isNotDigit(d.buyerAcceptPrice[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "00848") %>고객예상가는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.ketTargetPrice[i].value) ) {
          }else{
            if (isNotDigit(d.ketTargetPrice[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "02969") %>판매목표가는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.targetEarningRate[i].value) ) {
          }else{
            if (isNotDigit(d.targetEarningRate[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01391") %>목표수익율는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.targetInvestmentCost[i].value) ) {
          }else{
            if (isNotDigit(d.targetInvestmentCost[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01393") %>목표투자비는 반드시 숫자여야 합니다');
              return false;
            }
          } --%>
        }
      }else{
        if ( isNull(d.partName.value) ) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02614") %><%--제품정보의 제품명은 반드시 입력해야 합니다--%>');
          return false;
        }else{
          if (d.partName.value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02613") %><%--제품정보의 제품명은 200자를 초과할 수 없습니다--%>');
            return false;
          }
        }

        if ( isNull(d.carTypes.value) ) {
<%
  if(divCode.equals("CA")) {
    amsg = messageService.getString("e3ps.message.ket_message", "02616")/*제품정보의 차종은 반드시 입력해야 합니다.*/;
  }
  else {
    amsg = messageService.getString("e3ps.message.ket_message", "03395")/*제품정보의 적용기기는 반드시 입력해야 합니다.*/;
  }
%>
          alert('<%=amsg %>');
          return false;
        }

        if (d.appliedRegion.value.length > 200){
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02468") %><%--적용부위는 200자를 초과할 수 없습니다--%>');
          return false;
        }

        if (d.currentApplyPart.value.length > 200){
                    alert('<%=messageService.getString("e3ps.message.ket_message", "03209") %><%--현적용품은 200자를 초과할 수 없습니다--%>');
          return false;
        }

        if ( d.PackTypeCode.value == "0" ) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02618") %><%--제품정보의 포장사양은 반드시 입력해야 합니다.--%>');
          return false;
        }


<%--         if ( isNull(d.buyerAcceptPrice.value) ) {
        }else{
          if (isNotDigit(d.buyerAcceptPrice.value)) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00848") %>고객예상가는 반드시 숫자여야 합니다');
            return false;
          }
        }

        if ( isNull(d.ketTargetPrice.value) ) {
        }else{
          if (isNotDigit(d.ketTargetPrice.value)) {
                      alert('<%=messageService.getString("e3ps.message.ket_message", "02969") %>판매목표가는 반드시 숫자여야 합니다');
            return false;
          }
        }

        if ( isNull(d.targetEarningRate.value) ) {
        }else{
          if (isNotDigit(d.targetEarningRate.value)) {
                      alert('<%=messageService.getString("e3ps.message.ket_message", "02969") %>판매목표가는 반드시 숫자여야 합니다');
            return false;
          }
        }

        if ( isNull(d.targetInvestmentCost.value) ) {
        }else{
          if (isNotDigit(d.targetInvestmentCost.value)) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01393") %>목표투자비는 반드시 숫자여야 합니다');
            return false;
          }
        } --%>
      }
    }

    var bptcode = "false";

    if (d.ProductTypeCode[0]=="[object]"){
      for (var i = 0; i < d.ProductTypeCode.length; i++) {
        if (d.ProductTypeCode[i].checked == true) bptcode = "true";
      }
    }

    <%-- if(bptcode=="false"){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02566") %>제품Type은 반드시 선택해야 합니다.');
      return false;
    } --%>

    if (d.EtcSpecification.value.length > 100){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01148") %><%--기타내용은 200자를 초과할 수 없습니다--%>');
      return false;
    }

    <%-- if (d.TabSize.value.length > 200){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00478") %>Tab Size는 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.MaterialSubMaterial.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02206") %>원자재/부자재는 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.ApplyedWire.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02471") %>적용전선은 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.PrimaryFunction.value.length > 1333){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02677") %>주요기능은 1333자를 초과할 수 없습니다');
      return false;
    }

    if (d.Outlook.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03203") %>향후전망은 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.MoldDepreciationTypeEtcInfo.value.length > 200){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01084") %>금형비감가 기타는 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.EquipDepreciationTypeEtcInfo.value.length > 200){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01885") %>설비비감가 기타는 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.DeviceSpecification.value.length > 200){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00142") %>Device사양은 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.EnvironmentalRegulationItem.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03231") %>환경규제항목은 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.BuyerEtcRequirement.value.length > 200){
      alert('<%=messageService.getString("e3ps.message.ket_message", "01152") %>기타요청사항은 200자를 초과할 수 없습니다');
      return false;
    }

    if (d.SalesAdditionalRequirement.value.length > 1333){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02875") %>추가요청사항은 1333자를 초과할 수 없습니다');
      return false;
    } --%>

    return true;
  }

  function productTypes() {
    var d = document.forms[0];
    var productTypeCodes = "0";
    if (d.ProductTypeCode[0]=="[object]"){
      for (var i = 1; i < d.ProductTypeCode.length; i++) {
        if (d.ProductTypeCode[i].checked == true) productTypeCodes =  productTypeCodes + "," + d.ProductTypeCode[i].id;

      }
      productTypeCodes = productTypeCodes.substring(2);
    }
    return productTypeCodes;
  }

  function changeDevReviewType(){
    var fm = document.form01;
    var rCode = fm.DevReviewTypeCode.options[fm.DevReviewTypeCode.selectedIndex].value;

    //기타
    if(rCode=="e3ps.common.code.NumberCode:21068"){
      fm.DevReviewTypeEtc.style.visibility='visible';

    }else{
      fm.DevReviewTypeEtc.style.visibility='hidden';
    }
  }

  function selectDevRequest(){
	    var url="../../jsp/dms/SearchDevRequestPop.jsp?method=setProject&mode=one&developmentStep=R&modal=N";
	    openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
	    /* var returnValue;
	    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=495px; dialogHeight:500px; center:yes");

	    if(typeof returnValue == "undefined" || returnValue == null) {
	      return;
	    }

	    var trArr;
	    trArr = returnValue[0];
	    var d = document.forms[0];
	    d.ProjectOID.value = trArr[0];
	    d.ProjectNo.value = trArr[1];

	    document.location.href="/plm/jsp/dms/CreateDevRequest.jsp?oid=" + trArr[0]; */
  
  
  }

  function setProject(arr){
    if(arr.length == 0) {
      return;
    }

    var trArr;
    trArr = arr[0];
    var d = document.forms[0];
    d.ProjectOID.value = trArr[0];
    d.ProjectNo.value = trArr[1];
  }
  
  window.setProjectNo = function(returnValue){
	  
	  if(returnValue == null) {
          return;
        }
	  var d = document.forms[0];
        var trArr;
        var pjtOids = new Array();
        var pjtNos = new Array();
        for(var i=0; i<returnValue.length; i++){
            trArr = returnValue[i];
            pjtOids[i] = trArr[0];
            pjtNos[i] = trArr[1];
        }
        
        pjtOids = pjtOids.sort();
        pjtNos = pjtNos.sort();
        
        d.ProjectOID.value = pjtOids.join(",");
        d.ProjectNo.value = pjtNos.join(",");

        
        if(d.DivisionCode.value == "ER"){
            if(trArr[17] != '진행'){
                flashEventStart();
            }else{
                flashEventClose();
            }
        }
  }

  function selProjectNo(){
	    var d = document.forms[0];
	    if(d.DevelopmentStep.value=="R"){
	      alert('<%=messageService.getString("e3ps.message.ket_message", "00630") %><%--개발단계에서만 기검토 프로젝트를 입력할 수 있습니다--%>');
	    }else{
	    	
	    	var url="/plm/jsp/project/SearchPjtPop.jsp?mode=m&modal=N&fncall=setProjectNo&paramRadio=1&status=C&type=D";
	        var name = "";
	        var defaultWidth = 1024;
	        var defaultHeight = 768;
	        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
	        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
	        
	        
	     
/* 	      var url="/plm/jsp/project/SearchPjtPop.jsp?status=C&type=D&mode=m&modal=Y&paramRadio=1";
	      returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes"); */

	      

	      //copyReviewPjt(trArr[0])
	      
	      
	    }
	  }

  function delProjectNo(){
    var d = document.forms[0];
    d.ProjectNo.value = "";
    d.ProjectOID.value = "";
    flashEventClose();
  }

  function selectUser() {

    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }
    acceptMember(attacheMembers);
  }

  function acceptMember(arrObj) {
    if(typeof arrObj == "undefined" || arrObj == null) {
      return;
    }
    var fm = document.form01;
    var infoArr = arrObj[0];
    fm.reception.value= infoArr[0];
    fm.receptionName.value= infoArr[4];
  }

  //년도별 예상수량 초과체크
  function checkCarCount(){
    var fm = document.form01
    var returnValue = false;

    var tBody = document.getElementById("iCarTable");
    var tLength = tBody.rows.length;
    var arr1 = new Array();
    var tmpIdx = 0;

    for(var i = 0; i < tLength; i++) {
      if (fm.tId1[i]=="[object]"){
        if(fm.y1[i]=="[object]"){
          arr1[tmpIdx++] = fm.y1[i].value;
          arr1[tmpIdx++] = fm.y2[i].value;
          arr1[tmpIdx++] = fm.y3[i].value;
          arr1[tmpIdx++] = fm.y4[i].value;
          arr1[tmpIdx++] = fm.y5[i].value;
          arr1[tmpIdx++] = fm.y6[i].value;
        }else{
          arr1[tmpIdx++] = fm.y1.value;
          arr1[tmpIdx++] = fm.y2.value;
          arr1[tmpIdx++] = fm.y3.value;
          arr1[tmpIdx++] = fm.y4.value;
          arr1[tmpIdx++] = fm.y5.value;
          arr1[tmpIdx++] = fm.y6.value;
        }
      }else{
        arr1[tmpIdx++] = fm.y1.value;
        arr1[tmpIdx++] = fm.y2.value;
        arr1[tmpIdx++] = fm.y3.value;
        arr1[tmpIdx++] = fm.y4.value;
        arr1[tmpIdx++] = fm.y5.value;
        arr1[tmpIdx++] = fm.y6.value;
      }
    }

    for(var j = 0; j < arr1.length; j++) {
      if(arr1[j] >= 10000){
        returnValue = true;
      }
    }

    return returnValue;

  }

  function doSave(){
    if (!valcheck()) return;
    else {
      if(checkCarCount()){
        if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03320") %><%--예상 수량 단위는 '천개' 입니다. 현재 입력한 수량으로 진행하시겠습니까?--%>")){
          save();
        }
      }else{
                if(confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>')){
          save();
        }
      }
    }
  }

  function save(){
    var d = document.forms[0];
    
    

    var buystr="";
    for (var i = 0; i < d.RequestBuyer.length; i++) {
      buystr = buystr + "," + d.RequestBuyer.options[i].value;
    }
    d.RequestBuyerStr.value = buystr.substring(1);

    buystr="";
    for (var i = 0; i < d.LastUsingBuyer.length; i++) {
      buystr = buystr + "," + d.LastUsingBuyer.options[i].value;
    }
    d.LastUsingBuyerStr.value = buystr.substring(1);
    d.ProductTypeCodes.value = productTypes();

    d.encoding = 'multipart/form-data';
    d.cmd.value = 'update';
    d.action = "/plm/servlet/e3ps/DevRequestServlet?cmd=update";

    d.submit();
  }
  function bindCalendarField(fieldObj){
      $('[name='+fieldObj+']').attr('readonly',false);
      $('[name='+fieldObj+']').removeClass('txt_fieldRO');
      $('[name='+fieldObj+']').addClass('txt_field');
      // Calener field 설정
      CalendarUtil.dateInputFormat(fieldObj);
  }
  
  function unbindCalendarField(fieldObj){
      $('[name='+fieldObj+']').attr('readonly',true);
      $('[name='+fieldObj+']').removeClass('txt_field');
      $('[name='+fieldObj+']').addClass('txt_fieldRO');
      $('[name='+fieldObj+']').val('');
      $('[name='+fieldObj+']').unmask();
      $('[name='+fieldObj+']').datepicker("destroy");
  }
  
  function selectPartCategory(checkedNode){
      var nodeOIdStr='', nodeNameStr='';
      for(var i=0; i < checkedNode.length; i++){
          if(i == checkedNode.length - 1){
              nodeOIdStr += checkedNode[i].id;
              nodeNameStr += checkedNode[i].name;
          }else{
              nodeOIdStr += checkedNode[i].id+','; 
              nodeNameStr += checkedNode[i].name+',';
          }
      }
      //초기화
      $('[name=ProductCategoryCode]').val('');
      $('[name=ProductCategoryName]').val('');
      //재맵핑
      $('[name=ProductCategoryCode]').val(nodeOIdStr);
      $('[name=ProductCategoryName]').val(nodeNameStr);
  }
  
  function selectCarType(){
      var url = '/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s&cbMethod=onModel';
      window.open(url, "selectCarType", "top=100px, left=100px, height=800px, width=1200px");
  }
  
  function onModel(attache) {
      $('[name=RepCarType]').val(attache[0][1]);
      $('[name=RepCarTypeCode]').val(attache[0][0]);
  }
  
  
  function flashEventStart(){
      $("#checkComment").css("display","");
      var x;
      repeat = setInterval(function() {
          if(x == 0) {
              $('#checkComment').hide();
              x = 1;
          } else  {
              $('#checkComment').show();
              x = 0;
          }
      }, 600); // change the time if you want
  }
  
  function flashEventClose(){
      $("#checkComment").css("display","none");
      clearInterval(repeat);
  }
  
  $(document).ready(function(){
      CalendarUtil.dateInputFormat('InitialSampleSummitDate');
      CalendarUtil.dateInputFormat('ESIRDate');
      CalendarUtil.dateInputFormat('ISIRDate');
      CalendarUtil.dateInputFormat('KetMassProductionDate');
      CalendarUtil.dateInputFormat('ProductSaleFirstYear');
      
      CalendarUtil.dateInputFormat('InitialSampleSummitDate2');
      CalendarUtil.dateInputFormat('ESIRDate2');
      CalendarUtil.dateInputFormat('ISIRDate2');
      CalendarUtil.dateInputFormat('KetMassProductionDate2');
      CalendarUtil.dateInputFormat('ProductSaleFirstYear2');
      
      SuggestUtil.bind('CARTYPE', 'RepCarType');
      var pjtNo = '<%=projectNO%>';
      
      $('[name=ProjectNo]').val(pjtNo);
      <%if(!isReviewResult){%>
      flashEventStart();
      <%}%>
      
      numberFormat();
  });
//-->
</script>
<script id='dummy'></script>
</head>
<body>
<form name=form01 method="post" >
<input type="hidden" name="cmd" value="update">
<input type="hidden" name="drOid" value="<%=oid%>">
<input type="hidden" name="currPartLine" value=0 >
<input type="hidden" name="DivisionCode" value="<%=divisionCode%><%--CommonUtil.isMember("전자사업부")?"EA":"CA"--%>" >
<input type="hidden" name="DevelopmentStep" value="<%=statusCode%>" >
<input type="hidden" name="ProjectOID" value="<%=projectOID%>" >
<input type="hidden" name="RequestBuyerStr" value="" >
<input type="hidden" name="LastUsingBuyerStr" value="" >
<input type=hidden name=isFileDel value="0">
<input type="hidden" name="ProductTypeCode" value="0" >
<input type="hidden" name="ProductTypeCodes" value="0" >
<table style="width: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td width="7px"></td>
                                <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03389", developmentStep) %><%--{0}서 수정--%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="176px" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="space5"></td>
    </tr>
</table>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:cancelDevRequest('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
          <td colspan="3" class="tdwhiteL0"><%=reqNo%></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248") %><%--의뢰담당자--%></td>
          <td colspan=3 class="tdwhiteL0"><%=creatorName%>/<%=deptName%></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
          <td width="260" class="tdwhiteL"><%=developmentStep%></td>
          
          <input type="hidden" name="oldProjectNo" value="<%=projectNO%>">
          <%if(statusCode.equals("D")){%>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00736") %><%--검토프로젝트번호--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL0"><input type="text" name="ProjectNo" id="ProjectNo" class='txt_fieldRO' style="width: 100px"  readonly>
            &nbsp;<a href="javascript:selProjectNo()"><img src="../../portal/images/icon_5.png" border="0" align="middle"></a>&nbsp;&nbsp;<a href="javascript:delProjectNo()"><img src="../../portal/images/icon_delete.gif" border="0" align="middle"></a><span id="checkComment" name="checkComment" style="display : none"><font color="red"><b>※사업부장 승인 필요</b></font></span></td>
          <%}else{%>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01114") %><%--기검토 의뢰번호--%></td>
          <td width="260" class="tdwhiteL0"><input type="text" name="ProjectNo" readonly class='txt_fieldRO'  style="width:210" id="ProjectNo" value="<%=projectNO%>">
            &nbsp;<a href="javascript:selectDevRequest()"><img src="../../portal/images/icon_5.png" border="0" align="middle"></a>&nbsp;&nbsp;<a href="javascript:delProjectNo()"><img src="../../portal/images/icon_delete.gif" border="0" align="middle"></a></td>
          <%}%>
        </tr>
        <tr>
          <td width="130" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL"><select name="RequestBuyer" style="width:80%" size=2 multiple >
<%
          if(!StringUtil.checkNull(requestBuyer).equals("")){
            StringTokenizer st = new StringTokenizer(requestBuyer, ",");
            String ct;
            while (st.hasMoreTokens()) {
              ct=st.nextToken();
              NumberCode nCode = (NumberCode)CommonUtil.getObject(ct);
              String bName="";
              if(nCode==null){
                bName=ct;
              }else{
                bName=nCode.getName();
              }
%>
                <option value="<%=ct%>" ><%=bName%></option>
<%          }
          }
%>
             </select>
            &nbsp;&nbsp;<a href="javascript:insertRequestBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a><a href="javascript:deleteRequestBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
          <td width="130" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "00859")%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01205")%><%--접점고객 담당자--%></td>
          <td width="260" class="tdwhiteL0" valign=top><input type="text" name="RequestBuyerManager" class='txt_field'  style="width:258" id="textfield" value="<%=requestBuyerManager %>"></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL"><select name="LastUsingBuyer" style="width:80%" size=2 multiple >
<%
          if(!StringUtil.checkNull(lastUsingBuyer).equals("")){
            StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ",");
            String ct;
            while (st1.hasMoreTokens()) {
              ct=st1.nextToken();
              NumberCode nCode = (NumberCode)CommonUtil.getObject(ct);
              String bName="";
              if(nCode==null){
                bName=ct;
              }else{
                bName=nCode.getName();
              }
%>
                <option value="<%=ct%>"><%=bName%></option>
<%          }
          }
%>
             </select>
            &nbsp;&nbsp;<a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a><a href="javascript:deleteLastUsingBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
          <td width="130" class="tdblueL" valign=middle><%=messageService.getString("e3ps.message.ket_message", "02848") %><%--최종사용처 담당자--%></td>
          <td width="260" class="tdwhiteL0" valign=top><input type="text" name="LastUsingBuyerManager" class='txt_field'  style="width:258" id="textfield2" value="<%=lastUsingBuyerManager%>"></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%if(divCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL">
            <input type="text" name="RepCarType" class='txt_field' style="width: 80%" id="textfield4" value="<%=repCarType%>">
            <input type="hidden" name="RepCarTypeCode" value="<%=repCarType%>">
            <a href="javascript:selectCarType();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="javascript:CommonUtil.deleteValue('RepCarTypeCode','RepCarType');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
<%if(statusCode.equals("R")){%>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00616") %><%--개발검토유형--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL0">
            <select name="DevReviewTypeCode" class="fm_jmp" style="width:247" onchange="javascript:changeDevReviewType();">
              <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                NumberCode nCode1=null;
                int iidx=0;
                String devReviewName = "";

                // 개발검토유형
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "DEVREVIEWTYPE");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i < numCode.size(); i++ ) {
                    if ( devReviewTypeCode.equals(numCode.get(i).get("oid").toString()))
                        devReviewName = numCode.get(i).get("code").toString();
                %>
                <option value="<%=numCode.get(i).get("oid")%>" <%if(devReviewTypeCode.equals(numCode.get(i).get("oid"))){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                <%
                }
                %>
            </select><input type="text" name="DevReviewTypeEtc" class='txt_field' style="width:412; margin: 0px 0px 0px 1px; <%if(!devReviewName.equals("DRT9000")){%> display:none <%}%>" id="DevReviewTypeEtc" value="<%=devReviewTypeEtc%>"></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02527") %><%--제안서 제출일정--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL"><span class="tdwhiteL0">
<%if(isProposalSubmit.equals("1")){%>
            <input name="IsProposalSubmit" type="radio" class="Checkbox" checked value="1" onClick="javascript:showCal(ProposalSubmitDate)">
            <%=messageService.getString("e3ps.message.ket_message", "03134") %><%--필요--%>
            <input name="IsProposalSubmit" type="radio" class="Checkbox" value="2" onClick="javascript:deleteValue('ProposalSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "01626") %><%--불필요--%>
<%}else{%>
      <input name="IsProposalSubmit" type="radio" class="Checkbox" value="1" onClick="javascript:showCal(ProposalSubmitDate)">
            <%=messageService.getString("e3ps.message.ket_message", "03134") %><%--필요--%>
            <input name="IsProposalSubmit" type="radio" class="Checkbox" value="2" checked onClick="javascript:deleteValue('ProposalSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "01626") %><%--불필요--%>
<%}%>
           <input type="text" name="ProposalSubmitDate" readonly class='txt_fieldRO'  style="width:80" id="ProposalSubmitDate" value="<%=proposalSubmitDate%>">
            &nbsp;<img src="../../portal/images/icon_6.png" border="0" onClick="javascript:show_Cal3(ProposalSubmitDate)">&nbsp;<a href="javascript:deleteValue('ProposalSubmitDate')"><img src="../../portal/images/icon_delete.gif" border="0"></a></span></td>
<%}%>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00650") %><%--개발원가 제출일정--%><%if(statusCode.equals("R")){%><span class="red">*</span><%}%></td>
          <td width="260" class="tdwhiteL0">
<%if(isProposalSubmit.equals("1")){%>
          <input name="IsCostSubmit" type="radio" checked class="Checkbox" value="1" onClick="javascript:bindCalendarField('CostSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "03134") %><%--필요--%>
            <input name="IsCostSubmit" type="radio" class="Checkbox" value="2" onClick="javascript:unbindCalendarField('CostSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "01626") %><%--불필요--%>
<%}else{%>
            <input name="IsCostSubmit" type="radio" class="Checkbox" value="1" onClick="javascript:bindCalendarField('CostSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "03134") %><%--필요--%>
            <input name="IsCostSubmit" type="radio" checked class="Checkbox" value="2" onClick="javascript:unbindCalendarField('CostSubmitDate')">
            <%=messageService.getString("e3ps.message.ket_message", "01626") %><%--불필요--%>
<%}%>
            <input type="text" name="CostSubmitDate" readonly class='txt_fieldRO'  style="width:95" id="textfield5" value="<%=costSubmitDate%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('CostSubmitDate');" style="cursor: hand;"></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%if(statusCode.equals("R")){%><%=messageService.getString("e3ps.message.ket_message", "00623") %><%--개발검토제품명--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%><%}%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="DevProductName" class='txt_field'  style="width:664" id="DevProductName" value="<%=devProductName%>"></td>
        </tr>
<%if(!statusCode.equals("R")){%>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><select name="DevReviewTypeCode" class="fm_jmp" style="width:247" onchange="javascript:changeDevReviewType();">
              <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                NumberCode nCode1=null;
                int iidx=0;
                String devReviewName = "";

                // 개발검토유형
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "DEVREVIEWTYPE");
                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i < numCode.size(); i++ ) {
                    if ( devReviewTypeCode.equals(numCode.get(i).get("oid").toString()))
                        devReviewName = numCode.get(i).get("code").toString();
                %>
                <option value="<%=numCode.get(i).get("oid")%>" <%if(devReviewTypeCode.equals(numCode.get(i).get("oid"))){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                <%
                }
                %>
            </select><input type="text" name="DevReviewTypeEtc" class='txt_field'  style="width:412; margin: 0px 0px 0px 1px; <%if(!devReviewName.equals("DRT9000")){%> visibility:hidden <%}%>" id="DevReviewTypeEtc" value="<%=devReviewTypeEtc%>">
            </td>
        </tr>
<%}%>        
        <tr>
          <td width="130" style="height:50" class="tdblueL"><%if(statusCode.equals("D")){%><%=messageService.getString("e3ps.message.ket_message", "00668") %><%--개발착수내용--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "00612") %><%--개발검토내용--%><%}%></td>
          <td colspan="3" style="height:50" class="tdwhiteL0">
                <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
                    <tr>
                        <td width="90" class="tdgrayL">1.<%
                        if (divCode.equals("CA")) {
                        %><%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%> <%
                            } else {
                        %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                            }
                        %></td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute2" style="width:99%" class='txt_field' value="<%=attribute2%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">2.적용부위</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute3" style="width:99%" class='txt_field' value="<%=attribute3%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">3.개발부품</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute4" style="width:99%" class='txt_field' value="<%=attribute4%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">4.개발일정</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute5" style="width:99%" class='txt_field' value="<%=attribute5%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">5.양산투자비</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute6" style="width:99%" class='txt_field' value="<%=attribute6%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td  class="tdgrayL">6.납입처</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute7" style="width:99%" class='txt_field' value="<%=attribute7%>" maxlength="1000"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">7.예상매출액</td>
                        <td width="*" style="padding-left:2px"><textarea name="attribute8" style="width:99%; height:50px" cols="3" rows="2" class='txt_field'><%=attribute8%></textarea></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">8.<%=messageService.getString("e3ps.message.ket_message", "03202")%><%--향후전망--%></td>
                        <td width="*" style="padding-left:2px"><input type="text" name="Outlook" value="<%=outlook%>" style="width:99%" class='txt_field' maxlength="1200"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">9.<%=messageService.getString("e3ps.message.ket_message", "09475")%><%--경쟁사 정보--%></td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute10" style="width:99%" class='txt_field' value='<%=attribute10%>' maxlength="1000"/></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">10.<%=messageService.getString("e3ps.message.ket_message", "09476")%><%--KET 단독입찰여부--%></td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute11" value="<%=attribute11%>" style="width:99%" class='txt_field' maxlength="1200"></td>
                    </tr>
                    <tr>
                        <td class="tdgrayL">11.기타</td>
                        <td width="*" style="padding-left:2px"><input type="text" name="attribute9" style="width:99%" class='txt_field' value='<%=attribute9%>' maxlength="1000"/></td>
                    </tr>
                </table> 
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02874") %><%--추가요청사항 및 첨부Data--%></td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="120" style="height:100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02873") %><%--추가요청사항--%></td>
          <td style="height:100" class="tdwhiteL0"><textarea name="SalesAdditionalRequirement" class='txt_field' id="SalesAdditionalRequirement" style="width:100%; height:96%"><%=salesAdditionalRequirement%></textarea></td>
        </tr>
        <tr>
          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td class="tdwhiteM0">
             <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tr class="headerLock3">
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                <tr>
                                    <td width="20" class="tdgrayM">
                                        <a href="#" onclick="javascript:insertFileLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                                    <td width="" class="tdgrayM0">파일명(※ <%=messageService.getString("e3ps.message.ket_message", "03252")%><%--회의록, 주변Data, Concept view 관련 파일 유첨바랍니다.--%>)</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                <col width="20">
                                <col width="">
                                <tbody id="iFileTableOld">
                                <%
                                       String appDataOid;
                                       String urlpath;
                                       String iconpath;
                                
                                      if ( dr instanceof FormatContentHolder ) {
                                        FormatContentHolder holder = (FormatContentHolder)dr;
                                        Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
                                        if(secondaryFiles.size()>0){
                                
                                          for(int i=0; i<secondaryFiles.size(); i++) {
                                            ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
                                
                                            if( info == null) {
                                              iconpath = "";
                                              urlpath = "";
                                            } else {
                                              ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
                                              appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                              //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                              urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                              urlpath = "<a href=javascript:PLM_FILE_DOWNLOAD('" + urlpath + "');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                              iconpath = info.getIconURLStr();
                                
                                            }
                                %>
                                <tr>
                                    <td class="tdwhiteM"><a href="#" onclick="javascript:$(this).parent().parent().remove();deleteFile('<%=info.getContentOid()%>');return false;"><img src="/plm/portal/images/b-minus.png"></a>
                                        <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                    </td>
                                    <td class="tdwhiteL0"><input name='ContentOid' class='txt_fieldRO' id='ContentOid' type='hidden' value='<%=info.getContentOid()%>'><%=iconpath%>&nbsp;<%=urlpath%>
                                    </td>
                                </tr>
                                <%
                                          }
                                        }
                                      }
                                %>
                </tbody>
                </table>
            </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertPartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deletePartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:copyPartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00128") %><%--Copy 등록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <DIV style='overflow:auto;width:991px;height:195;'>
            <table id='partTable' width="1460" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="40px" rowspan="2" class="tdblueM">&nbsp;</td>
                <td width="150px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02591") %><%--제품명--%></td>
                <td width="120px" rowspan="2" class="tdblueM"><%if(divCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%> <%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%> <%}%></td>
                <td width="80px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02467") %><%--적용부위--%></td>
                <td width="60px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03208") %><%--현적용품--%></td>
                <td width="50px" rowspan="2" class="tdblueM">U/S</td>
                <td colspan="7" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02154") %><%--예상수량(천개/년)--%></td>
                <td width="70px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02991") %><%--포장사양--%><span class="red">*</span></td>
                <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01168") %><%--납입처--%></td>
                <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00847") %><%--고객예상가(원)--%></td>
                <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02968") %><%--판매목표가(원)--%></td>
                <td width="100px" colspan="<%="D".equals(statusCode) ? 2 : 1 %>" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01390") %><%--목표수익률(%)--%></td>
                <td width="50px" rowspan="2" class="tdblueM">기간<br>수익률</td>
                <td width="105px" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01392") %><%--목표투자비(천원)--%></td>
              </tr>
              <tr>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 1) %><%--{0}년차--%></td>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 2) %><%--{0}년차--%></td>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 3) %><%--{0}년차--%></td>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 4) %><%--{0}년차--%></td>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 5) %><%--{0}년차--%></td>
                <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 6) %><%--{0}년차--%></td>
                <td width="60px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
                <td width="100" class="tdblueM">초도</td>
                <%if("D".equals(statusCode)){ %>
                <td width="100" class="tdblueM">평균</td>
                <%} %>
              </tr>
<%
         KETRequestPartList pl = null;
         QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
         int lineTid = 10000;
         while(r.hasMoreElements()){
           pl = (KETRequestPartList) r.nextElement();
%>
     <tr onMouseOver="javascript:fncOnMouseOver(this.rowIndex)">
<%
           String partName=pl.getPartName();
           lineTid++;
%>
     <td width="40" class="tdwhiteM"><input type='checkbox' name='iPartChk' id='iPartChk' ><input type='hidden' name='tId' id='tId' value='<%=lineTid%>'></td>
     <td width="150" class="tdwhiteM"><input type='text' class='txt_field' style='width:140' name='partName' value='<%=StringUtil.checkNull(pl.getPartName())%>'></td>
<%
           KETCarYearlyAmount cy = null;
            QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

           double d = 0;
           double y1  = 0;
           double y2  = 0;
           double y3  = 0;
           double y4  = 0;
           double y5  = 0;
           double y6  = 0;
           double sum = 0;
           int usg = 0;
           String carTypeCode = "";

           while(r1.hasMoreElements()){
             cy = (KETCarYearlyAmount) r1.nextElement();

             d = (cy.getYearAmount1()*cy.getApplyedUsage()*cy.getOptionRate())/100;
             y1 = y1 + d;
         y1 = Math.round(y1);
        // out.println(d);
             d = cy.getYearAmount2()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y2 = y2 + d;
         y2 = Math.round(y2);

             d = cy.getYearAmount3()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y3 = y3 + d;
          y3 = Math.round(y3);

             d = cy.getYearAmount4()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y4 = y4 + d;
         y4 = Math.round(y4);

             d = cy.getYearAmount5()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y5 = y5 + d;
          y5 = Math.round(y5);

             d = cy.getYearAmount6()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y6 = y6 + d;
          y6 = Math.round(y6);

             if (usg < cy.getApplyedUsage()){
               usg = cy.getApplyedUsage().intValue();
             }

             carTypeCode = carTypeCode + "," + cy.getCarTypeCode();
           }
           if(!"".equals(carTypeCode) && carTypeCode != null){
             carTypeCode = carTypeCode.substring(1);
           }

         sum = y1 + y2 + y3 + y4 + y5 + y6;
         if(cy != null){
           sum = Math.round(sum*cy.getApplyedUsage()*cy.getOptionRate()/100);
         }
         String packTypeCode = StringUtil.checkNull(pl.getPackTypeCode());
         String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());
         String sdName = "";

          NumberCode nCd = (NumberCode)CommonUtil.getObject(summitDestinationOid);
         if(nCd==null){
           sdName=summitDestinationOid;
         }else{
           sdName=nCd.getName();
         }
%>

     <td width="120" class="tdwhiteM"><input type='text' readonly class='txt_fieldRO' style='width:90' name='carTypes' value='<%=carTypeCode%>'>&nbsp;<a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a></td>
     <td width="80" class="tdwhiteM"><input type='text' class='txt_field' style='width:75' name='appliedRegion' value='<%=StringUtil.checkNull(pl.getAppliedRegion())%>'></td>
     <td width="60" class="tdwhiteM"><input type='text' class='txt_field' style='width:55' name='currentApplyPart' value='<%=StringUtil.checkNull(pl.getCurrentApplyedPart())%>'></td>
     <td width="50" class="tdwhiteM"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='applyedUsage' value='<%=usg%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount1' value='<%=StringUtil.getDouble(y1, "", "###,###")%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount2' value='<%=StringUtil.getDouble(y2, "", "###,###")%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount3' value='<%=StringUtil.getDouble(y3, "", "###,###")%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount4' value='<%=StringUtil.getDouble(y4, "", "###,###")%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount5' value='<%=StringUtil.getDouble(y5, "", "###,###")%>'></td>
     <td width="50" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:45' name='yearAmount6' value='<%=StringUtil.getDouble(y6, "", "###,###")%>'></td>
     <td width="60" class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width:55' name='sumAmount' value='<%=StringUtil.getDouble(sum, "", "###,###")%>'></td>
     <td width="70" class="tdwhiteM">
     <select name='PackTypeCode' class='fm_jmp' style='width:60'>
     <option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
     <%
     // 포장사양
     parameter.clear();
     parameter.put("locale",   messageService.getLocale());
     parameter.put("codeType", "PACKTYPE");
     numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
     for ( int i=0; i < numCode.size(); i++ ) {
     %>
         <option value="<%=numCode.get(i).get("oid")%>" <%if(numCode.get(i).get("oid").equals(packTypeCode)){%> selected <%}%>><%=numCode.get(i).get("value")%></option>
     <%
     }
     %>
     </select>
     </td>
     <td width="105" class="tdwhiteM"><input type='hidden' name='summitDestinationOid' value='<%=summitDestinationOid%>'><input type='text' readonly class='txt_fieldRO' style='width:62' name='summitDestination' value='<%=sdName%>'>&nbsp;<img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'><a href='javascript:deleteCustom()'><img src='../../portal/images/icon_delete.gif' border='0'></a></td>
     <td width="105" class="tdwhiteM"><input type='text' class='txt_fieldR' style='width:95' name='buyerAcceptPrice' value='<%=pl.getBuyerAcceptPrice()%>'></td>
     <td width="105" class="tdwhiteM"><input type='text' class='txt_fieldR' style='width:95' name='ketTargetPrice' value='<%=pl.getKetTargetPrice()%>'></td>
     <td width="120" class="tdwhiteM"><input type='text' class='txt_fieldR' style='width:40' name='targetEarningRate' value='<%=pl.getTargetEarningRate()%>'></td>
     <%if("D".equals(statusCode)) {%>
     <td width="120" class="tdwhiteM"><input type='text' class='txt_fieldR' style='width:40' name='targetAverageRate' value='<%=pl.getTargetAverageRate() == null ? 0 : pl.getTargetAverageRate()%>'></td>
     <%} %>
     <td width="40" class="tdwhiteM"><input type='text' class='txt_fieldR' style='width:40' name='targetTermRate' value='<%=pl.getTargetTermRate()%>'></td>
     <td width="105" class="tdwhiteM0"><input type='text' class='txt_fieldR' style='width:95' name='targetInvestmentCost' value='<%=pl.getTargetInvestmentCost()%>'></td>
     
     
       </tr>
<%
     }
%>

    </table>
    </DIV>
    </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border" >
        <tr>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02734") %><%--진행일정--%></td>
        <%if(divisionCode.equals("CA")){%>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823") %><%--초도품 제출일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228") %><%--ESIR일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261") %><%--ISIR일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
        <%}else{ %>
          <%if(statusCode.equals("D")){%>
          <td width="140" class="tdblueM">DR0(목표)일<span class="red">*</span></td>
          <td width="140" class="tdblueM">초도품 제출일정 <span class="red">*</span></td>
          <td width="140" class="tdblueM">승인원 제출 일정</td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
          <%}else{%>
          <td width="140" class="tdblueM">개발시작회의<span class="red">*</span></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02824")%><span class="red">*</span></td><%--초도품 제출--%>
          <td width="140" class="tdblueM">승인샘플/승인원 제출<span class='red'>*</span></td>
          <td width="140" class="tdblueM">최종고객승인<span class='red'>*</span></td>
          <%}%>
          <%}%>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%--고객SOP--%><span class="red">*</span></td>
        </tr>
        <tr>
          <td width="140" class="tdwhiteM">
            <input type="text" name="InitialSampleSummitDate" class='txt_field'  style="width: 72%" id="InitialSampleSummitDate" value="<%=initialSampleSummitDate%>">
            <a href="javascript:deleteValue('InitialSampleSummitDate')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
          <td width="140" class="tdwhiteM">
            <input type="text" name="ESIRDate" class='txt_field' style="width:72%" id="ESIRDate" value="<%=eSIRDate%>">
            <a href="javascript:deleteValue('ESIRDate')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text"  name="ISIRDate" class='txt_field'  style="width:72%" id="ISIRDate" value="<%=iSIRDate%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ISIRDate');" style="cursor: hand;"></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text"  name="KetMassProductionDate" class='txt_field'  style="width:72%" id="KetMassProductionDate" value="<%=ketMassProductionDate%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('KetMassProductionDate');" style="cursor: hand;"></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text" class='txt_field'  name="ProductSaleFirstYear" style='width:72%' id="ProductSaleFirstYear" value="<%=productSaleFirstYear%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ProductSaleFirstYear');" style="cursor: hand;"></td>

        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border" >
        <tr>
          <td width="60" rowspan="2" class="tdblueM"><input type="text" name="scheduleName" class='txt_field'  style="width: 100%" id="scheduleName" value="<%=scheduleName%>"></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823") %><%--초도품 제출일정--%><span class="red"></span></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228") %><%--ESIR일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261") %><%--ISIR일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
          <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%--고객SOP--%></td>
        </tr>
        <tr>
          <td width="140" class="tdwhiteM">
            <input type="text" name="InitialSampleSummitDate2" class='txt_field'  style="width: 72%" id="InitialSampleSummitDate2" value="<%=InitialSampleSummitDate2%>">
            <a href="javascript:deleteValue('InitialSampleSummitDate2')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
          <td width="140" class="tdwhiteM">
            <input type="text" name="ESIRDate2" class='txt_field' style="width:72%" id="ESIRDate2" value="<%=ESIRDate2%>">
            <a href="javascript:deleteValue('ESIRDate2')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text"  name="ISIRDate2" class='txt_field'  style="width:72%" id="ISIRDate2" value="<%=ISIRDate2%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ISIRDate2');" style="cursor: hand;"></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text"  name="KetMassProductionDate2" class='txt_field'  style="width:72%" id="KetMassProductionDate2" value="<%=KetMassProductionDate2%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('KetMassProductionDate2');" style="cursor: hand;"></td>
          <td width="140" class="tdwhiteM">&nbsp;<input type="text" class='txt_field'  name="ProductSaleFirstYear2" style='width:72%' id="ProductSaleFirstYear2" value="<%=ProductSaleFirstYear2%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ProductSaleFirstYear2');" style="cursor: hand;"></td>

        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02679") %><%--주요제품 사양--%></td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="130" class="tdblueL">제품구분</td>
          <td colspan="3" class="tdwhiteL0">
            <input type="text" name="ProductCategoryName" class="txt_field" style="width: 300px" value="<%=productCategoryName%>">
            <input type="hidden" name="ProductCategoryCode" value="<%=productCategoryCode%>">
            <a href="javascript:SearchUtil.selectPartClaz(selectPartCategory,'onlyLeaf=Y&openAll=N&depthLevel2=Y');">
            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('ProductCategoryCode','ProductCategoryName');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
           </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01147") %><%--기타내용--%></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="EtcSpecification" class='txt_field'  style="width:100%" id="EtcSpecification" value="<%=etcSpecification%>"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="5" valign="bottom">
      <DIV style='overflow:auto;width:640;height:4;'>
        <table width="600" height="3" border="0" cellpadding="0" cellspacing="0">
          <tbody id="iCarTable"/>
<%
         pl = null;
         r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
         lineTid = 10000;
         while(r.hasMoreElements()){
           pl = (KETRequestPartList) r.nextElement();
           String partName=pl.getPartName();
           lineTid++;
           KETCarYearlyAmount cy = null;
           QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

           while(r1.hasMoreElements()){
             cy = (KETCarYearlyAmount) r1.nextElement();
%>
         <tr>
               <td><input type='hidden' readonly name='tId1' value='<%=lineTid%>'></td>
               <td><input type='hidden' readonly name='y1' value='<%=cy.getYearAmount1()%>'></td>
               <td><input type='hidden' readonly name='y2' value='<%=cy.getYearAmount2()%>'></td>
               <td><input type='hidden' readonly name='y3' value='<%=cy.getYearAmount3()%>'></td>
               <td><input type='hidden' readonly name='y4' value='<%=cy.getYearAmount4()%>'></td>
               <td><input type='hidden' readonly name='y5' value='<%=cy.getYearAmount5()%>'></td>
               <td><input type='hidden' readonly name='y6' value='<%=cy.getYearAmount6()%>'></td>
               <td><input type='hidden' readonly name='sum' value='<%=cy.getYearAmount1()+cy.getYearAmount2()+cy.getYearAmount3()+cy.getYearAmount4()+cy.getYearAmount5()+cy.getYearAmount6()%>'></td>
               <td><input type='hidden' readonly name='usage' value='<%=cy.getApplyedUsage().intValue()%>'></td>
               <td><input type='hidden' readonly name='optRate' value='<%=cy.getOptionRate().intValue()%>'></td>
               <td><input type='hidden' readonly name='carTypeOid' value='<%=cy.getCarType()%>'></td>
               <td><input type='hidden' readonly name='carTypeCode' value='<%=cy.getCarTypeCode()%>'></td>
               </tr>
<%
           }
     }
%>
            </table>
      </DIV>
    </td>
  </tr>
</table>
</form>
</body>
</html>
