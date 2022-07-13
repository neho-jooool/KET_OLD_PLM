<?xml version="1.0" encoding="UTF-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.fc.*,
                wt.httpgw.*,
                wt.org.*,
                wt.project.Role,
                wt.query.*,
                wt.team.*
                "%>
<%@page import="e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.*,
                e3ps.groupware.company.*,
                e3ps.project.*,
                e3ps.project.beans.*
                "%>
                
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
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
    Kogger.debug("command : " +command);

    if("checkProject".equals(command)) {
/*    String projectNo = request.getParameter("projectNo");
        projectNo= WTURLEncoder.decode(projectNo,"euc-kr");
        //boolean flag = ProjectHelper.manager.checkProjectNo(projectNo);
        //if(flag) {
            //message = "true";
        //}*/
        message = "false";
    } else if("create".equals(command) || "update".equals(command)) {
        String initType = request.getParameter("initType");
        String projectType = request.getParameter("projectType");
        String projectState = request.getParameter("projectState");
        String planStartDate = request.getParameter("planStartDate");
        String planEndDate = request.getParameter("planEndDate");
        String projectDesc = request.getParameter("projectDesc");
        String templateOid = request.getParameter("templateOid");
        String pwlinkOid = request.getParameter("pwlinkOid");
        String userMemberArr[] = request.getParameterValues("userMember");

        String levelCode = "";//request.getParameter("levelcode");
        String productCode = "";//request.getParameter("productcode");
        String customerCode = "";//request.getParameter("customercode");
        String devcompanyCode = "";//request.getParameter("devcompanycode");
        String makecompanyCode = "";//request.getParameter("makecompanycode");
        String modelCode = request.getParameter("modelcode");

        String teamType = request.getParameter("teamType");                //사업부
        String process = request.getParameter("process");                    //Process
        String sales = request.getParameter("sales");                      //영업담당자
        String devManager = request.getParameter("devManager");              //개발담당자
        String department = request.getParameter("department");              //부서
        String partNo = request.getParameter("partNo");                    //Part No
        String productType = request.getParameter("productType");              //제품구분
        String rank = request.getParameter("rank");                      //Rank
        String model = request.getParameter("model");                    //대표차종
        String assembledType = request.getParameter("assembledType");          //조립구분
        String developentType = request.getParameter("developentType");          //개발구분
        String designType = request.getParameter("designType");              //설계구분
        String assemblyPlace = request.getParameter("assemblyPlace");            //조립처
        String customer[] = request.getParameterValues("CUSTOMEREVENTOid");      //최종 사용처
        String dependence[] = request.getParameterValues("SUBCONTRACTOROid");    //개발검토 의뢰처
        String costsDate = request.getParameter("costsDate");                //개발원가 제출일
        //Item 현황
        String itemType[] = request.getParameterValues("itemType");
        String moldProductType[] = request.getParameterValues("moldProductType");
        String moldPartNo[] = request.getParameterValues("moldPartNo");
        String partName[] = request.getParameterValues("partName");
        String dieNo[] = request.getParameterValues("dieNo");
        String moldType[] = request.getParameterValues("moldType");
        String cVPitch[] = request.getParameterValues("cVPitch");
        String cTSPM[] = request.getParameterValues("cTSPM");
        String making[] = request.getParameterValues("making");
        String productionPlace[] = request.getParameterValues("productionPlace");
        String productionPlace2[] = request.getParameterValues("productionPlace2");
        String materials[] = request.getParameterValues("materials");
        String poidvalue[] = request.getParameterValues("poidvalue");
        String height[] = request.getParameterValues("height");
        String wide[] = request.getParameterValues("wide");
        //비용관리
        String costType[] = request.getParameterValues("costType");
        String partNoCost[] = request.getParameterValues("partNoCost");
        String dieNoCost[] = request.getParameterValues("dieNoCost");
        String moldTypeCost[] = request.getParameterValues("moldTypeCost");
        String costName[] = request.getParameterValues("costName");
        String order[] = request.getParameterValues("order");
        String targetCost[] = request.getParameterValues("targetCost");
        String executionCost[] = request.getParameterValues("executionCost");
        String editCost[] = request.getParameterValues("editCost");

        String projectoid[] = request.getParameterValues("projectoid");

        String lifecycle = request.getParameter("lifecycle");
        String pmoid = request.getParameter("pmoid");

        String projectName = request.getParameter("projectName");
        String dsProjectOid = request.getParameter("dsProjectOid");

        pmoid = WTURLEncoder.decode(pmoid==null?"":pmoid);
        if(projectName != null){
            projectName = WTURLEncoder.decode(projectName==null?"":projectName);
        }else{
            projectName = "";
        }

        e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();

        initType = WTURLEncoder.decode(initType==null?"":initType);
        projectType = WTURLEncoder.decode(projectType==null?"":projectType);

        String projectNo = null;
        String teamTypeCode = "";
        if("자동차 사업부".equals(teamType))
            teamTypeCode = "C";
        else
            teamTypeCode = "E";
        String tempDate = DateUtil.getDateString(new Date(), "all");
        String processCode = "";
        if(process != null && process.length() > 0) {
               NumberCode code = NumberCodeHelper.manager.getNumberCode("PROCESS", process);
               if("Proto".equals(code.getName()))
                   processCode = "T";
               else if("Pilot".equals(code.getName()))
                   processCode = "B";
               else
                   processCode = "A";
           }
        projectNo = teamTypeCode + tempDate.substring(2, 4) + processCode;
        projectNo = projectNo + ManageSequence.getSeqNo(projectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);

        //projectName = WTURLEncoder.decode(projectName==null?"":projectName);
        projectState = WTURLEncoder.decode(projectState==null?"":projectState);
        planStartDate = WTURLEncoder.decode(planStartDate==null?"":planStartDate);
        planEndDate = WTURLEncoder.decode(planEndDate==null?"":planEndDate);
        projectDesc = WTURLEncoder.decode(projectDesc==null?"":projectDesc);
        templateOid = WTURLEncoder.decode(templateOid==null?"":templateOid);
        pwlinkOid = WTURLEncoder.decode(pwlinkOid==null?"":pwlinkOid);
        //divisionCode = WTURLEncoder.decode(divisionCode==null?"":divisionCode);
        levelCode = WTURLEncoder.decode(levelCode==null?"":levelCode);
        productCode = WTURLEncoder.decode(productCode==null?"":productCode);
        customerCode = WTURLEncoder.decode(customerCode==null?"":customerCode);
        devcompanyCode = WTURLEncoder.decode(devcompanyCode==null?"":devcompanyCode);
        makecompanyCode = WTURLEncoder.decode(makecompanyCode==null?"":makecompanyCode);
        modelCode = WTURLEncoder.decode(modelCode==null?"":modelCode);

        teamType = WTURLEncoder.decode(teamType==null?"":teamType);              //사업부
        process = WTURLEncoder.decode(process==null?"":process);                //Process
        sales = WTURLEncoder.decode(sales==null?"":sales);                      //영업담당자
        devManager = WTURLEncoder.decode(devManager==null?"":devManager);        //개발담당자
        department = WTURLEncoder.decode(department==null?"":department);          //부서
        partNo = WTURLEncoder.decode(partNo==null?"":partNo);                  //Part No
        productType = WTURLEncoder.decode(productType==null?"":productType);        //제품구분
        rank = WTURLEncoder.decode(rank==null?"":rank);                      //Rank
        model = WTURLEncoder.decode(model==null?"":model);                    //대표차종
        assembledType = WTURLEncoder.decode(assembledType==null?"":assembledType);  //조립구분
        developentType = WTURLEncoder.decode(developentType==null?"":developentType);  //개발구분
        designType = WTURLEncoder.decode(designType==null?"":designType);          //설계구분
        assemblyPlace = WTURLEncoder.decode(assemblyPlace==null?"":assemblyPlace);      //조립처
        costsDate = WTURLEncoder.decode(costsDate==null?"":costsDate);              //개발원가 제출일
        //최종 사용처
        Vector customerVec = new Vector();
        if(customer != null && customer.length > 0) {
            for(int i = 0; i < customer.length; i++) {
                customerVec.add(WTURLEncoder.decode(customer[i]));
            }
        }
        //개발검토 의뢰처
        Vector dependenceVec = new Vector();
        if(dependence != null && dependence.length > 0) {
            for(int i = 0; i < dependence.length; i++) {
                dependenceVec.add(WTURLEncoder.decode(dependence[i]));
            }
        }

        //Item 현황
        Vector itemTypeVec = new Vector();
        if(itemType != null && itemType.length > 0) {
            for(int i = 0; i < itemType.length; i++) {
//        itemTypeVec.add(WTURLEncoder.decode(itemType[i]));
                itemTypeVec.add(itemType[i]);
            }
        }

        Vector moldProductTypeVec = new Vector();
        if(moldProductType != null && moldProductType.length > 0) {
            for(int i = 0; i < moldProductType.length; i++) {
//        moldProductTypeVec.add(WTURLEncoder.decode(moldProductType[i]));
                moldProductTypeVec.add(moldProductType[i]);
            }
        }

        Vector moldPartNoVec = new Vector();
        if(moldPartNo != null && moldPartNo.length > 0) {
            for(int i = 0; i < moldPartNo.length; i++) {
//        moldPartNoVec.add(WTURLEncoder.decode(moldPartNo[i]));
                moldPartNoVec.add(moldPartNo[i]);
            }
        }

        Vector partNameVec = new Vector();
        if(partName != null && partName.length > 0) {
            for(int i = 0; i < partName.length; i++) {
//        partNameVec.add(WTURLEncoder.decode(partName[i]));
                partNameVec.add(partName[i]);
            }
        }

        Vector dieNoVec = new Vector();
        if(dieNo != null && dieNo.length > 0) {
            for(int i = 0; i < dieNo.length; i++) {
//        dieNoVec.add(WTURLEncoder.decode(dieNo[i]));
                dieNoVec.add(dieNo[i]);
            }
        }

        Vector moldTypeVec = new Vector();
        if(moldType != null && moldType.length > 0) {
            for(int i = 0; i < moldType.length; i++) {
//        moldTypeVec.add(WTURLEncoder.decode(moldType[i]));
                moldTypeVec.add(moldType[i]);
            }
        }

        Vector cVPitchVec = new Vector();
        if(cVPitch != null && cVPitch.length > 0) {
            for(int i = 0; i < cVPitch.length; i++) {
//        cVPitchVec.add(WTURLEncoder.decode(cVPitch[i]));
                cVPitchVec.add(cVPitch[i]);
            }
        }

        Vector cTSPMVec = new Vector();
        if(cTSPM != null && cTSPM.length > 0) {
            for(int i = 0; i < cTSPM.length; i++) {
//        cTSPMVec.add(WTURLEncoder.decode(cTSPM[i]));
                cTSPMVec.add(cTSPM[i]);
            }
        }

        Vector makingVec = new Vector();
        if(making != null && making.length > 0) {
            for(int i = 0; i < making.length; i++) {
//        makingVec.add(WTURLEncoder.decode(making[i]));
                makingVec.add(making[i]);
            }
        }

        Vector productionPlaceVec = new Vector();
        if(productionPlace != null && productionPlace.length > 0) {
            for(int i = 0; i < productionPlace.length; i++) {
//        productionPlaceVec.add(WTURLEncoder.decode(productionPlace[i]));
                productionPlaceVec.add(productionPlace[i]);
            }
        }

        Vector productionPlace2Vec = new Vector();
        if(productionPlace2 != null && productionPlace2.length > 0) {
            for(int i = 0; i < productionPlace2.length; i++) {
//        productionPlace2Vec.add(WTURLEncoder.decode(productionPlace2[i]));
                productionPlace2Vec.add(productionPlace2[i]);
            }
        }

        Vector materialsVec = new Vector();
        if(materials != null && materials.length > 0) {
            for(int i = 0; i < materials.length; i++) {
//        materialsVec.add(WTURLEncoder.decode(materials[i]));
                materialsVec.add(materials[i]);
            }
        }

        Vector poidvalueVec = new Vector();
        if(poidvalue != null && poidvalue.length > 0) {
        Kogger.debug("------->>>"+poidvalue);
            for(int i = 0; i < poidvalue.length; i++) {
        Kogger.debug("materials[i]------->>>"+poidvalue[i]);
                poidvalueVec.add(poidvalue[i]);
            }
        }
        Vector heightVec = new Vector();
        if(height != null && height.length > 0) {
            for(int i = 0; i < height.length; i++) {
                heightVec.add(height[i]);
            }
        }
        Vector wideVec = new Vector();
        if(wide != null && wide.length > 0) {
            for(int i = 0; i < wide.length; i++) {
                wideVec.add(wide[i]);
            }
        }

        lifecycle = WTURLEncoder.decode(lifecycle==null?"":lifecycle);
        pmoid = WTURLEncoder.decode(pmoid==null?"":pmoid);
        dsProjectOid = WTURLEncoder.decode(dsProjectOid==null?"":dsProjectOid);
        Vector userMemberVec = new Vector();
        if(userMemberArr != null && userMemberArr.length > 0) {
            for(int i = 0; i < userMemberArr.length; i++) {
                userMemberVec.add(WTURLEncoder.decode(userMemberArr[i]));
            }
        }

        //비용 관리
        Vector costTypeVec = new Vector();
        if(costType != null && costType.length > 0) {
            for(int i = 0; i < costType.length; i++) {
//        costTypeVec.add(WTURLEncoder.decode(costType[i]));
                costTypeVec.add(costType[i]);
            }
        }

        Vector partNoCostVec = new Vector();
        if(partNoCost != null && partNoCost.length > 0) {
            for(int i = 0; i < partNoCost.length; i++) {
//        partNoCostVec.add(WTURLEncoder.decode(partNoCost[i]));
                partNoCostVec.add(partNoCost[i]);
            }
        }

        Vector dieNoCostVec = new Vector();
        if(dieNoCost != null && dieNoCost.length > 0) {
            for(int i = 0; i < dieNoCost.length; i++) {
//        dieNoCostVec.add(WTURLEncoder.decode(dieNoCost[i]));
                dieNoCostVec.add(dieNoCost[i]);
            }
        }

        Vector moldTypeCostVec = new Vector();
        if(moldTypeCost != null && moldTypeCost.length > 0) {
            for(int i = 0; i < moldTypeCost.length; i++) {
//        moldTypeCostVec.add(WTURLEncoder.decode(moldTypeCost[i]));
                moldTypeCostVec.add(moldTypeCost[i]);
            }
        }

        Vector costNameVec = new Vector();
        if(costName != null && costName.length > 0) {
            for(int i = 0; i < costName.length; i++) {
//        costNameVec.add(WTURLEncoder.decode(costName[i]));
                costNameVec.add(costName[i]);
            }
        }

        Vector orderVec = new Vector();
        if(order != null && order.length > 0) {
            for(int i = 0; i < order.length; i++) {
                orderVec.add(WTURLEncoder.decode(order[i]));
                orderVec.add(order[i]);
            }
        }

        Vector targetCostVec = new Vector();
        if(targetCost != null && targetCost.length > 0) {
            for(int i = 0; i < targetCost.length; i++) {
                targetCostVec.add(WTURLEncoder.decode(targetCost[i]));
                targetCostVec.add(targetCost[i]);
            }
        }

        Vector executionCostVec = new Vector();
        if(executionCost != null && executionCost.length > 0) {
            for(int i = 0; i < executionCost.length; i++) {
                executionCostVec.add(WTURLEncoder.decode(executionCost[i]));
                executionCostVec.add(targetCost[i]);
            }
        }

        Vector editCostVec = new Vector();
        if(editCost != null && editCost.length > 0) {
            for(int i = 0; i < editCost.length; i++) {
                editCostVec.add(WTURLEncoder.decode(editCost[i]));
                editCostVec.add(targetCost[i]);
            }
        }

        //관련 Project
        Vector projectOidVec = new Vector();
        if(projectoid != null && projectoid.length > 0) {
            for(int i = 0; i < projectoid.length; i++) {
                projectOidVec.add(WTURLEncoder.decode(projectoid[i]));
            }
        }

        Hashtable hash = new Hashtable();
        hash.put("projectType", projectType);            //프로젝트 종류
        hash.put("projectNo", projectNo);                 //ProjectNO
        hash.put("projectName", projectName);              //프로젝트 명
        hash.put("planStartDate", planStartDate);             //계획 시작일자

        hash.put("planEndDate", planEndDate);               //계획 종료일자
        hash.put("projectDesc", projectDesc);               //설명

        hash.put("teamType", teamType);          //사업부
        hash.put("sales", sales);                //영업담당자
        hash.put("devManager", devManager);      //개발담당자
        hash.put("department", department);        //부서
        hash.put("partNo", partNo);            //Part No
        hash.put("productType", productType);      //제품구분
        hash.put("rank", rank);                //Rank
        hash.put("model", model);              //대표차종
        hash.put("assembledType", assembledType);  //조립구분
        hash.put("process", process);            //Process
        hash.put("developentType", developentType);  //개발구분
        hash.put("designType", designType);        //설계구분
        hash.put("assemblyPlace", assemblyPlace);    //조립처
        hash.put("customer", customerVec);        //최종 사용처
        hash.put("dependence", dependenceVec);    //개발검토 의뢰처
        hash.put("costsDate", costsDate);          //개발원가 제출일
        //Item 현황
        hash.put("itemType", itemTypeVec);
        hash.put("moldProductType", moldProductTypeVec);
        hash.put("moldPartNo", moldPartNoVec);
        hash.put("partName", partNameVec);
        hash.put("dieNo", dieNoVec);
        hash.put("moldType", moldTypeVec);
        hash.put("cVPitch", cVPitchVec);
        hash.put("cTSPM", cTSPMVec);
        hash.put("making", makingVec);
        hash.put("productionPlace", productionPlaceVec);
        hash.put("productionPlace2", productionPlace2Vec);
        hash.put("materials", materialsVec);
        hash.put("poidvalue", poidvalueVec);
        hash.put("height", heightVec);
        hash.put("wide", wideVec);
        //비용 관리
        hash.put("costType", costTypeVec);
        hash.put("partNoCost", partNoCostVec);
        hash.put("dieNoCost", dieNoCostVec);
        hash.put("moldTypeCost", moldTypeCostVec);
        hash.put("costName", costNameVec);
        hash.put("order", orderVec);
        hash.put("targetCost", targetCostVec);
        hash.put("executionCost", executionCostVec);
        hash.put("editCost", editCostVec);
        hash.put("projectoid", projectOidVec);      //관련 Project

        hash.put("TemplateOID", templateOid);
        hash.put("pwlinkOid", pwlinkOid);

        hash.put("projectState", projectState);

        hash.put("USERMEMBER", userMemberVec);

        hash.put("dsProjectOid",dsProjectOid);

//    제품정보
        if(request.getParameterValues("rowId") !=null){
            String rowId[] = request.getParameterValues("rowId");
            Vector rowIdVec = new Vector();
            if(rowId != null && rowId.length > 0) {
                for(int i = 0; i < rowId.length; i++) {
                    rowIdVec.add(WTURLEncoder.decode(rowId[i]));

                    if(request.getParameterValues("count"+rowId[i]) !=null){
                        String count[] = request.getParameterValues("count"+rowId[i]);
                        Vector countVec = new Vector();
                        if(count != null && count.length > 0) {
                            for(int j = 0; j < count.length; j++) {
                                countVec.add(WTURLEncoder.decode(count[j]));
                            }
                        }
                        hash.put("countVec"+rowId[i], countVec);
                    }

                        Kogger.debug("optOidoptOidoptOidoptOidoptOidoptOidoptOid7777777"+request.getParameterValues("optOid"+rowId[i]));
                    if(request.getParameterValues("optOid"+rowId[i]) !=null){
                        String optOid[] = request.getParameterValues("optOid"+rowId[i]);
                        Kogger.debug("optOidoptOidoptOidoptOidoptOidoptOidoptOid7777777"+optOid);
                        Vector optOidVec = new Vector();
                        if(optOid != null && optOid.length > 0) {
                            for(int j = 0; j < optOid.length ; j++) {
                                optOidVec.add(WTURLEncoder.decode(optOid[j]));
                            }
                        }
                        hash.put("optOidVec"+rowId[i], optOidVec);
                    }

                    if(request.getParameterValues("y1"+rowId[i]) !=null){
                        String y1[] = request.getParameterValues("y1"+rowId[i]);
                        Vector y1Vec = new Vector();
                        if(y1 != null && y1.length > 0) {
                            for(int j = 0; j < y1.length; j++) {
                                y1Vec.add(WTURLEncoder.decode(y1[j]));
                            }
                        }
                        hash.put("y1Vec"+rowId[i], y1Vec);
                    }

                    if(request.getParameterValues("y2"+rowId[i]) !=null){
                        String y2[] = request.getParameterValues("y2"+rowId[i]);
                        Vector y2Vec = new Vector();
                        if(y2 != null && y2.length > 0) {
                            for(int j = 0; j < y2.length; j++) {
                                y2Vec.add(WTURLEncoder.decode(y2[j]));
                            }
                    }
                    hash.put("y2Vec"+rowId[i], y2Vec);
                }

                if(request.getParameterValues("y3"+rowId[i]) !=null){
                    String y3[] = request.getParameterValues("y3"+rowId[i]);
                    Vector y3Vec = new Vector();
                    if(y3 != null && y3.length > 0) {
                        for(int j = 0; j < y3.length; j++) {
                            y3Vec.add(WTURLEncoder.decode(y3[j]));
                        }
                    }
                    hash.put("y3Vec"+rowId[i], y3Vec);
                }

                if(request.getParameterValues("y4"+rowId[i]) !=null){
                    String y4[] = request.getParameterValues("y4"+rowId[i]);
                    Vector y4Vec = new Vector();
                    if(y4 != null && y4.length > 0) {
                        for(int j = 0; j < y4.length; j++) {
                            y4Vec.add(WTURLEncoder.decode(y4[j]));
                        }
                    }
                    hash.put("y4Vec"+rowId[i], y4Vec);
                }

                if(request.getParameterValues("y5"+rowId[i]) !=null){
                    String y5[] = request.getParameterValues("y5"+rowId[i]);
                    Vector y5Vec = new Vector();
                    if(y5 != null && y5.length > 0) {
                        for(int j = 0; j < y5.length; j++) {
                            y5Vec.add(WTURLEncoder.decode(y5[j]));
                        }
                    }
                    hash.put("y5Vec"+rowId[i], y5Vec);
                }

                if(request.getParameterValues("y6"+rowId[i]) !=null){
                    String y6[] = request.getParameterValues("y6"+rowId[i]);
                    Vector y6Vec = new Vector();
                    if(y6 != null && y6.length > 0) {
                        for(int j = 0; j < y6.length; j++) {
                            y6Vec.add(WTURLEncoder.decode(y6[j]));
                        }
                    }
                    hash.put("y6Vec"+rowId[i], y6Vec);
                }

                if(request.getParameterValues("y7"+rowId[i]) !=null){
                    String y7[] = request.getParameterValues("y7"+rowId[i]);
                    Vector y7Vec = new Vector();
                    if(y7 != null && y7.length > 0) {
                        for(int j = 0; j < y7.length; j++) {
                            y7Vec.add(WTURLEncoder.decode(y7[j]));
                        }
                    }
                    hash.put("y7Vec"+rowId[i], y7Vec);
                }

                if(request.getParameterValues("y8"+rowId[i]) !=null){
                    String y8[] = request.getParameterValues("y8"+rowId[i]);
                    Vector y8Vec = new Vector();
                    if(y8 != null && y8.length > 0) {
                        for(int j = 0; j < y8.length; j++) {
                            y8Vec.add(WTURLEncoder.decode(y8[j]));
                        }
                    }
                    hash.put("y8Vec"+rowId[i], y8Vec);
                }

                if(request.getParameterValues("y9"+rowId[i]) !=null){
                    String y9[] = request.getParameterValues("y9"+rowId[i]);
                    Vector y9Vec = new Vector();
                    if(y9 != null && y9.length > 0) {
                        for(int j = 0; j < y9.length; j++) {
                            y9Vec.add(WTURLEncoder.decode(y9[j]));
                        }
                    }
                    hash.put("y9Vec"+rowId[i], y9Vec);
                }

                if(request.getParameterValues("y10"+rowId[i]) !=null){
                    String y10[] = request.getParameterValues("y10"+rowId[i]);
                    Vector y10Vec = new Vector();
                    if(y10 != null && y10.length > 0) {
                        for(int j = 0; j < y10.length; j++) {
                            y10Vec.add(WTURLEncoder.decode(y10[j]));
                        }
                    }
                    hash.put("y10Vec"+rowId[i], y10Vec);
                }

                if(request.getParameterValues("usage"+rowId[i]) !=null){
                    String usage[] = request.getParameterValues("usage"+rowId[i]);
                    Vector usageVec = new Vector();
                    if(usage != null && usage.length > 0) {
                        for(int j = 0; j < usage.length; j++) {
//              Kogger.debug("usage[j]---"+usage[j]);
                            usageVec.add(WTURLEncoder.decode(usage[j]));
                        }
                    }
                    hash.put("usageVec"+rowId[i], usageVec);
                }

                if(request.getParameterValues("optionRate"+rowId[i]) !=null){
                    String optionRate[] = request.getParameterValues("optionRate"+rowId[i]);
                    Vector optionRateVec = new Vector();
                    if(optionRate != null && optionRate.length > 0) {
                        for(int j = 0; j < optionRate.length; j++) {
                            optionRateVec.add(WTURLEncoder.decode(optionRate[j]));
                        }
                    }
                    hash.put("optionRateVec"+rowId[i], optionRateVec);
                }



                    if(request.getParameter("pOid"+rowId[i]) !=null){
                        String pOid = request.getParameter("pOid"+rowId[i]);
                        hash.put("pOidVec"+rowId[i], pOid);
                    }

                }
            }
            hash.put("rowIdVec", rowIdVec);
        }

        if(request.getParameterValues("pNum") !=null){
            String pNum[] = request.getParameterValues("pNum");
            Vector pNumVec = new Vector();
            if(pNum != null && pNum.length > 0) {
                for(int i = 0; i < pNum.length; i++) {
                    pNumVec.add(pNum[i]);
                }
            }
            hash.put("pNumVec", pNumVec);
        }

        if(request.getParameterValues("pName") !=null){
            String pName[] = request.getParameterValues("pName");
            Vector pNameVec = new Vector();
            if(pName != null && pName.length > 0) {
                for(int i = 0; i < pName.length; i++) {
                    pNameVec.add(pName[i]);
                }
            }
            hash.put("pNameVec", pNameVec);
        }

        if(request.getParameterValues("areas") !=null){
            String areas[] = request.getParameterValues("areas");
            Vector areasVec = new Vector();
            if(areas != null && areas.length > 0) {
                for(int i = 0; i < areas.length; i++) {
                    areasVec.add(areas[i]);
                }
            }
            hash.put("areasVec", areasVec);
        }

        if(request.getParameterValues("carName") !=null){
            String carName[] = request.getParameterValues("carName");
            Vector carNameVec = new Vector();
            if(carName != null && carName.length > 0) {
                for(int i = 0; i < carName.length; i++) {
                    carNameVec.add(carName[i]);
                }
            }
            hash.put("carNameVec", carNameVec);
        }

        if(request.getParameterValues("usageT") !=null){
            String usageT[] = request.getParameterValues("usageT");
            Vector usageTVec = new Vector();
            if(usageT != null && usageT.length > 0) {
                for(int i = 0; i < usageT.length; i++) {
                    usageTVec.add(WTURLEncoder.decode(usageT[i]));
                }
            }
            hash.put("usageTVec", usageTVec);
        }

        if(request.getParameterValues("price") !=null){
            String price[] = request.getParameterValues("price");
            Vector priceVec = new Vector();
            if(price != null && price.length > 0) {
                for(int i = 0; i < price.length; i++) {
                    priceVec.add(WTURLEncoder.decode(price[i]));
                }
            }
            hash.put("priceVec", priceVec);
        }

        if(request.getParameterValues("cost") !=null){
            String cost[] = request.getParameterValues("cost");
            Vector costVec = new Vector();
            if(cost != null && cost.length > 0) {
                for(int i = 0; i < cost.length; i++) {
                    costVec.add(WTURLEncoder.decode(cost[i]));
                }
            }
            hash.put("costVec", costVec);
        }

        if(request.getParameterValues("rate") !=null){
            String rate[] = request.getParameterValues("rate");
            Vector rateVec = new Vector();
            if(rate != null && rate.length > 0) {
                for(int i = 0; i < rate.length; i++) {
                    rateVec.add(WTURLEncoder.decode(rate[i]));
                }
            }
            hash.put("rateVec", rateVec);
        }

        if(request.getParameterValues("y1T") !=null){
            String y1T[] = request.getParameterValues("y1T");
            Vector y1TVec = new Vector();
            if(y1T != null && y1T.length > 0) {
                for(int i = 0; i < y1T.length; i++) {
                    y1TVec.add(WTURLEncoder.decode(y1T[i]));
                }
            }
            hash.put("y1TVec", y1TVec);
        }

        if(request.getParameterValues("y2T") !=null){
            String y2T[] = request.getParameterValues("y2T");
            Vector y2TVec = new Vector();
            if(y2T != null && y2T.length > 0) {
                for(int i = 0; i < y2T.length; i++) {
                    y2TVec.add(WTURLEncoder.decode(y2T[i]));
                }
            }
            hash.put("y2TVec", y2TVec);
        }

        if(request.getParameterValues("y3T") !=null){
            String y3T[] = request.getParameterValues("y3T");
            Vector y3TVec = new Vector();
            if(y3T != null && y3T.length > 0) {
                for(int i = 0; i < y3T.length; i++) {
                    y3TVec.add(WTURLEncoder.decode(y3T[i]));
                }
            }
            hash.put("y3TVec", y3TVec);
        }

        if(request.getParameterValues("y4T") !=null){
            String y4T[] = request.getParameterValues("y4T");
            Vector y4TVec = new Vector();
            if(y4T != null && y4T.length > 0) {
                for(int i = 0; i < y4T.length; i++) {
                    y4TVec.add(WTURLEncoder.decode(y4T[i]));
                }
            }
            hash.put("y4TVec", y4TVec);
        }

        if(request.getParameterValues("y5T") !=null){
            String y5T[] = request.getParameterValues("y5T");
            Vector y5TVec = new Vector();
            if(y5T != null && y5T.length > 0) {
                for(int i = 0; i < y5T.length; i++) {
                    y5TVec.add(WTURLEncoder.decode(y5T[i]));
                }
            }
            hash.put("y5TVec", y5TVec);
        }

        if(request.getParameterValues("y6T") !=null){
            String y6T[] = request.getParameterValues("y6T");
            Vector y6TVec = new Vector();
            if(y6T != null && y6T.length > 0) {
                for(int i = 0; i < y6T.length; i++) {
                    y6TVec.add(WTURLEncoder.decode(y6T[i]));
                }
            }
            hash.put("y6TVec", y6TVec);
        }

        if(request.getParameterValues("y7T") !=null){
            String y7T[] = request.getParameterValues("y7T");
            Vector y7TVec = new Vector();
            if(y7T != null && y7T.length > 0) {
                for(int i = 0; i < y7T.length; i++) {
                    y7TVec.add(WTURLEncoder.decode(y7T[i]));
                }
            }
            hash.put("y7TVec", y7TVec);
        }

        if(request.getParameterValues("y8T") !=null){
            String y8T[] = request.getParameterValues("y8T");
            Vector y8TVec = new Vector();
            if(y8T != null && y8T.length > 0) {
                for(int i = 0; i < y8T.length; i++) {
                    y8TVec.add(WTURLEncoder.decode(y8T[i]));
                }
            }
            hash.put("y8TVec", y8TVec);
        }

        if(request.getParameterValues("y9T") !=null){
            String y9T[] = request.getParameterValues("y9T");
            Vector y9TVec = new Vector();
            if(y9T != null && y9T.length > 0) {
                for(int i = 0; i < y9T.length; i++) {
                    y9TVec.add(WTURLEncoder.decode(y9T[i]));
                }
            }
            hash.put("y9TVec", y9TVec);
        }

        if(request.getParameterValues("y10T") !=null){
            String y10T[] = request.getParameterValues("y10T");
            Vector y10TVec = new Vector();
            if(y10T != null && y10T.length > 0) {
                for(int i = 0; i < y10T.length; i++) {
                    y10TVec.add(WTURLEncoder.decode(y10T[i]));
                }
            }
            hash.put("y10TVec", y10TVec);
        }

        if(request.getParameter("deletePOid") !=null){
            String deletePOid = request.getParameter("deletePOid");
            hash.put("deletePOid", deletePOid);
        }

        Role role = null;
        String roleUser = null;

        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
        Vector vecTeamStd = tempTeam.getRoles();
        for(int i = 0; i < vecTeamStd.size(); i++) {
            role = (Role)vecTeamStd.get(i);
            roleUser = request.getParameter(role.toString());
            if(roleUser == null) {
                roleUser = "";
            }
            roleUser = WTURLEncoder.decode(roleUser,"euc-kr");
            hash.put(role.toString(), roleUser);
        }

        hash.put("lifecycle", lifecycle);

        if(pmoid.length() > 0 ){
            hash.put("pmoid", pmoid);
        }
        E3PSProject project = null;

        try {
            if("create".equals(command))
                project = E3PSProjectHelper.service.createE3PSProject(hash);
            else {
                String oid = request.getParameter("oid");
                oid = WTURLEncoder.decode(oid==null?"":oid);
                hash.put("oid", oid);

                project = E3PSProjectHelper.service.updateE3PSProject(hash);
            }

            if(project != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=project.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }

        } catch (Exception e) {
            Kogger.error(e);
        }

        //Project Create End
    } else if("addMember".equals(command)) {
        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid);

        String userOidArr[] = request.getParameterValues("userOid");
        Vector userMemberVec = new Vector();
        if(userOidArr != null && userOidArr.length > 0) {
            for(int i = 0; i < userOidArr.length; i++) {
                userMemberVec.add(WTURLEncoder.decode(userOidArr[i]));
            }
        }


        HashMap  map = new HashMap();
        map.put("oid", oid);
        map.put("MEMBER", userMemberVec);

        boolean flag = ProjectHelper.assignProjectMember(map);
        if(flag) {
            message = "true";
        }

    }
    else if("addRefDept".equals(command)) {
        String oid = request.getParameter("oid");
        String deptOid = request.getParameter("deptOid");
        oid = WTURLEncoder.decode(oid==null?"":oid);
        deptOid = WTURLEncoder.decode(deptOid==null?"":deptOid);

        ReferenceFactory rf = new ReferenceFactory();
        TemplateProject project = (TemplateProject)rf.getReference(oid).getObject();

        ArrayList list = new ArrayList();
        list.add(rf.getReference(deptOid).getObject());

        boolean flag = ProjectHelper.addRefDepartment(project, list);
        if(flag) {
            message = "true";
        }
    }
    else if("addTaskRefDept".equals(command)){

        //Kogger.debug("########## command =" +command);
        String oid = request.getParameter("oid");
        String deptOid = request.getParameter("deptOid");
        oid = WTURLEncoder.decode(oid==null ? "":oid);
        deptOid = WTURLEncoder.decode(deptOid==null?"":deptOid);

        ReferenceFactory rf = new ReferenceFactory();
        TemplateTask task = (TemplateTask)rf.getReference(oid).getObject();
        TemplateProject project = task.getProject();
        Department depart = (Department)CommonUtil.getObject(deptOid);
        //Kogger.debug("######### depart = " +depart.getName());

        task.setDepartment(depart);
//    task.setDeptRole(null);
        task = (TemplateTask)PersistenceHelper.manager.modify(task);

//    if(task instanceof JELTask){
//
//    }

        message = "true";

%>
        <data_info>

          <l_name><![CDATA[<%=depart.getName()%>]]></l_name>
          <l_oid><![CDATA[<%=deptOid%>]]></l_oid>

        </data_info>

<%
    }else if("changeDeptRow".equals(command)){
        String oid = request.getParameter("oid");
        String deptRole = request.getParameter("role");
        ReferenceFactory rf = new ReferenceFactory();
        TemplateTask task = (TemplateTask)rf.getReference(oid).getObject();
//    task.setDeptRole(deptRole);

//    task.setDepartment(Department.newDepartment());
//    if(task instanceof JELTask){

//      ProjectDeptRole proDeptRole = TaskHelper.manager.getProjectDeptRole(task.getProject(), deptRole);

//      if(proDeptRole != null){
//        task.setDepartment(proDeptRole.getDepartment());
//      }
//    }

        task = (TemplateTask)PersistenceHelper.manager.modify(task);



        message = "true";
%>

        <data_info>

          <deptRole><![CDATA[<%=deptRole%>]]></deptRole>

        </data_info>

<% }else if ("settingDeptRole".equals(command)){

        String pjtOid = request.getParameter("oid");
        String deptOid = request.getParameter("deptOid");
        String deptRole = request.getParameter("deptRole");
        String departName = "";

        pjtOid = WTURLEncoder.decode(pjtOid == null? "":pjtOid);
        deptOid = WTURLEncoder.decode(deptOid==null?"":deptOid);
        deptRole = WTURLEncoder.decode(deptRole);


        TemplateProject project = (TemplateProject)CommonUtil.getObject(pjtOid);
        ProjectDeptRole prjRole = TaskHelper.manager.getProjectDeptRole(project, deptRole);
        ObjectReference o = null;
        if(deptOid.length() == 0){
            try{
            prjRole.setDepartment(new Department());
            }catch(Exception e){Kogger.error(e);}
        }else{
            Department depart = (Department)CommonUtil.getObject(deptOid);
            departName = depart.getName();

            if(prjRole != null){
                prjRole.setDepartment(depart);

            }else{
                prjRole = ProjectDeptRole.newProjectDeptRole();
                prjRole.setProject(project);
//        prjRole.setDeptRole(deptRole);
                prjRole.setDepartment(depart);

            }

            PersistenceHelper.manager.save(prjRole);
            message = "true";
        }
    %>


    <data_info>

          <l_name><![CDATA[<%=departName%>]]></l_name>
          <l_oid><![CDATA[<%=deptOid%>]]></l_oid>

        </data_info>
    <%}
    else if ("refTaskDeptDelete".equals(command)){

        String oid = request.getParameter("oid");
        String deptOid = request.getParameter("deptOid");

        oid = WTURLEncoder.decode(oid==null?"":oid);
        deptOid = WTURLEncoder.decode(deptOid==null?"":deptOid);

        Department depart = null;
        if(deptOid.length() > 0 ){
            depart = (Department)CommonUtil.getObject(deptOid);
        }

        ReferenceFactory rf = new ReferenceFactory();
        TemplateTask task = (TemplateTask)rf.getReference(oid).getObject();

        //if(depart == null){
            //task.setDepartment(Department.newDepartment());
        //}
//    task.setDepartment(Department.newDepartment());
//    if(task instanceof JELTask){
//      if(depart != null){
//        task.setDeptRole(null);
//      }
//    }

        task = (TemplateTask)PersistenceHelper.manager.modify(task);


 //      if(task instanceof JELTask){
//     if(depart != null){
            // WTUser master = PeopleHelper.manager.getChiefUser(depart);
            // if(master != null){
            //  TaskUserHelper.manager.deleteTaskUser(task,master);
            // }
//     }
    //   }


        boolean  flag= false;
        if(task != null){
            flag = true;

        }

        //boolean flag = ProjectHelper.addRefDepartment(task, list);

        if(flag) {
            message = "true";
        }
%>
        <data_info>
        </data_info>
<%  }
    else if("addRefMember".equals(command)) {
        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid);

        String userOidArr[] = request.getParameterValues("userOid");
        Vector userMemberVec = new Vector();
        if(userOidArr != null && userOidArr.length > 0) {
            for(int i = 0; i < userOidArr.length; i++) {
                userMemberVec.add(WTURLEncoder.decode(userOidArr[i]));
            }
        }

        HashMap  map = new HashMap();
        map.put("oid", oid);
        map.put("REF_MEMBER", userMemberVec);

        boolean flag = ProjectHelper.assignProjectMember(map);
        if(flag) {
            message = "true";
        }
    }
    else if("addRoleMember".equals(command)) {
        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid);

        HashMap  map = new HashMap();
        map.put("oid", oid);

        //Role별 담당자 처리
        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Team_Project");
        if(tempTeam != null) {
            Vector vecTeamStd = tempTeam.getRoles();
            Role role = null;
            String newRoleMember = null;
            Vector roleVec = null;
            for(int i = 0; i < vecTeamStd.size(); i++) {
                role = (Role) vecTeamStd.get(i);
                newRoleMember = request.getParameter(role.toString());
                newRoleMember = WTURLEncoder.decode(newRoleMember==null?"":newRoleMember);
                //roleVec에 사이즈가 0인 경우에는 이미 assign된 role 담당자는 삭제 됨.
                roleVec = new Vector();
                if(newRoleMember != null &&  (newRoleMember.trim()).length() > 0) {
                    roleVec.add(newRoleMember);
                }

                map.put(role.toString(), roleVec);
            }
        }

        boolean flag = ProjectHelper.assignProjectMember(map);
        if(flag) {
            message = "true";
        }
    }
    else if("searchRoleMember".equals(command)) {
        message = "true";
    }
    else if("memberDelete".equals(command) || "refMemberDelete".equals(command)
            || "refDeptDelete".equals(command) || "roleMemberDelete".equals(command) ) {

        //Kogger.debug(" ############### command =" +command);
        String linkOid = request.getParameter("linkOid");
        linkOid = WTURLEncoder.decode(linkOid==null?"":linkOid);
        ReferenceFactory rf = new ReferenceFactory();
        ArrayList list = new ArrayList();
        Object object = rf.getReference(linkOid).getObject();
        E3PSProject project = null;
//    JELTask task = null;

        if(object  instanceof ProjectMemberLink){
            ProjectMemberLink projectMemberLink = (ProjectMemberLink)object;
            project = (E3PSProject)projectMemberLink.getProject();
        }else if(object instanceof ProjectViewDepartmentLink){
            ProjectViewDepartmentLink dlink = (ProjectViewDepartmentLink)object;
            project = (E3PSProject)dlink.getProject();
        //}else if(object instanceof TaskViewDepartmentLink){
            //TaskViewDepartmentLink dlink = (TaskViewDepartmentLink)object;
            //task = (JELTask)dlink.getTask();
            //project = (E3PSProject)task.getProject();
        }

        list.add(object);
        boolean isCandelete = true;
        boolean isLatest = project.isLastest();

        if(!isLatest){
            isCandelete = false;
            l_result = messageService.getString("e3ps.message.ket_message", "02324")/*이전 프로젝트 이력에 대해 삭제 할 수 없습니다*/;
        }

        boolean isPM = ProjectUserHelper.manager.isPM(project);

        boolean isAdmin = CommonUtil.isAdmin();

        if(!(isPM || isAdmin)){
            isCandelete = false;
            l_result = messageService.getString("e3ps.message.ket_message", "00378")/*PM 이외에 삭제 할 수 없습니다*/;
        }

        if(isCandelete && "memberDelete".equals(command) && object  instanceof ProjectMemberLink){

            WTUser member = ((ProjectMemberLink)object).getMember();
            WTUser pmuser = ProjectUserHelper.manager.getPM(((ProjectMemberLink)object).getProject());
            if(member.getName().equals(pmuser.getName())){
                isCandelete = false;
                l_result = messageService.getString("e3ps.message.ket_message", "00372")/*PM 구성원은 삭제 하실 수 없습니다*/;
            }
        }


        boolean flag = false;
        if(isCandelete){
            flag = ProjectHelper.deleteObjectLink(list);
        }
        if(flag) {
            message = "true";
        }
    }
    else if("addOutput".equals(command)) {
        String oid = request.getParameter("oid");
        String taskOid = request.getParameter("taskOid");
        String docTypeOid = request.getParameter("docTypeOid");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String role = request.getParameter("role");
        String outputUser = request.getParameter("outputUser");

        String objType = request.getParameter("objType");
        String outputtype = request.getParameter("outputtype");

        String isPrimary = request.getParameter("isPrimary");

        //Kogger.debug("isPrimary addOutput---- " + isPrimary);
        //Kogger.debug("objType --......-- " + objType);

        String userOidArr[] = request.getParameterValues("userOid");
        String deptOidArr[] = request.getParameterValues("deptOid");

        oid = WTURLEncoder.decode(oid==null?"":oid);
        taskOid = WTURLEncoder.decode(taskOid==null?"":taskOid);
        docTypeOid = WTURLEncoder.decode(docTypeOid==null?"":docTypeOid);
        name =WTURLEncoder.decode(name==null?"":name);

        description = WTURLEncoder.decode(description==null?"":description);
        //Kogger.debug("description = " + description);
        role = WTURLEncoder.decode(role==null?"":role);
        outputUser = WTURLEncoder.decode(outputUser==null?"":outputUser);
        //Kogger.debug("role ============== " + role);
        HashMap map = new HashMap();
        map.put("oid", oid);
        map.put("taskOid", taskOid);
        map.put("name", name);
        map.put("description", description);
        map.put("docTypeOid", docTypeOid);
        map.put("role", role);
        map.put("outputUser", outputUser);
        map.put("objType",objType);
        map.put("outputtype",outputtype);
        map.put("isPrimary",isPrimary);

        if(userOidArr != null && userOidArr.length > 0) {
            map.put("userOid", userOidArr);
        }

        if(deptOidArr != null && deptOidArr.length > 0) {
            map.put("deptOid", deptOidArr);
        }

        ProjectOutput output = null;
        try {
            output = ProjectOutputHelper.saveDefProjectOutput(map);
        }
        catch(Exception e) {
            Kogger.error(e);
            output = null;
        }
        if(output != null) {
            message = "true";
        }
    }else if("addOutputTemplate".equals(command)) {
        //Kogger.debug("################ command="+command);
        String oid = request.getParameter("oid");
        String taskOid = request.getParameter("taskOid");
        String docTypeOid = request.getParameter("docTypeOid");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String role = request.getParameter("role");
        String objType = request.getParameter("objType");
        String outputtype = request.getParameter("outputtype");
        String isPrimary = request.getParameter("isPrimary");
        //Kogger.debug("isPrimary ----------------------- " + isPrimary);
        oid = WTURLEncoder.decode(oid==null?"":oid);
        taskOid = WTURLEncoder.decode(taskOid==null?"":taskOid);
        docTypeOid = WTURLEncoder.decode(docTypeOid==null?"":docTypeOid);
        name =WTURLEncoder.decode(name==null?"":name);
        description = WTURLEncoder.decode(description==null?"":description);
        role = WTURLEncoder.decode(role==null?"":role);
        //Kogger.debug(" ######### command"+ command);
        //Kogger.debug("######## docTypeOid= " +docTypeOid);
        HashMap map = new HashMap();
        map.put("oid", oid);
        map.put("taskOid", taskOid);
        map.put("name", name);
        map.put("description", description);
        map.put("docTypeOid", docTypeOid);
        map.put("role", role);
        map.put("objType",objType);
        map.put("outputtype",outputtype);
        map.put("isPrimary", isPrimary);

        ProjectOutput output = null;
        try {
            output = ProjectOutputHelper.saveDefProjectOutputTemplate(map);
        }
        catch(Exception e) {
            Kogger.error(e);
            output = null;
        }
        if(output != null) {
            message = "true";
        }
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    //리스트 검색 .............................................................................
    if( (("addMember".equals(command) || "addRefMember".equals(command) || "memberDelete".equals(command) || "refMemberDelete".equals(command)) && "true".equals(message)) || "searchMember".equals(command)) {



        if("searchMember".equals(command)) {
            message = "true";
        }

        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid,"euc-kr");
%>
            <data_info>
<%
        TemplateProject project = null;
        project = (TemplateProject)CommonUtil.getObject(oid);

        ProjectMemberLink link = null;
        PeopleData data = null;
        String wtuserOID = "";
        String peopleOID = "";
        String memberType = "MEMBER";
        if("refMemberDelete".equals(command) || "addRefMember".equals(command)) {
            memberType = "ONLY_VIEW_MEMBER";
        }

        /* */
        QueryResult members = null;

        if(memberType.equals("MEMBER")){

            members = ProjectUserHelper.manager.getOnlyAppendMember(project);
        }else{
            members = ProjectUserHelper.manager.getQueryForTeamUsers(project, memberType);
        }

        while(members.hasMoreElements()) {
            link = (ProjectMemberLink)members.nextElement();
            data = new PeopleData(link.getMember());
            wtuserOID = (data.wtuser).getPersistInfo().getObjectIdentifier().getStringValue();
            peopleOID = (data.people).getPersistInfo().getObjectIdentifier().getStringValue();
%>
            <l_name><![CDATA[<%=data.name%>]]></l_name>
            <l_duty><![CDATA[<%=data.duty%>]]></l_duty>
            <l_departmentName><![CDATA[<%=data.departmentName%>]]></l_departmentName>
            <l_email><![CDATA[<%=data.email%>]]></l_email>
            <l_oid><![CDATA[<%=wtuserOID%>]]></l_oid>
            <l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
            <l_linkOid><![CDATA[<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_linkOid>
<%
        }
%>
            </data_info>
<%
    }
    else if( ("refDeptDelete".equals(command) || "addRefDept".equals(command)) && "true".equals(message)) {
%>
            <data_info>
<%
        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid,"euc-kr");

        ReferenceFactory rf = new ReferenceFactory();
        TemplateProject project = (TemplateProject)rf.getReference(oid).getObject();

        ProjectViewDepartmentLink deptLink = null;
        Department viewDept = null;
        QueryResult refDepts= ProjectUserHelper.manager.getViewDepartmentLink(project, null);
        Object deptObj[] = null;
        while(refDepts.hasMoreElements()) {
            deptObj = (Object[])refDepts.nextElement();
            deptLink = (ProjectViewDepartmentLink)deptObj[0];
            viewDept = deptLink.getDepartment();
%>
            <l_name><![CDATA[<%=viewDept.getName()%>]]></l_name>
            <l_code><![CDATA[<%=viewDept.getCode()%>]]></l_code>
            <l_oid><![CDATA[<%=viewDept.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            <l_linkOid><![CDATA[<%=deptLink.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_linkOid>
<%
        }

%>
            </data_info>
<%
    }
    else if( ("roleMemberDelete".equals(command) || "addRoleMember".equals(command)
                ||"searchRoleMember".equals(command)) && "true".equals(message)) {
%>
            <data_info>
<%
        String oid = request.getParameter("oid");
        oid = WTURLEncoder.decode(oid==null?"":oid,"euc-kr");

        ReferenceFactory rf = new ReferenceFactory();
        TemplateProject project = (TemplateProject)rf.getReference(oid).getObject();

        HashMap legacyMap = new HashMap();
        ProjectMemberLink roleLink = null;
        QueryResult result = ProjectUserHelper.manager.getQueryForTeamUsers(project, "ROLEMEMBER");
        while(result.hasMoreElements()) {
            roleLink = (ProjectMemberLink)result.nextElement();
            legacyMap.put(roleLink.getPjtRole(), roleLink);
        }

        Vector vecTeamStd = null;
        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Team_Project");
        if(tempTeam != null) {
            vecTeamStd = tempTeam.getRoles();
        }

        if(vecTeamStd != null) {
            Role role = null;
            String roleName_en = null;
            String roleName_ko = null;

            PeopleData data = null;
            String wtuserOID = "";
            String peopleOID = "";

            String name = "";
            String duty = "";
            String deptName = "";
            String email = "";
            String linkOid = "";

            for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                role = (Role) vecTeamStd.get(i);
                roleName_en = role.toString();
                roleName_ko = role.getDisplay(Locale.KOREA);

                roleLink = null;
                data = null;
                wtuserOID = "";
                peopleOID = "";
                name = "";
                duty = "";
                deptName = "";
                email = "";
                linkOid = "";
                if(legacyMap.get(roleName_en) != null) {
                    roleLink = (ProjectMemberLink)legacyMap.get(roleName_en);
                    data = new PeopleData(roleLink.getMember());
                    wtuserOID = (data.wtuser).getPersistInfo().getObjectIdentifier().getStringValue();
                    peopleOID = (data.people).getPersistInfo().getObjectIdentifier().getStringValue();
                    name = data.name;
                    duty = data.duty;
                    deptName = data.departmentName;
                    email = data.email;
                    linkOid = roleLink.getPersistInfo().getObjectIdentifier().getStringValue();
                }
%>
                <l_roleName><![CDATA[<%=roleName_ko%>]]></l_roleName>
                <l_name><![CDATA[<%=name%>]]></l_name>
                <l_duty><![CDATA[<%=duty%>]]></l_duty>
                <l_departmentName><![CDATA[<%=deptName%>]]></l_departmentName>
                <l_email><![CDATA[<%=email%>]]></l_email>
                <l_oid><![CDATA[<%=wtuserOID%>]]></l_oid>
                <l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
                <l_linkOid><![CDATA[<%=linkOid%>]]></l_linkOid>
<%
            }
        }
%>
            </data_info>
<%
    }
    else if("outSearchRoleMember".equals(command)) {
%>
            <data_info>

<%
        String oid = request.getParameter("oid");
        String role = request.getParameter("role");

        oid = WTURLEncoder.decode(oid==null?"":oid,"euc-kr");
        role = WTURLEncoder.decode(role==null?"":role,"euc-kr");

        message = "true";
        try {
            TemplateProject project = null;

            ReferenceFactory rf = new ReferenceFactory();
            Object object = rf.getReference(oid).getObject();
            if(object instanceof TemplateProject) {
                project = (TemplateProject)object;
            }
            else if(object instanceof TemplateTask) {
                project = ((TemplateTask)object).getProject();
            }

            QueryResult result = ProjectUserHelper.manager.getProjectRoleMember(project, null, role);
            if (result.hasMoreElements()) {
                ProjectMemberLink link = (ProjectMemberLink)result.nextElement();
                WTUser wtuser = link.getMember();
                String name = wtuser.getFullName();
                String wtuserOID = wtuser.getPersistInfo().getObjectIdentifier().getStringValue();
                String linkOid = link.getPersistInfo().getObjectIdentifier().getStringValue();
%>
                <l_name><![CDATA[<%=name%>]]></l_name>
                <l_oid><![CDATA[<%=wtuserOID%>]]></l_oid>
                <l_linkOid><![CDATA[<%=linkOid%>]]></l_linkOid>
<%
            }
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }
%>
            </data_info>
<%
    }
    else if("autoCompleteMember".equals(command)) {
        message = "true";
%>
            <data_info>
<%
        String oid = request.getParameter("oid");
        String name = request.getParameter("name");
        String roleType = request.getParameter("roleType");

        //Kogger.debug("oid  >>> " + oid);
        //Kogger.debug("name >>> " + name);
        //Kogger.debug("roletype >>> " + roleType);


        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        name = java.net.URLDecoder.decode(name==null?"":name,"euc-kr");
        roleType = java.net.URLDecoder.decode(roleType==null?"":roleType,"euc-kr");


        if(name == null) {
            name = "";
        }

        if(roleType == null || roleType.length() == 0) {
            roleType = "MEMBER";
        }


        //Kogger.debug("&&&&&&&&&&&&&&&&&&&&&& >>> " + name);


        int type = -1;
        if(roleType.equals("PM")) {
            type = ProjectUserHelper.PM;
        } else if(roleType.equals("ROLEMEMBER")) {
            type = ProjectUserHelper.ROLEMEMBER;
        } else if(roleType.equals("MEMBER")) {
            type = ProjectUserHelper.MEMBER;
        } else if(roleType.equals("ONLY_VIEW_MEMBER")) {
            type = ProjectUserHelper.ONLY_VIEW_MEMBER;
        }

        TemplateProject project = null;

        ReferenceFactory rf = new ReferenceFactory();
        Object object = rf.getReference(oid).getObject();
        if(object instanceof TemplateProject) {
            project = (TemplateProject)object;
        }
        else if(object instanceof TemplateTask) {
            project = ((TemplateTask)object).getProject();
        }

        try {

            ProjectMemberLink link = null;
            PeopleData data = null;
            String wtuserOID = "";
            String peopleOID = "";

            Object linkObj[] = null;

            QueryResult members = ProjectUserHelper.manager.autoCompleteMember(project, name, type);

            while(members.hasMoreElements()) {
                linkObj = (Object[])members.nextElement();
                link = (ProjectMemberLink)linkObj[0];
                data = new PeopleData(link.getMember());
                wtuserOID = (data.wtuser).getPersistInfo().getObjectIdentifier().getStringValue();
                peopleOID = (data.people).getPersistInfo().getObjectIdentifier().getStringValue();
%>
                <l_name><![CDATA[<%=data.name%>]]></l_name>
                <l_duty><![CDATA[<%=data.duty%>]]></l_duty>
                <l_departmentName><![CDATA[<%=data.departmentName%>]]></l_departmentName>
                <l_email><![CDATA[<%=data.email%>]]></l_email>
                <l_oid><![CDATA[<%=wtuserOID%>]]></l_oid>
                <l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
                <l_linkOid><![CDATA[<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_linkOid>
<%
            }
        }
        catch(Exception e) {
            Kogger.error(e);


            message = "false";
        }
%>
            </data_info>
<%
    }else if("seartTaskRefDef".equals(command)){
        String oid = request.getParameter("oid");
        TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);

        Department depart = task.getDepartment();



        String departOid = CommonUtil.getOIDString(depart);
        message = "true";

%>
        <data_info>
        <%if(depart != null){%>
          <l_name><![CDATA[<%=depart.getName()%>]]></l_name>
          <l_oid><![CDATA[<%=departOid%>]]></l_oid>
        <%}%>
        </data_info>
<%
    }else if("deptInfoDept".equals(command)){
        String oid = request.getParameter("deptOid");

        Department depart = (Department)CommonUtil.getObject(oid);

        message = "true";

%>
        <data_info>
        <%if(depart != null){%>
          <l_name><![CDATA[<%=depart.getName()%>]]></l_name>
          <l_oid><![CDATA[<%=CommonUtil.getOIDString(depart)%>]]></l_oid>
        <%}else{
            message = "false";
        }
        %>
        </data_info>
<%
    }else if("codeSelectCreate".equals(command)) {
%>
        <data_info>
<%
        String nation = request.getParameter("nation");
        String oemtype = request.getParameter("oemtype");
        String selectId = request.getParameter("selectId");

        nation= java.net.URLDecoder.decode(nation==null?"":nation,"euc-kr");
        oemtype= java.net.URLDecoder.decode(oemtype==null?"":oemtype,"euc-kr");
        selectId= java.net.URLDecoder.decode(selectId==null?"":selectId,"euc-kr");

        QuerySpec qs = new QuerySpec();
        int idx = qs.appendClassList(NumberCode.class, true);
        if(nation!=null){
        SearchCondition sc1 = new SearchCondition(NumberCode.class, "name", "=", nation);
        qs.appendWhere(sc1, new int[] {idx});

        qs.appendAnd();
        }
        SearchCondition sc2 = new SearchCondition(NumberCode.class, "codeType", "=", oemtype);
        qs.appendWhere(sc2, new int[] {idx});

        QueryResult qr = PersistenceHelper.manager.find( qs );

        String numCodeName = "";
        String numCodeOid = "";

        while(qr.hasMoreElements()){
            Object[] o = (Object[])qr.nextElement();
            NumberCode nCode = (NumberCode) o[0];

            ArrayList list = NumberCodeHelper.getChildNumberCode(nCode);
            for(int i = 0 ; i < list.size(); i++){
              numCodeOid = ((NumberCode)list.get(i)).getPersistInfo().getObjectIdentifier().getStringValue();
              numCodeName = ((NumberCode)list.get(i)).getName();
%>
            <l_name><![CDATA[<%=numCodeName%>]]></l_name>
            <l_oid><![CDATA[<%=numCodeOid%>]]></l_oid>
<%
            }
        }
        %>
        <message>
            <l_message><![CDATA[<%=message%>]]></l_message>
            <l_selectId><![CDATA[<%=selectId%>]]></l_selectId>
        </message>
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

<%


%>

