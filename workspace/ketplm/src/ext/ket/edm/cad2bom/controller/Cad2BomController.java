package ext.ket.edm.cad2bom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import ext.ket.edm.cad2bom.service.Cad2BomHelper;
import ext.ket.shared.log.Kogger;

/**
 * 
 * 
 * @클래스명 : PartBaseController
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/edm/cad2bom/*")
public class Cad2BomController {

    // CAD2BOM 등록
    @RequestMapping("/checkRootValid") 
    @ResponseBody
    public String checkRootValid(@RequestParam(value = "oid") String modelOid, SessionStatus status, Model model) throws Exception {
	try {
	    
	    // "F↕~~"; //ROOT_NOT_FIRST_CAD
	    // "E↕~~"; //ROOT_NOT_INCLUDE_EOHEADER
	    // "B↕~~"; // ROOT_HAS_BOM_ALREADY
	    String cad2BomFlag = Cad2BomHelper.service.checkRootValid(modelOid);
	    status.setComplete();
	    return  cad2BomFlag;
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }
    
//    // CAD2BOM 등록
//    @RequestMapping("/checkRootValid") 
//    @ResponseBody
//    public String checkRootValid(@RequestParam(value = "oid") String modelOid, SessionStatus status, Model model) throws Exception {
//	try {
//	    
//	    // "F↕~~"; //ROOT_NOT_FIRST_CAD
//	    // "E↕~~"; //ROOT_NOT_INCLUDE_EOHEADER
//	    // "B↕~~"; // ROOT_HAS_BOM_ALREADY
//	    String cad2BomFlag = Cad2BomHelper.service.checkRootValid(modelOid);
//	    status.setComplete();
//	    return  cad2BomFlag;
//	    
//	} catch (Exception e) {
//	    Kogger.error(getClass(), e);
//	    return "Fail";
//	}
//    }
    
}
