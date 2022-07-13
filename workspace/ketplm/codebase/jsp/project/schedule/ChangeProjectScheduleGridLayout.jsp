<%@page import="e3ps.project.ElectronTemplateProject"%>
<%@page import="e3ps.project.ProductProject"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.project.E3PSProject,
                e3ps.project.MoldProject,
                java.text.SimpleDateFormat,
                java.util.*,
                e3ps.common.code.*"
                 %>

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
    String commonTypeEnumKey    = request.getParameter("CommonTypeEnumKey");
    String commonTypeEnum       = request.getParameter("CommonTypeEnum");
    String taskTypeEnumKey      = request.getParameter("Tasktypeenumkey");
    String taskTypeEnum         = request.getParameter("Tasktypeenum");
    String scheduleTypeEnumKey  = request.getParameter("Scheduletypeenumkey");
    String scheduleTypeEnum     = request.getParameter("Scheduletypeenum");
    String priorityControlEnumKey  = request.getParameter("Prioritycontrolenumkey");
    String priorityControlEnum     = request.getParameter("Prioritycontrolenum");
    String mainScheduleCodeEnumKey  = request.getParameter("Mainschedulecodeenumkey");
    String mainScheduleCodeEnum     = request.getParameter("Mainschedulecodeenum");
    String debugSubEnumKey      = request.getParameter("Debugsubenumkey");
    String debugSubEnum         = request.getParameter("Debugsubenum");
    String debugNEnumKey        = request.getParameter("Debugnenumkey");
    String debugNEnum           = request.getParameter("Debugnenum");
    String debugTaskEnumKey     = request.getParameter("Debugtaskenumkey");
    String debugTaskEnum        = request.getParameter("Debugtaskenum");
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
            
            if(project instanceof TemplateProject) {
                pjtType = "REVIEW";
            }
            
            if(project instanceof ProductProject) {
                pjtType = "PRODUCT";
            }
            
        }
    }
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ChangeProjectScheduleGrid' />
     <!-- 쿠키 사용 여부 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%= cookiesType %>'/>
    <Cfg GanttLap="1"/>

    <!-- ID 체계 설정 -->
    <Cfg IdPrefix='T' IdChars='0123456789' NumberId='1'/>

    <!-- Undo 기능 설정 -->
    <Cfg Undo='1'/>

    <!-- 정렬 관련 설정 : 0 - a user cannot sort grid, 1 - a user can sort grid -->
    <Cfg Sorting='0'/><!-- TreeGrid는 Level이 있는 구조로 사용자가 원하는 형태로 정렬되지 않으므로, 정렬 기능 사용하지 않도록 함 -->

    <!-- 셀 편집 관련 설정 -->
    <Cfg Editing='1'/><!-- 0 - read only, 1 - editable(default), 2 - preview -->

    <!-- 행 삭제 관련 설정 -->
    <Cfg Deleting='1'/><!-- 0 - disables Panel Delete button, 1 - can be deleted from grid -->
    <Cfg ShowDeleted='0'/><!-- 0 - deleted rows are hidden, 1 - deleted rows are still visible -->

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting="0"/>

    <Cfg CopySelected="1" CopyPasteTree="1"/>
    <Cfg Dragging="1"/>
    <Cfg Dropping="1"/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg ConstHeight='0' ConstWidth='1'  /> 
     <Cfg MaxHeight='1' MinHeight='0'/>
     <Cfg MinLeftWidth="535"
         MidWidth="300" MinMidWidth="150"
         RightWidth="500" MinRightWidth="300"
    />
    

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1'/> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates'/>

    <!-- 간트챠트 그리드 기본 스타일 설정 -->
    <Cfg Style='<%= ganttGridStyle %>'/>

    <!-- 기타 설정 -->
    <Cfg SaveSession='1'/>
    <Cfg UsePrefix='1'/><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='0'/> <!-- Custom style setting, every third row will have different color -->
    <Cfg ScrollLeftLap='0'/><!-- Saves horizontal scroll in Gantt to cookies -->

    <!-- Panel 설정 -->
    <Panel Move="1" Copy="1" CanHide="0"
           CopyMenu="CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd"
    />

    <!-- Main 컬럼 설정 -->
    <Cfg MainCol="TaskName"/>

    <!-- Print Prefix 설정: 첫 페이지에만 나옴 -->
    <!-- PrintLocation : 1 - Preview 표시 -->
    <!-- PrintPrint : 6 - Preview + alert message -->
    <Cfg PrintPrefix="&lt;center&gt;&lt;font class=&apos;font_03&apos; style=&apos;font-size:16pt;&apos;&gt;<%= title %>&lt;/font&gt; - &lt;font class=&apos;font_03&apos; style=&apos;font-size:14pt;&apos;&gt;<%= pjtVersion %>&lt;/font&gt;&lt;/center&gt;"/>
    <Cfg PrintLocation="1"/>
    <Cfg PrintPrint="6"/>

    <!-- Action 설정 -->
    <!-- OnClickButtonAdd : Toolbar 'Add' 아이콘 클릭 시 표시할 메뉴 -->
    <!-- OnClickButtonAddChild : Toolbar 'Add Child' 아이콘 클릭 시 표시할 메뉴 -->
    <!-- OnClickPanelCopy : 행 좌측 Panel 복사 아이콘 클릭 시 표시할 메뉴 -->
    <!-- OnCtrlV : 행 복사 시 선택한 행 아래쪽에 복사(Tree 구조 복사) -->
    <Actions OnClickButtonAdd="CopyMenuF"
             OnClickButtonAddChild="CopyMenuF"
             OnClickPanelCopy="FocusRow,CopyMenuF"
             OnCtrlV="CopyTreeBelowF"/>

    <!-- 용어 재정의 -->
    <Lang>
        <!-- MenuCopy : 복사 메뉴 -->
        <MenuCopy
                CopyRow      ='<%=messageService.getString("e3ps.message.ket_message", "02230") %><%--위에 Task 복사--%>'
                CopyRowBelow ='<%=messageService.getString("e3ps.message.ket_message", "02070") %><%--아래에 Task 복사--%>'
                CopyTree     ='<%=messageService.getString("e3ps.message.ket_message", "02231") %><%--위에 Tree 복사--%>'
                CopyTreeBelow='<%=messageService.getString("e3ps.message.ket_message", "02071") %><%--아래에 Tree 복사--%>'
                AddRow       ='<%=messageService.getString("e3ps.message.ket_message", "02232") %><%--위에 신규 Task 추가--%>'
                AddRowBelow  ='<%=messageService.getString("e3ps.message.ket_message", "02072") %><%--아래에 신규 Task 추가--%>'
                AddChild     ='<%=messageService.getString("e3ps.message.ket_message", "02801") %><%--첫번째 하위 Task 추가--%>'
                AddChildEnd  ='<%=messageService.getString("e3ps.message.ket_message", "01356") %><%--마지막 하위 Task 추가--%>'
        />
        <!-- Alert Message -->
        <Alert DelRow="<%=messageService.getString("e3ps.message.ket_message", "00494") %><%--Task를 삭제하시겠습니까?--%>"/>
    </Lang>

    <LeftCols>
        <C Name="id"                Visible="0" Type="Text"     CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskName"        Width="280" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="TaskNameEn"        Width="280" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="PlanStartDate"     Width="100" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Date"     Format="yyyy-MM-dd"             EditFormat="yyyy-MM-dd"                 ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="PlanEndDate"       Width="100" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Date"     Format="yyyy-MM-dd"             EditFormat="yyyy-MM-dd"                 ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="ExecStartDate"     Visible="0" Type="Date"     Format="yyyy-MM-dd"             CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ExecEndDate"       Visible="0" Type="Date"     Format="yyyy-MM-dd"             CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="PlanDuration"      Width="35"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      Formula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''"
           ExportStyle="text-align:right;vertical-align:middle"
        />
        <C Name="TaskStateImg"      Width="67"  MinWidth="<%= colMinWidth %>"   PrintWidth="82" Align="Center"  Type="Html"     CanExport="0" />
        <C Name="TaskCompletion"    Width="50"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      CanEdit="0"     ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="TaskPreferComp"    Width="50"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Float"    Format="0.0"   CanEdit="0"     ExportStyle="text-align:right;vertical-align:middle"/>
    </LeftCols>

    <Cols>
        <C Name="PlanWorkTime"       Width="40"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Float"    Format="0.0"    ExportStyle="text-align:right;vertical-align:middle"/>
        <C Name="PersonRole"        Width="105" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Enum"     EnumKeys="<%= personRoleEnumKey %>"     Enum="<%= personRoleEnum %>"
           ExportStyle="text-align:left;vertical-align:middle"
        />
        <C Name="TaskMaster"        Width="85"  MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     CanEdit="0"     ExportStyle="text-align:center;vertical-align:middle"/>
        <C Name="TaskMasterId"      Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskMember"        Width="120" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     CanEdit="0"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="TaskMemberId"      Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="MilestoneType"     Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= milestoneTypeEnumKey %>"  Enum="<%= milestoneTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="OptionType"        Width="50"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= optionTypeEnumKey %>"     Enum="<%= optionTypeEnum %>"    CanEdit="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="TaskType"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"  Clear="TaskTypeCategory"  EnumKeys="<%= taskTypeEnumKey %>"       Enum="<%= taskTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
         <%
                    String str ="|";
                    Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForSortingQuery("GATELEVELNAME");
                    if (targetTypeVec != null) {
                        for (int i = 0; i < targetTypeVec.size(); i++) {
                            NumberCode code = (NumberCode) targetTypeVec.get(i);
                           
                            str +="|"+ ((String)code.getName()).replaceAll("Gate", "");
                        }
                    }
                    
                    String str2 ="|";
                    String keys = "|";
                    NumberCode costTypeCode = NumberCodeHelper.manager.getNumberCode("TASKTYPE", "COST");
                    
                    ArrayList<NumberCode> list = NumberCodeHelper.manager.getChildNumberCode2(costTypeCode, "");
                    
                    for(NumberCode numberCode : list) {
                        if(numberCode.isDisabled()){
                            continue;
                        }
                        
                        String code = numberCode.getCode();
                        String display = numberCode.getName();
                        if("PRODUCT".equals(pjtType)){
                            if(!"COST013".equals(code) && !"COST014".equals(code) && !"COST015".equals(code) && !"COST016".equals(code)){//제품프로젝트에서 필요한 TaskType만 설정(원가산출요청,원가산출,판매목표가등록,보고서등록)
                                continue;
                            }    
                        }
                        
                        
                        str2 += "|" + display;
                        keys += "|" + code;
                        //map.put(code, display);
                    }
                    
                    String DRstr ="|";
                    String DRkeys = "|";
                    costTypeCode = NumberCodeHelper.manager.getNumberCode("TASKTYPE", "DR");
                    
                    list = NumberCodeHelper.manager.getChildNumberCode2(costTypeCode, "");
                    for(NumberCode numberCode : list) {
                        if(numberCode.isDisabled()){
                            continue;
                        }
                        
                        String code = numberCode.getCode();
                        String display = numberCode.getName();
                                    
                        DRstr += "|" + display;
                        DRkeys += "|" + display;
                    }
                    
                    String TrustStr ="|";
                    String TrustKeys = "|";
                    
                    costTypeCode = NumberCodeHelper.manager.getNumberCode("TASKTYPE", "신뢰성평가");
                    
                    list = NumberCodeHelper.manager.getChildNumberCode2(costTypeCode, "");
                    
                    for(NumberCode numberCode : list) {
                        if(numberCode.isDisabled()){
                            continue;
                        }
                        
                        String code = numberCode.getCode();
                        String display = numberCode.getName();
                                                    
                        TrustStr += "|" + display;
                        TrustKeys += "|" + display;
                    }
        %>
       
        <C Name="TaskTypeCategory"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   Related="TaskType"    EnumKeys="|"   Enum일반="|-" EnumDR="|-" Enum신뢰성평가 ="|-" EnumCOST ="<%=str2%>" EnumKeysCOST ="<%=keys%>" EnumDR ="<%=DRstr%>" EnumKeysDR ="<%=DRkeys%>" Enum신뢰성평가 ="<%=TrustStr%>" EnumKeys신뢰성평가 ="<%=TrustKeys%>" EnumGate="<%=str%>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="MainScheduleCode"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= mainScheduleCodeEnumKey %>" Enum="<%= mainScheduleCodeEnum %>"  CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="ScheduleType"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= scheduleTypeEnumKey %>" Enum="<%= scheduleTypeEnum %>"  CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="PriorityControl"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= priorityControlEnumKey %>" Enum="<%= priorityControlEnum %>"  CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="DRValue"           Width="50"  MinWidth="<%= colMinWidth %>"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"
           Visible="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"                 CanExport="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"
           CanPrint="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"                CanHide="<%= !"MOLD".equals(pjtType) ? "1" : "0" %>"
        /><!-- 금형 Project가 아닐 경우에만 DR 값 표시 -->
        <C Name="DebugSub"          Visible="0" Type="Enum"     EnumKeys="<%= debugSubEnumKey %>"       Enum="<%= debugSubEnum %>"      CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="DebugN"            Visible="0" Type="Enum"     EnumKeys="<%= debugNEnumKey %>"         Enum="<%= debugNEnum %>"        CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="Ncha"              Visible="0" Type="Int"      CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskLevel"         Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskSeq"           Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskOid"           Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ParentHierarchy"   Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"     CanGroup="1"    GroupChar="/"/>
        <C Name="ParentTaskOid"     Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="OemPlan"           Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ExecDate"          Visible="0" Type="Date"     Format="yyyy-MM-dd"             Range="1"       CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskAncestors"     Visible="0" Type="Text"     CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskDescendants"   Visible="0" Type="Text"     CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskState"         Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="DeleteFlag"        Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
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
        <!-- GanttBaseCanEdit : Gantt Chart에서 Project 계획시작일 편집 가능 여부 -->
        <!-- GanttFinishCanEdit : Gantt Chart에서 Project 계획완료일 편집 가능 여부 -->
        <C Name="G" Type="Gantt" GanttClass="GanttNavy"
           GanttStart="PlanStartDate" GanttEnd="PlanEndDate"
           GanttComplete="TaskPreferComp"
           GanttAncestors="TaskAncestors" GanttDescendants="TaskDescendants"
           GanttIncorrectDependencies="1"
           GanttCorrectDependencies="0"
           GanttCheckDependencies="1"
           GanttEdit="Main,Dependency"
           GanttMenu="DelGanttDep,-,DelGanttMainBar"
           GanttMainTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%" GanttMilestoneTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%"
           GanttFlowTip="[*TaskName*] C: *TaskCompletion*%, P: *TaskPreferComp*%"
           GanttMark="<%= today %>#1"
           GanttChartMaxStart="<%= ganttChartMaxStart %>"
           GanttChartMinEnd="<%= ganttChartMinEnd %>"
           GanttRun="OemPlan"
           GanttFlow="ExecDate"
           GanttDataUnits="d"
           GanttLastUnit="d"
           GanttZoom="week"
           GanttSmoothZoom="0"
           GanttBaseCanEdit="0"
           GanttFinishCanEdit="0"
           CanExport="0"
        />
    </RightCols>

    <Zoom>
        <Z Name="day"
           GanttUnits="d"
           GanttChartRound="d"
           GanttWidth="20"
           GanttHeader1="w#yyyy-MM-dd" GanttHeader2="d#dd"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="week"
           GanttUnits="d"
           GanttChartRound="d"
           GanttWidth="10"
           GanttHeader1="M#yyyy-MM" GanttHeader2="w#'w 'ddddddd"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="month"
           GanttUnits="w"
           GanttChartRound="w"
           GanttWidth="30"
           GanttHeader1="y#yyyy" GanttHeader2="M#MM"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
        <Z Name="year"
           GanttUnits="M"
           GanttChartRound="M"
           GanttWidth="60"
           GanttHeader1="y#yyyy"
           GanttBackground="w#2013-01-05~2013-01-07"
        />
    </Zoom>

    <Header CanDelete="0" CanCopy="0" Align="Center"
            id="ID"
            TaskName="Task"                     TaskNameExportStyle="text-align:left;vertical-align:middle"
            TaskNameEn="Task(EN)"                     TaskNameEnExportStyle="text-align:left;vertical-align:middle"
            PlanStartDate="<%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%>"              PlanStartDateExportStyle="text-align:center;vertical-align:middle"
            PlanEndDate="<%=messageService.getString("e3ps.message.ket_message", "00820") %><%--계획{0}완료일--%>"                PlanEndDateExportStyle="text-align:center;vertical-align:middle"
            PlanDuration="<%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%>"                        PlanDurationExportStyle="text-align:center;vertical-align:middle"
            TaskStateImg='<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>'
            TaskCompletion="<%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%>&#x0a;(%)"           TaskCompletionExportStyle="text-align:center;vertical-align:middle"
            TaskPreferComp="<%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%>&#x0a;(%)"           TaskPreferCompExportStyle="text-align:center;vertical-align:middle"
            PlanWorkTime="<%=messageService.getString("e3ps.message.ket_message", "07101") %><%--공수(hr)--%>"                     PlanWorkTimeExportStyle="text-align:center;vertical-align:middle"
            PersonRole="Role"                   PersonRoleExportStyle="text-align:left;vertical-align:middle"
            TaskMaster="<%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%>"                        TaskMasterExportStyle="text-align:center;vertical-align:middle"
            TaskMember="<%=messageService.getString("e3ps.message.ket_message", "02757") %><%--참여자--%>"                        TaskMemberExportStyle="text-align:left;vertical-align:middle"
            MilestoneType="<%=messageService.getString("e3ps.message.ket_message", "00324", "&#x0a;") %><%--Milestone{0}여부--%>" MilestoneTypeExportStyle="text-align:center;vertical-align:middle"
            OptionType="<%=messageService.getString("e3ps.message.ket_message", "03133", "&#x0a;") %><%--필수{0}여부--%>"         OptionTypeExportStyle="text-align:center;vertical-align:middle"
            TaskType="<%=messageService.getString("e3ps.message.ket_message", "00502") %><%--Task종류--%>"                        TaskTypeExportStyle="text-align:center;vertical-align:middle"
            TaskTypeCategory="<%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%>"
            MainScheduleCode="주요일정식별코드"                                                                     MainScheduleCodeExportStyle="text-align:center;vertical-align:middle"
            PriorityControl="중점관리"                                                                     PriorityControlExportStyle="text-align:center;vertical-align:middle"
            ScheduleType="Schedule 구분<%--Schedule 구분--%>"                                                                     ScheduleTypeExportStyle="text-align:center;vertical-align:middle"
            DRValue="<%=messageService.getString("e3ps.message.ket_message", "00163") %><%--DR값--%>"                             DRValueExportStyle="text-align:center;vertical-align:middle"
            TaskAncestors="<%=messageService.getString("e3ps.message.ket_message", "01825") %><%--선행 Task--%>"                  TaskAncestorsExportStyle="text-align:left;vertical-align:middle"
            TaskDescendants="<%=messageService.getString("e3ps.message.ket_message", "03259") %><%--후행 Task--%>"                TaskDescendantsExportStyle="text-align:left;vertical-align:middle"
    />

    <Def>
        <D Name="Root" GGanttIcons="1" GGanttClass="GanttMaroon"/>
        <D Name="Root" Expanded="1" Calculated="1" CalcOrder="PlanStartDate,PlanEndDate,PlanDuration,PlanWorkTime,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Root" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Root" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Root" PlanDurationFormula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''"/>
        <D Name="Root" PlanWorkTimeFormula="sum()"/>
        <D Name="Summary" GGanttIcons="1" GGanttClass="GanttTeal"/>
        <D Name="Summary" Expanded="1" Calculated="1" CalcOrder="PlanStartDate,PlanEndDate,PlanDuration,PlanWorkTime,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Summary" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Summary" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Summary" PlanDurationFormula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''"/>
        <D Name="Summary" PlanWorkTimeFormula="sum()"/>
        <D Name="Task"/>
    </Def>

    <Toolbar Cells="Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,ZoomIn,ZoomOut,ZoomFit,Zoom,Space1,Reload,Export,Print,Columns,Space2,DDay,Version"
             AddCopyMenu="CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow"
             AddChildCopyMenu="AddChild,AddChildEnd"
             ZoomType="SelectGanttZoom" ZoomWidth="45" ZoomLeft="3"
             Space1Type="Html" Space1Width="5"
             Space2Label="<%= space %>" Space2Type="Html" Space2RelWidth="1"
             DDayType="Html" DDayWidth="120"
             DDayFormula="'<%= todayDateStr %>' && Grid.GetFirst() && Grid.GetFirst().PlanEndDate && '<%= todayDateStr %>' &lt;= DateToString(Grid.GetFirst().PlanEndDate, 'yyyy-MM-dd') ? Math.round(Grid.DiffGanttDate('<%= todayDateStr %>', DateToString(Grid.GetFirst().PlanEndDate, 'yyyy-MM-dd'), 'd')) * -1 : '-'"
             DDayHtmlPrefix="&lt;font class='font_03' style='font-size:10pt;'&gt;<%= dday %>" DDayHtmlPostfix="&lt;/font&gt;"
             VersionType="Html" VersionWidth="80" VersionTip='<%=messageService.getString("e3ps.message.ket_message", "02366") %><%--일정변경이력조회--%>'
             VersionHtmlPrefix="&lt;a href='#' onclick='javascript:openProjectChangeHistoryPopup(&quot;<%= oid %>&quot;);'&gt;&lt;font class='font_03' style='font-size:10pt;<%= pjtVersionStyle %>'&gt;<%= pjtVersion %>"
             VersionHtmlPostfix="&lt;/font&gt;&lt;/a&gt;"
             Styles="2" Space="0" Kind="Topbar" Calculated="1" Link="0"
    />
</Grid>
