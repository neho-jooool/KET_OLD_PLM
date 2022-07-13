<%@page import="ext.ket.part.util.PartSpecEnum"%>
<%@page import="ext.ket.part.util.PartSpecGetter"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.part.WTPart"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page import="e3ps.sap.EcoBudgetInterface
                            ,e3ps.common.util.StringUtil
                            ,java.util.Hashtable
                            ,java.lang.Integer" %>
                            
                            
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                            
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>                            
<%
    String devYn = request.getParameter("devYn");
    String division = request.getParameter("division");
    String dieNo = request.getParameter("dieNo");
    System.out.println(" $$$$$$$$$$$$$$$ dieNo ::: " + dieNo);
    String cost = request.getParameter("cost");
    String rowInx = request.getParameter("rowInx");
    String partOid = request.getParameter("partOid");

    String tableId = StringUtil.checkNull(request.getParameter("tableId"));
    
    
    String budgetFlag = "N";
    String msg = "";
    
    // 유효성 검사
    boolean isPass = true;
    // 단계구분
    if(devYn == null || devYn.equals("")) {
	    msg += "단계구분을 선택하십시오.\\n";
	    isPass = false;
    } else {
	   
	  if(devYn.equalsIgnoreCase("TCAR") || devYn.equalsIgnoreCase("PROTO") || devYn.equalsIgnoreCase("PILOT")  || devYn.equalsIgnoreCase("D")) {
	      devYn = "1";
	  } else if(devYn.equalsIgnoreCase("PRODUCTION") || devYn.equalsIgnoreCase("P")) {
	      devYn = "2";
	  } else if(devYn.equalsIgnoreCase("1") || devYn.equalsIgnoreCase("2")) {
	      
	      // Do nothing..!!
	      //devYn = devYn;
	      
	  } else {
	       msg += "ERROR : 허용되지 않는 단계구분["+ devYn +"]코드입니다.\\n관리자에 문의하십시오.\\n";
	       isPass = false;
	      
	  }
	
    }
    
    WTPart part = (WTPart)CommonUtil.getObject(partOid);
    if(part != null){
	   devYn = "PC004".equals(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartDevLevel)) ? "2" : "1";
    }
    
    // 양산일 경우 무조건 확보이다.
    if(devYn.equalsIgnoreCase("2")) {
	    budgetFlag = "Y";
	    
    } 
    // 사업구가 'KETS'일 경우 무조건 확보이다.
    else if(division.equalsIgnoreCase("3") || division.equalsIgnoreCase("K")) {
	    budgetFlag = "Y";
	        
	} else {
	    
	    
	    // 사업부구분
	    if(isPass) {
		    if(division == null || division.equals("")) {
		        msg += "사업부구분이 없는 경우\\nERP에서 \\\'비용확보\\\'를 확인할 수 없습니다.\\n";
		        isPass = false;
		    } else {
		       
		      if(division.equalsIgnoreCase("C")) {
			      division = "1";
		      } else if(division.equalsIgnoreCase("K")) {
	              division = "3";
	          } else {
			      division = "2";
		      } 
		    
		    }
	    }
	    
	    // Die No
	    if(isPass) {
		    if(devYn.equalsIgnoreCase("1") && (dieNo == null || dieNo.equals(""))) {
		        msg += "Die No가 없는 경우\\nERP에서 \\\'비용확보\\\'를 확인할 수 없습니다.\\n";
		        isPass = false;
		    } 
	    }
	    
	    // 비용확보
	    int budget = 0;
	    if(isPass) {
	        if(cost == null || cost.equals("")) {
	            msg += "예상비용(천원)이 없는 경우\\nERP에서 \\\'비용확보\\\'를 확인할 수 없습니다.\\n";
	            isPass = false;
	        } else {
	            
	            try {
	
	        	Kogger.debug("HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
	        	Kogger.debug("cost is "+ cost);
	        	Kogger.debug("HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
	        	
	        	    if(!Pattern.matches("^[0-9,]*$", cost)) {
	        		    msg += "예상비용(천원)은 숫자만 입력 가능합니다.\\n";
	                    isPass = false;
	        	    } else {
	        		    cost = cost.replaceAll(",", "");
	        			   		    
	        	    }
	        	    
	        	    if(isPass) {
		        	    budget = Integer.parseInt( cost+"000" );        	
	
	        	    }
	        	    
	            } catch(NumberFormatException nfe) {
	        	    msg += "예상비용(천원)이 허용치(-2147483 ~ 2147483)를 벗어난 경우\\nERP에서 \\\'비용확보\\\'를 확인할 수 없습니다.\\n";
	                isPass = false;
	            }
	                        
	        }
	    }    
	    
	    if(isPass) {
		    EcoBudgetInterface sapIf = new EcoBudgetInterface();
		    Kogger.debug( devYn +"+"+division +"+"+ dieNo +"+"+budget );
		    Hashtable<String, String> budgetHash = sapIf.getEcoBudget( devYn, division, dieNo, budget );
		
		    if( budgetHash != null )
		    {
			    if(budgetHash.get("success_yn") != null) {
			        if( budgetHash.get("success_yn").equalsIgnoreCase("S") )
			        {
			            budgetFlag = budgetHash.get("budget_exist_yn");
			
			            if( StringUtil.checkNull(budgetHash.get("msg")).indexOf("투자오더") > -1 )
			            {
			                budgetFlag = "Y";
			                //msg = budgetHash.get("msg");
			            }
			        }
			        else
			        {
			             budgetFlag = "N";
			             msg = budgetHash.get("msg");
			
			             if( msg.indexOf("No data found") > -1 )
			             {
			                 budgetFlag = "Y";
			                 msg = "";
			             }
			        }
			        Kogger.debug("msg =>" + budgetHash.get("msg"));
			        Kogger.debug("success_yn =>" + budgetHash.get("success_yn"));
			        Kogger.debug("budgetFlag =>" + budgetHash.get("budget_exist_yn"));
			        Kogger.debug("remain_budget ==>" + budgetHash.get("remain_budget"));
		
			    } else {
					budgetFlag = "N";
					// <entry key="04490">ERP I/F에서 에러가 발생하였습니다.</entry>
					msg = messageService.getString("e3ps.message.ket_message", "04490");
					
			    }
			    if(!"Y".equals(budgetFlag)){
			    	if( StringUtil.checkNull(budgetHash.get("msg")).indexOf("초과") > -1 ){
			    		msg += "예산잔액이 초과되었습니다.";
			    	}else{
			    		msg += budgetHash.get("msg");
			    	}
			    }
		    }
		    
	    }
	    
    }
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script language="javascript">
    parent.setBudgetYn<%=tableId %>("<%=budgetFlag%>", "<%=rowInx%>", "<%=msg%>");

</script>
<html>
<body>
<form>
<input type="hidden"  name="devYn" value="<%=devYn %>">
<input type="hidden"  name="division" value="<%=division %>">
<input type="hidden"  name="dieNo" value="<%=dieNo %>">
<input type="hidden"  name="cost" value="<%=cost %>">
<input type="hidden"  name="rowInx" value="<%=rowInx %>">
<input type="hidden"  name="tableId" value="<%=tableId %>">
</form>
</body>
</html>
