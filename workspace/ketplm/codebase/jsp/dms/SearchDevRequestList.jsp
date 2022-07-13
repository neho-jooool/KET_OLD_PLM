<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "wt.fc.QueryResult,
                  java.util.Vector,
                  java.util.Hashtable,
                  java.io.IOException,
                  java.util.StringTokenizer,
                  e3ps.project.E3PSProject,
                  e3ps.project.beans.ProjectHelper,
                  e3ps.dms.beans.DMSUtil,
                  e3ps.dms.entity.KETDevelopmentRequest,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
  Hashtable tempHash = new Hashtable();
  int i;

  String divCode = "";
   if(CommonUtil.isMember("전자사업부")){
    divCode = "ER";
  }else if(CommonUtil.isMember("자동차사업부")){
    divCode = "CA";
  }else{
    divCode = "CA";
  }

  Vector drOid = null;
  Vector drNo = null;
  Vector developmentStep = null;
  Vector projectNo = null;
  Vector repCarType = null;
  Vector devProductName = null;
  Vector requestBuyer = null;
  Vector devReviewTypeCode = null;
  Vector drState = null;
  Vector drCreator = null;
  Vector drCreateDate = null;
  Vector partName = null;
  Vector carTypeCode = null;

  String DevelopmentStep = request.getParameter("DevelopmentStep");
  String DevProductName = request.getParameter("DevProductName");
  String RequestBuyer = request.getParameter("RequestBuyer");
  String LastUsingBuyer = request.getParameter("LastUsingBuyer");
  String userName = request.getParameter("userName");
  String RepCarType = request.getParameter("RepCarType");
  String authorStatus = request.getParameter("authorStatus");
  String DevReviewTypeCode = request.getParameter("DevReviewTypeCode");
  String ProductCategoryCode = request.getParameter("ProductCategoryCode");
  String predate = request.getParameter("predate");
  String postdate = request.getParameter("postdate");
  String sortKey = request.getParameter("sortKey");
  String sortKeyD = request.getParameter("sortKeyD");
  String pageCnt= request.getParameter("pageCnt");

  sortKeyD=StringUtil.checkNull(sortKeyD);
  if(sortKeyD.equals("ASC")){
    sortKeyD="DESC";
  }else{
    sortKeyD="ASC";
  }
  String sortKeyDP = "";
  if(sortKeyD.equals("ASC")){
    sortKeyDP="DESC";
  }else{
    sortKeyDP="ASC";
  }

  tempHash.put("developmentStep" , new String(DevelopmentStep));
  tempHash.put("devProductName" , new String(DevProductName));
  tempHash.put("requestBuyer" , new String(RequestBuyer));
  tempHash.put("lastUsingBuyer" , new String(LastUsingBuyer));
  tempHash.put("creator" , new String(userName));
  tempHash.put("repCarType" , new String(RepCarType));
  tempHash.put("authorStatus" , new String(authorStatus));
  tempHash.put("devReviewTypeCode" , new String(DevReviewTypeCode));
  tempHash.put("productCategoryCode" , new String(ProductCategoryCode));
  tempHash.put("predate" , new String(predate));
  tempHash.put("postdate" , new String(postdate));
  tempHash.put("sortKey" , new String(sortKey));
  tempHash.put("sortKeyD" , new String(sortKeyD));
  tempHash.put("divCode" , new String(divCode));

  try {

    Hashtable drInfo = DMSUtil.serDevRequestList(tempHash);

    if(drInfo.get("drNo") instanceof Vector) {
      drOid = new Vector();
      drNo = new Vector();
      developmentStep = new Vector();
      projectNo = new Vector();
      repCarType = new Vector();
      devProductName = new Vector();
      requestBuyer = new Vector();
      devReviewTypeCode = new Vector();
      drState = new Vector();
      drCreator = new Vector();
      drCreateDate = new Vector();
      partName = new Vector();
      carTypeCode = new Vector();

      drOid = (Vector)drInfo.get("drOid");
      drNo = (Vector)drInfo.get("drNo");
      developmentStep = (Vector)drInfo.get("developmentStep");
      projectNo = (Vector)drInfo.get("projectNo");
      repCarType = (Vector)drInfo.get("repCarType");
      devProductName = (Vector)drInfo.get("devProductName");
      requestBuyer = (Vector)drInfo.get("requestBuyer");
      devReviewTypeCode = (Vector)drInfo.get("devReviewTypeCode");
      drState = (Vector)drInfo.get("drState");
      drCreator = (Vector)drInfo.get("drCreator");
      drCreateDate = (Vector)drInfo.get("drCreateDate");
      partName = (Vector)drInfo.get("partName");
      carTypeCode = (Vector)drInfo.get("carTypeCode");
    }

  } catch (Exception e) {
    Kogger.error(e);
  }

   int totalRecord=0;
   int numPerPage= Integer.parseInt(pageCnt);
   int pagePerBlock= 10;
   int totalPage=0;
   int totalBlock=0;
   int nowPage=0;
   int nowBlock=0;
   int beginPerPage=0;

   totalRecord = drNo.size();
   if (request.getParameter("page") != null) {
     nowPage = Integer.parseInt(request.getParameter("page"));
   }

   beginPerPage = nowPage * numPerPage;

   totalPage = (int)Math.ceil((double)totalRecord / numPerPage);

   if (request.getParameter("nowBlock") != null) {
       nowBlock = Integer.parseInt(request.getParameter("nowBlock"));
   }
  totalBlock=(int)Math.ceil((double)totalPage / pagePerBlock);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<style type="text/css">

</style>
<script language="JavaScript">
<!--
  function isNull(str) {
    if(str==null||str==""){
      return true;
    }
    return false;
  }

  function hideProcessing() {
    var div1 = parent.document.getElementById('div1');
    var div2 = parent.document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
  }

  window.onload=function(){
    hideProcessing();
  };
-->

</script>
</head>
<body>
<form name=form01 >
<table id="drSearch"  border="0" cellspacing="0" cellpadding="0" width="780" style="table-layout:fixed">
  <tr>
    <td width="35" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
    <td width="70" class="tdblueM">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=9'>
        <% if(sortKey.equals("9")){ %><u><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%><% } %>
      </a>
    </td>
    <td width="80" class="tdblueM">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=0'>
          <% if(sortKey.equals("0")){ %><u><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%><% } %>
      </a>
    </td>
    <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00726") %><%--검토PJT No--%></td>
    <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00608") %><%--개발PJT No--%></td>
    <td width="150" class="tdblueM">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=2'>
        <% if(sortKey.equals("2")){ %><u><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%><% } %>
      </a>
    </td>
    <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02261") %><%--의뢰처--%></td>
    <td width="60" class="tdblueM">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=6'>
          <% if(sortKey.equals("6")){ %><u><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%><% } %>
      </a>
    </td>
    <td width="70" class="tdblueM">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=7'>
            <% if(sortKey.equals("7")){ %><u><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%><% } %>
      </a>
    </td>
    <td width="55" class="tdblueM0">
      <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=8'>
        <% if(sortKey.equals("8")){ %><u><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%><% } %>
      </a>
    </td>
  </tr>
<%
 if(drNo.size()>0){
    String s_drOid = null;
    String s_drNo = null;
    String s_developmentStep = null;
    String s_projectNo = null;
   String s_repCarType = null;
   String s_devProductName = null;
   String s_requestBuyer = null;
   String s_devReviewTypeCode = null;
   String s_drState = null;
   String s_drCreator = null;
   String s_drCreateDate = null;
   String s_partName = null;
   String s_carTypeCode = null;
   String s_prjCnt="";
   String s_prjNo="";

   for (i=beginPerPage; i<(beginPerPage+numPerPage); i++){
      if (i==totalRecord){break;}

      s_drOid = StringUtil.checkNull((String)drOid.get(i));
      KETDevelopmentRequest dr = (KETDevelopmentRequest)CommonUtil.getObject(s_drOid);

      s_drNo = StringUtil.checkNull((String)drNo.get(i));

      s_developmentStep = StringUtil.checkNull((String)developmentStep.get(i));
      if(s_developmentStep.equals("R")){
        s_developmentStep = "검토의뢰";
      }else if(s_developmentStep.equals("D")){
        s_developmentStep = "개발의뢰";
      }

      s_projectNo = StringUtil.checkNull((String)projectNo.get(i));
      if(!s_projectNo.equals("")){
       Object obj = CommonUtil.getObject(s_projectNo);

       if(obj==null){
         s_projectNo = "";
       }else{
         if ( obj instanceof E3PSProject ) {
           E3PSProject project = (E3PSProject)obj;
           s_projectNo = project.getPjtNo();
         }else{
           s_projectNo = "";
         }
       }
     }

     s_prjNo="";
     QueryResult qrPrj = ProjectHelper.getDevRequestProject(dr);
     Object[] objPrj  = null;
     E3PSProject eep = null;
     if(qrPrj.hasMoreElements()){
         s_prjCnt = String.valueOf(qrPrj.size()-1);
        objPrj = (Object[])qrPrj.nextElement();
        eep = (E3PSProject)objPrj[0];
        s_prjNo = StringUtil.checkNull(eep.getPjtNo());
        if(qrPrj.size()>1){
           s_prjNo = messageService.getString("e3ps.message.ket_message", "00013", s_prjNo, s_prjCnt)/*{0}외 {1}건*/;
        }
     }

     if(s_developmentStep.equals("검토의뢰")){
        s_projectNo = s_prjNo;
         s_prjNo = "";
      }

     s_repCarType = StringUtil.checkNull((String)repCarType.get(i));
     s_devProductName = StringUtil.checkNull((String)devProductName.get(i));
     s_requestBuyer = StringUtil.checkNull((String)requestBuyer.get(i));
     StringTokenizer st = new StringTokenizer(s_requestBuyer, ",");
     String ct="";
     String bName="";
     while (st.hasMoreTokens()) {
       ct=st.nextToken();
       NumberCode nCode1 = (NumberCode)CommonUtil.getObject(ct);

       if(nCode1==null){
         bName=bName + "," + ct;
       }else{
         bName=bName + "," + nCode1.getName();
       }
     }
     s_requestBuyer=bName.substring(1);
     s_devReviewTypeCode = StringUtil.checkNull((String)devReviewTypeCode.get(i));
     if(dr==null){
       s_drState = "";
     }else{
       s_drState = StringUtil.checkNull(dr.getLifeCycleState().getDisplay(messageService.getLocale()));
     }
     s_drCreator = StringUtil.checkNull((String)drCreator.get(i));
     s_drCreateDate = StringUtil.checkNull((String)drCreateDate.get(i));
     s_partName = StringUtil.checkNull((String)partName.get(i));
     s_carTypeCode = StringUtil.checkNull((String)carTypeCode.get(i));
%>
  <tr>
    <td class="tdwhiteM"><%=i+1%></td>
    <td class="tdwhiteM"><%=s_developmentStep%>&nbsp;</td>
    <td class="tdwhiteM"><a href="/plm/jsp/dms/ViewDevRequest.jsp?oid=<%=s_drOid%>&nowBlock=<%=nowBlock%>&page=<%=nowPage%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=<%=sortKey%>&isSer=T" target="_parent"><%=s_drNo%></a></td>
    <td class="tdwhiteM"><%=s_projectNo%>&nbsp;</td>
    <td class="tdwhiteM"><%=s_prjNo%>&nbsp;</td>
    <td class="tdwhiteL" title="<%=s_devProductName%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=s_devProductName%>&nbsp;</nobr></td>
    <td class="tdwhiteL" title="<%=s_requestBuyer%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=s_requestBuyer%>&nbsp;</nobr></td>
    <td class="tdwhiteM"><%=s_drCreator%>&nbsp;</td>
    <td class="tdwhiteM"><%=s_drCreateDate%>&nbsp;</td>
    <td class="tdwhiteM0"><%=s_drState%>&nbsp;</td>
  </tr>
<%
   }
 }else{
%>
   <tr>
     <td colspan="11"  class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
   </tr>

<%
 }
%>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="780">
  <tr>
    <td class="space10"></td>
  </tr>
</table>

<table border="0" cellspacing="0" cellpadding="0" width="780">
  <tr>
        <td class="small" align="left">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> : <%=totalPage%> )</td>
    <td>
      <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="40" align="center">
<%
   if(totalRecord !=0){
      if(nowBlock > 0) {
%>

             <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock - 1 %>&page=<%=((nowBlock-1) * pagePerBlock)%>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyDP%>&sortKey=<%=sortKey%>' class='small'><img src='../../portal/images/btn_arrow4.gif' style='border:0'></a>
<%    }
%>
        </td>
        <td width="1" bgcolor="#dddddd"></td>

 <%   for(i=0; i< pagePerBlock; i++) {
        if ((nowBlock * pagePerBlock) + i ==nowPage){
 %>
         <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
          <b><font color=006699><%=nowPage+1%></font></b>
        </td>
<%       }else{
%>
        <td style="padding:2 8 0 7;cursor:hand" onMouseOver="this.style.background='#ECECEC'" OnMouseOut="this.style.background=''" class="nav_on">
          <font color=777777>
            <a href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock%>&page=<%=(nowBlock * pagePerBlock)+ i %>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyDP%>&sortKey=<%=sortKey%>'><%=(nowBlock * pagePerBlock) + i + 1 %></a>
           </font>
        </td>
<%       }
     if ((nowBlock * pagePerBlock) + i + 1 ==totalPage){
            break;
         }
      }
%>
        <td width="1" bgcolor="#dddddd"></td>
        <td width="45"align="center">
<%   if (totalBlock > nowBlock + 1) { %>
          <a  href='SearchDevRequestList.jsp?nowBlock=<%=nowBlock + 1%>&page=<%=((nowBlock + 1) * pagePerBlock) %>&DevelopmentStep=<%=DevelopmentStep%>&DevProductName=<%=DevProductName%>&RequestBuyer=<%=RequestBuyer%>&LastUsingBuyer=<%=LastUsingBuyer%>&userName=<%=userName%>&RepCarType=<%=RepCarType%>&authorStatus=<%=authorStatus%>&DevReviewTypeCode=<%=DevReviewTypeCode%>&ProductCategoryCode=<%=ProductCategoryCode%>&predate=<%=predate%>&postdate=<%=postdate%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyDP%>&sortKey=<%=sortKey%>' class='small'><img src='../../portal/images/btn_arrow2.gif' style='border:0'></a>
<%   }
   }
%>
        </td>
      </tr>
    </table>
  </td>
    <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%> : <%=totalRecord%>  )</td>
  </tr>
</table>
</form>
</body>
<script>
self.resizeTo(document.body.scrollWidth , document.body.scrollHeight + 10);
</script>
</html>
