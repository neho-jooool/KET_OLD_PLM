<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


    <div class="sub-title"><img src="/plm/portal/images/icon_3.png" />자동차 사업부 프로젝트 진행현황 (차종개수 ${carTypeCount}건)</div>   
    <div class="b-space30">
        <table class="table-style-01" cellpadding="0">
            <colgroup>
                <col width="5%" />
                <col width="5%" />
                <col width="5%" />
                <col width="8%" />
                <col width="4%" />
                <col width="7%" />
                <col width="7%" />
                <col width="5%" />
                <col width="7%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="3%" />
                <col width="5%" />
                <col width="3%" />
                <col width="3%" />
            </colgroup>
            <thead>
                <tr>
                    <td rowspan="2">OEM</td>
                    <td rowspan="2">차종</td>
                    <td rowspan="2">형태</td>
                    <td rowspan="2">상세차종</td>
                    <td rowspan="2">생산량(천대)</td>
                    <td colspan="2">진행단계</td>
                    <td rowspan="2">진행현황</td>
                    <td rowspan="2">SOP</td>
                    <td colspan="3">제품프로젝트</td>
                    <td colspan="3">제품Task</td>
                    <td colspan="3">금형프로젝트</td>
                    <td colspan="3">Item</td>
                    <td colspan="2">미결이슈</td>
                </tr>
                <tr>
                    <td>현단계</td>
                    <td>다음단계</td>
                    <td>계</td>
                    <td>완료</td>
                    <td>진행</td>
                    <td>계</td>
                    <td>완료</td>
                    <td>진행</td>
                    <td>계</td>
                    <td>완료</td>
                    <td>진행</td>
                    <td>Mold</td>
                    <td>Press</td>
                    <td>구매품</td>
                    <td>이슈</td>
                    <td>품질</td>
                </tr>
            </thead>
            <tbody>
              <c:forEach var="data" items="${carDivisionProjectData}">
                <tr>
                    <td class="center">${data.customer}</td>
                    <td class="center">${data.carType}</td>
                    <td class="center">${data.formType}</td>
                    <td class="center">${data.modelName}</td>
                    <td class="center">${data.total}</td>
                    <td class="center">${data.currentStep}</br>${data.currentDate}</td>
                    <c:if test="${data.nextDate ne '-'}">
                       <td class="center">${data.nextStep}</br>${data.nextDate}</td>
                    </c:if>
                    <c:if test="${data.nextDate eq '-'}">
                       <td class="center">-</td>
                    </c:if>
                    <td class="center">
                            <c:if test="${data.currentGate ne null}">
                                <c:if test="${data.currentGate eq 'R'}"><img src="/plm/extcore/image/ico_red.png" /></c:if>
                                <c:if test="${data.currentGate eq 'Y'}"><img src="/plm/extcore/image/ico_yellow.png" /></c:if>
                                <c:if test="${data.currentGate eq 'G'}"><img src="/plm/extcore/image/ico_green.png" /></c:if> 
                            </c:if>
                    </td>
                    <td class="center">${data.sopDate}</td>
                    <td class="center"><a href="javascript:opener.productTotalProjectPopup('${data.customer}','${data.carType}')">${data.productTotal}</a></td>
                    <td class="center"><a href="javascript:opener.productCompleteProjectPopup('${data.customer}','${data.carType}')">${data.completeCount}</a></td>
                    <td class="center"><a href="javascript:opener.productProcessProjectPopup('${data.customer}','${data.carType}')">${data.processCount}<c:if test="${data.processCount >= 100}"><br></c:if><c:if test="${data.delayCount > 0}">/<span style="color:#ff0000;">${data.delayCount}</span></c:if></a></td>
                    <td class="center"><a href="javascript:opener.productTotalTaskPopup('${data.customer}','${data.carType}')">${data.taskTotal}</a></td>
                    <td class="center"><a href="javascript:opener.productCompleteTaskPopup('${data.customer}','${data.carType}')">${data.completeTaskCount}</a></td>
                    <td class="center"><a href="javascript:opener.productProcessTaskPopup('${data.customer}','${data.carType}')">${data.processTaskCount}<c:if test="${data.processTaskCount >= 100}"><br></c:if><c:if test="${data.delayTaskCount > 0}">/<span style="color:#ff0000;">${data.delayTaskCount}</span></c:if></a></td>
                    <td class="center"><a href="javascript:opener.moldTotalProjectPopup('${data.customer}','${data.carType}')">${data.moldTotal}</a></td>
                    <td class="center"><a href="javascript:opener.moldCompleteProjectPopup('${data.customer}','${data.carType}')">${data.moldCompleteCount}</a></td>
                    <td class="center"><a href="javascript:opener.moldProcessProjectPopup('${data.customer}','${data.carType}')">${data.moldProcessCount}<c:if test="${data.moldProcessCount >= 100}"><br></c:if><c:if test="${data.moldDelayCount > 0}">/<span style="color:#ff0000;">${data.moldDelayCount}</span></c:if></a></td>
                    <td class="center"><a href="javascript:opener.itemMoldListPopup('${data.customer}','${data.carType}','Mold')">${data.itemMoldTotal}</a></td>
                    <td class="center"><a href="javascript:opener.itemMoldListPopup('${data.customer}','${data.carType}','Press')">${data.itemPressTotal}</a></td>
                    <td class="center"><a href="javascript:opener.itemGoodsListPopup('${data.customer}','${data.carType}','구매품')">${data.itemGoodsTotal}</a></td>
                    <td class="center"><a href="javascript:opener.issueProjectPopup('${data.customer}','${data.carType}')">${data.issueTotal}</a></td>
                    <td class="center"><a href="javascript:opener.quiltyProblemPopup('${data.customer}','${data.carType}')">${data.qualityTotal}</a></td>
                </tr> 
                </c:forEach>                            
            </tbody>
        </table>
    </div>
