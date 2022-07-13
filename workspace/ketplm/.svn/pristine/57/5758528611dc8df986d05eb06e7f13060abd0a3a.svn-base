<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="com.oreilly.servlet.*"%>
<%@ page import="com.oreilly.servlet.multipart.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*, java.util.Calendar"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.net.URLDecoder"%>
<%
	String DirectoryPath = "";

	int maxSize = 1024*1024*10 ;
	File f = new File(DirectoryPath);
	if(!f.isDirectory()){
		f.mkdir();
	}
	String filesName    = null;
	String file_name_a  = "";
	String GridObj_name = "";
	String file_type    = StringUtil.checkNull(request.getParameter("file_type"));
	String file_name    = StringUtil.checkNull(request.getParameter("file_name"));
	String page_name    = StringUtil.checkNull(request.getParameter("page_name"));
	String pk_cr_group  = StringUtil.checkNull(request.getParameter("pk_cr_group"));
	String report_pk  = StringUtil.checkNull(request.getParameter("report_pk"));
	String file_2_name = java.net.URLDecoder.decode( StringUtil.checkNull(request.getParameter("file_2_name")), "utf-8");
	String file_3_name = java.net.URLDecoder.decode( StringUtil.checkNull(request.getParameter("file_3_name")), "utf-8");
	String file_4_name = java.net.URLDecoder.decode( StringUtil.checkNull(request.getParameter("file_4_name")), "utf-8");
	String file_5_name = java.net.URLDecoder.decode( StringUtil.checkNull(request.getParameter("file_5_name")), "utf-8");
	String file_6_name = java.net.URLDecoder.decode( StringUtil.checkNull(request.getParameter("file_6_name")), "utf-8");

	String file_path    = "";
	String file_del     = StringUtil.checkNull(request.getParameter("file_del"));
	String tempFileName = "";
	if(page_name.equals("work") || page_name.equals("report")){
		DirectoryPath = "D:\\Upload\\cost_file\\보고서";
	}else{
		DirectoryPath = "D:\\Upload\\cost_file\\원가산출요청";
	}

	if(file_del.equals("ok")){
		//tempFileName = new String(StringUtil.checkNull(request.getParameter("file_name")).getBytes("8859_1"), "UTF-8");
		tempFileName = java.net.URLDecoder.decode(request.getParameter("file_name"), "utf-8");
		file_path = DirectoryPath+"\\"+tempFileName;

		File fieEx = new File(file_path);
		if(fieEx.exists()){
			boolean de = fieEx.delete();
			if(de){
				//out.println("삭제");
			}
		}
		file_path = "-";
	}else{
		try {
			MultipartRequest multi;
			multi = new MultipartRequest(request,DirectoryPath,maxSize,"UTF-8",new DefaultFileRenamePolicy());
			filesName = multi.getFilesystemName("file_1");

			if(filesName == null){
				out.println("<script language=javascript>");
				out.println("alert('첨부파일이 존재하지 않습니다.');");
				out.println("history.back()");
				out.println("</script>");
				return;
			}else{
				multi.getFile(filesName);
				file_path = DirectoryPath+"\\"+filesName;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	Connection conn = null;
	conn = DBUtil.getConnection();
	try{
		CostComDao dao = new CostComDao(conn);

		dao.costFileSave(page_name,file_type,file_path,pk_cr_group,report_pk,file_2_name,file_3_name,file_4_name,file_5_name,file_6_name);

	}catch(Exception e){
		e.printStackTrace();
		e.getMessage();
	}finally{
	    DBUtil.close(conn);
	}
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>파일첨부</title>
</head>
<script language="JavaScript">
/**********************************************
등록된 파일보기
**********************************************/
function CALL()
{
	<%if(file_del.equals("ok") ){%>
		alert("삭제되었습니다.");
	<%}else{%>
		alert("등록되었습니다.");
	<%}%>

	<%if(page_name.equals("work")){%>
		GridObj_name = "GridObj3";
	<%}else{%>
		GridObj_name = "GridObj1";
	<%}%>


	var file_path = '<%=file_path%>';
	<%if(file_del.equals("ok")){%>
		file_path = "";
	<%}%>
	var file_type = '<%=file_type%>';

	<%if(page_name.equals("work")){%>
		opener.GridObj3.SetCellValue( "file_1" , 0, file_path);
	<%}else if(page_name.equals("report")){%>
		opener.show_file('<%=filesName%>',file_type);
	<%}else{%>
		opener.GridObj1.SetCellValue( "file_1" , 0, file_path);
	<%}%>

	self.close();
}
</script>
<body onload="CALL();">
</body>
</html>