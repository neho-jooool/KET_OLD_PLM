<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,e3ps.common.util.*,e3ps.common.content.*,wt.content.*" %>
<%
  // command : 첨부파일 Action type
  // insert : 신규 등록
  // update : 수정
  String command = StringUtil.checkReplaceStr(request.getParameter("command"),"insert");
  // command 가 update시에 update 할 ContentHolder OID
  String oid = StringUtil.checkNull(request.getParameter("oid"));

  // type : 첨부파일 타입
  //  p or primary : (Primary)주요문서 파일
  // s or secondary : (Secondary)참조문서 파일
  String type = StringUtil.checkNull(request.getParameter("type"));
  if ( type.equalsIgnoreCase("p") || type.equalsIgnoreCase("primary")  ) type = "primary";
  else type = "secondary";

  // (Secondary)참조문서 파일일 경우 첨부할수 있는 갯수
  int attacheCount = StringUtil.parseInt(request.getParameter("count"), 5);

  // (Secondary)참조문서 파일일 경우 설명 사용여부
  String desc = StringUtil.checkNull(request.getParameter("desc"));
  boolean canDesc = false;
  if ( desc.equalsIgnoreCase("t") || desc.equalsIgnoreCase("true") ) canDesc = true;

  ContentInfo primaryFile = null;
  if ( type.equals("primary") && command.equalsIgnoreCase("update") ) {
    ContentHolder holder = (ContentHolder)CommonUtil.getObject(oid);
    if ( holder instanceof FormatContentHolder ) primaryFile = ContentUtil.getPrimaryContent((FormatContentHolder)holder);
  }

  Vector secondaryFiles = new Vector();
  int deleteFileCnt = 0;
  if ( type.equals("secondary") && command.equalsIgnoreCase("update") ) {
    ContentHolder holder = (ContentHolder)CommonUtil.getObject(oid);
    secondaryFiles = ContentUtil.getSecondaryContents(holder);
  }
%>
<!-- 파일 첨부 시작 //-->
<%  if ( type.equals("primary") ) {  %>
<SCRIPT LANGUAGE="JavaScript">
<!--
function checkPrimary() {
  if(document.forms[0].<%=type%>.value == "") {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02960") %><%--파일(file)을 지정하세요--%>');
    return false;
  } else {
    return true;
  }
}

// --- 주요파일 변경하기 -----//
function fcFileShowHide(obj) {
  if (obj.value == '변경') {
    FileShow.style.display = '';
    FileHide.style.display = 'none';
    obj.value = '취소';
  } else {
    FileShow.style.display = 'none';
    FileHide.style.display = '';
    obj.value = '변경';
  }
}
//-->
//-- 파일 첨부 끝
//-->
</SCRIPT>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
<%  if ( command.equals("insert") ) {  %>
    <td><input type="file" name="<%=type%>" id=input class="txt_field" style="width:90%" onkeydown="event.returnValue=false;"></td>
<%
  } else {
    if ( primaryFile != null ) {
%>
    <td width="40">
      <!--a href="javascript:fcFileShowHide(this);">
      <img src="/plm/portal/images/img_default/button/board_btn_file_edit.gif" alt="파일변경" width="76" height="20" border="0">
      </a-->
      <!--a href="javascript:fcFileShowHide(this);">
      <img src="/plm/portal/images/img_default/button/board_btn_file_edit_no.gif" alt="파일변경취소" width="98" height="20" border="0">
      </a-->
      <input type=button class="s_font" value='변경' onclick="fcFileShowHide(this);">
    </td>
    <td>
      <span id="FileShow" style="display:'none';">
      &nbsp;<input type="file" name="<%=type%>" id=input class="txt_field" style="width:90%" onkeydown="event.returnValue=false;">
      <input type="hidden" name="<%=type%>Description" value="PRIMARY FILE">
      </span>
      <span id="FileHide" style="display:'';"><%=primaryFile.getIconURLStr()%>&nbsp;<%= primaryFile.getDownURLStr() %></span>
    </td>

<%    } else { %>
    <td>
      <input type="file" name="<%=type%>" id=input class="txt_field" style="width:90%">
      <input type="hidden" name="<%=type%>Description" value="PRIMARY FILE">
    </td>
<%    }  %>
<%  }  %>
  </tr>
</table>
<%  } else {  %>
<SCRIPT LANGUAGE="JavaScript">
<!--
//-- 파일 첨부 시작
function insertFile<%=type%>() {
  index = fileTable<%=type%>.rows.length;

<%    if ( type.equals("secondary") && attacheCount > 0 ) {  %>
  if(index >= (<%=attacheCount%>+2)) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02959", attacheCount) %><%--파일 첨부는 최대 {0}개까지 입니다--%>');
    return;
  }
<%    }  %>

  if(index >= 2) {
    if(fileTableRow<%=type%>.style != null)
      fileTableRow<%=type%>.style.display = 'block';
    else
      fileTableRow<%=type%>[0].style.display = 'block';
  }
  trObj = fileTable<%=type%>.insertRow(index);
  trObj.replaceNode(fileTable<%=type%>.rows[1].cloneNode(true));
  fileTableRow<%=type%>[0].style.display = 'none';
}

function deleteFile<%=type%>() {
  index = document.forms[0].fileDelete<%=type%>.length-1;

  for(i=index; i>=1; i--) {
    if(document.forms[0].fileDelete<%=type%>[i].checked == true) fileTable<%=type%>.deleteRow(i+1);
  }
}
//-- 파일 첨부 끝
//-->
</SCRIPT>
<table width="100%" cellspacing="0" cellpadding="1" border="0">
  <tr>
    <td height="25">
                <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFile<%=type%>()" class="btn_blue">추가</a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            <td>&nbsp;</td>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteFile<%=type%>()" class="btn_blue">삭제</a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
        <%if(type.equals("secondary")&&attacheCount>0){%><td align="left"><font color="red">※<%=messageService.getString("e3ps.message.ket_message", "02959", attacheCount) %><%--파일 첨부는 최대 {0}개까지 입니다--%></font></td><%}%>
  </tr>
</table>
<table width="100%" cellspacing="0" cellpadding="1" border="0" id="fileTable<%=type%>" align="center">
  <tr align=center>
<%    if(canDesc) {  %>
    <td width="60%" colspan="2" id=tb_inner><%=messageService.getString("e3ps.message.ket_message", "00795") %><%--경로--%></td>
    <td width="40%" id=tb_inner><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
<%    } else {  %>
<%    }  %>
  </tr>
  <tr align="center" id="fileTableRow<%=type%>" style="display:none">
    <td class="tdwhiteM" width="3%"><input type="checkbox" name="fileDelete<%=type%>"></td>
<%    if(canDesc) {  %>
    <td width="57%"><input type="file" name="<%=type%>" id=input style="width:99%" onkeydown="event.returnValue=false;"></td>
    <td width="40%"><input type="text" name="<%=type+"Desc"%>" id=input style="width:99%"></td>
<%    } else {  %>
    <td class="tdwhiteM" width="97%"><input type="file" name="<%=type%>" id=input style="width:99%" class="txt_field" onkeydown="event.returnValue=false;"></td>
<%    }  %>
  </tr>
<%
    for(int i=0; secondaryFiles !=null && i<secondaryFiles.size(); i++) {
      ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
%>
  <tr align="center">
<%    if(canDesc) {  %>
    <td id=tb_gray width="3%"><input type="checkbox" name="fileDelete<%=type%>"></td>
    <td id=tb_gray width="57%" align=left><input type="hidden" name="secondaryDelFile" value="<%=info.getContentOid()%>"><%=info.getIconURLStr()%>&nbsp;<%=info.getDownURLStr()%></td>
    <td id=tb_gray width="40%"><input type="text" name="secondaryDelFileDesc" id=input style="width:99%" readonly value="<%=info.getDescription()%>"></td>
<%    } else {  %>
    <td class="tdwhiteM" width="3%"><input type="checkbox" name="fileDelete<%=type%>"></td>
    <td class="tdwhiteL" width="97%" align=left><input type="hidden" name="secondaryDelFile" value="<%=info.getContentOid()%>"><%=info.getIconURLStr()%>&nbsp;<%=info.getDownURLStr()%></td>
<%    }  %>
  </tr>
<%    }  %>
</table>
<%  }  %>
<!-- 파일 첨부 끝 //-->
