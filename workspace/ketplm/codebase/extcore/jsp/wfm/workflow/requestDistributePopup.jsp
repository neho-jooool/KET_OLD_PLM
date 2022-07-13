<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.beans.ProjectTaskScheduleHelper"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.dms.service.KETDmsHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalLine"%>
<%@page import="e3ps.ecm.entity.KETProdChangeRequest"%>
<%@page import="e3ps.ecm.entity.KETProdChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeRequest"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeOrder"%>
<%@page import="e3ps.wfm.util.*"%>
<%@page import="e3ps.dms.entity.*"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="e3ps.ecm.beans.*"%>
<%@page import="wt.fc.*"%>
<%@page import="wt.org.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.groupware.company.*"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.common.web.*"%>
<%@page import="java.util.*"%>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  String[] pboOids = request.getParameterValues("pboOids");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "05110") %><%--결재요청--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<%@include file="/jsp/common/processing.html"%>
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
<script type="text/javascript">
//<![CDATA[
var tld;
var winObj1;
var winObj2;
var stop = false;

function popup(type){
    var url = '/plm/jsp/wfm/SetDistributeParticipant.jsp';
    winObj2 = window.open(url,'배포처지정',"status=1, menu=no, width=950, height=700");
}

function deldistribute(){
  var obj = document.requestApproval;

  var tableObj2 = document.getElementById('sDistribute');
    for(j=tableObj2.rows.length-1; j>=0; j--){
      tableObj2.deleteRow(j);
    }
    obj.receiver.value = '';
    obj.reference.value = '';
}

function addTR(totalArray , type){
  var cnt;
  var ptable;

    ptable = document.getElementById("sDistribute");
    if(ptable.rows.length>0)cnt = ptable.rows.length+1;
    else cnt = 1;
    for(var i=0; i<totalArray.length; i++){
      var newTR = ptable.insertRow(0);
      for(var j=0; j<totalArray[i].length; j++){
        var newTD = document.createElement('TD');
        if(j==0){
          newTD.innerText = cnt;
          newTD.width = "59";
        }
        else if(j==1){
          newTD.innerText = totalArray[i][j];
          newTD.width = "100";
        }
        else if(j==2){
          newTD.innerText = totalArray[i][j];
          newTD.width = "170";
        }
        else if(j==3){
          newTD.innerText = totalArray[i][j];
          newTD.width = "90";
        }
        else if(j==4){
          newTD.innerText = totalArray[i][j];
          newTD.width = "159";
        }
        newTD.className = "tdwhiteM";
        newTR.appendChild(newTD);
      }
      cnt++;
    }
}

function saveSelectBox(type, selBox, page){
  var obj = document.requestApproval;

    for(i=0; i<selBox.length; i++){
      if(type=='type4'){
        obj.receiver.value +=selBox.options[i].value + ",";
      }else{
        obj.reference.value +=selBox.options[i].value + ",";
      }
    }
}

function startProcess(){
  var totalArray = new Array();
  var totalcnt = 0;
  var ptable = document.getElementById("sDistribute");
  if(ptable.rows.length<1){
    alert('<%=messageService.getString("e3ps.message.ket_message", "02712") %>');<%--지정된 사용자가 없습니다--%>
    return;
  }
  if (confirm('<%=messageService.getString("e3ps.message.ket_message", "05117") %>')) {<%--추가배포를 요청하시겠습니까?--%>
      showProcessing();
      var url = "/plm/ext/wfm/workflow/doRequestDistribute.do";
      $.ajax({
          url : url,
          type : "POST",
          data : decodeURIComponent($('[name=requestApproval]').serialize()),
          dataType : 'json',
          async : false,
          success : function(flag) {
              hideProcessing();
              if(flag){
                  alert('요청 되었습니다.');
                  self.close();
              }
              else{
                  alert('에러.');
              }
          }
      });
    }
  }
    //]]>
</script>
</head>
<body onload="javascript:window.focus();">
  <form name=requestApproval method="post">
    <input type="hidden" id="receiver" name="receiver">
    <input type="hidden" id="reference" name="reference">
    <input type="hidden" id="bReview" name="bReview">
    <input type="hidden" id="aReview" name="aReview">
    <input type="hidden" id="discuss" name="discuss">
    <%
        if (pboOids != null && pboOids.length > 0) {
    		for (String poid : pboOids) {
    %>
    <input type="hidden" id="pboOids" name="pboOids" value="<%=poid%>">
    <%
    		}
        }
    %>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="50" valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "05110")%><%--결재요청--%></td>
                  </tr>
                </table>
              </td>
              <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td valign="top">
          <table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="10">&nbsp;</td>
              <td valign="top">
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                  <tr>
                    <td class="tab_btm2"></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03354")%><%--배포처--%></td>
                    <td class="tdwhiteM0">
                      <table border="0" cellspacing="0" cellpadding="0" width="580">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                      <table width="580" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>&nbsp;</td>
                          <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td>
                                  <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                      <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popup('setDistribute');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01479")%><%--배포처 지정--%></a></td>
                                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                  </table>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                      <table border="0" cellspacing="0" cellpadding="0" width="580">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                      <table width="580" cellpadding="0" cellspacing="0" class="table_border">
                        <tr>
                          <td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01983")%><%--순차--%></td>
                          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                          <td width="170" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01538")%><%--부서--%></td>
                          <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02714")%><%--직급--%></td>
                          <td width="160" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                        </tr>
                      </table>
                      <div style="width: 580px; height: 270px; overflow-x: hidden; overflow-y: auto;">
                        <table id="sDistribute" name="sDistribute" width="580" cellpadding="0" cellspacing="0" class="table_border">
                        </table>
                      </div>
                      <table border="0" cellspacing="0" cellpadding="0" width="580">
                        <tr>
                          <td class="space5"></td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05116")%><%--추가배포사유--%> <br> (<%=messageService.getString("e3ps.message.ket_message", "00045", 600)%><%--{0}자 이내--%>)</td>
                    <td class="tdwhiteM0" style="height: 100"><textarea name="textfield" class="txt_field" id="textfield" maxlength="600" style="width: 580; height: 96%"></textarea></td>
                  </tr>
                </table>
                <table width="700" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="space10"></td>
                  </tr>
                </table>
                <table width="700" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center">
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:startProcess()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "05115")%><%--배포요청--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table>
                          </td>
                          <td width="5">&nbsp;</td>
                          <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--01197--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="700">
                  <tr>
                    <td class="space5"></td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>