var sales = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SalesCSSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['SalesCSSearchGrid'])?Grids['SalesCSSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:sales.search(Value);',
			Cols : [
			        {Name : 'year', Width:200, Align : 'center', CanSort : '1', CanEdit : '0'},
			        {Name : 'month', Width:200, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'degree', Width:230, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'csStartDate', Width:230, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'csEndDate', Width:230, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'csDegreeDate', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createUser', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'modifyUser', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'csClose', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
			],Header :{
				CanDelete : '0', Align : 'Center',
				year : '년도',
				month : '월',
				degree :  '차수',
				csDegreeDate : '생성일자',
				csStartDate : 'CS기간 시작일',
				csEndDate : 'CS기간 종료일',
				createUser : '작성자',
				modifyUser : '수정자',
				csClose : '회의마감'
			},
			//excelDownloadFn : 'sales.excelDown();',
			SelectingSingle : '0'
				/*Panel : {
					Width : '21', Visible : '1',CanHide : '0'
				}*/
		}),'listGrid');
		//row click event
		Grids.OnClick = sales.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			//grid.Data.Page.Url = '/plm/ext/sales/project/findPagingList.do?sortName=*Sort0';
			grid.Data.Page.Url = '/plm/ext/sales/project/findPagingCSList.do?sortName=';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength
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
		if(!perPage) perPage = Grids['SalesCSSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		//Grids['SalesCSSearchGrid'].Source.Data.Url = '/plm/ext/sales/project/findPagingCSList.do?sortName=*Sort0';
		Grids['SalesCSSearchGrid'].Source.Data.Url = '/plm/ext/sales/project/findPagingCSList.do?sortName=';
		Grids['SalesCSSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SalesCSSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['SalesCSSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SalesCSSearchGrid'].ReloadBody();
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
	
	goCreateView : function(){
		getOpenWindow2("/plm/ext/sales/project/createSalesCSForm.do",'createSalesCSForm',600,400);
	},
	
	csMeetingClose : function(){
		
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		
		var year = $('[name=year]').val();
		var month = $('[name=month]').val();
		var degree = $('[name=degree]').val();
		var msg = year + "년 " + month + "월 " + degree+ " 차수 CS회의";
		if(!confirm(msg+"를 종료하시겠습니까?\n종료시 지정된 프로젝트 목록에 대해 리비전이 생성됩니다.")){
        	return;
        }
		showProcessing();
		
		$.ajax({
        type: 'get',
        async: false,
        cache: false,
        url: '/plm/ext/sales/project/csMeetingClose.do',
        data : $('[name=SalesCSForm]').serialize(),
        success: function (data) {
            try{
                if(data == 'S'){
                	alert(msg+"가 종료되었습니다.");
                }else if(data == 'Fail'){
                	alert("실패하였습니다.\n관리자에게 문의하시기 바랍니다.");
                }else if(data.substring(0,1) == 'D'){
                	alert("아직 결재가 진행되지 않았습니다.\n\n"+data.substring(1));
                }
                
            }catch(e){alert(e.message); ret = "E"; }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
            ret = "E";
        }
        });
		hideProcessing();
		document.location.reload();     
	},
    
	CSDegreeCreate : function(gubun){
		
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		
		if(!confirm("차수 생성 하시겠습니까?")){
        	return;
        }
		
		/*$.ajax({
            type: 'get',
            async: false,
            cache: false,
            url: '/plm/ext/sales/project/csDegreeCreate.do',
            data : $('[name=SalesCSForm]').serialize(),
            success: function (data) {
                try{
                    if(data == 'S'){
                    	alert("저장되었습니다.");
                    	sales.search();
                    }else if(data == 'Fail'){
                    	alert("실패하였습니다.");
                    }else if(data == 'E'){
                    	alert("CS회의로 지정된 프로젝트가 없습니다.");
                    }else{
                    	alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 	바랍니다.");
                    }
                    
                }catch(e){alert(e.message); ret = "E"; }
            },
            fail : function(){
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
                ret = "E";
            }
        });*/
		
		$('[name=SalesCSForm]').attr('action', '/plm/ext/sales/project/csDegreeCreate.do');
		$('[name=SalesCSForm]').attr('target', 'download');
		
		$('[name=SalesCSForm]').submit();
		showProcessing();
	},
	
	goView : function(grid,row,col,x,y){
        if(row.oid){
        	if('degree' == col){
        		
        		var url = "/plm/ext/sales/project/viewSalesCSForm.do?oid="+row.oid;
                
                var name = "viewSalesCSForm"+row.oid;
                var opt = launchCenter(800, 768);
                opt += ", resizable=yes,toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=1,resizable=1";
                var windowWin = window.open(url,name,opt);
                
                //windowWin.resizeTo(width,height);
                windowWin.focus();
        	}
        }
    },
	
    openCsProject : function(oid){
		//getOpenWindow2('/plm/ext/sales/project/viewSalesCSProjectForm.do?oid='+oid+'&csYN=N','viewSalesCSProjectFormPopup',1300,800);
    	getOpenWindow2('/plm/ext/sales/project/viewSalesProjectForm.do?oid='+oid,'viewSalesProjectFormPopup',1100,800);
	},
	
	
    csMeetingSortUpdate : function(isFile){
    	
    	var msg = "CS 회의 순서를 변경하시겠습니까?";
    	var goFlag = true;
    	
    	if(isFile == 'file'){
    		msg = "CS 회의 파일 및 순서 저장하시겠습니까?\r\n\r\n1.해당 작업은 가능한 해당 차수의 모든 CS회의순서 지정 종료 후\r\n  진행하시기 바랍니다.\r\n\r\n";
    		msg += "2.파일 저장 이후에 CS회의 순서에 변동이 생겼다면 반드시\r\n   해당 작업을 재진행하여야합니다.";
    	}
    	
    	if(isFile == 'file' && isCsFileSave != 'ALL'){
    		alert("대상 부서 전체 조회시 저장 할 수 있습니다.");
    		goFlag = false;
    		return;
    	}
    	
    	
    	if(isFile == 'file'){
    		$('input[name=secondaryFiles]').each(function(i) {
    	    	if($(this).val() == ''){
    	    		alert('파일을 첨부하세요.');
    	    		goFlag = false;
    	    		this.focus();
    	    		return;
    	    	}
    	    });
    		if(!goFlag){
        		return;
        	}
    		
    		var uniqueArr = new Array();
    		var filecnt = 0;
    		
    		$('select[name=fileSort]').each(function(i) {
    			if($(this).val() == ''){
    				alert('파일 순서를 선택하세요.');
    				goFlag = false;
    	    		this.focus();
    	    		return;
    			}
    			uniqueArr[i] = $(this).val();
    			filecnt++;
    		});
    		
    		if(!goFlag){
        		return;
        	}
    		
    		uniqueArr = $.unique(uniqueArr.sort());

    		if(uniqueArr.length != filecnt){
    			goFlag = false;
    			alert("파일 순서가 중복되었습니다.");
    			return;
    		}
    		
    	}
    	if(!goFlag){
    		return;
    	}

    	if(!confirm(msg)){
        	return;
        }
    	
    	$('input[name=sortNo]').each(function(i) {
	    	$(this).val(i+1);
	    });
    	
    	if(isFile == 'file'){
    		$('[name=SalesCSForm]').attr('action', '/plm/ext/sales/project/csMeetingFileSortUpdate.do');
    		showProcessing();
            disabledAllBtn();
            $('[name=SalesCSForm]').submit();
    	}else{
    		//$('[name=SalesCSForm]').attr('action', '/plm/ext/sales/project/csMeetingSortUpdate.do');
    		$.ajax({
    	        type: 'post',
    	        async: false,
    	        cache: false,
    	        url: '/plm/ext/sales/project/csMeetingSortUpdate.do',
    	        data : $('[name=SalesCSForm]').serialize(),
    	        success: function (data) {
    	            try{
    	                if(data == 'S'){
    	                	alert("저장되었습니다.");
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


    	
    },
    
    CSdateUpdate : function(){
    	
    	if(!CommonUtil.checkEsseOk()){ 
			return;
		}
    	
    	var csStartDate = $('[name=csStartDate]').val();
    	var csEndDate = $('[name=csEndDate]').val();
    	var csNextStartDate = $('[name=csNextStartDate]').val();
    	var oid = $('[name=oid]').val();
    	
    	if(!confirm("날짜관련정보를 수정하시겠습니까?")){
        	return;
        }
    	
    	$.ajax({
	        type: 'post',
	        async: false,
	        cache: false,
	        url: '/plm/ext/sales/project/CSdateUpdate.do?csStartDate='+csStartDate+"&csEndDate="+csEndDate+"&csNextStartDate="+csNextStartDate+"&oid="+oid,
	        success: function (data) {
	            try{
	                if(data == 'S'){
	                	alert("저장되었습니다.");
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