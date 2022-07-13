var invest = {
		
		createPaingGrid : function(){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sort : '-reqNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:invest.search(Value);',
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
				Cols : [
						{Name : 'investTypeCodeName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'reqNo', Width:150, Align : 'center', CanSort : '1', CanEdit : '0',Type : "Text", OnClick : 'javascript:openView(Row.oid);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
						{Name : 'reqName', Width:200, Align : 'left', CanSort : '1', CanEdit : '0',Type : "Text", OnClick : 'javascript:openView(Row.oid);',HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
						{Name : 'pjtNo', Width:130, Align : 'left', CanSort : '1', CanEdit : '0'},
						{Name : 'investStateCnt', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'delayState', Type : "Html", Width : 90, Align : 'center', CanSort : '0', CanEdit : '0'},
						{Name : 'investExpense_1', Width : 115, Align : 'right', CanSort : '1', CanEdit : '0', Format : "###,##0", Type : "Int",},
						{Name : 'investExpense_2', Width : 110, Align : 'right', CanSort : '1', CanEdit : '0', Format : "###,##0", Type : "Int",},
						{Name : 'collectExpense_1',Width : 110, Align : 'right', CanSort : '1', CanEdit : '0', Format : "###,##0", Type : "Int",},
						{Name : 'collectExpense_2',Width : 110, Align : 'right', CanSort : '1', CanEdit : '0', Format : "###,##0", Type : "Int",},
						{Name : 'requestDate',Width : 75, Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'completeDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'managerName', Width : 75, Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'mDeptName', Width : 75, Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'taskDeptCodeNames',Width : 200, Align : 'center', CanSort : '0', CanEdit : '0'},
						{Name : 'createDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'}   
					],
					Head :[
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id : "Header",Spanned : '1',
								investTypeCodeName : "유형",
								reqNo		  : "투자품의번호",
								reqName		  : "투자품의명",
								pjtNo       : "프로젝트번호",
								investStateCnt	  : "상태",
								delayState	  : "신호등",
								investExpense_1  : "투자비(금형/설비)",
								investExpense_2  : "투자비(기타)",
								collectExpense_1  : "회수(금형/설비)",
								collectExpense_2  : "회수(기타)",
								requestDate	  : "회수\n예정일자",
								completeDate  : "회수\n완료일자",
								managerName	  : "영업",
								mDeptName     : "영업부서",
								taskDeptCodeNames	  : "관련부서\n담당자",
								createDate	  : "등록일"
							}
							]
				/*,
				SelectingSingle : '1',
				Panel : {
					Width : '21', Visible : '1',CanHide : '0'
				}*/
			}),'listGrid');

			
			//row click event
			//Grids.OnClick = issue.goView;
		},
		search : function(perPage){
			
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/invest/findPagingList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['searchGrid'].Source.Data.Param.formPage=perPage;
			Grids['searchGrid'].ReloadBody();
			//Grids[0].Reload();
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
				perPageOnChange : 'javascript:invest.searchToDo(Value);',
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
	    	
	    	if(isNotEmpty){
	    		var investExpense_1 = $("#investExpense_1").val();
	    		var investExpense_2 = $("#investExpense_2").val();
	    		var investExpense = investExpense_1 + investExpense_2;
		    	if(investExpense_1 == ''){
		    		alert("투자비 (금형/설비)를 입력하세요.");
		    		isNotEmpty = false;
		    	}else if(investExpense_2 == ''){
		    		alert("투자비 (QDM/기타)를 입력하세요.");
		    		isNotEmpty = false;
		    	}else if(investExpense < 1){
		    		alert("투자비 합계가 0 원입니다.");
		    		isNotEmpty = false;
		    	}else{
		    		var managerOid = $("#managerOid").val();
		    		if(managerOid == null || managerOid.trim().length == 0){
		    			alert("영업 담당자를 입력하세요");
		    			isNotEmpty = false;
		    		}
		    		
		    		
		    		if($('#productProjectTableBody').length && $('#productProjectTableBody tr').length < 1 && isNotEmpty){
			    		alert('관련 프로젝트가 누락되었습니다.');
			    		isNotEmpty = false;
			    	}
			    	var fileCnt = 0;
			        $("#iFileTableOld1").find("#secondaryFiles").each(function() {
			        	if(this.value != ''){
			        		fileCnt++;
			        	}
			        });
			        
			        if(fileCnt == 0){
			        	
			        	$("#iFileTableOld1").find("#secondaryFileOids").each(function() {
				        	if(this.value != ''){
				        		fileCnt++;
				        	}
				        });
			        }
			        if(fileCnt == 0 && isNotEmpty){
			            alert('투자품의 첨부파일이 누락되었습니다.');
			            isNotEmpty = false;
			        }
		    	}
		    	
	    	}
	    	
	    	
	    	return isNotEmpty;
	    },
	    investTaskFileDownload : function(oid){
	    	var param = new Object();
	    	param.oid = oid;
	    	ajaxCallServer("/plm/ext/invest/investTaskFileDownload", param, function(data){
	    		if(data.downloadUrl){
	    			location.href = data.downloadUrl;
	    		}else{
	    			alert("첨부파일이 없습니다.");
	    		}
	        });
	    },
	    customerSetting : function(){
	        var tmpArr= new Array();
	    	
	    	var name = "";
	    	$("[name='pjt_customer']").each(function(i){
	    		
	    		if($(this).val() != ''){
	    			name += ","+$(this).val();	
	    		}
	        });
	    	
	    	
	    	tmpArr = $.merge(name.substr(1).split(","), tmpArr);
	    	tmpArr = $.unique(tmpArr.sort());
	    	
	    	if($('#subContractorName').length){
	    		$('#subContractorName').val(tmpArr.join(","));	
	    	}else{
	    		$('#subContractorNames').text(tmpArr.join(","));
	    	}
	    	
	    }
}