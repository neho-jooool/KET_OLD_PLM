<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.ArrayList,
                                    wt.query.QuerySpec,
                                    wt.query.SearchCondition,
                                    wt.query.ClassAttribute,
                                    wt.query.OrderBy,
                                    wt.fc.PersistenceHelper,
                                    wt.fc.QueryResult,
                                    e3ps.project.beans.OEMTypeHelper,
                                    e3ps.project.outputtype.OEMProjectType,
                                    e3ps.project.outputtype.OEMType,
                                    e3ps.common.util.CommonUtil,
                                    e3ps.common.util.StringUtil,
                                    e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                                    e3ps.project.outputtype.ModelPlan"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String carTypeOid = request.getParameter("carTypeOid");
    String carTypeName = request.getParameter("carTypeName");
    String companyName = request.getParameter("companyName");
    long oidLong = CommonUtil.getOIDLongValue(carTypeOid);

    QuerySpec qs = new QuerySpec();
    int idx = qs.appendClassList(ModelPlan.class, true);

    SearchCondition sc3 = new SearchCondition(ModelPlan.class,"carTypeReference.key.id","=", oidLong);
    qs.appendWhere(sc3, new int[]{idx});

    QueryResult qr4 = PersistenceHelper.manager.find( qs );
%>
    var pos = 1;
    var pOid = "<%=carTypeOid%>";
<%
    if(qr4.hasMoreElements()){
%>
            if(!carTypeDuplicateCheck(pOid)) {

                var tBody = document.getElementById("carTypePlanTable");
                var innerRow = tBody.insertRow(tBody.rows.length-1);

<%
                while(qr4.hasMoreElements()){
                    Object[] o2 = (Object[])qr4.nextElement();
                    ModelPlan mp = (ModelPlan)o2[0];

                    double y1 = Double.parseDouble(mp.getYield1()==null?"0":mp.getYield1());
            double y2 = Double.parseDouble(mp.getYield2()==null?"0":mp.getYield2());
            double y3 = Double.parseDouble(mp.getYield3()==null?"0":mp.getYield3());
            double y4 = Double.parseDouble(mp.getYield4()==null?"0":mp.getYield4());
            double y5 = Double.parseDouble(mp.getYield5()==null?"0":mp.getYield5());
            double y6 = Double.parseDouble(mp.getYield6()==null?"0":mp.getYield6());
            double sum = y1 + y2 + y3 + y4 + y5 + y6;
%>
                    var innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input type='checkbox' name='iCarTypePlanChk' id='iCarTypePlanChk' style='width:40'>"

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerText = "<%=companyName%>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input type='text' readonly name='carTypeCode' class='txt_fieldRO' style='width:80px'  value='<%=carTypeName%>'> ";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y1' class='txt_fieldRRO' style='width:45'  value='<%=StringUtil.getDouble(y1, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y2' class='txt_fieldRRO' style='width:45' value='<%=StringUtil.getDouble(y2, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y3' class='txt_fieldRRO' style='width:45' value='<%=StringUtil.getDouble(y3, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y4' class='txt_fieldRRO' style='width:45' value='<%=StringUtil.getDouble(y4, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y5' class='txt_fieldRRO' style='width:45' value='<%=StringUtil.getDouble(y5, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR"
                    innerCell.innerHTML = "<input type='text' readonly name='y6' class='txt_fieldRRO' style='width:45' value='<%=StringUtil.getDouble(y6, "", "###,###")%>'>";


                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteR";
                    innerCell.innerHTML = "<input type='text' readonly name='sum' class='txt_fieldRRO' style='width:55' value='<%=StringUtil.getDouble(sum, "", "###,###")%>'>";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input type='text' name='usage' class='txt_fieldR' style='width:30' onChange='javascript:usgChgVal();'  >";

                    innerCell = innerRow.insertCell();
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input type='text' name='optRate' class='txt_fieldR' style='width:40' onChange='javascript:usgChgVal();'>%";

                    innerCell = innerRow.insertCell();
                    innerCell.innerHTML = "<input type='hidden' name='carTypeOid' value='<%=carTypeOid%>'>";

                    var confBtn = document.getElementById("confBtn");
                    confBtn.disabled = true;
                    confBtn.onClick = "return false";
                }else{
                    alert('<%=messageService.getString("e3ps.message.ket_message", "02290") %><%--이미 추가된 차종입니다--%>' + "--><%=carTypeName%>");
                }
<%
            }
        }else{
%>
         alert('<%=messageService.getString("e3ps.message.ket_message", "02155") %><%--예상수량이 지정되지 않은 차종입니다--%>' + "--><%=carTypeName%>");
<%
      }
%>
