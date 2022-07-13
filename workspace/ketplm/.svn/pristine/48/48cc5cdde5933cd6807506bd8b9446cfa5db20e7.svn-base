<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ page import="e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
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
    String selectYear = request.getParameter("Year");
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='SearchMonthEcoReportGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='2' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-RowNum'/>
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
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='400' MinHeight='400'/>
    <Cfg MaxTagHeight='400' MinTagHeight='400'/> <!-- Minimal height of the main tag to not shrink it too much -->

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
        <C Name='RowNum'      Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='DeptName'    Width='65' Align='Left'   CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='OrgName'     Width='65' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='OldEcoCnt'   Width='50' Align='Right'  CanSort='0' CanEdit='0' Type='Int'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='NewEcoCnt'   Width='50' Align='Right'  CanSort='0' CanEdit='0' Type='Int'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
    </LeftCols>
    <Cols>
        <C Name='M1'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M2'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M3'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M4'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M5'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M6'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M7'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M8'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M9'    Width='40' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M10'   Width='46' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M11'   Width='46' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='M12'   Width='46' Align='Right' CanSort='0' CanEdit='0' Type='Int' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>

    </Cols>

    <Head>
    <Header id="HeaderTop" Spanned='1' Align='Center'
            RowNum    ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'          RowNumRowSpan='2'
            DeptName  ='<%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%>'      DeptNameRowSpan='2'
            OrgName   ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'      OrgNameRowSpan='2'
            OldEcoCnt ='<%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%>'        OldEcoCntSpan='2'
            M1        ='<%=selectYear %>'                                                                       M1Span='12'
            />
    <Header id="Header" CanDelete='0' Align='Center'
            RowNum    ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            DeptName  ='<%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%>'
            OrgName   ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
            OldEcoCnt ='<%=messageService.getString("e3ps.message.ket_message", "02473") %><%--전년--%>'
            NewEcoCnt ='<%=messageService.getString("e3ps.message.ket_message", "00995") %><%--금년--%>'
            M1        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 1) %><%--{0}월--%>'
            M2        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 2) %><%--{0}월--%>'
            M3 	      ='<%=messageService.getString("e3ps.message.ket_message", "00039", 3) %><%--{0}월--%>'
            M4        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 4) %><%--{0}월--%>'
            M5        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 5) %><%--{0}월--%>'
            M6        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 6) %><%--{0}월--%>'
            M7        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 7) %><%--{0}월--%>'
            M8        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 8) %><%--{0}월--%>'
            M9        ='<%=messageService.getString("e3ps.message.ket_message", "00039", 9) %><%--{0}월--%>'
            M10       ='<%=messageService.getString("e3ps.message.ket_message", "00039", 10) %><%--{0}월--%>'
            M11       ='<%=messageService.getString("e3ps.message.ket_message", "00039", 11) %><%--{0}월--%>'
            M12       ='<%=messageService.getString("e3ps.message.ket_message", "00039", 12) %><%--{0}월--%>'
            />
        </Head>

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
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1' PAGERCanEdit='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PAGEREditWidth='70' PAGEREditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
