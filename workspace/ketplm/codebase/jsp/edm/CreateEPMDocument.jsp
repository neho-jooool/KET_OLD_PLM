<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.content.*,wt.fc.*,wt.util.*,wt.part.WTPart,wt.part.WTPartMaster,wt.epm.EPMDocument,wt.epm.EPMDocumentMaster,wt.folder.*,wt.clients.folder.*,wt.query.*"%>
<%@page import = "e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@page import = "e3ps.project.ProjectMaster,e3ps.project.E3PSProject,e3ps.project.beans.ProjectHelper"%>
<%@page import = "e3ps.project.E3PSTask,e3ps.project.ProjectOutput,e3ps.project.beans.ProjectOutputHelper"%>
<%@page import = "e3ps.common.code.*,e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.iba.IBAUtil"%>
<%@page import = "ext.ket.part.util.PartSpecGetter,ext.ket.part.util.PartSpecEnum,e3ps.common.util.StringUtil"%>
<%@page import = "e3ps.common.content.ContentUtil,e3ps.common.content.ContentInfo"%>
<%@page import = "e3ps.project.MoldProject, e3ps.project.ProductProject, e3ps.project.MoldItemInfo"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
 
    boolean isLibrary = false;

    // 2차 고도화 추가사항 : Project에서 도면 등록시
    // isCustomer=Y인 경우  도면유형이 
    // 도면구분 '개발검토단계' 선택시 > 개발검토도면(그대로유지)
    // 도면구분 '개발단계' 선택시 > 고객제출도면 만
    // 도면구분 '양산단계' 선택시 > 고객제출도면 만
    String isCustomer = request.getParameter("isCustomer");

    Map<String, Object> parameter = new HashMap<String, Object>();
    List<Map<String, Object>> numCode = null;

    boolean isAdmin = CommonUtil.isAdmin();
    EDMProperties edmProperties = EDMProperties.getInstance();
    //EDMAttributes epmAttributes = EDMAttributes.getInstance();

    //WTProperties wtproperties = WTProperties.getLocalProperties();
    //String webAppName = wtproperties.getProperty("wt.webapp.name");
    String webAppName = CommonUtil.getWebAppName();

    e3ps.common.content.fileuploader.FormUploader uploader = null;
    Hashtable param = null;

    String command = "";
    String oid = "";
    // Added by MJOH, 2011-03-12
    // 설계변경사유로 도면양산이관 선택된 경우 처리
    String mDrawing = "";
    // Added by KKH, 2011-03-30
    // 설계변경에서 도면변경팝업 오픈시 사용 - 개정 후 수정화면
    String isECM = "";

    String contentType = request.getContentType();
    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 )
    {
        uploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
        param = uploader.getFormParameters();
        command = (String)param.get("command");
        oid = (String)param.get("oid");
        // Added by MJOH, 2011-03-12
        // 설계변경사유로 도면양산이관 선택된 경우 처리
        mDrawing = (String)param.get("mDrawing");
        // Added by KKH, 2011-03-30
      // 설계변경에서 도면변경팝업 오픈시 사용
        isECM = (String)param.get("isECM");
    }
    else
    {
        command = request.getParameter("command");
        oid = request.getParameter("oid");
        // Added by MJOH, 2011-03-12
        // 설계변경사유로 도면양산이관 선택된 경우 처리
        mDrawing = request.getParameter("mDrawing");
        // Added by KKH, 2011-03-30
      // 설계변경에서 도면변경팝업 오픈시 사용
        isECM = request.getParameter("isECM");
    }

    if( (command == null) || (command.trim().length() == 0) ) { command = ""; }
    if( (oid == null) || (oid.trim().length() == 0) ) { oid = ""; }
    // Added by MJOH, 2011-03-12
    // 설계변경사유로 도면양산이관 선택된 경우 처리
    if( (mDrawing == null) || (mDrawing.trim().length() == 0) ) { mDrawing = ""; }

    /*2013.02.07 shkim 보안등급 추가*/
    String security = "";

    if("save".equals(command)) {

        String businessType = (param.get("businessType")==null)? "":(String)param.get("businessType");
        String manageType = (param.get("manageType")==null)? "":(String)param.get("manageType");
        String devStage = (param.get("devStage")==null)? "":(String)param.get("devStage");
        String category = (param.get("category")==null)? "":(String)param.get("category");
        String cadAppType = (param.get("cadAppType")==null)? "":(String)param.get("cadAppType");
        String project = (param.get("project")==null)? "":(String)param.get("project");
        String description = (param.get("description")==null)? "":(String)param.get("description");

        String isDummyFile = (param.get("isDummyFile")==null)? "":(String)param.get("isDummyFile");

        String number = (param.get("number")==null)? "":(String)param.get("number");
        String name = (param.get("name")==null)? "":(String)param.get("name");

        String repPoid = (param.get("repPoid")==null)? "":(String)param.get("repPoid");
        String repModelOid = (param.get("repModelOid")==null)? "":(String)param.get("repModelOid");

        String torelPoid[] = uploader.getFormParameters("torelPoid");
        String torelModelOid[] = uploader.getFormParameters("torelModelOid");

        String torelModelOidNonPart = (param.get("torelModelOidNonPart")==null)? "":(String)param.get("torelModelOidNonPart");

        String outputOid = (param.get("outputOid")==null)? "":(String)param.get("outputOid");

        /*2013.02.07 shkim 보안등급 추가*/
        security = (param.get("security")==null)?"":(String)param.get("security");

        // Added by MJOH, 2011-03-27
        // 프로젝트 산출물 등록 팝업에서 도면 등록시 Opener 화면 refresh를 위해서 task oid가 필요함
        String taskOid = "";

        if( outputOid != null && outputOid.trim().length() > 0 )
        {
            ProjectOutput pOutput = (ProjectOutput)CommonUtil.getObject( outputOid );
            E3PSTask task = (E3PSTask)pOutput.getTask();
            taskOid = CommonUtil.getOIDString( task );
        }

        /*
         * ECAD
         */
        //PCB
        String numberPCB = (param.get("number_pcb")==null)? "":(String)param.get("number_pcb");
        String namePCB = (param.get("name_pcb")==null)? "":(String)param.get("name_pcb");
        //Schematic
        String numberSCH = (param.get("number_sch")==null)? "":(String)param.get("number_sch");
        String nameSCH = (param.get("name_sch")==null)? "":(String)param.get("name_sch");
        //AutoCAD
        String numberDWG = (param.get("number_dwg")==null)? "":(String)param.get("number_dwg");
        String nameDWG = (param.get("name_dwg")==null)? "":(String)param.get("name_dwg");

        String oidPCB = (param.get("oid_pcb")==null)? "":(String)param.get("oid_pcb");
        String oidSCH = (param.get("oid_sch")==null)? "":(String)param.get("oid_sch");
        String oidDWG = (param.get("oid_dwg")==null)? "":(String)param.get("oid_dwg");

        String gerberDelFile = (param.get("gerberDelFile")==null)? "":(String)param.get("gerberDelFile");


        String pdmLinkProduct = (param.get("pdmLinkProduct")==null)? "":(String)param.get("pdmLinkProduct");
        String lifecycle = (param.get("lifecycle")==null)? "":(String)param.get("lifecycle");
        String location = (param.get("location")==null)? "":(String)param.get("location");

        String manufacturingVersion = (param.get("manufacturingVersion")==null)? "":(String)param.get("manufacturingVersion");

        if(isDummyFile.trim().length() == 0) {
            isDummyFile = EDMHelper.IBA_DUMMY_FILE_VALUE_NO;
        }

        ReferenceFactory rf = new ReferenceFactory();

        //폴더 처리시 : 사업부/제품*금형/2D/도면유형
        CADCategory cadCat = CADCategory.toCADCategory(category);

        String EPMApplicationType = edmProperties.getAppTypeByPLM();//"WINDCHILL";
        String EPMDocumentType = "";

        boolean isWGM = false;
        if(oid.length() > 0) {
            try {
                EPMDocument o = (EPMDocument)rf.getReference(oid).getObject();
                EPMDocumentType = o.getDocType().toString();
                if(!edmProperties.isAppTypeByPLM(o.getOwnerApplication().toString())) { //if(!"WINDCHILL".equals(o.getOwnerApplication().toString())) {
                    isWGM = true;
                    EPMApplicationType = o.getOwnerApplication().toString();
                    location = o.getLocation();
                }
            }
            catch(Exception e) {
            Kogger.error(e);
            }
        }


        NumberCode bizType = null;
        if(businessType.trim().length() > 0) {
            try {
                bizType = (NumberCode)rf.getReference(businessType).getObject();
            }catch(Exception e){ bizType = null; }
        }

        if(isWGM == false) {
            location = edmProperties.getFullPath((bizType==null)? "":bizType.getName(), manageType, cadCat.toString(), isWGM);
        }
        //location = edmProperties.getFullPath((bizType==null)? "":bizType.getName(), manageType, cadCat.getDisplay(Locale.KOREAN), isWGM);
        //location = edmProperties.getFullPath(bizType.getName(), manageType, cadCat.getDisplay(Locale.KOREAN), isWGM);


        String delFiles[] = uploader.getFormParameters("delFile");
        Vector files = uploader.getFiles();


        HashMap map = new HashMap();
        if(oid.length() > 0) { map.put("oid", oid); }
        if(businessType.length() > 0) { map.put("businessType", businessType); }
        if(manageType.length() > 0) { map.put("manageType", manageType); }
        if(devStage.length() > 0) { map.put("devStage", devStage); }
        if(category.length() > 0) { map.put("category", category); }
        if(cadAppType.length() > 0) { map.put("cadAppType", cadAppType); }
        if(project.length() > 0) { map.put("project", project); }
        if(description.length() > 0) { map.put("description", description); }
        if(number.length() > 0) { map.put("number", number); }
        if(name.length() > 0) { map.put("name", name); }

        if(outputOid.length() > 0) { map.put("outputOid", outputOid); }

        if(repPoid.length() > 0) { map.put("repPoid", repPoid); }
        if(repModelOid.length() > 0) { map.put("repModelOid", repModelOid); }

        if(torelPoid != null) {
            map.put("torelPoid", torelPoid);
            map.put("torelModelOid", torelModelOid);
        }

        if(torelModelOidNonPart.length() > 0) { map.put("torelModelOidNonPart", torelModelOidNonPart); }


        if(numberPCB.length() > 0) { map.put("number_pcb", numberPCB); }
        if(namePCB.length() > 0) { map.put("name_pcb", namePCB); }
        if(numberSCH.length() > 0) { map.put("number_sch", numberSCH); }
        if(nameSCH.length() > 0) { map.put("name_sch", nameSCH); }
        if(numberDWG.length() > 0) { map.put("number_dwg", numberDWG); }
        if(nameDWG.length() > 0) { map.put("name_dwg", nameDWG); }

        if(oidPCB.length() > 0) { map.put("oid_pcb", oidPCB); }
        if(oidSCH.length() > 0) { map.put("oid_sch", oidSCH); }
        if(oidDWG.length() > 0) { map.put("oid_dwg", oidDWG); }
        if(gerberDelFile.length() > 0) { map.put("gerberDelFile", gerberDelFile); }


        if(files != null) { map.put("files", files); }

        if(delFiles != null) { map.put("delFile", delFiles); }

        if(lifecycle.length() > 0) { map.put("lifecycle", lifecycle); }
        if(location.length() > 0) { map.put("location", location); }
        if(pdmLinkProduct.length() > 0) { map.put("PDMLinkProduct", pdmLinkProduct); }


        if(EPMApplicationType.length() > 0) { map.put("EPMApplicationType", EPMApplicationType); }
        if(EPMDocumentType.length() > 0) { map.put("EPMDocumentType", EPMDocumentType); }


        //IBA 값 처리
        if(manageType.length() > 0) {
            map.put(EDMHelper.IBA_CAD_MANAGE_TYPE, manageType);
        }
        if(devStage.length() > 0) {
            map.put(EDMHelper.IBA_DEV_STAGE, devStage);
        }
        if(category.length() > 0) {
            map.put(EDMHelper.IBA_CAD_CATEGORY, category);
        }
        if(cadAppType.length() > 0) {
            map.put(EDMHelper.IBA_CAD_APP_TYPE, cadAppType);
        }
        if(manufacturingVersion.length() > 0) {
            map.put(EDMHelper.IBA_MANUFACTURING_VERSION, manufacturingVersion);
        }
        /*2013.02.07 shkim 보안등급 추가*/
        if(security.length() > 0) {
            map.put(EDMHelper.IBA_SECURITY, security);
        }
        //if("true".equals(isDummyFile)) {
            map.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
        //}

        boolean isSuccess = true;

        EPMDocument epm = null;

        try {
            if(edmProperties.isCADCatsByEcad(category)) {
                epm = EDMServiceHelper.saveEPMDocumentByECAD(map);
            } else {
                epm = EDMServiceHelper.saveEPMDocument(map);
            }
            oid = epm.getPersistInfo().getObjectIdentifier().getStringValue();
        }
        catch(Exception e) {
            Kogger.error(e);
            isSuccess = false;
            String errorMessage = e.getLocalizedMessage();
            oid = "";
%>
        <form name="errorForm"><input type=hidden name='command' value='error'><input type=hidden name='errorMessage' value='<%=errorMessage%>'></form>
        <script language="javascript" type="text/javascript" >
            alert('<%=messageService.getString("e3ps.message.ket_message", "02455") %><%--저장 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
            document.errorForm.method = "post";
            document.errorForm.action = "/<%=webAppName%>/jsp/edm/CreateEPMDocument.jsp";
            document.errorForm.submit();
        </script>
<%
        }
%>
        <script language="javascript" type="text/javascript" >
            alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');

            if( window.dialogArguments != null) {
                window.returnValue="<%=isSuccess%>";
                window.close();
            }

            if(opener != null) {
                //opener.document.location.reload();
                //opener.document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=taskOid%>";
                // window.close();
                if(opener.loadEjsGrid){
                    try{
                        opener.loadEjsGrid();
                    }catch(e){}
                }
            } 
            
            document.location.href = "/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp?oid=<%=oid%>";
            
        </script>
<%
    }


    String errorMessage = "";

    //프로젝트 산출물 OID
    String outputOid = "";

    String taskOid = "";
    String pRank = "";

    /*
     * 금형도면/UG 인 경우 :
     * - WGM으로 등록 후 속성 정보 추가/수정.
     * - 제품도면과 같이 대표/관련 부품의 참조 모델은 없음. 화면 상 display 만 함.
     */
    boolean isMoldUgModel = false;

    EPMDocument epm = null;

    ReferenceFactory rf = new ReferenceFactory();

    errorMessage = request.getParameter("errorMessage");

/*shkim*/
    outputOid = request.getParameter("outputOid");
    if( outputOid != null && outputOid.trim().length() > 0 )
    {
        ProjectOutput pOutput = (ProjectOutput)CommonUtil.getObject( outputOid );
        E3PSTask task = (E3PSTask)pOutput.getTask();
        E3PSProject project2 = (E3PSProject)task.getProject();
        if(project2!=null && project2.getRank()!=null) pRank = project2.getRank().getName();
        taskOid = CommonUtil.getOIDString( task );
    }
    if( (errorMessage == null) || (errorMessage.trim().length() == 0) ) { errorMessage = ""; }
    if( (outputOid == null) || (outputOid.trim().length() == 0) ) { outputOid = ""; }

    if(oid.trim().length() > 0) {
        try {
            epm = (EPMDocument)rf.getReference(oid).getObject();
        }
        catch(Exception e) {
            Kogger.error(e);
        }
    }

    if(epm != null) {
        if(!VersionHelper.isLatestRevision(epm)) {
%>
            <script language="javascript">
            <!--
                alert("<%=messageService.getString("e3ps.message.ket_message", "01953") %><%--수정하려는 도면이 최신 버전/이터레이션이 아닙니다!!!--%>");
                if( window.dialogArguments != null) {
                    window.returnValue=false;
                    window.close();
                }

                if(opener != null) {
                    window.close();
                } else {
                    window.history.back();
                }
            // -->
            </script>
<%
        }
        
        String epmFolderPath = EDMFolderUtil.getFolderLocation(epm);
        
        if(StringUtils.contains(epmFolderPath, "KET_LIB")){
            isLibrary = true;
        }
    }

    /*
     * 도면 번호 변경이 가능한지 여부
     * 초기 버전이고 WGM으로 등록한 도면이 아닌 경우.
     */
    boolean isNumbering = true;
    if(epm != null) {
        isNumbering = VersionHelper.isFirstVersion(epm);
        if( (isNumbering) && !edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
            isNumbering = false;
        }
    }


    String manageType = "";
    String category = "";
    String devStage = "";
    String cadAppType = "";
    String manufacturingVersion = "";
    String isDummyFile = "";
    /*2013.02.07 shkim 보안등급 추가*/
    security = "";


    String repPoid = "";
    String repModelOid = "";

    manageType = request.getParameter("manageType");


    HashMap ibaValues = null;
    if( epm != null ) { ibaValues = EDMAttributes.getIBAValues(epm,Locale.KOREAN); }
    if( ibaValues != null ) {

        if( ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE) != null ) {
            manageType = EDMEnumeratedTypeUtil.getCADManageType((String)ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE),Locale.KOREAN);
        }
        if( ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null ) {
            category = EDMEnumeratedTypeUtil.getCADCategory((String)ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),Locale.KOREAN);
        }

        if( ibaValues.get(EDMHelper.IBA_DEV_STAGE) != null ) {
            devStage = EDMEnumeratedTypeUtil.getDevStage((String)ibaValues.get(EDMHelper.IBA_DEV_STAGE),Locale.KOREAN);
        }
        if( ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE) != null ) {
            cadAppType = EDMEnumeratedTypeUtil.getCADAppType((String)ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE),Locale.KOREAN);
        }
        if( ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION) != null ) {
            manufacturingVersion = (String)ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION);
        }
        if( ibaValues.get(EDMHelper.IBA_DUMMY_FILE) != null ) {
            isDummyFile = (String)ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
        }
        /*2013.02.07 shkim 보안등급 추가*/
        if(ibaValues.get(EDMHelper.IBA_SECURITY) != null ) {
            security = (String)ibaValues.get(EDMHelper.IBA_SECURITY);
        }
    }

    if( (manageType == null) || (manageType.trim().length() == 0) ) { manageType = "PRODUCT_DRAWING"; }
    if( (isDummyFile == null) || (isDummyFile.trim().length() == 0) ) { isDummyFile = "false"; }


    isMoldUgModel = "MOLD_DRAWING".equals(manageType);
    if(isMoldUgModel) { isMoldUgModel = "UG".equals(cadAppType); }

    //대표부품/3D 모델...
    WTPart part = null;
    EPMDocument model = null;

    if(epm != null) {
        ArrayList relateds = null;
        try {
            relateds = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceType(category),EDMHelper.REQUIRED_STANDARD);
            if( (relateds != null) && (relateds.size() > 0) ) {
                part = (WTPart)((Object[])relateds.get(0))[1];
            }
        }
        catch(Exception e) {
            Kogger.error(e);
        }
    }

    if(part != null) {
        repPoid = PersistenceHelper.getObjectIdentifier(part).getStringValue();
    }
    if( (part != null) && edmProperties.isRefModel(category)) {
        model = EDMHelper.getAssociatedModel(epm, part,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
    }
    if(model != null) {
        repModelOid = PersistenceHelper.getObjectIdentifier(model).getStringValue();
    }
    if((model == null) && isMoldUgModel ) {//
        model = epm;
    }



    boolean keyinfornumber = isNumbering;
    if(keyinfornumber) {
        keyinfornumber = edmProperties.isKeyInCatsToNrField(category);
    }

    boolean keyinforname = isNumbering;
    if(keyinforname) {
        keyinforname = edmProperties.isKeyInCatsToNmField(category);
    }


    /****************************************************************************************
     * ECAD 인 경우
     * PCB,Schematic,AutoCAD를 함께 등록/수정...
     ****************************************************************************************/
    EPMDocument ecadPCB = null;
    EPMDocument ecadSCH = null;
    EPMDocument ecadDWG = null;
    String nowEcad = null;
    String nowEcadState = "CREATE";
    
    if(edmProperties.isCADCatsByEcad(category)) {//if("ECAD_DRAWING".equals(category)) {
        if( (epm != null) && (part != null) ) {
            ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
            for(int i = 0; i < ecads.size(); i++) {
                EPMDocument ecad = (EPMDocument)ecads.get(i);
                if(edmProperties.getAuthoringAppTypeByEcadPcb().equals(ecad.getAuthoringApplication().toString())) {
                    ecadPCB = ecad;
                }
                else if(edmProperties.getAuthoringAppTypeByEcadSch().equals(ecad.getAuthoringApplication().toString())) {
                    ecadSCH = ecad;
                }
                else if(edmProperties.getAuthoringAppTypeByEcadAcad().equals(ecad.getAuthoringApplication().toString())) {
                    ecadDWG = ecad;
                }
            }
        }
        
        if(epm != null){
            if(edmProperties.getAuthoringAppTypeByEcadPcb().equals(epm.getAuthoringApplication().toString())) {
               nowEcad= "PCB";
            }
            else if(edmProperties.getAuthoringAppTypeByEcadSch().equals(epm.getAuthoringApplication().toString())) {
               nowEcad= "SCH";
            }
            else if(edmProperties.getAuthoringAppTypeByEcadAcad().equals(epm.getAuthoringApplication().toString())) {
               nowEcad= "ACAD";
            }
            
            if("0".equals(epm.getVersionIdentifier().getValue())){
               nowEcadState = "MODIFY";
            }else{
               nowEcadState = "REVISE";
            }
        }
        
    }

    String oid_pcb = "";
    String oid_sch = "";
    String oid_dwg = "";

    boolean isEcadEditable = true;

    if(ecadPCB != null) {
        oid_pcb = PersistenceHelper.getObjectIdentifier(ecadPCB).getStringValue();
    }
    if(ecadSCH != null) {
        oid_sch = PersistenceHelper.getObjectIdentifier(ecadSCH).getStringValue();
    }
    if(ecadDWG != null) {
        oid_dwg = PersistenceHelper.getObjectIdentifier(ecadDWG).getStringValue();
    }

    if( (ecadPCB != null) && !"INWORK".equals(ecadPCB.getState().getState().toString()) ) {
        isEcadEditable = false;
    }
    if( (isEcadEditable) && (ecadSCH != null) && !"INWORK".equals(ecadSCH.getState().getState().toString()) ) {
        isEcadEditable = false;
    }
    if( (isEcadEditable) && (ecadDWG != null) && !"INWORK".equals(ecadDWG.getState().getState().toString()) ) {
        isEcadEditable = false;
    }

    /****************************************************************************************
     * 사업부 정보
     ****************************************************************************************/
    NumberCode bizCode0 = null;
    if(epm != null) {
        bizCode0 = (NumberCode)EDMHelper.getBizType(epm);
    }

    /****************************************************************************************
     * 프로젝트 정보
     * 제품도면만 존재.
     ****************************************************************************************/
    E3PSProject project = null;
    String projectOid = "";
    String projectNumber = "";
    if(epm != null) {
        ProjectMaster pjtMst = (ProjectMaster)EDMHelper.getProject(epm);
        if(pjtMst != null) {
            project = ProjectHelper.getProject(pjtMst.getPjtNo());
            projectOid = PersistenceHelper.getObjectIdentifier(project).getStringValue();
            projectNumber = project.getPjtNo();
        }
    }


    if( (epm == null) && (outputOid.trim().length() > 0 ) ) {
        ProjectOutput po = (ProjectOutput)CommonUtil.getObject(outputOid);

        //프로젝트...
        project = ProjectOutputHelper.getProjectOutPutLinkInfo(po);
        Kogger.debug("CreateEPMDocument", null, null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ project === " + project);
        if( outputOid != null && outputOid.trim().length() > 0 )
        {
            ProjectOutput pOutput = (ProjectOutput)CommonUtil.getObject( outputOid );
            E3PSTask task = (E3PSTask)pOutput.getTask();
            Kogger.debug("CreateEPMDocument", null, null, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ task ===== "+ task.getTaskName());
            projectOid = PersistenceHelper.getObjectIdentifier(project).getStringValue();
            Kogger.debug("CreateEPMDocument", null, projectOid, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ projectOid ==== " + projectOid);
            if("제품도출도".equals(task.getTaskName())){
                Persistable per = (Persistable)CommonUtil.getObject(projectOid);
                if(per instanceof MoldProject) {
                    MoldProject moldProject = (MoldProject)CommonUtil.getObject(projectOid);
                    //Kogger.debug("#########"+ moldProject.getName());
                    MoldItemInfo moldInfo = moldProject.getMoldInfo();
                    //Kogger.debug("#########"+ moldInfo.getProject());
                    ProductProject productProject = null;
                    if(moldInfo != null){
                        productProject = moldInfo.getProject();
                        projectOid = PersistenceHelper.getObjectIdentifier(productProject).getStringValue();
                        projectNumber = project.getPjtNo();
                    }
                }
            }
        }
        //사업부 정보...
        NumberCode nccc = ProjectOutputHelper.getNumberCodeLinkProjectInfo(project);

        projectOid = PersistenceHelper.getObjectIdentifier(project).getStringValue();
        projectNumber = project.getPjtNo();

        if(bizCode0 == null) {
            bizCode0 = nccc;
        }

        int pjtType = project.getPjtType();
        if( (pjtType == 3) ) {//금형
            manageType = "MOLD_DRAWING";

        } else if( (pjtType == 0) || (pjtType == 1) ) {//개발검토
            manageType = "PRODUCT_DRAWING";
            devStage = "DEV_REVIEW_STAGE";
            category = "DEV_REVIEW_DRAWING";

        } else {
            //2: 자동차
            //4: 전자
            //제품
            manageType = "PRODUCT_DRAWING";
        }

        String pjtDwg = request.getParameter("pjtDwg");
        if( (pjtDwg != null) && (pjtDwg.trim().length() > 0) ) {
            if( "1".equals(pjtDwg) ) {
                manageType = "PRODUCT_DRAWING";
            } if( "2".equals(pjtDwg) ) {
                manageType = "MOLD_DRAWING";
                devStage = "";
                category = "";
            }
        }
    }
%>
<html>
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%>&nbsp;<%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></title>
<link href="/<%=webAppName%>/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
.fixedlayout { layout-grid:both fixed none none; }

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script language=JavaScript src="/<%=webAppName%>/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="js/edm.js"></SCRIPT>
<SCRIPT language=JavaScript src="js/create.js"></SCRIPT>
<%--
<script language=JavaScript src="/<%=webAppName%>/portal/js/common.js"></script>
<script language=JavaScript src="/<%=webAppName%>/portal/js/jquery-1.4.3.min.js"></script>
 --%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<!-- <SCRIPT language=JavaScript src="js/jquery-latest.min.js"></SCRIPT> -->
<!--
<SCRIPT language=JavaScript src="js/jquery-1.4.2.min.js"></SCRIPT>
 -->
<script type="text/Javascript">
<!--

<%--
var $jquery=jQuery.noConflict();
--%>

$(document).ready(function(){
    
    SuggestUtil.bind('PRODUCTPROJNO', 'projectNumber', 'project'); // , 'projectName'
    
    // ECAD 처리
    <% if(ecadPCB == null && ecadSCH == null && ecadDWG == null){ // 최초 등록시에 %>   
    <% }else{  // 수정, 개정 시점에
        
        if("PCB".equals(nowEcad)) {
    %>
        $("[name=number_pcb]").attr("readonly", false);
        $("[name=name_pcb]").attr("readonly", false);
        $("[name=file_pcb_primary]").attr("disabled", false);
        $("[name=file_gerber_primary]").attr("disabled", false);
        $("[id^=gerber_delete_]").attr("disabled", false);
        $("[id^=gerber_delete_btn_]").attr("disabled", false);
        $("[name=number_sch]").attr("readonly", true);
        $("[name=name_sch]").attr("readonly", true);
        $("[name=file_sch_primary]").attr("disabled", true);
        $("[name=name_dwg]").attr("readonly", true);
        $("[name=file_dwg_primary]").attr("disabled", true);
        
    <% }else if("SCH".equals(nowEcad)) { %>
       
        $("[name=number_pcb]").attr("readonly", true);
        $("[name=name_pcb]").attr("readonly", true);
        $("[name=file_pcb_primary]").attr("disabled", true);
        $("[name=file_gerber_primary]").attr("disabled", true);
        $("[id^=gerber_delete_]").attr("disabled", true);
        $("[id^=gerber_delete_btn_]").attr("disabled", true);
        $("[name=number_sch]").attr("readonly", false);
        $("[name=name_sch]").attr("readonly", false);
        $("[name=file_sch_primary]").attr("disabled", false);
        $("[name=name_dwg]").attr("readonly", true);
        $("[name=file_dwg_primary]").attr("disabled", true);
    
    <% }else if("ACAD".equals(nowEcad)) { %>
    
        $("[name=number_pcb]").attr("readonly", true);
        $("[name=name_pcb]").attr("readonly", true);
        $("[name=file_pcb_primary]").attr("disabled", true);
        $("[name=file_gerber_primary]").attr("disabled", true);
        $("[id^=gerber_delete_]").attr("disabled", true);
        $("[id^=gerber_delete_btn_]").attr("disabled", true);
        $("[name=number_sch]").attr("readonly", true);
        $("[name=name_sch]").attr("readonly", true);
        $("[name=file_sch_primary]").attr("disabled", true);
        $("[name=name_dwg]").attr("readonly", false);
        $("[name=file_dwg_primary]").attr("disabled", false);
    
    <% } %>
    <% } %>
});

var maxLength_number;
var maxLength_name;
var maxLength_description;


maxLength_number=new Number(<%=edmProperties.getMaxLenForNumber()%>);
maxLength_name=new Number(<%=edmProperties.getMaxLenForName()%>);
maxLength_description=new Number(<%=edmProperties.getMaxLenForDescription()%>);


/*****************************************************************************************
    도면  시작 ...
 *****************************************************************************************/
<%  CADCategory catarr[] = CADCategory.getCADCategorySet(); %>
// Category Array
var defedCatValueArr = new Array();
<%  if(catarr != null) { for(int i = 0; i < catarr.length; i++) { %>defedCatValueArr[defedCatValueArr.length] = "<%=catarr[i].toString()%>"; <% } } %>

//품번이 없는 도면 여부...
var noRelatedPartArr = new Array();
<%
    CADCategory nonPartCats[] = edmProperties.getCADCatsByNonPart();
    if(nonPartCats != null) {
        for(int i = 0; i < nonPartCats.length; i++) {
%>
            noRelatedPartArr[noRelatedPartArr.length] = "<%=nonPartCats[i].toString()%>";
<%
        }
    }
%>

//essentialElementMarkByPart
//품번이 필수인 도면 유형's(대표 부품).
var essPartCats = new Array();
<%
    CADCategory essPartCats[] = edmProperties.getEssentialPartCats();
    if(nonPartCats != null) {
        for(int i = 0; i < essPartCats.length; i++) {
%>
            essPartCats[essPartCats.length] = "<%=essPartCats[i].toString()%>";
<%
        }
    }
%>

//다품일도 여부 ...
var typeOnPartRef = new Array();
<%
    if(catarr != null) {
        for(int i = 0; i < catarr.length; i++) {
            if(!edmProperties.isOnlyRefesCat(catarr[i].toString())) {//if(edmProperties.isVariousPartRefs(catarr[i].toString())) {
%>
                typeOnPartRef[typeOnPartRef.length] = "<%=catarr[i].toString()%>";
<%
            }
        }
    }
%>

//대표/관련 부품의 참조 모델 추가 여부...
var typeOnModelRef = new Array();
<%  CADCategory refModelCats[] = edmProperties.getCategoryRefModel();
    if(refModelCats != null) {
        for(int i = 0; i < refModelCats.length; i++) {
%>
            typeOnModelRef[typeOnModelRef.length] = "<%=refModelCats[i].toString()%>";
<%    }
    }
%>


//부품채번관리에서 도면번호를 관리하는 Category.
//개발검토도면
var catByNumberingSystem = new Array();
<%  CADCategory catByNumberingSystem[] = edmProperties.getCatByNumberingSystem();
    if(catByNumberingSystem != null) {
        for(int i = 0; i < catByNumberingSystem.length; i++) {
%>
            catByNumberingSystem[catByNumberingSystem.length] = "<%=catByNumberingSystem[i].toString()%>";
<%    }
    }
%>


//도면번호 사용자 입력(keyin) 여부...
var typeOnKeyInNums = new Array();
<%  CADCategory keyinNrCats[] = edmProperties.getKeyInCatsToNrField();
    if(keyinNrCats != null) {
        for(int i = 0; i < keyinNrCats.length; i++) {
%>
            typeOnKeyInNums[typeOnKeyInNums.length] = "<%=keyinNrCats[i].toString()%>";
<%    }
    }
%>


//도면번호 사용자 입력(keyin) 여부...
var typeOnKeyInNames = new Array();
<%  CADCategory keyinNmCats[] = edmProperties.getKeyInCatsToNmField();
    if(keyinNmCats != null) {
        for(int i = 0; i < keyinNmCats.length; i++) {
%>
            typeOnKeyInNames[typeOnKeyInNames.length] = "<%=keyinNmCats[i].toString()%>";
<%    }
    }
%>

//dummy file 가능 여부.
var isDummyFileCats = new Array();
<%  CADCategory dummyFileCats[] = edmProperties.getCADCatsByDummyFile();
    if(dummyFileCats != null) {
        for(int i = 0; i < dummyFileCats.length; i++) {
%>
            isDummyFileCats[isDummyFileCats.length] = "<%=dummyFileCats[i].toString()%>";
<%    }
    }
%>

var ecadCats = new Array();
<%
    CADCategory ecadCats[] = edmProperties.getCADCatsByEcad();
    if(ecadCats != null) {
        for(int i = 0; i < ecadCats.length; i++) {
%>
            ecadCats[ecadCats.length] = "<%=ecadCats[i].toString()%>";
<%
        }
    }
%>

//도면유형의 End Leaf 폴더 명.
var endLeafFolderNames = new Array();
var endLeafs;
<%  for(int i = 0; i < catarr.length; i++ ) { %>
        endLeafs = new Array();
        endLeafs[0] = "<%=catarr[i].toString()%>";
        endLeafs[1] = "<%=edmProperties.getEndLeafFolderName(catarr[i].toString(), true)%>";
        endLeafFolderNames[endLeafFolderNames.length] = endLeafs;
<%  } %>

function checkNonPart(_k) {
    for(var i = 0; i < noRelatedPartArr.length; i++) {
        if(_k == noRelatedPartArr[i]) {
            return true;
        }
    }
    return false;
}

function checkEssPartCat(_k) {
    for(var i = 0; i < essPartCats.length; i++) {
        if(_k == essPartCats[i]) {
            return true;
        }
    }
    return false;
}

function isOnlyRefesCat(_k) {
    for(var i = 0; i < typeOnPartRef.length; i++) {
        if(_k == typeOnPartRef[i]) {
            return false;
        }
    }
    return true;
}

function checkTypeOnModelRef(_k) {
    for(var i = 0; i < typeOnModelRef.length; i++) {
        if(_k == typeOnModelRef[i]) {
            return true;
        }
    }
    return false;
}


function checkByNumberingSystem(_k) {
    for(var i = 0; i < catByNumberingSystem.length; i++) {
        if(_k == catByNumberingSystem[i]) {
            return true;
        }
    }
    return false;
}

function checkTypeOnKeyInNums(_k) {
    for(var i = 0; i < typeOnKeyInNums.length; i++) {
        if(_k == typeOnKeyInNums[i]) {
            return true;
        }
    }
    return false;
}

function checkTypeOnKeyInNames(_k) {
    for(var i = 0; i < typeOnKeyInNames.length; i++) {
        if(_k == typeOnKeyInNames[i]) {
            return true;
        }
    }
    return false;
}

function checkDummyFileCats(_k) {
    for(var i = 0; i < isDummyFileCats.length; i++) {
        if(_k == isDummyFileCats[i]) {
            return true;
        }
    }
    return false;
}

function checkEcadCats(_k) {
    for(var i = 0; i < ecadCats.length; i++) {
        if(_k == ecadCats[i]) {
            return true;
        }
    }
    return false;
}

function getEndLeafFolderName(_k) {
    for(var i = 0; i < endLeafFolderNames.length; i++) {
        var efn = endLeafFolderNames[i];
        if(_k == efn[0]) {
            return efn[1];
        }
    }
    return _k;
}


/*****************************************************************************************
 * 맵핑 조건 처리 ...
 *****************************************************************************************/

function doChangeDevStage(iObj) {
    doDevStageFilter(iObj.value, document.forms[0].manageType.value);
}

function doDevStageFilter(_ds,_mt) {
    var param = "";
    param += "command=searchCategory";
    param += "&devStage=" + _ds;
    param += "&manageType=" + _mt;

    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" error (function : doDevStageFilter)"); changingPageContents();  },
        success: function(xml) {
            rtnDevStageFilter(xml);
        }
    });
}

function getXmlText(xmlDoc,key){
	return xmlDoc.getElementsByTagName(key)[0].text || xmlDoc.getElementsByTagName(key)[0].textContent; 
}

function rtnDevStageFilter(xmlDoc) {

    var msg = getXmlText(xmlDoc, "l_message");
    
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        changingPageContents();
        return;
    }

    
    var l_display = xmlDoc.getElementsByTagName("l_display");
    var l_value = xmlDoc.getElementsByTagName("l_value");

    var lcatObj = document.getElementById("category");

    removeOptions(lcatObj);

    if((l_value != null) && (l_value.length > 0)) {
        if(l_value.length == 1)  {
            // isCustomer Y CUSTOMER_DRAWING
            l_display = getXmlText(xmlDoc,'l_display');
            l_value = getXmlText(xmlDoc,'l_value')
            
            <% if("Y".equals(isCustomer)){ %>
               if( unescape(l_value) != 'CUSTOMER_DRAWING' && unescape(l_value) != 'DEV_REVIEW_DRAWING' && unescape(l_value) != 'ECAD_DRAWING' ){
                   return;
               }
            <% }%>
            //alert("text:" + unescape(l_display[0].text) + " value:" + unescape(l_value[0].text) );
            addOptions(lcatObj,unescape(l_display),unescape(l_value),true);
        } else {
            addOptions(lcatObj,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
            for(var i = 0; i < l_value.length; i++) {
                
                <% if("Y".equals(isCustomer)){ %>
                if( unescape(l_value[i].text) != 'CUSTOMER_DRAWING' && unescape(l_value[i].text) != 'DEV_REVIEW_DRAWING' && unescape(l_value[i].text) != 'ECAD_DRAWING' ){
                    continue;
                }
               <% }%>
               
               //alert("text1:" + unescape(l_display[i].text) + " value1:" + unescape(l_value[i].text) );
                addOptions(lcatObj,unescape(l_display[i].text || l_display[i].textContent),unescape(l_value[i].text || l_value[i].textContent),false);
            }
        }
    } else {
        addOptions(lcatObj,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
    }

    doCategoryFilter(lcatObj);
}

function doCategoryFilter(iObj) {
    var param = "";
    param += "command=searchCadAppType";
    param += "&category=" + iObj.value;

    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" error (function : doCategoryFilter)"); changingPageContents(); },
        success: function(xml) {
            rtnCategoryFilter(xml);
        }
    });
}

function rtnCategoryFilter(xmlDoc) {
    
    var msg = getXmlText(xmlDoc, "l_message");
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        changingPageContents();
        return;
    }
    
    var l_display = xmlDoc.getElementsByTagName("l_display");
    var l_value = xmlDoc.getElementsByTagName("l_value");

    var lCadAppTypeObj = document.getElementById("cadAppType");

    removeOptions(lCadAppTypeObj);

    if(l_value != null && l_value.length > 0) {
        if(l_value.length == 1)  {
            addOptions(lCadAppTypeObj,unescape(l_display[0].text || l_display[0].textContent),unescape(l_value[0].text || l_value[0].textContent),true);
        } else {
            addOptions(lCadAppTypeObj,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
            for(var i = 0; i < l_value.length; i++) {
                addOptions(lCadAppTypeObj,unescape(l_display[i].text || l_display[i].textContent),unescape(l_value[i].text || l_value[i].textContent),false);
            }
        }
    } else {
        addOptions(lCadAppTypeObj,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
    }

    doCadAppTypeFilter(lCadAppTypeObj);
}

function doCadAppTypeFilter(_obj) {

    doChangeAppType(_obj);

    changingPageContents();

    disCallNumber();

    document.forms[0].ecadConvPrefixNumber.value = "";
    document.forms[0].ecadConvPrefixName.value = "";
}

function doChangeAppType(_obj) {
    //primary_file_attach_div
    //ug_model_search_btn
    <%if("MOLD_DRAWING".equals(manageType)) {%>
        if(_obj.value == 'UG') {
            document.getElementById('primary_file_attach_div').style.display='none';
            //document.getElementById('table_DummyFile').style.display='none';
            document.getElementById('ug_model_search_btn').style.display='';
        } else {
            document.getElementById('primary_file_attach_div').style.display='';
            //document.getElementById('table_DummyFile').style.display='none';
            document.getElementById('ug_model_search_btn').style.display='none';
        }

        <%if(epm == null){%>
            document.forms[0].oid.value = '';
            document.forms[0].repPoid.value = '';
        <%}%>
        document.forms[0].number.value='';
        document.forms[0].name.value='';

        removeSeledItem('part','torep');
        deleteRowByReledPart(true,'');

    <%}%>
}

function disCallNumber() {
    //callNumberBtn
    //numberTxt
    if( (document.forms[0].oid.value == '') && checkByNumberingSystem(document.forms[0].category.value)) {
        document.getElementById('number').style.width=240;
        document.getElementById('callNumberBtn').style.display='';
    } else {
        document.getElementById('number').style.width=290;
        document.getElementById('callNumberBtn').style.display='none';
    }
}

function changingPageContents() {
    var _isInitContent = false;
    if( (document.forms[0].devStage.value == '') || (document.forms[0].category.value == '') || (document.forms[0].cadAppType.value == '') ) {
        _isInitContent = true;
    }
    initPage(_isInitContent);
}


/*****************************************************************************************
    도면 저장 시작 ...
 *****************************************************************************************/
function doCancel() {
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02439") %><%--작업을 취소하시겠습니까?--%>')) {
        return;
    }

    if( window.dialogArguments != null) {
        window.returnValue=false;
        window.close();
    }

    if(document.forms[0].oid.value != '') {
        document.location.href="/<%=webAppName%>/jsp/edm/ViewEPMDocument.jsp?oid="+document.forms[0].oid.value;
    } else {
        if(window.opener) {
            self.close();
        } else {
            document.location.href="/<%=webAppName%>/jsp/edm/SearchEPMDocument.jsp";
        }
    }
}

function doSave(){
    document.getElementById("security").disabled = false;
    if(!checkInData()){
        return false;
    }

    try{
        showProcessing();
        validateData();
        hideProcessing();
    }catch(e){
        hideProcessing();
    }
}

function doSaveAction() {

    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>')) {
        return;
    }

    form = document.forms[0];
    form.command.value = "save";

    //form.target = "_self";
    form.method = "post";
    form.encoding = 'multipart/form-data';
    form.action = "/<%=webAppName%>/jsp/edm/CreateEPMDocument.jsp";
    try{
        showProcessing();
        form.submit();
        hideProcessing();
    }catch(e){
        hideProcessing();
    }
}

function checkInData(){
    var form = document.forms[0];

    if(form.businessType.value=='') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01666") %><%--사업부를 선택하세요--%>");
        form.businessType.focus();
        return false;
    }

    if(form.devStage.value=='') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01265") %><%--도면구분을 선택하시기 바랍니다--%>');
        form.devStage.focus();
        return false;
    }

    if(form.category.value=='') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01290") %><%--도면유형을 선택하시기 바랍니다--%>');
        form.category.focus();
        return false;
    }

    if(form.cadAppType.value=='') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00101") %><%--CAD 종류를 선택하시기 바랍니다--%>");
        form.cadAppType.focus();
        return false;
    }

    if(form.description.value=='') {
        //alert("도면설명을 입력하시기 바랍니다.");
        //form.description.focus();
        //return false;
    } else if( (maxLength_description > 0) && lwGetByte(document.forms[0].description.value) > maxLength_description) {
//        alert("도면설명을 "+maxLength_description+" bytes 이하로 입력하시기 바랍니다.");
        alert('<%=messageService.getString("e3ps.message.ket_message", "01284") %><%--도면설명을 {0} bytes 이하로 입력하시기 바랍니다--%>');
        //
        form.description.focus();
        return false;
    }

    if(checkEssPartCat(form.category.value)) {//if((form.oid.value != '') && !checkNonPart(form.category.value)) {
        if(form.repPoid.value=='' && form.isLibrary.value=='false') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01246") %><%--대표부품을 입력하시기 바랍니다.--%>');
            return false;
        }
    }


    if(form.category.value == 'DEV_REVIEW_DRAWING') {
        if(form.isDummyFile != null && form.isDummyFile != 'undefined') {
            if(getValueRadio('isDummyFile').length==0) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00175") %><%--Dummy 파일 여부를 선택하시기 바랍니다--%>");
                return;
            }
        }
    } else {
        if(form.isDummyFile != null && form.isDummyFile != 'undefined') {
            form.isDummyFile.value = "<%=EDMHelper.IBA_DUMMY_FILE_VALUE_NO%>";
        }
    }

    if(!checkEcadCats(form.category.value)) {//if(form.category.value != 'ECAD_DRAWING') {
        if(form.number.value=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01279") %><%--도면번호를 입력하시기 바랍니다.--%>');
            return false;
        }

        if(form.name.value=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01271") %><%--도면명을 입력하시기 바랍니다.--%>');
            return false;
        }

        if(form.oid.value == '') {//등록인 경우.
            //첨부파일
            if(form.primary.value=='') {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02668") %><%--주 첨부파일을 입력하시기 바랍니다.--%>');
                form.primary.focus();
                return false;
            }
        }

    } else {
        
        <% if(ecadPCB == null && ecadSCH == null && ecadDWG == null){ // 최초 등록시에 3개중에 하나만 등록하면 됨 %>   

        if(form.number_pcb.value=='' && form.number_sch.value =='' && form.number_dwg.value == '') {
            alert("PCB, Schematic, AutoCAD 셋 중에 하나는 반드시 입력하셔야 합니다.");
            return false;
        }

        if(form.number_pcb.value !='') {
            
            if(form.name_pcb.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00355") %><%--PCB 도면명을 입력하시기 바랍니다--%>");
                return false;
            }
            
            //첨부파일
            if(form.file_pcb_primary.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00358") %><%--PCB 도면첨부 파일을 입력하시기 바랍니다--%>");
                form.file_pcb_primary.focus();
                return false;
            }
        }

        if(form.number_sch.value !='') {

            if(form.name_sch.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00460") %><%--Schematic 도면명을 입력하시기 바랍니다--%>");
                return false;
            }
    
            //첨부파일
            if(form.file_sch_primary.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00462") %><%--Schematic 도면첨부 파일을 입력하시기 바랍니다--%>");
                form.file_sch_primary.focus();
                return false;
            }
        }

        //AutoCAD 첨부파일이 있는 경우
        if(form.number_dwg.value != '') {
            if(form.file_dwg_primary.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00074") %><%--AutoCAD 도면첨부를 입력하시기 바랍니다--%>");
                return false;
            }

            if(form.name_dwg.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00072") %><%--AutoCAD 도면명을 입력하시기 바랍니다--%>");
                return false;
            }
        }
        
        <% }else{  // 수정, 개정 시점에
            
            if("PCB".equals(nowEcad)) {
        %>
                if(form.number_pcb.value=='') {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00356") %><%--PCB 도면번호를 입력하시기 바랍니다--%>");
                    return false;
                }
    
                if(form.name_pcb.value=='') {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00355") %><%--PCB 도면명을 입력하시기 바랍니다--%>");
                    return false;
                }
            
            <% }else if("SCH".equals(nowEcad)) { %>
            
                if(form.number_sch.value=='') {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00461") %><%--Schematic 도면번호를 입력하시기 바랍니다--%>");
                    return false;
                }
    
                if(form.name_sch.value=='') {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00460") %><%--Schematic 도면명을 입력하시기 바랍니다--%>");
                    return false;
                }
            
            <% }else if("ACAD".equals(nowEcad)) { %>
            
                if(form.name_dwg.value=='') {
                    alert("<%=messageService.getString("e3ps.message.ket_message", "00072") %><%--AutoCAD 도면명을 입력하시기 바랍니다--%>");
                    return false;
                }
            
            <% } %>
        <% } %>
    }

    return true;
}

function validateData() {
    form = document.forms[0];
    var params = { command:'validateSave',
        oid:encodeURIComponent(document.forms[0].oid.value),
        number:encodeURIComponent(form.number.value),
        name:encodeURIComponent(form.name.value),
        manageType:encodeURIComponent(form.manageType.value),
        category:encodeURIComponent(form.category.value),
        cadAppType:encodeURIComponent(form.cadAppType.value),
        primary:encodeURIComponent(form.primary.value),
        file_pcb_primary:encodeURIComponent(form.file_pcb_primary.value),
        file_gerber_primary:encodeURIComponent(form.file_gerber_primary.value),
        file_sch_primary:encodeURIComponent(form.file_sch_primary.value),
        file_dwg_primary:encodeURIComponent(form.file_dwg_primary.value),
        number_pcb:encodeURIComponent(form.number_pcb.value),
        number_sch:encodeURIComponent(form.number_sch.value),
        number_dwg:encodeURIComponent(form.number_dwg.value),
        oid_pcb:encodeURIComponent(form.oid_pcb.value),
        oid_sch:encodeURIComponent(form.oid_sch.value),
        oid_dwg:encodeURIComponent(form.oid_dwg.value)
    };
    var _str0 = $.param(params);

    showProcessing();
    
    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: _str0,
        async: true,
        error: function() {  
            alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "validateData") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>"); 
            hideProcessing();
        },
        success: function(xml) {
            try{
                showProcessing();
                recValidateData(xml);
                hideProcessing();
            }catch(e){
                hideProcessing();
            }
        }
    });
}

function recValidateData(xmlDoc) {
	
    var msg = getXmlText(xmlDoc,"l_message");
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }
    
    var lcheckmsg = unescape(getXmlText(xmlDoc,"l_checkmsg"));
    var lvalid = unescape(getXmlText(xmlDoc,"l_valid"));
    var lNumber = unescape(getXmlText(xmlDoc,"l_Number"));
    var lFileExt = unescape(getXmlText(xmlDoc,"l_FileExt"));
    var lFileName = unescape(getXmlText(xmlDoc,"l_FileName"));
    var lNamingRule = unescape(getXmlText(xmlDoc,"l_NamingRule"));

    
    var vmsg = '';
    if(lNamingRule == 'false') {
        vmsg += '<%=messageService.getString("e3ps.message.ket_message", "01278") %><%--도면번호를 규칙에 맞는 번호를 입력하시기 바랍니다--%>';
    }

    if(lNumber == 'false') {
        if(vmsg.length>0) { vmsg += "\n"; }
        vmsg += '<%=messageService.getString("e3ps.message.ket_message", "01277") %><%--도면번호가 이미 등록된 번호입니다--%>';
    }

    if(lFileExt == 'false') {
        if(vmsg.length>0) { vmsg += "\n"; }
        vmsg += '<%=messageService.getString("e3ps.message.ket_message", "02685") %><%--주첨부파일을 첨부하여 주십시오.--%>';
    }

    if(lFileName == 'false') {
        if(vmsg.length>0) { vmsg += "\n"; }
        vmsg += '<%=messageService.getString("e3ps.message.ket_message", "02683") %><%--주첨부 파일을 파일명 규칙에 맞는 파일을 입력하시기 바랍니다.--%>';
    }

    if(lvalid == 'false') {
        alert(vmsg);
        return;
    }else{
        doSaveAction();
    }
}


/*****************************************************************************************
    부품 검색 시작 ...
 *****************************************************************************************/
var subfix_item;
var epm_subfix_item;
var subfix_rep_flag;

var newWinSPart;
function doSeledPart(_subfix){
    
    subfix_item = _subfix;
    var param = "cmd=openSearchPopup&";
    
    if(_subfix == 'torep') {
        subfix_rep_flag = 1;
    }else{
        subfix_rep_flag = 0;
    }
    
    if(_subfix == 'torel') {
        var url="/plm/ext/part/base/listPartPopup.do?mode=s&modal=N&modal=N&fncall=" + "getSeledParts" + "&" + param;
        var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        newWinSPart = getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    }else{
        var url="/plm/ext/part/base/listPartPopup.do?mode=m&modal=N&modal=N&fncall=" + "getSeledParts" + "&" + param;
        var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        newWinSPart = getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    }
    
    <%--
    subfix_item = _subfix;

    if(_subfix == 'torel') {
        param += "&mode=multi";
        //param += "&mode=multi";
    }else{
        param += "&mode=one";
    }

    if(_subfix == 'torep') {
        subfix_rep_flag = 1;
    }else{
        subfix_rep_flag = 0;
    }

    param += "&modal=&invokeMethod=getSeledParts";

    url = "/<%=webAppName%>/servlet/e3ps/PartServlet?"+param;

    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=0,top=100,left=100,width=725px,height=515px');
    newWinSPart.focus();
    newWinSPart.location.href = url;
    --%>
}

/*
 * BOM 모듈 부품 검색 기능 사용 시.
 */
function selectedPart(_arr) {
    //alert("LLLL : " + _arr.length);
    /*
        var seledPartArr = new Array();
        for( var i = 0; i < _arr.length ; i++ ){
            subSeledPartArr = new Array();
            subSeledPartArr[0] = "wt.part.WTPart:" + _arr[i][0];//oid- ida2a2
            subSeledPartArr[1] = _arr[i][1];//number
            subSeledPartArr[2] = _arr[i][2];//name
            subSeledPartArr[3] = _arr[i][3];//version

            seledPartArr[seledPartArr.length] = subSeledPartArr;

        }
        getSeledParts(seledPartArr);
    */
    getSeledParts(_arr);
}

function getSeledParts(_arr) {
    for(var i=0; i < _arr.length; i++){
        //번호 체크,
        //부품추가 가능 체크
        //모델 체크
        goPartFilter(_arr[i]);
    }
}


/*
 * 도면의 부품 추가 시 체크.(이미 도면 연관된 정보가 있는지 여부, 승인도/고객제출도/제춤참고도
 */
function goPartFilter(_arr) {
    if( (_arr[1] == "") ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01597") %><%--부품정보가 없습니다--%>');
        return false;
    }

    if( checkDuplicate("pnumber", _arr[1]) ) {
        if( (subfix_item == 'torep')) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02284") %><%--이미 대표 부품이나 관련 부품에 추가된 부품입니다--%>');
            return false;
        } else {
            return false;
        }
    }

    _checkNumberParseRule='false';
    if(subfix_item=='torep') {
        _checkNumberParseRule='true';
    }

    var params = { command:'CheckPartReference',
        oid:document.forms[0].oid.value,
        poid:_arr[0],
        category:document.forms[0].category.value,
        cadAppType:document.forms[0].cadAppType.value,
        required:subfix_rep_flag,
        checkNumberParseRule:_checkNumberParseRule
    };

    var _str0 = $.param(params);


    var url = "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp";

    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: _str0,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "goPartFilter") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            rtnPartFilter(xml);
        }
    });
}

function rtnPartFilter(xmlDoc) {

    var msg = getXmlText(xmlDoc,"l_message");
    
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }
    
    var l_valid = getXmlText(xmlDoc,"l_valid");
    var l_validParseRule = getXmlText(xmlDoc,"l_validParseRule");

    var lvalid;
    
    if((l_valid != null) && (l_valid.length > 0)) {
        lvalid = unescape(l_valid);
    }
    
    var lvalidParseRule;
    if((l_validParseRule != null) && (l_validParseRule.length > 0)) {
        lvalidParseRule = unescape(l_validParseRule);
    }

    if(lvalidParseRule == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03182") %><%--해당 부품에 대한 도면번호 규칙이 없습니다--%>');
        return;
    }

    if(lvalid == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03185") %><%--해당 부품은 이미 동일한 도면유형의 도면과 연관을 가지고 있습니다.--%>');
        return;
    }

    var loid = getXmlText(xmlDoc,"l_oid");
    var lnumber = getXmlText(xmlDoc,"l_number");
    var lname = getXmlText(xmlDoc,"l_name");
    var lversion = getXmlText(xmlDoc,"l_version");


    var lDataArr = new Array();
    lDataArr[0] = unescape(loid);//oid
    lDataArr[1] = unescape(lnumber);//number
    lDataArr[2] = unescape(lname);//name
    lDataArr[3] = unescape(lversion);//version

    insertPartInfo(lDataArr);
}



function insertPartInfo(_arr) {
/*
    if( (_arr[1] == "") ) {
        alert("부품정보가 없습니다.");
        return false;
    }

    if( checkDuplicate("pnumber", _arr[1]) ) {
        if( (subfix_item == 'torep')) {
            alert('이미 대표 부품이나 관련 부품에 추가된 부품입니다.');
            return false;
        } else {
            return false;
        }
    }
*/
    var pnr;
    var pnm;
    var pnv;

    if(subfix_item == 'torep') { // 대표부품 추가
        pnr = document.getElementById("part_number_"+subfix_item);
        pnm = document.getElementById("part_name_"+subfix_item);
        pnv = document.getElementById("part_version_"+subfix_item);

        var repnums = document.getElementsByName("pnumber");
        for(var i=0; i<repnums.length; i++) {
            if(repnums[i].datatype == 'torep') {
                repnums[i].value = _arr[1];
                break;
            }
        }

        pnr.innerText=_arr[1];
        pnm.innerText=_arr[2];
        pnv.innerText=_arr[3];

        document.forms[0].repPoid.value=_arr[0];
        //document.forms[0].number.value=_arr[1];
        //document.forms[0].name.value=_arr[2];

        if(checkEssPartCat(document.forms[0].category.value)) {
            setConvertNrNm(_arr[1],_arr[2]);
        }

        if(document.forms[0].cadAppType.value != 'UG') {
            doSearchRelatedModel(_arr[0],subfix_item);
        }

    }else if(subfix_item == 'torel') { //관련 부품 일괄 추가
        var lDataArr = new Array();
        lDataArr[0] = _arr[0];//oid
        lDataArr[1] = _arr[1];//number
        lDataArr[2] = _arr[2];//name
        lDataArr[3] = _arr[3];//version
        insertRowToPart(document.getElementById('relatedPartsTbody'), lDataArr, false);

    }else{ // 관련 부품 추가

        //torelPoid
        //pnumber
        var relNums = document.getElementsByName("pnumber");
        for(var i=0; i<relNums.length; i++) {
            if( (relNums[i].datatype == 'torel') && (relNums[i].rowskey=subfix_item) ) {
                relNums[i].value = _arr[1];
                break;
            }
        }

        var relPoids = document.getElementsByName("torelPoid");
        for(var i=0; i<relPoids.length; i++) {
            if(relPoids[i].rowskey=subfix_item) {
                relPoids[i].value = _arr[0];
                break;
            }
        }

        pnr = document.getElementById("part_number_"+subfix_item);
        pnm = document.getElementById("part_name_"+subfix_item);
        pnv = document.getElementById("part_version_"+subfix_item);

        pnr.innerText=_arr[1];
        pnm.innerText=_arr[2];
        pnv.innerText=_arr[3];

        if(document.forms[0].cadAppType.value != 'UG') {
            doSearchRelatedModel(_arr[0],subfix_item);
        }
    }
}

function doSearchRelatedModel(_oid, _subfix_item) {

    if(!checkTypeOnModelRef(document.forms[0].category.value)) {
        var subarr = new Array();
        subarr[0] = "";
        subarr[1] = "";
        subarr[2] = "";
        subarr[3] = "";

        insertEPMInfo(subarr);
        handleModelBtn(_subfix_item);

    }else{
        epm_subfix_item = _subfix_item;


        var param = "";
        param += "command=SearchRelatedModel";
        param += "&oid=" + _oid;
        param += "&epmoid=" + document.forms[0].oid.value;
        param += "&category=" + document.forms[0].category.value;


        $.ajax({
            type: "POST",
            url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
            data: param,
            async: false,
            error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "doSearchRelatedModel") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");   },
            success: function(xml) {
                rtnSearchRelatedModel(xml);
            }
        });
    }
}


function rtnSearchRelatedModel(xmlDoc) {
    
	var msg = getXmlText(xmlDoc,"l_message");
    
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var subarr = new Array();
    
    subarr[0] = "";
    subarr[1] = "";
    subarr[2] = "";
    subarr[3] = "";
    
    var l_number = xmlDoc.getElementsByTagName("l_number");
    var l_name = xmlDoc.getElementsByTagName("l_name");
    var l_version = xmlDoc.getElementsByTagName("l_version");
    var l_oid = xmlDoc.getElementsByTagName("l_oid");

    if(l_oid != null && l_oid.length > 0) {
        for(var i = 0; i < l_oid.length; i++) {
            r_oid = unescape(l_oid[i].text || l_oid[i].textContent);
            r_number = unescape(l_number[i].text || l_number[i].textContent);
            r_name = unescape(l_name[i].text || l_name[i].textContent);
            r_version = unescape(l_version[i].text || l_version[i].textContent);

            subarr[0] = r_oid;
            subarr[1] = r_number;
            subarr[2] = r_name;
            subarr[3] = r_version;
        }
    }
    

    insertEPMInfo(subarr);
    handleModelBtn(epm_subfix_item);
}


// 관련 부품 추가
function insertRowToPart(iToObj, iDataArr, icheck){

    var isInsert = true;

    if( icheck==true ) {
        isInsert = !checkDuplicate("pnumber", iDataArr[1]);
    }

    if(!isInsert) {
        return true;
    }

    var refnow=new Date();
    var refkey = subfix_item+refnow.valueOf();

    newTr=iToObj.insertRow();
    newTr.id = refkey;

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "100";
    newTd.innerHTML = "<span id='part_number_"+refkey+"'>"+iDataArr[1]+"</span><input type='hidden' name='pnumber' rowskey='"+refkey+"' datatype='torel' value='"+iDataArr[1]+"'><input type='hidden' name='torelPoid' rowskey='"+refkey+"' value='"+iDataArr[0]+"'>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "150";
    newTd.innerHTML = "<div class=\"ellipsis\" style=\"width:150;\"><nobr><span id='part_name_"+refkey+"'>"+iDataArr[2]+"</span></nobr></div>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "40";
    newTd.innerHTML = "<span id='part_version_"+refkey+"'>"+iDataArr[3]+"</span>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "100";
    newTd.innerHTML = btnTableStrForPart(refkey);

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "120";
    newTd.innerHTML = "<span id='epm_number_"+refkey+"'></span><input type='hidden' name='torelModelOid' rowskey='"+refkey+"' value=''>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "40";
    newTd.innerHTML = "<span id='epm_version_"+refkey+"'></span>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "140";
    newTd.innerHTML = btnTableStrForEPM(refkey);


    doSearchRelatedModel(iDataArr[0],refkey);
}

function btnTableStrForPart(id_subfix) {
    var lhtmlStr = "";
    lhtmlStr += "<div id='btn_table_part_"+id_subfix+"'>";
    lhtmlStr += "<table border='0' cellspacing='0' cellpadding='0'>";
    lhtmlStr += "<tr>";
    lhtmlStr += "<td width='10'><img src=\"../../portal/images/btn_1.gif\"></td>";
    lhtmlStr += "<td width='60' background=\"../../portal/images/btn_bg1.gif\" class='btn_blue' nowrap><a href='javascript:;' class='btn_blue' name='delBtn_part_"+id_subfix+"' onclick=\"javascript:removeSeledItem('part','"+id_subfix+"');\"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>";
    lhtmlStr += "<td width='10'><img src=\"../../portal/images/btn_2.gif\"></td>";
    lhtmlStr += "</tr>";
    lhtmlStr += "</table>";
    lhtmlStr += "</div>";

    return lhtmlStr;
}

function btnTableStrForEPM(id_subfix) {
    var lhtmlStr = "";
    lhtmlStr += "<div id='btn_table_epm_"+id_subfix+"'>";
    lhtmlStr += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    lhtmlStr += "<tr>";
    lhtmlStr += "<td>";
    lhtmlStr += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    lhtmlStr += "<tr>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_1.gif\"></td>";
    lhtmlStr += "<td class=\"btn_blue\" background=\"../../portal/images/btn_bg1.gif\" nowrap><a href=\"javascript:;\" onclick=\"javascript:doSeledModel('"+id_subfix+"');\" name='addBtn_epm_"+id_subfix+"' class=\"btn_blue\" ><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_2.gif\"></td>";
    lhtmlStr += "</tr>";
    lhtmlStr += "</table>";
    lhtmlStr += "</td>";
    lhtmlStr += "<td width=\"5\">&nbsp;</td>";
    lhtmlStr += "<td>";
    lhtmlStr += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    lhtmlStr += "<tr>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_1.gif\"></td>";
    lhtmlStr += "<td class=\"btn_blue\" background=\"../../portal/images/btn_bg1.gif\" nowrap><a href=\"javascript:;\" onclick=\"javascript:removeSeledItem('epm','"+id_subfix+"');\" name='delBtn_epm_"+id_subfix+"' class=\"btn_blue\"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_2.gif\"></td>";
    lhtmlStr += "</tr>";
    lhtmlStr += "</table>";
    lhtmlStr += "</td>";
    lhtmlStr += "</tr>";
    lhtmlStr += "</table>";
    lhtmlStr += "</div>";

    return lhtmlStr;
}

function removeSeledItem(_type, _subid) {
    if(_type == 'part') {
        removeItemByPart(_subid);
    }


    if(  !((document.forms[0].manageType.value == 'MOLD_DRAWING') && (document.forms[0].cadAppType.value == 'UG')) ) {
        removeItemByEPM(_subid);
    }

    if( (_subid == 'torep') && (_type == 'part') ) {
        if(  !((document.forms[0].manageType.value == 'MOLD_DRAWING') && (document.forms[0].cadAppType.value == 'UG')) ) {
            document.forms[0].number.value='';
            document.forms[0].name.value='';
        }
        disabledModelBtn(_subid, true);
    }

    /*

        if(_subid == 'torep') {

            document.getElementById("epm_number_"+_subid).innerText='';
            document.getElementById("epm_version_"+_subid).innerText='';
            document.forms[0].repModelOid.value='';


            if(_type == 'part') {
                document.getElementById("part_number_"+_subid).innerText='';
                document.getElementById("part_name_"+_subid).innerText='';
                document.getElementById("part_version_"+_subid).innerText='';

                var repnums = document.getElementsByName("pnumber");
                for(var i=0; i<repnums.length; i++) {
                    if(repnums[i].datatype == 'torep') {
                        repnums[i].value = '';
                        break;
                    }
                }

                document.forms[0].repPoid.value='';
                document.forms[0].number.value='';
                document.forms[0].name.value='';

                disabledModelBtn(_subid, true);
            }
        } else if(_subid == 'torelmodel') {
            document.getElementById("epm_number_"+_subid).innerText='';
            document.getElementById("epm_name_"+_subid).innerText='';
            document.getElementById("epm_version_"+_subid).innerText='';

            document.forms[0].torelModelOidNonPart.value = '';

        } else {
            if(_type == 'part') {
                deleteRowByReledPart(false,_subid);
                return true;
            }

            document.getElementById("epm_number_"+_subid).innerText='';
            document.getElementById("epm_version_"+_subid).innerText='';

            var torelModels = document.getElementsByName("torelModelOid");
            for(var i=0; i<torelModels.length; i++) {
                if(torelModels[i].rowskey == _subid) {
                    torelModels[i].value = '';
                    break;
                }
            }
        }
    */
}

function removeItemByPart(_subid) {
    if(_subid == 'torep') {
        document.getElementById("part_number_"+_subid).innerText='';
        document.getElementById("part_name_"+_subid).innerText='';
        document.getElementById("part_version_"+_subid).innerText='';

        var repnums = document.getElementsByName("pnumber");
        for(var i=0; i<repnums.length; i++) {
            if(repnums[i].datatype == 'torep') {
                repnums[i].value = '';
                break;
            }
        }

        document.forms[0].repPoid.value='';
    } else {
        deleteRowByReledPart(false,_subid);
    }
    return true;
}

function removeItemByEPM(_subid) {
    var lepmNumber = null;
    var lepmVersion = null;
    var lepmName = null;

    if(_subid == 'torep') {
        lepmNumber = document.getElementById("epm_number_"+_subid);
        lepmVersion = document.getElementById("epm_version_"+_subid);

        document.forms[0].repModelOid.value='';

    } else if(_subid == 'torelmodel') {
        lepmNumber = document.getElementById("epm_number_"+_subid);
        lepmName = document.getElementById("epm_name_"+_subid);
        lepmVersion = document.getElementById("epm_version_"+_subid);

        document.forms[0].torelModelOidNonPart.value = '';
    } else {
        lepmNumber = document.getElementById("epm_number_"+_subid);
        lepmVersion = document.getElementById("epm_version_"+_subid);

        var torelModels = document.getElementsByName("torelModelOid");
        for(var i=0; i<torelModels.length; i++) {
            if(torelModels[i].rowskey == _subid) {
                torelModels[i].value = '';
                break;
            }
        }
    }

    if( lepmNumber != null && lepmNumber != 'undefined') {
        lepmNumber.innerText='';
    }

    if( lepmName != null && lepmName != 'undefined') {
        lepmName.innerText='';
    }

    if( lepmVersion != null && lepmVersion != 'undefined') {
        lepmVersion.innerText='';
    }

    return true;
}

function checkDuplicate(iObjName, iObjValue) {
    var cdpObj = document.getElementsByName(iObjName);
    if( (cdpObj == null) || (cdpObj == 'undefined') ) {
        return false;
    }

    for(var i=0; i<cdpObj.length; i++) {
        if(cdpObj[i].value==iObjValue) {
            return true;
        }
    }
    return false;
}
/*****************************************************************************************
    부품 검색 끝 .
 *****************************************************************************************/

/*****************************************************************************************
    Model 검색 시작 ...
 *****************************************************************************************/
//모델 Naming Rule 체크
/* ===============================================
 * 제품도면
 * 도면번호 기준 : 개발검토도면
 * 부품번호 기준 : 제품도면
 *
 * 금형도면
 * 도면번호 : '부품번호' + '_PRT'
 * ===============================================
 */
function doSeledModel(_subfix) {

    if( (_subfix == 'torelmodel') && (document.forms[0].number.value=='')) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01358") %><%--먼저 도면번호를 입력하시기 바랍니다!!!--%>');
        return;
    }

    epm_subfix_item = _subfix;

    var param = "";
    param += "command=select";
    param += "&mode=one";
    param += "&isModel=true";
    param += "&invokeMethod=getSeledEPMs";


    var _folderpathbymodel = "<%=edmProperties.getEPMDefaultFolderPath()%>";
    _folderpathbymodel += "/"+getSeledSelectText("businessType");
    _folderpathbymodel += "<%=edmProperties.getSubPath(manageType, true)%>";
    _folderpathbymodel += "/"+getEndLeafFolderName(document.forms[0].category.value);//_folderpathbymodel += "/"+getSeledSelectText("category");

    //param += "&folderpath="+encodeURI (_folderpathbymodel);
    param += "&folderpath="+encodeURIComponent(_folderpathbymodel);

    url = "/<%=webAppName%>/jsp/edm/edmSearchEPM.jsp?"+param;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=500,height=600');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function getSeledEPMs(_arr) {
    for(var i=0; i < _arr.length; i++){
        if(epm_subfix_item == 'wgmmodel') {
            //document.forms[0].number.value = _arr[i][1];
            //document.forms[0].name.value = _arr[i][2];

            inqDataForEPM(_arr[i]);

        } else {
            goModelFilter(_arr[i]);
        }
        //insertEPMInfo(_arr[i]);
        //handleModelBtn(epm_subfix_item);
    }
}

function inqDataForEPM(_arr) {
    var param = "";
    param += "command=inqDataForEPM";
    param += "&oid=" + _arr[0];

    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "inqDataForEPM") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            callInqDataForEPM(xml);
        }
    });
}

function callInqDataForEPM(xmlDoc) {

    
	var msg = getXmlText(xmlDoc,"l_message");
	
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }
    
    var lvalid = getNodeValue(xmlDoc.getElementsByTagName("l_valid"));
    if( (lvalid == 'false') ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03180") %><%--해당 모델은 이미 관련 연관을 가지고 있습니다--%>');
        return;
    }

    var loid = getNodeValue(xmlDoc.getElementsByTagName("l_oid"));
    var lnumber = getNodeValue(xmlDoc.getElementsByTagName("l_number"));
    var lname = getNodeValue(xmlDoc.getElementsByTagName("l_name"));
    var lversion = getNodeValue(xmlDoc.getElementsByTagName("l_version"));

    var lfileName = getNodeValue(xmlDoc.getElementsByTagName("l_fileName"));
    var lfileSize = getNodeValue(xmlDoc.getElementsByTagName("l_fileSize"));
    var lappDataOid = getNodeValue(xmlDoc.getElementsByTagName("l_appDataOid"));


    var lmanageType = getNodeValue(xmlDoc.getElementsByTagName("l_manageType"));
    var lcategory = getNodeValue(xmlDoc.getElementsByTagName("l_category"));
    var ldevStage = getNodeValue(xmlDoc.getElementsByTagName("l_devStage"));
    var lcadAppType = getNodeValue(xmlDoc.getElementsByTagName("l_cadAppType"));
    var lmanufacturingVersion = getNodeValue(xmlDoc.getElementsByTagName("l_manufacturingVersion"));
    var lisDummyFile = getNodeValue(xmlDoc.getElementsByTagName("l_isDummyFile"));

    var lpoid = getNodeValue(xmlDoc.getElementsByTagName("l_poid"));
    var lpumber = getNodeValue(xmlDoc.getElementsByTagName("l_pumber"));
    var lpname = getNodeValue(xmlDoc.getElementsByTagName("l_pname"));
    var lpversion = getNodeValue(xmlDoc.getElementsByTagName("l_pversion"));

    //도면유형 체크...
    if( (lcategory != '') && (document.forms[0].category.value != lcategory) ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01289") %><%--도면유형과 모델의 도면유형이 일치하지 않습니다\n확인 후 다시 진행하시기 바랍니다--%>');
        //return;
    }

    if( (lcadAppType != '') && (document.forms[0].cadAppType.value != lcadAppType) ) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "00103") %><%--CAD종류가 모델의 CAD종류와 일치하지 않습니다\n확인 후 다시 진행하시기 바랍니다--%>");
        //return;
    }


    document.forms[0].oid.value = loid;
    document.forms[0].number.value = lnumber;
    document.forms[0].name.value = lname;


    var lDataArr = new Array();
    lDataArr[0] = loid;
    lDataArr[1] = lnumber;
    lDataArr[2] = lname;
    lDataArr[3] = lversion;
    lDataArr[4] = lfileName;
    lDataArr[5] = lfileSize;
    lDataArr[6] = lappDataOid;

    insertEPMInfo(lDataArr);


    var lpdata = new Array();
    lpdata[0] = lpoid;
    lpdata[1] = lpumber;
    lpdata[2] = lpname;
    lpdata[3] = lpversion;
    setPartData(lpdata, 'torep');
}

function setPartData(_arr,_subfix) {

    var pnr;
    var pnm;
    var pnv;

    pnr = document.getElementById("part_number_"+_subfix);
    pnm = document.getElementById("part_name_"+_subfix);
    pnv = document.getElementById("part_version_"+_subfix);

    var repnums = document.getElementsByName("pnumber");
    for(var i=0; i<repnums.length; i++) {
        if(repnums[i].datatype == 'torep') {
            repnums[i].value = _arr[1];
            break;
        }
    }

    pnr.innerText=_arr[1];
    pnm.innerText=_arr[2];
    pnv.innerText=_arr[3];

    document.forms[0].repPoid.value=_arr[0];
}


function goModelFilter(_arr) {

    var lpoid = '';
    if(epm_subfix_item == 'torep') {
        lpoid = document.forms[0].repPoid.value;
    } else if(epm_subfix_item == 'torelmodel') {

    } else {
        var ltorelPoids = document.getElementsByName("torelPoid");
        for(var i = 0; i < ltorelPoids.length; i++) {
        	
            if(epm_subfix_item == ltorelPoids[i].getAttribute('rowskey')) {
                lpoid = ltorelPoids[i].getAttribute('value');
                break;
            }
        }
    }

    var params = {
        command:'CheckModelReference',
        number:encodeURIComponent(document.forms[0].number.value),
        oid:encodeURIComponent(document.forms[0].oid.value),
        poid:encodeURIComponent(lpoid),
        category:encodeURIComponent(document.forms[0].category.value),
        cadAppType:encodeURIComponent(document.forms[0].cadAppType.value),
        modelOid:encodeURIComponent(_arr[0])
    };
    var _str0 = $.param(params);


    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: _str0,
        async: false,
        error: function() { alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "inqDataForEPM") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            callRecModel(xml);
        }
    });
}

function callRecModel(xmlDoc) {
	
	var msg = getXmlText(xmlDoc,"l_message");
	
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }
    
    var l_valid = getXmlText(xmlDoc,"l_valid"); 
    var l_ReferencedBy = getXmlText(xmlDoc,"l_ReferencedBy");
    var l_isNamingRule = getXmlText(xmlDoc,"l_isNamingRule");

    lNamingRule = unescape(l_isNamingRule);
    if(lNamingRule == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02877") %><%--추가할 수 없는 3D 모델입니다\n(Naming Rule에 따라 입력하시기 바랍니다!!!--%>');
        return;
    }

    var lvalid;
    var lReferencedBy;
    if((l_valid != null) && (l_valid.length > 0)) { lvalid = unescape(l_valid);  }
    if((l_ReferencedBy != null) && (l_ReferencedBy.length > 0)) { lReferencedBy = unescape(l_ReferencedBy); }

    if( (lvalid == 'false') && (lReferencedBy == 'false') ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03180") %><%--해당 모델은 이미 관련 연관을 가지고 있습니다--%>');
        return;
    }

    var loid = getXmlText(xmlDoc,"l_oid"); 
    var lnumber = getXmlText(xmlDoc,"l_number"); 
    var lname = getXmlText(xmlDoc,"l_name"); 
    var lversion = getXmlText(xmlDoc,"l_version"); 
    //file
    var lfileName = getXmlText(xmlDoc,"l_fileName"); 
    var lfileSize = getXmlText(xmlDoc,"l_fileSize"); 
    var lappDataOid = getXmlText(xmlDoc,"l_appDataOid"); 


    var lDataArr = new Array();
    lDataArr[0] = unescape(loid);//oid
    lDataArr[1] = unescape(lnumber);//number
    lDataArr[2] = unescape(lname);//name
    lDataArr[3] = unescape(lversion);//version

    lDataArr[4] = unescape(lfileName);//version
    lDataArr[5] = unescape(lfileSize);//version
    lDataArr[6] = unescape(lappDataOid);//version

    insertEPMInfo(lDataArr);
    handleModelBtn(epm_subfix_item);
}

function insertEPMInfo(_arr) {
    var enr;
    var enm;
    var env;

    var subfix0 = epm_subfix_item;

    enr = document.getElementById("epm_number_"+subfix0);
    env = document.getElementById("epm_version_"+subfix0);

    if(enr != null && enr != 'undefined') { enr.innerText=_arr[1]; }
    if(env != null && env != 'undefined') { env.innerText=_arr[3]; }

    if(subfix0 == 'torep') {

        document.forms[0].repModelOid.value = _arr[0];

    } else if(subfix0 == 'torelmodel') {

        enm = document.getElementById("epm_name_"+subfix0);
        if(enm != null && enm != 'undefined') { enm.innerText=_arr[2]; }

        document.forms[0].torelModelOidNonPart.value = _arr[0];

    } else if(subfix0 == 'wgmmodel') {
        document.getElementById("epm_number_torep").innerText=_arr[1];
        document.getElementById("epm_version_torep").innerText=_arr[3];
        document.getElementById("primaryFileURL").innerHTML="<a href='javascript:;'>"+_arr[4]+"&nbsp;("+_arr[5]+")</a>";


    } else {
        var torelModels = document.getElementsByName("torelModelOid");
        for(var i=0; i<torelModels.length; i++) {
            if(torelModels[i].getAttribute('rowskey') == subfix0) {
                torelModels[i].value = _arr[0];
                break;
            }
        }
    }
}

function handleModelBtn(epm_subfix) {
    var disabledModel = true;

    //alert(document.getElementById("part_number_"+epm_subfix).innerText.length);
    if(document.forms[0].cadAppType.value != 'UG') {
        if( (checkTypeOnModelRef(document.forms[0].category.value) == true) ) {
            var pnobj = document.getElementById("part_number_"+epm_subfix);
            if( (pnobj != null) && (pnobj != 'undefined') ) {
                if(document.getElementById("part_number_"+epm_subfix).innerText != '') {
                    disabledModel = false;
                }
            }else{
                disabledModel = false;
            }
        }
    }

    disabledModelBtn(epm_subfix, disabledModel);
}

function disabledModelBtn(epm_subfix,_disabled) {
    var tblbtn = document.getElementById("btn_table_epm_"+epm_subfix);
    if(tblbtn != null && tblbtn != 'undefined') {
        if(_disabled) {
            tblbtn.style.display="none";
        } else {
            tblbtn.style.display="";
        }
        return true;
    }

    var abtn = document.getElementById("addBtn_epm_"+epm_subfix);
    if(abtn != null && abtn != 'undefined') {
        abtn.disabled = _disabled;
    }

    var dbtn = document.getElementById("delBtn_epm_"+epm_subfix);
    if(dbtn != null && dbtn != 'undefined') {
        dbtn.disabled = _disabled;
    }
}


 /*****************************************************************************************
    Model 검색 끝 .
 *****************************************************************************************/

function setConvertNrNm(_partnr,_partnm) {

    form = document.forms[0];

    if(form.isNumbering.value == "false") {
        return;
    }
    /*
     <%if( (epm != null) && edmProperties.isKeyInCatsToNrField(category)){%>return;<%}%>
    */
    <%if(epm != null){%>
        if(checkTypeOnKeyInNums(form.category.value)) {
            return;
        }
    <%}%>
    if(checkTypeOnKeyInNums(form.category.value)) {
        return;
        //form.number.value = _partnr;
        //form.name.value = _partnm;
    }
    if( (document.forms[0].manageType.value == 'MOLD_DRAWING') && (document.forms[0].cadAppType.value == 'UG') ) {
        return;
    }

    getConvertedNumber(_partnr, _partnm);

    return;
}

function getConvertedNumber(_partnr,_partnm) {
    /*
        var param = "";
        param += "command=numbering";
        param += "&number="+_partnr;
        param += "&name="+ _partnm;
        param += "&category=" + document.forms[0].category.value;
        param += "&cadAppType=" + document.forms[0].cadAppType.value;
    */

    var params = { command:'numbering',
        number:encodeURIComponent(_partnr),
        name:encodeURIComponent(_partnm),
        category:encodeURIComponent(document.forms[0].category.value),
        cadAppType:encodeURIComponent(document.forms[0].cadAppType.value)
    };
    var _str0 = $.param(params);

    $.ajax({
        type: "POST",
        url: "/<%=webAppName%>/jsp/edm/EDMAjaxAction.jsp",
        data: _str0,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "getConvertedNumber") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            rtnConvertedNumber(xml);
        }
    });
}

function rtnConvertedNumber(xmlDoc) {
	
	var msg = getXmlText(xmlDoc,"l_message");
	
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    
    var l_number = getXmlText(xmlDoc,"l_number");
    var l_name = getXmlText(xmlDoc,"l_name");
    var l_valid =  getXmlText(xmlDoc,"l_valid");

    if(unescape(l_valid) == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02520") %><%--정의되지 않은 부품 번호 입니다\n확인 후 진행하시기 바랍니다--%>');
        document.forms[0].number.value = "";
        document.forms[0].name.value = "";
        return;
    }
    //alert("name1 : " + unescape(l_name[0].text));
    form = document.forms[0];
    if(checkEcadCats(form.category.value)){
        form.number_pcb.value = unescape(l_number)+"_PCB";
        form.name_pcb.value = unescape(l_name);
        form.number_sch.value = unescape(l_number)+"_SCH";
        form.name_sch.value = unescape(l_name);
        //form.number_dwg.value = unescape(l_number[0].text)+"_DWG";
        //form.name_dwg.value = unescape(l_name[0].text);

        form.ecadConvPrefixNumber.value = unescape(l_number);
        form.ecadConvPrefixName.value = unescape(l_name);

    }else{
        form.number.value = unescape(l_number);
        form.name.value = unescape(l_name);

        form.ecadConvPrefixNumber.value = "";
        form.ecadConvPrefixName.value = "";
    }
}


function goAlertMessage(_type, _fg) {
    var hsNumb=history.length;
    if(_type=='ECAD_DRAWING'){
        if(_fg == 'false') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00179") %><%--ECAD 문서 중 작업이 불가한 문서가 있습니다\n확인 후 업무를 진행하시기 바랍니다--%>");

            if(hsNumb > 0) {
                history.go(-1);
            } else {
                if(opener != null) {
                    window.close();
                } else {
                    document.location.href = "/<%=webAppName%>/jsp/edm/CreateEPMDocument.jsp";
                }
            }
        }
    }
}


function doDeleteProject() {
    document.forms[0].project.value = "";
    document.forms[0].projectNumber.value = "";
    document.forms[0].security.disabled = false;
}
/*
function doSelectProject() {
    if(document.forms[0].category.value == '') {
        alert("먼저 도면유형을 선택하시기 바랍니다.");
        return;
    }

    var param = "";
    var param = "command=select";
    param += "&mode=one";
    param += "&invokeMethod=getSeledProject";

    if(document.forms[0].category.value == 'DEV_REVIEW_DRAWING') {
        param += "&type=1";
    } else {
        param += "&type=2";
    }

    url = "/<%=webAppName%>/jsp/project/ProjectSelectPopUp.jsp?"+param;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=500,height=600');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function getSeledProject(_arr) {
    for(var i=0; i < _arr.length; i++){
        document.forms[0].project.value = _arr[i][0];
        document.forms[0].projectNumber.value = _arr[i][2];
    }
}
*/
function popupProject() {
    var param = "mode=one";
    //param += "&status=P"; // TKLEE 2014-10-29 고도화 프로젝트 상태를 빼달라는 요구사항
    if(document.forms[0].category.value == 'DEV_REVIEW_DRAWING') {
        param += "&type=D";
    } else if(document.forms[0].manageType.value == 'MOLD_DRAWING') {
        param += "&type=M";
    } else {
        param += "&type=P";
    }

    var url="/<%=webAppName%>/jsp/project/SearchPjtPop.jsp?"+param;
    openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
}

function setProject(objArr) {

    if(objArr.length == 0) {
        return;
    }

    var trArr;
    var str = "";

    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        if(trArr[9] == "S") {
            document.forms[0].security.value="S2"; //대내비
            document.getElementById("security").disabled = true;
        }else{
            document.forms[0].security.value="S1"; //대외비
            document.getElementById("security").disabled = false;
        }
        document.forms[0].project.value = trArr[0];
        document.forms[0].projectNumber.value = trArr[1];
    }
}



/****************************************************************************************************************
 */

function deleteRowByReledPart(isall, _rowId) {
    var rpst = document.getElementById('relatedPartsTbody');
    if((rpst != null) && (rpst != 'undefined')) {
        if(rpst.rows.length > 0) {
            for(var i = rpst.rows.length; i > 0; i--) {
                if(isall) {
                    rpst.deleteRow[i-1];
                }else{
                    if(rpst.rows[i-1].id == _rowId) {
                        rpst.deleteRow[i-1];
                        break;
                    }
                }
            }
        }
    }
    return;
}

function getNodeValue(_node) {
    if( (_node != null) && (_node.length > 0) ) {
        return unescape(_node[0].firstChild.nodeValue);
        //return unescape(_node[0].text);
    }
    return '';
}

/*
 ****************************************************************************************************************/


function ecadNaming(_objnr, _objnm, _suffix, _flag) {

    if(_flag == false) {
        _objnr.value = "";
        _objnm.value = "";

        return;
    }

    if(document.forms[0].pnumber.value == '') {
        alert("대표 부품을 먼저 선택해 주세요.");
        return;
    }

    _objnr.value = document.forms[0].ecadConvPrefixNumber.value+'_'+_suffix;
    _objnm.value = document.forms[0].ecadConvPrefixName.value;
}


//개발검토도면 번호 검색 팝업
function callNumber() {
    //window.open("/plm/jsp/part/CreatePart_pop2.jsp", "_blank", "width=810, height=420");
    SearchUtil.selectOnePart("setPartNo1","pType=O&number=CP*");
}

function setPartNo1(objArr){
    
    var srcPartNo= "";
    var srcPartName= "";
    
    for ( var i = 0; i < objArr.length; i++ ) {
        
        srcPartNo = objArr[i][1];
        srcPartName = objArr[i][2];
    }
    
    $('#number').val(srcPartNo);
    $('#name').val(srcPartName);
}

function init(){
    var pRank = "<%=pRank%>";
    if(pRank == "S") {
        document.forms[0].security.value = "S2";
        document.forms[0].security.disabled = true;
    }
}
// -->
</script>
</head>

<body <%if(isEcadEditable){%>onload="javascript:goAlertMessage('ECAD_DRAWING','<%=isEcadEditable%>');init();"<%}else{%>onload="init()";<%} %>>
<form autocomplete="off">
<!-- hidden -->
<input type='hidden' name='command' value=''>
<input type='hidden' name='oid' value='<%=oid%>'>
<!-- ECAD -->
<input type='hidden' name='oid_pcb' value='<%=oid_pcb%>'>
<input type='hidden' name='oid_sch' value='<%=oid_sch%>'>
<input type='hidden' name='oid_dwg' value='<%=oid_dwg%>'>

<input type='hidden' name='repPoid' value='<%=repPoid%>'>
<input type='hidden' name='repModelOid' value='<%=repModelOid%>'>


<input type='hidden' name='outputOid' value='<%=outputOid%>'>


<!-- lifecycle/folder -->
<%
    String lifecycle = edmProperties.getEPMDefaultLC();
    if(epm != null) {
        lifecycle = epm.getLifeCycleTemplate().getName();
    }
%>
<input type='hidden' name='lifecycle' value='<%=lifecycle%>'>
<input type='hidden' name='location' value=''>
<input type='hidden' name='pdmLinkProduct' value='<%=edmProperties.getContainer()%>'>

<input type='hidden' name='manageType' value='<%=manageType%>'>
<input type='hidden' name='manufacturingVersion' value='<%=manufacturingVersion%>'>

<input type='hidden' name='isNumbering' value='<%=isNumbering%>'>
<input type='hidden' name='isLibrary' value='<%=isLibrary%>'>
<!-- hidden end -->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tbody id='popup_log' style="display:none;">
    <tr>
        <td height="50" valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="../../portal/images/logo_popupbg.png">
                        <table height="28" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="7"></td>
                                <td width="20"><img src="../../portal/images/icon_3.png"></td><!-- 제품도면 등록 -->
                                <td class="font_01"><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%>&nbsp;<%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
    </tbody>
    <tr>
        <td valign="top">
            <table id="view_log" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="../../portal/images/icon_3.png"></td><!-- 제품도면 등록 -->
                                <td class="font_01"><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%>&nbsp;<%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
                                <!-- home > 도면관리 > 제품도면 등록 -->
                                <td align="right">
                                    <img src="../../portal/images/icon_navi.gif">Home
                                    <img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%>
                                    <img src="../../portal/images/icon_navi.gif"><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%><%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <%if(true) {//if(oid.trim().length() > 0) {
                                %>
                                <%if(isECM == null || !isECM.equals("Y")) { %>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <%}%>
                                <%}%>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
            <col width="120">
            <col width="335">
            <col width="120">
            <col width="335">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%><span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <select name="businessType" class="fm_jmp" style="width:289" readonly>
                            <%
                            String divisionCode = "-";
                            if( CommonUtil.isMember("전자사업부") ) {
                                divisionCode = "E";
                            } else if( CommonUtil.isMember("자동차사업부") ) {
                                divisionCode = "C";
                            } else if( CommonUtil.isKETSUser() ) {
                                divisionCode = "K";
                            }

                            parameter.clear();
                            parameter.put("locale",   messageService.getLocale());
                            parameter.put("codeType", edmProperties.getBizCodeType());
                            numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                            for ( int i=0; i<numCode.size(); i++ ) {
                                if ( divisionCode.equals(numCode.get(i).get("code")) ) {
                            %>
                                    <OPTION VALUE="<%=numCode.get(i).get("oid")%>" selected><%=numCode.get(i).get("value")%></OPTION>
                            <%
                                }
                            }
                            %>
                        </select>
                    </td>
                    <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01530") %><%--보안등급--%><span class="red">*</span></td>
                    <td width="300" class="tdwhiteL0">
                          <select name="security" id="security" style="width:180"  >
                          <%
                          parameter.clear();
                          parameter.put("locale",   messageService.getLocale());
                          parameter.put("codeType", "SECURITYLEVEL");
                          numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                          for ( int i=0; i<numCode.size(); i++ ) {
                          %>
                          <option value="<%=numCode.get(i).get("code") %>" <%if(security.equals(numCode.get(i).get("code"))){%>selected<%}%>><%=numCode.get(i).get("value")%></option>
                          <%
                          }
                          %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%><span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <select name="devStage" class="fm_jmp" style="width:289">
                        <%
                            DevStage[] devStageArr = null;
                            //if(epm == null) {
                                if(manageType.length() > 0) {
                                    devStageArr =  edmProperties.getDevStageSet(manageType);
                                }
                            //} else {
                            //  if(devStage.length() > 0) {
                            //    devStageArr = new DevStage[1];
                            //    devStageArr[0] = DevStage.toDevStage(devStage);
                            //  }
                            //}

                            if(devStageArr == null) {
                                devStageArr = new DevStage[0];
                            }
                        %>
                        <%  if( (epm == null) && ((devStageArr.length == 0) || (devStageArr.length > 1)) ) { %>
                                <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION>
                        <%
                            }

                            DevStage stage = null;
                            for( int i = 0; i < devStageArr.length; i++ )
                            {
                                stage = devStageArr[i];

                                if( ((epm != null) || (project != null)) && devStage.equals("DEV_REVIEW_STAGE") )
                                {
                                    if( !"DEV_REVIEW_STAGE".equals(stage.toString()) )
                                    {
                                        continue;
                                    }
                                    // Added by MJOH
                                    else
                                    {
                        %>
                                        <OPTION VALUE="<%=stage.toString()%>" selected><%=stage.getDisplay(messageService.getLocale())%></OPTION>
                        <%
                                    }
                                }
                                /*
                                else{
                                    if("DEV_REVIEW_STAGE".equals(stage.toString())) { continue; }
                                }
                                */

                                // Added by MJOH, 2011-03-14
                                // 현재 개발단계 도면이면서, 설계변경 사유가 도면양산이관인 경우 양산단계가 선택되도록 수정 처리함
                                else if( epm != null && "DEVELOPMENT_STAGE".equals(devStage.toString()) && mDrawing.equalsIgnoreCase("Y") )
                                {
                                    if( !"PRODUCTION_STAGE".equals(stage.toString()) )
                                    {
                                        continue;
                                    }
                                    else
                                    {
                        %>
                                        <OPTION VALUE="<%=stage.toString()%>" selected><%=stage.getDisplay(messageService.getLocale())%></OPTION>
                        <%
                                    }
                                }
                                else
                                {
                        %>
                                    <OPTION VALUE="<%=stage.toString()%>" <%if(devStage.equals(stage.toString())){%>selected<%}%>><%=stage.getDisplay(messageService.getLocale())%></OPTION>
                        <%
                                }
                            }
                        %>
                        </select>

                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%><span class="red">*</span></td>
                    <td class="tdwhiteL0">
                        <select name="category" id="category" class="fm_jmp" style="width:290">
                        <%
                            CADCategory[] cadCatArr = null;

                            if(epm == null) {
                                ArrayList catArr = new ArrayList();
                                if( (manageType.length() > 0) && (devStage.length() > 0) ) {
                                    catArr = edmProperties.getCADCatsSet(manageType,devStage);
                                }

                                cadCatArr = new CADCategory[catArr.size()];
                                for(int i = 0; i < catArr.size(); i++) {
                                    cadCatArr[i] = (CADCategory)catArr.get(i);
                                }

                            } else {
                                if(category.trim().length() > 0) {
                                    cadCatArr = new CADCategory[1];
                                    cadCatArr[0] = CADCategory.toCADCategory(category);
                                }
                            }

                            if(cadCatArr == null) {
                                cadCatArr = new CADCategory[0];
                            }
                        %>
                        <%  if( (epm == null) && ((cadCatArr.length == 0) || (cadCatArr.length > 1)) ) { %>
                                <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION>
                        <%
                            }

                            CADCategory cadCategory = null;
                            for(int i = 0; i < cadCatArr.length; i++) {
                                cadCategory = cadCatArr[i];
                        %>
                                <OPTION VALUE="<%=cadCategory.toString()%>" <%if(category.equals(cadCategory.toString())){%>selected<%}%>><%=cadCategory.getDisplay(messageService.getLocale())%></OPTION>
                        <%
                            }
                        %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%><span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <select id="cadAppType" name="cadAppType" class="fm_jmp" style="width:289">
                        <%
                            CADAppType[] cadAppTypeArr = null;
                            if(epm == null) {
                                if(category.length() > 0) {
                                    cadAppTypeArr = edmProperties.getCADAppTypeSet(category);
                                }
                            } else {
                                if(cadAppType.length() > 0) {
                                    cadAppTypeArr = new CADAppType[1];
                                    cadAppTypeArr[0] = CADAppType.toCADAppType(cadAppType);
                                }
                            }

                            if(cadAppTypeArr == null) {
                                cadAppTypeArr = new CADAppType[0];
                            }
                        %>
                        <%  if( (epm == null) && ((cadAppTypeArr.length == 0) || (cadAppTypeArr.length > 1)) ) { %>
                                <OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%></OPTION>
                        <%
                            }

                            CADAppType cadApp = null;
                            for(int i = 0; i < cadAppTypeArr.length; i++) {
                                cadApp = cadAppTypeArr[i];
                        %>
                                <OPTION VALUE="<%=cadApp.toString()%>" <%if(cadAppType.equals(cadApp.toString())){%>selected<%}%>><%=cadApp.getDisplay(messageService.getLocale())%></OPTION>
                        <%
                            }
                        %>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL0">
                        <input name="project" type="hidden" value="<%=projectOid%>">
                        <input name="projectNumber" type="text" class="txt_field" style="width:240" value="<%=projectNumber%>" >
                        &nbsp;<a href="javascript:;" onClick="javascript:popupProject();"><img src="../../portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="javascript:;" onClick="javascript:doDeleteProject();"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                    </td>
                </tr>
                <!-- 수정 시 도면버전/양산버전 -->
                <%  if(epm != null) {
                        String mVersion = null;
                        if(ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION) != null) {
                            mVersion = (String)ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION);
                        }

                        // Added by MJOH, 2011-03-14
                        // 설계변경 사유가 도면양산 이관이고, 기존에 양산 버전이 없는 경우 초기 양산버전 P0 부여
                        if( mVersion == null && mDrawing.equalsIgnoreCase("Y") )
                        {
                            mVersion = "0";
                        }
                %>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01274") %><%--도면버전--%></td>
                            <td <%if(mVersion==null && !mDrawing.equalsIgnoreCase("Y")){%>class="tdwhiteL0" colspan="3"<%}else{%>class="tdwhiteL"<%}%>><%=StringUtil.checkNull(IBAUtil.getAttrValue(epm, EDMHelper.IBA_MANUFACTURING_VERSION))%></td>
                            <%if(mVersion != null || mDrawing.equalsIgnoreCase("Y")){%><td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02095") %><%--양산도면버전--%></td><td width="290" class="tdwhiteL0"><%=mVersion%></td><%}%>
                        </tr>
                <%  } %>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01283") %><%--도면설명--%><!-- <span class="red">*</span> --></td>
                    <td colspan="3" class="tdwhiteL0" style="height:50">
                        <textarea name="description" class="txt_field" id="description" style="width:100%; height:96%"><%if(epm != null){%><%=(epm.getDescription()==null)? "":epm.getDescription()%><%}%></textarea>
                    </td>
                </tr>
                <!-- 수정 시 작성자/상태 정보 -->
                <%  if(epm != null) { %>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                            <td class="tdwhiteL"><%=epm.getCreatorFullName()%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                            <td class="tdwhiteL0">
                            <%  String createDeptName = "";
                                EDMUserData ud = EDMHelper.getEDMUserData(epm);
                                if( (ud != null) && (ud.getCreatorDeptName() != null) ) {
                                    createDeptName = ud.getCreatorDeptName();
                                }
                                if(createDeptName.trim().length()==0) { out.print("&nbsp;"); }else{ out.print(createDeptName.trim()); }
                            %>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                            <td class="tdwhiteL"><%=epm.getCreateTimestamp().toString()%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                            <td class="tdwhiteL0"><%=epm.getState().getState().getDisplay(messageService.getLocale())%></td>
                        </tr>
                <%  } %>

                <%
                    boolean fileboolean = false;
                    if(!edmProperties.isCADCatsByEcad(category) && (epm != null)) {//if(!"ECAD_DRAWING".equals(category) && (epm != null) ) {
                        fileboolean = true;
                    }

                    if(!fileboolean) {//if(!"ECAD_DRAWING".equals(category) && (epm != null) ) {
                        if("DEV_REVIEW_DRAWING".equals(category)) {
                            fileboolean = true;
                        }
                    }
                %>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="toDwgDiv" style="display:<%=(fileboolean==false)? "none":""%>;table-layout: fixed;">
                <col width="120">
                <col width="335">
                <col width="120">
                <col width="335">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%><span class="red">*</span></td>
                    <td class="tdwhiteL" style="padding-right: 1px;">
                    <%  boolean isCallNumber = false;
                        if(epm == null) {
                            if(edmProperties.isCatByNumberingSystem(category)) {//if("DEV_REVIEW_DRAWING".equals(category)) {
                                isCallNumber=true;//isCallNumber = true;
                            }
                        }
                    %>
                        <span style="float: left;">
                            <input id="number" type="text" name="number" class="txt_field"  style="width:<%if(isCallNumber){%>230<%}else{%>290<%}%>;" value="<%if(epm != null){%><%=(epm.getNumber()==null)? "":epm.getNumber()%><%}%>" <%if(!keyinfornumber){%>readonly<%}%>>
                        </span>
                        <span id="callNumberBtn" style="float: right;display:<%if(!isCallNumber){%>none<%}%>;">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif" nowrap><a href="javascript:;" onClick="javascript:callNumber();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </span>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%><span class="red">*</span></td>
                    <td class="tdwhiteL0">
                        <input id="name" name="name" type="text" class="txt_field" style="width:290" value="<%if(epm != null){%><%=(epm.getName()==null)? "":epm.getName()%><%}%>" <%if(!keyinforname){%>readonly<%}%>>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02666") %><%--주 도면 첨부--%><span class="red">*</span></td>
                    <td colspan="3" class="tdwhiteL0">
                        <!-- 금형도면/UG -->
                        <table id="ug_model_search_btn" border="0" cellspacing="0" cellpadding="0" style="display:<%=(isMoldUgModel==true)? "":"none"%>;">
                            <tr>
                                <%if(epm == null){%>
                                <td width="100">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td width="80" class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSeledModel('wgmmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <%}%>
                                <td width="<%if(epm==null){%>560<%}else{%>660<%}%>" style="padding-left:3px;">
                                    <span id='primaryFileURL'>
                                    <%  if(isMoldUgModel == true) {
                                            ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(epm);
                                            ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                            if( (item != null) && (item instanceof ApplicationData)) {
                                                ApplicationData appData = (ApplicationData) item;
                                    %>
                                                <%=wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument)holder, item)%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                    <%
                                            }
                                        }
                                    %>
                                    </span>
                                </td>
                            </tr>
                        </table>
                        <table id="table_DummyFile_Flag" border="0" cellspacing="0" cellpadding="0" style="display:<%if(!edmProperties.isCADCatsByDummyFile(category)){%>none<%}%>;">
                            <tr>
                                <td width="680">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>   <!-- Dummy -->
                                            <td><%=messageService.getString("e3ps.message.ket_message", "00174") %><%--Dummy File 여부--%><b>:</b>&nbsp;&nbsp;<input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_YES%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_YES)){%>checked<%}%>>Y
                                                <input name="isDummyFile" type="radio" class="Checkbox" value="<%=EDMHelper.IBA_DUMMY_FILE_VALUE_NO%>" <%if(isDummyFile.equals(EDMHelper.IBA_DUMMY_FILE_VALUE_NO)){%>checked<%}%>>N
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <div id="primary_file_attach_div" style="margin:0;padding:0;border:0;display:<%=(isMoldUgModel==false)? "":"none"%>;">
                            <jsp:include page="/jsp/edm/AttachFile.jsp" flush="false">
                                <jsp:param name="oid" value="<%=oid%>"/>
                                <jsp:param name="isPrimary" value="<%=String.valueOf(true)%>"/>
                                <jsp:param name="primaryName" value="primary"/>
                                <jsp:param name="width" value="680"/>
                            </jsp:include>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01537") %><%--부도면 첨부--%></td>
                    <td colspan="3" class="tdwhiteL0">
                        <jsp:include page="/jsp/edm/AttachFile.jsp" flush="false">
                            <jsp:param name="oid" value="<%=oid%>"/>
                            <jsp:param name="isPrimary" value="<%=String.valueOf(false)%>"/>
                            <jsp:param name="secondaryName" value="secondary"/>
                            <jsp:param name="delFileName" value="delFile"/>
                            <jsp:param name="width" value="680"/>
                        </jsp:include>
                    </td>
                </tr>
                </table>

                <!-- 관련 3D 모델 시작 -->
                <%  EPMDocument refModel = null;
                    if(epm != null) {
                        if(edmProperties.isRefModel(category) && edmProperties.isNonPart(category)) {
                            ArrayList modelArry = EDMHelper.getReferencedByModels(epm,EDMHelper.REQUIRED_REFERENCE_MODEL);
                            if( (modelArry != null) && (modelArry.size() > 0) ) {
                                refModel = (EPMDocument)((Object[])modelArry.get(0))[1];
                            }
                        }
                    }
                %>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="torelModelNonPartDiv" style="display:<%if( !(edmProperties.isRefModel(category) && edmProperties.isNonPart(category)) ){%>none<%}%>;table-layout: fixed;">
                <col width="121">
                <col width="*">
                <tr><!-- hidden --><input type=hidden name='torelModelOidNonPart' value='<%=(refModel==null)? "":rf.getReferenceString(refModel)%>'>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00902") %><%--관련 3D모델--%><br><%=messageService.getString("e3ps.message.ket_message", "01537") %><%--부도면 첨부--%></td>
                    <td class="tdwhiteL0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="180" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                                <td width="310" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00053") %><%--3D 모델명--%></td>
                                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></td>
                            </tr>
                            <tr>
                                <td width="180" class="tdwhiteM"><a href="javascript:;"><span id='epm_number_torelmodel'><%=(refModel==null)? "&nbsp;":refModel.getNumber()%></span></a></td>
                                <td width="310" class="tdwhiteM">
                                    <div class="ellipsis" style="width:305;"><nobr>
                                        <p><span id='epm_name_torelmodel'><%=(refModel==null)? "&nbsp;":refModel.getName()%></span></p>
                                    </nobr></div>
                                </td>
                                <td width="50" class="tdwhiteM"><span id='epm_version_torelmodel'><%=(refModel==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(refModel, EDMHelper.IBA_MANUFACTURING_VERSION)) %></span></td>
                                <td  class="tdwhiteM">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="addBtn_epm_torelmodel" href="javascript:;"  onClick="javascript:doSeledModel('torelmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="5">&nbsp;</td>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="delBtn_epm_torelmodel" href="javascript:;" onClick="javascript:removeSeledItem('epm','torelmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <!-- 개발검토도면 -->
                                    <div id="desc_DEV_REVIEW_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"DEV_REVIEW_DRAWING".equals(category)){%>none<%}%>;">
                                        <!-- <br>
                                        <font color="black">
                                        1. 개발검토 도면의 "도면번호"와 "도면명"은 직접 입력해야 합니다.<br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;- 도면번호,도면명: 개발검토프로젝트번호 + 부품명
                                        </font>
                                         -->
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>

                <!-- 대표부품/3D 모델 시작 -->
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="torepPartDiv" style="display:<%if((category.length() == 0) || edmProperties.isNonPart(category)){%>none<%}%>;table-layout: fixed;">
                <col width="121">
                <col width="*">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01242") %><%--대표부품--%><span id="essentialElementMarkByPart" class="red" style="display:;">*</span><br><%=messageService.getString("e3ps.message.ket_message", "00051") %><%--3D 모델--%></td>
                    <td class="tdwhiteL0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM">
                                <%  if(isNumbering || isAdmin ) {%>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td width="60" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a id="addBtn_part_torep" href="javascript:;" onClick="javascript:doSeledPart('torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                <%  } else {%>&nbsp;<% } %>
                                </td>
                                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01370") %><%--모델검색/삭제--%></td>
                            </tr>
                            <tr>
                                <td width="100" class="tdwhiteM"><a href="javascript:;"><span id='part_number_torep'><%=(part==null)? "":part.getNumber()%></span></a><input type='hidden' name='pnumber' datatype='torep' value='<%=(part==null)? "":part.getNumber()%>'></td>
                                <td width="150" class="tdwhiteM">
                                    <div class="ellipsis" style="width:150;"><nobr>
                                        <p><span id='part_name_torep'><%=(part==null)? "":part.getName()%></span></p>
                                    </nobr></div>
                                </td>
                                <td width="40" class="tdwhiteM"><span id='part_version_torep'><%=(part==null)? "":StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision)) %></span></td>
                                <td width="100" class="tdwhiteM">
                                <%  if(isNumbering) {%>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td width="60" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a id="delBtn_part_torep" href="javascript:;" onClick="javascript:removeSeledItem('part','torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                <%  } else {%>&nbsp;<% } %>
                                </td>
                                <td width="120" class="tdwhiteM"><a href="javascript:;"><span id='epm_number_torep'><%=(model==null)? "":model.getNumber()%></span></a></td>
                                <td width="40" class="tdwhiteM"><span id='epm_version_torep'><%=(model==null)? "":StringUtil.checkNull(IBAUtil.getAttrValue(model, EDMHelper.IBA_MANUFACTURING_VERSION)) %></span></td>
                                <td class="tdwhiteM">
                                    <div id="btn_table_epm_torep" style="display:<%if((category.length() == 0) || !edmProperties.isRefModel(category) || (isMoldUgModel)){%>none<%}%>;">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif" nowrap><a id="addBtn_epm_torep" href="javascript:;" onClick="javascript:doSeledModel('torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="5">&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="delBtn_epm_torep" href="javascript:;" onClick="javascript:removeSeledItem('epm','torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <!-- 제폼도면 -->
                                    <div id="desc_PRODUCT_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRODUCT_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01189") %><%--다품일도의 부품들은 대표부품을 먼저 등록합니다. (주의 : 대표부품 품번 기준으로 도면번호가 채번됨)--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02870") %><%--추가로 등록되는 부품을 관련부품 항목에 등록합니다. (추가 관련부품의 도면은 대표도번으로 검색해야 함)--%><br>
                                        </font>
                                    </div>
                                    <!-- 승인도면 -->
                                    <div id="desc_APPROVAL_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"APPROVAL_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01994") %><%--승인도면은 CATIA V4, CATIA V5, UG 도면만 등록할 수 있습니다--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "01993") %><%--승인도면과 관련된 모델은 첨부파일로 등록합니다--%><br>
                                        </font>
                                    </div>
                                    <!-- 고객제출도면 -->
                                    <div id="desc_CUSTOMER_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"CUSTOMER_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "00858") %><%--고객제출도면은 AutoCAD 도면만 등록할 수 있습니다--%><br>
                                        </font>
                                    </div>
                                    <!-- ECAD 도면 -->
                                    <div id="desc_ECAD_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"ECAD_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "00180") %><%--ECAD는 PCB Assembly를 대표부품으로 등록합니다--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "00181") %><%--ECAD는 PCB, Schematic 도면을 필수로 등록해야 합니다--%><br>
                                        <b>3. <%=messageService.getString("e3ps.message.ket_message", "00075") %><%--AutoCAD는 AutoCAD 도면첨부 파일이 없는 경우에 등록하지 않습니다--%></b>
                                        </font>
                                    </div>
                                    <!-- 제품참고도면 -->
                                    <div id="desc_PRODUCT_REFERENCE_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRODUCT_REFERENCE_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "02622") %><%--제품참고도면은 Assembly 부품의 참조도면으로 관리한다--%><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;- <%=messageService.getString("e3ps.message.ket_message", "00068") %><%--Assembly 부품을 "대표부품"으로 지정한다--%>
                                        </font>
                                    </div>
                                    <!-- 프레스금형도면 -->
                                    <div id="desc_PRESS_MOLD_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRESS_MOLD_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01188") %><%--다품일도의 부품들은 대표부품을 먼저 등록합니다.--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02869") %><%--추가로 등록되는 부품을 관련부품 항목에 등록합니다.--%><br>
                                        3. <%=messageService.getString("e3ps.message.ket_message", "03070") %><%--프로젝트 번호는 제품 프로젝트 번호를 등록합니다.--%><br>
                                        </font>
                                    </div>
                                    <!-- 사출금형도면 -->
                                    <div id="desc_INJECTION_MOLD_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"INJECTION_MOLD_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01188") %><%--다품일도의 부품들은 대표부품을 먼저 등록합니다.--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02869") %><%--추가로 등록되는 부품을 관련부품 항목에 등록합니다.--%><br>
                                        3. <%=messageService.getString("e3ps.message.ket_message", "03070") %><%--프로젝트 번호는 제품 프로젝트 번호를 등록합니다.--%><br>
                                        </font>
                                    </div>
                                    <!-- 사출금형SET도면 -->
                                    <div id="desc_INJECTION_MOLD_SET_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"INJECTION_MOLD_SET_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01022") %><%--금형 셋트 관련도면은 금형셋트(Die)의 참조 도면으로 관리됩니다--%>.<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "03070") %><%--프로젝트 번호는 제품 프로젝트 번호를 등록합니다.--%><br>
                                        <!-- 2. 참조되는 3D모델은 없습니다.(비활성) -->
                                        </font>
                                    </div>
                                    <!-- 프레스금형SET도면 -->
                                    <div id="desc_PRESS_MOLD_SET_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRESS_MOLD_SET_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01022") %><%--금형 셋트 관련도면은 금형셋트(Die)의 참조 도면으로 관리됩니다--%>.<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02764") %><%--참조되는 3D모델은 없습니다(비활성)--%>
                                        3. <%=messageService.getString("e3ps.message.ket_message", "03070") %><%--프로젝트 번호는 제품 프로젝트 번호를 등록합니다.--%><br>
                                        </font>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>

                <!-- 관련 부품 / 3D 모델 시작 -->

                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="relatedPartsDiv" style="display:<%if((category.length() == 0) || edmProperties.isNonPart(category) || edmProperties.isOnlyRefesCat(category)){%>none<%}%>;" >
                <col width="121">
                <col width="*">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00934", "<br>") %><%--관련부품{0}[3D 모델]--%></td>
                    <td class="tdwhiteL0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td>&nbsp;</td>
                                <td align="right">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSeledPart('torel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00936") %><%--관련부품추가--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="150" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM">&nbsp;</td>
                                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="140" class="tdgrayM">&nbsp;</td>
                            </tr>
                            <tbody id="relatedPartsTbody"><!-- 관련 부품 목록 -->
                            <%
                                if(epm != null) {

                                    ArrayList relatedParts = null;
                                    try {
                                        relatedParts = EDMHelper.getReferencedByParts(epm,edmProperties.getReferenceType(category),EDMHelper.REQUIRED_RELATED);

                                    }
                                    catch(Exception e) {
                                    Kogger.error(e);
                                    }

                                    if(relatedParts != null) {

                                        long refkey = System.currentTimeMillis();

                                        WTPart relatedPart0 = null;
                                        EPMDocument relatedModel0 = null;

                                        ArrayList models = null;
                                        for(int i = 0; i < relatedParts.size(); i++) {
                                            relatedPart0 = (WTPart)((Object[])relatedParts.get(i))[1];

                                            if( (relatedPart0 != null) && edmProperties.isRefModel(category)) {
                                                relatedModel0 = EDMHelper.getAssociatedModel(epm, relatedPart0,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_RELATED);
                                            }

                                            refkey = refkey+1;
                            %>
                                            <tr id="<%=refkey%>">
                                                <td width="100" class="tdwhiteM">
                                                    <span id='part_number_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getNumber()%></span>
                                                    <input type='hidden' name='pnumber' rowskey='<%=refkey%>' datatype='torel' value='<%=(relatedPart0==null)? "":relatedPart0.getNumber()%>'>
                                                    <input type='hidden' name='torelPoid' rowskey='<%=refkey%>' value='<%=(relatedPart0==null)? "":PersistenceHelper.getObjectIdentifier(relatedPart0).getStringValue()%>'>
                                                </td>
                                                <td width="150" class="tdwhiteM">
                                                    <div class="ellipsis" style="width:150;"><nobr>
                                                        <span id='part_name_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getName()%></span>
                                                    </nobr></div>
                                                </td>
                                                <td width="40" class="tdwhiteM"><span id='part_version_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":StringUtil.checkNull(PartSpecGetter.getPartSpec(relatedPart0, PartSpecEnum.SpPartRevision))%></span></td>
                                                <td width="100" class="tdwhiteM">
                                                    <div id='btn_table_part_<%=refkey%>'>
                                                        <table border='0' cellspacing='0' cellpadding='0'>
                                                            <tr>
                                                                <td width='10'><img src="../../portal/images/btn_1.gif"></td>
                                                                <td width='60' background="../../portal/images/btn_bg1.gif" class='btn_blue'><a href="javascript:;" onClick="javascript:removeSeledItem('part','<%=refkey%>');" class='btn_blue' name='delBtn_part_<%=refkey%>' ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                <td width='10'><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                                <td width="120" class="tdwhiteM">
                                                    <span id='epm_number_<%=refkey%>'><%=(relatedModel0==null)? "&nbsp;":relatedModel0.getNumber()%></span>
                                                    <input type='hidden' name='torelModelOid' rowskey='<%=refkey%>' value='<%=(relatedModel0==null)? "":PersistenceHelper.getObjectIdentifier(relatedModel0).getStringValue()%>'>
                                                </td>
                                                <td width="40" class="tdwhiteM"><span id='epm_version_<%=refkey%>'><%=(relatedModel0==null)? "&nbsp;":StringUtil.checkNull(IBAUtil.getAttrValue(relatedModel0, EDMHelper.IBA_MANUFACTURING_VERSION))%></span></td>
                                                <td class="tdwhiteM">
                                                    <div id='btn_table_epm_<%=refkey%>' style="display:<%if((category.length() == 0) || !edmProperties.isRefModel(category)|| (isMoldUgModel)){%>none<%}%>;">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td>
                                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doSeledModel('<%=refkey%>');" name='addBtn_epm_<%=refkey%>' class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <td width="5">&nbsp;</td>
                                                                <td>
                                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:removeSeledItem('epm','<%=refkey%>');" name='delBtn_epm_<%=refkey%>' class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                            </tbody>

                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <%
                    String numberPCB = (ecadPCB==null)? "":ecadPCB.getNumber();
                    String namePCB = (ecadPCB==null)? "":ecadPCB.getName();
                    String numberSCH = (ecadSCH==null)? "":ecadSCH.getNumber();
                    String nameSCH = (ecadSCH==null)? "":ecadSCH.getName();
                    String numberDWG = (ecadDWG==null)? "":ecadDWG.getNumber();
                    String nameDWG = (ecadDWG==null)? "":ecadDWG.getName();

                    if( (numberPCB.length() == 0) && (part != null) ) {
                    
                        if(!numberPCB.endsWith("_PCB")){
                            numberPCB = edmProperties.getConvertedNumber(part.getNumber(), category, null)+"_PCB";
                        }
                        namePCB = part.getName();
                    }

                    if( (numberSCH.length() == 0) && (part != null) ) {
                    
                        if(!numberSCH.endsWith("_SCH")){
                           numberSCH = edmProperties.getConvertedNumber(part.getNumber(), category, null)+"_SCH";
                        }
                        nameSCH = part.getName();
                    }

                    if( (numberDWG.length() == 0) && (part != null) ) {
                    
                        if(!numberDWG.endsWith("_DWG")){
                          numberDWG = edmProperties.getConvertedNumber(part.getNumber(), category, null)+"_DWG";
                        }
                        nameDWG = part.getName();

                    }
                %>
                <!-- ECAD 시작 -->
                <table width="100%" border="0" cellspacing="0" cellpadding="0" id="toEcadDiv" style="display:<%if(!"ECAD_DRAWING".equals(category)){%>none<%}%>;">
                <input type=hidden name="ecadConvPrefixNumber" value="<%=(part == null)? "":edmProperties.getConvertedNumber(part.getNumber(), category, null)%>">
                <input type=hidden name="ecadConvPrefixName" value="<%=(part == null)? "":part.getName()%>">
                <% String spdWidth = "305"; %>
                <tr id="toEcadDiv_pcb" style="display:;">
                    <td class="tdwhiteM0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border" style="table-layout: fixed;">
                            <col width="125"/>
                            <col width="*"/>
                            <col width="125"/>
                            <col width="*"/>
                            <tr>
                                <td width="125" class="tdgrayR">PCB&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
                                <td class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td>
                                                <input type="text" name="number_pcb" value="<%=numberPCB%>" class="txt_field" <%if(ecadPCB==null){%>style="width:145;padding-right:3px;"<%}else{%>style="width:330;"<%}%> style="background-color:#EFEFEF;" id="number_pcb" readonly>
                                            </td>
                                            <%if(ecadPCB==null){%>
                                            <td style="width:88;padding-left:0;padding-right:2px;">
                                                <table border="0" cellspacing="0" cellpadding="0" >
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_pcb, document.forms[0].name_pcb, 'PCB', true);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td style="width:88;padding-left:0;padding-right:2px;">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_pcb, document.forms[0].name_pcb, 'PCB', false);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>
                                                       </a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <%}%>
                                         </tr>
                                    </table>
                                </td>
                                <td width="125" class="tdgrayR">PCB&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
                                <td  class="tdwhiteL"><input type="text" name="name_pcb" value="<%=namePCB%>" class="txt_field"  style="width:98%;background-color:#EFEFEF;" id="name_pcb" readonly></td>
                            </tr>
                            <tr>
                                <td width="125" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00357") %><%--PCB 도면첨부--%></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0" style="border:0;padding:0;margin:0;">
                                        <tr>
                                            <td>
                                                <input type="file" name="file_pcb_primary" class="txt_field"  style="width:800px" id="file_pcb_primary">
                                            </td>
                                        </tr>
                                    </table>
                                    <%  if(ecadPCB != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="820">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="825">
                                                <tr>
                                                    <td>
                                                        <div id="pcb_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadPCB);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                                                            %>
                                                                    <a target='blank' href="<%=cntInfo.getDownURL()%>"><%=cntInfo.getName()%>&nbsp;(<%=cntInfo.getFileSize() + " KB"%>)</a>
                                                            <%  }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                    <%  } %>
                                </td>
                            </tr>
                            <tr>
                                <td width="125" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00245") %><%--Gerber데이터첨부--%></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_gerber_primary" class="txt_field"  style="width:800px" id="file_gerber_primary"></td>
                                        </tr>
                                    </table>
                                    <%  ApplicationData gerberData = null;
                                        if(ecadPCB != null) {
                                            ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadPCB);
                                            QueryResult contents = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
                                            while(contents.hasMoreElements()) {
                                                ContentItem item0 = (ContentItem)contents.nextElement();
                                                if(item0 instanceof ApplicationData) {
                                                    gerberData = (ApplicationData)item0;
                                                }
                                            }

                                            if(gerberData != null) {
                                                ContentInfo cntInfo = ContentUtil.getContentInfo(holder, gerberData);

                                                String appDataOid = cntInfo.getContentOid();
                                                long appDataId = PersistenceHelper.getObjectIdentifier(gerberData).getId();
                                    %>
                                                <table border="0" cellspacing="0" cellpadding="0" width="825">
                                                    <tr>
                                                        <td class="space5"></td>
                                                    </tr>
                                                </table>
                                                <table width="825" cellpadding="0" cellspacing="0" class="table_border" style="margin-bottom:3px;padding:2px;">
                                                    <tr>
                                                        <td width="50" class="tdwhiteM0">
                                                            <div id="gerber_delete_<%=appDataId%>" style="display:;">
                                                                <table border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                        <td id="gerber_delete_btn_<%=appDataId%>" class="btn_blue" background="../../portal/images/btn_bg1.gif" width="80px"><a href="javascript:;" onClick="javascript:goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','true','gerber_delete_<%=appDataId%>','gerber_cancel_<%=appDataId%>','gerberDelFile');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></font></a></td>
                                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                            <div id="gerber_cancel_<%=appDataId%>" style="display:none;">
                                                                <table border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="80px"><a href="javascript:;" onClick="javascript:goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','false','gerber_cancel_<%=appDataId%>','gerber_delete_<%=appDataId%>','gerberDelFile');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></font></a></td>
                                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                        </td>
                                                        <td width="775" class="tdwhiteL0">
                                                            <div id="gerber_<%=appDataId%>" style="text-decoration:none;">
                                                                <a target='blank' href="<%=cntInfo.getDownURL()%>"><%=cntInfo.getName()%>&nbsp;(<%=cntInfo.getFileSize() + " KB"%>)</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                        <%  } %>
                                    <%  } %>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr id="toEcadDiv_sch" style="display:;">
                    <td class="tdwhiteM0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border" style="table-layout: fixed;">
                            <col width="125"/>
                            <col width="*"/>
                            <col width="125"/>
                            <col width="*"/>
                            <tr>
                                <td width="125" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00467") %><%--Schematic{0} 도면번호--%></td>
                                <td  class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                        <td>
                                            <input type="text" name="number_sch" value="<%=numberSCH%>" class="txt_field" <%if(ecadSCH==null){%>style="width:145;padding-right:3px;"<%}else{%>style="width:330;"<%}%> style="background-color:#EFEFEF;" id="number_sch" readonly>
                                        </td>
                                        <%if(ecadSCH==null){%>
                                        <td style="width:88;padding-left:0;padding-right:2px;">
                                            <table border="0" cellspacing="0" cellpadding="0" >
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_sch, document.forms[0].name_sch, 'SCH', true);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td style="width:88;padding-left:0;padding-right:2px;">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_sch, document.forms[0].name_sch, 'SCH', false);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>
                                                   </a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%}%>
                                        </tr>
                                    </table>
                                </td>
                                <td width="125" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00459") %><%--Schematic 도면명--%></td>
                                <td  class="tdwhiteL"><input type="text" name="name_sch" value="<%=nameSCH%>" class="txt_field"  style="width:98%;background-color:#EFEFEF;" id="name_sch" readonly></td>
                            </tr>
                            <tr>
                                <td width="125" class="tdgrayR"><%=messageService.getString("e3ps.message.ket_message", "00463") %><%--Schematic도면첨부--%></td>
                                <td colspan="3" class="tdwhiteL" >
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_sch_primary" class="txt_field"  style="width:800px" id="file_sch_primary"></td>
                                        </tr>
                                    </table>
                                    <%  if(ecadSCH != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="825">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="825">
                                                <tr>
                                                    <td>
                                                        <div id="sch_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadSCH);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    //ApplicationData appData = (ApplicationData) item;
                                                                    ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                                                            %>
                                                                    <a target='blank' href="<%=cntInfo.getDownURL()%>"><%=cntInfo.getName()%>&nbsp;(<%=cntInfo.getFileSize() + " KB"%>)</a>
                                                            <%  }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                    <%  } %>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr id="toEcadDiv_dwg" style="display:;">
                    <td class="tdwhiteM0">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border"  style="table-layout: fixed;">
                            <col width="125"/>
                            <col width="*"/>
                            <col width="125"/>
                            <col width="*"/>
                            <tr>
                                <td width="125" class="tdgrayR" style="width: 125">AutoCAD&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%>
                                </td>
                                <td  class="tdwhiteL" >
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td>
                                                <input type="text" name="number_dwg" value="<%=numberDWG%>" class="txt_field" <%if(ecadDWG==null){%>style="width:145;padding-right:3px;"<%}else{%>style="width:330;"<%}%> style="background-color:#EFEFEF;" id="number_dwg" readonly>
                                            </td>
                                            <%if(ecadDWG==null){%>
                                            <td style="width:88;padding-left:0;padding-right:2px;">
                                                <table border="0" cellspacing="0" cellpadding="0" >
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_dwg, document.forms[0].name_dwg, 'DWG', true);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td style="width:88;padding-left:0;padding-right:2px;">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif" width="78px"><a href="javascript:;" onClick="javascript:ecadNaming(document.forms[0].number_dwg, document.forms[0].name_dwg, 'DWG', false);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>
                                                       </a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <%}%>
                                        </tr>
                                    </table>
                                </td>
                                <td width="125" class="tdgrayR">AutoCAD&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%>
                                </td>
                                <td  class="tdwhiteL"><input type="text" name="name_dwg" value="<%=nameDWG%>" class="txt_field"  style="width:98%;background-color:#EFEFEF;" id="name_dwg" readonly></td>
                            </tr>
                            <tr>
                                <td width="125" class="tdgrayR">AutoCAD&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01294") %><%--도면첨부--%>
                                </td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_dwg_primary" class="txt_field"  style="width:800px" id="file_dwg_primary"></td>
                                        </tr>
                                    </table>
                                    <%  if(ecadDWG != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="825">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="825">
                                                <tr>
                                                    <td>
                                                        <div id="dwg_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadDWG);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    //ApplicationData appData = (ApplicationData) item;
                                                                    ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                                                            %>
                                                                    <a target='blank' href="<%=cntInfo.getDownURL()%>"><%=cntInfo.getName()%>&nbsp;(<%=cntInfo.getFileSize() + " KB"%>)</a>
                                                            <%  }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                    <%  } %>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom"><iframe id="copyright" src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
    </tr>
</table>
</form>
</body>
</html>
<script type="text/Javascript">
<!--

function goPageChange(_obj) {
    document.location.href="/<%=webAppName%>jsp/edm/CreateEPMDocument.jsp?manageType="+_obj.value;
}

/*****************************************************************************************
 * Event ...
 *****************************************************************************************/
function goChangeEvent(oEvent) {
    var oEvnetObj = oEvent.srcElement;
    var oEvnetObjName = oEvnetObj.name;
    if(oEvnetObjName == 'devStage') {
        doChangeDevStage(oEvnetObj);
    }else if(oEvnetObjName == 'category'){
        doCategoryFilter(oEvnetObj);
    //}else if(oEvnetObjName == 'manageType'){
    //  goPageChange(oEvnetObj);
    }else if(oEvnetObjName == 'cadAppType'){
        doCadAppTypeFilter(oEvnetObj);
    }
}

function goKeyupEvent(oEvent) {
    var oEvnetObj = oEvent.srcElement;
    var oEvnetObjName = oEvnetObj.name;
    //alert(oEvnetObj.tagName);
    if(oEvnetObjName == 'description') {
        //length check
        var le = lwGetByte(document.forms[0].description.value);
        if( (maxLength_description > 0) && (le > maxLength_description)) {
            alert(maxLength_description+"<%=messageService.getString("e3ps.message.ket_message", "00010") %><%--{0}bytes 이하로 입력하시기 바랍니다--%>");
            //document.forms[0].description.value = lwTrimByte(document.forms[0].description.value, maxLength_description);
        }
    }
}

function goClickEvent(oEvent) {
    var oEvnetObj = oEvent.srcElement;
    var oEvnetObjName = oEvnetObj.name;
}


function changeIE(){if(event.type=='change'){goChangeEvent(event);}}
function keyupIE(){if(event.type=='keyup'){goKeyupEvent(event);}}
function blurIE(){if(event.type=='blur'){goKeyupEvent(event);}}
function clickIE(){if(event.type=='click'){goClickEvent(event);}}

//onChange event
<%if(epm == null){%>document.forms[0].devStage.onchange=changeIE;<%}%>
<%if(epm == null){%>document.forms[0].category.onchange=changeIE;<%}%>
<%if(epm == null){%>document.forms[0].cadAppType.onchange=changeIE;<%}%>

document.forms[0].manageType.onchange=changeIE;

//onKeyup event
//document.forms[0].description.onkeyup=keyupIE;
document.forms[0].description.onblur=blurIE;


function goFileMarkingIE() {
    var _obj = event.srcElement;
    var prm;
    if(_obj.id=='file_pcb_primary') {
        prm="pcb_"
    } else if(_obj.id=='file_sch_primary') {
        prm="sch_"
    } else if(_obj.id=='file_dwg_primary') {
        prm="dwg_"
    }

    var primaryCnt = document.getElementById(prm+"primaryContentDiv");
    if(primaryCnt != null && primaryCnt != 'undefined') {
        if(_obj.value.length > 0) {
            primaryCnt.style.textDecorationLineThrough=true;
            primaryCnt.style.color='red';
        }else{
            primaryCnt.style.textDecorationLineThrough=false;
            primaryCnt.style.color='';
        }
    }
}

<%if(ecadPCB !=null){%>document.getElementById("file_pcb_primary").onchange=goFileMarkingIE;<%}%>
<%if(ecadSCH !=null){%>document.getElementById("file_sch_primary").onchange=goFileMarkingIE;<%}%>
<%if(ecadDWG !=null){%>document.getElementById("file_dwg_primary").onchange=goFileMarkingIE;<%}%>


function goFileDeleteMarking(_key,_fileid,_flag,_hidden,_display,_delFieldName) {
    var delFileDiv = document.getElementById(_fileid);
    var btnHidden = document.getElementById(_hidden);
    var btnDisplay = document.getElementById(_display);

    if(delFileDiv != null && delFileDiv != 'undefined') {
        if(_flag == 'true') {
            delFileDiv.style.textDecorationLineThrough=true;
            delFileDiv.style.color='red';
        }else{
            delFileDiv.style.textDecorationLineThrough=false;
            delFileDiv.style.color='';
        }

        if(_flag == 'true') {
            var newObjFile = document.createElement('INPUT');
            newObjFile.type='hidden';
            newObjFile.name=_delFieldName;
            newObjFile.value = _key;
            delFileDiv.insertAdjacentElement('beforeEnd',newObjFile);

        } else {
            var chNodes = delFileDiv.children.tags('INPUT');
            for(var i = 0; i < chNodes.length; i++) {
                chNode = chNodes[i];
                if(chNode.name = _delFieldName) {
                    delFileDiv.removeChild(chNode);
                    break;
                }
            }
        }
    }

    btnHidden.style.display='none';
    btnDisplay.style.display='';
}

<%  if(gerberData != null) {
        String appDataOid = PersistenceHelper.getObjectIdentifier(gerberData).getStringValue();
        long appDataId = PersistenceHelper.getObjectIdentifier(gerberData).getId();
%>
        function gerberFileMarkingIE() {
            var _obj = event.srcElement;

            if(_obj.value.length > 0) {
                goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','true','gerber_delete_<%=appDataId%>','gerber_cancel_<%=appDataId%>','gerberDelFile');
                document.getElementById('gerber_delete_<%=appDataId%>').style.display='none';
                document.getElementById('gerber_cancel_<%=appDataId%>').style.display='none';
            }else{
                goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','false','gerber_cancel_<%=appDataId%>','gerber_delete_<%=appDataId%>','gerberDelFile');
            }
        }

        document.getElementById("file_gerber_primary").onchange=gerberFileMarkingIE;
<%  }%>

document.onkeydown=checkKey;

function checkKey(){
    //alert("You pressed a following key: "+window.event.keyCode);
    // ESC Key 누를 때 데이터 사라지는 것 방지
    if(window.event.keyCode == 27){
        window.event.returnValue = false;
        return;
    }

    // back-space 누를 때
    if(window.event.keyCode == 8){
        // TextEdit가 아니면 작동하지 않도록
        if(!window.event.srcElement.isTextEdit){
            window.event.returnValue = false;
            return;
        }else if(window.event.srcElement.readOnly || window.event.srcElement.disabled){
            // readOnly나 disabled인 경우 작동하지 않도록
            window.event.returnValue = false;
            return;
        }
    }
    event.returnValue = true;
}


//  -->
</script>

<script language="javascript">

//var opener = window.dialogArguments;
var isModal = false;
if( window.dialogArguments != null) {
    isModal = true;
}


if( isModal || (window.opener) ) {
    document.getElementById("copyright").src = "../../portal/common/copyright_p.jsp";
    document.getElementById("popup_log").style.display="";
    document.getElementById("view_log").style.display="none";
}


</script>
