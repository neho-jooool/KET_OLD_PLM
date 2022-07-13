<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.content.*,wt.fc.*,wt.util.*,wt.part.WTPart,wt.part.WTPartMaster,wt.epm.EPMDocument,wt.epm.EPMDocumentMaster,wt.folder.*,wt.clients.folder.*,wt.query.*"%>
<%@page import = "e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
    private static EPMDocument getAssociatedModels(EPMDocument epm, WTPart part,String referenceType, int required) {
        if( (epm == null) || (part == null) ) {
            return null;
        }

        EPMDocument model = null;
        try {
            ArrayList arrymodels = EDMHelper.getAssociatedModels(epm, part,referenceType, required);
            for(int i = 0; i < arrymodels.size(); i++) {
                model = (EPMDocument)arrymodels.get(i);
            }
        }
        catch(WTException e) {
            Kogger.error(e);
        }
        return model;
    }
%>

<%

    EDMProperties edmProperties = EDMProperties.getInstance();
    //EDMAttributes epmAttributes = EDMAttributes.getInstance();

    boolean isNumbering = true;

    e3ps.common.content.fileuploader.FormUploader uploader = null;
    Hashtable param = null;

    String command = "";
    String oid = "";
    String outOid = "";

    String contentType = request.getContentType();
    if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 )
    {
        uploader = e3ps.common.content.fileuploader.FormUploader.newFormUploader(request);
        param = uploader.getFormParameters();
        command = (String)param.get("command");
        oid = (String)param.get("oid");
        outOid = (String)param.get("outOid");
    }
    else
    {
        command = request.getParameter("command");
        oid = request.getParameter("oid");
        outOid = request.getParameter("outOid");

    }

    if( (command == null) || (command.trim().length() == 0) ) { command = ""; }
    if( (oid == null) || (oid.trim().length() == 0) ) { oid = ""; }


    if("save".equals(command)) {

        String businessType = (param.get("businessType")==null)? "":(String)param.get("businessType");
        String manageType = (param.get("manageType")==null)? "":(String)param.get("manageType");
        String devStage = (param.get("devStage")==null)? "":(String)param.get("devStage");
        String category = (param.get("category")==null)? "":(String)param.get("category");
        String cadAppType = (param.get("cadAppType")==null)? "":(String)param.get("cadAppType");
        String project = (param.get("project")==null)? "":(String)param.get("project");
        String description = (param.get("description")==null)? "":(String)param.get("description");

        String number = (param.get("number")==null)? "":(String)param.get("number");
        String name = (param.get("name")==null)? "":(String)param.get("name");

        String repPoid = (param.get("repPoid")==null)? "":(String)param.get("repPoid");
        String repModelOid = (param.get("repModelOid")==null)? "":(String)param.get("repModelOid");

        String torelPoid[] = uploader.getFormParameters("torelPoid");
        String torelModelOid[] = uploader.getFormParameters("torelModelOid");

        String torelModelOidNonPart = (param.get("torelModelOidNonPart")==null)? "":(String)param.get("torelModelOidNonPart");


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




        //폴더 처리시 : 사업부/제품*금형/2D/도면유형
        CADCategory cadCat = CADCategory.toCADCategory(category);

        String EPMApplicationType = edmProperties.getAppTypeByPLM();//"WINDCHILL";

        boolean isWGM = false;
        if(oid.length() > 0) {
            try {
                ReferenceFactory rf = new ReferenceFactory();
                EPMDocument o = (EPMDocument)rf.getReference(oid).getObject();
                if(!edmProperties.isAppTypeByPLM(o.getOwnerApplication().toString())) { //if(!"WINDCHILL".equals(o.getOwnerApplication().toString())) {
                    isWGM = true;
                    EPMApplicationType = o.getOwnerApplication().toString();
                }
            }
            catch(Exception e) {
        	Kogger.error(e);
            }
        }
        location = edmProperties.getFullPath(businessType, manageType, cadCat.getDisplay(Locale.KOREA), isWGM);


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


        EPMDocument epm = null;

        try {
            if("ECAD_DRAWING".equals(category)) {
                epm = EDMServiceHelper.saveEPMDocumentByECAD(map);
            } else {
                epm = EDMServiceHelper.saveEPMDocument(map);
            }
            oid = epm.getPersistInfo().getObjectIdentifier().getStringValue();
        }
        catch(Exception e) {
            String errorMessage = e.getLocalizedMessage();
            oid = "";
%>
        <form name="errorForm"><input type=hidden name='command' value='error'><input type=hidden name='errorMessage' value='<%=errorMessage%>'></form>
        <script language="javascript" type="text/javascript" >
            alert('<%=messageService.getString("e3ps.message.ket_message", "02455") %><%--저장 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
            document.errorForm.method = "post";
            document.errorForm.action = "/plm/jsp/project/ProjectOutputDwgResultRegistry.jsp";
            document.errorForm.submit();
        </script>
<%
        }
        ReferenceFactory rf = new ReferenceFactory();
        ProjectOutput output = null;
        try {
            output = (ProjectOutput)rf.getReference(outOid).getObject();
            ProjectOutputHelper.manager.registryProjectOutput(output, epm);
            WTPrincipalReference wtref = null;
            WTPrincipal principal = SessionHelper.manager.getPrincipal();
            if( output.getOwner() == null ) {
                wtref = WTPrincipalReference.newWTPrincipalReference(principal);
            }
            else {
                WTPrincipalReference wr = output.getOwner();
                String oldname = wr.getName()==null?"":wr.getName();
                if( !oldname.equals(principal.getName()) ) {
                    wtref = WTPrincipalReference.newWTPrincipalReference(principal);
                }
            }

            if(wtref != null) {
                output.setOwner(wtref);
                output = (ProjectOutput)PersistenceHelper.manager.save(output);
            }
            Kogger.debug("ssssssssssssssssss==>"+output.getOutputName());
        }
        catch(Exception e) {
            Kogger.error(e);
        }


%>
        <script language="javascript" type="text/javascript" >
            alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');
            //document.location.href = "/Windchill/jsp/change/ecr/CreateECO.jsp?oid=<%=oid%>";
            if(opener != null) {
                opener.document.forms[0].submit();
                window.close();

            } else {
                document.location.href = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=<%=oid%>";
            }
        </script>
<%
    }

    String errorMessage = request.getParameter("errorMessage");
    if( (errorMessage == null) || (errorMessage.trim().length() == 0) ) {
        errorMessage = "";
    }

    EPMDocument epm = null;
    if(oid.trim().length() > 0) {
        try {
            ReferenceFactory rf = new ReferenceFactory();
            epm = (EPMDocument)rf.getReference(oid).getObject();
        }
        catch(Exception e) {
            Kogger.error(e);
        }
    }


    HashMap ibaValues = null;
    if(epm != null) {
        ibaValues = EDMAttributes.getIBAValues(epm,Locale.KOREAN);
    }


    String manageType = "";
    String category = "";
    String devStage = "";
    String cadAppType = "";
    String manufacturingVersion = "";

    manageType = request.getParameter("manageType");


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
    }

    if( (manageType == null) || (manageType.trim().length() == 0) ) {
        manageType = "PRODUCT_DRAWING";
    }

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

    if( (part != null) && edmProperties.isRefModel(category)) {
        model = getAssociatedModels(epm, part,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_STANDARD);
        /*
            ArrayList relatedModels = null;
            try {
                relatedModels = EDMHelper.getReferenceDocs(part,edmProperties.getReferenceTypeForModel(null),EDMHelper.REQUIRED_STANDARD);
                if( (relatedModels != null) && (relatedModels.size() > 0) ) {
                    EPMDocument model0 = null;
                    for(int i = 0; i < relatedModels.size(); i++) {
                        model0 = (EPMDocument)((Object[])relatedModels.get(i))[1];
                        ArrayList modelArr0 = EDMHelper.getReferenceEPMs(model0, -1);
                        for(int k = 0; k < modelArr0.size(); k++) {
                            Object[] moo = (Object[])modelArr0.get(k);
                            if( PersistenceHelper.isEquivalent(epm, (EPMDocument)moo[1]) ) {
                                model = model0;
                                break;
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        */
    }

    if(epm != null) {
        isNumbering = VersionHelper.isFirstVersion(epm);
    }

    boolean keyinfornumber = (isNumbering && edmProperties.isKeyInCatsToNrField(category));
    boolean keyinforname = (isNumbering && edmProperties.isKeyInCatsToNmField(category));


    EPMDocument ecadPCB = null;
    EPMDocument ecadSCH = null;
    EPMDocument ecadDWG = null;

    if("ECAD_DRAWING".equals(category)) {
        if( (epm != null) && (part != null) ) {
            ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
            for(int i = 0; i < ecads.size(); i++) {
                EPMDocument ecad = (EPMDocument)ecads.get(i);
                if("PADS".equals(ecad.getAuthoringApplication().toString())) {
                    ecadPCB = ecad;
                }
                else if("PADS_SCH".equals(ecad.getAuthoringApplication().toString())) {
                    ecadSCH = ecad;
                }
                else if("ACAD".equals(ecad.getAuthoringApplication().toString())) {
                    ecadDWG = ecad;
                }
            }
        }
    }
    //out.println("<br>category : " + category);

    //out.println("<br>isNumbering : " + isNumbering);
    //out.println("<br>keyinfornumber : " + keyinfornumber);
    //out.println("<br>keyinforname : " + keyinforname);

    //out.println("<br>keyinforname : " + edmProperties.isKeyInCatsToNrField(category));
    //out.println("<br>keyinforname : " + edmProperties.isKeyInCatsToNmField(category));
%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.project.beans.ProjectOutputHelper"%>
<%@page import="wt.org.WTPrincipalReference"%>
<%@page import="wt.org.WTPrincipal"%>
<%@page import="wt.session.SessionHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<SCRIPT language=JavaScript src="js/edm.js"></SCRIPT>
<SCRIPT language=JavaScript src="js/jquery-latest.min.js"></SCRIPT>
<!--
<SCRIPT language=JavaScript src="js/jquery-1.4.2.min.js"></SCRIPT>
 -->
<script type="text/Javascript">
<!--
var $jquery=jQuery.noConflict();


var maxLength_number;
var maxLength_name;
var maxLength_description;


maxLength_number=new Number(<%=edmProperties.getMaxLenForNumber()%>);
maxLength_name=new Number(<%=edmProperties.getMaxLenForName()%>);
maxLength_description=new Number(<%=edmProperties.getMaxLenForDescription()%>);


/*****************************************************************************************
    도면  시작 ...
 *****************************************************************************************/

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

//다품일도 여부 ...
var typeOnPartRef = new Array();
<%
    CADCategory catarr[] = CADCategory.getCADCategorySet();
    if(catarr != null) {
        for(int i = 0; i < catarr.length; i++) {
            if(edmProperties.isVariousPartRefs(catarr[i].toString())) {
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



function checkNonPart(_k) {
    for(var i = 0; i < noRelatedPartArr.length; i++) {
        if(_k == noRelatedPartArr[i]) {
            return true;
        }
    }
    return false;
}

function isVariousPartRefs(_k) {
    for(var i = 0; i < typeOnPartRef.length; i++) {
        if(_k == typeOnPartRef[i]) {
            return true;
        }
    }
    return false;
}

function checkTypeOnModelRef(_k) {
    for(var i = 0; i < typeOnModelRef.length; i++) {
        if(_k == typeOnModelRef[i]) {
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



/*****************************************************************************************
    맵핑 조건 처리 ...
 *****************************************************************************************/

function doChangeDevStage(iObj) {
    doDevStageFilter(iObj.value, document.forms[0].manageType.value);
}

function doDevStageFilter(_ds,_mt) {
    var param = "";
    param += "command=ChangeDevStage";
    param += "&devStage=" + _ds;
    param += "&manageType=" + _mt;

    //({id : this.getAttribute('id')}),

    var url = "/plm/jsp/edm/EDMAjaxAction.jsp";

    $jquery.ajax({
        type: "POST",
        url: "/plm/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" error (function : doDevStageFilter)");  },
        success: function(xml) {
            callbackChangeDevStageOptions(xml);
        }
    });
}

function callbackChangeDevStageOptions(xmlDoc) {

    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_display = showElements[0].getElementsByTagName("l_display");
    var l_value = showElements[0].getElementsByTagName("l_value");

    var catObject = document.getElementById("category");
    removeOptions(catObject);


    var seledFlag = false;
    if((l_value != null) && (l_value.length > 0)) {
        if(l_value.length == 1)  {
            addOptions(catObject,unescape(l_display[0].text),unescape(l_value[0].text),true);
        } else {
            addOptions(catObject,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
            for(var i = 0; i < l_value.length; i++) {
                addOptions(catObject,unescape(l_display[i].text),unescape(l_value[i].text),false);
            }
        }
    } else {
        addOptions(catObject,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
    }

    doChangeCategory(catObject);
}

function doChangeCategory(iObj) {
    /*
     * 유형에 따른 화면 처리.
     */

    //다품일도 유형 처리.
    goCheckedPartRef(iObj.value);

    //주의, 설명 내용.
    goRefDescripton(iObj.value);

    //무도번 여부 처리.
    goToRepPart(iObj.value);

    //도면 번호/명, 첨부파일
    goToDrawing(iObj.value);

    doCategoryFilter(iObj);
}

function goCheckedPartRef(_cat) {
    var relatedPartsDiv = document.getElementById("relatedPartsDiv");
    if(isVariousPartRefs(_cat)) {
        relatedPartsDiv.style.display="";
    } else {
        var rpst = document.getElementById('relatedPartsTbody');
        for(var i = rpst.rows.length; i > 0; i--) {
            rpst.deleteRow(i-1);
        }

        relatedPartsDiv.style.display="none";
    }
}

function goToRepPart(_cat) {

    var lObj = document.getElementById("torepPartDiv");

    var isNonPart = checkNonPart(_cat);
    if((_cat == '') || isNonPart) {
        lObj.style.display = "none";
    } else {
        lObj.style.display = "";
    }


    var modelNonPartDiv = document.getElementById("torelModelNonPartDiv");

    if(isNonPart && checkTypeOnModelRef(_cat)) {
        modelNonPartDiv.style.display="";
    }else{
        modelNonPartDiv.style.display="none";
        removeSeledItem('epm','torelmodel');
    }

    removeSeledItem('part','torep');

    /*

    subfix_item = "torep";

    var lDataArr = new Array();
    lDataArr[0] = '';//oid
    lDataArr[1] = '';//number
    lDataArr[2] = '';//name
    lDataArr[3] = '';//version

    insertPartInfo(lDataArr);


    epm_subfix_item = "torep";

    var subarr = new Array();
    subarr[0] = "";
    subarr[1] = "";
    subarr[2] = "";
    subarr[3] = "";

    insertEPMInfo(subarr);
    handleModelBtn(epm_subfix_item);
    */
}

function goToDrawing(_cat) {
    form = document.forms[0];

    form.number.value = "";
    form.name.value = "";

    if(checkTypeOnKeyInNums(_cat)) {
        form.number.readOnly = false;
        form.number.style.backgroundColor="";
    }else{
        form.number.readOnly = true;
        form.number.style.backgroundColor="#EFEFEF";
    }

    if(checkTypeOnKeyInNames(_cat)) {
        form.name.readOnly = false;
        form.name.style.backgroundColor="";
    }else{
        form.name.readOnly = true;
        form.name.style.backgroundColor="#EFEFEF";
    }


    var div_dwg = document.getElementById("toDwgDiv");
    if((_cat == '') || (_cat=='ECAD_DRAWING')) {
        div_dwg.style.display = "none";
    } else {
        div_dwg.style.display = "";
    }

    var div_ecad = document.getElementById("toEcadDiv");
    if(_cat=='ECAD_DRAWING') {
        div_ecad.style.display = "";

        form.number_pcb.value = "";
        form.name_pcb.value = "";

        form.number_sch.value = "";
        form.name_sch.value = "";

        form.number_dwg.value = "";
        form.name_dwg.value = "";

    } else {

        form.number_pcb.value = "";
        form.name_pcb.value = "";

        form.number_sch.value = "";
        form.name_sch.value = "";

        form.number_dwg.value = "";
        form.name_dwg.value = "";

        div_ecad.style.display = "none";
    }
}


/*
 * 참고 설명 ....
 */
function goRefDescripton(_c) {
    <%  if(catarr != null) {
            for(int i = 0; i < catarr.length; i++) {
    %>
                var descRef<%=i%> = document.getElementById("desc_<%=catarr[i].toString()%>");
                if( (descRef<%=i%> != null) && (descRef<%=i%> != "undefined") ) {
                    if("<%=catarr[i].toString()%>" == _c) {
                        descRef<%=i%>.style.display = "inline";
                    }else{
                        descRef<%=i%>.style.display = "none";
                    }
                }
    <%    }
        }
    %>
}


function doCategoryFilter(iObj) {

    if(iObj.value == "") {
        removeOptionsNDefault(document.getElementById("cadAppType"),true,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","");
        return;
    }

    var param = "";
    param += "command=ChangeCategory";
    param += "&category=" + iObj.value;

    var url = "/plm/jsp/edm/EDMAjaxAction.jsp";

    $jquery.post(url,param, function(xml) {
        changeAppTypeOptions(xml);
    });
}

function changeAppTypeOptions(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_display = showElements[0].getElementsByTagName("l_display");
    var l_value = showElements[0].getElementsByTagName("l_value");

    var catObject = document.getElementById("cadAppType");

    removeOptions(catObject);

    var seledFlag = false;
    if(l_value != null && l_value.length > 0) {
        if(l_value.length == 1)  {
            addOptions(catObject,unescape(l_display[0].text),unescape(l_value[0].text),true);
        } else {
            addOptions(catObject,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
            for(var i = 0; i < l_value.length; i++) {
                addOptions(catObject,unescape(l_display[i].text),unescape(l_value[i].text),false);
            }
        }
    } else {
        addOptions(catObject,"<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>","",true);
    }

    doChangeAppType(catObject);
}


/*****************************************************************************************
    도면 저장 시작 ...
 *****************************************************************************************/
function doCancel() {
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02439") %><%--작업을 취소하시겠습니까?--%>')) {
        return;
    }

    if(document.forms[0].oid.value != '') {
        document.location.href="/plm/jsp/edm/ViewEPMDocument.jsp?oid="+document.forms[0].oid.value;
    } else {
        if(opener) {
            self.close();
        } else {
            document.location.href="/plm/jsp/edm/SearchEPMDocument.jsp";
        }
    }
}

function doSave(){
    if(!checkInData()){
        return false;
    }

    validateData();
}

function doSaveAction() {

    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "02463") %><%--저장하시겠습니까?--%>')) {
        return;
    }



    form = document.forms[0];
    form.command.value = "save";

    form.target = "_self";
    form.method = "post";
    form.encoding = 'multipart/form-data';
    form.action = "/plm/jsp/project/ProjectOutputDwgResultRegistry.jsp";
    form.submit();
}

function checkInData(){
    var form = document.forms[0];


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
        alert('<%=messageService.getString("e3ps.message.ket_message", "01285") %><%--도면설명을 입력하시기 바랍니다--%>');
        form.description.focus();
        return false;
    } else if( (maxLength_description > 0) && lwGetByte(document.forms[0].description.value) > maxLength_description) {
//        alert("도면설명을 "+maxLength_description+" bytes 이하로 입력하시기 바랍니다.");
        alert('<%=messageService.getString("e3ps.message.ket_message", "01284") %><%--도면설명을 {0} bytes 이하로 입력하시기 바랍니다--%>');
        form.description.focus();
        return false;
    }


    if(!checkNonPart(form.category.value)) {
        if(form.repPoid.value=='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01246") %><%--대표부품을 입력하시기 바랍니다.--%>");
            return false;
        }
    }


    if(form.category.value != 'ECAD_DRAWING') {
        if(form.number.value=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01279") %><%--도면번호를 입력하시기 바랍니다.--%>');
            return false;
        }

        if(form.name.value=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01271") %><%--도면명을 입력하시기 바랍니다.--%>');
            return false;
        }

        <%if(epm == null){%>
        //첨부파일
        if(form.primary.value=='') {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02668") %><%--주 첨부파일을 입력하시기 바랍니다.--%>');
            form.primary.focus();
            return false;
        }
        <%}%>
    }else{

        if(form.number_pcb.value=='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00356") %><%--PCB 도면번호를 입력하시기 바랍니다--%>");
            return false;
        }

        if(form.name_pcb.value=='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00355") %><%--PCB 도면명을 입력하시기 바랍니다--%>");
            return false;
        }

        <%if(ecadPCB == null){%>
            //첨부파일
            if(form.file_pcb_primary.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00358") %><%--PCB 도면첨부 파일을 입력하시기 바랍니다--%>");
                form.file_pcb_primary.focus();
                return false;
            }
        <%}%>

        if(form.number_sch.value=='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00461") %><%--Schematic 도면번호를 입력하시기 바랍니다--%>");
            return false;
        }

        if(form.name_sch.value=='') {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00460") %><%--Schematic 도면명을 입력하시기 바랍니다--%>");
            return false;
        }

        <%if(ecadSCH == null){%>
            //첨부파일
            if(form.file_sch_primary.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00462") %><%--Schematic 도면첨부 파일을 입력하시기 바랍니다--%>");
                form.file_sch_primary.focus();
                return false;
            }
        <%}%>

        //AutoCAD 첨부파일이 있는 경우
        if(form.file_dwg_primary.value != '') {
            if(form.number_dwg.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00073") %><%--AutoCAD 도면번호를 입력하시기 바랍니다--%>");
                return false;
            }

            if(form.name_dwg.value=='') {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00072") %><%--AutoCAD 도면명을 입력하시기 바랍니다--%>");
                return false;
            }
        }

    }

    return true;
}

function validateData() {
    form = document.forms[0];
    var param = "";
    param += "command=validateSave";
    param += "&oid="+form.oid.value;
    param += "&number="+form.number.value;

    var url = "/plm/jsp/edm/EDMAjaxAction.jsp";

        $jquery.post(url,param, function(xml) {
            recValidateData(xml);
        });
}

function recValidateData(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_checkmsg = showElements[0].getElementsByTagName("l_checkmsg");
    var l_valid = showElements[0].getElementsByTagName("l_valid");

    lcheckmsg = unescape(l_checkmsg[0].text);
    lvalid = unescape(l_valid[0].text);

    if(lvalid == 'false') {
        alert(lcheckmsg);
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

    var param = "command=select";
    if(_subfix == 'torel') {
        param += "&mode=one";
        //param += "&mode=multi";
    }else{
        param += "&mode=one";
    }

    if(_subfix == 'torep') {
        subfix_rep_flag = 1;
    }else{
        subfix_rep_flag = 0;
    }

    param += "&invokeMethod=getSeledParts";

    url = "/plm/jsp/edm/edmSearchPart.jsp?"+param;
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=500,height=600');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function getSeledParts(_arr) {
    for(var i=0; i < _arr.length; i++){
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
//alert(_arr[0]);
    var param = "";
    param += "command=CheckPartReference";
    param += "&oid="+ document.forms[0].oid.value;
    param += "&poid=" + _arr[0];
    param += "&category=" + document.forms[0].category.value;
    param += "&required=" + subfix_rep_flag;


    var url = "/plm/jsp/edm/EDMAjaxAction.jsp";

    $jquery.ajax({
        type: "POST",
        url: "/plm/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "goPartFilter") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            callRecParts(xml);
        }
    });
}

function callRecParts(xmlDoc) {

    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_valid = showElements[0].getElementsByTagName("l_valid");


    var lvalid;
    if((l_valid != null) && (l_valid.length > 0)) {
        lvalid = unescape(l_valid[0].text);
    }

    if(lvalid == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03183") %><%--해당 부품은 이미 관련 연관을 가지고 있습니다--%>');
        return;
    }

    var loid = showElements[0].getElementsByTagName("l_oid");
    var lnumber = showElements[0].getElementsByTagName("l_number");
    var lname = showElements[0].getElementsByTagName("l_name");
    var lversion = showElements[0].getElementsByTagName("l_version");


    var lDataArr = new Array();
    lDataArr[0] = unescape(loid[0].text);//oid
    lDataArr[1] = unescape(lnumber[0].text);//number
    lDataArr[2] = unescape(lname[0].text);//name
    lDataArr[3] = unescape(lversion[0].text);//version

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

        setConvertNrNm(_arr[1],_arr[2]);

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

        var url = "/plm/jsp/edm/EDMAjaxAction.jsp";


        $jquery.ajax({
            type: "POST",
            url: "/plm/jsp/edm/EDMAjaxAction.jsp",
            data: param,
            async: false,
            error: function() {    alert("Failed to submit - ");  },
            success: function(xml) {
                recPartModelData(xml);
            }
        });

        /*

        $jquery.post(url,param, function(xml) {
            recPartModelData(xml);
        });
        */
    }
}


function recPartModelData(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");

    var subarr = new Array();
    if( (showElements != null) && (showElements.length > 0)) {

        var l_number = showElements[0].getElementsByTagName("l_number");
        var l_name = showElements[0].getElementsByTagName("l_name");
        var l_version = showElements[0].getElementsByTagName("l_version");
        var l_oid = showElements[0].getElementsByTagName("l_oid");

        if(l_oid != null && l_oid.length > 0) {
            for(var i = 0; i < l_oid.length; i++) {
                r_oid = unescape(l_oid[i].text);
                r_number = unescape(l_number[i].text);
                r_name = unescape(l_name[i].text);
                r_version = unescape(l_version[i].text);

                subarr[0] = r_oid;
                subarr[1] = r_number;
                subarr[2] = r_name;
                subarr[3] = r_version;
            }
        }

    } else {
        subarr[0] = "";
        subarr[1] = "";
        subarr[2] = "";
        subarr[3] = "";
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
    newTd.width = "90";
    newTd.innerHTML = "<span id='part_number_"+refkey+"'>"+iDataArr[1]+"</span><input type='hidden' name='pnumber' rowskey='"+refkey+"' datatype='torel' value='"+iDataArr[1]+"'><input type='hidden' name='torelPoid' rowskey='"+refkey+"' value='"+iDataArr[0]+"'>";

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.width = "130";
    newTd.innerHTML = "<span id='part_name_"+refkey+"'>"+iDataArr[2]+"</span>";

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
    lhtmlStr += "<td width='60' background=\"../../portal/images/btn_bg1.gif\" class='btn_blue'><a href='#' class='btn_blue' name='delBtn_part_"+id_subfix+"' onClick=\"javascript:removeSeledItem('part','"+id_subfix+"');\"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>";
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
    lhtmlStr += "<td class=\"btn_blue\" background=\"../../portal/images/btn_bg1.gif\"><a href=\"#\" onClick=\"javascript:doSeledModel('"+id_subfix+"');\" name='addBtn_epm_"+id_subfix+"' class=\"btn_blue\" ><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_2.gif\"></td>";
    lhtmlStr += "</tr>";
    lhtmlStr += "</table>";
    lhtmlStr += "</td>";
    lhtmlStr += "<td width=\"5\">&nbsp;</td>";
    lhtmlStr += "<td>";
    lhtmlStr += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
    lhtmlStr += "<tr>";
    lhtmlStr += "<td width=\"10\"><img src=\"../../portal/images/btn_1.gif\"></td>";
    lhtmlStr += "<td class=\"btn_blue\" background=\"../../portal/images/btn_bg1.gif\"><a href=\"#\" onClick=\"javascript:removeSeledItem('epm','"+id_subfix+"');\" name='delBtn_epm_"+id_subfix+"' class=\"btn_blue\"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>";
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
            var rpst = document.getElementById('relatedPartsTbody');
            for(var i = rpst.rows.length; i > 0; i--) {
                if(rpst.rows(i-1).id == _subid) {
                    rpst.deleteRow(i-1);
                    break;
                }
            }

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

function doSeledModel(_subfix) {
    epm_subfix_item = _subfix;

    url = "/plm/jsp/edm/edmSearchEPM.jsp?command=select&mode=one&invokeMethod=getSeledEPMs&isModel=true";
    newWinSPart=window.open("",'','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100,width=500,height=600');
    newWinSPart.focus();
    newWinSPart.location.href = url;
}

function getSeledEPMs(_arr) {
    for(var i=0; i < _arr.length; i++){
        if(epm_subfix_item == 'wgmmodel') {
            document.forms[0].number.value = _arr[i][1];
            document.forms[0].name.value = _arr[i][2];
        }

        goModelFilter(_arr[i]);
        //insertEPMInfo(_arr[i]);
        //handleModelBtn(epm_subfix_item);
    }
}


function goModelFilter(_arr) {

    var lpoid = '';
    if(epm_subfix_item == 'torep') {
        lpoid = document.forms[0].repPoid.value;
    } else if(epm_subfix_item == 'torelmodel') {

    } else {
        var ltorelPoids = document.getElementsByName("torelPoid");
        for(var i = 0; i < ltorelPoids.length; i++) {
            if(epm_subfix_item == ltorelPoids[i].rowskey) {
                lpoid = ltorelPoids[i].value;
                break;
            }
        }
    }

    var param = "";
    param += "command=CheckModelReference";
    param += "&oid=" + document.forms[0].oid.value;
    param += "&poid=" + lpoid;
    param += "&modelOid=" + _arr[0];
    param += "&category=" + document.forms[0].category.value;
    param += "&cadAppType=" + document.forms[0].cadAppType.value;

    $jquery.ajax({
        type: "POST",
        url: "/plm/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "goModelFilter") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            callRecModel(xml);
        }
    });
}

function callRecModel(xmlDoc) {

    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_valid = showElements[0].getElementsByTagName("l_valid");
    var l_ReferencedBy = showElements[0].getElementsByTagName("l_ReferencedBy");


    var lvalid;
    var lReferencedBy;
    if((l_valid != null) && (l_valid.length > 0)) { lvalid = unescape(l_valid[0].text);  }
    if((l_ReferencedBy != null) && (l_ReferencedBy.length > 0)) { lReferencedBy = unescape(l_ReferencedBy[0].text); }

    if( (lvalid == 'false') && (lReferencedBy == 'false') ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03180") %><%--해당 모델은 이미 관련 연관을 가지고 있습니다--%>');
        return;
    }

    var loid = showElements[0].getElementsByTagName("l_oid");
    var lnumber = showElements[0].getElementsByTagName("l_number");
    var lname = showElements[0].getElementsByTagName("l_name");
    var lversion = showElements[0].getElementsByTagName("l_version");
    //file
    var lfileName = showElements[0].getElementsByTagName("l_fileName");
    var lfileSize = showElements[0].getElementsByTagName("l_fileSize");
    var lappDataOid = showElements[0].getElementsByTagName("l_appDataOid");


    var lDataArr = new Array();
    lDataArr[0] = unescape(loid[0].text);//oid
    lDataArr[1] = unescape(lnumber[0].text);//number
    lDataArr[2] = unescape(lname[0].text);//name
    lDataArr[3] = unescape(lversion[0].text);//version

    lDataArr[4] = unescape(lfileName[0].text);//version
    lDataArr[5] = unescape(lfileSize[0].text);//version
    lDataArr[6] = unescape(lappDataOid[0].text);//version

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
        document.getElementById("primaryFileURL").innerHTML="<a href='#'>"+_arr[4]+_arr[5]+"</a>";


    } else {
        var torelModels = document.getElementsByName("torelModelOid");
        for(var i=0; i<torelModels.length; i++) {
            if(torelModels[i].rowskey == subfix0) {
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

    if("<%=String.valueOf(isNumbering)%>" == "false") {
        return;
    }

    <%if( (epm != null) && edmProperties.isKeyInCatsToNrField(category)){%>return;<%}%>


    if(checkTypeOnKeyInNums(form.category.value)) {
        form.number.value = _partnr;
        form.name.value = _partnm;
    }else{
        if(document.forms[0].cadAppType.value != 'UG') {
            goNumbering(_partnr, _partnm);
        }
    }
}

function goNumbering(_partnr,_partnm) {
    var param = "";
    param += "command=numbering";
    param += "&number="+_partnr;
    param += "&name="+_partnm;
    param += "&category=" + document.forms[0].category.value;
    param += "&cadAppType=" + document.forms[0].cadAppType.value;

    $jquery.ajax({
        type: "POST",
        url: "/plm/jsp/edm/EDMAjaxAction.jsp",
        data: param,
        async: false,
        error: function() {    alert(" <%=messageService.getString("e3ps.message.ket_message", "00952", "goNumbering") %><%--관리자에게 문의하시기 바랍니다(function : {0})--%>");  },
        success: function(xml) {
            setNumbering(xml);
        }
    });
}

function setNumbering(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>");
        return;
    }

    var showElements = xmlDoc.selectNodes("//data_info");
    var l_number = showElements[0].getElementsByTagName("l_number");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_valid = showElements[0].getElementsByTagName("l_valid");

    if(unescape(l_valid[0].text) == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02520") %><%--정의되지 않은 부품 번호 입니다\n확인 후 진행하시기 바랍니다--%>');
        document.forms[0].number.value = "";
        document.forms[0].name.value = "";
        return;
    }

    form = document.forms[0];
    if(form.category.value=='ECAD_DRAWING'){
        form.number_pcb.value = unescape(l_number[0].text)+"_PCB";
        form.name_pcb.value = unescape(l_name[0].text);
        form.number_sch.value = unescape(l_number[0].text)+"_SCH";
        form.name_sch.value = unescape(l_name[0].text);
        form.number_dwg.value = unescape(l_number[0].text)+"_DWG";
        form.name_dwg.value = unescape(l_name[0].text);
    }else{
        form.number.value = unescape(l_number[0].text);
        form.name.value = unescape(l_name[0].text);
    }
}
// -->
</script>
</head>

<body>
<form>
<!-- hidden -->
<input type='hidden' name='command' value=''>

<input type='hidden' name='oid' value='<%=oid%>'>
<!-- ECAD -->
<input type='hidden' name='oid_pcb' value='<%=(ecadPCB==null)? "":PersistenceHelper.getObjectIdentifier(ecadPCB).getStringValue()%>'>
<input type='hidden' name='oid_sch' value='<%=(ecadSCH==null)? "":PersistenceHelper.getObjectIdentifier(ecadSCH).getStringValue()%>'>
<input type='hidden' name='oid_dwg' value='<%=(ecadDWG==null)? "":PersistenceHelper.getObjectIdentifier(ecadDWG).getStringValue()%>'>

<input type='hidden' name='repPoid' value='<%=(part==null)? "":PersistenceHelper.getObjectIdentifier(part).getStringValue()%>'>
<input type='hidden' name='repModelOid' value='<%=(model==null)? "":PersistenceHelper.getObjectIdentifier(model).getStringValue()%>'>

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
<input type='hidden' name='outOid' value='<%=outOid%>'>
<!-- hidden end -->

<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="780" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                <td class="font_01"><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%>&nbsp;<%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
                                <td align="right"><img src="../../portal/images/icon_navi.gif">Home<img src="../../portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="../../portal/images/icon_navi.gif"><%=CADManageType.toCADManageType(manageType).getDisplay(messageService.getLocale())%><%if(epm == null){%><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}%></td>
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
            <table width="780" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:doSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:doCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
            </table>
            <table width="780" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%><span class="red">*</span></td>
                    <td colspan="3" class="tdwhiteL0">
                        <select name="businessType" class="fm_jmp" style="width:250">
                            <OPTION VALUE="자동차사업부"><%=messageService.getString("e3ps.message.ket_message", "02401") %><%--자동차사업부--%></OPTION>
                            <OPTION VALUE="전자사업부"><%=messageService.getString("e3ps.message.ket_message", "02483") %><%--전자사업부--%></OPTION>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%><span class="red">*</span></td>
                    <td width="290" class="tdwhiteL">
                        <select name="devStage" class="fm_jmp" style="width:250">
                            <%if(epm == null){%><OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION><%}%>
                            <%
                                DevStage dss[] = edmProperties.getDevStageSet(manageType);
                                for(int i = 0; i < dss.length; i++) {
                                    DevStage ds = dss[i];
                                    if( epm != null ) {
                                        if( devStage.equals("DEV_REVIEW_STAGE") ) {
                                            if(!"DEV_REVIEW_STAGE".equals(ds.toString())) { continue; }
                                        }else{
                                            if("DEV_REVIEW_STAGE".equals(ds.toString())) { continue; }
                                        }
                                    }
                            %>
                                    <OPTION VALUE="<%=ds.toString()%>" <%if(devStage.equals(ds.toString())){%>selected<%}%>><%=ds.getDisplay(messageService.getLocale())%></OPTION>
                            <%  }
                            %>
                        </select>
                    </td>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%><span class="red">*</span></td>
                    <td width="290" class="tdwhiteL0">
                        <select name="category" class="fm_jmp" style="width:250">
                        <%  if(epm == null) {
                                ArrayList catArr = edmProperties.getCADCatsSet(manageType,devStage);
                        %>
                                <%  if( catArr.size() > 1 ) { %><OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION><% } %>
                                <%  if(catArr.size() > 0) {
                                        for(int i = 0; i < catArr.size(); i++) {
                                            CADCategory cc = (CADCategory)catArr.get(i);
                                %>
                                            <OPTION VALUE="<%=cc.toString()%>" <%if(category.equals(cc.toString())){%>selected<%}%>><%=cc.getDisplay(messageService.getLocale())%></OPTION>
                                <%    }
                                    }
                                %>
                        <%  } else {
                                CADCategory cc = CADCategory.toCADCategory(category);
                        %>
                                <OPTION VALUE="<%=cc.toString()%>" selected><%=cc.getDisplay(messageService.getLocale())%></OPTION>
                        <%  } %>
                        </select>
                    </td>
                </tr>
                <!-- 수정 시 도면버전/양산버전 -->
                <%  if(epm != null) {
                        String mVersion = null;
                        if(ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION) != null) {
                            mVersion = (String)ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION);
                        }
                %>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01274") %><%--도면버전--%></td>
                            <td width="290" <%if(mVersion==null){%>class="tdwhiteL0" colspan="3"<%}else{%>class="tdwhiteL"<%}%>><%=epm.getVersionIdentifier().getSeries().getValue()%>.<%=epm.getIterationInfo().getIdentifier().getSeries().getValue()%></td>
                            <%if(mVersion != null){%><td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02095") %><%--양산도면버전--%></td><td width="290" class="tdwhiteL0"><%=mVersion%></td><%}%>
                        </tr>
                <%  } %>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%><span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <select id="cadAppType" name="cadAppType" class="fm_jmp" style="width:250">
                        <%  if(epm == null) { %>
                                <%
                                    CADAppType cats[] = null;
                                    if(category.length() > 0) { cats = edmProperties.getCADAppTypeSet(category); }

                                    if((cats == null) || (cats.length > 1)) { %><OPTION VALUE=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></OPTION><% }

                                    if(cats != null) {
                                        for(int i = 0; i < cats.length; i++) {
                                            CADAppType cat = cats[i];
                                %>
                                            <OPTION VALUE="<%=cat.toString()%>" <%if(cadAppType.equals(cat.toString())){%>selected<%}%>><%=cat.getDisplay(messageService.getLocale())%></OPTION>
                                <%    }
                                    }
                            } else {
                                CADAppType cat = CADAppType.toCADAppType(cadAppType);
                        %>
                                <OPTION VALUE="<%=cat.toString()%>" selected><%=cat.getDisplay(messageService.getLocale())%></OPTION>
                        <%  } %>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL0"><input name="project" type="text" class="txt_field" id="textfield4"  style="width:200" value="10001-01">
                        &nbsp;<a href="#"><img src="../../portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="#"><img src="../../portal/images/icon_delete.gif" border="0"></a></td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01283") %><%--도면설명--%></td>
                    <td colspan="3" class="tdwhiteL0" style="height:50">
                        <textarea name="description" class="txt_field" id="description" style="width:100%; height:96%"><%if(epm != null){%><%=(epm.getDescription()==null)? "":epm.getDescription()%><%}%></textarea>
                    </td>
                </tr>
                <!-- 수정 시 작성자/상태 정보 -->
                <%  if(epm != null) { %>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                            <td width="290" class="tdwhiteL"><%=epm.getCreatorFullName()%></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                            <td width="290" class="tdwhiteL0">???</td>
                        </tr>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                            <td width="290" class="tdwhiteL"><%=epm.getCreateTimestamp().toString()%></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
                            <td width="290" class="tdwhiteL0"><%=epm.getState().getState().getDisplay(messageService.getLocale())%></td>
                        </tr>
                <%  } %>

                <%
                    boolean fileboolean = false;
                    if(!"ECAD_DRAWING".equals(category) && (epm != null) ) {
                        fileboolean = true;
                    }
                %>
                <tbody id="toDwgDiv" style="display:<%=(fileboolean==false)? "none":""%>;">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%><span class="red">*</span></td>
                    <td class="tdwhiteL">
                        <input type="text" name="number" class="txt_field"  style="width:250" id="number" value="<%if(epm != null){%><%=(epm.getNumber()==null)? "":epm.getNumber()%><%}%>" <%if(!keyinfornumber){%>readonly<%}%>>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%><span class="red">*</span></td>
                    <td class="tdwhiteL0">
                        <input name="name" type="text" class="txt_field" id="name"  style="width:250" value="<%if(epm != null){%><%=(epm.getName()==null)? "":epm.getName()%><%}%>" <%if(!keyinforname){%>readonly<%}%>>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02666") %><%--주 도면 첨부--%><span class="red">*</span></td>
                    <td colspan="3" class="tdwhiteL0">
                        <table id="ug_model_search_btn" border="0" cellspacing="0" cellpadding="0" style="display:none;">
                            <tr>
                                <td width="100">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:doSeledModel('wgmmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="560"><span id='primaryFileURL'></span></td>
                            </tr>
                        </table>
                        <div id="primary_file_attach_div" style="margin:0;padding:0;border:0;display:;">
                            <jsp:include page="/jsp/edm/AttachFile.jsp" flush="false">
                                <jsp:param name="oid" value="<%=(oid==null)?"":oid%>"/>
                                <jsp:param name="isPrimary" value="<%=String.valueOf(true)%>"/>
                                <jsp:param name="primaryName" value="primary"/>
                                <jsp:param name="width" value="660"/>
                            </jsp:include>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01537") %><%--부도면 첨부--%></td>
                    <td colspan="3" class="tdwhiteM0">
                        <jsp:include page="/jsp/edm/AttachFile.jsp" flush="false">
                            <jsp:param name="oid" value="<%=(oid==null)?"":oid%>"/>
                            <jsp:param name="isPrimary" value="<%=String.valueOf(false)%>"/>
                            <jsp:param name="secondaryName" value="secondary"/>
                            <jsp:param name="delFileName" value="delFile"/>
                            <jsp:param name="width" value="660"/>
                        </jsp:include>
                    </td>
                </tr>
                </tbody>

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
                <tbody id="torelModelNonPartDiv" style="display:<%if( !(edmProperties.isRefModel(category) && edmProperties.isNonPart(category)) ){%>none<%}%>;">
                <tr><!-- hidden --><input type=hidden name='torelModelOidNonPart' value=''>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00902") %><%--관련 3D모델--%></td>
                    <td colspan="3" class="tdwhiteM0">
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="660" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="170" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                                <td width="300" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00053") %><%--3D 모델명--%></td>
                                <td width="50" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></td>
                            </tr>
                            <tr>
                                <td width="170" class="tdwhiteM"><a href="#"><span id='epm_number_torelmodel'><%=(refModel==null)? "&nbsp;":refModel.getNumber()%></span></a></td>
                                <td width="300" class="tdwhiteM"><p><span id='epm_name_torelmodel'><%=(refModel==null)? "&nbsp;":refModel.getName()%></span></p></td>
                                <td width="50" class="tdwhiteM"><span id='epm_version_torelmodel'><%=(refModel==null)? "&nbsp;":refModel.getVersionIdentifier().getSeries().getValue()%></span></td>
                                <td width="140" class="tdwhiteM">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="addBtn_epm_torelmodel" href="#"  onClick="javascript:doSeledModel('torelmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="5">&nbsp;</td>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="delBtn_epm_torelmodel" href="#" onClick="javascript:removeSeledItem('epm','torelmodel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <!-- 개발검토도면 -->
                                    <div id="desc_DEV_REVIEW_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"DEV_REVIEW_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "00619") %><%--개발검토 도면의 "도면번호"와 "도면명"은 직접 입력해야 합니다--%><br>
                                        &nbsp;&nbsp;&nbsp;&nbsp;- <%=messageService.getString("e3ps.message.ket_message", "01276") %><%--도면번호,도면명: 개발검토프로젝트번호 + 부품명--%>
                                        </font>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>

                <!-- 대표부품/3D 모델 시작 -->
                <tbody id="torepPartDiv" style="display:<%if((category.length() == 0) || edmProperties.isNonPart(category)){%>none<%}%>;">
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01242") %><%--대표부품--%><span class="red">*<br></span><%=messageService.getString("e3ps.message.ket_message", "00051") %><%--3D 모델--%></td>
                    <td colspan="3" class="tdwhiteM0">
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="660" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="100" class="tdgrayM">
                                <%  if(isNumbering) {%>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td width="60" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a id="addBtn_part_torep" href="#" onClick="javascript:doSeledPart('torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품 검색--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                <%  } else {%>&nbsp;<% } %>
                                </td>
                                <td width="120" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00052") %><%--3D 모델 번호--%></td>
                                <td width="40" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                                <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01370") %><%--모델검색/삭제--%></td>
                            </tr>
                            <tr>
                                <td width="90" class="tdwhiteM"><a href="#"><span id='part_number_torep'><%=(part==null)? "":part.getNumber()%></span></a><input type='hidden' name='pnumber' datatype='torep' value='<%=(part==null)? "":part.getNumber()%>'></td>
                                <td width="130" class="tdwhiteM"><p><span id='part_name_torep'><%=(part==null)? "":part.getName()%></span></p></td>
                                <td width="40" class="tdwhiteM"><span id='part_version_torep'><%=(part==null)? "":part.getVersionIdentifier().getSeries().getValue()%></span></td>
                                <td width="100" class="tdwhiteM">
                                <%  if(isNumbering) {%>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td width="60" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a id="delBtn_part_torep" href="#" onClick="javascript:removeSeledItem('part','torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                <%  } else {%>&nbsp;<% } %>
                                </td>
                                <td width="120" class="tdwhiteM"><a href="#"><span id='epm_number_torep'><%=(model==null)? "":model.getNumber()%></span></a></td>
                                <td width="40" class="tdwhiteM"><span id='epm_version_torep'><%=(model==null)? "":model.getVersionIdentifier().getSeries().getValue()%></span></td>
                                <td width="140" class="tdwhiteM">
                                    <div id="btn_table_epm_torep" style="display:<%if((category.length() == 0) || !edmProperties.isRefModel(category)){%>none<%}%>;">
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="addBtn_epm_torep" href="#" onClick="javascript:doSeledModel('torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="5">&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="delBtn_epm_torep" href="#" onClick="javascript:removeSeledItem('epm','torep');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <!-- 제폼도면 -->
                                    <div id="desc_PRODUCT_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRODUCT_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01189", "<b>", "</b>") %><%--다품일도의 부품들은 {0}대표부품{1}을 먼저 등록합니다. (주의 : 대표부품 품번 기준으로 도면번호가 채번됨)--%><br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02869", "<b>", "</b>") %><%--추가로 등록되는 부품을 {0}관련부품{1} 항목에 등록합니다.--%>&nbsp;&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "02862") %><%--추가 관련부품의 도면은 대표도번으로 검색해야 함--%>)
                                        </font>
                                    </div>
                                    <!-- 승인도면 -->
                                    <div id="desc_APPROVAL_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"APPROVAL_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01995") %><%--승인도면은 CATIA, UG 도면만 등록할 수 있습니다--%><br>
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
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01188", "<b>", "</b>") %><%--다품일도의 부품들은 {0}대표부품{1}을 먼저 등록합니다.--%><br>
                                        &nbsp;&nbsp;&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "02680") %><%--주의 : 대표부품 품번 기준으로 도면번호가 채번됨 일품일도인 경우, 관련 부품 지정이 불가능함--%>)<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02869") %><%--추가로 등록되는 부품을 관련부품 항목에 등록합니다.--%><br>
                                        &nbsp;&nbsp;&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "02862") %><%--추가 관련부품의 도면은 대표도번으로 검색해야 함--%>)
                                        </font>
                                    </div>
                                    <!-- 사출금형도면 -->
                                    <div id="desc_INJECTION_MOLD_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"INJECTION_MOLD_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01188", "<b>", "</b>") %><%--다품일도의 부품들은 {0}대표부품{1}을 먼저 등록합니다.--%><br>
                                        &nbsp;&nbsp;&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "02680") %><%--주의 : 대표부품 품번 기준으로 도면번호가 채번됨 일품일도인 경우, 관련 부품 지정이 불가능함--%>)<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02869") %><%--추가로 등록되는 부품을 관련부품 항목에 등록합니다.--%><br>
                                        &nbsp;&nbsp;&nbsp;(<%=messageService.getString("e3ps.message.ket_message", "02862") %><%--추가 관련부품의 도면은 대표도번으로 검색해야 함--%>)
                                        </font>
                                    </div>
                                    <!-- 사출금형SET도면 -->
                                    <div id="desc_INJECTION_MOLD_SET_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"INJECTION_MOLD_SET_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01022") %><%--금형 셋트 관련도면은 금형셋트(Die)의 참조 도면으로 관리됩니다--%>.<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02764") %><%--참조되는 3D모델은 없습니다(비활성)--%>
                                        </font>
                                    </div>
                                    <!-- 프레스금형SET도면 -->
                                    <div id="desc_PRESS_MOLD_SET_DRAWING" style="border:0;padding:0;margin:0;display:<%if(!"PRESS_MOLD_SET_DRAWING".equals(category)){%>none<%}%>;"><br>
                                        <font color="black">
                                        1. <%=messageService.getString("e3ps.message.ket_message", "01022") %><%--금형 셋트 관련도면은 금형셋트(Die)의 참조 도면으로 관리됩니다--%>.<br>
                                        2. <%=messageService.getString("e3ps.message.ket_message", "02764") %><%--참조되는 3D모델은 없습니다(비활성)--%>
                                        </font>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>



                <!-- 관련 부품 / 3D 모델 시작 -->


                <tbody id="relatedPartsDiv" style="display:<%if((category.length() == 0) || edmProperties.isNonPart(category) || !edmProperties.isVariousPartRefs(category)){%>none<%}%>;" >
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%><span class="red"><br></span><%=messageService.getString("e3ps.message.ket_message", "00051") %><%--3D 모델--%></td>
                    <td colspan="3" class="tdwhiteM0">
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="660" border="0" cellspacing="0" cellpadding="0" >
                            <tr>
                                <td>&nbsp;</td>
                                <td align="right">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="../../portal/images/btn_3.gif"></td>
                                            <td class="btn_blue" background="../../portal/images/btn_bg2.gif"><a href="#" onClick="javascript:doSeledPart('torel');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00936") %><%--관련부품추가--%></a></td>
                                            <td width="10"><img src="../../portal/images/btn_4.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="660">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table width="660" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                <td width="130" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
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
                                                relatedModel0 = getAssociatedModels(epm, relatedPart0,edmProperties.getReferenceType(category), EDMHelper.REQUIRED_RELATED);
                                            }

                                            refkey = refkey+1;
                            %>
                                            <tr id="<%=refkey%>">
                                                <td width="90" class="tdwhiteM">
                                                    <span id='part_number_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getNumber()%></span>
                                                    <input type='hidden' name='pnumber' rowskey='<%=refkey%>' datatype='torel' value='<%=(relatedPart0==null)? "":relatedPart0.getNumber()%>'>
                                                    <input type='hidden' name='torelPoid' rowskey='<%=refkey%>' value='<%=(relatedPart0==null)? "":PersistenceHelper.getObjectIdentifier(relatedPart0).getStringValue()%>'>
                                                </td>
                                                <td width="130" class="tdwhiteM"><span id='part_name_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getName()%></span></td>
                                                <td width="40" class="tdwhiteM"><span id='part_version_<%=refkey%>'><%=(relatedPart0==null)? "&nbsp;":relatedPart0.getVersionIdentifier().getSeries().getValue()%></span></td>
                                                <td width="100" class="tdwhiteM">
                                                    <div id='btn_table_part_<%=refkey%>'>
                                                        <table border='0' cellspacing='0' cellpadding='0'>
                                                            <tr>
                                                                <td width='10'><img src="../../portal/images/btn_1.gif"></td>
                                                                <td width='60' background="../../portal/images/btn_bg1.gif" class='btn_blue'><a href='#' onClick="javascript:removeSeledItem('part','<%=refkey%>');" class='btn_blue' name='delBtn_part_<%=refkey%>' ><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                                <td width='10'><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                                <td width="120" class="tdwhiteM">
                                                    <span id='epm_number_<%=refkey%>'><%=(relatedModel0==null)? "&nbsp;":relatedModel0.getNumber()%></span>
                                                    <input type='hidden' name='torelModelOid' rowskey='<%=refkey%>' value='<%=(relatedModel0==null)? "":PersistenceHelper.getObjectIdentifier(relatedModel0).getStringValue()%>'>
                                                </td>
                                                <td width="40" class="tdwhiteM"><span id='epm_version_<%=refkey%>'><%=(relatedModel0==null)? "&nbsp;":relatedModel0.getVersionIdentifier().getSeries().getValue()%></span></td>
                                                <td width="140" class="tdwhiteM">
                                                    <div id='btn_table_epm_<%=refkey%>' style="display:<%if((category.length() == 0) || !edmProperties.isRefModel(category)){%>none<%}%>;">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td>
                                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:doSeledModel('<%=refkey%>');" name='addBtn_epm_<%=refkey%>' class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "01369") %><%--모델검색--%></a></td>
                                                                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <td width="5">&nbsp;</td>
                                                                <td>
                                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="#" onClick="javascript:removeSeledItem('epm','<%=refkey%>');" name='delBtn_epm_<%=refkey%>' class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
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
                        <table border="0" cellspacing="0" cellpadding="0" width="660">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>

                <!-- ECAD 시작 -->
                <tbody id="toEcadDiv" style="display:<%if(!"ECAD_DRAWING".equals(category)){%>none<%}%>;">
                <tr id="toEcadDiv_pcb" style="display:;">
                    <td colspan="4" class="tdwhiteM0">
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="750" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="140" class="tdgrayL"><%=messageService.getString("e3ps.message.ket_message", "00177", "<br>") %><%--PCB{0}도면번호--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="text" name="number_pcb" value="<%=(ecadPCB==null)? "":ecadPCB.getNumber()%>" class="txt_field"  style="width:170" id="number_pcb" readonly></td>
                                            <td width="52">
                                            <!--
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td background="../../portal/images/btn_bg1.gif"><a href="#" class="btn_blue">수정</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                             -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="130" class="tdgrayL">PCB <%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL"><input type="text" name="name_pcb" value="<%=(ecadPCB==null)? "":ecadPCB.getName()%>" class="txt_field"  style="width:220" id="name_pcb" readonly></td>
                            </tr>
                            <tr>
                                <td width="140" class="tdgrayL">PCB <%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%><span class="red">*</span></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td>
                                                <input type="file" name="file_pcb_primary" class="txt_field"  style="width:590" id="file_pcb_primary">
                                            </td>
                                            <!--
                                            <td><input type="text" name="file_pcb_primary" class="txt_field"  style="width:470" id="file_pcb_primary"></td>
                                            <td width="120">
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td width="90" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a href="#" class="btn_blue">도면검색(PC)</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                             -->
                                        </tr>
                                    </table>
                                    <%  if(ecadPCB != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="590">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="590">
                                                <tr>
                                                    <td>
                                                        <div id="pcb_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadPCB);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    ApplicationData appData = (ApplicationData) item;
                                                            %>
                                                                    <a href="#">
                                                                    <%  if(holder instanceof wt.epm.EPMDocument) { %>
                                                                            <%=wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument)holder, item)%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } else { %>
                                                                            <%=appData.getFileName()%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } %>
                                                                    </a>
                                                            <%  }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                    <%  } %>
                                </td>
                            </tr>
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
                                }

                            %>
                            <tr>
                                <td width="140" class="tdgrayL"><%=messageService.getString("e3ps.message.ket_message", "00245") %><%--Gerber데이터첨부--%></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_gerber_primary" class="txt_field"  style="width:590" id="file_gerber_primary"></td>
                                            <!--
                                            <td><input type="text" name="file_gerber_primary" class="txt_field"  style="width:470" id="file_gerber_primary"></td>
                                            <td width="120">
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td width="90" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a href="#" class="btn_blue">검색(PC)</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                             -->
                                        </tr>
                                    </table>
                                    <%  if(gerberData != null) {
                                            String appDataOid = PersistenceHelper.getObjectIdentifier(gerberData).getStringValue();
                                            long appDataId = PersistenceHelper.getObjectIdentifier(gerberData).getId();
                                    %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="590">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table width="590" cellpadding="0" cellspacing="0" class="table_border" style="margin-bottom:3px;padding:2px;">
                                                <tr>
                                                    <td width="50" class="tdwhiteM0">
                                                        <div id="gerber_delete_<%=appDataId%>" style="display:;">
                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td width="10"><img src="../../portal/images/btn_3.gif"></td>
                                                                    <td class="btn_blue" background="../../portal/images/btn_bg2.gif"><a href="#" onClick="javascript:goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','true','gerber_delete_<%=appDataId%>','gerber_cancel_<%=appDataId%>','gerberDelFile');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></font></a></td>
                                                                    <td width="10"><img src="../../portal/images/btn_4.gif"></td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                        <div id="gerber_cancel_<%=appDataId%>" style="display:none;">
                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td width="10"><img src="../../portal/images/btn_3.gif"></td>
                                                                    <td class="btn_blue" background="../../portal/images/btn_bg2.gif"><a href="#" onClick="javascript:goFileDeleteMarking('<%=appDataOid%>','gerber_<%=appDataId%>','false','gerber_cancel_<%=appDataId%>','gerber_delete_<%=appDataId%>','gerberDelFile');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></font></a></td>
                                                                    <td width="10"><img src="../../portal/images/btn_4.gif"></td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    </td>
                                                    <td width="540" class="tdwhiteL0">
                                                        <div id="gerber_<%=appDataId%>" style="text-decoration:none;">
                                                            <a href="#"><%=gerberData.getFileName()%>&nbsp;(<%=gerberData.getFileSizeKB() + " KB"%>)</a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                    <%  } %>
                                </td>
                            </tr>
                        </table>
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr id="toEcadDiv_sch" style="display:;">
                    <td colspan="4" class="tdwhiteM0">
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="750" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="140" class="tdgrayL"><%=messageService.getString("e3ps.message.ket_message", "00467", "<br>") %><%--Schematic{0} 도면번호--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="text" name="number_sch" value="<%=(ecadSCH==null)? "":ecadSCH.getNumber()%>" class="txt_field"  style="width:170" id="number_sch" readonly></td>
                                            <td width="52">
                                            <!--
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td background="../../portal/images/btn_bg1.gif"><a href="#" class="btn_blue">수정</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="130" class="tdgrayL"><%=messageService.getString("e3ps.message.ket_message", "00459") %><%--Schematic 도면명--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL"><input type="text" name="name_sch" value="<%=(ecadSCH==null)? "":ecadSCH.getName()%>" class="txt_field"  style="width:220" id="name_sch" readonly></td>
                            </tr>
                            <tr>
                                <td width="140" class="tdgrayL"><%=messageService.getString("e3ps.message.ket_message", "00463", "<br>") %><%--Schematic{0}도면첨부--%><span class="red">*</span></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_sch_primary" class="txt_field"  style="width:590" id="file_sch_primary"></td>
                                            <!--
                                            <td><input type="text" name="file_sch_primary" class="txt_field"  style="width:470" id="file_sch_primary"></td>
                                            <td width="120">
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td width="90" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a href="#" class="btn_blue">도면검색(PC)</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                             -->
                                        </tr>
                                    </table>
                                    <%  if(ecadSCH != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="590">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="590">
                                                <tr>
                                                    <td>
                                                        <div id="sch_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadSCH);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    ApplicationData appData = (ApplicationData) item;
                                                            %>
                                                                    <a href="#">
                                                                    <%  if(holder instanceof wt.epm.EPMDocument) { %>
                                                                            <%=wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument)holder, item)%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } else { %>
                                                                            <%=appData.getFileName()%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } %>
                                                                    </a>
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
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr id="toEcadDiv_dwg" style="display:;">
                    <td colspan="4" class="tdwhiteM0">
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                        <table width="750" cellpadding="0" cellspacing="0" class="table_border">
                            <tr>
                                <td width="140" class="tdgrayL">AutoCAD<br><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="text" name="number_dwg" value="<%=(ecadDWG==null)? "":ecadDWG.getNumber()%>" class="txt_field"  style="width:170" id="number_dwg" readonly></td>
                                            <td width="52">
                                            <!--
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td background="../../portal/images/btn_bg1.gif"><a href="#" class="btn_blue">수정</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                             -->
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="130" class="tdgrayL">AutoCAD <%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%><span class="red">*</span></td>
                                <td width="240" class="tdwhiteL"><input type="text" name="name_dwg" value="<%=(ecadDWG==null)? "":ecadDWG.getName()%>" class="txt_field"  style="width:220" id="name_dwg" readonly></td>
                            </tr>
                            <tr>
                                <td width="140" class="tdgrayL">AutoCAD<br><%=messageService.getString("e3ps.message.ket_message", "01294") %><%--도면첨부--%><span class="red">*</span></td>
                                <td colspan="3" class="tdwhiteL">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td><input type="file" name="file_dwg_primary" class="txt_field"  style="width:590" id="file_dwg_primary"></td>
                                            <!--
                                            <td><input type="text" name="file_dwg_primary" class="txt_field"  style="width:470" id="file_dwg_primary"></td>
                                            <td width="120">
                                                <table border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td width="90" background="../../portal/images/btn_bg1.gif" class="btn_blue"><a href="#" class="btn_blue">도면검색(PC)</a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                             -->
                                        </tr>
                                    </table>
                                    <%  if(ecadDWG != null) { %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="590">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table cellpadding="0" cellspacing="0" style="margin-bottom:3px;" width="590">
                                                <tr>
                                                    <td>
                                                        <div id="dwg_primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                                            <%  ContentHolder holder = (ContentHolder)ContentHelper.service.getContents(ecadDWG);
                                                                ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                                                if( (item != null) && (item instanceof ApplicationData)) {
                                                                    ApplicationData appData = (ApplicationData) item;
                                                            %>
                                                                    <a href="#">
                                                                    <%  if(holder instanceof wt.epm.EPMDocument) { %>
                                                                            <%=wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument)holder, item)%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } else { %>
                                                                            <%=appData.getFileName()%>&nbsp;(<%=appData.getFileSizeKB() + " KB"%>)
                                                                    <%  } %>
                                                                    </a>
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
                        <table width="750" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="space10"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
    </tr>
</table>
</form>
</body>
</html>
<script type="text/Javascript">
<!--

function goPageChange(_obj) {
    document.location.href="/plm/jsp/project/ProjectOutputDwgResultRegistry.jsp?manageType="+_obj.value;
}

function doChangeAppType(_obj) {
    //primary_file_attach_div
    //ug_model_search_btn
    if(_obj.value == 'UG') {
        document.getElementById('primary_file_attach_div').style.display='none';
        document.getElementById('ug_model_search_btn').style.display='';
    } else {
        document.getElementById('primary_file_attach_div').style.display='';
        document.getElementById('ug_model_search_btn').style.display='none';
    }
}
/*****************************************************************************************
 * Event ...
 *****************************************************************************************/
function goChangeEvent(oEvent) {
    var oEvnetObj = oEvent.srcElement;
    var oEvnetObjName = oEvnetObj.name;
    //alert(oEvnetObj.tagName);
    if(oEvnetObjName == 'devStage') {
        doChangeDevStage(oEvnetObj);
    }else if(oEvnetObjName == 'category'){
        doChangeCategory(oEvnetObj);
    //}else if(oEvnetObjName == 'manageType'){
    //  goPageChange(oEvnetObj);
    }else if(oEvnetObjName == 'cadAppType'){
        doChangeAppType(oEvnetObj);
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


function changeIE(){ // Explorer Key Event
    if(event.type=='change') {
        goChangeEvent(event);
    }
}

function keyupIE() {
    if(event.type=='keyup') {
        goKeyupEvent(event);
    }
}

function blurIE() {
    if(event.type=='blur') {
        goKeyupEvent(event);
    }
}

function clickIE() {
    if(event.type=='click') {
        goClickEvent(event);
    }
}



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
//  -->
</script>
