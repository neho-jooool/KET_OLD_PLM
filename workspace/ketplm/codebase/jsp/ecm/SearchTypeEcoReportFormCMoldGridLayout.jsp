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
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='SearchTypeEcoReportFormCMoldGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='2' PageLength='<%=pagingSize%>'/>
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
        <C Name='RowNum'   Width='40'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='ObjType'  Width='55'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PartNo'   Width='65'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PartName' Width='150' Align='Left'   CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='EcoCount' Width='50'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Oid'      Width='0'   Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
    </LeftCols>
    <Cols>
        <C Name='DevCnt'   Width='50' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ProdCnt'  Width='50' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason01' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason02' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason03' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason04' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason05' Width='60' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason06' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Reason07' Width='40' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
    </Cols>

    <Head>
    <Header id="HeaderTop" Spanned='1' Align='Center'
            RowNum   ='No'                                                                                                 RowNumRowSpan='2'
            ObjType  ='Type'                                                                                               ObjTypeRowSpan='2'
            PartNo   ='Die No'                                                                                             PartNoRowSpan='2'
            PartName ='<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>'                  PartNameRowSpan='2'
            EcoCount ='<%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%>'                    EcoCountRowSpan='2'
            DevCnt   ='<%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%>'                DevCntSpan='2'
            Reason01 ='<%=messageService.getString("e3ps.message.ket_message", "00850") %><%--고객요청--%>'                Reason01RowSpan='2' Reason01Wrap='1'
            Reason02 ='<%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%>'                Reason02RowSpan='2' Reason02Wrap='1'
            Reason03 ='<%=messageService.getString("e3ps.message.ket_message", "02602") %><%--제품설변--%>'                Reason03RowSpan='2' Reason03Wrap='1'
            Reason04 ='<%=messageService.getString("e3ps.message.ket_message", "01020") %><%--금형 보완--%>'               Reason04RowSpan='2' Reason04Wrap='1'
            Reason05 ='<%=messageService.getString("e3ps.message.ket_message", "01784") %><%--생산성{0} 향상--%>'       Reason05RowSpan='2' Reason05Wrap='1'
            Reason06 ='<%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%>'                                                                                          Reason06RowSpan='2' Reason06Wrap='1'
            Reason07 ='<%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>'                    Reason07RowSpan='2' Reason07Wrap='1'
            />
    <Header id="Header" CanDelete='0' Align='Center'
            RowNum   ='No'
            ObjType  ='Type'
            PartNo   ='Die No'
            PartName ='<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>'
            EcoCount ='<%=messageService.getString("e3ps.message.ket_message", "00699") %><%--건수--%>'
            DevCnt   ='<%=messageService.getString("e3ps.message.ket_message", "00582") %><%--개발--%>'
            ProdCnt  ='<%=messageService.getString("e3ps.message.ket_message", "02078") %><%--양산--%>'
            Reason01 ='<%=messageService.getString("e3ps.message.ket_message", "00850") %><%--고객요청--%>'
            Reason02 ='<%=messageService.getString("e3ps.message.ket_message", "03027") %><%--품질문제--%>'
            Reason03 ='<%=messageService.getString("e3ps.message.ket_message", "02602") %><%--제품설변--%>'
            Reason04 ='<%=messageService.getString("e3ps.message.ket_message", "01020") %><%--금형 보완--%>'
            Reason05 ='<%=messageService.getString("e3ps.message.ket_message", "01784") %><%--생산성{0} 향상--%>'
            Reason06 ='<%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%>'
            Reason07 ='<%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%>'
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
