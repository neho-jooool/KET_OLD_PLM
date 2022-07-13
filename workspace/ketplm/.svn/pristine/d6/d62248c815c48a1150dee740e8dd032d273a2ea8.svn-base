var kqisFlag = 0;

var purchase = {
		
		
		createPaingGrid : function(url){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sort : '-pjtNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:purchase.search(Value);',
				Sync : 1,
				usePaging : false,
				//useToolbar : false,//grid toolbar hide
				Data : {
					Url : url,
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Cols : [
						{Name : 'taskId', Visible:'0', 	Width:0,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'rnum', Realwidth:30,	Width:30, Align : 'Center', CanSort : '0', CanEdit : '0', Spanned:'1'},
		                {Name : 'partNo', 	  Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
		                {Name : 'partName', 	  Width:280,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1',Type : 'Text'},
						{Name : 'dieNo', 	Width:90,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'pjtNo', 	Width:90,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'inspagDocNo', 	Width:150,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
						{Name : 'rev', 	Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
						{Name : 'recNo', 	Width:150,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
						{Name : 'stepName', 	Width:150,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1', Type : 'Html'},
						],
						Head :[
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id : "Header",Spanned : '1',
									rnum : 'No', rnumRowSpan:'2',
									partNo : "품번", partNoRowSpan:'2',
									partName : "품명", partNameRowSpan:'2',
									dieNo : "DieNo", dieNoRowSpan:'2',
									pjtNo: "PJT No", pjtNoRowSpan : '2',
									inspagDocNo: "검사협정서", inspagDocNoSpan : '2',
									recNo: "ISIR", recNoSpan : '2'
								},
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id:"HeaderTop", 
									inspagDocNo : "PLM 문서",
									rev : "Rev.",
									recNo : "의뢰번호",
									stepName : "단계",
								}
								]
				}),'listGrid');
			
			
		  Grids.OnDownloadPage = function(grid,row){
			  
				grid.Data.Page.Format = 'Form';
				grid.Data.Page.Method = 'Post';
				grid.Data.Page.Url = '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0';
				
				var param = {
			       		page : grid.GetPageNum(row),
			            formPage : grid.Source.Layout.Data.Cfg.PageLength
			        }

		        $('input,select').each(function(){
		        	var name = $(this).attr('name');
		            var value = $(this).val();
		            param[name] = value;
		        });
		        grid.Data.Page.Param = param;
			}

			
			//row click event
			
			//Grids.OnClick = purchase.goView;
			
			
			Grids.OnRenderPageFinish = function(){
				
				var pageLength = Grids[0].RowCount>0?Grids[0].Body.childNodes.length:0;
				var gridRowCount = 4;
				//alert(Grids[0].RootCount);
				gridRowCount = Grids[0].RootCount;
				//$('#testid').html("전체페이지 : <b>"+pageLength+"</b>  /  전체개수: <b>"+ gridRowCount +"</b>");
				var rowCount = 0;
	    		var G = Grids[0];
	    		G.Toolbar.Formula = '0';
	    		var rows = G.Rows;
	            var rowIds = Object.keys(rows);
	            var colNames = G.ColNames[1];
	            
	            //colNames.push('outSourcing');
	            //colNames.push('outSourcingGubun');
	            
	            for(var i = 0; i < rowIds.length; i++){
	            	var row = rows[rowIds[i]];
	            	if(row.Kind == 'Header' || row.Kind == 'HeaderTop'){
	            		
	            		purchase.gridHeaderFontSetting(colNames,G,row);
	            		rowCount++;
	            	}else{
	            		if(rowCount > 0){
	            			purchase.gridHeaderFontSetting(colNames,G,row);
	            			break;
	            		}
	            		
	            	}
	            	
	            }
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
		
		
		trimPjtNo : function(){
			var pjtNo = $('#pjtNo').val();
			
			console.log("params before === "+"["+pjtNo+"]");
			
			$("#pjtNo").val((pjtNo.replace(/ /g,"")).toUpperCase());
			
			console.log("params after === "+"["+$("#pjtNo").val()+"]");
		},

		search : function(perPage){
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/project/purchase/findDocList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['searchGrid'].Source.Data.Param.formPage=perPage;
			Grids['searchGrid'].ReloadBody();
			
			//Grids[0].Reload();
		},
		
		doViewDoc : function(oid){
			
			url = "/plm/jsp/dms/ViewDocument.jsp?oid="+oid;
			purchase.openDocLink(url);
		},
		
		openDocLink : function(url){
			
		    
			var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
		    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
		    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;
		    var rest = "width=" + (screen.availWidth * 0.9) + ",height="+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='+ toppos;
		    var pop = '';
			if(kqisFlag == 0){
				pop = window.open(url, '', (opts + rest));
				setTimeout(function(){
					window.open(url, '', (opts + rest));
					pop.close();
				}, 1000);
				//window.open('/plm/ext/project/purchase/kqisDocForm.do?url='+encodeURIComponent(url),'', (opts + rest));	
			}else{
				
			    window.open(url, '', (opts + rest));
			}
			
			kqisFlag++;
		}
		
		
		
		
}