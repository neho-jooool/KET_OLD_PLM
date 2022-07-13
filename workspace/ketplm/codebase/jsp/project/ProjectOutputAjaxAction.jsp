<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="ext.ket.project.task.service.ProjectTaskCompHelper"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.*, wt.org.*,wt.query.*,wt.team.*,wt.project.Role"%>
<%@page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@page import="e3ps.project.*,e3ps.project.beans.*,wt.content.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@include file="/jsp/common/context.jsp"%>
<stdinfo>
    <results>
        <contents>
<%
    String message = "false";
    String errorMessage = "";

    String command = request.getParameter("command");
    if(command == null) {
        command = "";
    }
    command = java.net.URLDecoder.decode(command,"euc-kr");

    if("inputOutputProgress".equals(command)) {

        message = "true";
        String outputOid = request.getParameter("outputOid");
        String outputCompletion = request.getParameter("outputCompletion");
        outputOid = java.net.URLDecoder.decode(outputOid==null?"":outputOid,"euc-kr");
        outputCompletion = java.net.URLDecoder.decode(outputCompletion==null?"":outputCompletion,"euc-kr");

        if(outputCompletion.length() == 0) {
            outputCompletion = "0";
        }

        try {
            ReferenceFactory rf = new ReferenceFactory();
            ProjectOutput output = (ProjectOutput)rf.getReference(outputOid).getObject();
            output.setCompletion(Integer.parseInt(outputCompletion));
            ProjectTaskHelper.updateCompletion(output);
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
            errorMessage = e.getCause().getLocalizedMessage();

        }

        command = "searchOutput";
    }

    else if("deleteOutput".equals(command)) {
        String outputOid = request.getParameter("outputOid");
        outputOid = java.net.URLDecoder.decode(outputOid==null?"":outputOid,"euc-kr");
        try {
            
            
            ReferenceFactory rf = new ReferenceFactory();
            ProjectOutput output = (ProjectOutput)rf.getReference(outputOid).getObject();
            E3PSTask task = (E3PSTask) output.getTask();
            ProjectOutputHelper.manager.deleteProjectOutput(output);
            ProjectTaskCompHelper.service.deleteAssessByTask(task);
            ProjectTaskHelper.updateCompletionFromOutput(task);
        }
        catch(Exception e) {
            e.printStackTrace();
            Kogger.error(e);
            message = "false";
        }

        command = "searchOutput";
    }
    else if("deleteOutputTemplate".equals(command)) {
        String outputOid = request.getParameter("outputOid");
        outputOid = java.net.URLDecoder.decode(outputOid==null?"":outputOid,"euc-kr");
        try {
            ReferenceFactory rf = new ReferenceFactory();
            ProjectOutput output = (ProjectOutput)rf.getReference(outputOid).getObject();
            ProjectOutputHelper.manager.deleteProjectOutputTemplate(output);
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }

        command = "searchOutputTemplate";
    }
    else if("inputOutputLink".equals(command)) {
        String oid = request.getParameter("oid");
        String docOid = request.getParameter("docOid");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        docOid = java.net.URLDecoder.decode(docOid==null?"":docOid,"euc-kr");

        Hashtable hash = new Hashtable();
        hash.put("oid", oid);
        hash.put("docOid", docOid);

        message = "true";
        try {
            ProjectOutput output = ProjectOutputHelper.saveProjectOutputDocLink(hash);
            if(output == null) {
                message = "false";
            }
        }
        catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }
    }
    else if("inputGwLink".equals(command)) {

        String oid = request.getParameter("oid");
        String docOid = request.getParameter("docOid");
        String targetDbPath = request.getParameter("targetDbPath");
        String targetUnId = request.getParameter("targetUnId");
        String targetSubject = request.getParameter("targetSubject");
        String EmpNo = request.getParameter("EmpNo");

        oid = java.net.URLDecoder.decode(oid==null?"":oid, "euc-kr");
        docOid = java.net.URLDecoder.decode(docOid==null?"":docOid, "euc-kr");
        targetDbPath = java.net.URLDecoder.decode(targetDbPath==null?"":targetDbPath, "euc-kr");
        targetUnId = java.net.URLDecoder.decode(targetUnId==null?"":targetUnId, "euc-kr");
        targetSubject = java.net.URLDecoder.decode(targetSubject==null?"":targetSubject, "euc-kr");
        EmpNo = java.net.URLDecoder.decode(EmpNo==null?"":EmpNo, "euc-kr");

        Hashtable hash = new Hashtable();

        hash.put("oid", oid);
        hash.put("docTypeOid", docOid);
        hash.put("targetDbPath", targetDbPath);
        hash.put("targetUnId", targetUnId);
        hash.put("targetSubject", targetSubject);
        hash.put("EmpNo", EmpNo);

        message = "true";

        try {
            boolean reMsg = ProjectOutputHelper.saveGwDocLink(hash);
            if(!reMsg) {
                message = "false";
            }
        } catch(Exception e) {
            Kogger.error(e);
            message = "false";
        }
    }
    else if("addViewMember".equals(command)) {
        String oid = request.getParameter("oid");
        String viewMemberArr[] = request.getParameterValues("viewMember");

        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

        HashMap map = new HashMap();
        map.put("oid", oid);

        if(viewMemberArr != null && viewMemberArr.length > 0) {
            map.put("viewMember", viewMemberArr);
        }

        boolean flag = ProjectOutputHelper.setOutputViewMember(map);
        if(flag) {
            message = "true";
        }
    }
    else if("deleteViewMember".equals(command)) {
        String oid = request.getParameter("oid");
        String viewMemberOid = request.getParameter("viewMemberOid");

        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
        viewMemberOid = java.net.URLDecoder.decode(viewMemberOid==null?"":viewMemberOid,"euc-kr");

        ArrayList list = new ArrayList();
        list.add(viewMemberOid);

        HashMap map = new HashMap();
        map.put("viewMember", list);
        boolean flag = ProjectOutputHelper.deleteOutputViewMember(map);
        if(flag) {
            message = "true";
        }
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    //리스트 검색 .............................................................................
    if("searchViewMember".equals(command)) {
        message = "true";
%>
            <data_info>
<%
        String oid = request.getParameter("oid");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");

        ReferenceFactory rf = new ReferenceFactory();
        ProjectOutput output = (ProjectOutput)rf.getReference(oid).getObject();
        QueryResult viewQr = ProjectOutputHelper.getOutputViewMember(output, true);

        OutputViewMemberLink viewMemberLink = null;
        ProjectMemberLink memberLink = null;

        WTUser wtuser = null;
        PeopleData userData = null;

        OutputViewDepartMentLink viewDeptLink = null;
        ProjectViewDepartmentLink deptLink = null;
        Department department = null;

        String viewOid = null;
        String viewLinkOid = "";
        String name = "";
        String duty = "";
        String departmentName = "";
        String email = "";
        String peopleOID = "";

        boolean isView = false;
        boolean isDept = false;

        Object view = null;
        Object viewObj[] = null;
        while(viewQr.hasMoreElements()) {
            viewObj = (Object[])viewQr.nextElement();
            viewOid = (String)viewObj[0];

            viewLinkOid = "";
            name = "";
            duty = "";
            departmentName = "";
            email = "";
            peopleOID = "";

            isView = false;
            isDept = false;

            view = rf.getReference(viewOid).getObject();
            if(view instanceof OutputViewMemberLink) {
                viewMemberLink = (OutputViewMemberLink)view;
                memberLink = viewMemberLink.getViewMember();
                wtuser = (WTUser)memberLink.getMember();
                userData = new PeopleData(wtuser);

                viewLinkOid = viewMemberLink.getPersistInfo().getObjectIdentifier().getStringValue();
                name = userData.name;
                duty = userData.duty;
                departmentName = userData.departmentName;
                email = userData.email;
                peopleOID = (userData.people).getPersistInfo().getObjectIdentifier().getStringValue();

                if(memberLink.getPjtMemberType() == ProjectUserHelper.ONLY_VIEW_MEMBER) {
                    isView = true;
                }
            }
            else if(view instanceof OutputViewDepartMentLink) {
                viewDeptLink = (OutputViewDepartMentLink)view;
                deptLink = viewDeptLink.getViewDepartment();
                department = deptLink.getDepartment();

                viewLinkOid = viewDeptLink.getPersistInfo().getObjectIdentifier().getStringValue();
                name = department.getName();
                isView = true;
                isDept = true;
            }
%>
                <l_name><![CDATA[<%=name%>]]></l_name>
                <l_duty><![CDATA[<%=duty%>]]></l_duty>
                <l_departmentName><![CDATA[<%=departmentName%>]]></l_departmentName>
                <l_email><![CDATA[<%=email%>]]></l_email>
                <l_isView><![CDATA[<%=isView%>]]></l_isView>
                <l_isDept><![CDATA[<%=isDept%>]]></l_isDept>
                <l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
                <l_oid><![CDATA[<%=viewOid%>]]></l_oid>
                <l_linkOid><![CDATA[<%=viewLinkOid%>]]></l_linkOid>
<%
        }
%>
            </data_info>
<%
    }
    else if("searchOutput".equals(command)) {
        message = "true";

        String oid = request.getParameter("oid");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
%>
            <data_info>
<%
        TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);


        TaskViewButtonAuth buttonAuth = null;
        if(task instanceof E3PSTask){
            buttonAuth = new 	TaskViewButtonAuth((E3PSTask)task);
        }

        ProjectOutput output = null;
        ProjectOutputData outputData = null;
        PeopleData peopleData = null;
        WTUser outputUser = null;
        String deptName = "";
        String outputChargerName = "";
        String version = "";
        String state = "";
        String modifyDate = "";
        String peopleOID = "";
        int completion = 0;


        boolean isAdmin = CommonUtil.isAdmin();
        boolean isPM = ProjectUserHelper.manager.isPM(task.getProject());

        Object[] opObj = null;

        try {

            QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);

            while ( outputQr.hasMoreElements() ) {

                int docCreateOrLinkType = 1;
                int outputCompletionAuthType = 1;
                String isDeleteOutput = "true";

                boolean isCanRegistry = false;
                opObj = (Object[])outputQr.nextElement();
                output = (ProjectOutput)opObj[0];
                outputUser = output.getOwner() == null? null:(WTUser)output.getOwner().getPrincipal();
                outputData = new ProjectOutputData (output);

                outputChargerName = "";
                deptName = "";
                version = "";
                state = "";
                modifyDate = "";
                peopleOID = "";
                peopleData = null;
                if(outputUser != null) {
                    outputChargerName = outputUser.getFullName();
                    peopleData = new PeopleData(outputUser);
                    deptName = peopleData.departmentName;
                    peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
                }

                if(outputData.isRegistry) {
                    
                    if (output.getObjType().equals("ECO")) {
                	   e3ps.ecm.entity.KETProdChangeOrder prodChangeOrderObj = null;
                	   e3ps.ecm.entity.KETMoldChangeOrder moldChangeOrderObj = null;

                        QueryResult qr = PersistenceHelper.manager.navigate(output, "change", e3ps.project.KETProdChangeOrderOutputLink.class);

                        while (qr.hasMoreElements()) {
                            prodChangeOrderObj = (e3ps.ecm.entity.KETProdChangeOrder) qr.nextElement();
                        }

                        qr = PersistenceHelper.manager.navigate(output, "change", e3ps.project.KETMoldChangeOrderOutputLink.class);

                        while (qr.hasMoreElements()) {
                            moldChangeOrderObj = (e3ps.ecm.entity.KETMoldChangeOrder) qr.nextElement();
                        }

                        if(prodChangeOrderObj!=null) {
                            version = CommonUtil.getOIDString(outputData.changeOrder);
                            state = outputData.changeOrder.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                            //lastVersionOid = CommonUtil.getOIDString(prodChangeOrderObj);
                            modifyDate = DateUtil.getDateString(outputData.changeOrder.getPersistInfo().getModifyStamp(), "D");
                        }else if(moldChangeOrderObj!=null) {
                            version = CommonUtil.getOIDString(outputData.changeOrder);
                            state = outputData.changeOrder.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
							//lastVersionOid = CommonUtil.getOIDString(moldChangeOrderObj);
                            modifyDate = DateUtil.getDateString(outputData.changeOrder.getPersistInfo().getModifyStamp(), "D");
                        }

                    } else if (output.getObjType().equals("TRY") && output.getCompletion() == 100) {
	                	ext.ket.project.trycondition.entity.KETTryMold moldTryConditionObj = null;
	                	ext.ket.project.trycondition.entity.KETTryPress pressTryConditionObj = null;

                        QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", e3ps.project.KETTryMoldOutputLink.class);

                        while (qr.hasMoreElements()) {
                            moldTryConditionObj = (ext.ket.project.trycondition.entity.KETTryMold) qr.nextElement();
                        }

                        qr = PersistenceHelper.manager.navigate(output, "tryPress", e3ps.project.KETTryPressOutputLink.class);

                        while (qr.hasMoreElements()) {
                            pressTryConditionObj = (ext.ket.project.trycondition.entity.KETTryPress) qr.nextElement();
                        }

                        if(moldTryConditionObj!=null) {
                            version = CommonUtil.getOIDString(outputData.tryCondition);
                            state = outputData.tryCondition.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                            //lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
                            modifyDate = DateUtil.getDateString(outputData.tryCondition.getPersistInfo().getModifyStamp(), "D");
                        }else if(pressTryConditionObj!=null) {
                            version = CommonUtil.getOIDString(outputData.tryCondition);
                            state = outputData.tryCondition.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                            //lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
                            modifyDate = DateUtil.getDateString(outputData.tryCondition.getPersistInfo().getModifyStamp(), "D");
                        }
                    } else if (output.getObjType().equals("QLP")) {
                	    ext.ket.dqm.entity.KETDqmRaise dqmRaiseObj = null;
                	    ext.ket.dqm.entity.KETDqmHeader dqmHeaderObj = null;
                	    ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service.getOutputQLP(output);
                        
                        if(dqmDto!=null) {
                            dqmRaiseObj = ("".equals(dqmDto.getRaiseOid()))?null:(ext.ket.dqm.entity.KETDqmRaise)CommonUtil.getObject(dqmDto.getRaiseOid());
                            dqmHeaderObj = ("".equals(dqmDto.getOid()))?null:(ext.ket.dqm.entity.KETDqmHeader)CommonUtil.getObject(dqmDto.getOid());
                            
                            version = CommonUtil.getOIDString(dqmRaiseObj);
                            state = dqmHeaderObj.getDqmStateName();
                            //lastVersionOid = CommonUtil.getOIDString(moldTryConditionObj);
                            modifyDate = DateUtil.getDateString(dqmHeaderObj.getPersistInfo().getModifyStamp(), "D");

                        }
                        
                    }else {
	                    version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
	                    state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
	                    modifyDate = DateUtil.getDateString(outputData.currentDocument.getPersistInfo().getModifyStamp(), "D");
                    }
                    
                }


                if(task instanceof E3PSTask) {
                    docCreateOrLinkType = buttonAuth.isOutputDocCreateOrLink(output);
                    outputCompletionAuthType = buttonAuth.isOutputCompletion(output);
                    isDeleteOutput = String.valueOf(buttonAuth.isDeleteOutput(output));

                }
                else {
                        isCanRegistry = true;
                }

                completion = output.getCompletion();
%>
                <l_name><![CDATA[<%=outputData.name%>]]></l_name>
                <l_location><![CDATA[<%=outputData.locationStr%>]]></l_location>
                <l_departmentName><![CDATA[<%=deptName%>]]></l_departmentName>
                <l_userName><![CDATA[<%=outputChargerName%>]]></l_userName>
                <l_version><![CDATA[<%=version%>]]></l_version>
                <l_state><![CDATA[<%=state%>]]></l_state>
                <l_modifyDate><![CDATA[<%=modifyDate%>]]></l_modifyDate>
                <l_isRegistry><![CDATA[<%=outputData.isRegistry%>]]></l_isRegistry>
                <l_isCanRegistry><![CDATA[<%=isCanRegistry%>]]></l_isCanRegistry>

                <docCreateOrLinkType><![CDATA[<%=docCreateOrLinkType%>]]></docCreateOrLinkType>
                <outputCompletionAuthType><![CDATA[<%=outputCompletionAuthType%>]]></outputCompletionAuthType>
                <isDeleteOutput><![CDATA[<%=isDeleteOutput%>]]></isDeleteOutput>

                <l_oid><![CDATA[<%=outputData.oid%>]]></l_oid>
                <l_peopleOid><![CDATA[<%=peopleOID%>]]></l_peopleOid>
                <l_completion><![CDATA[<%=completion%>]]></l_completion>
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

    else if("searchOutputTemplate".equals(command)) {
        message = "true";

        String oid = request.getParameter("oid");
        oid = java.net.URLDecoder.decode(oid==null?"":oid,"euc-kr");
%>
            <data_info>
<%
        TemplateTask task = (TemplateTask)CommonUtil.getObject(oid);

        ProjectOutput output = null;
        ProjectOutputData outputData = null;
        Object[] opObj = null;

        
        
        try {
            QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
            while ( outputQr.hasMoreElements() ) {
                opObj = (Object[])outputQr.nextElement();
                output = (ProjectOutput)opObj[0];
                outputData = new ProjectOutputData (output);

                ContentHolder holder = (ContentHolder) output;
                Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
                int size = secondaryFiles.size();
%>
                <l_oid><![CDATA[<%=outputData.oid%>]]></l_oid>
                <l_name><![CDATA[<%=outputData.name%>]]></l_name>
                <l_objType><![CDATA[<%=outputData.objType%>]]></l_objType>
                <l_isPrimary><![CDATA[<%=outputData.isPrimary%>]]></l_isPrimary>
                <l_location><![CDATA[<%=outputData.locationStr%>]]></l_location>
                <!-- l_role><![CDATA[<%//=outputData.role_ko%>]]></l_role-->
                <l_docName><![CDATA[<%=output.getOutputDocName()%>]]></l_docName>
                <l_docOid><![CDATA[<%=StringUtil.checkNull(output.getOutputDocOid())%>]]></l_docOid>
                <l_subjectType><![CDATA[<%=output.getSubjectType()%>]]></l_subjectType>
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

%>

            <message>
                <l_message><![CDATA[<%=message%>]]></l_message>
                <l_errorMessage><![CDATA[<%=errorMessage%>]]></l_errorMessage>
            </message>

        </contents>
    </results>
</stdinfo>
