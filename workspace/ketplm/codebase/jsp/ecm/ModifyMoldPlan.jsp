<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Untitled Document</title>
<%@ page import="wt.org.WTUser
                            ,wt.fc.QueryResult
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ContentRoleType
                            ,wt.content.ApplicationData
                            ,wt.content.ContentItem
                            ,wt.session.SessionHelper
                            ,wt.fc.PersistenceHelper" %>
<%@ page import= "java.util.Vector
                             ,java.net.URL" %>
<%@ page import= "e3ps.dms.entity.KETProjectDocument
                             ,e3ps.common.util.DateUtil
                             ,e3ps.common.util.StringUtil
                             ,e3ps.ecm.entity.KETMoldPlanRefDocLink
                             ,e3ps.common.util.CommonUtil
                             ,e3ps.common.content.ContentUtil
                             ,e3ps.common.content.ContentInfo
                             ,e3ps.ecm.beans.EcmSearchHelper
                             ,e3ps.groupware.company.*
                             ,e3ps.common.code.NumberCodeHelper"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="plan" class="e3ps.ecm.entity.KETMoldChangePlan" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%

    QueryResult linkQr = null;
    QueryResult itemQr = null;

    linkQr = PersistenceHelper.manager.navigate( plan, "refDoc", KETMoldPlanRefDocLink.class );

    Vector attachFileList = ContentUtil.getSecondaryContents( plan );

    String currentProcess = StringUtil.checkNull(plan.getCurrentProcess());

    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)plan.getOwner().getPrincipal();

    boolean isOwner = false;
    if( owner.equals( loginUser ) )
    {
        isOwner = true;
    }

    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin

    boolean isProd = false;
    boolean isMold = false;
    if(loginUser.getFullName().equals( plan.getProdEcoOwner())){
        isProd = true;
    }
    if(loginUser.getFullName().equals( plan.getMoldEcoOwner())){
        isMold = true;
    }

    People people = PeopleHelper.manager.getPeople(loginUser.getName());
    String DeptName = "";
    if ( people.getDepartment() != null ) {
        DeptName = people.getDepartment().getName();
    }

    boolean prod_Auth = false;
    boolean mold_Auth = false;
    boolean store_Auth = false;
    boolean work_Auth = false;
    boolean ass_Auth = false;
    boolean try_Auth = false;
    boolean test_Auth = false;
    boolean approve_Auth = false;

    if(DeptName.equals("제품개발1팀") || DeptName.equals("제품개발2팀") || DeptName.equals("제품개발3팀") || DeptName.equals("제품개발4팀")){
        prod_Auth = true;
    }

    if(DeptName.equals("사출금형개발팀") || DeptName.equals("프레스금형개발팀") ){
        mold_Auth     = true;
        approve_Auth = true;
    }

    if(DeptName.equals("금형수리팀") || DeptName.equals("생산관리팀") || DeptName.equals("사출금형개발팀") || DeptName.equals("프레스금형개발팀") ){
        store_Auth = true;
    }

    if(DeptName.equals("금형부품팀") || DeptName.equals("금형수리팀") || DeptName.equals("구매2팀") ){
        work_Auth = true;
    }

    if(DeptName.equals("사출금형개발팀") || DeptName.equals("프레스금형개발팀") || DeptName.equals("금형수리팀") || DeptName.equals("생산1팀") || DeptName.equals("생산2팀") || DeptName.equals("생산3팀") ){
        ass_Auth = true;
    }

    if(DeptName.equals("초류관리팀") || DeptName.equals("생산1팀") || DeptName.equals("생산2팀") || DeptName.equals("생산3팀") || DeptName.equals("사출금형개발팀") || DeptName.equals("프레스금형개발팀")){
        try_Auth = true;
    }

    if(DeptName.equals("나노정밀") ){
        test_Auth = true;
    }


%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language="javascript" src="/plm/portal/js/org.js"></script>
<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>

<script type="text/javascript">
<!--
function load()
{
    var completed = "<%=StringUtil.checkNull(plan.getScheduleStatus() ) %>";

    var boolAdmin = <%=isAdmin%>;
    var boolMember = <%=CommonUtil.isMember("Try제작관리") %>;
    var isOwner = <%=isOwner%>;

    addSelectBox( document.forms[0].type, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%=StringUtil.checkNull(plan.getPlanType())%>" );
    addSelectBox( document.forms[0].type, "<%=messageService.getString("e3ps.message.ket_message", "00693") %><%--개조--%>", "개조", "<%=StringUtil.checkNull(plan.getPlanType())%>" );
    addSelectBox( document.forms[0].type, "<%=messageService.getString("e3ps.message.ket_message", "00676") %><%--개선--%>", "개선", "<%=StringUtil.checkNull(plan.getPlanType())%>");

    addSelectBox( document.forms[0].vendor_flag, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%=StringUtil.checkNull(plan.getVendorFlag())%>");
    addSelectBox( document.forms[0].vendor_flag, "<%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%>", "i", "<%=StringUtil.checkNull(plan.getVendorFlag())%>");
    addSelectBox( document.forms[0].vendor_flag, "<%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%>", "o", "<%=StringUtil.checkNull(plan.getVendorFlag())%>");

    addSelectBox( document.forms[0].reg_basis, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%=StringUtil.checkNull(plan.getRegBasis())%>");
    addSelectBox( document.forms[0].reg_basis, "<%=messageService.getString("e3ps.message.ket_message", "03251") %><%--회의록--%>", "회의록", "<%=StringUtil.checkNull(plan.getRegBasis())%>");
    addSelectBox( document.forms[0].reg_basis, "<%=messageService.getString("e3ps.message.ket_message", "03219") %><%--협조전--%>", "협조전", "<%=StringUtil.checkNull(plan.getRegBasis())%>");
    addSelectBox( document.forms[0].reg_basis, "<%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>", "기타", "<%=StringUtil.checkNull(plan.getRegBasis())%>");

    addSelectBox( document.forms[0].m_customer, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%=StringUtil.checkNull(plan.getAttribute1())%>");
    addSelectBox( document.forms[0].m_customer, "Yes", "Yes", "<%=StringUtil.checkNull(plan.getAttribute1())%>");
    addSelectBox( document.forms[0].m_customer, "No", "No", "<%=StringUtil.checkNull(plan.getAttribute1())%>");

    if(document.forms[0].m_customer.value != 'Yes'){
        document.getElementById('m_date_td').style.display = "none";
    }


    if( completed != "" ){
        addSelectBox( document.forms[0].measure_type, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%= StringUtil.checkNull(plan.getMeasureType())%>" );
        if( document.forms[0].currentProcess.value == '제품측정' || document.forms[0].currentProcess.value == '검토협의'
        || ( document.forms[0].try_actual_date.value != "" && document.forms[0].currentProcess.value == "" )
        || ( document.forms[0].approve_actual_date.value != "" && document.forms[0].currentProcess.value == "" ) )
        {
            addSelectBox( document.forms[0].measure_type, "<%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%>", "합격", "<%= StringUtil.checkNull(plan.getMeasureType())%>" );
            addSelectBox( document.forms[0].measure_type, "<%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%>", "불합격", "<%= StringUtil.checkNull(plan.getMeasureType())%>" );
            for(var i=0; i<document.getElementById('measure_type').childNodes.length; i++) {
                var oDiv = document.getElementById('measure_type').childNodes[i];
                if(oDiv.value == "불합격"){
                    oDiv.style.color ="red";
                }
                if(oDiv.value == "합격"){
                    oDiv.style.color ="blue";
                }
            }
        }

        addSelectBox( document.forms[0].fail_action, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%= StringUtil.checkNull(plan.getFailAction())%>" );
        if( document.forms[0].currentProcess.value == '제품측정' || document.forms[0].currentProcess.value == '검토협의'
        || ( document.forms[0].try_actual_date.value != "" && document.forms[0].currentProcess.value == "" )
        || ( document.forms[0].approve_actual_date.value != "" && document.forms[0].currentProcess.value == "" ) )
        {
            addSelectBox( document.forms[0].fail_action, "<%=messageService.getString("e3ps.message.ket_message", "00737") %><%--검토협의--%>", "검토협의", "<%= StringUtil.checkNull(plan.getFailAction())%>" );
            addSelectBox( document.forms[0].fail_action, "<%=messageService.getString("e3ps.message.ket_message", "02369") %><%--일정재수립--%>", "일정재수립", "<%= StringUtil.checkNull(plan.getFailAction())%>" );
        }

        addSelectBox( document.forms[0].result, "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>", "", "<%=StringUtil.checkNull(plan.getResult())%>" );
        if( document.forms[0].currentProcess.value == '제품측정' || document.forms[0].currentProcess.value == '검토협의'
        || ( document.forms[0].try_actual_date.value != "" && document.forms[0].currentProcess.value == "" )
        || ( document.forms[0].approve_actual_date.value != "" && document.forms[0].currentProcess.value == "" ) )
        {
            addSelectBox( document.forms[0].result, "<%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%>", "합격", "<%=StringUtil.checkNull(plan.getResult())%>" );
            addSelectBox( document.forms[0].result, "<%=messageService.getString("e3ps.message.ket_message", "02369") %><%--일정재수립--%>", "일정재수립", "<%=StringUtil.checkNull(plan.getResult())%>" );
            for(var i=0; i<document.getElementById('result').childNodes.length; i++) {
                var oDiv = document.getElementById('result').childNodes[i];
                if(oDiv.value == "합격"){
                    oDiv.style.color ="blue";
                }
            }
        }
    }

    if(boolAdmin && completed != ""){
        document.getElementById('productDay1').style.display = "block";
        document.getElementById('productDay2').style.display = "block";
    }

    if( !boolAdmin && completed != "" ){
        document.forms[0].prod_plan_date[0].disabled = true;
        document.forms[0].mold_eco_plan_date[0].disabled = true;
        document.forms[0].store_plan_date[0].disabled = true;
        document.forms[0].work_plan_date[0].disabled = true;
        document.forms[0].ass_plan_date[0].disabled = true;
        document.forms[0].try_plan_date[0].disabled = true;
        document.forms[0].test_plan_date[0].disabled = true;
        document.forms[0].approve_plan_date[0].disabled = true;

        var delimg = document.getElementsByName("icon_delete");
        for( var i=0; i < delimg.length ; i++ )
        {
            document.getElementsByName("img_prod_plan_date")[0].style.display = "none";
            document.getElementsByName("img_mold_eco_plan_date")[0].style.display = "none";
            document.getElementsByName("img_store_plan_date")[0].style.display = "none";
            document.getElementsByName("img_work_plan_date")[0].style.display = "none";
            document.getElementsByName("img_ass_plan_date")[0].style.display = "none";
            document.getElementsByName("img_try_plan_date")[0].style.display = "none";
            document.getElementsByName("img_test_plan_date")[0].style.display = "none";
            document.getElementsByName("img_approve_plan_date")[0].style.display = "none";
            delimg[i].style.display = "none";
        }

        if( document.forms[0].result.value=="합격" ){
            document.getElementById('productDay1').style.display = "block";
            document.getElementById('productDay2').style.display = "block";
        }else{
            document.forms[0].measure_date.value="";
        }
    }

    if(!boolAdmin){

        var prod_Auth  =  <%=prod_Auth%>;
        var mold_Auth  =  <%=mold_Auth%>;
        var store_Auth  =  <%=store_Auth%>;
        var work_Auth  =   <%=work_Auth%>;
        var ass_Auth      =  <%=ass_Auth%>;
        var try_Auth        =  <%=try_Auth%>;
        var test_Auth      =  <%=test_Auth%>;
        var approve_Auth =  <%=approve_Auth%>;

        if(!prod_Auth && completed != ""){
            document.forms[0].prod_actual_date.disabled = true;
            document.getElementsByName("img_prod_plan_date")[1].style.display = "none";
        }

        if(!mold_Auth && completed != ""){
            document.forms[0].mold_eco_actual_date.disabled = true;
            document.getElementsByName("img_mold_eco_plan_date")[1].style.display = "none";
        }

        if(!store_Auth && completed != ""){
            document.forms[0].store_actual_date.disabled = true;
            document.getElementsByName("img_store_plan_date")[1].style.display = "none";
        }

        if(!work_Auth && completed != ""){
            document.forms[0].work_actual_date.disabled = true;
            document.getElementsByName("img_work_plan_date")[1].style.display = "none";
        }

        if(!ass_Auth && completed != ""){
            document.forms[0].ass_actual_date.disabled = true;
            document.getElementsByName("img_ass_plan_date")[1].style.display = "none";
        }

        if(!try_Auth && completed != ""){
            document.forms[0].try_actual_date.disabled = true;
            document.getElementsByName("img_try_plan_date")[1].style.display = "none";
        }

        if(!test_Auth && completed != ""){
            document.forms[0].test_actual_date.disabled = true;
            document.getElementsByName("img_test_plan_date")[1].style.display = "none";
        }

        if(!approve_Auth  && completed != ""){
            document.forms[0].approve_actual_date.disabled = true;
            document.getElementsByName("img_approve_plan_date")[1].style.display = "none";
        }
        /*
        if(!isOwner || completed != ''){
            document.forms[0].store_plan_date[0].disabled = true;
            document.getElementsByName("img_store_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[2].style.display = "none";

            document.forms[0].work_plan_date[0].disabled = true;
            document.getElementsByName("img_work_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[3].style.display = "none";

            document.forms[0].ass_plan_date[0].disabled = true;
            document.getElementsByName("img_ass_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[4].style.display = "none";
            document.forms[0].try_plan_date[0].disabled = true;
            document.getElementsByName("img_try_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[5].style.display = "none";

            document.forms[0].test_plan_date[0].disabled = true;
            document.getElementsByName("img_test_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[6].style.display = "none";

            document.forms[0].approve_plan_date[0].disabled = true;
            document.getElementsByName("img_approve_plan_date")[0].style.display = "none";
            document.getElementsByName("icon_delete")[7].style.display = "none";
        }*/

    }

/*
    if( document.forms[0].prod_plan_date.value == '' )
    {
        deletePlan('prod_plan_date');
    }
    if( document.forms[0].mold_eco_plan_date.value == '' )
    {
        deletePlan('mold_eco_plan_date');
    }
    if( document.forms[0].store_plan_date.value == '' )
    {
        deletePlan('store_plan_date');
    }
    if( document.forms[0].work_plan_date.value == '' )
    {
        deletePlan('work_plan_date');
    }
    if( document.forms[0].ass_plan_date.value == '' )
    {
        deletePlan('ass_plan_date');
    }
    if( document.forms[0].try_plan_date.value == '' )
    {
        deletePlan('try_plan_date');
    }
    if( document.forms[0].test_plan_date.value == '' )
    {
        deletePlan('test_plan_date');
    }
    if( document.forms[0].approve_plan_date.value == '' )
    {
        deletePlan('approve_plan_date');
    }
*/
}
    //첨부파일 관련 스크립트 시작
    function insertFileLine()
    {
        var innerRow = fileTable.insertRow();
        var innerCell;

        var filePath = "filePath";
        var filehtml = "";

        for( var k=0 ; k < 2 ; k++ )
        {
            innerCell = innerRow.insertCell();
            innerCell.height = "23";

            if (k == 0)
            {
                    innerCell.className = "tdwhiteM";
                     innerCell.innerHTML = "<input name='fileSelect' type='checkbox' class='chkbox' value=''>";
            }
            else if (k == 1)
            {
                    innerCell.className = "tdwhiteL0";
                    innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:99%'>";
            }
        }
    }

    function allCheck( isChecked )
    {
        var checkedList = document.getElementsByName("fileSelect");

        for( var i=0; i < checkedList.length ; i++ )
        {
            checkedList[i].checked = isChecked;
        }
    }

    function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
    {
        var body = document.getElementById(tableElementId);
        if (body.rows.length == 0) return;
        var formNameStr = "document." + formName + ".";
        var objChecks = eval(formNameStr + checkboxName);
        var objAllChecks = eval(formNameStr + allCheckName);

        var listVal = "";
        var objList;
        if(listVarName != "") {
            objList = eval(formNameStr + listVarName);
            listVal = objList.value;
        }

        if (body.rows.length == 1) {
            if (objChecks.checked || objChecks[0].checked) {
                if(objChecks.checked) {
                    if(listVal == "") {
                        listVal = objChecks.value;
                    } else {
                        listVal += "*" + objChecks.value;
                    }
                } else if(objChecks[0].checked) {
                    if(listVal == "") {
                        listVal = objChecks[0].value;
                    } else {
                        listVal += "*" + objChecks[0].value;
                    }
                }
                body.deleteRow(0);
            }
        } else {
            for (var i = body.rows.length; i > 0; i--) {
                if (objChecks[i-1].checked) {
                    if(listVal == "") {
                        listVal = objChecks[i-1].value;
                    } else {
                        listVal += "*" + objChecks[i-1].value;
                    }
                    body.deleteRow(i - 1);
                }
            }
        }
        if (body.rows.length < 1) {
            objAllChecks.checked = false;
        }
        if(listVarName != "") {
            objList.value = listVal;
        }
}

    function check()
    {
        var form = document.forms[0];
        var isChecked = true;
        var boolAdmin = <%=isAdmin%>;
        var date = '';
        var sYear = '';
        var sDate = '';
        var completed = "<%=StringUtil.checkNull(plan.getScheduleStatus() ) %>";

        if( form.die_no.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>");
        }else if( form.type.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00972") %><%--구분을 선택해 주십시오--%>");
        }else if( form.reg_basis.value == '' )
        {
             isChecked = false;
             alert('<%=messageService.getString("e3ps.message.ket_message", "01323") %><%--등록근거를 선택하세요--%>');
        }else if( form.m_customer.value == '' )
        {
             isChecked = false;
             alert("<%=messageService.getString("e3ps.message.ket_message", "00841") %><%--고객사4M을 선택하세요--%>");
        }
        if(completed != ""){

            if( form.currentProcess.value =='제품측정' && form.test_actual_date.value !='' && form.measure_type.value == '' )
            {
                isChecked = false;
                 alert('<%=messageService.getString("e3ps.message.ket_message", "02900") %><%--측정구분을 선택하세요--%>');
            }
            else if(form.approve_plan_date.value != ''  && form.fail_action.value == '검토협의'  && form.measure_type.value == '불합격')
            {
                if( form.result.value == '' && form.approve_actual_date.value != '' )
                {
                    isChecked = false;
                     alert("<%=messageService.getString("e3ps.message.ket_message", "00753") %><%--결과를 선택하세요--%>");
                }else if(form.result.value !=  ''  && form.approve_actual_date.value == ''){
                    isChecked = false;
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00738") %><%--검토협의 실적일자를 입력하세요--%>");
                }else if( form.result.value == '합격' && form.measure_date.value == '' ){
                    isChecked = false;
                     alert('<%=messageService.getString("e3ps.message.ket_message", "02626") %><%--제품측정일을 선택하세요--%>');
                }
            }
        }

        if( !boolAdmin && completed != "")
        {
            date = new Date();
            sYear = date.getFullYear();
            sMonth = date.getMonth() + 1;
            sDate = date.getDate();

            sMonth = sMonth > 9 ? sMonth : "0" + sMonth;
            sDate  = sDate > 9 ? sDate : "0" + sDate;
            date = sYear +'-' + sMonth +'-'+ sDate;

            if(form.siljuk_date1.value != form.prod_actual_date.value && form.prod_actual_date.value != date &&  form.prod_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02053") %><%--실적일자(제품도) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date2.value != form.mold_eco_actual_date.value && form.mold_eco_actual_date.value != date &&  form.mold_eco_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02050") %><%--실적일자(금형설계) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date3.value != form.store_actual_date.value && form.store_actual_date.value != date &&  form.store_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02051") %><%--실적일자(금형입고) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date4.value != form.work_actual_date.value && form.work_actual_date.value != date &&  form.work_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02049") %><%--실적일자(금형부품) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date5.value != form.ass_actual_date.value && form.ass_actual_date.value != date &&  form.ass_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02052") %><%--실적일자(금형조립) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date6.value != form.try_actual_date.value && form.try_actual_date.value != date &&  form.try_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02048") %><%--실적일자(금형Try) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date7.value != form.test_actual_date.value && form.test_actual_date.value != date &&  form.test_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02054") %><%--실적일자(제품측정) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

            if(form.siljuk_date8.value != form.approve_actual_date.value && form.approve_actual_date.value != date &&  form.approve_actual_date.value != ''){
                isChecked = false;
                alert('<%=messageService.getString("e3ps.message.ket_message", "02047") %><%--실적일자(검토협의) 입력시 오늘일자만 허용됩니다--%>');
                return isChecked;
            }

        }
        return isChecked;
    }

    function save()
    {
        var form = document.ModifyPlanForm;

        if( check() )
        {
            document.forms[0].type.disabled = false;
            document.forms[0].basis_date.disabled = false;
            document.forms[0].vendor_flag.disabled = false;
            document.forms[0].m_customer.disabled = false;
            document.forms[0].reg_basis.disabled = false;
            document.forms[0].reg_basis.disabled = false;

            <%if(!"".equals(StringUtil.checkNull(plan.getScheduleStatus())) ) {%>
                document.forms[0].prod_actual_date.disabled = false;
                document.forms[0].mold_eco_actual_date.disabled = false;
                document.forms[0].store_actual_date.disabled = false;
                document.forms[0].work_actual_date.disabled = false;
                document.forms[0].ass_actual_date.disabled = false;
                document.forms[0].try_actual_date.disabled = false;
                document.forms[0].test_actual_date.disabled = false;
                document.forms[0].approve_actual_date.disabled = false;
            <%}%>

            form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
            form.submit();
        }

    }

    function popupPart(type, fncall)
    {
        var url = "/plm/ext/part/base/listPartPopup.do?mode=one&pType="+type+"&fncall="+fncall;
        openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
    }

    function selectDieNo( objArr )
    {
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].die_no.value = trArr[1];
            document.forms[0].part_no.value = trArr[5];
            document.forms[0].part_name.value = trArr[6];
        }
    }

    function selectDieNo( objArr )
    {
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].die_no.value = trArr[1];

            if( trArr[5] != "" && trArr[6] != "" )
            {
                document.forms[0].part_no.value = trArr[5];
                document.forms[0].part_name.value = trArr[6];
            }
        }
    }

    function selectPartNo( objArr )
    {
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].part_no.value = trArr[1];
            document.forms[0].part_name.value = trArr[2];

            if( trArr[5] != "" )
            {
                document.forms[0].die_no.value = trArr[5];
            }
        }
    }

    function deletePartNo( flag )
    {
        if( flag == 'D' )
        {
            document.forms[0].die_no.value = '';
        }
        else
        {
            document.forms[0].part_no.value = '';
            document.forms[0].part_name.value = '';
        }
    }

    function popupDoc()
    {
        var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog";
        attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:500px; center:yes");
        if(typeof attache == "undefined" || attache == null) {
            return;
        }
        setDoc(attache);
    }

    function duplicateCheck(poid) {
        var tBody = document.getElementById("docTable");
        var rowsLen = tBody.rows.length;
        if(rowsLen > 0) {
            var primaryPart = document.forms[0].docOid;
            var partLen = primaryPart.length;
            if(partLen > 0 ) {
                for(var i = 0; i < partLen; i++) {
                    if(primaryPart[i].value == poid) {
                        return true;
                    }
                }
            } else {
                if(primaryPart.value == poid) {
                    return true;
                }
            }
        }
        return false;
    }

    function setDoc( objArr )
    {
        var trArr;
        var targetTable = document.getElementById("docTable");

        if(objArr.length == 0)
        {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            if( !duplicateCheck(trArr[0]) )
            {
                var tableRows = targetTable.rows.length;
                var newTr = targetTable.insertRow(tableRows);

                //전체선택 checkbox
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "40";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = "<input name='chkSelectDoc' type='checkbox' value=''>";

                //문서명
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "369";
                newTd.className = "tdwhiteL";
                newTd.innerHTML = trArr[2];
                newTd.innerHTML += "<input type='hidden' name='docOid' value='"+trArr[0]+"'>";

                //작성부서
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "149";
                newTd.className = "tdwhiteM";
                newTd.innerHTML = trArr[4];

                //작성자
                newTd = newTr.insertCell(newTr.cells.length);
                newTd.width = "131";
                newTd.className = "tdwhiteM0";
                newTd.innerHTML = trArr[5];
            }
        }
    }

    //부서 검색 팝업  Open
function selectDepartment() {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, acceptDepartment);
}

function acceptDepartment(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_oid = showElements[0].getElementsByTagName("l_oid");

    document.forms[0].prod_dept_name.value = decodeURIComponent(l_name[0].text);
    //document.forms[0].orgOid.value = decodeURIComponent(l_oid[0].text);
}

//생산처 검색 팝업
function popupVendor(){
    var form = document.forms[0];
    if(form.vendor_flag.value == "i") {//사내
        selectInnerDepartment();
    } else {//외주
        selectPartner();
    }
}

//생산처 초기화
function clearVendor(){
    var form = document.forms[0];
    form.vendor_code.value = '';
    form.prodVendorName.value = '';
}

//협력사검색 팝업 Open
function selectPartner() {
    var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
    openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
    if(arr.length == 0) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
        return;
    }
    var form = document.forms[0];
    form.vendor_code.value = arr[0][0];
    form.vendor_name.value = arr[0][1];
}

//사내생산처 검색 팝업  Open
function selectInnerDepartment() {
    var mode = "single";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    linkDept(returnValue);
}

//사내생산처 검색결과 셋팅
function linkDept(arr){
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>");
        return;
    }
    var form = document.forms[0];
    form.vendor_name.value = arr[0][2];
    form.vendor_code.value = arr[0][1];
}

function deletePlan( objName )
{
    eval( "document.forms[0]."+objName+"[0].value = ''");
    /*var table = document.getElementById("planTable");
    var pos = table.clickedRowIndex;

    var imgobj  = document.getElementsByName("img_"+objName);
    var planObj;
    var actualObj;

    if( objName.indexOf("plan_date") )
    {
        planObj = eval( "document.forms[0]."+objName);
        actualObj = eval( "document.forms[0]."+objName.substring(0, objName.indexOf("plan_date"))+"actual_date" );
    }
    else
    {
        actualObj = eval( "document.forms[0]."+objName);
        planObj = eval( "document.forms[0]."+objName.substring(0, objName.indexOf("plan_date"))+"plan_date" );

    }

    actualObj.value = '';
    planObj.value = '';

    if( planObj.readOnly )
    {
        planObj.readOnly = false;
    }
    else
    {
        planObj.readOnly = true;
    }

    if( actualObj.readOnly )
    {
        actualObj.readOnly = false;
    }
    else
    {
        actualObj.readOnly = true;
    }

    if( planObj.className == "txt_fieldRO" )
    {
        planObj.className = "txt_field";
    }
    else
    {
        planObj.className = "txt_fieldRO";
    }

    if( actualObj.className == "txt_fieldRO" )
    {
        actualObj.className = "txt_field";
    }
    else
    {
        actualObj.className = "txt_fieldRO";
    }

    for( var i=0; i < imgobj.length ; i++ )
    {
        if( imgobj[i].style.display == "none")
        {
            imgobj[i].style.display = "";
        }
        else
        {
            imgobj[i].style.display = "none";
        }
    }*/
}

//ECO담당자검색 팝업
function popupEcoUser( flag ){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    setEcoUser(attacheMembers, flag );
}

//표준품 담당자 검색 팝업 리턴 포맷
function setEcoUser(objArr, flag ) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];

    if( flag == 'P' )
    {
        form.p_owner_oid.value = trArr[0];
        form.p_owner_name.value = trArr[4];
    }
    else
    {
        form.m_owner_oid.value = trArr[0];
        form.m_owner_name.value = trArr[4];
    }
}

//ECO담당자 초기화
function clearEcoUser( flag ){
    var form = document.forms[0];

    if( flag == 'P' )
    {
        form.p_owner_oid.value = '';
        form.p_owner_name.value ='';
    }
    else
    {
        form.m_owner_oid.value = '';
        form.m_owner_name.value = '';
    }
}

function cancel()
{
    if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
    {
        history.back();
    }
    else
    {
        return;
    }
}

/* 2011. 07. 13 수정중 */
 function setFailAction(flag){
    if(flag == "합격"){
        //document.all.dept21.style.display="block";
        document.forms[0].fail_action.value="";
        document.forms[0].fail_action.disabled = true;
        document.forms[0].result.value="";
        document.forms[0].result.disabled = true;
        document.forms[0].measure_date.value="";
        document.getElementById('productDay1').style.display = "none";
        document.getElementById('productDay2').style.display = "none";
    }else{
        document.forms[0].fail_action.value="";
        document.forms[0].fail_action.disabled = false;
        document.forms[0].result.value="";
        document.forms[0].result.disabled = false;
    }
}

 function setResult(flag){
        if(flag == "일정재수립"){
            //document.all.dept21.style.display="block";
            document.forms[0].result.value="";
            document.forms[0].result.disabled = true;
            document.forms[0].measure_date.value="";
            document.getElementById('productDay1').style.display = "none";
            document.getElementById('productDay2').style.display = "none";
        }else{
            document.forms[0].result.value="";
            document.forms[0].result.disabled = false;
        }
    }

 function setComplete(flag){
        if(flag == "합격"){
            //document.all.dept21.style.display="block";
            document.forms[0].measure_date.value="";
            document.getElementById('productDay1').style.display = "block";
            document.getElementById('productDay2').style.display = "block";
        }else{
            document.forms[0].measure_date.value="";
            document.getElementById('productDay1').style.display = "none";
            document.getElementById('productDay2').style.display = "none";
        }
    }

 function setDateOnChange(flag, sDate){
    var goFlag;
    if(sDate != ""){
        sDate = sDate.replace(/-/gi,"");
        var year = sDate.substring(0, 4);
        var month = sDate.substring(4, 6);
        var day = sDate.substring(6, 8);
        var newDate = year+"-"+month+"-"+day;
        if(flag.indexOf('actual') != -1) {
            goFlag = eval( "document.forms[0]."+flag+".value=newDate");
        }else{
            goFlag = eval( "document.forms[0]."+flag+".value=newDate");
        }
        goFlag;

        if(!pointCheck(goFlag) || !dateCheck(goFlag,'-') ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>');
            if(flag.indexOf('actual') != -1) {
                eval( "document.forms[0]."+flag+".value=''");
                setTimeout(function(){
                    eval( "document.forms[0]."+flag+".focus()");
                });
            }else{
                eval( "document.forms[0]."+flag+".value=''");
                setTimeout(function(){
                    eval( "document.forms[0]."+flag+".focus()");
                });
            }
        }
    }else{
        goFlag;
    }
}

 function pointCheck(point){
    var string = point;
    var isChecked = true;
     if(string.substr(4,1) != '-' || string.substr(7,1) != '-'){
         isChecked =  false;
     }
     return isChecked;
 }

 function setMdate(flag){
        if(flag == "Yes"){
            document.getElementById('m_date_td').style.display = "block";
        }else{
            document.forms[0].m_date.value="";
            document.getElementById('m_date_td').style.display = "none";
        }
    }


 /* 2011. 07. 13 수정중 */
-->
</script>
</head>
<body class="popup-background popup-space" onload="load();">
<form name="ModifyPlanForm"  method="post" enctype="multipart/form-data">
<input type="hidden"  name="cmd"  value="Modify">
<input type= "hidden" name ="oid"  value="<%=CommonUtil.getOIDString( plan )%>" >
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteDocList" value="">
<input type="hidden" name="currentProcess" value="<%=currentProcess %>">

<input type="hidden" name="siljuk_date1" value="<%=DateUtil.getTimeFormat( plan.getProdDwgActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date2" value="<%=DateUtil.getTimeFormat( plan.getMoldChangeActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date3" value="<%=DateUtil.getTimeFormat( plan.getStoreActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date4" value="<%=DateUtil.getTimeFormat( plan.getWorkActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date5" value="<%=DateUtil.getTimeFormat( plan.getAssembleActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date6" value="<%=DateUtil.getTimeFormat( plan.getTryActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date7" value="<%=DateUtil.getTimeFormat( plan.getTestActualDate(), "yyyy-MM-dd") %>">
<input type="hidden" name="siljuk_date8" value="<%=DateUtil.getTimeFormat( plan.getApproveActualDate(), "yyyy-MM-dd") %>">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02092") %><%--양산금형 일정수정--%></td>
                
              </tr>
            </table></td>
        </tr>
        </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="110" class="tdblueL">Die No<span class="red">*</span></td>
          <td width="155" class="tdwhiteL"><input type="text" name="die_no"  value="<%=StringUtil.checkNull(plan.getDieNo()) %>"class="txt_fieldRO" style="width:99" id="textfield" readonly>
          <%if(isOwner || isAdmin){%>
            &nbsp;<a href="javascript:popupPart('D', 'selectDieNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="javascript:deletePartNo('D');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;</td>
          <%}%>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="140" class="tdwhiteL"><input type="text" name="part_no"  value="<%=StringUtil.checkNull(plan.getPartNo()) %>" class="txt_fieldRO" style="width:85" id="textfield2" readonly>
          <%if(isOwner || isAdmin){%>
            &nbsp;<a href="javascript:popupPart('P', 'selectPartNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="javascript:deletePartNo('P');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;</td>
          <%}%>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="200" class="tdwhiteL0"><input type="text" name="part_name"  value="<%=StringUtil.checkNull(plan.getPartName())%>" class="txt_fieldRO" style="width:99%" id="textfield2" readonly></td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%><span class="red">*</span></td>
          <td width="155" class="tdwhiteL">
                   <%if(!isOwner && !isAdmin){%>
                  <select name="type" class="fm_jmp" style="width:97%" disabled = "disabled"  >
                  </select>
                  <%}else{%>
                  <select name="type" class="fm_jmp" style="width:97%" >
                  </select>
                  <%}%>
          </td>
           <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
          <td width="140" class="tdwhiteL"><input type="text" name="prod_dept_name"  value="<%=StringUtil.checkNull(plan.getProdDeptName()) %>" class="txt_fieldRO" style="width:85" id="textfield" readonly>
          <%if(isOwner || isAdmin){%>
            &nbsp;<a href="javascript:selectDepartment()"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="javascript:deletePartNo('D');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;</td>
          <%}%>
         <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
          <td width="200" class="tdwhiteL0">
                  <%if(!isOwner && !isAdmin){%>
                  <select name="vendor_flag" class="fm_jmp" style="width:50" disabled = "disabled"  >
                  </select>
                  <%}else{%>
                  <select name="vendor_flag" class="fm_jmp" style="width:50" >
                  </select>
                  <%}%>
                  <%
                   if( StringUtil.checkNull(plan.getVendorFlag()).equals("i") && plan.getVendorCode() != null ) {
                   %>
                  <input type="text" name="vendor_name" value="<%=StringUtil.checkNull(NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", plan.getVendorCode() ).getName() ) %>" class="txt_fieldRO" style="width:95" id="textfield2" readonly>
                  <%
                   }
                   else if( StringUtil.checkNull(plan.getVendorFlag()).equals("o") && plan.getVendorCode() != null )
                   {
                   %>
                   <input type="text" name="vendor_name" value="<%=StringUtil.checkNull(EcmSearchHelper.manager.getPartnersName(plan.getVendorCode())) %>" class="txt_fieldRO" style="width:95" id="textfield2" readonly>
                  <%
                }
                   else
                   {
                   %>
                   <input type="text" name="vendor_name" value="" class="txt_fieldRO" style="width:95" id="textfield2" readonly>
                   <%
                   }
                   %>
                  <input type="hidden" name="vendor_code"  value="<%=StringUtil.checkNull(plan.getVendorCode()) %>">
                   <%if(isOwner || isAdmin){%>
                   <a href="javascript:popupVendor();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:deletePartNo('P');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                   <%} %>
          </td>
        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01322") %><%--등록근거--%><span class="red">*</span></td>
          <td width="155" class="tdwhiteL">
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr valign="top">
                  <td>
                      <%if(!isOwner && !isAdmin){%>
                      <select name="reg_basis" class="fm_jmp" style="width:60" disabled = "disabled"  >
                      </select>
                      <%}else{ %>
                      <select name="reg_basis" class="fm_jmp" style="width:60" >
                      </select>
                      <%} %>
                  </td>
                  <td>
                      <%if(isOwner || isAdmin){%>
                      <input type="text" name="basis_date" value="<%=DateUtil.getTimeFormat( plan.getBasisDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:65" id="textfield16">
                      <%}else{ %>
                      <input type="text" name="basis_date" value="<%=DateUtil.getTimeFormat( plan.getBasisDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:65" id="textfield16"  readonly="true">
                      <%} %>
                  </td>
                  <%if(isOwner || isAdmin){%>
                  <td><a href="#"><img src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.ModifyPlanForm.basis_date,0);"></a>&nbsp;
                  </td>
                <%} %>
                  </tr>
                 </table>
          </td>
          <td width="90" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02562") %><%--제품ECO담당--%></td>
          <td width="140" class="tdwhiteL">
                      <%if( plan.getProdEcoOwner() !=null ){ %>
                      <input type="text" name="p_owner_name"  value="<%=StringUtil.checkNull(plan.getProdEcoOwner().getFullName()) %>" class="txt_fieldRO" style="width:85" value='' readonly>
                      <input type="hidden" name="p_owner_oid"  value="<%=CommonUtil.getOIDString(plan.getProdEcoOwner())%>">
                      <%}else{ %>
                      <input type="text" name="p_owner_name"  value="" class="txt_fieldRO" style="width:85" value='' readonly>
                      <input type="hidden" name="p_owner_oid"  value="">
                      <%} %>
                      <%if(isOwner || isAdmin){%>
                    <a href="javascript:popupEcoUser('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;
                    <a href="javascript:clearEcoUser('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;
                    <%} %>
          </td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01033") %><%--금형ECO담당--%></td>
          <td width="200" class="tdwhiteL0">
                      <%if( plan.getMoldEcoOwner() !=null ){ %>
                    <input type="text" name="m_owner_name" value="<%=StringUtil.checkNull(plan.getMoldEcoOwner().getFullName()) %>"class="txt_fieldRO" style="width:150" value='' readonly>
                    <input type="hidden" name="m_owner_oid" value="<%=CommonUtil.getOIDString(plan.getMoldEcoOwner())%>">
                    <%}else{ %>
                    <input type="text" name="m_owner_name" value="" class="txt_fieldRO" style="width:150" value='' readonly>
                    <input type="hidden" name="m_owner_oid" value="" >
                    <%} %>
                    <%if(isOwner || isAdmin){%>
                    <a href="javascript:popupEcoUser('M');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                    <a href="javascript:clearEcoUser('M');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                    <%} %>
          </td>
        </tr>
        <tr>
         <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00840") %><%--고객사4M--%><span class="red">*</span></td>
          <td width="155" class="tdwhiteL">
                   <%if(!isOwner && !isAdmin){%>
                  <select name="m_customer" class="fm_jmp" style="width:97%" disabled = "disabled"  >
                  </select>
                  <%}else{%>
                  <select name="m_customer" class="fm_jmp" style="width:97%"  onchange="javascript:setMdate(this.value);">
                  </select>
                  <%}%>
          </td>
          <td width="85" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00056") %><%--4M완료일--%></td>
          <td id ="m_date_td"colspan="3" width="200" class="tdwhiteL0">
              <%if(isOwner || isAdmin){%>
            <input type="text" name="m_date" value="<%=StringUtil.checkNull(plan.getAttribute2()) %>" class="txt_field"  style="width:65" id="textfield16">
            <%}else{ %>
            <input type="text" name="m_date" value="<%=StringUtil.checkNull(plan.getAttribute2()) %>" class="txt_field"  style="width:65" id="textfield16"  readonly="true">
            <%} %>
            <%if(isOwner || isAdmin){%>
            <a href="#"><img src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.ModifyPlanForm.m_date,0);"></a>&nbsp;
            <%} %>
          </td>


        </tr>
        <tr>
          <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01946") %><%--수정내용--%></td>
          <td class="tdwhiteL0" colspan=5>
          <%if(isOwner || isAdmin){%>
          <textarea name="modify_desc" style='overflow: auto; width:98%; height:50px; padding: 10;' class="fm_area" ><%=StringUtil.checkNull(plan.getModifyDesc()) %></textarea>
          <%}else{ %>
          <textarea name="modify_desc" style='overflow: auto; width:98%; height:50px; padding: 10;' class="fm_area"  readonly><%=StringUtil.checkNull(plan.getModifyDesc()) %></textarea>
          <%} %>
          </td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02370") %><%--일정정보--%></td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border"  id="planTable">
              <tr>
                 <td width="30" class="tdgrayM">&nbsp;</td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02585") %><%--제품도--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01097") %><%--금형입고--%>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01037") %><%--금형Try--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02624") %><%--제품측정--%></td>
                 <td width="83" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00737") %><%--검토협의--%></td>
              </tr>
               <tr onMouseOver='planTable.clickedRowIndex=this.rowIndex'>
               <td width="30" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                <td width="83" class="tdwhiteM"><input type="text" name="prod_plan_date"  value="<%=DateUtil.getTimeFormat( plan.getProdDwgPlanDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('prod_plan_date', this.value);">&nbsp;
                  <a href="#"><img name="img_prod_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:showCal(document.forms[0].prod_plan_date);"></a>
                  <a href="javascript:deletePlan('prod_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                <td width="83" class="tdwhiteM"><input type="text" name="mold_eco_plan_date" value="<%=DateUtil.getTimeFormat( plan.getMoldChangePlanDate(),  "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('mold_eco_plan_date', this.value);">&nbsp;
                <a href="#"><img name="img_mold_eco_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].mold_eco_plan_date,0);"></a>
                <a href="javascript:deletePlan('mold_eco_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                <td width="83" class="tdwhiteM"><input type="text" name="store_plan_date" value="<%=DateUtil.getTimeFormat( plan.getStorePlanDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('store_plan_date', this.value);">&nbsp;
                <a href="#"><img name="img_store_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].store_plan_date,0);"></a>
                <a href="javascript:deletePlan('store_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="work_plan_date" value="<%=DateUtil.getTimeFormat( plan.getWorkPlanDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('work_plan_date', this.value);">&nbsp;
                 <a href="#"><img name="img_work_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].work_plan_date,0);"></a>
                 <a href="javascript:deletePlan('work_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="ass_plan_date" value="<%=DateUtil.getTimeFormat( plan.getAssemblePlanDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('ass_plan_date', this.value);">&nbsp;
                 <a href="#"><img name="img_ass_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].ass_plan_date,0);"></a>
                 <a href="javascript:deletePlan('ass_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="try_plan_date" value="<%=DateUtil.getTimeFormat( plan.getTryPlanDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('try_plan_date', this.value);">&nbsp;
                 <a href="#"><img name="img_try_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].try_plan_date,0);"></a>
                 <a href="javascript:deletePlan('try_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="test_plan_date" value="<%=DateUtil.getTimeFormat( plan.getTestPlanDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('test_plan_date', this.value);">&nbsp;
                 <a href="#"><img name="img_test_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].test_plan_date,0);"></a>
                 <a href="javascript:deletePlan('test_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="approve_plan_date" value="<%=DateUtil.getTimeFormat( plan.getApprovePlanDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('approve_plan_date', this.value);">&nbsp;
                 <a href="#"><img name="img_approve_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].approve_plan_date,0);"></a>
                 <a href="javascript:deletePlan('approve_plan_date');" onfocus="this.blur();"><img name="icon_delete" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                 </td>
              </tr>
              <%if(!"".equals(StringUtil.checkNull(plan.getScheduleStatus())) ) { %>
              <tr onMouseOver='planTable.clickedRowIndex=this.rowIndex'>
               <td width="30" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%></td>
                <td width="83" class="tdwhiteM"><input type="text" name="prod_actual_date" value="<%=DateUtil.getTimeFormat( plan.getProdDwgActualDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('prod_actual_date', this.value);">&nbsp;
                  <a href="#"><img name="img_prod_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:showCal(document.forms[0].prod_actual_date);"></a>
                </td>
                <td width="83" class="tdwhiteM"><input type="text" name="mold_eco_actual_date" value="<%=DateUtil.getTimeFormat( plan.getMoldChangeActualDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('mold_eco_actual_date', this.value);">&nbsp;
                <a href="#"><img name="img_mold_eco_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].mold_eco_actual_date,0);"></a>
                </td>
                <td width="83" class="tdwhiteM"><input type="text" name="store_actual_date" value="<%=DateUtil.getTimeFormat( plan.getStoreActualDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('store_actual_date', this.value);">&nbsp;
                <a href="#"><img name="img_store_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].store_actual_date,0);"></a>
                </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="work_actual_date" value="<%=DateUtil.getTimeFormat( plan.getWorkActualDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('work_actual_date', this.value);">&nbsp;
                 <a href="#"><img name="img_work_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].work_actual_date,0);"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="ass_actual_date" value="<%=DateUtil.getTimeFormat( plan.getAssembleActualDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('ass_actual_date', this.value);">&nbsp;
                 <a href="#"><img name="img_ass_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].ass_actual_date,0);"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="try_actual_date" value="<%=DateUtil.getTimeFormat( plan.getTryActualDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('try_actual_date', this.value);">&nbsp;
                 <a href="#"><img name="img_try_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].try_actual_date,0);"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="test_actual_date" value="<%=DateUtil.getTimeFormat( plan.getTestActualDate(), "yyyy-MM-dd" ) %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('test_actual_date', this.value);">&nbsp;
                 <a href="#"><img name="img_test_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].test_actual_date,0);"></a>
                 </td>
                 <td width="83" class="tdwhiteM"><input type="text" name="approve_actual_date" value="<%=DateUtil.getTimeFormat( plan.getApproveActualDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:50%" onChange="javascript:setDateOnChange('approve_actual_date', this.value);">&nbsp;
                 <a href="#"><img name="img_approve_plan_date" src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.forms[0].approve_actual_date,0);"></a>
                 </td>
              </tr>
              <%} //등록버튼 누르지 않을때 %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
         <%if(!"".equals(StringUtil.checkNull(plan.getScheduleStatus())) ) { %>
        <tr>
            <td width="110" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02728") %><%--진행내용--%></td>
            <td colspan="5" class="tdwhiteL0">
                    <table border="0" cellspacing="0" cellpadding="0" width="670">
                        <tr>
                            <td class="space5"></td>
                          </tr>
                    </table>
                    <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
                        <tr>
                            <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
                            <td class="tdwhiteM">
                                <select name="measure_type" class="fm_jmp" style="width:60" onchange="javascript:setFailAction(this.value);">
                                  </select>
                              </td>
                              <td width="80" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "01628") %><%--불합격 조치--%></td>
                            <td class="tdwhiteM">
                                <select name="fail_action" class="fm_jmp" style="width:90"onchange="javascript:setResult(this.value);">
                                  </select>
                              </td>
                              <td width="50" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00747") %><%--결과--%></td>
                            <td class="tdwhiteM">
                                <select name="result" class="fm_jmp" style="width:90" onchange="javascript:setComplete(this.value);">
                                  </select>
                              </td>
                              <td width="80" class="tdgrayR"><div id="productDay1" style="display:none;"><%=messageService.getString("e3ps.message.ket_message", "02625") %><%--제품측정일--%></div></td>
                            <td class="tdwhiteM" width="165"><div id="productDay2" style="display:none;">
                            <%if( "R".equals(plan.getScheduleStatus()) ){
                                 if(isAdmin){
                            %>
                                  <input type="text" name="measure_date"  value="<%=DateUtil.getTimeFormat( plan.getMeasureDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:70" id="textfield16">
                                  &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.ModifyPlanForm.measure_date,0);"></a></div>
                            <%}else{ %>
                            <%=DateUtil.getTimeFormat( plan.getMeasureDate(), "yyyy-MM-dd") %>
                               <%}}else{ %>
                                  <input type="text" name="measure_date"  value="<%=DateUtil.getTimeFormat( plan.getMeasureDate(), "yyyy-MM-dd") %>" class="txt_field"  style="width:70" id="textfield16">
                                  &nbsp;<a href="#"><img src="/plm/portal/images/icon_6.png"border="0" onclick="javascript:show_calendar(document.ModifyPlanForm.measure_date,0);"></a></div>
                                  <%} %>
                              </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="670">
                        <tr>
                            <td class="space5"></td>
                          </tr>              </table>
            </td>
        </tr>
        <%} // 등록버튼 누르지 않을때 %>
        <tr>
            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
            <td colspan="5" class="tdwhiteL0"><textarea name="plan_desc" style='overflow:auto; width:98%; height:50px; padding: 10;' class="fm_area" ><%=StringUtil.checkNull(plan.getPlanDesc())%></textarea></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02124") %><%--연계산출물--%></td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupDoc();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'docTable', 'chkSelectDoc', 'chkDocAll', 'deleteDocList' );"  class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM"><input type="checkbox" name="chkDocAll" id="checkbox26"></td>
                <td width="375" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td> <!-- 문서명 -->
                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
              </tr>
            </table>
            <div style="height:54;width:98%;overflow-x:hidden;overflow-y:auto;" class="table_border">
            <table width="100%" cellpadding="0" cellspacing="0" id="docTable">
            <%
            if( linkQr != null )
            {
                KETProjectDocument doc = null;
                while( linkQr.hasMoreElements() )
                {
                    doc = (KETProjectDocument)linkQr.nextElement();
            %>
              <tr>
                <td width="40" class="tdwhiteM"><input type="checkbox" name="chkSelectDoc" id="checkbox29"></td>
                <td width="369" class="tdwhiteL"><%=doc.getTitle() %><input type="hidden" name="docOid" value="<%=CommonUtil.getOIDString(doc) %>"></td>
                <td width="149" class="tdwhiteM"><%=doc.getDeptName() %></td>
                <td width="131" class="tdwhiteM0"><%=doc.getCreatorFullName() %></td>
              </tr>
            <%
                    }
            }
            %>
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td colspan="5" class="tdwhiteL0"><table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td width="5">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif" ><a href="javascript:deleteDataLine('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');"class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="40" class="tdgrayM"><input type="checkbox" name="chkFileAll" id="checkbox14" onclick="javascript:allCheck(this.checked);"></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
              </tr>
            </table>
            <div style="height:54;width:98%;overflow-x:hidden;overflow-y:auto;" class="table_border" >
            <table width="100%" cellpadding="0" cellspacing="1"  id="fileTable">
            <col width="39"><col>
             <%
                ContentInfo info = null;
                String url = null;
                  for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
                  {
                      info = (ContentInfo)attachFileList.get( fileCnt );
                      url = info.getDownURL().toString();
             %>
                      <tr>
                           <td width="39" class="tdwhiteM"><input name='fileSelect' type='checkbox' class='chkbox'  value="<%=info.getContentOid()%>"></td>
                           <td class="tdwhiteM"><a href="<%=url %>"><%=info.getName()%></a></td>
                      </tr>
             <%
                  }
             %>
             <!-- <tbody id="fileTable"></tbody>-->
            </table>
            </div>
            <table border="0" cellspacing="0" cellpadding="0" width="670">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
