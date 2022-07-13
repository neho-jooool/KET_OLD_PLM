<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@page import = "e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->
<%
    /*-----------------------------------------------------------------------------------------------------------------
    ! Support file only, run Grid.html instead !
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String gridStyle     = PropertiesUtil.getSearchGridStyle();
    String cookiesType   = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort       = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons     = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();

    String parentOid  = request.getParameter("ParentOid");
    String numberCodeType  = request.getParameter("NumberCodeType");

    String mode =  request.getParameter("Mode");
    String checkMode = "1";

    if( mode != null && mode.equals("multi") ){
      checkMode="0";
    }

    //(parentOid.length() == 0) && numberCodeType.equals("CUSTOMEREVENT")
    Kogger.debug("NumberCodeMgtListPopupGridLayout", null, null, numberCodeType);
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='numberCodeMgtListPopupGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->

    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='Name'/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort %>'/>
    <Cfg SortIcons='<%=sortIcons %>'/>

    <!-- 행 삭제 관련 설졍 -->
    <Cfg Deleting='0'/>
    <Cfg ShowDeleted='1'/> <!-- This example hides deleted row instead of coloring them red -->

    <!--  셀 병합 관련 설정 -->
    <Cfg DynamicSpan='0'/>

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting='1' SelectingCells='0'/>

    <!-- 멀티선택 설정   -->
    <!-- 7-05  남현승   -->
    <Cfg SelectingSingle='<%=checkMode %>'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='1'/>
    <Cfg MaxHeight='1' MinHeight='1'/>

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1'/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates'/>

    <!-- 그리드 기본 스타일 설정 -->
    <Cfg Style='<%=gridStyle %>'/>

    <!-- 기타 설정 -->
    <Cfg NoPager='1'/>
    <Cfg UsePrefix='1'/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='<%=alternateType %>'/> <!-- Custom style setting, every third row will have different color -->

    <!-- Panel 설정 -->
    <Panel Width="21" CanHide="0" />

    <Cols>
        <C Name='oid'          Width='10' Align='Center' CanSort='1' CanEdit='0' Visible='0' Hidden='1' Type='Text'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='codelong'     Width='5'  Align='Center' CanSort='1' CanEdit='0' Visible='0' Hidden='1' Type='Text'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>

<%if( parentOid != null && !parentOid.equals("null") && numberCodeType.equals("CUSTOMEREVENT") ){ %>
        <C Name='Code'         Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Name'         Width='225'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Description'  Width='200' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Division'     Width='100' RelWidth='50' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
<%   }else{ %>
        <C Name='Code'         Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Name'         Width='225'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Description'  Width='300' RelWidth='50' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
<%} %>
  </Cols>
    <Header CanDelete='0'   Align='Center'
            Code       ='<%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%>'
            Name       ='<%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%>'
            Description='<%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%>'
            Division   ='<%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%>'
            />

    <Def>
         <!-- Default row used for all created groups -->
    </Def>
    <Toolbar Visible="0"/>
</Grid>
