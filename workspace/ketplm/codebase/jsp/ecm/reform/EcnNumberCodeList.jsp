<%@page import="e3ps.common.util.NumberCodeUtil"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.query.*"%>
<%@page import="e3ps.common.code.*,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                wt.fc.QueryResult"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
String codetype = StringUtil.trimToEmpty(request.getParameter("codetype"));
String refDocStr = StringUtil.trimToEmpty(request.getParameter("refDocStr"));

String[] t1 = refDocStr.split("\\,");

HashMap ecnMap = new HashMap();
int t1Length = (t1 != null) ? t1.length : 0;
for(int i=0; i < t1Length; i++) {
    String t2 = t1[i];
    if(t2 != null && !t2.equals("")) {
	    String[] t3 = t2.split("\\|");
	    
	    HashMap map1 = new HashMap();
	    int t3Length = (t3 != null) ? t3.length : 0;
	    for(int j=0; j < t3Length; j++) {
		    
	        String t4 = t3[j];
	        if(j == 0) {
	            map1.put("codecode", t4);
	            ecnMap.put(t4, map1);
	            
	        } else if(j == 1) {
	            map1.put("dsCode", t4);
	        } else if(j == 2) {
	            map1.put("codename", t4);
	        } else {
	            
	        }
	        
	    }
    }
    
}
Kogger.debug("ecnMap is "+ ecnMap.toString());

HashMap map = new HashMap();
map.put("type", codetype);
map.put("isParent", "false");
QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);
QueryResult result =  PersistenceHelper.manager.find(qs);
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Untitled Document</title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 5px;
  margin-right: 10px;
  margin-bottom: 5px;
}

-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script type="text/javascript">
$(document).ready(function(){

	

})
</script>
<script language="JavaScript">
<!--

function lfn_add(dsCode) {
    var innerRow = prodEcoDocTypeTable.insertRow();
    innerRow.height = "27";
    var rowIndex = innerRow.rowIndex - 1;
    
    var innerCell;
    
    for( var k=0 ; k < 2 ; k++ ) {
        innerCell = innerRow.insertCell();
            
        if (k == 0) 
        {
            innerCell.style.width = "40";
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<input type='checkbox' value='' name='oid' checked='checked' "
                                + "       codecode='"+ lfn_getUniqueTime() +"' "
                                + "       dsCode='"+ dsCode +"' "
                                + "       codelong='' > "
                                ;
    
        }
        else if(k == 1)
        {
            //innerCell.style.width = "*";
            innerCell.className = "tdwhiteL";
            innerCell.innerHTML = "<input type='text' name='codename' >";
        }
    }
}
function lfn_getUniqueTime() {
	var time = new Date().getTime();
	while (time == new Date().getTime());
	return new Date().getTime();
}

function lfn_select() {
  var codetype = '<%=codetype%>';
    
  form = document.forms[0];

  var arr = new Array();
  var subArr = new Array();
  var idx = 0;
  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
                
        subArr = new Array();
        subArr[0] = form.oid[i].value;
        subArr[1] = form.oid[i].codecode;
        subArr[2] = form.codename[i].value;
        subArr[3] = form.oid[i].dsCode;
        subArr[4] = form.oid[i].codelong;
        /* 
        alert(subArr);
        e3ps.common.code.NumberCode:21519,DOC_03,작업기준서,,21519
        */
        arr[idx++] = subArr;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      subArr = new Array();
      subArr[0] = form.oid.value;
      subArr[1] = form.oid.codecode;
      subArr[2] = form.codename.value;
      subArr[3] = form.oid.dsCode;
      subArr[4] = form.oid.codelong;
      arr[idx++] = subArr;
    }
  }

  
  window.returnValue= arr;
  window.close();
}

function checkAll() {
  form = document.forms[0];
  if(typeof form.oid == 'undefined' || form.oid == null) {
    return;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      form.oid[i].checked = form.chkAll.checked;
    }
  }
  else {
    form.oid.checked = form.chkAll.checked;
  }
}

//-->
</script>
</head>
<body>
<form>
<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png">
            <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "04116") %><%--ECN 추가--%></td>
                <td width="10"></td>
              </tr>
            </table>
          </td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table>
     
      <table border="0" cellspacing="0" cellpadding="0" width="100%" >
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_add('DOC');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04117") %><%--문서추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td>&nbsp;</td>
                                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_add('ACT');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04118") %><%--활동추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td>&nbsp;</td>
                                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:lfn_select();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td>&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td>&nbsp;</td>
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
      
      
      <div style="width:100%; height:100%; overflow-x:auto; overflow-y:auto;">
        <div style="min-width:800px; height:640px;">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
          </table>
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td  class="tab_btm1"></td>
            </tr>
          </table>

          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="prodEcoDocTypeTable">
            <tr bgcolor="#D8E0E7" align=center height=23>
              <td class="tdblueM" width="40">
                <input name="chkAll" type="checkbox" class="Checkbox" onClick="javascript:checkAll();">
              </td>
              <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>

            </tr>
  
            <%
            if(result == null || result.size() == 0) {
            %>
              <TR class="tdwhiteM0" >
                <td class="tdwhiteM0" colspan='2'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
              </TR>
            <%
            } else {
        	
        	    while(result.hasMoreElements()) {
        		    Object[] obj = (Object[])result.nextElement();
        	    
        		    NumberCode numberCode = (NumberCode)obj[0];
        		    String numberCodeOid = numberCode.getPersistInfo().getObjectIdentifier().getStringValue();
        		    String code = numberCode.getCode();
        		    String dsCode = StringUtil.checkNull(numberCode.getDsCode());
                
        		    String checked = "";
        		    if(ecnMap.containsKey(code)) {
        			    checked = "checked=\"checked\""; 
        			    ecnMap.remove(code);
        		    }
            %>
	                <tr>
	                  <td class="tdwhiteM">
	                    <input type="checkbox" value="<%=numberCodeOid %>" name="oid" <%=checked %> 
	                           codecode="<%=code %>" 
	                           dsCode="<%=dsCode %>" 
	                           codelong="<%=CommonUtil.getOIDLongValue(numberCode) %>" >
	                    <input type="hidden" name="codename" value="<%=numberCode.getName() %>" >       
	                  </td>
	                  <td class="tdwhiteL" title="<%=numberCode.getName() %>"><%=numberCode.getName() %></td>
	 
	                </tr>
            <%
                }
        	    
        	    Set set = ecnMap.keySet();
        	    Object[] objects = (set != null) ? set.toArray() : null;
        	    int objectsLength = (objects != null) ? objects.length : 0;
        	    for(int i=0; i < objectsLength; i++) {
        		    String key = (String) objects[i];
        		    HashMap map1 = (HashMap) ecnMap.get(key);
        	%>
                    <tr>
                      <td class="tdwhiteM">
                        <input type="checkbox" value="" name="oid" checked="checked"
                               codecode="<%=(String) map1.get("codecode") %>" 
                               dsCode="<%=(String) map1.get("dsCode") %>" 
                               codelong="" >
                        <input type="hidden" name="codename" value="<%=(String) map1.get("codename") %>" >       
                      </td>
                      <td class="tdwhiteL" title="<%=(String) map1.get("codename") %>"><%=(String) map1.get("codename") %></td>
     
                    </tr>        	
        	
        	<%   
        	    }
            }
            %>
            </TABLE>
          </div>
        </div>
            
            
      </td>
    </tr>
</table>
</form>
</body>
</html>

