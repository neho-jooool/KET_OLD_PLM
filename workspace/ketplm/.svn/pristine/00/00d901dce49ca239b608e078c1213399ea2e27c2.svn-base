var projectTaskProcess = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(programNo, pjtNo){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:projectTaskProcess.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/projectTaskProcessSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
				    programNo     : programNo,
				    pjtNo         : pjtNo
				}
			},
			Cols : [
	                {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'pjtNo', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					/*{Name : 'carType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},*/
					{Name : 'stateState', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'taskName', RelWidth:50, Width:100, Align : 'Left', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'departName', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'userName', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'displayPlanEndDate',	Width:90, Align : 'Center', CanSort : '0', CanEdit : '0', Type:'Date', Format:"yyyy-MM-dd" , Spanned:'1'}, //RelWidth:50
					{Name : 'displayExecEndDate', Width:90, Align : 'Center', CanSort : '0', CanEdit : '0',Type:'Date', Format:"yyyy-MM-dd", Spanned:'1'},
					{Name : 'reason', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'oid', Visible:'0', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'mOid', Visible:'0', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}

				],
				Head :[
				{
					CanDelete : '0', CanEdit : '0', Align : 'Center',
					id : "Header",Spanned : '1',
					rowNum : 'No', rowNumRowSpan:'2',
					pjtNo : "Project No", pjtNoRowSpan:'2',//제목
					pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
					/*carType : LocaleUtil.getMessage('02745'), carTypeRowSpan:'2',*/
					stateState : LocaleUtil.getMessage('01760'), stateStateRowSpan : '2',
					taskName :"Task" , taskNameRowSpan :'2',
					departName : LocaleUtil.getMessage('07274'), departNameRowSpan :'2',
					userName : LocaleUtil.getMessage('07275'), userNameRowSpan:'2',
					displayPlanEndDate : LocaleUtil.getMessage('07276'), displayPlanEndDateSpan:'2', displayPlanEndDateType:"Text",//'수정일'
					reason : LocaleUtil.getMessage('03349'), reasonRowSpan:'2',//'수정일'
					
				},
				{
					CanDelete : '0', CanEdit : '0', Align : 'Center',
					id : "HeaderTop",Spanned : '1',
					rowNum : 'No', rowNumRowSpan:'2',
					pjtNo : "Project No", pjtNoRowSpan:'2',//제목
					pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
					/*carType : LocaleUtil.getMessage('02745'), carTypeRowSpan:'2',*/
					stateState : LocaleUtil.getMessage('01760'), stateStateRowSpan : '2',
					taskName :"Task" , taskNameRowSpan :'2',
					departName : LocaleUtil.getMessage('07274'), departNameRowSpan :'2',
					userName : LocaleUtil.getMessage('07275'), userNameRowSpan:'2',
					displayPlanEndDate : LocaleUtil.getMessage('00822'), displayPlanEndDateRowSpan:'2', displayPlanEndDateType:"Text",//'수정일'
					displayExecEndDate : LocaleUtil.getMessage('02065'), displayExecEndDateType:"Text", 
					reason : LocaleUtil.getMessage('03349'), reasonRowSpan:'2',//'수정일'
				}
				]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = projectTaskProcess.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/projectTaskProcessSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				programNo     : programNo,
			    pjtNo         : pjtNo
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
		if(col != 'Panel' && row.oid){
			if(col == "pjtNo"){
			getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName"){
				getOpenWindow2('/plm/jsp/project/TaskView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
		}
	}
}