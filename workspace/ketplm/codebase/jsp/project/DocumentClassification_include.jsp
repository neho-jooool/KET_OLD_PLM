<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.query.QuerySpec,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.query.SearchCondition,
                  java.util.ArrayList"%>
<%@page import = "e3ps.doc.DocCodeType,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.CharUtil,
                  e3ps.common.jdf.config.Config,
                  e3ps.common.jdf.config.ConfigImpl,
                  e3ps.common.query.SearchUtil,
                  e3ps.doc.*,
                  e3ps.doc.beans.*,
                  e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function changeCategory(obj){
        if(obj.name == 'docClassify0'){
            document.forms[0].docClassify1.selectedIndex = 0;
            document.forms[0].docClassify2.selectedIndex = 0;
            document.forms[0].docClassify3.selectedIndex = 0;
        }else if(obj.name == 'docClassify1'){
            document.forms[0].docClassify2.selectedIndex = 0;
            document.forms[0].docClassify3.selectedIndex = 0;
        }else if(obj.name == 'docClassify2'){
            document.forms[0].docClassify3.selectedIndex = 0;
        }
        document.forms[0].submit();
    }

    function changeCategoryUpdate(obj) {
        if(obj.name == 'docClassify0') {
            document.forms[0].docClassify0.options[document.forms[0].docClassify0.selectedIndex].value;
            document.forms[0].docClassify1.selectedIndex = 0;
            document.forms[0].docClassify2.selectedIndex = 0;
            document.forms[0].docClassify3.selectedIndex = 0;
        } else if(obj.name == 'docClassify1') {
            document.forms[0].docClassify1.options[document.forms[0].docClassify1.selectedIndex].value;
            document.forms[0].docClassify2.selectedIndex = 0;
            document.forms[0].docClassify3.selectedIndex = 0;
        } else if(obj.name == 'docClassify2') {
            document.forms[0].docClassify2.options[document.forms[0].docClassify2.selectedIndex].value;
            document.forms[0].docClassify3.selectedIndex = 0;
        } else if(obj.name == 'docClassify3') {
            document.forms[0].docClassify3.options[document.forms[0].docClassify3.selectedIndex].value;
        }
        document.forms[0].isCheck.value = "true";
        document.forms[0].submit();
    }

    function checkClassification(){
        var form = document.forms[0];
        if(form.docClassify0.selectedIndex > 0) {
            if(form.docClassify1.selectedIndex > 0) {
                if(form.docClassify2.selectedIndex > 0){
                    if(form.docClassify3.options.length > 1) {
                        if(form.docClassify3.selectedIndex > 0) {
                            return true;
                        }
                    } else { return true; }
                } else {
                    if(form.docClassify2.options.length > 1) {
                    } else { return true; }
                }
            } else {
                if(form.docClassify1.options.length > 1) {
                } else { return true; }
            }
        }
        return false;
    }
-->
</script>

<!-- Hidden Value -->
<input type="hidden" name="isCheck">
<!-- //Hidden Value -->
<%
    //TemplateProjectOutputCreate.jsp & TemplateProjectOutputUpdate.jsp
    String tLocation = null;
    String tMode = null;
    if(request.getParameter("isCheck") == null) {
        if(request.getParameter("location") != null) tLocation = request.getParameter("location");
        if(request.getParameter("mode") != null) tMode = request.getParameter("mode");
    } else {
        if(request.getParameter("mode") != null) tMode = request.getParameter("mode");
    }

    //Init Value Setting
    String docClassify_0 = null;
    String docClassify_1 = null;
    String docClassify_2 = null;
    String docClassify_3 = null;

    DocCodeType docCode_0 = null;  //1분류
    DocCodeType docCode_1 = null;  //2분류
    DocCodeType docCode_2 = null;  //3분류
    DocCodeType docCode_3 = null;  //4분류

    if(tMode.equalsIgnoreCase("create")) {
        docClassify_0 = request.getParameter("docClassify0");
        docClassify_1 = request.getParameter("docClassify1");
        docClassify_2 = request.getParameter("docClassify2");
        docClassify_3 = request.getParameter("docClassify3");

        docCode_0 = DocCodeTypeHelper.getDocTypeForECO(docClassify_0);
        if(StringUtil.checkString(docClassify_1)) docCode_1 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_1);
        if(StringUtil.checkString(docClassify_2)) docCode_2 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_2);
        if(StringUtil.checkString(docClassify_3)) docCode_3 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_3);

        QuerySpec query = new wt.query.QuerySpec();
        Class docType = DocCodeType.class;
        int idx_docType = query.addClassList(docType, true);
        DocCodeType Root = DocCodeTypeHelper.getRoot();
        query.appendWhere(new SearchCondition(docType, "parentReference.key.id", "=", CommonUtil.getOIDLongValue(Root)), idx_docType);
        SearchUtil.setOrderBy(query, docType, idx_docType, "sort", "sort", false);
        QueryResult result = PersistenceHelper.manager.find(query);
        Object[] obj = null;
%>
                    <!-- 문서 1분류 //-->
                    <select name="docClassify0" style="width:150" onChange="Javascript:changeCategory(this)">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        while(result.hasMoreElements()){
            obj = (Object[])result.nextElement();
            DocCodeType docCodeType = (DocCodeType)obj[0];
%>
                        <option value="<%=docCodeType.getName()%>" <%=(docCodeType.getName().equals(docClassify_0))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(docCodeType)%>"><%=docCodeType.getName()%></option>
<%
        }//while(result.hasMoreElements()){
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 2분류 //-->
                    <select name="docClassify1" style="width:150" onChange="Javascript:changeCategory(this)">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        QueryResult rs = PersistenceHelper.manager.navigate(docCode_0, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
        while(rs.hasMoreElements()){
            DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(childCode.getPath().equals(docClassify_1))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
        }
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 3분류 //-->
                     <select name="docClassify2" style="width:150" onChange="Javascript:changeCategory(this)">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        if(docCode_1 != null){
            rs = PersistenceHelper.manager.navigate(docCode_1, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
            while(rs.hasMoreElements()){
                DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(childCode.getPath().equals(docClassify_2))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
            }
        }
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 4분류 //-->
                     <select name="docClassify3" style="width:150" onChange="Javascript:changeCategory(this)">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        if(docCode_2 != null){
            rs = PersistenceHelper.manager.navigate(docCode_2, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
            while(rs.hasMoreElements()){
                DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(childCode.getPath().equals(docClassify_3))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
            }
        }
%>
<%
    } else if(tMode.equalsIgnoreCase("update")) {
        String isCheck = request.getParameter("isCheck");

        if(StringUtil.checkString(isCheck) && isCheck.equalsIgnoreCase("true")) {
            docClassify_0 = request.getParameter("docClassify0");
            docClassify_1 = request.getParameter("docClassify1");
            docClassify_2 = request.getParameter("docClassify2");
            docClassify_3 = request.getParameter("docClassify3");

            docCode_0 = DocCodeTypeHelper.getDocTypeForECO(docClassify_0);
            if(StringUtil.checkString(docClassify_1)) docCode_1 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_1);
            if(StringUtil.checkString(docClassify_2)) docCode_2 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_2);
            if(StringUtil.checkString(docClassify_3)) docCode_3 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_3);
        } else {
            DocumentUtil util = new DocumentUtil();
            String returnPath = ProjectOutputHelper.manager.getDocCodeTypePath(DocCodeTypeHelper.getDocCodeTypeToPath(tLocation), "");
            returnPath = returnPath.substring(5, returnPath.length());
            String[] array = util.tokenDocType(returnPath, "^");

            if(array.length == 5) {     //4분류체계 선택
                if(request.getParameter("docClassify0") != "") {
                    docClassify_0 = array[1];
                } else {
                    docClassify_0 = request.getParameter("docClassify0");
                }
                if(request.getParameter("docClassify1") != "") {
                    docClassify_1 = "/ROOT/"+array[1]+"/"+array[2];
                } else {
                    docClassify_1 = request.getParameter("docClassify1");
                }
                if(request.getParameter("docClassify2") != "") {
                    docClassify_2 = "/ROOT/"+array[1]+"/"+array[2]+"/"+array[3];
                } else {
                    docClassify_2 = request.getParameter("docClassify2");
                }
                if(request.getParameter("docClassify3") != "") {
                    docClassify_3 = "/ROOT/"+array[1]+"/"+array[2]+"/"+array[3]+"/"+array[4];
                } else {
                    docClassify_3 = request.getParameter("docClassify3");
                }
            } else if(array.length == 4) {  //3분류체계 선택
                if(request.getParameter("docClassify0") != "") {
                    docClassify_0 = array[1];
                } else {
                    docClassify_0 = request.getParameter("docClassify0");
                }
                if(request.getParameter("docClassify1") != "") {
                    docClassify_1 = "/ROOT/"+array[1]+"/"+array[2];
                } else {
                    docClassify_1 = request.getParameter("docClassify1");
                }
                if(request.getParameter("docClassify2") != "") {
                    docClassify_2 = "/ROOT/"+array[1]+"/"+array[2]+"/"+array[3];
                } else {
                    docClassify_2 = request.getParameter("docClassify2");
                }
            } else if(array.length == 3) {  //2분류체계 선택
                if(request.getParameter("docClassify0") != "") {
                    docClassify_0 = array[1];
                } else {
                    docClassify_0 = request.getParameter("docClassify0");
                }
                if(request.getParameter("docClassify1") != "") {
                    docClassify_1 = "/ROOT/"+array[1]+"/"+array[2];
                } else {
                    docClassify_1 = request.getParameter("docClassify1");
                }
            } else if(array.length < 3) {  //1분류체계 선택
                if(request.getParameter("docClassify0") != "") {
                    docClassify_0 = array[1];
                } else {
                    docClassify_0 = request.getParameter("docClassify0");
                }
            }

            docCode_0 = DocCodeTypeHelper.getDocTypeForECO(docClassify_0);
            if(StringUtil.checkString(docClassify_1)) docCode_1 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_1);
            if(StringUtil.checkString(docClassify_2)) docCode_2 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_2);
            if(StringUtil.checkString(docClassify_3)) docCode_3 = DocCodeTypeHelper.getDocCodeTypeToPath(docClassify_3);
        }

        QuerySpec query = new wt.query.QuerySpec();
        Class docType = DocCodeType.class;
        int idx_docType = query.addClassList(docType, true);
        DocCodeType Root = DocCodeTypeHelper.getRoot();
        query.appendWhere(new SearchCondition(docType, "parentReference.key.id", "=", CommonUtil.getOIDLongValue(Root)), idx_docType);
        SearchUtil.setOrderBy(query, docType, idx_docType, "sort", "sort", false);
        QueryResult result = PersistenceHelper.manager.find(query);
        Object[] obj = null;
%>
                    <!-- 문서 1분류 //-->
                    <select name="docClassify0" style="width:150" onChange="Javascript:changeCategoryUpdate(this)">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        while(result.hasMoreElements()){
            obj = (Object[])result.nextElement();
            DocCodeType docCodeType = (DocCodeType)obj[0];
%>
                        <option value="<%=docCodeType.getName()%>" <%=(docCode_0 != null && (docCodeType.getName()).equals(docCode_0.getName()))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(docCodeType)%>"><%=docCodeType.getName()%></option>
<%
        }//while(result.hasMoreElements()){
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 2분류 //-->
                    <select name="docClassify1" style="width:150" onChange="Javascript:changeCategoryUpdate(this)">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        QueryResult rs = PersistenceHelper.manager.navigate(docCode_0, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
        while(rs.hasMoreElements()){
            DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(docCode_1 != null && (childCode.getName()).equals(docCode_1.getName()))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
        }
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 3분류 //-->
                     <select name="docClassify2" style="width:150" onChange="Javascript:changeCategoryUpdate(this)">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        if(docCode_1 != null){
            rs = PersistenceHelper.manager.navigate(docCode_1, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
            while(rs.hasMoreElements()){
                DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(docCode_2 != null && (childCode.getName()).equals(docCode_2.getName()))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
            }
        }
%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 문서 4분류 //-->
                     <select name="docClassify3" style="width:150" onChange="Javascript:changeCategoryUpdate(this)">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
        if(docCode_2 != null){
            rs = PersistenceHelper.manager.navigate(docCode_2, DocCodeTypeParentChild.CHILD_ROLE, DocCodeTypeParentChild.class);
            while(rs.hasMoreElements()){
                DocCodeType childCode = (DocCodeType) rs.nextElement();
%>
                        <option value="<%=childCode.getPath()%>" <%=(docCode_3 != null && (childCode.getName()).equals(docCode_3.getName()))?"SELECTED":""%> size="<%=DocCodeTypeHelper.getAttrCount(childCode)%>"><%=childCode.getName()%></option>
<%
            }
        }
%>
<%
    }
%>
