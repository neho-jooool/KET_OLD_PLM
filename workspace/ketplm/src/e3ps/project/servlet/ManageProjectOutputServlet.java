package e3ps.project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import e3ps.common.content.fileuploader.FormUploader;
//import e3ps.common.content.uploader.WBFile;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
//import e3ps.doc.DocCodeDocMasterLink;
//import e3ps.doc.DocCodeType;
//import e3ps.doc.E3PSDocument;
//import e3ps.doc.E3PSDocumentHelper;
//import e3ps.doc.JELDocument;
//import e3ps.doc.beans.DocCodeTypeHelper;
//import e3ps.doc.beans.DocumentUtil;

public class ManageProjectOutputServlet extends CommonServlet {

	protected void doService(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String cmd = req.getParameter("cmd");
		String contentType = req.getContentType();
		if ( contentType != null && contentType.indexOf("multipart/form-data") >= 0 && cmd.equalsIgnoreCase("create") ) {
//			this.alertNclose(res, create(req, res));
//			create(req, res);
		} else if(cmd.equalsIgnoreCase("createLink")){
			this.alertNreloadNclose(res, createLink(req,res));
		}
	}

	private String createLink(HttpServletRequest req, HttpServletResponse res) {
		CommonUtil.printlnParamValues(req);
		
		String linkDocOID = req.getParameter("docOid");
		String outputOID = req.getParameter("oid");
//		E3PSDocument e3psDoc = null;
		
//		try {
//			//E3PSDocument Object
//			e3psDoc = (E3PSDocument) CommonUtil.getObject(linkDocOID);
//			e3psDoc = (E3PSDocument) PersistenceHelper.manager.refresh(e3psDoc);
//			
//			//OutputDocumentLink Connect
//	        ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOID);
//	        ProjectOutputHelper.manager.registryProjectOutput(output, e3psDoc);
//	        
//	        //JELDocProjectLink Connect(doc Package)
//            DocumentUtil docUtil = new DocumentUtil();
//            Hashtable docParam = new Hashtable();
//            docParam.put("projectoid", CommonUtil.getOIDString((JELProject)output.getProject()));
//            docUtil.getCreateProjectLink(docParam, (JELDocument)e3psDoc);
//            
//            //ADD(2008.05.04)
//            //기능: 최초에 산출물일 경우 JELProject & JELTask 에 실제 시작일자 설정
////            ProjectOutputData outputData = new ProjectOutputData(output);
////            //JELProject ExecStartDate Setting
////            JELProject jelPjt = (JELProject)outputData.project;
////            JELProjectData pjtData = new JELProjectData(jelPjt);
////            if(pjtData.pjtExecStartDate == null) { 
////            	ExtendScheduleData pjtSchedule = pjtData.schedule;
////            	Calendar cal = Calendar.getInstance();
////            	pjtSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
////            	PersistenceHelper.manager.modify(pjtSchedule);
////            }
////            //LEVEL2 JELTask ExecStartDate Setting
////            JELTask jelTask = (JELTask) outputData.task;
////            JELTaskData taskData = new JELTaskData(jelTask);
////            if(taskData.taskExecStartDate == null) {
////            	ExtendScheduleData taskSchedule = taskData.schedule;
////            	Calendar cal = Calendar.getInstance();
////            	taskSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
////            	PersistenceHelper.manager.modify(taskSchedule);
////            }
////            
////            //LEVEL1 JELTask ExecStartDate Setting
////            JELTask parentTask = (JELTask) jelTask.getParent();
////            JELTaskData parentTaskData = new JELTaskData(parentTask);
////            if(parentTaskData.taskExecStartDate == null) {
////            	ExtendScheduleData taskSchedule = parentTaskData.schedule;
////            	Calendar cal = Calendar.getInstance();
////            	taskSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
////            	PersistenceHelper.manager.modify(taskSchedule);
////            }
//		} catch (ObjectNoLongerExistsException e) {
//			Kogger.error(getClass(), e);
//		} catch (WTException e) {
//			Kogger.error(getClass(), e);
//		} catch (Exception e) {
//			Kogger.error(getClass(), e);
//		}
		
		return "";//e3psDoc.getNumber() + "["+e3psDoc.getName()+"] 를 LINK 했습니다.";
	}

//	private String create(HttpServletRequest req, HttpServletResponse res) {
//		try {
//			CommonUtil.printlnParamValues(req);
			
			//DocumentServlet 내용(2008.03.21) - Start
//            FormUploader uploader = FormUploader.newFormUploader(req);
//            Hashtable param = uploader.getFormParameters();
            
//            Enumeration en = param.keys();
//            while(en.hasMoreElements()){
//                Object obj = en.nextElement();
//            }
            
//            E3PSDocument e3psDoc = null;
//            DocumentUtil util = new DocumentUtil();
            
            //Title Setting(부서)
//            WTUser user=(WTUser)SessionHelper.manager.getPrincipal();
//            PeopleData peoData = new PeopleData(user);
//            String title = peoData.departmentName;
//            param.put("title", title);
            
            //프로젝트 LINK(StandardJELDocumentService 에서 있을 때 필요함)
            //문서와 프로젝트 (DocJELProjectLink.class )
            
            //Document Number Setting
//            param.put("number", util.setDocNumber((String)param.get("name0")));
//            
//            if( util.checkDocNumber((String)param.get("number")) ) {
//                return "번호가 중복되어서 생성되지 않았습니다.";
//            } else {
//                Vector files = uploader.getFiles();
//                if(files != null){
//                    for(int i=0; i < files.size(); i++){
//                        WBFile file = (WBFile) files.elementAt(i);
//                        Kogger.debug(getClass(), i+"-"+file.getName()+"-"+file.getFieldName());
//                    }
//                }
//                e3psDoc = E3PSDocumentHelper.service.create(param, files);
//                if(e3psDoc != null) {
//                    DocCodeType docCodeType=null;
//                    String name0 = (String)param.get("name0");
//                        for(int i=9;i>-1;i--) {
//                            String type = (String)param.get("docClassify"+i);
//                            if(type!=null && type.length()>0) {
//                                if(i == 0){
//                                    docCodeType = (DocCodeType) DocCodeTypeHelper.getDocTypeForECO(type);
//                                }else{
//                                    docCodeType = (DocCodeType) DocCodeTypeHelper.getDocCodeTypeToPath(type);
//                                }
//                                break;
//                            }
//                        }
//                        
//                    //Link To DocCodeType Object
//                    DocCodeDocMasterLink docCodeMasterLink = DocCodeDocMasterLink.newDocCodeDocMasterLink(docCodeType, (WTDocumentMaster) e3psDoc.getMaster());
//                    docCodeMasterLink = (DocCodeDocMasterLink) PersistenceHelper.manager.save(docCodeMasterLink);
//                    
//                    //OutputDocumentLink Connect
//                    String projectOutputOID = (String) param.get("projectOutputOID");
//                    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(projectOutputOID);
//                    ProjectOutputHelper.manager.registryProjectOutput(output, e3psDoc);
//                    
//                    //JELDocProjectLink Connect(doc Package)
//                    DocumentUtil docUtil = new DocumentUtil();
//                    Hashtable docParam = new Hashtable();
//                    docParam.put("projectoid", CommonUtil.getOIDString((JELProject)output.getProject()));
//                    docUtil.getCreateProjectLink(docParam, (JELDocument)e3psDoc);
                    
                    //ADD(2008.05.04)
                    //기능: 최초에 산출물일 경우 JELProject & JELTask 에 실제 시작일자 설정
//                    ProjectOutputData outputData = new ProjectOutputData(output);
//                    //JELProject ExecStartDate Setting
//                    JELProject jelPjt = (JELProject)outputData.project;
//                    JELProjectData pjtData = new JELProjectData(jelPjt);
//                    if(pjtData.pjtExecStartDate == null) { 
//                    	ExtendScheduleData pjtSchedule = pjtData.schedule;
//                    	Calendar cal = Calendar.getInstance();
//                    	pjtSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
//                    	PersistenceHelper.manager.modify(pjtSchedule);
//                    }
//                    //LEVEL2 JELTask ExecStartDate Setting
//                    JELTask jelTask = (JELTask) outputData.task;
//                    JELTaskData taskData = new JELTaskData(jelTask);
//                    if(taskData.taskExecStartDate == null) {
//                    	ExtendScheduleData taskSchedule = taskData.schedule;
//                    	Calendar cal = Calendar.getInstance();
//                    	taskSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
//                    	PersistenceHelper.manager.modify(taskSchedule);
//                    }
//                    
//                    //LEVEL1 JELTask ExecStartDate Setting
//                    JELTask parentTask = (JELTask) jelTask.getParent();
//                    JELTaskData parentTaskData = new JELTaskData(parentTask);
//                    if(parentTaskData.taskExecStartDate == null) {
//                    	ExtendScheduleData taskSchedule = parentTaskData.schedule;
//                    	Calendar cal = Calendar.getInstance();
//                    	taskSchedule.setExecStartDate(new java.sql.Timestamp(cal.getTime().getTime()));
//                    	PersistenceHelper.manager.modify(taskSchedule);
//                    }
                    
//                    String msg = e3psDoc.getNumber() + " 인 문서가 생성되었습니다.";
//                    
//                    //바로결재(true) or 개인 캐비넷(false)
//                    String isDirect = req.getParameter("isdirectprocess");
//                    if ("true".equals(isDirect)) // 바로 결재
//                    	alertNgoNcloseParent(res, msg, "/plm/jsp/common/loading.jsp?" +
//                                "url=/plm/jsp/groupware/workprocess/CreateProcess.jsp&key=oid&value="+CommonUtil.getOIDString(e3psDoc));
//                    else
//                    	alertNgoNcloseParent(res, msg, "/plm/servlet/e3ps.groupware.workprocess.servlet.SearchMyFolderServlet");
//
////                    return e3psDoc.getNumber() + " 인 문서가 생성되었습니다.";
//                } else {
//                    return "생성되지 않았습니다";
//                }
//            }
//        } catch (Exception e) {
//            Kogger.error(getClass(), e);
//        }
//        return "";
//	}
}
