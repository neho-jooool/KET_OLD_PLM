var WorkTime = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-departName',
			perPageOnChange : 'javascript:WorkTime.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/workTimeTeamReportList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					division : $("[name=vdivision]").val(),
					startDate    : $("[name=startDate]").val(),
					endDate      : $("[name=endDate]").val(),
					userName      : $("[name=userName]").val(),
					pjtType :     $(':radio[name="pjtType"]:checked').val()
				}
			},
			Cols : [
                {Name : 'rowNum', Realwidth:30,	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'departName',   Width:120,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'duty', 	  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'userName', 	  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'work_load', 	  Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'havework', Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'havemaxwork', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'work_plan_sum', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'common', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'work_plan_project', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankSum', 	Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankS', 	Width:30,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankA', 	Width:30,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankB', 	Width:30,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankC', 	Width:30,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'rankR', 	Width:30,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'taskCnt', 	Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'ecrCnt', 	Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'ecoCnt', 	Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'oid', Visible:'0', 	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							departName : LocaleUtil.getMessage('04034'), departNameRowSpan:'2',//부서 
							duty : LocaleUtil.getMessage('02714'), dutyRowSpan:'2', //직급
							userName : LocaleUtil.getMessage('06147'), userNameRowSpan:'2',//이름
							work_load : "업무\n로드\n(%)", work_loadRowSpan:'2',//
							havework : "기준공수(H)", haveworkSpan:'2',
							work_plan_sum : "업무별계획공수(H)", work_plan_sumSpan:'3',
							rankSum: "프로젝트(건)", rankSumSpan:'6',
							taskCnt: "Task\n(건)", taskCntRowSpan:'2',
							ecrCnt : "ECM(건)", ecrCntSpan:"2"
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id:"HeaderTop", 
							rowNum : 'No', rowNumRowSpan:'2',
							departName : LocaleUtil.getMessage('04034'), departNameRowSpan:'2',//부서
							duty : LocaleUtil.getMessage('02714'), dutyRowSpan:'2',
							userName : LocaleUtil.getMessage('06147'), userNameRowSpan:'2',//
							work_load : "업무\n로드\n(%)",
							havework : "보유공수", 
							havemaxwork : "최대공수" ,
							work_plan_sum : "합계",
							common : "공통",
							work_plan_project : "프로젝트", sumCntType:"Text",
							rankSum: "계", 
							rankS: "S",
						    rankA: "A",
							rankB: "B",
							rankC: "C",
							rankR: "R",
							taskCnt: "Task\n(건)",
							ecrCnt: "ECR",
							ecoCnt: "ECO"
						}
						]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = WorkTime.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/workTimeTeamReportList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				startDate : $("[name=startDate]").val(),
				endDate  : $("[name=endDate]").val(),
				dateType     : $("#dateType option:selected").val(),
				userName:$("[name=userName]").val(),
				deptCode:$("[name=deptCode]").val(),
				division:$("[name=vdivision]").val(),
				startDate:$("[name=startDate]").val(),
				endDate:$("[name=endDate]").val(),
				pjtType:$(':radio[name="pjtType"]:checked').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
//		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$("[name=dateType]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$("[name=startDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.endDate=$("[name=endDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.userCode=$("[name=userCode]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.deptCode=$("[name=deptCode]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.duty=$("[name=duty]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.dateType=$("#dateType option:selected").val();
		Grids['SampleSearchGrid'].Source.Data.Param.pjtType=$(':radio[name="pjtType"]:checked').val();
		
//		Grids['SampleSearchGrid'].Source.Data.Param.division=$("[name=vdivision]").val();
		//alert(decodeURIComponent($('[name=workTimeReport]').serialize()));
		//var aa = decodeURIComponent($('[name=workTimeReport]').serialize());
		//alert(aa);
		//Grids['SampleSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=workTimeReport]').serialize());
		
		Grids['SampleSearchGrid'].Reload();
	},
	
	clear : function(){
		$('[name=SampleSearchForm]')[0].reset();
	},
	
	
	goList : function(){
		location.href = '/plm/ext/sample/list.do';
	},
	
	
	goView : function(grid,row,col,x,y){
	
		if(col != 'Panel' && row.oid){
			if(col == 'pjtNo'){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
			else if(col == 'subject'){
				getOpenWindow2('/plm/jsp/project/projectIssueView.jsp?oid='+row.issueOid+"&popup=popup",'SampleUpdatePopup',800,600);	
			}
//			location.href='/plm/ext/sample/updateForm.do?oid='+row.oid;
		}
	},
	
	viewStandard : function(){
		getOpenWindow2('/plm/ext/dashboard/standard/viewStandardForm.do','StandardPopup',900,620);
	}
}