<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page import="java.util.Vector
               ,java.util.Hashtable
               ,java.util.ArrayList
               ,java.util.StringTokenizer
               
               ,wt.fc.*
               ,wt.query.*
               ,wt.org.WTUser
               ,wt.session.SessionHelper
               ,wt.content.ApplicationData
               ,wt.util.WTProperties
               
               ,wt.workflow.engine.WfProcess
               ,wt.workflow.work.WfAssignedActivity
               ,wt.workflow.work.WorkItem
               ,wt.pds.StatementSpec

               ,wt.util.WTProperties
               ,wt.content.ContentHolder
               ,wt.content.ContentHelper
               ,wt.content.ContentItem" %>

<%@page import="e3ps.ecm.beans.*
               ,e3ps.ecm.entity.*
               ,e3ps.common.code.*
               ,e3ps.common.content.*
               ,e3ps.common.util.*              
               ,e3ps.project.beans.MoldProjectHelper" %>
               
<%@page import="ext.ket.shared.util.SearchUtil" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="ecaType" class="java.lang.String" scope="request"/>
<jsp:useBean id="tabName" class="java.lang.String" scope="request"/>

<%
	WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
	WTUser owner = (WTUser)prodEco.getCreator().getPrincipal();
	
	boolean isExistMChange = false;
	boolean isOwner = false;
	if( owner.equals( loginUser ) )
	{
	  isOwner = true;
	}
	
	
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
	// 설계변경 상세사유
	ArrayList<Hashtable<String, String>> changeDetailReasonList = codeHash.get("changeDetailReasonList");
	
	ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEco );
	Vector attachFileList = ContentUtil.getSecondaryContents(holder);
	
	String preAction = StringUtil.checkNull(request.getParameter("preAction"));
	
	String userGroupStr = KETObjectUtil.getCurrentUserGroup();
	String isMDrawing  = EcmSearchHelper.manager.isManufacturingDrawing(prodEco);
	
	ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
	ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
	ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
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
   
   int ecoHashSize = (ecoHash != null) ? ecoHash.size() : 0;
   if( ecoHashSize != 0) {
       
       eco_name = ecoHash.get("eco_name").toString();
       dev_yn = ecoHash.get("dev_yn").toString();
       project_oid = ecoHash.get("project_oid").toString();
       project_no = ecoHash.get("project_no").toString();
       project_name = ecoHash.get("project_name").toString();
       other_chg_reason_desc = ecoHash.get("other_chg_reason_desc").toString();
       other_cust_chk_desc = ecoHash.get("other_cust_chk_desc").toString();
       status = ecoHash.get("status").toString();    
   
       if(status.equals("ACTIVITYCOMPLETED") )
       {
           isAddableActivity = false;
       }
       
   }

   
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
   String reviewResult = ecoHash.get("review_result").toString();
   
   // 변경 전
   String webEditor = ecoHash.get("web_editor").toString();
   String webEditorText = ecoHash.get("web_editor_text").toString();

   // 변경 후
   String webEditor1 = ecoHash.get("web_editor_1").toString();
   String webEditorText1 = ecoHash.get("web_editor_text_1").toString();
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

   
   // 초도인지 아닌지
   String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
   boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
   %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02540") %><%--제품 ECO 변경활동--%></title>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 10px;
  margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script type="text/javascript" src="/plm/jsp/common/content/content.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/searchUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script language="javascript">
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

function init() {
  
  <%-- 	
  <%
  if( isOwner && !ecaType.equals("3") && !ecaType.equals("4") )
  {
  %>
    ownerInit();
  <%
  }
  %>
  --%>

  document.forms[0].preAction.value='<%=preAction%>';
  if( document.forms[0].preAction.value == 'R' )
  {
	
	/*   
    clickTabBtn(1);
    clickTabBtn(2);
    */
    
    var ecaType = document.forms[0].ecaType.value;  
    if(ecaType == '1' || ecaType == '2') {
      clickTabBtn(2);
    } else if(ecaType == '3' || ecaType == '4') {
      clickTabBtn(3);
    } else {
      clickTabBtn(1);
    }
    
  }
  else
  {
      
    var ecaType = document.forms[0].ecaType.value;  
    if(ecaType == '1' || ecaType == '2') {
      clickTabBtn(2);
    } else if(ecaType == '3' || ecaType == '4') {
      clickTabBtn(3);
    } else {
      clickTabBtn(1);
    }
    
  }
}

function ownerInit()
{
    var form = document.forms[0];

    deleteAllSelectBox( form.div_flag );
    deleteAllSelectBox( form.effective_date );
    deleteAllSelectBox( form.inv_process );

    if( '<%=ecoHash.get("division_flag").toString()%>' == 'C'  )
    {
      deleteAllSelectBox( form.domestic_yn );
    }

    <%
    Hashtable<String, String> divHash = new Hashtable<String, String>();
    for(int divCnt = 0 ; divCnt < divList.size(); divCnt++ )
    {
      divHash = divList.get(divCnt);
    %>
      addSelectBox( form.div_flag, '<%=divHash.get("name")%>', '<%=divHash.get("code")%>', '<%=ecoHash.get("division_flag").toString()%>');
    <%
    }

    Hashtable<String, String> effDate = new Hashtable<String, String>();
    for( int eCnt=0; eCnt < effDateList.size() ; eCnt++ )
    {
      effDate = effDateList.get( eCnt );
    %>
      addSelectBox( form.effective_date, '<%=effDate.get("name")%>', '<%=effDate.get("code")%>', '<%=ecoHash.get("effective_date_flag").toString()%>');
    <%
    }

    Hashtable<String, String> invProc = new Hashtable<String, String>();
    for( int pCnt=0 ; pCnt < invProcList.size(); pCnt++ )
    {
      invProc = invProcList.get( pCnt );
    %>
      addSelectBox( form.inv_process, '<%=invProc.get("name")%>', '<%=invProc.get("code")%>', '<%=ecoHash.get("inv_clear").toString()%>');
    <%
    }
    %>

    if( '<%=ecoHash.get("division_flag").toString()%>' == 'C'  )
    {
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>', '', '<%=ecoHash.get("domestic_yn_code").toString()%>');
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00983") %><%--국내--%>', '1000', '<%=ecoHash.get("domestic_yn_code").toString()%>');
      addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00985") %><%--국외--%>', '2000', '<%=ecoHash.get("domestic_yn_code").toString()%>');

      searchCarData( 'maker', '<%=ecoHash.get("domestic_yn_code").toString()%>' , '<%=ecoHash.get("car_maker").toString()%>');
      searchCarData( 'car', '<%=ecoHash.get("car_maker").toString()%>', '<%=ecoHash.get("car_category").toString()%>' );
    }

    var chkChgReason = document.getElementsByName( 'chk_chg_reason' );
    <%
    String strChgReason = ecoHash.get("chg_reason").toString();
    StringTokenizer st = new StringTokenizer( strChgReason, "|" );
    ArrayList<String> chgReasonList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      chgReasonList.add( st.nextToken() );
    }

    for( int rCnt=0; rCnt < chgReasonList.size(); rCnt++ )
    {
    %>
      for( var inx=0; inx < chkChgReason.length ; inx++)
      {
        if( chkChgReason[inx].value == '<%=chgReasonList.get(rCnt)%>' )
        {
          chkChgReason[inx].checked = true;
        }
        /* 
        if( inx == 5 )
        {
          disable('other_reason', chkChgReason[inx].checked );
        }
        */
      }
    <%
    }
    %>

    var chkCustom = document.getElementsByName( 'chk_cust' );
    <%
    String strChkCustom = ecoHash.get("cust_chk").toString();
    st = new StringTokenizer( strChkCustom, "|" );
    ArrayList<String> customChkList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      customChkList.add( st.nextToken() );
    }

    for( int cCnt=0; cCnt < customChkList.size(); cCnt++ )
    {
    %>
      for( var inx=0; inx < chkCustom.length ; inx++)
      {
        if( chkCustom[inx].value == '<%=customChkList.get(cCnt)%>' )
        {
          chkCustom[inx].checked = true;
        }

        if( inx == 3 )
        {
          disable( 'other_cust_flag', chkCustom[inx].checked );
        }
      }
    <%
    }
    %>

    var chgFact = document.getElementsByName( 'chk_chg_fact' );
    <%
    String strChgFact = ecoHash.get("chg_fact").toString();
    st = new StringTokenizer( strChgFact, "|" );
    ArrayList<String> factList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
      factList.add( st.nextToken() );
    }

    for( int fCnt=0; fCnt < factList.size(); fCnt++ )
    {
    %>
      for( var inx=0; inx < chgFact.length ; inx++)
      {
        if( chgFact[inx].value == '<%=factList.get(fCnt)%>' )
        {
          <% isExistMChange = true;  %>
          chgFact[inx].checked = true;
        }
      }
    <%
    }
    %>

    
    // 설계변경 유형
    var chgType = document.getElementsByName( 'chk_chg_type' );
    <%
    String strChgType = ecoHash.get("chg_type").toString();
    st = new StringTokenizer( strChgType, "|" );
    ArrayList<String> typeList = new ArrayList<String>();
    while( st.hasMoreTokens() )
    {
        typeList.add( st.nextToken() );
    }

    for( int tCnt=0; tCnt < typeList.size(); tCnt++ )
    {
    %>
      for( var inx=0; inx < chgType.length ; inx++)
      {
        if( chgType[inx].value == '<%=typeList.get(tCnt)%>' )
        {
            chgType[inx].checked = true;
        }
      }
    <%
    }
    %>

        
    var chChgChk = document.getElementsByName( 'chk_cu' );

    for( var inx=0; inx < chChgChk.length ; inx++)
    {
      if( chChgChk[inx].value == '<%=ecoHash.get("cu_chg_yn").toString()%>' )
      {
        chChgChk[inx].checked = true;
      }
    }
}
//첨부파일 관련 스크립트 시작
  function insertFileLine()
  {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for( var k=0 ; k < 2 ; k++ )
    {
        innerCell = innerRow.insertCell();

        if (k == 0)
        {
             innerCell.className = "tdwhiteM";
               innerCell.innerHTML = "<input name='fileSelect' type='checkbox' class='chkbox'>";
      }
        else if (k == 1)
        {
            innerCell.className = "tdwhiteL0";
              innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:99%'>";
        }
    }

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

    if( isChecked(checkboxName) )
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

    if(value) {
      obj.disabled = false;
    } else {
      obj.disabled = true;
    }
    }

//연계ECR 검색 팝업 호출
function popupRelEcr() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
  url += "&obj=prodMoldCls^1&obj=mode^multi";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=1000px; dialogHeight:670px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
//  var attache = getSampleRelEcrData();
  insertRelEcrLine(attache);
}

//연계ECR 라인추가(projectIssueCreate.jsp 참조)
function insertRelEcrLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relEcrTable");

  var trArr;
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);

    //전체선택 checkbox
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "40";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<input name='chkSelectRelEcr' type='checkbox' value=''>";

    //ECR번호
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "120";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<a href=\"javascript:viewRelEcr('" + trArr[0] + "');\">" + trArr[1] + "</a>";
    newTd.innerHTML += "<input type='hidden' name='relEcrOid' value='" + trArr[0] + "'>";
    newTd.innerHTML += "<input type='hidden' name='relEcrId' value='" + trArr[1] + "'>";

    //ECR 제목
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "162";
    newTd.className = "tdwhiteL";
    str ="";
    str +="<font title='"+trArr[5]+"'>";
    str += "<div class='ellipsis' style='width:162;'><nobr>";
    str += trArr[5] +"</nobr></div></font>";
    newTd.innerHTML = str;

    //작성부서
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "100";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

    //작성자
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "90";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

    //승인일자
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.width = "100";
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
  }
}

<% /* deprecated */ %>
<% /* 관련모부품 검색 */ %>
function popupPart( fncall )
{
  var url="/plm/ext/part/base/listPartPopup.do?mode=multi&pType=P&fncall="+fncall;
  openWindow(url,"","1024","768","status=1,scrollbars=no,resizable=no");
}

<% /* deprecated */ %>
<% /* 관련모부품 추가 */ %>
function setParentPart( objArr )
{
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relBomTable");
  var form = document.forms[0];

  var tableRows = targetTable.rows.length;
  var pos = targetTable.clickedRowIndex - 2;

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( tableRows > 3 )
    {
      form.parentPart[pos].value += trArr[1];

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

function selectedPart(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relPartTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !partDuplicateCheck(trArr[0]))
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="27";
        newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "40";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input name='chkSelectRelPart' type='checkbox' value=''>";

      //Part No
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "85";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + getTruncStr(trArr[1], 10) + "</a>";
      str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relPartLinkOid' value=''>";
      newTd.innerHTML = str;

      //Part Name
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "130";
      newTd.className = "tdwhiteL";
      newTd.Title= trArr[2];
      str = "";
      str += "<div class='ellipsis' style='width:120;'><nobr>";
      str += trArr[2] +"&nbsp;</nobr></div>";
        newTd.innerHTML = str;

      //Die No
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "90";
      newTd.className = "tdwhiteM";
      str = "";
      if( trArr[5] != '' )
      {
        //str += getTruncStr(trArr[5], 10);
        //str += "<input type='hidden' name='relDieNo' value='" + trArr[5] + "'>";

        if( trArr[7] > 1 )
        {
          str += "<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:55' readonly>&nbsp;";
          str +="&nbsp;<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        }
        else
        {
          str += "&nbsp;<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:80' readonly>&nbsp;";
        }
        newTd.innerHTML = str;
      }
      else
      {
        str +=  "&nbsp;"
        str += "<input type='hidden' name='relDieNo' value=''>";
        newTd.innerHTML = str;
      }

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "45";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[3]+ "&nbsp;";

      //예상비용(천원)
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "100";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='text' name='expectCost' class='txt_fieldR' style='width:90'>";

      //비용확보여부
      newTd = newTr.insertCell(newTr.cells.length);
      //newTd.width = "";
      newTd.className = "tdwhiteL0";
      str = "";

      var partStr = trArr[1]+"";
      subValue =  partStr.substr(0,3);
      if( (subValue == 'R40'  || subValue == 'R41' || subValue == 'R50' || subValue == 'R60' || subValue == 'R68' )
       || (subValue == 'H42'  || subValue == 'H43'  || subValue == 'H45'  || subValue == 'H46' || subValue == 'H47' || subValue == 'H52' || subValue == 'H54' || subValue == 'H57' || subValue == 'H59' || subValue == 'H64' || subValue == 'H65' || subValue == 'H66' )
         || (partStr.substr(0,2) == '68') )
      {
        str += "<table width='100' border='0' cellspacing='0' cellpadding='0'>";
        str += "  <tr>";
        str += "    <td width='45' align='middle'><input type='hidden' name='budget' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'></td>";
        str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
        str += "      <tr>";
        str += "      <td width='30'></td>";
        //str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
        //str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
        //str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
        str += "      </tr>";
        str += "    </table></td>";
        str += "  </tr>";
        str += "</table>";
      }
      else
      {
        str += "<table width='100' border='0' cellspacing='0' cellpadding='0'>";
        str += "  <tr>";
        str += "    <td width='45' align='middle'><input type='text' name='budget' value='미확보' class='txt_field' style='width:45' readonly><input type='hidden' name='secureBudgetYn' value='N'></td>";
        str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
        str += "      <tr>";
        str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
        str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>";
        str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
        str += "      </tr>";
        str += "    </table></td>";
        str += "  </tr>";
        str += "</table>";
      }
      newTd.innerHTML = str;
    }
  }
}

function partDuplicateCheck(poid) {
//부품추가시 선택된 부품정보를 중복체크한다.
  var tBody = document.getElementById("relPartTable");
  var rowsLen = tBody.rows.length;
  if(rowsLen > 0) {
    var primaryPart = document.forms[0].relPartOid;
    var partLen = primaryPart.length;
    if(partLen) {
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

function deletePartLine() {
//부품삭제버튼 클릭시 선택된 부품정보를 삭제한다.
  var body = document.getElementById("iPartTable");
  if (body.rows.length == 0) return;
  var part_checks = document.forms[0].iPartChk;

  if (body.rows.length == 1) {
    if (part_checks[0]=="[object]"){
      if (part_checks[0].checked){
        body.deleteRow(0);
      }
    }else{
      if (part_checks.checked){
        body.deleteRow(0);
      }
    }
  } else {
    for (var i = body.rows.length; i > 0; i--) {
      if (part_checks[i-1].checked) body.deleteRow(i - 1);
    }
  }
}

function popupProject()
{
  var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P";
  openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(objArr) {

  if(objArr.length == 0) {
    return;
  }

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++)
  {
    trArr = objArr[i];

    document.forms[0].project_oid.value= trArr[0];
    document.forms[0].project_id.value = trArr[1];
    document.forms[0].project_name.value= trArr[2];
  }
}

function doCancel()
{
  if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
  { 
	/*   
    document.forms[0].action="/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
    document.forms[0].target="_self";
    document.forms[0].submit();
    */
    
    window.close();
    
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

   if(pre_domestic_flag != value )
   {
     deleteAllSelectBox( document.forms[0].car_category );
   }

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
    var objTarget;
  var paramObj;
  if( typeof(target)!="object")
  {
    paramObj = document.all.item(target);

    if(paramObj == null)
    {
      paramObj = eval("document.forms[0]." + target);
    }
  }
  else{
    paramObj = target;
  }

  if(typeof(paramObj)=="object"){
      objTarget = eval(paramObj);
  }
  addSelectBox( objTarget, display_name, value, selected_txt );
}

function delCarData( target )
{
  var objTarget;
  var paramObj;
  if( typeof(target)!="object")
  {
    paramObj = document.all.item(target);

    if(paramObj == null)
    {
      paramObj = eval("document.forms[0]." + target);
    }
  }
  else{
    paramObj = target;
  }

  if(typeof(paramObj)=="object"){
      objTarget = eval(paramObj);
  }

  deleteAllSelectBox( objTarget );
}

<% /* deprecated */ %>
function doSave()
{
  var form = document.forms[0];

  if( check() )
  {
	  
    <%if( isOwner && !ecaType.equals("3") && !ecaType.equals("4") ){%>
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
    <%}else{

      if(ecaType.equals("3") || ecaType.equals("4") )
      {
    %>
        var refDocStr = setDocList();
        document.forms[0].doc_list.value = refDocStr;
        //return;
    <%
      }
      if( (ecaType.equals("3") || ecaType.equals("4")) || !isOwner )
      {
    %>
        document.forms[0].cmd.value= "ModifyNotOwner";
    <%
      }
    }
    %>
    
    document.forms[0].isChanged.value = 'N';
    document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
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

function doRevise( flag )
{
    document.forms[0].isChanged.value = 'N';
    document.forms[0].cmd.value = flag;
    document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
    //document.forms[0].target='_self';
    document.forms[0].target='download';

    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
}

//ECO담당자검색 팝업
function popupUser( inx )
{
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  setUser( inx, attacheMembers );
}

//표준품 담당자 검색 팝업 리턴 포맷
function setUser( inx, objArr)
{
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var relEcaDocWorkerOid = 'relEcaDocWorkerOid'+inx;
  var relEcaDocWorkerName = 'relEcaDocWorkerName'+inx;

  relEcaDocWorkerOid = eval('form.'+relEcaDocWorkerOid);
  relEcaDocWorkerOid.value = trArr[0];
  relEcaDocWorkerName = eval('form.'+relEcaDocWorkerName);
  relEcaDocWorkerName.value = trArr[4];
}

//ECO담당자 초기화
function clearUser( inx )
{
  var form = document.forms[0];

  var relEcaDocWorkerOid = 'relEcaDocWorkerOid'+inx;
  var relEcaDocWorkerName = 'relEcaDocWorkerName'+inx;

  relEcaDocWorkerOid = eval('form.'+relEcaDocWorkerOid);
  relEcaDocWorkerName = eval('form.'+relEcaDocWorkerName);

  relEcaDocWorkerOid.value = '';
  relEcaDocWorkerName.value = '';
}

function check()
{
  var form = document.forms[0];
  var isValid = true;
  <%
  if( isOwner && !ecaType.equals("3") && !ecaType.equals("4") )
  {
  %>
        var dev_yn = document.all("dev_yn");
        var str_dev_yn = '';
        for( var inx=0; inx < dev_yn.length ; inx++)
        {
          if( dev_yn[inx].checked )
          {
            str_dev_yn  = dev_yn[inx].value;
          }
        }
    
        var chk_chg_reason = document.all("chk_chg_reason");
        var str_chk_chg_reason = '';
          for( var inx=0; inx < chk_chg_reason.length ; inx++)
        {
          if( chk_chg_reason[inx].checked )
          {
            str_chk_chg_reason  += chk_chg_reason[inx].value;
          }
        }
    
        var targetTable = document.getElementById("relPartTable");
    
        var chk_cust = document.all("chk_cust");
        var str_chk_cust = '';
          for( var inx=0; inx < chk_cust.length ; inx++)
        {
          if( chk_cust[inx].checked )
          {
            str_chk_cust  += chk_cust[inx].value;
          }
        }
    
        var chk_chg_fact = document.all("chk_chg_fact");
        var str_chk_chg_fact = '';
          for( var inx=0; inx < chk_chg_fact.length ; inx++)
        {
          if( chk_chg_fact[inx].checked )
          {
            str_chk_chg_fact  += chk_chg_fact[inx].value;
          }
        }
    
        if( form.eco_name.value == '' )
        {
          isValid = false;
          alert('<%=messageService.getString("e3ps.message.ket_message", "02526") %><%--제목을 입력하세요--%>');
        }
        else if( str_dev_yn == '' )
        {
          isValid = false;
          alert('<%=messageService.getString("e3ps.message.ket_message", "01191") %><%--단계 구분을 선택하세요--%>');
        }
        else if( str_dev_yn == 'D' && form.project_id.value == '' )
        {
          isValid = false;
          alert('<%=messageService.getString("e3ps.message.ket_message", "04930") %><%--개발단계가 \'양산\'이 아닐 경우 프로젝트 정보를 입력하셔야 합니다.--%>');
        }
        else if( targetTable.rows.length <=0 )
        {
          isValid = false;
          alert('<%=messageService.getString("e3ps.message.ket_message", "00914") %><%--관련 부품을 추가하세요--%>');
        }
        else if( str_chk_chg_reason == '' )
        {
          isValid = false;
          alert("<%=messageService.getString("e3ps.message.ket_message", "01855") %><%--설계변경사유를 선택하여 주십시오--%>");
        }
        <%-- 
        else if( str_chk_chg_reason != '' && str_chk_chg_reason.indexOf('REASON_6') > 0 && form.other_reason.value == '' )
        {
          isValid = false;
          alert("<%=messageService.getString("e3ps.message.ket_message", "01846") %>설계변경 기타사유를 입력하여 주십시오");
        }
        --%>
        else if( str_chk_cust != '' && str_chk_cust.indexOf('CHECK_4')> 0 && form.other_cust_flag.value == '' )
        {
          isValid = false;
          alert("<%=messageService.getString("e3ps.message.ket_message", "00864") %><%--고객확인 기타 내용을 입력하여 주십시오--%>");
        }
  <%
  }

  if( !ecaType.equals("3") && !ecaType.equals("4") )
  {
  %>
        var str_dev_yn = '<%=ecoHash.get("dev_yn").toString()%>';
        var str_chk_chg_fact = '<%=ecoHash.get("chg_fact").toString()%>';
    
        if( str_dev_yn == "P" && (str_chk_chg_fact.indexOf('FACT_3') > 0 || str_chk_chg_fact.indexOf('FACT_4') > 0) )
        {
         //  //개발/양산구분이 P:양산인 경우 도면의 연계일정은 필수입력항목이 된다.
         //  //도면/BOM 입력항목 체크
        //  //양산단계
        //  var relEpmTable = document.getElementById("relEpmTable");
        //  var relEpmTableRows = relEpmTable.rows.length;
        //  var epmCnt = 0;
        //  var planCnt = 0;
        //  var curRow = 0;
        //  if(relEpmTableRows > 1) {
        //    if(relEpmTableRows > 2) {
        //      for(var i = 1; i < relEpmTableRows; i++) {
        //        curRow = i - 1;
        //        if(form.scheduleId[curRow].value != "") {
        //          planCnt++;
        //        }
        //        if(form.relEcaEpmActivityType[curRow].value == "1") {//도면
        //          epmCnt++;
        //        }
        //      }
        //    }else{
        //      if(form.scheduleId.value != "") {
        //        planCnt++;
        //      }
        //      if(form.relEcaEpmActivityType.value == "1") {//도면
        //        epmCnt++;
        //      }
        //    }
        //    if(planCnt != epmCnt)
        //    {
        //      isValid = false;
        //      alert("연계일정을 입력하세요.");
        //    }
        //  }
        }
        
        <%-- 
        else if( '<%=ecaType%>'=="2" )
        {
          var relBomTable = document.getElementById("relBomTable");
          var relBomTableRows = relBomTable.rows.length;
          var curRow = 0;
    
          if( relBomTableRows > 2 )
          {
            if( relBomTableRows > 3 )
            {
              for( var i = 0; i < relBomTableRows-2; i++)
              {
                curRow = i;
                if(form.relEcaActivityType[curRow].value == "2")
                {
                  if( form.masschange_yn[curRow].value == 'Y' )
                  {
                    if( form.parentPart[curRow].value == '' )
                    {
                      isValid = false;
                      alert("<%=messageService.getString("e3ps.message.ket_message", "00912") %>관련 모부품을 입력하세요");
                      return;
                    }
                  }
                }
              }
            }
            else
            {
              if(form.relEcaActivityType.value == "2")
              {
                if( form.masschange_yn.value == 'Y' )
                {
                  if( form.parentPart.value == '' )
                  {
                    isValid = false;
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00912") %>관련 모부품을 입력하세요");
                    return;
                  }
                }
              }
             }
          }
        }
        --%>
        
   <%
  }
   %>

  return isValid;
}

function deleteDataLineMinus1(formName, tableElementId, checkboxName, allCheckName, listVarName) {
  var body = document.getElementById(tableElementId);
  if (body.rows.length <= 1) return;
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

    form.isChanged.value = 'Y';
  }
  else
  {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01715") %><%--삭제할 항목을 선택하세요--%>");
    return;
  }
}

function deleteDataLineMinus2(formName, tableElementId, checkboxName, allCheckName, listVarName) {
  var body = document.getElementById(tableElementId);
  if (body.rows.length <=2) return;
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
    var size = body.rows.length - 2;
    var curRow = 0;
    var objPreRev;
    var objAfterRev;
    if(size == 0) {
      return;
    } else if(size == 1) {
      if (objChecks.checked || objChecks[0].checked) {
        if(objChecks.checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relObjPreRev");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relObjPreRev");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relObjPreRev");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev");
          }
          if(objAfterRev.value > objPreRev.value) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00688") %><%--개정된 자료는 삭제 불가합니다--%>");
            return;
          }
          if(listVal == "") {
            listVal = objChecks.value;
          } else {
            listVal += "*" + objChecks.value;
          }
        } else if(objChecks[0].checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev[0]");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev[0]");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[0]");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev[0]");
          }
          if(objAfterRev.value > objPreRev.value) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00688") %><%--개정된 자료는 삭제 불가합니다--%>");
            return;
          }
          if(listVal == "") {
            listVal = objChecks[0].value;
          } else {
            listVal += "*" + objChecks[0].value;
          }
        }
        body.deleteRow(2);
      }
    } else {
      var hasNotPass = false;
      
      for (var i = body.rows.length; i > 2; i--) {
        curRow = i - 3;
        if (objChecks[curRow].checked) {
          if(tableElementId == "relEpmTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaEpmAfterRev[" + curRow + "]");
          } else if(tableElementId == "relBomTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaBomAfterRev[" + curRow + "]");
          } else if(tableElementId == "relDocTable") {
            objPreRev = eval(formNameStr + "relObjPreRev[" + curRow + "]");
            objAfterRev = eval(formNameStr + "relEcaDocAfterRev[" + curRow + "]");
          }
          
          var isPass = true;
          if(objAfterRev.value > objPreRev.value) {
            //alert("<%=messageService.getString("e3ps.message.ket_message", "00688") %><%--개정된 자료는 삭제 불가합니다--%>");
            hasNotPass = true;
            isPass = false;
          }
          
          
          if(isPass) {
	          if(listVal == "") {
	            listVal = objChecks[curRow].value;
	          } else {
	            listVal += "*" + objChecks[curRow].value;
	          }
	          body.deleteRow(i - 1);
	          
          }
          
          
        }
      }
      
      if(hasNotPass) {
    	  alert("<%=messageService.getString("e3ps.message.ket_message", "00688") %><%--개정된 자료는 삭제 불가합니다--%>");
          
      }
      
    }
    if (size <= 2) {
      objAllChecks.checked = false;
    }
    if(listVarName != "") {
      objList.value = listVal;
    }

    document.forms[0].isChanged.value = 'Y';
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

  var chk_chg_fact = document.all("chk_chg_fact");
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

  return isExist;
}

<% /* 작업완료 */ %>
<% /* deprecated */ %>
function doComplete2()
{
	if( confirm("<%=messageService.getString("e3ps.message.ket_message", "04410") %><%--정말로 작업완료하시겠습니까? 작업완료 후 MyTodo에서 사라집니다.--%>") )
	{ 
	
	    document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
	    document.forms[0].cmd.value='Complete';
	    //document.forms[0].target='_self';
	    document.forms[0].target='download';
	    disabledAllBtn();
	    showProcessing();
	    document.forms[0].submit();
	    
	}
}

<% /* deprecated */ %>
function doComplete()
{
  <%-- var chgFlagList;
  var isNotChangedEpmDoc = true;
  
  // 1. 저장했으면
  if( document.forms[0].isChanged.value == 'N' )
  {
	// 2. 도면이면
    if( '<%=ecaType%>' == '1' )
    {
      chgFlagList = document.getElementsByName('relEcaEpmChangeYn');

      for( var cnt=0; cnt < chgFlagList.length; cnt++ )
      {
        if( chgFlagList[cnt].value == '' )
        {
          isNotChangedEpmDoc = false;
          break;
        }
      }
      

      if( !isNotChangedEpmDoc )
      {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01517") %>변경을 하지 않은 도면이 있습니다');
        return;
      }
      
    }

    // 3. BOM이면
    <%
    Hashtable<String, String> rtnHash = EcmSearchHelper.manager.getCanCompleteFlagInProdECO(prodEco, ecaType);
    if( rtnHash != null && !rtnHash.isEmpty() )
    {
       if( rtnHash.get("flag").equals("TRUE") )
       {
    %>
         document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
         document.forms[0].cmd.value='Complete';
         //document.forms[0].target='_self';
         document.forms[0].target='download';
         disabledAllBtn();
         showProcessing();
         document.forms[0].submit();
    <%
       }
       else
       {
    %>
         alert('<%=rtnHash.get("msg")%>');
    <%
       }
    }
    %>
  }
  // 4. 저장안했으면
  else
  {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02459") %>저장 후 작업완료를 진행하세요');
    return;
  } --%>
}

//연계일정검색 팝업
function popupRelPlan(tableId){
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchMoldPlanPopupForm.jsp?oid="+document.forms[0].oid.value;
  arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=770px; dialogHeight:500px; center:yes");
  if(typeof arr == "undefined" || arr == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex - 2;
  setRelPlan(arr, pos);
}

//연계일정 검색 팝업 리턴 포맷
function setRelPlan(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relEpmTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 3){//테이블 헤더가 포함되어 있기 때문에.
    form.newMoldChangePlanOid[pos].value = trArr[0];
    form.scheduleId[pos].value = trArr[2];
    form.dieNo[pos].value = trArr[1];
  } else {
    form.newMoldChangePlanOid.value = trArr[0];
    form.scheduleId.value = trArr[2];
    form.dieNo.value = trArr[1];
  }
}

//연계일정 초기화
function clearRelPlan(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 2;
  if(tableRows > 3){
    form.newMoldChangePlanOid[pos].value = '';
    form.scheduleId[pos].value = '';
    form.dieNo[pos].value = '';
  } else {
    form.newMoldChangePlanOid.value = '';
    form.scheduleId.value = '';
    form.dieNo.value = '';
  }
}

//변경예정일 팝업
function popupEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 2;
  if(tableRows > 3){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearEpmCal(objName, tableId)
{
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 2;
  if(tableRows > 3){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}

function viewEpmDocPopup(oid)
{
  var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid +"&frompage=TodoECO";
  openWindow(url,"","820","800","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

//부품 상세조회 팝업
function viewPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function checkBudget()
{
  var form = document.forms[0];
  var pos = relPartTable.clickedRowIndex;
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  var division  = "";
  var dev_yn = "";
  var dieno = "";
  var expectCost = "";

  //HEENEETODO : KETS 사업부일 경우 ERP에 던져줘야 하는 division은 무엇인가?
  if( '<%=userGroupStr%>' == '자동차사업부' )
  {
    division = "1";
  }
  else if( '<%=userGroupStr%>' == 'KETS' )
  {
	division = "3";
  }
  else
  {
    division = "2";
  }

  var dev_yn = document.all("dev_yn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( dev_yn[inx].checked )
    {
      str_dev_yn  = dev_yn[inx].value;
    }
  }

  if( str_dev_yn == "D" )
  {
    dev_yn = "1";
  }
  else
  {
    dev_yn = "2";
  }
  
  var relObjOid = "";
  
  if( tableRows > 1 )
  {
    dieno = form.relDieNo[pos].value;
    expectCost = form.expectCost[pos].value;
    relObjOid = form.relObjOid[pos].value;
  }
  else
  {
    dieno = form.relDieNo.value;
    expectCost = form.expectCost.value;
    relObjOid = form.relObjOid.value;
  }

  if( dev_yn == "2" && isNumber(expectCost) ||  dev_yn == "1" && isNumber(expectCost) && dieno != ''  )
  {
    document.forms[0].target="setCarData";
    document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos+"&partOid="+relObjOid;
    document.forms[0].submit();
  }
  else
  {
    if( dev_yn == "1"  && dieno == ''  )
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00906") %><%--관련 Die No가 존재하지 않아서 예산을 확인할 수 없습니다--%>");
    }
    else if( !isNumber(expectCost)  )
    {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01648") %><%--비용이 존재하지 않아서 예산을 확인할 수 없습니다--%>');
      if( tableRows > 1 )
      {
        form.expectCost[pos].focus();
      }
      else
      {
        form.expectCost.focus();
      }

    }
    return;
  }

}

function setBudgetYn( budget_yn, row_inx )
{
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;

  if( tableRows > 1 )
  {
    if( budget_yn == 'N' )
    {
       document.forms[0].budget[row_inx].value = '미확보';
     }
     else
     {
       document.forms[0].budget[row_inx].value = '확보';
     }
     document.forms[0].secureBudgetYn[row_inx].value = budget_yn;
   }
   else
   {
     if( budget_yn == 'N' )
    {
       document.forms[0].budget.value = '미확보';
     }
     else
     {
       document.forms[0].budget.value = '확보';
     }
     document.forms[0].secureBudgetYn.value = budget_yn;
   }
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
  var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","820","700","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}
//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//수신확인
function doReceive() {
	document.forms[0].cmd.value = "receiveConfirm";
	
    document.forms[0].isChanged.value = 'N';
    document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
    document.forms[0].target='download';
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
}

function fnExcelReport() {

    var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
    tab_text = tab_text + '<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>';
    tab_text = tab_text + '<x:Name>Sheet1</x:Name>';
    tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
    tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
    tab_text = tab_text + "<table border='1px'>";
    var exportTable = $('#bomListExcel').clone();
    exportTable.find('input').each(function (index, elem) { $(elem).remove(); });
    tab_text = tab_text + exportTable.html();
    tab_text = tab_text + '</table></body></html>';
    
    var d = new Date();
    var curr_hour = d.getHours();
    var curr_min = d.getMinutes();
    var curr_sec = d.getSeconds();
    
    var fileName = "bomList"+curr_hour + curr_min + curr_sec  + '.xls';
    // ie 10+
    try{        
        
        //단순 엑셀 다운로드는 아래와 같이하면 됨. 하지만 암호화를 위해 바이너리 문자열을 자바단으로 넘겨 서버에서 암호화 후 내려받음
        var blob = new Blob([tab_text], { type: "application/vnd.ms-excel;charset=euc-kr" })
        
        if(window.navigator.msSaveOrOpenBlob){
            
            window.navigator.msSaveOrOpenBlob(blob, fileName);
            window.saveAs(blob, fileName);
            
        }else if(navigator.userAgent.indexOf("Firefox") !== -1){
            
            var url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = fileName;
            document.body.appendChild(link);
            link.click();
            
            setTimeout(function(){
                document.body.removeChild(link);            
                window.URL.revokeObjectURL(url);
            },100);
            
         }else{
             link.href = window.URL.createObjectURL(blob);
             link.download = fileName;
             link.click();
         }
        
/*         $("#costExcel").val(tab_text);
        
         var url = '/plm/ext/cost/costExcelCreate.do';
        
        $('[name=ToDoProdEcoForm]').attr('action', url);
        $('[name=ToDoProdEcoForm]').attr('target', 'download');
        $('[name=ToDoProdEcoForm]').submit();     */    
    }
    // ie 9이하
    catch(e){
        var data = document.getElementById('bomList');
        var csvData = [];
        var tmpArr = [];
        var tmpStr = '';
        for (var i = 0; i < data.rows[0].cells.length; i++)
        {
        tmpArr.push((data.rows[0].cells[i].innerText || data.rows[0].cells[i].textContent));
        }
        csvData.push(tmpArr.join('\t'));
        for (var i = 1; i < data.rows.length; i++)
        {
        tmpArr = [];
        for (var j = 0; j < data.rows[0].cells.length; j++)
        {
            tmpArr.push(data.rows[i].cells[j].innerHTML);
        }
        csvData.push(tmpArr.join('\t'));
        }
        var output = csvData.join('\n');
        SaveContents(output);
    }
}

</script>

</head>
<body onload="javascript:init();">
<form  name="ToDoProdEcoForm" method="post" action="/plm/servlet/e3ps/ProdEcoServlet" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Modify">
<input type="hidden" name="ecaType" value="<%=ecaType %>">
<input type="hidden" name="tabName" value="<%=tabName %>">

<input type="hidden" name="prePage" value="ToDo">
<input type= "hidden" name ="oid"  value="<%=CommonUtil.getOIDString( prodEco )%>" >
<input type="hidden" name="ecoId" value="<%=prodEco.getEcoId() %>">
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
<input type="hidden" name="preAction">
<input type="hidden" name="bomPartNo">
<input type="hidden" name="bomLinkOid">
<input type="hidden" name="bomMassChange">
<input type="hidden" name="bomReviseYn">
<input type="hidden" name="relatedParentPart">
<input type="hidden" name="isChanged"  value='N'>
<input type="hidden" name="budgetYn"  value='N'>
<input type="hidden" name="activityType"  value=''>
<input type="hidden" name="costExcel" id="costExcel" />
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02540") %><%--제품 ECO 변경활동--%></td>
                <!-- td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02563") %><%--제품ECO등록/수정--%></td>
              </tr -->
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <!-- tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr -->
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
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%></a>
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
                                <%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%>
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
                                <a href="javascript:clickTabBtn(2);" onfocus="this.blur();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03235") %><%--활동계획--%></a>
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
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <!-- td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doReceive();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "수신확인") %><%--수신확인--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td -->      
                <!-- td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete2();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td -->
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
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
            <!-- table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20">&nbsp;</td>
              </tr>
            </table -->
            
            <!--내용-->
            <div style="position:; width:100%; height:670px; overflow-x:auto; overflow-y:auto;">
            <div id="tabBasic" style="position:; display:none; z-index:2; ">
                
                <%-- 
                <%
                if( isOwner && ( ecaType.equals("1") || ecaType.equals("2") ) ) { 
                %>
                  <%@include file="/jsp/ecm/ModifyProdEcoBasicForm.jsp" %>
                <%
                } else { 
                %>
                  <%@include file="/jsp/ecm/ViewProdEcoBasicForm.jsp" %>
                <%
                }
                %>
                --%>
              
                <%@include file="/jsp/ecm/ViewProdEcoBasicForm.jsp" %>
                
            </div>
            <div id="tabActivity" style="position:; display:none; z-index:1; ">
              
                <%
                if( ecaType.equals("1") ) { 
                %>
                  <%@include file="/jsp/ecm/ToDoProdEcoEpmDocForm.jsp" %>
                <%
                } else if( ecaType.equals("2") ) {
                %>
                  <%@include file="/jsp/ecm/ToDoProdEcoBomForm.jsp" %>
                <%
                } else { 
                %>
                  <%@include file="/jsp/ecm/ViewProdEcoActivityForm.jsp" %>
                <%
                }
                %>
              
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
                
                <%
                if(status.equals("APPROVED")) {
                %>
                  <%@include file="/jsp/ecm/reform/ToDoEcnForm.jsp" %>
                <%
                } else { 
                %>
                  <%@include file="/jsp/ecm/reform/ViewProdEcnForm.jsp" %>
                <%
                }
                %>
                
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
      </table></td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" style="display:none"></iframe>
<iframe name="setCarData" width="0" height="0" style="display:none"></iframe>
</body>
</html>
