var mass = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'massPartSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['massPartSearchGrid'])?Grids['massPartSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:mass.search(Value);',
			Cols : [
					//{Name : 'devTeamName', Width:120, Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'oid', Width:0, Visible : '0'},
					{Name : 'newPart', Width:60, Align : 'center',CanSort : '0', CanEdit : '0'},
					{Name : 'partNo', Width:85, Align : 'left', CanSort : '0', CanEdit : '0', OnClick :'javascript:openViewPart(Row.partNo);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'partName', Width:100, Align : 'left', CanSort : '0', CanEdit : '0'},					
			        {Name : 'pjtNo', Width:70, Align : 'left', CanSort : '0', CanEdit : '0', Type : "Text", OnClick : 'javascript:openProject(Row.pjtNo);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
			        {Name : 'pjtName', Width:90, Align : 'left', CanSort : '0', CanEdit : '0'},
			        {Name : 'processGb', Width:60, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'masStartDate', Width:100, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Date", Format : "yyyy-MM-dd", EditFormat : "yyyy-MM-dd", DefaultDate : ""},
			        {Name : 'dr6EndDate', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'pjtEndDate', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'pjtNos', Width:85, Align : 'left', CanSort : '0', CanEdit : '0'},
			        {Name : 'ecoNo', Width:90, Align : 'left', CanSort : '0', CanEdit : '0'},
			        {Name : 'ecoApproveDate', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'createName', Width:65, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'createDate', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'modifyName', Width:65, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'modifyDate', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'bigo', Width:150, Align : 'center', CanSort : '0', CanEdit : '1', Type : "Text"}
			],Header :{
				CanDelete : '0', Align : 'Center',
				CanSelect : 1,
				//devTeamName	: "제품개발팀",
				newPart : "신제품\n구분",
				partNo		: "품번",
				partName		: "자재명",
				pjtNo		: "대표\n프로젝트",
				pjtName : "프로젝트명",
				processGb : "프로젝트\n단계",
				masStartDate		: "양산시작일",
				dr6EndDate : "DR6종료일",
				pjtEndDate : "프로젝트\n종료일",
				pjtNos		: "관련 프로젝트",
				ecoNo : "양산ECO",
				ecoApproveDate : "ECO승인일\n(양산이관일)",
				createName		: "양산정보\n등록자",
				createDate		: "양산정보\n등록일",
				modifyName		: "양산정보\n수정자",
				modifyDate		: "양산정보\n수정일",
				bigo : "양산정보\n비고"
			},
			SelectingSingle : '0',
			Panel : {
				Visible : '0'
			}
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/part/base/findPartMassPagingList.do?sortName=*Sort0';
			
			var param = {
		       		page : grid.GetPageNum(row),
		            formPage : grid.Source.Layout.Data.Cfg.PageLength
		        }

	        $('input,select').each(function(){
	        	var name = $(this).attr('name');
	            var value = $(this).val();
	            param[name] = value;
	        });
	        grid.Data.Page.Param = param;
		},
		
		Grids.OnRenderPageFinish = function(){
		},
		
		Grids.OnRenderFinish = function(){
        }
		//row click event
		//Grids.OnClick = cost.goView;
	},
	
	partPopup : function (taskOid,partOid){
		
		getOpenWindow2("/plm/ext/cost/costPartCalculatePopup.do?taskOid="+taskOid+"&oid=" + partOid, taskOid+partOid, 1280, 720);
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['massPartSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['massPartSearchGrid'].Source.Data.Url = '/plm/ext/part/base/findPartMassPagingList.do?sortName=*Sort0';
		Grids['massPartSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['massPartSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['massPartSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['massPartSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	goSave : function(){
		
        	
	        var grid = Grids['massPartSearchGrid'];

	        var colNames = grid.ColNames[1];
	        
	        var paramArr = new Array();
	        
	        for (var tRow = grid.GetFirstVisible(); tRow; tRow = grid.GetNextVisible(tRow) ) {
	        	var paramObj = {};
	        	for(var j = 0; j < colNames.length; j++){
	        		var value = grid.GetString(tRow, colNames[j]);
            		paramObj[colNames[j]] = value;
	        	}
	        	paramArr.push(paramObj);
	        }
	        
	        if(paramArr.length < 1){
	        	alert("수정대상 데이터가 조회되지 않았습니다.");
	        	return;
	        }
	        
	        var gridParam = {};
	        gridParam.treeData = paramArr;
	        
	        var gridParam = new Object();
	        gridParam.jsonData = JSON.stringify(paramArr);
	        
	        ajaxCallServer("/plm/ext/part/base/saveMassPart", gridParam, function(data){
				
				alert("저장되었습니다.");
				grid.Reload();
				
		    });
        
	},
	
	gridSave : function(){
		
		$('#pjtNo').focus();
		//$('#pjtNo').blur();
		if(confirm("저장하시겠습니까?")){
			setTimeout( function() { mass.goSave(); }, 10);
		}
	}
}