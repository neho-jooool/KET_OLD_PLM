<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    src="/plm/extcore/js/dqm/dqm.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	// 이노디터에서 작성된 내용을 전달
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////


     // 이노디터에서 작성된 내용을 전달
    var strContent1 = document.innoditorDataForm1["webEditor1"].value;

    var objView1 = document.getElementById("divView1");
    objView1.innerHTML = strContent1;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.innoditorDataForm1["hdnBackgroundColor1"].value;
    if("" != strBackgroundColor)
    {
        objView1.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.innoditorDataForm1["hdnBackgroundImage1"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView1.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView1.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.innoditorDataForm1["hdnBackgroundRepeat1"].value;
    if("" != strBackgroundRepeat)
    {
        objView1.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    var strContent2 = document.innoditorDataForm2["webEditor2"].value;

    var objView2 = document.getElementById("divView2");
    objView2.innerHTML = strContent2;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor2 = document.innoditorDataForm2["hdnBackgroundColor2"].value;
    if("" != strBackgroundColor2)
    {
        objView2.style.backgroundColor = strBackgroundColor2;
    }

    var strBackgroundImage2 = document.innoditorDataForm2["hdnBackgroundImage2"].value;
    if("" != strBackgroundImage2)
    {
        var strCopyBackgroundImage2 = strBackgroundImage2.toUpperCase();

        if("none" == strCopyBackgroundImage2)
        {
            objView2.style.backgroundImage = strBackgroundImage2;
        }
        else
        {
            objView2.style.backgroundImage = "url(" + strBackgroundImage2 + ")";
        }
    }

    var strBackgroundRepeat2 = document.innoditorDataForm2["hdnBackgroundRepeat2"].value;
    if("" != strBackgroundRepeat2)
    {
        objView2.style.backgroundRepeat = strBackgroundRepeat2;
    }
    
    $.ajax({
        url : "/plm/ext/dms/getApprovalLine.do",
        type : "POST",
        data : {
            pbooid : "${dqm.raiseOid}"
        },
        dataType : 'json',
        async : false,
        success : function(data) {
            $("#approvalLineDiv").html(data);
        }
    });
})

var selDownUrl = "";
var oid = "${dqm.oid}";
var raiseOid = "${dqm.raiseOid}";

function requestApprove(requestOid)
{
    goPage(requestOid);
}


function allCheck(checkboxName, isChecked) {
    var checkedList = document.getElementsByName(checkboxName);

    for ( var i = 0; i < checkedList.length; i++) {
        checkedList[i].checked = isChecked;
    }
}

function selDownload(){
    //get 방식
    /*
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected?oid="+oid;
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            selDownUrl += "&soid=OR:" + $('[name=secondaryFileOids]')[i].value;
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    openWindow(selDownUrl,"","200","200","scrollbars=no,resizable=no");
    */
    
    //post 방식
    document.getElementById("selDownloadForm").innerHTML = "";
    selDownUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet/selected";
    var checkedList = document.getElementsByName('fileSelect');

    var checkFlag = false;
    var str = "";
    var subStr = "";
    for ( var i = 0; i < checkedList.length; i++) {
        if(checkedList[i].checked){
            checkFlag = true;
            subStr += "<input type='hidden' name='soid' value=\'"+"OR:" + $('[name=secondaryFileOids]')[i].value+"\'/>";
        }
    }
    if(!checkFlag){
        alert("선택해");
        return;
    }
    str +=  "<input type='hidden' name='oid' value=\'"+raiseOid+"\'/>";
    str +=  subStr;
    
    alert(str);
    document.getElementById("selDownloadForm").innerHTML = str;
    
    var multiDownForm = document.selDownloadForm;
    window.open("" ,"selDownloadForm", 
          "toolbar=no, width=200, height=200, directories=no, status=no,    scrollorbars=no, resizable=no"); 
    multiDownForm.action =selDownUrl; 
    multiDownForm.method="post";
    multiDownForm.target="selDownloadForm";
    multiDownForm.submit();
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

<OBJECT id=IEPageSetupX classid="clsid:41C5BC45-1BE8-42C5-AD9F-495D6C8D7586" codebase="/plm/extcore/js/dashboard/IEPageSetupX.cab#version=1,4,0,3" width=0 height=0><param name="copyright" value="http://isulnara.com">
</OBJECT>     

<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${dqm.webEditor}</textarea>

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
<form name="innoditorDataForm1" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor1" rows="0" cols="0" style="display: none">${dqm.causeWebEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor1" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage1" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat1" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>

<form name="innoditorDataForm2" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor2" rows="0" cols="0" style="display: none">${dqm.improveWebEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat2" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>

<table id="buttonTable" width="99%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;width: 980px">
    <tr>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doPrint();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "09074") %><%--인쇄--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
       </td>
    </tr>
</table>
<table width="99%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;width: 980px">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<div class="contents-wrap">
    <div>
        <table cellpadding="0" class="table-style-04 border-top-line" style="table-layout: fixed;width: 980px">
            <colgroup>
                <col width="60%">
                <col width="4%">
                <col width="12%">
                <col width="12%">
                <col width="12%">
            </colgroup>
            <tbody>
                <tr>
                    <td rowspan="2" class="title border-bottom-none"><span style="font-size:20px"><%=messageService.getString("e3ps.message.ket_message", "09073") %><%--개발품질문제 검토보고서--%><span><br><span style="font-size:12px">(Development Quality Problem Review)</span></td>
                    <td rowspan="2" class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "00755") %><%--결재--%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02423") %><%--작성--%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "04840") %><%--검토--%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "01987") %><%--승인--%></td>
                </tr>
                <tr>
                    <td class="center border-bottom-none">${dqm.actionUserName}<br>${dqm.actionUserDept}<br>${dqm.actionCreateStamp}</td>
                    <td class="center border-bottom-none">${dqm.actionReviewName}<br>${dqm.actionReviewDept}<br>${dqm.actionReviewDate}</td>
                    <td class="center border-bottom-none">${dqm.actionApprovName}<br>${dqm.actionApprovDept}<br>${dqm.actionApprovDate}</td>
                </tr>
                <!-- 재기 검토 결재선에서 검토결재선만 보이도록 수정함
                <tr>
                    <td rowspan="4" class="title border-bottom-none"><span style="font-size:20px">개발품질문제 검토보고서<span><br><span style="font-size:12px">(Development Quality Problem Review)</span></td>
                    <td rowspan="2" class="title">제기<br>결재</td>
                    <td class="title">작성</td>
                    <td class="title">검토</td>
                    <td class="title">승인</td>
                </tr>
                
                <tr>
                    <td class="center">${dqm.raiseCreateUserName}<br>${dqm.raiseCreateUserDept}<br>${dqm.raiseCreateStamp}</td>
                    <td class="center">${dqm.raiseReviewName}<br>${dqm.raiseReviewDept}<br>${dqm.raiseReviewDate}</td>
                    <td class="center">${dqm.raiseApprovName}<br>${dqm.raiseApprovDept}<br>${dqm.raiseApprovDate}</td>
                </tr>
                <tr>
                    <td rowspan="2" class="title border-bottom-none">검토<br>결재</td>
                    <td class="title">작성</td>
                    <td class="title">검토</td>
                    <td class="title">승인</td>
                </tr>
                <tr>
                    <td class="center border-bottom-none">${dqm.actionUserName}<br>${dqm.actionUserDept}<br>${dqm.actionCreateStamp}</td>
                    <td class="center border-bottom-none">${dqm.actionReviewName}<br>${dqm.actionReviewDept}<br>${dqm.actionReviewDate}</td>
                    <td class="center border-bottom-none">${dqm.actionApprovName}<br>${dqm.actionApprovDept}<br>${dqm.actionApprovDate}</td>
                </tr>
                -->
            </tbody>
        </table>
        <table cellpadding="0" class="table-style-04 border-top-line" style="table-layout: fixed;width: 980px">
            <colgroup>
	              <col width="10%" />
	              <col width="23%" />
	              <col width="10%" />
	              <col width="57%" />
	          </colgroup>
            <tbody>
                <tr>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "09001") %><%--품질문제 No--%></td>
                    <td class="border-bottom-none">${dqm.problemNo}</td>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "09002") %><%--문제내용--%></td>
                    <td class="border-bottom-none">${dqm.problem}</td>
                </tr>
                <tr>
                    <td class="title border-bottom-none">Project No</td>
                    <td class="border-bottom-none">${dqm.pjtno}</td>
                    <td class="title border-bottom-none">Project Name</td>
                    <td class="border-bottom-none">${dqm.pjtname}</td>
                </tr>
            </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
          <colgroup>
              <col width="10%" />
              <col width="33%" />
              <col width="10%" />
              <col width="47%" />
          </colgroup>
             <tbody>
	            <tr>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
                    <td class="border-bottom-none">${dqm.customerName}</td>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
                    <td class="border-bottom-none">${dqm.cartypeName}</td>
                </tr>
             </tbody
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
          <colgroup>
              <col width="10%" />
              <col width="23%" />
              <col width="10%" />
              <col width="24%" />
              <col width="10%" />
              <col width="23%" />
          </colgroup>
             <tbody>
                <tr>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "07272") %><%--불량구분--%></td>
                    <td class="border-bottom-none">${dqm.defectDivName}</td>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "09004") %><%--불량유형--%></td>
                    <td class="border-bottom-none">${dqm.defectTypeName}</td>
                    <td class="title border-bottom-none"><%=messageService.getString("e3ps.message.ket_message", "04126") %><%--긴급도--%></td>
                    <td class="border-bottom-none">${dqm.urgencyName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09025") %><%--적용부위1--%></td>
                    <td>${dqm.applyArea1}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09026") %><%--적용부위2--%></td>
                    <td>${dqm.applyArea2}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09027") %><%--적용부위3--%></td>
                    <td>${dqm.applyArea3}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "00932") %><%--관련부품--%></td>
                    <td>${dqm.relatedPart}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "06112") %><%--부품분류--%></td>
                    <td>${dqm.partCategoryName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02004") %><%--시리즈--%></td>
                    <td>${dqm.seriesName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07139") %><%--발생구분--%></td>
                    <td>${dqm.occurDivName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09028") %><%--발생단계--%></td>
                    <td>${dqm.occurStepName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07138") %><%--발생일--%></td>
                    <td>${dqm.occurDate}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "07137") %><%--발생처--%></td>
                    <td>${dqm.occurName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09029") %><%--발생장소--%></td>
                    <td>${dqm.occurPlaceName}</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                    <td>${dqm.actionDeptName}</td>
                </tr>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "04630") %><%--문제점--%><br>/<br><%=messageService.getString("e3ps.message.ket_message", "04127") %><%--현상--%></td>
                    <td colspan="5">
                        <div id="divView" style="overflow-x:auto; overflow-y:hidden;width: 100%;" class="outline"></div>
                    </td>
                </tr>
            </tbody>
        </table>
        <table class="table-style-04" cellpadding="0" style="table-layout: fixed;width: 980px">
            <colgroup>
                <col width="50%" />
                <col width="50%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09032") %><%--원인--%>(${dqm.causeCodeName})</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "03433") %><%--개선대책--%></td>
                </tr>
                <tr>
                    <td class="border-bottom-none">
                        <div id="divView1" style="overflow-x:auto; overflow-y:hidden; width: 100%" class="outline"></div>
                    </td>
                    <td class="border-bottom-none">
                        <div id="divView2" style="overflow-x:auto; overflow-y:hidden; width: 100%" class="outline"></div>
                    </td>
                </tr>
            </tbody>
        </table>
        <table cellpadding="0" class="table-style-04" style="table-layout: fixed;width: 980px">
            <colgroup>
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
            </colgroup>
            <tbody>
                <tr>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09040") %><%--도면출도--%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09041") %><%--금형수정--%></td>
                    <td class="title">T/O</td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09042") %><%--신뢰성시험--%></td>
                    <td class="title"><%=messageService.getString("e3ps.message.ket_message", "09045") %><%--유효성검증 예정--%></td>
                </tr>
                <tr>
                    <td align="center" class="border-bottom-none">${dqm.drawingOutDate}</td>
                    <td align="center" class="border-bottom-none">${dqm.moldModifyDate}</td>
                    <td align="center" class="border-bottom-none">${dqm.toDate}</td>
                    <td align="center" class="border-bottom-none">${dqm.trustTestDate}</td>
                    <td align="center" class="border-bottom-none">${dqm.validationDate}</td>
                </tr>
            <tbody>
         </table>
         <table cellpadding="0" class="table-style-04 border-bottom-line" style="table-layout: fixed;width: 980px">
	         <tbody>
	            <c:if test="${secondaryFiles.size() eq 0 }">
		            <tr>
		                <td style="width: 10%" class="title">
		                      <%=messageService.getString("e3ps.message.ket_message", "02794") %><%--첨부--%>
		                </td>
		                <td colspan="2" align="center">
		                      <%=messageService.getString("e3ps.message.ket_message", "09075") %><%--첨부파일이 존재 하지 않습니다.--%>
		                </td>
		            </tr>
	            </c:if>
	            <c:if test="${secondaryFiles.size() != 0 }">
	               <c:forEach var="content" items="${secondaryFiles}" varStatus="i">
	                    <c:if test="${i.index eq 0 }">
	                        <tr>
	                            <td rowspan="${secondaryFiles.size()}" style="width: 10%" class="title"><%=messageService.getString("e3ps.message.ket_message", "02794") %><%--첨부--%></td>
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