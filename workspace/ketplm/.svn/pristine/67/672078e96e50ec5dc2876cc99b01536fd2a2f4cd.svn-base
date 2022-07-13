<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Vector" %>
<%@page import="e3ps.sap.StdInfoInterface" %>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%
	Vector vec = new Vector();

	try
	{
		/*
		vec = StdInfoInterface.getVendorInfo();

        Kogger.debug( "========> sapJCOConnectionTest : vec size : " + vec.size() );

		for( int i = 0; i < vec.size(); i++ )
		{
			out.print( "<br>" + vec.elementAt(i) );
		}
		*/

		StdInfoInterface.testBOM();
	}
	catch( Exception e )
	{
	    Kogger.error(e);
	}
%>
