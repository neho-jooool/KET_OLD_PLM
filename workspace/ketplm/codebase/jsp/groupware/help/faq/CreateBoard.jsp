<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.util.KETObjectUtil"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.web.ParamUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String menu = request.getParameter("menu");
  String module = request.getParameter("module");
  String subMenu = request.getParameter("subMenu");
  String from = ParamUtil.checkStrParameter(request.getParameter("from"));

    String writeDate = DateUtil.getToDay("yyyy-MM-dd");

    WTUser loginUser = KETObjectUtil.getLoginUser();
    String writer = loginUser.getFullName();
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00238") %><%--FAQ--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src='/plm/jsp/common/content/content.js'></script>
<script src="/plm/jsp/groupware/js/board.js"></script>

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
  function save(){
    var form01 = document.forms[0];

    if ( isNullData(form01.title.value) ) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
      form01.title.focus();
      return;
    }

    // innoditor WebEditor
    // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
    // 두번째 매개변수 => 이노디터 번호
    form01.webEditor.value = fnGetEditorHTMLCode(false, 0);
    form01.webEditorText.value = fnGetEditorText(0);

    if ( isNullData(form01.webEditorText.value) ) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01174") %><%--내용을 입력해 주십시오--%>');
      return;
    }

    showProcessing();
    disabledAllBtn();
    form01.action = "/plm/jsp/groupware/help/faq/ActionBoard.jsp";
    form01.submit();
  }
  
function insertFileLine() {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:deleteFile(this);$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b>"
                  + "<input name='fileDelete' type='hidden' class='chkbox' value=''></a>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
        }
    }
}

//-->
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
//<![CDATA[

// -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
// -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
// -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
var g_arrSetEditorArea = new Array();
g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

//]]>
</script>

<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>

<script type="text/javascript">
//<![CDATA[

// -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

// Skin 재정의
//g_nSkinNumber = 0;

// 크기, 높이 재정의
g_nEditorWidth = 770;
g_nEditorHeight = 480;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->

</head>

<body class="popup-background popup-space">
<form name="frm" method="post" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="save">
<input type="hidden" name="from" value="<%=from %>">

<!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"></textarea>
    <textarea name="webEditorText" rows="0" cols="0" style="display:none"></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 보낼 Form End -->

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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00240") %><%--FAQ 등록--%></td>
                </tr>
                </table>
            </td>
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
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                            <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
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

        <!-- [START] body area -->
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
            <td class="tdwhiteL0" colspan="3">
                <input type="text" class="txt_field" name="title" style="width:98%;" engnum="engnum" onKeyUp="common_CheckStrLength(this, 1333)"  onChange="common_CheckStrLength(this, 1333)" >
            </td>
        </tr>
        <tr>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL"   style="width: 270px;">
                <input type="text" class="txt_fieldRO" name="writer" value="<%=writer %>" style="width:98%;" engnum="engnum" readonly>
            </td>
            <td class="tdblueL"      style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL0"   style="width: 270px;">
                <input type="text" class="txt_fieldRO" name="writeDate" value="<%=writeDate %>" style="width:98%;" engnum="engnum" readonly>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01755") %><%--상위 게시 여부--%></td>
            <td class="tdwhiteL0" colspan="3">
              <input name="preferred" type="checkbox" class="Checkbox" value="1"> <%=messageService.getString("e3ps.message.ket_message", "01754") %><%--상위 게시--%>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
            <td class="tdwhiteL0" colspan="3" style="padding:5px">
                <input type="hidden" name="deleteFiles">
                <table style="width: 645px;">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                <table style="width: 645px;" id="fileTable">
                <%-- <tr>
                    <td colspan="2">
                        <table>
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                <a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %>추가</a>
                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td style="width:5px;">&nbsp;</td>
                                <td>
                                    <table>
                                        <tr>
                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                <a href="javascript:deleteFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a>
                                            </td>
                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr> --%>
                <tr class="headerLock3">
                      <td>
                              <tr>
                                  <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;""><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                  <td width="500" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                              </tr>
                      </td>
                  </tr>
                <!-- <tr align="center" bgcolor="#ffffff" id="fileTableRow" style="display:none">
                  <td align="left" valign="middle" style="width: 20px; height: 22px;">
                     <input type="checkbox" name="fileDelete">
                  </td>
                  <td>
                     <input type="file" name="filePath" onChange='isValidSecondarySize(this)' id=input style="width:99%" onkeyDown="this.blur()" class="txt_field" size="92">
                    </td>
                </tr> -->
                </table>
                <table style="width: 645px;">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
          <td class="tdwhiteL0" colspan="4">
              <!-- Editor Area Container : Start -->
              <div id="EDITOR_AREA_CONTAINER"></div>
              <!-- Editor Area Container : End -->
          </td>
        </tr>
      </table>
      <!-- [END] body area -->
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
