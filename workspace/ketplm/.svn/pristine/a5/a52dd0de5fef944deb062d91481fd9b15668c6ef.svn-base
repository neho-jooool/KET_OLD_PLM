<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page import="e3ps.ecm.beans.EcmUtil
                            ,e3ps.project.beans.MoldProjectHelper
                            ,e3ps.ecm.beans.ProdEcoBeans
                            ,e3ps.common.content.ContentInfo
                            ,e3ps.common.content.ContentUtil
                            ,e3ps.common.util.StringUtil
                            ,e3ps.common.util.KETStringUtil
                            ,e3ps.common.util.KETObjectUtil
                            ,e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="java.util.Vector
                            ,java.util.Hashtable
                            ,java.util.ArrayList" %>
<%@page import="wt.content.ContentHolder
                            ,wt.fc.Persistable" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="ecrHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%

    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale", messageService.getLocale());

    ProdEcoBeans beans = new ProdEcoBeans();
    Hashtable<String, ArrayList<Hashtable<String,String>>> codeHash = beans.getInitCodeList(parameter);

    ArrayList<Hashtable<String, String>> devFlagList = codeHash.get("devYn");
    ArrayList<Hashtable<String, String>> divList = codeHash.get("division");
    ArrayList<Hashtable<String, String>> chgReason = codeHash.get("changeReason");
    ArrayList<Hashtable<String, String>> custChkList = codeHash.get("custChk");
    ArrayList<Hashtable<String, String>> chgFactList = codeHash.get("changeFact");
    ArrayList<Hashtable<String, String>> effDateList = codeHash.get("effectiveDate");
    ArrayList<Hashtable<String, String>> invProcList = codeHash.get("invProcess");
    ArrayList<Hashtable<String, String>> docTypeList = codeHash.get("docTypeList");
    ArrayList<Hashtable<String, String>> defectDivList = codeHash.get("defectDivList");
    ArrayList<Hashtable<String, String>> costChangeList = codeHash.get("costChangeList");
    
    // 설계변경 상세사유
    ArrayList<Hashtable<String, String>> changeDetailReasonList = codeHash.get("changeDetailReasonList");
    
    ContentHolder holder = null;
    Vector attachFileList = null;

    String userGroupStr = KETObjectUtil.getCurrentUserGroup();

    ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
    ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
    ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
    // 기 저장된 ECN 리스트
    ArrayList<Hashtable<String, String>> docList = (ArrayList)ecoHash.get("docList");

    
    String eco_name = "";
    String dev_yn = "";
    String project_oid = "";
    String project_no = "";
    String project_name = "";
    String other_chg_reason_desc = "";
    String other_cust_chk_desc = "";
    String status = "";
    boolean isAddableActivity  = true;
    

    //제품, 금형 ECO 확장팩
    /* 
    KETChangeRequestExpansion expansion = null;
    // ECR 로 찾는다.
    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
    spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(prodEcr)), new int[] { 0 });
    QueryResult result = PersistenceHelper.manager.find(spec);
    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
      expansion = (KETChangeRequestExpansion) result.nextElement();
    }

    String reviewRequestDate = "";
    String carTypeDisplayName = "";
    String carTypeOid = "";
    String carTypeCode = "";

    String moldChangeDisplayName = "";
    String moldChangeOid = "";
    String moldChangeCode = "";

    String costChangeDisplayName = "";
    String costChangeOid = "";
    String costChangeCode = "";

    String emergencyPositionDisplayName = "";
    String emergencyPositionOid = "";
    String emergencyPositionCode = "";

    String subjectDisplayName = "";
    String subjectOid = "";
    String subjectCode = "";
    String chargeDisplayName = "";
    String chargeOid = "";
    String chargeName = "";
    */
    
    // 검토결과
    String reviewResult = "";
    
    // 변경 전
    String webEditor = "";
    String webEditorText = "";

    // 변경 후
    String webEditor1 = "";
    String webEditorText1 = "";
    /*   
    if (PersistenceHelper.isPersistent(expansion)) {
        
        // 검토요청기한
        reviewRequestDate = DateUtil.getTimeFormat(expansion.getReviewRequestDate(), "yyyy-MM-dd"); 
        
        // 차종
        carTypeDisplayName = "";
        carTypeOid = "";
        carTypeCode = "";
        
        // 금형변경
        NumberCode moldChange = expansion.getMoldChange();
        moldChangeDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(moldChange.getName()) : "";
        moldChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(moldChange));
        moldChangeCode = StringUtils.stripToEmpty(expansion.getMoldChangeCode());
        
        // 원가변동
        NumberCode costChange = expansion.getCostChange();
        costChangeDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(costChange.getName()) : "";
        costChangeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(costChange));
        costChangeCode = StringUtils.stripToEmpty(expansion.getCostChangeCode());
      
        // 긴급도
        NumberCode emergencyPosition = expansion.getMoldChange();
        emergencyPositionDisplayName = (moldChange != null) ? StringUtils.stripToEmpty(emergencyPosition.getName()) : "";
        emergencyPositionOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(emergencyPosition));
        emergencyPositionCode = StringUtils.stripToEmpty(expansion.getEmergencyPositionCode());
            
        // 주관부서
        Department subject = expansion.getSubject();
        subjectDisplayName = (subject != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(subject.getName())) : "";
        subjectOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(subject));
        subjectCode = StringUtils.stripToEmpty(expansion.getSubjectCode());
        
        // 담당자
        WTUser charge = expansion.getCharge();
        chargeDisplayName = (charge != null) ? HtmlUtils.htmlEscape(StringUtils.stripToEmpty(charge.getFullName())) : "";
        chargeOid = StringUtils.stripToEmpty(CommonUtil.getOIDString(charge));
        chargeName = StringUtils.stripToEmpty(expansion.getChargeName());
        
        // 현상
        webEditor = (String) expansion.getWebEditor();
        webEditorText = (String) expansion.getWebEditorText();
         
        // 문제점 및 요구사항
        webEditor1 = (String) expansion.getWebEditor1();
        webEditorText1 = (String) expansion.getWebEditorText1();

    }
    */
    int FertCnt = 0;
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02563") %><%--제품ECO등록/수정--%></title>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: -10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<script language="javascript">

//처리중 화면 전환
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    var tabEcn = document.getElementById("tabEcn");
    
    if(tabId == 1) {
        tabBasic.style.display = "block";
        tabActivity.style.display = "none";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 2;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabBasic';
        
    } else if(tabId == 2) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "block";
        tabEcn.style.display = "none";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "none";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 2;
        tabEcn.style.zIndex = 1;
        
        document.forms[0].tabName.value = 'tabActivity';
        
    } else if(tabId == 3) {
        tabBasic.style.display = "none";
        tabActivity.style.display = "none";
        tabEcn.style.display = "block";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
        var infoEcn = document.getElementById("infoEcn");
        infoEcn.style.display = "block";
        
        tabBasic.style.zIndex = 1;
        tabActivity.style.zIndex = 1;
        tabEcn.style.zIndex = 2;
        
        document.forms[0].tabName.value = 'tabEcn';

      }
}

//초기화면세팅
function init() {

    var form = document.CreateProdEcoForm;

    deleteAllSelectBox( form.div_flag );
    deleteAllSelectBox( form.effective_date );
    deleteAllSelectBox( form.inv_process );
    <%if( userGroupStr.equals("자동차사업부") || userGroupStr.equals("KETS") ){%>
    deleteAllSelectBox( form.domestic_yn );
    <%}%>

    <%
    Hashtable<String, String> divHash = new Hashtable<String, String>();
    String userGroupCode = "";
    for(int divCnt = 0 ; divCnt < divList.size(); divCnt++ )
    {

        divHash = divList.get(divCnt);

        Kogger.debug(divHash.get("name"));
        if( divHash.get("name").equals(userGroupStr) )
        {
            userGroupCode = divHash.get("code");
    %>
            addSelectBox( form.div_flag, '<%=divHash.get("name")%>', '<%=divHash.get("code")%>', '<%=userGroupCode%>');
    <%
        }
    }
    Hashtable<String, String> effDate = new Hashtable<String, String>();
    for( int eCnt=0; eCnt < effDateList.size() ; eCnt++ )
    {
        effDate = effDateList.get( eCnt );
    %>
        addSelectBox( form.effective_date, '<%=effDate.get("name")%>', '<%=effDate.get("code")%>', '');
    <%
    }
    %>
    addSelectBox( form.costChange, '<%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%>', '', '');
    <%
    Hashtable<String, String> costChange = new Hashtable<String, String>();
    for( int costCnt=0; costCnt < costChangeList.size() ; costCnt++ )
    {
	    costChange = costChangeList.get( costCnt );
    
    %>
        
        addSelectBox( form.costChange, '<%=costChange.get("name")%>', '<%=costChange.get("code")%>', '');
    <%
    }

    Hashtable<String, String> invProc = new Hashtable<String, String>();
    for( int pCnt=0 ; pCnt < invProcList.size(); pCnt++ )
    {
        invProc = invProcList.get( pCnt );
    %>
        addSelectBox( form.inv_process, '<%=invProc.get("name")%>', '<%=invProc.get("code")%>', '');
    <%
    }
    %>
    form.dev_yn[0].checked=true;

    <%if( userGroupStr.equals("자동차사업부") || userGroupStr.equals("KETS") ){%>
    addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>', '', '');
    addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00983") %><%--국내--%>', '1000', '');
    addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00985") %><%--국외--%>', '2000', '');
    <%}%>
    //searchCarData('maker', '1000');
    addSelectBox( form.defectDiv, '<%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%>', '', '');
    <%
    Hashtable<String, String> defectDiv = new Hashtable<String, String>();
    
    for( int defectCnt=0 ; defectCnt < defectDivList.size(); defectCnt++ )
    {
        defectDiv = defectDivList.get( defectCnt );
    %>
        addSelectBox( form.defectDiv, '<%=defectDiv.get("name")%>', '<%=defectDiv.get("code")%>', '');
    <%
    }
    %>

    loadEcrData();
    
    /* 
    disable( 'other_reason', false );
    */
    disable( 'other_cust_flag', false );
    clickTabBtn(1);
}
function loadEcrData()
{
    <%
    if( ecrHash != null && !ecrHash.isEmpty() )
    {
        Hashtable<String, String> partHash = null;
        ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
        partList = (ArrayList)ecrHash.get("partList");
    %>
        document.forms[0].prePage.value = 'ECR';
        
        <%-- document.forms[0].eco_name.value = "<%=ecrHash.get("ecr_name").toString()%>"; --%>
        
        var eco_name = '<%=StringUtil.checkNull(ecrHash.get("ecr_name").toString()).replaceAll("'", "^quot^")%>'; //eco_name
        eco_name = eco_name.replace('^quot^', "\'");
        
        document.forms[0].eco_name.value = eco_name;

        var dev_yn = document.all("dev_yn");
        for( var inx=0; inx < dev_yn.length ; inx++)
        {
            if( dev_yn[inx].value == '<%=ecrHash.get("dev_yn").toString()%>' )
            {
                dev_yn[inx].checked = true;
            }
        }

        var div_flag = document.all("div_flag");
        for( var inx=0; inx < div_flag.length ; inx++)
        {
            if( div_flag[inx].value == '<%=ecrHash.get("division").toString()%>' )
            {
                div_flag[inx].checked = true;
            }
        }

        document.forms[0].project_name.value = "<%=ecrHash.get("pjt_name").toString()%>";
        document.forms[0].project_oid.value = '<%=ecrHash.get("pjt_oid").toString()%>';
        document.forms[0].project_id.value = '<%=ecrHash.get("pjt_no").toString()%>';

        <%
        String maker = ecrHash.get("maker").toString();
        String category = ecrHash.get("carcategory").toString();
        String domain = ecrHash.get("domain").toString();

        if( category.equals("") && ecrHash.get("pjt_name") != null )
        {
            E3PSProject project = ProjectHelper.manager.getProject(ecrHash.get("pjt_no").toString());

            if( project != null && project.getOemType() != null )
             {
                 maker = project.getOemType().getMaker().getCode();
                 category= project.getOemType().getCode();
                 Kogger.debug( "category = "  + category);
                 if( maker.startsWith("10") )
                 {
                     domain = "1000";
                 }
                 else
                 {
                     domain = "2000";
                 }
             }
        }
        %>

        if( '<%=maker%>' != "" &&  ('<%=userGroupStr%>' == '자동차사업부' || '<%=userGroupStr%>' == 'KETS') )
        {
            document.forms[0].domestic_yn.value = '<%=domain%>';

            searchCarData( 'maker',  '<%=domain%>', '<%=maker%>' );
            searchCarData( 'car', '<%=maker%>', '<%=category%>' );
        }

        var ecrListArr = new Array();
        var ecrArr = new Array();
        ecrArr[0] = '<%=ecrHash.get("ecr_oid").toString()%>';
        ecrArr[1] = '<%=ecrHash.get("ecr_id").toString()%>';
        ecrArr[2] = '<%=ecrHash.get("dept_name").toString()%>';
        ecrArr[3] = '<%=ecrHash.get("creator_name").toString()%>';
        ecrArr[4] = '<%=EcmUtil.getLastApproveDate( (Persistable)KETObjectUtil.getObject( ecrHash.get("ecr_oid").toString() ) )%>';
        <%-- ecrArr[5] = "<%=ecrHash.get("ecr_name").toString()%>"; --%>
        
        var eco_name = '<%=StringUtil.checkNull(ecrHash.get("ecr_name").toString()).replaceAll("'", "^quot^")%>'; //eco_name
        eco_name = eco_name.replace('^quot^', "\'");
        
        ecrArr[5] = eco_name;
        
        ecrListArr[0] = ecrArr;

        insertRelEcrLine( ecrListArr );

        var partListArr = new Array();
        var partArr;
    <%
        for( int pCnt=0 ; pCnt < partList.size(); pCnt++ )
        {
            partHash = partList.get(pCnt);
            
            // 완제품만 자동 Set된다.
            String PART_TYPE_CODE = partHash.get("PART_TYPE_CODE").toString();
            if(PART_TYPE_CODE != null && PART_TYPE_CODE.equalsIgnoreCase("F")) {
    %>
            partArr = new Array();
            partArr[0] = '<%=partHash.get("part_oid").toString()%>';
            partArr[1] = '<%=partHash.get("part_no").toString()%>';
            partArr[2] = "<%=partHash.get("part_name").toString()%>";
            partArr[3] = '<%=partHash.get("ver").toString()%>';
            partArr[4] = 'P';
            partArr[5] = '<%=StringUtil.checkNull(MoldProjectHelper.getDieNo(partHash.get("part_no").toString()))%>';

            partArr[6] = '';
            partArr[7] = '';
            partArr[8] = '';
            partArr[9] = '';
            partArr[10] = '';
            partArr[11] = '';
            partArr[12] = '<%=partHash.get("revDis").toString()%>';
            
            partListArr[ <%=FertCnt%> ] = partArr;
    <%
            FertCnt++;
            }
        }
    %>
        selectedPart( partListArr);
    <%
    }
    %>
}

    function isChecked( checkboxName )
    {
        var isChecked = false;
        var objCheck = document.getElementsByName(checkboxName);

        for( var i=0; i < objCheck.length; i++ )
        {
            if( objCheck[i].checked )
            {
                isChecked = true;
            }
        }

        return isChecked;
    }

    function deleteDataLine(formName, tableElementId, checkboxName, allCheckName, listVarName)
    {
        var body = document.getElementById(tableElementId);
        if (body.rows.length == 0) return;
        var formNameStr = "document." + formName + ".";
        var objChecks = eval(formNameStr + checkboxName);
        var objAllChecks = eval(formNameStr + allCheckName);

        if( isChecked( checkboxName ) )
        {
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
        else
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
            return;
        }
    }

    function allCheck( checkboxName, isChecked )
    {
        var checkedList = document.getElementsByName( checkboxName );

        for( var i=0; i < checkedList.length ; i++ )
        {
            checkedList[i].checked = isChecked;
        }
    }

    function disable( chkBoxName, value )
    {
        var obj = eval("document.forms[0]." + chkBoxName);
        if ( obj != undefined ) {
            if(value) {
                obj.disabled = false;
            } else {
                obj.disabled = true;
            }
        }
    }

function popupPart( fncall )
{
    var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=P&fncall="+fncall;
    openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
}

function setParentPart( objArr )
{
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("relEpmTable");
    var form = document.forms[0];

    var tableRows = targetTable.rows.length;
    var pos = targetTable.clickedRowIndex - 1;

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];

        if( tableRows > 2 )
        {
            form.parentPart[pos].value += trArr[1] ;

            if( i != objArr.length -1 )
            {
                form.parentPart[pos].value += ',';
            }
        }
        else
        {
            form.parentPart.value += trArr[1];
            if( i != objArr.length -1 )
            {
                form.parentPart.value += ',';
            }

        }
    }
}

function searchDieNo( partno )
{
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/DieNoListPopup.jsp";
    url += "&obj=partno^"+partno;
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=465px; dialogHeight:300px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    setDieNo( attache );
}

function setDieNo( attache )
{
    var form = document.forms[0];
    var pos = relPartTable.clickedRowIndex;
    var targetTable = document.getElementById("relPartTable");
    var tableRows = targetTable.rows.length;
    if( tableRows > 1 )
    {
        if( attache != '기타' )
        {
            form.relDieNo[pos].value = attache;
            form.relDieNo[pos].readOnly = true;
        }
        else
        {
            form.relDieNo[pos].value = '';
            form.relDieNo[pos].readOnly = false;
        }
    }
   else
   {
        if( attache != '기타' )
        {
            form.relDieNo.value = attache;
            form.relDieNo.readOnly = true;
        }
        else
        {
            form.relDieNo.value = '';
            form.relDieNo.readOnly = false;
        }
    }
}

function popupRelDoc(tableId) {
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one";
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=480px; dialogHeight:500px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    var pos = eval(tableId).clickedRowIndex;
    setRelDoc(attache, pos);
}

function setRelDoc(objArr, pos) {
    if(objArr.length == 0) {
        return;
    }

    var trArr = objArr[0];
    var form = document.forms[0];

    var targetTable = document.getElementById("relDocTable");
    var tableRows = targetTable.rows.length;
    if(tableRows > 1){
        form.relEcaDocOid[pos].value = trArr[0];
        form.relEcaDocNo[pos].value = trArr[1];
        form.relEcaDocName[pos].value = trArr[2];
        form.relEcaDocPreRev[pos].value = trArr[3];
    } else {
        form.relEcaDocOid.value = trArr[0];
        form.relEcaDocNo.value = trArr[1];
        form.relEcaDocName.value = trArr[2];
        form.relEcaDocPreRev.value = trArr[3];
    }
}

function doCancel()
{
    if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
    {
        if( document.forms[0].prePage.value == 'ECR' )
        {
            history.back();
        }
        else
        {
            
            /* 
            document.forms[0].action= '/plm/jsp/ecm/SearchEcoForm.jsp';
            document.forms[0].target='_self';
            document.forms[0].submit();
            */
            window.close();
            
        }
    }
    else
    {
        return;
    }
}

function searchCarData( type, value, selectedValue )
{
    var pre_domestic_flag = document.forms[0].pre_domestic_yn.value;
    document.forms[0].pre_domestic_yn.value = value;

    /* 
    if(pre_domestic_flag != value )
    {
        deleteAllSelectBox( document.forms[0].car_category );
    }
    */
    
    if( type == 'maker' )
    {
        document.forms[0].target="setCarData";
    }
    else
    {
        document.forms[0].target="download";
    }
    document.forms[0].action="/plm/jsp/ecm/setCarData.jsp?type="+type+"&condition="+value+"&selectedvalue="+selectedValue;
    document.forms[0].submit();
}
function setCarData( target, display_name, value, selected_txt )
{
    //alert( target+', '+ typeof(target) +', '+ display_name +', '+ value +', '+ selected_txt );
    var paramObj;
    if(typeof(target) == "string") {
        paramObj = eval(target);
    } 
    else if( typeof(target)!="object") {
        paramObj = document.all.item(target);
        if(paramObj == null) {
            paramObj = eval("document.forms[0]." + target);
        }
    }
    else{
        paramObj = target;
    }

    // 마지막 점검
    var objTarget;
    if(typeof(paramObj)=="object"){
        objTarget = eval(paramObj);
    }

    //alert( objTarget +', '+ display_name +', '+ value +', '+ selected_txt );
    addSelectBox( objTarget, display_name, value, selected_txt );
}

function delCarData( target )
{
    var paramObj;
    if(typeof(target) == "string") {
        paramObj = eval(target);
    } 
    else if( typeof(target)!="object") {
        paramObj = document.all.item(target);
        if(paramObj == null) {
            paramObj = eval("document.forms[0]." + target);
        }
    }
    else{
        paramObj = target;
    }

    // 마지막 점검
    var objTarget;
    if(typeof(paramObj) == "object"){
        objTarget = eval(paramObj);
    }

    //alert(objTarget);
    deleteAllSelectBox(objTarget);
}




function check_attachFile(){
    $.ajax({
        type       : "POST",
        url        : "/plm/ext/dqm/reReview.do",
        data       :{
            oid : "${dqm.oid}"
        },
        dataType   : "json",
        success    : function(data){
            alert('<%=messageService.getString("e3ps.message.ket_message", "09060") %><%--재검토되었습니다.--%>');
            var flag = false;
            try {
                parent.opener.dqm.search();
                flag = true;
            }
            catch (e) {
                // TODO: handle exception
            }
            
            try { 
                if(!flag){
                    parent.opener.location.reload();
                }
           } catch(error) {
               
           }
            
            parent.location.href = '/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid=${dqm.oid}';
        },
        error    : function(xhr, status, error){
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
    
}




function doSave()
{
    var form = document.forms[0];
    
    if( check() )
    {
    	
    	//설계변경 사유
        var cnt = 0;
        form.changeReason.value = "";
        for(var i=0; i<form.chk_chg_reason.length; i++)
        {
            if(form.chk_chg_reason[i].checked)
            {
                cnt++;
                if(cnt < 2) {
                    form.changeReason.value = form.chk_chg_reason[i].value;
                } else {
                    form.changeReason.value += "|" + form.chk_chg_reason[i].value;
                }
            }
        }
        
        /*
        if(form.changeReason.value.indexOf("6") < 0) {
            form.other_reason.value = "";
        }
        */
        
        //고객확인 구분
        var custCnt = 0;
        form.custom_flag.value = "";
        for(var i=0; i<form.chk_cust.length; i++)
        {
            if(form.chk_cust[i].checked)
            {
                custCnt++;
                if(custCnt < 2) {
                    form.custom_flag.value = form.chk_cust[i].value;
                } else {
                    form.custom_flag.value += "|" + form.chk_cust[i].value;
                }
            }
        }

        if(form.custom_flag.value.indexOf("4") < 0) {
            form.other_cust_flag.value = "";
        }

        //조정및 변경사항
        var factCnt = 0;
        form.chg_fact.value = "";
        for(var i=0; i<form.chk_chg_fact.length; i++)
        {
            if(form.chk_chg_fact[i].checked)
            {
                factCnt++;
                if(factCnt < 2) {
                    form.chg_fact.value = form.chk_chg_fact[i].value;
                } else {
                    form.chg_fact.value += "|" + form.chk_chg_fact[i].value;
                }
            }
        }

        //설계변경 유형
        var typeCnt = 0;
        var chk_chg_type = document.getElementsByName("chk_chg_type");
        var chk_chg_typeLength = (chk_chg_type != null && typeof chk_chg_type != 'undefined') ? chk_chg_type.length : 0;
        
        form.changeType.value = "";
        for(var i=0; i < chk_chg_typeLength; i++)
        {
            if(chk_chg_type[i].checked)
            {
                typeCnt++;
                if(typeCnt < 2) {
                    form.changeType.value = chk_chg_type[i].value;
                } else {
                    form.changeType.value += "|" + chk_chg_type[i].value;
                }
            }
        }

        // 저장시 ECN에 대한 유효성을 검사할 필요 없다. ECO 상신시 하면된다.
        if(true) //if( checkDoc() )    // ECN 유효성 검사
        {
            
            // 이노디터 처리
            $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
            $('[name=webEditorText]').val(fnGetEditorText(0));

            // 이노디터 처리
            $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
            $('[name=webEditorText1]').val(fnGetEditorText(1));

            
            var refDocStr = setDocList();   // ECN 정보 컨버팅
            document.forms[0].doc_list.value = refDocStr;
            
            
            document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
            //document.forms[0].target='_self';
            document.forms[0].target='download';
            disabledAllBtn();
            showProcessing();
            document.forms[0].submit();
        }
        else
        {
            return;
        }
    }
    else
    {
        return;
    }
}

function deleteDataLineMinus1(formName, tableElementId, checkboxName, allCheckName, listVarName) {
    var body = document.getElementById(tableElementId);
    if (body.rows.length == 0) return;
    var formNameStr = "document." + formName + ".";
    var objChecks = eval(formNameStr + checkboxName);
    var objAllChecks = eval(formNameStr + allCheckName);

    if( isChecked(checkboxName) )
    {
        var listVal = "";
        var objList;
        if(listVarName != "") {
            objList = eval(formNameStr + listVarName);
            listVal = objList.value;
        }

        var size = body.rows.length - 1;
        var curRow = 0;
        if(size == 1) {
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
                body.deleteRow(1);
            }
        } else {
            for (var i = body.rows.length; i > 1; i--) {
                curRow = i - 2;
                if (objChecks[curRow].checked) {
                    if(listVal == "") {
                        listVal = objChecks[curRow].value;
                    } else {
                        listVal += "*" + objChecks[curRow].value;
                    }
                    body.deleteRow(i - 1);
                }
            }
        }
        if (size < 1) {
            objAllChecks.checked = false;
        }
        if(listVarName != "") {
            objList.value = listVal;
        }
    }
    else
    {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
        return;
    }
}

function isExistMoldChg()
{
    var isExist = false;
    var isProduction = false;

    var div_flag = document.all("dev_yn")
    for( var inx=0; inx < div_flag.length; inx++ )
    {
        if( div_flag[inx].checked)
        {
            isProduction = true;
        }
    }
    var chk_chg_fact = document.all("chk_chg_fact");

    if( isProduction )
    {
        for( var inx=0; inx < chk_chg_fact.length ; inx++)
        {
            if( chk_chg_fact[inx].checked )
            {
                if( chk_chg_fact[inx].value == 'FACT_3' || chk_chg_fact[inx].value == 'FACT_4' )
                {
                    isExist = true;
                }
            }
        }
    }

    return isExist;
}

//부품 상세조회 팝업
function viewRelPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function delProject()
{
    document.forms[0].project_oid.value =  '';
    document.forms[0].project_id.value =  '';
    document.forms[0].project_name.value =  '';
}

//부품 상세조회 팝업
function viewRelPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//체크박스 언체크 처리
function lfn_uncheck_chkPostAct(pos) {
    var form = document.forms[0];
    
    if(typeof form.chkPostAct.length == 'undefined') {
     if(form.relEcaDocWorkerOid.value == '' && form.completeRequestDate.value == '') {
       form.chkPostAct.checked = false;
     }
           
    } else {
     if(form.relEcaDocWorkerOid[pos].value == '' && form.completeRequestDate[pos].value == '') {
       
         if(form.chkPostAct[pos].style.display == 'none') {
             // 추가하여 생성된 ECN은 체크박스 언체크 처리에서 제외한다.
             
         } else {
             form.chkPostAct[pos].checked = false;   
         }
       
     }
     
    }
}
</script>

</head>
<body onload="javascript:init();">
<form  name="CreateProdEcoForm" method="post" action="/plm/servlet/e3ps/ProdEcoServlet" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Create">
<input type="hidden" name="prePage">
<input type="hidden" name="tabName" value="">
<input type="hidden" name="oid" value="">
<input type="hidden" name="ecoId" value="">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="divisionFlag" value="">
<input type="hidden" name="changeReason" value="">
<input type="hidden" name="custom_flag" value="">
<input type="hidden" name="chg_fact">
<input type="hidden" name="changeType"><!-- 설계변경 유형 -->
<input type="hidden" name="deleteFileList" value="">
<input type="hidden" name="deleteRelEcrList" value="">
<input type="hidden" name="deleteRelPartList" value="">
<input type="hidden" name="deleteRelBomList" value="">
<input type="hidden" name="deleteRelEpmList" value="">
<input type="hidden" name="deleteRelDocList" value="">
<input type="hidden" name="pre_domestic_yn">
<input type="hidden" name="doc_list">

<!-- 프로젝트에서 산출물로 ECO 직접작성 -->
<input type="hidden" name="projectOutputOid" value="">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02539") %><%--제품 ECO 등록--%></td>
                  <!-- td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02539") %><%--제품 ECO 등록--%>
                </tr -->
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">&nbsp;</td>
            <td class="font_03">
            
                <table id="infoShow" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>                    
                </tr>
                </table>
                <table id="infoHide" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(3);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>                     
                </tr>
                </table>
                <table id="infoEcn" border="0" cellspacing="0" cellpadding="0" style="display: none;">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(1);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03399") %><%--기본사항--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>

                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_4.png"></td>
                            <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "04290") %><%--설변부품/도면--%></a>
                            </td>
                            <td><img src="/plm/portal/images/tab_5.png""></td>
                        </tr>
                        </table>
                    </td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><img src="/plm/portal/images/tab_1.png"></td>
                            <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                <%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%>
                            </td>
                            <td><img src="/plm/portal/images/tab_2.png"></td>
                        </tr>
                        </table>
                    </td>
                                       
                </tr>
                </table>

            </td>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
      </table>

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <!-- table width="100%" height="100%" border="0" cellspacing="0" cellpadding="10">
              <tr>
                <td valign="top">&nbsp;</td>
              </tr>
            </table -->

            <!--내용-->
            <div style="position:; width:100%; height:656px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">

                <%@include file="/jsp/ecm/ModifyProdEcoBasicForm.jsp" %>

            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">

                <%@include file="/jsp/ecm/ModifyProdEcoActivityForm.jsp" %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
              
                 <%@include file="/jsp/ecm/reform/ModifyProdEcnForm.jsp" %>
                 
            </div>  
            </div> 
            
            </td>
          <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
        </tr>
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
          <td height="10" background="/plm/portal/images/box_16.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
        </tr>
      </table>
      
    </td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
<iframe name="setCarData" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
