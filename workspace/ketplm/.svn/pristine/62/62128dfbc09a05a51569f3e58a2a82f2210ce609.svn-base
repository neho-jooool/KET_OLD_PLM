var TaskDelay = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(type,state,step,year,month,moldType,useCompleteType,division){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'TaskSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:TaskDelay.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/projectTaskDelayReport?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					type     : type,
					state    : state,
					step     : step,
					year     : year,
					month    : month,
					moldType : moldType,
					useCompleteType : useCompleteType,
					division : division
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
				{Name : 'moldCategory', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
				{Name : 'pjtNo', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'stateState', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'taskName', Width:100, Align : 'Left', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'departName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'userName', Width:60, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'displayPlanEndDate', Width:90, Align : 'Center', Type:'Date', Format:"yyyy-MM-dd", CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'displayExecEndDate', Width:90, Align : 'Center', Type:'Date', Format:"yyyy-MM-dd", CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'reason', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'oid',Visible:'0', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'mOid', Visible:'0',Width:100, Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'}
				],
			Head :[
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
				id : "Header",Spanned : '1',
				rowNum : 'No', rowNumRowSpan:'2',
				moldCategory : LocaleUtil.getMessage('07281'), moldCategoryRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
				pjtNo : "Project No", pjtNoRowSpan:'2',//제목
				pjtName : "Project Name", pjtNameRowSpan:'2',//'등록일'
				stateState : LocaleUtil.getMessage('01760'), stateStateRowSpan : '2',
				taskName : "Task", taskNameRowSpan : '2',
				departName : LocaleUtil.getMessage('07274'), departNameRowSpan : '2',
				userName : LocaleUtil.getMessage('01205'), userNameRowSpan:'2',
				displayPlanEndDate : LocaleUtil.getMessage('07297'), displayPlanEndDateSpan : '2', displayPlanEndDateType : "Text",
				displayExecEndDate : "",
				reason : LocaleUtil.getMessage('03349'), reasonRowSpan : '2'
			},
			{
				CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
				id:"HeaderTop", 
				rowNum : 'No', 
				moldCategory : LocaleUtil.getMessage('07281'),//이름LocaleUtil.getMessage('02276'),
				pjtNo : "Project No",//프로젝터 번호
				pjtName : "Project Name",//프로젝트명
				stateState : LocaleUtil.getMessage('01760'),
				taskName : "Task", taskNameRowSpan : '2',
				departName : LocaleUtil.getMessage('07274'),
				userName : LocaleUtil.getMessage('01205'), userNameRowSpan:'2',
				displayPlanEndDate : LocaleUtil.getMessage('00822'), displayPlanEndDateType : "Text",
				displayExecEndDate : LocaleUtil.getMessage('02065'), displayExecEndDateType : "Text",
				reason : LocaleUtil.getMessage('03349')
			}
			]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'taskListGrid');
		
		//row click event
		Grids.OnClick = TaskDelay.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/projectTaskDelayReport?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val(),
				type  : type,
				state : state,
				step  : step,
				year     : year,
				month    : month,
				moldType : moldType,
				useCompleteType : useCompleteType,
				division : division
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
		if(row.oid != "oid"){
			if(col == "pjtNo"){
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName"){
				getOpenWindow2('/plm/jsp/project/TaskView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
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