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
    <Cfg id='ListMoldPart' />
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
    <!-- Cfg ConstHeight='1' ConstWidth='1'/>
    <Cfg MaxHeight='1' MaxWidth='1'/ -->

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

<%
  int[] w = new int[10];
  w[0] = 40; // DocNo
  w[1] = 70; // DieNo
  w[2] = 80; // LevelType
  w[4] = 70; // Creator
  w[5] = 70; // PartUser
  w[6] = 80; // CreateDate
  w[7] = 80; // PlanDate
  w[8] = 80; // EndDate
  w[9] = 50; // Status
  w[3] = 775 - (w[0]+w[1]+w[2]+w[4]+w[5]+w[6]+w[7]+w[8]+w[9]) - 1; // Title
%>
    <!-- LeftCols>
        <C Name='RowNum'      Width='<%=w[0] %>'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DieNo'       Width='<%=w[1] %>'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='LevelType'   Width='<%=w[2] %>'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Title'       RelWidth='50' Width='<%=w[3] %>'  Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='Creator'     Width='<%=w[4] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartUser'    Width='<%=w[5] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='MoldDrawDate' Width='1'         Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0'  ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='CreateDate'  Width='<%=w[6] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PlanDate'    Width='<%=w[7] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EndDate'     Width='<%=w[8] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Status'      Width='<%=w[9] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols -->

    <LeftCols>
        <C Name='RowNum'      Width='0'   Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DieNo'       Width='80'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='LevelType'   Width='80'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Title'       Width='120' RelWidth='50'  Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='Creator'         Width='65' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartUser'        Width='85' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='MoldDrawDate'    Width='0'  Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0'  ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate'      Width='85' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PlanDate'        Width='85' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EndDate'         Width='85' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Status'          Width='80' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
<%--         <C Name='EcaCompleteDate' Width='85' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/> --%>
    </Cols>
    
    <Header CanDelete='0' Align='Center'
            RowNum          ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            DieNo           ='<%=messageService.getString("e3ps.message.ket_message", "00144") %><%--Die No--%>'
            LevelType       ='<%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%>'
            Title           ='<%=messageService.getString("e3ps.message.ket_message", "01745") %><%--상세구분--%>'
            Creator         ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
            PartUser        ='<%=messageService.getString("e3ps.message.ket_message", "01583") %><%--부품공정--%>'
            MoldDrawDate    ='<%=messageService.getString("e3ps.message.ket_message", "01089") %><%--금형설계출도일--%>'
            CreateDate      ='<%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%>'
            PlanDate        ='<%=messageService.getString("e3ps.message.ket_message", "01594") %><%--부품완료 예정일--%>'
            EndDate         ='<%=messageService.getString("e3ps.message.ket_message", "02062") %><%--실제 완료일--%>'
            Status          ='<%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%>'
<%--             EcaCompleteDate ='<%=messageService.getString("e3ps.message.ket_message", "00056") %>4M완료일' --%>
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
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1' PAGERCanEdit='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PAGEREditWidth='70' PAGEREditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0' LISTCount='20'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
