package ext.ket.dashboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.web.PageControl;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.dashboard.entity.StandardDTO;
import ext.ket.dashboard.entity.StandardDataDTO;
import ext.ket.dashboard.service.DashBoardService;
import ext.ket.dashboard.service.StandardMngService;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramNotifyDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.dto.EjsDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * 프로그램 관리 Controller
 * 
 * @클래스명 : ProgramController
 * @작성자 : sw775.park
 * @작성일 : 2014. 7. 11.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/dashboard/standard/*")
public class StandardMngController {

    @Inject
    private StandardMngService standardService;

    /**
     * 공수관리 기준정보 확인화면
     * 
     * @메소드명 : viewStanardForm
     * @작성자 : 황정태
     * @작성일 : 2016. 03. 28.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewStandardForm")
    public void viewStandardForm(StandardDTO paramObject, Model model) throws Exception {
	model.addAttribute("selectTab", paramObject.getSelectTab());
    }

    /**
     * 프로그램에 등록된 차종별 event 일정
     * 
     * @param programOid
     * @return
     * @throws Exception
     * @메소드명 : findEventByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findStandardByDashboard")
    @ResponseBody
    public Map<String, Object> findStandardByDashboard(StandardDTO paramObject, Model model) throws Exception {

	return EjsConverUtil.convertToDto(standardService.findStandardByDashboard(paramObject));

    }

    /**
     * 프로그램 수정
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : updateProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/updateStandard")
    @ResponseBody
    public Map<String, Object> updateStandard(StandardDataDTO paramObject) {
	String msg = "";
	try {
	    return CommonUtil.returnData(true, standardService.save(paramObject));
	} catch (Exception e) {
	    msg = e.getLocalizedMessage();
	}

	return CommonUtil.returnData(false, msg);
    }

    @RequestMapping("/isLeaf")
    @ResponseBody
    public Map<String, Object> isLeaf(String department) {
	String leaf = "";
	String msg = "";
	try {
	    
	    if("root".equals(department)){
		return CommonUtil.returnData(true, "0");
	    }
	    
	    Department depart = (Department) CommonUtil.getObject(department);
	    
	    if(depart.getParent() == null){
		return CommonUtil.returnData(true, "0");
	    }
	    
	    ArrayList childList = DepartmentHelper.manager.getChildList(depart);
	    if (childList.size() > 0) {
		leaf = "0";
	    } else {
		leaf = "1";
	    }
	    return CommonUtil.returnData(true, leaf);
	} catch (Exception e) {
	    msg = e.getLocalizedMessage();
	}

	return CommonUtil.returnData(false, msg);
    }
}
