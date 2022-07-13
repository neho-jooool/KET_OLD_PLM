var ReviseAssessSheet = {
	
		
	//client Paging Grid
	revisePaigngGrid : function() {
		//this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'ReviseAssessSheetSearchGrid',
			Sort : '-revNo',
			Data : {
				Url : '/plm/ext/project/gate/findListRevisedProjectAssess.do?pjtOid='+$('[name=pjtOid]').val()+'&sortName=*Sort0', // 
				Method : 'POST'
			},
			perPageOnChange : 'javascript:ReviseAssessSheet.search(Value);',
			LeftCols : [
			            {Width:60, Align : 'Center', CanSort : '0', CanEdit : '0'}
					],
			Cols : [
			    {Name:'oid', Width:0, Align:'Center', CanSort:'1', CanEdit:'1', Spanned:'1', Visible:'0'},
			    {Name:'revNo', Width:80, RelWidth:'80', Align:'Center', CanSort:'1', CanEdit:'1', Spanned:'1', Type : 'Html'},
			    {Name:'creator', Width:120, RelWidth:'120', Align:'Center',  CanSort:'1', CanEdit:'1', Spanned:'1', Type : 'Html'},
			    {Name:'createStamp', Width:120, RelWidth:'120', Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG1]').val()},
			    {Name:'reviseCause', Width:250, RelWidth:'250', Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Type : 'Html', Visible:$('[name=gateExistG1]').val()},
			    {Name:'reviseFileCount', Width:150, RelWidth:'150', Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Type : 'Html', Visible:$('[name=gateExistG2]').val()}
			],
			Header :
			{
				CanDelete:'0', Align:'Center',
				//rowNum :'No',
				revNo : 'Rev',
				creator : '작성자',
				createStamp : '작성일',
				reviseCause : '개정사유',
				reviseFileCount : '파일'
			}
			,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
		}), 'listGrid');
	
		Grids.OnClick = ReviseAssessSheet.goView;
		
		Grids.OnRenderFinish = function(){
         	var G = Grids[0];
         	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		G.SetString(tRow,'revNo','<font color="#1660C7">'+tRow.revNo+'</font>',1);
        		//G.SetString(tRow,'creator','<font color="#1660C7">'+tRow.creator+'</font>',1);
        		G.SetString(tRow,'reviseCause','<font color="#1660C7">'+tRow.reviseCause+'</font>',1);
        		G.SetString(tRow,'reviseFileCount','<font color="#1660C7">'+tRow.reviseFileCount+'</font>',1);
         	}
         }

		
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['ReviseAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['ReviseAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['ReviseAssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=ReviseAssessSheetSearchForm]').serialize());
		Grids['ReviseAssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['ReviseAssessSheetSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=ReviseAssessSheetSearchForm]')[0].reset();
	},
	
	goView : function(grid, row, col, x, y) {
		if(col == 'revNo' || col == 'reviseCause') {
			getOpenWindow2('/plm/ext/project/gate/viewVersionedAssessForm.jsp?oid='+row.oid+'&versionNo='+row.revNo,'TemplateAssessSheetViewPopup',800,650);
		}else if(col == 'reviseFileCount') {
			var rowCntStr = row.reviseFileCount;
			rowCntStr = rowCntStr.substring( (rowCntStr.indexOf(">")+2) , (rowCntStr.indexOf("</")-1));
			if(rowCntStr>0) {
				getOpenWindow2('/plm/ext/project/gate/viewAttatchedAssessForm.do?oid='+row.oid+'&versionNo='+row.revNo,'TemplateAssessSheetViewPopup',800,650);
			}else {
				return;
			}
		}
	}
	       
}
