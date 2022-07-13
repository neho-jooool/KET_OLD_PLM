var ViewProjectDR = {
	
	//Assess Paging Grid
	createAssessPaingGrid : function() {
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'ViewProjectDRSearchGrid',
			Sort : 'orderNo',
			Data : {
				Url : '/plm/ext/project/gate/findListProjectGateResult.do?oid='+$('[name=pjtOid]').val()+'&sortName=*Sort0', // 
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['ViewProjectDRSearchGrid'])?Grids['ViewProjectDRSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},

			Header :{
				CanDelete:'0', Align:'Center',
				rowNum :'No',
				gateName : LocaleUtil.getMessage('00969'),//'구분',
				rsltStage : LocaleUtil.getMessage('01190'),//'단계',
				outputResult : LocaleUtil.getMessage('01716'),//'산출물',
				checkSheet : LocaleUtil.getMessage('02989'),//'평가항목',
				qltyPrbm : LocaleUtil.getMessage('03027'),//'품질문제',
				finalCheck : LocaleUtil.getMessage('07337'),//'최초평가',
				recovPlan : 'Recovery Plan'
			}
			,
			Cols : [
			    {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			    {Name:'orderNo', Width:80, Align:'Center', CanSort:'0', Visible:'0'},
			    {Name:'gateName', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1'},
			    {Name:'rsltStage', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1'},
			    {Name:'outputResult', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1'},
			    {Name:'checkSheet', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1'},
			    {Name:'qltyPrbm', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1'},
			    {Name:'finalCheck', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1'},
			    {Name:'recovPlan', Width:50, Align:'Center', CanSort:'0', CanEdit:'', Spanned:'1'},
			],
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
		}), 'listAssessGrid');
	
		//Grids.OnClick = ViewProjectDR.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findListProjectGateResult.do?oid='+$('[name=pjtOid]').val()+'&sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				gateName : $('[name=gateName]').val(),
				rsltStage : $('[name=rsltStage]').val(),
				outputResult : $('[name=outputResult]').val(),
				checkSheet : $('[name=checkSheet]').val(),
				qltyPrbm : $('[name=qltyPrbm]').val(),
				finalCheck : $('[name=finalCheck]').val(),
				recovPlan : $('[name=recovPlan]').val()
			}
		}
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['ViewProjectDRSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['ViewProjectDRSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['ViewProjectDRSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=ViewProjectDRSearchForm]').serialize());
		Grids['ViewProjectDRSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['ViewProjectDRSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=ViewProjectDRSearchForm]')[0].reset();
	},
	
	goView : function(grid, row, col, x, y) {
//		getOpenWindow2('/plm/ext/project/gate/viewTemplateAssessForm.do?oid='+row.oid,'TemplateAssessSheetViewPopup',900,650);
	}
	
	
}
