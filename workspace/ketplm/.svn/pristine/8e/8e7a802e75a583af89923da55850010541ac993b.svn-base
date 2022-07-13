<%@page import="java.util.ArrayList"%>
<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ page import="e3ps.common.util.PropertiesUtil"%>
<%@ page import="e3ps.common.util.StringUtil"%>
<%@ page import="java.util.List,java.util.ArrayList"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    /*-----------------------------------------------------------------------------------------------------------------
    ! Support file only, run Grid.html instead !
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String ganttGridStyle  = PropertiesUtil.getGanttGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth   = PropertiesUtil.getSearchColMinWidth();

    String resultStr = request.getParameter("Result");

    String versions = StringUtil.checkNull(request.getParameter("Versions")); // ProjectScheduleHistoryServlet.search() 'versions' 참고
    String detail = StringUtil.checkNull(request.getParameter("Detail"));
    Kogger.debug("ViewProjectScheduleHistoryGridLayout.jsp: versions=[" + versions + "], detail=[" + detail + "]");
    String[] strAry = versions.split(",");
    List<String[]> versionList = new ArrayList<String[]>();
    for (int i = 0; i < strAry.length; ++i) {
      String str = strAry[i];
      if (StringUtil.checkString(str)) {
        versionList.add(str.split("/"));
      }
    }
    int versionsLen = versionList.size();
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id="ViewProjectScheduleHistory" />
     <!-- 쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg="<%=cookiesType %>"/>

    <!-- 정렬 관련 설정 -->
    <Cfg Sorting="0"/>

    <!-- 행 삭제 관련 설졍 -->
    <Cfg Deleting="0"/>
    <Cfg ShowDeleted="0"/> <!-- This example hides deleted row instead of coloring them red -->

    <!--  셀 병합 관련 설정 -->
    <Cfg DynamicSpan="0"/>

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting="0" SelectingCells="0"/>

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging="0"/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='0' ConstWidth='1'  /> 
     <Cfg MaxHeight='1' MinHeight='0'/>
    <Cfg MaxTagHeight="600" MinTagHeight="600"/> <!-- Minimal height of the main tag to not shrink it too much -->

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat="1"/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType="Filtered,Hidden,Strings,Dates"/>

    <!-- 그리드 기본 스타일 설정 -->
    <Cfg Style="<%=ganttGridStyle %>"/>

    <!-- 기타 설정 -->
    <Cfg UsePrefix="1"/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate="0"/> <!-- Custom style setting, every third row will have different color -->
    <Panel Visible="0" CanHide="0"/>

    <!-- Main 컬럼 설정 -->
    <Cfg MainCol="TaskName"/>

    <LeftCols>
        <C Name="TaskName"      Width="315"  Align="Left" CanSort="0" CanEdit="0" Type="Text" MinWidth="<%=colMinWidth%>" Spanned="1" Background="#FFFFFF" ExportStyle="text-align:left;vertical-align:middle"/>
<%
  if (versionsLen > 0) {
%>
        <C Name="Version0_Start" Width="70" Align="Center" CanSort="0" CanEdit="0" CanResize="0" CanMove="0" Type="Text" MinWidth="<%=colMinWidth%>" Background="#FFFFFF" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="Version0_End" Width="70" Align="Center" CanSort="0" CanEdit="0" CanResize="0" CanMove="0" Type="Text" MinWidth="<%=colMinWidth%>" Background="#FFFFFF" ExportStyle="text-align:center;vertical-align:middle"/>
<%
  }
%>

    </LeftCols>

    <Cols>
<%
  for (int i = 1; i < versionsLen; ++i) {
%>
        <C Name="Version<%=i %>_Start" Width="75" Align="Center" CanSort="0" CanEdit="0" CanResize="0" CanMove="0" Type="Text" MinWidth="<%=colMinWidth%>" Background="#FFFFFF" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="Version<%=i %>_End" Width="75" Align="Center" CanSort="0" CanEdit="0" CanResize="0" CanMove="0" Type="Text" MinWidth="<%=colMinWidth%>" Background="#FFFFFF" ExportStyle="text-align:center;vertical-align:middle"/>
<%
  }
%>
    </Cols>

    <Head>
      <Header id="HeaderTop" CanDelete="0" Align="Center" Spanned="1"
              TaskName="Task" TaskNameRowSpan="2"
<%
  for (int i = 0; i < versionsLen; ++i) {
    String[] sary = versionList.get(i); // [0] pjtHistory, [1] pjtIteration, [2] '(작업중)', [3] project oid
    String title;
    if ("Y".equals(detail)) {
      title = "Ver " + sary[0] + "." + sary[1] + " " + sary[2];
    }
    else {
      title = "Ver " + sary[0] + " " + sary[2];
    }
    String tip = sary[0] + "." + sary[1];
    String poid = sary[3];
%>
              Version<%=i %>_Start="<%=title %>"
                 Version<%=i %>_StartSpan="2"
                 Version<%=i %>_StartOnClick="javascript:openViewProjectSchedule('<%= poid %>');"
                 Version<%=i %>_StartHtmlPrefix="<font color='<%=PropertiesUtil.getSearchGridLinkColor() %>'>"
                 Version<%=i %>_StartHtmlPostfix="</font>"
                 Version<%=i %>_StartTip="<%=tip %>"
                 Version<%=i %>_StartCursor="hand"
<%
  }
%>
      />

      <Header id="Header" CanDelete='0' Align='Center'
              TaskName="Task"
<%
  for (int i = 0; i < versionsLen; ++i) {
%>
              Version<%=i %>_Start="<%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%>"
              Version<%=i %>_End="<%=messageService.getString("e3ps.message.ket_message", "00820") %><%--계획{0}완료일--%>"
<%
  }
%>
      />
    </Head>

    <Def>
         <!-- Default row used for all created groups -->
    </Def>

    <Toolbar Cells="Reload,Export,Print"
             Styles="2" Space="0" Kind="Topbar" Link="0" Empty="1"
             />

    <Body>
        <B><%=resultStr%></B>
    </Body>
</Grid>
