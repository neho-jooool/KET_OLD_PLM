<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import = "wt.org.*,wt.session.*"%>
<%@page import ="e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.common.web.*,
                 e3ps.common.util.KETStringUtil,
                 e3ps.groupware.company.*,
            ext.ket.project.task.entity.*,
            ext.ket.project.gate.entity.*,
            ext.ket.project.gate.service.*,
            ext.ket.project.task.service.*
                 "%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />

<%
	String oid = request.getParameter("oid");
	String pjtOid = request.getParameter("pjtOid");
	String versionNo = request.getParameter("versionNo");
    Map<String, Object> parameter = new HashMap<String, Object>();
    KETParamMapUtil enumMap = null;
    String targetTypeEnumKey = "";
    String targetTypeEnum = "";
    
    AssessSheet assSheet = (AssessSheet)CommonUtil.getObject(oid);
    
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

    
    String gateExistG1 = "0";
    String gateExistG2 = "0";
    String gateExistG3 = "0";
    String gateExistG4 = "0";
    String gateExistG5 = "0";
    String gateExistG6 = "0";
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
    String gateExistG7Name = "Gate7";
    String gateExistG8Name = "Gate8";
    String gateExistG9Name = "Gate9";
    String gateExistG10Name = "Gate10";
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
    List<GateTaskOutputDTO> gateStringList = ProjectTaskCompHelper.service.getProjectGateCheckSheetList(pjtOid);
//     Vector gateLevelVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
    if (gateStringList != null) {
        for (int i = 0; i < gateStringList.size(); i++) {
            GateTaskOutputDTO gateCategoryDto = (GateTaskOutputDTO)gateStringList.get(i);
            String gateCategory = gateCategoryDto.getOutputTaskCategory();
            
            String codeName = "";
            Vector gateLevelVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
            if (gateLevelVec != null) {
	            for (int k = 0; k < gateLevelVec.size(); k++) {
	                    NumberCode code = (NumberCode) gateLevelVec.get(k);
	                    if(gateCategory!=null && gateCategory.equals(code.getName())){
	                	    codeName = code.getCode();
	                    }
	            }
            }
            
            if("G1".equals(codeName)) {
               gateExistG1 = "1";
               gateExistG1Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G2".equals(codeName)) {
               gateExistG2 = "1";
               gateExistG2Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G3".equals(codeName)) {
               gateExistG3 = "1";
               gateExistG3Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G4".equals(codeName)) {
               gateExistG4 = "1";
               gateExistG4Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G5".equals(codeName)) {
               gateExistG5 = "1";
               gateExistG5Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G6".equals(codeName)) {
               gateExistG6 = "1";
               gateExistG6Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G7".equals(codeName)) {
               gateExistG7 = "1";
               gateExistG7Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G8".equals(codeName)) {
               gateExistG8 = "1";
               gateExistG8Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G9".equals(codeName)) {
               gateExistG9 = "1";
               gateExistG9Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G10".equals(codeName)) {
               gateExistG10 = "1";
               gateExistG10Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G11".equals(codeName)) {
               gateExistG11 = "1";
               gateExistG11Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G12".equals(codeName)) {
               gateExistG12 = "1";
               gateExistG12Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G13".equals(codeName)) {
               gateExistG13 = "1";
               gateExistG13Name = gateCategoryDto.getOutputTaskName();    //
            }
            else if("G14".equals(codeName)) {
               gateExistG14 = "1";
               gateExistG14Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G15".equals(codeName)) {
               gateExistG15 = "1";
               gateExistG15Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G16".equals(codeName)) {
               gateExistG16 = "1";
               gateExistG16Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G17".equals(codeName)) {
               gateExistG17 = "1";
               gateExistG17Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G18".equals(codeName)) {
               gateExistG18 = "1";
               gateExistG18Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G19".equals(codeName)) {
               gateExistG19 = "1";
               gateExistG19Name = gateCategoryDto.getOutputTaskName();    //

            }
            else if("G20".equals(codeName)) {
               gateExistG20 = "1";
               gateExistG20Name = gateCategoryDto.getOutputTaskName();    //

            }
        }
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
<script type="text/javascript" src="/plm/extcore/js/project/gate/viewVersionedAssessSheet.js"></script>
<script type="text/javascript">

function createGateTemplate() 
{
    var rntStr = CreateTemplateAssessSheet.createTemplateGateCheckSheetList();
    alert('<%=messageService.getString("e3ps.message.ket_message", "02518") %><%--정상적으로 처리되었습니다--%>');
    opener.searchTemplateAssessSheetList();
    this.close();
}

$(document).ready(function(){
    CommonUtil.singleSelect('devType',100);
    CommonUtil.singleSelect('division',100);
    CommonUtil.singleSelect('devDiv',100);
    CommonUtil.singleSelect('prodCategory',100);

    CommonUtil.singleSelect('active',100);
    
    ViewAssessSheet.createPaingGrid();
});
</script>
<table border="0" cellpadding="0" cellspacing="0" width="900px">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
      <table width="900px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="900px" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07177") %><%--평가항목/목표--%></td>
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
      <table width="900px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
                

            <form name="ViewAssessSheetSearchForm" method="post">
            <input type="hidden" name="targetTypeEnumKey" value="<%=targetTypeEnumKey %>"/>
            <input type="hidden" name="targetTypeEnum" value="<%=targetTypeEnum %>"/>
            <input type="hidden" name="creteriaEnumKey" value="<%=creteriaEnumKey %>"/>
            <input type="hidden" name="creteriaEnum" value="<%=creteriaEnum %>"/>
            <input type="hidden" name="oid" value="<%=oid %>"/>
            <input type="hidden" name="versionNo" value="<%=versionNo %>"/>
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
                <table border="0" cellspacing="0" cellpadding="0" width="900">
                    <tr>
                        <td height="30" align="left">
                          Rev. : ${ASS_VERSION} , &nbsp;&nbsp;
                          <%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일 --%> : ${CREATE_DATE} , &nbsp;&nbsp;
                          <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태 --%> : 
<%
    if(assSheet!=null)
    {
%>
                    <a href="javascript:viewHistory('<%=(CommonUtil.getOIDString(assSheet))%>')">${ASS_STATE_NAME}</a>
<%
    }else {
%>
                    ${ASS_STATE_NAME} 
<%
    }
%>
                          
                        </td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                    <a href="javascript:this.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
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
                
          </td>
        </tr>
      </table>

            </form>
            <table border="0" cellspacing="0" cellpadding="0" width="900">
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
