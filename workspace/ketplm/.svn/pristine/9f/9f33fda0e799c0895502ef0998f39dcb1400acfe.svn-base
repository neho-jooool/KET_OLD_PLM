var drawingDistRequest = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'drawingDistRequestSearchGrid',
			Sync : 1,
			perPageOnChange : 'javascript:drawingDistRequest.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['drawingDistRequestSearchGrid'])?Grids['drawingDistRequestSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			Cols : [
                    {Name : 'distReqNumber', Align : 'Center', CanSort : '1', CanEdit : '0', Width:110 },
                    {Name : 'title', Align : 'left', CanSort : '1', CanEdit : '0', Width:200},
                    {Name : 'distType', Align : 'center', CanSort : '1', CanEdit : '0', Width:80 },
                    {Name : 'distDeptName', Align : 'left', CanSort : '1', CanEdit : '0', Width:150},
                    {Name : 'distSubcontractor', Align : 'left', CanSort : '1', CanEdit : '0', Width:100},
                    {Name : 'distReason', Align : 'left', CanSort : '1', CanEdit : '0', Width:200},
                    {Name : 'creator', Align : 'center', CanSort : '1', CanEdit : '0', Width:100 },
                    {Name : 'createStamp', Align : 'center', CanSort : '1', CanEdit : '0', Width:100},
                    {Name : 'state', Align : 'center', CanSort : '1', CanEdit : '0', Width:100 }
			],Header :{
				CanDelete : '0', Align : 'Center',
				distReqNumber : LocaleUtil.getMessage('09111'),//'배포요청서 No',
				title : LocaleUtil.getMessage('02524'),//'제목',
				distType : LocaleUtil.getMessage('09112'),//'배포유형',
				distDeptName : LocaleUtil.getMessage('09113'),//'사내배포처',
				distSubcontractor : LocaleUtil.getMessage('09114'),//'외주배포처',
				distReason : LocaleUtil.getMessage('09115'),//'배포사유',
				creator : LocaleUtil.getMessage('02431'),//'작성자',
				createStamp : LocaleUtil.getMessage('02428'),//'작성일',
				state : LocaleUtil.getMessage('01760'),//'상태'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = drawingDistRequest.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/edm/findPagingObjectList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				distReqNumber : $('[name=distReqNumber]').val(),
				title : $('[name=title]').val(),
				distType : $('[name=distType]').val(),
				drawingName : $('[name=drawingName]').val(),
				creator : $('[name=creator]').val(),
				drawingNo : $('[name=drawingNo]').val(),
				documentName : $('[name=documentName]').val(),
				documentNo : $('[name=documentNo]').val(),
				distDeptName : $('[name=distDeptName]').val(),
				createStartDate : $('[name=createStartDate]').val(),
				createEndDate : $('[name=createEndDate]').val(),
				distSubcontractor : $('[name=distSubcontractor]').val(),
				drawingDistEoArray : $('[name=drawingDistEoArray]').val()
			}
		}		
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['drawingDistRequestSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['drawingDistRequestSearchGrid'].Source.Data.Url = '/plm/ext/edm/findPagingObjectList.do?sortName=*Sort0';
		Grids['drawingDistRequestSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['drawingDistRequestSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['drawingDistRequestSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['drawingDistRequestSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	create : function(){
		
		if($('[name=title]').val() == "" || $('[name=title]').val() == null){
			alert(LocaleUtil.getMessage('09086'));//"제목을 입력하세요.");
			return;
		}
		if($('[name=distType]').val() == "" || $('[name=distType]').val() == null){
			alert(LocaleUtil.getMessage('09116'));//"배포유형을 선택하세요.");
			return;
		}
		
		var checkFlag = true;
		
		$("[name='drawingDistFileTypeArray']").each(function(){
            if(this.checked == true){
            	checkFlag = false;    
            }
        });
		
		if($("[name='direct2Hompage']").is(":checked")){
			checkFlag = false;
		}
		
		if(checkFlag){
			if($('[name=drawingDisEpmArray]').val() != "" && $('[name=drawingDisEpmArray]').val() != null){
				alert(LocaleUtil.getMessage('09117'));//"배포포맷을 선택하세요.");
				return;
			}
		}
		
		if($('[name=distReason]').val() == "" || $('[name=distReason]').val() == null){
			alert(LocaleUtil.getMessage('09118'));//"배포사유를 입력하세요.");
			return;
		}
		
		if($('[name=drawingDistDocArray]').val() == "" || $('[name=drawingDistDocArray]').val() == null){
			if($('[name=drawingDisEpmArray]').val() == "" || $('[name=drawingDisEpmArray]').val() == null){
				alert(LocaleUtil.getMessage('09119'));//"배포도면 또는 배포문서를 추가하세요.");
				return;
			}
		}
		
		var is3D = true;
		var cadNo = null;
		var isPdf = true;
		var cadNo2 = null;
		
		$('input[name=drawingDistFileTypeArray]').each(function(i) {
			if ($(this).is(":checked") && ($(this).attr("value") == 'STEP,IGS,CAT')) {
				
				$('#drawingTable tr').each(function() {
				    if (this.rowIndex > 0){
				    	if(!(this.cells[4].innerHTML == 'PRO/ENGINEER' && this.cells[9].innerHTML == '고객제출도면')){
				    		is3D = false;
				    		cadNo = this.cells[1].innerHTML;
				    	}
				    }
				});
			}
			
			if ($(this).is(":checked") && ($(this).attr("value") != 'STEP,IGS,CAT')) {
				$('#drawingTable tr').each(function() {
				    if (this.rowIndex > 0){
				    	if(this.cells[4].innerHTML == 'PRO/ENGINEER'){
				    		isPdf = false;
				    		cadNo2 = this.cells[1].innerHTML;
				    	}
				    }
				});
				
			}
		});
		
		if(!is3D){
			alert(cadNo+" 는 3D 변환대상이 아닙니다.");
			return;
		}
		if(!isPdf){
			alert(cadNo2+" 는 STEP,IGS,CAT 만 변환가능합니다.");
			return;
		}
		
		$('[name=createForm]').attr('action', '/plm/ext/edm/drawingDistRequestCreate.do');
		$('[name=createForm]').attr('target', 'download');
		$('[name=createForm]').submit();
		showProcessing();
	},
	
	reApproved  : function(){
		$('[name=viewForm]').attr('action', '/plm/ext/edm/drawingDistRequestReApproved.do');
		$('[name=viewForm]').attr('target', 'download');
		$('[name=viewForm]').submit();
		showProcessing();
	},
	
	
	update : function(){
		if($('[name=title]').val() == "" || $('[name=title]').val() == null){
			alert(LocaleUtil.getMessage('09086'));//"제목을 입력하세요.");
			return;
		}
		if($('[name=distType]').val() == "" || $('[name=distType]').val() == null){
			alert(LocaleUtil.getMessage('09116'));//"배포유형을 선택하세요.");
			return;
		}
		
		var checkFlag = true;
		
		$("[name='drawingDistFileTypeArray']").each(function(){
            if(this.checked == true){
            	checkFlag = false;    
            }
        });
		
		if(checkFlag){
			if($('[name=drawingDisEpmArray]').val() != "" && $('[name=drawingDisEpmArray]').val() != null){
				alert(LocaleUtil.getMessage('09117'));//"배포포맷을 선택하세요.");
				return;
			}
		}
		
		if($('[name=distReason]').val() == "" || $('[name=distReason]').val() == null){
			alert(LocaleUtil.getMessage('09118'));//"배포사유를 입력하세요.");
			return;
		}
		
		if($('[name=drawingDistDocArray]').val() == "" || $('[name=drawingDistDocArray]').val() == null){
			if($('[name=drawingDisEpmArray]').val() == "" || $('[name=drawingDisEpmArray]').val() == null){
				alert(LocaleUtil.getMessage('09119'));//"배포도면 또는 배포문서를 추가하세요.");
				return;
			}
		}
		
		var is3D = true;
		var cadNo = null;
		var isPdf = true;
		var cadNo2 = null;
		
		$('input[name=drawingDistFileTypeArray]').each(function(i) {
			if ($(this).is(":checked") && ($(this).attr("value") == 'STEP,IGS,CAT')) {
				
				$('#drawingTable tr').each(function() {
				    if (this.rowIndex > 0){
				    	if(!(this.cells[4].innerHTML == 'PRO/ENGINEER' && this.cells[9].innerHTML == '고객제출도면')){
				    		is3D = false;
				    		cadNo = this.cells[1].innerHTML;
				    	}
				    }
				});
			}
			
			if ($(this).is(":checked") && ($(this).attr("value") != 'STEP,IGS,CAT')) {
				$('#drawingTable tr').each(function() {
				    if (this.rowIndex > 0){
				    	if(this.cells[4].innerHTML == 'PRO/ENGINEER'){
				    		isPdf = false;
				    		cadNo2 = this.cells[1].innerHTML;
				    	}
				    }
				});
				
			}
		});
		
		if(!is3D){
			alert(cadNo+" 는 3D 변환대상이 아닙니다.");
			return;
		}
		if(!isPdf){
			alert(cadNo2+" 는 STEP,IGS,CAT 만 변환가능합니다.");
			return;
		}
		
		$('[name=updateForm]').attr('action', '/plm/ext/edm/drawingDistRequestUpdate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=updateForm]').submit();
		showProcessing();
	},
	
	remove : function(){
		if( confirm(LocaleUtil.getMessage('09022')) ){//"삭제하시겠습니까?") ){
			$('[name=updateForm]').attr('action', '/plm/ext/edm/drawingDistRequestDelete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
	},
	
	totalDownLoad : function(){
		$('[name=viewForm]').attr('action', '/plm/ext/edm/drawingDistTotalDown.do');
		$('[name=viewForm]').attr('target', 'download');
		$('[name=viewForm]').submit();
		//showProcessing();
	},
	
	goLocationView : function(oid){
		location.href="/plm/ext/edm/drawingDistRequestViewForm.do?oid="+oid;
		showProcessing();
	},
	
	goModify : function(oid){
		location.href="/plm/ext/edm/drawingDistRequestUpdateForm.do?oid="+oid;
		showProcessing();
	},
	
	goList : function(){
		location.href = '/plm/ext/edm/drawingDistRequestSearchList.do';
	},
	
	goCreate : function(grid,row,col,x,y){
		getOpenWindow2('/plm/ext/edm/drawingDistRequestCreateForm.do','drawingDistRequestCreatePopup',1024,768);
		//location.href = '/plm/ext/edm/drawingDistRequestCreateForm.do';
	},
	
	goView : function(grid,row,col,x,y){
        if(row.oid){
        	if('title' == col){
        	getOpenWindow2('/plm/ext/edm/drawingDistRequestViewForm.do?oid='+row.oid,'drawingDistRequestViewPopup',1024,768);
            //location.href='/plm/ext/edm/drawingDistRequestViewForm.do?oid='+row.oid;
        	}
    	}
    },
	
	docCheck : function(){
		var docCheckFlag = false;
	    
	    $("[name='drawingDistDocArray']").each(function(){
	        if(this.value != "" || this.value != "undefined"){
	        	docCheckFlag = true;    
	        }
	    });
	    
	    if(docCheckFlag == true){
	    	$("[name='drawingDistDiv']").each(function(){
	            if(this.value == "DOC"){
	                this.checked = true;    
	            }
	        });
	    } else {
	    	$("[name='drawingDistDiv']").each(function(){
	            if(this.value == "DOC"){
	                this.checked = false;    
	            }
	        });
	    }
	    
	},
    
    epmCheck : function(){
		var empCheckFlag = false;
	    
	    $("[name='drawingDisEpmArray']").each(function(){
	        if(this.value != "" || this.value != "undefined"){
	        	empCheckFlag = true;    
	        }
	    });
	    
	    if(empCheckFlag == true){
	    	$("[name='drawingDistDiv']").each(function(){
	            if(this.value == "DWG"){
	                this.checked = true;    
	            }
	        });
	    } else {
	    	$("[name='drawingDistDiv']").each(function(){
	            if(this.value == "DWG"){
	                this.checked = false;    
	            }
	        });
	    }
	    
	},
	
	docCheckYn : function(){
		var docCheckFlag = "N";
	    
	    $("[name='drawingDistDocArray']").each(function(){
	        if(this.value != "" || this.value != "undefined"){
	        	docCheckFlag = "Y";    
	        }
	    });
	    
	    return docCheckFlag;
	},
    
    epmCheckYn : function(){
		var empCheckFlag = "N";
	    
	    $("[name='drawingDisEpmArray']").each(function(){
	        if(this.value != "" || this.value != "undefined"){
	        	empCheckFlag = "Y";    
	        }
	    });
	    
	    return empCheckFlag;
	},
	
	//협력사검색 팝업 Open
	selectPartner : function(){
	  var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=multi&method=drawingDistRequest.linkPartner";
	  openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
	},

	//협력사 검색결과 셋팅
	linkPartner : function(arr){
	  if(arr.length == 0) {
	    alert(LocaleUtil.getMessage('09120'));//"협력사를 선택하시기 바랍니다.");
	    return;
	  }
	  
	  var name = "";
	  var no = "";

	  for(var i = 0; i < arr.length; i++){
		  if(name == ""){
			  name += arr[i][1];
		  }
		  else{
			  name += ", "+arr[i][1];
		  }
		  if(no == ""){
			  no += arr[i][0];
		  }
		  else{
			  no += ","+arr[i][0];
		  }
	  }
	  
	  //$("[name='distSubcontractorArray']").val(no);
	  $("[name='distSubcontractor']").val(name);
	  
	},
	
	fileTypeToggle : function(ckbox){
		var filetype = $(ckbox).val();
    	
    	if($(ckbox).val() != 'STEP,IGS,CAT'){
    		filetype = 'DWG,PDF'
    	}
    	
        if($(ckbox).is(":checked")){
        	$('input[name=drawingDistFileTypeArray]').each(function(){
        		if(filetype.indexOf($(this).val()) < 0){
        			$(this).prop("checked",false);	
        		}
        		
        	});
        }
	}
}