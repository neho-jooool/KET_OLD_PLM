<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
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


    var newTR = tableObj.insertRow(1);
    for(var i=1; i<8; i++){
        var newTD = document.createElement('TD');
        if(i==1) newTD.innerHTML = '<input type=checkbox name=addcheck id=addcheck>';
        else if(i==2) newTD.innerText = rowlength;
        else if(i==3) {
            newTD.title = epmbox.tnumber;
            newTD.className = 'tdwhiteL';
            newTD.innerHTML = '<nobr style=\"text-overflow:ellipsis;overflow:hidden;width:150;\">'+epmbox.tnumber+'</nobr>';
        }else if(i==4) {
            newTD.title = epmbox.tname;
            newTD.className = 'tdwhiteL';
            newTD.innerHTML = '<nobr style=\"text-overflow:ellipsis;overflow:hidden;width:210;\">'+epmbox.tname+'</nobr>';
        }else if(i==5) newTD.innerText = epmbox.state;
        else if(i==6) newTD.innerText = epmbox.version;
        else if(i==7) newTD.innerText = epmbox.creator;
        newTD.className='tdwhiteM';
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
        else {
            if(sendList.value!='')sendList.value = sendList.value + "," + oidArray[i];
            else sendList.value = oidArray[i];
        }
    }
    var maxRow = tableObj.rows.length;
    for(var j=1; j<tableObj.rows.length; j++){
        if(tableObj.rows[j].childNodes[1].innerText!=maxRow-j){
            tableObj.rows[j].childNodes[1].innerText = maxRow-j;
        }
    }
}
function searchList(){
    var url="SearchEPMDocumentList.jsp?command=search&multiReqOid=";
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

    if(rowlength==1){
        alert('<%=messageService.getString("e3ps.message.ket_message", "02464") %><%--저장할 도면을 추가하여 주세요--%>');
        return;
    }
    var ok = confirm('<%=messageService.getString("e3ps.message.ket_message", "02427") %><%--작성을 완료하시겠습니까?--%>');
    if(ok){
        document.createMulti.method = "post";
        document.createMulti.action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=createMulti";
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
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02340") %><%--일괄결재요청등록--%></td>
                <!-- home>도면관리> -->
                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02340") %><%--일괄결재요청등록--%></td>
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
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="SearchMultiApproval.jsp" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%><br>(<%=messageService.getString("e3ps.message.ket_message", "00045", 600) %><%--{0}자 이내--%>)
            </td>
          <td width="680" class="tdwhiteM0" style="height:100"><textarea name="desc" class="txt_field" id="desc" style="width:670; height:96%"></textarea></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%><span class="red">*</span></td>
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
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:searchList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
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
            <table width="670" cellpadding="0" cellspacing="0" class="table_border" name="epmList" id="epmList">
              <tr class="StaticHeader">
                <td width="40"  class="tdgrayM"><input type="checkbox" name="checkAll" id="checkAll" onclick="javascript:selectAll()"></td>
                <td width="50"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="210" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                <td width="70"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td width="40"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
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
