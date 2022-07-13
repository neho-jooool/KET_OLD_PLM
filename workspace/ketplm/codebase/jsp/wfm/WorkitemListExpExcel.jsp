<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "jxl.write.WritableWorkbook"%>
<%@page import = "java.io.File"%>
<%@page import = "jxl.Workbook"%>
<%@page import = "jxl.write.WritableSheet"%>
<%@page import = "jxl.write.Label"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "java.util.Date"%>
<%@page import = "wt.workflow.work.WorkItem" %>
<%@page import = "wt.org.WTPrincipal" %>
<%@page import = "wt.session.SessionHelper" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "wt.workflow.engine.*" %>
<%@page import = "wt.workflow.work.*" %>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.wfm.util.ClassifyPBOUtil" %>
<%@page import = "wt.workflow.work.WfAssignedActivity" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
	String sWtHome = "";
	String sFilePath = "", sFileName = "";
	//file path
	sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	sFilePath = sWtHome + "/codebase/jsp/wfm" ;
	//file name
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMdd");
	WTPrincipal user = SessionHelper.manager.getPrincipal();
	sFileName = "WorkItemList" +  ff.format(new Date()) + ".xls";
	//copy file
	File copyfile = new File(sFilePath+"/ExcelForm.xls") ;
	
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=EUC-KR"); 
	response.setHeader("Content-Disposition","attachment; filename="+sFileName);
	ServletOutputStream objSos=response.getOutputStream();
	
	Workbook wbb= Workbook.getWorkbook(copyfile);
	WritableWorkbook writebook = Workbook.createWorkbook(objSos, wbb);
	
	WritableSheet s1 = writebook.getSheet(0);
	s1.setName("결재대기함 목록");

	int row = 2;

	//문서제목
	Label lr = new Label(0, 0, "결재대기함목록");
	s1.addCell(lr);
	
	//타이틀
	lr = new Label(0, row, "번호");
	s1.addCell(lr);

	lr = new Label(1, row, "유형");
	s1.addCell(lr);

	lr = new Label(2, row, "작업명");
	s1.addCell(lr);

	lr = new Label(3, row, "결재대상명");
	s1.addCell(lr);

	lr = new Label(4, row, "결재상태");
	s1.addCell(lr);

	lr = new Label(5, row, "기안자");
	s1.addCell(lr);

	lr = new Label(6, row, "도착일");
	s1.addCell(lr);
	
	QuerySpec spec = new QuerySpec();
	int itemIdx = spec.addClassList(WorkItem.class,true);
	spec.appendWhere(new SearchCondition(WorkItem.class , 
			"ownership.owner.key", SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(user)),new int[]{itemIdx});
	if(spec.getConditionCount()>0) spec.appendAnd();
	spec.appendWhere(new SearchCondition(WorkItem.class,
			WorkItem.STATUS, SearchCondition.EQUAL, "POTENTIAL"),new int[] {itemIdx});
	spec.appendOrderBy(new OrderBy(new ClassAttribute(WorkItem.class, "thePersistInfo.createStamp"),true), new int[]{itemIdx});
	
	QueryResult result = PersistenceHelper.manager.find(spec);
	
	int cnt = result.size();
	ReferenceFactory rf = new ReferenceFactory();
	while(result.hasMoreElements()){
		row++;
		
		lr = new Label(0, row, Integer.toString(cnt));
		s1.addCell(lr);
		
		String status = "";
    	String type = "";
  		String creator = "";
  		String title = "";
  		String taskName ="";
  		
		Object[] obj = (Object[])result.nextElement();
		WorkItem item = (WorkItem)obj[0];
		Persistable pbo = item.getPrimaryBusinessObject().getObject();
		title = ClassifyPBOUtil.extractPBOInfo(pbo).get("title").toString();
  		creator = ClassifyPBOUtil.extractPBOInfo(pbo).get("creator").toString();
  		type = ClassifyPBOUtil.extractPBOInfo(pbo).get("type").toString();
  		status = ClassifyPBOUtil.extractPBOInfo(pbo).get("state").toString();
  		WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
  		if(activity!=null)	{taskName = activity.getName();}
  		else {
  			taskName = item.getRole().getDisplay();
  		}
  		
		lr = new Label(1, row, type);
		s1.addCell(lr);
		
		lr = new Label(2, row, taskName);
		s1.addCell(lr);
		
		lr = new Label(3, row, title);
		s1.addCell(lr);
		
		lr = new Label(4, row, status);
		s1.addCell(lr);
		
		lr = new Label(5, row, creator);
		s1.addCell(lr);
		
		lr = new Label(6, row, DateUtil.getDateString(item.getPersistInfo().getCreateStamp(), "d" ));
		s1.addCell(lr);
		cnt--;
		
	}
	
	writebook.write();
	writebook.close();
	
	objSos.close();
%>
