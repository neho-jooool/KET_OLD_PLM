<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import = "e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                                    e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.content.*,
                  e3ps.dms.entity.KETStandardTemplate,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.vc.VersionControlHelper,
                  wt.org.WTUser,
                  wt.content.*,
                                    java.util.Vector"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
                                    

<%
    String oid = ParamUtil.getParameter(request, "oid");
    KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(oid);

    Vector vecDivisioncode = NumberCodeHelper.manager.getNumberCodeLevel("DIVISIONFLAG", "top");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src="../common/content/content.js?ver=0.1"></script>

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
<script language='javascript'>
<!--

  //부서 검색 팝업  Open
    function selectDepartment(rsltArrayObject) {
        
        document.forms[0].deptCode.value = rsltArrayObject[0].NAME;
    }
    
    function addDepartment(){
        
        var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=s&invokeMethod=selectDepartment";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=465";
        getOpenWindow2(url,'standardDocCreate', 430, 465, opts);
        
    }

    //필드 값 삭제
  function deleteValue(param){
      document.getElementById(param).value = "";
  }

  //저장
  function save(){
      if (isNullData(document.forms[0].docName.value)){
          alert('<%=messageService.getString("e3ps.message.ket_message", "01416") %><%--문서명 입력하시기 바랍니다--%>');
          return;
      }

      var flag = false;
      var fileTable = document.getElementById("fileTable");

      for (var inx = 1 ; inx < fileTable.rows.length ; inx++){
            if (fileTable.rows[inx].cells[1].children[0].value != ""){
                flag = true;
            }
        }

        if (!flag){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03015") %><%--표준양식 파일을 등록하십시오--%>');
          return;
      }

      showProcessing();
      disabledAllBtn();

      document.forms[0].action = '/plm/servlet/e3ps/StandardDocServlet?cmd=update';
      document.forms[0].encoding = 'multipart/form-data';
      document.forms[0].submit();
  }

  //초기유동관리 지정 상세화면 이동
  function goView(){
        var oid = document.forms[0].oid.value;

        showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/dms/ViewStandardDoc.jsp?oid='+oid;
    }

  //초기유동관리 목록화면 이동
  function goList(){

      showProcessing();
      disabledAllBtn();

      document.SearchListForm.action = '/plm/servlet/e3ps/StandardDocServlet?cmd=search';
      document.SearchListForm.submit();
    }
   /* 2014.07.24 이력조회팝업 추가 */
   function historySearch(){
	   var url = "/plm/jsp/dms/CreateStandardHistorySearch.jsp?oid=<%=oid%>";
       window.open(url, "_blank","width=500, height=300,status=no,scrollbars=yes,resizable=no");
   }
//-->
</script>
</head>
<body class="popup-background popup-space">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03014") %><%--표준양식 수정--%></td>
                
              </tr>
            </table>
          </td>
        </tr>
       </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <%-- <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01378") %>목록</a></td>
                       <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기닫기</a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td> --%>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%><span class="red">*</span></td>
          <td width="270" class="tdwhiteL">
              <select name="divisionCode" class="fm_jmp" style="width:265">
              <option value="0"> <%=messageService.getString("e3ps.message.ket_message", "00895") %><%--공통--%> </option>
              <%  for (int inx = 0 ; inx < vecDivisioncode.size() ; inx++){  %>
                  <option value=<%=((NumberCode)vecDivisioncode.get(inx)).getCode()%>
                      <% if((((NumberCode)vecDivisioncode.get(inx)).getCode()).equals(StringUtil.checkNull(ketStandardTemplate.getDivisionCode())) )out.print("selected");%> >
                      <%=((NumberCode)vecDivisioncode.get(inx)).getName()%>
                  </option>
                  <%  }  %>
            </select>
          </td>
          <td width="120" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00947") %><%--관리부서--%></td>
          <td width="270" class="tdwhiteL0">
              <input type="text" name="deptCode" id="deptCode" class="txt_field" style="width:212" value=<%=StringUtil.checkNull(ketStandardTemplate.getDeptCode())%> >
              &nbsp;<img src="../../portal/images/icon_5.png" border="0" onClick="javascript:addDepartment();" style="cursor:hand;">
            &nbsp;<img src="../../portal/images/icon_delete.gif" border="0" onclick="javascript:deleteValue('deptCode');" style="cursor:hand;">
          </td>
        </tr>
        <tr>
          <td class="tdblueL">Rev<%--Rev--%></td>
          <td class="tdwhiteL"><a href="javascript:historySearch();"><%=StringUtil.checkNull(ketStandardTemplate.getVersionInfo().getIdentifier().getValue())%></a></td>  
          <td class="tdblueL non-back">최종배포일</td>
          <td class="tdwhiteL0"><%=DateUtil.getDateString(ketStandardTemplate.getModifyTimestamp(), "date")  %></td>
        </tr>
          <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">
              
              <%   Kogger.debug("UpdateStandardDoc", null, null, "********************"+ketStandardTemplate.getTitle()); %>
              <input type="text" name="docName" class="txt_field" style="width:665" value="<%=ketStandardTemplate.getTitle()%>" onKeyUp="common_CheckStrLength(this, 64)" onChange="common_CheckStrLength(this, 64)" >
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02102") %><%--양식설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:80">
              <textarea name="docDesc" class="txt_field" style="width:665; height:96%" onKeyUp="common_CheckStrLength(this, 1333)"  onChange="common_CheckStrLength(this, 1333)" ><%=StringUtil.checkNull(ketStandardTemplate.getTemplateDescription())%></textarea>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03008") %><%--표준양식--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
                            <tr>
                                <td></TD>
                                <td align="right">
                                    <table >
                                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td width="2"></td>
                          </tr>
                        </table>
                                </td>
                            </tr>
                            <tr align="center" bgcolor="#ffffff" id="fileTableRow" style="display:none">
                                <td align="center" valign="middle" width="24" height="22" >
                                    <input type="checkbox" name="fileDelete">
                                </td>
                                <td align="center">
                                    <input type="file" name="filePath" onChange='isValidSecondarySize(this)' id=input onkeyDown="this.blur()" class="txt_field" size="89">
                                </td>
                            </tr>
                            <%
                                ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(oid));
                                Vector files = ContentHelper.getContentList(holder);

                                for(int inx=0; files !=null && inx<files.size(); inx++) {
                                    ApplicationData appData = (ApplicationData)files.get(inx);
                                    String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                    //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                    String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                            %>
                                    <tr align="center"  >
                                        <td align="center" width="24" height="22"><input type="checkbox" name="fileDelete"></td>
                                        <td align="left"><input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" style="width:100%">&nbsp;
                                            <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a></td>
                                    </tr>
                            <%
                                }
                            %>
                        </table>
            <table border="0" cellspacing="0" cellpadding="0" width="660">
              <tr>
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<form method="post"  name="SearchListForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="divisionCode" value="">
<input type="hidden" name="deptCode" value="">
<input type="hidden" name="docName" value="">
<input type="hidden" name="docDesc" value="">
</form>
<script>
<% String type = request.getParameter("type");
   if("refresh".equals(type)){ %>
             window.opener.search();
<%   }%>
</script>
</body>
</html>
