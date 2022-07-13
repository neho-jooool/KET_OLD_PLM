<%@page import="wt.content.ApplicationData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.groupware.board.NotifyData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.groupware.board.Notify"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="/jsp/common/error.jsp"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String oid = ParamUtil.checkStrParameter(request.getParameter("oid"));
  String from = ParamUtil.checkStrParameter(request.getParameter("from"));

  Notify notify = (Notify)CommonUtil.getObject(oid);
  NotifyData data = new NotifyData(notify,1);

  ContentHolder holder = ContentHelper.service.getContents((ContentHolder)notify);
  Vector vec = ContentHelper.getContentList(holder);

  Vector secondaryFiles = data.attacheList;
  int deleteFileCnt = 0;
  for(int i=0; secondaryFiles !=null && i<secondaryFiles.size(); i++) {
    ContentInfo contentInfo = (ContentInfo)secondaryFiles.elementAt(i);
    if((contentInfo.getType()).equals("FILE")) {
      deleteFileCnt++;
    }
  }
%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00893") %><%--공지사항 수정--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<SCRIPT src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT src="/plm/portal/js/org.js"></SCRIPT>
<script src='/plm/jsp/common/content/content.js'></script>
<script src="/plm/portal/js/script.js"></script>
<SCRIPT src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT src="/plm/portal/js/org.js"></SCRIPT>
<script src='/plm/jsp/common/content/content.js'></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/jsp/groupware/js/board.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    /* margin-left: 10px; */
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
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

<script type="text/javascript">
<!--
function modify(){
  var form01 = document.forms[0];

  if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "01954") %><%--수정하시겠습니까?--%>')) return;

  if ( isNullData(form01.title.value) ) {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
    form01.title.focus();
    return;
  }

  var deadline = form01.deadline.value;
  if ( !isNullData(deadline) && !afterNow(deadline) ) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00740") %><%--게시 기한날짜는 과거날짜를 입력할 수 없습니다--%>");
    form01.deadline.focus();
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
  form01.action = "/plm/servlet/e3ps/ManageNotifyServlet?command=update&from=<%=from %>";
  form01.submit();
}

function afterNow(dateStr) {
  var year = parseInt(dateStr.substring(0, 4));
  var month = dateStr.substring(5, 7);
  var day = parseInt(dateStr.substring(8, 10));

  var today = new Date();
  var tYear = today.getFullYear();
  var tMonth = today.getMonth() + 1;
  var tDay = today.getDate();

  if (year < tYear) return false;
  if (month < tMonth) return false;
  if (day < tDay) return false;
  return true;
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
                  + "<input name='fileDelete' type='hidden' class='chkbox'></a>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteL0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
        }
    }
}

//-->
</SCRIPT>

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

<script type="text/javascript">
//<![CDATA[
  function fnSetEditorHTML() {
    var strHTMLCode = document.forms[0].webEditor.value;

    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
  }
//]]>
</script>

</head>

<body onload="javascript:fnSetEditorHTML();" scroll="auto" style="overflow-x:hidden">
<form name="update" method="post" enctype="multipart/form-data">
<!-- Hidden Value -->
<input type="hidden" name="command" value="update">
<input type="hidden" name="oid" value="<%=data.oid%>">
<input type="hidden" name="from" value="<%=from%>">

<!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=data.webEditor %></textarea>
    <textarea name="webEditorText" rows="0" cols="0" style="display:none"><%=data.webEditorText %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 보낼 Form End -->

<table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="height:50px;" valign="top"><table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table style="height:28px;" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td style="width:7px;"></td>
            <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00893") %><%--공지사항 수정--%></td>
          </tr>
        </table></td>
        <td style="width:136px;" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table style="width:780px; height:100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="width:10px;">&nbsp;</td>
          <td valign="top"><table style="width:770px;" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:modify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td>&nbsp;</td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
                <td class="tdwhiteL0">
                  <input type="text" class="txt_field" name="title" value="<%=data.title %>" style="width:100%;" engnum="engnum" onKeyUp="common_CheckStrLength(this, 1333)"  onChange="common_CheckStrLength(this, 1333)" >
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td class="tdblueL" style="width:130px;"><%=messageService.getString("e3ps.message.ket_message", "00742") %><%--게시 기한일자--%></td>
                <td class="tdwhiteL" style="width:240px;">
                  <input id="deadline" name="deadline" value="<%=data.deadline == null ? "" : DateUtil.getDateString( data.deadline, "d" )%>" class="txt_fieldRO" style="width:190px;" engnum="engnum" readonly>
                  <img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(deadline)" style="cursor:hand;">
                  &nbsp;<a href="javascript:clearDate('deadline')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01755") %><%--상위 게시 여부--%></td>
                <td class="tdwhiteL0">
                  <input name="preferred" type="checkbox" class="Checkbox" value="1" <%=(data.isPreferred())?"checked":"" %>> <%=messageService.getString("e3ps.message.ket_message", "01754") %><%--상위 게시--%>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td class="tdwhiteL0">
                  <input type="hidden" name="deleteFiles">
                  <table style="width:100%;" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
                    <%-- <tr>
                      <td></TD>
                      <td>
                        <table>
                          <tr>
                            <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %>추가</a></td>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href="javascript:deleteFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a>
                                  </td>
                                  <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
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
                    <!-- <tr align="left" id="fileTableRow" style="display:none">
                      <td align="left" valign="middle" style="width:20px; height:22px;">
                        <input type="checkbox" name="fileDelete">
                      </td>
                      <td>
                        <input type="file" name="filePath" id="input" onchange='isValidSecondarySize(this)' onkeyDown="this.blur()" style="ime-mode:disabled" class="txt_field" size="83">
                      </td>
                    </tr> -->
                    <%
                    for(int i=0; vec !=null && i<vec.size(); i++) {
                        ApplicationData appData = (ApplicationData)vec.get(i);
                        String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                        //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                        String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                    %>
                    <tr align="center" bgcolor="#ffffff" >
                    <!-- <td align="left" style="width: 20px; height: 22px;"><input type="checkbox" name="fileDelete"></td> -->
                    <td class="tdwhiteM">
                           <a href="#" onclick="javascript:deleteFile(this);$(this).parent().parent().remove();return false;"><b><img src="/plm/portal/images/b-minus.png"></b>
                           <input name='fileDelete' type="hidden" class='chkbox' value="<%=appDataOid%>"></a>                 
                    </td>
                    <td class="tdwhiteL0">                        
                        <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a>
                        <input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" style="width:740px;">
                    </td>
                    <%-- <td align="left"><input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" style="width:740px;">&nbsp;
                        <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a>
                    </td> --%>
                </tr>
                    <%
                    }
                    %>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" style="width:770px;">
              <tr>
                <td>
                  <!-- Editor Area Container : Start -->
                  <div id="EDITOR_AREA_CONTAINER"></div>
                  <!-- Editor Area Container : End -->
                </td>
              </tr>
            </table>
            
            </td>
        </tr>
      </table></td>
  </tr>
  
</table>
</form>
</body>
</html>
