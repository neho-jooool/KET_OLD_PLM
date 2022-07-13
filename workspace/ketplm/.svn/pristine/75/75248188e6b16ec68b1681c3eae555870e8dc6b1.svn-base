var moldTotalReport = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(programNo, itemType){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:moldTotalReport.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/moldTotalReportProjectList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					programNo : programNo,
					itemType  : itemType
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
				{Name : 'devType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
				{Name : 'pjtNo', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'partNo', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'partName', Width:150, Align : 'Left', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'shrinkage', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'itemType', Width:70, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'dieNo', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'type', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}
			],
			Header :
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				partNo : "Part No",
				partName : "Part Name",
				shrinkage : LocaleUtil.getMessage('01606'),
				itemType : LocaleUtil.getMessage('00969'),
				dieNo : "Die No",
				type : LocaleUtil.getMessage('07277')
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = moldTotalReport.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/moldTotalReportProjectList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				programNo : programNo,
				itemType  : itemType
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
			getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
//			location.href='/plm/ext/sample/updateForm.do?oid='+row.oid;
		}else if(row.partNo != "Part No" && col == "partNo"){
			openViewPart(row.partNo+"");
		}else if((row.dieNo != "Die No" && row.dieNo != null && row.dieNo != "미입력") && col =="dieNo"){
			openProject(row.dieNo);
		}
	}
}