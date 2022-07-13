<%@page import="ext.ket.shared.drm.DRMHelper"%>
<%@page import="e3ps.common.util.Base64"%>
<%@page import="java.io.*"%>
<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>

<%@ page
	import="java.text.SimpleDateFormat,
                         java.util.Date,
                         e3ps.common.util.DateUtil"%>

<%
    request.setCharacterEncoding("utf-8");
    String file = null;
    String fileSuffix = DateUtil.getDateString(new Date(), new SimpleDateFormat("yyyyMMddHHMMss"));

    file = request.getParameter("File");

    if (file == null || file.equals("")) {

		file = "ExcelExport_" + fileSuffix + ".xls";
    } else {
		file = file + "_" + fileSuffix + ".xls";
    }

    if (file.indexOf(".") < 0) {

		file = file.substring(0, file.lastIndexOf(".")) + "_" + fileSuffix + "." + file.substring(file.lastIndexOf(".") + 1);
    }

    String XML = request.getParameter("TGData");

    if (XML == null)
		XML = "";

    if (XML.charAt(0) == '&') {

		XML = XML.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"").replaceAll("&apos;", "'");
    }

    //response.addHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
    //response.addHeader("Cache-Control", "max-age=1, must-revalidate");

    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    String sFilePath = sWtHome + "/temp/download/";

    File downPath = new File(sFilePath);
    if (!downPath.isDirectory()) {
		downPath.mkdir();
    }

    File destFile = new File(downPath + file);

    byte[] buff = XML.getBytes("UTF-8");

    FileOutputStream fos = null;

    fos = new FileOutputStream(destFile);
    fos.write(buff);
    fos.close();
    
    File encFile = null;
    
    try {
	    
	    FileInputStream fin = null;
	    int ifilesize = 0;
	    
	    if(DRMHelper.useDrm){
		    encFile = DRMHelper.encryptFile(destFile);
		    fin = new FileInputStream(encFile);
		    ifilesize = (int) encFile.length();
	    }else{
		    fin = new FileInputStream(destFile);
		    ifilesize = (int) destFile.length();
	    }
	    
		
		byte b[] = new byte[ifilesize];

		response.setContentLength(ifilesize);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		out.clear();
		out = pageContext.pushBody();

		ServletOutputStream oout = response.getOutputStream();

		fin.read(b);
		oout.write(b, 0, ifilesize);

		oout.close();

		//response.getOutputStream().close();
		//response.flushBuffer();

		fin.close();
    } catch (Exception e) {
		e.printStackTrace();
    } finally{
	    
	    if(destFile != null && destFile.isFile()){
		    destFile.delete();
	    }
	    
	    if(encFile != null && encFile.isFile()){
		    encFile.delete();
	    }
	    
    }

    //out.print(XML);
%>
