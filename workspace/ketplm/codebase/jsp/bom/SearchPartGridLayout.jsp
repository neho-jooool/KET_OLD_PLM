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
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='SearchPart' />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%=cookiesType %>'/>

    <!-- 검색결과 페이징 처리 관련 설정 -->
    <Cfg Paging='2' PageLength='<%=pagingSize%>'/>
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
    <Cfg Selecting='0' SelectingCells='0'/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <Cfg MaxHeight='400' MinHeight='400'/>
    <Cfg MaxTagHeight='400' MinTagHeight='400'/> <!-- Minimal height of the main tag to not shrink it too much -->

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
  int[] w = new int[8];
  w[0] = 40; // RowNum
  w[1] = 140; // PartNo
  w[3] = 80; // PartType
  w[4] = 50; // Ver
  w[5] = 80; // Status
  w[6] = 70; // Unit
  w[7] = 70; // Del
  w[2] = 760 - (w[0]+w[1]+w[3]+w[4]+w[5]+w[6]+w[7]); // PartName
%>
    <LeftCols>
        <C Name='RowNum'   Width='<%=w[0] %>' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartType' Width='<%=w[3] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='PartNo'   Width='<%=w[1] %>' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
        <C Name='PartName' Width='<%=w[2] %>' Align='Left'   CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:left;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='Ver'        Width='<%=w[4] %>' Align='Center' CanSort='1' CanEdit='0' Type='Int'  MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Status'     Width='<%=w[5] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Unit'       Width='<%=w[6] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='Del'        Width='<%=w[7] %>' Align='Center' CanSort='1' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' ExportStyle='text-align:center;vertical-align:middle'/>

        <C Name='PartGroup'  Width='1' Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='PartWeight' Width='1' Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='IsYazaki'   Width='1' Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
        <C Name='YazakiNo'   Width='1' Align='Center' CanSort='0' CanEdit='0' CanResize='0' Type='Text' MinWidth='0' Visible='0' ExportStyle='text-align:center;vertical-align:middle;width:80;'/>
    </Cols>

    <Header CanDelete='0' Align='Center'
            RowNum    ='<%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%>'
            PartNo    ='<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>'
            PartName  ='<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>'
            PartType  ='<%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%>'
            Ver       ='<%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%>'
            Status    ='<%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%>'
            Unit      ='<%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%>'
            Del       ='<%=messageService.getString("e3ps.message.ket_message", "01706") %><%--삭제여부--%>'
            PartGroup ='<%=messageService.getString("e3ps.message.ket_message", "02419") %><%--자재그룹--%>'
            PartWeight='<%=messageService.getString("e3ps.message.ket_message", "02619") %><%--제품중량--%>'
            IsYazaki  ='<%=messageService.getString("e3ps.message.ket_message", "00569") %><%--YAZAKI여부--%>'
            YazakiNo  ='<%=messageService.getString("e3ps.message.ket_message", "00570") %><%--YAZAKI제번--%>'
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
            LISTType='Pages' LISTWrap='1' LISTRelWidth='1' LISTAlign='left' LISTLeft='0'
            Formula='Grids.LoadedCount'/>
    </Solid>
</Grid>
