<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.project.outputtype.CustomerTheModelPlan"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.SearchCondition"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
String msg = "";
//String returnLocation = "/plm/jsp/project/ListProgram.jsp";
String errorMsg = "";
String oid ="";
String re = "";
//<a href="javascript:parent.movePaage('/plm/portal/common/program_submenu.jsp', '/plm/jsp/project/ListProgram.jsp');" target="_self" class="topmenu2">프로그램</a>&nbsp;&nbsp;
try{

    String cmd = request.getParameter("cmd");
    Kogger.debug("cmd----------" + cmd ) ;
    if(cmd == null) {


        cmd = "";
    }

    if(cmd != null && cmd.equals("Create")){
        ModelPlan mp = null;


        //일정

        HashMap hm = new HashMap();
        String[] oemtypeOid = request.getParameterValues("oemtypeOid");

        Hashtable oemtypeOid_ht = new Hashtable();
        Hashtable oemEndDate_ht = new Hashtable();
        Kogger.debug("emtypeOid.length=="+oemtypeOid.length);
        for(int oemplan = 0 ; oemplan < oemtypeOid.length; oemplan++){
                    String key0 = "oemtypeOid"+oemplan;
                    oemtypeOid_ht.put(key0, oemtypeOid[oemplan]);

                    Kogger.debug("######################   일정 " + oemplan + "  ################");

                    String key1 = "oemEndDate"+oemplan;
                    String keyValue1 = request.getParameter(key1);//"2001/11/11";

                    Kogger.debug("##key1 = " + key1 + "//keyValue1 = " + keyValue1);

                    oemEndDate_ht.put(key1, keyValue1);

                    Kogger.debug("######################   일정 " + oemplan + "  ################");
                    Kogger.debug("##key0 = " + key0 + "//keyValue0 = " + oemtypeOid[oemplan]);
                    Kogger.debug("##key1 = " + key1 + "//keyValue1 = " + keyValue1);
                    Kogger.debug("######################   일정 END  #######################");


        }
        hm.put("oemtypeOid", oemtypeOid_ht);
        hm.put("oemEndDate", oemEndDate_ht);
        hm.put("programBaseNo", request.getParameter("programBaseNo"));
        //생산량

        String yield1 = request.getParameter("yield1");
        String yield2 = request.getParameter("yield2");
        String yield3 = request.getParameter("yield3");
        String yield4 = request.getParameter("yield4");
        String yield5 = request.getParameter("yield5");
        String yield6 = request.getParameter("yield6");
        String yield7 = request.getParameter("yield7");
        String yield8 = request.getParameter("yield8");
        String yield9 = request.getParameter("yield9");
        String yield10 = request.getParameter("yield10");

        hm.put("yield1", yield1);
        hm.put("yield2", yield2);
        hm.put("yield3", yield3);
        hm.put("yield4", yield4);
        hm.put("yield5", yield5);
        hm.put("yield6", yield6);
        hm.put("yield7", yield7);
        hm.put("yield8", yield8);
        hm.put("yield9", yield9);
        hm.put("yield10", yield10);

        //차종

        String cartypeOid = request.getParameter("cartype");
        hm.put("cartypeOid", cartypeOid);

        String modelname = request.getParameter("modelname");
        hm.put("modelname", modelname);


        //형태
        String formtypeOid = request.getParameter("formtype");
        if(formtypeOid == null){
            formtypeOid = "";
        }
        hm.put("formtypeOid", formtypeOid);

        //자동차사 설계 담당
        String person = request.getParameter("person");
        hm.put("person", person);

        String SUBCONTRACTOROid[] = request.getParameterValues("sOid");
        Vector SUBCONTRACTOROidVec = new Vector();
        if(SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
            for(int i = 0; i < SUBCONTRACTOROid.length; i++) {

                SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
            }
        }

        /* for(int i = 0; i < SUBCONTRACTOROidVec.size(); i++){
             Kogger.debug("SUBCONTRACTOROidVec[" + i + "] : " + SUBCONTRACTOROidVec.get(i));
         }*/

         hm.put("SUBCONTRACTOROidVec", SUBCONTRACTOROidVec);


        Kogger.debug("######################   생산량  ################");
        Kogger.debug("##yield1   : " +   yield1);
        Kogger.debug("##yield2   : " +   yield2);
        Kogger.debug("##yield3   : " +   yield3);
        Kogger.debug("##yield4   : " +   yield4);
        Kogger.debug("##yield5   : " +   yield5);
        Kogger.debug("##yield6   : " +   yield6);
        Kogger.debug("##yield7   : " +   yield7);
        Kogger.debug("##yield8   : " +   yield8);
        Kogger.debug("##yield9   : " +   yield9);
        Kogger.debug("##yield10   : " +   yield10);
        Kogger.debug("###########################################");

//    Kogger.debug("######################   차종  ################");
//    Kogger.debug("##cartypeOid      : " +   cartypeOid);
//    Kogger.debug("##modelname   : " +   modelname);
//    Kogger.debug("###########################################");

//    Kogger.debug("######################   형태  ################");
//    Kogger.debug("##formtypeOid      : " +   formtypeOid);
//    Kogger.debug("###########################################");


        mp = (ModelPlan)ProgramManagerHelper.manager.save(hm);
        re = mp.getPersistInfo().getObjectIdentifier().getStringValue();

        if(mp != null)
        {
            if(SUBCONTRACTOROidVec != null)
            {
                for(int i=0; i<SUBCONTRACTOROidVec.size(); i++)
                {

                    String numberCodeOID = (String)SUBCONTRACTOROidVec.get(i);
                    NumberCode code = (NumberCode)CommonUtil.getObject(numberCodeOID);
                    CustomerTheModelPlan link = CustomerTheModelPlan.newCustomerTheModelPlan(code, mp);
                    PersistenceHelper.manager.save(link);
                    Kogger.debug("저장" + i);
                }
            }
        }


        if(mp != null){
            msg = messageService.getString("e3ps.message.ket_message", "01315")/*등록 되었습니다*/;
        }else{
            msg=messageService.getString("e3ps.message.ket_message", "01317")/*등록 실패*/;
        }

    }else if(cmd != null && cmd.equals("Modify")){
        ModelPlan mp = null;

        HashMap hm = new HashMap();
        String[] opOid = request.getParameterValues("opOid");
        String[] oemtypeOid = request.getParameterValues("oemtypeOid");

        Hashtable oemtypeOid_ht = new Hashtable();
        Hashtable oemEndDate_ht = new Hashtable();
        Hashtable opOid_ht = new Hashtable();

        for(int oemplan = 0 ; oemplan < oemtypeOid.length; oemplan++){
                    String key0 = "oemtypeOid"+oemplan;
                    oemtypeOid_ht.put(key0, oemtypeOid[oemplan]);

                    String key1 = "oemEndDate"+oemplan;
                    String keyValue1 = request.getParameter(key1);//"2001/11/11";

//          Kogger.debug("##key1 = " + key1 + "//keyValue1 = " + keyValue1);

                    oemEndDate_ht.put(key1, keyValue1);

//          Kogger.debug("######################   일정 " + oemplan + "  ################");
//          Kogger.debug("##key0 = " + key0 + "//keyValue0 = " + oemtypeOid[oemplan]);
//          Kogger.debug("##key1 = " + key1 + "//keyValue1 = " + keyValue1);
//          Kogger.debug("######################   일정 END  #######################");


        }
        hm.put("oemtypeOid", oemtypeOid_ht);
        hm.put("oemEndDate", oemEndDate_ht);
        hm.put("programBaseNo", request.getParameter("programBaseNo"));
        //생산량

        String yield1 = request.getParameter("yield1");
        String yield2 = request.getParameter("yield2");
        String yield3 = request.getParameter("yield3");
        String yield4 = request.getParameter("yield4");
        String yield5 = request.getParameter("yield5");
        String yield6 = request.getParameter("yield6");
        String yield7 = request.getParameter("yield7");
        String yield8 = request.getParameter("yield8");
        String yield9 = request.getParameter("yield9");
        String yield10 = request.getParameter("yield10");

        hm.put("yield1", yield1);
        hm.put("yield2", yield2);
        hm.put("yield3", yield3);
        hm.put("yield4", yield4);
        hm.put("yield5", yield5);
        hm.put("yield6", yield6);
        hm.put("yield7", yield7);
        hm.put("yield8", yield8);
        hm.put("yield9", yield9);
        hm.put("yield10", yield10);

        //차종

        String modelname = request.getParameter("modelname");
        hm.put("modelname", modelname);


        //형태
        String formtypeOid = request.getParameter("formtype");
        hm.put("formtypeOid", formtypeOid);

        //자동차사 설계 담당
        String person = request.getParameter("person");
        hm.put("person", person);


//    Kogger.debug("######################   생산량  ################");
//    Kogger.debug("##yield1   : " +   yield1);
//    Kogger.debug("##yield2   : " +   yield2);
//    Kogger.debug("##yield3   : " +   yield3);
//    Kogger.debug("##yield4   : " +   yield4);
//    Kogger.debug("##yield5   : " +   yield5);
//    Kogger.debug("##yield6   : " +   yield6);
//    Kogger.debug("##yield7   : " +   yield7);
//    Kogger.debug("##yield8   : " +   yield8);
//    Kogger.debug("##yield9   : " +   yield9);
//    Kogger.debug("##yield10   : " +   yield10);
//    Kogger.debug("###########################################");

        Kogger.debug("######################   차종  ################");
        Kogger.debug("##modelname   : " +   modelname);
        Kogger.debug("###########################################");

//    Kogger.debug("######################   형태  ################");
//    Kogger.debug("##formtypeOid      : " +   formtypeOid);
//    Kogger.debug("###########################################");

        oid = request.getParameter("oid");

        hm.put("oid", oid);

        String SUBCONTRACTOROid[] = request.getParameterValues("sOid");

        Vector SUBCONTRACTOROidVec = new Vector();

        if(SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
            for(int i = 0; i < SUBCONTRACTOROid.length; i++) {
                SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
            }
        }

        QuerySpec qs1 = new QuerySpec();
        int idx1 = qs1.appendClassList(CustomerTheModelPlan.class, true);

        long longValue = CommonUtil.getOIDLongValue(oid);

        SearchCondition sc1 =  new SearchCondition(CustomerTheModelPlan.class, "roleBObjectRef.key.id", "=", longValue);
        qs1.appendWhere(sc1, new int[]{idx1});
        QueryResult qr1 = PersistenceHelper.manager.find(qs1);
        CustomerTheModelPlan link2 = null;

        while(qr1.hasMoreElements() )
        {

            Object[] objs = (Object[])qr1.nextElement();
            link2 = (CustomerTheModelPlan)objs[0];
            //link2 = (CustomerTheModelPlan)CommonUtil.getObject(oid);
            PersistenceHelper.manager.delete(link2);
        }


        /* for(int i = 0; i < SUBCONTRACTOROidVec.size(); i++){
             Kogger.debug("SUBCONTRACTOROidVec[" + i + "] : " + SUBCONTRACTOROidVec.get(i));
         }*/

        hm.put("SUBCONTRACTOROidVec", SUBCONTRACTOROidVec);


        mp = (ModelPlan)ProgramManagerHelper.manager.save(hm);

        re = mp.getPersistInfo().getObjectIdentifier().getStringValue();



        if(mp != null)
        {
            if(SUBCONTRACTOROidVec != null)
            {
                for(int i=0; i<SUBCONTRACTOROidVec.size(); i++)
                {

                    String numberCodeOID = (String)SUBCONTRACTOROidVec.get(i);
                    NumberCode code = (NumberCode)CommonUtil.getObject(numberCodeOID);
                    CustomerTheModelPlan link = CustomerTheModelPlan.newCustomerTheModelPlan(code, mp);
                    if(link != null){
                        PersistenceHelper.manager.save(link);
                    }
                }
            }
        }

        if(mp != null){
            msg=messageService.getString("e3ps.message.ket_message", "01940")/*수정 되었습니다*/;
        }else{
            msg=messageService.getString("e3ps.message.ket_message", "01942")/*수정 실패*/;
        }
        %>
        <%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%


    }else if(cmd != null && cmd.equals("Delete")){
        oid = request.getParameter("oid");

        ModelPlan mp = null;

        HashMap hm = new HashMap();
        String[] opOid = request.getParameterValues("opOid");
        String[] oemtypeOid = request.getParameterValues("oemtypeOid");

        msg = ProgramManagerHelper.manager.delete(oid);

    }

%>

<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.ProgramManagerHelper"%>
<body onLoad="javascript:gotoMenu('<%=cmd%>')" >
<form name=programForm method="post">

</form>
</body>
<script language="JavaScript">
<!--
    function gotoMenu(a){
        //alert(a);
        alert('<%=msg%>');
        if(a=="Create"){
            document.programForm.action = "/plm/jsp/project/ViewProgram.jsp?oid=<%=re%>";
        }else if(a=="Modify"){
            document.programForm.action = "/plm/jsp/project/ViewProgram.jsp?oid=<%=oid%>";
        }else if(a=="Delete"){
            document.programForm.action = "/plm/jsp/project/ListProgram.jsp";
        }

        document.programForm.submit();
    }

//-->
</script>
</form>
<%
}catch(Exception ex){
    throw ex;
}
%>
