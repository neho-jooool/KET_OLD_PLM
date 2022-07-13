var carTypeRequire = false;
var sampleRequest = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SampleRequestSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['SampleRequestSearchGrid'])?Grids['SampleRequestSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			perPageOnChange : 'javascript:sampleRequest.search(Value);',
			Sort : '-requestNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
			Cols : [
                    {Name : 'requestNo', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestTitle', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'purpose', Width:120, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'customerName', Width:150, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'customerContractor', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'carTypeName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'partNumber', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestPartName', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestCount', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'developeStageName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'createUserName', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestDate', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'pmUserName', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'dispensationDate', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'sampleRequestStateName', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				requestNo : LocaleUtil.getMessage('09078'),//요청서 No
				requestTitle : LocaleUtil.getMessage('02524'),//'제목',
				purpose : LocaleUtil.getMessage('09079'),//'용도',
				customerName : LocaleUtil.getMessage('00837'),//'고객사',
				customerContractor : LocaleUtil.getMessage('09080'),//'고객담당자',
				carTypeName : LocaleUtil.getMessage('09081'),//'적용차종',
				partNumber : LocaleUtil.getMessage('09082'),//'요청부품번호',
				requestPartName : LocaleUtil.getMessage('09083'),//'요청부품명',
				requestCount : LocaleUtil.getMessage('09084'),//'요청수량',
				developeStageName : LocaleUtil.getMessage('00629'),//'개발단계',
				createUserName : LocaleUtil.getMessage('02196'),//'요청자',
				requestDate : LocaleUtil.getMessage('02194'),//'요청일',
				pmUserName : LocaleUtil.getMessage('04104'),//'담당자',
				dispensationDate : LocaleUtil.getMessage('09085'),//'불출일',
				sampleRequestStateName : LocaleUtil.getMessage('01760'),//'상태'
			},
			SelectingSingle : '0'
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
		Grids.OnClick = sampleRequest.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/sample/sampleRequestFindPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				requestNo : $('[name=requestNo]').val(),
				requestTitle : $('[name=requestTitle]').val(),
				customerName : $('[name=customerName]').val(),
				carTypeName : $('[name=carTypeName]').val(),
				sampleRequestStateName : $('[name=sampleRequestStateName]').val(),
				customerContractor : $('[name=customerContractor]').val(),
				createUserDeptName : $('[name=createUserDeptName]').val(),
				createUserName : $('[name=createUserName]').val(),
				requstPartName : $('[name=requstPartName]').val(),
				pmUserName : $('[name=pmUserName]').val(),
				developeStageCodeArr : $('[name=developeStageCodeArr]').val(),
				pmUserDeptName : $('[name=pmUserDeptName]').val(),
				requstPartOidArr : $('[name=requstPartOidArr]').val()
			}
		}		
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['SampleRequestSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SampleRequestSearchGrid'].Source.Data.Url = '/plm/ext/sample/sampleRequestFindPagingList.do?sortName=*Sort0';
		Grids['SampleRequestSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SampleRequestSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['SampleRequestSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SampleRequestSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	setCarTypeRequire : function(flag){
    	carTypeRequire = flag;
    },
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	
	create : function(){
		if($('[name="requestTitle"]').val() == "" || $('[name="requestTitle"]').val() == null){
			$('[name="requestTitle"]').focus();
			alert(LocaleUtil.getMessage('09086'));//"제목을 입력하세요.");
			return;
		}
		if($('[name="customerCode"]').val() == "" || $('[name="customerCode"]').val() == null){
			$('[name="customerName"]').focus();
			alert(LocaleUtil.getMessage('09087'));//"고객사를 입력하세요.");
			return;
		}
		
		if(carTypeRequire){
			if($('[name="carTypeCode"]').val() == "" || $('[name="carTypeCode"]').val() == null){
				$('[name=""carTypeName""]').focus();
				alert(LocaleUtil.getMessage('09088'));//"차종을 입력하세요.");
				return;
			}
		}
		if($('[name="requestDate"]').val() == "" || $('[name="requestDate"]').val() == null){
			$('[name="requestDate"]').focus();
			alert(LocaleUtil.getMessage('09089'));//"요청기한을 입력하세요.");
			return;
		}
		if($('[name="purpose"]').val() == "" || $('[name="purpose"]').val() == null){
			$('[name=""purpose""]').focus();
			alert(LocaleUtil.getMessage('09090'));//"용도를 입력하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditorText"]').val() == "" || $('[name="webEditorText"]').val() == null){
			$('[name="webEditorText"]').focus();
			alert(LocaleUtil.getMessage('09091'));//"요청사유를 입력하세요.");
			return;
		}
		if($('[name="partOidArr"]').val() == "" || $('[name="partOidArr"]').val() == null){
			$('[name="partOidArr"]').focus();
			alert(LocaleUtil.getMessage('09092'));//"부품을 추가하세요.");
			return;
		}
		
		var countCheck = sampleRequest.partRequestCountArrNullCheckValidation()
		var pmUserOidcheck = sampleRequest.partPmUserOidArrNullCheckValidation()
		if(!countCheck){
			alert(LocaleUtil.getMessage('09093'));//"요청부품의 요청수량을 입력하세요.");
			return;
		}
		if(!pmUserOidcheck){
			alert(LocaleUtil.getMessage('09094'));//"요청부품의 담당자를 입력하세요.");
			return;
		}
		$('[name=createForm]').attr('action', '/plm/ext/sample/sampleRequestCreate.do');
		$('[name=createForm]').attr('target', 'download');
		$('[name=createForm]').submit();
		showProcessing();
	},
	
	
	reRequestCreate : function(){
		if($('[name="requestTitle"]').val() == "" || $('[name="requestTitle"]').val() == null){
			$('[name="requestTitle"]').focus();
			alert(LocaleUtil.getMessage('09086'));//"제목을 입력하세요.");
			return;
		}
		if($('[name="customerCode"]').val() == "" || $('[name="customerCode"]').val() == null){
			$('[name="customerName"]').focus();
			alert(LocaleUtil.getMessage('09087'));//"고객사를 입력하세요.");
			return;
		}
		if(carTypeRequire){
			if($('[name="carTypeCode"]').val() == "" || $('[name="carTypeCode"]').val() == null){
				$('[name=""carTypeName""]').focus();
				alert(LocaleUtil.getMessage('09088'));//"차종을 입력하세요.");
				return;
			}
		}
		if($('[name="requestDate"]').val() == "" || $('[name="requestDate"]').val() == null){
			$('[name="requestDate"]').focus();
			alert(LocaleUtil.getMessage('09089'));//"요청기한을 입력하세요.");
			return;
		}
		if($('[name="purpose"]').val() == "" || $('[name="purpose"]').val() == null){
			$('[name=""purpose""]').focus();
			alert(LocaleUtil.getMessage('09090'));//"용도를 입력하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditorText"]').val() == "" || $('[name="webEditorText"]').val() == null){
			$('[name="webEditorText"]').focus();
			alert(LocaleUtil.getMessage('09091'));//"요청사유를 입력하세요.");
			return;
		}
		if($('[name="partOidArr"]').val() == "" || $('[name="partOidArr"]').val() == null){
			$('[name="partOidArr"]').focus();
			alert(LocaleUtil.getMessage('09092'));//"부품을 추가하세요.");
			return;
		}
		
		var countCheck = sampleRequest.partRequestCountArrNullCheckValidation()
		var pmUserOidcheck = sampleRequest.partPmUserOidArrNullCheckValidation()
		if(!countCheck){
			alert(LocaleUtil.getMessage('09093'));//"요청부품의 요청수량을 입력하세요.");
			return;
		}
		if(!pmUserOidcheck){
			alert(LocaleUtil.getMessage('09094'));//"요청부품의 담당자를 입력하세요.");
			return;
		}
		$('[name=updateForm]').attr('action', '/plm/ext/sample/reRequsetCreate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=updateForm]').submit();
		showProcessing();
	},
	
	update : function(){
		if($('[name="requestTitle"]').val() == "" || $('[name="requestTitle"]').val() == null){
			$('[name="requestTitle"]').focus();
			alert(LocaleUtil.getMessage('09086'));//"제목을 입력하세요.");
			return;
		}
		if($('[name="customerCode"]').val() == "" || $('[name="customerCode"]').val() == null){
			$('[name="customerName"]').focus();
			alert(LocaleUtil.getMessage('09087'));//"고객사를 입력하세요.");
			return;
		}
		
		if(carTypeRequire){
			if($('[name="carTypeCode"]').val() == "" || $('[name="carTypeCode"]').val() == null){
				$('[name=""carTypeName""]').focus();
				alert(LocaleUtil.getMessage('09088'));//"차종을 입력하세요.");
				return;
			}
		}
		if($('[name="requestDate"]').val() == "" || $('[name="requestDate"]').val() == null){
			$('[name="requestDate"]').focus();
			alert(LocaleUtil.getMessage('09089'));//"요청기한을 입력하세요.");
			return;
		}
		if($('[name="purpose"]').val() == "" || $('[name="purpose"]').val() == null){
			$('[name=""purpose""]').focus();
			alert(LocaleUtil.getMessage('09090'));//"용도를 입력하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditorText"]').val() == "" || $('[name="webEditorText"]').val() == null){
			$('[name="webEditorText"]').focus();
			alert(LocaleUtil.getMessage('09091'));//"요청사유를 입력하세요.");
			return;
		}
		if($('[name="partOidArr"]').val() == "" || $('[name="partOidArr"]').val() == null){
			$('[name="partOidArr"]').focus();
			alert(LocaleUtil.getMessage('09092'));//"부품을 추가하세요.");
			return;
		}
		
		var countCheck = sampleRequest.partRequestCountArrNullCheckValidation()
		var pmUserOidcheck = sampleRequest.partPmUserOidArrNullCheckValidation()
		if(!countCheck){
			alert(LocaleUtil.getMessage('09093'));//"요청부품의 요청수량을 입력하세요.");
			return;
		}
		if(!pmUserOidcheck){
			alert(LocaleUtil.getMessage('09094'));//"요청부품의 담당자를 입력하세요.");
			return;
		}
		$('[name=updateForm]').attr('action', '/plm/ext/sample/sampleRequestUpdate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=updateForm]').submit();
		showProcessing();
	},
	
	remove : function(){
		if( confirm(LocaleUtil.getMessage('09095')) ){//"삭제하시겠습니까?") ){
			$('[name=viewForm]').attr('action', '/plm/ext/sample/sampleDelete.do');
			$('[name=viewForm]').attr('target', 'download');
			$('[name=viewForm]').submit();
			showProcessing();
		}
	},
	
	goModify : function(oid){
		location.href="/plm/ext/sample/sampleRequestUpdateForm.do?oid="+oid;
		showProcessing();
	},
	
	goCreate : function(grid,row,col,x,y){
//		location.href = '/plm/ext/dqm/createForm.do';
		//getOpenWindow2('/plm/ext/dqm/createForm.do','createFormPopup',1100,750);
		getOpenWindow2('/plm/ext/sample/sampleRequstMainForm.do','sampleRequstMainForm',1024,670);
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
        	if('requestTitle' == col){
        		getOpenWindow2('/plm/ext/sample/sampleRequstMainForm.do?oid='+row.oid,'sampleRequstMainForm',1024,670);
        	}
        }
    },
    
    goPrint : function(oid){
    	getOpenWindow2('/plm/ext/sample/printSampleRequestViewForm.do?oid='+oid,'sampleRequstPrintForm',1024,670);
    },

    goReceptionView : function(oid){
		location.href="/plm/ext/sample/sampleReceptionViewForm.do?oid="+oid;
		showProcessing();
	},
	
	receptionUpdate  : function(){
		$('[name=updateForm]').attr('action', '/plm/ext/sample/sampleReceptionUpdate.do');
		$('[name=updateForm]').attr('target', 'download');
		$('[name=updateForm]').submit();
		//showProcessing();
	},
	
	reRequest : function(){
		var check = sampleRequest.partCheckValidation();
		if(check){
			getOpenWindow2('','reRequsetCreateForm',1024,670);
			$('[name=updateForm]').attr('action', '/plm/ext/sample/reRequsetCreateForm.do?');
			$('[name=updateForm]').attr('target', 'reRequsetCreateForm');
			$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
			$('[name=webEditorText]').val(fnGetEditorText(0));
			$('[name=updateForm]').submit();
		} else {
			alert(LocaleUtil.getMessage('09096'));//"부품을 선택하세요.");
		}
		/* 이전 재요청 부품별 재요청 한것 요구사항 제거됨
		var check = sampleRequest.partCheckValidation();
		if(check){
			$('[name=updateForm]').attr('action', '/plm/ext/sample/reRequest.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
			$('[name=webEditorText]').val(fnGetEditorText(0));
			$('[name=updateForm]').submit();
			showProcessing();
		} else {
			alert("부품을 선택하세요.");
		}
		*/
	},
	
	reception : function(){
		var check = sampleRequest.dispensationExpectDateCheckValidation();
		if(check){
			$('[name=updateForm]').attr('action', '/plm/ext/sample/sampleReception.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
		else {
			alert(LocaleUtil.getMessage('09097'));//"불출예정일을 입력하세요.");
		}
		
	},
	
	golocationView : function(oid){
		location.href="/plm/ext/sample/sampleRequestViewForm.do?oid="+oid;
		showProcessing();
	},
	
	complete : function(oid){
		if( confirm(LocaleUtil.getMessage('09098')) ){//"완료 하시겠습니까?") ){
			$('[name=updateForm]').attr('action', '/plm/ext/sample/sampleComplete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
	},
	
	workComplete : function(oid){
		if( confirm(LocaleUtil.getMessage('09099')) ){//"작업완료 하시겠습니까?") ){
			$('[name=updateForm]').attr('action', '/plm/ext/sample/sampleWorkComplete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();
		}
	},
	
	dispensationExpectDateCheckValidation : function () {
		var returnFlag = true;
		$('[name=dispensationExpectDateArr]').each( function() {
			if($(this).val() == "" || $(this).val() == null){
				returnFlag = false;
			}
		});
		return returnFlag;
	},
	
	partPmUserOidArrNullCheckValidation : function () {
		var returnFlag = true;
		$("[name=partPmUserOidArr]").each( function() {
			if($(this).val() == null || $(this).val() == ""){
				returnFlag = false;
			}
		});
		
		return returnFlag;
	},
	
	partRequestCountArrNullCheckValidation : function () {
		var returnFlag = true;
		$("[name=partRequestCountArr]").each( function() {
			if($(this).val() == null || $(this).val() == ""){
				returnFlag = false;
			}
		});
		
		return returnFlag;
	},
	
	partCheckValidation : function () {
		var returnFlag = false;
		$("input[name=partCheck]:checkbox").each( function() {
			if($("input[name=partCheck]:checkbox").is(":checked")){
				returnFlag = true;
			}
		});
		
		return returnFlag;
	},
	
	goReceptionModify : function(oid){
		location.href="/plm/ext/sample/sampleReceptionUpdateForm.do?oid="+oid;
		//showProcessing();
	}
	
}