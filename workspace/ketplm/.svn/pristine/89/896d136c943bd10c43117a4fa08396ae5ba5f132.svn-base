package ext.ket.edm.stamping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ext.ket.edm.stamping.service.StampingHelper;

/**
 * 
 * 
 * @클래스명 : DrawingDistReqController
 * @작성자 : KKW
 * @작성일 : 2014. 7. 22.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */

@Controller
@RequestMapping("/edm/stamping/*")
public class StampingController {

    @RequestMapping("/testInsertQueue")
    public void drawingDistRequestSearchList() throws Exception {

	StampingHelper.service.inputQueueDrawingDistReq();
    }

}
