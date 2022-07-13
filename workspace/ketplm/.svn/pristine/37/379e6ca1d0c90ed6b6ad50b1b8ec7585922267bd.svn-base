<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                                    e3ps.dms.entity.KETDevelopmentRequest,
                                    e3ps.dms.entity.KETRequestPartList,
                                    e3ps.dms.entity.KETCarYearlyAmount,
                                    e3ps.dms.entity.KETRequestPartLink,
                                    e3ps.dms.entity.KETPartCarLink,
                                    e3ps.dms.beans.DMSUtil,
                                    wt.fc.QueryResult,
                                    wt.fc.PersistenceHelper,
                                    java.util.Vector,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.util.DateUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%

    String oid = request.getParameter("oid");
    String pName = request.getParameter("pname");
    String divisionCode = "";
     KETDevelopmentRequest dr = null;

    if(oid!=null){
        dr = (KETDevelopmentRequest)CommonUtil.getObject(oid);
        divisionCode = StringUtil.checkNull(dr.getDivisionCode());
    }

    if(pName!=null){
        pName = pName.trim();
    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" height="90%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../portal/images/logo_popupbg.png">
              <table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <%if(divisionCode.equals("CA")){%>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02546") %><%--제품 차종별 년간 예상수량 상세조회--%></td>
                <%}else{%>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02545") %><%--제품 기기별 년간 예상수량 상세조회--%></td>
                <%}%>
              </tr>
            </table>
          </td>
          <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td valign="top">
        <table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
              <table border="0" cellspacing="0" cellpadding="0" width="770">
              <tr>
                <td class="space15"><b><%=pName%></b></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="770">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="770">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table width="770" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="100"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                <%if(divisionCode.equals("CA")){%>
                <td width="100"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                <%}else{%>
                <td width="100"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%></td>
                <%}%>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 1) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 2) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 3) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 4) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 5) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016", 6) %><%--{0}년차--%></td>
                <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></td>
                <td width="40" class="tdblueM">U/S<span class="red">*</span></td>
                <td width="50" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02166") %><%--옵션률--%><span class="red">*</span></td>
              </tr>
<%
                 KETRequestPartList pl = null;
                 QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
                 String partName=null;
                 String cName=null;
                 String carType=null;

         while(r.hasMoreElements()){
             pl = (KETRequestPartList) r.nextElement();
           partName=pl.getPartName().trim();

           if(partName.equals(pName)){
                 KETCarYearlyAmount cy = null;
                          QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

                 while(r1.hasMoreElements()){
                     cy = (KETCarYearlyAmount) r1.nextElement();
                     carType = cy.getCarTypeCode();
                             cName = DMSUtil.serCustomerNm(carType);
%>
                            <tr>
                                 <td class="tdwhiteM"><%=cName%>&nbsp;</td>
                                 <td class="tdwhiteM"><%=cy.getCarTypeCode()%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount1(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount2(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount3(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount4(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount5(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount6(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=StringUtil.getDouble(cy.getYearAmount1()+cy.getYearAmount2()+cy.getYearAmount3()+cy.getYearAmount4()+cy.getYearAmount5()+cy.getYearAmount6(), "", "###,###")%></td>
                 <td class="tdwhiteR"><%=cy.getApplyedUsage()%></td>
                 <td class="tdwhiteR0"><%=cy.getOptionRate()%></td>
                 </tr>
<%
               }
             }

         }
%>

            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="770" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
