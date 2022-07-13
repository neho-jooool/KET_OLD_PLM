<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<script type="text/javascript">
 function projectPopup(oid){
	 var url = "/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/ProjectViewFrm.jsp&key=oid&value="+oid;
	 window.open(url,"projectReportPopup","width=900, height=750,scrollbars=yes");
 }
</script>

<body class="popup-background popup-space">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07307") %></div>
<div align="right" class="b-space10"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>   
<table cellpadding="0" summary="" class="table-style-01">
    <colgroup>
        <col width="72px" />
        <col width="82px" />
        <col width="114px" />
        <col width="107px" />
        <col width="62px" />
        <col width="62px" />
        <col width="62px" />
        <col width="72px" />
        <col width="72px" />
        <col width="72px" />
        <col width="72px" />
        <col width="72px" />
        <col width="62px" />
        <col width="18px" />
    </colgroup>
    <thead>
        <tr>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "07234") %></td>
            <td rowspan="2">Project No</td>
            <td rowspan="2">Project Name</td>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02110") %></td>
            <td colspan="8"><%=messageService.getString("e3ps.message.ket_message", "07205") %></td>
            <td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02296") %></td>
            <td rowspan="2"></td>
        </tr>
        <tr>
            <td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07215") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "01012") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07209") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07210") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07211") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "07212") %></td>
            <td><%=messageService.getString("e3ps.message.ket_message", "01095") %></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td colspan="14" style="padding-left:0">
                <div style="height:493px;overflow-y:scroll;overflow-x:hidden">
                    <table cellpadding="0" class="table-style-01 table-scroll" sytle="border-top:none;border-bottom:none">
                        <colgroup>
                            <col width="72px" />
					        <col width="81px" />
					        <col width="110px" />
					        <col width="102px" />
					        <col width="62px" />
					        <col width="62px" />
					        <col width="62px" />
					        <col width="71px" />
					        <col width="72px" />
					        <col width="72px" />
					        <col width="72px" />
					        <col width="72px" />
					        <col width="62px" />
                        </colgroup>
                        <tbody>
                        <c:forEach var="data" items="${manufactureProjectList}">
                             <tr>
                                <td class="bgcol fontb center">${data.devType}</td>
                                <td class="bgcol fontb center"><a href="javascript:projectPopup('${data.oid}')">${data.pjtNO}</a></td>
                                <td class="bgcol fontb center" title="${data.pjtName}"><div style="width:95px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">${data.pjtName}</div></td>
                                <td class="bgcol fontb center" title="${data.outsourcing}"><div style="width:95px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;">${data.outsourcing}</div></td>
                                <td class="center">${data.taskTotalCount}/${data.taskTotalDelayCount}</td>
                                <td class="center">${data.plan}/${data.planDelay}</td>
                                <td class="center">${data.firstTry}/${data.firstTryDelay}</td>
                                <td class="center">${data.debuging1}/${data.debugingDelay1}</td>
                                <td class="center">${data.debuging2}/${data.debugingDelay2}</td>
                                <td class="center">${data.debuging3}/${data.debugingDelay3}</td>
                                <td class="center">${data.debuging4}/${data.debugingDelay4}</td>
                                <td class="center">${data.moldtransfer}/${data.moldtransferDelay}</td>
                                <td class="center">${data.issueCount}</td>
                             </tr> 
                          </c:forEach>  
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
    </tbody>
</table>
</body>