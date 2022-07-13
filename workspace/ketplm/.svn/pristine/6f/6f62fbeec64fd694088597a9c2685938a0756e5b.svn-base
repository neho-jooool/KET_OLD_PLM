var teamWorkTimeReportPeople = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(startDate, endDate, deptCode){
		$('#listGrid4').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid4',
			Sort : '-displayName',
			perPageOnChange : 'javascript:teamWorkTimeReportPeople.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamWorkTimeReportPeopleList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid4'])?Grids['SampleSearchGrid4'].PageLength:CommonGrid.pageSize,
					startDate : startDate,
					endDate : endDate,
					deptCode : deptCode	
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'displayName',   Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'duty', 	  Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'displayTaskPlan', 	  Width:67,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
                {Name : 'displayTaskProcess', 	  Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'displayTaskComplete', 	  Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'displayTaskDelay', 	  Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtPlanNum', 	Width:67,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtProcessNum', 	Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtCompleteNum', 	Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtDelayNum', 	    Width:67,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'issueNum', 	    Width:68,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'qulityNum', 	    Width:68,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'oid', Visible : '0', Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							displayName : LocaleUtil.getMessage('07278'), displayNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							duty : LocaleUtil.getMessage('02715'), dutyRowSpan:'2',
							displayTaskPlan : "Task", displayTaskPlanSpan:'4',//
							pjtPlanNum : LocaleUtil.getMessage('03046'), pjtPlanNumSpan:'4',//
							issueNum : LocaleUtil.getMessage('02296'), issueNumRowSpan:'2',
							qulityNum : LocaleUtil.getMessage('07231'), qulityNumRowSpan:'2'
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id:"HeaderTop", 
							rowNum : 'No', rowNumRowSpan:'2',
							displayName : LocaleUtil.getMessage('07278'), displayNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							duty : LocaleUtil.getMessage('02715'), dutyRowSpan:'2',
							displayTaskPlan : LocaleUtil.getMessage('00798'),
							displayTaskProcess : LocaleUtil.getMessage('02726'),
							displayTaskComplete : LocaleUtil.getMessage('02171'),
							displayTaskDelay : LocaleUtil.getMessage('02703'),
							pjtPlanNum : LocaleUtil.getMessage('00798'),
							pjtProcessNum : LocaleUtil.getMessage('02726'),
							pjtCompleteNum : LocaleUtil.getMessage('02171'),
							pjtDelayNum : LocaleUtil.getMessage('02703'),
							issueNum : LocaleUtil.getMessage('02296'), 
							qulityNum : LocaleUtil.getMessage('07231'), 
						}
						]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid4');
		
		//row click event
		Grids.OnClick = teamWorkTimeReportPeople.goView;
		
		Grids.OnDataReceive = function(){ flag++; hideProcessing(); btnEnabled();}
		
		Grids.OnRenderPageFinish = function (G){
			if(G.cA == "Grids[0]"){
				$("#projectTotalCount").html("("+G.RootCount+")");
			}else if(G.cA == "Grids[1]"){
				$("#taskTotalCount").html("("+G.RootCount+")");
			}else if(G.cA == "Grids[2]"){
				$("#issueTotalCount").html("("+G.RootCount+")");
			}else if(G.cA == "Grids[3]"){
				$("#qulityTotalCount").html("("+G.RootCount+")");
			}else if(G.cA == "Grids[4]"){
				$("#peopleTotalCount").html("("+G.RootCount+")");
			}
		}
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Timeout = 0;
			
			if(grid.cA == "Grids[0]"){
				grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportProjectList?sortName=*Sort0';
			}else if(grid.cA == "Grids[1]"){
				grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportTaskList?sortName=*Sort0';
			}else if(grid.cA == "Grids[2]"){
				grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportIssueList?sortName=*Sort0';
			}else if(grid.cA == "Grids[3]"){
				grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportQualityList?sortName=*Sort0';
			}else if(grid.cA == "Grids[4]"){
				grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportPeopleList?sortName=*Sort0';
			}
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
		if(!perPage) perPage = Grids['SampleSearchGrid4'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid4'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid4'].Source.Data.Params = decodeURIComponent($('[name=workTimeReport]').serialize());
		Grids['SampleSearchGrid4'].Source.Data.Url = '/plm/ext/dashboard/teamWorkTimeReportPeopleList?sortName=*Sort0';
		Grids['SampleSearchGrid4'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid4'].Source.Data.Param.deptCode=$('[name=deptCode').val();
		Grids['SampleSearchGrid4'].Source.Data.Param.startDate=$('[name=startDate').val();
		Grids['SampleSearchGrid4'].Source.Data.Param.endDate=$('[name=endDate').val();
		Grids['SampleSearchGrid4'].Reload();
		$('#listGrid4').loadMask('loading...'); 
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
				if(row.pjtTypeName == "검토"){
					getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
				}else if(row.pjtTypeName == "제품"){
					getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
				}else{
					getOpenWindow2('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
				}
				
			}else if(col == "subject"){
				getOpenWindow2('/plm/jsp/project/projectIssueView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "taskName"){
				getOpenWindow2('/plm/jsp/project/TaskView.jsp?oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "displayName"){
				getOpenWindow2('/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}else if(col == "displayTaskPlan"){
				$("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamTaskProcessPlanPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "displayTaskProcess"){
				 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamTaskProcessPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "displayTaskComplete"){
				$("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamTaskProcessCompletePopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "displayTaskDelay"){
				 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamTaskProcessDelayPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "pjtPlanNum"){
				$("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamProjectPlanPopup", target:"projectReportPopup", method: "POST"}).submit();	
			}else if(col == "pjtProcessNum"){
		 		 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamProjectProcessPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "pjtDelayNum"){
				 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamProjectDelayPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "pjtCompleteNum"){
				 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamProjectProcessCompletePopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "issueNum"){
				 $("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamIssueListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "qulityNum"){
				$("#userName").val(row.displayName);
				 window.open("","projectReportPopup","width=1000, height=700");
				 $("#multiPieChart").attr({action:"/plm/ext/dashboard/teamQulityListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "problem" && row.mOid != undefined){
				getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',1100,768);
			}else if(col == "partNo" && row.partNo != "Part No"){
				openViewPart(row.partNo+"");
			}
			
		}
	}
	
}