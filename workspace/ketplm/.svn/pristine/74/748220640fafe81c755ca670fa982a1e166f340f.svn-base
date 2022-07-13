var purchase = {
		
		
		createPaingGrid : function(){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'purchaseSaveGrid',
				Sort : '-pjtNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:purchase.search(Value);',
				usePaging : false,
				Sync : 1,
				Data : {
					Url : '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['purchaseSaveGrid'])?Grids['purchaseSaveGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				LeftCols : [
				            	{Name : 'oid',            Width:0,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				            	{Name : 'editAuth',     Width:0,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				            	{Name : 'deleteGb', 	  Width:20,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				            	{Name : 'partNo', 	  Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
				            	{Name : 'partName', 	  Width:200,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1',Type : 'Text'},
	                          ],
				Cols : [  
						{Name : 'outSourcing', Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'outSourcingGubun', 	Width:60,  Align : 'Left', CanSort : '0' ,Spanned:'1', Type : "Enum", Enum : "|기존|신규", Range : 0},
						{Name : 'managerName', 	Width:60,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'managerOid', Visible:'0', 	Width:0,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'pjtNo', 	Width:60,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'devOrderStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'devOrderEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'imspStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'imspEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'exaAgreeStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'exaAgreeEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'moldmkStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'moldmkEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'moldTryStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'moldTryEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'imsizeStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'imsizeEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'trustStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'trustEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'p1StartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'p1EndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'allSizeStartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'allSizeEndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapStartDate1', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapEndDate1', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapStartDate2', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapEndDate2', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapStartDate3', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'ppapEndDate3', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'p2StartDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						{Name : 'p2EndDate', 	Width:95,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
						],
						RightCols : [
			             	{Name : 'bigo', 	Width:180,  Align : 'Left', CanSort : '0', CanEdit : '1' ,Spanned:'1'},
			             ],
			    
						Head :[
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id : "Header",Spanned : '1',
									deleteGb : '' , deleteGbRowSpan : '3', deleteGbClass : 'border-top-red',
									partNo : "품번", partNoRowSpan:'3',
									partName : "품명", partNameRowSpan:'3',
									outSourcing : "협력사(제조사)", outSourcingSpan:'2', outSourcingRowSpan:'2', 
									managerName : "담당자", managerNameRowSpan: '3', 
									pjtNo: "PJT No", pjtNoRowSpan : '3',
									devOrderStartDate : "개발발주", devOrderStartDateSpan : '2', devOrderStartDateRowSpan : '2',
									imspStartDate : "수입검사기준서", imspStartDateSpan : '2', imspStartDateRowSpan : '2',
									exaAgreeStartDate : "검사협정서", exaAgreeStartDateSpan : '2', exaAgreeStartDateRowSpan : '2',
									moldmkStartDate : "금형제작", moldmkStartDateSpan : '4',
									imsizeStartDate : "중요치수", imsizeStartDateSpan : '2', imsizeStartDateRowSpan : '2',
									trustStartDate : "신뢰성", trustStartDateSpan : '2', trustStartDateRowSpan : '2',
									p1StartDate : "P1입고", p1StartDateSpan : '2', p1StartDateRowSpan : '2',
									allSizeStartDate : "전치수", allSizeStartDateSpan : '2', allSizeStartDateRowSpan : '2',
									ppapStartDate1 : "PPAP", ppapStartDate1Span : '6',
									p2StartDate : "P2" , p2StartDateSpan : '2', p2StartDateRowSpan : '2',
									bigo: "비고", bigoRowSpan : '3',
								},
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',Spanned : '1',
									id:"HeaderTop", 
									moldmkStartDate : "제작완료", moldmkStartDateSpan : "2",
									moldTryStartDate : "Try", moldTryStartDateSpan : "2",
									ppapStartDate1 : "서류제출", ppapStartDate1Span : '2',
									ppapStartDate2 : "공정감사", ppapStartDate2Span : '2',
									ppapStartDate3 : "승인", ppapStartDate3Span : '2',

								},
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',Spanned : '1',
									id:"HeaderTop2", 
									outSourcing : "협럭사", 
									outSourcingGubun : "구분", 
									devOrderStartDate : "계획", 
									devOrderEndDate : "실행", 
									imspStartDate : "계획", 
									imspEndDate : "실행", 
									exaAgreeStartDate : "계획", 
									exaAgreeEndDate : "실행", 
									moldmkStartDate : "계획",  
									moldmkEndDate : "실행", 
									moldTryStartDate : "계획", 
									moldTryEndDate : "실행", 
									imsizeStartDate : "계획", 
									imsizeEndDate : "실행",   
									trustStartDate : "계획", 
									trustEndDate : "실행",  
									p1StartDate : "계획",  
									p1EndDate : "실행", 
									allSizeStartDate : "계획",  
									allSizeEndDate : "실행", 
									ppapStartDate1 : "계획",  
									ppapEndDate1 : "실행",  
									ppapStartDate2 : "계획", 
									ppapEndDate2 : "실행",  
									ppapStartDate3 : "계획", 
									ppapEndDate3 : "실행",  
									p2StartDate : "계획",  
									p2EndDate : "실행",  
								},
								]
/*					,
					SelectingSingle : '1',
					Panel : {
						Width : '21', Visible : '1',CanHide : '0'
					}*/
				}),'listGrid');
			
			Grids.OnRenderPageFinish = function(){
				
				var rowCount = 0;
	    		var G = Grids[0];
	    		
	    		var rows = G.Rows;
	            var rowIds = Object.keys(rows);
	            var colNames = G.ColNames[1];
	            
	            colNames.push('partNo');
	            colNames.push('partName');
	            colNames.push('bigo');
	            colNames.push('deleteGb');
	            
	            for(var i = 0; i < rowIds.length; i++){
	            	var row = rows[rowIds[i]];
	            	if(row.Kind == 'Header'){
	            		
	            		purchase.gridHeaderFontSetting(colNames,G,row);
	            		rowCount++;
	            	}else{
	            		
	            		rowCount++;
	            		
	            		purchase.gridHeaderFontSetting(colNames,G,row);
	            		
	            		if(rowCount > 2){
	            			break;
	            		}
	            		
	            	}
	            	
	            }
	    	}
			
			Grids.OnRenderFinish = function(){
			}
			
			Grids.OnClick = purchase.goView;
			
			Grids.OnAfterValueChanged = function(grid, row, col, val){
				
			}
			
		},
		
		gridHeaderFontSetting : function(colNames,G,row){
			for(var j = 0; j < colNames.length; j++){
    			purchase.gridHeaderFontSizeChange(G, row, colNames[j], 2);
        	}
			
		},
		
		gridHeaderFontSizeChange : function(G, tRow, attr, size){
			
			G.SetAttribute(tRow, attr, "Background", "#E2EDF4", 1);
			G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);
            G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
            G.SetAttribute(tRow, attr, "Changed", 0, 1);
		},
		
		goView : function(grid,row,col,x,y){
			if(row.Kind == 'Data'){
				
				if(col == 'partNo'){
					openViewPart(row.partNo);
				}else if(col == 'pjtNo'){
					openProject(row.pjtNo);
				}
			}
		},
		
		trimPjtNo : function(){
			var pjtNo = $('#pjtNo').val();
			
			
			
			$("#pjtNo").val((pjtNo.replace(/ /g,"")).toUpperCase());
			
			
		},
		
		
		search : function(perPage){
			
			if(!perPage) perPage = Grids['purchaseSaveGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['purchaseSaveGrid'].Source.Data.Url = '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0';
			Grids['purchaseSaveGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['purchaseSaveGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['purchaseSaveGrid'].Source.Data.Param.formPage=perPage;
			Grids['purchaseSaveGrid'].Reload();
			//Grids['purchaseSaveGrid'].ReloadBody();
			
//			Grids[0].Reload();
		},
		
		
		openSaveListView : function(){
			var url = "/plm/ext/project/purchase/purchaseProjectUpdateList.do?pjtNo="+$('#pjtNo').val()
		    var name = "";
		    defaultWidth = 1024;
		    defaultHeight = 768;
		    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
		},
		
		addMember :  function(row) {
			var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
		    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
		      return;
		    }
		    
		    var values = "";
		    var names = "";
		    
		    for(var i = 0; i < attacheMembers.length; i++) {
		    	
		        if(i != 0){
		        	values += ",";
		        	names += ",";
		        }
		    	
		        values += attacheMembers[i][0];
		        names += attacheMembers[i][4];
		        
				//wtuser oid
				//people oid
				//dept oid
				//uid
				//name
				//dept name
				//duty
				//duty code
				//email
		    }
		    
		    Grids[0].SetValue(row, "managerName", names, 1);
		    Grids[0].SetValue(row, "managerOid", values, 1);
		},
		
		deletePart : function(row, deleteAuth){
			
			var partNo = Grids[0].GetString(row, 'partNo');
			var managerName = Grids[0].GetString(row, 'managerName');
			
			if(!deleteAuth){
				alert(partNo + "은 담당자 : [ " + managerName + " ]  에게 삭제권한이 있습니다.");
				return;
			}
			
			if(confirm("정말로 품번 "+partNo + " 을 삭제하시겠습니까?\n삭제된 데이터는 복구할 수 없습니다.")){
				
				setTimeout( function() { purchase.exeuteDelete(row); }, 10);	
				
			}
		},
		
		exeuteDelete : function(row) {
			
			if(purchase.isChangedCell()){
				alert('변경된 데이터가 있습니다.저장 후 실행하세요.');
				return;
			}
			
			var param = {};
			param.oid = Grids[0].GetString(row, 'oid');
			ajaxCallServer("/plm/ext/project/purchase/deletePurchasePart", param, function(data){				
				//Grids[0].ReloadBody();
				Grids[0].Reload();
		    });
		},
		
		isChangedCell : function(){
			var grid = Grids[0];

	        var colNames = grid.ColNames[1];

	        var changed = false;	        
	        outer : for (var tRow = grid.GetFirstVisible(); tRow; tRow = grid.GetNextVisible(tRow) ) {
	        	
	        	inner : for(var j = 0; j < colNames.length; j++){
	        		
	        		changed = tRow[colNames[j]+'Changed'] != null && typeof tRow[colNames[j]+'Changed']  != "undefined";
	        		if(changed){
	        			break outer;
	        		}
	        		
	        	}
	        }
	        return changed;
		},

		
		goSave : function(){	
	        var grid = Grids[0];

	        var colNames = grid.ColNames[1];
	        colNames.push('partNo');
	        var paramArr = new Array();
   
	        for (var tRow = grid.GetFirstVisible(); tRow; tRow = grid.GetNextVisible(tRow) ) {
	        	var paramObj = {};
	        	for(var j = 0; j < colNames.length; j++){
	        		var value = grid.GetString(tRow, colNames[j]);
            		paramObj[colNames[j]] = value;
            		
            		//console.log(colNames[j]+'Changed' + "  : " + tRow[colNames[j]+'Changed']);
	        	}
	        	
	        	paramObj.oid = grid.GetString(tRow, 'oid');
	        	paramObj.bigo = grid.GetString(tRow, 'bigo');
	        	paramObj.editAuth = grid.GetString(tRow, 'editAuth');
	        	paramArr.push(paramObj);
	        }

	        if(paramArr.length < 1){
	        	alert("수정대상 데이터가 조회되지 않았습니다.");
	        	return;
	        }
	        
	        var gridParam = {};
	        gridParam.treeData = paramArr;
	        
	        var gridParam = new Object();
	        gridParam.jsonData = JSON.stringify(paramArr);
	        
	        ajaxCallServer("/plm/ext/project/purchase/savePurchasePart", gridParam, function(data){				
				grid.Reload();
	        	//grid.ReloadBody();
		    });
		},
	
		gridSave : function(){
		
			$('#pjtNo').focus();
			//$('#pjtNo').blur();
			if(confirm("저장하시겠습니까?")){
				setTimeout( function() { purchase.goSave(); }, 10);
			}
		},
		
		addPart : function(){
			if(purchase.isChangedCell()){
				alert('변경된 데이터가 있습니다.저장 후 실행하세요.');
				return;
			}
			
			var pjtNo = $('#pjtNo').val();
			if(pjtNo == ''){
				alert('대상 프로젝트를 입력하십시오.');
				$('#pjtNo').focus();
				return;
			}
			if(pjtNo.split(',').length > 1){
				alert('한개의 프로젝트 기준으로 일괄등록이 가능합니다.');
				$('#pjtNo').focus();
				return;
			}
			purchase.trimPjtNo();
			purchase.search();
			getOpenWindow2("/plm/ext/project/purchase/purchasePartAddForm.do?pjtNo="+pjtNo, 'purchasePartAdd' , 800, 720);
		}
}