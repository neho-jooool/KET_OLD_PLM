<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.Weights"%>
<%@page import="e3ps.project.beans.PerformanceHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String cmd = StringUtil.checkNull(request.getParameter("cmd"));
    String oid = StringUtil.checkNull(request.getParameter("oid"));
    String wType = StringUtil.checkNull(request.getParameter("wType"));
    String wTypeName = "";
    String projectTypeName="";
    if(wType.equals("1")){
        wTypeName = " 제품(커넥터)";
        projectTypeName = "자동차 사업부";
    }else if(wType.equals("2")){
        wTypeName = " 제품(모듈)";
        projectTypeName = "자동차 사업부";
    }else if(wType.equals("3")){
        wTypeName = " 금형";
        projectTypeName = "자동차 사업부";
    }else if(wType.equals("4")){
        wTypeName = " 제품(커넥터)";
        projectTypeName = "전자 사업부";
    }else if(wType.equals("5")){
        wTypeName = " 제품(모듈)";
        projectTypeName = "전자 사업부";
    }

    Weights wg = null;
    if(oid.length() > 0 ){
        wg = (Weights)CommonUtil.getObject(oid);
    }


    if(cmd.equals("save")){
        oid = StringUtil.checkNull(request.getParameter("oid"));
        wType = StringUtil.checkNull(request.getParameter("wType"));
        String quality = StringUtil.checkNull(request.getParameter("quality"));
        String cost = StringUtil.checkNull(request.getParameter("cost"));
        String deliveryOne = StringUtil.checkNull(request.getParameter("deliveryOne"));
        String deliveryTwo = StringUtil.checkNull(request.getParameter("deliveryTwo"));
        String deliveryThree = StringUtil.checkNull(request.getParameter("deliveryThree"));
        String technical = StringUtil.checkNull(request.getParameter("technical"));

        String mcost = StringUtil.checkNull(request.getParameter("mcost"));
        String mdeliveryOne = StringUtil.checkNull(request.getParameter("mdeliveryOne"));
        String mtechnical = StringUtil.checkNull(request.getParameter("mtechnical"));

        String equality = StringUtil.checkNull(request.getParameter("equality"));
        String ecost = StringUtil.checkNull(request.getParameter("ecost"));
        String edeliveryOne = StringUtil.checkNull(request.getParameter("edeliveryOne"));

        String totalS = StringUtil.checkNull(request.getParameter("totalS"));
        String totalA = StringUtil.checkNull(request.getParameter("totalA"));
        String totalB = StringUtil.checkNull(request.getParameter("totalB"));
        String totalC = StringUtil.checkNull(request.getParameter("totalC"));
        String totalD = StringUtil.checkNull(request.getParameter("totalD"));

        String etotalS = StringUtil.checkNull(request.getParameter("etotalS"));
        String etotalA = StringUtil.checkNull(request.getParameter("etotalA"));
        String etotalB = StringUtil.checkNull(request.getParameter("etotalB"));
        String etotalC = StringUtil.checkNull(request.getParameter("etotalC"));
        String etotalD = StringUtil.checkNull(request.getParameter("etotalD"));

        String mtotalS = StringUtil.checkNull(request.getParameter("mtotalS"));
        String mtotalA = StringUtil.checkNull(request.getParameter("mtotalA"));
        String mtotalB = StringUtil.checkNull(request.getParameter("mtotalB"));
        String mtotalC = StringUtil.checkNull(request.getParameter("mtotalC"));
        String mtotalD = StringUtil.checkNull(request.getParameter("mtotalD"));

        String qS = StringUtil.checkNull(request.getParameter("qS"));
        String qA = StringUtil.checkNull(request.getParameter("qA"));
        String qB = StringUtil.checkNull(request.getParameter("qB"));
        String qC = StringUtil.checkNull(request.getParameter("qC"));
        String qD = StringUtil.checkNull(request.getParameter("qD"));
        String cS = StringUtil.checkNull(request.getParameter("cS"));
        String cA = StringUtil.checkNull(request.getParameter("cA"));
        String cB = StringUtil.checkNull(request.getParameter("cB"));
        String cC = StringUtil.checkNull(request.getParameter("cC"));
        String cD = StringUtil.checkNull(request.getParameter("cD"));
        String c2S = StringUtil.checkNull(request.getParameter("c2S"));
        String c2A = StringUtil.checkNull(request.getParameter("c2A"));
        String c2B = StringUtil.checkNull(request.getParameter("c2B"));
        String c2C = StringUtil.checkNull(request.getParameter("c2C"));
        String c2D = StringUtil.checkNull(request.getParameter("c2D"));

        String eqS = StringUtil.checkNull(request.getParameter("eqS"));
        String eqA = StringUtil.checkNull(request.getParameter("eqA"));
        String eqB = StringUtil.checkNull(request.getParameter("eqB"));
        String eqC = StringUtil.checkNull(request.getParameter("eqC"));
        String eqD = StringUtil.checkNull(request.getParameter("eqD"));
        String ecS = StringUtil.checkNull(request.getParameter("ecS"));
        String ecA = StringUtil.checkNull(request.getParameter("ecA"));
        String ecB = StringUtil.checkNull(request.getParameter("ecB"));
        String ecC = StringUtil.checkNull(request.getParameter("ecC"));
        String ecD = StringUtil.checkNull(request.getParameter("ecD"));
        String ec2S = StringUtil.checkNull(request.getParameter("ec2S"));
        String ec2A = StringUtil.checkNull(request.getParameter("ec2A"));
        String ec2B = StringUtil.checkNull(request.getParameter("ec2B"));
        String ec2C = StringUtil.checkNull(request.getParameter("ec2C"));
        String ec2D = StringUtil.checkNull(request.getParameter("ec2D"));

        String mcS = StringUtil.checkNull(request.getParameter("mcS"));
        String mcA = StringUtil.checkNull(request.getParameter("mcA"));
        String mcB = StringUtil.checkNull(request.getParameter("mcB"));
        String mcC = StringUtil.checkNull(request.getParameter("mcC"));
        String mcD = StringUtil.checkNull(request.getParameter("mcD"));
        String mc2S = StringUtil.checkNull(request.getParameter("mc2S"));
        String mc2A = StringUtil.checkNull(request.getParameter("mc2A"));
        String mc2B = StringUtil.checkNull(request.getParameter("mc2B"));
        String mc2C = StringUtil.checkNull(request.getParameter("mc2C"));
        String mc2D = StringUtil.checkNull(request.getParameter("mc2D"));


        String dOneS = StringUtil.checkNull(request.getParameter("dOneS"));
        String dOneA = StringUtil.checkNull(request.getParameter("dOneA"));
        String dOneB = StringUtil.checkNull(request.getParameter("dOneB"));
        String dOneC = StringUtil.checkNull(request.getParameter("dOneC"));
        String dOneD = StringUtil.checkNull(request.getParameter("dOneD"));

        String mdOneS = StringUtil.checkNull(request.getParameter("mdOneS"));
        String mdOneA = StringUtil.checkNull(request.getParameter("mdOneA"));
        String mdOneB = StringUtil.checkNull(request.getParameter("mdOneB"));
        String mdOneC = StringUtil.checkNull(request.getParameter("mdOneC"));
        String mdOneD = StringUtil.checkNull(request.getParameter("mdOneD"));

        String edOneS = StringUtil.checkNull(request.getParameter("edOneS"));
        String edOneA = StringUtil.checkNull(request.getParameter("edOneA"));
        String edOneB = StringUtil.checkNull(request.getParameter("edOneB"));
        String edOneC = StringUtil.checkNull(request.getParameter("edOneC"));
        String edOneD = StringUtil.checkNull(request.getParameter("edOneD"));

        String dTwoS = StringUtil.checkNull(request.getParameter("dTwoS"));
        String dTwoA = StringUtil.checkNull(request.getParameter("dTwoA"));
        String dTwoB = StringUtil.checkNull(request.getParameter("dTwoB"));
        String dTwoC = StringUtil.checkNull(request.getParameter("dTwoC"));
        String dTwoD = StringUtil.checkNull(request.getParameter("dTwoD"));
        String dThreeS = StringUtil.checkNull(request.getParameter("dThreeS"));
        String dThreeA = StringUtil.checkNull(request.getParameter("dThreeA"));
        String dThreeB = StringUtil.checkNull(request.getParameter("dThreeB"));
        String dThreeC = StringUtil.checkNull(request.getParameter("dThreeC"));
        String dThreeD = StringUtil.checkNull(request.getParameter("dThreeD"));

        String tS = StringUtil.checkNull(request.getParameter("tS"));
        String tA = StringUtil.checkNull(request.getParameter("tA"));
        String tB = StringUtil.checkNull(request.getParameter("tB"));
        String tC = StringUtil.checkNull(request.getParameter("tC"));
        String tD = StringUtil.checkNull(request.getParameter("tD"));

        String keyNo = StringUtil.checkNull(request.getParameter("keyNo"));

        HashMap map = new HashMap();

        map.put("oid", oid);
        map.put("wType", wType);
        if(wType.equals("3")){
            map.put("cost", mcost);
            map.put("deliveryOne", mdeliveryOne);
            map.put("deliveryTwo", deliveryTwo);
            map.put("deliveryThree", deliveryThree);
            map.put("totalS", mtotalS);
            map.put("totalA", mtotalA);
            map.put("totalB", mtotalB);
            map.put("totalC", mtotalC);
            map.put("totalD", mtotalD);
            map.put("cS", mcS);
            map.put("cA", mcA);
            map.put("cB", mcB);
            map.put("cC", mcC);
            map.put("cD", mcD);
            map.put("c2S", mc2S);
            map.put("c2A", mc2A);
            map.put("c2B", mc2B);
            map.put("c2C", mc2C);
            map.put("c2D", mc2D);
            map.put("dOneS", mdOneS);
            map.put("dOneA", mdOneA);
            map.put("dOneB", mdOneB);
            map.put("dOneC", mdOneC);
            map.put("dOneD", mdOneD);

        }else if(wType.equals("1") || wType.equals("2")) {
            map.put("quality", quality);
            map.put("cost", cost);
            map.put("deliveryOne", deliveryOne);
            map.put("technical", technical);
            map.put("totalS", totalS);
            map.put("totalA", totalA);
            map.put("totalB", totalB);
            map.put("totalC", totalC);
            map.put("totalD", totalD);
            map.put("qS", qS);
            map.put("qA", qA);
            map.put("qB", qB);
            map.put("qC", qC);
            map.put("qD", qD);
            map.put("cS", cS);
            map.put("cA", cA);
            map.put("cB", cB);
            map.put("cC", cC);
            map.put("cD", cD);
            map.put("c2S", c2S);
            map.put("c2A", c2A);
            map.put("c2B", c2B);
            map.put("c2C", c2C);
            map.put("c2D", c2D);
            map.put("dOneS", dOneS);
            map.put("dOneA", dOneA);
            map.put("dOneB", dOneB);
            map.put("dOneC", dOneC);
            map.put("dOneD", dOneD);

        }else if(wType.equals("4") || wType.equals("5")) {
            map.put("quality", equality);
            map.put("cost", ecost);
            map.put("deliveryOne", edeliveryOne);
            map.put("totalS", etotalS);
            map.put("totalA", etotalA);
            map.put("totalB", etotalB);
            map.put("totalC", etotalC);
            map.put("totalD", etotalD);
            map.put("qS", eqS);
            map.put("qA", eqA);
            map.put("qB", eqB);
            map.put("qC", eqC);
            map.put("qD", eqD);
            map.put("cS", ecS);
            map.put("cA", ecA);
            map.put("cB", ecB);
            map.put("cC", ecC);
            map.put("cD", ecD);
            map.put("c2S", ec2S);
            map.put("c2A", ec2A);
            map.put("c2B", ec2B);
            map.put("c2C", ec2C);
            map.put("c2D", ec2D);
            map.put("dOneS", edOneS);
            map.put("dOneA", edOneA);
            map.put("dOneB", edOneB);
            map.put("dOneC", edOneC);
            map.put("dOneD", edOneD);

        }

        map.put("dTwoS", dTwoS);
        map.put("dTwoA", dTwoA);
        map.put("dTwoB", dTwoB);
        map.put("dTwoC", dTwoC);
        map.put("dTwoD", dTwoD);
        map.put("dThreeS", dThreeS);
        map.put("dThreeA", dThreeA);
        map.put("dThreeB", dThreeB);
        map.put("dThreeC", dThreeC);
        map.put("dThreeD", dThreeD);
        map.put("tS", tS);
        map.put("tA", tA);
        map.put("tB", tB);
        map.put("tC", tC);
        map.put("tD", tD);
        map.put("keyNo", keyNo);

        try{
            wg = PerformanceHelper.manager.saveAction(map);
        }catch(Exception e){ Kogger.error(e); }
        if(wg != null){
%>
        <script>
            alert('<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>');
            opener.document.forms[0].submit();
            self.close();
        </script>
<%
        }else{
%>
        <script>
            alert("저장 실패.");
            opener.document.forms[0].submit();
            self.close();
        </script>
<%

        }
    }


%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>

<style type="text/css">

body {
    margin-left: 10px;
    margin-top: 12px;
    margin-right: 10px;
    margin-bottom: 5px;
}
</style>
<script language="JavaScript">

    function onSave(){
        form = document.forms[0];
        //기준 타입
        var wType = form.wType.value;
        if(form.wType.value == ""){ alert("평가 기준 타입을 입력 해 주십시오 ."); }

        //가중치
        var quality = form.quality.value;
        var cost = form.cost.value;
        var mcost = form.mcost.value;
        var deliveryOne = form.deliveryOne.value;
        var mdeliveryOne = form.mdeliveryOne.value;
        var deliveryTwo = form.deliveryTwo.value;
        var deliveryThree = form.deliveryThree.value;
        var technical = form.technical.value;

        //종합 평가 점수
        var totalS = form.totalS.value;
        var totalA = form.totalA.value;
        var totalB = form.totalB.value;
        var totalC = form.totalC.value;
        var totalD = form.totalD.value;

        var mtotalS = form.mtotalS.value;
        var mtotalA = form.mtotalA.value;
        var mtotalB = form.mtotalB.value;
        var mtotalC = form.mtotalC.value;
        var mtotalD = form.mtotalD.value;

        //Quality S~D 기준
        var qS = form.qS.value;
        var qA = form.qA.value;
        var qB = form.qB.value;
        var qC = form.qC.value;
        var qD = form.qD.value;

        //Cost S~D 기준
        var cS = form.cS.value;
        var cA = form.cA.value;
        var cB = form.cB.value;
        var cC = form.cC.value;
        var cD = form.cD.value;
        /*

        var c2S = form.c2S.value;
        var c2A = form.c2A.value;
        var c2B = form.c2B.value;
        var c2C = form.c2C.value;
        var c2D = form.c2D.value;
        */
        var mcS = form.mcS.value;
        var mcA = form.mcA.value;
        var mncB = form.mcB.value;
        var mcC = form.mcC.value;
        var mcD = form.mcD.value;
        /*
        var mc2S = form.mc2S.value;
        var mc2A = form.mc2A.value;
        var mc2B = form.mc2B.value;
        var mc2C = form.mc2C.value;
        var mc2D = form.mc2D.value;
        */

        //Delivery S~D 기준
        var d1S = form.dOneS.value;
        var d1A = form.dOneA.value;
        var d1B = form.dOneB.value;
        var d1C = form.dOneC.value;
        var d1D = form.dOneD.value;

        var md1S = form.mdOneS.value;
        var md1A = form.mdOneA.value;
        var md1B = form.mdOneB.value;
        var md1C = form.mdOneC.value;
        var md1D = form.mdOneD.value;

        var d2S = form.dTwoS.value;
        var d2A = form.dTwoA.value;
        var d2B = form.dTwoB.value;
        var d2C = form.dTwoC.value;
        var d2D = form.dTwoD.value;

        var d3S = form.dThreeS.value;
        var d3A = form.dThreeA.value;
        var d3B = form.dThreeB.value;
        var d3C = form.dThreeC.value;
        var d3D = form.dThreeD.value;

        //Technical S~D 기준
        var tS = form.tS.value;
        var tA = form.tA.value;
        var tB = form.tB.value;
        var tC = form.tC.value;
        var tD = form.tD.value;

        if(form.wType.value == "1" || form.wType.value == "2"){
            if(!checkIntMuti(quality,cost,deliveryOne,technical)){ return; }

            if(!checkInt(qS, "Quality S") ){ return; }
            if(!checkInt(qA, "Quality A") ){ return; }
            if(!checkInt(qB, "Quality B") ){ return; }
            if(!checkInt(qC, "Quality C") ){ return; }
            if(!checkInt(qD, "Quality D") ){ return; }


            if(!checkInt(totalS, "종합 평가 점수 S") ){ return; }
            if(!checkInt(totalA, "종합 평가 점수 A") ){ return; }
            if(!checkInt(totalB, "종합 평가 점수 B") ){ return; }
            if(!checkInt(totalC, "종합 평가 점수 C") ){ return; }
            if(!checkInt(totalD, "종합 평가 점수 D") ){ return; }

            if(!checkInt(tS, "Technical S") ){ return; }
            if(!checkInt(tA, "Technical A") ){ return; }
            if(!checkInt(tB, "Technical B") ){ return; }
            if(!checkInt(tC, "Technical C") ){ return; }
            if(!checkInt(tD, "Technical D") ){ return; }
        }

        if(form.wType.value == "3"){
            if(!checkIntMuti(mcost,mdeliveryOne,deliveryTwo,deliveryThree)){ return; }
            if(!checkInt(mtotalS, "종합 평가 점수 S") ){ return; }
            if(!checkInt(mtotalA, "종합 평가 점수 A") ){ return; }
            if(!checkInt(mtotalB, "종합 평가 점수 B") ){ return; }
            if(!checkInt(mtotalC, "종합 평가 점수 C") ){ return; }
            if(!checkInt(mtotalD, "종합 평가 점수 D") ){ return; }
        }

        form.cmd.value = "save";
        form.action = "/plm/jsp/project/performance/CreateValuation.jsp";
        form.submit();
    }

    function checkIntMuti(q,c,d,t){
        var checkTotal = parseInt(q)+parseInt(c)+parseInt(d)+parseInt(t);
        if(checkTotal == 100){
            return true;
        }else{
            alert('<%=messageService.getString("e3ps.message.ket_message", "02381") %><%--입력값이 잘못되었습니다\n가중치 입력값의 합은 100 이어야 합니다--%>');
            return false;
        }
    }

    function checkInt(v, m){
        if(!isNotNumData(v) && parseInt(v)<=100){
            return true;
        }else{
            alert(m + " <%=messageService.getString("e3ps.message.ket_message", "00008") %><%--{0} 입력값이 잘못되었습니다\n입력값은 0~100 사이어야 합니다--%>");
            return false;
        }

    }
    function SetNum2(obj){
        val=obj.value;
        re=/[^0-9,\-]+/gi;
        obj.value=val.replace(re,"");
    }
    function SetNum3(obj){
        val=obj.value;
        re=/[^0-9,\-,\.]+/gi;
        obj.value=val.replace(re,"");
    }
    function onTypeChange(obj){
        var wtypeObj = obj.value;
        //document.forms[0].reset();
        //document.forms[0].wType.value = wtypeObj;
        if(wtypeObj =='1' || wtypeObj =='2'){
        product.style.display = "block";
        mold.style.display = "none";
        }else if(wtypeObj =='3' ) {
        product.style.display = "none";
        mold.style.display = "block";
        }else {
        product.style.display = "none";
        mold.style.display = "none";
        }
    }
    function onTypeChangeOnload(wtypeObj){
        if(wtypeObj =='1' || wtypeObj =='2'){
        product.style.display = "block";
        mold.style.display = "none";
        electron.style.display = "none";
        }else if(wtypeObj =='3' ) {
        product.style.display = "none";
        mold.style.display = "block";
        electron.style.display = "none";
        }else if(wtypeObj =='4' || wtypeObj == '5'){
        electron.style.display = "none";
        mold.style.display = "none";
        electron.style.display = "block";
        }

    }


</script>
</head>


<form>
<body onload="javascript:onTypeChangeOnload(<%=wType%>)">
<!-- hidden begin -->
<input type="hidden" name="cmd" value="">
<input type="hidden" name="oid" value="<%=oid%>">
<input type='hidden' name='wType' value='<%=wType%>'>

<!-- hidden end -->

<!-- ############################################################################################################ START -->
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
             <table width="780" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td background="/plm/portal/images/logo_popupbg.png">
                      <table height="28" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02984") %><%--평가 기준 등록 및 수정--%></td>
                            <td width="10"></td>
                          </tr>
                      </table>
                      </td>
                      <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                  </table>
                </td>
            </tr>
            <tr>
              <td class="space10"></td>
            </tr>
              </table>
             <table width="780" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=projectTypeName%><%=wTypeName%></td>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td>&nbsp;</td>

                    </tr>
                      </table>
                </td>
              </tr>
            </table>
        </td>
     </tr>

<!-- ############################################################################################################ START -->

     <!-- ###################################            Main    Start -->

     <tr>
         <td>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            <div id="product" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                  <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdblueM"> ~<input type=text name="totalS" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalS()%>"></td>
                <td class="tdblueM"> ~<input type=text name="totalA" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalA()%>"></td>
                <td class="tdblueM"> ~<input type=text name="totalB" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalB()%>"></td>
                <td class="tdblueM"> ~<input type=text name="totalC" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalC()%>"></td>
                <td class="tdblueM"> <input type=text name="totalD" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalD()%>">~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdblueM"> ~<input type=text name="qS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQS())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="qA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQA())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="qB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQB())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="qC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQC())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> <input type=text name="qD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQD())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                  <td class="tdblueM"> <input type=text name="quality" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getQuality()%>" >%</td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdblueM"> ~<input type=text name="cS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCS())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="cA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCA())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="cB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCB())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="cC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCC())%>">%</td>
                <td class="tdblueM"> <input type=text name="cD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCD())%>">%~</td>
                <td class="tdblueM"> <input type=text name="cost" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getCost()%>" >%</td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdblueM"> ~<input type=text name="dOneS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneS())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dOneA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneA())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dOneB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneB())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dOneC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneC())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> <input type=text name="dOneD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneD())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdblueM"> <input type=text name="deliveryOne" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getDeliveryOne()%>" >% </td>
              </tr>
              <tr>
                <td class="tdblueM">Technical </td>
                <td class="tdblueM"> ~<input type=text name="tS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getTS())%>" ><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="tA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getTA())%>" ><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="tB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getTB())%>" ><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="tC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getTC())%>" ><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> <input type=text name="tD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getTD())%>" ><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                <td class="tdblueM"> <input type=text name="technical" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTechnical()%>" >%</td>
              </tr>
            </table>
            </div>
            <div id="mold" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="14%"><COL width="14%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" colspan=2 rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                  <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdblueM"> ~<input type=text name="mtotalS" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalS()%>"></td>
                <td class="tdblueM"> ~<input type=text name="mtotalA" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalA()%>"></td>
                <td class="tdblueM"> ~<input type=text name="mtotalB" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalB()%>"></td>
                <td class="tdblueM"> ~<input type=text name="mtotalC" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalC()%>"></td>
                <td class="tdblueM"> <input type=text name="mtotalD" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalD()%>">~</td>
              </tr>
              <tr>
                <td class="tdblueM" colspan=2>Cost </td>
                <td class="tdblueM"> ~<input type=text name="mcS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCS())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="mcA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCA())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="mcB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCB())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="mcC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCC())%>">%</td>
                <td class="tdblueM"> <input type=text name="mcD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCD())%>">%~</td>
                <td class="tdblueM"> <input type=text name="mcost" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getCost()%>">% </td>
              </tr>
              <tr>
                <td class="tdblueM" rowspan=3>Delivery</td>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01099") %><%--금형제작--%></td>
                <td class="tdblueM"> ~<input type=text name="mdOneS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneS())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="mdOneA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneA())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="mdOneB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneB())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="mdOneC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneC())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> <input type=text name="mdOneD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneD())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdblueM"> <input type=text name="mdeliveryOne" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getDeliveryOne()%>">% </td>
              </tr>
              <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%></td>
                <td class="tdblueM"> ~<input type=text name="dTwoS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDTwoS())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dTwoA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDTwoA())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dTwoB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDTwoB())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dTwoC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDTwoC())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> <input type=text name="dTwoD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDTwoD())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdblueM"> <input type=text name="deliveryTwo" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getDeliveryTwo()%>">% </td>
              </tr>
              <tr>
                <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01095") %><%--금형이관--%></td>
                <td class="tdblueM"> ~<input type=text name="dThreeS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDThreeS())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dThreeA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDThreeA())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dThreeB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDThreeB())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="dThreeC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDThreeC())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> <input type=text name="dThreeD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDThreeD())%>"><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdblueM"> <input type=text name="deliveryThree" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getDeliveryThree()%>">% </td>
              </tr>
            </table>
            </div>
            <div id="electron" style="display:none">
            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
            <COL width="28%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%"><COL width="12%">
              <tr>
                <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02983") %><%--평가 구분/판정--%></td>
                <td class="tdblueM">S(100%)</td>
                <td class="tdblueM">A(90%)</td>
                <td class="tdblueM">B(80%)</td>
                <td class="tdblueM">C(70%)</td>
                <td class="tdblueM">D(60%)</td>
                  <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%><br>(%)</td>
              </tr>
              <tr>
                <td class="tdblueM"> ~<input type=text name="etotalS" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalS()%>"></td>
                <td class="tdblueM"> ~<input type=text name="etotalA" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalA()%>"></td>
                <td class="tdblueM"> ~<input type=text name="etotalB" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalB()%>"></td>
                <td class="tdblueM"> ~<input type=text name="etotalC" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalC()%>"></td>
                <td class="tdblueM"> <input type=text name="etotalD" style="ime-mode:disabled;width:40%;text-align:center" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getTotalD()%>">~</td>
              </tr>
              <tr>
                <td class="tdblueM">Quality </td>
                <td class="tdblueM"> ~<input type=text name="eqS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQS())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="eqA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQA())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="eqB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQB())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> ~<input type=text name="eqC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQC())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%></td>
                <td class="tdblueM"> <input type=text name="eqD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getQD())%>"><%=messageService.getString("e3ps.message.ket_message", "02502") %><%--점--%>~</td>
                <td class="tdblueM"> <input type=text name="equality" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getQuality()%>" >% </td>
              </tr>
              <tr>
                <td class="tdblueM">Cost </td>
                <td class="tdblueM"> ~<input type=text name="ecS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCS())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="ecA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCA())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="ecB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCB())%>">%</td>
                <td class="tdblueM"> ~<input type=text name="ecC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCC())%>">%</td>
                <td class="tdblueM"> <input type=text name="ecD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum3(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getCD())%>">%~</td>
                <td class="tdblueM"> <input type=text name="ecost" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getCost()%>" >% </td>
              </tr>
              <tr>
                <td class="tdblueM">Delivery </td>
                <td class="tdblueM"> ~<input type=text name="edOneS" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneS())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="edOneA" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneA())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="edOneB" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneB())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> ~<input type=text name="edOneC" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneC())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                <td class="tdblueM"> <input type=text name="edOneD" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum2(this)" onmouseout="SetNum2(this)" value="<%=(wg == null)?"":StringUtil.checkNull(wg.getDOneD())%>" ><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>~</td>
                <td class="tdblueM"> <input type=text name="edeliveryOne" style="ime-mode:disabled;width:40%;text-align:right" onkeyup ="SetNum(this)" value="<%=(wg == null)?"":wg.getDeliveryOne()%>" >% </td>
              </tr>
            </table>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" width="780">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
         </td>
     </tr>

     <!-- ###################################            Main    End -->


<!-- ############################################################################################################ END -->
    <tr>
      <td height="30" valign="bottom">
          <iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      </td>
    </tr>
</table>
<!-- ############################################################################################################ END -->

</body>
</form>
</html>


