<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page import="java.util.*"%>

<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.ecm.entity.*"%>
<%@page import="ext.ket.dqm.entity.*"%>
<%@page import="e3ps.groupware.company.PeopleData"%>               
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>  

<%@page import="wt.fc.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String EcoApprover = "-";
String EcoApprovedDate = "-";
if ( !prodEco.getLifeCycleState().toString().equals("INWORK") ) {

    WTObject pbo = KETCommonHelper.service.getPBO( prodEco );

    if ( pbo != null ) {
        WTUser appUser = WorkflowSearchHelper.manager.getLastApprover(pbo);
        EcoApprover = (appUser == null)? "&nbsp;":appUser.getFullName();
        EcoApprovedDate = WorkflowSearchHelper.manager.getLastApprovalDate(pbo);
    }
}
%>
<style type="text/css">
<!--
.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td style="width:130px;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("eco_id").toString() %></td>
    <td style="width:130px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
    <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("DEVYN", ecoHash.get("dev_yn").toString(), messageService.getLocale().toString())%></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
    <td colspan="3" class="tdwhiteL0"><%=ecoHash.get("eco_name").toString() %></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("division_flag_name").toString() %></td>
    
    <td class="tdblueL">Project No</td>
    <%
    if(!ecoHash.get("project_oid").toString().equals("")) {
    %>
    <td class="tdwhiteL0"><a href="javascript:openProject('<%=ecoHash.get("project_no").toString()%>');"><%=ecoHash.get("project_no").toString()%>/<%=ecoHash.get("project_name").toString() %></a>&nbsp;</td>
    <%
    } else {
    %>
    <td class="tdwhiteL0">-&nbsp;</td>
    <%
    }
    %>
    
  </tr>
  <%if( ecoHash.get("division_flag_name").toString().equals("자동차사업부") || ecoHash.get("division_flag_name").toString().equals("KETS") ) {%>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
    <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(ecoHash.get("domestic_yn").toString(), "-") %><%if( ecoHash.get("domestic_yn").toString().length()>0 ) {%>/<%} %> <%=ecoHash.get("car_maker_name").toString() %>&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
    <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecoHash.get("car_category_name").toString(),"-") %>&nbsp;</td>
  </tr>
  <%
  }
  %>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("dept_name").toString() %></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
    <td class="tdwhiteL0"><%=ecoHash.get("creator_name").toString() %></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("create_date").toString() %></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
    <td class="tdwhiteL0"><%=ecoHash.get("update_date").toString() %></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
    <td class="tdwhiteL"><%=EcoApprover%>&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
    <td class="tdwhiteL0"><%=EcoApprovedDate%>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
    <td colspan="3"  class="tdwhiteL0"><a href="javascript:viewHistory('<%=ecoHash.get("eco_oid").toString()%>');"><%=ecoHash.get("status_name").toString() %></a>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <div id="div_scroll" style="width=610;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" >
        <tr class="">
          <!-- td width="40" class="tdgrayM">No</td -->
          <td width="110" class="tdgrayM">ECR No</td>
          <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
          <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
          <td width="100" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
        </tr>
        <%
        ArrayList<Hashtable<String,String>> ecrList = (ArrayList)ecoHash.get("ecrList");
        Hashtable<String, String> ecrHash = null;
        if( ecrList != null && ecrList.size() > 0 )
        {
            int ecrLength = ecrList.size();
            for( int ecrCnt=0; ecrCnt < ecrList.size(); ecrCnt++ )
            {
                ecrHash = ecrList.get(ecrCnt);
        %>
        <tr>
          <!-- td class="tdwhiteM"><%=ecrLength-- %></td -->
          <td class="tdwhiteM"><a href="javascript:viewRelEcr('<%=ecrHash.get("oid")%>');"><%=ecrHash.get("ecr_id") %></a></td>
          <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ecrHash.get("ecr_name")) %>"><div class="ellipsis" style="width:320px;"><nobr>&nbsp;<%=ecrHash.get("ecr_name") %></nobr></div></td>
          <td class="tdwhiteM"><%=ecrHash.get("dept_name") %></td>
          <td class="tdwhiteM"><%=ecrHash.get("creator_name") %></td>
          <td class="tdwhiteM0"><%=EcmUtil.getLastApproveDate( (KETProdChangeRequest)KETObjectUtil.getObject(ecrHash.get("oid")) )%>&nbsp;</td>
        </tr>
        <%
            }
        }
        else
        {
        %>
        <!-- tr>
            <td colspan=5 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00208") %><%--ECR 정보가 없습니다--%></td>
        </tr -->
        <%
        }
        %>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09003") %><%--불량구분--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("defectDivName")%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
    <td class="tdwhiteL0"><%=ecoHash.get("defectTypeName") %></td>
  </tr>

 <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04620") %><%--관련품질문제--%></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="100%" cellpadding="0" cellspacing="0" id="relDqmTable">
        <tr class="">

                <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%--문제점--%></td>
                <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04640") %><%--작성자--%></td>
                <td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "04650") %><%--작성일자--%></td>

        </tr>
        <%
        // 관련품질문제
        if(PersistenceHelper.isPersistent(prodEco)) {
            QueryResult dqmQr = PersistenceHelper.manager.navigate( prodEco, "dqm", KETEcoDqmLink.class );
            if( dqmQr != null && dqmQr.size() > 0 )
            {
                while( dqmQr.hasMoreElements() )
                {
                    KETDqmAction ketDqmAction = (KETDqmAction) dqmQr.nextElement();
                    
                    QuerySpec query = new QuerySpec();
                    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
                    SearchCondition sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL, CommonUtil.getOIDLongValue(ketDqmAction));
                    query.appendWhere(sc, new int[] { idxHeaer });
    
                    QueryResult dqmHeaderQr = PersistenceHelper.manager.find(query);
                    while (dqmHeaderQr.hasMoreElements()) {
                        Object[] tempObj = (Object[]) dqmHeaderQr.nextElement();
                        KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
                        
                        KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
                        WTUser createUser = (WTUser) ketDqmRaise.getCreator().getPrincipal();
                        PeopleData peopleData = new PeopleData(createUser);
                   
        %>
           <tr>
             <td class="tdwhiteL" title='<%=ketDqmHeader.getProblem()%>'><a href="javascript:viewRelDqm('<%=CommonUtil.getOIDString(ketDqmHeader) %>');"><div class="ellipsis" style="width:550px;"><nobr><%=ketDqmHeader.getProblem() %></nobr></div></a></td>
             <td class="tdwhiteM"><%=peopleData.name %></td>
             <td class="tdwhiteM0"><%=DateUtil.getDateString(ketDqmRaise.getCreateTimestamp(), "date") %></td>
           </tr>
        <%
                    }    // while (dqmHeaderQr.hasMoreElements()) {
                }    // while( dqmQr.hasMoreElements() )
            }    // if( dqmQr != null && dqmQr.size() > 0 )     
        }
        %>    
      </table>
    </td>
  </tr>           
      
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="620">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <div id="div_scroll2" style="width=610;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0">
        <tr class="">
          <!-- td width="40"  class="tdgrayM">No</td -->
          <td width="150"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="*"    class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <!-- td width="90"  class="tdgrayM">Die No</td -->
          <td width="45"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="60"  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04080") %><%--매칭부품--%></td>
          <!-- td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td -->
          <!-- td width="112" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%></td -->
        </tr>
        <%
        ArrayList<Hashtable<String, String>> partList = (ArrayList)ecoHash.get("partList");
        Hashtable<String, String> partHash = null;
        if( partList != null && partList.size() > 0 )
        {
            int partLength = partList.size();
            for( int partCnt=0 ; partCnt < partList.size() ; partCnt++ )
            {
                partHash = partList.get(partCnt);
        %>
        <tr>
          <!-- td class="tdwhiteM"><%=partLength-- %></td -->
          <td class="tdwhiteM">
            
            <!-- ECN에서 실중량 수정을 위한 정보이다. -->
            <input type="hidden" name="actualWeight_eco_oid" value='<%=ecoHash.get("eco_oid").toString() %>' >
            <input type="hidden" name="actualWeight_part_oid" value='<%=partHash.get("part_oid") %>' >
            <input type="hidden" name="actualWeight_part_name" value='<%=partHash.get("part_name") %>' >
            <input type="hidden" name="actualWeight_part_ver" value='<%=partHash.get("part_ver") %>' >
            
            <%-- <a href="javascript:viewPart('<%=partHash.get("part_oid")%>');"><%=partHash.get("part_no") %></a> --%>
            <a href="javascript:viewPart('<%=EcmUtil.getPartOid(partHash.get("part_no"), partHash.get("part_ver")) %>');"><%=partHash.get("part_no") %></a>
            <input type="hidden" name="relPartNumber" value="<%=partHash.get("part_no") %>">  
                        
          </td>
          <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(partHash.get("part_name")) %>"><div class="ellipsis" style="width:150;"><nobr><%=partHash.get("part_name") %></nobr></div></td>
          <!-- td class="tdwhiteM"><%=partHash.get("die_no") %>&nbsp;</td -->
          <td class="tdwhiteM"><%=partHash.get("revDis") %></td>
          <!-- td class="tdwhiteM"><%=partHash.get("part_ver") %></td -->
          <td class="tdwhiteM0"><a href="javascript:openViewMatchingPart('<%=partHash.get("part_oid") %>');" ><%=PartBaseHelper.service.getMatchingPartCount(partHash.get("part_oid")) %></a></td>
                    
          <%-- 
          <td class="tdwhiteR"><%=KETStringUtil.getFormattedNumber(partHash.get("expect_cost"), "###,###,##0") %>&nbsp;</td>
          <%
          if( partHash.get("part_no").charAt(0) == 'R' )
          {
          %>
          <td class="tdwhiteM0">&nbsp;</td>
          <%
          }else{
          %>
          <td class="tdwhiteM0"><%=partHash.get("budget_yn") %></td>
          <%
          }
          %>
          --%>
          
        </tr>
        <%
          }
        }
        else
        {
        %>
        <!-- tr>
            <td colspan=6 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00915") %><%--관련 부품이 없습니다--%></td>
        </tr -->
        <%
        }
        %>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("chg_reason_name").toString() %><%if( ecoHash.get("other_chg_reason_desc").toString().length() > 0){ %>(<%=ecoHash.get("other_chg_reason_desc").toString() %>)<%} %></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00865") %><%--고객확인구분--%></td>
    <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecoHash.get("cust_chk_name").toString(),"-") %><%if( ecoHash.get("other_cust_chk_desc").toString().length() > 0){ %>(<%=ecoHash.get("other_cust_chk_desc").toString() %>)<%} %></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04740") %><%--설계변경 상세사유--%></td>
    <td class="tdwhiteL">
      <%
      String chg_type_name = "";
      
      HashMap<String, ArrayList<String>> chg_type_hashmap = (HashMap<String, ArrayList<String>>) ecoHash.get("chg_type_hashmap");
      
      
      String chg_reason_name = ecoHash.get("chg_reason_name").toString();
      String[] chg_reason_names = StringUtils.splitPreserveAllTokens(chg_reason_name, "/");
      int keysLength = (chg_reason_names != null) ? chg_reason_names.length : 0;
      
      if(chg_type_hashmap != null) {
	      Set<String> keySet = (chg_type_hashmap!= null) ? chg_type_hashmap.keySet() : null;
	      Object[] keys = (keySet != null) ? keySet.toArray() : null;
	      //int keysLength = (keys != null) ? keys.length : 0;
	      for(int i=0; i < keysLength; i++) {
		      String key = (String) chg_reason_names[i];
		      key = key.trim();
		          
		      ArrayList<String> chgTypeNames = chg_type_hashmap.get(key);
		      int chgTypeNamesSize = (chgTypeNames != null) ? chgTypeNames.size() : 0;
		      if(chgTypeNamesSize > 0) {
			      chg_type_name += " - "+ key +" : ";
		          for(int j=0; j < chgTypeNamesSize; j++) {
				      chg_type_name += chgTypeNames.get(j);
				      if(j != (chgTypeNamesSize - 1)) chg_type_name += " / "; 
			      }
			      chg_type_name += "<br>";
		      }
	          
	      }
	      if(chg_type_name.lastIndexOf("<br>") > -1) chg_type_name = chg_type_name.substring(0, chg_type_name.lastIndexOf("<br>"));
      }
      %>
      <%=StringUtil.checkReplaceStr(chg_type_name, "-") %>&nbsp;
      <%-- <%=StringUtil.checkReplaceStr(ecoHash.get("chg_type_name").toString(), "-") %>&nbsp; --%>
      
    </td>
    <td class="tdblueL">ECO적용시점</td>
    <td class="tdwhiteL0"><%=ecoHash.get("ecoApplyPoint").toString() %>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02647") %><%--조정 및 변경사항--%></td>
    <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(ecoHash.get("chg_fact_name").toString(), "-") %>&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00134") %><%--CU도면{0} 변경여부--%></td>
    <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecoHash.get("cu_chg_yn").toString(),"-") %>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%></td>
    <td class="tdwhiteL"><%=ecoHash.get("costChange").toString()%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04145") %><%--원가증감비율(수주대비)--%></td>
    <td class="tdwhiteL0"><%=ecoHash.get("costVariation").toString()%></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02469") %><%--적용시기--%></td>
    <td class="tdwhiteL"><%=NumberCodeUtil.getNumberCodeValue("EFFECTIVEDATE", ecoHash.get("effective_date_flag").toString(), messageService.getLocale().toString())%></td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02445") %><%--재고처리--%></td>
    <td class="tdwhiteL0"><%=NumberCodeUtil.getNumberCodeValue("INVPROCESS", ecoHash.get("inv_clear").toString(), messageService.getLocale().toString())%></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09471") %><%--설계가이드 반영--%></td>
    <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(ecoHash.get("design_guide_yn").toString(),"-") %>&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09472") %><%--설계검증체크시트 반영--%></td>
    <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecoHash.get("design_check_sheet_yn").toString(),"-") %>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09477") %><%--설계가이드 반영--%></td>
    <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(ecoHash.get("pointYn").toString(),"-") %>&nbsp;</td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09478") %><%--설계검증체크시트 반영--%></td>
    <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecoHash.get("specChangeYn").toString(),"-") %>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05153") %><%--변경 전--%></td>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "05154") %><%--변경 후--%></td>
  </tr> 
  <tr>
  
	
	<!-- 이노디터 JS Include Start -->
	<script type="text/javascript">
	    //<![CDATA[
	
	    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
	    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
	    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
	    var g_arrSetEditorArea = new Array();
	    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
	    g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정
	
	    //]]>
	</script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui_half.js"></script>
	<script type="text/javascript">
	    //<![CDATA[
	
	    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
	
	    // Skin 재정의
	    //g_nSkinNumber = 0;
	
	    // 크기, 높이 재정의
	    g_nEditorWidth = 460;
	    g_nEditorHeight = 200;
	
	    // 메뉴바 설정
	    g_bCustomize_MenuBar_Display = false;
	    // 1번 툴바 설정
	    g_bCustomize_ToolBar1_Display = false;
	    // 2번 툴바 설정
	    g_bCustomize_ToolBar2_Display = false;
	    // 3번 툴바 설정
	    g_bCustomize_ToolBar3_Display = false;
	    // 탭바 설정
	    g_bCustomize_TabBar_Display = false;
	    // 조회모드
	    g_nCustomFormEditMode = parseInt('2');
	    
	    
	    // 편집창의 focus/blur 이벤트 발생 후 추가적인 처리를 위한 함수 호출 설정
	    // 이노디터를 호출하는 곳에서 해당 함수가 정의되어 있어야 함
	    g_strCustomFocusEventFunction = "fnTestFocusEventFunction";
	
	    //]]>
	</script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
	
	<script language=javascript>
	var isBlur = 0;
	// 고객사에서 정의한 함수 - focus 이벤트 발생시 호출되는 함수
	// 매개변수 1,2 값은 자동으로 이노디터에서 넘겨줌
	// 매개변수 1 - 에디터번호
	// 매개변수 2 - focus 인 경우는 1 리턴, 아닌 경우 0 리턴
	function fnTestFocusEventFunction(EditNumber, FocusYN) {
	    
	    if(isBlur >= 2) {
	        
	        // 이노디터 제공함수 - 편집창모드 true, 소스창모드 false 리턴
	        if(1 == FocusYN) {
	            fnEventHandler_OnFocus(EditNumber);
	        
	        } else {
	            fnEventHandler_OnBlur(EditNumber);
	            
	        }
	        
	    } else {
	        //document.forms[0].ecr_desc.focus();
	        isBlur++;
	    }
	}
	
	
	//click event에 대한 Handler
	function fnEventHandler_OnFocus(EditNumber) {
	    fnResizeEditor(0, 460, 600);
	    fnResizeEditor(1, 460, 600);
	
	}
	function fnEventHandler_OnBlur(EditNumber) {
	    fnResizeEditor(0, 460, 200);
	    fnResizeEditor(1, 460, 200);
	    
	}
	</script>
	
	<!-- 이노디터 JS Include End -->
  
    <td colspan="2" class="tdwhiteL">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor").toString() %></textarea> 
                  <textarea name="webEditorText" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor_text").toString() %></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>
    <td colspan="2" class="tdwhiteL0">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor1" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor_1").toString() %></textarea> 
                  <textarea name="webEditorText1" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor_text_1").toString() %></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER1"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>            
  </tr>            
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04106") %><%--검토결과--%></td>
    <td colspan="3" class="tdwhiteL0">
      <div style="position:; width:100%; height:50px; overflow-x:auto; overflow-y:auto;">
        <pre><%=StringUtil.checkNull(ecoHash.get("review_result").toString()) %></pre>
      </div>
    </td>
  </tr>  
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <div id="div_scroll3" style="width=610;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0">
        <tr  class="">
          <!-- td width="40" class="tdgrayM">No</td -->
          <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
        </tr>
                <%
                if( attachFileList.size() > 0 )
        {
          int attachCnt = attachFileList.size();
          ContentInfo cntInfo = null;
          ContentItem ctf = null;
          String appDataOid = "";
          String url = "";

            for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
            {
                cntInfo = (ContentInfo)attachFileList.elementAt(fileCnt);
                ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                      appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                      url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                      //url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                      url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
           %>
              <tr>
                <!-- td class="tdwhiteM"><%=attachCnt-- %></td -->
                <td class="tdwhiteL0"><%=url%></td>
              </tr>
           <%
                }
          }
          else
          {
           %>
             <!-- tr>
                 <td colspan=1 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "02800") %><%--첨부파일이 존재하지 않습니다--%></td>
             </tr -->
           <%
          }
           %>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
</table>
