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
    String sapAuth    = request.getParameter("Sapauth");
    String sapCheck   = request.getParameter("Sapcheck");
    String paging     = "3";     // 서버 페이징에 추가
    String searchPopup  = request.getParameter("Searchpopup");
    String mode         = request.getParameter("Mode");
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ProjectSearchProdGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='<%=paging%>' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-PlanStartDate'/>
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
    <%if("Y".equals(searchPopup)){%>
    <Cfg Selecting='1' SelectingCells='0'/>
	    <%if("one".equals(mode)){%>
    <Cfg SelectingSingle="1"/>
	    <%}else{%>
    <Cfg SelectingSingle="0"/>	    
	    <%}%>
    <%}else{%>
    <Cfg Selecting='0' SelectingCells='0'/>
    <%}%>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>
    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='1'/>
    <Cfg MaxHeight='1' MaxWidth='1'/>
    <!-- Cfg MaxHeight='400' MinHeight='400'/>
    <Cfg MaxTagHeight='400' MinTagHeight='400'/ --> <!-- Minimal height of the main tag to not shrink it too much -->

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1'/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates'/>

    <!-- 그리드 기본 스타일 설정 -->
    <Cfg Style='<%=gridStyle %>'/>

    <!-- 기타 설정 -->
    <Cfg NoPager='1'/>
    <Cfg UsePrefix='1'/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='<%=alternateType %>'/> <!-- Custom style setting, every third row will have different color -->
    <%if("Y".equals(searchPopup)){%>
    <Panel Width='21' Visible='1' CanHide='0' />
    <%}else{%>
    <Panel Visible='0' CanHide='0' />
    <%}%>
    
    <Cols>
        <C Name='PjtNo'         Width='90'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1' />
        <%-- <C Name='PjtName'       Width='130' RelWidth='50' Align='Left' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/> --%>
        <C Name='PjtName'       Width='270' Align='Left' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle' Spanned='1'/>
        <C Name='PlanStartDate' Width='80' Align='Center' CanSort='1' CanEdit='0' Type='Date' Format='yyyy-MM-dd' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PlanEndDate'   Width='80' Align='Center' CanSort='1' CanEdit='0' Type='Date' Format='yyyy-MM-dd' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <%if(!"Y".equals(searchPopup)){%>
        <C Name='PjtState'      Width='90' Align='Center' CanSort='0' CanEdit='0' Type='Html' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <%}%>
        <C Name='PmDept'         Width='80' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='PmUser'         Width='70' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
        <C Name='Statestate'         Width='70' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle' Spanned='1'/>
    </Cols>
    

    
    <Header CanDelete='0' Align='Center'
            PjtNo         ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'
            PjtName       ='<%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%>'
            PlanStartDate ='<%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%>' 
            PlanEndDate   ='<%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%>' 
            <%if(!"Y".equals(searchPopup)){%>
            PjtState      ='<%=messageService.getString("e3ps.message.ket_message", "00409") %><%--Project 현황--%>' PjtStateRowSpan='2'
            <%}%>
            PmDept         ='PM부서' PmDeptRowSpan='2'
            PmUser         ='PM' PmUserRowSpan='2'
            Statestate         ='<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>' StatestateRowSpan='2'
    />
    

    <Def>
         <!-- Default row used for all created groups -->
    </Def>
    <Toolbar Cells="Reload,Export,Print,Columns,Link,Empty1,Sap,SapTxt,excelDown,perPage,Formula"
             Styles="2" Space="0" Kind="Topbar" Link="0"
             Empty1RelWidth="1" Empty1Type="Html"
             perPageType="Enum" perPageWidth="50" perPageEnumKeys="<%=pagingList%>" perPageEnum="<%=pagingNameList%>" perPage="<%=pagingSize%>"
             perPageOnChange="javascript:perPage(Value);" Visible="<%=(searchPopup == null || !"Y".equals(searchPopup))?"1":"0"%>"
             Formula="' <%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  <%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>: <b>'+count()+'</b> '"/>
             
    <Solid>
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1' PAGERCanEdit='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PAGEREditWidth='70' PAGEREditHeight='15'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0' LISTCount='20'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
