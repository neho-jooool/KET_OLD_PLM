<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.web.*, e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String formObj = StringUtil.checkNull(request.getParameter("formObj"));						// Form Object 명
    String checkBoxName = StringUtil.checkNull(request.getParameter("checkBoxName"));			// 체크박스 명
    String tableName = StringUtil.checkNull(request.getParameter("tableName"));					// 테이블 ID 명
    String addFunction = StringUtil.checkNull(request.getParameter("addFunction"));			// 호출 스크립트 함수 명
    String insertFunction = StringUtil.checkNull(request.getParameter("insertFunction"));	// 추가 스크립트 함수 명
    String insertURL = StringUtil.checkNull(request.getParameter("insertURL"));					// 추가 URL
    String deleteFunction = StringUtil.checkNull(request.getParameter("deleteFunction"));	// 삭제 스크립트 함수 명

    String mode = ParamUtil.getStrParameter(request.getParameter("mode"),"s");						// 선택 모드 (s:single , m:multi)
    String checkOverLap = ParamUtil.getStrParameter(request.getParameter("overLap"),"t");		// 중복체크 여부 (t:true(체크함) , f:false(체크안함))

    insertURL = insertURL + "?function=addUserCreateForProject&mode="+mode+"&other="+addFunction;
    //boolean canManage = StringUtil.parseBoolean(request.getParameter("canManage"));
    String canManage = StringUtil.checkNull(request.getParameter("canManage"));
%>

<script language="JavaScript">
<!--
function <%=addFunction%>(data) {
    var dataArr = data.split("//");
    index = <%=tableName%>.rows.length;

<%	if ( mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("single") ) {	%>
    var subDataArr = dataArr[0].split("&");
    if(index < 3) {
        trObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
        trObjClone = trObj.cloneNode("true");
        trObj.insertAdjacentElement("afterEnd", trObjClone);
        newTrObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
        newTrObj.style.display = "block";

        newTrObj.children(0).children(0).value = subDataArr[0];
        newTrObj.children(1).innerHTML = "<input type='hidden' name='<%=formObj%>' value='"+subDataArr[0]+"'>"+ subDataArr[1];
        newTrObj.children(2).innerText = subDataArr[2];
        newTrObj.children(3).innerText = subDataArr[3];
        newTrObj.children(4).innerText = subDataArr[4];
    } else {
        newTrObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
        newTrObj.style.display = "block";
        newTrObj.children(0).children(0).value = subDataArr[0];
        newTrObj.children(1).innerHTML = "<input type='hidden' name='<%=formObj%>' value='"+subDataArr[0]+"'>"+ subDataArr[1];
        newTrObj.children(2).innerText = subDataArr[2];
        newTrObj.children(3).innerText = subDataArr[3];
        newTrObj.children(4).innerText = subDataArr[4];
    }
<%
    } else {
        if ( checkOverLap.equalsIgnoreCase("f") || checkOverLap.equalsIgnoreCase("false") ) {
%>
    for ( var i = 0 ; i < (dataArr.length-1) ; i++ ) {
        var subDataArr = dataArr[i].split("&");
        trObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
        trObjClone = trObj.cloneNode("true");
        trObj.insertAdjacentElement("afterEnd", trObjClone);
        newTrObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
        newTrObj.style.display = "block";

        newTrObj.children(0).children(0).value = subDataArr[0];
        newTrObj.children(1).innerHTML = "<input type='hidden' name='<%=formObj%>' value='"+subDataArr[0]+"'>"+ subDataArr[1];
        newTrObj.children(2).innerText = subDataArr[2];
        newTrObj.children(3).innerText = subDataArr[3];
        newTrObj.children(4).innerText = subDataArr[4];
    }
<%
        } else {
%>
    for ( var i = 0 ; i < (dataArr.length-1) ; i++ ) {
        var subDataArr = dataArr[i].split("&");
        if ( !checkOID(subDataArr[0]) ) {
            trObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
            trObjClone = trObj.cloneNode("true");
            trObj.insertAdjacentElement("afterEnd", trObjClone);
            newTrObj = <%=tableName%>.children(0).children(<%=tableName%>.children(0).children.length-1);
            newTrObj.style.display = "block";

            newTrObj.children(0).children(0).value = subDataArr[0];
            newTrObj.children(1).innerHTML = "<input type='hidden' name='<%=formObj%>' value='"+subDataArr[0]+"'>"+ subDataArr[1];
            newTrObj.children(2).innerText = subDataArr[2];
            newTrObj.children(3).innerText = subDataArr[3];
            newTrObj.children(4).innerText = subDataArr[4];
        }
    }
<%
        }
    }
%>
}

function <%=insertFunction%>(){
    var url = "<%=insertURL%>";
    openOtherName(url,"selectuser","800","600","status=no,scrollbars=no,resizable=no");
}

function <%=deleteFunction%>() {
    index = document.forms[0].<%=checkBoxName%>.length-1;
    for(i=index; i>=1; i--) {
        if(document.forms[0].<%=checkBoxName%>[i].checked == true) {
            <%=tableName%>.deleteRow(i+1);
        }
    }
}

function checkOID(oid) {
    index = document.forms[0].<%=checkBoxName%>.length-1;
    for(i=index; i>=1; i--) {
        return (document.forms[0].<%=checkBoxName%>[i].value == oid);
    }
}

function viewPeople(v){
    var str="/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+v;
    newWin = window.open(str,"viewPeople", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=400,resizable=yes,mebar=no,left=40,top=65");
    newWin.focus();
}
//-->
</script>

                <table width="100%" align="center">
                <% 	if(canManage.equalsIgnoreCase("true")){ %>
                    <tr>
                        <td height="25" >
                            <input type=button class="btnTras" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="javascript:<%=insertFunction%>()">&nbsp;
                            <input type=button class="btnTras" id=innerbutton value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onclick="javascript:<%=deleteFunction%>()">
                        </td>
                    </tr>
                <% 	} %>
                </table>
                <table width="100%" cellspacing="1" cellpadding="1" border="0" id="<%=tableName%>" bgcolor=AABDC6>
                    <tr bgcolor="#D6DBE7"  align=center>
                        <td width="20%" id=tb_blue colspan="2"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                        <td width="20%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                        <td width="30%" id=tb_blue><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                        <td width="30%" id=tb_blue>e-mail</td>
                    </tr>
                    <tr id="tempTableRowName" style="display:none" bgcolor="#ffffff" onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
                        <td width="3%" id=tb_gray align="" ><input type="checkbox" name="<%=checkBoxName%>"></td>
                        <td width="17%" id=tb_gray>&nbsp;</td>
                        <td width="20%" id=tb_gray>&nbsp;</td>
                        <td width="30%" id=tb_gray>&nbsp;</td>
                        <td width="30%" id=tb_gray>&nbsp;</td>
                    </tr>
                </table>
