var MyECM = {
    /**
     * server paging grid
     */
    createPaingGrid : function() {
        this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
            id : 'listMyECMGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST',
                Format : 'Form',
                Param : {
                    formPage : (Grids['listMyECMGrid']) ? Grids['listMyECMGrid'].PageLength : CommonGrid.pageSize,
                    command : 'listMyECM'
                }
            },
            perPageOnChange : 'javascript:MyECM.search(Value);',
            Cols : [ {
                Name : 'ecmType',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'ecmNo',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'title',
                RelWidth : 100,
                Align : 'Left',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'projectNo',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'ecmReason',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'budget',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'creatorName',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'createDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'state',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                prodMoldCls : LocaleUtil.getMessage('00969'),           /*구분*/
                ecmNo : 'NO',                                           /*NO*/
                title : LocaleUtil.getMessage('02524'),                 /*제목*/
                projectNo : LocaleUtil.getMessage('03104'),             /*Project No*/
                ecmReason : LocaleUtil.getMessage('04580'),             /*설계변경사유*/
                budget : LocaleUtil.getMessage('02143'),                /*예산*/
                creatorName : LocaleUtil.getMessage('02431'),           /*작성자*/
                createDate : LocaleUtil.getMessage('04650'),            /*작성일자*/
                state : LocaleUtil.getMessage('00771')                  /*결재상태*/
            }
        }), 'listMyECMGridDiv');

        //row click event
        Grids.OnClick = MyECM.goView;

        //download page event
        Grids.OnDownloadPage = function(grid, row) {
            grid.Data.Page.Format = 'Form';
            grid.Data.Page.Method = 'Post';
            grid.Data.Page.Url = '/plm/ext/wfm/workspace/listMyECMData.do';
            grid.Data.Page.Param = {
                page : grid.GetPageNum(row),
                formPage : grid.PageLength,
                command : 'listMyECM'
            }
        };

        Grids.OnRenderPageStart = function(grid, row) {
        };

        Grids.OnRenderPageFinish = function(grid, row) {
        };

    },
    
    clear : function(){
//        $('[name=form01]').reset();
        $("form").each(function(){
           this.reset(); 
        });
        $("#prodMoldCls").multiselect("uncheckAll");
        $("#devYn").multiselect("uncheckAll");
        $("#sancStateFlag").multiselect("uncheckAll");
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids[0].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids[0].Source.Data.Url = '/plm/ext/wfm/workspace/listMyECMData.do';
        Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
        Grids[0].Source.Data.Param.command = 'listMyECM';
        Grids[0].Source.Data.Param.formPage = perPage;
        Grids[0].Source.Data.Param.Pagingsize = perPage;
        Grids[0].Reload();
    },

    goView : function(grid, row, col, x, y) {
        if (row) {
            if ("status" == col) {
                viewHistory(row.pboOid);
            } else if ("title" == col || "taskName" == col) {
                if ((row.pboOid).indexOf("e3ps.ecm.entity") > -1) {
                    var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=1";
                    getOpenWindow2(row.viewTaskUrl, 'ReviewTaskPopup', 1024, 768, opts);

                } else {
                    getOpenWindow2(row.viewTaskUrl, 'ReviewTaskPopup', 800, 600);
                }
            }
        }
    }
}