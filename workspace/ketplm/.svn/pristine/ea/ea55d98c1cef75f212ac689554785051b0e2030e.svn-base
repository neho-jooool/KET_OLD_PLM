<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
<!--

$(document).ready(function(){
    

});

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
            <c:if test="${status.index%3==0}" ><tr id="partSpecPropTr"></c:if>
                <td class="title">${fn:replace(result.partAttributeDTO.attrName, '(', '<br/>(')}</td>
                <td>
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
                              <input type="text" id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectOnePart('setPartNo${result.partAttributeDTO.attrCode}', 'pType=D');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('${result.partAttributeDTO.attrCode}');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
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
                      <c:otherwise>
                            <input id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="text" class="txt_field" style="width: 95%" value="" >
                      </c:otherwise>
                      </c:choose>
		          </c:when>
		          <c:when test="${result.partAttributeDTO.attrInputType=='SELECT'}">
		              <!-- YAZAKI 여부는 LIST화면에 있어서 빼야 함. : DISABLE 상단 ? -->
		              <c:if test="${result.partAttributeDTO.attrCode != 'spIsYazaki'}" >
			              <c:choose>  
			              <%-- 재질(비철), 재질(수지) --%>
	                      <c:when test="${result.partAttributeDTO.attrCode == 'spMaterialInfo' || result.partAttributeDTO.attrCode == 'spMaterNotFe'}">
	                        <input id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" type="text" class="txt_field" style="width: 95%" value="" >
	                      </c:when>
			              <c:when test="${result.partAttributeDTO.attrMultiSelect}">
	                        <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" multiple="multiple" otherHtml="ketMultiSelect='Y'" />
			              </c:when>
			              <c:otherwise>
			               <ket:select id="${result.partAttributeDTO.attrCode}" name="${result.partAttributeDTO.attrCode}" className="fm_jmp" style="width: 170px;" useCodeValue="true" codeType="${result.partAttributeDTO.attrCodeType}" multiple="multiple" otherHtml="ketMultiSelect='N'" />
			              </c:otherwise>
			              </c:choose>
		              </c:if>
                  </c:when>
                  <c:otherwise>
                      <input type="text" value="" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_5.png" />&nbsp;&nbsp;<img src="/plm/portal/images/icon_delete.gif" />
                  </c:otherwise>
		       </c:choose>
            <c:if test="${status.index%3==2}" ></tr></c:if>
        </c:forEach>
        <c:if test="${fn:length(resultList)%3==0}" ></c:if>
        <c:if test="${fn:length(resultList)%3==1}" >
             </tr>
             <%-- 끝에 colspan 추가 및 사이즈 조절 --%>
             <script lang="javascript">
                 $('#partSpecPropTbody tr:last').each(function(){
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
        </c:if>
        <c:if test="${fn:length(resultList)%3==2}" >
             </tr>
             <%-- 끝에 colspan 추가 --%>
             <script lang="javascript">
                 $('#partSpecPropTbody tr:last').each(function(){
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
        </c:if>
	</tbody>
</table>
