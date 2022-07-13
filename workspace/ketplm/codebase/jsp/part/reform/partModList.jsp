<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page
    import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 ext.ket.shared.query.*,
                 ext.ket.part.entity.*,
                 e3ps.project.ProjectMaster,
                 e3ps.project.E3PSProject,
                 e3ps.project.beans.ProjectHelper,
                 ext.ket.shared.code.service.*,
                 ext.ket.part.classify.service.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      
    
    WTPart wt = null;
    if (poid != null && !poid.equals("")) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
    }

    // DIE와 연결된 반제품 리스트 가져오기 - KETHalbPartDieSetPartLink
    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    List<KETHalbPartDieSetPartLink> halbList = query.queryForListLinkByRoleB(KETHalbPartDieSetPartLink.class, WTPartMaster.class, (WTPartMaster) wt.getMaster());
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
<!--

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
  openWindow(url, '',1050,800);
}

function addHalbPartAfterFunc(rtnArr){
    for(var i = 0; i < rtnArr.length ; i++){
        var arr = new Array();
        arr = rtnArr[i];
        addHalbPart(arr);
    }   
}

function addHalbPart(arr){
	
    var paramOid = arr[0];
    var partno = arr[1];
    var Ispermission = arr[16]; 
    var rtnFlag = false;
    $.each($('[name=diePartMastOidArray]'), function(i, val){
        if(val.value == paramOid) {
            alert("선택한 "+arr[1]+" 반제품은 이미 추가된 부품입니다.");
            rtnFlag = true;
            hideProcessing();
            return;
        }
    });
    
    if(Ispermission != "Y"){
    	alert("선택한 "+ partno +" 원자재는 대표금형에 연결할 수 없습니다.");
    	rtnFlag = true;
        hideProcessing();
        return;
    }
    
    if(rtnFlag){
    	hideProcessing();
        return;
    }
    
    var dieOid = "<%=poid%>";
    
    if(dieOid == ""){
    	alert("'Die No'가 없습니다.");
    	hideProcessing();
    	return;
    }
    
    //alert('d:' + dieOid + " h:" + paramOid);
    var checkStr = saveDieHalbLink(dieOid, paramOid);
    if("E" == checkStr){
        hideProcessing();
        return;
    }else if("Y" == checkStr){
    	location.reload(true);
    }else if("N" == checkStr){
        alert("부품 추가에 실패했습니다.");
        hideProcessing();
        return;
    }
}

function saveDieHalbLink(dieOid, halbOid){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/addDieHalbLink.do?dieOid='+dieOid+'&halbOid='+halbOid,
        success: function (data) {
            if(data != 'Fail'){
                try{
                    if(data=='Y'){
                        ret = 'Y';
                    }else{
                        ret = 'N';
                    }
                }catch(e){alert(e.message); ret = "E"; }
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }
 
function delHalbPart(halbOid){
    
    var dieOid = "<%=poid%>";
    if(dieOid == ""){
        alert("'Die No'가 없습니다.");
        hideProcessing();
        return;
    }
    
    //alert('d:' + dieOid + " h:" + halbOid);
    var checkStr = deleteDieHalbLink(dieOid, halbOid);
    if("E" == checkStr){
        hideProcessing();
        return;
    }else if("Y" == checkStr){
        alert("'선택'하신 Part가 삭제되었습니다.");
        location.reload(true);
    }else if("N" == checkStr){
        alert("부품 삭제에 실패했습니다.");
        hideProcessing();
        return;
    }
}

function deleteDieHalbLink(dieOid, halbOid){
    var ret = "N";
    $.ajax({
        type: 'get',
        async: false,
        cache:false,
        url: '/plm/ext/part/base/deleteDieHalbLink.do?dieOid='+dieOid+'&halbOid='+halbOid,
        success: function (data) {
            if(data != 'Fail'){
                try{
                    if(data=='Y'){
                        ret = 'Y';
                    }else{
                        ret = 'N';
                    }
                }catch(e){alert(e.message); ret = "E"; }
            }else{
                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
                ret = "E";
            }
        },
        fail : function(){
            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            ret = "E";
        }
    });
    
    return ret;
 }
 
-->
</script>
</head>
    <body>
        <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%">
            <!-- tr>
                 <td class="tdblueL">금형(SET)</td>
            </tr>  -->
            <tr>
		         <td class="tdwhiteL0" >
		            <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                <tr>
		                    <td class="space5"></td>
		                </tr>
		            </table>
		            <div id="div_scroll3"
		                style="overflow-x: hidden; overflow-y: auto;" class="table_border">
		                <table id="drawingTable" width="100%" cellpadding="0" cellspacing="0">
		                    <tbody id="drawinTableBody">
		                        <colgroup>
		                          <col width="30"/>
		                          <col width="30"/>
		                          <col width="80"/>
		                          <col width="120"/>
		                          <col width="90"/>
		                          <col width="*"/>
		                          <col width="80"/>
		                          <col width="100"/>
		                          <col width="100"/>
		                          <col width="110"/>
		                       </colgroup>
		                       <tr>
		                           <td class="tdgrayM"><a href="#" onclick="javascript:showProcessing();SearchUtil.selectOnePart('addHalbPartAfterFunc', 'pType=HRW');"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
		                            <td class="tdgrayM">No</td>
					                <td class="tdgrayM">부품유형</td>
					                <td class="tdgrayM">부품분류</td>
					                <td class="tdgrayM">부품번호</td>
					                <td class="tdgrayM">부품명</td>
					                <td class="tdgrayM">Rev</td>
					                <td class="tdgrayM">프로젝트번호</td>
					                <td class="tdgrayM">ECO NO</td>
					                <td class="tdgrayM">주도면 NO</td>
		                      </tr>
                                <%
					            if ((halbList == null) || (halbList.size() == 0)) {
					            %>
					            <%
					                } else {
					            %>
					            <%
					                int halbCount = 1;
					                for (KETHalbPartDieSetPartLink link : halbList) {
					                    
					                    WTPartMaster wtPartMast = link.getHalb();
					                    WTPart wtPart = (WTPart)e3ps.common.obj.ObjectUtil.getLatestObject(wtPartMast);
					                    String partOid = KETObjectUtil.getOidString(wtPart);
					                    // 부품유형 
					                    String spPartType = PartUtil.getPartType(wtPart);
					                    // 부품분류 
					                    String clazKrName = PartUtil.getPartClassificationKrName(wtPart);
					                    // Rev 
					                    String spPartRevision = PartUtil.getKetPartRevision(wtPart);
					                    // 프로젝트번호
					                    E3PSProject  project = PartUtil.getE3PSProject(wtPart);
					                    String projectNo = project == null ? null : project.getPjtNo();
					
					                    // ECO NO 
					                    String ecoNo = PartUtil.getEcoNo(wtPart);
					                    // 주도면 NO 
					                    List<EPMDocument> primaryList = PartUtil.getReleateEpmDocumnetList(wtPart);
					                   
					            %>
					            <tr>
					                <td width="30" class="tdwhiteM">
                                       <a href="#" onclick="javascript:showProcessing();delHalbPart('<%=KETObjectUtil.getOidString(wtPart)%>');"><b><img src="/plm/portal/images/b-minus.png"></b></a>
                                    </td>
					                <td class="tdwhiteM"><%=halbCount++%></td>
					                <td class="tdwhiteM"><%=spPartType%></td>
					                <td class="tdwhiteM"><%=clazKrName%></td>
					                <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:openView('<%=partOid %>');"><%=wtPartMast.getNumber()%></a></td>
					                <td class="tdwhiteM"><%=wtPartMast.getName()%></td>
					                <td class="tdwhiteM"><%=spPartRevision%></td>
					                <% if(projectNo == null){ %>
					                <td class="tdwhiteM">&nbsp;</td>
					                <% }else{%>
					                <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:viewProjectPopup('<%=PersistenceHelper.getObjectIdentifier(project).getStringValue()%>');"><%=projectNo%></a></td>
					                <% }%>
					                <td class="tdwhiteM"><a href="javascript:;" onclick="javascript:openViewEco('<%=ecoNo%>');"><%=ecoNo%></a></td>
					                <% if(primaryList.size() == 0 ){ %>
					                <td class="tdwhiteM">&nbsp;</td>
					                <% }else{ %>
					                <td class="tdwhiteM">
					                <%
					                    for(int j=0; j < primaryList.size(); j++){
					                        EPMDocument epmDoc = primaryList.get(j);
					                %>
					                   <%=((j==0)?"":",") %><a href="javascript:;" onclick="javascript:openView('<%=PersistenceHelper.getObjectIdentifier(epmDoc).getStringValue()%>');"><%=epmDoc.getNumber()%></a>
					                <%
					                   }
					                %>
					                <input type='hidden' name='diePartMastOidArray' value='<%=KETObjectUtil.getOidString(wtPartMast)%>'/>
					                </td>
					                <% } %>
					            </tr>
					    
					            <%
					                }
					            %>
					            <%
					                }
					            %>
		                  </tbody>
		                </table>
		            </div>
		            <table border="0" cellspacing="0" cellpadding="0" width="100%">
		                <tr>
		                    <td class="space5"></td>
		                </tr>
		            </table>
		         </td>
		    </tr>
        </table>
    
    </body>
</html>