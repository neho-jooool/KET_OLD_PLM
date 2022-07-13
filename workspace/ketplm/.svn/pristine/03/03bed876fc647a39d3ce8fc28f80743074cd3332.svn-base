<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.project.E3PSProject,
                e3ps.project.MoldProject,
                java.text.SimpleDateFormat" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    /*-----------------------------------------------------------------------------------------------------------------
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String ganttGridStyle  = PropertiesUtil.getGanttGridStyle();
    String cookiesType     = PropertiesUtil.getSearchGridCookiesType();
    String alternateType   = PropertiesUtil.getSearchGridAlternateType();
    String colMinWidth     = PropertiesUtil.getSearchColMinWidth();
    String screenWidth          = request.getParameter("Screenwidth");
    String screenHeight         = request.getParameter("Screenheight");
    String oid                  = request.getParameter("Oid");
    String personRoleEnumKey    = request.getParameter("Personroleenumkey");
    String personRoleEnum       = request.getParameter("Personroleenum");
    String milestoneTypeEnumKey = request.getParameter("Milestonetypeenumkey");
    String milestoneTypeEnum    = request.getParameter("Milestonetypeenum");
    String optionTypeEnumKey    = request.getParameter("Optiontypeenumkey");
    String optionTypeEnum       = request.getParameter("Optiontypeenum");
    String taskTypeEnumKey      = request.getParameter("Tasktypeenumkey");
    String taskTypeEnum         = request.getParameter("Tasktypeenum");
    String ganttChartMaxStart   = request.getParameter("Ganttchartmaxstart");
    String ganttChartMinEnd     = request.getParameter("Ganttchartminend");
    String title = "";
    String pjtNo = "";
    String pjtName = "";
    String dday = "";
    String pjtVersion = "";
    String pjtVersionStyle = "";
    String space = " ";
    String todayDateStr = DateUtil.getCurrentDateString(new SimpleDateFormat ( "yyyy-MM-dd" ));
    String today = todayDateStr + " 12:00";
    String pjtType = "";
    int pjtHistory = 0;
    int pjtIteration = 0;
    Object obj = CommonUtil.getObject(oid);
    E3PSProject project = null;

    // Title - Project : PJT_NO (PJT_NAME)
    if ( obj instanceof E3PSProject ) {

        project = (E3PSProject)obj;

        if ( project != null ) {

            // Project Title
            pjtNo = StringUtil.checkNull(project.getPjtNo());
            pjtName = StringUtil.checkNull(project.getPjtName());
            title = pjtNo + " (" + pjtName + ")";

            // D-Day
            dday = "[D-Day] ";

            // Project Version
            pjtHistory = project.getPjtHistory();
            pjtIteration = project.getPjtIteration();
            pjtVersion = "Ver. " + pjtHistory;
            if ( pjtIteration > 0 ) {
                pjtVersion = pjtVersion + "." + pjtIteration;
            }

            // Project Version CSS
            if ( project.isWorkingCopy() ) {
                pjtVersion = pjtVersion + " (작업중)";
                pjtVersionStyle = " color: #d10808;";
            }

            // Project Type
            if ( project instanceof MoldProject ) {
                pjtType = "MOLD";
            }
        }
    }
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ViewProjectScheduleGrid' />
     <!-- 쿠키 사용 여부 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%= cookiesType %>'/>
    <Cfg GanttLap="1"/>

    <!-- 정렬 관련 설정 : 0 - a user cannot sort grid, 1 - a user can sort grid -->
    <Cfg Sorting='0'/><!-- TreeGrid는 Level이 있는 구조로 사용자가 원하는 형태로 정렬되지 않으므로, 정렬 기능 사용하지 않도록 함 -->

    <!-- 셀 편집 관련 설정 -->
    <Cfg Editing='0'/><!-- 0 - read only, 1 - editable(default), 2 - preview -->

    <!-- 행 삭제 관련 설정 -->
    <Cfg Deleting='0'/><!-- 0 - disables Panel Delete button, 1 - can be deleted from grid -->
    <Cfg ShowDeleted='0'/><!-- 0 - deleted rows are hidden, 1 - deleted rows are still visible -->

    <!-- 셀 병합 관련 설정 -->
    <Cfg DynamicSpan='0'/>

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting='0' SelectingCells='0'/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg MaxHeight="1" />
    <Cfg MinLeftWidth="300"
         MidWidth="550" MinMidWidth="300"
         RightWidth="500" MinRightWidth="300"
    />

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat="1"/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType="Filtered,Hidden,Strings,Dates"/>

    <!-- 간트챠트 그리드 기본 스타일 설정 -->
    <Cfg Style="<%= ganttGridStyle %>"/>

    <!-- 기타 설정 -->
    <Cfg UsePrefix="1"/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate="0"/> <!-- Custom style setting, every third row will have different color -->
    <Panel Visible="0" CanHide="0"/>

    <!-- Main 컬럼 설정 -->
    <Cfg MainCol="TaskName"/>

    <!-- Print Prefix 설정: 첫 페이지에만 나옴 -->
    <!-- PrintLocation : 1 - Preview 표시 -->
    <!-- PrintPrint : 6 - Preview + alert message -->
    <Cfg PrintPrefix="&lt;center&gt;&lt;font class=&apos;font_03&apos; style=&apos;font-size:16pt;&apos;&gt;<%= title %>&lt;/font&gt; - &lt;font class=&apos;font_03&apos; style=&apos;font-size:14pt;&apos;&gt;<%= pjtVersion %>&lt;/font&gt;&lt;/center&gt;"/>
    <Cfg PrintLocation="1"/>
    <Cfg PrintPrint="6"/>

    <LeftCols>
        <C Name="TaskName"          Width="300" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
    </LeftCols>

    <Cols>
        <C Name="PlanStartDate"     Width="80"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Date"     Format="yyyy-MM-dd" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="PlanEndDate"       Width="80"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Date"     Format="yyyy-MM-dd" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="ExecStartDate"     Width="80"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Date"     Format="yyyy-MM-dd" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="ExecEndDate"       Width="80"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Date"     Format="yyyy-MM-dd" ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="PlanDuration"      Width="35"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="TaskStateImg"      Width="67"  MinWidth="<%= colMinWidth %>"   PrintWidth="82" Align="Center"  Type="Html"     CanExport="0"/>
        <C Name="TaskCompletion"    Width="50"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="TaskPreferComp"    Width="50"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Float"    Format="0.0"   ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="PlanManHour"       Width="40"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Float"    Format="0.0"    ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="PersonRole"        Width="90"  MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Enum"     EnumKeys="<%= personRoleEnumKey %>"     Enum="<%= personRoleEnum %>"
           ExportStyle="text-align:left;vertical-align:middle"
        />
        <C Name="TaskMaster"        Width="50"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Text"     ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="TaskMember"        Width="70"  MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="MilestoneType"     Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= milestoneTypeEnumKey %>"  Enum="<%= milestoneTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="OptionType"        Width="50"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= optionTypeEnumKey %>"     Enum="<%= optionTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="TaskType"          Width="60"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= taskTypeEnumKey %>"       Enum="<%= taskTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="DRValue"           Width="50"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"
           Visible="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"                 CanExport="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"
           CanPrint="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"                CanHide="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"
        /><!-- 금형 Project가 아닐 경우에만 DR 값 표시 -->
        <C Name="TaskLevel"         Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskSeq"           Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskOid"           Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ParentHierarchy"   Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"     CanGroup="1"    GroupChar="/"/>
        <C Name="ParentTaskOid"     Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="OemPlan"           Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ExecDate"          Visible="0" Type="Date"     Format="yyyy-MM-dd"             Range="1"       CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskAncestors"     Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskDescendants"   Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskState"         Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
    </Cols>

    <RightCols>
        <!-- Gantt chart column, basic settings -->
        <!-- GanttBackground : 배경색 표시할 기간 (ex. 주말 범위 설정하면 회색 표시) -->
        <!-- GanttIncorrectDependencies : 선후행 연결 허용 조건 (0 - none, 1 - only dependencies with start before end, 2 - also dependencies with start after end) -->
        <!-- GanttCorrectDependencies : 잘못된 선후행 연결 자동 변경 여부 (0 – no, 1 – automatic update all dependent tasks, 2 – ask user) -->
        <!-- GanttCheckDependencies : 순환 선후행 연결 가능 여부 (0 – don’t check, 1 - restrict circular dependencies, 2 - ask user, 3 - shows alert about the circular dependency) -->
        <!-- GanttMark : 오늘 날짜 라인 표시 -->
        <!-- GanttChartMaxStart : Gantt Chart 표시 시작 날짜 -->
        <!-- GanttChartMinEnd : Gantt Chart 표시 완료 날짜 -->
        <!-- GanttRun="OemPlan" : 자동차 일정 표시를 위한 Gantt Run 설정 -->
        <C Name="G" Type="Gantt" GanttClass="GanttNavy"
           GanttStart="PlanStartDate" GanttEnd="PlanEndDate"
           GanttComplete="TaskPreferComp"
           GanttAncestors="TaskAncestors" GanttDescendants="TaskDescendants"
           GanttMainTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%" GanttMilestoneTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%"
           GanttFlowTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%"
           GanttMark="<%= today %>#1"
           GanttChartMaxStart="<%= ganttChartMaxStart %>"
           GanttChartMinEnd="<%= ganttChartMinEnd %>"
           GanttRun="OemPlan"
           GanttFlow="ExecDate"
           GanttZoom="week"
           GanttSmoothZoom="1"
           CanExport="0"
        />
    </RightCols>

    <Zoom>
        <Z Name="day"
           GanttDataUnits="d"
           GanttLastUnit="d"
           GanttUnits="d"
           GanttChartRound="w"
           GanttWidth="20"
           GanttHeader1="w#yyyy-MM-dd" GanttHeader2="d#dd"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="week"
           GanttDataUnits="d"
           GanttLastUnit="d"
           GanttUnits="d"
           GanttChartRound="w"
           GanttWidth="10"
           GanttHeader1="M#yyyy-MM" GanttHeader2="w#'w 'ddddddd"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="month"
           GanttDataUnits="w"
           GanttLastUnit="w"
           GanttUnits="w"
           GanttChartRound="M"
           GanttWidth="30"
           GanttHeader1="y#yyyy" GanttHeader2="M#MM"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="year"
           GanttDataUnits="M"
           GanttLastUnit="M"
           GanttUnits="M"
           GanttChartRound="y"
           GanttWidth="60"
           GanttHeader1="y#yyyy"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
    </Zoom>

    <Header CanDelete="0" Align="Center"
            TaskName="Task"                     TaskNameExportStyle="text-align:left;vertical-align:middle"
            PlanStartDate="<%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%>"              PlanStartDateExportStyle="text-align:center;vertical-align:middle"
            PlanEndDate="<%=messageService.getString("e3ps.message.ket_message", "00820") %><%--계획{0}완료일--%>"                PlanEndDateExportStyle="text-align:center;vertical-align:middle"
            ExecStartDate='<%=messageService.getString("e3ps.message.ket_message", "02044") %><%--실적시작일--%>'                 ExecStartDateExportStyle="text-align:center;vertical-align:middle"
            ExecEndDate='<%=messageService.getString("e3ps.message.ket_message", "02045") %><%--실적완료일--%>'                   ExecEndDateExportStyle="text-align:center;vertical-align:middle"
            PlanDuration='<%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%>'                        PlanDurationExportStyle="text-align:center;vertical-align:middle"
            TaskStateImg='<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>'
            TaskCompletion="<%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%>&#x0a;(%)"           TaskCompletionExportStyle="text-align:center;vertical-align:middle"
            TaskPreferComp="<%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%>&#x0a;(%)"           TaskPreferCompExportStyle="text-align:center;vertical-align:middle"
            PlanManHour="<%=messageService.getString("e3ps.message.ket_message", "07101") %><%--공수(hr)--%>"                     PlanManHourExportStyle="text-align:center;vertical-align:middle"
            PersonRole="Role"                   PersonRoleExportStyle="text-align:left;vertical-align:middle"
            TaskMaster="<%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%>"                        TaskMasterExportStyle="text-align:center;vertical-align:middle"
            TaskMember="<%=messageService.getString("e3ps.message.ket_message", "02757") %><%--참여자--%>"                        TaskMemberExportStyle="text-align:left;vertical-align:middle"
            MilestoneType="<%=messageService.getString("e3ps.message.ket_message", "00324", "&#x0a;") %><%--Milestone{0}여부--%>" MilestoneTypeExportStyle="text-align:center;vertical-align:middle"
            OptionType="<%=messageService.getString("e3ps.message.ket_message", "03133", "&#x0a;") %><%--필수{0}여부--%>"         OptionTypeExportStyle="text-align:center;vertical-align:middle"
            TaskType="<%=messageService.getString("e3ps.message.ket_message", "00502") %><%--Task종류--%>"                        TaskTypeExportStyle="text-align:center;vertical-align:middle"
            DRValue="<%=messageService.getString("e3ps.message.ket_message", "00163") %><%--DR값--%>"                             DRValueExportStyle="text-align:center;vertical-align:middle"
            TaskAncestors="<%=messageService.getString("e3ps.message.ket_message", "01825") %><%--선행 Task--%>"                  TaskAncestorsExportStyle="text-align:left;vertical-align:middle"
            TaskDescendants="<%=messageService.getString("e3ps.message.ket_message", "03259") %><%--후행 Task--%>"                TaskDescendantsExportStyle="text-align:left;vertical-align:middle"
    />

    <Def>
        <D Name="Root" GGanttIcons="1" GGanttClass="GanttMaroon"/>
        <D Name="Root" Expanded="1" Calculated="1" CalcOrder="PlanStartDate,PlanEndDate,PlanManHour,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Root" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Root" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Root" PlanManHourFormula="sum()"/>
        <D Name="Summary" GGanttIcons="1" GGanttClass="GanttTeal"/>
        <D Name="Summary" Expanded="1" Calculated="1" CalcOrder="PlanStartDate,PlanEndDate,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Summary" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Summary" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Task"/>
    </Def>

    <Toolbar Cells='ExpandAll,CollapseAll,ZoomIn,ZoomOut,ZoomFit,Zoom,Space1,Reload,Export,Print,Columns,Space2,DDay,Version'
             ZoomType="SelectGanttZoom" ZoomWidth="45" ZoomLeft="3"
             Space1Type="Html" Space1Width="5"
             Space2Label="<%= space %>" Space2Type="Html" Space2RelWidth="1"
             DDayType="Html" DDayWidth="120"
             DDayFormula="'<%= todayDateStr %>' && Grid.GetFirst() && Grid.GetFirst().PlanEndDate && '<%= todayDateStr %>' &lt;= DateToString(Grid.GetFirst().PlanEndDate, 'yyyy-MM-dd') ? Math.round(Grid.DiffGanttDate('<%= todayDateStr %>', DateToString(Grid.GetFirst().PlanEndDate, 'yyyy-MM-dd'), 'd')) * -1 : '-'"
             DDayHtmlPrefix="&lt;font class='font_03' style='font-size:10pt;'&gt;<%= dday %>" DDayHtmlPostfix="&lt;/font&gt;"
             VersionType="Html" VersionWidth="130" VersionTip='<%=messageService.getString("e3ps.message.ket_message", "02366") %><%--일정변경이력조회--%>'
             VersionHtmlPrefix="&lt;a href='#' onclick='javascript:openProjectChangeHistoryPopup(&quot;<%= oid %>&quot;);'&gt;&lt;font class='font_03' style='font-size:10pt;<%= pjtVersionStyle %>'&gt;<%= pjtVersion %>"
             VersionHtmlPostfix="&lt;/font&gt;&lt;/a&gt;"
             Styles='2' Space='0' Kind='Topbar' Calculated='1' Link='0'
    />
</Grid>
