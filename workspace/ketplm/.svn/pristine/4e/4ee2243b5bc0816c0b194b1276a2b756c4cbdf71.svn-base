package ext.ket.part.replacePart.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import e3ps.common.util.CommonUtil;
import ext.ket.common.files.util.DownloadView;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.entity.RivalPartInfo;
import ext.ket.part.replacePart.service.ReplacePartHelper;
import ext.ket.part.replacePart.util.ReplacePartUtil;
import ext.ket.part.replacePart.util.replacePartUploadUtil;

@Controller
@RequestMapping("/replacePart/*")
public class ReplacePartConroller {
    
    @RequestMapping("/replacePartList")
    public Model replacePartList(Model model) throws Exception {
	String ERROR = "권한이 없습니다.";
	
	boolean isAdmin = CommonUtil.isMember("대체품관리");
	
	if(CommonUtil.isMember("대체품조회") || CommonUtil.isMember("대체품관리") || CommonUtil.isAdmin()){
	    ERROR = "";
	}
	
	model.addAttribute("ERROR",ERROR);
	model.addAttribute("isAdmin",isAdmin);
	return model;
    }
    
    @RequestMapping("/replacePartPopup")
    public Model replacePartPopup(String oid, String newPartNo, Model model) throws Exception {
	RivalPartInfo rival = (RivalPartInfo)CommonUtil.getObject(oid);
	
//	if(rival == null && StringUtils.isNotEmpty(newPartNo)){
//	    Map<String, Object> reqMap = new HashMap<String, Object>();
//	    reqMap.put("partNo", newPartNo);
//	    rival = ReplacePartUtil.manager.getRivalPart(reqMap);
//	}
	Map<String, Object> resMap = new HashMap<String, Object>();
	String ERROR = "";
	if(rival != null){
	    resMap = ObjectUtil.manager.converObjectToMap(rival);
	    resMap.put("rivalPartOid", CommonUtil.getOIDString(rival));    
	}else{
	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    reqMap.put("partNo", newPartNo);
	    RivalPartInfo part = ReplacePartUtil.manager.getRivalPart(reqMap);
	    if(part != null){
		ERROR = newPartNo +" 는 이미 등록된 경쟁사Part 입니다.";
	    }
	}
	
	resMap.put("newPartNo", newPartNo);
	
	model.addAttribute("rival", resMap);
	model.addAttribute("ERROR", ERROR);
	
	return model;
    }
    
    @ResponseBody
    @RequestMapping("/saveReplacePart")
    public Map<String, Object> saveReplacePart(@RequestParam Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	Map<String, Object> ioMap = new HashMap<String, Object>();
	
	try {
	    resMap = ReplacePartHelper.service.saveReplacePart(reqMap);
	    
	    ioMap.put("Result", 0);
	    ioMap.put("Reload", 1);
	    resMap.put("IO", ioMap);

	} catch (Exception e) {
	    ioMap.put("Result", -1);
	    resMap.put("IO", ioMap);
	    e.printStackTrace();
	}

	return resMap;
    }
    
    @ResponseBody
    @RequestMapping("/replacePartGridData")
    public Map<String, Object> replacePartGridData(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) {
	
	Map<String, Object> result = null;
	reqMap.put("rival", req.getParameterValues("rival"));
	reqMap.put("replaceGb", req.getParameterValues("replaceGb"));
	try {
	    result = ReplacePartHelper.service.getReplacePartGridData(reqMap);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }
    
    @ResponseBody
    @RequestMapping("/replacePartExcelUpload")
    public Map<String, Object> replacePartExcelUpload(@RequestParam Map<String, Object> reqMap,
            @RequestParam("uploadFile") MultipartFile file) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        try {

            System.out.println("file ==== " + file.getInputStream());
            
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
            String tmpFileName = sdf.format(cal.getTime());
		
            File uploadFile = new File(DownloadView.TEMPPATH + File.separator + tmpFileName+"_"+file.getOriginalFilename());
            file.transferTo(uploadFile);
            System.out.println("uploadFile ==== " + uploadFile.getAbsolutePath());
            
            replacePartUploadUtil.load(uploadFile.getAbsolutePath());

            resMap.put("message", "저장되었습니다.");
            resMap.put("result", true);

        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    @ResponseBody
    @RequestMapping("/replacePartExcelExtract")
    public Map<String, Object> replacePartExcelExtract(@RequestParam Map<String, Object> reqMap,
            @RequestParam("multiSearchFile") MultipartFile file) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        List<Map<String, Object>> partNoList = new ArrayList<Map<String, Object>>();
        
        try {

            System.out.println("file ==== " + file.getInputStream());
            
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
            String tmpFileName = sdf.format(cal.getTime());
		
            File uploadFile = new File(DownloadView.TEMPPATH + File.separator + tmpFileName+"_"+file.getOriginalFilename());
            file.transferTo(uploadFile);
            System.out.println("uploadFile ==== " + uploadFile.getAbsolutePath());
            
            partNoList = replacePartUploadUtil.partNoExtract(uploadFile.getAbsolutePath());
            resMap.put("partNoList", partNoList);
            resMap.put("result", true);

        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    
    @ResponseBody
    @RequestMapping("/createReplacePartListExcel")
    public Map<String, Object> createReplacePartListExcel(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) {
        
        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            
            reqMap.put("rival", req.getParameterValues("rival"));
            reqMap.put("replaceGb", req.getParameterValues("replaceGb"));
            
            resMap = ReplacePartUtil.manager.createReplacePartListExcel(reqMap);
            resMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    @ResponseBody
    @RequestMapping("/replacePartDelete")
    public Map<String, Object> replacePartDelete(@RequestParam Map<String, Object> reqMap) {
        
        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            ReplacePartHelper.service.deleteReplacePart(reqMap);
            resMap.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
}
