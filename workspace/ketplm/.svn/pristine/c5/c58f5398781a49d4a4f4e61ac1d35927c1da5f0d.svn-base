<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                  e3ps.dms.entity.*,
                  e3ps.dms.beans.DMSUtil,
                  wt.fc.QueryResult,
                  wt.fc.PersistenceHelper,
                  wt.fc.PagingSessionHelper,
                  wt.fc.PagingQueryResult,
                  wt.fc.ReferenceFactory,
                  wt.doc.WTDocument,
                  wt.doc.WTDocumentMaster,
                  wt.content.*,
                  wt.part.WTPart,
                  wt.org.WTUser,
                  wt.lifecycle.LifeCycleHelper,
                  wt.lifecycle.LifeCycleManaged,
                  e3ps.common.content.*,
                  java.util.Vector,
                  java.util.Hashtable,
                  java.io.IOException,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.DateUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%

  KETTechnicalDocument docu = null;
  Hashtable tempHash = new Hashtable();
  int i;

  String cmd = request.getParameter("cmd");
  String documentNo = request.getParameter("documentNo");
  String divisionCode = request.getParameter("divisionCode");
  String categoryCode = request.getParameter("categoryCode");
  String documentName = request.getParameter("documentName");
  String authorStatus = request.getParameter("authorStatus");
  String creatorName = request.getParameter("creatorName");
  String predate = request.getParameter("predate");
  String postdate = request.getParameter("postdate");
  String islastversion = request.getParameter("islastversion");

  String sortKey = request.getParameter("sortKey");
  String sortKeyD = request.getParameter("sortKeyD");
  String pageCnt = request.getParameter("pageCnt");

  sortKeyD=StringUtil.checkNull(sortKeyD);
  if(sortKeyD.equals("ASC")){
    sortKeyD="DESC";
  }else{
    sortKeyD="ASC";
  }

  tempHash.put("documentNo" , new String(documentNo));

  if(divisionCode.equals("0")){
    tempHash.put("divisionCode" , "");
  }else{
    tempHash.put("divisionCode" , new String(divisionCode));
  }
  tempHash.put("categoryCode" , new String(categoryCode));

  tempHash.put("documentName" , new String(documentName));
  tempHash.put("authorStatus" , new String(authorStatus));
  tempHash.put("creator" , new String(creatorName));
  tempHash.put("predate" , new String(predate));
  tempHash.put("postdate" , new String(postdate));
  tempHash.put("islastversion" , new String(islastversion));
  tempHash.put("sortKey" , new String(sortKey));
  tempHash.put("sortKeyD" , new String(sortKeyD));

  Vector docuOids = DMSUtil.serTechDocumentList(tempHash);

  String docuOid;

   if(docuOids.size()>0){
     for(i = 0; i < docuOids.size(); i++) {
       docuOid = docuOids.get(i).toString();
        docu = (KETTechnicalDocument)CommonUtil.getObject(docuOid);
      }
   }

   int totalRecord=0;
   int numPerPage= Integer.parseInt(pageCnt);
   int pagePerBlock=10;
   int totalPage=0;
   int totalBlock=0;
   int nowPage=0;
   int nowBlock=0;
   int beginPerPage=0;

   totalRecord = docuOids.size();
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

  function ViewDocument(oid){
    document.location.href="/plm/jsp/dms/ViewDocument.jsp?oid="+oid;
  }

-->
</script>
</head>
<body>
<form name=form01 >
<input type=hidden name=partOid value="0">
<table id="docuSearch" border="0" cellspacing="0" cellpadding="0" width="780" style="table-layout:fixed">
  <tr>
    <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
    <td width="100" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=1'>
        <% if(sortKey.equals("1")){ %><u><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%><% } %>
      </a>
    </td>
    <td width="145" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=2'>
        <% if(sortKey.equals("2")){ %><u><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%><% } %>
      </a>
    </td>
    <td width="240" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=3'>
        <% if(sortKey.equals("3")){ %><u><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%><% } %>
      </a>
    </td>
    <td width="60" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=4'>
        <% if(sortKey.equals("4")){ %><u><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%><% } %>
      </a>
    </td>
    <td width="30" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=5'>
        <% if(sortKey.equals("5")){ %><u><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%><% } %>
      </a>
    </td>
    <td width="55" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=6'>
        <% if(sortKey.equals("6")){ %><u><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%><% } %>
      </a>
    </td>
    <td width="70" class="tdblueM">
      <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=7'>
        <% if(sortKey.equals("7")){ %><u><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></u><% }else{ %><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%><% } %>
      </a>
    </td>
    <td width="35" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
  </tr>
<%
 if(docuOids.size()>0){
    QueryResult r = null;
    KETDocumentCategory docCate = null;
    String docCatePath = null;
    String versionInfo = null;
    String lifeCycleState = null;
    String insUser = null;
    String insDate = null;

    FormatContentHolder holder = null;
    ContentInfo info = null;
    ContentItem ctf = null;
    String appDataOid = "";
    String urlpath = null;
    String iconpath = null;
    String docuName = "";
   for (i=beginPerPage; i<(beginPerPage+numPerPage); i++){
     if (i==totalRecord){break;}
        docuOid = docuOids.get(i).toString();
       docu = (KETTechnicalDocument)CommonUtil.getObject(docuOid);

       String tmpStr = "";
       String resStr = "";
       int strLen = 0;
       docuName = docu.getTitle();

        r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETTechnicalCategoryLink.class);
       if (r.hasMoreElements()){
         docCate = (KETDocumentCategory) r.nextElement();
         docCatePath=docCate.getCategoryName();
       }

       versionInfo = docu.getVersionIdentifier().getValue();

       lifeCycleState = docu.getLifeCycleState().getDisplay();

       insUser = docu.getCreator().getFullName();
        insDate = DateUtil.getTimeFormat(docu.getCreateTimestamp(),"yyyy-MM-dd");

        //문서객체로 주첨부파일의 정보를 가져온다.
       holder = (FormatContentHolder)docu;
       info = ContentUtil.getPrimaryContent(holder);
       ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
       appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
       //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
       urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
       iconpath = info.getIconURLStr();
       iconpath = iconpath.substring(iconpath.indexOf("<img"));
       urlpath = "<a href=" + urlpath + " target='_blank'>" + iconpath;
%>
  <tr>
    <td width="40" class="tdwhiteM"><%=i+1%></td>
    <td width="100" class="tdwhiteM"><a href="/plm/jsp/dms/ViewTechDocument.jsp?oid=<%=CommonUtil.getOIDString(docu)%>&nowBlock=<%=nowBlock%>&page=<%=nowPage%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=<%=sortKey%>&isSer=T" target="_parent"><%=docu.getNumber()%></a></td>
    <td width="145" class="tdwhiteM" title="<%=docCatePath%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=docCatePath%></nobr></td>
    <td width="240" class="tdwhiteL" title="<%=docuName%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=docuName%></nobr></td>
    <td width="60" class="tdwhiteM"><%=lifeCycleState%></td>
    <td width="30" class="tdwhiteM"><%=versionInfo%></td>
    <td width="55" class="tdwhiteM" title="<%=insUser%>" style="text-overflow:ellipsis;overflow:hidden;"><nobr><%=insUser%></nobr></td>
    <td width="70" class="tdwhiteM"><%=insDate%></td>
    <td width="35" class="tdwhiteM"><%=urlpath%></td>
<%
    }
 }else{
%>
  <tr>
    <td colspan="9"  class="tdwhiteM0"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
  </tr>
<%
 }
%>
  </tr>
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
        <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock - 1 %>&page=<%=((nowBlock-1) * pagePerBlock)%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=<%=sortKey%>' class='small'><img src='../../portal/images/btn_arrow4.gif' style='border:0'></a>
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
          <a href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock%>&page=<%=(nowBlock * pagePerBlock)+ i %>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=<%=sortKey%>'><%=(nowBlock * pagePerBlock) + i + 1 %></a>
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
        <a  href='SearchTechDocumentList.jsp?nowBlock=<%=nowBlock + 1%>&page=<%=((nowBlock + 1) * pagePerBlock) %>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&islastversion=<%=islastversion%>&pageCnt=<%=pageCnt%>&sortKeyD=<%=sortKeyD%>&sortKey=<%=sortKey%> ' class='small'><img src='../../portal/images/btn_arrow2.gif' style='border:0'></a>
<%   }
   }
%>
        </td>
      </tr>
    </table>
  </td>
  <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02497") %><%--전체보기--%> : <%=totalRecord%>  )</td>
</tr>
</table>
</form>
</body>
<script>
self.resizeTo(document.body.scrollWidth , document.body.scrollHeight + 10);
</script>
</html>













