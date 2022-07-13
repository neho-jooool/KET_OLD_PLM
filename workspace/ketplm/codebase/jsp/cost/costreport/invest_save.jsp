<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<%@ page import="e3ps.cost.control.CostInvestCtl"%>
<%
	request.setCharacterEncoding("euc-kr");
	String firstItem = request.getParameter("frItem")!=null?request.getParameter("frItem"):"";
	String seconItem = request.getParameter("seItem")!=null?request.getParameter("seItem"):"";
	String thirdItem = request.getParameter("thItem")!=null?request.getParameter("thItem"):"";
	String fourItem = request.getParameter("foItem")!=null?request.getParameter("foItem"):"";
	String fiveItem = request.getParameter("fiItem")!=null?request.getParameter("fiItem"):"";
	String productItem = request.getParameter("productItem")!=null?request.getParameter("productItem"):"";
	String report_pk = request.getParameter("report_pk")!=null?request.getParameter("report_pk"):"";
	String modify = request.getParameter("modify")!=null?request.getParameter("modify"):"";
	int complet = 0;
	String[] firstTempArray = firstItem.split("@");
	String[] seconTempArray = seconItem.split("@");
	String[] thirdTempArray = thirdItem.split("@");
	String[] fourTempArray = fourItem.split("@");
	String[] fiveTempArray = fiveItem.split("@");
	int itemLength = 0;
	String temp = "";
	String gubun = "";
	String tempReplace = "";
	CostInvestCtl investCtl = new CostInvestCtl();

	if("M".equals(modify)){
		int delete_ok = investCtl.deleteInvest(report_pk);
	}

	for(int i=0;i<5;i++){
		if(i==0){
			itemLength = firstTempArray.length;
		}else if(i==1){
			itemLength = seconTempArray.length;
		}else if(i==2){
			itemLength = thirdTempArray.length;
		}else if(i==3){
			itemLength = fourTempArray.length;
		}else if(i==4){
			itemLength = fiveTempArray.length;
		}
		for(int f=0;f<itemLength;f++){
			if(i==0){
				temp = firstTempArray[f];
				gubun = "first";
			}else if(i==1){
				temp = seconTempArray[f];
				gubun = "second";
			}else if(i==2){
				temp = thirdTempArray[f];
				gubun = "third";
			}else if(i==3){
				temp = fourTempArray[f];
				gubun = "four";
			}else if(i==4){
				temp = fiveTempArray[f];
				gubun = "five";
			}

			if(!"".equals(productItem)){
				tempReplace = temp.replaceAll("\\|", "").replace(productItem, "").replaceAll("\\^", "");
			}else{
				tempReplace = temp.replaceAll("\\|", "").replaceAll("\\^", "");
			}
			if(!"".equals(tempReplace)){
				String [] tempArray = temp.split("\\|", 8);
				ArrayList<Hashtable<String, String>> insertItemList = new ArrayList<Hashtable<String, String>>();
				Hashtable<String, String> insertMap = null;
				insertMap = new Hashtable<String, String>();
				insertMap.put("report_pk", report_pk);
				insertMap.put("gubun", gubun);
				insertMap.put("invest_order", Integer.toString(f));
				insertMap.put("part_name",tempArray[0]);
				insertMap.put("to_cost",tempArray[1]);
				insertMap.put("to_su",tempArray[2]);
				insertMap.put("cost_sum",tempArray[3]);
				insertMap.put("to_type",tempArray[4]);
//				if(f==0){
					insertMap.put("to_note",tempArray[5]);
					if(!"".equals(productItem)){
						insertMap.put("pro_cost_name",tempArray[6]);
						insertMap.put("pro_cost",tempArray[7]);
					}
//				}else{
//					if(!"".equals(productItem)){
//						insertMap.put("pro_cost_name",tempArray[5]);
//						insertMap.put("pro_cost",tempArray[6]);
//						insertMap.put("note","");
//					}
//				}
				insertItemList.add(insertMap);
				complet = investCtl.costInsertItem(insertItemList);
				insertItemList.clear();
				/*if(complet == 0){
					out.println("<script type='text/javascript'>alert('등록에 실패하였습니다.');</script>");
					break;
				}*/
			}
		}
	}

%>
<html>
<head>
<script>
function call(){
	<%
		if(complet == 0){
	%>
		alert('등록에 실패하였습니다.');
		history.back(-1);
	<%}else{%>
		alert('등록 하였습니다');
		var url = "http://plm.ket.com/plm/jsp/cost/costreport/cost_invest_edit.jsp?report_pk="+'<%=report_pk%>';
		location.replace(url);
	<%}%>
}
</script>
</head>
<body onload="call();">
</body>
</html>