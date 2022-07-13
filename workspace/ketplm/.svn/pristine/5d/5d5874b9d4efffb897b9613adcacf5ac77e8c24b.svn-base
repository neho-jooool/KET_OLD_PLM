<%@page import="e3ps.project.ElectronTemplateProject"%>
<%@page import="e3ps.project.ProductTemplateProject"%>
<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.project.TemplateProject,
                e3ps.project.MoldTemplateProject,
                java.text.SimpleDateFormat,e3ps.common.code.*,java.util.*"
                 %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    /*-----------------------------------------------------------------------------------------------------------------
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/
    //response.addHeader("Cache-Control","max-age=1, must-revalidate");

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
    String newTypeEnumKey       = request.getParameter("Newtypeenumkey");
    String newTypeEnum          = request.getParameter("Newtypeenum");
    String modifyTypeEnumKey    = request.getParameter("Modifytypeenumkey");
    String modifyTypeEnum       = request.getParameter("Modifytypeenum");
    String commonTypeEnumKey    = request.getParameter("Commontypeenumkey");
    String commontypeEnum       = request.getParameter("Commontypeenum");
    String mdrawTypeEnumKey    = request.getParameter("Mdrawtypeenumkey");
    String mdrawTypeEnum       = request.getParameter("Mdrawtypeenum");
    String hwTypeEnumKey    = request.getParameter("Hwtypeenumkey");
    String hwTypeEnum       = request.getParameter("Hwtypeenum");
    String swTypeEnumKey    = request.getParameter("Swtypeenumkey");
    String swTypeEnum       = request.getParameter("Swtypeenum");
    String mTypeEnumKey    = request.getParameter("Mtypeenumkey");
    String mTypeEnum       = request.getParameter("Mtypeenum");
    String pTypeEnumKey    = request.getParameter("Ptypeenumkey");
    String pTypeEnum       = request.getParameter("Ptypeenum");
    String buyTypeEnumKey    = request.getParameter("Buytypeenumkey");
    String buyTypeEnum       = request.getParameter("Buytypeenum");
    String systemTypeEnumKey    = request.getParameter("Systemtypeenumkey");
    String systemTypeEnum       = request.getParameter("Systemtypeenum");
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
    String divide               = request.getParameter("Divide");
    if(divide == null || divide.equals("")){
        divide = "";
    }
    
    String title = "";
    String pjtName = "";
    String space = " ";
    String today = DateUtil.getCurrentDateString(new SimpleDateFormat ( "yyyy-MM-dd" ));
    String pjtType = "";
    Object obj = CommonUtil.getObject(oid);
    TemplateProject project = null;
   
    // Title - PJT_NO (PJT_NAME)
    if ( obj instanceof TemplateProject ) {

        project = (TemplateProject)obj;

        if ( project != null ) {

            // Project Title
            pjtName = StringUtil.checkNull(project.getPjtName());
            title = pjtName;

        }
    }
    
    String projectType = "REVIEW";

    if (obj instanceof MoldTemplateProject) {
        projectType = "MOLD";
    }

    if (obj instanceof ProductTemplateProject || obj instanceof ElectronTemplateProject) {
        projectType = "PRODUCT";
    }
    
  //  String divide = request.getParameter("divide");
%>

<?xml version="1.0"?>
<Grid>
    <!--<Cfg tag is splitted by attributes just for comments, you should merge them in your standard applications>-->
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ChangeWBSScheduleGrid' />
     <!-- 쿠키 사용 여부 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='<%= cookiesType %>'/>

    <!-- ID 체계 설정 -->
    <Cfg IdPrefix='T' IdChars='0123456789' NumberId='1'/>

    <!-- Undo 기능 설정 -->
    <Cfg Undo='1'/>

    <!-- 정렬 관련 설정 : 0 - a user cannot sort grid, 1 - a user can sort grid -->
    <Cfg Sorting='0'/><!-- TreeGrid는 Level이 있는 구조로 사용자가 원하는 형태로 정렬되지 않으므로, 정렬 기능 사용하지 않도록 함 -->

    <!-- 셀 편집 관련 설정 -->
    <Cfg Editing='<%= "modify".equals(divide) ? 1 : 0%>'/><!-- 0 - read only, 1 - editable(default), 2 - preview  -->

    <!-- 행 삭제 관련 설정 -->
    <Cfg Deleting='<%= "modify".equals(divide) ? 1 : 0%>'/><!-- 0 - disables Panel Delete button, 1 - can be deleted from grid -->
    <Cfg ShowDeleted='0'/><!-- 0 - deleted rows are hidden, 1 - deleted rows are still visible -->

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting="0"/>

    <!-- 그리드 사이즈 관련 설정 -->
    <Cfg MaxHeight="1"/>
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
    <Panel Move="<%= "modify".equals(divide) ? 1 : 0%>" Copy="<%= "modify".equals(divide) ? 1 : 0%>" CanHide="0"
           CopyMenu="CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd"
    />

    <!-- Main 컬럼 설정 -->
    <Cfg MainCol="TaskName"/>

    <!-- Print Header 설정 -->
    <Cfg PrintHead="<%= title %>"/>

    <!-- Action 설정 -->
    <!-- Toolbar 'Add' 아이콘 클릭 시 표시할 메뉴 -->
    <Actions OnClickButtonAdd="CopyMenuF"
             OnClickButtonAddChild="CopyMenuF"
             OnClickPanelCopy="FocusRow,CopyMenuF"/>

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
        <Alert DelRow="%d <%=messageService.getString("e3ps.message.ket_message", "00494") %><%--Task를 삭제하시겠습니까?--%>"/>
    </Lang>
    
    <Head>
    <Header CanDelete="0" CanCopy="0" Align="Center"
            id="Headertop"
            Num="" Spanned='1'
            TaskName=""          TaskNameExportStyle="text-align:left;vertical-align:middle"
            TaskNameEn=""          TaskNameEnExportStyle="text-align:left;vertical-align:middle"
            PlanStartDate=""              PlanStartDateExportStyle="text-align:center;vertical-align:middle"
            PlanEndDate=""                PlanEndDateExportStyle="text-align:center;vertical-align:middle"
            PlanDuration=""                        PlanDurationExportStyle="text-align:center;vertical-align:middle"
            PersonRole=""               PersonRoleExportStyle="text-align:left;vertical-align:middle"
            MilestoneType="" MilestoneTypeExportStyle="text-align:center;vertical-align:middle"
            OptionType=""         OptionTypeExportStyle="text-align:center;vertical-align:middle"
            TaskType=""                        TaskTypeExportStyle="text-align:center;vertical-align:middle"
            TaskTypeCategory=""                TaskTypeCategoryExportStyle="text-align:center;vertical-align:middle"
            MainScheduleCode=""                MainScheduleCodeExportStyle="text-align:center;vertical-align:middle"
            ScheduleType=""                        ScheduleTypeExportStyle="text-align:center;vertical-align:middle"
            PriorityControl=""                        PriorityControlExportStyle="text-align:center;vertical-align:middle"
            DRValue=""                             DRValueExportStyle="text-align:center;vertical-align:middle"
            DRValueCondition=""                             DRValueConditionExportStyle="text-align:center;vertical-align:middle"
            PlanWorkTime="" PlanWorkTimeExportStyle="text-align:center;vertical-align:middle"
            New="<%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%>"  NewSpan="2"
            Modify=""
            Common="Category" CommonSpan="8" CommonExportStyle="text-align:center;vertical-align:middle"
            Mdraw=""  MdrawExportStyle="text-align:center;vertical-align:middle"
            HW=""     HWExportStyle="text-align:center;vertical-align:middle"
            SW=""     SWExportStyle="text-align:center;vertical-align:middle"
            M=""      MExportStyle="text-align:center;vertical-align:middle"
            P=""      PExportStyle="text-align:center;vertical-align:middle"
            Buy=""    BuyExportStyle="text-align:center;vertical-align:middle"
            System="" SystemExportStyle="text-align:center;vertical-align:middle"
            TaskAncestors=""                  TaskAncestorsExportStyle="text-align:left;vertical-align:middle"
            TaskDescendants=""                TaskDescendantsExportStyle="text-align:left;vertical-align:middle"
            G =" "
    />
    <Header CanDelete="0" CanCopy="0" Align="Center"
            id="Header" Spanned='1'
            Num="ID"
            TaskName="Task"                     TaskNameExportStyle="text-align:left;vertical-align:middle"
            TaskNameEn="Task(EN)"                     TaskNameEnExportStyle="text-align:left;vertical-align:middle"
            PlanStartDate="<%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%>"              PlanStartDateExportStyle="text-align:center;vertical-align:middle"
            PlanEndDate="<%=messageService.getString("e3ps.message.ket_message", "00820") %><%--계획{0}완료일--%>"                PlanEndDateExportStyle="text-align:center;vertical-align:middle"
            PlanDuration="<%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%>"                        PlanDurationExportStyle="text-align:center;vertical-align:middle"
            PersonRole="Role"     PersonRoleRowSpan="2"              PersonRoleExportStyle="text-align:left;vertical-align:middle"
            MilestoneType="<%=messageService.getString("e3ps.message.ket_message", "00324", "&#x0a;") %><%--Milestone{0}여부--%>" MilestoneTypeExportStyle="text-align:center;vertical-align:middle"
            OptionType="<%=messageService.getString("e3ps.message.ket_message", "03133", "&#x0a;") %><%--필수{0}여부--%>"         OptionTypeExportStyle="text-align:center;vertical-align:middle"
            TaskType="<%=messageService.getString("e3ps.message.ket_message", "00502") %><%--Task종류--%>"                        TaskTypeExportStyle="text-align:center;vertical-align:middle"
            TaskTypeCategory="<%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%>"                    TaskTypeCategoryExportStyle="text-align:center;vertical-align:middle"
            MainScheduleCode="주요일정식별코드"                                                                                     MainScheduleCodeExportStyle="text-align:center;vertical-align:middle"
            PriorityControl="중점관리"                                                                     PriorityControlExportStyle="text-align:center;vertical-align:middle"
            ScheduleType="Schedule 구분<%--Schedule 구분--%>"                                                                     ScheduleTypeExportStyle="text-align:center;vertical-align:middle"
            DRValue="<%=messageService.getString("e3ps.message.ket_message", "00163") %><%--DR값--%>"                             DRValueExportStyle="text-align:center;vertical-align:middle"
            DRValueCondition="조건부값"                             DRValueConditionExportStyle="text-align:center;vertical-align:middle"
            PlanWorkTime="<%=messageService.getString("e3ps.message.ket_message", "07101") %><%--계획작업시간--%>" PlanWorkTimeExportStyle="text-align:center;vertical-align:middle"
            New="<%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%>" NewExportStyle="text-align:center;vertical-align:middle" 
            Modify="Modify" ModifyExportStyle="text-align:center;vertical-align:middle" 
            Common="<%=messageService.getString("e3ps.message.ket_message", "00895") %><%--공통--%>" CommonExportStyle="text-align:center;vertical-align:middle" 
            Mdraw="<%=messageService.getString("e3ps.message.ket_message", "07102") %><%--기구--%>"  MdrawExportStyle="text-align:center;vertical-align:middle"
            HW="HW"     HWExportStyle="text-align:center;vertical-align:middle"
            SW="SW"     SWExportStyle="text-align:center;vertical-align:middle"
            M="M"      MExportStyle="text-align:center;vertical-align:middle"
            P="P"      PExportStyle="text-align:center;vertical-align:middle"
            Buy="<%=messageService.getString("e3ps.message.ket_message", "07103") %><%--구매--%>"    BuyExportStyle="text-align:center;vertical-align:middle"
            System="<%=messageService.getString("e3ps.message.ket_message", "01874") %><%--설비--%>" SystemExportStyle="text-align:center;vertical-align:middle"
            TaskAncestors="<%=messageService.getString("e3ps.message.ket_message", "01825") %><%--선행 Task--%>"                  TaskAncestorsExportStyle="text-align:left;vertical-align:middle"
            TaskDescendants="<%=messageService.getString("e3ps.message.ket_message", "03259") %><%--후행 Task--%>"                TaskDescendantsExportStyle="text-align:left;vertical-align:middle"
            
    />
    </Head>
    
    <LeftCols>
        <C Name="Num"                Width="45"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Text"     CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskName"          Width="280" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="TaskNameEn"          Width="280" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Text"     ExportStyle="text-align:left;vertical-align:middle"/>
        <C Name="PlanStartDate"     Width="100" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Date"     Format="yyyy-MM-dd"             DefaultDate="<%= today %>"  EditFormat="yyyy-MM-dd"     CanExport="0"   CanPrint="0"/>
        <C Name="PlanEndDate"       Width="100" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Date"     Format="yyyy-MM-dd"             DefaultDate="<%= today %>"  EditFormat="yyyy-MM-dd"     CanExport="0"   CanPrint="0"/>
        <C Name="PlanDuration"      Width="35"  MinWidth="<%= colMinWidth %>"   Align="Right"   Type="Int"      Formula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : (MilestoneType == '1' && PlanEndDate ? 1 : '')"
           ExportStyle="text-align:right;vertical-align:middle"
        />
    </LeftCols>

    <Cols>
        <C Name="PersonRole"        Width="105" MinWidth="<%= colMinWidth %>"   Align="Left"    Type="Enum"     EnumKeys="<%= personRoleEnumKey %>"     Enum="<%= personRoleEnum %>"
           ExportStyle="text-align:left;vertical-align:middle" 
        />
        <C Name="MilestoneType"     Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= milestoneTypeEnumKey %>"  Enum="<%= milestoneTypeEnum %>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="OptionType" Visible="<%= "MOLD".equals(projectType) ? "1" : "0" %>"       Width="50"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= optionTypeEnumKey %>"     Enum="<%= optionTypeEnum %>"
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
        <C Name="TaskTypeCategory"          Width="70"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   Related="TaskType"    EnumKeys="|"   Enum일반="|-" EnumDR="|-" Enum신뢰성평가 ="|-" EnumDR="|-" EnumKeys신뢰성평가 ="|-" EnumCOST ="<%=str2%>" EnumKeysCOST ="<%=keys%>" EnumDR ="<%=DRstr%>" EnumKeysDR ="<%=DRkeys%>" Enum신뢰성평가 ="<%=TrustStr%>" EnumKeys신뢰성평가 ="<%=TrustKeys%>" EnumGate="<%=str%>"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        
        
        
        <C Name="MainScheduleCode"          Width="100"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= mainScheduleCodeEnumKey %>" Enum="<%= mainScheduleCodeEnum %>" CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="PriorityControl"          Width="100"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= priorityControlEnumKey %>" Enum="<%= priorityControlEnum %>" CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        
        <C Name="ScheduleType"          Width="100"  MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"   EnumKeys="<%= scheduleTypeEnumKey %>" Enum="<%= scheduleTypeEnum %>" CanEmpty ="1"
           ExportStyle="text-align:center;vertical-align:middle"
        />
        <C Name="DRValue"           Width="50"  MinWidth="<%= colMinWidth %>"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"
           Visible="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"                 CanExport="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"
           CanPrint="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"                CanHide="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"
        /><!-- 금형 WBS가 아닐 경우에만 DR 값 표시 -->
        <C Name="DRValueCondition"           Width="70"  MinWidth="<%= colMinWidth %>"   Type="Int"      ExportStyle="text-align:right;vertical-align:middle"
           Visible="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"                 CanExport="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"
           CanPrint="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"                CanHide="<%= !"MOLD".equals(projectType) ? "1" : "0" %>"
        /><!-- 금형 WBS가 아닐 경우에만 DR조건부 값 표시 -->
        <C Name="DebugSub"          Visible="0" Type="Enum"     EnumKeys="<%= debugSubEnumKey %>"       Enum="<%= debugSubEnum %>"      CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="DebugN"            Visible="0" Type="Enum"     EnumKeys="<%= debugNEnumKey %>"         Enum="<%= debugNEnum %>"        CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="Ncha"              Visible="0" Type="Int"      CanEdit="0"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskLevel"         Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskSeq"           Visible="0" Type="Int"      CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="TaskOid"           Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="ParentHierarchy"   Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"     CanGroup="1"    GroupChar="/"/>
        <C Name="ParentTaskOid"     Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <!-- 2014.07.15 프로젝트 별 속성추가 -->
        <C Name="PlanWorkTime"      Visible="1" Type="Text" Align="Center"/>
        <C Name="New"               Visible="<%= "PRODUCT".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= newTypeEnumKey %>"     Enum="<%= newTypeEnum %>" />
        <C Name="Modify"            Visible="<%= "PRODUCT".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= modifyTypeEnumKey %>"     Enum="<%= modifyTypeEnum %>" />
        <C Name="Common"            Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= commonTypeEnumKey %>"     Enum="<%= commontypeEnum %>" />
        <C Name="Mdraw"             Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= mdrawTypeEnumKey %>"     Enum="<%= mdrawTypeEnum %>" />
        <C Name="HW"                Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= hwTypeEnumKey %>"     Enum="<%= hwTypeEnum %>" />
        <C Name="SW"                Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= swTypeEnumKey %>"     Enum="<%= swTypeEnum %>" />
        <C Name="M"                 Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= mTypeEnumKey %>"     Enum="<%= mTypeEnum %>" />
        <C Name="P"                 Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= pTypeEnumKey %>"     Enum="<%= pTypeEnum %>" />
        <C Name="Buy"               Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= buyTypeEnumKey %>"     Enum="<%= buyTypeEnum %>" />
        <C Name="System"            Visible="<%= !"MOLD".equals(projectType) ? 1 : 0 %>" MinWidth="<%= colMinWidth %>"   Align="Center"  Type="Enum"     EnumKeys="<%= systemTypeEnumKey %>"     Enum="<%= systemTypeEnum %>" />
        <C Name="TaskAncestors"     Visible="1" Type="Text"/>
        <C Name="TaskDescendants"   Visible="1" Type="Text"/>
        <C Name="DeleteFlag"        Visible="0" Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
    </Cols>

    <RightCols>
        <!-- Gantt chart column, basic settings -->
        <!-- GanttBackground : 배경색 표시할 기간 (ex. 주말 범위 설정하면 회색 표시) -->
        <!-- GanttIncorrectDependencies : 선후행 연결 허용 조건 (0 - none, 1 - only dependencies with start before end, 2 - also dependencies with start after end) -->
        <!-- GanttCorrectDependencies : 잘못된 선후행 연결 자동 변경 여부 (0 – no, 1 – automatic update all dependent tasks, 2 – ask user) -->
        <!-- GanttCheckDependencies : 순환 선후행 연결 가능 여부 (0 – don’t check, 1 - restrict circular dependencies, 2 - ask user, 3 - shows alert about the circular dependency) -->
        <!-- GanttBaseCanEdit : Gantt Chart에서 Project 계획시작일 편집 가능 여부 -->
        <!-- GanttFinishCanEdit : Gantt Chart에서 Project 계획완료일 편집 가능 여부 -->
        <C Name="G" Type="Gantt" GanttClass="GanttNavy"
           GanttStart="PlanStartDate" GanttEnd="PlanEndDate"
           GanttAncestors="TaskAncestors" GanttDescendants="TaskDescendants"
           GanttIncorrectDependencies="1"
           GanttCorrectDependencies="0"
           GanttCheckDependencies="1"
           GanttDataUnits="d"
           GanttLastUnit="d"
           GanttUnits="d"
           GanttChartRound="w"
           GanttEdit="Main,Dependency"
           GanttMenu="DelGanttDep,-,DelGanttMainBar"
           GanttBackground="w#2013-01-05 00:00~2013-01-07 00:00"
           GanttHeader1="w#yyyy-MM-dd" GanttHeader2="d#dd"
           GanttMainTip="[*TaskName*] d: *PlanDuration*" GanttMilestoneTip="[*TaskName*] d: *PlanDuration*"
           GanttBaseCanEdit="0"
           GanttFinishCanEdit="0"
           GanttZoom="week"
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
    
    <Def>
        <D Name="Root" GGanttIcons="1" GGanttClass="GanttMaroon"/>
        <D Name="Root" Expanded="1" Calculated="1" CalcOrder="PlanStartDate,PlanEndDate,PlanDuration,PlanWorkTime,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Root" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Root" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Root" PlanDurationFormula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''"/>
        <D Name="Root" PlanWorkTimeFormula="sum()"/>
        <D Name="Summary" GGanttIcons="1" GGanttClass="GanttTeal"/>
        <D Name="Summary" Expanded="1" CalculatedChanges="1" CalcOrder="PlanStartDate,PlanEndDate,PlanDuration,PlanWorkTime,G"/> <!-- Group of task calculates summary of the tasks -->
        <D Name="Summary" PlanStartDateFormula="minimum(min('PlanStartDate'),min('PlanEndDate'))"/> <!-- Gets the first start date from its children -->
        <D Name="Summary" PlanEndDateFormula="maximum(max('PlanStartDate'),max('PlanEndDate'))"/> <!-- Gets the last end date from its children -->
        <D Name="Summary" PlanDurationFormula="PlanStartDate ? Math.round(Grid.DiffGanttDate(PlanStartDate, PlanEndDate, 'd')) + 1 : ''"/>
        <D Name="Summary" PlanWorkTimeFormula="sum()" />
        <D Name="Task"/>
    </Def>

    <Toolbar Cells='<%= "modify".equals(divide) ? "Save,Add,AddChild,Outdent,Indent,Undo,Redo,ExpandAll,CollapseAll,ZoomIn,ZoomOut,ZoomFit,Space1,Reload,Export,Print,Columns,Zoom": "Zoom"%>'
             ZoomType="SelectGanttZoom" ZoomWidth="45" ZoomLeft="3"
             AddCopyMenu="CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow"
             AddChildCopyMenu="AddChild,AddChildEnd"
             Space1Type="Html" Space1Width="20"
             Styles="2" Space="0" Kind="Topbar" Calculated="1" Link="0"
    />
</Grid>
