<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.CheckoutLink"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="ext.ket.shared.content.service.KETContentHelper"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Vector"%>

<%@page import="wt.content.ContentRoleType"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="wt.lifecycle.State"%>
<%@page import="wt.query.*"%>
<%@page import="wt.fc.QueryResult"%>

<%@page import="e3ps.common.util.CharUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="e3ps.common.content.E3PSContentHelper"%>
<%@page import="e3ps.common.content.uploader.FileUploader"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.ProjectChangeStop"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.ReviewProject"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="ext.ket.shared.content.*"%>
<%@page import="e3ps.project.historyprocess.HistoryHelper"%>
<%@page import="e3ps.project.ProjectOemTypeLink"%>
<%@page import="e3ps.project.CheckoutLink"%>           
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02689") %><%--중단 사유--%></title>
<base target="_self">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script type="text/javascript" src="/plm/portal/js/script.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/select.js"></script>
<script type="text/javascript" src="/plm/portal/js/table.js"></script>
<script type="text/javascript" src="/plm/portal/js/viewObject.js"></script>
<script type="text/javascript" src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript" src="/plm/portal/js/checkbox2.js"></script>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PROJECTSTOPTYPE");
    List<Map<String, Object>> stopTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    String oid = request.getParameter("oid");
    String command = request.getParameter("command");

    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ReviewProject rp = null;
    ProductProject pp = null;
    MoldProject mp = null;
    if ( project instanceof ReviewProject ) {
        pjtType = "review";
        rp = (ReviewProject)project;
    }
    else if ( project instanceof ProductProject ) {
        pjtType = "product";
        pp = (ProductProject)project;
    }
    else if ( project instanceof MoldProject ) {
        pjtType = "mold";
        mp = (MoldProject)project;
    }

    String contentType = request.getContentType();
    FileUploader uploader = null;

    e3ps.common.content.fileuploader.FormUploader fileUploader = null;
    String stopDetil = "";
    String webEditor = "";
    String webEditorText = "";
    String stopType = "";
    
    fileUploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
    Hashtable param = fileUploader.getFormParameters();
    String deleteOID = (String) param.get("deleteOID");
    
    deleteOID = StringUtils.removeEnd(deleteOID, "&");
    
    
    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 ) {
        

        command = (String)param.get("command");
        stopDetil = (String)param.get("stopDetil");
        webEditor = (String)param.get("webEditor");
        webEditorText = (String)param.get("webEditorText");
        oid = (String)param.get("oid");
        stopType = (String) param.get("stopType");
    }
    else {
        command = CharUtil.E2K(request.getParameter("command"));
        oid = CharUtil.E2K(request.getParameter("oid"));
    }
    
    ProjectChangeStop ps = ProjectHelper.getStopProject(project);
    
    
    Object webEditorText_ = "";
    Object webEditor_ = "";
    if(ps != null && !"중지완료".equals(ps.getChangeStopType()) ){
	    webEditor_ = ps.getWebEditor();
        webEditorText_ = ps.getWebEditorText();
        stopType = ps.getChangeStopType();
    }

    if ( command.equals("create") ) {
        //project.setStopedDetil(stopDetil);
        //project = (E3PSProject)PersistenceHelper.manager.save(project);
       
       
        
        if(ps == null || (ps != null && "중지".equals(ps.getChangeStopType()))){
            
            QueryResult oemTypeQr = PersistenceHelper.manager.navigate(project, ProjectOemTypeLink.OEM_PJT_TYPE_ROLE, ProjectOemTypeLink.class, false);
            // 일정변경 상태 변경 (체크아웃, Working Copy 생성) - 일정변경사유는 저장하지 않음
            CheckoutLink checkoutLink = (CheckoutLink) HistoryHelper.checkOut(project, "", 999, "STOPINWORK");

            // 일정변경 상태 변경 후 화면 이동

            if (checkoutLink != null) {

                String copyOid = CommonUtil.getOIDString(checkoutLink.getWorkingCopy());
                oid = copyOid;
                project = (E3PSProject) CommonUtil.getObject(copyOid);
                
                // 파생차종 복사
                while (oemTypeQr.hasMoreElements()) {
                    ProjectOemTypeLink link = (ProjectOemTypeLink) oemTypeQr.nextElement();
                    ProjectOemTypeLink copyLink = ProjectOemTypeLink.newProjectOemTypeLink(link.getOemPjtType(), project);
                    PersistenceHelper.manager.save(copyLink);
                }
            }
            
            ps = ProjectChangeStop.newProjectChangeStop();  
        }else{
            
            String deleteFiles[] = deleteOID.split("&");
            
            //기존 존재하던 모든 파일 목록
            Vector oldFiles = ContentUtil.getSecondaryContents(ps);
            
            for(int i = 0; i < oldFiles.size(); i++) {
                ContentInfo info = (ContentInfo) oldFiles.elementAt(i);
                
                if(deleteFiles != null){
                    for(int j=0; j<deleteFiles.length; j++){
                        if(info.getContentOid().equals(deleteFiles[j])){
                           ps = (ProjectChangeStop)E3PSContentHelper.service.delete(ps, (ApplicationData)CommonUtil.getObject(info.getContentOid()));
                        }
                    }    
                }
            }
        }
        
        ps.setPcsMaster(project.getMaster());
        ps.setChangeType("중지");
        ps.setChangeDetil(stopDetil);
        ps.setChangeStopType(stopType);

        ps.setWebEditor( webEditor );
        ps.setWebEditorText( webEditorText );

        ps.setChangeDate(DateUtil.getCurrentTimestamp());
        ps.setChangeAttr1(SessionHelper.manager.getPrincipalReference().getFullName());
        ps = (ProjectChangeStop)PersistenceHelper.manager.save(ps);

        Vector files = fileUploader.getFiles();
        if ( files != null ) {
            for ( int i = 0; i < files.size(); i++ ) {
                ps = (ProjectChangeStop)E3PSContentHelper.service.attach(ps, (WBFile)files.get(i), "", ContentRoleType.SECONDARY);
            }
        }

/*         Hashtable userH = ProjectUserHelper.manager.getProjectUserForMail(project);

        State lstate = project.getLifeCycleState();
        String state_str = lstate.getDisplay(Locale.KOREA);
        if ( lstate.toString().equals("PROGRESS") ) {
            state_str = "재시작";
        } */

        // mail 발송을 위해 아래처리 처리 합니다.
        // 프로젝트가 {0} 되었습니다. 업무에 참조하시길 바랍니다.
/*         String text = "msg.mail.ProjectHelper.project_status_is_please_refer_to_your_work" + "||" + ps.getChangeType();
        ProjectHelper._sendProjectInfoMail(project, userH, text, "ProjectMailNoti"); */
    }

    if ( command.equals("create") ) {
%>
<script>
        alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');
        <%if ( "review".equals(pjtType) ) {%>
                opener.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&command=stateChange&state=STOPED";
        <%}
            else if ( "product".equals(pjtType) ) {%>
                opener.location.href = "/plm/jsp/project/ProjectView.jsp?oid=<%=oid%>&command=stopStart";
        <%}
            else if ( "mold".equals(pjtType) ) {%>
                opener.location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>&command=stateChange&state=STOPED";
        <%}%>
        self.close();
</script>
<%
    }
%>

<script type="text/javascript">
    // 첨부파일 시작 *****************************************************************************************************************
    function insertFileLine() {
        var tBody = document.getElementById("secondaryTable");
        var innerRow = tBody.insertRow();
        //var innerCell = innerRow.insertCell();
        var filePath = "secondaryFile"+tBody.rows.length;

        //innerCell.innerHTML = "<input name='secondarySelect' type='checkbox' class='Checkbox'>";
        //innerCell.innerHTML += "<input name='"+filePath+"' type='file' class='txt_field' style='width:95%;'>";

        newTd = innerRow.insertCell();//delete
        newTd.className = "tdwhiteM";
        newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a><input name='secondarySelect' type='hidden'>";

        newTd = innerRow.insertCell();//file
        newTd.className = "tdwhiteL0";
        //newTd.colSpan = 2;
        newTd.innerHTML = "<input name='"+filePath+"' type='file' class='txt_field' style='width:550px;'>";
    }

    function deleteFileLine() {
        var body = document.getElementById("secondaryTable");
        if ( body.rows.length == 0 ) return;
        var file_checks = document.forms[0].secondarySelect;
        var returnDel = document.forms[0].deleteOID.value;
        alert(returnDel);
        if ( body.rows.length == 1 ) {
        	returnDel = document.forms[0].secondarySelect.value + "&";
            body.deleteRow(0);
        }
        else {
            for ( var i = body.rows.length; i > 0; i-- ) {
                if (file_checks[i-1].checked) {
                	returnDel = returnDel + document.forms[0].secondarySelect[i-1].value + "&";
                	alert(returnDel);
                	body.deleteRow(i - 1);
                }
            }
        }
        alert(returnDel);
        document.forms[0].deleteOID.value = returnDel;
    }
    // 첨부 파일 끝 *****************************************************************************************************************

    function doCreateJsp() {
        if ( !checkValidate() ) {
            return;
        }

        if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "03346") %><%--프로젝트를 중지 하시겠습니까?--%>") ) {
            return;
        }

        document.frm.stopDetil.value = "";
        // innoditor WebEditor
        // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
        // 두번째 매개변수 => 이노디터 번호
        document.frm.webEditor.value = fnGetEditorHTMLCode(false, 0);
        document.frm.webEditorText.value = fnGetEditorText(0);

        document.frm.command.value = "create";
        document.frm.method = "post";
        document.frm.submit();
    }

    function checkValidate() {
        if ( getCheckedValue(document.frm.stopType) == "" ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02697") %><%--중지 구분을 선택 하십시오--%>');
            return false;
        }

        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02699") %><%--중지 사유를 입력 하십시오--%>');
            return false;
        }
        return true;
    }

    function getCheckedValue(radioObj) {
        if(!radioObj)
            return "";
        var radioLength = radioObj.length;
        if(radioLength == undefined)
            if(radioObj.checked)
                return radioObj.value;
            else
                return "";
        for(var i = 0; i < radioLength; i++) {
            if(radioObj[i].checked) {
                return radioObj[i].value;
            }
        }
        return "";
    }
    
    function deleteFile(contentOid){
    	document.forms[0].deleteOID.value += contentOid+"&";
    }
    
    $(document).ready(function() {
        var strHTMLCode = document.forms[0].webEditor.value;
        
        // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
        // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
        // 세번째 매개변수 => 이노디터 번호
        fnSetEditorHTMLCode(strHTMLCode, false, 0);
        
    });
</script>

<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
//<![CDATA[

// -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
// -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
// -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
var g_arrSetEditorArea = new Array();
g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

//]]>
</script>

<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>

<script type="text/javascript">
//<![CDATA[

// -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우

// Skin 재정의
//g_nSkinNumber = 0;

// 크기, 높이 재정의
g_nEditorWidth = 710;
g_nEditorHeight = 390;


//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<script type="text/javascript">
//<![CDATA[
function fnSetEditorHTML() {
    var strHTMLCode = document.frm["webEditor"].value;
    
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(strHTMLCode, false, 0);
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.frm["hdnBackgroundColor"].value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.frm["hdnBackgroundImage"].value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.frm["hdnBackgroundRepeat"].value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
//]]>
</script>
<!-- 이노디터 JS Include End -->
</head>

<body>
<!-------------------------------------- 컨텐츠 시작 //-------------------------------------->
<form id="frm" name="frm" method="POST" enctype="multipart/form-data">
<input type="hidden" name="oid" value="<%=oid%>" >
<input type="hidden" name="command">
<input type=hidden name=deleteOID>

<!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"><%=webEditor_%></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 보낼 Form End -->

<table style="width: 720px;">
    <tr>
    <td valign="top">
<!-------------------------------------- 상단 제목 버튼 시작 //-------------------------------------->
        <table style="width: 720px;">
        <tr>
            <td>
                <table style="width: 720px;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                     <table style="height: 28px;">
                         <tr>
                          <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                          <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02698") %><%--중지 사유--%></td>
                          <td style="width: 10px;"></td>
                         </tr>
                     </table>
                    </td>
                    <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
                <table style="width: 720px;">
                <tr>
                    <td class="space5"> </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 720px;">
        <tr>
            <td align="right">
                <table>
                <tr>
                    <td>&nbsp;</td>
                 <td>
                  <table>
                  <tr>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="Javascript:doCreateJsp();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                  </table>
                 </td>
                 <td width="5px"></td>
                 <td><table border="0" cellspacing="0" cellpadding="0">
                    <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                    href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table></td>
                </table></td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
<!-------------------------------------- 상단 제목 버튼 끝 //-------------------------------------->
        <table style="width: 720px;">
        <tr>
            <td>
            <!------------------------------ 본문 Start //------------------------------>
                <table >
                <tr>
                    <td class="space5"> </td>
                </tr>
                </table>
                <table style="width: 720px;">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
                </table>
                <table style="width: 720px;">
                <tr>
                    <td class="tdblueL" style="width:120px"><%=messageService.getString("e3ps.message.ket_message", "02696") %><%--중지 구분--%></td>
                    <td class="tdwhiteL0" style="width:600px">
                        <%
                        for ( int i=0; i<stopTypeNumCode.size(); i++ ) {
                        %>
                        <input id="stopType" name="stopType" type="radio" class="Checkbox" value="<%=stopTypeNumCode.get(i).get("code") %>" <%if ( stopType.contains( stopTypeNumCode.get(i).get("code").toString() ) ){%>checked<%}%> ><%=stopTypeNumCode.get(i).get("value")%>
                        <%
                        }
                        %>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL" style="width:120px"><%=messageService.getString("e3ps.message.ket_message", "02701") %><%--중지일--%><font color="red">*</font></td>
                    <td class="tdwhiteL0"><%=DateUtil.getToDay() %></td>
                </tr>
                <tr>
                    <td class="tdblueL" style="width:120px"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                    <td class="tdwhiteM0" style="padding:5px">
                        <table style="width: 600px;" class="table_border">
                            <tr>
                                <td style="width: 40px"  class="tdgrayM"><a href="#" onclick="insertFileLine();"><img src="/plm/portal/images/b-plus.png"></a></td>
                                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                            </tr>
                            <tbody id="secondaryTable"/>
                            <%
                                if ( ps != null ) {
                                    ContentHolder contentHolder = ContentHelper.service.getContents(ps);
                                    Vector secondaryFiles = ContentUtil.getSecondaryContents(contentHolder);
    
                                    if ( secondaryFiles.size() > 0 ) {
                                        for ( int i=0; i<secondaryFiles.size(); i++ ) {
                                            ContentInfo info = (ContentInfo)secondaryFiles.elementAt(i);
    
                                            String iconpath = "";
                                            String urlpath = "";
                                            if ( info == null ) {
                                                iconpath = "";
                                                urlpath = "";
                                            }
                                            else {
                                                iconpath = info.getIconURLStr();
                                                urlpath = info.getDownURLStr();
                                            }
                            %>
                              <tr>
                                  <td style="width: 40px" class="tdwhiteM"><a href="#" onclick="javascript:$(this).parent().parent().remove();deleteFile('<%=info.getContentOid()%>');"><img src="/plm/portal/images/b-minus.png"></a><input type="hidden" name="secondarySelect" value="<%=info.getContentOid()%>"></td>
                                  <td style="width: 450px" class="tdwhiteL0"><%=iconpath%><%=urlpath%></td>
                              </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="tdwhiteL0" colspan="2">
<!--                         <textarea name="stopDetil" rows=7 style="width:100%;"></textarea> -->

                        <textarea name="stopDetil" rows="0" cols="0" style="display:none"></textarea>
                        <textarea name="webEditorText" rows="0" cols="0" style="display:none"><%=webEditorText_%></textarea>
                        <!-- Editor Area Container : Start -->
                        <div id="EDITOR_AREA_CONTAINER"></div>
                        <!-- Editor Area Container : End -->
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>

</form>
</body>
</html>
