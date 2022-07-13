<%@ page contentType="text/html;charset=utf-8"%>

<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%
	ArrayList SearchStore	= (ArrayList)request.getAttribute("SearchStore");
	Hashtable StoreMap 		= null;

	String store					= StringUtil.checkNull(request.getParameter("store"));

	String Search_ok			= StringUtil.checkNull(request.getParameter("Search_ok"));
	String page_name			= StringUtil.checkNull(request.getParameter("page_name"));

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>물류흐름 </title>
<style type="text/css">
<!--
.style1 {font-size: 12px;text-align:center}
.style2 {font-size: 12px;text-align:right}
.style5 {font-size: 12px; background-color:#D2D2D2; text-align: center; font-weight: bold; color:#333333}
.style3 {font-size: 12px; font-weight: bold;color:#4F4F4F;}
#bg_color {background:#E6E6E6}
-->
</style>
<script type = "text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type = "text/javascript" src="/plm/jsp/cost/js/ajax/jsAjax.js"></script>
</head>

<script language="javascript">

/**********************************************
 직접 입력선택2
 **********************************************/
 function SearchStore()
 {
 	var form = document.forms[0];

 	form.cmd.value = "popStore";
 	form.action =  '/plm/servlet/e3ps/costComServlet';
 	if(form.Search_ok.value != 'no'){
 		form.submit();
 	}else{
 		all_select();
 	}
 }

 function chk_na()
  {
	if (document.distri_input.nab_chk.checked == true)
	{
		document.distri_input.dest_txt.disabled = false;
    	document.distri_input.dest_txt.style.background = "#FFFFFF";
    	document.distri_input.dest.disabled = true;
	}
	else
	{
		document.distri_input.dest_txt.disabled = true;
    	document.distri_input.dest_txt.style.background = "#E6E6E6";
    	document.distri_input.dest.disabled = false;
   		document.distri_input.dest_txt.value = "";
	}

  }

 function all_select()
  {
	 var form = document.forms[0];
	 var i = form.store.options.selectedIndex;
	 var j = form.dest.options.selectedIndex;
	 depth_set(form.store.options[i].value,'store');
     depth_set(form.dest.options[j].value,'distri_cost');
  }

 function depth_set(parentValue, child)
  {
	 parentValue = escape(encodeURIComponent(parentValue));
	 var url = "/plm/jsp/cost/costdb/distriAction.jsp?code=" + parentValue +"&child="+ child;
	 callServer(url, setNextDept);

  }

function setNextDept(req) {
	var xmlDoc = req.responseXML;

	var showElements = xmlDoc.selectNodes("//message");
	var child = showElements[0].getElementsByTagName("l_child")[0].text;


	showElements = xmlDoc.selectNodes("//data_info");
	if(child == 'store'){
		var l_oid = showElements[0].getElementsByTagName("l_oid");
		var l_name = showElements[0].getElementsByTagName("l_name");

		var arr = new Array();
		for(var i = 0; i < l_oid.length; i++) {
			subArr = new Array();
			subArr[0] = decodeURIComponent(l_oid[i].text);
			subArr[1] = decodeURIComponent(l_name[i].text);
			arr[i] = subArr;
		}

		var fTD = document.all.item('dest');
		var childList = document.getElementById('dest');

		var len = childList.length;
		for(var j = len ; j >= 0 ; j--){
			childList.remove(j);
		}
		var newSpan;
		for(var i = 0; i < arr.length; i++) {
			newSpan = document.createElement("option");
			newSpan.innerHTML = arr[i][1];
			newSpan.value= arr[i][0];
			fTD.appendChild(newSpan);
		}
	}else{
		var l_distri = showElements[0].getElementsByTagName("l_distri")[0].text;
		document.distri_input.distri_cost.value = l_distri;
	}
}

 function submit_dis()
  {
	<%if(page_name.equals("work")){ %>
		opener.costWork.store.value = document.distri_input.store.value;
		if (document.distri_input.nab_chk.checked == true)
		{
			opener.costWork.dest.value = document.distri_input.dest_txt.value;
		}
		else
		{
			opener.costWork.dest.value = document.distri_input.dest.value;
		}
	<%}%>
	for(var ii=0; ii< opener.GridObj3.getRowCount(); ii++) {

		opener.GridObj3.SetCellValue("store" , ii, document.distri_input.store.value);

		if (document.distri_input.nab_chk.checked == true)
		{
			opener.GridObj3.SetCellValue("dest" , ii, document.distri_input.dest_txt.value);
			opener.GridObj3.SetCellValue("distri_cost",ii,"1100");
		}
		else
		{
			opener.GridObj3.SetCellValue("dest" , ii, document.distri_input.dest.value);
			opener.GridObj3.SetCellValue("distri_cost",ii,document.distri_input.distri_cost.value);
		}

	}
	self.close();
  }
</script>
<body onload="SearchStore();">
<Form method="post" name="distri_input">
<input type="hidden" name="distri_cost" value="">
<input type="hidden" name="Search_ok" value="<%=Search_ok%>">
<input type="hidden" name="wise_gb" value="No">
<input type="hidden" name="cmd" value="popStore">
<input type="hidden" name="page_name" value="<%=page_name%>"">

<table width="434" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2"><table width="434" height="63" border="0" cellpadding="0" cellspacing="1">
      <tr>
        <td height="19" colspan="4" class="style5">물류흐름</td>
      </tr>
      <tr>
        <td width="68" height="20" class="style5">생산처</td>
        <td width="82" class="style5">보관</td>
        <td colspan="2" class="style5">납입처 <input type="checkbox" name="nab_chk" value="Yes"  onclick="chk_na();" > 직접입력</td>
      </tr>
      <tr>
        <td class="style1"><select name="start_pro" class="style1">
          <option value="생산1">생산1</OPTION>
          <option value="생산2">생산2</OPTION>
          <option value="생산3">생산3</OPTION>
          <option value="생산4">생산4</OPTION>
          <option value="중국">중국</OPTION>
          <option value="외주">외주</OPTION>
        </select></td>
        <td class="style1"><select name="store" onChange="javascript:all_select();">
         	  <%if(SearchStore != null && SearchStore.size() > 0){
         	 		for(int j = 0; j < SearchStore.size(); j++) {
              			StoreMap = (Hashtable)SearchStore.get(j);
              %>
						<option value="<%=StoreMap.get("store") %>" ><%=StoreMap.get("store") %></option>
			  <%
              		}
         	    }
			  %>
          </select></td>
         <td width="172" class="style1">
          <select name="dest" onChange="javascript:depth_set(this.value,'distri_cost');">
          <option value="">선택</option>>
          </select></td>
        <td width="107" class="style1"><input name="dest_txt" type="text" size="15" id="bg_color" disabled ></td>
      </tr>
    </table>
	</td>
  </tr>
  <tr>
    <td width="209">&nbsp;</td>
    <td width="225" class="style2"><input name="dis_ok" type="button" class="style1" onClick= "submit_dis()" value="확 인"></td>
  </tr>
</table>
</form>
</body>
</html>