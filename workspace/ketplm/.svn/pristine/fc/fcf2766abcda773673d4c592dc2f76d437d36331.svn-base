var teamQulityTeamList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(displayCode, departName,startDate,endDate){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:teamQulityTeamList.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamQulityTeamListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					displayCode : displayCode,
					departName : departName,
					startDate : startDate,
					endDate : endDate
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
			        {Name : 'devType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
			        {Name : 'pjtNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'problem', 	Width:100,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'defectDivName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'defectTypeName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'occurName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayOccurDate', Type:'Date', Format:"yyyy-MM-dd",	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'partNo', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'carType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'occurDivName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'ecrNumber', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayCompleteDate', 	Width:90,  Align : 'Center', Type:'Date', Format:"yyyy-MM-dd" ,CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'dqmStateName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
			],
			Header : 
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', 
				devType : LocaleUtil.getMessage('07234'),
				pjtNo : "Project No", 
				pjtName : "Project Name", 
				problem : LocaleUtil.getMessage('01438'),
				defectDivName : LocaleUtil.getMessage('07272'),
				defectTypeName : LocaleUtil.getMessage('09004'),
				occurName : LocaleUtil.getMessage('07137'),
				displayOccurDate : LocaleUtil.getMessage('07138'), 
				partNo : "Part No",
				carType : LocaleUtil.getMessage('02745'),
				occurDivName : LocaleUtil.getMessage('07139'),
				displayCompleteDate : LocaleUtil.getMessage('02179'),
				ecrNumber : LocaleUtil.getMessage('07273'),
				dqmStateName : LocaleUtil.getMessage('01760')
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = teamQulityTeamList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamQulityTeamListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				displayCode  : displayCode,
				departName : departName,
				startDate : startDate,
				endDate : endDate
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=SampleSearchForm]').serialize());
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Reload();
	},
	
	clear : function(){
		$('[name=SampleSearchForm]')[0].reset();
	},
	
	create : function(){
		$('[name=SampleCreateForm]').attr('action', '/plm/ext/sample/create.do');
		$('[name=SampleCreateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=SampleCreateForm]').submit();
	},
	
	update : function(){
		$('[name=SampleUpdateForm]').attr('action', '/plm/ext/sample/update.do');
		$('[name=SampleUpdateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=SampleUpdateForm]').submit();		
	},
	
	remove : function(){
		$('[name=SampleUpdateForm]').attr('action', '/plm/ext/sample/delete.do');
		$('[name=SampleUpdateForm]').submit();		
	},
	
	goList : function(){
		location.href = '/plm/ext/sample/list.do';
	},
	
	goCreate : function(grid,row,col,x,y){
		getOpenWindow2('/plm/ext/sample/createForm.do','SampleCreatePopup',800,600);
	},
	
	goView : function(grid,row,col,x,y){
		if(col == 'pjtNo' && row.oid != undefined){
			getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
		}else if(col == 'partNo' && row.partNo != "Part No"){
			openViewPart(row.partNo+"");
		}else if(col == 'problem' && row.mOid != undefined){
			getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',1100,768);
		}
	}
}