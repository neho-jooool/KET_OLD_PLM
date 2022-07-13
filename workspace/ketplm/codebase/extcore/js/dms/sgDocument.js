var carTypeRequire = false;
var sgDocument = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'SGSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['SGSearchGrid'])?Grids['SGSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-modifyDate',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:sgDocument.search(Value);',
			Cols : [
					{Name : 'rowNum', Width: 40, Align : 'center', CanSort : '0', CanEdit : '0'},
			        {Name : 'moduleDisplay', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:sgDocument.openView(Row.oid);', 
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'docNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:sgDocument.openView(Row.oid);', 
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'docName', MinWidth:150, RelWidth : 1, Align : 'Left', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:sgDocument.openView(Row.oid);',
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
                    {Name : 'version', Width:50, Align : 'Center', CanSort : '1', CanEdit : '0'},
                    {Name : 'modifyDate', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'creatorName', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
			],Header :{
				CanDelete : '0', Align : 'Center',
				CanSelect : 1,
				rowNum : 'No',
				moduleDisplay : '모듈',
				docNo : '문서번호',
				docName : '문서명',
				version : 'Rev',
				modifyDate : '최종배포일',
				creatorName : '작성자'
			},
			SelectingSingle : '1',
			Panel : {
				Width : '20', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/dms/findPagingList.do?sortName=*Sort0';
			
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
		//Grids.OnClick = cost.goView;
	},
	openView : function(oid){
		if(oid != null) getOpenWindow2("/plm/ext/dms/viewSGDocumentPopup?oid=" + oid, "SGDocumentPopup", 800, 450);
	},
	openHistory : function(oid){
		if(oid != null) getOpenWindow2("/plm/ext/dms/viewSGDocHistoryPopup?oid=" + oid, "SGDocHistoryPopup", 800, 350);
	},
	search : function(perPage){
		if(!perPage) perPage = Grids['SGSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['SGSearchGrid'].Source.Data.Url = '/plm/ext/dms/findPagingList.do?sortName=*Sort0';
		Grids['SGSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['SGSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['SGSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['SGSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	needCheckAttribute : function(){
    	
    	var isNotEmpty = true;
    	
    	$("select, input").each(function(){
    		
    		var message = $(this).data("message");
    		var value = this.value;
    		
    		window.console.log($(this).attr("name"), value);
    		window.console.log((value == null || value.trim().length == 0));
    		
    		if(message != null && message.trim().length > 0 && (value == null || value.trim().length == 0)){
    			alert(message);
    			isNotEmpty = false;
    			return false;
    		}
    	});
    	
    	return isNotEmpty;
    },
    saveSGDocument : function(){
		
        if(sgDocument.needCheckAttribute() && confirm("저장하시겠습니까?")){
            
            var param = $("[name=uploadForm]").serializefiles();
            
            ajaxCallServer("/plm/ext/dms/saveSGDocument", param, function(data){
                
                alert("저장되었습니다.");
                
                if(opener) opener.sgDocument.search();
                
                location.href = '/plm/ext/dms/viewSGDocumentPopup?oid=' + data.oid;
            });
        }
	},
	deleteSGDocument : function(oid){
		
        if(confirm("삭제하시겠습니까?")){
            
            var param = { oid : oid }
            
            ajaxCallServer("/plm/ext/dms/deleteSGDocument", param, function(data){
            	
            	if(opener) opener.sgDocument.search();
            	
            	if(data.oid != null){
            		location.href = '/plm/ext/dms/viewSGDocumentPopup?oid=' + data.oid;
            	}else{
            		self.close();
            	}
                
            });
        }
	},
	reviseSGDocument : function(oid){
		
        if(confirm("개정하시겠습니까?")){
            
        	var param = { oid : oid }
            
            ajaxCallServer("/plm/ext/dms/reviseSGDocument", param, function(data){
            	
            	if(opener) opener.sgDocument.search();
            	location.href = '/plm/ext/dms/viewSGDocumentPopup?oid=' + data.oid;
            });
        }
	},
}