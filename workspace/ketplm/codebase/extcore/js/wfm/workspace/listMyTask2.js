var MyTask = {
    /**
     * client paging grid
     */
    createGrid : function() {
        this.grid = TreeGrid(CommonGrid.getGridConfig({
            id : 'myTaskGrid',
            Data : {
                Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
                Method : 'POST'
            },
            Sort : 'taskPlanStartDate',
            perPageOnChange : 'javascript:MyTask.search(Value);',
            LeftCols : [
            // {Name : 'rowNum', Width:40, Align : 'Center', CanSort : '0', CanEdit : '0'},
            {
                Name : 'pjtType',
                Width : 80,
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
                RelWidth : 50,
                Align : 'Left',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'taskName',
                Width : 200,
                Align : 'Left',
                CanSort : '1',
                CanEdit : '0'
            } ],
            Cols : [ {
                Name : 'taskPlanStartDate',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'taskPlanEndDate',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            }, {
                Name : 'taskStatus',
                Align : 'Center',
                CanSort : '0',
                CanEdit : '0',
                Type : 'Html'
            }, {
                Name : 'pjtStatus',
                Align : 'Center',
                CanSort : '1',
                CanEdit : '0'
            },
            // {Name : 'taskCompletion', Align : 'Center', CanSort : '0', CanEdit : '0', Type : 'Html'}
            ],
            Header : {
                CanDelete : '0',
                CanEdit : '0',
                Align : 'Center',
                // rowNum : LocaleUtil.getMessage('00342'), /* No */
                pjtType : LocaleUtil.getMessage('00969'), /* 구분 */
                pjtNo : LocaleUtil.getMessage('00397'), /* Project No */
                pjtName : LocaleUtil.getMessage('00395'), /* Project Name */
                taskName : LocaleUtil.getMessage('00479'), /* Task */
                taskPlanStartDate : LocaleUtil.getMessage('07165', ''), /* 계획시작일 */
                taskPlanEndDate : LocaleUtil.getMessage('07166', ''), /* 계획완료일 */
                taskStatus : LocaleUtil.getMessage('03210'), /* 현황 */
                pjtStatus : LocaleUtil.getMessage('02176'), /* 완료여부 */
            // taskCompletion : LocaleUtil.getMessage('00501') /* TASK완료 */
            },
            SelectingSingle : '0',
            Panel : {
                Width : '21',
                Visible : '1',
                CanHide : '1',
                Delete : '1'
            }
        }), 'myTaskGridDiv');
        // row click event
        Grids.OnClick = MyTask.goView;
    },

    search : function(perPage) {
        if (!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
        Grids[0].Source.Layout.Data.Cfg.PageLength = perPage;
        Grids[0].Source.Data.Url = '/plm/ext/wfm/workspace/listMyTaskData.do';
        Grids[0].Source.Data.Param.command = 'listMyTask';
        Grids[0].Source.Data.Params = decodeURIComponent($('[name=myTask]').serialize());
        Grids[0].Source.Data.Param.formPage = perPage;
        Grids[0].Source.Data.Param.Pagingsize = perPage;
        Grids[0].Reload();
    },

    clear : function() {
        $('[name=myTask]')[0].reset();
        $('[name=searchType]').val('');
    },
    
    setProjectNo : function(returnValue) {
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

    selProjectNo : function() {
    	
    	SearchUtil.selectOneProjectPopUp('MyTask.setProjectNo');        
        
    },

    registerProjectOutput : function() {

        var grid = Grids['myTaskGrid'];
        if (grid != null) {
            var rows = grid.GetSelRows();
            if (rows.length == 0) {
                alert(LocaleUtil.getMessage('00495'));/* 'Task를 선택하여 주십시오' */
                return;
            }
            var flag = true;
            var tasknameArr = new Array();
            var taskoidArr = new Array();
            if (rows.length > 0) {
                for ( var int = 0; int < rows.length; int++) {
                    var row = rows[int];
                    var taskname = row.taskName;
                    tasknameArr[int] = taskname;
                    taskoidArr[int] = row.oid;
                    for ( var int2 = 0; int2 < tasknameArr.length; int2++) {
                        var array_element = tasknameArr[int2];
                        if (array_element != taskname) {
                            flag = false;
                        }
                    } // for end
                } // for end
            }// if end
            if (flag) {
                var url = '/plm/ext/wfm/workspace/registerProjectOutputPopup.do?';
                var params = '';
                for ( var int = 0; int < taskoidArr.length; int++) {
                    var taskoid = taskoidArr[int];
                    params += 'taskoids=' + taskoid + '&';
                }
                getOpenWindow2(url + params, 'registerProjectOutputPopup', 1024, 768);
                // alert(url + decodeURIComponent(params));
            } else {
                alert(LocaleUtil.getMessage('05134'));/* '동일한 Task의 산출물만 등록 가능합니다' */
                return;
            }
        }// if end
    },

    doTaskCompletion : function(taskoid) {
        var url = "/plm/ext/project/task/completeProjectTaskForm.do?oid=" + taskoid;
        getOpenWindow2(url, 'completeProjectTaskForm', 780, 590);
    },

    goView : function(grid, row, col, x, y) {
        if (row.Kind != "Header") {
            if ("taskName" == col) {
                // openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.oid+"&popup=popup", '',1150,800);
                openView(row.oid);
            } else if ("pjtNo" == col) {
                // getOpenWindow2('/plm/jsp/project/ProjectViewFrm.jsp?oid='+row.pjtOid+"&popup=popup", '',1150,800);
                openView(row.pjtOid);
            }
        }
    }
}