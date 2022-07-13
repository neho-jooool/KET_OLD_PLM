var teamTaskList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(pjtType, status, deptCode, startDate,endDate, vDevType, vDevPattern, carType, level2){
		$('#listGrid1').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid1',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamTaskListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid1'])?Grids['SampleSearchGrid1'].PageLength:CommonGrid.pageSize,
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
			},
			Cols : [
	                {Name : 'rowNum', 	Width:50, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
	                {Name : 'taskName', RelWidth:50,  Width:120,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'userName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	              //  {Name : 'taskState', 	Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayPlanEndDate', Type:'Date',	Format:'yyyy-MM-dd', Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayExecEndDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	             //   {Name : 'pjtTypeName',   Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'devType', 	  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'pjtNo', 	  Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	  Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'state', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'oid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'mOid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
					],
					Head :[
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id : "Header",Spanned : '1',
								rowNum : 'No', rowNumRowSpan:'2',
								taskName : "Task", taskNameRowSpan:'2',
								userName : LocaleUtil.getMessage('02773'), userNameRowSpan:'2',
								//taskState : LocaleUtil.getMessage('02736'), taskStateRowSpan:'2',
								displayPlanEndDate : LocaleUtil.getMessage('04600'), displayPlanEndDateSpan:'2',
								//pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
								devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
								pjtNo : "Project No", pjtNoRowSpan:'2',//
								pjtName : "Project Name", pjtNameRowSpan:'2',//
								state : LocaleUtil.getMessage('01760'), stateRowSpan:'2'
							},
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id:"HeaderTop", 
								rowNum : 'No', rowNumRowSpan:'2',
								taskName : "Task", taskNameRowSpan:'2',
								userName : LocaleUtil.getMessage('02773'), userNameRowSpan:'2',
								//taskState : LocaleUtil.getMessage('02736'), taskStateRowSpan:'2',
								displayPlanEndDate : LocaleUtil.getMessage('00798'),displayPlanEndDateType:"Text",
								displayExecEndDate : LocaleUtil.getMessage('02171'),displayExecEndDateType:"Text",
								//pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
								devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
								pjtNo : "Project No", pjtNoRowSpan:'2',//
								pjtName : "Project Name", pjtNameRowSpan:'2',//
								state : LocaleUtil.getMessage('01760'), stateRowSpan:'2'
							}
							]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid1');
		
		//row click event
		Grids.OnClick = teamTaskList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamTaskListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
					page : grid.GetPageNum(row),
					formPage : grid.PageLength,
					deptCode : deptCode,
					startDate : startDate,
					endDate : endDate,
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
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName"){
				getOpenWindow2('/plm/jsp/project/TaskView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,700);
			}
		}
	}
	
}