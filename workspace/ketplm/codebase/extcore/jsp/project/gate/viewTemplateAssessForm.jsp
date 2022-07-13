<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>

<%@ page import="java.util.*" %>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 ext.ket.project.gate.entity.*,
                 ext.ket.part.entity.*,
                 e3ps.groupware.company.*"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />

<%
	String isModify = request.getParameter("isModify");
	TemplateAssessSheetDTO tmplSheetObj = (TemplateAssessSheetDTO)request.getAttribute("templateAssessSheet");
	
	Map<String, Object> parameter = new HashMap<String, Object>();
    KETParamMapUtil enumMap = null;
    String targetTypeEnumKey = "";
    String targetTypeEnum = "";
    String activeEnumKey    = "";
    String activeEnum       = "";
    
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
    String gateExistG1Name = "Gate1";
    String gateExistG2Name = "Gate2";
    String gateExistG3Name = "Gate3";
    String gateExistG4Name = "Gate4";
    String gateExistG5Name = "Gate5";
    String gateExistG6Name = "Gate6";
    String gateExistG7Name = "";
    String gateExistG8Name = "";
    String gateExistG9Name = "";
    String gateExistG10Name = "";
    String gateExistG11Name = "";
    String gateExistG12Name = "";
    String gateExistG13Name = "";
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

    String selectPjtType = request.getParameter("selectPjtType");
    List<Map<String, Object>> divisionNumCode = new ArrayList<Map<String, Object>>();
    if ( selectPjtType!=null && selectPjtType.equals("3") ) {
    }
    else {
        // 사업부
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DIVISIONNUMBER");
        divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }
    
    

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
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/project/gate/viewTemplateAssessSheet.js"></script>

<script type="text/javascript">
function deleteAssessSheet() 
{
    var rtnStr = CreateTemplateAssessSheet.deleteObj();
    if(rtnStr=='success') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %><%--정상적으로 처리되었습니다--%>');
        opener.searchTemplateAssessSheetList();
        this.close();
    }
}

function fn_updateMode() {
// 	alert('${templateAssessSheet.oid}');
	//location.replace('/plm/ext/project/gate/updateTemplateAssessForm.do');
	document.forms[0].method = 'POST';
    var oidFull =  '${templateAssessSheet.oid}';
    var oidLongStr = '';
    var oidSplit = oidFull.split(':');
    if(oidSplit.length>1) {
    	oidLongStr = oidSplit[1];
    }
    document.forms[0].action = '/plm/ext/project/gate/updateTemplateAssessForm.do';
    //document.forms[0].oid.value = oidLongStr;
	document.forms[0].submit();
}

function activeAssSheet(active) {
    //document.CreateTemplateAssessSheetUpdateForm.active.value = active;
    document.CreateTemplateAssessSheetUpdateForm.active.value = active;
    var rtnStr = CreateTemplateAssessSheet.updateTemplateGateCheckSheetList();
    if(active=='Y') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07144") %><%--활성상태로 변경합니다.--%>');
    }else {//if(active=='N') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "07145") %><%--비활성상태로 변경합니다.--%>');
    }
    location.replace('/plm/ext/project/gate/viewTemplateAssessForm.do?oid=${templateAssessSheet.oid}');
}
    
$(document).ready(function(){
    CommonUtil.singleSelect('devType',100);
    CommonUtil.singleSelect('division',100);
    CommonUtil.singleSelect('devDiv',100);
    CommonUtil.singleSelect('prodCategory',100);
    CommonUtil.singleSelect('active',100);
    CreateTemplateAssessSheet.createPaingGrid();
    treeGridResize("#CreateTemplateAssessSheetSearchGrid","#listCheckGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
<%
    if("671979068".equals(tmplSheetObj.getDevType())) {
%>
    $("#devDivLoc").show();
    $("#devDivTitle").show();
<%
    }else {
%>
    $("#devDivLoc").hide();
    $("#devDivTitle").hide();
<%
    }
%>
})


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
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07146") %><%--평가시트 조회--%></td>
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
                <%
                if(CommonUtil.isAdmin() || CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO")){
                %>
                <c:choose>
                <c:when test="${templateAssessSheet.active eq 'Y'}">
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:activeAssSheet('N');" class="btn_blue">
<%--                                 <%=messageService.getString("e3ps.message.ket_message", "07107") %> --%>

                                <%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
              </c:when>
              <c:otherwise>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:activeAssSheet('Y');" class="btn_blue">
<%--                                 <%=messageService.getString("e3ps.message.ket_message", "07106") %> --%>
                                <%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></a>
                            </td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
              </c:otherwise> 
            </c:choose>
                    <td>&nbsp;</td>
                    <td>
<%
	if(!"N".equals(isModify)) {
%>
                        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:fn_updateMode();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><!--수정--></a></span><span class="pro-cell b-right"></span></span></span>
<%
	}
%>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:this.deleteAssessSheet();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제 --%></a></span><span class="pro-cell b-right"></span></span></span>
                    </td>
                    <%
                }
                    %>
                    <td width="5">&nbsp;</td>
                    <td>
                        <span class="in-block v-middle r-space5"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"><a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><!--닫기--></a></span><span class="pro-cell b-right"></span></span></span>
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
            <form name="CreateTemplateAssessSheetUpdateForm" method="post">
            <input type="hidden" name="targetTypeEnumKey" value="<%=targetTypeEnumKey %>"/>
            <input type="hidden" name="targetTypeEnum" value="<%=targetTypeEnum %>"/>
            <input type="hidden" name="creteriaEnumKey" value="<%=creteriaEnumKey %>"/>
            <input type="hidden" name="creteriaEnum" value="<%=creteriaEnum %>"/>
            <input type="hidden" name="activeEnumKey" value="<%=activeEnumKey%>">
            <input type="hidden" name="activeEnum" value="<%=activeEnum%>">
            <input type="hidden" name="oid" value="${templateAssessSheet.oid}">
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
            <input type="hidden" name="GATE1_CNT" value="${GATE1_CNT}"/>
            <input type="hidden" name="GATE2_CNT" value="${GATE2_CNT}"/>
            <input type="hidden" name="GATE3_CNT" value="${GATE3_CNT}"/>
            <input type="hidden" name="GATE4_CNT" value="${GATE4_CNT}"/>
            <input type="hidden" name="GATE5_CNT" value="${GATE5_CNT}"/>
            <input type="hidden" name="GATE6_CNT" value="${GATE6_CNT}"/>
            <input type="hidden" name="GATE7_CNT" value="${GATE7_CNT}"/>
            <input type="hidden" name="GATE8_CNT" value="${GATE8_CNT}"/>
            <input type="hidden" name="GATE9_CNT" value="${GATE9_CNT}"/>
            <input type="hidden" name="GATE10_CNT" value="${GATE10_CNT}"/>
            <input type="hidden" name="GATE11_CNT" value="${GATE11_CNT}"/>
            <input type="hidden" name="GATE12_CNT" value="${GATE12_CNT}"/>
            <input type="hidden" name="GATE13_CNT" value="${GATE13_CNT}"/>
            <input type="hidden" name="GATE14_CNT" value="${GATE14_CNT}"/>
            <input type="hidden" name="GATE15_CNT" value="${GATE15_CNT}"/>
            <input type="hidden" name="GATE16_CNT" value="${GATE16_CNT}"/>
            <input type="hidden" name="GATE17_CNT" value="${GATE17_CNT}"/>
            <input type="hidden" name="GATE18_CNT" value="${GATE18_CNT}"/>
            <input type="hidden" name="GATE19_CNT" value="${GATE19_CNT}"/>
            <input type="hidden" name="GATE20_CNT" value="${GATE20_CNT}"/>
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
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="division" name="division" className="fm_jmp" style="width: 160px;" codeType="DIVISIONNUMBER" disabled="true" value="${templateAssessSheet.division}"/>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00653") %><%--개발유형--%></td>
                        <td width="*" class="tdwhiteL">
                            <ket:select id="devType" name="devType" className="fm_jmp" style="width: 160px;" codeType="PROJECTTYPE" disabled="true" value="${templateAssessSheet.devType}"/>
                        </td>
                        <td class="tdblueL"><div id="devDivTitle"><%=messageService.getString("e3ps.message.ket_message", "00583") %><%--개발 구분--%></div></td>
                        <td width="*" class="tdwhiteL">
	                        <div id="devDivLoc">
	                            <ket:select id="devDiv" name="devDiv" className="fm_jmp" style="width: 160px;" codeType="DEVELOPENTTYPE" disabled="true" value="${templateAssessSheet.devDiv}"/>
	                        </div>
                        </td>
                    </tr>
<%
TemplateAssessSheetDTO tmpAss = (TemplateAssessSheetDTO)request.getAttribute("templateAssessSheet");
String partOid =tmpAss.getPartOid();
String partName = "";
if(!StringUtil.isEmpty(partOid)) {
    KETPartClassification part = (KETPartClassification)CommonUtil.getObject(partOid);
	if(part!=null) partName = part.getClassKrName();
}
%>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
                        <td width="*" class="tdwhiteL">
<%--                             <ket:select id="prodCategory" name="prodCategory" className="fm_jmp" style="width: 160px;" codeType="PRODCATEGORYCODE" disabled="true" value="${templateAssessSheet.prodCategory}"/> --%>
                           <%=partName%>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07147") %><%--평가시트명--%></td>
                        <td width="*" class="tdwhiteL" colspan="3">${templateAssessSheet.sheetName}</td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td width="*" class="tdwhiteL">
                            <select id="active" name="active" className="fm_jmp" style="width: 160px;"  disabled="true">
                                <c:choose>
                                    <c:when test="${templateAssessSheet.active eq 'Y'}">
                                        <option value="Y" selected><%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></option>
                                        <option value="N"><%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></option>
                                    </c:when>
                                    <c:when test="${templateAssessSheet.active eq 'N'}">
                                        <option value="Y"><%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></option>
                                        <option value="N" selected><%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="Y"><%=messageService.getString("e3ps.message.ket_message", "07106") %><%--활성--%></option>
                                        <option value="N" selected><%=messageService.getString("e3ps.message.ket_message", "07107") %><%--비활성--%></option>
                                    </c:otherwise> 
                                </c:choose> 
                            </select>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                        <td width="*" class="tdwhiteL">
                            ${templateAssessSheet.createStamp}
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                        <td width="*" class="tdwhiteL">
                            ${templateAssessSheet.creator}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
                        <td width="*" colspan="5" class="tdwhiteL">
                        ${templateAssessSheet.sheetDesc}</td>
                    </tr>
                    
                 </table>
                 
            </form>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space5"></td>
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
