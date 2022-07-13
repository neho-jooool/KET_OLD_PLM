<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page
	import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 ext.ket.shared.query.*,
                 ext.ket.part.entity.*,
                 ext.ket.part.base.service.*,
                 ext.ket.shared.code.service.*,
                 ext.ket.project.trycondition.entity.*,
                 ext.ket.project.trycondition.service.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");
    String ViewOid = "";

    WTPart wt = null;
    if (poid != null && !poid.equals("")) {
		wt = (WTPart) KETObjectUtil.getObject(poid);
		ViewOid = CommonUtil.getOIDLongValue2Str(poid);
    }

    // 반제품과 연결된 DIE 리스트 가져오기 - KETHalbPartDieSetPartLink
    SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    List<KETHalbPartDieSetPartLink> dieList = query.queryForListLinkByRoleA(WTPartMaster.class, KETHalbPartDieSetPartLink.class, (WTPartMaster) wt.getMaster());
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
var ViewOid = <%=ViewOid%>;
function ViewPartForm(oid){
	var url = "/plm/jsp/bom/ViewPart.jsp?oid="+oid;
    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
    //var name = "halb:" + oid;
    //var name = "registHalbPartFormPopup";
    var name = ViewOid;
    var defaultWidth = 1024;
    var defaultHeight = 768;
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
}
</script>
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td width="60" class="tdgrayM">No</td>
			<td width="130" class="tdgrayM">Die No</td>
			<td width="250" class="tdgrayM">부품명</td>
			<td width="100" class="tdgrayM">Cavity</td>
			<td width="160" class="tdgrayM">목표 C/T (SPM)</td>
			<td width="110" class="tdgrayM">제작구분</td>
			<td width="110" class="tdgrayM">금형제작처</td>
			<td width="110" class="tdgrayM">금형담당자</td>
			<td width="100" class="tdgrayM">금형 Try</td>
			<td width="100" class="tdgrayM">상태</td>
		</tr>
		<%
		    if ((dieList == null) || (dieList.size() == 0)) {
		%>
		<tr>
			<td class="tdwhiteM" colspan="10">관련 금형이 없습니다.</td>
		</tr>
		<%
		    } else {
		%>
		<%
		    int dieCount = 1;
			for (KETHalbPartDieSetPartLink link : dieList) {
			    
			    WTPartMaster wtPartMast = link.getDieSet();
			    WTPart wtPart = (WTPart)e3ps.common.obj.ObjectUtil.getLatestObject(wtPartMast);
			    String partOid = KETObjectUtil.getOidString(wtPart);
			    // Cavity
			    String spCavityStd = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpCavityStd);
			    // 목표 C/T (SPM)
			    String spObjectiveCT = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpObjectiveCT);
			    // 제작구분
			    String spMMakingAt = CodeHelper.manager.getCodeValue(PartSpecEnum.SpMMakingAt.getAttrCodeType(), PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMMakingAt));
			    // 금형제작처 // 생산처
			    // 사급구분
			    String spDieWhereCode = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpDieWhere);
			    String spDieWhereName = PartBaseHelper.service.getManufacPlaceName(spDieWhereCode);
			    
			    // 금형담당자
			    String spDesignManNm = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpDesignManNm);
			    
			    // 금형 Try - 누가 해야 하는지 모름 ...  DIE와 연결된 금형 Try 정보
			    TryConditionDTO dto = new TryConditionDTO();
			    dto.setDieNo(wtPartMast.getNumber());
			    List<KETTryCondition> moldTryList = TryConditionHelper.service.find(dto);
			    String moldTry = "";
			    if(moldTryList == null || moldTryList.size() == 0){
				    moldTry = "0";
			    }else{
				    moldTry = String.valueOf(moldTryList.size());
			    }
			    
			    // 상태 
			    String stateState = State.toState(wtPart.getState().toString()).getDisplay(messageService.getLocale());
			   
		%>
		<tr>
			<td class="tdwhiteM"><%=dieCount++%></td>
			<td class="tdwhiteM"><a href="javascript:ViewPartForm('<%=partOid %>');"><%=wtPartMast.getNumber()%></a></td>
			<td class="tdwhiteM"><%=wtPartMast.getName()%></td>
			<td class="tdwhiteM"><%=spCavityStd%></td>
			<td class="tdwhiteM"><%=spObjectiveCT%></td>
			<td class="tdwhiteM"><%=spMMakingAt%></td>
			<td class="tdwhiteM"><%=spDieWhereName%></td>
			<td class="tdwhiteM"><%=spDesignManNm%></td>
			<td class="tdwhiteM"><a href="javascript:SearchUtil.openTryConditionListByDieNo('<%=wtPartMast.getNumber()%>');"><%=moldTry%></a></td>
			<td class="tdwhiteM"><%=stateState%></td>
		</tr>

		<%
		    }
		%>
		<%
		    }
		%>

	</table>
</body>
</html>