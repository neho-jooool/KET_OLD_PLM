<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String Ename 	     = StringUtil.checkNull((String)session.getAttribute("Ename"));
    String position	     = StringUtil.checkNull((String)session.getAttribute("position"));
    String dept_no	     = StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String select_name = StringUtil.checkNull((String)request.getParameter("select_name"));
    String auth		         = StringUtil.checkNull((String)session.getAttribute("costAuth"));

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>한국단자 종합 개발 시스템 - 상세검색</title>
<style type="text/css">
<!--
.style1 {font-size: 12px; text-align:center;}
.style2 {font-size: 12px; text-align:left;font-weight: bold; color:#3D6A98;}
.style3 {font-size: 12px; text-align:right;}
.style4 {font-size: 12px; text-align:left; color:#4F4F4F;}
.style5 {font-size: 12px; text-align:center; background-color:#CCCCCC}
-->
</style>
<script language="javascript">

 function search_ok()
 {
    var select_name	= document.forms[0].select_name.value;
    var select_team		= document.forms[0].select_team.value;
    var pjt_name			= document.forms[0].pjt_name.value;
    var pjt_no				= document.forms[0].pjt_no.value;
    var f_name			= document.forms[0].f_name.value;
    var a_name			= document.forms[0].a_name.value;
    var customer_F		= document.forms[0].customer_F.value;
    var car_type			= document.forms[0].car_type.value;
    var search_state	= document.forms[0].search_state.value;

    //한글깨짐 현상 방지
    pjt_name 		= escape(encodeURIComponent(pjt_name));
    f_name 			= escape(encodeURIComponent(f_name));
    a_name 		= escape(encodeURIComponent(a_name));
    customer_F 	= escape(encodeURIComponent(customer_F));
    car_type 		= escape(encodeURIComponent(car_type));

    opener.parent.mainListMovePage(select_name,select_team,pjt_name,pjt_no,f_name,a_name,customer_F,car_type,search_state);
 }

  function search_all()
 {
      opener.parent.mainListMovePage('');
 }

</script>
</head>
<body topmargin="0">
<form action="/plm/jsp/cost/list/mainList.jsp" name="searchpage" method="post">
<input type="hidden" name="select_name" value="<%=select_name%>">
<input type="hidden" name="search_state" value="ok">
<table width="324" height="310" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="271" background="/plm/jsp/cost/acc_img/search_bg.jpg" bgcolor="#FFFFFF"><table width="279" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="67" valign="bottom" class="style2">&nbsp;</td>
        <td height="67" valign="bottom" class="style2">&nbsp;</td>
        </tr>
      <tr>
        <td width="119" bgcolor="#FDF9F2" class="style1">개발팀</td>
        <td width="160" bgcolor="#F7F7F7" class="style4">&nbsp;&nbsp;
          <select name="select_team" class="style4" <%if(dept_no.equals("R4121") || auth.equals("A") || dept_no.equals("R4321")){ out.println("");}else{out.println("readonly");}%>>
            <option value=""></option>
            <option value= "1" <%if(position.equals("팀장") && dept_no.equals("11732")){out.println("selected");}%>>커넥터설계1팀</option>
            <option value= "11" <%if(position.equals("팀장") && dept_no.equals("11728")){out.println("selected");}%>>커넥터개발팀</option>
            <option value= "12" <%if(position.equals("팀장") && dept_no.equals("11729")){out.println("selected");}%>>커넥터양산개선팀</option>
            <option value= "22" <%if(position.equals("팀장") && dept_no.equals("11724")){out.println("selected");}%>>전장모듈연구개발1팀</option>
            <option value= "23" <%if(position.equals("팀장") && dept_no.equals("11725")){out.println("selected");}%>>전장모듈연구개발2팀</option>
            <option value= "24" <%if(position.equals("팀장") && dept_no.equals("11726")){out.println("selected");}%>>전장모듈연구개발3팀</option>
            <option value= "3" <%if(position.equals("팀장") && dept_no.equals("11737")){out.println("selected");}%>>연구개발3팀</option>
            <option value= "4" <%if(position.equals("팀장") && dept_no.equals("11738")){out.println("selected");}%>>연구개발4팀</option>
            <option value= "4" <%if(position.equals("팀장") && dept_no.equals("11743")){out.println("selected");}%>>연구개발5팀</option>
            <option value= "6" <%if(position.equals("팀장") && dept_no.equals("11740")){out.println("selected");}%>>연구개발6팀</option>
            <option value= "21" <%if(position.equals("팀장") && dept_no.equals("11745")){out.println("selected");}%>>시작개발팀</option>
            <option value= "7" <%if(position.equals("팀장") && dept_no.equals("11734")){out.println("selected");}%>>전장부품개발팀</option>
          </select></td>
        </tr>
      <tr>
        <td bgcolor="#FDF9F2" class="style1">Project Name </td>
        <td bgcolor="#F7F7F7" class="style1"><input type="text" name="pjt_name" size="22" class="style4"></td>
        </tr>
      <tr>
        <td bgcolor="#FDF9F2" class="style1">Project No. </td>
        <td bgcolor="#F7F7F7" class="style1"><input type="text" name="pjt_no" size="22" class="style4"></td>
      </tr>
      <tr>
        <td bgcolor="#FDF9F2" class="style1">개발담당자</td>
        <td bgcolor="#F7F7F7" class="style1"><input name="f_name" type="text" size="22" class="style4" value="<%if(dept_no.equals("11200") || dept_no.equals("11731") || dept_no.equals("11651") || auth.equals("A") || dept_no.equals("11614") || dept_no.equals("11632") || dept_no.equals("11640") || dept_no.equals("11641") || position.equals("팀장")){out.println("");}else{out.println(Ename);}%>" <%if(dept_no.equals("11200") || dept_no.equals("11701") || dept_no.equals("11651") || auth.equals("A") || dept_no.equals("11614") || dept_no.equals("11612") || dept_no.equals("11616") || position.equals("팀장")){ out.println("");}else{out.println("readonly");}%>></td>
        </tr>
      <tr>
        <td bgcolor="#FDF9F2" class="style1">영업담당자</td>
        <td bgcolor="#F7F7F7" class="style1"><input name="a_name" type="text" size="22" class="style4"></td>
        </tr>

      <tr>
        <td bgcolor="#FDF9F2" class="style1">고객사</td>
        <td bgcolor="#F7F7F7" class="style1"><input name="customer_F" type="text" size="22" class="style4"></td>
        </tr>
      <tr>
        <td bgcolor="#FDF9F2" class="style1">차종</td>
        <td bgcolor="#F7F7F7" class="style1"><input type="text" name="car_type" size="22" class="style4"></td>
        </tr>
      <tr>
        <td height="31">&nbsp;</td>
        <td class="style3"><input type="button" name="btn_search" value="검 색" onClick="search_ok();">
            <input type="reset" name="all_search" value="초기화" onClick="search_all();" ></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="71" valign="top" bgcolor="#FFFFFF" ><table width="280" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="121" valign="top" class="style4"><p>※ 검색방법예제<br>
          ① 여러개의 제품을 검색할때<br>
          - Project Name 입력창에 입력 할 경우 <br>
          &nbsp;&nbsp;→ <span class="style2">090II,250 (',' 를 사용하여 입력)</span><br>
           - Project No. 입력창에 입력 할 경우 <br>
          &nbsp;&nbsp;→ <span class="style2">09A056,08A005 (',' 를 사용하여 입력)</span>
          <br><br>
            ② 한가지의 제품을 검색할때 <br>
             - Project Name 입력창에 입력 할 경우 <br>
          &nbsp;&nbsp;→ <span class="style2">090II</span><br>
           - Project No. 입력창에 입력 할 경우 <br>
          &nbsp;&nbsp;→ <span class="style2">09A056</span>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>