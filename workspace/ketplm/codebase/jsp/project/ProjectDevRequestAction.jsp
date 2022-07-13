<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="e3ps.project.E3PSProject"%>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="wt.httpgw.WTURLEncoder"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.dms.entity.KETDevelopmentRequest"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.dms.entity.KETRequestPartLink"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.dms.entity.KETRequestPartList"%>
<%@page import="e3ps.dms.entity.KETPartCarLink"%>
<%@page import="e3ps.dms.entity.KETCarYearlyAmount"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<stdinfo>
    <results>
        <contents>
<%

    String message = "false";
    String l_result = "";
    String command = request.getParameter("command");

    if(command == null) {
        command = "";
    }

    command = WTURLEncoder.decode(command,"euc-kr");


    //리스트 검색 .............................................................................
    if( "drSearch".equals(command)) {
        message = "true";

        String oid = request.getParameter("drOid");
        String rvProjectNos = "";
        String rvProjectOids = "";
        //접수 번호
        String drNumber = "";
        String drKeyOid = "";

        // 영업담당자 (생성자)
        String creatorName = "";
        String creatorOid = "";
        String salesUserDept = "";

        //제품 명
        String name = "";

        //제안서 제출 일자
        String proposalSubmitDate = "";

        //개발원가 제출 일자
        String costSubmitDate = "";

        // 최종 사용처
        String[] lastUsingBuyer;

        // 개발검토 의뢰처
        String[] requestBuyer;

        // 제품 정보
        String partName = "";
        String appliedRegion = "";
        String buyerAcceptPrince = "";
        String targetPrice = "";
        String targetRate = "";

        //car 정보
        String carTypeOid = "";
        String carTypeCode = "";
        String y1 = "";
        String y2 = "";
        String y3 = "";
        String y4 = "";
        String y5 = "";
        String y6 = "";
        String y7 = "";
        String y8 = "";
        String y9 = "";
        String y10 = "";
        String usage = "";
        String optionRate = "";

        if(oid != null){
            Kogger.debug("######################################################");
            //for(int drcount = 0 ; drcount < oid.length ; drcount++){
                Kogger.debug("dr : "+oid );
                KETDevelopmentRequest drObj = (KETDevelopmentRequest)CommonUtil.getObject(oid);

                drNumber = drObj.getNumber();
                rvProjectOids = drObj.getProjectOID();
                
                if(StringUtil.checkString(rvProjectOids)){
                    String rvProjectOid[] =  rvProjectOids.split(",");
                    for(String rvoid : rvProjectOid){
                        rvProjectNos += ((E3PSProject)CommonUtil.getObject(rvoid)).getPjtNo()+",";
                    }    
                }
                
                rvProjectNos = StringUtils.removeEnd(rvProjectNos, ",");
                
                drKeyOid = CommonUtil.getOIDString(drObj);
                creatorName = drObj.getCreatorFullName();
                WTUser wtuser = (WTUser)drObj.getCreator().getPrincipal();
                creatorOid = CommonUtil.getOIDString(wtuser);
                PeopleData pd = new PeopleData(wtuser);
                if(pd != null){
                    salesUserDept = pd.departmentName;
                }
                name = drObj.getName();
                if(drObj.getProposalSubmitDate() != null){
                    proposalSubmitDate = DateUtil.getDateString(drObj.getProposalSubmitDate(), "D");
                }
                if(drObj.getCostSubmitDate() != null){
                    costSubmitDate = DateUtil.getDateString(drObj.getCostSubmitDate(), "D");
                }

                String lastUsingBuyerStr = drObj.getLastUsingBuyer();
                StringTokenizer token = new StringTokenizer(lastUsingBuyerStr,",");
                 lastUsingBuyer = new String[token.countTokens()];
                 NumberCode nc = null;
                 int lub = 0;
                 %><data_custinfo><%
                 while(token.hasMoreTokens()) {
                    lastUsingBuyer[lub] = token.nextToken();
                    Kogger.debug("최종 고객사 :"+lastUsingBuyer[lub]);
                    try{
                    nc = (NumberCode)CommonUtil.getObject(lastUsingBuyer[lub]);
                    }catch(Exception e){
                	Kogger.error(e);
                        nc = null;
                    }
                    //nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", lastUsingBuyer[lub]);
                    if(nc != null){
                    %>
                    <l_custOid><![CDATA[<%=CommonUtil.getOIDString(nc)%>]]></l_custOid>
                    <l_custName><![CDATA[<%=nc.getName()%>]]></l_custName>
                    <%
                    }
                    lub++;

                }
                 %></data_custinfo><%
                String requestBuyerStr = drObj.getRequestBuyer();
                StringTokenizer token2 = new StringTokenizer(requestBuyerStr,",");
                requestBuyer = new String[token2.countTokens()];
                int rqb = 0;
                %><data_subinfo><%
                while(token2.hasMoreTokens()) {
                    requestBuyer[rqb] = token2.nextToken();
                    Kogger.debug("검토  의뢰처 :"+requestBuyer[rqb]);
                    try{
                        nc = (NumberCode)CommonUtil.getObject(requestBuyer[rqb]);
                        }catch(Exception e){
                            Kogger.error(e);
                            nc = null;
                    }
                    //nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", requestBuyer[rqb]);

                    if(nc != null){
                    %>
                    <l_subOid><![CDATA[<%=CommonUtil.getOIDString(nc)%>]]></l_subOid>
                    <l_subName><![CDATA[<%=nc.getName()%>]]></l_subName>

                    <%
                    }
                    rqb++;
                }
                %></data_subinfo><%

                QueryResult rpqr = PersistenceHelper.manager.navigate(drObj, KETRequestPartLink.ROLE_BOBJECT_ROLE, KETRequestPartLink.class);
                KETRequestPartList rpList = null;
                %><data_productinfo><%
                %><data_carinfo><%
                while(rpqr.hasMoreElements()){
                    rpList= (KETRequestPartList)rpqr.nextElement();
                    partName = rpList.getPartName();
                    appliedRegion = StringUtil.checkNull( rpList.getAppliedRegion() );
                    buyerAcceptPrince = StringUtil.checkNull(  rpList.getBuyerAcceptPrice() );
                    targetPrice = "" + rpList.getKetTargetPrice();
                    targetRate = "" + rpList.getTargetEarningRate();

                    %>

                    <l_partOid><![CDATA[<%=CommonUtil.getOIDString(rpList)%>]]></l_partOid>
                    <l_partName><![CDATA[<%=partName%>]]></l_partName>
                    <l_appliedRegion><![CDATA[<%=appliedRegion%>]]></l_appliedRegion>
                    <l_buyerAcceptPrince><![CDATA[<%=buyerAcceptPrince%>]]></l_buyerAcceptPrince>
                    <l_targetPrice><![CDATA[<%=targetPrice%>]]></l_targetPrice>
                    <l_targetRate><![CDATA[<%=targetRate%>]]></l_targetRate>
                    <%


                    KETCarYearlyAmount kya = null;
                    QueryResult rpListqr = PersistenceHelper.manager.navigate(rpList, KETPartCarLink.ROLE_BOBJECT_ROLE, KETPartCarLink.class);

                    while(rpListqr.hasMoreElements()){
                        kya = (KETCarYearlyAmount)rpListqr.nextElement();

                        carTypeOid = StringUtil.checkNull( kya.getCarType() );
                        carTypeCode = StringUtil.checkNull( kya.getCarTypeCode() );
                        if(carTypeOid.equals("0")) {
                            carTypeOid = "nodata";
                        }
                        y1 = ""+kya.getYearAmount1();
                        y2 = ""+kya.getYearAmount2();
                        y3 = ""+kya.getYearAmount3();
                        y4 = ""+kya.getYearAmount4();
                        y5 = ""+kya.getYearAmount5();
                        y6 = ""+kya.getYearAmount6();
                        y7 = ""+kya.getYearAmount7();
                        y8 = ""+kya.getYearAmount8();
                        y9 = ""+kya.getYearAmount9();
                        y10 = ""+kya.getYearAmount10();
                        if(y1.equals("null")){	y1 ="0";	}
                        if(y2.equals("null")){	y2 ="0";	}
                        if(y3.equals("null")){	y3 ="0";	}
                        if(y4.equals("null")){	y4 ="0";	}
                        if(y5.equals("null")){	y5 ="0";	}
                        if(y6.equals("null")){	y6 ="0";	}
                        if(y7.equals("null")){	y7 ="0";	}
                        if(y8.equals("null")){	y8 ="0";	}
                        if(y9.equals("null")){	y9 ="0";	}
                        if(y10.equals("null")){	y10 ="0";	}

                        usage = ""+ kya.getApplyedUsage();
                        optionRate = ""+kya.getOptionRate();
                        Kogger.debug("######################################################");
                        Kogger.debug("carinfo :" + carTypeOid +" | " + usage +" | " + optionRate);
                        Kogger.debug("y1~10 :" + y1+" | "+ y2+" | "+ y3+" | "+ y4+ " | "+y5+ " | "+y6);
                        Kogger.debug("######################################################");

                        %>
                        <l_partKeyOid><![CDATA[<%=CommonUtil.getOIDString(rpList)%>]]></l_partKeyOid>
                        <l_carTypeOid><![CDATA[<%=carTypeOid%>]]></l_carTypeOid>
                        <l_carTypeCode><![CDATA[<%=carTypeCode%>]]></l_carTypeCode>
                        <l_y1><![CDATA[<%=y1%>]]></l_y1>
                        <l_y2><![CDATA[<%=y2%>]]></l_y2>
                        <l_y3><![CDATA[<%=y3%>]]></l_y3>
                        <l_y4><![CDATA[<%=y4%>]]></l_y4>
                        <l_y5><![CDATA[<%=y5%>]]></l_y5>
                        <l_y6><![CDATA[<%=y6%>]]></l_y6>
                        <l_y7><![CDATA[<%=y7%>]]></l_y7>
                        <l_y8><![CDATA[<%=y8%>]]></l_y8>
                        <l_y9><![CDATA[<%=y9%>]]></l_y9>
                        <l_y10><![CDATA[<%=y10%>]]></l_y10>
                        <l_usage><![CDATA[<%=usage%>]]></l_usage>
                        <l_optionRate><![CDATA[<%=optionRate%>]]></l_optionRate>

                        <%
                    }


                }
                %></data_carinfo><%
                %></data_productinfo><%
            //}
            Kogger.debug("######################################################################################");

        }


%>
            <data_info>
            <l_drNumber><![CDATA[<%=drNumber%>]]></l_drNumber>
            <l_drKeyOid><![CDATA[<%=drKeyOid%>]]></l_drKeyOid>
            <l_creatorName><![CDATA[<%=creatorName%>]]></l_creatorName>
            <l_creatorOid><![CDATA[<%=creatorOid%>]]></l_creatorOid>
            <l_salesUserDept><![CDATA[<%=salesUserDept%>]]></l_salesUserDept>
            <l_name><![CDATA[<%=name%>]]></l_name>
            <l_proposalSubmitDate><![CDATA[<%=proposalSubmitDate%>]]></l_proposalSubmitDate>
            <l_costSubmitDate><![CDATA[<%=costSubmitDate%>]]></l_costSubmitDate>
            <l_rvPjtNos><![CDATA[<%=rvProjectNos%>]]></l_rvPjtNos>
            <l_rvPjtOids><![CDATA[<%=rvProjectOids%>]]></l_rvPjtOids>
            </data_info>
<%
    }
%>
            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
                <l_result><![CDATA[<%=l_result%>]]></l_result>
            </message>
        </contents>
    </results>
</stdinfo>
