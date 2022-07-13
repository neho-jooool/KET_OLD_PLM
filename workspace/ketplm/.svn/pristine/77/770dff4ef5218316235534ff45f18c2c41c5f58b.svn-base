<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.lifecycle.*,wt.org.*,
                wt.query.*,wt.session.*,wt.util.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*,
                e3ps.common.util.*,e3ps.common.code.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
    HashMap map = new HashMap();

    String command = request.getParameter("command");
    String isSelect = request.getParameter("isSelect");
    String mode = request.getParameter("mode");

    // 검색
    String pjtNo = request.getParameter("pjtNo"); //pjtNo(프로젝트 NO)
    String pjtName = request.getParameter("pjtName"); //pjtName(프로젝트 명)
    String pjtType = request.getParameter("pjtType"); //pjtType(프로젝트종류)

    String modelcode = request.getParameter("modelcode");
    String divisioncode = request.getParameter("divisioncode");
    String levelcode = request.getParameter("levelcode");
    String productcode = request.getParameter("productcode");
    String customercode = request.getParameter("customercode");
    String devcompanycode = request.getParameter("devcompanycode");
    String makecompanycode = request.getParameter("makecompanycode");

    String planStartStartDate = request
            .getParameter("planStartStartDate"); //planStartStartDate(계획 시작일자 - 시작)
    String planStartEndDate = request.getParameter("planStartEndDate"); //planStartEndDate(계획 시작일자 - 끝)

    String planEndStartDate = request.getParameter("planEndStartDate"); //planEndStartDate(계획 종료일자 - 시작)
    String planEndEndDate = request.getParameter("planEndEndDate"); //planEndEndDate(계획 종료일자 - 끝)

    if (command != null && command.length() > 0) {
        map.put("command", command);
    } else {
        command = "";
    }
    if (isSelect != null && isSelect.length() > 0) {
        map.put("isSelect", isSelect);
    }
    if (mode != null && mode.length() > 0) {
        map.put("mode", mode);
    } else {
        mode = "";
    }

    ////////////////////////////////////////////////////////////////
    // 검색
    ////////////////////////////////////////////////////////////////

    if (pjtNo != null && pjtNo.length() > 0) {
        map.put("pjtNo", pjtNo);
    }
    if (pjtName != null && pjtName.length() > 0) {
        map.put("pjtName", pjtName);
    }
    if (pjtType != null && pjtType.length() > 0) {
        map.put("pjtType", pjtType);
    }

    if (modelcode != null && modelcode.length() > 0) {
        map.put("modelcode", modelcode);
    }

    if (divisioncode != null && divisioncode.length() > 0) {
        map.put("divisioncode", divisioncode);
    }

    if (levelcode != null && levelcode.length() > 0) {
        map.put("levelcode", levelcode);
    }

    if (productcode != null && productcode.length() > 0) {
        map.put("productcode", productcode);
    }

    if (customercode != null && customercode.length() > 0) {
        map.put("customercode", customercode);
    }

    if (devcompanycode != null && devcompanycode.length() > 0) {
        map.put("devcompanycode", devcompanycode);
    }

    if (makecompanycode != null && makecompanycode.length() > 0) {
        map.put("makecompanycode", makecompanycode);
    }


    if (planStartStartDate != null && planStartStartDate.length() > 0) {
        map.put("planStartStartDate", planStartStartDate);
    }
    if (planStartEndDate != null && planStartEndDate.length() > 0) {
        map.put("planStartEndDate", planStartEndDate);
    }
    if (planEndStartDate != null && planEndStartDate.length() > 0) {
        map.put("planEndStartDate", planEndStartDate);
    }
    if (planEndEndDate != null && planEndEndDate.length() > 0) {
        map.put("planEndEndDate", planEndEndDate);
    }


    String[] stateArr = request.getParameterValues("pjtState");
    if(stateArr != null) {
        map.put("pjtState", stateArr);
    }

    map.put("checkAccess", "false");


    int psize = 15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    //page
    String sessionidstring = request.getParameter("sessionid");
    if (sessionidstring == null)
        sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring);
    String pagestring = request.getParameter("tpage");
    if (pagestring != null && pagestring.length() > 0) {
        cpage = Integer.parseInt(pagestring);
    }

    PagingQueryResult result = null;
    if (sessionid <= 0) {
        try {
            result = ProjectQueryUtil.openPagingSession(map, 0,psize);
        }
        catch(Exception e) {
            Kogger.error(e);
        }
    } else {
        result = PagingSessionHelper.fetchPagingSession((cpage - 1) * psize, psize, sessionid);
    }

    if (result != null) {
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }
%>
<html>
<head>
<title>Project Search Result</title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<script language=JavaScript>
<!--
function gotoPage(p){
    document.forms[0].command.value='search';
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}


function checkAll() {
    form = document.forms[0];
    if(form.oid) {
        var chkLen = form.oid.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                form.oid[i].checked = form.chkAll.checked;
            }
        }else{
            form.oid.checked = form.chkAll.checked;
        }
    }
}

function checkedCheck() {
    form = document.forms[0];
    if(form.oid) {
        var chkLen = form.oid.length;
        if(chkLen) {
            for(var i = 0; i < chkLen; i++) {
                if(form.oid[i].checked == true) {
                    return true;
                }
            }
        }else{
            if(form.oid.checked == true) {
                return true;
            }
        }
    }
    return false;
}


function checkList() {
    form = document.forms[0];

    var s_result = new Array();
    if(form.oid==null){
        return s_result;
    }

    var count = 0;
    if(form.oid.length==null){
        if(form.oid.checked){
            oArrayData = getSeledData(form.oid.value);
            if(oArrayData != false) {
                s_result[count++] = oArrayData;
            }
            //i_result = new Array();
            //i_result[0] = form.oid.value;
            //s_result[count++] = i_result;
        }

        return s_result;

    } else {
        for(var i=0; i< form.oid.length; i++){
            if(form.oid[i].checked){
                oArrayData = getSeledData(form.oid[i].value);
                if(oArrayData != false) {
                    s_result[count++] = oArrayData;
                }
                //i_result = new Array();
                //i_result[0] = form.oid[i].value;
                //s_result[count++] = i_result;
            }
        }

        return s_result;
    }

    return s_result;
}

var lDataArray=new Array();
function getSeledData(oOid) {
    for(var i=0; i< lDataArray.length; i++){
        if(lDataArray[i][0]==oOid){
            return lDataArray[i];
        }
    }
    return false;
}
//-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"  marginwidth="0" marginheight="0">
<form>
<!-- Hidden Value -->
<input type='hidden' name="command" value="<%=command%>">
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>

<input type="hidden" name="mode" value="<%=mode%>">
<!-- //Hidden Value -->

        <!------------------------------ 본문 시작 //------------------------------>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td  class="tab_btm1"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <col width="3%">  <!-- checkbox -->
        <col width="10%">  <!-- 프로젝트번호 -->
        <col width="*">    <!-- 프로젝트명 -->
        <col width="10%">  <!-- 종류 -->
        <col width="10%">  <!-- 차종/모델 -->
        <col width="10%">  <!-- 발주처 -->
        <col width="10%">  <!-- 개발조직 -->
        <col width="10%">  <!-- 생산조직 -->
        <col width="10%">  <!-- 상태 -->
        <col width="10%">  <!-- PM -->
            <tr>
                <td class="tdblueM">
                    <%if("multi".equals(mode)) {%><input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();"><%}else{%>&nbsp;<%}%>
                </td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02661") %><%--종류--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02748") %><%--차종/모델--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01474") %><%--발주처--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01788") %><%--생산조직--%></td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td class="tdblueM0">PM</td>
            </tr>
            <%
                if((result == null) || (result.getTotalSize() == 0) ) {
            %>
                    <tr>
                        <td class='tdwhiteM0' align='center' colspan='10'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                    </tr>
            <%
                }
                else {
                    int lcount=0;
                    E3PSProjectData data = null;
                    while (result.hasMoreElements()) {
                        Object[] obj = (Object[]) result.nextElement();
                        data = new E3PSProjectData((E3PSProject) obj[0]);
            %>
                        <script language="javascript">
                            var dataArray=Array();
                            dataArray[0]="<%=data.jelPjtOID%>";
                            dataArray[1]="<%=data.pjtNo%>";
                            dataArray[2]="<%=data.pjtName%>";
                            dataArray[3]="<%=data.pjtTypeName%>";
                            dataArray[4]="<%=data.E3PSProject.getModel()==null? "":StringUtil.checkNull(data.E3PSProject.getModel().getName())%>";
                            dataArray[5]="<%=data.E3PSProject.getCustomer()==null? "":StringUtil.checkNull(data.E3PSProject.getCustomer().getName())%>";
                            dataArray[6]="<%=data.E3PSProject.getDevcompany()==null?"":StringUtil.checkNull(data.E3PSProject.getDevcompany().getName())%>";
                            dataArray[7]="<%=data.E3PSProject.getMakecompany()==null?"":StringUtil.checkNull(data.E3PSProject.getMakecompany().getName())%>";
                            dataArray[8]="<%=data.E3PSProject.getLifeCycleState().getDisplay()%>";
                            dataArray[9]="<%=data.pjtPmName%>";

                            dataArray[10]="<%=data.E3PSProject.getModel()==null? "":StringUtil.checkNull(data.E3PSProject.getModel().getCode())%>";
                            dataArray[11]="<%=data.E3PSProject.getLevel()==null? "":StringUtil.checkNull(data.E3PSProject.getLevel().getCode())%>";
                            dataArray[12]="<%=data.E3PSProject.getCustomer()==null?"": StringUtil.checkNull(data.E3PSProject.getCustomer().getCode())%>"
                            dataArray[13]="<%=data.E3PSProject.getMakecompany()==null?"":StringUtil.checkNull(data.E3PSProject.getMakecompany().getCode())%>";
                            dataArray[14]="<%=data.E3PSProject.getProduct() == null?"":StringUtil.checkNull(data.E3PSProject.getProduct().getCode())%>"

                            lDataArray[<%=lcount++%>]=dataArray;
                        </script>
                        <tr>
                            <td class="tdwhiteM">
                                <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%> value='<%=data.jelPjtOID%>'/>
                            </td>
                            <td class="tdwhiteL">&nbsp;<%=data.pjtNo%></td>
                            <td class="tdwhiteL" title='<%=data.pjtName%>'>
                                <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                    <nobr>&nbsp;<%=data.pjtName%></nobr>
                                </div>
                            </td>
                            <td class="tdwhiteM">
                                <%=data.pjtTypeName%>
                            </td>
                            <td class="tdwhiteM">
                                <%=data.E3PSProject.getModel()==null? "&nbsp;":StringUtil.checkNull(data.E3PSProject.getModel().getName())%>
                            </td>
                            <td class="tdwhiteM" nowrap>
                                <%=data.E3PSProject.getCustomer()==null? "&nbsp;":StringUtil.checkNull(data.E3PSProject.getCustomer().getName())%>
                            </td>
                            <td class="tdwhiteM">
                                <%=data.E3PSProject.getDevcompany()==null? "&nbsp;":StringUtil.checkNull(data.E3PSProject.getDevcompany().getName())%>
                            </td>
                            <td class="tdwhiteM">
                                <%=data.E3PSProject.getMakecompany()==null? "&nbsp;":StringUtil.checkNull(data.E3PSProject.getMakecompany().getName())%>
                            </td>
                            <td class="tdwhiteM">
                                <%=data.E3PSProject.getLifeCycleState().getDisplay(messageService.getLocale())%>
                            </td>
                            <td class="tdwhiteM0">
                                <%=data.pjtPmName%>
                            </td>
                        </tr>
            <%
                    }
                }
            %>
        </table>
        <%
            int ksize = total / psize;
            int x = total % psize;
            if (x > 0)
                ksize++;
            int temp = cpage / pageCount;
            if ((cpage % pageCount) > 0)
                temp++;
            int start = (temp - 1) * pageCount + 1;

            int end = start + pageCount - 1;
            if (end > ksize) {
                end = ksize;
            }
        %>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
                <td>
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40" align="center">
                        <%
                        if (start > 1) {
                        %><a href="JavaScript:gotoPage(1)" class="small"><%=messageService.getString("e3ps.message.ket_message", "02792") %><%--처음--%></a>
                        <%
                        }
                        %>
                        </td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                        if (start > 1) {
                        %>
                        <td width="60" class="quick" align="center"><a
                            href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%
                            }
                            for (int i = start; i <= end; i++) {
                        %>
                        <td style="padding:2 8 0 7;cursor:hand"
                            onMouseOver="this.style.background='#ECECEC'"
                            OnMouseOut="this.style.background=''" class="nav_on">
                        <%
                        if (i == cpage) {
                        %><b><%=i%>
                        <%
                        } else {
                        %><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
                        <%
                        }
                        %>
                        </td>
                        <%
                            }
                            if (end < ksize) {
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="60" align="center"><a
                            href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><%=messageService.getString("e3ps.message.ket_message", "01186") %><%--다음--%></a></td>
                        <%
                        }
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="45" align="center">
                        <%
                        if (end < ksize) {
                        %><a href="JavaScript:gotoPage(<%=ksize%>)"
                            class="small"><%=messageService.getString("e3ps.message.ket_message", "01354") %><%--마지막--%></a>
                        <%
                        }
                        %>
                        </td>
                    </tr>
                </table>
                </td>
                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
            </tr>
        </table>
        <!------------------------------ 본문 끝 //------------------------------>
<!-- 본문외관테두리 끝 //--></form>
</body>
</html>
