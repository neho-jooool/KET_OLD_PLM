package ext.ket.project.purchase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.groupware.company.PeopleData;
import ext.ket.common.files.util.FileContentUtil;
import ext.ket.project.purchase.service.KETPurchaseHelper;
import ext.ket.project.purchase.util.purchaseUtil;

@Controller
@RequestMapping("/project/purchase/*")
public class KETPurchaseColtroller {

    @RequestMapping("/kqisDocForm")
    public Model kqisDocForm(@RequestParam Map<String, Object> reqMap, Model model) {
	purchaseUtil putil = new purchaseUtil();
	String sabun = "";
	String url = (String) reqMap.get("url");
	try {
	    sabun = putil.getAccountNo();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	model.addAttribute("sabun", "20110147");
	model.addAttribute("url", url);
	return model;
    }

    @RequestMapping("/kqisRedirect")
    public Model kqisRedirect(@RequestParam Map<String, Object> reqMap, Model model) {
	String sabun = (String) reqMap.get("sabun");

	model.addAttribute("sabun", sabun);

	return model;
    }

    @ResponseBody
    @RequestMapping("/addPartList")
    public Map<String, Object> addPartList(@RequestParam Map<String, Object> reqMap) throws Exception {
	return KETPurchaseHelper.service.addPartList(reqMap);
    }

    @RequestMapping("/purchasePartAddForm")
    public Model purchasePartAddForm(Model model, @RequestParam Map<String, Object> reqMap) throws Exception {

	boolean isAuth = false;
	purchaseUtil pcu = new purchaseUtil();
	try {
	    isAuth = pcu.purchaseAuthCheck();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	model.addAttribute("authCheck", isAuth);
	model.addAttribute("pjtNo", (String) reqMap.get("pjtNo"));

	return model;
    }

    @RequestMapping("/purchaseProjectList")
    public Model purchaseProjectList(@RequestParam Map<String, Object> reqMap, Model model) {

	boolean isAuth = false;
	purchaseUtil pcu = new purchaseUtil();
	List<Map<String, String>> stateList = null;
	String pjtNo = (String) reqMap.get("pjtNo");
	String menu = (String) reqMap.get("menu");

	try {
	    FileContentUtil.manager.saveDownloadHistory("구매품개발현황조회", pjtNo);
	    isAuth = pcu.purchaseAuthCheck();
	    stateList = pcu.getStateList();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	model.addAttribute("authCheck", isAuth);
	model.addAttribute("pjtNo", pjtNo);
	model.addAttribute("menu", menu);
	model.addAttribute("stateList", stateList);
	return model;
    }

    @RequestMapping("/purchaseDocList")
    public Model purchaseDocList(@RequestParam Map<String, Object> reqMap, Model model) {

	purchaseUtil pcu = new purchaseUtil();
	List<Map<String, String>> stateList = null;
	String pjtNo = (String) reqMap.get("pjtNo");
	String sabun = "";
	try {
	    WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData pData = new PeopleData(user);
	    sabun = pData.people.getAccountNo();
	    FileContentUtil.manager.saveDownloadHistory("구매품산출물조회", pjtNo);
	    stateList = pcu.getStateList();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	model.addAttribute("pjtNo", pjtNo);
	model.addAttribute("sabun", sabun);
	model.addAttribute("stateList", stateList);
	return model;
    }

    @ResponseBody
    @RequestMapping("/findDocList")
    public Map<String, Object> findDocList(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) throws Exception {
	reqMap.put("state", req.getParameterValues("state"));
	reqMap.put("pjtState", req.getParameterValues("pjtState"));
	reqMap.put("devDateType", req.getParameterValues("devDateType"));
	reqMap.put("selectGb", req.getParameterValues("selectGb"));
	return KETPurchaseHelper.service.findDocList(reqMap);
    }

    @RequestMapping("/purchaseProjectUpdateList")
    public Model purchaseProjectUpdateList(String pjtNo, Model model) {

	purchaseUtil pcu = new purchaseUtil();
	boolean isAuth = false;
	List<Map<String, String>> stateList = null;

	try {
	    isAuth = pcu.purchaseAuthCheck();
	    stateList = pcu.getStateList();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	model.addAttribute("pjtNo", pjtNo);
	model.addAttribute("authCheck", isAuth);
	model.addAttribute("stateList", stateList);
	return model;
    }

    @ResponseBody
    @RequestMapping("/findPagingList")
    public Map<String, Object> findPagingList(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) throws Exception {
	reqMap.put("state", req.getParameterValues("state"));
	reqMap.put("pjtState", req.getParameterValues("pjtState"));
	reqMap.put("devDateType", req.getParameterValues("devDateType"));
	return KETPurchaseHelper.service.findPagingList(reqMap);
    }

    @ResponseBody
    @RequestMapping("/savePurchasePart")
    public Map<String, Object> savePurchasePart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    resMap = KETPurchaseHelper.service.savePurchasePart(reqMap);
	    resMap.put("message", "저장되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    resMap.put("result", false);
	    resMap.put("message", "저장 중 오류가 발생했습니다.");
	    e.printStackTrace();
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/deletePurchasePart")
    public Map<String, Object> deletePurchasePart(@RequestBody Map<String, Object> reqMap) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    KETPurchaseHelper.service.deletePurchasePart(reqMap);
	    resMap.put("message", "삭제되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    resMap.put("result", false);
	    resMap.put("message", "삭제 중 오류가 발생했습니다.");
	    e.printStackTrace();
	}

	return resMap;
    }

}
