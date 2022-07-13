<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                e3ps.project.beans.*,
                e3ps.groupware.company.Department,
                wt.folder.Folder,
                wt.query.QuerySpec,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                e3ps.common.impl.Tree"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
    String oemtype = request.getParameter("oemtype");
    String tempViewType = "" ;
    String oid = request.getParameter("oid");
    
    Kogger.debug("ViewOEMTyp", null, oid, "ViewProjectType.jsp::oid = "+oid);
    String tempName = null;
    String tempCode = null;
    String tempPath = null;
    String tempSort = null;
    String tempDesc = null;
    String tempFolder = "";
    String docCheck = null;
    String docCheckValue  = null;
    StringTokenizer tempAttr = null;
    String oLevel = "0";
    Hashtable attrHash = new Hashtable();
    boolean isModify = false;
    boolean isRoot = false;
    boolean isDisabled = false;
    String isEcoCar = "No";
    String isRunningChange = "No";
    QueryResult qr = null;
    String button = "";
    String numberOid = "";
    if(oid!=null)
    {

        Object type = CommonUtil.getObject(oid);

        if(type instanceof NumberCode){
            NumberCode code = (NumberCode)CommonUtil.getObject(oid);
            tempName = code.getName();
            tempCode = code.getCode();

            if(code.getParent()==null){
//        NumberCode에서 parent가 없는 것. -> 1랩

                button = "1";
                if(oemtype.equals("PAYCONTRACTOR")){
                    button= "2";
                }


            }else{
//        NumberCode에서 parent가 있는 것. -> 1랩
                button = "2";
            }


        }else if(type instanceof OEMProjectType){
//      OEMProjectType View.

            if(oid.equals("root")){
                button="1";
            }else{
                OEMProjectType code = (OEMProjectType)CommonUtil.getObject(oid);
                numberOid = (code.getMaker() == null)?"":CommonUtil.getOIDString(code.getMaker());
                tempName = code.getName();
                tempCode = code.getCode();
                isDisabled= code.isIsDisabled();
                if(code.getIsEcoCar()){
                    isEcoCar = "Yes";
                }
                if(code.getIsRunningChange() != null && code.getIsRunningChange()){
                    isRunningChange = "Yes";
                }
                int aa = code.getOLevel();
                if(aa==0){
                    if(tempName.equals("SOP")){
                        tempViewType="SOP";
                    }else{
                        tempViewType="-";
                    }
                }if(aa==1){
                    tempViewType="Model";
                }else if(aa==2){
                    tempViewType="PROTO";
                }else if(aa==3){
                    tempViewType="PILOT1";
                }else if(aa==4){
                    tempViewType="PILOT2";
                }else if(aa==5){
                    tempViewType="M";
                }

                button = "3";
            }

        }


        //nCode = (NumberCode)CommonUtil.getObject(oid);
//    docCheck = new String(prjType.getPath());
//    Kogger.debug(docCheck);

//    if(nCode.getName().equals("ROOT")) isRoot = true;
//    tempName = nCode.getName();
//    tempCode = nCode.getCode();
//    tempPath = nCode.getPath();

//    oLevel = String.valueOf(prjType.getOLevel());


//    TreePath tp = new TreePath();
//    stack = tp.getTreePath(prjType);
    }



%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.impl.ParentChildLink"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>


<%@page import="e3ps.common.jdf.log.Logger"%>



<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="java.util.Stack"%>
<%@page import="e3ps.project.outputtype.OEMType"%>

<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=OEMType.toOEMType(oemtype).getComment()%></title>
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
function createType(oid)
{
    document.location.href="/plm/jsp/project/projectType/CreateOEMType.jsp?oid="+oid+"&oemtype=<%=oemtype%>";
}

function modType(oid)
{
//alert(oid);
    document.location.href="/plm/jsp/project/projectType/UpdateOEMType.jsp?oid="+oid+"&oemtype=<%=oemtype%>";
}

function delType(oid)
{
//alert(document.forms[0].oid.value);
if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')) {
            return;
        }
    document.ViewOEMType.cmd.value="deleteOEM";
    document.ViewOEMType.action="/plm/servlet/e3ps/ProjectOutPutTypeServlet?oid="+oid+"&oemtype=<%=oemtype%>&parentNc=<%=numberOid%>";
    document.ViewOEMType.submit();
}
function viewUpdate(oid, viewtype)
{
    document.ViewOEMType.cmd.value="updateViewType";
    document.ViewOEMType.action="/plm/servlet/e3ps/ProjectOutPutTypeServlet?oid="+oid+"&viewtype="+viewtype.value+"&oemtype=<%=oemtype%>";
    document.ViewOEMType.submit();
}


//parent.tree.location.reload();
//-->
</script>
</head>
<body>
<form name = ViewOEMType method="post">
<input type="hidden" name="cmd">
<input type=hidden name=oid value="<%=oid%>">
<input type=hidden name=oemtype value="<%=oemtype%>">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=OEMType.toOEMType(oemtype).getComment()%> <%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%></td>
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

              <%
              if(button.equals("1")){
              %>
                  <%
              }else  if(button.equals("2")){
                  %>
                  <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"  class="btn_blue" onclick="javascript:createType('<%=oid%>')" ><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>

                  <%
              }else  if(button.equals("3")){
                  %>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:modType('<%=oid%>')"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onclick="javascript:delType('<%=oid%>')"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <%
              }
                %>

              </tr>

            </table></td>
        </tr>
      </table>
      <%
      if(button.equals("3")){
      %>

      <table border="0" cellspacing="0" cellpadding="0" width="780">
          <tr>
              <td> <span class="red">*</span> <%=messageService.getString("e3ps.message.ket_message", "00031", OEMType.toOEMType(oemtype).getComment()) %><%--삭제시 해당 {0}를 사용하는 PMS모듈에 영향을 끼칠수 있습니다--%></td>
              </tr>
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
          <td width="630" class="tdwhiteL0"><%=StringUtil.checkNull(tempName) %>&nbsp;</td>
        </tr>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
          <td width="630" class="tdwhiteL0"><%=StringUtil.checkNull(tempCode)%>&nbsp;</td>
        </tr>
        <tr>
          <td width="150" class="tdblueL">친환경여부</td>
          <td width="630" class="tdwhiteL0"><%=isEcoCar%>&nbsp;</td>
          
        </tr>
        <tr>
           <td width="150" class="tdblueL">Running Change 여부</td>
           <td width="630" class="tdwhiteL0"><%=isRunningChange %>&nbsp;</td>
        </tr>
        <%
              if(oemtype.equals("CUSTOMEREVENT")){
              %>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02408") %><%--자동차일정 목록 View--%></td>
          <td width="630" class="tdwhiteL0"><%=tempViewType%>&nbsp;</td>
        </tr>
        <tr>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%> </td>
          <td width="630" class="tdwhiteL0"><%=isDisabled==false?messageService.getString("e3ps.message.ket_message", "01669")/*사용*/:messageService.getString("e3ps.message.ket_message", "01639")/*비사용*/%>&nbsp;</td>
        </tr>


        <%} %>
      </table>
        <%
      }else if(button.equals("2")){
          %>
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
          <%  if(oemtype.equals("CUSTOMEREVENT")){  %>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01672") %><%--사용여부--%></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02405") %><%--자동차일정 View Type--%></td>
          <%}else{ %>
          <td width="390" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
          <td width="390" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
          <td width="390" class="tdblueM0">친환경여부</td>
          <td width="390" class="tdblueM0">Running Change 여부</td>
          <%} %>
        </tr>
        <%

        QuerySpec qs4 = new QuerySpec();
          int idx4 = qs4.appendClassList(OEMProjectType.class, true);
          SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", CommonUtil.getOIDLongValue(oid) );
          qs4.appendWhere(sc4, new int[] {idx4});
          qs4.appendAnd();
        SearchCondition sc5 = new SearchCondition(OEMProjectType.class, "oemType", "=", oemtype);
        qs4.appendWhere(sc5, new int[] {idx4});
        ClassAttribute ca = null;
        if("CARTYPE".equals(oemtype)){
            ca = new ClassAttribute(OEMProjectType.class,"name");
        }else{
            ca = new ClassAttribute(OEMProjectType.class,"code");
        }

        qs4.appendOrderBy(new OrderBy(ca, false), new int[] { idx4 });
          QueryResult qr4 = PersistenceHelper.manager.find( qs4 );
          int idx = 0;
          while(qr4.hasMoreElements()){
              Object[] o2 = (Object[])qr4.nextElement();
              OEMProjectType opt = (OEMProjectType)o2[0];
              String optName = opt.getName();
              String optCode = opt.getCode();
              String ecoCar = "No";
              String runningChange = "No";
              if(opt.getIsEcoCar()){
                  ecoCar = "Yes";
              }
              if(opt.getIsRunningChange() != null &&opt.getIsRunningChange()){
                  runningChange = "Yes";
              }

              %>
              <tr>
              <td <%  if(oemtype.equals("CUSTOMEREVENT")){  %>width="150"<%}else{ %>width="390"<%} %> class="tdwhiteR"><%=StringUtil.checkNull(optName)%>&nbsp;</td>
              <td <%  if(oemtype.equals("CUSTOMEREVENT")){  %>width="150"<%}else{ %>width="390"<%} %> class="tdwhiteR"><%=StringUtil.checkNull(optCode)%>&nbsp;</td>
              <%if(!oemtype.equals("CUSTOMEREVENT")){%>
              <td width="390" class="tdwhiteR"><%=StringUtil.checkNull(ecoCar)%>&nbsp;</td>
              <td width="390" class="tdwhiteR"><%=StringUtil.checkNull(runningChange)%>&nbsp;</td>
        	  <%}%>
              
              <%
              if(oemtype.equals("CUSTOMEREVENT") && !optName.equals("SOP")){
              %>
              <td width="150" class="tdwhiteR"><%=opt.isIsDisabled()==false?messageService.getString("e3ps.message.ket_message", "01669")/*사용*/:messageService.getString("e3ps.message.ket_message", "01639")/*비사용*/%>&nbsp;</td>
              <td width="150" class="tdwhiteR0"><input type=hidden name="updateOid<%=idx++%>" value="<%=opt.getPersistInfo().getObjectIdentifier().getStringValue() %>">
                  <select name="oLevel" class="fm_jmp" style="width:180" onchange="javascript:viewUpdate('<%=opt.getPersistInfo().getObjectIdentifier().getStringValue()%>',this)">
                    <option value="0" <%if(0 == opt.getOLevel()){ %> selected <%} %>>[ <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%> ]</option>
                    <option value="1" <%if(1 == opt.getOLevel()){ %> selected <%} %>><%=messageService.getString("e3ps.message.ket_message", "00328") %><%--Model 고정--%></option>
                    <option value="2" <%if(2 == opt.getOLevel()){ %> selected <%} %>>PROTO</option>
                    <option value="3" <%if(3 == opt.getOLevel()){ %> selected <%} %>>PILOT1</option>
                    <option value="4" <%if(4 == opt.getOLevel()){ %> selected <%} %>>PILOT2</option>
                    <option value="5" <%if(5 == opt.getOLevel()){ %> selected <%} %>>M</option>
                  </select>
                </td>
              <%
              }else if(oemtype.equals("CUSTOMEREVENT") && optName.equals("SOP")){
              %>
               <td width="150" class="tdwhiteR"><%=opt.isIsDisabled()==false?messageService.getString("e3ps.message.ket_message", "01669")/*사용*/:messageService.getString("e3ps.message.ket_message", "01639")/*비사용*/%>&nbsp;</td>
               <td width="150" class="tdwhiteR0">SOP</td>
              <%
              }
              %>

            </tr>
          <%
          }
      %>

      </table>
      <%
      }else{
      %>
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
          <td width="390" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
          <td width="390" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
        </tr>
        <%
        ArrayList list = null;
               NumberCode code = (NumberCode)CommonUtil.getObject(oid);
            list = (ArrayList)NumberCodeHelper.getChildNumberCode(code);
            for(int i = 0 ; i < list.size() ; i++){
                String codeName = ((NumberCode)list.get(i)).getName();
                String codeCode = ((NumberCode)list.get(i)).getCode();

        %>

        <tr>
          <td width="390" class="tdwhiteR"><%=StringUtil.checkNull(codeName)%>&nbsp;</td>
          <td width="390" class="tdwhiteR0"><%=StringUtil.checkNull(codeCode)%>&nbsp;</td>
        </tr>
        <%
          }
        %>
      </table>

      <%

      }
      %>
      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
