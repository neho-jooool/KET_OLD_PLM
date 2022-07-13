<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.inf.container.WTContainerRef"%>
<%@ page import="e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@ page import="e3ps.common.util.CommonUtil,e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String webAppName = CommonUtil.getWebAppName();

    WTContainerRef wtcontainerRef = EDMUtil.getWTContinerRefByEDMDefault();

    String command = request.getParameter("command");
    String fromPage = request.getParameter("fromPage");
    String mode = request.getParameter("mode");

    String invokeMethod = request.getParameter("invokeMethod");

    String number = request.getParameter("number");
    String name = request.getParameter("name");
    String viewName = request.getParameter("view");
    String state = request.getParameter("state");

    String containerOid = request.getParameter("containerOid");

    //페이지 관련 Parameter
    String psizeStr = request.getParameter("psize");
    String cpageStr = request.getParameter("cpage");


    if( (command == null) || (command.trim().length() == 0) )
        command = "";

    if( (fromPage == null) || (fromPage.trim().length() == 0) )
        fromPage = "";

    if( (mode == null) || (mode.trim().length() == 0))
        mode = "one";

    if(invokeMethod == null)
        invokeMethod = "";

    if(number == null)
        number = "";

    if(name == null)
        name = "";

    if(viewName == null)
        viewName = "Design";

    if(state == null)
        state = "";

    if( (containerOid == null) || (containerOid.trim().length() == 0))
        containerOid = "";



    if(containerOid.length() == 0) {
        containerOid = wtcontainerRef.getObjectId().toString();
    }


    //페이지 처리
    int psize = 10;
    int cpage = 1;
    int total = 0;
    int pageCount = 5;


    long sessionId = 0L;

    if( (psizeStr != null) && (psizeStr.trim().length() > 0) ) {
        psize = Integer.parseInt(psizeStr);
    }

    if( (cpageStr != null) && (cpageStr.trim().length() > 0) ) {
        cpage = Integer.parseInt(cpageStr);
    }


    int listStart = (cpage-1)*psize+1;
    int listEnd = listStart+psize;

    //QueryResult result = null;
    PagingQueryResult result = null;
    if("search".equals(command)) {
        try {
            if(sessionId > 0L) {
                result = EDMQueryHelper.fetchPagingSession(listStart-1,psize,sessionId);
            } else {
                HashMap map = new HashMap();
                map.put("number",number);
                map.put("name",name);
                map.put("view",viewName);
                map.put("state",state);
                map.put("className",wt.part.WTPart.class.getName());

                //map.put("containerOid",containerOid);

                result = EDMQueryHelper.openPagingSession(map,listStart-1,psize);

                sessionId = result.getSessionId();
                total = result.getTotalSize();

                /*
                    map.put("start",new Integer(listStart));
                    map.put("size",new Integer(psize));
                    map.put("end",new Integer(listEnd));
                    result = VersionedQueryUtil.search(map);

                    map = new HashMap();
                    map.put("number",number);
                    map.put("name",name);
                    map.put("view",viewName);
                    map.put("state",state);
                    map.put("className",wt.part.WTPart.class.getName());
                    total = VersionedQueryUtil.totalCount(map);
                */
            }
        }
        catch(Exception e) {
            Kogger.error(e);
        }
        finally {
            if(sessionId != 0L) {
                PagingSessionHelper.closePagingSession(sessionId);
            }
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="js/edm.js"></SCRIPT>
<SCRIPT LANGUAGE=JavaScript>
function onKeyPress() {
    if (window.event) {
        if (window.event.keyCode == 13) doSearch();
    } else return;
}
function doSearch(){
    var nn = document.forms[0].number.value;
    //if(nn==null || nn.length<4){
        //alert("품번을 최소 4자리 입력 해 주십시오.");
        //return;
    //}
    document.forms[0].command.value="search";
    document.forms[0].method="post";
    document.forms[0].action="/<%=webAppName%>/jsp/edm/edmSearchPart.jsp";
    document.forms[0].submit();
}

function gotoPage(p){
    document.forms[0].command.value='search';
    document.forms[0].cpage.value=p;
    document.forms[0].submit();
}


document.onkeypress = onKeyPress;


function doSeledItem(_itemName) {
    form = document.forms[0];

    var arr = goCheckedData(_itemName);
    if(arr.length == 0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01808") %><%--선택된 부품이 없습니다--%>");
        return;
    }

    //alert(arr.length + "\n" + arr);
    /*
        if(arr.length > 0) {
            for(var i = 0; i < arr.length; i++) {
                subarr = arr[i];//0:oid, 1:number, 2:name, 3:state, 4:type, 5:view, 6:규격, 7:단위, 8:재질, 9:표면처리
            }
        }
    */

<%  if(invokeMethod.length() == 0) {  %>
    //modal dialog
    selectModalDialog(arr);
<%  } else {  %>
    //open window
    selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    if(opener) {
        opener.focus();
    }
    window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
    //...이하 원하는 스크립트를 만들어서 작업...
    if(opener) {
        if(opener.<%=invokeMethod%>) {
            opener.<%=invokeMethod%>(arrObj);
        }
        opener.focus();
    } else if(parent) {
        if(parent.opener) {
            if(parent.opener.<%=invokeMethod%>) {
                parent.opener.<%=invokeMethod%>(arrObj);
            }
            parent.opener.focus();
        }
    }
    self.close();
}
<%  }  %>

function isCheckedData(_itemName) {
    var chkItems = document.getElementsByName(_itemName);
    for(var i = 0; i < chkItems.length; i++) {
        if(chkItems[i].checked == true) {
            return true;
        }
    }

    return false;

}

function goCheckedData(_itemName) {
    var chkItems = document.getElementsByName(_itemName);

    var arr = new Array();
    var subarr = new Array();//0:oid, 1:number, 2:name, 3:version
    if(!isCheckedData(_itemName)) {
        return arr;
    }

    var idx = 0;
    for(var i = 0; i < chkItems.length; i++) {
        if(chkItems[i].checked == true) {
            subarr = new Array();
            subarr[0] = chkItems[i].value;//oid
            subarr[1] = chkItems[i].partnumber;//number
            subarr[2] = chkItems[i].partname;//name
            subarr[3] = chkItems[i].partversion;//version

            arr[idx++] = subarr;
        }
    }

    return arr;
}
</SCRIPT>
</head>
<body>
<form method=post>
<!-- hidden -->
<input type="hidden" name="command" value="<%=command%>">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>">
<input type="hidden" name="fromPage" value="<%=fromPage%>">
<input type="hidden" name="view" value="<%=viewName%>">
<input type="hidden" name="state" value="<%=state%>">

<!-- paging -->
<input type='hidden' name='cpage'>
<input type='hidden' name='psize' value="<%=psize%>">

<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="50" valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="../../portal/images/logo_popupbg.png">
                        <table height="28" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="7"></td>
                                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01573") %><%--부품 추가--%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10">&nbsp;</td>
                    <td valign="top">
                        <table width="460" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td>&nbsp;</td>
                                <td align="right">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td  class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="330" class="tdwhiteL0">
                                    <input type="text" name="number" class="txt_field"  value="<%=number%>" style="width:100%;text-transform:'uppercase';ime-mode:disabled;" id="number">
                                </td>
                            </tr>
                            <tr>
                                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="330" class="tdwhiteL0">
                                    <input type="text" name="name" class="txt_field"  style="width:100%" value="<%=name%>" id="name">
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td class="space15"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td  class="tab_btm2"></td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td width="40" class="tdblueM">
                                    <%if("one".equals(mode)){%><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%><%}else{%><input type="checkbox" name="chkAll" value="" onClick="javascript:goCheckAll(this,'chkItem');"/><%}%>
                                </td>
                                <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                                <td width="85" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                            </tr>
                            <%  if( (result == null) || (result.size() == 0)) { %>
                                    <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td></tr>
                            <%  } else { %>
                                    <%  ReferenceFactory rf = new ReferenceFactory();

                                        WTUser creator = null;

                                        Object oo[] = null;
                                        while(result.hasMoreElements()) {
                                            oo = (Object[])result.nextElement();
                                            String nr = (String)oo[1];
                                            String nm = (String)oo[2];
                                            String nv = (String)oo[3];
                                            String noid = (String)oo[0];


                                            Timestamp createStamp = (Timestamp)oo[5];

                                            try {
                                                creator = (WTUser)rf.getReference((String)oo[7]).getObject();
                                            }
                                            catch(Exception e) {
                                        	Kogger.error(e);
                                                creator= null;
                                            }


                                    %>
                                            <tr>
                                                <td width="40" class="tdwhiteM">
                                                    <input type="checkbox" name="chkItem" value="<%=noid%>" <%if("one".equals(mode)){%>onClick="javascript:goCheckItem(this);"<%}%>
                                                                                            partnumber='<%=nr%>'
                                                                                            partname='<%=nm%>'
                                                                                            partversion='<%=nv%>'
                                                    />

                                                </td>
                                                <td width="110" class="tdwhiteM"><%=nr%></td>
                                                <td width="110" class="tdwhiteL"><%=nm%></td>
                                                <td width="40" class="tdwhiteM"><%=nv%></td>
                                                <td width="75" class="tdwhiteM"><%=(creator==null)? "&nbsp;":creator.getFullName()%></td>
                                                <td width="85" class="tdwhiteM0"><%=DateUtil.getDateString(createStamp,"d")%></td>
                                            </tr>
                                    <%
                                        }
                                    %>

                            <%  } %>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
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
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td class="small" align="left">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> : <%=ksize%> )</td>
                                <td>
                                    <table border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <!-- 처음 -->
                                            <td width="20" align="center"><% if(start > 1) { %><a href="javascript:;" onClick="JavaScript:gotoPage(1);" class='small'><img src='../../portal/images/btn_arrow4.gif' style='border:0'></a><%  } %></td>
                                            <td width="1" bgcolor="#dddddd"></td>
                                            <!-- 이전 -->
                                            <%  if(start > 1) { %>
                                                    <td width="20" align="center"><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=start-1%>);" class='small'><img src='../../portal/images/btn_arrow3.gif' style='border:0'></a></td>
                                                    <td width="1" bgcolor="#dddddd"></td>
                                            <%  } %>
                                            <!-- 페이지 -->
                                            <%  for(int i=start; i<= end; i++){ %>
                                                    <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on"><font color=777777><%if(i==cpage){%><b><font color=006699><%=i%></font></b><%}else{%><a href="javascript:;" onClick="JavaScript:gotoPage(<%=i%>);"><%=i%></a><%}%></font></td>
                                            <%  } %>
                                            <!-- 다음 -->
                                            <%  if(end < ksize) { %>
                                                    <td width="1" bgcolor="#dddddd"></td>
                                                    <td width="20"align="center"><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=end+1%>);" class='small'><img src='../../portal/images/btn_arrow1.gif' style='border:0'></a></td>
                                            <%  } %>
                                            <!-- 마지막 -->
                                            <td width="1" bgcolor="#dddddd"></td>
                                            <td width="20"align="center"><%  if(end < ksize) { %><a  href="javascript:;" onClick="JavaScript:gotoPage(<%=ksize%>);" class='small'><img src='../../portal/images/btn_arrow2.gif' style='border:0'></a><% } %></td>

                                        </tr>
                                    </table>
                                </td>
                                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%> : <%=total%>)</td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="460">
                            <tr>
                                <td class="space15"></td>
                            </tr>
                        </table>
                        <table width="460" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td align="center">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSeledItem('chkItem');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                        </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom">
            <table width="470" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10">&nbsp;</td>
                    <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form>
</body>
</html>
