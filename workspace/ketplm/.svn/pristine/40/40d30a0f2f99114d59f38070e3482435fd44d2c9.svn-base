<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                e3ps.project.beans.*,
                e3ps.groupware.company.Department,
                wt.folder.Folder,
                wt.query.QuerySpec,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                e3ps.common.impl.Tree"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
    String oemtype = request.getParameter("oemtype");
    String oid = StringUtil.trimToEmpty(request.getParameter("oid"));
    String mode = request.getParameter("mode");
    String cbMethod = request.getParameter("cbMethod");
    String valueField = request.getParameter("valueField");
    String displayField = request.getParameter("displayField");

    if(oid.equals("null")){
        oid="";
    }
    Kogger.debug("-----oid---List---" + oid );
    Kogger.debug("SelectOEMTypeList", null , "oid, SelectProjectTypeList.jsp::oid = "+oid, null);
    OEMProjectType prjType = null;
    String tempName = null;
    String tempCode = null;
    String tempPath = null;
    String tempSort = null;
    String tempDesc = null;
    String tempFolder = "";
    String docCheck = null;
    String docCheckValue  = null;
    StringTokenizer tempAttr = null;
    String oLevel = "0";
    String isDisabled = "";
    Hashtable attrHash = new Hashtable();
    boolean isModify = false;
    boolean isRoot = false;
    QueryResult qr = null;
    String stack = "";

%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.impl.ParentChildLink"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>
<%@page import="e3ps.common.jdf.log.Logger"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="java.util.Stack"%>
<%@page import="e3ps.project.outputtype.OEMType"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="e3ps.common.code.NumberCode"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=OEMType.toOEMType(oemtype).getComment()%></title>
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
function onSelect() {
        var form = document.SelectOEMTypeList;
        var cbMethod = '<%=cbMethod%>';
        var arr =checkList();
        
        if(arr.length == 0) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01810") %><%--선택된 차종이 없습니다--%>");
            return;
        }
        
        if(cbMethod != '' && cbMethod != null && cbMethod !== undefined){
        	if('unknown' == cbMethod){
        		var target = (opener || parent.opener);
                
                if(target.document.getElementById('<%=valueField%>')){
                    target.document.getElementById('<%=valueField%>').value = arr[0][0];   
                }
                
                if(target.document.getElementById('<%=displayField%>')){
                    target.document.getElementById('<%=displayField%>').value = arr[0][1]; 
                }
                
                if(target.document.getElementsByName('<%=valueField%>')[0]){
                    target.document.getElementsByName('<%=valueField%>')[0].value = arr[0][0]; 
                }
                
                if(target.document.getElementsByName('<%=displayField%>')[0]){
                    target.document.getElementsByName('<%=displayField%>')[0].value = arr[0][1];
                }
        	}else{
        		parent.opener.eval(cbMethod)(arr);	
        	}
        	
        	parent.close();
        }else{
        
        	window.returnValue = arr;
        }
        window.close();
        
}

window.selfClose = function(){
    if(opener || parent) {
        parent.close();
    }else {
        window.close();
    }
}

function checkList() {
	
        form = document.SelectOEMTypeList;

        var arr = new Array();
        var subarr = new Array();
        if(!isCheckedCheckBox()) {
            return arr;
        }

        len = form.check.length;

        var idx = 0;
        if(len) {
            for(var i = 0; i < len; i++) {
                if(form.check[i].checked == true) {
                    subarr = new Array();

                    subarr[0] = form.check[i].value;//OID
                    subarr[1] = form.check[i].codename || form.check[i].getAttribute("codename");//name
                    subarr[2] = form.check[i].codecode || form.check[i].getAttribute("codecode");//code
                    subarr[3] = form.check[i].codedesc || form.check[i].getAttribute("codedesc");//code

                    arr[idx++] = subarr;
                }
            }
        } else {
            if(form.check.checked == true) {
                subarr = new Array();

                subarr[0] = form.check.value;//OID
                subarr[1] = form.check.codename || form.check.getAttribute("codename");//name
                subarr[2] = form.check.codecode || form.check.getAttribute("codecode");//code
                subarr[3] = form.check.codedesc || form.check.getAttribute("codedesc");//code

                arr[idx++] = subarr;
            }
        }

        return arr;
    }

    function isCheckedCheckBox() {
        form = document.SelectOEMTypeList;
        if(form.check == null) {
            return false;
        }

        len = form.check.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.check[i].checked == true) {
                    return true;
                }
            }
        }
        else {
            if(form.check.checked == true) {
                return true;
            }
        }

        return false;

    }

//parent.tree.location.reload();
//-->
</script>
</head>
<body>
    <form name=SelectOEMTypeList method="post">
        <input type="hidden" name="cmd">
        <input type=hidden name=oid value="<%=oid%>">
        <input type=hidden name=oemtype value="<%=oemtype%>">
        <input type=hidden name=cbMethod value="<%=cbMethod%>">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>

                            <td align="left" background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0"
                                    cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "02971")%><%--팝업 화면--%></td>
                                        <td width="10"></td>
                                    </tr>
                                </table></td>
                            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top">

                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="#" onclick="javascript:onSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td width="5px"></td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="#" onclick="javascript:selfClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="460" style="table-layout: fixed;">
                                    <tr>
                                        <td width="40" class="tdblueM">&nbsp;</td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02109")%><%--업체--%></td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00953")%><%--관리코드--%></td>
                                        <td width="140" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01866")%><%--설명--%></td>
                                    </tr>
                                </table>
                                <div style="overflow: auto; height: 340px;">
                                    <table border="0" cellspacing="0" cellpadding="0" width="460" style="table-layout: fixed;">
                                        <%
                                            QuerySpec qs4 = new QuerySpec();

                                            if (StringUtil.checkString(oid) && CommonUtil.getObject(oid) instanceof NumberCode) {

                                        		NumberCode nc = (NumberCode) CommonUtil.getObject(oid);

                                        		int idx4 = qs4.appendClassList(OEMProjectType.class, true);
                                        		if (oid.equals("")) {
                                        		    SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.classname", "<>", "ROOT");
                                        		    qs4.appendWhere(sc4, new int[] { idx4 });
                                        		    qs4.appendAnd();
                                        		} else {
                                        		    long oidLong = CommonUtil.getOIDLongValue(oid);
                                        		    SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", oidLong);
                                        		    qs4.appendWhere(sc4, new int[] { idx4 });
                                        		    qs4.appendAnd();
                                        		}

                                        		SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
                                        		qs4.appendWhere(sc5, new int[] { idx4 });
                                        		ClassAttribute ca = new ClassAttribute(OEMProjectType.class, "name");
                                        		qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 });

                                        		QueryResult qr4 = PersistenceHelper.manager.find(qs4);

                                        		while (qr4.hasMoreElements()) {
                                        		    Object[] o2 = (Object[]) qr4.nextElement();
                                        		    OEMProjectType opt = (OEMProjectType) o2[0];
                                        %>

                                        <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'"
                                            onMouseout="this.style.backgroundColor='#ffffff'">
                                            <td width="40" class="tdwhiteM"><input type="checkbox" name="check"
                                                <%if (!"m".equals(mode)) {%> onClick="oneClick(this)" <%}%>
                                                value="<%=opt.getPersistInfo().getObjectIdentifier().getStringValue()%>"
                                                codename="<%=opt.getName()%>" codecode="<%=opt.getCode()%>"
                                                codedesc="<%=StringUtil.checkNull(opt.getDescription())%>"></td>
                                            <td width="70" class="tdwhiteL"><%=nc.getParent().getName()%>&nbsp;</td>
                                            <td width="70" class="tdwhiteL"><%=nc.getName()%>&nbsp;</td>
                                            <td width="70" class="tdwhiteL"><%=opt.getName()%>&nbsp;</td>
                                            <td width="70" class="tdwhiteL"><%=StringUtil.checkNull(opt.getCode())%>&nbsp;</td>
                                            <td class="tdwhiteL0">&nbsp;<%=StringUtil.checkNull(opt.getDescription())%></td>
                                        </tr>
                                        <%
                                            }

                                            } else if (StringUtil.checkString(oid) && CommonUtil.getObject(oid) instanceof OEMProjectType) {

                                        		int idx4 = qs4.addClassList(NumberCode.class, true);
                                        		long oidLong = CommonUtil.getOIDLongValue(oid);
                                        		SearchCondition sc4 = new SearchCondition(NumberCode.class, "thePersistInfo.theObjectIdentifier.id", "=", oidLong);
                                        		qs4.appendWhere(sc4, new int[] { idx4 });

                                        		ClassAttribute ca = new ClassAttribute(NumberCode.class, "code");
                                        		qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 });

                                        		QueryResult qr4 = PersistenceHelper.manager.find(qs4);

                                        		while (qr4.hasMoreElements()) {
                                        		    Object[] o2 = (Object[]) qr4.nextElement();
                                        		    NumberCode opt = (NumberCode) o2[0];
                                        %>

                                        <TR bgcolor=ffffff onMouseover="this.style.backgroundColor='#efefef'"
                                            onMouseout="this.style.backgroundColor='#ffffff'">
                                            <td width="40" class="tdwhiteM"><input type="checkbox" name="check"
                                                <%if(!"m".equals(mode)){%> onClick="oneClick(this)" <%}%>
                                                value="<%=opt.getPersistInfo().getObjectIdentifier().getStringValue()%>"
                                                codename="<%=opt.getName()%>" codecode="<%=opt.getCode()%>"
                                                codedesc="<%=StringUtil.checkNull(opt.getDescription())%>"></td>
                                            <td width="70" class="tdwhiteL"><%=StringUtil.checkNull(((OEMProjectType)CommonUtil.getObject(oid)).getName()) %></td>
                                            <td width="70" class="tdwhiteL">22</td>
                                            <td width="70" class="tdwhiteL"><%=opt.getName()%>&nbsp;</td>
                                            <td width="70" class="tdwhiteL"><%=StringUtil.checkNull(opt.getCode())%>&nbsp;</td>
                                            <td class="tdwhiteL0"><%=StringUtil.checkNull(opt.getDescription())%>&nbsp;</td>
                                        </tr>
                                        <%

                        }
                }
            %>





                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
   </form>
</body>
</html>
