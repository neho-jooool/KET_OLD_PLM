var moldTypeList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(division,programNo, itemType){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:moldTypeList.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/moldTypeListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					division : division,
				    programNo: programNo,
				    itemType : itemType
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'devType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'partNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'partName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'level2', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'shrinkage', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'itemType', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'dieNo', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'moldType', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'}
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
							level2 : LocaleUtil.getMessage('06112'), 
							shrinkage : LocaleUtil.getMessage('01606'), 
							itemType : LocaleUtil.getMessage('00969'), 
							dieNo : "Die No" ,
							moldType : LocaleUtil.getMessage('07277')
						}
					
			
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = moldTypeList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/moldTypeListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				division : division,
				programNo: programNo,
			    itemType : itemType
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
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',1200,1000);
			}else if(col == "partNo" && row.partNo != "Part No"){
				openViewPart(row.partNo+"");
			}else if((col == "dieNo" && row.dieNo != null && row.dieNo != "미입력") && row.dieNo != "Die No"){
				openProject(row.dieNo);
			}
			/*if(col == "programName"){
				getOpenWindow2('/plm/ext/dashboard/programCard?programNo='+row.programNo+"&popup=popup",'SampleUpdatePopup',1200,1000);
			}else if(col == "projectCount"){
				getOpenWindow2('/plm/ext/dashboard/projectListPopup?programNo='+row.programNo+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "moldCount"){
				 $("#programNo").val(row.programNo);
				 $("#itemType").val("Mold");
				 window.open("","projectReportPopup","width=1000, height=500");
				 $("#programReportForm").attr({action:"/plm/ext/dashboard/moldTypeListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "pressCount"){
				$("#programNo").val(row.programNo);
				 $("#itemType").val("Press");
				 window.open("","projectReportPopup","width=1000, height=500");
				 $("#programReportForm").attr({action:"/plm/ext/dashboard/moldTypeListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}
*/		
	}
}