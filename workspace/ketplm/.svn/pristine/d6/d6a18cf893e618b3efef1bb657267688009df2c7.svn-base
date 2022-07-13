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

    String pagingList    = PropertiesUtil.getSearchPagingSizeList();    // |5|7|10
	String pagingNameList= PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle     = PropertiesUtil.getSearchGridStyle();         // Office
    String cookiesType   = PropertiesUtil.getSearchGridCookiesType();   // 0
    String alternateType = PropertiesUtil.getSearchGridAlternateType(); // 2
    String maxSort       = PropertiesUtil.getSearchGridMaxSort();       // 1
    String sortIcons     = PropertiesUtil.getSearchGridSortIcons();     // 1
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();       // 30

    String pagingSize = request.getParameter("Pagingsize");             // 10
    String paging     = "3";     // 서버 페이징에 추가
    
%>

<?xml version="1.0"?>
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
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='EDMSearchGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <!-- client paging 2, server paging은 3  -->
    <Cfg Paging='<%=paging%>' PageLength='<%=pagingSize%>' />    <%--  --%>
    <Cfg AllPages='0'/>
    
    <Cfg Adding='0' Editing='0' /> <!-- Disables other features to demonstrate grouping only -->
    
    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-CreateDate'/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort%>'/>
    <Cfg SortIcons='<%=sortIcons%>'/>

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
    <Cfg MaxHeight='1' MinHeight='400'/>
   
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

<%
  int[] w = new int[10];
  w[0] = 0; // RowNum
  w[1] = 130; // DrawingNo
  w[3] = 40; // File
  w[4] = 50; // Ver
  w[5] = 100; // CADType
  w[6] = 60; // Creator
  w[7] = 75; // CreateDate
  w[8] = 60; // Status
  w[9] = 50; // Dummy
  w[2] = 775 - (w[0]+w[1]+w[3]+w[4]+w[5]+w[6]+w[7]+w[8]+w[9]); // DrawingName
%>
    <LeftCols>
        <C Name='DrawingNo'      Width='<%=w[1] %>' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
        <C Name='File'           Width='<%=w[3] %>' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ManufactureVer' Width='<%=w[4] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DrawingName'    RelWidth='50' Width='<%=w[2] %>' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Ver'            Width='0'          Align='Center' CanSort='0' CanEdit='0' Type='Int' Visible='0'/>
        <C Name='Security'       Width='0'          Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Oid'            Width='0'          Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='OidMaster'      Width='0'          Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
    </LeftCols>

    <Cols>
        <C Name='CADType'        Width='<%=w[5] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Creator'        Width='<%=w[6] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate'     Width='<%=w[7] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Status'         Width='<%=w[8] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Dummy'          Width='<%=w[9] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartNumber'     Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='DevStage'       Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='Category'       Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='CreateDeptName' Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='RequestDate'    Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='ApprovalDate'   Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='ProjectNo'      Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='ProjectName'    Width='0' Align='Center' CanSort='0' CanEdit='0' CanResize='1' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            DrawingNo      ='<%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%>'
            File           ='<%=messageService.getString("e3ps.message.ket_message", "00137") %><%--D/W--%>'
            ManufactureVer ='<%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>'
            DrawingName    ='<%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%>'
            CADType        ='<%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%>'
            Creator        ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
            CreateDate     ='<%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%>'
            Status         ='<%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%>'
            Dummy          ='<%=messageService.getString("e3ps.message.ket_message", "00173") %><%--Dummy--%>'
            PartNumber     ='<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>'
            DevStage       ='<%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%>'
            Category       ='<%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%>'
            CreateDeptName ='<%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%>'
            RequestDate    ='<%=messageService.getString("e3ps.message.ket_message", "01753") %><%--상신일자--%>'
            ApprovalDate   ='<%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%>'
            ProjectNo      ='<%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%>'
            ProjectName    ='<%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%>'
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
             Formula="' <%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  <%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>: <b>'+count()+'</b> '"
             />
    <Solid>
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1' PAGERCanEdit='1'  <%-- 우측속성 서버 페이징에 추가 --%>NAVType='Pager'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PAGEREditWidth='70' PAGEREditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0' <%-- 우측 서버 페이징에 추가 : 페이징개수 몇개 보여주나 --%>LISTCount='20'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>

