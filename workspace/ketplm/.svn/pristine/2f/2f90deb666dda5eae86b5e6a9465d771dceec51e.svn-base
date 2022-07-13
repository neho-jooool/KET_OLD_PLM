var kqisAdd = {
		
		
		createPaingGrid : function(){
			
			var config = {
					id : 'addKqisGrid',
					Data : {
					Url : '/plm/ext/project/task/addKqisSearchList.do?pjtNo='+$('[name=pjtNo]').val() + "&adminNo=" + $('[name=adminNo]').val(),
					Method : 'POST',
					Format : 'Form',
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
					},
					usePaging : false,
					useToolbar : false,//grid toolbar hide
					Cols : [
					        {Name : 'kqisCode', Width:0, CanEdit : '0', Visible : '0'},
					        {Name : 'adminNo', Width:120,  Align : 'Left', CanSort : '0', CanEdit : '0'},
							{Name : 'pjtNo', 	Width:90,  Align : 'Center', CanSort : '0' , CanEdit : '0'},
							{Name : 'testGubun', 	Width:90,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'testPurpose', 	Width:150,  Align : 'Left', CanSort : '0', CanEdit : '0'},
							{Name : 'reqDay', 	Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'reqId', 	Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0'},
							{Name : 'tester', 	Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0'},
					],Header :{
						CanDelete : '0', Align : 'Center',
						adminNo : "관리번호",
						pjtNo : "Project No",  
						testGubun : "시험구분", 
						testPurpose : "시험목적",
						reqDay: "의뢰일",
						reqId : "의뢰자",
						tester : "시험자",
					},
					SelectingSingle : '0',
					Panel : {
						Width : '21', Visible : '1',CanHide : '0'
					}
				};
			this.grid = TreeGrid(CommonGrid.getGridConfig(config),'listGrid');
			//row click event
			Grids.OnClick = kqisAdd.goView;
			
			Grids.OnRenderPageFinish = function(){
				
	    		var G = Grids[0];
	    		
	    		var rows = G.Rows;
	            var rowIds = Object.keys(rows);
	            var colNames = G.ColNames[1];
	            
	            colNames.push('Panel');
	            for(var i = 0; i < rowIds.length; i++){
	            	var row = rows[rowIds[i]];
	            	
	            	if(row.Kind == 'Header'){
	            		
	            		kqisAdd.gridHeaderFontSetting(colNames,G,row);

	            	}
	            }
	    	}
		},
		
		gridHeaderFontSetting : function(colNames,G,row){
			for(var j = 0; j < colNames.length; j++){
				kqisAdd.gridHeaderFontSizeChange(G, row, colNames[j], 2);
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
			
			if(!perPage) perPage = Grids['addKqisGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['addKqisGrid'].Source.Data.Url = '/plm/ext/project/task/addKqisSearchList.do';
			Grids['addKqisGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['addKqisGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['addKqisGrid'].Source.Data.Param.formPage=perPage;
//			Grids['addKqisGrid'].ReloadBody();
			
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
/*		        	paramObj.addPart = "add";
		        	paramObj.pjtNo = $('#pjtNo').val();*/
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
		        gridParam.taskOid = $('#taskOid').val();
		        
		        ajaxCallServer("/plm/ext/project/task/saveKqisTrust", gridParam, function(data){				

		        	if(opener && opener.kqisTrustReload){
		        		opener.kqisTrustReload();
		        	}
		        	//opener.parent.location = window.opener.parent.document.location.href;
		        	window.close();
			    });
			}
		}
}