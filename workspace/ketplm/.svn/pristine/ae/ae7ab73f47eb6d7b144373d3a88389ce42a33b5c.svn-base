var Klog = {
	
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'Klog',
			Sort : '-logDate',
			perPageOnChange : 'javascript:Klog.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['Klog'])?Grids['Klog'].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
			    //{Name : 'changeUserId', Width: 200,  Align : 'Center', CanSort : '1', CanEdit : '0'},
			    {Name : 'changeUserName', Width: 80,  Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'ecoNo', Width: 100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'eventName', Width: 200,  Align : 'Left', CanSort : '1', CanEdit : '0'},
                {Name : 'eventResult', Width: 150, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'logDate', Width: 150, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'errMessage', Width: 200, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'logLog', Width: 200, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ecoState', Width: 150, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'reEventResult', Width: 100, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ecaName', Width: 100, Align : 'Center', CanSort : '1', CanEdit : '0'},
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				rowNum : 'No',
				//changeUserId : 'User ID',//ecoNo
				changeUserName : 'User 명',//ecoNo
				ecoNo : 'ECO No',//ecoNo
				eventName : '작업명',//ecoNo
				eventResult : '작업결과',//'최종이벤트'
				logDate : '로그 일시',//'로그 일시'
				errMessage : '에러메시지',//'에러메시지'
				logLog : '로그로그',//'로그로그'
				ecoState : 'ECO상태',//상태
			    reEventResult : '재작업결과', //'재작업 결과'
			    ecaName : 'ECA 명',//'ecaName'
			}/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Klog.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/klog/findPagingList.do?sortName=*Sort0&pageTotalSize='+ grid.RootCount;
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['Klog'].Source.Layout.Data.Cfg.PageLength;
		Grids['Klog'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['Klog'].Source.Data.Url = '/plm/ext/klog/findPagingList.do?sortName=*Sort0&pageTotalSize=-1';
		Grids['Klog'].Source.Data.Params = decodeURIComponent($('[name=KlogSearchForm]').serialize());
		Grids['Klog'].Source.Data.Param.formPage=perPage
		Grids['Klog'].Reload();
	},
	
	clear : function(){
		$('[name=KlogSearchForm]')[0].reset();
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
		if("errMessage" == col && "에러메시지" != row.errMessage){
			if(row.errMessage != null){
			alert(row.errMessage);
			}
		}else if("logLog" == col && "로그로그" != row.logLog){
			if(row.logLog != null){
			alert(row.logLog);
			}
		}else if("ecoNo" == col && "ECO No" != row.ecoNo){
			if(row.ecoOid.indexOf("KETProdChangeOrder") != -1){
				getOpenWindow2("/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + row.ecoOid,'KETProdChangePopup',1000,600);
			}else{
				getOpenWindow2("/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + row.ecoOid,'KETMoldChangePopup',1000,600);
			}
		}
			
			//getOpenWindow2('/plm/ext/sample/updateForm.do?oid='+row.oid,'SampleUpdatePopup',800,600);
//			location.href='/plm/ext/sample/updateForm.do?oid='+row.oid;
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