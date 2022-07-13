var WorkItem = {
        /**
         * server paging grid
         */
        createPaingGrid : function(){
            this.grid = TreeGrid(
                    CommonGrid.getPagingGridConfig({
                        id : 'listWorkItemGrid',
                        Data : {
                            Url : '/plm/ext/wfm/workflow/listWorkItemData.do',
                            Method : 'POST',
                            Format : 'Form',
                            Timeout : 0,
                            Param : {
                                formPage : (Grids['listWorkItemGrid'])?Grids['listWorkItemGrid'].PageLength:CommonGrid.pageSize,
                                        command : 'listWorkItem'
                            },
                            Params : decodeURIComponent($('[name=form01]').serialize())
                        },
                        perPageOnChange : 'javascript:WorkItem.search(Value);',
                        Cols : [
                                {Name : 'taskType', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'taskName', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'title', RelWidth : 100, Align : 'Left', CanSort : '0', CanEdit : '0'},
                                {Name : 'status', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'creator', Align : 'Center', CanSort : '0', CanEdit : '0'},
                                {Name : 'arriveDate', Align : 'Center', CanSort : '0', CanEdit : '0'}
                                ]
                        ,Header :{
                            CanDelete : '0', CanEdit : '0', Align : 'Center',
                            taskType : LocaleUtil.getMessage('02242'),/*유형*/
                            taskName : LocaleUtil.getMessage('02436'),/*작업명*/
                            title : LocaleUtil.getMessage('00756'),/*결재 대상명*/
                            status : LocaleUtil.getMessage('00771'),/*결재상태*/
                            creator : LocaleUtil.getMessage('01133'),/*기안자*/
                            arriveDate : LocaleUtil.getMessage('01300')/*도착일*/
                        },
                        SelectingSingle : '0',
                        Panel : {
                            Width : '21', Visible : '1', CanHide : '1', Delete : '1'
                        }
                    }), 'listWorkItemGridDiv');

            //row click event
            Grids.OnClick = WorkItem.goView;

            //download page event
            Grids.OnDownloadPage = function(grid,row){
                grid.Data.Page.Format = 'Form';
                grid.Data.Page.Method = 'Post';
                grid.Data.Page.Url = '/plm/ext/wfm/workflow/listWorkItemData.do';
                grid.Data.Page.Param = {
                        page : grid.GetPageNum(row),
                        formPage : grid.PageLength,
                        command : 'listWorkItem'
                }
            };
            
            Grids.OnReload = function(grid, row){
                $('#mainDiv').loadMask("Loading"); 
            };

            Grids.OnRenderFinish = function(grid, row){
                $('#mainDiv').unLoadMask();
                //parent.Ext.getCmp('workspace').getStore().reload();
            };

        },

        search : function(perPage){
            if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
            Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
            Grids[0].Source.Data.Url= '/plm/ext/wfm/workflow/listWorkItemData.do';
            Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
            Grids[0].Source.Data.Param.command = 'listWorkItem';
            Grids[0].Source.Data.Param.formPage=perPage;
            Grids[0].Source.Data.Param.Pagingsize = perPage;
            Grids[0].Reload();
        },

        doBatchCompleteWorkItem : function(){
            var grid = Grids[0];
            if(grid != null){
                var rows = grid.GetSelRows();
                if(rows.length == 0){
                    alert(LocaleUtil.getMessage('05130'));//결재대상을 선택하여 주십시오
                    return;
                }
                var flag = false;
                for(var i = 0; i < rows.length; i++){
                    if(rows[i].taskName == '반려확인'){
                        flag = true;
                        break;
                    }
                    else if(rows[i].taskName == '수신확인'){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    alert(LocaleUtil.getMessage('05162')); // 반려확인 업무는 일괄승인 할 수 없습니다.
                    return;
                }

                var url = "/plm/ext/wfm/workflow/doBatchCompleteWorkItemForm.do?";
                var workItemoids = new Array();
                for(var i = 0; i < rows.length; i++){
                    workItemoids[workItemoids.length] = rows[i].oid;
                }
                var params = decodeURIComponent($.param({workItemoids : workItemoids}, true));
                getOpenWindow2(url + params, 'doBatchCompleteWorkItemForm', 500, 260);
                
                /*if(confirm(LocaleUtil.getMessage('05131'))){//일괄승인 하시겠습니까?
                    showProcessing();
                    var url = "/plm/ext/wfm/workflow/doBatchCompleteWorkItem.do";
                    var workItemoids = new Array();
                    for(var i = 0; i < rows.length; i++){
                        workItemoids[workItemoids.length] = rows[i].oid;
                    }
                    $.ajax({
                        url : url,
                        type : "POST",
                        data : decodeURIComponent($.param({workItemoids : workItemoids}, true)),
                        dataType : 'json',
                        async : false,
                        success : function(flag) {
                            hideProcessing();
                            if(flag){
                                alert(LocaleUtil.getMessage('05132'));//정상적으로 승인 되었습니다.
                                WorkItem.search();
                            }
                            else{
                                alert(LocaleUtil.getMessage('05139') + "\n" + LocaleUtil.getMessage('05140'));//처리중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.
                            }
                        }
                    });
                    // menu reload
                    parent.Ext.getCmp('workspace').getStore().reload();
                }*/
            }// if end
        },
        
        doDelegateWorkItem : function(){
            var grid = Grids[0];
            if(grid != null){
                var rows = grid.GetSelRows();
                if(rows.length == 0){
                    alert('위임대상을 선택하여 주십시오');
                    return;
                }
                else if(rows.length > 1){
                    alert('한 건만 선택하여 주십시오');
                    return;
                }
                var url = "/plm/ext/wfm/workflow/delegateWorkItemForm.do?";
                var workItemoids = new Array();
                for(var i = 0; i < rows.length; i++){
                    workItemoids[workItemoids.length] = rows[i].oid;
                }
                var data = decodeURIComponent($.param({workItemoids : workItemoids}, true));
//                alert(url+data);
                getOpenWindow2(url+data,'delegateWorkItemForm',500,200);
            }
        },

        goView : function(grid,row,col,x,y){
            if(row.Kind != "Header"){
                if("status" == col){
                    viewHistory(row.pboOid);
                }
                else if("title" == col || "taskName" == col){
//                    if((row.pboOid).indexOf("e3ps.ecm.entity") > -1) {
//                        var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=1";
//                        getOpenWindow2(row.viewTaskUrl,'ReviewTaskPopup',1024,768, opts);
//
//                    } else {
                        //getOpenWindow2(row.viewTaskUrl,'ReviewTaskPopup',1100,900);
                		  
                		WorkItem.aprrovalOpenWindow2(row.viewTaskUrl,'ReviewTaskPopup','full',800);
//                    }

                }
            }
        },
        
        aprrovalOpenWindow2 : function (url, name, width, height, opts) {
        	var opts = (typeof opts == "undefined") ? "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1" : opts;
            if (width == 'full') {
                // rest = "width=" + (screen.availWidth-10) + ",height=" + (screen.availHeight-60)+',left=0,top=0';

                leftpos = (screen.availWidth - screen.availWidth) / 2;
                toppos = (screen.availHeight - screen.availHeight) / 2;

                rest = ",width=" + (screen.availWidth) + ",height=" + (screen.availHeight) + ',left=' + leftpos + ',top=' + toppos;
            } else {
                leftpos = (screen.availWidth - width) / 2;
                toppos = (screen.availHeight - 60 - height) / 2;

                rest = ",width=" + width + ",height=" + height + ',left=' + leftpos + ',top=' + toppos;
            }
            
            var newname = changePopupNameForIE9(name);
            var newwin = open(url, newname, (rest + opts));
            newwin.focus();
            return newwin;
        }
}