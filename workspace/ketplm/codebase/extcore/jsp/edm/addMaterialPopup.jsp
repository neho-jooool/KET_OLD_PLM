<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />

<script type="text/javascript">

    function addRow(){
    	var addContent;
            addContent = "<tr>";
            addContent +="<td class=\"tdwhiteM\" align=\"center\"><input type=\"BUTTON\" onclick=\"deleteRow(this);\" value=\"삭제\"></td>"; 
            addContent +="<td class=\"tdwhiteM\" align=\"center\"><input type=\"text\" name=\"materialAttr\"></td>" 
            addContent +="<td class=\"tdwhiteM\" align=\"center\"><input type=\"text\" name=\"materialPropertiesAttr\"></td>" 
            addContent +="</tr>";
        	$('#material > tbody:last').append(addContent);        	
    } 
    
    function deleteRow(obj){
    	 var tr = $(obj).parent().parent();
    	 tr.remove();
    }
    
    function save(){
    	if(check()){
	        $.ajax({
	            type: 'post',
	            async: false,
	            url: '/plm/ext/edm/saveMaterial.do',
	            data: $('[name=materialForm]').serialize(),
	            success    : function(data){
	            	if(data == 'OK') alert('저장하였습니다.');
	            },
	            error    : function(xhr, status, error){
	                         alert(xhr+"  "+status+"  "+error);
	                         
	            }
	        });     	
    	}else{
    		   alert('조립품 재질정보가 없습니다.')
    	}
    	
    	
    }
    
    function check(){
        var check = true;
        try{
            
        	//if($('#material >tbody >tr').length == "1") check = false;
        	
            $("input[name=materialAttr]").each(function() {
                  if(this.value == "") check = false;
             });
            $("input[name=materialPropertiesAttr]").each(function() {
                  if(this.value == "") check = false;
             });
              
        }catch(e){
            
            return false;
        }
        return check;
    }
    
</script>


<form id="materialForm" name="materialForm" method="post" autocomplete="off">
<input type="hidden" name="partOid" value="${partOid}"> 
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
                            <td class="font_01">조립품 재질 등록</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space5"></td>
                </tr>
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:addRow();"
                                                class="btn_blue">추가</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:save();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:self.close();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
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
            <table id="material" class="table-style-01" width="100%" border="0" cellspacing="0" cellpadding="0">
               <thead>
                <col width="120">
                <col width="*">
                <col width="*">
                <tr>
                    <td class="tdwhiteM" align="center"></td>
                    <td class="tdwhiteM" align="center">조립품 재질명</td>
                    <td class="tdwhiteM" align="center">조립품 재질속성</td>
                </tr>
               </thead>
               <tbody id="list">
					<c:forEach var="data" items="${materialDTOList}" varStatus="state">
	                    <tr>
	                    <td class="tdwhiteM" align="center"><input type="BUTTON" onclick="deleteRow(this);" value="삭제"></td>
	                    <td class="tdwhiteM" align="center"><input type="text" name="materialAttr" value="${data.material}"></td>
	                    <td class="tdwhiteM" align="center"><input type="text" name="materialPropertiesAttr" value="${data.materialProperties}"></td>
	                    </tr>
					</c:forEach>
               </tbody>           
            </table>
        </td>
    </tr>
</table> 
  </form>
 