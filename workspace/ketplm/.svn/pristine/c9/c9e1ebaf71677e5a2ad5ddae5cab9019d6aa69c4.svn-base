<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="ext.ket.bom.util.KETExcelUtil" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%

//String clientBrowser = (String)request.getHeader("User-Agent");


//String titleName = "한글제목"; 

//titleName = new String(titleName.getBytes("KSC5601"), "8859_1"); 



PrintWriter pw;
KETExcelUtil util = new KETExcelUtil();
/*
response.setHeader("Content-Type", "application/vnd.ms-excel;charset=EUC-KR");
response.setHeader("Content-Disposition", "attachment; filename="+titleName+".xls");

response.setHeader("Content-Transfer-Encoding", "binary;");
response.setHeader("Pragma", "no-cache;");
response.setHeader("Expires", "-1;"); 
*/

pw = response.getWriter();

//pw.print("<html><body><table>");



  String partType       = (String)request.getParameter("partType"); 

  String[] srcpartNo       = (String[])request.getParameterValues("srcpartNo");
  String[] srclvl          = (String[])request.getParameterValues("srclvl");
  String[] srcpartName     = (String[])request.getParameterValues("srcpartName");
  String[] srcqty          = (String[])request.getParameterValues("srcqty");
  String[] srcunit         = (String[])request.getParameterValues("srcunit");
  String[] srcrev          = (String[])request.getParameterValues("srcrev");
  String[] srcreftop       = (String[])request.getParameterValues("srcreftop");
  String[] srcrefbtm       = (String[])request.getParameterValues("srcrefbtm");
  String[] srcmaterial     = (String[])request.getParameterValues("srcmaterial");
  String[] srchardnessFrom = (String[])request.getParameterValues("srchardnessFrom");
  String[] srchardnessTo   = (String[])request.getParameterValues("srchardnessTo");
  String[] srcdesignDate   = (String[])request.getParameterValues("srcdesignDate");
  String[] srcparentNo     = (String[])request.getParameterValues("srcparentNo");
  //String[] srcpver         = (String[])request.getParameterValues("srcpver");
  
  String[] despartNo       = (String[])request.getParameterValues("despartNo");
  String[] deslvl          = (String[])request.getParameterValues("deslvl");
  String[] despartName     = (String[])request.getParameterValues("despartName");
  String[] desqty          = (String[])request.getParameterValues("desqty");
  String[] desunit         = (String[])request.getParameterValues("desunit");
  String[] desrev          = (String[])request.getParameterValues("desrev");
  String[] desreftop       = (String[])request.getParameterValues("desreftop");
  String[] desrefbtm       = (String[])request.getParameterValues("desrefbtm");
  String[] desmaterial     = (String[])request.getParameterValues("desmaterial");
  String[] deshardnessFrom = (String[])request.getParameterValues("deshardnessFrom");
  String[] deshardnessTo   = (String[])request.getParameterValues("deshardnessTo");
  String[] desdesignDate   = (String[])request.getParameterValues("desdesignDate");
  String[] desparentNo     = (String[])request.getParameterValues("desparentNo");
  //String[] despver         = (String[])request.getParameterValues("despver");
  
  ArrayList excelList = new ArrayList();
  ArrayList keyList = new ArrayList();
  
  keyList.add(new String("srcpartNo"));
  keyList.add(new String("srclvl"));
  keyList.add(new String("srcpartName"));
  keyList.add(new String("srcqty"));
  keyList.add(new String("srcunit"));
  keyList.add(new String("srcrev"));
  keyList.add(new String("srcreftop"));
  keyList.add(new String("srcrefbtm"));
  keyList.add(new String("srcmaterial"));
  keyList.add(new String("srchardnessFrom"));
  keyList.add(new String("srchardnessTo"));
  keyList.add(new String("srcdesignDate"));
  keyList.add(new String("srcparentNo"));
  //keyList.add(new String("srcpver"));
  
  keyList.add(new String("despartNo"));
  keyList.add(new String("deslvl"));
  keyList.add(new String("despartName"));
  keyList.add(new String("desqty"));
  keyList.add(new String("desunit"));
  keyList.add(new String("desrev"));
  keyList.add(new String("desreftop"));
  keyList.add(new String("desrefbtm"));
  keyList.add(new String("desmaterial"));
  keyList.add(new String("deshardnessFrom"));
  keyList.add(new String("deshardnessTo"));
  keyList.add(new String("desdesignDate"));
  keyList.add(new String("desparentNo"));
  //keyList.add(new String("despver"));
  
  
  Hashtable hdata = new Hashtable();
  
  hdata.put("srcpartNo",messageService.getString("e3ps.message.ket_message", "01569"));//부품번호
  hdata.put("srclvl",messageService.getString("e3ps.message.ket_message", "01346"));//레벨
  hdata.put("srcpartName",messageService.getString("e3ps.message.ket_message", "01586"));//부품명
  hdata.put("srcqty",messageService.getString("e3ps.message.ket_message", "01925"));//수량
  hdata.put("srcunit",messageService.getString("e3ps.message.ket_message", "01119"));//기본단위
  hdata.put("srcrev",messageService.getString("e3ps.message.ket_message", "01481"));//버전
  hdata.put("srcreftop","Ref No (TOP)");
  hdata.put("srcrefbtm","Ref No (BOTTOM)");
  hdata.put("srcmaterial",messageService.getString("e3ps.message.ket_message", "02452"));//재질
  hdata.put("srchardnessFrom",messageService.getString("e3ps.message.ket_message", "00792")+"(From)");//경도
  hdata.put("srchardnessTo",messageService.getString("e3ps.message.ket_message", "00792")+"(To)");//경도
  hdata.put("srcdesignDate",messageService.getString("e3ps.message.ket_message", "01865"));//설계일자
  hdata.put("srcparentNo",messageService.getString("e3ps.message.ket_message", "09377"));//모부품
  //hdata.put("srcpver","모버전");
  
  hdata.put("despartNo",messageService.getString("e3ps.message.ket_message", "01569"));//부품번호
  hdata.put("deslvl",messageService.getString("e3ps.message.ket_message", "01346"));//레벨
  hdata.put("despartName",messageService.getString("e3ps.message.ket_message", "01586"));//부품명
  hdata.put("desqty",messageService.getString("e3ps.message.ket_message", "01925"));//수량
  hdata.put("desunit",messageService.getString("e3ps.message.ket_message", "01119"));//기본단위
  hdata.put("desrev",messageService.getString("e3ps.message.ket_message", "01481"));//버전
  hdata.put("desreftop","Ref No (TOP)");
  hdata.put("desrefbtm","Ref No (BOTTOM)");
  hdata.put("desmaterial",messageService.getString("e3ps.message.ket_message", "02452"));//재질
  hdata.put("deshardnessFrom",messageService.getString("e3ps.message.ket_message", "00792")+"(From)");//경도
  hdata.put("deshardnessTo",messageService.getString("e3ps.message.ket_message", "00792")+"(To)");//경도
  hdata.put("desdesignDate",messageService.getString("e3ps.message.ket_message", "01865"));//설계일자
  hdata.put("desparentNo",messageService.getString("e3ps.message.ket_message", "09377"));//모부품
  //hdata.put("despver","모버전");
  
  excelList.add(hdata);
  
  if(srcpartNo!=null)
  {
      for(int i=0; i<srcpartNo.length;i++)
      {
          
	      Hashtable data = new Hashtable();
	  
	      data.put("srcpartNo",srcpartNo[i]);
          data.put("srclvl",srclvl[i]);
          data.put("srcpartName",srcpartName[i]);
          data.put("srcqty",srcqty[i]);
          data.put("srcunit",srcunit[i]);
          data.put("srcrev",srcrev[i]);
          data.put("srcreftop",srcreftop[i]);
          data.put("srcrefbtm",srcrefbtm[i]);
          data.put("srcmaterial",srcmaterial[i]);
          data.put("srchardnessFrom",srchardnessFrom[i]);
          data.put("srchardnessTo",srchardnessTo[i]);
          data.put("srcdesignDate",srcdesignDate[i]);
          data.put("srcparentNo",srcparentNo[i]);
          //data.put("srcpver",srcpver[i]);
          
          data.put("despartNo",despartNo[i]);
          data.put("deslvl",deslvl[i]);
          data.put("despartName",despartName[i]);
          data.put("desqty",desqty[i]);
          data.put("desunit",desunit[i]);
          data.put("desrev",desrev[i]);
          data.put("desreftop",desreftop[i]);
          data.put("desrefbtm",desrefbtm[i]);
          data.put("desmaterial",desmaterial[i]);
          data.put("deshardnessFrom",deshardnessFrom[i]);
          data.put("deshardnessTo",deshardnessTo[i]);
          data.put("desdesignDate",desdesignDate[i]);
          data.put("desparentNo",desparentNo[i]);
          //data.put("despver",despver[i]);
          
          excelList.add(data);
          
      }
  }
  
  String result = util.createExcelFile(keyList, excelList, "BOMCompareList");
  
pw.print(result);
pw.flush();
pw.close();
%>