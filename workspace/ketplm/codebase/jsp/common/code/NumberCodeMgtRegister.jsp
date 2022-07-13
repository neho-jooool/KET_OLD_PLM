<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.query.*,wt.util.*"%>
<%@page import="e3ps.common.code.*,e3ps.common.util.*,e3ps.common.web.HtmlTagUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/common/context.jsp"%>

<%
    String command = request.getParameter("command");
    String codetype = request.getParameter("codetype");
    String oid = request.getParameter("oid");
    String parentOid = request.getParameter("parentOid");
    String depth = request.getParameter("depth");
    //out.println("depth==>"+depth);
    if(command == null)command = "";
    if(codetype == null)codetype = "";
    
    if(oid == null)oid = "";
    if(parentOid == null)parentOid = "";
    if(depth == null)depth = "";
    if(codetype.equals(parentOid))parentOid = "";

    String code = "";
    String name = "";
    String description = "";
    String costCode = "";
    String venderCode = "";
    String sorting = "";
    Boolean disabled = true;


    NumberCode nCode = null;
    NumberCode parentCode = null;
    ArrayList ancestors = new ArrayList();
    ReferenceFactory rf = new ReferenceFactory();

    if(oid.length() > 0) {
        nCode = (NumberCode)CommonUtil.getObject(oid);
        if(nCode != null) {
          code = nCode.getCode() == null? "":nCode.getCode();
          name = nCode.getName() == null? "":nCode.getName();
          description = StringUtil.checkNull(nCode.getDescription());
          //costCode =  nCode.getCostCode() == null? "":nCode.getCostCode();
          venderCode = nCode.getVenderCode() == null? "":nCode.getVenderCode();
          parentCode = nCode.getParent();

          sorting = nCode.getSorting() == null? "":nCode.getSorting();
          disabled = nCode.isDisabled();
        }
    }

    if(parentCode == null && parentOid.length() > 0 )
        parentCode = (NumberCode)rf.getReference(parentOid).getObject();

    if(parentCode != null) {
       ancestors = NumberCodeHelper.ancestorNumberCode(parentCode);
       ancestors.add(parentCode);
    }

    String level1 = "";
    String level2 = "";
    if(ancestors.size() == 0) {
       if(parentCode != null)
           level1 = parentCode.getPersistInfo().getObjectIdentifier().getStringValue();
    } else {
       level1 = ((NumberCode)ancestors.get(0)).getPersistInfo().getObjectIdentifier().getStringValue();
       if(ancestors.size() > 1)
           level2 = ((NumberCode)ancestors.get(1)).getPersistInfo().getObjectIdentifier().getStringValue();
    }

    String str_CodeType = NumberCodeType.toNumberCodeType(codetype).getDisplay(Locale.KOREA);

%>
<%@page import="org.apache.batik.dom.util.HashTable"%>
<html>
<head>
<base target="_self">
<title>코드체계관리</title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/jsp/common/code/code.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>


<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSubmit()
{
  form = document.forms[0];
  
  if(form.name.value == ''){
    alert("이름을 입력 하십시오.");
    return;
  }
  if(form.code.value == ''){
    alert("코드를 입력 하십시오.");
    return;
  }
  <%if(depth.equals("0") && codetype.equals("CUSTOMEREVENT")){%>
  <%} else if(codetype.equals("CUSTOMEREVENT")){ %>
  
  if(form.venderCode.value == ''){
    alert("사업부를 입력 하십시오.");
    return;
  }
  
  if(form.abbr.value == ''){
      alert("검색 특성을 입력 하십시오.\r\n\r\n[자동차일정 검색] 화면에서 조회조건으로 활용됩니다.");
      return;
  }
  
  var abbrText = form.abbr.options[form.abbr.selectedIndex].text;
  
  if(form.abbr.value == 'HMC' || form.abbr.value == 'KMC' || form.abbr.value == '삼성전자' || form.abbr.value == 'LG전자'){
	  <%
	  if("modify".equals(command)) {
	  %>
	  
	  if(form.name.value != abbrText){
		  alert("해당 특성은 선택할 수 없습니다.");
		  return;
	  }
	  
	  <%}else{%>
	      alert("해당 특성은 선택할 수 없습니다.");
          return;
	  <%}%>
  }
  
  <%}%>
  var temp_codeType = document.forms[0].codetype.value;
  form.command.value = "<%=command%>";
  setParentCode();

//  if(!isValidCipher(temp_codeType, document.forms[0].code)) return;
//alert(form.parentOid.value);
  //disabledAllBtn();
  form.method = "post";
  form.target = "hiddenFrame";
  form.action = "/plm/jsp/common/code/searchChildCode.jsp";
  form.submit();
}

function regResult(arry) {
  //window.returnValue= arry;
  var myParent = opener || parent;
  myParent.callBackFn(arry);
  window.close();
}

function setParentCode() {
  form = document.forms[0];
  form.parentOid.value = form.selectType.options[form.selectType.selectedIndex].value;
}

var selectLevelIndex = 0;
function onChangeType(sObj) {
  form = document.forms[0];
  selectLevelIndex = sObj.selectlevel;
  if(sObj.value == '') {
    removeSelectOption(document.forms[0].selectType[++selectLevelIndex]);
    return;
  }

  form.parentOid.value = sObj.value;
  form.command.value = "";
  form.method = "post";
  form.target = "hiddenFrame";
  form.action = "/plm/jsp/common/code/searchChildCode.jsp";
  form.submit();
}

function removeSelectOption(sObj) {
  if(sObj != null) {
    len = sObj.options.length;
    for(var i = (len-1); i > 0; i--) {
      sObj.remove(i);
    }
  }
}

function addChildCodeList(oArr) {
  //removeSelect(selectLevelIndex);
  var idxCount = selectLevelIndex;
  if(selectLevelIndex == 0) {
    var cDiv = document.all.item("codeDiv");
    var col = cDiv.all.tags("SPAN").length;
    if(col == 1) {
      var newSpan = document.createElement("SPAN");
      var htmlStr = "<select name='selectType' selectlevel=" + ++selectLevelIndex + " class='fm_jmp' style='width:150' onChange='javascript:onChangeType(this);'><option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%><\/option><\/select>";
      newSpan.innerHTML = htmlStr;
      cDiv.appendChild(newSpan);
    }
  }

  selectTypeObj = document.forms[0].selectType[++idxCount];
  removeSelectOption(selectTypeObj);

  for(var i = 0; i < oArr.length; i++) {
    vArr = oArr[i];
    len = selectTypeObj.options.length;
<%
  if("modify".equals(command)) {
%>
    if( vArr[1] == '<%=code%>') {
      continue;
    }
<%
  }
%>
    selectTypeObj[len] = new Option(vArr[1] + " | " + vArr[2], vArr[0]);
  }
}
//-->
</SCRIPT>
</head>
<body style="margin:30px 15px" >
<form name='formCode' method='post'>

<!-- Hidden Value -->
<input type='hidden' name='codetype' value='<%=codetype%>'>
<input type='hidden' name='oid' value='<%=oid%>'>
<input type='hidden' name='parentOid' value=''>
<input type='hidden' name='depth' value=''>
<input type='hidden' name='command' value='<%=command%>'>

<!-- ############################## 항목 시작 : 이름부터 ############################## -->

    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=str_CodeType%> CODE <%if("modify".equals(command)){%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}else{%>등록<%}%>
      </div>
      <div class="float-r b-space10" style="text-align: right; width: 500px">
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:doSubmit();" class="btn_blue" ><%if("modify".equals(command)){%><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%><%}else{%>등록<%}%></a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center">
          <a href="javascript:self.close();" class="btn_blue" >닫기</a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="b-space40 clear">
        <table cellpadding="0" class="table-style-01" style="width: 480px" summary="">
          <colgroup>
            <col width="30%" />
            <col width="70%" />
          </colgroup>
          <tbody>
            <tr>
              <td class="title">
                <%if(codetype.equals("CUSTOMEREVENT")){ %>
				   <%if(depth.equals("0") && codetype.equals("CUSTOMEREVENT")){ %>이름<%}else{ %>이름<%} %>
				<%}else{ %>분류<%} %>
              </td>
              <td>
                <%if(depth.equals("0") && codetype.equals("CUSTOMEREVENT")){ %>
                <div id="codeDiv">
                    <span>
                        <select name="selectType" selectlevel=0 class="fm_jmp" style="width:150">
                        <option value=""><%=messageService.getString("e3ps.message.ket_message", "01815") %><%--선택없음--%></option>
                    </select>
                </span>
             </div>

            <%}else{ %>
                <div id="codeDiv">
                <span>
                    <select name="selectType" selectlevel=0 class="fm_jmp" style="width:150">
                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                    <%
                    String sType = codetype;
                    HashMap map = new HashMap();
                    map.put("type", sType);
                    map.put("isParent", "false");

                    QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);
                    QueryResult qr = PersistenceHelper.manager.find(qs);
                    Object obj[] = null;
                    NumberCode typecode = null;
                    String selOid = "";

                    while(qr.hasMoreElements()) {
                	   obj = (Object[])qr.nextElement();
                	   typecode = (NumberCode)obj[0];

                	   selOid = typecode.getPersistInfo().getObjectIdentifier().getStringValue();
                	   if( "modify".equals(command) && code.equals(typecode.getCode()) ) {
                	       continue;
                	   }
					%>
					      <option value="<%=selOid%>" <%if(level1.equals(selOid) ) {%>selected<%}%>>
					        <%=typecode.getCode()%>&nbsp;|&nbsp;<%=typecode.getName()%>
					      </option>
					<%
					    }
					%>
                    </select>
                </span>
		      </div>
		<%} %>
            </td>
        </tr>
        
        
        
        <!-- ############################## CASE1 항목 ############################## -->
		<%if(depth.equals("0") && codetype.equals("CUSTOMEREVENT")){ %>
		<tr>
		  <td class="title">코드<span class="style1"> *</span></td>
		  <td><input type='text' name='code' id='input' maxlength=20 style="width:99%" value="<%=code%>"></td>
		</tr>
		
		<tr>
          <td class="title">이름(한국어)<span class="style1"> *</span></td>
          <td><input type='text' name='name' id='input' style="width:99%" value="<%=name%>"></td>
        </tr>
        
        <tr>
          <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01868") %><%--설명(한국어)--%></td>
          <td><input type='text' name='description' id='input' style="width:99%" value="<%=description%>"></td>
        </tr>

		    <%
		    String abbr = null;
		    Map<String, Object> parameter = new HashMap<String, Object>();
		    parameter.put("codeType", "LANGUAGE");
		    List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
		    Map<String, Object> numberCode = new HashMap<String, Object>();
		    for ( Map<String, Object> item : list ) {
		        if ( !item.get("code").equals("ko") ) {
		            numberCode = NumberCodeUtil.getNumberCodeValueMap(codetype, code, item.get("code").toString());
		    %>
		            
		            <tr>
                        <td class="title">이름(<%=item.get("value") %>)</td>
                        <td><input type='text' name='codeLang' id='input' style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("value"))%>"></td>
                    </tr>
                    
                    <tr>
                        <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%>(<%=item.get("value") %>)</td>
                        <td><input type='text' name='desc' id='input' style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("desc"))%>"></td>
                    </tr>
		    <%
		            abbr =(String)numberCode.get("abbr");
		        }
		    }
		    %>
		    <!-- 항목 : 약어 시작 -->
		    
		    <tr>
                <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06145") %><%--약어--%></td>
                <td><input type='text' name='abbr' id=input style="width:99%" value="<%=StringUtil.checkNull(abbr)%>"></td>
            </tr>
		    <!-- 항목 : 약어 끝  -->
		<%}else if(codetype.equals("CUSTOMEREVENT")){ %>
		<!-- ############################## CASE2 항목 ############################## -->
		<tr>
          <td class="title">사업부<span class="style1"> *</span></td>
          <td>
            <select name="venderCode" selectlevel=0 class="fm_jmp" style="width:150">
                <option value="" >[ <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%> ] </option>
                <option value="자동차" <%=("자동차".equals(venderCode))?"selected":"" %> >자동차</option>
                <option value="전자" <%=("전자".equals(venderCode))?"selected":"" %> >전자</option>
            </select>
          </td>
        </tr>
        
        <tr>
          <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%><span class="style1"> *</span></td>
          <td><input type='text' name='code' id=input maxlength=20 style="width:99%" value="<%=code%>"></td>  
        </tr>
        
        <tr>
          <td class="title">이름(한국어)<span class="style1"> *</span></td>
          <td><input type='text' name='name' id='input' style="width:99%" value="<%=name%>"></td>  
        </tr>
        
        <tr>
          <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01868") %><%--설명(한국어)--%></td>
          <td><input type='text' name='description' id=input style="width:99%" value="<%=description%>"></td>  
        </tr>
        
		    <%
		    String abbr = null;
		    Map<String, Object> parameter = new HashMap<String, Object>();
		    parameter.put("codeType", "LANGUAGE");
		    List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
		    Map<String, Object> numberCode = new HashMap<String, Object>();
		    for ( Map<String, Object> item : list ) {
		        if ( !item.get("code").equals("ko") ) {
		            numberCode = NumberCodeUtil.getNumberCodeValueMap(codetype, code, item.get("code").toString());
		    %>
		    
		           <tr>
		             <td class="title">이름(<%=item.get("value") %>)</td>
                     <td><input type='text' name='codeLang' id=input style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("value"))%>"></td>  
                   </tr>
                   
		           <tr>
		             <td class="title">이름(<%=item.get("value") %>)</td>
                     <td><input type='text' name='desc' id=input style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("desc"))%>"></td>  
                   </tr>
		    <%
		           abbr =(String)numberCode.get("abbr");
		        }
		    }
		    %>
		    <!-- 항목 : 약어 시작 -->
		    
		    <tr>
		      <td class="title">검색특성</td>
              <td>
                <select id="abbr" name="abbr">
                  <option value="">선택</option>
                  <%
                    if("modify".equals(command)) {
                  %>
                  <option value="HMC" <%if("HMC".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>HMC</option>
                  <option value="KMC" <%if("KMC".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>KMC</option>
                  <option value="삼성전자" <%if("삼성전자".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>삼성전자</option>
                  <option value="LG전자" <%if("LG전자".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>LG전자</option>
                  <%} %>
                  <option value="중국OEM" <%if("중국OEM".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>중국 OEM</option>
                  <option value="GlobalOEM" <%if("GlobalOEM".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>Global OEM</option>
                  <option value="ETC" <%if("ETC".equals(StringUtil.checkNull(abbr))){%>selected<%} %>>기타</option>
                 </select>
              </td>  
            </tr>
		    
		    <!-- 항목 : 약어 끝  -->
		<%}else{ %>
		<!-- ############################## CASE3 항목 ############################## -->
		
		<tr>
            <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%><span class="style1"> *</span></td>
            <td><input type='text' name='code' id=input maxlength=10 style="width:99%" value="<%=code%>"></td>  
        </tr>
        
		<tr>
            <td class="title">이름(한국어)<span class="style1"> *</span></td>
            <td><input type='text' name='name' id=input style="width:99%" value="<%=name%>"></td>  
        </tr>
        
		<tr>
            <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01868") %><%--설명(한국어)--%></td>
            <td><input type='text' name='description' id=input style="width:99%" value="<%=description%>"></td>  
        </tr>

		  <%
		  String abbr = null;
		  Map<String, Object> parameter = new HashMap<String, Object>();
		  parameter.put("codeType", "LANGUAGE");
		  List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
		  Map<String, Object> numberCode = new HashMap<String, Object>();
		  for ( Map<String, Object> item : list ) {
		      if ( !item.get("code").equals("ko") ) {
		          numberCode = NumberCodeUtil.getNumberCodeValueMap(codetype, code, item.get("code").toString());
		  %>
		          <tr>
                    <td class="title">이름(<%=item.get("value") %>)</td>
                    <td><input type='text' name='codeLang' id='input' style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("value"))%>"></td>  
                  </tr>
                  
		          <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%>(<%=item.get("value") %>)</td>
                    <td><input type='text' name='desc' id='input' style="width:99%" value="<%=StringUtil.checkNull((String)numberCode.get("desc"))%>"></td>  
                  </tr>
                  
		  <%
		         abbr =(String)numberCode.get("abbr");
		      }
		  }
		  %>
		     <!-- 항목 : 약어 시작 -->
		    <tr>
		      <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06145") %><%--약어--%></td>
              <td><input type='text' name='abbr' id=input style="width:99%" value="<%=StringUtil.checkNull(abbr)%>"></td>  
            </tr>
		    <!-- 항목 : 약어 끝  -->
		    
		    <tr>
              <td class="title">순서</td>
              <td><input type='text' name='sorting' id=input style="width:99%" value="<%=sorting%>"></td>  
            </tr>
            
            <tr>
              <td class="title">사용여부</td>
              <td>
                <select name="disabled">
                    <option value="0" <%=!disabled ? "selected" : "" %>>사용</option>
                    <option value="1" <%=disabled ? "selected" : "" %>>사용안함</option>
                </select>
              </td>  
            </tr>
		<%} %>
        
        
        </tbody>
        </table>
      </div>
    </div>
</form>



<iframe src="" name="hiddenFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display:none"></iframe>
</body>
</html>
