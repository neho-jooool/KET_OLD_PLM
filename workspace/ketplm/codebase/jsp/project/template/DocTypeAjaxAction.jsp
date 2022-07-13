<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.query.*"%>
<%@page import="e3ps.common.util.*,e3ps.common.code.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp"%>


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
    String oid = request.getParameter("oid");
    oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
    /*
    ReferenceFactory rf = new ReferenceFactory();
    DocCodeType parent = (DocCodeType)rf.getReference(oid).getObject();
    ArrayList list = DocCodeTypeHelper.getChildCode(parent);

    String docTypeName = "";
    String docTypeOid = "";
    DocCodeType docType = null;
    for(int i = 0; i < list.size(); i++) {
      docType = (DocCodeType)list.get(i);

      docTypeName = docType.getName();
      docTypeOid = docType.getPersistInfo().getObjectIdentifier().getStringValue();
%>
        <l_name><![CDATA[<%=docTypeName%>]]></l_name>
        <l_oid><![CDATA[<%=docTypeOid%>]]></l_oid>
<%
    }
    */
%>
        <l_name><![CDATA[doctype1]]></l_name>
        <l_oid><![CDATA[null]]></l_oid>

      </data_info>
<%
  }
  else if("subcheck".equals(command)) {
    //Kogger.debug("###########DocTypeAjaxAction############");
    //Kogger.debug("###########command ="+ command);
    String oid = request.getParameter("oid");
    oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

    ReferenceFactory rf = new ReferenceFactory();
    //DocCodeType parent = (DocCodeType)rf.getReference(oid).getObject();
    //ArrayList list = DocCodeTypeHelper.getChildCode(parent);
    if(0 > 0) {
      message = "true";
    }
  }

  //문서 타입의 속성 가져오기 ...................................................................
  if("docTypeAttrHtml".equals(command)) {
    String oid = request.getParameter("oid");
    oid= java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

    ReferenceFactory rf = new ReferenceFactory();
    //DocCodeType parent = (DocCodeType)rf.getReference(oid).getObject();
    //ArrayList list = DocCodeTypeHelper.getChildCode(parent);

    StringBuffer attrHtml = new StringBuffer();
      /*
    String description = parent.getDescription()==null? "":parent.getDescription();
    if( (list.size() == 0 )&& (description.length() > 0) ) {
      e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("document");
      String[] allAttrName = conf.getArray("document.attr.name.list");
      String[] allAttr = conf.getArray("document.attr.list");
      String[] attr = description.split(";");


      HashMap allMap = new HashMap();
      for(int i=0; i < allAttr.length; i++){
        allMap.put(allAttr[i], allAttrName[i]);
      }

      String attrKey = "";
      HashMap attrMap = new HashMap();
      for(int i=0; i < attr.length; i++){
        attrKey = attr[i];
        if(allMap.containsKey(attrKey)) {
          attrMap.put(attrKey, allMap.get(attrKey));
        }
      }


      QueryResult ncQr = null;
      NumberCode numberCode = null;

      String key = "";
      String value = "";
      String attribute = "";
      int index = 0;

      String attrLabelClassName = "";
      String attrValueClassName = "";
      int attrColspan = 1;

      Set set = attrMap.keySet();
      Iterator iter = set.iterator();
      while(iter.hasNext()) {
        key = (String)iter.next();
        value = (String)attrMap.get(key);

        attrLabelClassName = "tdorgL";
        attrValueClassName = "tdwhiteL";
        attrColspan = 1;
        if(index%2 == 1) {
          attrLabelClassName = "tdorgM";
          attrValueClassName = "tdwhiteL0";
        }

        if(index % 2 == 0) {
          attrHtml.append("<tr>");

          if((index == (attrMap.size()-1)) && (attrMap.size()%2 == 1)) {
            attrColspan = 3;
            attrValueClassName = "tdwhiteL0";
          }
        }



        attrHtml.append("<td class='" + attrLabelClassName + "'>");
        attrHtml.append(value);
        attrHtml.append("</td>");

        attrHtml.append("<td class='" + attrValueClassName + "' colspan='" + attrColspan + "'>");

        attribute = "";
        if(key.indexOf("Select")>=0) {
          attribute = key.substring(0,key.indexOf("Select"));

          if("ProcessDivisionCodeSelect".equals(key)) {
            attrHtml.append("<input name='" + key + "name' value='' class='txt_field' size='22' onClick=\"javascript:codePage('"+key+"');\" readonly >");
            attrHtml.append("<input type='hidden' name='" + key + "code' value='' >");
            attrHtml.append("<input type='hidden' name='" + key + "' value='' >&nbsp;");
            attrHtml.append("<input type='button' value='" + messageService.getString("e3ps.message.ket_message", "00705") + "' onClick=\"javascript:codePage('" + key + "');\" class='s_font'>");
            attrHtml.append("<input type='button' value='삭제' onClick=\"javascript:codeDelete('" + key + "');\" class='s_font'>");
          }
          else {
            attrHtml.append("<select name='" + key + "' style='width:145;' class='fm_jmp'>");
            attrHtml.append("<option value=''>선택</option>");
            ncQr = NumberCodeHelper.manager.getQueryResult(attribute, "name");
            while(ncQr.hasMoreElements()) {
              numberCode = (NumberCode)ncQr.nextElement();

              attrHtml.append("<option value='" + numberCode.getPersistInfo().getObjectIdentifier().getStringValue() + "'>" + numberCode.getName() + "</option>");
            }

            attrHtml.append("</select>");
          }
        }
        else if(key.indexOf("Date")>=0) {
          attribute = key.substring(0,key.indexOf("Date"));

          attrHtml.append("<input name='"+key+"' class='txt_field' size='17' maxlength='15'onclick=\"javascript:openCal('"+key+"');\"readonly>");
          attrHtml.append("<input name='Button23232223' type='button' class='buttoncalendar' value='' onclick=\"javascript:openCal('"+key+"');\">");
          attrHtml.append("<a href=\"JavaScript:clearDate('"+key+"');\"><img src=\"/plm/portal/images/img_common/x.gif\" border=0></a>");
        }
        else {
          attrHtml.append("<input name='" + key + "' class='txt_field' size='22'>");
        }

        attrHtml.append("</td>");


        if( (index % 2 == 1)
          || ( (index % 2 == 0)
              && (index == (attrMap.size()-1))
              && (attrMap.size()%2 == 1) ) ) {
          attrHtml.append("</tr>");
        }

        index++;
      }
    }
    */
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
