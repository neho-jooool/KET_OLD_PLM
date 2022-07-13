<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%

    String Ename 	= StringUtil.checkNull((String)session.getAttribute("Ename"));
    String Dname	    = StringUtil.checkNull((String)session.getAttribute("Dname"));
    String emp_no	= StringUtil.checkNull((String)session.getAttribute("emp_no"));
    String dept_no	= StringUtil.checkNull((String)session.getAttribute("dept_no"));
    String K_pass	= StringUtil.checkNull((String)session.getAttribute("K_pass"));
    String position	= StringUtil.checkNull((String)session.getAttribute("position"));
    String login_id	= StringUtil.checkNull((String)session.getAttribute("login_id"));//권한 관련 id
    String cost_id	= StringUtil.checkNull((String)session.getAttribute("cost_id"));//user id
    String group_m	= StringUtil.checkNull((String)session.getAttribute("group_m"));
    String etc_auth	= StringUtil.checkNull((String)session.getAttribute("etc_auth"));//개발팀,설계원가팀,선행해석팀용

    if(dept_no.equals("")){
        dept_no=" ";
    }
    String visitor             = StringUtil.checkNull(request.getParameter("visitor"));
    String step_no          = StringUtil.checkNull(request.getParameter("step_no"));
    String pk_cr_group    = StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String rev_no		       = StringUtil.checkNull(request.getParameter("rev_no"));
    String data_type 	    = StringUtil.checkNull(request.getParameter("data_type"));
    String group_case_count	= StringUtil.checkNull(request.getParameter("group_case_count"));
    String user_case_count  = "";
    String img_txt    = "";
    String img_txt_1  = "";
    String img_txt_2  = "";
    String img_case1  = "";
    String img_case2  = "";
    String f_name      	= "";
    String a_name       = "";
    String f_day		= "";
    String Leader_day	= "";
    String Leader_name	= "";
    String gr_day	= "";
    String gr_name	= "";
    String approval		= "";

    //여기서부터 어디서 request됐는 지 알수없음 최주임께 물어봐야함.
    String select_name = StringUtil.checkNull(request.getParameter("select_name"));
    String file_1_name = StringUtil.checkNull(request.getParameter("file_1_name"));
    String file_2_name = StringUtil.checkNull(request.getParameter("file_2_name"));
    String file_3_name = StringUtil.checkNull(request.getParameter("file_3_name"));
    String etc_note = request.getParameter("etc_note");
    //여기까지.



    if ((visitor.equals("1") || visitor.equals("2") || visitor.equals("3") || visitor.equals ("4") ) && !Ename.equals("")){
        login_id = "RND";

        /*session.setAttribute("id", login_id);
        session.setAttribute("menu_1" , "O");
        session.setAttribute("menu_2" , "O");
        session.setAttribute("menu_3" , "O");
        session.setAttribute("menu_4" , "X");
        session.setAttribute("menu_5" , "O");
        session.setAttribute("menu_6" , "X");
        session.setAttribute("menu_7" , "O");
        session.setAttribute("menu_8" , "O");
        session.setAttribute("menu_9" , "O");
        session.setAttribute("menu_10" , "O");
        session.setAttribute("menu_11" , "O");
        session.setAttribute("menu_12" , "X");
        session.setAttribute("menu_13" , "X");
        session.setAttribute("id_name", "연구소 권한");*/
    }else if(visitor.equals("5") && !Ename.equals("")){
        login_id = "pro_man";
        session.setAttribute("login_id", login_id);
    }/*else if((visitor.equals("1") || visitor.equals("2") || visitor.equals("3") || visitor.equals("4") || visitor.equals("5")) && Ename.length() == 0 ){
        user_case_count	= group_case_count;
        response.sendRedirect("/plm/jsp/cost/cost_acc/user_login.jsp?page_url=cost_re_view_test&visitor="+visitor+"&rev_no="+rev_no+"&pk_cr_group="+pk_cr_group+"&user_case_count="+user_case_count+"&step_no="+step_no);
    }*/

    if(!group_case_count.equals("")){
        group_case_count = group_case_count;
    }else{
        group_case_count = "0";
    }

    if(data_type.equals("case0")){
        img_txt = "small";
        img_txt_1 = "big";
        img_txt_2 = "small";
    }else if(data_type.equals("case1")){
        img_txt = "small";
        img_txt_1 = "small";
        img_txt_2 = "big";
    }else{
        img_txt = "big";
        img_txt_1 = "small";
        img_txt_2 = "small";
    }

    if(group_case_count.equals("1")){
        img_case1 = "visible";
        img_case2 = "hidden";
    }else if(group_case_count.equals("2")){
        img_case1 = "visible";
        img_case2 = "visible";
    }else{
        img_case1 = "hidden";
        img_case2 = "hidden";
    }


    Connection conn = null;
    conn = DBUtil.getConnection();
    CostComDao dao = new CostComDao(conn);
    ArrayList SearchCostView = null;
    try{
    	SearchCostView = dao.getSearchRe_view(pk_cr_group,rev_no);
    }catch(Exception e){

    }finally{
    	DBUtil.close(conn);
    }
    Hashtable SearchCostViewMap = null;
    int  cnt = 0;
    if(SearchCostView!= null){
        for(int i=0; i<SearchCostView.size(); i++){
            cnt++;
            SearchCostViewMap = (Hashtable)SearchCostView.get(i);

            f_name = StringUtil.checkNull((String)SearchCostViewMap.get("f_name"));
            a_name = StringUtil.checkNull((String)SearchCostViewMap.get("a_name"));
            f_day  = StringUtil.checkNull((String)SearchCostViewMap.get("f_day"));
            Leader_day  = StringUtil.checkNull((String)SearchCostViewMap.get("leader_day"));
            Leader_name = StringUtil.checkNull((String)SearchCostViewMap.get("leader_name"));
            approval    = StringUtil.checkNull((String)SearchCostViewMap.get("approval"));
            gr_day    = StringUtil.checkNull((String)SearchCostViewMap.get("gr_day"));
            gr_name    = StringUtil.checkNull((String)SearchCostViewMap.get("gr_name"));

        }
    }
    //approval = "ok";
    //position = "팀장";
    //login_id = "admin";
    if(group_m.equals("G")){
        position = "그룹장";
    }

%>
<%if(cnt < 1){ %>
<script language="javascript">
    alert("Data에 오류가 있습니다.\n관리자에게 문의바랍니다.(1303)");
    history.back();
</script>
<%} %>

<html>
<head>
<title>원가요청서</title>
<link rel='stylesheet' href="/plm/jsp/cost/css/wisegrid.css" type='text/css'>
<script type="text/javascript" src="/plm/jsp/cost/js/common.js"></script>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGridUni_TAG.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>

<!--
    WiseGrid 오브젝트가 생성되고 초기화된 후 발생하는
    JavaScript Event인 Initialize()를 받아 그리드의 헤더를 셋팅한다.
-->
<script language=javascript for="WiseGrid1" event="Initialize()">
    init1();
</script>

<script language=javascript for="WiseGrid2" event="Initialize()">
    init2();
</script>

<script language=javascript for="WiseGrid3" event="Initialize()">
    init3();
</script>
<script language=javascript for="WiseGrid5" event="Initialize()">
    init5();
</script>

<!--  	서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WiseGrid1" event="EndQuery()">
    GridEndQuery1();
</script>
<script language=javascript for="WiseGrid3" event="EndQuery()">
    GridEndQuery3();
</script>

<script language="JavaScript">
 var GridObj1;
 var GridObj2;
 var GridObj3;
 var GridObj5;

function init1() {
    GridObj1 = document.WiseGrid1;
    setProperty(GridObj1);
    GridObj1.bHDMoving = false;
    GridObj1.bHDSwapping = false;
    GridObj1.bStatusbarVisible = false;
    //GridObj1.strRowSizing = "autofree";
    GridObj1.nHDLines = 2;
    setHeader1();
    doQuery1();
}

function init2() {
    GridObj2 = document.WiseGrid2;
    setProperty(GridObj2);
    GridObj2.bHDMoving = false;
    GridObj2.bHDSwapping = false;
    GridObj2.bStatusbarVisible = false;
    setHeader2();
    doQuery2();
}

function init3() {
    GridObj3 = document.WiseGrid3;
    setProperty(GridObj3);
    GridObj3.bHDMoving = false;
    GridObj3.bHDSwapping = false;
    GridObj3.bStatusbarVisible = false;
    GridObj3.strRowSizing = "autofree";
    setHeader3();
    doQuery3();
}

function init5() {
    GridObj5 = document.WiseGrid5;
    setProperty(GridObj5);
    GridObj5.bHDMoving = false;
    GridObj5.bHDSwapping = false;
    GridObj5.bStatusbarVisible = false;
    GridObj5.nHDLines = 1;
    setHeader5();
}

/* 일반팝업을 중앙에 위치도록 할때  */
function popUpOpen(url, title, width, height)
{
    if (title == '') title = 'Popup_Open';
        if (width == '') width = 540;
        if (height == '') height = 500;
        var left = "";
        var top = "";

    //화면 가운데로 배치
            var dim = new Array(2);

    dim = CenterWindow(height,width);
    top = dim[0];
    left = dim[1];

    var toolbar = 'no';
    var menubar = 'no';
    var status = 'no';
    var scrollbars = 'no';
    var resizable = 'no';
    var code_search = window.open(url, title, 'left='+left+', top='+top+',width='+width+',height='+height+', toolbar='+toolbar+', menubar='+menubar+', status='+status+', scrollbars='+scrollbars+', resizable='+resizable);
    code_search.focus();
    return code_search;
}
function CenterWindow(height,width)
{
    var outx = screen.height;
    var outy = screen.width;
    var x = (outx - height)/2;
    var y = (outy - width)/2;
    dim = new Array(2);
    dim[0] = x;
    dim[1] = y;

    return  dim;
}

function setHeader1() {
    //그리드에 컬럼을 등록한다.
    GridObj1.AddHeader("pk_cr_group",	"pk_cr_group",		"t_text",			  50,			 10,		false);
    GridObj1.AddHeader("pjt_no",			"Project No.",		"t_text",			  50,			 90,		false);
    GridObj1.AddHeader("pjt_name",		"Project Name",	"t_text",			 500,			260,	  	false);
    GridObj1.AddHeader("team",			"개발팀",			"t_combo",	 	 100,			 80,		false);
    GridObj1.AddHeader("f_name",		"개발담당자",		"t_text",			  10,		 	 80,		false);
    GridObj1.AddHeader("a_name",		"영업담당자",		"t_text",			  10,			 80,		false);
    GridObj1.AddHeader("dev_step",		"개발단계",		"t_combo",	 	 100,			100,		false);
    GridObj1.AddHeader("dr_step",		"DR",			"t_text",	 	 100,			 41,		false);
    GridObj1.AddHeader("sub_step",		"차수",			"t_combo",	 	 100,			 41,		false);
    GridObj1.AddHeader("rev_no",        "Rev",          "t_text",        100,        41,        false);
    GridObj1.AddHeader("Leader_day",	"작성일",			"t_text",			 100,			 80,		false);
    GridObj1.AddHeader("request_day",	"요청일",			"t_date",			 100,			 80,		false);
    GridObj1.AddHeader("request_txt",		"요청목적",		"t_text",			2000,		290,		false);
    GridObj1.AddHeader("etc_note",		"비고",			"t_text",			5000,		290,		false);
    GridObj1.AddHeader("file_1",			"file_1",			"t_text",			2000,		290,		false);
    GridObj1.AddHeader("file_2",			"file_2",			"t_text",			2000,		290,		false);
    GridObj1.AddHeader("file_3",			"file_3",			"t_text",			2000,		290,		false);

      //마우스오른쪽비활성-내용부분
      //GridObj1.bUseDefaultContextMenu=false;
    //GridObj1.bUserContextMenu=true;

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj1.BoundHeader();

    // 콤보 리스트 - 팀
    GridObj1.AddComboListValue("team", "커넥터설계팀", "1");
    GridObj1.AddComboListValue("team", "커넥터개발팀", "11");
    GridObj1.AddComboListValue("team", "커넥터양산개선팀", "12");
    GridObj1.AddComboListValue("team", "전장모듈연구개발1팀", "22");
    GridObj1.AddComboListValue("team", "전장모듈연구개발2팀", "23");
    GridObj1.AddComboListValue("team", "전장모듈연구개발3팀", "24");
    GridObj1.AddComboListValue("team", "연구개발3팀", "3");
    GridObj1.AddComboListValue("team", "연구개발4팀", "4");
    GridObj1.AddComboListValue("team", "연구개발5팀", "5");
    GridObj1.AddComboListValue("team", "연구개발6팀", "6");
    GridObj1.AddComboListValue("team", "시작개발팀", "21");
    GridObj1.AddComboListValue("team", "전장부품개발팀", "7");
    // 콤보 리스트 - 개발단계

    GridObj1.AddComboListValue("dev_step", "개발검토", "개발검토");
    GridObj1.AddComboListValue("dev_step", "개발착수/진행중", "개발착수");
    GridObj1.AddComboListValue("dev_step", "개발완료", "개발완료");
    GridObj1.AddComboListValue("dev_step", "설계변경", "설계변경");
    GridObj1.AddComboListValue("dev_step", "기타", "기타");

    // 콤보 리스트 - DR
    /*
    GridObj1.AddComboListValue("dr_step", "", "empty");
    GridObj1.AddComboListValue("dr_step", "DR0", "DR0");
    GridObj1.AddComboListValue("dr_step", "DR1", "DR1");
    GridObj1.AddComboListValue("dr_step", "DR2", "DR2");
    GridObj1.AddComboListValue("dr_step", "DR3", "DR3");
    GridObj1.AddComboListValue("dr_step", "DR4", "DR4");
    GridObj1.AddComboListValue("dr_step", "DR5", "DR5");
    GridObj1.AddComboListValue("dr_step", "DR6", "DR6");
*/
    // 콤보 리스트 - 차수
    GridObj1.AddComboListValue("sub_step", "", "empty");
    GridObj1.AddComboListValue("sub_step", "1차", "1");
    GridObj1.AddComboListValue("sub_step", "2차", "2");
    GridObj1.AddComboListValue("sub_step", "3차", "3");
    GridObj1.AddComboListValue("sub_step", "4차", "4");
    GridObj1.AddComboListValue("sub_step", "5차", "5");
    GridObj1.AddComboListValue("sub_step", "6차", "6");
    GridObj1.AddComboListValue("sub_step", "7차", "7");
    GridObj1.AddComboListValue("sub_step", "8차", "8");
    GridObj1.AddComboListValue("sub_step", "9차", "9");
    GridObj1.AddComboListValue("sub_step", "10차","10");

      GridObj1.SetDateFormat("request_day", "yyyy-MM-dd");

      //Header 정렬
      GridObj1.SetColCellAlign('pjt_name',		'center');
          GridObj1.SetColCellAlign('pjt_no',			'center');
        GridObj1.SetColCellAlign('pjt_name',		'left');
        GridObj1.SetColCellAlign('team',			'center');
        GridObj1.SetColCellAlign('f_name',			'center');
        GridObj1.SetColCellAlign('a_name',		'center');
        GridObj1.SetColCellAlign('dev_step',		'center');
        GridObj1.SetColCellAlign('dr_step',		'center');
        GridObj1.SetColCellAlign('sub_step',		'center');
        GridObj1.SetColCellAlign('rev_no',        'center');
        GridObj1.SetColCellAlign('Leader_day',		'center');
        GridObj1.SetColCellAlign('request_day',	'center');
        GridObj1.SetColCellAlign('request_txt',		'left');
        GridObj1.SetColCellAlign('etc_note',		'center');
        GridObj1.SetColCellAlign('file_1',			'center');
        GridObj1.SetColCellAlign('file_2',			'center');
        GridObj1.SetColCellAlign('file_3',			'center');

    //GridObj1.SetColCellMultiLine("request_txt", true);

    //컬럼 숨기기
    GridObj1.SetColHide("pk_cr_group", true);
    GridObj1.SetColHide("etc_note", true);
    GridObj1.SetColHide("file_1", true);
    GridObj1.SetColHide("file_2", true);
    GridObj1.SetColHide("file_3", true);
}
function setHeader2() {
    //그리드에 컬럼을 등록한다.

    GridObj2.AddHeader("SEQ_NO",			"SEQ_NO", 			"t_text", 			  8,			 85,		false);
    GridObj2.AddHeader("pk_cr",				"pk_cr",				"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("pk_cr_group",		"pk_cr_group",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("group_no",			"그룹",				"t_text",			 20,			 66,		false);
    GridObj2.AddHeader("part_name",			"품명",				"t_text",			100,			277,	  	false);
    GridObj2.AddHeader("part_no",			"품번",				"t_text",			100,			 80,		false);
    GridObj2.AddHeader("car_type",			"적용차종",			"t_text",			100,			 65,		false);
    GridObj2.AddHeader("su_year_1",			"1년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_2",			"2년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_3",			"3년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_4",			"4년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_5",			"5년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_6",			"6년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_7",			"7년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("su_year_8",			"8년차",				"t_number",		10.3,			 50,		false);
    GridObj2.AddHeader("customer_F",		"1차",				"t_text",			100,			 60,		false);
    GridObj2.AddHeader("customer_L",		"최종",				"t_text",			100,			 64,		false);
    GridObj2.AddHeader("client_cost",			"고객사\n인정예상가",	"t_text",			100,			 80,		false);
    GridObj2.AddHeader("ket_cost",			"판매\n목표가",		"t_text",			100,			 70,		false);
    GridObj2.AddHeader("target_cost",			"목표\n수익률(%)",	"t_text",			100,			 60,		false);
    GridObj2.AddHeader("rev_pk",			"rev_pk",				"t_text",			 500,			  50,		false);
    GridObj2.AddHeader("rev_no",			"rev_no",				"t_text",			 500,			  50,		false);

      //그룹생성
    GridObj2.AddGroup("PLAN_INFO", "기획수량(천개/年)");
    GridObj2.AddGroup("CUSTOMER_INFO", "고객사");
    GridObj2.AddGroup("AIM_INFO", "목표가");

        //그룹에 헤더 추가
    GridObj2.AppendHeader("PLAN_INFO", "su_year_1");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_2");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_3");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_4");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_5");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_6");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_7");
    GridObj2.AppendHeader("PLAN_INFO", "su_year_8");

        //그룹에 헤더 추가
    GridObj2.AppendHeader("CUSTOMER_INFO", "customer_F");
    GridObj2.AppendHeader("CUSTOMER_INFO", "customer_L");

    GridObj2.AppendHeader("AIM_INFO", "client_cost");
    GridObj2.AppendHeader("AIM_INFO", "ket_cost");
    GridObj2.AppendHeader("AIM_INFO", "target_cost");

     //마우스오른쪽비활성-내용부분
      //GridObj2.bUseDefaultContextMenu=false;
    //GridObj2.bUserContextMenu=true;

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj2.BoundHeader();

    // 콤보 리스트 목표수익률
    /*
    GridObj2.AddComboListValue("target_cost", "", "empty");
    GridObj2.AddComboListValue("target_cost", "5%", "5");
    GridObj2.AddComboListValue("target_cost", "10%", "10");
    GridObj2.AddComboListValue("target_cost", "15%", "15");
    GridObj2.AddComboListValue("target_cost", "20%", "20");
    GridObj2.AddComboListValue("target_cost", "25%", "25");
    GridObj2.AddComboListValue("target_cost", "30%", "30");
    GridObj2.AddComboListValue("target_cost", "35%", "35");
    GridObj2.AddComboListValue("target_cost", "40%", "40");
    */

    //Header 정렬
         //GridObj2.SetColCellAlign('pjt_no','center');
        //GridObj2.SetColCellAlign('pjt_name','center');

    GridObj2.SetColCellAlign('SEQ_NO',		'center');
    GridObj2.SetColCellAlign('pk_cr',			'center');
    GridObj2.SetColCellAlign('pk_cr_group',     	'center');
    GridObj2.SetColCellAlign('group_no',		'left');
    GridObj2.SetColCellAlign('part_name',		'left');
    GridObj2.SetColCellAlign('part_no',	    	'center');
    GridObj2.SetColCellAlign('car_type',		'center');
    GridObj2.SetColCellAlign('customer_F',	    	'center');
    GridObj2.SetColCellAlign('customer_L',	    	'center');
    GridObj2.SetColCellAlign('client_cost',  	'right');
    GridObj2.SetColCellAlign('ket_cost',	       'right');
    GridObj2.SetColCellAlign('target_cost',         'right');
    GridObj2.SetColCellAlign('rev_pk',			'center');
        GridObj2.SetColCellAlign('rev_no',			'center');

         //t_number 타입의 컬럼을 포맷타입으로  지정한다.
    GridObj2.SetNumberFormat("su_year_1", "0,###.###");
    GridObj2.SetNumberFormat("su_year_2", "0,###.###");
    GridObj2.SetNumberFormat("su_year_3", "0,###.###");
    GridObj2.SetNumberFormat("su_year_4", "0,###.###");
    GridObj2.SetNumberFormat("su_year_5", "0,###.###");
    GridObj2.SetNumberFormat("su_year_6", "0,###.###");
    GridObj2.SetNumberFormat("su_year_7", "0,###.###");
    GridObj2.SetNumberFormat("su_year_8", "0,###.###");

    //컬럼 숨기기
    GridObj2.SetColHide("SEQ_NO", true);
    GridObj2.SetColHide("pk_cr", true);
    GridObj2.SetColHide("pk_cr_group", true);
    GridObj2.SetColHide("rev_pk", true);
    GridObj2.SetColHide("rev_no", true);

    //화면분할박스비활성
      GridObj2.bSplitBoxVisible = false;
}
function setHeader3() {
    //그리드에 컬럼을 등록한다.

    GridObj3.AddHeader("SELECTED",		"",							"t_checkbox", 		  2, 		 30,	false);
    GridObj3.AddHeader("CRUD",				"",							"t_text", 			  8, 		 40,	false);
    GridObj3.AddHeader("SEQ_NO",			"SEQ_NO", 					"t_text", 			  8,		 85,	false);
    GridObj3.AddHeader("case_count_1",		"case_count_1", 				"t_text", 			  50,		 40,	true);
    GridObj3.AddHeader("case_count_2",		"case_count_2", 				"t_text", 			  50,		 40,	true);
    GridObj3.AddHeader("pk_cr",				"pk_cr",						"t_text",			 50,		 10,	false);
    GridObj3.AddHeader("pk_cr_group",		"pk_cr_group",					"t_text",			 50,		 10,	false);
    GridObj3.AddHeader("make",				"make",						"t_text",			 50,		 10,	true);
    GridObj3.AddHeader("group_no",			"그룹",						"t_text",			 20,		 66,	false);
    GridObj3.AddHeader("pro_type",			"Type",						"t_combo",		100,		 80,	false);
    GridObj3.AddHeader("g_sel1",			"1",							"t_imagetext",		 40,		 20,	false);
    GridObj3.AddHeader("g_sel2",			"2",							"t_imagetext",	 	 40,		 20,	false);
    GridObj3.AddHeader("g_sel3",			"3",							"t_imagetext",		 40,		 20,	false);
    GridObj3.AddHeader("part_name",			"품명",						"t_text",			100,		200,	false);
    GridObj3.AddHeader("part_type",			"제작구분",					"t_combo",		100,		 60,	false);
    //GridObj3.AddHeader("Fam_group",		"Family\n금형",				"t_combo",		100,		 60,	true);
    //GridObj3.AddHeader("sel_group",		"개조금형",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("mix_group",			"복합금형",					"t_combo",		100,		 60,	false);
    GridObj3.AddHeader("part_no",			"품번",						"t_text",			100,		 80,	false);
    GridObj3.AddHeader("net_1",				"NetSize\n장측",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("net_2",				"NetSize\n단측",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("net_3",				"NetSize\n깊이",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("usage",			"조립\nU/S",					"t_text",			100,		 40,	false);
    GridObj3.AddHeader("meterial",			"Material",					"t_text",			100,		 80,	false);
    GridObj3.AddHeader("t_size",				"두께",						"t_text",			100,		 40,	false);
    GridObj3.AddHeader("w_size",			"폭",							"t_text",			100,		 40,	false);
    GridObj3.AddHeader("p_size",			"Pitch",						"t_text",			100,		 40,	false);
    GridObj3.AddHeader("plating",			"Finish",						"t_text",			100,		 60,	false);
    GridObj3.AddHeader("m_maker",			"Maker",						"t_text",			100,		 60,	false);
    GridObj3.AddHeader("m_mone",			"단위",						"t_text",			100,		 50,	false);
    GridObj3.AddHeader("unitcost",			"unitcost",					"t_text",			100,		 60,	false);
    GridObj3.AddHeader("unitcost_txt",			"단가\n(￦/Kg)",				"t_text",			100,		 60,	false);
    GridObj3.AddHeader("grade_name",		"grade_name",					"t_text",			100,		 60,	false);
    GridObj3.AddHeader("grade_color",		"grade_color",					"t_text",			100,		 60,	false);
    GridObj3.AddHeader("order_con",			"발주\n조건",				"t_text",			100,		 40,	false);
    GridObj3.AddHeader("h_weight",			"부품\n중량",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("h_scrap",			"스크랩\n중량",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("t_weight",			"제품\n총중량",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("recycle",			"재생\n비율\n%",			"t_text",			100,		 50,	false);
    GridObj3.AddHeader("die_no",			"Die No",						"t_text",			100,		 80,	false);
    GridObj3.AddHeader("cavity",				"C/V",						"t_text",			100,		 50,	false);
    GridObj3.AddHeader("m_su",				"금형\n벌수",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("mold_cost",			"금형비\n(천원)",				"t_number",		10.4,		 80,	false);
    GridObj3.AddHeader("mold_c_type",		"감가\n조건",				"t_combo",		100,		 50,	false);
    GridObj3.AddHeader("make_type",			"생산처1",					"t_combo",		100,		 60,	false);
    GridObj3.AddHeader("pro_1",				"생산처2",					"t_combo",		100,		 60,	false);
    GridObj3.AddHeader("ton",				"설비\nTon",					"t_text",			100,		 50,	false);
    GridObj3.AddHeader("spm",				"C/T\n(SPM)",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("method_type",		"공법"		,				"t_combo",		100,		 80,	false);
    GridObj3.AddHeader("pro_level",			"선택"		,				"t_combo",		100,		 50,	false);
    GridObj3.AddHeader("pro_level_txt",		"인원",						"t_text",			100,		 50,	false);
    GridObj3.AddHeader("line_su",			"조립\n라인",				"t_text",			100,		 50,	false);
    GridObj3.AddHeader("sul_cost",			"설비비\n(천원)",				"t_number",		10.4,		 80,	false);
    GridObj3.AddHeader("plating_type",		"구분1",						"t_combo",		100,		 80,	false);
    GridObj3.AddHeader("type_2",			"구분2",						"t_combo",		100,		 50,	false);
    GridObj3.AddHeader("plating_cost",		"￦/Kg\n￦/EA",				"t_text",			100,		 80,	false);
    GridObj3.AddHeader("type_1",			"구분"		,				"t_combo",		100,		 60,	false);
      GridObj3.AddHeader("t_mone",			"단위\n(Sec)",				"t_combo",		100,		 50,	false);
    GridObj3.AddHeader("type_cost",			"단가\n(시간/EA)",			"t_number",		10.4,		 80,	false);
    GridObj3.AddHeader("t_order",			"발주\n조건",				"t_combo",		100,		 50,	false);
    GridObj3.AddHeader("pack_type",         "포장유형",                 "t_text",         100,         50,    false);
    GridObj3.AddHeader("in_pack",			"내장\n(EA/봉지)",			"t_number",		10.4,		 63,	false);
    GridObj3.AddHeader("out_pack",			"외장\n(EA/Box)",				"t_number",		10.4,		 60,	false);
      GridObj3.AddHeader("store",				"보관",						"t_text",			100,		 85,	false);
      GridObj3.AddHeader("dest",				"납입처",						"t_text",			100,		 85,	false);
    GridObj3.AddHeader("dis_cost",			"물류비",						"t_number",		10.4,		 50,	false);
    GridObj3.AddHeader("distri_cost",			"물류\n흐름비",				"t_text",			100,		 50,	false);
      GridObj3.AddHeader("royalty",			"로얄티\n유무",				"t_combo",		100,		 50,	false);

    /* yazaki 투자비 제거 - 20101223 이성수J // 필드 yazaki_ro → 포장용투자비로대체
    GridObj3.AddHeader("yzk_mone",			"단위",						"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("yazaki_ro",			"천원/천엔",					"t_text",			100,		 80,	true);
    */

    GridObj3.AddHeader("etc_cost",			"기타\n투자비\n(천원)",		"t_number",		10.4,		 80,	false);
    GridObj3.AddHeader("yazaki_ro",			"포장용\n투자비\n(천원)",		"t_number",		10.4,		 80,	false);
    GridObj3.AddHeader("part_note",			"그외 특기사항",				"t_text",			2000,	300,	false);

    //그룹생성
    GridObj3.AddGroup("CHK_TOTAL", 		"선택");
       GridObj3.AddGroup("PRO_TOTAL", 		"제품Level");
       GridObj3.AddGroup("PRO_LIST_TOTAL", "제품내역");
       GridObj3.AddGroup("meterial_TOTAL", "주 재료내역");
       GridObj3.AddGroup("MOLD_TOTAL", 	"금형내역");
       GridObj3.AddGroup("MAKE_TOTAL", 	"생산내역");
       GridObj3.AddGroup("PLAT_TOTAL", 	"도금/후처리");
       GridObj3.AddGroup("TYPE_TOTAL", 	"외주/구매/OEM/검사/포장/기타");
       GridObj3.AddGroup("PACK_TOTAL", 	"포장내역");
       GridObj3.AddGroup("DIS_TOTAL", 		"물류흐름");
       //GridObj3.AddGroup("YZK_TOTAL", 		"YAZAKI\n기술료");

/****************************************************************
    그룹에 헤더 추가
*****************************************************************/

    //select
      GridObj3.AppendHeader("CHK_TOTAL", "SELECTED");
      GridObj3.AppendHeader("CHK_TOTAL", "CRUD");

    //제품Level
      GridObj3.AppendHeader("PRO_TOTAL", "group_no");
      GridObj3.AppendHeader("PRO_TOTAL", "pro_type");
      GridObj3.AppendHeader("PRO_TOTAL", "g_sel1");
      GridObj3.AppendHeader("PRO_TOTAL", "g_sel2");
      GridObj3.AppendHeader("PRO_TOTAL", "g_sel3");

    //제품내역
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "part_name");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "part_type");
 // GridObj3.AppendHeader("PRO_LIST_TOTAL", "Fam_group");
 // GridObj3.AppendHeader("PRO_LIST_TOTAL", "sel_group");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "mix_group");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "part_no");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "net_1");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "net_2");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "net_3");
      GridObj3.AppendHeader("PRO_LIST_TOTAL", "usage");
  //주 재료내역
      GridObj3.AppendHeader("meterial_TOTAL", "meterial");
      GridObj3.AppendHeader("meterial_TOTAL", "t_size");
      GridObj3.AppendHeader("meterial_TOTAL", "w_size");
      GridObj3.AppendHeader("meterial_TOTAL", "p_size");
      GridObj3.AppendHeader("meterial_TOTAL", "plating");
      GridObj3.AppendHeader("meterial_TOTAL", "m_maker");
      GridObj3.AppendHeader("meterial_TOTAL", "m_mone");
      GridObj3.AppendHeader("meterial_TOTAL", "unitcost");
      GridObj3.AppendHeader("meterial_TOTAL", "unitcost_txt");
      GridObj3.AppendHeader("meterial_TOTAL", "grade_name");
      GridObj3.AppendHeader("meterial_TOTAL", "grade_color");
      GridObj3.AppendHeader("meterial_TOTAL", "order_con");
      GridObj3.AppendHeader("meterial_TOTAL", "h_weight");
      GridObj3.AppendHeader("meterial_TOTAL", "h_scrap");
      GridObj3.AppendHeader("meterial_TOTAL", "t_weight");
      GridObj3.AppendHeader("meterial_TOTAL", "recycle");
    //금형내역
      GridObj3.AppendHeader("MOLD_TOTAL", "die_no");
      GridObj3.AppendHeader("MOLD_TOTAL", "cavity");
      GridObj3.AppendHeader("MOLD_TOTAL", "m_su");
      GridObj3.AppendHeader("MOLD_TOTAL", "mold_cost");
      GridObj3.AppendHeader("MOLD_TOTAL", "mold_c_type");
    //생산내역
    GridObj3.AddGroup("PP_SU", 	"작업인원");

      GridObj3.AppendHeader("MAKE_TOTAL", "make_type");
      GridObj3.AppendHeader("MAKE_TOTAL", "pro_1");
      GridObj3.AppendHeader("MAKE_TOTAL", "ton");
      GridObj3.AppendHeader("MAKE_TOTAL", "spm");
      GridObj3.AppendHeader("MAKE_TOTAL", "method_type");
      GridObj3.AppendGroup("MAKE_TOTAL", "PP_SU");
      GridObj3.AppendHeader("PP_SU", "pro_level");
      GridObj3.AppendHeader("PP_SU", "pro_level_txt");
      GridObj3.AppendHeader("MAKE_TOTAL", "line_su");
      GridObj3.AppendHeader("MAKE_TOTAL", "sul_cost");
    //도금/후처리
      GridObj3.AppendHeader("PLAT_TOTAL", "plating_type");
      GridObj3.AppendHeader("PLAT_TOTAL", "type_2");
      GridObj3.AppendHeader("PLAT_TOTAL", "plating_cost");
    //외주/구매/OEM/검사/포장/기타
      GridObj3.AppendHeader("TYPE_TOTAL", "type_1");
      GridObj3.AppendHeader("TYPE_TOTAL", "t_mone");
      GridObj3.AppendHeader("TYPE_TOTAL", "type_cost");
      GridObj3.AppendHeader("TYPE_TOTAL", "t_order");
    //포장내역
      GridObj3.AppendHeader("PACK_TOTAL", "pack_type");
      GridObj3.AppendHeader("PACK_TOTAL", "in_pack");
      GridObj3.AppendHeader("PACK_TOTAL", "out_pack");

  //물류흐름
      GridObj3.AppendHeader("DIS_TOTAL", "store");
      GridObj3.AppendHeader("DIS_TOTAL", "dest");
      GridObj3.AppendHeader("DIS_TOTAL", "dis_cost");
       GridObj3.AppendHeader("DIS_TOTAL", "distri_cost");

       //마우스오른쪽비활성-내용부분
      //GridObj3.bUseDefaultContextMenu=false;
    //GridObj3.bUserContextMenu=true;

     //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj3.BoundHeader();

    //저장모드를 사용해 서버사이드와 통신한다.
    GridObj3.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

    //이미지리스트에 이미지 URL을 추가한다.
    GridObj3.AddImageList("g_sel1", "/plm/jsp/cost/acc_img/btn_minus.jpg");
    GridObj3.AddImageList("g_sel2", "/plm/jsp/cost/acc_img/add.jpg");
    GridObj3.AddImageList("g_sel3", "/plm/jsp/cost/acc_img/add.jpg");


/****************************************************************
    콤보리스트 생성
*****************************************************************/

    //GridObj3.AddComboList("pro_type","A");	멀티콤보로 할때 사용
    //제품Type List
    GridObj3.AddComboListValue("pro_type",	""			,	"empty");
    GridObj3.AddComboListValue("pro_type",	"조립"		,	"assy");
    GridObj3.AddComboListValue("pro_type",	"Insert"		,	"Insert");
    GridObj3.AddComboListValue("pro_type",	"sub조립"	,	"sub_assy");
    GridObj3.AddComboListValue("pro_type",	"HS'G"		,	"HSG");
    GridObj3.AddComboListValue("pro_type",	"HS'G-Box"	,	"HSG-Box");
    GridObj3.AddComboListValue("pro_type",	"sub_Insert"	,	"sub_Insert");
    GridObj3.AddComboListValue("pro_type",	"TM'L"		,	"TML");
    GridObj3.AddComboListValue("pro_type",	"TML-조립"	,	"TML-조립");
    GridObj3.AddComboListValue("pro_type",	"부재료"		,	"부재료");
    GridObj3.AddComboListValue("pro_type",	"구매"		,	"구매");

    //제작구분List
    GridObj3.AddComboListValue("part_type",	"신규"		,	"신규");
    GridObj3.AddComboListValue("part_type",	"공용"    		,	"공용");
    GridObj3.AddComboListValue("part_type",	"개조"    		,	"개조");
    GridObj3.AddComboListValue("part_type",	"수정"    		,	"수정");
    GridObj3.AddComboListValue("part_type",	"복합금형"	,	"복합금형");
    GridObj3.AddComboListValue("part_type",	"Family"  		,	"Family");

    //복합금형List
    GridObj3.AddComboListValue("mix_group",	""   			,	"empty");
    GridObj3.AddComboListValue("mix_group",	"선생산"  	,	"선생산");
    GridObj3.AddComboListValue("mix_group",	"후생산"  	,	"후생산");
    GridObj3.AddComboListValue("mix_group",	"동시생산"	,	"동시생산");
    //감가조건
    GridObj3.AddComboListValue("mold_c_type","감가"	,	"감가");
    GridObj3.AddComboListValue("mold_c_type","지급"  	,	"지급");

    //생산처1
    GridObj3.AddComboListValue("make_type",	""		,	"empty");
    GridObj3.AddComboListValue("make_type",	"사내"	,	"사내");
    GridObj3.AddComboListValue("make_type",	"외주"    	,	"외주");
    GridObj3.AddComboListValue("make_type",	"구매"    	,	"구매");
    GridObj3.AddComboListValue("make_type",	"OEM"     ,	"OEM");

    //세부생산처
    GridObj3.AddComboListValue("pro_1",	""			,	"empty");
    GridObj3.AddComboListValue("pro_1",	"생산1"		,	"생산1");
    GridObj3.AddComboListValue("pro_1",	"생산2"    	,	"생산2");
    GridObj3.AddComboListValue("pro_1",	"생산3"    	,	"생산3");
    GridObj3.AddComboListValue("pro_1",	"생산4"    	,	"생산4");
    GridObj3.AddComboListValue("pro_1",	"중국"		,	"중국");
    GridObj3.AddComboListValue("pro_1",	"유상"		,	"유상");
    GridObj3.AddComboListValue("pro_1",	"무상"		,	"무상");
    GridObj3.AddComboListValue("pro_1",	"외주"		,	"외주");

    //공법
    GridObj3.AddComboListValue("method_type",	""  		,	"empty");
    GridObj3.AddComboListValue("method_type",	"단순"   	,	"단순");
    GridObj3.AddComboListValue("method_type",	"JIG이용"	,	"JIG이용");
    GridObj3.AddComboListValue("method_type",	"자동"   	,	"자동");
    GridObj3.AddComboListValue("method_type",	"수동"   	,	"수동");
    GridObj3.AddComboListValue("method_type",	"Box"    	,	"Box");
    GridObj3.AddComboListValue("method_type",	"Insert" 	,	"Insert");

    //작업인원
    GridObj3.AddComboListValue("pro_level",	"표준"	,	"표준");
    GridObj3.AddComboListValue("pro_level",	"기타"	,	"기타");

    //도금,후처리
    GridObj3.AddComboListValue("plating_type",		"" 			,	"empty");
    GridObj3.AddComboListValue("plating_type",		"재료도금"	,	"재료도금");
    GridObj3.AddComboListValue("plating_type",		"후도금" 		,	"후도금");
    GridObj3.AddComboListValue("plating_type",		"리와인딩"	,	"리와인딩");
    GridObj3.AddComboListValue("plating_type",		"열처리" 		,	"열처리");
    GridObj3.AddComboListValue("plating_type",		"세척" 		,	"세척");
    GridObj3.AddComboListValue("plating_type",		"변색방지" 		,	"변색방지");
    GridObj3.AddComboListValue("plating_type",		"기타" 		,	"기타");

    //도금사양
    GridObj3.AddComboListValue("type_2",	""  ,	"empty");
    GridObj3.AddComboListValue("type_2",	"Sn" 	 ,	"Sn");
    GridObj3.AddComboListValue("type_2",	"Au" 	 ,	"Au");
    GridObj3.AddComboListValue("type_2",	"Ag" 	 ,	"Ag");
    GridObj3.AddComboListValue("type_2",	"3가Cr"  	 ,	"3가Cr");
    GridObj3.AddComboListValue("type_2",	"기타" 	 ,	"기타" );

    //외주,구매...
    GridObj3.AddComboListValue("type_1",	""  ,	"empty" );
    GridObj3.AddComboListValue("type_1",	"임가공" 	,	"임가공");
    GridObj3.AddComboListValue("type_1",	"구매"   	,	"구매");
    GridObj3.AddComboListValue("type_1",	"OEM"   	,	"OEM");
    GridObj3.AddComboListValue("type_1",	"검사"   	,	"검사");
    GridObj3.AddComboListValue("type_1",	"포장"   	,	"포장");
    GridObj3.AddComboListValue("type_1",	"기타"   	,	"기타");

    //화폐단위-외주구매
    GridObj3.AddComboListValue("t_mone",	""  ,	"empty" );
    GridObj3.AddComboListValue("t_mone",	"KRW" ,	"KRW");
    GridObj3.AddComboListValue("t_mone",	"EUR" ,	"EUR");
    GridObj3.AddComboListValue("t_mone",	"CNY" ,	"CNY");
    GridObj3.AddComboListValue("t_mone",	"JPY" ,	"JPY");
    GridObj3.AddComboListValue("t_mone",	"USD" ,	"USD");
    GridObj3.AddComboListValue("t_mone",	"Sec" ,	"Sec");

    //발주조건
    GridObj3.AddComboListValue("t_order",	""  ,	"empty" );
    GridObj3.AddComboListValue("t_order",	"내자" 	 ,	"내자");
    GridObj3.AddComboListValue("t_order",	"CIF"  	 ,	"CIF" );
    GridObj3.AddComboListValue("t_order",	"FOB" 	 ,	"FOB" );
    GridObj3.AddComboListValue("t_order",	"L/C" 	 ,	"L/C" );

    //포장유형
    /* GridObj3.AddComboListValue("pack_type",	"" 	 	 ,	"empty" );
    GridObj3.AddComboListValue("pack_type",	"비닐" 	 ,	"비닐");
    GridObj3.AddComboListValue("pack_type",	"Reel" 	 ,	"Reel");
    GridObj3.AddComboListValue("pack_type",	"회수용"	 ,	"회수용");
    GridObj3.AddComboListValue("pack_type",	"Tray"  	 ,	"Tray");
    GridObj3.AddComboListValue("pack_type",	"골판지"	 ,	"골판지"); */

  //로얄티유무
      GridObj3.AddComboListValue("royalty", "有", "1");
      GridObj3.AddComboListValue("royalty", "無", "2");

    /*
    YZK기술료단위
    GridObj3.AddComboListValue("yzk_mone",	"" ,	"empty");
    GridObj3.AddComboListValue("yzk_mone",	"KRW" ,	"KRW");
    GridObj3.AddComboListValue("yzk_mone",	"JPY" ,	"JPY");
    */

    //전체선택
    GridObj3.SetColHDCheckBoxVisible("SELECTED", true);

      //Header 정렬

        GridObj3.SetColCellAlign('SELECTED',		'center');
        GridObj3.SetColCellAlign('CRUD',			'center');
        GridObj3.SetColCellAlign('SEQ_NO',		'center');
        GridObj3.SetColCellAlign('pk_cr',			'center');
        GridObj3.SetColCellAlign('pk_cr_group',	'center');
        GridObj3.SetColCellAlign('group_no',		'left');
          GridObj3.SetColCellAlign('g_sel1',			'center');
        GridObj3.SetColCellAlign('g_sel2',			'center');
        GridObj3.SetColCellAlign('g_sel3',			'center');
        GridObj3.SetColCellAlign('part_name',		'left');
        GridObj3.SetColCellAlign('part_no',		'center');
        GridObj3.SetColCellAlign('net_1',			'center');
        GridObj3.SetColCellAlign('net_2',			'center');
        GridObj3.SetColCellAlign('net_3',			'center');
        GridObj3.SetColCellAlign('usage',			'center');
        GridObj3.SetColCellAlign('meterial',		'center');
        GridObj3.SetColCellAlign('t_size',			'center');
        GridObj3.SetColCellAlign('w_size',		'center');
        GridObj3.SetColCellAlign('p_size',			'center');
        GridObj3.SetColCellAlign('plating',			'center');
        GridObj3.SetColCellAlign('m_maker',		'center');
        GridObj3.SetColCellAlign('m_mone',		'center');
        GridObj3.SetColCellAlign('unitcost',		'center');
        GridObj3.SetColCellAlign('unitcost_txt',		'center');
        GridObj3.SetColCellAlign('grade_name',	'center');
        GridObj3.SetColCellAlign('grade_color',		'center');
        GridObj3.SetColCellAlign('order_con',		'center');
        GridObj3.SetColCellAlign('h_weight',		'center');
        GridObj3.SetColCellAlign('h_scrap',		'center');
        GridObj3.SetColCellAlign('t_weight',		'center');
        GridObj3.SetColCellAlign('recycle',		'center');
        GridObj3.SetColCellAlign('die_no',			'center');
        GridObj3.SetColCellAlign('cavity',			'center');
        GridObj3.SetColCellAlign('m_su',			'center');
        GridObj3.SetColCellAlign('ton',			'center');
        GridObj3.SetColCellAlign('spm',			'center');
        GridObj3.SetColCellAlign('pro_level_txt',	'center');
        GridObj3.SetColCellAlign('line_su',		'center');
        GridObj3.SetColCellAlign('plating_cost',		'center');
        GridObj3.SetColCellAlign('store',			'center');
        GridObj3.SetColCellAlign('dest',			'center');
      GridObj3.SetColCellAlign('distri_cost', 		'right');
      GridObj3.SetColCellAlign('part_note', 		'left');

        //t_number 타입의 컬럼을 포맷타입으로  지정한다.
    GridObj3.SetNumberFormat("mold_cost", 	"#,###.###");
    GridObj3.SetNumberFormat("sul_cost", 		"#,###.###");
    GridObj3.SetNumberFormat("type_cost", 	"#,###.###");
    GridObj3.SetNumberFormat("dis_cost", 		"#,###.###");
    GridObj3.SetNumberFormat("etc_cost", 		"#,###.###");
    GridObj3.SetNumberFormat("yazaki_ro", 	"#,###.###");
        GridObj3.SetNumberFormat("in_pack",		"#,###.###");
        GridObj3.SetNumberFormat("out_pack",		"#,###.###");

    //컬럼 숨기기
      GridObj3.SetColHide("SEQ_NO", true);
      GridObj3.SetColHide("case_count_1",true);
      GridObj3.SetColHide("case_count_2",true);
      GridObj3.SetColHide("pk_cr",true);
      GridObj3.SetColHide("pk_cr_group", true);
      GridObj3.SetColHide("make", true);
      GridObj3.SetColHide("net_1", true);
      GridObj3.SetColHide("net_2", true);
      GridObj3.SetColHide("net_3", true);
 // GridObj3.SetColHide("Fam_group", true);
 // GridObj3.SetColHide("sel_group", true);
      GridObj3.SetColHide("mix_group", false);
      GridObj3.SetColHide("unitcost", true);
      GridObj3.SetColHide("unitcost", true);
      GridObj3.SetColHide("grade_name", true);
      GridObj3.SetColHide("grade_color", true);
      GridObj3.SetColHide("distri_cost", true);

      //화면분할박스비활성
      GridObj3.bSplitBoxVisible = false;

      GridObj3.SetColCellMultiLine("part_note", true);

}
function setHeader5() {
    //그리드에 컬럼을 등록한다.

    GridObj5.AddHeader("group_no",		"그룹",			"t_text",			  20,			 66,		false);
    GridObj5.AddHeader("part_name",		"품명",			"t_text",			 100,			200,		false);
    GridObj5.AddHeader("m_total_cost",	"재료비",			"t_number",		10.3,			 60,	  	false);
    GridObj5.AddHeader("labor_cost",		"노무비",			"t_number",	 	10.3,			 60,		false);
    GridObj5.AddHeader("common_cost",	"제조경비",		"t_number",	 	10.3,			 65,		false);
    GridObj5.AddHeader("ad_cost",		"관리비",			"t_number",	 	10.3,			 60,		false);
    GridObj5.AddHeader("g_depr_cost",	"감가비",			"t_number",	 	10.3,			 60,		false);
    GridObj5.AddHeader("g_actual_cost",	"총원가",			"t_number",	 	10.3,			 60,		false);
    GridObj5.AddHeader("g_earn_rate",	"수익율",			"t_number",	 	10.3,			 60,		false);

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj5.BoundHeader();

    //t_number 타입의 컬럼을 포맷타입으로  지정한다.
    GridObj5.SetNumberFormat("m_total_cost", 	"#,###.###");
    GridObj5.SetNumberFormat("labor_cost", 	"#,###.###");
    GridObj5.SetNumberFormat("common_cost", 	"#,###.###");
    GridObj5.SetNumberFormat("ad_cost", 		"#,###.###");
    GridObj5.SetNumberFormat("g_depr_cost", 	"#,###.###");
    GridObj5.SetNumberFormat("g_actual_cost", 	"#,###.###");
    GridObj5.SetNumberFormat("g_earn_rate", 	"###.##%");
/****************************************************************
    Header 정렬 비활성
*****************************************************************/
    GridObj5.SetColCellSortEnable('group_no',				'false');
    GridObj5.SetColCellSortEnable('part_name',				'false');
    GridObj5.SetColCellSortEnable('m_total_cost',			'false');
    GridObj5.SetColCellSortEnable('labor_cost',				'false');
    GridObj5.SetColCellSortEnable('common_cost',			'false');
    GridObj5.SetColCellSortEnable('ad_cost',				'false');
    GridObj5.SetColCellSortEnable('g_depr_cost',			'false');
    GridObj5.SetColCellSortEnable('g_actual_cost',			'false');
    GridObj5.SetColCellSortEnable('g_earn_rate',			'false');
}
/********************************************************************
 조회 1
********************************************************************/

function doQuery1() {
    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj1.SetParam("mode"       , "cost_re_viewSearch1");
    GridObj1.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj1.SetParam("rev_no"	   , document.part_1.rev_no.value);
    GridObj1.SetParam("data_type"  , document.part_1.data_type.value);
    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj1.DoQuery(servlet_url);
}

/********************************************************************
 조회 2
********************************************************************/
function doQuery2() {
    var servlet_url = "/plm/servlet/e3ps/costComServlet";

    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj2.SetParam("mode", "cost_re_viewSearch2");
    GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj2.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj2.SetParam("data_type", document.part_1.data_type.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj2.DoQuery(servlet_url);

}
/********************************************************************
 조회 3
********************************************************************/
function doQuery3() {

    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj3.SetParam("mode", "cost_re_viewSearch3");
    GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj3.SetParam("data_type", document.part_1.data_type.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj3.DoQuery(servlet_url);
}

/********************************************************************
data delete
********************************************************************/
function Delete_call() {

    <% if(!login_id.equals("pro_man") && !login_id.equals("admin") && !etc_auth.equals("ok") ){%>
        alert("Data삭제권한이 없습니다.\n관리자에게 문의바랍니다.(1307)");
    <%}else if((!login_id.equals("pro_man") && !login_id.equals("admin")) && (approval.equals("ok") || approval.equals("finish") || approval.equals("complete"))){%>
        alert("결재가진행된 이후에는 Data삭제가 불가능 합니다.\n관리자에게 문의바랍니다.(1307)")
    <%}else{%>
    if(confirm('해당요청서를 삭제하시겠습니까?\n확인을 누르시면 요청서 전체가 삭제됩니다.')){
        var servlet_url = "/plm/servlet/e3ps/costComServlet";
        //WiseGrid가 서버에 전송할 Param을 셋팅한다.
        GridObj3.SetParam("mode", "cost_re_view_delete");
        GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
        GridObj3.SetParam("data_type", document.part_1.data_type.value);

        //WiseGrid가 서버와 통신시에 데이터를 전달한다.
        GridObj3.DoQuery(servlet_url);
    }
<%}%>
}

/********************************************************************
로우가 체크되었는지 확인한다.
********************************************************************/

function chkSelected() {
    chkCount = 0;

    for(i = 0; i < GridObj3.GetRowCount(); i++) { //그리드 데이터의 로우수를 반환한다.

        if(GridObj3.GetCellValue("SELECTED", i) == "1") //지정한 셀의 값을 가져온다.
            chkCount = chkCount + 1;
    }

    if(chkCount == 0) {
        return false;
    }
    return true;
}

function checkRows()
{
    var GridObj3 = document.WiseGrid3;

    for(i = 0; i < GridObj2.GetRowCount(); i++)
    {
        if(GridObj3.GetCellValue("SELECTED", i) == 1)
            return true;
    }

    alert("선택된 로우가 없습니다.");
    return false;
}
/****************************************************************************************************************
EXCEL 내보내기 - 그리드의 데이터를 사용자의 PC에 파일로 저장한다. SetColHide()로 숨겨진 컬럼은 내보내지 않는다.
*****************************************************************************************************************/
function excelDown() {
    //GridObj3.ExcelExport("", "", true, true);
    var url =  "./cost_excel.asp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&data_type="+'<%=data_type%>';
    window.open(url, "window_222", "width=1024,height=800,scrollbars=yes,resizable=yes");
}

/**********************************************
서버와의 통신이 정상적으로 완료되면 발생한다.
**********************************************/
function GridEndQuery1() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    //if(GridObj1.getRowCount() < 0) {
        document.part_1.pk_cr_group.value= GridObj1.GetCellValue("pk_cr_group",0);
        document.part_1.etc_note.value= GridObj1.GetCellValue("etc_note",0);

    // 첨부파일명 replace /Upload/cost_file/원가산출요청/ → ""

        file_1_str = GridObj1.GetCellValue("file_1",0);
        file_1_str = file_1_str.replace("/Upload/cost_file/원가산출요청/","1) ");

        file_2_str = GridObj1.GetCellValue("file_2",0);
        file_2_str = file_2_str.replace("/Upload/cost_file/원가산출요청/","2) ");

        file_3_str = GridObj1.GetCellValue("file_3",0);
        file_3_str = file_3_str.replace("/Upload/cost_file/원가산출요청/","3) ");

        document.part_1.file_1.value = file_1_str;
        document.part_1.file_2.value = file_2_str;
        document.part_1.file_3.value = file_3_str;

    //}
}
function GridEndQuery3() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj3.GetParam("mode");
    if(mode == "cost_acc") {
        //alert(GridObj3.GetParam("mode"))
        if(GridObj3.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
            //서버에서 insert_data 셋팅한 파라미터를 가져온다.
            var cost_acc = GridObj3.GetParam("cost_acc");
            //var test_txt = GridObj3.GetParam("test_txt");
            //fieldset에 결과 값을 보이게 한다.
            alert("산출작업이 완료되었습니다.");

            setCostAcc(cost_acc); // 간이산출 그리드에 결과값 넣기
            //document.part_1.result.innerHTML= '<legend>간이산출</legend>'+cost_acc;
        } else	{
            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert(error_msg);
        }
        doQuery3();
    }
    if(mode == "del") {
        alert("Data가 삭제되었습니다");
        //history.back();
        //window.location.href ="/plm/jsp/cost/list/mainList.jsp?select_name="+'<%=select_name%>'+"&loading=goIndex";
        window.location.href ="/plm/jsp/cost/index.html";
    }

}

 /**********************************************
  setCostAcc
 **********************************************/
 function setCostAcc(cost_acc) {
     gridDeleteRow();
     var arrCostAcc = eval(cost_acc);
     var temp = arrCostAcc[0].group_no;
     var cnt = 0;

     temp = temp.substring(0, 4);

     for(var ii=0; ii < arrCostAcc.length; ii++) {
         if(temp != arrCostAcc[ii].group_no.substring(0, 4)) {
             cnt = ii;
             temp = arrCostAcc[ii].group_no.substring(0, 4);
         }

         if(arrCostAcc[ii].group_no.match("sum")) {
             try {
                  GridObj5.insertRow(cnt);
                 GridObj5.SetCellValue( "group_no" , cnt, arrCostAcc[ii].group_no.substring(0,4)  );
                 //GridObj5.SetCellValue( "group_no" , cnt, arrCostAcc[ii].group_no );
                 GridObj5.SetCellValue( "part_name" , cnt, arrCostAcc[ii].part_name );
                 GridObj5.SetCellValue( "m_total_cost" , cnt, arrCostAcc[ii].m_total_cost );
                 GridObj5.SetCellValue( "labor_cost" , cnt, arrCostAcc[ii].labor_cost );
                 GridObj5.SetCellValue( "common_cost" , cnt, arrCostAcc[ii].common_cost );
                 GridObj5.SetCellValue( "ad_cost" , cnt, arrCostAcc[ii].ad_cost );
                 GridObj5.SetCellValue( "g_depr_cost" , cnt, arrCostAcc[ii].g_depr_cost );
                 GridObj5.SetCellValue( "g_actual_cost" , cnt, arrCostAcc[ii].g_actual_cost );
                 GridObj5.SetCellValue( "g_earn_rate" , cnt, arrCostAcc[ii].g_earn_rate );

             } catch(e) {
                  GridObj5.AddRow();

                 GridObj5.SetCellValue( "group_no" , ii, arrCostAcc[ii].group_no.substring(0,4) );
                 GridObj5.SetCellValue( "part_name" , ii, arrCostAcc[ii].part_name );
                 GridObj5.SetCellValue( "m_total_cost" , ii, arrCostAcc[ii].m_total_cost );
                 GridObj5.SetCellValue( "labor_cost" , ii, arrCostAcc[ii].labor_cost );
                 GridObj5.SetCellValue( "common_cost" , ii, arrCostAcc[ii].common_cost );
                 GridObj5.SetCellValue( "ad_cost" , ii, arrCostAcc[ii].ad_cost );
                 GridObj5.SetCellValue( "g_depr_cost" , ii, arrCostAcc[ii].g_depr_cost );
                 GridObj5.SetCellValue( "g_actual_cost" , ii, arrCostAcc[ii].g_actual_cost );
                 GridObj5.SetCellValue( "g_earn_rate" , ii, arrCostAcc[ii].g_earn_rate );
             }

                 GridObj5.SetCellBgColor('group_no', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('part_name', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('m_total_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('labor_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('common_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('ad_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('g_depr_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('g_actual_cost', cnt , '233|230|238');
                 GridObj5.SetCellBgColor('g_earn_rate', cnt , '233|230|238');

                 GridObj5.SetCellFontBold('group_no', cnt, 'false');
                 GridObj5.SetCellFontBold('part_name', cnt, 'false');
                 GridObj5.SetCellFontBold('m_total_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('labor_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('common_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('ad_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('g_depr_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('g_actual_cost', cnt, 'true');
                 GridObj5.SetCellFontBold('g_earn_rate', cnt, 'true');

         } else {

             GridObj5.AddRow();

            if (arrCostAcc[ii].group_no.length == 4){
                GridObj5.SetCellValue( "group_no" , ii, "조립" );
            }
            else{
                GridObj5.SetCellValue( "group_no" , ii, arrCostAcc[ii].group_no );
            }
             GridObj5.SetCellValue( "part_name" , ii, arrCostAcc[ii].part_name );
             GridObj5.SetCellValue( "m_total_cost" , ii, arrCostAcc[ii].m_total_cost );
             GridObj5.SetCellValue( "labor_cost" , ii, arrCostAcc[ii].labor_cost );
             GridObj5.SetCellValue( "common_cost" , ii, arrCostAcc[ii].common_cost );
             GridObj5.SetCellValue( "ad_cost" , ii, arrCostAcc[ii].ad_cost );
             GridObj5.SetCellValue( "g_depr_cost" , ii, arrCostAcc[ii].g_depr_cost );
             GridObj5.SetCellValue( "g_actual_cost" , ii, arrCostAcc[ii].g_actual_cost );
             GridObj5.SetCellValue( "g_earn_rate" , ii, arrCostAcc[ii].g_earn_rate );
          }
     }
}

function gridDeleteRow() {
    var jj = GridObj5.getRowCount();

    for(var ii=0; ii<jj; ii++) {
        GridObj5.DeleteRow(0);
    }
}
 /**********************************************
  등록된 파일보기
 **********************************************/
function file_call1()
{
    var file_name = GridObj1.GetCellValue("file_1",0);
        file_name = file_name.replace("/Upload/cost_file/원가산출요청/","");
        file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=''";
    window.open(url);
}
function file_call2()
{
    var file_name = GridObj1.GetCellValue("file_2",0);
        file_name = file_name.replace("/Upload/cost_file/원가산출요청/","");
        file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=''";
    window.open(url);
}
function file_call3()
{
    var file_name = GridObj1.GetCellValue("file_3",0);
        file_name = file_name.replace("/Upload/cost_file/원가산출요청/","");
        file_name = escape(encodeURIComponent(file_name));
    var url = "/plm/jsp/cost/common/file_download.jsp?file_name="+file_name+"&page_name=''";
    window.open(url);
}
 /**********************************************
  파일첨부
 **********************************************/
    function  filecheck_file_1()
    {
        a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
        document.part_1.file_edit_1.checked = false;
            popUpOpen("./file_add.asp?file_type=file_1&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
    function  filecheck_file_2()
    {
        a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
          document.part_1.file_edit_2.checked = false;
          popUpOpen("./file_add.asp?file_type=file_2&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
    function  filecheck_file_3()
    {
        a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
          document.part_1.file_edit_3.checked = false;
          popUpOpen("./file_add.asp?file_type=file_3&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
 /**********************************************
     수정Page이동
 **********************************************/

 function doEdit	()
    {

        <%if((!login_id.equals("pro_man") && !login_id.equals("admin")) && (approval.equals("ok") || approval.equals("finish") || approval.equals("complete") || !etc_auth.equals("ok") )){ %>
            alert("결재가진행된 이후에는 Data수정이 불가능 합니다.");
           <%}else{%>
             var a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
             var a_rev_no = GridObj2.GetCellValue("rev_no",0);
             var data_type = document.part_1.data_type.value;
             var group_case_count = document.part_1.group_case_count.value;
            window.location.href = "/plm/jsp/cost/costdb/cost_re_edit_test.jsp?pk_cr_group="+a_pk_cr_group+"&rev_no="+a_rev_no+"&data_type="+data_type+"&group_case_count="+group_case_count ;
           <%}%>

    }
/**********************************************
    산출전 입력값 검증
**********************************************/
    var simple_cost_1 = "";

    function cost_input_chk()
    {
    for(var j=0; j<GridObj3.getRowCount; j++) {
    /******************************************************************************/
         if(GridObj3.GetCellValue("usage",j) == "" )
            {
                simple_cost_1  = "no";
                var msg_txt = GridObj3.GetCellValue("part_name",j);
                    msg_txt = msg_txt + "의 Usage가 입력되지 않았습니다.";
                alert(msg_txt);
                break;
            }
        else  if(GridObj3.GetCellHiddenValue("pro_type",j) != "assy" && GridObj3.GetCellHiddenValue("pro_type",j) != "sub_assy" && GridObj3.GetCellHiddenValue("pro_type",j) != "구매" && GridObj3.GetCellHiddenValue("pro_type",j) != "부재료" )
            {
                if(GridObj3.GetCellValue("cavity",j) == "" )
                {
                    simple_cost_1  = "no";
                    var msg_txt = GridObj3.GetCellValue("part_name",j);
                        msg_txt = msg_txt + "의 cavity가 입력되지 않았습니다.";
                    alert(msg_txt);
                    break;
                }
                if(GridObj3.GetCellValue("unitcost_txt",j) == "" )
                {
                    simple_cost_1  = "no";
                    var msg_txt = GridObj3.GetCellValue("part_name",j);
                        msg_txt = msg_txt + "의 원재료 단가가 입력되지 않았습니다.";
                    alert(msg_txt);
                    break;
                }
            }
    /******************************************************************************/
        if (GridObj3.GetCellValue("pro_type",j) == "구매")
            {
                if(GridObj3.GetCellValue("type_cost",j) == "" )
                {
                    simple_cost_1  = "no";
                    var msg_txt = GridObj3.GetCellValue("part_name",j);
                        msg_txt = msg_txt + "의 구매단가가 입력되지 않았습니다.";
                    alert(msg_txt);
                    break;
                }
            }
        else
        {
            if(GridObj3.GetCellValue("spm",j) == "")
                {
                     if(GridObj3.GetCellValue("make_type",j) == "외주" && GridObj3.GetCellValue("type_cost",j) != "" &&GridObj3.GetCellValue("method_type",j) != "Insert" && GridObj3.GetCellValue("method_type",j) != "자동")
                    {
                    }
                    else
                    {
                        simple_cost_1  = "no";
                        var msg_txt = GridObj3.GetCellValue("part_name",j);
                            msg_txt = msg_txt + "의 C/T(SPM)이 입력되지 않았습니다.";
                        alert(msg_txt);
                        break;
                    }
                }
        }
    /******************************************************************************/
        if(GridObj3.GetCellHiddenValue("pro_1",j) == "empty" )
            {
                simple_cost_1  = "no";
                var msg_txt = GridObj3.GetCellValue("part_name",j);
                msg_txt = msg_txt + "의 생산처가 입력되지 않았습니다.";
                alert(msg_txt);
                break;
            }
    /******************************************************************************/
        if(GridObj3.GetCellValue("part_type",j) != "복합금형" )
            {
                if(GridObj3.GetCellValue("group_no",j).length == 4 )
                {
                    if(GridObj3.GetCellValue("in_pack",j) == "" && GridObj3.GetCellValue("pack_type",j) != "회수용" && GridObj3.GetCellValue("pack_type",j) != "골판지" )
                    {
                        simple_cost_1  = "no";
                        var msg_txt = GridObj3.GetCellValue("part_name",j);
                        msg_txt = msg_txt + "의 포장내역의 내장수량이 입력되지 않았습니다.";
                        alert(msg_txt);
                        break;
                    }
                    if(GridObj3.GetCellValue("out_pack",j) == "")
                    {
                        simple_cost_1  = "no";
                        var msg_txt = GridObj3.GetCellValue("part_name",j);
                        msg_txt = msg_txt + "의 포장내역의 외장수량이 입력되지 않았습니다.";
                        alert(msg_txt);
                        break;
                    }
                    if(GridObj3.GetCellHiddenValue("pro_type",j) == "assy" || GridObj3.GetCellHiddenValue("pro_type",j) == "Insert" || GridObj3.GetCellHiddenValue("pro_type",j) == "HSG-Box" )
                    {
                        if(GridObj3.GetCellHiddenValue("method_type",j) == "empty")
                        {
                            simple_cost_1  = "no";
                            var msg_txt = GridObj3.GetCellValue("part_name",j);
                            msg_txt = msg_txt + "의 공법이 입력되지 않았습니다.";
                            alert(msg_txt);
                            break;
                        }
                    }
                    if(GridObj3.GetCellValue("pro_1",j) != "외주")
                    {
                        if(GridObj3.GetCellValue("method_type",j) == "자동" || GridObj3.GetCellValue("method_type",j) == "수동"  )
                        {
                            if(GridObj3.GetCellValue("sul_cost",j) == ""  )
                            {
                                simple_cost_1  = "no";
                                var msg_txt = GridObj3.GetCellValue("part_name",j);
                                msg_txt = msg_txt + "의 설비비가 입력되지 않았습니다.";
                                alert(msg_txt);
                                break;
                            }
                        }
                    }

                }

            }
    /******************************************************************************/
        if(GridObj3.GetCellValue("plating_type",j) == "후도금" )
            {
                if(GridObj3.GetCellValue("plating_cost",j) == ""  )
                {
                    simple_cost_1  = "no";
                    var msg_txt = GridObj3.GetCellValue("part_name",j);
                    msg_txt = msg_txt + "의 후도금비가 입력되지 않았습니다.";
                    alert(msg_txt);
                    break;
                }
            }
    /******************************************************************************/
        if(GridObj3.GetCellValue("plating_type",j) == "재료도금" )
            {
                if(GridObj3.GetCellValue("plating_cost",j) == ""  )
                {
                    simple_cost_1  = "no";
                    var msg_txt = GridObj3.GetCellValue("part_name",j);
                    msg_txt = msg_txt + "의 재료도금비가 입력되지 않았습니다.";
                    alert(msg_txt);
                    break;
                }
            }
    /******************************************************************************/
        if(GridObj3.GetCellHiddenValue("pro_type",j) == "TML" || GridObj3.GetCellHiddenValue("pro_type",j) == "TML-조립" || GridObj3.GetCellHiddenValue("pro_type",j) == "HSG" || GridObj3.GetCellHiddenValue("pro_type",j) == "HSG-Box" )
            {
                    if(GridObj3.GetCellHiddenValue("pro_1",j) == "생산1" || GridObj3.GetCellHiddenValue("pro_1",j) == "생산2" )
                    {
                        if(GridObj3.GetCellValue("ton",j) == "")
                        {
                            simple_cost_1  = "no";
                            var msg_txt = GridObj3.GetCellValue("part_name",j);
                            msg_txt = msg_txt + "의 설비Ton 값이 입력되지 않았습니다.";
                            alert(msg_txt);
                            break;
                        }
                    }
            }
    /******************************************************************************/
    } //for문 끝 괄호
}
/**********************************************
    간이산출
**********************************************/
function cost_acc_call()
{
    cost_input_chk(); //산출전 입력값 검증 call

    if (simple_cost_1 != "no"){
        var servlet_url = "/plm/servlet/e3ps/CostAccTestServlet";
        //WiseGrid가 서버에 전송할 Param을 셋팅한다.
        GridObj3.SetParam("mode", "cost_acc");
        GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj3.SetParam("rev_no", GridObj2.GetCellValue("rev_no",0));
        GridObj3.SetParam("table_row", GridObj2.getRowCount);
        GridObj3.AddGridRawData('WISEGRID_DATA3',document.WiseGrid3.GetGridRawData("WISEGRIDDATA_ALL"));

        //WiseGrid가 서버와 통신시에 데이터를 전달한다.
        GridObj3.DoQuery(servlet_url,"WISEGRIDDATA_ALL");
    }

}
function getdatetime()
{
    var today = new Date();
    var year = today.getYear();
    var month = today.getMonth() + 1;
    var day = today.getDate();

    if(month < 10)
        month = "0" + month;

    if(day < 10)
        day = "0" + day;
    var myDate = year + "" + month + "" + day;
    return myDate;
}

/**********************************************
결재요청-개발자
**********************************************/
function  pass_call_1(group_value)
{
    var myDate = getdatetime();
    cost_input_chk(); //산출전 입력값 검증 call

/*
    if(GridObj1.GetCellValue("a_name",0) == null)
    {
        simple_cost_1  = "no";
        alert("영업담당자가 입력되지 않았습니다.");
    }
    if(GridObj1.GetCellValue("request_day",0) < myDate)
    {
        simple_cost_1  = "no";
        alert("원가요청일이 현재일보다 과거로 되어있습니다.\n 변경후 요청바랍니다.");
    }else if(GridObj1.GetCellValue("request_day",0) == myDate)
    {
        simple_cost_1  = "no";
        alert("원가요청일은 적어도 현재일보다 1일이상의 여유가 있어야합니다.\n\n 변경후 요청바랍니다.");
    }else if(GridObj3.GetCellHiddenValue("pro_type",0) == "empty" )
    {
        simple_cost_1  = "no";
        alert("요청서작성후 결재요청바랍니다.");
    }
*/
     if (simple_cost_1 != "no"){
         var team = GridObj1.getCellHiddenValue ("team",0);
         var f_name_value = '<%=f_name%>';
         f_name_value = escape(encodeURIComponent(f_name_value));
         var url = "";

         if(group_value == 'groupJang'){
             url = "/plm/jsp/cost/costdb/group_select.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&f_name="+f_name_value+"&team="+team+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&team="+'<%=dept_no%>';
             window.open(url, "window_2", "width=580,height=300,scrollbars=no");
         }else{
             url = "/plm/jsp/cost/costdb/group_approval.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&f_name="+f_name_value+"&team="+team+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>';
             window.open(url, "window_2", "width=380,height=150,scrollbars=no");
         }
         
    }
 }

 /**********************************************
 결재-팀장
**********************************************/
  function  pass_call_3()
 {
     var team = GridObj1.getCellHiddenValue("team",0);
     var url = "/plm/jsp/cost/costdb/pass_login.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+team+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>';
     window.open(url, "window_2", "width=262,height=210,scrollbars=no");
  }

  function work_create(){
      var team = GridObj1.getCellHiddenValue("team",0);
      team = deptCodeChangeCP(team);
      if(confirm("이미 cost_work에 데이터가 있는지 확인하고 진행하세요")==true){
          var result_url = "/plm/jsp/cost/costdb/result_acc_admin.jsp?send_ok=ok&pk_cr_group="+'<%=pk_cr_group%>'+"&rev_no="+'<%=rev_no%>'+"&team="+team+"&group_case_count="+'<%=group_case_count%>';
          window.open(result_url, "result", "width=0, height=0, top=10000, left=1000");
          alert("cost_work 생성 완료!");
      }
  }
/**********************************************
 List Call
**********************************************/

function back_call()
{
    <%if(visitor.equals("4")){%>
        this.close();
    <%}else{%>
        window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';
    <%}%>
 }
/**********************************************
요청서 거부
**********************************************/
 function reject_pro()
 {
    var team = GridObj1.getCellHiddenValue ("team",0);
    var url = "/plm/jsp/cost/costdb/reject_pro_work.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+team+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>';

     window.open(url, "window_1","width=255,height=180,scrollbars=no,resizable=yes");
 }

/**********************************************
작성내용덮기
**********************************************/
 function  admin_call()
{
    var team = GridObj1.getCellHiddenValue ("team",0);
    var url = "/plm/jsp/cost/costdb/result_acc.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&step_no="+'<%=step_no%>'+"&team="+team+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&rev_no="+'<%=rev_no%>'+"&group_case_count="+'<%=group_case_count%>'+"&admin_pass=ok";
     window.open(url, "window_2", "width=262,height=210,scrollbars=no");
 }
/**********************************************
Main Call
**********************************************/
 function main_call()
{
    var group_case_count = document.part_1.group_case_count.value;
     window.location.href ="/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&rev_no="+'<%=rev_no%>'+"&data_type=main&group_case_count="+group_case_count;
 }
/**********************************************
Case0 Call
**********************************************/
 function case1_call()
{
    var group_case_count = document.part_1.group_case_count.value;
    window.location.href ="/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&rev_no="+'<%=rev_no%>'+"&data_type=case0&group_case_count="+group_case_count;
 }
/**********************************************
Case1 Call
**********************************************/
 function case2_call()
{
    var group_case_count = document.part_1.group_case_count.value;
    window.location.href ="/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&rev_no="+'<%=rev_no%>'+"&data_type=case1&group_case_count="+group_case_count;
 }

/**********************************************
담당자선택
**********************************************/
 function charge_sel()
{
    var url = "/plm/jsp/cost/costdb/charge_sel_work.jsp?pk_cr_group="+'<%=pk_cr_group%>'+"&rev_no="+'<%=rev_no%>'+"&step_no="+'<%=step_no%>'+"&visitor="+'<%=visitor%>'+"&select_name="+'<%=select_name%>'+"&group_case_count="+'<%=group_case_count%>';
     window.open(url, "window_1","width=255,height=100,scrollbars=no,resizable=yes");
 }
/**********************************************
펼쳐보기
**********************************************/
function seePlus(tb_id) {
    var tb_height = document.getElementById(tb_id).height;
    document.getElementById(tb_id).height = Number(tb_height) + 50;
}

/**********************************************
접기
**********************************************/
function seeMinus(tb_id) {
    var tb_height = document.getElementById(tb_id).height;
    document.getElementById(tb_id).height = 277;
}
</script>
<style type="text/css">
<!--

.style1 {font-size: 12px;text-align:center}

.style5 {font-size: 12px; background-color:#f2f2f2; text-align: center; font-weight: bold; color:#333333}
body {
    background-color: #FFFFFF;
    background-image: url(/plm/jsp/cost/acc_img/sub_top.jpg);
    background-repeat: no-repeat;
    margin-top: 0px;
}
.style2 {font-size: 12px; text-align:left; background-color:#ffffff;color:#F26522;}
#box {border:1 solid #ffffff ;padding:3 1 0 2;}

-->
</style>
</head>

<body onselectstart="return false">
<form name="part_1">
<input name="to_date" type="hidden" size="10" >
<input name="pk_cr_group" type="hidden" size="2" value=<%=pk_cr_group%>>
<input name="file_1_name" type="hidden" size="2" value=<%=file_1_name%>>
<input name="file_2_name" type="hidden" size="2" value=<%=file_2_name%>>
<input name="file_3_name" type="hidden" size="2" value=<%=file_3_name%>>
<input name="rev_no" type="hidden" size="2" value=<%=rev_no%>>
<input name="data_type" type="hidden" size="2" value=<%=data_type%>>
<input name="group_case_count" type="hidden" size="2" value=<%=group_case_count%>>
<table width="1226" height="102" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="1000" rowspan="2" height="102" valign="bottom"><img src="/plm/jsp/cost/acc_img/sub_top_trans.gif" width="1000" height="1"></td>
        <td width="183" height="28" align="right" valign="middle"><img src="/plm/jsp/cost/acc_img/btn_back.gif" border="0" width="57" height="15" onClick="back_call();" style="cursor:pointer;"/></td>
    </tr>
    <tr>
        <td>
            <table width="223" height="74" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc" >
                <tr>
                    <td width="38"rowspan="3" bgcolor="#f2f2f2" align="center"><b>결재</b></td>
                    <td width="71" height="27" bgcolor="#f2f2f2"  align="center" class="style1">담당 </td>
                    <td width="71" bgcolor="#f2f2f2"  align="center" class="style1">그룹장</td>
                    <td width="71" bgcolor="#f2f2f2"  align="center" class="style1">팀장</td>
                </tr>
                <tr>
                    <td height="44" bgcolor="#FFFFFF" align="center">
                        <%if ((f_name.equals(Ename) || login_id.equals("admin"))&&(step_no.equals("0") || step_no.equals("7") || step_no.equals("9")) && !visitor.equals("4")){%>
                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" width="50" height="16" onClick="pass_call_1('groupJang');" style="cursor:pointer;"/>
                        <%
                        }else{
                            out.println(f_name+"<br>"+f_day);
                        }%>
                    </td>
                    <td bgcolor="#FFFFFF" align="center">
                        <%if(step_no.equals("0.5") && approval.equals("ok") && (position.trim().equals("그룹장") || login_id.equals("admin"))) {%>
                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_1.gif" border="0" width="50" height="16" onClick="pass_call_1();" style="cursor:pointer;"/>
                        <%
                        }else if((step_no.equals("7") || step_no.equals("0") || step_no.equals("0.5")) && (!position.trim().equals("그룹장") && !position.trim().equals("팀장") &&  !login_id.equals("admin"))) {
                            out.println("");
                        }else{
                            out.println(gr_name+"<br>"+gr_day);
                        }%>
                    </td>
                    <td bgcolor="#FFFFFF" align="center">
                        <%if(step_no.equals("1") && approval.equals("ok") && (position.trim().equals("센타장") || position.trim().equals("팀장") || login_id.equals("admin"))) {%>
                        <img src="/plm/jsp/cost/acc_img/btn_pass_call_3.gif" border="0" width="66" height="16" onClick="pass_call_3();" style="cursor:pointer;"/>
                        <%
                        }else if((step_no.equals("7") || step_no.equals("0") || step_no.equals("0.5") || step_no.equals("1")) && (!position.trim().equals("팀장") || !login_id.equals("admin"))) {
                            out.println("");
                        }else{
                            out.println(Leader_name+"<br>"+Leader_day);
                        }%>
                    </td>
              </tr>
            </table>
        </td>
    </tr>
</table>
<br><br>
<table width="1226" height="635" border="0" cellpadding="0" cellspacing="0">
 <tr>
  <td>
    <table width="1224" height="77" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td height="20" align="right"  valign="bottom" ><%if(login_id.equals("admin") || dept_no.equals("11731") || dept_no.equals("11651")){%>
              <img src="/plm/jsp/cost/acc_img/pass.jpg" border="0"  onClick="admin_call();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_reject_pro.gif" border="0" width="77" height="17" onClick="reject_pro();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_work_se.gif" border="0" onClick="charge_sel();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_excelDown.gif" width="65" height="17" border="0" onClick="excelDown();" style="cursor:pointer;"/>
            <%}%><img src="/plm/jsp/cost/acc_img/btn_Edit.gif" width="51" height="17" border="0" onClick="doEdit();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_cost_acc_call.gif" width="77" height="17" border="0" onClick="cost_acc_call();" style="cursor:pointer;"/>
            <%if(login_id.equals("admin")){ %><input type="BUTTON" onclick="work_create();" value="관리자 산출작업(data 생성 주의)"><%} %>
            </td>
        </tr>
        <tr>
            <td bgcolor="#00455d" height="3" ></td>
        </tr>
        <tr>
            <td height="58" align="left" valign="top" ><script>initWiseGrid("WiseGrid1", "1224", "58");</script></td>
        </tr>
    </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td><table width="1224" height="173" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td width="1133" height="20" align="left" valign="bottom"><img src="/plm/jsp/cost/acc_img/tap_1_<%=img_txt%>.gif" border="0" usemap="#Map"><img src="/plm/jsp/cost/acc_img/tap_case_<%=img_txt_1%>.gif" border="1" usemap="#Map2" id="img_case1" style="visibility:<%=img_case1%>;"><img src="/plm/jsp/cost/acc_img/tap_case_pink_<%=img_txt_2%>.gif" border="0" usemap="#Map3" id="img_case2" style="visibility:<%=img_case2%>;"></td>
                <td width="91" align="right" valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Delete.gif" width="51" height="17" border="0" onClick="Delete_call();" style="cursor:pointer;"/></td>
            </tr>
            <tr>
                <td height="3" colspan="2" bgcolor="#00455d" ></td>
            </tr>
            <tr>
              <td height="150" colspan="2" align="left" valign="top" ><script>initWiseGrid("WiseGrid2", "1224", "150");</script></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td><table id="tb_product" width="1224" height="277" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td  height="20" valign="bottom" align="left"><img src="/plm/jsp/cost/acc_img/tap_2_<%=img_txt%>.gif" border="0" usemap="#Map4"><img src="/plm/jsp/cost/acc_img/tap_case_<%=img_txt_1%>.gif" border="0" usemap="#Map5" id="img_case1_1" style="visibility:<%=img_case1%>;"><img src="/plm/jsp/cost/acc_img/tap_case_pink_<%=img_txt_2%>.gif" border="0" usemap="#Map6" id="img_case2_1" style="visibility:<%=img_case2%>;"></td>
                <td height="20" align="right"  valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_plus.jpg" border="0" onClick="seePlus('tb_product');" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_minus.jpg" border="0" onClick="seeMinus('tb_product');" style="cursor:pointer;"/></td>
            </tr>
            <tr>
                <td bgcolor="#00455d" height="3" colspan="2"></td>
            </tr>
            <tr>
              <td align="left" valign="top" colspan="2"><script>initWiseGrid("WiseGrid3", "1224", "100%");</script></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td valign="top"><table width="1224" height="20" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td width="658" height="20" valign="bottom" ><img src="/plm/jsp/cost/acc_img/tap_3.gif"></td>
                <td width="566" height="20" valign="bottom" ><img src="/plm/jsp/cost/acc_img/tap_4.gif"></td>
              </tr>
        </table>
        <table width="1224" height="88" border="0" cellpadding="0" cellspacing="0" >
          <tr>
            <td bgcolor="#00455d" height="3"></td>
          </tr>
          <tr>
            <td align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
                <tr>
                  <td width="150" rowspan="3" bgcolor="F2F2F2" align="center">비고 및 특기사항 </td>
                  <td width="480" rowspan="3" bgcolor="#FFFFFF" ><textarea name="etc_note" cols="70" rows="5" ><%=etc_note%></textarea></td>
                  <td width="130" rowspan="3" bgcolor="F2F2F2" align="center">첨부파일</td>
                  <td width="450" bgcolor="#FFFFFF" ><input name="file_1" type="text" class="style2" size="68"  id="box" onClick="file_call1();" readonly></td>
                </tr>
                <tr>
                  <td bgcolor="#FFFFFF" ><input name="file_2" type="text" class="style2" size="68"  id="box" onClick="file_call2();" readonly> </td>
                </tr>
                <tr>
                  <td height="21" bgcolor="#FFFFFF" ><input name="file_3" type="text" class="style2" size="68"  id="box" onClick="file_call3();" readonly> </td>
                </tr>
            </table></td>
          </tr>
        </table>
    </td>
  </tr>
</table>
<br>
<!-- 등록, 수정, 삭제가 실행되면 발생한다.-->
<fieldset  id= 'result'>
<legend>간이산출 </legend>
<table width="1200" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="693"><script>initWiseGrid("WiseGrid5", "693", "250");</script></td>
        <td ></td>
    </tr>
</table>
</fieldset>
</form>
<map name="Map">
<area shape="rect" coords="9,3,115,16" href="#" onClick="main_call();">
</map>
<map name="Map2">
<area shape="rect" coords="5,4,38,15" href="#" onClick="case1_call();">
</map>
<map name="Map3">
<area shape="rect" coords="5,4,42,14" href="#" onClick="case2_call();">
</map>
<map name="Map4">
<area shape="rect" coords="4,3,93,16" href="#" onClick="main_call();">
</map>
<map name="Map5">
<area shape="rect" coords="4,4,43,14" href="#" onClick="case1_call();">
</map>
<map name="Map6"><area shape="rect" coords="4,3,42,13" href="#" onClick="case2_call();">
</map>
</body>
</html>