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
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();

    String pagingSize = request.getParameter("Pagingsize");

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
    <Cfg id='inputPartNumberPopup' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->

    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort=''/>
    <Cfg Sorting='1'/>
    <Cfg AutoSort='1'/>
    <Cfg MaxSort='<%=maxSort %>'/>
    <Cfg SortIcons='<%=sortIcons %>'/>

    <!-- 행 삭제 관련 설졍 -->
    <Cfg Deleting='1'/>
    <Cfg ShowDeleted='0'/> <!-- This example hides deleted row instead of coloring them red -->

    <!--  셀 병합 관련 설정 -->
    <Cfg DynamicSpan='0'/>

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting='0' SelectingCells='1'/>

    <Panel Width="38" Copy='1' CopyMenu="AddRowBelow" PanelCopyTip="Add" PanelDeleteTip="Delete"/>

    <!-- 멀티선택 설정   -->
<!--
    <Cfg SelectingSingle='<%=checkMode %>'/>
-->
    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='410' MinHeight='410'/>
    <Cfg MaxTagHeight='410' MinTagHeight='410'/> <!-- Minimal height of the main tag to not shrink it too much -->

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1'/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates'/>

    <!-- 그리드 기본 스타일 설정 -->
    <Cfg Style='<%=gridStyle %>'/>

    <!-- 기타 설정 -->
    <Cfg NoPager='1'/>
    <Cfg UsePrefix='1'/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='<%=alternateType %>'/> <!-- Custom style setting, every third row will have different color -->

    <!-- Action 설정 -->
    <!-- Toolbar 'Add' 아이콘 클릭 시 표시할 메뉴 -->
    <Actions OnClickButtonAdd="CopyMenuF"
             OnClickHeaderDelete="javascript:clearAll();"
             OnClickPanelCopy="FocusRow,CopyMenuF"/>

    <Cols>
        <C Name='PartNumber'    Width='400'  Align='Left' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
    </Cols>
    <Header Align='Center'
            PartNumber  ='<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>'
            PanelHeaderDeleteTip="Delete all rows"
            PanelHeaderCopyTip="Add new row"
            />
    <Def>
         <!-- Default row used for all created groups -->
    </Def>
<!--
    <Toolbar Visible="0"/>
-->
    <Toolbar Cells="Empty1,Cnt"
             Styles="2" Space="0" Kind="Topbar" Link="0" Empty1RelWidth='1' Empty1Type="Html"
             CntType="Html" CntWidth="250" CntAlign="Right"
             CntHtmlPrefix="<%=messageService.getString("e3ps.message.ket_message", "02385") %><%--입력된 부품 수--%>: &lt;b>" CntHtmlPostfix="&lt;/b>"/>
</Grid>
