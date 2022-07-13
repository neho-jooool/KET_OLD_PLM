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

    String gridStyle     = PropertiesUtil.getSearchGridStyle();
    String cookiesType   = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort       = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons     = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();

    String multi = request.getParameter("Multi");
    String checkMode = ("true".equals(multi))?"0":"1";
    
    String pagingSize = request.getParameter("Pagingsize");             // 10
    String paging     = "1";     
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='SearchEpmPopup' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>
    <!-- 검색결과 페이징 처리 관련 설정 -->
	<Cfg Paging='<%=paging%>' PageLength='<%=pagingSize%>' />    <%--  --%>
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
    <Cfg Selecting='1' SelectingCells='0'/>

    <!-- 멀티선택 설정   -->
    <Cfg SelectingSingle='<%=checkMode %>'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='410' MinHeight='410'/>
    <Cfg MaxTagHeight='330' MinTagHeight='330'/> <!-- Minimal height of the main tag to not shrink it too much -->

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

<%--
  int[] w = new int[10];
  w[0] = 40; // RowNum
  w[1] = 130; // DrawingNo
  w[3] = 40; // File
  w[4] = 50; // Ver
  w[5] = 100; // CADType
  w[6] = 60; // Creator
  w[7] = 75; // CreateDate
  w[8] = 60; // Status
  w[9] = 50; // Dummy
  w[2] = 775 - (w[0]+w[1]+w[3]+w[4]+w[5]+w[6]+w[7]+w[8]+w[9]); // DrawingName
--%>
    <Cols>

        <C Name='DrawingNo'      Width='180' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
        <C Name='File'           Width='60' Align='Center' CanSort='0' CanEdit='0'  Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ManufactureVer' Width='70' Align='Center' CanSort='1' CanEdit='0'  Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DrawingName'    Width='300' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CADType'        Width='150' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>

        <C Name='Status'         Width='100' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Dummy'          Width='100' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        
        <C Name='Ver'         Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Int' Visible='0'/>
        <C Name='Security'    Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='Oid'         Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='OidMaster'   Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>
        <C Name='category'   Width='0' Align='Center' CanSort='0' CanEdit='0' Type='Text' Visible='0'/>

    </Cols>

    <Header CanDelete='0' Align='Center'
            DrawingNo      ='<%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%>'
            File           ='<%=messageService.getString("e3ps.message.ket_message", "00137") %><%--D/W--%>'
            ManufactureVer ='<%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>'
            DrawingName    ='<%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%>'
            CADType        ='<%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%>'
            Status         ='<%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%>'
            Dummy          ='<%=messageService.getString("e3ps.message.ket_message", "00173") %><%--Dummy--%>'
            />
    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Visible="0" />
</Grid>
