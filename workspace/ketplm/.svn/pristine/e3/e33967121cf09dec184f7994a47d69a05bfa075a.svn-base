var issue = {
		createPaingGrid : function(viewMode){
			
			var cols = new Array();
			
			if(viewMode == "CS"){
				cols = [{Name : 'reqNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);', 
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'reqName', MinWidth:150, RelWidth : 1, Align : 'left', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);',
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'issueStateCnt', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'reqCodeName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'rankName', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'completeDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'delayState', Type : "Html", Width : 60, Align : 'center', CanSort : '0', CanEdit : '0'},
                ];
			}else if(viewMode){
				cols = [{Name : 'reqNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);', 
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'reqName', MinWidth:150, RelWidth : 1, Align : 'left', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);',
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'issueStateCnt', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'reqCodeName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'rankName', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'completeDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'delayState', Type : "Html", Width : 60, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'managerName', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'tmUserNames', Align : 'center', CanSort : '0', CanEdit : '0'},
                ];
			}else{
				cols = [{Name : 'reqNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);', 
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'reqName', MinWidth:150, RelWidth : 1, Align : 'left', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.oid);',
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'issueStateCnt', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'lastCustomerName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'subContractorName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'reqCodeName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'rankName', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'completeDate', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'delayState', Type : "Html", Width : 60, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'managerName', Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'tmUserNames', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'detailCodeName', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'partCount', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'partNos', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'pboNo', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:openView(Row.pboOid + "&key=popup&value=popup");',
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
                    {Name : 'pboName', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'pboState', Align : 'center', CanSort : '0', CanEdit : '0'}
                ];
			}
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sync : 1,
				Data : {
					Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Sort : '-reqNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:issue.search(Value);',
				Cols : cols,
				Header :{
					CanDelete : '0', Align : 'Center',
					CanSelect : 1,
					reqNo		  : "요청서 No",
					reqName		  : "요청명",
					issueStateCnt	  : "진행상태",
					lastCustomerName  : "자동차사",
					subContractorName : "고객사",
					reqCodeName	  : "요청구분",
					rankName	  : "등급",
					createDate	  : "등록일자",
					requestDate	  : "완료 요청일자",
					completeDate	  : "완료일자",
					delayState	  : "신호등",
					managerName	  : "요청자",
					tmUserNames	  : "세부 담당자",
					detailCodeName	  : "상세구분",
					partCount	  : "품목수",
					partNos	      : "품번",
					pboNo		  : "Project No",
					pboName		  : "프로젝트명",
					pboState	  : "프로젝트 상태"
				},
				//SelectingSingle : '0',
				Panel : {
					Width : '20', Visible : '1',CanHide : '0'
				}
			}),'listGrid');
			
			//row click event
			//Grids.OnClick = issue.goView;
		},
		search : function(perPage){
			
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/issue/findPagingList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['searchGrid'].Source.Data.Param.formPage=perPage;
			Grids['searchGrid'].ReloadBody();
			//Grids[0].Reload();
		},
		createTaskPaingGrid : function(viewMode){
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'taskSearchGrid',
				Sync : 1,
				Data : {
					Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['taskSearchGrid'])?Grids['taskSearchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Sort : '-issueMasterReqNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:issue.searchTask(Value);',
				Cols : [
					{Name : 'issueMasterReqNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.issueMaster != null ? Row.issueMaster.oid : "");', 
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'issueMasterReqName', MinWidth:150, RelWidth : 1, Align : 'left', CanSort : '1', CanEdit : '0',
					Type : "Text", OnClick : 'javascript:openView(Row.issueMaster != null ? Row.issueMaster.oid : "");',
					HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'issueMasterIssueStateCnt', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'subject', MinWidth:150, RelWidth : 1, Align : 'left', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:openView(Row.oid);',
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'workerName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'workerDeptName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'detailCodeName', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'partCount', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'partNos', MinWidth : 110, RelWidth : 1, Align : 'center', CanSort : '0', CanEdit : '0'},
					{Name : 'requestDate', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'completeDate', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'issueStateName', Type : 'Html', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'issueMasterLastCustomerName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'issueMasterSubContractorName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'issueMasterReqCodeName', Align : 'center', CanSort : '1', CanEdit : '0'},
					{Name : 'issueMasterRankName', Align : 'center', CanSort : '1', CanEdit : '0'},
	                {Name : 'issueMasterCreateDate', Align : 'center', CanSort : '1', CanEdit : '0'},
	                {Name : 'issueMasterRequestDate', Align : 'center', CanSort : '1', CanEdit : '0'},
	                {Name : 'issueMasterCompleteDate', Align : 'center', CanSort : '1', CanEdit : '0'},
	                {Name : 'issueMasterDelayState', Type : "Html", Width : 60, Align : 'center', CanSort : '0', CanEdit : '0'},
	                {Name : 'issueMasterManagerName', Align : 'center', CanSort : '1', CanEdit : '0'},
	            ],
				Header :{
					CanDelete : '0', Align : 'Center',
					CanSelect : 1,
					issueMasterReqNo				  : "요청서 No",
					issueMasterReqName			  	  : "요청명",
					issueMasterIssueStateCnt	 	  : "진행상태",
					subject							  :	"진행 요청사항",
					workerName						  :	"세부 담당자",
					workerDeptName					  :	"세부 담당부서",
					detailCodeName	  : "상세구분",
					partCount	  : "품목수",
					partNos	      : "품번",
					requestDate						  :	"세부 완료 요청일자",
					completeDate					  :	"세부 완료일자",
					issueStateName				 	  :	"세부항목 상태",
					issueMasterLastCustomerName		  : "자동차사",
					issueMasterSubContractorName	  : "고객사",
					issueMasterReqCodeName			  : "요청구분",
					issueMasterRankName	 		 	  : "등급",
					issueMasterCreateDate	 	 	  : "등록일자",
					issueMasterRequestDate	 	 	  : "완료 요청일자",
					issueMasterCompleteDate	  	 	  : "완료일자",
					issueMasterDelayState	 	 	  : "신호등",
					issueMasterManagerName	 	 	  : "요청자",
				},
				Panel : {
					Width : '20', Visible : '1',CanHide : '0'
				}
			}),'taskListGrid');
		},
		searchTask : function(perPage){
			if(!perPage) perPage = Grids['taskSearchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['taskSearchGrid'].Source.Data.Url = '/plm/ext/issue/findTaskPagingList.do?sortName=*Sort0';
			Grids['taskSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['taskSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['taskSearchGrid'].Source.Data.Param.formPage=perPage
			Grids['taskSearchGrid'].ReloadBody();
		},
		createToDoPaingGrid : function(viewMode){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sync : 1,
				Data : {
					Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				perPageOnChange : 'javascript:issue.searchToDo(Value);',
				Cols : [
					{Name : 'workName', MinWidth : 150, Align : 'center', CanSort : '0', CanEdit : '0'},
					{Name : 'title', MinWidth : 150, RelWidth : 1, Align : 'left', CanSort : '0', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:openView(Row.oid);',
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'reqUserName', MinWidth : 80, Align : 'center', CanSort : '0', CanEdit : '0'},
					{Name : 'receiveDate', MinWidth : 80, Align : 'center', CanSort : '0', CanEdit : '0'},
					{Name : 'requestDate', MinWidth : 80, Align : 'center', CanSort : '0', CanEdit : '0'},
	            ],
				Header :{
					CanDelete : '0', Align : 'Center',
					CanSelect : 1,
					workName	: "작업명",
					title		: "제목",
					reqUserName	: "요청자",
					receiveDate : "수신일",
					requestDate : "완료 요청일",
				},
				//SelectingSingle : '1',
				Panel : {
					Width : '20', Visible : '1',CanHide : '0'
				}
			}),'listGrid');
		},
		searchToDo : function(perPage){
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/issue/findToDoPagingList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Param.formPage=perPage
			Grids['searchGrid'].ReloadBody();
		},
	    clear : function(){
			$('[name=searchForm]')[0].reset();
			$('[name=searchForm] input').each(function(){
				$(this).val("");
			});
			$(".delayState").show();
		},
		viewIssueTaskHistory : function(oid){
		    var url = "/plm/ext/issue/itHistoryPopup?oid=" + oid;
		    window.open(url, "버전이력보기", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1080,height=500");
		},
		setPboNo : function(objArr){
	        $('[name=pboNo]').val(objArr[0][1]);
	        $('[name=pboOid]').val(objArr[0][0]).trigger('change');
	        $('[name=pboName]').val(objArr[0][2]);
		},
		changeCodeChild : function(codeType, pSel, cSel) {
	    	if ( $("#" + pSel).val() != null ) {
	    		var choiceCode  = $("#" + pSel).val();
	    		if(choiceCode.length > 1){
	    			$("#" + cSel).multiselect("uncheckAll");
	                $("#" + cSel).empty().data('options');
	                for(var i = 0; i < choiceCode.length; i++){
	                	$.ajax({
	            			url : "/plm/ext/code/getChildCodeList.do?codeType=" + codeType + "&parentCode="+choiceCode[i],
	            			type : "POST",
	            			dataType : 'json',
	            			async : false,
	            			success: function(data) {
	                            $.each(data, function() {
	                                $("#" + cSel).append("<option value='"+this.code+"'>"+ this.value +"</option>");
	                            });
	                            
	                            $("#" + cSel).multiselect('refresh');
	                        }
	            		});
	                }
	        		
	    		}
	    		else{
	    			$("#" + cSel).multiselect("uncheckAll");
	                $("#" + cSel).empty().data('options');
	        		$.ajax({
	        			url : "/plm/ext/code/getChildCodeList.do?codeType=" + codeType + "&parentCode="+choiceCode,
	        			type : "POST",
	        			dataType : 'json',
	        			async : false,
	        			success: function(data) {
	                        $.each(data, function() {
	                            $("#" + cSel).append("<option value='"+this.code+"'>"+ this.value +"</option>");
	                        });
	                        
	                        $("#" + cSel).multiselect('refresh');
	                    }
	        		});
	    		}
	    	}
	    },
	    onloadChildCode : function(codeType, code, pSel, cSel) {
	    	if ( $("#" + pSel).val() != null ) {
	    		var choiceCode  = $("#" + pSel).val();
	    		$("#" + cSel).multiselect("uncheckAll");
	            $("#" + cSel).empty().data('options');
	    		$.ajax({
	    			url : "/plm/ext/code/getChildCodeList.do?codeType=" + codeType + "&parentCode="+choiceCode,
	    			type : "POST",
	    			dataType : 'json',
	    			async : false,
	    			success: function(data) {
	                    $.each(data, function() {
	                        $("#" + cSel).append("<option value='"+this.code+"'>"+ this.value +"</option>");
	                    });
	                    $("#" + cSel).val(code);
	                    $("#" + cSel).multiselect('refresh');
	                }
	    		});
	    	}
	    },
	    needCheckAttribute : function(){
	    	
	    	var isNotEmpty = true;
	    	
	    	$("select, input").each(function(){
	    		
	    		var message = $(this).data("message");
	    		var value = this.value;
	    		
	    		window.console.log($(this).attr("name"), value);
	    		window.console.log((value == null || value.trim().length == 0));
	    		
	    		if(message != null && message.trim().length > 0 && (value == null || value.trim().length == 0)){
	    			alert(message);
	    			isNotEmpty = false;
	    			return false;
	    		}
	    	});
	    	
	    	return isNotEmpty;
	    },
	    issueTaskFileDownload : function(oid){
	    	var param = new Object();
	    	param.oid = oid;
	    	ajaxCallServer("/plm/ext/issue/issueTaskFileDownload", param, function(data){
	    		if(data.downloadUrl){
	    			location.href = data.downloadUrl;
	    		}else{
	    			alert("첨부파일이 없습니다.");
	    		}
	        });
	    },
	    
	    initDetailCode : function(code,detailCode){
	    	var choiceCode  = code;
	        $("#detailCode").multiselect("uncheckAll");
	        $("#detailCode").empty().data('options');
	        $.ajax({
	            url : "/plm/ext/code/getChildCodeList.do?codeType=ISSUEREQ&parentCode="+choiceCode,
	            type : "POST",
	            dataType : 'json',
	            async : false,
	            success: function(data) {
	            	data.sort(function(a,b){
	            	    return a.sorting > b.sorting ? 1 : -1
	            	})
	                $.each(data, function() {
	                    $("#detailCode").append("<option value='"+this.code+"'>"+ this.value +"</option>");
	                });
	                if(detailCode != null){
	                	$("#detailCode").val(detailCode);	
	                }
	                
	                $("#detailCode").multiselect('refresh');
	            }
	        });
	    }
	    
}