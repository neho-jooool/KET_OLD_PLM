var ProjectDocument = {
        /**
         * server paging grid
         */
        createPaingGrid : function(){
            this.grid = TreeGrid(
                    CommonGrid.getPagingGridConfig({
                        id : 'projectDocumentGrid',
                        SelectingSingle : '0',
            			Panel : {
            				Width : '20', Visible : '1',CanHide : '0'
            			},
                        Data : {
                            Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                            Method : 'POST',
                            Format : 'Form',
                            Param : {
                                formPage : (Grids['projectDocumentGrid'])?Grids['projectDocumentGrid'].PageLength:CommonGrid.pageSize,
                                        command : 'listProjectDocument'
                            },
                            Params : decodeURIComponent($('[name=ProjectDocumentFrm]').serialize())
                        },
                        perPageOnChange : 'javascript:ProjectDocument.search(Value);',
                        LeftCols : [
                                    {Name : 'primaryConentDownUrl', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html', Visible : 0},
                                    {Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
                                    {Name : 'documentNo', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                    {Name : 'projectDocType', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                    {Name : 'documentName', RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'}
                                    ],
                        Cols : [
                                {Name : 'state', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'version', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'creator', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'deptName', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'createDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'primaryConentIconUrl', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'},
                                {Name : 'buyerSummit', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'dateRevision', Align : 'Center', CanSort : '1', CanEdit : '0'}
                                
                                ],
                                Header :{
                                    CanDelete : '0', CanEdit : '0', Align : 'Center',
                                    rowNum : LocaleUtil.getMessage('00342'),                            /* No */
                                    documentNo : LocaleUtil.getMessage('01420'),                        /* 문서번호 */
                                    projectDocType : LocaleUtil.getMessage('01424'),                    /* 문서분류 */
                                    documentName : LocaleUtil.getMessage('01415'),                      /* 문서명 */
                                    state : LocaleUtil.getMessage('01760'),                             /* 상태 */
                                    version : LocaleUtil.getMessage('01481'),                           /* 버전 */
                                    creator : LocaleUtil.getMessage('02431'),                           /* 작성자 */
                                    deptName : LocaleUtil.getMessage('02425'),                       /*작성부서*/
                                    createDate : LocaleUtil.getMessage('02428'),                        /* 작성일 */
                                    primaryConentIconUrl : LocaleUtil.getMessage('02957'),                     /* 파일 */
                                    buyerSummit : LocaleUtil.getMessage('00854'),                        /* 고객제출 */
                                    dateRevision : LocaleUtil.getMessage('09494'),                        /* 개정도래일 */
                                }/*,
                                SelectingSingle : '0',
                                Panel : {
                                    Width : '21', Visible : '1', CanHide : '1', Delete : '1'
                                }*/
                    }), 'projectDocumentGridDiv');

            //row click event
            Grids.OnClick = ProjectDocument.goView;

            // page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/dms/listProjectDocumentData.do?sortName=*Sort0';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listProjectDocument'
                }
            }
        },

        createQualityPaingGrid : function(){
            this.grid = TreeGrid(
                    CommonGrid.getPagingGridConfig({
                        id : 'projectDocumentGrid',
                        Data : {
                            Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                            Method : 'POST',
                            Format : 'Form',
                            Param : {
                                formPage : (Grids['projectDocumentGrid'])?Grids['projectDocumentGrid'].PageLength:CommonGrid.pageSize,
                                        command : 'listProjectDocument'
                            },
                            Params : decodeURIComponent($('[name=ProjectDocumentFrm]').serialize())
                        },
                        perPageOnChange : 'javascript:ProjectDocument.search(Value);',
                        LeftCols : [
                                    {Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
                                    {Name : 'documentNo', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                    {Name : 'attribute1', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                    {Name : 'projectDocType', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                    {Name : 'documentName', RelWidth:50, Align : 'Left', CanSort : '1', CanEdit : '0'}
                                    ],
                        Cols : [
                                {Name : 'state', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'version', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'creator', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'createDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
                                {Name : 'primaryConentDownUrl', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'},
                                {Name : 'buyerSummit', Align : 'Center', CanSort : '1', CanEdit : '0'}
                                ],
                                Header :{
                                    CanDelete : '0', CanEdit : '0', Align : 'Center',
                                    rowNum : LocaleUtil.getMessage('00342'),                            /* No */
                                    documentNo : LocaleUtil.getMessage('01420'),                        /* 문서번호 */
                                    attribute1 : "품질문서번호",                        				    /* 품질번호 */
                                    projectDocType : LocaleUtil.getMessage('01424'),                    /* 문서분류 */
                                    documentName : LocaleUtil.getMessage('01415'),                      /* 문서명 */
                                    state : LocaleUtil.getMessage('01760'),                             /* 상태 */
                                    version : LocaleUtil.getMessage('01481'),                           /* 버전 */
                                    creator : LocaleUtil.getMessage('02431'),                           /* 작성자 */
                                    createDate : LocaleUtil.getMessage('02428'),                        /* 작성일 */
                                    primaryConentDownUrl : LocaleUtil.getMessage('02957'),                     /* 파일 */
                                    buyerSummit : LocaleUtil.getMessage('00854')                        /* 고객제출 */
                                }/*,
                                SelectingSingle : '0',
                                Panel : {
                                    Width : '21', Visible : '1', CanHide : '1', Delete : '1'
                                }*/
                    }), 'projectDocumentGridDiv');

            //row click event
            Grids.OnClick = ProjectDocument.goView;

            // page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/dms/listProjectDocumentData.do?sortName=*Sort0';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listProjectDocument'
                }
            }
        },
        
        search : function(perPage){
            if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
            Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids[0].Source.Data.Url = '/plm/ext/dms/listProjectDocumentData.do?sortName=*Sort0';
            Grids[0].Source.Data.Param.command = 'listProjectDocument';
            Grids[0].Source.Data.Params = decodeURIComponent($('[name=ProjectDocumentFrm]').serialize());
            Grids[0].Source.Data.Param.formPage=perPage
            Grids[0].Source.Data.Param.Pagingsize = perPage;
            Grids[0].Reload();
        },

        clear : function(){
            $('[name=ProjectDocumentFrm]')[0].reset();
            $("[name=projectDocType]").eq(0).attr("checked", true);
        },

        /**
         * 개발산출문서 다중 선택
         * @param checkedNode
         */
        setDevDocCategory : function(checkedNode){
                var nodeIdStr='', nodeNameStr='';
                for(var i=0; i < checkedNode.length; i++){
                        if(i == checkedNode.length - 1){
                                nodeIdStr += checkedNode[i].id;
                                nodeNameStr += checkedNode[i].name;
                        }else{
                                nodeIdStr += checkedNode[i].id+',';     
                                nodeNameStr += checkedNode[i].name+',';
                        }
                }
                $('[name=projectDocType]').val(nodeIdStr);
                $('[name=projectDocTypeTxt]').val(nodeNameStr);
        },

        selProjectNo : function(returnValue) {
            if ( returnValue == null ) {
                return;
            }
            var objArr = returnValue;
            var proj_number = "";
            for ( var i = 0; i < objArr.length; i++ ) {
                proj_number += objArr[i][1] + ",";
            }
            if(proj_number.length > 0) proj_number = proj_number.substring(0, proj_number.length-1);
            $('[name=pjtNo]').val(proj_number);
        },

        goCreate : function(grid,row,col,x,y){
//          getOpenWindow2('/plm/ext/sample/createForm.do','SampleCreatePopup',800,600);
            var url = "/plm/jsp/dms/CreateDocument.jsp";
            var params = "";
            getOpenWindow2(url+params, 'CreateProjectOutputPopup',1000,800);
        },
        
        goQualityCreate : function(grid,row,col,x,y){
            var url = "/plm/jsp/dms/CreateQualityDocument.jsp";
            var params = "";
            getOpenWindow2(url+params, 'CreateProjectOutputPopup',1000,800);
        },

        goView : function(grid,row,col,x,y){
            if(row.oid){
                if("documentNo" == col){
//                    getOpenWindow2('/plm/jsp/dms/ViewDocument.jsp?oid='+row.oid, '',1000,800);
                    
                    url = "/plm/jsp/dms/ViewDocument.jsp?oid="+row.oid;
            	    
            	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
            	    leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            	    toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            	    var rest = "width=" + (screen.availWidth * 0.9) + ",height="
            	            + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
            	            + toppos;

            	    window.open(url, '', (opts + rest));
                    
                }
                else if("primaryConentIconUrl" == col){
                	
                	var rtn = '';
                	
                    $.ajax({
                        url : "/plm/ext/dms/isgAuthUserDOC.do",
                        type : "POST",
                        data : {
                        	oid : row.oid
                        },
                        dataType : 'json',
                        async : false,
                        success : function(data) {
                            rtn = data;
                        }
                    });
                    
                    if(rtn != "Y"){
                    	alert("권한이 없습니다.");
                    	return;
                    }
                    var url = row.primaryConentDownUrl;
                    PLM_FILE_DOWNLOAD2(url);
                    
                   /* var winObj = null;
                    var url = row.primaryConentDownUrl;
                    var popName = "download";
                    var ot = "width=0 height=0 menubar=no status=no";
                    winObj = window.open(url, popName, ot);*/
                    
                }
            }
        },
        batchRevision : function(){
        	
        	var grid = Grids[0];
        	
        	var rows = grid.GetSelRows();
	        
	        window.console.log(rows);
	        
	        if( rows.length == 0 ){
	            alert(' 개정항목을 선택하세요.');
	            return;
	        }
	        
	        var list = [];
	        for(var i = 0; i < rows.length; i++){
	        	var row = rows[i];
	        	list.push(row.oid);
	        }
	        
	        var param = {
	        		docList : list
	        }
	        
	        ajaxCallServer("/plm/ext/dms/batchRevision", param, function(data){
	        	
	        	var errorList = data.errorList;
	        	
	        	if(errorList.length > 0){
	        		
	        		$(".errorList").html("");
	        		
	        		for(var i = 0; i < errorList.length; i++){
	        			
	        			var error = errorList[i];
	        			var html = "<tr>";
	        			html += "<td class='td'>" + i + "</td>";
	        			html += "<td class='td'><a href=\"javascript:openView('" + error.oid + "', 1000,800);\">";
	        			html +=	error.docNo + "</a></td>";
	        			html +=	"<td class='td'>" + error.message + "</td>";

	        			$(".errorList").append(html);
	        		}
	        		
	        		$('#layerPop').show();
	        		
	        	}else{
	        		Grids[0].Reload();
	        	}
	        });
        }
}