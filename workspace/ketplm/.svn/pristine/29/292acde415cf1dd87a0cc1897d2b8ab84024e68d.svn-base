<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="wt.fc.*,
                wt.org.*,
                wt.query.*,
                java.util.ArrayList
                "%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<head>
<base target="_self">
</head>
<script language=JavaScript  src="/plm/potal/js/common.js"></script>
<LINK rel="stylesheet" type="text/css" href="/plm/potal/css/default.css">
<link rel="stylesheet" href="/plm/potal/css/e3ps.css">
<%
    String oid = request.getParameter("oid");
    String quality = request.getParameter("quality");
    String cost1 = request.getParameter("cost1");
    String cost2 = request.getParameter("cost2");
    String delivery1 = request.getParameter("delivery1");
    String delivery2 = request.getParameter("delivery2");
    String gubun = request.getParameter("gubun");
    String level = request.getParameter("level");
    String bounse = request.getParameter("bounse");
    String sum ;


    //+ Integer.parseInt(cost2) + Integer.parseInt(delivery1) + Integer.parseInt(delivery2) + Integer.parseInt(level)
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function showProcessing()
    {
        var div1 = document.getElementById('div1');
        var div2 = document.getElementById('div2');

        div1.style.left = (document.body.offsetWidth / 2 - 160)+"px";
        div1.style.top = (document.body.offsetHeight / 2 - 100)+"px";
        div1.style.display = "block";

        div2.style.width = div1.offsetWidth;
        div2.style.height = div1.offsetHeight;
        div2.style.top = div1.style.top;
        div2.style.left = div1.style.left;
        div2.style.zIndex = div1.style.zIndex - 1;
        div2.style.display = "block";
    }
//-->
</SCRIPT>
<DIV ID="div1" style='POSITION:absolute;Z-INDEX:10;display:none'>
    <img src="/plm/portal/icon/processing.gif" border="0">
</DIV>

<form method="post" name="MGRCreate">
<input type="hidden" name="oid" value="<%=oid%>">
<table width=100% ><tr><td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td style="padding:10">
<table width="600" border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
        <!--popup타이틀 Start-->
        <tr>
          <td class="popuptop_L" width="20" height="40"></td>
          <td class="popuptop_BG text_white"><img src="/plm/portal/images/img_dongbu/popup_top_dot.gif" hspace="5" ><%=messageService.getString("e3ps.message.ket_message", "00402") %><%--Project 성과 입력--%><div></td>
          <td class="popuptop_BG" width="40"><a href="#" onClick="javascript:self.close()"><img src="/plm/portal/images/img_dongbu/popup_top_close.gif" border="0"></a></td>
          <td class="popuptop_R" width="20" height="40"></td>
        </tr>
        <!--popup타이틀 End-->
      </table>
    </td>
  </tr>
  <tr>
    <td class="space15"></td>
  </tr>
</table>

</td></tr><tr><td>

<!---리스트------->
            <table width="100%" border="2" cellpadding="1" cellspacing="1" bgcolor=#4B73E1 align=center>
                <tr><td height=1 width=100%></td>
            </tr>
            </table>
<table width="100%" border="2" cellpadding="0" cellspacing="0" align=center>


    <tr >
        <td width="12%"><%=messageService.getString("e3ps.message.ket_message", "02989") %><%--평가항목--%></td>
        <td width="7%"><%=messageService.getString("e3ps.message.ket_message", "00576") %><%--가중치--%></td>
        <td colspan=9><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
    </tr>
    <tr >
        <td rowspan=7 ><%=messageService.getString("e3ps.message.ket_message", "00400", "<br>") %><%--Project{0} 목표달성도--%></td>
        <td rowspan=7 >80%</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00752") %><%--결과값--%></td>
        <td colspan=4 ><%=messageService.getString("e3ps.message.ket_message", "02988") %><%--평가요소--%></td>
    </tr>
    <tr >
        <td>Quality</td>
        <td>
            <input type="text" name="quality" value="<%=quality==null?"":quality%>">
        </td>
        <td colspan=4 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "01383") %><%--목표 금형 품질 대비 실적 비율--%></td>
    </tr>
    <tr >
        <td rowspan=2 >Cost</td>
        <td>
            <input type="text" name="cost1" value="<%=cost1==null?"":cost1%>">
        </td>
        <td colspan=5 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "01384") %><%--목표 금형제작(디버깅포함) 비용 대비 실적 비율--%></td>
    </tr>
    <tr >
        <td>
            <input type="text" name="cost2" value="<%=cost2==null?"":cost2%>">
        </td>
        <td colspan=5 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "01382") %><%--목표 금형 원가 (C/T,SPM,Scrap,사용설비) 대비 실적 비율--%></td>
    </tr>
    <tr >
        <td rowspan=2 >Delivery</td>
        <td>
            <input type="text" name="delivery1" value="<%=delivery1==null?"":delivery1%>">
        </td>
        <td colspan=4 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "02097") %><%--양산승인 기준 목표일정 대비 실적 비율--%></td>
    </tr>
    <tr >
        <td>
            <input type="text" name="delivery2" value="<%=delivery2==null?"":delivery2%>">
        </td>
        <td colspan=4 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "00829") %><%--고객 Event Sample 공급 목표일정 대비 실적 비율--%></td>
    </tr>
    <tr >
        <td>
            <input type="text" name="gubun" value="<%=gubun==null?"":gubun%>">
        </td>
        <td>
            <input type="text" name="level" value="<%=level==null?"":level%>">
        </td>
        <td colspan=4 >&nbsp;* <%=messageService.getString("e3ps.message.ket_message", "00849") %><%--고객요구 평가 Spec 대비 달성율--%></td>
    </tr>
    <tr >
        <td rowspan=3 ><%=messageService.getString("e3ps.message.ket_message", "00628") %><%--개발난이도--%></td>
        <td rowspan=3 >20%</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
        <td>A Rank</td>
        <td>B Rank</td>
        <td>C Rank</td>
        <td>D Rank</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
    </tr>
    <tr >
        <td><%=messageService.getString("e3ps.message.ket_message", "00093") %><%--Box류--%></td>
        <td>100</td>
        <td>90</td>
        <td>80</td>
        <td>70</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01633") %><%--비고입력--%></td>
    </tr>
    <tr >
        <td><%=messageService.getString("e3ps.message.ket_message", "00126") %><%--Connector 류--%></td>
        <td>90</td>
        <td>80</td>
        <td>70</td>
        <td>60</td>
        <td><%=messageService.getString("e3ps.message.ket_message", "01633") %><%--비고입력--%></td>
    </tr>
    <tr >
        <td><%=messageService.getString("e3ps.message.ket_message", "02951") %><%--특허가산점--%></td>
        <td>
            <input type="text" name="bounse" value="<%=bounse==null?"":bounse%>" size="4">
        </td>
        <td colspan=6 ><%=messageService.getString("e3ps.message.ket_message", "03179") %><%--해당 Project 진행 중 출원 특허에 대한 평가결과 가산점 부여--%><br>(<%=messageService.getString("e3ps.message.ket_message", "02950") %><%--특허 평가 Table에 의한 0 ~ 10점 차등 부여(입력)--%>)</td>
    </tr>

</table>

</td></tr><tr><td>

<table width="95%" border="0" cellpadding="0" cellspacing="0" align=center valign=top>
    <tr height=25>
    <td align=center>
    <input type=submit value="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%>">
    </td></tr>

</table>

</td></tr></table></td></tr></table>
</form>
