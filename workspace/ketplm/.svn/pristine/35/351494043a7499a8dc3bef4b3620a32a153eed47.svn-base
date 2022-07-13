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
<script type="text/javascript">
$(document).ready(function(){
	opener.scrollControl();
	window.focus();
	
    var propelStrHTMLCode = document.forms[0].propelWebEditor.value;
    
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(propelStrHTMLCode, false, 0);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor[0].value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage[0].value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat[0].value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var problemStrHTMLCode = document.forms[0].problemWebEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(problemStrHTMLCode, false, 1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor[1].value, 1);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage[1].value, 1);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat[1].value, 1);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var planStrHTMLCode = document.forms[0].planWebEditor.value;
    // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
    // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
    // 세번째 매개변수 => 이노디터 번호
    fnSetEditorHTMLCode(planStrHTMLCode, false, 2);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor[2].value, 2);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage[2].value, 2);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat[2].value, 2);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
})

function cancel(){
	try{
		window.close();
	}catch(e){
	}
}

function contentsSet2Parent(){
	
	var planCheck = ('${sales.planCheck_1}');
    
    //진행현황
    var propelwebEditor = fnGetEditorHTMLCode(false, 0);
    var propelwebEditorText = fnGetEditorText(0);
    
    //문제점
    var problemWebEditor = fnGetEditorHTMLCode(false, 1);
    var problemWebEditorText = fnGetEditorText(1);
    
    //해결방안
    var planWebEditor = fnGetEditorHTMLCode(false, 2);
    var planWebEditorText = fnGetEditorText(2);
    
    var editor0 = fnGetEditorText(0);
    editor0 = opener.sales.trim(propelwebEditorText);
    
    var editor1 = fnGetEditorText(1);
    editor1 = opener.sales.trim(problemWebEditorText);
    
    var editor2 = fnGetEditorText(2);
    editor2 = opener.sales.trim(planWebEditorText);
    
    if(editor0 == ''){
        alert("진행현황을 입력하십시오");
        return;
    }
    
    if((planCheck == 'red') && (editor1 == '' || editor2 == '')){
    	alert("진행상황이 Red 일 때 문제점 및 해결방안은 입력되어야합니다.");
        return;
    }
    
/*     if($('[name="webEditor"]').val() == "<br />\n" || $('[name="webEditor"]').val() == null){
        alert(LocaleUtil.getMessage('09007'));//"검토의견을 입력하세요.");
        return;
    } */

	var arr = new Array();
	arr[0] = propelwebEditor;
	arr[1] = propelwebEditorText;
	
	arr[2] = problemWebEditor;
    arr[3] = problemWebEditorText;
    
    arr[4] = planWebEditor;
    arr[5] = planWebEditorText;
    arr[6] = ('${sales.idx}');
	opener.setWebEditor(arr);
/* 	try {
		opener.hideProcessing();
    } catch(e) {} */
    
    window.close();
}

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03">${sales.stepName}</td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <!-- <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:dqm.actionDelete();" class="btn_blue">삭제</a></td>
                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="5">&nbsp;</td> -->
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
	                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
	                                                            <a href="javascript:contentsSet2Parent();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a>
	                                                        </td>
                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="5">&nbsp;</td>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:cancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
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
                    <form name="updateForm" method="post" enctype="multipart/form-data">
                       <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                           <tr>
                               <td class="tdblueL">진행현황</td>
                                                      
                               <td  class="tdblueL">문제점</td>
                               <td  class="tdblueL">해결방안</td>
                               </td>
                           </tr>
                           <tr>
                               <td>
                                   <!-- 이노디터 JS Include Start --> <script type="text/javascript">
                                       // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
                                       // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
                                       // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
                                       var g_arrSetEditorArea = new Array();
                                       g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
                                       src="/plm/portal/innoditor_u/js/customize_ui_half.js?ver=0.1"></script> <script type="text/javascript">
                                       // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
                                       // Skin 재정의
                                       //g_nSkinNumber = 0;
                                       // 크기, 높이 재정의
                                       $("#viewTable").width() / 3
                                       g_nEditorHeight = 400;
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                                   <textarea name="propelWebEditor" rows="0" cols="0" style="display: none">${sales.propelwebEditor_1}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
                                   <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
                                   value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
                                   <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                                   <textarea name="propelWebEditorText" rows="0" cols="0" style="display: none">${sales.propelwebEditorText_1}</textarea> <!-- Editor Area Container : Start -->
                                   <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
                               </td>
                               <td>
                                   <!-- 이노디터 JS Include Start --> <script type="text/javascript">
                                       // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
                                       // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
                                       // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
                                       g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER2";// 이노디터를 위치시킬 영역의 ID값 설정
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
                                       src="/plm/portal/innoditor_u/js/customize_ui_half.js?ver=0.1"></script> <script type="text/javascript">
                                       // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
                                       // Skin 재정의
                                       //g_nSkinNumber = 0;
                                       // 크기, 높이 재정의
                                       $("#viewTable").width() / 3
                                       g_nEditorHeight = 400;
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                                   <textarea name="problemWebEditor" rows="0" cols="0" style="display: none">${sales.problemwebEditor_1}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
                                   <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
                                   value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
                                   <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                                   <textarea name="problemWebEditorText" rows="0" cols="0" style="display: none">${sales.problemwebEditorText_1}</textarea> <!-- Editor Area Container : Start -->
                                   <div id="EDITOR_AREA_CONTAINER2"></div> <!-- Editor Area Container : End -->
                               </td>
                               <td>
                                   <!-- 이노디터 JS Include Start --> <script type="text/javascript">
                                       // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
                                       // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
                                       // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
                                       //var g_arrSetEditorArea = new Array();
                                       g_arrSetEditorArea[2] = "EDITOR_AREA_CONTAINER3";// 이노디터를 위치시킬 영역의 ID값 설정
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
                                       src="/plm/portal/innoditor_u/js/customize_ui_half.js?ver=0.1"></script> <script type="text/javascript">
                                       // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
                                       // Skin 재정의
                                       //g_nSkinNumber = 0;
                                       // 크기, 높이 재정의
                                       //g_nEditorWidth = 500;
                                       //alert($("#viewTable").width() / 3 - 8);
                                       //alert($("#viewTable").width() / 3)
                                       g_nEditorWidth = $("#viewTable").width() / 3;
                                       g_nEditorHeight = 400;
                                   </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                                   <textarea name="planWebEditor" rows="0" cols="0" style="display: none">${sales.planwebEditor_1}</textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
                                   <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
                                   value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
                                   <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                                   <textarea name="planWebEditorText" rows="0" cols="0" style="display: none">${sales.planwebEditorText_1}</textarea> <!-- Editor Area Container : Start -->
                                   <div id="EDITOR_AREA_CONTAINER3"></div> <!-- Editor Area Container : End -->
                               </td>
                           </tr>
                      </table>
                   <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
           </td>
        </tr>
   </table>