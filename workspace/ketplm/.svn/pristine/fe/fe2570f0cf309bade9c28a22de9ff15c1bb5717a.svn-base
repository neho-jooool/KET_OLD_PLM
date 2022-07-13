var moldCategoryData1 = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(customer, carType, itemType, pjtNo){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:moldCategoryData1.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/moldCategoryDataProjectList1?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					customer    : customer,
					carType : carType,
					itemType : itemType,
					pjtNo : pjtNo
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'devType', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'partNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'partName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'specPartType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'shrinkage', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'itemType', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'dieNo', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'moldCategory', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'}
			],
			Header :
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center',
							id : "Header",Spanned : '1',
							rowNum : 'No', 
							devType : LocaleUtil.getMessage('07234'), 
							pjtNo : "Project No",
							pjtName : "Project Name", 
							partNo : "Part No", 
							partName : "Part Name",
							specPartType : LocaleUtil.getMessage('06112'), 
							shrinkage : LocaleUtil.getMessage('01606'), 
							itemType : LocaleUtil.getMessage('00969'), 
							dieNo : "Die No" ,
							moldCategory : LocaleUtil.getMessage('07277')
						}
					
			
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = moldCategoryData1.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/moldCategoryDataProjectList1?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				customer    : customer,
				carType : carType,
				itemType : itemType,
				pjtNo : pjtNo
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=SampleSearchForm]').serialize());
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Param.type=$(':radio[name="division"]:checked').val();
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
		if(col == "pjtNo" && row.oid != undefined){
			getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
		}else if(row.partNo != "Part No" && col == "partNo"){
			openViewPart(row.partNo+"");
		}else if((row.dieNo != "Die No" && row.dieNo != null) && col =="dieNo"){
			openProject(row.dieNo);
		}
	}
}