<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />


<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script language="javascript">
    $(document).ready(function(){
        
    	var partno = "${partNo}";
    	var ecoNo = "${ecoNo}";
   		var hpstatus = "${hpstatus}";
  		var stampstatus = "${stampstatus}";
  		var divisionflag = "${divisionflag}";
  		var createStartDate = "${createStartDate}";
  		var createEndDate = "${createEndDate}";
  		
  		if(partno != "")  $('[name="partNo"]').val(partno);
  		if(ecoNo != "")  $('[name="ecoNo"]').val(ecoNo);
  		if(createStartDate == ""){
  			$('[name="createStartDate"]').val(predate);
  		}else{
  			$('[name="createStartDate"]').val(createStartDate);
  		}
  		if(createEndDate == ""){
  			$('[name="createEndDate"]').val(postdate);
  		}else{
  			$('[name="createEndDate"]').val(createEndDate);
  		}

  		if(hpstatus != "") {
  			$("#hpstatus").val(hpstatus);
  		}
  		if(stampstatus != "") {
  			$("#stampstatus").val(stampstatus);
  		}
        if(divisionflag != "") {
            $("#divisionflag").val(divisionflag);
        }
        
  		CalendarUtil.dateInputFormat('createStartDate','createEndDate');
    });


    function searchItemclear(){
    	$('[name="partNo"]').val("");
    	$('[name="ecoNo"]').val(""); 
    	$("#hpstatus").val("");
    	$("#stampstatus").val("");
    	$("#divisionflag").val("");
    	$("#createStartDate").val(predate);
    	$("#createEndDate").val(postdate);
    }
    
    
    function createMaterial(reqOid, partOid){
        var url="/plm/ext/edm/addMaterialPopup.do?partOid="+partOid;
           openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
    }

    function sendHpAll(){
        var req="";
        $("[name='req']").each(function(){
            if(this.checked) {
                req += this.value + ","; 
            }
        });

        if(req == "") {
            alert('일괄전송할 정보가 없습니다.\n좌측 체크박스를 선택하신후 다시 시도하시기 바랍니다.');
            return;
        }   
        else
        {
            
            showProcessing();
            
            $("#oid").val(req);
            $.ajax({
                   type       : "POST",
                   url        : "/plm/ext/edm/sendHp.do",
                   data       : $("#plmHpIfForm").serialize(),
                   dataType   : "json",

                   success    : function(data){
                        if(data == 'OK') alert('전송하였습니다.');
                        else if(data == 'FAIL') alert('전송실패하였습니다.');
                        
                        window.location.reload(true);
                        
                   },
                   error    : function(xhr, status, error){
                                 alert(xhr+"  "+status+"  "+error);
                                 
                   }
               });
        }
        
    }

    
    function sendPartAttr(){
            
        showProcessing();
        
        $.ajax({
               type       : "POST",
               url        : "/plm/ext/edm/sendPartAttr.do",
               data       : $("#plmHpIfForm").serialize(),
               dataType   : "json",

               success    : function(data){
                    if(data == 'OK') alert('전송하였습니다.');
                    else if(data == 'FAIL') alert('전송실패하였습니다.');
                    
                    window.location.reload(true);
                    
               },
               error    : function(xhr, status, error){
                             alert(xhr+"  "+status+"  "+error);
                             
               }
           });
        
    }
    

    function sendHp(reqOid){
        showProcessing();
        $("#oid").val(reqOid);
        
        $.ajax({
               type       : "POST",
               url        : "/plm/ext/edm/sendHp.do",
               data       : $("#plmHpIfForm").serialize(),
               dataType   : "json",

               success    : function(data){
                   if(data == 'OK') alert('전송하였습니다.');
                   else if(data == 'FAIL') alert('전송실패하였습니다.');
                   
                   window.location.reload(true);
              },
              error    : function(xhr, status, error){
                            alert(xhr+"  "+status+"  "+error);
                            
              }
           });
    }
    
    function reStamping(reqOid){
        showProcessing();
        $("#oid").val(reqOid);
        
        $.ajax({
               type       : "POST",
               url        : '/plm/ext/edm/reStamping.do',
               data       : $("#plmHpIfForm").serialize(),
               dataType   : "json",

               success    : function(data){
                    if(data == 'OK') alert('Stamping을 재요청하였습니다.');
                    
                    window.location.reload(true);
               },
               error    : function(xhr, status, error){
                             alert(xhr+"  "+status+"  "+error);
                             
               }
           });
    }
    
    
    function searchPlmHpIfList(){
    	
        var d = document.plmHpIfForm;
        $("#searchCheck").val("Y");
        d.action = "/plm/ext/edm/plmHpIfList.do";
        d.submit();    	
    	
    }
    
    
    
    function searchPlmHpIfList2(){
        //showProcessing();
        var cnt = 0;
        $.ajax({
               type       : "POST",
               url        : "/plm/ext/edm/plmHpIfList.do",
               data       : $("#plmHpIfForm").serialize(),
               dataType   : "json",

               success    : function(data){

                    reDrawTable(data);
                    
                    var addCol = data.length;
                    
                    $.each(data, function( index, data ) {
                       // alert( index + " : " + data.WTPARTNUMBER );  
                    	
                       if(addCol>20){
                        	$("#tbl").append("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                       }else{
                        	$("#tbl").append("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                       }
                       
                        
                        
                    });
                    	$("#tbl").append("</tbody>"); 
                    
                    
               },
              error    : function(xhr, status, error){
                            alert(xhr+"  "+status+"  "+error);
                            
              }
           });
    }
    
    

    function ViewPartForm(oid){
        var url = "/plm/jsp/bom/ViewPart.jsp?oid=wt.part.WTPart:"+oid;
        var name = "partView";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    }
    
    function reload(){
        self.location.reload(true);
    }

    
    function update(reqOid, partOid){
        var url="/plm/ext/edm/updateHpIfFilePopup.do?oid="+reqOid+"&partOid="+partOid;
        myWindow= openWindow(url,"reSaveFile","760","400","status=1,scrollbars=no,resizable=no");   
    }
    
    function closeWin() {
        myWindow.close();
    }    
    
    function reDrawTable(data){
    	var selectedTable = $("#tbl")
    	selectedTable.find('tr').each(function(i) {
    		$(this).remove();
        });
	
		$("#tbl").append("<tbody>");
		$("#tbl").append("<colgroup>");
		$("#tbl").append("<col width='3%' />");
		$("#tbl").append("<col width='100px' />");
		$("#tbl").append("<col width='100px' />");
		$("#tbl").append("<col width='100px' />");
		$("#tbl").append("<col width='100px' />");
		$("#tbl").append("<col width='*' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='5%' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='4%' />");
	    $("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='5%' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='4%' />");
		$("#tbl").append("<col width='4%'/>");
		$("#tbl").append("<col width='5%'' />");
		
		if(data.length>20){
			$("#tbl").append("<col width='17px' />");
		}
		
		$("#tbl").append("</colgroup>");
	
    
    
    }
    
    
</script>


<form id="plmHpIfForm" name="plmHpIfForm" method="post" autocomplete="off">
<input type="hidden" id="searchCheck" name="searchCheck" value="">
<c:set var="dataYn" value="0"/>
<c:forEach var="data2" items="${resultMapList}" varStatus="state2">
<c:set var="dataYn" value="${state2.count}"/>
</c:forEach>
<fmt:formatNumber value="${dataYn}" type="number" var="numberType" />
<input type="hidden" id="oid" name="oid"> 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">Interface 이력관리</td>
                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="/plm/portal/images/icon_navi.gif">Interface 이력관리</td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:searchItemclear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:searchPlmHpIfList();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            
             <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            
            
            <table style="width: 100%;">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table style="width: 100%;">
                <tr>
                    <td class="tdblueL"  style="width: 110px;">Part No</td>
                    <td class="tdwhiteL" >
                        <input type="text" id="partNo" name="partNo" class="txt_field" style="width: 98%;" value="">
                    </td>
                    <td class="tdblueL" style="width: 110px;">ECO No</td>
                    <td class="tdwhiteL0">
                        <input type="text" id="ecoNo" name="ecoNo" class="txt_field" style="width: 98%;" value="">
                    </td>
                    <td class="tdblueL" style="width: 110px;">이관일</td>
                    <td class="tdwhiteL0">
                        <input type="text" id="createStartDate" name="createStartDate" class="txt_field" style="width: 70px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createStartDate');" style="cursor: hand;"> 
                                ~ 
                            <input type="text" id="createEndDate" name="createEndDate" class="txt_field" style="width: 70px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createEndDate');" style="cursor: hand;">
                    </td>                    
                </tr>
                <tr>
                    <td class="tdblueL"  style="width: 110px;">사업부</td>
                    <td class="tdwhiteL" >
                        <select id="divisionflag" name="divisionflag" class="fm_jmp" style="width: 160px;" >
                            <option value="">전체</option>
                            <option value="C">자동차사업부</option>
                            <option value="E">전자사업부</option>
                        </select>
                    </td>
                    <td class="tdblueL"  style="width: 110px;">전송여부</td>
                    <td class="tdwhiteL" >
                        <select id="hpstatus" name="hpstatus" class="fm_jmp" style="width: 160px;" >
                            <option value="">전체</option>
                            <option value="Y">완료</option>
                            <option value="N">대기</option>
                        </select>
                    </td>
                    <td class="tdblueL" style="width: 110px;">Stamping 상태</td>
                    <td class="tdwhiteL0">
                        <select id="stampstatus" name="stampstatus" class="fm_jmp" style="width: 160px;" >
                            <option value="">전체</option>
                            <option value="SUCCESS">성공</option>
                            <option value="QUEUE">변환중</option>
                            <option value="BEFORE">대기중</option>
                            <option value="FAIL">실패</option>
                        </select>
                    </td>
                </tr>
            </table>
              <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>           
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:reload();" class="btn_blue">Refresh</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:sendHpAll();" class="btn_blue">일괄전송</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:sendPartAttr();" class="btn_blue">부품속성순서 전송</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                              </td>
                        </tr>
                    </table>
                </td>
            </table>
     
            
            
            
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table class="table-style-01" cellpadding="0">
                <colgroup>
                   <col width="3%" />
                   <col width="100px" />
                   <col width="100px" />
                   <col width="100px" />
                   <col width="100px" />
                   <col width="*" />
                   <col width="4%" />
                   <col width="5%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="5%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="4%" />
                   <col width="5%" />
                  <c:if test="${dataYn > 15}">
                   <col width="17px" />
                   </c:if>
                </colgroup> 
                <thead>            
                <tr>
                    <td rowspan="2"></td>
                    <td rowspan="2">(대표)<BR>제품번호</td>
                    <td rowspan="2">(실제전송대상)<BR>제품번호</td>
                    <td rowspan="2">ECONO</td>
                    <td rowspan="2">사업부</td>
                    <td rowspan="2">제품명</td>
                    <td rowspan="2">산출물<br>변경</td>
                    <td rowspan="2">등록일<BR>(I/F일자)</td>
                    <td colspan="4">구분</td>
                    <td colspan="6">기타</td>
                    <td rowspan="2"></td>
                </tr>
                <tr>
                    <td colspan="2">Stamping</td>
                    <td colspan="2">Interface</td> 
                    <td>이미지</td>
                    <td>2D</td>
                    <td>3D</td>
                    <td>STEP</td>
                    <td>IGS</td>
                    <td>조립품재질</td>
                </tr>
                <tbody>
                </thead>
                   <tr cellpadding="0" cellspacing="0">
                       <td colspan="18" cellpadding="0" cellspacing="0" style="padding-left: 0px;">
                           <div style="height:600px;width:100%;overflow-y:auto;overflow-x:hidden;" >
                              <table id="tbl" class="table-style-01" border="0" cellpadding="0" cellspacing="0">
                                 <tbody>
                                    <colgroup>
                                        <col width="3%" />
                                        <col width="100px" />
                                        <col width="100px" />
                                        <col width="100px" />
                                        <col width="100px" />
                                        <col width="*" />
                                        <col width="4%" />
                                        <col width="5%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="5%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="4%" />
                                        <col width="4%" />

                                        <c:if test="${dataYn > 15}">
                                        
                                        <col width="17px" />
                                        
                                        </c:if>
                                        
                                    </colgroup>                
                                    <c:forEach var="data" items="${resultMapList}" varStatus="state">
                                    <tr name="contents">
                                        <td class="tdwhiteM" align="center"><input type="checkbox" name="req" value="${data.REQIDA2A2}"></td>
                                        <td class="tdwhiteM">${data.PARTNUMBER}</td>
                                        <td class="tdwhiteM">
                                           <a href="javascript:ViewPartForm('${data.PARTIDA2A2}');">${data.WTPARTNUMBER}</a> 
                                        </td>
                                        <td class="tdwhiteM"><a href="javascript:openViewEco('${data.ECOID}');">${data.ECOID}</a></td>
                                        <td class="tdwhiteM">${data.DIVISIONFLAG}</td>
                                        <td class="tdwhiteL">${data.PARTNAME}</td>
                                        <td class="tdwhiteL">
                                        <c:if test="${data.STAMPSTATUS == 'SUCCESS'}">
                                        <input type="BUTTON" onclick="javascript:update('${data.REQIDA2A2}','${data.PARTIDA2A2}');" value="수정">
                                        </c:if>
                                        </td>
                                        <td class="tdwhiteM" align="center">${data.HPSENDDATE}</td>
                                        <td class="tdwhiteM" align="center">${data.STAMPSTATUSNAME}</td>
                                        <td class="tdwhiteM" align="center"><input type="BUTTON" onclick="javascript:reStamping('${data.REQIDA2A2}');" value="재전송"></td>
                                        <td class="tdwhiteM" align="center">${data.HPSENDSTATUSNAME}</td>
                                        <td class="tdwhiteM" align="center">
                                        
                                         <c:choose>
                                            <c:when test="${data.HPSENDSTATUS == 'Y'}">
                                                <input type="BUTTON" onclick="javascript:sendHp('${data.REQIDA2A2}');" value="재전송">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="BUTTON" onclick="javascript:sendHp('${data.REQIDA2A2}');" value="전송">
                                            </c:otherwise>
                                        </c:choose>
                                        </td>
                                        <td class="tdwhiteM" align="center">${data.IMGICONURLSTR}</td>
                                        <td class="tdwhiteM" align="center">${data.PLANEICONURLSTR}</td>
                                        <td class="tdwhiteM" align="center">${data.PLANE3DICONURLSTR}</td>
                                        <td class="tdwhiteM" align="center">${data.STEPICONURLSTR}</td>
                                        <td class="tdwhiteM" align="center">${data.IGSICONURLSTR}</td>
                                        <td class="tdwhiteM" align="center"><input type="BUTTON" onclick="javascript:createMaterial('${data.REQIDA2A2}','${data.PARTNUMBER}');" value="등록"></td>
                                    </tr> 
                                    </c:forEach>
                               </tbody>
                            </table>
                          </div>
                        </td>  
                     </tr>                          
                </tbody>
             </table>
        </td>
    </tr>
</table>
</form>    