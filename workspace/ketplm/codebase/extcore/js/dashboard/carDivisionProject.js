var carDivisionProject = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : 'sop',
			perPageOnChange : 'javascript:carDivisionProject.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/carDivisionProjectReportPopup?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout:  0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
                {Name : 'rowNum', 	Width:'30',  Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
				{Name : 'customer', Width:'40',  Align : 'Center', CanSort : '1', CanEdit : '0', Type:'Text', Spanned:'1'},
				{Name : 'carType', 	Width:'40',  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'formType', Width:'40',  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'modelName', RelWidth:'50',Width:'80', Align : 'Left', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'total',  Width:'60',    Align : 'Center', CanSort : '1', CanEdit : '0',Type:'Int', Format:",0",Spanned:'1'},
				{Name : 'currentStep', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'nextStep',    Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'displayState', Width:'30', Type:'Html',Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'sopDate', Width:'90', Align : 'Center', CanSort : '1', CanEdit : '0',Spanned:'1'},
				{Name : 'productTotal',Width:'40',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'completeCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'processCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1', Type:'Html'},
				{Name : 'delayCount', Width:'40', Visible: 0 ,Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1', Type:'Html'},
				{Name : 'taskTotal', Width:'40',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'completeTaskCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'processTaskCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1', Type:'Html'},
				{Name : 'delayTaskCount', Width:'40', Visible: 0 , Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1', Type:'Html'},
				{Name : 'moldTotal', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'moldCompleteCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'moldProcessCount', Width:'40', Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1',Type:'Html'},
				{Name : 'moldDelayCount', Width:'40', Visible:0,  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1', Type:'Html'},
				{Name : 'itemMoldTotal',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'itemPressTotal',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'itemGoodsTotal',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'issueTotal',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'qualityTotal',  Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'},
				{Name : 'sop', Visible:'0',Type:'Date' , Align : 'Center', CanSort : '0', CanEdit : '0',Spanned:'1'}
			],
			
			Head :[
					{
						CanDelete : '0', CanEdit : '0', Align : 'Center',
						id : "Header",Spanned : '1',
						rowNum : 'No', rowNumRowSpan:'2',
						customer : "OEM", customerRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
						carType : LocaleUtil.getMessage('04122'), carTypeRowSpan:'2',//제목
						formType : LocaleUtil.getMessage('03223'), formTypeRowSpan:'2',//'등록일'
						modelName : LocaleUtil.getMessage('01750'), modelNameRowSpan : '2',
						total : LocaleUtil.getMessage('01781'), totalRowSpan:'2',
						currentStep : LocaleUtil.getMessage('02729'), currentStepSpan:'2',
					    displayState : LocaleUtil.getMessage('03210'), displayStateRowSpan:'2',
					    sopDate : "SOP", sopDateRowSpan: "2",
					    productTotal : LocaleUtil.getMessage('02630'), productTotalSpan:"3",
					    taskTotal : LocaleUtil.getMessage('07194') , taskTotalSpan:"3",
					    moldTotal : LocaleUtil.getMessage('07188'), moldTotalSpan:"3",
					    itemMoldTotal : "Item", itemMoldTotalSpan :"3",
					    issueTotal : LocaleUtil.getMessage('07189'), issueTotalSpan: "2"
					},
					{
						CanDelete : '0', CanEdit : '0', Align : 'Center',
						id:"HeaderTop", 
						rowNum : 'No', rowNumRowSpan:'2',
						customer : "OEM", devTypeRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
						carType : LocaleUtil.getMessage('04122'), pjtNoRowSpan:'2',//제목
						formType : LocaleUtil.getMessage('03223'), pjtNameRowSpan:'2',//'등록일'
						modelName : LocaleUtil.getMessage('01750'), displayStateRowSpan : '2',
						total : LocaleUtil.getMessage('01781'),
						currentStep : LocaleUtil.getMessage('07190'),
						nextStep : LocaleUtil.getMessage('07191'),
						displayState : LocaleUtil.getMessage('03210'),
						sopDate : "SOP",
						productTotal : LocaleUtil.getMessage('07192'),
						completeCount : LocaleUtil.getMessage('02171'),
						processCount : LocaleUtil.getMessage('02726'),
						/*delayCount : LocaleUtil.getMessage('02703'),*/
						taskTotal : LocaleUtil.getMessage('07192'),
						completeTaskCount : LocaleUtil.getMessage('02171'),
						processTaskCount : LocaleUtil.getMessage('02726'),
						/*delayTaskCount : LocaleUtil.getMessage('02703'),*/
						moldTotal : LocaleUtil.getMessage('07192'),
						moldCompleteCount : LocaleUtil.getMessage('02171'),
						moldProcessCount : LocaleUtil.getMessage('02726'),
						/*moldDelayCount : LocaleUtil.getMessage('02703'),*/
						itemMoldTotal : "Mold",
						itemPressTotal : "Press",
						itemGoodsTotal : LocaleUtil.getMessage('00966'),
						issueTotal : LocaleUtil.getMessage('02296'),
						qualityTotal : LocaleUtil.getMessage('03024')
					}
					]
			
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = carDivisionProject.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/carDivisionProjectReportPopup?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength
			}
		};
		

		
//		Grids.OnRenderPageFinish = function(G){
//			alert("22");
//        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
//        		if(G.GetString(tRow,'processCount').indexOf('href') < 0){
//        			G.SetString(tRow,'processCount','<a href=\"javascript:opener.productProcessProjectPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+tRow.processCount+'</a>/'+'<a href=\"javascript:opener.productDelayProjectPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+'<span style=\"color:#ff0000\">'+tRow.delayCount+'</span>'+'</a>',1);
//        			G.SetString(tRow,'processTaskCount','<a href=\"javascript:opener.productProcessTaskPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+tRow.processTaskCount+'</a>/'+'<a href=\"javascript:opener.productDelayTaskPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+'<span style=\"color:#ff0000\">'+tRow.delayTaskCount+'</span>'+'</a>',1);
//        			G.SetString(tRow,'moldProcessCount','<a href=\"javascript:opener.moldProcessProjectPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+tRow.moldProcessCount+'</a>/'+'<a href=\"javascript:opener.moldDelayProjectPopup(\''+tRow.customer+'\',\''+tRow.carType+'\');\">'+'<span style=\"color:#ff0000\">'+tRow.moldDelayCount+'</span>'+'</a>',1);
//        		}
//        	}
//        	
//        };
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
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
			if(col == "productTotal"){
				opener.productTotalProjectPopup(row.customer,row.carType);
			}else if(col == "completeCount"){
				opener.productCompleteProjectPopup(row.customer,row.carType);
			}else if(col == "taskTotal"){
				opener.productTotalTaskPopup(row.customer,row.carType);
			}else if(col == "completeTaskCount"){
				opener.productCompleteTaskPopup(row.customer,row.carType);
			}else if(col == "moldTotal"){
				opener.moldTotalProjectPopup(row.customer,row.carType);
			}else if(col == "moldCompleteCount"){
				opener.moldCompleteProjectPopup(row.customer,row.carType);
			}else if(col == "itemMoldTotal"){
				opener.itemMoldListPopup(row.customer,row.carType,'Mold');
			}else if(col == "itemPressTotal"){
				opener.itemMoldListPopup(row.customer,row.carType,'Press');
			}else if(col == "itemGoodsTotal"){
				opener.itemGoodsListPopup(row.customer,row.carType,'구매품');
			}else if(col == "issueTotal"){
				opener.issueProjectPopup(row.customer,row.carType);
			}else if(col == "qualityTotal"){
				opener.quiltyProblemPopup(row.customer,row.carType);
			}else if(col == "carType"){
				opener.carTypePopup(row.carType, row.customer);
			}

	}
}