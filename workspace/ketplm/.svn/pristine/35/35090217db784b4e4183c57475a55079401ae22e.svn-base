<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- 신 HTML -->
<body class="popup-background popup-space">
<form id="stepMoldData">
	<input type="hidden" id="type" name="type" /> 
	<input type="hidden" id="state" name="state" />
	<input type="hidden" id="step" name="step" /> 
	<input type="hidden" id="debuging" name="debuging">
	<input type="hidden" id="year" name="year">
	<input type="hidden" id="month" name="month">
	<div class="contents-wrap">
		<div class="sub-title">
			<img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "07197") %>
		</div>
		<div align="right" class="b-space10"><span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></span><span class="pro-cell b-right"></span></span></span></div>
		<div class="inquiry-table02 clearfix b-space30">
			<div class="float-r">
				<span class="r-space7 v-middle"><%=messageService.getString("e3ps.message.ket_message", "01051") %> :</span>
				<span class="r-space15"><input type="radio" id="total" name="moldType" value="Total" checked="checked"><%=messageService.getString("e3ps.message.ket_message", "02485") %></span>
				<span class="r-space15"><input type="radio" id="press" name="moldType" value="Press"> Press</span>
				<span class="r-space15"><input type="radio" id="mold" name="moldType" value="Mold">&nbsp;&nbsp;Mold</span>
				<span class="r-space15"><input type="checkbox" id="useCompleteType" name="useCompleteType" value="complete"><%=messageService.getString("e3ps.message.ket_message", "07203") %></span>
				<%=messageService.getString("e3ps.message.ket_message", "01175") %><span class="r-space15 v-middle">
				        <select id="yearSetting" name="yearSetting" multiple="multiple"></select>
				   </span>
				<%=messageService.getString("e3ps.message.ket_message", "02224") %><span class="r-space15 v-middle">
				        <select id="monthSetting" name="monthSetting" multiple="multiple">
                               <option>1</option>
                               <option>2</option>
                               <option>3</option>
                               <option>4</option>
                               <option>5</option>
                               <option>6</option>
                               <option>7</option>
                               <option>8</option>
                               <option>9</option>
                               <option>10</option>
                               <option>11</option>
                               <option>12</option>
                            </select>
				  </span>
				<%=messageService.getString("e3ps.message.ket_message", "01662") %>: <span class="r-space15 v-middle">
				            <select id="division" name="division" multiple="multiple">
			                    <option value="total"><%=messageService.getString("e3ps.message.ket_message", "02485") %></option>
			                    <option value="elect"><%=messageService.getString("e3ps.message.ket_message", "02483") %></option>
			                    <option value="car"><%=messageService.getString("e3ps.message.ket_message", "02401") %></option>
			                </select>
				        </span>
				<span class="in-block v-middle r-space10"><span
					class="pro-table"><span class="pro-cell b-left"></span><span
						class="pro-cell b-center"><a href="#" class="btn_blue" id="search"><%=messageService.getString("e3ps.message.ket_message", "02652") %></a></span><span
						class="pro-cell b-right"></span></span></span><!--<span class="r-space20"> <img
					src="/plm/portal/images/excel.png" /> </span>-->
			</div>
		</div>
		<p style="text-align: right; padding-right: 10px" class="b-space7"><%=messageService.getString("e3ps.message.ket_message", "01194") %>
			: <%=messageService.getString("e3ps.message.ket_message", "00698") %></p>
		<div class="b-space30">
			<table cellpadding="0" summary="" class="table-style-01">
				<colgroup>
					<col width="3%" />
					<col width="10%" />
					<col width="9%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="7%" />
					<col width="8%" />
				</colgroup>
				<thead>
					<tr>
						<td rowspan="2" colspan="2"><%=messageService.getString("e3ps.message.ket_message", "00969") %></td>
						<td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "07204") %></td>
						<td colspan="10"><%=messageService.getString("e3ps.message.ket_message", "07205") %></td>
						<td rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "02296") %></td>
					</tr>
					<tr>
						<td><%=messageService.getString("e3ps.message.ket_message", "07192") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07206") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07207") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "00571") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07208") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07209") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07210") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07211") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "07212") %></td>
						<td><%=messageService.getString("e3ps.message.ket_message", "01095") %></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${stepMoldData}" var="data">
						<c:if test="${data.state eq '시작금형' and data.type eq '사내'}">
							<tr>
								<td class="bgcol fontb center" rowspan="5">사내제작</td>
								<td class="bgcol fontb center">${data.state}</td>
								<td class="center"><a
									href="javascript:projectReport('${data.type}','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','제품도출도');">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','금형설계');">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형설계');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','금형부품');">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','금형Try_[T0]');">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','1 차 디버깅');">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','2 차 디버깅');">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','3 차 디버깅');">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','4 차 디버깅');">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','${data.state}','금형이관단계');">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:issueReport('${data.type}','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if
							test="${data.state ne '시작금형' and  data.type eq '사내' and data.state ne '소계'}">
							<tr>
								<td class="bgcol fontb center">${data.state}</td>
                                <td class="center"><a
                                    href="javascript:projectReport('${data.type}','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','제품도출도');">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형설계');">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형설계');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형부품');">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형Try_[T0]');">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','1 차 디버깅');">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','2 차 디버깅');">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','3 차 디버깅');">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','4 차 디버깅');">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형이관단계');">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:issueReport('${data.type}','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if test="${data.type eq '사내' and data.state eq '소계'}">
							<tr>
								<td class="bgcol fontb center">소계</td>
								<td class="center"><a href="javascript:projectReport('${data.type}','');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','','total');"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','제품도출도')">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','제품도출도');"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','금형설계')">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형설계');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','금형부품')">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','금형Try_[T0]')">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','1 차 디버깅')">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','2 차 디버깅')">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','3 차 디버깅')">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','4 차 디버깅')">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('${data.type}','','금형이관단계')">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:issueReport('${data.type}','');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if test="${data.state eq '시작금형' and data.type eq '외주'}">
							<tr>
								<td class="bgcol fontb center" rowspan="5">외주제작</td>
								<td class="bgcol fontb center">${data.state}</td>
                                <td class="center"><a
                                    href="javascript:projectReport('${data.type}','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','제품도출도');">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','외주금형제작');">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','외주금형제작');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형부품');">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형Try_[T0]');">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','1 차 디버깅');">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','2 차 디버깅');">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','3 차 디버깅');">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','4 차 디버깅');">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형이관단계');">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:issueReport('${data.type}','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if
							test="${data.state ne '시작금형' and  data.type eq '외주' and data.state ne '소계'}">
							<tr>
								<td class="bgcol fontb center">${data.state}</td>
                                <td class="center"><a
                                    href="javascript:projectReport('${data.type}','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','제품도출도');">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','외주금형제작');">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','외주금형제작');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형부품');">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형Try_[T0]');">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','1 차 디버깅');">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','2 차 디버깅');">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','3 차 디버깅');">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','4 차 디버깅');">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','${data.state}','금형이관단계');">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','${data.state}','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:issueReport('${data.type}','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if test="${data.type eq '외주' and data.state eq '소계'}">
							<tr>
								<td class="bgcol fontb center">소계</td>
                                <td class="center"><a href="javascript:projectReport('${data.type}','');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('${data.type}','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('${data.type}','','total');"><span style="color:red;"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','제품도출도')">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','제품도출도');"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','외주금형제작')">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','외주금형제작');"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','금형부품')">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형부품');"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','금형Try_[T0]')">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형Try_[T0]');"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','1 차 디버깅')">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','1 차 디버깅');"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','2 차 디버깅')">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','2 차 디버깅');"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','3 차 디버깅')">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','3 차 디버깅');"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','4 차 디버깅')">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('${data.type}','','4 차 디버깅');"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
                                <td class="center"><a
                                    href="javascript:taskReport('${data.type}','','금형이관단계')">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('${data.type}','','금형이관단계');"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
                                <td class="center"><a href="javascript:issueReport('${data.type}','');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if test="${data.state eq '시작금형' and data.type eq '전체'}">
							<tr>
								<td class="bgcol fontb center" rowspan="5">전체</td>
								<td class="bgcol fontb center">${data.state}</td>
								<td class="center"><a href="javascript:projectReport('','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','제품도출도')">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형설계,외주금형제작')">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형설계,외주금형제작')"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형부품')">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형부품')"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형Try_[T0]')">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형Try_[T0]')"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','1 차 디버깅')">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','1 차 디버깅')"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','2 차 디버깅')">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','2 차 디버깅')"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','3 차 디버깅')">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','3 차 디버깅')"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','4 차 디버깅')">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','4 차 디버깅')"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형이관단계')">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형이관단계')"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:issueReport('','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if
							test="${data.state ne '시작금형' and  data.type eq '전체' and data.state ne '소계'}">
							<tr>
								<td class="bgcol fontb center">${data.state}</td>
								<td class="center"><a href="javascript:projectReport('','${data.state}');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('','${data.state}','total')"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','제품도출도')">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형설계,외주금형제작')">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형설계,외주금형제작')"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형부품')">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형부품')"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형Try_[T0]')">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형Try_[T0]')"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','1 차 디버깅')">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','1 차 디버깅')"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','2 차 디버깅')">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','2 차 디버깅')"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','3 차 디버깅')">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','3 차 디버깅')"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','4 차 디버깅')">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('','${data.state}','4 차 디버깅')"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
								<td class="center"><a
									href="javascript:taskReport('','${data.state}','금형이관단계')">${data.moldtransfer}</a><c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('','${data.state}','금형이관단계')"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:issueReport('','${data.state}');">${data.issueCount}</a></td>
							</tr>
						</c:if>
						<c:if test="${data.type eq '전체' and data.state eq '소계'}">
							<tr>
								<td class="bgcol fontb center">소계</td>
								<td class="center"><a href="javascript:projectReport('','');">${data.pjtCount}</a><c:if test="${data.pjtDelayCount > 0}">/<a href="javascript:projectDelayReport('','${data.state}')"><span style="color:red;">${data.pjtDelayCount}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','total')">${data.total}</a><c:if test="${data.delaytotal > 0}">/<a href="javascript:taskDelayReport('','','total')"><span style="color:red;"><span style="color:red;">${data.delaytotal}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','제품도출도')">${data.begin}</a><c:if test="${data.beginDelay > 0}">/<a href="javascript:taskDelayReport('','','제품도출도')"><span style="color:red;">${data.beginDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','금형설계,외주금형제작')">${data.plan}</a><c:if test="${data.planDelay > 0}">/<a href="javascript:taskDelayReport('','','금형설계,외주금형제작')"><span style="color:red;">${data.planDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','금형부품')">${data.process}</a><c:if test="${data.processDelay > 0}">/<a href="javascript:taskDelayReport('','','금형부품')"><span style="color:red;">${data.processDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','금형Try_[T0]')">${data.firstTry}</a><c:if test="${data.firstTryDelay > 0}">/<a href="javascript:taskDelayReport('','','금형Try_[T0]')"><span style="color:red;">${data.firstTryDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','1 차 디버깅')">${data.debuging1}</a><c:if test="${data.debugingDelay1 > 0}">/<a href="javascript:taskDelayReport('','','1 차 디버깅')"><span style="color:red;">${data.debugingDelay1}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','2 차 디버깅')">${data.debuging2}</a><c:if test="${data.debugingDelay2 > 0}">/<a href="javascript:taskDelayReport('','','2 차 디버깅')"><span style="color:red;">${data.debugingDelay2}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','3 차 디버깅')">${data.debuging3}</a><c:if test="${data.debugingDelay3 > 0}">/<a href="javascript:taskDelayReport('','','3 차 디버깅')"><span style="color:red;">${data.debugingDelay3}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','4 차 디버깅')">${data.debuging4}</a><c:if test="${data.debugingDelay4 > 0}">/<a href="javascript:taskDelayReport('','','4 차 디버깅')"><span style="color:red;">${data.debugingDelay4}</span></a></c:if></td>
								<td class="center"><a href="javascript:taskReport('','','금형이관단계')">${data.moldtransfer}<c:if test="${data.moldtransferDelay > 0}">/<a href="javascript:taskDelayReport('','','금형이관단계')"><span style="color:red;">${data.moldtransferDelay}</span></a></c:if></td>
								<td class="center"><a href="javascript:issueReport('','');">${data.issueCount}</a></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>

<script type="text/javascript">
$(document).ready(function(){
	printTime();
	
	CommonUtil.singleSelect('division',100);
	CommonUtil.singleSelect('yearSetting',100);
	CommonUtil.singleSelect('monthSetting',100);
	
	
	if("Press" == "${moldType}"){
		$("#press").attr("checked", true);
	} 
	if("Mold" == "${moldType}"){
		$("#mold").attr("checked", true);
	}
	
	if("complete" == "${completeType}"){
		$("#useCompleteType").attr("checked", true);
	}
	
	
	
	
	$("#search").click(function(){
		showProcessing();
		$("#stepMoldData").attr({action:"/plm/ext/dashboard/moldMakeSituationPopup", method:"post", target:"_self"}).submit();
	})
	
    
	
});

function projectReport(taskType, taskState){
	//alert($("#division").val());
	$("#type").val(taskType);
    $("#state").val(taskState);
    window.open("","projectReportPopup","width=1000, height=700");
    $("#stepMoldData").attr({action:"/plm/ext/dashboard/projectReportSetting", target:"projectReportPopup", method: "POST"}).submit();
}

function projectDelayReport(taskType, taskState){
	$("#type").val(taskType);
    $("#state").val(taskState);
    window.open("","projectDelayReport","width=1000, height=700");
    $("#stepMoldData").attr({action:"/plm/ext/dashboard/projectDelayReportSetting", target:"projectDelayReport", method: "POST"}).submit();
}

function taskReport(taskType, taskState, moldStep){
	//alert(taskType+" "+taskState+" "+moldStep);
	$("#type").val(taskType);
	$("#state").val(taskState);
	if(moldStep == "1 차 디버깅" || moldStep == "2 차 디버깅" || moldStep == "3 차 디버깅" || moldStep == "4 차 디버깅"){
		$("#debuging").val("debuging");
	}
	$("#step").val(moldStep);
    window.open("","taskReportPopup","width=1000, height=700");
    $("#stepMoldData").attr({action:"/plm/ext/dashboard/projectTaskReportSetting", target:"taskReportPopup", method: "POST"}).submit();
    
}

function taskDelayReport(taskType, taskState, moldStep){
    $("#type").val(taskType);
    $("#state").val(taskState);
    if(moldStep == "1 차 디버깅" || moldStep == "2 차 디버깅" || moldStep == "3 차 디버깅" || moldStep == "4 차 디버깅"){
        $("#debuging").val("debuging");
    }
    $("#step").val(moldStep);
    window.open("","taskReportPopup","width=1000, height=700");
    $("#stepMoldData").attr({action:"/plm/ext/dashboard/projectTaskDelayReportSetting", target:"taskReportPopup", method: "POST"}).submit();
    
}


function issueReport(taskType, taskState){
	$("#type").val(taskType);
    $("#state").val(taskState);
    window.open("","issueReportPopup","width=1000, height=700");
    $("#stepMoldData").attr({action:"/plm/ext/dashboard/projectIssueReportSetting", target:"issueReportPopup", method: "POST"}).submit();
}

function printTime() {
    
    var now = new Date();                                                  // 현재시간
    var nowTime = now.getFullYear() + "." + (now.getMonth()+1) + "." + now.getDate() + " " + now.getHours() + ":" + now.getMinutes();
    $("#clock").html(nowTime);
    //$("#year").val(now.getFullYear());
    //$("#month").val((now.getMonth()+1));
   // setTimeout("printTime()",1000);         // setTimeout(“실행할함수”,시간) 시간은1초의 경우 1000
    var standardYear  = Number(now.getFullYear()) + 1;
    var str ="";
    for(var i=0; i <= 10; i++){
        str += "<option value='"+standardYear+"'>"+standardYear+"</option>";
        standardYear--;
    }
    $("#yearSetting").append(str);
    
    if("${yearSetting}" == 0){
        $("#yearSetting").val(now.getFullYear()).attr("selected", "selected");
        $("#year").val(now.getFullYear());
    }else{
    	$("#yearSetting").val("${yearSetting}").attr("selected", "selected");
    	$("#year").val("${yearSetting}");
    }
    
    if("car" == "${divisionType}"){
        $("#division").val("car").attr("selected","selected");
    }else if("elect" == "${divisionType}"){
        $("#division").val("elect").attr("selected","selected");
    }else{
        $("#division").val("total").attr("selected","selected");
    }
    //$("#division").val("total").attr("selected","selected");
   // alert("${monthSetting}");
    
    if("${monthSetting}" == 0){
        $("#monthSetting").val(now.getMonth()+1).attr("selected", "selected");
        $("#month").val(now.getMonth()+1);
    }else{
    	$("#monthSetting").val("${monthSetting}").attr("selected", "selected");
    	$("#month").val("${monthSetting}");
    }
}

</script>