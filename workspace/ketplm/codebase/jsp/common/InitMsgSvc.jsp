<%@page language="java" pageEncoding="UTF-8"%>

<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.message.KETMessageService"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%
  KETMessageService messageService = KETMessageService.getMessageService(session);
  if (messageService == null) {
    Kogger.debug("InitMsgSvc", null, null, "===> messageService is null!! (system locale=" + Locale.getDefault() + ")");
    WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();
    messageService = new KETMessageService(KETMessageService.getUserLocale(usr));
    KETMessageService.setMessageService(session, messageService);
  }

  if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage()) == false) {
    Locale.setDefault(Locale.ENGLISH);
    Kogger.debug("InitMsgSvc", null, null, "===> locale changed forcely (default=" + Locale.getDefault() + ")");
  }
%>
