<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="ext.ket.shared.mail.service.KETMailHelper"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.org.*,wt.query.*,wt.team.*,wt.project.Role, wt.httpgw.*"%>
<%@page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.content.fileuploader.*"%>
<%@page import="e3ps.common.content.uploader.*"%>
<%@page import="e3ps.common.content.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<stdinfo>
    <results>
        <contents>
<%
   
    FormUploader uploader = null;
    uploader = FormUploader.newFormUploader(request);
    
    Hashtable tempHash = new Hashtable();
    
    Hashtable param = uploader.getFormParameters();
    
    String command = (String)param.get("command");
 
    String message = "false";
    String l_result = "";
 /*    String command = request.getParameter("command"); */

    if(command == null) {
        command = "";
    }

    command = WTURLEncoder.decode(command,"euc-kr");


    if("checkProject".equals(command)) {
        String projectNo = request.getParameter("projectNo");
        projectNo= WTURLEncoder.decode(projectNo,"euc-kr");
        //boolean flag = ProjectHelper.manager.checkProjectNo(projectNo);
        //if(flag) {
            //message = "true";
        //}
        message = "false";
    } else if("create".equals(command)) {

    }else if("createReview".equals(command)) {

        String receiptNumber = request.getParameter("receiptNumber");
        String projectName = request.getParameter("projectName");
        String planStartDate = request.getParameter("planStartDate");
        String planEndDate = "";
        String projectTypeName = request.getParameter("projectTypeName");
        String developenttype = request.getParameter("developenttype");
        String salesUser = request.getParameter("salesUser");
        //String devUser = request.getParameter("devUser");
        String devDeptOid = request.getParameter("devDeptOid");

        String proposalDate = request.getParameter("proposalDate");
        String estimateDate = request.getParameter("estimateDate");

        //제품 정보
        String pNum = request.getParameter("pNum");
        String pName = request.getParameter("pName");
        String usage = request.getParameter("usage");
        String areas = request.getParameter("areas");
        String price = request.getParameter("price");
        String cost = request.getParameter("cost");
        String estimated = request.getParameter("estimated");
        String rate = request.getParameter("rate");
        String investments = request.getParameter("investments");
        String templateOid = request.getParameter("templateOid");
        String pwlinkOid = request.getParameter("pwlinkOid");

        //기준 정보
        String rank = request.getParameter("rank");
        String producttype = request.getParameter("producttype");
        String assembledtype = request.getParameter("assembledtype");
        String lifecycle = request.getParameter("lifecycle");

        //재 작업 필요
        e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();

        String userMemberArr[] = request.getParameterValues("userMember");
        Vector userMemberVec = new Vector();
        if(userMemberArr != null && userMemberArr.length > 0) {
            for(int i = 0; i < userMemberArr.length; i++) {
                userMemberVec.add(WTURLEncoder.decode(userMemberArr[i]));
            }
        }

        String CUSTOMEREVENTOid[] = request.getParameterValues("CUSTOMEREVENTOid");
        Vector CUSTOMEREVENTOidVec = new Vector();
        if(CUSTOMEREVENTOid != null && CUSTOMEREVENTOid.length > 0) {
            for(int i = 0; i < CUSTOMEREVENTOid.length; i++) {
                CUSTOMEREVENTOidVec.add(WTURLEncoder.decode(CUSTOMEREVENTOid[i]));
            }
        }

        String SUBCONTRACTOROid[] = request.getParameterValues("SAPSUBCONTRACTOROid");
        Vector SUBCONTRACTOROidVec = new Vector();
        if(SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
            for(int i = 0; i < SUBCONTRACTOROid.length; i++) {
                SUBCONTRACTOROidVec.add(WTURLEncoder.decode(SUBCONTRACTOROid[i]));
            }
        }
        //projectNo = WTURLEncoder.decode(projectNo==null?"":projectNo);
        projectName = WTURLEncoder.decode(projectName==null?"":projectName);
        receiptNumber = WTURLEncoder.decode(receiptNumber==null?"":receiptNumber);
        planStartDate = WTURLEncoder.decode(planStartDate==null?"":planStartDate);
        projectTypeName = WTURLEncoder.decode(projectTypeName==null?"":projectTypeName);

        // 개발 검토 프로젝트 자동 번호 생성
        String projectNo = "";
        String tempDate = DateUtil.getDateString(new Date(), "all");
        String checkprojectNo = "";
        Kogger.debug("사업부 :"+ projectTypeName);
        if(projectTypeName.equals("자동차")){
            checkprojectNo = "C"+tempDate.substring(2, 4)+"A";
        }else{
            checkprojectNo = "E"+tempDate.substring(2, 4)+"A";
        }
        projectNo = ManageSequence.getSeqNo(checkprojectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);
        projectNo = checkprojectNo + projectNo;


        developenttype = WTURLEncoder.decode(developenttype==null?"":developenttype);
        salesUser = WTURLEncoder.decode(salesUser==null?"":salesUser);
        //devUser = WTURLEncoder.decode(devUser==null?"":devUser);
        devDeptOid = WTURLEncoder.decode(devDeptOid==null?"":devDeptOid);
        proposalDate = WTURLEncoder.decode(proposalDate==null?"":proposalDate);
        estimateDate = WTURLEncoder.decode(estimateDate==null?"":estimateDate);
        producttype = WTURLEncoder.decode(producttype==null?"":producttype);
        assembledtype = WTURLEncoder.decode(assembledtype==null?"":assembledtype);
        rank = WTURLEncoder.decode(rank==null?"":rank);

        pNum = WTURLEncoder.decode(pNum==null?"":pNum);
        pName = WTURLEncoder.decode(pName==null?"":pName);
        usage = WTURLEncoder.decode(usage==null?"":usage);
        areas = WTURLEncoder.decode(areas==null?"":areas);
        price = WTURLEncoder.decode(price==null?"":price);
        cost = WTURLEncoder.decode(cost==null?"":cost);
        estimated = WTURLEncoder.decode(estimated==null?"":estimated);
        rate = WTURLEncoder.decode(rate==null?"":rate);
        investments = WTURLEncoder.decode(investments==null?"":investments);

        templateOid = WTURLEncoder.decode(templateOid==null?"":templateOid);
        pwlinkOid = WTURLEncoder.decode(pwlinkOid==null?"":pwlinkOid);
        /*
        Kogger.debug("################################################# ProjectAjaxAction Review ##");
        Kogger.debug("###receiptNumber   "+ receiptNumber);
        Kogger.debug("###projectName   "+ projectName);
        Kogger.debug("###projectNo   "+ projectNo);
        Kogger.debug("###planStartDate   "+ planStartDate);
        Kogger.debug("###planEndDate   "+ planEndDate);
        Kogger.debug("###projectTypeName   "+ projectTypeName);
        Kogger.debug("###developenttype   "+ developenttype);
        Kogger.debug("###proposalDate   "+ proposalDate);
        Kogger.debug("###estimateDate   "+ estimateDate);
        Kogger.debug("###producttype   "+ producttype);
        Kogger.debug("###assembledtype   "+ assembledtype);
        Kogger.debug("###rank   "+ rank);

        Kogger.debug("###제품 정보   ");
        Kogger.debug("###pNum   "+ pNum);
        Kogger.debug("###pName   "+ pName);
        Kogger.debug("###usage   "+ usage);
        Kogger.debug("###areas   "+ areas);
        Kogger.debug("###price   "+ price);
        Kogger.debug("###cost   "+ cost);
        Kogger.debug("###estimated   "+ estimated);
        Kogger.debug("###rate   "+ rate);
        Kogger.debug("##investments "+ investments);
        Kogger.debug("################################################# ProjectAjaxAction Review END##");
        */
        Hashtable hash = new Hashtable();

        hash.put("projectType", "1");
        hash.put("projectName", projectName);
        hash.put("projectNo", projectNo);
        hash.put("receiptNumber", receiptNumber);
        hash.put("planStartDate", planStartDate);
        hash.put("projectTypeName", projectTypeName);
        hash.put("developenttype", developenttype);
        hash.put("salesUser", salesUser);
        //hash.put("devUser", devUser);
        hash.put("devDeptOid", devDeptOid);
        hash.put("proposalDate", proposalDate);
        hash.put("estimateDate", estimateDate);
        hash.put("producttype", producttype);
        hash.put("assembledtype", assembledtype);
        hash.put("rank", rank);
        hash.put("planEndDate", planEndDate);

        hash.put("pNum", pNum);
        hash.put("pName", pName);
        hash.put("usage", usage);
        hash.put("areas", areas);
        hash.put("price", price);
        hash.put("cost", cost);
        hash.put("estimated", estimated);
        hash.put("rate", rate);
        hash.put("investments", investments);

        hash.put("TemplateOID", templateOid);
        hash.put("pwlinkOid", pwlinkOid);

        hash.put("USERMEMBER", userMemberVec);
        hash.put("CUSTOMEREVENT", CUSTOMEREVENTOidVec);
        hash.put("SUBCONTRACTOR", SUBCONTRACTOROidVec);

        hash.put("lifecycle", lifecycle);



        Role role = null;
        String roleUser = null;

        TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Review Project");
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


        E3PSProject E3PSProject = null;

        try {
            E3PSProject = E3PSProjectHelper.service.createE3PSProject(hash);
            if(E3PSProject != null) {
                message = "true";
%>
            <data_info>
                <l_oid><![CDATA[<%=E3PSProject.getPersistInfo().getObjectIdentifier().getStringValue()%>]]></l_oid>
            </data_info>
<%
            }

        } catch (Exception e) {
            Kogger.error(e);
        }

        //E3PSProject Create End
    }
    else if("addMember".equals(command)) {
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

        if(task instanceof E3PSTask){

        }

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

        task = TaskUserHelper.setTaskRole(task, deptRole);

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
                prjRole.setDeptRole(deptRole);
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
        task.setDepartment(Department.newDepartment());
        if(task instanceof E3PSTask){
            if(depart != null){
//        task.setDeptRole(null);
            }
        }

        task = (TemplateTask)PersistenceHelper.manager.modify(task);


       if(task instanceof E3PSTask){
         if(depart != null){
            // WTUser master = PeopleHelper.manager.getChiefUser(depart);
            // if(master != null){
            //  TaskUserHelper.manager.deleteTaskUser(task,master);
            // }
         }
       }


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
        Object obj = CommonUtil.getObject(oid);
        HashMap  map = new HashMap();
        map.put("oid", oid);
        E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
        E3PSProjectData projectData = new E3PSProjectData(project);

        //Role별 담당자 처리
        TeamTemplate tempTeam = null;
        if(obj instanceof ReviewProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
        }else if(obj instanceof ProductProject){
            if(projectData.teamType.equals("자동차 사업부") || projectData.teamType.equals("KETS")) {
                tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
            }else if(projectData.teamType.equals("전자 사업부")) {
                tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
            }
        }else if(obj instanceof MoldProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
        }else if(obj instanceof WorkProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
        }

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
        
        HashMap mailMap = ProjectHelper.manager.getCFTChangeMailContent(project, map);
        mailMap.put("from", projectData.pjtPm);
        boolean flag = ProjectHelper.assignProjectMember(map);
        if(flag) {
            message = "true";
            if(projectData.pjtPm != null && !project.getState().getState().toString().equals("PMOINWORK")){
                List<WTUser> to = new ArrayList();
                QueryResult result = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);
                while(result.hasMoreElements()){
                    Object[] objArr = (Object[]) result.nextElement();
                    ProjectMemberLink link = (ProjectMemberLink) objArr[0];
                    WTUser member = link.getMember();
                    if(!to.contains(member)) to.add(link.getMember());
                }
                mailMap.put("to", to);
                // CFT 변경 공지 메일 발송
                if(StringUtil.checkString((String)mailMap.get("content"))){
                    KETMailHelper.service.sendFormMail("08015", "ProjectCFTChangeNoticeMail.html", mailMap);
                }
            }
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
        E3PSTask task = null;

        if(object  instanceof ProjectMemberLink){
            ProjectMemberLink projectMemberLink = (ProjectMemberLink)object;
            project = (E3PSProject)projectMemberLink.getProject();
        }else if(object instanceof ProjectViewDepartmentLink){
            ProjectViewDepartmentLink dlink = (ProjectViewDepartmentLink)object;
            project = (E3PSProject)dlink.getProject();
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
            if(pmuser != null){
                if(member.getName().equals(pmuser.getName())){
                    //isCandelete = false;
                    //l_result = "PM 구성원은 삭제 하실 수 없습니다.";
                }
            }
        }


        boolean flag = false;
        if(isCandelete){
            flag = ProjectHelper.deleteObjectLink(list);
            ProjectUserHelper.syncTaskMemberUser(project);
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
        String docOid = request.getParameter("docOid");
        String subjectType = request.getParameter("subjectType");
        String selectTrustOid = request.getParameter("selectTrustOid");
        String selectAssessOid = request.getParameter("selectAssessOid");
        
        String costReview1 = request.getParameter("costReview1");
        String costReview2 = request.getParameter("costReview2");
        String costFinal1  = request.getParameter("costFinal1");
        String costFinal2  = request.getParameter("costFinal2");
        String gateCheckType = request.getParameter("gateCheckType");
        
        
        oid = WTURLEncoder.decode(oid==null?"":oid);
        taskOid = WTURLEncoder.decode(taskOid==null?"":taskOid);
        docTypeOid = WTURLEncoder.decode(docTypeOid==null?"":docTypeOid);
        name =WTURLEncoder.decode(name==null?"":name);
        description = WTURLEncoder.decode(description==null?"":description);
        role = WTURLEncoder.decode(role==null?"":role);
        outputUser = WTURLEncoder.decode(outputUser==null?"":outputUser);
        isPrimary = WTURLEncoder.decode(isPrimary==null?"":isPrimary);
        docOid = WTURLEncoder.decode(docOid==null?"":docOid);
        subjectType = subjectType==null?"":subjectType;
        
        costReview1 = costReview1==null?"":costReview1;
        costReview2 = costReview2==null?"":costReview2;
        costFinal1 = costFinal1==null?"":costFinal1;
        costFinal2 = costFinal2==null?"":costFinal2;
        

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
        map.put("docOid",docOid);
        map.put("selectTrustOid",selectTrustOid);
        map.put("selectAssessOid",selectAssessOid);
        map.put("subjectType",subjectType);
        
        map.put("costReview1",costReview1);
        map.put("costReview2",costReview2);
        map.put("costFinal1",costFinal1);
        map.put("costFinal2",costFinal2);
        map.put("gateCheckType",gateCheckType);

        //if(userOidArr != null && userOidArr.length > 0) {
        //  map.put("userOid", userOidArr);
        //}

        //if(deptOidArr != null && deptOidArr.length > 0) {
        //  map.put("deptOid", deptOidArr);
        //}

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
        String oid = (String)param.get("oid");
        String taskOid = (String)param.get("taskOid");
        String docTypeOid = (String)param.get("docTypeOid");
        String name = (String)param.get("name");
        String description = (String)param.get("description");
        String role = (String)param.get("role");
        String objType = (String)param.get("objType");
        String outputtype = (String)param.get("outputtype");
        String isPrimary = (String)param.get("isPrimary");
        String category1 = (String)param.get("category1");
        String category2 = (String)param.get("category2");
        String category3 = (String)param.get("category3");
        String category4 = (String)param.get("category4");
        String docOid = (String)param.get("docOid");
        String docName = (String)param.get("docName");
        String[] isFileDel = request.getParameterValues("isFileDel");
       
        String gateCheckType = request.getParameter("gateCheckType_param");
        
        /* String oid = request.getParameter("oid");
        String taskOid = request.getParameter("taskOid");
        String docTypeOid = request.getParameter("docTypeOid");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String role = request.getParameter("role");
        String objType = request.getParameter("objType");
        String outputtype = request.getParameter("outputtype");
        String isPrimary = request.getParameter("isPrimary"); */
        //Kogger.debug("isPrimary ----------------------- " + isPrimary);
        
        
        /* oid = WTURLEncoder.decode(oid==null?"":oid);
        taskOid = WTURLEncoder.decode(taskOid==null?"":taskOid);
        docTypeOid = WTURLEncoder.decode(docTypeOid==null?"":docTypeOid);
        name =WTURLEncoder.decode(name==null?"":name);
        description = WTURLEncoder.decode(description==null?"":description);
        role = WTURLEncoder.decode(role==null?"":role); */
        
        
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
        map.put("docOid", docOid);
        map.put("docName", docName);
        map.put("category4", category4);
        map.put("gateCheckType",gateCheckType);
        
        ProjectOutput output = null;
        try {
            output = ProjectOutputHelper.saveDefProjectOutputTemplate(map);
            
             Vector files = uploader.getFiles();
            if (files != null) {
                boolean isPrimary_ = false;
                for (int i = 0; i < files.size(); i++) {
                WBFile file = (WBFile) files.elementAt(i);

                isPrimary_ = false;
                if ("iFile0".equalsIgnoreCase(file.getFieldName())) {
                    isPrimary_ = true;
                }
                E3PSContentHelper.service.attach(output, file, "", isPrimary_);
                }
            } 
            
            if(isFileDel != null){
        	  ReferenceFactory rf = new ReferenceFactory();
        	  ContentHolder holder = (ContentHolder) rf.getReference(oid).getObject();
              for(int i=0; i < isFileDel.length; i++){
	        	  ContentItem ctf = (ContentItem) CommonUtil.getObject(isFileDel[i]);
	              E3PSContentHelper.service.delete(holder, ctf);
              }
        	  
            }
        }
        catch(Exception e) {
            Kogger.error(e);
            output = null;
        }
        if(output != null) {
            message = "true";
        }
    }else if("updateGateCheckType".equals(command)) {
	   String oid = request.getParameter("oid");
	   String gateCheckType = request.getParameter("gateCheckType");
	   HashMap map = new HashMap();
	   map.put("oid", oid);
	   map.put("gateCheckType", gateCheckType);
	   
	   ProjectOutput output = null;
	   
	   try {
	       output = ProjectOutputHelper.saveDefProjectOutputGateCheckType(map);
	   }catch(Exception e) {
	       Kogger.error(e);
	       output = null;
	   }
	   
	   if(output != null) {
	       message = "true";
	   }
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    //리스트 검색 .............................................................................
    if( (("addMember".equals(command) || "addRefMember".equals(command) || "memberDelete".equals(command)
            || "refMemberDelete".equals(command)) && "true".equals(message)) || "searchMember".equals(command)) {



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
        //Kogger.debug("ProjectAjaxAction memberType==>"+memberType);
        if(memberType.equals("MEMBER")){

            members = ProjectUserHelper.manager.getOnlyAppendMember(project);
        }else{
            members = ProjectUserHelper.manager.getQueryForTeamUsers(project, memberType);
        }
        Object[] obj = null;
        while(members.hasMoreElements()) {
            obj = (Object[])members.nextElement();
            link = (ProjectMemberLink)obj[0];
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
        //Kogger.debug("## ProjectAjaxAction addRoleMember getQueryForTeamUsers result.size()=>"+ result.size());
        while(result.hasMoreElements()) {
            roleLink = (ProjectMemberLink)result.nextElement();
            legacyMap.put(roleLink.getPjtRole(), roleLink);
        }

        Vector vecTeamStd = null;
        Object obj = CommonUtil.getObject(oid);
        TeamTemplate tempTeam = null;
        if(obj instanceof ReviewProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
        }else if(obj instanceof ProductProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
        }else if(obj instanceof MoldProject){
            tempTeam =  TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
        }


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
    }
%>
            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
                <l_result><![CDATA[<%=l_result%>]]></l_result>
            </message>
        </contents>
    </results>
</stdinfo>
