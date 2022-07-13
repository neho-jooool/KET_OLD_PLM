<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01231") %><%--대상부품 지정--%></title>
<base target="_self" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@ page import="e3ps.common.util.KETObjectUtil
              ,e3ps.common.util.StringUtil
              ,wt.org.WTUser
              ,wt.session.SessionHelper
              ,java.util.Hashtable" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="partLineList" class="java.util.ArrayList" scope="request"/>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<%

  WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
    String userGroup = KETObjectUtil.getCurrentPartialUserGroup();

  String mode = request.getParameter("mode");
  String modal = request.getParameter("modal");
  String pop = request.getParameter("pop");

  if( (mode == null) || (mode.trim().length() == 0))
  {
    mode = "one";
  }

  if( (modal == null) || (modal.trim().length() == 0))
  {
    modal = "N";
  }

  if( (pop == null) || (pop.trim().length() == 0))
  {
    pop = "N";
  }
%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script type="text/javascript">
<!--

//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function popupPart( fncall )
{
  var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=M&modal=N&fncall="+fncall;
  openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
}

function partDuplicateCheck(poid) {
//부품추가시 선택된 부품정보를 중복체크한다.
  var tBody = document.getElementById("partTable");
  var rowsLen = tBody.rows.length;
  if(rowsLen > 0) {
    var primaryPart = document.forms[0].partOid;
    var partLen = primaryPart.length;
    if(partLen > 0 ) {
      for(var i = 0; i < partLen; i++) {
        if(primaryPart[i].value == poid) {
          return true;
        }
      }
    } else {
      if(primaryPart.value == poid) {
        return true;
      }
    }
  }
  return false;
}

function setPartLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("partTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    if( !partDuplicateCheck(trArr[0]))
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
        newTr.onmouseover=function(){partTable.clickedRowIndex=this.rowIndex};

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "30";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input name='chkSelectRelPart' type='checkbox' value=''>";

      //Part No
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "127";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      str += "<input type='hidden' name='partOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='partNo' value='"+trArr[1]+"'>";
      newTd.innerHTML = str;

      //Part Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "191";
      newTd.className = "tdwhiteL";
      newTd.Title= trArr[2];
      str = "";
      str +="<font title=\""+trArr[2]+"\">";
      str += "<div class='ellipsis' style='width:191;'><nobr>";
      str += trArr[2] +"</nobr></div></font>";
        newTd.innerHTML = str;

      //유형
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "119";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<select name='changeType' class='fm_jmp' style='width:80'>";
      str += "<option value='가공' selected><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%>&nbsp;</option>";
      str += "<option value='수정'><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>&nbsp;</option>";
      str += "<option value='가공+수정'><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%>&nbsp;</option>";
      str += "<option value='도면정리'>도면정리&nbsp;</option>";
      str += "</select>";
      newTd.innerHTML = str;

      //비고
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width= "153";
      newTd.className="tdwhiteL0";
      str = "";
      str+="<input type='text' name='description'  class='txt_field'  style='width:87%' value=''>";
      newTd.innerHTML = str;
    }
  }
}

function isChecked( checkboxName )
{
  var isChecked = false;
   var objCheck = document.getElementsByName(checkboxName);

   for( var i=0; i < objCheck.length; i++ )
   {
     if( objCheck[i].checked )
     {
       isChecked = true;
     }
   }

   return isChecked;
}
function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
{
    var body = document.getElementById(tableElementId);
    if (body.rows.length == 0) return;
    var formNameStr = "document." + formName + ".";
    var objChecks = eval(formNameStr + checkboxName);
    var objAllChecks = eval(formNameStr + allCheckName);

    if( isChecked( checkboxName ) )
    {
      var listVal = "";
      var objList;
      if(listVarName != "") {
        objList = eval(formNameStr + listVarName);
        listVal = objList.value;
      }

      if (body.rows.length == 1) {
        if (objChecks.checked || objChecks[0].checked) {
          if(objChecks.checked) {
            if(listVal == "") {
              listVal = objChecks.value;
            } else {
              listVal += "*" + objChecks.value;
            }
          } else if(objChecks[0].checked) {
            if(listVal == "") {
              listVal = objChecks[0].value;
            } else {
              listVal += "*" + objChecks[0].value;
            }
          }
          body.deleteRow(0);
        }
      } else {
        for (var i = body.rows.length; i > 0; i--) {
          if (objChecks[i-1].checked) {
            if(listVal == "") {
              listVal = objChecks[i-1].value;
            } else {
              listVal += "*" + objChecks[i-1].value;
            }
            body.deleteRow(i - 1);
          }
        }
      }
      if (body.rows.length < 1) {
        objAllChecks.checked = false;
      }
      if(listVarName != "") {
        objList.value = listVal;
      }
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
      return;
    }
}
// 확인
function confirm()
{
  document.forms[0].cmd.value = 'Save';
  document.forms[0].action= '/plm/servlet/e3ps/MoldStdPartServlet';
  document.forms[0].target='_self';
  disabledAllBtn();
  showProcessing();
  document.forms[0].submit();
}

// 전체 체크박스 선택
function allCheck( checkboxName, isChecked )
{
        var checkedList = document.getElementsByName( checkboxName );

        for( var i=0; i < checkedList.length ; i++ )
    {
      checkedList[i].checked = isChecked;
    }
}

-->
</script>
</head>
<body>
<form method="post">
<input type='hidden' name='deleteRelPartList'>
<input type='hidden' name='pop' value='<%=pop %>'>
<input type='hidden' name='cmd'>
<input type='hidden' name='docLinkOid'  value='<%=StringUtil.checkNull(request.getParameter("docLinkOid")) %>'>
<input type='hidden' name='ecoOid' value='<%=StringUtil.checkNull(request.getParameter("ecoOid")) %>'>
<input type="hidden" name='div_type'>
<input type="hidden" name="mode" value='<%=mode%>'>
<input type="hidden" name="modal" value='<%=modal%>'>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <%if( pop.equals("N")){ %>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01231") %><%--대상부품 지정--%></td>
            <%}else{ %>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01230") %><%--대상부품 리스트--%></td>
            <%} %>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="625" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="625" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td width="120" class="tdblueL">ECO No</td>
                <td width="150" class="tdwhiteL">
                  <input type="text" name="ecoNo" class="txt_fieldRO"  style="width:98%" reaonly value='<%=StringUtil.checkNull(request.getParameter("ecoNo"))%>'>
                </td>
                <td width="120" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                <td width="" class="tdwhiteL0">
                  <input type="text" name="docNo" class="txt_fieldRO"  style="width:98%" readonly value='<%=StringUtil.checkNull(request.getParameter("docNo"))%>'>
                </td>
              </tr>
            </table>
             <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
             <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space15"></td>
                <%if(pop.equals("N")) {%>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
          <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupPart('setPartLine');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01573") %><%--부품 추가--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                </table></td>
                <td width="5">&nbsp;</td>
          <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'partTable', 'chkSelectRelPart', 'chk_all', 'deleteRelPartList');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01591") %><%--부품삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
              <%} %>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <%if( pop.equals("N") ){ %>
                <td width="30" class="tdblueM"><input type="checkbox" name="chk_all" onclick="javascript:allCheck( 'chkSelectRelPart', this.checked );"></td>
                <%}else{%>
                <td width="30" class="tdblueM">No</td>
                <%} %>
                <td width="125" class="tdblueM">Part No</td>
                <td width="196" class="tdblueM">Part Name</td>
                <td width="119" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                <td width="154" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
              </tr>
            </table>
            <div style="width=625;height=230;overflow-x:hidden;overflow-y:auto;">
             <table border="0" cellspacing="0" cellpadding="0" width="625"  id="partTable">
             <%
             if( partLineList.size() > 0 )
             {
               Hashtable<String, String> partHash = null;

               for(int cnt=0; cnt < partLineList.size(); cnt++)
               {
                 partHash = (Hashtable)partLineList.get(cnt);
             %>
               <tr>
                 <%if( pop.equals("N") ){ %>
                 <td width="30" class="tdwhiteM"><input name='chkSelectRelPart' type='checkbox' value=''></td>
                 <td width="127" class="tdwhiteM"><a href="javascript:viewRelPart(' <%=partHash.get("partOid") %>');"><%=partHash.get("partNo") %></a>
                 <input type='hidden' name='partOid' value='<%=partHash.get("partOid") %>'>
                 <input type='hidden' name='partNo' value='<%=partHash.get("partNo") %>'>
                 </td>
                 <td width="191" class="tdwhiteL" title="<%=partHash.get("partName") %>"><div class='ellipsis' style='width:191;'><nobr><%=partHash.get("partName") %></nobr></div></td>
                 <td width="119" class="tdwhiteM">
            <select name='changeType' class='fm_jmp' style='width:80'>
            <option value='가공'  <%if("가공".equals(partHash.get("changeType"))) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%>&nbsp;</option>
            <option value='수정' <%if("수정".equals(partHash.get("changeType"))) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>&nbsp;</option>
            <option value='가공+수정' <%if("가공+수정".equals(partHash.get("changeType"))) {out.print("selected");}%>><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%>&nbsp;</option>
            <option value='도면정리' <%if("도면정리".equals(partHash.get("changeType"))) {out.print("selected");}%>>도면정리&nbsp;</option>
                 </td>
                 <td width="153" class="tdwhiteL0"><input type="text" name="description"  class="txt_field"  style="width:87%" value="<%=partHash.get("description") %>"></td>
                 <%}else{ %>
                 <td width="31" class="tdwhiteM"><%=cnt+1 %></td>
                 <td width="126" class="tdwhiteM"><a href="javascript:viewRelPart(' <%=partHash.get("partOid") %>');"><%=partHash.get("partNo") %></a>
                 <input type='hidden' name='partOid' value='<%=partHash.get("partOid") %>'>
                 <input type='hidden' name='partNo' value='<%=partHash.get("partNo") %>'>
                 </td>
                 <td width="191" class="tdwhiteL" title="<%=partHash.get("partName") %>"><div class='ellipsis' style='width:191;'><nobr><%=partHash.get("partName") %></nobr></div></td>
          <td width="120" class="tdwhiteM"><%=partHash.get("changeType") %></td>
                 <td width="152" class="tdwhiteL0"><%=partHash.get("description") %>&nbsp;</td>
                 <%} %>
               </tr>
             <%
               }
             }
             %>
       </table>
     </div>
            <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
        <table border="0" cellspacing="0" cellpadding="0" width="625">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <%if( pop.equals("N")){ %>
            <table width="625" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:confirm();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table>
            <%} %>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="625" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="625" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</body>
</html>
