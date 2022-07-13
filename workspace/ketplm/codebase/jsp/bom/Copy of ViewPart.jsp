<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("poid");      // oid
    String type = "";
    String partNo = "";

    WTPart wt = null;

    if( poid != null && !poid.equals("") ) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        if (wt != null) {
            type = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
            partNo = wt.getNumber();
        }
    }

    QueryResult linkQrDoc = null;    // 관련문서
    ArrayList linkQrDwg = null;      // 관련도면
    ArrayList linkQrEco = null;      // 관련ECO

    linkQrDoc = PersistenceHelper.manager.navigate( wt, "document", KETDocumentPartLink.class );

    linkQrDwg = EDMPartHelper.getReferenceDocs(wt);

    linkQrEco = PartDao.searchRelatedEco(partNo);

//System.out.println("@@@@@ linkQrEco : " + linkQrEco);
//System.out.println("@@@@@ linkQrEco.size : " + linkQrEco.size());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style type="text/css">
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
.tabIframeWrapper {width:100%;height:640px;border:0px; margin:0px;position:relative;top:0px;}
</style>
<script language='javascript'>

    // 문서 상세조회 팝업
    function viewDocPop(docOid){
        var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid=" + docOid;
        openWindow(url,"","830","670","status=yes,scrollbars=no,resizable=no");
    }

    // 도면 상세조회 팝업
    function viewDwgPop(dwgOid){
        var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + dwgOid;
        openWindow(url,"","820","800","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
    }

    // 설계변경 상세조회 팝업
    function viewEcoPop(ecoOid, ecoType){
        var url = "";
        if (ecoType != null && ecoType == "P") {// 제품
            url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + ecoOid;
        }else {                  // 금형
            url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + ecoOid;
        }

        openWindow(url,"","800","750","scrollbars=no,resizable=no,top=200,left=250,center=yes");
    }


    // 이전 변경이력 조회 팝업
    function serachPartPopup(itemCode){
        var url = "/plm/jsp/bom/MoldChangHistoryPopup.jsp?itemcode=" + itemCode;
        openWindow(url,"","630","600","scrollbars=auto,resizable=no,top=200,left=450");
    }

    // 버전이력 팝업
    function viewVerHistory(poid){

        viewVersionHistory(poid);
        //var url ="/plm/servlet/e3ps/VersionHistoryServlet?oid=" + poid;
        //openWindow3(url,"버전이력보기","750","400");
    }

    // 결재이력 팝업
    function viewWFHistory(poid){
        viewHistory(poid);
    }

    // 수정 테스트
    function updateTest(poid){

        document.forms[0].action = "/plm/servlet/e3ps/PartServlet?cmd=modify";
        document.forms[0].oid.value = poid;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }

</script>

</head>
<body>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
    CommonUtil.tabs('tabs');
})
</script>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	    <td valign="top" style="padding: 0px 0px 0px 0px">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	                <td>
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                        <tr>
	                            <td background="/plm/portal/images/logo_popupbg.png">
	                                <table height="28" border="0" cellpadding="0" cellspacing="0">
	                                    <tr>
	                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
	                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01572") %><%--부품 상세조회--%></td>
	                                        <td width="10"></td>
	                                    </tr>
	                                </table>
	                            </td>
	                            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
	                        </tr>
	                    </table>
	                </td>
	            </tr>
	        </table>
	        <div id="tabs">
		    <ul>
		        <li><a class="tabref" href="#tabs-1">PART</a></li>
		        <li><a class="tabref" href="#tabs-2" rel="/plm/extcore/jsp/bom/KETBomEditor.jsp?oid='<%=poid%>'">BOM</a></li>
		    </ul>
		    <!-- 첫번째 tab 화면 -->
		    <div id="tabs-1" class="tabMain">
		    <table style="width: 100%; height: 100%;">
		      <form method=post>
			  <input type="hidden" name="oid">
			  <tr>
			    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="10">&nbsp;</td>
			          <td valign="top"><table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="20"><img src="../../portal/images/icon_4.png"></td>
			                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01924") %><%--속성정보--%></td>
			                <td align="right">&nbsp;</td>
			              </tr>
			            </table>
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			              <tr>
			                <td class="space5"></td>
			              </tr>
			            </table>
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			              <tr>
			                <td width="100%" valign="top"><table border="0" cellspacing="0" cellpadding="0" width="100%">
			                    <tr>
			                      <td  class="tab_btm2"></td>
			                    </tr>
			                  </table>
			
			                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			            <%
			                    // 정보 추출하기
			                    String p_oid = "";
			                    String part_number = "";
			                    String part_name = "";
			                    String part_ver = "";
			                    String part_unit = "";
			                    String part_createDate = "";
			                    String part_updateDate = "";
			                    String part_isDeleted ="";
			                    String part_group ="";
			                    String part_weight ="";
			                    String part_isYazaki ="";
			                    String part_isYazakiNo ="";
			                    String tempWeight = "";
			
			                    KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
			                    PeopleData pData = new PeopleData();
			
			                    if (wt != null) {
			
			                        p_oid = KETObjectUtil.getOidString(wt);
			                        part_number = wt.getNumber();
			                        part_name = wt.getName();
			                        part_ver = KETObjectUtil.getVersion(wt);
			                        part_unit = bean.getUnitDisplayValue(wt.getDefaultUnit() +"");
			                        part_createDate = DateUtil.getDateString(wt.getCreateTimestamp(), "a");
			                        part_updateDate = DateUtil.getDateString(wt.getModifyTimestamp(), "a");
			                        part_isDeleted = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpIsDeleted);
			                        if ( part_isDeleted.equals("Y") )
			                        {
			                            part_isDeleted = messageService.getString("e3ps.message.ket_message", "01703")/*삭제됨*/;
			                        } else {
			                            part_isDeleted = messageService.getString("e3ps.message.ket_message", "01679")/*사용중*/;
			                        }
			
			                        part_group = null; // 사용않함 - 1차고도화 PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartGroup);
			                        if ( part_group == null || (part_group != null && part_group.equals("")) )
			                        {
			                            part_group = "-";
			                        }
			
			                        part_weight = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpNetWeight);
			                        if ( part_weight == null || (part_weight != null && part_weight.equals("")) )
			                        {
			                            part_weight = "-";
			                        }
			                        else    // ".", "-." 으로 시작하는 것 확인
			                        {
			                            if ( part_weight.length() > 1 )
			                            {
			                                tempWeight = part_weight.substring( 0, 1 );
			                                if ( tempWeight.equals(".") )
			                                {
			                                    part_weight = "0" + part_weight;
			                                }
			
			                                tempWeight = part_weight.substring( 0, 2 );
			                                if ( tempWeight.equals("-.") )
			                                {
			                                    part_weight = "-0." + part_weight.substring( 2, part_weight.length() );
			                                }
			                            }
			                        }
			
			                        part_isYazaki = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpIsYazaki);
			                        if ( part_isYazaki == null || (part_isYazaki != null && part_isYazaki.equals("")))
			                        {
			                            part_isYazaki = "-";
			                        }
			
			                        part_isYazakiNo = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpYazakiNo);
			                        if ( part_isYazakiNo == null || (part_isYazakiNo != null && part_isYazakiNo.equals("")))
			                        {
			                            part_isYazakiNo = "-";
			                        }
			                    }
			            %>
			                    <tr>
			                      <td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
			                      <td width="165" class="tdwhiteL"><%=part_number%></td>
			                      <td width="80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%></td>
			                      <td width="165" class="tdwhiteL0"><%=part_unit%></td>
			                    </tr>
			                    <tr>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
			                      <td colspan="3" class="tdwhiteL0"><%=part_name%></td>
			                    </tr>
			                    <tr>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
			                      <td class="tdwhiteL"><%=part_createDate%></td>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
			                      <td class="tdwhiteL0"><%=part_updateDate%></td>
			                    </tr>
			                    <tr>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01695") %><%--삭제 여부--%></td>
			                      <td class="tdwhiteL"><%=part_isDeleted%></td>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
			                      <td class="tdwhiteL0"><a href="javascript:viewVerHistory('<%=p_oid%>');"><%=part_ver%></a></td>
			                    </tr>
			                    <tr>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02419") %><%--자재그룹--%></td>
			                      <td class="tdwhiteL"><%=part_group%></td>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02619") %><%--제품중량--%></td>
			                      <td class="tdwhiteL0"><%=part_weight%></td>
			                    </tr>
			                    <tr>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00569") %><%--YAZAKI여부--%></td>
			                      <td class="tdwhiteL"><%=part_isYazaki%></td>
			                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00570") %><%--YAZAKI제번--%></td>
			                      <td class="tdwhiteL0"><%=part_isYazakiNo%></td>
			                    </tr>
			                  </table></td>
			                <td width="10"></td>
			                <td width="200"  height="100%"  valign="top">
			                    <table width="200" height="100%" border="0" cellspacing="10" cellpadding="0" class="table_border">
			                        <tr>
			                        <td align="center" valign="middle">
			                            <jsp:include page="/jsp/edm/thumbview.jsp" flush="false">
			                                <jsp:param name="oid" value="<%=poid%>"/>
			                            </jsp:include>
			                        </td>
			                        </tr>
			                    </table>
			                </td>
			              </tr>
			            </table>
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			              <tr>
			                <td class="space15"></td>
			              </tr>
			            </table>
			            <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="20"><img src="../../portal/images/icon_4.png"></td>
			                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00929") %><%--관련문서--%></td>
			                <td align="right">&nbsp;</td>
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
			                <td width="60" class="tdblueM" >No</td>
			                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
			                <td width="250" class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
			                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
			                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
			                <td width="110" class="tdblueM0" ><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
			              </tr>
			            </table>
			
			            <div style="width=100%;height=45;overflow-x:hidden;overflow-y:auto;">
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			
			<%      if( (linkQrDoc == null) || (linkQrDoc.size() == 0)) { %>
			              <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00913") %><%--관련 문서가 없습니다--%></td></tr>
			<%      } else { %>
			<%
			            String docOid = null;
			            KETProjectDocument pDoc = null;
			            int docCount = linkQrDoc.size();
			            while( linkQrDoc.hasMoreElements() ){
			                pDoc =   (KETProjectDocument)linkQrDoc.nextElement();
			                docOid = KETObjectUtil.getOidString(pDoc);
			%>
			              <tr>
			                <td width="60" class="tdwhiteM"><%=docCount--%></td>
			                <td width="130" class="tdwhiteM"><a href="javascript:viewDocPop('<%=docOid%>');"><%=pDoc.getDocumentNo()%></a></td>
			                <td width="250" class="tdwhiteL"><%=pDoc.getTitle()%></td>
			                <td width="50" class="tdwhiteM"><%=KETObjectUtil.getVersion(pDoc)%></td>
			                <td width="100" class="tdwhiteM"><%=pDoc.getCreatorFullName()%></td>
			                <td width="110" class="tdwhiteM0"><%=DateUtil.getDateString(pDoc.getCreateTimestamp(),"d")%></td>
			              </tr>
			<%
			                }
			            }
			%>
			            </table>
			            </div>
			
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			              <tr>
			                <td class="space15"></td>
			              </tr>
			            </table>
			            <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="20"><img src="../../portal/images/icon_4.png"></td>
			                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00926") %><%--관련도면--%></td>
			                <td align="right">&nbsp;</td>
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
			                <td width="60" class="tdblueM">No</td>
			                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
			                <td width="250" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
			                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
			                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
			                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
			              </tr>
			            </table>
			
			            <div style="width=100%;height=45;overflow-x:hidden;overflow-y:auto;">
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			<%      if( (linkQrDwg == null) || (linkQrDwg.size() == 0)) { %>
			              <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00911") %><%--관련 도면이 없습니다--%></td></tr>
			<%      } else { %>
			<%
			                String dwgOid = "";
			                int dwgCount = linkQrDwg.size();
			                for(int inx = 0; inx < linkQrDwg.size(); inx++) {
			                    Object[] objs = (Object[])linkQrDwg.get(inx);
			                    EPMDocument epm = (EPMDocument)objs[1];
			                    dwgOid = KETObjectUtil.getOidString(epm);
			%>
			              <tr>
			                <td width="60" class="tdwhiteM"><%=dwgCount--%></td>
			                <td width="130" class="tdwhiteM"><a href="javascript:viewDwgPop('<%=dwgOid%>');"><%=epm.getNumber()%></a></td>
			                <td width="250" class="tdwhiteL"><%=epm.getName()%></td>
			                <td width="50" class="tdwhiteM"><%=KETObjectUtil.getVersion(epm)%></td>
			                <td width="100" class="tdwhiteM"><%=epm.getCreatorFullName()%></td>
			                <td width="110" class="tdwhiteM0"><%=DateUtil.getDateString(epm.getCreateTimestamp(),"d")%></td>
			              </tr>
			<%
			                }
			            }
			%>
			            </table>
			            </div>
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			              <tr>
			                <td class="space15"></td>
			              </tr>
			            </table>
			            <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="20"><img src="../../portal/images/icon_4.png"></td>
			                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00937") %><%--관련설계변경--%></td>
			
			<%
			            if ( !type.equals("") && !type.equals("P") )    // 제품일때는 안보여줌
			            {
			%>
			                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
			                    <td background="../../portal/images/btn_bg1.gif"><a href="javascript:serachPartPopup('<%=part_number%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02323") %><%--이전 변경 이력--%></a></td>
			                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
			                  </tr>
			                </table></td>
			<%
			            }
			%>
			
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
			                <td width="60" class="tdblueM">No</td>
			                <td width="100" class="tdblueM">ECO NO</td>
			                <td width="190" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
			                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
			                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
			                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
			                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01498") %><%--변경 사유--%></td>
			              </tr>
			            </table>
			
			            <div style="width=100%;height=45;overflow-x:hidden;overflow-y:auto;">
			            <table border="0" cellspacing="0" cellpadding="0" width="100%">
			<%      if( (linkQrEco == null) || (linkQrEco.size() == 0)) { %>
			              <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00917") %><%--관련 설계변경이 없습니다--%></td></tr>
			<%      } else { %>
			<%
			            String ecoOid = "";
			            String linkOid = "";
			            String ecoType = "";
			            String strEcoReason = "";
			            KETProdChangeOrder pEco = null;
			            KETMoldChangeOrder mEco = null;
			            Object resultObj = null;
			
			            int ecoCount = linkQrEco.size();
			            for(int inx = 0; inx < linkQrEco.size(); inx++) {
			                Hashtable<String, String> hash = (Hashtable<String, String>)linkQrEco.get(inx);
			                linkOid = hash.get("ecoOid");
			                resultObj = KETObjectUtil.getObject(linkOid);
			
			                if( resultObj instanceof KETProdChangeOrder ) {
			                    pEco = (KETProdChangeOrder)resultObj;
			                    ecoOid = KETObjectUtil.getOidString(pEco);
			                    ecoType = "P";
			                } else {
			                    mEco = (KETMoldChangeOrder)resultObj;
			                    ecoOid = KETObjectUtil.getOidString(mEco);
			                    ecoType = "M";
			                }
			%>
			              <tr>
			                <td width="60" class="tdwhiteM"><%=ecoCount--%></td>
			                <td width="100" class="tdwhiteM"><a href="javascript:viewEcoPop('<%=ecoOid%>','<%=ecoType%>');"><%if("P".equals(ecoType)){%><%=pEco.getEcoId()%><%}else{%><%=mEco.getEcoId()%><%}%></a></td>
			                <td width="190" class="tdwhiteL"><%if("P".equals(ecoType)){%><%=pEco.getEcoName()%><%}else{%><%=mEco.getEcoName()%><%}%></td>
			                <td width="60" class="tdwhiteM"><%if("P".equals(ecoType)){%>
			                                                    <%=PartDao.getChangeTypeName( pEco.getDevYn(), "DEVYN" )%>
			                                                <%}else{  %>
			                                                    <%=PartDao.getChangeTypeName(mEco.getDevYn(), "DEVYN" )%>
			                                                <% }%>
			                </td>
			                <td width="80" class="tdwhiteM"><%if("P".equals(ecoType)){%><%=pEco.getCreatorFullName()%><%}else{%><%=mEco.getCreatorFullName()%><%}%></td>
			                <td width="100" class="tdwhiteM"><%if("P".equals(ecoType)){%>
			                                                    <%=DateUtil.getDateString(pEco.getCreateTimestamp(),"d")%>
			                                                 <%}else{%>
			                                                     <%=DateUtil.getDateString(mEco.getCreateTimestamp(),"d")%>
			                                                 <%}%>
			                </td>
			<%
			            if("P".equals(ecoType)){
			                strEcoReason = PartDao.getChangeReasonName( pEco.getChangeReason(), "PRODECOREASON" );
			            }else {
			                strEcoReason = PartDao.getChangeReasonName( mEco.getChangeReason(), "CHANGEREASON" );
			            }
			%>
			                <td width="110" class="tdwhiteM0" title="<%=strEcoReason%>">
			                    <div class="ellipsis" style="width:110;"><nobr>
			                        <%=strEcoReason%>
			                    </nobr></div>
			                   </td>
			              </tr>
			<%
			                }
			            }
			%>
			            </table>
			    </div>
			          </td>
			        </tr>
			      </table>
			    </td>
			  </tr>
			
			<!--
			        <tr>
			          <td>&nbsp;</td>
			          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
			            <tr>
			              <td><table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
			                  <td background="../../portal/images/btn_bg1.gif"><a href="javascript:updateTest('<%=poid%>');" class="btn_blue">수정</a></td>
			                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
			                </tr>
			              </table></td>
			              </tr>
			          </table></td>
			        </tr>
			-->
			  <tr>
			    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="10">&nbsp;</td>
			          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
			        </tr>
			      </table>
			    </td>
			  </tr>
			</form>
		  </table>
		  </div>
		  <!-- 2번째 tab 화면부터는 rel속성에 정의된 url을 호출합니다. -->   
		  <div id="tabs-2">
		       <table style="width: 100%; height: 100%;">
		       </table>
		  </div>
		</div>
		</td>
    </tr>
</table>
</body>
</html>
