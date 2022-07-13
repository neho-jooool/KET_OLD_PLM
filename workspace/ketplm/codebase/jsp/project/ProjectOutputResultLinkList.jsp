<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.dms.service.KETDmsHelper,
                e3ps.dms.entity.KETDocumentCategory,
                e3ps.dms.entity.KETProjectDocument,
                e3ps.dms.entity.KETDocumentCategoryLink,
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
                java.sql.Connection,
                e3ps.dms.dao.DocumentDao,
                e3ps.common.util.PlmDBUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.DateUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

    KETProjectDocument docu = null;
    Hashtable tempHash = new Hashtable();
    int i;
    Connection conn = null;

    String cmd = request.getParameter("command");
    if(cmd == null){
        cmd = "search";
    }
    String documentNo = StringUtil.checkNull(request.getParameter("number") );
    String searchCreator = StringUtil.checkNull(request.getParameter("creator") );

    String divisionCode = "";
    if(CommonUtil.isMember("전자사업부")){
        divisionCode = "ER";
    }else if(CommonUtil.isMember("자동차사업부")){
        divisionCode = "CA";
    }else{
        divisionCode = "0";
    }
    String sessionidstring = request.getParameter("sessionid");
    if(sessionidstring==null)sessionidstring = "0";
    long sessionid = Long.parseLong(sessionidstring);

    String categoryCode = StringUtil.checkNull(request.getParameter("docTypeOid") );
    if ( categoryCode.equals("") ) {
        categoryCode = StringUtil.checkNull(request.getParameter("categoryCode") );
    }
    String documentName = StringUtil.checkNull(request.getParameter("name") );

    String authorStatus = "APPROVED";
    //WTUser wtuser = (WTUser)SessionHelper.getPrincipal();
    String creatorName = "";
    String creator = "";
    //searchCreator

    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;
    int peopleClassPos = spec.addClassList(peopleClass,true);
    if  ( spec.getConditionCount() > 0 ) spec.appendAnd();
    spec.appendWhere(new SearchCondition(peopleClass,People.NAME,SearchCondition.EQUAL, searchCreator),new int[]{peopleClassPos});
    QueryResult qr = PersistenceHelper.manager.find(spec);
    if(qr.hasMoreElements()){
        Object[] obj = (Object[])qr.nextElement();
        PeopleData data = new PeopleData(obj[0]);
        creator = data.id;
        creatorName= data.name;
    }

    String predate =StringUtil.checkNull( request.getParameter("predate") );
    String postdate = StringUtil.checkNull(request.getParameter("postdate") );
    String isBuyerSummit = "0";
    String buyerCodeStr = StringUtil.checkNull(request.getParameter("buyerCodeStr"));
    String islastversion = "1";
    String quality = StringUtil.checkNull(request.getParameter("quality"));
    String sortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String sortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));

    sortKeyD=StringUtil.checkNull(sortKeyD);
    if(sortKeyD.equals("ASC")){
        sortKeyD="DESC";
    }else{
        sortKeyD="ASC";
    }
     documentName = documentName.replace("*", "%");
    documentNo = documentNo.replace("*", "%");

    tempHash.put("documentNo" , new String(documentNo));

    if(divisionCode.equals("0")){
        tempHash.put("divisionCode" , "");
    }else{
        tempHash.put("divisionCode" , new String(divisionCode));
    }

    tempHash.put("categoryCode" , new String(categoryCode));
    tempHash.put("documentName" , new String(documentName));
    tempHash.put("authorStatus" , new String(authorStatus));
    tempHash.put("creator" , new String(creator));
    tempHash.put("predate" , new String(predate));
    tempHash.put("postdate" , new String(postdate));
    tempHash.put("isBuyerSummit" , new String(isBuyerSummit));
    tempHash.put("buyerCodeStr" , new String(buyerCodeStr));
    tempHash.put("quality" , new String(quality));
    tempHash.put("islastversion" , new String(islastversion));
    tempHash.put("sortKey" , new String(sortKey));
    tempHash.put("sortKeyD" , new String(sortKeyD));
    tempHash.put("locale", messageService.getLocale());

    Vector docuOids = DMSUtil.serDocumentList(tempHash);


    String docuOid;

   if(docuOids.size()>0){
       for(i = 0; i < docuOids.size(); i++) {
           docuOid = docuOids.get(i).toString();
              docu = (KETProjectDocument)CommonUtil.getObject(docuOid);
           }
   }



   int totalRecord=0;
   int numPerPage= 10;
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
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
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
function fncSort(order){
    var fm;
    var tb;
    var ary;
    var trs;

    fm  = document.form01;
    tb  = document.getElementById('docuSearch');
    trs = tb.childNodes[0].childNodes;

    //alert("trs[1].childNodes[1].innerText=" + trs[1].childNodes[1].innerText);
    //alert("trs[1].childNodes[2].innerText=" + trs[1].childNodes[2].innerText);
    //alert("trs[2].childNodes[1].innerText=" + trs[2].childNodes[1].innerText);
    //alert("trs[2].childNodes[2].innerText=" + trs[2].childNodes[2].innerText);

    alert(trs.length);
    alert(trs[0].childNodes.length);

    ary = new Array(trs.length);

    for(var i=0; i<trs.length; i++){

        for(var i=0; i<trs[0].childNodes.length; i++){
          ary.push(trs[i].childNodes[1].innerText);
          //alert(trs[i].childNodes[1].innerText);
        }
    }

    ary.sort();

    if(order==1){
        for(var j=0; j<trs.length; j++){
            trs[j].childNodes[1].innerText = ary[j];
        }
    }else{
        var pos = 0;
        for(var j=(trs.length-1); j>=0; j--){
           trs[j].childNodes[1].innerText = ary[pos++];
        }
    }
}

function ViewDocumentPop(oid){
    var url="/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid;
    openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
}

function ViewDocument(oid){
    document.location.href="/plm/jsp/dms/ViewDocument.jsp?oid="+oid;
}

function gotoPage(p){
    document.forms[0].sessionid.value='<%=sessionid%>';
    document.forms[0].tpage.value=p;
    document.forms[0].submit();
}

-->
</script>
</head>
<body>
<form name=form01 >
<input type=hidden name=partOid value="0">

<table border="0" cellpadding="0" cellspacing="0" width="700">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="700">
<tr>
    <td class="tab_btm2"></td>
</tr>
</table>


<table id="docuSearch" border="0" cellspacing="0" cellpadding="0" width="700">
<tr>
      <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
      <td width="240" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
      <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
      <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
      <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
      <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
      <td width="80" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
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


     //for(i = 0; i < docuOids.size(); i++) {

     for (i=beginPerPage; i<(beginPerPage+numPerPage); i++){
           if (i==totalRecord){break;}

           //docu = (KETProjectDocument)docuOids.get(i);

           docuOid = docuOids.get(i).toString();
        docu = (KETProjectDocument)CommonUtil.getObject(docuOid);
        Kogger.debug("docuOid ===>" + CommonUtil.getOIDString(docu));

           r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
        if (r.hasMoreElements()){
            docCate = (KETDocumentCategory) r.nextElement();
            //String cateCode=docCate.getCategoryCode();
            docCatePath=docCate.getCategoryName();
            //docCatePath= KETDmsHelper.service.selectCategoryPath(cateCode);
            //docCatePath=docCatePath.substring(1);
        }

        versionInfo = docu.getVersionIdentifier().getValue() + "." + docu.getIterationIdentifier().getValue();

        lifeCycleState = docu.getLifeCycleState().getDisplay();
        //lifeCycleState=lifeCycleState.substring(0,3);
        insUser = docu.getCreator().getFullName();
           //insDate = DateUtil.getTimeFormat(docu.getCreateTimestamp(),"yyyy-MM-dd");
           insDate = DateUtil.getDateString(docu.getCreateTimestamp(),"D");
           //DateUtil.getDateString(projectData.pjtPlanStartDate,"D")
        //문서객체로 주첨부파일의 정보를 가져온다.

        holder = (FormatContentHolder)docu;
        info = ContentUtil.getPrimaryContent(holder);
        ctf=(ContentItem)CommonUtil.getObject(info.getContentOid());
        appDataOid = ctf.getPersistInfo().getObjectIdentifier().getStringValue();
        //urlpath = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
        urlpath = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + ctf.getRole().toString();
        //urlpath = "<a href=" + urlpath + " target='_blank'>" + info.getName() + "</a>&nbsp;(&nbsp;" + info.getFileSize() + "&nbsp;)";
        iconpath = info.getIconURLStr();
        iconpath = iconpath.substring(iconpath.indexOf("<img"));
        urlpath = "<a href=" + urlpath + " target='_blank'>" + iconpath;

        String isBuyerSummitL = docu.getIsBuyerSummit();

%>
<tr>
  <td width="90" class="tdwhiteM"><a href="#" onClick="javascript:ViewDocumentPop('<%=CommonUtil.getOIDString(docu)%>');" ><%=docu.getDocumentNo()%></a></td>
  <td width="170" class="tdwhiteL" title="<%=docu.getTitle()%>"><div style="width:170;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
  <nobr><%=docu.getTitle()%></nobr></div></td>
  <td width="50" class="tdwhiteM"><%=lifeCycleState%></td>
  <td width="35" class="tdwhiteM"><%=versionInfo%></td>
  <td width="55" class="tdwhiteM"><%=insUser%></td>
  <td width="70" class="tdwhiteM"><%=insDate%></td>
  <td width="80" class="tdwhiteL0">&nbsp;
  <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' onClick="javascript:parent.onSave('<%=docuOid%>');" class="s_font">
  </td>
<%
     }
 }
%>

</tr>

</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
  <td class="space10"></td>
</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td class="small" align="left">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> : <%=totalPage%> )</td>
  <td><table border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="40" align="center">
<%
   if(totalRecord !=0){
      if(nowBlock > 0) {
%>
        <a href='ProjectOutputResultLinkList.jsp?nowBlock=<%=nowBlock - 1 %>&page=<%=((nowBlock-1) * pagePerBlock)%>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&isBuyerSummit=<%=isBuyerSummit%>&buyerCodeStr=<%=buyerCodeStr%>&quality=<%=quality%>&islastversion=<%=islastversion%>&sortKey=<%=sortKey%>' class='small'><img src='../../portal/images/btn_arrow4.gif' style='border:0'></a>
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
        <a href='ProjectOutputResultLinkList.jsp?nowBlock=<%=nowBlock%>&page=<%=(nowBlock * pagePerBlock)+ i %>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&isBuyerSummit=<%=isBuyerSummit%>&buyerCodeStr=<%=buyerCodeStr%>&quality=<%=quality%>&islastversion=<%=islastversion%>&sortKey=<%=sortKey%>'><%=(nowBlock * pagePerBlock) + i + 1 %></a>
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
        <a  href='ProjectOutputResultLinkList.jsp?nowBlock=<%=nowBlock + 1%>&page=<%=((nowBlock + 1) * pagePerBlock) %>&cmd=<%=cmd%>&documentNo=<%=documentNo%>&divisionCode=<%=divisionCode%>&categoryCode=<%=categoryCode%>&documentName=<%=documentName%>&authorStatus=<%=authorStatus%>&creatorName=<%=creatorName%>&predate=<%=predate%>&postdate=<%=postdate%>&isBuyerSummit=<%=isBuyerSummit%>&buyerCodeStr=<%=buyerCodeStr%>&quality=<%=quality%>&islastversion=<%=islastversion%>&sortKey=<%=sortKey%> ' class='small'><img src='../../portal/images/btn_arrow2.gif' style='border:0'></a>
<%   }
   }
%>
        </td>
      </tr>
    </table></td>
  <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%> : <%=totalRecord%>  )</td>

</tr>
</table>
</form>
</body>
<script>
//self.resizeTo(document.body.scrollWidth , document.body.scrollHeight + 10);
</script>
</html>













