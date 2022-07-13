var CreateTemplateGateCheckSheet = {
	
	//client Paging Grid
	createPaingGrid : function() {
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'CreateTemplateGateCheckSheetSearchGrid',
			Data : {
				Url : '/plm/ext/project/gate/findListTemplateGateCheckSheet.do?sortName=*Sort0', // 
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['CreateTemplateGateCheckSheetSearchGrid'])?Grids['CreateTemplateGateCheckSheetSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-checkSheetName',
			Cols : [
			    {Name:'rowNum', Width:60, Align:'Center', CanSort:'0', CanEdit:'1'},
			    {Name:'checkSheetName', Width:80, Align:'Center', CanSort:'1', CanEdit:'1'},
			    {Name:'achieveBase', Width:80, Align:'Center', CanSort:'0', CanEdit:'1'},
			    {Name:'unit', Width:60, Align:'Center', CanSort:'0', CanEdit:'1'}
			],
			Header :{
				CanDelete:'0', Align:'Center',
				rowNum :'No',
				checkSheetName : LocaleUtil.getMessage('02989'),//'평가항목',
				achieveBase : LocaleUtil.getMessage('07135'),//'달성기준',
				unit : LocaleUtil.getMessage('07136'),//'기준'
				//orderNo : '정렬순서',
				//createStamp : LocaleUtil.getMessage('01335'),//'등록일',
				//modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}
		}), 'listCheckGrid');
	
		Grids.OnClick = CreateTemplateGateCheckSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findListTemplateGateCheckSheet.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				rowNum : $('[name=rowNum]').val(),
				division : $('[name=checkSheetName]').val(),
				devType : $('[name=achieveBase]').val(),
				devDiv : $('[name=unit]').val()
			}
		}
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['TemplateGateCheckSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['TemplateGateCheckSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['TemplateGateCheckSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=TemplateGateCheckSheetSearchForm]').serialize());
		Grids['TemplateGateCheckSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['TemplateGateCheckSheetSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=TemplateGateCheckSheetSearchForm]')[0].reset();
	},
	
	create : function() {
		$('[name=TemplateGateCheckSheetCreateForm]').attr('action', '/plm/ext/project/gate/createTemplateGateCheck.do');
		$('[name=TemplateGateCheckSheetCreateForm]').attr('encType', 'multipart/form-data');
		$('[name=TemplateGateCheckSheetCreateForm]').submit();
	},
	
	update : function() {
		$('[name=TemplateGateCheckSheetUpdateForm]').attr('action', '/plm/ext/project/gate/updateTemplateGateCheck.do');
		$('[name=TemplateGateCheckSheetUpdateForm]').attr('encType', 'multipart/form-data');
		$('[name=TemplateGateCheckSheetUpdateForm]').submit();
	},
	
	remove : function() {
		$('[name=TemplateGateCheckSheetUpdateForm]').attr('action', '/plm/ext/project/gate/deleteTemplateGateCheck.do');
		$('[name=TemplateGateCheckSheetUpdateForm]').submit();
	},
	
	goList : function() {
		location.href = '/plm/ext/project/gate/listTemplateGateCheck.do';
	},
	
	goCreate : function(grid, row, col, x, y) {
		getOpenWindow2('/plm/ext/project/gate/createTemplateGateCheckForm.do','TemplateGateCheckSheetCreatePopup',900,600);
	},
	
	goView : function(grid, row, col, x, y) {
		if(col != 'Panel' && row.oid) {
			location.href = '/plm/ext/project/gate/updateTemplateGateCheckForm.do?oid='+row.oid;
		}
	},
	
	
	
	addRow : function() {
		alert(	'1');
		var G = Grids[0];
		var newRow = G.AddRow(G, G.GetLast(), true);
		//G.AddRowEnd(G);
		//G.AddRowBelow(G, G.GetLast(), false);
		//G.AddRowEndGrid(G, true);
		alert('3');

//	    if ( G != null ) {
//
//	        callMode = "completeChangeSchedule";
//	        callOid = oid;
//	        callHistoryNoteType = historyNoteType;
//
//	        G.Save();   // Project 일정 저장
//	    }
	}
}
