package e3ps.ecm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.common.web.CommonServlet;
import e3ps.ecm.entity.KETMoldChangeRequestVO;

public class TestMoldEcoServlet extends CommonServlet {
	private static final long serialVersionUID = -1632992021717095364L;

	protected void doService(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		KETMoldChangeRequestVO ketMoldChangeRequestVO = new KETMoldChangeRequestVO();
		ketMoldChangeRequestVO.getKetMoldChangeRequest().getLifeCycleName();
		ketMoldChangeRequestVO.getKetMoldChangeRequest().getLifeCycleState();
//		ketMoldChangeRequestVO.getKetMoldChangeRequest().getLifeCycleTemplate()
	}
}
