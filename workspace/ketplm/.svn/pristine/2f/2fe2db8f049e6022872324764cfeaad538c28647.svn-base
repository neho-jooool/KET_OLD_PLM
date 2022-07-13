var WorkTime = {
	/**
	 * client paging grid
	 */	
	createGrid : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'SampleSearchGrid',
			Data : {
				Url : '/plm/ext/sample/findList.do',
				Method : 'POST'
			},
			Sort : '-createStamp',
			perPageOnChange : 'javascript:WorkTime.search(Value);',
			LeftCols : [
	            {Name : 'rowNum', Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'}
			],
			Cols : [
				{Name : 'name', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'title', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'createStamp', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyStamp', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				rowNum : 'No',
				name : LocaleUtil.getMessage('02276'),//이름
				title : LocaleUtil.getMessage('02524'),//제목
				createStamp : LocaleUtil.getMessage('01335'),//'등록일',
				modifyStamp : LocaleUtil.getMessage('01951')//'수정일'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Sample.goView;
	},
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(type, division){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleSearchGrid',
			Sort : '-pjtNo',
			perPageOnChange : 'javascript:WorkTime.search(Value);',
			Data : {
				Url : '/plm/ext/dashboard/workTimeReportList?sortName=*Sort0',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize,
				    moldType : $("[name=moldType]:checked").val(),
					division : division,
					startDate    : $("[name=startDate]").val(),
					endDate      : $("[name=endDate]").val(),
					dateType     : $("#dateType option:selected").val()
				}
			},
			Cols : [
                {Name : 'rowNum', Realwidth:30,	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
                {Name : 'division', Visible: type !="mold"? 1:0,  Width:60,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'devType', 	Visible: type !="mold"? 1:0,  Width:70,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
                {Name : 'pjtNo', 	Visible: type !="mold"? 1:0,  Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'pjtName', 	Visible: type !="mold"? 1:0,  Width:200,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'customCompany', Visible: type !="mold"? 1:0,	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'lastCompany', 	Visible: type =="review"? 1:0,    Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'partNo', 	Visible: type !="review"? 1:0,    Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'carName', 	Visible: type !="review"? 1:0,    Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'dieNo', 	Visible: type =="mold"? 1:0,    Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'partName', 	Visible: type =="mold"? 1:0,    Width:80,  Align : 'Left', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'userName', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'planStartDate', Type:'Date', Format:'yyyy-MM-dd', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'execStartDate', Type:'Date', Format:'yyyy-MM-dd',	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'execEndDate', 	Type:'Date', Format:'yyyy-MM-dd', Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'stateState', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'expectTime', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				{Name : 'realTime', 	Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'},
				{Name : 'oid', Visible:'0', 	Width:200,  Align : 'Center', CanSort : '1', CanEdit : '0' ,Spanned:'1'}
				],
				Head :[
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id : "Header",Spanned : '1',
							rowNum : 'No', rowNumRowSpan:'2',
							division : LocaleUtil.getMessage('01662'), divisionRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
							pjtNo : "Project No", pjtNoRowSpan:'2',//
							pjtName : "Project Name", pjtNameRowSpan:'2',//
							customCompany : LocaleUtil.getMessage('00859'), customCompanyRowSpan:'2',
							lastCompany : LocaleUtil.getMessage('02847'), lastCompanyRowSpan:'2',
							partNo : "Part No", partNoRowSpan:'2',//
							carName : LocaleUtil.getMessage('04122'), carNameRowSpan:'2',//
							dieNo : "Die No", dieNoRowSpan:'2',//
							partName : "Part Name", partNameRowSpan:'2',//
							userName :"PM", userNameRowSpan:'2',
							planStartDate : LocaleUtil.getMessage('07165'), planStartDateRowSpan:'2', planStartDateType:"Text",//
							execStartDate : LocaleUtil.getMessage('02179'), execStartDateSpan:"2",
							stateState : LocaleUtil.getMessage('01760'), stateRowSpan:'2',
							expectTime: LocaleUtil.getMessage('07169'), expectTimeSpan:'2',
						},
						{
							CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
							id:"HeaderTop", 
							rowNum : 'No', rowNumRowSpan:'2',
							division : LocaleUtil.getMessage('01662'), divisionRowSpan:'2',//이름LocaleUtil.getMessage('02276'),
							devType : LocaleUtil.getMessage('07234'), devTypeRowSpan:'2',
							pjtNo : "Project No", pjtNoRowSpan:'2',//
							pjtName : "Project Name", pjtNameRowSpan:'2',//
							customCompany : LocaleUtil.getMessage('00859'), customCompanyRowSpan:'2',
							lastCompany : LocaleUtil.getMessage('02847'), lastCompanyRowSpan:'2',
							partNo : "Part No", partNoRowSpan:'2',//
							carName : LocaleUtil.getMessage('04122'), carNameRowSpan:'2',//
							dieNo : "Die No", dieNoRowSpan:'2',//
							partName : "Part Name", partNameRowSpan:'2',//
							userName :"PM", userNameRowSpan:'2',
							planStartDate : LocaleUtil.getMessage('07165'), planStartDateRowSpan:'2', planStartDateType:"Text",//
							execStartDate : LocaleUtil.getMessage('00798'), execStartDateType:"Text",
							execEndDate : LocaleUtil.getMessage('02055') , execEndDateType:"Text",
							stateState : LocaleUtil.getMessage('01760'), 
							expectTime: LocaleUtil.getMessage('07296'), 
							realTime: LocaleUtil.getMessage('02055')
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
			grid.Data.Page.Url = '/plm/ext/dashboard/workTimeReportList?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				startDate : $("[name=startDate]").val(),
				endDate  : $("[name=endDate]").val(),
				dateType     : $("#dateType option:selected").val(),
				pjtNo:$("[name=pjtNo]").val(),
				pjtName:$("[name=pjtName]").val(),
				userName:$("[name=userName]").val(),
				departName:$("[name=departName]").val(),
				division:$("[name=division]").val(),
				devType:$("[name=devType]").val(),
				state:$("[name=state]").val(),
				customCompany:$("[name=customCompany]").val(),
				lastCompany:$("[name=lastCompany]").val(),
				dateType:$("[name=dateType]").val(),
				startDate:$("[name=startDate]").val(),
				endDate:$("[name=endDate]").val(),
				partNo:$("[name=partNo]").val(),
				carName:$("[name=carName]").val(),
				rank:$("[name=rank]").val(),
				dieNo:$("[name=dieNo]").val(),
				partName:$("[name=partName]").val(),
				moldType1:$("[name=moldType1]").val(),
				dieCategory:$("[name=dieCategory]").val(),
				moldCategory:$("[name=moldCategory]").val(),
				making:$("[name=making]").val(),
				outsourcing:$("[name=outsourcing]").val(),
				moldType : $("[name=moldType]:checked").val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$("[name=startDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.endDate=$("[name=endDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.pjtNo=$("[name=pjtNo]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.pjtName=$("[name=pjtName]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.userName=$("[name=userName]").val();
		
		Grids['SampleSearchGrid'].Source.Data.Param.departName=$("[name=departName]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.division=$("[name=division]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.devType=$("[name=devType]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.state=$("[name=state]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.customCompany=$("[name=customCompany]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.lastCompany=$("[name=lastCompany]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.dateType=$("[name=dateType]").val();
		
		
		Grids['SampleSearchGrid'].Source.Data.Param.startDate=$("[name=startDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.endDate=$("[name=endDate]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.partNo=$("[name=partNo]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.carName=$("[name=carName]").val();
		
		Grids['SampleSearchGrid'].Source.Data.Param.rank=$("[name=rank]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.dieNo=$("[name=dieNo]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.partName=$("[name=partName]").val();
		
		Grids['SampleSearchGrid'].Source.Data.Param.moldType1=$("[name=moldType1]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.dieCategory=$("[name=dieCategory]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.moldCategory=$("[name=moldCategory]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.making=$("[name=making]").val();
		Grids['SampleSearchGrid'].Source.Data.Param.outsourcing=$("[name=outsourcing]").val();
		
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
	
	/**
	 * 개발산출문서 다중 선택
	 * @param checkedNode
	 */
	setDevDocCategory : function(checkedNode){
		var nodeIdStr, nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=projectDocType]').val(nodeIdStr);
		$('[name=projectDocTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 개발산출/기술문서 다중 선택
	 * @param checkedNode
	 */
	setDocCategory : function(checkedNode){
		var nodeIdStr='', nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=docType]').val(nodeIdStr);
		$('[name=docTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 기술문서 분류 다중 선택
	 * @param checkedNode
	 */
	setTechDocCategory : function(checkedNode){
		var nodeIdStr='', nodeNameStr='';
		for(var i=0; i < checkedNode.length; i++){
			if(i == checkedNode.length - 1){
				nodeIdStr += checkedNode[i].id;
				nodeNameStr += checkedNode[i].name;
			}else{
				nodeIdStr += checkedNode[i].id+',';	
				nodeNameStr += checkedNode[i].name+',';
			}
		}
		$('[name=techDocType]').val(nodeIdStr);
		$('[name=techDocTypeTxt]').val(nodeNameStr);
	},
	
	/**
	 * 파일 추가
	 */
	insertFileLine : function() {
	    //첨부파일 라인을 추가한다.
	    var tBody = document.getElementById("iFileTable");
	    var innerRow = tBody.insertRow();
	    var innerCell = innerRow.insertCell();
	    var filehtml = "";
	    filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
	    filehtml += "  <input name='secondaryFiles' type='file' class='txt_fieldRO' style='100%'>";
	    innerCell.innerHTML = filehtml;
	},
	
	/**
	 * 파일 삭제
	 */
	deleteFileLine : function() {
	    $('[name=iFileChk]').each(function(){
	        if($(this).is(':checked')){
	        	$(this).parent().remove();
	        }
	    });   
	}
}