<%@page import="e3ps.common.util.StringUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="/jsp/common/error.jsp"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<%
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    String valueField = StringUtil.checkNull(request.getParameter("valueField"));
    String displayField = StringUtil.checkNull(request.getParameter("displayField"));
    String popOn = request.getParameter("popOn");

    if ( mode == null || mode.length() == 0 || !mode.equals("m") )
        mode = "s";
    if ( invokeMethod == null || invokeMethod.length() == 0 )
        invokeMethod = "";

    String param = "mode=" + mode + "&invokeMethod=" + invokeMethod;
    String treeURL = "/plm/jsp/project/AddProjectDeptTree.jsp?" + param;
    String mainURL = "/plm/servlet/e3ps/ProjectServlet?command=searchPeople&" + param;
    
    // [START] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현
    mainURL += "&init=true";
    // [END] [PLM System 1차 고도화] 조직도 검색 처음 로딩시 화면만 나오게 하여 체감 속도를 높인다, 2014-06-17, 김태현

    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<base target="_self">
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01541") %></TITLE>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
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
-->
</style>
<script type="text/javascript">
<!--
    function view(id) {
        document.forms[0].action = "/plm/jsp/groupware/company/selectPeopleView.jsp?oid="+id;
        document.forms[0].submit();
    }

    function search(oid) {
    	try{
            
            var idx = 0;
            var D = Grids[idx].Data;
            var formName = 'selectPeopleList';
            
            if(oid){
                D.Data.Params = '&oid='+oid;            	
            }else{
                D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());            	
            }
            D.Data.Url = "/plm/servlet/e3ps/ProjectServlet";
            D.Data.Param.command = "searchPeople";
            Grids[idx].Reload(D);
            
            var S = document.getElementById("Status"+idx);
            if(S) S.innerHTML="Loading";
         
         }catch(e){
             alert(e.message);
         }
        //document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet?command=searchPeople";
        //document.forms[0].submit();
    }

    function seletPeople() {

        var G = Grids[<%="m".equals(mode)?"1":"0"%>];
        var arr = new Array();
        var subArr = new Array();
        var idx = 0;
        
        if(G.GetFirstVisible() == null){
        	alert('<%=messageService.getString("e3ps.message.ket_message", "01813")%>');
        	return;
        }
        
        if( G != null){

        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		<%if(!"m".equals(mode)){%>
        		if(tRow.Selected == true){
      			<%}%>
        			subArr = new Array();
                    subArr[0] = tRow.WtuserOID;  //wtuser oid
                    subArr[1] = tRow.Poid;       //people oid
                    subArr[2] = tRow.Doid;       //dept oid
                    subArr[3] = tRow.Uid;        //uid
                    subArr[4] = tRow.UserName;   //name
                    subArr[5] = tRow.DeptName;   //dept name
                    subArr[6] = tRow.Duty;       //duty
                    subArr[7] = tRow.Dutycode;   //duty code
                    subArr[8] = tRow.Email;      //email
                    arr[idx++] = subArr;	
               <%if(!"m".equals(mode)){%>
               }
               <%}%>
                
            }
        }

    <%  if ( invokeMethod.length() == 0 ) {  %>
        //modal dialog
        selectModalDialog(arr);
    <%  } else {  %>
        //open window
        selectOpenWindow(arr);
    <%  }  %>
    }

    function selectModalDialog(arrObj) {
        window.returnValue= arrObj;
        window.close();
    }
    

    <%  if(invokeMethod.length() > 0) {  %>
    
    function openerValueSet(infoArr){
    	var target = (opener || parent.opener);
    	
    	if(target.document.getElementById('<%=valueField%>')){
    		target.document.getElementById('<%=valueField%>').value = infoArr[0];	
    	}
    	
    	if(target.document.getElementById('<%=displayField%>')){
    		target.document.getElementById('<%=displayField%>').value = infoArr[4]; 
        }
    	
    	if(target.document.getElementsByName('<%=valueField%>')[0]){
    		target.document.getElementsByName('<%=valueField%>')[0].value = infoArr[0]; 
        }
    	
    	if(target.document.getElementsByName('<%=displayField%>')[0]){
    		target.document.getElementsByName('<%=displayField%>')[0].value = infoArr[4]; 
        }
        
    }

    function selectOpenWindow(arrObj) {
        //...이하 원하는 스크립트를 만들어서 작업...
        
        if(opener) {
            if(opener.<%=invokeMethod%>) {
                opener.<%=invokeMethod%>(arrObj);
            }else{
            	openerValueSet(arrObj[0]);
            }
        }else if(parent.opener) {
            if(parent.opener.<%=invokeMethod%>) {
                parent.opener.<%=invokeMethod%>(arrObj);
            }else{
            	openerValueSet(arrObj[0]);
            }
        }
        
        <%
        if(!"Y".equals(popOn)) {
        %>
        
        thiscolse();
        <%
        }
        %>
    }

    <%  }  %>
	function thiscolse() {
		self.close();
	}

	function searchMyProject(){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchMyProject.do",
            type : "POST",
            dataType : 'json',
            async : true,
            success : function(data) {
                var tBody = document.getElementById("inProgressProjDiv");
                deleteRows(tBody);
                $.each(data, function() {
                    addMyProjectRow('inProgressProjDiv', 'myProjectRadio', this);
                });
            }
        });
    }
	
	function deleteRows(tBody){
        if(tBody.rows.length > 0){
            for(var i=tBody.rows.length; i > 0; i--){
                tBody.deleteRow(tBody.rows[i]);
            }
        }
    }
	
	function addMyProjectRow(bodyId, radioName, eachData){

        var tBody = document.getElementById(bodyId);
        var innerRow = tBody.insertRow();
        var innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerHTML = "<input type='radio' name='"+ radioName +"' id='"+ radioName +"' value='"+ eachData.oid +"' onclick='projectRowOnClick(this)'>";
        
        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerText = eachData.projectname;

        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM";
        innerCell.innerText = eachData.pm;
    }
	
	function projectRowOnClick(radio){
        $.ajax({
            url : "/plm/ext/wfm/workspace/searchProjectAllMember.do",
            type : "POST",
            data : {
                projectoid : radio.value
            },
            dataType : 'json',
            async : true,
            success : function(data) {
            	var projectMembers = '';
            	var i=0;
                $.each(data, function() {
                	if(i == 0)projectMembers += this.name;                   
                	else projectMembers += ','+this.name;
                	i++;
                });
                var D = Grids[0].Data;
                var formName = 'selectPeopleList';
                D.Data.Params = 'keyvalue='+projectMembers;               
                D.Data.Url = "/plm/servlet/e3ps/ProjectServlet";
                D.Data.Param.command = "searchPeople";
                Grids[0].Reload(D);
            }
        });
    }

	function deletePeople() {
		var G = Grids[1];
        var rows = G.GetSelRows(); //선택한 Row
        if(rows=='' || rows.length == 0) {
            return;
        }
        G.RemoveRow(rows[0]);    //선택한 Row 삭제
	}
	
	function viewTodo2(oid){
        var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
        //openOtherName(url,"ViewTemplate","850","680","status=1,scrollbars=yes,resizable=1");
        //alert("aaaaaaaaaaaaaaaaaaaaa");
        window.open(url, '', "scrollbars=yes,resizable=yes,status=1, menu=no, width=850, height=680");
        //window.open(linkUrl,'',"scrollbars=yes,resizable=yes,status=1, menu=no, width=850, height=680");
    }

	function onKeyPress() {
		if (window.event) {
			if (window.event.keyCode == 13)
				search();
		} else
			return;
	}
	document.onkeypress = onKeyPress;
	
	function movePeople(){
		<%
		if("m".equals(mode)){
		%>         
        var G = Grids[0];    //대상 그리드
        var addedRow;
        var rows = G.GetSelRows(); //선택한 Row
        if(rows == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01813")%>');//선택된 항목이 없습니다.
            return;
        }
        
        var G2 = Grids[1];
        for(var i=0; i<rows.length;i++){
            //중복체크
        	for ( var tRow = G2.GetFirstVisible(); tRow; tRow = G2.GetNextVisible(tRow) ) {
        		   if(tRow.WtuserOID == rows[i].WtuserOID){
        			   alert('중복된 데이터가 존재합니다.')
        			   return;
        		   }
        	}
        	var newRow = G2.AddRow(null, null, 7);
        	newRow.NoColor = 2;
        	G2.SetString(newRow,'WtuserOID',rows[i].WtuserOID,1);
        	G2.SetString(newRow,'Poid',rows[i].Poid,1);
        	G2.SetString(newRow,'Doid',rows[i].Doid,1);
        	G2.SetString(newRow,'Uid',rows[i].Uid,1);
        	G2.SetString(newRow,'UserName',rows[i].UserName,'1');
        	G2.SetString(newRow,'DeptName',rows[i].DeptName,1);
        	G2.SetString(newRow,'Duty',rows[i].Duty,1);
        	G2.SetString(newRow,'Dutycode',rows[i].Dutycode,1);
        	G2.SetString(newRow,'Email',rows[i].Email,1);
        	G2.SetString(newRow,'PjtHistory',rows[i].PjtHistory,1);
        	G2.SetAttribute(newRow,'PjtHistory','Tip',rows[i].PjtHistoryTip,1);
        	G2.SetAttribute(newRow,'PjtHistory','OnClick',rows[i].PjtHistoryOnClick,1);
        	G2.SetAttribute(newRow,'PjtHistory','HtmlPrefix',rows[i].PjtHistoryHtmlPrefix,1);
        	G2.SetAttribute(newRow,'PjtHistory','HtmlPostfix',rows[i].PjtHistoryHtmlPostfix,1);
        	G2.RefreshRow(newRow);
        }
        <%
		}else{
        %>
        seletPeople();
        <%
		}
        %>
    }
	
	$(document).ready(function() {
    	//window.resizeTo(800,600);
        window.dialogWidth = 800+'px';
		window.dialogHeight = 600+'px';
		//tab 생성
		CommonUtil.tabs('tabs');
		// 진행중인 프로젝트 검색
        searchMyProject();
		
		$('[name=keyvalue]').focus();
	})
	
//-->
</script>
</head>
<body>
<form method="post" name="selectPeopleList">
<!-- hidden begin -->
<input type="text" name="mode" value="<%=mode%>" style="display: none;">
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>">
<!-- hidden end -->
    <table style="width: 100%;">
        <tr>
            <td valign="top">
                <table style="width: 100%;">
                    <tr>
                        <td background="/plm/portal/images/logo_popupbg.png">
                            <table style="height: 28px;">
                                <tr>
                                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01541")%><%--부서 및 사원--%></td>
                                    <td style="width: 10px;"></td>
                                </tr>
                            </table>
                        </td>
                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <td class="space10"></td>
                    </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <td width="300px" valign="top">
                            <div id="tabs">
                                <ul>
                                    <li><a class="tabref" href="#tabs-1">조직</a></li>
                                    <li><a class="tabref" href="#tabs-2">프로젝트</a></li>
                                </ul>
                                <!-- 첫번째 tab 화면 -->
                                <div id="tabs-1" class="tabMain">
                                    <iframe src="<%=treeURL%>" name="deptTree" id="deptTree" frameborder="0" width="300px" height="495px;" scrolling="auto" style="overflow-x:hidden;overflow-y:auto;"></iframe>
                                </div>
                                <div id="tabs-2">
                                    <table style="width: 300px; height: 495px;" border="0" cellspacing="0" cellpadding="0">
                                        <tr height="50%">
                                            <td valign="top">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "05101")%><%--참여중 프로젝트(진행중)--%></td>
                                                    </tr>
                                                </table>
                                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    <tr>
                                                        <td class="space5"></td>
                                                    </tr>
                                                </table>
                                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    <tr>
                                                        <td class="tab_btm2"></td>
                                                    </tr>
                                                </table>
                                                <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
                                                    <colgroup>
                                                        <col width="35">
                                                        <col width="*">
                                                        <col width="100">
                                                    </colgroup>
                                                    <tr>
                                                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></td>
                                                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113")%><%--프로젝트명--%></td>
                                                        <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00370")%><%--PM--%></td>
                                                    </tr>
                                                </table>
                                                <div style="overflow-x: hidden; overflow-y: auto; widows: 100%; height: 230px;">
                                                    <table style="width: 100%; table-layout: fixed;" border="0" cellspacing="0"
                                                        cellpadding="0">
                                                        <colgroup>
                                                            <col width="35">
                                                            <col width="*">
                                                            <col width="99">
                                                        </colgroup>
                                                        <tbody id="inProgressProjDiv"></tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </td>
                        <td align="right" valign="top">
                            <table>
                                <tr>
                                    <td><input type="text" name="keyvalue" class="txt_field" style="width: 150px;"
                                        value="<%=searchCondition.getString("keyvalue")%>">&nbsp;&nbsp;</td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:movePeople();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:thiscolse();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <table style="width: 100%;">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table> 
                            <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td valign="top" height="100%">
                                        <!-- EJS TreeGrid Start -->
                                        <div style="WIDTH: 100%; HEIGHT:  <%=("m".equals(mode)?"300px;":"495px")%>">
                                            <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/AddProjectPeopleGridLayout.jsp"
                                                Layout_Param_Mode="<%=mode %>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
                                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                                                Export_Param_File="Search_DocMultiApp_List"></bdo>
                                        </div> <!-- EJS TreeGrid Start -->
                                    </td>
                                </tr>
                            </table>
                            <%
                            if("m".equals(mode)){
                            %>
                            <table style="width: 450px; height: 100%;" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="20px" height="25px"><img src="/plm/portal/images/icon_4.png"></td>
                                    <td class="font_03">선택된 사용자</td>
                                    <td align="right">
                                        <table>
                                            <tr>
                                                <td>
                                                    <table>
                                                        <tr>
                                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:seletPeople();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a>
                                                            </td>
                                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td>&nbsp;</td>
                                                <td>
                                                    <table>
                                                        <tr>
                                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:deletePeople();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a>
                                                            </td>
                                                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" valign="top">
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="space5"></td>
                                            </tr>
                                        </table>
                                        <!-- EJS TreeGrid Start -->
                                        <div style="WIDTH: 100%; HEIGHT: 100%">
                                            <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/AddProjectPeopleGridLayout.jsp"
                                                Layout_Param_Mode="<%=mode %>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
                                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                                                Export_Param_File="Search_DocMultiApp_List"></bdo>
                                        </div> <!-- EJS TreeGrid Start -->
                                    </td>
                                </tr>
                            </table>
                            <%
                            }
                            %>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
