var TemplateAssessSheet = {
	
	//client Paging Grid
	createPagingGrid : function() {
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'TemplateAssessSheetSearchGrid',
			Sort : '-createStamp',
			perPageOnChange : 'javascript:TemplateAssessSheet.search(Value);',
			Data : {
				Url : '/plm/ext/project/gate/findPagingListTemplateAssess.do?sortName=*Sort0',
				Method : 'POST'
//					,
//				Format : 'Form',
//				Params : {
//					formPage : (Grids['TemplateAssessSheetSearchGrid'])?Grids['TemplateAssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
//				},
//				Param : {
//					formPage : (Grids['TemplateAssessSheetSearchGrid'])?Grids['TemplateAssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
//				}
			},
			Cols : [
			    {Name:'rowNum', Width:'30', RelWidth:'30', Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'division', Width:'50', RelWidth:'50', Align:'Center', CanSort:'1', CanEdit:'0', Type:'Enum', EnumKeys:$('[name=divisionEnumKey]').val(),  Enum:$('[name=divisionEnum]').val()},
			    {Name:'devType', Width:'50', RelWidth:'50', Align:'Center', CanSort:'1', CanEdit:'0', Type:'Enum', EnumKeys:$('[name=devTypeEnumKey]').val(),  Enum:$('[name=devTypeEnum]').val()},
			    {Name:'devDiv', Width:'50', RelWidth:'50', Align:'Center', CanSort:'1', CanEdit:'0', Type:'Enum', EnumKeys:$('[name=devDivEnumKey]').val(),  Enum:$('[name=devDivEnum]').val()},
//			    {Name:'prodCategory', Width:120, Align:'Center', CanSort:'1', CanEdit:'0', Type:'Enum', EnumKeys:$('[name=prodCategoryEnumKey]').val(),  Enum:$('[name=prodCategoryEnum]').val()},
			    {Name:'partName', Width:'80', RelWidth:'80', Align:'left', CanSort:'1', CanEdit:'0'},
			    {Name:'sheetName', Width:'280', RelWidth:'280', Align:'left', CanSort:'1', CanEdit:'0', Type : 'Html'},
			    {Name:'active', Width:'40', RelWidth:'40', Align:'Center', CanSort:'1', CanEdit:'0', Type:'Enum', EnumKeys:$('[name=activeEnumKey]').val(),  Enum:$('[name=activeEnum]').val()},
			    {Name:'creator', Width:'50', RelWidth:'50', Align:'Center', CanSort:'1', CanEdit:'0'},
				{Name:'createStamp', Width:'70', RelWidth:'70', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],
			Header :{
				CanDelete:'0', Align:'Center',
				rowNum :'No',
				division : LocaleUtil.getMessage('01662'),	//'사업부'
				devType : LocaleUtil.getMessage('00653'), //'개발유형',
				devDiv : LocaleUtil.getMessage('07234'), //'개발구분',
				partName : LocaleUtil.getMessage('02578'),//'제품구분',
				sheetName : LocaleUtil.getMessage('07147'),//'평가시트명',
				active : LocaleUtil.getMessage('01760'),//'상태',
				creator : LocaleUtil.getMessage('02431'),//'작성자',
				createStamp : LocaleUtil.getMessage('02428'),//'작성일'//LocaleUtil.getMessage('01951')//'수정일'
			}
		}), 'listGrid');
	
		Grids.OnClick = TemplateAssessSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findPagingListTemplateAssess.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				division : $('[name=division]').val(),
				devType : $('[name=devType]').val(),
				devDiv : $('[name=devDiv]').val(),
				prodCategory : $('[name=prodCategory]').val(),
				sheetName : $('[name=sheetName]').val(),
				active : $('[name=ative]').val(),
				status : $('[name=status]').val(),
				revReason : $('[name=revReason]').val()
			}
		}
		
		Grids.OnRenderFinish = function(){
         	var G = Grids[0];
         	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		G.SetString(tRow,'sheetName','<font color="#1660C7">'+tRow.sheetName+'</font>',1);
         	}
         }
	},
	
	
	search : function(perPage) {
		//$('[name=isSearch]').val('Y');
		if(!perPage) perPage = Grids['TemplateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['TemplateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
	    Grids['TemplateAssessSheetSearchGrid'].Source.Data.Url = '/plm/ext/project/gate/findPagingListTemplateAssess.do?sortName=*Sort0';
		Grids['TemplateAssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=TemplateAssessSheetSearchForm]').serialize());
		Grids['TemplateAssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['TemplateAssessSheetSearchGrid'].Reload();
		
	},
	
	clear : function() {
		$('[name=TemplateAssessSheetSearchForm]')[0].reset();
	},
	
	create : function() {
		$('[name=TemplateAssessSheetCreateForm]').attr('action', '/plm/ext/project/gate/createTemplateAssess.do');
		$('[name=TemplateAssessSheetCreateForm]').attr('encType', 'multipart/form-data');
		$('[name=TemplateAssessSheetCreateForm]').submit();
	},
	
	update : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows();
		if(rows=='') {
			alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
			return;
		}
		getOpenWindow2('/plm/ext/project/gate/updateTemplateAssessForm.do?oid='+rows[0].oid,'TemplateAssessSheetUpdatePopup',860,600);
	},
	
	remove : function() {
		var G = Grids[0];
		var rows = G.GetSelRows();
		if(rows=='') {
			alert(LocaleUtil.getMessage('01715'));//삭제할 항목을 선택하세요
			return;
		}
		if(confirm(LocaleUtil.getMessage('01707'))) {//삭제하시겠습니까?
			location.href = '/plm/ext/project/gate/deleteTemplateAssess.do?oid='+rows[0].oid;
		}
	},
	
	goList : function(perPage) {
		location.href = '/plm/ext/project/gate/listTemplateAssess.do';
	},
	
	goCreate : function(grid, row, col, x, y) {
//		getOpenWindow2('/plm/ext/project/gate/createTemplateAssessForm.do','TemplateAssessSheetCreatePopup',860,600);
        var url='/plm/ext/project/gate/createTemplateAssessForm.do';
        window.open(url,"createPop","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=860,height=600");
	},

	goView : function(grid, row, col, x, y) {
		if(col == 'sheetName' && row.oid) {
			//getOpenWindow2('/plm/ext/project/gate/viewTemplateAssessForm.do?oid='+row.oid,'TemplateAssessSheetViewPopup',860,600);
	        var url='/plm/ext/project/gate/viewTemplateAssessForm.do?oid='+row.oid;
	        window.open(url,"viewPop","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=860,height=600");

			//common.js 함수
			//openView(row.oid, 800, 600);
		}

	},

	selectAssessPopup : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows();
		
		if(rows=='') {
			alert(LocaleUtil.getMessage('00707'));//검색 항목을 선택하세요
			return;
		}
		opener.registProjectAssessSheet(rows[0].oid);
		//getOpenWindow2('/plm/ext/project/gate/updateTemplateAssessForm.do?oid='+,'TemplateAssessSheetUpdatePopup',900,650);
	}
	
}
