var moldProjectTotalStep = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(itemType,outsourcing,year, month,division){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			//Sort : '-planStartDate',
			perPageOnChange : 'javascript:moldProjectTotalStep.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/moldProjectTotalStepList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					outsourcing : outsourcing,
					itemType : itemType,
					year   : year,
					month  : month,
					division : division
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
			        {Name : 'moldCategory', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:160,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'stateState', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'taskName', 	Width:160,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'departName', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'userName', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'planEndDate', 	Width:90,  Align : 'Center',Type:'Date', Format:"yyyy-MM-dd", CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'execEndDate', 	Width:90,  Align : 'Center',Type:'Date', Format:"yyyy-MM-dd", CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'reason', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'oid', Visible:'0', 	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'mOid',Visible:'0', 	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
			],
			Header : 
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				moldCategory : LocaleUtil.getMessage('07281'), moldCategoryRowSpan:'2',
				rowNum : 'No', rowNumRowSpan:'2',
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				stateState : LocaleUtil.getMessage('01760'), stateStateRowSpan :'2',
				taskName : "Task ", taskNameRowSpan : '2',
				departName : LocaleUtil.getMessage('02672'), 
				userName : LocaleUtil.getMessage('07275'),
				planEndDate : LocaleUtil.getMessage('00822'),
				execEndDate : LocaleUtil.getMessage('02065'),
				reason : LocaleUtil.getMessage('03349')
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = moldProjectTotalStep.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/moldProjectTotalStepList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val(),
				outsourcing : outsourcing,
				itemType : itemType,
				year   : year,
				month  : month,
				division : division
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
			if(col == "pjtNo" && row.oid != "oid"){
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName" && row.oid != "oid"){
				getOpenWindow2('/plm/jsp/project/TaskView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
	}
}