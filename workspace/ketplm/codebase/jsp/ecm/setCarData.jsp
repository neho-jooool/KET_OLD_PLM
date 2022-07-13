<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="e3ps.ecm.beans.ProdEcoBeans
                            ,e3ps.common.util.StringUtil
                            ,java.util.ArrayList
                            ,java.util.Hashtable" %>
                            
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                            
                            
<%
    String condition = request.getParameter("condition");
    String type = request.getParameter("type");
    String selectedValue = request.getParameter("selectedvalue");

    ProdEcoBeans beans = new ProdEcoBeans();
    ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();
    Hashtable<String, String> code = null;
    if( type.equals("maker") )
    {
        codeList = beans.getCarMakerList( condition );
    }
    else if( type.equals("car") )
    {
        codeList = beans.getCarCategory( condition );
    }
     Kogger.debug( "codeList :------------------------------> " + codeList.size() );

%>
<script language="javascript">
    if('<%=type%>' == 'maker')
    {
        parent.delCarData( 'document.forms[0].car_maker' );
        parent.setCarData( 'document.forms[0].car_maker', '전체', '', '');
        parent.delCarData( 'document.forms[0].car_category' );
        parent.setCarData( 'document.forms[0].car_category', '전체', '', '');
    }
    else
    {
        parent.delCarData( 'document.forms[0].car_category' );
        parent.setCarData( 'document.forms[0].car_category', '전체', '', '');
    }
    <%
    for( int cnt=0; cnt < codeList.size(); cnt++ )
    {
        code = codeList.get(cnt);
    %>
        if( '<%=type%>' == 'maker')
        {
            parent.setCarData( 'document.forms[0].car_maker', '<%=code.get("name")%>', '<%=StringUtil.trim(code.get("code"))%>', '<%=selectedValue%>');
        }
        else if( '<%=type%>' == 'car')
        {
            parent.setCarData( 'document.forms[0].car_category', '<%=code.get("name")%>', '<%=StringUtil.trim(code.get("code"))%>', '<%=selectedValue%>');
        }
    <%
    }
    %>
</script>
<html>
<body>
<form>
<input type="hidden"  name="condition">
<input type="hidden"  name="type">
</form>
</body>
</html>
