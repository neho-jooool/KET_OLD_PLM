var MyProject = {
    /**
     * server paging grid
     */
    createPaingGrid : function() {
        this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
            id : 'myProjectGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST',
                Format : 'Form',
                Param : {
                    formPage : (Grids['myProjectGrid']) ? Grids['myProjectGrid'].PageLength : CommonGrid.pageSize,
                    command : 'listMyProject'
                },
                Params : decodeURIComponent($('[name=myProject]').serialize())
            },
            perPageOnChange : 'javascript:MyProject.search(Value);',
            LeftCols : [ {
                Name : 'pjtType',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'pjtNo',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'pjtName',
                RelWidth : 150,
                Align : 'Left',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'buyer',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Cols : [ {
                Name : 'pjtPlanStartDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'pjtPlanEndDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
//                Name : 'pjtStatus',
//                Align : 'Center',
//                CanSort : '0',
//                CanEdit : '0',
//                Type : 'Html'
//            }, {
                Name : 'pjtState',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            } ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                rowNum : LocaleUtil.getMessage('00342'), /* No */
                pjtType : LocaleUtil.getMessage('00969'), /* 구분 */
                pjtNo : LocaleUtil.getMessage('00397'), /* Project No */
                pjtName : LocaleUtil.getMessage('00395'), /* Project Name */
                buyer : LocaleUtil.getMessage('00828'), /* 고객 */
                pjtPlanStartDate : LocaleUtil.getMessage('00643', ''), /* 개발시작일 */
                pjtPlanEndDate : LocaleUtil.getMessage('00647'), /* 개발완료일 */
                //pjtStatus : LocaleUtil.getMessage('03124'), /* 프로젝트현황 */
                pjtState : LocaleUtil.getMessage('01760')
            /* 상태 */
            }
        }), 'myProjectGridDiv');

        // row click event
        Grids.OnClick = MyProject.goView;

        // page event
        Grids.OnDownloadPage = function(grid, row) {
            grid.Data.Page.Format = 'Form';
            grid.Data.Page.Method = 'Post';
            grid.Data.Page.Url = '/plm/ext/wfm/workspace/listMyProjectData.do';
            grid.Data.Page.Param = {
                page : grid.GetPageNum(row),
                formPage : grid.PageLength,
                command : 'listMyProject'
            }
        }
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids[0].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids[0].Source.Data.Url = '/plm/ext/wfm/workspace/listMyProjectData.do';
        Grids[0].Source.Data.Param.command = 'listMyProject';
        Grids[0].Source.Data.Params = decodeURIComponent($('[name=myProject]').serialize());
        Grids[0].Source.Data.Param.formPage = perPage
        Grids[0].Source.Data.Param.Pagingsize = perPage;
        Grids[0].Reload();
    },

    selProjectNo : function() {
        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=Y";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if ( returnValue == null ) {
            return;
        }
        var objArr = returnValue;
        var proj_number = "";
        for ( var i = 0; i < objArr.length; i++ ) {
            proj_number += objArr[i][1] + ",";
        }
        if(proj_number.length > 0) proj_number = proj_number.substring(0, proj_number.length-1);
        $('[name=searchPjtNo]').val(proj_number);
    },

    clear : function() {
        $('[name=myProject]')[0].reset();
    },

    goView : function(grid, row, col, x, y) {
        if(row.Kind != "Header"){
            if ("pjtNo" == col) {
                openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=' + row.oid + "&popup=popup", '', 1150, 800);
            }
        }
    }
}