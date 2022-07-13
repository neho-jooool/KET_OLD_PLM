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

    String perPage = request.getParameter("perPage");

    if(perPage == null){
        perPage = "";
    }


    boolean isPostMethod = false;
    if ( isPost != null && isPost.trim().equals("true") ) isPostMethod = true;

    StringBuffer firstPageHrefStr = new StringBuffer();
    if ( currentPage >= 1 && totalPage > 1) {
        if ( isPostMethod ) {
            firstPageHrefStr.append("<a  href=javascript:gotoPage('1','");
            firstPageHrefStr.append(href);
            firstPageHrefStr.append("')");
        } else {
            firstPageHrefStr.append("<a  href='");
            firstPageHrefStr.append(href);
            firstPageHrefStr.append("?sessionid="+sessionid);
            firstPageHrefStr.append("&page=1");
            firstPageHrefStr.append("&perPage=" + perPage);
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
                lastPageHrefStr.append("<a  href=javascript:gotoPage('"+totalPage+"','");
                lastPageHrefStr.append(href);
                lastPageHrefStr.append("')");
            } else {
                lastPageHrefStr.append("<a  href='");
                lastPageHrefStr.append(href);
                lastPageHrefStr.append("?sessionid="+sessionid);
                lastPageHrefStr.append("&page="+totalPage);
                lastPageHrefStr.append("&perPage=" + perPage);
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
                prePageHrefStr.append("<a  href=javascript:gotoPage('"+(startPage-1)+"','");
                prePageHrefStr.append(href);
                prePageHrefStr.append("')");
            } else {
                prePageHrefStr.append("<a  href='");
                prePageHrefStr.append(href);
                prePageHrefStr.append("?sessionid="+sessionid);
                prePageHrefStr.append("&page="+(startPage-1));
                prePageHrefStr.append("&perPage=" + perPage);
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
                postPageHrefStr.append("<a  href=javascript:gotoPage('"+(endPage+1)+"','");
                postPageHrefStr.append(href);
                postPageHrefStr.append("')");
            } else {
                postPageHrefStr.append("<a  href='");
                postPageHrefStr.append(href);
                postPageHrefStr.append("?sessionid="+sessionid);
                postPageHrefStr.append("&page="+(endPage+1));
                postPageHrefStr.append("&perPage=" + perPage);
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
                currentPageHrefStr.append("<td style=\"padding:2 8 0 7;cursor:hand\" onMouseOver=\"this.style.background='#ECECEC'\" OnMouseOut=\"this.style.background=''\" class=\"nav_on\"><font color=777777>");

                endTag = true;
                //currentPageHrefStr.append("|");
                if ( i == currentPage ) {
                    currentPageHrefStr.append("<b><font color=006699>"+i+"</font></b>");
                    continue;
                } else {
                    if ( isPostMethod ) {
                        currentPageHrefStr.append("<a  href=javascript:gotoPage('"+i+"','");
                        currentPageHrefStr.append(href);
                        currentPageHrefStr.append("')");
                    } else {
                        currentPageHrefStr.append("<a  href='");
                        currentPageHrefStr.append(href);
                        currentPageHrefStr.append("?sessionid="+sessionid);
                        currentPageHrefStr.append("&page="+i);
                        currentPageHrefStr.append("&perPage=" + perPage);
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

    if ( isPostMethod ) {
%>
<input type='hidden' name='sessionid' value='<%=sessionid%>'>
<input type='hidden' name='page' value='<%=currentPage%>'>
<input type='hidden' name='cmd' value='<%=cmd%>'>
<input type='hidden' name='perPage' value='<%=perPage%>'>
<script language='javascript'>
function gotoPage(pageno,param){
    showProcessing();
    document.forms[0].page.value = pageno;
    document.forms[0].action = param;
    document.forms[0].submit();
}
</script>
<%  }  %>
<%@include file="/jsp/common/processing.html"%>
<!-- 페이지 설정 시작 //-->
<!--table border="0" cellpadding="2" cellspacing="1" width="100%"  align=center-->
<table border="0" cellspacing="0" cellpadding="0" width="450">
    <tr>
        <%if(type.trim().equals("normal")){%><td class="small" align="left">(전체페이지 : <%=totalPage%> )</td><%}%>
        <td>
            <table border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <!--td height=20 align="center"-->
                    <td width="40" align="center"><%=firstPageHrefStr%></td>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%  if( prePageHrefStr.length() > 0 ) {%>
                    <td width="40" class="quick" align="center"><%=prePageHrefStr%></td>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%  }%>
                    <%=currentPageHrefStr%>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%  if( postPageHrefStr.length() > 0 ) {%>
                    <td width="40" align="center"><%=postPageHrefStr%></td>
                    <td width="1" bgcolor="#dddddd"></td>
                    <%  }%>
                    <td width="40"align="center"><%=lastPageHrefStr%></td>
                    <!--/td-->
                </tr>
            </table>
        </td>
        <%if(type.trim().equals("normal")){%><td class="small" align="right">(전체개수 : <%=totalCount%>)</td><%}%>
    </tr>
</table>
<!-- 페이지 설정 끝 //-->
