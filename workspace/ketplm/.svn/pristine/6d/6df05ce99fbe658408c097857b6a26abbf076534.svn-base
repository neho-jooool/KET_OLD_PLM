/****************************************************
 * 서버 페이징시 사용하는 JS
 * ( 참고 : Grid_KET.js는 클라이언트 페이징시 사용하는 JS )
 ****************************************************/
 
/**
 * 페이징이 로드 된 후에, No(RowNum) 컬럼 값 변경
 * No 처리 로직은 totalcount - pageSize * ( pos - 1) 인데
 * totalcount, pageSize, pos Grid API에서 정보를 받아옴.
 * pos를 Row Loop로 찾아옴
 */
Grids.OnRenderPageFinish = function(G){
	changeRow(G);
};

function changeRow(G){
	
	try{
		  // 첫 Row가 없으면 작업 중지
		  var fRow = G.GetFirstVisible();
	    if(fRow){
	    	
	    	var rootCount = G.RootCount;
		    //alert("rootCount:" + rootCount);
		    
		    var pagingSize = G.Data.Layout.Param.Pagingsize;
		    //alert("pagingSize:" + pagingSize);
		    
		    var pos = -1;
		    var startNo = -1;
		    var changeStart = false;
		    
		    for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
		      
		    	var rowNum = G.GetString( tRow, "RowNum");
		    	//alert("rowNum:" + rowNum);
		    	
		    	if(rowNum == ''){
		    	  
		    		if(pos == -1){
		    		  
		    		  pos = G.GetPageNum( G.GetRowPage( tRow ) );
		    		  //alert("pos:" + pos);
		    		  
		    		  startNo = rootCount - (pagingSize*(pos));
		    		  //alert("startNo:" + startNo);
		    		}
		    		
		    		G.SetString( tRow, "RowNum", startNo--, true );
		    		start = true;
		    		
		    	}else{
		    		if(changeStart)
		    			break;
		    	}
		    }
	    }
    
	}catch(e){
	    alert("에러가 발생했습니다. \n관리자에게 문의바랍니다. \n" + e.message);
	}
}


/**
 * 페이징이 로드 된 후에, No(RowNum) 컬럼 값 변경
 * No 처리 로직은 totalcount - pageSize * ( pos - 1) 인데
 * pos를 Event 찾아옴 - 로직이 복잡해져 일단 멈춤
 */
 /*
var _EJS_GLOBAL_GRID_PAGE_INDEX = 0;
Grids.OnGoToPage = function ( grid, page, pagepos){
	_EJS_GLOBAL_GRID_PAGE_INDEX = pagepos;
	//alert("_EJS_GLOBAL_GRID_PAGE_INDEX:" + _EJS_GLOBAL_GRID_PAGE_INDEX );
}

function changeRow(G){
   try{
	    var fRow = G.GetFirstVisible();
	    if(fRow){
	      
	        // 그리드에 로드된 전체 행의 수
	        var rootCount =  G.RootCount;
	        //alert("rootCount:" + rootCount);
	        
	        var pagingSize = G.Data.Layout.Param.Pagingsize;
	        //alert(pagingSize);
	        
	        var pos = _EJS_GLOBAL_GRID_PAGE_INDEX;
	        //alert("pos:" + pos);
	        
	        var startNo = rootCount - (pagingSize*(pos));
	        //alert("startNo:" + startNo);
	        
	        var tRow;
	        for ( var idx = 0; idx < pagingSize; idx++ ) {
	            
	            if(idx == 0){
	                tRow = G.PagePosToRow(G.GetPage(pos), pos);
	                //alert("trow0:" + tRow);
	            }else{
	                tRow = G.GetNextVisible(tRow);
	                //alert("trow"+idx+":" + tRow);
	            }
	            if(tRow){
	            	   G.SetString( tRow, "RowNum", startNo--, true );
	            }else
	            	break;
	        }
	    }
            
    }catch(e){
        alert("에러가 발생했습니다. \n관리자에게 문의바랍니다. \n" + e.message);
    }
 }
*/
