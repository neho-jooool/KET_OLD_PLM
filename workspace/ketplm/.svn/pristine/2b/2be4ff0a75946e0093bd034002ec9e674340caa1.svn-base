<%@page import="wt.fc.Persistable"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.ecm.entity.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.headerLock6 {
  position: relative;
  top:expression(document.getElementById("div_scroll6").scrollTop);
  z-index:99;
 }
</style>
<script type="text/javascript">
$(document).ready(function(){
    // Calener field 재설정
    CalendarUtil.dateInputFormat('subContractorApprovalDate');

    //고객처 suggest
    SuggestUtil.bind('CUSTOMER', 'subContractorTxt', 'someOid');
    
})
</script>
<script type="text/javascript">
<% /* 저장 */ %>
function doSave4Ecn()
{
	
  var refDocStr = setDocList();
  document.forms[0].doc_list.value = refDocStr;
  
  
  var form = document.forms[0];

  document.forms[0].isChanged.value = 'N';
  document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
  document.forms[0].cmd.value='ModifyNotOwner';
  document.forms[0].target='download';
  disabledAllBtn();
  showProcessing();
  document.forms[0].submit();
  
}

function lfn_feedback_reStart(msg){
    alert(msg);
    
    opener.location.reload();
    
}

<% /* 작업완료 */ %>
function doComplete4Ecn()
{
    if( confirm("<%=messageService.getString("e3ps.message.ket_message", "04410") %><%--정말로 작업완료하시겠습니까? 작업완료 후 MyTodo에서 사라집니다.--%>") )
    { 
    
        document.forms[0].action= '/plm/servlet/e3ps/ProdEcoServlet';
        document.forms[0].cmd.value='Complete';
        document.forms[0].budgetYn.value='Y';
        document.forms[0].activityType.value='4';
        //document.forms[0].target='_self';
        document.forms[0].target='download';
        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
        
    }
}

//대상검색 팝업 호출
function popupRelDoc( pos ) {
    
    showProcessing();
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=authorStatus^ALL";
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=730px; dialogHeight:530px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        hideProcessing();
        return;
    }

    checkDocAjax(attache, pos);
}

function checkDocAjax(objArr, inx){
    var trArr = objArr[0];
    var url = "/plm/jsp/ecm/CheckDocAjaxAction.jsp?oid="+trArr[0]+"&no="+trArr[1]+"&name="+encodeURIComponent(trArr[2])+"&ver="+trArr[3]+"&inx=" + inx;
    callServer(url, checkDocResult);
}

function checkDocResult(req) {
    var xmlDoc = req.responseXML;
    
    showElements = xmlDoc.selectNodes("//data_info");
    var l_flag = showElements[0].getElementsByTagName("l_flag");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_no = showElements[0].getElementsByTagName("l_no");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_ver = showElements[0].getElementsByTagName("l_ver");
    var l_inx = showElements[0].getElementsByTagName("l_inx");
    var flag = decodeURIComponent(l_flag[0].text);
    var oid = decodeURIComponent(l_oid[0].text);
    var no = decodeURIComponent(l_no[0].text);
    var name = decodeURIComponent(l_name[0].text);
    var ver = decodeURIComponent(l_ver[0].text);
    var inx = decodeURIComponent(l_inx[0].text);
    
    
    hideProcessing();
    if ( flag == 'true'){
      	
      alert("<%=messageService.getString("e3ps.message.ket_message", "01862") %><%--설계변경이 진행중인 문서입니다--%>");
      return;
        
    }
    
    setRelDoc2(oid, no, name, ver, inx);
}

function setRelDoc2(oid, no, name, ver, inx)
{
    //alert(oid +', '+ no +', '+ name +', '+ ver +', '+ inx);
    if(oid.length == 0)
    {
      return;
    }
    
    var form = document.forms[0];
    
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return;
    
    var chkPostActLength = chkPostAct.length;
     if( chkPostActLength > 1 )
     {
       if( form.relObjOid[inx].value != oid )
       {
         if( form.deleteRelDocList.value == ''  )
         {
             form.deleteRelDocList.value = form.relEcaLinkOid[inx].value;
         }
         else
         {
             form.deleteRelDocList.value +="*" + form.relEcaLinkOid[inx].value;
         }
       }
    
      form.relObjOid[inx].value = oid;
      form.relEcaDocNo[inx].value =no;
      form.relEcaDocName[inx].value = name;
      form.relObjPreRev[inx].value = ver;
      form.relObjAfterRev[inx].value = '';
      
      //var relObjPreRev = $($('[name=relObjPreRev]').get(inx)).val();
      $($($($('[name=relObjPreRev]').get(inx))).prev()).html(ver);
      $($($($('[name=relObjAfterRev]').get(inx))).prev()).html('');
      
      form.viewDocOid[inx].value = oid;
      
    }
    else
    {
      if( form.relObjOid.value != oid )
      {
        if( form.deleteRelDocList.value == ''  )
         {
             form.deleteRelDocList.value = form.relEcaLinkOid.value;
         }
         else
         {
             form.deleteRelDocList.value +="*" + form.relEcaLinkOid.value;
         }
       }
    
      form.relObjOid.value = oid;
      form.relEcaDocNo.value =no;
      form.relEcaDocName.value = name;
      form.relObjPreRev.value = ver;
      form.relObjAfterRev.value = '';
      
      $($('[name=relObjPreRev]').prev()).html(ver);
      $($('[name=relObjAfterRev]').prev()).html('');
      
      form.viewDocOid.value = oid;
    }
}

// 선택 문서 삭제
function clearRelDoc(pos) {
  var form = document.forms[0];

  var chkPostAct = document.getElementsByName("chkPostAct");
  if(chkPostAct == null || typeof chkPostAct == 'undefined') return;

  var chkPostActLength = chkPostAct.length;
  if(chkPostActLength > 1) {
      if(form.relObjOid[pos].value != '') {
          if(form.deleteRelDocList.value == '') {
        	  form.deleteRelDocList.value = form.relEcaLinkOid[pos].value;
          } else {
        	  form.deleteRelDocList.value +="*" + form.relEcaLinkOid[pos].value;
          }
          
          
          form.relObjOid[pos].value = "";
          form.relEcaDocNo[pos].value = "";
          form.relEcaDocName[pos].value = "";
          form.relObjPreRev[pos].value = "";
          form.relObjAfterRev[pos].value = "";
          
          $($($($('[name=relObjPreRev]').get(pos))).prev()).html('');
          $($($($('[name=relObjAfterRev]').get(pos))).prev()).html('');
          
          form.viewDocOid[pos].value = "";
         
      }
  } else {
      if(form.relObjOid.value != '') {
          if(form.deleteRelDocList.value == '') {
              form.deleteRelDocList.value = form.relEcaLinkOid.value;
          } else {
              form.deleteRelDocList.value +="*" + form.relEcaLinkOid.value;
          }
         
          
          form.relObjOid.value = "";
          form.relEcaDocNo.value = "";
          form.relEcaDocName.value = "";
          form.relObjPreRev.value = "";
          form.relObjAfterRev.value = "";
          
          $($('[name=relObjPreRev]').prev()).html('');
          $($('[name=relObjAfterRev]').prev()).html('');
          
          form.viewDocOid.value = "";
         
      }	  
  }

}

//변경문서 팝업 호출
function popupRelDoc2( flag, docNo, pos) {
    
    //var inx = eval('relDocTable').clickedRowIndex;
    var form = document.forms[0];
    
    var docNo = "";
    
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return;
    
    var chkPostActLength = chkPostAct.length;

    if( chkPostActLength > 1 ){
        docNo = form.relEcaDocNo[pos].value;
    }else{
        docNo = form.relEcaDocNo.value;
    }
    
     
    showProcessing();
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=isReview^Y&obj=docNo^"+docNo;
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:540px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        hideProcessing();
        return;
    }
    
    
    setRelDoc(attache, pos, flag);
}

//변경문서 팝업 리턴 세팅
function setRelDoc(objArr, inx, flag)
{
    //alert(oid +', '+ no +', '+ name +', '+ ver +', '+ inx);
    
    if(objArr.length == 0)
    {
      return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];
    
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return;
    
    var chkPostActLength = chkPostAct.length;
    
    // 로직으로는 flag 가 'p' 일 수 없다.
    if( flag == 'p') alert('ToDoEcnForm.jsp, setRelDoc('+ oid +', '+ no +', '+ name +', '+ ver +', '+ inx +')');
    /* 
    if( flag == 'p' )
    {
       if( chkPostActLength > 1 )
       {
         if( form.relObjOid[inx].value != trArr[0] )
         {
           if( form.deleteRelDocList.value == ''  )
           {
               form.deleteRelDocList.value = form.relEcaLinkOid[inx].value;
           }
           else
           {
               form.deleteRelDocList.value +="*" + form.relEcaLinkOid[inx].value;
           }
         }
    
        form.relObjOid[inx].value = trArr[0];
        form.relEcaDocNo[inx].value =trArr[1];
        form.relEcaDocName[inx].value = trArr[2];
        form.relObjPreRev[inx].value = trArr[3];
      }
      else
      {
        if( form.relObjOid.value != trArr[0] )
        {
          if( form.deleteRelDocList.value == ''  )
           {
               form.deleteRelDocList.value = form.relEcaLinkOid.value;
           }
           else
           {
               form.deleteRelDocList.value +="*" + form.relEcaLinkOid.value;
           }
         }
    
        form.relObjOid.value = trArr[0];
        form.relEcaDocNo.value =trArr[1];
        form.relEcaDocName.value = trArr[2];
        form.relObjPreRev.value = trArr[3];
      }
    }
    else 
    */  
    if( flag == 'a' )
    {
      if( chkPostActLength > 1 )
      {
        if( form.relEcaDocNo[inx].value == trArr[1] && form.relObjPreRev[inx].value < trArr[3] )
        {
          form.relObjAfterRev[inx].value = trArr[3];
        }
        else
        {
          alert("<%=messageService.getString("e3ps.message.ket_message", "00580") %><%--같은 문서의 최신버전이 아닙니다--%>");
        }
      }
      else
      {
        if( form.relEcaDocNo.value == trArr[1] && form.relObjPreRev.value < trArr[3] )
        {
          form.relObjAfterRev.value = trArr[3];
        }
        else
        {
          alert("<%=messageService.getString("e3ps.message.ket_message", "00580") %><%--같은 문서의 최신버전이 아닙니다--%>");
        }
      }
    }
    
    
    hideProcessing();
    
}

//문서상세조회 팝업
function viewRelDoc(projectDocumentOid) {
  if(projectDocumentOid == '') {
      
      alert("<%=messageService.getString("e3ps.message.ket_message", "04170") %><%--해당 버전의 문서가 존재하지 않습니다.--%>");
      return;
      
  }
  
  //var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+projectDocumentOid+"&buttenView=T"+"&isRefresh=N";
  var url = "/plm/jsp/dms/ViewDocument.jsp?oid="+projectDocumentOid+"&buttenView=T"+"&isRefresh=N";
  getOpenWindow2(url,"","1024","768");
}


//문서개정
function reviseRelDoc2(pos) {
    //alert(pos);
    var form = document.forms[0];
        
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return;
    var chkPostActLength = chkPostAct.length;
    
    var has2relDoc = document.getElementsByName("has2relDoc");
    
    
    // 일괄개정
    if(pos == null || typeof pos == 'undefined') {
    	if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04240") %><%--문서를 일괄개정하시겠습니까?--%>') ) return;
        
    	if(chkPostActLength == 1) {
    		if(form.has2relDoc.value == 'Y') {
                if(form.relObjOid.value != '') {
                	if(form.relObjAfterRev.value == '') form.chkPostAct.checked = true;
                	else  form.chkPostAct.checked = false;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04210") %><%--개정할 문서가 존재하지 않습니다.--%>');
                    form.chkPostAct.checked = false;
                    return;
                    
                }
            } else {
                form.chkPostAct.checked = false;
                
            }
    		
        } else {
	    	for(var i=0; i < chkPostActLength; i++) {
	    		if(form.has2relDoc[i].value == 'Y') {
	    		    if(form.relObjOid[i].value != '') {
	    		    	if(form.relObjAfterRev[i].value == '') form.chkPostAct[i].checked = true;
	    		    	form.chkPostAct[i].checked = true;
	    		    } else {
	    		    	
	    		    	alert('<%=messageService.getString("e3ps.message.ket_message", "04210") %><%--개정할 문서가 존재하지 않습니다.--%>');
	    		    	for(var i=0; i < chkPostActLength; i++) {
	    		            form.chkPostAct[i].checked = false;
	    		        }
	                    return;
	                    
	    		    }
	    		} else {
	    			form.chkPostAct[i].checked = false;
	                
	    		}
	    		
	        }
	    	
        }
    	
    } else {    
    	if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "03304") %><%--문서를 개정하시겠습니까?--%>') ) return;
                
	    if(chkPostActLength == 1) {
	    	if(form.has2relDoc.value == 'Y') {
                if(form.relObjOid.value != '') {
                    if(form.relObjAfterRev.value == '') form.chkPostAct.checked = true;
                    else  form.chkPostAct.checked = false;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04210") %><%--개정할 문서가 존재하지 않습니다.--%>');
                    form.chkPostAct.checked = false;
                    return;
                    
                }
            } else {
                form.chkPostAct.checked = false;
                
            }
	    	
	    } else {
	    	for(var i=0; i < chkPostActLength; i++) {
	    		if(pos == i) {
	    			if(form.has2relDoc[i].value == 'Y') {
	                    if(form.relObjOid[i].value != '') {
	                    	if(form.relObjAfterRev[i].value == '') form.chkPostAct[i].checked = true;
	                        else  form.chkPostAct[i].checked = false;
	                    } else {
	                        
	                        alert('<%=messageService.getString("e3ps.message.ket_message", "04210") %><%--개정할 문서가 존재하지 않습니다.--%>');
	                        for(var i=0; i < chkPostActLength; i++) {
	                            form.chkPostAct[i].checked = false;
	                        }
	                        return;
	                        
	                    }
	                } else {
	                    form.chkPostAct[i].checked = false;
	                    
	                }
	    	        		
	    		} else {
	    			form.chkPostAct[i].checked = false;
	    	        
	    		}
	    	}
	        
	    }
	  
    }
    
    doRevise('ReviseRelDoc');
    
    // 에러시 Refresh를 하지 않을 경우를 대비해 초기화하여야 한다.
    if(chkPostActLength == 1) {
        form.chkPostAct.checked = false;
    } else {
        for(var i=0; i < chkPostActLength; i++) {
            form.chkPostAct[i].checked = false;
        }
    }
    
}

<% /* @deprecated */ %>
function reviseRelDoc() {
    var body = document.getElementById("relEpmTable");
    if (body.rows.length < 3) return;
    var formNameStr = "document.forms[0].";
    var form = document.forms[0];
    var objChecks = eval(formNameStr + "chkSelectRelEpm");
    
    var listVal = "";
    
    var chkComp = document.getElementsByName("chkSelectRelEpm");
    var afterRev = document.getElementsByName("relEcaEpmAfterRev");
    
    var rtn = false;
    for( var t=0; t <chkComp.length; t++)
    {
      if( chkComp[t].checked && afterRev[t].value == '' )
      {
        rtn = true;
      }
    }
    
    var size = body.rows.length - 2;
    var selCnt = 0;
    var curRow = 0;
    if( rtn )
    {
       if( form.isChanged.value == 'N' )
       {
        if(size <= 1) {
          if (objChecks.checked || objChecks[0].checked) {
            if(objChecks.checked &&  form.relEcaEpmAfterRev.value == '' ) {
              listVal = form.relObjOid.value;
              form.relEcaEpmDocReviseYn.value = "Y";
            } else if(objChecks[0].checked &&  form.relEcaEpmAfterRev[0].value == '' ) {
              listVal = form.relObjOid[0].value;
              form.relEcaEpmDocReviseYn[0].value = "Y";
            }
            selCnt++;
          }
        } else {
          for (var i = body.rows.length; i > 2; i--) {
            curRow = i - 3;
            if (objChecks[curRow].checked &&  form.relEcaEpmAfterRev[curRow].value == '' ) {
              if(selCnt < 1) {
                listVal = form.relObjOid[curRow].value;
                form.relEcaEpmDocReviseYn[curRow].value = "Y";
              } else {
                listVal += "*" + form.relObjOid[curRow].value;
                form.relEcaEpmDocReviseYn[curRow].value = "Y";
              }
              selCnt++;
            }
          }
        }
      }
      else
      {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02456") %><%--저장 후 개정을 수행하세요--%>');
        return;
      }
    }
    //form.reviseRelBomList.value = "";
    if(selCnt > 0) {
    //  form.reviseRelBomList.value = "listVal";
    //  alert("개정:" + listVal);
      doRevise('ReviseRelDoc');
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00692") %><%--개정할 도면이 없습니다--%>");
    }
}

//문서 개정취소
function cancelRevRelDoc2(pos) {
    
    var form = document.forms[0];
        
    var chkPostAct = document.getElementsByName("chkPostAct");
    if(chkPostAct == null || typeof chkPostAct == 'undefined') return;
    var chkPostActLength = chkPostAct.length;

    var has2relDoc = document.getElementsByName("has2relDoc");
    
    
    // 일괄개정취소
    if(pos == null || typeof pos == 'undefined') {
    	if (!confirm('<%=messageService.getString("e3ps.message.ket_message", "04250") %><%--문서를 일괄개정취소하시겠습니까?--%>') ) return;
        
        if(chkPostActLength == 1) {
            if(form.has2relDoc.value == 'Y') {
                if(form.relObjOid.value != '') {
                	if(form.relObjAfterRev.value != '') form.chkPostAct.checked = true;
                	else form.chkPostAct.checked = false;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04190") %><%--개정취소할 문서가 존재하지 않습니다.--%>');
                    form.chkPostAct.checked = false;
                    return;
                    
                }
            } else {
                form.chkPostAct.checked = false;
                
            }
            
        } else {
            for(var i=0; i < chkPostActLength; i++) {
                if(form.has2relDoc[i].value == 'Y') {
                    if(form.relObjOid[i].value != '') {
                    	if(form.relObjAfterRev[i].value != '') form.chkPostAct[i].checked = true;
                        else form.chkPostAct[i].checked = false;
                    } else {
                        
                        alert('<%=messageService.getString("e3ps.message.ket_message", "04190") %><%--개정취소할 문서가 존재하지 않습니다.--%>');
                        for(var i=0; i < chkPostActLength; i++) {
                            form.chkPostAct[i].checked = false;
                        }
                        return;
                        
                    }
                } else {
                    form.chkPostAct[i].checked = false;
                    
                }
                
            }
            
        }
            	
    } else {    
    	if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03286") %><%--개정을 취소하시겠습니까?--%>")) return;
        
        if(chkPostActLength == 1) {
        	if(form.has2relDoc.value == 'Y') {
                if(form.afterProjectDocumentOid.value != '') {
                	if(form.relObjAfterRev.value != '') form.chkPostAct.checked = true;
                    else form.chkPostAct.checked = false;
                } else {
                    
                    alert('<%=messageService.getString("e3ps.message.ket_message", "04190") %><%--개정취소할 문서가 존재하지 않습니다.--%>');
                    form.chkPostAct.checked = false;
                    return;
                    
                }
            } else {
                form.chkPostAct.checked = false;
                
            }
                
        } else {
        	for(var i=0; i < chkPostActLength; i++) {
                if(pos == i) {
                    if(form.has2relDoc[i].value == 'Y') {
                        if(form.afterProjectDocumentOid[i].value != '') {
                        	if(form.relObjAfterRev[i].value != '') form.chkPostAct[i].checked = true;
                            else form.chkPostAct[i].checked = false;
                        } else {
                            
                            alert('<%=messageService.getString("e3ps.message.ket_message", "04190") %><%--개정취소할 문서가 존재하지 않습니다.--%>');
                            for(var i=0; i < chkPostActLength; i++) {
                                form.chkPostAct[i].checked = false;
                            }
                            return;
                            
                        }
                    } else {
                        form.chkPostAct[i].checked = false;
                        
                    }
                            
                } else {
                    form.chkPostAct[i].checked = false;
                    
                }
            }
            
        }
      
    }
        
    doRevise('CancelReviseRelDoc');
    
    // 에러시 Refresh를 하지 않을 경우를 대비해 초기화하여야 한다.
    if(chkPostActLength == 1) {
        form.chkPostAct.checked = false;
    } else {
        for(var i=0; i < chkPostActLength; i++) {
            form.chkPostAct[i].checked = false;
        }
    }

}

<% /* @deprecated */ %>
function cancelRevRelDoc() {
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03286") %><%--개정을 취소하시겠습니까?--%>")) return;
    
    var body = document.getElementById("relEpmTable");
    if (body.rows.length < 3) return;
    var formNameStr = "document.forms[0].";
    var form = document.forms[0];
    var objChecks = eval(formNameStr + "chkSelectRelEpm");
    
    var chkComp = document.getElementsByName("chkSelectRelEpm");
    var afterRev = document.getElementsByName("relEcaEpmAfterRev");
    
    var rtn = false;
    for( var t=0; t <chkComp.length; t++)
    {
      if(chkComp[t].checked && afterRev[t].value != '' )
      {
        rtn = true;
      }
    }
    var listVal = "";
    
    var size = body.rows.length - 2;
    var selCnt = 0;
    var curRow = 0;
    if( rtn )
    {
       if( form.isChanged.value == 'N' )
       {
        if(size <= 1) {
          if (objChecks.checked || objChecks[0].checked) {
            if(objChecks.checked) {
              if(form.relObjPreRev.value < form.relEcaEpmAfterRev.value) {
                form.relEcaEpmDocCancelRevYn.value = "Y";
                selCnt++;
              }
            } else if(objChecks[0].checked) {
              if(form.relObjPreRev[0].value < form.relEcaEpmAfterRev[0].value) {
                form.relEcaEpmDocCancelRevYn[0].value = "Y";
                selCnt++;
              }
            }
          }
        } else {
          for (var i = body.rows.length; i > 2; i--) {
            curRow = i - 3;
            if (objChecks[curRow].checked) {
              if(form.relObjPreRev[curRow].value < form.relEcaEpmAfterRev[curRow].value) {
                form.relEcaEpmDocCancelRevYn[curRow].value = "Y";
                selCnt++;
              }
            }
          }
        }
      }
      else
      {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02457") %><%--저장 후 개정취소를 수행하세요--%>');
        return;
      }
    }
    if(selCnt > 0) {
      doRevise('CancelReviseRelDoc');
    } else {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00687") %><%--개정 취소할 도면이 없습니다--%>");
    }
}


//문서변경 팝업
function changeEpmDoc(oid) {
var form = document.forms[0];
var pos = relEpmTable.clickedRowIndex - 2;
var targetTable = document.getElementById("relEpmTable");
var tableRows = targetTable.rows.length;

var isMDrawing = '<%=isMDrawing%>';
var url = "/plm/jsp/ecm/ModalFormScroll.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&obj=oid^" + oid+"&obj=mDrawing^"+isMDrawing+"&obj=isECM^"+"Y";
var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=1024,height=768";
window.open(url,'CreateEPMDocument',opts);
/* arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=auto; dialogWidth=1024px; dialogHeight:500px; center:yes");
if(typeof arr == "undefined" || arr == null) {
  return;
}
if(arr == false) {
  //alert("도면변경이 실패했습니다.")
} else {
  //alert("도면변경이 완료되었습니다.")
  if(tableRows > 3){
    form.relEcaEpmChangeYn[pos].value = "Y";
  } else {
    form.relEcaEpmChangeYn.value = "Y";
  }
  doRevise( "EpmDocChanged" );
} */
}

//ECN 정보 컨버팅
function setDocList()
{
    var docActFlag = document.getElementsByName("docActFlag");
    if(docActFlag == null || typeof docActFlag == 'undefined') return "";
    
    var refDocStr = "";
    var docActFlagLength = (docActFlag != null) ? docActFlag.length : 0;
    if(docActFlagLength == 1) {
        
        if( eval("document.forms[0].relEcaEpmDocType").value == '' )
        {
          refDocStr += '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relEcaEpmDocType").value;
        }
        refDocStr += "|";
        
        if( eval("document.forms[0].relObjOid").value == '' )
        {
          refDocStr += '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relObjOid").value;
        }
        refDocStr += "|";
        
        if( eval("document.forms[0].relObjPreRev").value == '' )
        {
          refDocStr += '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relObjPreRev").value;
        }
        refDocStr += "|";
            
        // 상세구분(내용)
        if( eval("document.forms[0].relEcaDocOtherTypeDesc").value  == '' )
        {
          refDocStr+= '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relEcaDocOtherTypeDesc").value;
        }
        refDocStr += "|";

        if( eval("document.forms[0].relObjAfterRev").value == '' )
        {
          refDocStr += '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relObjAfterRev").value;
        }
        refDocStr += "|";

        if( eval("document.forms[0].relEcaWorkerOid").value == '' )
        {
          refDocStr += '-';
        }
        else
        {
          refDocStr += eval("document.forms[0].relEcaWorkerOid").value;
        }
        refDocStr += "|";

        // 타입
        if( eval("document.forms[0].docActFlag").value == '' )
        {
            refDocStr += "-";
        }
        else
        {
            refDocStr += eval("document.forms[0].docActFlag").value;
        }
        refDocStr += "|";
        
        // 사용자 입력 후속업무
        if( eval("document.forms[0].customName").value == '' )
        {
            refDocStr += "-";
        }
        else
        {
            refDocStr += eval("document.forms[0].customName").value;
        }
        refDocStr += "|";
        
        // 완료요청일
        if( eval("document.forms[0].completeRequestDate").value == '' )
        {
            refDocStr += "-";
        }
        else
        {
            refDocStr += eval("document.forms[0].completeRequestDate").value;
        }
        refDocStr += "|";
        
        // KETProdChangeActivity OID
        if( eval("document.forms[0].ecaUniqueKey").value == '' )
        {
            refDocStr += "-";
        }
        else
        {
            refDocStr += eval("document.forms[0].ecaUniqueKey").value;
        }
        
        refDocStr += "↕";
        
    } else {
        
        for( var inx=0; inx < docActFlagLength ; inx++ )
        {
            if( eval("document.forms[0].relEcaEpmDocType["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relEcaEpmDocType["+inx+"]").value;
            }
            refDocStr += "|";
            
            if( eval("document.forms[0].relObjOid["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relObjOid["+inx+"]").value;
            }
            refDocStr += "|";
            
            if( eval("document.forms[0].relObjPreRev["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relObjPreRev["+inx+"]").value;
            }
            refDocStr += "|";
                
            // 상세구분(내용)
            if( eval("document.forms[0].relEcaDocOtherTypeDesc["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relEcaDocOtherTypeDesc["+inx+"]").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relObjAfterRev["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relObjAfterRev["+inx+"]").value;
            }
            refDocStr += "|";
    
            if( eval("document.forms[0].relEcaWorkerOid["+inx+"]").value == '' )
            {
              refDocStr += '-';
            }
            else
            {
              refDocStr += eval("document.forms[0].relEcaWorkerOid["+inx+"]").value;
            }
            refDocStr += "|";
                  
            // 타입
            if( eval("document.forms[0].docActFlag["+inx+"]").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].docActFlag["+inx+"]").value;
            }
            refDocStr += "|";
    
            // 사용자 입력 후속업무
            if( eval("document.forms[0].customName["+inx+"]").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].customName["+inx+"]").value;
            }
            refDocStr += "|";
            
            // 완료요청일
            if( eval("document.forms[0].completeRequestDate["+inx+"]").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].completeRequestDate["+inx+"]").value;
            }
            refDocStr += "|";
            
            // KETProdChangeActivity OID
            if( eval("document.forms[0].ecaUniqueKey["+inx+"]").value == '' )
            {
                refDocStr += "-";
            }
            else
            {
                refDocStr += eval("document.forms[0].ecaUniqueKey["+inx+"]").value;
            }
    
            refDocStr += "↕";
            
        }       
    }    
     
    //alert(refDocStr);
    return refDocStr;
}

function insert120Line(ecaOid) {

    var targetTable = document.getElementById('relEcaExpansion120Table');

    var innerRow = targetTable.insertRow();
    innerRow.height = "27";
    var rowIndex = innerRow.rowIndex - 1;
    
    var innerCell;
    
    for( var k=0 ; k < 3 ; k++ ) {
        innerCell = innerRow.insertCell();
            
        if (k == 0) 
        {
            innerCell.style.width = "20";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><img src=\"/plm/portal/images/b-minus.png\"></a>"
                                + "<input type='hidden' name='ecaOid' value='ecaOid'>"
                                + "<input type='hidden' name='ecaExpansionOid' value=''>"
                                + "<input type='hidden' name='subContractorCode' value=''>"
                                ;
    
        }
        else if(k == 1)
        {
            innerCell.style.width = "*";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<input type='text' name='subContractorTxt' class='txt_field' style='width: 80%;'>"   
                                + "<input type='hidden' name='someOid'>"
                                + "&nbsp;<a href=\"javascript:SearchUtil.selectOneSubContractor('someOid','subContractorTxt');\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>"
                                + "&nbsp;<a href=\"javascript:CommonUtil.deleteValue('someOid','subContractorTxt');\"><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></a>"
                                ;
            
        }
        else if(k == 2)
        {
            innerCell.style.width = "*";
            innerCell.className = "tdwhiteM0";
            innerCell.innerHTML = "<input id='subContractorApprovalDate"+ rowIndex +"' name='subContractorApprovalDate' class='txt_field' style='width: 80px;'/>"
                                + "&nbsp;<img src='/plm/portal/images/icon_delete.gif' border='0' onclick='javascript:CommonUtil.deleteDateValue(\"subContractorApprovalDate\");' style='cursor: hand;'>"
                                ;
        }
    }
    
    
    // Calener field 재설정
    CalendarUtil.dateInputFormat('subContractorApprovalDate');

    //고객처 suggest
    SuggestUtil.bind('CUSTOMER', 'subContractorTxt', 'someOid');
    
}


function popupRelProduct(pos) {
	var url = "/plm/ext/part/classify/actualWeightInputForm.do?eco_oid=<%=ecoHash.get("eco_oid").toString() %>";
    openWindow(url,"","920","650","scrollbars=no,resizable=no,top=200,left=250");
}

function viewEcnWeightHistory() {
    var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=ViewEcnWeightHistory&oid=<%=ecoHash.get("eco_oid").toString() %>";
    openWindow(url,"","600","500","scrollbars=no,resizable=no,top=200,left=250");
}
</script>

<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
  
            <td>
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSave4Ecn();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
               </table>
            </td>
            <td width="5">&nbsp;</td>
            <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doComplete4Ecn();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02438") %><%--작업완료--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
              </table>
            </td>
            <td width="5">&nbsp;</td>
            
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
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03255") %><%--후속변경대상문서--%></td>
    
      <td align="right">
        <%
        boolean hasOneMore = false;
        if( docList != null && docList.size() > 0 )
        {
            int docListLength = docList.size();
            for( int docCnt=0; docCnt < docList.size(); docCnt++ )
            {
        	    Hashtable<String, String> docHash = docList.get(docCnt);
                String activity_type = docHash.get("activity_type");
                if(activity_type == null || activity_type.equals("") || activity_type.equalsIgnoreCase("3")) {
                    hasOneMore = true;
                    break;
                }
            }
        }
        
        if(hasOneMore) {
        %>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseRelDoc2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04220") %><%--일괄개정--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                </table>
            </td>
            <td width="5">&nbsp;</td>
            <td>
                <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelRevRelDoc2();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "04230") %><%--일괄개정취소--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
                </table>
            </td>
            <td width="5">&nbsp;</td>            
          </tr>
        </table>
       <%
       }
       %>  
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

<div id="div_scroll6" class="table_border">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr class="">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <!-- td width="40"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td -->
          <!-- td width="25"  rowspan="2" class="tdblueM"><input type="checkbox" name="allChkPostAct"  onclick="javascript:allCheck( 'chkPostAct', this.checked );"></td -->
          
          <td width="50" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="150" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
          <td width="*" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "내용") %><%--내용--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td width="240" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
          <!-- td width="50"  rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01824") %><%--선택해제--%></td -->
          <td width="90" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="200" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
        </tr>
        <tr>
          <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
          <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
        </tr>
      <!-- /table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable" style=table-layout:fixed -->
<%

int rowIndex = 0;

Hashtable<String, String> docHash = null;

if( docList != null && docList.size() > 0 )
{
    int docListLength = docList.size();
    for( int docCnt=0; docCnt < docList.size(); docCnt++ )
    {
        docHash = docList.get(docCnt);
        String activity_type = docHash.get("activity_type");
        if(activity_type == null || activity_type.equals("") || activity_type.equalsIgnoreCase("3")) {
            String eca_oid =  docHash.get("eca_oid");
            
            
            KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(eca_oid);
            if("WORKCOMPLETED".equals(eca.getActivityStatus())){
                continue;
            }
            if(eca != null) {
                // To-Do 에서 오는 경우로 수신일을 set 한다.
                Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
                if(receiveConfirmedDate == null) {
                    eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
                    
                    PersistenceHelper.manager.save(eca);
                    PersistenceHelper.manager.refresh(eca);
                }
            
            }
            
            
            String obj_type = docHash.get("obj_type");
            String activity_type_name = docHash.get("activity_type_name");
            String eca_obj_oid = docHash.get("eca_obj_oid");
            String doc_type_desc = docHash.get("doc_type_desc");
        	    
            String docActFlag = (activity_type.equals("") || activity_type.equalsIgnoreCase("3")) ? "DOC" : "ACT";
            // 완료요청일
            String completeRequestDate = docHash.get("complete_request_date");
            // 내용(ToDo 에서 사용자 입력 정보)
            String activityBackDesc = StringUtils.stripToEmpty(docHash.get("activity_back_desc"));

            // 이전 버전 문서 OID
            String preProjectDocumentOid = EcmUtil.getDocumentOid(docHash.get("eca_obj_no"), docHash.get("eca_obj_pre_rev"));
            // 이후 버전 문서 OID
            String afterProjectDocumentOid = EcmUtil.getDocumentOid(docHash.get("eca_obj_no"), docHash.get("eca_obj_after_rev"));
            String eca_obj_after_rev = (afterProjectDocumentOid != null && !afterProjectDocumentOid.equals("")) ? docHash.get("eca_obj_after_rev") : "";
%>
		   <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex' >
		     <!-- td width="40" class="tdwhiteM"><%=docListLength-- %></td -->
		     <!-- td width="25" class="tdwhiteM"></td -->
		     <input type="checkbox" name="chkPostAct" value='<%=eca_oid %>' style="display:none;">
		     <td class="tdwhiteM">
		     <select name="activityTemp" class="fm_jmp" style="width: 45">
		     <option value="3" selected>문서</option>
		     <option value="4" >활동</option>
		     </select>
             </td>
		     <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(activity_type_name) %>"><%=activity_type_name %>
		     
		         <%-- 서버에 넘기는 필수 정보 --%>
                 <input type='hidden' name="ecaUniqueKey" value='<%=eca_oid %>'>
		         <input type='hidden' name='relObjOid'  value='<%=eca_obj_oid %>'>
                 <input type='hidden' name='relEcaDocOtherTypeDesc' value='<%=doc_type_desc %>'>
                 <input type='hidden' name='relEcaWorkerOid'  value='<%=docHash.get("worker_id") %>' >
                 <%-- 
                 <input type='hidden' name='relObjPreRev' value='<%=docHash.get("eca_obj_pre_rev") %>'>
                 <input type='hidden' name='relObjAfterRev'  value='<%=eca_obj_after_rev %>'>
                 --%>
                 <input type='hidden' name="docActFlag" value="<%=docActFlag %>">
                 <input type='hidden' name="customName" value="<%=activity_type_name %>">
                 <input type='hidden' name="completeRequestDate" value="<%=completeRequestDate %>">
                 
                 <input type='hidden' name='relDocChangeYn' value='N'>
                 <input type='hidden' name='afterProjectDocumentOid' value='<%=afterProjectDocumentOid %>'>
                 <%-- 서버에 넘기는 필수 정보 --%>
                 
		         <input type='hidden' name='relEcaActivityType' value='<%=activity_type %>'>
		         <input type='hidden' name='relEcaEpmDocType'  value='<%=obj_type %>'>
		         <input type='hidden' name='relEcaLinkOid' value='<%=docHash.get("eca_obj_link_oid")%>'>
		         <input type='hidden' name='viewDocOid' value='<%=preProjectDocumentOid %>'>
		         
		         <input type='hidden' name="activityBackDesc" value="<%=activityBackDesc %>">
                            
		         <%-- 문서를 가지고 있어야 하는지 --%>
                 <input type='hidden' name='has2relDoc' value='Y'>
                 		         
		     </td>
		     <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(doc_type_desc) %>"><div class="ellipsis" style="width:170px;"><nobr><%=doc_type_desc %></nobr></div></td>
		     <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(docHash.get("eca_obj_no")) %>"><input type="text" name="relEcaDocNo" value='<%=docHash.get("eca_obj_no") %>' class="txt_fieldRO" style="width:90px;" readonly></td>
		     <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(docHash.get("eca_obj_name")) %>">
		       <input type="text" name="relEcaDocName" value="<%=docHash.get("eca_obj_name") %>" class="txt_fieldRO" style="width:180px;" readonly>
		       <a href="javascript:popupRelDoc(<%=rowIndex %>);"><img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc('<%=rowIndex %>');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
             </td>          
             <td class="tdwhiteM"><a href="javascript:void(0);" onclick="javascript:viewRelDoc('<%=preProjectDocumentOid %>');"><%=docHash.get("eca_obj_pre_rev") %></a><input type="hidden" name="relObjPreRev" value='<%=docHash.get("eca_obj_pre_rev") %>'></td>
		     <td class="tdwhiteM"><a href="javascript:void(0);" onclick="javascript:viewRelDoc('<%=afterProjectDocumentOid %>');"><%=eca_obj_after_rev %></a><input type="hidden" name="relObjAfterRev" value='<%=eca_obj_after_rev %>'></td>
		     <td class="tdwhiteM0">
		       <table border="0" cellspacing="0" cellpadding="0">
		         <tr>
		           
		           <%-- 
		           <td class='tdwhiteM'>
		             <table border='0' cellspacing='0' cellpadding='0'>
		                 <tr>
		                   <td><table border='0' cellspacing='0' cellpadding='0'>
		                       <tr>
		                         <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>
		                         <%
		                         if( docHash.get("change_yn").equals("") || docHash.get("change_yn").equals("N") )
		                         {
		                         %>
		                         <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=EcmUtil.getDocumentAfterOid(eca_obj_oid, eca_obj_after_rev)%>");' class='btn_blue' onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "01280") %>도면변경</a></td>
		                         <%
		                         }else{%>
		                         <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:changeEpmDoc("<%=EcmUtil.getDocumentAfterOid(eca_obj_oid, eca_obj_after_rev)%>");' class='btn_blue' onfocus='this.blur();'><font color="#0000FF"><%=messageService.getString("e3ps.message.ket_message", "01516") %>변경완료</font></a></td>
		                         <%} %>
		                         <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>
		                       </tr>
		                     </table></td>
		                 </tr>
		               </table></td>
		            --%>    
		           
		           <%
		           if(eca_obj_after_rev.equals("")) {
		           %>                  
                   <td>
                     <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseRelDoc2(<%=rowIndex %>);" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                     </table>
                   </td>
                   <td width="1">&nbsp;</td>
                   <%
		           } else {
                   %>
                   <td>
                     <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelRevRelDoc2(<%=rowIndex %>);" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                     </table>
                   </td>
                   <td width="1">&nbsp;</td>
                   <td>
                     <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:viewRelDoc('<%=afterProjectDocumentOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491") %><%--변경--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                     </table>
                   </td>
                   <%
                   }
                   %>
                                        		         
		           <!-- td width="2">&nbsp;</td>
                   <td width="*">
		             <table border="0" cellspacing="0" cellpadding="0">
		             <tr>
		               <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		               <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelDoc(<%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01229") %><%--대상검색--%></a></td>
		               <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		             </tr>
		             </table>
		           </td>
		           <td width="2">&nbsp;</td>
		           <td width="*"><table border="0" cellspacing="0" cellpadding="0">
		             <tr>
		               <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		               <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelDoc2('a', '', <%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01509") %><%--변경문서--%></a></td>
		               <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
		               </tr>
		           </table></td -->
		           
		           </tr>
		       </table>
		     </td>
		   </tr>
<%
            rowIndex++;
        }
    }
} else {
%>
           <tr>
            <td colspan=8 class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "01497") %><%--변경 문서가 없습니다--%>.</td>
           </tr>
<%    
}
%>
      </table>
    </td>
  </tr>
</table>
</div>


<%
docHash = null;

if( docList != null && docList.size() > 0 )
{
    int docListLength = docList.size();
    for( int docCnt=0; docCnt< docList.size(); docCnt++ )
    {
        docHash = docList.get(docCnt);
        String activity_type = docHash.get("activity_type");
        if(activity_type != null && !activity_type.equals("") && activity_type.equalsIgnoreCase("4")) {
            String eca_oid =  docHash.get("eca_oid");
            
            
            KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(eca_oid);
            if("WORKCOMPLETED".equals(eca.getActivityStatus())){
        	    continue;
            }
            if(eca != null) {
	            // To-Do 에서 오는 경우로 수신일을 set 한다.
	            Timestamp receiveConfirmedDate = eca.getReceiveConfirmedDate();
	            if(receiveConfirmedDate == null) {
	                eca.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());
	                
	                PersistenceHelper.manager.save(eca);
	                PersistenceHelper.manager.refresh(eca);
	            }
            
            }
            
             
            String obj_type = docHash.get("obj_type");
            String activity_type_name = docHash.get("activity_type_name");
            String eca_obj_oid = docHash.get("eca_obj_oid");
            String doc_type_desc = docHash.get("doc_type_desc");
                
            String docActFlag = (activity_type.equals("") || activity_type.equalsIgnoreCase("3")) ? "DOC" : "ACT";
            // 완료요청일
            String completeRequestDate = StringUtils.stripToEmpty(docHash.get("complete_request_date"));
            // 수신확인일
            String receiveConfirmedDate = StringUtils.stripToEmpty(docHash.get("receive_confirmed_date"));
            // 내용(ToDo 에서 사용자 입력 정보)
            String activityBackDesc = StringUtils.stripToEmpty(docHash.get("activity_type_desc"));
            
            // 이전 버전 문서 OID
            String preProjectDocumentOid = EcmUtil.getDocumentOid(docHash.get("eca_obj_no"), docHash.get("eca_obj_pre_rev"));
            // 이후 버전 문서 OID
            String afterProjectDocumentOid = EcmUtil.getDocumentOid(docHash.get("eca_obj_no"), docHash.get("eca_obj_after_rev"));
            String eca_obj_after_rev = (afterProjectDocumentOid != null && !afterProjectDocumentOid.equals("")) ? docHash.get("eca_obj_after_rev") : "";
            
            // 실중량적용 또는 고객승인
            if(obj_type.equalsIgnoreCase("DOC_08") || obj_type.equalsIgnoreCase("DOC_09")) {
%>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td class="space15"></td>
                  </tr>
                </table>
                <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=activity_type_name %><%--실중량적용 또는 고객승인--%></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td class="space5"></td></tr></table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td  class="tab_btm2"></td></tr></table>
                        
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="headerLock6">
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "내용") %><%--내용--%></td>
                          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></td>
                          <td width="140" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                        </tr>
                      <!-- /table>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable" style=table-layout:fixed -->
    
                        <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex' >

		                  <%-- 서버에 넘기는 필수 정보 --%>
		                  <input type='hidden' name="ecaUniqueKey" value='<%=eca_oid %>'>
                          <input type='hidden' name='relObjOid'  value='<%=eca_obj_oid %>'>
		                  <input type='hidden' name='relEcaDocOtherTypeDesc' value='<%=doc_type_desc %>'>
		                  <input type='hidden' name='relEcaWorkerOid'  value='<%=docHash.get("worker_id") %>' >
		                  
		                  <%
		                  // 개정할 문서가 없을 경우, 서버에 넘기는 정보의 갯수를 맞추기 위해서 필요하다.
                          if(eca_obj_oid == null || eca_obj_oid.equals("")) {
		                  %> 
		                  <input type="checkbox" name="chkPostAct" value='<%=eca_oid %>' style="display:none;">
		                  
		                  <input type='hidden' name='relObjPreRev' value=''>
		                  <input type='hidden' name='relObjAfterRev'  value=''>
		                                    
						  <input type='hidden' name="relEcaDocNo" value=''>
						  <input type='hidden' name="relEcaDocName" value=''>
						  
		                  <%-- 문서를 가지고 있어야 하는지 --%>
		                  <input type='hidden' name='has2relDoc' value='N'>
		                  <%
		                  }
		                  %>
		                  
		                  <input type='hidden' name="docActFlag" value="<%=docActFlag %>">
		                  <input type='hidden' name="customName" value="<%=activity_type_name %>">
		                  <input type='hidden' name="completeRequestDate" value="<%=completeRequestDate %>">
                 
                          <input type='hidden' name='relDocChangeYn' value='N'>
                          <input type='hidden' name='afterProjectDocumentOid' value='<%=afterProjectDocumentOid %>'>
		                  <%-- 서버에 넘기는 필수 정보 --%>
		                 		                          
		                  <input type='hidden' name='relEcaActivityType' value='<%=activity_type %>'>
                          <input type='hidden' name='relEcaEpmDocType'  value='<%=obj_type %>'>
                          <input type='hidden' name='relEcaLinkOid' value='<%=docHash.get("eca_obj_link_oid")%>'>
                          <input type='hidden' name='viewDocOid' value='<%=preProjectDocumentOid %>'>
                          
                          <td class="tdwhiteM">
                            <select name="activityTemp" class="fm_jmp" style="width: 45">
	                           <option value="3">문서</option>
	                           <option value="4" selected>활동</option>
	                        </select>
                          </td>
                          
                          <td class="tdwhiteM"><%=completeRequestDate %><%--완료요청일--%>&nbsp;</td>
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(activityBackDesc) %>">
                            <input type="text" name="activityBackDesc" value="<%=activityBackDesc %>" style="width:98%;" maxlength="100"><%--내용--%>
                          </td>
                          <td class="tdwhiteM"><%=receiveConfirmedDate %><%--수신확인일--%>&nbsp;</td>
                          <td class="tdwhiteM0">
                            
                            <%
                            // 실중량적용
                            if(obj_type.equalsIgnoreCase("DOC_08")) {
                            %>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                              
                                <td>
                                  <table border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelProduct(<%=rowIndex %>);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491") %><%--변경--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                  </tr>
                                  </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                  <table border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:viewEcnWeightHistory();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02270") %><%--이력--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                  </tr>
                                  </table>
                                </td>                                
                              </tr>
                            </table>
                            <%
                            }
                            %>
                            
                          </td>
                          
                        </tr>
			                   
			            <%
			            // 고객승인
	                    if(obj_type.equalsIgnoreCase("DOC_09")) {
			            %>            
			            <tr>
			              <td colspan="5" class="tdwhiteL0">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relEcaExpansion120Table">
                              <tr class="">
			
			                          <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insert120Line('<%=eca_oid %>');"><img src="/plm/portal/images/b-plus.png"></a></td>
			                          <td width="*" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "고객사") %><%--고객사--%></td>
			                          <td width="*" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "고객승인일") %><%--고객승인일--%></td>
            
			                  </tr>
			                  
			                  <%
			                  QueryResult linkResult = PersistenceHelper.manager.navigate(eca, "some", KETChangeActivityExpansion.class, false);
			                  while (linkResult.hasMoreElements()) {
				                  KETChangeActivityExpansion ecaExpansion = (KETChangeActivityExpansion) linkResult.nextElement();
				                  String ecaExpansionOid = CommonUtil.getOIDString(ecaExpansion);

		                          String subContractorCode = StringUtils.stripToEmpty(ecaExpansion.getSubContractorCode());
		                          String subContractorApprovalDate = DateUtil.getTimeFormat(ecaExpansion.getSubContractorApprovalDate(), "yyyy-MM-dd"); 
		                           
		                          String subContractorTxt = "";
				                  Persistable some = (Persistable) ecaExpansion.getSome();
				                  if(some instanceof NumberCode) {
				                      NumberCode numberCode = (NumberCode) some;
				                      subContractorTxt = numberCode.getName();
				                      subContractorCode = numberCode.getCode();
				                  }
				                  String someOid = CommonUtil.getOIDString(some);
				                  
			                  %>
			                  <tr height="27">
			                    <td class="tdwhiteM">
			                      <a href="#" onclick="javascript:$(this).parent().parent().remove();"><img src="/plm/portal/images/b-minus.png"></a>
			                      
			                      <input type="hidden" name="ecaOid" value="<%=eca_oid %>">
                                  <input type="hidden" name="ecaExpansionOid" value="<%=ecaExpansionOid %>">
                                  <input type="hidden" name="subContractorCode" value="<%=subContractorCode %>">
                                  
			                    </td>
			                    <td class="tdwhiteM">
			                      <input type="text" name="subContractorTxt" value="<%=subContractorTxt %>"class="txt_field" style="width: 80%;">   
                                  <input type="hidden" name="someOid" value="<%=someOid %>">
                                  <a href="javascript:SearchUtil.selectOneSubContractor('someOid','subContractorTxt');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                                  <a href="javascript:CommonUtil.deleteValue('someOid','subContractorTxt');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			                    </td>
			                    <td class="tdwhiteM0">
			                      <input name="subContractorApprovalDate" value="<%=subContractorApprovalDate %>" class="txt_field" style="width: 80px;"/>
                                  <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('subContractorApprovalDate');" style="cursor: hand;">
                                </td>
			                  </tr>
			                  <%
			                  }
			                  %>
			                </table>
			                
			              </td>
			            </tr>
			            <%
	                    }
			            %>
			                                    
                      </table>
                    </td>
                  </tr>
                </table> 
<%                	
            } 
            // 설변적용시점 또는 파생차종고객통보 또는 사용자 입력 활동
            else {  // else if(obj_type.equalsIgnoreCase("DOC_110") || obj_type.equalsIgnoreCase("DOC_130")) {
%>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                    <td class="space15"></td>
                  </tr>
                </table>
                <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=activity_type_name %><%--설변적용시점 또는 파생차종고객통보--%></td>
                  </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td class="space5"></td></tr></table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td  class="tab_btm2"></td></tr></table>
                        
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="headerLock6">
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
                          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02178") %><%--완료요청일--%></td>
                          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "내용") %><%--내용--%></td>
                          <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01931") %><%--수신확인--%></td>
                          <td width="75" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                        </tr>
                      <!-- /table>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable" style=table-layout:fixed -->
    
                        <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex' >

                          <%-- 서버에 넘기는 필수 정보 --%>
                          <input type='hidden' name="ecaUniqueKey" value='<%=eca_oid %>'>
                          <input type='hidden' name='relObjOid'  value='<%=eca_obj_oid %>'>
                          <input type='hidden' name='relEcaDocOtherTypeDesc' value='<%=doc_type_desc %>'>
                          <input type='hidden' name='relEcaWorkerOid'  value='<%=docHash.get("worker_id") %>' >
                          
                          <%
                          // 개정할 문서가 없을 경우, 서버에 넘기는 정보의 갯수를 맞추기 위해서 필요하다.
                          if(eca_obj_oid == null || eca_obj_oid.equals("")) {
                          %> 
                          <input type="checkbox" name="chkPostAct" value='<%=eca_oid %>' style="display:none;">
                          
                          <input type='hidden' name='relObjPreRev' value=''>
                          <input type='hidden' name='relObjAfterRev'  value=''>
                                            
                          <input type='hidden' name="relEcaDocNo" value=''>
                          <input type='hidden' name="relEcaDocName" value=''>
                          
                          <%-- 문서를 가지고 있어야 하는지 --%>
                          <input type='hidden' name='has2relDoc' value='N'>
                          <%
                          }
                          %>
                          
                          <input type='hidden' name="docActFlag" value="<%=docActFlag %>">
                          <input type='hidden' name="customName" value="<%=activity_type_name %>">
                          <input type='hidden' name="completeRequestDate" value="<%=completeRequestDate %>">
                 
                          <input type='hidden' name='relDocChangeYn' value='N'>
                          <input type='hidden' name='afterProjectDocumentOid' value='<%=afterProjectDocumentOid %>'>
                          <%-- 서버에 넘기는 필수 정보 --%>
                                                          
                          <input type='hidden' name='relEcaActivityType' value='<%=activity_type %>'>
                          <input type='hidden' name='relEcaEpmDocType'  value='<%=obj_type %>'>
                          <input type='hidden' name='relEcaLinkOid' value='<%=docHash.get("eca_obj_link_oid")%>'>
                          <input type='hidden' name='viewDocOid' value='<%=preProjectDocumentOid %>'>
                          
                          <td class="tdwhiteM">
                            <select name="activityTemp" class="fm_jmp" style="width: 45">
                               <option value="3">문서</option>
                               <option value="4" selected>활동</option>
                            </select>
                          </td>
                          
                          <td class="tdwhiteM"><%=completeRequestDate %><%--완료요청일--%>&nbsp;</td>
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(activityBackDesc) %>">
                            <input type="text" name="activityBackDesc" value="<%=activityBackDesc %>" style="width:98%;" maxlength="100"><%--내용--%>
                          </td>
                          <td class="tdwhiteM"><%=receiveConfirmedDate %><%--수신확인일--%>&nbsp;</td>
                          <td class="tdwhiteM0">&nbsp;</td>
                          
                        </tr>
                        
                      </table>
                    </td>
                  </tr>
                </table>             
<%                       
            }

            // 개정할 문서가 있을 경우
            if(eca_obj_oid != null && !eca_obj_oid.equals("")) {
%>        	
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="headerLock6">
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                          <!-- td width="25"  rowspan="2" class="tdblueM"><input type="checkbox" name="allChkPostAct"  onclick="javascript:allCheck( 'chkPostAct', this.checked );"></td -->
                          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04115") %><%--후속업무--%></td>
                          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "내용") %><%--내용--%></td>
                          <td width="100"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
                          <td width="190" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
                          <td width="50"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01824") %><%--선택해제--%></td>
                          <td width="45"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
                          <td width="45"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
                          <td width="225" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
                        </tr>
                      <!-- /table>
                    </td>
                  </tr>
                  <tr>
                      <td>
                      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable" style=table-layout:fixed -->
    
                        <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex' >
                        
                          <%-- 문서를 가지고 있어야 하는지 --%>
                          <input type='hidden' name='has2relDoc' value='Y'>
                          <input type="checkbox" name="chkPostAct" value='<%=eca_oid %>' style="display:none;">
                          
                          <!-- td width="25" class="tdwhiteM"></td -->
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(activity_type_name) %>"><%=activity_type_name %></td>
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(doc_type_desc) %>"><%=doc_type_desc %>&nbsp;</td>
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(docHash.get("eca_obj_no")) %>"><input type="text" name="relEcaDocNo" value='<%=docHash.get("eca_obj_no") %>' class="txt_fieldRO" style="width:90px;" readonly></td>
                          <td class="tdwhiteM" title="<%=HtmlUtils.htmlEscape(docHash.get("eca_obj_name")) %>"><input type="text" name="relEcaDocName" value="<%=docHash.get("eca_obj_name") %>" class="txt_fieldRO" style="width:180px;" readonly></td>
                          <td class="tdwhiteM"><a href="javascript:popupRelDoc(<%=rowIndex %>);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a><a href="javascript:clearRelDoc('<%=rowIndex %>');">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                          </td>          
                          <td class="tdwhiteM"><a href="javascript:void(0);" onclick="javascript:viewRelDoc('<%=preProjectDocumentOid %>');"><%=docHash.get("eca_obj_pre_rev") %></a><input type="hidden" name="relObjPreRev" value='<%=docHash.get("eca_obj_pre_rev") %>'></td>
                          <td class="tdwhiteM"><a href="javascript:void(0);" onclick="javascript:viewRelDoc('<%=afterProjectDocumentOid %>');"><%=eca_obj_after_rev %></a><input type="hidden" name="relObjAfterRev" value='<%=eca_obj_after_rev %>'></td>
                          <td class="tdwhiteM0">
                            <table border="0" cellspacing="0" cellpadding="0">
                              <tr>
			                                
			                   <td>
			                     <table border="0" cellspacing="0" cellpadding="0">
			                     <tr>
			                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reviseRelDoc2(<%=rowIndex %>);" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00684") %><%--개정--%></a></td>
			                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                     </tr>
			                     </table>
			                   </td>
			                   <td width="1">&nbsp;</td>
			                   <td>
			                     <table border="0" cellspacing="0" cellpadding="0">
			                     <tr>
			                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:cancelRevRelDoc2(<%=rowIndex %>);" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "00691") %><%--개정취소--%></a></td>
			                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                     </tr>
			                     </table>
			                   </td>
			                   <td width="1">&nbsp;</td>
			                   <td>
			                     <table border="0" cellspacing="0" cellpadding="0">
			                     <tr>
			                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:viewRelDoc('<%=afterProjectDocumentOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01491") %><%--변경--%></a></td>
			                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                     </tr>
			                     </table>
			                   </td>
			                   			                       
                              </tr>
                            </table>
                          </td>
                        </tr>
                        
                      </table>
                    </td>
                  </tr>
                </table>     	
<%        	
            }
            rowIndex++;
            
        }
    }
}
%>

