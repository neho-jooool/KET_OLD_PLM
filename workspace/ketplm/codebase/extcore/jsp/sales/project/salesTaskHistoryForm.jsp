<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style>
.dragRow {
    background-color: #EDFFE7;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/sales/project/salesCSProject.js"></script>
<script type="text/javascript">
var year;
var mon;

<!--
$(document).ready(function(){
    window.focus();
})
//-->

</script>
<form name="SalesCSForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="${sales.oid}" />
<input type="hidden" name="year" value="${sales.year}" />
<input type="hidden" name="month" value="${sales.month}" />
<input type="hidden" name="degree" value="${sales.degree}" />
    <div class="contents-wrap">
        <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                        <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">프로젝트 Step 이력관리</td>
                        </tr>
                    </table>
                </td>
                <td width="136" align="right"><img
                    src="/plm/portal/images/logo_popup.png"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
        
        <div class="float-r" style="text-align: right">
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue">닫기</a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <c:forEach items="${sales.salesTaskList}" var="taskList" varStatus="status">
        <div id="title" class="sub-title-02 b-space15 clear">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" />
            ${taskList.version} 버전
            </span>
        </div>
        <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                <tr>
                    <td class="tdblueL">진행현황</td>
                    <td  class="tdblueL">문제점</td>
                    <td  class="tdblueL">해결방안</td>
                </tr>
                <tr>
                    <td valign="top" class="tdwhiteL">
                        <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${taskList.PropelwebEditor}</div>
                    </td>
                    <td valign="top" class="tdwhiteL">
                        <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${taskList.ProblemwebEditor}</div>
                    </td>
                    <td valign="top" class="tdwhiteL">
                        <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${taskList.planwebEditor}</div>
                    </td>
                </tr>
            </table>
        </div>
        </c:forEach>
    </div>
</form>