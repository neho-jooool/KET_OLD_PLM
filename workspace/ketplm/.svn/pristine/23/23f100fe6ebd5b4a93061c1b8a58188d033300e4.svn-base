var prodDevCostReport = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(year,devType,pjtType){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:prodDevCostReport.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/prodDevCostReportProjectList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					devType  : devType,
					year     : year,
					pjtType	 : pjtType
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
			        {Name : 'devType', 	Width:65,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtNo', 	Width:85,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', RelWidth: 50, Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'totalBudget', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'totalResult', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'budgetCost', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'budgetCost1', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost1', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'budgetCost2', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost2', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'budgetCost3', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost3', 	Width:55,  Align : 'Right', CanSort : '0', CanEdit : '0' , Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'oid',Visible:'0', 	Width:55,  Align : 'Center', CanSort : '0', CanEdit : '0' , Spanned:'1'}
			],
			Head :[ 
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				devType: LocaleUtil.getMessage('00583'), devTypeRowSpan:'2',
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				totalBudget : LocaleUtil.getMessage('07187'), totalBudgetSpan:'2',
				budgetCost : LocaleUtil.getMessage('00997'), budgetCostSpan :'2',
				budgetCost1 : LocaleUtil.getMessage('01874'), budgetCost1Span :'2',
				budgetCost2 : "JIG", budgetCost2Span :'2',
				budgetCost3 : LocaleUtil.getMessage('00796'), budgetCost3Span :'2',
			},
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				id : "HeaderTop",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				devType : LocaleUtil.getMessage('00583'),
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				totalBudget : LocaleUtil.getMessage('02143'), totalBudgetType : "Text",
				totalResult : LocaleUtil.getMessage('02032'), totalResultType : "Text",
				budgetCost : LocaleUtil.getMessage('02143'), budgetCostType :"Text",
				resultCost : LocaleUtil.getMessage('02032'), resultCostType : "Text",
				budgetCost1 : LocaleUtil.getMessage('02143'), budgetCost1Type : "Text",
				resultCost1 : LocaleUtil.getMessage('02032'), resultCost1Type : "Text",
				budgetCost2 : LocaleUtil.getMessage('02143'), budgetCost2Type :"Text",
				resultCost2 : LocaleUtil.getMessage('02032'), resultCost2Type : "Text",
				budgetCost3 : LocaleUtil.getMessage('02143'), budgetCost3Type :"Text",
				resultCost3 : LocaleUtil.getMessage('02032'), resultCost3Type : "Text"
			}
			],
			
			Toolbar : {
				Cells : 'Reload,Export,Print,Columns,Link,Empty1,CostTxt,perPage,Formula',
	            Styles : '2', Space:'0', Kind:'Topbar', Link:'0',
				Empty1RelWidth:'1', Empty1Type:'Html',	
				CostTxtType:'Text', CostTxt: LocaleUtil.getMessage('01196'),	 CostTxtCanEdit:'0',	
				perPageType:'Enum', perPageWidth:'50', perPageEnumKeys:CommonGrid.pagingList, perPageEnum:CommonGrid.pagingNameList, perPage:CommonGrid.pageSize,
				perPageOnChange:'javascript:prodDevCostReport.search(Value);',
				Formula:"'전체페이지: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  전체개수: <b>'+count()+'</b> '"
			}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = prodDevCostReport.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/prodDevCostReportProjectList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				year  : year,
				devType  : devType,
				pjtType	 : pjtType
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
		if(col != 'Panel' && row.oid != "oid"){
			if(col == "pjtNo"){
			getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
			}
	}
}