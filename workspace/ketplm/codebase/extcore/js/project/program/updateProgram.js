var UpdateProgram = {
	/**
	 * event grid 조회/수정시에 사용됩니다.
	 */
	createEventGrid : function(isEditable){
		var config = {
			id : 'ProgramEventGrid',
			Data : {
				Url : '/plm/ext/project/program/findEventByProgram.do',
				Param : {
					programOid : $('[name=oid]').val()
				}				
			},
			useToolbar : false,//grid toolbar hide
			Cols : [
				{Name : 'carEventName', Width:200, RelWidth : 50, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'carEventDate', Width:150, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'customerEventName', Width:200, RelWidth : 50, Align : 'Center', CanSort : '0', CanEdit : isEditable?'1':'0', Type : 'Text'},
				{Name : isEditable?'gridCustomerEventDate':'customerEventDate', Width:150, Align : 'Center', CanSort : '0', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				carEventName : LocaleUtil.getMessage('09211'),//'차종 이벤트명'
				carEventDate : LocaleUtil.getMessage('02348'),//일정
				customerEventName : LocaleUtil.getMessage('09212'),//'고객 이벤트명'
				customerEventDate : LocaleUtil.getMessage('02348')//일정
			},
			Panel : isEditable?{
				Width : '21', Visible : '1',CanHide : '0'
			}:null
		};
		if(isEditable){
			delete config.Header.customerEventDate;
			config.Header.gridCustomerEventDate = LocaleUtil.getMessage('02348');//일정
		}
		this.grid = TreeGrid(CommonGrid.getGridConfig(config),'listGrid');
		
		if(isEditable){
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
		}else{
			Grids.OnRenderFinish = UpdateProgram.changeColor;
		}
	
	},
	
	/**
	 * 차종이벤트 일정과 고객이벤트 일정이 서로 틀리면 blue표시
	 * @param G
	 */
	changeColor : function(G){
		var G = Grids[0];
		// 그리드에 로드된 전체 행의 수
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			var carEventDate = new Date(G.GetString(tRow,'carEventDate')).getTime();
			var customerEventDate = new Date(G.GetString(tRow,'customerEventDate')).getTime();
			var today = new Date().getTime();
			if(today > customerEventDate){
				G.SetAttribute(tRow, 'customerEventDate','HtmlPrefix','<font color=#CCCCCC>',1,0);
				G.SetAttribute(tRow, 'customerEventDate','HtmlPostfix','</font>',1,0);
			}else if(carEventDate != customerEventDate){
				G.SetAttribute(tRow, 'customerEventDate','HtmlPrefix','<font color=blue>',1,0);
				G.SetAttribute(tRow, 'customerEventDate','HtmlPostfix','</font>',1,0);				
			}
			//SOP 일정이 오늘 일정 보다 지나면 붉은색
			if(G.GetString(tRow,'carEventName') == 'SOP' && carEventDate < customerEventDate){
				G.SetAttribute(tRow, 'customerEventDate','HtmlPrefix','<font color=red>',1,0);
				G.SetAttribute(tRow, 'customerEventDate','HtmlPostfix','</font>',1,0);
			}
		}
		CalendarUtil.dateInputFormat('customerEventDate');
	},

	/**
	 * 프로그램 저장
	 */
	update : function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		//등록 정보 검증
		if(!CreateProgram.validation()){
			return;
		}
		var formData = $('[name=ProgramUpdateForm]').serialize()+'&'+this.getEventParamStr()+'&'+CreateProgram.getProjectParamStr();
		//프로그래스바 보이기
		showProcessing();
		
		if(CreateProgram.getProjectParamStr()){
			//form data 전송
			$.ajax({
				type: 'post',
				url: '/plm/ext/project/program/hasChangedEvent.do',
				data: formData,
				success: function (data) {
					if(data){
						if(confirm(LocaleUtil.getMessage('09228'))){//'일정 변경사항이 있습니다. 공지하시겠습니까?'
							UpdateProgram.goNotify();
						}else{
							UpdateProgram.updateData();
						}
					}else{
						UpdateProgram.updateData();
					}
				},
				error : function(){
					alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
					//프로그레스바 숨기기
					hideProcessing();
				}
			});			
		}else{
			UpdateProgram.updateData();
		}
		
	},
	
	updateData : function(){
		var formData = $('[name=ProgramUpdateForm]').serialize()+'&'+this.getEventParamStr()+'&'+CreateProgram.getProjectParamStr();
		//form data 전송
		$.ajax({
			type: 'post',
			url: '/plm/ext/project/program/updateProgram.do',
			data: formData,
			success: function (data) {
				if(data.success == 'OK'){
					alert(LocaleUtil.getMessage('02460'));//'저장되었습니다.' 
//					UpdateProgram.goView(data.value.oid);
					var url = '/plm/ext/project/program/viewProgramForm.do?oid='+data.value.oid;
					location.href = url;
				}else{
					alert(data.value);
				}
				//프로그레스바 숨기기
				hideProcessing();
			},
			error : function(){
				alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
				//프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	/**
	 * 프로그램 삭제
	 */
	deleteProgram : function(){
		if(!confirm(LocaleUtil.getMessage('09229'))){//'프로그램을 삭제 하시겠습니까?'
			return;
		}
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/project/program/deleteProgram.do',
            data: $('[name=ProgramViewForm]').serialize(),
            success: function (data) {
                alert(LocaleUtil.getMessage('01692'));//'삭제 되었습니다.'
                opener.Program.search();
                window.close();
            },
            error : function(){
            	alert(LocaleUtil.getMessage('09230'));//'삭제 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	goUpdate : function(selectTab){
		var url = '/plm/ext/project/program/updateProgramForm.do?oid='+$('[name=oid]').val()+'&selectTab='+selectTab;
		location.href = url;
//		getOpenWindow2(url,'ProgramPopup',800,620);
	},
	
	goNotify : function(){
		getOpenWindow2('/plm/ext/project/program/notifyProgramForm.do?oid='+$('[name=oid]').val(),'ProgramNotifyPopup',450,160);
	},
	
	goHistory : function(){
		var url = '/plm/ext/project/program/getNotifyHistoryPopup.do?oid='+$('[name=oid]').val();
		getOpenWindow2(url,'NotifyHistoryPopup',500,300);
//		window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:300px; center:yes");
	},
	
	notify : function(){
		if($('[name=notifyReason]').val() == ''){
			alert(LocaleUtil.getMessage('09231'));//'공지사유를 입력하여야 합니다.'
			$('[name=notifyReason]').focus();
			return;
		}
			
		if(confirm(LocaleUtil.getMessage('09232'))){//'PM에게 공지하시겠습니까?'
			//프로그래스바 보이기
			showProcessing();
			//form data 전송
			$.ajax({
				type: 'post',
				url: '/plm/ext/project/program/notifyProgram.do',
				data: $('[name=ProgramNotifyForm]').serialize()+'&oid='+opener.$('[name=oid]').val()+'&'+opener.UpdateProgram.getEventParamStr()+'&'+opener.CreateProgram.getProjectParamStr(),
				success: function (data) {
					alert(LocaleUtil.getMessage('09233'));//'성공적으로 공지 되었습니다.'
					var url = '/plm/ext/project/program/viewProgramForm.do?oid='+data.oid;
					opener.location.href = url;
//					parent.UpdateProgram.goView(data.oid,null);
					window.close();
				},
				error : function(){
					alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
					//프로그레스바 숨기기
	            	hideProcessing();
				}
			});
		}
		return;
	},
	
	getEventParamStr : function(){
		//grid data 조합
		var G = Grids[0];
		var eventArry = '';
		var i = 0;
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			eventArry += '&events['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
			eventArry += '&events['+i+'].carEventOid='+ (typeof(tRow.carEventOid) != 'undefined'?tRow.carEventOid:'');
			eventArry += '&events['+i+'].carEventName='+ (G.GetString( tRow, 'carEventName') == '-'?'':G.GetString( tRow, 'carEventName'));
			eventArry += '&events['+i+'].carEventDate='+ (G.GetString( tRow, 'carEventDate') == '-'?'':G.GetString( tRow, 'carEventDate'));
			eventArry += '&events['+i+'].customerEventName='+ G.GetString( tRow, 'customerEventName');
			if($('input[name=customerEventDate]').length > 0){
				eventArry += '&events['+i+'].customerEventDate='+ $('input[name=customerEventDate]')[i].value;
			}else{
				eventArry += '&events['+i+'].customerEventDate='+  G.GetString( tRow, 'customerEventDate');
			}
			i++;
	    }
		return eventArry;
	},
	
	goView : function(oid, selectTab){
		var url = '/plm/ext/project/program/viewProgramForm.do?oid='+(oid?oid:$('[name=oid]').val())+'&selectTab='+selectTab;
		getOpenWindow2(url,'ProgramPopup',800,620);
	}
}