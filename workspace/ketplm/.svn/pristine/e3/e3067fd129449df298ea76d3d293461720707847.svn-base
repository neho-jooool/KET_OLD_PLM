<%@page import="e3ps.groupware.company.People"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Hashtable"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page
    import="java.util.StringTokenizer,
                  java.util.Vector,
                  java.util.ArrayList,
                  java.util.HashMap,
                  java.util.Map,
                  java.util.List,
                  e3ps.groupware.company.PeopleData,
                  e3ps.common.util.*,
                  e3ps.common.content.*,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.dms.entity.*,
                  e3ps.project.E3PSProject,
                  e3ps.project.ProjectOutput,
                  wt.org.WTUser,
                  wt.session.SessionHelper,
                  wt.content.*,
                  wt.query.QuerySpec,
                  wt.query.SearchCondition,
                  wt.query.ClassAttribute,
                  wt.query.OrderBy,
                  wt.fc.PersistenceHelper,
                  wt.fc.QueryResult"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

  WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
  String userName = user.getFullName();
  PeopleData peoData = new PeopleData(user);
  String deptName = peoData.departmentName;
  String divCode = "";

  String statusCode = null;
  String status = null;
  statusCode = request.getParameter("step");

  if(statusCode == null) statusCode="R";

  if(statusCode.equals("R")){
    status =  messageService.getString("e3ps.message.ket_message","00617");//"개발검토의뢰"
  }else if(statusCode.equals("D")){
    status =  messageService.getString("e3ps.message.ket_message","00656");//"개발의뢰";
  }else{
    statusCode="R";
    status =  messageService.getString("e3ps.message.ket_message","00617");//"개발검토의뢰"
  }

  String projectType = "";
  if(CommonUtil.isMember("전자사업부") && !deptName.startsWith("전장IT")){
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

  //기존 개발검토의뢰서 데이터 호출시 oid 받음
  String oid = request.getParameter("oid");

  String reqNo = "";
  String reqName = "";
  String reception = "";
  String receptionName = "";

  String creatorName = "";
  String lifeCycleState = "";
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
  KETDevelopmentRequest dr = null;
   
   
   String InitialSampleSummitDate2 = "";
   String ESIRDate2 = "";
   String ISIRDate2 = "";
   String ProductSaleFirstYear2 = "";
   String KetMassProductionDate2 = "";
   String scheduleName = "";

  //기존 개발검토의뢰서데이터 추출하는경우
  if(oid!=null){
    if(oid.equals("0000")){

    }else{
      //KETDevelopmentRequest oid로 해당 객체의 정보를 화면에 나타낸다.
      dr = (KETDevelopmentRequest)CommonUtil.getObject(oid);
      reqNo = dr.getNumber();

      reception = StringUtil.checkNull(dr.getReception());
      if(!reception.equals("")){
        WTUser user1 = (WTUser)CommonUtil.getObject(reception);
        receptionName = user1.getFullName();
      }

      //의뢰처, 최종사용처
      requestBuyer = StringUtil.checkNull(dr.getRequestBuyer());
      requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());
      lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer());
      lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());

        //대표차종
      repCarType = StringUtil.checkNull(dr.getRepCarType());
      //DR요구일정
      isDRRequest = StringUtil.checkNull(dr.getIsDRRequest());
      if(isDRRequest.equals("1")){
        drRequestDate = DateUtil.getTimeFormat(dr.getDrRequestDate(),"yyyy-MM-dd");
      }else{
        drRequestDate = "";
      }
      //제안서제출일정
      isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
      if(isProposalSubmit.equals("1")){
        proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(),"yyyy-MM-dd");
      }else{
        proposalSubmitDate = "";
      }
      //개발원가제출일정
      isCostSubmit = StringUtil.checkNull(dr.getIsCostSubmit());
      if(isCostSubmit.equals("1")){
        costSubmitDate = DateUtil.getTimeFormat(dr.getCostSubmitDate(),"yyyy-MM-dd");
      }else{
        costSubmitDate = "";
      }
      //제품명
      devProductName = StringUtil.checkNull(dr.getDevProductName());

      //개발검토유형
      devReviewTypeCode = StringUtil.checkNull(dr.getDevReviewTypeCode());
      devReviewTypeEtc = StringUtil.checkNull(dr.getDevReviewTypeEtc());
      //상세내용
      devReviewDetailComment = StringUtil.checkNull(dr.getDevReviewDetailComment());
      //고객SOP
      productSaleFirstYear = StringUtil.checkNull(dr.getProductSaleFirstYear());
      //초도품제출일정
      initialSampleSummitDate = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(),"yyyy-MM-dd");
      //ESIR일정
      eSIRDate = DateUtil.getTimeFormat(dr.getESIRDate(),"yyyy-MM-dd");
      //ISIR일정
      iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(),"yyyy-MM-dd");
      ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(),"yyyy-MM-dd");
      //제품군
      productCategoryCode = StringUtil.checkNull(dr.getProductCategoryCode());
      //제품Type
      productTypeCode = StringUtil.checkNull(dr.getProductTypeCode());

      //기타내용
      etcSpecification = StringUtil.checkNull(dr.getEtcSpecification());
      //Tab Size
      tabSize = StringUtil.checkNull(dr.getTabSize());
      //원자재부자재
      materialSubMaterial = StringUtil.checkNull(dr.getMaterialSubMaterial());
      //표면처리
      surfaceTreatmentCode = StringUtil.checkNull(dr.getSurfaceTreatmentCode());
      //적용전선
      applyedWire = StringUtil.checkNull(dr.getApplyedWire());
      //주요기능
      primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction());
      //향후전망
      outlook = StringUtil.checkNull(dr.getOutlook());
      //금형비감가
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
      //설비비감가
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

      //Device사양
      deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
      //환경규제항목
      environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem());
      //기타요청사항
      buyerEtcRequirement = StringUtil.checkNull(dr.getBuyerEtcRequirement());
      //추가요청사항
      salesAdditionalRequirement = StringUtil.checkNull(dr.getSalesAdditionalRequirement());
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
      
      attribute10 = StringUtil.checkNull(dr.getAttribute10());
      
      attribute11 = StringUtil.checkNull(dr.getAttribute11());
      
      //고객SOP
      ProductSaleFirstYear2 = DateUtil.getTimeFormat(dr.getProductSaleFirstYear2() ,"yyyy-MM-dd");
      
      InitialSampleSummitDate2 = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate2() ,"yyyy-MM-dd");
      ESIRDate2 = DateUtil.getTimeFormat(dr.getESIRDate2() ,"yyyy-MM-dd");
      ISIRDate2 = DateUtil.getTimeFormat(dr.getISIRDate2() ,"yyyy-MM-dd");
      KetMassProductionDate2 = DateUtil.getTimeFormat(dr.getKetMassProductionDate2() ,"yyyy-MM-dd");
      scheduleName = dr.getScheduleName(); 
      
      //제품 정보
      KETRequestPartList pl = null;
      QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
      if (r.hasMoreElements()){
        pl = (KETRequestPartList) r.nextElement();
        String partName=pl.getPartName();
      }
     }
  }

  String amsg; // alert 메시지용 변수
  
  Hashtable<String, String> hashTab = null;
  ArrayList<Hashtable> list = null;
  
  String internalManager = "";
  String outsideManager = "";
  
  list = CommonUtil.findUser("국내차종관리","userId");
  
  for(int i=0; i<list.size(); i++){
      hashTab = (Hashtable) list.get(i);
      String temp = "";
      People people = PeopleHelper.manager.getPeople((String)hashTab.get("userId"));
      PeopleData pd = new PeopleData(people);
      internalManager += pd.name + "("+pd.departmentName +")" +",";
  }
  
  internalManager = StringUtils.removeEnd(internalManager, ",");
  
  list = CommonUtil.findUser("국외차종관리","userId");
  
  for(int i=0; i<list.size(); i++){
      hashTab = (Hashtable) list.get(i);
      String temp = "";
      People people = PeopleHelper.manager.getPeople((String)hashTab.get("userId"));
      PeopleData pd = new PeopleData(people);
      outsideManager += pd.name + "("+pd.departmentName +")" +",";
  }
  
  outsideManager = StringUtils.removeEnd(outsideManager, ",");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=status%> <%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></title>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/processing.html"%>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=javascript src="../../portal/js/Calendar.js"></script>
<script language=javascript src="/plm/portal/js/util.js"></script>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
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
var repeat;
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

  //납입처삭제
  function deleteCustom(obj) {
    /* $(obj).prev().prev().val('');
    $(obj).prev().prev().prev().val(''); */
    
    var fm = document.form01
    var cLine = document.form01.currPartLine.value;
    var selLine = cLine - 2;
    
    $('[name=summitDestinationOid]').eq(selLine).val('');
    $('[name=summitDestination]').eq(selLine).val('');
    
    /* if(selLine==0){
      if (fm.summitDestination[0]=="[object]"){
        fm.summitDestinationOid[0].value = "";
        fm.summitDestination[0].value = "";
      }else{
        fm.summitDestinationOid.value = "";
        fm.summitDestination.value = "";
      }
    }else{
      fm.summitDestinationOid[selLine].value = "";
      fm.summitDestination[selLine].value = "";
    } */
  }

  //연도별예상수량 지정화면 open
  function openCarTypeWindow() {
    var fm = document.form01
    var cLine = document.form01.currPartLine.value;
    var selLine = cLine - 2;
    var body = document.getElementById("partTable");
    var t_checks = fm.iPartChk;
    var cTypes;
    var cTid;
    var returnValue;

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
    var divCode = fm.divCode.value;
    if(cTypes!=""){ //데이터가 존재한다면 연도별예상수량 지정화면에 데이터를 넘김
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
      var url="/plm/jsp/dms/AddDevRequestPart.jsp?cLine="+cLine+"&arr="+arr1+"&divCode="+divCode;
      //returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");
      
    }else{
      var url="/plm/jsp/dms/AddDevRequestPart.jsp?cLine="+cLine+"&arr=0"+"&divCode="+divCode;
      //returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=720px; dialogHeight:500px; center:yes");
      
    }
    window.open(url, "AddDevRequestPart", "top=100px, left=100px, height=500px, width=730px");
  }

  //화면상의 마우스의 제품정보 라인확인
  function fncOnMouseOver(rowIndex) {
    document.form01.currPartLine.value = rowIndex;
  }

  //연도별예상수량 지정화면에서 지정된 데이터를 받아  제품정보 화면에 나타냄
  function getCarType(arr, arr1, cLine){
    var fm = document.form01
    var body = document.getElementById("partTable");
    var tBody = document.getElementById("iCarTable");

    if (body.rows.length == 2) return;
    var t_checks = fm.iPartChk;

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
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='tId1' class='txt_fieldRO' style='width:5px'  value='" + arr1[i][0] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y1' class='txt_fieldRRO' style='width:5px'  value='" + removecomma(arr1[i][1]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y2' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][2]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y3' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][3]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y4' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][4]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y5' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][5]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y6' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][6]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='sum' class='txt_fieldRRO' style='width:5px' value='" + removecomma(arr1[i][7]) + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='usage' class='txt_fieldRRO' style='width:5px' value='" + arr1[i][8] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='optRate' class='txt_fieldRRO' style='width:5px' value='" + arr1[i][9] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeOid' class='txt_fieldRO' style='width:5px' value='" + arr1[i][10] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeCode' class='txt_fieldRO' style='width:5px' value='" + arr1[i][11] + "'>";

    }
  }

  function removecomma(s){
    s = s.replace(/\,/, "");
    return s;
  }

  //선택된 제품정보를 삭제함
  function deletePartLine() {
    var fm = document.form01

    var body = document.getElementById("partTable");
    var tBody = document.getElementById("iCarTable");
    
    $('[name=iPartChk]:checked').parent().parent().remove();
   
/* 
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
    } */
  }

  //제품정보의 라인을 추가함
  function insertPartLine() {
    var tBody = document.getElementById("partTable");
    var innerRow = tBody.insertRow();
    innerRow.onmouseover=function(){fncOnMouseOver(this.rowIndex)};

    var tLength = tBody.rows.length;

    var innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 40;
    innerCell.innerHTML = "<input type='checkbox' name='iPartChk' id='iPartChk' ><input type='hidden' name='tId' id='tId' value='0'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 150;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:140px' name='partName'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 120;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRO' style='width:90px' name='carTypes'>&nbsp;<a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 80;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:65px' name='appliedRegion'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' class='txt_field' style='width:45px' name='currentApplyPart'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='applyedUsage'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount1'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount2'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount3'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount4'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount5'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 50;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='yearAmount6'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 60;
    innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:35px' name='sumAmount'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 70;
    innerCell.innerHTML = "<select name='PackTypeCode' class='fm_jmp' style='width:60px'><option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>" + "<%=packArrStr%>" + "</select>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = '105px';
    innerCell.innerHTML = "<input type='hidden' name='summitDestinationOid'><input type='text' readonly class='txt_fieldRO' style='width:60px' name='summitDestination'>&nbsp;<img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'><a href='javascript:deleteCustom(this)'><img src='../../portal/images/icon_delete.gif' border='0'></a>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
   // innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='buyerAcceptPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='ketTargetPrice'>";

    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 70;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40px' name='targetEarningRate'>";
    
    <%if(statusCode.equals("D")) {%>
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 70;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40px' name='targetAverageRate'>";
    <%} %>
    
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:40px' name='targetTermRate'>";
    
    innerCell = innerRow.insertCell();
    innerCell.className = "tdwhiteM";
    //innerCell.width = 105;
    innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='targetInvestmentCost'>";
    
    $("input[name='buyerAcceptPrice']").number(true, 1);
    $("input[name='ketTargetPrice']").number(true, 1);
    $("input[name='targetEarningRate']").number(true, 1);
    $("input[name='targetAverageRate']").number(true, 1);
    $("input[name='targetInvestmentCost']").number(true);
    $("input[name='targetTermRate']").number(true, 1);

  }

  //선택된 제품정보의 라인을 복사함
  function copyPartLine() {
    var fm = document.form01
    var body = document.getElementById("partTable");
    var t_checks = fm.iPartChk;
    var selLine = $('[name=iPartChk]').index($('[name=iPartChk]:checked'));
    var divCode = fm.divCode.value;
    var amsg = "";
    if ($('[name=iPartChk]').length == 0){
          alert('<%=messageService.getString("e3ps.message.ket_message", "02607") %><%--제품을 추가하시고 선택하셔야 합니다.--%>');
      return;
    }
    
    if ($('[name=iPartChk]:checked').length > 1){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01534") %><%--복사할 제품을 하나만 선택하셔야 합니다--%>');
        return;
    }

    if ($('[name=iPartChk]:checked').length == 0){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02606") %><%--제품을 선택하셔야 합니다--%>');
        return;
    }

    if ($('[name=iPartChk]:checked').length == 1){
      if( isNull($('[name=partName')[selLine].value) || isNull($('[name=carTypes]')[selLine].value)) {
          
          if(divCode == "CA"){
              //제품명과 차종이 정해진 제품을 선택하셔야 합니다.
              amsg = '<%=messageService.getString("e3ps.message.ket_message", "03390")%>';
          }else{
              //제품명과 적용기기가 정해진 제품을 선택하셔야 합니다
              amsg = '<%=messageService.getString("e3ps.message.ket_message", "03391")%>';
          }
          alert(amsg);
          return;
      }
    }
    var pLength = $('#partTable tr').length - 2;
    insertPartLine();
    var destLine = pLength;

    //fm.partName[destLine].value      = fm.partName[selLine].value;
    fm.appliedRegion[destLine].value      = fm.appliedRegion[selLine].value;
    fm.currentApplyPart[destLine].value   = fm.currentApplyPart[selLine].value;
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
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='tId1' class='txt_field' style='width:5px'  value='" + arr1[i][0] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y1' class='txt_fieldR' style='width:5px'  value='" + arr1[i][1] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y2' class='txt_fieldR' style='width:5px' value='" + arr1[i][2] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y3' class='txt_fieldR' style='width:5px' value='" + arr1[i][3] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y4' class='txt_fieldR' style='width:5px' value='" + arr1[i][4] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y5' class='txt_fieldR' style='width:5px' value='" + arr1[i][5] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y6' class='txt_fieldR' style='width:5px' value='" + arr1[i][6] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='sum' class='txt_fieldR' style='width:5px' value='" + arr1[i][7] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='usage' class='txt_field' style='width:5px' value='" + arr1[i][8] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='optRate' class='txt_field' style='width:5px' value='" + arr1[i][9] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeOid' class='txt_field' style='width:5px' value='" + arr1[i][10] + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeCode' class='txt_field' style='width:5px' value='" + arr1[i][11] + "'>";
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

  //선택된 의뢰처를 삭제한다.
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

  //최종사용처 검색화면 오픈하여 선택된 최종사용처를 입력한다.
  function insertLastUsingBuyer() {
	 
	window.open("/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=multi&designTeam=&invokeMethod=selectMultiCustomer", "CUSTOMEREVENT", "top=100px, left=100px, height=800px, width=1200px");
    
  }

  //선택된 최종사용처를 삭제한다.
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
    var code = fm.ProductCategoryCode.options[fm.ProductCategoryCode.selectedIndex].value;

    numCodeAjax("PRODCATEGORYCODE", code.replace("e3ps.common.code.NumberCode:", ""));
  }

  //개발검토유형 변경시 기타를 선택하면 기타사항 입력칸을 활성화한다.
  function changeDevReviewType(){
    var fm = document.form01;
    var rCode = fm.DevReviewTypeCode.options[fm.DevReviewTypeCode.selectedIndex].value;
    var rName = fm.DevReviewTypeCode.options[fm.DevReviewTypeCode.selectedIndex].text;

    // 기타
    if(rCode=="e3ps.common.code.NumberCode:21068"){
      //fm.DevReviewTypeEtc.style.visibility='visible';
    }else{
      //fm.DevReviewTypeEtc.style.visibility='hidden';
    }
  }
  
  function changeDivision(){
    var fm = document.form01;
    var rCode = fm.divisionCodeStr.options[fm.divisionCodeStr.selectedIndex].value;

    if(rCode == 'ER'){
        fm.divCode.value = 'ER';
         var hasAutoComplete = $("input[name='RepCarType']").attr("autocomplete"); 
            if ( !typeof hasAutoComplete == "undefined" ){ 
                SuggestUtil.bindDestroy('RepCarType'); //자동완성 검색 해제
            }
        $("#attrText").text("1."+"<%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>");
        $("#representText").text("<%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>");
        $("#carTypeText").text("<%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>");
        
        <%if(statusCode.equals("D")){%>
        $("#SampleSummitDateText").html("DR0(목표)일<span class='red'>*</span>");<%-- DR0(목표)일 --%>
        $("#ESIRDateText").html("<%=messageService.getString("e3ps.message.ket_message", "02823")%><span class='red'>*</span><%--초도품 제출일정--%>");
        $("#ISIRDateText").text("승인원 제출 일정");
        <%}else{%>
        $("#SampleSummitDateText").html("개발시작회의<span class='red'>*</span>");<%-- 개발시작회의 --%>
        $("#ESIRDateText").html("<%=messageService.getString("e3ps.message.ket_message", "02824")%><span class='red'>*</span><%--초도품 제출--%>");
        $("#ISIRDateText").html("승인샘플/승인원 제출<span class='red'>*</span>");
        $("#KETMPDateText").html("최종고객승인<span class='red'>*</span>");
        <%}%>
        
    }else if(rCode == 'CA'){
        SuggestUtil.bind('CARTYPE', 'RepCarType', 'RepCarTypeCode');
        fm.divCode.value = 'CA';
        $("#attrText").text("1."+"<%=messageService.getString("e3ps.message.ket_message", "02745")%><%--적용기기--%>");
        $("#representText").text("<%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%>");
        $("#carTypeText").html("<%=messageService.getString("e3ps.message.ket_message", "02745")%><span class='red'>*</span><%--차종--%>");
        $("#SampleSummitDateText").html("<%=messageService.getString("e3ps.message.ket_message", "02823")%><span class='red'>*</span><%--초도품 제출일정--%>");
        $("#ESIRDateText").text("<%=messageService.getString("e3ps.message.ket_message", "00228")%><%--ESIR일정--%>");
        $("#ISIRDateText").text("<%=messageService.getString("e3ps.message.ket_message", "00261")%><%--ISIR일정--%>");
        $("#KETMPDateText").text("KET 양산일정");
    }
    
  }

  //첨부파일입력
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

  //첨부파일삭제
  function deleteFileLine() {
    var body = document.getElementById("iFileTable");
    if (body.rows.length == 0) return;
    var file_checks = document.forms[0].iFileChk;
    if (body.rows.length == 1) {
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
  }

  //필수입력사항 체크
  function valcheck() {
    var d = document.form01;
    var divCode = d.divCode.value;
    
    if ( d.divisionCodeStr.value=="0" ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07331") %><%--사업부를 입력해주세요.--%>');
        return false;
    }
    
    if(d.ProjectNo.value == "" && d.DevelopmentStep.value=="D"){
    	alert("검토 Project No를 입력하십시오.");
    	return false;
    }
    
    if( d.RequestBuyer.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02265") %><%--의뢰처는 반드시 입력해야 합니다.--%>');
      return false;
    }

    if (d.RequestBuyerManager.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02263") %><%--의뢰처 담당자는 200자를 초과할 수 없습니다--%>');
      return false;
    }

    <%-- if(d.DivisionCode.value == "ER"){
      if( isNull(d.reception.value)) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02260") %>의뢰접수자는 반드시 입력해야 합니다');
        return false;
      }
    } --%>

    if( d.LastUsingBuyer.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02850") %><%--최종사용처는 반드시 입력해야 합니다.--%>');
      return false;
    }

    if (d.LastUsingBuyerManager.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02849") %><%--최종사용처 담당자는 200자를 초과할 수 없습니다--%>');
      return false;
    }
    var amsg = "";
    if( isNull(d.RepCarType.value) ) {
        if(divCode == "CA"){
            //대표차종은 반드시 입력해야 합니다.
            amsg = '<%=messageService.getString("e3ps.message.ket_message", "01249")%>';
        }else{
            //적용기기는 반드시 입력해야 합니다.
            amsg = '<%=messageService.getString("e3ps.message.ket_message", "03392")%>';
        }
        alert(amsg);
        return false;
    }
    
    if(d.DevelopmentStep.value=="D" && divCode == "CA"){
    	var carEventDate_ = "";
    	$.ajax({
            type: 'get',
            async: false,
            cache: false,
            dataType: "json",
            url: '/plm/ext/project/program/findEventByCarType.do?carTypeOid='+d.RepCarTypeCode.value,
            success: function (data) {
                var map;
                $.each(data.Body, function(){
                    map = this;
                    $.each(map, function(){
                        if(this.carEventName == 'SOP'){
                        	carEventDate_ = this.carEventDate;
                        }
                    });
                });
            },
            fail : function(){
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
                ret = "E";
            }
        });
    	
    	if( isNull(carEventDate_) ) {
    		var internalManager = '<%=internalManager%>';
    		var outsideManager = '<%=outsideManager%>';
    		alert(d.RepCarType.value+" 차종의 SOP 일정이 없습니다.\r\n먼저 일정등록을 요청하십시오.\r\n\r\n"+"국내차종담당자 : "+internalManager+"\r\n국외차종담당자 : "+outsideManager);
    		return false;
    	}
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
      
      if(divCode == "ER"){
    	  
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
        if(divCode == "ER"){
            
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
      if ($(t_checks).length > 0){
        for (var i = 0; i < t_checks.length; i++) {
          if ( isNull(d.partName[i].value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02610") %><%--제품정보는 반드시 입력해야 합니다.--%>');
            return false;
          }else{
            if (d.partName[i].value.length > 200){
                            alert('<%=messageService.getString("e3ps.message.ket_message", "02613") %><%--제품정보의 제품명은 200자를 초과할 수 없습니다--%>');
              return false;
            }
          }

          if ( isNull(d.carTypes[i].value) ) {
              if(divCode == "CA"){
                  //대표차종은 반드시 입력해야 합니다.
                  amsg = '<%=messageService.getString("e3ps.message.ket_message", "02616")%>';
              }else{
                  //적용기기는 반드시 입력해야 합니다.
                  amsg = '<%=messageService.getString("e3ps.message.ket_message", "03395")%>';
              }
              alert(amsg);
              return false;
          }

          if (d.appliedRegion[i].value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02468") %>');//<%--적용부위는 200자를 초과할 수 없습니다--%>');
            return false;
          }

          if (d.currentApplyPart[i].value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "03209") %>');//<%--현적용품은 200자를 초과할 수 없습니다--%>');
            return false;
          }

          if ( d.PackTypeCode[i].value == "0" ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02618") %>');//<%--제품정보의 포장사양은 반드시 입력해야 합니다.--%>');
            return false;
          }

          <%-- if ( isNull(d.buyerAcceptPrice[i].value) ) {
          }else{
            if (isNotDigit(d.buyerAcceptPrice[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "00848") %>');//고객예상가는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.ketTargetPrice[i].value) ) {
          }else{
            if (isNotDigit(d.ketTargetPrice[i].value)) {
                          alert('<%=messageService.getString("e3ps.message.ket_message", "02969") %>');//판매목표가는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.targetEarningRate[i].value) ) {
          }else{
            if (isNotDigit(d.targetEarningRate[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01391") %>');//목표수익율는 반드시 숫자여야 합니다');
              return false;
            }
          }

          if ( isNull(d.targetInvestmentCost[i].value) ) {
          }else{
            if (isNotDigit(d.targetInvestmentCost[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "01393") %>');//목표투자비는 반드시 숫자여야 합니다');
              return false;
            }
          } --%>
        }
      }else{
        if ( isNull(d.partName.value) ) {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02614") %>');//<%--제품정보의 제품명은 반드시 입력해야 합니다--%>');
          return false;
        }else{
          if (d.partName.value.length > 200){
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02613") %>');//<%--제품정보의 제품명은 200자를 초과할 수 없습니다--%>');
            return false;
          }
        }

        if ( isNull(d.carTypes.value) ) {
            if(divCode == "CA"){
                //대표차종은 반드시 입력해야 합니다.
                amsg = '<%=messageService.getString("e3ps.message.ket_message", "02616")%>';
            }else{
                //적용기기는 반드시 입력해야 합니다.
                amsg = '<%=messageService.getString("e3ps.message.ket_message", "03395")%>';
            }
            alert(amsg);
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


        <%-- if ( isNull(d.buyerAcceptPrice.value) ) {
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
            alert('<%=messageService.getString("e3ps.message.ket_message", "01391") %>목표수익율는 반드시 숫자여야 합니다');
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
      alert('<%=messageService.getString("e3ps.message.ket_message", "01148") %><%--기타내용은 100자를 초과할 수 없습니다--%>');
      return false;
    }

   <%--  if (d.TabSize.value.length > 200){
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
    } --%>

    if (d.Outlook.value.length > 200){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03203") %><%--향후전망은 200자를 초과할 수 없습니다--%>');
      return false;
    }

    <%-- if (d.MoldDepreciationTypeEtcInfo.value.length > 200){
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

  //제품타입 문자열생성
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

  function doCancel(){
    if(confirm('<%=messageService.getString("e3ps.message.ket_message", "00604") %><%--개발(검토)의뢰서 등록을 취소하시겠습니까?--%>')){
      //document.location.href="/plm/jsp/dms/SearchDevRequest.jsp";
      window.close();
    }
  }

  //기검토의뢰번호 선택시 의뢰번호 입력 및 데이터 호출
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
  
  function selDr(arr){
      document.location.href="/plm/jsp/dms/CreateDevRequest.jsp?oid=" + arr[0];
  }


  //기검토의뢰번호 선택시 의뢰번호 입력 및 데이터 호출
  function setProject(arr){

    var trArr;
    trArr = arr[0];
    var d = document.forms[0];
    d.ProjectOID.value = trArr[0];
    d.ProjectNo.value = trArr[1];

    document.location.href="/plm/jsp/dms/CreateDevRequest.jsp?oid=" + trArr[0];
  }
  
  function selProjectCallBack(returnValue){
	  var d = document.forms[0];
	  
	  if(returnValue == null) {
	        return;
	  }
	  
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

  //기검토프로젝트 선택시 프로젝트번호 입력
  function selProjectNo(){
    var d = document.forms[0];
    if(d.DevelopmentStep.value=="R"){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00630") %><%--개발단계에서만 기검토 프로젝트를 입력할 수 있습니다--%>');
    }else{
      var url="/plm/jsp/project/SearchPjtPop.jsp?status=C&type=D&mode=m&modal=N&paramRadio=1&fncall=selProjectCallBack";
      window.open(url, "SearchPjtPop", "top=100px, left=100px, height=768px, width=1024px");

      //copyReviewPjt(trArr[0])
      
    }
  }

  //기검토프로젝트 선택 시 정보 copy
  function copyReviewPjt(param){
    var url = "/plm/jsp/dms/CopyPjtAjaxAction.jsp?pjtOid=" + param;
    callServer(url, setReviewPjt);
  }

  //기검토프로젝트 정보 setting
  function setReviewPjt(req){
	return;
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//data_info");

    //개발제품명 setting
    var l_pjtName = showElements[0].getElementsByTagName("l_pjtName");
    document.forms[0].DevProductName.value = l_pjtName[0].text;

    //최종사용처 setting
    var l_endName = showElements[0].getElementsByTagName("l_endName");
    var l_endOid = showElements[0].getElementsByTagName("l_endOid");

    var LastUsingBuyer = document.forms[0].LastUsingBuyer;
    var flag = false;
    var listLength;

    for(var i = 0; i < l_endOid.length; i++) {
      flag = false;

      for(var j = 0; j < LastUsingBuyer.length; j++){
        if(l_endOid[i].text == LastUsingBuyer.options[j].value){
          flag = true;
        }
      }

      if(!flag){
        listLength = document.forms[0].LastUsingBuyer.length;
        document.forms[0].LastUsingBuyer.length += 1;
        LastUsingBuyer.options[listLength].text = l_endName[i].text;
        LastUsingBuyer.options[listLength].value = l_endOid[i].text;
      }
    }

    //의뢰처 setting
    var l_reqName = showElements[0].getElementsByTagName("l_reqName");
    var l_reqOid = showElements[0].getElementsByTagName("l_reqOid");

    var RequestBuyer = document.forms[0].RequestBuyer;

    for(var i = 0; i < l_reqOid.length; i++) {
      flag = false;

      for(var j = 0; j < RequestBuyer.length; j++){
        if(l_reqOid[i].text == RequestBuyer.options[j].value){
          flag = true;
        }
      }

      if(!flag){
        listLength = document.forms[0].RequestBuyer.length;
        document.forms[0].RequestBuyer.length += 1;
        RequestBuyer.options[listLength].text = l_reqName[i].text;
        RequestBuyer.options[listLength].value = l_reqOid[i].text;
      }
    }

    //제품정보 setting
    var l_pOid = showElements[0].getElementsByTagName("l_pOid");
    var l_productName = showElements[0].getElementsByTagName("l_productName");
    var l_carName = showElements[0].getElementsByTagName("l_carName");
    var l_productArea = showElements[0].getElementsByTagName("l_productArea");
    var l_us = showElements[0].getElementsByTagName("l_us");
    var l_t1 = showElements[0].getElementsByTagName("l_t1");
    var l_t2 = showElements[0].getElementsByTagName("l_t2");
    var l_t3 = showElements[0].getElementsByTagName("l_t3");
    var l_t4 = showElements[0].getElementsByTagName("l_t4");
    var l_t5 = showElements[0].getElementsByTagName("l_t5");
    var l_t6 = showElements[0].getElementsByTagName("l_t6");
    var l_tsum = showElements[0].getElementsByTagName("l_tsum");
    var l_price = showElements[0].getElementsByTagName("l_price");
    var l_cost = showElements[0].getElementsByTagName("l_cost");
    var l_profit = showElements[0].getElementsByTagName("l_profit");
    var l_mpOid = showElements[0].getElementsByTagName("l_mpOid");
    var l_mOid = showElements[0].getElementsByTagName("l_mOid");
    var l_mName = showElements[0].getElementsByTagName("l_mName");
    var l_y1 = showElements[0].getElementsByTagName("l_y1");
    var l_y2 = showElements[0].getElementsByTagName("l_y2");
    var l_y3 = showElements[0].getElementsByTagName("l_y3");
    var l_y4 = showElements[0].getElementsByTagName("l_y4");
    var l_y5 = showElements[0].getElementsByTagName("l_y5");
    var l_y6 = showElements[0].getElementsByTagName("l_y6");
    var l_sum = showElements[0].getElementsByTagName("l_sum");
    var l_usage = showElements[0].getElementsByTagName("l_usage");
    var l_option = showElements[0].getElementsByTagName("l_option");

    var tBody = document.getElementById("partTable");
    var innerRow;
    var innerCell;

    for(var i = 0; i < l_pOid.length; i++) {
      innerRow = tBody.insertRow();
      innerRow.onmouseover=function(){fncOnMouseOver(this.rowIndex)};

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 40;
      innerCell.innerHTML = "<input type='checkbox' name='iPartChk' id='iPartChk' >"
                            + "<input type='hidden' name='tId' id='tId' value='" + l_pOid[i].text + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 150;
      innerCell.innerHTML = "<input type='text' class='txt_field' style='width:140px' name='partName' value='" + l_productName[i].text + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 120;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRO' style='width:90px' name='carTypes' value='" + l_carName[i].text + "' >"
                            + "&nbsp;<a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 80;
      innerCell.innerHTML = "<input type='text' class='txt_field' style='width:75px' name='appliedRegion' value='" + l_productArea[i].text + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 60;
      innerCell.innerHTML = "<input type='text' class='txt_field' style='width:50px' name='currentApplyPart'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='applyedUsage' value='" + money_format(l_us[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount1' value='" + money_format(l_t1[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount2' value='" + money_format(l_t2[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount3' value='" + money_format(l_t3[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount4' value='" + money_format(l_t4[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount5' value='" + money_format(l_t5[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 50;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='yearAmount6' value='" + money_format(l_t6[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 60;
      innerCell.innerHTML = "<input type='text' readonly class='txt_fieldRRO' style='width:40px' name='sumAmount' value='" + money_format(l_tsum[i].text) + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 70;
      innerCell.innerHTML = "<select name='PackTypeCode' class='fm_jmp' style='width:60px'><option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>" + "<%=packArrStr%>" + "</select>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 105;
      innerCell.innerHTML = "<input type='hidden' name='summitDestinationOid'><input type='text' readonly class='txt_fieldRO' style='width:62px' name='summitDestination'>&nbsp;<img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'><a href='javascript:deleteCustom(this)'><img src='../../portal/images/icon_delete.gif' border='0'></a>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 105;
      innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='buyerAcceptPrice' value='" + l_price[i].text + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 105;
      innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='ketTargetPrice' value='" + l_cost[i].text + "' >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 70;
      innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:60px' name='targetEarningRate' value='" + l_profit[i].text + "' >";
      
      <%if(statusCode.equals("D")) {%>
      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 70;
      innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:60px' name='targetAverageRate'>";
      <%} %>
      

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.width = 105;
      innerCell.innerHTML = "<input type='text' class='txt_fieldR' style='width:95px' name='targetInvestmentCost'>";
    }

    tBody = document.getElementById("iCarTable");

    for(var i = 0; i < l_mOid.length; i++) {
      innerRow = tBody.insertRow();

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='tId1' class='txt_field' style='width:5px'  value='" + l_mpOid[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y1' class='txt_fieldR' style='width:5px'  value='" + l_y1[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y2' class='txt_fieldR' style='width:5px' value='" + l_y2[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y3' class='txt_fieldR' style='width:5px' value='" + l_y3[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y4' class='txt_fieldR' style='width:5px' value='" + l_y4[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y5' class='txt_fieldR' style='width:5px' value='" + l_y5[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='y6' class='txt_fieldR' style='width:5px' value='" + l_y6[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='sum' class='txt_fieldR' style='width:5px' value='" + l_sum[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='usage' class='txt_field' style='width:5px' value='" + l_usage[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='optRate' class='txt_field' style='width:5px' value='" + l_option[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeOid' class='txt_field' style='width:5px' value='" + l_mOid[i].text + "'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM"
      innerCell.innerHTML = "<input type='hidden' readonly name='carTypeCode' class='txt_field' style='width:5px' value='" + l_mName[i].text + "'>";
    }

  }

  //기검토프로젝트 프로젝트번호 삭제
  function delProjectNo(){
    var d = document.forms[0];
    d.ProjectNo.value = "";
    d.ProjectOID.value = "";
    flashEventClose();
  }

  //고객 SOP는 년월만 나오도록
  function ProductSaleFirstYearMnth(){
    var d = document.forms[0];
    var sDate=d.ProductSaleFirstYear.value;
    d.ProductSaleFirstYear.value = sDate.substring(0,7);
  }

  //의뢰접수자선택
  function selectUser() {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }
    acceptMember(attacheMembers);
  }

  //의뢰접수자입력
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

  //저장
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

    function save() {
        var d = document.forms[0];

        var fbody = document.getElementById("iFileTableOld");

        /* if (fbody.rows.length > 0) {
            if (fbody.rows.length == 1) {
                if (d.ContentOid == "[object]") {
                    d.fileContents.value = d.fileContents.value + "/"
                            + d.ContentOid.value;
                }
            } else {
                d.fileContents.value = "";
                for ( var i = 0; i < fbody.rows.length; i++) {
                    if (d.ContentOid[i] == "[object]") {
                        d.fileContents.value = d.fileContents.value + "/"
                                + d.ContentOid[i].value;
                    }
                }
            }
        } */

        var buystr = "";
        for ( var i = 0; i < d.RequestBuyer.length; i++) {
            buystr = buystr + "," + d.RequestBuyer.options[i].value;
        }
        d.RequestBuyerStr.value = buystr.substring(1);

        buystr = "";
        for ( var i = 0; i < d.LastUsingBuyer.length; i++) {
            buystr = buystr + "," + d.LastUsingBuyer.options[i].value;
        }
        d.LastUsingBuyerStr.value = buystr.substring(1);

        d.ProductTypeCodes.value = productTypes();

        d.encoding = 'multipart/form-data';
        d.cmd.value = 'create';
        d.action = "/plm/servlet/e3ps/DevRequestServlet?cmd=create";
        showProcessing();
        disabledAllBtn();
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
    
    function selectPartClazPopUp(){
    	
        var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=false&onlyLeaf=Y&openAll=N&depthLevel2=Y&callBackFn=selectPartCategory&modal=n";
        
    	window.open(url, "selectPartCategory", "top=100px, left=100px, height=700px, width=700px");
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
    
    function selectProjectAfterFunc( objArr )
    {
        var goFlag = true;
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];
            
            $('[name=relatedSalesProjectOid]').val(trArr[0]);
            $('[name=relatedSalesProject]').val(trArr[1]);
            /* if(trArr[3] == '승인완료' && trArr[4] == '성공'){
                $('[name=relatedSalesProjectOid]').val(trArr[0]);
                $('[name=relatedSalesProject]').val(trArr[1]);
            }else{
                goFlag = false;
            } */
            
        }
        
        /* if(!goFlag){
            alert("완료된 프로젝트만 선택 할 수있습니다.");
        } */
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
        
        <%if("CA".equals(divCode)){%>
        SuggestUtil.bind('CARTYPE', 'RepCarType', 'RepCarTypeCode');
        <%}%>
        SuggestUtil.bind('USER', 'receptionName','reception');
    });
//-->
</script>
<script id='dummy'></script>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name=form01 method="post">
        <input type="hidden" name="cmd" value="create"> 
        <input type="hidden" name="currPartLine" value=0> 
        <input type="hidden" name="DivisionCode" value="<%=CommonUtil.isMember("전자사업부")?"ER":"CA"%>">
        <input type="hidden" name="DevelopmentStep" value="<%=statusCode%>">
        <input type="hidden" name="ProjectOID" value="<%=oid%>" id="ProjectOID">
        <input type="hidden" name="RequestBuyerStr" value="">
        <input type="hidden" name="LastUsingBuyerStr" value="">
        <input type="hidden" name="ProductTypeCode" value="0">
        <input type="hidden" name="ProductTypeCodes" value="0">
        <input type="hidden" name="fileContents" value="0">
        <input type="hidden" name="divCode" value="<%=divCode%>">
        <table style="width: 100%;">
            <tr>
                <td valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=status%> <%=messageService.getString("e3ps.message.ket_message", "01310")%><%--등록--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="176" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="../../portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                        href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                        href="javascript:doCancel()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248")%><%--의뢰담당자--%></td>
                            <td class="tdwhiteL0"><%=userName%>/<%=deptName%>&nbsp;
                            
                            <td width="130" class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "03463")%><%--검토담당 사업부--%><span class="red">*</span>
                            </td>
                            <td class="tdwhiteL0">
                                <select name="divisionCodeStr" class="fm_jmp" style="width: 247px" onchange="javascript:changeDivision();">
                                    <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DIVISIONTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for ( int i=0; i<numCode.size(); i++ ) {
                                            if(numCode.get(i).get("value").equals("KETS") ){
                                               continue;
                                            }
                                            //if ( divCode.equals(numCode.get(i).get("code")) ) {
                                           %>
                                           <!--  <option value="<%=numCode.get(i).get("code") %>" selected="selected"><%=numCode.get(i).get("value")%></option> --> 
                                           <%
                                            //}else {
                                           %>
                                           <option value="<%=numCode.get(i).get("code") %>"><%=numCode.get(i).get("value")%></option>     
                                           <%    
                                            //}
                                           }
                                           %>
                                    %>
                            </select>
                            </td>
                         </tr>
                            <%-- <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248")%>의뢰담당자</td>
                            <td class="tdwhiteL"><%=userName%>/<%=deptName%>&nbsp;
                            <%
                                if ("전장모듈".equals(deptName.substring(0, 4))) {
                            %><input type="checkbox" value="1" name="attribute1"><%=messageService.getString("e3ps.message.ket_message", "02484")%>전장모듈사업부
                            <%
                                } else {
                            %><input type="hidden" value="0" name="attribute1"> <%
                                }
                            %>
                            </td>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02259")%>의뢰접수자
                                <span class="red">*</span></td>
                            <td class="tdwhiteL0">
                                <input type="text" name="receptionName" class='txt_field' style="width: 210" value="<%=receptionName%>">
                                <input type="hidden" name="reception" value="<%=reception%>">
                                <a href="javascript:selectUser()"><img src="../../portal/images/icon_user.gif" border="0"></a>
                                <a href="JavaScript:deleteValue2('reception','receptionName')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
                            <%
                                }
                            %> --%>
                       
                        <tr>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%></td>
                            <td class="tdwhiteL" width="290"><%=status%></td>
                            <%
                                if (statusCode.equals("D")) {
                            %>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00736")%><%--검토프로젝트번호--%><span class="red">*</span></td>
                            <td class="tdwhiteL0"><input type="text" readonly name="ProjectNo" class='txt_fieldRO' style="width: 80%" id="ProjectNo"> &nbsp;<a href="javascript:selProjectNo()"><img
                                    src="../../portal/images/icon_5.png" border="0" align="middle"></a>&nbsp;&nbsp;<a
                                href="javascript:delProjectNo()"><img src="../../portal/images/icon_delete.gif" border="0"
                                    align="middle"></a> <span id="checkComment" name="checkComment" style="display : none"><font color="red"><b>※사업부장 승인 필요</b></font></span></td>
                            <%
                                } else {
                            %>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01114")%><%--기검토 의뢰번호--%></td>
                            <td class="tdwhiteL0"><input type="text" readonly name="ProjectNo" class='txt_fieldRO'
                                style="width: 80%" id="textfield3" value="<%=reqNo%>"> &nbsp;<a href="javascript:selectDevRequest()"><img
                                    src="../../portal/images/icon_5.png" border="0" align="middle"></a>&nbsp;&nbsp;<a
                                href="javascript:delProjectNo()"><img src="../../portal/images/icon_delete.gif" border="0"
                                    align="middle"></a></td>
                            <%
                                }
                            %>
                        </tr>
                        <tr>
                            <td width="130" class="tdblueL" valign=middle><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--접점고객--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL">
                                <select name="RequestBuyer" style="width: 80%;hiehgt:50px;!importants" size=3 multiple>
                                    <%
                                        if (!StringUtil.checkNull(requestBuyer).equals("")) {
                                            StringTokenizer st = new StringTokenizer(requestBuyer, ",");
                                            String ct;
                                            while (st.hasMoreTokens()) {
                                                ct = st.nextToken();
                                                NumberCode nCode = (NumberCode) CommonUtil.getObject(ct);
                                                String bName = "";
                                                if (nCode == null) {
                                                bName = ct;
                                                } else {
                                                bName = nCode.getName();
                                                }
                                    %>
                                    <option value="<%=ct%>"><%=bName%></option>
                                    <%
                                        }
                                        }
                                    %>
                            </select>
                            <a href="javascript:insertRequestBuyer();"><img src="../../portal/images/icon_5.png" border="0"align="top"></a>
                            <a href="javascript:deleteRequestBuyer();"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                            <td width="130" class="tdblueL" valign=middle><%=messageService.getString("e3ps.message.ket_message", "00859")%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01205")%><%--접점고객 담당자--%></td>
                            <td class="tdwhiteL0" valign=middle><input type="text" name="RequestBuyerManager"
                                class='txt_field' style="width: 100%;" id="textfield" value="<%=requestBuyerManager%>"></td>
                        </tr>
                        <tr>
                            <td width="130" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "02847")%><%--최종사용처--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL"><select name="LastUsingBuyer" style="width: 80%;" size=3 multiple>
                                    <%
                                        if (!StringUtil.checkNull(lastUsingBuyer).equals("")) {
                                            StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ",");
                                            String ct;
                                            while (st1.hasMoreTokens()) {
                                                ct = st1.nextToken();
                                                NumberCode nCode = (NumberCode) CommonUtil.getObject(ct);
                                                String bName = "";
                                                if (nCode == null) {
                                                bName = ct;
                                                } else {
                                                bName = nCode.getName();
                                                }
                                    %>
                                    <option value="<%=ct%>"><%=bName%></option>
                                    <%
                                        }
                                        }
                                    %>
                            </select>
                            <a href="javascript:insertLastUsingBuyer()"><img src="../../portal/images/icon_5.png" border="0" align="top"></a>
                            <a href="javascript:deleteLastUsingBuyer()"><img src="../../portal/images/icon_delete.gif" border="0" align="top"></a></td>
                            <td width="130" class="tdblueL" valign=middle><%=messageService.getString("e3ps.message.ket_message", "02848")%><%--최종사용처 담당자--%></td>
                            <td class="tdwhiteL0" valign=middle><input type="text" name="LastUsingBuyerManager"
                                class='txt_field' style="width: 100%;" id="textfield2" value="<%=lastUsingBuyerManager%>"></td>
                        </tr>
                        <tr>
                            <td id="representText" width="130" class="tdblueL">
                                <%
                                    if (divCode.equals("CA")) {
                                %><%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%> <%
                                    } else {
                                %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                                    }
                                %><span class="red">*</span>
                            </td>
                            <td class="tdwhiteL">
                                <input type="text" name="RepCarType" class='txt_field' style="width: 79%" id="textfield4" value="<%=repCarType%>">
                                <input type="hidden" id="RepCarTypeCode" name="RepCarTypeCode" value="">
                                <a href="javascript:selectCarType();"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                <a href="javascript:CommonUtil.deleteValue('RepCarTypeCode','RepCarType');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td> 
                            </td>
                            <%
                                if (statusCode.equals("R")) {
                            %>

                            <td width="130" class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "00616")%><%--개발검토유형--%><span class="red">*</span>
                            </td>
                            <td class="tdwhiteL0">
                                <select name="DevReviewTypeCode" class="fm_jmp" style="width: 247px"
                                onchange="javascript:changeDevReviewType();">
                                    <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        NumberCode nCode1 = null;
                                        int iidx = 0;
                                        String devReviewName = "";

                                        // 개발검토유형
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVREVIEWTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {

                                            if (devReviewTypeCode.equals(numCode.get(i).get("oid").toString()))
                                                devReviewName = numCode.get(i).get("code").toString();
                                    %>
                                    <option value="<%=numCode.get(i).get("oid")%>"
                                        <%if (numCode.get(i).get("oid").equals(devReviewTypeCode)) {%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02527")%><%--제안서 제출일정--%>
                                <span class="red">*</span></td>
                            <td class="tdwhiteL">
                             <%
                                 if (isProposalSubmit.equals("1")) {
                             %> <input name="IsProposalSubmit" type="radio" class="Checkbox" checked value="1" onClick="javascript:bindCalendarField('ProposalSubmitDate')">
                                    <%=messageService.getString("e3ps.message.ket_message", "03134")%><%--필요--%> <input
                                    name="IsProposalSubmit" type="radio" class="Checkbox" value="2"
                                    onClick="javascript:unbindCalendarField('ProposalSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "01626")%><%--불필요--%>
                                    <%
                                        } else {
                                    %> <input name="IsProposalSubmit" type="radio" class="Checkbox" value="1"
                                    onClick="javascript:bindCalendarField('ProposalSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "03134")%><%--필요--%>
                                    <input name="IsProposalSubmit" type="radio" class="Checkbox" checked value="2"
                                    onClick="javascript:unbindCalendarField('ProposalSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "01626")%><%--불필요--%>
                                    <%
                                        }
                                    %> <input type="text" name="ProposalSubmitDate" class='txt_fieldRO' readonly style="width: 80"
                                    id="ProposalSubmitDate" value="<%=proposalSubmitDate%>"><img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ProposalSubmitDate');" style="cursor: hand;"></td>

                            <%
                                }
                            %>

                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00650")%><%--개발원가 제출일정--%>
                                <%
                                    if (statusCode.equals("R")) {
                                %><span class="red">*</span> <%
                                    }
                                %></td>
                            <td class="tdwhiteL0">
                                <%
                                    if (isCostSubmit.equals("1")) {
                                %> <input name="IsCostSubmit" type="radio" checked class="Checkbox" value="1"
                                onClick="javascript:bindCalendarField('CostSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "03134")%><%--필요--%>
                                <input name="IsCostSubmit" type="radio" class="Checkbox" value="2"
                                onClick="javascript:unbindCalendarField('CostSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "01626")%><%--불필요--%>
                                <%
                                    } else {
                                %> <input name="IsCostSubmit" type="radio" class="Checkbox" value="1"
                                onClick="javascript:bindCalendarField('CostSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "03134")%><%--필요--%>
                                <input name="IsCostSubmit" type="radio" checked class="Checkbox" value="2"
                                onClick="javascript:unbindCalendarField('CostSubmitDate')"> <%=messageService.getString("e3ps.message.ket_message", "01626")%><%--불필요--%>
                                <%
                                    }
                                %> <input type="text" name="CostSubmitDate" readonly class='txt_fieldRO' style="width: 95" id="textfield5"
                                value="<%=costSubmitDate%>"><img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('CostSubmitDate');" style="cursor: hand;">
                            </td>
                        </tr>
                        <%
                        if (statusCode.equals("R") && (CommonUtil.isMember("자동차사업부_영업") || CommonUtil.isAdmin() )) {
                        %>
                        <tr>
                            <td width="130" class="tdblueL">영업프로젝트번호</td>
                                <td class="tdwhiteL0" colspan='3'>
                                <input type="text" readonly name="relatedSalesProject" class='txt_fieldRO' style="width: 30%" id="textfield3" value=""> &nbsp;
                                <input type="hidden" name="relatedSalesProjectOid"">
                                <a href="javascript:SearchUtil.selectOneSalesProject('selectProjectAfterFunc');">
                                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:CommonUtil.deleteValue('relatedSalesProject','relatedSalesProjectOid');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                        <tr>
                            <td width="130" class="tdblueL">
                                <%
                                    if (statusCode.equals("D")) {
                                %><%=messageService.getString("e3ps.message.ket_message", "00664")%><%--개발제품명--%> <%
                                    } else {
                                %><%=messageService.getString("e3ps.message.ket_message", "00623")%><%--개발검토제품명--%> <%
                                    }
                                %><span class="red">*</span>
                            </td>
                            <td colspan="3" class="tdwhiteL0"><input type="text" name="DevProductName" class='txt_field'
                                style="width: 100%" id="DevProductName" value="<%=devProductName%>"></td>
                        </tr>
                        <%
                            if (statusCode.equals("D")) {
                        %>
                        <tr>
                            <td width="130" class="tdblueL">
                                <%=messageService.getString("e3ps.message.ket_message", "00653")%><%--개발유형--%><span class="red">*</span>
                            </td>
                            <td colspan="3" class="tdwhiteL0"><select name="DevReviewTypeCode" class="fm_jmp" style="width: 247px"
                                onchange="javascript:changeDevReviewType();">
                                    <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        NumberCode nCode1 = null;
                                        int iidx = 0;
                                        String devReviewName = "";

                                        // 개발검토유형
                                        parameter.clear();
                                        parameter.put("locale", messageService.getLocale());
                                        parameter.put("codeType", "DEVREVIEWTYPE");
                                        numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                                        for (int i = 0; i < numCode.size(); i++) {

                                            if (devReviewTypeCode.equals(numCode.get(i).get("oid").toString()))
                                                devReviewName = numCode.get(i).get("code").toString();
                                    %>
                                    <option value="<%=numCode.get(i).get("oid")%>"
                                        <%if (numCode.get(i).get("oid").equals(devReviewTypeCode)) {%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                                    <%
                                        }
                                    %>
                            </select>&nbsp;<input type="text" id="DevReviewTypeEtc" name="DevReviewTypeEtc" class='txt_field'
                                style="width:412; margin: 0px 0px 0px 1px; <%if (!devReviewName.equals("DRT9000")) {%> visibility:hidden <%}%>"
                                id="DevReviewTypeEtc" value="<%=devReviewTypeEtc%>"></td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td width="130" style="height: 50" class="tdblueL">
                                <%
                                    if (statusCode.equals("D")) {
                                %><%=messageService.getString("e3ps.message.ket_message", "00668")%><%--개발착수내용--%> <%
                                    } else {
                                %><%=messageService.getString("e3ps.message.ket_message", "00612")%><%--개발검토내용--%> <%
                                    }
                                %>
                            </td>
                            <td colspan="3" style="height: 50" class="tdwhiteL0">
                                <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
                                    <tr>
                                        <td id="attrText" width="90" class="tdgrayL">1.<%
                                        if (divCode.equals("CA")) {
                                        %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%> <%
                                            } else {
                                        %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                                            }
                                        %></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute2" style="width:99%" class='txt_field' value="<%=attribute2%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">2.<%=messageService.getString("e3ps.message.ket_message", "02467") %><!-- 적용부위 --></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute3" style="width:99%" class='txt_field' value="<%=attribute3%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">3.<%=messageService.getString("e3ps.message.ket_message", "09362") %><!-- 개발부품 --></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute4" style="width:99%" class='txt_field' value="<%=attribute4%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">4.<%=messageService.getString("e3ps.message.ket_message", "09363") %><!-- 개발일정 --></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute5" style="width:99%" class='txt_field' value="<%=attribute5%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">5.<%=messageService.getString("e3ps.message.ket_message", "09364") %><!-- 양산투자비 --></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute6" style="width:99%" class='txt_field' value="<%=attribute6%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td  class="tdgrayL">6.<%=messageService.getString("e3ps.message.ket_message", "01168") %><!-- 납입처 --></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute7" style="width:99%" class='txt_field' value="<%=attribute7%>" maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">7.<%=messageService.getString("e3ps.message.ket_message", "09365") %><!-- 예상매출액 --></td>
                                        <td width="*" style="padding-left:2px"><textarea name="attribute8" style="width:99%; height:50px" cols="3" rows="2" class='txt_field'><%=attribute8%></textarea></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">8.<%=messageService.getString("e3ps.message.ket_message", "03202")%><%--향후전망--%></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="Outlook" value="<%=outlook%>" style="width:99%" class='txt_field' maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">9.<%=messageService.getString("e3ps.message.ket_message", "09475")%><%--경쟁사 정보--%></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute10" value="<%=attribute10%>" style="width:99%" class='txt_field' maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">10.<%=messageService.getString("e3ps.message.ket_message", "09476")%><%--KET 단독입찰여부--%></td>
                                        <td width="*" style="padding-left:2px"><input type="text" name="attribute11" value="<%=attribute11%>" style="width:99%" class='txt_field' maxlength="1000"></td>
                                    </tr>
                                    <tr>
                                        <td class="tdgrayL">11.<%=messageService.getString("e3ps.message.ket_message", "01136") %><!-- 기타 --></td>
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="../../portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02874")%><%--추가요청사항 및 첨부Data--%></td>
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
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="130" style="height: 100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02873")%><%--추가요청사항--%></td>
                            <td class="tdwhiteL0"><textarea name="SalesAdditionalRequirement"
                                    class='txt_field' id="SalesAdditionalRequirement" style="width: 100%; height: 40px"><%=salesAdditionalRequirement%></textarea></td>
                        </tr>
                        <tr>
                            <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%><span class="red">*</span></td>
                            <td class="tdwhiteM0">
                                <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                    <table width="100%" cellpadding="0" cellspacing="0">
                                        <tr class="headerLock3">
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                                    <tr>
                                                        <td width="20" class="tdgrayM">
                                                            <a href="#" onclick="javascript:insertFileLine();return false;"><img src="/plm/portal/images/b-plus.png"></a></td>
                                                        <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><!-- 파일명 -->(※ <%=messageService.getString("e3ps.message.ket_message", "03252")%><%--회의록, 주변Data, Concept view 관련 파일 유첨바랍니다.--%>)</td>
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
                                        if (dr != null) {
                                            if (dr instanceof FormatContentHolder) {
                                                FormatContentHolder holder = (FormatContentHolder) dr;
                                                Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
                                                if (secondaryFiles.size() > 0) {

                                                for (int i = 0; i < secondaryFiles.size(); i++) {
                                                    ContentInfo info = (ContentInfo) secondaryFiles.elementAt(i);

                                                    if (info == null) {
                                                    iconpath = "";
                                                    urlpath = "";
                                                    } else {
                                                    ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
                                                    appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                                    //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + CommonUtil.getOIDString(holder) + "&adOid=" + appDataOid;
                                                    urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                                    urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                                    iconpath = info.getIconURLStr();

                                                    }
                                    %>
                                                    <tr>
                                                        <td class="tdwhiteL0">
                                                            <input name='ContentOid' class='txt_fieldRO' id='ContentOid' type='hidden' value='<%=info.getContentOid()%>'>
                                                            <%=iconpath%>&nbsp;<%=urlpath%>
                                                        </td>
                                                    </tr>
                                    <%
                                        }
                                                }
                                            }
                                        }
                                    %>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tbody id="iFileTable" />
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="../../portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                        href="javascript:insertPartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                        href="javascript:deletePartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                        href="javascript:copyPartLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00128")%><%--Copy 등록--%></a></td>
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
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td>
                                <div style='overflow:auto;width:991px;height:195;'>
                                    <table id="partTable" width="1420px" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
                                        <tr>
                                            <td width="40px" rowspan="2" class="tdblueM">&nbsp;</td>
                                            <td width="150px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02591")%><%--제품명--%>
                                                <span class="red">*</span></td>
                                            <td id="carTypeText" width="120px" rowspan="2" class="tdblueM">
                                                <%
                                                    if (divCode.equals("CA")) {
                                                %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%> <%
                                                    } else {
                                                %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                                                    }
                                                %><span class="red">*</span>
                                            </td>
                                            <td width="80px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02467")%><%--적용부위--%></td>
                                            <td width="60px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03208")%><%--현적용품--%></td>
                                            <td width="50px" rowspan="2" class="tdblueM">U/S</td>
                                            <td colspan="7" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02154")%><%--예상수량(천개/년)--%></td>
                                            <td width="70px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02991")%><%--포장사양--%><span class="red">*</span></td>
                                            <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01168")%><%--납입처--%></td>
                                            <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00847")%><%--고객예상가(원)--%></td>
                                            <td width="105px" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02968")%><%--판매목표가(원)--%><span class="red">*</span></td>
                                            <td width="100px" colspan="<%=statusCode.equals("D") ? 2 : 1 %>" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01390")%><%--목표수익률(%)--%></td>
                                            <td width="50px" rowspan="2" class="tdblueM">기간<br>수익률</td>
                                            <td width="105px" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01392")%><%--목표투자비(천원)--%></td>
                                        </tr>
                                        <tr>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 1)%><%--{0}년차--%></td>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 2)%><%--{0}년차--%></td>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 3)%><%--{0}년차--%></td>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 4)%><%--{0}년차--%></td>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 5)%><%--{0}년차--%></td>
                                            <td width="50px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 6)%><%--{0}년차--%></td>
                                            <td width="60px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03151")%><%--합계--%></td>
                                            <td width="100" class="tdblueM">초도</td>
                                            <%if(statusCode.equals("D")){ %>                                            
                                            <td width="100" class="tdblueM">평균</td>
                                            <% } %>
                                        </tr>
            
                                        <%
                                            KETRequestPartList pl = null;
                                            QueryResult r;
                                            int lineTid = 10000;
                                            if (dr != null) {
            
                                                r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
            
                                                while (r.hasMoreElements()) {
                                                    pl = (KETRequestPartList) r.nextElement();
                                        %>
                                        <tr onMouseOver="javascript:fncOnMouseOver(this.rowIndex)">
                                            <%
                                                String partName = pl.getPartName();
                                                        lineTid++;
                                            %>
                                            <td width="40px" class="tdwhiteM"><input type='checkbox' name='iPartChk' id='iPartChk'><input
                                                type='hidden' name='tId' id='tId' value='<%=lineTid%>'></td>
                                            <td width="150px" class="tdwhiteM"><input type='text' class='txt_field' style='width: 95%'
                                                name='partName' value='<%=StringUtil.checkNull(pl.getPartName())%>'></td>
                                            <%
                                                KETCarYearlyAmount cy = null;
                                                        QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);
            
                                                        double d = 0;
                                                        double y1 = 0;
                                                        double y2 = 0;
                                                        double y3 = 0;
                                                        double y4 = 0;
                                                        double y5 = 0;
                                                        double y6 = 0;
                                                        double sum = 0;
                                                        int usg = 0;
                                                        String carTypeCode = "";
            
                                                        while (r1.hasMoreElements()) {
                                                        cy = (KETCarYearlyAmount) r1.nextElement();
            
                                                        d = cy.getYearAmount1() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y1 = y1 + d;
                                                        d = cy.getYearAmount2() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y2 = y2 + d;
                                                        d = cy.getYearAmount3() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y3 = y3 + d;
                                                        d = cy.getYearAmount4() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y4 = y4 + d;
                                                        d = cy.getYearAmount5() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y5 = y5 + d;
                                                        d = cy.getYearAmount6() * cy.getApplyedUsage() * cy.getOptionRate() / 100;
                                                        y6 = y6 + d;
            
                                                        if (usg < cy.getApplyedUsage()) {
                                                            usg = cy.getApplyedUsage().intValue();
                                                        }
            
                                                        carTypeCode = carTypeCode + "," + cy.getCarTypeCode();
                                                        }
                                                        carTypeCode = carTypeCode.substring(1);
            
                                                        sum = y1 + y2 + y3 + y4 + y5 + y6;
                                                        String packTypeCode = StringUtil.checkNull(pl.getPackTypeCode());
                                                        String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());
                                                        String sdName = "";
            
                                                        //[START][KET PLM 고도화 프로젝트] Null 처리, 2014. 6. 18. Jason, Han
                                                        if (StringUtil.checkString(summitDestinationOid)) {
                                                        NumberCode nCd = (NumberCode) CommonUtil.getObject(summitDestinationOid);
                                                        sdName = nCd.getName();
                                                        } else {
                                                        sdName = summitDestinationOid;
                                                        }
                                                        //[END][KET PLM 고도화 프로젝트] , 2014. 6. 18. Jason, Han
                                            %>
            
                                            <td width="120px" class="tdwhiteM">
                                                <input type='text' style='width: 90px' name='carTypes' class='txt_fieldRO' readonly value='<%=carTypeCode%>'>
                                                <a href='javascript:openCarTypeWindow();'><img src='../../portal/images/icon_5.png' border='0'></a></td>
                                            <td class="tdwhiteM"><input type='text' class='txt_field' style='width: 65px' name='appliedRegion' value='<%=StringUtil.checkNull(pl.getAppliedRegion())%>'></td>
                                            <td class="tdwhiteM"><input type='text' class='txt_field' style='width: 45px' name='currentApplyPart' value='<%=StringUtil.checkNull(pl.getCurrentApplyedPart())%>'></td>
                                            <td class="tdwhiteM"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='applyedUsage' value='<%=usg%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount1' value='<%=StringUtil.getDouble(y1, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount2' value='<%=StringUtil.getDouble(y2, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount3' value='<%=StringUtil.getDouble(y3, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount4' value='<%=StringUtil.getDouble(y4, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount5' value='<%=StringUtil.getDouble(y5, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='yearAmount6' value='<%=StringUtil.getDouble(y6, "", "###,###")%>'></td>
                                            <td class="tdwhiteR"><input type='text' readonly class='txt_fieldRRO' style='width: 35px' name='sumAmount' value='<%=StringUtil.getDouble(sum, "", "###,###")%>'></td>
                                            <td  class="tdwhiteM">
                                                <select name='PackTypeCode' class='fm_jmp' style='width: 60px'>
                                                    <option value='0'><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                                    <%
                                                        // 포장사양
                                                                parameter.clear();
                                                                parameter.put("locale", messageService.getLocale());
                                                                parameter.put("codeType", "PACKTYPE");
                                                                numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
            
                                                                for (int i = 0; i < numCode.size(); i++) {
                                                    %>
                                                    <option value="<%=numCode.get(i).get("oid")%>"
                                                        <%if (numCode.get(i).get("oid").equals(packTypeCode)) {%> selected <%}%>><%=numCode.get(i).get("value")%></option>
                                                    <%
                                                        }
                                                    %>
                                            </select></td>
                                            <td class="tdwhiteM">
                                                <input type='hidden' name='summitDestinationOid' value='<%=summitDestinationOid%>'>
                                                <input type='text' readonly class='txt_fieldRO' style='width: 60px' name='summitDestination' value='<%=sdName%>'>
                                                <img src='../../portal/images/icon_5.png' border='0' onClick='javascript:insertCustom()'>
                                                <a href='javascript:deleteCustom(this);return false;'><img
                                                    src='../../portal/images/icon_delete.gif' border='0'></a>
                                            </td>
                                            <td class="tdwhiteM"><input type='text' class='txt_fieldR' style='width: 95px' name='buyerAcceptPrice' value='<%=pl.getBuyerAcceptPrice()%>'></td>
                                            <td class="tdwhiteM"><input type='text' class='txt_fieldR' style='width: 95px' name='ketTargetPrice' value='<%=pl.getKetTargetPrice()%>'></td>
                                            <td class="tdwhiteM"><input type='text' class='txt_fieldR' style='width: 60px' name='targetEarningRate' value='<%=pl.getTargetEarningRate()%>'></td>
                                            <td class="tdwhiteM"><input type='text' class='txt_fieldR' style='width: 40px' name='targetTermRate' value='<%=pl.getTargetTermRate()%>'></td>
                                            <td class="tdwhiteM0"><input type='text' class='txt_fieldR' style='width: 95px' name='targetInvestmentCost' value='<%=pl.getTargetInvestmentCost()%>'></td>
                                        </tr>
                                        <%
                                            }
                                            }
                                        %>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>                                
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
                        <tr>
                            <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02734")%><%--진행일정--%></td>
                            <td id="SampleSummitDateText" width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823")%><%--초도품 제출일정--%>
                            <span class="red">*</span></td>
                            <td id="ESIRDateText" width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228")%><%--ESIR일정--%></td>
                            <td id="ISIRDateText"  width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261")%><%--ISIR일정--%></td>
                            <td id="KETMPDateText" width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294")%><%--KET 양산일정--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00831")%><span class='red'>*</span><%--고객SOP--%></td>
                        </tr>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="InitialSampleSummitDate"
                                class='txt_field' style="width: 72%" id="InitialSampleSummitDate" value="<%=initialSampleSummitDate%>">
                                <a href="javascript:deleteValue('InitialSampleSummitDate')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="ESIRDate" class='txt_field'
                                style="width: 72%" id="ESIRDate" value="<%=eSIRDate%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ESIRDate');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="ISIRDate" class='txt_field'
                                style="width: 72%" id="ISIRDate" value="<%=iSIRDate%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ISIRDate');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="KetMassProductionDate"
                                class='txt_field' style="width: 72%" id="KetMassProductionDate" value="<%=ketMassProductionDate%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('KetMassProductionDate');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" class='txt_field'
                                name="ProductSaleFirstYear" style='width: 72%' id="ProductSaleFirstYear" value="<%=productSaleFirstYear%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ProductSaleFirstYear');" style="cursor: hand;"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border">
                        <tr>
                            <td width="130" rowspan="2" class="tdblueM"><input type="text" name="scheduleName"
                                class='txt_field' style="width: 72%" id="scheduleName" value="<%=scheduleName%>"></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823")%><%--초도품 제출일정--%>
                                <span class="red"></span></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228")%><%--ESIR일정--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261")%><%--ISIR일정--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294")%><%--KET 양산일정--%></td>
                            <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00831")%><%--고객SOP--%></td>
                        </tr>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="InitialSampleSummitDate2"
                                class='txt_field' style="width: 72%" id="InitialSampleSummitDate2" value="<%=InitialSampleSummitDate2%>">
                                <a href="javascript:deleteValue('InitialSampleSummitDate2')"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="ESIRDate2" class='txt_field'
                                style="width: 72%" id="ESIRDate2" value="<%=ESIRDate2%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ESIRDate2');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="ISIRDate2" class='txt_field'
                                style="width: 72%" id="ISIRDate2" value="<%=ISIRDate2%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ISIRDate2');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" name="KetMassProductionDate2"
                                class='txt_field' style="width: 72%" id="KetMassProductionDate2" value="<%=KetMassProductionDate2%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('KetMassProductionDate2');" style="cursor: hand;"></td>
                            <td class="tdwhiteM">&nbsp;<input type="text" class='txt_field'
                                name="ProductSaleFirstYear2" style='width: 72%' id="ProductSaleFirstYear2" value="<%=ProductSaleFirstYear2%>">
                                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('ProductSaleFirstYear2');" style="cursor: hand;"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="../../portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02679")%><%--주요제품 사양--%></td>
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
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="130px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><!-- 제품구분 --></td>
                            <td colspan="3" class="tdwhiteL0">
                                <input type="text" name="ProductCategoryName" class="txt_field" style="width: 300px">
                                <input type="hidden" name="ProductCategoryCode" value="">
                                <a href="javascript:selectPartClazPopUp();">
                                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                <a href="javascript:CommonUtil.deleteValue('ProductCategoryCode','ProductCategoryName');">
                                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01147") %><!-- 기타내용 --></td>
                            <td colspan="3" class="tdwhiteL0">
                                <input name="EtcSpecification" class="txt_field" style="width:100%">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td height="5" valign="bottom">
                    <DIV style='overflow: auto; width:100%; height: 4;'>
                        <table width="600" height="3" border="0" cellpadding="0" cellspacing="0" style="display:none">
                            <tbody id="iCarTable" />
                            <%
                                if (dr != null) {
                                    pl = null;
                                    r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
                                    lineTid = 10000;
                                    while (r.hasMoreElements()) {
                                        pl = (KETRequestPartList) r.nextElement();
                                        String partName = pl.getPartName();
                                        lineTid++;
                                        KETCarYearlyAmount cy = null;
                                        QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

                                        while (r1.hasMoreElements()) {
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
                                <td><input type='hidden' readonly name='sum'
                                    value='<%=cy.getYearAmount1() + cy.getYearAmount2() + cy.getYearAmount3() + cy.getYearAmount4() + cy.getYearAmount5() + cy.getYearAmount6()%>'></td>
                                <td><input type='hidden' readonly name='usage' value='<%=cy.getApplyedUsage().intValue()%>'></td>
                                <td><input type='hidden' readonly name='optRate' value='<%=cy.getOptionRate().intValue()%>'></td>
                                <td><input type='hidden' readonly name='carTypeOid' value='<%=cy.getCarType()%>'></td>
                                <td><input type='hidden' readonly name='carTypeCode' value='<%=cy.getCarTypeCode()%>'></td>
                            </tr>
                            <%
                                }
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
