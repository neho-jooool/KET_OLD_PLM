var flag= false;
var teamWorkTimeReportProject = {
	
		
	/**
	 * server paging grid
	 */
	createPaingGrid : function(startDate, endDate, deptCode, pjtType){
		$('#listGrid1').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:teamWorkTimeReportProject.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamWorkTimeReportProjectList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					startDate : startDate,
					endDate : endDate,
					deptCode : deptCode,
					pjtType : pjtType
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'pjtTypeName',   Width:40,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'devType', 	  Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'pjtNo', 	  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtName', 	RelWidth:50,  Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'carName', 	   Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'customCompany', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},				
				{Name : 'pm', 	Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'state', 	Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayPlanStartDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayPlanEndDate', Type:'Date',	Format:'yyyy-MM-dd', Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayExecEndDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'oid', Visible : '0', Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
							pjtNo : "Project No", pjtNoRowSpan:'2',//
							pjtName : "Project Name", pjtNameRowSpan:'2',//
							carName : LocaleUtil.getMessage('02745'), carNameRowSpan:'2',//
							customCompany : LocaleUtil.getMessage('00859'), customCompanyRowSpan:'2',
							pm : "PM", pmRowSpan:'2',
							state : LocaleUtil.getMessage('01760'), stateRowSpan:'2',
							displayPlanStartDate : LocaleUtil.getMessage('07165'), displayPlanStartDateRowSpan:'2', displayPlanStartDateType:"Text",//
							displayPlanEndDate : '계획완료일', displayPlanEndDateRowSpan:'2',
							displayExecEndDate : '실제완료일', displayExecEndDateRowSpan:'2'
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id:"HeaderTop", 
							rowNum : 'No', rowNumRowSpan:'2',
							pjtTypeName : LocaleUtil.getMessage('02242'), pjtTypeNameRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
							pjtNo : "Project No", pjtNoRowSpan:'2',//
							pjtName : "Project Name", pjtNameRowSpan:'2',//
							carName : LocaleUtil.getMessage('02745'), carNameRowSpan:'2',//
							customCompany : LocaleUtil.getMessage('00859'), customCompanyRowSpan:'2',
							pm : "PM", pmRowSpan:'2',
							state : LocaleUtil.getMessage('01760'), stateRowSpan:'2',
							displayPlanStartDate : LocaleUtil.getMessage('07165'), displayPlanStartDateRowSpan:'2', planStartDateType:"Text",//
							displayPlanEndDate : LocaleUtil.getMessage('00798'), displayPlanEndDateType : "Text", 
							displayExecEndDate : LocaleUtil.getMessage('02055'), displayExecEndDateType : "Text"
						}
						]
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = teamWorkTimeReportProject.goView;
		
		
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamWorkTimeReportProjectList?sortName=*Sort0';
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
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=multiPieChart]').serialize());
		Grids['SampleSearchGrid'].Source.Data.Url = '/plm/ext/dashboard/teamWorkTimeReportProjectList?sortName=*Sort0';
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Param.deptCode=$('[name=deptCode').val();
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$('[name=startDate').val();
		Grids['SampleSearchGrid'].Source.Data.Param.endDate=$('[name=endDate').val();
		Grids['SampleSearchGrid'].Source.Data.Param.pjtType=$(':radio[name="pjtType"]:checked').val();
		Grids['SampleSearchGrid'].Source.Data.Param.carType=$('[name=carType]').val();
		Grids['SampleSearchGrid'].Source.Data.Param.vDevType=$('[name=vDevType]').val();
		Grids['SampleSearchGrid'].Source.Data.Param.vDevPattern=$('[name=vDevPattern]').val();
		Grids['SampleSearchGrid'].Source.Data.Param.level2=$('[name=level2]').val();
		Grids['SampleSearchGrid'].Reload();
		$('#listGrid').loadMask('loading...');
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
			}
		}
	}
	
}