<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.dms.service.KETDmsHelper,
        e3ps.dms.entity.KETDocumentCategory,
        java.util.ArrayList"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%

  String onlyLeaf = request.getParameter("onlyLeaf");
  String isSearch = request.getParameter("isSearch");
  String modal = request.getParameter("modal");
  String fnCall = request.getParameter("fnCall");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<link rel="stylesheet" href="../../portal/css/drtree.css" type="text/css">
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
<script type="text/javascript" src="/plm/portal/js/drtree.js?ver=1"></script>
<script language="JavaScript">

$(document).ready(function(){
    
    <% 
    if("Y".equals(onlyLeaf)){
    %>
    
    //img 중에 폴더로 된 (하위가 존재하는) 분류는 선택될 수 없다.
    $("img").each(function(){
        if ($(this).attr("src") == "img/folder.gif" || $(this).attr("src") == "img/folderopen.gif") {
            $(this).siblings("[name='checkNode']").hide();
        }
    });

    $("#cd0").hide();
    
    <% 
    }
    %>
});

function getCheckedNode() {
	
	var modal = '<%=modal%>';
	
	if(modal == 'N'){
		
		if(opener) {
			
			opener.<%=fnCall%>(d.getCheckedNode());
			
        }else if(parent.opener) {
        	
            parent.opener.<%=fnCall%>(d.getCheckedNode());
            
        }
		
		
	}else{
		window.returnValue = d.getCheckedNode();
	    window.close();	
	}
	
	
}
	
function thiscolse(){
    window.close();
}
</script>
<BODY>
    <table style="width: 450px;">
        <tr>
            <td valign="top">
                <table style="width: 100%;margin-bottom:10px">
                    <tr>
                        <td background="/plm/portal/images/logo_popupbg.png">
                            <table style="height: 28px;">
                                <tr>
                                    <td style="width:10px"></td>
                                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                    <td class="font_01">문서분류</td>
                                    <td style="width: 10px;"></td>
                                </tr>
                            </table>
                        </td>
                        <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                    </tr>
                </table>
                <table style="width: 98%; ">
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
                <div style="margin-left: 15px;overflow:auto;height:250px;">
                    <script type="text/javascript">
                    var obname;
                    d = new dTree("d");
                    d.config.check = true;
                    d.config.isSingleSel = <%=request.getParameter("singleSel")%>;
                    
                    <%
					  boolean isSearch_ = false;                     
                      if("Y".equals(isSearch)){
                    	  isSearch_ = true;
                      }
                      String category = "";
                      String rootName = "";
                      ArrayList nodeList = null;
                      if("DEV".equals(request.getParameter("docRoot"))){
                          category = "PD100001";
                          rootName = messageService.getString("e3ps.message.ket_message", "00638");//개발산출문서
                      }else if("TECH".equals(request.getParameter("docRoot"))){
                          category = "TD100001";
                          rootName = messageService.getString("e3ps.message.ket_message", "01123");//기술문서
                      }else{
                	      category = "ROOT";
                	      rootName = "ROOT";
                      }
                      if(!"ROOT".equals(category)){
                	      nodeList = KETDmsHelper.service.selectDocCategoryForTree(category,isSearch_);                      
                      }else{
                    	  nodeList = KETDmsHelper.service.selectDocCategoryForTree();
                      
                      }
                      Object[] obj = (Object[])nodeList.get(0);
                        
                      String parent = (String)obj[0];
                      String my = (String)obj[1];
                      String myname = (String)obj[2];
                    %>
                      d.add("<%=my%>",-1,"<%=rootName%>","","<%=rootName%>","main");
        
                    <%
                      String attribute1 = "";
                      String qulityYn = "";
                      for(int i = 1; i < nodeList.size(); i++)
                      {
                        obj = (Object[])nodeList.get(i);
        
                        parent = (String)obj[0];
                        my = (String)obj[1];
                        myname = (String)obj[2];
                        attribute1 = (String)obj[3];
                        qulityYn = (String)obj[4];
                    %>
                        obname = "<%=myname%>";
                        d.add("<%=my%>","<%=parent%>",obname,"#",obname,"main","","","","<%=attribute1%>","<%=qulityYn%>");
                    <%
                        }
                    %>
        
                    document.write(d);
                    </script>
                </div>
            </td>
        </tr>
    </table>
        </tr>
    </table>
</body>
</html>
