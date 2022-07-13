package ext.ket.project.trycondition.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.QueryResult;
import wt.project.Role;
import wt.team.TeamException;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.project.trycondition.entity.TryConditionDTO;
import ext.ket.project.trycondition.entity.TryMoldDTO;
import ext.ket.project.trycondition.entity.TryPressDTO;
import ext.ket.project.trycondition.service.TryConditionHelper;
import ext.ket.project.trycondition.util.TryExcelExportUtil;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;

@Controller
@RequestMapping("/project/trycondition/*")
public class TryConditionController {

    /**
     * 금형 Try Press 복사 Popup
     * 
     * @메소드명 : copyTryConditionPopup
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/copyTryConditionPopup")
    public void copyTryConditionPopup(TryConditionDTO paramObject, Model model) throws Exception {
	model.addAttribute("moldProject", (MoldProject) CommonUtil.getObject(paramObject.getProjectOid()));
	model.addAttribute("tryConditionList",
	        CommonUtil.toJsonString(TryConditionHelper.service.getTryConditionByProject(paramObject.getProjectOid())));
    }

    /**
     * <p>
     * Try 조건표 등록
     * </p>
     * ProjectOutput 객체를 통해 Press/Mold인지 구분한다.
     * 
     * @param paramObject
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : createTryConditionForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("static-access")
    @RequestMapping("/createTryConditionForm")
    public String createTryConditionForm(TryConditionDTO paramObject, Model model) throws Exception {
	ProjectOutput projectOutput = (ProjectOutput) CommonUtil.getObject(paramObject.getProjectOutputOid());
	MoldProject moldProject = (MoldProject) projectOutput.getTask().getProject();
	E3PSTask task = (E3PSTask) projectOutput.getTask();
	E3PSTask parentTask = (E3PSTask) CommonUtil.getObject(task.getParent().getPersistInfo().getObjectIdentifier().getStringValue());

	// Try No를 결정
	// 현재 Task에 T0 문자열이 있으면 T0
	// 상위 Task에 디버깅 문자열이 있으면 앞의 차수 정보로 Try No를 결정한다.
	String tryNo = "";
	if (task.getTaskName().indexOf("T0") > 0) {
	    tryNo = "T0";
	} else if (parentTask.getTaskName().indexOf("디버깅") > 0) {
	    tryNo = "T" + parentTask.getTaskName().substring(0, 1);
	}
	paramObject.setTryNo(tryNo);
	model.addAttribute("moldProject", moldProject);

	model.addAttribute("tryNo", tryNo);
	model.addAttribute("maxSubVer", TryConditionHelper.service.getMaxSubVer(paramObject));
	model.addAttribute("parentTask", parentTask);
	model.addAttribute("role", getRoleMember(CommonUtil.getFullOIDString(moldProject)));
	if ("Press".equals(moldProject.getMoldInfo().getItemType())) {
	    KETTryPress tryPress = (KETTryPress) CommonUtil.getObject(paramObject.getOid());
	    List<Map<String, Object>> partList = null;
	    // 금형 원재료 정보 추가
	    if (tryPress == null) {
		if (moldProject.getMoldInfo().getMaterial() != null) {
		    tryPress = tryPress.newKETTryPress();
		    tryPress.setMaterial(moldProject.getMoldInfo().getMaterial());
		}
	    } else {
		try {
		    partList = TryConditionHelper.service.getTryPartList(CommonUtil.getOIDString(tryPress));
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }

	    model.addAttribute("tryPress", tryPress);

	    model.addAttribute("partList", partList);

	    return "project/trycondition/createTryPressForm";
	} else {
	    KETTryMold tryMold = (KETTryMold) CommonUtil.getObject(paramObject.getOid());
	    if (tryMold == null && moldProject.getMoldInfo() != null && moldProject.getMoldInfo().getMaterial() != null) {
		tryMold = tryMold.newKETTryMold();
		if (moldProject.getMoldInfo().getMaterial() != null) {
		    tryMold.setMaterial(moldProject.getMoldInfo().getMaterial());
		    tryMold.setGrade(moldProject.getMoldInfo().getMaterial().getGrade());
		    if (moldProject.getMoldInfo().getProperty() != null) {
			tryMold.setColor(moldProject.getMoldInfo().getProperty().getName());
		    }
		}
	    }
	    model.addAttribute("tryMold", tryMold);
	    model.addAttribute("cavity", TryConditionHelper.service.getCavityByProject(moldProject));
	    return "project/trycondition/createTryMoldForm";
	}
    }

    /**
     * Try조건표 등록(MOLD)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : createTryMold
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTryMold")
    @ResponseBody
    public Map<String, Object> createTryMold(TryMoldDTO paramObject) throws Exception {
	try {
	    KETTryCondition tryCondition = TryConditionHelper.service.save(paramObject);
	    return CommonUtil.returnData(true, CommonUtil.getOIDString(tryCondition));
	} catch (Exception e) {
	    return CommonUtil.returnData(false, e.getLocalizedMessage());
	}
    }

    /**
     * Try조건표 등록(PRESS)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : createTryPress
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/createTryPress")
    @ResponseBody
    public Map<String, Object> createTryPress(TryPressDTO paramObject) throws Exception {
	try {
	    KETTryCondition tryCondition = TryConditionHelper.service.save(paramObject);

	    return CommonUtil.returnData(true, CommonUtil.getOIDString(tryCondition));
	} catch (Exception e) {
	    return CommonUtil.returnData(false, e.getLocalizedMessage());
	}
    }

    /**
     * Try 조건표 삭제
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : deleteTryCondition
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/deleteTryCondition")
    @ResponseBody
    public Map<String, Object> deleteTryCondition(TryConditionDTO paramObject) throws Exception {
	try {
	    KETTryCondition tryCondition = TryConditionHelper.service.delete(paramObject);
	    return CommonUtil.returnData(true, CommonUtil.getFullOIDString(tryCondition));
	} catch (Exception e) {
	    return CommonUtil.returnData(false, e.getLocalizedMessage());
	}
    }

    /**
     * Try 조건표 검색 데이터
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : findPagingList
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(TryConditionDTO paramObject) throws Exception {
	// if (paramObject.getPage() == 0) {
	// SessionUtil.removeAttribute("pageSessionId");
	// }
	// PageControl pageControl = TryConditionHelper.service.findPaging(paramObject);
	// SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	// QueryResult queryResult = pageControl.getResult();
	// List<TryConditionDTO> programList = new ArrayList<TryConditionDTO>();
	// while (queryResult.hasMoreElements()) {
	// Object[] objArr = (Object[]) queryResult.nextElement();
	// programList.add(new TryConditionDTO((KETTryCondition) objArr[0]));
	// }
	// return EjsConverUtil.convertToDto(programList, pageControl);
	// methodserver로 부터 데이터를 조회한다.
	List<KETTryCondition> tryConditionList = TryConditionHelper.service.find(paramObject);
	// DTO 객체로 변환
	List<TryConditionDTO> tryConditionDTOList = new ArrayList<TryConditionDTO>();
	for (KETTryCondition vo : tryConditionList) {
	    tryConditionDTOList.add(new TryConditionDTO(vo));
	}
	return EjsConverUtil.convertToDto(tryConditionDTOList);
    }

    /**
     * Try 조건표 등록을 위한 프로젝트 Role별 멤버 정보 조회
     * 
     * @param projectOid
     * @return
     * @throws WTException
     * @throws TeamException
     * @throws Exception
     * @메소드명 : getRoleMember
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Hashtable getRoleMember(String projectOid) throws WTException, TeamException, Exception {
	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	// ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
	// E3PSProjectData data = new E3PSProjectData(project);
	Vector vecTeamStd = null;
	TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
	if (tempTeam != null) {
	    vecTeamStd = tempTeam.getRoles();
	}
	Hashtable roleHash = new Hashtable();
	for (int i = vecTeamStd.size() - 1; i > -1; i--) {
	    Role role = (Role) vecTeamStd.get(i);
	    String roleName_en = role.toString();
	    QueryResult roleQr = ProjectUserHelper.manager.getProjectRoleMember(project, null, roleName_en);
	    if (roleQr.hasMoreElements()) {
		ProjectMemberLink link = (ProjectMemberLink) roleQr.nextElement();
		roleHash.put(roleName_en, new PeopleData(link.getMember()).name);
	    } else {
		vecTeamStd.remove(i);
	    }
	}
	return roleHash;
    }

    /**
     * Try 조건표 검색 화면
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : listTryCondition
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listTryCondition")
    public void listTryCondition(TryConditionDTO paramObject) throws Exception {
    }

    /**
     * Try 조건표 수정(MOLD)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : updateTryMold
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTryMold")
    @ResponseBody
    public Map<String, Object> updateTryMold(TryMoldDTO paramObject) throws Exception {
	try {
	    KETTryCondition tryCondition = TryConditionHelper.service.modify(paramObject);
	    return CommonUtil.returnData(true, CommonUtil.getOIDString(tryCondition));
	} catch (Exception e) {
	    return CommonUtil.returnData(false, e.getLocalizedMessage());
	}
    }

    /**
     * Try 조건표 수정 화면(MOLD)
     * 
     * @param paramObject
     * @param model
     * @메소드명 : updateTryMoldForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTryMoldForm")
    public void updateTryMoldForm(TryMoldDTO paramObject, Model model) throws Exception {
	KETTryMold tryMold = (KETTryMold) CommonUtil.getObject(paramObject.getOid());
	model.addAttribute("tryMold", tryMold);
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(tryMold));
	model.addAttribute("cavity", TryConditionHelper.service.getCavityByProject(tryMold.getMoldProject()));
    }

    /**
     * Try 조건표 수정(PRESS)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : updateTryPress
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTryPress")
    @ResponseBody
    public Map<String, Object> updateTryPress(TryPressDTO paramObject) throws Exception {
	try {
	    KETTryCondition tryCondition = TryConditionHelper.service.modify(paramObject);
	    return CommonUtil.returnData(true, CommonUtil.getOIDString(tryCondition));
	} catch (Exception e) {
	    return CommonUtil.returnData(false, e.getLocalizedMessage());
	}
    }

    /**
     * Try 조건표 수정 화면(PRESS)
     * 
     * @param paramObject
     * @param model
     * @메소드명 : updateTryPressForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateTryPressForm")
    public void updateTryPressForm(TryPressDTO paramObject, Model model) {
	KETTryPress tryPress = (KETTryPress) CommonUtil.getObject(paramObject.getOid());
	model.addAttribute("tryPress", tryPress);
	List<Map<String, Object>> partList = null;
	try {
	    partList = TryConditionHelper.service.getTryPartList(CommonUtil.getOIDString(tryPress));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	model.addAttribute("partList", partList);
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(tryPress));
    }

    /**
     * Try 조건표 조회(MOLD)
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : viewTryMoldForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewTryMoldForm")
    public void viewTryMoldForm(TryMoldDTO paramObject, Model model) throws Exception {
	KETTryMold tryMold = (KETTryMold) CommonUtil.getObject(paramObject.getOid());
	model.addAttribute("tryMold", tryMold);
	model.addAttribute("tryCondition", new TryConditionDTO(tryMold));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(tryMold));
	model.addAttribute("cavity", TryConditionHelper.service.getCavityByProject(tryMold.getMoldProject()));
    }

    /**
     * Try 조건표 조회(PRESS)
     * 
     * @param paramObject
     * @param model
     * @throws Exception
     * @메소드명 : viewTryPressForm
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewTryPressForm")
    public void viewTryPressForm(TryPressDTO paramObject, Model model) throws Exception {
	KETTryPress tryPress = (KETTryPress) CommonUtil.getObject(paramObject.getOid());
	model.addAttribute("tryPress", tryPress);
	model.addAttribute("tryCondition", new TryConditionDTO(tryPress));
	List<Map<String, Object>> partList = null;
	try {
	    partList = TryConditionHelper.service.getTryPartList(CommonUtil.getOIDString(tryPress));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	model.addAttribute("partList", partList);
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(tryPress));
    }

    /**
     * DieNo별 Try 조건표
     * 
     * @param dieNo
     * @throws Exception
     * @메소드명 : listTryCondtionPopupByDieNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listTryCondtionByDieNo")
    public void listTryCondtionByDieNo(TryConditionDTO paramObject, Model model) throws Exception {
	List<KETTryCondition> tryConditionList = TryConditionHelper.service.find(paramObject);
	// DTO 객체로 변환
	List<TryConditionDTO> tryConditionDTOList = new ArrayList<TryConditionDTO>();
	for (KETTryCondition vo : tryConditionList) {
	    tryConditionDTOList.add(new TryConditionDTO(vo));
	}
	model.addAttribute("tryConditionList", CommonUtil.toJsonString(tryConditionDTOList));
    }

    /**
     * DieNo별 Try 조건표 팝업
     * 
     * @param dieNo
     * @throws Exception
     * @메소드명 : listTryCondtionPopupByDieNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listTryCondtionByDieNoPopup")
    public void listTryCondtionByDieNoPopup(TryConditionDTO paramObject, Model model) throws Exception {
	this.listTryCondtionByDieNo(paramObject, model);
    }

    /**
     * Try조건표 Excel Export
     * 
     * @param response
     * @param paramObject
     * @throws Exception
     * @메소드명 : excelExportTryCondition
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/excelExportTryCondition")
    public void excelExportTryCondition(HttpServletResponse response, TryConditionDTO paramObject) throws Exception {
	KETTryCondition tryCondition = (KETTryCondition) CommonUtil.getObject(paramObject.getOid());
	GregorianCalendar gc = new GregorianCalendar();
	long milis = gc.getTimeInMillis();
	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String folder = sWtHome + "/temp/download/";
	String fileName = "";
	;
	if (tryCondition instanceof KETTryMold) {
	    fileName = "TryMold" + "_" + Long.toString(milis) + ".xlsx";
	} else {
	    fileName = "TryPress" + "_" + Long.toString(milis) + ".xlsx";
	}
	String savePath = folder + fileName;
	if (tryCondition == null) {
	    return;
	}
	TryExcelExportUtil excelExport = new TryExcelExportUtil(tryCondition);
	excelExport.exportExcelTemplate();
	excelExport.saveAs(savePath);

	response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
	response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	OutputStream out = response.getOutputStream();
	FileInputStream fis = null;
	try {
	    File file = new File(savePath);
	    fis = new FileInputStream(file);
	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;

	    // write bytes read from the input stream into the output stream
	    while ((bytesRead = fis.read(buffer)) != -1) {
		out.write(buffer, 0, bytesRead);
	    }

	    fis.close();
	    out.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (Exception e) {
		}
	    }
	}
	out.flush();
    }
}
