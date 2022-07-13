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
    <Cfg id='ListPerformance1Grid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
<%--     <Cfg Paging='2' PageLength='<%=pagingSize%>'/> --%>
    <Cfg Paging='3' PageLength='<%=pagingSize%>'/>
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
        <C Name='RowNum'      Width='40'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='DevelopType' Width='60'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PjtNo'       Width='70'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PjtName'     Width='260' Align='Left'   CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Pm'          Width='50'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
    </LeftCols>

    <Cols>
        <C Name='Rank'       Width='60' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Quality'    Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Cost'       Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Delivery'   Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Technical'  Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EcoCount'   Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='TotalScore' Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Evaluate'   Width='30' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
    </Cols>

    <Head>
    <Header id="HeaderTop" Spanned='1' Align='Center'
            RowNum      ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'                   RowNumRowSpan='2'
            DevelopType ='<%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%>'             DevelopTypeRowSpan='2' DevelopTypeWrap='1'
            PjtNo       ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'           PjtNoRowSpan='2'
            PjtName     ='<%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%>'         PjtNameRowSpan='2'
            Pm          ='<%=messageService.getString("e3ps.message.ket_message", "00370") %><%--PM--%>'                   PmRowSpan='2'
            Rank        ='<%=messageService.getString("e3ps.message.ket_message", "00436") %><%--Rank--%>'                 RankRowSpan='2'
            Quality     ='<%=messageService.getString("e3ps.message.ket_message", "00401") %><%--Project 성과--%>'         QualitySpan='7' QualityType='Text'
            />
    <Header id="Header" CanDelete='0' Align='Center'
            RowNum      ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            DevelopType ='<%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%>'
            PjtNo       ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'
            PjtName     ='<%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%>'
            Pm          ='<%=messageService.getString("e3ps.message.ket_message", "00370") %><%--PM--%>'
            Rank        ='<%=messageService.getString("e3ps.message.ket_message", "00436") %><%--Rank--%>'
            Quality     ='<%=messageService.getString("e3ps.message.ket_message", "03024") %><%--품질--%>'
            Cost        ='<%=messageService.getString("e3ps.message.ket_message", "01640") %><%--비용--%>'
            Delivery    ='<%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%>'
            Technical   ='<%=messageService.getString("e3ps.message.ket_message", "01122") %><%--기술--%>'
            EcoCount    ='<%=messageService.getString("e3ps.message.ket_message", "01871") %><%--설변--%>'
            TotalScore  ='<%=messageService.getString("e3ps.message.ket_message", "02832") %><%--총점--%>'
            Evaluate    ='<%=messageService.getString("e3ps.message.ket_message", "02982") %><%--평가--%>'
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
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PagerEditWidth='70' PagerEditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0' LISTCount='20'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
