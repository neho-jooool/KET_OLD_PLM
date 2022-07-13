<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.ArrayList,
                            java.util.Hashtable,
                            e3ps.project.beans.MoldProjectHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
String partNo = request.getParameter("partno");

ArrayList<Hashtable<String, String>> dieNoList = MoldProjectHelper.getDieNoList(partNo);
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00923") %><%--관련Die No--%></title>
<base target="_self" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }
-->
</style>
<script type="text/javascript">
function selectDieNo()
{
    var checkedList = document.getElementsByName("chkSelectRelPart");

        var selectedDieNo = "";
        var selectedList = new Array();
        var subarr = null;
        var idx = 0;
        if( checkedList.length > 0 ){
            for( var inx=0; inx < checkedList.length ; inx++ ){
                if(checkedList[inx].checked == true) {
                    selectedDieNo = checkedList[inx].value;
                }
            }

            if( selectedDieNo == '' ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00147") %><%--Die No을 선택한 후 확인을 눌러주세요!--%>");
                return;
            }else {
                    window.returnValue = selectedDieNo;
                    window.close();
            }
        }
}
</script>
</head>
<body>
<form method="post">
<input type="hidden" name="partno" value='<%=partNo%>'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00905") %><%--관련 Die No 리스트--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="450" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top"><table width="450" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="450">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="450">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <div style="width=450;height=150;overflow-x:hidden;overflow-y:auto;" id="div_scroll">
             <table border="0" cellspacing="0" cellpadding="0" width="100%"  id="partTable" style=table-layout:fixed >
               <tr class="headerLock">
                 <td width="30" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
                <td width="120" class="tdblueM">Die No</td>
                <td width="200" class="tdblueM">Part Name</td>
                <td width="100" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "03118") %><%--프로젝트상태--%></td>
              </tr>
             <%
             if( dieNoList.size() > 0 )
             {
                 Hashtable<String, String> partHash = null;

                 for(int cnt=0; cnt < dieNoList.size(); cnt++)
                 {
                     partHash = (Hashtable)dieNoList.get(cnt);
             %>
                 <tr>
                     <td width="30" class="tdwhiteM"><input name='chkSelectRelPart' type='checkbox' value="<%=partHash.get("dieNo") %>"  onClick="javascript:selectDieNo();"></td>
                     <td width="120" class="tdwhiteM"><%=partHash.get("dieNo") %></a>
                     </td>
                     <td width="200" class="tdwhiteL" title="<%=partHash.get("partName") %>"><div class='ellipsis' style='width:200;'><nobr><%=partHash.get("partName") %></nobr></div></td>
                     <td width="100" class="tdwhiteM0"><%=partHash.get("status") %></a>
                 </tr>
             <%
                 }

                 if( dieNoList.size() > 1 )
                 {
             %>
                  <tr>
                     <td width="30" class="tdwhiteM"><input name='chkSelectRelPart' type='checkbox' value="기타"  onClick="javascript:selectDieNo();"></td>
                     <td width="120" class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                     <td width="200" class="tdwhiteL" title=""><div class='ellipsis' style='width:200;'><nobr>&nbsp;</nobr></div></td>
                     <td width="100" class="tdwhiteM0">&nbsp;</a>
                 </tr>           <%
                 }
             }
             %>
             </table>
         </div>
            <table border="0" cellspacing="0" cellpadding="0" width="450">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="450" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:selectDieNo();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="450" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</body>
</html>
