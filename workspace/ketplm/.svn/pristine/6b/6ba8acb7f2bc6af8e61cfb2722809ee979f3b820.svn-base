<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.control.CostMainListCtl"%>
<%@ page import="e3ps.cost.control.CostCommonCtl"%>

<%

    String Ename 		= StringUtil.checkNull((String)session.getAttribute("Ename"));
    String Dname		= StringUtil.checkNull((String)session.getAttribute("Dname"));
    String emp_no		= StringUtil.checkNull((String)session.getAttribute("emp_no"));
    String dept_no		= StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String K_pass		= StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String position		= StringUtil.checkNull((String)session.getAttribute("position"));
    String login_id		= StringUtil.checkNull((String)session.getAttribute("cost_id"));
    String auth		    = StringUtil.checkNull((String)session.getAttribute("costAuth"));
    String group_m		= StringUtil.checkNull((String)session.getAttribute("group_m"));

    String select_name 	= StringUtil.checkNull((String)request.getParameter("select_name"));
    String pjt_no		= StringUtil.checkNull((String)request.getParameter("pjt_no"));
    String pjt_name		= StringUtil.checkNull((String)request.getParameter("pjt_name"));
    String customer_F	= StringUtil.checkNull((String)request.getParameter("customer_F"));
    String car_type		= StringUtil.checkNull((String)request.getParameter("car_type"));
    String a_name		= StringUtil.checkNull((String)request.getParameter("a_name"));
    String f_name		= StringUtil.checkNull((String)request.getParameter("f_name"));
    String select_team	= StringUtil.checkNull((String)request.getParameter("select_team"));
    String search_state	= "ok";

    String step_no     = "";
    String step_ing    = "";
    String w_name      = "";
    String dev_step    = "";

    String pk_cr_group 		= "";
    String rev_no      		= "";
    String rev_pk      		= "";
    String group_case_count = "";
    String pk_crp      		= "";
    String table_row   		= "";
    String case_type_user 	= "";
    String division = "";
    String team = "";
    String approval = "";
    String R_PRE_DAY = "";
    String PASS_TYPE = "";
    String INPUT_GB = "";

    // dept_no로 team code로 변경 START
    team = StringUtil.ChangeDeptNoPC(dept_no);
    // dept_no로 team code로 변경 END
 
    CostMainListCtl MainCtl = new CostMainListCtl();
    ArrayList<Hashtable<String, String>> searchMainList = new ArrayList<Hashtable<String, String>>();
    Hashtable<String, String> searchMainHash = null;
    searchMainList = MainCtl.searchMainList(select_name,auth,dept_no,position,group_m,team,Ename, select_team, pjt_name, pjt_no, f_name, a_name, customer_F,car_type,search_state);

    int ListSize = 0;
    String data_view = "";
    if(searchMainList != null){
        ListSize = searchMainList.size();
        if(ListSize > 0){
            searchMainHash = (Hashtable)searchMainList.get(0);
            data_view      = StringUtil.checkNull((String)searchMainHash.get("data_view"));
        }
    }

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/common/processing.html"%>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/list/list.css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/common.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	    resizeContent();
	    $(window).resize(function() {
	        resizeContent();
	    });
	    // Enter 검색
	    $("form[name=searchForm]").keypress(function(e) {
	        if (e.which == 13) {
	            search();
	            return false;
	        }
	    });
	});
	
	function resizeContent() { // 리스트 div 높이 조정
		height = document.body.clientHeight - 320;
	    $('#content').height(height);
	    hideProcessing();
	}

	function search(){
		showProcessing();
	   $('[name=searchForm]').attr('action', '/plm/jsp/cost/list/mainList.jsp');
       $('[name=searchForm]').submit();
       
	}
	
	 function clear(){
		 $('[name=searchForm]')[0].reset();
	}
	 
	function create(){
		var url = '/plm/jsp/cost/costdb/cost_re_add.jsp';
		location.href = url;
	}

</script>
</head>
<body scroll="no">
<div style="width:1241;height:60;">
<form name="searchForm" method="post">
<table width="1241" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="right">
            <%if(!data_view.equals("no")){%>
            <input type="button" value="등 록" onClick="parent.movePage('newRequest');">
            <%}%>
            <input type="button" value="검 색" onClick="search();">
            <input type="button" value="초기화" onClick="clear();" >
        </td>
    </tr>
</table>
<table width="1241" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#799fac">
    <tr>
        <td width="180" class="style5">진행상태</td>
        <td class="style55" colspan="5">
            <input name="select_name" type="radio" class="Checkbox" value=""            <%if("".equals(select_name))out.println("checked"); %>/>전체보기
            <input name="select_name" type="radio" class="Checkbox" value="request"     <%if("request".equals(select_name))out.println("checked"); %>/>원가요청단계
            <input name="select_name" type="radio" class="Checkbox" value="work"        <%if("work".equals(select_name))out.println("checked"); %>/>원가산출단계
            <input name="select_name" type="radio" class="Checkbox" value="im_complete" <%if("im_complete".equals(select_name))out.println("checked"); %>/>원가산출결재중
            <input name="select_name" type="radio" class="Checkbox" value="complete"    <%if("complete".equals(select_name))out.println("checked"); %>/>원가산출완료
           <%if(("11908").equals(dept_no)){ %>
                | 
                <a href="/plm/jsp/cost/costreport/cost_report_add_temp.jsp" target="_blank">
                <span>보고서 작성</span>
            <%}else if(auth.equals("A")){ %>
                |
                <a href="/plm/jsp/cost/costreport/cost_report_1_edit_temp.jsp" target="_blank">
                <span>보고서 작성</span>
            <%} %>
        </td>
    </tr>
    <tr>
        <td width="180" class="style5">Project No</td>
        <td class="style55"><input style="width:98%;" type="text" name="pjt_no" value="<%=pjt_no %>" /></td>
        <td width="180" class="style5" >Project Name</td>
        <td class="style55" colspan="3"><input style="width:99%;" type="text" name="pjt_name" value="<%=pjt_name %>" /></td>
    </tr>
    <tr>
        <td width="180" class="style5" >개발담당부서</td>
        <td width="161" class="style55">
        <%if(dept_no.equals("R4121") || auth.equals("A") || dept_no.equals("R4321")){
            out.println("<input type='hidden' name = 'select_team' value=''/>");
        }else{
            CostCommonCtl commonCtl = new CostCommonCtl();
            out.println(commonCtl.deptName(dept_no));
            out.println("<input type='hidden' name = 'select_team' value='"+team+"'/>");
        } %>
        </td>
        <td width="180" class="style5" >개발담당자</td>
        <td width="161" class="style55">
        <%if(dept_no.equals("R4121") || auth.equals("A") || dept_no.equals("R4321")){%>
            <input type="text" name="f_name" class="txt_field" value="<%=f_name %>" style="width: 70%">
            <input type="hidden" name="hf_name"> 
            <a href="javascript:SearchUtil.selectCostOneUser('hf_name','f_name');">
            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('hf_name','f_name');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
        <%}else{out.println(Ename);out.println("<input type='hidden' name = 'f_name' value='"+f_name+"'/>");} %>
        </td>
        <td width="180" class="style5" >영업담당자</td>
        <td width="161" class="style55">
            <input type="text" name="a_name" class="txt_field" value="<%=a_name %>" style="width: 70%">
            <input type="hidden" name="ha_name"> 
            <a href="javascript:showProcessing();SearchUtil.selectCostOneUser('ha_name','a_name');">
            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('ha_name','a_name');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
        </td>
    </tr>
    <tr>
        <td width="180" class="style5">고객사</td>
        <td class="style55"><input style="width:98%;" type="text" name="customer_F" value="<%=customer_F %>" /></td>
        <td class="style5">차종</td>
        <td class="style55" colspan="3"><input style="width:158px;" type="text" name="car_type" value="<%=car_type %>" /></td>
    </tr>
</table>
</form>
</div>
<%if(!data_view.equals("no")){ %>
<div style="width:1241;height:60;">
<table width="1241" border="0" height="60" align="center" cellpadding="0" cellspacing="1" bgcolor="#799fac" style="table-layout:fixed">
    <tr>
        <td width="36" height="60"rowspan="2" class="style5">No</td>
        <td width="138" height="25" colspan="2" class="style5">Project No </td>
        <td width="23" height="60"rowspan="2" class="style5">Rev</td>
        <td width="320" height="60"rowspan="2" class="style5">품명</td>
        <td width="89" height="60"rowspan="2" class="style5">품번</td>
        <td width="53" height="60"rowspan="2" class="style5">품목수</td>
        <td width="121" height="60" rowspan="2" class="style5">고객사</td>
        <td width="108" height="60"rowspan="2" class="style5">대상차종</td>
        <td width="153" height="25" colspan="3" class="style5">진행일정</td>
        <td width="81" height="60"rowspan="2" class="style5">진행<br>현황</td>
        <td width="103" height="60"rowspan="2" class="style5">&nbsp;</td>
    </tr>
    <tr>
        <td width="40" height="35" class="style5">영업</td>
        <td width="40" height="35"class="style5">개발</td>
        <td width="42" height="35"valign="bottom" class="style4">원가<br>의뢰</td>
        <td width="42" height="35"valign="bottom" class="style4">원가<br>산출</td>
        <td width="42" height="35"valign="bottom" class="style4">최종<br>배포</td>
    </tr>
</table>
</div>
<div id="content" style="width:1241;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
<script>showProcessing();</script>
<table width="1241" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#799fac" style="table-layout:fixed">
        <%for(int i=0; i<ListSize; i++){
            searchMainHash = (Hashtable)searchMainList.get(i);
            step_no  = StringUtil.checkNull((String)searchMainHash.get("STEP_NO"));
            w_name   = StringUtil.checkNull((String)searchMainHash.get("W_NAME"));
            dev_step = StringUtil.checkNull((String)searchMainHash.get("DEV_STEP"));

            pk_cr_group 		= StringUtil.checkNull((String)searchMainHash.get("PK_CR_GROUP"));
            rev_no 				= StringUtil.checkNull((String)searchMainHash.get("REV_NO"));
            rev_pk 				= StringUtil.checkNull((String)searchMainHash.get("REV_PK"));
            group_case_count 	= StringUtil.checkNull((String)searchMainHash.get("CASE_TYPE_USER"));
            pk_crp 				= StringUtil.checkNull((String)searchMainHash.get("PK_CRP"));
            table_row 			= StringUtil.checkNull((String)searchMainHash.get("TABLE_ROW"));
            case_type_user 	= StringUtil.checkNull((String)searchMainHash.get("CASE_TYPE_USER"));
            approval 				= StringUtil.checkNull((String)searchMainHash.get("APPROVAL"));
            R_PRE_DAY 				= StringUtil.checkNull((String)searchMainHash.get("R_PRE_DAY"));
            PASS_TYPE 				= StringUtil.checkNull((String)searchMainHash.get("PASS_TYPE"));
            INPUT_GB 				= StringUtil.checkNull((String)searchMainHash.get("INPUT_GB"));
            division 				= StringUtil.checkNull((String)searchMainHash.get("division"));

            if(step_no.equals("0")){
                step_ing = "요청서<br>작성중";
            }else if(step_no.equals("0.5")){
                step_ing = "요청서<br>결재중<br><font color=blue>(그룹장)</font>";
            }else if(step_no.equals("1")){
                step_ing = "요청서<br>결재중<br><font color=blue>(팀장)</font>";
            }else if(step_no.equals("2")){
                step_ing = "원가<br>산출<br>작업중<br><font color=blue>("+w_name+")</font>";
            }else if(step_no.equals("3")){
                step_ing = "보고서<br>결재중<br><font color=blue>(팀장)</font>";
            }else if(step_no.equals("4")){
                step_ing = "보고서<br>결재중<br><font color=blue>(센타장)</font>";
            }else if(step_no.equals("5")){
                step_ing = "1차 배포<br><font color=blue>결재중<br>(본부장)</font>";
            }else if(step_no.equals("5.1")){
                step_ing = "1차 배포<br><font color=blue>결재중<br>(원장)</font>";
            	//step_ing = "1차 배포<br><font color=blue>결재중<br>(소장)</font>";
            }else if(step_no.equals("5.2")){
                step_ing = "3차 배포<br><font color=blue>결재중<br>(사장)</font>";
            }else if(step_no.equals("6") && !dev_step.equals("개발검토") && R_PRE_DAY.equals("") && "".equals(PASS_TYPE)){
                step_ing = "3차 배포<br><font color=blue>결재중<br>(회장)</font>";
            }else if(step_no.equals("6")){
            	if("".equals(PASS_TYPE)){
                	step_ing = "완료<br><font color=blue>("+w_name+")</font>";
            	}else{
            		step_ing = "전결<br><font color=blue>("+w_name+")</font>";
            	}
            }else if(step_no.equals("7")){
                step_ing = "요청서<br>재검토<br>요망<br><font color=blue>("+w_name+")</font>";
            }else if(step_no.equals("8")){
                if(dev_step.equals("개발검토")){
                    step_ing = "개발<br>검토서<br>작성중";
                }else if(dev_step.equals("개발착수")){
                    step_ing = "개발<br>의뢰서<br>작성중";
                }
            }else if(step_no.equals("9")){
                if(dev_step.equals("개발검토")){
                    step_ing = "개발<br>검토서<br>결재중";
                }else if(dev_step.equals("개발착수")){
                    step_ing = "개발<br>의뢰서<br>결재중";
                }
            }else if(step_no.equals("E")){
                step_ing = "개발팀<br>지정중";
            }else if(step_no.equals("2E")){
                step_ing = "담당자<br>지정중";
            }else if(step_no.equals("3E")){
                step_ing = "재검토<br>요망";
            }else if(step_no.equals("4E")){
                step_ing = "기획팀<br>반려";
            }else if(step_no.equals("5E")){
                step_ing = "검토<br>취소";
            }


        %>
    <tr>
	    <td width="30" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=i%></td>
	    <td width="114" height="25" colspan="2" valign="bottom" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("PJT_NO"))%></td>
	    <td width="20" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("REV_NO"))%></td>
	    <td width="265" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("PJT_NAME"))%></td>
	    <td width="74" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("PART_NO"))%></td>
	    <td width="45" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("TABLE_ROW"))%>종</td>
	    <td width="100" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("CUSTOMER_F"))%></td>
	    <td width="90" rowspan="3" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("CAR_TYPE"))%></td>
	    <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("REQUEST_DAY_2"))%></td>
	    <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("REQUEST_DAY_1"))%></td>
	    <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("REQUEST_DAY"))%></td>
	    <td width="67" rowspan="3" bgcolor="#F7F7F7" class="style9_1"><%=step_ing%></td>
	    <td width="85" rowspan="3" valign="bottom" bgcolor="#F7F7F7" class="style1_1">
            <table width="85" border="0" align="center" cellpadding="0" cellspacing="1">
	            <tr>
	                <td width="85" height="16" valign="bottom" bgcolor="#F7F7F7" class="style1_1"><a href="/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group=<%= pk_cr_group%>&select_name=<%=select_name%>&rev_no=<%=rev_no%>&rev_pk=<%=rev_pk%>&data_type=main&group_case_count=<%=group_case_count%>&step_no=<%=step_no%>" target="_top">요청서</a></td>
	            </tr>
	            <tr>
	                <td width="85" height="1" valign="bottom" bgcolor="#bbbbbb" ></td>
	            </tr>
	            <tr>
	                <td width="85" height="16" valign="bottom" bgcolor="#F7F7F7" class="style1_1">
	                 <%if(dept_no.equals("11651") || dept_no.equals("11731") || dept_no.equals("11908")  || auth.equals("A")){ //설계원가팀,제품기획팀,관리자,전자기획팀%>
	
	                  <%if("1".equals(INPUT_GB)){ %>
	                  <%if("2".equals(division)){ %>
	                  	<a href="/plm/jsp/cost/costreport/cost_report_add_temp.jsp?report_pk=<%=pk_crp%>"  target="_top">수기 보고서</a>
	                  <%}else{ %>
	                  	<a href="/plm/jsp/cost/costreport/cost_report_1_edit_temp.jsp?report_pk=<%=pk_crp%>"  target="_top">수기 보고서</a>
	                  <%} %>
	                  <%}else{ %>
	                  	<a href="/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&data_type=main&select_group=T001&select_name=<%=select_name%>&rev_no=<%=rev_no%>&group_case_count=<%=group_case_count%>&report_pk=<%=pk_crp%>&approval=<%=approval %>"  target="_top">산출작업</a>
	                  <%} %>
	                 <%} %>
	                </td>
	            </tr>
	            <tr>
	                <td width="85" height="1" valign="bottom" bgcolor="#bbbbbb" ></td>
	            </tr>
	            <tr>
	                <td width="85" height="16" valign="bottom" bgcolor="#F7F7F7" class="style1_1">
	                 <%if(step_no.equals("3") || step_no.equals("4") ||  step_no.equals("5.1") ||  step_no.equals("5.2") || approval.equals("im_complete") || approval.equals("complete")){%>
	                 <a href="/plm/jsp/cost/costreport/cost_report_1.jsp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=pk_crp%>&cost_report_add=ok&select_name=<%=select_name%>&rev_no=<%=rev_no%>&rev_pk=<%=rev_pk%>&user_case_count=<%=case_type_user%>" target="_top">보고서</a>
	                 <%}%>
	                </td>
	            </tr>
            </table>
        </td>
    </tr>
    <tr>
      <td width="40" rowspan="2" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("A_NAME"))%></td>
      <td width="40" rowspan="2" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("F_NAME"))%></td>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%=StringUtil.checkNull((String)searchMainHash.get("LEADER_DAY"))%></td>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%if(step_no.equals("3") || step_no.equals("4") || step_no.equals("5") ){out.println(StringUtil.checkNull((String)searchMainHash.get("CO_AC_DAY")));}%></td>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1"><%if(step_no.equals("5")){out.println(StringUtil.checkNull((String)searchMainHash.get("FINISH_DAY")));}%></td>
    </tr>
    <tr>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1">&nbsp;</td>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1">&nbsp;</td>
      <td width="42" height="20" bgcolor="#F7F7F7" class="style1_1">&nbsp;</td>
    </tr>
    <%} %>
</table>
</div>
                        <%}else{
                            out.println("<script type=text/javascript>alert('"+"접속권한이 잘못되었습니다. 관리자에게 문의바랍니다.(1156)"+"');</script>");
                          }
                        %>
<br/>
<table width="1241" border="0" height="60" cellpadding="0" cellspacing="0" bgcolor="#799fac" style="table-layout:fixed">
    <tr>
        <td width="1241" colspan="3" bgcolor="#FFFFFF" class="line_name_2"></td>
    </tr>
    <tr>
        <td width="170" height="42" bgcolor="#FFFFFF" class="style3"><br>&nbsp;</td>
        <td width="901" bgcolor="#FFFFFF"><div align="center"><font color=666666 style="font-size:9pt;" >문의사항은 <strong>경영관리본부 경영정보팀(1155, 1156)</strong>으로 문의 바랍니다.<br>
                                                                                                        Copyright ⓒ 2001 Korea Electric Terminal Co., Ltd. All right reserved.&nbsp; </font></div></td>
        <td width="170" valign="bottom" bgcolor="#FFFFFF" class="line_name_1"></td>
    </tr>
</table>
</body>
</html>