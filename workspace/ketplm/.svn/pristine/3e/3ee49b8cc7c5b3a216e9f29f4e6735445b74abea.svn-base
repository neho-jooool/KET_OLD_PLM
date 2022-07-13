<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="e3ps.ecm.beans.ProdEcoBeans
                            ,e3ps.common.util.StringUtil
                            ,java.util.ArrayList
                            ,java.util.Hashtable" %>
<%
    String type = request.getParameter("type");

    ProdEcoBeans beans = new ProdEcoBeans();
    ArrayList<Hashtable<String, String>> codeList = new ArrayList<Hashtable<String, String>>();
    ArrayList<Hashtable<String, String>> incProdTypeList = new ArrayList<Hashtable<String, String>>();
    Hashtable<String, String> code = null;
    Hashtable<String, String> incProdType = null;

    if( type.equals("1") )
    {
        codeList = beans.getCodeList( "PRODECOREASON" );
    }
    else
    {
        codeList = beans.getCodeList( "CHANGEREASON" );
        incProdTypeList = beans.getCodeList( "INCREASEPRODTYPE" );
    }

%>
<script language="javascript">

    parent.delChangeReason( 'document.forms[0].changeReason' );
    parent.setChangeReason( 'document.forms[0].changeReason', '전체', '', '');
    parent.delChangeReason( 'document.forms[0].increaseProdType' );
    parent.setChangeReason( 'document.forms[0].increaseProdType', '전체', '', '');

    <%
    for( int cnt=0; cnt < codeList.size(); cnt++ )
    {
        code = codeList.get(cnt);
    %>
        parent.setChangeReason( 'document.forms[0].changeReason', '<%=code.get("name")%>', '<%=StringUtil.trim(code.get("code"))%>', '');
    <%
    }

    if( type.equals("2") )
    {
        for( int cnt=0; cnt < incProdTypeList.size(); cnt++ )
        {
            incProdType = incProdTypeList.get( cnt );
        %>
            parent.setChangeReason( 'document.forms[0].increaseProdType', '<%=incProdType.get("name")%>', '<%=StringUtil.trim(incProdType.get("code"))%>', '');
        <%
        }
    }
    %>
</script>
<html>
<body>
<form>
<input type="hidden"  name="type">
</form>
</body>
</html>
