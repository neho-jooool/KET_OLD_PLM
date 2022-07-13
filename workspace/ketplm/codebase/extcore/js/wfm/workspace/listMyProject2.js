var MyProject = {
    /**
         * client paging grid
     */
    createGrid : function() {
        this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'myProjectGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST'
            },
            Sort : '-pjtPlanEndDate',
            perPageOnChange : 'javascript:MyProject.search(Value);',
            LeftCols : [ {
                Name : 'pjtType',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'pjtNo',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'pjtName',
                RelWidth : 150,
                Align : 'Left',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'buyer',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            } ],
            Cols : [ {
                Name : 'pjtPlanStartDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'p1_contact_date',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'p2_contact_date',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            },{
                Name : 'pjtPlanEndDate',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'sop_contact_date',
                Align : 'Center',
                CanSort : '1',
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
                CanSort : '1',
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
                buyer : LocaleUtil.getMessage('00859'), /* 고객 */
                pjtPlanStartDate : LocaleUtil.getMessage('07165', ''), /* 계획시작일 */
                p1_contact_date : LocaleUtil.getMessage('00859', '')+'P1', /* 접점고객P1 */
                p2_contact_date : LocaleUtil.getMessage('00859', '')+'P2', /* 접점고객P2 */
                pjtPlanEndDate : LocaleUtil.getMessage('07166'), /* 계획종료일 */
                sop_contact_date : LocaleUtil.getMessage('00859', '')+'SOP', /* 접점고객SOP */
                //pjtStatus : LocaleUtil.getMessage('03124'), /* 프로젝트현황 */
                pjtState : LocaleUtil.getMessage('01760')
            /* 상태 */
            }
        }), 'myProjectGridDiv');
        // row click event
        Grids.OnClick = MyProject.goView;
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids[0].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids[0].Source.Data.Url = '/plm/ext/wfm/workspace/listMyProjectData.do';
        Grids[0].Source.Data.Param.command = 'listMyProject';
        Grids[0].Source.Data.Params = decodeURIComponent($('[name=myProject]').serialize());
        Grids[0].Source.Data.Param.formPage = perPage;
        Grids[0].Source.Data.Param.Pagingsize = perPage;
        Grids[0].Reload();
    },
    
    setProjectNo : function(returnValue) {
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

    selProjectNo : function() {
    	
    	SearchUtil.selectOneProjectPopUp('MyProject.setProjectNo');        
        
    },

    clear : function() {
        $('[name=myProject]')[0].reset();
    },

    goView : function(grid, row, col, x, y) {
        if(row.Kind != "Header"){
            if ("pjtNo" == col) {
            	openView(row.oid);
                //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid=' + row.oid + "&popup=popup", '', 1150, 800);
            }
        }
    }
}