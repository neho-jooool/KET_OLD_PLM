<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/dqm/dqm.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	// 이노디터에서 작성된 내용을 전달
	var curState = "${curState}";
	if(curState != 'RAISEINWORK'){
	    parent.$('#deleteButtonTd').attr('style', 'display:none');
	}
	else{
		parent.$('#deleteButtonTd').attr('style', 'display:block');
	}
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

    //var tab = CommonUtil.tabs('tabs');
    //tab.tabs({enable: [2]});
    
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
<table style="table-layout: fixed;width: 1000px" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td background="/plm/portal/images/logo_popupbg.png">
                   <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="7"></td>
                       <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                       <!--  문서 상세조회  -->
                       <td class="font_01">품질문제 제기</td>
                     </tr>
                   </table>
                 </td>
                 <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
               </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0" width="100%">
	          <tr>
	            <td class="space5"></td>
	          </tr>
	        </table>    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left">
                        
                    </td>
                    <td align="right">
                        <table id="buttonTable" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
		                                           <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
		                                           <td class="btn_blue"
		                                               background="/plm/portal/images/btn_bg1.gif"><a
		                                               href="javascript:doPrint();"
		                                               class="btn_blue">인쇄</a></td>
		                                           <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>  
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:self.close();"
                                                       class="btn_blue">닫기</a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                               </tr>
                                            </table>
                                        </td>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                    
                </tr>
              </table>
              <div id="approvalLineDiv"></div>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class="space5"></td>
                  </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td  class="tab_btm2"></td>
                  </tr>
              </table>
              <form name="updateForm" method="post">
              <input type="hidden" name="oid" value="${dqm.oid}">
              <table id="viewMainTable" border="0" cellspacing="0" cellpadding="0" width="100%">
                <colgroup>
                    <col width="120">
                    <col width="37%">
                    <col width="120">
                    <col width="*">
                 </colgroup>
                 <tr>
                    <td class="tdblueL">품질문제 No</td>
                    <td class="tdwhiteL">${dqm.problemNo}</td>
                    <td class="tdblueL">상태</td>
                    <td class="tdwhiteL">${dqm.dqmStateName}</td>
                    <!-- 
                    <td class="tdblueM">작성</td>
                    <td class="tdblueM">승인</td>
                     -->
                </tr>
                <tr>
                    <td class="tdblueL">문제내용</td>
                    <td class="tdwhiteL" colspan="3">${dqm.problem}</td>
                    <!--
                    <td class="text-center-border">${dqm.raiseCreateUserName}</td>
                    <td class="text-center-border">${dqm.raiseApprovName}</td>
                     -->
                </tr>
                <tr>
                    <td class="tdblueL">Project No</td>
                    <td class="tdwhiteL">${dqm.pjtno}</td>
                    <td class="tdblueL">Project Name</td>
                    <td class="tdwhiteL">${dqm.pjtname}</td>
                    <!--
                    <td class="text-center-border">${dqm.raiseCreateStamp}</td>
                    <td class="text-center-border">${dqm.raiseApprovDate}</td>
                     -->
                </tr>
              </table>
              <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%">
                  <colgroup>
                    <col width="120">
                    <col width="37%">
                    <col width="120">
                    <col width="*">
                 </colgroup>
                  <tr>
                      <td class="tdblueL">고객사</td>
                      <td class="tdwhiteL">${dqm.customerName}</td>
                      <td class="tdblueL">차종</td>
                      <td class="tdwhiteL">${dqm.cartypeName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">불량구분</td>
                      <td class="tdwhiteL">${dqm.defectDivName}</td>
                      <td class="tdblueL">불량유형</td>
                      <td class="tdwhiteL">${dqm.defectTypeName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">긴급도</td>
                      <td class="tdwhiteL">${dqm.urgencyName}</td>
                      <td class="tdblueL">적용부위 1</td>
                      <td class="tdwhiteL">${dqm.applyArea1}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">적용부위 2</td>
                      <td class="tdwhiteL">${dqm.applyArea2}</td>
                      <td class="tdblueL">적용부위 3</td>
                      <td class="tdwhiteL">${dqm.applyArea3}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">관련부품</td>
                      <td class="tdwhiteL">${dqm.relatedPart}</td>
                      <td class="tdblueL">부품분류</td>
                      <td class="tdwhiteL">${dqm.partCategoryName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">시리즈</td>
                      <td class="tdwhiteL" colspan="3">${dqm.seriesName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">발생구분</td>
                      <td class="tdwhiteL">${dqm.occurDivName}</td>
                      <td class="tdblueL">발생단계</td>
                      <td class="tdwhiteL">${dqm.occurStepName}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">발생처</td>
                      <td class="tdwhiteL">${dqm.occurName}</td>
                      <td class="tdblueL">발생일</td>
                      <td class="tdwhiteL">${dqm.occurDate}</td>
                  </tr>
                  <tr>
                      <td class="tdblueL">발생장소</td>
                      <td class="tdwhiteL">${dqm.occurPlaceName}</td>
                      <td class="tdblueL">제기자</td>
                      <td class="tdwhiteL">${dqm.actionDeptName}</td>
                  </tr>
                  
                  <tr>
                      <td class="tdblueL">요청기한</td>
                      <td class="tdwhiteL">${dqm.requestDate}</td>
                      <td class="tdblueL">PM</td>
                      <td class="tdwhiteL">${dqm.pmUserName}</td>
                  </tr>
                  
                  <tr>
                      <td class="tdblueL">상세내용</td>
                      <td colspan="3" class="tdwhiteL">
                           <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                              <div id="divView" style="width: 100%;" class="outline"></div>
                              <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                      </td>
                  </tr>
                  <tr>
                      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                      <td colspan="3" class="tdwhiteL">
                          <!-- 
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                  <td class="space5"></td>
                              </tr>
                          </table>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                  <td>&nbsp;</td>
                                  <td align="right"><table border="0" cellspacing="0"
                                          cellpadding="0">
                                          <tr>
                                              <td><table border="0" cellspacing="0" cellpadding="0">
                                                      <tr>
                                                          <td width="10"><img
                                                              src="/plm/portal/images/btn_1.gif"></td>
                                                          <td class="btn_blue"
                                                              background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:selDownload();" class="btn_blue">다운로드</a></td>
                                                          <td width="10"><img
                                                              src="/plm/portal/images/btn_2.gif"></td>
                                                      </tr>
                                                  </table></td>
                                          </tr>
                                      </table></td>
                              </tr>
                          </table>
                          -->
                          <table border="0" cellspacing="0" cellpadding="0" width="100%">
                              <tr>
                                  <td class="space5"></td>
                              </tr>
                          </table>
                          <div id="div_scroll3"
                              style="overflow-x: hidden; overflow-y: auto;"
                              class="table_border">
                              <table width="100%" cellpadding="0" cellspacing="0">
                                  <tr class="headerLock3">
                                      <td>
                                          <table border="0" cellpadding="0" cellspacing="0"
                                              width="100%" style="table-layout: fixed">
                                              <tr>
                                                  <!-- 
                                                  <td width="40" class="tdgrayM"><input type="checkbox"
                                                      name="chkFileAll" id="checkbox14"
                                                      onclick="javascript:allCheck( 'fileSelect', this.checked);"></td>
                                                  -->
                                                  <td class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                              </tr>
                                          </table>
                                      </td>
                                  </tr>
                                  <table width="100%" cellpadding="0" cellspacing="0"
                                      style="table-layout: fixed">
                                      <tbody id="fileTable">
                                      <c:forEach var="content" items="${secondaryFiles}">
                                                  <tr>
                                                      <!-- 
                                                      <td width="40" class="tdwhiteM">
                                                          <input name='fileSelect' type='checkbox' class='chkbox'>
                                                      </td>
                                                      -->
                                                      <td class="tdwhiteL">
                                                          <input name='secondaryFileOids' type='hidden' value='${content.contentoid}'>
                                                          <a target='download' href='${content.downURLStr}'>${content.iconURLStr}</a>&nbsp;
                                                          <a href='${content.downURLStr}' target='_blank'>${content.name}</a>&nbsp;(&nbsp;${content.fileSizeKB}&nbsp;)
                                                      </td>
                                                  </tr>
                                                  </c:forEach>
                                      </tbody>
                                  </table>
                                  </div>
                                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                      <tr>
                                          <td class="space5"></td>
                                      </tr>
                                  </table>
                              </table>
                      </td>
                  </tr>  
              </table>
          </form>
          <form id="selDownloadForm" name="selDownloadForm" method="post">
           </form>
            </td>
        </tr>
    </table>