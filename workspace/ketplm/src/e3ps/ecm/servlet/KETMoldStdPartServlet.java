package e3ps.ecm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.MoldStdPartBeans;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.shared.log.Kogger;

public class KETMoldStdPartServlet extends CommonServlet {

	private static final long serialVersionUID = 2318955843892783919L;

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		
		RequestDispatcher rd = null;
		
		String docLinkOid = StringUtil.checkNull( req.getParameter("docLinkOid") );
		String docNo = StringUtil.checkNull( req.getParameter("docNo") );
		String ecoOid = StringUtil.checkNull( req.getParameter("ecoOid") );
		String ecoNo = StringUtil.checkNull( req.getParameter("ecoNo") );
		String popYn = StringUtil.checkNull( req.getParameter("pop") );
		
		String command = StringUtil.checkNull( req.getParameter("cmd") );
		
		String[] partOidList = req.getParameterValues("partOid");
		String[] partNoList = req.getParameterValues("partNo");
		String[] chgTypeList = req.getParameterValues("changeType");
		String[] descList = req.getParameterValues("description");
		
		MoldStdPartBeans beans = new MoldStdPartBeans();
		boolean isExist = beans.isExistStdPartLine( docLinkOid );
		boolean isSuccess = false;
		
		ArrayList<Hashtable<String, String>> partLineList = new ArrayList<Hashtable<String, String>>();
		if( isExist )
		{
			partLineList = beans.getStdPartLineList( docLinkOid );
		}
		
		if( command.equalsIgnoreCase("Save") )
		{
			try 
			{
				partLineList = KETEcmHelper.service.saveMoldStdPartLine(docLinkOid, partOidList, partNoList, chgTypeList, descList, ecoOid);
				isSuccess = true;
			} 
			catch (WTException e) 
			{
				Kogger.error(getClass(), e);
			}
		}

		req.setAttribute( "partLineList", partLineList );
		
		if( command.equalsIgnoreCase("Save") && isSuccess )
		{
			alertGo( res, "/plm/servlet/e3ps/MoldStdPartServlet", "정상적으로 처리되었습니다.", docNo, docLinkOid, ecoOid, ecoNo );
		}
		else if( !command.equalsIgnoreCase("Save") )
		{
			rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateStdPartLine.jsp?pop="+popYn+"&docNo="+docNo+"&docLinkOid="+docLinkOid+"&ecoOid="+ecoOid+"&ecoNo="+ecoNo );
			rd.forward( req, res );
		}
		else if( command.equalsIgnoreCase("Save") && !isSuccess )
		{
			alertGo( res, "/plm/servlet/e3ps/MoldStdPartServlet", "저장에 실패하였습니다.", docNo, docLinkOid, ecoOid, ecoNo );
		}
	}
	
	public void alertGo( HttpServletResponse res, String url, String msg, String docNo, String docLinkOid, String ecoOid, String ecoNo )
	{		
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
		  	String rtn_msg = "";
			rtn_msg = "\n <form name='frmProc' method='post' target='_self' action='" + url + "'>"
			+ "\n <input type='hidden' name='docNo' value='" + docNo + "'>"
			+ "\n <input type='hidden' name='docLinkOid' value='" + docLinkOid + "'>"
			+ "\n <input type='hidden' name='ecoOid' value='"+ecoOid+"'>"
			+ "\n <input type='hidden' name='ecoNo' value='"+ecoNo+"'>"
			+ "\n </form>"
			+ "\n <script language=\"javascript\">"
			+ "\n   alert(\"" + msg + "\");"
			+ "\n   document.frmProc.submit();"
			+ "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

}
