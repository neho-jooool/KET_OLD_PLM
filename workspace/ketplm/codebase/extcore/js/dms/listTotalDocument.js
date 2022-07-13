var MyDocument = {
        /**
         * server paging grid
         */
        createPaingGrid : function(isSingle){
            this.grid = TreeGrid(
                    CommonGrid.getGridConfig({
                        id : 'myDocumentGrid',
                        Data : {
                        	Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                            Method : 'POST',
                            Format : 'Form',
                            Param : {
                                formPage : (Grids['myDocumentGrid'])?Grids['myDocumentGrid'].PageLength:CommonGrid.pageSize,
                                        command : 'listMyDocument'
                            },
                            Params : decodeURIComponent($('[name=frm]').serialize())
                        },
                        perPageOnChange : 'javascript:MyDocument.search(Value);',
                        LeftCols : [
//                                    {Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
                                    {Name : 'documentNo', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                    {Name : 'documentCategory', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                    {Name : 'documentName', RelWidth:50, Align : 'Left', CanSort : '0', CanEdit : '0'}
                                    ],
                                    Cols : [
                                            {Name : 'state', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'version', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'createDate', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'primaryConentDownUrl', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'},
                                            {Name : 'buyerSummit', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                            {Name : 'security', Align : 'Center', CanSort : '0', CanEdit : '0', Visible: 0 }
                                            ],
                                            Header :{
                                                CanDelete : '0', CanEdit : '0', Align : 'Center',
//                                                rowNum : LocaleUtil.getMessage('00342'),                                /* No */
                                                documentNo : LocaleUtil.getMessage('01420'),                            /* 문서번호 */
                                                documentCategory : LocaleUtil.getMessage('01424'),                      /* 문서분류 */
                                                documentName : LocaleUtil.getMessage('01415'),                          /* 문서명 */
                                                state : LocaleUtil.getMessage('01760'),                                 /* 상태 */
                                                version : LocaleUtil.getMessage('01481', ' '),                          /* 버전 */
                                                creator : LocaleUtil.getMessage('02431', ' '),                          /* 작성자 */
                                                createDate : LocaleUtil.getMessage('02428'),                            /* 작성일 */
                                                primaryConentDownUrl : LocaleUtil.getMessage('02957'),                  /* 파일 */
                                                buyerSummit : LocaleUtil.getMessage('00854'),                            /* 고객제출 */
                                                security : '보안등급'
                                            },
                                            SelectingSingle : isSingle,
                                            Panel : {
                                                Width : '21', Visible : '1', CanHide : '1', Delete : '1'
                                            }
                    }), 'myDocumentGridDiv');

            //row click event
            Grids.OnClick = MyDocument.goView;
            /*
            // page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/wfm/workspace/listMyDocumentData.do';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listMyDocument'
                }
            }
             */
        },

        search : function(perPage){
            if(!perPage) perPage = Grids['myDocumentGrid'].Source.Layout.Data.Cfg.PageLength;
            Grids['myDocumentGrid'].Source.Data.Url = '/plm/ext/wfm/workspace/listMyDocumentData.do?totalFlag=Y&state=APPROVED';
            Grids['myDocumentGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids['myDocumentGrid'].Source.Data.Params = decodeURIComponent($('[name=frm]').serialize());
            Grids['myDocumentGrid'].Source.Data.Param.formPage=perPage
            Grids['myDocumentGrid'].Source.Data.Param.Pagingsize = perPage;
            Grids['myDocumentGrid'].Reload();
        },

        clear : function(){
            $('[name=frm]')[0].reset();
            $("#state").val("APPROVED");
            $("#state").multiselect("refresh");
        },

        approvalRequest : function(){

            var grid = Grids['myDocumentGrid'];
            if(grid != null){
                var rows = grid.GetSelRows();
                if(rows.length == 0){
                    alert('결재할 문서를 선택하여 주십시오');
                    return;
                }
                var stateFlag = true;
                var stateArr = new Array();
                var pboOidArr = new Array();
                if(rows.length > 0){
                    for ( var int = 0; int < rows.length; int++) {
                        var state = rows[int].state;
                        var oid = rows[int].oid;
                        if('작업중' == state || '재작업' == state){
                            pboOidArr[pboOidArr.length] = oid;
                        }
                        else{
                            stateFlag = false;
                        }
                    }// for end
                } // if end
                if(!stateFlag){
                    alert('결재요청 할 수 없습니다.\n문서의 상태가 \'작업중\', \'재작업\'인 경우만 결재요청 가능합니다.');
                    return;
                }
                else{
                    var params = '';
                    for ( var int = 0; int < pboOidArr.length; int++) {
                        var pboOid = pboOidArr[int];
                        params += 'pboOids=' + pboOid + '&';
                    }
                    var addr = "/plm/jsp/wfm/RequestApproval.jsp?" + params;
                    var topener = window.open(addr,"approval","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=740,height=550");
                    topener.focus();
                }
            }// if end
        },

        selectDocCategory : function(checkedNode){
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
            $('[name=documentCategory]').val(nodeIdStr);
            $('[name=documentCategoryTxt]').val(nodeNameStr);
        },

        setProjectNo: function(objArr){
            var projectNo= "";
            for ( var i = 0; i < objArr.length; i++ ) {
                projectNo+= objArr[i][1] + ",";
            }
            if(projectNo.length > 0) projectNo= projectNo.substring(0, projectNo.length-1);
            $('[name=pjtNo]').val(projectNo);
        },

        goView : function(grid,row,col,x,y){
            if(row){
                if("documentNo" == col){
                    var oid = row.oid;
                    if(oid.indexOf("ProjectDocument") > 0){
                        var url = "/plm/jsp/dms/ViewDocument.jsp?oid="+row.oid;
                    }
                    else{
                        var url = "/plm/jsp/dms/ViewTechDocument.jsp?oid="+row.oid;
                    }
                    getOpenWindow2(url, '',1000,800);
                }
                else if("pjtNo" == col){
                    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.pjtOid+"&popup=popup", '',1150,800);
                }
            }
        }
}