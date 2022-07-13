<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*,wt.query.*,wt.util.*"%>
<%@page import="e3ps.common.code.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@include file="/jsp/common/context.jsp"%>

<%
  String command = request.getParameter("command");
  String oid = request.getParameter("oid");
  String parentOid = request.getParameter("parentOid");

  String codetype = request.getParameter("codetype");
  String depth = request.getParameter("depth");

  if(oid == null)oid = "";
  if(parentOid == null)parentOid = "";
  if(codetype == null)codetype = "";
  if(depth == null)depth = "";

  NumberCode nCode = null;
  if(oid.length() > 0) {
    nCode = (NumberCode)CommonUtil.getObject(oid);
  }

  String msg = "";
  boolean flag = false;
  NumberCode saveCode = null;
  QueryResult qr = null;
  if("delete".equals(command)) {


    NumberCode numberCode = (NumberCode)CommonUtil.getObject(oid);
    HashMap hm_delete = new HashMap();
    //ProcessInterface pi = new ProcessInterface();
    if("WORKCENTER".equals(numberCode.getCodeType().toString())) {
      if(numberCode.getWcType() != null){
        String checkWctype = numberCode.getWcType();
        String zFlag = "";
        if(checkWctype.trim().equals("사내")){
          zFlag = "A";
        }else if(checkWctype.trim().equals("사내외주")){
          zFlag = "B";
        }else if(checkWctype.trim().equals("사외외주")){
          zFlag = "C";
        }else if(checkWctype.trim().equals("해외외주")){
          zFlag = "D";
        }

        hm_delete.put("WERKS", numberCode.getParent().getCode());
        hm_delete.put("ARBPL", numberCode.getCode());//대문자
        hm_delete.put("Z_DATASTA", "D");
        hm_delete.put("Z_FLAG", zFlag);
        //if(numberCode.getName() != null){
          //hm_delete.put("STEXT", numberCode.getName());
        //}
        
        Kogger.debug("searchChildCode", null, null, "@@@@@##### ##### ERP 전송 - 삭제@@@@@#####");
        //hm_delete = pi.WorkCenter(hm_delete);
        %>
        <script>
        alert("SAP Log : "+'<%=hm_delete.get("RE")%>' +'(<%=hm_delete.get("MSG")%>)');
        </script>
        <%

      }
    }

    flag = NumberCodeHelper.deleteNumberCode((NumberCode)CommonUtil.getObject(oid));

  } else if("create".equals(command) || "modify".equals(command)) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String wctype = request.getParameter("wctype");
        String costCode = request.getParameter("costCode");
        String venderCode = request.getParameter("venderCode");

        String sorting = request.getParameter("sorting");
        String disabled = request.getParameter("disabled");
        String abbr = request.getParameter("abbr");

        String[] codeLang = request.getParameterValues("codeLang");
        String[] desc = request.getParameterValues("desc");

        //String submitCheck = request.getParameter("submitCheck");
        String submitCheck = "";
        HashMap nMap = new HashMap();
        nMap.put("code", code);
        if(wctype != null){
            nMap.put("wctype", wctype);
        }
        if(costCode != null){
            nMap.put("costCode", costCode);
        }
        if(venderCode != null){
            nMap.put("venderCode", venderCode);
            submitCheck = "false";
        }

        nMap.put("name", name);
        nMap.put("description", description);
        nMap.put("type", codetype);
        nMap.put("oid", oid);
        nMap.put("parentOid", parentOid);
        nMap.put("submitCheck", submitCheck);
        nMap.put("checkSapSubmit", "false");

        nMap.put("sorting", sorting);
        nMap.put("disabled", disabled);

        if( "create".equals(command)
          || ( "modify".equals(command) && !(nCode.getCode()).equals(code) ) ) {
            flag = NumberCodeHelper.checkNumberCode(nMap);
        }

        if(!flag) {

            //saveCode = NumberCodeHelper.saveNumberCode(nMap);
            msg =  NumberCodeHelper.saveNumberCode(nMap);

            /*
            [PLM 1차 개선] NumberCodeValue
            */
            // [Start] NumberCodeValue //
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("codeType", "LANGUAGE");
            List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);

            if ( msg.equals("저장 되었습니다.") ) {
                Connection conn = null;
                try {
                    conn = PlmDBUtil.getConnection();
                    NumberCodeDao dao = new NumberCodeDao(conn);

                    int i = 0;
                    Map<String, Object> param = null;
                    for ( Map<String, Object> item : list ) {

                        param = new HashMap<String, Object>();

                        if ( !item.get("code").equals("ko") ) {

                            param.put("codeType", codetype);
                            param.put("code",     code);
                            param.put("lang",     item.get("code"));
                            codeLang[i] = KETStringUtil.replaceSpecialTag(codeLang[i]);
                            param.put("value",    codeLang[i]);
                            desc[i] = KETStringUtil.replaceSpecialTag(desc[i]);
                            param.put("desc",     desc[i]);
                            param.put("abbr",     KETStringUtil.replaceSpecialTag(abbr));
                            dao.createNumberCodeValue(param);

                            i++;
                        }
                        else {
                            param.put("codeType", codetype);
                            param.put("code",     code);
                            param.put("lang",     "ko");
                            name = KETStringUtil.replaceSpecialTag(name);
                            param.put("value",    name);
                            description = KETStringUtil.replaceSpecialTag(description);
                            param.put("desc",     description);
                            param.put("abbr",     KETStringUtil.replaceSpecialTag(abbr));
                            dao.createNumberCodeValue(param);
                        }
                    }
                }
                finally {
                    PlmDBUtil.close(conn);
                }
                // [End] NumberCodeValue //
            }
        }

    } else {
        NumberCode parentCode = (NumberCode)CommonUtil.getObject(parentOid);

        HashMap map = new HashMap();
        map.put("parent", parentCode);
        qr = PersistenceHelper.manager.find(NumberCodeHelper.getCodeQuerySpec(map));
    }
%>

<%@page import="e3ps.sap.ProcessInterface"%>
<html>
<head>
<script language="javascript">
<%
  if("delete".equals(command)) {
    if(flag) {
%>
      alert("<%=messageService.getString("e3ps.message.ket_message", "01699") %><%--삭제되었습니다.--%>");
      parent.onSubmit();
<%
    } else {
%>
      alert("사용 중인 코드입니다. \n 삭제할 수 없습니다.");
<%
    }
  } else if("create".equals(command) || "modify".equals(command)) {
    if(flag) {
%>
      alert("이미 등록된 코드 입니다. \n 다시 입력하십시오.");
<%
    } else {

%>
        alert("<%=msg%>");
        var arr = new Array();
        arr[0] = '<%=codetype%>';
        arr[1] = '<%=parentOid%>';
        parent.regResult(arr);
      //parent.opener.document.location.href = "/plm/jsp/common/loading.jsp?url=/plm/jsp/common/code/NumberCodeMgtList.jsp&key=codetype&value=<%=codetype%>"
      //+"&key=parentOid&value=<%=parentOid%>&key=depth&value=<%=depth%>";
      //parent.self.close();
<%

    }
  } else {
%>
    if(parent) {
      var fArr = new Array();
      var idx = 0;
<%
      Object obj[] = null;
      NumberCode childCode = null;
      while(qr.hasMoreElements()) {
        obj = (Object[])qr.nextElement();
        childCode = (NumberCode)obj[0];
%>
        var vArr = new Array();
        vArr[0] = "<%=childCode.getPersistInfo().getObjectIdentifier().getStringValue()%>";
        vArr[1] = "<%=childCode.getCode()%>";
        vArr[2] = "<%=childCode.getName()%>";
        fArr[idx++] = vArr;
<%
      }
%>
      parent.addChildCodeList(fArr);
    }
<%
  }
%>
</script>
</head>
<body>
</body>
</html>
