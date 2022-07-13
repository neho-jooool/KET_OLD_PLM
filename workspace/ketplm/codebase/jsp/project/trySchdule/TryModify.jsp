<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.project.material.MoldMaterial"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01016") %><%--금형 Try 일정 수정--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
    
    CalendarUtil.dateInputFormat('planDate', null);
    
});

function addMember(rname) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        alert("error");
        return;
    }

    //document.getElementById('requester').value = "";
    acceptMember(rname, attacheMembers);
}

function acceptMember(rname, objArr) {
    if(objArr.length == 0) {
        return;
    }

    var keyName = document.getElementById("temp" + rname);
    var displayName = document.getElementById(rname);

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

    }
}

//원재료 검색 팝업 호출
function selMaterial() {
    var param = "";
    var form = document.forms[0];
    if(form.tempmaterial.value != ""){
        param = "materialOid=" + form.tempmaterial.value + "&pOid=" + form.pOid.value + "&height=" + form.height.value + "&wide=" +form.wide.value + "&moldType=" + form.moldType.value;
    }else{
        param = "moldType=" + form.moldType.value;
    }
    param = param + "&itemTypeCheck=true&isTry=true";
    var url = "/plm/jsp/project/material/SelectMaterialPopup.jsp?" + param;

    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=450px; dialogHeight:250px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    setMaterial(attache);
}

function setMaterial(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];

    form.tempmaterial.value = trArr[0];
    form.material.value = trArr[1];
    form.pOid.value = trArr[2];
    form.height.value = trArr[3];
    form.wide.value = trArr[4];

//	for(i = 0; i < trArr.length; i++){
//		alert(trArr[i]);
//	}

}
//검색 호출 끝

//프로젝트 속성  가져오기 시작
function addProject() {
//	var form = document.forms[0];

    var url = "/plm/jsp/project/trySchdule/TrySearchProject.jsp";

    returnValues = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");

//	if(typeof returnValue == "undefined" || returnValue == null) {
//		alert("..false");
//		return;
//	}
    acceptProject(returnValues);
}

function acceptProject(arrObj){
    var subArr;
    var mold = document.getElementById('moldProject');
    var tempmold = document.getElementById('tempmold');
        for(var i = 0; i < arrObj.length; i++) {
            subArr = arrObj[i];

            tempmold.value = subArr[0];
            mold.value =subArr[1];

        }
}

function save(){
    document.forms[0].action="/plm/jsp/project/trySchdule/TrySave.jsp";
    document.forms[0].submit();
}

function delRequestor(){
    document.forms[0].requester.value = "";
    document.forms[0].temprequester.value = "";
}

function delMember(rname){
    document.getElementById(rname).value = "";
    document.getElementById("temp" + rname).value = "";
}

</script>
</head>
<%
    TrySchduleData data = null;
    String oid = request.getParameter("oid");
    Object obj = CommonUtil.getObject(oid);
    if(obj instanceof E3PSTask){
        E3PSTask task = (E3PSTask)obj;
        data = new TrySchduleData(task);
    }else if(obj instanceof TrySchdule){
        TrySchdule trySchdule = (TrySchdule)obj;
        data = new TrySchduleData(trySchdule);
    }

    String dieNo = "&nbsp;";
    String partNumber = "&nbsp;";
    String tryType = "&nbsp;";
    String moldPlanerName = "&nbsp;";
    String moldMakerName = "&nbsp;";
    String projectPlanerName = "&nbsp;";
    String partName = "&nbsp;";
    String materialName = "&nbsp;";
    String grade = "&nbsp";
    String materialOid = "";
    String propertyOid = "";
    String thickness = "";
    String width = "";
    String outsourcingName = "&nbsp;";
    String cavSu = "&nbsp;";
    String ton = "";
    String quantity = "";
    String requestorName = "&nbsp;";
    String state = "&nbsp;";
    String tryPlace = "연구소";
    String des = "";
    String dayString = "";
    String requestorId = "";

    MoldMaterial material = null;
    String outsourcing = null;

    boolean isCompleted = data.isCompleted;
    boolean isTryPlan = data.isTryPlan;

    //String oid = data.oid;

    if(data.dieNo != null){
        dieNo = data.dieNo;
    }

    if(data.partNumber != null){
        partNumber = data.partNumber;
    }

     if(data.tryType != null){
         tryType = data.tryType;
     }

     if(data.moldPlanerName != null){
         moldPlanerName = data.moldPlanerName;
     }

     if(data.moldMakerName != null){
         moldMakerName = data.moldMakerName;
     }

     if(data.projectPlanerName != null){
         projectPlanerName = data.projectPlanerName;
     }

     if(data.partName != null){
         partName = data.partName;
     }

     if(data.materialName != null){
         materialName = data.materialName;
     }

     if(data.material != null){
         material = data.material;
         grade = material.getGrade();
         materialOid = CommonUtil.getOIDString(material);
         propertyOid = CommonUtil.getOIDString(data.property);
         if("비철".equals(material.getMaterial())){
             thickness = data.thickness;
              width = data.width;
         }

     }

     if(data.outsourcingName != null){
         outsourcingName = data.outsourcingName;
     }

     if(data.outsourcing != null){
         outsourcing = data.outsourcing;
     }

     if(data.cavSu != null){
         cavSu = data.cavSu;
     }

     if(data.ton != null){
         ton = data.ton;
     }

     if(data.quantity != null){
         quantity = data.quantity;
     }

     if(data.requestorName != null){
         requestorName = data.requestorName;
     }

    if(data.requestor != null){
         WTUser requestor = data.requestor;
         requestorId = CommonUtil.getOIDString(requestor);
         //Kogger.debug("%%%%%%asdf = " + requestor);
         //Kogger.debug("%%%%%%asdf = " + requestorId);
     }else{
         //Kogger.debug("444444444" + requestorId);
     }

     if(data.state != 0){
         if(data.state == -1){
             state = "지연";
         }else if(data.state == 1){
             state = "완료";
         }else if(data.state == 2){
             state = "확정";
         }else if(data.state == 3){
             state = "예정";
         }
     }

     if(data.tryPlace != null){
         tryPlace = data.tryPlace;
     }

     if(data.des != null){
         des = data.des;
     }

     if(data.dayString != null){
         dayString = data.dayString;
     }

     String itemType = "Cavity";
     if("Press".equals(data.project.getMoldInfo().getItemType())){
         itemType = "Line*Pcs";
     }

     HashMap map = new HashMap();
    map.put("type", "TRYTYPE");

    QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);

    QueryResult result = PersistenceHelper.manager.find(qs);
%>

<body>
<form>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<input type="hidden" name="tempmold" value=""></input>
<input type="hidden" name="mode" value="save"></input>
<input type="hidden" name="height" value="<%=thickness %>"></input>
<input type="hidden" name="wide" value="<%=width %>"></input>
<input type="hidden" name="pOid" value="<%=propertyOid %>"></input>
<input type="hidden" name="moldType" value="<%=data.project.getMoldInfo().getItemType() %>"></input>

<table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01041") %><%--금형Try 일정 수정--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            
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
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="20%" class="tdblueL">Die No</td>
                <td width="30%" class="tdwhiteL"><%=dieNo %></td>
                <td width="20%" class="tdblueL">Part No</td>
                <td width="30%" class="tdwhiteL0"><%=partNumber %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL">Part Name</td>
                <td width="30%" class="tdwhiteL"><%=partName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00538") %><%--Try단계--%></td>
                <td width="30%" class="tdwhiteL0">
                  <%if(obj instanceof E3PSTask){
                      out.print(tryType);
                      }else if(obj instanceof TrySchdule){
                  %>
                    <select name="tryType" class="fm_jmp" style="width:100">
                      <%if(result != null && result.size() > 0){
                        NumberCode nCode = null;
                        String nCodeOid = "";
                        String checked = "";
                        Object obj2[] = null;

                        while(result.hasMoreElements()) {
                            obj2 = (Object[])result.nextElement();
                            nCode = (NumberCode)obj2[0];
                            //Kogger.debug("nCode = " + nCode.getName());
                    %>
                              <option value="<%=nCode.getName() %>" <%if(tryType.equals(nCode.getName())){ %>selected<%} %>><%=nCode.getName() %></option>
                    <%  }
                      }
                    %>
                    </select>
                  <%} %>
                </td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02599") %><%--제품설계--%></td>
                <td width="30%" class="tdwhiteL"><%=projectPlanerName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                <td width="30%" class="tdwhiteL0"><%=moldPlanerName %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                <td width="30%" class="tdwhiteL"><%=moldMakerName %></td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02221") %><%--원재료명--%></td>
                <td width="30%" class="tdwhiteL0">
                  <%if(obj instanceof E3PSTask){
                      out.print(materialName);
                      }else if(obj instanceof TrySchdule){
                  %>
                  <input type="text" id="textfield" name="material" class="txt_field"  value="<%=grade %>">
                  <input type="hidden" name="tempmaterial" value="<%=materialOid %>"></input>
                  <a href="#" onclick="javascript:selMaterial();"><img src="/plm/portal/images/icon_5.png" border="0" align="absmiddle"></a>&nbsp;<a href="#" onclick="javascript:delMember('material');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                  <%} %>
                </td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
                <td width="30%" class="tdwhiteL"><%=outsourcingName %></td>
                <td width="20%" class="tdblueL"><%=itemType %></td>
                <td width="30%" class="tdwhiteL0"><%=cavSu %></td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%></td>
                <td width="30%" class="tdwhiteL">
                  <input type="text" name="ton" class="txt_field"  style="width:100" id="textfield4" value="<%=ton %>">
                </td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
                <td width="30%" class="tdwhiteL0">
                  <input type="text" name="quantity" class="txt_field"  id="textfield5" value="<%=quantity %>">
                </td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02258") %><%--의뢰자--%></td>
                <td width="30%" class="tdwhiteL">
                  <%if(obj instanceof E3PSTask){
                      out.print(requestorName);
                      }else if(obj instanceof TrySchdule){
                  %>
                  <input type="text" name="requester" class="txt_field"  style="width:100" id="textfield6" value="<%=requestorName %>" readOnly>
                  <input type="hidden" name="temprequester" value="<%=requestorId %>"></input>
                <a href="#" onclick="javascript:addMember('requester');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;&nbsp;<a href="#" onclick="javascript:delRequestor();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                <%} %>
                </td>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                <td width="30%" class="tdwhiteL0">
                  <%if(obj instanceof TrySchdule && !isCompleted){
                      if((!isTryPlan && CommonUtil.isMember("MoldDesign")) || CommonUtil.isMember("DateChange") || CommonUtil.isAdmin()){
                  %>
                  <input type="text" name="planDate" class="txt_field"  style="width:100" id="textfield7" value="<%=dayString %>">
                  <%  }else{%>
                      <input type="hidden" name="planDate" value="<%=dayString %>"></input>
                  <%  out.print(dayString);
                        }
                    }else{%>
                        <input type="hidden" name="planDate" value="<%=dayString %>"></input>
                  <%
                      out.print(dayString);
                    } %>
                </td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00534") %><%--Try 종류--%></td>
                <td width="80%" colspan="3" class="tdwhiteL0">
                    <input type="text" name="tryPlace"class="txt_field"  style="width:100" id="textfield7" value="<%=tryPlace %>"></input>
                </td>
              </tr>
              <tr>
                <td width="20%" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                <td width="80%" colspan="3" class="tdwhiteL0" style="height:100"><textarea name="des" class="txt_field" id="textfield3" style="width:100%; height:96%"><%=des %></textarea></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="10">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
