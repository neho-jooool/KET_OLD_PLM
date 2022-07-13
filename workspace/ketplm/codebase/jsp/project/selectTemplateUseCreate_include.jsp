<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script language="JavaScript">
function addTemp(data) {
    var tempData = data.split("?");
    tempData = tempData[0].split("&");
    index = tempTableName.rows.length;
    if(index < 3) {
        trObj = tempTableName.children(0).children(tempTableName.children(0).children.length-1);
        trObjClone = trObj.cloneNode("true");
        trObj.insertAdjacentElement("afterEnd", trObjClone);
        newTrObj = tempTableName.children(0).children(tempTableName.children(0).children.length-1);
        newTrObj.style.display = "block";

        newTrObj.children(0).children(0).value = tempData[0];
        newTrObj.children(1).innerHTML = "<input type='hidden' name='tempid' value='"+tempData[0]+"'>&nbsp;" + tempData[1];
        newTrObj.children(2).innerHTML = tempData[2] + "&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%>";
        newTrObj.children(3).innerHTML = tempData[3];
        newTrObj.children(4).innerHTML = tempData[4];
    } else {
        newTrObj = tempTableName.children(0).children(tempTableName.children(0).children.length-1);
        newTrObj.style.display = "block";
        newTrObj.children(0).children(0).value = tempData[0];
        newTrObj.children(1).innerHTML = "<input type='hidden' name='tempid' value='"+tempData[0]+"'>&nbsp;" + tempData[1];
        newTrObj.children(2).innerHTML = tempData[2] + "&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%>";
        newTrObj.children(3).innerHTML = tempData[3];
        newTrObj.children(4).innerHTML = tempData[4];
    }
}

function insertTemp(){
    var url = "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=s&function=addTempUseTemplateCreate";
    openOtherName(url,"selecttemplete","600","400","status=no,scrollbars=yes,resizable=no");
}

function deleteTemp() {
    index = document.forms[0].box.length-1;
    for(i=index; i>=1; i--) {
        if(document.forms[0].box[i].checked == true) {
            tempTableName.deleteRow(i+1);
        }
    }
}
</script>

                <table width="100%" align="center">
                    <tr>
                        <td height="25" >
                            <input type=button class="btnTras" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="javascript:insertTemp()">&nbsp;
                            <input type=button class="btnTras" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onclick="javascript:deleteTemp()">
                        </td>
                    </tr>
                </table>
                <table width="100%" cellspacing="1" cellpadding="1" border="0" id="tempTableName" bgcolor=AABDC6>
                    <tr bgcolor="#D6DBE7"  align=center>
                        <td width="50%" id=tb_blue colspan="2">Template</td>
                        <td width="10%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                        <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02859") %><%--최초등록일--%></td>
                        <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
                    </tr>
                    <tr bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'" align="center" style="display:none">
                        <td width="3%" id=tb_gray align="center"><input type="checkbox" name="box"></td>
                        <td width="47%" id=tb_gray align="left"></td>
                        <td width="10%" id=tb_gray> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                        <td width="20%" id=tb_gray></td>
                        <td width="20%" id=tb_gray></td>
                    </tr>
                </table>
