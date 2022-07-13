 var GridObj1; 	//기본정보(맨위)
 var GridObj2; 	//판매단위제품정보
 var GridObj3; 	//제품생산내역
 var GridObj5;	//간이산출
 var GridObj6;
/*
 var GridObj6;	//data_type = case0 , 판매단위제품정보
 var GridObj7;  //data_type = case0 , 제품생산내역
 var GridObj8;	//data_type = case1 , 판매단위제품정보
 var GridObj9;  //data_type = case1 , 제품생산내역
*/
function init1() {
    GridObj1 = document.WiseGrid1;
    setProperty(GridObj1);
    GridObj1.bHDMoving = false;
    GridObj1.bHDSwapping = false;
    GridObj1.bStatusbarVisible = false;
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
    //ROW 전체선택여부
    //GridObj3.bRowSelectorVisible=true;
     //엑셀 붙여넣기(그리드기반으로 설정함)
      GridObj3.strBlockPaste='gridareabase';
    GridObj3.strActiveRowBgColor="254|252|219";
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


function init6() {
    GridObj6 = document.WiseGrid_hidden;
    setHeader6();
}

/*
function init7() {
    GridObj7 = document.WiseGrid7;
    setProperty(GridObj7);
    GridObj7.bHDMoving = false;
    GridObj7.bHDSwapping = false;
    GridObj7.bStatusbarVisible = false;
    setHeader7();
}

function init8() {
    GridObj8 = document.WiseGrid8;
    setProperty(GridObj8);
    GridObj8.bHDMoving = false;
    GridObj8.bHDSwapping = false;
    GridObj8.bStatusbarVisible = false;
    setHeader8();
}

function init9() {
    GridObj9 = document.WiseGrid9;
    setProperty(GridObj9);
    GridObj9.bHDMoving = false;
    GridObj9.bHDSwapping = false;
    GridObj9.bStatusbarVisible = false;
    setHeader9();
}
*/
function setHeader1() {
    //그리드에 컬럼을 등록한다.

    GridObj1.AddHeader("pk_cr_group",	"pk_cr_group",		"t_text",			  50,			 10,		false);
    GridObj1.AddHeader("pjt_no",			"Project No.",		"t_text",			  50,			 90,		false);
    GridObj1.AddHeader("pjt_name",		"Project Name",	"t_text",			 500,			260,	  	false);
    GridObj1.AddHeader("team",			"개발팀",			"t_combo",	 	 100,			 80,		true);
    GridObj1.AddHeader("f_name",		"개발담당자",		"t_text",			  10,		 	 80,		false);
    GridObj1.AddHeader("a_name",		"영업담당자",		"t_text",			  10,			 80,		false);
    GridObj1.AddHeader("dev_step",		"개발단계",		"t_combo",	 	 100,			100,		true);
    GridObj1.AddHeader("dr_step",		"DR",			"t_text",	 	 	100,			 41,		true);
    GridObj1.AddHeader("sub_step",		"차수",			"t_combo",	 	 100,			 41,		true);
    GridObj1.AddHeader("rev_no",			"Rev",				"t_text",			 100,			  41,		false);
    GridObj1.AddHeader("Leader_day",	"작성일",			"t_text",			 100,			 80,		false);
    GridObj1.AddHeader("request_day",	"요청일",			"t_date",			 100,			 80,		true);
    GridObj1.AddHeader("request_txt",		"요청목적",		"t_text",			2000,		290,		true);
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
  /*
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
*/
    //날짜포멧지정
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
        GridObj1.SetColCellAlign('rev_no',		'center');
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
    GridObj1.SetColHide("sub_step", true);

}

function setHeader2() {
    //그리드에 컬럼을 등록한다.

    GridObj2.AddHeader("SEQ_NO",			"SEQ_NO", 			"t_text", 			  8,			 85,		false);
    GridObj2.AddHeader("pk_cr",				"pk_cr",				"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("pk_cr_group",		"pk_cr_group",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("partSqe",			"partSqe",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("group_no",			"그룹",				"t_text",			 20,			 66,		false);
    GridObj2.AddHeader("part_name",			"품명",				"t_text",			500,			277,	  	false);
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
    GridObj2.SetColCellAlign('SEQ_NO',		'center');
    GridObj2.SetColCellAlign('pk_cr',			'center');
    GridObj2.SetColCellAlign('pk_cr_group',     	'center');
    GridObj2.SetColCellAlign('group_no',		'left');
    GridObj2.SetColCellAlign('part_name',		'left');
    GridObj2.SetColCellAlign('part_no',		'center');
    GridObj2.SetColCellAlign('car_type',		'center');
    GridObj2.SetColCellAlign('customer_F',   	'center');
    GridObj2.SetColCellAlign('customer_L',	    	'center');
    GridObj2.SetColCellAlign('client_cost', 		'right');
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
    GridObj2.SetColHide("partSqe", true);
    GridObj2.SetColHide("rev_pk",	true);
    GridObj2.SetColHide("rev_no",	true);

    //화면분할박스비활성
      GridObj2.bSplitBoxVisible = false;

}

function setHeader2() {
    //그리드에 컬럼을 등록한다.

    GridObj2.AddHeader("SEQ_NO",			"SEQ_NO", 			"t_text", 			  8,			 85,		false);
    GridObj2.AddHeader("pk_cr",				"pk_cr",				"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("pk_cr_group",		"pk_cr_group",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("partSqe",			"partSqe",			"t_text",			 50,			 10,		false);
    GridObj2.AddHeader("group_no",			"그룹",				"t_text",			 20,			 66,		false);
    GridObj2.AddHeader("part_name",			"품명",				"t_text",			500,			277,	  	false);
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
    GridObj2.SetColCellAlign('SEQ_NO',		'center');
    GridObj2.SetColCellAlign('pk_cr',			'center');
    GridObj2.SetColCellAlign('pk_cr_group',     	'center');
    GridObj2.SetColCellAlign('group_no',		'left');
    GridObj2.SetColCellAlign('part_name',		'left');
    GridObj2.SetColCellAlign('part_no',		'center');
    GridObj2.SetColCellAlign('car_type',		'center');
    GridObj2.SetColCellAlign('customer_F',   	'center');
    GridObj2.SetColCellAlign('customer_L',	    	'center');
    GridObj2.SetColCellAlign('client_cost', 		'right');
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
    GridObj2.SetColHide("partSqe", true);
    GridObj2.SetColHide("rev_pk",	true);
    GridObj2.SetColHide("rev_no",	true);

    //화면분할박스비활성
      GridObj2.bSplitBoxVisible = false;

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
    GridObj3.AddHeader("group_no",			"그룹",						"t_text",			 20,		 66,	true);
    GridObj3.AddHeader("pro_type",			"Type",						"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("g_sel1",			"1",							"t_imagetext",		 40,		 20,	false);
    GridObj3.AddHeader("g_sel2",			"2",							"t_imagetext",	 	 40,		 20,	false);
    GridObj3.AddHeader("g_sel3",			"3",							"t_imagetext",		 40,		 20,	false);
    GridObj3.AddHeader("part_name",			"품명",						"t_text",			500,		200,	true);
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
    GridObj3.AddHeader("cavity",				"C/V",						"t_number",		10.4,		 50,	true);
    GridObj3.AddHeader("m_su",				"금형\n벌수",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("mold_cost",			"금형비\n(천원)",				"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("mold_c_type",		"감가\n조건",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("make_type",			"생산처1",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("pro_1",				"생산처2",					"t_combo",		100,		 60,	true);
    GridObj3.AddHeader("ton",				"설비\nTon",					"t_text",			100,		 50,	true);
    GridObj3.AddHeader("spm",				"C/T\n(SPM)",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("method_type",		"공법"		,				"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("pro_level",			"선택",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("pro_level_txt",		"인원",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("line_su",			"조립\n라인",				"t_text",			100,		 50,	true);
    GridObj3.AddHeader("sul_cost",			"설비비\n(천원)",				"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("plating_type",		"구분1",						"t_combo",		100,		 80,	true);
    GridObj3.AddHeader("type_2",			"구분2",						"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("plating_cost",		"￦/Kg\n￦/EA",				"t_text",			100,		 80,	true);
    GridObj3.AddHeader("type_1",			"구분"		,				"t_combo",		100,		 60,	true);
      GridObj3.AddHeader("t_mone",			"단위\n(Sec)",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("type_cost",			"단가\n(시간/EA)",			"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("t_order",			"발주\n조건",				"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("pack_type",			"포장유형",					"t_text",		100,		 80,	true);
    GridObj3.AddHeader("pack_cost",			"포장비",					"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("in_pack",			"내장\n(EA/봉지)",			"t_number",		10.4,		 63,	true);
    GridObj3.AddHeader("out_pack",			"외장\n(EA/Box)",				"t_number",		10.4,		 60,	true);
      GridObj3.AddHeader("store",				"보관",						"t_text",			100,		 85,	false);
      GridObj3.AddHeader("dest",				"납입처",						"t_text",			100,		 85,	false);
    GridObj3.AddHeader("dis_cost",			"물류비",						"t_number",		10.4,		 50,	true);
    GridObj3.AddHeader("distri_cost",			"물류\n흐름비",				"t_text",			100,		 50,	true);
      GridObj3.AddHeader("royalty",			"로얄티\n유무",				"t_combo",		100,		 50,	true);

    /* yazaki 투자비 제거 - 20101223 이성수J // 필드 yazaki_ro → 포장용투자비로대체
    GridObj3.AddHeader("yzk_mone",			"단위",						"t_combo",		100,		 50,	true);
    GridObj3.AddHeader("yazaki_ro",			"천원/천엔",					"t_text",			100,		 80,	true);
    */

    GridObj3.AddHeader("etc_cost",			"기타\n투자비\n(천원)",		"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("yazaki_ro",			"포장용\n투자비\n(천원)",		"t_number",		10.4,		 80,	true);
    GridObj3.AddHeader("part_note",			"그외 특기사항\n(줄바꾸기 :Ctrl+Enter)",				"t_text",			2000,	300,	true);

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
      GridObj3.AppendHeader("PACK_TOTAL", "pack_cost");
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
    GridObj3.AddComboListValue("pro_type",	"구매"		,	"구매");
    GridObj3.AddComboListValue("pro_type",	"부재료"		,	"부재료");

    //제작구분List
    GridObj3.AddComboListValue("part_type",	"신규"		,	"신규");
    GridObj3.AddComboListValue("part_type",	"공용"    		,	"공용");
    GridObj3.AddComboListValue("part_type",	"개조"    		,	"개조");
    GridObj3.AddComboListValue("part_type",	"수정"    		,	"수정");
    GridObj3.AddComboListValue("part_type",	"복합금형"	,	"복합금형");
    GridObj3.AddComboListValue("part_type",	"Family"  		,	"Family");

    //복합금형List
    GridObj3.AddComboListValue("mix_group",	""   			,	"empty"   );
    GridObj3.AddComboListValue("mix_group",	"선생산"  	,	"선생산"  );
    GridObj3.AddComboListValue("mix_group",	"후생산"  	,	"후생산"  );
    GridObj3.AddComboListValue("mix_group",	"동시생산"	,	"동시생산");
    //감가조건
    GridObj3.AddComboListValue("mold_c_type",	"감가"	,	"감가");
    GridObj3.AddComboListValue("mold_c_type",	"지급"	,	"지급");

    //생산처1
    GridObj3.AddComboListValue("make_type",	""		,	"empty"		);
    GridObj3.AddComboListValue("make_type",	"사내"	,	"사내"		);
    GridObj3.AddComboListValue("make_type",	"외주"    	,	"외주"		);
    GridObj3.AddComboListValue("make_type",	"구매"    	,	"구매"		);
    GridObj3.AddComboListValue("make_type",	"OEM"    	,	"OEM" 	  );

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
    /*
    GridObj3.AddComboListValue("pack_type",	"" 	 ,	"empty" );
    GridObj3.AddComboListValue("pack_type",	"비닐" 		 ,	"비닐" 	);
    GridObj3.AddComboListValue("pack_type",	"Reel" 		 ,	"Reel" 	);
    GridObj3.AddComboListValue("pack_type",	"회수용"	 ,	"회수용");
    GridObj3.AddComboListValue("pack_type",	"Tray"  	 ,	"Tray"  );
    GridObj3.AddComboListValue("pack_type",	"골판지"	 ,	"골판지");
*/
  //로얄티유무
      GridObj3.AddComboListValue("royalty", "無", "2");
    GridObj3.AddComboListValue("royalty", "有", "1");

    /*
    YZK기술료단위
    GridObj3.AddComboListValue("yzk_mone",	"" ,	"empty");
    GridObj3.AddComboListValue("yzk_mone",	"KRW" ,	"KRW");
    GridObj3.AddComboListValue("yzk_mone",	"JPY" ,	"JPY");
    */

    //전체선택
    GridObj3.SetColHDCheckBoxVisible("SELECTED", true);

    //Header 정렬
         //GridObj3.SetColCellAlign('pjt_no','center');
        //GridObj3.SetColCellAlign('pjt_name','center');
        GridObj3.SetColCellAlign('SELECTED',		'center');
        GridObj3.SetColCellAlign('CRUD',			'center');
        GridObj3.SetColCellAlign('SEQ_NO',		'center');
        GridObj3.SetColCellAlign('pk_cr',			'center');
        GridObj3.SetColCellAlign('pk_cr_group',		'center');
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

        //특정컬럼 비활성
        GridObj3.SetColCellActivation('group_no', 'activatenoedit');

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
      //GridObj3.SetColHide("pro_level_txt", true);
      GridObj3.SetColHide("grade_name", true);
      GridObj3.SetColHide("grade_color", true);
      GridObj3.SetColHide("distri_cost", true);
      GridObj3.SetColHide("pack_cost", true);
      

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
    GridObj3.SetColCellSortEnable("pack_type",  "false");
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
      //GridObj3.SetColCellMultiLine("part_note", true);
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
    GridObj5.SetNumberFormat("m_total_cost", 	"0,###.###");
    GridObj5.SetNumberFormat("labor_cost", 	"0,###.###");
    GridObj5.SetNumberFormat("common_cost", 	"0,###.###");
    GridObj5.SetNumberFormat("ad_cost", 		"0,###.###");
    GridObj5.SetNumberFormat("g_depr_cost", 	"0,###.###");
    GridObj5.SetNumberFormat("g_actual_cost", 	"0,###.###");
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


function setHeader6() {
    //그리드에 컬럼을 등록한다.

    GridObj6.AddHeader("SELECTED",		"",							"t_checkbox", 		  2, 		 30,	true);

    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
    GridObj6.BoundHeader();

}
