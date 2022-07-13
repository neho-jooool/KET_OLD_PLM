<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
<h1>제품 프로젝트 종합현황</h1>

<table border="1">
   <colgroup align="center">
	    <col width="100px">
	    <col width="100px">
	    <col width="200px">
	    <col width="100px">
	    <col width="100px">
	    <col width="10px">
	    <col width="100px">
	    <col width="100px">
    </colgroup>
       
   
    <tr align="center">
        <td  rowspan="2">OEM</td>
        <td  rowspan="2">차종</td>
        <td  rowspan="2">상세차종</td>
        <td  colspan="4">OEM진행단계</td>
        <td  rowspan="2">합계</td>
        <td  colspan="3">프로젝트 진행상태</td>
        <td  colspan="3">Item</td>
        <td  rowspan="2">이슈</td>
    </tr>
    <tr align="center">
        <td>PROTO</td>
        <td>P1</td>
        <td>P2</td>
        <td>SOP</td>
        <td>완료</td>
        <td>진행</td>
        <td>지연</td>
        <td>MOLD</td>
        <td>PRESS</td>
        <td>구매품</td>
    </tr>
    <c:forEach items="${productPjtOverallData}" var="data">
      <tr align="center">
        <td>${data.customer}</td>
        <td><a href="">${data.carType}</a></td>
        <td>${data.detailCar}</td>
        <td>${data.proto}</td>
        <td>${data.p1}</td>
        <td>${data.p2}</td>
        <td>${data.sop}</td>
        <td><a href="">${data.total}</a></td>
        <td>${data.completed}</td>
        <td>${data.progress}</td>
        <td>${data.withdrawn}</td>
        <td>${data.moldCount}</td>
        <td>${data.pressCount}</td>
        <td>${data.goodsCount}</td>
        <td>${data.issueCount}</td>
    </tr>
    </c:forEach>
</table>

</div>