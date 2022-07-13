<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.query.*"%>
<%@page import="e3ps.common.util.*,e3ps.common.code.*"%>
<%@page import = "e3ps.doc.*,e3ps.doc.beans.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<stdinfo>
    <results>
        <contents>
<%
    String message = "false";

    String command = request.getParameter("command");
    if(command == null) {
        command = "";
    }
    Kogger.debug("command------------------------------" + command );
    if("selectModel".equals(command)) {
        %>
                <data_info>
        <%
                String code = request.getParameter("code");
                code = java.net.URLDecoder.decode(code==null?"":code,"euc-kr");
                NumberCode nc = (NumberCode)NumberCodeHelper.manager.getNumberCode("MODELCODE", code);
                String ncDesc = StringUtil.checkNull(nc.getDescription());

        %>
                <l_modelname><![CDATA[<%=ncDesc%>]]></l_modelname>


        <%


%>
                    </data_info>
<%	}

    if("select".equals(command)) {
%>
            <data_info>
<%
        String oid = request.getParameter("oid");
        oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

        ReferenceFactory rf = new ReferenceFactory();
        OEMProjectType parent = (OEMProjectType)rf.getReference(oid).getObject();
        String subAttrName = parent.getName();

        Vector tMap2 = NumberCodeHelper.manager.getNumberCodeLevel("OEMTYPECODE", "parent");

        for(int i = 0; i < tMap2.size(); i++) {
            NumberCode tNum = (NumberCode)tMap2.get(i);
            NumberCode parentNC = null;
            Object oo= tNum.getParent();
            if(oo != null){
                parentNC =(NumberCode) oo;
            }
                if(parentNC != null){
                    if(subAttrName.equals(parentNC.getName())) {

    %>
            <l_oemname><![CDATA[<%=tNum.getName()%>]]></l_oemname>
            <l_oemoid><![CDATA[<%=tNum.getCode()%>]]></l_oemoid>

    <%				}

                }

        }
        Vector tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "parent");
        for(int i = 0; i < tMap.size(); i++) {
            NumberCode tNum = (NumberCode)tMap.get(i);
            NumberCode parentNC = null;
            Object oo= tNum.getParent();
            if(oo != null){
                parentNC =(NumberCode) oo;
            }
                if(parentNC != null){
                    if(subAttrName.equals(parentNC.getName())) {

    %>
            <l_modelname><![CDATA[<%=tNum.getName()%>]]></l_modelname>
            <l_modeloid><![CDATA[<%=tNum.getCode()%>]]></l_modeloid>

    <%				}

                }

        }
        QuerySpec subqs = OEMTypeHelper.getCodeQuerySpec(parent);
        QueryResult subqr = PersistenceHelper.manager.find(subqs);

        ArrayList list = new ArrayList();
        Object obj[] = null;
        OEMProjectType oemObject = null;
        int k = 0;

        while (subqr.hasMoreElements()){
            obj = (Object[])subqr.nextElement();
            oemObject = (OEMProjectType)obj[0];
            list.add(k, oemObject);
            k++;
        }


        String oemTypeName = "";
        String oemTypeOid = "";
        OEMProjectType oemType = null;
        for(int i = 0; i < list.size(); i++) {
            oemType = (OEMProjectType)list.get(i);

            oemTypeName = oemType.getName();
            oemTypeOid = oemType.getPersistInfo().getObjectIdentifier().getStringValue();
%>
                <l_name><![CDATA[<%=oemTypeName%>]]></l_name>
                <l_oid><![CDATA[<%=oemTypeOid%>]]></l_oid>

<%
        }
%>
            </data_info>
<%
    }


    if("oemLevelSelect".equals(command)) {
        String oid = request.getParameter("oid");
        oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        ReferenceFactory rf = new ReferenceFactory();

        int plan = 0 ;

        OEMProjectType typeObject = (OEMProjectType)CommonUtil.getObject(oid);
        ArrayList oem_al = new ArrayList();
        oem_al = OEMTypeHelper.getOEMTypeTree(typeObject);
        String levelStr = "";
        boolean addOEMCheck = false;

        StringBuffer attrHtml = new StringBuffer();


        attrHtml.append("<table id='schedPlanID' border='0' cellpadding='0' cellspacing='0' width='100%' >")
        .append("<COL width='28%'><COL width='18%'><COL width='18%'><COL width='18%'><COL width='18%'>");
        for(int i = 0 ; i < oem_al.size() ; i++){
            addOEMCheck =false;
            OEMProjectType oemType = (OEMProjectType)CommonUtil.getObject((String)oem_al.get(i));
            if(oemType.getOLevel()==0){
                levelStr = "";
            }else if(oemType.getOLevel() == 1){
                levelStr = "...";
            }else if(oemType.getOLevel() == 2) {
                levelStr = "......";
                addOEMCheck = true;
            }




            attrHtml.append("<tr>");


            if(addOEMCheck){
                attrHtml.append("<td class=tdwhiteM >&nbsp;"+oemType.getName()+"</td>")
                .append("<input type='hidden' name=oemtypeOid value="+CommonUtil.getOIDString(oemType)+">")
                .append("<td class=tdwhiteR >")
                .append("<input name=oemStartDate"+plan+" value='' class=txt_field size=12 maxlength=15  onChange='javascript:isValidDate(this, 8);' />")
                .append("<input type=button class=buttoncalendar value='' onclick=\"javascript:openCal('oemStartDate"+plan+"');\"/>")
                .append("<a href=\"JavaScript:clearDate('oemStartDate"+plan+"')\"><img src='/plm/portal/images/img_common/x.gif' border=0></a>")
                .append("</td>")
                .append("<td class=tdwhiteR >")
                .append("<input name=oemEndDate"+plan+" value='' class=txt_field size=12 maxlength=15  onChange='javascript:isValidDate(this, 8);' />")
                .append("<input type=button class=buttoncalendar value='' onclick=\"javascript:openCal('oemEndDate"+plan+"');\"/>")
                .append("<a href=\"JavaScript:clearDate('oemEndDate"+plan+"')\"><img src='/plm/portal/images/img_common/x.gif' border=0></a>")
                .append("</td>")
                .append("<td class=tdwhiteR >")
                .append("<input name=dsStartDate"+plan+" value='' class=txt_field size=12 maxlength=15  onChange='javascript:isValidDate(this, 8);' />")
                .append("<input type=button class=buttoncalendar value='' onclick=\"javascript:openCal('dsStartDate"+plan+"');\"/>")
                .append("<a href=\"JavaScript:clearDate('dsStartDate"+plan+"')\"><img src='/plm/portal/images/img_common/x.gif' border=0></a>")
                .append("</td>")
                .append("<td class=tdwhiteR >")
                .append("<input name=dsEndDate"+plan+" value='' class=txt_field size=12 maxlength=15  onChange='javascript:isValidDate(this, 8);' />")
                .append("<input type=button class=buttoncalendar value='' onclick=\"javascript:openCal('dsEndDate"+plan+"');\"/>")
                .append("<a href=\"JavaScript:clearDate('dsEndDate"+plan+"')\"><img src='/plm/portal/images/img_common/x.gif' border=0></a>")
                .append("</td>");

                plan++;

            }

            attrHtml.append("</tr>");

        }
            attrHtml.append("</table>");
            attrHtml.append("<input type=hidden name=countPlan value="+plan+">");

            //Kogger.debug("attrHtml.toString()==>" + attrHtml.toString());
            message = "true";
%>
            <data_info>
                <l_attrHtml><![CDATA[<%=attrHtml.toString()%>]]></l_attrHtml>
            </data_info>
<%

    }
%>


            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
        </contents>
    </results>
</stdinfo>
