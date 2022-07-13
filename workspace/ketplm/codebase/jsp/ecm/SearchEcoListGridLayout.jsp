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
    String paging     = "3";     // 서버 페이징에 추가
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='EcoListSearchGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <!-- client paging 2, server paging은 3  -->
    <Cfg Paging='<%=paging %>' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 
    서버 Paging관련
    AllPages='0' Paging display mode 0  Shows one page at a time and switches to pages only by pager click or by pressing PageUp / PageDown key. 
                                     1  Shows all pages in grid at once and renders pages on demand, when they are displayed by scrolling.
    Paging='3'
    PageLength = Average count of rows in page.
    RootCount = Count of all rows in root // Used to compute Count parameter of the last page when server paging used and pages have not Count attribute set.
    OnePage = If set to 1, sorts, filters or groups rows only in actual page. Only when AllPages = 0 (one page visible at a time).
              Bit array: 1. bit (&1) sorting, 2. bit (&2) filtering, 3. bit (&4) grouping. Set to 7 for all actions done at one page only.
              Use this attribute only for server paging (Paging=3) if you are not able to do sorting, filtering or grouping on server.
    ReloadChanged = If permits reloading grid if not uploaded changes are pending.
                0 – no (does not allow neither reloading nor server side sorting/filtering/searching/grouping)
                1 – yes, save them (only when reloading as request for server side filtering/sorting/searching/grouping, for reload by Reload button always discards changes),
                2] – yes, discard changes (should be used only when changes are not saved to server at all and server paging is used)
                3 – show confirmation message to user
                &4 – test also selected rows (values 4,5,6,7 are related to 0,1,2,3)
    id(Both letters lowercase !) = Unique ID of the page. By this ID is page identified when downloading data for this page. Used only if Rows are not set.
         If set neither id nor Rows attribute, the page is identified by attribute Pos as its position inside body. But this position can change if any page is deleted.
    Rows = User string to identify the page. If set, the page is identified by this string when downloading data for this page. It can for example contain a list of row ids the page contains.
           It can also be used to specify different url for every page by using Page_Url with wildcard *Rows
    Pos = Page position inside body (from 0), identifies the page, if id and Rows not set.
    Name = Name of the page, displayed in right side pager. It can contain HTML code (coded).
    Title = Title of page, displayed in pager as tool tip. It can contain HTML if set <Cfg StandardTip=’0’/>
    NameXY = There can be predefined names according to sorting. If these are set, the Name and Title are created from them.
        These names specifies the first and last value on page in sorting columns
        X is boundary  = 0 – both (both values are the same in this column), 1 – top, 2 – bottom. Can be used either 0 or 1+2.
        Y is sorting column 0,1,2.
        There are possible combinations of XY (separated by #):
        10 => 20 # 10,11 => 20,21 # 10,11,12 => 20,21,22 # 00 # 00 | 11 => 21 # 00 | 11,12 => 21,22 # 00, 01 # 00, 01 | 12 => 22
        For example: Name00=’blue’ Name11=’100’ Name21=’154’ means that page shows all blue values from 100 to 154.
    Count = Count of rows the page contains. It specifies height of the page when it does not show rows yet. It is also result of count function in formulas.
            If not set, it is used <Cfg PageLength> value. Or for the last page is Count computed from <Cfg RootCount> and row count in other pages.
    sum, count = aggregation
     -->
     
    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-CreateDate'/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort %>'/>
    <Cfg SortIcons='<%=sortIcons %>'/>

    <!--  셀 편집 관련 설정 -->
    <Cfg Editing='0'/>

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
    <!-- Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='400' MinHeight='400'/>
    <Cfg MaxTagHeight='400' MinTagHeight='400'/ --> <!-- Minimal height of the main tag to not shrink it too much -->

    <Cfg ConstWidth='1' ConstHeight='1' MaxHeight='1' MinTagHeight='300'/>
    
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
        <C Name='RowNum'      Width='0' Align='Center' CanEdit='0' CanSort='0' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ProdMoldCls' Width='50' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EcoNo'       Width='80' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='EcoName'       Width='150'  Align='Left'   CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ProjectNo'     Width='85'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ChangeReason'  Width='110' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='BudgetYn'      Width='50'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Creator'       Width='65'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='OrgName'       Width='80'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate'    Width='80'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='StateAppro'    Width='75'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ApproveDate'    Width='80'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CostChange'    Width='70'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CostVariation' Width='80'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EcoApplyPoint' Width='80'  Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            RowNum       ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            ProdMoldCls  ='<%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%>'
            EcoNo        ='<%=messageService.getString("e3ps.message.ket_message", "00183") %><%--ECO No--%>'
            EcoName      ='<%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%>'
            ProjectNo    ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'
            ChangeReason ='<%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%>'
            BudgetYn     ='<%=messageService.getString("e3ps.message.ket_message", "02143") %><%--예산--%>'
            Creator      ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
            OrgName         ='<%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%>'
            CreateDate   ='<%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%>'
            StateAppro   ='<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>'
            ApproveDate   ='<%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%>'
            CostChange   ='<%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%>'
            CostVariation   ='<%=messageService.getString("e3ps.message.ket_message", "04145") %><%--원가증감비--%>'
            EcoApplyPoint = 'ECO적용시점'
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
        <I id='PAGER' Cells='NAV,LIST' Space='4' Calculated='1' Formula='count()'
            NAVType='Pager'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTCount='20'
            <%-- 우측 서버 페이징에 추가 : 페이징개수 몇개 보여주나 --%>
            <%-- LISTCount='20' --%>
            />
    </Solid>
 
</Grid>
