<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                e3ps.dms.entity.KETDocumentCategory"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String categoryCode =  request.getParameter("oid");
    String parentCategoryCode = null;
    String docCatePath = null;
    String categoryName = null;
    int categoryLevel = 0;
    String docTypeCode = null;
    String sortNo = null;
    String docCateFolder = null;
    Boolean isRoot  = true;
    Boolean isUsed = true;
    Boolean isAPQP = true;
    Boolean isPSO10 = true;
    Boolean isESIR = true;
    Boolean isISIRCar = true;
    Boolean isISIRElec = true;
    Boolean isANPQP = true;
    Boolean isSYMC = true;
    Boolean isDRCheckSheet = true;
    Boolean isDesignSheet = true;
    Boolean isSecurity = true;
    
    Boolean isMatlDupCk = true;
    Boolean isPrdRvsRqr = true;
    
    String numberingcode_levle_1 = "";
    String numberingcode_levle_2 = "";

    if(categoryCode!=null)
    {
    //선택된 categoryCode의 정보를 찾아 화면에 나타내어 준다.
        KETDocumentCategory docCate = null;
        docCate = KETDmsHelper.service.selectDocCategory(categoryCode);

        categoryName = "" + docCate.getCategoryName();
        parentCategoryCode = "" + docCate.getParentCategoryCode();
        //Kogger.debug("categoryName ===>" + categoryName);
        categoryLevel = docCate.getCategoryLevel();
        docTypeCode = "" + docCate.getDocTypeCode();
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
        
        numberingcode_levle_1 = docCate.getAttribute2();
        numberingcode_levle_2 = docCate.getAttribute3();
        
        if("".equals(numberingcode_levle_1) || numberingcode_levle_1 == null){
            numberingcode_levle_1 = "";
        }
        if("".equals(numberingcode_levle_2) || numberingcode_levle_2 == null){
            numberingcode_levle_2 = "";
        }
        
        docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
    }
%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01615") %><%--분류체계수정--%></title>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
<script language="JavaScript">
<!--
function valcheck() {
    var d = document.forms[0];

    if( isNull(d.categoryName.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01613") %><%--분류체계명을 입력해주세요--%>');
        return false;
    }
    if(IsSpecialStr(d.categoryName.value) ) {
        return false;
    }
    var s = d.categoryName.value;
    if (s.length > 200){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01611") %><%--분류체계명은 200자를 초과할 수 없습니다--%>');
        return false;
    }

    if ( isNull(d.sortNo.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02509") %><%--정렬순서는 반드시 입력해야 합니다--%>');
        return false;
    }

    if ( isNotDigit(d.sortNo.value) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02508") %><%--정렬순서는 반드시 숫자여야 합니다--%>');
        return false;
    }
    return true;
}

function isNotDigit(str) {
    var pattern = /^[0-9]+$/;
    str = str.replace(".", "0");
    if(!pattern.test(str)){
        return true;
    }
    return false;
}

function isNull(str) {
    if(str==null||str==""){
        return true;
    }
    return false
}
//수정된 분류체계를 DocuCateServlet를 호출하여 저장한다.
function updateDocCategory() {
    if (!valcheck()) return;
    else {
        document.forms[0].action = '/plm/servlet/e3ps/DocuCateServlet';
        document.forms[0].submit();
    }
}

function cancel(oid)
{
    document.location.href="/plm/jsp/dms/ViewDocuCate.jsp?oid="+oid;
}

function IsSpecialStr( obname ) {
    //var specialChars='~`!@#$%%^&*-=+\|[{]};:\',<.>/?';
    var specialChars='/';
    var str=obname;
    var i, j;
    if (str == '') {

    }else{
        for (i = 0; i < str.length; i++) {
          for (j = 0; j < specialChars.length; j++) {
            if (str.charAt(i) == specialChars.charAt(j)){
              alert(str.charAt(i)+'<%=messageService.getString("e3ps.message.ket_message", "00021") %><%--{0}는 사용불가합니다--%>');
              return true;
              //str = str.replace(str.charAt(i), "");
            }
          }
        }
    }
    return false;
  }

//-->
</script>
</head>
<body style="margin:30px 15px" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form method="post">
<input type="hidden" name=lvl value="<%=categoryLevel%>">
<input type="hidden" name="cmd" value="update">
<input type="hidden" name="oid" value="<%=categoryCode%>">
<input type="hidden" name="parentCategoryCode" value="<%=parentCategoryCode%>">
<input type=hidden name=docTypeCode value="<%=categoryCode.substring(0,2)%>">

<div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "01615") %><%--분류체계수정--%>
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 100%">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:updateDocCategory();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:cancel('<%=categoryCode%>');" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--취소--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 100%" summary="">
          <colgroup>
            <col width="20%" />
            <col width="80%" />
          </colgroup>
          <tbody>
          
            <tr>
			<td class="title"><%=messageService.getString("e3ps.message.ket_message", "01610") %><%--분류체계명--%><span class="red">*</span></td>
			<td><input type=text size=40 name="categoryName" value="<%=categoryName%>" onfocus='blur' ></td>
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
				              <td><input type='text' name='categoryName_<%=item.get("code") %>' size=40 value="<%=numberCode.get("value")%>"></td>
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
		    <td>
		    <input type=text size=40 name="numberingcode_levle_1" value="<%=numberingcode_levle_1%>" onfocus='blur' >
		    </td>
		    </tr>
		    
            <tr>
			    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03461") %><%--채번특성--%></td>
			    <td>
			    <input type=text size=40 name="numberingcode_levle_2" value="<%=numberingcode_levle_1%>" onfocus='blur' style="width:40%" > <Font Color = 'red'> 채번특성1이 입력되어 있는 경우, 채번특성2가 입력되지 않으면 개발산출문서 생성시 [품번]이 문서번호로 사용됩니다</Font>
			    </td>
		    </tr>
		    
		    <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01758") %><%--상위경로--%></td>
                <td><%=docCatePath%></td>
            </tr>
            
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02507") %><%--정렬순서--%><% if((categoryCode.substring(2,3).equals("1"))||(categoryCode.substring(2,3).equals("2"))){%><span class="red">*</span><%} %></td>
                <td>
                <input type=text size=40 name="sortNo" value="<%=sortNo%>" >
                </td>
            </tr>
            
            <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%><span class="red">*</span></td>
                <td>
                    <input name="isUsed" type="radio" class="Checkbox" value="true" <%if(isUsed){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01669") %><%--사용--%>
                    <input name="isUsed" type="radio" class="Checkbox" value="false" <%if(!isUsed){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01450") %><%--미사용--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">APQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isAPQP" type="radio" class="Checkbox" value="true" <%if(isAPQP){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isAPQP" type="radio" class="Checkbox" value="false" <%if(!isAPQP){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">PSO10 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isPSO10" type="radio" class="Checkbox" value="true" <%if(isPSO10){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isPSO10" type="radio" class="Checkbox" value="false" <%if(!isPSO10){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">ESIR <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isESIR" type="radio" class="Checkbox" value="true" <%if(isESIR){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isESIR" type="radio" class="Checkbox" value="false" <%if(!isESIR){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">ISIR(Car) <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isISIRCar" type="radio" class="Checkbox" value="true" <%if(isISIRCar){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isISIRCar" type="radio" class="Checkbox" value="false" <%if(!isISIRCar){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">ISIR(Elec) <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isISIRElec" type="radio" class="Checkbox" value="true" <%if(isISIRElec){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isISIRElec" type="radio" class="Checkbox" value="false" <%if(!isISIRElec){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">ANPQP <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isANPQP" type="radio" class="Checkbox" value="true" <%if(isANPQP){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isANPQP" type="radio" class="Checkbox" value="false" <%if(!isANPQP){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">SYMC <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isSYMC" type="radio" class="Checkbox" value="true" <%if(isSYMC){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isSYMC" type="radio" class="Checkbox" value="false" <%if(!isSYMC){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">DRCheckSheet <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isDRCheckSheet" type="radio" class="Checkbox" value="true" <%if(isDRCheckSheet){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isDRCheckSheet" type="radio" class="Checkbox" value="false" <%if(!isDRCheckSheet){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">부서 권한 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isDesignSheet" type="radio" class="Checkbox" value="true" <%if(isDesignSheet){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isDesignSheet" type="radio" class="Checkbox" value="false" <%if(!isDesignSheet){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">대내비 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isSecurity" type="radio" class="Checkbox" value="true" <%if(isSecurity){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isSecurity" type="radio" class="Checkbox" value="false" <%if(!isSecurity){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                    <Font Color = 'red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개발산출문서의 경우 대내비로 설정되면 작성자/결재자에게만 열람권한이 부여됩니다.</Font>
                </td>
            </tr>
            
            <tr>
                <td class="title">자재중복체크 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isMatlDupCk" type="radio" class="Checkbox" value="true" <%if(isMatlDupCk){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isMatlDupCk" type="radio" class="Checkbox" value="false" <%if(!isMatlDupCk){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>
            
            <tr>
                <td class="title">정기개정필요 <%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%><span class="red">*</span></td>
                <td>
                    <input name="isPrdRvsRqr" type="radio" class="Checkbox" value="true" <%if(isPrdRvsRqr){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01227") %><%--대상--%>
                    <input name="isPrdRvsRqr" type="radio" class="Checkbox" value="false" <%if(!isPrdRvsRqr){ %>checked<%} %>><%=messageService.getString("e3ps.message.ket_message", "01637") %><%--비대상--%>
                </td>
            </tr>

          </tbody>
        </table>
      </div>
</div>
</form>
</body>
</html>
