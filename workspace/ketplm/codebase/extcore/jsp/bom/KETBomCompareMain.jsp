<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%
/*
String srcPartNo="H641071";
String srcPartRev="0.0";

String desPartNo="H641071-2";
String desPartRev="0.0";

String src = "KETBomCompare.jsp?srcPartNo="+srcPartNo+"&srcPartRev="+srcPartRev+"&desPartNo="+desPartNo+"&desPartRev="+desPartRev;
*/
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
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript" src="/plm/extcore/js/bom/bom.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	//부품 suggest
    SuggestUtil.bind('PARTNO', 'srcPartNo');
    SuggestUtil.bind('PARTNO', 'desPartNo');
});
</script>
<script type="text/javascript">
function doCompare()
{
	var cform = document.search;
	
	if(cform.srcPartName.value=="")
	{
		//alert(LocaleUtil.getMessage('09371'));//비교 부품이 없습니다. 비교 부품을 검색하세요.
		alert('<%=messageService.getString("e3ps.message.ket_message", "09371")%>');//비교 부품이 없습니다. 비교 부품을 검색하세요.
		return;
	}
	
	if(cform.desPartName.value=="")
    {
		//alert(LocaleUtil.getMessage('09372'));//비교 대상 부품이 없습니다. 비교 대상 부품을 검색하세요.
		alert('<%=messageService.getString("e3ps.message.ket_message", "09372")%>');//비교 대상 부품이 없습니다. 비교 대상 부품을 검색하세요.
        return;
    }
	
	cform.target = "compareList";
	cform.action = "KETBomCompare.jsp";
	cform.submit();
}

function setPartNo1(objArr){
    var srcPartNo= "";
    var srcRev= "";
    var srcNewRev= "";
    var srcPartName= "";
    var srcPartType= "";
    for ( var i = 0; i < objArr.length; i++ ) {
    	//srcPartType+= objArr[i][4];
    	srcRev+= objArr[i][3];//+'.'+objArr[i][9];
    	
    	/*
    	if(srcPartType=="D")
    	{
    		srcPartNo+= objArr[i][5];
    		srcPartName+= objArr[i][6];
    	}else{
    		srcPartNo+= objArr[i][1];
    		srcPartName+= objArr[i][2];
    	}*/
    	srcPartNo+= objArr[i][1];
    	srcPartName+= objArr[i][2];
    	srcNewRev+= objArr[i][12];//+'.'+objArr[i][9];
    }
    //if(srcPartNo.length > 0) partNo1= srcPartNo.substring(0, srcPartNo.length-1);
    $('[name=srcPartNo]').val(srcPartNo);
    $('[name=srcPartType]').val(srcPartType);
    $('[name=srcRev]').val(srcRev);
    $('[name=srcPartName]').val(srcPartName);
    $('[name=srcNewRev]').val(srcNewRev);
    
    
    bom.getSrcRevList();
}

function setPartNo2(objArr){
	var desPartNo= "";
    var desRev= "";
    var desNewRev= "";
    var desPartName= "";
    var desPartType= "";
    for ( var i = 0; i < objArr.length; i++ ) {
        //desPartType+= objArr[i][4];
        desRev+= objArr[i][3];//+'.'+objArr[i][9];
        
        desPartNo+= objArr[i][1];
        desPartName+= objArr[i][2];
        desNewRev+= objArr[i][12];//+'.'+objArr[i][9];
    }
    //if(desPartNo.length > 0) partNo1= desPartNo.substring(0, desPartNo.length-1);
    $('[name=desPartNo]').val(desPartNo);
    $('[name=desPartType]').val(desPartType);
    $('[name=desRev]').val(desRev);
    $('[name=desPartName]').val(desPartName);
    $('[name=desNewRev]').val(desNewRev);
    
    bom.getDesRevList();
}


function getPartInfo(target)
{
	var cform = document.search;
	
	var srcPartNo= cform.srcPartNo.value;
    var srcRev= cform.srcRev.value;
    var desPartNo= cform.desPartNo.value;
    var desRev= cform.desRev.value;
	
	if(event.keyCode==13)
	{
		   if(target=="SRC")
		   {
			   if(srcPartNo=="")
			   {
				//alert(LocaleUtil.getMessage('09373'));   //비교 부품 번호가 없습니다.
				alert('<%=messageService.getString("e3ps.message.ket_message", "09373")%>');   //비교 부품 번호가 없습니다.
			    return;
			   }
			   
			   if(srcRev=="")
               {
                //alert(LocaleUtil.getMessage('09374'));   //비교 부품 버전이 없습니다.
                alert('<%=messageService.getString("e3ps.message.ket_message", "09374")%>');   //비교 부품 버전이 없습니다.
                return;
               }
			   
			   alert(srcPartNo+":src:"+srcRev);
			   
			   //document.hiddenFrame.location.href = "KETBOMGetPartInfo.jsp?obj= partno=";
			   
		   }else
		   {
			   if(desPartNo=="")
               {
                //alert(LocaleUtil.getMessage('09375'));   //비교 대상 부품 번호가 없습니다.
                alert('<%=messageService.getString("e3ps.message.ket_message", "09375")%>');   //비교 대상 부품 번호가 없습니다.
                return;
               }
               
               if(desRev=="")
               {
                //alert(LocaleUtil.getMessage('09376'));   //비교 대상 부품 버전이 없습니다.
                alert('<%=messageService.getString("e3ps.message.ket_message", "09376")%>');   //비교 대상 부품 버전이 없습니다.
                return;
               }
			   alert(desPartNo+":des:"+desRev);
               //document.hiddenFrame.location.href = "";
		   }
	}
}
</script>
</head>
<body onLoad="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01">BOM <%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%></td>
            </table>
          </td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        <tr>
             <td height="10"></td>
        </tr>
      </table>
    </td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doCompare();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search.reset();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:document.compareList.saveExcel();" class="btn_blue">Excel <%=messageService.getString("e3ps.message.ket_message", "02453") %><%--Excel 저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                
                 <td width="5">&nbsp;</td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window,close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                 </table> 
                 </td>
              </tr>
            </table></td>
        </tr>
      </table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      
            
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>     
       
       <form name="search">
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%><span class="red">*</span></td>
          <td width="30%" class="tdwhiteL"><input type="text" id="srcPartNo" name="srcPartNo" class="txt_field" style="width: 70%" onChange="bom.getSrcRevList();"> 
                            <a href="javascript:SearchUtil.selectOnePart('setPartNo1','pType=');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('srcPartNo', 'srcRev', 'srcPartName', 'srcNewRev');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            <script type="text/javascript">
                        function setSrcPartNo(objArr){
                                var srcPartNo= "";
                                for ( var i = 0; i < objArr.length; i++ ) {
                                	srcPartNo+= objArr[i][1] + ",";
                                }
                                if(srcPartNo.length > 0) srcPartNo= srcPartNo.substring(0, srcPartNo.length-1);
                                $('[name=srcPartNo]').val(srcPartNo);
                        }
                        </script>
          </td>
          <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%><span class="red">*</span></td>
          <td width="30%" class="tdwhiteL"><input type="text" id="desPartNo" name="desPartNo" class="txt_field" style="width: 70%" onChange="bom.getDesRevList();"> 
                            <a href="javascript:SearchUtil.selectOnePart('setPartNo2','pType=');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('desPartNo', 'desRev', 'desPartName', 'desNewRev');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            <script type="text/javascript">
                        function setDesPartNo(objArr){
                                var desPartNo= "";
                                for ( var i = 0; i < objArr.length; i++ ) {
                                	desPartNo+= objArr[i][1] + ",";
                                }
                                if(desPartNo.length > 0) desPartNo= desPartNo.substring(0, desPartNo.length-1);
                                $('[name=desPartNo]').val(desPartNo);
                        }
                        </script>
          </td>
        </tr>
        <tr>
          <td width="20%" class="tdblueL">Rev<%--Rev--%><span class="red">*</span></td>
          <td width="30%" class="tdwhiteL"><select id="srcRev" name="srcRev"  class="fm_jmp" style="width:70px;border-style:groove;background:#FFFFFF;text-align:left;height:20px;margin-bottom: 2px;"></select></td>
          <td width="20%" class="tdblueL">Rev<%--Rev--%><span class="red">*</span></td>
          <td width="30%" class="tdwhiteL"><select id="desRev" name="desRev"  class="fm_jmp" style="width:70px;border-style:groove;background:#FFFFFF;text-align:left;height:20px;margin-bottom: 2px;"></select></td>
        </tr>
        <tr>
          <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="30%" class="tdwhiteL"><input id="srcPartName" name="srcPartName" type="text" class="txt_field"  style="width:100%;border-style:groove;background:#e9e9e9;text-align:left;height:20px;margin-bottom: 2px;" readonly></td>
          <td width="20%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="30%" class="tdwhiteL"><input id="desPartName" name="desPartName" type="text" class="txt_field"  style="width:100%;border-style:groove;background:#e9e9e9;text-align:left;height:20px;margin-bottom: 2px;" readonly></td>
        </tr>
      </table>
      
        </form>
        
      </td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
<table width="100%" height="600" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>      
       <table width="100%" height="600" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td height="600"><iframe id="compareList" name="compareList" src="" style="width:100%; height:600px;border:0px;" frameborder="0"></iframe></td>
        </tr>
      </table>
      </td>
</tr>
</table>
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>