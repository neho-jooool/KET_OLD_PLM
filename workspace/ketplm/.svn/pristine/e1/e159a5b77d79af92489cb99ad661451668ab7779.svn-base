<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="e3ps.ecm.beans.EcmUtil
                            ,e3ps.ecm.beans.ProdEcoBeans" %>
<%@page import="java.util.*
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ApplicationData
                            ,wt.org.WTUser
                            ,wt.session.SessionHelper" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="e3ps.ecm.beans.EcmSearchHelper" %>
<%@page import="e3ps.ecm.entity.KETProdChangeRequest"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalHistory"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="wt.fc.WTObject"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="e3ps.common.service.KETCommonHelper"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="ext.ket.bom.query.KETBOMQueryBean"%>               
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>  

<%
KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEco" class="e3ps.ecm.entity.KETProdChangeOrder" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="ecoHash" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="prePage" class="java.lang.String" scope="request"/>
<jsp:useBean id="tabName" class="java.lang.String" scope="request"/>

<%
ArrayList<Hashtable<String, String>> bomList = (ArrayList)ecoHash.get("bomList");
ArrayList<Hashtable<String, String>> parentItemList = (ArrayList)ecoHash.get("parentItemList");
ArrayList<Hashtable<String, String>> epmDocList = (ArrayList)ecoHash.get("epmDocList");
%>

<html>
<head>
<%@include file="/jsp/common/multicombo.jsp" %>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	var strContent = document.innoditorDataForm["webEditor"].value;
	
    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.innoditorDataForm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.innoditorDataForm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.innoditorDataForm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    var strContent2 = document.innoditorDataForm2["webEditor2"].value;

    var objView2 = document.getElementById("divView2");
    objView2.innerHTML = strContent2;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor2 = document.innoditorDataForm2["hdnBackgroundColor2"].value;
    if("" != strBackgroundColor2)
    {
        objView2.style.backgroundColor = strBackgroundColor2;
    }

    var strBackgroundImage2 = document.innoditorDataForm2["hdnBackgroundImage2"].value;
    if("" != strBackgroundImage2)
    {
        var strCopyBackgroundImage2 = strBackgroundImage2.toUpperCase();

        if("none" == strCopyBackgroundImage2)
        {
            objView2.style.backgroundImage = strBackgroundImage2;
        }
        else
        {
            objView2.style.backgroundImage = "url(" + strBackgroundImage2 + ")";
        }
    }

    var strBackgroundRepeat2 = document.innoditorDataForm2["hdnBackgroundRepeat2"].value;
    if("" != strBackgroundRepeat2)
    {
        objView2.style.backgroundRepeat = strBackgroundRepeat2;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

});


function doPrint(){
    $('#buttonTable').hide();
    //window.print();
    //여백설정
    IEPageSetupX.topMargin=0;
    IEPageSetupX.bottomMargin=0;
    //가로설정
    IEPageSetupX.Orientation = 0;
    //머리글 설정
    IEPageSetupX.header="";
    //바닥글 설정
    IEPageSetupX.footer="";
    //배경색
    IEPageSetupX.PrintBackground = true;
    //미리보기
    IEPageSetupX.Preview();
    //인쇄
    //IEPageSetupX.Print();
    
    doneyet();
    
}

function doneyet() { 
    $('#buttonTable').show();
} 

</script>
   
</head>

<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT>
<body onload="test();">
<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor").toString() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>

<form name="innoditorDataForm2" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor2" rows="0" cols="0" style="display: none"><%=ecoHash.get("web_editor_1").toString() %></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
    <table id="buttonTable" width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue" onfocus="this.blur();">인쇄</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();">닫기</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
           </td>
        </tr>
    </table>
    <table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td class="space5"></td>
        </tr>
    </table>
    <div class="contents-wrap">
        <div>
            <table cellpadding="0" class="table-style-04 border-top-line">
                <colgroup>
                    <col width="60%">
                    <col width="4%">
                    <col width="12%">
                    <col width="12%">
                    <col width="12%">
                </colgroup>
                <tbody>
                    <tr>
                        <td rowspan="2" class="title border-bottom-none"><span style="font-size:20px">설계변경지시서<span><br><span style="font-size:12px">(Engineering Change Order)</span></td>
                        <td rowspan="2" class="title border-bottom-none">결재</td>
                        <td class="title">작성</td>
                        <td class="title">검토</td>
                        <td class="title">승인</td>
                    </tr>
                    <%
                    
                    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(ecoHash.get("eco_oid").toString()));
                    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
                    Object temp = new Object();
                    Vector vec = null;
                    // out.println(master.getPbo().toString()); 결재객체oid확인
                    
                    String reviewUserName = "";
                    String reviewUserDept = "";
                    String reviewDate = "";
                    
                    String approveUserName = "";
                    String approveUserDept = "";
                    String approveDate = "";
                    
                    if (master != null) {

	                    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	
	                    if (vec != null) {
	                        String activityName = "&nbsp;";
	
	                        for (int i = 0; i < vec.size(); i++) {
		                        KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		
		                        activityName = StringUtil.checkNull(history.getActivityName());
		                        if (activityName.equals("검토") && history.isLast()) {
		                            if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
		                        	    PeopleData pdata = new PeopleData(history.getOwner().getPrincipal());
		                        	    approveUserName = pdata.name;
		                        	    approveUserDept = pdata.departmentName;
		                        	    if (history.getCompletedDate() != null)
			                        	 approveDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
		                            } 
		                        }
		                        if (activityName.equals("검토") && !history.isLast()) {
                                    if (ParamUtil.checkStrParameter(history.getDecision(), "&nbsp;").equals("승인")) {
                                	    PeopleData pdata = new PeopleData(history.getOwner().getPrincipal());
                                        reviewUserName = pdata.name;
                                        reviewUserDept = pdata.departmentName;
                                        if (history.getCompletedDate() != null)
                                          reviewDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
                                    } 
                                }
	
	                        }
	                    }
                    }
                    
                    %>
                    <tr>
                        <td class="center border-bottom-none"><%=ecoHash.get("creator_name").toString() %><br><%=ecoHash.get("dept_name").toString() %><br><%=ecoHash.get("create_date").toString() %></td>
                        <td class="center border-bottom-none"><%=reviewUserName%><br><%=reviewUserDept%><br><%=reviewDate%></td>
                        <td class="center border-bottom-none"><%=approveUserName%><br><%=approveUserDept%><br><%=approveDate%></td>
                    </tr>
                </tbody>
            </table>
            <table cellpadding="0" class="table-style-04 border-top-line">
                <colgroup>
                    <col width="12%" />
                    <col width="15%" />
                    <col width="12%" />
                    <col width="25%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="title">ECO NO.</td>
                        <td align="center"><%=ecoHash.get("eco_id").toString() %></td>
                        <td class="title">제목</td>
                        <td colspan="2"><%=ecoHash.get("eco_name").toString() %></td>
                        <td class="title">개발단계</td>
                        <td align="center"><%=NumberCodeUtil.getNumberCodeValue("DEVYN", ecoHash.get("dev_yn").toString(), messageService.getLocale().toString())%></td>
                    </tr>
                    <%
                    ArrayList<Hashtable<String,String>> ecrList = (ArrayList)ecoHash.get("ecrList");
                    Hashtable<String, String> ecrHash = null;
                    if( ecrList != null && ecrList.size() > 0 )
                    {
                        int ecrLength = ecrList.size();
                    %>    
                    <tr>
                        <td rowspan="<%=ecrLength+1%>" class="title">연계<br>ECR</td>
                        <td class="title">ECR No.</td>
                        <td class="title" colspan="2">ECR 제목</td>
                        <td class="title">작성부서</td>
                        <td class="title">작성자</td>
                        <td class="title">승인일자</td>
                    </tr>
                    <%
			            for( int ecrCnt=0; ecrCnt < ecrList.size(); ecrCnt++ )
			            {
			                ecrHash = ecrList.get(ecrCnt);
			        %>
			        <tr>
						<td align="center"><a href="javascript:viewRelEcr('<%=ecrHash.get("oid")%>');"><%=ecrHash.get("ecr_id") %></a></td>
						<td colspan="2" title="<%=ecrHash.get("ecr_name") %>"><div class="ellipsis" style="width:164;"><nobr>&nbsp;<%=ecrHash.get("ecr_name") %></nobr></div></td>
						<td align="center"><%=ecrHash.get("dept_name") %></td>
						<td align="center"><%=ecrHash.get("creator_name") %></td>
						<td align="center"><%=EcmUtil.getLastApproveDate( (KETProdChangeRequest)KETObjectUtil.getObject(ecrHash.get("oid")) )%>&nbsp;</td>
			        </tr>
			        <%
			            }
			        }
                    else{
                	%>    
                        <tr>
                            <td rowspan="2" class="title">연계<br>ECR</td>
                            <td class="title">ECR No.</td>
                            <td class="title" colspan="2">ECR 제목</td>
                            <td class="title">작성부서</td>
                            <td class="title">작성자</td>
                            <td class="title">승인일자</td>
                        </tr>
                        <tr>
                            <td align="center" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00208") %><%--ECR 정보가 없습니다--%></td>
                        </tr>
                    <%
                    }
                    ArrayList<Hashtable<String, String>> partList = (ArrayList)ecoHash.get("partList");
                    Hashtable<String, String> partHash = null;
                    if( partList != null && partList.size() > 0 )
                    {
                        int partLength = partList.size();
                    %>   
                    <tr>
                        <td rowspan="<%=partLength+1%>" class="title">Part<br>Info.</td>
                        <td class="title">부품번호</td>
                        <td class="title" colspan="3">품명</td>
                        <td class="title">Rev.</td>
                        <td class="title">매칭부품</td>
                        <!-- td class="title">Die No.</td -->
                    </tr>
			        <%
			            for( int partCnt=0 ; partCnt < partList.size() ; partCnt++ )
			            {
			                partHash = partList.get(partCnt);
			        %>
			        <tr>
						<!-- td><%=partLength-- %></td -->
						<td align="center">
						  
						  <!-- ECN에서 실중량 수정을 위한 정보이다. -->
						  <input type="hidden" name="actualWeight_eco_oid" value='<%=ecoHash.get("eco_oid").toString() %>' >
						  <input type="hidden" name="actualWeight_part_oid" value='<%=partHash.get("part_oid") %>' >
						  <input type="hidden" name="actualWeight_part_name" value='<%=partHash.get("part_name") %>' >
						  <input type="hidden" name="actualWeight_part_ver" value='<%=partHash.get("part_ver") %>' >
						  
						  <a href="javascript:viewPart('<%=partHash.get("part_oid")%>');"><%=partHash.get("part_no") %></a>
						  <input type="hidden" name="relPartNumber" value="<%=partHash.get("part_no") %>">  
						              
						</td>
						<td colspan="3" title="<%=partHash.get("part_name") %>"><div class="ellipsis" style="width:150;"><nobr><%=partHash.get("part_name") %></nobr></div></td>
						<td align="center"><%=partHash.get("revDis") %></td>
						<td align="center"><%=PartBaseHelper.service.getMatchingPartCount(partHash.get("part_oid")) %></td>
						<!-- td align="center"><%=partHash.get("die_no") %>&nbsp;</td -->
                        
					</tr>
			        <%
			          }
			        }
                    else{
                        %>    
                            <tr>
                                <td rowspan="2" class="title">Part<br>Info.</td>
		                        <td class="title">부품번호</td>
		                        <td class="title" colspan="2">품명</td>
		                        <td class="title">Rev.</td>
		                        <td class="title">매칭부품</td>
		                        <td class="title">Die No.</td>
                            </tr>
                            <tr>
                                <td align="center" colspan="5"><%=messageService.getString("e3ps.message.ket_message", "00915") %><%--관련 부품이 없습니다--%></td>
                            </tr>
                        <%
                    }
			        %>
                    <tr>
                        <td class="title">Project No.</td>
                        <td><a href="javascript:viewProjectPopup('<%=ecoHash.get("project_oid").toString()%>');"><%=ecoHash.get("project_no").toString()%>/<%=ecoHash.get("project_name").toString() %></a></td>
                        <td class="title">고객사</td>
                        <td colspan="2"><%=StringUtil.checkReplaceStr(ecoHash.get("domestic_yn").toString(), "-") %><%if( ecoHash.get("domestic_yn").toString().length()>0 ) {%>/<%} %> <%=ecoHash.get("car_maker_name").toString() %></td>
                        <td class="title">차종</td>
                        <td><%=StringUtil.checkReplaceStr(ecoHash.get("car_category_name").toString(),"-") %></td>
                    </tr>
                    <tr>
                        <td class="title" rowspan="2">설변사유</td>
                        <td colspan="6"><%=ecoHash.get("chg_reason_name").toString() %><%if( ecoHash.get("other_chg_reason_desc").toString().length() > 0){ %>(<%=ecoHash.get("other_chg_reason_desc").toString() %>)<%} %></td>
                    </tr>
                    <tr>
                        <td colspan="6">
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
                          <%=StringUtil.checkReplaceStr(chg_type_name, "-") %>
                          <%-- <%=StringUtil.checkReplaceStr(ecoHash.get("chg_type_name").toString(), "-") %> --%>
                        
                        </td>
                    </tr>
                    <tr>
                        <td class="title">고객확인구분</td>
                        <td><%=StringUtil.checkReplaceStr(ecoHash.get("cust_chk_name").toString(),"-") %><%if( ecoHash.get("other_cust_chk_desc").toString().length() > 0){ %>(<%=ecoHash.get("other_cust_chk_desc").toString() %>)<%} %></td>
                        <td class="title">조정 및 변경</td>
                        <td colspan="4"><%=StringUtil.checkReplaceStr(ecoHash.get("chg_fact_name").toString(), "-") %></td>
                    </tr>
                    <tr>
                        <td class="title border-bottom-none">CU도면변경</td>
                        <td class="border-bottom-none"><%=ecoHash.get("cu_chg_yn").toString() %></td>
                        <td class="title border-bottom-none">적용시기</td>
                        <td class="border-bottom-none" colspan="2"><%=NumberCodeUtil.getNumberCodeValue("EFFECTIVEDATE", ecoHash.get("effective_date_flag").toString(), messageService.getLocale().toString())%></td>
                        <td class="title border-bottom-none">재고처리</td>
                        <td class="border-bottom-none"><%=NumberCodeUtil.getNumberCodeValue("INVPROCESS", ecoHash.get("inv_clear").toString(), messageService.getLocale().toString())%></td>
                    </tr>
                </tbody>
            </table>
            <table class="table-style-04" cellpadding="0">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="title">변경전</td>
                        <td class="title">변경후</td>
                    </tr>
                    <tr>
                        <td class="border-bottom-none">
                            <div id="divView" style="overflow-x:auto; overflow-y:hidden; width: 475px"></div>
                        </td>
                        <td class="border-bottom-none">
                            <div id="divView2" style="overflow-x:auto; overflow-y:hidden; width: 475px"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
			<table class="table-style-04" cellpadding="0">
                <colgroup>
                    <col width="80" />
                    <col width="120" />
                    <col width="200" />
                    <col width="40" />
                    <col width="120" />
                    <col width="200" />
                    <col width="50" />
                    <col width="50" /> 
                </colgroup>
                <tbody>
			        <tr>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "01377") %><%--모 부품번호--%></td>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "04021") %><%--모 부품명--%></td>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
			          <td rowspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "04022") %><%--자 부품명--%></td>
			          <td colspan="2" class="title"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
			        </tr>
			        <tr>
			          <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td><!-- 변경 전 -->
			          <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td><!-- 변경 후 -->
			        </tr>
			        <%
			        Hashtable<String, String> bomHash = null;
			        int totalBomCnt = 1;
			        int totalBom = 0;
			        if( bomList != null && bomList.size() >0 )
			        {
			            int bomLength = bomList.size();
			            totalBom = bomLength;
			            for( int bomCnt=0; bomCnt < bomList.size(); bomCnt++ )
			            {
			                bomHash = bomList.get(bomCnt);
			        %>
			                <tr>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("bom_type") %></td>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("parent_item_code") %>&nbsp; </td>
			                  <td class="border-bottom-none"><%=bomHash.get("parent_item_name") %></td>
			                  
			                  <%if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 ) {%>
			                    <td align="center" class="border-bottom-none"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("parent_item_code"),bomHash.get("eca_obj_after_rev")) %></td>
			                  <%}else{%>
			                   <td align="center" class="border-bottom-none">0</td>
			                  <%} %>
			                  
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("child_item_code") %></td>
			                  <td class="border-bottom-none"><%=bomHash.get("child_item_name") %></td>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("before_qty") %></td>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("atfer_qty") %></td>
			                </tr>
			        <%
			              }
			          }
			          if( parentItemList != null && parentItemList.size() > 0 )
			          {
			              int parentItemLength = parentItemList.size();
			              totalBom +=parentItemLength;
			              for( int itemCnt=0; itemCnt < parentItemList.size(); itemCnt++ )
			              {
			                  bomHash = parentItemList.get(itemCnt);
			        %>
			                <tr>
			                  <td align="center" class="border-bottom-none">BOM</td>
			                  <%if( StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() == 0 && StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("Y") ){ %>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("eca_parent_item_no") %></td>
			                  <td class="border-bottom-none"><%=bomHash.get("eca_parent_item_name") %></td>
			                  <%}else{ %>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("eca_obj_no") %></td>
			                  <td class="border-bottom-none"><%=bomHash.get("eca_obj_name") %></td>
			                  <%} %>
			
			                  <%if( StringUtil.checkNull( bomHash.get("eca_obj_after_rev") ).length() > 0 &&  StringUtil.checkNull( bomHash.get("eca_complete_date") ).length() > 0 ) {%>
			                  <td align="center" class="border-bottom-none"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_after_rev")) %></td>
			                  <%}else{%>
			                   <td align="center" class="border-bottom-none"><%=ketBOMQueryBean.getNewVersionCode(bomHash.get("eca_obj_no"),bomHash.get("eca_obj_pre_rev")) %></td>
			                  <%} %>
			                  
			                  <%if( StringUtil.checkNull( bomHash.get("masschange_yn") ).equals("N") ){ %>
			                  <td class="border-bottom-none">&nbsp;</td>
			                  <td class="border-bottom-none">&nbsp;</td>
			                  <%}else{ %>
			                  <td align="center" class="border-bottom-none"><%=bomHash.get("eca_obj_no") %></td>
			                  <td class="border-bottom-none"><%=bomHash.get("eca_obj_name") %></td>
			                  <%} %>
			                  <td class="border-bottom-none">&nbsp;</td>
			                  <td class="border-bottom-none">&nbsp;</td>
			                </tr>
			        <%
			                 }
			          }
			          if(totalBom==0)
			          {
			        %>
			           <tr>
			               <td colspan=11 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01494") %><%--변경 BOM이 없습니다--%>.</td>
			           </tr>
			        <%
			        }
			        %>
                </tbody>
			</table>                     
            <table cellpadding="0" class="table-style-04 border-bottom-line">
                <colgroup>
                    <col width="12%" />
                    <col width="5%" />
                    <col width="12%" />
                    <col width="35%" />
                    <col width="12%" />
                    <col width="12%" />
                    <col width="12%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="title">검토결과</td>
                        <td colspan="6"><%=ecoHash.get("review_result").toString() %></td>
                    </tr>
                    <%

                    ArrayList<Hashtable<String, String>> docList = (ArrayList)ecoHash.get("docList");
			        Hashtable<String, String> docHash = null;
			
			        if( docList != null && docList.size() > 0 )
			        {
			            int docListLength = docList.size();
		            %>    
			            <tr>
	                        <td rowspan="<%=docListLength+1%>" class="title">ECN</td>
	                        <td class="title" colspan="2">구분</td>
	                        <td class="title">후속업무</td>
	                        <td class="title">부서</td>
	                        <td class="title">담당자</td>
	                        <td class="title">완료요청일</td>
	                    </tr>
			        <%    
			            for( int i=0; i< docList.size(); i++ )
			            {
			                docHash = docList.get(i);
			          String activity_type = StringUtils.stripToEmpty(docHash.get("activity_type"));
			          String docActFlag = (activity_type.equals("") || activity_type.equalsIgnoreCase("3")) ? "DOC" : "ACT";
			          String docActFlagDisplayName = "";
			          WTUser wtuser = (WTUser) CommonUtil.getObject(docHash.get("worker_id"));
                      PeopleData pdata = new PeopleData(wtuser);
                      String userDept = pdata.departmentName;
			          
			          if(docActFlag.equals("") || docActFlag.equalsIgnoreCase("DOC")) docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04119");
			          else docActFlagDisplayName = messageService.getString("e3ps.message.ket_message", "04120");
			          %>
                    <tr>
			          <td align="center" colspan="2"><%=docActFlagDisplayName %></td>
			          <td align="center"><%=docHash.get("activity_type_name") %></td>
			          <td align="center"><%=userDept %></td>
			          <td align="center"><%=StringUtils.stripToEmpty(docHash.get("worker_name")) %>&nbsp;</td>
			          <td align="center"><%=StringUtils.stripToEmpty(docHash.get("complete_request_date")) %>&nbsp;</td>
			        </tr>
			        <%
			            }
			        }
			        else
			        {
			        %>
			          <tr>
                            <td rowspan="2" class="title">ECN</td>
                            <td class="title" colspan="2">구분</td>
                            <td class="title">후속업무</td>
                            <td class="title">부서</td>
                            <td class="title">담당자</td>
                            <td class="title">완료요청일</td>
                      </tr>
			          <tr>
			              <td colspan="6" align="center"><%=messageService.getString("e3ps.message.ket_message", "01497") %><%--변경 문서가 없습니다--%>.</td>
			          </tr>
			        <%
			        }
					ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEco );
					Vector attachFileList = ContentUtil.getSecondaryContents(holder);
					if( attachFileList.size() > 0 ){
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
			                      //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
			                      url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
			                      url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
			           %>
			              <tr>
			                <% if (fileCnt== 0){
			                %>
			                <td class="title" rowspan="<%=attachFileList.size()%>">첨부</td>
			                <% 
			                }
			                %>
                            <td class="title"><%=fileCnt+1%></td>
			                <td colspan="5"><%=url%></td>
			              </tr>
			           <%
			                }
			          }
			          else
			          {
			           %>
			             <tr>
			                 <td class="title" rowspan="<%=attachFileList.size()%>">첨부</td>
			                 <td colspan="6" align="center"><%=messageService.getString("e3ps.message.ket_message", "02800") %><%--첨부파일이 존재하지 않습니다--%></td>
			             </tr>
			           <%
			          }
			           %>
                    
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>