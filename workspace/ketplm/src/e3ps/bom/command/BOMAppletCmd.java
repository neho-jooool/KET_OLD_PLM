package e3ps.bom.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Vector;

//import laf.foundation.front.command.LAbstractCommand;
//import laf.support.collection.LCollectionUtility;
//import laf.support.collection.LData;
//import laf.support.collection.LMultiData;

import e3ps.bom.controller.BOMAppletController;
import e3ps.bom.framework.aif.*;

public class BOMAppletCmd extends AbstractAIFCommand
{
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception 
    {
//		LData paramData = LCollectionUtility.getData(req);

		BOMAppletController bomAppletController = new BOMAppletController();

		// 신규 BOM State
		Vector bomStateVec = new Vector();
		bomStateVec = bomAppletController.getBOMStateController();
		req.setAttribute("bomState", bomStateVec);

		// BOM ECO State
		Vector bomEcoStateVec = new Vector();
		bomEcoStateVec = bomAppletController.getBOMECOStateController();
		req.setAttribute("bomEcoState", bomStateVec);

		// 로그인 사용자 정보
//		LMultiData userInfo = bomAppletController.getUserInfoController(paramData);
//		req.setAttribute("userInfo", userInfo);
//
//		req.setAttribute("paramData", paramData);
	}
    
}
