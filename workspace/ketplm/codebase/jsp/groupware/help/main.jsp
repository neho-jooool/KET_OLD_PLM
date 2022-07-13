<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.groupware.board.ImproveBoardData"%>
<%@page import="e3ps.groupware.board.ImproveBoard"%>
<%@page import="e3ps.groupware.board.dao.ImproveBoardDao"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ApplicationData"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.groupware.board.ManualBoardData"%>
<%@page import="e3ps.groupware.board.ManualBoard"%>
<%@page import="e3ps.groupware.board.BoardData"%>
<%@page import="e3ps.groupware.board.Board"%>
<%@page import="e3ps.groupware.board.beans.HelpBoardUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.groupware.board.QnaBoardData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.groupware.board.QnaBoard"%>
<%@page import="e3ps.groupware.board.dao.QnaBoardDao"%>
<%@page import="e3ps.common.util.KETQueryUtil"%>
<%@page import="e3ps.common.util.SqlRowMap"%>
<%@page import="java.util.List"%>
<%@page import="e3ps.common.util.PlmDBUtil"%>
<%@page import="java.sql.Connection"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>

<html>
<head>
<title>Help Desk mainPage</title>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language="javascript" src="/plm/portal/js/e3psScroller.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/jsp/wfm/viewPBO.js"></SCRIPT>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
function openPopup(url) {
//   var top_position = (screen.height) ? (screen.height-230)/2 : 0; //팝업 위치 세로
//   var left_position = (screen.width) ? (screen.width-400)/2 : 0; //팝업 위치 가로
//   var settings = "width=400, height=230, top=" + top_position + ", left=" + left_position;

   openWindow2(url, 'helpDeskPopup', 815, 480);
}
//-->
</script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
body, td, th {
  color: #666;
}
-->
</style>
</head>

<%
  Connection conn = null;
  try {
    conn = PlmDBUtil.getConnection();

    String query = null;
    List<SqlRowMap> hbList = null;
    int[] w = new int[10];
    String newIcon = "&nbsp;<img src='" + e3ps.common.web.E3PSWebFlag.ICONURL + "/i_new.gif'/>";
%>
<body>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" valign="top" ><table border="0" cellspacing="0" cellpadding="0">
        <tr>

          <!-- [START] Q&A ======================================================================================================== -->
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20">&nbsp;</td>
              </tr>
              <tr>
                <td align="center" valign="top">
                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/groupwareTitleBarS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 40px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--Q&amp;A--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/groupwareTitleBarE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 270px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/jsp/groupware/help/menu.jsp', '/plm/servlet/e3ps/ManageQnaBoardServlet');" target="_self">
                                <img src="/plm/portal/images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
<%
  w[1] = 60; // 상태
    w[2] = 70; // 작성자
    w[3] = 60; // 작성일
    w[4] = 40; // 조회
    w[0] = 350 - (w[1]+w[2]+w[3]+w[4]); // 제목
%>
                  <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="center" valign="middle" background="/plm/portal/images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr class="title_b">
                          <td class="tdblue2_L" style="width:<%=w[0]%>;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                          <td class="tdblue2_R" style="width:<%=w[1]%>;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                          <td class="tdblue2_R" style="width:<%=w[2]%>;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                          <td class="tdblue2_R" style="width:<%=w[3]%>;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                          <td class="tdblue2_R" style="width:<%=w[4]%>;"><%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="350" height="110" border="0" cellspacing="0" cellpadding="0">
                    <tr><td valign="top">

<%
  query = "select * from (select classnamea2a2||':'||ida2a2 as hbOid from QnaBoard where classnamekeyparentreference is null order by createstampa2 desc) where rownum <= 5";
    hbList = KETQueryUtil.getSqlResultList(query, conn);
    if (hbList.size() > 0) {
      QnaBoardDao dao = new QnaBoardDao(conn);

      for (SqlRowMap record : hbList) {
        String hbOid = record.getString("hbOid");
        QnaBoard board = (QnaBoard) CommonUtil.getObject(hbOid);
        if (board != null) {

          QnaBoardData data = new QnaBoardData(board,0);
          String title = data.getTitle();
          String stateStr = data.getStateStr();
          String writer = data.getWriter();
          Timestamp createStamp = board.getPersistInfo().getCreateStamp();
          String writeDate = new SimpleDateFormat("yy-MM-dd").format(createStamp);
          int readCount = data.getReadCount();
          boolean isNew = HelpBoardUtil.isNewDoc(createStamp);

          String viewPage;
          List<String> clist = dao.getChildOidList(hbOid);
          if (clist.size() > 0) {
            viewPage = "/plm/jsp/groupware/help/qna/ViewQnaBoard_a.jsp?oid=" + clist.get(0);
          }
          else {
            viewPage = "/plm/jsp/groupware/help/qna/ViewQnaBoard_q.jsp?oid=" + hbOid;
          }
          viewPage = viewPage + "&from=main";
%>
                      <table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td class="tdblue3_R" style="width:<%=w[0]%>;" title="<%=title%>">
                              <nobr style="text-overflow:ellipsis;overflow:hidden;width:<%=w[0]%>;"><a href="javascript:openPopup('<%=viewPage%>');"><%=title%></a><%=(isNew)?newIcon:""%></nobr>
                          </td>
                          <td class="tdblue3_L" style="width:<%=w[1]%>;">
                            <nobr style="text-overflow:ellipsis;overflow:hidden;width:<%=w[1]%>;"><%=stateStr%></nobr></td>
                          <td class="tdblue3_L" style="width:<%=w[2]%>;"><%=writer%></td>
                          <td class="tdblue3_L" style="width:<%=w[3]%>;"><%=writeDate%></td>
                          <td class="tdblue3_L" style="width:<%=w[4]%>;"><%=readCount%></td>
                        </tr>
                      </table>
<%
  }
      }
    }
    else {
%>
                      <table width="350"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01327") %><%--등록된 글이 없습니다--%></font></td>
                        </tr>
                      </table>
<%
  }
%>

                    </td></tr></table>
                  <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/line_0.gif"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table></td>
          <!-- [END] Q&A ======================================================================================================== -->

          <td width="12">&nbsp;</td>

          <!-- [START] FAQ ======================================================================================================== -->
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="20">&nbsp;</td>
            </tr>
            <tr>
              <td align="center" valign="top">
                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td background="/plm/portal/images/groupwareTitleBarS.png" align="left">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width: 5px;"></td>
                      <td style="width: 40px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00238") %><%--FAQ--%></td>
                    </tr>
                    </table>
                  </td>
                  <td background="/plm/portal/images/groupwareTitleBarE.png" align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="width: 270px; text-align: right;">
                        <a href="javascript:parent.movePaage('/plm/jsp/groupware/help/menu.jsp', '/plm/jsp/groupware/help/faq/SearchBoard.jsp');" target="_self">
                        <img src="/plm/portal/images/btn_more.gif" border="0">
                        </a>
                      </td>
                      <td width="10"></td>
                    </tr>
                    </table>
                  </td>
                </tr>
                </table>
<%
  w[1] = 60; // 작성일
    w[2] = 40; // 조회
    w[0] = 350 - (w[1]+w[2]); // 제목
%>
                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="/plm/portal/images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td class="tdblue2_L" style="width:<%=w[0]%>;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td class="tdblue2_R" style="width:<%=w[1]%>;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                        <td class="tdblue2_R" style="width:<%=w[2]%>;"><%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">

<%
  query = "select * from (select classnamea2a2||':'||ida2a2 as hbOid from Board order by createstampa2 desc) where rownum <= 5";
    hbList = KETQueryUtil.getSqlResultList(query, conn);
    if (hbList.size() > 0) {
      for (SqlRowMap record : hbList) {
        String hbOid = record.getString("hbOid");
        Board board = (Board) CommonUtil.getObject(hbOid);
        if (board != null) {
          BoardData data = new BoardData(board,0);
          String title = data.title;
          Timestamp createStamp = board.getPersistInfo().getCreateStamp();
          String writeDate = new SimpleDateFormat("yy-MM-dd").format(createStamp);
          int readCount = data.readCount;
          boolean isNew = HelpBoardUtil.isNewDoc(createStamp);

          String viewPage = "/plm/jsp/groupware/help/faq/ViewBoard.jsp?oid=" + hbOid;
          viewPage = viewPage + "&from=main";
%>
                      <table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td class="tdblue3_R" style="width:<%=w[0]%>;" title="<%=title%>">
                              <nobr style="text-overflow:ellipsis;overflow:hidden;width:<%=w[0]%>;"><a href="javascript:openPopup('<%=viewPage%>');"><%=title%></a><%=(isNew)?newIcon:""%></nobr>
                          </td>
                          <td class="tdblue3_L" style="width:<%=w[1]%>;"><%=writeDate%></td>
                          <td class="tdblue3_L" style="width:<%=w[2]%>;"><%=readCount%></td>
                        </tr>
                      </table>
<%
  }
      }
    }
    else {
%>
                      <table width="350"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01327") %><%--등록된 글이 없습니다--%></font></td>
                        </tr>
                      </table>
<%
  }
%>

                </td></tr></table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="/plm/portal/images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
          <!-- [END] FAQ ======================================================================================================== -->

        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>

          <!-- [START] PLM Manual ======================================================================================================== -->
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" valign="top">
                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/groupwareTitleBarS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="width: 100px; font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "00364") %><%--PLM Manual--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/groupwareTitleBarE.png" align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 210px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/jsp/groupware/help/menu.jsp', '/plm/servlet/e3ps/ManageManualBoardServlet');" target="_self">
                                <img src="/plm/portal/images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
<%
  w[1] = 50; // 파일
    w[2] = 40; // 조회
    w[0] = 350 - (w[1]+w[2]); // 제목
%>
                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="/plm/portal/images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td class="tdblue2_L" style="width:<%=w[0]%>;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td class="tdblue2_R" style="width:<%=w[1]%>;"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
                        <td class="tdblue2_R" style="width:<%=w[2]%>;"><%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td></td>
                  </tr>
                </table>
                <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">

<%
  query = "select * from (select classnamea2a2||':'||ida2a2 as hbOid from ManualBoard order by createstampa2 desc) where rownum <= 5";
    hbList = KETQueryUtil.getSqlResultList(query, conn);
    if (hbList.size() > 0) {
      for (SqlRowMap record : hbList) {
        String hbOid = record.getString("hbOid");
        ManualBoard board = (ManualBoard) CommonUtil.getObject(hbOid);
        if (board != null) {
          ManualBoardData data = new ManualBoardData(board,0);
          String title = data.getTitle();
          int readCount = data.getReadCount();
          boolean isNew = HelpBoardUtil.isNewDoc(board.getPersistInfo().getCreateStamp());

          ContentHolder holder = ContentHelper.service.getContents(board);
          Vector vec = ContentHelper.getContentList(holder);
          String attach;
          String attachName;
          if (vec.size() > 0) {
            ApplicationData appData = (ApplicationData)vec.get(0); // 첫번째 하나만..
            String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
            //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
            String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
            attach ="<a href='" + url + "' target='_blank'>" + e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData) + "</a>";
            attachName = appData.getFileName();
          }
          else {
            attach = "";
            attachName = "";
          }

          String viewPage = "/plm/jsp/groupware/help/manual/ViewManualBoard.jsp?oid=" + hbOid;
          viewPage = viewPage + "&from=main";
%>
                      <table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td class="tdblue3_R" style="width:<%=w[0]%>;" title="<%=title%>">
                              <nobr style="text-overflow:ellipsis;overflow:hidden;width:<%=w[0]%>;"><a href="javascript:openPopup('<%=viewPage%>');"><%=title%></a><%=(isNew)?newIcon:""%></nobr>
                          </td>
                          <td class="tdblue3_L" style="width:<%=w[1]%>;" title="<%=attachName%>"><%=attach%></td>
                          <td class="tdblue3_L" style="width:<%=w[2]%>;"><%=readCount%></td>
                        </tr>
                      </table>
<%
  }
      }
    }
    else {
%>
                      <table width="350"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01327") %><%--등록된 글이 없습니다--%></font></td>
                        </tr>
                      </table>
<%
  }
%>

                </td></tr>
                </table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="/plm/portal/images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
          <!-- [END] PLM Manual ======================================================================================================== -->

          <td width="12">&nbsp;</td>

          <!-- [START] 시스템 개선 요청 ======================================================================================================== -->
          <td valign="top"><table width="384" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" valign="top">
                <table width="370" height="56" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/plm/portal/images/groupwareTitleBarS.png" align="left">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 5px;"></td>
                            <td style="font-family: 'Malgun Gothic'; font-size: 16px; font-weight: bold; padding-bottom:7px; color:#000"><%=messageService.getString("e3ps.message.ket_message", "02006") %><%--시스템 개선 요청--%></td>
                        </tr>
                        </table>
                    </td>
                    <td background="/plm/portal/images/groupwareTitleBarE.png" align="right" >
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 120px; text-align: right;">
                                <a href="javascript:parent.movePaage('/plm/jsp/groupware/help/menu.jsp', '/plm/servlet/e3ps/ManageImproveBoardServlet');" target="_self">
                                <img src="/plm/portal/images/btn_more.gif" border="0">
                                </a>
                            </td>
                            <td width="10"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
<%
  w[1] = 50; // 상태
    w[2] = 70; // 작성자
    w[3] = 60; // 작성일
    w[4] = 40; // 조회
    w[0] = 350 - (w[1]+w[2]+w[3]+w[4]); // 제목
%>
                <table width="370" height="30" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td align="center" valign="middle" background="/plm/portal/images/barbg.gif"><table width="350" border="0" cellspacing="0" cellpadding="0">
                      <tr class="title_b">
                        <td class="tdblue2_L" style="width:<%=w[0]%>;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                        <td class="tdblue2_R" style="width:<%=w[1]%>;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdblue2_R" style="width:<%=w[2]%>;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td class="tdblue2_R" style="width:<%=w[3]%>;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                        <td class="tdblue2_R" style="width:<%=w[4]%>;"><%=messageService.getString("e3ps.message.ket_message", "02652") %><%--조회--%></td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                <table width="370" height="7" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td></td>
                  </tr>
                </table>
                <table width="350"  height="110" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <td valign="top">

<%
  query = "select * from (select classnamea2a2||':'||ida2a2 as hbOid from ImproveBoard where classnamekeyparentreference is null order by createstampa2 desc) where rownum <= 5";
    hbList = KETQueryUtil.getSqlResultList(query, conn);
    if (hbList.size() > 0) {
      ImproveBoardDao dao = new ImproveBoardDao(conn);

      for (SqlRowMap record : hbList) {
        String hbOid = record.getString("hbOid");
        ImproveBoard board = (ImproveBoard) CommonUtil.getObject(hbOid);
        if (board != null) {
          ImproveBoardData data = new ImproveBoardData(board,0);
          String title = data.getTitle();
          String stateStr = data.getStateStr();
          String writer = data.getWriter();
          Timestamp createStamp = board.getPersistInfo().getCreateStamp();
          String writeDate = new SimpleDateFormat("yy-MM-dd").format(createStamp);
          int readCount = data.getReadCount();
          boolean isNew = HelpBoardUtil.isNewDoc(createStamp);

          String viewPage;
          List<String> clist = dao.getChildOidList(hbOid);
          if (clist.size() > 0) {
            viewPage = "/plm/jsp/groupware/help/improve/ViewImproveBoard_a.jsp?oid=" + clist.get(0);
          }
          else {
            viewPage = "/plm/jsp/groupware/help/improve/ViewImproveBoard_q.jsp?oid=" + hbOid;
          }
          viewPage = viewPage + "&from=main";
%>
                      <table width="350" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td class="tdblue3_R" style="width:<%=w[0] %>;" title="<%=title %>">
                              <nobr style="text-overflow:ellipsis;overflow:hidden;width:<%=w[0] %>;"><a href="javascript:openPopup('<%=viewPage %>');"><%=title %></a><%=(isNew)?newIcon:"" %></nobr>
                          </td>
                          <td class="tdblue3_L" style="width:<%=w[1] %>;"><%=stateStr %></td>
                          <td class="tdblue3_L" style="width:<%=w[2] %>;"><%=writer %></td>
                          <td class="tdblue3_L" style="width:<%=w[3] %>;"><%=writeDate %></td>
                          <td class="tdblue3_L" style="width:<%=w[4] %>;"><%=readCount %></td>
                        </tr>
                      </table>
<%
        }
      }
    }
    else {
%>
                      <table width="350"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td colspan="3" align="center"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01327") %><%--등록된 글이 없습니다--%></font></td>
                        </tr>
                      </table>
<%
    }
%>

                  </td></tr>
                </table>
                <table width="370" height="17" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td background="/plm/portal/images/line_0.gif"></td>
                  </tr>
                </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
          <!-- [END] 시스템 개선 요청 ======================================================================================================== -->

        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</body>
</html>
<%
  }
  finally {
    PlmDBUtil.close(conn);
  }
%>
