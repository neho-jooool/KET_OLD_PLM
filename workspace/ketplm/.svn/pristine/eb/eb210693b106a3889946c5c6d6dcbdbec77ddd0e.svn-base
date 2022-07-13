var Sample = {
	/**
	 * client paging grid
	 */	
	createGrid : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'SampleSearchGrid',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST'
			},			
			Sort : '-createStamp',
			perPageOnChange : 'javascript:Sample.search(Value);',
			LeftCols : [
	            {Name : 'rowNum', Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'}
			],
			Cols : [
				{Name : 'name', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'title', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'createStamp', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyStamp', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				rowNum : 'No',
				name : LocaleUtil.getMessage('02276'),//이름
				title : LocaleUtil.getMessage('02524'),//제목
				createStamp : LocaleUtil.getMessage('01335'),//'등록일',
				modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Sample.goView;
	},
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-createStamp',
			perPageOnChange : 'javascript:Sample.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'name', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'title',	Width:100,RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'createStamp', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyStamp', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				rowNum : 'No',
				name : LocaleUtil.getMessage('02276'),//이름
				title : LocaleUtil.getMessage('02524'),//제목
				createStamp : LocaleUtil.getMessage('01335'),//'등록일'
				modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Sample.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/sample/findPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Url = '/plm/ext/sample/findPagingList.do?sortName=*Sort0';
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
			getOpenWindow2('/plm/ext/sample/updateForm.do?oid='+row.oid,'SampleUpdatePopup',800,600);
//			location.href='/plm/ext/sample/updateForm.do?oid='+row.oid;
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