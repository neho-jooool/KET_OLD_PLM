<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" ext.ket.part.base.service.internal.associate.PartEpmRelation"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.KETBOMQueryBean"%>
<%@ page import="ext.ket.bom.service.*"%>
<%@ page import="ext.ket.part.classify.service.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%

  PrintWriter pw;
  KETBOMQueryBean bean = new KETBOMQueryBean();
  
  pw = response.getWriter();
  
  
  String partNumber = "";
  String boxQty     = "";
  String ecoNumber      = (String)request.getParameter("ecoNumber");
  String gubun          = (String)request.getParameter("gubun"); 
  String partType       = (String)request.getParameter("partType"); 
  
  String topPartNo      = (String)request.getParameter("topPartNo");
  String topRev      = (String)request.getParameter("topRev");
  
    
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
  
  
  List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
  Hashtable saveKeyHash = new Hashtable();
  
  
  
  if(index!=null)
  {
      for(int i=0; i<index.length;i++)
      {
          
	      Map<String, Object> data = new Hashtable();
	  
	      System.out.println("qty===========>"+qty[i]);
	      
	      if(qty[i].indexOf(".")==-1)
	      {
		    qty[i]+=".000";
	      }else
	      {
		    String qty_tmp = qty[i].substring(qty[i].indexOf(".")+1);
		    System.out.println("qty_tmp----------->"+qty_tmp);
		    
		    if(qty_tmp.length()==0)
	        {
	                qty[i]+="000";
	        }else if(qty_tmp.length()==1)
            {
                    qty[i]+="00";
            }else if(qty_tmp.length()==2)
            {
                qty[i]+="0";
            }else if(qty_tmp.length()>2)
            {
                qty[i] = qty[i].substring(0, qty[i].indexOf(".")+4);
            }
	      }
	      
	      data.put("partNo",partNo[i]);
          data.put("index",index[i]);
          data.put("lvl",lvl[i]);
          data.put("ict",ict[i]);
          if(partType.equals("D")||partType.equals("M"))
          {
              data.put("seq","10");
              data.put("hardnessFrom",hardnessFrom[i]);
              data.put("hardnessTo",hardnessTo[i]);
              
              String designDT = "";
              if(designDate[i].length()>10)
        	  designDT = designDate[i].substring(0,10); 
              data.put("designDate",designDT);
              
              System.out.println("designDate["+i+"]==>"+designDT);
          }else
          {
              data.put("seq",seq[i]);
              data.put("hardnessFrom","");
              data.put("hardnessTo","");
              data.put("designDate","");
          }
          data.put("seq",seq[i]);
          data.put("partName",partName[i]);
          data.put("qty",qty[i]);
          data.put("unit",unit[i]);
          data.put("rev",rev[i]);
          data.put("state",state[i]);
          data.put("econo",econo[i]);
          data.put("checkout",checkout[i]);
          data.put("reftop",reftop[i]);
          data.put("refbtm",refbtm[i]);
          data.put("material",material[i]);
         
         
          data.put("parentNo",parentNo[i]);
          data.put("pver",pver[i]);
          
          String key = parentNo[i]+"^"+partNo[i];
          if(!saveKeyHash.containsKey(key))
          {
              saveList.add(data);
          }
          
          
          
          if(lvl[i].equals("0"))
          {
              partNumber = partNo[i];
              boxQty = qty[i];
          }
          
          
          saveKeyHash.put(key, "");
      }
  }
  
  /************************************************************************/
  //재질 중량 업데이트 시작
  /*
  KETBomPartUtil util = new KETBomPartUtil();
  List<Map<String, Object>> materialList = new ArrayList<Map<String, Object>>();
  Hashtable params2 = new Hashtable();
  ArrayList arr = new ArrayList();
  
  String topPartOid = bean.getPartOid(topPartNo, topRev);
  WTPart topPart = util.getPart(topPartOid);
  
  
  params2.put("partNo", partNo);
  params2.put("index", index);
  params2.put("lvl", lvl);
  params2.put("ict", ict);
  params2.put("seq", seq);
  params2.put("partName", partName);
  params2.put("qty", qty);
  params2.put("unit", unit);
  params2.put("rev", rev);
  params2.put("state", state);
  params2.put("econo", econo);
  params2.put("checkout", checkout);
  params2.put("reftop", reftop);
  params2.put("refbtm", refbtm);
  params2.put("material", material);
  params2.put("hardnessFrom", hardnessFrom);
  params2.put("hardnessTo", hardnessTo);
  params2.put("designDate", designDate);
  params2.put("parentNo", parentNo);
  params2.put("pver", pver);
  
  materialList = util.getBomWeightMaterial(params2);
  System.out.println("###########################################################################");
  System.out.println("###########################################################################");
  
  if(materialList!=null)
  {
	  for(int x=0; x < materialList.size();x++)
	  {
	      Hashtable mdata = (Hashtable)materialList.get(x);
	      
	      String mPartNo        = (String)mdata.get("partNo");
	      String mPartOid       = (String)mdata.get("partOid");
	      String oldPWeight     = (String)mdata.get("old_weight");
	      String oldSWeight     = (String)mdata.get("old_sweight");
	      String oldTWeight     = (String)mdata.get("old_tweight");
	      String oldMaterial    = (String)mdata.get("old_material");
	      String newPWeight     = (String)mdata.get("new_weight");
	      String newSWeight     = (String)mdata.get("new_sweight");
	      String newTWeight     = (String)mdata.get("new_tweight");
	      String newMaterial    = (String)mdata.get("new_material");
	      
	      System.out.println("partNo["+x+"]==>"+mPartNo);
	      System.out.println("partOid["+x+"]==>"+mPartOid);
	      System.out.println("oldPWeight["+x+"]==>"+oldPWeight);
	      System.out.println("oldSWeight["+x+"]==>"+oldSWeight);
	      System.out.println("oldTWeight["+x+"]==>"+oldTWeight);
	      System.out.println("oldMaterial["+x+"]==>"+oldMaterial);
	      System.out.println("newPWeight["+x+"]==>"+newPWeight);
	      System.out.println("newSWeight["+x+"]==>"+newSWeight);
	      System.out.println("newTWeight["+x+"]==>"+newTWeight);
	      System.out.println("newMaterial["+x+"]==>"+newMaterial);
	      
	      
	      if(topPartNo.equals(mPartNo.substring(1)))
	       {
	           //topPart
	           PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpTotalWeight, newTWeight);
	           PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpScrabWeight, newSWeight);
	           PartSpecSetter.setPartSpec(topPart, PartSpecEnum.SpNetWeight, newPWeight);
	           List list = PartClazHelper.service.getMaterialPartSpecEnum(topPart);
	           if(list!=null && list.size()>0)
	           {
	               PartSpecEnum specenum = (PartSpecEnum) list.get(0);
	               PartSpecSetter.setPartSpec(topPart, specenum, newMaterial);
	           }
	           
	           arr.add(topPart);
	       }
	       
	       WTPart part  = util.getPart(mPartOid);
	       PartSpecSetter.setPartSpec(part, PartSpecEnum.SpTotalWeight, newTWeight);
	       PartSpecSetter.setPartSpec(part, PartSpecEnum.SpScrabWeight, newSWeight);
	       PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, newPWeight);
	       
	       List list2 = PartClazHelper.service.getMaterialPartSpecEnum(part);
	       
	       if(list2!=null && list2.size()>0)
	       {
	           PartSpecEnum specenum2 = (PartSpecEnum) list2.get(0);
	           PartSpecSetter.setPartSpec(part, specenum2, newMaterial);
	       }
	       
	       System.out.println("getPartSpec==>"+PartSpecGetter.getPartSpec(part, PartSpecEnum.SpTotalWeight));
	       System.out.println("------------------------------------------------------------------");  
	       
	       arr.add(part);
	  }
  }
  //System.out.println("materialList=================>"+materialList.toString());
  System.out.println("###########################################################################");
  System.out.println("###########################################################################");
  
  */
  //재질 중량 업데이트 종료
  /************************************************************************/
  
  
  
  Hashtable params = new Hashtable();
  
  params.put("gubun", gubun);
  params.put("ecoNumber", ecoNumber);
  params.put("partType",partType);
  params.put("partNumber",partNumber);
  params.put("boxQty", boxQty);
  params.put("dataType", "Data");
  params.put("inDelete", "Y");
  
  String result = "";
  
  
  
  result = bean.saveBom(params, saveList, saveKeyHash);
  /************************************************************************/
  //재질 중량 업데이트 시작
  //if(!(partType.equals("D")|| partType.equals("M"))
  //result += KETBom2Helper.service.savePart(arr);
  //재질 중량 업데이트 종료
  /************************************************************************/
  
  
pw.print(result);
pw.flush();
pw.close();
%>