<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.io.File,
                java.util.HashMap,
                java.util.StringTokenizer,
                java.util.Vector,
                java.util.Hashtable,
                jxl.Cell,
                jxl.Sheet,
                jxl.Workbook,
                wt.content.ContentHelper,
                wt.content.ContentHolder,
                wt.content.ContentItem,
                wt.content.ContentRoleType,
                wt.fc.PersistenceHelper,
                wt.fc.QueryResult,
                wt.folder.Folder,
                wt.folder.FolderEntry,
                wt.folder.FolderHelper,
                wt.inf.container.WTContainerRef,
                wt.org.WTUser,
                wt.part.WTPart,
                wt.pdmlink.PDMLinkProduct,
                wt.query.ClassAttribute,
                wt.query.ColumnExpression,
                wt.query.ConstantExpression,
                wt.query.QuerySpec,
                wt.query.SQLFunction,
                wt.query.SearchCondition,
                wt.session.SessionHelper,
                wt.util.WTProperties,
                e3ps.bom.service.KETPartHelper,
                e3ps.common.content.E3PSContentHelper,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.JExcelUtil,
                e3ps.common.util.ManageSequence,
                e3ps.dms.entity.KETDocumentCategory,
                e3ps.dms.entity.KETDocumentCategoryLink,
                e3ps.dms.entity.KETDocumentOutputLink,
                e3ps.dms.entity.KETDocumentPartLink,
                e3ps.dms.entity.KETDocumentProjectLink,
                e3ps.dms.entity.KETProjectDocument,
                e3ps.dms.service.KETDmsHelper,
                e3ps.common.content.fileuploader.FormUploader,
                e3ps.common.content.uploader.WBFile,
                e3ps.project.E3PSProject,
                e3ps.project.E3PSTask,
                e3ps.project.ProjectOutput,
                e3ps.project.beans.MoldProjectHelper,
                e3ps.project.beans.ProjectOutputHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    FormUploader uploader = null;
    uploader = FormUploader.newFormUploader(request);
    Hashtable param = uploader.getFormParameters();
    String fileName = "";
    WBFile wfile = null;
    Vector files = uploader.getFiles();
    if(files != null) {
        for(int i=0; i < files.size(); i++){
            wfile = (WBFile)files.elementAt(i);
            if(wfile==null) return;
            fileName = wfile.getFullPathName();
        }
    }

    File dataFile = wfile.getFile();

    Workbook wb = Workbook.getWorkbook(dataFile);
    Sheet sheets[] = wb.getSheets();

    Sheet sheet2 = sheets[2];

    String categoryCode = "";
    String categoryPath = "";
    HashMap categoryCodes = new HashMap();

    for (int i = 2; i < sheet2.getRows(); i++) {
        Cell[] cell = sheet2.getRow(i);
        categoryPath = JExcelUtil.getContent(cell, 0).trim();
        categoryCode = JExcelUtil.getContent(cell, 1).trim();
        categoryCodes.put(categoryPath, categoryCode);
    }

    Sheet sheet = sheets[0];
    Vector datas = new Vector();
    int startRow = 3;
    boolean result = true;
    StringBuffer sb = new StringBuffer();
    for (int row = startRow; row < sheet.getRows(); row++) {
        Cell[] cell = sheet.getRow(row);


        sb.append((row + 1) + " 라인=======> ");

        boolean isError = false;
        int columnIndex = 0;

        String divisionCode = JExcelUtil.getContent(cell, columnIndex++).trim();

        if (divisionCode.equals("자동차"))
            divisionCode = "CA";
        else if (divisionCode.equals("전자"))
            divisionCode = "ER";
        else {
            sb.append("사업부구분이 맞지 않습니다." + divisionCode + "\n");
            isError = true;
        }

        String deptName = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (deptName.equals("")) {
            sb.append("작성부서가 없습니다.\n");
            isError = true;
        }

        String documentNo = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!documentNo.equals("")) {
            if (!documentNo.substring(0, 2).equals("PD")) {
                sb.append("문서번호 형식이 틀립니다.\n");
                isError = true;
            }

            if (!documentNo.substring(2, 3).equals("-")) {
                sb.append("문서번호 형식이 틀립니다..\n");
                isError = true;
            }

            if (!documentNo.substring(7, 8).equals("-")) {
                sb.append("문서번호 형식이 틀립니다...\n");
                isError = true;
            }
        }

        String documentName = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (documentName.equals("")) {
            sb.append("문서명이 없습니다.\n");
            isError = true;
        }

        String documentDescription = JExcelUtil.getContent(cell, columnIndex++).trim();

        categoryPath = JExcelUtil.getContent(cell, columnIndex++).trim();
        categoryCode = (String) categoryCodes.get(categoryPath);
        if (categoryCode == null) {
            sb.append(messageService.getString("e3ps.message.ket_message", "01618") /*분류체계코드가 존재하지 않습니다*/+ "\n");
            isError = true;
        }

        KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);
        if (docCate == null) {
            sb.append(messageService.getString("e3ps.message.ket_message", "01617") /*분류체계코드가 맞지 않습니다*/ + "\n");
            isError = true;
        }


        String fileServer = "\\\\192.168.17.13";
        String fileNm = JExcelUtil.getContent(cell, columnIndex++).trim();
        String filePath = JExcelUtil.getContent(cell, columnIndex++).trim();

        File pFile = new File( fileServer + "\\" + filePath + "\\" + fileNm);
        if (!pFile.exists()) {
            sb.append(
                    messageService.getString("e3ps.message.ket_message", "03187") /*해당 파일이 없습니다*/ + "[" + fileServer + "\\" + filePath + "\\" + fileNm + "]\n");
            isError = true;
        }
        pFile = null;


        String projectNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
        E3PSProject project1 = null;

        if (!projectNo1.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo1 = projectNo1.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo1);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project1 = (E3PSProject) rs.nextElement();
            }

            if (project1 == null) {
                isError = true;
                sb.append(" ProjectNo1:" + projectNo1 + " 정보가 맞지 않습니다.\n");
            }
        }

        String projectNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();
        E3PSProject project2 = null;
        if (!projectNo2.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo2 = projectNo2.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo2);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project2 = (E3PSProject) rs.nextElement();
            }

            if (project2 == null) {
                isError = true;
                sb.append(" ProjectNo2:" + projectNo2 + " 정보가 맞지 않습니다.\n");
            }
        }

        String projectNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();
        E3PSProject project3 = null;
        if (!projectNo3.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo3 = projectNo3.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo3);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project3 = (E3PSProject) rs.nextElement();
            }

            if (project3 == null) {
                isError = true;
                sb.append(" ProjectNo3:" + projectNo3 + " 정보가 맞지 않습니다.\n");
            }
        }

        String projectNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();
        E3PSProject project4 = null;
        if (!projectNo4.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo4 = projectNo4.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo4);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project4 = (E3PSProject) rs.nextElement();
            }

            if (project4 == null) {
                isError = true;
                sb.append(" ProjectNo4:" + projectNo4 + " 정보가 맞지 않습니다.\n");
            }
        }

        String projectNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();
        E3PSProject project5 = null;
        if (!projectNo5.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo5 = projectNo5.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo5);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project5 = (E3PSProject) rs.nextElement();
            }

            if (project5 == null) {
                isError = true;
                sb.append(" ProjectNo5:" + projectNo5 + " 정보가 맞지 않습니다.\n");
            }
        }

        String taskName = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!taskName.equals("")) {
            if (projectNo1.equals("")) {
                isError = true;
                sb.append(messageService.getString("e3ps.message.ket_message", "00498")/*task명 정보는 projectNo1정보와 같이 입력되어야 합니다*/ + "\n");
            } else {
                E3PSTask et = MoldProjectHelper.getTask((E3PSProject) project1,
                        taskName);
                if (et == null) {
                    isError = true;
                    sb.append(messageService.getString("e3ps.message.ket_message", "00497")/*task명 정보가 맞지 않습니다*/ + "\n");
                }
            }
        }

        String partNo1 = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!partNo1.equals("")) {
            WTPart wtpart1 = KETPartHelper.service.getPart(partNo1);
            if (wtpart1 == null) {
                sb.append(messageService.getString("e3ps.message.ket_message", "01589", 1) /*부품번호{0}이(가) 맞지 않습니다*/ + "\n");
                isError = true;
            }
        }

        String partNo2 = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!partNo2.equals("")) {
            WTPart wtpart2 = KETPartHelper.service.getPart(partNo2);
            if (wtpart2 == null) {
                sb.append(messageService.getString("e3ps.message.ket_message", "01589", 2) /*부품번호{0}이(가) 맞지 않습니다*/ + "\n");
                isError = true;
            }
        }

        String partNo3 = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!partNo3.equals("")) {
            WTPart wtpart3 = KETPartHelper.service.getPart(partNo3);
            if (wtpart3 == null) {
                sb.append(messageService.getString("e3ps.message.ket_message", "01589", 3) /*부품번호{0}이(가) 맞지 않습니다*/ + "\n");
                isError = true;
            }
        }

        String partNo4 = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!partNo4.equals("")) {
            WTPart wtpart4 = KETPartHelper.service.getPart(partNo4);
            if (wtpart4 == null) {
                sb.append(messageService.getString("e3ps.message.ket_message", "01589", 4) /*부품번호{0}이(가) 맞지 않습니다*/ + "\n");
                isError = true;
            }
        }

        String partNo5 = JExcelUtil.getContent(cell, columnIndex++).trim();
        if (!partNo5.equals("")) {
            WTPart wtpart5 = KETPartHelper.service.getPart(partNo5);
            if (wtpart5 == null) {
                sb.append(messageService.getString("e3ps.message.ket_message", "01589", 5) /*부품번호{0}이(가) 맞지 않습니다*/ + "\n");
                isError = true;
            }
        }

        if (isError) {
            sb.append("failure...............\n");
        } else {
            HashMap map = new HashMap();
            map.put("divisionCode", divisionCode);
            map.put("deptName", deptName);
            map.put("documentNo", documentNo);
            map.put("documentName", documentName);
            map.put("documentDescription", documentDescription);
            map.put("categoryCode", categoryCode);
            map.put("fileName", fileServer + "\\" + filePath + "\\" + fileNm);
            map.put("projectNo1", projectNo1);
            map.put("projectNo2", projectNo2);
            map.put("projectNo3", projectNo3);
            map.put("projectNo4", projectNo4);
            map.put("projectNo5", projectNo5);
            map.put("taskName", taskName);
            map.put("partNo1", partNo1);
            map.put("partNo2", partNo2);
            map.put("partNo3", partNo3);
            map.put("partNo4", partNo4);
            map.put("partNo5", partNo5);
            datas.add(map);
        }
    }

    String aRoot = "/Default/자동차사업부/문서";
    String eRoot = "/Default/전자사업부/문서";
    String divisionCode = null;
    String deptName = null;
    String documentNo = null;
    String documentName = null;
    String documentDescription = null;
    String projectNo1 = null;
    String projectNo2 = null;
    String projectNo3 = null;
    String projectNo4 = null;
    String projectNo5 = null;
    String taskName = null;
    String partNo1 = null;
    String partNo2 = null;
    String partNo3 = null;
    String partNo4 = null;
    String partNo5 = null;

    KETProjectDocument d = null;
    E3PSProject project = null;
    E3PSTask et = null;
    ProjectOutput po = null;
    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
    WTPart wtpart = null;
    KETDocumentCategoryLink DCLink = null;
    KETDocumentOutputLink DoLink = null;
    KETDocumentProjectLink DPrLink = null;
    KETDocumentPartLink DpLink = null;
    File pFile = null;

    for (int i = 0; i < datas.size(); i++) {
        HashMap map = (HashMap) datas.get(i);

        divisionCode = (String) map.get("divisionCode");
        deptName = (String) map.get("deptName");

        documentNo = (String) map.get("documentNo");
        if (documentNo.equals("")) {
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd");
            java.util.Date currentTime = new java.util.Date();
            String ymonth = formatter.format(currentTime).substring(2, 4)
                    + formatter.format(currentTime).substring(5, 7);
            documentNo = "PD-" + ymonth + "-";
            documentNo += ManageSequence.getSeqNo(documentNo, "0000",
                    "WTDocumentMaster", "WTDocumentNumber");
        }

        documentName = (String) map.get("documentName");
        documentDescription = (String) map.get("documentDescription");

        d = KETProjectDocument.newKETProjectDocument();

        d.setDivisionCode(divisionCode);
        d.setDeptName(deptName);
        d.setDocumentNo(documentNo);
        d.setNumber(documentNo);
        d.setTitle(documentName);
        d.setName(documentName);
        d.setDocumentDescription(documentDescription);
        d.setFirstRegistrationStage(1);
        d.setSecurity("S1");
        d.setIsBuyerSummit("2");

        categoryCode = (String) map.get("categoryCode");
        categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);

        PDMLinkProduct wtProduct = null;
        WTContainerRef wtContainerRef = null;

        QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
        SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class,
                PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
        qs.appendSearchCondition(sc1);
        QueryResult results = (QueryResult) PersistenceHelper.manager
                .find(qs);
        if (results.hasMoreElements()) {
            wtProduct = (PDMLinkProduct) results.nextElement();
            wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
        }

        Folder cateFolder = null;
        if (divisionCode.equals("CA")) {
            cateFolder = FolderHelper.service.getFolder(aRoot
                    + categoryPath, wtContainerRef);
        } else if (divisionCode.equals("ER")) {
            cateFolder = FolderHelper.service.getFolder(eRoot
                    + categoryPath, wtContainerRef);
        } else {
            cateFolder = FolderHelper.service.getFolder(aRoot
                    + categoryPath, wtContainerRef);
        }
        FolderHelper.assignLocation((FolderEntry) d, cateFolder);

        d = (KETProjectDocument) PersistenceHelper.manager.save(d);

        //Kogger.debug("saved doc oid==>" + d.toString());

        KETDocumentCategory docCate = KETDmsHelper.service.selectDocCategory(categoryCode);
        //Kogger.debug("docCate==>" + docCate.toString());
        DCLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(
                docCate, d);
        // wt.fc.PersistenceServerHelper.manager.insert(DCLink);
        DCLink = (KETDocumentCategoryLink) PersistenceHelper.manager.save(DCLink);

        projectNo1 = (String) map.get("projectNo1");
        taskName = (String) map.get("taskName");
        if ((!projectNo1.equals("")) && (!taskName.equals(""))) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo1 = projectNo1.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo1);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            et = MoldProjectHelper.getTask((E3PSProject) project, taskName);

            QueryResult qr = ProjectOutputHelper.manager
                    .getTaskOutputDocLink(et);
            if (qr.hasMoreElements()) {
                Object[] obj = (Object[]) qr.nextElement();
                po = (ProjectOutput) obj[0];
            } else {
                HashMap poMap = new HashMap();
                String oid = CommonUtil.getOIDString(project);
                String taskOid = CommonUtil.getOIDString(et);
                String name = documentName;
                String description = documentDescription;
                String docTypeOid = categoryCode;
                String outputUser = CommonUtil.getOIDString(user);
                String isPrimary = "1";

                poMap.put("oid", oid);
                poMap.put("taskOid", taskOid);
                poMap.put("name", name);
                poMap.put("description", description);
                poMap.put("docTypeOid", docTypeOid);
                poMap.put("outputUser", outputUser);
                poMap.put("objType", "DOC");
                poMap.put("isPrimary", isPrimary);

                try {
                    po = ProjectOutputHelper.saveDefProjectOutput(map);
                } catch (Exception e) {
                    Kogger.error(e);
                    po = null;
                }
            }

            if (po != null) {
                DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d,
                        po);
                wt.fc.PersistenceServerHelper.manager.insert(DoLink);
                ProjectOutputHelper.manager.registryProjectOutput(po, d);
            }
        } else if (!projectNo1.equals("")) {
            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo1 = projectNo1.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo1);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d,project);
            wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
        }

        projectNo2 = (String) map.get("projectNo2");
        if (!projectNo2.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo2 = projectNo2.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo2);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d,
                    project);
            wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
        }

        projectNo3 = (String) map.get("projectNo3");
        if (!projectNo3.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo3 = projectNo3.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo3);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d,
                    project);
            wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
        }

        projectNo4 = (String) map.get("projectNo4");
        if (!projectNo4.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo4 = projectNo4.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo4);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d,
                    project);
            wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
        }

        projectNo5 = (String) map.get("projectNo5");
        if (!projectNo5.equals("")) {

            QuerySpec spec = new QuerySpec(E3PSProject.class);

            spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest",
                    SearchCondition.IS_TRUE, true), new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            spec.appendWhere(new SearchCondition(E3PSProject.class,
                    "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
                    new int[] { 0 });

            if (spec.getConditionCount() > 0) {
                spec.appendAnd();
            }

            ClassAttribute ca0 = new ClassAttribute(E3PSProject.class,
                    E3PSProject.PJT_NO);
            SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);

            projectNo5 = projectNo5.toUpperCase();
            ColumnExpression ce = ConstantExpression.newExpression(projectNo5);
            spec.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce),
                    new int[] { 0 });

            QueryResult rs = PersistenceHelper.manager.find(spec);

            if (rs.hasMoreElements()) {
                project = (E3PSProject) rs.nextElement();
            }

            DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d,
                    project);
            wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
        }

        partNo1 = (String) map.get("partNo1");
        if (!partNo1.equals("")) {
            wtpart = KETPartHelper.service.getPart(partNo1);
            DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
            wt.fc.PersistenceServerHelper.manager.insert(DpLink);
        }

        partNo2 = (String) map.get("partNo2");
        if (!partNo2.equals("")) {
            wtpart = KETPartHelper.service.getPart(partNo2);
            DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
            wt.fc.PersistenceServerHelper.manager.insert(DpLink);
        }

        partNo3 = (String) map.get("partNo3");
        if (!partNo3.equals("")) {
            wtpart = KETPartHelper.service.getPart(partNo3);
            DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
            wt.fc.PersistenceServerHelper.manager.insert(DpLink);
        }

        partNo4 = (String) map.get("partNo4");
        if (!partNo4.equals("")) {
            wtpart = KETPartHelper.service.getPart(partNo4);
            DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
            wt.fc.PersistenceServerHelper.manager.insert(DpLink);
        }

        partNo5 = (String) map.get("partNo5");
        if (!partNo5.equals("")) {
            wtpart = KETPartHelper.service.getPart(partNo5);
            DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
            wt.fc.PersistenceServerHelper.manager.insert(DpLink);
        }

        fileName = (String) map.get("fileName");
        pFile = new File(fileName);

        ContentHolder holder = ContentHelper.service.getContents(d);
        E3PSContentHelper.service.attach(holder, pFile, "",
                ContentRoleType.PRIMARY);

        //Kogger.debug("attached file ok==>" + fileName);
        sb.append(" Success.\n");
        pFile = null;

    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script language="JavaScript">
<!--



//-->
</script>
<script id='dummy'></script>
</head>
<body>
<form name=form01 method="post" >
<table width="780" height="100" border="0" cellspacingf="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0" >
       <tr>
          <td  class="head_line"></td>
       </tr>
       <tr>
          <td class="tdwhiteL0">&nbsp;</td>
          <td class="tdwhiteL0">&nbsp;</td>
       </tr>
       <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02118") %><%--엑셀파일선택--%></td>
          <td class="tdwhiteL"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td><%=fileName%></td>

              </tr>
          </table></td>
        </tr>
        </table>
     </td>
  </tr>
  <tr>
      <td class="tdwhiteL" style="height:500"><textarea name="rOutput" class="txt_field" style="width:770; height:99%" ><%=sb.toString()%></textarea></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
