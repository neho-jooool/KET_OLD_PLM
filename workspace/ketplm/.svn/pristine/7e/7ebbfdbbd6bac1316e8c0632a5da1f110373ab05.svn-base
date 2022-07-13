<%@page import="e3ps.common.drm.E3PSDRMHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
				e3ps.dms.entity.*,
				e3ps.dms.beans.DMSUtil,
				wt.fc.QueryResult,
				wt.fc.PersistenceHelper,
				wt.doc.WTDocument,
				wt.doc.WTDocumentMaster,
				wt.content.*,
				wt.lifecycle.LifeCycleHelper,
				wt.lifecycle.LifeCycleManaged,
				e3ps.common.content.*,
				e3ps.ecm.entity.KETMoldECADocLink,
				e3ps.ecm.entity.KETMoldChangeActivity,
				e3ps.ecm.entity.KETMoldChangeActivityLink,
				e3ps.ecm.entity.KETMoldChangeOrder,
				e3ps.ecm.entity.KETProdECADocLink,
				e3ps.ecm.entity.KETProdChangeActivity,
				e3ps.ecm.entity.KETProdChangeActivityLink,
				e3ps.ecm.entity.KETProdChangeOrder,
				e3ps.wfm.entity.KETWfmApprovalMaster,
				e3ps.wfm.entity.KETWfmApprovalHistory,
				wt.fc.*,
				e3ps.common.service.*,
				e3ps.wfm.util.WorkflowSearchHelper,
				java.util.Vector,
				java.util.Hashtable,
				java.util.StringTokenizer,
				java.text.SimpleDateFormat,
				java.util.Date,
				java.io.*,
				wt.part.WTPart,
				jxl.Workbook,
				jxl.format.Border,
				jxl.format.BorderLineStyle,
				jxl.write.Label,
				jxl.write.WritableCellFormat,
				jxl.write.WritableSheet,
				jxl.write.WritableWorkbook,
				jxl.write.WriteException,
				e3ps.project.*,
			    e3ps.project.beans.*,
				e3ps.common.util.CommonUtil,
				e3ps.common.util.StringUtil,
				e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

KETProjectDocument docu = null;
	Hashtable tempHash = new Hashtable();
	int i;
	String docuOid;

	String cmd = StringUtil.checkNull(request.getParameter("cmd"));
	String documentNo = StringUtil.checkNull(request.getParameter("documentNo"));
	String divisionCode = StringUtil.checkNull(request.getParameter("divisionCode"));
    String categoryCode = StringUtil.checkNull(request.getParameter("categoryCode"));
    String documentName = StringUtil.checkNull(request.getParameter("documentName"));
    String authorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
    String creator = StringUtil.checkNull(request.getParameter("creator"));
    String predate = StringUtil.checkNull(request.getParameter("predate"));
    String postdate = StringUtil.checkNull(request.getParameter("postdate"));
    String isBuyerSummit = StringUtil.checkNull(request.getParameter("isBuyerSummit"));
    String buyerCodeStr = StringUtil.checkNull(request.getParameter("buyerCodeStr"));
    String islastversion = StringUtil.checkNull(request.getParameter("islastversion"));
    String sortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String sortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
    String projectNo = StringUtil.checkNull(request.getParameter("projectNo"));
    String projectName = StringUtil.checkNull(request.getParameter("projectName"));

    sortKeyD=StringUtil.checkNull(sortKeyD);
	if(sortKeyD.equals("ASC")){
		sortKeyD="DESC";
	}else{
		sortKeyD="ASC";
	}

	tempHash.put("documentNo" , new String(documentNo));
	if(divisionCode.equals("0")) divisionCode = "";
	tempHash.put("divisionCode" , new String(divisionCode));
	tempHash.put("categoryCode" , new String(categoryCode));
	tempHash.put("documentName" , new String(documentName));
	tempHash.put("authorStatus" , new String(authorStatus));
	tempHash.put("creator" , new String(creator));
	tempHash.put("predate" , new String(predate));
	tempHash.put("postdate" , new String(postdate));
	tempHash.put("isBuyerSummit" , new String(isBuyerSummit));
	tempHash.put("buyerCodeStr" , new String(buyerCodeStr));
	tempHash.put("islastversion" , new String(islastversion));
	tempHash.put("quality" , "0");
	tempHash.put("sortKey" , new String(sortKey));
	tempHash.put("sortKeyD" , new String(sortKeyD));

	Vector docuOids = DMSUtil.serDocumentList(tempHash);

	if((!projectNo.equals(""))||(!projectName.equals(""))){
		Boolean isPrj = false;
	    String pjtNo = "";
	    String pjtName = "";

	    if(docuOids.size()>0){

	       for(i = docuOids.size()-1; i >= 0; i--) {
	    	   docuOid = docuOids.get(i).toString();
	   		   docu = (KETProjectDocument)CommonUtil.getObject(docuOid);

	   		    isPrj = false;
	   		    pjtNo = "";
	   		    pjtName = "";
				E3PSProject project = null;
				ProjectOutput po;
		        QueryResult r3 = PersistenceHelper.manager.navigate(docu, "output" , KETDocumentOutputLink.class);
		        if(r3.hasMoreElements()){
		    	    po = (ProjectOutput)r3.nextElement();
		    	    E3PSTask task = (E3PSTask)po.getTask();
		    	    project = (E3PSProject)task.getProject();
					pjtNo = StringUtil.checkNull(project.getPjtNo());
					pjtName = StringUtil.checkNull(project.getPjtName());

					if((!projectNo.equals(""))&&(!projectName.equals(""))){
						if((pjtNo.indexOf(projectNo)>=0)&&(pjtName.indexOf(projectName)>=0)){
							isPrj = true;
						}
					}else if(!projectNo.equals("")){
						if(pjtNo.indexOf(projectNo)>=0){
							isPrj = true;
						}
					}else if(!projectName.equals("")){
						if(pjtName.indexOf(projectName)>=0){
							isPrj = true;
						}
					}
		    	}

		        QueryResult qr = PersistenceHelper.manager.navigate(docu, "project" , KETDocumentProjectLink.class );
		        if(qr.hasMoreElements()) {
			    	while(qr.hasMoreElements()) {
				   		project = (E3PSProject) qr.nextElement();

						pjtNo = StringUtil.checkNull(project.getPjtNo());
						pjtName = StringUtil.checkNull(project.getPjtName());

						if((!projectNo.equals(""))&&(!projectName.equals(""))){
							if((pjtNo.indexOf(projectNo)>=0)&&(pjtName.indexOf(projectName)>=0)){
								isPrj = true;
							}
						}else if(!projectNo.equals("")){
							if(pjtNo.indexOf(projectNo)>=0){
								isPrj = true;
							}
						}else if(!projectName.equals("")){
							if(pjtName.indexOf(projectName)>=0){
								isPrj = true;
							}
						}
					}
				}

	   		    if(isPrj==false){
	   		   		docuOids.remove(i);
	   		    }
	   		}
   		}
	}

	String sWtHome = "";
	String sFilePath = "", sFileName = "";

	sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	sFilePath = sWtHome + "/codebase/jsp/dms" ;

	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	sFileName = "SearchProjectDocumentList_" +  ff.format(new Date()) + ".xls";

	try {

		WritableWorkbook workbook = Workbook.createWorkbook(new File( sFilePath+ "/" + sFileName ));
		WritableSheet s1 = workbook.createSheet("First Sheet", 0);

		s1.setName("문서검색목록");

		WritableCellFormat cellFormat = new WritableCellFormat();
	    cellFormat.setBorder(Border.ALL , BorderLineStyle.THIN);      //셀의 스타일을 지정

        //문서제목
		Label lr = new jxl.write.Label(0, 0, "문서검색목록");
		s1.addCell(lr);

		int row = 2;
		int rowCount = 0;
		String rowCnt;

		lr = new jxl.write.Label(0, row, "사업부", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(1, row, "문서번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(2, row, "버전", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(3, row, "결재상태", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(4, row, "대분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(5, row, "중분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(6, row, "소분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(7, row, "문서명", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(8, row, "문서설명", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(9, row, "작성자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(10, row, "작성부서", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(11, row, "작성일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(12, row, "상신일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(13, row, "승인일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(14, row, "최초 등록시점", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(15, row, "고객 제출자료", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(16, row, "고객사", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(17, row, "DR CheckSheet 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(18, row, "DR평가점수", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(19, row, "승인결과", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(20, row, "최종승인 의견", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(21, row, "APQP 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(22, row, "PSO10 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(23, row, "ESIR 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(24, row, "ISIR(Car) 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(25, row, "ISIR(Elec) 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(26, row, "ANPQP 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(27, row, "SYMC 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(28, row, "프로젝트 번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(29, row, "부품 번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(30, row, "설계변경 번호", cellFormat);
		s1.addCell(lr);

		if(docuOids.size()>0){
	 	 QueryResult r = null;
	 	 KETDocumentCategory docCate = null;
	 	 String docCatePath = "";
	 	 String versionInfo = "";
	 	 String lifeCycleState = "";
	 	 String insUser = "";
	 	 String insDate = "";

	 	 FormatContentHolder holder = null;
	 	 ContentInfo info = null;
	 	 ContentItem ctf = null;
	 	 String appDataOid = "";
	 	 String urlpath = "";
	 	 String iconpath = "";
	 	 String activityName = "";
	 	 String completeDate = "";
	 	 String approvalDate = "";
	  	 String requestDate = "";


	 	 Integer tmpInt = 0;

	     for(i = 0; i < docuOids.size(); i++) {



	   		docuOid = docuOids.get(i).toString();
	   		docu = (KETProjectDocument)CommonUtil.getObject(docuOid);
	   		//=========결재 요청일,승인일 관련 시작====================//
	   		Object obj = CommonUtil.getObject(docuOid);
	   		ReferenceFactory rf = new ReferenceFactory();
	   		KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
	   		KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
	   		KETWfmApprovalMaster master = null;
	   		Object temp = new Object();
	   		Vector vec = null;
	   		WTObject targetObj = KETCommonHelper.service.getPBO((WTObject)CommonUtil.getObject(docuOid));
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
	   		row++;
			rowCount++;
			rowCnt = String.valueOf(rowCount);

			divisionCode =  StringUtil.checkNull(docu.getDivisionCode());
			if(divisionCode.equals("CA")){
				divisionCode = "자동차";
			}else if(divisionCode.equals("ER")){
				divisionCode = "전자";
			}else{
				divisionCode = "-";
			}

			//사업부
			lr = new jxl.write.Label(0, row, divisionCode, cellFormat);
			s1.addCell(lr);
			//문서번호
			lr = new jxl.write.Label(1, row, docu.getDocumentNo(), cellFormat);
			s1.addCell(lr);
			//버전
			lr = new jxl.write.Label(2, row, StringUtil.checkNull(docu.getVersionIdentifier().getValue()), cellFormat);
			s1.addCell(lr);
			//결재상태
			lr = new jxl.write.Label(3, row, StringUtil.checkNull(docu.getLifeCycleState().getDisplay()), cellFormat);
			s1.addCell(lr);

			String isDRCheckSheet = "비대상";
			String isAPQP = "비대상";
			String isPSO10 = "비대상";
			String isESIR = "비대상";
			String isISIRCar = "비대상";
			String isISIRElec = "비대상";
			String isANPQP = "비대상";
			String isSYMC = "비대상";
			String docCatePath1 = "";
			String docCatePath2 = "";
			String docCatePath3 = "";

			try{
				docCate = null;
				r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
				if (r.hasMoreElements()){
	            	docCate = (KETDocumentCategory) r.nextElement();
	            	categoryCode=docCate.getCategoryCode();

	            	if(docCate.getIsDRCheckSheet()) isDRCheckSheet ="대상";
	            	if(docCate.getIsAPQP()) isAPQP ="대상";
				    if(docCate.getIsPSO10()) isPSO10 ="대상";
				    if(docCate.getIsESIR()) isESIR ="대상";
				    if(docCate.getIsISIRCar()) isISIRCar ="대상";
				    if(docCate.getIsISIRElec()) isISIRElec ="대상";
				    if(docCate.getIsANPQP()) isANPQP ="대상";
				    if(docCate.getIsSYMC()) isSYMC ="대상";

	            	docCatePath= KETDmsHelper.service.selectCategoryPath(categoryCode);
	            	docCatePath=docCatePath.substring(1);

	            	StringTokenizer st = new StringTokenizer(docCatePath, "/");
		            if (st.hasMoreTokens()) {
		        	   docCatePath1 = st.nextToken();
	            	}
	            	if (st.hasMoreTokens()) {
		        	   docCatePath2 = st.nextToken();
	            	}
	            	if (st.hasMoreTokens()) {
		        	   docCatePath3 = st.nextToken();
	            	}
	           }
			}catch (Exception e) {
			    Kogger.error(e);
			}

			//대분류
			lr = new jxl.write.Label(4, row, StringUtil.checkNull(docCatePath1), cellFormat);
			s1.addCell(lr);
			//중분류
			lr = new jxl.write.Label(5, row, StringUtil.checkNull(docCatePath2), cellFormat);
			s1.addCell(lr);
			//소분류
			lr = new jxl.write.Label(6, row, StringUtil.checkNull(docCatePath3), cellFormat);
			s1.addCell(lr);
			//문서명
			lr = new jxl.write.Label(7, row, StringUtil.checkNull(docu.getTitle()), cellFormat);
			s1.addCell(lr);
			//문서설명
			lr = new jxl.write.Label(8, row, StringUtil.checkNull(docu.getDocumentDescription()), cellFormat);
			s1.addCell(lr);
			//작성자
			lr = new jxl.write.Label(9, row, StringUtil.checkNull(docu.getIterationInfo().getCreator().getFullName()), cellFormat);
			s1.addCell(lr);
			//작성부서
			lr = new jxl.write.Label(10, row, StringUtil.checkNull(docu.getDeptName()), cellFormat);
			s1.addCell(lr);
			//작성일자
			lr = new jxl.write.Label(11, row, DateUtil.getTimeFormat(docu.getCreateTimestamp(),"yyyy-MM-dd"), cellFormat);
			s1.addCell(lr);

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

	             	 if(x == iComplet && isApprover && activityName.equals("검토") )
	             	 {
	             		 activityName = "승인";
	             		 isApprover = false;
	             	 }
	             	if(history.getCompletedDate()!=null)completeDate = DateUtil.getTimeFormat(history.getCompletedDate(),"yyyy-MM-dd");
	             	if(activityName.equals("승인"))approvalDate =  completeDate;
	             	if(activityName.equals("요청"))requestDate =  completeDate;

				}
            }

			//상신일자
			lr = new jxl.write.Label(12, row, requestDate, cellFormat);
			s1.addCell(lr);

			//승인일자
			lr = new jxl.write.Label(13, row, approvalDate, cellFormat);
			s1.addCell(lr);


			//최초 등록시점
			tmpInt = docu.getFirstRegistrationStage();
			String firstRegistrationStage = tmpInt.toString();

			if(firstRegistrationStage.equals("1")){
				firstRegistrationStage = "개발단계";
			}
			if(firstRegistrationStage.equals("2")){
				firstRegistrationStage = "양산단계";
			}

			lr = new jxl.write.Label(14, row, StringUtil.checkNull(firstRegistrationStage), cellFormat);
			s1.addCell(lr);

			String buyerCode = "";
			isBuyerSummit = StringUtil.checkNull(docu.getIsBuyerSummit());
			if(isBuyerSummit.equals("1")){
				isBuyerSummit = "대상";
				buyerCode = StringUtil.checkNull(docu.getBuyerCode());

				if(!StringUtil.checkNull(buyerCode).equals("")){
					StringTokenizer st = new StringTokenizer(buyerCode, ",");
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
					if(!bName.equals("")){
						buyerCode=bName.substring(1);
					}
				}

			}else{
				isBuyerSummit = "비대상";
				buyerCode = "-";
			}


			//고객 제출자료
			lr = new jxl.write.Label(15, row, isBuyerSummit, cellFormat);
			s1.addCell(lr);
			//고객사
			lr = new jxl.write.Label(16, row, buyerCode, cellFormat);
			s1.addCell(lr);
			//DR CheckSheet 대상
			lr = new jxl.write.Label(17, row, isDRCheckSheet, cellFormat);
			s1.addCell(lr);

			//DR평가점수
			tmpInt = docu.getDRCheckPoint();
			lr = new jxl.write.Label(18, row, tmpInt.toString(), cellFormat);
			s1.addCell(lr);

			//승인결과
			lr = new jxl.write.Label(19, row, StringUtil.checkNull(docu.getApprovalResult()), cellFormat);
			s1.addCell(lr);
			//최종승인 의견
			lr = new jxl.write.Label(20, row, StringUtil.checkNull(docu.getLastApprovalComment()), cellFormat);
			s1.addCell(lr);

			//APQP 대상
			lr = new jxl.write.Label(21, row, isAPQP, cellFormat);
			s1.addCell(lr);
			//PSO10 대상
			lr = new jxl.write.Label(22, row, isPSO10, cellFormat);
			s1.addCell(lr);
			//ESIR 대상
			lr = new jxl.write.Label(23, row, isESIR, cellFormat);
			s1.addCell(lr);
			//ISIR(Car) 대상
			lr = new jxl.write.Label(24, row, isISIRCar, cellFormat);
			s1.addCell(lr);
			//ISIR(Elec) 대상
			lr = new jxl.write.Label(25, row, isISIRElec, cellFormat);
			s1.addCell(lr);
			//ANPQP 대상
			lr = new jxl.write.Label(26, row, isANPQP, cellFormat);
			s1.addCell(lr);
			//SYMC 대상
			lr = new jxl.write.Label(27, row, isSYMC, cellFormat);
			s1.addCell(lr);

			String pjtNo = "0";
			try{
				E3PSProject project = null;
				ProjectOutput po;
		        QueryResult r4 = PersistenceHelper.manager.navigate(docu, "output" , KETDocumentOutputLink.class);
		        if(r4.hasMoreElements()){
		    	    po = (ProjectOutput)r4.nextElement();
		    	    E3PSTask task = (E3PSTask)po.getTask();
		    	    project = (E3PSProject)task.getProject();

					pjtNo = StringUtil.checkNull(project.getPjtNo());
		    	}


		        QueryResult r5 = PersistenceHelper.manager.navigate(docu, "project" , KETDocumentProjectLink.class );
		    	while (r5.hasMoreElements()) {
			   		project = (E3PSProject) r5.nextElement();
			   		if(pjtNo.equals("0")){
						pjtNo = StringUtil.checkNull(project.getPjtNo());
					}else{
						pjtNo = pjtNo + "," + StringUtil.checkNull(project.getPjtNo());
					}
				}
				if(pjtNo.equals("0")){
					pjtNo = "";
				}
			}catch (Exception e) {
			    Kogger.error(e);
			}

			//프로젝트번호
			lr = new jxl.write.Label(28, row, pjtNo, cellFormat);
			s1.addCell(lr);

            String partNo = "";
            try{
	            WTPart wtPart=null;
				QueryResult qr = PersistenceHelper.manager.navigate(docu, "part", KETDocumentPartLink.class);
	            if (qr.hasMoreElements()){
	            	while (qr.hasMoreElements()) {
				   		wtPart = (WTPart) qr.nextElement();
				   		partNo = partNo + "," + wtPart.getNumber();
				   	}
				   	partNo = partNo.substring(1);
				}
			}catch (Exception e) {
			    Kogger.error(e);
			}

			//부품 번호
			lr = new jxl.write.Label(29, row, partNo, cellFormat);
			s1.addCell(lr);

			String ecoId = "";
			try{
				KETProdChangeActivity pca;
	            QueryResult qr = PersistenceHelper.manager.navigate(docu, "prodECA" , KETProdECADocLink.class );
	        	while (qr.hasMoreElements()) {
	        		pca = (KETProdChangeActivity) qr.nextElement();
	        		QueryResult qr1 = PersistenceHelper.manager.navigate( pca ,"prodECO", KETProdChangeActivityLink.class );
	        		if( qr1.hasMoreElements() ){
						KETProdChangeOrder ecoObj = (KETProdChangeOrder)qr1.nextElement();
						ecoId = ecoId + "," + ecoObj.getEcoId();
					}
				}
			}catch (Exception e) {
			    Kogger.error(e);
			}

			try{
				KETMoldChangeActivity mca;
	            QueryResult qr = PersistenceHelper.manager.navigate(docu, "moldECA" , KETMoldECADocLink.class );
	        	while (qr.hasMoreElements()) {
	        		mca = (KETMoldChangeActivity) qr.nextElement();
	        		QueryResult qr1 = PersistenceHelper.manager.navigate( mca ,"moldECO", KETMoldChangeActivityLink.class );
	        		if( qr1.hasMoreElements() ){
						KETMoldChangeOrder ecoObj = (KETMoldChangeOrder)qr1.nextElement();
						//ecoId = ecoId + "," + CommonUtil.getOIDString(ecoObj);
						ecoId = ecoId + "," + ecoObj.getEcoId();
					}
				}
			}catch (Exception e) {
			    Kogger.error(e);
			}

			//설계변경 번호
			lr = new jxl.write.Label(30, row, ecoId, cellFormat);
			s1.addCell(lr);



	   	 }
	   	}
		workbook.write();
		workbook.close();
	} catch (Exception e) {
	    Kogger.error(e);
	   throw e;

	}

	try{
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

	}catch (Exception e) {
	    Kogger.error(e);
	   throw e;

	}

%>
