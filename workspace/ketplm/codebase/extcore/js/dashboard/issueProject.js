var issueProject = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(customer,carType,year){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			//Sort : '-planStartDate',
			perPageOnChange : 'javascript:issueProject.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/issueProjectList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					customer : customer,
					carType  : carType,
					year     : year
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'pjtNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'issueType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'subject', RelWidth:50,	Width:150,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'urgency', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'importance', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'userName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'createDate', 	Width:100,  Align : 'Center', Type:'Date', Format:"yyyy-MM-dd" ,CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'isDone', 	Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'oid', Visible:'0',	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'mOid', Visible:'0', Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
			Header : 
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				issueType : LocaleUtil.getMessage('00969'), issueTypeRowSpan :'2',
				subject : LocaleUtil.getMessage('02524'), subjectRowSpan : '2',
				urgency : LocaleUtil.getMessage('04126'), 
				importance : LocaleUtil.getMessage('02693'),
				userName : LocaleUtil.getMessage('02523'),
				createDate : LocaleUtil.getMessage('02521'),
				isDone : LocaleUtil.getMessage('02176')
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = issueProject.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/issueProjectList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val(),
				customer  : customer,
				carType : carType,
				year  : year
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
			if(col == "pjtNo" && row.oid !="oid"){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "subject" && row.oid !="oid"){
				getOpenWindow2('/plm/jsp/project/projectIssueView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
		}
}