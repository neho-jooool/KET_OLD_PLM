package ext.ket.shared.code.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.StringUtil;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.code.service.CacheManager;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;

@Controller
@RequestMapping("/code/*")
public class CodeController {
    @RequestMapping("/getChildCodeList")
    @ResponseBody
    public List<Map<String, String>> getChildCodeList(String codeType, String parentCode) throws Exception {
	ArrayList<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codeType);
	List<Map<String, String>> rtnListMap = new ArrayList<Map<String, String>>();
	Iterator<NumberCodeDTO> iterator = (arrayList != null) ? arrayList.iterator() : null;
	while (iterator != null && iterator.hasNext()) {
	    NumberCodeDTO dto = iterator.next();
	    if (dto.getPcode().equals(parentCode)) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("codeType", StringUtil.checkNull(dto.getCodetype().toString()));
		rtnMap.put("code", StringUtil.checkNull(dto.getCode()));
		rtnMap.put("value", StringUtil.checkNull(dto.getDisplay(KETMessageService.getMessageService().getLocale())));
		rtnMap.put("description", StringUtil.checkNull(dto.getDescription()));
		rtnMap.put("sorting", StringUtil.checkNull(dto.getSorting()));
		rtnMap.put("vendercode", StringUtil.checkNull(dto.getVendorCode()));
		rtnMap.put("ida2a2", StringUtil.checkNull(dto.getLoid()));
		rtnMap.put("oid", StringUtil.checkNull(dto.getOid()));
		rtnMap.put("oid2", StringUtil.checkNull(dto.getParentOid()));
		rtnMap.put("abbr", StringUtil.checkNull(dto.getAbbr()));

		rtnListMap.add(rtnMap);
	    }

	}
	return rtnListMap;
    }

    @RequestMapping("/doReloadNumberCode")
    @ResponseBody
    public boolean doReloadNumberCode() throws Exception {
	boolean flag = false;
	try {
	    CodeHelper.service.loadNumberCode();
	    CodeHelper.service.loadCarNumberCode();
	    flag = true;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}
	return flag;
    }
}
