<%--
/**
* @(#)   pageControl.jsp
* Copyright (c) e3ps. All rights reserverd
*
* @version 1.00
* @since jdk 1.3
* @author 최승환 skyprda@e3ps.com
* @desc    JSP의 페이지를 관리해준다.
*        2005-01-20 날 수정함  : 조성옥 ( Post 방식 추가 )
*/
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String cmd = request.getParameter("cmd");

    String type = request.getParameter("type");      // 표현 방식 type:s :simple
    if ( type == null ) type = "normal";          //  표현 방식 type:n :normal

    int totalCount = Integer.parseInt(request.getParameter("totalCount"));    // 총 게시물 수
    int totalPage = Integer.parseInt(request.getParameter("totalPage"));      // 총 페이지

    int currentPage = Integer.parseInt(request.getParameter("currentPage"));  // 현재 페이지
    int startPage = Integer.parseInt(request.getParameter("startPage"));      // 시작 페이지
    int endPage = Integer.parseInt(request.getParameter("endPage"));        // 마지막 페이지

    String href  = request.getParameter("href");        // 페이징 URL
    String param = request.getParameter("param");      // 페이징 URL 파라미터
    String sessionid = request.getParameter("sessionid");  // 페이징 세션 아이디

    String isPost = request.getParameter("isPost");      // Post/Get 방식 여부

    boolean isPostMethod = false;
    if ( isPost != null && isPost.trim().equals("true") ) isPostMethod = true;
    isPostMethod = true;

    StringBuffer firstPageHrefStr = new StringBuffer();
    if ( currentPage >= 1 && totalPage > 1) {
        if ( isPostMethod ) {
            firstPageHrefStr.append("<a  href='javascript:gotoPage(1);'");
        } else {
            firstPageHrefStr.append("<a  href='");
            firstPageHrefStr.append(href);
            firstPageHrefStr.append("?sessionid="+sessionid);
            firstPageHrefStr.append("&page=1");
            if ( param != null ) {
                if ( !param.startsWith("&") ) firstPageHrefStr.append("&");
                firstPageHrefStr.append(param);;
            }
            firstPageHrefStr.append("'");
        }
        firstPageHrefStr.append(" class='small'><img src='/plm/portal/images/btn_arrow4.gif' style='border:0'></a>");
    }

    StringBuffer lastPageHrefStr = new StringBuffer();
    if ( href != null ) {
        if ( currentPage >= 1 && totalPage > 1 ) {
            if ( isPostMethod ) {
                lastPageHrefStr.append("<a  href='javascript:gotoPage("+totalPage+");'");
            } else {
                lastPageHrefStr.append("<a  href='");
                lastPageHrefStr.append(href);
                lastPageHrefStr.append("?sessionid="+sessionid);
                lastPageHrefStr.append("&page="+totalPage);
                if ( param != null ) {
                    if ( !param.startsWith("&") ) lastPageHrefStr.append("&");
                    lastPageHrefStr.append(param);;
                }
                lastPageHrefStr.append("'");
            }
            lastPageHrefStr.append(" class='small'><img src='/plm/portal/images/btn_arrow2.gif' style='border:0'></a>");
        }
    }

    StringBuffer prePageHrefStr = new StringBuffer();
    if ( href != null ) {
        if ( startPage > 1 && totalPage > 1 ) {
            if ( isPostMethod ) {
                prePageHrefStr.append("<a  href='javascript:gotoPage("+(startPage-1)+");'");
            } else {
                prePageHrefStr.append("<a  href='");
                prePageHrefStr.append(href);
                prePageHrefStr.append("?sessionid="+sessionid);
                prePageHrefStr.append("&page="+(startPage-1));
                if ( param != null ) {
                    if ( !param.startsWith("&") ) prePageHrefStr.append("&");
                    prePageHrefStr.append(param);;
                }
                prePageHrefStr.append("'");
            }
            prePageHrefStr.append(" class='smallblue'><img src='/plm/portal/images/btn_arrow3.gif' style='border:0'></a>");
        }
    }

    StringBuffer postPageHrefStr = new StringBuffer();
    if ( href != null ) {
        if ( endPage < totalPage && totalPage > 1 ) {
            if ( isPostMethod ) {
                postPageHrefStr.append("<a  href='javascript:gotoPage("+(endPage+1)+");'");
            } else {
                postPageHrefStr.append("<a  href='");
                postPageHrefStr.append(href);
                postPageHrefStr.append("?sessionid="+sessionid);
                postPageHrefStr.append("&page="+(endPage+1));
                if ( param != null ) {
                    if ( !param.startsWith("&") ) postPageHrefStr.append("&");
                    postPageHrefStr.append(param);;
                }
                postPageHrefStr.append("'");
            }
            postPageHrefStr.append(" class='smallblue'><img src='/plm/portal/images/btn_arrow1.gif' style='border:0'></a>");
        }
    }

    StringBuffer currentPageHrefStr = new StringBuffer();
    if ( href != null ) {
        if ( startPage >= 1 && totalPage > 1 ) {
            boolean endTag = false;

            for ( int i = startPage ; i <= endPage ; i++ ) {
//        currentPageHrefStr.append("<td style='padding:2807;cursor:hand' onMouseOver='this.style.background=#ECECEC' OnMouseOut=this.style.background=''' class='nav_on'><font color=777777>");
                endTag = true;
                if ( i == currentPage ) {
                    currentPageHrefStr.append("<td style='padding:2 8 0 7;' class='nav_on'><font color=777777>");
                    currentPageHrefStr.append("<b><font color=006699>"+i+"</font></b>");
                    continue;
                } else {
                    currentPageHrefStr.append("<td style='padding:2 8 0 7;cursor:hand' class='nav_on'><font color=777777>");
                    if ( isPostMethod ) {
                        currentPageHrefStr.append("<a  href='javascript:gotoPage("+i+");'");
                    } else {
                        currentPageHrefStr.append("<a  href='");
                        currentPageHrefStr.append(href);
                        currentPageHrefStr.append("?sessionid="+sessionid);
                        currentPageHrefStr.append("&page="+i);
                        if ( param != null ) {
                            if ( !param.startsWith("&") ) currentPageHrefStr.append("&");
                            currentPageHrefStr.append(param);;
                        }
                        currentPageHrefStr.append("'");
                    }
                    currentPageHrefStr.append(">"+i+"</a>");
                }
                currentPageHrefStr.append("</font></td>");
            }
            //if ( endTag ) currentPageHrefStr.append("|");

        }
    }

    StringBuffer pageStr = new StringBuffer();
    pageStr.append("<table border='0' cellspacing='0' cellpadding='0' width='100%'>");
    pageStr.append("  <tr>");
    if(type.trim().equals("normal")){
        pageStr.append("    <td class='small' align='left'>(" + messageService.getString("e3ps.message.ket_message", "02499")/*전체페이지*/ + " : " + totalPage + " )</td>");
    }
    pageStr.append("    <td>");
    pageStr.append("      <table border='0' align='center' cellpadding='0' cellspacing='0'>");
    pageStr.append("        <tr>");
    pageStr.append("          <td width='40' align='center'>" + firstPageHrefStr + "</td>");
    pageStr.append("          <td width='1' bgcolor='#dddddd'></td>");
    if( prePageHrefStr.length() > 0 ) {
        pageStr.append("          <td width='60' class='quick' align='center'>" + prePageHrefStr + "</td>");
        pageStr.append("          <td width='1' bgcolor='#dddddd'></td>");
    }
    pageStr.append("          " + currentPageHrefStr + "");
    pageStr.append("          <td width='1' bgcolor='#dddddd'></td>");
    if( postPageHrefStr.length() > 0 ) {
        pageStr.append("          <td width='60' align='center'>" + postPageHrefStr + "</td>");
        pageStr.append("          <td width='1' bgcolor='#dddddd'></td>");
    }
    pageStr.append("          <td width='45' align='center'>" + lastPageHrefStr + "</td>");
    pageStr.append("        </tr>");
    pageStr.append("      </table>");
    pageStr.append("    </td>");
    if(type.trim().equals("normal")){
        pageStr.append("    <td class='small' align='right'>(" + messageService.getString("e3ps.message.ket_message", "02496")/*전체개수*/ + " : " + totalCount + ")</td>");
    }
    pageStr.append("  </tr>");
    pageStr.append("</table>");
%>
<!-- 페이지 설정 끝 //-->
<script language="javascript">
    <%=param%>enabledAllBtn();
    <%=param%>hideProcessing();
    <%=param%>document.forms[0].totalCount.value = "<%=totalCount%>";
    var targetTable = <%=param%>document.getElementById("pageControlTable");
    if(targetTable.rows.length >= 1) {
        targetTable.deleteRow(0);
    }
    var tableRows = targetTable.rows.length;
    var newTr = targetTable.insertRow(tableRows);
    var newTd = newTr.insertCell(newTr.cells.length);
    newTd.innerHTML = "<%=pageStr.toString()%>";
</script>
