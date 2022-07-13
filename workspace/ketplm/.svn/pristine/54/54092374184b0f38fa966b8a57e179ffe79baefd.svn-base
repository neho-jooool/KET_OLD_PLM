package ext.ket.part.classify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.shared.log.Kogger;

/**
 * 부품 분류 체계
 * 
 * @클래스명 : PartClazController
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 25.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/part/classify/*")
public class PartClazController {

    /**
     * 부품 분류체계 첫 화면 jsp call
     * 
     * @throws Exception
     * @메소드명 : viewListClaz
     * @작성자 : yjlee
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/viewClazListForm")
    public void viewClazListForm() throws Exception {

	Kogger.debug(getClass(), "###########################################");
	Kogger.debug(getClass(), "############ part Classifcaton ############");
	Kogger.debug(getClass(), "###########################################");

    }

    /**
     * Tree Grid 의 Data를 download 해 줌
     * 
     * @param model
     * @throws Exception
     * @메소드명 : listClazDataXml
     * @작성자 : yjlee
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listClazDataXml")
    public void listClazDataXml(Model model) throws Exception {

	String xmlStrt = PartClazHelper.service.searchFullListXml(false);

	model.addAttribute("result", xmlStrt);
    }

    /**
     * TreeGrid의 데이터를 업로드 해줌
     * 
     * @param tgData
     * @param status
     * @param model
     * @throws Exception
     * @메소드명 : save
     * @작성자 : yjlee
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/saveClazDataXml")
    public void save(@RequestParam(value = "TGData") String tgData, SessionStatus status, Model model) throws Exception {

	// partClassificationDTO.setName(SessionHelper.manager.getPrincipal().getName());
	// PartClazHelper.service.insertWithParent(partClassificationDTO);
	// tgData = tgData.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replaceAll("&apos;", "'").replaceAll("&amp;", "&");

	Kogger.debug(getClass(), "==========================================");// ,@RequestBody ClazXmlBodyObject body
	Kogger.debug(getClass(), "==========   saveClazDataXml    ==========");
	Kogger.debug(getClass(), "==========================================");

	// test
	// OxmUnmarshaller unmarshaller = new OxmUnmarshaller();
	// ClazXmlGridObject clazXmlGridObject = unmarshaller.getClazXmlGridObject(tgData);
	// Kogger.debug(getClass(), "clazXmlGridObject:" + clazXmlGridObject);

	try {

	    // TKLEE TEST
//	    StringInputStream inputstream = new StringInputStream(tgData);
//
//	    String filePath = "D:\\ket\\workspace\\ketplm\\codebase\\extcore\\jsp\\part\\classify\\listClazDataXmlUp.xml";
//	    File outputFile = new File(filePath);
//	    FileOutputStream fo = new FileOutputStream(outputFile);
//
//	    try {
//
//		int j = 0;
//		byte abyte0[] = new byte[(int) 32000];
//		while ((j = inputstream.read(abyte0, 0, abyte0.length)) >= 0) {
//		    fo.write(abyte0, 0, j);
//		}
//
//		fo.flush();
//		fo.close();
//		inputstream.close();
//
//	    } catch (IOException e) {
//		Kogger.error(getClass(), e);
//		throw e;
//	    }

	    PartClazHelper.service.saveXmlTreeData(tgData);
	    status.setComplete();
	    model.addAttribute("result", "1");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    model.addAttribute("result", "-1");
	}

	// return "/extcore/jsp/part/classify/viewClazListUpload"; // "/ext/part/classify/viewClazListUpload";
    }

}
