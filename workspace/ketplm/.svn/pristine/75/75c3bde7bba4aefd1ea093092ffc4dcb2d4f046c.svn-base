<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page import="e3ps.ecm.beans.EcmUtil
               ,e3ps.ecm.beans.ProdEcoBeans
               ,e3ps.ecm.entity.KETProdChangeRequest
               ,e3ps.project.beans.MoldProjectHelper
               ,java.lang.Integer" %>
<%@page import="java.util.Vector
               ,java.util.Hashtable
               ,java.util.ArrayList
               ,java.util.StringTokenizer" %>
<%@page import="wt.content.ContentHolder" %>
<%@page import="wt.content.ContentHelper
               ,wt.content.ContentItem" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.groupware.company.PeopleData"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="ecrHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="tabName" class="java.lang.String" scope="request"/>
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

  ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEco );
  Vector attachFileList = ContentUtil.getSecondaryContents(holder);

  String userGroupStr = KETObjectUtil.getCurrentUserGroup();

  ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
  ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
  ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
  ArrayList<Hashtable<String, String>> docList = (ArrayList)ecoHash.get("docList");
  // 설계변경 상세사유
  ArrayList<Hashtable<String, String>> changeDetailReasonList = codeHash.get("changeDetailReasonList");

  
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
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02563") %><%--제품ECO등록/수정--%></title>

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

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<script language="javascript">
//처리중 화면 전환
function clickTabBtn(tabId) {
	
	if(tabId == '' || tabId == 'tabBasic') tabId = 1;
	else if(tabId == 'tabActivity') tabId = 2;
	else if(tabId == 'tabEcn') tabId = 3;
	
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

  var form = document.ModifyProdEcoForm;
  
  
  
  
  deleteAllSelectBox( form.div_flag );
  console.log(form.div_flag);
  deleteAllSelectBox( form.effective_date );
  console.log(form.effective_date);
  deleteAllSelectBox( form.inv_process );
  console.log(form.inv_process);
  <%if(ecoHash.get("division_flag").toString().equals("C")){%>
  deleteAllSelectBox( form.domestic_yn );
  console.log(form.domestic_yn);
  <%}%>

  <%
  Hashtable<String, String> divHash = new Hashtable<String, String>();
  for(int divCnt = 0 ; divCnt < divList.size(); divCnt++ )
  {
    divHash = divList.get(divCnt);
    if( divHash.get("name").equals(userGroupStr) )
    {
  %>
    addSelectBox( form.div_flag, '<%=divHash.get("name")%>', '<%=divHash.get("code")%>', '<%=ecoHash.get("division_flag").toString()%>');
  <%
    }
  }

  Hashtable<String, String> effDate = new Hashtable<String, String>();
  for( int eCnt=0; eCnt < effDateList.size() ; eCnt++ )
  {
    effDate = effDateList.get( eCnt );
  %>
    addSelectBox( form.effective_date, '<%=effDate.get("name")%>', '<%=effDate.get("code")%>', '<%=ecoHash.get("effective_date_flag").toString()%>');
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
      
      addSelectBox( form.costChange, '<%=costChange.get("name")%>', '<%=costChange.get("code")%>', '<%=ecoHash.get("costChangeCode").toString()%>');
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

  <%if(ecoHash.get("division_flag").toString().equals("C")){%>
  addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%>', '', '<%=ecoHash.get("domestic_yn_code").toString()%>');
  addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00983") %><%--국내--%>', '1000', '<%=ecoHash.get("domestic_yn_code").toString()%>');
  addSelectBox( form.domestic_yn, '<%=messageService.getString("e3ps.message.ket_message", "00985") %><%--국외--%>', '2000', '<%=ecoHash.get("domestic_yn_code").toString()%>');

  searchCarData( 'maker', '<%=ecoHash.get("domestic_yn_code").toString()%>' , '<%=ecoHash.get("car_maker").toString()%>');
  searchCarData( 'car', '<%=ecoHash.get("car_maker").toString()%>', '<%=ecoHash.get("car_category").toString()%>' );
  <%}%>

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
        
        
        var prodEcoReason = "chgRsTable^"+ chkChgReason[inx].value;
        var chgRsTable = document.getElementById(prodEcoReason);
        

        var hasChkChgType = 0;
        $(chgRsTable).find('input[name=chk_chg_type]').each(function(i) {
            hasChkChgType++;
        });
                     
        if(hasChkChgType > 0) {
            chgRsTable.style.display = "block";
                        
        }
        
        
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

  
  // 초도배포가 선택되어 있을 경우
  // 초도를 제외한 모든 설계변경사유를 unchecked 처리 한다.
  $('input[name=chk_chg_reason]').each(function(i) {
      if($(this).val() == 'REASON_12' && $(this).is(':checked')) {
    	  
    	  $('input[name=chk_chg_reason]').each(function(j) {
    	      if($(this).val() != 'REASON_12') {
    	    	  $(this).attr('disabled', 'disabled');
    	      } 
    	      else {
    	    	  $(this).attr('disabled', 'disabled');
    	      }
    	  });
    	  
    	  return true;
      } 
      
  });
  
  
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

      /* 
      if( inx == 3 )
      {
        disable( 'other_cust_flag', chkCustom[inx].checked );
      }
      */
      
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
  
  var ecoApplyPoint = document.getElementsByName( 'ecoApplyPoint' );

  for( var inx=0; inx < ecoApplyPoint.length ; inx++)
  {
    if( ecoApplyPoint[inx].value == '<%=ecoHash.get("ecoApplyPoint").toString()%>' )
    {
    	ecoApplyPoint[inx].checked = true;
    }
  }

  
  var chChgChk = document.getElementsByName( 'chk_cu' );

  for( var inx=0; inx < chChgChk.length ; inx++)
  {
    if( chChgChk[inx].value == '<%=ecoHash.get("cu_chg_yn").toString()%>' )
    {
      chChgChk[inx].checked = true;
    }
  }
   
  var chDgChk = document.getElementsByName( 'chk_design_guide' );

  for( var inx=0; inx < chDgChk.length ; inx++)
  {
    if( chDgChk[inx].value == '<%=ecoHash.get("design_guide_yn").toString()%>' )
    {
    	chDgChk[inx].checked = true;
    }
  }
  
  var chDsChk = document.getElementsByName( 'chk_design_sheet' );

  for( var inx=0; inx < chDsChk.length ; inx++)
  {
    if( chDsChk[inx].value == '<%=ecoHash.get("design_check_sheet_yn").toString()%>' )
    {
    	chDsChk[inx].checked = true;
    }
  }
  
  var chpChk = document.getElementsByName( 'chk_point' );

  for( var inx=0; inx < chpChk.length ; inx++)
  {
    if( chpChk[inx].value == '<%=ecoHash.get("pointYn").toString()%>' )
    {
    	chpChk[inx].checked = true;
    }
  }
  
  var chScChk = document.getElementsByName( 'chk_spec_change' );

  for( var inx=0; inx < chScChk.length ; inx++)
  {
    if( chScChk[inx].value == '<%=ecoHash.get("specChangeYn").toString()%>' )
    {
    	chScChk[inx].checked = true;
    }
  }
  
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
  
  var defectDivCode = '<%=ecoHash.get("defectDivCode")%>';
  var defectTypeCode = '<%=ecoHash.get("defectTypeCode")%>';
  
  if(defectDivCode!=''){
	  var choiceCode  = defectDivCode;
	  $("#defectType").empty().data('options');
	  $.ajax({
	      url : "/plm/ext/code/getChildCodeList.do?codeType=PROBLEMDIVTYPE&parentCode="+choiceCode,
	      type : "POST",
	      dataType : 'json',
	      async : false,
	      success: function(data) {
	          $("#defectType").append("<option value=''><%=messageService.getString("e3ps.message.ket_message", "09434") %><%--선택--%></option>");
	          $.each(data, function() {
	              $("#defectType").append("<option value='"+this.code+"'>"+ this.value +"</option>");
	          });
	          $("#defectDiv").val(defectDivCode);
	          $("#defectType").val(defectTypeCode);
	      }
	  });
  }
  
  
  //loadEcrData();
  var tabName = document.forms[0].tabName.value;
  clickTabBtn(tabName);
  ecnCheckByReason();
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
         innerCell.width = "40";
         innerCell.className = "tdwhiteM";
        innerCell.innerHTML = "<input name='fileSelect' type='checkbox' class='chkbox'>";
      }
      else if (k == 1)
      {
        innerCell.width = "552";
        innerCell.className = "tdwhiteL0";
        innerCell.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:99%'>";
      }
    }

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

    if( trArr[7] != "" &&  ('<%=userGroupStr%>' == '자동차사업부' || '<%=userGroupStr%>' == 'KETS') )
    {
      document.forms[0].domestic_yn.value = trArr[6];

      searchCarData( 'maker', trArr[6], trArr[7] );
      searchCarData( 'car', trArr[7], trArr[8] );
    }
  }
}

function doCancel()
{
  if( confirm("<%=messageService.getString("e3ps.message.ket_message", "03331") %><%--작업한 내용이 사라집니다.\n그래도 진행하시겠습니까?--%>") )
  {
    if( document.forms[0].prePage.value == 'ECR' || document.forms[0].prePage.value == 'View' )
    {
      history.back();
    }
    else
    {
      document.forms[0].action= '/plm/jsp/ecm/SearchEcoForm.jsp';
      document.forms[0].target='_self';
      document.forms[0].submit();
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

function doSave()
{
  var form = document.forms[0];

  $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
  $('[name=webEditorText]').val(fnGetEditorText(0));
  $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
  $('[name=webEditorText1]').val(fnGetEditorText(1));
  
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

    if( '<%=ecoHash.get("status").toString()%>'  != 'ACTIVITYCOMPLETED' )
    {
    	// 저장시 ECN에 대한 유효성을 검사할 필요 없다. ECO 상신시 하면된다.
        if(true) //if( checkDoc() )    // ECN 유효성 검사
        {
        
          // ECN을 저장하기위해 반드시 필요한 코드이다. 서버에서 저장하는 방식이 다른 것과 다르다.
    	  var refDocStr = setDocList();
    	  document.forms[0].doc_list.value = refDocStr;
        
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
    else
    {
    	
        // ECN을 저장하기위해 반드시 필요한 코드이다. 서버에서 저장하는 방식이 다른 것과 다르다.
        var refDocStr = setDocList();
        document.forms[0].doc_list.value = refDocStr;
        
      document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
      document.forms[0].cmd.value='Modify';
      document.forms[0].target='download';
       disabledAllBtn();
      showProcessing();
      document.forms[0].submit();
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

//ECR 상세조회 팝업
function viewRelEcr(oid){
  var url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","820","700","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function delProject()
{
  document.forms[0].project_oid.value =  '';
  document.forms[0].project_id.value =  '';
  document.forms[0].project_name.value =  '';
}

//부품 상세조회 팝업
function viewPart(v_poid){
  if(v_poid != null && v_poid != '' && v_poid.lastIndexOf('WTPart') > -1) {	
	  viewRelPart(v_poid);
  } else {
	  viewEpmDocPopup(v_poid);
  }
}	
function viewRelPart(v_poid){
  if(v_poid != null && v_poid != '' && v_poid.lastIndexOf('WTPart') > -1) { 
      var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
      openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
  } else {
	  viewEpmDocPopup(v_poid);
  }
}

// 도면 상세조회팝업
function viewEpmDocPopup(oid)
{
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid;
    openWindow2(url,"","820","1000","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
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

</script>

</head>
<body onload="javascript:init();">
<form  name="ModifyProdEcoForm" method="post" action="/plm/servlet/e3ps/ProdEcoServlet" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="Modify">
<input type="hidden" name="prePage" value="<%=prePage %>">
<input type="hidden" name="tabName" value="<%=tabName %>">
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
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02563") %><%--제품ECO등록/수정--%></td>
                    <!-- td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02563") %><%--제품ECO등록/수정--%>
                    </td -->
                </tr>
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
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCancel();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                          <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
      <table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
          <td height="10" background="/plm/portal/images/box_14.gif"></td>
          <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
        </tr>
        <tr>
          <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
          <td valign="top">
            <!-- table width="760" height="630" border="0" cellspacing="0" cellpadding="10">
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
              
              <%
              if( ecoHash.get("status").equals("ACTIVITYCOMPLETED") )
              {
              %>
                <%@include file="/jsp/ecm/ViewProdEcoActivityForm.jsp" %>
              <%
              }
              else
              {
              %>
                <%@include file="/jsp/ecm/ModifyProdEcoActivityForm.jsp" %>
              <%
              }
              %>
                
            </div>
            <div id="tabEcn" style="position:; display:none; z-index:1; ">
              
              <%
              if( ecoHash.get("status").equals("APPROVED") )
              //if( ecoHash.get("status").equals("ACTIVITYCOMPLETED") )
              {
              %>
                <%@include file="/jsp/ecm/reform/ViewProdEcnForm.jsp" %>
              <%
              }
              else
              {
              %>
                 <%@include file="/jsp/ecm/reform/ModifyProdEcnForm.jsp" %>
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
  </tr>
</table -->
</table>
</form>
<iframe name="download" align="center" width="0" height="0" border="0" style="display:none"></iframe>
<iframe name="setCarData" width="0" height="0" style="display:none"></iframe>
</body>
</html>
