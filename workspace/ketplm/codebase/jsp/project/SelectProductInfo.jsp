<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="wt.fc.*,
                e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.web.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.SearchCondition"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%

    /**
    parameter
     - mode = multi 면 다중 체크 가능
     - pjtNo = 프로젝트 번호

     팝업사이즈
     Width=720px
     Height=500px;

    **/

    PageQueryBroker broker  = null;

    String pjtNo = request.getParameter("pjtNo");
    String pjtNoHidden = request.getParameter("pjtNoHidden");
    String mode = request.getParameter("mode");

    String drKeyOid = request.getParameter("drKeyOid");
    if(drKeyOid != null && drKeyOid.length() > 0) {
        e3ps.dms.entity.KETDevelopmentRequest dr = (e3ps.dms.entity.KETDevelopmentRequest)CommonUtil.getObject(drKeyOid);
        if(dr.getProjectOID() != null && dr.getProjectOID().length() > 0) {
            Object obj = CommonUtil.getObject(dr.getProjectOID());
            if ( obj instanceof E3PSProject ) {
                E3PSProject project = (E3PSProject)obj;

                pjtNo = project.getPjtNo();
                pjtNoHidden = project.getPjtNo();
            }
        }
    }

    QuerySpec spec = null;

    spec = new QuerySpec();

    Class target = ProductInfo.class;

    int idx = spec.addClassList(target, true);


    //if(pjtNo!=null&&pjtNo.length()>0){

        int idxpt = spec.addClassList(E3PSProject.class, false);
        int idxmaster = spec.addClassList(E3PSProjectMaster.class, false);


        SearchCondition sc = null;
        sc = new SearchCondition(
                new ClassAttribute(ProductInfo.class, "projectReference.key.id")
                , "=" ,
                new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id")
            );
        spec.appendWhere(sc, new int[] { idx , idxpt });

        spec.appendAnd();


        SearchCondition sc3 = null;
        sc3 = new SearchCondition(
                new ClassAttribute(E3PSProject.class, "masterReference.key.id") ,
                "=" ,
                new ClassAttribute(E3PSProjectMaster.class, "thePersistInfo.theObjectIdentifier.id"));
        spec.appendWhere(sc3, new int[] { idxpt , idxmaster });

        spec.appendAnd();
        if(CommonUtil.isMember("자동차사업부")){
            spec.appendOpenParen();
            spec.appendWhere(new SearchCondition(E3PSProject.class, "pjtType", "=",2), new int[]{idxpt} );
            spec.appendOr();
            spec.appendWhere(new SearchCondition(E3PSProject.class, "pjtType", "=",1), new int[]{idxpt} );
            spec.appendCloseParen();

        }else{
            spec.appendOpenParen();
            spec.appendWhere(new SearchCondition(E3PSProject.class, "pjtType", "=",4), new int[]{idxpt} );
            spec.appendOr();
            spec.appendWhere(new SearchCondition(E3PSProject.class, "pjtType", "=",0), new int[]{idxpt} );
            spec.appendCloseParen();
        }

        if(pjtNo!=null&&pjtNo.length()>0){
        spec.appendAnd();

        wt.query.StringSearch stringsearch = new wt.query.StringSearch("pjtNo");
        stringsearch.setValue(pjtNo.trim());
        spec.appendWhere(stringsearch.getSearchCondition(E3PSProjectMaster.class) , new int[]{idxmaster});
        }
        //SearchCondition sc2 = new SearchCondition(E3PSProjectMaster.class, "pjtNo" , "LIKE", "%"+pjtNo.toUpperCase()+"%");

        //spec.appendWhere(sc2, new int[] { idxmaster });
    //}


//  spec.appendOrderBy(new OrderBy(new ClassAttribute(target,"thePersistInfo.createStamp"), true), new int[] { idx });

//  Kogger.debug("QS---"+spec);

    broker = new PageQueryBroker(request, spec);
    broker.setPsize(10);

    if(pjtNo != null && pjtNo.trim().length() > 0 && pjtNoHidden.length()== 0){
        broker.setSessionid(0);
    }

    QueryResult qr = null;
    qr = broker.search();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02574")%><%--제품검색--%></title>
<base target="_self">
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
-->
</style>
<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<script>
function Search22(){
    document.SelectProductInfo.sessionid.value= "0";
    document.SelectProductInfo.pjtNoHidden.value = "";
    document.SelectProductInfo.submit();
}
function onSelect() {
        form = document.SelectProductInfo;
        var arr =checkList();
        if(arr.length == 0) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01809") %><%--선택된 제품정보가 없습니다--%>");
            return;
        }
        window.returnValue = arr;
        window.close();
    }

    function checkList() {
        form = document.SelectProductInfo;

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

                    subarr[0] = form.check[i].value;
                    subarr[1] = form.check[i].pNum;
                    subarr[2] = form.check[i].pName;
                    subarr[3] = form.check[i].areas;
                    subarr[4] = form.check[i].usage;
                    subarr[5] = form.check[i].price;
                    subarr[6] = form.check[i].cost;
                    subarr[7] = form.check[i].rate;
                    subarr[8] = form.check[i].estimated;
                    subarr[9] = form.check[i].targetRate;
                    subarr[10] = form.check[i].investments;
                    subarr[11] = form.check[i].seqNum;
                    subarr[12] = form.check[i].carName;
                    subarr[13] = form.check[i].Yield;
                    subarr[14] = form.check[i].reviewProjectNo;

                    arr[idx++] = subarr;
                }
            }
        } else {
            if(form.check.checked == true) {
                subarr = new Array();

                    subarr[0] = form.check.value;
                    subarr[1] = form.check.pNum;
                    subarr[2] = form.check.pName;
                    subarr[3] = form.check.areas;
                    subarr[4] = form.check.usage;
                    subarr[5] = form.check.price;
                    subarr[6] = form.check.cost;
                    subarr[7] = form.check.rate;
                    subarr[8] = form.check.estimated;
                    subarr[9] = form.check.targetRate;
                    subarr[10] = form.check.investments;
                    subarr[11] = form.check.seqNum;
                    subarr[12] = form.check.carName;
                    subarr[13] = form.check.Yield;
                    subarr[14] = form.check.reviewProjectNo;

                arr[idx++] = subarr;
            }
        }

        return arr;
    }

    function isCheckedCheckBox() {
        form = document.SelectProductInfo;
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

    function oneClick(param) {
        if ( !param.checked ) return;

        var len = <%=qr.size()%>;
        var checkStr = param.value;

        var objArr = document.SelectProductInfo;
        if (len > 1) {
            for ( var i = 0 ; i < objArr.length ; i++ ) {
                if ( objArr[i].type == "checkbox" ) {
                    if ( checkStr != objArr[i].value ) {
                        objArr[i].checked = false;
                    }
                }
            }
        }
    }
</script>
</head>
<%@include file="/jsp/common/processing.html"%>
<script type="text/javascript">
showProcessing();
</script>
<body>
    <form name="SelectProductInfo" method="post">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02574")%><%--제품검색--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="70%">&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:;" onclick="javascript:Search22();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td>&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:onSelect()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td>&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="150" class="tdblueM">Project No</td>
                                        <td width="550" class="tdwhiteL0"><input type=hidden name=pjtNoHidden value=<%=pjtNoHidden%>>
                                            <input type="text" name="pjtNo" class="txt_field" style="width: 98%" id="pjtNo"
                                            value="<%=pjtNo == null ? "" : pjtNo%>"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="40" class="tdblueM">&nbsp;</td>
                                        <td width="90" class="tdblueM">Project No</td>
                                        <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00625")%><%--개발구분--%></td>
                                        <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02591")%><%--제품명--%></td>
                                        <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%></td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02151")%><%--예상수량--%></td>
                                        <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02110")%><%--업체명--%></td>
                                        <td width="90" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02136")%><%--영업담당--%></td>
                                    </tr>
                                    <%
                                        while (qr.hasMoreElements()) {
                                    		Object o[] = (Object[]) qr.nextElement();
                                    		ProductInfo pi = (ProductInfo) o[0];
                                    		if (pi.getProject() != null) {
                                    		    E3PSProjectData projectData = new E3PSProjectData(pi.getProject());
                                    		    ProductHelper ph = new ProductHelper();
                                    		    String carName = ph.getCarName(pi.getPersistInfo().getObjectIdentifier().getStringValue());
                                    		    String Yield = ph.getYield(pi.getPersistInfo().getObjectIdentifier().getStringValue());
                                    		    String carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
                                    		    String usage = StringUtil.checkNull(pi.getUsage());
                                    		    String areas = StringUtil.checkNull(pi.getAreas());
                                    		    String pNum = StringUtil.checkNull(pi.getPNum());
                                    		    String pName = StringUtil.checkNull(pi.getPName());
                                    		    String price = StringUtil.checkNull(pi.getPrice());
                                    		    String cost = StringUtil.checkNull(pi.getCost());
                                    		    String rate = StringUtil.checkNull(pi.getRate());
                                    		    String estimated = StringUtil.checkNull(pi.getEstimated());
                                    		    String targetRate = StringUtil.checkNull(pi.getTargetRate());
                                    		    String investments = StringUtil.checkNull(pi.getInvestments());
                                    %>

                                    <tr>
                                        <td width="40" class="tdwhiteM"><input name="check" type="checkbox" class="Checkbox"
                                            <%if (!"multi".equals(mode)) {%> onClick="oneClick(this)" <%}%>
                                            value="<%=pi.getPersistInfo().getObjectIdentifier().getStringValue()%>" pNum="<%=pNum%>"
                                            pName="<%=pName%>" areas="<%=areas%>" usage="<%=usage%>" price="<%=price%>" cost="<%=cost%>"
                                            rate="<%=rate%>" estimated="<%=estimated%>" targetRate="<%=targetRate%>"
                                            investments="<%=investments%>" seqNum="<%=pi.getSeqNum()%>"
                                            carName="<%=carName == "" ? carName2 : carName%>" yield="<%=Yield%>"
                                            reviewProjectNo="<%=pi.getProject().getPjtNo()%>" /></td>
                                        <td width="90" class="tdwhiteM"><%=pi.getProject().getPjtNo()%>&nbsp;</td>
                                        <td width="90" class="tdwhiteM"><%=pi.getProject().getDevelopentType() == null ? "" : StringUtil.checkNull(pi.getProject().getDevelopentType().getName())%>&nbsp;</td>
                                        <td width="130" class="tdwhiteL">
                                            <div
                                                style="width: 120; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                <nobr><%=pi.getPName()%>&nbsp;
                                                </nobr>
                                            </div>
                                        </td>
                                        <td width="90" class="tdwhiteM">
                                            <div
                                                style="width: 80; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                <nobr><%=carName == "" ? carName2 : carName%>&nbsp;
                                                </nobr>
                                            </div>
                                        </td>
                                        <td width="70" class="tdwhiteM"><%=Yield%>&nbsp;</td>
                                        <td width="100" class="tdwhiteM" title="<%=projectData.customer%>">
                                            <div
                                                style="width: 100; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                                <nobr><%=projectData.customer%>&nbsp;
                                                </nobr>
                                            </div>
                                        </td>
                                        <td width="90" class="tdwhiteM0"><%=projectData.salesName%>&nbsp;</td>
                                    </tr>
                                    <%
                                             }
                                          }
                                     %>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td><%=broker.getHtml("SelectProductInfo")%></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
<script type="text/javascript">
hideProcessing();
</script>    
</body>
</html>
