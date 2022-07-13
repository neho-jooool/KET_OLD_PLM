var teamWorkTimeReportTask = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(startDate, endDate, deptCode, pjtType){
		$('#listGrid1').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid1',
			Sort : '-displayPlanEndDate',
			perPageOnChange : 'javascript:teamWorkTimeReportTask.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamWorkTimeReportTaskList?sortName=*Sort0',
				//Url : '/plm/ext/dashboard/teamWorkTimeReportTaskList',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid1'])?Grids['SampleSearchGrid1'].PageLength:CommonGrid.pageSize,
					startDate : startDate,
					endDate : endDate,
					deptCode : deptCode,
					pjtType : pjtType
				}
			},
			Cols : [
	                {Name : 'rowNum', 	Width:50, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
	                {Name : 'taskName', RelWidth:50,  Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'taskRev', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'taskResultSignal', 	Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'userName', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'deptName', 	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	              //  {Name : 'taskState', 	Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayPlanEndDate', Type:'Date',	Format:'yyyy-MM-dd', Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'displayExecEndDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	             //   {Name : 'pjtTypeName',   Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'devType', 	  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
	                {Name : 'pjtNo', 	  Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pjtName', 	  Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'state', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
					{Name : 'period', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'oid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'mOid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'type',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
					],
					Head :[
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id : "Header",Spanned : '1',
								rowNum : 'No', rowNumRowSpan:'2',
								taskName : "Task", taskNameRowSpan:'2',
								taskRev : LocaleUtil.getMessage('07125'), taskRevRowSpan:'2',
								taskResultSignal : LocaleUtil.getMessage('07141'), taskResultSignalRowSpan:'2',
								userName : LocaleUtil.getMessage('01205'), userNameRowSpan:'2',
								deptName : LocaleUtil.getMessage('01206'), deptNameRowSpan:'2',
								//taskState : LocaleUtil.getMessage('02736'), taskStateRowSpan:'2',
								displayPlanEndDate : '계획완료일', displayPlanEndDateRowSpan:'2',
								displayExecEndDate : '실제완료일', displayExecEndDateRowSpan:'2',
								//pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
								devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
								pjtNo : "Project No", pjtNoRowSpan:'2',//
								pjtName : "Project Name", pjtNameRowSpan:'2',//
								state : LocaleUtil.getMessage('01760'), stateRowSpan:'2',
								period : "기간", periodRowSpan:'2'	
							},
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id:"HeaderTop", 
								rowNum : 'No', rowNumRowSpan:'2',
								taskName : "Task", taskNameRowSpan:'2',
								taskRev : LocaleUtil.getMessage('07125'), taskRevRowSpan:'2',
								taskResultSignal : LocaleUtil.getMessage('07141'), taskResultSignalRowSpan:'2',
								userName : LocaleUtil.getMessage('01205'), deptNameRowSpan:'2',
								deptName : LocaleUtil.getMessage('01206'), deptNameRowSpan:'2',
								//taskState : LocaleUtil.getMessage('02736'), taskStateRowSpan:'2',
								displayPlanEndDate : LocaleUtil.getMessage('00798'),displayPlanEndDateType:"Text",
								displayExecEndDate : LocaleUtil.getMessage('02171'),displayExecEndDateType:"Text",
								//pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
								devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
								pjtNo : "Project No", pjtNoRowSpan:'2',//
								pjtName : "Project Name", pjtNameRowSpan:'2',//
								state : LocaleUtil.getMessage('01760'), stateRowSpan:'2',
								period : "기간", periodRowSpan:'2'	
							}
							]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid1');
		
		//row click event
		Grids.OnClick = teamWorkTimeReportTask.goView;
		
/*		Grids.OnRenderFinish = function(){
        	var G = Grids[1];
        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		if(tRow.state && tRow.state == '지연'){
        			G.SetString(tRow,'state',"<font color=red>"+tRow.state+"</font>",1);        			
        		}
        	}
        }*/
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportTaskList?sortName=*Sort0';
			//grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportTaskList';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				deptCode : $('[name=deptCode').val(),
				startDate : $('[name=startDate').val(),
				endDate : $('[name=endDate').val(),
				pjtType : $(':radio[name="pjtType"]:checked').val()
			}
		}
		
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid1'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid1'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid1'].Source.Data.Params = decodeURIComponent($('[name=workTimeReport]').serialize());
		Grids['SampleSearchGrid1'].Source.Data.Url = '/plm/ext/dashboard/teamWorkTimeReportTaskList?sortName=*Sort0';
		//Grids['SampleSearchGrid1'].Source.Data.Url = '/plm/ext/dashboard/teamWorkTimeReportTaskList';
		Grids['SampleSearchGrid1'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid1'].Source.Data.Param.deptCode=$('[name=deptCode').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.startDate=$('[name=startDate').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.endDate=$('[name=endDate').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.pjtType=$(':radio[name="pjtType"]:checked').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.carType=$('[name=carType]').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.vDevType=$('[name=vDevType]').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.vDevPattern=$('[name=vDevPattern]').val();
		Grids['SampleSearchGrid1'].Source.Data.Param.level2=$('[name=level2]').val();
		Grids['SampleSearchGrid1'].Reload();
		$('#listGrid1').loadMask('loading...'); 
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
	//해당 function은 죽어있음 실제로는 teamWorkTimeReportTeam의 goView를 사용함
		if(row.oid != "oid"){
			if(col == "pjtNo"){
				getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName"){
				var url = "";
				if(row.type == 'DR'){
					url = '/plm/jsp/project/TaskAssessView.jsp?oid='+row.mOid;
				}else if(row.type == 'GATE'){
					url = '/plm/jsp/project/TaskGateView.jsp?oid='+row.mOid;
				}else{
					url = '/plm/jsp/project/TaskView.jsp?oid='+row.mOid;
				}
				
				getOpenWindow2(url+"&popup=popup",'SampleUpdatePopup',800,700);
			}
		}
	}
	
}