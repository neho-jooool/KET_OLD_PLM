var HistoryProgram = {
	/**
	 * 공지 이력 grid
	 */
	createNotifyHistoryGrid : function(data){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ProgramHistoryGrid',
			Body : data,
			Sort : '-version',
			useToolbar : false,//grid toolbar hide
			Cols : [
				{Name : 'version', Width:50, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'notifyer', Width:100, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'notifyDate', Width:100, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'notifyReason', Width:200, RelWidth : 50, 	Align : 'Left', CanSort : '0', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				version : LocaleUtil.getMessage('01481'),//버전
				notifyer : LocaleUtil.getMessage('09225'), //공지자
				notifyDate : LocaleUtil.getMessage('09208'),//공지일
				notifyReason : LocaleUtil.getMessage('09226')//공지사유
			},
			SelectingSingle : '0',
			Panel : {
				Width : '21',CanHide : '0'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = this.goView;
		
		return grid;
	},
	
	/**
	 * 공지 비교 grid
	 */
	createCompareNotifyHistoryGrid : function(obj){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'CompareNotifyHistoryGrid',
			Body : obj.data,
			useToolbar : false,//grid toolbar hide
			Cols : obj.Cols,
			Header :obj.Header
		}),'listGrid');
		
		//row click event
		Grids.OnClick = this.goView;
		Grids.OnRenderFinish = this.changeColor;
		return grid;
	},

	/**
	 * 차종이벤트 일정과 고객이벤트 일정이 서로 틀리면 blue표시
	 * @param G
	 */
	changeColor : function(G){
		// 그리드에 로드된 전체 행의 수
	    var loadedCount = G.LoadedCount;
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			var firstEventDate = G.GetString(tRow,G.ColNames[1][G.ColNames.length - 1]); //마지막에서 전단계가 0버전의 일정 마지막 column은 '_ConstWidth'
			for(var i in G.ColNames[1]){
				if(i>0 && G.ColNames[1][i] != '_ConstWidth'){
					var customerEventDate = G.GetString(tRow,G.ColNames[1][i]);
					if(firstEventDate != customerEventDate){
						G.SetAttribute(tRow, G.ColNames[1][i],'HtmlPrefix','<font color=blue>',1,0);
						G.SetAttribute(tRow, G.ColNames[1][i],'HtmlPostfix','</font>',1,0);
					}
				}
			}
		}
	},
	
	/**
	 * program event grid
	 */
	createEventGrid : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
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
				{Name : 'customerEventName', Width:200, RelWidth : 50, Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Text'},
				{Name : 'customerEventDate', Width:150, Align : 'Center', CanSort : '0', CanEdit : '0', Type :'Date', Format : 'yyyy-MM-dd'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				carEventName : LocaleUtil.getMessage('09211'),//'차종 이벤트명'
				carEventDate : LocaleUtil.getMessage('02348'),//일정
				customerEventName : LocaleUtil.getMessage('09212'),//'고객 이벤트명'
				customerEventDate : LocaleUtil.getMessage('02348')//일정
			}
		}),'listGrid');
	},
	
	goCompareNotify : function(){
		var G = Grids[0];    //대상 그리드
        var rows = G.GetSelRows(); //선택한 Row
        if(!rows || rows.length <= 1){
        	alert(LocaleUtil.getMessage('09227'));//'비교대상이 없거나 1개 이상이어야 합니다.'
        	return;
        }
        var version = '';
        var versionOids = '';
        for(var i=0; i<rows.length;i++){
        	version += '&versions['+i+']='+rows[i].version;
        	versionOids += '&versionOids['+i+']='+rows[i].oid;
        }
        var url = '/plm/ext/project/program/compareNotifyPopup?oid='+$('[name=programScheduleOid]').val()+versionOids+version;
		getOpenWindow2(url,'NotifyViewPopup',800,600);
	},
	
	goView : function(grid,row,col,x,y){
		if(col == 'notifyDate'){
			var url = '/plm/ext/project/program/notifyDetailPopup.do?oid='+row.oid;
			getOpenWindow2(url,'NotifyViewPopup',800,600);
		}
	}
}