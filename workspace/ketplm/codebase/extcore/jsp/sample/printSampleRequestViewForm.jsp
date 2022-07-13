<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/sample/sampleRequest.js"></script>
    
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT>    
<script>
//Jquery
$(document).ready(function(){
    var strContent = document.innoditorDataForm["webEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.innoditorDataForm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.innoditorDataForm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.innoditorDataForm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
});


function requestApprove(requestOid)
{
    goPage(requestOid);
}   


function doPrint(){
    $('#buttonTable').hide();
    //window.print();
    //여백설정
    IEPageSetupX.topMargin=0;
    IEPageSetupX.bottomMargin=0;
    //가로설정
    IEPageSetupX.Orientation = 0;
    //머리글 설정
    IEPageSetupX.header="";
    //바닥글 설정
    IEPageSetupX.footer="";
    //배경색
    IEPageSetupX.PrintBackground = true;
    //미리보기
    IEPageSetupX.Preview();
    //인쇄
    //IEPageSetupX.Print();
    
    doneyet();
    
}

function doneyet() { 
    $('#buttonTable').show();
} 
</script>

<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${sampleRequest.webEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>


<table id="buttonTable" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;width: 980px">
    <tr>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue" onfocus="this.blur();">인쇄</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();">닫기</a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
       </td>
    </tr>
</table>
<table height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;width: 980px">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<div class="contents-wrap">
    <div>
        <table cellpadding="0" class="table-style-04 border-top-line" style="table-layout: fixed;width: 980px;height: 50px">
          <tbody>
               <tr>
                   <td class="title border-bottom-none"><span style="font-size:20px">샘플 요청서<span><br><span style="font-size:12px">(Sample Request)</span></td>
               </tr>
          </tbody>
        </table>
        <table cellpadding="0" class="table-style-04 border-top-line" style="table-layout: fixed;width: 980px">
          <colgroup>
              <col width="10%" />
              <col width="40%" />
              <col width="10%" />
              <col width="40%" />
          </colgroup>
             <tbody>
	            <tr>
                    <td class="title border-bottom-none">요청서No.</td>
                    <td class="border-bottom-none">${sampleRequest.requestNo}</td>
                    <td class="title border-bottom-none">요청기한</td>
                    <td class="border-bottom-none">${sampleRequest.requestDate}</td>
                </tr>
                <tr>
                    <td class="title border-bottom-none">제목</td>
                    <td colspan="3" class="border-bottom-none">${sampleRequest.requestTitle}</td>
                </tr>
             </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
            <colgroup>
              <col width="10%" />
              <col width="23%" />
              <col width="10%" />
              <col width="23%" />
              <col width="10%" />
              <col width="24%" />
          </colgroup>
             <tbody>
                <tr>
                    <td class="title border-bottom-none">요청부서</td>
                    <td class="border-bottom-none">${sampleRequest.createUserDeptName}</td>
                    <td class="title border-bottom-none">요청자</td>
                    <td class="border-bottom-none">${sampleRequest.createUserName}</td>
                    <td class="title border-bottom-none">요청일</td>
                    <td class="border-bottom-none">${sampleRequest.createDate}</td>
                </tr>
                <tr>
                    <td class="title border-bottom-none">고객사</td>
                    <td class="border-bottom-none">${sampleRequest.customerName}</td>
                    <td class="title border-bottom-none">고객담당자</td>
                    <td class="border-bottom-none">${sampleRequest.customerContractor}</td>
                    <td class="title border-bottom-none">차종</td>
                    <td class="border-bottom-none">${sampleRequest.carTypeName}</td>
                </tr>
             </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
            <colgroup>
              <col width="10%" />
              <col width="40%" />
              <col width="10%" />
              <col width="40%" />
          </colgroup>
             <tbody>
                <tr>
                    <td class="title border-bottom-none">승인자</td>
                    <td class="border-bottom-none">${sampleRequest.approvUserName}</td>
                    <td class="title border-bottom-none">승인일</td>
                    <td class="border-bottom-none">${sampleRequest.approvDate}</td>
                </tr>
                <tr>
                    <td colspan="4" class="title border-bottom-none">요청부품</td>
                </tr>
             </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
            <colgroup>
              <col width="10%" />
              <col width="24%" />
              <col width="4%" />
              <col width="9%" />
              <col width="8%" />
              <col width="8%" />
              <col width="6.5%" />
              <col width="7.5%" />
              <col width="8%" />
              <col width="8%" />
              <col width="8%" />
          </colgroup>
             <tbody>
                <tr>
                    <td rowspan="2" class="title border-bottom-none">부품번호</td>
                    <td rowspan="2" class="title border-bottom-none">품명</td>
                    <td rowspan="2" class="title border-bottom-none">rev</td>
                    <td rowspan="2" class="title border-bottom-none">Project No.</td>
                    <td rowspan="2" class="title border-bottom-none">개발단계</td>
                    <td rowspan="2" class="title border-bottom-none">담당자(PM)</td>
                    <td colspan="2" class="title border-bottom-none">수량</td>
                    <td colspan="3" class="title border-bottom-none">일자</td>
                </tr>
                <tr>
                    <td class="title border-bottom-none">요청</td>
                    <td class="title border-bottom-none">불출</td>
                    <td class="title border-bottom-none">접수</td>
                    <td class="title border-bottom-none">불출예정</td>
                    <td class="title border-bottom-none">불출</td>
                </tr>
                <c:forEach items="${samplePartList}" var="samplePart" varStatus="i">
                <tr>
                    <td align="center" class="border-bottom-none">${samplePart.partNo}</td>
                    <td class="border-bottom-none">${samplePart.partName}</td>
                    <td align="center" class="border-bottom-none">${samplePart.ver}</td>
                    <td align="center" class="border-bottom-none">${samplePart.projectNo}</td>
                    <td align="center" class="border-bottom-none">${samplePart.developeStageName}</td>
                    <td align="center" class="border-bottom-none">${samplePart.pmUserName}</td>
                    <td class="border-bottom-none right">${samplePart.requestCount}EA</td>
                    <td class="border-bottom-none right">${samplePart.dispensationCount}EA</td>
                    <td align="center" class="border-bottom-none">${samplePart.receptionDate}</td>
                    <td align="center" class="border-bottom-none">${samplePart.dispensationExpectDate}</td>
                    <td align="center" class="border-bottom-none">${samplePart.dispensationDate}</td>
                </tr>
                </c:forEach>
             </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
            <colgroup>
              <col width="10%" />
              <col width="90%" />
          </colgroup>
             <tbody>
                <tr>
                    <td class="title border-bottom-none">용도</td>
                    <td class="border-bottom-none">${sampleRequest.purpose}</td>
                </tr>
                <tr>
                    <td class="title border-bottom-none">요청사유</td>
                    <td class="border-bottom-none">
                        <div id="divView" style="overflow-x:auto; overflow-y:hidden; width: 100%" class="outline"></div>
                    </td>
                </tr>
             </tbody>
        </table>
         <table cellpadding="0" class="table-style-04 border-bottom-line" style="table-layout: fixed;width: 980px">
	         <tbody>
	            <c:if test="${secondaryFiles.size() eq 0 }">
		            <tr>
		                <td style="width: 10%" class="title">
		                      첨부
		                </td>
		                <td colspan="2" align="center">
		                      첨부파일이 존재 하지 않습니다.
		                </td>
		            </tr>
	            </c:if>
	            <c:if test="${secondaryFiles.size() != 0 }">
	               <c:forEach var="content" items="${secondaryFiles}" varStatus="i">
	                    <c:if test="${i.index eq 0 }">
	                        <tr>
	                            <td rowspan="${secondaryFiles.size()}" style="width: 10%" class="title">첨부</td>
	                            <td align="center" style="width: 5%">${i.index+1}</td>
	                            <td style="width: 85%">${content.name}</td>
	                        </tr>
	                    </c:if>
	                    <c:if test="${i.index != 0 }">
	                        <tr>
	                            <td align="center" style="width: 5%">${i.index+1}</td>
                                <td style="width: 85%">${content.name}</td>
	                        </tr>
	                    </c:if>
	                </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
</div>