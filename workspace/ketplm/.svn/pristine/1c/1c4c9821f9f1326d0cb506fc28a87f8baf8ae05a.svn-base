<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="wt.doc.WTDocumentMaster"%>
<%@page import="wt.doc.WTDocument"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
                 e3ps.common.util.*,
                 e3ps.project.beans.*,
                 e3ps.ecm.entity.*,
                 ext.ket.project.trycondition.entity.*,
                 ext.ket.dqm.entity.*,
                 e3ps.groupware.company.*"%>

<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:setProperty name="control" property="href" value="/plm/servlet/e3ps/SearchProjectOutputServlet" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp" %>
<%
    //

    String oid = StringUtil.checkNull(request.getParameter("oid"));

    String popup = StringUtil.checkNull(request.getParameter("popup"));
    String category = StringUtil.checkNull(request.getParameter("category"));

    String perPage = String.valueOf(control.getInitPerPage());
    String sortKey = ParamUtil.getStrParameter(request.getParameter("sortKey"));
    String dsc = ParamUtil.getStrParameter(request.getParameter("dsc"));
    String taskType = ParamUtil.getStrParameter(request.getParameter("taskType"));

    String chkAll = StringUtil.checkNull(request.getParameter("chkAll"));
    String isAPQP = StringUtil.checkNull(request.getParameter("isAPQP"));
    String isPSO10 = StringUtil.checkNull(request.getParameter("isPSO10"));
    String isESIR = StringUtil.checkNull(request.getParameter("isESIR"));
    String isISIR = StringUtil.checkNull(request.getParameter("isISIR"));


    if(isAPQP.length() == 0 && isPSO10.length() == 0 && isESIR.length() == 0 && isISIR.length() == 0){
        chkAll = "on";
    }

    if(dsc.length() == 0){
        dsc = "true";
    }

    String imgDsc = "(▲)";
    if(dsc.equals("false")){
        imgDsc = "(▼)";
    }

    if(sortKey.length() == 0){
        sortKey = "thePersistInfo.createStamp";
    }
%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="wt.org.WTUser"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript>
<!--
function search() {
//  onProgressBar();
//  document.forms[0].command.value='search';
//  document.forms[0].sessionid.value ="0";
//  document.forms[0].tpage.value = "";

    if(document.forms[0].sessionid != null){
        document.forms[0].sessionid.value = "";
    }

    if(document.forms[0].page != null){
        document.forms[0].page.value = "";
    }

    document.forms[0].method = "post";
//  document.forms[0].command = "search";
    document.forms[0].action = "/plm/servlet/e3ps/SearchProjectOutputServlet";
    document.forms[0].submit();
}

function viewTask(oid) {
    <%if(popup == null || popup.length() == 0){%>
        parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=' + oid, '/plm/jsp/project/TaskView.jsp?oid=' + oid);
    <%}else{%>
        parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    //parent.movePopup(poid,"/plm/jsp/project/TaskView.jsp?oid=" + oid);
    <%}%>
}


function sorting(key){

    document.forms[0].method = "post";
    document.forms[0].action = "/plm/servlet/e3ps/SearchProjectOutputServlet";
    //?oid=e3ps.project.E3PSTask:110050&type=nomal&subAll=all&taskType=task
    if(document.forms[0].sessionid != null){
        document.forms[0].sessionid.value = "";
    }

    if(document.forms[0].page != null){
        document.forms[0].page.value = "";
    }

    //document.forms[0].sessionid.value = "";
    //document.forms[0].page.value = "";

    if(key == document.forms[0].sortKey.value){
        if(document.forms[0].dsc.value == "false"){
            document.forms[0].dsc.value = "true";
        }else{
            document.forms[0].dsc.value = "false";
        }
    }
    else{
        document.forms[0].dsc.value = "false";
    }

    document.forms[0].sortKey.value = key;
    document.forms[0].submit();
}
function doViewEpm(dieNo){
    var url = "/plm/jsp/project/ProjectOutputDwgDieNoLink.jsp?dieNo="+dieNo;
    openOtherName(url, "popup", 660, 530, "status=yes,scrollbars=no,resizable=yes");
}

function openCompleteReson(oid){
    var url="/plm/jsp/project/EtcOutputReasonView.jsp?oid="+oid;
    openOtherName(url,"Reson","500","360","status=no,scrollbars=no,resizable=yes");
}

function checkAll(){
    form = document.forms[0];
    if(form.chkAll.checked == ""){
        form.isAPQP.checked = "checked";
        form.isPSO10.checked = "checked";
        form.isESIR.checked = "checked";
        form.isISIR.checked = "checked";
    }else{
        form.isAPQP.checked = "";
        form.isPSO10.checked = "";
        form.isESIR.checked = "";
        form.isISIR.checked = "";
    }
}
function selected(){
    form = document.forms[0];
    if(form.isAPQP.checked == "" && form.isPSO10.checked == "" && form.isESIR.checked == "" && form.isISIR.checked == "" ){
        form.chkAll.checked = "checked";
    }else{
        form.chkAll.checked = "";
    }
}

function ViewDoc(oid){
    openView(oid, 870, null, null);
}

function openECO(oid, type){
    var url="";
    if(type=='PROD') {
        url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid="+oid;
    }else {
        url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid="+oid;
    }
    openOtherName(url,"eco","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }
  function openDQM(oid, type){
    var url="/plm/ext/dqm/dqmMainForm.do?type=view&oid="+oid;
    openOtherName(url,"dqm","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }

  function openTry(oid, type){
      
    var url="";
    if(type=='MOLD') {
        url = "/plm/ext/project/trycondition/viewTryMoldForm.do?oid=" + oid;
    }else {
        url = "/plm/ext/project/trycondition/viewTryPressForm.do?oid=" + oid;
    }
    openOtherName(url,"TRY","1024","768","status=no,scrollbars=no,resizable=yes");
    
  }
//-->
</script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>
</head>
<body>
    <form>

        <input type="hidden" name="sortKey" value="<%=sortKey%>"> <input type="hidden" name="dsc" value="<%=dsc%>"> <input
            type="hidden" name="command" value=""> <input type="hidden" name="projectType" value="project"> <input
            type="hidden" name="type" value="normal"> <input type="hidden" name="subAll" value="All"> <input type="hidden"
            name="oid" value="<%=oid%>"> <input type="hidden" name="taskType" value="<%=taskType%>"> <input type="hidden"
            name="popup" value="<%=popup%>">
        <!-- Hidden Value -->

        <!-- //Hidden Value -->
        <!-- title제목 시작 //-->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03073")%><%--프로젝트 산출물 목록--%></td>
										<td align="right"></td>
                                    </tr>
                                </table></td>
                            <td>&nbsp;</td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td>
                                                        <!--  <a href="javaScript:excelDown()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a>-->
                                                    </td>
                                                </tr>
                                            </table></td>
                                        <td width="10"></td>
                                        <td align="right"><input type="checkbox" name="chkAll" onclick="javascript:checkAll();"
                                            <%if (chkAll.length() > 0) {%> checked <%}%>>전체</input> <input type="checkbox" name="isAPQP"
                                            onclick="javascript:selected();" <%if (isAPQP.length() > 0) {%> checked <%}%>>APQP</input> <input
                                            type="checkbox" name="isPSO10" onclick="javascript:selected();" <%if (isPSO10.length() > 0) {%>
                                            checked <%}%>>PSO12단계</input> <input type="checkbox" name="isESIR"
                                            onclick="javascript:selected();" <%if (isESIR.length() > 0) {%> checked <%}%>>ESIR</input> <input
                                            type="checkbox" name="isISIR" onclick="javascript:selected();" <%if (isISIR.length() > 0) {%>
                                            checked <%}%>>ISIR</input></td>
                                        <td width="10"></td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="10"></td>
                                        <td align="right"><select name="perPage" class="fm_jmp" style="width: 80"
                                            onChange="javaScript:search();">
                                                <option value="10" <%="10".equals(perPage) ? "selected" : ""%>>10</option>
                                                <option value="30" <%="30".equals(perPage) ? "selected" : ""%>>30</option>
                                                <option value="50" <%="50".equals(perPage) ? "selected" : ""%>>50</option>
                                                <option value="100" <%="100".equals(perPage) ? "selected" : ""%>>100</option>
                                        </select></td>
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
                        <tr>

                            <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486")%><%--번호--%></td>
                            <td width="200" class="tdblueM"><a href="javaScript:sorting('<%=TemplateTask.TASK_NAME%>');"><%=messageService.getString("e3ps.message.ket_message", "00496")%><%--Task명--%>&nbsp;<%=TemplateTask.TASK_NAME.equals(sortKey) ? imgDsc : ""%></a></td>
                            <td width="55" class="tdblueM"><a href="javaScript:sorting('<%=ProjectOutput.OBJ_TYPE%>');"><%=messageService.getString("e3ps.message.ket_message", "02918")%><%--타입--%>&nbsp;<%=ProjectOutput.OBJ_TYPE.equals(sortKey) ? imgDsc : ""%></a></td>
                            <td width="250" class="tdblueM"><a href="javaScript:sorting('<%=ProjectOutput.OUTPUT_NAME%>');">문서 제목&nbsp;<%=ProjectOutput.OUTPUT_NAME.equals(sortKey) ? imgDsc : ""%></a></td>
                            <!-- <td width="235" class="tdblueM"><a href="javaScript:sorting('<%=ProjectOutput.OUTPUT_LOCATION%>');"><%=messageService.getString("e3ps.message.ket_message", "01424")%><%--문서분류--%><br>&nbsp;<%=ProjectOutput.OUTPUT_LOCATION.equals(sortKey) ? imgDsc : ""%></a></td> -->
                            <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481")%><%--버전--%></td>
                            <td width="85" class="tdblueM"><a href="javaScript:sorting('<%="Master"%>');"><%=messageService.getString("e3ps.message.ket_message", "01205")%><%--담당자--%>&nbsp;<%="Master".equals(sortKey) ? imgDsc : ""%></a></td>
                            <td width="80" class="tdblueM0"><a href="javaScript:sorting('<%="isRegistry"%>');">등록여부&nbsp;<%="isRegistry".equals(sortKey) ? imgDsc : ""%></a></td>
                        </tr>
                      <%
                          int listCount = control.getTopListCount();
                          int count = 0;
                          PagingQueryResult result = control.getResult();
                          String outputNameStr = "";
                          if(result!=null) {
                        	
                            while (result.hasMoreElements()) {
                        		Object[] obj = (Object[]) result.nextElement();
                        		ProjectOutputData data = new ProjectOutputData((ProjectOutput) obj[0]);

                        		ProjectOutput output = (ProjectOutput) obj[0];
                      		    String objType = "";
                      		    if(output.getObjType() != null){
                      		      objType = output.getObjType();
                      		    }

                        		E3PSTaskData taskData = new E3PSTaskData((E3PSTask) data.task);
                        		if("Gate".equals(taskData.tasktype) ) continue;    //Gate Task인 경우 산출물을 보여주지 않음.
                        		
                        		WTUser masterUser = taskData.getTaskMaster();
                        		E3PSProject project = taskData.e3psProject;
                        		String stateStr = "";

                        		String version = "";
                        		String lastVersion = "";
                        		String versionOid = "";
                        		String lastVersionOid = "";
                        		String outputUser = "";
                        		boolean isEtc = "ETC".equals(output.getObjType());
                                KETDqmRaise dqmRaiseObj = null;
                                KETDqmHeader dqmHeaderObj = null;

                        		if (!isEtc && data.isRegistry) {
                        		    
                      		        if("ECO".equals(objType)) {
                      		            KETProdChangeOrder prodChangeOrderObj = null;
                      		            KETMoldChangeOrder moldChangeOrderObj = null;
                      		            QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);
                      		        
                      		            while (qr.hasMoreElements()) {
                      		                prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
                      		            }
                      		        
                      		            qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);
                      		        
                      		            while (qr.hasMoreElements()) {
                      		                moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
                      		            }
                      		            
                      		            if(prodChangeOrderObj!=null) {
                                            version = "";
                                            versionOid = CommonUtil.getOIDString(prodChangeOrderObj);
                                            outputUser = data.changeOrder.getCreatorFullName();
                                            lastVersion = "";
                                            lastVersionOid = CommonUtil.getOIDString(prodChangeOrderObj);
                                            outputNameStr = prodChangeOrderObj.getEcoName();

                      		            }else if(moldChangeOrderObj!=null) {
                                            version = "";
                                            versionOid = CommonUtil.getOIDString(moldChangeOrderObj);
                                            outputUser = data.changeOrder.getCreatorFullName();
                                            lastVersion = "";
                                            lastVersionOid = CommonUtil.getOIDString(moldChangeOrderObj);
                                            outputNameStr = moldChangeOrderObj.getEcoName();
                      		            }
                      		        }else if("TRY".equals(objType)) {
                      		            
                      		            KETTryMold moldTryConditionObj = null;
                      		            KETTryPress pressTryConditionObj = null;
                      		            
                      		            QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);
                      		        
                      		            while (qr.hasMoreElements()) {
                      		                moldTryConditionObj = (KETTryMold) qr.nextElement();
                      		            }
                      		        
                      		            qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);
                      		            
                      		            while (qr.hasMoreElements()) {
                      		                pressTryConditionObj = (KETTryPress) qr.nextElement();
                      		            }
                      		            if(moldTryConditionObj!=null) {
                      		                version = moldTryConditionObj.getVersionDisplayIdentifier().toString();
                                            versionOid = CommonUtil.getOIDString(data.tryCondition);
                                            outputUser = data.tryCondition.getCreatorFullName();
                                            lastVersion = moldTryConditionObj.getVersionDisplayIdentifier().toString();
                                            lastVersionOid = CommonUtil.getOIDString(data.tryCondition);
                      		            }else if(pressTryConditionObj!=null) {
                      		                
                                            version = pressTryConditionObj.getVersionDisplayIdentifier().toString();
                                            versionOid = CommonUtil.getOIDString(data.tryCondition);
                                            outputUser = data.tryCondition.getCreatorFullName();
                                            lastVersion = pressTryConditionObj.getVersionDisplayIdentifier().toString();
                                            lastVersionOid = CommonUtil.getOIDString(data.tryCondition);

                      		            }
                                        outputNameStr = data.tryCondition.getName();
                      		            
                      		        }else if("QLP".equals(objType)) {
                      		              

                      		            ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service.getOutputQLP(output);
                      		            String raiseOidStr =  dqmDto.getRaiseOid();
                      		            dqmRaiseObj = (raiseOidStr==null || "".equals(raiseOidStr))?null:(KETDqmRaise)CommonUtil.getObject(raiseOidStr);
                      		            dqmHeaderObj = (dqmDto==null || "".equals(dqmDto.getOid()))?null:(KETDqmHeader)CommonUtil.getObject(dqmDto.getOid());
                      		            
                      		            if(dqmDto!=null) {
											version = CommonUtil.getOIDString(dqmRaiseObj);
											versionOid = CommonUtil.getOIDString(dqmRaiseObj);
											outputUser = dqmRaiseObj.getCreatorFullName();
											lastVersion = CommonUtil.getOIDString(dqmRaiseObj);
											lastVersionOid = CommonUtil.getOIDString(dqmRaiseObj);
										}
		                      		    outputNameStr = dqmHeaderObj.getProblem();
                      		            
                      		        }else {    //문서, 도면인 경우
	                        		    version = data.currentDocument.getVersionDisplayIdentifier().toString();
	                        		    versionOid = CommonUtil.getOIDString(data.currentDocument);
	                        		    if (data.currentDocument != null) {
		                        			outputUser = data.currentDocument.getCreatorFullName();
	                        		    }
	                        		    lastVersion = data.LastDocument.getVersionDisplayIdentifier().toString();
	                        		    lastVersionOid = CommonUtil.getOIDString(data.LastDocument);
                      		        }
                        		}
                        		
                        		if(StringUtil.isEmpty(outputNameStr)) outputNameStr = data.name; 
                        		//(data.docStateStr).equals("승인됨");
                        		if (data.isRegistry) {
                        		    stateStr = "등록";
                        		} else {
                        		    stateStr = "등록전";
                        		}
                        		
                        		String docName = data.name;
                        %>
                        <tr>
                            <td width="50" class="tdwhiteL text-center"><%=listCount - count++%></td>
                            <td width="200" class="tdwhiteL" title="<%=taskData.taskName%>"><div
                                    style="width: 190; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                    <nobr>
                                        <a href="#" onclick="viewTask('<%=CommonUtil.getOIDString(data.task)%>')"><%=taskData.taskName%></a>
                                    </nobr>
                                </div></td>
                            <td width="55" class="tdwhiteL text-center"><%=("QLP".equals(objType))?"품질":data.objType%></td>
                            <td width="250" class="tdwhiteL" title="<%=docName%>">
                                <%
                                    if (data.currentDocument != null) {
                                        if(data.currentDocument instanceof KETProjectDocument){
                                            docName = ((KETProjectDocument) data.currentDocument).getTitle();
                                        }else if(data.currentDocument instanceof EPMDocument){
                                            docName = ((EPMDocument) data.currentDocument).getName();
                                        }
                                %>
                                <div style="width: 240; border: 0; padding: 0; margin: 0; text-overflow: ellipsis; overflow: hidden;">
                                    <nobr>
                                        <a href="javascript:openViewProject('<%=CommonUtil.getOIDString(data.currentDocument)%>')">&nbsp;<%=docName%></a>
                                    </nobr>
                                </div> 
                                <%
								     } else  {
								 
									     if (data.isRegistry) {
										     if(objType.equals("ETC")){
									    %> 
									          <a href="javascript:openCompleteReson('<%=CommonUtil.getOIDString(output)%>')">&nbsp;<%=docName%></a> 
	
									      <%      
									          }else if(objType.equals("ECO") && data.changeOrder!=null  & data.changeOrder instanceof KETProdChangeOrder){  %>
									          <a href="#" onClick="javascript:openECO('<%=data.changeOrder%>', 'PROD');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
									      <%      
									          }else if(objType.equals("ECO") && data.changeOrder!=null  & data.changeOrder instanceof KETMoldChangeOrder){  %>
									          <a href="#" onClick="javascript:openECO('<%=data.changeOrder%>', 'MOLD');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
									      <%      
									          }else if(objType.equals("TRY") && data.tryCondition!=null && data.tryCondition  instanceof KETTryMold) {  %>
									          <a href="#" onClick="javascript:openView('<%=data.tryCondition%>');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
									      <%      
									          }else if(objType.equals("TRY") && data.tryCondition!=null && data.tryCondition  instanceof KETTryPress) {  %>
									          <a href="#" onClick="javascript:openView('<%=data.tryCondition%>');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div> </a>
									          
									      <%      
									          }else if("QLP".equals(objType)){ 
									          
                                                  if(dqmHeaderObj==null) {  %>
                                              <div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr%></nobr></div>
                                          <%      }else {%>
                                              <a href="#" onClick="javascript:openDQM('<%=CommonUtil.getOIDString(dqmHeaderObj)%>');"><div class="ellipsis" style="width:100%;"><nobr><%=outputNameStr %></nobr></div> </a>
                                          <%      } 
									          } 
									       }else {
	                               %>
	                               
	                               &nbsp;<%= docName %> 
	                               <%       } 
									     }
	                               %>
                            </td>
                            <!--
          <%//if(data.objType.equals("문서")){ %>
          <td width="235" class="tdwhiteL" title="<%//=StringUtil.checkNull( data.location)%>"><div style="width:180;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                            &nbsp;<%//=StringUtil.checkNull( data.location)%>
                            </nobr>
          </td>
          <%//}else{ %>
          <td width="235" class="tdwhiteL" title=""><div style="width:180;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
          <%//if(data.document != null && project.getPjtType() == 3){%><a href="#" onClick="javascript:doViewEpm('<%//=project.getPjtNo()%>')">[관련도면]</a><%//}%>
                &nbsp;
          </td>

          <%//} %> -->

                            <%if(isEtc || !data.isRegistry || "QLP".equals(objType) || "TRY".equals(objType) || "ECO".equals(objType)){
              //=(masterUser != null)? masterUser.getFullName() : "&nbsp;";
          %>
                            <td width="70" class="tdwhiteL text-center">-</td>
                            <%}else{ %>
                            <td width="70" class="tdwhiteL text-center"><a href="#" onClick="javascript:ViewDoc('<%=versionOid%>')"><font
                                    color="blue"><%=version%></font></a>/<a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font
                                    color="blue"><%=lastVersion%></font></a></td>
                            <%} %>
                            <td width="85" class="tdwhiteL text-center">&nbsp;<%=outputUser %></td>
                            <td width="80" class="tdwhiteL0 text-center"><%=stateStr%></td>
                        </tr>
                        <%
                }
            }
            if(control.getTotalCount() == 0) {
        %>
                        <tr>
                            <td class='tdwhiteL0' align='center' colspan='10'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>
                            </td>
                        </tr>
                        <%  }  %>

                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table width=98% align=center>
                        <tr>
                            <td width=100%>
                                <%control.setPostMethod();%> <%@include file="/jsp/project/inc_page_project.jsp"%>
                            </td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
