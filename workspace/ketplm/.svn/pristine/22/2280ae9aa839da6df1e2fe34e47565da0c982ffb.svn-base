var etcReport = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-displayExecEndDate',
			perPageOnChange : 'javascript:etcReport.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					division : $("[name=vdivision]").val(),
					startDate    : $("[name=startDate]").val(),
					endDate      : $("[name=endDate]").val(),
					processCode      : $("[name=processCode]").val(),
					trustCode      : $("[name=trustCode]").val()
				}
			},
			Cols : [
                {Name : 'rowNum', Realwidth:20,	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'},
                {Name : 'taskName',   Width:120,  Align : 'Left', CanSort : '1', CanEdit : '0' },
                {Name : 'taskRev', 	  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' },
                {Name : 'trustResult', 	  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' },
				{Name : 'taskUserName', 	  Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'displayPlanEndDate', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Type:'Date', Format:"yyyy-MM-dd"},
				{Name : 'displayExecEndDate', Width:90, Align : 'Center', CanSort : '1', CanEdit : '0',Type:'Date', Format:"yyyy-MM-dd"},
				{Name : 'devType', 	Width:90,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'pjtNo', 	Width:85,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'pjtName', 	Width:130,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'taskState', 	Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'period', 	Width:50,  Align : 'Right', CanSort : '1', CanEdit : '0' },
				{Name : 'assessCheck', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' },
				{Name : 'execenMon', 	Width:85,  Align : 'Right', CanSort : '1', CanEdit : '0' },
				{Name : 'departName', 	Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'userName', 	Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' },
				{Name : 'unusualCnt', 	Width:75,  Align : 'Right', CanSort : '1', CanEdit : '0' },
				{Name : 'taskCategory', 	Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' },
				{Name : 'processGb', 	Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' },
				{Name : 'oid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'mOid',  Visible : '0'	,Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id : "Header",Spanned : '1',
							rowNum : 'No',
							taskName : "TASK",
							taskRev : "차수",
							trustResult : "평가결과",
							taskUserName : "책임자",
							displayPlanEndDate : "계획완료일",
							displayExecEndDate : "실제완료일",
							devType : "개발구분",
							pjtNo : "Project No",
							pjtName : "Project Name",
							taskState : "Task 상태",
							period : "기간",
							assessCheck : "재평가여부\n체크",
							execenMon : "실제완료 월",
							departName : "개발부서",
							userName : "개발담당자",
							unusualCnt : "이상발생\n통보건수",
							taskCategory : "평가구분",
							processGb : "개발단계"
						}
						]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = etcReport.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/etcReportList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				startDate:$("[name=startDate]").val(),
				endDate:$("[name=endDate]").val(),
				processCode:$("[name=processCode]").val(),
				trustCode:$("[name=trustCode]").val()
			}
		}
	},
	
	search : function(perPage){
		if($("[name=startDate]").val() == ''){
			alert('실제완료일을 입력하세요.');
			$("[name=startDate]").focus();
			return;
		}
		if($("[name=endDate]").val() == ''){
			alert('실제완료일을 입력하세요.');
			$("[name=endDate]").focus();
			return;
		}
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Url = '/plm/ext/dashboard/etcReportList?sortName=*Sort0';
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$("[name=startDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.endDate=$("[name=endDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.processCode=$('[name=processCode]').val();
		Grids['SampleSearchGrid'].Source.Data.Param.trustCode=$('[name=trustCode]').val();
		Grids['SampleSearchGrid'].Reload();
	},
	
	clear : function(){
		$('[name=SampleSearchForm]')[0].reset();
	},
	
	
	
	goView : function(grid,row,col,x,y){
		if(row.mOid != 'mOid'){
			if(row.oid && col == 'pjtNo'){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
			else if(row.mOid && col == 'taskName'){
				openView(row.mOid);	
			}
		}
		
	}
}