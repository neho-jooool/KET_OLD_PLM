var teamProjectDelayList = {
	
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(departName,userName,startDate,endDate){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtName',
			perPageOnChange : 'javascript:teamProjectDelayList.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamProjectDelayListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					departName	:	departName,
					userName	:	userName,
					startDate	:	startDate,
					endDate	:	endDate
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
					{Name : 'budgetCost', Width:90, Align : 'Right', CanSort : '0', CanEdit : '0',Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'resultCost', Width:90, Align : 'Right', CanSort : '0', CanEdit : '0',Type:'Int', Format:",0", Spanned:'1'},
					{Name : 'state', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
					{Name : 'oid',Visible:'0', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}
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
							displayPlanEndDate : LocaleUtil.getMessage('00647'), displayPlanEndDateSpan:'2', displayPlanEndDateType : "Text",//'수정일'
							displayExecEndDate : LocaleUtil.getMessage('02065'),//'수정일'
							term : LocaleUtil.getMessage('02733'), termRowSpan:'2',//'수정일'
							departName : LocaleUtil.getMessage('02672'), departNameRowSpan:'2',//'수정일'
							budgetCost : LocaleUtil.getMessage('02143'), budgetCostSpan:'2',//'수정일'
							resultCost : LocaleUtil.getMessage('02032'),resultCostRowSpan:'2',//'수정일'
							state : LocaleUtil.getMessage('01760'), stateRowSpan : '2'
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center',
							id : "HeaderTop",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							devType : LocaleUtil.getMessage('00583'), devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							pjtNo : "Project No", pjtNoRowSpan:'2',//제목
							pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
							displayPlanStartDate : LocaleUtil.getMessage('00590'), displayPlanStartDateRowSpan:'2', 
							displayPlanEndDate : LocaleUtil.getMessage('00822'), displayPlanEndDateType : "Text",//'수정일'
							displayExecEndDate : LocaleUtil.getMessage('02065'),displayExecEndDateType:"Text",//'수정일'
							term : LocaleUtil.getMessage('02733'), termRowSpan:'2',//'수정일'
							departName : LocaleUtil.getMessage('02672'), departNameRowSpan:'2',//'수정일'
							budgetCost : LocaleUtil.getMessage('02143'), budgetCostRowSpan:'2', budgetCostType:"Text",//'수정일'
							resultCost : LocaleUtil.getMessage('02032'),resultCostRowSpan:'2', resultCostType:"Text",//'수정일'
							state : LocaleUtil.getMessage('01760'), stateRowSpan : '2'
						}
						],
						
						Toolbar : {
							Cells : 'Reload,Export,Print,Columns,Link,Empty1,CostTxt,perPage,Formula',
				            Styles : '2', Space:'0', Kind:'Topbar', Link:'0',
							Empty1RelWidth:'1', Empty1Type:'Html',	
							CostTxtType:'Text', CostTxt: LocaleUtil.getMessage('01196'),	 CostTxtCanEdit:'0',	
							perPageType:'Enum', perPageWidth:'50', perPageEnumKeys:CommonGrid.pagingList, perPageEnum:CommonGrid.pagingNameList, perPage:CommonGrid.pageSize,
							perPageOnChange:'javascript:teamProjectDelayList.search(Value);',
							Formula:"'전체페이지: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  전체개수: <b>'+count()+'</b> '"
						}
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = teamProjectDelayList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamProjectDelayListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				departName	:	departName,
				userName	:	userName,
				startDate	:	startDate,
				endDate	:	endDate
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
			if(col == "pjtNo" && row.pjtTypeName == "검토"){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "pjtNo" && row.pjtTypeName == "제품"){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "pjtNo" && row.pjtTypeName == "금형"){
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
		}
	},
	
	/**
	 * 개발산출문서 다중 선택
	 * @param checkedNode
	 */
	setDevDocCategory : function(checkedNode){
		var nodeIdStr, nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=projectDocType]').val(nodeIdStr);
		$('[name=projectDocTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 개발산출/기술문서 다중 선택
	 * @param checkedNode
	 */
	setDocCategory : function(checkedNode){
		var nodeIdStr='', nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=docType]').val(nodeIdStr);
		$('[name=docTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 기술문서 분류 다중 선택
	 * @param checkedNode
	 */
	setTechDocCategory : function(checkedNode){
		var nodeIdStr='', nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=techDocType]').val(nodeIdStr);
		$('[name=techDocTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 파일 추가
	 */
	insertFileLine : function() {
	    //첨부파일 라인을 추가한다.
	    var tBody = document.getElementById("iFileTable");
	    var innerRow = tBody.insertRow();
	    var innerCell = innerRow.insertCell();
	    var filehtml = "";
	    filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
	    filehtml += "  <input name='secondaryFiles' type='file' class='txt_fieldRO' style='100%'>";
	    innerCell.innerHTML = filehtml;
	},
	
	/**
	 * 파일 삭제
	 */
	deleteFileLine : function() {
	    $('[name=iFileChk]').each(function(){
	        if($(this).is(':checked')){
	        	$(this).parent().remove();
	        }
	    });   
	}
}