<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import=" ext.ket.bom.query.*"%>
<%@page import=" ext.ket.part.base.service.internal.associate.PartEpmRelation"%>
<%@page import=" java.util.*"%>
<%@page import=" java.io.*"%>
<%@page import=" org.json.*"%>
<%@ page import="wt.part.*"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%


String[] modelNo            = (String[])request.getParameterValues("modelNo");
String[] modelRev           = (String[])request.getParameterValues("modelRev");
String[] modelName          = (String[])request.getParameterValues("modelName");
String[] partNo             = (String[])request.getParameterValues("partNo");
String[] partRev            = (String[])request.getParameterValues("partRev");
String[] partName           = (String[])request.getParameterValues("partName");
String[] qty                = (String[])request.getParameterValues("qty");
String[] partCadRelation    = (String[])request.getParameterValues("partCadRelation");
String[] partBomRelation    = (String[])request.getParameterValues("partBomRelation");
String[] modelOid           = (String[])request.getParameterValues("modelOid");
String[] partOid            = (String[])request.getParameterValues("partOid");
String[] parentModelNo      = (String[])request.getParameterValues("parentModelNo");
String[] parentPartNo       = (String[])request.getParameterValues("parentPartNo");

String success = "OK";

System.out.println(modelNo.length);

//cad2bom [part-cad] relation object
PartEpmRelation partEpmRelation = new PartEpmRelation();

KETBOMQueryBean bean = new KETBOMQueryBean();
KETBomPartUtil pUtil = new KETBomPartUtil();

List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

String productNo = "";

Hashtable saveKeyHash = new Hashtable();

Hashtable lvlSeqH = new Hashtable();

int init_seq = 10; 
for(int i=0; i < modelNo.length; i++)
{
    System.out.println("------------------------------------------------------------------");   
      System.out.println("modelNo["+i+"]==>"+modelNo[i]);
      System.out.println("modelRev["+i+"]==>"+modelRev[i]);
      System.out.println("modelName["+i+"]==>"+modelName[i]);
      System.out.println("partNo["+i+"]==>"+partNo[i]);
      System.out.println("partRev["+i+"]==>"+partRev[i]);
      System.out.println("partName["+i+"]==>"+partName[i]);
      System.out.println("qty["+i+"]==>"+qty[i]);
      System.out.println("partCadRelation["+i+"]==>"+partCadRelation[i]);
      System.out.println("partBomRelation["+i+"]==>"+partBomRelation[i]);
      System.out.println("modelOid["+i+"]==>"+modelOid[i]);
      System.out.println("partOid["+i+"]==>"+partOid[i]);
      System.out.println("parentModelNo["+i+"]==>"+parentModelNo[i]);
      System.out.println("parentPartNo["+i+"]==>"+parentPartNo[i]);
      System.out.println("------------------------------------------------------------------");  
      /*
      int seq = 0;
      String key = parentPartNo[i]+"^"+partNo[i];
      if(!saveKeyHash.containsKey(key))
      {
          if(lvlSeqH.containsKey(parentPartNo[i]))
          {
              seq = (int)lvlSeqH.get(parentPartNo[i]);
              seq = seq+10;
          }else
          {
              seq = 10;
          }
          System.out.println(partNo[i]+"===>"+Integer.toString(seq));
          lvlSeqH.put(parentPartNo[i],seq);
      }
      */
      
      // cad2bom [part-cad] relation - execute
      partEpmRelation.associateCreate(partOid[i], modelOid[i]);
      
      if(i==0)
      {
	   if(!parentPartNo[i].equals(""))
	   {
	       productNo = parentPartNo[i];
	   }else
	   {
	       productNo = partNo[i];
	   }
      }
      
      
      if(partBomRelation[i].equals("NEW"))
      {
	       if(!parentPartNo[i].equals(""))
           {
		        Map<String, Object> data = new Hashtable();
		        
		        WTPart part = pUtil.getPart(partOid[i]);
		        WTPartMaster master =  (WTPartMaster)part.getMaster();
		        
		        
		        //String unitinfo = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpUnitUnit); 		   
		        String unitinfo = master.getDefaultUnit().toString();  
		        System.out.println("unitinfo============>"+unitinfo);
		        String key = parentPartNo[i]+"^"+partNo[i];
		       
		        
		        int seq = 0;
		        
		        if(!saveKeyHash.containsKey(key))
	            {
			        if(lvlSeqH.containsKey(parentPartNo[i]))
			        {
			            seq = (int)lvlSeqH.get(parentPartNo[i]);
			            seq = seq+10;
			        }else
			        {
			            seq = 10;
			        }
	            }
                
		        
		        
	            data.put("partNo",partNo[i]);
	            data.put("rev",partRev[i]);
	            data.put("partName",partName[i]);
	            data.put("lvl","");
	            data.put("seq",Integer.toString(seq));
	            data.put("qty",qty[i]);
	            data.put("unit",unitinfo);
	            data.put("ict","");
	            data.put("reftop","");
	            data.put("refbtm","");
	            data.put("material","");
	            data.put("hardnessFrom","");
	            data.put("hardnessTo","");
	            data.put("designDate","");
	            data.put("parentNo",parentPartNo[i]);
	            
	            
	            
	            if(!saveKeyHash.containsKey(key))
	            {
	                saveList.add(data);
	            }
	           
	            saveKeyHash.put(key, "");
	            
	            
	            lvlSeqH.put(parentPartNo[i],seq);
	            
           }
       
	   }
}

success = bean.insertBomData("", productNo, saveList);

%>

{
    "success": <%=success %>,
    "result": {
        "checkdata1": "",
        "checkdata2": "",
        "checkdata3": "",
        "checkdata4": "",
        "checkdata5": "",
        "checkdata6": "",
        "checkdata7": "",
        "errlog": ""
    }
}
