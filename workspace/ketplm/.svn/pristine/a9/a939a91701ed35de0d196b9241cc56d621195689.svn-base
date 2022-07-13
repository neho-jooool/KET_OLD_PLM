package ext.ket.project.work.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.org.WTUser;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.WorkProject;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.project.work.service.WorkProjectHelper;


@Controller
@RequestMapping("/project/work/*")
public class WorkProjectController {
    
    
    @RequestMapping("/listWorkProject")
    public void listWorkProject() {
    }
    
    @RequestMapping("/createWorkProjectForm")
    public Model createWorkProjectForm(String oid, Model model) {
	
	WorkProject pjt =(WorkProject)CommonUtil.getObject(oid);
	
	String pjtName = "";
	String planStartDate = "";
	String workType = "";
	String rank = "";
	String tempdevUser = "";
	String devUser = "";
	
	if(pjt != null){
	    
	    pjtName = pjt.getPjtName();
	    
	    ExtendScheduleData schedule = (ExtendScheduleData)((E3PSProject)pjt).getPjtSchedule().getObject();
	    
	    planStartDate = DateUtil.getDateString(schedule.getPlanStartDate(), "date");
	    NumberCode num = pjt.getRank();
	    
	    if(num != null){
		rank = CommonUtil.getOIDString(num);
	    }
	    num = pjt.getWorkType();
	    
	    if(num != null){
		workType = CommonUtil.getOIDString(num);
	    }
	    WTUser pm = ProjectUserHelper.manager.getPM(pjt);
	    tempdevUser = pm.getFullName();
	    devUser = CommonUtil.getOIDString(pm);
	}
	
	model.addAttribute("pjtName", pjtName);
	model.addAttribute("planStartDate", planStartDate);
	model.addAttribute("workType", workType);
	model.addAttribute("rank", rank);
	model.addAttribute("tempdevUser", tempdevUser);
	model.addAttribute("devUser", devUser);
	model.addAttribute("oid", oid);
	
	return model;
    }
    
    
    @ResponseBody
    @RequestMapping("/saveWorkProject")
    public Map<String, Object> saveWorkProject(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            reqMap.put("category", req.getParameterValues("category"));
            resMap = WorkProjectHelper.service.saveWorkProject(reqMap);
            resMap.put("result", true);

        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }
        
        return resMap;
    }
}
