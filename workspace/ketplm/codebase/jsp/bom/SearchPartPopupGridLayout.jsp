<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ page import="e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

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

    String pagingSize = request.getParameter("Pagingsize");

    String multi = request.getParameter("Multi");
    String checkMode = ("true".equals(multi))?"0":"1";
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='SearchPartPopup' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->

    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort=''/>
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
    <Cfg SelectingSingle='<%=checkMode %>'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='410' MinHeight='410'/>
    <Cfg MaxTagHeight='330' MinTagHeight='330'/> <!-- Minimal height of the main tag to not shrink it too much -->

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

<%--
  int[] w = new int[5];
  w[0] = 60; // PartType
  w[1] = 95; // PartNumber
  w[3] = 50; // PartVersion
  w[4] = 60; // State
  w[2] = 475 - (w[0]+w[1]+w[3]+w[4]); // PartName
--%>
    <Cols>
        <C Name='PartType'    Width='100' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartNumber'  Width='100' Align='LEFT'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartName'    Width='280' Align='LEFT'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartVersion' Width='80' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='State'       Width='100' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>

        <C Name='Oid'         Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='DieNo'       Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='DieName'     Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='DieCnt'      Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='OidMaster'   Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='PartIteration' Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            PartType      ='<%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%>'
            PartNumber    ='<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>'
            PartName      ='<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>'
            PartVersion   ='<%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>'
            State         ='<%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%>'
            PartIteration ='부품이터레이션<%--부품이터레이션--%>'
            />
    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Cells="Empty1,Formula"
             Styles="2" Space="0" Kind="Topbar" Link="0"
             Empty1RelWidth="1" Empty1Type="Html"
             Formula="' <%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>: <b>'+count()+'</b> '"/>
</Grid>
