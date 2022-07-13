<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.Vector"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

    String pjtNo = StringUtil.checkNull(request.getParameter("pjtNo"));
    String pjtName = StringUtil.checkNull(request.getParameter("pjtName"));
    String pjtState = StringUtil.checkNull(request.getParameter("pjtState"));
    String dieNo = StringUtil.checkNull(request.getParameter("dieNo"));
    String partNo = StringUtil.checkNull(request.getParameter("partNo"));
    String partName = StringUtil.checkNull(request.getParameter("partName"));
    String rank = StringUtil.checkNull(request.getParameter("rank"));

    String   initType = request.getParameter("initType");
    if(initType == null || initType.length() == 0){
        initType = "";
    }
    String radioValue = StringUtil.checkNull(request.getParameter("radio"));
    if(radioValue.equals("")) radioValue="1";
    Vector tMap = null;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></title>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript>
<!--

function dosubmit(){
    document.forms[0].action="/plm/jsp/project/trySchdule/TrySearchProject.jsp";
    document.forms[0].submit();
}


function addSelectCode() {
    //if(!checkedCheck()) {
    //  alert("코드를 선택하십시오.");
    //  return;
    //}


    form = document.forms[0];

    var arr = new Array();
    var subArr = new Array();
    var idx = 0;

    if(form.oid.checked == true) {
        subArr = new Array();
        subArr[0] = form.oid.value;
        subArr[1] = form.oid.codename;
        arr[idx++] = subArr;
    }

    selectModalDialog(arr);
}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

function checkedCheck() {
    form = document.forms[0];
    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            return true;
        }
    }

    return false;

}

//-->
</script>
</head>
<body>
<form>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="780" id="moldSearchDisplay" >
          <tr>
          <td width="100" class="tdblueL">Die No.</td>
          <td width="160" class="tdwhiteL"><input type="text" name="dieNo" class="txt_field"  style="width:150" id="textfield2"></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td width="160" class="tdwhiteL"><select name="select" class="fm_jmp" style="width:150">
            <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
          </select></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
          <td width="160" class="tdwhiteL0">
            <select name="select2" class="fm_jmp" style="width:150">
              <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02553") %><%--제품 프로젝트번호--%></td>
          <td width="160" class="tdwhiteL"><input type="text" name="pjtNo" class="txt_field"  style="width:150" id="textfield"></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01078") %><%--금형분류--%></td>
          <td width="160" class="tdwhiteL"><select name="select3" class="fm_jmp" style="width:150">
              <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
              <%
              tMap = NumberCodeHelper.manager.getNumberCodeLevel("MOLDPRODUCTSTYPE", "");
                for(int i = 0; i < tMap.size(); i++) {
                    NumberCode tNum = (NumberCode)tMap.get(i);
                    %>
                        <option value="<%=tNum.getCode()%>"
                        <%if(rank.equals(tNum.getName())){out.print("selected");} %>
                        ><%=tNum.getName()%></option>
                    <%
                    }
                %>
            </select></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
          <td width="160" class="tdwhiteL0"><select name="select4" class="fm_jmp" style="width:150">
              <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
              <%
              tMap = NumberCodeHelper.manager.getNumberCodeLevel("MOLDTYPE", "");
                for(int i = 0; i < tMap.size(); i++) {
                    NumberCode tNum = (NumberCode)tMap.get(i);
                    %>
                        <option value="<%=tNum.getCode()%>"
                        <%if(rank.equals(tNum.getName())){out.print("selected");} %>
                        ><%=tNum.getName()%></option>
                    <%
                    }
                %>
            </select></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="160" class="tdwhiteL"><input type="text" name="partNo" class="txt_field"  style="width:150" id="textfield6"></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="160" class="tdwhiteL"><input type="text" name="partName" class="txt_field"  style="width:150" id="textfield9"></td>
          <td width="100" rowspan="2" class="tdblueL">PM</td>
          <td width="160" rowspan="2" class="tdwhiteL0">
            <input type="text" name="textfield3" class="txt_field"  style="width:120" id="textfield3">
&nbsp;<a href="#"><img src="/plm/portal/images/icon_user.gif" border="0"></a></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01010") %><%--금형 Rank--%></td>
          <td width="160" class="tdwhiteL"><select name="rank" class="fm_jmp" style="width:150">
            <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
            <%
              tMap = NumberCodeHelper.manager.getNumberCodeLevel("RANK", "");
                for(int i = 0; i < tMap.size(); i++) {
                    NumberCode tNum = (NumberCode)tMap.get(i);
                    %>
                        <option value="<%=tNum.getCode()%>"
                        <%if(rank.equals(tNum.getName())){out.print("selected");} %>
                        ><%=tNum.getName()%></option>
                    <%
                    }
                %>
          </select></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01078") %><%--금형분류--%></td>
          <td class="tdwhiteL"><select name="select5" class="fm_jmp" style="width:150">
            <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
            <%
              tMap = NumberCodeHelper.manager.getNumberCodeLevel("MOLDPRODUCTSTYPE", "");
                for(int i = 0; i < tMap.size(); i++) {
                    NumberCode tNum = (NumberCode)tMap.get(i);
                    %>
                        <option value="<%=tNum.getCode()%>"
                        <%if(rank.equals(tNum.getName())){out.print("selected");} %>
                        ><%=tNum.getName()%></option>
                    <%
                    }
                %>
          </select></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
          <td colspan="3" class="tdwhiteL"><input type="text" name="textfield5" class="txt_field"  style="width:150" id="textfield11">
            &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0"></a>&nbsp;~&nbsp;
            <input type="text" name="textfield5" class="txt_field"  style="width:150" id="textfield12">
            &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0"></a></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
          <td width="160" class="tdwhiteL0"><select name="select2" class="fm_jmp" style="width:150">
            <option value="">[ <%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%> ]</option>
            <%
              tMap = NumberCodeHelper.manager.getNumberCodeLevel("DEVELOPENTTYPE", "");
                for(int i = 0; i < tMap.size(); i++) {
                    NumberCode tNum = (NumberCode)tMap.get(i);
                    %>
                        <option value="<%=tNum.getCode()%>"
                        <%if(rank.equals(tNum.getName())){out.print("selected");} %>
                        ><%=tNum.getName()%></option>
                    <%
                    }
                %>
          </select></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
          <td colspan="3" class="tdwhiteL"><input type="text" name="textfield5" class="txt_field"  style="width:150" id="textfield7">
            &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0"></a>&nbsp;~&nbsp;
            <input type="text" name="textfield5" class="txt_field"  style="width:150" id="textfield8">
            &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0"></a></td>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
          <td width="160" class="tdwhiteL0">
            <input type="text" name="textfield6" class="txt_field"  style="width:150" id="textfield4">
          </td>
        </tr>

      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>

      <jsp:include  page="/jsp/project/trySchdule/TryListProject.jsp" flush="false"/>
      </td>
      </tr>
      <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
      </table>
</form>
</body>
</html>
