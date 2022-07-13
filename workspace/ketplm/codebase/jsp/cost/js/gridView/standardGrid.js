var GridObjReer; //환율 DB
var GridObjCutt; //절단비 DB
var GridObjPlating; //도금비 DB
var GridObjProces; //가공비 DB
var GridObjP_maker; //비철원재료비 DB
var GridObjM_maker; //수지원재료비 DB
var GridObjLME; //LME DB
var GridObjDis; //물류흐름비 DB
var GridObjExpcost; //제조경비기준가 DB

function initReer() 
{ 
	GridObjReer = document.WiseGridReer;
	setProperty(GridObjReer);
	GridObjReer.bHDMoving = false;
	GridObjReer.bHDSwapping = false;
	GridObjReer.bStatusbarVisible = false;
	//GridObjCutt.strRowSizing = "autofree";
	setHeaderReer();
}

function initCutt() 
{ 
	GridObjCutt = document.WiseGridCutt;
	setProperty(GridObjCutt);
	GridObjCutt.bHDMoving = false;
	GridObjCutt.bHDSwapping = false;
	GridObjCutt.bStatusbarVisible = false;
	//GridObjCutt.strRowSizing = "autofree";
	setHeaderCutt();
}

function initPlating() 
{ 
	GridObjPlating = document.WiseGridPlating;
	setProperty(GridObjPlating);
	GridObjPlating.bHDMoving = false;
	GridObjPlating.bHDSwapping = false;
	GridObjPlating.bStatusbarVisible = false;
	//GridObjCutt.strRowSizing = "autofree";
	setHeaderPlating();
}

function initProces() 
{ 
	GridObjProces = document.WiseGridProces;
	setProperty(GridObjProces);
	GridObjProces.bHDMoving = false;
	GridObjProces.bHDSwapping = false;
	GridObjProces.bStatusbarVisible = false;
	//GridObjProces.strRowSizing = "autofree";
	setHeaderProces();
}

function initP_maker() 
{ 
	GridObjP_maker = document.WiseGridP_maker;
	setProperty(GridObjP_maker);
	GridObjP_maker.bHDMoving = false;
	GridObjP_maker.bHDSwapping = false;
	GridObjP_maker.bStatusbarVisible = false;
	//GridObjP_maker.strRowSizing = "autofree";
	setHeaderP_maker();
}

function initM_maker() 
{ 
	GridObjM_maker = document.WiseGridM_maker;
	setProperty(GridObjM_maker);
	GridObjM_maker.bHDMoving = false;
	GridObjM_maker.bHDSwapping = false;
	GridObjM_maker.bStatusbarVisible = false;
	//GridObjM_maker.strRowSizing = "autofree";
	setHeaderM_maker();
}

function initLME() 
{ 
	GridObjLME = document.WiseGridLME;
	setProperty(GridObjLME);
	GridObjLME.bHDMoving = false;
	GridObjLME.bHDSwapping = false;
	GridObjLME.bStatusbarVisible = false;
	//GridObjLME.strRowSizing = "autofree";
	setHeaderLME();
}

function initDis() 
{ 
	GridObjDis = document.WiseGridDis;
	setProperty(GridObjDis);
	GridObjDis.bHDMoving = false;
	GridObjDis.bHDSwapping = false;
	GridObjDis.bStatusbarVisible = false;
	//GridObjDis.strRowSizing = "autofree";
	setHeaderDis();
}

function initExpcost() 
{ 
	GridObjExpcost = document.WiseGridExpcost;
	setProperty(GridObjExpcost);
	GridObjExpcost.bHDMoving = false;
	GridObjExpcost.bHDSwapping = false;
	GridObjExpcost.bStatusbarVisible = false;
	//GridObjExpcost.strRowSizing = "autofree";
	setHeaderExpcost();
}



/*환율DB셋팅*/
function setHeaderReer() {
	GridObjReer.AddHeader("SELECTED",	"",		"t_checkbox", 		2, 	    30,	    true);
	GridObjReer.AddHeader("CRUD",		"상태",			"t_text", 		8, 		40,		true);
	GridObjReer.AddHeader("PK_RE",	 	"PK_RE",		"t_text",		10,		10,		false);
	GridObjReer.AddHeader("REER_date",	"등록일",		"t_date",		20,		90,		true);
	GridObjReer.AddHeader("USD_rate",	"USD",			"t_text",	    10,	    90,	  	true);
	GridObjReer.AddHeader("YEN_rate", 	"YEN",			"t_text",	 	10,	    90,		true);
	GridObjReer.AddHeader("EURO_rate", 	"EURO",			"t_text",		10,		90,		true);
	GridObjReer.AddHeader("CNY_rate", 	"CNY",			"t_text",		10,		90,		true);
	
	//마우스오른쪽비활성-내용부분
  	//GridObjReer.bUseDefaultContextMenu=false;
	//GridObjReer.bUserContextMenu=true;

    //그룹생성
    GridObjReer.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
    GridObjReer.AppendHeader("CHK_TOTAL", "SELECTED");
    GridObjReer.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjReer.BoundHeader();
	
	GridObjReer.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

  	//Header정렬
	GridObjReer.SetColCellAlign('SELECTED','center');
	GridObjReer.SetColCellAlign('CRUD','center');
    GridObjReer.SetColCellAlign('REER_date','center');
    GridObjReer.SetColCellAlign('USD_rate',	'right');
    GridObjReer.SetColCellAlign('YEN_rate',	'right');
    GridObjReer.SetColCellAlign('EURO_rate','right');
    GridObjReer.SetColCellAlign('CNY_rate',	'right');

	//전체선택
    GridObjReer.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjReer.SetColHide("PK_RE", true);
	doQuery();
}

function setHeaderCutt() {
	//그리드에 컬럼을 등록한다.
	GridObjCutt.AddHeader("SELECTED",	"",							"t_checkbox",	2, 	    	30,		    true);
	GridObjCutt.AddHeader("CRUD",		"",							"t_text", 		8, 			40,			true);
	GridObjCutt.AddHeader("pk_cut",	 	"pk_cut",		            "t_text",		10,			 10,		true);
	GridObjCutt.AddHeader("met_type",	"원재료코드",				"t_text",		20,			 75,		true);
	GridObjCutt.AddHeader("met_w",		"폭",	            		"t_text",	    10,	         40,	  	true);
	GridObjCutt.AddHeader("c_size_t_1", "0.50t~0.999t",				"t_text",	 	10,	         90,		true);
	GridObjCutt.AddHeader("c_size_t_2", "0.40t~0.499t",				"t_text",		10,		 	 90,		true);
	GridObjCutt.AddHeader("c_size_t_3", "0.30t~0.399t",				"t_text",		10,			 90,		true);
	GridObjCutt.AddHeader("c_size_t_4", "0.25t~0.299t",				"t_text",	 	10,		     90,		true);
	GridObjCutt.AddHeader("c_size_t_5", "0.20t~0.249t",				"t_text",	 	10,	         90,		true);
	GridObjCutt.AddHeader("c_size_t_6", "0.15t~0.199t",				"t_text",	 	10,	         90,		true);
	GridObjCutt.AddHeader("c_size_t_7", "0.13t~0.149t",				"t_text",		10,		     90,		true);
	GridObjCutt.AddHeader("c_size_t_8", "0.08t~0.129t",				"t_text",		10,		     90,		true);
	GridObjCutt.AddHeader("c_size_t_9", "1.0t~",					"t_text",		10,		     67,		true);
	
  	//마우스오른쪽비활성-내용부분
  	//GridObjCutt.bUseDefaultContextMenu=false;
	//GridObjCutt.bUserContextMenu=true;

    //그룹생성
	GridObjCutt.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjCutt.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjCutt.AppendHeader("CHK_TOTAL", "CRUD");
    
	//AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
	GridObjCutt.BoundHeader();
	
	GridObjCutt.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

  	//Header 정렬
  	GridObjCutt.SetColCellAlign('pk_cut',		'center');
    GridObjCutt.SetColCellAlign('met_type',     'center');
    GridObjCutt.SetColCellAlign('met_w',		'center');
    GridObjCutt.SetColCellAlign('c_size_t_1',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_2',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_3',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_4',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_5',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_6',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_7',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_8',	'center');
    GridObjCutt.SetColCellAlign('c_size_t_9',	'center');

	//전체선택
    GridObjCutt.SetColHDCheckBoxVisible("SELECTED", true);
    
	//컬럼 숨기기
	GridObjCutt.SetColHide("pk_cut", true);
	doQuery();
}

/*도금비DB셋팅*/
function setHeaderPlating() {
	GridObjPlating.AddHeader("SELECTED",	"",		"t_checkbox", 		2, 	    30,	    true);
	GridObjPlating.AddHeader("CRUD",		"상태",			"t_text", 		8, 		40,		true);
	GridObjPlating.AddHeader("PK_PLC",	 	"PK_PLC",		"t_text",		10,		10,		false);
	GridObjPlating.AddHeader("MET_TYPE",	"원재료코드",	"t_text",		20,		90,		true);
	GridObjPlating.AddHeader("P_SIZE_T_1",	"0.15t~0.199t",	"t_text",	    10,	    90,	  	true);
	GridObjPlating.AddHeader("P_SIZE_T_2", 	"0.20t~0.249t",	"t_text",	 	10,	    90,		true);
	GridObjPlating.AddHeader("P_SIZE_T_3", 	"0.25t~0.299t",	"t_text",		10,		90,		true);
	GridObjPlating.AddHeader("P_SIZE_T_4", 	"0.30t~",		"t_text",		10,		90,		true);
	
	//마우스오른쪽비활성-내용부분
  	//GridObjPlating.bUseDefaultContextMenu=false;
	//GridObjPlating.bUserContextMenu=true;

    //그룹생성
	GridObjPlating.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjPlating.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjPlating.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjPlating.BoundHeader();
	
	GridObjPlating.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");

  	//Header정렬
	GridObjPlating.SetColCellAlign('SELECTED','center');
	GridObjPlating.SetColCellAlign('CRUD','center');
	GridObjPlating.SetColCellAlign('MET_TYPE','center');
	GridObjPlating.SetColCellAlign('P_SIZE_T_1','center');
	GridObjPlating.SetColCellAlign('P_SIZE_T_2','center');
	GridObjPlating.SetColCellAlign('P_SIZE_T_3','center');
	GridObjPlating.SetColCellAlign('P_SIZE_T_4','center');

	//전체선택
	GridObjPlating.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjPlating.SetColHide("PK_PLC", true);
	doQuery();
}

/*가공비DB 셋팅*/
function setHeaderProces() {
	GridObjProces.AddHeader("SELECTED",		"",				"t_checkbox", 		2, 	    30,	    true);
	GridObjProces.AddHeader("CRUD",			"상태",			"t_text", 		8, 		40,		true);
	GridObjProces.AddHeader("PK_PC",	 	"PK_PC",		"t_text",		10,		10,		false);
	GridObjProces.AddHeader("PC_COST_TYPE",	"Type",			"t_combo",		20,		90,		true);
	GridObjProces.AddHeader("MET_TYPE",		"원재료코드",	"t_text",	    10,	    90,	  	true);
	GridObjProces.AddHeader("PRO_COST", 	"비용",			"t_text",	 	10,	    90,		true);
	
	//마우스오른쪽비활성-내용부분
  	//GridObjProces.bUseDefaultContextMenu=false;
	//GridObjProces.bUserContextMenu=true;

    //그룹생성
	GridObjProces.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjProces.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjProces.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjProces.BoundHeader();
	
	GridObjProces.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
	// 가공비Type 콤보 리스트 
	GridObjProces.AddComboListValue("PC_COST_TYPE", "가공비", "가공비");
	GridObjProces.AddComboListValue("PC_COST_TYPE", "임가공비", "임가공비");
	GridObjProces.AddComboListValue("PC_COST_TYPE", "DeTin비", "DeTin비");

  	//Header정렬
	GridObjProces.SetColCellAlign('SELECTED','center');
	GridObjProces.SetColCellAlign('CRUD','center');
	GridObjProces.SetColCellAlign('PK_PC','center');
	GridObjProces.SetColCellAlign('PC_COST_TYPE','center');
	GridObjProces.SetColCellAlign('MET_TYPE','center');
	GridObjProces.SetColCellAlign('PRO_COST','center');

	//전체선택
	GridObjProces.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjProces.SetColHide("PK_PC", true);
	doQuery();
}

/*비철원재료DB 셋팅*/
function setHeaderP_maker() {
	GridObjP_maker.AddHeader("SELECTED",	"",				"t_checkbox", 		2, 	    30,	    true);
	GridObjP_maker.AddHeader("CRUD",		"상태",			"t_text", 		8, 		40,		true);
	GridObjP_maker.AddHeader("PK_PR",	 	"PK_PR",		"t_text",		10,		10,		false);
	GridObjP_maker.AddHeader("MK_CODE",		"업체코드",		"t_text",		20,		90,		true);
	GridObjP_maker.AddHeader("MAKER_NAME",	"업체명",		"t_text",		20,		90,		true);
	GridObjP_maker.AddHeader("MET_TYPE",	"원재료코드",	"t_text",	    10,	    90,	  	true);
	GridObjP_maker.AddHeader("GRADE_NAME", 	"원재료명",		"t_text",	 	10,	    90,		true);
	GridObjP_maker.AddHeader("S_GRAVITY", 	"비중",			"t_text",	 	10,	    90,		true);
	
	//마우스오른쪽비활성-내용부분
  	//GridObjP_maker.bUseDefaultContextMenu=false;
	//GridObjP_maker.bUserContextMenu=true;

    //그룹생성
	GridObjP_maker.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjP_maker.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjP_maker.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjP_maker.BoundHeader();
	
	GridObjP_maker.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
  	//Header정렬
	GridObjP_maker.SetColCellAlign('SELECTED','center');
	GridObjP_maker.SetColCellAlign('CRUD','center');
	GridObjP_maker.SetColCellAlign('PK_PR','center');
	GridObjP_maker.SetColCellAlign('MK_CODE','center');
	GridObjP_maker.SetColCellAlign('MAKER_NAME','center');
	GridObjP_maker.SetColCellAlign('MET_TYPE','center');
	GridObjP_maker.SetColCellAlign('GRADE_NAME','center');
	GridObjP_maker.SetColCellAlign('S_GRAVITY','center');

	//전체선택
	GridObjP_maker.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjP_maker.SetColHide("PK_PR", true);
	doQuery();
}

/*수지원재료DB 셋팅*/
function setHeaderM_maker() {
	GridObjM_maker.AddHeader("SELECTED",		"",				"t_checkbox", 		2, 	    30,	    true);
	GridObjM_maker.AddHeader("CRUD",			"상태",			"t_text", 		8, 		40,		true);
	GridObjM_maker.AddHeader("PK_MK",	 		"PK_MK",		"t_text",		10,		10,		false);
	GridObjM_maker.AddHeader("MK_CODE",			"업체코드",		"t_text",		20,		90,		true);
	GridObjM_maker.AddHeader("MAKER_NAME",		"업체명",		"t_text",		100,		90,		true);
	GridObjM_maker.AddHeader("MATERIAL_NAME",	"MATERIAL",		"t_text",		200,		90,		true);
	GridObjM_maker.AddHeader("GRADE_NAME",		"원재료명",		"t_text",	    200,	    90,	  	true);
	GridObjM_maker.AddHeader("GRADE_COLOR", 	"원재료색상",	"t_text",	 	10,	    90,		true);
	GridObjM_maker.AddHeader("GRADE_COST", 		"단가",			"t_text",	 	10,	    90,		true);
	GridObjM_maker.AddHeader("SU_STAN_DAY", 	"기준일",		"t_text",	 	10,	    90,		true);
	
	//마우스오른쪽비활성-내용부분
  	//GridObjM_maker.bUseDefaultContextMenu=false;
	//GridObjM_maker.bUserContextMenu=true;

    //그룹생성
	GridObjM_maker.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjM_maker.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjM_maker.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjM_maker.BoundHeader();
	
	GridObjM_maker.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
  	//Header정렬
	GridObjM_maker.SetColCellAlign('SELECTED','center');
	GridObjM_maker.SetColCellAlign('CRUD','center');
	GridObjM_maker.SetColCellAlign('PK_MK','center');
	GridObjM_maker.SetColCellAlign('MK_CODE','center');
	GridObjM_maker.SetColCellAlign('MAKER_NAME','center');
	GridObjM_maker.SetColCellAlign('MATERIAL_NAME','center');
	GridObjM_maker.SetColCellAlign('GRADE_NAME','center');
	GridObjM_maker.SetColCellAlign('GRADE_COLOR','center');
	GridObjM_maker.SetColCellAlign('GRADE_COST','center');
	GridObjM_maker.SetColCellAlign('SU_STAN_DAY','center');

	//전체선택
	GridObjM_maker.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjM_maker.SetColHide("PK_MK", true);
	doQuery();
}

/*LME DB 셋팅*/
function setHeaderLME() {
	GridObjLME.AddHeader("SELECTED",	"",		"t_checkbox", 		2, 	    30,	    true);
	GridObjLME.AddHeader("CRUD",		"상태",		"t_text", 		8, 		40,		true);
	GridObjLME.AddHeader("pk_LME",		"pk_LME",	"t_text",		10,		10,		false);
	GridObjLME.AddHeader("in_date",		"등록일",	"t_date",		20,		90,		true);
	GridObjLME.AddHeader("USD_rate",	"USD(달러) ",	"t_text",		100,		90,		true);
	GridObjLME.AddHeader("YEN_rate",	"JPY(100엔)",	"t_text",		200,		90,		true);
	GridObjLME.AddHeader("Lme_cu",		"Cu",		"t_text",	    200,	    90,	  	true);
	GridObjLME.AddHeader("Lme_zn",		"Zn",		"t_text",	 	10,	    90,		true);
	GridObjLME.AddHeader("Lme_sn",		"Sn",		"t_text",	 	10,	    90,		true);
	GridObjLME.AddHeader("Lme_ni",		"Ni",		"t_text",	 	10,	    90,		true);
	GridObjLME.AddHeader("Lme_type",	"Worst/Best",	"t_text",	 	10,	    90,		true);
	                                         
	//마우스오른쪽비활성-내용부분
  	//GridObjLME.bUseDefaultContextMenu=false;
	//GridObjLME.bUserContextMenu=true;

    //그룹생성
	GridObjLME.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjLME.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjLME.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjLME.BoundHeader();
	
	GridObjLME.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
  	//Header정렬
	GridObjLME.SetColCellAlign('SELECTED','center');     
	GridObjLME.SetColCellAlign('CRUD','center');         
	GridObjLME.SetColCellAlign('pk_LME','center');
	GridObjLME.SetColCellAlign('in_date','center');
	GridObjLME.SetColCellAlign('USD_rate','center');
	GridObjLME.SetColCellAlign('YEN_rate','center');
	GridObjLME.SetColCellAlign('Lme_cu','center');
	GridObjLME.SetColCellAlign('Lme_zn','center');
	GridObjLME.SetColCellAlign('Lme_sn','center');
	GridObjLME.SetColCellAlign('Lme_ni','center');
	GridObjLME.SetColCellAlign('Lme_type','center');

	//전체선택
	GridObjLME.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjLME.SetColHide("pk_LME", true);
	doQuery();
}
/*물류흐름비 DB 셋팅*/
function setHeaderDis() {
	GridObjDis.AddHeader("SELECTED",	"",				"t_checkbox", 	2, 		30, true);
	GridObjDis.AddHeader("CRUD",		"상태",			"t_text", 		8, 		40,	true);
	GridObjDis.AddHeader("pk_dis",		"pk_dis",		"t_text",		10,		10,	false);
	GridObjDis.AddHeader("distri_type",	"구분",			"t_text",		20,		90,	true);
	GridObjDis.AddHeader("store",		"출발지(보관)",	"t_text",		100,	90,	true);
	GridObjDis.AddHeader("dest",		"납입처(목적지)","t_text",		200,	90,	true);
	GridObjDis.AddHeader("dest_1",		"지역",			"t_text",	    200,	90,	true);
	GridObjDis.AddHeader("distri_cost",	"단가",			"t_text",	 	10,		90,	true);
	                                         
	//마우스오른쪽비활성-내용부분
  	//GridObjDis.bUseDefaultContextMenu=false;
	//GridObjDis.bUserContextMenu=true;

    //그룹생성
	GridObjDis.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjDis.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjDis.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjDis.BoundHeader();
	
	GridObjDis.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
  	//Header정렬
	GridObjDis.SetColCellAlign('SELECTED','center');     
	GridObjDis.SetColCellAlign('CRUD','center');         
	GridObjDis.SetColCellAlign('pk_dis','center');
	GridObjDis.SetColCellAlign('distri_type','center');
	GridObjDis.SetColCellAlign('store','center');
	GridObjDis.SetColCellAlign('dest','center');
	GridObjDis.SetColCellAlign('dest_1','center');
	GridObjDis.SetColCellAlign('distri_cost','center');

	//전체선택
	GridObjDis.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjDis.SetColHide("pk_dis", true);
	doQuery();
}
/*제조경비기준가 DB 셋팅*/
function setHeaderExpcost() {
	GridObjExpcost.AddHeader("SELECTED",		"",		"t_checkbox", 	2, 	30,    true);
	GridObjExpcost.AddHeader("CRUD",		"상태",		"t_text", 	8, 	40,	true);
	GridObjExpcost.AddHeader("pk_st",		"pk_st",	"t_text",	10,	10,	false);
	GridObjExpcost.AddHeader("st_team",		"생산처",	"t_text",	20,	90,	true);
	GridObjExpcost.AddHeader("st_pro_type",		"제품Type",	"t_text",	50,	90,	true);
	GridObjExpcost.AddHeader("st_met",		"공법",		"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_jun_cost",		"전력비",	"t_text",	10,	90,  	true);
	GridObjExpcost.AddHeader("st_ma_depr",		"기계감가비",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_tabalu",		"타발유",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_overhead",		"간접경비율",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_ge_cost",		"일반관리비율",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_jae_cost",		"재료관리비율",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_rnd_cost",		"R&D비율",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_royalty",		"로얄티비율",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_person",		"표준인원",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_rate",		"임율(1인)",	"t_text",	10,	90,	true);
	GridObjExpcost.AddHeader("st_eff_value",	"효율",		"t_text",	10,	90,	true);
	                                         
	//마우스오른쪽비활성-내용부분
  	//GridObjExpcost.bUseDefaultContextMenu=false;
	//GridObjExpcost.bUserContextMenu=true;

    //그룹생성
	GridObjExpcost.AddGroup("CHK_TOTAL", 		"선택");
    
    //그룹에 헤더 추가
	GridObjExpcost.AppendHeader("CHK_TOTAL", "SELECTED");
	GridObjExpcost.AppendHeader("CHK_TOTAL", "CRUD");
    
	GridObjExpcost.BoundHeader();
	
	GridObjExpcost.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
	
  	//Header정렬
	GridObjExpcost.SetColCellAlign('SELECTED','center');     
	GridObjExpcost.SetColCellAlign('CRUD','center');         
	GridObjExpcost.SetColCellAlign('pk_st','center');
	GridObjExpcost.SetColCellAlign('st_team','center');
	GridObjExpcost.SetColCellAlign('st_pro_type','center');
	GridObjExpcost.SetColCellAlign('st_met','center');
	GridObjExpcost.SetColCellAlign('st_jun_cost','center');
	GridObjExpcost.SetColCellAlign('st_ma_depr','center');
	GridObjExpcost.SetColCellAlign('st_tabalu','center');
	GridObjExpcost.SetColCellAlign('st_overhead','center');
	GridObjExpcost.SetColCellAlign('st_ge_cost','center');
	GridObjExpcost.SetColCellAlign('st_jae_cost','center');
	GridObjExpcost.SetColCellAlign('st_rnd_cost','center');
	GridObjExpcost.SetColCellAlign('st_royalty','center');
	GridObjExpcost.SetColCellAlign('st_person','center');
	GridObjExpcost.SetColCellAlign('st_rate','center');
	GridObjExpcost.SetColCellAlign('st_eff_value','center');
	

	//전체선택
	GridObjExpcost.SetColHDCheckBoxVisible("SELECTED", true);
    
  //컬럼 숨기기
	GridObjExpcost.SetColHide("pk_st", true);
	doQuery();
}
