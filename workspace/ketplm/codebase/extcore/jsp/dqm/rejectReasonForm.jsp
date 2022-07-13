<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script language="javascript">

function saveReason(){
    $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
    $('[name=webEditorText]').val(fnGetEditorText(0));
	$.ajax({
	       type       : "POST",
	       url        : "/plm/ext/dqm/saveReason.do",
	       data       : $("#rejectForm").serialize(),
	       dataType   : "json",
	       success    : function(data){
	    	   window.returnValue = data;
	    	   window.close();  	   
	       },
	       error    : function(xhr, status, error){
	                    alert(xhr+"  "+status+"  "+error);
	                    
	       }
	   });
}
</script>
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "09076") %><%--기각 사유--%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table> 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	    <td valign="top">    
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
                    <td class="space5"></td>
                </tr>
	            <tr>
	                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
	                <td align="right">
	                    <table border="0" cellspacing="0" cellpadding="0">
	                        <tr>
	                            <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue"
                                                background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:saveReason();"
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                                                class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
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
	        <table id="rejectTable" width="100%" border="0" cellspacing="0" cellpadding="0">
              <form id="rejectForm" name="reasonForm">
                <input type="hidden" name="oid" value="${oid}"> 
                <tr>
                    <td>
                       <!-- 이노디터 JS Include Start --> <script type="text/javascript">
                           // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
                           // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
                           // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
                           var g_arrSetEditorArea = new Array();
                           g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
                       </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script> <script type="text/javascript"
                           src="/plm/portal/innoditor_u/js/customize_ui.js"></script> <script type="text/javascript">
                           // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
                           // Skin 재정의
                           //g_nSkinNumber = 0;
                           // 크기, 높이 재정의
                           g_nEditorWidth = $("#rejectTable").width();
                           g_nEditorHeight = 300;
                       </script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                       <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
                       <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
                       value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
                       <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                       <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
                       <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
                   </td>
                </tr>
              </form>
            </table>
        </td>
    </tr>
</table>
