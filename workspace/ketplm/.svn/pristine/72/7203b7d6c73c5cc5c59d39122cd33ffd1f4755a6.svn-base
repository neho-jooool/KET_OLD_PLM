<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import = "java.util.ArrayList,java.util.*,
                  java.util.Hashtable,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  java.lang.*,
                  e3ps.bom.service.KETBOMHeaderQueryBean"%>

<%
KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

String headId = "";
try{
    headId = request.getParameter("oid");
}catch(Exception e){
    System.out.println("oid 받기 실패.");
}
    Hashtable condition = (Hashtable)request.getAttribute("condition");
    System.out.println("----------------------condition"+condition);
    ArrayList list = (ArrayList)request.getAttribute("list");
    //PageControl control = (PageControl)request.getAttribute("control");
    //System.out.println("----------------------control"+control);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "00764") %><%--결재대상 BOM 조회--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}

.tdwhiteLS {
    margin-top: 0px;
    padding-top: 0px;
    margin-bottom: 0px;
    padding-bottom: 0px;
    padding-left: 5px;
    border-bottom-width: 1px;
    border-bottom-style: solid;
    border-bottom-color: #e5e4e4;
    border-right-width: 1px;
    border-right-style: solid;
    border-right-color: #e5e4e4;
    background-color:#ffffff;
    height: 22px;
    color:#575757;
    text-align: left;
    font-family:Dotum;
    font-size: 12px;
}

.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<script language='javascript'>
<!--

function search(){
    document.forms[0].action =  '/plm/servlet/e3ps/WFBomViewServlet';
    document.forms[0].submit();
}

function tree_head(tid)
{
    var rows = document.getElementsByTagName("TR");

    for (var j = 0; j < rows.length; j++)
    {
       var s = rows[j];
       if ( s.id != tid  && s.id.indexOf(tid) >= 0)
       {
           if(document.getElementById("headopen").value == "true")
           {
               s.style.display = "none";
           }else{
               s.style.display = "block";
               try{
                   var cs_change = document.images["img_"+s.id].src;
                   cs_change = cs_change.replace("/plus","/minus");
                   document.images["img_"+s.id].src = cs_change;
                }catch(e){}
           }
       }
    }

    if(document.getElementById("headopen").value == "true")
    {
       document.getElementById("headopen").value = "false"
    }else{
       document.getElementById("headopen").value = "true"
    }

}

function tree_comp(tid, num)
{
    var img_path;
    var rows = document.getElementsByTagName("TR");
    for (var j = 0; j < rows.length; j++)
    {
       var s = rows[j];
       var chk = (s.id).substring(0,tid.length);
      // var underchk = document.images["img_"+s.id].src;
       if(num == '1')
       {
           if ( s.id == tid) //폴더모양 변경.
           {
               img_path = document.images["img_"+s.id].src;
               if(img_path.indexOf("minus.gif") >= 0)
               {
                   document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/plus.gif";
               }else{
                   document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/minus.gif";
               }
           }

           if ( s.id != tid  && chk.indexOf(tid) >= 0) //하위구조를 열고 닫음.
           {
               if(img_path.indexOf("/minus") >= 0)
               {
                   s.style.display = "none";
               }else{
                   s.style.display = "block";

                   try{
                       var s_change = document.images["img_"+s.id].src;
                        s_change = s_change.replace("/plus","/minus");
                       document.images["img_"+s.id].src = s_change;
                   }catch(e){}
               }

           }
       }else{
           if ( s.id == tid) //폴더모양 변경.
           {
               img_path = document.images["img_"+s.id].src;
               if(img_path.indexOf("minusbottom.gif") >= 0)
               {
                   document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/plusbottom.gif";
               }else{
                   document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/minusbottom.gif";
               }
           }

           if ( s.id != tid  && chk.indexOf(tid) >= 0) //하위구조를 열고 닫음.
           {
               if(img_path.indexOf("/minus") >= 0)
               {
                   s.style.display = "none";
               }else{
                   s.style.display = "block";

                   try{
                        var s_change = document.images["img_"+s.id].src;
                        s_change = s_change.replace("/plus","/minus");
                       document.images["img_"+s.id].src = s_change;
                   }catch(e){}
               }

           }
       }

      /* if ( s.id == tid) //폴더모양 변경.
       {
           img_path = document.images["img_"+s.id].src;
           if(img_path.indexOf("folderopen.gif") >= 0)
           {
               document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/folder.gif";
           }else{
               document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/folderopen.gif";
           }
       }

       if ( s.id != tid  && chk.indexOf(tid) >= 0) //하위구조를 열고 닫음.
       {
           if(img_path.indexOf("folderopen.gif") >= 0)
           {
               s.style.display = "none";
           }else{
               s.style.display = "block";

               try{
               document.images["img_"+s.id].src = "/plm/portal/images/img_wtbom/folderopen.gif";
               }catch(e){}
           }

       }*/
    }
}

function wfbom_open() {
    var url = "/plm/jsp/bom/WFBomView.jsp?oid=260522";
    openWindow(url,"","1100","800","status=0,scrollbars=no,resizable=no");
}

//-->
</script>

</head>

<% if(condition == null){%>
<body onload="search();">
<%}else{%>
<body>
<%}%>

<form method="post">

<input type="hidden" name="wtcode" value="<%=headId%>">
<input type="hidden" name="headopen"  id="headopen" value="true">
<!-- hidden begin -->
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sort" value="1 DESC">
<!-- hidden end -->

<table width="1000" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00763") %><%--결재대상 BOM 구조--%></td>

              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>

<%
if(condition != null)
{
    if( (condition.get("part_type")).equals("P") )
    {
%>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="19%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td width="4%" class="tdblueM">SEQ</td>
          <td width="4%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01346") %><%--레벨--%></td>
          <td width="4%" class="tdblueM">Item<br>SEQ</td>
          <td width="17%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="5%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="5%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%></td>
          <td width="4%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="6%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td width="12%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01237") %><%--대체부품번호--%></td>
          <td width="9%" class="tdblueM">ECO No</td>
          <td width="6%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
        </tr>
      </table>
      <div style="width=100%;overflow-x:hidden;overflow-y:auto;">
      <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed">
<%
        Hashtable earlyWarning = null;
          if( list !=null && list.size() > 0 ){
              String itemCode = "";
              String lineStr = "";
              //String lastStr = "";
              String crossStr = "";
              String trId = "";

            for(int inx = 0 ; inx < list.size(); inx++){
                earlyWarning = (Hashtable)list.get(inx);
//System.out.println("@@@@@ earlyWarning : " + earlyWarning);
                itemCode = (String)earlyWarning.get("itemcode");

                lineStr = (String)earlyWarning.get("linechk");
                //lastStr = (String)earlyWarning.get("lastchk");
                crossStr = (String)earlyWarning.get("crosschk");
                trId = (String)earlyWarning.get("sequencenumber");

                if(inx == 0)
                {
                    itemCode = lineStr + "<img src='/plm/portal/images/img_wtbom/base.gif' style='cursor:hand' onclick=\"javascript:tree_head('"+trId+"')\">&nbsp;"+ itemCode;
                }else{
                    //itemCode = lineStr + crossStr + lastStr + "&nbsp;<a style='vertical-align:bottom;'>"+ itemCode+"</a>";
                    itemCode = lineStr + crossStr + "&nbsp;<a style='vertical-align:bottom;'>"+ itemCode+"</a>";
                }
%>
        <tr id='<%=trId%>'>
          <td width="19%" class="tdwhiteLS"><%=itemCode %>&nbsp;</a></td>
          <!--td width="4%"  class="tdwhiteM"><%=earlyWarning.get("row_id") %>&nbsp;</td -->
          <td width="4%"  class="tdwhiteM"><%=inx %>&nbsp;</td>
          <td width="4%"  class="tdwhiteM"><%=earlyWarning.get("bomlevel") %>&nbsp;</td>
          <td width="4%"  class="tdwhiteM"><%=earlyWarning.get("itemseq") %>&nbsp;</td>
          <td width="17%" class="tdwhiteL"><div class="ellipsis" style="width:180"><nobr><%=earlyWarning.get("itemname") %>&nbsp;</td></nobr></div>
          <td width="5%"  class="tdwhiteM"><%=Double.parseDouble(String.valueOf(earlyWarning.get("quantity"))) %>&nbsp;</td>
          <td width="5%"  class="tdwhiteM"><%=bean.getUnitDisplayValue( (String)earlyWarning.get("unit") ) %>&nbsp;</td>
          <td width="4%"  class="tdwhiteM"><%=earlyWarning.get("version") %>&nbsp;</td>
          <td width="6%"  class="tdwhiteM"><%=earlyWarning.get("status") %>&nbsp;</td>
          <td width="12%" class="tdwhiteL" title="<%=earlyWarning.get("subcode") %>">
              <div class="ellipsis" style="width:132"><nobr><%=earlyWarning.get("subcode") %>&nbsp;</nobr></div></td>
          <td width="9%"  class="tdwhiteM">&nbsp;</td>
          <td width="6%"  class="tdwhiteM"><%=earlyWarning.get("usernm") %>&nbsp;</td>
        </tr>
<%
             }
        }else{
%>
         <tr>
             <td colspan="13"  class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
         </tr>
<%
        }
%>
      </table>
      </div>
<%
    }else{        // 금형 BOM
%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td width="15%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
      <td width="3%" class="tdblueM">SEQ</td>
      <td width="3%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01346") %><%--레벨--%></td>
      <td width="17%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
      <td width="17%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
      <td width="4%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
      <td width="7%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
      <td width="6%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00793") %><%--경도(From)--%></td>
      <td width="6%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00794") %><%--경도(To)--%></td>
      <td width="7%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01865") %><%--설계일자--%></td>
      <td width="3%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
      <td width="7%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
      <td width="8%" class="tdblueM0">ECO No</td>
    </tr>
    </table>
    <div style="width=100%;overflow-x:hidden;overflow-y:auto;">
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
<%
        Hashtable earlyWarning = null;
          if( list !=null && list.size() > 0 ){

              String itemCode = "";
              String quantity = "";
              String lineStr = "";
              //String lastStr = "";
              String crossStr = "";
              String trId = "";

            for(int inx = 0 ; inx < list.size(); inx++){
                earlyWarning = (Hashtable)list.get(inx);
                itemCode = (String)earlyWarning.get("itemcode");

                lineStr = (String)earlyWarning.get("linechk");
                //lastStr = (String)earlyWarning.get("lastchk");
                crossStr = (String)earlyWarning.get("crosschk");
                trId = (String)earlyWarning.get("sequencenumber");

                if(inx == 0)
                {
                    itemCode = lineStr + "<img src='/plm/portal/images/img_wtbom/base.gif' style='cursor:hand' onclick=\"javascript:tree_head('"+trId+"')\">&nbsp;"+ itemCode;
                }else{
                    //itemCode = lineStr + crossStr + lastStr + "&nbsp;<a style='vertical-align:bottom;'>"+ itemCode+"</a>";
                    itemCode = lineStr + crossStr + "&nbsp;<a style='vertical-align:bottom;'>"+ itemCode+"</a>";
                }

                // 수량정보 소숫점 제거
                quantity = String.valueOf(earlyWarning.get("quantity"));
                if ( !quantity.equals("") && quantity.indexOf(".") >= 0 ) {
                    quantity = quantity.substring(0, quantity.indexOf("."));
                }

%>
        <tr id='<%=trId%>'>
          <td width="15%" class="tdwhiteLS"><%=itemCode %>&nbsp;</a></td>
          <!-- td width="3%"  class="tdwhiteM"><%=earlyWarning.get("row_id") %>&nbsp;</td -->  <!-- Seq 정보를 row_id 로?? -->
          <td width="3%"  class="tdwhiteM"><%=inx %>&nbsp;</td>
          <td width="3%"  class="tdwhiteM"><%=earlyWarning.get("bomlevel") %>&nbsp;</td>
          <td width="17%" class="tdwhiteL" title="<%=earlyWarning.get("itemname") %>">
              <div class="ellipsis" style="width:187"><nobr><%=earlyWarning.get("itemname") %>&nbsp;</nobr></div></td>
          <td width="4%"  class="tdwhiteM"><%=quantity %>&nbsp;</td>
          <td width="7%"  class="tdwhiteM"><%=earlyWarning.get("material") %>&nbsp;</td>
          <td width="6%" class="tdwhiteM"><%=earlyWarning.get("hardnessfrom") %>&nbsp;</td>
          <td width="6%"  class="tdwhiteM"><%=earlyWarning.get("hardnessto") %>&nbsp;</td>
          <td width="7%" class="tdwhiteM"><%=earlyWarning.get("designdate") %>&nbsp;</td>
          <td width="3%"  class="tdwhiteM"><%=earlyWarning.get("version") %>&nbsp;</td>
          <td width="7%"  class="tdwhiteM"><%=earlyWarning.get("status") %>&nbsp;</td>
          <td width="8%"  class="tdwhiteM0">&nbsp;</td>

        </tr>
<%
             }
        }else{
%>
         <tr>
             <td colspan="13"  class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
         </tr>
<%
        }
%>

        </table>
        </div>
<%
    }
}//if(condition != null)
%>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
