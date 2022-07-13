<%@page import="e3ps.project.moldPart.beans.MoldSubPartData"%>
<%@page import="e3ps.project.moldPart.MoldSubPart"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartManagerData"%>
<%@page import="e3ps.project.moldPart.MoldPartManager"%>
<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.MoldProject"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@include file="/jsp/common/multicombo.jsp" %>
<%
    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if("popup".equals(popup)) {
        tmpPopUp = "&popup=popup";
    }
    boolean ispart = CommonUtil.isMember("자동차사업부_금형부품측정");
    boolean isMoldPart = CommonUtil.isMember("자동차사업부_금형부품") || ispart;


    boolean isAdmin = CommonUtil.isAdmin();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01072") %><%--금형부품관리 보기--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
-->
</style>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script>
    $(document).ready(function(){
    	$(window).scrollTop(0);
    });
// 이미지 변경 시작
    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function MM_swapImgRestore() { //v3.0
      var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
    }

    function MM_findObj(n, d) { //v4.01
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && d.getElementById) x=d.getElementById(n); return x;
    }

    function MM_swapImage() { //v3.0
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
    }
// 이미지 변경 끝


    function displayTable(arg){
        if(document.getElementById(arg).style.display == ''){
            document.getElementById(arg).style.display = 'none';
            MM_swapImage(arg + "_img",'','/plm/portal/images/btn_open_s.gif',1);
         }else{
             document.getElementById(arg).style.display = '';
             MM_swapImage(arg + "_img",'','/plm/portal/images/btn_open.gif',1);
         }
    }

    function goModify(pOid, mOid){
        location.href = "/plm/jsp/project/moldPart/ModifyMoldPart.jsp?projectOid=" + pOid + "&managerOid=" + mOid + "<%=tmpPopUp %>";
    }

    function goDelete(pOid, mOid){
        if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')){
            location.href = "/plm/jsp/project/moldPart/MoldPartAction.jsp?mode=del&projectOid=" + pOid + "&managerOid=" + mOid + "<%=tmpPopUp %>";
        }

    }

    function excelDown(oid){

        document.forms[0].method = "post";
        document.forms[0].managerOid.value =oid
        document.forms[0].action = "/plm/jsp/project/moldPart/ExcelDownLoad.jsp";
        document.forms[0].submit();
    }

    function viewEtc(obj){
        var text = obj.value;
        var url = "/plm/jsp/project/moldPart/PopupEtc.jsp?mode=view";
        etcData = window.showModalDialog(url,text,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:200px; center:yes");
        if(typeof etcData == "undefined" || etcData == null) {
            return;
        }
    }

    function viewMoldProject(oid){
        var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
        openWindow(url, '',1050,800);

    }

</script>
</head>
<%
    String projectOid = request.getParameter("projectOid");
    //projectOid = "e3ps.project.MoldProject:162931";

    MoldProject moldProject = (MoldProject)CommonUtil.getObject(projectOid);
    String lastOid = CommonUtil.getOIDString(MoldPartHelper.getLastMoldProject(moldProject));

    String managerOid = "";
    long lOid = 0;
    if(request.getParameter("managerOid") != null && request.getParameter("managerOid").length() > 0){
        managerOid = request.getParameter("managerOid");
        lOid = CommonUtil.getOIDLongValue(managerOid);
    }
    //managerOid = "e3ps.project.moldPart.MoldPartManager:206022";
    Vector vector = MoldPartHelper.getMoldPartManagerListWithSubPart(projectOid);

    long lastMOid = 0;
%>
<body class="popup-background popup-space">
<form>
<input type="hidden" name="managerOid">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01072") %><%--금형부품관리 보기--%></td>
                <%if(!("popup".equals(popup))){ %>
                
                <%} %>
              </tr>
            </table></td>
        </tr>
         <tr>
          <td class="space10"></td>
        </tr>
      </table>

     <%for(int i = 0; i < vector.size(); i++){
         MoldPartManager manager = (MoldPartManager)vector.get(i);
         Vector subParts = manager.getSubParts();

         MoldPartManagerData data = new MoldPartManagerData(manager);
         long longOid = CommonUtil.getOIDLongValue(data.managerOid);

         lastMOid = longOid;

         String planDate = "&nbsp;";
         String moldDate = "&nbsp;";

         if(data.planDate != null && data.planDate.length() > 0){
             planDate = data.planDate;
         }

         if(data.moldDate != null && data.moldDate.length() > 0){
             moldDate = data.moldDate;
         }

     %>

       <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><a href="javascript:;" onclick="javascript:displayTable('<%=longOid %>');"><img src="/plm/portal/images/<%if(lOid != longOid){ %>btn_open_s.gif<%}else{ %>btn_open.gif<%} %>" alt="" border="0" id="<%=longOid %>_img"></a></td>
          <td><b><%=data.title%></b></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <td><a href="javascript:;" onclick="javaScript:excelDown('<%=data.managerOid%>')"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                  <%if(isMoldPart || isAdmin){ %>
                  <td width="5">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:goModify('<%=projectOid %>','<%=data.managerOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td width="5">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:goDelete('<%=projectOid %>','<%=data.managerOid %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <td width="5">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                <%} %>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      <table border="0" cellspacing="0" cellpadding="0" class="table-style-01" width="100%">
        <tr>
          <td width="130" class="title">Die No</td>
          <td width="130" class="tdwhiteL"><a href="javascript:;" onclick="javascript:viewMoldProject('<%=lastOid %>')"><%=data.dieNo %></a></td>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "02495") %><%--전체/진행/완료--%></td>
          <td width="130" class="tdwhiteL"><%=data.totalState%></td>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "01583") %><%--부품공정--%></td>
          <td width="130" class="tdwhiteL0"><%=data.partUser%></td>
        </tr>
        <tr>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "02880") %><%--출도일--%></td>
          <td width="130" class="tdwhiteL"><%=moldDate%></td>
          <td width="130" class="title"><%=messageService.getString("e3ps.message.ket_message", "01594") %><%--부품완료 예정일--%></td>
          <td width="130" class="tdwhiteL0" colspan="3"><%=planDate%></td>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="<%=longOid %>" <%if(lOid != longOid){ %>style="display:none"<%} %>>
        <tr>
          <td width="30" rowspan="2" class="tdblueM">No</td>
          <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%><br>
            <%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01076") %><%--금형부품설명--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01600") %><%--부품측정--%><br><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%><br><%=messageService.getString("e3ps.message.ket_message", "02650") %><%--조치사항--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02326") %><%--인계일--%></td>
          <td width="40" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
        </tr>
        <tr>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
        </tr>

        <%
        int total = subParts.size();
        for(int j = 0; j < subParts.size(); j++){
            MoldSubPart subPart = (MoldSubPart)subParts.get(j);
            MoldSubPartData subData = new MoldSubPartData(subPart);
            String material = subData.material;
            if(material == null || material.length() == 0){
                material = "&nbsp;";
            }

            String partClass = subData.partClass;
            if(partClass == null || partClass.length() == 0){
                partClass = "&nbsp;";
            }

            String partType = subData.partType;
            if(partType == null || partType.length() == 0){
                partType = "&nbsp;";
            }

            String endDate = subData.endDate;
            if(endDate == null || endDate.length() == 0){
                endDate = "&nbsp;";
            }

            String mType = subData.mType;
            if(mType == null || mType.length() == 0){
                mType = "&nbsp;";
            }

            String actionType = subData.actionType;
            if(actionType == null || actionType.length() == 0){
                actionType = "&nbsp;";
            }

            String transferDate = subData.transferDate;
            boolean isLate = false;
            if(transferDate == null || transferDate.length() == 0){
                transferDate = "&nbsp;";
            }else{
                isLate = planDate.compareTo(transferDate) < 0;
            }

            String etc = subData.etc;
            if(etc == null || etc.length() == 0){
                etc = "";
            }
            String sea = "";
            if(subData.quantity != null){
                double dea = Double.parseDouble(subData.quantity);
                int iea = (int)dea;
                sea = String.valueOf(iea);
            }

        %>
        <tr>
          <td width="30" class="tdwhiteM"><%=j + 1%></td>
          <td width="110" class="tdwhiteL" title="<%=subData.partNumber%>"><div style="width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partNumber%></nobr></div></td>
          <td width="130" class="tdwhiteL" title="<%=subData.partName%>"><div style="width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;"><nobr><%=subData.partName%></nobr></div></td>
          <td width="45" class="tdwhiteM"><%=sea%></td>
          <td width="45" class="tdwhiteM"><%=material%></td>
          <td width="60" class="tdwhiteM"><%=partClass%></td>
          <td width="60" class="tdwhiteM"><%=partType%></td>
          <td width="70" class="tdwhiteM"><%=endDate%></td>
          <td width="60" class="tdwhiteM"><%=mType%></td>
          <td width="60" class="tdwhiteM"><%=actionType%></td>
          <td width="70" class="tdwhiteM"><%if(isLate){%><span class="red"><%=transferDate%></span><%}else{ %><%=transferDate%><%} %></td>
          <td width="40" class="tdwhiteM0"><%if(etc.length() > 0){ %><a href="javascript:;" name="etc" id="etc" value="<%=etc%>" title="<%=etc%>" onclick="javascript:viewEtc(this);"><%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%></a><%}else{ %>&nbsp;<%} %></td>
        </tr>
        <%}%>
      </table>


      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <%}%>

      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright<%if("popup".equals(popup)){ %>_t<%} %>.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<script>

<%if(lOid > 0){%>
    location.href = "#<%=lOid%>";
<%}else{%>
    displayTable('<%=lastMOid%>');
    location.href = "#<%=lastMOid%>";
<%}%>

</script>
</form>
</body>
</html>
