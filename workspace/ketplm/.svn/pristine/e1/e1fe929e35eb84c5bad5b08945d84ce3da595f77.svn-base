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
    
    /* 2014.07.09 동적컬럼을 위한 로직 */
    String moldType = request.getParameter("moldType");
    
    String popup = request.getParameter("Popup");
    
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='TemplateProjectListGrid' />
     <!-- 쿠기 사용 여부를 설정 : 0 ? uses, 1 ? does not load, 2 ? does not save, 3 ? nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='2' PageLength='<%=pagingSize%>'/>
    <Cfg AllPages='0'/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sort='-TempModifyDate'/>
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
    <Cfg Selecting='<%="yes".equals(popup) ? "1" : "0" %>' SelectingCells='0'/>

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
    <Cfg SelectingSingle='1' />
    <Panel   Visible = '1' CanHide = '0' />
    

    <Cols>
        <C Name='TempName'       Width='200' RelWidth='50' Align='Left' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='TempDu'         Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <!-- 2014.07.09 컬럼추가 -->
        <C Name='TempDivision'   Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DevType'        Visible="<%= !"mold".equals(moldType) ? "1" : "0" %>"  Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ClientCompany'  Visible="<%= !"mold".equals(moldType) ? "1" : "0" %>"  Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='DevStep'        Visible="<%= "product".equals(moldType) ? "1" : "0" %>"  Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='MakeOffice'     Visible="<%= "mold".equals(moldType) || "purchase".equals(moldType) ? "1" : "0" %>"  Width='100' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='MoldType'       Visible="<%= "mold".equals(moldType) ? "1" : "0" %>"  Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Making'         Visible="<%= "mold".equals(moldType) || "purchase".equals(moldType) ? "1" : "0" %>"  Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ActiveType'          Width='50' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='TempCreateDate' Width='80'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='TempModifyDate' Width='80' Align='Center'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='CreatorName'    Width='70'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ProjectOid'    Visible='0' Width='70'  Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>    
    </Cols>

    <Header CanDelete='0' Align='Center'
            TempName       ='WBS Name'
            TempDu         ='<%=messageService.getString("e3ps.message.ket_message", "02106") %><%--업무기간(일)--%>'
            TempDivision   = '<%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%>'
            DevType        = '<%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발구분--%>'
            ClientCompany  = '<%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%>'
            DevStep        = '<%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%>'
            MakeOffice     = '<%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제조처--%>'
            MoldType       = '<%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%>'
            Making         = '<%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%>'
            ActiveType     = '<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>'
            TempCreateDate ='<%=messageService.getString("e3ps.message.ket_message", "02859") %><%--최초등록일--%>'
            TempModifyDate ='<%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%>'
            CreatorName    ='<%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%>'
            />

    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Cells="Reload,Export,Print,Columns,Link,Empty1,perPage,Formula"
             Styles="2" Space="0" Kind="Topbar" Link="0"
             Empty1RelWidth="1" Empty1Type="Html"
             perPageType="Enum" perPageWidth="50" perPageEnumKeys="<%=pagingList%>" perPageEnum="<%=pagingNameList%>" perPage="<%=pagingSize%>"
             perPageOnChange="javascript:perPage(Value);"
             Formula="' <%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%>: <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  <%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>: <b>'+count()+'</b> '"/>

    <Solid>
        <I id='PAGER' Cells='PAGER,LIST' Space='4' Calculated='1'
            PAGERType='Pager' PAGERRelWidth='1' PAGERAlign='left' PagerEditWidth='70' PagerEditHeight='20'
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
