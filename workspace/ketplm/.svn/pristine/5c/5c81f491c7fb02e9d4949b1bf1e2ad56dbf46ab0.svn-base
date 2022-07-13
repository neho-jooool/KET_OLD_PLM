var carTypeRequire = false;
var dqm = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'DqmSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['DqmSearchGrid'])?Grids['DqmSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:dqm.search(Value);',
			Cols : [
			        {Name : 'problemNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'problem', Width:200, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'dqmStateName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurDate', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'pjtno', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'pjtname', Width:130, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'cartypeName', Width:60, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'issueName', Width:100, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurStepName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurPointName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'relatedPart', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'createStamp', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'requestDate', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'reviewDate', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'raiseCreateUserName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'raiseCreateUserDept', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'actionUserName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'actionDeptName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'occurDivName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'defectDivName', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'defectTypeName', Width:80, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'relatedEcrNo', Width:80, Align : 'center', CanSort : '0', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				problemNo : 'Issue No',
				problem :  LocaleUtil.getMessage('09002'),//'문제내용',
				dqmStateName : LocaleUtil.getMessage('01760'),//'상태',
				occurDate : LocaleUtil.getMessage('07138'),//'발생일',
				pjtno : 'Project No',
				pjtname : 'Project Name',
				cartypeName : LocaleUtil.getMessage('02745'),//'차종',
				issueName : LocaleUtil.getMessage('04630')+LocaleUtil.getMessage('00969'),//'문제점구분',
				occurStepName : LocaleUtil.getMessage('09028'),//'발생단계',
				occurPointName : '발생시점',
				occurName : LocaleUtil.getMessage('07137'),//'발생처',
				relatedPart : LocaleUtil.getMessage('09005'),//'부품 No',
				createStamp : LocaleUtil.getMessage('02428'),//'작성일'
				requestDate : '완료목표일',
				reviewDate : LocaleUtil.getMessage('02179'),//'완료일',
				raiseCreateUserName : '작성자',
				raiseCreateUserDept : '작성부서',
				actionUserName : '검토자',
				actionDeptName : '검토부서',
				occurDivName : LocaleUtil.getMessage('07139'),//'발생구분',
				defectDivName : LocaleUtil.getMessage('09003'),//'불량구분',
				defectTypeName : LocaleUtil.getMessage('09004'),//'불량유형',
				relatedEcrNo : LocaleUtil.getMessage('00907')//'관련ECO',
			},
			excelDownloadFn : 'dqm.excelDown();',
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
		Grids.OnClick = dqm.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
//			var totalCount = grid.Toolbar.T;
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dqm/findPagingList.do?sortName=*Sort0';
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
		if(!perPage) perPage = Grids['DqmSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['DqmSearchGrid'].Source.Data.Url = '/plm/ext/dqm/findPagingList.do?sortName=*Sort0';
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
		
		var checkFlag = true;

		$("[name='validationCheck']").each(function(){
            if(this.checked == true){
            	checkFlag = false;    
            }
        });
		
		if(checkFlag){
			alert("유효성검증 확인을 선택하세요.");
			return;
		}
		
		$('[name=reviewWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=reviewWebEditorText]').val(fnGetEditorText(0));
		
		if($('[name="reviewWebEditor"]').val() == "<br />\n" || $('[name="reviewWebEditor"]').val() == null){
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
		
		var checkFlag = true;

		$("[name='validationCheck']").each(function(){
            if(this.checked == true){
            	checkFlag = false;    
            }
        });
		
		if(checkFlag){
			alert("유효성검증 확인을 선택하세요.");
			return;
		}
		
		$('[name=reviewWebEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=reviewWebEditorText]').val(fnGetEditorText(0));
		
		if($('[name="reviewWebEditor"]').val() == "<br />\n" || $('[name="reviewWebEditor"]').val() == null){
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
	
	actionComplete : function(){
		if( confirm("진행완료하시겠습니까?") ){
			/*if($('[name=actionUserOid]').val() == '' || $('[name=actionUserOid]').val() == null){
				alert(LocaleUtil.getMessage('09008'));//"검토자를 선택하세요.");
				return;
			}*/
			
			if($('[name="execEndDate"]').val() == "" || $('[name="execEndDate"]').val() == null){
				alert("실제완료일을 입력하세요.");
				return;
			}
			
			$('[name=updateForm]').attr('action', '/plm/ext/dqm/actionComplete.do');
			$('[name=updateForm]').attr('target', 'download');
			$('[name=updateForm]').submit();
			showProcessing();	
		}
		
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
		
		if($('[name="issueCode"]').val() == "" || $('[name="issueCode"]').val() == null){
			$('[name="issueCode"]').focus();
			alert("문제점구분을 입력하세요.");
			return;
		}
		
		if($('[name="occurPointCode"]').val() == "" || $('[name="occurPointCode"]').val() == null){
			$('[name="occurPointCode"]').focus();
			alert("발생시점을 입력하세요.");
			return;
		}

		var issueCode = $('[name="issueCode"]').val();
		
		if(issueCode == 'DQMIS8' && ($('[name="defectDivCode"]').val() == "" || $('[name="defectDivCode"]').val() == null)){//개발품질문제일때만 체크
			$('[name="defectDivCode"]').focus();
			alert(LocaleUtil.getMessage('09011'));//"불량구분을 입력하세요.");
			return;
		}

		if(issueCode == 'DQMIS8' && ($('[name="defectTypeCode"]').val() == "" || $('[name="defectTypeCode"]').val() == null)){
			$('[name="defectTypeCode"]').focus();
			alert(LocaleUtil.getMessage('09012'));//"불량유형을 입력하세요.");
			return;
		}

		if($('[name="urgencyCode"]').val() == "" || $('[name="urgencyCode"]').val() == null){
			$('[name="urgencyCode"]').focus();
			alert(LocaleUtil.getMessage('09013'));//"긴급도를 입력하세요.");
			return;
		}
		
		if($('[name="importanceCode"]').val() == "" || $('[name="importanceCode"]').val() == null){
			$('[name="importanceCode"]').focus();
			alert("중요도를 입력하세요");
			return;
		}
		
		
		
		if($('[name="partCategoryName"]').val() == "" || $('[name="partCategoryName"]').val() == null){
			alert(LocaleUtil.getMessage('09014'));//"부품분류를 입력하세요.");
			return;
		}
		
		if($('[name="requestDate"]').val() == "" || $('[name="requestDate"]').val() == null){
			alert("완료목표일을 입력하세요.");
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
		
		if($('[name="occurStepCode"]').val() == "" || $('[name="occurStepCode"]').val() == null){
			alert("발생단계를 입력하세요.");
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
		
		if(!dqm.checkPeriod()){
			alert("완료목표일은 발생일 이후 날짜로 입력하여야합니다.");
			return;
		}
		
		var requestDate = $('[name=requestDate]').val();
		if(!dqm.checkToday(requestDate)){
			alert(LocaleUtil.getMessage('02161')); //오늘 일자보다 이전날짜를 입력하였습니다. 다시 입력해 주시기 바랍니다
			$('[name=requestDate]').focus();
			return;
		}
		
		if(carTypeRequire){
			if($('[name="cartypeCode"]').val() == "" || $('[name="cartypeCode"]').val() == null){
				alert(LocaleUtil.getMessage('02753'));//"차종을 입력해 주십시오");
				return;
			}
		}
		
		if($('[name=actionUserOid]').val() == '' || $('[name=actionUserOid]').val() == null){
			alert(LocaleUtil.getMessage('09008'));//"검토자를 선택하세요.");
			return;
		}
		
		if($('[name=closerOid]').val() == '' || $('[name=closerOid]').val() == null){
			alert("종결담당자를 선택하세요.");
			return;
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditor"]').val() == "<br />\n" || $('[name="webEditor"]').val() == null){
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

		var issueCode = $('[name="issueCode"]').val();
		
		if(issueCode == 'DQMIS8' && ($('[name="defectDivCode"]').val() == "" || $('[name="defectDivCode"]').val() == null)){//개발품질문제일때만 체크
			$('[name="defectDivCode"]').focus();
			alert(LocaleUtil.getMessage('09011'));//"불량구분을 입력하세요.");
			return;
		}

		if(issueCode == 'DQMIS8' && ($('[name="defectTypeCode"]').val() == "" || $('[name="defectTypeCode"]').val() == null)){//개발품질문제일때만 체크
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
		
		if($('[name="requestDate"]').val() == "" || $('[name="requestDate"]').val() == null){
			alert("완료목표일을 입력하세요.");
			return;
		}
		
		var requestDate = $('[name=requestDate]').val();
		if(!dqm.checkToday(requestDate)){
			alert(LocaleUtil.getMessage('02161')); //오늘 일자보다 이전날짜를 입력하였습니다. 다시 입력해 주시기 바랍니다
			$('[name=requestDate]').focus();
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
		
		if ( $("#occurCode").val() == "OP02" && ($('[name="customerName"]').val() == null || $('[name="customerName"]').val() == ""))  {
			alert(LocaleUtil.getMessage('09018'));//"발생처가 고객인 경우 고객사는 필수 입니다.");
			return;
		}
		
		if($('[name="occurDate"]').val() == "" || $('[name="occurDate"]').val() == null){
			alert(LocaleUtil.getMessage('09019'));//"발생일을 입력하세요.");
			return;
		}
		
		if(!dqm.checkPeriod()){
			alert("완료목표일은 발생일 이후 날짜로 입력하여야합니다.");
			return;
		}
		
		if(carTypeRequire){
			if($('[name="cartypeName"]').val() == "" || $('[name="cartypeName"]').val() == null){
				alert(LocaleUtil.getMessage('02753'));//"차종을 입력해 주십시오");
				return;
			}
		}
		
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		
		if($('[name="webEditor"]').val() == "<br />\n" || $('[name="webEditor"]').val() == null){
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
		var cause = null;
		$('input[name=causeCode]').each(function(i) {
			if ($(this).is(":checked")) {
				cause = $(this).attr("value");
			}
		});
		
		if(cause == null || cause == ''){
			alert(LocaleUtil.getMessage('09140'));//"원인을 선택하세요.");
			return;
		}
		
		if($('[name=actionUserOid]').val() == '' || $('[name=actionUserOid]').val() == null){
			alert(LocaleUtil.getMessage('09008'));//"검토자를 선택하세요.");
			return;
		}
		
		if($('[name="designChangeYn"]').val() == "" || $('[name="designChangeYn"]').val() == null){
			alert(LocaleUtil.getMessage('09142'));//"설계변경반영여부를 선택하세요.");
			return;
		}
		
		if($('[name="validationDate"]').val() == "" || $('[name="validationDate"]').val() == null){
			alert("유효성검증 예정일을 입력하세요.");
			return;
		}
		
		if($('[name="designChangeYn"]').val() == "YES" && ($('[name="drawingOutDate"]').val() == null || $('[name="drawingOutDate"]').val() == '' )){
			alert(LocaleUtil.getMessage('09143'));//"설계변경반영여부가 YES 이면 도면출도 항목을 입력하여야합니다.");
			$('[name=drawingOutDate]').focus();
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
	
	goCopy : function(oid){
//		location.href = '/plm/ext/dqm/createForm.do';
		//getOpenWindow2('/plm/ext/dqm/createForm.do','createFormPopup',1100,750);
		getOpenWindow2('/plm/ext/dqm/dqmMainForm.do?type=copy&oid='+oid,'dqmMainFormPopup',1100,768);
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
        		if(row.dqmStateCode == 'RAISEAPPROVED' || row.dqmStateCode == 'ACTIONINWORK' || row.dqmStateCode == 'ACTIONPROGRESS' ){
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
    
    setCarTypeRequire : function(flag){
    	carTypeRequire = flag;
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

                    	if(this.partOid != null && this.partOid != ""){
                        	$('[name=relatedPartOid]').val(this.partOid).trigger('change');
                        	$('[name=relatedPart]').val(this.partNumber).trigger('change');
                    	}
                    	$('[name=cartypeName]').val(this.carTypeName);
                    	$('[name=cartypeCode]').val(this.carTypeOid);

                    	$("#occurStepCode").val(this.occurStepCode);
                        $("#occurStepCode").multiselect('refresh');
                        if("true" == this.carTypeRequiredFlag){
                        	$('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745')+"<span class='red'>*</span>");
                        	carTypeRequire = true;
                        }else{
                        	$('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745'));
                        	carTypeRequire = false;
                        }
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
    
    onChangeRequestDate : function(){
    	if ( $('[name=requestDate]').val() != null &&  $('[name=requestDate]').val() != "") {
    		var requestDate = $('[name=requestDate]').val();
    		if(!dqm.checkToday(requestDate)){
    			alert(LocaleUtil.getMessage('02161')); //오늘 일자보다 이전날짜를 입력하였습니다. 다시 입력해 주시기 바랍니다
    			$('[name=requestDate]').val("");
    			$('[name=requestDate]').focus();
    			return;
    		}
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
    
    checkToday : function(checkdate) {
    	var now = new Date();

        var today = now.getFullYear()+'-'+dqm.fncLPAD((now.getMonth()+1))+'-'+dqm.fncLPAD(now.getDate());
        if(today <= checkdate) {
        	return true;
        }
        return false;
    },
    
    fncLPAD : function(num) {
    	if(num<10) return '0'+num;
        else return ''+num;
    },
    
    checkPeriod : function() {
	    var requestDate = $('[name=requestDate]').val();
		var occurDate = $('[name=occurDate]').val();
		
		var startCal = new Date(requestDate.substring(0,4), Number(requestDate.substring(5,7))-1, Number(requestDate.substring(8,10)));
		var endCal = new Date(occurDate.substring(0,4), Number(occurDate.substring(5,7))-1, Number(occurDate.substring(8,10)));
	
		var interval = endCal - startCal;
		var day = 1000*60*60*24;
		
		var value = (interval / day) + 1;
		if(value > 0){
			return false;
		}else{
			return true;
		}
    },
    
    changeIssueCode : function(){
    	
    	if("DQMIS8" == $("#issueCode").val()){
    		
    		$("#defectDivCode").multiselect('enable');
            $("#defectTypeCode").multiselect('enable');
            
            
    	}else{
    		
    		$("#defectDivCode").val("");
            $("#defectDivCode").multiselect('refresh');
            $("#defectDivCode").multiselect('disable');
            
            $("#defectTypeCode").multiselect("uncheckAll");
            $("#defectTypeCode").empty().data('options');
            $("#defectTypeCode").multiselect('refresh');
            
            $("#defectTypeCode").multiselect('disable');
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
    },
    
    setCloser : function(dqmoid){
    	showProcessing();
    	var closer = "";
    	if ( $('[name=pjtoid]').val() != null &&  $('[name=pjtoid]').val() != "") {
    		var pjtno = $('[name=pjtno]').val();
    		
    		$.ajax({
    			url : "/plm/ext/dqm/setCloser.do?oid="+dqmoid ,
    			type : "POST",
    			dataType : 'json',
    			async : false,
    			cache : false,
    			success: function(data) {
                    $.each(data, function() {
                    	if(this.closerName != ''){
                    		closer = this.closerName;
                    		var temp = '<tr><td class="tdblueL">종결담당자</td><td colspan="3" class="tdwhiteL">'+this.closerName+'</td></tr>';
                    		$('[id=closeTbody]').html(temp);
                    		$('[name=closerName]').val(this.closerName);

                    	}
                    });
                }
    		
    		});
    	}else{
    		alert("프로젝트가 지정되지 않았습니다.");
    		hideProcessing();
    		return;
    	}
    	
    	if(closer == ''){
    		alert(pjtno+" 프로젝트의 선행품질보증 Role을 확인하세요.\n(전자사업부 : 선행품질보증 or 전자품질관리)");
    	}
    	hideProcessing();
    }
}