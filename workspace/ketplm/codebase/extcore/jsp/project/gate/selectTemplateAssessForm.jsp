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
<!-- MultiCombo Start -->
<%@include file="/jsp/common/multicombo.jsp" %>

<%

	////평가항목 등록시 ProjectOID를 받아서 사업부, 개발유형, 개발구분정보를 가져와서 세팅하기. ------------작업중--------------
	String oid = request.getParameter("oid");
	Object resultObj = KETObjectUtil.getObject(oid);
	e3ps.project.E3PSProject project = (e3ps.project.E3PSProject)CommonUtil.getObject(oid);
	e3ps.project.beans.E3PSProjectData projectData = new e3ps.project.beans.E3PSProjectData(project);
	
	
	String pjtDivisionStr = projectData.teamType;
	if(pjtDivisionStr!=null && pjtDivisionStr.indexOf("자동차")>=0) {
	    pjtDivisionStr = NumberCodeUtil.getNumberCodeMap("DIVISIONNUMBER", "1"); //"671979063";
	}else if(pjtDivisionStr!=null && pjtDivisionStr.indexOf("전자")>=0) {
	    pjtDivisionStr = NumberCodeUtil.getNumberCodeMap("DIVISIONNUMBER", "2"); //"671979064";
	}

	String pjtDevDivStr = (project.getDevelopentType() == null) ? "" : StringUtil.checkNull(NumberCodeUtil.getNumberCodeValue("DEVELOPENTTYPE", project.getDevelopentType().getCode(), messageService.getLocale().toString()));
    if(pjtDevDivStr!=null && pjtDevDivStr.indexOf("전략개발")>=0) {
	    pjtDevDivStr = NumberCodeUtil.getNumberCodeMap("DEVELOPENTTYPE", "DEV001"); //19910
    }else if(pjtDevDivStr!=null && pjtDevDivStr.indexOf("수주개발")>=0) {
	    pjtDevDivStr = NumberCodeUtil.getNumberCodeMap("DEVELOPENTTYPE", "DEV002"); //19911
    }else if(pjtDevDivStr!=null && pjtDevDivStr.indexOf("연구개발")>=0) {
	    pjtDevDivStr = NumberCodeUtil.getNumberCodeMap("DEVELOPENTTYPE", "DEV003"); //19912
    }else if(pjtDevDivStr!=null && pjtDevDivStr.indexOf("추가금형")>=0) {
	    pjtDevDivStr = NumberCodeUtil.getNumberCodeMap("DEVELOPENTTYPE", "DEV004"); //19913
    }
	
	int pjtType = project.getPjtType();
	String pjtTypeName = "";
	String pjtDevTypeStr = "";
	if (pjtType == 0) {
       pjtTypeName = "검토개발(전자)";
	   pjtDevTypeStr = NumberCodeUtil.getNumberCodeMap("PROJECTTYPE", "1"); // "671979067";
    } else if (pjtType == 1) {
        pjtTypeName = "검토개발(자동차)";
        pjtDevTypeStr = NumberCodeUtil.getNumberCodeMap("PROJECTTYPE", "1"); // "671979067";
    } else if (pjtType == 2) {
        pjtTypeName = "제품(자동차)";
        pjtDevTypeStr = NumberCodeUtil.getNumberCodeMap("PROJECTTYPE", "2"); // "671979068";
    } else if (pjtType == 3) {
        pjtTypeName = "금형(자동차)";
        pjtDevTypeStr = NumberCodeUtil.getNumberCodeMap("PROJECTTYPE", "3"); // "671979069";
    } else if (pjtType == 4) {
        pjtTypeName = "제품(전자)";
        pjtDevTypeStr = NumberCodeUtil.getNumberCodeMap("PROJECTTYPE", "2"); // "671979068";
    }
    //out.println(pjtDivisionStr + ", pjtDevDivStr:"+pjtDevDivStr+",idx:"+pjtDevDivStr.indexOf("전략개발")+", pjtType:"+pjtType);

	String divisionInfo = "";
	String devType = "";
	String devDiv = "";
	if( resultObj instanceof e3ps.project.ReviewProject ) {
	
    }else if( resultObj instanceof e3ps.project.ProductProject ) {
	
    }
	////평가항목 등록시 ProjectOID를 받아서 사업부, 개발유형, 개발구분정보를 가져와서 세팅하기


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
    

    ArrayList developTypeCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("developType")), ", " );
    // Project Type
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PROJECTTYPE");
    List<Map<String, Object>> projectTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVELOPENTTYPE");
    List<Map<String, Object>> developTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    // 제품군
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PRODCATEGORYCODE");
    List<Map<String, Object>> prodCategoryCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    String selectPjtType = request.getParameter("selectPjtType");
    Vector tMap = null;
    String selected = "";
    List<Map<String, Object>> divisionNumCode = new ArrayList<Map<String, Object>>();
    
    boolean isType0 = CommonUtil.isMember("전자사업부");
    // 전자사업부 권한이면
    if ( searchCondition != null && searchCondition.get("bizUnit") != null ) {
        if ( searchCondition.get("bizUnit").equals("1") ) {
            isType0 = false;
        }
        else {
            isType0 = true;
        }
    }

    if ( selectPjtType!=null && selectPjtType.equals("3") ) {
    }
    else {
        // 사업부
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DIVISIONNUMBER");
        divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }
    
%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/selectTemplateAssessSheet.js"></script>


<script type="text/javascript">


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
    $('[name=startDate]').val(predate);
    $('[name=endDate]').val(postdate);
    
//     // Calener field 설정
     CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    

    // multiselect
    CommonUtil.multiSelect('devType',100);
    CommonUtil.multiSelect('division',100);
    CommonUtil.multiSelect('devDiv',100);
    CommonUtil.multiSelect('prodCategory',100);
    
    //client paging
    //TemplateAssessSheet.createGrid();
    //server paging
    TemplateAssessSheet.createPagingGrid();
    
});



function selectAssessList() {
    if(confirm("<%=messageService.getString("e3ps.message.ket_message", "07158") %><%--선택한 평가항목을 반영하시겠습니까?--%>")) {
		TemplateAssessSheet.selectAssessPopup();
		this.close();
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

}


</script>
<!-- <table width="780" height="100%" border="0" cellspacing="0" cellpadding="0"> -->

<table border="0" cellpadding="0" cellspacing="0" width="860px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="860px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="860px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07156") %><%--평가시트 검색--%></td>
                      <td width="10"></td>
                    </tr>
                </table>
                </td>
                <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="860px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></span><span class="pro-cell b-right"></span></span></span>
                </td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                          onclick="javascript:selectAssessList();" class="btn_blue">
                          <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>
                          </a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                          <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                              href="javascript:TemplateAssessSheet.search();" class="btn_blue">
                              <%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>
                              </a></td>
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
                                href="javascript:this.close();" class="btn_blue">
                                <%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>
                                </a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
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
            <table style="width: 100%;">
                <tr>
                    <td class="tab_btm2"></td>
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
                <input type="hidden" name="active" value="Y">

                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="division" name="division" className="fm_jmp" style="width: 150px;" codeType="DIVISIONNUMBER" multiple="multiple" value="<%=pjtDivisionStr%>"/>
                        </td>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                        <td width="*" class="tdwhiteL">
                            
                            <ket:select id="devType" name="devType" className="fm_jmp" style="width: 150px;" codeType="PROJECTTYPE" multiple="multiple" value="<%=pjtDevTypeStr%>"/>
                        </td>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발 구분--%></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="devDiv" name="devDiv" className="fm_jmp" style="width: 150px;" codeType="DEVELOPENTTYPE" multiple="multiple" value="<%=pjtDevDivStr%>"/>
                            
                        </td>
                    </tr>
                    <tr>
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
                        <td width="*" class="tdwhiteL">
<%--                             <ket:select id="prodCategory" name="prodCategory" className="fm_jmp" style="width: 160px;" codeType="PRODCATEGORYCODE" multiple="multiple"/> --%>
                            
                            <input type="text" name="partMultiNames" class="txt_field" style="width: 70%">
                            <input type="hidden" name="partMultiOids" value="">
                            <a href="javascript:SearchUtil.selectPartClaz(setPartClazOid, 'openAll=Y');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partMultiNames','partMultiOids');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>

                        </td>
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
                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                        <td width="*" colspan="3" class="tdwhiteL">
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
