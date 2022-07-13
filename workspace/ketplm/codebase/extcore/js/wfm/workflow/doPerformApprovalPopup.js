var Approval = {

    complete : function(condition, masterOid, workitemOid) {
        var ok;
        var comment;
        if (condition == 'reject') {
            if(isDRR){
                ok = confirm(LocaleUtil.getMessage('01467')/*반려 하시겠습니까?*/);
            }else{
                ok = confirm(LocaleUtil.getMessage('01467')/*반려 하시겠습니까?*/ + "\n(" + LocaleUtil.getMessage('00784') + ")");/*'결재의견은 필수 입력입니다'*/
            }
            comment = document.forms[0].acomment.value;
        } else if (condition == 'cancel') {
            ok = confirm(LocaleUtil.getMessage('05157'));/*검토취소 하시겠습니까?*/
        } else if (condition == 'accept') {
            ok = confirm(LocaleUtil.getMessage('01989'));/*승인 하시겠습니까?*/
        } else if (condition == 'conAccept') {
            ok = confirm(LocaleUtil.getMessage('05156'));/*조건부승인 하시겠습니까?*/
        } else if (condition == 'update') {
            ok = confirm(LocaleUtil.getMessage('01944'));/*수정 후 결재 요청 하시겠습니까?*/
        } else if (condition == 'taskComplete') {
            ok = confirm(LocaleUtil.getMessage('01979'));/*수행작업이 완료되었습니까?*/
        } else
            ok = true;
        if (ok) {
            if (!comment && condition == 'reject') {
                alert(LocaleUtil.getMessage('00784'));/*'결재의견은 필수 입력입니다'*/
                return;
            }
            document.forms[0].method = "post";
            if (condition == 'update') {
                document.forms[0].action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=updateRestart&master=" + masterOid + "&item=" + workitemOid;
            } else {
                document.forms[0].action = "/plm/servlet/e3ps/CommonWorkflowServlet?cmd=complete&item=" + workitemOid + "&master=" + masterOid + "&condition=" + condition;
            }
            document.forms[0].submit();
        }
    },

    doViewDistribute : function(oid) {

        var url = "/plm/jsp/common/loading.jsp?url=/plm/ext/wfm/workflow/doViewDistributePopup.do&key=oid&value=" + oid;
        getOpenWindow2(url, "ViewDistributePopup", 1024, 600);
    },
    HistoryChangeView : function(oid){
        var url = "/plm/jsp/project/HistoryChange.jsp?oid=" + oid + "&from=ChangeSchedule&mode=view";
        setTimeout( function() { window.open(url, "HistoryChangeView", "status=1, menu=no, width=770, height=660, scrollbars=yes, resizable=no"); }, 20);
    },
    doDelegateWorkItem : function(workItemoid) {
        var url = "/plm/ext/wfm/workflow/delegateWorkItemForm.do?";
        var workItemoids = new Array();
        workItemoids[workItemoids.length] = workItemoid
        var data = decodeURIComponent($.param({
            workItemoids : workItemoids
        }, true));
        getOpenWindow2(url + data, 'delegateWorkItemForm', 500, 400);
    },
}