<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@page import = "e3ps.common.util.PropertiesUtil"%>
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

    String mode = request.getParameter("Mode");
    String checkMode = "0";
    if ( mode != null && mode != "" && mode.equals("single") ) {
        checkMode = "1";
    }
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ProjectIssueViewListPopupGrid' />
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
    <Cfg MaxTagHeight='370' MinTagHeight='370'/> <!-- Minimal height of the main tag to not shrink it too much -->

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
        <C Name='RowNum'     Width='40'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='IssueType'  Width='60'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Subject'    Width='200' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Urgency'    Width='60'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Importance' Width='60'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Owner'      Width='80'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate' Width='90'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Manager'    Width='80'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='IsDone'     Width='75'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='IssueOid'   Width='0'   Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            RowNum     ='No'
            IssueType  ='<%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%>'
            Subject    ='<%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%>'
            Urgency    ='<%=messageService.getString("e3ps.message.ket_message", "01158") %><%--긴급도--%>'
            Importance ='<%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%>'
            Owner      ='<%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%>'
            CreateDate ='<%=messageService.getString("e3ps.message.ket_message", "02522") %><%--제기일자--%>'
            Manager    ='<%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%>'
            IsDone     ='<%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%>'
            />

    <Def>
         <!-- Default row used for all created groups -->
    </Def>
    <Toolbar Visible="0"/>
</Grid>
