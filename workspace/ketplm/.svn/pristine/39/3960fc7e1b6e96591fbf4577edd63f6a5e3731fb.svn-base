package ext.ket.project.bom.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ext.ket.common.files.util.FileContentUtil;
import ext.ket.project.bom.service.BomCheckHelper;

@Controller
@RequestMapping("/project/bom/*")
public class BomCheckController {

    @RequestMapping("/bomCheckList")
    public Model bomCheckList(String pjtNo, Model model) {

	model.addAttribute("pjtNo", pjtNo);

	return model;

    }

    @ResponseBody
    @RequestMapping("/findPagingList")
    public Map<String, Object> findPagingList(@RequestParam Map<String, Object> reqMap) throws Exception {
	FileContentUtil.manager.saveDownloadHistory("BOM정합성 검증", (String) reqMap.get("pjtNo"));
	return BomCheckHelper.service.findPagingList(reqMap);
    }

    @ResponseBody
    @RequestMapping("/getMoldTaskOid")
    public Map<String, String> getMoldTaskOid(String pjtNo) throws Exception {

	return BomCheckHelper.service.getMoldTaskOid(pjtNo);
    }
}
