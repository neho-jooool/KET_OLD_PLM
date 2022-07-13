<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Vector,
                  java.util.Hashtable,
                  java.util.StringTokenizer,
                  java.util.Date,
                  java.util.ArrayList,
                  java.io.*,
                  java.text.SimpleDateFormat,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  jxl.Workbook,
                  jxl.format.Border,
                  jxl.format.BorderLineStyle,
                  jxl.write.Label,
                  jxl.write.WritableCellFormat,
                  jxl.write.WritableSheet,
                  jxl.write.WritableWorkbook,
                  jxl.write.WriteException,
                  e3ps.dms.beans.DMSUtil,
                  e3ps.dms.entity.*,
                  e3ps.project.E3PSProject,
                  e3ps.project.ReviewProject,
                  e3ps.project.beans.ProjectHelper,
                  e3ps.project.beans.E3PSProjectData,

                  e3ps.wfm.entity.KETWfmApprovalMaster,
                                   e3ps.wfm.entity.KETWfmApprovalHistory,
                                   wt.fc.*,
                                   e3ps.common.service.*,
                                   e3ps.wfm.util.WorkflowSearchHelper,

                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  Hashtable tempHash = new Hashtable();
  int i;

  String divCode = "";
   if(CommonUtil.isMember("전자사업부")){
    divCode = "ER";
  }else if(CommonUtil.isMember("자동차사업부")){
    divCode = "CA";
  }else{
    divCode = "CA";
  }

  Vector drOid = null;
  Vector drNo = null;
  Vector developmentStep = null;
  Vector projectNo = null;
  Vector repCarType = null;
  Vector devProductName = null;
  Vector requestBuyer = null;
  Vector devReviewTypeCode = null;
  Vector drState = null;
  Vector drCreator = null;
  Vector drCreateDate = null;
  Vector partName = null;
  Vector carTypeCode = null;

  String DevelopmentStep = request.getParameter("DevelopmentStep");
  String DevProductName = request.getParameter("DevProductName");
  String RequestBuyer = request.getParameter("RequestBuyer");
  String LastUsingBuyer = request.getParameter("LastUsingBuyer");
  String userName = request.getParameter("userName");
  String RepCarType = request.getParameter("RepCarType");
  String authorStatus = request.getParameter("authorStatus");
  String DevReviewTypeCode = request.getParameter("DevReviewTypeCode");
  String ProductCategoryCode = request.getParameter("ProductCategoryCode");
  String predate = request.getParameter("predate");
  String postdate = request.getParameter("postdate");
  String sortKey = request.getParameter("sortKey");
  String sortKeyD = request.getParameter("sortKeyD");
  String pageCnt= request.getParameter("pageCnt");

  String sWtHome = "";
  String sFilePath = "";
  String sFileName = "";

  sortKeyD=StringUtil.checkNull(sortKeyD);
  if(sortKeyD.equals("ASC")){
    sortKeyD="DESC";
  }else{
    sortKeyD="ASC";
  }

  tempHash.put("developmentStep" , new String(DevelopmentStep));
  tempHash.put("devProductName" , new String(DevProductName));
  tempHash.put("requestBuyer" , new String(RequestBuyer));
  tempHash.put("lastUsingBuyer" , new String(LastUsingBuyer));
  tempHash.put("creator" , new String(userName));
  tempHash.put("repCarType" , new String(RepCarType));
  tempHash.put("authorStatus" , new String(authorStatus));
  tempHash.put("devReviewTypeCode" , new String(DevReviewTypeCode));
  tempHash.put("productCategoryCode" , new String(ProductCategoryCode));
  tempHash.put("predate" , new String(predate));
  tempHash.put("postdate" , new String(postdate));
  tempHash.put("sortKey" , new String(sortKey));
  tempHash.put("sortKeyD" , new String(sortKeyD));
  tempHash.put("divCode" , new String(divCode));

  try {

    Hashtable drInfo = DMSUtil.serDevRequestList(tempHash);

    if(drInfo.get("drNo") instanceof Vector) {
      drOid = new Vector();
      drNo = new Vector();
      developmentStep = new Vector();
      projectNo = new Vector();
      repCarType = new Vector();
      devProductName = new Vector();
      requestBuyer = new Vector();
      devReviewTypeCode = new Vector();
      drState = new Vector();
      drCreator = new Vector();
      drCreateDate = new Vector();
      partName = new Vector();
      carTypeCode = new Vector();

      drOid = (Vector)drInfo.get("drOid");
      drNo = (Vector)drInfo.get("drNo");
      developmentStep = (Vector)drInfo.get("developmentStep");
      projectNo = (Vector)drInfo.get("projectNo");
      repCarType = (Vector)drInfo.get("repCarType");
      devProductName = (Vector)drInfo.get("devProductName");
      requestBuyer = (Vector)drInfo.get("requestBuyer");
      devReviewTypeCode = (Vector)drInfo.get("devReviewTypeCode");
      drState = (Vector)drInfo.get("drState");
      drCreator = (Vector)drInfo.get("drCreator");
      drCreateDate = (Vector)drInfo.get("drCreateDate");
      partName = (Vector)drInfo.get("partName");
      carTypeCode = (Vector)drInfo.get("carTypeCode");
    }

    sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
    sFilePath = sWtHome + "/codebase/jsp/dms" ;

    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
    sFileName = "SearchDevRequestList_" +  ff.format(new Date()) + ".xls";

    WritableWorkbook workbook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
    WritableSheet s1 = workbook.createSheet("First Sheet", 0);

    s1.setName("의뢰서 검색목록");

    WritableCellFormat cellFormat = new WritableCellFormat();
      cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

      Label lr = new jxl.write.Label(0, 0, "의뢰서 검색목록");
    s1.addCell(lr);

    int row = 2;
    int rowCount = 0;
    String rowCnt;

    lr = new jxl.write.Label(0, row, "의뢰번호", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(1, row, "개발프로젝트번호", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(2, row, "검토프로젝트번호", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(3, row, "개발단계", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(4, row, "개발(검토)유형", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(5, row, "제품군", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(6, row, "개발(검토) 제품명", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(7, row, "의뢰부서", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(8, row, "의뢰담당자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(9, row, "의뢰일자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(10, row, "상신일자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(11, row, "승인일자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(12, row, "개발부서", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(13, row, "개발담당자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(14, row, "결재상태", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(15, row, "의뢰처 담당자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(16, row, "최종사용처 담당자", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(17, row, "상세내용", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(18, row, "품목수", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(19, row, "의뢰처", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(20, row, "납입처", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(21, row, "최종사용처", cellFormat);
    s1.addCell(lr);

    if(divCode.equals("CA")){
      lr = new jxl.write.Label(22, row, "대표차종", cellFormat);
    }else{
      lr = new jxl.write.Label(22, row, "적용기기", cellFormat);
    }
    s1.addCell(lr);

    lr = new jxl.write.Label(23, row, "U/S", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(24, row, "예상수량(1년차)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(25, row, "예상수량(2년차)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(26, row, "예상수량(3년차)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(27, row, "예상수량(4년차)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(28, row, "예상수량(5년차)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(29, row, "예상수량(6년차~)[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(30, row, "수량합계[천개]", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(31, row, "양산 1차년도", cellFormat);
    s1.addCell(lr);

    if(divCode.equals("CA")){
      lr = new jxl.write.Label(32, row, "DR0 요구일정", cellFormat);
    }else{
      lr = new jxl.write.Label(32, row, "DR1 요구일정", cellFormat);
    }
    s1.addCell(lr);

    lr = new jxl.write.Label(33, row, "제안서 체출일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(34, row, "개발원가 제출일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(35, row, "초도품 제출일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(36, row, "ESIR일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(37, row, "ISIR일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(38, row, "KET 양산일정", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(39, row, "제품Type", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(40, row, "기타 내용", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(41, row, "TabSize", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(42, row, "원자재/부자재", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(43, row, "표면처리", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(44, row, "적용전선", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(45, row, "주요기능", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(46, row, "향후전망", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(47, row, "금형비 감가(판매)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(48, row, "금형비 감가(지급)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(49, row, "금형비 감가(감가기간)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(50, row, "금형비 감가(기타사항)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(51, row, "설비비 감가(판매)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(52, row, "설비비 감가(지급)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(53, row, "설비비 감가(감가기간)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(54, row, "설비비 감가(기타사항)", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(55, row, "Device 사양", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(56, row, "환경규제항목", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(57, row, "기타 요청사항", cellFormat);
    s1.addCell(lr);

    lr = new jxl.write.Label(58, row, "추가 요구사항", cellFormat);
    s1.addCell(lr);

    if(drNo.size()>0){
       String s_drOid = null;
       String s_drNo = null;
       String s_developmentStep = null;
       String s_projectNo = null;
      String s_repCarType = null;
      String s_devProductName = null;
      String s_requestBuyer = null;
      String s_devReviewTypeCode = null;
      String s_drState = null;
      String s_drCreator = null;
      String s_drCreateDate = null;
      String s_partName = null;
      String s_carTypeCode = null;

      String reqNo = "";
       String reqName = "";
       String reception = "";
       String receptionName = "";
       String creatorName = "";
       String insDate = "";
       String deptName = "";
       String lifeCycleState = "";
       String stateState = "";
      String divisionCode = "";
      String projectOID = "";
      String projectNO = "";

      String requestBuyerManager = "";
      String lastUsingBuyer = "";
      String lastUsingBuyerManager = "";

      String isDRRequest = "";
      String drRequestDate = "";
      String isProposalSubmit = "";
      String proposalSubmitDate = "";
      String isCostSubmit = "";
      String costSubmitDate = "";

      String devReviewTypeEtc = "";
      String devReviewDetailComment = "";

      String productSaleFirstYear = "";

      String initialSampleSummitDate = "";
      String eSIRDate = "";
      String iSIRDate = "";
      String ketMassProductionDate = "";

      String productCategoryCode = "";
      String productTypeCode = "";

      String etcSpecification = "";
      String tabSize = "";
      String materialSubMaterial = "";
      String surfaceTreatmentCode = "";
      String applyedWire = "";
      String primaryFunction = "";
      String outlook = "";

      String moldDepreciationTypeSale = "";
      String moldDepreciationTypeGeneral = "";
      String moldDepreciationTypePayment = "";
      String moldDepreciationTypePeriod = "";
      String moldDepreciationTypeEtcInfo = "";

      String equipDepreciationTypeSale = "";
      String equipDepreciationTypePayment = "";
      String equipDepreciationTypePeriod = "";
      String equipDepreciationTypeEtcInfo = "";

      String deviceSpecification = "";
      String environmentalRegulationItem = "";
      String buyerEtcRequirement = "";
      String salesAdditionalRequirement = "";

      String productCategoryCon = "";
      String moldDepreciationType = "";
      String equipDepreciationType = "";
      String activityName = "";
       String completeDate = "";
       String approvalDate = "";
        String requestDate = "";
       KETDevelopmentRequest dr = null;

      for (i=0; i<drNo.size(); i++){

         s_drOid = (String)drOid.get(i);
         dr = (KETDevelopmentRequest)CommonUtil.getObject(s_drOid);

       //=========결재 요청일,승인일 관련 시작====================//
         Object obj2 = CommonUtil.getObject(s_drOid);
         //ReferenceFactory rf = new ReferenceFactory();
         KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
         KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
         KETWfmApprovalMaster master = null;
         Object temp = new Object();
         Vector vec = null;
         WTObject targetObj = KETCommonHelper.service.getPBO((WTObject)CommonUtil.getObject(s_drOid));
         if(targetObj!=null)master = (KETWfmApprovalMaster)WorkflowSearchHelper.manager.getMaster(targetObj);
         if(master!=null){

           vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

           for(int k=0; k<vec.size()-1; k++){
             for(int j=k+1; j<vec.size(); j++){
               history1 = (KETWfmApprovalHistory)vec.get(k);
               history2 = (KETWfmApprovalHistory)vec.get(k);
               if(history1.getSeqNum() < history2.getSeqNum()){
                 temp = vec.get(k);
                 vec.set(k , vec.get(j));
                 vec.set(j , temp);
               }
             }
           }
         }
          //=========결재 요청일,승인일 관련 끝====================//

          if( vec != null )
            {
        boolean isApprover = true;
        activityName = "";
        int iComplet = 0;
        for( int x = 0; x< vec.size(); x++ ){
          KETWfmApprovalHistory history = (KETWfmApprovalHistory)vec.get(x);
          activityName = StringUtil.checkNull( history.getActivityName() );
          if(activityName.equals("검토") ){
            iComplet++;
          }
        }

        for( int x = 0; x< vec.size(); x++ ){
          KETWfmApprovalHistory history = (KETWfmApprovalHistory)vec.get(x);
          activityName = StringUtil.checkNull( history.getActivityName() );

          if(activityName.equals("합의") ){
             iComplet++;
                  }

                  if( x == iComplet && isApprover && activityName.equals("검토") )
                  {
                    activityName = "승인";
                    isApprover = false;
                  }
                 if(history.getCompletedDate()!=null)completeDate = DateUtil.getTimeFormat(history.getCompletedDate(),"yyyy-MM-dd");
                 if(activityName.equals("승인"))approvalDate =  completeDate;
                 if(activityName.equals("요청"))requestDate =  completeDate;

        }
            }

         reqNo = dr.getNumber();
         divisionCode = StringUtil.checkNull(dr.getDivisionCode());
         creatorName = StringUtil.checkNull(dr.getIterationInfo().getCreator().getFullName());
         lifeCycleState = dr.getLifeCycleState().getDisplay();
         insDate = DateUtil.getTimeFormat(dr.getCreateTimestamp(),"yyyy-MM-dd");

         deptName = StringUtil.checkNull(dr.getDeptName());
         requestBuyerManager = StringUtil.checkNull(dr.getRequestBuyerManager());

         lastUsingBuyer = StringUtil.checkNull(dr.getLastUsingBuyer());
        if(!StringUtil.checkNull(lastUsingBuyer).equals("")){
          StringTokenizer st1 = new StringTokenizer(lastUsingBuyer, ",");
          String ct="";
          String bName="";
          while (st1.hasMoreTokens()) {
            ct=st1.nextToken();
            NumberCode nCode2 = (NumberCode)CommonUtil.getObject(ct);

            if(nCode2==null){
              bName=bName + "," + ct;
            }else{
              bName=bName + "," + nCode2.getName();
            }
          }
          if(!bName.equals("")) lastUsingBuyer=bName.substring(1);
        }

        lastUsingBuyerManager = StringUtil.checkNull(dr.getLastUsingBuyerManager());

        isDRRequest = StringUtil.checkNull(dr.getIsDRRequest());
        if(isDRRequest.equals("1")){
          drRequestDate = DateUtil.getTimeFormat(dr.getDrRequestDate(),"yyyy-MM-dd");
        }else{
          drRequestDate = "불필요";
        }

        isProposalSubmit = StringUtil.checkNull(dr.getIsProposalSubmit());
        if(isProposalSubmit.equals("1")){
          proposalSubmitDate = DateUtil.getTimeFormat(dr.getProposalSubmitDate(),"yyyy-MM-dd");
        }else{
          proposalSubmitDate = "불필요";
        }

        isCostSubmit = StringUtil.checkNull(dr.getIsCostSubmit());
        if(isCostSubmit.equals("1")){
          costSubmitDate = DateUtil.getTimeFormat(dr.getCostSubmitDate(),"yyyy-MM-dd");
        }else{
          costSubmitDate = "불필요";
        }

         devReviewTypeEtc = StringUtil.checkNull(dr.getDevReviewTypeEtc());
        devReviewDetailComment = StringUtil.checkNull(dr.getDevReviewDetailComment());

        productSaleFirstYear = StringUtil.checkNull(dr.getProductSaleFirstYear());

        initialSampleSummitDate = DateUtil.getTimeFormat(dr.getInitialSampleSummitDate(),"yyyy-MM-dd");
        eSIRDate = DateUtil.getTimeFormat(dr.getESIRDate(),"yyyy-MM-dd");
        iSIRDate = DateUtil.getTimeFormat(dr.getISIRDate(),"yyyy-MM-dd");
        ketMassProductionDate = DateUtil.getTimeFormat(dr.getKetMassProductionDate(),"yyyy-MM-dd");

         String nCodeOid = StringUtil.checkNull(dr.getProductCategoryCode());
        String productTypeCodes = StringUtil.checkNull(dr.getProductTypeCode());

        if(nCodeOid.substring(0,4).equals("e3ps")){
          NumberCode nCode = (NumberCode)CommonUtil.getObject(nCodeOid);
          productCategoryCode = nCode.getName();
          ArrayList carProductTypeCodeList = NumberCodeHelper.getChildNumberCode(nCode);
          productTypeCode = "";
          for(int j = 0 ; j < carProductTypeCodeList.size(); j++){
            String numCodeOid = ((NumberCode)carProductTypeCodeList.get(j)).getPersistInfo().getObjectIdentifier().getStringValue();
            String numCodeName = ((NumberCode)carProductTypeCodeList.get(j)).getName();
            if(productTypeCodes.indexOf(numCodeOid)>=0){
              productTypeCode = productTypeCode + " / " + numCodeName;
            }
          }
          if(productTypeCode.length()>3) productTypeCode = productTypeCode.substring(3);
        }else{
          productCategoryCode = "";
          productTypeCode = "";
        }

         etcSpecification = StringUtil.checkNull(dr.getEtcSpecification());
        tabSize = StringUtil.checkNull(dr.getTabSize());
        materialSubMaterial = StringUtil.checkNull(dr.getMaterialSubMaterial());
        surfaceTreatmentCode = StringUtil.checkNull(dr.getSurfaceTreatmentCode());

        if(surfaceTreatmentCode.equals("0")) surfaceTreatmentCode = "";

        if(!StringUtil.checkNull(surfaceTreatmentCode).equals("")){

          NumberCode nCode1 = (NumberCode)CommonUtil.getObject(surfaceTreatmentCode);
          String bName="";
          if(nCode1==null){
            bName=surfaceTreatmentCode;
          }else{
            bName=nCode1.getName();
          }
          if(!bName.equals("")) surfaceTreatmentCode=bName;
        }

        applyedWire = StringUtil.checkNull(dr.getApplyedWire());
        primaryFunction = StringUtil.checkNull(dr.getPrimaryFunction());
        outlook = StringUtil.checkNull(dr.getOutlook());

        moldDepreciationTypeSale = StringUtil.checkNull(dr.getMoldDepreciationTypeSale());
        moldDepreciationTypeGeneral = StringUtil.checkNull(dr.getMoldDepreciationTypeGeneral());
        moldDepreciationTypePayment = StringUtil.checkNull(dr.getMoldDepreciationTypePayment());
        moldDepreciationTypePeriod = StringUtil.checkNull(dr.getMoldDepreciationTypePeriod());
        moldDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getMoldDepreciationTypeEtcInfo());

        if(!moldDepreciationTypeSale.equals("")) moldDepreciationType = moldDepreciationType + "/판매";
        if(!moldDepreciationTypeGeneral.equals("")) moldDepreciationType = moldDepreciationType + "/일반";
        if(!moldDepreciationTypePayment.equals("")) moldDepreciationType = moldDepreciationType + "/지급";
        if(!moldDepreciationTypePeriod.equals("")) moldDepreciationType = moldDepreciationType + "/감가기간";
        if(!moldDepreciationTypeEtcInfo.equals("")) moldDepreciationType = moldDepreciationType + "/기타:" + moldDepreciationTypeEtcInfo;
        if(moldDepreciationType.equals("")||moldDepreciationType.equals("/")) moldDepreciationType="";
        else moldDepreciationType = moldDepreciationType.substring(1);

        equipDepreciationTypeSale = StringUtil.checkNull(dr.getEquipDepreciationTypeSale());
        equipDepreciationTypePayment = StringUtil.checkNull(dr.getEquipDepreciationTypePayment());
        equipDepreciationTypePeriod = StringUtil.checkNull(dr.getEquipDepreciationTypePeriod());
        equipDepreciationTypeEtcInfo = StringUtil.checkNull(dr.getEquipDepreciationTypeEtcInfo());

        if(!equipDepreciationTypeSale.equals("")) equipDepreciationType = equipDepreciationType + "/판매";
        if(!equipDepreciationTypePayment.equals("")) equipDepreciationType = equipDepreciationType + "/지급";
        if(!equipDepreciationTypePeriod.equals("")) equipDepreciationType = equipDepreciationType + "/감가기간";
        if(!equipDepreciationTypeEtcInfo.equals("")) equipDepreciationType = equipDepreciationType + "/기타:" + equipDepreciationTypeEtcInfo;
        if(equipDepreciationType.equals("")||equipDepreciationType.equals("/")) equipDepreciationType="";
        else equipDepreciationType = equipDepreciationType.substring(1);

        deviceSpecification = StringUtil.checkNull(dr.getDeviceSpecification());
        environmentalRegulationItem = StringUtil.checkNull(dr.getEnvironmentalRegulationItem());
        buyerEtcRequirement = StringUtil.checkNull(dr.getBuyerEtcRequirement());
        salesAdditionalRequirement = StringUtil.checkNull(dr.getSalesAdditionalRequirement());

         s_drNo = (String)drNo.get(i);

         s_developmentStep = StringUtil.checkNull((String)developmentStep.get(i));
         if(s_developmentStep.equals("R")){
           s_developmentStep = "검토의뢰";
         }else if(s_developmentStep.equals("D")){
           s_developmentStep = "개발의뢰";
         }

         s_projectNo = StringUtil.checkNull((String)projectNo.get(i));
         if(!s_projectNo.equals("")){
          Object obj = CommonUtil.getObject(s_projectNo);

          if(obj==null){
            s_projectNo = "";
          }else{
            if ( obj instanceof E3PSProject ) {
              E3PSProject project = (E3PSProject)obj;
              s_projectNo = project.getPjtNo();
            }else{
              s_projectNo = "";
            }
          }
        }

         s_repCarType = StringUtil.checkNull((String)repCarType.get(i));
         s_devProductName = StringUtil.checkNull((String)devProductName.get(i));

         s_requestBuyer = StringUtil.checkNull((String)requestBuyer.get(i));
         StringTokenizer st = new StringTokenizer(s_requestBuyer, ",");
        String ct="";
        String bName="";
        while (st.hasMoreTokens()) {
          ct=st.nextToken();
          NumberCode nCode1 = (NumberCode)CommonUtil.getObject(ct);

          if(nCode1==null){
            bName=bName + "," + ct;
          }else{
            bName=bName + "," + nCode1.getName();
          }
        }
        s_requestBuyer=bName.substring(1);


         s_devReviewTypeCode = StringUtil.checkNull((String)devReviewTypeCode.get(i));
         if(!StringUtil.checkNull(s_devReviewTypeCode).equals("")){

        NumberCode nCode1 = (NumberCode)CommonUtil.getObject(s_devReviewTypeCode);
        bName="";
        if(nCode1==null){
          bName=s_devReviewTypeCode;
        }else{
          bName=nCode1.getName();
        }
        if(!bName.equals("")) s_devReviewTypeCode=bName;
      }
      if(s_devReviewTypeCode.equals("기타")) s_devReviewTypeCode = "기타: " + devReviewTypeEtc;

         s_drState = StringUtil.checkNull((String)drState.get(i));
         s_drCreator = StringUtil.checkNull((String)drCreator.get(i));
         s_drCreateDate = StringUtil.checkNull((String)drCreateDate.get(i));
         s_partName = StringUtil.checkNull((String)partName.get(i));
         s_carTypeCode = StringUtil.checkNull((String)carTypeCode.get(i));

         KETRequestPartList pl = null;
         QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);

         String itemCnt = String.valueOf(r.size());
        String sdName = "";
        double d = 0;
        int y1  = 0;
        int y2  = 0;
        int y3  = 0;
        int y4  = 0;
        int y5  = 0;
        int y6  = 0;
        int sum = 0;
        int usg = 0;

         if(r.hasMoreElements()){
           pl = (KETRequestPartList) r.nextElement();
             String summitDestinationOid = StringUtil.checkNull(pl.getSummitDestination());

            NumberCode nCd = (NumberCode)CommonUtil.getObject(summitDestinationOid);
           if(nCd==null){
             sdName=summitDestinationOid;
           }else{
             sdName=nCd.getName();
           }

           KETCarYearlyAmount cy = null;
           QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);

           if(r1.hasMoreElements()){
             cy = (KETCarYearlyAmount) r1.nextElement();

             d = cy.getYearAmount1()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y1 = y1 + (int)d;
             d = cy.getYearAmount2()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y2 = y2 + (int)d;
             d = cy.getYearAmount3()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y3 = y3+ (int)d;
             d = cy.getYearAmount4()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y4 = y4+ (int)d;
             d = cy.getYearAmount5()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y5 = y5+ (int)d;
             d = cy.getYearAmount6()*cy.getApplyedUsage()*cy.getOptionRate()/100;
             y6 = y6 + (int)d;

             if (usg < cy.getApplyedUsage()){
               usg = cy.getApplyedUsage().intValue();
             }
           }
           sum = y1 + y2 + y3 + y4 + y5 + y6;
        }

        String s_y1  = "";
        String s_y2  = "";
        String s_y3  = "";
        String s_y4  = "";
        String s_y5  = "";
        String s_y6  = "";
        String s_sum = "";
        String s_usg = "";

         s_y1  = String.valueOf(y1);
        s_y2  = String.valueOf(y2);
        s_y3  = String.valueOf(y3);
        s_y4  = String.valueOf(y4);
        s_y5  = String.valueOf(y5);
        s_y6  = String.valueOf(y6);
        s_sum = String.valueOf(sum);
        s_usg = String.valueOf(usg);

         String s_prjCnt="";
         String s_prjNo="";
         String s_prjDeptNm="";
         String s_prjPmName="";

        QueryResult qrPrj = ProjectHelper.getDevRequestProject(dr);
        Object[] objPrj  = null;
        E3PSProject eep = null;

        if(qrPrj.hasMoreElements()){
            s_prjCnt = String.valueOf(qrPrj.size()-1);
           objPrj = (Object[])qrPrj.nextElement();
           eep = (E3PSProject)objPrj[0];
           s_prjNo = StringUtil.checkNull(eep.getPjtNo());
           if(qrPrj.size()>1){
              s_prjNo = messageService.getString("e3ps.message.ket_message", "00013", s_prjNo, s_prjCnt)/*{0}외 {1}건*/;
           }
           Kogger.debug("SearchDevRequestListExcel", null, null, "==============> "+CommonUtil.getOIDLongValue(dr));
           if("209698255".equals(Long.toString(CommonUtil.getOIDLongValue(dr))) || "133893747".equals(Long.toString(CommonUtil.getOIDLongValue(dr)))){
           //E3PSProjectData projectData = new E3PSProjectData(eep);

           if(eep instanceof ReviewProject){
               String rvOid = CommonUtil.getOIDString(eep);
               ReviewProject rp = (ReviewProject)CommonUtil.getObject(rvOid);
               if( rp.getDevDept() != null) {
                s_prjDeptNm = rp.getDevDept().getName();
               }
           }else{
               s_prjDeptNm = "";//projectData.department;
           }
           s_prjPmName = "";//projectData.pjtPmName;
           }else{
             E3PSProjectData projectData = new E3PSProjectData(eep);

         if(eep instanceof ReviewProject){
             String rvOid = CommonUtil.getOIDString(eep);
           ReviewProject rp = (ReviewProject)CommonUtil.getObject(rvOid);
           if( rp.getDevDept() != null) {
            s_prjDeptNm = rp.getDevDept().getName();
           }
           }else{
             s_prjDeptNm = projectData.department;
           }
           s_prjPmName = projectData.pjtPmName;
           }
        }

        if(s_developmentStep.equals("검토의뢰")){
          s_projectNo = s_prjNo;
           s_prjNo = "";
         }

         row++;
        //의뢰번호
        rowCount++;
        rowCnt = String.valueOf(rowCount);
        lr = new jxl.write.Label(0, row, s_drNo, cellFormat);
        s1.addCell(lr);

         //개발프로젝트번호
        lr = new jxl.write.Label(1, row, s_prjNo, cellFormat);
        s1.addCell(lr);

        //검토프로젝트번호
        lr = new jxl.write.Label(2, row, s_projectNo, cellFormat);
        s1.addCell(lr);

        //개발단계
        lr = new jxl.write.Label(3, row, s_developmentStep, cellFormat);
        s1.addCell(lr);

        //개발(검토)유형
        lr = new jxl.write.Label(4, row, s_devReviewTypeCode, cellFormat);
        s1.addCell(lr);

        //제품군
        lr = new jxl.write.Label(5, row, productCategoryCode, cellFormat);
        s1.addCell(lr);

        //개발(검토) 제품명
        lr = new jxl.write.Label(6, row, s_devProductName, cellFormat);
        s1.addCell(lr);

        //의뢰부서
        lr = new jxl.write.Label(7, row, deptName, cellFormat);
        s1.addCell(lr);

        //의뢰담당자
        lr = new jxl.write.Label(8, row, creatorName, cellFormat);
        s1.addCell(lr);

        //의뢰일자
        lr = new jxl.write.Label(9, row, insDate, cellFormat);
        s1.addCell(lr);

        //상신일자
        lr = new jxl.write.Label(10, row, requestDate, cellFormat);
        s1.addCell(lr);

        //승인일자
        lr = new jxl.write.Label(11, row, approvalDate, cellFormat);
        s1.addCell(lr);

        //개발부서
        lr = new jxl.write.Label(12, row, s_prjDeptNm, cellFormat);
        s1.addCell(lr);

        //개발담당자
        lr = new jxl.write.Label(13, row, s_prjPmName, cellFormat);
        s1.addCell(lr);

        //결재상태
        lr = new jxl.write.Label(14, row, lifeCycleState, cellFormat);
        s1.addCell(lr);

        //의뢰처 담당자
        lr = new jxl.write.Label(15, row, requestBuyerManager, cellFormat);
        s1.addCell(lr);

        //최종사용처 담당자
        lr = new jxl.write.Label(16, row, lastUsingBuyerManager, cellFormat);
        s1.addCell(lr);

        //상세내용
        lr = new jxl.write.Label(17, row, devReviewDetailComment, cellFormat);
        s1.addCell(lr);

        //품목수
        lr = new jxl.write.Label(18, row, itemCnt , cellFormat);
        s1.addCell(lr);

        //의뢰처
        lr = new jxl.write.Label(19, row, s_requestBuyer, cellFormat);
        s1.addCell(lr);

        //납입처
        lr = new jxl.write.Label(20, row, sdName , cellFormat);
        s1.addCell(lr);

        //최종사용처
        lr = new jxl.write.Label(21, row, lastUsingBuyer, cellFormat);
        s1.addCell(lr);

        //대표차종
        lr = new jxl.write.Label(22, row, s_repCarType, cellFormat);
        s1.addCell(lr);

        //US
        lr = new jxl.write.Label(23, row, s_usg , cellFormat);
        s1.addCell(lr);

        //예상수량(1년차)[천개]
        lr = new jxl.write.Label(24, row, StringUtil.getDouble(s_y1, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //예상수량(2년차)[천개]
        lr = new jxl.write.Label(25, row, StringUtil.getDouble(s_y2, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //예상수량(3년차)[천개]
        lr = new jxl.write.Label(26, row, StringUtil.getDouble(s_y3, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //예상수량(4년차)[천개]
        lr = new jxl.write.Label(27, row, StringUtil.getDouble(s_y4, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //예상수량(5년차)[천개]
        lr = new jxl.write.Label(28, row, StringUtil.getDouble(s_y5, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //예상수량(6년차~)[천개]
        lr = new jxl.write.Label(29, row, StringUtil.getDouble(s_y6, "", "###,###"), cellFormat);
        s1.addCell(lr);

        //수량합계[천개]
        lr = new jxl.write.Label(30, row, StringUtil.getDouble(s_sum, "", "###,###") , cellFormat);
        s1.addCell(lr);

        //양산 1차년도
        lr = new jxl.write.Label(31, row, productSaleFirstYear, cellFormat);
        s1.addCell(lr);

        //DR0 요구일정
        lr = new jxl.write.Label(32, row, drRequestDate, cellFormat);
        s1.addCell(lr);

        //제안서 체출일정
        lr = new jxl.write.Label(33, row, proposalSubmitDate, cellFormat);
        s1.addCell(lr);

        //개발원가 제출일정
        lr = new jxl.write.Label(34, row, costSubmitDate, cellFormat);
        s1.addCell(lr);

        //초도품 제출일정
        lr = new jxl.write.Label(35, row, initialSampleSummitDate, cellFormat);
        s1.addCell(lr);

        //ESIR일정
        lr = new jxl.write.Label(36, row, eSIRDate, cellFormat);
        s1.addCell(lr);

        //ISIR일정
        lr = new jxl.write.Label(37, row, iSIRDate, cellFormat);
        s1.addCell(lr);

        //KET 양산일정
        lr = new jxl.write.Label(38, row, ketMassProductionDate, cellFormat);
        s1.addCell(lr);

        //제품Type
        lr = new jxl.write.Label(39, row, productTypeCode, cellFormat);
        s1.addCell(lr);

        //기타 내용
        lr = new jxl.write.Label(40, row, etcSpecification, cellFormat);
        s1.addCell(lr);

        //TabSize
        lr = new jxl.write.Label(41, row, tabSize , cellFormat);
        s1.addCell(lr);

        //원자재/부자재
        lr = new jxl.write.Label(42, row, materialSubMaterial , cellFormat);
        s1.addCell(lr);

        //표면처리
        lr = new jxl.write.Label(43, row, surfaceTreatmentCode , cellFormat);
        s1.addCell(lr);

        //적용전선
        lr = new jxl.write.Label(44, row, applyedWire , cellFormat);
        s1.addCell(lr);

        //주요기능
        lr = new jxl.write.Label(45, row, primaryFunction  , cellFormat);
        s1.addCell(lr);

        //향후전망
        lr = new jxl.write.Label(46, row, outlook , cellFormat);
        s1.addCell(lr);

        //금형비 감가(판매)
        lr = new jxl.write.Label(47, row, moldDepreciationTypeSale , cellFormat);
        s1.addCell(lr);

        //금형비 감가(지급)
        lr = new jxl.write.Label(48, row, moldDepreciationTypePayment , cellFormat);
        s1.addCell(lr);

        //금형비 감가(감가기간)
        lr = new jxl.write.Label(49, row, moldDepreciationTypePeriod , cellFormat);
        s1.addCell(lr);

        //금형비 감가(기타사항)
        lr = new jxl.write.Label(50, row, moldDepreciationTypeEtcInfo , cellFormat);
        s1.addCell(lr);

        //설비비 감가(판매)
        lr = new jxl.write.Label(51, row, equipDepreciationTypeSale , cellFormat);
        s1.addCell(lr);

        //설비비 감가(지급)
        lr = new jxl.write.Label(52, row, equipDepreciationTypePayment , cellFormat);
        s1.addCell(lr);

        //설비비 감가(감가기간)
        lr = new jxl.write.Label(53, row, equipDepreciationTypePeriod , cellFormat);
        s1.addCell(lr);

        //설비비 감가(기타사항)
        lr = new jxl.write.Label(54, row, equipDepreciationTypeEtcInfo , cellFormat);
        s1.addCell(lr);

        //Device 사양
        lr = new jxl.write.Label(55, row, deviceSpecification , cellFormat);
        s1.addCell(lr);

        //환경규제항목
        lr = new jxl.write.Label(56, row, environmentalRegulationItem , cellFormat);
        s1.addCell(lr);

        //기타 요청사항
        lr = new jxl.write.Label(57, row, buyerEtcRequirement , cellFormat);
        s1.addCell(lr);

        //추가 요구사항
        lr = new jxl.write.Label(58, row, salesAdditionalRequirement , cellFormat);
        s1.addCell(lr);

      }
     }

     workbook.write();
     workbook.close();

     File file = new File(sFilePath+ "/" + sFileName);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Added by MJOH, 2011-04-18
    // 엑셀 다운로드 파일 DRM 암호화 적용
    String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
    file = E3PSDRMHelper.downloadExcel( file, sFileName, contentID, request.getRemoteAddr() );
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    FileInputStream fin = new FileInputStream(file);
    int ifilesize = (int)file.length();
    byte b[] = new byte[ifilesize];

    response.setContentLength(ifilesize);
    response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
    response.setHeader("Content-Disposition","attachment; filename="+sFileName+";");

    out.clear();
    out.close();

    ServletOutputStream oout = response.getOutputStream();
    fin.read(b);
    oout.write(b,0,ifilesize);
    oout.close();
    fin.close();
    file.delete();

  } catch (Exception e) {
      Kogger.error(e);
  }

%>
