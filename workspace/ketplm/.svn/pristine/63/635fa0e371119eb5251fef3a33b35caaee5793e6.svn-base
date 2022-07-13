<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<%-- <div class="sub-title-02 b-space5 float-l">
  <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%>기본정보
</div> --%>
<div class="b-space5">
  <table cellpadding="0" summary="" class="table-style-01">
    <colgroup>
      <col width="10%" />
      <col width="23%" />
      <col width="10%" />
      <col width="23%" />
      <col width="10%" />
      <col width="23%" />
    </colgroup>
    <tbody>
      <tr>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "02242")%><!-- 유형 --></td>
        <td class="left">${pjtInfo.taskType }</td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "00756")%><!-- 결재대상명 --></td>
        <td class="left" colspan="3">${pjtInfo.title }</td>
      </tr>
      <tr>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "03104")%><!-- 프로젝트 No --></td>
        <td class="left"><a href="javascript:openView('${pjtInfo.pjtOid }')">${pjtInfo.pjtNo }</a></td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "00370")%><!-- PM --></td>
        <td class="left">${pjtInfo.pjtPM }</td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "02428")%><!-- 작성일 --></td>
        <td class="left">${pjtInfo.pjtCreateDate }</td>
      </tr>
      <tr>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "03113")%><!-- 프로젝트명 --></td>
        <td class="left" colspan="3">${pjtInfo.pjtName }</td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "02847")%><!-- 최종사용처 --></td>
        <td class="left">${pjtInfo.pjtCustomer }</td>
      </tr>
      <tr>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "00625")%><!-- 개발구분 --></td>
        <td class="left">${pjtInfo.pjtDevType }</td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "01248")%><!-- 대표차종 --></td>
        <td class="left">${pjtInfo.pjtCarType }</td>
        <td class="right bgcol fontb"><%=messageService.getString("e3ps.message.ket_message", "00859")%><!-- 고객처 --></td>
        <td class="left">${pjtInfo.pjtSubcontractor }</td>
      </tr>
    </tbody>
  </table>
</div>
<div class="b-space5 clear">
  <iframe id="ifrPBO" name="ifrPBO" style="width: 100%; height: ${pjtInfo.iFrameHeight }; border: 0px;" src="${pjtInfo.iFrameSrc }"></iframe>
</div>