<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript" src="/plm/extcore/js/sample/sample.js"></script>
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="780" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">Sample 등록</td>
                                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img
                                    src="/plm/portal/images/icon_navi.gif">Sample <img src="/plm/portal/images/icon_navi.gif">Sample
                                    등록</td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table width="780" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Sample.create();" class="btn_blue">저장</a></td>
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
                                                <!-- 목록  --> <a href="javascript:Sample.goList();" class="btn_blue">목록</a>
                                            </td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <form name="SampleCreateForm" method="post">
                <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                        <td class="tdblueL">번호<span class="red">*</span></td>
                        <td width="*" class="tdwhiteL"><input type="text" name="num" class="txt_field" style="width: 100%"></td>
                        <td class="tdblueL">제목</td>
                        <td width="*" class="tdwhiteL"><input type="text" name="title" class="txt_field" style="width: 100%"></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">주 첨부파일<span class="red">*</span></td>
                        <td colspan="3" class="tdwhiteL">
                            <input name="primaryFile" type="file" class="txt_fieldRO" style="width: 100%"></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">첨부파일</td>
                        <td colspan="3" width="650" class="tdwhiteL">
                            <table width="645" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                            <table width="645" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:Sample.insertFileLine()" class="btn_blue">추가</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td width="5">&nbsp;</td>
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                href="javascript:Sample.deleteFileLine()" class="btn_blue">삭제</a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td align="right">&nbsp;</td>
                                </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="645">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                            <table border="0" cellpadding="0" cellspacing="0" width="645">
                                <tbody id="iFileTable" />
                            </table>
                            <table width="645" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                 </table>
                 <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                        <td colspan="4">
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
    							g_nEditorWidth = 780;
    							g_nEditorHeight = 400;
    						</script> <script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script> <!-- 이노디터 JS Include End --> <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                            <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
                            <input type="hidden" name="hdnBackgroundColor" value="" /> <input type="hidden" name="hdnBackgroundImage"
                            value="" /> <input type="hidden" name="hdnBackgroundRepeat" value="" /> <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
                            <!-- 이노디터에서 작성된 내용을 보낼 Form End --> <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                            <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> <!-- Editor Area Container : Start -->
                            <div id="EDITOR_AREA_CONTAINER"></div> <!-- Editor Area Container : End -->
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
