var sales = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SalesSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['SalesSearchGrid'])?Grids['SalesSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		//Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-projectNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:sales.search(Value);',
			Cols : [
			        {Name : 'projectNo', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'projectName', Width:230, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'salesStateName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'rev', Width:100, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'pmName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'pmdept', Width:100, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'rankName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'devTypeName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'sopDate', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'nationName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'oemName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'stateName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createDateFrom', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'mainSignalAndon', Width:60, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Type : "Int", Name : 'mainExpectSalesTotal', Width:70, Align : 'center', CanSort : '0', CanEdit : '0', Format : ",0"},
                    {Name : 'isDelay', Width:60, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'signalText', Width:60, Align : 'center', CanSort : '0', CanEdit : '0'},
			],Header :{
				CanDelete : '0', Align : 'Center',
				projectNo : 'ProjectNo',
				projectName :  '프로젝트명',
				salesStateName : '프로젝트 상태',
				rev : '버전',
				pmName : '담당자',
				pmdept : '부서',
				rankName : '중요도',
				devTypeName : '프로젝트유형',
				sopDate : 'SOP',
				nationName : '국가',
				oemName : '차종',
				stateName : '상태',
				createDateFrom : '작성일',
				mainSignalAndon : '신호등',
				mainExpectSalesTotal : '총매출',
				isDelay : '지연여부',
				signalText : '신호등(text)'
					
			},
			//excelDownloadFn : 'sales.excelDown();',
			SelectingSingle : '0',
				Panel : {
					Width : '21', Visible : '1',CanHide : '0'
				}
		}),'listGrid');
		/*
		createPaingGrid : function(){
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'SampleSearchGrid',
				Data : {
					Url : '/plm/ext/dqm/findPagingList.do?sortName=*Sort0',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:CommonGrid.pageSize
					}
				},
				Sort : '-title',
				Cols : [
	                {Name : 'title', Width:400, Align : 'Center', CanSort : '1'},
                    {Name : 'status', Width:100, Align : 'Center', CanSort : '1'}
				],Header :{
					CanDelete : '0', Align : 'Center',
					title : '제목',
					status : '상태'
				}
			}),'listGrid');
		*/
		//row click event
		Grids.OnClick = sales.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/sales/project/findPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				problem : $('[name=problem]').val(),
				problemNo : $('[name=problemNo]').val(),
				defectDivName : $('[name=defectDivName]').val(),
				defectTypeName : $('[name=defectTypeName]').val(),
				pjtno : $('[name=pjtno]').val(),
				occurName : $('[name=occurName]').val(),
				occurPlaceName : $('[name=occurPlaceName]').val(),
				raiserUserName : $('[name=raiserUserName]').val(),
				raiserUserOid : $('[name=raiserUserOid]').val(),
				partCategoryName : $('[name=partCategoryName]').val(),
				customerName : $('[name=customerName]').val(),
				relatedPart : $('[name=relatedPart]').val(),
				relatedPartOid : $('[name=relatedPartOid]').val(),
				cartypeName : $('[name=cartypeName]').val(),
				applyArea1 : $('[name=applyArea1]').val(),
				applyArea2 : $('[name=applyArea2]').val(),
				applyArea3 : $('[name=applyArea3]').val(),
				dqmStateName : $('[name=dqmStateName]').val(),
				createStartDate : $('[name=createStartDate]').val(),
				createEndDate : $('[name=createEndDate]').val(),
				compStartDate : $('[name=compStartDate]').val(),
				compEndDate : $('[name=compEndDate]').val(),
				defectTypeCode : $('[name=defectTypeCode]').val(),
				defectDivCode : $('[name=defectDivCode]').val(),
				partCategoryCode : $('[name=partCategoryCode]').val(),
				customerCode : $('[name=customerCode]').val()
			}
		}		
	},
	
	excelDown : function(){
		var url = '/plm/ext/dqm/excelDown.do';
		
		$('[name=searchForm]').attr('action', url);
		$('[name=searchForm]').attr('target', 'download');
		$('[name=searchForm]').submit();
		showProcessing();
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SalesSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SalesSearchGrid'].Source.Data.Url = '/plm/ext/sales/project/findPagingList.do?sortName=*Sort0';
		Grids['SalesSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SalesSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['SalesSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SalesSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	trim : function(val){
		val = val.replace(/^\s*/,'').replace(/\s*$/, '');
		val = val.replace(/\s/gi,'').replace(/ /gi,'');
		return val;
	},
	
	create : function(gubun){
		if(!confirm("저장하시겠습니까?")){
        	return;
        }
		/*var checkFlag = true;
		var checkcnt = 0
		$("[name='maincheck_r']").each(function(i){
            if(this.checked == true){
            	checkcnt = i;
            	checkFlag = false;
            }
        });
		
		$("[name='mainCheck']").each(function(j){
    		if(checkcnt=j){
    			$(this).val("Y");
    		}else{
    			$(this).val("N");
    		}
    	});
		
		if(checkFlag){
			alert("제품구분의 Main을 선택하세요.");
			return;
		}*/
		

		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		
		var projectCheck = true;
		var projectCnt = 0;
		
		var salesStateName = $('[name=salesStateName').val();
		if(salesStateName == '성공'){
			$("[name='productPjtNos']").each(function(i){
				projectCnt++;
	        });
			
			if(projectCnt == 0){
				projectCheck = false;
			}
			
		}
		
		if(!projectCheck){
			alert("수주 성공일때 개발 프로젝트를 입력하여야합니다.");
			selProjectNo();
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
/*		if($('[name="webEditor"]').val() == "<br />\n" || $('[name="webEditor"]').val() == null){
			alert(LocaleUtil.getMessage('09007'));//"검토의견을 입력하세요.");
			return;
		}*/
		
		var editor0 = fnGetEditorText(0);
		var salesGoal = $('[name=salesGoal').val();
		
		var basicEditAuth = $('[name=basicEditAuth').val();
		if("ok" == basicEditAuth){
			editor0 = sales.trim(editor0);
			if(editor0 == '' && salesGoal ==''){
				alert("영업목표를 입력하십시오.");
				return;
			}
		}
		
		var targetIdx = 0;
		
		$('#salesTargetTable tr').each(function(i){
			targetIdx = i;
		});
		
		if(targetIdx < 2){
			alert("영업추진현황을 입력하십시오.");
			return;
		}
		
		var planDate;
		var resultDate;
		
		var dateCheck = true;
		
		$('select[name=subject]').each(function(i) {
			if($(this).val() == ''){
				alert("Step " + eval(i+1) + " 의 Subject를 선택하십시오.");
				this.focus();
				dateCheck = false;
				return false;
			}
		});
		
		$("[name='planDate']").each(function(i){
			
			if(dateCheck){
				planDate = $(this).val();
				if(planDate == ''){
					alert("Step " + eval(i+1) + " 의 계획일자를 입력하십시오.");
					this.focus();
					dateCheck = false;
					return;
				}
				//영업기획요청으로 날짜 validation 제거
				/*resultDate = $("input[name=resultDate]").eq(i).val();
				
				if(resultDate != ''){
					var startCal = new Date(planDate.substring(0,4), Number(planDate.substring(5,7))-1, Number(planDate.substring(8,10)));
					var endCal = new Date(resultDate.substring(0,4), Number(resultDate.substring(5,7))-1, Number(resultDate.substring(8,10)));
				
					var interval = endCal - startCal;
					var day = 1000*60*60*24;
					
					var value = (interval / day) + 1;
					if(value > 0){
						
					}else{
						alert("Step " + eval(i+1) + " 의 실적일자는 계획일자 이후 날짜로 입력하십시오.");
						this.focus();
						dateCheck = false;
						return;
					}
				}*/
			}
			
        });
		
		
		if(!dateCheck){
			return;
		}
		
		$('input[name=tempPlanDate]').each(function(i) {
        	$(this).attr("name","planDate");
        });
        
        $('input[name=tempResultDate]').each(function(i) {
            $(this).attr("name","resultDate");
        });
		
		var plancheck = true;
		$('select[name=planCheck]').each(function(i) {
			if($(this).val() == 'none' && $("input[name=resultDate]").eq(i).val() != ''){
				
				this.focus();
				plancheck = false;
			}
		});
		
		if(!plancheck){
			alert("진행상황(신호등)을 선택하시기 바랍니다.");
			return;
		}
		
		var editorCheck = true;
		
		$('select[name=planCheck]').each(function(i) {
			
			if(editorCheck){
				if(sales.trim($("textarea[name=propelwebEditorText]").eq(i).val()) == '' && $('select[name=planCheck]').eq(i).val() != 'none'){
					alert("진행현황이 입력되지 않았습니다.\n\nStep "+eval(i+1)+"을 확인하세요.");
					editorCheck = false;
					return;
				}
				
				if($(this).val() == 'red'){
					if(sales.trim($("textarea[name=problemwebEditorText]").eq(i).val()) == '' || sales.trim($("textarea[name=planwebEditorText]").eq(i).val()) == ''){
						alert("진행현황이 Red 일때 문제점 및 해결방안을 입력하셔야합니다.\n\nStep "+eval(i+1)+"을 확인하세요.");
						editorCheck = false;
						return;
					}
				}
			}
			
		});
		
		if(!editorCheck){
			return;
		}
		
		
		
		if(gubun == 'transient'){
			$('[name=isTransient]').val(true);
		}
		
		
        
        $("[name='resultDate']").each(function(i){
			if($(this).val() == ''){
				$(this).val('0');
			}
		});
		
		$('[name=salesProjectForm]').attr('action', '/plm/ext/sales/project/salesProjectCreate.do');
		$('[name=salesProjectForm]').attr('target', 'download');
		
		$('[name=salesProjectForm]').submit();
		showProcessing();
	},
	
	update : function(oid,revise){
		location.href="/plm/ext/sales/project/updateSalesProjectForm.do?oid="+oid+"&revise="+revise;
		showProcessing();
	},
	
	goCreateView : function(){
		getOpenWindow2('/plm/ext/sales/project/createSalesProjectForm.do','createSalesProjectFormPopup',1100,800);
	},
	
	golocationView : function(oid){
		location.href="/plm/ext/sales/project/viewSalesProjectForm.do?oid="+oid;
		showProcessing();
	},
	
	// 버전이력 팝업
	viewVerHistory : function (oid){
		try{
			viewVersionHistory(oid);
		}catch(e){
			alert(e);
		}
	    
	},
	
	csView : function(oid){
		getOpenWindow2('/plm/ext/sales/project/viewSalesCSProjectForm.do?oid='+oid+'&csYN=N','viewSalesCSProjectFormPopup',1300,800);
	},
	
	fn_changeCombo2 : function() {
        var color = "";
        $('select[name=planCheck]').each(function(i) {
        	color = $(this).val();
        	if(color == 'none'){
        		color = '';
        	}

            $(this).css('backgroundColor', color);
        });
        
    },
	
	goView : function(grid,row,col,x,y){
        if(row.oid){
        	if('projectNo' == col){
        		getOpenWindow2('/plm/ext/sales/project/viewSalesProjectForm.do?oid='+row.oid,'viewSalesProjectFormPopup',1100,800);
        
        	}else if('rev' == col){
        		viewVersionHistory(row.oid);
        	}
        }
    },
	
	goCsView : function(){
		getOpenWindow2("/plm/ext/sales/project/viewSalesCSProjectForm.do?oid=&csYN=Y",'viewSalesCSProjectFormPopup',1300,800);
	},
    
    csMettingUpdate : function(oid){
    	if(!confirm("저장하시겠습니까?")){
        	return;
        }
		
    	$('[name=salesProjectForm]').attr('action', '/plm/ext/sales/project/salesProjectSetCSmeeting.do?oid='+oid+"&gubun=Y");
		$('[name=salesProjectForm]').attr('target', 'download');
		
		$('[name=salesProjectForm]').submit();
		showProcessing();
	},
	csMettingCancel : function(oid){
		if(!confirm("저장하시겠습니까?")){
        	return;
        }
    	$('[name=salesProjectForm]').attr('action', '/plm/ext/sales/project/salesProjectSetCSmeeting.do?oid='+oid+"&gubun=");
		$('[name=salesProjectForm]').attr('target', 'download');
		
		$('[name=salesProjectForm]').submit();
		showProcessing();
	},
	
	deleteLastProject : function(){
		var G = Grids[0];
        
        if( G != null ){
        	var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('프로젝트를 선택하십시오.');
                return;
            }else{
            	if(!confirm("선택한 프로젝트의 최신본이 삭제되며 복구 불가합니다.\n정말로 삭제하시겠습니까?")){
            		return;
            	}else{
            		var arroid = "";
                    
                    for ( var i=0; i<R.length; i++ ) {
                         subArr = new Array();

                         arroid += R[i].oid+",";
                    }

                    $.ajax({
                        type: 'get',
                        async: false,
                        cache: false,
                        url: '/plm/ext/sales/project/deleteLastProject.do?arroid='+arroid,
                        success: function (data) {
                            try{
                                if(data == 'S'){
                                	alert("삭제되었습니다.");
                                	sales.search();
                                }else if(data == 'Fail'){
                                	alert("실패하였습니다.");
                                }else{
                                	alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 	바랍니다.");
                                }
                                
                            }catch(e){alert(e.message); ret = "E"; }
                        },
                        fail : function(){
                            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
                            ret = "E";
                        }
                    });
            	}
            }
        }
	},
	
	multiCSupdate : function(gubun){
		var G = Grids[0];
        
        if( G != null ){
            
            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('프로젝트를 선택하십시오.');
                return;
            }
            
            if(gubun == 'Y'){
            	if(!confirm("회의 지정하시겠습니까?\n\n※ 자동 skip대상 안내\n1.이미 지정되어있는 프로젝트는 skip 됩니다.\n2.내 부서가 아닌 경우 skip 됩니다.\n3.결재진행중인 프로젝트는 skip 됩니다.")){
                	return;
                }
            }else{
            	if(!confirm("취소하시겠습니까?")){
                	return;
                }
            }
            
            
            var arrOid = "";
            
            for ( var i=0; i<R.length; i++ ) {
                 subArr = new Array();

                 arrOid += R[i].oid+"," //oid
            }
            
            $.ajax({
                type: 'get',
                async: false,
                cache: false,
                url: '/plm/ext/sales/project/csMultiUpdate.do?arroid='+arrOid+"&gubun="+gubun,
                success: function (data) {
                    try{
                        if(data == 'S'){
                        	alert("저장되었습니다.");
                        	sales.search();
                        }else if(data == 'Fail'){
                        	alert("실패하였습니다.");
                        }else if(data.substring(0,1) == 'N'){
                        	alert("CS회의가 마감되었습니다.\n\n다음 회의등록가능일자 : "+data.substring(1)+"");
                        }else{
                        	alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 	바랍니다.");
                        }
                        
                    }catch(e){alert(e.message); ret = "E"; }
                },
                fail : function(){
                    alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
                    ret = "E";
                }
            });
        }
	}
	
}