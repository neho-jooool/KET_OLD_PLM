var updateStandard = {
	
createStandardGrid : function(){
		
		var config = {
			id : 'DashBoradStandardGrid',
			Data : {
				Url : '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=0',
//				Param : {
//					programOid : $('[name=oid]').val()
//				}				
			},
			useToolbar : false,//grid toolbar hide
			
			LeftCols : [
			    {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			    {Name : 'rev', Width:50, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},        
			    {Name : 'year', Width:50, Align : 'Center', CanSort : '0', CanEdit : '1',Spanned:'1',Type:'Enum', EnumKeys:$('[name=yearEnumKey]').val(),  Enum:$('[name=yearTypeEnum]').val()},
			],
			
			Cols : [				
				{Name : 'dayHour', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1', Spanned:'1'},
				{Name : 'dayMaximum', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1', Spanned:'1'},
				{Name : 'workday', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1',Spanned:'1'},
				{Name : 'ecrhour', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1',Spanned:'1'},
				{Name : 'ecohour', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1',Spanned:'1'}
			],

			Head :[
					{
						CanDelete : '0', 
						Align : 'Center',
						id : "Header",
						Spanned : '1',
						CanEdit:'0',
						rev : "Rev",revRowSpan:'2',
						year : "년도",yearRowSpan:'2',
						dayHour : "기준근무시간",dayHourSpan:'2',
						workday : "연간근무일수(D)",workdayRowSpan:'2',
						ecrhour : "ECR(건당)(H)",ecrhourRowSpan:'2',
						ecohour : "ECO(건당)(H)",ecohourRowSpan:'2'
					},
					{
						CanDelete : '0', 
						Align : 'Center',
						id:"HeaderTop",
						CanEdit:'0',
						Spanned : '1',
						rev : "Rev",revRowSpan:'2',
						year : "년도",yearRowSpan:'2',
						dayHour : "하루", dayHourSpan:'1',
						dayMaximum : "하루최대", dayMaximumSpan:'1',
						workday : "연간근무일수(D)",workdayRowSpan:'2',
						ecrhour : "ECR(건당)(H)",ecrhourRowSpan:'2',
						ecohour : "ECO(건당)(H)",ecohourRowSpan:'2'
					}
			       ],
			       
			SelectingSingle : '0',
			
			Panel : {
				Width : '18', Visible : '1',CanHide : '1', Spanned : '1'
			}
			
		};
		this.grid = TreeGrid(CommonGrid.getGridConfig(config),'listGrid');
	
	},
	
	createStandardGrid2 : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'DashBoradStandardGrid2',
			Data : {
				Url : '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=1',
			},
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Cols : [
		        {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			        {Name : 'rev', Width:30, Align : 'Center', CanSort : '0', CanEdit : '0',},        
			        {Name : 'year', Width:50, Align : 'Center', CanSort : '0', CanEdit : '1',Type:'Enum', EnumKeys:$('[name=yearEnumKey]').val(),  Enum:$('[name=yearTypeEnum]').val()},
			        {Name : 'mon_1', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_2', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_3', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_4', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_5', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_6', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_7', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_8', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_9', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_10', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_11', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'mon_12', Width:40, Align : 'Center', CanSort : '0', CanEdit : '1'}
		    ],Header :{
		    	CanDelete : '0', Align : 'Center',
	        	rev : "Rev",
	        	year : "년도",
	        	mon_1 : "1월",
	        	mon_2 : "2월",
	        	mon_3 : "3월",
	        	mon_4 : "4월",
	        	mon_5 : "5월",
	        	mon_6 : "6월",
	        	mon_7 : "7월",
	        	mon_8 : "8월",
	        	mon_9 : "9월",
	        	mon_10 : "10월",
	        	mon_11 : "11월",
	        	mon_12 : "12월"
	        },
	        Panel : {
	        	Width : '18', Visible : '1',CanHide : '1', Spanned : '1'
			}
		}),'listGrid2');
	},
	
	createStandardGrid3 : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'DashBoradStandardGrid3',
			Data : {
				Url : '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=2',
			},
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Cols : [
		        {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			        {Name : 'rev', Width:30, Align : 'Center', CanSort : '0', CanEdit : '0',},        
			        {Name : 'pm_role', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'pmcft_role', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'participant_role', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
		    ],Header :{
		    	CanDelete : '0', Align : 'Center',
	        	rev : "Rev",
	        	pm_role : "PM(겸임)",
	        	pmcft_role : "PM(전담)&CFT",
	        	participant_role : "참가자"
	        },
	        Panel : {
	        	Width : '18', Visible : '1',CanHide : '1', Spanned : '1'
			}
		}),'listGrid3');
	},
	
	createStandardGrid4 : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'DashBoradStandardGrid4',
			Data : {
				Url : '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=3',
			},
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Cols : [
		        {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			        {Name : 'rev', Width:30, Align : 'Center', CanSort : '0', CanEdit : '0',},        
			        {Name : 's_rank', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'a_rank', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'b_rank', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'c_rank', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'r_rank', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'}
		    ],Header :{
		    	CanDelete : '0', Align : 'Center',
	        	rev : "Rev",
	        	s_rank : "S",
	        	a_rank : "A",
	        	b_rank : "B",
	        	c_rank : "C",
	        	r_rank : "R"
	        },
	        Panel : {
	        	Width : '18', Visible : '1',CanHide : '1', Spanned : '1'
			}
		}),'listGrid4');
	},
	
	createStandardGrid5 : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'DashBoradStandardGrid5',
			Data : {
				Url : '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=4',
			},
			useToolbar : false,//grid toolbar hide
			SelectingSingle : '0',
			Cols : [
		        {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
			        {Name : 'rev', Width:30, Align : 'Center', CanSort : '0', CanEdit : '0',},        
			        {Name : 'm_f1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'p_h1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 's_j1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'y_l1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'e_p1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'},
			        {Name : 'k_r1020', Width:100, Align : 'Center', CanSort : '0', CanEdit : '1'}
		    ],Header :{
		    	CanDelete : '0', Align : 'Center',
	        	rev : "Rev",
	        	m_f1020 : "부장\n(총괄)",
	        	p_h1020 : "차장\n(수석)",
	        	s_j1020 : "과장\n(책임)",
	        	y_l1020 : "대리\n(선임)",
	        	e_p1020 : "주임\n(주임)",
	        	k_r1020 : "사원\n(연구)"
	        },
	        Panel : {
	        	Width : '18', Visible : '1',CanHide : '1', Spanned : '1'
			}
		}),'listGrid5');
	},


	
	updateData : function(index){
		
		if(index == "1"){
			
			if($('[name=departmentOid]').val() == '' || $('[name=departmentOid]').val() == 'root' ){
				alert("부서를 선택하세요.");
				return;
			}
			if($('[name=leaf]').val() == '0'){
				alert("최하위 레벨의 부서에만 입력 가능합니다.");
				return;
			}
		}
		if(!updateStandard.dataCheck(index)){
			alert("빈 항목이 있습니다. 값을 입력하시기 바랍니다.");
			return;
		}
		
		var formData = decodeURIComponent($('[name=StandardViewForm]').serialize())+'&'+this.getStandardParamList(index);
		
		//form data 전송
		$.ajax({
			type: 'post',
			url: '/plm/ext/dashboard/standard/updateStandard.do?selectTab='+index,
			data: formData,
			success: function (data) {
				if(data.success == 'OK'){
					alert(LocaleUtil.getMessage('02460'));//'저장되었습니다.' 
					if(index == "1"){
						updateStandard.gridReload(data.value);
					}else{
						var url = '/plm/ext/dashboard/standard/viewStandardForm.do?selectTab='+index;
						location.href = url;
					}
					
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
	
	getStandardParamList : function(index){
		//grid data 조합
		var G = Grids[index];
		var standardArry = '';
		var i = 0;
		
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			standardArry += '&standard['+i+'].selectTab='+index;
			if(index == 0){
				standardArry += '&standard['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
				standardArry += '&standard['+i+'].year='+ G.GetString( tRow, 'year');
				standardArry += '&standard['+i+'].dayHour='+ G.GetString( tRow, 'dayHour');
				standardArry += '&standard['+i+'].dayMaximum='+ G.GetString( tRow, 'dayMaximum');
				standardArry += '&standard['+i+'].workday='+ G.GetString( tRow, 'workday');
				standardArry += '&standard['+i+'].ecrhour='+ G.GetString( tRow, 'ecrhour');
				standardArry += '&standard['+i+'].ecohour='+ G.GetString( tRow, 'ecohour');
			}else if(index == 1){
				standardArry += '&standard['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
				standardArry += '&standard['+i+'].year='+ G.GetString( tRow, 'year');
				standardArry += '&standard['+i+'].mon_1='+ G.GetString( tRow, 'mon_1');
				standardArry += '&standard['+i+'].mon_2='+ G.GetString( tRow, 'mon_2');
				standardArry += '&standard['+i+'].mon_3='+ G.GetString( tRow, 'mon_3');
				standardArry += '&standard['+i+'].mon_4='+ G.GetString( tRow, 'mon_4');
				standardArry += '&standard['+i+'].mon_5='+ G.GetString( tRow, 'mon_5');
				standardArry += '&standard['+i+'].mon_6='+ G.GetString( tRow, 'mon_6');
				standardArry += '&standard['+i+'].mon_7='+ G.GetString( tRow, 'mon_7');
				standardArry += '&standard['+i+'].mon_8='+ G.GetString( tRow, 'mon_8');
				standardArry += '&standard['+i+'].mon_9='+ G.GetString( tRow, 'mon_9');
				standardArry += '&standard['+i+'].mon_10='+ G.GetString( tRow, 'mon_10');
				standardArry += '&standard['+i+'].mon_11='+ G.GetString( tRow, 'mon_11');
				standardArry += '&standard['+i+'].mon_12='+ G.GetString( tRow, 'mon_12');
				standardArry += '&standard['+i+'].departmentOid='+$('[name=departmentOid]').val();
			}else if(index == 2){
				standardArry += '&standard['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
				standardArry += '&standard['+i+'].pm_role='+ G.GetString( tRow, 'pm_role');
				standardArry += '&standard['+i+'].pmcft_role='+ G.GetString( tRow, 'pmcft_role');
				standardArry += '&standard['+i+'].participant_role='+ G.GetString( tRow, 'participant_role');
			}else if(index == 3){
				standardArry += '&standard['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
				standardArry += '&standard['+i+'].s_rank='+ G.GetString( tRow, 's_rank');
				standardArry += '&standard['+i+'].a_rank='+ G.GetString( tRow, 'a_rank');
				standardArry += '&standard['+i+'].b_rank='+ G.GetString( tRow, 'b_rank');
				standardArry += '&standard['+i+'].c_rank='+ G.GetString( tRow, 'c_rank');
				standardArry += '&standard['+i+'].r_rank='+ G.GetString( tRow, 'r_rank');
			}else if(index == 4){
				standardArry += '&standard['+i+'].oid='+ (typeof(tRow.oid) != 'undefined'?tRow.oid:'');
				standardArry += '&standard['+i+'].m_f1020='+ G.GetString( tRow, 'm_f1020');
				standardArry += '&standard['+i+'].p_h1020='+ G.GetString( tRow, 'p_h1020');
				standardArry += '&standard['+i+'].s_j1020='+ G.GetString( tRow, 's_j1020');
				standardArry += '&standard['+i+'].y_l1020='+ G.GetString( tRow, 'y_l1020');
				standardArry += '&standard['+i+'].e_p1020='+ G.GetString( tRow, 'e_p1020');
				standardArry += '&standard['+i+'].k_r1020='+ G.GetString( tRow, 'k_r1020');
			}
			i++;
	    }

		return standardArry;
	},
	
	dataCheck : function(index) {
		var G = Grids[index];
		var flag = "";
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			
			if(index == 0){
				if(G.GetString( tRow, 'year') == '' || G.GetString( tRow, 'dayHour') == '' || G.GetString( tRow, 'dayMaximum') == ''
					|| G.GetString( tRow, 'workday') == '' || G.GetString( tRow, 'ecrhour') == '' || G.GetString( tRow, 'ecohour') == ''){
					return false;
				}
			}else if(index == 1){
				if(G.GetString( tRow, 'year') == '' || G.GetString( tRow, 'mon_1') == '' || G.GetString( tRow, 'mon_2') == ''
					|| G.GetString( tRow, 'mon_3') == '' || G.GetString( tRow, 'mon_4') == '' || G.GetString( tRow, 'mon_5') == '' 
						||G.GetString( tRow, 'mon_6') == '' || G.GetString( tRow, 'mon_7') == '' || G.GetString( tRow, 'mon_8') == ''
							||G.GetString( tRow, 'mon_9') == '' || G.GetString( tRow, 'mon_10') == '' || G.GetString( tRow, 'mon_11') == '' || G.GetString( tRow, 'mon_12') == ''){
					return false;
				}
			}else if(index == 2){
				if(G.GetString( tRow, 'pm_role') == '' || G.GetString( tRow, 'pmcft_role') == '' || G.GetString( tRow, 'participant_role') == ''){
					return false;
				}
			}else if(index == 3){
				if(G.GetString( tRow, 's_rank') == '' || G.GetString( tRow, 'a_rank') == '' || G.GetString( tRow, 'b_rank') == ''
					|| G.GetString( tRow, 'c_rank') == '' || G.GetString( tRow, 'r_rank') == ''){
					return false;
				}
			}else if(index == 4){
				if(G.GetString( tRow, 'm_f1020') == '' || G.GetString( tRow, 'p_h1020') == '' || G.GetString( tRow, 's_j1020') == ''
					|| G.GetString( tRow, 'y_l1020') == '' || G.GetString( tRow, 'e_p1020') == '' || G.GetString( tRow, 'k_r1020') == ''){
					return false;
				}
			}
			
	    }
		
		return true;
	},
	
	addRow : function(index) {
		if(index == "1" && $('[name=leaf]').val() == '0'){
			alert("최하위 레벨의 부서에만 추가가 가능합니다.");
			return;
		}
		/*Tab이 여러개일때 여러개의 그리드에 Panel 옵션이 존재시 두번째 탭의 그리드부터는 addRow가 굉장히 느린현상때문에 
		  차선책으로 tempColumn에 의미없는 값을 세차례 세팅함.. 원인불명..*/
		$('[name=tempColumn]').attr('value', "temp");
		var G = Grids[index];
		$('[name=tempColumn]').attr('value', "temp1");
		addedRow = G.AddRow(null, null, 7);
		$('[name=tempColumn]').attr('value', "temp2");
    	return;
	},
	
	removeRowOnly : function(index) {
		var G = Grids[index];
		var rows = G.GetSelRows(); //선택한 Row

		G.RemoveRow(rows[0]);
		
		var delOids = $('[name=delOids]').val();
		for(var i=0;i<rows.length;i++) {
			G.RemoveRow(rows[i]);
			
			if(delOids=='') delOids = rows[i].oid;
			else delOids = delOids + ',' + rows[i].oid;
		}
		
		$('[name=delOids]').attr('value', delOids);
		//alert($('[name=delOids]').val());
	},
	
	gridReload : function(oid) { 
		$('[name=delOids]').attr('value', '');
		$('[name=departmentOid]').attr('value', oid);
		Grids[1].Source.Data.Url = '/plm/ext/dashboard/standard/findStandardByDashboard.do?selectTab=1&departmentOid='+ oid;
        //Grids[1].Source.Data.Params = "programOid=ext.ket.project.program.entity.KETProgramSchedule:99209110";
		Grids[1].ReloadBody();
	}
}