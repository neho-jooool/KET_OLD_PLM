<%@ page language="java" 
	import="java.io.*, java.text.*, java.lang.*, java.util.*" 
	contentType="application;"%>
	
	
	<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%
 	InputStream in = null; 	
	out.clear(); //out--> jsp자체 객체 http://blog.naver.com/jjeaby?Redirect=Log&logNo=100004016377
    out=pageContext.pushBody(); //out--> jsp자체 객체
    OutputStream os = null;
    
 	try {
 		File file = new File(pageContext.getServletContext().getRealPath("/") + "/jsp/part/code_R2.pdf");
 		in = new FileInputStream(file);
 		response.reset() ;
 		response.setContentType("aapplication/pdf");
 		response.setHeader ("Content-Disposition", "attachment; filename=code_R2.pdf" );
 		response.setHeader ("Content-Length", ""+file.length() );
 		os = response.getOutputStream();
 		byte b[] = new byte[(int)file.length()];
 		int leng = 0;
 		while( (leng = in.read(b)) > 0 ){
 			os.write(b,0,leng);
 		}
 	} catch(Exception e) {
 	   Kogger.error(e);
 	} finally {
 		if(in !=null)try{in.close();}catch(Exception e){}
 		if(os !=null)try{os.close();}catch(Exception e){}
 	}
%>

