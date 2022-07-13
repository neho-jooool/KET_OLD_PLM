<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="ext.ket.part.util.PartSpecEnum"%>
<%@page import="ext.ket.part.util.PartSpecGetter"%>
<%@page import="wt.part.WTPart"%>
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");


    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData data = new E3PSProjectData(project);
    MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
    MoldItemInfo moldInfo = moldProject.getMoldInfo();

    ProductProject productProject = null;
    E3PSProjectData productData = null;
    if(moldInfo != null){
        productProject = moldInfo.getProject();
        if(productProject != null){
            productData = new E3PSProjectData(productProject);
        }
    }

    // 금형 정보
    String itemType = moldInfo.getItemType();

    boolean isMold = false;

    if(itemType.equals("Mold")){
        isMold = true;
    }

    String moldType = "&nbsp;";
    if(moldInfo.getMoldType() != null){
        moldType = moldInfo.getMoldType().getName();
    }

    String productType = "&nbsp;";
    if(moldInfo.getProductType() != null){
        productType = moldInfo.getProductType().getName();
    }

    String place = "&nbsp;";
    if(moldInfo.getProductionPlace() != null){
        place = moldInfo.getProductionPlace();
    }

    String making = "&nbsp;";
    if(moldInfo.getMaking() != null){
        making = moldInfo.getMaking();
    }

    String outSourcing = "";

    /* if(moldProject.getOutSourcing() != null){
        outSourcing = moldProject.getOutSourcing();
    } */
    
    /* if("사내".equals(moldInfo.getMaking()) && moldInfo.getMakingPlace() != null){
        outSourcing = moldInfo.getMakingPlace().getName();
    }else{
       PartnerDao partnerDao = new PartnerDao();
       outSourcing = partnerDao.ViewPartnerName(moldInfo.getMakingPlacePartnerNo());
    }
     */
     
     WTPart dieMaster = PartBaseHelper.service.getLatestPart(moldInfo.getDieNo());
     outSourcing = PartSpecGetter.getPartSpec(dieMaster, PartSpecEnum.SpDieWhere);
     NumberCode outSourcingCode = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", outSourcing);
     if(outSourcingCode != null){
        outSourcing = outSourcingCode.getName();
     }else{
        outSourcing = "&nbsp;";
     }
     

    String rank = "";
    if(moldProject.getRank() != null){
        rank = CommonUtil.getOIDString(moldProject.getRank());
    }

    String productWeight = "";
    double intProduct = 0;
    if(moldProject.getProductWeight() != null){
        productWeight = moldProject.getProductWeight();
        try{
            intProduct = Double.parseDouble(productWeight);
        }catch(Exception e){

        }
    }
    String scrapWeight = "";
    double intScrap = 0;
    if(moldProject.getScrapWeight() != null){
        scrapWeight = moldProject.getScrapWeight();
        try{
            intScrap = Double.parseDouble(scrapWeight);
        }catch(Exception e){

        }
    }

    String cavity = "&nbsp";
    double intCavity = 0;
    if(moldInfo.getCVPitch() != null){
        cavity = moldInfo.getCVPitch();
        StringTokenizer st = new StringTokenizer(cavity, "*");
        intCavity = 1;
        while(st.hasMoreElements()){
            String value = st.nextToken();
            int value_i = 0;
            try{
                value_i = Integer.parseInt(value);
            }catch(Exception e){
                Kogger.debug("value = " + value);
            }
            intCavity *= value_i;
        }
    }

    String ctSpm = "&nbsp";
    if(moldInfo.getCTSPM() != null){
        ctSpm = moldInfo.getCTSPM();
    }

    double totalWeight = (intProduct + intScrap) * intCavity;

    String specialSpec = "";
    if(moldProject.getSpecialSpec() != null && moldProject.getSpecialSpec().length() > 0){
        specialSpec = moldProject.getSpecialSpec();
    }

    //PM 정보
    String pmName = "";
    String pmDept = "";
    String pmOid = "";

    WTUser user =  null;
    if(ProjectUserHelper.manager.getPM(project) != null){
        user = ProjectUserHelper.manager.getPM(project);
        pmOid = CommonUtil.getOIDString(user);
        PeopleData pData = new PeopleData(user);
        pmName = pData.name;
        pmDept = pData.departmentName;
    }


    //원재료 정보

    String mType = "&nbsp;";
    String maker = "&nbsp;";
    String materialType = "&nbsp;";
    String grade = "&nbsp;";
    MoldMaterial material = null;

    if(moldInfo.getMaterial() != null){
        material = moldInfo.getMaterial();

        mType = material.getMaterial();
        maker = material.getMaterialMaker().getName();
        materialType = material.getMaterialType().getName();
        grade = material.getGrade();
    }

    String property = "&nbsp;";
    if(moldInfo.getProperty() != null){
        property = moldInfo.getProperty().getName();
    }

    String grade2 = "&nbsp;";

    if(mType.equals("비철")){
        grade2 = moldInfo.getThickness() + "t" + "*" + moldInfo.getWidth() +"w";
    }

    String printMat = "Shrinkage";

    if("Press".equals(moldInfo.getItemType())){
        printMat = "Clearance";
    }

    String shrinkage = "";
    if(moldProject.getShrinkage() != null){
        shrinkage = moldProject.getShrinkage();
    }else if(isMold){
        shrinkage = "/1000";
    }


    //설비 정보
    String machineOid = "";

    MoldMachine machine = null;

    String makerOid = "";
    String typeOid = "";
    String tonOid = "";
    String modelOid = "";
    String model = "";
    String type = "";
    String remark = "";

    if(moldProject.getMoldMachine() != null){
        //machine = (MoldMachine)CommonUtil.getObject("e3ps.project.machine.MoldMachine:236048");

        machine = moldProject.getMoldMachine();
        makerOid = CommonUtil.getOIDString(machine.getMachineMaker());
        typeOid = CommonUtil.getOIDString(machine.getMachineType());
        type = machine.getMachineType().getName();
        tonOid = CommonUtil.getOIDString(machine.getTon());
        modelOid = CommonUtil.getOIDString(machine);
        model = machine.getModel();
    }

    if(moldProject.getRemark() != null && moldProject.getRemark().length() > 0){
        remark = moldProject.getRemark();
    }

    // 생산처
    String inoutName1 = "&nbsp;";
    String inoutName2 = "&nbsp;";
    PartnerDao partnerDao2 = null;
    if(moldInfo.getPartnerNo() != null && moldInfo.getPartnerNo().length() > 0) {
           partnerDao2 = new PartnerDao();
           inoutName1 = "외주";
        inoutName2 = partnerDao2.ViewPartnerName(moldInfo.getPartnerNo());
    }else if(moldInfo.getPurchasePlace() != null) {
        inoutName1 = "사내";
        inoutName2 = StringUtil.checkNull(moldInfo.getPurchasePlace().getName());
    }

//  Kogger.debug("makerOid = " + makerOid);
//  Kogger.debug("typeOid = " + typeOid);
//  Kogger.debug("tonOid = " + tonOid);
//  Kogger.debug("modelOid = " + modelOid);


    Vector tMap = null;


%>

<%@page import="e3ps.project.machine.MoldMachine"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.machine.benas.MachineHelper"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>


<%@page import="e3ps.project.material.MoldMaterial"%>
<%@page import="e3ps.ews.dao.PartnerDao"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Collections"%><%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01028")%><%--금형 프로젝트 수정--%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<script>
function goView(){
    location.href = "/plm/jsp/project/MoldProjectView_2.jsp?oid=<%=oid%>";
}

function onModify(){
    document.forms[0].mode.value="modify";
    document.forms[0].action="/plm/jsp/project/MoldProjectAction.jsp";
    document.forms[0].submit();
}

var ajax = new sack();

function selected(actionType){

    document.forms[0].actionType.value = actionType;
    ajax.requestFile = "/plm/jsp/project/machine/MachineSelectAjax.jsp";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = selectedList;
    ajax.runAJAX();

}


types = new Array("ton", "maker", "model");

function removeAllOption(type){

    var re = document.getElementById(type);
    var len = re.length;

    for(var j = len ; j > 0 ; j--){
        re.remove(j);
    }
}

function selectedList(){
    xmlDoc = ajax.responseXML;
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_Oid = showElements[0].getElementsByTagName("l_Oid");
    var l_name = showElements[0].getElementsByTagName("l_name");

    var showType = xmlDoc.selectNodes("//typeInfo");
    var type = decodeURIComponent(showType[0].getElementsByTagName("type")[0].text);

    var isDelete = false;

    for(var i = 0; i < types.length; i++){
        if(type == types[i]){
            isDelete = true;
        }

        if(isDelete){

            removeAllOption(types[i]);

        }

    }

    var defualt = document.createElement("option");
    defualt.value = "";
    defualt.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>";

    if(l_Oid != null){
        var fTD = document.all.item(type);
        if(type == "model"){
            for(var i = 0; i < l_Oid.length; i++) {
                mate = new Array();
                mate[0] = decodeURIComponent(l_Oid[i].text);
                mate[1] = decodeURIComponent(l_name[i].text);

                var newSpan = document.createElement("option");
                newSpan.innerHTML = decodeURIComponent(l_name[i].text);
                newSpan.value = mate[0];//decodeURIComponent(l_Oid[i].text);
                fTD.appendChild(newSpan);
            }
        }else{
            for(var i = 0; i < l_Oid.length; i++) {
                var newSpan = document.createElement("option");
                newSpan.innerHTML = decodeURIComponent(l_name[i].text);
                newSpan.value = decodeURIComponent(l_Oid[i].text);
                fTD.appendChild(newSpan);
            }
        }
    }

}

//금형담당자 변경
var rname_ = "";
function addMember(rname) {
	rname_ = rname;
	var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod=acceptMember";
    getOpenWindow2(url,'ModifyMoldProject', 820, 710);
}

function acceptMember(objArr) {
    if(objArr.length == 0) {
        return;
    }

    var keyName = document.getElementById("temp" + rname_);
    var displayName = document.getElementById(rname_);
    var department = document.getElementById("department");


    /*
        subArr[0] = chkobj[i].value;//wtuser oid
        subArr[1] = chkobj[i].poid;//people oid
        subArr[2] = chkobj[i].doid;//dept oid
        subArr[3] = chkobj[i].uid;//uid
        subArr[4] = chkobj[i].sname;//name
        subArr[5] = chkobj[i].dname;//dept name
        subArr[6] = chkobj[i].duty;//duty
        subArr[7] = chkobj[i].dutycode;//duty code
        subArr[8] = chkobj[i].email;//email
    */

    var nonUserArr = new Array();
    for(var i = 0; i < objArr.length; i++) {
        infoArr = objArr[i];
        displayName.value = infoArr[4];
        keyName.value = infoArr[0];
        //department.value = infoArr[5];
    }
}


function delMember(name){
    document.getElementById(name).value = "";
    document.getElementById("temp" + name).value = "";
}

//제작처  가져오기 시작
function addProcess(type, depth) {
    var form = document.forms[0];

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    acceptProcess(returnValue, type);
}

function acceptProcess(arrObj, type){
    var subArr;
    var form = document.forms[0];

        for(var i = 0; i < arrObj.length; i++) {
            subArr = arrObj[i];

//      form.outSourcingOid.value = subArr[0];
            form.outSourcing.value = subArr[2];
        }
}

function delProcess(){
    document.getElementById("outSourcing").value = "";
//  document.getElementById("outSourcingOid").value = "";
}

//협력사검색 팝업 Open
function selectPartner(){
    var url="../../jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
    openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
    if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        //document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("outSourcing").value = arr[0][1];
}
$(document).ready(function(){
	//사용자 suggest
    SuggestUtil.bind('USER', 'pmName','temppmName');
});
</script>
</head>
<body>
    <form>
        <input type="hidden" name="mode" value="modify"></input> <input type="hidden" name="actionType" value=""> <input
            type="hidden" name="oid" value="<%=oid%>"></input> <input type="hidden" name="itemType" value="<%=itemType%>"></input>
        <table style="width: 100%; height: 100%;">
            <tr>
                <td valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table style="height: 28px;">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01028")%><%--금형 프로젝트 수정--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01098")%><%--금형정보--%></td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:onModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <colgroup>
                            <col width="120px">
                            <col>
                            <col width="120px">
                            <col>
                            <col width="120px">
                            <col>
                        </colgroup>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01051")%><%--금형구분--%></td>
                            <td class="tdwhiteL"><%=itemType%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01078")%><%--금형분류--%></td>
                            <td class="tdwhiteL"><%=productType%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01094")%><%--금형유형--%></td>
                            <td class="tdwhiteL0"><%=moldType%></td>
                        </tr>
                        <tr>
                            <td class="tdblueL">Rank</td>
                            <td class="tdwhiteL"><select  class="fm_jmp" name="rank" style="width: 70%">
                                    <%
                                        tMap = NumberCodeHelper.manager.getNumberCodeLevelType("RANK", "금형");
                                        for (int i = 0; i < tMap.size(); i++) {
                                    		NumberCode tNum = (NumberCode) tMap.get(i);
                                    		String value = CommonUtil.getOIDString(tNum);
                                    		String selected = "";
                                    		if (value.equals(rank)) {
                                    		    selected = "selected";
                                    		}
                                    %>
                                    <option value="<%=value%>" <%=selected%>><%=tNum.getName()%></option>
                                    <%
                                        }
                                    %>
                            </select></td>
                            <td class="tdblueL">
                                <%
                                    if (isMold) {
                                %>Cavity<%
                                    } else {
                                %>Line*Pcs<%
                                    }
                                %>
                            </td>
                            <td class="tdwhiteL"><%=cavity%></td>
                            <td class="tdblueL">
                                <%
                                    if (isMold) {
                                %>Target C/T<%
                                    } else {
                                %>Target SPM<%
                                    }
                                %>
                            </td>
                            <td class="tdwhiteL0"><%=ctSpm%></td>
                        </tr>
<%--                         <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02604")%>제품예상중량</td>
                            <td class="tdwhiteL"><INPUT style="width: 60%" class="txt_field" name="productWeight" value="<%=productWeight%>">g</td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00464")%>Scrap예상중량</td>
                            <td class="tdwhiteL"><INPUT style="width: 60%" class="txt_field" name="scrapWeight" value="<%=scrapWeight%>">g</td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00526")%>Total 중량</td>
                            <td class="tdwhiteL0"><%=totalWeight%>g</td>
                        </tr> --%>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532")%><%--제작구분--%></td>
                            <td class="tdwhiteL"><%=making%></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01058")%><%--금형담당자--%></td>
                            <input type="hidden" id="temppmName" name="temppmName" value="<%=pmOid%>"></input>
                            <td class="tdwhiteL">
                                <INPUT style="width: 60%" id="pmName" class="txt_field" name="pmName" value="<%=pmName%>">&nbsp;
                                <a href="javascript:addMember('pmName')"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;
                                <a href="javascript:delMember('pmName')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533")%><%--제작처--%></td>
                            <td class="tdwhiteL0"><%=outSourcing%></td>
                            <%-- <td class="tdwhiteL0">
                                <%
                                    if (making.equals("사내")) {
                                %> <select class="fm_jmp" id="outSourcing" name="outSourcing">
                                    <option value="사출금형개발팀" 
                                        <%if ("사출금형개발팀".equals(outSourcing)) {
                                		    out.print("selected");
                                		}%>>사출금형개발팀</option>
                                    <option value="프레스금형개발팀" <%if ("프레스금형개발팀".equals(outSourcing)) {
                                		    out.print("selected");
                                		}%>>프레스금형개발팀</option>
                                    <option value="중국법인" <%if ("중국법인".equals(outSourcing)) {
                                		    out.print("selected");
                                		}%>>중국법인</option>
                                    <option value="전자금형개발팀" <%if ("전자금형개발팀".equals(outSourcing)) {
                                		    out.print("selected");
                                		}%>>전자금형개발팀</option>
                                	<option value="KETS 사출금형개발팀" <%if ("KETS 사출금형개발팀".equals(outSourcing)) {
                                            out.print("selected");
                                        }%>>KETS 사출금형개발팀</option>
                                    <option value="KETS 프레스금형개발팀" <%if ("KETS 프레스금형개발팀".equals(outSourcing)) {
                                            out.print("selected");
                                        }%>>KETS 프레스금형개발팀</option>
                                   </select> <%
                                     } else {
                                 %> <INPUT style="width: 60%" id="outSourcing" name="outSourcing" class="txt_field" value="<%=outSourcing%>" readOnly>&nbsp;<a
                                                                href="javascript:selectPartner();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a
                                                                href="javascript:delProcess();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a> <%
                                     }
                                 %>
                            </td> --%>

                        </tr>
                        <tr>
                            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07249")%><%--금형 현황--%></td>
                            <td colspan="5" class="tdwhiteL0" style="height: 60">
                                <textarea name="specialSpec" cols="110" rows="5" class="fm_area" style="width: 100%; height: 97%; border: 0"><%=specialSpec%></textarea></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02219")%><%--원재료 정보--%></td>
                            <td align="right">&nbsp;</td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%></td>
                            <td width="130" class="tdblueM">Maker</td>
                            <td width="130" class="tdblueM">Type</td>
                            <td width="130" colspan="2" class="tdblueM">Grade</td>
                            <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02223")%><%--원재료특성--%></td>
                            <td width="130" class="tdblueM0"><%=printMat%></td>
                        </tr>
                        <tr>
                            <td width="130" class="tdwhiteM"><%=mType%></td>
                            <td width="130" class="tdwhiteM"><%=maker%></td>
                            <td width="130" class="tdwhiteM"><%=materialType%></td>
                            <td width="70" class="tdwhiteM"><%=grade%></td>
                            <td width="60" class="tdwhiteM"><%=grade2%></td>
                            <td width="130" class="tdwhiteM"><%=property%></td>
                            <td width="130" class="tdwhiteL0">
                                <%
                                    if (material != null) {
                                %> <input style="width: 85%" name="shrinkage" value="<%=shrinkage%>"><%=isMold ? "%" : "mm"%>
                                <%
                                    } else {
                                %> <input type="hidden" name="shrinkage" value=""></input>&nbsp; <%
     }
 %>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space15"></td>
                        </tr>
                    </table>
                    <table width="100%" height=20 " border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01875")%><%--설비 정보--%></td>
                            <td align="right">&nbsp;</td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td width="100" class="tdblueL">Type</td>
                            <td width="160" class="tdwhiteL"><select class="fm_jmp" style="width: 115" name="type"
                                onchange="javascript:selected('ton');">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        if (itemType != null && itemType.length() > 0) {
                                    		tMap = NumberCodeHelper.manager.getNumberCodeLevelType("MACHINETYPE", itemType);
                                    		for (int i = 0; i < tMap.size(); i++) {
                                    		    NumberCode tNum = (NumberCode) tMap.get(i);
                                    		    String value = CommonUtil.getOIDString(tNum);
                                    		    String selected = "";
                                    		    if (value.equals(typeOid)) {
                                    			selected = "selected";
                                    		    }
                                    %>
                                    <option value="<%=CommonUtil.getFullOIDString(tNum)%>" <%=selected%>><%=tNum.getName()%></option>
                                    <%
                                        }
                                        }
                                    %>
                            </select></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03384")%><%--형 체력(Ton)--%></td>
                            <td width="160" class="tdwhiteL"><select class="fm_jmp" style="width: 115" id="ton" name="ton"
                                onchange="javascript:selected('maker');">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                    <%
                                        if (machine != null) {
                                    		Vector vector = MachineHelper.getTonList(machine);

                                    		Collections.sort(vector, new TonComparator());
                                    		for (int i = 0; i < vector.size(); i++) {
                                    		    NumberCode code = (NumberCode) vector.get(i);
                                    		    String value = CommonUtil.getOIDString(code);
                                    		    String name = code.getName();
                                    		    String selected = "";
                                    		    if (value.equals(tonOid)) {
                                    			selected = "selected";
                                    		    }
                                    %>
                                    <option value="<%=value %>" <%=selected%>><%=name%></option>


                                    <%}}%>
                            </select></td>
                            <td width="100" class="tdblueL">Maker</td>
                            <td width="160" class="tdwhiteL0"><select class="fm_jmp" style="width: 115" id="maker" name="maker"
                                onchange="javascript:selected('model');">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                    <%if(machine != null){
                       Vector vector = MachineHelper.getMakerList(machine);
                       for(int i = 0; i < vector.size(); i++){
                              NumberCode code = (NumberCode)vector.get(i);
                           String value = CommonUtil.getOIDString(code);
                           String name = code.getName();
                           String selected = "";
                           if(value.equals(makerOid)){
                               selected = "selected";
                           }
                  %>
                                    <option value="<%=value %>" <%=selected%>><%=name%></option>


                                    <%}}%>

                            </select></td>
                        </tr>
                        <tr>
                            <td width="100" class="tdblueL">Model</td>
                            <td width="160" class="tdwhiteL"><select class="fm_jmp" style="width: 115" id="model" name="model">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                    <%if(machine != null){
                       Vector vector = MachineHelper.getModelList(machine);
                       for(int i = 0; i < vector.size(); i++){
                              MoldMachine data2 = (MoldMachine)vector.get(i);
                           String value = CommonUtil.getOIDString(data2);
                           String name = data2.getModel();
                           String selected = "";
                           if(value.equals(modelOid)){
                               selected = "selected";
                           }
                  %>
                                    <option value="<%=value %>" <%=selected%>><%=name%></option>


                                    <%}}%>
                            </select></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01778") %><%--생산구분--%></td>
                            <td width="160" class="tdwhiteL"><%=inoutName1 %></td>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                            <td width="160" class="tdwhiteL0"><%=inoutName2 %></td>
                        </tr>
                        <tr>
                            <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                            <td style="height: 100" colspan="5" class="tdwhiteL0"><textarea name="remark" cols="110" rows="3"
                                    class="fm_area" style="width: 100%; height: 97%;"><%=remark %></textarea></td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
