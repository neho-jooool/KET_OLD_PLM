var bomCheck = {
		
		createPaingGrid : function(){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sort : '-dieNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:bomCheck.search(Value);',
				usePaging : false,
				Sync : 1,
				Data : {
					Url : '/plm/ext/project/bom/findPagingList.do?sortName=*Sort0',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Cols : [
						{Name : 'taskId', Visible:'0', 	Width:0,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
		                {Name : 'rowNum', Realwidth:30,	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
		                {Name : 'dieNo',   Width:80,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
		                {Name : 'partNo', 	  Width:80,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
		                {Name : 'partName', 	  Width:280,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1',Type : 'Text'},
						//{Name : 'rawMaterial', 	  Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'rawQty', Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'scrapQty', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'tryRawQty', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'tryScrapQty', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'costRawQty', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'costScrapQty', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'tryRawRate', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'tryScrapRate', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'costRawRate', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'costScrapRate', 	Width:80,  Align : 'Right', CanSort : '0', CanEdit : '0' ,Spanned:'1'}
						],
						Head :[
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id : "Header",Spanned : '1',
									rowNum : 'No', rowNumRowSpan:'2',
									dieNo : "DIE No", dieNoRowSpan:'2',
									partNo : "부품번호", partNoRowSpan:'2',
									partName : "부품명", partNameRowSpan:'2',
									//rawMaterial : "원재료", rawMaterialRowSpan:'2',
									rawQty : "BOM", rawQtySpan:'2', rawQtyBackground : '#BCA9F5',
									tryRawQty : "TRY", tryRawQtySpan:'2', tryRawQtyBackground : '#9FF781',
									costRawQty: "원가", costRawQtySpan:'2', costRawQtyBackground : '#F2F5A9',
									tryRawRate: "차이 (BOM-TRY)", tryRawRateSpan:'2', tryRawRateBackground  : '#E6E6E6',
									costRawRate : "차이 (BOM-원가)", costRawRateSpan:"2", costRawRateBackground  : '#E6E6E6',
								},
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id:"HeaderTop", 
									rowNum : 'No', rowNumRowSpan:'2',
									dieNo : "DIE No", dieNoRowSpan:'2',
									partNo : "부품번호", partNoRowSpan:'2',
									partName : "부품번호", partNameRowSpan:'2',
									//rawMaterial : "원재료", rawMaterialRowSpan:'2',
									rawQty : "원재료",  rawQtyBackground : '#BCA9F5',
									scrapQty : "스크랩" , scrapQtyBackground : '#BCA9F5',
									tryRawQty : "원재료", tryRawQtyBackground : '#9FF781',
									tryScrapQty : "스크랩", tryScrapQtyBackground : '#9FF781',
									costRawQty : "원재료", costRawQtyBackground : '#F2F5A9',
									costScrapQty: "스크랩", costScrapQtyBackground : '#F2F5A9', 
									tryRawRate: "원재료", tryRawRateBackground  : '#E6E6E6',
									tryScrapRate: "스크랩", tryScrapRateBackground  : '#E6E6E6',
									costRawRate: "원재료", costRawRateBackground  : '#E6E6E6',
									costScrapRate: "스크랩", costScrapRateBackground  : '#E6E6E6',
								}
								]
					/*,
					SelectingSingle : '1',
					Panel : {
						Width : '21', Visible : '1',CanHide : '0'
					}*/
				}),'listGrid');

			//row click event
			//Grids.OnClick = issue.goView;
			
			Grids.OnRenderPageFinish = function(){
	    		var G = Grids[0];
	    		
	    		var rows = G.Rows;
	            var rowIds = Object.keys(rows);
	            
	            for(var i = 0; i < rowIds.length; i++){
	            	
	                var row = rows[rowIds[i]];
	                
	                if(row.Kind == 'Header'){
	                	
	                	bomCheck.gridHeaderFontSizeChange(G, row, "rowNum", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "dieNo", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "partNo", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "partName", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "rawQty", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "tryRawQty", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "costRawQty", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "tryRawRate", 2);
	                	bomCheck.gridHeaderFontSizeChange(G, row, "costRawRate", 2);
	                    
	                }
	            }
	    		
	    		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
	    			
	    			G.SetAttribute(tRow, "rawQty", "HtmlPrefix", "<b>", 1);
                    G.SetAttribute(tRow, "rawQty", "Postfix", "</>", 1);
                    G.SetAttribute(tRow, "rawQty", "Changed", 0, 1);
                    
                    G.SetAttribute(tRow, "scrapQty", "HtmlPrefix", "<b>", 1);
                    G.SetAttribute(tRow, "scrapQty", "Postfix", "</>", 1);
                    G.SetAttribute(tRow, "scrapQty", "Changed", 0, 1);
	                
	                bomCheck.gridColorChange(G, tRow, "partNo","#4398bc");
	                
	                
	                
	                //if(tRow.taskId){
	                	bomCheck.gridColorChange(G, tRow, "dieNo","#4398bc");	
	                //}
	                
	                if(tRow.tryRawRate && Math.abs(bomCheck.changeString(tRow.tryRawRate)) >= 5){
		                bomCheck.gridColorChange(G, tRow, "tryRawRate","red");
		                bomCheck.gridColorChange(G, tRow, "tryRawQty","red");
	    			}
	    			
	    			if(tRow.tryRawRate && Math.abs(bomCheck.changeString(tRow.tryRawRate)) >= 5){
		                bomCheck.gridColorChange(G, tRow, "tryRawRate","red");
		                bomCheck.gridColorChange(G, tRow, "tryRawQty","red");
	    			}
	    			
	    			if(tRow.tryScrapRate && Math.abs(bomCheck.changeString(tRow.tryScrapRate)) >= 5){
		                bomCheck.gridColorChange(G, tRow, "tryScrapRate","red");
		                bomCheck.gridColorChange(G, tRow, "tryScrapQty","red");
	    			}
	    			
	    			if(tRow.costRawRate && Math.abs(bomCheck.changeString(tRow.costRawRate)) >= 5){
		                bomCheck.gridColorChange(G, tRow, "costRawRate","red");
		                bomCheck.gridColorChange(G, tRow, "costRawQty","red");
	    			}
	    			
	    			if(tRow.costScrapRate && Math.abs(bomCheck.changeString(tRow.costScrapRate)) >= 5){
		                bomCheck.gridColorChange(G, tRow, "costScrapRate","red");
		                bomCheck.gridColorChange(G, tRow, "costScrapQty","red");
	    			}

	    		}
	    	}
			
			Grids.OnRenderFinish = function(grid, row){
			}
			
			Grids.OnClick = bomCheck.goView;
		},
		
		changeString : function(rate){
			return rate.replace(/%/gi,"");
		},
		
		gridColorChange : function(G, tRow, attr,color){
			G.SetAttribute(tRow, attr, "HtmlPrefix", "<font color="+color+">", 1);
            G.SetAttribute(tRow, attr, "Postfix", "</font>", 1);
            G.SetAttribute(tRow, attr, "Changed", 0, 1);
		},
		
		gridHeaderFontSizeChange : function(G, tRow, attr, size){
			G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);
            G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
            G.SetAttribute(tRow, attr, "Changed", 0, 1);
		},
		
		goView : function(grid,row,col,x,y){
			if(row.Kind == 'Data'){
				if(col == 'dieNo'){
					if(row.taskId){
						openView(row.taskId);
					}else{
						var taskId = bomCheck.getMoldTaskId(row.dieNo);
						if(taskId){
							openView(taskId);
						}else{
							alert('금형Try_[양산검증] Task가 존재하지 않습니다.');
							openProject(row.dieNo);
						}
					}
				}
				
				if(col == 'partNo'){
					openViewPart(row.partNo);
				}
			}
		},
		
		trimPjtNo : function(){
			var pjtNo = $('#pjtNo').val();
			
			console.log("params before === "+"["+pjtNo+"]");
			
			$("#pjtNo").val((pjtNo.replace(/ /g,"")).toUpperCase());
			
			console.log("params after === "+"["+$("#pjtNo").val()+"]");
		},
		
		
		search : function(perPage){
			
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/project/bom/findPagingList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['searchGrid'].Source.Data.Param.formPage=perPage;
//			Grids['searchGrid'].ReloadBody();
			
			Grids[0].Reload();
		},
		
		getMoldTaskId : function(dieNo){
			
			var taskId = '';
			
			$.ajax({
		          type: 'post',
		          url : '/plm/ext/project/bom/getMoldTaskOid.do?pjtNo='+dieNo,
		          async : false,
		          cache:false,
		          success : function(result){
		              if(result.taskId != ''){
		            	  taskId = result.taskId;
		              }
		          }
		    });
			
			return taskId;
		}
}