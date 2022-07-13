<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.elecSop.ProjectSopLink"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.elecSop.CustomerSopHistory"%>
<%@page import="e3ps.common.obj.ObjectUtil"%>
<%@page import="e3ps.project.beans.ProductProjectHelper"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Date,
                java.util.Hashtable,
                java.util.Vector"%>
<%@page import="wt.project.Role,
                wt.team.TeamHelper,
                wt.team.TeamTemplate,
                wt.org.WTUser,
                wt.session.*,
                wt.query.*"%>
<%@page import="e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.*,
                e3ps.project.*,
                e3ps.groupware.company.PeopleData"%>
                
                

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
    PeopleData pd = new PeopleData(sessionUser);
    String msg = "";
    String oid = "";
    boolean isCreate = false;

    String command = request.getParameter("command");
    if(command == null) {
        command = "";
    }

    if ( command.equals("Create") || command.equals("Update") ) {
        String initType = request.getParameter("initType");
        String projectType = request.getParameter("projectType");
        String pjtno1 = request.getParameter("pjtno1");
        String projectState = request.getParameter("projectState");
        if(projectState == null) projectState = "";
        String planStartDate = request.getParameter("planStartDate");
        String planEndDate = request.getParameter("planEndDate");
        if(planEndDate == null) planEndDate = "";
        String projectDesc = request.getParameter("projectDesc");
        if(projectDesc == null) projectDesc = "";
        String templateOid = request.getParameter("templateOid");
        String wbsType = request.getParameter("wbsType");
        String category[] = request.getParameterValues("category");
        String pwlinkOid = request.getParameter("pwlinkOid");
        String userMemberArr[] = request.getParameterValues("userMember");

        String levelCode = "";//request.getParameter("levelcode");
        String productCode = "";//request.getParameter("productcode");
        String customerCode = "";//request.getParameter("customercode");
        String devcompanyCode = "";//request.getParameter("devcompanycode");
        String makecompanyCode = "";//request.getParameter("makecompanycode");
        String modelCode = request.getParameter("modelcode");

        String drNumber = request.getParameter("drNumber");
        String drKeyOid = request.getParameter("drKeyOid");
        String reviewPjtNo[] = request.getParameterValues("reviewPjt");
        String teamType = request.getParameter("teamType");                        //사업부
        String itDivision = request.getParameter("itDivision");                    //전자사업부 특화부서
        String process = request.getParameter("process");                          //Process
        String sales = request.getParameter("sales");                              //영업담당자
        String isPM = request.getParameter("isPM");                                //개발담당자(false) or PM(true)
        String devManager = request.getParameter("devManager");                    //개발담당자(PM) 이후
        String ToUser = request.getParameter("ToUser");                            //개발담당자 이전
        String department = request.getParameter("department");                    //부서
        String productType = request.getParameter("productType");                  //제품구분
        String partNo = request.getParameter("partNo");                            //Part No
        String productTypeLevel2 = request.getParameter("productTypeLevel2");      //제품구분 Level2
        String sealed = request.getParameter("sealed");                            //방수여부
        String series = request.getParameter("series");                            //시리즈
        String rank = request.getParameter("rank");                                //Rank
        String model = request.getParameter("model");                              //대표차종
        String assembledType = request.getParameter("assembledType");              //조립구분
        String developentType = request.getParameter("developentType");            //개발구분
        if("-".equals(developentType)){
            developentType = null;
        }
        String designType = request.getParameter("designType");                    //설계구분
        String proteamNo = request.getParameter("proteamNo");                      //생산조립처
        String customer[] = request.getParameterValues("CUSTOMEREVENTOid");        //최종 사용처
        String dependence[] = request.getParameterValues("SUBCONTRACTOROid");      //개발검토 의뢰처
        String costsDate = request.getParameter("costsDate");                      //개발원가 제출일
        String devPattern = request.getParameter("developTyped");                  //개발유형
        
        String projectoid[] = request.getParameterValues("projectOid");

        String lifecycle = request.getParameter("lifecycle");
        String pmoid = request.getParameter("pmoid");

        String projectName = request.getParameter("projectName");

        if(projectName == null)
            projectName = "";

        String manageProduct    = request.getParameter("manageProduct"); //관리 제품군
        String developePurpose1 = request.getParameter("developePurpose1"); //개발목적1레벨
        String developePurpose2 = request.getParameter("developePurpose2"); //개발목적2레벨
        
        String obtainType = request.getParameter("obtainType"); //수주관리
        
        String linkpjtoid = request.getParameter("linkpjtoid"); //연계프로젝트
        
        e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();

        String projectNo = null;
        String teamTypeCode = "";

        if ( "자동차 사업부".equals(teamType) ) {
            
            if ( StringUtils.startsWith(pd.departmentName, "전장모듈") ){
                teamTypeCode = "M";
            } else {
                teamTypeCode = "A";
            }
        } else if ("KETS".equals(teamType) ) {
            teamTypeCode = "S";
        } else {
            teamTypeCode = "E";
        }

        String tempDate = DateUtil.getDateString(new Date(), "all");
        String processCode = "";
        if ( process != null && process.length() > 0 ) {
               NumberCode code = NumberCodeHelper.manager.getNumberCode("PROCESS", process);
               
               
               
               if("Proto".equals(code.getName())){
                   processCode = "T";
               }else if("Pilot".equals(code.getName())){
                   processCode = "B";
               }else if("T-CAR".equals(code.getName())){
                   processCode = "M";
               }else if("Etc".equals(code.getName())){
                   processCode = "R";
               }else if("양산".equals(code.getName())){
        	   
        	       if("DP2".equals(developePurpose1)){//개발목적이 4M
        		       processCode = "C";    
                   }else{
                       processCode = "P";   
                   }
        	       
               }
               
               
              /* if(developentType != null){
               if(developentType.length() > 0 ){
                   NumberCode nc = NumberCodeHelper.manager.getNumberCode("DEVELOPENTTYPE", developentType);
                   if("추가금형".equals(nc.getName())){
                       processCode = "C";
                   }
               }
               } */
            projectNo = teamTypeCode + tempDate.substring(2, 4) + processCode;
            projectNo = projectNo + ManageSequence.getSeqNo(projectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);
           }

        //최종 사용처
        Vector customerVec = new Vector();
        if(customer != null && customer.length > 0) {
            for(int i = 0; i < customer.length; i++) {
                customerVec.add(customer[i]);
            }
        }
        // 고객처
        String SUBCONTRACTOROid[] = request.getParameterValues("sOid");
        Vector SUBCONTRACTOROidVec = new Vector();
        if(SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
            for(int i = 0; i < SUBCONTRACTOROid.length; i++) {
                SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
            }
        }
        // 납입처 Link
        String nOid[] = request.getParameterValues("nOid");
        Vector nOidVec = new Vector();
        if(nOid != null && nOid.length > 0) {
            for(int i = 0; i < nOid.length; i++) {
                nOidVec.add(nOid[i]);
            }
        }
        
        //파생차종
        String[] oemOids = request.getParameterValues("oemOid");
        Vector oemOidVec = new Vector();
        if(oemOids != null && oemOids.length > 0) {
            for(int i = 0; i < oemOids.length; i++) {
                oemOidVec.add(oemOids[i]);
            }
        }

        //관련 Project
        Vector projectOidVec = new Vector();
        if(projectoid != null && projectoid.length > 0) {
            for(int i = 0; i < projectoid.length; i++) {
                projectOidVec.add(projectoid[i]);
            }
        }
        
        

        
        Hashtable hash = new Hashtable();
        hash.put("projectType", projectType);             //프로젝트 종류
        hash.put("pjtno1", pjtno1);                       // 원가 COST_I 테이블에 넣어 줄 데이터 추가
        if(projectNo != null)
            hash.put("projectNo", projectNo);             //ProjectNO
        hash.put("projectName", projectName);             //프로젝트 명
        if(planStartDate != null)
            hash.put("planStartDate", planStartDate);     //계획 시작일자
        hash.put("planEndDate", planEndDate);             //계획 종료일자
        hash.put("projectDesc", projectDesc);             //설명

        if(drNumber != null)
            hash.put("drNumber", drNumber);
        if(drKeyOid != null)
            hash.put("drKeyOid", drKeyOid);
        if(reviewPjtNo != null)
            hash.put("reviewPjtNo", reviewPjtNo);
        if(teamType != null)
            hash.put("teamType", teamType);               //사업부
        if(itDivision != null)
            hash.put("itDivision", itDivision);           //전자사업부 특화부서
        hash.put("sales", sales);                         //영업담당자
        if(isPM != null){
            hash.put("isPM", isPM);                       //개발담당 or PM
        }
        if(devManager != null)
            hash.put("devUser", devManager);              //개발담당자
        if(ToUser != null)
            hash.put("ToUser", ToUser);
        if(department != null)
            hash.put("department", department);           //부서
        //hash.put("productType", productType);           //제품구분
        //hash.put("partNo", StringUtil.checkNull(partNo))//Part No
        hash.put("productTypeLevel2", productTypeLevel2); //제품구분 Level2
        if(sealed != null)hash.put("sealed", sealed);     //방수여부
        if(series != null)hash.put("series", series);     //시리즈
        if(rank != null)hash.put("rank", rank);           //Rank
        if(model != null)hash.put("model", model);        //대표차종
        if(assembledType != null)hash.put("assembledType", assembledType);         //조립구분
        if(process != null)
            hash.put("process", process);                 //Process
        if(developentType != null)hash.put("developentType", developentType);       //개발구분
        if(designType != null)hash.put("designType", designType);               //설계구분
        if(proteamNo != null)hash.put("proteamNo", proteamNo);                 //생산조립처
        if(customerVec != null)hash.put("customer", customerVec);                //최종 사용처
        if(SUBCONTRACTOROidVec != null)hash.put("SUBCONTRACTOR", SUBCONTRACTOROidVec);
        if(nOidVec != null)hash.put("nOidVec", nOidVec);
        if(costsDate != null)hash.put("costsDate", costsDate);                 //개발원가 제출일
        if(devPattern != null)hash.put("devPattern", devPattern);                 //개발유형
        hash.put("oemOids", oemOidVec);                     //파생차종
        
        hash.put("projectoid", projectOidVec);            //관련 Project
        
        

        if(templateOid != null){
            hash.put("TemplateOID", templateOid);
            hash.put("category", category);
            hash.put("wbsType", wbsType);
        }
        hash.put("pwlinkOid", pwlinkOid);
        
        Vector userMemberVec = new Vector();
        if(userMemberArr != null && userMemberArr.length > 0) {
            for(int i = 0; i < userMemberArr.length; i++) {
                userMemberVec.add(userMemberArr[i]);
            }
        }
        hash.put("projectState", projectState);

        hash.put("USERMEMBER", userMemberVec);
        
        if(developePurpose1 != null)hash.put("developePurpose1", developePurpose1);
        if(developePurpose2 != null)hash.put("developePurpose2", developePurpose2);

        if(manageProduct != null)hash.put("manageProduct", manageProduct);
        
        if(obtainType != null)hash.put("obtainType", obtainType);
        if(linkpjtoid != null)hash.put("linkpjtoid", linkpjtoid);
        Role role = null;
        String roleUser = null;

        TeamTemplate tempTeam = null;
        if(teamTypeCode.equals("A") || teamTypeCode.equals("M") || teamTypeCode.equals("S") ) {
         tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Product Project");
        }else if(teamTypeCode.equals("E")) {
         tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Electron Project");
        }

        Vector vecTeamStd = tempTeam.getRoles();
        for(int i = 0; i < vecTeamStd.size(); i++) {
            role = (Role)vecTeamStd.get(i);
            roleUser = request.getParameter(role.toString());
            if(roleUser == null) {
                roleUser = "";
            }
            hash.put(role.toString(), roleUser);
        }

        hash.put("lifecycle", lifecycle);

        if ( pmoid.length() > 0 ) {
            hash.put("pmoid", pmoid);
        }

        E3PSProject project = null;

        if ( command.equals("Create") ) {
            project = E3PSProjectHelper.service.createE3PSProject(hash);
            msg = messageService.getString("e3ps.message.ket_message", "01315")/*등록 되었습니다*/;
        } else {
            oid = request.getParameter("oid");
            hash.put("oid", oid);

            project = E3PSProjectHelper.service.updateE3PSProject(hash);
            msg=messageService.getString("e3ps.message.ket_message", "01940")/*수정 되었습니다*/;
        }

        if ( project != null ) {
            isCreate = true;
            oid = CommonUtil.getOIDString(project);
        } else {
            msg=messageService.getString("e3ps.message.ket_message", "01317")/*등록 실패*/;
        }

    } else if(command.equals("CostInfoUpdate") ) {
        //비용관리
        String costType[] = request.getParameterValues("costType");
        String partNoCost[] = request.getParameterValues("partNoCost");
        String dieNoCost[] = request.getParameterValues("dieNoCost");
        String moldTypeCost[] = request.getParameterValues("moldTypeCost");
        String costName[] = request.getParameterValues("costName");
        String order[] = request.getParameterValues("order");
        String targetCost[] = request.getParameterValues("targetCost");
        String decideCost[] = request.getParameterValues("decideCost");
        String executionCost[] = request.getParameterValues("executionCost");
        String editCost[] = request.getParameterValues("editCost");

        //비용 관리
        Vector costTypeVec = new Vector();
        if(costType != null && costType.length > 0) {
            for(int i = 0; i < costType.length; i++) {
                costTypeVec.add(costType[i]);
            }
        }
        Vector partNoCostVec = new Vector();
        if(partNoCost != null && partNoCost.length > 0) {
            for(int i = 0; i < partNoCost.length; i++) {
                partNoCostVec.add(partNoCost[i]);
            }
        }
        Vector dieNoCostVec = new Vector();
        if(dieNoCost != null && dieNoCost.length > 0) {
            for(int i = 0; i < dieNoCost.length; i++) {
                dieNoCostVec.add(dieNoCost[i]);
            }
        }
        Vector moldTypeCostVec = new Vector();
        if(moldTypeCost != null && moldTypeCost.length > 0) {
            for(int i = 0; i < moldTypeCost.length; i++) {
                moldTypeCostVec.add(moldTypeCost[i]);
            }
        }
        Vector costNameVec = new Vector();
        if(costName != null && costName.length > 0) {
            for(int i = 0; i < costName.length; i++) {
                costNameVec.add(costName[i]);
            }
        }
        Vector orderVec = new Vector();
        if(order != null && order.length > 0) {
            for(int i = 0; i < order.length; i++) {
                orderVec.add(order[i]);
            }
        }
        Vector targetCostVec = new Vector();
        if(targetCost != null && targetCost.length > 0) {
            for(int i = 0; i < targetCost.length; i++) {
                targetCostVec.add(targetCost[i]);
            }
        }
        Vector decideCostVec = new Vector();
        if(decideCost != null && decideCost.length > 0) {
            for(int i = 0; i < decideCost.length; i++) {
                decideCostVec.add(decideCost[i]);
            }
        }
        Vector executionCostVec = new Vector();
        if(executionCost != null && executionCost.length > 0) {
            for(int i = 0; i < executionCost.length; i++) {
                executionCostVec.add(executionCost[i]);
            }
        }
        Vector editCostVec = new Vector();
        if(editCost != null && editCost.length > 0) {
            for(int i = 0; i < editCost.length; i++) {
                editCostVec.add(editCost[i]);
            }
        }

        Hashtable hash = new Hashtable();
        //비용 관리
        hash.put("costType", costTypeVec);
        hash.put("partNoCost", partNoCostVec);
        hash.put("dieNoCost", dieNoCostVec);
        hash.put("moldTypeCost", moldTypeCostVec);
        hash.put("costName", costNameVec);
        hash.put("order", orderVec);
        hash.put("targetCost", targetCostVec);
        hash.put("decideCost", decideCostVec);
        hash.put("executionCost", executionCostVec);
        hash.put("editCost", editCostVec);

        oid = request.getParameter("oid");
        hash.put("oid", oid);

        boolean saveCheck = e3ps.project.beans.ProductProjectHelper.manager.CostInfoSave(hash);

        if(saveCheck) {
            msg = messageService.getString("e3ps.message.ket_message", "01315")/*등록 되었습니다*/;
            isCreate = true;
        }else{
            msg=messageService.getString("e3ps.message.ket_message", "01317")/*등록 실패*/;
        }
    }else if(command.equals("ProductInfoUpdate") ) {
	   Hashtable hash = new Hashtable();
	   //Item 현황
        String moldItemOid[] = request.getParameterValues("moldItemOid");
        String itemType[] = request.getParameterValues("itemType");
        String moldProductType[] = request.getParameterValues("moldProductType");
        String moldPartNo[] = request.getParameterValues("moldPartNo");
        String partName[] = request.getParameterValues("partName");
        String dieNo[] = request.getParameterValues("dieNo");
        String costOid[] = request.getParameterValues("costOid");
        String moldType[] = request.getParameterValues("moldType");
        String cVPitch[] = request.getParameterValues("cVPitch");
        String cTSPM[] = request.getParameterValues("cTSPM");
        String making[] = request.getParameterValues("making");
        String makingPlace2[] = request.getParameterValues("makingPlaceTwo");
        String productionPlace[] = request.getParameterValues("productionPlace");
        String productionPlace2[] = request.getParameterValues("productionPlaceTwo");
        String materials[] = request.getParameterValues("materials");
        String poidvalue[] = request.getParameterValues("poidvalue");
        String height[] = request.getParameterValues("height");
        String wide[] = request.getParameterValues("wide");
        String shrinkage[] = request.getParameterValues("newType");
        String etc[] = request.getParameterValues("etc");
        String delItemOid = request.getParameter("delItemOid");
        String userMemberArr[] = request.getParameterValues("userMember");
        
        if(delItemOid != null)
            hash.put("delItemOid", delItemOid);
        
      //Item 현황
        Vector moldItemOidVec = new Vector();
        if(moldItemOid != null && moldItemOid.length > 0) {
            for(int i = 0; i < moldItemOid.length; i++) {
                moldItemOidVec.add(moldItemOid[i]);
            }
        }
        Vector itemTypeVec = new Vector();
        if(itemType != null && itemType.length > 0) {
            for(int i = 0; i < itemType.length; i++) {
                itemTypeVec.add(itemType[i]);
            }
        }
        Vector moldProductTypeVec = new Vector();
        if(moldProductType != null && moldProductType.length > 0) {
            for(int i = 0; i < moldProductType.length; i++) {
                moldProductTypeVec.add(moldProductType[i]);
            }
        }
        Vector moldPartNoVec = new Vector();
        if(moldPartNo != null && moldPartNo.length > 0) {
            for(int i = 0; i < moldPartNo.length; i++) {
                moldPartNoVec.add(moldPartNo[i]);
            }
        }
        Vector partNameVec = new Vector();
        if(partName != null && partName.length > 0) {
            for(int i = 0; i < partName.length; i++) {
                partNameVec.add(partName[i]);
            }
        }
        Vector dieNoVec = new Vector();
        if(dieNo != null && dieNo.length > 0) {
            for(int i = 0; i < dieNo.length; i++) {
                dieNoVec.add(dieNo[i].trim());
            }
        }
        Vector costOidVec = new Vector();
        if(costOid != null && costOid.length > 0) {
            for(int i = 0; i < costOid.length; i++) {
                costOidVec.add(costOid[i]);
            }
        }
        Vector moldTypeVec = new Vector();
        if(moldType != null && moldType.length > 0) {
            for(int i = 0; i < moldType.length; i++) {
                moldTypeVec.add(moldType[i]);
            }
        }
        Vector cVPitchVec = new Vector();
        if(cVPitch != null && cVPitch.length > 0) {
            for(int i = 0; i < cVPitch.length; i++) {
                cVPitchVec.add(cVPitch[i]);
            }
        }
        Vector cTSPMVec = new Vector();
        if(cTSPM != null && cTSPM.length > 0) {
            for(int i = 0; i < cTSPM.length; i++) {
                cTSPMVec.add(cTSPM[i]);
            }
        }
        Vector makingVec = new Vector();
        if(making != null && making.length > 0) {
            for(int i = 0; i < making.length; i++) {
                makingVec.add(making[i]);
            }
        }
        Vector makingPlace2Vec = new Vector();
        if(makingPlace2 != null && makingPlace2.length > 0) {
            for(int i = 0; i < makingPlace2.length; i++) {
        	   makingPlace2Vec.add(makingPlace2[i]);
            }
        }
        Vector productionPlaceVec = new Vector();
        if(productionPlace != null && productionPlace.length > 0) {
            for(int i = 0; i < productionPlace.length; i++) {
                productionPlaceVec.add(productionPlace[i]);
            }
        }
        Vector productionPlace2Vec = new Vector();
        if(productionPlace2 != null && productionPlace2.length > 0) {
            for(int i = 0; i < productionPlace2.length; i++) {
                productionPlace2Vec.add(productionPlace2[i]);
            }
        }
        Vector materialsVec = new Vector();
        if(materials != null && materials.length > 0) {
            for(int i = 0; i < materials.length; i++) {
                materialsVec.add(materials[i]);
            }
        }

        Vector poidvalueVec = new Vector();
        if(poidvalue != null && poidvalue.length > 0) {
            for(int i = 0; i < poidvalue.length; i++) {
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
        Vector shrinkageVec = new Vector();
        if(shrinkage != null && shrinkage.length > 0) {
            for(int i = 0; i < shrinkage.length; i++) {
                shrinkageVec.add(shrinkage[i]);
            }
        }
        Vector etcVec = new Vector();
        if(etc != null && etc.length > 0) {
            for(int i = 0; i < etc.length; i++) {
                etcVec.add(etc[i]);
            }
        }

        //제품정보
        if(request.getParameterValues("rowId") !=null){
          String rowId[] = request.getParameterValues("rowId");
          Vector rowIdVec = new Vector();
          if(rowId != null && rowId.length > 0) {
              for(int i = 0; i < rowId.length; i++) {
                  rowIdVec.add(rowId[i]);

                  if(request.getParameterValues("count"+rowId[i]) !=null){
                      String count[] = request.getParameterValues("count"+rowId[i]);
                      Vector countVec = new Vector();
                      if(count != null && count.length > 0) {
                          for(int j = 0; j < count.length; j++) {
                              countVec.add(count[j]);
                          }
                      }
                      hash.put("countVec"+rowId[i], countVec);
                  }

                  if(request.getParameterValues("optOid"+rowId[i]) !=null){
                      String optOid[] = request.getParameterValues("optOid"+rowId[i]);
                      Vector optOidVec = new Vector();
                      if(optOid != null && optOid.length > 0) {
                          for(int j = 0; j < optOid.length ; j++) {
                              optOidVec.add(optOid[j]);
                          }
                      }
                      hash.put("optOidVec"+rowId[i], optOidVec);
                  }

                  if(request.getParameterValues("y1"+rowId[i]) !=null){
                      String y1[] = request.getParameterValues("y1"+rowId[i]);
                      Vector y1Vec = new Vector();
                      if(y1 != null && y1.length > 0) {
                          for(int j = 0; j < y1.length; j++) {
                              y1Vec.add(y1[j]);
                          }
                      }
                      hash.put("y1Vec"+rowId[i], y1Vec);
                  }

                  if(request.getParameterValues("y2"+rowId[i]) !=null){
                      String y2[] = request.getParameterValues("y2"+rowId[i]);
                      Vector y2Vec = new Vector();
                      if(y2 != null && y2.length > 0) {
                          for(int j = 0; j < y2.length; j++) {
                              y2Vec.add(y2[j]);
                          }
                  }
                  hash.put("y2Vec"+rowId[i], y2Vec);
              }

              if(request.getParameterValues("y3"+rowId[i]) !=null){
                  String y3[] = request.getParameterValues("y3"+rowId[i]);
                  Vector y3Vec = new Vector();
                  if(y3 != null && y3.length > 0) {
                      for(int j = 0; j < y3.length; j++) {
                          y3Vec.add(y3[j]);
                      }
                  }
                  hash.put("y3Vec"+rowId[i], y3Vec);
              }

              if(request.getParameterValues("y4"+rowId[i]) !=null){
                  String y4[] = request.getParameterValues("y4"+rowId[i]);
                  Vector y4Vec = new Vector();
                  if(y4 != null && y4.length > 0) {
                      for(int j = 0; j < y4.length; j++) {
                          y4Vec.add(y4[j]);
                      }
                  }
                  hash.put("y4Vec"+rowId[i], y4Vec);
              }

              if(request.getParameterValues("y5"+rowId[i]) !=null){
                  String y5[] = request.getParameterValues("y5"+rowId[i]);
                  Vector y5Vec = new Vector();
                  if(y5 != null && y5.length > 0) {
                      for(int j = 0; j < y5.length; j++) {
                          y5Vec.add(y5[j]);
                      }
                  }
                  hash.put("y5Vec"+rowId[i], y5Vec);
              }

              if(request.getParameterValues("y6"+rowId[i]) !=null){
                  String y6[] = request.getParameterValues("y6"+rowId[i]);
                  Vector y6Vec = new Vector();
                  if(y6 != null && y6.length > 0) {
                      for(int j = 0; j < y6.length; j++) {
                          y6Vec.add(y6[j]);
                      }
                  }
                  hash.put("y6Vec"+rowId[i], y6Vec);
              }

              if(request.getParameterValues("y7"+rowId[i]) !=null){
                  String y7[] = request.getParameterValues("y7"+rowId[i]);
                  Vector y7Vec = new Vector();
                  if(y7 != null && y7.length > 0) {
                      for(int j = 0; j < y7.length; j++) {
                          y7Vec.add(y7[j]);
                      }
                  }
                  hash.put("y7Vec"+rowId[i], y7Vec);
              }

              if(request.getParameterValues("y8"+rowId[i]) !=null){
                  String y8[] = request.getParameterValues("y8"+rowId[i]);
                  Vector y8Vec = new Vector();
                  if(y8 != null && y8.length > 0) {
                      for(int j = 0; j < y8.length; j++) {
                          y8Vec.add(y8[j]);
                      }
                  }
                  hash.put("y8Vec"+rowId[i], y8Vec);
              }

              if(request.getParameterValues("y9"+rowId[i]) !=null){
                  String y9[] = request.getParameterValues("y9"+rowId[i]);
                  Vector y9Vec = new Vector();
                  if(y9 != null && y9.length > 0) {
                      for(int j = 0; j < y9.length; j++) {
                          y9Vec.add(y9[j]);
                      }
                  }
                  hash.put("y9Vec"+rowId[i], y9Vec);
              }

              if(request.getParameterValues("y10"+rowId[i]) !=null){
                  String y10[] = request.getParameterValues("y10"+rowId[i]);
                  Vector y10Vec = new Vector();
                  if(y10 != null && y10.length > 0) {
                      for(int j = 0; j < y10.length; j++) {
                          y10Vec.add(y10[j]);
                      }
                  }
                  hash.put("y10Vec"+rowId[i], y10Vec);
              }

              if(request.getParameterValues("usage"+rowId[i]) !=null){
                  String usage[] = request.getParameterValues("usage"+rowId[i]);
                  Vector usageVec = new Vector();
                  if(usage != null && usage.length > 0) {
                      for(int j = 0; j < usage.length; j++) {
                          usageVec.add(usage[j]);
                      }
                  }
                  hash.put("usageVec"+rowId[i], usageVec);
              }

              if(request.getParameterValues("optionRate"+rowId[i]) !=null){
                  String optionRate[] = request.getParameterValues("optionRate"+rowId[i]);
                  Vector optionRateVec = new Vector();
                  if(optionRate != null && optionRate.length > 0) {
                      for(int j = 0; j < optionRate.length; j++) {
                          optionRateVec.add(optionRate[j]);
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
      if(request.getParameterValues("reviewProjectNo") !=null){
          String reviewProjectNo[] = request.getParameterValues("reviewProjectNo");
          Vector reviewProjectNoVec = new Vector();
          if(reviewProjectNo != null && reviewProjectNo.length > 0) {
              for(int i = 0; i < reviewProjectNo.length; i++) {
                  reviewProjectNoVec.add(reviewProjectNo[i]);
              }
          }
          hash.put("reviewProjectNoVec", reviewProjectNoVec);
      }
      if(request.getParameterValues("reviewSeqNo") !=null){
          String reviewSeqNo[] = request.getParameterValues("reviewSeqNo");
          Vector reviewSeqNoVec = new Vector();
          if(reviewSeqNo != null && reviewSeqNo.length > 0) {
              for(int i = 0; i < reviewSeqNo.length; i++) {
                  reviewSeqNoVec.add(reviewSeqNo[i]);
              }
          }
          hash.put("reviewSeqNoVec", reviewSeqNoVec);
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
                  usageTVec.add(usageT[i]);
              }
          }
          hash.put("usageTVec", usageTVec);
      }

      if(request.getParameterValues("price") !=null){
          String price[] = request.getParameterValues("price");
          Vector priceVec = new Vector();
          if(price != null && price.length > 0) {
              for(int i = 0; i < price.length; i++) {
                  priceVec.add(price[i]);
              }
          }
          hash.put("priceVec", priceVec);
      }

      if(request.getParameterValues("cost") !=null){
          String cost[] = request.getParameterValues("cost");
          Vector costVec = new Vector();
          if(cost != null && cost.length > 0) {
              for(int i = 0; i < cost.length; i++) {
                  costVec.add(cost[i]);
              }
          }
          hash.put("costVec", costVec);
      }

      if(request.getParameterValues("rate") !=null){
          String rate[] = request.getParameterValues("rate");
          Vector rateVec = new Vector();
          if(rate != null && rate.length > 0) {
              for(int i = 0; i < rate.length; i++) {
                  rateVec.add(rate[i]);
              }
          }
          hash.put("rateVec", rateVec);
      }

      if(request.getParameterValues("y1T") !=null){
          String y1T[] = request.getParameterValues("y1T");
          Vector y1TVec = new Vector();
          if(y1T != null && y1T.length > 0) {
              for(int i = 0; i < y1T.length; i++) {
                  y1TVec.add(y1T[i]);
              }
          }
          hash.put("y1TVec", y1TVec);
      }

      if(request.getParameterValues("y2T") !=null){
          String y2T[] = request.getParameterValues("y2T");
          Vector y2TVec = new Vector();
          if(y2T != null && y2T.length > 0) {
              for(int i = 0; i < y2T.length; i++) {
                  y2TVec.add(y2T[i]);
              }
          }
          hash.put("y2TVec", y2TVec);
      }

      if(request.getParameterValues("y3T") !=null){
          String y3T[] = request.getParameterValues("y3T");
          Vector y3TVec = new Vector();
          if(y3T != null && y3T.length > 0) {
              for(int i = 0; i < y3T.length; i++) {
                  y3TVec.add(y3T[i]);
              }
          }
          hash.put("y3TVec", y3TVec);
      }

      if(request.getParameterValues("y4T") !=null){
          String y4T[] = request.getParameterValues("y4T");
          Vector y4TVec = new Vector();
          if(y4T != null && y4T.length > 0) {
              for(int i = 0; i < y4T.length; i++) {
                  y4TVec.add(y4T[i]);
              }
          }
          hash.put("y4TVec", y4TVec);
      }

      if(request.getParameterValues("y5T") !=null){
          String y5T[] = request.getParameterValues("y5T");
          Vector y5TVec = new Vector();
          if(y5T != null && y5T.length > 0) {
              for(int i = 0; i < y5T.length; i++) {
                  y5TVec.add(y5T[i]);
              }
          }
          hash.put("y5TVec", y5TVec);
      }

      if(request.getParameterValues("y6T") !=null){
          String y6T[] = request.getParameterValues("y6T");
          Vector y6TVec = new Vector();
          if(y6T != null && y6T.length > 0) {
              for(int i = 0; i < y6T.length; i++) {
                  y6TVec.add(y6T[i]);
              }
          }
          hash.put("y6TVec", y6TVec);
      }

      if(request.getParameterValues("y7T") !=null){
          String y7T[] = request.getParameterValues("y7T");
          Vector y7TVec = new Vector();
          if(y7T != null && y7T.length > 0) {
              for(int i = 0; i < y7T.length; i++) {
                  y7TVec.add(y7T[i]);
              }
          }
          hash.put("y7TVec", y7TVec);
      }

      if(request.getParameterValues("y8T") !=null){
          String y8T[] = request.getParameterValues("y8T");
          Vector y8TVec = new Vector();
          if(y8T != null && y8T.length > 0) {
              for(int i = 0; i < y8T.length; i++) {
                  y8TVec.add(y8T[i]);
              }
          }
          hash.put("y8TVec", y8TVec);
      }

      if(request.getParameterValues("y9T") !=null){
          String y9T[] = request.getParameterValues("y9T");
          Vector y9TVec = new Vector();
          if(y9T != null && y9T.length > 0) {
              for(int i = 0; i < y9T.length; i++) {
                  y9TVec.add(y9T[i]);
              }
          }
          hash.put("y9TVec", y9TVec);
      }

      if(request.getParameterValues("y10T") !=null){
          String y10T[] = request.getParameterValues("y10T");
          Vector y10TVec = new Vector();
          if(y10T != null && y10T.length > 0) {
              for(int i = 0; i < y10T.length; i++) {
                  y10TVec.add(y10T[i]);
              }
          }
          hash.put("y10TVec", y10TVec);
      }
      
      if(request.getParameterValues("assemblyPlaceType") !=null){
          String assemblyPlaceType[] = request.getParameterValues("assemblyPlaceType");
          Vector assemblyPlaceTypeVec = new Vector();
          if(assemblyPlaceType != null && assemblyPlaceType.length > 0) {
              for(int i = 0; i < assemblyPlaceType.length; i++) {
        	    assemblyPlaceTypeVec.add(assemblyPlaceType[i]);
              }
          }
          hash.put("assemblyPlaceTypeVec", assemblyPlaceTypeVec);
      }
      
      if(request.getParameterValues("assemblyPlace") !=null){
          String assemblyPlace[] = request.getParameterValues("assemblyPlace");
          Vector assemblyPlaceVec = new Vector();
          if(assemblyPlace != null && assemblyPlace.length > 0) {
              for(int i = 0; i < assemblyPlace.length; i++) {
        	    assemblyPlaceVec.add(assemblyPlace[i]);
              }
          }
          hash.put("assemblyPlaceVec", assemblyPlaceVec);
      }
      
      if(request.getParameterValues("assembledType") !=null){
          String assembledType[] = request.getParameterValues("assembledType");
          Vector assembledTypeVec = new Vector();
          if(assembledType != null && assembledType.length > 0) {
              for(int i = 0; i < assembledType.length; i++) {
        	    assembledTypeVec.add(assembledType[i]);
              }
          }
          hash.put("assembledTypeVec", assembledTypeVec);
      }
      
      //Item 현황
      hash.put("moldItemOid", moldItemOidVec);
      hash.put("itemType", itemTypeVec);
      hash.put("moldProductType", moldProductTypeVec);
      hash.put("moldPartNo", moldPartNoVec);
      hash.put("partName", partNameVec);
      hash.put("dieNo", dieNoVec);
      hash.put("costOid", costOidVec);
      hash.put("moldType", moldTypeVec);
      hash.put("cVPitch", cVPitchVec);
      hash.put("cTSPM", cTSPMVec);
      hash.put("making", makingVec);
      hash.put("makingPlace2", makingPlace2Vec);
      hash.put("productionPlace", productionPlaceVec);
      hash.put("productionPlace2", productionPlace2Vec);
      hash.put("materials", materialsVec);
      hash.put("poidvalue", poidvalueVec);
      hash.put("height", heightVec);
      hash.put("wide", wideVec);
      hash.put("shrinkage", shrinkageVec);
      hash.put("etc", etcVec);
      
      if(request.getParameter("deletePOid") !=null){
          String deletePOid = request.getParameter("deletePOid");
          hash.put("deletePOid", deletePOid);
      }
      oid = request.getParameter("oid");
      //방어코드
      try{
	      E3PSProjectHelper.service.updateProductInfo(hash, (ProductProject)CommonUtil.getObject(oid));
      }catch(Exception e){
	      e.printStackTrace();
      }
    }else if(command.equals("ElecSopCreate") ) {//전자사업부 고객 sop 관리 추가 2017.02.17 황정태 추가
	   String sop = request.getParameter("sop");
	   String pjtno = request.getParameter("pjtno");
	   String changeReason = request.getParameter("changeReason");
	   
	   Timestamp sop_ = DateUtil.getTimestampFormat(sop, "yyyy-MM-dd");
	   
	   E3PSProject project = ProjectHelper.manager.getProject(pjtno);
	   
	   E3PSProjectMaster pjtMaster = (E3PSProjectMaster)project.getMaster();
	    
	   QuerySpec spec = new QuerySpec(); 
	   
	   int idx = spec.appendClassList(ProjectSopLink.class, true);
	   spec.appendWhere(new SearchCondition(ProjectSopLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(pjtMaster) ), new int[] { idx });

	   QueryResult qr = PersistenceHelper.manager.find(spec);
	   
	   int rev = qr.size()+1;
	   
	   CustomerSopHistory sopHistory = CustomerSopHistory.newCustomerSopHistory();
	   sopHistory.setRev(Integer.toString(rev));
	   sopHistory.setChangeReason(changeReason);
	   sopHistory.setSopDate(sop_);
	   PersistenceHelper.manager.save(sopHistory);
	   
	   ProjectSopLink link = ProjectSopLink.newProjectSopLink(sopHistory, pjtMaster);
	   link = (ProjectSopLink)PersistenceHelper.manager.save(link);
	   oid = CommonUtil.getOIDString(link);
    }
%>
<%=oid%>