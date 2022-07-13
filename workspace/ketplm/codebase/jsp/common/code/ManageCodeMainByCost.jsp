<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.code.NumberCodeType,
				e3ps.common.util.CommonUtil" %>
				
<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%
NumberCodeType numCode=null;
%>
<!-- 로그 설정 임포트 끝 -->				
<jsp:include page="/jsp/common/context.jsp" flush="false"/>

<html>
<head>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<!--<base target="iframe">-->
<SCRIPT LANGUAGE="JavaScript"><!--
function selectFrame(str, tName)
{
	//alert(str + "\n" + tName);
	var srcc;
	//if( (str == 'PROCESSDIVISIONCODE') || (str == 'CHARGE') ) {
		//srcc = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=2&codetype=" + str + "&tname=" + tName;
	//} else if( (str == 'UNITDIVISIONCODE') || (str == 'JELSTDPARTCODE') ) {
		//srcc = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=" + str + "&tname=" + tName;
	//} else if( (str == 'ECHANGEREASON') ) {
		//srcc = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&codetype=" + str + "&tname=" + tName;
	//}else{
		//srcc = "/plm/servlet/e3ps/NumberCodeServlet?codetype=" + str + "&tname=" + tName;
	//}

	//if( (str == 'PJTDIVISIONCODE') || (str == 'PJTLEVELCODE') || (str == 'PJTPRODUCTCODE') || (str == 'PJTCUSTOMERCODE') || (str == 'PJTDEVCOMPANYCODE') || (str == 'PJTMAKECOMPANYCODE')|| (str == 'WORKCENTERCODE') ) {
		//srcc = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=2&selectedDepth=0&codetype=" + str + "&tname=" + tName;
	//} else {
		//srcc = "/plm/servlet/e3ps/NumberCodeServlet?codetype=" + str + "&tname=" + tName;
	//}

	var expandedDepth = -1;
	var selectedDepth = -1;
	if(str=='DIVISIONCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='LEVELCODE') {
		expandedDepth = 0;
		selectedDepth = -1;
	} else if(str=='PRODUCTCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='CUSTOMERCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='DEVCOMPANYCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='MAKECOMPANYCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='MODELCODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='WORKCENTER') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='OEMTYPECODE') {
		expandedDepth = 1;
		selectedDepth = -1;
	} else if(str=='MOLDPRODUCTSTYPE') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='CUSTOMEREVENT') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='FORMTYPE') {
		expandedDepth = 0;
		selectedDepth = -1;
	}else if(str=='SUBCONTRACTOR') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='RANK') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='PRODUCTTYPE') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='SPECTYPECHARGER') {
		expandedDepth = 1;
		selectedDepth = -1;
	}else if(str=='SPECTYPECIRCUIT') {
		expandedDepth = 1;
		selectedDepth = -1;
	}

	srcc = "/plm/jsp/common/code/AdminNumberCodeMgtFrame.jsp?expandedDepth="+expandedDepth+"&selectedDepth="+selectedDepth+"&codetype=" + str + "&tname=" + tName;

	document.all.iframe.src = srcc;
}
</SCRIPT>
</head>

<body  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">
<form name=drawingmanager>
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
   <tr>
		<td bgcolor=EDE9DD valign="top"  height="100%" valign=top>
		  <div id="numberCodeTypeListDiv" style="left:35px; top:200x; width:100%; height:100%; z-index:0; overflow-x:auto; overflow-y:auto;">
			<table width=200  border="0" cellpadding="0" cellspacing="1">
				<c_rt:forEach items="<%=NumberCodeType.getNumberCodeTypeSet()%>" var="data">
				<%
				Kogger.debug("ManageCodeMain", null, null, NumberCodeType.getNumberCodeTypeSet().length);
				
				String Key[] = {"MFTFACTORY","MOLDDIVISION","FACDIVISION","PURCHASES","RAWMATERIAL","PLATING","SCENARIO","RAWMATTHICKNESS","RAWMATWIDTH","MONETARYUNIT","CALCULATIONSTD","RESINSDIVISION","COSTMAKING","COSTREDUCEOPTION","COSTPACKING"};
				boolean isMatch = false;

				for(int i = 0; i < NumberCodeType.getNumberCodeTypeSet().length; i++) {
					numCode = NumberCodeType.getNumberCodeTypeSet()[i];
					isMatch = false;
					for(String temp : Key){
					    if(temp.equals(numCode.toString())){
						    isMatch = true;
						    break;
					    }
					}
					
					if(!isMatch){
					    continue;
					}
					
					if(numCode.getComment().equals("원재료 Maker")){
						continue;
					}else if(numCode.getComment().equals("원재료 Type")){
						continue;
					}else if(numCode.getComment().equals("원재료 특성")){
						continue;
					}else if(numCode.getComment().equals("Machine Maker")){
						continue;
					}else if(numCode.getComment().equals("Machine Type")){
						continue;
					}else if(numCode.getComment().equals("Machine Ton")){
						continue;
					}
				%>
					<tr>
						<td height="25" bgcolor=white>
							<img src="/plm/portal/icon/sub_arr.gif" width=22 height=9 border=0>
							<a href="javascript:selectFrame('<%=(numCode.getStringValue()).substring(32)%>','<%=numCode.getComment()%>')" id=sub onFocus="this.blur()">
								<%=numCode.getComment()%>
							</a>
						</td>
					</tr>
				<%
				}
				%>
			</table>
			</div>
		</td>
		<td width="100%" height="100%">

			<iframe frameborder=0 width="100%" height="100%" src="/plm/jsp/common/code/AdminNumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=-1&codetype=MFTFACTORY&tname=MFTFACTORY" name=iframe scrolling=auto></iframe>
		</td>
	</tr>
</table>
<script src='/plm/portal/js/menu.js'></script>
<input type=hidden name=myname value="코드체계관리">
<script>setPosition(null);</script>
<script>selectFrame('MFTFACTORY','샏산처>');</script>
</form>
</body>
</html>

