<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String mode = request.getParameter("mode");

    String popup = request.getParameter("popup");

    String oid = "";
    String moid = "";
    String date = "";
    String tempOid = "";

    String poid = "";

    String moldOid = "";
    String moldRank = "";
    String productWeight = "";
    String scrapWeight = "";
    String pmOid = "";
    String specialSpec = "";

    String outSourcing = "";

    String machineOid = "";
    String remark = "";

    String shrinkage = "";

    if(mode.equals("save")){
        moid = request.getParameter("moid");
        date = request.getParameter("date");
        tempOid = request.getParameter("oid");
        poid = request.getParameter("poid");

//		Kogger.debug("mode = " + mode);
//		Kogger.debug("moid = " + moid);
//		Kogger.debug("date = " + date);
//		Kogger.debug("oid = " + tempOid);

        //if("save".equals(mode)){
        //	Kogger.debug("저장 실행");
            MoldProjectHelper.createMoldProject(moid, date, tempOid);
        //}else{
        //	Kogger.debug("error");
        //}
    }else if(mode.equals("modify")){
        moldOid = request.getParameter("oid");
        moldRank = request.getParameter("rank");
        productWeight = request.getParameter("productWeight");
        scrapWeight = request.getParameter("scrapWeight");
        pmOid = request.getParameter("temppmName");
        specialSpec = request.getParameter("specialSpec");
        machineOid = request.getParameter("model");
        remark = request.getParameter("remark");

        if(request.getParameter("outSourcing") != null && request.getParameter("outSourcing").length() > 0){
            outSourcing = request.getParameter("outSourcing");
        }


        if(request.getParameter("shrinkage") != null && request.getParameter("shrinkage").length() > 0){
            shrinkage = request.getParameter("shrinkage");
        }


//		Kogger.debug("moldOid = " + moldOid);
//		Kogger.debug("moldRank = " + moldRank);
//		Kogger.debug("productWeight = " + productWeight);
//		Kogger.debug("scrapWeight = " + scrapWeight);
//		Kogger.debug("pmOid = " + pmOid);
//		Kogger.debug("specialSpec = " + specialSpec);
//		Kogger.debug("outSourcing = " + outSourcing);
//		Kogger.debug("machineOid = " + machineOid);
//		Kogger.debug("remark = " + remark);
//		Kogger.debug("shrinkage = " + shrinkage);

        HashMap map = new HashMap();

        map.put("moldOid", moldOid);
        map.put("moldRank", moldRank);
        map.put("productWeight", productWeight);
        map.put("scrapWeight", scrapWeight);
        map.put("pmOid", pmOid);
        map.put("specialSpec", specialSpec);
        map.put("outSourcing", outSourcing);
        map.put("machineOid", machineOid);
        map.put("remark", remark);
        map.put("shrinkage", shrinkage);

        MoldProjectHelper.modifyMoldProject(map);
    }else if(mode.equals("delete")|| mode.equals("delete2")){

        oid = request.getParameter("oid");

        TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);

        QueryResult rs = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);
        TemplateProject original = null;
        if (rs.hasMoreElements()) {
            original = (TemplateProject) rs.nextElement();
        }

        MoldProjectHelper.deleteMoldProject(oid); // 삭제

        oid = CommonUtil.getOIDString(original);
        Kogger.debug("oid ==== " + oid);
    }else if(mode.equals("wbs")){
        moid = request.getParameter("moid");
        date = request.getParameter("date");
        tempOid = request.getParameter("oid");

        MoldProjectHelper.LinkWBS(moid, date, tempOid);
    }

%>

<%@page import="e3ps.project.beans.MoldProjectHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.CheckoutLink"%><html>
<head>
<title></title>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
</head>
<body>
<form>
<script>
<%
    if(mode.equals("save")){
%>
        alert("등록되었습니다.");

        location.href = "/plm/jsp/project/ProjectExtView2.jsp?oid=<%=poid%>&popup=popup";

        //opener.location.reload();

        //self.close();
<%
    }else if(mode.equals("modify")){
%>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01947") %><%--수정되었습니다.--%>");
        opener.location.reload();
        self.close();
        //location.href = "/plm/jsp/project/MoldProjectView_2.jsp?oid=<%=moldOid%>";
<%
    }else if(mode.equals("delete") || mode.equals("delete2")){
%>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01699") %><%--삭제되었습니다.--%>");
        <%if(popup == null || popup.length() == 0){%>
            <%if(mode.equals("delete")){%>
                location.href = "javascript:parent.parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>');"
            <%}else{%>
                location.href = "javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=devproject', '/plm/jsp/project/ProjectSearch.jsp')";
            <%}%>
        <%}else{%>
            // [START] [PLM System 1차개선] 금형 Project 삭제 후 처리, 2013-08-22, BoLee
            if ( parent.parent.opener != null ) {
                parent.parent.opener.document.location.href = parent.parent.opener.document.location.href;
            }
            // [END] [PLM System 1차개선] 금형 Project 삭제 후 처리, 2013-08-22, BoLee

            parent.self.close();
        <%}%>
<%
    }else if(mode.equals("wbs")){
%>
        opener.reloadTree();

        self.close();
<%}%>
</script>
</form>
</body>
</html>
