<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.io.*"%>
<%
	String page_name = StringUtil.checkNull(request.getParameter("page_name"));
	String path = "";
	if(page_name.equals("work") || page_name.equals("report")){
		path = "D:\\Upload\\cost_file\\보고서";
	}else{
		path = "D:\\Upload\\cost_file\\원가산출요청";
	}

	String tempFileName = java.net.URLDecoder.decode(request.getParameter("file_name"), "utf-8");

	String filePath = path+"\\"+tempFileName;

	java.io.File tempFile = new java.io.File(filePath);
	String agentType = request.getHeader("User-Agent");
	try {
		if (!tempFile.exists() || !tempFile.canRead()) {
			out.println("<script>alert('File Not Found');</script>");
			out.println("<script>self.close();</script>");
			return;
		}
	} catch (Exception e) {
		e.printStackTrace();
		out.println("<script>alert('File Not Found');</script>");
		out.println("<script>self.close();</script>");
		return;
	}

	boolean flag = false;
	if (agentType != null && agentType.indexOf("MSIE 5.5") >= 0)
		flag = true;

	tempFileName = java.net.URLEncoder.encode(tempFileName, "UTF-8");

    if (flag) {
		response.setHeader("Content-Type", "doesn/mater;charset=8859_1");
		response.setHeader("Content-Disposition", "attachment;filename="+tempFileName +";");
	} else {
		response.setContentType("application/octet-stream");
		response.setContentLength((int)tempFile.length());
		response.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
		response.setHeader("Content-Disposition", "attachment;filename="+tempFileName.replaceAll("\\+", "%20") +";");
		response.setHeader("Content-Type", "application/x-msdownload");

	}
	response.setHeader("Content-Transfer-Encoding", "binary;");
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	out.clear();
	out = pageContext.pushBody();

	byte readByte[] = new byte[4096];
	try {
		InputStream bufferedinputstream =new FileInputStream(tempFile);
		int i;
		while ((i = bufferedinputstream.read(readByte, 0, 4096)) != -1){
			response.getOutputStream().write(readByte, 0, i);
		}
		bufferedinputstream.close();
	} catch (Exception _ex) {
		_ex.printStackTrace();
	}finally{
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	/*try {
	InputStream fin =new FileInputStream(tempFile);
	OutputStream outs = response.getOutputStream();
	int read;
	while((read = fin.read(readByte,0,4096))!= -1){
		outs.write(readByte,0,read);
	}
		outs.flush();
		outs.close();
		fin.close();
	} catch (Exception _ex) {
		_ex.printStackTrace();
	}*/
%>