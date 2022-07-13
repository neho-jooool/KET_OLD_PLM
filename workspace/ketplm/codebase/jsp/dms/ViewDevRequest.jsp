<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="ext.ket.sales.entity.KETSalesProject"%>
<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                  e3ps.dms.entity.KETDevelopmentRequest,
                  e3ps.dms.entity.KETRequestPartList,
                  e3ps.dms.entity.KETCarYearlyAmount,
                  e3ps.dms.entity.KETRequestPartLink,
                  e3ps.dms.entity.KETPartCarLink,
                  e3ps.project.outputtype.ModelPlan,
                  e3ps.project.E3PSProject,
                  ext.ket.sales.entity.KETSalesProject,
                  wt.fc.QueryResult,
                  wt.query.QuerySpec,
                  wt.query.SearchCondition,
                  wt.query.ClassAttribute,
                  wt.query.OrderBy,
                  wt.fc.PersistenceHelper,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.content.*,
                  wt.part.WTPart,
                  wt.org.WTUser,
                  wt.lifecycle.LifeCycleHelper,
                  wt.lifecycle.LifeCycleManaged,
                  wt.session.SessionHelper,
                  e3ps.common.content.*,
                  java.util.Vector,
                  java.util.ArrayList,
                  java.util.StringTokenizer,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/multicombo.jsp" %>
<%

  String oid = request.getParameter("oid");
  String isPop = request.getParameter("isPop");
  isPop = StringUtil.checkNull(isPop);
  String isPrt = request.getParameter("isPrt");
  isPrt = StringUtil.checkNull(isPrt);

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
  boolean isIframe = Boolean.parseBoolean(StringUtil.checkReplaceStr(request.getParameter("isIframe"), "false"));

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

  String reqNo = "";
   String reqName = "";
   String reception = "";
   String receptionName = "";
   String creatorName = "";
   String lifeCycleState = "";
   String stateState = "";
  String divisionCode = "";
  String developmentStep = "";
  String developmentStepStr = "";
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
  String divCode = "";
  
  String InitialSampleSummitDate2 = "";
  String ESIRDate2 = "";
  String ISIRDate2 = "";
  String ProductSaleFirstYear2 = "";
  String KetMassProductionDate2 = "";
  String scheduleName = "";
  String salesProjectNo = "";
  String salesProjectOid = "";
  String reviewResult = "진행";
  
   KETDevelopmentRequest dr = null;

   Boolean isAuth = false;
   Boolean isDivAuth = false;
   Boolean isRecvAuth = false;

   String loginOid = "";

  if(oid!=null){
    if(oid.equals("0000")){

    }else{
      //KETDevelopmentRequest oid로 해당 객체의 정보를 화면에 나타낸다.
      dr = (KETDevelopmentRequest)CommonUtil.getObject(oid);
      reqNo = dr.getNumber();

      WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
      loginOid = CommonUtil.getOIDString(loginUser);

      if(loginOid.equals(dr.getIterationInfo().getCreator().toString())){
        isAuth = true;
      }

      divisionCode = StringUtil.checkNull(dr.getDivisionCode());

      String logDiv;
      if(CommonUtil.isMember("전자사업부") && !CommonUtil.getUserDeptNameFromSession().startsWith("전장IT")){
        logDiv = "ER";
        divCode = "ER";
      }else{
        logDiv = "CA";
        divCode = "CA";
      }
      
      if(divisionCode.equals(logDiv)){
        isDivAuth = true;
      }

      reception = StringUtil.checkNull(dr.getReception());

      if(!reception.equals("")){
        WTUser rcvUser = (WTUser)CommonUtil.getObject(reception);
        String rcvOid = CommonUtil.getOIDString(rcvUser);
        receptionName = rcvUser.getFullName();

        if(divisionCode.equals("ER")){
          if(loginOid.equals(rcvOid)){
            isRecvAuth = true;
          }
        }
      }

      if(divisionCode.equals("CA")){
        if(CommonUtil.isMember("자동차PMO")){
          isRecvAuth = true;
        }
      }

      creatorName = StringUtil.checkNull(dr.getIterationInfo().getCreator().getFullName());
      
      lifeCycleState = dr.getLifeCycleState().getDisplay();
      stateState = dr.getLifeCycleState().toString();

      developmentStep = StringUtil.checkNull(dr.getDevelopmentStep());

      if(developmentStep.equals("D")){
        developmentStepStr = messageService.getString("e3ps.message.ket_message","00656");//"개발의뢰";
      }else if(developmentStep.equals("R")){
        developmentStepStr = messageService.getString("e3ps.message.ket_message","00617");//"개발검토의뢰";
      }else{
        developmentStepStr = "-";
      }

      projectOID = StringUtil.checkNull(dr.getProjectOID());
      
      //[START] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, Null 처리 2014-06-10, Jason Han
      if(!StringUtil.isEmpty(projectOID)){
      //if(!projectOID.equals("")){
      //[END] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, Null 처리 2014-06-10, Jason Han
      
          String objs[] = projectOID.split(",");
      
	      for(String target : objs){
		      
		      Object obj = CommonUtil.getObject(target);
		      
		      if ( obj instanceof E3PSProject ) {
	
		          E3PSProject project = (E3PSProject)obj;
		          projectNO += project.getPjtNo()+",";
		          
		          if(obj instanceof ReviewProject){
		              ReviewProject rproject = (ReviewProject)obj;
		              if(!"진행".equals(StringUtil.checkNull(rproject.getReviewResult()) ) ){
		        	      reviewResult = rproject.getReviewResult();
		              }
		              
		          }
		          
		        }else if ( obj instanceof KETDevelopmentRequest ) {
		          KETDevelopmentRequest dr1 = (KETDevelopmentRequest)obj;
		          projectNO = dr1.getNumber();
	
		        }else{
		          projectNO = "";
		        }
	      }
	      projectNO = StringUtils.removeEnd(projectNO, ",");

        //Object obj = CommonUtil.getObject(projectOID);
      }

      requestBuyer = StringUtil.checkNull(dr.getRequestBuyer());
      if(!StringUtil.checkNull(requestBuyer).equals("")){
        StringTokenizer st = new StringTokenizer(requestBuyer, ",");
        String ct="";
        String bName="";
        while (st.hasMoreTokens()) {
          ct=st.nextToken();
          NumberCode nCode1 = (NumberCode)CommonUtil.getObject(ct);

          if(nCode1==null){
            bName=bName + "," + ct;
          }else{
            bName=bName + "," + nCode1.getName();
          }
        }
        if(!bName.equals("")) requestBuyer=bName.substring(1);
      }

      requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());

      lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer());
      if(!StringUtil.checkNull(lastUsingBuyer).equals("")){
        StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ",");
        String ct="";
        String bName="";
        while (st1.hasMoreTokens()) {
          ct=st1.nextToken();
          NumberCode nCode2 = (NumberCode)CommonUtil.getObject(ct);

          if(nCode2==null){
            bName=bName + "," + ct;
          }else{
            bName=bName + "," + nCode2.getName();
          }
        }
        if(!bName.equals("")) lastUsingBuyer=bName.substring(1);
      }

      lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());

      repCarType = StringUtil.checkNull(dr.getRepCarType());

      isDRRequest = StringUtil.checkNull(dr.getIsDRRequest());
      if(isDRRequest.equals("1")){
        drRequestDate = DateUtil.getTimeFormat(dr.getDrRequestDate(),"yyyy-MM-dd");
      }else{
        drRequestDate = messageService.getString("e3ps.message.ket_message","01626");//"불필요";
      }

      isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
      if(isProposalSubmit.equals("1")){
        proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(),"yyyy-MM-dd");
      }else{
        proposalSubmitDate = messageService.getString("e3ps.message.ket_message","01626");//"불필요";
      }

      isCostSubmit = StringUtil.checkNull(dr.getIsCostSubmit());
      if(isCostSubmit.equals("1")){
        costSubmitDate = DateUtil.getTimeFormat(dr.getCostSubmitDate(),"yyyy-MM-dd");
      }else{
        costSubmitDate = "불필요";
      }

      devProductName = StringUtil.checkNull(dr.getDevProductName());
      devReviewTypeCode = StringUtil.checkNull(dr.getDevReviewTypeCode());
      devReviewTypeEtc = StringUtil.checkNull(dr.getDevReviewTypeEtc());

      if(!StringUtil.checkNull(devReviewTypeCode).equals("")){
        NumberCode nCode1 = (NumberCode)CommonUtil.getObject(devReviewTypeCode);
        String bName="";
        if(nCode1==null){
          bName=devReviewTypeCode;
        }else{
          bName=nCode1.getName();
        }
        if(!bName.equals("")) devReviewTypeCode=bName;
      }
      if(devReviewTypeCode.equals("기타")) devReviewTypeCode = "기타: " + devReviewTypeEtc;

      devReviewDetailComment = StringUtil.checkNull(dr.getDevReviewDetailComment());

      productSaleFirstYear = StringUtil.checkNull(dr.getProductSaleFirstYear());

      initialSampleSummitDate = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(),"yyyy-MM-dd");
      eSIRDate = DateUtil.getTimeFormat(dr.getESIRDate(),"yyyy-MM-dd");
      iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(),"yyyy-MM-dd");
      ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(),"yyyy-MM-dd");

      String nCodeOid = StringUtil.checkNull(dr.getProductCategoryCode());
      String productTypeCodes = StringUtil.checkNull(dr.getProductTypeCode());
      if(StringUtil.checkString(nCodeOid) && nCodeOid.indexOf("KETPartClassification") > 0){
          String[] productCategorys = nCodeOid.split(",");
          try{
              for(int i=0;i<productCategorys.length;i++){
                  KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(productCategorys[i]);
                  if(i != productCategorys.length - 1){
                      productCategoryCode += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName()+", ";
                  }else{
                      productCategoryCode += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();              
                  }
              }
          }catch(Exception e){ Kogger.error(e);
          }
      }else{
        productCategoryCode = "";
        productTypeCode = "";
      }

      etcSpecification = StringUtil.checkNull(dr.getEtcSpecification());
      tabSize = StringUtil.checkNull(dr.getTabSize());
      materialSubMaterial = StringUtil.checkNull(dr.getMaterialSubMaterial());
      surfaceTreatmentCode = StringUtil.checkNull(dr.getSurfaceTreatmentCode());

      if(surfaceTreatmentCode.equals("0")) surfaceTreatmentCode = "";

      if(!StringUtil.checkNull(surfaceTreatmentCode).equals("")){
        NumberCode nCode1 = (NumberCode)CommonUtil.getObject(surfaceTreatmentCode);
        String bName="";
        if(nCode1==null){
          bName=surfaceTreatmentCode;
        }else{
          bName=nCode1.getName();
        }
        if(!bName.equals("")) surfaceTreatmentCode=bName;
      }

      applyedWire = StringUtil.checkNull(dr.getApplyedWire());
      primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction());
      outlook = StringUtil.checkNull(dr.getOutlook());

      moldDepreciationTypeSale = StringUtil.checkNull(dr.getMoldDepreciationTypeSale());
      moldDepreciationTypeGeneral = StringUtil.checkNull(dr.getMoldDepreciationTypeGeneral());
      moldDepreciationTypePayment = StringUtil.checkNull(dr.getMoldDepreciationTypePayment());
      moldDepreciationTypePeriod = StringUtil.checkNull(dr.getMoldDepreciationTypePeriod());
      moldDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getMoldDepreciationTypeEtcInfo());

      if(!moldDepreciationTypeSale.equals("")) moldDepreciationType = moldDepreciationType + "/판매";
      if(!moldDepreciationTypeGeneral.equals("")) moldDepreciationType = moldDepreciationType + "/일반";
      if(!moldDepreciationTypePayment.equals("")) moldDepreciationType = moldDepreciationType + "/지급";
      if(!moldDepreciationTypePeriod.equals("")) moldDepreciationType = moldDepreciationType + "/감가기간";
      if(!moldDepreciationTypeEtcInfo.equals("")) moldDepreciationType = moldDepreciationType + "/기타:" + moldDepreciationTypeEtcInfo;
      if(moldDepreciationType.equals("")||moldDepreciationType.equals("/")) moldDepreciationType="";
      else moldDepreciationType = moldDepreciationType.substring(1);

      equipDepreciationTypeSale = StringUtil.checkNull(dr.getEquipDepreciationTypeSale());
      equipDepreciationTypePayment = StringUtil.checkNull(dr.getEquipDepreciationTypePayment());
      equipDepreciationTypePeriod = StringUtil.checkNull(dr.getEquipDepreciationTypePeriod());
      equipDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getEquipDepreciationTypeEtcInfo());

      if(!equipDepreciationTypeSale.equals("")) equipDepreciationType = equipDepreciationType + "/판매";
      if(!equipDepreciationTypePayment.equals("")) equipDepreciationType = equipDepreciationType + "/지급";
      if(!equipDepreciationTypePeriod.equals("")) equipDepreciationType = equipDepreciationType + "/감가기간";
      if(!equipDepreciationTypeEtcInfo.equals("")) equipDepreciationType = equipDepreciationType + "/기타:" + equipDepreciationTypeEtcInfo;
      if(equipDepreciationType.equals("")||equipDepreciationType.equals("/")) equipDepreciationType="";
      else equipDepreciationType = equipDepreciationType.substring(1);

      deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
      environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem());
      buyerEtcRequirement = StringUtil.checkNull(dr.getBuyerEtcRequirement());
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
      
      QuerySpec spec = new QuerySpec();
      int idx = spec.addClassList(KETSalesProject.class, true);
      SearchCondition sc = null;
      
      sc = new SearchCondition(KETSalesProject.class, "devRequestReference.key.id", SearchCondition.EQUAL,CommonUtil.getOIDLongValue(dr));
      
      spec.appendWhere(sc, new int[] { idx });
      
      
      
      QueryResult salesqr = PersistenceHelper.manager.find(spec);
      
      while (salesqr.hasMoreElements()) {
          Object[] tempObj = (Object[]) salesqr.nextElement();
          KETSalesProject salesProject = (KETSalesProject) tempObj[0];
          salesProjectNo = salesProject.getProjectNo();
          salesProjectOid = CommonUtil.getOIDString(salesProject);
      }



     }
  }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01804", developmentStepStr) %><%--{0} 서 상세조회--%></title>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: <%= ("true".equals(isIframe))?"0":"10"%>px;
    margin-top: 0px;
    margin-right: <%= ("true".equals(isIframe))?"0":"10"%>px;
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
  
  function updateDevRequest(oid){
    document.location.href="/plm/jsp/dms/UpdateDevRequest.jsp?cmd=update&oid="+oid+"&nowBlock=<%=SnowBlock%>&page=<%=Spage%>&DevelopmentStep=<%=SDevelopmentStep%>&DevProductName=<%=SDevProductName%>&RequestBuyer=<%=SRequestBuyer%>&LastUsingBuyer=<%=SLastUsingBuyer%>&userName=<%=SuserName%>&RepCarType=<%=SRepCarType%>&authorStatus=<%=SauthorStatus%>&DevReviewTypeCode=<%=SDevReviewTypeCode%>&ProductCategoryCode=<%=SProductCategoryCode%>&predate=<%=Spredate%>&postdate=<%=Spostdate%>&pageCnt=<%=SpageCnt%>&sortKeyD=<%=SsortKeyD%>&sortKey=<%=SsortKey%>&isSer=T";
  }

  function printDevRequest(oid){
    var url="/plm/jsp/dms/ViewDevRequestPrint.jsp?oid="+oid;
    openWindow2(url,"","818","1110","status=1,scrollbars=yes,resizable=no");
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

  function deleteDevRequest(oid){
	  
	  var d = document.form01;
	    
	  <%if(developmentStep.equals("D")){%>
		  
	      var msg = checkDevRequest(oid);
	      
          if(msg != ''){
                alert(msg);
                return;
            }
	   <%}%>
        if(confirm('<%=messageService.getString("e3ps.message.ket_message", "02256") %><%--의뢰서를 삭제하시겠습니까?--%>')){
        	  document.location.href="/plm/servlet/e3ps/DevRequestServlet?cmd=delete&oid="+oid;
        }
  }

  function openDrItemWindow(pName) {
    var oid = document.form01.drOid.value;
    var url="/plm/jsp/dms/ViewDevRequestItem.jsp?oid="+oid+"&pname="+pName;
    openWindow(url,"","810","300","status=1,scrollbars=no,resizable=no");
  }

  function opencarTypePlanWindow(oid) {
    var url="/plm/jsp/project/ViewProgramPop.jsp?oid="+oid;
    openWindow2(url,"","800","600","status=1,scrollbars=yes,resizable=no");
  }

  function viewProjectPop(oid){
    var url="/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    openWindow(url,"","1050","640","status=1,scrollbars=no,resizable=no");
  }

  function selDr(arr){
    for (var i = 0; i < arr.length; i++){
       var drOid = arr[i];
    }
  }

  function listDocument(){
    document.location.href="/plm/jsp/dms/SearchDevRequest.jsp";
  }
  
  function viewSales(oid){
      getOpenWindow2('/plm/ext/sales/project/viewSalesProjectForm.do?oid='+oid,'viewSalesProjectFormPopup',1100,800);
  }
  
//-->
</script>
</head>
<body>
<form name=form01 method="post" >
<input type="hidden" name="drOid" value="<%=oid%>">
<table style="width: 100%;">
    <tr>
        <td valign="top">
            <table style="<%=(isPop.equals("Y") && isIframe)?"display:none":"width: 100%;" %>">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td width="7px"></td>
                                <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01804", developmentStepStr) %><%--{0} 서 상세조회--%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="176px" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="780" style="<%=(isPop.equals("Y") && isIframe)?"display:none":"" %>">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="<%=(isPop.equals("Y") && isIframe)?"display:none":"" %>">
       <tr>
         <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
         <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
         <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
             <tr>
               <%if((stateState.equals("RECEIVING"))&&(isRecvAuth==true)) {%>
               <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateDevRequest('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                 </table>
               </td>
               <td width="5">&nbsp;</td>
                <%}%>
               <%if((((stateState.equals("INWORK"))||(stateState.equals("REWORK")))&&(isAuth==true)) || CommonUtil.isAdmin()) {%>
               <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goPage('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00778") %><%--결재요청--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                 </table>
               </td>
               <td width="5">&nbsp;</td>
               <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateDevRequest('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
               <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDevRequest('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                  </table>
                </td>
               <td width="5">&nbsp;</td>
               <%}%>
               <td>
                 <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                         <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:printDevRequest('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03126") %><%--프린트--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                 </table>
               </td>
               <td width="5">&nbsp;</td>
               <td>
                 <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:parent.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
     <table <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellspacing="0" cellpadding="0" width="100%">
     <colgroup>
        <col width="125px">
        <col>
        <col width="140px">
        <col>        
     </colgroup>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
       <td colspan="3" class="tdwhiteL0"><%=reqNo%></td>
     </tr>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248") %><%--의뢰담당자--%></td>
       <td class="tdwhiteL"><%=creatorName%></td>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
       <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=oid%>')"><%=lifeCycleState%></a></td>
     </tr>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
       <td class="tdwhiteL"><%=developmentStepStr%></td>
       <%if(developmentStep.equals("D")){%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00736") %><%--검토프로젝트번호--%></td>
       <td class="tdwhiteL0">
       
       <%String projectNos[] = projectNO.split(","); String projectOids[] = projectOID.split(",");
           for(int i=0; i<projectNos.length; i++){
       %>
       <a href="javascript:openView('<%=projectOids[i]%>')"><%=projectNos[i]%>&nbsp;</a>
       <%}%>
       
       
       
       <%if( !"진행".equals(reviewResult) && (divisionCode.equals("ER"))  ){ %><span><font color="red">(미진행)</font></span> <%} %>   </td>
       <%}else{%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01114") %><%--기검토 의뢰번호--%></td>
       <td class="tdwhiteL0"><%=projectNO%>&nbsp;</td>
     <%}%>
     </tr>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
       <td class="tdwhiteL"><%=requestBuyer%></td>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01205")%><%--접점고객 담당자--%></td>
       <td class="tdwhiteL0"><%=requestBuyerManager%>&nbsp;</td>
     </tr>
     <tr>
             <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
       <td class="tdwhiteL"><%=lastUsingBuyer%></td>
             <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02848") %><%--최종사용처 담당자--%></td>
       <td class="tdwhiteL0"><%=lastUsingBuyerManager%>&nbsp;</td>
     </tr>
     <tr>
             <td class="tdblueL"><%if(divisionCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
       <td class="tdwhiteL"><%=repCarType%>&nbsp;</td>
       <%if(developmentStep.equals("R")){%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00616") %><%--개발검토유형--%></td>
       <td class="tdwhiteL0"><pre><%=devReviewTypeCode%></pre></td>
     </tr>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02527") %><%--제안서 제출일정--%></td>
       <td class="tdwhiteL"><%=proposalSubmitDate%>&nbsp;</td>
       <%}%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00650") %><%--개발원가 제출일정--%></td>
       <td class="tdwhiteL0"><%=costSubmitDate%>&nbsp;</td>
     </tr>
     <tr>
       <%if(developmentStep.equals("D")){%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%></td>
       <%}else{%>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00623") %><%--개발검토제품명--%></td>
       <%}%>
       <td class="tdwhiteL"><div style='text-align:left;width: 95%; border: 0; padding-left:3px; margin: 0; text-overflow: ellipsis; overflow: hidden;' title="<%=devProductName%>"><nobr><%=devProductName%></nobr></div></td>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
       <td class="tdwhiteL0"><%=DateUtil.getDateString(dr.getCreateTimestamp(), "d")%>&nbsp;</td>
       <%if(!developmentStep.equals("D")){%>
       <tr>
       <td class="tdblueL">영업프로젝트</td>
       <td class="tdwhiteL0" colspan=3><a href="javascript:viewSales('<%=salesProjectOid%>')"><%=salesProjectNo%></a>&nbsp;</td>
       </tr>
       <%}%>
     </tr>
     <%if(developmentStep.equals("D")){%>
     <tr>
       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
       <td colspan="3" class="tdwhiteL0"><pre><%=devReviewTypeCode%></pre></td>
     </tr>
     <%}%>
     <tr>
       <td class="tdblueL"><%if(developmentStep.equals("D")){%><%=messageService.getString("e3ps.message.ket_message", "00668") %><%--개발착수내용--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "00612") %><%--개발검토내용--%><%}%></td>
       <td colspan="3" class="tdwhiteL0">
            <table border="0" cellspacing="0" cellpadding="0" class="table_border" width="100%">
                <tr>
                    <td width="100" class="tdgrayL">1.<%
                    if (divisionCode.equals("CA")) {
                    %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%> <%
                        } else {
                    %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                        }
                    %></td>
                    <td width="*" class="tdgrayL"><%=attribute2%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">2.<%=messageService.getString("e3ps.message.ket_message", "02467") %><!-- 적용부위 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute3%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">3.<%=messageService.getString("e3ps.message.ket_message", "09362") %><!-- 개발부품 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute4%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">4.<%=messageService.getString("e3ps.message.ket_message", "09363") %><!-- 개발일정 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute5%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">5.<%=messageService.getString("e3ps.message.ket_message", "09364") %><!-- 양산투자비 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute6%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">6.<%=messageService.getString("e3ps.message.ket_message", "01168") %><!-- 납입처 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute7%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">7.<%=messageService.getString("e3ps.message.ket_message", "09365") %><!-- 예상매출액 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute8%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">8.<%=messageService.getString("e3ps.message.ket_message", "03202")%><%--향후전망--%></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=outlook%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">9.<%=messageService.getString("e3ps.message.ket_message", "09475") %><!-- 경쟁사정보 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute10%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">10.<%=messageService.getString("e3ps.message.ket_message", "09476") %><!-- KET단독입찰여부 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute11%></td>
                </tr>
                <tr>
                    <td class="tdgrayL">11.<%=messageService.getString("e3ps.message.ket_message", "01136") %><!-- 기타 --></td>
                    <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute9%></td>
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
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
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
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td width="140px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02873") %><%--추가요청사항--%></td>
 <td class="tdwhiteL0" style="white-space:pre-line;"><%=salesAdditionalRequirement%></td>
 </tr>
 <tr>
 <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
 <%
       String appDataOid;
       String urlpath;
       String iconpath;

      if ( dr instanceof FormatContentHolder ) {
        FormatContentHolder holder = (FormatContentHolder)dr;
        Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
        if(secondaryFiles.size()>0){
%>
 <td class="tdwhiteL0"><p>
 <%
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
              urlpath = "<a href=javascript:PLM_FILE_DOWNLOAD2('" + urlpath + "');>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
              iconpath = info.getIconURLStr();

            }
%>
            <%=iconpath%>&nbsp;<%=urlpath%><br>
<%
          }
%>
            </p></td>
<%
        }else{
%>
      <td colspan="3" class="tdwhiteL0"><p> &nbsp;</p></td>
<%
        }
      }else{
%>
      <td colspan="3" class="tdwhiteL0"><p> &nbsp;</p></td>
<%
      }
%>

 </tr>
 </table>
   <table border="0" cellspacing="0" cellpadding="0" width="100%">
     <tr>
        <td class="space15"></td>
     </tr>
   </table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0" >
     <tr>
       <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
             <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></td>
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
       <td>
         <table border="0" cellspacing="0" cellpadding="0" width="100%">
           <tr>
              <td class="tab_btm2"></td>
           </tr>
         </table>
         <table width="100%" <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellpadding="0" cellspacing="0" style="table-layout:fixed">
           <tr>
                         <td width="170" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02591") %><%--제품명--%></td>
                         <td width="80" rowspan="2" class="tdblueM"><%if(divisionCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
                         <td width="90" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02467") %><%--적용부위--%></td>
                         <td width="85" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03208") %><%--현적용품--%></td>
             <td width="40" rowspan="2" class="tdblueM">U/S</td>
             <td colspan="7" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02154") %><%--예상수량(천개/년)--%></td>
           </tr>
           <tr>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 1) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 2) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 3) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 4) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 5) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 6) %><%--{0}년차--%></td>
             <td width="45" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
           </tr>
<%
         KETRequestPartList pl = null;
         QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
         while(r.hasMoreElements()){
           pl = (KETRequestPartList) r.nextElement();
%>
            <tr>
<%
           String partName=pl.getPartName();
%>
               <td width="170" class="tdwhiteM" title="<%=StringUtil.checkNull(pl.getPartName())%>" style="text-overflow:ellipsis;overflow:hidden;"><a href="javascript:openDrItemWindow('<%=StringUtil.checkNull(pl.getPartName())%>')"><nobr><%=StringUtil.checkNull(pl.getPartName())%></nobr></a></td>
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
           String carTypeOid = "";
           String carTypePlanOid = "";
           String carTypeStr = "";
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

             carTypeOid = cy.getCarType();

             long oidLong = CommonUtil.getOIDLongValue(carTypeOid);

             QuerySpec qs = new QuerySpec();
             int idx = qs.appendClassList(ModelPlan.class, true);

             SearchCondition sc3 = new SearchCondition(ModelPlan.class,"carTypeReference.key.id","=", oidLong);
             qs.appendWhere(sc3, new int[]{idx});

             QueryResult qr4 = PersistenceHelper.manager.find( qs );

             if(qr4.hasMoreElements()){
              Object[] o2 = (Object[])qr4.nextElement();
              ModelPlan mp = (ModelPlan)o2[0];
              carTypePlanOid = CommonUtil.getOIDString(mp);

              carTypeCode = cy.getCarTypeCode();
              carTypeStr = carTypeStr + "," + "<a href=" +"\""+ "javascript:opencarTypePlanWindow('"+carTypePlanOid+"')" +"\""+ "><nobr>"+carTypeCode+"</nobr></a>";

             }else{
               carTypeCode = cy.getCarTypeCode();
               carTypeStr = carTypeStr + "," + carTypeCode;
             }
           }
           
           if(!"".equals(carTypeStr) && carTypeStr != null){
               carTypeStr = carTypeStr.substring(1);
           }
           sum = y1 + y2 + y3 + y4 + y5 + y6;
/*            if(cy != null && !"".equals(cy.getApplyedUsage()) && cy.getApplyedUsage() != null && !"".equals(cy.getOptionRate()) && cy.getOptionRate() != null){
               sum = Math.round(sum*cy.getApplyedUsage()*cy.getOptionRate()/100);      
           } */
       
       //out.println("sum="+sum);
%>

         <td width="80" class="tdwhiteM" title="<%=StringUtil.checkNull(carTypeCode)%>" style="text-overflow:ellipsis;overflow:hidden;"><%=carTypeStr%></td>
         <td width="90" class="tdwhiteM" title="<%=StringUtil.checkNull(pl.getAppliedRegion())%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=StringUtil.checkNull(pl.getAppliedRegion())%>&nbsp;</nobr></td>
         <td width="85" class="tdwhiteM" title="<%=StringUtil.checkNull(pl.getCurrentApplyedPart())%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=StringUtil.checkNull(pl.getCurrentApplyedPart())%>&nbsp;</nobr></td>
         <td width="40" class="tdwhiteM"><%=usg%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y1, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y2, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y3, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y4, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y5, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y6, "", "###,###")%></td>
         <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(sum, "", "###,###")%></td>
         </tr>
<%
     }
%>
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
<table width="100%" <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellpadding="0" cellspacing="0" style="table-layout:fixed">
 <tr>
     <td width="170" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02591") %><%--제품명--%></td>
     <td width="90" class="tdblueM" rowspan="2" ><%=messageService.getString("e3ps.message.ket_message", "02991") %><%--포장사양--%></td>
     <td width="120" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01168") %><%--납입처--%></td>
     <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00847") %><%--고객예상가(원)--%></td>
     <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02968") %><%--판매목표가(원)--%></td>
     <td width="100" class="tdblueM" colspan="<%="D".equals(developmentStep) ? 2: 1%>"><%=messageService.getString("e3ps.message.ket_message", "01390") %><%--목표수익률(%)--%></td>
     <td width="40" class="tdblueM" rowspan="2">기간<br>수익률<br>(%)</td>
     <td width="100" class="tdblueM0" rowspan="2">목표투자비<br>천원</td>
</tr>
<tr>
     <td width="100" class="tdblueM">초도</td>
     <%if("D".equals(developmentStep)) {%>
     <td width="100" class="tdblueM">평균</td>
     <%} %>
</tr>
<%
     r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
     while(r.hasMoreElements()){
       pl = (KETRequestPartList) r.nextElement();
       String partName=pl.getPartName();

       String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());
       String sdName = "";

        //[START] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, Null 처리 2014-06-10, Jason Han
        NumberCode nCd = null;
        if(!StringUtil.isTrimToEmpty(summitDestinationOid)) nCd = (NumberCode)CommonUtil.getObject(summitDestinationOid);
        //[END] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, Null 처리 2014-06-10, Jason Han
        if(nCd==null){
         sdName=summitDestinationOid;
       }else{
         sdName=nCd.getName();
       }

       String packTypeoid = StringUtil.checkNull(pl.getPackTypeCode());
       String ptName = "";

        NumberCode pCd = (NumberCode)CommonUtil.getObject(packTypeoid);
        if(pCd==null){
         ptName=packTypeoid;
       }else{
         ptName=pCd.getName();
       }
%>
      <tr>
       <td width="157" class="tdwhiteM"><a href="javascript:openDrItemWindow('<%=StringUtil.checkNull(pl.getPartName())%>')"><%=StringUtil.checkNull(pl.getPartName())%></a></td>
       <td width="100" class="tdwhiteM"><%=ptName%>&nbsp;</td>
       <td width="123" class="tdwhiteM"><%=sdName%>&nbsp;</td>
       <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getBuyerAcceptPrice(), "", "###,###")%>&nbsp;</td>
       <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getKetTargetPrice(), "", "###,###")%>&nbsp;</td>
       <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getTargetEarningRate(), "", "###,###")%>&nbsp;</td><!-- 초도(예전 목표수익률 컬럼) -->
       <%if("D".equals(developmentStep)) {%>
       <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getTargetAverageRate(), "", "###.###") %>&nbsp;</td><!-- 평균(새로 만든 컬럼)-->
       <%} %>
       <td width="40" class="tdwhiteM"><%=StringUtil.getDouble(pl.getTargetTermRate(), "", "###,###")%>&nbsp;</td>
       <td width="100" class="tdwhiteM0"><%=StringUtil.getDouble(pl.getTargetInvestmentCost(), "", "###,###")%>&nbsp;</td>
      </tr>
<%
     }
%>

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
 <table <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellspacing="0" cellpadding="0" width="100%"  >
  <tr>
    <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02734") %><%--진행일정--%></td>
    <%if(divisionCode.equals("CA")){%>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823") %><%--초도품 제출일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228") %><%--ESIR일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261") %><%--ISIR일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
    <%}else{ %>
	    <%if(developmentStep.equals("D")){%>
	    <td width="140" class="tdblueM">DR0(목표)일</td>
	    <td width="140" class="tdblueM">초도품 제출일정</td>
	    <td width="140" class="tdblueM">승인원 제출 일정</td>
	    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
	    <%}else{%>
	    <td width="140" class="tdblueM">개발시작회의</td>
	    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02824")%></td><%--초도품 제출--%>
	    <td width="140" class="tdblueM">승인샘플/승인원 제출</td>
	    <td width="140" class="tdblueM">최종고객승인</td>
	    <%}%>
    <%}%>
    <td width="140" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%--고객SOP--%></td>
  </tr>
  <tr>
   <td width="140" class="tdwhiteM"><%=initialSampleSummitDate%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=eSIRDate%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=iSIRDate%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=ketMassProductionDate%>&nbsp;</td>
   <td width="140" class="tdwhiteM0"><%=productSaleFirstYear%>&nbsp;</td>
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
 <table <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellspacing="0" cellpadding="0" width="100%"  >
  <tr>
    <td width="60" rowspan="2" class="tdblueM"><%=StringUtil.checkNull(dr.getScheduleName())%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823") %><%--초도품 제출일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228") %><%--ESIR일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261") %><%--ISIR일정--%></td>
    <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %><%--KET 양산일정--%></td>
    <td width="140" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%--고객SOP--%></td>
  </tr>
  <tr>
   <td width="140" class="tdwhiteM"><%=InitialSampleSummitDate2%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=ESIRDate2%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=ISIRDate2%>&nbsp;</td>
   <td width="140" class="tdwhiteM"><%=KetMassProductionDate2%>&nbsp;</td>
   <td width="140" class="tdwhiteM0"><%=ProductSaleFirstYear2%>&nbsp;</td>
  </tr>
 </table>

 <table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="space15"></td>
 </tr>
 </table>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
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
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table <%if(isPrt.equals("Y")){%> border="1" <%}else{%> border="0" <%}%> cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><!-- 제품구분 --></td>
 <td colspan="3" class="tdwhiteL0"><%=productCategoryCode%>&nbsp;</td>
 </tr>
 <tr>
 <tr>
 <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01147") %><%--기타내용--%></td>
 <td colspan="3" class="tdwhiteL0"><%=etcSpecification%>&nbsp;</td>
 </tr>
</table>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>
</form>
</body>
</html>
