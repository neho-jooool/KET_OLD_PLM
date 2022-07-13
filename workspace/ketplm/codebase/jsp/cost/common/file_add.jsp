<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%
		String file_type   = StringUtil.checkNull(request.getParameter("file_type"));
		String pk_cr_group = StringUtil.checkNull(request.getParameter("pk_cr_group"));
		String report_pk = StringUtil.checkNull(request.getParameter("report_pk"));
		String page_name   = StringUtil.checkNull(request.getParameter("page_name"));
		String file_2_name = StringUtil.checkNull(request.getParameter("file_2_name"));

		String file_3_name   = StringUtil.checkNull(request.getParameter("file_3_name"));
		String file_4_name   = StringUtil.checkNull(request.getParameter("file_4_name"));
		String file_5_name   = StringUtil.checkNull(request.getParameter("file_5_name"));
		String file_6_name   = StringUtil.checkNull(request.getParameter("file_6_name"));
		String wise_gb         = "No";
		String DirectoryPath   = "";
		String file_1          = "";
		String file_name       = "";
		String search_gb       = StringUtil.checkNull(request.getParameter("search_gb"));
		if(search_gb.equals("")){
			search_gb = "ok";
		}

		ArrayList SearchFile = (ArrayList)request.getAttribute("SearchFile");
		Hashtable FileMap = null;

		if(SearchFile!= null){
			for(int i=0; i<SearchFile.size(); i++){
				FileMap = (Hashtable)SearchFile.get(i);
				if(page_name.equals("report")){
					if(file_type.equals("file_1")){
						file_1 = (String)FileMap.get("file_1") ;
					}else if(file_type.equals("file_2")){
						file_1 = (String)FileMap.get("file_2") ;
					}else if(file_type.equals("file_3")){
						file_1 = (String)FileMap.get("file_3") ;
					}else if(file_type.equals("file_4")){
						file_1 = (String)FileMap.get("file_4") ;
					}else if(file_type.equals("file_5")){
						file_1 = (String)FileMap.get("file_5") ;
					}else if(file_type.equals("file_6")){
						file_1 = (String)FileMap.get("file_6") ;
					}
				}else if(page_name.equals("work") ){
					file_1 = (String)FileMap.get("file_1") ;
				}else{
					if(file_type.equals("file_1")){
						file_1 = (String)FileMap.get("file_1") ;
					}else if(file_type.equals("file_2")){
						file_1 = (String)FileMap.get("file_2") ;
					}else if(file_type.equals("file_3")){
						file_1 = (String)FileMap.get("file_3") ;
					}
				}
			}
		}


		file_name = file_1;
		//file_name = java.net.URLEncoder.encode(new String(file_name.getBytes("UTF-8")));

		String del_file = file_1.replaceAll("D:", "");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>파일첨부</title>
<style type="text/css">
<!--
.style12 {font-size: 12px;text-align:left; }
.style5 {font-size: 12px; text-align: center; font-weight: bold; color:#333333}
body {
	margin: 25 0 0 0px;
}
-->
</style>
</head>
<script language="JavaScript">
function search(){
	var search_gb = document.file_search.search_gb.value;
	document.file_search.file_2_name.value = '<%=file_2_name%>';
	document.file_search.file_3_name.value = '<%=file_3_name%>';
	document.file_search.file_4_name.value = '<%=file_4_name%>';
	document.file_search.file_5_name.value = '<%=file_5_name%>';
	document.file_search.file_6_name.value = '<%=file_6_name%>';

	if(search_gb == 'ok'){
		document.file_search.submit();
	}else{
		<%
		if(!file_2_name.equals("")){
			file_2_name = java.net.URLDecoder.decode(file_2_name, "utf-8");
		}
		if(!file_3_name.equals("")){
			file_3_name = java.net.URLDecoder.decode(file_3_name, "utf-8");
		}
		if(!file_4_name.equals("")){
			file_4_name = java.net.URLDecoder.decode(file_4_name, "utf-8");
		}
		if(!file_5_name.equals("")){
			file_5_name = java.net.URLDecoder.decode(file_5_name, "utf-8");
		}
		if(!file_6_name.equals("")){
			file_6_name = java.net.URLDecoder.decode(file_6_name, "utf-8");
		}
		%>
		document.file_search.file_2_name.value = '<%=file_2_name%>';
		document.file_search.file_3_name.value = '<%=file_3_name%>';
		document.file_search.file_4_name.value = '<%=file_4_name%>';
		document.file_search.file_5_name.value = '<%=file_5_name%>';
		document.file_search.file_6_name.value = '<%=file_6_name%>';
	}

}
 /**********************************************
 * 등록된 파일보기
 **********************************************/
function file_call1()
{
	var file_name = '<%=file_name%>';

	var page_name = document.file_search.page_name.value;
	file_name = escape(encodeURIComponent(file_name));
	var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name="+page_name;
	window.open(url);


}

function del_file()
{
	document.file_up.file_del.value = "ok";
	var file_del = document.file_up.file_del.value;
	var file_name = '<%=del_file%>';
	file_name = escape(encodeURIComponent(file_name));
	var page_name = document.file_search.page_name.value;
	var file_type = document.file_search.file_type.value;
	var pk_cr_group = '<%=pk_cr_group%>';
	var report_pk = '<%=report_pk%>';
	document.file_up.action = "/plm/jsp/cost/common/file_upload.jsp?file_del="+file_del+"&file_name="+file_name+"&page_name="+page_name+"&file_type="+file_type+"&pk_cr_group="+pk_cr_group+"&report_pk="+report_pk;;
	document.file_up.submit();
}

function Save()
{
	var file_type = document.file_search.file_type.value;
	var page_name = document.file_search.page_name.value;
	var pk_cr_group = '<%=pk_cr_group%>';
	var report_pk = '<%=report_pk%>';
	var file_2_name = '<%=file_2_name%>';
	var file_3_name = '<%=file_3_name%>';
	var file_4_name = '<%=file_4_name%>';
	var file_5_name = '<%=file_5_name%>';
	var file_6_name = '<%=file_6_name%>';
	if(file_2_name != null){
		file_2_name = escape(encodeURIComponent(file_2_name));
	}
	if(file_3_name != null){
		file_3_name = escape(encodeURIComponent(file_3_name));
	}
	if(file_4_name != null){
		file_4_name = escape(encodeURIComponent(file_4_name));
	}
	if(file_5_name != null){
		file_5_name = escape(encodeURIComponent(file_5_name));
	}
	if(file_6_name != null){
		file_6_name = escape(encodeURIComponent(file_6_name));
	}

	document.file_up.action = "/plm/jsp/cost/common/file_upload.jsp?file_type="+file_type+"&page_name="+page_name+"&pk_cr_group="+pk_cr_group+"&report_pk="+report_pk+"&file_2_name="+file_2_name+"&file_3_name="+file_3_name+"&file_4_name="+file_4_name+"&file_5_name="+file_5_name+"&file_6_name="+file_6_name;

	document.file_up.submit();
}
</script>
<body onload = "search();">
<form name="file_search" method="post" action='/plm/servlet/e3ps/costComServlet'>
<input name="wise_gb"    type="hidden"  value="No" >
<input name="cmd"        type="hidden"  value="popFile" >
<input name="search_gb"  type="hidden"  value="<%=search_gb%>" >
<input name="page_name"  type="hidden"  value="<%=page_name%>" >
<input name="report_pk"  type="hidden"  value="<%=report_pk%>" >
<input name="pk_cr_group"  type="hidden"  value="<%=pk_cr_group%>" >
<input name="DirectoryPath"  type="hidden"  value="<%=DirectoryPath%>" >
<input name="file_name"  type="hidden"  value="<%=file_name%>" >
<input name="file_type"  type="hidden"  value="<%=file_type%>" >
<input name="file_2_name"  type="hidden"  value="" >
<input name="file_3_name"  type="hidden"  value="" >
<input name="file_4_name"  type="hidden"  value="" >
<input name="file_5_name"  type="hidden"  value="" >
<input name="file_6_name"  type="hidden"  value="" >
</form>

<form name="file_up" method="post" enctype="multipart/form-data" target="_self" >
<input name="file_del" type="hidden"  value="" >
<input name="file_name"  type="hidden"  value="<%=file_name%>" >

<table width="429" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif"  border="0" onClick="Save();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_cancel.gif"  border="0" onClick="self.close();" style="cursor:pointer;"/></td>
  </tr>
</table>
<table width="429" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
  <tr>
    <td width="84" height="20" bgcolor="F2F2F2" class="style5">기존파일 </td>
    <td width="342" height="20" valign="bottom" bgcolor="#FFFFFF" class="style12" style="padding-top:1;">&nbsp;<img src="/plm/jsp/cost/acc_img/file.png" border="0" onClick="file_call1();">&nbsp;(<%=del_file%>)&nbsp;&nbsp;<img src="/plm/jsp/cost/acc_img/btn_Delete.gif"  border="0" onClick="del_file();" style="cursor:pointer;"/></td>
  </tr>
  <tr>
    <td width="84" height="20" bgcolor="F2F2F2" class="style5">첨부파일</td>
    <td width="342" height="20" bgcolor="#FFFFFF" class="style12">&nbsp;<input name="file_1" type="file" size="20" ></td>
  </tr>
</table>
</form>
</body>