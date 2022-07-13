var CreateStandard = {
	/**
	 * event grid 등록시에 사용됩니다.
	 */
	createEventGrid : function(){
		alert();
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ProgramEventGrid',
			Data : {
				Url : '/plm/ext/project/program/findEventByCarType.do'
			},
			useToolbar : false,//grid toolbar hide
			Cols : [
				{Name : 'carEventName', Width:200, RelWidth : 50, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'carEventDate', Width:150, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'customerEventName', Width:200, RelWidth : 50, Align : 'Center', CanSort : '0', CanEdit : '1', Type : 'Text'},
				{Name : 'gridCustomerEventDate', Width:150, Align : 'Center', CanSort : '0', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				carEventName : LocaleUtil.getMessage('09211'),//'차종 이벤트명'
				carEventDate : LocaleUtil.getMessage('02348'),//일정
				customerEventName : LocaleUtil.getMessage('09212'),//'고객 이벤트명'
				gridCustomerEventDate : LocaleUtil.getMessage('02348')//일정
			},
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		Grids.OnRenderFinish = function(){
			CalendarUtil.dateInputFormat('customerEventDate');
		}
		//grid 고객 이벤트명 중복체크
		Grids.OnAfterValueChanged  = function(G, row, col, val){
			for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
				var customerEventName = G.GetString(tRow,'customerEventName');
				if(row != tRow && val == customerEventName){
					alert(LocaleUtil.getMessage('09213'));//'동일한 고객 이벤트명이 존재합니다.\n다른 이름으로 변경 하시기 바랍니다.'
					G.SetString(row,'customerEventName','',1);
					break;
					
				}
			}
		}
	},
	
	/**
	 * project grid
	 */
	createProjectLinkGrid : function(isEditable){
		
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ProjectLinkGrid',
			Data : {
				Url : '/plm/ext/project/program/findProjectByProgram.do',
				Param : {
					programOid : $('[name=oid]').val()
				}
			},
			Sort : 'projectNo',
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Cols : [
		        {Name : 'projectNo', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
		        {Name : 'projectName', Width:50, RelWidth : 50, Align : 'Left', CanSort : '1', CanEdit : '0'},
		        {Name : 'planStartDate', Width:80,  Align : 'Center', CanSort : '1', CanEdit : '0'},
		        {Name : 'planEndDate', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
		        {Name : 'pm', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0'},
		        {Name : 'displayState', Align : 'Center', CanSort : '0', CanEdit : '0'},
		        {Name : 'checkedEvent', Align : 'Center', CanSort : '0', CanEdit : '0',Type : 'Html'},
		        {Name : 'sumary', Align : 'Center', CanSort : '0', CanEdit : '0',Type : 'Html'}
		    ],Header :{
	        	CanDelete : '0', Align : 'Center',
	        	projectNo : LocaleUtil.getMessage('03104'),//'Project No'
	        	projectName : LocaleUtil.getMessage('00395'),//'Project Name'
	        	planStartDate : LocaleUtil.getMessage('02018'),//'시작일'
	        	planEndDate : LocaleUtil.getMessage('02179'),//완료일
	        	pm : LocaleUtil.getMessage('00370'),//'PM'
	        	displayState : LocaleUtil.getMessage('01760'),//'상태'
	        	checkedEvent : LocaleUtil.getMessage('09214'),//'일정Check'
	        	sumary : LocaleUtil.getMessage('03210')//'현황'
	        },
	        Panel : isEditable?{
	        	Width : '21', Visible : '1',CanHide : '0'
	        }:null
		}),'listGrid2');
		
		Grids.OnRenderFinish = function(G){
        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		if(tRow.checkedEvent == '1'){
        			G.SetString(tRow,'checkedEvent',LocaleUtil.getMessage('03226')+"<input type=hidden name=checkedEvent value=1>",1);        			
        		}else if(tRow.checkedEvent == '0'){
        			G.SetString(tRow,'checkedEvent',LocaleUtil.getMessage('09215')+"<input type=hidden name=checkedEvent value=0>",1);
        		}
        		G.SetString(tRow, 'sumary','<img src="/plm/portal/icon/projectCard.png">',1);
        	}
        	//첫번째 Grid(일정 grid)의 OnRenderFinish event까지 먹어버린다.
        	CalendarUtil.dateInputFormat('customerEventDate',CreateStandard.checkDateValidation,{
        		beforeShow : function(inputElement, picker){
        			var index = $.inArray(inputElement,$('[name=customerEventDate]'));
        			if(index > 0){
        				var parentSelectedDay = $('[name=customerEventDate]').eq(index-1).val();
        				var nextDayOfSelectedDate = new Date(parentSelectedDay);
        				nextDayOfSelectedDate.setDate(nextDayOfSelectedDate.getDate() + 1);
        				$(inputElement).datepicker('option','minDate', nextDayOfSelectedDate);        				
        			}
        		}
        	});
        	UpdateProgram.changeColor(G);
        }
		
		Grids.OnClick = function(grid,row,col,x,y){
			if(col == 'sumary'){
				SearchUtil.projectCard(row.projectOid, row.projectNo);
			}else if(col == 'projectNo'){
				openView(row.projectOid);
			}
		}
	},
	
	/**
	 * 상위추가
	 */
	addRowAbove : function() {
        var G = Grids[0];    //대상 그리드
        var addedRow;
        var rows = G.GetSelRows(); //선택한 Row
        if(rows=='') {
            alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
            return;
        }
        if(G.RowCount<=0) { 
        	addedRow = G.AddRow(null, null, 7); //Row가 없으면 맨하위에 추가 parent와 next 모두 null이면 맨 하위에 Empty Row추가
        	this.setDefaultString(addedRow);
        	return;
        }
        addedRow = G.AddRow(G, rows[0], 7);    //선택한 Row 상위추가
        this.setDefaultString(addedRow);
    },
	
    /**
     * 하위추가
     */
    addRowBelow : function() {
        var G = Grids[0];
        var addedRow;
        var rows = G.GetSelRows(); //선택한 Row
        if(rows=='') {
        	alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
        	return;
        }
        if(G.RowCount<=0) {
        	addedRow = G.AddRow(null, null, 7);
            this.setDefaultString(addedRow);
            return;
        }
        var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
        if(nextRow==null) {    
        	addedRow = G.AddRow(null, null, 7);    //선택한 Row 다음 Row가 없으면 맨하위 추가
            this.setDefaultString(addedRow);
        }else {
        	addedRow = G.AddRow(G, nextRow, true);    //선택한 Row 하위에 추가
            this.setDefaultString(addedRow);
        }
    },
	
    /**
     * 삭제
     */
	removeRow : function(){
		var G = Grids[0];
        if(G.RowCount<=0) {
            G.AddRow(null, null, 7);
            return;
        }
        var rows = G.GetSelRows(); //선택한 Row
        var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
        if(rows=='' || rows.length == 0) {
            alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
            return;
        }
        if(!rows[0].carEventOid){
        	G.RemoveRow(rows[0]);    //선택한 Row 삭제
        }
	},
	
	setDefaultString : function(row){
		var G = Grids[0];
		row.NoColor = 2;
		G.SetString(row, 'carEventName','-',1);
	    G.SetString(row, 'carEventDate','-',1);
	    var dateField = "<input type='text' name='customerEventDate' class='txt_field' style='width:100px'>" +
	    		"<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"customerEventDate\");' style='cursor: hand;'>"
	    G.SetAttribute(row, 'gridCustomerEventDate','HtmlPrefix',dateField,1,0);
	    //재 binding
	    CalendarUtil.dateInputFormat('customerEventDate');
	    
	},
	
	clear : function(){
		window.close;
	},
	
	/**
	 * 프로그램 저장
	 */
	create : function(){
		if(!confirm(LocaleUtil.getMessage('02463'))){//'저장하시겠습니까?'
			return;
		}
		
		//등록 정보 검증
		if(!this.validation()){
			return;
		}
		
		//grid data 조합
		var G = Grids[0];
		var eventArry = '';
		var i = 0;
		
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			eventArry += '&events['+i+'].carEventOid='+ (typeof(tRow.carEventOid) != 'undefined'?tRow.carEventOid:'');
			eventArry += '&events['+i+'].carEventName='+ (G.GetString( tRow, 'carEventName') == '-'?'':G.GetString( tRow, 'carEventName'));
			eventArry += '&events['+i+'].carEventDate='+ (G.GetString( tRow, 'carEventDate') == '-'?'':G.GetString( tRow, 'carEventDate'));
			eventArry += '&events['+i+'].customerEventName='+ G.GetString( tRow, 'customerEventName');
			eventArry += '&events['+i+'].customerEventDate='+ $('input[name=customerEventDate]')[i].value;
			i++;
	    }
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/project/program/createProgram.do',
            data: $('[name=ProgramCreateForm]').serialize()+'&'+eventArry,
            success: function (data) {
                alert(LocaleUtil.getMessage('02460'));//'저장되었습니다.'                
                UpdateProgram.goView(data.value.oid);
//                //저장 버튼 숨김
//                $('#programSaveBtn').hide();
//                //관련 프로젝트 활성화
//                $('#tabs').tabs('enable');
//                $('#tabs').tabs({active : 1});
//                $('[name=oid]').val(data);
//                //프로그레스바 숨기기
//                hideProcessing();
            },
            error : function(){
            	alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	/**
	 * 프로젝트 link 저장
	 */
	saveProjectLink : function(){
		var projectArry = this.getProjectParamStr();
//		if(!projectArry || projectArry == ''){
//			alert('추가된 프로젝트 정보가 없습니다.');
//			return;
//		}
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/project/program/saveProjectLink.do',
            data: $('form').serialize()+projectArry,
            success: function (data) {
            	alert(LocaleUtil.getMessage('02460'));//'저장되었습니다.' 
                UpdateProgram.goView(data.oid, 'PROJECT');
            },
            error : function(){
            	alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	/**
	 * 프로그램 link 삭제
	 */
    removeProjectLink : function(){
    	var G = Grids[1];
        var rows = G.GetSelRows(); //선택한 Row
        var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
        if(rows=='' || rows.length == 0) {
            alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
            return;
        }
        for(var i=0;i<rows.length;i++){
        	G.RemoveRow(rows[i]);    //선택한 Row 삭제        	
        }
    },
	
	validation : function(){
		if(!$('[name=programName]').val()){
			alert(LocaleUtil.getMessage('09216'));//'프로그램명을 입력하세요'
			$('[name=programName]').focus();
			return false;
		}
		if(!$('[name=subContractor]').val()){
			alert(LocaleUtil.getMessage('09217'));//'고객처를 입력하세요'
			$('[name=subContractorTxt]').focus();
			return false;
		}
		if(!$('[name=divisionCode]').val()){
			alert(LocaleUtil.getMessage('01666'));//'사업부를 선택하세요'
			$('[name=divisionCode]').multiselect('open');
			return false;
		}
		if(!$('[name=lastUsingBuyer]').val()){
			alert(LocaleUtil.getMessage('09218'));//'최종사용처 입력하세요'
			$('[name=lastUsingBuyerTxt]').focus();
			return false;
		}
		if(!$('[name=carType]').val()){
			alert(LocaleUtil.getMessage('09219'));//'차종을 입력하세요'
			$('[name=carTypeTxt]').focus();
			return false;
		}
		if(!$('[name=programAdmin]').val()){
			alert(LocaleUtil.getMessage('09220'));//'프로그램 관리자를 입력하세요'
			$('[name=programAdminTxt]').focus();
			return false;
		}
		var G = Grids[0];
		var i=0;
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			if(G.GetString( tRow, 'customerEventName') == ''){
				alert(LocaleUtil.getMessage('09221'));//'추가된 고객 이벤트명을 입력하세요'
				return false;
			}
			if($('input[name=customerEventDate]')[i].value == ''){
				alert(LocaleUtil.getMessage('09222'));//'고객 일정을 입력하세요'
				$('input[name=customerEventDate]')[i].focus();
				return false;
			}
			if(i != 0){
				 var targetDate = new Date($('input[name=customerEventDate]').eq(i-1).val());
				 var sourceDate = new Date($('input[name=customerEventDate]').eq(i).val());
				 if(targetDate.getTime() >= sourceDate.getTime()){
					 alert(LocaleUtil.getMessage('09223'));//'해당 고객 일정이 이전 일정보다 늦을 수 없습니다.'
					 $('input[name=customerEventDate]').eq(i).select();
					 return false;
				 }
			}
			i++;
	    }
		
		return true;
	},
	
	/**
	 * 프로젝트 Link
	 * @param objArr
	 */
	addProjectLink : function(projectArr){
		var G = Grids[1];
		var hasDuplicated = false;
    	//중복체크
    	for (var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
	    	for(var i=0;i<projectArr.length;i++){
	    		if(tRow.projectNo == projectArr[i][1]){
	    			alert(LocaleUtil.getMessage('09224',projectArr[i][2]));//{0}해당 프로젝가 이미 존재합니다.
	    			hasDuplicated = true;
	    			return;
	    		}
	    	}
    	}
    	if(hasDuplicated){
    		return;
    	}
    	for(var i=0;i<projectArr.length;i++){
    		var row = G.AddRow(null, null, 7); //맨하위 추가
        	row.NoColor = 2;
        	G.SetString(row, 'projectOid',projectArr[i][0],1);
        	G.SetString(row, 'projectNo',projectArr[i][1],1);
        	G.SetString(row, 'projectName',projectArr[i][2],1);
        	G.SetString(row, 'planStartDate',projectArr[i][3],1);
        	G.SetString(row, 'planEndDate',projectArr[i][4],1);
        	G.SetString(row, 'pm',projectArr[i][10],1);
        	G.SetString(row, 'displayState',projectArr[i][5],1);
            G.SetString(row, 'checkedEvent',LocaleUtil.getMessage('09215')+"<input type=hidden name=checkedEvent value=0>",1);//미확인
            G.SetString(row, 'sumary','<img src="/plm/portal/icon/projectCard.png">',1);
        	G.RefreshRow(row);
    	}
	},
	
	/**
	 * 수정화면
	 * @param grid
	 * @param row
	 * @param col
	 * @param x
	 * @param y
	 */
	goUpdate : function(grid,row,col,x,y){
		getOpenWindow2('/plm/ext/project/program/updateProgramForm.do',row.oid,800,620);
	},
	
	getProjectParamStr : function(){
		var G = Grids[1];
		if(!G.LoadedCount || G.LoadedCount == 0){
    		return '';
    	}
		var projectArry = '';
		var i = 0;
		for (var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			projectArry += '&projects['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
			projectArry += '&projects['+i+'].projectOid='+ (typeof(tRow.projectOid) != 'undefined'?tRow.projectOid:'');
			projectArry += '&projects['+i+'].checkedEvent='+ ($('[name=checkedEvent]').eq(i).val() != 'undefinded'?$('[name=checkedEvent]').eq(i).val:'');
			projectArry += '&projects['+i+'].baseProgram='+ (typeof(tRow.baseProgram) != 'undefined'?tRow.baseProgram:'');
			i++;
	    }
		return projectArry;
	},
	
	setPrefixName : function(){
		var prefixName = $('[name=carTypeTxt]').val()+' '+$('[name=subContractorTxt]').val();
		$('[name=programNamePrefix]').val(prefixName);
	} 
}