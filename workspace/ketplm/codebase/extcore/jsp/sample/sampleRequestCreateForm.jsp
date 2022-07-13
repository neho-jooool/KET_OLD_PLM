<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@page import="e3ps.common.util.CommonUtil"%>
    
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
    
<%
String carTypeRequire = "false";
if ( CommonUtil.isMember("자동차사업부") || CommonUtil.isKETSUser() ) {
    carTypeRequire = "true";
}
%>

<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript"
    src="/plm/extcore/js/sample/sampleRequest.js"></script>
    
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<script>
var indexCnt = 0;
//Jquery
$(document).ready(function(){
	
    CalendarUtil.dateInputFormat('requestDate');
    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    

    var onload_carTypeRequire = '<%=carTypeRequire%>';

    if(onload_carTypeRequire == 'true'){
        $('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745')+"<span class='red'>*</span>");
        sampleRequest.setCarTypeRequire(true);
    }
    else{
        $('[id=cartypeHeader]').html(LocaleUtil.getMessage('02745'));
        sampleRequest.setCarTypeRequire(false);
    }
});


function onlyNumberFunc(e) {
	//숫자만 입력 0~9
    var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(/[^0-9]/);
    if (verified) {e.preventDefault();}
}

    //첨부파일 관련 스크립트 시작
function insertFileLine() {
    var innerRow = fileTable.insertRow();
    innerRow.height = "27";
    var innerCell;

    var filePath = "filePath";
    var filehtml = "";

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
                  + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteM0";
            innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
        }
    }
}
    
    
    function selectMultiSubContractor(returnValue){
        var nodeIdStr='', nodeNameStr='';
        for(var i=0; i < returnValue.length; i++){
                if(i == returnValue.length - 1){
                        nodeIdStr += returnValue[i][0];
                        nodeNameStr += returnValue[i][2];
                }else{
                        nodeIdStr += returnValue[i][0]+',';     
                        nodeNameStr += returnValue[i][2]+',';
                }
        }
        $('[name=customerCode]').val(nodeIdStr);
        $('[name=customerName]').val(nodeNameStr);
    }
    
    function addPartAfterFunc( rtnArr )
    {
    	for(var i = 0; i < rtnArr.length ; i++){
            var arr = new Array();
            arr = rtnArr[i];
            addPart(arr);
        }   
    	/*
        var trArr;
        if(objArr.length == 0) {
            return;
        }
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];
            $('[name=relatedPartOid]').val(trArr[0]);
            $('[name=relatedPart]').val(trArr[1]);
        }
        */
    }
    
    function addPart(arr){
        var paramOid = arr[0];
        
        var rtnFlag = false;
        
        $.each($('[name=partOidArr]'), function(i, val){
            if(val.value == paramOid) {
                alert(LocaleUtil.getMessage('09104',arr[1]));//"선택한 부품은 이미 추가된 부품입니다."
                rtnFlag = true;
                return;
            }
        });
        
        if(rtnFlag){
            return;
        }
        
        var innerRow = partTable.insertRow();
        
        indexCnt = indexCnt + 1; //인덱스 증가
        
        innerRow.height = "27";
        var innerCell;
        /**
         * 부품 검색
         * 
         * type(부품분류) : 
            'A' 모두 
            ,'P' 제품
            ,'D' DIE NO 선택
            ,'M' 금형부품
         * 
         * fncall :
         *  후처리 함수명(objectArray 로 리턴함)
             subArr[0] = "wt.part.WTPart:" + R[i].Oid;//oid
             subArr[1] = R[i].PartNumber;//number
             subArr[2] = R[i].PartName;//name
             subArr[3] = R[i].PartVersion;//version
             subArr[4] = R[i].PartType;//type
             subArr[5] = R[i].DieNo;//dieno
             subArr[6] = R[i].DieName;
             subArr[7] = R[i].DieCnt;
             subArr[8] = R[i].OidMaster;//wtpartmaster oid
         */
         var ajaxData = "";
         $.ajax({
             type       : "POST",
             url        : "/plm/ext/sample/projectInfo.do",
             data       : "partOid="+arr[0],
             dataType   : "json",
             async : false,
             success    : function(data){
            	 ajaxData = data; 
             },
             error    : function(xhr, status, error){
                          alert(xhr+"  "+status+"  "+error);
                          
             }
         });
         
                 
        for ( var k = 0; k < 9; k++) {
            innerCell = innerRow.insertCell();
            innerCell.height = "23";

            if (k == 0) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>";
            } else if (k == 1) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = arr[1];
            } else if (k == 2) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = arr[2];
            } else if (k == 3) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = arr[3];
            } else if (k == 4) {
                innerCell.className = "tdwhiteM";
                var selectTagContext = "<select id='pjtSelect"+indexCnt+"' name='pjtSelect' style='width:100px' onchange=\"changePjtSelect('"+indexCnt+"', this.options[this.selectedIndex].value)\">";
                selectTagContext += "<option value=''>---"+LocaleUtil.getMessage('01802')+"---</option>";
                for(var l = 0; l < ajaxData.length; l++){
                	selectTagContext += "<option value="+ajaxData[l].pjtOid+">"+ajaxData[l].pjtno+"</option>";
                }
                selectTagContext += "</select>";
                
                if(ajaxData.length > 0){
                	selectTagContext += "<input type='hidden' id='pjtoid"+indexCnt+"' name='pjtoid' value=''>";
                }
                else{
                	selectTagContext += "<input type='hidden' id='pjtoid"+indexCnt+"' name='pjtoid' value=''>";
                }
                
                innerCell.innerHTML = selectTagContext;
                
            } else if (k == 5) {
                innerCell.className = "tdwhiteM";
                
                if(ajaxData.length > 0){
                	innerCell.innerHTML = "<input type='text' id='partPmUserNameArr"+indexCnt+"' name='partPmUserNameArr' value='"+ajaxData[0].pjtPmName+"' class='txt_field' style='width: 70%'>"
	                    +"<input type='hidden' id='partPmUserOidArr"+indexCnt+"' name='partPmUserOidArr' value='"+ajaxData[0].pmUserOid+"'>"
	                    +"<a href=\"javascript:SearchUtil.selectOneUserById('partPmUserOidArr"+indexCnt+"','partPmUserNameArr"+indexCnt+"');\">"
	                    +"<img src='/plm/portal/images/icon_user.gif' border='0'></a>"
	                    +"<a href=\"javascript:CommonUtil.deleteValueById('partPmUserOidArr"+indexCnt+"','partPmUserNameArr"+indexCnt+"');\">"
	                    +"<img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
                }
                else{
                	innerCell.innerHTML = "<input type='text' id='partPmUserNameArr"+indexCnt+"' name='partPmUserNameArr' value='' class='txt_field' style='width: 70%'>"
	                    +"<input type='hidden' id='partPmUserOidArr"+indexCnt+"' name='partPmUserOidArr' value=''>"
	                    +"<a href=\"javascript:SearchUtil.selectOneUserById('partPmUserOidArr"+indexCnt+"','partPmUserNameArr"+indexCnt+"');\">"
	                    +"<img src='/plm/portal/images/icon_user.gif' border='0'></a>"
	                    +"<a href=\"javascript:CommonUtil.deleteValueById('partPmUserOidArr"+indexCnt+"','partPmUserNameArr"+indexCnt+"');\">"
	                    +"<img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
                }
                
                
            } else if (k == 6) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<input name='partRequestCountArr' type='text' class='txt_field' style='width:70%' value=''  onkeypress='onlyNumberFunc(event)'/><input name='partOidArr' type='hidden' value='"+arr[0]+"'/>EA";
            } 
        
            SuggestUtil.bind('USER', 'partPmUserNameArr', 'partPmUserOidArr');
            //<input name='drawingDisEpmArray' type='hidden' value='"+arr[0]+"'/>
            //CommonUtil.singleSelect('pjtoid',100);
            
        }
    }
    
    function changePjtSelect(idex, chgValue){
    	if(chgValue == ""){
    		$('#pjtoid'+idex).val(chgValue);
            return;
        }

    	
    	$('#pjtoid'+idex).val(chgValue);

    	var ajaxData = "";
        $.ajax({
            type       : "POST",
            url        : "/plm/ext/sample/projectPMUserInfo.do",
            data       : "pjtoid="+chgValue,
            dataType   : "json",
            async : false,
            success    : function(data){
                ajaxData = data; 
                
                $('#partPmUserOidArr'+idex).val(ajaxData.pmUserOid);
                $('#partPmUserNameArr'+idex).val(ajaxData.pjtPmName);
            },
            error    : function(xhr, status, error){
                         alert(xhr+"  "+status+"  "+error);
                         
            }
        });
    }
    /*
    function checktest(){
    	$("[name=pjtoid]").each( function() {
            alert($(this).val());
        });
    }
    */
    
</script>
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
	                                            <td>
	                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                       <td class="btn_blue"
                                                            background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:sampleRequest.create();"
                                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                                                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                    </table>
                                                </td>
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
                    <form name="createForm" method="post" enctype="multipart/form-data">         
                        <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                            <colgroup>
                                <col width="120">
                                <col width="*">
                                <col width="120">
                                <col width="*">
                            </colgroup>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
                                <td class="tdwhiteL0" colspan="3"><input type="text" name="requestTitle" class="txt_field" style="width:100%" value=""></td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%><span class="red">*</span></td>
                                <td class="tdwhiteL0">
                                    <input type="text" name="customerName" class="txt_fieldRO" style="width: 70%" readonly>
                                    <input type="hidden" name="customerCode">
                                    <a href="javascript:SearchUtil.selectMultiSubContractor('selectMultiSubContractor');">
                                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                    
                                </td>
                                <td class="tdblueL" id="cartypeHeader"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%><span class="red">*</span></td>
                                <td class="tdwhiteL0">
                                    <input type="text" name="carTypeName" class="txt_field" style="width: 70%"> 
                                    <input type="hidden" name="carTypeCode">
                                    <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
                                    <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                                    <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09080") %><%--고객담당자--%></td>
                                <td class="tdwhiteL0"><input type="text" name="customerContractor" class="txt_field" style="width:100%" value=""></td>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "08102") %><%--요청기한--%><span class="red">*</span></td>
                                <td class="tdwhiteL0">
                                    <input type="text" name="requestDate" class="txt_field" style="width: 80px">
                                    <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('requestDate');" style="cursor: hand;">
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09101") %><%--요청부품--%><span class="red">*</span></td>
                                <td class="tdwhiteL0" colspan="3">
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                       <tr>
                                           <td class="space5"></td>
                                       </tr>
                                   </table>
                                   <div id="div_scroll3"
                                       style="overflow-x: hidden; overflow-y: auto;"
                                       class="table_border">
                                       <table id="partTable" width="100%" cellpadding="0" cellspacing="0">
                                           <tbody id="partTableBody">
                                               <colgroup>
                                                 <col width="30"/>
                                                 <col width="130"/>
                                                 <col width="*"/>
                                                 <col width="30"/>
                                                 <col width="130"/>
                                                 <col width="120"/>
                                                 <col width="90"/>
                                              </colgroup>
                                              <tr>
                                                  <td class="tdgrayM"><a href="#" onclick="javascript:showProcessing();SearchUtil.selectPart('addPartAfterFunc','pType=P');"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03021") %><%--품명--%></td>
                                                  <td class="tdgrayM">Rev</td>
                                                  <td class="tdgrayM">Project No</td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "04104") %><%--담당자--%>(PM)<span class="red">*</span></td>
                                                  <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "09084") %><%--요청수량--%><span class="red">*</span></td>
                                             </tr>
                                         </tbody>
                                       </table>
                                   </div>
                                   <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                       <tr>
                                           <td class="space5"></td>
                                       </tr>
                                   </table>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09079") %><%--용도--%><span class="red">*</span></td>
                                <td class="tdwhiteL0" colspan="3"><input type="text" name="purpose" class="txt_field" style="width:100%" value=""></td>
                            </tr>
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "09105") %><%--요청사유--%><span class="red">*</span></td>
                                <td colspan="3" id="enoediter">
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
                                        g_nEditorWidth = $("#enoediter").width();
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
                            <tr>
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                                <td colspan="3" class="tdwhiteL0">
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                            <td class="space5"></td>
                                        </tr>
                                    </table>
                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                        <tr>
                                            <td class="space5"></td>
                                        </tr>
                                    </table>
                                    <div id="div_scroll3"
                                        style="overflow-x: hidden; overflow-y: auto;"
                                        class="table_border">
                                        <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                            <tr class="headerLock3">
                                                <td>
                                                    <table border="0" cellpadding="0" cellspacing="0"
                                                        width="100%" style="table-layout: fixed">
                                                        <tr>
                                                            <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                            <td width="*" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <table width="100%" cellpadding="0" cellspacing="0"
                                                style="table-layout: fixed">
                                                <col width="20">
                                                <col width="*">
                                                <tbody id="fileTable">
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
                    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
                 </td>
            </tr>
        </table>