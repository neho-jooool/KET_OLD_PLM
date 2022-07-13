<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.write.WritableSheet"%>
<%@page import="jxl.write.Label"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
	
	String oid = request.getParameter("oid");
	NumberCode pnc = null;
	ReferenceFactory rf = new ReferenceFactory();
	if(oid!=null && oid.length()>0){
		
		pnc = (NumberCode)rf.getReference(oid).getObject();
	}
	String codetype = request.getParameter("codetype");
	
	
	String sWtHome = "";
	String sFilePath = "", sFileName = "";
	//file path
	sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	sFilePath = sWtHome + "/codebase/jsp/common/code" ;
	//file name
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMdd");
	sFileName = "codeExcelForm" +  ff.format(new Date()) + ".xls";
	//copy file
	File copyfile = new File(sFilePath+"/codeExcelForm.xls") ;
	
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=EUC-KR"); 
	response.setHeader("Content-Disposition","attachment; filename="+sFileName);
	ServletOutputStream objSos=response.getOutputStream();
	
	Workbook wbb= Workbook.getWorkbook(copyfile);
	WritableWorkbook writebook = Workbook.createWorkbook(objSos, wbb);
	WritableSheet s1 = writebook.getSheet(0);
	if(pnc != null){
		s1.setName(pnc.getCode());
	}else{
		s1.setName(codetype);
	}
    QuerySpec qs = new QuerySpec(NumberCode.class);
	
    qs.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", codetype), new int[] { 0 });
	if(pnc != null){
	    qs.appendAnd();
	    SearchCondition sc = new SearchCondition(NumberCode.class, "parentReference.key.id", 
				SearchCondition.EQUAL, pnc.getPersistInfo().getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { 0 });
	}
	
    QueryResult qr = PersistenceHelper.manager.find(qs);
	int i= 2;
	
	Label lr = new Label(1, 0, codetype );
	s1.addCell(lr);
	lr = new Label(3, 0, codetype );
	s1.addCell(lr);
	
	while(qr.hasMoreElements()){
	  	NumberCode nc = (NumberCode)qr.nextElement();
      	   
	  	lr = new Label(0, i, nc.getCode() );
		s1.addCell(lr);
		
		lr = new Label(1, i, nc.getName());
		s1.addCell(lr);
		
		lr = new Label(2, i, nc.getDescription());
		s1.addCell(lr);
		
		lr = new Label(3, i, (nc.getParent()!=null)?nc.getParent().getCode():"");
		s1.addCell(lr);
		
		lr = new Label(4, i, StringUtil.checkNull(nc.getWcType()));
		s1.addCell(lr);
		
		lr = new Label(5, i, StringUtil.checkNull(nc.getDsCode()));
		s1.addCell(lr);
		
		lr = new Label(6, i, StringUtil.checkNull(nc.getCostCode()));
		s1.addCell(lr);
		
		lr = new Label(7, i, StringUtil.checkNull(nc.getVenderCode()) );
		s1.addCell(lr);
	       
		i++; 
	}
	writebook.write();
	writebook.close();
	
%>
