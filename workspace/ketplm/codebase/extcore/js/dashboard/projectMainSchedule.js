window.generateGridColumn = function(columnList){
	
	var colunmProp = {};
	
	var kHeader1Lv = {};
	var kHeader2Lv = {};
	var kHeader3Lv = {};
	var kHeader4Lv = {};

	kHeader1Lv["Kind"] = "Header";
	kHeader1Lv["id"] = "Header1";
	kHeader1Lv["Spanned"] = 1;
	kHeader2Lv["Kind"] = "Header";
	kHeader2Lv["id"] = "Header2";
	kHeader2Lv["Spanned"] = 1;
	kHeader3Lv["Kind"] = "Header";
	kHeader3Lv["id"] = "Header3";
	kHeader3Lv["Spanned"] = 1;
	kHeader4Lv["Kind"] = "Header";
	kHeader4Lv["id"] = "Header4";
	kHeader4Lv["Spanned"] = 1;
	
	
	var cols = [];
	var leftCols = [];
	for(var i = 0; i < columnList.length; i++){
		
		var column = columnList[i];
		
		var level = column["LEVEL"];
		var key = column["KEY"];
		var label = column["LABEL"];
		
		var col = new Object();
		col["Name"] = key;
		col["CanEdit"] = 0;
		var columnKeys = Object.keys(column);
		
		for(var j = 0; j < columnKeys.length; j++){
			var optionKey = columnKeys[j];
			if("LABEL" == optionKey || "KEY" == optionKey || "LEVEL" == optionKey || "COLSPAN" == optionKey) continue;
			col[optionKey] = column[optionKey];
			
		}
		switch(level) {
			case 1 :
				kHeader1Lv[key] = label;
				kHeader1Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader1Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
				}else{
					kHeader1Lv[key + "RowSpan"] = 4;
					col["Spanned"] = 1;
					kHeader2Lv[key] = label;
					kHeader3Lv[key] = label;
					kHeader4Lv[key] = label;
				}
				kHeader1Lv[key + "Align"] = "Scroll";
				
				break;
			case 2 :
				kHeader2Lv[key] = label;
				kHeader2Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader2Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
				}else{
					kHeader2Lv[key + "RowSpan"] = 3;
					col["Spanned"] = 1;
					kHeader3Lv[key] = label;
					kHeader4Lv[key] = label;
				}
				kHeader2Lv[key + "Align"] = "Scroll";
				
				break;
			case 3 :
				kHeader3Lv[key] = label;
				kHeader3Lv[key + "Type"] = "Html";
				if(column["COLSPAN"] != null) {
					var colspan = column["COLSPAN"];
					kHeader3Lv[key + "Span"] = colspan;
					col["Visible"] = 0;
					col["CanHide"] = 0;
					col["CanExport"] = 0;
					
				}else{
					kHeader3Lv[key + "RowSpan"] = 2;
					col["Spanned"] = 1;
					kHeader4Lv[key] = label;
				}
				kHeader3Lv[key + "Align"] = "Scroll";
				
				break;
			default :
				kHeader4Lv[key] = label;
				kHeader4Lv[key + "Type"] = "Html";
				kHeader4Lv[key + "Align"] = "Scroll";
				break;
		}
		
		if(col.Visible == 0) col["CanExport"] = 0;
		
		if(column["LEFTCOLS"]){
			leftCols.push(col);
		}else{
			cols.push(col);
		}
	}
	
	var columnProp = {};
	columnProp["cols"] = cols;
	columnProp["leftCols"] = leftCols;
	columnProp["kHeader1Lv"] = kHeader1Lv;
	columnProp["kHeader2Lv"] = kHeader2Lv;
	columnProp["kHeader3Lv"] = kHeader3Lv;
	columnProp["kHeader4Lv"] = kHeader4Lv;
	
	return columnProp;
}

var columnList = new Array();

//####################################### 제품정보 ##################################################################
columnList.push({LABEL : "Project No",						KEY : "Project_No", LEFTCOLS : true				,LEVEL : 1, Align : "Center", OnClick : 'javascript:openView(Row.oid);', 
	HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>'});
columnList.push({LABEL : "Project Name",					KEY : "Project_Name", LEFTCOLS : true			,Width : 200, LEVEL : 1, OnClick : 'javascript:openView(Row.oid);', 
	HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' });
columnList.push({LABEL : "상태",							KEY : "상태"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발단계",						KEY : "개발단계"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발구분",						KEY : "개발구분"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "설계구분",						KEY : "설계구분"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "조립구분",						KEY : "조립구분"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "Rank",							KEY : "Rank"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발목적",						KEY : "개발목적"                ,CanSort : 0, LEVEL : 1, COLSPAN : 3});
columnList.push({LABEL : "구분1",							KEY : "개발목적1"					, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "구분2",							KEY : "개발목적2"					, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "제품군",							KEY : "제품군"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "PM",								KEY : "PM"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "PM부서",							KEY : "PM부서"				, Width : 120	,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "초류관리",						KEY : "초류관리"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "초류관리부서",					KEY : "초류관리부서"		, Width : 120		,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발",							KEY : "개발"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발부서",						KEY : "개발부서"			, Width : 120		,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "영업",							KEY : "영업"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "영업부서",						KEY : "영업부서"			, Width : 120		,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "최종사용처",						KEY : "최종사용처"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "접점고객",						KEY : "접점고객"					,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "대표차종",						KEY : "대표차종"					,LEVEL : 1, Align : "Center" });
columnList.push({ LABEL : "<div class='headerDiv'>연간수량<p/>(K/1Year)</div> ",		KEY : "연간수량"					,LEVEL : 1, Align : "Center"});
columnList.push({ LABEL : "<div class='headerDiv'>전체수량<p/>(K)</div> ",		KEY : "전체수량"					,LEVEL : 1, Align : "Center"});
columnList.push({ LABEL : "<div class='headerDiv'>SOP-EOP<p/>(Years)</div> ",		KEY : "SOP~EOP(YEARS)"		, Width : 80		,LEVEL : 1, Align : "Center"});
columnList.push({LABEL : "최종사용처",						KEY : "최종사용처(PROTO)"                         	,CanSort : 0, LEVEL : 1, COLSPAN : 5 });
columnList.push({LABEL : "Proto",							KEY : "PROTO_최종사용처"			, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "P1", 								KEY : "P1_최종사용처"				, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "P2", 								KEY : "P2_최종사용처"				, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "SOP", 							KEY : "SOP_최종사용처"				, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "접점고객 (종합)",					KEY : "접점고객(PROTO)"             	,CanSort : 0, LEVEL : 1, COLSPAN : 5 });
columnList.push({LABEL : "Proto", 							KEY : "PROTO_접점고객"				, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "P1", 								KEY : "P1_접점고객"					, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "P2", 								KEY : "P2_접점고객"					, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "SOP", 							KEY : "SOP_접점고객"				, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "개발시작일", 						KEY : "개발시작일"					, Width : 80	,LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "개발착수의뢰", 					KEY : "개발착수의뢰"							,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "개발착수의뢰_계획일"			, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "완료일", 							KEY : "개발착수의뢰_완료일"			, Width : 80	, LEVEL : 2, Align : "Center" });
columnList.push({LABEL : "개발시작회의(DR1)",				KEY : "개발시작회의(DR1)"                         				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "개발시작회의(DR1)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "개발시작회의(DR1)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "투자품의", 						KEY : "투자품의"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "투자품의_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "투자품의_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "DESIGN REVIEW(DR2)",				KEY : "DESIGN_REVIEW(DR2)"                     		, Width : 80	,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "DESIGN_REVIEW(DR2)_계획완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "DESIGN_REVIEW(DR2)_실제완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "도면출도", 						KEY : "도면출도"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "도면출도_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "도면출도_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "금형제작(PROTO)", 				KEY : "금형제작(PROTO)"                          				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "금형제작(PROTO)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "금형제작(PROTO)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "설비제작(PROTO)", 				KEY : "설비제작(PROTO)"                          				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "설비제작(PROTO)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "설비제작(PROTO)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품제작(PROTO)", 				KEY : "초도품제작(PROTO)"                         				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품제작(PROTO)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품제작(PROTO)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품평가(PROTO)", 				KEY : "초도품평가(PROTO)"							,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품평가(PROTO)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품평가(PROTO)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품납품(PROTO)", 				KEY : "초도품납품(PROTO)"							,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품납품(PROTO)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품납품(PROTO)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "PROTO샘플제작", 					KEY : "PROTO_샘플제작"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "PROTO_샘플제작_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "PROTO_샘플제작_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "PROTO샘플납품", 					KEY : "PROTO_샘플납품"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "PROTO_샘플납품_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "PROTO_샘플납품_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "PROTO신뢰성평가(DV)", 				KEY : "PROTO_신뢰성평가(DV)"						,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "PROTO_신뢰성평가(DV)_계획완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "PROTO_신뢰성평가(DV)_실제완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "GATE1", 							KEY : "GATE1"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "GATE1_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "GATE1_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "GATE2", 							KEY : "GATE2"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "GATE2_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "GATE2_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산계획(DR3)", 					KEY : "양산계획(DR3)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산계획(DR3)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산계획(DR3)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "금형제작", 						KEY : "금형제작"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "금형제작_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "금형제작_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "설비입고 및 설치",				KEY : "설비입고_및_설치"                          				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "설비입고_및_설치_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "설비입고_및_설치_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품제작", 						KEY : "초도품제작(양산)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품제작(양산)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품제작(양산)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품평가", 						KEY : "초도품평가(양산)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품평가(양산)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품평가(양산)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도품납품", 						KEY : "초도품납품"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도품납품_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도품납품_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "제품유효성평가(DR4)",				KEY : "제품유효성평가(DR4)"                        				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "제품유효성평가(DR4)_계획완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "제품유효성평가(DR4)_실제완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "신뢰성평가(DV)", 					KEY : "신뢰성평가(DV)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "신뢰성평가(DV)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "신뢰성평가(DV)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "ALL TOOL 준비", 					KEY : "ALL TOOL 준비"                         			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "All-TOOL_준비_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "All-TOOL_준비_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "ALL TOOL 준비 회의",				KEY : "ALL TOOL 준비 회의"						,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "All-TOOL_준비회의_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "All-TOOL_준비회의_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "ALL TOOL 검증", 					KEY : "All-TOOL_검증"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "All-TOOL_검증_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "All-TOOL_검증_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "ALL TOOL 점검", 					KEY : "All-TOOL_점검"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "All-TOOL_점검_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "All-TOOL_점검_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "GATE3", 							KEY : "GATE3"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "GATE3_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "GATE3_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "P1 납품", 						KEY : "P1_납품"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "P1_납품_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "P1_납품_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "신뢰성평가(PV)", 					KEY : "신뢰성평가(PV)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "신뢰성평가(PV)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "신뢰성평가(PV)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "FULL TOOL 준비회의",				KEY : "Full-TOOL_준비회의"						,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "Full-TOOL_준비회의_계획완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "Full-TOOL_준비회의_실제완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "FULL TOOL 검증", 					KEY : "Full-TOOL_검증"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "Full-TOOL_검증_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "Full-TOOL_검증_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산유효성평가(DR5)",				KEY : "양산유효성평가(DR5)"                        				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산유효성평가(DR5)_계획완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산유효성평가(DR5)_실제완료일"	, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "제품합격", 						KEY : "제품합격"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "제품합격_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "제품합격_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "협력사ISIR승인", 					KEY : "협력사ISIR승인"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "협력사ISIR승인_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "협력사ISIR승인_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "FULL TOOL 점검", 					KEY : "FULL-TOOL점검"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "FULL-TOOL점검_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "FULL-TOOL점검_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "GATE4", 							KEY : "GATE4"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "GATE4_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "GATE4_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "P2 납품", 						KEY : "P2_납품"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "P2_납품_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "P2_납품_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "ISIR/PPAP 제출", 					KEY : "ISIR/PPAP_제출"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "ISIR/PPAP_제출_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "ISIR/PPAP_제출_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산이관(금형)", 					KEY : "양산이관(금형)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산이관(금형)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산이관(금형)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산이관(설비)", 					KEY : "양산이관(설비)"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산이관(설비)_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산이관(설비)_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산이관", 						KEY : "양산이관"                          			,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산이관_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산이관_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "양산최종평가(DR6)",				KEY : "양산최종평가(DR6)"                          				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "양산최종평가(DR6)_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "양산최종평가(DR6)_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "GATE5", 							KEY : "GATE5"					,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "GATE5_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "GATE5_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "초도양산", 						KEY : "초도양산"                          				,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "초도양산_계획완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "초도양산_실제완료일"			, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "프로젝트완료", 					KEY : "프로젝트완료"                ,CanSort : 0, LEVEL : 1, COLSPAN : 3 });
columnList.push({LABEL : "계획일", 							KEY : "프로젝트완료_계획완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });
columnList.push({LABEL : "완료일", 							KEY : "프로젝트완료_실제완료일"		, Width : 80	, LEVEL : 2, Align : "Center", Type :'Date', Format : 'yyyy-MM-dd' });

var columnProp = generateGridColumn(columnList);

var protoColumn = [
	"금형제작(PROTO)",
	"설비제작(PROTO)",
	"초도품제작(PROTO)",
	"초도품평가(PROTO)",
	"초도품납품(PROTO)",
	"PROTO_샘플제작",
	"PROTO_샘플납품",
	"PROTO_신뢰성평가(DV)",
	"GATE1",
	"금형제작(PROTO)_계획완료일",
	"금형제작(PROTO)_실제완료일",
	"설비제작(PROTO)_계획완료일",
	"설비제작(PROTO)_실제완료일",
	"초도품제작(PROTO)_계획완료일",
	"초도품제작(PROTO)_실제완료일",
	"초도품평가(PROTO)_계획완료일",
	"초도품평가(PROTO)_실제완료일",
	"초도품납품(PROTO)_계획완료일",
	"초도품납품(PROTO)_실제완료일",
	"PROTO_샘플제작_계획완료일",
	"PROTO_샘플제작_실제완료일",
	"PROTO_샘플납품_계획완료일",
	"PROTO_샘플납품_실제완료일",
	"PROTO_신뢰성평가(DV)_계획완료일",
	"PROTO_신뢰성평가(DV)_실제완료일",
	"GATE1_계획완료일",
	"GATE1_실제완료일"
];

var roleColumnList = new Array();

roleColumnList.push({LABEL : "금형개발(M)",      KEY : "금형개발(M)" ,COLSPAN : 3 });	
roleColumnList.push({LABEL : "금형개발(M)",      KEY : "금형개발(M)" });	
roleColumnList.push({LABEL : "금형개발(M)부서",  KEY : "금형개발(M)부서" });
roleColumnList.push({LABEL : "금형개발(P)",      KEY : "금형개발(P)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "금형개발(P)",      KEY : "금형개발(P)" });
roleColumnList.push({LABEL : "금형개발(P)부서",  KEY : "금형개발(P)부서" });
roleColumnList.push({LABEL : "금형개발(中)",     KEY : "금형개발(中)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "금형개발(中)",     KEY : "금형개발(中)" });
roleColumnList.push({LABEL : "금형개발(中)부서", KEY : "금형개발(中)부서" });
roleColumnList.push({LABEL : "선행QA",           KEY : "선행QA" ,COLSPAN : 3 });
roleColumnList.push({LABEL : "선행QA",           KEY : "선행QA" });
roleColumnList.push({LABEL : "선행QA부서",       KEY : "선행QA부서" });
roleColumnList.push({LABEL : "선행QA(中)",       KEY : "선행QA(中)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "선행QA(中)",       KEY : "선행QA(中)" });
roleColumnList.push({LABEL : "선행QA(中)부서",   KEY : "선행QA(中)부서" });
roleColumnList.push({LABEL : "선행QC",           KEY : "선행QC" ,COLSPAN : 3});
roleColumnList.push({LABEL : "선행QC",           KEY : "선행QC" });
roleColumnList.push({LABEL : "선행QC부서",       KEY : "선행QC부서" });
roleColumnList.push({LABEL : "QC(조립)",         KEY : "QC(조립)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "QC(조립)",         KEY : "QC(조립)" });
roleColumnList.push({LABEL : "QC(조립)부서",     KEY : "QC(조립)부서" });
roleColumnList.push({LABEL : "QC(中)",           KEY : "QC(中)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "QC(中)",           KEY : "QC(中)" });
roleColumnList.push({LABEL : "QC(中)부서",       KEY : "QC(中)부서" });
roleColumnList.push({LABEL : "초류관리(中)",     KEY : "초류관리(中)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "초류관리(中)",     KEY : "초류관리(中)" });
roleColumnList.push({LABEL : "초류관리(中)부서", KEY : "초류관리(中)부서" });
roleColumnList.push({LABEL : "생산(조립)",       KEY : "생산(조립)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "생산(조립)",       KEY : "생산(조립)" });
roleColumnList.push({LABEL : "생산(조립)부서",   KEY : "생산(조립)부서" });
roleColumnList.push({LABEL : "생산(M)",          KEY : "생산(M)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "생산(M)",          KEY : "생산(M)" });
roleColumnList.push({LABEL : "생산(M)부서",      KEY : "생산(M)부서" });
roleColumnList.push({LABEL : "생산(中)",         KEY : "생산(中)" ,COLSPAN : 3});
roleColumnList.push({LABEL : "생산(中)",         KEY : "생산(中)" });
roleColumnList.push({LABEL : "생산(中)부서",     KEY : "생산(中)부서" });

var pmschedule = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'projectMainScheduleGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['projectMainScheduleGrid'])?Grids['projectMainScheduleGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : 'Project_No',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:pmschedule.search(Value);',
			LeftCols : columnProp.leftCols,
			Cols : columnProp.cols,
			Head : [
				{
					Kind : "Header",
					id : "Header",
					RowSpan : 0,
					Visible : 0
				},
				columnProp.kHeader1Lv, columnProp.kHeader2Lv
			],
			SelectingSingle : '1',
			Panel : {
				Width : '20', Visible : '1',CanHide : '0'
			},
			excelDownloadFn : 'pmschedule.excelDownload();'
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/findPMSchedulePagingList.do?sortName=*Sort0';
			
			var param = {
		       		page : grid.GetPageNum(row),
		            formPage : grid.Source.Layout.Data.Cfg.PageLength
		        }

	        $('input,select').each(function(){
	        	var name = $(this).attr('name');
	            var value = $(this).val();
	            param[name] = value;
	        });
	        grid.Data.Page.Param = param;
		}
		
		Grids.OnSort = function(grid, col, sort){

			grid.Sort = sort;
			$("#sortKey").val(sort);
			Grids['projectMainScheduleGrid'].Source.Data.Param.sortKey=sort;
			Grids['projectMainScheduleGrid'].ReloadBody();
			
			return -1;
		}
		
		//row click event
		//Grids.OnClick = cost.goView;
	},
	hideCols : function(isHide){
		
		var staticCols = [
			"금형제작(PROTO)",
			"설비제작(PROTO)",
			"초도품제작(PROTO)",
			"초도품평가(PROTO)",
			"초도품납품(PROTO)",
			"PROTO_샘플제작",
			"PROTO_샘플납품",
			"PROTO_신뢰성평가(DV)",
			"GATE1"
		];
		
		for(var i = 0; i < protoColumn.length; i++){
			
			var colKey = protoColumn[i];
			
			if(staticCols.contains(colKey)) continue;
			if(isHide) Grids['projectMainScheduleGrid'].HideCol(colKey);
			else Grids['projectMainScheduleGrid'].ShowCol(colKey);
		}
	},
	excelDownload : function(){
		
		var param = $("[name=searchForm]").serialize();
		
		var dataCols = new Array();
		for(var i = 0; i < columnList.length; i++){
			
			var col = columnList[i];
			
			var dataCol = {};
			dataCol.key   = col.KEY;
			dataCol.label = col.LABEL;
			if(col.COLSPAN == null) dataCol.colspan = 0;
			else					dataCol.colspan = (col.COLSPAN-1);
			
			dataCols.push(dataCol);
		}
		
		
		
		if($("#isRole").prop("checked")){
			for(var i = 0; i < roleColumnList.length; i++){
				
				var col = roleColumnList[i];
				
				var dataCol = {};
				dataCol.key   = col.KEY;
				dataCol.label = col.LABEL;
				if(col.COLSPAN == null) dataCol.colspan = 0;
				else					dataCol.colspan = (col.COLSPAN-1);
				
				dataCols.push(dataCol);
			}
		}

		
		param += "&cols=" + JSON.stringify(dataCols) + "&protoCols=" + JSON.stringify(protoColumn);
		
		ajaxCallServer("/plm/ext/dashboard/createPMScheduleListExcel", param, function(data){
            location.href = data.downloadLink;
        });
		
	},
	search : function(perPage){
		if(!perPage) perPage = Grids['projectMainScheduleGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['projectMainScheduleGrid'].Source.Data.Url = '/plm/ext/dashboard/findPMSchedulePagingList.do?sortName=*Sort0';
		Grids['projectMainScheduleGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['projectMainScheduleGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
		Grids['projectMainScheduleGrid'].Source.Data.Param.formPage=perPage;
		Grids['projectMainScheduleGrid'].ReloadBody();
	},
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
}