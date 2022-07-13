<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page import="e3ps.project.*, e3ps.project.beans.*, e3ps.project.moldPart.beans.*"%>
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
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("poid");  // oid
    String type = request.getParameter("type");  // 구분
    String itemCode = "";            // 부품번호
    String itemType = "";            // 부품타입 (P: 제품, D/M : 금형)

    WTPart wt = null;

    if( poid != null && !poid.equals("") ) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        itemCode = wt.getNumber();
        itemType = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
    }

    QueryResult linkQrDoc = null;    // 관련문서
    ArrayList linkQrDwg = null;      // 관련도면
    ArrayList linkQrEco = null;    // 관련ECO
    QueryResult projectResult = null;  // 관련프로젝트

    // 관련 문서 조회
    linkQrDoc = PersistenceHelper.manager.navigate( wt, "document", KETDocumentPartLink.class );

    // 관련 도면 조회
    linkQrDwg = EDMPartHelper.getReferenceDocs(wt);

    // 관련 설계변경 조회
    linkQrEco = PartDao.searchRelatedEco(itemCode);

    // 관련 제품/금형 Profile 조회
    if(itemType != null && itemType.length() > 0 && itemCode != null && itemCode.length() > 0) {
        if("P".equals(itemType)) {
            projectResult = E3PSProjectSearchHelper.getProductProject(itemCode);
        }else if("D/M".equals(itemType)) {
            projectResult = MoldPartHelper.getMoldProject(itemCode);
        }
    }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00916") %><%--관련 상세조회 목록--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language='javascript'>

    // 문서 상세조회 팝업
    function viewDocPop(docOid){
        var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid=" + docOid;
        openWindow(url,"","820","640","status=yes,scrollbars=no,resizable=no");
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
        openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
    }

    // 제품 Profile 상세조회 팝업
    function viewProductProject(oid) {
        var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
        openSameName(url,"1080","768","status=no,scrollbars=yes,resizable=no");
    }

    // 금형 Profile 상세조회 팝업
    function viewMoldProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
    }

</script>

</head>
<body>
<form method=post>
<input type="hidden" name="oid">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
<%
        if (type != null && !type.equals("") ){
            if (type.equals("dwg")) {
%>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00927") %><%--관련도면 조회--%></td>
<%
            }else if (type.equals("dms")) {
%>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00930") %><%--관련문서 조회--%></td>
<%
            }else if (type.equals("eco")) {
%>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00938") %><%--관련설계변경 조회--%></td>
<%
            }else if (type.equals("ppjt")) {
%>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00940") %><%--관련제품Profile 조회--%></td>
<%
            }else if (type.equals("mpjt")) {
%>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00925") %><%--관련금형Profile 조회--%></td>
<%
            }
        }
%>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">

            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
<%
        if (type.equals("dms")) {
%>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td width="60" class="tdblueM">NO</td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                <td width="250" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">

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
<%
        } else if (type.equals("dwg")) {
%>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td width="60" class="tdblueM">NO</td>
                <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                <td width="250" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
<%      if( (linkQrDwg == null) || (linkQrDwg.size() == 0)) { %>
              <tr><td class="tdwhiteM0" colspan="6"><%=messageService.getString("e3ps.message.ket_message", "00911") %><%--관련 도면이 없습니다--%></td></tr>
<%      } else { %>
<%
                String dwgOid = null;
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
<%
        }else if (type.equals("eco")) {
%>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
                <td width="60" class="tdblueM">NO</td>
                <td width="100" class="tdblueM">ECO NO</td>
                <td width="190" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="110" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01498") %><%--변경 사유--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
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

<%
        }else if (type.equals("ppjt")) {
%>
<!-- 제품 Pfofile 조회 테이블 추가 -->
            <table border="0" cellspacing="0" cellpadding="0" width="700">
              <tr>
              <td width="50" class="tdblueM">NO</td>
              <td width="100" class="tdblueM">Project No</td>
              <td width="180" class="tdblueM">Project Name</td>
              <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
                  <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
                  <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
              <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%></td>
              <td width="90" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
            <%
            int listCount = 0;
            if (projectResult != null) {
                listCount = projectResult.size();
            }

            if(listCount>0){
                boolean checkOut = false;
                while (projectResult.hasMoreElements()) {
                    Object[] obj = (Object[]) projectResult.nextElement();
                    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
                    E3PSProject project = data.e3psProject;
                    checkOut = project.isCheckOut();
            %>
                <tr>
                  <td width="50" class="tdwhiteM"><%=listCount %></td>
                  <td width="100" class="tdwhiteL"><a href="javascript:viewProductProject('<%=data.e3psPjtOID%>');"><%=data.pjtNo==null?"":data.pjtNo%>&nbsp;</a></td>
                  <td width="180" class="tdwhiteL"><a href="javascript:viewProductProject('<%=data.e3psPjtOID%>');"><%=data.pjtName==null?"":data.pjtName%>&nbsp;</a></td>
                  <td width="90" class="tdwhiteM"><%=(project.getOemType()==null)?"":project.getOemType().getName() %></td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanEndDate, "d") %>&nbsp;</td>
                  <td width="100" class="tdwhiteM"><%=data.getStateStr(data.stateKorea, false)%></td>
                  <td width="90" class="tdwhiteM0"><%=data.stateKorea%></td>
                </tr>
                <%
                    listCount--;
                }
            }else{
            %>
                <tr>
                  <td class='tdwhiteM0' align='center' colspan='8'> <%=messageService.getString("e3ps.message.ket_message", "00919") %><%--관련 제품프로젝트가 없습니다--%> </td>
                </tr>

            <%
            }
            %>
          </table>

<%
        }else if (type.equals("mpjt")) {
%>
<!-- 금형 Pfofile 조회 테이블 추가 -->
            <table border="0" cellspacing="0" cellpadding="0" width="700">
            <tr>
              <td width="30" class="tdblueM">No</td>
              <td width="100" class="tdblueM">Die No</td>
              <td width="70" class="tdblueM">Part No</td>
              <td width="160" class="tdblueM">Part Name</td>
                  <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
                  <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
              <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%></td>
              <td width="60" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="700">
             <%
            int listCount = 0;
            if (projectResult != null) {
                listCount = projectResult.size();
            }

            boolean checkOut = false;
            if(listCount>0){
                while (projectResult.hasMoreElements()) {
                    Object[] obj = (Object[]) projectResult.nextElement();
                    E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
                    MoldProject project = (MoldProject)data.e3psProject;
                    MoldItemInfo itemInfo = project.getMoldInfo();
                    String itemPartNo = itemInfo.getPartNo();
                    if(itemPartNo == null || itemPartNo.length() == 0){
                        itemPartNo = "&nbsp";
                    }

                    String itemPartName = itemInfo.getPartName();
                    if(itemPartName == null || itemPartName.length() == 0){
                        itemPartName = "&nbsp";
                    }

                    ProductProject productProject = itemInfo.getProject();
                    String productName = productProject.getPjtName();
                    checkOut = project.isCheckOut();

                    String making = itemInfo.getMaking();
                    if(making == null || making.length() == 0){
                        making = "&nbsp";
                    }
            %>
                <tr>
                  <td width="30" class="tdwhiteM"><%=listCount--%></td>
                  <td width="100" class="tdwhiteL"><a href="javascript:viewMoldProject('<%=data.e3psPjtOID%>');"><%=data.pjtNo == null? "" : data.pjtNo%>&nbsp;</a></td>
                  <td width="70" class="tdwhiteL"><%=itemPartNo%></td>
                  <td width="160" class="tdwhiteL"><%=itemPartName%></td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                  <td width="90" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanEndDate, "d") %>&nbsp;</td>
                  <td width="100" class="tdwhiteM"><%=data.getStateStr(data.stateKorea, false)%></td>
                  <td width="60" class="tdwhiteM0"><%=data.stateKorea%></td>
                </tr>
             <%

                }

            }else{
             %>
             <tr>
              <td class='tdwhiteM0' align='center' colspan='8'> <%=messageService.getString("e3ps.message.ket_message", "00910") %><%--관련 금형프로젝트가 없습니다--%> </td>
            </tr>
            <%
            }
            %>
          </table>

<%
        }
%>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="710" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
