<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,
                 e3ps.common.web.HtmlTagUtil,
                 e3ps.part.entity.KETNewPartList,
                 e3ps.common.web.PageControl"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
                wt.fc.*,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.PhaseTemplate,
                java.util.Hashtable"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    Hashtable partList = (Hashtable)request.getAttribute("partList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");
    String adminFlag = request.getAttribute("adminFlag")+"";
    KETNewPartList ketNewPartList = new KETNewPartList();
    PageControl control = (PageControl)request.getAttribute("control");

    String rowCount = "8";
    int count = 0;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
function hideProcessing() {}

parent.document.getElementById("adminFlag").value = "<%=adminFlag%>";

//페이지 조회
function gotoPage(pageno){
    parent.document.getElementById("page").value = pageno;
    parent.doSelect();
}

function doDisplay(idx) {
    var partcodeLen;
    try {
        partcodeLen = parent.document.getElementById("partCode").value.length;
    } catch(e) {
        partcodeLen = 0;
    }

    try {
        parent.document.form.partNumber.value = document.getElementById("partNumber"+idx).value.substring(partcodeLen, document.getElementById("partNumber"+idx).value.length);
        parent.document.form.partName.value = document.getElementById("partName"+idx).value;
        parent.document.form.rawMaterial.value = document.getElementById("rawMaterial"+idx).value;
        parent.document.form.customer.value = document.getElementById("customer"+idx).value;
        parent.document.form.description.value = document.getElementById("description"+idx).value;
        parent.document.form.del.value = document.getElementById("oId"+idx).checked;
        parent.document.form.oId.value = document.getElementById("oId"+idx).value;
    } catch(e) {

    }
}

// 초기값 셋팅
function initPage(){
    parent.doClear();
    <%
    if( condition != null && !condition.isEmpty() ){
        %>
        parent.document.form.page.value = '<%=condition.get("page")%>';

        for(var inx=0; inx< parent.document.form.perPage.options.length ; inx++){
            if( parent.document.form.perPage.options[inx].value =='<%=condition.get("perPage")%>'){
                parent.document.form.perPage.options[inx].selected = true;
            }
        }
        <%
    }
    if( control != null ){
%>
        document.forms[0].totalCount.value = '<%=control.getTotalCount()%>';
<%
    }
%>

}
</script>
</head>
<body onload="initPage();">
<form name="form">
<input name="cmd" type="hidden">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sort" value="1 DESC">
<table border="0" cellspacing="0" cellpadding="0" width="780">
<tr>
  <td class="space5"></td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="780">
<tr>
  <td  class="tab_btm2"></td>
</tr>
</table>
 <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="40" class="tdblueM">No</td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="160" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%></td>
          <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
          <td width="40" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>

        </tr>
        <%
                int lastInx = 0;
                 if( control != null) {
                     Kogger.debug("control.getTotalCount = " + control.getTotalCount() + "CurrentPage = " + control.getCurrentPage() + "getInitPerPage = "+ control.getInitPerPage());
                     lastInx = control.getTotalCount() - ((control.getCurrentPage() - 1) * control.getInitPerPage());
                 }
                if( partList != null && partList.size() > 0 ){
                    int idx = 0;
                    String style = "";
                    for(int ii = 0 ; ii <  partList.size(); ii++){
                        idx = lastInx-ii;
                        ketNewPartList = (KETNewPartList) partList.get(ii+"");

                        if(ketNewPartList.getDel()) {
                            style = "style='background-color: #E9E9E9;'";
                        } else {
                            style = "";
                        }
        %>
        <tr onclick="javascript:doDisplay('<%=idx%>')">
          <td width="40" class="tdwhiteM" <%=style%>><%=idx%></td>
          <td width="70" class="tdwhiteM" <%=style%> id="partNumber<%=idx%>" value="<%=ketNewPartList.getPartNumber()%>" title="<%=ketNewPartList.getPartNumber()%>"><div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=ketNewPartList.getPartNumber()%></nobr></div></td>
          <td width="160" class="tdwhiteM" <%=style%> id="partName<%=idx%>" value="<%=ketNewPartList.getPartName()%>" title="<%=ketNewPartList.getPartName()%>"><div style="width:160;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=ketNewPartList.getPartName()%></nobr></div></td>
          <td width="150" class="tdwhiteM" <%=style%> id="rawMaterial<%=idx%>" value="<%=ketNewPartList.getRawMaterial()%>" title="<%=ketNewPartList.getRawMaterial()%>"><div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=ketNewPartList.getRawMaterial()%></nobr></div></td>
          <td width="90" class="tdwhiteM" <%=style%> id="customer<%=idx%>" value="<%=ketNewPartList.getCustomer()%>" title="<%=ketNewPartList.getCustomer()%>"><div style="width:90;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=ketNewPartList.getCustomer()%></nobr></div></td>
          <td width="60" class="tdwhiteM" <%=style%> ><%=ketNewPartList.getRegister()%></td>
          <td width="75" class="tdwhiteM" <%=style%> ><%=ketNewPartList.getRegDate()%></td>
          <td width="70" class="tdwhiteM" <%=style%> id="description<%=idx%>" value="<%=ketNewPartList.getDescription()%> " title="<%=ketNewPartList.getDescription()%>"><div style="width:70;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=ketNewPartList.getDescription()%></nobr></div></td>
          <td width="40" class="tdwhiteM0" <%=style%> >
          <%    if(ketNewPartList.getDel()) { %>
          <input type="checkbox" name="checkbox" id="oId<%=idx%>" value="<%=ketNewPartList.getSecurityLabels()%>" checked  <%=adminFlag%>/>
           <%
                     } else {
           %>
              <input type="checkbox" name="checkbox" id="oId<%=idx%>" value="<%=ketNewPartList.getSecurityLabels()%>"  <%=adminFlag%>/>
          <%
                     }
            %>
          </td>
        </tr>

        <%
                }
              } else {
          %>
            <tr>
                <td colSpan="11"  class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%>
                </td>
            </tr>
        <%
              }
        %>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" width="780">
    <tr>
      <td class="space10"></td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" width="780" id="pageControlTable">
      <%if(partList != null) {%>
          <%@include file="/jsp/ecm/PageEcmInclude.jsp" %>
    <%} %>
  </table>
</form>
</body>
</html>
