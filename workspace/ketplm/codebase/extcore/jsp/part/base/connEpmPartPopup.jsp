<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
<!--
$(document).ready(function(){
    $(document).attr('title','도면 부품 연계');
    //server paging
});
//-->
</script>

<%


%>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}

input[type="radio"] {width:13px; height:13px; margin:0 0 0px; padding:0; border: 1px solid #FFF; vertical-align:middle;}
</style>

<script type="text/javascript">

//UI 초기화
$(document).ready(function(){
    
    try{
    
    $('body').addClass('popup-background02 body')
        
    //------------ start suggest binding ------------//
    SuggestUtil.bind('PARTNO', 'partNo', 'partOid', 'partName');
    //------------ end suggest binding ------------//

    CommonUtil.singleSelect('referenceType',350);
    CommonUtil.singleSelect('requied',100);
    CommonUtil.singleSelect('ecad',100);

    }catch(e){
    	
    }
    
});

function connEpm2Part(){

    var form = document.forms[0];

    // validation check
    if(!CommonUtil.checkEsseOk()){
        return;
    }
    
    $.ajax({
        type: 'post',
        async: false,
        url: '/plm/ext/part/base/saveEpmPartPopup.do',
        data: $('[name=partForm]').serialize(),
        success: function (data) {
            if(data != 'Fail'){
                disabledAllBtn();
                alert('<%=messageService.getString("e3ps.message.ket_message", "02460")%><%--저장되었습니다--%>');
                try{
                    if(opener){
                    	opener.location.reload();
                    }
                }catch(e){alert(e.message);}
                self.close();
            }else{
                alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            }
           
            hideProcessing();
        },
        fail : function(){
            alert("<%=messageService.getString("e3ps.message.ket_message", "02461")%><%--저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다--%>");
            hideProcessing();
        }
    });
}
 
function closeWindow(){

   try {
       hideProcessing();
   } catch(e) {}
   
   try {
       opener.hideProcessing();
   } catch(e) {}
   
   window.close();
    
}

$(window).onload = function initOnLoad(){
    try{

    }catch(e){
        alert(e.message);
    }    
}

</script>

<div class="contents-wrap">
    
        <div class="popup-title"><img src="/plm/portal/images/icon_3.png" />도면 부품 연계</div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"></td>
            </tr>
            <tr>
                <td class="space2"></td>
            </tr>
        </table>
        
        <div class="b-space10" style="text-align:right">

        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:connEpm2Part();" class="btn_blue">도면부품연결</a></span><span class="pro-cell b-right"></span></span></span>
        <span class="in-block v-middle"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:closeWindow();" class="btn_blue">닫기</a></span><span class="pro-cell b-right"></span></span></span>

        </div>
        <div class="b-space30">
        
            <form name="partForm">
            <!-- 검색영역 collapse/expand -->
            <div id="collapseDiv" align="center"><%--<img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon">--%></div>
            <div style="display: none">지우지 마세요</div>
            <table id="mainTable" cellpadding="0" class="table-style-01" summary="">
                <colgroup>
                    <col width="18%" />
                    <col width="32%" />
                    <col width="18%" />
                    <col width="32%" />
                </colgroup>
                <tbody>
                    <tr>
                       <td class="title">부품 번호</td>
                       <input type="hidden" id="partOid" name="partOid" value="" esse="true" esseLabel="부품 번호" >
                        <td><input type="text" name="partNo" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo','pType=A');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partOid', 'partNo', 'partName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        </td>
                        <td class="title">부품명</td>
                        <td><input id="partName" name="partName" type="text" class="txt_field" style="width: 98%" value="${partName}" readonly="readonly"></td>
                        <script type="text/javascript">
                        function setPartNo(objArr){
                            var partOid = "";
                            var partNo = "";
                            var partName = "";
                            for ( var i = 0; i < objArr.length; i++ ) {
                                partOid = objArr[i][0];
                                partNo = objArr[i][1];
                                partName = objArr[i][2];
                            }
                            $('[name=partOid]').val(partOid);
                            $('[name=partNo]').val(partNo);
                            $('[name=partName]').val(partName);
                        }
                        </script>
                    </tr>
                    <tr>
                        <input type="hidden" name="epmOid" value="${epmOid}">
                        <td class="title">도면 번호 / Rev </td>
                        <td><a href="javascript:openView('${epmOid}');">${epmNo}&nbsp;/&nbsp;${epmVer}</a></td>
                        <td class="title">도면명</td>
                        <td>${epmName}</td>
                    </tr>
                    <tr>
                        <td class="title">Reference Type</td>
                        <td colspan="3">
                        <select id="referenceType" name="referenceType" referenceType"" style="width: 170px; display:block;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="Reference Type">
                        <option value="RELATED_MODEL" >제품도 3D, 금형 3D ( MODEL )</option>
                        <option value="RELATED_DRAWING" >제품도 2D, ECAD, 금형 2D ( DRAWING )</option>
                        <option value="APP_DRAWING" >승인도</option>
                        <option value="CU_DRAWING" >고객제출도</option>
                        <option value="REF_DRAWING" >제품참고도</option>
                        <option value="NONE" >CP도면</option>
                        </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">대표 부품여부</td>
                        <td colspan="3">
                        <select id="requied" name="requied" referenceType"" style="width: 170px; display:block;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="대표부품 여부">
                        <option value="1" >대표 부품</option>
                        <option value="0" >관련 부품</option>
                        </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">ECAD 여부</td>
                        <td colspan="3">
                        <select id="ecad" name="ecad" referenceType"" style="width: 170px; display:block;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="ECAD 유무">
                        <option value="1" >ECAD 임</option>
                        <option value="0" >ECAD 아님</option>
                        </select>
                        </td>
                    </tr>
                    
                    <%--
                    <tr>
                        <td class="title">부품Rev</td>
                        <td><input id="spPartRevision" name="spPartRevision" type="text" class="txt_field" style="width: 95%" value="" ></td>
                        <td class="title">도면Rev</td>
                        <td><input id="spEpmRevision" name="spEpmRevision" type="text" class="txt_field" style="width: 95%" value="" ></td>
                    </tr>
                    <tr>
                        <td class="title">부품상태</td>
                        <td><ket:select id="partStateCode" name="partStateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_PART_LC" multiple="multiple" otherHtml=" " /></td>
                        <td class="title">도면상태</td>
                        <td><ket:select id="epmStateCode" name="epmStateCode" className="fm_jmp" style="width: 170px;" lifeCycleState="KET_EPM_LC" multiple="multiple" otherHtml=" " /></td>
                    </tr>
                    
                    <tr>
                        <td class="title">부품개발단계</td>
                        <td>
                        <select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <option value="PC003">개발</option>
                        <option value="PC004">양산</option>
                        </select></td>
                        <td class="title">도면개발단계</td>
                        <td>
                        <select id="spPartDevLevel" name="spPartDevLevel" style="width: 170px; display: none;" onblur="fm_jmp" multiple="multiple" esse="true" esseLabel="개발단계">
                        <option value="PC003">개발</option>
                        <option value="PC004">양산</option>
                        </select></td>
                    </tr>
                     --%>
                </tbody>
            </table>
            </form>
        </div>
    </div>
