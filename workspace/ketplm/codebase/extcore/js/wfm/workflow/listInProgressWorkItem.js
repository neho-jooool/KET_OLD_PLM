var WorkItem = {
        /**
         * client paging grid
         */     
        createGrid : function(){
            this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
                id : 'listInProgressWorkItemGrid',
                Data : {
                    Url : '/plm/ext/wfm/workflow/listInProgressWorkItemData.do',
                    Method : 'POST',
                    Param : {
                        formPage : (Grids['listInProgressWorkItemGrid'])?Grids['listInProgressWorkItemGrid'].PageLength:CommonGrid.pageSize,
                        command : 'listInProgressWorkItem'
                    },
                    Params : decodeURIComponent($('[name=form01]').serialize())
                },
                perPageOnChange : 'javascript:WorkItem.search(Value);',
                Cols : [
                        //{Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'taskType', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'title', RelWidth: 50, Align : 'Left', CanSort : '0', CanEdit : '0'},
                        {Name : 'status', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'createDate', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'arriveDate', Align : 'Center', CanSort : '0', CanEdit : '0'},
                        {Name : 'cancelApproval', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'}
                        ],Header :{
                            CanDelete : '0', CanEdit : '0', Align : 'Center',
                            //rowNum : LocaleUtil.getMessage('00342'),/* No */
                            taskType : LocaleUtil.getMessage('02242'),                  /*유형*/
                            title : LocaleUtil.getMessage('00756'),                     /*결재 대상명*/
                            status : LocaleUtil.getMessage('00771'),                    /*결재상태*/
                            creator : LocaleUtil.getMessage('01133'),                   /*기안자*/
                            createDate : LocaleUtil.getMessage('01335'),                /*등록일*/
                            arriveDate : LocaleUtil.getMessage('05111'),                /*상신일*/
                            cancelApproval : LocaleUtil.getMessage('05106')             /*상신취소*/
                        }
            }),
            'listInProgressWorkItemGridDiv');

            //download page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/wfm/workflow/listInProgressWorkItemData.do';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listInProgressWorkItem'
                }
            };
            
            //row click event
            Grids.OnClick = WorkItem.goView;
        },

        search : function(perPage){
            if(!perPage) perPage = Grids['listInProgressWorkItemGrid'].Source.Layout.Data.Cfg.PageLength;
            Grids['listInProgressWorkItemGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids['listInProgressWorkItemGrid'].Source.Data.Param.command = 'listInProgressWorkItem';
            Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
            Grids['listInProgressWorkItemGrid'].Source.Data.Param.formPage=perPage
            Grids['listInProgressWorkItemGrid'].Source.Data.Param.Pagingsize = perPage;
            Grids['listInProgressWorkItemGrid'].Reload();
        },

        doCancelApproval : function(oid){
            if(confirm(LocaleUtil.getMessage('05155') + "\n" + LocaleUtil.getMessage('05107'))){/*상신 취소 시에는 결재선 정보가 삭제됩니다.\n상신취소 하시겠습니까?*/
                showProcessing();
                $.ajax({
                    url : "/plm/ext/wfm/workflow/doCancelApproval.do",
                    type : "POST",
                    data : {
                        pbooid : oid
                    },
                    dataType : 'json',
                    async : true,
                    success : function(msgcode) {
                        hideProcessing();
                        alert(LocaleUtil.getMessage(msgcode));
                        if(msgcode != '05109'){
//                            alert(LocaleUtil.getMessage('05108'));/*취소되었습니다*/
                            WorkItem.search();
                        }
//                        else{
//                            alert(LocaleUtil.getMessage('05109'));/*취소중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.*/
//                        }
                    }
                });
            }
        },

        goView : function(grid,row,col,x,y){
            if(row.Kind != "Header"){
                if("status" == col){
                    viewHistory(row.pboOid);
                }
                else if("title" == col){
//                    if(row.multiApproval) openView2(row.pboOid);
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