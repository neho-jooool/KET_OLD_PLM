<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.CommonUtil,
                                e3ps.dms.entity.KETProjectDocument,
                                e3ps.ecm.beans.EcmUtil"%>

<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
    <results>
        <contents>

        <data_info>
<%
    request.setCharacterEncoding("UTF-8");

    String oid = request.getParameter("oid");
    String no = request.getParameter("no");
    String name = request.getParameter("name");
    String ver = request.getParameter("ver");
    String inx = request.getParameter("inx");

    oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
    no= java.net.URLDecoder.decode(no==null?"":no,"euc-kr");
    name= java.net.URLDecoder.decode(name==null?"":name,"euc-kr");
    ver= java.net.URLDecoder.decode(ver==null?"":ver,"euc-kr");
    inx= java.net.URLDecoder.decode(inx==null?"":inx,"euc-kr");

    KETProjectDocument ketProjectDocument = (KETProjectDocument)CommonUtil.getObject(oid);
        boolean flag = false;
        flag = EcmUtil.existEcaDocLink(ketProjectDocument);

%>
      <l_flag><![CDATA[<%=flag%>]]></l_flag>
      <l_oid><![CDATA[<%=oid%>]]></l_oid>
      <l_no><![CDATA[<%=no%>]]></l_no>
      <l_name><![CDATA[<%=name%>]]></l_name>
      <l_ver><![CDATA[<%=ver%>]]></l_ver>
      <l_inx><![CDATA[<%=inx%>]]></l_inx>

    </data_info>
        </contents>
    </results>
</stdinfo>


