var AssessSheet = {
	//client Paging Grid
	createGrid : function() {
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'AssessSheetSearchGrid',
			Data : {
				Url : '/plm/ext/project/gate/findListAssess.do',
				Method : 'POST'
			} ,
			Sort : '-createStamp',
			perPageOnChange : 'javascript:AssessSheet.search(Value);',
			Cols : [
			    {Name:'rowNum', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'division', Width:60, Align:'Center', CanSort:'1', CanEdit:'0'},
			    {Name:'devType', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'devDiv', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'prodCategory', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'sheetName', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'sheetDesc', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'active', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'status', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'revReason', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
				{Name : 'createStamp', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyStamp', Align : 'Center', CanSort : '1', CanEdit : '0'}
			   
			],
			Header :{
				CanDelete:'0', Align:'Center',
				rowNum :'No',
				division : '사업부',
				devType : '개발유형',
				devDiv : '개발구분',
				prodCategory : '제품목록',
				sheetName : '이름',
				sheetDesc : '설명',
				active : '활성여부',
				status : '상태',
				revReason : '개정사유',
				createStamp : LocaleUtil.getMessage('01335'),//'등록일',
				modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}
		}), 'listGrid');
	
		//row click event
		Grids.OnClick = AssessSheet.goView;
	},
	
	//client Paging Grid
	createPaingGrid : function() {
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'AssessSheetSearchGrid',
			Data : {
				Url : '/plm/ext/project/gate/findPagingListAssess.do?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['AssessSheetSearchGrid'])?Grids['AssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-createStamp',
			Cols : [
			    {Name:'rowNum', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'division', Width:60, Align:'Center', CanSort:'1', CanEdit:'0'},
			    {Name:'devType', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'devDiv', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'prodCategory', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'sheetName', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'sheetDesc', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'active', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'status', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
			    {Name:'revReason', Width:60, Align:'Center', CanSort:'0', CanEdit:'0'},
				{Name : 'createStamp', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyStamp', Align : 'Center', CanSort : '1', CanEdit : '0'}
			   
			],
			Header :{
				CanDelete:'0', Align:'Center',
				rowNum :'No',
				division : '사업부',
				devType : '개발유형',
				devDiv : '개발구분',
				prodCategory : '제품목록',
				sheetName : '이름',
				sheetDesc : '설명',
				active : '활성여부',
				status : '상태',
				revReason : '개정사유',
				createStamp : LocaleUtil.getMessage('01335'),//'등록일',
				modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}
		}), 'listGrid');
	
		Grids.OnClick = AssessSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findPagingListAssess.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				division : $('[name=division]').val(),
				devType : $('[name=devType]').val(),
				devDiv : $('[name=devDiv]').val(),
				prodCategory : $('[name=prodCategory]').val(),
				sheetName : $('[name=sheetName]').val(),
				sheetDesc : $('[name=sheetDesc]').val(),
				active : $('[name=active]').val(),
				status : $('[name=status]').val(),
				revReason : $('[name=revReason]').val()
			}
		}
	},
	
	search : function(perPage) {
		if(!perPage) perPage = Grids['AssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['AssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['AssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=AssessSheetSearchForm]').serialize());
		Grids['AssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['AssessSheetSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=AssessSheetSearchForm]')[0].reset();
	},
	
	
	goView : function(grid, row, col, x, y) {
		if(col != 'Panel' && row.oid) {
			location.href = '/plm/ext/project/gate/updateAssessForm.do?oid='+row.oid;
		}
	}
}
