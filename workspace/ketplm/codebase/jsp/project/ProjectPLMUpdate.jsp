<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.project.*,
                 e3ps.project.beans.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.groupware.company.*"%>
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>

    <%
    Config conf = ConfigImpl.getInstance();
    String valuesize = request.getParameter("valuesize");

    if(valuesize == null){
        valuesize = "";
    }

    GenNumberCode genCode = new GenNumberCode();

    String oid = request.getParameter("oid");

    String valuePN = "";            //프로젝트 NO
    String valuePName = "";            //프로젝트 명(장비명)
    NumberCode valuePDivisionCode = null;    //개발유형
    NumberCode valuePLevelCode = null;      //난이도
    NumberCode valuePProductCode = null;    //제품군
    NumberCode valuePCustomerCode = null;    //발주처
    NumberCode valuePDevCompanyCode = null;    //개발조직
    NumberCode valuePMakeCompanyCode = null;  //생산조직
    NumberCode valuePModelCode = null;      //모델(차종)

    String pjtType = "";            //프로젝트 종류

    E3PSProject E3PSProject = null;
    E3PSProjectData projectData = null;

    if( StringUtil.checkString(oid) ) {
        E3PSProject = (E3PSProject)CommonUtil.getObject(oid);
        projectData = new E3PSProjectData(E3PSProject);

        valuePN = projectData.pjtNo;
        valuePName = projectData.pjtName;

//    valuePDivisionCode = projectData.pjtDivisionCodeLink;
//    valuePLevelCode = projectData.pjtLevelCodeLink;
//    valuePProductCode = projectData.pjtProductCodeLink;
//    valuePCustomerCode = projectData.pjtCustomerCodeLink;
//    valuePDevCompanyCode = projectData.pjtDevCompanyCodeLink;
        valuePMakeCompanyCode = projectData.pjtMakeCompanyCodeLink;
//    valuePModelCode = projectData.pjtModelCodeLink;




        pjtType = String.valueOf(projectData.pjtType);
    }

    String projectType = "";
    projectType = String.valueOf(E3PSProject.getPjtType());

//  ProjectManager pm  = E3PSProject.getManager();


    String selected = "";
    Vector tMap = null;
    %>

<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03052") %><%--프로젝트 PLM정보 수정--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="JavaScript">


function numberUpdate(){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03335") %><%--정말 수정하시겠습니까?--%>")) {
        return;
     }

     var form = document.forms[0];

     document.forms[0].action = "/plm/jsp/project/projectNumberUpdate.jsp";
     document.forms[0].method = "post";
     document.forms[0].submit();

}

function update(){
    var form = document.forms[0];


        //개발유형 CHECK
        if(form.divisioncode.value == '') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00655") %><%--개발유형을 선택하시기 바랍니다--%>");
            form.divisioncode.focus();
            return;
        }

        //난이도 CHECK
        if(form.levelcode.value == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01163") %><%--난이도를 선택하시기 바랍니다--%>');
            form.levelcode.focus();
            return;
        }

        //제품군 CHECK
        if(form.productcode.value == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02583") %><%--제품군을 선택하시기 바랍니다--%>');
            form.productcode.focus();
            return;
        }
         <% if(!(projectType.equals("2")) ) {%>
        //발주처 CHECK
        if(form.customercode.value == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01475") %><%--발주처를 선택하시기 바랍니다--%>');
            form.customercode.focus();
            return;
        }
        <%}%>
        <%if(!projectType.equals("0")){%>
        //생산조직 CHECK
        if(form.makecompanycode.value == '') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01789") %><%--생산조직을 선택하시기 바랍니다--%>");
            form.makecompanycode.focus();
            return;
        }
        <%}%>
        //개발조직 CHECK
        if(form.devcompanycode.value == '') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00667") %><%--개발조직을 선택하시기 바랍니다--%>");
            form.devcompanycode.focus();
            return;
        }

        <% if(!(projectType.equals("2")) ) {%>
        //모델(차종) CHECK
        if(form.modelcode.value == '') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01368") %><%--모델(차종)을 선택하시기 바랍니다--%>');
            form.modelcode.focus();
            return;
        }
        <%}%>



    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03335") %><%--정말 수정하시겠습니까?--%>")) {
        return;
    } else {

        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";

        document.forms[0].command.value = "PLMupdate";

        document.forms[0].method = "post";

        document.forms[0].submit();
    }

}


function closeForm() {
    self.close();
}

//function addProcess() {
//  var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=2&selectedDepth=2&codetype=PROCESSDIVISIONCODE&command=select&mode=multi&invokeMethod=selectFromProject";
//  openWindow(url, "addProcess", 860, 600);
//}

function deleteProcess(){
    document.forms[0].command.value="removeProcess";
    document.forms[0].submit();
}

function selectFromProject(arrObj){
    var addparts = "";
    for(var i=0; i < arrObj.length; i++){
        addparts += "<input type=hidden name=processoid value='"+arrObj[i][0]+"'>";
    }
    document.all.addProcess.innerHTML = addparts;
    document.forms[0].submit();
}




    function addProcess(type, depth) {
        var form = document.forms[0];

        var tmpType = "";
        if(type==("divisioncode")) {
            tmpType = "DIVISIONCODE";
        } else if(type==("levelcode")) {
            tmpType = "LEVELCODE";
        } else if(type==("productcode")) {
            tmpType = "PRODUCTCODE";
        } else if(type==("customercode")) {
            tmpType = "CUSTOMERCODE";
        } else if(type==("devcompanycode")) {
            tmpType = "DEVCOMPANYCODE";
        } else if(type==("makecompanycode")) {
            tmpType = "MAKECOMPANYCODE";
        } else if(type==("modelcode")) {
            tmpType = "MODELCODE";
        }

        var mode = "one";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=3&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        acceptProcess(returnValue, type);
    }

    function acceptProcess(arrObj, type){
        var tmpType = "";
        var tmpType1 = "";

        if(type==("divisioncode")) {
            tmpType = "divisioncodetable";
            tmpType1 = "divisioncodeoid";
        } else if(type==("levelcode")) {
            tmpType = "levelcodetable";
            tmpType1 = "levelcodeoid";
        } else if(type==("productcode")) {
            tmpType = "productcodetable";
            tmpType1 = "productcodeoid";
        } else if(type==("customercode")) {
            tmpType = "customercodetable";
            tmpType1 = "customercodeoid";
        } else if(type==("devcompanycode")) {
            tmpType = "devcompanycodetable";
            tmpType1 = "devcompanycodeoid";
        } else if(type==("makecompanycode")) {
            tmpType = "makecompanycodetable";
            tmpType1 = "makecompanycodeoid";
        } else if(type==("modelcode")) {
            tmpType = "modelcodetable";
            tmpType1 = "modelcodeoid";
        }

        var subArr;
        var form = document.forms[0];

            for(var i = 0; i < arrObj.length; i++) {
                subArr = arrObj[i];

                if(type==("divisioncode")) {
                    form.divisioncode.value = subArr[1];
                    form.divisioncode.name =subArr[2];
                } else if(type==("levelcode")) {
                    form.levelcode.value = subArr[1];
                    form.levelcode.name =subArr[2];
                } else if(type==("productcode")) {
                    form.productcode.value =subArr[1];
                    form.productcode.name =subArr[2];
                } else if(type==("customercode")) {
                    form.customercode.value =subArr[1];
                    form.customercode.name =subArr[2];
                } else if(type==("devcompanycode")) {
                    form.devcompanycode.value =subArr[1];
                    form.devcompanycode.name =subArr[2];
                } else if(type==("makecompanycode")) {
                    form.makecompanycode.value =subArr[1];
                    form.makecompanycode.name =subArr[2];
                } else if(type==("modelcode")) {
                    form.modelcode.value =subArr[1];
                    form.modelcode.name =subArr[2];
                }
            }
    }


</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:5px;

    overflow-x:hidden;
    overflow-y:auto;
    scrollbar-highlight-color:#f4f6fb;
    scrollbar-3dlight-color:#c7d0e6;
    scrollbar-face-color:#f4f6fb;
    scrollbar-shadow-color:#f4f6fb;
    scrollbar-darkshadow-color:#c7d0e6;
    scrollbar-track-color:#f4f6fb;
    scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>

<!-- Hidden Values -->
<%String command = request.getParameter("command");%>
<input type="hidden" name="command">
<input type="hidden" name="mode" value="plmUpdate">
<input type="hidden" name="oid" value="<%=StringUtil.checkNull(oid)%>">

<!-- //Hidden Vlaues -->

<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03052") %><%--프로젝트 PLM정보 수정--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03052") %><%--프로젝트 PLM정보 수정--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <a href="javascript:update();">
                        <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
                        </a>
                        &nbsp;&nbsp;
                        <a href="javascript:closeForm();">
                        <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                        </a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm5"></td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td class="tab_btm1"></td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" align=center width="100%">

    <tr>
        <td width="15%" class="tdblueL" >&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
        <td width="35%" class="tdwhiteL">&nbsp;<%=StringUtil.checkNull(valuePN)%>

        <a href="javascript:numberUpdate();">
            <img src="/plm/portal/images/img_default/button/board_btn_modify.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" width="62" height="20" border="0">
        </a>

        </td>
        <td width="15%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
        <td width="35%" class="tdwhiteL"><input type=text name="projectName" value="<%=StringUtil.checkNull(valuePName)%>" style="width=80%"></td>
    </tr>
    <tr>
        <td width="15%" class="tdblueL" >&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%> <span class="style1"> *</span></td>
        <td width="35%" class="tdwhiteL">
                        <%

                            if(projectType.equals("2")) {


                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("DIVISIONCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);

                                    if(tNum.getParent() != null){
                                        if("견적".equals(tNum.getParent().getName())){
                                            if(valuePDivisionCode != null){
                                                if(valuePDivisionCode.getCode().equals(tNum.getCode())) selected = "selected";
                                                else selected = "";
                                            }
                                    %>
                                        <input type=hidden name="divisioncode" value="<%=tNum.getCode()%>">
                                        <input type=text name="divisioncodeName" value="<%=tNum.getName()%>"  readOnly>
                                    <%
                                        }
                                    }
                                }
                            }else if(projectType.equals("0")){


                            %>
                                <select name="divisioncode">
                                <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("DIVISIONCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(tNum.getParent() != null){
                                        //if("선행개발".equals(tNum.getParent().getName())){
                                            if(valuePDivisionCode != null){
                                                if(valuePDivisionCode.getCode().equals(tNum.getCode())) selected = "selected";
                                                else selected = "";
                                            }

                                    %>
                                        <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                                    <%
                                        //}
                                    }
                                }

                            %>
                                </select>


                            <%
                            }else{
                            %>


                                <select name="divisioncode">
                                <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("DIVISIONCODE", "child");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(tNum.getParent() != null){
                                        if("양산개발".equals(tNum.getParent().getName())){
                                            if(valuePDivisionCode != null){
                                                if(valuePDivisionCode.getCode().equals(tNum.getCode())) selected = "selected";
                                                else selected = "";
                                            }



                                    %>
                                        <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                                    <%
                                        }
                                    }
                                }

                            %>
                                </select>


                            <%} %>



        </td>
        <td width="15%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01162") %><%--난이도--%>
 <span class="style1"> *</span></td>
        <td width="35%" class="tdwhiteL">
                        <select name="levelcode">
                            <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("LEVELCODE", "parent");
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(valuePLevelCode != null){
                                    if(valuePLevelCode.getCode().equals(tNum.getCode())) selected = "selected";
                                    else selected = "";
                                    }
                            %>
                                <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                            <%
                            }
                            %>
                        </select>
                        &nbsp;&nbsp;
                        <a href="javascript:addProcess('levelcode', 1);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>

        </td>
    </tr>
    <tr>
        <td width="10%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%><span class="style1"> *</span></td>
        <td width="35%" class="tdwhiteL">
                        <select name="productcode">
                            <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("PRODUCTCODE", "child");

                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(valuePProductCode != null){
                                    if(valuePProductCode.getCode().equals(tNum.getCode())) selected = "selected";
                                    else selected = "";
                                    }

                            %>
                                <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>


                                ><%=tNum.getName()%></option>
                            <%
                            }
                            %>

                        </select>
                        &nbsp;&nbsp;
                        <a href="javascript:addProcess('productcode', 2);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>
        </td>
        <td width="10%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%> <% if(!(projectType.equals("2")) ) {%><span class="style1"> *</span><%} %></td>
        <td width="35%" class="tdwhiteL">
                        <select name="customercode" style="width=70%">

                            <option style="background-color:#EEE8AA;" value="" >[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("CUSTOMERCODE", "child");
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(valuePCustomerCode != null){
                                    if(valuePCustomerCode.getCode().equals(tNum.getCode())) selected = "selected";
                                    else selected = "";
                                    }
                            %>
                                <option style="background-color:#EEE8AA;" title="<%=tNum.getName()%>" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                            <%
                            }
                            %>

                        </select>
                        &nbsp;&nbsp;
                        <a href="javascript:addProcess('customercode', 2);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>
        </td>
    </tr>
    <tr>
        <td width="10%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%> <span class="style1"> *</span></td>
        <td width="35%" class="tdwhiteL">
                        <% if(projectType.equals("0")&&false) {%>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("DEVCOMPANYCODE", "child");
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                //if("선행개발팀".equals(tNum.getName())){
                            %>
                        <input type=hidden name="devcompanycode" value="<%=tNum.getCode()%>">
                        <input type=text name="devcompanycodeName" value="<%=tNum.getName()%>"  readOnly>
                            <%  //}
                            }
                            %>
                        <%}else{ %>
                        <select name="devcompanycode">
                            <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("DEVCOMPANYCODE", "child");
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                    if(valuePDevCompanyCode != null){
                                    if(valuePDevCompanyCode.getCode().equals(tNum.getCode())) selected = "selected";
                                    else selected = "";
                                    }
                            %>
                                <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                            <%
                            }
                            %>
                        </select>
                        &nbsp;&nbsp;
                        <a href="javascript:addProcess('devcompanycode', 2);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>
                        <%} %>
        </td>
        <td width="10%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%><% if(!projectType.equals("0")) {%><span class="style1"> *</span><%} %></td>
        <td width="35%" class="tdwhiteL">
                        <select name="makecompanycode">
                            <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                            <%
                            tMap = NumberCodeHelper.manager.getNumberCodeLevel("MAKECOMPANYCODE", "child");
                            for(int i = 0; i < tMap.size(); i++) {
                                NumberCode tNum = (NumberCode)tMap.get(i);
                                if(valuePMakeCompanyCode != null){
                                if(valuePMakeCompanyCode.getCode().equals(tNum.getCode())) selected = "selected";
                                else selected = "";
                                }
                            %>
                                <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                            <%
                            }
                            %>
                        </select>
                        &nbsp;&nbsp;
                        <a href="javascript:addProcess('makecompanycode', 2);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a>

        </td>
    </tr>
    <tr>
        <td width="15%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%><%if(!(projectType.equals("2")) ){ %><span class="style1"> *</span><%} %></td>
        <td width="35%" class="tdwhiteL0" colspan=3>

                        <%if(/*pm==null ||*/ true){ %>
                            <select name="modelcode">
                                <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                                <%
                                tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "parent");
                                for(int i = 0; i < tMap.size(); i++) {
                                    NumberCode tNum = (NumberCode)tMap.get(i);
                                        if(valuePModelCode != null){
                                        if(valuePModelCode.getCode().equals(tNum.getCode())) selected = "selected";
                                        else selected = "";
                                        }

                                    if(projectType.equals("0")){

                                    %>
                                        <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>
                                    <%
                                    }else{


                                    %>
                                        <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" <%=selected%>><%=tNum.getName()%></option>

                                    <%
                                    }
                                }
                                %>
                            </select>


                        <%}else{ %>
                        <input type=hidden name="modelcode" value="<%//=pm.getModel().getCode()%>">
                        <input type=text name="projectTypeName" value="<%//=pm.getModel().getName()%>"  readOnly>

                        <%} %>

                        <%if(projectType.equals("2") || projectType.equals("0") || true){ %>
                        &nbsp;&nbsp;<a href="javascript:addProcess('modelcode', 2);">
                        <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                        </a >
                        <%} %>

        </td>
        <!--td width="15%" class="tdblueL">&nbsp;프로젝트 종류</td>
        <td width="35%" class="tdwhiteL0">&nbsp;
            <%
            //if("1".equals(pjtType)){
            %>
                <%//=conf.getString("produceproject.value")%><input type=hidden name=projectType value="1">
            <%
            //}else if("2".equals(pjtType)){
            %>
                <%//=conf.getString("devproject.value") %><input type=hidden name=projectType value="2">
            <%
            //}
            //String[] key = {"1", "2"};
            //String[] value = {"양산", "개발"};
            //out.print(HtmlTagUtil.selectTag("projectType", pjtType, key, value, "style=width:200"));
            %>
        </td-->
    </tr>

    <!-- ETC Attribute -->
    <!-- tr>
        <td bgcolor=#EBEFF3 id=textblue>&nbsp;계획 시작일자<span class="style1"> *</span></td>
        <td bgcolor="ffffff">&nbsp;<input name="planStartDate" id=i size=20 maxlength=20 readonly onclick="javascript:openCal('planStartDate');" value="<%//=valuePPlanStartDate%>">&nbsp;&nbsp;<a href="javascript:openCal('planStartDate')" value="<%//=valuePPlanStartDate%>"><img src="/plm/portal/icon/icon_cal.gif" border=0 onfocus="this.blur();" ></a></td>
        <td bgcolor=#EBEFF3 id=textblue>&nbsp;계획 종료일자<span class="style1"> *</span></td>
        <td bgcolor="ffffff">&nbsp;<input name="planEndDate" id=i size=20 maxlength=20 readonly onclick="javascript:openCal('planEndDate');" value="<%//=valuePPlanEndDate%>">&nbsp;&nbsp;<a href="javascript:openCal('planEndDate')" value="<%//=valuePPlanEndDate%>"><img src="/plm/portal/icon/icon_cal.gif" border=0 onfocus="this.blur();" ></a></td>
        <td bgcolor=#EBEFF3 id=textblue>&nbsp;총 기간<span class="style1"> *</span></td>
        <td bgcolor="ffffff">&nbsp;<input name="projectDuration" id=i style="width:69%" onkeyup ="SetNum(this)" style='IME-MODE: disabled'></td>
    </tr-->
    <!-- //ETC Attribute -->
    <!-- 설명 -->
    <tr>
        <td width="15%" class="tdblueL">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
        <td width="85%" class="tdwhiteL0" align="center" colspan="5">
            <textarea name="projectDesc" rows=5 style="width:99%" id=i><%=projectData.pjtDesc%></textarea>
        </td>
    </tr>
    <!-- //설명 -->
</table>
</form>
</body>
</html>
