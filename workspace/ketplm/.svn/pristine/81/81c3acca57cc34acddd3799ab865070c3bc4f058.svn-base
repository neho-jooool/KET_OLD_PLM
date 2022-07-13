<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.text.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%
    DecimalFormatSymbols symbol = new DecimalFormatSymbols();
    DecimalFormat decimalFormat = new DecimalFormat("##0.#%");
%>
<%
    String planValue =  request.getParameter("planValue");
    String workValue =  request.getParameter("workValue");

    if( (planValue == null) || (planValue.trim().length() == 0) ) {
        planValue = "0";
    }

    if( (workValue == null) || (workValue.trim().length() == 0) ) {
        workValue = "0";
    }

    double planPer = Double.parseDouble(planValue);
    double workPer = Double.parseDouble(workValue);
%>
<html>
<head>
<title></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
body {
    background-color: #ffffff;
    margin: 0;
}

.bar_class {
    background-repeat:repeat-x;
    border:0;
    padding:0;

    margin-top:1em;

    font-size:.7em;
}
-->
</style>
</head>
<body>
    <table border=0 width=100% cellpadding=0 cellspacing=0>
        <tr height=10>
            <%
                double mark_left_width = planPer;
                double mark_right_width = 100-planPer;

                String mark_left_str = "";
                String mark_right_str = "";
                if(mark_left_width >= 5)
                    mark_left_str = decimalFormat.format(planPer/100);
                else
                    mark_right_str = decimalFormat.format(planPer/100);

            %>
            <td align=right width="<%=mark_left_width%>%">
                <font color='MidnightBlue' size='1em'><%=mark_left_str%></font><img src="/plm/portal/icon/bar_arrow1.gif">
            </td>
            <td align=left width="<%=mark_right_width%>%">
                <img src="/plm/portal/icon/bar_arrow2.gif"><font color='MidnightBlue' size='1em'><%=mark_right_str%></font>
            </td>
        </tr>
    </table>
    <table border=0 width=100% cellpadding=0 cellspacing=0>
        <tr height=10>
            <%
                double work_left_width = workPer;
                double work_right_width = 100-workPer;

                String work_left_str = "";
                String work_right_str = "";
                if(work_left_width >= 5)
                    work_left_str = decimalFormat.format(workPer/100);
                else
                    work_right_str = decimalFormat.format(workPer/100);

            %>
            <td style="padding:0 0 0 6px;"></td>
            <td class='bar_class' <%if(workPer!=0){%>background="/plm/portal/icon/bar_full.gif"<%}%> style="width:<%=work_left_width%>%;" align='right' valign='middle' nowrap=true>
                <div style="border:0;padding:0;margin:-0.3em 0.3em 0 0;"><font color='MidnightBlue' ><%=work_left_str%></font></div>
            </td>
            <td class='bar_class'  <%if(workPer!=100){%>background="/plm/portal/icon/bar_blank.gif"<%}%> style="width:<%=work_right_width%>%;" align='left'>
                <div style="border:0;padding:0;margin:-0.3em 0 0 0.3em;"><font color='MidnightBlue' ><%=work_right_str%></font></div>
            </td>
            <td style="padding:0 5px 0 0;"></td>
        </tr>
    </table>
</body>
</html>
