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

    String gridStyle     = PropertiesUtil.getSearchGridStyle();
    String cookiesType   = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort       = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons     = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='CommisionSearchPopupGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-RequestNo'/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort %>'/>
    <Cfg SortIcons='<%=sortIcons %>'/>

    <!-- 행 삭제 관련 설졍 -->
    <Cfg Deleting='0'/>
    <Cfg ShowDeleted='1'/> <!-- This example hides deleted row instead of coloring them red -->

    <!--  셀 병합 관련 설정 -->
    <Cfg DynamicSpan='0'/>

    <Cfg Selecting='1' SelectingCells='0'/>

    <!-- 멀티선택 설정   -->
    <!-- 06-20  남현승   -->
    <Cfg SelectingSingle='1'/>


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

    <!-- Panel 설정 -->
    <Panel Width="21" CanHide="0" />

   <Cols>
        <C Name='oid'             Width='10'  Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0' Hidden='1'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='drState'         Width='10'  Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0' Hidden='1'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='RequestDivision' Width='90'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='RequestNo'       Width='90'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DevPjtNo'        Width='100' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DevProdName'     Width='160' RelWidth='50' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            RequestDivision='<%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>'
            RequestNo      ='<%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%>'
            DevPjtNo       ='<%=messageService.getString("e3ps.message.ket_message", "00397") %><%--Project No--%>'
            DevProdName    ='<%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%>'
            />

    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Visible="0"/>
</Grid>
