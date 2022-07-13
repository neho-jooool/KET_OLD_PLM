<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="e3ps.common.util.PropertiesUtil,
                 e3ps.common.util.StringUtil,
                 e3ps.common.util.CommonUtil,
                 e3ps.common.util.DateUtil,
                 e3ps.project.TemplateProject,
                 e3ps.project.MoldTemplateProject,
                 java.text.SimpleDateFormat" %>
                 
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                 

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    /*-----------------------------------------------------------------------------------------------------------------
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/
    //response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String ganttGridStyle  = PropertiesUtil.getGanttGridStyle(); // ExtJs
    String cookiesType     = PropertiesUtil.getSearchGridCookiesType(); // 0
    String alternateType   = PropertiesUtil.getSearchGridAlternateType(); // 
    String colMinWidth     = PropertiesUtil.getSearchColMinWidth(); // 30
    String screenWidth     = "0";
    String screenHeight    = "30";
    
    // TODO TKLEE 다국어
    java.util.Locale loc = e3ps.common.message.KETMessageService.service.getMessageService(((javax.servlet.http.HttpServletRequest) pageContext.getRequest())).getLocale();
    String[] useKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("PARTCLAZUSEYN", loc);
    String[] partGubunKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("PARTCLAZPARTGUBUN", loc);
    String[] partNumCharicsKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("NUMBERINGCHARICS", loc);
    String[] divisionFlagKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("DIVISIONFLAG", loc);
    String[] classPartTypeKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("SPECPARTTYPE", loc);
    String[] partNamingKeyVal = ext.ket.part.classify.service.PartClazHelper.service.getNamingEnumKeyValue(loc);
    String[] partisEcoCarVal = ext.ket.part.classify.service.PartClazHelper.service.getEnumKeyValue("PARTCLAZISECOCAR", loc);
    
    
    Kogger.debug("viewClazListLayout", null, null, useKeyVal);
    Kogger.debug("viewClazListLayout", null, null, partGubunKeyVal);
    
%>

<?xml version="1.0"?>
<Grid>
    
    <!-- 쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함 -->
    <Cfg id='ViewClazListGrid' />
    
     <!-- 쿠키 사용 여부 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing  -->
    <Cfg SuppressCfg='0' />
    
    <!-- ID 체계 설정 -->
    <Cfg IdPrefix='No.' IdChars='0123456789' NumberId='1' />
    
    <!-- Undo 기능 설정 -->
    <Cfg Undo='1' />

    <!-- 정렬 관련 설정 : 0 - a user cannot sort grid, 1 - a user can sort grid -->
    <Cfg Sorting='0' /><!-- TreeGrid는 Level이 있는 구조로 사용자가 원하는 형태로 정렬되지 않으므로, 정렬 기능 사용하지 않도록 함 -->

    <!-- 셀 편집 관련 설정 -->
    <Cfg Editing='1' /><!-- 0 - read only, 1 - editable(default), 2 - preview  -->

    <!-- 행 삭제 관련 설정 -->
    <Cfg Deleting='1' /><!-- 0 - disables Panel Delete button, 1 - can be deleted from grid -->
    <Cfg ShowDeleted='0' /><!-- 0 - deleted rows are hidden, 1 - deleted rows are still visible -->

    <!-- 행, 셀 선택 관련 설정 -->
    <Cfg Selecting='1' SelectingCells='0' />

    <!-- 그리드 사이즈 관련 설정 -->
    <!-- 
    <Cfg MaxHeight="1" />
    -->
    <Cfg ConstHeight='1' ConstWidth='2'/>
    <!-- 
    <Cfg MinLeftWidth="535"          MidWidth="300" MinMidWidth="150"         RightWidth="500" MinRightWidth="500"   />
     -->

    <!-- 엑셀 export 관련 설정 -->
    <Cfg ExportFormat='1' /> <!-- 1 : xls, 2 : csv -->
    <Cfg ExportType='Filtered,Hidden,Strings,Dates' />

    <!-- 간트챠트 그리드 기본 스타일 설정 -->
    <Cfg Style='ExtJs'/>

    <!-- 기타 설정 -->
    <Cfg SaveSession='1' />
    <Cfg UsePrefix='1' /><!-- Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style -->
    <Cfg Alternate='0' /> <!-- Custom style setting, every third row will have different color -->
    <Cfg ScrollLeftLap='0' /><!-- Saves horizontal scroll in Gantt to cookies -->

    <!-- drag & drop 관련 설정 -->
    <Cfg Dragging='0'/>
    
    <!-- Panel 설정 -->
    <Panel Move="0" Copy="1" CanHide="0"
           CopyMenu="AddRow,AddRowBelow,AddChild,AddChildEnd"
    />

    <!-- Main 컬럼 설정 -->
    <Cfg MainCol="classKrName" />
    
    <!-- 테스트용 추가 -->
    <!-- 
    NoHScroll="1"
     -->
    <Cfg NoVScroll="1" SuppressCfg="1" UpCounter="No." UpCounterType="15" FullId="0" AutoIdPrefix='N0.' />

    <!-- Print Header 설정 -->
    <Cfg PrintHead="<%=messageService.getString("e3ps.message.ket_message", "06112")%><%--부품분류--%>"/>

    <!-- Action 설정 -->
    <!-- Toolbar 'Add' 아이콘 클릭 시 표시할 메뉴 -->
    <Actions OnClickButtonAdd="CopyMenuF"
             OnClickButtonAddChild="CopyMenuF"
             OnClickPanelCopy="FocusRow,CopyMenuF" />

    <!-- 용어 재정의 -->
    <Lang>
        <!-- MenuCopy : 복사 메뉴 -->
        <MenuCopy
                CopyRow      ='위에 부품분류 복사<%--위에 Task 복사--%>'
                CopyRowBelow ='아래에 부품분류 복사<%--아래에 Task 복사--%>'
                CopyTree     ='위에 부품분류 복사<%--위에 Tree 복사--%>'
                CopyTreeBelow='아래에 부품분류 복사<%--아래에 Tree 복사--%>'
                AddRow       ='위에 부품분류 복사<%--위에 신규 Task 추가--%>'
                AddRowBelow  ='아래에 부품분류 복사<%--아래에 신규 Task 추가--%>'
                AddChild     ='첫번째 부품분류 복사<%--첫번째 하위 Task 추가--%>'
                AddChildEnd  ='마지막 부품분류 복사<%--마지막 하위 Task 추가--%>'
        />
        <!-- Alert Message -->
        <Alert DelRow="%d 분류체계를 삭제하시겠습니까?"/>
    </Lang>
    
   <Toolbar Visible="0" />
   <Solid>
   <Toolbar Cells="Save,Add,AddChild,Undo,Redo,ExpandAll,CollapseAll,moveUp,moveDown,Space1,Reload,Export,Print,Columns"
        AddCopyMenu="AddRow,AddRowBelow"
        AddChildCopyMenu="AddChild,AddChildEnd"
        moveUp="Img" moveUpIcon="/plm/portal/images/claz_up.gif" moveUpWidth="16" moveUpCanEdit="0" moveUpOnClick="javascript:moveRowUp();"
        moveDown="Img" moveDownIcon="/plm/portal/images/claz_down.gif" moveDownWidth="16" moveDownCanEdit="0" moveDownOnClick="javascript:moveRowDown();"
        Space1Type="Html" Space1Width="20"
        Styles="2" Space="0" Kind="Topbar" Calculated="1" Link="0"
   />
   </Solid>
   
    <!-- 
     -->
    <Header CanDelete='0' CanCopy="0" Align="Center" 
             id                 ='ID'   <%-- No.1, No.2... --%>
             clazOid            ='Oid'   <%-- ext.ket.part.entity.KETPartClassification:897873135  --%>
             parentOid          ='parentOid'   <%-- "" or ext.ket.part.entity.KETPartClassification:897873129--%>
             hasPart            ='<%=messageService.getString("e3ps.message.ket_message", "06129")%><%--연결부품--%>'   <%--0 or 1 --%>
             lev                ='Level'   <%--0 or 1 --%>
             deleteFlag         ='deleteFlag'   <%--0 or 1 --%>
             
             classKrName        ='<%=messageService.getString("e3ps.message.ket_message", "06130")%><%--분류명--%>'   <%-- --%>
             classCode          ='분류코드'   <%-- --%>
             classEnName        ='<%=messageService.getString("e3ps.message.ket_message", "06131")%><%--분류명(영문)--%>'   <%-- --%>
             classZhName        ='<%=messageService.getString("e3ps.message.ket_message", "06132")%><%--분류명(중문)--%>'   <%-- --%>
             classPartType      ='부품유형'   <%-- --%>
             numberingCode      ='<%=messageService.getString("e3ps.message.ket_message", "06133")%><%--채번코드--%>'   <%-- --%>
             numberingMinNo     ='<%=messageService.getString("e3ps.message.ket_message", "03462")%><%--채번최소문자--%>'   <%-- --%>
             numberingCharics   ='<%=messageService.getString("e3ps.message.ket_message", "03461")%><%--채번특성--%>'   <%-- --%>
             ecoCar             = '친환경여부'
             treeFullPath       ='<%=messageService.getString("e3ps.message.ket_message", "06134")%><%--경로--%>'   <%-- --%>
             sortNo             ='<%=messageService.getString("e3ps.message.ket_message", "06135")%><%--정렬순서--%>'   <%-- --%>
             useClaz            ='<%=messageService.getString("e3ps.message.ket_message", "06136")%><%--사용여부--%>'   <%-- --%>
             erpProdCode        ='ERP자재그룹'   <%-- --%>
             epmCode            ='<%=messageService.getString("e3ps.message.ket_message", "06139")%><%--도면 코드--%>'   <%-- --%>
             erpProdGroupCode   ='<%=messageService.getString("e3ps.message.ket_message", "06140")%><%--제품군--%>'  <%-- --%>
             erpClassNo         ='ERP Class No<%--제품군--%>'  <%-- --%>
             catalogueCode      ='<%=messageService.getString("e3ps.message.ket_message", "06141")%><%--카달로그 코드--%>'   <%-- --%>
             divisionFlag       ='<%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%>'   <%-- --%>
             partNamingOid      ='partNamingOid'   <%-- --%>
             partNamingName     ='<%=messageService.getString("e3ps.message.ket_message", "06142")%><%--품명코드--%>'   <%-- --%>
             partClassificType  ='<%=messageService.getString("e3ps.message.ket_message", "06143")%><%--부품구분--%>'   <%-- --%>
             partAttributeOid   ='partAttributeOid'   <%-- --%>
             partAttributeName  ='Attributes'   <%-- --%>
             
             createStamp        ='<%=messageService.getString("e3ps.message.ket_message", "01336")%><%--등록일자--%>'   <%-- --%>
             modifyStamp        ='<%=messageService.getString("e3ps.message.ket_message", "01951")%><%--수정일자--%>'   <%-- --%>
    />
    
    <!-- 
    -->
         
    <LeftCols>
           <!-- 
        <C Name="id"                Type="Text"    CanEdit="0" CanExport="0" CanPrint="0" CanHide="1" Visible='0' <%-- RelWidth='80' --%> />
        <C Name="clazOid"           Type="Text"    CanEdit="0" CanExport="0" CanPrint="0" CanHide="1" Visible='0' <%-- RelWidth='80' --%> />
        <C Name="parentOid"         Type="Text"    CanEdit="0" CanExport="0" CanPrint="0" CanHide="1" Visible='0' <%-- RelWidth='80' --%> />
        <C Name="hasPart"           Type="Text"    CanEdit="0" CanExport="0" CanPrint="0" CanHide="1" Visible='0' <%-- RelWidth='80' --%> />
        <C Name="lev"           Type="Text"    CanEdit="0" CanExport="0" CanPrint="0" CanHide="1" Visible='0' <%-- RelWidth='80' --%> />
            -->
        
        <C Name="id"                Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="clazOid"           Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="parentOid"         Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="hasPart"           Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="lev"               Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <C Name="deleteFlag"        Visible="0"  Type="Text"     CanExport="0"   CanPrint="0"    CanHide="0"/>
        <%-- Icon="Check"  --%>
        <C Name='classKrName'       Align='Left'   CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:left;vertical-align:middle'/>
    </LeftCols>

    <Cols>
        <C Name='classCode'         Align='Left'   CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='classEnName'       Align='Left'   CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        
        <C Name='classZhName'       Align='Left'   CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='classPartType'     Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=classPartTypeKeyVal[0]%>"Enum="<%=classPartTypeKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='numberingCode'     Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='numberingMinNo'    Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='numberingCharics'  Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=partNumCharicsKeyVal[0]%>" Enum="<%=partNumCharicsKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='ecoCar'           Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=partisEcoCarVal[0]%>" Enum="<%=partisEcoCarVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='treeFullPath'      Align='Left'   CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='sortNo'            Align='Center' CanSort='0' CanEdit='1' Type='Int' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        
        <C Name='useClaz'           Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=useKeyVal[0]%>" Enum="<%=useKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        
        <C Name='erpProdCode'       Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='epmCode'           Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='erpProdGroupCode'  Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='erpClassNo'        Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='catalogueCode'     Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='divisionFlag'      Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=divisionFlagKeyVal[0]%>" Enum="<%=divisionFlagKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='partNamingName'    Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=partNamingKeyVal[0]%>"Enum="<%=partNamingKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='partClassificType' Align='Center' CanSort='0' CanEdit='1' Type='Enum' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> EnumKeys="<%=partGubunKeyVal[0]%>" Enum="<%=partGubunKeyVal[1]%>" ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='partAttributeName' Align='Center' CanSort='0' CanEdit='0' Type='Text' MinWidth='<%=colMinWidth%>' Visible='1' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        
        <C Name='createStamp'       Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        <C Name='modifyStamp'       Align='Center' CanSort='0' CanEdit='1' Type='Text' MinWidth='<%=colMinWidth%>' <%-- RelWidth='80' --%> ExportStyle='text-align:center;vertical-align:middle'/>
        
    </Cols>

   <Solid>
   <Toolbar Cells="Save,Add,AddChild,Undo,Redo,ExpandAll,CollapseAll,moveUp,moveDown,Space1,Reload,Export,Print,Columns"
        AddCopyMenu="AddRow,AddRowBelow"
        AddChildCopyMenu="AddChild,AddChildEnd"
        moveUp="Img" moveUpIcon="/plm/portal/images/claz_up.gif" moveUpWidth="16" moveUpCanEdit="0" moveUpOnClick="javascript:moveRowUp();"
        moveDown="Img" moveDownIcon="/plm/portal/images/claz_down.gif" moveDownWidth="16" moveDownCanEdit="0" moveDownOnClick="javascript:moveRowDown();"
        Space1Type="Html" Space1Width="20"
        Styles="2" Space="4" Kind="Topbar" Calculated="1" Link="0"
   /> 
   </Solid>
   
</Grid>
