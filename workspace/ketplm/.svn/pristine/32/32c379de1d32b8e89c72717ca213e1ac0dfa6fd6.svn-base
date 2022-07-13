<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="e3ps.common.content.uploader.*"%>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.poi.ss.usermodel.Cell" %>
<%@ page import="org.apache.poi.ss.usermodel.Row" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFSheet" %>
<%@ page import="org.apache.poi.xssf.usermodel.XSSFWorkbook" %>
<%@page import=" ext.ket.bom.util.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.*"%>
<%@ page import="ext.ket.part.entity.dto.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%

String ecoNumber      = (String)request.getParameter("ecoNumber");
String topPartNo      = (String)request.getParameter("topPartNo");
String topRev      = (String)request.getParameter("topRev");

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript">
function alertMsg(msg)
{
	alert(msg);
}

function addRow(partNo, partOid, rPartOid, oldPWeight, oldSWeight, oldTWeight, oldMType, oldMaterial, oldMaterial2, newPWeight, newSWeight, newTWeight, newMType, newMaterial, newMaterial2)
{
	var tbObject = document.getElementById("rowdata");
	var lastRow = tbObject.rows.length;
	var row = tbObject.insertRow(lastRow);
	row.innerHTML = "<td height='20' width='12%' align='center'>"
	+"<input type='hidden' name='partNo' value='"+partNo+"'><input type='hidden' name='rPartOid' value='"+rPartOid+"'><input type='hidden' name='partOid' value='"+partOid+"'>"+partNo+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='oldPWeight' value='"+oldPWeight+"'>"+oldPWeight+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='oldSWeight' value='"+oldSWeight+"'>"+oldSWeight+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='oldTWeight' value='"+oldTWeight+"'>"+oldTWeight+"</td>"
	+"<td width='11%' align='center'><input type='hidden' name='oldMaterialType' value='"+oldMType+"'>"+"<input type='hidden' name='oldMaterial' value='"+oldMaterial+"'>"+oldMaterial2+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='newPWeight' value='"+newPWeight+"'>"+newPWeight+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='newSWeight' value='"+newSWeight+"'>"+newSWeight+"</td>"
	+"<td width='11%' align='right'><input type='hidden' name='newTWeight' value='"+newTWeight+"'>"+newTWeight+"</td>"
	+"<td width='11%' align='center'><input type='hidden' name='newMaterialType' value='"+newMType+"'>"+"<input type='hidden' name='newMaterial' value='"+newMaterial+"'>"+newMaterial2+"</td>";
}

function addRow2(partNo, partOid, rPartOid, oldPWeight, oldSWeight, oldTWeight, oldMType, oldMaterial, oldMaterial2, newPWeight, newSWeight, newTWeight, newMType, newMaterial, newMaterial2)
{
	var tbObject = document.getElementById("rowdata");
	var lastRow = tbObject.rows.length;
	var row = tbObject.insertRow(lastRow);
	row.innerHTML = "<td height='20' width='12%' align='center' style='color: red;'>"
	+"<input type='hidden' name='partNo' value='"+partNo+"'><input type='hidden' name='rPartOid' value='"+rPartOid+"'><input type='hidden' name='partOid' value='"+partOid+"'>"+partNo+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='oldPWeight' value='"+oldPWeight+"'>"+oldPWeight+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='oldSWeight' value='"+oldSWeight+"'>"+oldSWeight+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='oldTWeight' value='"+oldTWeight+"'>"+oldTWeight+"</td>"
	+"<td width='11%' align='center' style='color: red;'><input type='hidden' name='oldMaterialType' value='"+oldMType+"'>"+"<input type='hidden' name='oldMaterial' value='"+oldMaterial+"'>"+oldMaterial2+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='newPWeight' value='"+newPWeight+"'>"+newPWeight+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='newSWeight' value='"+newSWeight+"'>"+newSWeight+"</td>"
	+"<td width='11%' align='right' style='color: red;'><input type='hidden' name='newTWeight' value='"+newTWeight+"'>"+newTWeight+"</td>"
	+"<td width='11%' align='center' style='color: red;'><input type='hidden' name='newMaterialType' value='"+newMType+"'>"+"<input type='hidden' name='newMaterial' value='"+newMaterial+"'>"+newMaterial2+"</td>";
}

var validationError = false;
function addRowDatas()
{
	validationError = false;
	var partOid = opener.mPartOid;
	var rpartOid = opener.mrPartOid;
	var partNo = opener.mPartNo;
	var oldPWeight = opener.mOldWeight;
	var oldSWeight = opener.mOldSweight;
	var oldTWeight = opener.mOldTweight;
	var oldMaterialType = opener.mOldMaterialType;
	var oldMaterial = opener.mOldMaterial;
	var oldMaterial2 = opener.mOldMaterial2;
	var newPWeight = opener.mNewWeight;
	var newSWeight = opener.mNewSweight;
	var newTWeight = opener.mNewTweight;
	var newMaterialType = opener.mNewMaterialType;
	var newMaterial = opener.mNewMaterial;
	var newMaterial2 = opener.mNewMaterial2;
	var newProblem = opener.mNewProblem;
	    
	//alert(partOid.length);
	if(partOid.length>0)
	{
		for(i=0; i < partOid.length; i++)
	    {
	        //alert(oldMaterialType[i]+"--"+newMaterialType[i]);
	        //if(rpartOid[i]!='')
	        // 안정화에서 수정함.
	        // rpartOid[i]가 있던 없던 중량은 업데이트 하고, 재질은 있는 경우만 업데이트 함.
	        if(newProblem[i] == 'Y'){
	        	validationError = true;
	        	addRow2(partNo[i], partOid[i], rpartOid[i], oldPWeight[i], oldSWeight[i], oldTWeight[i], oldMaterialType[i], oldMaterial[i], oldMaterial2[i], newPWeight[i], newSWeight[i], newTWeight[i], newMaterialType[i], newMaterial[i], newMaterial2[i]);
	        }else{
	        	if(rpartOid[i]!=''){
	        		addRow(partNo[i], partOid[i], rpartOid[i], oldPWeight[i], oldSWeight[i], oldTWeight[i], oldMaterialType[i], oldMaterial[i], oldMaterial2[i], newPWeight[i], newSWeight[i], newTWeight[i], newMaterialType[i], newMaterial[i], newMaterial2[i]);
	        	}else{
	        	   addRow(partNo[i], partOid[i], rpartOid[i], oldPWeight[i], oldSWeight[i], oldTWeight[i], oldMaterialType[i], oldMaterial[i], oldMaterial2[i], newPWeight[i], newSWeight[i], newTWeight[i], newMaterialType[i], newMaterial[i], newMaterial2[i]);
	        	}
	        }
	    }
	}else
	{
		var tbObject = document.getElementById("rowdata");
	    var lastRow = tbObject.rows.length;
	    var row = tbObject.insertRow(lastRow);
	    
	    row.innerHTML = "<td height='20' width='100%' align='center' colspan='9'><%=messageService.getString("e3ps.message.ket_message", "09415") %></td>";//데이터가 없습니다.
	}
	
	
}

function savePart()
{
	if(validationError){
		alert('빨강 색으로 표시된 라인에 저장할 수 없는 에러가 발생했습니다.\n총중량이 부품중량보다 적거나, 부품중량이 음수입니다. \n이유는 해당 부품 또는 하위 구성 부품의 중량정보가 올바르지 않습니다.\n관리자에게 문의해 주십시요.');
		return;
	}
	
	var tbObject = document.getElementById("rowdata");
    var lastRow = tbObject.rows.length;
    var zeroRowData = tbObject.rows[0].outerHTML;
    //alert(lastRow);
    if(zeroRowData.indexOf('<%=messageService.getString("e3ps.message.ket_message", "09415") %>')!=-1)//데이터가 없습니다.
    {
    	alert('<%=messageService.getString("e3ps.message.ket_message", "09416") %>');//저장할 데이터가 없습니다.
    }else
    {
        	
               
    	    var saveform = document.all.saveList;
    	    
    	   
    	    if(tbObject.rows.length>1)
    	   	{
    	    	
	    	    for(var i=0; i < tbObject.rows.length;i++)
	    	    {
	    	    	
	    	    	/*
                    alert(saveform.partNo[i].value);
                    alert(saveform.oldMaterial[i].value);
                    alert(saveform.newMaterial[i].value);
                    */
                        
	    	    	if(saveform.oldMaterialType[i].value=='')
	    	    	{
	    	    		  alert('<%=messageService.getString("e3ps.message.ket_message", "09466") %>');//부품의 재질속성이 없습니다.
	    	    		  return;
	    	    	}
	    	    	
	    	    	if(saveform.newMaterialType[i].value=='')
	                {
	    	    		  // 재질이 없어도 중량은 업데이트 되도록 수정됨 tklee 15.01.28 - 중량이 재질과 무관하게 필요함 즉 반제품아래 반제품만 존재함
	                      // alert('<%=messageService.getString("e3ps.message.ket_message", "09467") %>');//원재료의 재질속성이 없습니다.
	                      // return;
	                }
	    	    	
	    	    	if(saveform.oldMaterialType[i].value!=saveform.newMaterialType[i].value)
	    	    	{
	    	    		alert('<%=messageService.getString("e3ps.message.ket_message", "09468") %>');//부품과 원재료의 재질 속성이 다릅니다.
	                    return;
	    	    	}
	    	    }
    	   	}else
    	   	{
    	   		if(saveform.oldMaterialType.value=='')
                {
                      alert('<%=messageService.getString("e3ps.message.ket_message", "09466") %>');//부품의 재질속성이 없습니다.
                      return;
                }
                
                if(saveform.newMaterialType.value=='')
                {
                	  // 재질이 없어도 중량은 업데이트 되도록 수정됨 tklee 15.01.28 - 중량이 재질과 무관하게 필요함 즉 반제품아래 반제품만 존재함
                      // alert('<%=messageService.getString("e3ps.message.ket_message", "09467") %>');//원재료의 재질속성이 없습니다.
                      // return;
                }
                
                
                if(saveform.oldMaterialType.value!=saveform.newMaterialType.value)
                {
                    alert('<%=messageService.getString("e3ps.message.ket_message", "09468") %>');//부품과 원재료의 재질 속성이 다릅니다.
                    return;
                }
    	   	}
    	    
    	    saveform.action="./KETBomMaterialSave.jsp?cnt="+lastRow;
        	saveform.submit();
    }
}

function resultMsg(msg)
{
	alert(msg);
	window.close();
}


function Msg(msg)
{
    alert(msg);
}

</script>
</head>
<body width="100%" height="100%" oncontextmenu="return false" onLoad="addRowDatas();">
<form name="saveList" method="post" target="hiddenFrame">
<input type="hidden" name="ecoNumber" value="<%=ecoNumber%>">
<input type="hidden" name="topPartNo" value="<%=topPartNo%>">
<input type="hidden" name="topRev" value="<%=topRev%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top" align="right">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09417") %><%--재질/중량 업데이트--%></td>
            </table>
          </td>
          <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
             <td height="10"></td>
        </tr>
      </table>
    </td>
</tr>
<tr>
    <td valign="top">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td> </td>
          <td align="right" width="70">
			      <table border="0" cellspacing="0" cellpadding="0">
			        <tr>
				        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:savePart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
				        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			        </tr>
			     </table> 
		</td>
		<td align="right" width="70">	     
			     <table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window,close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
			            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			        </tr>
			     </table> 
		</td>
		</tr>
		</table>	     
    </td>
</tr> 
<tr>
    <td height="10"></td>
</tr>   
<tr>
    <td id="result" name="result">
     <table width="100%" height="100%" border="1" cellspacing="2" cellpadding="2" style="border-color:#9ACCEB" oncontextmenu="return false">
     
	  <theader>
	  <tr>
	       <td height="20" width="12%" rowspan="2" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
	       <td width="44%" colspan="4" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "05153") %><%--변경 전--%></td>
	       <td width="44%" colspan="4" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "05154") %><%--변경 후--%></td>
	  </tr>
	  <tr>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04138") %><%--부품중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04139") %><%--스크랩중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04137") %><%--충중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04138") %><%--부품중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04139") %><%--스크랩중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "04137") %><%--충중량--%></td>
           <td width="11%" align="center" bgcolor="#E2EDF4"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
      </tr>
      </theader>
      <tbody id="rowdata" name="rowdata">      
      </tbody>
	</table>
    </td>
</tr>
</table>
</form>
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>