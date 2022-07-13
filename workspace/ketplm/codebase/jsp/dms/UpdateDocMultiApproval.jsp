<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="wt.doc.WTDocument"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.wfm.entity.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "e3ps.common.obj.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "java.util.*" %>
<%@page import = "wt.epm.EPMDocument" %>
<%@page import = "wt.lifecycle.*" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    String oid = request.getParameter("oid");
    String title = "";
    String desc = "";
    Vector docVec = null;
    String docOid = "";
    boolean availableState = false;

    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest)CommonUtil.getObject(oid);
    title = multiReq.getReqName();
    desc = ParamUtil.checkStrParameter(multiReq.getDescription(),"");
    if(multiReq.getState().getState().equals(State.toState("INWORK"))||
            multiReq.getState().getState().equals(State.toState("REWORK"))){availableState = true;}
    docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
    for(int idx=0; idx<docVec.size(); idx++){
        WTDocument doc = (WTDocument)docVec.elementAt(idx);
        doc = (KETProjectDocument)ObjectUtil.getLatestVersion(doc);
        if(idx==0)docOid = KETObjectUtil.getOidString(doc);
        else docOid = docOid + "," + KETObjectUtil.getOidString(doc);
    }
    docOid.trim();


%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<head>
<script language="javascript">

function addTR(epmbox){
    var tableObj = document.getElementById("epmList");
    var sendList = document.getElementById("sendList");
    var rowlength = tableObj.rows.length;

    var numArray = sendList.value.split(',');
    if(numArray.length>0){
        for(var index=0; index<numArray.length; index++){
            if(numArray[index]==epmbox.oid) return;
        }
    }

    var newTR = tableObj.insertRow(1);
    for(var i=1; i<8; i++){
        var newTD = document.createElement('TD');
        if(i==1) {
            newTD.innerHTML = '<input type=checkbox name=addcheck id=addcheck>';
            newTD.className='tdwhiteM';
        }else if(i==2) {
            newTD.innerText = rowlength;
            newTD.className='tdwhiteM';
        }else if(i==3) {
            newTD.title = epmbox.tnumber;
            newTD.className = 'tdwhiteL';
            newTD.innerHTML = '<nobr style=\"text-overflow:ellipsis;overflow:hidden;width:120;\">'+epmbox.tnumber+'</nobr>';
        }else if(i==4) {
            newTD.title = epmbox.tname;
            newTD.className = 'tdwhiteL';
            newTD.innerHTML = '<nobr style=\"text-overflow:ellipsis;overflow:hidden;width:190;\">'+epmbox.tname+'</nobr>';
        }else if(i==5) {
            newTD.innerText = epmbox.state;
            newTD.className='tdwhiteM';
        }else if(i==6) {
            newTD.innerText = epmbox.version;
            newTD.className='tdwhiteM';
        }else if(i==7) {
            newTD.innerText = epmbox.creator;
            newTD.className='tdwhiteM';
        }
        newTR.appendChild(newTD);
    }
    if(sendList.value!='')sendList.value = sendList.value + "," + epmbox.oid;
    else sendList.value = epmbox.oid;

}

function deleteTR(){
    var tableObj = document.getElementById("epmList");
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
function searchList(){
    var url="SearchEPMDocumentList.jsp?command=search&multiReqOid=<%= oid %>";
    window.open(url,"도면검색","status=1, menu=no, resizable=0, width=720, height=650")
}

function selectAll(){
    var box = document.createMulti.addcheck;
    var allbox = document.getElementById("checkAll");
    var tableObj = document.getElementById("epmList");
    var rowlength = tableObj.rows.length;
    if(rowlength>1){
        var total = box.length;

        if(total>1){
            if(allbox.checked==true){
                for(var i=total-1; i>=0; i--){
                    box[i].checked = true;
                }
            }
            else{
                for(i=total-1; i>=0; i--){
                    box[i].checked = false;
                }
            }
        }

        else{
            if(allbox.checked==true){
                box.checked = true;
            }
            else{
                box.checked = false;
            }
        }
    }
}
function save(){

    var title = document.getElementById('title');
    var tableObj = document.getElementById("epmList");
    var rowlength = tableObj.rows.length;

    if(title.value=='' || title.value==null){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02198") %><%--요청제목을 입력하지 않으셨습니다--%>');
        return;
    }
    var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02427") %><%--작성을 완료하시겠습니까?--%>');
    if(ok){
        document.createMulti.method = "post";
        document.createMulti.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=updateMulti2&oid=<%=oid%>";
        document.createMulti.submit();
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
-->
</style>
</head>
<body>
<form name="createMulti">
<input type="hidden" name="sendList" id="sendList" value="<%=docOid %>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02342") %><%--일괄결재요청수정--%></td>
                <td align="right">
                  <img src="../../portal/images/icon_navi.gif">Home
                  <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406") %><%--문서관리--%>
                  <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%>
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
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:history.back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
          <td width="680" class="tdwhiteM0"><input type="text" name="title" class="txt_field"  style="width:670" id="title" value="<%=title %>"></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%><br>
            (<%=messageService.getString("e3ps.message.ket_message", "00045", 600) %><%--{0}자 이내--%>)</td>
          <td width="680" class="tdwhiteM0" style="height:100"><textarea name="desc" class="txt_field" id="desc" style="width:670; height:96%"><%=desc %></textarea></td>
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
            <table width="670" cellpadding="0" cellspacing="0" class="table_border" name="epmList" id="epmList">
              <tr>
                <td width="40" class="tdgrayM"><input type="checkbox" name="checkAll" id="checkAll" onclick="javascript:selectAll()"></td>
                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                <td width="190" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
              <%
              if(docVec!=null){
                  for(int i=docVec.size()-1; i>=0; i--){
                      WTDocument doc = (WTDocument)docVec.elementAt(i);
                      doc = (KETProjectDocument)ObjectUtil.getLatestVersion(doc);
                      String docVersion = "";
                    String docState = "";
                    boolean availableStateDoc = true;
                    if(!doc.getState().getState().equals(State.toState("INWORK")) && !doc.getState().getState().equals(State.toState("REWORK"))) {
                        availableStateDoc = false;
                    }
                    if(KETObjectUtil.getVersion(doc).equals("0")) {
                        docVersion = KETObjectUtil.getVersion(doc);
                    }else {
                        docVersion = "<font color=\"red\">"+KETObjectUtil.getVersion(doc)+"</font>";
                    }
                    if(availableState && availableStateDoc) {
                        docState = doc.getState().getState().getDisplay();
                    }else {
                        docState = "<font color=\"red\">"+doc.getState().getState().getDisplay()+"</font>";
                    }
              %>
              <tr>
                  <td class="tdwhiteM"><input type="checkbox" name="addcheck" id="addcheck" oid="<%=doc.toString() %>"></td>
                  <td class="tdwhiteM"><%=i+1 %></td>
                  <td class="tdwhiteL" title="<%=doc.getNumber() %>"><nobr style="text-overflow:ellipsis;overflow:hidden;width:120;"><%=doc.getNumber() %></nobr></td>
                  <td class="tdwhiteL" title="<%=doc.getName() %>"><nobr style="text-overflow:ellipsis;overflow:hidden;width:190;"><%=doc.getName() %></nobr></td>
                  <td class="tdwhiteM"><%=doc.getLifeCycleState().getDisplay(messageService.getLocale()) %></td>
                  <td class="tdwhiteM"><%=KETObjectUtil.getVersion(doc) %></td>
                  <td class="tdwhiteM"><%=doc.getCreatorFullName() %></td>
              </tr>
              <%
                  }
              }
              %>
              </table>
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
