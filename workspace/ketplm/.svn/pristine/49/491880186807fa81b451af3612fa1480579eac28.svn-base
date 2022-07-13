<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>

<title>Insert title here</title>
<%
  String msg = "";
  String mode = "";
  if(request.getParameter("mode") != null){
    mode = request.getParameter("mode");
    //Kogger.debug("***** mode = " + mode);
  }

  if(mode.equals("simple")){
    msg = messageService.getString("e3ps.message.ket_message", "02460")/*저장되었습니다*/;
    String oid = request.getParameter("oid");
    String planDate = request.getParameter("planDate");

    String isCompleted = "";

    if(request.getParameter("isCompleted") != null && request.getParameter("isCompleted").length() > 0){
      isCompleted = request.getParameter("isCompleted");
      //Kogger.debug("55555" + isCompleted);
    }

    //Kogger.debug("FFEEEEE " + isCompleted);
    //Kogger.debug("OOOOOOO " + oid);
    //Kogger.debug("E###### " + planDate);

    Hashtable hash = new Hashtable();

    hash.put("oid", oid);
    hash.put("completed", isCompleted);
    hash.put("planDate", planDate);

    TrySchduleHelper.simpleSaveTrySchdule(hash);

  }else if(mode.equals("delete")){

    msg = messageService.getString("e3ps.message.ket_message", "01699")/*삭제되었습니다.*/;
    String oid = request.getParameter("oid");

    Hashtable hash = new Hashtable();

    hash.put("oid", oid);

    TrySchduleHelper.deleteTrySchdele(hash);

  }else if(mode.equals("save")){
    msg = messageService.getString("e3ps.message.ket_message", "01315"); // 등록 되었습니다
    String moldProject = request.getParameter("tempmold");
    String tryType = request.getParameter("tryType");
    String material = request.getParameter("tempmaterial");
    String property = request.getParameter("pOid");
    String thickness = request.getParameter("height");
    String width = request.getParameter("wide");
    String ton = request.getParameter("ton");
    String quantity = request.getParameter("quantity");
    String requester = request.getParameter("temprequester");
    String planDate = request.getParameter("planDate");
    String des = request.getParameter("des");
    String tryPlace = request.getParameter("tryPlace");

    String oid = "";
    if(request.getParameter("oid") != null){
      oid = request.getParameter("oid");
      msg = messageService.getString("e3ps.message.ket_message", "01940")/*수정 되었습니다*/;
    }
    /*
    Kogger.debug("#####moldProject = " + moldProject);
    Kogger.debug("#####tryType = " + tryType);
    Kogger.debug("#####material = " + material);
    Kogger.debug("#####ton = " + ton);
    Kogger.debug("#####quantity = " + quantity);
    Kogger.debug("#####requester = " + requester);
    Kogger.debug("#####planDate = " + planDate);
    Kogger.debug("#####des = " + des);
    Kogger.debug("#####tryPlace" + tryPlace);
      */

      Kogger.debug("#####property = " + property);
      Kogger.debug("#####thickness = " + thickness);
      Kogger.debug("#####width = " + width);
    if(tryType == null){
      tryType = "";
    }
    if(requester == null){
      requester = "";
    }

    if(material == null){
      material = "";
    }

    if(property == null){
      property = "";
    }

    if(thickness == null){
      thickness = "";
    }

    if(width == null){
      width = "";
    }

    if(ton == null){
      ton = "";
    }

    if(quantity == null){
      quantity = "";
    }

    if(planDate == null){
      planDate = "";
    }

    if(des == null){
      des = "";
    }

    if(tryPlace == null){
      tryPlace = "";
    }

    Hashtable hash = new Hashtable();

    hash.put("oid", oid);
    hash.put("moldProject", moldProject);
    hash.put("tryType", tryType);
    hash.put("material", material);
    hash.put("property", property);
    hash.put("thickness", thickness);
    hash.put("width", width);
    hash.put("ton", ton);
    hash.put("quantity", quantity);
    hash.put("requester", requester);
    hash.put("planDate", planDate);
    hash.put("des", des);
    hash.put("tryPlace", tryPlace);

    TrySchduleHelper.saveTrySchdule(hash);

    //Kogger.debug("save");

/*
    hash.put("moldProject", "");
    hash.put("tryType", "Sample");
    hash.put("material", "");
    hash.put("ton", "5");
    hash.put("quantity", "5");
    hash.put("requester", "");
    hash.put("planDate", "2010/10/31");
    hash.put("des", "testDes..")
*/
  }
%>
</head>
<body>
<script>
  alert("<%=msg %>");
  opener.display();
  self.close();
</script>
</body>
</html>
