<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@page import = "e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
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

    String pagingSize = request.getParameter("Pagingsize");
    String hidePanel = request.getParameter("hidePanel");

    String mode = request.getParameter("Mode");
    String checkMode = "0";
    if ( mode != null && mode != "" && mode.equals("s") ) {
        checkMode = "1";
    }
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <!-- Cfg id='AddProjectPeopleGrid' / -->
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->

    <Cfg AllPages='2'/>

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
    <Cfg Selecting='<%="true".equals(hidePanel)?"0":"1"%>' SelectingCells='0'/>

    <!-- 멀티선택 설정   -->
    <Cfg SelectingSingle='<%=checkMode %>'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='1'/>
    <Cfg MaxHeight='1' MinHeight='1'/>

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
    <Panel Width="21" CanHide="<%="true".equals(hidePanel)?"1":"0"%>" />

    <Cols>
        <!-- C Name='RowNum'      Width='40'  Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/ -->
        <C Name='UserName'    Width='70'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DeptName'    Width='150' RelWidth='50' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Duty'        Width='70'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PjtHistory'  Width='100' Align='Left' CanSort='0' CanEdit='0' Type='Html' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Dutycode'    Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='WtuserOID'   Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Poid'        Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Doid'        Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Uid'         Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Email'       Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
    </Cols>

    <Header CanDelete='0'   Align='Center'
            UserName   ='<%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%>'
            DeptName   ='<%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%>'
            Duty       ='<%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%>'
            PjtHistory ='<%=messageService.getString("e3ps.message.ket_message", "03122") %><%--프로젝트이력--%>'
            />
    <Def>
         <!-- Default row used for all created groups -->
    </Def>
    <Toolbar Visible="0"/>
</Grid>
