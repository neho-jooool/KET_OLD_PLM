/*
 * 화면 렌더전 perPage 설정
 */
Grids.OnRenderStart = function(G){
	try{
		G.Toolbar.perPage = G.Source.Layout.Data.Cfg.PageLength;
	}catch(exception){};
}
/*
 * 화면 렌더링 완료 후, No(rowNum) 컬럼 값 변경
 */
Grids.OnRenderFinish = changeRowNum;

/*
 * 정렬 완료 후, No(rowNum) 컬럼 값 변경
 */
Grids.OnSortFinish = changeRowNum;

function changeRowNum(G){
	
    // client paging만 rownum을 업데이트 합니다.
    if(G.Source.Layout.Data.Cfg.Paging == '2'){
      
      // 그리드에 로드된 전체 행의 수
      var loadedCount = G.LoadedCount;
    
	    for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
	
	    	G.SetString( tRow, "rowNum", loadedCount--, true );
	    }
    }
}