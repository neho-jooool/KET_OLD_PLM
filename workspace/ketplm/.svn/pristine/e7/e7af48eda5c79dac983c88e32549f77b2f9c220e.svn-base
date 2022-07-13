<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Vector,
                                    java.util.Hashtable,
                                    e3ps.common.util.*,
                                    e3ps.dms.beans.DMSUtil"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
doClear();
<%
    String divCode = "CA";
    if(CommonUtil.isMember("전자사업부")){
        divCode = "ER";
    }else if(CommonUtil.isMember("자동차사업부")){
        divCode = "CA";
    }else{
        divCode = "CA";
    }

    Hashtable tempHash = new Hashtable();
    int i;

    Vector drOid = null;
    Vector developmentStep = null;
    Vector drNo = null;
    Vector devProductName = null;
    Vector drState = null;
    Vector pjtNo = null;

    String mode = request.getParameter("mode");
    String DevelopmentStep = request.getParameter("DevelopmentStep");
    String DevProductName = request.getParameter("DevProductName");
    String RequestNo = request.getParameter("RequestNo");
    String projectNo = request.getParameter("projectNo");

    tempHash.put("developmentStep" , new String(DevelopmentStep));
    tempHash.put("devProductName" , new String(DevProductName));
    tempHash.put("requestNo" , new String(RequestNo));
    tempHash.put("projectNo" , new String(projectNo));
    tempHash.put("divCode" , new String(divCode));

    try {

        Hashtable drInfo = DMSUtil.serPopDevRequestList(tempHash);

        if(drInfo.get("drNo") instanceof Vector) {
            drOid = new Vector();
            drNo = new Vector();
            developmentStep = new Vector();
            devProductName = new Vector();
            drState = new Vector();
            pjtNo = new Vector();

            drOid = (Vector)drInfo.get("drOid");
            drNo = (Vector)drInfo.get("drNo");
            developmentStep = (Vector)drInfo.get("developmentStep");
            devProductName = (Vector)drInfo.get("devProductName");
            drState = (Vector)drInfo.get("drState");
            pjtNo = (Vector)drInfo.get("pjtNo");
        }

    } catch (Exception e) {
	Kogger.error(e);
    }

    if(drNo.size()>0){
%>

    var tBody = document.getElementById("iDrTable");
    var innerRow;
        var innerCell;

<%
        String s_drOid = null;
        String s_drNo = null;
      String s_developmentStep = null;
      String s_devProductName = null;
        String s_drState = null;
        String s_pjtNo = null;

      for(i = 0; i < drNo.size(); i++) {

            s_drNo = drNo.get(i).toString();
            s_developmentStep = developmentStep.get(i).toString();
            s_devProductName = devProductName.get(i).toString().replaceAll("\\\"", "&quot;") ;
            s_drState = drState.get(i).toString();
            s_drOid = drOid.get(i).toString();
            s_pjtNo = pjtNo.get(i).toString();
%>

            innerRow = tBody.insertRow();

            innerCell = innerRow.insertCell();
            innerCell.className = "tdwhiteM";
            <% if(mode.equals("one")){ %>
            innerCell.innerHTML = "<input type='checkbox' id='iDrChk_<%=i%>' value='<%=s_drOid%>' name='iDrChk_<%=i%>' iDrOid=<%=s_drOid%> drNo='<%=s_drNo%>' drName='<%=s_devProductName%>' drState='<%=s_drState%>' pjtNo='<%=s_pjtNo%>' onClick='oneClick(this)'>"
            <% }else{ %>
            innerCell.innerHTML = "<input type='checkbox' id='iDrChk_<%=i%>' value='<%=s_drOid%>' name='iDrChk_<%=i%>' iDrOid=<%=s_drOid%> drNo='<%=s_drNo%>' drName='<%=s_devProductName%>' drState='<%=s_drState%>' pjtNo='<%=s_pjtNo%>' >"
            <% } %>

            innerCell = innerRow.insertCell();
            innerCell.className = "tdwhiteM";
            innerCell.innerText = <%if(s_developmentStep.equals("D")){%>"<%=messageService.getString("e3ps.message.ket_message", "00656") %><%--개발의뢰--%>"<%}else{%>"<%=messageService.getString("e3ps.message.ket_message", "00729") %><%--검토의뢰--%>"<%}%>;

            innerCell = innerRow.insertCell();
            innerCell.className = "tdwhiteM";
            innerCell.innerText = "<%=s_drNo%>";

            innerCell = innerRow.insertCell();
            innerCell.className = "tdwhiteM";
            innerCell.innerText = "<%=s_pjtNo%>";

            innerCell = innerRow.insertCell();
            innerCell.className = "tdwhiteL0";
            innerCell.title = "<%=s_devProductName%>";
            innerCell.style.cssText = "text-overflow:ellipsis;overflow:hidden;";
            innerCell.innerHTML = "<nobr><%=s_devProductName%></nobr>";
<%
        }
     }else{
%>
        var tBody = document.getElementById("iDrTable");
      var innerRow;
        var innerCell;

        innerRow = tBody.insertRow();
        innerCell = innerRow.insertCell();
        innerCell.className = "tdwhiteM0";
        innerCell.colSpan = 5;
        innerCell.innerText = '<%=messageService.getString("e3ps.message.ket_message", "02676") %><%--주요기능--%>'
<%
 }
%>





