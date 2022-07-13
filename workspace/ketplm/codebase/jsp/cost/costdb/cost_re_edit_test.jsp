<%@ page language="java" contentType="text/html; charset=utf-8"%>

<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
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

    String visitor = "";
    String select_name = "";
    String etc_note = "";

    String pk_cr_group 	    = StringUtil.checkNull(request.getParameter("pk_cr_group"));
    String rev_no		    = StringUtil.checkNull(request.getParameter("rev_no"));
    String data_type		= StringUtil.checkNull(request.getParameter("data_type"));
    String group_case_count_temp = StringUtil.checkNull(request.getParameter("group_case_count"));
    int group_case_count = 0;
    if(!group_case_count_temp.equals("")){
        group_case_count = Integer.parseInt(group_case_count_temp);
    }

    String img_txt   = "";
    String img_txt_1 = "";
    String img_txt_2 = "";
    String img_case1 = "";
    String img_case2 = "";

    String file_1_name = StringUtil.checkNull(request.getParameter("file_1_name"));
    String file_2_name = StringUtil.checkNull(request.getParameter("file_2_name"));
    String file_3_name = StringUtil.checkNull(request.getParameter("file_3_name"));

    if(data_type.equals("case0")){
        img_txt = "small";
        img_txt_1 = "big";
        img_txt_2 = "small";
    }else if(data_type.endsWith("case1")){
        img_txt = "small";
        img_txt_1 = "small";
        img_txt_2 = "big";
    }else{
        img_txt = "big";
        img_txt_1 = "small";
        img_txt_2 = "small";
    }

    if(group_case_count == 1){
        img_case1 = "visible";
        img_case2 = "hidden";
    }else if(group_case_count == 2){
        img_case1 = "visible";
        img_case2 = "visible";
    }else{
        img_case1 = "hidden";
        img_case2 = "hidden";
    }
%>
<html>
<head>
<title>원가요청서</title>
<link rel='stylesheet' href="/plm/jsp/cost/css/wisegrid.css" type='text/css'>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGridUni_TAG.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/cost_re_edit_test_g.js"></script>
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

<script language=javascript for="WiseGrid_hidden" event="Initialize()">
    init6(); //WiseGrid_hidden
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
<script language=javascript for="WiseGrid_hidden" event="EndQuery()">
    GridEndQuery_hidden();
</script>

<!--  	WiseGrid의 셀을 클릭했을때 발생하는 Javacript Event인 CellClick()을 받아 해당하는 작업을 진행한다.  -->
<script language=javascript for="WiseGrid3" event="CellClick(strColumnKey,nRow)">
    GridCellClick3(strColumnKey, nRow)
</script>
<!--  	WiseGrid의 셀을 더블클릭했을때 발생하는 Javacript Event인 CellClick()을 받아 해당하는 작업을 진행한다.  -->
<script language=javascript for="WiseGrid3" event="CellDblClick(strColumnKey,nRow)">
    GridCellDblClick3(strColumnKey, nRow)
</script>

<!--	 WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WiseGrid3" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell3(strColumnKey, nRow);
</script>

<!--   	 WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WiseGrid3" event="ChangeCombo(strColumnKey,nRow,nOldIndex,nNewIndex)">
    GridChangeCombo3(strColumnKey, nRow);
</script>
<!--   	 WiseGrid의 Data 나 엑셀에서 복사한 Data를 붙여넣기 할경우 확인 메세지 출력   -->
<script language=javascript for='WiseGrid3' event='BeforeBlockPaste()'>
    if(!confirm('해당데이터를 변경하시겠습니까?')){
        GridObj3.SetBlockPasteCancel(true);
    }
</script>
<script language="JavaScript">

/*Row add*/
function GridCellClick3(strColumnKey, nRow)
{

    var GridObj3 = document.WiseGrid3;

    if(strColumnKey == "g_sel1" && GridObj3.getCellImage(strColumnKey, nRow) != -1) {
        doInsertRow1(strColumnKey, nRow);
    } else if(strColumnKey == "g_sel2" && GridObj3.getCellImage(strColumnKey, nRow) != -1) {
        doInsertRow2(strColumnKey, nRow);
    } else if(strColumnKey == "SELECTED" && GridObj3.getCellValue("SELECTED", nRow) == 1) {
        allSelectedChk(nRow, 1);
    } else if(strColumnKey == "SELECTED" && GridObj3.getCellValue("SELECTED", nRow) == 0) {
        allSelectedChk(nRow, 0);
    }

    for(var j=0; j<GridObj3.GetRowCount(); j++) {

        if(GridObj3.GetCellValue("group_no",j).length > 4 )
        {
              GridObj3.SetCellActivation('part_name', j, 'edit');
        }
        else
        {
              GridObj3.SetCellActivation('part_name', j, 'activatenoedit');
        }
    }
}

function allSelectedChk(nRow, gbn) {
    var str = GridObj3.getCellValue("group_no", nRow);
    for(var i=0; i < GridObj3.getRowCount(); i++) {
        if(GridObj3.GetCellValue("group_no",i).match(str)) {
            GridObj3.SetCellValue("SELECTED", i, gbn);
        }
    }
}

/**********************************************
 더블클릭이벤트
**********************************************/
function GridCellDblClick3(strColumnKey, nRow)
{
    var GridObj3 = document.WiseGrid3;
    if(strColumnKey == "pack_type")
    {
        var a_pk_cr = GridObj3.GetCellValue("pk_cr", nRow);
        var a_usage = GridObj3.GetCellValue("usage", nRow);

        //if(GridObj2.GetCellValue("store", nRow) != "")
            popUpOpen("/plm/jsp/cost/costdb/cost_pack.jsp?page_name=edit&k="+nRow+"&pk_cr="+a_pk_cr+"&a_usage="+a_usage, "pack_detail_pop", 1024, 300);
    }
    if(strColumnKey == "store")
    {
        var a_store = GridObj3.GetCellValue("store", nRow);
        var a_dest = GridObj3.GetCellValue("dest", nRow);

        //if(GridObj2.GetCellValue("store", nRow) != "")
            popUpOpen("/plm/jsp/cost/costdb/distri_cost.jsp?page_name=edit&i="+nRow+"&store="+a_store+"&dest="+a_dest, "prod_detail_pop", 470, 120);
    }
    if(strColumnKey == "meterial")
    {
        var pro_type = GridObj3.GetCellHiddenValue("pro_type", nRow);
        var meterial = GridObj3.GetCellValue("meterial", nRow);
        var m_maker  = GridObj3.GetCellValue("m_maker", nRow);
        var make     = GridObj3.GetCellValue("make", nRow);
        var plating  = GridObj3.GetCellValue("plating", nRow);
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

        unitcost_txt = escape(encodeURIComponent(unitcost_txt));
        m_maker = escape(encodeURIComponent(m_maker));
        //alert("/plm/jsp/cost/common/cost_grade.jsp?search_first=first&page_name=edit&k="+nRow+"&meterial="+meterial+"&make="+make+"&m_maker="+m_maker+"&plating="+plating+"&t_size="+t_size+"&w_size="+w_size+"&p_size="+p_size+"&m_mone="+m_mone+"&unitcost_txt="+unitcost_txt+"&order_con="+order_con+"&h_weight="+h_weight+"&h_scrap="+h_scrap+"&t_weight="+t_weight+"&recycle="+recycle+"&grade_name="+grade_name+"&grade_color="+grade_color+"&p_pro_type="+pro_type+"&sul_cost="+sul_cost+"&type_2="+type_2+"&plating_cost="+plating_cost);

        popUpOpen("/plm/jsp/cost/common/cost_grade.jsp?search_first=first&page_name=edit&k="+nRow+"&meterial="+meterial+"&make="+make+"&m_maker="+m_maker+"&plating="+plating+"&t_size="+t_size+"&w_size="+w_size+"&p_size="+p_size+"&m_mone="+m_mone+"&unitcost_txt="+unitcost_txt+"&order_con="+order_con+"&h_weight="+h_weight+"&h_scrap="+h_scrap+"&t_weight="+t_weight+"&recycle="+recycle+"&grade_name="+grade_name+"&grade_color="+grade_color+"&p_pro_type="+pro_type+"&sul_cost="+sul_cost+"&type_2="+type_2+"&plating_cost="+plating_cost, "cost_grade", 970, 220);
    }

}

function getRowCountTop(str) { // T001- 와 같은 그룹넘버의 갯수를 리턴
    var cnt = 0;

    for(var ii=0; ii<GridObj3.getRowCount; ii++) {
        if(GridObj3.GetCellValue("group_no",ii).match(str)) {
            cnt = cnt+1;
        }
    }
    return cnt;
}

/**********************************************
 그룹 넘버 생성
**********************************************/
function getRowCount(str, inx) { // T001- 와 같은 그룹넘버의 갯수를 리턴
    var GridObj3 = document.WiseGrid3;
    var cnt = 0;

    for(var ii=0; ii<GridObj3.getRowCount; ii++) {
        if(GridObj3.GetCellValue("group_no",ii).match(str) && str.length+3 == GridObj3.GetCellValue("group_no",ii).length) {
            if(GridObj3.GetCellHiddenValue("CRUD",ii) != "D") {
                cnt = cnt+1;
            }
        }
    }
    return cnt;
}
function getGroup_no(nRow) {
    var GridObj3 = document.WiseGrid3;
    var inx = Math.max(5, GridObj3.GetCellValue("group_no", nRow).length);
    var str = GridObj3.GetCellValue("group_no", nRow).substring(0, inx);
    var con = getRowCount(str, inx) + 1;

    if(String(con).length == 1) {
        con = "0"+con;
    }
    str	= str + "-"+con;

    return str;
}

/**********************************************
 케이스 카운트1 가져오기
**********************************************/
function getCaseCnt(nRow) {
    var inx = Math.max(5, GridObj3.GetCellValue("group_no", nRow).length);
    var str = GridObj3.GetCellValue("group_no", nRow).substring(0, inx);
    var con = getRowCount(str, inx);

    return con;
}

/**********************************************
 케이스 카운트2 가져오기
**********************************************/
function getCaseCnt2(nRow) {
    var con = GridObj3.GetCellValue("case_count_2", nRow);

    return Number(con) + 1;
}

/**********************************************
 상위 그룹넘버 nRow 가져오기
**********************************************/
function getGroupRow(nRow) {
    var cnt = 0;
    var str = GridObj3.GetCellValue("group_no", nRow).substring(0, 4);
    for(var ii=0; ii<GridObj3.getRowCount; ii++) {
        if(GridObj3.GetCellValue("group_no",ii) == str) {
            cnt = ii;
        }
    }
    return cnt;
}

function getGroupRow2(nRow) {
    var cnt = 0;
    var str = GridObj3.GetCellValue("group_no", nRow).substring(0, 7);
    for(var ii=0; ii<GridObj3.getRowCount; ii++) {
        if(GridObj3.GetCellValue("group_no",ii) == str) {
            cnt = ii;
        }
    }
    return cnt;
}

/**********************************************
 그룹 넘버 생성 끝
**********************************************/
function doInsertRow1(strColumnKey, nRow) {

    try {
        var pRow = GridObj3.GetActiveRowIndex() + getRowCountTop(GridObj3.GetCellValue("group_no", nRow).substring(0, 4));

        GridObj3.InsertRow(pRow);
        GridObj3.SetCellValue("group_no", pRow, getGroup_no(nRow));	// 그룹 넘버 생성
        GridObj3.SetCellValue("case_count_1", nRow,  getCaseCnt(nRow)); // 케이스 카운트 가져오기

    } catch(e) {
        GridObj3.AddRow();
        GridObj3.SetCellValue("group_no", GridObj3.GetActiveRowIndex(), getGroup_no(nRow)); // 그룹 넘버 생성
        GridObj3.SetCellValue("case_count_1", nRow, getCaseCnt(nRow)); // 케이스 카운트 가져오기
    }
    GridObj3.SetCellImage('g_sel2', GridObj3.GetActiveRowIndex(), 0);

}

function doInsertRow2(strColumnKey, nRow) {
    try {
        var tRow = nRow+1+Number(GridObj3.getCellValue("case_count_2", nRow));
        GridObj3.InsertRow(tRow);
        GridObj3.SetCellValue("group_no", GridObj3.GetActiveRowIndex(), getGroup_no(nRow)); // 그룹 넘버 생성
        GridObj3.SetCellValue("case_count_2", nRow,  getCaseCnt2(nRow)); // 케이스 카운트 가져오기
    } catch(e) {
        GridObj3.AddRow();
        GridObj3.SetCellValue("group_no", GridObj3.GetActiveRowIndex(), getGroup_no(nRow)); // 그룹 넘버 생성
        GridObj3.SetCellValue("case_count_2", nRow,  getCaseCnt2(nRow)); // 케이스 카운트 가져오기
    }
    GridObj3.SetCellImage('g_sel3', GridObj3.GetActiveRowIndex(), 0);
}

/* Row Del - Active된 로우의 인덱스 위치의 행을 삭제한다. */
function doDelete3(strColumnKey) {
    var cnt = GridObj3.getRowCount();
    var pRow = 0;
    var pRow2 = 0;
    var str = "";

    if(!chkSelected()) {
        alert("선택된 건이 없습니다.");
        return;
    }

    for(var ii=0; ii<cnt; ii++) {
        if(GridObj3.getCellValue("SELECTED", ii) == 1) {
            str = GridObj3.getCellValue("group_no", ii);
        }
        if(str != "" && GridObj3.GetCellValue("group_no",ii).match(str) && GridObj3.GetCellImage("g_sel1", ii) == -1) {
            pRow = getGroupRow(ii);
            pRow2 = getGroupRow2(ii);

            if(GridObj3.getCellValue("CRUD", ii) != "NEW") {
                GridObj3.DeleteRow(ii);
            } else {
                GridObj3.DeleteRow(ii);
                ii = ii-1;
                cnt = cnt-1;
            }

            GridObj3.SetCellValue("case_count_1", pRow,  getCaseCnt(pRow)); // 케이스 카운트 가져오기

            try {
                GridObj3.SetCellValue("case_count_2", pRow2,  getCaseCnt(pRow2)); // 케이스 카운트 2번째 가져오기
            } catch(e) {
            }

        }
    }
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

/********************************************************************
 조회 1
********************************************************************/

function doQuery1() {
    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj1.SetParam("mode", "cost_re_edit");
    GridObj1.SetParam("pk_cr_group",document.part_1.pk_cr_group.value);
    GridObj1.SetParam("etc_note", "");
    GridObj1.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj1.SetParam("data_type", document.part_1.data_type.value);
    GridObj1.SetParam("group_case_count", document.part_1.group_case_count.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj1.DoQuery(servlet_url);
}

/********************************************************************
 조회 2
********************************************************************/
function doQuery2() {
    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj2.SetParam("mode", "cost_re_edit2");
    GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj2.SetParam("etc_note", "");
    GridObj2.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj2.SetParam("data_type", document.part_1.data_type.value);
    GridObj2.SetParam("group_case_count", document.part_1.group_case_count.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj2.DoQuery(servlet_url);
}
/********************************************************************
 조회 3
********************************************************************/
function doQuery3() {
    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj3.SetParam("mode", "cost_re_edit3");
    GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj3.SetParam("etc_note", "");
    GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj3.SetParam("data_type", document.part_1.data_type.value);
    GridObj3.SetParam("group_case_count", document.part_1.group_case_count.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj3.DoQuery(servlet_url);
}
/********************************************************************
 저장1
********************************************************************/
function doSave3() {

    var servlet_url = "/plm/servlet/e3ps/costComServlet";

    //WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj3.SetParam("mode", "cost_re_edit_save");
    GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj3.SetParam("etc_note", document.part_1.etc_note.value);
    GridObj3.SetParam("data_type", document.part_1.data_type.value);
    GridObj3.SetParam("group_case_count", document.part_1.group_case_count.value);
    GridObj3.SetParam("pjt_no", GridObj1.GetCellValue("pjt_no",0));
    GridObj3.SetParam("pjt_name", GridObj1.GetCellValue("pjt_name",0));
    GridObj3.SetParam("team", GridObj1.GetCellHiddenValue("team",0));
    GridObj3.SetParam("f_name", GridObj1.GetCellValue("f_name",0));
    GridObj3.SetParam("a_name", GridObj1.GetCellValue("a_name",0));
    GridObj3.SetParam("dev_step", GridObj1.GetCellHiddenValue("dev_step",0));
    GridObj3.SetParam("dr_step", GridObj1.GetCellValue("dr_step",0));
    GridObj3.SetParam("sub_step", GridObj1.GetCellHiddenValue("sub_step",0));
    GridObj3.SetParam("request_day", GridObj1.GetCellValue("request_day",0));
    GridObj3.SetParam("request_txt", GridObj1.GetCellValue("request_txt",0));
    GridObj3.SetParam("table_row", GridObj2.getRowCount());

    GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj2.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj2.SetParam("etc_note", document.part_1.etc_note.value);
    GridObj2.SetParam("table_row", GridObj2.getRowCount());
    GridObj2.SetParam("data_type", document.part_1.data_type.value);
    GridObj2.SetParam("group_case_count", document.part_1.group_case_count.value);
    GridObj2.SetParam("pjt_no", GridObj1.GetCellValue("pjt_no",0));
    GridObj2.SetParam("pjt_name", GridObj1.GetCellValue("pjt_name",0));
    GridObj2.SetParam("team", GridObj1.GetCellHiddenValue("team",0));
    GridObj2.SetParam("f_name", GridObj1.GetCellValue("f_name",0));
    GridObj2.SetParam("a_name", GridObj1.GetCellValue("a_name",0));
    GridObj2.SetParam("dev_step", GridObj1.GetCellHiddenValue("dev_step",0));
    GridObj2.SetParam("dr_step", GridObj1.GetCellValue("dr_step",0));
    GridObj2.SetParam("sub_step", GridObj1.GetCellHiddenValue("sub_step",0));
    GridObj2.SetParam("request_day", GridObj1.GetCellValue("request_day",0));
    GridObj2.SetParam("request_txt", GridObj1.GetCellValue("request_txt",0));
    GridObj3.AddGridRawData("cost_re_edit_save2",document.WiseGrid2.GetGridRawData("WISEGRIDDATA_ALL"));


    //WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj3.DoQuery(servlet_url, "WISEGRIDDATA_ALL");
}
/********************************************************************
 저장2
********************************************************************/
function doSave2() {
    var servlet_url = "./cost_re_edit_test_db.asp";

    //WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj2.SetParam("mode", "save2");
    GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
    GridObj2.SetParam("rev_no", document.part_1.rev_no.value);
    GridObj2.SetParam("etc_note", document.part_1.etc_note.value);
    GridObj2.SetParam("table_row", GridObj2.getRowCount());
    GridObj2.SetParam("data_type", document.part_1.data_type.value);
    GridObj2.SetParam("group_case_count", document.part_1.group_case_count.value);

    GridObj2.AddGridRawData('WISEGRID_DATA1',document.WiseGrid1.GetGridRawData("WISEGRIDDATA_ALL"));

    //WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj2.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
}
/********************************************************************
 case추가
********************************************************************/
function case_add() {

    var servlet_url = "/plm/servlet/e3ps/costComServlet";

    document.part_1.group_case_count.value = "<%=group_case_count + 1 %>" ;

     if(document.part_1.group_case_count.value > 2 )
     {
         alert("Case추가는 두번을 넘을수 없습니다.");
         document.part_1.group_case_count.value = <%=group_case_count %> ;
     }
     else
     {
         //WiseGrid가 서버에 전송할 mode를 셋팅한다.

         var pjt_no = GridObj1.GetCellValue("pjt_no", 0);
         var pjt_name = GridObj1.GetCellValue("pjt_name", 0);
         var team = GridObj1.GetCellValue("team", 0);
         var f_name = GridObj1.GetCellValue("f_name", 0);
         var a_name = GridObj1.GetCellValue("a_name", 0);
         var dev_step = GridObj1.GetCellValue("dev_step", 0);
         var dr_step = GridObj1.GetCellValue("dr_step", 0);
         var request_day = GridObj1.GetCellValue("request_day", 0);
         var request_txt = GridObj1.GetCellValue("request_txt", 0);
         var etc_note = GridObj1.GetCellValue("etc_note", 0);


        GridObj3.SetParam("mode", "cost_re_edit_case1");
        GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
        GridObj3.SetParam("etc_note", document.part_1.etc_note.value);
        GridObj3.SetParam("data_type", document.part_1.data_type.value);
        GridObj3.SetParam("table_row2", GridObj3.getRowCount());
        GridObj3.SetParam("group_case_count", document.part_1.group_case_count.value);

        GridObj2.SetParam("pjt_no", pjt_no);
        GridObj2.SetParam("pjt_name", pjt_name);
        GridObj2.SetParam("team", team);
        GridObj2.SetParam("f_name", f_name);
        GridObj2.SetParam("a_name", a_name);
        GridObj2.SetParam("dev_step", dev_step);
        GridObj2.SetParam("dr_step", dr_step);
        GridObj2.SetParam("request_day", request_day);
        GridObj2.SetParam("request_txt", request_txt);
        GridObj2.SetParam("etc_note", etc_note);
        GridObj2.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj2.SetParam("rev_no", document.part_1.rev_no.value);
        GridObj2.SetParam("etc_note", document.part_1.etc_note.value);
        GridObj2.SetParam("table_row", GridObj2.getRowCount());
        GridObj2.SetParam("data_type", document.part_1.data_type.value);
        GridObj2.SetParam("group_case_count", document.part_1.group_case_count.value);

        GridObj1.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj1.SetParam("rev_no", document.part_1.rev_no.value);
        GridObj1.SetParam("etc_note", document.part_1.etc_note.value);
        GridObj1.SetParam("table_row", GridObj2.getRowCount());
        GridObj1.SetParam("data_type", document.part_1.data_type.value);
        GridObj1.SetParam("group_case_count", document.part_1.group_case_count.value);

        GridObj3.AddGridRawData("cost_re_edit_case2",document.WiseGrid2.GetGridRawData("WISEGRIDDATA_ALL"));
        GridObj3.AddGridRawData("cost_re_edit_case3",document.WiseGrid1.GetGridRawData("WISEGRIDDATA_ALL"));
        //WiseGrid가 서버와 통신시에 데이터를 전달한다
        GridObj3.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
    }
}
/********************************************************************
 case삭제
********************************************************************/
function case_del() {

if(confirm('해당Case를 삭제하시겠습니까?\n확인을 누르시면 Case가 삭제됩니다.')){
    var servlet_url = "/plm/servlet/e3ps/costComServlet";
        //WiseGrid가 서버에 전송할 mode를 셋팅한다.
        GridObj3.SetParam("mode", "case_del");
        GridObj3.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj3.SetParam("rev_no", document.part_1.rev_no.value);
        GridObj3.SetParam("etc_note", document.part_1.etc_note.value);
        GridObj3.SetParam("data_type", document.part_1.data_type.value);
        GridObj3.SetParam("table_row2", GridObj3.getRowCount());
        GridObj3.SetParam("group_case_count", document.part_1.group_case_count.value);

        //WiseGrid가 서버와 통신시에 데이터를 전달한
        GridObj3.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
    }
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
              GridObj3.SetCellActivation('pro_level_txt', nRow, 'edit');
        }
        else
        {
              GridObj3.SetCellActivation('pro_level_txt', nRow, 'activatenoedit');
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
    //if(GridObj1.getRowCount() < 0) {
        document.part_1.pk_cr_group.value= GridObj1.GetCellValue("pk_cr_group",0);
        document.part_1.etc_note.value= GridObj1.GetCellValue("etc_note",0);

    // 첨부파일명 replace /Upload/cost_file/원가산출요청/ → ""

        file_1_str = GridObj1.GetCellValue("file_1",0);
        file_1_str = file_1_str.replace("/Upload/cost_file/원가산출요청/","▶ ");

        file_2_str = GridObj1.GetCellValue("file_2",0);
        file_2_str = file_2_str.replace("/Upload/cost_file/원가산출요청/","▶ ");

        file_3_str = GridObj1.GetCellValue("file_3",0);
        file_3_str = file_3_str.replace("/Upload/cost_file/원가산출요청/","▶ ");

        document.part_1.file_1.value = file_1_str;
        document.part_1.file_2.value = file_2_str;
        document.part_1.file_3.value = file_3_str;


    //}
}
 /**********************************************
  main, case 에 따른 GridObj2 수정 여부
 **********************************************/
function setCellActivation() {
    var activation = 'activatenoedit';

    if(document.part_1.data_type.value == "main" && GridObj2.GetCellValue("partSqe",0).length > 0) {
        activation = 'activatenoedit';
    } else {
        activation = 'edit';
    }

    for(var i=0; i<GridObj2.GetRowCount(); i++) {
        GridObj2.SetCellActivation('su_year_1', i, activation);
        GridObj2.SetCellActivation('su_year_2', i, activation);
        GridObj2.SetCellActivation('su_year_3', i, activation);
        GridObj2.SetCellActivation('su_year_4', i, activation);
        GridObj2.SetCellActivation('su_year_5', i, activation);
        GridObj2.SetCellActivation('su_year_6', i, activation);
        GridObj2.SetCellActivation('su_year_7', i, activation);
        GridObj2.SetCellActivation('su_year_8', i, activation);

        <% if(login_id.equals("admin")){ %>
        if( i == 0){
        GridObj1.SetCellActivation('f_name', i, 'edit');
        GridObj1.SetCellActivation('a_name', i, 'edit');
        }
        GridObj2.SetCellActivation('su_year_1', i, 'edit');
        GridObj2.SetCellActivation('su_year_2', i, 'edit');
        GridObj2.SetCellActivation('su_year_3', i, 'edit');
        GridObj2.SetCellActivation('su_year_4', i, 'edit');
        GridObj2.SetCellActivation('su_year_5', i, 'edit');
        GridObj2.SetCellActivation('su_year_6', i, 'edit');
        GridObj2.SetCellActivation('su_year_7', i, 'edit');
        GridObj2.SetCellActivation('su_year_8', i, 'edit');

        GridObj2.SetCellActivation('client_cost', i, 'edit');
        GridObj2.SetCellActivation('ket_cost', i, 'edit');
        GridObj2.SetCellActivation('target_cost', i, 'edit');

        <%}else if(login_id.equals("pro_man")){ %>

        GridObj2.SetCellActivation('client_cost', i, 'edit');
        GridObj2.SetCellActivation('ket_cost', i, 'edit');
        GridObj2.SetCellActivation('target_cost', i, 'edit');
        <%}%>
    }

    for(var j=0; j<GridObj3.GetRowCount(); j++) {
        if(GridObj3.GetCellValue("pro_level", j) == "기타")
        {
              GridObj3.SetCellActivation('pro_level_txt', j, 'edit');
        }
        else
        {
              GridObj3.SetCellActivation('pro_level_txt', j, 'activatenoedit');
        }
    }
}
 /**********************************************
  작업인원 선택에 따른 수정 여부
 **********************************************/
function setCellActivation_2() {

    for(var j=0; j<GridObj3.GetRowCount(); j++) {

        if(GridObj3.GetCellValue("pro_level", j) == "기타")
        {
              GridObj3.SetCellActivation('pro_level_txt', j, 'edit');
        }
        else
        {
              GridObj3.SetCellActivation('pro_level_txt', j, 'activatenoedit');
        }
    }
}
function GridEndQuery2() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj2.GetParam("mode");

    if(mode == "search") {
        setCellActivation();
    } else if(mode == "save2") {

        if(GridObj2.GetStatus() == "true")  	{ // 서버에서 전송한 상태코드를 가져온다.
            alert("저장되었습니다.");
        }
        else
        {
            var error_msg = GridObj2.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert("error_msg");
        }

        document.part_1.submit();
    }
}

function GridEndQuery3() {

    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj3.GetParam("mode");
    if(mode == "search") {
        setCellActivation_2();
    } else if(mode == "save") {

        if(GridObj3.GetStatus() != "true")  	{ // 서버에서 전송한 상태코드를 가져온다.

            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert("error_msg");
        }
        doSave2();
    }

    if(mode == "save3") {

        var data_type = GridObj3.GetParam("data_type");

        if(data_type == "" || data_type == null){
            data_type = GridObj2.GetParam("data_type");
        }

        document.part_1.data_type.value= data_type ;

        if(GridObj3.GetStatus() != "true")  	{ // 서버에서 전송한 상태코드를 가져온다.
            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert(error_msg);
        }else{
            alert("저장되었습니다.");
        }
        document.part_1.submit();
        //doSave2();
    }
    if(mode == "del") {
        var data_type = GridObj3.GetParam("data_type");

        document.part_1.data_type.value= data_type ;

        var group_case_co = document.part_1.group_case_count.value;
        if(group_case_co != 0)
        {
            document.part_1.group_case_count.value = document.part_1.group_case_count.value - 1
        }

        if(GridObj3.GetStatus() == "true")  	{ // 서버에서 전송한 상태코드를 가져온다.

            alert("삭제되었습니다.");
        }
        else
        {
            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert(error_msg);
        }

        document.part_1.submit();
    }
}

function GridEndQuery_hidden() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj6.GetParam("mode");

    if(mode == "cost_acc") {
        if(GridObj6.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
            //서버에서 insert_data 셋팅한 파라미터를 가져온다.
            var cost_acc = GridObj6.GetParam("cost_acc");
            //var test_txt = GridObj6.GetParam("test_txt");
            //fieldset에 결과 값을 보이게 한다.
            //alert(test_txt)
            alert("산출작업이 완료되었습니다.");

            setCostAcc(cost_acc); // 간이산출 그리드에 결과값 넣기
            //document.part_1.result.innerHTML= '<legend>간이산출</legend>'+cost_acc;
        } else	{
            var error_msg = GridObj6.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert(error_msg);
        }
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
            popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type=file_1&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
    function  filecheck_file_2()
    {
        a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
          document.part_1.file_edit_2.checked = false;
          popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type=file_2&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
    function  filecheck_file_3()
    {
        a_pk_cr_group = GridObj1.GetCellValue("pk_cr_group",0);
          document.part_1.file_edit_3.checked = false;
          popUpOpen("/plm/jsp/cost/common/file_add.jsp?file_type=file_3&pk_cr_group="+a_pk_cr_group, "file_pop", 470, 120);
    }
 /**********************************************
  검토(개발)의뢰서 검색
 **********************************************/
    function SEL_PLM()
    {
        a_pjt_no = GridObj1.GetCellValue("pjt_no",0);
        Hcount  = GridObj3.GetRowCount();
        window.open("./plm_search.asp?search_s=ok&page_type=edit&pjt_no="+a_pjt_no+"&Hcount="+Hcount, "plm_search_pop", "width=510,height=400,scrollbars=yes");
    }
 /**********************************************
     간이산출
 **********************************************/
    function cost_acc_call()
    {
    var simple_cost_1 = "";
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
        if(GridObj3.GetCellHiddenValue("pro_1",j) == "empty"){
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


     if (simple_cost_1 != "no"){

         var servlet_url = "/plm/servlet/e3ps/CostAccTestServlet";
        //WiseGrid가 서버에 전송할 Param을 셋팅한다.
        GridObj6.SetParam("mode", "cost_acc");
        GridObj6.SetParam("pk_cr_group", document.part_1.pk_cr_group.value);
        GridObj6.SetParam("table_row", GridObj2.getRowCount);
        GridObj6.SetParam("rev_no", GridObj2.GetCellValue("rev_no",0));
        //GridObj3.SetParam("c_count_1", GridObj3.GetCellValue("case_count_1",0));
        //GridObj3.SetParam("c_count_2", GridObj3.GetCellValue("case_count_2",0));
        GridObj6.AddGridRawData('WISEGRID_DATA3',document.WiseGrid3.GetGridRawData("WISEGRIDDATA_ALL"));
        //WiseGrid가 서버와 통신시에 데이터를 전달한다.
        GridObj6.DoQuery(servlet_url,"WISEGRIDDATA_ALL");
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

    document.part_1.to_date.value = year + "" + month + "" + day;
}
/**********************************************
 List Call
**********************************************/
  function back_call()
 {
      <% if(visitor.equals("4")){%>
          this.close();
      <%}else{%>
          window.location.href ="/plm/jsp/cost/index.html?select_name="+'<%=select_name%>';
      <%}%>
  }
/**********************************************
 Main Call
**********************************************/
  function main_call()
 {
     var group_case_count = document.part_1.group_case_count.value;
     var pk_cr_group = '<%=pk_cr_group%>';
     var rev_no = '<%=rev_no%>';
      window.location.href ="/plm/jsp/cost/costdb/cost_re_edit_test.jsp?pk_cr_group="+pk_cr_group + "&rev_no=" + rev_no + "&data_type=main&group_case_count="+group_case_count;
  }
/**********************************************
 Case0 Call
**********************************************/
  function case1_call()
  {
     var group_case_count = document.part_1.group_case_count.value;
     var pk_cr_group = '<%=pk_cr_group%>';
     var rev_no = '<%=rev_no%>';
      window.location.href ="/plm/jsp/cost/costdb/cost_re_edit_test.jsp?pk_cr_group="+pk_cr_group + "&rev_no=" + rev_no + "&data_type=case0&group_case_count="+group_case_count;
  }
/**********************************************
 Case1 Call
**********************************************/
  function case2_call()
  {
     var group_case_count = document.part_1.group_case_count.value;

      window.location.href ="./cost_re_edit_test.asp?pk_cr_group=<%=pk_cr_group%>&rev_no=<%=rev_no%>&data_type=case1&group_case_count="+group_case_count
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
body {
    background-color: #FFFFFF;
    background-image: url(/plm/jsp/cost/acc_img/sub_top.jpg);
    background-repeat: no-repeat;
    margin-top: 0px;
}
.style2 {font-size: 12px; text-align:left; background-color:#ffffff;color:#F26522;}
#box {border:1 solid #ffffff ;padding:3 1 0 2;}

-->
</style></head>
<body onselectstart="return false">
<form name="part_1">
<input name="pk_cr_group" type="hidden" size="2" value=<%=pk_cr_group%>>
<input name="file_1_name" type="hidden" size="2" value=<%=file_1_name%>>
<input name="file_2_name" type="hidden" size="2" value=<%=file_2_name%>>
<input name="file_3_name" type="hidden" size="2" value=<%=file_3_name%>>
<input name="rev_no" type="hidden" size="2" value=<%=rev_no%>>
<input name="data_type" type="hidden" size="2" value=<%=data_type%>>
<input name="group_case_count" type="hidden" size="2" value=<%=group_case_count%>>
<script>initWiseGrid("WiseGrid_hidden", "0", "0");</script>
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
<table width="1226" height="635" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <table width="1224" height="61" border="0" cellpadding="0" cellspacing="0" >
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
    <td><table width="1224" height="173" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td height="20" valign="bottom" align="left"><img src="/plm/jsp/cost/acc_img/tap_1_big.gif" border="0"></td>
            </tr>
            <tr>
                <td bgcolor="#00455d" height="3"></td>
            </tr>
            <tr>
                  <td height="150" align="left" valign="top"><script>initWiseGrid("WiseGrid2", "1224", "150");</script></td>
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
                <td  height="20" valign="bottom" align="left"><img src="/plm/jsp/cost/acc_img/tap_2_big.gif" border="0"></td>
                <td height="20" align="right" valign="bottom">
                    <img src="/plm/jsp/cost/acc_img/btn_Delete.gif" width="51" height="17" border="0" onClick="doDelete3();" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_Save.gif" width="51" height="17" border="0" onClick="doSave3();" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_Reset.gif" width="61" height="17" border="0" onClick="doSave3Cancel();" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_cost_acc_call.gif" width="77" height="17" border="0" onClick="cost_acc_call();" style="cursor:pointer;"/>&nbsp;&nbsp;
                    <img src="/plm/jsp/cost/acc_img/btn_plus.jpg" border="0" onClick="seePlus('tb_product');" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_minus.jpg" border="0" onClick="seeMinus('tb_product');" style="cursor:pointer;"/>
                </td>
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
        <table width="1224" height="88" border="1" cellpadding="0" cellspacing="0" >
          <tr>
            <td bgcolor="#00455d" height="3"></td>
          </tr>
          <tr>
            <td align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="CCCCCC">
                <tr>
                  <td width="150" rowspan="3" bgcolor="F2F2F2" align="center">비고 및 특기사항 </td>
                  <td width="480" rowspan="3" bgcolor="#FFFFFF" ><textarea name="etc_note" cols="70" rows="5" ><%=etc_note%></textarea></td>
                  <td width="130" rowspan="3" bgcolor="F2F2F2" align="center">첨부파일</td>
                  <td width="450" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file_1()"style='cursor:pointer'>
                      <input type="checkbox" name="file_edit_1" value="ON" checked>이전 파일 유지하기
                      <input name="file_1" type="text" class="style2" size="42"  id="box" onClick="file_call1();" readonly>
                  </td>
                </tr>
                <tr>
                <td bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file_2()" style='cursor:pointer'>
                      <input type="checkbox" name="file_edit_2" value="ON" checked >이전 파일 유지하기
                      <input name="file_2" type="text" class="style2" size="42"  id="box" onClick="file_call2();" readonly>
                </td>
                </tr>
                <tr>
                <td height="21" bgcolor="#FFFFFF" ><img src="/plm/jsp/cost/acc_img/btn_filecheck_file_1.gif" border="0" onClick="filecheck_file_3()" style="cursor:pointer;"/>
                    <input type="checkbox" name="file_edit_3" value="ON" checked >이전 파일 유지하기
                    <input name="file_3" type="text" class="style2" size="42"  id="box" onClick="file_call3();" readonly>
                </td>
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
<area shape="rect" coords="8,3,114,16" href="#" onClick="main_call();">
<area shape="rect" coords="117,4,132,16" href="#" <%if(data_type.equals("main")){%>onClick="case_add();"<%}%>>
</map>
<map name="Map2">
<area shape="rect" coords="5,3,38,14" href="#" onClick="case1_call();">
<area shape="rect" coords="43,3,55,13" href="#" <%if(data_type.equals("case0")){%>onClick="case_del();"<%}%>>
</map>
<map name="Map3">
<area shape="rect" coords="5,3,42,13" href="#" onClick="case2_call();">
<area shape="rect" coords="44,3,56,14" href="#" <%if(data_type.equals("case1")){%>onClick="case_del();"<%}%>>
</map>
<map name="Map4">
<area shape="rect" coords="3,3,92,16" href="#" onClick="main_call();">
<area shape="rect" coords="94,4,107,17" href="#" <%if(data_type.equals("main")){%>onClick="case_add();"<%}%>>
</map>
<map name="Map5">
<area shape="rect" coords="3,4,42,14" href="#" onClick="case1_call();">
<area shape="rect" coords="44,4,57,16" href="#" <%if(data_type.equals("case0")){%>onClick="case_del();"<%}%>></map>
<map name="Map6"><area shape="rect" coords="4,3,42,13" href="#" onClick="case2_call();">
<area shape="rect" coords="45,3,56,13" href="#" <%if(data_type.equals("case1")){%>onClick="case_del();"<%}%>></map>
</body>
</html>