<%@page import="e3ps.ecm.entity.KETProdChangeOrder"%>
<%@page import="e3ps.ecm.entity.KETProdChangeLink"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Untitled Document</title>
<%@ page import="wt.org.WTUser
                            ,wt.fc.QueryResult
                            ,wt.content.ContentHolder
                            ,wt.content.ContentHelper
                            ,wt.content.ContentRoleType
                            ,wt.content.ApplicationData
                            ,wt.content.ContentItem
                            ,wt.session.SessionHelper
                            ,wt.fc.PersistenceHelper"%>
<%@ page import= "java.util.Vector
                 ,java.net.URL
                 ,java.util.ArrayList
                 ,java.util.Hashtable" %>
<%@ page import= "e3ps.ecm.entity.KETProdECRIssueLink
                             ,e3ps.common.util.DateUtil
                             ,e3ps.common.util.StringUtil
                             ,e3ps.common.util.KETObjectUtil
                             ,e3ps.ecm.entity.KETProdECRPartLink
                             ,e3ps.common.util.CommonUtil
                             ,e3ps.common.content.ContentInfo
                             ,e3ps.common.content.ContentUtil
                             ,e3ps.common.code.NumberCodeHelper
                             ,e3ps.project.ProjectIssue
                             ,e3ps.ecm.beans.EcmUtil"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="prodEcr" class="e3ps.ecm.entity.KETProdChangeRequest" scope="request"/>
<jsp:useBean id="ecrHash" class="java.util.Hashtable" scope="request"/>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

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
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    WTUser owner = (WTUser)prodEcr.getCreator().getPrincipal();

    QueryResult issueQr = null;

    boolean isOwner = false;
    if( owner.equals( loginUser ) )
    {
        isOwner = true;
    }

    issueQr = PersistenceHelper.manager.navigate( prodEcr, "issue", KETProdECRIssueLink.class );

    ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
    partList = (ArrayList)ecrHash.get("partList");

    ContentHolder holder = ContentHelper.service.getContents( (ContentHolder)prodEcr );
    Vector attachFileList = ContentUtil.getSecondaryContents(holder);

    QueryResult ecoqr = PersistenceHelper.manager.navigate(prodEcr, "prodECO", KETProdChangeLink.class );

%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script type="text/javascript">
//관련 ECO 상세조회 팝업
function viewEco(oid) {
    var url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + oid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

function viewIssue(oid){
    var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
    openWindow2(url,"","750","320","scrollbars=no,resizable=no,top=200,left=250");
}
</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02542") %><%--제품 ECR 상세조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="790" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td width="780" valign="top"><table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
          </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td width="135" class="tdblueL">ECR No</td>
                <td width="265" class="tdwhiteL"><%=ecrHash.get("ecr_id").toString() %></td>
                <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
                <td width="280" class="tdwhiteL0"><%=ecrHash.get("dev_yn_desc").toString() %></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td colspan="3" class="tdwhiteL0"><%=prodEcr.getEcrName().toString() %></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01204") %><%--담당사업부--%></td>
                <td class="tdwhiteL"><%=ecrHash.get("division_desc").toString() %></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                <td class="tdwhiteL0"><a href="javascript:viewHistory('<%=ecrHash.get("ecr_oid").toString() %>');"><%=prodEcr.getLifeCycleState().getDisplay(messageService.getLocale())%></a></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                <td class="tdwhiteL"><%=prodEcr.getDeptName() %></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td class="tdwhiteL0"><%=prodEcr.getCreatorFullName() %></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td class="tdwhiteL"><%=ecrHash.get("create_date").toString()  %></td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                <td class="tdwhiteL0"><%=ecrHash.get("modify_date").toString() %></td>
              </tr>
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02000") %><%--승인자--%></td>
                <td class="tdwhiteL"><%=StringUtil.checkReplaceStr(EcmUtil.getLastApproverName( prodEcr ), "-")%>&nbsp;</td>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>
                <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(EcmUtil.getLastApproveDate( prodEcr ), "-")%>&nbsp;</td>
              </tr>
                    <tr>
                       <td class="tdblueL">Project No</td>
                      <td class="tdwhiteL"><a href="javascript:viewProjectPopup('<%=ecrHash.get("pjt_oid").toString() %>');"><%=StringUtil.checkReplaceStr(ecrHash.get("pjt_no").toString(), "-") %></a>&nbsp;</td>
                      <td class="tdblueL">Project Name</td>
                      <td class="tdwhiteL0"><%=StringUtil.checkReplaceStr(ecrHash.get("pjt_name").toString(), "-")%>&nbsp;</td>
                    </tr>
                  <tr>
                    <td  width="135" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00939") %><%--관련이슈--%></td>
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
                          <td width="145" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                          <td width="102" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                        </tr>
               <%
                       if( issueQr != null && issueQr.size() > 0 )
                       {
                           int issueCnt = issueQr.size();
                           ProjectIssue issue = null;
                           while( issueQr.hasMoreElements() )
                           {
                               issue = (ProjectIssue)issueQr.nextElement();
                       %>
                    <tr>
                      <td class="tdwhiteM"><%=issueCnt-- %></td>
                      <td class="tdwhiteL" title='<%=issue.getSubject()%>'><a href="javascript:viewIssue('<%=CommonUtil.getOIDString(issue) %>');"><div class="ellipsis" style="width:342;"><nobr><%=issue.getSubject() %></nobr></div></a></td>
                      <td class="tdwhiteM"><%=issue.getOwner().getFullName() %></td>
                      <td class="tdwhiteM0"><%=DateUtil.getTimeFormat( issue.getCreateDate(), "yyyy-MM-dd" ) %></td>
                    </tr>
                   <%
                           }
                       }
                       else
                       {
                   %>
                               <tr>
                                   <td colspan=4 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00918") %><%--관련 이슈가 없습니다--%></td>
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
              <tr>
                <td width="135" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01859") %><%--설계변경요청사유--%></td>
                <td colspan="3" class="tdwhiteL0"><%=NumberCodeHelper.manager.getNumberCode("PRODCHAGEREASON", prodEcr.getChangeReason()).getName() %><%if( prodEcr.getOtherChangeReasonDesc() != null ) {%>(<%=prodEcr.getOtherChangeReasonDesc()  %>)<%} %></td>
              </tr>
              <tr>
                <td width="135" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                <td colspan="3" class="tdwhiteL0">
                    <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                    <textarea name="ecr_desc" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field" readonly><%=StringUtil.checkNull(prodEcr.getEcrDesc()) %></textarea>
                  <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                </td>
              </tr>
              <tr>
                <td width="135" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01116") %><%--기대효과--%></td>
                <td colspan="3" class="tdwhiteL0">
                    <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                    <textarea name="ecr_effect" style='overflow: auto; width:655; height:50px; padding: 2;' class="txt_field"  readonly><%=StringUtil.checkNull(prodEcr.getExpectEffect()) %></textarea>
                  <table border="0" cellspacing="0" cellpadding="0" width="645">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                </td>
              </tr>
              <tr>
                <td width="135" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
                <td colspan="3" class="tdwhiteM0">
                    <table border="0" cellspacing="0" cellpadding="0" width="655">
                      <tr>
                        <td class="space5"></td>
                      </tr>
                    </table>
                    <div id="div_scroll2" style="width=655;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border" >
                  <table width="100%" cellpadding="0" cellspacing="0" >
                    <tr>
                      <td width="40" class="tdgrayM">No</td>
                      <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                      <td width="310" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                      <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                      <td width="148" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02193") %><%--요청사항--%></td>
                    </tr>
                   <%
                    if( partList != null )
                    {
                        int partCnt = partList.size();
                        Hashtable<String, String> part = null;
                        for( int pCnt=0; pCnt < partList.size(); pCnt++ )
                        {
                            part = partList.get( pCnt );
                    %>
                      <tr>
                        <td class="tdwhiteM"><%=partCnt-- %></td>
                        <td class="tdwhiteM"><a href="javascript:viewPart('<%=part.get("part_oid")%>');"><%=part.get("part_no") %></a></td>
                        <td class="tdwhiteL" title="<%=part.get("part_name") %>"><div class="ellipsis" style="width:302;"><nobr><%=part.get("part_name") %></nobr></div></td>
                        <td class="tdwhiteM"><%=part.get("ver")%></td>
                        <td class="tdwhiteL0"><%=part.get("req_comment") %>&nbsp;</td>
                      </tr>
                  <%
                        }
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
              <tr>
                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
                <td colspan="3" class="tdwhiteL0">
                          <table border="0" cellspacing="0" cellpadding="0" width="655">
                          <tr>
                            <td class="space5"></td>
                          </tr>
                        </table>
                        <div id="div_scroll3" style="width=655;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border" >
                          <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                            <td width="597" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
                          </tr>
                            <%
                            if( attachFileList.size() > 0 )
                            {
                        int attachCnt = attachFileList.size();
                        ContentInfo cntInfo = null;
                        ContentItem ctf = null;
                        String appDataOid = "";
                        String url = "";
                              for( int fileCnt=0; fileCnt < attachFileList.size() ; fileCnt++ )
                              {
                              cntInfo = (ContentInfo)attachFileList.elementAt(fileCnt);
                              ctf=(ContentItem)CommonUtil.getObject(cntInfo.getContentOid());
                                            appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
                                            //url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                            url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
                                            url = "<a href=" + url + " target='_blank'>" + cntInfo.getName() + "</a>&nbsp;(&nbsp;" + cntInfo.getFileSize() + "&nbsp;)";
                              %>
                                  <tr>
                                       <td class="tdwhiteM"><%=attachCnt-- %></td>
                                       <td class="tdwhiteL0"><%=url%></td>
                                  </tr>
                             <%
                              }
                            }
                            else
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
                      <td colspan="3" class="tdwhiteL0">
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
                        <td width="507" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
                      </tr>
                      <%

                          int count = 0;
                          String ecoOid = "";
                          while(ecoqr.hasMoreElements()) {
                              Object obj = ecoqr.nextElement();
                              KETProdChangeOrder eco = (KETProdChangeOrder)obj;
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

                </table>
            </div>
                <table border="0" cellspacing="0" cellpadding="0" width="655">
                    <tr>
                          <td class="space5"></td>
                    </tr>
                  </table>
          </td>
        </tr>
        <!-- 2013.03.12 e3ps shkim add end -->
          </table></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="790" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
