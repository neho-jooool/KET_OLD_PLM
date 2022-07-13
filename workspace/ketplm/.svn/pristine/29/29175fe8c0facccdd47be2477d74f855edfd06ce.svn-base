var purchase = {
		
		
		createPaingGrid : function(url){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sort : '-pjtNo',Sorting : '1',AutoSort : '1', MaxSort : '1',
				perPageOnChange : 'javascript:purchase.search(Value);',
				Sync : 1,
				Data : {
					//Url : '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0',
					
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
		                {Name : 'partNo', 	  Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
		                {Name : 'partName', 	  Width:140,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1',Type : 'Text'},
						//{Name : 'rawMaterial', 	  Width:100,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'outSourcing', Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'outSourcingGubun', 	Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'managerName', 	Width:50,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'pjtNo', 	Width:60,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'dateGubun', 	Width:40,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date1', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date2', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date3', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date4', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date5', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date6', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date7', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date8', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date9', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date10', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date11', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date12', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'date13', 	Width:70,  Align : 'Center', CanSort : '0', CanEdit : '0' ,Spanned:'1'},
						{Name : 'bigo', 	Width:150,  Align : 'Left', CanSort : '0', CanEdit : '0' ,Spanned:'1'}
						],
						Head :[
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id : "Header",Spanned : '1',
									rnum : 'No', rnumRowSpan:'2',
									partNo : "품번", partNoRowSpan:'2',
									partName : "품명", partNameRowSpan:'2',
									outSourcing : "협력사(제조사)", outSourcingSpan:'2',
									managerName : "담당자", managerNameRowSpan: '2',
									pjtNo: "PJT No", pjtNoRowSpan : '2',
									dateGubun: "일정\n구분", dateGubunRowSpan : '2',
									date1: "개발\n발주", date1RowSpan : '2',
									date2: "수입검사\n기준서\n접수", date2RowSpan : '2',
									date3: "검사\n협정서\n접수", date3RowSpan : '2',
									date4 : "금형제작", date4Span:'2',
									date6: "중요\n치수", date6RowSpan : '2',
									date7: "신뢰성\n(DV)", date7RowSpan : '2',
									date8: "P1\n입고", date8RowSpan : '2',
									date9: "전치수", date9RowSpan : '2',
									date10: "PPAP", date10Span : '3',
									date13: "P2", date13RowSpan : '2',
									bigo: "비고", bigoRowSpan : '2',
								},
								{
									CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
									id:"HeaderTop", 
									rnum : 'No', rnumRowSpan:'2',
									partNo : "품번", partNoRowSpan:'2',
									partName : "품명", partNameRowSpan:'2',
									outSourcing : "협력사", 
									outSourcingGubun : "구분" ,
									managerName : "담당자", managerNameRowSpan : '2',
									pjtNo: "PJT No", pjtNoRowSpan : '2',
									dateGubun: "일정\n구분", dateGubunRowSpan : '2',
									date1: "개발\n발주", date1RowSpan : '2',
									date2: "수입검사\n기준서\n접수", date2RowSpan : '2',
									date3: "검사\협정서\n접수", date3RowSpan : '2',
									date4: "제작\n완료", 
									date5: "Try", 
									date6: "중요\n치수", date6RowSpan : '2',
									date7: "신뢰성\n(DV)", date7RowSpan : '2',
									date8: "P1\n입고", date8RowSpan : '2',
									date9: "전치수", date9RowSpan : '2',
									date10: "서류\n제출", 
									date11: "공정\n감사", 
									date12: "승인", 
									date13: "P2", date13RowSpan : '2',
									bigo: "비고", bigoRowSpan : '2',
								}
								]
//				,
//								Toolbar : {
//									Cells : 'Reload,Export,Print,Columns,Link,Empty1,perPage,Formula',
//						            Styles : '2', Space:'0', Kind:'Topbar', Link:'0',
//									Empty1RelWidth:'1', Empty1Type:'Html',
//									perPageType:'Enum', perPageWidth:'50', perPageEnumKeys:CommonGrid.pagingList, perPageEnum:CommonGrid.pagingNameList, perPage:CommonGrid.pageSize,
//									perPageOnChange : 'javascript:purchase.search(Value);',
//									Formula:"'전체페이지: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  전체개수: <b>'+Grid.RootCount+'</b>'"
//								}
/*					,
					SelectingSingle : '1',
					Panel : {
						Width : '20', Visible : '1',CanHide : '0'
					}*/
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
			
			Grids.OnClick = purchase.goView;
			
			
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
	            	if(row.Kind == 'Header'){
	            		
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
			
			console.log("params before === "+"["+pjtNo+"]");
			
			$("#pjtNo").val((pjtNo.replace(/ /g,"")).toUpperCase());
			
			console.log("params after === "+"["+$("#pjtNo").val()+"]");
		},
		
		
		
		
		search : function(perPage){
			if(!perPage) perPage = Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength;
			Grids['searchGrid'].Source.Data.Url = '/plm/ext/project/purchase/findPagingList.do?sortName=*Sort0';
			Grids['searchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids['searchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids['searchGrid'].Source.Data.Param.formPage=perPage;
			Grids['searchGrid'].ReloadBody();
			
			//Grids[0].Reload();
		},
		
		openSaveListView : function(){
			var url = "/plm/ext/project/purchase/purchaseProjectUpdateList.do?pjtNo="+$('#pjtNo').val();
			
			var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
			leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
			toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

			var rest = "width=" + (screen.availWidth * 0.9) + ",height="
					+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
					+ toppos;

			window.open(url, '', (opts + rest));
		},
		
		addPart : function(){
			purchase.trimPjtNo();
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
			getOpenWindow2("/plm/ext/project/purchase/purchasePartAddForm.do?pjtNo="+pjtNo, pjtNo , 800, 720);
		},
		
		openDocList : function(){
			purchase.trimPjtNo();
			var pjtNo = $('#pjtNo').val();
			getOpenWindow2("/plm/ext/project/purchase/purchaseDocList.do?pjtNo="+pjtNo, pjtNo , 1500, 800);
		}
}