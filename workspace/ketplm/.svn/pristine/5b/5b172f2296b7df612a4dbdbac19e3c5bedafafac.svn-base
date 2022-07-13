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

    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();

    String pagingSize = request.getParameter("Pagingsize");
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='EcoPartPopupSearchGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='2' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-CompDate'/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort %>'/>
    <Cfg SortIcons='<%=sortIcons %>'/>

    <!--  셀 편집 관련 설정 -->
    <Cfg Editing='1'/>

    <!-- 행 삭제 관련 설졍 -->
    <Cfg Deleting='0'/>
    <Cfg ShowDeleted='1'/> <!-- This example hides deleted row instead of coloring them red -->

    <!--  셀 병합 관련 설정 -->
    <Cfg DynamicSpan='0'/>

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting='1' SelectingCells='0'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <!-- Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='400' MinHeight='400'/>
    <Cfg MaxTagHeight='245' MinTagHeight='245'/ --> <!-- Minimal height of the main tag to not shrink it too much -->

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

    <!-- Panel 설정 -->
    <Panel Width="21" CanHide="0" />

    <Cols>
        <C Name='Oid' Width='30' Align='Center' CanEdit='0' CanSort='0' Type='Text'  Visible='0' Hidden='1' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EcoNo' Width='100' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='EcoName' Width='300' RelWidth='50' Align='Left' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DeptName' Width='30' Align='Center' CanEdit='0' CanSort='0' Type='Text'  Visible='0' Hidden='1' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Creator' Width='80' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreateDate' Width='80' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CompDate' Width='80' Align='Center' CanEdit='0' CanSort='1' Type='Text' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Budgetyn' Width='80' Align='Center' CanEdit='0' CanSort='1' Type='Text'   Visible='0' Hidden='1'  ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
        Oid=''
        EcoNo     ='<%=messageService.getString("e3ps.message.ket_message", "00183") %><%--ECO No--%>'
        EcoName   ='<%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%>'
        Creator   ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
        CreateDate='<%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%>'
        CompDate  ='<%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%>'
        Budgetyn  ='<%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%>'/>

    <Def>
         <!-- Default row used for all created groups -->
    </Def>
   <Toolbar Visible="0"/>
    <Solid>
        <I id='PAGER' Cells='NAV,LIST' Space='4' Calculated='1' Formula='count()'
            NAVType='Pager'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' />
    </Solid>
</Grid>
