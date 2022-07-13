<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%
    String pjtOid = request.getParameter("pjtOid");
    

%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/ReviseAssessSheet.js"></script>


<script type="text/javascript">

function searchTemplateAssessSheetList() {
	TemplateAssessSheet.search();
}

function clearAll() {
    $("#devType").multiselect("uncheckAll");
    $("#division").multiselect("uncheckAll");
    $("#devDiv").multiselect("uncheckAll");
    $("#prodCategory").multiselect("uncheckAll");
    $("#isActive").multiselect("uncheckAll");

    CommonUtil.deleteValue('startDate');
    CommonUtil.deleteValue('endDate');

}

$(document).ready(function(){
    //------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'fstChargeName', 'fstCharge');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    //Die no suggest
    SuggestUtil.bind('DIENO', 'dieNumber');
    //검토 Project No suggest
    SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeText', 'carType');
    //개발산출물 문서 분류 suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
    //기술 문서 분류 suggest
    SuggestUtil.bind('TECHDOCTYPE', 'techDocTypeTxt', 'techDocType');
    //고객사 suggest
    SuggestUtil.bind('CUSTOMER', 'oemTypeTxt', 'oemType');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNumber');
    //제품분류 suggest
    SuggestUtil.bind('PRODUCTTYPE', 'productTypeText', 'productType');
    //ECO no suggest
    SuggestUtil.bind('ECONO', 'ecoNumber', 'ecoOid');
    //ECR no suggest
    SuggestUtil.bind('ECRNO', 'ecrNumber', 'ecrOid');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'customerEventTxt', 'customerEventValue');
    //------------ end suggest binding ------------//
    // default 한달 설정
    $('[name=startDate]').val(predate);
    $('[name=endDate]').val(postdate);
    
//     // Calener field 설정
     CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    

    // multiselect
//     CommonUtil.multiSelect('devType',100);
//     CommonUtil.multiSelect('division',100);
//     CommonUtil.multiSelect('devDiv',100);
//     CommonUtil.multiSelect('prodCategory',100);
    
//     CommonUtil.singleSelect('isActive',100);

       
    //server paging
    ReviseAssessSheet.revisePaigngGrid();
});



</script>
<!-- <table width="780" height="100%" border="0" cellspacing="0" cellpadding="0"> -->
<form name="ReviseAssessSheetSearchForm">
<input type="hidden" name="pjtOid" value="<%=pjtOid%>"/>
<table border="0" cellpadding="0" cellspacing="0" width="820px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="820px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="820px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07155") %><%--개정 이력 조회--%></td>
                      <td width="10"></td>
                    </tr>
                </table>
                </td>
                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="820px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:this.close();" class="btn_blue">
                                                <%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
          </td>
        </tr>
      </table>
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>


            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                        </div>
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   
</form>
