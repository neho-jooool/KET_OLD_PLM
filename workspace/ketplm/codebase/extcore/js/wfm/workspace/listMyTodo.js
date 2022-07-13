var MyTodo = {
    /**
     * server paging grid
     */
    createPaingGrid : function() {
        this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
            id : 'listMyTodoGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST',
                Format : 'Form',
                Timeout : 0,
                Param : {
                    formPage : (Grids['listMyTodoGrid']) ? Grids['listMyTodoGrid'].PageLength : CommonGrid.pageSize,
                    command : 'listMyTodo'
                }
            },
            perPageOnChange : 'javascript:MyTodo.search(Value);',
            Cols : [ {
                Name : 'taskType',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'taskName',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'title',
                RelWidth : 100,
                Align : 'Left',
                CanSort : '0',
                CanEdit : '0'
            }, /*{
                Name : 'status',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            },*/ {
                Name : 'creator',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'receiptDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'arriveDate',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0'
            }, {
                Name : 'delegateHistory',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0',
                Type : 'Html'
            } ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                taskType : LocaleUtil.getMessage('02242'), /*유형*/
                taskName : LocaleUtil.getMessage('02436'), /*작업명*/
                title : LocaleUtil.getMessage('02524'), /*제목*/
                //status : LocaleUtil.getMessage('00771'), /*결재상태*/
                creator : LocaleUtil.getMessage('01133'), /*기안자*/
                receiptDate : LocaleUtil.getMessage('05141'), /*수신일*/
                arriveDate : LocaleUtil.getMessage('01300'), /*도착일*/
                delegateHistory : LocaleUtil.getMessage('05145') /*위임이력*/
            },
            SelectingSingle : '0',
            Panel : {
                Width : '21', Visible : '1',CanHide : '0'
            }
        }), 'listMyTodoGridDiv');

        //row click event
        Grids.OnClick = MyTodo.goView;

        //download page event
        Grids.OnDownloadPage = function(grid, row) {
            grid.Data.Page.Format = 'Form';
            grid.Data.Page.Method = 'Post';
            grid.Data.Page.Url = '/plm/ext/wfm/workspace/listMyTodoData.do';
            grid.Data.Page.Param = {
                page : grid.GetPageNum(row),
                formPage : grid.PageLength,
                command : 'listMyTodo'
            }
        };

        Grids.OnRenderPageStart = function(grid, row) {
        };

        Grids.OnRenderPageFinish = function(grid) {
        	var rows = grid.Rows;
        	var rowKeys = Object.keys(rows);
        	
        	for(var i = 0; i < rowKeys.length; i++){
        		
        		var row = rows[rowKeys[i]];
        		
        		if(row.oid != null){
        			if(row.oid.indexOf("ext.ket.issue.entity") > -1 || row.oid.indexOf("ext.ket.invest.entity") > -1){
            			grid.SetAttribute(row, null, "CanSelect", "0", 1);
                	}
        		}
        	}
        };
    },
    
    doDelegateWorkItem : function(){
        var grid = Grids['listMyTodoGrid'];
        if(grid != null){
            var rows = grid.GetSelRows();
            if(rows.length == 0){
                alert(LocaleUtil.getMessage('05146'));//위임 대상을 선택하여 주십시오.
                return;
            }
            var url = "/plm/ext/wfm/workspace/delegateTodoWorkItemForm.do?";
            var workItemoids = new Array();
            var checkFlag = false;
            var alertMsg = "";
            for(var i = 0; i < rows.length; i++){
            	if((rows[i].pboOid).indexOf("ext.ket.sample.entity") > -1){
                	if(rows[i].taskName != "Sample 요청"){
                		checkFlag = true;
                		if(alertMsg != ""){
                			alertMsg += ", ";
                		}
                		alertMsg += rows[i].taskName;
                	}
                }
            	
            	if((rows[i].pboOid).indexOf("ext.ket.dqm.entity") > -1){
                	if( (rows[i].taskName != "검토담당자지정" && rows[i].taskName != "개발품질문제 검토") || rows[i].taskName != "ISSUE 검토"){
                		checkFlag = true;
                		if(alertMsg != ""){
                			alertMsg += ", ";
                		}
                		alertMsg += rows[i].taskName;
                	}
                }
            	workItemoids[workItemoids.length] = rows[i].oid;
            }
            if(checkFlag){
            	alert(alertMsg + " 작업은 위임할수 없습니다.");
            	return;
            }
            var params = decodeURIComponent($.param({workItemoids : workItemoids}, true));
            getOpenWindow2(url + params, 'delegateTodoWorkItemForm', 500, 350);
        }
    },
    
    doAcknowledgement : function(){
        var grid = Grids['listMyTodoGrid'];
        if(grid != null){
            var rows = grid.GetSelRows();
            if(rows.length == 0){
                alert(LocaleUtil.getMessage('05142'));//수신확인 대상을 선택하여 주십시오.
                return;
            }
            if(confirm(LocaleUtil.getMessage('05143'))){//수신확인 하시겠습니까?
                showProcessing();
                var url = "/plm/ext/wfm/workspace/doAcknowledgement.do";
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
                    success : function(msg) {
                        hideProcessing();
                        if(msg == ''){
                            alert(LocaleUtil.getMessage('05144'));//정상적으로 처리되었습니다.
                            MyTodo.search();
                        }
                        else{
                            alert(msg + "\n" + LocaleUtil.getMessage('05139') + "\n" + LocaleUtil.getMessage('05140'));//처리중 에러가 발생하였습니다.\n관리자에게 문의하여 주십시오.
                        }
                    }
                });
            }
        }// if end
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids[0].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids[0].Source.Data.Url = '/plm/ext/wfm/workspace/listMyTodoData.do';
        Grids[0].Source.Data.Params = decodeURIComponent($('[name=form01]').serialize());
        Grids[0].Source.Data.Param.command = 'listMyTodo';
        Grids[0].Source.Data.Param.formPage = perPage;
        Grids[0].Source.Data.Param.Pagingsize = perPage;
        Grids[0].Reload();
    },

    goView : function(grid, row, col, x, y) {
//        alert(row.oid);
        if(row.Kind != "Header"){
            if ("status" == col) {
                viewHistory(row.pboOid);
            } else if ("title" == col || "taskName" == col) {
            	if((row.oid).indexOf("ext.ket.issue.entity") > -1 || (row.oid).indexOf("ext.ket.invest.entity") > -1){
            		openView(row.oid);
            	}else if ((row.pboOid).indexOf("e3ps.ecm.entity") > -1 || (row.pboOid).indexOf("ext.ket.sample.entity") > -1  || (row.pboOid).indexOf("ext.ket.dqm.entity") > -1  || (row.pboOid).indexOf("KETEarlyWarning") > -1) {
                	if((row.pboOid).indexOf("ext.ket.dqm.entity") > -1){
                		var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=1";
                        getOpenWindow2(row.viewTaskUrl, 'ReviewTaskPopup', 1100, 768, opts);
                    }
                	else{
                		var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=1";
                        getOpenWindow2(row.viewTaskUrl, 'ReviewTaskPopup', 1024, 768, opts);
                    }
                } else {
                    getOpenWindow2(row.viewTaskUrl, 'ReviewTaskPopup', 800, 600);
                }
            }
        }
    }
}