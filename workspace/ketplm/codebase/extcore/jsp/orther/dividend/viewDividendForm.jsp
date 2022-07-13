<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<script type="text/javascript">
$(document).ready(function() {
    document.title = '새마을 배당금';
    
    $("input:radio[name='divi_yn']:radio[value='${dividend.divi_yn}']").prop("checked",true);
    $("input:radio[name='prin_yn']:radio[value='${dividend.prin_yn}']").prop("checked",true);
    
    
    $('[name=afr_mm_paym_amt]').number( true );
    $('[name=prin_amt]').number( true );
    
    $('#td_divi_amt').number( true );
    
    $('#td_paym_sum').number( true );
    $('#td_mm_paym_amt').number( true );
});
</script>
<script>
function numberTrunc(amt){//만원단위 절사(버림)
    amt = Math.floor(amt/10000) * 10000
    return amt;
}

function save(){
    
    var check_divi_yn_val = $("input[name=divi_yn]:checked").val();
    
    if ( typeof check_divi_yn_val == "undefined" || check_divi_yn_val == null ) {
        alert("연배당금 적치/회수 여부를 선택하세요.");
        return;
    }
    
    var check_prin_yn_val = $("input[name=prin_yn]:checked").val();
    
    if ( typeof check_prin_yn_val == "undefined" || check_prin_yn_val == null ) {
        alert("원금회수 적치/회수 여부를 선택하세요.");
        return;
    }
    
    var prin_amt_val = $('[name=prin_amt]').val(); //원금회수금액
    
    
    if(check_prin_yn_val == "N"){
        if ( typeof prin_amt_val == "undefined" || prin_amt_val == null || prin_amt_val == '' ) {
            alert("원금회수금액을 입력하세요.");
            $('[name=prin_amt]').focus();
            return;
        }else{
            prin_amt_val = prin_amt_val.replace(/,/gi,"");
            if(parseInt(prin_amt_val) < 10000 ){
                alert("원금회수금액은 10,000원 보다 큰 숫자를 입력하세요.");
                $('[name=prin_amt]').focus();
                return;
            }
            
            //prin_amt_val = Math.floor(prin_amt_val/10000) * 10000
            
            
            var paym_sum = '${dividend.paym_sum}';
            
            paym_sum = paym_sum.replace(/,/gi,"");
/*             alert(parseInt(prin_amt_val));
            alert(parseInt(paym_sum)); */
            
            if(parseInt(prin_amt_val) > parseInt(paym_sum)){
                alert("원금회수금액은 출자누계액을 초과할 수 없습니다.");
                return;
            }
            
            
            //$('[name=prin_amt]').val(prin_amt_val);
        }
        
    }
    
    if(check_prin_yn_val == "Y"){
        $('[name=prin_amt]').val(0);
    }
    
    var afr_mm_paym_amt_val = $('[name=afr_mm_paym_amt]').val();
    
    if ( typeof afr_mm_paym_amt_val == "undefined" || afr_mm_paym_amt_val == null || afr_mm_paym_amt_val == '' ) {
        alert("변경출자금을 입력하세요.");
        $('[name=afr_mm_paym_amt]').focus();
        return;
    }else{
        afr_mm_paym_amt_val = afr_mm_paym_amt_val.replace(/,/gi,"");
        
        if(parseInt(afr_mm_paym_amt_val) < 1000 ){
            alert("변경출자금은 1,000원 보다 큰 숫자를 입력하세요.");
            return;
        }
/*      afr_mm_paym_amt_val = Math.floor(afr_mm_paym_amt_val/10000) * 10000;
        
        
        
        $('[name=afr_mm_paym_amt]').val(afr_mm_paym_amt_val);
        return; */
        
    }
    
    if(confirm("저장하시겠습니까?")){
        
        $('[name=check_divi_yn]').val($("input[name=divi_yn]:checked").val());
        $('[name=check_prin_yn]').val($("input[name=prin_yn]:checked").val());
        
        showProcessing();
        
        $.ajax({
                method: "post",
                type: "json",
                data : $('[name=dividendForm]').serialize(),
                async: false,
                cache: false,
                url: '/plm/ext/orther/dividend/dividendSave.do',
                success: function (data) {
                    hideProcessing();
                    try{
                        if(data == 'S'){
                            alert("저장되었습니다.");
                        }else if(data == 'Fail'){
                            alert("실패하였습니다.");
                        }else{
                            alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기  바랍니다.");
                        }
                        
                    }catch(e){alert(e.message); ret = "E"; }
                },
                fail : function(){
                    hideProcessing();
                    alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
                    ret = "E";
                }
        });
    }
        
}

function toggleCheck() {
  location.href = "http://year.ket.com/year_tax/year/year_expire_set_cookie.asp";
}
</script>
<form name="dividendForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="saemal_acct_no" value='${dividend.saemal_acct_no}'>
<input type="hidden" name="check_divi_yn" value=''>
<input type="hidden" name="check_prin_yn" value=''>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
       <td class="space10"></td>
    </tr>
</table>

<div class="sub-title-02 b-space15 clear">
    <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>새마을 배당금
</div>
<div class="float-r" style="text-align: right">
    <span class="in-block v-middle">
        <span class="pro-table">
        <span class="pro-cell b-left"></span>
        <span class="pro-cell b-center"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
        <span class="pro-cell b-right"></span>
        </span>
    </span>
    <span class="in-block v-middle">
        <span class="pro-table">
        <span class="pro-cell b-left"></span>
        <span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></span>
        <span class="pro-cell b-right"></span>
        </span>
    </span>
</div>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
       <td class="space10"></td>
    </tr>
</table>
<div class="b-space30">
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
    </table>
    <table id="dividendTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;" border="1">
        <colgroup>
        <col width="4%" /> <!-- 년도 -->
        <col width="8%" /> <!-- 사번 -->
        <col width="7%" /> <!-- 성명 -->
        <col width="5%"/>  <!-- 연배당율 -->
        <col width="5%"  />  <!-- 적치 -->
        <col width="5%"  />  <!-- 회수 -->
        <col width="8%"  />  <!-- 배당금 -->
        <col width="5%"  /> <!-- 적치 -->
        <col width="5%"  /> <!-- 회수 -->
        <col width="10%" />  <!-- 출자누계액 -->
        <col width="10%" />  <!-- 원금회수금액 -->
        <col width="10%" />  <!-- 현출자금 -->
        <col width="10%" />  <!-- 변경출자금 -->
        </colgroup>
        <tbody>
            <tr>
                <td rowspan="2" class="tdblueM">년도</td>
                <td rowspan="2" class="tdblueM">사번</td>
                <td rowspan="2" class="tdblueM">성명</td>
                <td rowspan="2" class="tdblueM">연배당율</td>
                <td colspan="2" class="tdblueM">연배당금</td>
                <td rowspan="2" class="tdblueM">배당금</td>
                <td colspan="2" class="tdblueM">원금회수</td>
                <td rowspan="2" class="tdblueM">출자누계액</td>
                <td rowspan="2" class="tdblueM">원금회수금액</td>
                <td rowspan="2" class="tdblueM">현출자금</td>
                <td rowspan="2" class="tdblueM">변경출자금</td>
            </tr>
            <tr>
                <td class="tdblueM">적치</td>
                <td class="tdblueM">회수</td>
                <td class="tdblueM">적치</td>
                <td class="tdblueM">회수</td>
            </tr>
                
            <tr>
                <td class="tdwhiteM">${dividend.divi_year}</a></td>
                <td class="tdwhiteM">${dividend.emp_no}</a></td>
                <td class="tdwhiteM">${dividend.kor_name}</td>
                <td class="tdwhiteM">${dividend.year_share_rate}</td>
                <td class="tdwhiteM"><input type="radio" name="divi_yn" value="Y"></td>
                <td class="tdwhiteM"><input type="radio" name="divi_yn" value="N"></td>
                <td id="td_divi_amt" class="tdwhiteM">${dividend.divi_amt}</td>
                <td class="tdwhiteM"><input type="radio" name="prin_yn" value="Y"></td>
                <td class="tdwhiteM"><input type="radio" name="prin_yn" value="N"></td>
                <td id="td_paym_sum"  class="tdwhiteM">${dividend.paym_sum}</td>
                <td class="tdwhiteM"><input type="text" id="prin_amt" name="prin_amt" class="txt_field" style="width: 80%; text-align: center" value="${dividend.prin_amt}" esse="true" esseLabel="원금회수금액" ></td>
                <td id="td_mm_paym_amt" class="tdwhiteM">${dividend.mm_paym_amt}</td>
                <td class="tdwhiteM"><input type="text" id="afr_mm_paym_amt" name="afr_mm_paym_amt" class="txt_field" style="width: 80%; text-align: center" value="${dividend.afr_mm_paym_amt}" esse="true" esseLabel="변경출자금" ></td>
            </tr>
            
         </tbody>
     </table>
     
     <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
            <td class="space20"></td>
        </tr>
     </table>

     <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td class="tdwhiteL0">
                <div id="divView" style="width: 750px;" class="outline">
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03"> ※ 오픈기간 : 2018년 1월 22일 ~ 23일 </span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   담    당 : 재경관리팀 이현정(문의 : 850-1144) </span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   지급일자 : 2018년 2월 5일 </span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   기한내 제출하지 않을 경우 변동사항이 없는(적치) 것으로 처리됨.</span></b><br>
                
                
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03"> ★ 기 타 </span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   출자금 원금 및 배당금은 인출 희망자에 한하여 급여(2/5)일에</span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   급여통장으로만 지급되며, 인출금액이 만원 미만인경우 인출되지</span></b><br>
                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><span class="font_03">   않으니(적치) 착오 없으시길 바랍니다.</span></b><br>
                

                </div> <br>
            </td>
        </tr>
     </table>
     
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     <tr>
       <td class="space10"></td>
     </tr>
     </table>
     <table style="width:100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td style="height: 38px;" align="right" valign="top" >
              <input type="checkbox" name="Notice" value="checkbox" style="border:0" onclick="javascript:toggleCheck();">&nbsp;<span style="font-size:9pt; cursor:default;"><%=messageService.getString("e3ps.message.ket_message", "02160") %><%--오늘 이창을 다시 열지 않음--%></span>
          </td>
        </tr>
        </table>
</div>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>