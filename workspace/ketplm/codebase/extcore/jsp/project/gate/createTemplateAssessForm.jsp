<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.groupware.company.*"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />

<%
    Map<String, Object> parameter = new HashMap<String, Object>();
    KETParamMapUtil enumMap = null;
    String targetTypeEnumKey = "";
    String targetTypeEnum = "";
    
    String pjtDivisionStr = "";
    if(CommonUtil.isMember("전자사업부")){
        pjtDivisionStr = NumberCodeUtil.getNumberCodeMap("DIVISIONNUMBER", "1"); //"671979063";    "CA";
    }else if(CommonUtil.isMember("자동차사업부")){
	    pjtDivisionStr = NumberCodeUtil.getNumberCodeMap("DIVISIONNUMBER", "2"); //"671979064";    "ER";
    }
    
    ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
    
    Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForQuery("ASSESSTARGETTYPE");
    if (targetTypeVec != null) {
        for (int i = 0; i < targetTypeVec.size(); i++) {
            NumberCode code = (NumberCode) targetTypeVec.get(i);
            enumMap = KETParamMapUtil.getMap();
            enumMap.put("key",      code.getCode());
            enumMap.put("value",    code.getName());
            enumList.add(enumMap);
            // code.getPersistInfo().getObjectIdentifier().getStringValue()
        }
        targetTypeEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
        targetTypeEnum       = KETGridUtil.getValueEnum(enumList, false);
    }

    
    String gateExistG1 = "1";
    String gateExistG2 = "1";
    String gateExistG3 = "1";
    String gateExistG4 = "1";
    String gateExistG5 = "1";
    String gateExistG6 = "1";
    String gateExistG7 = "0";
    String gateExistG8 = "0";
    String gateExistG9 = "0";
    String gateExistG10 = "0";
    String gateExistG11 = "0";
    String gateExistG12 = "0";
    String gateExistG13 = "0";
    String gateExistG14 = "0";
    String gateExistG15 = "0";
    String gateExistG16 = "0";
    String gateExistG17 = "0";
    String gateExistG18 = "0";
    String gateExistG19 = "0";
    String gateExistG20 = "0";
    String gateExistG1Name = "GateA";
    String gateExistG2Name = "GateB";
    String gateExistG3Name = "Gate0";
    String gateExistG4Name = "Gate1";
    String gateExistG5Name = "Gate2";
    String gateExistG6Name = "Gate3";
    String gateExistG7Name = "Gate4";
    String gateExistG8Name = "Gate5";
    String gateExistG9Name = "Gate6";
    String gateExistG10Name = "Gate7";
    String gateExistG11Name = "Gate8";
    String gateExistG12Name = "Gate9";
    String gateExistG13Name = "Gate10";
    String gateExistG14Name = "";
    String gateExistG15Name = "";
    String gateExistG16Name = "";
    String gateExistG17Name = "";
    String gateExistG18Name = "";
    String gateExistG19Name = "";
    String gateExistG20Name = "";
    
    Vector gateLevelVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
    if (gateLevelVec != null) {
	for (int i = 0; i < gateLevelVec.size(); i++) {
            NumberCode code = (NumberCode) gateLevelVec.get(i);

            if("G1".equals(code.getCode())) {
                gateExistG1 = "1";
               gateExistG1Name = "Gate"+code.getName();
            }
            else if("G2".equals(code.getCode())) {
                gateExistG2 = "1";
               gateExistG2Name = "Gate"+code.getName();
            }
            else if("G3".equals(code.getCode())) {
                gateExistG3 = "1";
               gateExistG3Name = "Gate"+code.getName();
            }
            else if("G4".equals(code.getCode())) {
               gateExistG4 = "1";
               gateExistG4Name = "Gate"+code.getName();
            }
            else if("G5".equals(code.getCode())) {
               gateExistG5 = "1";
               gateExistG5Name = "Gate"+code.getName();
            }
            else if("G6".equals(code.getCode())) {
               gateExistG6 = "1";
               gateExistG6Name = "Gate"+code.getName();
            }
            else if("G7".equals(code.getCode())) {
               gateExistG7 = "1";
               gateExistG7Name = code.getName();
            }
            else if("G8".equals(code.getCode())) {
               gateExistG8 = "1";
               gateExistG8Name = code.getName();
            }
            else if("G9".equals(code.getCode())) {
               gateExistG9 = "1";
               gateExistG9Name = code.getName();
            }
            else if("G10".equals(code.getCode())) {
               gateExistG10 = "1";
               gateExistG10Name = code.getName();
            }
            else if("G11".equals(code.getCode())) {
               gateExistG11 = "1";
               gateExistG11Name = "Gate"+code.getName();
            }
            else if("G12".equals(code.getCode())) {
               gateExistG12 = "1";
               gateExistG12Name = "Gate"+code.getName();
            }
            else if("G13".equals(code.getCode())) {
               gateExistG13 = "1";
               gateExistG13Name = "Gate"+code.getName();
            }
            else if("G14".equals(code.getCode())) {
               gateExistG14 = "1";
               gateExistG14Name = "Gate"+code.getName();
            }
            else if("G15".equals(code.getCode())) {
               gateExistG15 = "1";
               gateExistG15Name = "Gate"+code.getName();
            }
            else if("G16".equals(code.getCode())) {
               gateExistG16 = "1";
               gateExistG16Name = "Gate"+code.getName();
            }
            else if("G17".equals(code.getCode())) {
               gateExistG17 = "1";
               gateExistG17Name = "Gate"+code.getName();
            }
            else if("G18".equals(code.getCode())) {
               gateExistG18 = "1";
               gateExistG18Name = "Gate"+code.getName();
            }
            else if("G19".equals(code.getCode())) {
               gateExistG19 = "1";
               gateExistG19Name = "Gate"+code.getName();
            }
            else if("G20".equals(code.getCode())) {
               gateExistG20 = "1";
               gateExistG20Name = "Gate"+code.getName();
            }
        }
    }


//     ArrayList developTypeCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("developType")), ", " );
//     NumberCode valuePProductCode = null;    //제품군
//     // Project Type
//     parameter.clear();
//     parameter.put("locale",   messageService.getLocale());
//     parameter.put("codeType", "PROJECTTYPE");
//     List<Map<String, Object>> projectTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
//     // 개발구분
//     parameter.clear();
//     parameter.put("locale",   messageService.getLocale());
//     parameter.put("codeType", "DEVELOPENTTYPE");
//     List<Map<String, Object>> developTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

//     // 제품군
//     parameter.clear();
//     parameter.put("locale",   messageService.getLocale());
//     parameter.put("codeType", "PRODCATEGORYCODE");
//     List<Map<String, Object>> prodCategoryCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    
//     boolean isType0 = CommonUtil.isMember("전자사업부");
//     // 전자사업부 권한이면
//     if ( searchCondition != null && searchCondition.get("bizUnit") != null ) {
//         if ( searchCondition.get("bizUnit").equals("1") ) {
//             isType0 = false;
//         }
//         else {
//             isType0 = true;
//         }
//     }

//     String selectPjtType = request.getParameter("selectPjtType");
//     List<Map<String, Object>> divisionNumCode = new ArrayList<Map<String, Object>>();
//     if ( selectPjtType!=null && selectPjtType.equals("3") ) {
//     }
//     else {
//         // 사업부
//         parameter.clear();
//         parameter.put("locale",   messageService.getLocale());
//         parameter.put("codeType", "DIVISIONNUMBER");
//         divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
//     }

enumList = new ArrayList<KETParamMapUtil>();
String creteriaEnumKey = "";
String creteriaEnum = "";

enumMap = KETParamMapUtil.getMap();

enumMap.put("key",      0);
enumMap.put("value",    ">=");

enumList.add(enumMap);

enumMap = KETParamMapUtil.getMap();

enumMap.put("key",      1);
enumMap.put("value",    "=");

enumList.add(enumMap);

enumMap = KETParamMapUtil.getMap();

enumMap.put("key",      2);
enumMap.put("value",    "<=");

enumList.add(enumMap);

creteriaEnumKey    = KETGridUtil.getKeyEnum(enumList, false);
creteriaEnum       = KETGridUtil.getValueEnum(enumList, false);
%>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/CreateTemplateAssessSheet.js"></script>
<script type="text/javascript">

window.setPartClz = function(returnValue){
    
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    
    $('[name=partOid]').val(returnValue[0].id);//oid
    $('[name=partName]').val(returnValue[0].name);//kr name
    
}

function createGateTemplate() 
{
//  	alert($('[name=devType]').val() + ':' + $('[name=devDiv]').val());
	if($('[name=devType]').val()=='' || $('[name=devType]').val()==null) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07240", "개발유형") %><%--개발유형을 입력해주세요--%>');
        return;
    }
    if($('[name=division]').val()=='' || $('[name=division]').val()==null) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07240", "사업부") %><%--사업부을(를) 입력해주세요--%>');
        return;
    }
    if($('[name=devType]').val()=='671979068') {
	    if($('[name=devDiv]').val()=='' || $('[name=devDiv]').val()==null) {
	        alert('<%=messageService.getString("e3ps.message.ket_message", "07240", "개발구분") %><%--개발구분을(를) 입력해주세요--%>');
	        return;
	    }
    }
    if($('[name=partOid]').val()=='' || $('[name=partOid]').val()==null) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07240", "제품구분") %><%--제품구분을(를) 입력해주세요--%>');
        return;
    }
    if($('[name=sheetName]').val()=='' || $('[name=sheetName]').val()==null) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07240", "이름") %><%--이름을(를) 입력해주세요--%>');
        return;
    }
	
	var rtnStr = CreateTemplateAssessSheet.createTemplateGateCheckSheetList();
	if(rtnStr=='success') {
	    alert('<%=messageService.getString("e3ps.message.ket_message", "05144") %>');
// 	    $('[name=CreateTemplateAssessSheetCreateForm]').attr('action', '/plm/ext/project/gate/viewTemplateAssessDTOForm.do');
// 	    $('[name=CreateTemplateAssessSheetCreateForm]').submit();
	    
        opener.searchTemplateAssessSheetList();
        
	    document.CreateTemplateAssessSheetCreateForm.method = "post";
	    document.CreateTemplateAssessSheetCreateForm.action = "/plm/ext/project/gate/viewTemplateAssessDTOForm.do";
	    document.CreateTemplateAssessSheetCreateForm.submit();

	}
    //this.close();
}

$(document).ready(function(){
    CommonUtil.singleSelect('devType',100);
    CommonUtil.singleSelect('division',100);
    CommonUtil.singleSelect('devDiv',100);
    CommonUtil.singleSelect('prodCategory',100);

//     CommonUtil.singleSelect('active',100);
    
    
    CreateTemplateAssessSheet.createPaingGrid();
    treeGridResize("#CreateTemplateAssessSheetSearchGrid","#listCheckGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
    //제품분류 suggest
    SuggestUtil.bind('PRODUCTTYPE', 'partName', 'partOid');


    // Project 구분 radio click event
    $("[name=devType]").change(function() {
        form = document.forms[0];
        var devTypeValue = "";
        for(i=0; i<form.devType.length; i++) {
            if(form.devType[i].selected) devTypeValue = form.devType[i].value;
        }

        if(devTypeValue == "671979068") {   //제품    671979068   //검토 671979067  //금형 671979069   
            $("#devDivLoc").show();
            $("#devDivTitle").show();
        }else { 
            $("#devDivLoc").hide();
            $("#devDivTitle").hide();
            //$('#devType').attr('value','');
            $('#devDiv').prop('selectedIndex', -1);

            //$("form[name='CreateTemplateAssessSheetCreateForm'] select[name='devType'] option").remove();   
            //$("form[name='CreateTemplateAssessSheetCreateForm'] select[name='devType']").append("<option value="">==SELECT==</option>");

        }
    });
    
    $("#devDivLoc").hide();
    $("#devDivTitle").hide();
});
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07157") %><%--평가시트 등록--%></td>
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                href="javascript:createGateTemplate();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
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
                                <!-- 목록  --> <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a>
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

            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <form name="CreateTemplateAssessSheetCreateForm" method="post">
            <input type="hidden" name="targetTypeEnumKey" value="<%=targetTypeEnumKey %>"/>
            <input type="hidden" name="targetTypeEnum" value="<%=targetTypeEnum %>"/>
            <input type="hidden" name="creteriaEnumKey" value="<%=creteriaEnumKey %>"/>
            <input type="hidden" name="creteriaEnum" value="<%=creteriaEnum %>"/>
            <input type="hidden" name="oid" value=""/>
            <input type="hidden" name="delOids" value=""/>
            <input type="hidden" name="gateExistG1" value="<%=gateExistG1 %>"/>
            <input type="hidden" name="gateExistG2" value="<%=gateExistG2 %>"/>
            <input type="hidden" name="gateExistG3" value="<%=gateExistG3 %>"/>
            <input type="hidden" name="gateExistG4" value="<%=gateExistG4 %>"/>
            <input type="hidden" name="gateExistG5" value="<%=gateExistG5 %>"/>
            <input type="hidden" name="gateExistG6" value="<%=gateExistG6 %>"/>
            <input type="hidden" name="gateExistG7" value="<%=gateExistG7 %>"/>
            <input type="hidden" name="gateExistG8" value="<%=gateExistG8 %>"/>
            <input type="hidden" name="gateExistG9" value="<%=gateExistG9 %>"/>
            <input type="hidden" name="gateExistG10" value="<%=gateExistG10 %>"/>
            <input type="hidden" name="gateExistG11" value="<%=gateExistG11 %>"/>
            <input type="hidden" name="gateExistG12" value="<%=gateExistG12 %>"/>
            <input type="hidden" name="gateExistG13" value="<%=gateExistG13 %>"/>
            <input type="hidden" name="gateExistG14" value="<%=gateExistG14 %>"/>
            <input type="hidden" name="gateExistG15" value="<%=gateExistG15 %>"/>
            <input type="hidden" name="gateExistG16" value="<%=gateExistG16 %>"/>
            <input type="hidden" name="gateExistG17" value="<%=gateExistG17 %>"/>
            <input type="hidden" name="gateExistG18" value="<%=gateExistG18 %>"/>
            <input type="hidden" name="gateExistG19" value="<%=gateExistG19 %>"/>
            <input type="hidden" name="gateExistG20" value="<%=gateExistG20 %>"/>
            <input type="hidden" name="gateExistG1Name" value="<%=gateExistG1Name %>"/>
            <input type="hidden" name="gateExistG2Name" value="<%=gateExistG2Name %>"/>
            <input type="hidden" name="gateExistG3Name" value="<%=gateExistG3Name %>"/>
            <input type="hidden" name="gateExistG4Name" value="<%=gateExistG4Name %>"/>
            <input type="hidden" name="gateExistG5Name" value="<%=gateExistG5Name %>"/>
            <input type="hidden" name="gateExistG6Name" value="<%=gateExistG6Name %>"/>
            <input type="hidden" name="gateExistG7Name" value="<%=gateExistG7Name %>"/>
            <input type="hidden" name="gateExistG8Name" value="<%=gateExistG8Name %>"/>
            <input type="hidden" name="gateExistG9Name" value="<%=gateExistG9Name %>"/>
            <input type="hidden" name="gateExistG10Name" value="<%=gateExistG10Name %>"/>
            <input type="hidden" name="gateExistG11Name" value="<%=gateExistG11Name %>"/>
            <input type="hidden" name="gateExistG12Name" value="<%=gateExistG12Name %>"/>
            <input type="hidden" name="gateExistG13Name" value="<%=gateExistG13Name %>"/>
            <input type="hidden" name="gateExistG14Name" value="<%=gateExistG14Name %>"/>
            <input type="hidden" name="gateExistG15Name" value="<%=gateExistG15Name %>"/>
            <input type="hidden" name="gateExistG16Name" value="<%=gateExistG16Name %>"/>
            <input type="hidden" name="gateExistG17Name" value="<%=gateExistG17Name %>"/>
            <input type="hidden" name="gateExistG18Name" value="<%=gateExistG18Name %>"/>
            <input type="hidden" name="gateExistG19Name" value="<%=gateExistG19Name %>"/>
            <input type="hidden" name="gateExistG20Name" value="<%=gateExistG20Name %>"/>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                      <col width="13%" />
                      <col width="20%" />
                      <col width="14%" />
                      <col width="20%" />
                      <col width="13%" />
                      <col width="20%" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%><span class="red">*</span></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="division" name="division" className="fm_jmp" style="width: 150px;" codeType="DIVISIONNUMBER" multiple="multiple" value="<%=pjtDivisionStr %>"/>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%><span class="red">*</span></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="devType" name="devType" className="fm_jmp" style="width: 160px;" codeType="PROJECTTYPE"  multiple="multiple"/>
                        </td>
                        <td class="tdblueL non-back" width="80">
                            <div id="devDivTitle">  <%-- --%>
                                <%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발 구분--%><span class="red">*</span>
                            </div>
                        </td>
                        <td width="*" class="tdwhiteL"><%-- --%>
                            <div id="devDivLoc">
                                <ket:select id="devDiv" name="devDiv" className="fm_jmp" style="width: 150px;" codeType="DEVELOPENTTYPE" multiple="multiple"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%><span class="red">*</span></td>
                        <td width="*" class="tdwhiteL"> 
                            <input type="hidden" name="partOid" value="">
			                <input type="text" id="partName" name="partName" class="txt_field" style="width:70%;" >
			                <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClz','onlyLeaf=Y&openAll=Y');"><img src="/plm/portal/images/icon_5.png"></a>
			                <a href="javascript:clearPartNo('partName');"><img src="/plm/portal/images/icon_delete.gif"></a>
                        </td>
                        
                        
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07147") %><%--평가시트명--%><span class="red">*</span></td>
                        <td width="*" class="tdwhiteL" colspan="3"><input type="text" name="sheetName" class="txt_field" style="width: 100%"></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                        <td width="*" class="tdwhiteL" colspan="5"><input type="text" name="sheetDesc" class="txt_field" style="width: 100%"></td>
                    </tr>
                 </table>
            </form>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5">&nbsp;</td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
	                            <td align="right">
	
				                    <table border="0" cellspacing="0" cellpadding="0">
				                        <tr>
				                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
				                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
				                                href="javascript:CreateTemplateAssessSheet.addRowAbove();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07151") %><%--상위추가--%></a></td>
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
				                                href="javascript:CreateTemplateAssessSheet.addRowBelow();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07152") %><%--하위추가--%></a></td>
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
				                                href="javascript:CreateTemplateAssessSheet.removeRowOnly();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
				                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
				                        </tr>
				                    </table>
			                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
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
                                    <div id="listCheckGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
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
