/*
 * 화면 렌더링 완료 후, No(RowNum) 컬럼 값 변경
 */
Grids.OnRenderFinish = function(G) {

	// 그리드에 로드된 전체 행의 수
    var loadedCount = G.LoadedCount;

    for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {

    	G.SetString( tRow, "RowNum", loadedCount--, true );
    }
};

/*
 * 정렬 완료 후, No(RowNum) 컬럼 값 변경
 */
Grids.OnSortFinish = function(G) {

	// 그리드에 로드된 전체 행의 수
    var loadedCount = G.LoadedCount;

    for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {

        G.SetString( tRow, "RowNum", loadedCount--, true );
    }
};