<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.content.ApplicationData"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.common.util.KETObjectUtil"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.groupware.board.QnaBoard"%>
<%@page import="e3ps.groupware.board.QnaBoardData"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String poid = ParamUtil.checkStrParameter(request.getParameter("poid"));
  String from = ParamUtil.checkStrParameter(request.getParameter("from"));
  QnaBoard qboard = (QnaBoard) CommonUtil.getObject(poid);
  QnaBoardData qdata = new QnaBoardData(qboard, 1);

  ContentHolder qholder = ContentHelper.service.getContents(qboard);
  Vector qvec = ContentHelper.getContentList(qholder);

  String state = QnaBoardData.STATE_COMPLETED;
  String stateStr = QnaBoardData.stateToString(state);
  String writeDate = DateUtil.getToDay("yyyy-MM-dd");

  WTUser loginUser = KETObjectUtil.getLoginUser();
  String writer = loginUser.getFullName();
//  String department = KETObjectUtil.getCurrentUserGroup();
  String department = new PeopleData(loginUser).departmentName;

  Map<String, Object> parameter = new HashMap<String, Object>();
  parameter.put("locale",   messageService.getLocale());
  parameter.put("codeType", "QNASTATUS");
  List<Map<String, Object>> stateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--Q&amp;A--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<script type="text/javascript" src="/plm/portal/js/script.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/jsp/common/content/content.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>

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
    form01.action = "/plm/servlet/e3ps/ManageQnaBoardServlet?command=create&from=<%=from %>";
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
</SCRIPT>

<!-- for question -->
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
#divViewQ            {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;text-align:left;overflow-x:auto;word-wrap:break-word;}
#divViewQ TD         {font-family:굴림;font-size:10pt;color:#000000;line-height:normal;}

#divViewQ P          {margin-top:2px;margin-bottom:2px;}/* IE 에서 줄바꿈(엔터친 경우) style 설정 */
#divViewQ BLOCKQUOTE {margin-top:2px;margin-bottom:2px;}/* IE 에서 들여쓰기 style 설정 */
/*]]>*/
</style>
<!-- End : 이노디터에서 글 작성시와 동일한 style 설정 처리부분 -->

<script type="text/javascript">
//<![CDATA[
  function fnSetEditorHTML() {

    // 이노디터에서 작성된 내용을 전달
    var strContent = document.forms[0].webEditorQ.value;

    var objView = document.getElementById("divViewQ");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.forms[0].hdnBackgroundColorQ.value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.forms[0].hdnBackgroundImageQ.value;
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

    var strBackgroundRepeat = document.forms[0].hdnBackgroundRepeatQ.value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
  }
//]]>
</script>

</head>

<body class="popup-background popup-space" onload="javascript:fnSetEditorHTML();">
<form name="boardform" method="post" enctype="multipart/form-data">
<input type="hidden" name="command" value="create">
<input type="hidden" name="poid" value="<%=poid %>">
<input type="hidden" name="from" value="<%=from %>">
<input type="hidden" name="title" value="<%=qdata.getTitle() %>" />

<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditorQ" rows="0" cols="0" style="display:none"><%=qdata.getWebEditor() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColorQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImageQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeatQ" value="" /><!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->

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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00424") %><%--Q&amp;A 답변 등록--%></td>
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

        <!-- [START] 질문 보기 영역 -->
        <table style="width: 100%;">
        <tr>
            <td class="font_03"><img src="/plm/portal/images/icon_4.png" /> Question</td>
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
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
            <td class="tdwhiteL0" style="width: 660px;" colspan="3">
                <%=qdata.getTitle() %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%><span class="red">*</span></td>
            <td class="tdwhiteL" style="width: 270px;">
                <%=qdata.getClassificationStr() %>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <%=qdata.getWriteDate() %>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <%=qdata.getWriter() %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
            <td class="tdwhiteL0">
                <%=qdata.getDepartment() %>
            </td>
        </tr>
        <%
        if ( qdata.isAttach() ) {
        %>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="3" >
        <%
          for ( int i = 0 ; i < qvec.size() ; i++ ) {
              ApplicationData appData = (ApplicationData)qvec.get(i);
              String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
              //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(qholder)+"&adOid="+appDataOid;
              String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(appData) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
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
            <td class="tdwhiteL0" colspan="4">
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
            <td class="font_03"><img src="/plm/portal/images/icon_4.png" /> Answer</td>
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
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL" style="width: 270px;">
              <select name="state" id="state" class="fm_jmp" style="width:98%">
                <%
                for ( int i=0; i<stateNumCode.size(); i++ ) {
                  Object code = stateNumCode.get(i).get("code");
                  Object value = stateNumCode.get(i).get("value");
                  if (QnaBoardData.STATE_REGISTERED.equals(code) == false) {
                %>
                    <option value="<%=code %>" <%=("PROCS".equals(code))?"selected":"" %>><%=value%></option>
                <%
                  }
                }
                %>
              </select>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <%=writeDate %>
                <input type="hidden" name="writeDate" value="<%=writeDate %>" />
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <%=writer %>
                <input type="hidden" name="writer" value="<%=writer %>" />
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
            <td class="tdwhiteL0">
                <%=department %>
                <input type="hidden" name="department" value="<%=department %>" />
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
                <table style="width: 500px;">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                <table style="width: 500px;" id="fileTable">
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
                            <tr>
                            <td class="space5"></td>
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
                <table style="width: 500px;">
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
        <!-- [END] 답변 입력 영역 -->
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
