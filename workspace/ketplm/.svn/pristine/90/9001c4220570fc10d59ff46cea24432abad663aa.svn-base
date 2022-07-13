<%@page import="wt.content.ContentItem"%>
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
<%@page import="e3ps.project.CostInfo"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="e3ps.project.sap.ProductPrice"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String command = request.getParameter("command");
    String pcsOid = request.getParameter("pcsOid");

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ProjectChangeStop pcs = (ProjectChangeStop)CommonUtil.getObject(pcsOid);
    String changeType = "";
    String changeDetail = "";
    String changeDate = "";

    String webEditor = "";
    String webEditorText = "";

    if(pcs.getChangeType() != null) { changeType = pcs.getChangeType(); }
    if(pcs.getChangeDetil() != null) { changeDetail = pcs.getChangeDetil(); }
    if(pcs.getChangeDate() != null) { changeDate = DateUtil.getDateString(pcs.getChangeDate(), "D"); }

    if(pcs.getWebEditor() != null) { webEditor = (String)pcs.getWebEditor(); }
    if(pcs.getWebEditorText() != null) { webEditorText = (String)pcs.getWebEditorText(); }


    String stopChageType = "";
    if ( pcs.getChangeStopType() != null ) {
        Map<String, Object> numCodeValue = new HashMap<String, Object>();
        String change = "";
        if ( changeType.equals("중지") || changeType.equals("중지검토") || changeType.equals("중지완료") ) {
            change = "PROJECTSTOPTYPE";
        }
        else if ( changeType.equals("취소") ) {
            change = "PROJECTCANCELTYPE";
        }

        numCodeValue = NumberCodeUtil.getNumberCodeValueMap(change, pcs.getChangeStopType().toString(), messageService.getLocale().toString());

        stopChageType = numCodeValue.get("value").toString();
    }
        
    QueryResult rtCost = ProductProjectHelper.manager.getCostInfo((ProductProject) project);
    
    int totalExpense = 0; //총집행가
    
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
	           totalExpense = totalExpense + Integer.parseInt(costInfo.getTargetCost().replaceAll(",", ""));
	       }
       }
       
       
    }
    
    totalExpenceStr = String.format("%,d", totalExpense);
%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01492") %><%--변경 사유--%></title>
<base target="_self">

<link rel="stylesheet" href="/plm/portal/css/e3ps.css">

<script type="text/javascript" src="/plm/portal/js/script.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/select.js"></script>
<script type="text/javascript" src="/plm/portal/js/table.js"></script>
<script type="text/javascript" src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript" src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript" src="/plm/portal/js/checkbox2.js"></script>

<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
body {
    margin-top:0px;
    margin-left:0px;
    margin-right: 0px;
    margin-bottom: 0px;
}

table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}

img {
    vertical-align: middle;
}

input {
    vertical-align:middle;line-height:22px;
}
</style>

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
//]]>
</script>
</head>

<body onload="javascript:fnLoadContent();">
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
    <form id="frm" name="frm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="oid" value="<%=oid%>" /> <input type="hidden" name="command" />
        <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
        <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=webEditor%></textarea>
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
        <input type="hidden" name="hdnBackgroundColor" value="" />
        <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
        <input type="hidden" name="hdnBackgroundImage" value="" />
        <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
        <input type="hidden" name="hdnBackgroundRepeat" value="" />
        <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
        <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
        <table style="width: 100%">
            <tr>
                <td valign="top">
                    <!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->

                    <!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->
                    <table style="width: 100%">
                        <tr>
                            <td>
                                <!------------------------------ 본문 Start //------------------------------>
                                <table style="width: 100%">
                <div class="sub-title-02 b-space5 float-l">
                <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>중지이력
                </div>
                </table>
                                <table>
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table style="width: 100%">
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "01498")%><%--변경 사유--%></td>
                                        <td class="tdwhiteL" style="width: 240px"><%=stopChageType%>&nbsp;</td>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "02020")%><%--시행일--%></td>
                                        <td class="tdwhiteL" style="width: 240px"><%=changeDate%>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                        <td class="tdwhiteL" style="width: 240px"><%=changeType%>&nbsp;</td>
                                        <td class="tdblueL" style="width: 120px">집행 투자비(원)</td>
                                        <td class="tdwhiteL" style="width: 240px"><%=totalExpenceStr%>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="tdblueL" style="width: 120px"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                                        <td class="tdwhiteM0" colspan="3" style="padding: 5px">
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table class="table_border" style="width: 100%;">
                                                <tr>
                                                    <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                </tr>
                                                <tr>
                                                    <td class="tdwhiteL">
                                                        <%
                                Vector contents = new Vector();
                                contents = ContentUtil.getSecondaryContents(pcs);
                                String urlpath = "";
                                String iconpath = "";
                                
                                
                                if ( contents.size() > 0 ) {
                                    for ( int j = 0 ; j < contents.size() ; j++ ) {
                                	
                                        ContentInfo info = (ContentInfo)contents.get(j);
                                        if( info == null) {
                                            iconpath = "";
                                            urlpath = "";
                                        }else{
                                            ContentItem ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
                                            String appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                            urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + pcsOid + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                            urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
                                            iconpath = info.getIconURLStr();
                                            iconpath = iconpath.substring(iconpath.indexOf("<img"));
                                        }
                                        
                                       
                                        
                            %> <%=iconpath%>&nbsp;<%=urlpath%><br> <%
                        	    }
                                }
                    %>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdwhiteL0" colspan="4">
                                            <%--                         <textarea name="stopDetil" rows=7 style="width:100%;" readOnly><%=changeDetail%></textarea> --%>
                                            <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                                            <div id="divView" style="width: 680px;" class="outline"></div> <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <iframe id="download" name="download" align="center" width="0" height="0" border="0" style="display:none"></iframe>
    </form>
</body>
</html>
