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

    String pagingList    = PropertiesUtil.getSearchPagingSizeList();
	String pagingNameList= PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle     = PropertiesUtil.getSearchGridStyle();
    String cookiesType   = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort       = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons     = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();

    String pagingSize = request.getParameter("Pagingsize");
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ProjectIssueTotalListGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='3' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-CreateDate'/>
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
    <Cfg Selecting='0' SelectingCells='0'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='1'/>
    <Cfg MaxHeight='1' MaxWidth='1'/>

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1'/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates'/>

    <!-- 그리드 기본 스타일 설정 -->
    <Cfg Style='<%=gridStyle %>'/>

    <!-- 기타 설정 -->
    <Cfg NoPager='1'/>
    <Cfg UsePrefix='1'/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='<%=alternateType %>'/> <!-- Custom style setting, every third row will have different color -->
    <Panel Visible='0' CanHide='0' />

    <LeftCols>
        <C Name='RowNum'    Width='40'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PjtNo'     Width='90'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PjtName'   Width='150' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='IssueType' Width='50'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Subject'   RelWidth='50' Width='150' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='Urgency'    Width='60' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Importance' Width='60' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='OwnerName'  Width='60' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ManagerName'  Width='60' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate' Width='70' Align='Center' CanSort='1' CanEdit='0' Type='Date' Format='yyyy-MM-dd' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='IsDone'     Width='70' Align='Center' CanSort='1' CanEdit='0' Type='Html' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            RowNum     ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            PjtNo      ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'
            PjtName    ='<%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%>'
            IssueType  ='<%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%>'
            Subject    ='<%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%>'
            Urgency    ='<%=messageService.getString("e3ps.message.ket_message", "01158") %><%--긴급도--%>'
            Importance ='<%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%>'
            OwnerName  ='<%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%>'
            CreateDate ='<%=messageService.getString("e3ps.message.ket_message", "02521") %><%--제기일--%>'
            ManagerName  ='<%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%>'
            IsDone     ='<%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%>'
            />

    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Cells="Reload,Export,Print,Columns,Link,Empty1,excelDown,perPage,Formula"
             Styles="2" Space="0" Kind="Topbar" Link="0"
             Empty1RelWidth="1" Empty1Type="Html"
             excelDownType="Img" excelDownIcon="/plm/portal/images/iocn_excel.png" excelDownWidth="16" excelDownCanEdit="0"
             excelDownOnClick="javascript:excelDown();"
             perPageType="Enum" perPageWidth="50" perPageEnumKeys="<%=pagingList%>" perPageEnum="<%=pagingNameList%>" perPage="<%=pagingSize%>"
             perPageOnChange="javascript:perPage(Value);"
             Formula="' <%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  <%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>: <b>'+count()+'</b> '"/>

    <Solid>
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PagerEditWidth='70' PagerEditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0' LISTCount='20'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
