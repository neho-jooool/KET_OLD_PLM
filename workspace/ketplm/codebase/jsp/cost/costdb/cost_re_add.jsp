<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
    String etc_note    		= StringUtil.checkNull(request.getParameter("etc_note"));
    String pk_cr_group		= StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String rev_no				= StringUtil.checkNull(request.getParameter("rev_no"));
    String visitor 				= StringUtil.checkNull(request.getParameter("visitor"));
    String select_name 	= StringUtil.checkNull(request.getParameter("select_name"));
%>
<html>
<head>
<title>원가요청서</title>
<link rel='stylesheet' href='/plm/jsp/cost/css/wisegrid.css' type='text/css'>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGridTag.js"></script>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>

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

<!--  	서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WiseGrid1" event="EndQuery()">
    GridEndQuery1();
</script>
<script language=javascript for="WiseGrid2" event="EndQuery()">
    GridEndQuery2();
</script>
<script language=javascript for="WiseGrid3" event="EndQuery()">
    GridEndQuery3();
</script>

<!--  	WiseGrid의 셀을 클릭했을때 발생하는 Javacript Event인 CellClick()을 받아 해당하는 작업을 진행한다.  -->
<script language=javascript for="WiseGrid3" event="CellClick(strColumnKey,nRow)">
    GridCellClick3(strColumnKey, nRow)
</script>

<!--	 WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WiseGrid3" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell3(strColumnKey, nRow);
</script>
<!--  	WiseGrid의 셀을 더블클릭했을때 발생하는 Javacript Event인 CellClick()을 받아 해당하는 작업을 진행한다.  -->
<script language=javascript for="WiseGrid3" event="CellDblClick(strColumnKey,nRow)">
    GridCellDblClick3(strColumnKey, nRow)
</script>
<!--   	 WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WiseGrid3" event="ChangeCombo(strColumnKey,nRow,nOldIndex,nNewIndex)">
    GridChangeCombo3(strColumnKey, nRow);
</script>

<script language="JavaScript">
 var GridObj1;
 var GridObj2;
 var GridObj3;

function init1() {
    GridObj1 = document.WiseGrid1;
    setProperty(GridObj1);
    GridObj1.bHDMoving = false;
    GridObj1.bHDSwapping = false;
    GridObj1.bStatusbarVisible = false;
    GridObj1.nHDLines = 2;
    setHeader1();
    //doQuery1();
}

function init2() {
    GridObj2 = document.WiseGrid2;
    setProperty(GridObj2);
    GridObj2.bHDMoving = false;
    GridObj2.bHDSwapping = false;
    GridObj2.bStatusbarVisible = false;
    setHeader2();
    //doQuery2();
}

function init3() {
    GridObj3 = document.WiseGrid3;
    setProperty(GridObj3);
    GridObj3.bHDMoving = false;
    GridObj3.bHDSwapping = false;
    GridObj3.bStatusbarVisible = false;
    GridObj3.strRowSizing = "autofree";
    setHeader3();
    //doQuery3();
}


/*Row add*/
function GridCellClick3(strColumnKey, nRow)
{

    var GridObj3 = document.WiseGrid3;
    if(strColumnKey == "store")
    {
        var aaa = GridObj3.GetCellValue("store", nRow);
        //if(GridObj2.GetCellValue("store", nRow) != "")
            popUpOpen("/plm/jsp/cost/costdb/distri_cost.jsp?i="+nRow+"&store="+aaa, "prod_detail_pop", 470, 120);
    }
    /*
    if(strColumnKey == "g_sel1")	{
        doInsertRow1(strColumnKey, nRow);
    } else if(strColumnKey == "g_sel2") {
        doInsertRow2(strColumnKey, nRow);
    }
    */
}

/**********************************************
 그룹 넘버 생성
**********************************************/

function getRowCount(str, inx) { // T001- 와 같은 그룹넘버의 갯수를 리턴
    var GridObj3 = document.WiseGrid3;
    var cnt = 0;

    for(var ii=0; ii<GridObj3.getRowCount; ii++) {
        if(GridObj3.GetCellValue("group_no",ii).match(str) && str.length+3 == GridObj3.GetCellValue("group_no",ii).length) {
            cnt = cnt+1;
        }
    }
    return cnt+1;
}

function getGroup_no(nRow) {
    var GridObj3 = document.WiseGrid3;
    var inx = Math.max(5, GridObj3.GetCellValue("group_no", nRow).length);
    var str = GridObj3.GetCellValue("group_no", nRow).substring(0, inx);
    var con = getRowCount(str, inx);

    if(String(con).length == 1) {
        con = "0"+con;
    }
    str	= str + "-"+con;

    return str;
}

/**********************************************
 그룹 넘버 생성 끝
**********************************************/

function doInsertRow1(strColumnKey, nRow) {
    var GridObj3 = document.WiseGrid3;
    try {
        GridObj3.InsertRow(nRow+1);
        GridObj3.SetCellValue("group_no", nRow+1, getGroup_no(nRow));

    } catch(e) {
        GridObj3.AddRow();
        GridObj3.SetCellValue("group_no", GridObj3.GetActiveRowIndex(), getGroup_no(nRow));
    }
    GridObj3.SetCellImage('g_sel2', GridObj3.GetActiveRowIndex(), 0);

}

function doInsertRow2(strColumnKey, nRow) {
    var GridObj3 = document.WiseGrid3;

    try {
        GridObj3.InsertRow(nRow+1);
        GridObj3.SetCellValue("group_no", nRow+1, getGroup_no(nRow));
    } catch(e) {
            GridObj3.AddRow();
            GridObj3.SetCellValue("group_no", GridObj3.GetActiveRowIndex(), getGroup_no(nRow));
    }
    GridObj3.SetCellImage('g_sel3', GridObj3.GetActiveRowIndex(), 0);
}

/* Row Del - Active된 로우의 인덱스 위치의 행을 삭제한다. */
function doDelete3(strColumnKey, nRow) {
    if(strColumnKey != "SELECTED"){
        GridObj3.SetCellValue("SELECTED", GridObj3.GetActiveRowIndex(), "1");
    }
    GridObj3.DeleteRow(GridObj3.GetActiveRowIndex());
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
    GridObj1.AddHeader("dev_step",		"개발단계",		"t_combo",		 100,			100,		true);
    GridObj1.AddHeader("dr_step",		"DR",			"t_text",	 		 100,			 41,		false);
    GridObj1.AddHeader("sub_step",		"차수",			"t_combo",	 	 100,			 41,		false);
    GridObj1.AddHeader("Leader_day",	"작성일",			"t_text",			 100,			 80,		false);
    GridObj1.AddHeader("request_day",	"요청일",			"t_date",			 100,			 80,		true);
    GridObj1.AddHeader("request_txt",		"요청목적",		"t_text",			2000,		290,		true);
    GridObj1.AddHeader("etc_note",		"비고",			"t_text",			2000,		290,		false);
    GridObj1.AddHeader("file_1",			"file_1",			"t_text",			2000,		290,		false);
    GridObj1.AddHeader("file_2",			"file_2",			"t_text",			2000,		290,		false);
    GridObj1.AddHeader("file_3",			"file_3",			"t_text",			2000,		290,		false);

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
    GridObj1.AddComboListValue("dev_step", "", "empty");
    GridObj1.AddComboListValue("dev_step", "개발검토", "개발검토");
    GridObj1.AddComboListValue("dev_step", "개발착수/진행중", "개발착수");
    GridObj1.AddComboListValue("dev_step", "개발완료", "개발완료");
    GridObj1.AddComboListValue("dev_step", "설계변경", "설계변경");
    GridObj1.AddComboListValue("dev_step", "기타", "기타");

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
         GridObj1.SetColCellAlign('pjt_no',			'center');
        GridObj1.SetColCellAlign('pjt_name',		'left');
        GridObj1.SetColCellAlign('team',			'center');
        GridObj1.SetColCellAlign('f_name',			'center');
        GridObj1.SetColCellAlign('a_name',		'center');
        GridObj1.SetColCellAlign('dev_step',		'center');
        GridObj1.SetColCellAlign('dr_step',		'center');
        GridObj1.SetColCellAlign('sub_step',		'center');
        GridObj1.SetColCellAlign('Leader_day',		'center');
        GridObj1.SetColCellAlign('request_day',	'center');
        GridObj1.SetColCellAlign('request_txt',		'left');
        GridObj1.SetColCellAlign('etc_note',		'center');
        GridObj1.SetColCellAlign('file_1',			'center');
        GridObj1.SetColCellAlign('file_2',			'center');
        GridObj1.SetColCellAlign('file_3',			'center');

    //컬럼 숨기기
    GridObj1.SetColHide("pk_cr_group", true);
    GridObj1.SetColHide("etc_note", true);
    GridObj1.SetColHide("file_1", true);
    GridObj1.SetColHide("file_2", true);
    GridObj1.SetColHide("file_3", true);

}
function setHeader2() {
    //그리드에 컬럼을 등록한다.
    GridObj2.AddHeader("pk_cr",				"pk_cr",				"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("pk_cr_group",		"pk_cr_group",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("partSqe",			"partSqe",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("group_no",			"그룹",				"t_text",			 20,			 40,		false);
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
    GridObj2.AddHeader("customer_F",		"1차",				"t_text",			100,			 50,		false);
    GridObj2.AddHeader("customer_L",		"최종",				"t_text",			100,			 50,		false);
    GridObj2.AddHeader("client_cost",			"고객사\n인정예상가",	"t_text",			100,			 90,		false);
    GridObj2.AddHeader("ket_cost",			"판매목표가",			"t_text",			100,			 85,		false);
    GridObj2.AddHeader("target_cost",			"목표\n수익률(%)",	"t_text",			100,			 85,		false);
    GridObj2.AddHeader("rev_pk",			"rev_pk",				"t_text",			 500,			290,		false);
    GridObj2.AddHeader("rev_no",			"rev_no",				"t_text",			 500,			290,		false);

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
    GridObj2.SetColCellAlign('pk_cr',			'center');
    GridObj2.SetColCellAlign('pk_cr_group',     	'center');
    GridObj2.SetColCellAlign('group_no',		'left');
    GridObj2.SetColCellAlign('part_name',		'left');
    GridObj2.SetColCellAlign('part_no',	  	'center');
    GridObj2.SetColCellAlign('car_type',		'center');
    GridObj2.SetColCellAlign('customer_F',	    	'center');
    GridObj2.SetColCellAlign('customer_L',	    	'center');
    GridObj2.SetColCellAlign('client_cost', 		'right');
    GridObj2.SetColCellAlign('ket_cost',	       'right');
    GridObj2.SetColCellAlign('target_cost',		'right');
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
    GridObj2.SetColHide("pk_cr", true);
    GridObj2.SetColHide("pk_cr_group", true);
    GridObj2.SetColHide("partSqe", true);
    GridObj2.SetColHide("rev_pk", true);
    GridObj2.SetColHide("rev_no", true);

}
function setHeader3() {
    //그리드에 컬럼을 등록한다.

    GridObj3.AddHeader("SELECTED",		"",							"t_checkbox", 		  2, 		 30,	true);
    GridObj3.AddHeader("CRUD",				"",							"t_text", 			  8, 		 40,	true);
    GridObj3.AddHeader("SEQ_NO",			"SEQ_NO", 					"t_text", 			  8,		 85,	true);
    GridObj3.AddHeader("case_count_1",		"case_count_1", 				"t_text", 			  50,		 40,	true);
    GridObj3.AddHeader("case_count_2",		"case_count_2", 				"t_text", 			  50,		 40,	true);
    GridObj3.AddHeader("pk_cr",				"pk_cr",						"t_text",			 50,		 10,	true);
    GridObj3.AddHeader("pk_cr_group",		"pk_cr_group",					"t_text",			 50,		 10,	true);
    GridObj3.AddHeader("make",				"make",						"t_text",			 50,		 10,	true);
    GridObj3.AddHeader("group_no",			"그룹",						"t_text",			 20,		 40,	true);
    GridObj3.AddHeader("pro_type",			"Type",						"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("g_sel1",			"1",							"t_imagetext",		 40,		 20,	true);
    GridObj3.AddHeader("g_sel2",			"2",							"t_imagetext",	 	 40,		 20,	true);
    GridObj3.AddHeader("g_sel3",			"3",							"t_imagetext",		 40,		 20,	true);
    GridObj3.AddHeader("part_name",			"품명",						"t_text",			100,		200,	false);
    GridObj3.AddHeader("part_type",			"제작구분",					"t_combo",		100,		 60,	true);
    //GridObj3.AddHeader("Fam_group",		"Family\n금형",				"t_combo",		100,		 60,	true);
    //GridObj3.AddHeader("sel_group",		"개조금형",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("mix_group",			"복합금형",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("part_no",			"품번",						"t_text",			100,		 80,	true);
    GridObj3.AddHeader("net_1",				"NetSize\n장측",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("net_2",				"NetSize\n단측",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("net_3",				"NetSize\n깊이",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("usage",			"조립\nU/S",					"t_text",			100,		 40,	true);
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
    GridObj3.AddHeader("die_no",			"Die No",						"t_text",			100,		 80,	true);
    GridObj3.AddHeader("cavity",				"C/V",						"t_text",			100,		 50,	true);
    GridObj3.AddHeader("m_su",				"금형\n벌수",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("mold_cost",			"금형비\n(천원)",				"t_number",		10.3,		 80,	true);
    GridObj3.AddHeader("mold_c_type",		"감가\n조건",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("make_type",			"생산처1",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("pro_1",				"생산처2",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("ton",				"설비\nTon",					"t_text",			100,		 50,	true);
    GridObj3.AddHeader("spm",				"C/T\n(SPM)",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("method_type",		"공법"		,				"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("pro_level",			"작업\n인원",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("pro_level_txt",		"작업\n인원",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("line_su",			"조립\n라인",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("sul_cost",			"설비비\n(천원)",				"t_number",		10.3,		 80,	true);
    GridObj3.AddHeader("plating_type",		"구분1",						"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("type_2",			"구분2",						"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("plating_cost",		"￦/Kg\n￦/EA",				"t_text",			100,		 80,	true);
    GridObj3.AddHeader("type_1",			"구분"		,				"t_combo",		100,		 60,	true);
      GridObj3.AddHeader("t_mone",			"단위\n(Sec)",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("type_cost",			"단가\n(시간/EA)",			"t_number",		10.3,		 80,	true);
    GridObj3.AddHeader("t_order",			"발주\n조건",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("pack_type",			"포장유형",					"t_text",		100,		 80,	true);
    GridObj3.AddHeader("in_pack",			"내장\n(EA/봉지)",			"t_number",		10.3,		 63,	true);
    GridObj3.AddHeader("out_pack",			"외장\n(EA/Box)",				"t_number",		10.3,		 60,	true);
      GridObj3.AddHeader("store",				"보관",						"t_text",			100,		 85,	false);
      GridObj3.AddHeader("dest",				"납입처",						"t_text",			100,		 85,	false);
    GridObj3.AddHeader("dis_cost",			"물류비",						"t_number",		10.3,		 50,	true);
    GridObj3.AddHeader("distri_cost",			"물류\n흐름비",				"t_text",			100,		 50,	true);
      GridObj3.AddHeader("royalty",			"로얄티\n유무",				"t_combo",		100,		 50,	true);

    /* yazaki 투자비 제거 - 20101223 이성수J // 필드 yazaki_ro → 포장용투자비로대체

    GridObj3.AddHeader("yzk_mone",			"단위",						"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("yazaki_ro",			"천원/천엔",					"t_text",			100,		 80,	true);

    */

    GridObj3.AddHeader("etc_cost",			"기타\n투자비\n(천원)",		"t_number",		10.3,		 80,	true);
    GridObj3.AddHeader("yazaki_ro",			"포장용\n투자비\n(천원)",		"t_number",		10.3,		 80,	true);
    GridObj3.AddHeader("part_note",			"그외 특기사항",				"t_text",		     2000,		300,	true);


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
      GridObj3.AppendHeader("MAKE_TOTAL", "make_type");
      GridObj3.AppendHeader("MAKE_TOTAL", "pro_1");
      GridObj3.AppendHeader("MAKE_TOTAL", "ton");
      GridObj3.AppendHeader("MAKE_TOTAL", "spm");
      GridObj3.AppendHeader("MAKE_TOTAL", "method_type");
      GridObj3.AppendHeader("MAKE_TOTAL", "pro_level");
      GridObj3.AppendHeader("MAKE_TOTAL", "pro_level_txt");
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

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj3.BoundHeader();

    //저장모드를 사용해 서버사이드와 통신한다.
    GridObj3.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

    //이미지리스트에 이미지 URL을 추가한다.
    GridObj3.AddImageList("g_sel1", "/plm/jsp/cost/acc_img/add_red.jpg");
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
    GridObj3.AddComboListValue("part_type",	"공용"    	,	"공용");
    GridObj3.AddComboListValue("part_type",	"개조"    	,	"개조");
    GridObj3.AddComboListValue("part_type",	"수정"    	,	"수정");
    GridObj3.AddComboListValue("part_type",	"복합금형"	,	"복합금형");
    GridObj3.AddComboListValue("part_type",	"Family"  	,	"Family");

    //복합금형List
    GridObj3.AddComboListValue("mix_group",	""   		,	"empty"   );
    GridObj3.AddComboListValue("mix_group",	"선생산"  	,	"선생산"  );
    GridObj3.AddComboListValue("mix_group",	"후생산"  	,	"후생산"  );
    GridObj3.AddComboListValue("mix_group",	"동시생산"	,	"동시생산");
    //감가조건
    GridObj3.AddComboListValue("mold_c_type",	"감가"	,	"감가");
    GridObj3.AddComboListValue("mold_c_type",	"지급"  ,	"지급");

    //생산처1
    GridObj3.AddComboListValue("make_type",	""			,	"empty"		);
    GridObj3.AddComboListValue("make_type",	"사내"		,	"사내"		);
    GridObj3.AddComboListValue("make_type",	"외주"    	,	"외주"		);
    GridObj3.AddComboListValue("make_type",	"구매"    	,	"구매"		);
    GridObj3.AddComboListValue("make_type",	"OEM"     	,	"OEM" 	  );

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
    GridObj3.AddComboListValue("method_type",	""  	,	"empty"  );
    GridObj3.AddComboListValue("method_type",	"단순"   	,	"단순"   );
    GridObj3.AddComboListValue("method_type",	"JIG이용"	,	"JIG이용");
    GridObj3.AddComboListValue("method_type",	"자동"   	,	"자동"   );
    GridObj3.AddComboListValue("method_type",	"수동"   	,	"수동"   );
    GridObj3.AddComboListValue("method_type",	"Box"    	,	"Box"    );
    GridObj3.AddComboListValue("method_type",	"Insert" 	,	"Insert" );

    //작업인원
    GridObj3.AddComboListValue("pro_level",	"표준"			,	"표준");
    GridObj3.AddComboListValue("pro_level",	"기타"			,	"기타");

    //도금,후처리
    GridObj3.AddComboListValue("plating_type",	"" 		,	"empty" 	);
    GridObj3.AddComboListValue("plating_type",	"재료도금"	,	"재료도금");
    GridObj3.AddComboListValue("plating_type",	"후도금" 	,	"후도금" 	);
    GridObj3.AddComboListValue("plating_type",	"리와인딩"	,	"리와인딩");
    GridObj3.AddComboListValue("plating_type",	"열처리" 	,	"열처리" 	);
    GridObj3.AddComboListValue("plating_type",	"세척" 		,	"세척" 		);
    GridObj3.AddComboListValue("plating_type",	"변색방지" 	,	"변색방지" 	);
    GridObj3.AddComboListValue("plating_type",	"기타" 		,	"기타" 		);

    //도금사양
    GridObj3.AddComboListValue("type_2",	""  ,	"empty");
    GridObj3.AddComboListValue("type_2",	"Sn" 	 ,	"Sn" 	 );
    GridObj3.AddComboListValue("type_2",	"Au" 	 ,	"Au" 	 );
    GridObj3.AddComboListValue("type_2",	"Ag" 	 ,	"Ag" 	 );
    GridObj3.AddComboListValue("type_2",	"3가Cr"  ,	"3가Cr");
    GridObj3.AddComboListValue("type_2",	"기타" 	 ,	"기타" );

    //외주,구매...
    GridObj3.AddComboListValue("type_1",	""  ,	"empty" );
    GridObj3.AddComboListValue("type_1",	"임가공" ,	"임가공");
    GridObj3.AddComboListValue("type_1",	"구매"   ,	"구매"  );
    GridObj3.AddComboListValue("type_1",	"OEM"    ,	"OEM"   );
    GridObj3.AddComboListValue("type_1",	"검사"   ,	"검사"  );
    GridObj3.AddComboListValue("type_1",	"포장"   ,	"포장"  );
    GridObj3.AddComboListValue("type_1",	"기타"   ,	"기타"  );

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
/*     GridObj3.AddComboListValue("pack_type",	"" 	 ,	"empty" );
    GridObj3.AddComboListValue("pack_type",	"비닐" 		 ,	"비닐" 	);
    GridObj3.AddComboListValue("pack_type",	"Reel" 		 ,	"Reel" 	);
    GridObj3.AddComboListValue("pack_type",	"회수용"	 ,	"회수용");
    GridObj3.AddComboListValue("pack_type",	"Tray"  	 ,	"Tray"  );
    GridObj3.AddComboListValue("pack_type",	"골판지"	 ,	"골판지");
 */
  //로얄티유무
      GridObj3.AddComboListValue("royalty", "有", "1");
      GridObj3.AddComboListValue("royalty", "無", "2");

    /*
    //YZK기술료단위
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
    GridObj3.SetNumberFormat("mold_cost", 	"0,###.###");
    GridObj3.SetNumberFormat("sul_cost", 		"0,###.###");
    GridObj3.SetNumberFormat("type_cost", 	"0,###.###");
    GridObj3.SetNumberFormat("dis_cost", 		"0,###.###");
    GridObj3.SetNumberFormat("etc_cost", 		"0,###.###");
    GridObj3.SetNumberFormat("yazaki_ro", 	"0,###.###");
        GridObj3.SetNumberFormat("in_pack",		"0,###.###");
        GridObj3.SetNumberFormat("out_pack",		"0,###.###");

    //컬럼 숨기기
      GridObj3.SetColHide("SEQ_NO", true);
      GridObj3.SetColHide("case_count_1",true);
      GridObj3.SetColHide("case_count_2",true);
      GridObj3.SetColHide("pk_cr", true);
      GridObj3.SetColHide("pk_cr_group", true);
      GridObj3.SetColHide("make", true);
      GridObj3.SetColHide("net_1", true);
      GridObj3.SetColHide("net_2", true);
      GridObj3.SetColHide("net_3", true);
 // GridObj3.SetColHide("Fam_group", true);
 // GridObj3.SetColHide("sel_group", true);
      GridObj3.SetColHide("mix_group", false);
      GridObj3.SetColHide("unitcost", true);
      GridObj3.SetColHide("pro_level_txt", true);
      GridObj3.SetColHide("unitcost", true);
      GridObj3.SetColHide("grade_name", true);
      GridObj3.SetColHide("grade_color", true);
      GridObj3.SetColHide("distri_cost", true);

      /****************************************************************
    Header 정렬 비활성
*****************************************************************/
    GridObj3.SetColCellSortEnable("SELECTED",  "false");
    GridObj3.SetColCellSortEnable("CRUD",  "false");
    GridObj3.SetColCellSortEnable("SEQ_NO",  "false");
    GridObj3.SetColCellSortEnable("case_count_1",  "false");
    GridObj3.SetColCellSortEnable("case_count_2",  "false");
    GridObj3.SetColCellSortEnable("pk_cr",  "false");
    GridObj3.SetColCellSortEnable("pk_cr_group",  "false");
    GridObj3.SetColCellSortEnable("make",  "false");
    GridObj3.SetColCellSortEnable("group_no",  "false");
    GridObj3.SetColCellSortEnable("pro_type",  "false");
    GridObj3.SetColCellSortEnable("g_sel1",  "false");
    GridObj3.SetColCellSortEnable("g_sel2",  "false");
    GridObj3.SetColCellSortEnable("g_sel3",  "false");
    GridObj3.SetColCellSortEnable("part_name",  "false");
    GridObj3.SetColCellSortEnable("part_type",  "false");
    //GridObj3.SetColCellSortEnable("Fam_group",  "false");
    //GridObj3.SetColCellSortEnable("sel_group",  "false");
    GridObj3.SetColCellSortEnable("mix_group",  "false");
    GridObj3.SetColCellSortEnable("part_no",  "false");
    GridObj3.SetColCellSortEnable("net_1",  "false");
    GridObj3.SetColCellSortEnable("net_2",  "false");
    GridObj3.SetColCellSortEnable("net_3",  "false");
    GridObj3.SetColCellSortEnable("usage",  "false");
    GridObj3.SetColCellSortEnable("meterial",  "false");
    GridObj3.SetColCellSortEnable("t_size",  "false");
    GridObj3.SetColCellSortEnable("w_size",  "false");
    GridObj3.SetColCellSortEnable("p_size",  "false");
    GridObj3.SetColCellSortEnable("plating",  "false");
    GridObj3.SetColCellSortEnable("m_maker",  "false");
    GridObj3.SetColCellSortEnable("m_mone",  "false");
    GridObj3.SetColCellSortEnable("unitcost",  "false");
    GridObj3.SetColCellSortEnable("unitcost_txt",  "false");
    GridObj3.SetColCellSortEnable("grade_name",  "false");
    GridObj3.SetColCellSortEnable("grade_color",  "false");
    GridObj3.SetColCellSortEnable("order_con",  "false");
    GridObj3.SetColCellSortEnable("h_weight",  "false");
    GridObj3.SetColCellSortEnable("h_scrap",  "false");
    GridObj3.SetColCellSortEnable("t_weight",  "false");
    GridObj3.SetColCellSortEnable("recycle",  "false");
    GridObj3.SetColCellSortEnable("die_no",  "false");
    GridObj3.SetColCellSortEnable("cavity",  "false");
    GridObj3.SetColCellSortEnable("m_su",  "false");
    GridObj3.SetColCellSortEnable("mold_cost",  "false");
    GridObj3.SetColCellSortEnable("mold_c_type",  "false");
    GridObj3.SetColCellSortEnable("make_type",  "false");
    GridObj3.SetColCellSortEnable("pro_1",  "false");
    GridObj3.SetColCellSortEnable("ton",  "false");
    GridObj3.SetColCellSortEnable("spm",  "false");
    GridObj3.SetColCellSortEnable("method_type",  "false");
    GridObj3.SetColCellSortEnable("pro_level",  "false");
    GridObj3.SetColCellSortEnable("pro_level_txt",  "false");
    GridObj3.SetColCellSortEnable("line_su",  "false");
    GridObj3.SetColCellSortEnable("sul_cost",  "false");
    GridObj3.SetColCellSortEnable("plating_type",  "false");
    GridObj3.SetColCellSortEnable("type_2",  "false");
    GridObj3.SetColCellSortEnable("plating_cost",  "false");
    GridObj3.SetColCellSortEnable("type_1",  "false");
    GridObj3.SetColCellSortEnable("t_mone",  "false");
    GridObj3.SetColCellSortEnable("type_cost",  "false");
    GridObj3.SetColCellSortEnable("t_order",  "false");
    //GridObj3.SetColCellSortEnable("pack_type",  "false");
    GridObj3.SetColCellSortEnable("in_pack",  "false");
    GridObj3.SetColCellSortEnable("out_pack",  "false");
    GridObj3.SetColCellSortEnable("store",  "false");
    GridObj3.SetColCellSortEnable("dest",  "false");
    GridObj3.SetColCellSortEnable("dis_cost",  "false");
    GridObj3.SetColCellSortEnable("distri_cost",  "false");
    GridObj3.SetColCellSortEnable("royalty",  "false");
    //GridObj3.SetColCellSortEnable("yzk_mone",  "false");
    GridObj3.SetColCellSortEnable("etc_cost",  "false");
    GridObj3.SetColCellSortEnable("yazaki_ro",  "false");
    GridObj3.SetColCellSortEnable("part_note",  "false");

    //화면분할박스비활성
      GridObj3.bSplitBoxVisible = false;

    GridObj3.SetColCellMultiLine("part_note", true);
}
/********************************************************************
 저장 2
********************************************************************/
function doSave2() {
    var servlet_url = "./cost_re_add_db.asp";

    //WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj2.SetParam("mode", "save2");
    GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj2.SetParam("pjt_no", GridObj1.GetCellValue("pjt_no", 0) );
    GridObj2.SetParam("table_row", GridObj2.getRowCount);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다
    GridObj2.DoQuery(servlet_url, "WISEGRIDDATA_ALL");
}

/********************************************************************
 저장 3
********************************************************************/
function doSave3() {

    var data_con = "";
    if(GridObj1.GetCellHiddenValue("dev_step",0) == "empty" ){
        data_con  = "no";
        alert("개발단계를 입력해주시기 바랍니다.");
        return;
    }
    var year_1 = 0;
    var year_2 = 0;
    var year_3 = 0;
    var year_4 = 0;
    var year_5 = 0;
    var year_6 = 0;
    var year_7 = 0;
    var year_8 = 0;
	var year_sum = 0;
    for(var i=0; i<GridObj2.GetRowCount(); i++){
    	year_1 = GridObj2.GetCellValue("su_year_1",i);
    	year_2 = GridObj2.GetCellValue("su_year_2",i);
    	year_3 = GridObj2.GetCellValue("su_year_3",i);
    	year_4 = GridObj2.GetCellValue("su_year_4",i);
    	year_5 = GridObj2.GetCellValue("su_year_5",i);
    	year_6 = GridObj2.GetCellValue("su_year_6",i);
    	year_7 = GridObj2.GetCellValue("su_year_7",i);
    	year_8 = GridObj2.GetCellValue("su_year_8",i);
    	year_sum = Number(year_1)+Number(year_2)+Number(year_3)+Number(year_4)+Number(year_5)+Number(year_6)+Number(year_7)+Number(year_8);
    	if(year_sum < 1){
    		data_con  = "no";
    		alert("기획수량이 입력되지 않았습니다. PLM에서 먼저 입력하세요.");
    		break;
    	}
    }
     if (data_con != "no"){

        var servlet_url = "/plm/servlet/e3ps/costComServlet";
        //WiseGrid가 서버에 전송할 mode를 셋팅한다.
        GridObj3.SetParam("mode", "cost_re_add_save");
        GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj3.SetParam("rev_no", GridObj2.GetCellValue("rev_no", 0) );
        GridObj3.SetParam("pjt_no", GridObj1.GetCellValue("pjt_no", 0) );
        GridObj3.SetParam("pjt_name", GridObj1.GetCellValue("pjt_name", 0) );
        GridObj3.SetParam("team", GridObj1.GetCellHiddenValue("team", 0) );
        GridObj3.SetParam("f_name", GridObj1.GetCellValue("f_name", 0) );
        GridObj3.SetParam("a_name", GridObj1.GetCellValue("a_name", 0) );
        GridObj3.SetParam("dev_step", GridObj1.GetCellHiddenValue("dev_step", 0) );
        GridObj3.SetParam("dr_step", GridObj1.GetCellValue("dr_step", 0) );
        GridObj3.SetParam("sub_step", GridObj1.GetCellHiddenValue("sub_step", 0) );
        GridObj3.SetParam("request_day", GridObj1.GetCellValue("request_day", 0) );
        GridObj3.SetParam("request_txt", GridObj1.GetCellValue("request_txt", 0) );
        GridObj3.SetParam("etc_note", document.part_1.etc_note.value);
        GridObj3.SetParam("table_row", GridObj2.getRowCount);
        GridObj3.SetParam("rowcount", GridObj3.getRowCount);

        GridObj2.SetParam("mode", "save2");
        GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj2.SetParam("pjt_no", GridObj1.GetCellValue("pjt_no", 0) );
        GridObj2.SetParam("table_row", GridObj2.getRowCount);
        GridObj3.AddGridRawData("COST_RE_ADD_GRID",GridObj2.GetGridRawData("WISEGRIDDATA_ALL"));
        //WiseGrid가 서버와 통신시에 데이터를 전달한다
        GridObj3.DoQuery(servlet_url, "WISEGRIDDATA_ALL");

    }
}


/********************************************************************
조회 1
********************************************************************/

function doQuery1() {
   var servlet_url = "/plm/servlet/e3ps/costComServlet";
   //WiseGrid가 서버에 전송할 Param을 셋팅한다.
   GridObj1.SetParam("mode"       , "getPlm_cost_viewSearch1");
   GridObj1.SetParam("pjt_no", document.part_1.pk_cr_group.value);
   //WiseGrid가 서버와 통신시에 데이터를 전달한다.
   GridObj1.DoQuery(servlet_url);
}

function doQuery2() {
   var servlet_url = "/plm/servlet/e3ps/costComServlet";
   //WiseGrid가 서버에 전송할 Param을 셋팅한다.
   GridObj2.SetParam("mode"       , "getPlm_cost_viewSearch2");
   GridObj2.SetParam("pjt_no", document.part_1.pk_cr_group.value);
   //WiseGrid가 서버와 통신시에 데이터를 전달한다.
   GridObj2.DoQuery(servlet_url);
}

function doQuery3() {
   var servlet_url = "/plm/servlet/e3ps/costComServlet";
   //WiseGrid가 서버에 전송할 Param을 셋팅한다.
   GridObj3.SetParam("mode"       , "getPlm_cost_viewSearch3");
   GridObj3.SetParam("pjt_no", document.part_1.pk_cr_group.value);
   //WiseGrid가 서버와 통신시에 데이터를 전달한다.
   GridObj3.DoQuery(servlet_url);
}
/********************************************************************
 저장모드에서 저장 플래그를 모두 삭제하고 초기 데이터로 롤백한다.
********************************************************************/

function doSave3Cancel() {
    if(confirm("지금까지의 모든 작업내용이 초기화됩니다"))
        GridObj3.CancelCRUD();
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
    GridObj3.ExcelExport("", "", true, true);
}

/*******************************************************************************************************
2. 제품생산 내역 List 셀의 내용이 변경되었을때 발생- 지정한 셀 SELECTED 에서 선택된 로우에 값을 넣는다.
********************************************************************************************************/
function GridChangeCell3(strColumnKey, nRow) {
    if(strColumnKey != "SELECTED")
        GridObj3.SetCellValue("SELECTED", nRow, "1");
}
/***************************************************
2. 제품생산 내역 List콤보의 내용이 변경되었을때 발생
***************************************************/
function GridChangeCombo3(strColumnKey, nRow) {
    if(strColumnKey == "pro_level"){

        if(GridObj3.GetCellValue("pro_level", nRow) == "기타")
        {
              GridObj3.SetColHide("pro_level_txt", false);
        }
        else
        {
              GridObj3.SetColHide("pro_level_txt", true);
        }
    }
        if(strColumnKey != "SELECTED"){
        GridObj3.SetCellValue("SELECTED", nRow, "1");
    }
}

 /**********************************************
 서버와의 통신이 정상적으로 완료되면 발생한다.
 **********************************************/

function GridEndQuery1() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.

    document.part_1.etc_note.value= GridObj1.GetCellValue("etc_note",0);

}
function GridEndQuery2() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj2.GetParam("mode");
    if(mode == "save2") {
        alert("저장되었습니다");
        var pk_cr_group_1 = GridObj2.GetCellValue("pk_cr_group",0);
        var rev_no_1 = GridObj2.GetCellValue("rev_no",0);
        window.location = "./cost_re_edit_test.jsp?data_type=main&group_case_count=0&pk_cr_group="+pk_cr_group_1+"&rev_no="+rev_no_1;
    }
}
function GridEndQuery3() {
	//서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj3.GetParam("mode");

    if(mode == "save3") {
        alert("저장되었습니다.");
        var pk_cr_group_1 = GridObj2.GetCellValue("pk_cr_group",0);
        var rev_no_1 = GridObj2.GetCellValue("rev_no",0);
        window.location = "./cost_re_edit_test.jsp?data_type=main&group_case_count=0&pk_cr_group="+pk_cr_group_1+"&rev_no="+rev_no_1;
    }
}

 /**********************************************
  등록된 파일보기
 **********************************************/
function file_call1()
{
        if (document.part_1.file_1_name.value =="" ){
            document.part_1.file_1_name.value = GridObj1.GetCellValue("file_1",0);
        }
        var file_1WholePath = document.part_1.file_1_name.value;
              window.open(file_1WholePath);
}
function file_call2()
{
        if (document.part_1.file_2_name.value =="" ){
            document.part_1.file_2_name.value = GridObj1.GetCellValue("file_2",0);
        }
        var file_2WholePath = document.part_1.file_2_name.value;
              window.open(file_2WholePath);
}
function file_call3()
{
        if (document.part_1.file_3_name.value =="" ){
            document.part_1.file_3_name.value = GridObj1.GetCellValue("file_3",0);
        }
        var file_3WholePath = document.part_1.file_3_name.value;
              window.open(file_3WholePath);
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
  검토(개발)의뢰서 검색
 **********************************************/
    function SEL_PLM()
    {
        window.open("/plm/jsp/cost/costdb/plmSearch.jsp", "plm_search_pop", "width=510,height=400,scrollbars=yes");
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

    document.part_1.to_date.value = year + "" + month + "" + day;
}

/**********************************************
 List Call
**********************************************/
function back_call()
{
    <%if(StringUtil.checkNull(visitor).equals("4")){%>
        this.close();
    <%}else{%>
        window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';

    <%}%>
}
/**********************************************
 더블클릭이벤트
**********************************************/
function GridCellDblClick3(strColumnKey, nRow)
{
    var GridObj3 = document.WiseGrid3;
    if(strColumnKey == "store")
    {
        var a_store = GridObj3.GetCellValue("store", nRow);
        var a_dest = GridObj3.GetCellValue("dest", nRow);

        //if(GridObj2.GetCellValue("store", nRow) != "")
            popUpOpen("./distri_cost.asp?page_name=edit&i="+nRow+"&store="+a_store+"&dest="+a_dest, "prod_detail_pop", 470, 120);
    }
    if(strColumnKey == "meterial")
    {
        var pro_type = GridObj3.GetCellHiddenValue("pro_type", nRow);
        var meterial = GridObj3.GetCellValue("meterial", nRow);
        var m_maker = GridObj3.GetCellValue("m_maker", nRow);
        var make = GridObj3.GetCellValue("make", nRow);
        var plating = GridObj3.GetCellValue("plating", nRow);
        var t_size = GridObj3.GetCellValue("t_size", nRow);
        var w_size = GridObj3.GetCellValue("w_size", nRow);
        var p_size = GridObj3.GetCellValue("p_size", nRow);
        var m_mone = GridObj3.GetCellValue("m_mone", nRow);
        var unitcost_txt = GridObj3.GetCellValue("unitcost_txt", nRow);
        var order_con = GridObj3.GetCellValue("order_con", nRow);
        var h_weight = GridObj3.GetCellValue("h_weight", nRow);
        var h_scrap = GridObj3.GetCellValue("h_scrap", nRow);
        var t_weight = GridObj3.GetCellValue("t_weight", nRow);
        var recycle = GridObj3.GetCellValue("recycle", nRow);
        var grade_name = GridObj3.GetCellValue("grade_name", nRow);
        var grade_color = GridObj3.GetCellValue("grade_color", nRow);
        var sul_cost = GridObj3.GetCellValue("sul_cost", nRow);
        var type_2 = GridObj3.GetCellValue("type_2", nRow);
        var plating_cost = GridObj3.GetCellValue("plating_cost", nRow);

        popUpOpen("/plm/jsp/cost/common/cost_grade.jsp?page_name=edit&k="+nRow+"&p_meterial="+meterial+"&make="+make+"&p_m_maker="+m_maker+"&p_plating="+plating+"&p_t_size="+t_size+"&p_w_size="+w_size+"&p_p_size="+p_size+"&p_m_mone="+m_mone+"&p_unitcost_txt="+unitcost_txt+"&p_order_con="+order_con+"&p_h_weight="+h_weight+"&p_h_scrap="+h_scrap+"&p_t_weight="+t_weight+"&p_recycle="+recycle+"&p_grade_name="+grade_name+"&p_grade_color="+grade_color+"&p_pro_type="+pro_type+"&p_sul_cost="+sul_cost+"&p_type_2="+type_2+"&p_plating_cost="+plating_cost, "cost_grade", 970, 220);
    }

}
</script>
<style type="text/css">
<!--
body {
    background-color: #FFFFFF;
    background-image: url(/plm/jsp/cost/acc_img/sub_top.jpg);
    background-repeat: no-repeat;
    margin-top: 0px;
}
-->
</style></head>
<body onLoad="SEL_PLM();" onselectstart="return false">
<form name="part_1">
<input name="pk_cr_group" type="hidden" size="2">
<input name="rev_no" type="hidden" size="2">
<input name="data_type" type="hidden" size="2">
<input name="file_1_name" type="hidden" size="2">
<input name="file_2_name" type="hidden" size="2">
<input name="file_3_name" type="hidden" size="2">
<table height="102" width="1226"border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td width="1043" rowspan="2" height="102" valign="bottom"><img src="/plm/jsp/cost/acc_img/sub_top_trans.gif" width="1043" height="1"></td>
        <td width="183" height="28" align="right" valign="middle"><img src="/plm/jsp/cost/acc_img/btn_back.gif" border="0" width="57" height="15" onClick="back_call();" style="cursor:pointer;"/></td>
    </tr>
    <tr>
        <td height="74"></td>
    </tr>
</table>
<br><br>
<table width="1226" height="691" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <table width="1224" height="77" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td valign="bottom" align="right" height="16"><img src="/plm/jsp/cost/acc_img/btn_SEL_PLM.gif" width="154" height="17" border="0" onClick="SEL_PLM();" style="cursor:pointer;"/><img src="/plm/jsp/cost/acc_img/btn_excelDown.gif" width="65" height="17" border="0" onClick="excelDown();" style="cursor:pointer;"/></td>
        </tr>
        <tr>
            <td bgcolor="#00455d" height="3"></td>
        </tr>
        <tr>
            <td height="58" align="left" valign="top"><script>initWiseGrid("WiseGrid1", "1224", "58");</script></td>
        </tr>
    </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td><table width="1224" height="183" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td height="20" valign="bottom" align="left"><img src="/plm/jsp/cost/acc_img/tap_1_view.gif" border="0" ></td>
            </tr>
            <tr>
                <td bgcolor="#00455d" height="3"></td>
            </tr>
            <tr>
              <td height="160" align="left" valign="top"><script>initWiseGrid("WiseGrid2", "1224", "160");</script></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td><table width="1224" height="323" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td  height="20" valign="bottom" align="left"><img src="/plm/jsp/cost/acc_img/tap_2_view.gif" border="0" ></td>
                <td height="20" align="right"  valign="bottom"><img src="/plm/jsp/cost/acc_img/btn_Save.gif" width="51" height="17" border="0" onClick="doSave3();" style="cursor:pointer;"/></td>
            </tr>
            <tr>
                <td bgcolor="#00455d" height="3" colspan="2"></td>
            </tr>
            <tr>
              <td height="300" colspan="2" align="left" valign="top"><script>initWiseGrid("WiseGrid3", "1224", "300");</script></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
      <td height="4"></td>
  </tr>
  <tr>
    <td valign="top">
        <table width="1224" height="20" border="0" cellpadding="0" cellspacing="0" >
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
                <td align="left" valign="top"><table width="1224" border="0" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
                      <tr>
                          <td width="150" rowspan="3" bgcolor="F2F2F2" align="center">비고 및 특기사항 </td>
                          <td width="480" rowspan="3" bgcolor="#FFFFFF" ><textarea name="etc_note" cols="70" rows="5" ><%=etc_note%></textarea></td>
                          <td width="130" rowspan="3" bgcolor="F2F2F2" align="center">첨부파일</td>
                          <td width="450" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1_b.gif" border="0" >
                        <input type="checkbox" name="file_edit_1" value="ON" checked disabled >
                          이전 파일 유지하기(<img src="/plm/jsp/cost/acc_img/file.png" border="0" onClick="file_call1();" style="cursor:pointer;"/>) </td>
                      </tr>
                      <tr>
                          <td bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1_b.gif" border="0" >
                        <input type="checkbox" name="file_edit_2" value="ON" checked disabled >
                          이전 파일 유지하기(<img src="/plm/jsp/cost/acc_img/file.png" border="0" onClick="file_call2();" style="cursor:pointer;"/>) </td>
                      </tr>
                      <tr>
                          <td height="21" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1_b.gif" border="0" >
                        <input type="checkbox" name="file_edit_3" value="ON" checked disabled >
                          이전 파일 유지하기(<img src="/plm/jsp/cost/acc_img/file.png" border="0" onClick="file_call3();" style="cursor:pointer;"/>) </td>
                      </tr>
        </table></td>
            </tr>
        </table>
    </td>
  </tr>
</table>
<br>
<!-- 등록, 수정, 삭제가 실행되면 발생한다.-->
<table width="1224" border="0" cellspacing="0" cellpadding="0">
<tr><td>
<fieldset  id= 'result'>
 <legend>간이산출 </legend>
</fieldset>
</td></tr>
</table>
</form>
<map name="Map"><area shape="rect" coords="8,3,114,16" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=main"><area shape="rect" coords="117,4,132,16" href="#"></map>

<map name="Map2"><area shape="rect" coords="5,3,38,14" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=case0">
<area shape="rect" coords="43,3,55,13" href="#"></map>
<map name="Map3"><area shape="rect" coords="5,3,42,13" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=case1"><area shape="rect" coords="44,3,56,14" href="#"></map>
<map name="Map4">
<area shape="rect" coords="3,3,92,16" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=main"><area shape="rect" coords="94,4,107,17" href="#">
</map>
<map name="Map5">
<area shape="rect" coords="3,4,42,14" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=case0">
<area shape="rect" coords="44,4,57,16" href="#"></map>
<map name="Map6"><area shape="rect" coords="4,3,42,13" href="./cost_re_edit_test.jsp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=case1"><area shape="rect" coords="45,3,56,13" href="#"></map></body>
</html>