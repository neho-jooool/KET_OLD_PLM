var Arm = {
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'ArmSearchGrid',
			Sync : 1,
			perPageOnChange : 'javascript:Arm.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['ArmSearchGrid'])?Grids['ArmSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-analysisRequestNo',
			Cols : [
                {Name : 'rowNum', 	Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'},
				{Name : 'analysisRequestNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'projectNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'analysisUseName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'analysisDivisionName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'analysisTitle',	Width:100,RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'startDate', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ketClientDepartmentName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},				
				{Name : 'ketClientUserName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'approvalUserName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'endDate', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'stateName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				rowNum : 'No',
				analysisRequestNo : '의뢰번호', // 의뢰번호
				projectNo : 'Project No', // projectNo
				analysisUseName : '용도', // 해석용도
				analysisDivisionName : '구분', // 구분				
				analysisTitle : LocaleUtil.getMessage('02524'), // 제목
				startDate : '의뢰일',// 의뢰일
				ketClientDepartmentName : '의뢰부서', // 의뢰부서
				ketClientUserName : '의뢰자', // 의뢰자
				approvalUserName : '담당자', // 담당자
				endDate : '완료일', // 완료일
				stateName : '진행상태' // 진행상태
			}/*,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}*/
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Arm.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/arm/master/findPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
	},
	
	createPaingPopupGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'ArmSearchGrid',
			Sync : 1,
			perPageOnChange : 'javascript:Arm.searchPopup(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['ArmSearchGrid'])?Grids['ArmSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			Sort : '-analysisRequestNo',
			Cols : [
				{Name : 'analysisRequestNo', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'analysisUseName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'analysisTitle',	Width:100,RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'startDate', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ketClientUserName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ketClientUserName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'ketClientUserName', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'endDate', 	Width:100,  Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'stateName', Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				analysisRequestNo : '의뢰번호', // 의뢰번호
				analysisUseName : '용도', // 해석구분
				analysisTitle : LocaleUtil.getMessage('02524'), // 제목
				startDate : '의뢰일',// 의뢰일
				ketClientUserName : '의뢰자', // 의뢰자
				ketClientUserName : '의뢰자', // 의뢰자
				ketClientUserName : '의뢰자', // 의뢰자
				endDate : '완료일', // 완료일
				stateName : '진행상태' // 진행상태
			},
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = Arm.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/arm/master/findPopUpPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				name : $('[name=name]').val(),
				title : $('[name=title]').val()
			}
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['ArmSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['ArmSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['ArmSearchGrid'].Source.Data.Url = '/plm/ext/arm/master/findPagingList.do?sortName=*Sort0';
		Grids['ArmSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=ArmSearchForm]').serialize());
		Grids['ArmSearchGrid'].Source.Data.Param.formPage=perPage;
		Grids['ArmSearchGrid'].Reload();
	},
	
	searchPopup : function(perPage){
		if(!perPage) perPage = Grids['ArmSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['ArmSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['ArmSearchGrid'].Source.Data.Url = '/plm/ext/arm/master/findPopUpPagingList.do?sortName=*Sort0';
		Grids['ArmSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=ArmSearchForm]').serialize());
		Grids['ArmSearchGrid'].Source.Data.Param.formPage=perPage;
		Grids['ArmSearchGrid'].Reload();
	},
	
	clear : function(){
		$('[name=ArmSearchForm]')[0].reset();
	},
	
	create : function(){
		var partNo = "";
		for(var i=0; i < $("input[name=pOid]").length; i++){
			if(i > 0){
				partNo += ",";
			}
			partNo += $("input[name=pOid]").eq(i).val();
		}
		$("#partNo").val(partNo);
		if(Arm.checkValidate()){
			$('[name=customerDepartment] option').prop('selected', true);
			$('[name=ArmCreateForm]').attr('action', '/plm/ext/arm/master/armMasterCreate.do');
			$('[name=ArmCreateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
			$('[name=ArmCreateForm]').submit();
			showProcessing();
		}
	},
	
	createInfo : function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		$('[name=ArmInfoForm]').attr('action', '/plm/ext/arm/info/armInfoCreate.do');
		$('[name=ArmInfoForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=ArmInfoForm]').submit();
		showProcessing();
	},
	
	update : function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		var partNo = "";
		for(var i=0; i < $("input[name=pOid]").length; i++){
			if(i > 0){
				partNo += ",";
			}
			partNo += $("input[name=pOid]").eq(i).val();
		}
		$("#partNo").val(partNo);
		if(Arm.checkValidate()){
			$('[name=customerDepartment] option').prop('selected', true);
			$('[name=ArmUpdateForm]').attr('action', '/plm/ext/arm/master/armMasterUpdate.do');
			$('[name=ArmUpdateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
			$('[name=ArmUpdateForm]').submit();
			showProcessing();
		}
	},
	
	updateInfo : function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		$('[name=ArmInfoForm]').attr('action', '/plm/ext/arm/info/armInfoUpdate.do');
		$('[name=ArmInfoForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=ArmInfoForm]').submit();
		showProcessing();
	},
	
	remove : function(){
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/arm/master/masterDelete.do',
            data: $('[name=ArmViewForm]').serialize(),
            success: function (data) {
                alert(LocaleUtil.getMessage('01692'));//'삭제 되었습니다.'
                opener.Arm.search();
                window.close();
            },
            error : function(){
            	alert(LocaleUtil.getMessage('09230'));//'삭제 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	removeInfo : function(){
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/arm/info/infoDelete.do',
            data: $('[name=ArmInfoForm]').serialize(),
            success: function (data) {
                alert(LocaleUtil.getMessage('01692'));//'삭제 되었습니다.'
                opener.Arm.search();
                window.close();
            },
            error : function(){
            	alert(LocaleUtil.getMessage('09230'));//'삭제 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	goList : function(){
		location.href = '/plm/ext/arm/master/armList.do';
	},
	
	goCreate : function(){
		getOpenWindow2('/plm/ext/arm/master/armMasterCreateForm.do','ArmCreatePopup',800,600);
	},
	
	goUpdate : function(){
		var url = '/plm/ext/arm/master/armMasterUpdateForm.do?oid='+$('[name=oid]').val();
		location.href = url;
	},

	goInfoUpdate : function(){
		var url = '/plm/ext/arm/info/armInfoUpdateForm.do?oid='+$('[name=oid]').val();
		location.href = url;
	},	
	
	goView : function(grid,row,col,x,y){
		if(row.infoStartFlag){
			if(row.oid && col == 'stateName'){
				if(row.infoProgressFlag){
				}else{
					getOpenWindow2('/plm/ext/arm/info/armInfoCreateForm.do?oid='+row.oid,'ArmCreateInfoPopup',800,600);
				}
			}else if(row.oid && col == 'analysisRequestNo' || row.oid && col == 'analysisTitle'){
				if(row.infoProgressFlag == true){
					getOpenWindow2('/plm/ext/arm/info/armInfoViewForm.do?oid='+row.oid,'ArmInfoViewPopup',800,600);
				}else{
					getOpenWindow2('/plm/ext/arm/master/armMasterViewForm.do?oid='+row.oid,'ArmMasterInfoPopup',800,600);
				}
			}
		}else{
			if(row.oid && col == 'analysisRequestNo'){
				getOpenWindow2('/plm/ext/arm/master/armMasterViewForm.do?oid='+row.oid,'ArmMasterViewPopup',800,600);
			}else if(row.oid && col == 'analysisTitle'){
				getOpenWindow2('/plm/ext/arm/master/armMasterViewForm.do?oid='+row.oid,'ArmMasterViewPopup',800,600);
			}
		}
	},
	goResultCreate : function(){
		getOpenWindow2('/plm/jsp/dms/CreateDocument.jsp','DocumentCreatePopup',800,600);
	},
	
	/**
	 * 파일 추가
	 */
	insertFileLine : function() {
	    //첨부파일 라인을 추가한다.
	    var tBody = document.getElementById("iFileTable");
	    var innerRow = tBody.insertRow();
	    var innerCell = innerRow.insertCell();
	    var filehtml = "";
	    filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
	    filehtml += "  <input name='secondaryFiles' type='file' class='txt_fieldRO' style='100%'>";
	    innerCell.innerHTML = filehtml;
	},
	
	/**
	 * 파일 삭제
	 */
	deleteFileLine : function() {
	    $('[name=iFileChk]').each(function(){
	        if($(this).is(':checked')){
	        	$(this).parent().remove();
	        }
	    });   
	},
	
	checkValidate : function(){
		if($("#analysisUse").val() == null){
			alert("용도를 선택하세요.");
			return false;
		}
		if($("#analysisDivision").val() == null){
			alert("구분를 선택하세요.");
			return false;
		}
		if($("[name=analysisTitle]").val() == "" || $("[name=analysisTitle]").val() == null){
			alert("의뢰명을 입력하세요.");
			return false;
		}
//		if($("#partNo").val() == "" || $("#partNo").val() == null){
//			alert("의뢰부품을 등록하세요.");
//			return false;
//		}
		return true;
	},
	
	selectArm : function(){
		try{
			var G = Grids[0];
			var arr = new Array();
			var subArr = new Array();

		    if( G != null ){
		    	
		        var R = G.GetSelRows();
	
		        if( R.length == 0 ){
		            alert('의뢰완료 문서를 선택하세요.');
		            return;
		        }
		        for ( var i=0; i<R.length; i++ ) {
		             subArr = new Array();
		             subArr[0] = R[i].oid; //oid
		             subArr[1] = R[i].analysisRequestNo;

		             arr.push(subArr);
		        }
		        window.returnValue= arr;
		        window.close();
		    }
		}catch(e){
			alert(e.message);		
		}
	}
}