var ecnProjectDelayList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(year, divisionFlag){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-ecnNo',
			perPageOnChange : 'javascript:ecnProjectDelayList.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/ecnProjectDelayList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
							year    : year,
							divisionFlag : divisionFlag
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
				{Name : 'ecnNo', 	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
				{Name : 'ecoNo', 	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'ecoName', RelWidth:'50',	Width:120,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'creator', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'pjtNo', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'stateApproName', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'receiveConfirm', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'jobComplete', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'jobCompleteDate',Type:'Date', Format:"yyyy-MM-dd" ,Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
			],
			Header :
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				ecnNo : "ECN No", devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
				ecoNo : "ECO No", devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
				ecoName : LocaleUtil.getMessage('00193'), pjtNoRowSpan:'2',//제목
				creator : LocaleUtil.getMessage('00187'), pjtNameRowSpan:'2',//'등록일'
				pjtNo : "Project No", displayStateRowSpan : '2',
				stateApproName : LocaleUtil.getMessage('02731'),
				receiveConfirm   : LocaleUtil.getMessage('01931'),
				jobComplete : LocaleUtil.getMessage('02108'),
				jobCompleteDate : LocaleUtil.getMessage('02179'),
					
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = ecnProjectDelayList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/ecnProjectDelayList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				year    : year,
				divisionFlag : divisionFlag
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
		}else if(col == 'ecoNo' && row.mOid != undefined){
			if(row.type == '제품'){
			    getOpenWindow2('/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',1024,768);
			}else if(row.type == '금형'){
				getOpenWindow2('/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',1024,768);
			}
		}else if(col == 'ecnNo' && row.mOid != undefined){
			if(row.type == '제품'){
			    getOpenWindow2('/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid='+row.mOid+"&tabName=tabEcn&popup=popup",'SampleUpdatePopup',1024,768);
			}else if(row.type == '금형'){
				getOpenWindow2('/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid='+row.mOid+"&tabName=tabEcn&popup=popup",'SampleUpdatePopup',1024,768);
			}
		}
	}
}