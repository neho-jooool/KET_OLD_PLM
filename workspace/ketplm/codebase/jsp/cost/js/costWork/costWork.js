var GridObj3;
var GridObj4;
var GridObj5;
var GridObj6;
var GridObj7;
var pageNo = 'T001';

function init3() {
    GridObj3 = document.WiseGrid3;
    setProperty(GridObj3);
    GridObj3.bHDMoving = false;
    GridObj3.bHDSwapping = false;
    GridObj3.bStatusbarVisible = false;
    GridObj3.strActiveRowBgColor = "254|252|219";
    setHeader3();
    doQuery3('T001');
}

function init4() {
    GridObj4 = document.WiseGrid4;
    setProperty(GridObj4);
    GridObj4.bHDMoving = false;
    GridObj4.bHDSwapping = false;
    GridObj4.bStatusbarVisible = false;
    GridObj4.strActiveRowBgColor = "254|252|219";
    setHeader4();
    doQuery4('T001');
}
function init5() {
    GridObj5 = document.WiseGrid5;
    setProperty(GridObj5);
    GridObj5.bHDMoving = false;
    GridObj5.bHDSwapping = false;
    GridObj5.bStatusbarVisible = false;
    GridObj5.nHDLines = 2;
    setHeader5();
}
function init6() {
    GridObj6 = document.WiseGrid6;
    setProperty(GridObj6);
    GridObj6.bHDMoving = false;
    GridObj6.bHDSwapping = false;
    GridObj6.bStatusbarVisible = false;
    GridObj6.nHDLines = 1;
    setHeader6();
    doQuery6('T001');
}

function init7() {
    GridObj7 = document.WiseGrid_hidden;
    setHeader7();
}

function setHeader3() {
    // 그리드에 컬럼을 등록한다.

    GridObj3.AddHeader("SELECTED", "", "t_checkbox", 2, 30, true);
    GridObj3.AddHeader("CRUD", "", "t_text", 8, 40, true);
    GridObj3.AddHeader("m_co_chk", "제외", "t_checkbox", 2, 30, true);
    GridObj3.AddHeader("case_count_1", "case_count_1", "t_text", 50, 40, true);
    GridObj3.AddHeader("case_count_2", "case_count_2", "t_text", 50, 40, true);
    GridObj3.AddHeader("pk_cw", "pk_cw", "t_text", 50, 10, true);
    GridObj3.AddHeader("pk_cr_group", "pk_cr_group", "t_text", 50, 10, true);
    GridObj3.AddHeader("make", "make", "t_text", 50, 10, true);
    GridObj3.AddHeader("group_no", "그룹", "t_text", 20, 66, true);
    GridObj3.AddHeader("pro_type", "Type", "t_combo", 20, 80, true);
    GridObj3.AddHeader("g_sel1", "1", "t_imagetext", 40, 20, false);
    GridObj3.AddHeader("g_sel2", "2", "t_imagetext", 40, 20, false);
    GridObj3.AddHeader("g_sel3", "3", "t_imagetext", 40, 20, false);
    GridObj3.AddHeader("part_name", "품명", "t_text", 2000, 200, true);
    GridObj3.AddHeader("part_type", "제작구분", "t_combo", 10, 60, true);
    // GridObj3.AddHeader("Fam_group", "Family\n금형", "t_combo", 10, 60, true);
    // GridObj3.AddHeader("sel_group", "개조금형", "t_combo", 10, 60, true);
    GridObj3.AddHeader("mix_group", "복합금형", "t_combo", 100, 60, true);
    GridObj3.AddHeader("part_no", "품번", "t_text", 50, 80, true);
    GridObj3.AddHeader("net_1", "NetSize\n장측", "t_text", 10, 50, true);
    GridObj3.AddHeader("net_2", "NetSize\n단측", "t_text", 10, 50, true);
    GridObj3.AddHeader("net_3", "NetSize\n깊이", "t_text", 10, 50, true);
    GridObj3.AddHeader("usage", "조립\nU/S", "t_text", 10, 40, true);
    GridObj3.AddHeader("meterial", "Material", "t_text", 255, 80, true);
    GridObj3.AddHeader("t_size", "두께", "t_text", 10, 40, true);
    GridObj3.AddHeader("w_size", "폭", "t_text", 10, 40, true);
    GridObj3.AddHeader("p_size", "Pitch", "t_text", 10, 40, true);
    GridObj3.AddHeader("plating", "Finish", "t_text", 50, 60, true);
    GridObj3.AddHeader("m_maker", "Maker", "t_text", 50, 60, true);
    GridObj3.AddHeader("m_mone", "단위", "t_text", 10, 50, true);
    GridObj3.AddHeader("unitcost", "원재료단가", "t_text", 50, 60, true);
    GridObj3.AddHeader("c_unitcost", "c_unitcost", "t_text", 50, 60, true);
    GridObj3.AddHeader("grade_name", "grade_name", "t_text", 255, 60, true);
    GridObj3.AddHeader("grade_color", "grade_color", "t_text", 50, 60, true);
    GridObj3.AddHeader("order_con", "발주\n조건", "t_text", 10, 40, true);
    GridObj3.AddHeader("h_weight", "부품\n중량", "t_text", 20, 50, true);
    GridObj3.AddHeader("h_scrap", "스크랩\n중량", "t_text", 20, 50, true);
    GridObj3.AddHeader("t_weight", "제품\n총중량", "t_text", 20, 50, true);
    GridObj3.AddHeader("recycle_state", "스크랩\n재생", "t_combo", 20, 50, true);
    GridObj3.AddHeader("recycle", "재생\n비율\n%", "t_text", 10, 50, true);
    GridObj3.AddHeader("die_no", "Die No", "t_text", 10, 80, true);
    GridObj3.AddHeader("cavity", "C/V", "t_text", 10, 50, true);
    GridObj3.AddHeader("m_su", "금형\n벌수", "t_text", 10, 50, true);
    GridObj3.AddHeader("mold_cost", "금형비\n(천원)", "t_number", 10.3, 80, true);
    GridObj3.AddHeader("mold_c_type", "감가\n조건", "t_combo", 10, 50, true);
    GridObj3.AddHeader("make_type", "생산처1", "t_combo", 10, 60, true);
    GridObj3.AddHeader("pro_1", "생산처2", "t_combo", 10, 60, true);
    GridObj3.AddHeader("ton", "설비\nTon", "t_number", 10.4, 50, true);
    GridObj3.AddHeader("spm", "C/T\n(SPM)", "t_text", 10, 50, true);
    GridObj3.AddHeader("method_type", "공법", "t_combo", 20, 80, true);
    GridObj3.AddHeader("pro_level", "선택", "t_combo", 10, 50, true);
    GridObj3.AddHeader("pro_level_txt", "인원", "t_text", 10, 50, true);
    GridObj3.AddHeader("line_su", "조립\n라인", "t_text", 10, 50, true);
    GridObj3.AddHeader("sul_cost", "설비비\n(천원)", "t_number", 10.3, 80, true);
    GridObj3.AddHeader("plating_type", "구분1", "t_combo", 50, 80, true);
    GridObj3.AddHeader("type_2", "구분2", "t_combo", 20, 50, true);
    GridObj3.AddHeader("plating_cost", "￦/Kg\n￦/EA", "t_text", 50, 80, true);
    GridObj3.AddHeader("type_1", "구분", "t_combo", 50, 60, true);
    GridObj3.AddHeader("t_mone", "단위\n(Sec)", "t_combo", 20, 50, true);
    GridObj3.AddHeader("type_cost", "단가\n(시간/EA)", "t_number", 10.3, 80, true);
    GridObj3.AddHeader("t_order", "발주\n조건", "t_combo", 10, 50, true);
    GridObj3.AddHeader("pack_type", "포장유형", "t_combo", 20, 80, true);
    GridObj3.AddHeader("in_pack", "내장\n(EA/봉지)", "t_number", 10.3, 63, true);
    GridObj3.AddHeader("out_pack", "외장\n(EA/Box)", "t_number", 10.3, 60, true);
    GridObj3.AddHeader("distri_cost", "물류\n흐름비", "t_text", 20, 50, true);
    // GridObj3.AddHeader("yzk_mone", "로얄티\n유무", "t_combo", 10, 50, true);
    GridObj3.AddHeader("etc_cost", "기타\n투자비\n(천원)", "t_number", 10.3, 80, true);
    GridObj3.AddHeader("yazaki_ro", "포장용\n투자비\n(천원)", "t_number", 10.3, 80,
            true);
    GridObj3.AddHeader("part_note", "그외 특기사항", "t_text", 2000, 300, true);
    GridObj3.AddHeader("car_type", "적용차종", "t_text", 100, 65, false);
    GridObj3.AddHeader("p_su_chk", "공용선택", "t_checkbox", 2, 30, false);
    GridObj3.AddHeader("su_year_1", "1년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_2", "2년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_3", "3년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_4", "4년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_5", "5년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_6", "6년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_7", "7년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("su_year_8", "8년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_1", "1년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_2", "2년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_3", "3년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_4", "4년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_5", "5년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_6", "6년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_7", "7년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("p_su_year_8", "8년차", "t_number", 10.3, 50, false);
    GridObj3.AddHeader("client_cost", "고객사\n인정예상가", "t_text", 100, 80, false);
    GridObj3.AddHeader("ket_cost", "판매\n목표가", "t_text", 100, 70, false);
    GridObj3.AddHeader("target_cost", "목표\n수익률", "t_text", 100, 70, false);
    GridObj3.AddHeader("store", "보관", "t_text", 100, 85, false);
    GridObj3.AddHeader("dest", "납입처", "t_text", 100, 85, false);
    GridObj3.AddHeader("royalty", "로얄티\n유무", "t_text", 100, 50, false);
    GridObj3.AddHeader("information", "참고사항", "t_text", 2000, 50, false);
    GridObj3.AddHeader("USD_rate", "USD", "t_text", 100, 50, false);
    GridObj3.AddHeader("EURO_rate", "EURO", "t_text", 100, 50, false);
    GridObj3.AddHeader("YEN_rate", "YEN", "t_text", 100, 50, false);
    GridObj3.AddHeader("CNY_rate", "CNY", "t_text", 100, 50, false);
    GridObj3.AddHeader("lme_ton", "LME", "t_text", 100, 50, false);
    GridObj3.AddHeader("u_ex_rate", "LME-USD", "t_text", 100, 50, false);
    GridObj3.AddHeader("y_ex_rate", "LME-YEN", "t_text", 100, 50, false);
    GridObj3.AddHeader("cost_report_add", "cost_report_add", "t_text", 30, 50,
            false);
    GridObj3.AddHeader("file_1", "file_1", "t_text", 2000, 290, false);
    // GridObj3.AddHeader("file_2", "file_2", "t_text", 2000, 290, false);
    // GridObj3.AddHeader("file_3", "file_3", "t_text", 2000, 290, false);

    // 그룹생성
    GridObj3.AddGroup("CHK_TOTAL", "선택");
    GridObj3.AddGroup("PRO_TOTAL", "제품Level");
    GridObj3.AddGroup("PRO_LIST_TOTAL", "제품내역");
    GridObj3.AddGroup("meterial_TOTAL", "주 재료내역");
    GridObj3.AddGroup("MOLD_TOTAL", "금형내역");
    GridObj3.AddGroup("MAKE_TOTAL", "생산내역");
    GridObj3.AddGroup("PLAT_TOTAL", "도금/후처리");
    GridObj3.AddGroup("TYPE_TOTAL", "외주/구매/OEM/검사/포장/기타");
    GridObj3.AddGroup("PACK_TOTAL", "포장내역");

    /***************************************************************************
     * 그룹에 헤더 추가
     **************************************************************************/

    // select
    GridObj3.AppendHeader("CHK_TOTAL", "SELECTED");
    GridObj3.AppendHeader("CHK_TOTAL", "CRUD");
    GridObj3.AppendHeader("CHK_TOTAL", "m_co_chk");

    // 제품Level
    GridObj3.AppendHeader("PRO_TOTAL", "group_no");
    GridObj3.AppendHeader("PRO_TOTAL", "pro_type");
    GridObj3.AppendHeader("PRO_TOTAL", "g_sel1");
    GridObj3.AppendHeader("PRO_TOTAL", "g_sel2");
    GridObj3.AppendHeader("PRO_TOTAL", "g_sel3");

    // 제품내역
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
    // 주 재료내역
    GridObj3.AppendHeader("meterial_TOTAL", "meterial");
    GridObj3.AppendHeader("meterial_TOTAL", "t_size");
    GridObj3.AppendHeader("meterial_TOTAL", "w_size");
    GridObj3.AppendHeader("meterial_TOTAL", "p_size");
    GridObj3.AppendHeader("meterial_TOTAL", "plating");
    GridObj3.AppendHeader("meterial_TOTAL", "m_maker");
    GridObj3.AppendHeader("meterial_TOTAL", "m_mone");
    GridObj3.AppendHeader("meterial_TOTAL", "unitcost");
    GridObj3.AppendHeader("meterial_TOTAL", "c_unitcost");
    GridObj3.AppendHeader("meterial_TOTAL", "grade_name");
    GridObj3.AppendHeader("meterial_TOTAL", "grade_color");
    GridObj3.AppendHeader("meterial_TOTAL", "order_con");
    GridObj3.AppendHeader("meterial_TOTAL", "h_weight");
    GridObj3.AppendHeader("meterial_TOTAL", "h_scrap");
    GridObj3.AppendHeader("meterial_TOTAL", "t_weight");
    GridObj3.AppendHeader("meterial_TOTAL", "recycle_state");
    GridObj3.AppendHeader("meterial_TOTAL", "recycle");
    // 금형내역
    GridObj3.AppendHeader("MOLD_TOTAL", "die_no");
    GridObj3.AppendHeader("MOLD_TOTAL", "cavity");
    GridObj3.AppendHeader("MOLD_TOTAL", "m_su");
    GridObj3.AppendHeader("MOLD_TOTAL", "mold_cost");
    GridObj3.AppendHeader("MOLD_TOTAL", "mold_c_type");
    // 생산내역
    GridObj3.AddGroup("PP_SU", "작업인원");

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

    // 도금/후처리
    GridObj3.AppendHeader("PLAT_TOTAL", "plating_type");
    GridObj3.AppendHeader("PLAT_TOTAL", "type_2");
    GridObj3.AppendHeader("PLAT_TOTAL", "plating_cost");

    // 외주/구매/OEM/검사/포장/기타
    GridObj3.AppendHeader("TYPE_TOTAL", "type_1");
    GridObj3.AppendHeader("TYPE_TOTAL", "t_mone");
    GridObj3.AppendHeader("TYPE_TOTAL", "type_cost");
    GridObj3.AppendHeader("TYPE_TOTAL", "t_order");
    // 포장내역
    GridObj3.AppendHeader("PACK_TOTAL", "pack_type");
    GridObj3.AppendHeader("PACK_TOTAL", "in_pack");
    GridObj3.AppendHeader("PACK_TOTAL", "out_pack");

    // AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj3.BoundHeader();

    // 저장모드를 사용해 서버사이드와 통신한다.
    GridObj3.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

    // 이미지리스트에 이미지 URL을 추가한다.
    GridObj3.AddImageList("g_sel1", "/plm/jsp/cost/images/common/add_red.jpg");
    GridObj3.AddImageList("g_sel2", "/plm/jsp/cost/images/common/add.jpg");
    GridObj3.AddImageList("g_sel3", "/plm/jsp/cost/images/common/add.jpg");

    /***************************************************************************
     * 콤보리스트 생성
     **************************************************************************/

    // GridObj3.AddComboList("pro_type","A"); 멀티콤보로 할때 사용
    // 제품Type List
    GridObj3.AddComboListValue("pro_type", "", "empty");
    GridObj3.AddComboListValue("pro_type", "조립", "assy");
    GridObj3.AddComboListValue("pro_type", "Insert", "Insert");
    GridObj3.AddComboListValue("pro_type", "sub조립", "sub_assy");
    GridObj3.AddComboListValue("pro_type", "HS'G", "HSG");
    GridObj3.AddComboListValue("pro_type", "HS'G-Box", "HSG-Box");
    GridObj3.AddComboListValue("pro_type", "sub_Insert", "sub_Insert");
    GridObj3.AddComboListValue("pro_type", "TM'L", "TML");
    GridObj3.AddComboListValue("pro_type", "TML-조립", "TML-조립");
    GridObj3.AddComboListValue("pro_type", "부재료", "부재료");
    GridObj3.AddComboListValue("pro_type", "구매", "구매");

    // 제작구분List
    GridObj3.AddComboListValue("part_type", "신규", "신규");
    GridObj3.AddComboListValue("part_type", "공용", "공용");
    GridObj3.AddComboListValue("part_type", "개조", "개조");
    GridObj3.AddComboListValue("part_type", "수정", "수정");
    GridObj3.AddComboListValue("part_type", "복합금형", "복합금형");
    GridObj3.AddComboListValue("part_type", "Family", "Family");

    // 복합금형List
    GridObj3.AddComboListValue("mix_group", "", "empty");
    GridObj3.AddComboListValue("mix_group", "선생산", "선생산");
    GridObj3.AddComboListValue("mix_group", "후생산", "후생산");
    GridObj3.AddComboListValue("mix_group", "동시생산", "동시생산");

    // 감가조건
    GridObj3.AddComboListValue("mold_c_type", "감가", "감가");
    GridObj3.AddComboListValue("mold_c_type", "지급", "지급");

    // 생산처1
    GridObj3.AddComboListValue("make_type", "", "empty");
    GridObj3.AddComboListValue("make_type", "사내", "사내");
    GridObj3.AddComboListValue("make_type", "외주", "외주");
    GridObj3.AddComboListValue("make_type", "구매", "구매");
    GridObj3.AddComboListValue("make_type", "OEM", "OEM");

    // 세부생산처
    GridObj3.AddComboListValue("pro_1", "", "empty");
    GridObj3.AddComboListValue("pro_1", "생산1", "생산1");
    GridObj3.AddComboListValue("pro_1", "생산2", "생산2");
    GridObj3.AddComboListValue("pro_1", "생산3", "생산3");
    GridObj3.AddComboListValue("pro_1", "생산4", "생산4");
    GridObj3.AddComboListValue("pro_1", "중국", "중국");
    GridObj3.AddComboListValue("pro_1", "유상", "유상");
    GridObj3.AddComboListValue("pro_1", "무상", "무상");
    GridObj3.AddComboListValue("pro_1", "외주", "외주");

    // 공법
    GridObj3.AddComboListValue("method_type", "", "empty");
    GridObj3.AddComboListValue("method_type", "단순", "단순");
    GridObj3.AddComboListValue("method_type", "JIG이용", "JIG이용");
    GridObj3.AddComboListValue("method_type", "자동", "자동");
    GridObj3.AddComboListValue("method_type", "수동", "수동");
    GridObj3.AddComboListValue("method_type", "Box", "Box");
    GridObj3.AddComboListValue("method_type", "Insert", "Insert");

    // 스크랩재생
    GridObj3.AddComboListValue("recycle_state", "", "empty");
    GridObj3.AddComboListValue("recycle_state", "有", "1");
    GridObj3.AddComboListValue("recycle_state", "無", "2");

    // 작업인원
    GridObj3.AddComboListValue("pro_level", "표준", "표준");
    GridObj3.AddComboListValue("pro_level", "기타", "기타");

    // 도금,후처리
    GridObj3.AddComboListValue("plating_type", "", "empty");
    GridObj3.AddComboListValue("plating_type", "재료도금", "재료도금");
    GridObj3.AddComboListValue("plating_type", "후도금", "후도금");
    GridObj3.AddComboListValue("plating_type", "리와인딩", "리와인딩");
    GridObj3.AddComboListValue("plating_type", "열처리", "열처리");
    GridObj3.AddComboListValue("plating_type", "세척", "세척");
    GridObj3.AddComboListValue("plating_type", "변색방지", "변색방지");
    GridObj3.AddComboListValue("plating_type", "기타", "기타");

    // 도금사양
    GridObj3.AddComboListValue("type_2", "", "empty");
    GridObj3.AddComboListValue("type_2", "Sn", "Sn");
    GridObj3.AddComboListValue("type_2", "Au", "Au");
    GridObj3.AddComboListValue("type_2", "Ag", "Ag");
    GridObj3.AddComboListValue("type_2", "3가Cr", "3가Cr");
    GridObj3.AddComboListValue("type_2", "기타", "기타");

    // 외주,구매...
    GridObj3.AddComboListValue("type_1", "", "empty");
    GridObj3.AddComboListValue("type_1", "임가공", "임가공");
    GridObj3.AddComboListValue("type_1", "구매", "구매");
    GridObj3.AddComboListValue("type_1", "OEM", "OEM");
    GridObj3.AddComboListValue("type_1", "검사", "검사");
    GridObj3.AddComboListValue("type_1", "포장", "포장");
    GridObj3.AddComboListValue("type_1", "기타", "기타");

    // 화폐단위-외주구매
    GridObj3.AddComboListValue("t_mone", "", "empty");
    GridObj3.AddComboListValue("t_mone", "KRW", "KRW");
    GridObj3.AddComboListValue("t_mone", "EUR", "EUR");
    GridObj3.AddComboListValue("t_mone", "CNY", "CNY");
    GridObj3.AddComboListValue("t_mone", "JPY", "JPY");
    GridObj3.AddComboListValue("t_mone", "USD", "USD");
    GridObj3.AddComboListValue("t_mone", "Sec", "Sec");

    // 발주조건
    GridObj3.AddComboListValue("t_order", "", "empty");
    GridObj3.AddComboListValue("t_order", "내자", "내자");
    GridObj3.AddComboListValue("t_order", "CIF", "CIF");
    GridObj3.AddComboListValue("t_order", "FOB", "FOB");
    GridObj3.AddComboListValue("t_order", "L/C", "L/C");

    // 포장유형
    GridObj3.AddComboListValue("pack_type", "", "empty");
    GridObj3.AddComboListValue("pack_type", "비닐", "비닐");
    GridObj3.AddComboListValue("pack_type", "Reel", "Reel");
    GridObj3.AddComboListValue("pack_type", "회수용", "회수용");
    GridObj3.AddComboListValue("pack_type", "Tray", "Tray");
    GridObj3.AddComboListValue("pack_type", "골판지", "골판지");

    // 전체선택
    GridObj3.SetColHDCheckBoxVisible("SELECTED", true);

    // Header 정렬
    // GridObj3.SetColCellAlign('pjt_no','center');
    // GridObj3.SetColCellAlign('pjt_name','center');
    GridObj3.SetColCellAlign('SELECTED', 'center');
    GridObj3.SetColCellAlign('CRUD', 'center');
    GridObj3.SetColCellAlign('m_co_chk', 'center');
    GridObj3.SetColCellAlign('pk_cw', 'center');
    GridObj3.SetColCellAlign('pk_cr_group', 'center');
    GridObj3.SetColCellAlign('group_no', 'left');
    GridObj3.SetColCellAlign('pro_type', 'center');
    GridObj3.SetColCellAlign('g_sel1', 'center');
    GridObj3.SetColCellAlign('g_sel2', 'center');
    GridObj3.SetColCellAlign('g_sel3', 'center');
    GridObj3.SetColCellAlign('part_name', 'left');
    GridObj3.SetColCellAlign('part_type', 'center');
    // GridObj3.SetColCellAlign('Fam_group', 'center');
    // GridObj3.SetColCellAlign('sel_group', 'center');
    GridObj3.SetColCellAlign('part_no', 'center');
    GridObj3.SetColCellAlign('net_1', 'center');
    GridObj3.SetColCellAlign('net_2', 'center');
    GridObj3.SetColCellAlign('net_3', 'center');
    GridObj3.SetColCellAlign('usage', 'center');
    GridObj3.SetColCellAlign('meterial', 'center');
    GridObj3.SetColCellAlign('t_size', 'center');
    GridObj3.SetColCellAlign('w_size', 'center');
    GridObj3.SetColCellAlign('p_size', 'center');
    GridObj3.SetColCellAlign('plating', 'center');
    GridObj3.SetColCellAlign('m_maker', 'center');
    GridObj3.SetColCellAlign('m_mone', 'center');
    GridObj3.SetColCellAlign('unitcost', 'center');
    GridObj3.SetColCellAlign('grade_name', 'center');
    GridObj3.SetColCellAlign('grade_color', 'center');
    GridObj3.SetColCellAlign('order_con', 'center');
    GridObj3.SetColCellAlign('h_weight', 'center');
    GridObj3.SetColCellAlign('h_scrap', 'center');
    GridObj3.SetColCellAlign('t_weight', 'center');
    GridObj3.SetColCellAlign('recycle', 'center');
    GridObj3.SetColCellAlign('die_no', 'center');
    GridObj3.SetColCellAlign('cavity', 'center');
    GridObj3.SetColCellAlign('m_su', 'center');
    GridObj3.SetColCellAlign('mold_c_type', 'center');
    GridObj3.SetColCellAlign('make_type', 'center');
    GridObj3.SetColCellAlign('pro_1', 'center');
    GridObj3.SetColCellAlign('ton', 'center');
    GridObj3.SetColCellAlign('spm', 'center');
    GridObj3.SetColCellAlign('method_type', 'center');
    GridObj3.SetColCellAlign('pro_level', 'center');
    GridObj3.SetColCellAlign('pro_level_txt', 'center');
    GridObj3.SetColCellAlign('line_su', 'center');
    GridObj3.SetColCellAlign('plating_type', 'center');
    GridObj3.SetColCellAlign('type_2', 'center');
    GridObj3.SetColCellAlign('plating_cost', 'center');
    GridObj3.SetColCellAlign('type_1', 'center');
    GridObj3.SetColCellAlign('t_mone', 'center');
    GridObj3.SetColCellAlign('t_order', 'center');
    GridObj3.SetColCellAlign('pack_type', 'center');
    GridObj3.SetColCellAlign('distri_cost', 'right');
    // GridObj3.SetColCellAlign('yzk_mone', 'center');
    GridObj3.SetColCellAlign('part_note', 'left');

    // 특정컬럼 비활성
    GridObj3.SetColCellActivation('group_no', 'activatenoedit');

    // t_number 타입의 컬럼을 포맷타입으로 지정한다.
    GridObj3.SetNumberFormat("mold_cost", "0,###.###");
    GridObj3.SetNumberFormat("sul_cost", "0,###.###");
    GridObj3.SetNumberFormat("type_cost", "0,###.###");
    GridObj3.SetNumberFormat("etc_cost", "0,###.###");
    GridObj3.SetNumberFormat("in_pack", "0,###.###");
    GridObj3.SetNumberFormat("out_pack", "0,###.###");

    // 컬럼 숨기기

    GridObj3.SetColHide("SELECTED", false);
    GridObj3.SetColHide("case_count_1", true);
    GridObj3.SetColHide("case_count_2", true);
    GridObj3.SetColHide("pk_cw", true);
    GridObj3.SetColHide("pk_cr_group", true);
    GridObj3.SetColHide("make", true);
    GridObj3.SetColHide("net_1", true);
    GridObj3.SetColHide("net_2", true);
    GridObj3.SetColHide("net_3", true);
    // GridObj3.SetColHide("Fam_group", true);
    // GridObj3.SetColHide("sel_group", true);
    GridObj3.SetColHide("grade_name", true);
    GridObj3.SetColHide("grade_color", true);
    GridObj3.SetColHide("distri_cost", true);
    GridObj3.SetColHide("c_unitcost", true);
    GridObj3.SetColHide("cost_report_add", true);
    GridObj3.SetColHide("car_type", true);
    GridObj3.SetColHide("p_su_chk", true);
    GridObj3.SetColHide("su_year_1", true);
    GridObj3.SetColHide("su_year_2", true);
    GridObj3.SetColHide("su_year_3", true);
    GridObj3.SetColHide("su_year_4", true);
    GridObj3.SetColHide("su_year_5", true);
    GridObj3.SetColHide("su_year_6", true);
    GridObj3.SetColHide("su_year_7", true);
    GridObj3.SetColHide("su_year_8", true);
    GridObj3.SetColHide("p_su_year_1", true);
    GridObj3.SetColHide("p_su_year_2", true);
    GridObj3.SetColHide("p_su_year_3", true);
    GridObj3.SetColHide("p_su_year_4", true);
    GridObj3.SetColHide("p_su_year_5", true);
    GridObj3.SetColHide("p_su_year_6", true);
    GridObj3.SetColHide("p_su_year_7", true);
    GridObj3.SetColHide("p_su_year_8", true);
    GridObj3.SetColHide("client_cost", true);
    GridObj3.SetColHide("ket_cost", true);
    GridObj3.SetColHide("target_cost", true);
    GridObj3.SetColHide("store", true);
    GridObj3.SetColHide("dest", true);
    GridObj3.SetColHide("royalty", true);
    GridObj3.SetColHide("information", true);
    GridObj3.SetColHide("USD_rate", true);
    GridObj3.SetColHide("EURO_rate", true);
    GridObj3.SetColHide("YEN_rate", true);
    GridObj3.SetColHide("CNY_rate", true);
    GridObj3.SetColHide("lme_ton", true);
    GridObj3.SetColHide("u_ex_rate", true);
    GridObj3.SetColHide("y_ex_rate", true);
    GridObj3.SetColHide("file_1", true);
    // GridObj3.SetColHide("file_2", true);
    // GridObj3.SetColHide("file_3", true);

    // 화면분할박스비활성
    GridObj3.bSplitBoxVisible = false;

}

function setHeader4() {
    // 그리드에 컬럼을 등록한다.
    GridObj4.AddHeader("CRUD", "", "t_text", 8, 40, false);
    GridObj4.AddHeader("pk_cw", "pk_cw", "t_text", 50, 10, false);
    GridObj4.AddHeader("pk_cr_group", "pk_cr_group", "t_text", 50, 10, false);
    GridObj4.AddHeader("group_no", "그룹", "t_text", 20, 66, false);
    GridObj4.AddHeader("pro_type", "Type", "t_text", 100, 80, false);
    GridObj4.AddHeader("g_sel1", "1", "t_imagetext", 40, 20, false);
    GridObj4.AddHeader("g_sel2", "2", "t_imagetext", 40, 20, false);
    GridObj4.AddHeader("g_sel3", "3", "t_imagetext", 40, 20, false);
    GridObj4.AddHeader("part_name", "품명", "t_text", 100, 200, false);
    GridObj4.AddHeader("usage", "U/S", "t_text", 50, 30, true);
    GridObj4.AddHeader("meterial_cost", "원재료비", "t_text", 50., 60, true);
    GridObj4.AddHeader("loss", "Loss", "t_text", 50, 40, true);
    GridObj4.AddHeader("scrap_cost", "스크랩\n판매비", "t_text", 50, 50, true);
    GridObj4.AddHeader("scrap_rate", "스크랩\n비율\n(%)", "t_text", 50, 50, true);
    GridObj4.AddHeader("m_total_cost", "합계", "t_text", 50, 50, true);
    GridObj4.AddHeader("output", "생산량\n[/Hr]", "t_text", 50, 50, true);
    GridObj4.AddHeader("rate", "임율\n[/Hr]", "t_text", 50, 50, true);
    GridObj4.AddHeader("eff_value", "효율", "t_text", 50, 50, true);
    GridObj4.AddHeader("labor_cost", "합계", "t_text", 50, 50, true);
    GridObj4.AddHeader("jun_cost", "전력비", "t_text", 50, 50, true);
    GridObj4.AddHeader("ma_depr", "기계\n감가", "t_text", 50, 50, true);
    GridObj4.AddHeader("tabalu", "타발유", "t_text", 50, 50, true);
    GridObj4.AddHeader("m_upkeep", "금형\n유지비", "t_text", 50, 50, true);
    GridObj4.AddHeader("etc_expense", "후처리", "t_text", 50, 50, true);
    GridObj4.AddHeader("pack_cost", "포장비", "t_text", 50, 50, true);
    GridObj4.AddHeader("out_cost", "외주\n단가", "t_text", 50, 50, true);
    GridObj4.AddHeader("total_expense", "소계", "t_text", 50, 50, true);
    GridObj4.AddHeader("overhead", "간접\n경비", "t_text", 50, 50, true);
    GridObj4.AddHeader("gita_cost", "기타\n경비", "t_text", 50, 50, true);
    GridObj4.AddHeader("common_cost", "경비\n합계", "t_text", 50, 50, true);
    GridObj4.AddHeader("mold_type", "감가\n형태", "t_combo", 10, 55, true);
    GridObj4.AddHeader("start_depr", "금형\n감가", "t_text", 50, 50, true);
    GridObj4.AddHeader("pro_depr", "설비\n감가", "t_text", 50, 50, true);
    GridObj4.AddHeader("etc_depr", "기타\n감가", "t_text", 50, 50, true);
    GridObj4.AddHeader("yzk_depr", "포장\n감가", "t_text", 50, 50, true);
    GridObj4.AddHeader("depr_cost", "총감가비", "t_text", 50, 50, true);
    GridObj4.AddHeader("jae_cost", "재료\n관리비", "t_text", 50, 50, true);
    GridObj4.AddHeader("ge_cost", "일반\n관리비", "t_text", 50, 50, true);
    GridObj4.AddHeader("ad_cost", "총관리비", "t_text", 50, 50, true);
    GridObj4.AddHeader("rnd_cost", "R&D", "t_text", 50, 50, true);
    GridObj4.AddHeader("qu_cost", "품질\n비용", "t_text", 50, 50, true);
    GridObj4.AddHeader("tariff", "관세", "t_text", 50, 50, true);
    GridObj4.AddHeader("royalty_cost", "로얄티", "t_text", 50, 50, true);
    GridObj4.AddHeader("dis_cost", "물류비", "t_text", 50, 50, true);
    GridObj4.AddHeader("actual_cost", "총원가\n[\/EA]", "t_text", 50, 60, true);
    GridObj4.AddHeader("earn_rate", "수익율", "t_text", 50, 60, true);
    /***************************************************************************
     * 그룹생성 및 헤더 추가 시작
     **************************************************************************/
    // 그룹생성
    GridObj4.AddGroup("PROA_TOTAL", "제품내역");
    GridObj4.AddGroup("meterial_TOTAL", "재료비[￦/EA]");
    GridObj4.AddGroup("LABOR_TOTAL", "노무비");
    GridObj4.AddGroup("EXPEN_TOTAL", "직접경비");
    GridObj4.AddGroup("DEPR_TOTAL", "감가상각비 및 관리비");

    // 그룹 헤더 추가

    // 제품Level
    GridObj4.AppendHeader("PROA_TOTAL", "group_no");
    GridObj4.AppendHeader("PROA_TOTAL", "pro_type");
    GridObj4.AppendHeader("PROA_TOTAL", "g_sel1");
    GridObj4.AppendHeader("PROA_TOTAL", "g_sel2");
    GridObj4.AppendHeader("PROA_TOTAL", "g_sel3");
    GridObj4.AppendHeader("PROA_TOTAL", "part_name");
    GridObj4.AppendHeader("PROA_TOTAL", "usage");

    // 재료비[￦/EA]
    GridObj4.AppendHeader("meterial_TOTAL", "meterial_cost");
    GridObj4.AppendHeader("meterial_TOTAL", "loss");
    GridObj4.AppendHeader("meterial_TOTAL", "scrap_cost");
    GridObj4.AppendHeader("meterial_TOTAL", "scrap_rate");
    GridObj4.AppendHeader("meterial_TOTAL", "m_total_cost");

    // 노무비
    GridObj4.AppendHeader("LABOR_TOTAL", "output");
    GridObj4.AppendHeader("LABOR_TOTAL", "rate");
    GridObj4.AppendHeader("LABOR_TOTAL", "eff_value");
    GridObj4.AppendHeader("LABOR_TOTAL", "labor_cost");

    // 직접경비
    GridObj4.AppendHeader("EXPEN_TOTAL", "jun_cost");
    GridObj4.AppendHeader("EXPEN_TOTAL", "ma_depr");
    GridObj4.AppendHeader("EXPEN_TOTAL", "tabalu");
    GridObj4.AppendHeader("EXPEN_TOTAL", "m_upkeep");
    GridObj4.AppendHeader("EXPEN_TOTAL", "etc_expense");
    GridObj4.AppendHeader("EXPEN_TOTAL", "pack_cost");
    GridObj4.AppendHeader("EXPEN_TOTAL", "out_cost");
    GridObj4.AppendHeader("EXPEN_TOTAL", "total_expense");

    // 감가상각비 및 관리비
    GridObj4.AppendHeader("DEPR_TOTAL", "mold_type");
    GridObj4.AppendHeader("DEPR_TOTAL", "start_depr");
    GridObj4.AppendHeader("DEPR_TOTAL", "pro_depr");
    GridObj4.AppendHeader("DEPR_TOTAL", "etc_depr");
    GridObj4.AppendHeader("DEPR_TOTAL", "yzk_depr");
    GridObj4.AppendHeader("DEPR_TOTAL", "depr_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "jae_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "ge_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "ad_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "rnd_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "qu_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "tariff");
    GridObj4.AppendHeader("DEPR_TOTAL", "royalty_cost");
    GridObj4.AppendHeader("DEPR_TOTAL", "dis_cost");

    /***************************************************************************
     * 그룹에 헤더 추가 끝
     **************************************************************************/

    // AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj4.BoundHeader();

    // 저장모드를 사용해 서버사이드와 통신한다.
    GridObj4.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

    // 이미지리스트에 이미지 URL을 추가한다.
    GridObj4.AddImageList("g_sel1", "/plm/jsp/cost/images/common/add.jpg");
    GridObj4.AddImageList("g_sel2", "/plm/jsp/cost/images/common/add.jpg");
    GridObj4.AddImageList("g_sel3", "/plm/jsp/cost/images/common/add.jpg");

    // 감가형태
    GridObj4.AddComboListValue("mold_type", "", "empty");
    GridObj4.AddComboListValue("mold_type", "일반", "일반");
    GridObj4.AddComboListValue("mold_type", "판매", "판매");

    /***************************************************************************
     * Header 정렬 및 t_number 타입의 컬럼 포맷타입으로 지정 시작
     **************************************************************************/
    // Header 정렬
    GridObj4.SetColCellAlign('CRUD', 'center');
    GridObj4.SetColCellAlign('pk_cw', 'center');
    GridObj4.SetColCellAlign('pk_cr_group', 'center');
    GridObj4.SetColCellAlign('group_no', 'left');
    GridObj4.SetColCellAlign('pro_type', 'center');
    GridObj4.SetColCellAlign('g_sel1', 'center');
    GridObj4.SetColCellAlign('g_sel2', 'center');
    GridObj4.SetColCellAlign('g_sel3', 'center');
    GridObj4.SetColCellAlign('part_name', 'left');
    GridObj4.SetColCellAlign('usage', 'center');

    GridObj4.SetColCellAlign('meterial_cost', 'right');
    GridObj4.SetColCellAlign('loss', 'right');
    GridObj4.SetColCellAlign('scrap_cost', 'right');
    GridObj4.SetColCellAlign('scrap_rate', 'right');
    GridObj4.SetColCellAlign('m_total_cost', 'right');
    GridObj4.SetColCellAlign('output', 'right');
    GridObj4.SetColCellAlign('rate', 'right');
    GridObj4.SetColCellAlign('eff_value', 'right');
    GridObj4.SetColCellAlign('labor_cost', 'right');
    GridObj4.SetColCellAlign('jun_cost', 'right');
    GridObj4.SetColCellAlign('ma_depr', 'right');
    GridObj4.SetColCellAlign('tabalu', 'right');
    GridObj4.SetColCellAlign('m_upkeep', 'right');
    GridObj4.SetColCellAlign('etc_expense', 'right');
    GridObj4.SetColCellAlign('pack_cost', 'right');
    GridObj4.SetColCellAlign('out_cost', 'right');
    GridObj4.SetColCellAlign('total_expense', 'right');
    GridObj4.SetColCellAlign('overhead', 'right');
    GridObj4.SetColCellAlign('gita_cost', 'right');
    GridObj4.SetColCellAlign('common_cost', 'right');
    GridObj4.SetColCellAlign('mold_type', 'right');
    GridObj4.SetColCellAlign('start_depr', 'right');
    GridObj4.SetColCellAlign('pro_depr', 'right');
    GridObj4.SetColCellAlign('etc_depr', 'right');
    GridObj4.SetColCellAlign('yzk_depr', 'right');
    GridObj4.SetColCellAlign('jae_cost', 'right');
    GridObj4.SetColCellAlign('ge_cost', 'right');
    GridObj4.SetColCellAlign('rnd_cost', 'right');
    GridObj4.SetColCellAlign('qu_cost', 'right');
    GridObj4.SetColCellAlign('tariff', 'right');
    GridObj4.SetColCellAlign('royalty_cost', 'right');
    GridObj4.SetColCellAlign('dis_cost', 'right');
    GridObj4.SetColCellAlign('actual_cost', 'right');

    // 특정컬럼 비활성
    GridObj4.SetColCellActivation('group_no', 'activatenoedit');

    // t_number 포맷타입 지정
    /*
     * GridObj4.SetNumberFormat("meterial_cost", "0,###.###");
     * GridObj4.SetNumberFormat("loss", "0,###.###");
     * GridObj4.SetNumberFormat("scrap_cost", "0,###.###");
     * GridObj4.SetNumberFormat("scrap_rate", "0,###.###");
     * GridObj4.SetNumberFormat("m_total_cost", "0,###.###");
     * GridObj4.SetNumberFormat("output", "0,###.###");
     * GridObj4.SetNumberFormat("rate", "0,###.###");
     * GridObj4.SetNumberFormat("eff_value", "0,###.###");
     * GridObj4.SetNumberFormat("labor_cost", "0,###.###");
     * GridObj4.SetNumberFormat("jun_cost", "0,###.###");
     * GridObj4.SetNumberFormat("ma_depr", "0,###.###");
     * GridObj4.SetNumberFormat("tabalu", "0,###.###");
     * GridObj4.SetNumberFormat("m_upkeep", "0,###.###");
     * GridObj4.SetNumberFormat("etc_expense", "0,###.###");
     * GridObj4.SetNumberFormat("pack_cost", "0,###.###");
     * GridObj4.SetNumberFormat("out_cost", "0,###.###");
     * GridObj4.SetNumberFormat("total_expense", "0,###.###");
     * GridObj4.SetNumberFormat("overhead", "0,###.###");
     * GridObj4.SetNumberFormat("gita_cost", "0,###.###");
     * GridObj4.SetNumberFormat("common_cost", "0,###.###");
     * GridObj4.SetNumberFormat("mold_type", "0,###.###");
     * GridObj4.SetNumberFormat("start_depr", "0,###.###");
     * GridObj4.SetNumberFormat("pro_depr", "0,###.###");
     * GridObj4.SetNumberFormat("etc_depr", "0,###.###");
     * GridObj4.SetNumberFormat("yzk_depr", "0,###.###");
     * GridObj4.SetNumberFormat("jae_cost", "0,###.###");
     * GridObj4.SetNumberFormat("ge_cost", "0,###.###");
     * GridObj4.SetNumberFormat("rnd_cost", "0,###.###");
     * GridObj4.SetNumberFormat("qu_cost", "0,###.###");
     * GridObj4.SetNumberFormat("tariff", "0,###.###");
     * GridObj4.SetNumberFormat("royalty_cost", "0,###.###");
     * GridObj4.SetNumberFormat("dis_cost", "0,###.###");
     * GridObj4.SetNumberFormat("actual_cost", "0,###.###");
     */

    /***************************************************************************
     * Header 정렬 비활성
     **************************************************************************/
    GridObj4.SetColCellSortEnable('group_no', 'false');
    GridObj4.SetColCellSortEnable('pro_type', 'false');
    GridObj4.SetColCellSortEnable('g_sel1', 'false');
    GridObj4.SetColCellSortEnable('g_sel2', 'false');
    GridObj4.SetColCellSortEnable('g_sel3', 'false');
    GridObj4.SetColCellSortEnable('part_name', 'false');
    GridObj4.SetColCellSortEnable('usage', 'false');
    GridObj4.SetColCellSortEnable('meterial_cost', 'false');
    GridObj4.SetColCellSortEnable('loss', 'false');
    GridObj4.SetColCellSortEnable('scrap_cost', 'false');
    GridObj4.SetColCellSortEnable('scrap_rate', 'false');
    GridObj4.SetColCellSortEnable('m_total_cost', 'false');
    GridObj4.SetColCellSortEnable('output', 'false');
    GridObj4.SetColCellSortEnable('rate', 'false');
    GridObj4.SetColCellSortEnable('eff_value', 'false');
    GridObj4.SetColCellSortEnable('labor_cost', 'false');
    GridObj4.SetColCellSortEnable('jun_cost', 'false');
    GridObj4.SetColCellSortEnable('ma_depr', 'false');
    GridObj4.SetColCellSortEnable('tabalu', 'false');
    GridObj4.SetColCellSortEnable('m_upkeep', 'false');
    GridObj4.SetColCellSortEnable('etc_expense', 'false');
    GridObj4.SetColCellSortEnable('pack_cost', 'false');
    GridObj4.SetColCellSortEnable('out_cost', 'false');
    GridObj4.SetColCellSortEnable('total_expense', 'false');
    GridObj4.SetColCellSortEnable('overhead', 'false');
    GridObj4.SetColCellSortEnable('gita_cost', 'false');
    GridObj4.SetColCellSortEnable('common_cost', 'false');
    GridObj4.SetColCellSortEnable('mold_type', 'false');
    GridObj4.SetColCellSortEnable('start_depr', 'false');
    GridObj4.SetColCellSortEnable('pro_depr', 'false');
    GridObj4.SetColCellSortEnable('etc_depr', 'false');
    GridObj4.SetColCellSortEnable('yzk_depr', 'false');
    GridObj4.SetColCellSortEnable('jae_cost', 'false');
    GridObj4.SetColCellSortEnable('ge_cost', 'false');
    GridObj4.SetColCellSortEnable('rnd_cost', 'false');
    GridObj4.SetColCellSortEnable('qu_cost', 'false');
    GridObj4.SetColCellSortEnable('tariff', 'false');
    GridObj4.SetColCellSortEnable('royalty_cost', 'false');
    GridObj4.SetColCellSortEnable('dis_cost', 'false');
    GridObj4.SetColCellSortEnable('actual_cost', 'false');
    GridObj4.SetColCellSortEnable('tariff', 'false');

    // 컬럼 숨기기
    GridObj4.SetColHide("pk_cw", true);
    GridObj4.SetColHide("pk_cr_group", true);

    // 화면분할박스비활성
    GridObj4.bSplitBoxVisible = false;

}

function setHeader5() {
    // 그리드에 컬럼을 등록한다.

    GridObj5.AddHeader("group_no", "그룹", "t_text", 20, 66, false);
    GridObj5.AddHeader("part_name", "품명", "t_text", 100, 200, false);
    GridObj5.AddHeader("m_total_cost", "재료비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("labor_cost", "노무비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("common_cost", "제조경비", "t_number", 10.3, 65, false);
    GridObj5.AddHeader("ad_cost", "관리비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_depr_cost", "감가비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_actual_cost", "총원가", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_earn_rate", "수익율", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("m_total_cost_m", "재료비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("labor_cost_m", "노무비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("common_cost_m", "제조경비", "t_number", 10.3, 65, false);
    GridObj5.AddHeader("ad_cost_m", "관리비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_depr_cost_m", "감가비", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_actual_cost_m", "총원가", "t_number", 10.3, 60, false);
    GridObj5.AddHeader("g_earn_rate_m", "수익율", "t_number", 10.3, 60, false);

    /***************************************************************************
     * 그룹생성 및 헤더 추가 시작
     **************************************************************************/
    // 그룹생성
    GridObj5.AddGroup("Cost_O", "요청서");
    GridObj5.AddGroup("Cost_M", "변경");

    // 그룹 헤더 추가

    GridObj5.AppendHeader("Cost_O", "m_total_cost");
    GridObj5.AppendHeader("Cost_O", "labor_cost");
    GridObj5.AppendHeader("Cost_O", "common_cost");
    GridObj5.AppendHeader("Cost_O", "ad_cost");
    GridObj5.AppendHeader("Cost_O", "g_depr_cost");
    GridObj5.AppendHeader("Cost_O", "g_actual_cost");
    GridObj5.AppendHeader("Cost_O", "g_earn_rate");

    GridObj5.AppendHeader("Cost_M", "m_total_cost_m");
    GridObj5.AppendHeader("Cost_M", "labor_cost_m");
    GridObj5.AppendHeader("Cost_M", "common_cost_m");
    GridObj5.AppendHeader("Cost_M", "ad_cost_m");
    GridObj5.AppendHeader("Cost_M", "g_depr_cost_m");
    GridObj5.AppendHeader("Cost_M", "g_actual_cost_m");
    GridObj5.AppendHeader("Cost_M", "g_earn_rate_m");

    // AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj5.BoundHeader();

    // t_number 타입의 컬럼을 포맷타입으로 지정한다.
    GridObj5.SetNumberFormat("m_total_cost", "0,###.###");
    GridObj5.SetNumberFormat("labor_cost", "0,###.###");
    GridObj5.SetNumberFormat("common_cost", "0,###.###");
    GridObj5.SetNumberFormat("ad_cost", "0,###.###");
    GridObj5.SetNumberFormat("g_depr_cost", "0,###.###");
    GridObj5.SetNumberFormat("g_actual_cost", "0,###.###");
    GridObj5.SetNumberFormat("g_earn_rate", "###.##%");

    GridObj5.SetNumberFormat("m_total_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("labor_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("common_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("ad_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("g_depr_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("g_actual_cost_m", "0,###.###");
    GridObj5.SetNumberFormat("g_earn_rate_m", "###.##%");

    /***************************************************************************
     * Header 정렬 비활성
     **************************************************************************/
    GridObj5.SetColCellSortEnable('group_no', 'false');
    GridObj5.SetColCellSortEnable('part_name', 'false');
    GridObj5.SetColCellSortEnable('m_total_cost', 'false');
    GridObj5.SetColCellSortEnable('labor_cost', 'false');
    GridObj5.SetColCellSortEnable('common_cost', 'false');
    GridObj5.SetColCellSortEnable('ad_cost', 'false');
    GridObj5.SetColCellSortEnable('g_depr_cost', 'false');
    GridObj5.SetColCellSortEnable('g_actual_cost', 'false');
    GridObj5.SetColCellSortEnable('g_earn_rate', 'false');
    GridObj5.SetColCellSortEnable('m_total_cost_m', 'false');
    GridObj5.SetColCellSortEnable('labor_cost_m', 'false');
    GridObj5.SetColCellSortEnable('common_cost_m', 'false');
    GridObj5.SetColCellSortEnable('ad_cost_m', 'false');
    GridObj5.SetColCellSortEnable('g_depr_cost_m', 'false');
    GridObj5.SetColCellSortEnable('g_actual_cost_m', 'false');
    GridObj5.SetColCellSortEnable('g_earn_rate_m', 'false');
}
function setHeader6() {
    // 그리드에 컬럼을 등록한다.
    GridObj6.AddHeader("SELECTED", "선택", "t_checkbox", 2, 40, true);
    GridObj6.AddHeader("pk_cw", "pk_cw", "t_text", 50, 10, false);
    GridObj6.AddHeader("pk_cr_group", "pk_cr_group", "t_text", 50, 10, false);
    GridObj6.AddHeader("case_type_admin", "case순서", "t_combo", 20, 80, true);
    GridObj6.AddHeader("part_name", "품명", "t_text", 100, 300, false);
    GridObj6.AddHeader("actual_cost", "총원가", "t_text", 100, 80, false);
    GridObj6.AddHeader("earn_rate", "수익율", "t_text", 20, 80, false);
    GridObj6.AddHeader("case_infor", "변경내용", "t_text", 500, 300, true);
    GridObj6.AddHeader("group_no", "그룹", "t_text", 20, 66, true);
    //GridObj6.AddHeader("combo_test", "콤보", "t_combo", 20, 80, true);
    // AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj6.BoundHeader();

    /***************************************************************************
     * Header 정렬 비활성
     **************************************************************************/
    GridObj6.SetColCellSortEnable('pk_cw', 'false');
    GridObj6.SetColCellSortEnable('pk_cr_group', 'false');
    GridObj6.SetColCellSortEnable('case_type_admin', 'false');
    GridObj6.SetColCellSortEnable('part_name', 'false');
    GridObj6.SetColCellSortEnable('actual_cost', 'false');
    GridObj6.SetColCellSortEnable('earn_rate', 'false');
    GridObj6.SetColCellSortEnable('case_infor', 'false');
    GridObj6.SetColCellSortEnable('group_no', 'false');
    //GridObj6.SetColCellSortEnable('combo_test', 'false');

    // 컬럼 숨기기
    GridObj6.SetColHide("pk_cw", true);
    GridObj6.SetColHide("pk_cr_group", true);
    GridObj6.SetColHide("group_no", true);

}

function setHeader7() {
    GridObj7.AddHeader("SELECTED", "", "t_checkbox", 2, 30, true);
    GridObj7.BoundHeader();
}

/*******************************************************************************
 * 조회 3
 ******************************************************************************/
function doQuery3(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj3.SetParam("mode", "search3");
    GridObj3.SetParam("group_no", val);
    GridObj3.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj3.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj3.SetParam("data_type", document.forms[0].data_type.value);
    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj3.DoQuery(servlet_url);
}

/*******************************************************************************
 * 조회 4
 ******************************************************************************/
function doQuery4(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj4.SetParam("mode", "search4");
    GridObj4.SetParam("group_no", val);
    GridObj4.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj4.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj4.SetParam("data_type", document.forms[0].data_type.value);

    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj4.DoQuery(servlet_url);
}

/*******************************************************************************
 * 조회 6
 ******************************************************************************/
function doQuery6(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj6.SetParam("mode", "search6");
    GridObj6.SetParam("group_no", val);
    GridObj6.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj6.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj6.SetParam("data_type", document.forms[0].data_type.value);

    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj6.DoQuery(servlet_url);
}

/* 일반팝업을 중앙에 위치도록 할때 */
function popUpOpen(url, title, width, height) {
    if (title == '')
        title = 'Popup_Open';
    if (width == '')
        width = 540;
    if (height == '')
        height = 500;
    var left = "";
    var top = "";

    // 화면 가운데로 배치
    var dim = new Array(2);

    dim = CenterWindow(height, width);
    top = dim[0];
    left = dim[1];

    var toolbar = 'no';
    var menubar = 'no';
    var status = 'no';
    var scrollbars = 'no';
    var resizable = 'no';
    var code_search = window.open(url, title, 'left=' + left + ', top=' + top
            + ',width=' + width + ',height=' + height + ', toolbar=' + toolbar
            + ', menubar=' + menubar + ', status=' + status + ', scrollbars='
            + scrollbars + ', resizable=' + resizable);
    code_search.focus();
    return code_search;
}

function CenterWindow(height, width) {
    var outx = screen.height;
    var outy = screen.width;
    var x = (outx - height) / 2;
    var y = (outy - width) / 2;
    dim = new Array(2);
    dim[0] = x;
    dim[1] = y;

    return dim;
}

/*******************************************************************************
 * 더블클릭이벤트
 ******************************************************************************/
function GridCellDblClick3(strColumnKey, nRow) {

    var GridObj3 = document.WiseGrid3;
    if (strColumnKey == "meterial") {
        var pro_type = GridObj3.GetCellHiddenValue("pro_type", nRow);
        var meterial = GridObj3.GetCellValue("meterial", nRow);
        var m_maker = GridObj3.GetCellValue("m_maker", nRow);
        var make = GridObj3.GetCellValue("make", nRow);
        var plating = GridObj3.GetCellValue("plating", nRow);
        var t_size = GridObj3.GetCellValue("t_size", nRow);
        var w_size = GridObj3.GetCellValue("w_size", nRow);
        var p_size = GridObj3.GetCellValue("p_size", nRow);
        var m_mone = GridObj3.GetCellValue("m_mone", nRow);
        var unitcost = GridObj3.GetCellValue("unitcost", nRow);
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

        // popUpOpen("./cost_grade.asp?page_name=work&k="+nRow+"&meterial="+meterial+"&make="+make+"&m_maker="+m_maker+"&plating="+plating+"&t_size="+t_size+"&w_size="+w_size+"&p_size="+p_size+"&m_mone="+m_mone+"&unitcost_txt="+unitcost+"&order_con="+order_con+"&h_weight="+h_weight+"&h_scrap="+h_scrap+"&t_weight="+t_weight+"&recycle="+recycle+"&grade_name="+grade_name+"&grade_color="+grade_color+"&p_pro_type="+pro_type+"&sul_cost="+sul_cost+"&type_2="+type_2+"&plating_cost="+plating_cost,
        // "cost_grade", 970, 220);
        popUpOpen("/plm/jsp/cost/common/cost_grade.jsp?page_name=work&k="
                + nRow + "&meterial=" + meterial + "&make=" + make
                + "&m_maker=" + m_maker + "&plating=" + plating + "&t_size="
                + t_size + "&w_size=" + w_size + "&p_size=" + p_size
                + "&m_mone=" + m_mone + "&unitcost_txt=" + unitcost
                + "&order_con=" + order_con + "&h_weight=" + h_weight
                + "&h_scrap=" + h_scrap + "&t_weight=" + t_weight + "&recycle="
                + recycle + "&grade_name=" + grade_name + "&grade_color="
                + grade_color + "&p_pro_type=" + pro_type + "&sul_cost="
                + sul_cost + "&type_2=" + type_2 + "&plating_cost="
                + plating_cost, "cost_grade", 970, 220);
    }

}
/*******************************************************************************
 * 서버와의 통신이 정상적으로 완료되면 발생한다.
 ******************************************************************************/

function GridEndQuery3() {
    // 서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj3.GetParam("mode");
    var rawdata = "";

    if (mode == "search3") {
        document.forms[0].car_type.value = GridObj3.GetCellValue("car_type", 0);
        document.forms[0].p_su_chk.value = GridObj3.GetCellValue("p_su_chk", 0);
        document.forms[0].su_year_1.value = GridObj3.GetCellValue("su_year_1",
                0);
        document.forms[0].su_year_2.value = GridObj3.GetCellValue("su_year_2",
                0);
        document.forms[0].su_year_3.value = GridObj3.GetCellValue("su_year_3",
                0);
        document.forms[0].su_year_4.value = GridObj3.GetCellValue("su_year_4",
                0);
        document.forms[0].su_year_5.value = GridObj3.GetCellValue("su_year_5",
                0);
        document.forms[0].su_year_6.value = GridObj3.GetCellValue("su_year_6",
                0);
        document.forms[0].su_year_7.value = GridObj3.GetCellValue("su_year_7",
                0);
        document.forms[0].su_year_8.value = GridObj3.GetCellValue("su_year_8",
                0);
        document.forms[0].p_su_year_1.value = GridObj3.GetCellValue(
                "p_su_year_1", 0);
        document.forms[0].p_su_year_2.value = GridObj3.GetCellValue(
                "p_su_year_2", 0);
        document.forms[0].p_su_year_3.value = GridObj3.GetCellValue(
                "p_su_year_3", 0);
        document.forms[0].p_su_year_4.value = GridObj3.GetCellValue(
                "p_su_year_4", 0);
        document.forms[0].p_su_year_5.value = GridObj3.GetCellValue(
                "p_su_year_5", 0);
        document.forms[0].p_su_year_6.value = GridObj3.GetCellValue(
                "p_su_year_6", 0);
        document.forms[0].p_su_year_7.value = GridObj3.GetCellValue(
                "p_su_year_7", 0);
        document.forms[0].p_su_year_8.value = GridObj3.GetCellValue(
                "p_su_year_8", 0);
        document.forms[0].client_cost.value = GridObj3.GetCellValue(
                "client_cost", 0);
        document.forms[0].ket_cost.value = GridObj3.GetCellValue("ket_cost", 0);
        document.forms[0].target_cost.value = GridObj3.GetCellValue(
                "target_cost", 0);
        document.forms[0].store.value = GridObj3.GetCellValue("store", 0);
        document.forms[0].dest.value = GridObj3.GetCellValue("dest", 0);
        document.forms[0].royalty.value = GridObj3.GetCellValue("royalty", 0);
        document.forms[0].information.value = GridObj3.GetCellValue(
                "information", 0);
        document.forms[0].USD_rate.value = GridObj3.GetCellValue("USD_rate", 0);
        document.forms[0].EURO_rate.value = GridObj3.GetCellValue("EURO_rate",
                0);
        document.forms[0].YEN_rate.value = GridObj3.GetCellValue("YEN_rate", 0);
        document.forms[0].CNY_rate.value = GridObj3.GetCellValue("CNY_rate", 0);
        document.forms[0].lme_ton.value = GridObj3.GetCellValue("lme_ton", 0);
        document.forms[0].u_ex_rate.value = GridObj3.GetCellValue("u_ex_rate",
                0);
        document.forms[0].y_ex_rate.value = GridObj3.GetCellValue("y_ex_rate",
                0);
        document.forms[0].cost_report_add.value = GridObj3.GetCellValue(
                "cost_report_add", 0);

    }

    if (mode == "save") {

        if ((GridObj3.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
        } else {
            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }
        DB_call_4(pageNo);

    }
    if (mode == "modi") {

        //var test_txt = GridObj3.GetParam("test_txt");

        //alert(test_txt)

        if ((GridObj3.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
            alert("저장되었습니다.");
        } else {
            var error_msg = GridObj3.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }
        doQuery3(pageNo);
        doQuery4(pageNo);
        doQuery6(pageNo);

    }
}
function GridEndQuery4() {
    // 서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj4.GetParam("mode");
    if (mode == "save") {
        if ((GridObj4.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
            alert("저장되었습니다.");
        } else {
            var error_msg = GridObj4.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }

        doQuery3(pageNo);
        doQuery4(pageNo);
        doQuery6(pageNo);
    }else if(mode == "save_case"){
        if ((GridObj4.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
            alert("저장되었습니다.");
        } else {
            var error_msg = GridObj4.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }
        doQuery3_case(document.forms[0].case_type_admin_1.value ,document.forms[0].case_group_no_1.value);
        doQuery4_case(document.forms[0].case_type_admin_1.value ,document.forms[0].case_group_no_1.value);
        doQuery6_case(pageNo);
    }

    /***************************************************************************
     * 특정 Cell 색상변경
     **************************************************************************/
    for ( var j = 0; j < GridObj4.getRowCount; j++) {
        if (j % 2 == 0) {
            GridObj4.SetCellBgColor('scrap_rate', j, '255|214|89');
            GridObj4.SetCellBgColor('eff_value', j, '255|214|89');
            GridObj4.SetCellBgColor('pack_cost', j, '255|214|89');
            GridObj4.SetCellBgColor('gita_cost', j, '255|214|89');
            GridObj4.SetCellBgColor('qu_cost', j, '255|214|89');
            GridObj4.SetCellBgColor('dis_cost', j, '255|214|89');
        }
    }

}
function GridEndQuery6() {
    // 서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj6.GetParam("mode");
    if (mode == "delete") {
        if ((GridObj6.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
            alert("삭제되었습니다.");
        } else {
            var error_msg = GridObj6.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }
        doQuery3(pageNo);
        doQuery4(pageNo);
        doQuery6(pageNo);
    }

    if (mode == "case_update") {
        if ((GridObj6.GetStatus() == "true")) { // 서버에서 전송한 상태코드를 가져온다.
            alert("저장되었습니다.");
        } else {
            var error_msg = GridObj6.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert(error_msg);
        }
        doQuery3(pageNo);
        doQuery4(pageNo);
        doQuery6(pageNo);
    }


}

function GridEndQuery_hidden() {
    var mode = GridObj7.GetParam("mode");

    if (mode == "cost_acc") {
        if (GridObj7.GetStatus() == "true") {// 서버에서 전송한 상태코드를 가져온다.

            // 서버에서 insert_data 셋팅한 파라미터를 가져온다.
            var cost_acc = GridObj7.GetParam("cost_acc");
            // var test_txt = GridObj7.GetParam("test_txt");

            // fieldset에 결과 값을 보이게 한다.
            alert("산출작업이 완료되었습니다.");
            // alert(test_txt);

            if (0 == GridObj5.getRowCount()) { // 그리드5에 원가산출을 이미 했는지 체크
                setCostAcc(cost_acc); // 간이산출 그리드에 결과값 넣기
            } else {
                setCostAccM(cost_acc); // 간이산출 그리드에 결과값 넣기
            }

            // document.forms[0].result.innerHTML=
            // '<legend>간이산출</legend>'+cost_acc;

             var rawdata = document.WiseGrid_hidden.GetRecvRawData("WISEGRID4");

             document.WiseGrid4.SetGridRawData(rawdata,false);

            // document.WiseGrid4.SetGridRawData(document.WiseGrid_hidden.GetRecvRawData("WISEGRID4"), false);

        } else {
            var error_msg = GridObj7.GetMessage(); // 서버에서 전송한 상태코드값이 false라면
                                                    // 에러메세지를 가져온다.
            alert("에러"+error_msg);
        }
    }
}

/*******************************************************************************
 * setCostAcc
 ******************************************************************************/
function setCostAcc(cost_acc) {
    // gridDeleteRow();
    var arrCostAcc = eval(cost_acc);
    var temp = arrCostAcc[0].group_no;
    var cnt = 0;

    temp = temp.substring(0, 4);

    for ( var ii = 0; ii < arrCostAcc.length; ii++) {
        if (temp != arrCostAcc[ii].group_no.substring(0, 4)) {
            cnt = ii;
            temp = arrCostAcc[ii].group_no.substring(0, 4);
        }

        if (arrCostAcc[ii].group_no.match("sum")) {
            try {
                GridObj5.insertRow(cnt);

                GridObj5.SetCellValue("group_no", cnt, arrCostAcc[ii].group_no
                        .substring(0, 4));
                // GridObj5.SetCellValue( "group_no" , cnt,
                // arrCostAcc[ii].group_no );
                GridObj5.SetCellValue("part_name", cnt,
                        arrCostAcc[ii].part_name);
                GridObj5.SetCellValue("m_total_cost", cnt,
                        arrCostAcc[ii].m_total_cost);
                GridObj5.SetCellValue("labor_cost", cnt,
                        arrCostAcc[ii].labor_cost);
                GridObj5.SetCellValue("common_cost", cnt,
                        arrCostAcc[ii].common_cost);
                GridObj5.SetCellValue("ad_cost", cnt, arrCostAcc[ii].ad_cost);
                GridObj5.SetCellValue("g_depr_cost", cnt,
                        arrCostAcc[ii].g_depr_cost);
                GridObj5.SetCellValue("g_actual_cost", cnt,
                        arrCostAcc[ii].g_actual_cost);
                GridObj5.SetCellValue("g_earn_rate", cnt,
                        arrCostAcc[ii].g_earn_rate);

            } catch (e) {
                GridObj5.AddRow();

                GridObj5.SetCellValue("group_no", cnt, arrCostAcc[ii].group_no
                        .substring(0, 4));
                GridObj5
                        .SetCellValue("part_name", ii, arrCostAcc[ii].part_name);
                GridObj5.SetCellValue("m_total_cost", ii,
                        arrCostAcc[ii].m_total_cost);
                GridObj5.SetCellValue("labor_cost", ii,
                        arrCostAcc[ii].labor_cost);
                GridObj5.SetCellValue("common_cost", ii,
                        arrCostAcc[ii].common_cost);
                GridObj5.SetCellValue("ad_cost", ii, arrCostAcc[ii].ad_cost);
                GridObj5.SetCellValue("g_depr_cost", ii,
                        arrCostAcc[ii].g_depr_cost);
                GridObj5.SetCellValue("g_actual_cost", ii,
                        arrCostAcc[ii].g_actual_cost);
                GridObj5.SetCellValue("g_earn_rate", ii,
                        arrCostAcc[ii].g_earn_rate);
            }

            GridObj5.SetCellBgColor('m_total_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('labor_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('common_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('ad_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('g_depr_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('g_actual_cost', cnt, '233|230|238');
            GridObj5.SetCellBgColor('g_earn_rate', cnt, '233|230|238');

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

            if (arrCostAcc[ii].group_no.length == 4) {
                GridObj5.SetCellValue("group_no", ii, "조립");
            } else {
                GridObj5.SetCellValue("group_no", ii, arrCostAcc[ii].group_no);
            }
            GridObj5.SetCellValue("part_name", ii, arrCostAcc[ii].part_name);
            GridObj5.SetCellValue("m_total_cost", ii,
                    arrCostAcc[ii].m_total_cost);
            GridObj5.SetCellValue("labor_cost", ii, arrCostAcc[ii].labor_cost);
            GridObj5
                    .SetCellValue("common_cost", ii, arrCostAcc[ii].common_cost);
            GridObj5.SetCellValue("ad_cost", ii, arrCostAcc[ii].ad_cost);
            GridObj5
                    .SetCellValue("g_depr_cost", ii, arrCostAcc[ii].g_depr_cost);
            GridObj5.SetCellValue("g_actual_cost", ii,
                    arrCostAcc[ii].g_actual_cost);
            GridObj5
                    .SetCellValue("g_earn_rate", ii, arrCostAcc[ii].g_earn_rate);
        }
    }
}

/*******************************************************************************
 * setCostAccM
 ******************************************************************************/
function setCostAccM(cost_acc) {
    var arrCostAcc = eval(cost_acc);
    var temp = arrCostAcc[0].group_no;

    temp = temp.substring(0, 4);

    for ( var ii = 0; ii < arrCostAcc.length; ii++) {
        if (temp != arrCostAcc[ii].group_no.substring(0, 4)) {
            temp = arrCostAcc[ii].group_no.substring(0, 4);
        }

        if (arrCostAcc[ii].group_no.match("sum")) {
            try {
                GridObj5.SetCellValue("m_total_cost_m", 0,
                        arrCostAcc[ii].m_total_cost);
                GridObj5.SetCellValue("labor_cost_m", 0,
                        arrCostAcc[ii].labor_cost);
                GridObj5.SetCellValue("common_cost_m", 0,
                        arrCostAcc[ii].common_cost);
                GridObj5.SetCellValue("ad_cost_m", 0, arrCostAcc[ii].ad_cost);
                GridObj5.SetCellValue("g_depr_cost_m", 0,
                        arrCostAcc[ii].g_depr_cost);
                GridObj5.SetCellValue("g_actual_cost_m", 0,
                        arrCostAcc[ii].g_actual_cost);
                GridObj5.SetCellValue("g_earn_rate_m", 0,
                        arrCostAcc[ii].g_earn_rate);

                GridObj5.SetCellFontBold('m_total_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('labor_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('common_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('ad_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('g_depr_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('g_actual_cost_m', 0, 'true');
                GridObj5.SetCellFontBold('g_earn_rate_m', 0, 'true');

            } catch (e) {
                alert(e);
            }
            GridObj5.SetCellBgColor('m_total_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('labor_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('common_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('ad_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('g_depr_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('g_actual_cost_m', 0, '247|217|211');
            GridObj5.SetCellBgColor('g_earn_rate_m', 0, '247|217|211');

        } else {

            GridObj5.SetCellValue("m_total_cost_m", ii + 1,
                    arrCostAcc[ii].m_total_cost);
            GridObj5.SetCellValue("labor_cost_m", ii + 1,
                    arrCostAcc[ii].labor_cost);
            GridObj5.SetCellValue("common_cost_m", ii + 1,
                    arrCostAcc[ii].common_cost);
            GridObj5.SetCellValue("ad_cost_m", ii + 1, arrCostAcc[ii].ad_cost);
            GridObj5.SetCellValue("g_depr_cost_m", ii + 1,
                    arrCostAcc[ii].g_depr_cost);
            GridObj5.SetCellValue("g_actual_cost_m", ii + 1,
                    arrCostAcc[ii].g_actual_cost);
            GridObj5.SetCellValue("g_earn_rate_m", ii + 1,
                    arrCostAcc[ii].g_earn_rate);
        }
    }
}

function gridDeleteRow() {
    var jj = GridObj5.getRowCount();

    for ( var ii = 0; ii < jj; ii++) {
        GridObj5.DeleteRow(0);
    }
}
/*******************************************************************************
 * 원가산출(간이산출)
 ******************************************************************************/
function cost_acc_call() {
    var servlet_url = "/plm/servlet/e3ps/CostAccTestServlet";
    // WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj7.SetParam("mode", "cost_acc");
    GridObj7.SetParam("page_name", "work");
    GridObj7.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj7.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj7.SetParam("table_row", document.forms[0].table_row.value);
    GridObj3.SetParam("table_row2", GridObj4.getRowCount());
    // GridObj7.SetParam("rev_no", document.forms[0].rev_no.value);

    GridObj7.AddGridRawData("WISEGRID_DATA4", document.WiseGrid4
            .GetGridRawData("WISEGRIDDATA_ALL"));
    GridObj7.AddGridRawData("WISEGRID_DATA3", document.WiseGrid3
            .GetGridRawData("WISEGRIDDATA_ALL"));

    // WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj7.DoQuery(servlet_url, "WISEGRIDDATA_ALL");
}
/*******************************************************************************
 * 변경내용 초기화
 ******************************************************************************/
function reset_call(val) {
    gridDeleteRow();
    pageNo = val;
    doQuery3(val);
    doQuery4(val);
    doQuery6(val);
    document.forms[0].case_yn.value = "";
}
/*******************************************************************************
 * 저장3
 ******************************************************************************/
function DB_call(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";

    // WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj3.SetParam("mode", "save3");
    GridObj3.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj3.SetParam("group_no", val);
    GridObj3.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj3.SetParam("data_type", document.forms[0].data_type.value);

    // WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj3.DoQuery(servlet_url, "CRUD");
}
/*******************************************************************************
 * 저장4
 ******************************************************************************/
function DB_call_4(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";

    // WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj4.SetParam("mode", "save4");
    GridObj4.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj4.SetParam("group_no", val);
    GridObj4.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj4.SetParam("data_type", document.forms[0].data_type.value);
    GridObj4.SetParam("table_row", GridObj4.getRowCount());
    GridObj4.SetParam("information", document.forms[0].information.value);
    GridObj4.SetParam("case_yn", document.forms[0].case_yn.value);
    // WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj4.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
}
/*******************************************************************************
 * 산출결과 모두보기
 ******************************************************************************/
function case_review() {
    // window.open("./new_case_request.asp?btn_type=2&w_name=<%=w_name%>&step_no=<%=step_no%>&pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>",
    // "pop_100", "width=950,height=600,scrollbars=yes,resizable=yes");
    var url = "/plm/jsp/cost/common/new_case_request.jsp?btn_type=2&pk_cr_group="
            + document.forms[0].pk_cr_group.value
            + "&table_row="
            + document.forms[0].table_row.value;

    window.open(url, "pop_100",
            "width=950,height=600,scrollbars=yes,resizable=yes");
}

/*******************************************************************************
 * 요청서 보기
 ******************************************************************************/
function request_call() {
    // window.open("./cost_re_view_test.asp?pk_cr_group=<%=pk_cr_group%>&rev_pk=<%=rev_pk%>&rev_no=<%=rev_no%>&data_type=main&group_case_count=<%=group_case_count%>",
    // "pop_999", "width=1024,height=800,scrollbars=yes,resizable=yes");
    window.open("/plm/jsp/cost/costdb/cost_re_view_test.jsp?pk_cr_group="
            + document.forms[0].pk_cr_group.value + "&rev_no="
            + document.forms[0].rev_no.value
            + "&data_type=main&group_case_count="
            + document.forms[0].group_case_count.value, "pop_999",
            "width=1024,height=800,scrollbars=yes,resizable=yes");
}

/*******************************************************************************
 * 보고서 작성
 ******************************************************************************/
function report_add() {
    // document.forms[0].action =
    // "./cost_report_1_edit.asp?pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&report_pk=<%=report_pk%>&rev_no=<%=rev_no%>&user_case_count=<%=group_case_count%>";
    document.forms[0].action = "/plm/jsp/cost/costreport/cost_report_1_edit.jsp?pk_cr_group="
            + document.forms[0].pk_cr_group.value
            + "&table_row="
            + document.forms[0].table_row.value
            + "&report_pk="
            + document.forms[0].report_pk.value
            + "&rev_no="
            + document.forms[0].rev_no.value
            + "&user_case_count="
            + document.forms[0].group_case_count.value;
    document.forms[0].submit();
}

/*******************************************************************************
 * 요청자산출Case
 ******************************************************************************/
function user_case_call_1() {
    document.forms[0].action = "/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group="
            + document.forms[0].pk_cr_group.value + "&table_row="
            + document.forms[0].table_row.value + "&data_type=main";
    document.forms[0].submit();
}
function user_case_call_2() {
    document.forms[0].action = "/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group="
            + document.forms[0].pk_cr_group.value + "&table_row="
            + document.forms[0].table_row.value + "&data_type=case0";
    document.forms[0].submit();
}
function user_case_call_3() {
    document.forms[0].action = "/plm/jsp/cost/costWork/costWork.jsp?pk_cr_group="
            + document.forms[0].pk_cr_group.value + "&table_row="
            + document.forms[0].table_row.value + "&data_type=case1";
    document.forms[0].submit();
}
/*******************************************************************************
 * modify추가
 ******************************************************************************/
function mody_add(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";

    // WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj3.SetParam("mode", "modi");
    GridObj3.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj3.SetParam("group_no", val);
    GridObj3.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj3.SetParam("data_type", document.forms[0].data_type.value);
    GridObj3.SetParam("table_row", GridObj3.getRowCount());
    GridObj3.SetParam("case_infor", document.forms[0].case_infor.value);
    GridObj3.SetParam("table_row2", GridObj4.getRowCount());
    GridObj3.SetParam("case_count", GridObj6.getRowCount()+1);

    GridObj3.AddGridRawData('WISEGRID_DATA6', document.WiseGrid6
            .GetGridRawData("WISEGRIDDATA_ALL"));
    GridObj3.AddGridRawData('WISEGRID_DATA4', document.WiseGrid4
            .GetGridRawData("WISEGRIDDATA_ALL"));

    // WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj3.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
}
/*******************************************************************************
 * modify 삭제
 ******************************************************************************/
function doDelete3(val) {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";

    // if(!chkSelected()) {
    // alert("선택된 건이 없습니다.");
    // return;
    // }
    // WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj6.SetParam("mode", "delete");
    GridObj6.SetParam("pk_cr_group", document.forms[0].pk_cr_group.value);
    GridObj6.SetParam("group_no", val);
    GridObj6.SetParam("rev_no", document.forms[0].rev_no.value);
    GridObj6.SetParam("data_type", document.forms[0].data_type.value);

    // WiseGrid가 서버와 통신시에 데이터를 전달한다. 서버에서는 체크된 로우만 전송받게 된다.
    GridObj6.DoQuery(servlet_url, "SELECTED");
}


function DB_call_case() {
    // var servlet_url = "./cost_work_edit_test_db.asp";
    var servlet_url = "/plm/servlet/e3ps/CostWorkServlet";

    // WiseGrid가 서버에 전송할 mode를 셋팅한다.
    GridObj6.SetParam("mode", "case_update");
    // WiseGrid가 서버와 통신시에 데이터를 전달한
    GridObj6.DoQuery(servlet_url, 'WISEGRIDDATA_ALL');
}
/*******************************************************************************
 * 파일첨부
 ******************************************************************************/
/*function filecheck_file_1() {
    a_pk_cr_group = GridObj3.GetCellValue("pk_cr_group", 0);
    popUpOpen("./file_add.asp?page_name=work&file_type=file_1&pk_cr_group="
            + a_pk_cr_group, "file_pop", 470, 120);
}*/
/*
 * function filecheck_file_2() { a_pk_cr_group =
 * GridObj3.GetCellValue("pk_cr_group",0); document.forms[0].file_edit_2.checked =
 * false;
 * popUpOpen("./file_add.asp?page_name=work&file_type=file_2&pk_cr_group="+a_pk_cr_group,
 * "file_pop", 470, 120); } function filecheck_file_3() { a_pk_cr_group =
 * GridObj3.GetCellValue("pk_cr_group",0); document.forms[0].file_edit_3.checked =
 * false;
 * popUpOpen("./file_add.asp?page_name=work&file_type=file_3&pk_cr_group="+a_pk_cr_group,
 * "file_pop", 470, 120); }
 */
/*******************************************************************************
 * 등록된 파일보기
 ******************************************************************************/
function file_call1() {
    document.forms[0].file_1_name.value = GridObj3.GetCellValue("file_1", 0);
    var file_1WholePath = document.forms[0].file_1_name.value;
    window.open(file_1WholePath);
}
/*
 * function file_call2() { document.forms[0].file_2_name.value =
 * GridObj3.GetCellValue("file_2",0); var file_2WholePath =
 * document.forms[0].file_2_name.value; window.open(file_2WholePath); } function
 * file_call3() { document.forms[0].file_3_name.value =
 * GridObj3.GetCellValue("file_3",0); var file_3WholePath =
 * document.forms[0].file_3_name.value; window.open(file_3WholePath); }
 */
function changeValue(obj) {
    for ( var ii = 0; ii < GridObj3.getRowCount(); ii++) {
        GridObj3.SetCellValue(obj.name, ii, obj.value);
    }

    if (obj.name == "p_su_chk") {
        if (obj.checked) {
            for ( var ii = 0; ii < GridObj3.getRowCount(); ii++) {
                GridObj3.SetCellValue(obj.name, ii, "1");
            }
        } else {
            for ( var ii = 0; ii < GridObj3.getRowCount(); ii++) {
                GridObj3.SetCellValue(obj.name, ii, "0");
            }
        }

    }
}
/*******************************************************************************
 * 완료로 넘기기
 ******************************************************************************/
function admin_call() {
    // window.open("./cost_work_pass.asp?select_group=<%=select_group%>&pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&case_type_user=<%=data_type%>",
    // "window_222", "width=262,height=210,scrollbars=no");
    // window.open("./url_data.asp?call_page=cost_work_pass&pk_cr_group=<%=pk_cr_group%>&table_row=<%=table_row%>&data_type=main&rev_no=<%=rev_no%>&group_case_count=<%=group_case_count%>",
    // "window_222", "width=262,height=150,scrollbars=no");
    window.open(
            "/plm/jsp/cost/common/url_data.jsp?call_page=cost_work_pass&pk_cr_group="
                    + document.forms[0].pk_cr_group.value + "&table_row="
                    + document.forms[0].table_row.value
                    + "&data_type=main&rev_no="
                    + document.forms[0].rev_no.value + "&group_case_count="
                    + document.forms[0].group_case_count + "", "window_222",
            "width=262,height=150,scrollbars=no");
}
/*******************************************************************************
 * 펼쳐보기
 ******************************************************************************/
function seePlus(tb_id) {
    var tb_height = document.getElementById(tb_id).height
    document.getElementById(tb_id).height = Number(tb_height) + 50;
}

/*******************************************************************************
 * 접기
 ******************************************************************************/
function seeMinus(tb_id) {
    var tb_height = document.getElementById(tb_id).height

    if (tb_id == "tb_product") {
        document.getElementById(tb_id).height = 223;
    } else {
        document.getElementById(tb_id).height = 325;
    }
}