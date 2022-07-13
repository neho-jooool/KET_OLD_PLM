<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="ext.ket.bom.util.KETExcelUtil" %>
<%@ page import="ext.ket.bom.util.KETBomPartUtil" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%

//String clientBrowser = (String)request.getHeader("User-Agent");


//String titleName = "한글제목"; 

//titleName = new String(titleName.getBytes("KSC5601"), "8859_1"); 



PrintWriter pw;
KETExcelUtil util = new KETExcelUtil();
KETBomPartUtil pUtil = new KETBomPartUtil();
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
  System.out.println("partType===>"+partType);
  boolean isMold = false;
  
  if(partType.equals("D") || partType.equals("M"))
      isMold = true;

  String[] partNo       = (String[])request.getParameterValues("partNo");
  String[] index        = (String[])request.getParameterValues("index");
  String[] lvl          = (String[])request.getParameterValues("lvl");
  String[] ict          = (String[])request.getParameterValues("ict");
  String[] seq          = (String[])request.getParameterValues("seq");
  String[] partName     = (String[])request.getParameterValues("partName");
  String[] qty          = (String[])request.getParameterValues("qty");
  String[] unit         = (String[])request.getParameterValues("unit");
  String[] rev          = (String[])request.getParameterValues("rev");
  String[] state        = (String[])request.getParameterValues("state");
  String[] econo        = (String[])request.getParameterValues("econo");
  String[] checkout     = (String[])request.getParameterValues("checkout");
  String[] reftop       = (String[])request.getParameterValues("reftop");
  String[] refbtm       = (String[])request.getParameterValues("refbtm");
  String[] material     = (String[])request.getParameterValues("material");
  String[] hardnessFrom = (String[])request.getParameterValues("hardnessFrom");
  String[] hardnessTo   = (String[])request.getParameterValues("hardnessTo");
  String[] designDate   = (String[])request.getParameterValues("designDate");
  String[] parentNo     = (String[])request.getParameterValues("parentNo");
  String[] pver         = (String[])request.getParameterValues("pver");
  
  
  HashMap nCode = new HashMap();
  if(isMold)
      nCode = (HashMap)pUtil.getNumberCode("SPECMATERIALMOLD");
  
  ArrayList excelList = new ArrayList();
  ArrayList keyList = new ArrayList();
  
  keyList.add(new String("index"));
  keyList.add(new String("partNo"));
  keyList.add(new String("lvl"));
  if(!isMold)
  {
	  keyList.add(new String("ict"));
	  keyList.add(new String("seq"));
  }
  keyList.add(new String("partName"));
  keyList.add(new String("qty"));
  keyList.add(new String("unit"));
  keyList.add(new String("rev"));
  keyList.add(new String("state"));
  keyList.add(new String("econo"));
  keyList.add(new String("checkout"));  
  if(!isMold)
  {
	  keyList.add(new String("reftop"));
	  keyList.add(new String("refbtm"));
  }else
  {
	  keyList.add(new String("material"));
	  keyList.add(new String("hardnessFrom"));
	  keyList.add(new String("hardnessTo"));
	  keyList.add(new String("designDate"));
  }
  //keyList.add(new String("partNo"));
  keyList.add(new String("parentNo"));
  //keyList.add(new String("pver"));
  
  
  Hashtable hdata = new Hashtable();
  
  hdata.put("index","No");
  hdata.put("partNo",messageService.getString("e3ps.message.ket_message", "01569"));//부품번호
  hdata.put("lvl",messageService.getString("e3ps.message.ket_message", "01346"));//레벨
  if(!isMold)
  {
	  hdata.put("ict","Ict");
	  hdata.put("seq","Item Seq");
  }
  hdata.put("partName",messageService.getString("e3ps.message.ket_message", "01586"));//부품명
  hdata.put("qty",messageService.getString("e3ps.message.ket_message", "01925"));//수량
  hdata.put("unit",messageService.getString("e3ps.message.ket_message", "01119"));//기본단위
  hdata.put("rev",messageService.getString("e3ps.message.ket_message", "01481"));//버전
  hdata.put("state",messageService.getString("e3ps.message.ket_message", "01760"));//상태
  hdata.put("econo","ECO No");
  hdata.put("checkout",messageService.getString("e3ps.message.ket_message", "04104"));//담당자
  if(!isMold)
  {
	  hdata.put("reftop","Ref No (TOP)");
	  hdata.put("refbtm","Ref No (BOTTOM)");
  }else
  {
	  hdata.put("material",messageService.getString("e3ps.message.ket_message", "02452"));//재질
	  hdata.put("hardnessFrom",messageService.getString("e3ps.message.ket_message", "00792")+"(From)");//경도
	  hdata.put("hardnessTo",messageService.getString("e3ps.message.ket_message", "00792")+"(To)");//경도
	  hdata.put("designDate",messageService.getString("e3ps.message.ket_message", "01865"));//설계일자
  }
  hdata.put("parentNo",messageService.getString("e3ps.message.ket_message", "09377"));//모부품
  //hdata.put("pver","모부품 버전");
  /*
  pw.print("<tr>");
  pw.print("<td>"+hdata.get("partNo")+"</td>");
  pw.print("<td>"+hdata.get("index")+"</td>");
  pw.print("<td>"+hdata.get("lvl")+"</td>");
  pw.print("<td>"+hdata.get("seq")+"</td>");
  pw.print("<td>"+hdata.get("partName")+"</td>");
  pw.print("<td>"+hdata.get("qty")+"</td>");
  pw.print("<td>"+hdata.get("unit")+"</td>");
  pw.print("<td>"+hdata.get("rev")+"</td>");
  pw.print("<td>"+hdata.get("state")+"</td>");
  pw.print("<td>"+hdata.get("econo")+"</td>");
  pw.print("<td>"+hdata.get("checkout")+"</td>");
  pw.print("<td>"+hdata.get("reftop")+"</td>");
  pw.print("<td>"+hdata.get("refbtm")+"</td>");
  pw.print("<td>"+hdata.get("material")+"</td>");
  pw.print("<td>"+hdata.get("hardnessFrom")+"</td>");
  pw.print("<td>"+hdata.get("hardnessTo")+"</td>");
  pw.print("<td>"+hdata.get("designDate")+"</td>");
  pw.print("<td>"+hdata.get("parentNo")+"</td>");
  pw.print("<td>"+hdata.get("pver")+"</td>");
  pw.print("</tr>\r\n");
  */
  
  excelList.add(hdata);
  
  if(index!=null)
  {
      for(int i=0; i<index.length;i++)
      {
          
	      Hashtable data = new Hashtable();
	      
	      int cnt = Integer.parseInt(index[i])+1;
	  
	      data.put("index",Integer.toString(cnt));
	      data.put("partNo",partNo[i]);
          data.put("lvl",lvl[i]);
          if(!isMold)
          {
	          data.put("ict",ict[i]);
	          data.put("seq",seq[i]);
          }
          data.put("partName",partName[i]);
          data.put("qty",qty[i]);
          data.put("unit",unit[i]);
          data.put("rev",rev[i]);
          data.put("state",state[i]);
          data.put("econo",econo[i]);
          data.put("checkout",checkout[i]);
          if(!isMold)
          {
	          data.put("reftop",reftop[i]);
	          data.put("refbtm",refbtm[i]);
          }else
          {
	          String materialDesc = "";
	          
	          if(!material[i].equals(""))
	          materialDesc = (String) nCode.get(material[i]);
	          //System.out.println(materialDesc);
              
              data.put("material", materialDesc);
	          data.put("hardnessFrom",hardnessFrom[i]);
	          data.put("hardnessTo",hardnessTo[i]);
	          data.put("designDate",designDate[i]);
          }
          data.put("parentNo",parentNo[i]);
          //data.put("pver",pver[i]);
          /*
          pw.print("<tr>");
          pw.print("<td>"+data.get("partNo")+"</td>");
          pw.print("<td>"+data.get("index")+"</td>");
          pw.print("<td>"+data.get("lvl")+"</td>");
          pw.print("<td>"+data.get("seq")+"</td>");
          pw.print("<td>"+data.get("partName")+"</td>");
          pw.print("<td>"+data.get("qty")+"</td>");
          pw.print("<td>"+data.get("unit")+"</td>");
          pw.print("<td>"+data.get("rev")+"</td>");
          pw.print("<td>"+data.get("state")+"</td>");
          pw.print("<td>"+data.get("econo")+"</td>");
          pw.print("<td>"+data.get("checkout")+"</td>");
          pw.print("<td>"+data.get("reftop")+"</td>");
          pw.print("<td>"+data.get("refbtm")+"</td>");
          pw.print("<td>"+data.get("material")+"</td>");
          pw.print("<td>"+data.get("hardnessFrom")+"</td>");
          pw.print("<td>"+data.get("hardnessTo")+"</td>");
          pw.print("<td>"+data.get("designDate")+"</td>");
          pw.print("<td>"+data.get("parentNo")+"</td>");
          pw.print("<td>"+data.get("pver")+"</td>");
          pw.print("</tr>");
          */
          
          excelList.add(data);
          
      }
  }
  
  String result = util.createExcelFile(keyList, excelList, "BOMList");
  
pw.print(result);
pw.flush();
pw.close();
%>