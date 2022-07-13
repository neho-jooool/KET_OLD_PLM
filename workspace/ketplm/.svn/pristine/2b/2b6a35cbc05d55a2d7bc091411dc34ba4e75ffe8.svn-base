<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">
<script type="text/javascript" src="/plm/extcore/js/invest/invest.js"></script>
<script>
$(document).ready(function(){
	
	if(${isEdit}){
		SuggestUtil.bind('USER', 'workerName', 'workerOid');
		
		var strHTMLCode = document.forms[0].webEditor.value;
	    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
	    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
	    // 세번째 매개변수 => 이노디터 번호
	    fnSetEditorHTMLCode(strHTMLCode, false, 0);
	    
	    $("input[name=workerOid]").data("message", "담당자를 입력하세요.");
	}
	
    var tab = CommonUtil.tabs('tabs');
    
    $(".atag").css("color","#4398BC");
    
    $(".atag").hover(function(){
        $(".atag").css('text-decoration','underline');
    },
    function(){
    	$(".atag").css('text-decoration','none');
    });
    
    $('.tabContent').hide();
    $('.tabContent :first').show();
    
    $(".tabref").click(function(){
        $(".tabContent").hide();
        var ref = $(this).attr("href");
        $(ref).show();
    });
});
window.saveInvestTask = function(isComplete){
	
	
	var confirmMsg = "저장하시겠습니까?";
    var completeMsg = "저장되었습니다.";
    
    if(isComplete) {
        confirmMsg = "완료하시겠습니까?";
        completeMsg = "작업이 완료되었습니다.";
    }
	
	if(invest.needCheckAttribute() && confirm(confirmMsg)){
	
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		    
		var param = $("[name=uploadForm]").serializefiles();
		param.append("isComplete", isComplete);
	
		ajaxCallServer("/plm/ext/invest/saveInvestTask", param, function(data){
			
			alert(completeMsg);
			
			try{
                if(opener) opener.reload();
            }catch(e){}
            
			//location.href = "/plm/ext/invest/investTaskPopup?oid=" + data.oid;
            location.reload();
	    });
	}
}
</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${itDTO.oid}"/>
<input type="hidden" name="managerOid" id="managerOid" value="${itDTO.investMaster.managerName }"> 
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />세부 진행현황
        </div>
        <div id="tabs" style="margin:0 auto;">
        <ul>
            <li><a class="tabref" href="#INVESTTASK" rel="">세부진행항목</a></li>
            <li><a class="tabref" href="#INVESTMASTER" rel="">요청서</a></li>
        </ul>
        <div class="b-space5 float-r" style="text-align:right;position:relative;top:-30px;">
            <c:if test="${isEdit }">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveInvestTask(true);" class="btn_blue">
                        완료
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:saveInvestTask(false);" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            </c:if>
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div id="INVESTTASK" class="tabContent">
            <div class="sub-title-02 float-l b-space5">
	            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>세부진행항목 기본정보
	        </div>
	        <div class="b-space5" >
	            <table summary="" class="info" style="width:100%;">
	                <colgroup>
	                    <col width="150" />
	                    <col width="*" />
	                    <col width="150" />
	                    <col width="*" />
	                </colgroup>
	                <tbody>
	                    <tr>
	                        <td class="tdblueL">
	                            투자품의번호
	                        </td>
	                        <td class="tdwhiteL">
	                            <a href="javascript:openView('${itDTO.investMaster.oid }')" class="atag">${itDTO.investMaster.reqNo }</a>
	                        </td>
	                        <td class="tdblueL">
	                            상태<span class="red">*</span>
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investStateName }
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            투자품의명
	                        </td>
	                        <td colspan="3" class="tdwhiteL">
	                            ${itDTO.investMaster.reqName }
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            세부 진행항목
	                        </td>
	                        <td colspan="3" class="tdwhiteL">
	                            ${itDTO.subject }
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            요청자
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investMaster.managerName } / ${itDTO.investMaster.mDeptName }
	                        </td>
	                        <td class="tdblueL">
	                            완료 요청일자
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.requestDate }
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            담당자<span class="red">*</span>
	                        </td>
	                            <c:if test="${isEdit }">
	                                <td class="tdwhiteL">
	                                    <input type="text" name="workerName" class="WP70 txt_field" value="${itDTO.workerName }">
	                                    <input type="hidden" name="workerOid" value="${itDTO.workerOid }"> 
	                                    <a href="javascript:SearchUtil.selectOneUser('workerOid','workerName');">
	                                    <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
	                                    <a href="javascript:CommonUtil.deleteValue('workerOid','workerName');">
	                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	                                </td>
	                            </c:if>
	                            <c:if test="${!isEdit }">
	                               <td class="tdwhiteL">
	                                ${itDTO.workerName } / ${itDTO.workerDeptName }
	                                </td>
	                            </c:if>
	                        <td class="tdblueL">
	                            완료일자
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.completeDate }
	                        </td>
	                    </tr>
	                    <tr>
                            <td class="tdblueL">
                                참조자
                            </td>
                            <td class="tdwhiteL" colspan="3">
                                ${itDTO.memberNames }
                            </td>
                        </tr>
	                    <tr>
	                        <th class="tdblueL" >
	                            진행사항
	                        </td>
	                        <c:if test="${isEdit }">
	                        <td class="tdwhiteL"  colspan="3" id="enoediter" style="height:300px;">
	                            <!-- 이노디터 JS Include Start -->
	                            <script type="text/javascript">
	                                // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
	                                // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
	                                // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
	                                var g_arrSetEditorArea = new Array();
	                                g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
	                            </script>
	                            <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
	                            <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
	                            <script type="text/javascript">
	                                // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
	                                // Skin 재정의
	                                //g_nSkinNumber = 0;
	                                // 크기, 높이 재정의
	                                g_nEditorWidth = 650;
	                                g_nEditorHeight = 300;
	                            </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
	                            <textarea name="webEditor" rows="0" cols="0" style="display: none">${itDTO.webEditor}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
	                            <input type="hidden" name="hdnBackgroundColor" value="" /><input type="hidden" name="hdnBackgroundImage"
	                            value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
	                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
	                            <textarea name="webEditorText" rows="0" cols="0" style="display: none">${itDTO.webEditorText}</textarea> <!-- Editor Area Container : Start -->
	                            <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
	                        </td>
	                        </c:if>
	                        <c:if test="${!isEdit }">
	                        <td colspan="3" class="tdwhiteL">
	                            <div class="webEditor">${itDTO.webEditor}</div>
	                        </td>
	                        </c:if>
	                        
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            첨부파일
	                        </td>
	                        <td colspan="3" class="tdwhiteL">
	                            <div id="attachFileDiv"></div>
	                            <script>
                                var mode = "";
                                if(!${isEdit}){
                                    mode = "view";
                                }
                                $("#attachFileDiv").load("/plm/ext/common/attachFilesForm?oid=${itDTO.oid}&mode=" + mode);
                                </script>
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	        </div>
        </div>
        <div id="INVESTMASTER" class="tabContent">
            <div class="sub-title-02 float-l b-space5">
	            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>요청서 기본정보
	        </div>
	        <div class="b-space5" >
	            <table summary="" class="info">
	                <colgroup>
	                    <col width="150" />
	                    <col width="*" />
	                    <col width="150" />
	                    <col width="*" />
	                    <col width="150" />
	                    <col width="*" />
                    </colgroup>
	                <tbody>
	                    <tr>
	                        <td class="tdblueL">
	                            투자품의번호<span class="red">*</span>
	                        </td>
	                        <td class="tdwhiteL">${itDTO.investMaster.reqNo}</td>
	                        <td class="tdblueL">
	                            투자품의명<span class="red">*</span>
	                        </td>
	                        <td class="tdwhiteL" colspan="3">${itDTO.investMaster.reqName }</td>
                        </tr>
                        
	                    <tr>
	                        <td class="tdblueL">
	                            투자비 (금형/설비)
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.investExpense_1}</fmt:formatNumber>
	                        </td>
	                        <td class="tdblueL">
	                            투자비 (QDM/기타)
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.investExpense_2}</fmt:formatNumber>
	                        </td>
	                        <td class="tdblueL">
	                            투자비 합계
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.investExpense }</fmt:formatNumber>
	                        </td>
	                        <%-- <td class="tdblueL">
	                            접점고객
	                        </td>
	                        <td class="tdwhiteL" id="subContractorNames">
	                            ${imDTO.subContractorName }
	                        </td> --%>
	                    </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            회수비 (금형/설비)
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.collectExpense_1}</fmt:formatNumber>
	                        </td>
	                        <td class="tdblueL">
	                            회수비 (QDM/기타)
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.collectExpense_2}</fmt:formatNumber>
	                        </td>
	                        <td class="tdblueL">
	                            회수비 합계
	                        </td>
	                        <td class="tdwhiteR">
	                           <fmt:formatNumber>${itDTO.investMaster.collectExpense}</fmt:formatNumber>
	                        </td>
	                    </tr>
                        
	                    <tr>
	                        <td class="tdblueL">회수 예정일자<span class="red">*</span></td>
	                        <td class="tdwhiteL">${itDTO.investMaster.requestDate }
	                        </td>
	                        <td class="tdblueL">회수 완료일<span class="red">*</span>
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investMaster.completeDate }
	                        </td>
	                        <td class="tdblueL">진행상태</td>
	                        <td class="tdwhiteL">${itDTO.investMaster.investStateName }</td>
                        </tr>
	                    <tr>
	                        <td class="tdblueL">
	                            영업 담당자<span class="red">*</span>
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investMaster.managerName }
	                        </td>
	                        <td class="tdblueL">
	                            개발발의자
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investMaster.creatorName }
	                        </td>
	                        <td class="tdblueL">
	                            등록일
	                        </td>
	                        <td class="tdwhiteL">
	                            ${itDTO.investMaster.createDate }
	                        </td>
	                        </td>
                        </tr>
	                    <tr>
<!--                         <td class="tdblueL">
                            첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDivMaster"></div>
                            <script>
                            $("#attachFileDivMaster").load("/plm/ext/common/attachFilesForm?oid=${imDTO.oid}&mode=view");
                            </script>
                        </td> -->
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">
                            투자품의 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv1"></div>
                            <script>
                            $("#attachFileDiv1").load("/plm/ext/common/attachFilesForm?oid=${itDTO.investMaster.oid}&tid=1&mode=view");
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            회수증빙 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv2"></div>
                            <script>
                            $("#attachFileDiv2").load("/plm/ext/common/attachFilesForm?oid=${itDTO.investMaster.oid}&tid=2&mode=view");
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            일반 첨부파일
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            <div id="attachFileDiv3"></div>
                            <script>
                            $("#attachFileDiv3").load("/plm/ext/common/attachFilesForm?oid=${itDTO.investMaster.oid}&tid=3&mode=view");
                            </script>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">관련 프로젝트</td>
                        <td class="tdwhiteL" colspan="5">
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                           </table>
                           
                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                <table id="productProjectTable" width="100%" cellpadding="0" cellspacing="0">
                                        <colgroup>
                                            <col width="10%" />
                                            <col width="30%" />
                                            <col width="20%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                            <col width="10%" />
                                        </colgroup>
                                    <tr>
                                        <td class="tdgrayM">ProjectNo</td>
                                        <td class="tdgrayM">Proejct명</td>
                                        <td class="tdgrayM">접점고객</td>
                                        <td class="tdgrayM">영업</td>
                                        <td class="tdgrayM">영업부서</td>
                                        <td class="tdgrayM">프로젝트<br>완료일</td>
                                    </tr>
                                    <tbody id="productProjectTableBody">       
                                    <c:forEach items="${itDTO.investMaster.productProjectList}" var="projectList" varStatus="i">
                                    <tr>
                                        <td class="tdwhiteM"><a href="javascript:openProject('${projectList.pjtNo}');" class="atag">${projectList.pjtNo}</a></td>
                                        <td class="tdwhiteM">${projectList.pjtName}</td>
                                        <td class="tdwhiteM">${projectList.pjt_customer}</td>
                                        <td class="tdwhiteM">${projectList.salesName}</td>
                                        <td class="tdwhiteM">${projectList.salesDept}</td>
                                        <td class="tdwhiteM">${projectList.execEndDate}</td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr><td class="space5"></td></tr>
                            </table>
                         </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">
                            요청사항
                        </td>
                        <td colspan="5" class="tdwhiteL">
                            ${imDTO.bigoHtml }
                        </td>
                    </tr>
	                </tbody>
	            </table>
	        </div>
        </div>
        
        </div>
    </div>
</form>
</body>
