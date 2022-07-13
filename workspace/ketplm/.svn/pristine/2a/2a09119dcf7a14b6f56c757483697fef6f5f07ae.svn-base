var WorkItem = {
        /**
         * client paging grid
         */     
        createGrid : function(){
            this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
                id : 'listCompletedWorkItemGrid',
                Data : {
                    Url : '/plm/ext/wfm/workflow/listCompletedWorkItemData.do',
                    Method : 'POST',
                    Param : {
                        formPage : (Grids['listCompletedWorkItemGrid'])?Grids['listCompletedWorkItemGrid'].PageLength:CommonGrid.pageSize,
                                command : 'listCompletedWorkItem'
                    },
                    Params : decodeURIComponent($('[name=form01]').serialize())
                },
                perPageOnChange : 'javascript:WorkItem.search(Value);',
                Cols : [
                        /*{Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},*/
                        {Name : 'taskType', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'title', RelWidth: 50, Align : 'Left', CanSort : '0', CanEdit : '0'},
                        {Name : 'status', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'createDate', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'arriveDate', Align : 'Center', CanSort : '0', CanEdit : '0'}
                        ]
                ,Header :{
                    CanDelete : '0', CanEdit : '0', Align : 'Center',
                    /*rowNum : LocaleUtil.getMessage('00342'),*/                            /* No */
                    taskType : LocaleUtil.getMessage('02242'),                          /*유형*/
                    title : LocaleUtil.getMessage('00756'),                             /*결재 대상명*/
                    status : LocaleUtil.getMessage('00771'),                            /*결재상태*/
                    creator : LocaleUtil.getMessage('01133'),                           /*기안자*/
                    createDate : LocaleUtil.getMessage('01335'),                        /*등록일*/
                    arriveDate : LocaleUtil.getMessage('01998')                         /*승인일*/
                },
                SelectingSingle : '0',
                Panel : {
                    Width : '21', Visible : '1',CanHide : '0'
                }
            }),
            'listCompletedWorkItemGridDiv');

            //download page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/wfm/workflow/listCompletedWorkItemData.do';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listCompletedWorkItem'
                }
            };

            //row click event
            Grids.OnClick = WorkItem.goView;
        },

        doDistribute : function(){

            var grid = Grids['listCompletedWorkItemGrid'];
            if(grid != null){
                var rows = grid.GetSelRows();
                if(rows.length == 0){
                    alert(LocaleUtil.getMessage('05133'));/*배포대상을 선택하여 주십시오.*/
                    return;
                }
                var url = "/plm/extcore/jsp/wfm/workflow/requestDistributePopup.jsp?";
                var params = "";
                for(var i = 0; i < rows.length; i++){
                    params += "pboOids=" + rows[i].pboOid + "&";
                }
                getOpenWindow2(url+params,'requestDistribute',740,550);
            }// if end
        },

        search : function(perPage){
            if(!perPage) perPage = Grids['listCompletedWorkItemGrid'].Source.Layout.Data.Cfg.PageLength;
            Grids['listCompletedWorkItemGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids['listCompletedWorkItemGrid'].Source.Data.Param.command = 'listCompletedWorkItem';
            Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
            Grids['listCompletedWorkItemGrid'].Source.Data.Param.formPage=perPage
            Grids['listCompletedWorkItemGrid'].Source.Data.Param.Pagingsize = perPage;
            Grids['listCompletedWorkItemGrid'].Reload();
        },

        goView : function(grid,row,col,x,y){
            if(row.Kind != "Header"){
                if("status" == col){
                    viewHistory(row.pboOid);
                }
                else if("title" == col){
                    
                    var pboid = row.pboOid;
                	if(pboid.indexOf("CostReport") > 0){
                		openCostReport(row.costReportOid);
                	}else{
                		openView(row.pboOid);	
                	}
                }
            }
        }
}