<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.content.ApplicationData"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="wt.org.WTUser"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.groupware.board.ImproveBoard"%>
<%@page import="e3ps.groupware.board.ImproveBoardData"%>
<%@page import="e3ps.common.code.NumberCode"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String oid = ParamUtil.checkStrParameter(request.getParameter("oid"));
  String from = ParamUtil.checkStrParameter(request.getParameter("from"));
  ImproveBoard board = (ImproveBoard) CommonUtil.getObject(oid);
  ImproveBoardData data = new ImproveBoardData(board, 1);
  data.increaseReadCount();

  ContentHolder holder = ContentHelper.service.getContents(board);
  Vector vec = ContentHelper.getContentList(holder);

  ImproveBoard qboard = (ImproveBoard) board.getParent();
  ImproveBoardData qdata = new ImproveBoardData(qboard, 1);
  ContentHolder qholder = ContentHelper.service.getContents(qboard);
  Vector qvec = ContentHelper.getContentList(qholder);

  String ownerOid = CommonUtil.getOIDString((WTUser)board.getOwner().getPrincipal());
  String loginOid = CommonUtil.getOIDString((WTUser)wt.session.SessionHelper.manager.getPrincipal());
  boolean isOwner = (loginOid.equals(ownerOid));
  boolean isAdmin = CommonUtil.isAdmin();
  boolean isBizAdmin = CommonUtil.isBizAdmin();

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--Q&amp;A--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
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

<script type="text/javascript">
<!--
  function deleteNotice() {
    var form01 = document.forms[0];
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "01707") %><%--삭제하시겠습니까?--%>") ) {
      showProcessing();
      disabledAllBtn();
      
      $.ajax({
          type       : "POST",
          url        : "/plm/servlet/e3ps/UpgradeBoardServlet?command=delete",
          data       : $("#frm").serialize(),
          success    : function(data){
                           opener.search();
                           window.close();
          },
          error    : function(xhr, status, error){
                       alert('삭제에 실패하였습니다');
                       
          }
      }); 
      
     /*  form01.command.value = "delete";
      form01.action="/plm/servlet/e3ps/UpgradeBoardServlet";
      form01.submit(); */
    }
    return;
  }

  function go_to(url) {
    showProcessing();
    disabledAllBtn();
    window.location=url;
  }

  function openView(url) {
      newWindow = openWindow( url, '', 'full', 'full');
  }
//-->
</script>

<!-- 글 내용을 보여줄 외곽 style 설정 : 사이트 특성에 맞게 수정 가능 및 제거 가능 -->
<!-- 이노디터의 직접적인 설정과는 무관하며, View 영역을 표시하기 위한 Style 설정임 -->
<style type="text/css">
/*<![CDATA[*/
.outline    {border:0px solid #c4cad1;padding:5px;}
/*]]>*/
</style>

<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 (Question) -->
<!--
    이노디터 기본글꼴을 굴림, 기본글크기를 10pt 로 설정하였다면
    View 페이지에서도 작성된 곳의 보여주는 부분을 동일하게 설정
-->
<style type="text/css">
/*<![CDATA[*/
#divViewQ            {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;text-align:left;overflow-x:auto;word-wrap:break-word;}
#divViewQ TD         {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;}

#divViewQ P          {margin-top:2px;margin-bottom:2px;}/* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divViewQ BLOCKQUOTE {margin-top:2px;margin-bottom:2px;}/* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 (Question) -->

<!-- Start : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 (Answer) -->
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
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 (Answer) -->

<script type="text/javascript">
//<![CDATA[
  function fnSetEditorHTML() {
    fnSetEditorHTMLQ();
    fnSetEditorHTMLA();
  }
  function fnSetEditorHTMLQ() {

    // 이노디터에서 작성된 내용을 전달
    var strContentQ = document.forms[0].webEditorQ.value;

    var objViewQ = document.getElementById("divViewQ");
    objViewQ.innerHTML = strContentQ;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColorQ = document.forms[0].hdnBackgroundColorQ.value;
    if("" != strBackgroundColorQ)
    {
        objViewQ.style.backgroundColor = strBackgroundColorQ;
    }

    var strBackgroundImageQ = document.forms[0].hdnBackgroundImageQ.value;
    if("" != strBackgroundImageQ)
    {
        var strCopyBackgroundImageQ = strBackgroundImageQ.toUpperCase();

        if("none" == strCopyBackgroundImageQ)
        {
            objViewQ.style.backgroundImage = strBackgroundImageQ;
        }
        else
        {
            objViewQ.style.backgroundImage = "url(" + strBackgroundImageQ + ")";
        }
    }

    var strBackgroundRepeatQ = document.forms[0].hdnBackgroundRepeatQ.value;
    if("" != strBackgroundRepeatQ)
    {
        objViewQ.style.backgroundRepeat = strBackgroundRepeatQ;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
  }
  function fnSetEditorHTMLA() {

    // 이노디터에서 작성된 내용을 전달
    var strContent = document.forms[0].webEditor.value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.forms[0].hdnBackgroundColor.value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.forms[0].hdnBackgroundImage.value;
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

    var strBackgroundRepeat = document.forms[0].hdnBackgroundRepeat.value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
  }
  
  $(document).ready(function(){
      var division = '<%=data.getDivision()%>';
      
      if(division == 'SYSERR'){
          $("#causeTR").show();
          $("#handleResultTR").show();
      }
    });
  
//]]>
</script>

</head>
<body class="popup-background popup-space" onload="javascript:fnSetEditorHTML();">

<form id="boardform" name="boardform" method="post">
<!-- Hidden Value -->
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=data.getOid() %>">
<input type="hidden" name="from" value="<%=from %>">

<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start (Question) -->
    <textarea name="webEditorQ" rows="0" cols="0" style="display:none"><%=qdata.getWebEditor() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColorQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImageQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeatQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End (Question) -->

<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start (Answer) -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=data.getWebEditor() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End (Answer) -->

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">

        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;">
                <tr>
                    <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00425") %><%--Q&amp;A 답변 상세조회--%></td>
                </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="space10"></td>
        </tr>
        </table>
        <!-- [END] title & position -->

        <!-- [START] button -->
        <table style="width: 100%;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <%if ( isAdmin == true ){%>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:go_to('/plm/jsp/groupware/help/improve/UpdateImproveBoard_a.jsp?oid=<%=data.getOid() %>&from=<%=from %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02786") %><%--처리수정--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 5px;">&nbsp;</td>
                    <%} %>
                    <%if ( isAdmin == true ){%>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:deleteNotice();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%} %>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
<%
  if ("main".equals(from)) {
%>
                                <a href="javascript:closeWindow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
<%
  }
  else if ("list".equals(from)) {
%>
                                <a href="javascript:closeWindow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a>
<%
  }
  else {
%>
                                <a href="javascript:closeWindow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a>
<%
  }
%>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
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
        <!-- [END] button -->

        <!-- [START] 질문 보기 영역 -->
        <table style="width: 100%;">
        <tr>
            <td class="font_03"><img src="/plm/portal/images/icon_4.png" /> <%=messageService.getString("e3ps.message.ket_message", "02190") %><%--요청--%></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <%=qdata.getTitle() %>
            </td>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>

            <td class="tdwhiteL" style="width: 270px;">
                <%
                Map<String, Object> parameter = new HashMap<String, Object>();
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "IMPROVETYPE");
                List<Map<String, Object>> classification1NumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                NumberCode numberCode = NumberCodeHelper.manager.getNumberCode("IMPROVETYPE", qdata.getClassification());
                NumberCode parentNumberCode = null;
                if(numberCode != null){
                	parentNumberCode = numberCode.getParent();
                }
                String parentCodeName = null;
                String parentCode = null;
                if (parentNumberCode != null) {
                	parentCodeName = parentNumberCode.getName() ;
                	parentCode = parentNumberCode.getCode();
                	out.println(parentCodeName);
                }
                %>
             </td>
             <td class="tdwhiteL0" style="width: 270px;" colspan="2">
                <%=qdata.getClassificationStr() %>
            </td>
        </tr>
        <tr>

			<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <%=qdata.getWriter() %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
            <td class="tdwhiteL">
                <%=qdata.getDepartment() %>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <%=qdata.getWriteDate() %>
            </td>

        </tr>
        <%
        if ( qdata.isAttach() ) {
        %>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="6">
        <%
            for ( int i = 0 ; i < qvec.size() ; i++ ) {
                ApplicationData appData = (ApplicationData)qvec.get(i);
                String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(qholder)+"&adOid="+appDataOid;
                String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(qholder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
        %>
                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;
                <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(i<qvec.size()){%><br><%} %>
        <%
            }
        %>
            </td>
        </tr>
        <%
        }
        %>
        <tr>
            <td class="tdwhiteL0" colspan="6">
                <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                <div id="divViewQ" style="width:750px;" class="outline"></div>
                <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
            </td>
        </tr>
        </table>
        <!-- [END] 질문 보기 영역 -->

        <!-- [START] 답변 입력 영역 -->
        <table style="width: 100%;">
        <tr>
            <td class="space20"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="font_03"><img src="/plm/portal/images/icon_4.png" /> <%=messageService.getString("e3ps.message.ket_message", "02775") %><%--처리--%></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
        	<td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
            <td class="tdwhiteL" >
                <%=data.getDivisionStr() %>
            </td>

            <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0" colspan="3">
                <%=data.getStateStr() %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
            <td class="tdwhiteL" >
                <%=data.getManager()  %>
            </td>
<%--             <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02425") %>작성부서</td>
            <td class="tdwhiteL0" >
                <%=data.getDepartment() %>
            </td> --%>
            <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL0" colspan="3">
                <%=data.getWriteDate() %>
            </td>
        </tr>

        <tr id="causeTR" style="display:none;">
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "03434") %><%--원인--%></td>
            <td colspan="6"   style="width: 270px;" class="tdwhiteL0"><%=data.getCause() %></td>
        </tr>

        <tr id="handleResultTR" style="display:none;">
            <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "03455") %><%--처리결과--%></td>
            <td colspan="6"  style="width: 270px;" class="tdwhiteL0"><%=data.getHandleResult() %></td>
        </tr>


        <%
        if ( data.isAttach() ) {
        %>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="6" >
        <%
            for ( int i = 0 ; i < vec.size() ; i++ ) {
              ApplicationData appData = (ApplicationData)vec.get(i);
              String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
              //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
              String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
        %>
                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;
                <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(i<vec.size()){%><br><%} %>
        <%
            }
        %>
            </td>
        </tr>
        <%
        }
        %>
        <tr>
            <td class="tdwhiteL0" colspan="6">
                <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                <div id="divView" style="width:750px;" class="outline"></div>
                <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
            </td>
        </tr>
        </table>
        <!-- [END] 답변 조회 영역 -->
    </td>
</tr>
<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 100%;">
        <tr>
            <td style="width:10px;">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 100%; height: 24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
<!-- [END] copyright -->
</table>
</form>
</body>
</html>
