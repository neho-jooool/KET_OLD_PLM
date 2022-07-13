<%@page contentType="text/html; charset=UTF-8"%>
<%@ page
    import="e3ps.common.code.*,
                 e3ps.common.jdf.config.*,
                 e3ps.common.util.WCUtil,
                 e3ps.common.web.HtmlTagUtil,
                 e3ps.part.entity.KETNewPartList,
                 e3ps.common.web.PageControl"%>
<%@page
    import="wt.lifecycle.LifeCycleTemplate,
                wt.fc.*,
                wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.PhaseTemplate,
                java.util.Hashtable"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script language="JavaScript">
<!--

//체크박스, select box 초기화
$(document).ready(function() {
   
    CommonUtil.singleSelect('essential',100);
    CommonUtil.singleSelect('checked',100);
    CommonUtil.singleSelect('hpYn',100);
    
    // check box option
    $("#checkAll").click(function(){
        $('input:checkbox').not(this).prop('checked', this.checked);
    });
    
    // body class popup-background body-space
    $("body").removeClass("body").removeClass("body-space").addClass("popup-background body-space");
    $(document).attr('title',"<%=messageService.getString("e3ps.message.ket_message", "06114")%><%--Attribute 지정--%>");
    
    
});


window.addPartAttrCallBack = function(partSelAttrPop){
	
	if ( typeof partSelAttrPop == "undefined" || partSelAttrPop == null ) {
        hideProcessing();
        return;
    }
    if(partSelAttrPop.length == 0) {
        hideProcessing();
        return;
    }
    
    var attrOidArry = $("input:hidden[name='partAttrOid']");
    for(var i = 0; i < partSelAttrPop.length; i++) {
        var infoArr = partSelAttrPop[i];
        var checkExist = false;
        attrOidArry.each(function(){
            if(infoArr.OID == this.value){
                // 동일한 part Attribute 체크 : 중복 방지
                checkExist = true;
                return false; // break;
            }
        });
        
        if(!checkExist){
            insertTableRow(infoArr.OID, infoArr.NAME);
        }
    }
    
    hideProcessing();
}

function addPartAttr(){
    
    var partAttrOidArry="";
    $("[name='partAttrOid']").each(function(){
        if(partAttrOidArry == "")
            partAttrOidArry = partAttrOidArry + this.value;
        else
            partAttrOidArry = partAttrOidArry + "," + this.value;
    });
    
    
    var url = "/plm/ext/part/spec/searchPartAttrPopup.do?attrOidArray="+partAttrOidArry;
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,'', 1034, 780, opts);
    
    //var partSelAttrPop = window.showModalDialog(url,window,"help=no; resizable=false; status=no; scroll=yes; dialogWidth=1034px; dialogHeight:780px; center:yes");
    //alert('partSelAttrPop:' + partSelAttrPop);
    
    
}

// selected part attribute의 값 하나에 대한 tr 추가
function insertTableRow(attrOid, atrrName){

    var trValue = "<tr>"
    + "<input name=\"partAttrOid\" type=\"hidden\" value=\"" + attrOid + "\" >"
    + "<input name=\"partClazAttrLinkOid\" type=\"hidden\" value=\"\" >"
    + "<input name=\"partAttrName\" type=\"hidden\" value=\"" + atrrName + "\" />"
    + "<td class=\"center\"><input type=\"checkbox\" name=\"check\"></td>"
    + "<td class=\"center\">" + atrrName + "</td>"           
    + "<td class=\"center\"><select name=\"essential\" ><option value=\"\" selected></option><option value=\"true\" >YES</option><option value=\"false\">NO</option></select></td>"
    + "<td class=\"center\"><select name=\"checked\" ><option value=\"\" selected></option><option value=\"true\" >YES</option><option value=\"false\">NO</option></select></td>"
    + "<td class=\"center\"><input type=\"text\" name=\"indexCol\" value=\"\" numberOnly=\"true\" /></td>"
    + "<td class=\"center\"><input type=\"text\" name=\"indexRow\" value=\"\" numberOnly=\"true\" /></td>"
    + "<td class=\"center\"><select name=\"hpYn\" ><option value=\"\" selected></option><option value=\"true\" >YES</option><option value=\"false\">NO</option></select></td>"
    + "<td class=\"center\"><input type=\"text\" name=\"indexSort\" value=\"\" numberOnly=\"true\" /></td></tr>";
    
    //alert(trValue);
    
    $('#partSpecTbl tr:last').after(trValue);
}

// number format check
$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});

function initLoad(){
    $("body").removeClass("body").removeClass("body-space").addClass("popup-background body-space");
    
}

// check box 선택된 것 delete
function delPartAttr(){

    var checkExist = false;
    $('input:checkbox[name="check"]:checked').each(function(){
         checkExist = true;
         return false; // break;
    });
    
    if(!checkExist){
        alert("<%=messageService.getString("e3ps.message.ket_message", "06115")%><%--제거하려는 속성을 선택해 주십시요.--%>");
        return;
    }
    
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "06116")%><%--해당하는 속성을 제거하시겠습니까?--%>")){
        
        $('input:checkbox[name="check"]:checked').each(function(){
            	var tr = $(this).parent().parent();
            	tr.remove();
        });
        
        //$('input:checkbox[name="check"]:checked').parents("tr").remove();
    }
}

// 저장시점에 validation 걸고, ajax로 save
function savePartAttr(){
    
    try{

        // validation
        var checkExist = false;
        $("[name='essential']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
       
        if(checkExist){
           alert("<%=messageService.getString("e3ps.message.ket_message", "06117")%><%--'필수'의 값을 모두 선택해 주세요.--%>");
           return;
        }
       
        $("[name='checked']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
        
        if(checkExist){
            alert("<%=messageService.getString("e3ps.message.ket_message", "06118")%><%--'체크'의 값을 모두 선택해 주세요.--%>");
            return;
        }
        
        $("[name='indexCol']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
        
        if(checkExist){
              alert("<%=messageService.getString("e3ps.message.ket_message", "06119")%><%--행의 값을 모두 입력해 주세요.--%>");
              return;
        }
     
        $("[name='indexRow']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
        
        if(checkExist){
            alert("<%=messageService.getString("e3ps.message.ket_message", "06120")%><%--열의 값을 모두 입력해 주세요.--%>");
            return;
        }

        $("[name='hpYn']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
        
        if(checkExist){
            alert("Homepage 전시여부의 값을 모두 선택해 주세요.--%>");
            return;
        }
        
        
        
        $("[name='indexSort']").each(function(){
            if(this.value == ""){
                checkExist = true;
                return false; // break;
            }
        });
        
        if(checkExist){
            alert("순서의 값을 모두 입력해 주세요.");
            return;
        }
        
        // return value Check
        var retValue = "";
        $("[name='partAttrName']").each(function(){
            if(retValue == ""){
                retValue = retValue + this.value;
            }else{
                retValue = retValue + "," + this.value; 
            }
        });
        
        //alert(retValue);
        
        // ajax로 save
        populateHiddenInput();
        
        $.ajax({
            type: 'post',
            url: '/plm/ext/part/spec/savePartSpecAttrList.do',
            data: $('[name=submitForm]').serialize(),
            success: function (data) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다--%>');

                if(opener){
                    
                    try {
                        opener.hideProcessing();
                    } catch(e) {}
                    
                    opener.setPartAttributeListName(retValue);
                }
                //window.returnValue = retValue;
                window.close();
            },
            fail : function(){
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
        });
        
    }catch(e){
        alert(e.message);
    }
}

function closeWindow(){

   try {
       hideProcessing();
   } catch(e) {}
   
   try {
       opener.hideProcessing();
   } catch(e) {}
   
   window.close();
    
}

$(window).unload( function () { 
    
    try {
          hideProcessing();
    } catch(e) {}
       
    try {
          opener.hideProcessing();
    } catch(e) {}

 }
);

// 속성을 form에 입력
function populateHiddenInput(){
    
    var html = "<input name=\"oid\"  type=\"hidden\" value=\"${oid}\" >";
    var rootObj = "partClassAttrLinkDTOs";
    
    var idx = 0;
    $("[name='partAttrOid']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "partAttrOid\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    idx = 0;
    $("[name='partClazAttrLinkOid']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "partClazAttrLinkOid\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    idx = 0;
    $("[name='essential']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "essential\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    idx = 0;
    $("[name='checked']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "checked\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });

    idx = 0;
    $("[name='hpYn']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "hpYn\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    idx = 0;
    $("[name='indexCol']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "indexCol\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    idx = 0;
    $("[name='indexRow']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "indexRow\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });

    idx = 0;
    $("[name='indexSort']").each(function(){
        html = html + "<input name=\"" + rootObj + "[" + idx + "]."  + "indexSort\"  type=\"hidden\" value=\"" + this.value + "\" >";
        idx++;
    });
    
    //alert(html);
    
    $('#submitForm')[0].innerHTML = html;
    
}


--> 
</script>
</head>

<body class="popup-background body-space" onLoad="initLoad();">
    <form name="form">
    <c:set var="dataYn" value="0"/>
    <c:forEach var="data" items="${resultList}" varStatus="status">
         <c:set var="dataYn" value="${status.count}"/>
    </c:forEach>
    
    <fmt:formatNumber value="${dataYn}" type="number" var="numberType" />
    
    <div class="contents-wrap">
        <div class="sub-title b-space20"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "06114")%><%--Attribute 지정--%></div>
        <div class="b-space20" style="text-align:right">
            <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:addPartAttr();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:delPartAttr();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "06122")%><%--제거--%></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:savePartAttr();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></span><span class="pro-cell b-right"></span></span></span>
            <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:closeWindow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
        </div>
        <div>
            <table id="partSpecTbl" border="0" cellpadding="0" class="table-style-01" summary="">
                <colgroup>
                    <col width="5%" />
                    <col width="330px" />
                    <col width="10%" />
                    <col width="10%" />
                    <col width="10%" />
                    <col width="10%" />
                    <col width="10%" />
                    <col width="10%" />
                    <col width="17px" />
                </colgroup>
                <thead>
                    <tr>
                        <td><input type="checkbox" value="" id="checkAll" /></td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "06121")%><%--Attribue--%></td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "06123")%><%--필수--%></td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "06124")%><%--체크--%></td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "06125")%><%--행--%></td>
                        <td><%=messageService.getString("e3ps.message.ket_message", "06126")%><%--열--%></td>
                        <td>Homepage<br/>전시여부</td>
                        <td>순서</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="9" cellpadding="0" cellspacing="0" style="padding-left: 0px;">
                            <div style="height:300px;width:100%;overflow-y:auto;overflow-x:hidden;" >
                                <table class="table-style-01" border="0" cellpadding="0" cellspacing="0">
                                    <tbody>
                                        <c:forEach var="result" items="${resultList}" varStatus="status">
                                            <c:if test="${dataYn < 10}">
                                            <tr>
                                                <input name="partAttrOid" type="hidden" value="${result.partAttrOid}" >
                                                <input name="partClazAttrLinkOid" type="hidden" value="${result.partClazAttrLinkOid}" >
                                                <input name="partAttrName" type="hidden" value="<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>" />
                                                <td style="width: 47px;" class="center"><input type="checkbox" name="check" ></td>
                                                <td style="width: 315px;" class="center">${result.partAttributeDTO.attrName}</td>           
                                                <td style="width: 94px;" class="center"><select name="essential" ><option value=""></option><option value="true" <c:if test="${result.essential}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.essential}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 94px;" class="center"><select name="checked" ><option value=""></option><option value="true" <c:if test="${result.checked}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.checked}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 94px;" class="center"><input type="text" name="indexCol" value="${result.indexCol}"  numberOnly="true" /></td>
                                                <td style="width: 94px;" class="center"><input type="text" name="indexRow" value="${result.indexRow}"  numberOnly="true" /></td>
                                                <td style="width: 94px;" class="center"><select name="hpYn" ><option value=""></option><option value="true" <c:if test="${result.hpYn}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.hpYn}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 94px;" class="center"><input type="text" name="indexSort" value="${result.indexSort}"  numberOnly="true" /></td>
                                                <td></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${dataYn >= 10}">
                                            <tr>
                                                <input name="partAttrOid" type="hidden" value="${result.partAttrOid}" >
                                                <input name="partClazAttrLinkOid" type="hidden" value="${result.partClazAttrLinkOid}" >
                                                <input name="partAttrName" type="hidden" value="<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>" />
                                                <td style="width: 47px;" class="center"><input type="checkbox" name="check" ></td>
                                                <td style="width: 328px;" class="center">${result.partAttributeDTO.attrName}</td>           
                                                <td style="width: 95px;" class="center"><select name="essential" ><option value=""></option><option value="true" <c:if test="${result.essential}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.essential}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 95px;" class="center"><select name="checked" ><option value=""></option><option value="true" <c:if test="${result.checked}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.checked}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 96px;" class="center"><input type="text" name="indexCol" value="${result.indexCol}"  numberOnly="true" /></td>
                                                <td style="width: 96px;" class="center"><input type="text" name="indexRow" value="${result.indexRow}"  numberOnly="true" /></td>
                                                <td style="width: 96px;" class="center"><select name="hpYn" ><option value=""></option><option value="true" <c:if test="${result.hpYn}">selected</c:if> >YES</option><option value="false" <c:if test="${!result.hpYn}">selected</c:if> >NO</option></select></td>
                                                <td style="width: 96px;" class="center"><input type="text" name="indexSort" value="${result.indexSort}"  numberOnly="true" /></td>
                                            </tr>
                                        </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>    
                </tbody>
            </table>
        </div>
    </div>
    </form>
    <form id="submitForm" name="submitForm">
    </form>
</body>
</html>
