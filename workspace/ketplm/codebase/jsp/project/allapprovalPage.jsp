<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.common.util.KETObjectUtil"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.project.beans.ProjectOutputData"%>
<%@page import="e3ps.project.beans.ProjectOutputData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.project.trycondition.entity.*"%>
<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<head>

<%
    String[] oids = request.getParameterValues("oid");
    String taskoid = request.getParameter("taskOid");
    ProjectOutput output = null;
    ProjectOutputData outputData = null;
    KETProjectDocument pDoc = null;
    EPMDocument eDoc = null;
    KETProdChangeOrder pChange = null;
    KETMoldChangeOrder mChange = null;
    KETTryMold tryMold = null;
    KETTryPress tryPress = null;
%>
<script language="javascript">

        function save(){

            var title = document.getElementById('title');
            var tableObj = document.getElementById("docList");
            var rowlength = tableObj.rows.length;

            if(title.value=='' || title.value==null){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02198") %><%--요청제목을 입력하지 않으셨습니다--%>');
                return;
            }

            if(rowlength==1){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02465") %><%--저장할 문서을 추가하여 주세요--%>');
                return;
            }
            var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02427") %><%--작성을 완료하시겠습니까?--%>');
            if(ok){
                document.createMulti.method = "post";
                document.createMulti.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=createMulti2&taskoid=<%=taskoid%>";
                document.createMulti.submit();
            }
        }
        /*
        function a()
        {
            alert(this.offsetParent.scrollTop);
        }
        */

        function deleteTR(){
            var tableObj = document.getElementById("docList");
            var box = document.getElementsByName("addcheck");
            var sendList = document.getElementById("sendList");
            var oidArray = sendList.value.split(",");
            sendList.value = '';
            var total = box.length;
            for(var i=total-1; i>=0; i-- ){
                if(box[i].checked==true){
                    tableObj.deleteRow(i+1);
                }
            }
            var obj = document.createMulti;
            if(obj.addcheck.length>1){
                for(i=0; i<obj.addcheck.length; i++){
                    if(sendList.value=='') sendList.value = obj.addcheck[i].oid;
                    else sendList.value = sendList.value + ',' + obj.addcheck[i].oid;
                }
            }else{
                sendList.value = obj.addcheck.oid;
            }

            var maxRow = tableObj.rows.length;
            for(var j=1; j<tableObj.rows.length; j++){
                if(tableObj.rows[j].childNodes[1].innerText!=maxRow-j){
                    tableObj.rows[j].childNodes[1].innerText = maxRow-j;
                }
            }
        }

</script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.StaticHeader
{
position: relative;
top: expression(this.offsetParent.scrollTop-2);
}

-->
</style>
</head>
<body>
<form name="createMulti">
<input type="hidden" name="sendList" id="sendList">
<input type="hidden" name="taskoid" value="<%=taskoid %>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01729") %><%--산출물 일괄결재 요청--%></td>
                <td align="right">
                  <img src="../../portal/images/icon_navi.gif">Home
                  <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%>
                  <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01729") %><%--산출물 일괄결재 요청--%>
                </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5"></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02197") %><%--요청제목--%><span class="red">*</span></td>
          <td width="680" class="tdwhiteM0"><input type="text" name="title" class="txt_field"  style="width:670" id="title"></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%><br>
            (<%=messageService.getString("e3ps.message.ket_message", "00045", 600) %><%--{0}자 이내--%>)</td>
          <td width="680" class="tdwhiteM0" style="height:100"><textarea name="desc" class="txt_field" id="desc" style="width:670; height:96%"></textarea></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01394") %><%--문서--%><span class="red">*</span></td>
          <td width="680" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="680">
                <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="670" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteTR();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>

            <div style="height=200;overflow-x:hidden;overflow-y:auto;">
            <table width="670" cellpadding="0" cellspacing="0" class="table_border" name="docList" id="docList">
              <tr class="StaticHeader" align="center">
                <td width="40" class="tdgrayM"></td>
                <td width="50" class="tdgrayM"></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="70" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              </tr>
              <%
              	int count = oids.length;
                String docVersion = "";
                String docState = "";
                String number = "";
                String name = "";
                String creatorName = "";
                String docOid = "";
                Object obj = null;
                  for(int i=0; i < oids.length; i++) {
                	  obj = CommonUtil.getObject(oids[i]);
                	  if(obj instanceof KETProjectDocument){
                		  pDoc = (KETProjectDocument)obj;
                		  number = pDoc.getNumber();
                		  name = pDoc.getName();
                		  creatorName = pDoc.getCreatorFullName();
                		  docVersion = KETObjectUtil.getVersion(pDoc);
                          docState = pDoc.getState().getState().getDisplay();
                          docOid = pDoc.toString();
                	  }else if(obj instanceof EPMDocument){
                		  eDoc = (EPMDocument)obj;
                		  number = eDoc.getNumber();
                		  name = eDoc.getName();
                		  creatorName = eDoc.getCreatorFullName();
                		  docVersion = KETObjectUtil.getVersion(eDoc);
                          docState = eDoc.getState().getState().getDisplay();
                          docOid = eDoc.toString();
                      }else if(obj instanceof KETProdChangeOrder){
                          pChange = (KETProdChangeOrder)obj;
                          number = pChange.getNumber();
                          name = pChange.getEcoName();
                          creatorName = pChange.getCreatorFullName();
                          docVersion = "";
                          docState = "";
                          docOid = pChange.toString();
                      }else if(obj instanceof KETMoldChangeOrder){
                          mChange = (KETMoldChangeOrder)obj;
                          number = mChange.getNumber();
                          name = mChange.getEcoName();
                          creatorName = mChange.getCreatorFullName();
                          docVersion = "";
                          docState = "";
                          docOid = mChange.toString();
                      }else if(obj instanceof KETTryMold){
                          tryMold = (KETTryMold)obj;
                          number = tryMold.getNumber();
                          name = tryMold.getName();
                          creatorName = tryMold.getCreatorFullName();
                          docVersion = "";
                          docState = "";
                          docOid = tryMold.toString();
                      }else if(obj instanceof KETTryPress){
                          tryPress = (KETTryPress)obj;
                          number = tryPress.getNumber();
                          name = tryPress.getName();
                          creatorName = tryPress.getCreatorFullName();
                          docVersion = "";
                          docState = "";
                          docOid = tryPress.toString();
                	  }


              %>
              <tr align="center">
                  <td class="tdwhiteM26"><input type="checkbox" id="addcheck" name="addcheck" oid="<%=docOid%>"><input type="hidden" name="docOid" value="<%=oids[i]%>"></td>
                <td class="tdwhiteM26"><%=count--%></td>
                  <td class="tdwhiteM26"><%=number %></td>
                  <td class="tdwhiteM26"><%=name %></td>
                  <td class="tdwhiteM26"><%=creatorName %></td>
                  <td class="tdwhiteM26"><%=docVersion %></td>
                  <td class="tdwhiteM26"><%=docState %></td>
              </tr>
              <%
               }
              %>
              </table>
              </div>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
