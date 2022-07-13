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
			Cols : [
			    {Name:'oid', Width:0, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1', Visible:'0'},
			    {Name:'revNo', Width:80, RelWidth:'80', Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1', Type : 'Html'},
			    {Name:'creator', Width:100, RelWidth:'100', Align:'Center',  CanSort:'1', CanEdit:'0', Spanned:'1'},
			    {Name:'createStamp', Width:100, RelWidth:'100', Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1'},
			    {Name:'reviseCause', Width:250, RelWidth:'250', Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1'},
			    {Name:'reviseFileCount', Width:80, RelWidth:'80', Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Type : 'Html'}
			],
			Header :
			{
				CanDelete:'0', Align:'Center',
				//rowNum :'No',
				revNo : 'Rev',
				creator : LocaleUtil.getMessage('02431'),//'작성자',
				createStamp : LocaleUtil.getMessage('02428'),//'작성일',
				reviseCause : LocaleUtil.getMessage('07179'),//'개정사유',
				reviseFileCount : LocaleUtil.getMessage('02957'),//'파일'
			}
		}), 'listGrid');
	
		Grids.OnClick = ReviseAssessSheet.goView;
		
		Grids.OnRenderFinish = function(){
         	var G = Grids[0];
         	for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
        		G.SetString(tRow,'revNo','<font color="#1660C7">'+tRow.revNo+'</font>',1);
        		//G.SetString(tRow,'creator','<font color="#1660C7">'+tRow.creator+'</font>',1);
        		//G.SetString(tRow,'reviseCause','<font color="#1660C7">'+tRow.reviseCause+'</font>',1);
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
		var rowRevNoStr = row.revNo;
		rowRevNoStr = rowRevNoStr.substring( (rowRevNoStr.indexOf(">")+1) , (rowRevNoStr.indexOf("</")));
		var rowCntStr = row.reviseFileCount;
		rowCntStr = rowCntStr.substring( (rowCntStr.indexOf(">")+1) , (rowCntStr.indexOf("</")));
		if(col == 'revNo') {
			//alert("rowCntStr:"+rowCntStr + ", row.revNo" + row.revNo);
			getOpenWindow2('/plm/ext/project/gate/viewVersionedAssessForm.jsp?oid='+row.oid+'&versionNo='+rowRevNoStr+'&pjtOid='+$('[name=pjtOid]').val(),'TemplateAssessSheetViewPopup',920,550);
		}else if(col == 'reviseFileCount') {
			if(rowCntStr>0) {
				getOpenWindow2('/plm/ext/project/gate/viewAttatchedAssessForm.do?oid='+row.oid+'&versionNo='+rowRevNoStr,'TemplateAssessSheetViewPopup',600,450);
			}else {
				return;
			}
		}
	}
	       
}
