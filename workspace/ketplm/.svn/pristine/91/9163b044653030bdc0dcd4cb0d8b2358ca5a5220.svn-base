var ecrProjectList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(devType, year, divisionFlag){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:ecrProjectList.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/ecrProjectList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
							devType : devType,
							year    : year,
							divisionFlag : divisionFlag
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
				{Name : 'devType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
				//{Name : 'pjtName', 	Width:180,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				//{Name : 'carType', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				//{Name : 'pjtTypeName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'ecrId', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'ecrName', RelWidth:50,Width:100, Align : 'Left', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'deptName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'userName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'pjtNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'createDate',Type:'Date', Format:"yyyy-MM-dd" ,Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'stateState', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'oid', Visible:'0',Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}
			],
			Header :
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				//carType : "차종", displayStateRowSpan : '2',
				//pjtTypeName : "구분",
				ecrId   : "ECR No.",
				ecrName : LocaleUtil.getMessage('00209'),
				deptName : LocaleUtil.getMessage('02425'),
				userName : LocaleUtil.getMessage('02431'),
				createDate : LocaleUtil.getMessage('04650'),
				stateState : LocaleUtil.getMessage('00771')
					
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = ecrProjectList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/ecrProjectList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				devType : devType,
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

			if(col == "pjtNo" && row.oid != "oid"){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "ecrId" && row.oid != "oid"){
				if(row.type == '제품'){
				    getOpenWindow2('/plm/jsp/ecm/reform/ViewEcrForm.jsp?redirectURL='+encodeURIComponent('/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid='+row.mOid),'SampleUpdatePopup',1000,770);
				}else if(row.type == '금형'){
					getOpenWindow2('/plm/jsp/ecm/reform/ViewEcrForm.jsp?redirectURL='+encodeURIComponent ('/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid='+row.mOid),'SampleUpdatePopup',1000,770);
				}
			}

	}
}