var columnList = new Array();

columnList.push({LABEL : "PartNo",				    KEY : "partNo" });
columnList.push({LABEL : "경쟁사",					KEY : "companyName", LEFTCOLS : true			,Width : 70, LEVEL : 1, Align : "Center", CanSort : '0'});
columnList.push({LABEL : "품명",							KEY : "partName", LEFTCOLS : true			,Width : 200, LEVEL : 1, Align : "Center" , CanSort : '0' });
columnList.push({LABEL : "제품형상", KEY : "partNopartForm", LEVEL : 1, CanEdit : 0, CanSelect : 0, Type : "Html", LEFTCOLS : true, Width : 80 , CanSort : '0' });
columnList.push({LABEL : "방수여부",						KEY : "waterProof", LEFTCOLS : true,Width : 70		,LEVEL : 1, Align : "Center", CanSort : '0' });
columnList.push({LABEL : "<div class='headerDiv'>Male/<p/>Female</div>",						KEY : "mfType", LEFTCOLS : true,Width : 70	,LEVEL : 1, Align : "Center", CanSort : '0' });
columnList.push({LABEL : "<div class='headerDiv'>극수<p/>(P)</div>",						KEY : "pole",LEFTCOLS : true					,LEVEL : 1, Align : "Center",Width : 55	, CanSort : '0' });
columnList.push({LABEL : "시리즈",						KEY : "seriesName",LEFTCOLS : true					,LEVEL : 1, Align : "Center", CanSort : '0' });
columnList.push({LABEL : "<div class='headerDiv'>Terminal<p/>PartNo</div>",		KEY : "matchTerminal" ,LEFTCOLS : true					,LEVEL : 1, Align : "Center", Type : 'Lines', AcceptEnters : '1', CanSort : '0'});
columnList.push({LABEL : "<div class='headerDiv'>Replace<p/>(O /△ /X)</div>", LEFTCOLS : true,						KEY : "replaceGb"                ,CanSort : 0, LEVEL : 1,Align : "Center", CanSort : '0'});
columnList.push({LABEL : "KET품번",							KEY : "ketPartNo"					, LEVEL : 1, Align : "Center" });
columnList.push({LABEL : "KET품명",							KEY : "ketPartName"					, LEVEL : 1, Align : "Center" , CanSort : '0'});
columnList.push({LABEL : "제품형상", KEY : "ketPartNopartForm", LEVEL : 1, CanEdit : 0, CanSelect : 0, Type : "Html", Width : 80 , CanSort : '0'});
columnList.push({LABEL : "방수여부",							KEY : "ketWaterProof"					,LEVEL : 1, Align : "Center" ,Width : 70, CanSort : '0'});
columnList.push({LABEL : "분류체계",								KEY : "classification"					,LEVEL : 1, Align : "Center", CanSort : '0' });
columnList.push({LABEL : "<div class='headerDiv'>극수<p/>(P)</div>",						KEY : "ketPole"		,LEVEL : 1, Align : "Center",Width : 55	, CanSort : '0' });
columnList.push({LABEL : "시리즈",							KEY : "ketSeries"				, Width : 120	,LEVEL : 1, Align : "Center" , CanSort : '0'});
columnList.push({LABEL : "매칭터미널",							KEY : "ketMatchTerminal"				, Width : 120	,LEVEL : 1, Align : "Center" , Type : 'Lines', AcceptEnters : '1',CanSort : '0'});
columnList.push({LABEL : "매칭커넥터",						KEY : "ketMatchConnector"					,LEVEL : 1, Align : "Center" , Type : 'Lines', AcceptEnters : '1',CanSort : '0'});
columnList.push({LABEL : "비고",					KEY : "bigo"		, Width : 120		,LEVEL : 1, Align : "Center" ,Type : 'Lines', AcceptEnters : '1', CanSort : '0'});

var replacePart = {
		
	createPaingGrid : function(){
		
		
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'replacePartListGrid',
			Sync : 1,
			//useToolbar : false,//grid toolbar hide
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['replacePartListGrid'])?Grids['replacePartListGrid'].PageLength:CommonGrid.pageSize,
					partNoList : ''
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-partNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:replacePart.search(Value);',
			LeftCols : [
			            {Name : 'partNo', Width : 75, LEVEL : 1, Align : "Center", CanEdit : '0'},
			            {Name : 'companyName', Width : 55, LEVEL : 1, Align : "Center", CanEdit : '0'},
			            {Name : 'partName', Width : 200, LEVEL : 1, Align : "Center" , CanSort : '0', CanEdit : '0' },
			            {Name : 'partNopartForm', CanEdit : 0, CanSelect : 0, Type : "Html", Width : 80 , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'waterProof', Width : 55		,LEVEL : 1, Align : "Center", CanSort : '1' ,CanEdit : '0'},
			            {Name : 'mfType', Width : 60	,LEVEL : 1, Align : "Center", CanSort : '1' ,CanEdit : '0'},
			            {Name : 'pole', Align : "Center",Width : 40	, CanSort : '1' ,CanEdit : '0'},
			            {Name : 'seriesName', Width : 100,Align : "Center", CanSort : '0' ,CanEdit : '0'},
			            {Name : 'matchTerminal', Width : 90, Align : "Center",  CanSort : '0' ,CanEdit : '0'},
			            {Name : 'replaceGb', CanSort : 0, LEVEL : 1,Align : "Center", CanSort : '1',CanEdit : '0'}
					],
			Cols : [
			        	{Name : 'ketPartNo',        Width:75,  Align : 'Center', CanSort : '1', CanEdit : '0' ,CanEdit : '0'},
			            {Name : 'ketPartName',      Width:100, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketPartNopartForm',    CanEdit : 0, CanSelect : 0, Type : "Html", Width : 80 , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketWaterProof',     Align : "Center" ,Width : 55, CanSort : '0' ,CanEdit : '0'},
			            {Name : 'classification',     Width:80,Align : "Center", CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketPole',         Align : "Center",Width : 40	, CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketSeries',    Width:80, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketMatchTerminal',   Width : 80	,LEVEL : 1, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'ketMatchConnector',      Width:80,     Align : "Center" , CanSort : '0' ,CanEdit : '0'},
			            {Name : 'bigo',           Width:100,Align : "Center" , CanSort : '0' ,CanEdit : '0'}
			       ],
		    Header :{
		    			CanDelete : '0', CanEdit : '0', Align : 'Center',
		    			partNo : 'PartNo',
		    			companyName : '경쟁사',
		    			partName : '품명',
		    			partNopartForm : '제품형상',
		    			waterProof : '방수여부',
		    			mfType : "Male\r\nFemale",
		    			pole : "극수\r\n(P)",
		    			seriesName : '시리즈',
		    			matchTerminal : "Terminal\r\nPartNo",
		    			replaceGb : "Replace\r\n(O /△ /X)",
		    			ketPartNo : 'KET품번',
		    			ketPartName : "KET품명",
		    			ketPartNopartForm : "제품형상", 
		    			ketWaterProof : "방수여부",
		    			classification : "분류체계",	
		    			ketPole : "극수\r\n(P)",	
		    			ketSeries : "시리즈",
		    			ketMatchTerminal : "매칭터미널",
		    			ketMatchConnector : "매칭커넥터",
		    			bigo : "비고"
			        },
			SelectingSingle : '0',
			Panel : {
				Width : '20', Visible : '1',CanHide : '0'
			},
			excelDownloadFn : 'replacePart.excelDownload();'
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/replacePart/replacePartGridData.do?sortName=*Sort0';
			
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
		
		Grids[0].Data.Layout.Data.Cfg.Deleting = 0;
		
//		Grids.OnSort = function(grid, col, sort){
//
//			grid.Sort = sort;
//			$("#sortKey").val(sort);
//			Grids['replacePartListGrid'].Source.Data.Param.sortKey=sort;
//			Grids['replacePartListGrid'].ReloadBody();
//			
//			return -1;
//		}
		
		//row click event
		//Grids.OnClick = cost.goView;
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
		
		param += "&cols=" + JSON.stringify(dataCols);
		
		ajaxCallServer("/plm/ext/replacePart/createReplacePartListExcel", param, function(data){
            location.href = data.downloadLink;
        });
		
	},
	
	gridSearch : function(perPage, partNoList){
		
		if (!perPage)perPage = Grids['replacePartListGrid'].Source.Layout.Data.Cfg.PageLength;
		
		Grids['replacePartListGrid'].Source.Data.Url = '/plm/ext/replacePart/replacePartGridData.do?sortName=*Sort0';
		Grids['replacePartListGrid'].Source.Layout.Data.Cfg.PageLength = perPage;
		Grids['replacePartListGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
		Grids['replacePartListGrid'].Source.Data.Param.partNoList = JSON.stringify(partNoList);
		Grids['replacePartListGrid'].Source.Data.Param.formPage = perPage;
		Grids['replacePartListGrid'].ReloadBody();
	},

	
	search : function(perPage){
		if(!$("input[name='multiSearchFile']").val().trim() == ""){
			var dataParam = $("[name=searchForm]").serializefiles();

			ajaxCallServer("/plm/ext/replacePart/replacePartExcelExtract",dataParam, function(data) {
				var partNoList = data.partNoList;
				replacePart.gridSearch(perPage, partNoList);
				
		    }, false);
		}else{
			replacePart.gridSearch(perPage);
		}
		

		
	},
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	deleteDataSet : function(Rows, gubun){
		
		var paramDatas = new Array();
		
		for ( var i=0; i<Rows.length; i++ ) {
			var paramData = {};
			
			if(gubun == 1){
				paramData.oid = Rows[i].rivalPartOid;	
			}else{
				paramData.oid = Rows[i].ketPartOid;
			}
			
			paramDatas.push(paramData);
		}
		
		return JSON.stringify(paramDatas);
	},
	
	deletePart : function(gubun){
		
		var G = Grids[0];
		var paramDatas = new Array();
		
        if( G != null ){
        	var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('삭제 대상을 선택하십시오.');
                return;
            }else{
            	
            	if(gubun == 1){
            		if(!confirm("경쟁사 마스터를 삭제할 경우, 관련 KET부품정보도 모두 삭제되며\n복구 불가합니다.정말로 삭제하시겠습니까?")){
            			return;
            		}

            	}else{
            		if(!confirm("선택한 KET부품정보를 삭제할 경우 복구 불가합니다.\n정말로 삭제하시겠습니까?")){
            			return;
            		}
            	}
            	
            	ajaxCallServer("/plm/ext/replacePart/replacePartDelete","dels="+replacePart.deleteDataSet(R, gubun), function(data) {
            		alert("삭제되었습니다.");
            		replacePart.search();
    		    }, false);
            	
            }
        }
	},
	
	viewRivalPart : function(row){
		
		getOpenWindow2("/plm/ext/replacePart/replacePartPopup.do?oid=" + row.rivalPartOid+"&newPartNo=", row.rivalPartOid, 1400, 800);
	},
	
    newRivalPart : function(){
    	var newPartNo = $("#newPartNo").val();
    	if(newPartNo == ''){
    		alert("등록할 경쟁사 품번을 입력하십시오.");
    		$('[name="newPartNo"]').focus();
    	}else{
    		getOpenWindow2("/plm/ext/replacePart/replacePartPopup.do?oid=&newPartNo="+newPartNo, newPartNo, 1400, 800);	
    	}
		
	}
}