var IndexSearch = {
	/**
	 * client paging grid
	 */	
	createGrid : function(){
		this.grid = TreeGrid(CommonGrid.getGridConfig({
			id : 'IndexSearchGrid',
			Data : {
				Url : '/plm/ext/index/findDoc.do',
				Method : 'POST'
			},
			Sort : '-createStamp',
			perPageOnChange : 'javascript:IndexSearch.searchDoc(Value);',
			Cols : [
				{Name : 'type', 		Width:100, Align : 'Center', CanSort : '1', CanEdit:'0'},
				{Name : 'number', 		Width:100, Align : 'Center', CanSort : '1', CanEdit:'0'},
                {Name : 'title', 		Width:230, RelWidth : 50, Align : 'Left', CanSort : '1', CanEdit:'0'},
                {Name : 'createBy', 	Width:60, Align : 'Center', CanSort : '1', CanEdit:'0'},
                {Name : 'createStamp', 	Width:150, Align : 'Center', CanSort : '1', CanEdit:'0'},
                {Name : 'version', 		Width:50, Align : 'Center', CanSort : '1', CanEdit:'0'},
                {Name : 'state', 		Width:80, Align : 'Center', CanSort : '1', CanEdit:'0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				type : '구분',				
				number : '번호',
				title : '이름',
				createBy : '담당자',
				createStamp : '작성일(시)',
				version : 'Rev',
				state : '상태'
			}
		}),'listGrid');
		
		//row click event
		Grids.OnClick = IndexSearch.goView;
	},
	
	searchDoc : function(perPage){
		if($('[name=searchKeyword]').val() == '' || $('[name=searchKeyword]').val().length == 0){
			alert('검색어를 입력하여야 합니다.');
			return;
		}
		parent.$('[name=searchKeyword]').val($('[name=searchKeyword]').val());
		Grids[0].Source.Data.Params = decodeURIComponent($('[name=SearchForm]').serialize());
		Grids[0].Reload();
		if(perPage){
			Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
		}
	},
	
	callMain : function(searchCategory, searchKeyword){
		$('[name=searchCategory]').val(searchCategory.split(','));
		$('[name=searchKeyword]').val(searchKeyword);
		$('[name=searchCategory]').multiselect("refresh");
		this.searchDoc();
	},
	
	goView : function(grid,row,col,x,y){
		if(col == 'number' && row.oid){
			openView(row.oid);			
		}
	}
}