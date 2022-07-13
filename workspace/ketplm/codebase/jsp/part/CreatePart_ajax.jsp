<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="e3ps.common.code.*,
				 e3ps.common.jdf.config.*,
				 e3ps.common.util.WCUtil,
				 e3ps.common.util.PlmDBUtil,
				 e3ps.common.web.HtmlTagUtil,
				 e3ps.part.dao.KETNewPartListDao"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
				wt.lifecycle.LifeCycleHelper,
				wt.lifecycle.PhaseTemplate,
				java.util.Vector,
				java.sql.Connection"%>				 
<%
	Vector tMap = null;
	String codeType = request.getParameter("codeType");
	String code = request.getParameter("code");
	String type = request.getParameter("type");
	NumberCode tNum = null; 	
	StringBuffer burf = new StringBuffer();
	
	if("getNumberCodeLevelType".equals(type)) {
			tMap = NumberCodeHelper.manager.getNumberCodeLevelType(codeType, code);
			
			for(int ii = 0; ii < tMap.size(); ii++) {
				tNum = (NumberCode)tMap.get(ii);						
				if(ii > 0) {
					burf.append(",");
				}
				burf.append(tNum.getCode());			
				burf.append("|");							
				burf.append(tNum.getCode() + " : " +tNum.getName());		
			}	
	} else if("getNumberCodeLevel".equals(type)) {
			tMap = NumberCodeHelper.manager.getNumberCodeLevel(codeType, code);
			for(int ii = 0; ii < tMap.size(); ii++) {
				tNum = (NumberCode)tMap.get(ii);						
				if(ii > 0) {
					burf.append(",");
				}
				burf.append(tNum.getCode());			
				burf.append("|");		
				burf.append(tNum.getName());	
			}	
	} else if("chkPartNumber".equals(type)) {
		 //커넥션
        Connection conn = null;		        
        // 커넥션 생성
		conn = PlmDBUtil.getConnection();
		KETNewPartListDao ketNewPartListDao = new KETNewPartListDao(conn);
		
		burf.append(ketNewPartListDao.getPartNumberSize(codeType+code));
		
		if(conn != null) {
			//커넥션 종료
			PlmDBUtil.close(conn);
		}	
	}
%>
<%=burf%>
