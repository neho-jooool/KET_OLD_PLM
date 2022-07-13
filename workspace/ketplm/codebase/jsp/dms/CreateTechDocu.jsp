<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.HashMap,
                java.util.List,
                java.util.Map,
                java.util.ArrayList,
                java.util.Vector,
                e3ps.dms.service.KETDmsHelper,
                e3ps.common.util.*,
                e3ps.common.code.*,
                e3ps.groupware.company.*,
                e3ps.dms.entity.KETDocumentCategory"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다*/;
    String docTypeCode = "";

    String divisionCode = "";
    
    if(CommonUtil.isMember("전자사업부")){
        divisionCode = "ER";
    }else if(CommonUtil.isMember("자동차사업부")){
        divisionCode = "CA";
    }else{
        divisionCode = "SU";
    }

    String security = "S1";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01129") %><%--기술문서등록--%></title>
<%@include file="/jsp/common/processing.html" %>
<script type="text/javascript" src="../../portal/js/org.js"></script>
<script type="text/javascript" src="../../portal/js/common.js"></script>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
-->
</style>
<script type="text/javascript">
<!--
//UI 초기화
    $(document).ready(function(){
        $("#deptTR").hide();
        
       //기술 문서 분류 suggest
       SuggestUtil.bind('TECHDOCTYPE', 'categoryCodeTxt', 'categoryCode');
       
       $("#categoryCode").change(function(){
            if($(this).val() == ""){
                $("[name=categoryCodeTxt]").val("");
            }else{
            }
            
            deptTrSetting($(this).val());
            
        });
    
    });


    function isNull(str) {
        if(str==null||str==""){
            return true;
        }
        return false;
    }

    function insertFileLine() {
        //첨부파일 라인을 추가한다.
        var tBody = document.getElementById("iFileTable");
        var innerRow = tBody.insertRow();
        var innerCell = innerRow.insertCell();
        var filePath = "filePath"+tBody.rows.length;

        var filehtml = "";
        filehtml += "  <input type='checkbox' name='iFileChk' id='checkbox' >";
        filehtml += "  <input name='"+filePath+"' type='file' class='txt_fieldRO' size='85'>";
        innerCell.innerHTML = filehtml;
    }

    function deleteFileLine() {
    //선택된 첨부파일 라인을 삭제한다.
        var body = document.getElementById("iFileTable");
        if (body.rows.length == 0) return;
        var file_checks = document.form01.iFileChk;
        if (body.rows.length == 1) {
            if (file_checks[0]=="[object]"){
                if (file_checks[0].checked){
                    body.deleteRow(0);
                }
            }else{
                if (file_checks.checked){
                    body.deleteRow(0);
                }
            }
        } else {
            for (var i = body.rows.length; i > 0; i--) {
                if (file_checks[i-1].checked) body.deleteRow(i - 1);
            }
        }
    }

    //취소시 검색화면으로 이동한다.
    function doCancel(){
        document.location.href="/plm/jsp/dms/SearchTechDocument.jsp";
    }

    //저장한다.
    function doSave () {
        if ( !valcheck() ) {
            return;
        }
        else {
            var d = document.form01;

            d.documentDescription.value = "";
            // innoditor WebEditor
            // 첫번째 매개변수 => true : < & 특수문자 처리,  false : 처리안함
            // 두번째 매개변수 => 이노디터 번호
            d.webEditor.value = fnGetEditorHTMLCode(false, 0);
            d.webEditorText.value = fnGetEditorText(0);

            d.encoding = 'multipart/form-data';
            d.action = "/plm/servlet/e3ps/TechDocumentServlet?cmd=create";
            showProcessing();
          disabledAllBtn();
            d.submit();
        }
    }

    //숫자판별
    function isNotDigit(str) {
        var pattern = /^[0-9]+$/;

        if(!pattern.test(str)){
            return true;
        }
        return false;
    }
    
    function checkDisabledCategory(categoryCode){
        var rtn = '';
        $.ajax({
            url : "/plm/ext/dms/isDisabledCategory.do",
            type : "POST",
            data : {
                devDocCagegoryCode : categoryCode
            },
            dataType : 'json',
            async : false,
            success : function(data) {
                rtn = data;
            }
        });
        return rtn;
    }

    //필수입력체크
    function valcheck() {
        var d = document.form01;

        if(( d.categoryCode.value=="0" )||( isNull(d.categoryCode.value) )) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01425") %><%--문서분류는 반드시 입력해야 합니다--%>');
            return false;
        }
        
        var msg = checkDisabledCategory(d.categoryCode.value);
        
        if(msg != ''){
            alert(msg);
            return false;
        }

        if ( isNull(d.documentName.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01418") %><%--문서명은 반드시 입력해야 합니다.--%>');
            return false;
        }else{
            var s = d.documentName.value;
            if (s.length > 160){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01417") %><%--문서명은 160자를 초과할 수 없습니다--%>');
                return false;
            }
        }
        if (d.isDesign.value == "Y" && isNull(d.techDeptCode.value) ) {
            alert('부서조회권한을 설정해야합니다.');
            return false;
        }
        var strHTMLCode = fnGetEditorHTMLCode(false, 0);
        if ( strHTMLCode == "" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01429") %><%--문서설명은 반드시 입력해야 합니다.--%>');
            return false;
        }

        if(isNull(d.iFile0.value)){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02685") %><%--주첨부파일을 첨부하여 주십시오.--%>');
            return false;
        }
        return true;
    }
    
    function setTechDocCategory(checkedNode){
        var nodeIdStr='', nodeNameStr='';
        for(var i=0; i < checkedNode.length; i++){
            if(i == checkedNode.length - 1){
                nodeIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            }else{
                nodeIdStr += checkedNode[i].id+','; 
                nodeNameStr += checkedNode[i].name+',';
            }
        }
        $('[name=categoryCode]').val(nodeIdStr);
        $('[name=categoryCodeTxt]').val(nodeNameStr);
    }
    
//-->
</script>

<script type="text/javascript">
//Document Category Ajax
function numCodeAjax(docTypeCode, parentCode, targetId) {
    $.ajax( {
        url : "/plm/servlet/e3ps/DocumentCategoryAjax",
        type : "POST",
        data : {docTypeCode:docTypeCode, parentCode:parentCode},
        dataType : 'json',
        async : false,
        success: function(data) {
//             $("#"+targetId).append("<option value=''>"+ "" +"</option>");
            $.each(data.returnObj, function() {
                $("#"+targetId).append("<option value='"+this.categoryCode+"'>"+ this.categoryName +"</option>");
            });
        }
    });
}

function setCategoryCallBack(returnValue){

    $('[name=categoryCode]').val(returnValue[0].id);//id
    $('[name=categoryCodeTxt]').val(returnValue[0].name);//name
    deptTrSetting(returnValue[0].id);
}

function initDeptTr(){
    $("[name=techDeptName]").val("");           
    $("[name=techDeptCode]").val("");
    $("#deptTR").hide();
}

function deptTrSetting(categoryCode){
    
    var isDesign = '';
    
    initDeptTr();
    $("[name=isDesign]").val("");
    $.ajax({
        url : "/plm/ext/dms/setDevDocCategory.do",
        type : "POST",
        data : {
            devDocCagegoryCode : categoryCode
        },
        dataType : 'json',
        success : function(data) {
            
            isDesign = data.attribute1;
            
            if(isDesign == 'Y'){
                $("[name=isDesign]").val("Y");
                //$("#standardTR").hide();
                $("#deptTR").show();
            }
        }
    });
}

function setDocCategory(){
    
    var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=TECH&singleSel=true&onlyLeaf=Y&modal=N&fnCall=setCategoryCallBack";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    
    getOpenWindow2(url,'techDocCreate', 450, 350, opts);
    
    
    
}
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
g_nEditorWidth = 890;
g_nEditorHeight = 500;

//]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<!-- 이노디터 JS Include End -->
</head>
<body class="popup-background02 popup-space">
<form name="form01" method="post" >
<input type="hidden" name="security" value="<%=security%>">

<!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display:none"></textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
<!-- 이노디터에서 작성된 내용을 보낼 Form End -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01126") %><%--기술문서 등록--%></td>
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
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%><span class="red">*</span></td>
          <td colspan="3" width="650" class="tdwhiteL0">
            <input type="text" id="categoryCodeTxt" name="categoryCodeTxt" class="txt_field" style="width: 380px">
            <input type="hidden" id="categoryCode" name="categoryCode" value="">
            <input type="hidden" id="isDesign" name="isDesign" value="">
            <a href="javascript:setDocCategory();">
            <img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="javascript:CommonUtil.deleteValue('categoryCode', 'categoryCodeTxt', 'isDesign');initDeptTr();">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0"><input type="text" name="documentName" class="txt_field"  style="width:640" ></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%><span class="red">*</span></td>
          <td width="260" class="tdwhiteL">
         <%--  <% if ( divisionCode != null && (divisionCode.equals("CA") || divisionCode.equals("EA")) ) { %>
              <%
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "DIVISIONTYPE");
              numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

              for ( int i=0; i<numCode.size(); i++ ) {
                  if ( divisionCode.equals(numCode.get(i).get("code")) ) {
              %>
                      <input type="hidden" name="divisionCodeStr" value="<%=numCode.get(i).get("code")%>" ><%=numCode.get(i).get("value") %>
              <%
                  }
              }
              %>
          <% }else { %> --%>
               <select name="divisionCodeStr" style="width:258" >
              <%
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "DIVISIONTYPE");
              numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

              for ( int i=0; i<numCode.size(); i++ ) {
               if ( divisionCode.equals(numCode.get(i).get("code")) ) {
              %>
              <option value="<%=numCode.get(i).get("code") %>" selected="selected"><%=numCode.get(i).get("value")%></option>
              <%
               }else {
              %>
              <option value="<%=numCode.get(i).get("code") %>"><%=numCode.get(i).get("value")%></option>     
              <%    
               }
              }
              %>
              </select>
         <%--  <% } %> --%>
            </td>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530") %><%--보안등급--%></td>
          <td class="tdwhiteL0">
              <%
              parameter.clear();
              parameter.put("locale",   messageService.getLocale());
              parameter.put("codeType", "SECURITYLEVEL");
              numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

              for ( int i=0; i<numCode.size(); i++ ) {
                  if ( "S1".equals(numCode.get(i).get("code")) ) {
              %>
                  <input type="hidden" disabled name="secu" value="대외비"><div style="width: 258px;"><%=numCode.get(i).get("value") %></div>
              <%
                  }
              }
              %>
          </td>
        </tr>
        <tr id="deptTR">
          <td width="130" class="tdblueL">부서조회권한<span class="red">*</span></td>
          <td class="tdwhiteL">
            <input type="text" name="techDeptName" class="txt_field" style="width: 30%" readonly> 
            <input type="hidden" name="techDeptCode"> 
            <a href="javascript:SearchUtil.addAllDepartment('techDeptCode', 'techDeptName','y');">
            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('techDeptCode','techDeptName');">
            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            
            <td class="tdblueL">사용자조회권한 <span class="red">*</span></td>
              <td class="tdwhiteL" colspan="2"><select name="duty" id="duty" style="width: 70%">
                  <%
                        Vector dutyNameList = CompanyState.dutyNameList;
                        Vector dutyCodeList = CompanyState.dutyCodeList;
                      for (int i = 0; i < dutyCodeList.size(); i++) {
                          if(!"고문".equals(dutyNameList.get(i)) && !"감사".equals(dutyNameList.get(i))){
                  %>
                          <option value="<%=dutyCodeList.get(i)%>" <%if("연구원".equals((String)dutyNameList.get(i)))out.print("selected");%> ><%=dutyNameList.get(i)%></option>
                  <%
                          }
                      }
                  %>
              </select></td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02667") %><%--주 첨부파일--%><span class="red">*</span></td>
          <td colspan="3" width="650" class="tdwhiteL0">
              <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <input name="iFile0" type="file" class="txt_fieldRO" style="width:640;">
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
          <td colspan="3" width="650" class="tdwhiteL0">
              <table width="645" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="645" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertFileLine()" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteFileLine()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
                <td align="right">&nbsp;</td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="645">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="645">
              <tbody id="iFileTable"/>
            </table>
            <table width="645" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="4" class="tdwhiteL0" style="width:100%; height:500;">
<!--              <textarea name="documentDescription" class="txt_field"  style="width:640; height:96%"></textarea>-->

                <textarea name="documentDescription" rows="0" cols="0" style="display:none"></textarea>
                <textarea name="webEditorText" rows="0" cols="0" style="display:none"></textarea>
                <!-- Editor Area Container : Start -->
                <div id="EDITOR_AREA_CONTAINER"></div>
                <!-- Editor Area Container : End -->
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
</body>
</html>
