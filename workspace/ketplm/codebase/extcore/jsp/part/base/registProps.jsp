﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%


%>
<script type="text/javascript">
<!--

$(document).ready(function(){
    //alert(parent.name);
    //alert('${numberingCode}');
    //setInitial();
    parent.setPartNumber('${numberingCode}');
    parent.clazDivision = ('${clazDivision}');
    <c:forEach var="result" items="${resultList}" varStatus="status">
    <c:if test="${result.essential}">
    $('#${result.partAttributeDTO.attrCode}').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
    </c:if>
    <c:if test="${result.partAttributeDTO.attrCode == 'hompageImgIF' && result.essential}">
    $('#primaryFile').attr("esse","true").attr("esseLabel","<c:out value="${result.partAttributeDTO.attrName}" escapeXml="true"/>");
    </c:if>
    </c:forEach>
    
    

    
    //
    //alert("${partNamingOid}");
    parent.setAutoGenOid(""); // partName
    parent.setAutoGenOid("${partNamingOid}"); // partName
    
    // === 프로젝트, 부품중량 필수 체크 ===
    // 프로젝트
    <c:choose>  
    <c:when test="${isEsseProject}">
        parent.$("#projectNoSpan").removeClass("blue-star").addClass("red-star").show();
        parent.$('#projectNo').attr("esse","true");
    </c:when>
    <c:when test="${isCheckedProject}">
        parent.$("#projectNoSpan").removeClass("red-star").addClass("blue-star").show();
        parent.$('#projectNo').attr("esse","false");
    </c:when>
    <c:otherwise>
        parent.$("#projectNoSpan").removeClass("blue-star").removeClass("red-star").hide();
        parent.$('#projectNo').attr("esse","false");
    </c:otherwise>
    </c:choose>

    // 부품 중량
    <c:choose>  
    <c:when test="${isEsseWeight}">
        parent.$("#spNetWeightSpan").removeClass("blue-star").addClass("red-star").show();
        parent.$('#spNetWeight').attr("esse","true");
    </c:when>
    <c:when test="${isCheckedWeight}">
        parent.$("#spNetWeightSpan").removeClass("red-star").addClass("blue-star").show();
        parent.$('#spNetWeight').attr("esse","false");
    </c:when>
    <c:otherwise>
        parent.$("#spNetWeightSpan").removeClass("blue-star").removeClass("red-star").hide();
        parent.$('#spNetWeight').attr("esse","false");
    </c:otherwise>
    </c:choose>
    
    // //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // 부품유형 제한 : 부품분류의 classPartType이 F이면 제품(FERT), 반제품(HALB) 선택 나머지는 유형별로 FIX 'O'나 값이 없으면 기타
    // 부품유형 : 초기화
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////
    
    if(parent && parent.document && parent.document.forms[0] && parent.document.forms[0].partNumberFromErp){
        // erp에서 넘어온 것은 건들지 말자
        //alert("a");
    }else{
    <c:choose>  
    <c:when test="${classPartType=='F'}">

        parent.$("select[name=spPartType]").empty();
        parent.$("select[name=spPartType]").append(parent.spPartType_options);
        
        // 부품 타입별 세팅    
        parent.$("select[name=spPartType]").children('option').each(function() {
            //alert($(this).val());
            if ( $(this).val() != 'F' &&  $(this).val() != 'H' ) { 
                $(this).remove(); 
            }else{
                $(this).removeAttr("selected");
            }
         });
        
        parent.$('#spPartType').multiselect({
            noneSelectedText: LocaleUtil.getMessage('01802'),//선택
            header : false,
            multiple : false,
            selectList : 1,
            minWidth: 100,
            
        });
        
        parent.document.forms[0].spPartType.selectedIndex = -1;
        parent.$("select[name=spPartType]").siblings("button").first().find('span').next().text('<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>');
    
        parent.$('#partProdOfDieTr').hide();
        parent.$('#partName').attr("readonly", false);
        
    </c:when>
    <c:otherwise>
    
        parent.$("select[name=spPartType]").children('option').each(function() {
            $(this).remove();
        });
        parent.$("select[name=spPartType]").append(parent.spPartType_options);
        var partTypeVar = parent.CommonUtil.singleSelect('spPartType', 100);
        
        // 부품 타입별 세팅
        var isOthers = false;
        var hasOthersOption = false;
        // 불필요한 옵션 삭제
        parent.$("select[name=spPartType]").children('option').each(function() {
            <c:choose>  
            <c:when test="${classPartType=='F'}">
            if ( $(this).val() != 'F' &&  $(this).val() != 'H' ) { 
                $(this).remove(); 
            }
            </c:when>
            <c:when test="${empty classPartType || classPartType=='O'}">
            isOthers = true;
            if ( $(this).val() != 'O' ) { 
                $(this).remove(); 
            }
            </c:when>
            <c:otherwise>
               if ( $(this).val() != '${classPartType}' ) { 
                $(this).remove(); 
               }
            </c:otherwise>
            </c:choose>
         });
         // 옵션 선택
         parent.$("select[name=spPartType]").children('option').each(function() {
            <c:choose>  
            <c:when test="${classPartType=='F'}">
            if ( $(this).val() == 'F' || $(this).val() == 'H' ) { 
    
            }
            </c:when>
            <c:when test="${empty classPartType || classPartType=='O'}">
            if ( $(this).val() == 'O' ) { 
                hasOthersOption = true;
                $(this).attr('selected','selected');
            }
            </c:when>
            <c:otherwise>
            if ( $(this).val() == '${classPartType}' ) { 
                $(this).attr('selected','selected');
            }
            </c:otherwise>
            </c:choose>
         });
         
         // 홈페이지 유무 화면 처리
         parent.displayHp(parent.$("#spPartType").val());
         // others 처리
         if(isOthers && !hasOthersOption){
            parent.$("select[name=spPartType]").append("<option value='O' >기타</option>");
         }
        
         // Refresh 한 번
         partTypeVar.multiselect("refresh");
         
         // Mold일 경우에 부품 번호 선택되도록 수정
         var isNotMold = true;
         var isDie = false;
         parent.$("#spPartType option:selected").each(function () {
             if( $(this).val() == 'M' ){
                 isNotMold = false;
                 //return false;
             }else if( $(this).val() == 'D' ){
                 isDie = true;
             }
         });
         parent.$('#partNumber').attr("readonly", isNotMold);
         
         //Die - 제품 - 대표금형 자동 선택 처리 : Die일 경우에 제품번호가 보이고 PartName readOnly로 PartName에는 제품명 들어감
         if(isDie){
             parent.$('#partProdOfDieTr').show();
             parent.$('#partName').attr("readonly", true);
         }else{
             parent.$('#partProdOfDieTr').hide();
             parent.$('#partName').attr("readonly", false);
         }
         
    </c:otherwise>
    </c:choose>
    
    // partClassificType 이 Mold:M, Press:S 에 따라 spMMoldAt 금형구분 선택되도록 수정
    <c:choose>  
    <c:when test="${partClassificType=='M'}">
       try{
       parent.spMMoldAt_check_val = "2";
       }catch(e){}
    </c:when>
    <c:when test="${partClassificType=='S'}">
       try{
       parent.spMMoldAt_check_val = "1";
       }catch(e){}
    </c:when>
    <c:otherwise>
      try{
       parent.spMMoldAt_check_val = "";
      }catch(e){}
    </c:otherwise>
    </c:choose>
    
    }
    
    
});

<%--
function setInitial(){
    
   var tdWidth = 100;
    <c:forEach var="result" items="${resultList}" varStatus="status">
          <c:if test="${result.partAttributeDTO.attrInputType=='SELECT'}">
              <c:choose>  
              <c:when test="${result.partAttributeDTO.attrMultiSelect}">
               CommonUtil.multiSelect('${result.partAttributeDTO.attrCode}',tdWidth);
              </c:when>
              <c:otherwise>
                  <c:choose>  
                  <c:when test="${result.partAttributeDTO.attrCode == 'spSeries'}">
                  </c:when>
                  <c:otherwise>
                      CommonUtil.singleSelect('${result.partAttributeDTO.attrCode}',tdWidth);
                  </c:otherwise>
                  </c:choose>
              </c:otherwise>
              </c:choose>
          </c:if>
    </c:forEach>
}
--%>

-->
</script>
</head>
<table cellpadding="0" class="table-style-01" summary="">
    <colgroup>
        <col width="13%" />
        <col width="20%" />
        <col width="13%" />
        <col width="20%" />
        <col width="13%" />
        <col width="21%" />
    </colgroup>
    <tbody id="partSpecPropTbody" >
        <c:forEach var="result" items="${resultList}" varStatus="status">
            <c:choose>  
            
            <c:when test="${status.index%3==0}" ><tr id="partSpecPropTr"></c:when>
             <c:when test="${status.index%3!=0  && result.partAttributeDTO.attrCode == 'spDevSpec'}" >
             <tr id="partSpecPropTrspDevSpec"></c:when>
             <c:when test="${status.index%3!=0  && result.partAttributeDTO.attrCode == 'hompageImgIF'}" >
             <tr id="partSpecPropTrhompageImgIF" style="display:none"></c:when>
            <c:otherwise></c:otherwise>
            </c:choose>  
                <td class="title">${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}
                <c:choose>  
                   <c:when test="${result.essential}"><span class="red-star">*</span></c:when>
                   <c:when test="${result.checked}"><span class="blue-star">*</span></c:when>
                   <c:otherwise></c:otherwise>
                </c:choose>
                </td>
                <c:choose>  
                    <c:when test="${result.partAttributeDTO.attrCode == 'spDevSpec' || result.partAttributeDTO.attrCode == 'hompageImgIF'  && result.partAttributeDTO.attrCode != 'spTotalWeight' && result.partAttributeDTO.attrCode != 'spNetWeight' && result.partAttributeDTO.attrCode != 'spScrabWeight'}" >
                        <td colspan="5" class="tdwhiteL0"></c:when>
                    <c:otherwise><td></c:otherwise>
                </c:choose>  
                <c:choose>  
                  <c:when test="${result.partAttributeDTO.attrInputType=='TEXT'}">
                      <c:choose>  
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMTerminal' || result.partAttributeDTO.attrCode == 'spMConnector' 
                                    ||result.partAttributeDTO.attrCode == 'spMClip'     || result.partAttributeDTO.attrCode == 'spMRH'
                                    || result.partAttributeDTO.attrCode == 'spMCover'
                                    ||result.partAttributeDTO.attrCode == 'spMWireSeal' || result.partAttributeDTO.attrCode == 'spMatchBulb'}">
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectPart('setPartNo${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spScrabCode'}">
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}', 'pType=S');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spRepresentM'}">
                           <c:choose>
                           <c:when test="${classPartType=='D'}">
                            <input type="hidden" id="spRepresentMOid" name="spRepresentMOid" value="" >
                            <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%" >
                            <a id="spRepresentM_search_img_a" name="spRepresentM_search_img_a" href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}NOid', 'pType=D');">
                            <img id="spRepresentM_search_img" name="spRepresentM_search_img" src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a id="spRepresentM_del_img_a" name="spRepresentM_del_img_a" href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}', 'spRepresentMOid');">
                            <img id="spRepresentM_del_img" name="spRepresentM_del_img" src="/plm/portal/images/icon_delete.gif" border="0"></a>
                            <select id="${result.partAttributeDTO.attrCode}_select" name="${result.partAttributeDTO.attrCode}_select" class="fm_jmp" style="width: 170px;" multiple="multiple" numbercodetypeuse="N" ketmultiselect="N" esse="false" ></select>
                           </c:when>
                           <c:otherwise>
                            <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}', 'pType=D');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                           </c:otherwise>
                          </c:choose>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spDevManNm' || result.partAttributeDTO.attrCode == 'spManufacManNm' || result.partAttributeDTO.attrCode == 'spDesignManNm'}">
                            <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%">
                            <input type="hidden" name="${result.partAttributeDTO.attrCode}Oid">
                            <a href="javascript:SearchUtil.selectOneUser('${result.partAttributeDTO.attrCode}Oid','${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}Oid','${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spManufacPlace' || result.partAttributeDTO.attrCode == 'spDieWhere'}">
                            <input type="hidden" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" value="" >
                            <!-- 사내 외주 구분 -->
                            <input type="text" id="${result.partAttributeDTO.attrCode}Temp" name="${result.partAttributeDTO.attrCode}Temp" class="txt_field" readonly="readonly" style="width: 70%" >
                            <a href="javascript:setManuFacPlace('${result.partAttributeDTO.attrCode}', 'Temp');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}', '${result.partAttributeDTO.attrCode}Temp');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spCustomRev'}">
                            <input id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="text" class="txt_field" style="width: 95%" value="C00" readonly="readonly" >
                      </c:when>
               
                       <c:when test="${result.partAttributeDTO.attrCode == 'spDevSpec'}">
                            <textarea id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="textline" class="txt_field" value="${result.partStandardValue}" style="width: 95%" value="" >${result.partStandardValue}</textarea>
                       </c:when>
                      <c:when test="${result.partAttributeDTO.attrCode == 'hompageImgIF'}">
                        <input name="primaryFile" id="primaryFile" type="file"  style="width: 100%" >
                       </c:when>
                      <c:otherwise>
                            <input id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="text" class="txt_field" style="width: 95%" value="" >
                      </c:otherwise>
                      </c:choose>
                  </c:when>
                  <c:when test="${result.partAttributeDTO.attrInputType=='SELECT'}">
                      <c:choose>  
                      <%-- 시리즈 SpSeries numberCodeTypeUse="N" numberCodeType=""  ketMultiSelect="N" multiple="multiple" value="" --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spSeries'}">
                          <input type="hidden" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}">
                          <input type="text" id="${result.partAttributeDTO.attrCode}Name" name="${result.partAttributeDTO.attrCode}Name" class="txt_field" style="width: 70%" >
                            <a href="javascript:SearchUtil.selectOneSpSeriesPopUp('setSeries');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('spSeries', 'spSeriesName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      </c:when>
                      <%-- 원재료 Maker --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterMaker'}">
                          <ket:material id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="MAKER" clazOid="${clazOid}" className="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numberCodeType=""  ketMultiSelect="N" multiple="multiple" value="" />
                      </c:when>
                      <%-- 원재료 Type --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterType'}">
                          <select id="spMaterType" name="spMaterType" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N"></select>
                      </c:when>
                      <%-- 재질 수지 --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterialInfo'}">
                          <select id="spMaterialInfo" name="spMaterialInfo" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N"></select>
                      </c:when>
                      <%-- 재질 비철 --%>
                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterNotFe'}">
                          <select id="spMaterNotFe" name="spMaterNotFe" class="fm_jmp" style="width: 170px;" numberCodeTypeUse="N" numbercodetype="" ketmultiselect="N"></select>
                      </c:when>
                      <c:when test="${result.partAttributeDTO.attrMultiSelect}">
                        <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" multiple="multiple" otherHtml="ketMultiSelect='Y' numberCodeType='${result.partAttributeDTO.attrCodeType}' numberCodeTypeUse='Y' " />
                      </c:when>
                      <c:otherwise>
                       <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N' numberCodeType='${result.partAttributeDTO.attrCodeType}' numberCodeTypeUse='Y' " />
                      </c:otherwise>
                      </c:choose>
                  </c:when>
                  <c:otherwise>
                      <input type="text" value="" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_5.png" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_delete.gif" />
                  </c:otherwise>
               </c:choose>
               </td>
            <c:if test="${status.index%3==2}" ></tr></c:if>

        </c:forEach>
        <c:if test="${status.index%3==2}" >
            <%-- 수지(R20, R29)의 경우 원재료와 동일한 번호로 스크랩 생성 S + 원재료 품번 --%>
            <c:choose>  
            <c:when test="${numberingCode == 'R20' }">
            <tr id="partSpecPropTr">
            <td class="title">스크랩 자동생성</td><td colspan="5"><input id="partScrabAutoGen" name="partScrabAutoGen" type="checkbox" value="Y" checked="checked" disabled="disabled" /></td></tr>
            </c:when>
            <c:otherwise>
            </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${status.index%3==0}" >
            <c:choose>  
            <c:when test="${numberingCode == 'R20' }">
              <td class="title">스크랩 자동생성</td><td colspan="3"><input id="partScrabAutoGen" name="partScrabAutoGen" type="checkbox" value="Y" checked="checked" disabled="disabled" /></td></tr>
            </c:when>
            <c:otherwise>
             </tr>
             <%-- 끝에 colspan 추가 및 사이즈 조절 --%>
             <script lang="javascript">
                 $('#mainTable tr#partSpecPropTr:last').each(function(){
                     $(this).find('td:last').attr('colspan',5);
                     
                     var hasImageTd = false;
                     $(this).find("td:last img").each(function(){
                         hasImageTd = true;
                     });
                     
                     $(this).find("td:last [type='text']").each(function(){
                        //alert($(this).width());
                         if( !hasImageTd ){ // 95%
                             $(this).width("21.4%");
                         }else if( hasImageTd ){ // 70%
                             $(this).width("15.7%");
                         }
                     });
                 });
             </script>
            </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${status.index%3==1}" >
            <c:choose>  
            <c:when test="${numberingCode == 'R20' }">
              <td class="title">스크랩 자동생성</td><td><input id="partScrabAutoGen" name="partScrabAutoGen" type="checkbox" value="Y" checked="checked" disabled="disabled" /></td></tr>
            </c:when>
            <c:otherwise>
             </tr>
             <%-- 끝에 colspan 추가 --%>
             <script lang="javascript">
                 $('#mainTable tr#partSpecPropTr:last').each(function(){
                     $(this).find('td:last').attr('colspan',3);
                     
                     var hasImageTd = false;
                     $(this).find("td:last img").each(function(){
                         hasImageTd = true;
                     });
                     
                     $(this).find("td:last [type='text']").each(function(){
                         //alert($(this).width());
                         if( !hasImageTd ){ // 95%
                             $(this).width("34.5%");
                         }else if( hasImageTd){ // 70%
                             $(this).width("25.4%");
                         }
                     });
                 });
             </script>
            </c:otherwise>
            </c:choose>
        </c:if>
    </tbody>
</table>
