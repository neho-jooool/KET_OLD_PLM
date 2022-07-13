<%@page import="e3ps.ecm.entity.KETMoldChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeLink"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.ecm.entity.KETMoldChangeRequest" %>
<%@page import="e3ps.ecm.entity.KETMoldChangeRequestVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECRPartLink" %>
<%@page import="e3ps.ecm.entity.KETMoldECOPartLinkVO" %>
<%@page import="e3ps.ecm.entity.KETMoldECRIssueLinkVO" %>
<%@page import="e3ps.ecm.beans.EcmUtil" %>
<%@page import="java.util.Vector" %>
<%@page import="java.util.ArrayList" %>

<%@page import="wt.content.ContentHolder
                            ,wt.content.ContentHelper" %>
<%@page import="wt.content.ContentItem" %>
<%@page import="e3ps.common.content.ContentInfo" %>
<%@page import="e3ps.common.content.ContentUtil" %>
<%@page import="e3ps.common.util.StringUtil" %>
<%@page import="e3ps.common.util.KETStringUtil" %>
<%@page import="e3ps.common.util.KETObjectUtil" %>
<%@page import="e3ps.ecm.servlet.MoldEcoServlet" %>
<%@page import="e3ps.common.util.CommonUtil" %>
<%@page import="wt.enterprise.Managed" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="ketMoldChangeRequestVO" class="e3ps.ecm.entity.KETMoldChangeRequestVO" scope="request" />
<%
String ecrOid = ketMoldChangeRequestVO.getOid();
KETMoldChangeRequest ecr = (KETMoldChangeRequest)CommonUtil.getObject(ecrOid);

QueryResult ecoqr = PersistenceHelper.manager.navigate(ecr, "moldECO", KETMoldChangeLink.class);
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01006") %><%--금형 ECR 상세조회--%></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 3px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>

<script language=JavaScript src="/plm/jsp/ecm/ViewMoldEcr.js"></script>
<script language="javascript">
//관련 ECO 상세화면 팝업
function viewEco(oid) {
    var url = "/plm/servlet/e3ps/MoldEcoServlet?prePage=Search&cmd=view&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}
</script>
</head>
<body>
<form method="post" action="/plm/servlet/e3ps/MoldEcoServlet">
<input type="hidden" name="cmd" value="view">
<input type="hidden" name="oid" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) { out.print(CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest()));}%>">
<input type="hidden" name="ecrId" value="">
<input type="hidden" name="devYn" value="">
<input type="hidden" name="divisionFlag" value="">
<input type="hidden" name="changeType" value="">
<input type="hidden" name="stateState" value="<%if(ketMoldChangeRequestVO.getTotalCount() > 0) {out.print(ketMoldChangeRequestVO.getProgressState());}%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00207") %><%--ECR 상세조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top">
      <table width="790" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td width="780" valign="top">
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
                  <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td width="135" class="tdblueL">ECR No</td>
                      <td width="265" class="tdwhiteL"><%=ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrId()%>&nbsp;</td>
                      <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
                      <td width="280" class="tdwhiteL0"><%=ketMoldChangeRequestVO.getDevYnName()%>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                      <td colspan="3" class="tdwhiteL0"><%=ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrName()%>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getDivisionFlagName()%>&nbsp;</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                      <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=CommonUtil.getOIDString(ketMoldChangeRequestVO.getKetMoldChangeRequest())%>');" onfocus="this.blur();"><%=ketMoldChangeRequestVO.getProgressStateName()%></a>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getOrgName()%>&nbsp;</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getCreatorName()%>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getCreateDate()%>&nbsp;</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getUpdateDate()%>&nbsp;
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getApprovalName()%>&nbsp;</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getApprovalDate()%>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL">Project No</td>
                        <%
                        String projectOid = StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getProjectOid());
                        if("".equals(projectOid)) {%>
                      <td class="tdwhiteL">-</td>
                        <%} else {%>
                      <td class="tdwhiteL"><a href="javascript:viewProjectPopup('<%=projectOid%>');"><%=ketMoldChangeRequestVO.getProjectNo()%></td>
                        <%}%>
                      <td class="tdblueL">Project Name</td>
                      <%if("".equals(projectOid)) { %>
                      <td class="tdwhiteL0">-</td>
                      <%}else{ %>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getProjectName()%></td>
                      <%} %>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %><%--관련이슈--%></td>
                      <td colspan="3" class="tdwhiteM0">
                          <table border="0" cellspacing="0" cellpadding="0" width="655">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <div id="div_scroll" style="width=655;height=71;overflow:auto;" class="table_border">
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr class="headerLock">
                            <td width="40" class="tdgrayM">No</td>
                            <td width="350" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02317") %><%--이슈명--%></td>
                            <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                            <td width="102" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                          </tr>
                               <%
                               int size = 0;
                               int i = 0;
                               if(ketMoldChangeRequestVO.getTotalCount() > 0) {
                                   ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = ketMoldChangeRequestVO.getKetMoldECRIssueLinkVOList();
                                   if(ketMoldECRIssueLinkVOList == null) {
                                       size = 0;
                                   } else {
                                       size = ketMoldECRIssueLinkVOList.size();
                                   }
                                   KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;
                                   for (i = 0 ; i<size; i++ ) {
                                       ketMoldECRIssueLinkVO = (KETMoldECRIssueLinkVO)ketMoldECRIssueLinkVOList.get(i);
                               %>
                          <tr>
                                   <td class="tdwhiteM"><%=(i+1)%></td>
                                <td class="tdwhiteL"><a href="javascript:viewIssue('<%=ketMoldECRIssueLinkVO.getRelIssueOid()%>');"><%=ketMoldECRIssueLinkVO.getRelIssueName()%></a></td>
                                <td class="tdwhiteM"><%=ketMoldECRIssueLinkVO.getRelIssueCreatorName()%>&nbsp;</td>
                                <td class="tdwhiteM0"><%=ketMoldECRIssueLinkVO.getRelIssueCreateDate()%>&nbsp;</td>
                            </tr>
                               <%
                                    }
                                    if(size == 0){
                                 %>
                                <tr>
                               <td colspan=4 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00918") %><%--관련 이슈가 없습니다--%></td>
                             </tr>
                             <%
                                     }
                                    }
                                    %>
                        </table></div>
                        <table border="0" cellspacing="0" cellpadding="0" width="655">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getProdVendorName()%>&nbsp;</td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getCompleteReqDateFormat()%>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02782") %><%--처리구분--%></td>
                      <td colspan="3" class="tdwhiteL0"><%=ketMoldChangeRequestVO.getProcessTypeName()%><%if(ketMoldChangeRequestVO.getOtherProcessdesc().length()>0){ %>(<%=ketMoldChangeRequestVO.getOtherProcessdesc() %>)<%} %></td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02247") %><%--의뢰구분--%></td>
                      <td class="tdwhiteL"><%=ketMoldChangeRequestVO.getRequestTypeName()%><%if(ketMoldChangeRequestVO.getOtherRequestType().length() >0 ){ %>(<%=ketMoldChangeRequestVO.getOtherRequestType()%>)<%} %></td>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01860") %><%--설계변경유형--%></td>
                      <td class="tdwhiteL0"><%=ketMoldChangeRequestVO.getChangeTypeName()%><%if(ketMoldChangeRequestVO.getOtherChangeType().length() > 0 ){ %>(<%=ketMoldChangeRequestVO.getOtherChangeType() %>)<%} %></td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                      <td colspan="3" class="tdwhiteL0">
                          <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <textarea name="ecrDesc" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field" readonly><%=StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getEcrDesc())%></textarea>
                        <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%><span class="red"></span></td>
                      <td colspan="3" class="tdwhiteL0">
                          <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <textarea name="expectEffect" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field"  readonly><%=StringUtil.checkNull(ketMoldChangeRequestVO.getKetMoldChangeRequest().getExpectEffect())%></textarea>
                        <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                    </td>
                    </tr>
                    <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
                      <td colspan="3" class="tdwhiteM0">
                          <table border="0" cellspacing="0" cellpadding="0" width="655">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <div id="div_scroll2" style="width=655;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border" >
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr class="headerLock2">
                            <td width="40" class="tdgrayM">No</td>
                            <td width="90" class="tdgrayM">Die No</td>
                            <td width="310" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                            <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                            <td width="148" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02191") %><%--요청내용--%></td>
                          </tr>
                        <%
                        if(ketMoldChangeRequestVO.getTotalCount() > 0) {
                            ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = ketMoldChangeRequestVO.getKetMoldECOPartLinkVOList();
                            size = ketMoldECOPartLinkVOList.size();
                            KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
                            for (i = 0 ; i<size; i++ ) {
                                ketMoldECOPartLinkVO = (KETMoldECOPartLinkVO)ketMoldECOPartLinkVOList.get(i);
                        %>
                            <tr>
                                  <td class="tdwhiteM"><%=(i+1)%></td>
                                <td class="tdwhiteM"><a href="javascript:viewPart('<%=ketMoldECOPartLinkVO.getRelPartOid()%>');"><%=KETStringUtil.getTruncatedText(ketMoldECOPartLinkVO.getRelPartNumber(), 10)%></a>&nbsp;</td>
                                <td class="tdwhiteL"><%=KETStringUtil.getTruncatedText(ketMoldECOPartLinkVO.getRelPartName(), 30)%>&nbsp;</td>
                                <td class="tdwhiteM"><%=ketMoldECOPartLinkVO.getRelPartRev()%>&nbsp;</td>
                                <td class="tdwhiteL0"><%=ketMoldECOPartLinkVO.getChangeReqComment()%>&nbsp;</td>
                              </tr>
                        <%
                            }
                        }
                        %>
                        </table></div>
                        <table border="0" cellspacing="0" cellpadding="0" width="655">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                        <td colspan="3" class="tdwhiteM0">
                            <table border="0" cellspacing="0" cellpadding="0" width="655">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
                          <div id="div_scroll3" style="width=655;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border" >
                          <table width="100%" cellpadding="0" cellspacing="0">
                            <tr class="headerLock3">
                                    <td width="40" class="tdgrayM">No</td>
                                <td width="597" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                            </tr>
                                        <%
                                        Vector moldEcrAttacheVec = new Vector();
                                        ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)ketMoldChangeRequestVO.getKetMoldChangeRequest() );
                                        moldEcrAttacheVec = ContentUtil.getSecondaryContents(holder);
                                        int max = moldEcrAttacheVec.size();
                                        if( moldEcrAttacheVec != null )
                                        {
                                            ContentInfo cntInfo = null;
                                ContentItem ctf = null;
                                String appDataOid = "";
                                String url = "";

                                            for ( int j = 0 ; j<max; j++ ) {
                                                cntInfo = (ContentInfo)moldEcrAttacheVec.elementAt(j);
                                      ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                                                appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();

                                                //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                                url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                                url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
                                      %>
                                        <tr>
                                            <td class="tdwhiteM"><%=j+1%></td>
                                            <td class="tdwhiteL0"><%=url%></td>
                                        </tr>
                                        <%
                                            }
                                        }
                                        if(max == 0)
                            {
                                        %>
                                        <tr>
                                    <td colspan="2" class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "02800") %><%--첨부파일이 존재하지 않습니다--%></td>
                                </tr>
                               <%
                            }
                               %>
                          </table>
                          </div>
                          <table border="0" cellspacing="0" cellpadding="0" width="655">
                            <tr>
                              <td class="space5"></td>
                            </tr>
                          </table>
                        </td>
                    </tr>
                    <!-- 2013.03.12 e3ps shkim add start -->
      <tr>
        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00907") %><%--관련 ECO--%></td>
        <td colspan="3" class="tdwhiteM0">
            <table border="0" cellspacing="0" cellpadding="0" width="655">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
          <div id="div_scroll3" style="width=655;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border" >
          <table width="100%" cellpadding="0" cellspacing="0">
            <tr class="headerLock3">
                <td width="40" class="tdgrayM">No</td>
                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00199") %><%--ECO번호--%></td>
                <td width="507" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
            </tr>
            <%

                          int count = 0;
                          String ecoOid = "";
                          while(ecoqr.hasMoreElements()) {
                              Object obj = ecoqr.nextElement();
                              KETMoldChangeOrder eco = (KETMoldChangeOrder)obj;
                              ecoOid = eco.getPersistInfo().getObjectIdentifier().toString();
                              count++;
                      %>
                      <tr>
                           <td class="tdwhiteM"><%=count %></td>
                           <td class="tdwhiteL"><a href="javascript:viewEco('<%=ecoOid%>');"><%=eco.getEcoId()%></a></td>
                           <td class="tdwhiteL0"><%=eco.getEcoName()%></td>
                      </tr>
                    <%
                          }
                      %>
          </table></div>
          <table border="0" cellspacing="0" cellpadding="0" width="655">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
        </td>
      </tr>
      <!-- 2013.03.12 e3ps shkim add end -->
                  </table>
              </td>
          </table>
        </td>
    </tr>
  </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="770" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</form>
</body>
<iframe name="download" align="center" width="0" height="0" border="0"></iframe>
</html>
