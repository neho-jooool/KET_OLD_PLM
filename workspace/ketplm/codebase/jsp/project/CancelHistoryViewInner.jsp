<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="e3ps.project.cancel.CancelProject"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Hashtable"%>

<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.ProjectChangeStop"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.CostInfo"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");
    String pcsOid = request.getParameter("pcsOid");
    String isIframe = StringUtil.checkNull(request.getParameter("isIframe"));
    

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
    CancelProject pcs = (CancelProject)CommonUtil.getObject(pcsOid);
    String changeType = "";
    String changeDetail = "";
    String changeDate = "";

    String webEditor = "";
    String webEditorText = "";
    String stopChageType = "";
    String targetCost = "";
    String realCost = "";

    if(pcs.getCancelType() != null) { changeType = pcs.getCancelType(); }

		FormUploader fileUploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
		Hashtable param = fileUploader.getFormParameters();
		
		command = (String)param.get("command");
    if("saveRealCost".equals(command)){
	   
	   pcs.setRealCost(StringUtils.remove((String)param.get("realCost"), ","));
	   pcs = (CancelProject)PersistenceHelper.manager.save(pcs);
    }
    
    if ( pcs.getCancelReasonType() != null ) {
        Map<String, Object> numCodeValue = new HashMap<String, Object>();
        String change = "PROJECTCANCELTYPE";

        numCodeValue = NumberCodeUtil.getNumberCodeValueMap(change, pcs.getCancelReasonType().toString(), messageService.getLocale().toString());

        stopChageType = numCodeValue.get("value").toString();
    }
    
    targetCost = StringUtils.defaultIfEmpty(pcs.getTargetCost(), "0") ;
    realCost = StringUtils.defaultIfEmpty(pcs.getRealCost(), "0") ; 
    
    targetCost = String.format("%,d", Long.parseLong(targetCost));
    realCost = String.format("%,d", Long.parseLong(realCost));
    
    wt.org.WTUser wtuser = (wt.org.WTUser) wt.session.SessionHelper.manager.getPrincipal();
    wt.org.WTUser Salesuser = projectData.sales;
    boolean isSalesRole = false;
    if(Salesuser != null){
        if(Salesuser.getName().equals(wtuser.getName()))  {
            isSalesRole = true;//영업 Role
        }
    }

    if(pcs.getCancelDate() != null) { changeDate = DateUtil.getDateString(pcs.getCancelDate(), "D"); }

    if(pcs.getWebEditor() != null) { webEditor = (String)pcs.getWebEditor(); }
    if(pcs.getWebEditorText() != null) { webEditorText = (String)pcs.getWebEditorText(); }
    
    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
    
    Long totalExpense = 0L; //총집행가
    
    String totalExpenceStr = "0";
    
    while (rtCost.hasMoreElements()) {
	   Object[] obj = (Object[]) rtCost.nextElement();
       CostInfo costInfo = (CostInfo) obj[0];
       if (costInfo.getOrderInvest() != null) {
	       Hashtable datas = ProductPrice.getPrice(costInfo.getOrderInvest());
	       Long totalExpenseObj = (Long) datas.get(ProductPrice.TOTALEXPENSE);
	       
	       if (totalExpenseObj != null){
		       totalExpense += totalExpenseObj.longValue(); //총집행가
		       
           }
       }else{
           if (costInfo.getTargetCost() != null && costInfo.getTargetCost().length() > 0){
               totalExpense = totalExpense + Long.parseLong(costInfo.getTargetCost().replaceAll(",", ""));
           }
       }
    }
    
    totalExpenceStr = String.format("%,d", totalExpense);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01492") %><%--변경 사유--%></title>
<base target="_self">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<style type="text/css">
body {
    margin-left: <%= ("true".equals(isIframe))?"0px":"10px"%>;
    margin-top: 0px;
    margin-right: <%= ("true".equals(isIframe))?"0px":"10px"%>;
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
    line-height: 22px;
}
</style>
<script type="text/javascript" src="/plm/portal/js/script.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/select.js"></script>
<script type="text/javascript" src="/plm/portal/js/table.js"></script>
<script type="text/javascript" src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript" src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript" src="/plm/portal/js/checkbox2.js"></script>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<!-- 글 내용을 보여줄 외곽 style 설정 : 사이트 특성에 맞게 수정 가능 및 제거 가능 -->
<!-- 이노디터의 직접적인 설정과는 무관하며, View 영역을 표시하기 위한 Style 설정임 -->
<style type="text/css">
/*<![CDATA[*/
.outline    {border:0px solid #c4cad1;padding:5px;}
/*]]>*/
</style>

<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->
<!--
    이노디터 기본글꼴을 굴림, 기본글크기를 10pt 로 설정하였다면
    View 페이지에서도 작성된 곳의 보여주는 부분을 동일하게 설정
-->
<style type="text/css">
/*<![CDATA[*/
#divView            {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;text-align:left;overflow-x:auto;word-wrap:break-word;}
#divView TD         {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;}

#divView P          {margin-top:2px;margin-bottom:2px;}/* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divView BLOCKQUOTE {margin-top:2px;margin-bottom:2px;}/* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->

<script type="text/javascript">
//<![CDATA[
function fnLoadContent () {
	if(document.forms[0].command.value == 'saveRealCost'){
		alert("수정되었습니다.");
		document.forms[0].command.value = "";
	}
	
    // 이노디터에서 작성된 내용을 전달
    var strContent = document.frm["webEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.frm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.frm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.frm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
}

function saveRealCost(){
	if(document.forms[0].realCost.value == ""){
        alert("회수비 실적을 입력하여주십시오");
        document.forms[0].targetCost.focus();
        return;
    }
    
    if(!onlyNumber(document.forms[0].realCost.value)){
        alert("회수비 실적은 숫자를 입력하셔야합니다.");
        document.forms[0].realCost.focus();
        return;
    }
    if(confirm("회수비 실적을 수정하시겠습니까?")){
    	document.forms[0].command.value = 'saveRealCost';
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
    
}

function onlyNumber(num){
    num = String(num).replace(/^\s+|\s+$/g, "");
    var regex = /^[-]?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
    
    if( regex.test(num) ){
        num = num.replace(/,/g, "");

        return isNaN(num) ? false : true;

    }else{ return false;  }
}

//]]>
</script>
</head>

<body onload="javascript:fnLoadContent();">
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<form id="frm" name="frm" method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>" />
<input type="hidden" name="command" value="<%=command%>"/>

<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=webEditor%></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->

<table style="width: 100%">
<tr>
    <td valign="top">
<!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->
        <table style="width: 100%; <%= ("true".equals(isIframe))?"display:none":""%>">
        <tr>
            <td>
                <table style="width: 100%">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                     <table style="height: 28px;">
                         <tr>
                          <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                          <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01499") %><%--변경 사유 상세 보기--%></td>
                          <td style="width: 10px"></td>
                         </tr>
                     </table>
                    </td>
                    <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
                <table style="width: 100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>

<!--         <table style="width: 100%" > -->
<!--         <tr> -->
<!--             <td align="right"> -->
<!--                 <table > -->
<!--                 <tr> -->
<!--                     <td>&nbsp;</td> -->
<!--                     <td> -->
<!--                         <table > -->
<!--                         <tr> -->
<!--                              <td style="width: 10px"><img src="/plm/portal/images/btn_1.gif"></td> -->
<!--                         <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:history.back();" class="btn_blue">뒤로</a></td> -->
<!--                         <td style="width: 10px"><img src="/plm/portal/images/btn_2.gif"></td> -->
<!--                    </tr> -->
<!--                    </table> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 </table> -->
<!--             </td> -->
<!--         </tr> -->
<!--         </table> -->
<!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->
        <table style="width: 100%">
        <tr>
            <td>
            <!------------------------------ 본문 Start //------------------------------>
                <table style="width: 100%">
                <div class="sub-title-02 b-space5 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>취소이력
                </div>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr><td class="tab_btm2"></td></tr>
                </table>
                
                <table style="width: 100%">
                <colgroup>
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
                </colgroup>
                <tr>
                    <td class="tdblueL">취소사유</td>
                    <td class="tdwhiteL0"><%=stopChageType%>&nbsp;</td>
                    <td class="tdblueL">취소일</td>
                    <td class="tdwhiteL0"><%=changeDate%>&nbsp;</td>
                </tr>
                <tr>
                    <td class="tdblueL">목표 회수비</td>
                    <td class="tdwhiteL0"><%=targetCost%>&nbsp;</td>
                    <td class="tdblueL">집행 투자비(원)</td>
                    <td class="tdwhiteL0"><%=totalExpenceStr%>&nbsp;</td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                    <td class="tdwhiteM0" colspan="3" style="padding:5px">
                        <table style="width: 100%;">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                        </table>
                        <table class="table_border" style="width: 100%;">
                        <tr>
                             <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                        </tr>
                        <tr>
                            <td class="tdwhiteL0">
                            <%
                                Vector contents = new Vector();
                                contents = ContentUtil.getSecondaryContents(pcs);
                                if ( contents.size() > 0 ) {
                            %>
                            <%
                                    for ( int j = 0 ; j < contents.size() ; j++ ) {
                                        ContentInfo info = (ContentInfo)contents.get(j);
                            %>
                                        <%=info.getDownURLStr()%>
                                        <%if ( j<(contents.size()-1) ) out.print("<br>");%>
                            <%    } %>
                            <%}%>
                            </td>
                        </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="tdwhiteL0" colspan="4">
<%--                         <textarea name="stopDetil" rows=7 style="width:100%;" readOnly><%=changeDetail%></textarea> --%>
                        <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                        <div id="divView" style="width:100%;" class="outline"></div>
                        <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr><!-- 
<tr>
    <td style="height: 30px;" valign="bottom">
        <table style="width: 100%" >
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px;">
                <iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" style="width: 100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr> -->
</table>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>
</body>
</html>
