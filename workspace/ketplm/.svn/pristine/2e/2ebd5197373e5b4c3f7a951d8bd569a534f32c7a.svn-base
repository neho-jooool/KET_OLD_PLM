var Cost = {
    /**
     * server paging grid  
     */
	createDevCostGrid : function  (){
        this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'DevCostGrid',
            Sort : '-revision',
            useToolbar : false,//grid toolbar hide
            Data : {
                Url : '/plm/ext/project/cost/findCostByProject.do?sortName=*Sort0',
                Method : 'POST',
                Format : 'Form',
                Param : {
                	projectNo : $('[name=projectNo]').val(),
                	division : $('[name=division]').val(),
                    formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
                }
            },
            Cols : [
                {Name : 'revision',             Width:50, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'reviewCompleteDate',   Width:80, Align : 'Left', CanSort : '1', CanEdit : '0'},
                {Name : 'reviewStep',           Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'partName',             Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'production',           Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
                {Name : 'salesTargetCost',      Width:100, Align : 'Right', CanSort : '1', CanEdit : '0'},
                {Name : 'suyearSum',         Align : 'Right', CanSort : '1', CanEdit : '0'},
		        {Name : 'totalsalesSum',         Align : 'Right', CanSort : '1', CanEdit : '0'},
                {Name : 'classification',       Align : 'Right', CanSort : '1', CanEdit : '0'},
                {Name : 'totalCost',            Align : 'Right', CanSort : '1', CanEdit : '0'},
                {Name : 'profitRate',           Align : 'Right', CanSort : '0', CanEdit : '0', Type : 'Html'},
                {Name : 'mainIssue',            Width:120, Align : 'Center', CanSort : '0', CanEdit : '0'},
                {Name : 'creator',              Width:80, Align : 'Center', CanSort : '0', CanEdit : '0'},
                {Name : 'state',                Width:80, Align : 'Center', CanSort : '0', CanEdit : '0'},
                {Name : 'url',           		Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'}
            ],Header :{
                CanDelete : '0', CanEdit : '0', Align : 'Center',
                revision : 'Rev',
                reviewCompleteDate : '검토완료일',
                reviewStep : '검토단계',
                partName : 'PartName',
                production : '생산처',
                salesTargetCost : '판매목표가(원)',
                suyearSum : '예상판매수량(천개)',
	        	totalsalesSum : '예상매출액(백만원)',
                classification : '구분',
                totalCost : '총원가(원)',
                profitRate : '수익률(%)',
                mainIssue : '주요Issue사항',
                creator : '작성자',
                state : '상태',
                url : '링크'
            }
        }),'listGrid');
        
        //row click event
        Grids.OnClick = function (grid,row,col,x,y){
	        if(col == 'url'){
    			var screenWidth = screen.availWidth;
                var screenHeight = screen.availHeight;
                openOtherName(row.url, "COST_INFO_POPOUP", screenWidth, screenHeight, "status=no,scrollbars=yes,resizable=yes");
	        }
	    };
        
        Grids.OnRenderFinish = function(){
        	var G = Grids[0];
        	var hasInwork = false;
        	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		if(tRow.profitRate && tRow.profitRate < 0){
        			G.SetString(tRow,'profitRate',"<font color=red>"+tRow.profitRate+"</font>",1);        			
        		}else{
        			G.SetString(tRow,'profitRate',"<font color=blue>"+tRow.profitRate+"</font>",1);
        		}
        		if(tRow.state == '작업중'){
        			hasInwork = true;
        		}
        		//링크
        		if(tRow.url){
        			G.SetAttribute(tRow, 'url','HtmlPrefix', '<img src="/plm/portal/images/calendar.gif"><span style=display:none>',1,0);        			
        			G.SetAttribute(tRow, 'url','HtmlPostfix', '</span>',1,0);
        		}
        	}
        	if(!hasInwork){
        		$('#buttonTable').show();
        	}else{
        		$('#buttonTable').hide();        		
        	}
        }
    },
    
    /**
     * server paging grid  
     */
    createPaingGrid : function  (){
    	this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
    		id : 'DevCostGrid',
    		Sort : '-projectNo',
    		perPageOnChange : 'javascript:Cost.search(Value);',
    		Data : {
    			Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
    			Method : 'POST',
    			Format : 'Form',
    			Data : 'TGData',
    			Param : {
    				projectOid : $('[name=projectOid]').val(),
    				formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
    			}    	
    		},
    		Cols : [
    		        {Name : 'projectNo',         Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'projectName',       Width:1250, Align : 'Left', CanSort : '1', CanEdit : '0', RelWidth : 50},
    		        {Name : 'subContractor',     Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'carType',           Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'developedType',     Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'state',      	     Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'revision',          Width:50, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'drStep',            Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'production',        Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
    		        {Name : 'salesTargetCost',   Width:100, Align : 'Right', CanSort : '1', CanEdit : '0'},
    		        {Name : 'suyearSum',         Align : 'Right', CanSort : '1', CanEdit : '0'},
    		        {Name : 'totalsalesSum',         Align : 'Right', CanSort : '1', CanEdit : '0'},
    		        {Name : 'classification',       Align : 'Right', CanSort : '1', CanEdit : '0'},
    		        {Name : 'totalCost',         Align : 'Right', CanSort : '1', CanEdit : '0'},
    		        {Name : 'profitRate',        Align : 'Right', CanSort : '1', CanEdit : '0', Type : 'Html'},
    		        {Name : 'url',           		Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'}
    		        ],Header :{
    		        	CanDelete : '0', CanEdit : '0', Align : 'Center',
    		        	projectNo : '프로젝트No',
    		        	projectName : '프로젝트명',
    		        	subContractor : '고객',
    		        	carType : '차종',
    		        	developedType : '개발유형',
    		        	state : '상태',
    		        	revision : 'Rev',
                        drStep   : '검토단계',
    		        	production : '생산처',
    		        	salesTargetCost : '판매목표가(원)',
    		        	suyearSum : '예상판매수량(천개)',
    		        	totalsalesSum : '예상매출액(백만원)',
    		        	classification : '구분',
    		        	totalCost : '총원가(원)',
    		        	profitRate : '수익률(%)',
    		        	url : '링크'
    		        }
    	}),'listGrid');
    	
    	//row click event
    	Grids.OnClick = function (grid,row,col,x,y){
    		if(row.projectOid && col == 'projectNo'){
    			openView(row.projectOid);
    		}
    		if(row.projectOid && (col == 'url' || col == 'revision')){
    			var screenWidth = screen.availWidth;
                var screenHeight = screen.availHeight;
                openOtherName(row.url, "COST_INFO_POPOUP", screenWidth, screenHeight, "status=no,scrollbars=yes,resizable=yes");
    		}
    	};
    	
    	Grids.OnRenderPageFinish = function(){
    		alert();
    		var G = Grids[0];
    		var hasInwork = false;
    		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
    			if(tRow.profitRate && tRow.profitRate < 0){
    				G.SetString(tRow,'profitRate',"<font color=red>"+tRow.profitRate+"</font>",1);        			
    			}else{
    				G.SetString(tRow,'profitRate',"<font color=blue>"+tRow.profitRate+"</font>",1);
    			}
    			//링크
        		if(tRow.url){
        			G.SetAttribute(tRow, 'url','HtmlPrefix', '<img src="/plm/portal/images/calendar.gif"><span style=display:none>',1,0);        			
        			G.SetAttribute(tRow, 'url','HtmlPostfix', '</span>',1,0);
        		}
    		}
    	}
    	
    	//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/cost/findPagingListForReport.do?sortName=*Sort0&pageTotalSize='+ grid.RootCount;
			grid.Data.Page.Data = 'TGData';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				perPage : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
    },
    
    search : function(perPage){
    	
		if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
		else{
			$('[name=perPage]').val(perPage);
		}
		Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids[0].Source.Data.Url = '/plm/ext/project/cost/findPagingListForReport.do?sortName=*Sort0&pageTotalSize=-1';
		Grids[0].Source.Data.Params = decodeURIComponent($('[name=ProjectSearch]').serialize());
		Grids[0].Source.Data.Param.formPage=perPage
		Grids[0].Source.Data.Params.perPage=perPage
		
		Grids[0].Reload(Grids[0].Data);
	},
	
	clear : function(){
		$('[name=ProjectSearch]')[0].reset();
	},
	    
	goCreate : function(projectOid){
		getOpenWindow2('/plm/ext/project/cost/createCostForm.do?projectOid='+projectOid,'CostCreatePopup',800,400);
	},
	
	goUpdate : function(oid){
		location.href='/plm/ext/project/cost/updateCostForm.do?oid='+oid;
	},
	
	goView : function(oid){
		getOpenWindow2('/plm/ext/project/cost/viewCostPopup.do?oid='+oid,oid,800,400);
	},
	
	goRevise : function(){
		var G = Grids[0];
		var latestRevision = 0;
		var latestRevisionOid = 0;
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			if(latestRevision <= tRow.revision){
				latestRevision = tRow.revision;
				latestRevisionOid = tRow.oid;
			}
		}
		getOpenWindow2('/plm/ext/project/cost/reviseCostForm.do?oid='+latestRevisionOid+'&projectOid='+$('[name=projectOid').val(),'ReviseForm',800,400);
	},
	
	revise : function(oid){
		//등록 정보 검증
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		//프로그래스바 보이기
		showProcessing();
		$.ajax({
			type : 'post',
			url : '/plm/ext/project/cost/revise.do',
			data : $("[name=CostCreateForm]").serializefiles(),
			processData : false,
			contentType : false,
			success : function(data) {
				alert('성공적으로 저장되었습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
				opener.location.reload();
				self.close();
			},
			error : function() {
				alert('등록에 실패하였습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	/**
	 * 프로그램 저장
	 */
	create : function(){
		//등록 정보 검증
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		//프로그래스바 보이기
		showProcessing();
		$.ajax({
			type : 'post',
			url : '/plm/ext/project/cost/create.do',
			data : $("[name=CostCreateForm]").serializefiles(),
			processData : false,
			contentType : false,
			success : function(data) {
				alert('성공적으로 저장되었습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
				opener.location.reload();
				self.close();
			},
			error : function() {
				alert('등록에 실패하였습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	modify : function(){
		// form data 전송
		$.ajax({
			type : 'post',
			url : '/plm/ext/project/cost/modify.do',
			data : $("[name=CostUpdateForm]").serializefiles(),
			processData : false,
			contentType : false,
			success : function(data) {
				alert('성공적으로 수정되었습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
				location.href='/plm/ext/project/cost/viewCostPopup.do?oid='+$('[name=oid]').val();
			},
			error : function() {
				alert('등록에 실패하였습니다.');
				// 프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	deleteCost : function(){
		if(confirm('삭제 하시겠습니까?')){
			// form data 전송
			$.ajax({
				type : 'post',
				url : '/plm/ext/project/cost/delete.do',
				data : $("[name=CostViewForm]").serializefiles(),
				processData : false,
				contentType : false,
				success : function(data) {
					alert('성공적으로 삭제되었습니다.');
					// 프로그레스바 숨기기
					hideProcessing();
					opener.location.reload();
					self.close();
				},
				error : function() {
					alert('등록에 실패하였습니다.');
					// 프로그레스바 숨기기
					hideProcessing();
				}
			});
		}
	},
	
	/**
	 * 파일 추가
	 */
	insertFileLine : function() {
	    // 첨부파일 라인을 추가한다.
	    var innerRow = fileTable.insertRow();
	    innerRow.height = "27";
	    var innerCell;

	    var filePath = "filePath";
	    var filehtml = "";

	    for ( var k = 0; k < 2; k++) {
	        innerCell = innerRow.insertCell();
	        innerCell.height = "23";

	        if (k == 0) {
	            innerCell.className = "tdwhiteM";
	            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
	        } else if (k == 1) {
	            innerCell.className = "tdwhiteL0";
	            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
	        }
	    }

	},
	
	/**
	 * 파일 삭제
	 */
	deleteFileLine : function() {
		var body = document.getElementById("fileTable");
        if (body.rows.length == 0)
            return;
        var file_checks = document.forms[0].fileSelect;
        if (body.rows.length == 1) {
            body.deleteRow(0);
        } else {
            for ( var i = body.rows.length; i > 0; i--) {
                if (file_checks[i - 1].checked)
                    body.deleteRow(i - 1);
            }
        }
	}
}
$(document).ready(function(){
    CommonUtil.setNumberField('salesTargetCost',true);
    CommonUtil.setNumberField('totalCost',true); 
    CommonUtil.setNumberField('profitRate',true); 
});