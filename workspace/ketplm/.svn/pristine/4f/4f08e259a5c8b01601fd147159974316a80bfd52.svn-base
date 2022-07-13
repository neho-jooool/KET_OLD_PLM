<%@page import="ext.ket.part.entity.KETPartClassification"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                                    e3ps.dms.entity.KETDevelopmentRequest,
                                    e3ps.dms.entity.KETRequestPartList,
                                    e3ps.dms.entity.KETCarYearlyAmount,
                                    e3ps.dms.entity.KETRequestPartLink,
                                    e3ps.dms.entity.KETPartCarLink,
                                    e3ps.project.outputtype.ModelPlan,
                                    e3ps.project.E3PSProject,
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
                                    e3ps.common.content.*,
                                    java.util.Vector,
                                    java.util.ArrayList,
                                    java.util.StringTokenizer,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.util.DateUtil,
                                    e3ps.wfm.entity.KETWfmApprovalHistory,
                                    e3ps.wfm.entity.KETWfmApprovalMaster,
                                    e3ps.common.service.KETCommonHelper,
                                    wt.fc.WTObject,
                                    e3ps.wfm.util.WorkflowSearchHelper,
                                    e3ps.groupware.company.People,
                                    e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil"%>

<%
    String oid = request.getParameter("oid");

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
    String divCode = "";
    
     KETDevelopmentRequest dr = null;
     Vector vec = null;
     ArrayList<String[]> approvalLine = new ArrayList<String[]>();

    if(oid!=null){
        if(oid.equals("0000")){

        }else{
            //KETDevelopmentRequest oid로 해당 객체의 정보를 화면에 나타낸다.
            dr = (KETDevelopmentRequest)CommonUtil.getObject(oid);
            reqNo = dr.getNumber();
            creatorName = StringUtil.checkNull(dr.getIterationInfo().getCreator().getFullName());
            
            if(CommonUtil.isMember("전자사업부") && !CommonUtil.getUserDeptNameFromSession().startsWith("전장IT")){
                divCode = "ER";
              }else{
                divCode = "CA";
              }

            lifeCycleState = dr.getLifeCycleState().getDisplay();
            stateState = dr.getLifeCycleState().toString();

            developmentStep = StringUtil.checkNull(dr.getDevelopmentStep());
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
            /**************
            * 결재라인 시작
            ************/
            KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
            KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
            KETWfmApprovalMaster master = null;
            Object temp = new Object();
            WTObject targetObj;

            targetObj = KETCommonHelper.service.getPBO((WTObject)CommonUtil.getObject(oid));
            if(targetObj!=null)master = (KETWfmApprovalMaster)WorkflowSearchHelper.manager.getMaster(targetObj);

            if(master!=null){

                vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

                for(int i=0; i<vec.size()-1; i++){
                    for(int j=i+1; j<vec.size(); j++){
                        history1 = (KETWfmApprovalHistory)vec.get(i);
                        history2 = (KETWfmApprovalHistory)vec.get(j);
                        if(history1.getSeqNum() > history2.getSeqNum()){
                            temp = vec.get(i);
                            vec.set(i , vec.get(j));
                            vec.set(j , temp);
                        }
                    }
                }
            }

            if( vec != null )
            {
                for( int i = 0; i < vec.size(); i++ )
                {
                    KETWfmApprovalHistory history = (KETWfmApprovalHistory)vec.get(i);
                    String[] app = new String[3];
                    if("의뢰접수".equals(StringUtil.checkNull(history.getActivityName()))) {
                          break;
                     }

                    People people = PeopleHelper.manager.getPeople(history.getOwner().getName());
                    app[0] = ParamUtil.checkStrParameter(people.getDuty(),"&nbsp;");
                    app[1] = ParamUtil.checkStrParameter(history.getOwner().getFullName(),"&nbsp;");
                    app[2] = ParamUtil.checkStrParameter(DateUtil.getTimeFormat(history.getCompletedDate(),"yyyy-MM-dd"),"&nbsp;");
                    if(history.getCompletedDate() == null) {
                        app[1] = "&nbsp;";
                    }
                    approvalLine.add(i, app);
                }
            }

            /**************
            * 결재라인 끝
            ************/
            if(developmentStep.equals("D")){
                developmentStepStr = messageService.getString("e3ps.message.ket_message","00656");//"개발의뢰";;
            }else if(developmentStep.equals("R")){
                developmentStepStr = "개발검토의뢰";
            }else{
                developmentStepStr = "-";
            }
            reception = StringUtil.checkNull(dr.getReception());
            if(!reception.equals("")){
                WTUser user = (WTUser)CommonUtil.getObject(reception);
                receptionName = user.getFullName();
            }
            projectOID = StringUtil.checkNull(dr.getProjectOID());
            if(!projectOID.equals("")){

                Object obj = CommonUtil.getObject(projectOID);

                if ( obj instanceof E3PSProject ) {

                    E3PSProject project = (E3PSProject)obj;
                    projectNO = project.getPjtNo();
                }else if ( obj instanceof KETDevelopmentRequest ) {
                    KETDevelopmentRequest dr1 = (KETDevelopmentRequest)obj;
                    projectNO = dr1.getNumber();

                }else{
                    projectNO = "";
                }
            }

            divisionCode = StringUtil.checkNull(dr.getDivisionCode());


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
                drRequestDate = "불필요";
            }

            isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
            if(isProposalSubmit.equals("1")){
                proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(),"yyyy-MM-dd");
            }else{
                proposalSubmitDate = "불필요";
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
                for(int i=0;i<productCategorys.length;i++){
                    KETPartClassification partClaz = (KETPartClassification)CommonUtil.getObject(productCategorys[i]);
                    if(i != productCategorys.length - 1){
                        productCategoryCode += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName()+", ";
                    }else{
                        productCategoryCode += partClaz.getParent().getClassKrName() + "/" + partClaz.getClassKrName();              
                    }
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

            KETRequestPartList pl = null;
            QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
      if (r.hasMoreElements()){
          pl = (KETRequestPartList) r.nextElement();
          String partName=pl.getPartName();
      }
         }
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> </title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script language="JavaScript">

var initBody;

function printArea() {
    document.getElementById("prtBtn").style.display = "none";
    window.print();
    self.close();
}

function printWindow() {
    document.getElementById("prtBtn").style.display = "none";

    factory.printing.header = "1 "
    factory.printing.footer = "2 "
    factory.printing.portrait = false
    factory.printing.leftMargin = 10
    factory.printing.topMargin = 10
    factory.printing.rightMargin = 10
    factory.printing.bottomMargin = 10
    factory.printing.Print(false, window)
}

</script>
</head>
<body>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
 <tr>
 <td valign="top" align="right">
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr><td align="right">
     <table id="prtBtn" width="100" border="0" cellspacing="0" cellpadding="0"  >
        <tr>
         <td width="10"><img src="../../portal/images/btn_1.gif"></td>
         <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:printArea();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03126") %><%--프린트--%></a></td>
         <td width="10"><img src="../../portal/images/btn_2.gif"></td>
        </tr>
     </table></td>
 </tr>
 </table>
 <br/>
<table border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
             <tr>
             <td class="tab_btm2"></td>
             </tr>
             </table>
             <table height="90" border="0" cellpadding="0" cellspacing="0" align="right">
              <tr>
                <td rowspan="2" width="25px" class="tdblueM" style="border-color:#779cb4;border-style: solid;border-width: 1px;"><%=messageService.getString("e3ps.message.ket_message", "00755") %><%--결재--%></td>
                <td width="70px" class="tdblueM" style="border-color:#779cb4;border-style: solid;border-width: 1px;"><%=messageService.getString("e3ps.message.ket_message", "01200") %><%--담당--%></td>
                <%
                    for(int ii=1; ii<approvalLine.size(); ii++) {
                %>
                <td width="70px" class="tdblueM" style="border-color:#779cb4;border-style: solid;border-width: 1px;"><%=approvalLine.get(ii)[0]%></td>
                <%
                    }
                if(developmentStep.equals("D")){  %>
                <td width="70px" class="tdblueM" style="border-color:#779cb4;border-style: solid;border-width: 1px;"><%=messageService.getString("e3ps.message.ket_message", "03253") %><%--회장--%></td>
                <%} %>
              </tr>
              <tr>
                <%
                if(approvalLine.size() > 0){
                    for(int ii=0; ii<approvalLine.size(); ii++) {
                %>
                <td class="tdwhiteM"  style="height:68px;" style="border-color:#779cb4;border-style: solid;border-width: 1px;"><%=approvalLine.get(ii)[1]%><br/><font size="1"><%=approvalLine.get(ii)[2]%></font>&nbsp;</td>
                <%
                    }
                    if(developmentStep.equals("D")){  %>
                <td class="tdwhiteM" style="border-color:#779cb4;border-style: solid;border-width: 1px;">&nbsp;</td>
                <%
                    }
                }else{                    
                %>
                <td class="tdwhiteM" style="border-color:#779cb4;border-style: solid;border-width: 1px;">&nbsp;</td>
                <%
                    if(developmentStep.equals("D")){
                %>
                <td class="tdwhiteM" style="border-color:#779cb4;border-style: solid;border-width: 1px;">&nbsp;</td>
                <%
                    }
                }
                %>
              </tr>
            </table>
        </td>
    </tr>
    <tr>
      <td>
          <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
               <td class="font_01"><%=developmentStepStr%>서</td>
           </tr>
         </table>
    </td>
    </tr>
     <tr>
       <td class="head_line"></td>
     </tr>
     <tr>
       <td class="space10"></td>
     </tr>
 </table>
 <table width="780" border="0" cellspacing="0" cellpadding="0">
 <tr>
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
 <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780" class="table_border">
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
 <td colspan="3" class="tdwhiteL0"><%=reqNo%></td>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248") %><%--의뢰담당자--%>
</td>
 <td width="250" class="tdwhiteL"><%=creatorName%></td>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
 <td width="250" class="tdwhiteL0"><%=lifeCycleState%></td>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
 <td width="250" class="tdwhiteL"><%=developmentStepStr%></td>
 <%if(developmentStep.equals("D")){%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00736") %><%--검토프로젝트번호--%></td>
 <td width="250" class="tdwhiteL0"><%=projectNO%>&nbsp;</td>
 <%}else{%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01114") %><%--기검토 의뢰번호--%></td>
 <td width="250" class="tdwhiteL0"><%=projectNO%>&nbsp;</td>
 <%}%>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02261") %><%--의뢰처--%></td>
 <td width="250" class="tdwhiteL"><%=requestBuyer%></td>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02262") %><%--의뢰처 담당자--%></td>
 <td width="250" class="tdwhiteL0"><%=requestBuyerManager%>&nbsp;</td>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
 <td width="250" class="tdwhiteL"><%=lastUsingBuyer%></td>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02848") %><%--최종사용처 담당자--%></td>
 <td width="250" class="tdwhiteL0"><%=lastUsingBuyerManager%>&nbsp;</td>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%if(divisionCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
 <td width="250" class="tdwhiteL"><%=repCarType%>&nbsp;</td>

 <%if(developmentStep.equals("R")){%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00616") %><%--개발검토유형--%></td>
 <td width="250" class="tdwhiteL0"><%=devReviewTypeCode%>&nbsp;</td>
 </tr>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02527") %><%--제안서 제출일정--%></td>
 <td width="250" class="tdwhiteL"><%=proposalSubmitDate%>&nbsp;</td>
 <%}%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00650") %><%--개발원가 제출일정--%></td>
 <td width="250" class="tdwhiteL0"><%=costSubmitDate%>&nbsp;</td>
 </tr>
 <tr>
 <%if(developmentStep.equals("D")){%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%></td>
 <%}else{%>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00623") %><%--개발검토제품명--%></td>
 <%}%>
 <td colspan="3" class="tdwhiteL0"><%=devProductName%>&nbsp;</td>
 </tr>
 <%if(developmentStep.equals("D")){%>
 <tr>
 <td width="140" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
 <td colspan="3" class="tdwhiteL0"><%=devReviewTypeCode%>&nbsp;</td>
 </tr>
 <%}%>
 <tr>
 <td width="140" class="tdblueL"><%if(developmentStep.equals("D")){%><%=messageService.getString("e3ps.message.ket_message", "00668") %><%--개발착수내용--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "00612") %><%--개발검토내용--%><%}%></td>
 <td colspan="3" class="tdwhiteL0">
     <table border="0" cellspacing="0" cellpadding="0" class="table_border" width="100%">
        <tr>
            <td width="100" class="tdgrayL">1.<%
            if (divCode.equals("CA")) {
            %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%> <%
                } else {
            %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
                }
            %></td>
            <td width="*" class="tdgrayL"><%=attribute2%></td>
        </tr>
        <tr>
            <td class="tdgrayL">2.<%=messageService.getString("e3ps.message.ket_message", "02467") %><!-- 적용부위 --></td></td>
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
            <td class="tdgrayL">9.<%=messageService.getString("e3ps.message.ket_message", "01136") %><!-- 기타 --></td>
            <td width="*" class="tdgrayL" style="white-space:pre-line;"><%=attribute9%></td>
        </tr>
    </table>  
 </td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space10"></td>
 </tr>
 </table>
 <table width="780" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
 <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02874") %><%--추가요청사항 및 첨부Data--%></td>
 <td align="right">&nbsp;</td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780" class="table_border">
 <tr>
 <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02873") %><%--추가요청사항--%></td>
 <td width="660" class="tdwhiteL0"><%=salesAdditionalRequirement%>&nbsp;</td>
 </tr>
 <tr>
 <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
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
                             urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
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
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space10"></td>
 </tr>
 </table>
 <table width="780" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
 <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609") %><%--제품정보--%></td>
 <td align="right">&nbsp;</td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table width="780" border="0" cellpadding="0" cellspacing="0"  class="table_border">
 <tr>
     <td width="170" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02591") %><%--제품명--%></td>
     <td width="120" rowspan="2" class="tdblueM"><%if(divisionCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
     <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02467") %><%--적용부위--%></td>
     <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03208") %><%--현적용품--%></td>
     <td width="45" rowspan="2" class="tdblueM">U/S</td>
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
         <td width="170" class="tdwhiteM"><%=StringUtil.checkNull(pl.getPartName())%></td>
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

       while(r1.hasMoreElements()){
           cy = (KETCarYearlyAmount) r1.nextElement();

           d = cy.getYearAmount1()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y1 = y1 + d;
           d = cy.getYearAmount2()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y2 = y2 + d;
           d = cy.getYearAmount3()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y3 = y3 + d;
           d = cy.getYearAmount4()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y4 = y4 + d;
           d = cy.getYearAmount5()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y5 = y5 + d;
           d = cy.getYearAmount6()*cy.getApplyedUsage()*cy.getOptionRate()/100;
           y6 = y6 + d;

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

                 }

                 carTypeCode = carTypeCode + "," + cy.getCarTypeCode();

       }
       carTypeCode = carTypeCode.substring(1);
       sum = y1 + y2 + y3 + y4 + y5 + y6;
%>
           <td width="120" class="tdwhiteM"><%=carTypeCode%></td>
             <td width="70" class="tdwhiteM"><%=StringUtil.checkNull(pl.getAppliedRegion())%>&nbsp;</td>
             <td width="60" class="tdwhiteM"><%=StringUtil.checkNull(pl.getCurrentApplyedPart())%>&nbsp;</td>
             <td width="45" class="tdwhiteM"><%=usg%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y1, "", "###,###")%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y2, "", "###,###")%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y3, "", "###,###")%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y4, "", "###,###")%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y5, "", "###,###")%></td>
             <td width="45" class="tdwhiteR"><%=StringUtil.getDouble(y6, "", "###,###")%></td>
             <td width="45" class="tdwhiteR0"><%=StringUtil.getDouble(sum, "", "###,###")%></td>
         </tr>
<%
         }
%>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
<table width="780" border="0" cellpadding="0" cellspacing="0" class="table_border">
 <tr>
     <td width="157" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02591") %><%--제품명--%></td>
     <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02991") %><%--포장사양--%></td>
     <td width="123" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01168") %><%--납입처--%></td>
     <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "00847") %><%--고객예상가(원)--%></td>
     <td width="100" class="tdblueM" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02968") %><%--판매목표가(원)--%></td>
     <td width="100" class="tdblueM" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "01390") %><%--목표수익률(%)--%></td>
     <td width="100" class="tdblueM0" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "01392") %><%--목표투자비(천원)--%></td>
</tr>
<tr>
    <td width="100" class="tdblueM">초도</td>
    <td width="100" class="tdblueM">평균</td>
</tr>
<%
         r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
     while(r.hasMoreElements()){
         pl = (KETRequestPartList) r.nextElement();
       String partName=pl.getPartName();
       String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());
       String sdName = "";
       NumberCode nCd = (NumberCode)CommonUtil.getObject(summitDestinationOid);
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
         <td width="157" class="tdwhiteM"><%=StringUtil.checkNull(pl.getPartName())%></td>
         <td width="100" class="tdwhiteM"><%=ptName%>&nbsp;</td>
         <td width="123" class="tdwhiteM"><%=sdName%>&nbsp;</td>
         <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getBuyerAcceptPrice(), "", "###,###")%>&nbsp;</td>
         <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getKetTargetPrice(), "", "###,###")%>&nbsp;</td>
         <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getTargetEarningRate(), "", "###,###")%>&nbsp;</td>
         <td width="100" class="tdwhiteM"><%=StringUtil.getDouble(pl.getTargetAverageRate(), "", "###,###")%>&nbsp;</td>
         <td width="100" class="tdwhiteM0"><%=StringUtil.getDouble(pl.getTargetInvestmentCost(), "", "###,###")%>&nbsp;</td>
         </tr>
<%
         }
%>

</table>
</td>
</tr>
</table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780"  class="table_border">
    <tr>
      <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02734") %><%--진행일정--%></td>
     <%--  <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02823") %>초도품 제출일정</td>
      <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00228") %>ESIR일정</td>
      <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00261") %>ISIR일정</td>
      <td width="140" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00294") %>KET 양산일정</td>
       --%>
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
      <td width="140" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00831") %><%-- 고객SOP --%></td>
    </tr>
         <tr>
         <td width="140" class="tdwhiteM"><%=initialSampleSummitDate%>&nbsp;</td>
         <td width="140" class="tdwhiteM"><%=eSIRDate%>&nbsp;</td>
         <td width="140" class="tdwhiteM"><%=iSIRDate%>&nbsp;</td>
         <td width="140" class="tdwhiteM"><%=ketMassProductionDate%>&nbsp;</td>
         <td width="140" class="tdwhiteM0"><%=productSaleFirstYear%>&nbsp;</td>
         </tr>
 </table>

 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space10"></td>
 </tr>
 </table>
 
 <table width="780" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
 <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02679") %><%--주요제품 사양--%></td>
 <td align="right">&nbsp;</td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
 <tr>
 <td class="space5"></td>
 </tr>
 </table>
 
<table border="0" cellspacing="0" cellpadding="0" width="100%">
 <tr>
 <td class="tab_btm2"></td>
 </tr>
 </table>
 <table border="0" cellspacing="0" cellpadding="0" width="780" class="table_border">
 <tr>
 <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><!-- 제품구분 --></td>
 <td colspan="3" class="tdwhiteL0"><%=productCategoryCode%>&nbsp;</td>
 </tr>
 <tr>
 <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01147") %><%--기타내용--%></td>
 <td colspan="3" class="tdwhiteL0"><%=etcSpecification%>&nbsp;</td>
 </tr>
 </table>
 </td>
 </tr> 
</table>
</body>
</html>
