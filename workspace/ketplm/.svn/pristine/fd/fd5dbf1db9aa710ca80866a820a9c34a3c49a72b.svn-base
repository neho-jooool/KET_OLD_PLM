var gMode;
var dqm = {
	createPaingGrid : function(isSingle,mode){
		gMode = mode;
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'DqmSearchGrid',
			Sync : 1,
			SelectingSingle : isSingle,
			Panel : {
				Width : 21,
				CanHide : 0
			},
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['DqmSearchGrid'])?Grids['DqmSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			Cols : [
		        	{Name : 'problemNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'problem', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'cartypeName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'defectDivName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'defectTypeName', Width:120, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurDate', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'relatedPart', Width:200, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurDivName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'reviewDate', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'relatedEcrNo', Width:200, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'dqmStateName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createStamp', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				problemNo : LocaleUtil.getMessage('09001'),
				problem : LocaleUtil.getMessage('09002'),
				cartypeName : LocaleUtil.getMessage('02745'),//'차종',
				defectDivName : LocaleUtil.getMessage('09003'),//'불량구분',
				defectTypeName : LocaleUtil.getMessage('09004'),//'불량유형',
				occurName : LocaleUtil.getMessage('07137'),//'발생처',
				occurDate : LocaleUtil.getMessage('07138'),//'발생일',
				relatedPart : LocaleUtil.getMessage('09005'),//'부품 No',
				occurDivName : LocaleUtil.getMessage('07139'),//'발생구분',
				reviewDate : LocaleUtil.getMessage('02179'),//'완료일',
				relatedEcrNo : LocaleUtil.getMessage('07273'),//'관련ECR',
				dqmStateName : LocaleUtil.getMessage('01760'),//'상태',
				createStamp : LocaleUtil.getMessage('02428')//'작성일'
			},
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
		Grids.OnClick = dqm.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dqm/findPagingList.do?mode='+mode+'&sortName=*Sort0';
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
	
	search : function(perPage){
		if(!perPage) perPage = Grids['DqmSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['DqmSearchGrid'].Source.Data.Url = '/plm/ext/dqm/findPagingList.do?mode='+gMode+'&sortName=*Sort0';
		Grids['DqmSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['DqmSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['DqmSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['DqmSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	closeUpdate : function(){
		if($('[name="reviewRsltCode"]').val() == "" || $('[name="reviewRsltCode"]').val() == null){
			$('[name="reviewRsltCode"]').focus();
			alert(LocaleUtil.getMessage('09006'));//"검토결과를 입력하세요.");
			return;
		}
		
		$('[name=reviewWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=reviewWebEditorText]').val(fnGetEditorText(0));
		
		if($('[name="reviewWebEditorText"]').val() == "" || $('[name="reviewWebEditorText"]').val() == null){
			alert(LocaleUtil.getMessage('09007'));//"검토의견을 입력하세요.");
			return;
		}
		
		$('[name=updateForm]').attr('action', '/plm/ext/dqm/closeUpdate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=updateForm]').submit();
		showProcessing();
	},
	
	closeCreate : function(){
		if($('[name="reviewRsltCode"]').val() == "" || $('[name="reviewRsltCode"]').val() == null){
			$('[name="reviewRsltCode"]').focus();
			alert(LocaleUtil.getMessage('09006'));//"검토결과를 입력하세요.");
			return;
		}
		
		$('[name=reviewWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=reviewWebEditorText]').val(fnGetEditorText(0));
		
		if($('[name="reviewWebEditorText"]').val() == "" || $('[name="reviewWebEditorText"]').val() == null){
			alert(LocaleUtil.getMessage('09007'));//"검토의견을 입력하세요.");
			return;
		}
		
		$('[name=createForm]').attr('action', '/plm/ext/dqm/closeCreate.do');
		$('[name=createForm]').attr('target', 'download');
		$('[name=createForm]').submit();
		showProcessing();
	},
	
	goCloseModify : function(oid){
		location.href="/plm/ext/dqm/closeUpdateForm.do?oid="+oid;
		showProcessing();
	},
	
	goCloseCreate : function(oid){
		location.href="/plm/ext/dqm/closeCreateForm.do?oid="+oid;
		showProcessing();
	},
	
	goActionCreate : function(oid){
		location.href="/plm/ext/dqm/actionCreateForm.do?oid="+oid;
		showProcessing();
	},
	
	actionCreate : function(){
		if($('[name=actionUserOid]').val() == '' || $('[name=actionUserOid]').val() == null){
			alert(LocaleUtil.getMessage('09008'));//"검토자를 선택하세요.");
			return;
		}
		$('[name=createForm]').attr('action', '/plm/ext/dqm/actionCreate.do');
		$('[name=createForm]').attr('target', 'download');
		$('[name=causeWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=causeWebEditorText]').val(fnGetEditorText(0));
		$('[name=improveWebEditor]').val(fnGetEditorHTMLCode(false, 1));
		$('[name=improveWebEditorText]').val(fnGetEditorText(1));
		$('[name=createForm]').submit();
		showProcessing();
	},
	
	create : function(){
		if($('[name="problem"]').val() == "" || $('[name="problem"]').val() == null){
			$('[name="problem"]').focus();
			alert(LocaleUtil.getMessage('09009'));//"문제내용을 입력하세요.");
			return;
		}
		if($('[name="pjtoid"]').val() == "" || $('[name="pjtoid"]').val() == null){
			$('[name="pjtno"]').focus();
			alert(LocaleUtil.getMessage('09010'));//"Project No를 입력하세요.");
			return;
		}

		if($('[name="defectDivCode"]').val() == "" || $('[name="defectDivCode"]').val() == null){
			$('[name="defectDivCode"]').focus();
			alert(LocaleUtil.getMessage('09011'));//"불량구분을 입력하세요.");
			return;
		}

		if($('[name="defectTypeCode"]').val() == "" || $('[name="defectTypeCode"]').val() == null){
			$('[name="defectTypeCode"]').focus();
			alert(LocaleUtil.getMessage('09012'));//"불량유형을 입력하세요.");
			return;
		}

		if($('[name="urgencyCode"]').val() == "" || $('[name="urgencyCode"]').val() == null){
			$('[name="urgencyCode"]').focus();
			alert(LocaleUtil.getMessage('09013'));//"긴급도를 입력하세요.");
			return;
		}
		
		if($('[name="partCategoryName"]').val() == "" || $('[name="partCategoryName"]').val() == null){
			alert(LocaleUtil.getMessage('09014'));//"부품분류를 입력하세요.");
			return;
		}

		if($('[name="relatedPartOid"]').val() == "" || $('[name="relatedPartOid"]').val() == null){
			$('[name="relatedPart"]').focus();
			alert(LocaleUtil.getMessage('09015'));//"관련부품을 입력하세요.");
			return;
		}

		if($('[name="occurDivCode"]').val() == "" || $('[name="occurDivCode"]').val() == null){
			alert(LocaleUtil.getMessage('09016'));//"발생구분을 입력하세요.");
			return;
		}
		
		if($('[name="occurCode"]').val() == "" || $('[name="occurCode"]').val() == null){
			alert(LocaleUtil.getMessage('09017'));//"발생처를 입력하세요.");
			return;
		}
		
		if ( $("#occurCode").val() == "OP02" && ($('[name="customerCode"]').val() == null || $('[name="customerCode"]').val() == ""))  {
			alert(LocaleUtil.getMessage('09018'));//"발생처가 고객인 경우 고객사는 필수 입니다.");
			return;
		}
		
		if($('[name="occurDate"]').val() == "" || $('[name="occurDate"]').val() == null){
			alert(LocaleUtil.getMessage('09019'));//"발생일을 입력하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditorText"]').val() == "" || $('[name="webEditorText"]').val() == null){
			alert(LocaleUtil.getMessage('09020'));//"상세내용을 입력하세요.");
			return;
		}
		
		$('[name=createForm]').attr('action', '/plm/ext/dqm/create.do');
		$('[name=createForm]').attr('target', 'download');
		
		$('[name=createForm]').submit();
		showProcessing();
	},
	
	update : function(){
		if($('[name="problem"]').val() == "" || $('[name="problem"]').val() == null){
			alert(LocaleUtil.getMessage('09009'));//"문제내용을 입력하세요.");
			return;
		}
		if($('[name="pjtoid"]').val() == "" || $('[name="pjtoid"]').val() == null){
			alert(LocaleUtil.getMessage('09010'));//"Project No를 입력하세요.");
			return;
		}

		if($('[name="defectDivCode"]').val() == "" || $('[name="defectDivCode"]').val() == null){
			alert(LocaleUtil.getMessage('09011'));//"불량구분을 입력하세요.");
			return;
		}

		if($('[name="defectTypeCode"]').val() == "" || $('[name="defectTypeCode"]').val() == null){
			alert(LocaleUtil.getMessage('09012'));//"불량유형을 입력하세요.");
			return;
		}
		
		if($('[name="urgencyCode"]').val() == "" || $('[name="urgencyCode"]').val() == null){
			$('[name="urgencyCode"]').focus();
			alert(LocaleUtil.getMessage('09013'));//"긴급도를 입력하세요.");
			return;
		}
		
		if($('[name="partCategoryName"]').val() == "" || $('[name="partCategoryName"]').val() == null){
			alert(LocaleUtil.getMessage('09014'));//"부품분류를 입력하세요.");
			return;
		}

		if($('[name="relatedPartOid"]').val() == "" || $('[name="relatedPartOid"]').val() == null){
			alert(LocaleUtil.getMessage('09015'));//"관련부품을 입력하세요.");
			return;
		}

		if($('[name="occurDivCode"]').val() == "" || $('[name="occurDivCode"]').val() == null){
			alert(LocaleUtil.getMessage('09016'));//"발생구분을 입력하세요.");
			return;
		}
		
		if($('[name="occurCode"]').val() == "" || $('[name="occurCode"]').val() == null){
			alert(LocaleUtil.getMessage('09017'));//"발생처를 입력하세요.");
			return;
		}
		
		if ( $("#occurCode").val() == "OP02" && ($('[name="customerCode"]').val() == null || $('[name="customerCode"]').val() == ""))  {
			alert(LocaleUtil.getMessage('09018'));//"발생처가 고객인 경우 고객사는 필수 입니다.");
			return;
		}
		
		if($('[name="occurDate"]').val() == "" || $('[name="occurDate"]').val() == null){
			alert(LocaleUtil.getMessage('09019'));//"발생일을 입력하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditorText"]').val() == "" || $('[name="webEditorText"]').val() == null){
			alert(LocaleUtil.getMessage('09020'));//"상세내용을 입력하세요.");
			return;
		}
		
		$('[name=updateForm]').attr('action', '/plm/ext/dqm/update.do');
		$('[name=updateForm]').attr('target', 'download');
		
		$('[name=updateForm]').submit();
		showProcessing();
		/*
		$('[name=updateForm]').ajaxForm({
				  dataType    : "text",
				  success    : function(data){
			    	   alert("정상적으로 처리되었습니다.");
			    	   location.href='/plm/ext/dqm/viewForm.do?oid='+data;
			       },
			       error    : function(xhr, status, error){
			                    alert(xhr+"  "+status+"  "+error);
			                    
			       }
			  });
		/*
		$.ajax({
		       type       : "POST",
		       url        : "/plm/ext/dqm/update.do",
		       data       : $('[name=updateForm]').serialize(),
		       dataType   : "json",
		       success    : function(data){
		    	   alert("정상적으로 처리되었습니다.");
		    	   location.href='/plm/ext/dqm/viewForm.do?oid='+data;
		       },
		       error    : function(xhr, status, error){
		                    alert(xhr+"  "+status+"  "+error);
		                    
		       }
		   });
		*/
		
		/*
		$('[name=updateForm]').attr('action', '/plm/ext/dqm/update.do');
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=updateForm]').submit();		
		*/
	},

	remove : function(){
		if( confirm(LocaleUtil.getMessage('09022'))){//"삭제하시겠습니까?") ){
			$('[name=updateForm]').attr('action', '/plm/ext/dqm/delete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
	},
	
	actionUpdate : function(){
		if($('[name=actionUserOid]').val() == '' || $('[name=actionUserOid]').val() == null){
			alert(LocaleUtil.getMessage('09008'));//"검토자를 선택하세요.");
			return;
		}
		
		if ( $("#duplicateYn").val() == "YES" && ($('[name="duplicateReportCode"]').val() == null || $('[name="duplicateReportCode"]').val() == "")) {
			alert(LocaleUtil.getMessage('09021'));//"중복인경우 중복문제보고를 입력하셔야 합니다.");
			return;
		}
		$('[name=updateForm]').attr('action', '/plm/ext/dqm/actionUpdate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=causeWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=causeWebEditorText]').val(fnGetEditorText(0));
		$('[name=improveWebEditor]').val(fnGetEditorHTMLCode(false, 1));
		$('[name=improveWebEditorText]').val(fnGetEditorText(1));
		$('[name=updateForm]').submit();
		showProcessing();
	},
	
	actionDelete : function(){
		if( confirm(LocaleUtil.getMessage('09022'))){//"삭제하시겠습니까?") ){
			$('[name=updateForm]').attr('action', '/plm/ext/dqm/actionDelete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
	},
	
	goActionModify : function(oid){
		location.href="/plm/ext/dqm/actionUpdateForm.do?oid="+oid;
		showProcessing();
	},
	
	goModify : function(oid){
		location.href="/plm/ext/dqm/updateForm.do?oid="+oid;
		showProcessing();
	},
	
	
	goList : function(){
		location.href = '/plm/ext/dqm/dqmList.do';
		showProcessing();
	},
	
	goCreate : function(grid,row,col,x,y){
//		location.href = '/plm/ext/dqm/createForm.do';
		//getOpenWindow2('/plm/ext/dqm/createForm.do','createFormPopup',1100,750);
		getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=create','dqmMainFormPopup',1100,768);
	},
	
	goUpdateView : function(grid,row,col,x,y){
		if(row.oid){
//			getOpenWindow2('/plm/ext/dqm/updateForm.do?oid='+row.oid+'&raiseOid='+row.raiseOid ,'updateFormPopup',800,600);
			location.href='/plm/ext/dqm/updateForm.do?oid='+row.oid+'&raiseOid='+row.raiseOid;
			showProcessing();
		}
	},
	goView : function(grid,row,col,x,y){
        if(row.oid){
        	if('problem' == col){
        		if(row.dqmStateCode == 'RAISEAPPROVED' || row.dqmStateCode == 'ACTIONINWORK'){
        			getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid='+row.oid,'dqmMainFormPopup',1100,768);
        		} else if(row.dqmStateCode == 'ACTIONAPPROVED' || row.dqmStateCode == 'END'){
        			getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=closeToGrid&oid='+row.oid,'dqmMainFormPopup',1100,768);
        		} else{
        			getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=view&oid='+row.oid,'dqmMainFormPopup',1100,768);
        		}
        		
        	//getOpenWindow2('/plm/ext/dqm/viewForm.do?oid='+row.oid+'&raiseOid='+row.raiseOid,'viewFormPopup',1100,750);
//        	location.href='/plm/ext/dqm/viewForm.do?oid='+row.oid+'&raiseOid='+row.raiseOid;
        
        	}
        }
    },
	
	golocationView : function(oid){
		location.href="/plm/ext/dqm/viewForm.do?oid="+oid;
		showProcessing();
	},
	
	golocationActionView : function(oid){
		location.href="/plm/ext/dqm/actionViewForm.do?oid="+oid;
		showProcessing();
	},
	
	golocationCloseView : function(oid){
		location.href="/plm/ext/dqm/closeViewForm.do?oid="+oid;
		showProcessing();
	},
	
	setProjectNo: function(objArr){
        var projectNo= "";
        if(objArr[0][0].indexOf("ProductProject")== -1) {
        	alert(LocaleUtil.getMessage('09023'));//"제품 프로젝트만 선택 가능합니다.");
        	return;
        }
        
        $('[name=pjtno]').val(objArr[0][1]);
        $('[name=pjtoid]').val(objArr[0][0]).trigger('change');;
        $('[name=pjtname]').val(objArr[0][2]);
    },
    
    openDqmPopup: function(oid){
    	getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=view&oid='+oid,oid,1100,768);
    },
    
    openPrintViewPopup: function(oid){
    	getOpenWindow2('/plm/ext/dqm/printViewForm.do?oid='+oid,oid,1024,768);
    },
    
    openPrintActionViewPopup: function(oid){
    	getOpenWindow2('/plm/ext/dqm/printActionViewForm.do?oid='+oid,oid,1024,768);
    },
    
    openCreateECR : function(oid) {
        var url = "/plm/jsp/ecm/CreateProdEcr.jsp?dqmOid="+oid;
        
        var name = "CreateECRPROD";
    	var opt = launchCenter(1024, 768);
    	opt += ", resizable=yes";
        var windowWin = window.open(url,name,opt);
        windowWin.focus();
    	
    },
    
    openCreateECO : function(oid) {
        var url = "/plm/jsp/ecm/CreateProdEcoForm.jsp?dqmOid="+oid;
        
        var name = "CreateECRPROD";
    	var opt = launchCenter(1024, 768);
    	opt += ", resizable=yes";
        var windowWin = window.open(url,name,opt);
        windowWin.focus();
    	
    },
    
    openViewECR : function(oid) {
        var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid="+oid;
        
        var opt = launchCenter(1024, 768);
        opt += ", resizable=yes";
        var windowWin = window.open(url,name,opt);
    },
    
    occurCodeCheck : function(){
    	if ( $("#occurCode").val() == "OP02" && ($('[name="customerCode"]').val() == null || $('[name="customerCode"]').val() == ""))  {
    		SearchUtil.selectMultiSubContractor(selectMultiSubContractor);
    	}
    },
    
    duplicateYnCheck : function(){
    	if ( $("#duplicateYn").val() == "YES" && ($('[name="duplicateReportCode"]').val() == null || $('[name="duplicateReportCode"]').val() == ""))  {
    		SearchUtil.selectOneDqm('selectDqmAfterFunc');
    	}
    },
    
    onChangeProjectOid : function(){
    	if ( $('[name=pjtoid]').val() != null &&  $('[name=pjtoid]').val() != "") {
    		$.ajax({
    			url : "/plm/ext/dqm/searchPjtInfo.do?oid="+$('[name=pjtoid]').val() ,
    			type : "POST",
    			dataType : 'json',
    			async : false,
    			success: function(data) {
                    $.each(data, function() {
                    	$('[name=customerName]').val(this.subcontractorValue);
                    	$('[name=customerCode]').val(this.subcontractorOid);

                    	$('[name=relatedPartOid]').val(this.partOid);
                    	$('[name=relatedPart]').val(this.partNumber);
                    	$('[name=cartypeName]').val(this.carTypeName);
                    	$('[name=cartypeCode]').val(this.carTypeOid);

                    	$("#occurStepCode").val(this.occurStepCode);
                        $("#occurStepCode").multiselect('refresh');
                    });
                }
    		});
    	}
    },
    
    openTotalPrintView : function(oid){
    	getOpenWindow2('/plm/ext/dqm/printDqmViewForm.do?oid='+oid,oid,1024,768);
    },
    
    onChangePartOid : function(){
    	if ( $('[name=relatedPartOid]').val() != null &&  $('[name=relatedPartOid]').val() != "") {
    		$.ajax({
    			url : "/plm/ext/dqm/getPartClassification.do?oid="+$('[name=relatedPartOid]').val() ,
    			type : "POST",
    			dataType : 'json',
    			async : false,
    			success: function(data) {
                    $.each(data, function() {
                    	$('[name=partCategoryCode]').val(this.oid);
                    	$('[name=partCategoryName]').val(this.name);
                    });
                }
    		});
    	}
    },
    
    onloadDefectTypeCode : function(defectTypeCode) {
    	if ( $("#defectDivCode").val() != null ) {
    		var choiceCode  = $("#defectDivCode").val();
    		$("#defectTypeCode").multiselect("uncheckAll");
            $("#defectTypeCode").empty().data('options');
    		$.ajax({
    			url : "/plm/ext/code/getChildCodeList.do?codeType=PROBLEMDIVTYPE&parentCode="+choiceCode,
    			type : "POST",
    			dataType : 'json',
    			async : false,
    			success: function(data) {
                    $.each(data, function() {
                        $("#defectTypeCode").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                    });
                    $("#defectTypeCode").val(defectTypeCode);
                    $("#defectTypeCode").multiselect('refresh');
                }
    		});
    	}
    },
    
    changeDefectDivCode : function() {
    	if ( $("#defectDivCode").val() != null ) {
    		var choiceCode  = $("#defectDivCode").val();
    		if(choiceCode.length > 1){
    			$("#defectTypeCode").multiselect("uncheckAll");
                $("#defectTypeCode").empty().data('options');
                for(var i = 0; i < choiceCode.length; i++){
                	$.ajax({
            			url : "/plm/ext/code/getChildCodeList.do?codeType=PROBLEMDIVTYPE&parentCode="+choiceCode[i],
            			type : "POST",
            			dataType : 'json',
            			async : false,
            			success: function(data) {
                            $.each(data, function() {
                                $("#defectTypeCode").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                            });
                            
                            $("#defectTypeCode").multiselect('refresh');
                        }
            		});
                }
        		
    		}
    		else{
    			$("#defectTypeCode").multiselect("uncheckAll");
                $("#defectTypeCode").empty().data('options');
        		$.ajax({
        			url : "/plm/ext/code/getChildCodeList.do?codeType=PROBLEMDIVTYPE&parentCode="+choiceCode,
        			type : "POST",
        			dataType : 'json',
        			async : false,
        			success: function(data) {
                        $.each(data, function() {
                            $("#defectTypeCode").append("<option value='"+this.code+"'>"+ this.value +"</option>");
                        });
                        
                        $("#defectTypeCode").multiselect('refresh');
                    }
        		});
    		}
    		
    		
//    		$.ajax( {
//                url : "/plm/ext/shared/code/getChildCodeList.do",
//                type : "POST",
//                data : {codeType:'PROBLEMDIVTYPE', parentCode:$("#defectDivCode").val()},
//                dataType : 'json',
//                async : false,
//                success: function(data) {
//                    $.each(data.returnObj, function() {
//                        $("#"+defectTypeCode).append("<option value='"+this.code+"'>"+ this.value +"</option>");
//                    });
//                }
//            });
    	}
    }
}