<%@page import="e3ps.common.util.StringUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="ext.ket.part.classify.service.PartClazHelper,
        ext.ket.part.entity.dto.PartClassificationDTO,
        java.util.List,
        java.util.ArrayList" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%

  String onlyLeaf = request.getParameter("onlyLeaf");
  String depthLevel2 = request.getParameter("depthLevel2");
  String openAll = request.getParameter("openAll");
  String classCode = request.getParameter("classCode");
  String callBackFn = request.getParameter("callBackFn");
  String modal = request.getParameter("modal");
  String valueField = request.getParameter("valueField");
  String displayField = request.getParameter("displayField");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<link rel="stylesheet" href="/plm/portal/css/drtree.css" type="text/css">
<style type="text/css">
body {
    /* margin-left: 10px; */
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}

</style>
<script type="text/javascript" src="/plm/portal/js/drtree.js"></script>
<script language="JavaScript">

function openerValueSet(returnValue){
	
    var target = (opener || parent.opener);
    
    if(target.document.getElementById('<%=valueField%>')){
        target.document.getElementById('<%=valueField%>').value = returnValue[0].id;   
    }
    
    if(target.document.getElementById('<%=displayField%>')){
        target.document.getElementById('<%=displayField%>').value = returnValue[0].name; 
    }
    
    if(target.document.getElementsByName('<%=valueField%>')[0]){
        target.document.getElementsByName('<%=valueField%>')[0].value = returnValue[0].id; 
    }
    
    if(target.document.getElementsByName('<%=displayField%>')[0]){
        target.document.getElementsByName('<%=displayField%>')[0].value = returnValue[0].name; 
    }
    
}

function getCheckedNode() {
	var modal = '<%=modal%>';
	
	if(modal == 'n'){
		if(parent.opener.<%=callBackFn%>){
			parent.opener.<%=callBackFn%>(d.getCheckedNode());
		}else{
			openerValueSet(d.getCheckedNode());
		}
	}else{
		window.returnValue = d.getCheckedNode();	
	}
	
    window.close();
}
	
function thiscolse(){
    window.close();
}

$(document).ready(function(){
	
	<% 
	if("Y".equals(onlyLeaf)){
	%>
	
    //img 중에 폴더로 된 (하위가 존재하는) 분류는 선택될 수 없다.
    $("img").each(function(){
		if ($(this).attr("src") == "img/folder.gif" || $(this).attr("src") == "img/folderopen.gif") {
		   <%	if("Y".equals(depthLevel2)){ %>
			 $(this).siblings("[type='checkbox']").hide();
			 // 1레벨로 끝나는 것도 체크 가능할 경우
			 //$(this).parent().siblings(".dTreeNode").find("img[src='img/folderopen.gif']").siblings("[name='checkNode']").hide();
			 // 1레벨로 끝나는 경우 체크 불가능할 경우
			 $(this).parent().siblings(".dTreeNode").find("[name='checkNode']").hide();
		   <%	}else{ %>
			 $(this).siblings("[name='checkNode']").hide();
		   <%   } %>
		}
	});

    $("#cd0").hide();
    
    <% 
	}
    %>
});




</script>
<BODY >
    <table style="width: 700px;">
        <tr>
            <td valign="top">
                <table style="width: 100%;margin-bottom:10px">
                    <tr>
                        <td background="/plm/portal/images/logo_popupbg.png">
                            <table style="height: 28px;">
                                <tr>
                                    <td style="width:10px"></td>
                                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01">부품분류</td>
                                    <td style="width: 10px;"></td>
                                </tr>
                            </table>
                        </td>
                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                </table>
                <table style="width: 98%; style="margin-right: 5px">
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right">
                            <table >
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 7px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:getCheckedNode();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a>
                                                </td>
                                                <td style="width: 7px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="javascript:thiscolse();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                                                </td>
                                                <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <div style="margin-left: 15px;overflow:auto;height:610px;">
                    <script type="text/javascript">
                    var obname;
                    d = new dTree("d");
                    d.config.check = true;
                    d.config.isSingleSel = <%=request.getParameter("singleSel")%>;
                    <%
                      String category = "";
                      String rootName = messageService.getString("e3ps.message.ket_message", "06112");//부품 분류
                      List<PartClassificationDTO> nodeList = PartClazHelper.service.searchFullList(true);                      

                      PartClassificationDTO dto = nodeList.get(0);
                        
                      String parent = dto.getParentOid();
                      String my = dto.getClazOid();
                      String myname = dto.getClassKrName();
                      String lev = dto.getLev();
                    %>
                      d.add("<%=my%>",-1,"<%=rootName%>","","<%=rootName%>","main");
        
                    <%
                      for(int i = 1; i < nodeList.size(); i++)
                      {
                	    dto = nodeList.get(i);
        
                        parent = dto.getParentOid();
                        my = dto.getClazOid();
                        myname = dto.getClassKrName();
                        lev = dto.getLev();
                        
                        if("Y".equals(depthLevel2)){
                            if(  Integer.parseInt(lev) <= 3){
                    %>
                                obname = "<%=myname%>";
                                d.add("<%=my%>","<%=parent%>",obname,"",obname,"main");
                    <%      
                            }
                        }else{
                            if(classCode == null){
                    %>
                        obname = "<%=myname%>";
                        d.add("<%=my%>","<%=parent%>",obname,"",obname,"main");
                    <%
                            }else if(dto.getClassCode()!=null && dto.getClassCode().startsWith(classCode)){
                    %>
                            obname = "<%=myname%>";
                            d.add("<%=my%>","<%=parent%>",obname,"",obname,"main");
                    <%  
                            }
                        }
                      }
                    %>
        
                    document.write(d);
                    
                    <% 
                    if("Y".equals(openAll)){
                    %>
                    
                    d.openAll();
                    
                    <% 
                    }
                    %>
                    
                    </script>
                </div>
            </td>
        </tr>
    </table>
        </tr>
    </table>
</body>
</html>
