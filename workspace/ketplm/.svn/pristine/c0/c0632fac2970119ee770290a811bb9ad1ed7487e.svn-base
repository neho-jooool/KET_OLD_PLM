var TryCondition = {
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'TryConditionGrid',
			Sort : 'partNo',
//			perPageOnChange : 'javascript:Program.search(Value);',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
				{Name : 'dieNo', 	Width:90, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'partNo',	Width:100, Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'partName', RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'moldType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'tryNo', 	Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'subVer', 	Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'creator', 	Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'createdDate', 	Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'state', 	Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'moldMake', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],Header :{
				CanDelete : '0', CanEdit : '0', Align : 'Center',
				dieNo : 	LocaleUtil.getMessage('00144'),//'Die No',
				partNo : 	LocaleUtil.getMessage('00348'),//'Part No',
				partName : 	LocaleUtil.getMessage('00347'),//'Part Name',
				moldType : 	LocaleUtil.getMessage('01051'),//'금형구분',
				tryNo : 	'Try No',
				subVer : 	'Sub ver.',
				creator : 	LocaleUtil.getMessage('02431'),//'작성자',
				createdDate : 	LocaleUtil.getMessage('02428'),//'작성일',
				state : 	LocaleUtil.getMessage('01760'),//'상태',
				moldMake : 	LocaleUtil.getMessage('02532')//'제작구분'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = TryCondition.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/trycondition/findPagingList.do?sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength
			}
		}
	},
	
	/**
	 * try copy grid
	 */
	createTryCopyGrid : function(data){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'TryConditionGrid',
			Sort : '-tryNo',
			Body : data,
			useToolbar : false,
			Panel : true,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
			        {Name : 'tryNo', 	RelWidth : 10, Width : 80, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'subVer', 	Width : 80, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'creator', 	Width : 90, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'createdDate', Width : 90, 	Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'state', Width : 80, Align : 'Center', CanSort : '1', CanEdit : '0'}
			        ],Header :{
			        	CanDelete : '0', CanEdit : '0', Align : 'Center',
			        	tryNo : 	'Try No',
			        	subVer : 	'Sub ver.',
			        	creator : 	LocaleUtil.getMessage('02431'),//'작성자',
			        	createdDate : 	LocaleUtil.getMessage('04650'),//'작성일자',
			            state : 	LocaleUtil.getMessage('01760')//'상태',
			        }
		}),'listGrid');
		
		//row click event
		Grids.OnClick = TryCondition.goView;
	},
	
	/**
	 * try copy grid
	 */
	createTryConditionGrid : function(data){
		var grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'TryConditionGrid',
			Sort : '-tryNo',
			Body : data,
			useToolbar : false,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
				}
			},
			Cols : [
			        {Name : 'tryNo', 	Width : 60, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'subVer', 	Width : 70, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'creator', 	Width : 60, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'tryPlace', Width : 100, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'debugReason', RelWidth : '1', Width : 90, Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'createdDate', Width : 80, 	Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'modifyedDate', Width : 80, 	Align : 'Center', CanSort : '1', CanEdit : '0'},
			        {Name : 'state', Width : 50, Align : 'Center', CanSort : '1', CanEdit : '0'}
			        ],Header :{
			        	CanDelete : '0', CanEdit : '0', Align : 'Center',
			        	tryNo : 	'Try No',
			        	subVer : 	'Sub ver.',
			        	creator : 	LocaleUtil.getMessage('02431'),//'작성자',
			        	tryPlace : 	LocaleUtil.getMessage('00532'),//'Try 장소',
			        	debugReason : 	LocaleUtil.getMessage('03349'),//'디버깅 사유',
			        	createdDate : 	LocaleUtil.getMessage('04650'),//'작성일자',
			        	modifyedDate : 	LocaleUtil.getMessage('01951'),//'수정일자',
			            state : 	LocaleUtil.getMessage('01760')//'상태'
			        }
		}),'listGrid');
		
		//row click event
		Grids.OnClick = TryCondition.goView;
	},
	
	copyTryCondition : function(){
		var G = Grids[0];
		var selRows = G.GetSelRows();
		if(selRows.length > 0){
			TryCondition.goCreate(opener.$('[name=projectOutputOid]').val(),selRows[0].oid);
//			TryCondition.goCreate(opener.$('[name=projectOutputOid]').val(),'ext.ket.project.trycondition.entity.KETTryMold:100000757123');
			self.close();
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
		Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids[0].Source.Data.Url = '/plm/ext/project/trycondition/findPagingList.do?sortName=*Sort0';
		Grids[0].Source.Data.Params = decodeURIComponent($('[name=TryConditionSearchForm]').serialize());
		Grids[0].Source.Data.Param.formPage=perPage
		Grids[0].Reload();
		
	},
	
	goCreate : function(projectOutputOid,oid){
		var param = '?projectOutputOid='+projectOutputOid;
		if(oid){
			param += '&oid='+oid;
		}
		getOpenWindow2('/plm/ext/project/trycondition/createTryConditionForm.do'+param,'TryConditionCreatePopup',900,800);			
	},
	
	clear : function(){
		$('[name=TryConditionSearchForm]')[0].reset();
	},
	
	goUpdateTryPress : function(oid){
		location.href='/plm/ext/project/trycondition/updateTryPressForm.do?oid='+oid;			
	},
	
	goUpdateTryMold : function(oid){
		location.href='/plm/ext/project/trycondition/updateTryMoldForm.do?oid='+oid;			
	},
	
	goCopyTryConditionPopup : function(){
		getOpenWindow2('/plm/ext/project/trycondition/copyTryConditionPopup.do?projectOid='+$('[name=projectOid]').val(),'TryConditionCopyPopup',550,500);
	},
	
	tryPressPartWeightCheck : function(){
		
		var cnt = $("#partList tr").length;

		if(cnt < 1){
			alert('제품 중량정보 기입을 위한 원자재를 추가해야합니다.');
			return false;
		}
		var partNo = '';
		var msg = '';
		$("#partList .partRow").each(function(i){
			partNo = $("td[name=partNo]").eq(i).text(); 
			var scrabWeight = $("input[name=scrabWeight]").eq(i).val();
			var scrabCarrierWeight = $("input[name=scrabCarrierWeight]").eq(i).val();
			var netWeight = $("input[name=netWeight]").eq(i).val();
			if(scrabWeight == '' || scrabCarrierWeight == '' || netWeight == ''){
				msg += partNo+' 의 중량정보가 기입되지 않았습니다.\n\r';
			}
		});
		
		if(msg != ''){
			alert(msg);
			return false;
		}
		
		return true;
	},
	
	createTryPress : function(){
		//등록 정보 검증
		
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		if(!TryCondition.tryPressPartWeightCheck()){
			return;
		}
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
		var param = $('[name=TryConditionForm]').serializefiles();
		param.append("jsonData", JSON.stringify(TryCondition.getpartList()));
		
        $.ajax({
            type: 'post',
            url: '/plm/ext/project/trycondition/createTryPress.do',
            data: param,
            processData : false,
			contentType : false,
            success: function (data) {
            	if(data.success == 'OK'){
            		alert(LocaleUtil.getMessage('02460'));//'성공적으로 저장되었습니다.'            		
            		location.href='/plm/ext/project/trycondition/viewTryPressForm.do?oid='+data.value;            		            		
            	}else{
            		alert(LocaleUtil.getMessage('01796')+'\n'+data.value);//'생성 실패하였습니다.
            		//프로그레스바 숨기기
            		hideProcessing();
            	}
            },
            error : function(){
            	alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	createTryMold : function(){
		//등록 정보 검증
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		
		//프로그래스바 보이기
		showProcessing();
		
		//form data 전송
		$.ajax({
			type: 'post',
			url: '/plm/ext/project/trycondition/createTryMold.do',
			data: $('[name=TryConditionForm]').serializefiles(),
			processData : false,
			contentType : false,
			success: function (data) {
				if(data.success == 'OK'){
					alert(LocaleUtil.getMessage('02460'));//'성공적으로 저장되었습니다.'  		
					location.href='/plm/ext/project/trycondition/viewTryMoldForm.do?oid='+data.value;
				}else{
					alert(LocaleUtil.getMessage('01796')+'\n'+data.value);//'생성 실패하였습니다.
					//프로그레스바 숨기기
					hideProcessing();
				}
			},
			error : function(){
				alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
				//프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	updateTryPress : function(){
		//등록 정보 검증
		
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		if(!TryCondition.tryPressPartWeightCheck()){
			return;
		}
		var param = $('[name=TryConditionForm]').serializefiles();
		param.append("jsonData", JSON.stringify(TryCondition.getpartList()));
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
        $.ajax({
            type: 'post',
            url: '/plm/ext/project/trycondition/updateTryPress.do',
            data: param,
            processData : false,
			contentType : false,
            success: function (data) {
            	if(data.success == 'OK'){
            		alert(LocaleUtil.getMessage('02460'));//'성공적으로 저장되었습니다.'  		     		
            		location.href = '/plm/ext/project/trycondition/viewTryPressForm.do?oid='+data.value;
            	}else{
            		alert(LocaleUtil.getMessage('01796')+'\n'+data.value);//'생성 실패하였습니다.
            		//프로그레스바 숨기기
            		hideProcessing();
            	}
            },
            error : function(){
            	alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
            	//프로그레스바 숨기기
            	hideProcessing();
            }
        });
	},
	
	updateTryMold : function(){
		//등록 정보 검증
		if(!CommonUtil.checkEsseOk()){ 
			return;
		}
		
		//프로그래스바 보이기
		showProcessing();
		//form data 전송
		$.ajax({
			type: 'post',
			url: '/plm/ext/project/trycondition/updateTryMold.do',
			data: $('[name=TryConditionForm]').serializefiles(),
			processData : false,
			contentType : false,
			success: function (data) {
				if(data.success == 'OK'){
					alert(LocaleUtil.getMessage('02460'));//'성공적으로 저장되었습니다.'  		
					location.href = '/plm/ext/project/trycondition/viewTryMoldForm.do?oid='+data.value;
				}else{
					alert(LocaleUtil.getMessage('01796')+'\n'+data.value);//'생성 실패하였습니다.
					//프로그레스바 숨기기
					hideProcessing();
				}
			},
			error : function(){
				alert(LocaleUtil.getMessage('01796'));//'등록에 실패하였습니다.'
				//프로그레스바 숨기기
				hideProcessing();
			}
		});
	},
	
	deleteTryCondition : function(oid){
		if(confirm('삭제 하시겠습니까?')){
			//프로그래스바 보이기
			showProcessing();
			//form data 전송
			$.ajax({
				type: 'post',
				url: '/plm/ext/project/trycondition/deleteTryCondition.do',
				data: {
					oid : oid
				},
				success: function (data) {
					if(data.success == 'OK'){
						alert(LocaleUtil.getMessage('01692'));//'성공적으로 삭제 되었습니다.'            		
						TryCondition.close();  
					}else{
						alert(LocaleUtil.getMessage('09230')+'\n'+data.value);//삭제에 실패하였습니다
						//프로그레스바 숨기기
						hideProcessing();
					}
				},
				error : function(){
					alert(LocaleUtil.getMessage('09230'));//'삭제에 실패하였습니다.'
					//프로그레스바 숨기기
					hideProcessing();
				}
			});
		}
	},
	
	goView : function(grid,row,col,x,y){
		if(row.oid && col == 'dieNo'){
			openView(row.projectOid);
			//SearchUtil.openTryConditionListByDieNo(row.dieNo);
		}else if(row.oid && col == 'tryNo'){
			if(row.oid.indexOf('Press') > 0){
				getOpenWindow2('/plm/ext/project/trycondition/viewTryPressForm.do?oid='+row.oid,row.oid,900,800);				
			}else{
				getOpenWindow2('/plm/ext/project/trycondition/viewTryMoldForm.do?oid='+row.oid,row.oid,900,800);				
			}
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
	            //innerCell.style.border = "1px solid #b7d1e2";
	            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
	                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
	        } else if (k == 1) {
	            innerCell.className = "tdwhiteL0";
	            //innerCell.style.border = "1px solid #b7d1e2";
	            //innerCell.style.borderRight = "1px solid #b7d1e2";
	            //innerCell.style.borderTop="1px solid #b7d1c2";
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
	},
	
	//원재료 검색 팝업 호출
	selMaterial : function(moldType) {
	    var param = "";
	    var form = document.forms[0];
	    if(form.tempmaterial.value != ""){
	        //param = "materialOid=" + form.tempmaterial.value;
	    }
	    param += "moldType=" + moldType;
	    
	    param = param + "&itemTypeCheck=true&isTry=true";
	    var url = "/plm/jsp/project/material/SelectMaterialPopup.jsp?" + param;

	    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=450px; dialogHeight:250px; center:yes");
	    if(typeof attache == "undefined" || attache == null) {
	        return;
	    }
	    TryCondition.setMaterial(attache, moldType);
	},

	setMaterial : function(objArr, moldType) {
	    if(objArr.length == 0) {
	        return;
	    }
	    var trArr = objArr[0];
	    var form = document.forms[0];

	    form.material.value = trArr[0];
	    if(moldType  == 'Press'){
	    	form.material.value = trArr[0];
		    form.tempmaterial.value = trArr[1];
	    	//form.pOid.value = trArr[2];
	    	form.thickness.value = trArr[3] + 't X ' + trArr[4] + 'w';
	    	//form.wide.value = trArr[4];
	    	form.plating.value = trArr[6];
	    }else{
	    	form.tempmaterial.value = trArr[3];
	    	form.grade.value = trArr[4];
	    	form.color.value = trArr[2];
	    	//form.plating.value = trArr[5];	    	
	    }

	},
	
	setEnableEtcField : function(selectField, enableField){
		if($('[name='+selectField+'] option:selected').text().indexOf('기타') >= 0){
			$('[name='+enableField+']').attr('disabled',false);
		}else{
			$('[name='+enableField+']').attr('disabled',true);
		}
	},

	reviewPrint : function(id){
		window.print();
		 //$.printPreview.loadPrintPreview();
	},
	
	exportTryCondition : function(oid){
		showProcessing();
		$.fileDownload('/plm/ext/project/trycondition/excelExportTryCondition.do?oid='+oid,{
			 successCallback : function(){
				 hideProcessing();	 
			 },
			 failCallback: function(responseHtml, url) {
				 hideProcessing();
			 }
		});
	},
	
	close : function(){
		try{
			if(opener.location.toString().indexOf('TaskView.jsp') > 0){
				opener.showProcessing();
				opener.location.href='/plm/jsp/project/TaskView.jsp?oid='+opener.$('[name=taskOid]').val();
			}
			self.close();				
		}catch(exception){self.close();}
	},
	
	calculateValue : function(value1, value2, value3, calc, fixed){
	    
	    if(value1 == "") value1 = 0;
	    if(value2 == "") value2 = 0;
	    if(value3 == "") value3 = 0;

	    value1 = parseFloat(value1).toFloat(fixed);
	    value2 = parseFloat(value2).toFloat(fixed);
	    value3 = parseFloat(value3).toFloat(fixed);
	    
	    var text = "(" + value1 + ")" + calc + "(" + value2 + ")" + calc + "(" + value3 + ")";
	    
	    return eval(text).toFloat(fixed).toString();
	},
	
	addPartList : function(){
	    
	    showProcessing();
	    SearchUtil.selectPart('TryCondition.addPartAfterFunc','pType=R'); 

	},
	
	addPartAfterFunc :  function( rtnArr )
	{
	    for(var i = 0; i < rtnArr.length ; i++){
	        var arr = new Array();
	        arr = rtnArr[i];
	        TryCondition.addPart(arr);
	    }   
	},
	
	addPart : function(arr){
	    var paramOid = arr[8];
	    
	    var rtnFlag = false;
	    
	    $.each($('[name=partOid]'), function(i, val){
	        if(val.value == paramOid) {
	            alert(LocaleUtil.getMessage('09104',arr[1]));//"선택한 부품은 이미 추가된 부품입니다."
	            rtnFlag = true;
	            return;
	        }
	    });
	    
	    if(rtnFlag){
	        return;
	    }
	    
	    /**
	     * 부품 검색
	     * 
	     * type(부품분류) : 
	        'A' 모두 
	        ,'P' 제품
	        ,'D' DIE NO 선택
	        ,'M' 금형부품
	     * 
	     * fncall :
	     *  후처리 함수명(objectArray 로 리턴함)
	         subArr[0] = "wt.part.WTPart:" + R[i].Oid;//oid
	         subArr[1] = R[i].PartNumber;//number
	         subArr[2] = R[i].PartName;//name
	         subArr[3] = R[i].PartVersion;//version
	         subArr[4] = R[i].PartType;//type
	         subArr[5] = R[i].DieNo;//dieno
	         subArr[6] = R[i].DieName;
	         subArr[7] = R[i].DieCnt;
	         subArr[8] = R[i].OidMaster;//wtpartmaster oid
	     */
	    var addRow = "<tr class='partRow'>";
	   
	    /*if(isrelatedPart){
	    	addRow += "<td class='td center'><input type='hidden' name='partOid' value='"+arr[8]+"'/><input type='hidden' name='relatedPart' value='"+isrelatedPart+"'/></td>";
	    }else{
	    	addRow += "<td class='td center'><input type='checkbox' name='selectPartDel' /><input type='hidden' name='partOid' value='"+arr[8]+"'/><input type='hidden' name='relatedPart' value='false'/></td>";
	    }*/
	    addRow += "<td class='td center'><input type='checkbox' name='selectPartDel' /><input type='hidden' name='partOid' value='"+arr[8]+"'/></td>";
	    addRow += "<td class='td top' name='partNo'>"+arr[1]+"</td>";
	    addRow += "<td class='td top'>"+arr[2]+"</td>";
	    addRow += "<td class='td top'><input type='text' name='totalWeight' id='totalWeight' class='txt_fieldRO' style='width: 80%' readonly value=''> g</td>";
	    addRow += "<td class='td top'><input type='text' name='scrabWeight' id='scrabWeight' style='width:80%' value=''> g</td>";
	    addRow += "<td class='td top'><input type='text' name='scrabCarrierWeight' id='scrabCarrierWeight' style='width:80%' value=''> g</td></td>";
	    addRow += "<td class='td top'><input type='text' name='netWeight' id='netWeight' style='width:80%' value=''> g</td></td>";
	    addRow += "</tr>";
	    
	    var tabLength = $('#partList tbody tr').length;
	    
	    $("#partList").append(addRow);
	        
	    $("[id$='netWeight']").unbind("keyup");
	    $("[id$='scrabCarrierWeight']").unbind("keyup");
	    $("[id$='scrabWeight']").unbind("keyup");
	    
	    $("input[name='netWeight']").each(function(i){
	    
		    $(this).keyup(function(){
		    	
		    	TryCondition.totalWeightSet(i);
		    });
		    
		    $("input[name=scrabWeight]").eq(i).keyup(function(){
		    	
		    	TryCondition.totalWeightSet(i);
	        });
	        
		    $("input[name=scrabCarrierWeight]").eq(i).keyup(function(){
		    	
		    	TryCondition.totalWeightSet(i);
		    });
	    
	    }); 
	    
	    TryCondition.numberSet('totalWeight');
	    TryCondition.numberSet('scrabWeight');
	    TryCondition.numberSet('scrabCarrierWeight');
	    TryCondition.numberSet('netWeight');
	},

	totalWeightSet : function(i){
		var totalWeight = TryCondition.calculateValue($("input[name=netWeight]").eq(i).val() ,$("input[name=scrabWeight]").eq(i).val(),$("input[name=scrabCarrierWeight]").eq(i).val(),"+");
	    $("input[name=totalWeight]").eq(i).val(totalWeight);
	},

	numberSet : function(attr){
	 	$.each($("[name="+attr+"]"), function(i, val){
	        $(this).number(true,3);
	    });
	},

	delPartList : function(){
	    $("input[name='selectPartDel']").each(function(){
	        var isChecked = $(this).is(":checked");
	        if(isChecked){
	            $(this).parents(".partRow:first").remove();
	        }
	    });
	},
	
	getpartList : function(){
		var partList = new Array();
		$("#partList .partRow").each(function(i){
					    
			var row = new Object();
			
			row.partOid = $("input[name=partOid]").eq(i).val();
			row.netWeight = $("input[name=netWeight]").eq(i).val();
			row.scrabWeight = $("input[name=scrabWeight]").eq(i).val();
			row.scrabCarrierWeight = $("input[name=scrabCarrierWeight]").eq(i).val();
			row.totalWeight = $("input[name=totalWeight]").eq(i).val();
			row.relatedPart = $("input[name=relatedPart]").eq(i).val();
			partList.push(row);
		});
		
		return partList;
	},
}
$(document).ready(function(){
	//숫자만 입력('.','-' 포함)
	CommonUtil.setNumberField('screwDiameter',true);
	CommonUtil.setNumberField('oilTemp',true);
	CommonUtil.setNumberField('tone',true);
	CommonUtil.setNumberField('cylinderTempNH',true);
	CommonUtil.setNumberField('cylinderTempN1',true);
	CommonUtil.setNumberField('cylinderTempN2',true);
	CommonUtil.setNumberField('cylinderTempN3',true);
	CommonUtil.setNumberField('cylinderTempN4',true);
	CommonUtil.setNumberField('coolWaterTempHigh',true);
	CommonUtil.setNumberField('coolWaterTempLow',true);
	CommonUtil.setNumberField('holdPress1',true);
	CommonUtil.setNumberField('holdPress2',true);
	CommonUtil.setNumberField('holdPress3',true);
	CommonUtil.setNumberField('holdPressSpeed1',true);
	CommonUtil.setNumberField('holdPressSpeed2',true);
	CommonUtil.setNumberField('holdPressSpeed3',true);
	CommonUtil.setNumberField('holdPressTime',true);
	CommonUtil.setNumberField('holdPressTurnPoint',true);
	CommonUtil.setNumberField('injectPress1',true);
	CommonUtil.setNumberField('injectPress2',true);
	CommonUtil.setNumberField('injectPress3',true);
	CommonUtil.setNumberField('injectPress4',true);
	CommonUtil.setNumberField('injectPress5',true);
	CommonUtil.setNumberField('injectSpeed1',true);
	CommonUtil.setNumberField('injectSpeed2',true);
	CommonUtil.setNumberField('injectSpeed3',true);
	CommonUtil.setNumberField('injectSpeed4',true);
	CommonUtil.setNumberField('injectSpeed5',true);
	CommonUtil.setNumberField('lowPressShape',true);
	CommonUtil.setNumberField('mensuration',true);
	CommonUtil.setNumberField('mensurationDist',true);
	CommonUtil.setNumberField('mensurationTime',true);
	CommonUtil.setNumberField('moistureContent',true);
	CommonUtil.setNumberField('moldBaseSizeHeigh',true);
	CommonUtil.setNumberField('moldBaseSizeLength',true);
	CommonUtil.setNumberField('moldBaseSizeWidth',true);
	CommonUtil.setNumberField('moldCloseTime',true);
	CommonUtil.setNumberField('moldOpenDist1',true);
	CommonUtil.setNumberField('moldOpenDist2',true);
	CommonUtil.setNumberField('moldOpenDist3',true);
	CommonUtil.setNumberField('moldOpenSpeed1',true);
	CommonUtil.setNumberField('moldOpenSpeed2',true);
	CommonUtil.setNumberField('moldOpenSpeed3',true);
	CommonUtil.setNumberField('movingSideTemp',true);
	CommonUtil.setNumberField('oilTemp',true);
	CommonUtil.setNumberField('pressCount',true);
	CommonUtil.setNumberField('pressTime',true);
	CommonUtil.setNumberField('pressures',true);
	CommonUtil.setNumberField('screwDiameter',true);
	CommonUtil.setNumberField('shortTransition1',true);
	CommonUtil.setNumberField('shortTransition2',true);
	CommonUtil.setNumberField('shortTransition3',true);
	CommonUtil.setNumberField('shortTransition4',true);
	CommonUtil.setNumberField('shortTransition5',true);
	CommonUtil.setNumberField('shotCount',true);
	CommonUtil.setNumberField('shotWeight',true);
	CommonUtil.setNumberField('speed',true);
	CommonUtil.setNumberField('spillResistantSpeed',true);
	CommonUtil.setNumberField('spillResistentCfg',true);
	CommonUtil.setNumberField('sprue',true);
	CommonUtil.setNumberField('stroke',true);
	CommonUtil.setNumberField('tone',true);
	CommonUtil.setNumberField('weight',true);
	
	Number.prototype.toFloat = function(fixed){
        
        if(fixed == null) fixed = 4;
        
        var result = this + "";
        
        if(result.indexOf(".") != -1){

            result = parseFloat(result).toFixed(fixed);

            result = parseFloat(result.replace(/(0+$)/, ""));
        }
        
        return result;
    }
	
	$("#selectPartAll").change(function(){

        var isAllChecked = $("#selectPartAll").is(":checked");
        
        $("input[name=selectPartDel]").prop("checked",isAllChecked);

    });
	
	
});