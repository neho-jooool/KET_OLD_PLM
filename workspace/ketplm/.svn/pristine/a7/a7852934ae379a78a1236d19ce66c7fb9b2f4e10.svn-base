var teamIssueTotalList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(pjtType,status, deptCode, startDate, endDate, vDevType, vDevPattern, carType, level2){
		$('#listGrid2').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid2',
			Sort : '-createDate',
			perPageOnChange : 'javascript:search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamIssueTotalListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid2'])?Grids['SampleSearchGrid2'].PageLength:CommonGrid.pageSize,
					startDate : startDate,
					endDate : endDate,
					deptCode : deptCode,
					status : status,
					pjtType : pjtType,
					vDevType :  vDevType == '' ? null : vDevType,
				    vDevPattern : vDevPattern == '' ? null : vDevPattern,
					carType : carType,
					level2 : level2
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:50, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'pjtNo', 	  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtName', 	  Width:100,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'issueType', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'subject',  RelWidth:100,  Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'urgency', 	   Width:40,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'importance', 	   Width:40,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'userName', 	  Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'taskUserName', 	  Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'createDate', 	Width:80, Type:'Date', Format:'yyyy-MM-dd', Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'isDone', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},			
				{Name : 'attchCount', 	Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'oid', 	Visible : '0', Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'mOid', Visible : '0', 	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Header : {
					 		CanDelete : '0', CadEdit : '0', Align : 'Center',
					 		rowNum : 'No',
					 		pjtNo  : 'Project No',
					 		pjtName : 'Project Name',
					 		issueType : LocaleUtil.getMessage('00969'),
					 		subject : LocaleUtil.getMessage('02317'),
					 		urgency : LocaleUtil.getMessage('04126'),
					 		importance : LocaleUtil.getMessage('02693'),
					 		userName : LocaleUtil.getMessage('02523'),
					 		taskUserName : LocaleUtil.getMessage('04104'),
					 		createDate : LocaleUtil.getMessage('02521'),
					 		isDone : LocaleUtil.getMessage('01760'),
					 		attchCount : LocaleUtil.getMessage('02794')
					 	 }
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid2');
		
		//row click event
		Grids.OnClick = teamIssueTotalList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamIssueTotalListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				startDate : startDate,
				endDate : endDate,
				deptCode : deptCode,
				pjtType : pjtType,
				status : status,
				vDevType :  vDevType == '' ? null : vDevType,
				vDevPattern : vDevPattern == '' ? null : vDevPattern,
				carType : carType,
				level2 : level2
			}
		}
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
				getOpenWindow2('/plm/jsp/project/ProjectView.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "subject"){
				getOpenWindow2('/plm/jsp/project/projectIssueView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
		}
	}
	
}