<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ext.ket.bom.query.*"%>
<%@page import="ext.ket.part.base.service.internal.associate.PartEpmRelation"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="org.json.*"%>
<%@page import="wt.util.*"%>
<%@page import="e3ps.common.drm.*"%>
<%@page import="javax.servlet.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

String filepath = request.getParameter("filepath");


System.out.println(filepath.lastIndexOf("/"));



String filename = filepath.substring(filepath.lastIndexOf("/")+1);
System.out.println(filename);


String userAgent = request.getHeader("User-Agent");
boolean ie = userAgent.indexOf("MSIE") > -1;
// file path
//String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");

//String folder = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/bom/tmp/";
String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
String folder = sWtHome + "/temp/download/";
String ketHost =  request.getServerName();

String downFilePath = folder+filename;



//URI fileUrl  = new URI("http://"+ketHost + filepath);
File excelFileObj = new File(downFilePath);

try {
        
        // //////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Added by MJOH, 2011-04-18
        // 엑셀 다운로드 파일 DRM 암호화 적용//e3ps.common.drm
        // String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
        // excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
        // //////////////////////////////////////////////////////////////////////////////////////////////////////////

        FileInputStream fin = new FileInputStream(downFilePath);
        
        
        int ifilesize = (int) excelFileObj.length();
        byte b[] = new byte[ifilesize];
     
        
        response.setContentLength(ifilesize);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + excelFileObj.getName() + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        out.clear();
        OutputStream oout = response.getOutputStream();
        
        fin.read(b);
        oout.write(b, 0, ifilesize);
        oout.close();
        fin.close();
    } catch (Exception e) {
        //e.printStackTrace();
        //throw e;
    } finally {
        if (excelFileObj.isFile()) 
        {
            excelFileObj.delete();
        }
    }

%>