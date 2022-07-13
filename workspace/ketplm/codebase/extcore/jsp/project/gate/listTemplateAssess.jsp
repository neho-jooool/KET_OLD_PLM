<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    
    KETParamMapUtil enumMap = null;
    String divisionEnumKey = "";
    String divisionEnum = "";
    String devTypeEnumKey = "";
    String devTypeEnum = "";
    String devDivEnumKey = "";
    String devDivEnum = "";
    String prodCategoryEnumKey = "";
    String prodCategoryEnum = "";
    String activeEnumKey    = "";
    String activeEnum       = "";
   
    
    ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
    

    Vector divisionVec = NumberCodeHelper.manager.getNumberCodeForQuery("DIVISIONNUMBER");
    if (divisionVec != null) {
        for (int i = 0; i < divisionVec.size(); i++) {
            NumberCode code = (NumberCode) divisionVec.get(i);
            enumMap = KETParamMapUtil.getMap();
            enumMap.put("key",      code.getPersistInfo().getObjectIdentifier().getId()+"");
            enumMap.put("value",    code.getName());
            enumList.add(enumMap);
            // code.getPersistInfo().getObjectIdentifier().getStringValue()
        }
        divisionEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
        divisionEnum       = KETGridUtil.getValueEnum(enumList, false);
    }
        
    enumList = new ArrayList<KETParamMapUtil>();
    
    Vector projTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("PROJECTTYPE");
    if (projTypeVec != null) {
        for (int i = 0; i < projTypeVec.size(); i++) {
            NumberCode code = (NumberCode) projTypeVec.get(i);
            enumMap = KETParamMapUtil.getMap();
            enumMap.put("key",      code.getPersistInfo().getObjectIdentifier().getId()+"");
            enumMap.put("value",    code.getName());
            enumList.add(enumMap);
            // code.getPersistInfo().getObjectIdentifier().getStringValue()
        }
        devTypeEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
        devTypeEnum       = KETGridUtil.getValueEnum(enumList, false);
    }

    enumList = new ArrayList<KETParamMapUtil>();
        
    Vector devDivVec = NumberCodeHelper.manager.getNumberCodeForQuery("DEVELOPENTTYPE");
    if (devDivVec != null) {
        for (int i = 0; i < devDivVec.size(); i++) {
            NumberCode code = (NumberCode) devDivVec.get(i);
            enumMap = KETParamMapUtil.getMap();
            enumMap.put("key",      code.getPersistInfo().getObjectIdentifier().getId()+"");
            enumMap.put("value",    code.getName());
            enumList.add(enumMap);
            // code.getPersistInfo().getObjectIdentifier().getStringValue()
        }
        devDivEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
        devDivEnum       = KETGridUtil.getValueEnum(enumList, false);
    }
    
    enumList = new ArrayList<KETParamMapUtil>();
    
    Vector prodCategoryVec = NumberCodeHelper.manager.getNumberCodeForQuery("PRODCATEGORYCODE");
    if (prodCategoryVec != null) {
        for (int i = 0; i < prodCategoryVec.size(); i++) {
            NumberCode code = (NumberCode) prodCategoryVec.get(i);
            enumMap = KETParamMapUtil.getMap();
            enumMap.put("key",      code.getPersistInfo().getObjectIdentifier().getId()+"");
            enumMap.put("value",    code.getName());
            enumList.add(enumMap);
            // code.getPersistInfo().getObjectIdentifier().getStringValue()
        }
        prodCategoryEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
        prodCategoryEnum       = KETGridUtil.getValueEnum(enumList, false);
    }

    
    enumList = new ArrayList<KETParamMapUtil>();
    enumMap = KETParamMapUtil.getMap();
    
    enumMap.put("key",      "Y");
    enumMap.put("value",    "활성");
    
    enumList.add(enumMap);
    
    enumMap = KETParamMapUtil.getMap();
    
    enumMap.put("key",      "N");
    enumMap.put("value",    "비활성");
    
    enumList.add(enumMap);
    
    enumMap = KETParamMapUtil.getMap();
    enumMap.put("key",      "0");
    enumMap.put("value",    "비활성");
    
    enumList.add(enumMap);
    
    activeEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
    activeEnum       = KETGridUtil.getValueEnum(enumList, false);
    

    String selectPjtType = request.getParameter("selectPjtType");
    String isSearch = request.getParameter("isSearch");
    


%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/TemplateAssessSheet.js"></script>

<script type="text/javascript">

function searchTemplateAssessSheetList() {
	TemplateAssessSheet.search();
}
function onSearchSubmit(event) {
	if(event.keyCode==13) {
		TemplateAssessSheet.search();
	}
}


function clearAll() {
    $("#devType").multiselect("uncheckAll");
    $("#division").multiselect("uncheckAll");
    $("#devDiv").multiselect("uncheckAll");
    $("#prodCategory").multiselect("uncheckAll");
    $("#active").multiselect("uncheckAll");
    
    $('[name=partMultiNames]').val("");
    $('[name=partMultiOids]').val("");
    		
    CommonUtil.deleteValue('startDate');
    CommonUtil.deleteValue('endDate');

    // 사업부변경시 functioin 호출
//     dsChange($("#dType2 option:selected").val());
    // 제품구분변경시 function 호출
//     changeProductType();

    // 비용
//     var G = Grids[0];
//     G.SetValue(G.Toolbar, "Sap", "0", 1);
//     document.ProjectSearch.sap.value = "";

    // 결과내재검색 체크해제
//     $("input:checkbox[id=searchInSearch]").attr("checked", false);
}


$(document).ready(function(){
    //------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'fstChargeName', 'fstCharge');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    //Die no suggest
    SuggestUtil.bind('DIENO', 'dieNumber');
    //검토 Project No suggest
    SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeText', 'carType');
    //개발산출물 문서 분류 suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
    //기술 문서 분류 suggest
    SuggestUtil.bind('TECHDOCTYPE', 'techDocTypeTxt', 'techDocType');
    //고객사 suggest
    SuggestUtil.bind('CUSTOMER', 'oemTypeTxt', 'oemType');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNumber');
    //제품분류 suggest
    SuggestUtil.bind('PRODUCTTYPE', 'productTypeText', 'productType');
    //ECO no suggest
    SuggestUtil.bind('ECONO', 'ecoNumber', 'ecoOid');
    //ECR no suggest
    SuggestUtil.bind('ECRNO', 'ecrNumber', 'ecrOid');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'customerEventTxt', 'customerEventValue');
    //------------ end suggest binding ------------//
    // default 한달 설정
   /*  $('[name=startDate]').val(predate);
    $('[name=endDate]').val(postdate);
    
//     // Calener field 설정
     CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다. */
    

    // multiselect
    CommonUtil.multiSelect('devType',100);
    CommonUtil.multiSelect('division',100);
    CommonUtil.multiSelect('devDiv',100);
    CommonUtil.multiSelect('prodCategory',100);
    
    CommonUtil.multiSelect('active',100);

       
    //client paging
    //TemplateAssessSheet.createGrid();
    //server paging
    TemplateAssessSheet.createPagingGrid();
    
    treeGridResize("#TemplateAssessSheetSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});



</script>
<!-- <table width="780" height="100%" border="0" cellspacing="0" cellpadding="0"> -->

<table style="width: 100%; height: 40; align:center; border:0; cellspacing:0; cellpadding:0;" >
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">Gate <%=messageService.getString("e3ps.message.ket_message", "07156") %><%-- 평가시트 검색 --%></td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home 
                                <img src="/plm/portal/images/icon_navi.gif">Gate <%=messageService.getString("e3ps.message.ket_message", "07175") %><%-- 평가시트 --%>
                                <img src="/plm/portal/images/icon_navi.gif">Gate <%=messageService.getString("e3ps.message.ket_message", "07156") %><%-- 평가시트 검색 --%></td>
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TemplateAssessSheet.goCreate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07157") %><%--평가시트 등록--%></a></span><span class="pro-cell b-right"></span></span></span>
                                </td>
                                <td width="5">&nbsp;</td>
                                <td>
                                    <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></span><span class="pro-cell b-right"></span></span></span>
                                </td>
                                <td width="5">&nbsp;</td>
<!--                                 <td> -->
<%--                                     <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TemplateAssessSheet.update();"><%=messageService.getString("e3ps.message.ket_message", "02819") %>수정</a></span><span class="pro-cell b-right"></span></span></span> --%>
<!--                                 </td> -->
<!--                                 <td width="5">&nbsp;</td> -->
<!--                                 <td> -->
<%--                                     <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TemplateAssessSheet.remove();"><%=messageService.getString("e3ps.message.ket_message", "01690") %>삭제</a></span><span class="pro-cell b-right"></span></span></span> --%>
<!--                                 </td> -->
                                <td width="5">&nbsp;</td>
                                <td>
                                    <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:TemplateAssessSheet.search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></span><span class="pro-cell b-right"></span></span></span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table style="width: 100%;">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <form name="TemplateAssessSheetSearchForm">
                <input type="hidden" name="divisionEnumKey" value="<%=divisionEnumKey%>">
                <input type="hidden" name="divisionEnum" value="<%=divisionEnum%>">
                <input type="hidden" name="devTypeEnumKey" value="<%=devTypeEnumKey%>">
                <input type="hidden" name="devTypeEnum" value="<%=devTypeEnum%>">
                <input type="hidden" name="devDivEnumKey" value="<%=devDivEnumKey%>">
                <input type="hidden" name="devDivEnum" value="<%=devDivEnum%>">
                <input type="hidden" name="prodCategoryEnumKey" value="<%=prodCategoryEnumKey%>">
                <input type="hidden" name="prodCategoryEnum" value="<%=prodCategoryEnum%>">
                <input type="hidden" name="activeEnumKey" value="<%=activeEnumKey%>">
                <input type="hidden" name="activeEnum" value="<%=activeEnum%>">
                <input type="hidden" name="isSearch" value="<%=isSearch%>">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table style="width: 100%;">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
                        <td width="20%" class="tdwhiteL">
                            <ket:select id="division" name="division" className="fm_jmp" style="width: 160px;" codeType="DIVISIONNUMBER" multiple="multiple"/>
                        </td>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                        <td width="20%" class="tdwhiteL">
                            
                            <ket:select id="devType" name="devType" className="fm_jmp" style="width: 160px;" codeType="PROJECTTYPE" multiple="multiple"/>
                        </td>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발 구분--%></td>
                        <td width="30%" class="tdwhiteL">
                            <ket:select id="devDiv" name="devDiv" className="fm_jmp" style="width: 160px;" codeType="DEVELOPENTTYPE" multiple="multiple"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
                        <td width="20%" class="tdwhiteL">
<%--                             <ket:select id="prodCategory" name="prodCategory" className="fm_jmp" style="width: 160px;" codeType="PRODCATEGORYCODE" multiple="multiple"/> --%>
                            <input type="text" id="partMultiNames" name="partMultiNames" class="txt_field" style="width: 70%" onkeydown="onSearchSubmit(event)">
                            <input type="hidden" id="partMultiOids" name="partMultiOids" value="">
                            <a href="javascript:SearchUtil.selectPartClaz(setPartClazOid, 'openAll=Y');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partMultiNames','partMultiOids');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        <script type="text/javascript">
                        function setPartClazOid(checkedNode){
                            var nodeOIdStr='', nodeNameStr='';
                            for(var i=0; i < checkedNode.length; i++){
                                if(i == checkedNode.length - 1){
                                    nodeOIdStr += checkedNode[i].id;
                                    nodeNameStr += checkedNode[i].name;
                                }else{
                                    nodeOIdStr += checkedNode[i].id+','; 
                                    nodeNameStr += checkedNode[i].name+',';
                                }
                            }
                            $('[name=partMultiOids]').val(nodeOIdStr);
                            $('[name=partMultiNames]').val(nodeNameStr);
                        }
                        </script>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td width="20%" class="tdwhiteL">
                            <select id="active" name="active" className="fm_jmp" style="width: 160px;" multiple="multiple">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                    <option value="Y"><%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></option>
                                    <option value="N"><%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></option>
                            </select>
                        </td>
                        <td width="10%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                        <td width="30%" class="tdwhiteL">
                            <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('startDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('endDate');"
                            style="cursor: hand;"></td>
                        
                        </td>
                        </td>
                    </tr>
                                    
                </table>
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                        </div>
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   