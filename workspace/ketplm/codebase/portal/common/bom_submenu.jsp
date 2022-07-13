<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import= "e3ps.common.util.KETObjectUtil" %>
<%@page import = "wt.org.*,
          wt.fc.PersistenceHelper,
          wt.session.SessionHelper,
          e3ps.common.util.CommonUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
  boolean isAdmin = CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators");
  boolean isDesignGroup = isAdmin || CommonUtil.isMember("자동차사업부_제품설계") || CommonUtil.isMember("전자사업부_제품설계") || CommonUtil.isMember("전자PMO");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="../../portal/js/common.js"></script>
<style type="text/css">
<!--
body {
  margin-left: 0px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
-->
</style>

<script language="javascript">
  // 일반 Editor 오픈 시 사용
    function openEditor()
    {
      window.open( '/plm/jsp/bom/BOMEditorLoading.jsp','BOMEditor','width=400,height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no' );
    }

    function openEditor2()
    {
    var url = "/plm/jsp/bom/BOMEditorLoading.jsp";
    openWindow(url,"BOMEditor","400","400","height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
    }
    
    
    function openEditor3()
    {
      window.open( '/plm/extcore/jsp/bom/BOMEditor.jsp','BOMEditor','width=1024,height=768,menubar=no,toolbar=no,location=no,scrollbars=no,status=no' );
    }

    ////////////////////////////////////////////////////////////////
    // 설계변경에서 BOM Editor 오픈 시 사용 [일반변경]
    //
    // v_oid : KETBomEcoHeader 객체 OID
    // v_ecoType : 일반변경(standard)
    ////////////////////////////////////////////////////////////////
  function openEditor(v_oid, v_ecoType){

    var url = "/plm/jsp/bom/BOMEditorLoading.jsp?oid=" + v_oid + "&ecoType=" + v_ecoType;
    openWindow(url,"BOMEditor","400","400","height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
  }

  ////////////////////////////////////////////////////////////////
    // 설계변경에서 BOM Editor 오픈 시 사용 [일괄변경]
    //
    // v_oid : 'multiple' 로 입력 -> 의미없음
    // v_ecoType : 일괄변경(multiple)
    // v_child : 변경대상 자부품번호
    // v_ecoNo : ECO Number
    // v_parent : 변경 모부품번호 (","를 구분자로 사용함)
    ////////////////////////////////////////////////////////////////
  function openEditor(v_oid, v_ecoType, v_child, v_ecoNo, v_parent){

    var url = "/plm/jsp/bom/BOMEditorLoading.jsp?oid=" + v_oid + "&ecoType=" + v_ecoType + "&child=" + v_child + "&ecoNo=" + v_ecoNo + "&parent=" + v_parent;
    openWindow(url,"BOMEditor","400","400","height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no");
  }
</script>

</head>
<body>
<tr>
  <table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/subh_11.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td valign="top">
              <!-- 부품 검색 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal//images/icon_2.gif">
                    <a href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/PartServlet?cmd=openSearch" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></a></td>
                </tr>
              </table>
              <!-- BOM 편집 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="javascript:window.open( '/plm/jsp/bom/BOMEditorLoading.jsp','BOMEditor','width=400,height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no' );" target="BOMEditor" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00090") %><%--BOM편집--%></a></td>
                </tr>
              </table>
              <!-- 부품채번관리 메뉴 -->
              <%if( isDesignGroup ) { %>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                  <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/part/CreatePart.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01598") %><%--부품채번관리--%></a></td>
                </tr>
              </table>
              <%} %>
<%
            if( KETObjectUtil.isAdmin() )
            {
%>
              <!-- 부품등록테스트 메뉴 >
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/CreatePartTest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01567") %><%--부품 등록 테스트--%></a></td>
                </tr>
              </table -->

              <!-- 금형부품 변경이력 검색 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/MoldChangePlanServlet?cmd=openSearch" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01065") %><%--금형부품 설계변경--%></a></td>
                </tr>
              </table>

              <!-- 자재 I/F 테스트 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/sapInterfaceTest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02418") %><%--자재 I/F 테스트--%></a></td>
                </tr>
              </table>

              <!-- Migration 테스트 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/MigrationTest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00321") %><%--Migration테스트--%></a></td>
                </tr>
              </table>
              
               <!-- EJS Treegrid -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="javascript:window.open( '/plm/extcore/jsp/bom/BOMEditor.jsp','_BOMEditor3','width=1152,height=768,menubar=no,toolbar=no,location=no,scrollbars=no,status=no' );" target="contName" class="leftmenu1">BOM 편집기</a></td>
                </tr>
              </table>

              <!-- EJS Treegrid -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/bom/EJSTreeGridTest.jsp" target="contName" class="leftmenu1">EJS Treegrid</a></td>
                </tr>
              </table>
              
               <!-- EJS Treegrid -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/extcore/extjs/maximgb/examples/client_editable/index.html" target="contName" class="leftmenu1">Editable Treegrid</a></td>
                </tr>
              </table>
              
                            
<%
            }
%>
            </td>
          </tr>
          <tr>
            <td height="118" align="center"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/banner_1.gif"></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
                <tr>
                 <td height="70" align="center" valign="top" background="../images/banner_2.gif"><table width="150" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td height="31">&nbsp;</td>
                    </tr>
                    <tr>
                      <td>(032)850-1369(PMS)</td>
                    </tr>
                    <tr>
                      <td>(032)850-1304(PDM)</td>
                    </tr>
                    <tr>
                      <td><%=messageService.getString("e3ps.message.ket_message", "00001") %><%--(032)850-1154(시스템)--%></td>
                    </tr>
                </table></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
    </tr>
  </table>
</body>
</html>
