var teamQualityTotalList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(pjtType,status, deptCode, startDate, endDate, vDevType, vDevPattern, carType, level2){
		$('#listGrid').loadMask('loading...'); 
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid3',
			Sort : '-problem',
			perPageOnChange : 'javascript:search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/teamQualityTotalListSetting?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid3'])?Grids['SampleSearchGrid3'].PageLength:CommonGrid.pageSize,
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
                {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'problem', RelWidth:50, Width:150,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'defectDivName', 	  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'defectTypeName', 	  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'occurName', 	  Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayOccurDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'partNo', 	    Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'carType', 	    Width:50,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'occurDivName',    Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayRequestDate', 	Type:'Date', Format:'yyyy-MM-dd',   Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'displayCompleteDate', 	Type:'Date', Format:'yyyy-MM-dd',  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'dqmStateName', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Header : {
			 		CanDelete : '0', CadEdit : '0', Align : 'Center',
			 		rowNum : 'No',
			 		problem  : LocaleUtil.getMessage('04630'),
			 		defectDivName : LocaleUtil.getMessage('07272'),
			 		defectTypeName : LocaleUtil.getMessage('01624'),
			 		occurName : LocaleUtil.getMessage('07137'),
			 		displayOccurDate : LocaleUtil.getMessage('07138'),
			 		partNo : 'Part No',
			 		carType : LocaleUtil.getMessage('02745'),
			 		occurDivName : LocaleUtil.getMessage('07139'),
			 		displayRequestDate : LocaleUtil.getMessage('02194'),
			 		displayCompleteDate : LocaleUtil.getMessage('04600'),
			 		dqmStateName : LocaleUtil.getMessage('01760')
			 	 }
			/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = teamQualityTotalList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dashboard/teamQualityTotalListSetting?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
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
			if(col == 'pjtNo'){
				getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup",'SampleUpdatePopup',800,600);
			}
			else if(col == 'problem' && row.mOid != undefined){
				getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid='+row.mOid+"&popup=popup",'SampleUpdatePopup',1100,768);
			}
			else if(col == 'partNo' && row.partNo != 'Part No'){
				openViewPart(row.partNo+"");
			}
	}
	
}