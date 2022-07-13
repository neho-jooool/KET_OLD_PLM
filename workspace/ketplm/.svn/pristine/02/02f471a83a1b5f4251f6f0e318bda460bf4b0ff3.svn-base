<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>

<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.write.WritableSheet"%>
<%@page import="jxl.write.Label"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper "  %>
<%@include file="/jsp/common/context.jsp" %>
<%
  String command = StringUtil.trimToEmpty(request.getParameter("command"));
  String number = StringUtil.trimToEmpty(request.getParameter("number"));
  String name = StringUtil.trimToEmpty(request.getParameter("name"));
  String state = StringUtil.trimToEmpty(request.getParameter("state"));
  String latest = StringUtil.trimToEmpty(request.getParameter("latest"));

  String businessType = StringUtil.trimToEmpty(request.getParameter("businessType"));
  String devStage = StringUtil.trimToEmpty(request.getParameter("devStage"));
  String category = StringUtil.trimToEmpty(request.getParameter("category"));
  String cadAppType = StringUtil.trimToEmpty(request.getParameter("cadAppType"));
  String isDummyFile = StringUtil.trimToEmpty(request.getParameter("isDummyFile"));


  String createStart = StringUtil.trimToEmpty(request.getParameter("create_start"));
  String createEnd = StringUtil.trimToEmpty(request.getParameter("create_end"));
  String modifyStart = StringUtil.trimToEmpty(request.getParameter("modify_start"));
  String modifyEnd = StringUtil.trimToEmpty(request.getParameter("modify_end"));

  String creator = StringUtil.trimToEmpty(request.getParameter("creator"));
  String modifier = StringUtil.trimToEmpty(request.getParameter("modifier"));

  String edmCreateDeptName = StringUtil.trimToEmpty(request.getParameter("edmCreateDeptName"));

  HashMap map = new HashMap();
  map.put("number",number);
  map.put("name",name);
  map.put("state",state);
  map.put("latest",latest);

  map.put("create_start",createStart);
  map.put("create_end",createEnd);
  map.put("modify_start",modifyStart);
  map.put("modify_end",modifyEnd);

  map.put("creator",creator);
  map.put("modifier",modifier);

  map.put("edmCreateDeptName",edmCreateDeptName);

  map.put("className",wt.epm.EPMDocument.class.getName());

  //IBA 값 처리
  if(devStage.length() > 0) {
    map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
  }
  if(category.length() > 0) {
    map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
  }
  if(cadAppType.length() > 0) {
    map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
  }
  if(isDummyFile.length() > 0) {
    map.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
  }

  Vector sorts = new Vector();
  Object[] so = new Object[3];
  so[0] = "number";
  so[1] = wt.epm.EPMDocumentMaster.class;
  so[2] = new Boolean(false);
  sorts.add(so);
  map.put("sort",sorts);

%>
<%
  java.util.Date date = new java.util.Date();
  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA );
  String fileName = "EDMList_" + formatter.format(date);

  response.setContentType("application/vnd.ms-excel;charset=euc-kr");

  String strClient = request.getHeader("user-agent");
  if( strClient.indexOf("MSIE 5.5") != -1 ) {
    // explorer 5.5
    response.setHeader("Content-Disposition", "filename="+ fileName + ".xls");

  } else {
    response.setHeader("Content-Disposition", "attachment;filename="+ fileName + ".xls");
  }
  response.setHeader("Content-Description", "JSP Generated Data");

  response.setHeader("Content-Transfer-Encoding", "binary;");
  response.setHeader("Pragma", "no-cache;");
  response.setHeader("Expires", "-1;");


  EDMExcelModel em = new EDMExcelModel(map, request);
  WritableWorkbook workbook = null;
  try {
    workbook = em.getWorkbook(response);
    workbook.write();
  }
  catch (IOException e) {}
  catch (Exception e) {}
  finally {
    try {
      if (workbook != null) { workbook.close(); }
    }
    catch (IOException ie) { workbook = null; }
  }
%>
