<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.part.WTPart"%>


<%

    String itemType = request.getParameter("itemType");
    Vector data = new Vector();

    //Kogger.debug("itemType = " + itemType);


    if(itemType != null && itemType.length() > 0){
        //Kogger.debug("itemType = " + WTURLEncoder.decode(itemType));
        data = NumberCodeHelper.manager.getNumberCodeLevelType("MOLDPRODUCTSTYPE", itemType);
    }else{
        data = NumberCodeHelper.manager.getNumberCodeLevelType("MOLDPRODUCTSTYPE", "Press");
        Vector data2 = NumberCodeHelper.manager.getNumberCodeLevelType("MOLDPRODUCTSTYPE", "Mold");
        data.addAll(data2);
    }

    Kogger.debug("gggg ===== " + data.size());


//	Kogger.debug("oids.size() == " + oids.size() + " " + qs);
%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>

<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="com.ptc.ddl.servlet.WTURLEncoder"%>
<stdinfo>
            <results>
                <contents>
                    <data_info>


                    <%for(int i = 0; i < data.size(); i++){
                        NumberCode numberCode = (NumberCode)data.get(i);
                        String name = numberCode.getName();
                        String code = numberCode.getCode();
                        %>


                        <l_Code><![CDATA[<%=code%>]]></l_Code>
                        <l_name><![CDATA[<%=name%>]]></l_name>
                    <%}%>
                    </data_info>

                    <message>
                        <l_message><![CDATA[<%="ggggfffffmmm"%>]]></l_message>
                    </message>
                </contents>
            </results>
</stdinfo>
