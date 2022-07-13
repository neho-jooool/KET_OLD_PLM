var MyDocument = {
    /**
     * server paging grid
     */
    createPaingGrid : function() {
        this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'myDocumentGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
//                Url : '/plm/ext/wfm/workspace/listMyDocumentData.do',
                Method : 'POST',
                Format : 'Form',
                Param : {
                    formPage : (Grids['myDocumentGrid']) ? Grids['myDocumentGrid'].PageLength : CommonGrid.pageSize,
                    state : $('[name=state1]').val(),
                    version : $('[name=version1]').val(),
                    createDateFrom : $('[name=createDateFrom1]').val(),
                    createDateTo : $('[name=createDateTo1]').val(),
                    pjtType : $('[name=pjtType1]').val(),
                    pjtNo : $('[name=pjtNo1]').val(),
                    command : 'listMyDocument'
                },
                Params : decodeURIComponent($('[name=frm]').serialize())
            },
            perPageOnChange : 'javascript:MyDocument.search(Value);',
            LeftCols : [ {
                Name : 'rowNum',
                Width : 40,
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'documentNo',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'documentCategory',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'documentName',
                RelWidth : 50,
                Align : 'Left',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Cols : [ {
                Name : 'state',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'version',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'creator',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'createDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'creator',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'primaryConentDownUrl',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0',
                Type : 'Html'
            }, {
                Name : 'buyerSummit',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                rowNum : LocaleUtil.getMessage('00342'), /* No */
                documentNo : LocaleUtil.getMessage('01420'), /* 문서번호 */
                documentCategory : LocaleUtil.getMessage('01424'), /* 문서분류 */
                documentName : LocaleUtil.getMessage('01415'), /* 문서명 */
                state : LocaleUtil.getMessage('01760'), /* 상태 */
                version : LocaleUtil.getMessage('01481', ' '), /* 버전 */
                creator : LocaleUtil.getMessage('02431', ' '), /* 작성자 */
                createDate : LocaleUtil.getMessage('02428'), /* 작성일 */
                primaryConentDownUrl : LocaleUtil.getMessage('02957'), /* 파일 */
                buyerSummit : LocaleUtil.getMessage('00854')
            /* 고객제출 */
            },
            SelectingSingle : '1',
            Panel : {
                Width : '21',
                Visible : '1',
                CanHide : '1',
                Delete : '1'
            }
        }), 'myDocumentGridDiv');
        //row click event
        Grids.OnClick = MyDocument.goView;
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids['myDocumentGrid'].Source.Layout.Data.Cfg.PageLength;
        Grids['myDocumentGrid'].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids['myDocumentGrid'].Source.Data.Url = '/plm/ext/wfm/workspace/listMyDocumentData.do';
        Grids['myDocumentGrid'].Source.Data.Param.state = $('[name=state1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.version = $('[name=version1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.createDateFrom = $('[name=createDateFrom1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.createDateTo = $('[name=createDateTo1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.pjtType = $('[name=pjtType1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.pjtNo = $('[name=pjtNo1]').val();
        Grids['myDocumentGrid'].Source.Data.Param.formPage = perPage;
        Grids['myDocumentGrid'].Source.Data.Param.Pagingsize = perPage;
        Grids['myDocumentGrid'].Source.Data.Param.command = 'listMyDocument';
        Grids['myDocumentGrid'].Source.Data.Params = decodeURIComponent($('[name=frm]').serialize());
        Grids['myDocumentGrid'].ReloadBody();
    },

    clear : function() {
        $('[name=frm]')[0].reset();
    },

    selectDocument : function() {
        var grid = Grids['myDocumentGrid'];
        if (grid != null) {
            var rows = grid.GetSelRows();
            if (rows.length == 0) {
                alert(LocaleUtil.getMessage('05138'));/*'문서를 선택하여 주십시오'*/
                return;
            }
            var url = "/plm/ext/wfm/workspace/linkProjectDocument.do";
            var params = "?docoid=" + rows[0].oid;
            for ( var i = 0; i < $('[name=outputoid]').length; i++) {
                params += "&outputoid=" + $('[name=outputoid]')[i].value;
            }
            
            $.ajax({
                url : url + params,
                type : "POST",
                dataType : 'json',
                async : false,
                success : function(flag) {
                    if (flag) {
                        alert(LocaleUtil.getMessage('02518'));//정상적으로 처리되었습니다
                        if(document.frm.returnTargetUrl.value!='') {
                        	//예,returnTargetUrl=/plm/jsp/project/ProjectViewFrm.jsp?oid=Oid:e3ps.project.E3PSTask:100000646355"
                        	opener.parent.document.location.href=document.frm.returnTargetUrl.value+'&popup=popup'; 
                        }else {
                        	opener.location.reload();
                        }
                        window.close();
                    } else {
                        alert(LocaleUtil.getMessage('05139') + "\n" + LocaleUtil.getMessage('05140'));//'에러.'
                    }
                }
            });
        }// if end
    },

    selectDocCategory : function(checkedNode) {
        var nodeIdStr = '', nodeNameStr = '';
        for ( var i = 0; i < checkedNode.length; i++) {
            if (i == checkedNode.length - 1) {
                nodeIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            } else {
                nodeIdStr += checkedNode[i].id + ',';
                nodeNameStr += checkedNode[i].name + ',';
            }
        }
        $('[name=documentCategory]').val(nodeIdStr);
        $('[name=documentCategoryTxt]').val(nodeNameStr);
    },

    goView : function(grid, row, col, x, y) {
        if(row.Kind != "Header"){
            if ("documentNo" == col) {
                var oid = row.oid;
                if (oid.indexOf("ProjectDocument") > 0) {
                    var url = "/plm/jsp/dms/ViewDocument.jsp?oid=" + row.oid;
                } else {
                    var url = "/plm/jsp/dms/ViewTechDocument.jsp?oid=" + row.oid;
                }
                getOpenWindow2(url, '', 1000, 800);
            } else if ("pjtNo" == col) {
                openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=' + row.pjtOid + "&popup=popup", '', 1150, 800);
            }
        }
    }
}

var ProjectDocument = {
    /**
     * server paging grid
     */
    createPaingGrid : function() {
        this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
            id : 'projectDocumentGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
//                Url : '/plm/ext/dms/listProjectDocumentData.do',
                Method : 'POST',
                Format : 'Form',
                Param : {
                    formPage : (Grids['projectDocumentGrid']) ? Grids['projectDocumentGrid'].PageLength : CommonGrid.pageSize,
                    state : $('[name=state2]').val(),
                    version : $('[name=version2]').val(),
                    createDateFrom : $('[name=createDateFrom2]').val(),
                    createDateTo : $('[name=createDateTo2]').val(),
                    pjtType : $('[name=pjtType2]').val(),
                    pjtNo : $('[name=pjtNo2]').val(),
                    command : 'listProjectDocument'
                },
                Params : decodeURIComponent($('[name=ProjectDocumentFrm]').serialize())
            },
            perPageOnChange : 'javascript:ProjectDocument.search(Value);',
            LeftCols : [ {
                Name : 'rowNum',
                Width : 40,
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'documentNo',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'projectDocType',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'documentName',
                RelWidth : 50,
                Align : 'Left',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Cols : [ {
                Name : 'state',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'version',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'creator',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'createDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'primaryConentDownUrl',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0',
                Type : 'Html'
            }, {
                Name : 'buyerSummit',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                rowNum : LocaleUtil.getMessage('00342'), /* No */
                documentNo : LocaleUtil.getMessage('01420'), /* 문서번호 */
                projectDocType : LocaleUtil.getMessage('01424'), /* 문서분류 */
                documentName : LocaleUtil.getMessage('01415'), /* 문서명 */
                state : LocaleUtil.getMessage('01760'), /* 상태 */
                version : LocaleUtil.getMessage('01481'), /* 버전 */
                creator : LocaleUtil.getMessage('02431'), /* 작성자 */
                createDate : LocaleUtil.getMessage('02428'), /* 작성일 */
                primaryConentDownUrl : LocaleUtil.getMessage('02957'), /* 파일 */
                buyerSummit : LocaleUtil.getMessage('00854')
            /* 고객제출 */
            },
            SelectingSingle : '1',
            Panel : {
                Width : '21',
                Visible : '1',
                CanHide : '1',
                Delete : '1'
            }
        }), 'projectDocumentGridDiv');

        //row click event
        Grids.OnClick = ProjectDocument.goView;

        // page event
        Grids.OnDownloadPage = function(grid, row) {
            grid.Data.Page.Format = 'Form';
            grid.Data.Page.Method = 'Post';
            grid.Data.Page.Url = '/plm/ext/dms/listProjectDocumentData.do';
            grid.Data.Page.Param = {
                page : grid.GetPageNum(row),
                formPage : grid.PageLength,
                command : 'listProjectDocument'
            }
        }
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids['projectDocumentGrid'].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids['projectDocumentGrid'].Source.Data.Url = '/plm/ext/dms/listProjectDocumentData.do';
        Grids['projectDocumentGrid'].Source.Data.Param.state = $('[name=state2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.version = $('[name=version2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.createDateFrom = $('[name=createDateFrom2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.createDateTo = $('[name=createDateTo2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.pjtType = $('[name=pjtType2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.pjtNo = $('[name=pjtNo2]').val();
        Grids['projectDocumentGrid'].Source.Data.Param.formPage = perPage;
        Grids['projectDocumentGrid'].Source.Data.Param.Pagingsize = perPage;
        Grids['projectDocumentGrid'].Source.Data.Param.command = 'listProjectDocument';
        Grids['projectDocumentGrid'].Source.Data.Params = decodeURIComponent($('[name=ProjectDocumentFrm]').serialize());
        Grids['projectDocumentGrid'].ReloadBody();
        
        $('#projectDocumentGridDiv').height($('#myDocumentGridDiv').height()-150);
        
        
    },

    clear : function() {
        $('[name=ProjectDocumentFrm]')[0].reset();
    },

    /**
     * 개발산출문서 다중 선택
     * @param checkedNode
     */
    setDevDocCategory : function(checkedNode) {
        var nodeIdStr = '', nodeNameStr = '';
        for ( var i = 0; i < checkedNode.length; i++) {
            if (i == checkedNode.length - 1) {
                nodeIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            } else {
                nodeIdStr += checkedNode[i].id + ',';
                nodeNameStr += checkedNode[i].name + ',';
            }
        }
        $('[name=projectDocType]').val(nodeIdStr);
        $('[name=projectDocTypeTxt]').val(nodeNameStr);
    },

    selProjectNo : function() {
        var url = "/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=Y";
        returnValue = window.showModalDialog(url, window, "help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if (returnValue == null) {
            return;
        }
        var objArr = returnValue;
        var proj_number = "";
        for ( var i = 0; i < objArr.length; i++) {
            proj_number += objArr[i][1] + ",";
        }
        if (proj_number.length > 0) proj_number = proj_number.substring(0, proj_number.length - 1);
        $('[name=pjtNo]').val(proj_number);
    },

    selectDocument : function() {
        var grid = Grids['projectDocumentGrid'];
        if (grid != null) {
            var rows = grid.GetSelRows();
            if (rows.length == 0) {
                alert(LocaleUtil.getMessage('05138'));//'문서를 선택하여 주십시오'
                return;
            }
            //alert($('[name=outputoid]').length);
            //alert(rows[0].oid);
            var url = "/plm/ext/wfm/workspace/linkProjectDocument.do";
            var params = "?docoid=" + rows[0].oid;
            for ( var i = 0; i < $('[name=outputoid]').length; i++) {
                params += "&outputoid=" + $('[name=outputoid]')[i].value;
            }
            $.ajax({
                url : url + params,
                type : "POST",
                dataType : 'json',
                async : false,
                success : function(flag) {
                    if (flag) {
                        alert(LocaleUtil.getMessage('02518'));//'정상적으로 등록 되었습니다.'
                        if(document.frm.returnTargetUrl.value!='') {
                        	//예,returnTargetUrl=/plm/jsp/project/ProjectViewFrm.jsp?oid=Oid:e3ps.project.E3PSTask:100000646355"
                        	opener.parent.document.location.href=document.frm.returnTargetUrl.value+'&popup=popup'; 
                        }else {
                        	opener.document.location.reload();
                        }
                        window.close();
                    } else {
                        alert(LocaleUtil.getMessage('05139') + "\n" + LocaleUtil.getMessage('05140'));//'에러.'
                    }
                }
            });
        }// if end
    },

    goCreate : function(grid, row, col, x, y) {
        var url = "/plm/jsp/dms/CreateDocument.jsp";
        var params = "";
        getOpenWindow2(url + params, 'CreateProjectOutputPopup', 1000, 800);
    },

    goView : function(grid, row, col, x, y) {
        if(row.Kind != "Header"){
            if ("documentNo" == col) {
                getOpenWindow2('/plm/jsp/dms/ViewDocument.jsp?oid=' + row.oid, '', 1000, 800);
            } else if ("primaryConentIconUrl" == col) {

            }
        }
    }
}
