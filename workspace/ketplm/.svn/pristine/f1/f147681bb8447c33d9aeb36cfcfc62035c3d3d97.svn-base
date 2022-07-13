var programProcessReport = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(type){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-programNo',
			perPageOnChange : 'javascript:programProcessReport.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/programProcessReportList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
					type     : type
				}
			},
			Cols : [
			        {Name : 'rowNum', 	Width:40, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
					{Name : 'programNo', 	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'programName', 	Width:180,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'lastCompany', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'customCompany', 	Width:130,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'userName', 	Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'carName', 	Width:90,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'formType', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
					{Name : 'moldCount', 	Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'pressCount', 	Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'beforeType', 	Width:120,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'currentType', 	Width:120,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'nextType', 	Width:120,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
					{Name : 'projectCount', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'}
			],
			Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							programNo : LocaleUtil.getMessage('07295'), programNoRowSpan:'2',//제목
							programName : LocaleUtil.getMessage('03044'), programNameRowSpan:'2',//'등록일'
							lastCompany : LocaleUtil.getMessage('02847'), lastCompanyRowSpan :'2',
							customCompany : LocaleUtil.getMessage('00859'), customCompanyRowSpan : '2',
							userName : LocaleUtil.getMessage('07294'), userNameRowSpan :'2',
							carName : LocaleUtil.getMessage('02745'), carNameRowSpan :'2',
							formType : LocaleUtil.getMessage('03223'), formTypeRowSpan:'2',
							moldCount : LocaleUtil.getMessage('07249'), moldCountSpan : '2',
							beforeType : LocaleUtil.getMessage('00851') ,beforeTypeSpan : '3',
							projectCount : LocaleUtil.getMessage('03046'), projectCountRowSpan :'2'
						}
					,
					{
						CanDelete : '0', CanEdit : '0', Align : 'Center',
						id : "HeaderTop",Spanned : '1',
						rowNum : 'No', rowNumRowSpan:'2',
						programNo : LocaleUtil.getMessage('07295'), pjtNoRowSpan:'2',//제목
						programName : LocaleUtil.getMessage('03044'), programNameRowSpan:'2',//'등록일'
						lastCompany : LocaleUtil.getMessage('02844'), issueTypeRowSpan :'2',
						customCompany : LocaleUtil.getMessage('00859'), subjectRowSpan : '2',
						userName : LocaleUtil.getMessage('07294'), 
						carName : LocaleUtil.getMessage('04122'),
						formType : LocaleUtil.getMessage('03223'),
						moldCount : "Mold", 
						pressCount : "Press",
						beforeType : LocaleUtil.getMessage('02322') ,
						currentType : LocaleUtil.getMessage('07319'),
						nextType : LocaleUtil.getMessage('01186'),
						projectCount : LocaleUtil.getMessage('03046'), projectCountRowSpan :'2'
					}
					]
			
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = programProcessReport.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/programProcessReportList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				type : $(':radio[name="division"]:checked').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=SampleSearchForm]').serialize());
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Param.type=$(':radio[name="division"]:checked').val();
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
		if(row.oid != undefined){
			if(col == "programName"){
				getOpenWindow2('/plm/ext/dashboard/programCard?programNo='+row.programNo+"&popup=popup",'SampleUpdatePopup',1200,1000);
			}else if(col == "projectCount"){
				getOpenWindow2('/plm/ext/dashboard/projectListPopup?programNo='+row.programNo+"&popup=popup",'SampleUpdatePopup',1000,700);
			}else if(col == "moldCount"){
				 $("#programNo").val(row.programNo);
				 $("#itemType").val("Mold");
				 window.open("","projectReportPopup","width=1000, height=500");
				 $("#programReportForm").attr({action:"/plm/ext/dashboard/moldTypeListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}else if(col == "pressCount"){
				$("#programNo").val(row.programNo);
				 $("#itemType").val("Press");
				 window.open("","projectReportPopup","width=1000, height=500");
				 $("#programReportForm").attr({action:"/plm/ext/dashboard/moldTypeListPopup", target:"projectReportPopup", method: "POST"}).submit();
			}
		}
	}
}