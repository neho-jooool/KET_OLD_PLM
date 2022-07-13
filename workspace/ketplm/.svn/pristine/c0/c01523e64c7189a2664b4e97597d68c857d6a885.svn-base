var Program = {
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ProgramGrid',
//			Sort : '-createStamp',
			perPageOnChange : 'javascript:Program.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
				{Name : 'programNo', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'programName', Width:100, RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'lastUsingBuyer', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'subContractor', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'state', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'programAdmin', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'carType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'projectsCount', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'startDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'endDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'sumary', Align : 'Center', CanSort : '0', CanEdit : '0', Type:'Html'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				programNo : LocaleUtil.getMessage('07295')/*프로그램 No*/,
				programName : LocaleUtil.getMessage('09202')/*'프로그램 명'*/,
				lastUsingBuyer : LocaleUtil.getMessage('02847')/*'최종사용처'*/,
				subContractor : LocaleUtil.getMessage('00859')/*'고객처'*/,
				state : LocaleUtil.getMessage('01760')/*'상태'*/,
				programAdmin : LocaleUtil.getMessage('07294')/*'관리자'*/,
				carType : LocaleUtil.getMessage('02745')/*'차종'*/,
				projectsCount : LocaleUtil.getMessage('03046')/*'프로젝트'*/,
				startDate : LocaleUtil.getMessage('02018')/*'시작일'*/,
				endDate : LocaleUtil.getMessage('02658')/*'종료일'*/,
				sumary : LocaleUtil.getMessage('03210')/*'현황'*/
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Program.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/program/findList.do';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
		
		Grids.OnRenderPageFinish = function(G){
			for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
				G.SetString(tRow, 'sumary','<img src="/plm/portal/icon/projectCard.png">',1);
			}
		}
	},
	
	createPaingGridPopup : function(){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ProgramGrid',
//			Sort : '-createStamp',
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
				{Name : 'programNo', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'programName', Width:100, RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'lastUsingBuyer', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'subContractor', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'state', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'programAdmin', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'carType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'projectsCount', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'startDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'endDate', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				programNo : LocaleUtil.getMessage('07295')/*프로그램 No*/,
				programName : LocaleUtil.getMessage('09202')/*'프로그램 명'*/,
				lastUsingBuyer : LocaleUtil.getMessage('02847')/*'최종사용처'*/,
				subContractor : LocaleUtil.getMessage('00859')/*'고객처'*/,
				state : LocaleUtil.getMessage('01760')/*'상태'*/,
				programAdmin : LocaleUtil.getMessage('07294')/*'관리자'*/,
				carType : LocaleUtil.getMessage('02745')/*'차종'*/,
				projectsCount : LocaleUtil.getMessage('03046')/*'프로젝트'*/,
				startDate : LocaleUtil.getMessage('02018')/*'시작일'*/,
				endDate : LocaleUtil.getMessage('02658')/*'종료일'*/
					
					
			},
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Program.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/program/findList.do';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
		Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids[0].Source.Data.Url = '/plm/ext/project/program/findList.do';
		Grids[0].Source.Data.Params = decodeURIComponent($('[name=ProgramSearchForm]').serialize());
		Grids[0].Source.Data.Param.formPage=perPage
		Grids[0].Reload();
	},
	
	selectProgram : function(){
		var G = Grids[0];
		var rows = G.GetSelRows(); //선택한 Row
        if(rows=='' || rows.length == 0) {
            alert(LocaleUtil.getMessage('01957'));//검색 항목을 선택하세요
            return;
        }
		eval('opener.'+callBackFn+'(G.GetSelRows())');
		window.close();
	},
	
	clear : function(){
		$('[name=ProgramSearchForm]')[0].reset();
	},
	
	goCreate : function(grid,row,col,x,y){
		getOpenWindow2('/plm/ext/project/program/createProgramForm.do','ProgramPopup',800,620);
	},
	
	goView : function(grid,row,col,x,y){
		if(row.oid && col == 'programNo'){
            getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid,'ProgramPopup',800,620);
		}else if(row.oid && col == 'programName'){
			getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid,'ProgramPopup',800,620);
		}else if(row.oid && col == 'projectsCount'){
			getOpenWindow2('/plm/ext/project/program/viewProgramForm.do?oid='+row.oid+'&selectTab=PROJECT','ProgramPopup',800,620);
		}else if(row.oid && col == 'sumary'){
			SearchUtil.programCard(row.programNo);
		}
	}
}