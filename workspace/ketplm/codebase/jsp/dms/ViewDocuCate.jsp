<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
        e3ps.dms.entity.KETDocumentCategory"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  String categoryCode =  request.getParameter("oid");
  String docCatePath = null;
  String categoryName = null;
  int categoryLevel = 0;
  String docTypeCode = null;
  String parentCategoryCode = null;
  String sortNo = null;
  String docCateFolder = null;
  Boolean isRoot = false;
  Boolean isUsed = true;
  Boolean isAPQP = true;
  Boolean isPSO10 = true;
  Boolean isESIR = true;
  Boolean isISIRCar = true;
  Boolean isISIRElec = true;
  Boolean isANPQP = true;
  Boolean isSYMC = true;
  Boolean isDRCheckSheet = true;
  Boolean isDesignSheet  = true;
  Boolean isSecurity = true;
  
  Boolean isMatlDupCk = true;
  Boolean isPrdRvsRqr = true;
  
  String numberingcode_levle_1 = "";
  String numberingcode_levle_2 = "";

  if(categoryCode!=null){
  //선택된 categoryCode의 정보를 찾아 화면에 나타내어 준다.
    if(categoryCode.equals("00000000")){
      //Kogger.debug("new categoryCode ===>" + categoryCode);
      docCatePath = "";
      categoryName = "";
      docTypeCode = "";
      parentCategoryCode = "";
      sortNo = "";
      docCateFolder = "";
      numberingcode_levle_1 = "";
      numberingcode_levle_2 = "";
    }else if(categoryCode.equals("ROOT")){
      categoryLevel = 0;
      docCatePath = "";
      categoryName = "ROOT";
      docTypeCode = "";
      parentCategoryCode = "";
      sortNo = "";
      docCateFolder = "";
      numberingcode_levle_1 = "";
      numberingcode_levle_2 = "";
    }else{

      KETDocumentCategory docCate = null;
      docCate = KETDmsHelper.service.selectDocCategory(categoryCode);
      if( docCate==null){
        categoryCode = "00000000";
        docCatePath = "";
        categoryName = "";
        docTypeCode = "";
        parentCategoryCode = "";
        sortNo = "";
        docCateFolder = "";
        numberingcode_levle_1 = "";
        numberingcode_levle_2 = "";
      }else{

        categoryLevel = docCate.getCategoryLevel();
        categoryName = "" + docCate.getCategoryName();
        docTypeCode = "" + docCate.getDocTypeCode();
        parentCategoryCode = "" + docCate.getParentCategoryCode();
        sortNo = "" + docCate.getSortNo();
        isUsed = docCate.getIsUsed();
        isAPQP = docCate.getIsAPQP();
        isPSO10 = docCate.getIsPSO10();
        isESIR = docCate.getIsESIR();
        isISIRCar = docCate.getIsISIRCar();
        isISIRElec = docCate.getIsISIRElec();
        isANPQP = docCate.getIsANPQP();
        isSYMC = docCate.getIsSYMC();
        isDRCheckSheet = docCate.getIsDRCheckSheet();
        if("Y".equals(docCate.getAttribute1())){
        	isDesignSheet = true;
        }else{
        	isDesignSheet = false;
        }
        
        if("Y".equals(docCate.getAttribute4())){
            isSecurity = true;
        }else{
            isSecurity = false;
        }
        
        if("Y".equals(docCate.getAttribute5())){
        	isMatlDupCk = true;
        }else{
        	isMatlDupCk = false;
        }
        
        if("Y".equals(docCate.getAttribute6())){
        	isPrdRvsRqr = true;
        }else{
        	isPrdRvsRqr = false;
        }
        
        docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
        numberingcode_levle_1 = docCate.getAttribute2();
        numberingcode_levle_2 = docCate.getAttribute3();
        if("".equals(numberingcode_levle_1) || numberingcode_levle_1 == null){
            numberingcode_levle_1 = "";
        }
        if("".equals(numberingcode_levle_2) || numberingcode_levle_2 == null){
            numberingcode_levle_2 = "";
        }
      }
    }
  }else{
    categoryCode = "00000000";
    docCatePath = "";
    categoryName = "";
    docTypeCode = "";
    parentCategoryCode = "";
    sortNo = "";
    docCateFolder = "";
  }
  
  isRoot = categoryCode.equals("00000000")||categoryCode.equals("ROOT");
%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01616") %><%--분류체계조회--%></title>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
<script language="JavaScript">
<!--

function createDocuCate(oid,path){
//입력버튼을 클릭하면 분류체계 입력화면으로 이동한다.
  document.location.href="/plm/jsp/dms/CreateDocuCate.jsp?oid="+oid+"&path="+path;
}

function updateDocuCate(oid)
{
//수정버튼을 클릭하면 분류체계 수정화면으로 이동한다.
  document.location.href="/plm/jsp/dms/UpdateDocuCate.jsp?oid="+oid;
}

function deleteDocuCate(oid)
{

  if(confirm("<%=messageService.getString("e3ps.message.ket_message", "03348") %><%--해당 분류체계를 삭제하시겠습니까?--%>")){

    document.forms[0].cmd.value="delete";
    document.forms[0].action="/plm/servlet/e3ps/DocuCateServlet";
    document.forms[0].submit();
    parent.tree.location.reload();
  }

}

-->
</script>
</head>
<body style="margin:30px 15px" bgcolor="white" text="black" link="blue" vlink="purple" alink="red">
<form method="post" >
<input type="hidden" name="cmd" >
<input type=hidden name=oid value="<%=categoryCode%>">
<input type=hidden name=poid value="00000000">
<input type=hidden name=lvl value="<%=categoryLevel%>">
<div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "01614") %><%--분류체계상세조회--%>
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 100%">
        <% if(!categoryCode.equals("00000000")){ %>
        <%if((categoryCode.equals("ROOT"))||(categoryCode.substring(2,3).equals("1"))||(categoryCode.substring(2,3).equals("2"))||((categoryCode.substring(2,3).equals("3"))&&(categoryCode.substring(0,2).equals("TD")))){ %>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:createDocuCate('<%=categoryCode%>','<%=docCatePath%>');" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "03144") %><%--하위분류등록--%></a></span><span class="pro-cell b-right"></span></span></span>
        <%} %>
        <%if(!categoryCode.equals("ROOT")){ %>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:updateDocuCate('<%=categoryCode%>')" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:deleteDocuCate('<%=categoryCode%>');" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></span><span class="pro-cell b-right"></span></span></span>
        <%} %>
        <% }%>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 100%" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
            <tr>
			<td class="title"><%=messageService.getString("e3ps.message.ket_message", "01610") %><%--분류체계명--%></td>
			<td><%=categoryName%></td>
			</tr>
			
            <%
                Map<String, Object> parameter = new HashMap<String, Object>();
                parameter.put("codeType", "LANGUAGE");
                List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
                Map<String, Object> numberCode = new HashMap<String, Object>();
                for ( Map<String, Object> item : list ) {
                    if ( !item.get("code").equals("ko") ) {
                	numberCode = NumberCodeUtil.getNumberCodeValueMap("DOCUMENTCATEGORY", categoryCode, item.get("code").toString());
            %>
                    <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01610") %><%--분류체계명--%>(<%=item.get("value") %>)</td>
                    <td><%=numberCode.get("value")%></td>
                    </tr>
                    <%
                    }
               }
            %>
			<tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01619") %><%--분류코드--%></td>
                <td><%=docTypeCode%></td>
            </tr>
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03461") %><%--채번특성--%>1</td>
                <td><%=numberingcode_levle_1%></td>
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03461") %><%--채번특성--%>2</td>
                <td><%=numberingcode_levle_2%></td>
            </tr>
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00795") %><%--경로--%></td>
                <td><%=docCatePath%></td>
            </tr>
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02507") %><%--정렬순서--%></td>
                <td><%=sortNo%></td>
            </tr>
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%></td>
                <td><%if(isRoot){%><%}else if(isUsed){ %><%=messageService.getString("e3ps.message.ket_message", "01669") %><%--사용--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01450") %><%--미사용--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">APQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isAPQP){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">PSO10 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isPSO10){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">ESIR <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isESIR){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            
            <tr>
                <td class="title">ISIR(Car) <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isISIRCar){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">ISIR(Elec) <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isISIRElec){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">ANPQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isANPQP){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">SYMC <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isSYMC){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">DRCheckSheet <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isDRCheckSheet){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">부서 권한 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isDesignSheet){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">대내비 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isSecurity){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">자재중복체크 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isMatlDupCk){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
            
            <tr>
                <td class="title">정기개정필요 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%></td>
                <td><%if(isRoot){%><%}else if(isPrdRvsRqr){ %><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%><%} %></td>
            </tr>
          </tbody>
        </table>
      </div>
</div>

</form>
</body>
</html>
