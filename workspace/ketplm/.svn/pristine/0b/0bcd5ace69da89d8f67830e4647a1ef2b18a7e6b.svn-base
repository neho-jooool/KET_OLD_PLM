<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.StringTokenizer,
                  java.util.Vector,
                  java.util.ArrayList,
                  java.util.HashMap,
                  java.util.Map,
                  java.util.List,
                  e3ps.groupware.company.PeopleData,
                  e3ps.common.util.*,
                  e3ps.common.content.*,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.dms.entity.*,
                  e3ps.project.E3PSProject,
                  e3ps.project.ProjectOutput,
                  wt.org.WTUser,
                  wt.session.SessionHelper,
                  wt.content.*,
                  wt.query.QuerySpec,
                  wt.query.ClassAttribute,
                  wt.query.OrderBy,
                  wt.fc.PersistenceHelper,
                  wt.fc.QueryResult,
                  e3ps.common.web.ParamUtil,
                  e3ps.lesson.LessonLearn,
                  e3ps.lesson.beans.LessonHelper"%>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />



<%
	String oid = ParamUtil.checkStrParameter(request.getParameter("oid"));
	LessonLearn lesson = (LessonLearn)CommonUtil.getObject(oid);

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    Map<String, Object> LessonMap = new HashMap<String, Object>();
    List<Map<String, Object>> Lessonlist = null;

    Lessonlist = LessonHelper.LessonData(lesson);
    LessonMap  = Lessonlist.get(0) ;
    String part_no   	= (String)LessonMap.get("part_no");
    String part_name 	= (String)LessonMap.get("part_name");
	String gubun   	 	= (String)LessonMap.get("gubun");
	String factory   	= (String)LessonMap.get("factory");
	String problem   	= (String)LessonMap.get("problem");
	String cause  	 	= (String)LessonMap.get("cause");
	String improve   	= (String)LessonMap.get("improve");
	String department   = (String)LessonMap.get("department");
	String isAttache 	=  (String)LessonMap.get("isAttache");

	String DieName 			=  (String)LessonMap.get("DieName");
	String DieNo 			=  (String)LessonMap.get("DieNo");
	String equipNo 			=  (String)LessonMap.get("equipNo");
	String equipName 		=  (String)LessonMap.get("equipName");
	String creator 			=  (String)LessonMap.get("creator");
	String create_date 		=  (String)LessonMap.get("create_date");

	String occur_date 		= (String)LessonMap.get("occur_date");
	String projectOid  		= (String)LessonMap.get("projectOid");
	String projectName  	= (String)LessonMap.get("projectName");
	String projectNo  		= (String)LessonMap.get("projectNo");
	String prodcut_gubun 	= (String)LessonMap.get("prodcut_gubun");
	//String cause_gubun 		= (String)LessonMap.get("cause_gubun");
	String cause_gubun 		= "";
	//String improve_gubun 	= (String)LessonMap.get("improve_gubun");
	String improve_gubun 	= "";
	String cause_gubun_check 		= (String)LessonMap.get("cause_gubun_check");
	String improve_gubun_check 	 	= (String)LessonMap.get("improve_gubun_check");

	String[] cause_temp = cause_gubun_check.split("\\|");
	String[] improve_temp = improve_gubun_check.split("\\|");


 	NumberCode nCode =null;
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "LESSONCOMMON");
    numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);


	for(int i=0; i<cause_temp.length; i++ ){
		for ( int j=0; j < numCode.size(); j++ ) {
	    	if(cause_temp[i].equals(numCode.get(j).get("code"))){
	    		if(i == cause_temp.length-1){
	    			cause_gubun += numCode.get(j).get("value");
	    		}else{
	    			cause_gubun += numCode.get(j).get("value")+",";
	    		}
	    	}
	    }
	}
	for(int i=0; i<improve_temp.length; i++ ){
		for ( int j=0; j < numCode.size(); j++ ) {
	    	if(improve_temp[i].equals(numCode.get(j).get("code"))){
	    		if(i == improve_temp.length-1){
	    			improve_gubun += numCode.get(j).get("value");
	    		}else{
	    			improve_gubun += numCode.get(j).get("value")+",";
	    		}

	    	}
	    }
	}

	ContentHolder holder = ContentHelper.service.getContents(lesson);
	Vector vec = ContentHelper.getContentList(holder);

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
  	String userName = user.getFullName();
  	PeopleData peoData = new PeopleData(user);

  	String amsg; // alert 메시지용 변수


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=javascript src="../../portal/js/Calendar.js"></script>
<script language=javascript src="/plm/portal/js/util.js"></script>
<script src='/plm/jsp/common/content/content.js'></script>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

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
<script language="JavaScript">
<!--
function go_to(url) {
  showProcessing();
  disabledAllBtn();
  window.location=url;
}

function deleteLesson() {
	  if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03290") %><%--경고!!\n삭제하시겠습니까?--%>") ) {
	    showProcessing();
	    disabledAllBtn();
	    
	    $.ajax({
            type : 'post',
            url : '/plm/servlet/e3ps/LessonLearnServlet?cmd=delete',
            data : $("#form01").serializefiles(),
            processData : false,
            contentType : false,
            success : function(data) {
            	opener.search();
                window.close();
            },
            error : function() {
                alert('삭제에 실패하였습니다.');
                // 프로그레스바 숨기기
                hideProcessing();
            }
        });
	    
	    
	    
	    
	   /*  var form01 = document.forms[0];
	    form01.action="/plm/servlet/e3ps/LessonLearnServlet?cmd=delete";
	    form01.submit(); */
	  }
	  return;
}

function goPrint(title){
	var sw=screen.width;
	var sh=screen.height;
	var w=800;//팝업창 가로길이
	var h=600;//세로길이
	var xpos=(sw-w)/2; //화면에 띄울 위치
	var ypos=(sh-h)/2; //중앙에 띄웁니다.
	var pHeader="<html><head><title>" + title +"</title><table><td class='font_03'>습득교훈관리</td></tr></table><br><br><br>";
	var pgetContent=document.getElementById("printarea").innerHTML + "<br>";      //innerHTML을 이용하여 Div로 묶어준 부분을 가져옵니다.
	var pFooter="</body></html>";
	pContent=pHeader + pgetContent + pFooter;
	pWin=window.open("","print","width=" + w +",height="+ h +",top=" + ypos + ",left="+ xpos +",status=yes,scrollbars=yes"); //동적인 새창을 띄웁니다.
	pWin.document.open(); //팝업창 오픈
	pWin.document.write(pContent); //새롭게 만든 html소스를 씁니다.
	pWin.document.close(); //클로즈
	pWin.print(); //윈도우 인쇄 창 띄우고
	pWin.close(); //인쇄가 되던가 취소가 되면 팝업창을 닫습니다.
}
//-->
</script>
</head>
<body class="popup-background popup-space">
<form id="form01" name=form01 method="post" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03453") %><%--습득교훈관리 상세조회--%></td>
                
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="../../portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:go_to('/plm/jsp/lesson/UpdateLessonLearn.jsp?oid=<%=oid%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteLesson();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:goPrint('print');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03451") %><%--출력--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:opener.search();window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="main_tab">
        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="partOid" value="">
	                	<input type="text" name="partName" class="txt_fieldRO" style="width:200" readonly value="<%=part_no %>">
	                	</td>
	                	<td width=""><input type="text" name="partNo" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=part_name %>"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
        	<td width="" class="tdblueL">Die No<%--Die No--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="dieOid" value="">
	                	<input type="text" name="DieNo" class="txt_fieldRO" style="width:200" readonly value="<%=DieNo %>">
	                	</td>
	                	<td width=""><input type="text" name="DieName" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=DieName %>"></td>
                	</tr>
                </table>
           		</td>
        </tr>
<!--
        <tr>
        	<td width="" class="tdblueL">Project No<%--Project No--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><input type="hidden" name="projectOid" value="">
	                	<input type="text" name="partName" class="txt_fieldRO" style="width:200" readonly value="<%=projectNo %>">
	                	</td>
	                	<td width=""><input type="text" name="partNo" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=projectName %>"></td>
                	</tr>
                </table>
           		</td>
        </tr>
-->
        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03445") %><%--설비번호--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipNo" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=equipNo%>" maxlength="8"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03446") %><%--설비명--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipName" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=equipName%>" maxlength="10"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><span class="red">*</span><%--제품구분--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="prodcut_gubun" class="txt_fieldRO" style="width:200" readonly value="<%=prodcut_gubun %>"></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><span class="red">*</span><%--구분--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="gubun" class="txt_fieldRO" style="width:200" readonly value="<%=gubun %>"></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03432") %><span class="red">*</span><%--공장--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="factory" class="txt_fieldRO" style="width:200" readonly value="<%=factory %>"></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><span class="red">*</span><%--부서--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="department" class="txt_fieldRO" style="width:200" readonly value="<%=department %>"></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><span class="red">*</span><%--작성자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205">
	                	<input style="width:200" type="text" id="creator" class="txt_fieldRO" readonly value="<%=creator%>">
	                	</td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><span class="red">*</span><%--작성일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="create_date" class="txt_fieldRO"  style="width:200" readonly value="<%=create_date%>">
		                </td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03450") %><span class="red">*</span><%--발생일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="occur_date" class="txt_fieldRO"  style="width:200" readonly value="<%=occur_date%>">
		                </td>
                	</tr>
                </table>
            </td>
        </tr>

        <%
        if ( "yes".equals(isAttache) ) {
        %>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="3" >
    <%
        for ( int i = 0 ; i < vec.size() ; i++ ) {
          ApplicationData appData = (ApplicationData)vec.get(i);
          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
    %>
                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;
                <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(i<vec.size()){%><br><%} %>
        <%
            }
        %>
            </td>
        </tr>
        <%
        }
        %>

        <tr>
          <td width="130" style="height:130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="problemComment" class='txt_fieldRO' id="problemComment" style="width:100%; height:96%" readonly><%=problem%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL">원인구분<%--원인구분--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="factory" class="txt_fieldRO" style="width:200" readonly value="<%=cause_gubun %>"></td>
                	</tr>
          </table>
            </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03434") %><%--원인--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="causeComment" class='txt_fieldRO' id="causeComment" style="width:100%; height:90%" size=2 readonly><%=cause%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL">개선대책구분<span class="red">*</span><%--개선대책--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><input type="text" name="factory" class="txt_fieldRO" style="width:200" readonly value="<%=improve_gubun %>"></td>
                	</tr>
          </table>
            </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선대책--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea name="improveComment" class='txt_fieldRO' id="improveComment" style="width:100%; height:90%" size=2 readonly><%=improve%></textarea></td>
        </tr>

      </table>
      <div id="printarea" style="visibility:hidden;">
      <table border="1" cellspacing="0" cellpadding="0" width="100%">
        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><%=part_no %></td>
	                	<td width=""><%=part_name %></td>
                	</tr>
                </table>
           		</td>
        </tr>

         <tr>
        	<td width="" class="tdblueL">Die No<%--Die No--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><%=DieNo %></td>
	                	<td width=""><%=DieName %></td>
                	</tr>
                </table>
           		</td>
        </tr>
<!--
        <tr>
        	<td width="" class="tdblueL">Project No<%--Die No--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205"><%=projectNo %></td>
	                	<td width=""><%=projectName %></td>
                	</tr>
                </table>
           		</td>
        </tr>
-->

        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03445") %><%--설비번호--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipNo" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=equipNo%>" maxlength="8"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03446") %><%--설비명--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="equipName" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=equipName%>" maxlength="10"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
        	<td width="" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            	<td colspan="3" class="tdwhiteL0">
            	<table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width=""><input type="text" name="prodcut_gubun" class="txt_fieldRO" style="width:200" id="textfield3" readOnly value="<%=prodcut_gubun%>" maxlength="10"></td>
                	</tr>
                </table>
           		</td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><%=gubun %></td>
                	</tr>
          </table>
            </td>
        </tr>



        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03432") %><%--공장--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><%=factory %></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><%=department %></td>
                	</tr>
          </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td width="205">
	                	<input style="width:200" type="text" id="creator" class="txt_fieldRO" readonly value="<%=creator%>">
	                	</td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="create_date" class="txt_fieldRO"  style="width:200" readonly value="<%=create_date%>">
		                </td>
                	</tr>
                </table>
            </td>
        </tr>

        <tr>
        	<td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03450") %><%--발생일자--%></td>
            <td colspan="3" class="tdwhiteL0">
            	<table border="0" width="255" cellspacing="0" cellpadding="0">
                	<tr>
	                	 <td width="205">
	                	 <input type="text" name="create_date" class="txt_fieldRO"  style="width:200" readonly value="<%=occur_date%>">
		                </td>
                	</tr>
                </table>
            </td>
        </tr>

        <%
        if ( "yes".equals(isAttache) ) {
        %>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%> </td>
            <td class="tdwhiteL0" colspan="3" >
    <%
        for ( int i = 0 ; i < vec.size() ; i++ ) {
          ApplicationData appData = (ApplicationData)vec.get(i);
          String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
          //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
          String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
    %>
                <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;
                <a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a><%if(i<vec.size()){%><br><%} %>
        <%
            }
        %>
            </td>
        </tr>
        <%
        }
        %>

        <tr>
          <td width="130" style="height:130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01438") %><%--문제점--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea style="overflow:hidden" name="problemComment" class='txt_fieldRO' id="problemComment" style="width:100%; height:96%" readonly><%=problem%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03448") %><%--원인구분--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><%=cause_gubun %></td>
                	</tr>
          </table>
            </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03434") %><%--원인--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea style="overflow:hidden"  name="causeComment" class='txt_fieldRO' id="causeComment" style="width:100%; height:90%" size=2 readonly><%=cause%></textarea></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03447") %><%--개선대책구분--%></td>
          <td colspan="3" class="tdwhiteL0">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
                	<tr>
	                	<td><%=improve_gubun %></td>
                	</tr>
          </table>
            </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL" style="height:130"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선대책--%></td>
          <td colspan="3" style="height:130" class="tdwhiteL0"><textarea style="overflow:hidden"  name="improveComment" class='txt_fieldRO' id="improveComment" style="width:100%; height:90%" size=2 readonly><%=improve%></textarea></td>
        </tr>

      </table>
      </div>
      </form>
</body>
</html>
