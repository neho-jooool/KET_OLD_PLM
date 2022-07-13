<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="wt.fc.*" %>
<%@page import="wt.query.*" %>
<%@page import ="e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*,e3ps.common.web.ParamUtil,wt.introspection.BaseTableInfo,wt.introspection.WTIntrospector,e3ps.project.TemplateProject"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
    String command = request.getParameter("command");
    String mode = request.getParameter("mode");
    boolean isPurchase = Boolean.parseBoolean(request.getParameter("isPurchase"));
    
    String name = request.getParameter("name");
    String code = request.getParameter("code");
    String ptType = request.getParameter("ptType");
    if(ptType == null) ptType = "";
    String divisionValue = ParamUtil.getStrParameter(request.getParameter("division"));
    String wbsTypeValue = ParamUtil.getStrParameter(request.getParameter("wbsType"));
    
    String wType = ParamUtil.getStrParameter(request.getParameter("wType"));
    String selectReview = ParamUtil.getStrParameter(request.getParameter("selectReview"));
    String sessionidstring = request.getParameter("sessionid");
    String pagestring = request.getParameter("tpage");
    String type = request.getParameter("type");
    String makeType = request.getParameter("makeType");
    String moldType = request.getParameter("moldType");
    if(command == null)
        command = "";
    if(mode == null)
        mode = "";
    if(name == null)
        name = "";
    if(code == null)
        code = "";

    if(type == null){
        type = "";
    }
    if(makeType == null){
        makeType = "";
    }
    if(moldType == null){
	   moldType = "";   
    }
    if(sessionidstring == null)
        sessionidstring = "0";

    if(pagestring == null)
        pagestring = "";

    int psize = 10;//15;
    int cpage = 1;
    int total = 0;
    int pageCount = 10;

    long sessionid = Long.parseLong(sessionidstring);
    if(pagestring.length()>0)
        cpage = Integer.parseInt(pagestring);

    PagingQueryResult result = null;

    if(sessionid <= 0){
        QuerySpec qs = null;
        CompoundQuerySpec query = null;

        Map<String,Object> divisionMap = new HashMap<String,Object>();
        Hashtable hash = new Hashtable();
        hash.put("name", name);
        if(moldType != null){
            Map<String,Object> activeType = new HashMap<String,Object>();
            activeType.put("type", moldType.toLowerCase());
        }
        Map<String,Object> activeType = new HashMap<String,Object>();
        activeType.put("activeType","활성");
        hash.put("activeType", activeType);
        
        if(isPurchase) hash.put("moldType", "purchase");
        
        if(divisionValue.length() > 0){
            if("자동차 사업부".equals(divisionValue)) {
                if(wbsTypeValue.length() > 0) {
              	    hash.put("projectType", "3");
                    qs = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());
                }else {
                    hash.put("projectType", "3");
                    QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());
                    query = new CompoundQuerySpec();
                    query.setSetOperator(SetOperator.UNION_ALL);
                    query.addComponent(queryC);
                }
                divisionMap.put("division", "자동차사업부");

            }else if("전자 사업부".equals(divisionValue)) {
                hash.put("projectType", "4");
                QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());
                query = new CompoundQuerySpec();
                query.setSetOperator(SetOperator.UNION_ALL);
                query.addComponent(queryC);
                divisionMap.put("division", "전자사업부");
            }else if("KETS".equals(divisionValue)) {
                hash.put("projectType", "6");
                QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());
                query = new CompoundQuerySpec();
                query.setSetOperator(SetOperator.UNION_ALL);
                query.addComponent(queryC);
                divisionMap.put("division", "KETS");
            }

        }else {
            QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());

            query = new CompoundQuerySpec();
            query.setSetOperator(SetOperator.UNION_ALL);
            query.addComponent(queryC);
        }
        
        if(wType.equals("1")){
            hash.put("projectType", selectReview);
        }
        hash.put("division",divisionMap);
        
        Kogger.debug("wType >> " + wType);
        hash.put("activeType", activeType);
        qs = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, new Vector());
        Kogger.debug("qs >> " + qs);
        if(qs != null){
        Kogger.debug("query1 >> " + query);
            result = PagingSessionHelper.openPagingSession(0, psize, qs);
        }else {
        Kogger.debug("query2 >> " + query);
            result = PagingSessionHelper.openPagingSession(0, psize, query);
        }
    }
    else {
        result = PagingSessionHelper.fetchPagingSession((cpage-1) * psize, psize, sessionid);
    }

    if(result != null) {
        total = result.getTotalSize();
        sessionid = result.getSessionId();
    }

%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language="javascript">
<!--
function gotoPage(p){
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}

function viewProject(oid) {
    //parent.parent.document.location.href = "/plm/jsp/project/template/TemplateProjectViewFrm.jsp?oid="+oid;
    var url = "/plm/jsp/project/template/TemplateProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    openSameName(url,"1200","900","status=no,scrollbars=yes,resizable=no");
}

function tmpCopy(tmpid) {
    if(parent.tmpRegister) {
        parent.tmpRegister(tmpid);
    }
}

function onMoveEditPage(tmpoid) {
    var url = "/plm/jsp/project/template/ProjectTempEditFrame.jsp?oid=" + tmpoid;
    newPopUpPage(url, 'EditPage','1220','800','yes', 'yes');
}

var win = null;
function newPopUpPage(pageUrl,pageName,pageWidth,pageHeight,scroll, resize) {
    leftPosition = (screen.width) ? (screen.width - pageWidth)/2 : 0;
    topPosition = (screen.height) ? (screen.height - pageHeight)/2 : 0;
    pageOption = 'toolbar=no,menubar=no,status=no,titlebar=no';
    pageOption += ',height=' + pageHeight;
    pageOption += ',width=' + pageWidth;
    pageOption += ',top=' + topPosition;
    pageOption += ',left=' + leftPosition;
    pageOption += ',scrollbars=' + scroll;
    pageOption += ',resizable=' + resize;
    win = window.open(pageUrl,pageName,pageOption);
    win.focus();
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

function isCheckedCheckBox() {
    form = document.forms[0];
    if(form.oid == null) {
        return false;
    }

    len = form.oid.length;
    if(len) {
        for(var i = 0; i < len;i++) {
            if(form.oid[i].checked == true) {
                return true;
            }
        }
    }
    else {
        if(form.oid.checked == true) {
            return true;
        }
    }

    return false;

}

function checkList() {
    form = document.forms[0];

    var arr = new Array();
    var subarr = new Array();//0:oid, 1:name, 2:no, 3:duration, 4:createDate, 5:modifyDate

    if(!isCheckedCheckBox()) {
        return arr;
    }

    len = form.oid.length;

    var idx = 0;
    if(len) {
        for(var i = 0; i < len; i++) {
            if(form.oid[i].checked == true) {
                subarr = new Array();
                subarr[0] = form.oid[i].value;//oid
                subarr[1] = form.oid[i].tempName;//name
                subarr[2] = form.oid[i].tempNo;//no
                subarr[3] = form.oid[i].duration;//duration
                subarr[4] = form.oid[i].createDate;//createDate
                subarr[5] = form.oid[i].modifyDate;//modifyDate

                arr[idx++] = subarr;
            }
        }
    } else {
        if(form.oid.checked == true) {
            subarr = new Array();
            subarr[0] = form.oid.value;//oid
            subarr[1] = form.oid.tempName;//name
            subarr[2] = form.oid.tempNo;//no
            subarr[3] = form.oid.duration;//duration
            subarr[4] = form.oid.createDate;//createDate
            subarr[5] = form.oid.modifyDate;//modifyDate
            arr[idx++] = subarr;
        }
    }


    return arr;
}


<!-- 2014.07.09 WBS Name 클릭시 조회팝업 -->
function searchPopup(taskUrl, wbsContentUrl){
    var type = "mold";
    openOtherName(encodeURI(wbsContentUrl+"&taskUrl="+taskUrl+"&popup=yes&type="+type), "StandardWBSSearch", "1335", "768", "status=no,scrollbars=yes,resizable=yes");
    //openOtherName(a, "process", "950", "550", "status=no,scrollbars=yes,resizable=no");
}
// -->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form>
<!-- hidden begin -->
<input type='hidden' name='sessionid'>
<input type='hidden' name='tpage'>
<input type='hidden' name='command' value="<%=command%>">
<input type='hidden' name='mode' value="<%=mode%>">
<input type='hidden' name='name' value="<%=name%>">
<input type='hidden' name='code' value="<%=code%>">
<input type='hidden' name='type' value="<%=type%>">
<input type='hidden' name='makeType' value="<%=makeType%>">
<!-- hidden end -->

<!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueM" width="50">
<%
    if("select".equals(command)) {
        if("multi".equals(mode)) {
%>
                        <input name="chkAll" type="checkbox" class="Checkbox" onclick="javascript:checkAll();">
<%
        }
        else {
            out.println("&nbsp;");
        }
    }
    else {
        out.println("NO");
    }
%>
                    </td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00555") %><%--WBS 명--%></td>
                    <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "02105") %><%--업무기간--%></td>
                    <%if(!isPurchase){ %>
                    <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
                    <%} %>
                    <td class="tdblueM"  width="100"><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                    <td class="tdblueM"  width="100"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                    <%if(mode.equals("")){ %>
                    <td class="tdblueM0" width="50">&nbsp;</td>
                    <%} %>
                </tr>
<%
    if(result == null || result.size() == 0 )
    {

%>
        <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'" onMouseout="this.style.backgroundColor='#ffffff'">
            <TD align=center colspan='8' class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></TD>
        </TR>

        <%
            }
            else {
                int count = (psize*(cpage-1)) + 1;
                while ( result.hasMoreElements() ) {
                    Object[] obj = (Object[])result.nextElement();
                    TemplateProject template = null;
                    if(obj[0] instanceof String) {
                        template = (TemplateProject)CommonUtil.getObject((String)obj[0]);
                    }else {
                        template = (TemplateProject)obj[0];
                    }
                    String moldTypeName = "";
                    if(moldTypeName != null && "press".equals(template.getMoldType())){
                	   moldTypeName = "Press";
                    }else if(moldTypeName != null && "mold".equals(template.getMoldType())){
                	   moldTypeName = "Mold";
                    }
                    
        //      for(int i=0; i<obj.length-1; i++) {
                        TemplateProjectData data = new TemplateProjectData(template);
        %>
                    <tr>
                        <td class="tdwhiteM">
        <%
        if("select".equals(command)) {
        %>
                            <input name="oid" type="checkbox" class="Checkbox" <%if(!"multi".equals(mode)){%>onClick="oneClick(this)"<%}%>
                                    value='<%=data.tempProjectOID%>'
                                    tempName='<%=data.tempName%>'
                                    tempNo='<%=template.getPjtNo()%>'
                                    duration='<%=data.tempDuration%>'
                                    createDate='<%=DateUtil.getDateString(data.tempCreateDate,"D")%>'
                                    modifyDate='<%=DateUtil.getDateString(data.tempModifyDate,"D")%>'/>
        <%
            }
            else {
                out.println(count++);
            }
        
        %>
                        </td>
                        <td class="tdwhiteM text-left">
                            <div style="width:300;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                                <nobr>&nbsp;<a href="javascript:searchPopup('/plm/servlet/e3ps/ProjectScheduleServlet?oid=<%=data.tempProjectOID%>','/plm/jsp/project/template/TemplateProjectView.jsp?oid=<%=data.tempProjectOID%>')"><%=data.tempName%></a></nobr>
                            </div>
                        </td>
                        <td class="tdwhiteM">&nbsp;<%=data.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                        <%if(!isPurchase){ %>
                        <td class="tdwhiteM">&nbsp;<%=moldTypeName%></td>
                        <%} %>
                        <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(data.tempCreateDate,"D")%></td>
                        <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(data.tempModifyDate,"D")%></td>
                        <%if(mode.equals("")){ %>
                        <td class="tdwhiteM0">
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:tmpCopy('<%=data.tempProjectOID%>');" class="btn_blue">Copy</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                              </tr>
                            </table>
                            <!--
                            <a href="javascript:tmpCopy('<%=data.tempProjectOID%>');">
                            <img src="/plm/portal/images/img_default/button/board_btn_copy.gif" alt="COPY" width="62" height="20" border="0">
                            </a>-->
                            <!--input type='button' value='Copy' onClick="javascript:tmpCopy('<%//=data.tempProjectOID%>');" class="s_font"-->
                        </td>
                        <%} %>
                    </tr>
    <%
            }
        }
    %>
            </table>
<!------------------------------ 본문 끝 //------------------------------>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="space10"> </td>
        </tr>
    </table>
    <!-- 리스트 시작 //-->
<%
    int ksize = total/psize;
    int x = total%psize;
    if(x>0) ksize++;
    int temp = cpage/pageCount;
    if( (cpage%pageCount)>0)temp++;
    int start = (temp-1)*pageCount+1;

    int end = start + pageCount-1;
    if(end> ksize){
        end = ksize;
    }

%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
          <td>
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40" align="center"><%if(start>1){%><a href="JavaScript:gotoPage(1)" class="small"><img src="/plm/portal/images/btn_arrow4.gif" style="border:0"></a><%}%></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%if(start>1){%>
                        <td width="60" class="quick" align="center"><a href="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow3.gif" style="border:0"></a></td>
                        <td width="1" bgcolor="#dddddd"></td>
                        <%}
                        for(int i=start; i<= end; i++){
                        %>
                        <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
                            <%if(i==cpage){%><font color='006699'><b><%=i%></b></font><%}else{%><font color='777777'><a href="JavaScript:gotoPage(<%=i%>)"><%=i%></a></font><%}%></td>
                        <%}
                        if(end < ksize){
                        %>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="60" align="center"><a href="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><img src="/plm/portal/images/btn_arrow1.gif" style="border:0"></a></td>
                        <%}%>
                        <td width="1" bgcolor="#dddddd"></td>
                        <td width="45"align="center"><%if(end<ksize){%><a href="JavaScript:gotoPage(<%=ksize%>)" class="small"><img src="/plm/portal/images/btn_arrow2.gif" style="border:0"></a><%}%></td>
                    </tr>
                </table>
          </td>
            <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
        </tr>
    </table>

</form>
</body>
</html>
<%
%>
