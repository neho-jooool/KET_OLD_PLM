var purchaseAdd = {
		
		
		createPaingGrid : function(){
			
			var config = {
					id : 'addPartSearchGrid',
					Data : {
					Url : '/plm/ext/project/purchase/addPartList.do',
					Method : 'POST',
					Format : 'Form',
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
					},
					usePaging : false,
					useToolbar : false,//grid toolbar hide
					Cols : [
					        {Name : 'partNo', Width:80,  Align : 'Left', CanSort : '0', CanEdit : '0'},
							{Name : 'partName', 	Width:230,  Align : 'Left', CanSort : '0' , CanEdit : '0'},
							{Name : 'partType', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'itemType', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'dieNo', 	Width:80,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'outSourcing', 	Width:150,  Align : 'Left', CanSort : '0', CanEdit : '0'},
					],Header :{
						CanDelete : '0', Align : 'Center',
						partNo : "Part No",
						partName : "Part Name",  
						partType : "부품분류", 
						itemType : "분류",
						dieNo: "DIE No",
						outSourcing : "생산처"
					},
					SelectingSingle : '0',
					Panel : {
						Width : '21', Visible : '1',CanHide : '0'
					}
				};
			this.grid = TreeGrid(CommonGrid.getGridConfig(config),'listGrid');
			//row click event
			Grids.OnClick = purchaseAdd.goView;
			
			Grids.OnRenderPageFinish = function(){
				
	    		var G = Grids[0];
	    		
	    		var rows = G.Rows;
	            var rowIds = Object.keys(rows);
	            var colNames = G.ColNames[1];
	            
	            colNames.push('Panel');
	            for(var i = 0; i < rowIds.length; i++){
	            	var row = rows[rowIds[i]];
	            	
	            	if(row.Kind == 'Header'){
	            		
	            		purchaseAdd.gridHeaderFontSetting(colNames,G,row);

	            	}
	            }
	    	}
		},
		
		gridHeaderFontSetting : function(colNames,G,row){
			for(var j = 0; j < colNames.length; j++){
				purchaseAdd.gridHeaderFontSizeChange(G, row, colNames[j], 2);
        	}
			
		},
		
		gridHeaderFontSizeChange : function(G, tRow, attr, size){
			
			G.SetAttribute(tRow, attr, "Background", "#E2EDF4", 1);
			G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);
            G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
            G.SetAttribute(tRow, attr, "Changed", 0, 1);
		},
		
		goView : function(grid,row,col,x,y){
			return false;
		},

		search : function(perPage){
			
			if(!perPage) perPage = Grids['addPartSearchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['addPartSearchGrid'].Source.Data.Url = '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0';
			Grids['addPartSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['addPartSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['addPartSearchGrid'].Source.Data.Param.formPage=perPage;
//			Grids['addPartSearchGrid'].ReloadBody();
			
			Grids[0].Reload();
		},

		gridSave : function(){
			
			var R = Grids[0].GetSelRows();
		       
		    if( R.length == 0 ){
		        alert('항목을 선택하세요.');
		        return;
		    }
		    
			if(confirm("저장하시겠습니까?")){
				var grid = Grids[0];

		        var colNames = grid.ColNames[1];
		        
		        var paramArr = new Array();
		        
		        for ( var i=0; i<R.length; i++ ) {
		        	var paramObj = {};
		        	for(var j = 0; j < colNames.length; j++){
		        		var value = grid.GetString(R[i], colNames[j]);
	            		paramObj[colNames[j]] = value;
		        	}
		        	paramObj.addPart = "add";
		        	paramObj.pjtNo = $('#pjtNo').val();
		        	paramArr.push(paramObj);
		        }
		        
		        
		        if(paramArr.length < 1){
		        	alert("데이터가 조회되지 않았습니다.");
		        	return;
		        }
		        
		        var gridParam = {};
		        gridParam.treeData = paramArr;
		        
		        var gridParam = new Object();
		        gridParam.jsonData = JSON.stringify(paramArr);
		        
		        ajaxCallServer("/plm/ext/project/purchase/savePurchasePart", gridParam, function(data){				
		        	window.opener.purchase.search();
		        	window.close();
			    });
			}
		}
}