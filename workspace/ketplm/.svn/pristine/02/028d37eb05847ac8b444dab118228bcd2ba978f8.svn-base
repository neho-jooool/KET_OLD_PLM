<%@ page import="java.util.Hashtable
              ,java.util.ArrayList
              ,java.util.Vector
              ,java.util.List
              ,java.util.Map
              ,java.util.HashMap
              ,e3ps.common.util.StringUtil
              ,e3ps.common.util.DateUtil
              ,e3ps.common.util.CommonUtil
              ,e3ps.common.util.KETObjectUtil
              ,e3ps.common.code.NumberCode
              ,e3ps.common.code.NumberCodeHelper
              ,e3ps.ecm.beans.ProdEcrBeans
              ,e3ps.ecm.entity.*
              ,e3ps.common.content.ContentInfo
              ,e3ps.common.content.ContentUtil
              ,e3ps.groupware.company.Department
              ,wt.org.WTUser
              ,wt.fc.QueryResult
              ,wt.fc.PersistenceHelper
              ,wt.query.*
              ,wt.content.ContentHelper
              ,wt.content.ContentHolder
              ,wt.content.ContentItem
              ,wt.session.SessionHelper
              ,wt.content.ApplicationData" %>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@page import="java.sql.Timestamp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td valign="top">
        <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01261") %><%--도면/BOM--%></td>
          <!-- td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEpm();" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "03245") %><%--활동추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLineMinus1('forms[0]', 'relEpmTable', 'chkSelectRelEpm', 'chkAllRelEpm', 'deleteRelEpmList');" class="btn_blue" onfocus='this.blur();'><%=messageService.getString("e3ps.message.ket_message", "03234") %><%--활동 삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td -->
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>      
      <!-- div id="div_scroll4" style="height:162;width:100%;overflow-x:auto;overflow-y:auto;" class="table_border" -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="relEpmTable">
          <tr class="">
            <td width="20"  class="tdblueM"><a href="#" onclick="javascript:popupRelEpm();"><b>+</b></a></td>
            <td width="40"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
            <td width="90"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01580") %><%--부품/도면번호--%></td>
            <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01579") %><%--부품/도면명--%></td>
            <td width="35"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
            <td width="55"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
            <td width="40"  class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02332") %><%--일괄--%></td>
            <td width="110" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00928") %><%--관련모부품번호--%></td>
            <td width="115" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01504") %><%--변경계획일--%></td>
            <td width="102" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02125") %><%--연계일정--%></td>
          </tr>
        </table>
      <!-- /div -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
          <td class="space15" align="right"><b>*<%=messageService.getString("e3ps.message.ket_message", "02126") %><%--연계일정은 양산단계 금형변경시 입력하세요--%></b></td>
        </tr>
     </table>
    </td>
  </tr>
</table>
