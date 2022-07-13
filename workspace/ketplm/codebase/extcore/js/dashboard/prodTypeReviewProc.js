var prodTypeReviewProc = {
	
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(level2){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:prodTypeReviewProc.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/prodTypeReviewProcSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
				    level2   : level2
				}
			},
			Cols : [
	                {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'devType', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
					{Name : 'pjtNo', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayPlanStartDate',	Width:80, Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Date', Format:"yyyy-MM-dd" , Spanned:'1'}, //RelWidth:50
					{Name : 'displayPlanEndDate', Width:80, Align : 'Center', CanSort : '0', CanEdit : '0',Type:'Date', Format:"yyyy-MM-dd", Spanned:'1'},
					{Name : 'displayExecEndDate', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Type:'Date', Format:"yyyy-MM-dd", Spanned:'1'},
					{Name : 'term', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'departName', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'budgetCost', Width:90, Align : 'Right', CanSort : '0', CanEdit : '0', Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost', Width:90, Align : 'Right', CanSort : '0', CanEdit : '0', Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'stateState', Width:70, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							devType : LocaleUtil.getMessage('00583'), devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							pjtNo : "Project No", pjtNoRowSpan:'2',//제목
							pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
							displayPlanStartDate : LocaleUtil.getMessage('00590'), displayPlanStartDateRowSpan:'2', displayPlanStartDateType:"Text",//'수정일'
							displayPlanEndDate : LocaleUtil.getMessage('00647'), displayPlanEndDateSpan:'2', 
							displayExecEndDate : LocaleUtil.getMessage('02065'),//'수정일'
							term : LocaleUtil.getMessage('02733'), termRowSpan:'2',//'수정일'
							departName : LocaleUtil.getMessage('02672'),departNameRowSpan:'2',//'수정일'
							budgetCost : LocaleUtil.getMessage('02143'), budgetCostSpan:'2',//'수정일'
							resultCost : LocaleUtil.getMessage('02032'),//'수정일'
							stateState : LocaleUtil.getMessage('01760'), stateStateRowSpan : '2'
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center',
							id:"HeaderTop", 
							rowNum : 'No', 
							type : LocaleUtil.getMessage('00583'),typeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							pjtNo : "Project No",//프로젝터 번호
							pjtName : "Project Name",//프로젝트명
							displayPlanStartDate : LocaleUtil.getMessage('00590'),//
							displayPlanEndDate : LocaleUtil.getMessage('00822'), displayPlanEndDateType:"Text",
							displayExecEndDate : LocaleUtil.getMessage('02065'), displayExecEndDateType:"Text",//
							term : LocaleUtil.getMessage('02733'),//
							departName : LocaleUtil.getMessage('02672'),//
							budgetCost : LocaleUtil.getMessage('02143'), budgetCostType:"Text",//
							resultCost : LocaleUtil.getMessage('02032'), resultCostType:"Text",//
							stateState : LocaleUtil.getMessage('01760')
						}
						],
						Toolbar : {
							Cells : 'Reload,Export,Print,Columns,Link,Empty1,CostTxt,perPage,Formula',
				            Styles : '2', Space:'0', Kind:'Topbar', Link:'0',
							Empty1RelWidth:'1', Empty1Type:'Html',	
							CostTxtType:'Text', CostTxt: LocaleUtil.getMessage('01196'),	 CostTxtCanEdit:'0',	
							perPageType:'Enum', perPageWidth:'50', perPageEnumKeys:CommonGrid.pagingList, perPageEnum:CommonGrid.pagingNameList, perPage:CommonGrid.pageSize,
							perPageOnChange:'javascript:prodTypeProdTotal.search(Value);',
							Formula:"'전체페이지: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  전체개수: <b>'+count()+'</b> '"
						}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = prodTypeReviewProc.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/prodTypeReviewProcSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				level2   : level2
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
		}
	}
	
}