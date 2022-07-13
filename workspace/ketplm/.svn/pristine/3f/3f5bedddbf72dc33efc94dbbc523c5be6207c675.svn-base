var WorkItem = {
        /**
         * server paging grid
         */
        createPaingGrid : function(){
            this.grid = TreeGrid(
                    CommonGrid.getPagingGridConfig({
                        id : 'listTempWorkItemGrid',
                        Data : {
                            Url : '/plm/ext/wfm/workflow/listTempWorkItemData.do?sortName=*Sort0',
                            Method : 'POST',
                            Format : 'Form',
                            Param : {
                                formPage : (Grids['listTempWorkItemGrid'])?Grids['listTempWorkItemGrid'].PageLength:CommonGrid.pageSize,
                                        command : 'listTempWorkItem'
                            },
                            Params : decodeURIComponent($('[name=form01]').serialize())
                        },
                        perPageOnChange : 'javascript:WorkItem.search(Value);',
                        Cols : [
//                                {Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'taskType', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'title', RelWidth : 50, Align : 'Left', CanSort : '0', CanEdit : '0'},
                                {Name : 'version', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'status', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'createDate', Align : 'Center', CanSort : '0', CanEdit : '0'}
                                ]
                        ,Header :{
                            CanDelete : '0', CanEdit : '0', Align : 'Center',
//                            rowNum : LocaleUtil.getMessage('00342'),/* No */
                            taskType : LocaleUtil.getMessage('02242'),          /*유형*/
                            title : LocaleUtil.getMessage('00756'),             /*결재 대상명*/
                            version : LocaleUtil.getMessage('01481'),           /*버전*/
                            status : LocaleUtil.getMessage('00771'),            /*결재상태*/
                            creator : LocaleUtil.getMessage('02431'),           /*작성자*/
                            createDate : LocaleUtil.getMessage('02428')         /*작성일*/
                        }/*,
                        SelectingSingle : '0',
                        Panel : {
                            Width : '21', Visible : '1',CanHide : '0'
                        }*/
                    }), 'listTempWorkItemGridDiv');

            //row click event
            Grids.OnClick = WorkItem.goView;

            //download page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/wfm/workflow/listTempWorkItemData.do?sortName=*Sort0';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listTempWorkItem',
                        predate : $('[name=predate]').val(),
                        postdate : $('[name=postdate]').val()
                }
            }
        },

        search : function(perPage){
            if(!perPage) perPage = Grids['listTempWorkItemGrid'].Source.Layout.Data.Cfg.PageLength;
            Grids['listTempWorkItemGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids['listTempWorkItemGrid'].Source.Data.Param.command = 'listTempWorkItem';
            Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
            Grids['listTempWorkItemGrid'].Source.Data.Param.formPage=perPage
            Grids['listTempWorkItemGrid'].Source.Data.Param.Pagingsize = perPage;
            Grids['listTempWorkItemGrid'].Reload();
        },

        goView : function(grid,row,col,x,y){
            if(row.Kind != "Header"){
                if("status" == col){
                    viewHistory(row.pboOid);
                }
                else if("title" == col){
                    openView(row.pboOid);
                }
            }
        }
}