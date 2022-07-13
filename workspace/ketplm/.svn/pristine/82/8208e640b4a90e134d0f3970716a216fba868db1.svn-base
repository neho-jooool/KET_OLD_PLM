<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.query.*"%>
<%@page import="e3ps.common.util.*,e3ps.common.code.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<stdinfo>
    <results>
        <contents>
<%
    String message = "false";

    String command = request.getParameter("command");

    if(command == null) {
        command = "";
    }

    if("select".equals(command)) {
%>
            <data_info>
<%
        String nation = request.getParameter("nation");
        String oemtype = request.getParameter("oemtype");

        nation= java.net.URLDecoder.decode(nation==null?"":nation,"euc-kr");
        oemtype= java.net.URLDecoder.decode(oemtype==null?"":oemtype,"euc-kr");

        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(NumberCode.class, true);
        if(nation!=null){
        SearchCondition sc1 = new SearchCondition(NumberCode.class, "name", "=", nation);
        qs.appendWhere(sc1, new int[] {idx});

        qs.appendAnd();
        }
        SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", oemtype);
        qs.appendWhere(sc2, new int[] {idx});

        QueryResult qr = PersistenceHelper.manager.find( qs );

        String numCodeName = "";
        String numCodeOid = "";

        while(qr.hasMoreElements()){
            Object[] o = (Object[])qr.nextElement();
            NumberCode nCode = (NumberCode) o[0];

            ArrayList list = NumberCodeHelper.getChildNumberCode2(nCode, "자동차");
            for(int i = 0 ; i < list.size(); i++){
              numCodeOid = ((NumberCode)list.get(i)).getPersistInfo().getObjectIdentifier().getStringValue();
              numCodeName = ((NumberCode)list.get(i)).getName();
%>
            <l_name><![CDATA[<%=numCodeName%>]]></l_name>
            <l_oid><![CDATA[<%=numCodeOid%>]]></l_oid>
<%
            }
        }
        %>
        <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
            </data_info>
        <%
    }
    if("select2".equals(command)){

        %>
        <data_info>
        <%
        String oid = request.getParameter("oid");
        String oemtype = request.getParameter("oemtype");
        String mode = request.getParameter("mode");
        if(mode==null) mode="";

        oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        oemtype= java.net.URLDecoder.decode(oemtype==null?"":oemtype,"euc-kr");
        long oidLong = CommonUtil.getOIDLongValue(oid);

        QuerySpec qs4 = new QuerySpec();
        int idx4 = qs4.addClassList(OEMProjectType.class, true);

        SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", oidLong );
        qs4.appendWhere(sc4, new int[] {idx4});

        qs4.appendAnd();

        SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
        qs4.appendWhere(sc5, new int[] {idx4});

        qs4.appendAnd();

        if(mode.equals("1")){
            SearchCondition sc6 = new SearchCondition(OEMProjectType.class, "isSavePlan", SearchCondition.IS_TRUE);
            qs4.appendWhere(sc6, new int[] {idx4});
        }else{
            SearchCondition sc6 = new SearchCondition(OEMProjectType.class, "isSavePlan", SearchCondition.IS_FALSE);
            qs4.appendWhere(sc6, new int[] {idx4});

        }

        ClassAttribute ca = null;
        if("CARTYPE".equals(oemtype)){
            ca = new ClassAttribute(OEMProjectType.class,"name");
        }else{
            ca = new ClassAttribute(OEMProjectType.class,"code");
        }
        qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 });

        QueryResult qr4 = PersistenceHelper.manager.find( qs4 );

        String oemName = "";
        String oemOid = "";

        while(qr4.hasMoreElements()){
            Object[] o = (Object[])qr4.nextElement();
            OEMProjectType oType = (OEMProjectType) o[0];
            String oName = oType.getName();
            String oOid = oType.getPersistInfo().getObjectIdentifier().getStringValue();

%>
            <l_name><![CDATA[<%=oName%>]]></l_name>
            <l_oid><![CDATA[<%=oOid%>]]></l_oid>
            <%
        }
            %>
            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
            </message>
            </data_info>
            <%
    }

    if("oemLevelSelect".equals(command)) {
        %>
        <data_info>
        <%
        String oid = request.getParameter("oid");
        String oemtype = request.getParameter("oemtype");

        oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        oemtype= java.net.URLDecoder.decode(oemtype==null?"":oemtype,"euc-kr");
        long oidLong = CommonUtil.getOIDLongValue(oid);

        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(OEMProjectType.class, true);
        if(oid!=null){
            ReferenceFactory rf = new ReferenceFactory();
            NumberCode nCode = (NumberCode)rf.getReference(oid).getObject();
            SearchCondition sc3 = new SearchCondition(OEMProjectType.class,"makerReference.key.id","=", oidLong);

            qs.appendWhere(sc3, new int[]{idx});
            qs.appendAnd();
        }
        int plan = 0 ;

        SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "isDisabled", SearchCondition.IS_FALSE);
        qs.appendWhere(sc4, new int[] {idx});

        qs.appendAnd();

        SearchCondition sc2 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
        qs.appendWhere(sc2, new int[] {idx});

        ClassAttribute ca = new ClassAttribute(OEMProjectType.class,"code");
        qs.appendOrderBy(new OrderBy(ca, false), new int[] { idx });

        QueryResult qr = PersistenceHelper.manager.find( qs );

        String customName = "";
        String customOid = "";
        StringBuffer attrHtml = new StringBuffer();

        attrHtml.append("<table id='schedPlanID' border='0' cellspacing='0' cellpadding='0' width='100%'>");
        attrHtml.append("<tr><td class=tab_btm2 colspan=4></td></tr>");
        attrHtml.append("<tr>");
        while(qr.hasMoreElements()){
            Object[] o = (Object[])qr.nextElement();
            OEMProjectType oType = (OEMProjectType) o[0];
                attrHtml.append(" <td width='150px' class='tdblueL'>&nbsp;"+oType.getName())
                .append("<input type='hidden' name=oemtypeOid value="+oType.getPersistInfo().getObjectIdentifier().getStringValue()+"></td>");

                if(qr.size()==plan+1){
                    attrHtml.append("<td width='240px' colspan='3' class='tdwhiteL0'>")
                    .append("<input name=oemEndDate"+plan+" value='' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />");


                }else if(plan%2==1){
                    attrHtml.append("<td width='240px' class='tdwhiteL0'>")
                    .append("<input name=oemEndDate"+plan+" value='' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />");
                }else{
                    attrHtml.append("<td width='240px' class='tdwhiteL'>")
                    .append("<input name=oemEndDate"+plan+" value='' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />");
                }
                attrHtml.append(" &nbsp;<img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"javascript:CommonUtil.deleteDateValue('oemEndDate"+plan+"');\" style=\"cursor: hand;\">")
                .append("</td>");

                if( plan%2==1){

                    attrHtml.append("</tr>");
                    attrHtml.append("<tr>");
                    }
                if(o.length==plan){
                    attrHtml.append("</tr>");
                }
                plan++;
            }

        attrHtml.append("</table>");
        attrHtml.append("<input type=hidden name=countPlan value="+plan+">");

        message = "true";
            %>
            <data_info>
                <l_attrHtml><![CDATA[<%=attrHtml.toString()%>]]></l_attrHtml>
            </data_info>

            </data_info>
            <%
    }

    if("select3".equals(command)) {
        %>
        <data_info>
        <%
        String oid = request.getParameter("oid");


        oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        long oidLong = CommonUtil.getOIDLongValue(oid);

        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(ModelPlan.class, true);
        if(oid!=null){

            SearchCondition sc3 = new SearchCondition(ModelPlan.class,"carTypeReference.key.id","=", oidLong);

            qs.appendWhere(sc3, new int[]{idx});
        }


        QueryResult qr = PersistenceHelper.manager.find( qs );

        String customName = "";
        String customOid = "";
        StringBuffer attrHtml = new StringBuffer();

        attrHtml.append("<table border='0' cellspacing='0' cellpadding='0' '>");
        while(qr.hasMoreElements()){
            Object[] o = (Object[])qr.nextElement();
            ModelPlan mp = (ModelPlan) o[0];

            int y1 = Integer.parseInt(mp.getYield1()==null?"0":mp.getYield1());
            int y2 = Integer.parseInt(mp.getYield2()==null?"0":mp.getYield2());
            int y3 = Integer.parseInt(mp.getYield3()==null?"0":mp.getYield3());
            int y4 = Integer.parseInt(mp.getYield4()==null?"0":mp.getYield4());
            int y5 = Integer.parseInt(mp.getYield5()==null?"0":mp.getYield5());
            int y6 = Integer.parseInt(mp.getYield6()==null?"0":mp.getYield6());
            int y7 = Integer.parseInt(mp.getYield7()==null?"0":mp.getYield7());
            int y8 = Integer.parseInt(mp.getYield8()==null?"0":mp.getYield8());
            int y9 = Integer.parseInt(mp.getYield9()==null?"0":mp.getYield9());
            int y10 = Integer.parseInt(mp.getYield10()==null?"0":mp.getYield10());
            int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10 ;

                %>

                <l_oid><![CDATA[<%=mp.getCarType().getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
                <l_custom><![CDATA[<%=mp.getCarType().getMaker().getName()%>]]></l_custom>
                <l_name><![CDATA[<%=mp.getCarType().getName()%>]]></l_name>
                <l_y1><![CDATA[<%=y1%>]]></l_y1>
                <l_y2><![CDATA[<%=y2%>]]></l_y2>
                <l_y3><![CDATA[<%=y3%>]]></l_y3>
                <l_y4><![CDATA[<%=y4%>]]></l_y4>
                <l_y5><![CDATA[<%=y5%>]]></l_y5>
                <l_y6><![CDATA[<%=y6%>]]></l_y6>
                <l_y7><![CDATA[<%=y7%>]]></l_y7>
                <l_y8><![CDATA[<%=y8%>]]></l_y8>
                <l_y9><![CDATA[<%=y9%>]]></l_y9>
                <l_y10><![CDATA[<%=y10%>]]></l_y10>
                <l_sum><![CDATA[<%=sum%>]]></l_sum>



                <%

            }
        attrHtml.append("</table'>");
        message = "true";
            %>
            <data_info>
                <l_attrHtml2><![CDATA[<%=attrHtml.toString()%>]]></l_attrHtml2>
            </data_info>

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
