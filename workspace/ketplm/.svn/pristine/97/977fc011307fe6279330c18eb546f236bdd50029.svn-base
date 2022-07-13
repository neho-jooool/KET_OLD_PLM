<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper
                "%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.common.jdf.config.ConfigEx"%>
<%@page import="java.util.Hashtable"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>

<%
    //e3ps.common.jdf.config.Config conf = ConfigEx.getInstance("document");
    String oid = request.getParameter("oid");
    String oemtype = request.getParameter("oemtype");
    OEMProjectType docType = null;

    //String[] attrName = conf.getArray("document.attr.name.list");
    //String[] attr = conf.getArray("document.attr.list");


        docType = (OEMProjectType) CommonUtil.getObject(oid);
        String tempName = null;
        String tempCode = null;
        String tempPath = null;
        String tempSort = null;
        String tempFolder = "";
        String isDisabled = "";
        String isEcoCar = "";
        boolean isRunningChange = false;
        StringTokenizer tempAttr = null;
        Hashtable attrHash = new Hashtable();
        Hashtable attr2Hash = new Hashtable();

        boolean isModify = false;
        boolean isRoot = false;
        QueryResult qr = null;
        if(oid!=null)
        {
            docType = (OEMProjectType)CommonUtil.getObject(oid);

            if(docType.getName().equals("ROOT")) isRoot = true;
            tempName = docType.getName();
            tempCode = docType.getCode();
            tempPath = docType.getPath();
            isDisabled = "" + docType.isIsDisabled();
            isEcoCar = ""+ docType.getIsEcoCar();
            if(docType.getIsRunningChange() != null){
            	isRunningChange = docType.getIsRunningChange();	
            }
            


            qr = PersistenceHelper.manager.navigate(docType, "parent", ParentChildLink.class);
            while (qr.hasMoreElements())
            {
                OEMProjectType tempDct = (OEMProjectType)qr.nextElement();
                if(tempDct.getName().equals("ROOT"))
                    isRoot = true;
            }
        }
        //for(int i=0;i<attrName.length;i++)
        //{
         //   attrHash.put(attr[i],attrName[i]);
        //}

        if(docType!=null && docType.getDescription()!=null)
        {
            tempAttr = new StringTokenizer(docType.getDescription(),";");
            while(tempAttr.hasMoreElements())
            {
                String key = tempAttr.nextToken();
                if(attrHash.get(key)!=null)
                {
                    attr2Hash.put(key,tempAttr);

                }
            }
        }

%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.common.impl.ParentChildLink"%>
<%@page import="e3ps.project.outputtype.OEMType"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeType"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "00943") %><%--관리(수정)--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script language="JavaScript">
<!--
function updateData() {
    if (!check()){
        return;
    }
    else {
        document.UpdateOEMType.action = '/plm/servlet/e3ps/ProjectOutPutTypeServlet';
        document.UpdateOEMType.submit();
    }
}

function check() {
    var doc = document.forms[0];

    //alert("aaaaaaa");
    if( doc.oid.value=="" ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01959") %><%--수정할 카테고리를 선택해주세요--%>');
        return false;
    } else {
        if( doc.oid.value == 'root' ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "02838") %><%--최상의 카테고리는 수정할 수 없습니다--%>');
            return false;
        } else {
            //return true;
        }
    }
    if ( doc.NAME.value== "" ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02515") %><%--정보를 입력하십시오--%>');
        return false;
    }

    return true;
}


function selectCustomerEvent(inputObj, inputLabel) {
    var url = "/plm/jsp/common/code/SelectNumberCode.jsp?codetype=CUSTOMEREVENT";

    attache = window.showModalDialog(url,window,"help=no; scroll=yes; resizable=yes; dialogWidth=500px; dialogHeight:450px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }

    addList(attache, inputObj, inputLabel);
}

function addList(arrObj, inputObj, inputLabel) {
    if(arrObj.length == 0) {
        return;
    }

    var CustomerOid;//
    var CustomerName;//
    var CustomerCode

    for(var i = 0; i < arrObj.length; i++) {
        subarr = arrObj[i];
        CustomerOid = subarr[0];//
        CustomerCode = subarr[1];//
        CustomerName = subarr[2];

        inputObj.value = CustomerOid;
        inputLabel.value = CustomerName;
    }
}

function selectCustomerEvent1(inputObj, inputLabel) {
    var url = "/plm/jsp/common/code/SelectNumberCode.jsp?codetype=SUBCONTRACTOR";

    attache = window.showModalDialog(url,window,"help=no; scroll=yes; resizable=yes; dialogWidth=500px; dialogHeight:450px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    addList(attache, inputObj, inputLabel);
}
function cancel(oid, oemtype)
{
    document.location.href="/plm/jsp/project/projectType/ViewOEMType.jsp?oid="+oid+"&oemtype="+oemtype;
}

parent.tree.location.reload();
//-->
</script>
</head>
<body>
<form name="UpdateOEMType" method="post">
<input type="hidden" name="cmd" value="modifyOEM">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="oemtype" value="<%=oemtype %>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00949") %><%--관리자 화면--%><img src="/plm/portal/images/icon_navi.gif"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "00941") %><%--관리--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20">&nbsp;</td>
          <td class="font_03">&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:updateData();"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:cancel('<%=oid%>', '<%=oemtype%>')"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>

        <td width="630" class="tdwhiteL0"><input type="text" name="NAME" class="txt_field"  style="width:400" id="textfield2" value="<%=StringUtil.checkNull(tempName) %>" >

            </td>
        </tr>
        <!-- tr>
          <td width="150" class="tdblueL">코드</td>
          <td width="630" class="tdwhiteL0"><input type="text" name="CODE" class="txt_field"  style="width:400" id="textfield" value="<%=StringUtil.checkNull(tempCode) %>"></td>
        </tr -->
        <%if(CommonUtil.isAdmin() ){%>
         <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%></td>
          <td width="630" class="tdwhiteL0">
          [  <label>사용<input type=radio size=40 name="isDisabled" id=i value="false" style="position:relative;top:2px;" <%if(!isDisabled.equals("true")){ %>checked <%} %>></label>
             <label>비사용<input type=radio size=40 name="isDisabled" id=i value="true" style="position:relative;top:2px;" <%if(isDisabled.equals("true")){ %>checked <%} %>></label> ]
          </td>
        <%}%>
        </tr>
        <tr>
          <td width="150" class="tdblueL">친환경여부</td>
          
          <td width="630" class="tdwhiteL0">
          [  <label>Yes <input type=radio size=40 name="isEcoCar" id=i value="true" style="position:relative;top:2px;" <%if(isEcoCar.equals("true")){ %>checked <%} %>></label>
             <label>No<input type=radio size=40 name="isEcoCar" id=i value="false" style="position:relative;top:2px;" <%if(!isEcoCar.equals("true")){ %>checked <%} %>></label> ]
          </td>
          </td>
        </tr>
        <tr>
          <td width="150" class="tdblueL">Running Change 여부</td>
          <td width="630" class="tdwhiteL0">
          [  <label>Yes <input type=radio size=40 name="isRunningChange" value="true" style="position:relative;top:2px;" <%if(isRunningChange){ %>checked<%} %>></label>
             <label>No    <input type=radio size=40 name="isRunningChange" value="false" style="position:relative;top:2px;" <%if(!isRunningChange){ %>checked<%} %>></label> ]
          </td>
        </tr>

      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
