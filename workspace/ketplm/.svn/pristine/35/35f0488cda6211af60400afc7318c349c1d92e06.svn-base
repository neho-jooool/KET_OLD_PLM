<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
.headerLock6 {
  position: relative;
  top:expression(document.getElementById("div_scroll6").scrollTop);
  z-index:99;
 }

</style>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td valign="top">
        <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01253") %><%--도면--%></td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td  class="tab_btm2"></td>
          </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
          <td width="150" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="100" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td width="250" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td>
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
        </tr>
      </table>
        <div style="width=100%;height=50;overflow-x:hidden;overflow-y:auto;">
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="epmDocList">
                <tr>
                  <td colspan=7 class="tdwhiteM0">&nbsp;</td>
          </tr>
      </table>
      </div>
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="space15"></td>
  </tr>
</table>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03">BOM</td>
    <td align="right">&nbsp;</td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space5"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
    <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
    <td width="95" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01377") %><%--모부품번호--%></td>
    <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
    <td width="95" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02388") %><%--자 부품번호--%></td>
    <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
    <td width="90" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
    <td width="200" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01507") %><%--변경내용--%></td>
  </tr>
  <tr>
    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
    <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
  </tr>
</table>
<div style="height=50;width=100%;overflow-x:auto;overflow-y:auto;">
<table border="0" cellspacing="0" cellpadding="0" width="100%" id="relBomTable">
  <tr>
      <td colspan=9 class="tdwhiteM0">&nbsp;</td>
  </tr>
</table>
</div>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space15"></td>
  </tr>
</table>
<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03255") %><%--후속변경대상문서--%></td>
    <td align="right">&nbsp;</td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space5"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>

<script type="text/javascript">

//대상검색 팝업 호출
function popupRelDoc( flag ) {

var pos = eval('relDocTable').clickedRowIndex;
var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one";
attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:540px; center:yes");
if(typeof attache == "undefined" || attache == null) {
  return;
}

checkDocAjax(attache, pos);
}

function checkDocAjax(objArr, inx){
var trArr = objArr[0];
var url = "/plm/jsp/ecm/CheckDocAjaxAction.jsp?oid="+trArr[0]+"&no="+trArr[1]+"&name="+trArr[2]+"&ver="+trArr[3]+"&inx=" + inx;
callServer(url, checkDocResult);
}

function checkDocResult(req) {
var xmlDoc = req.responseXML;

showElements = xmlDoc.selectNodes("//data_info");
var l_flag = showElements[0].getElementsByTagName("l_flag");
var l_oid = showElements[0].getElementsByTagName("l_oid");
var l_no = showElements[0].getElementsByTagName("l_no");
var l_name = showElements[0].getElementsByTagName("l_name");
var l_ver = showElements[0].getElementsByTagName("l_ver");
var l_inx = showElements[0].getElementsByTagName("l_inx");

var flag = decodeURIComponent(l_flag[0].text);
var oid = decodeURIComponent(l_oid[0].text);
var no = decodeURIComponent(l_no[0].text);
var name = decodeURIComponent(l_name[0].text);
var ver = decodeURIComponent(l_ver[0].text);
var inx = decodeURIComponent(l_inx[0].text);

if ( flag == 'true'){
  alert("<%=messageService.getString("e3ps.message.ket_message", "01862") %><%--설계변경이 진행중인 문서입니다--%>");
  return;
}

 setRelDoc2(oid, no, name, ver, inx);
}

function setRelDoc2(oid, no, name, ver, inx)
{
if(oid.length == 0)
{
  return;
}

var form = document.forms[0];

var targetTable = document.getElementById("relDocTable");

var length = targetTable.rows.length;

 if( length > 1 )
 {
   if( form.relObjOid[inx].value != oid )
   {
     if( form.deleteRelDocList.value == ''  )
     {
         form.deleteRelDocList.value = form.relEcaLinkOid[inx].value;
     }
     else
     {
         form.deleteRelDocList.value +="*" + form.relEcaLinkOid[inx].value;
     }
   }

  form.relObjOid[inx].value = oid;
  form.relEcaDocNo[inx].value =no;
  form.relEcaDocName[inx].value = name;
  form.relObjPreRev[inx].value = ver;
}
else
{
  if( form.relObjOid.value != oid )
  {
    if( form.deleteRelDocList.value == ''  )
     {
         form.deleteRelDocList.value = form.relEcaLinkOid.value;
     }
     else
     {
         form.deleteRelDocList.value +="*" + form.relEcaLinkOid.value;
     }
   }

  form.relObjOid.value = oid;
  form.relEcaDocNo.value =no;
  form.relEcaDocName.value = name;
  form.relObjPreRev.value = ver;
}
}

//변경문서 팝업 호출
function popupRelDoc2( flag, docNo ) {

var inx = eval('relDocTable').clickedRowIndex;
var form = document.forms[0];

var docNo;

var targetTable = document.getElementById("relDocTable");
var length = targetTable.rows.length;
 if( length > 1 ){
   docNo = form.relEcaDocNo[inx].value;
 }else{
   docNo = form.relEcaDocNo.value;
 }

var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/dms/SearchDocumentPop.jsp&obj=method^selectModalDialog&obj=mode^one&obj=isReview^Y&obj=docNo^"+docNo;
attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=500px; dialogHeight:540px; center:yes");
if(typeof attache == "undefined" || attache == null) {
  return;
}

setRelDoc(attache, inx, flag);
}

//문서 검색 팝업 리턴 세팅
function setRelDoc(objArr, inx, flag)
{
if(objArr.length == 0)
{
  return;
}
var trArr = objArr[0];
var form = document.forms[0];

var targetTable = document.getElementById("relDocTable");

var length = targetTable.rows.length;

if( flag == 'p' )
{
   if( length > 1 )
   {
     if( form.relObjOid[inx].value != trArr[0] )
     {
       if( form.deleteRelDocList.value == ''  )
       {
           form.deleteRelDocList.value = form.relEcaLinkOid[inx].value;
       }
       else
       {
           form.deleteRelDocList.value +="*" + form.relEcaLinkOid[inx].value;
       }
     }

    form.relObjOid[inx].value = trArr[0];
    form.relEcaDocNo[inx].value =trArr[1];
    form.relEcaDocName[inx].value = trArr[2];
    form.relObjPreRev[inx].value = trArr[3];
  }
  else
  {
    if( form.relObjOid.value != trArr[0] )
    {
      if( form.deleteRelDocList.value == ''  )
       {
           form.deleteRelDocList.value = form.relEcaLinkOid.value;
       }
       else
       {
           form.deleteRelDocList.value +="*" + form.relEcaLinkOid.value;
       }
     }

    form.relObjOid.value = trArr[0];
    form.relEcaDocNo.value =trArr[1];
    form.relEcaDocName.value = trArr[2];
    form.relObjPreRev.value = trArr[3];
  }
}
else if( flag == 'a' )
{
  if( length > 1 )
  {
    if( form.relEcaDocNo[inx].value == trArr[1] && form.relObjPreRev[inx].value < trArr[3] )
    {
      form.relObjAfterRev[inx].value = trArr[3];
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00580") %><%--같은 문서의 최신버전이 아닙니다--%>");
    }
  }
  else
  {
    if( form.relEcaDocNo.value == trArr[1] && form.relObjPreRev.value < trArr[3] )
    {
      form.relObjAfterRev.value = trArr[3];
    }
    else
    {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00580") %><%--같은 문서의 최신버전이 아닙니다--%>");
    }
  }
}
}

</script>
<div id="div_scroll6" style="height=198;width=100%;overflow-x:auto;overflow-y:auto;" class="table_border">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr class="headerLock6">
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" style=table-layout:fixed >
        <tr>
          <td width="40" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01410") %><%--문서구분--%></td>
          <td width="80" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01745") %><%--상세구분--%></td>
           <td width="90" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
          <td width="150" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
          <td width="100" colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td width="182" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02433") %><%--작업--%></td>
        </tr>
        <tr>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01502") %><%--변경 전--%></td>
          <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01503") %><%--변경 후--%></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
      <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="relDocTable" style=table-layout:fixed>
        <%
        Hashtable<String, String> docHash = null;

        if( docList != null && docList.size() > 0 )
        {
            int docListLength = docList.size();
            for( int docCnt=0; docCnt< docList.size(); docCnt++ )
            {
                docHash = docList.get(docCnt);
        %>
        <tr height="29" onMouseOver='relDocTable.clickedRowIndex=this.rowIndex' >
          <td width="40" class="tdwhiteM"><%=docListLength-- %></td>
          <td width="80" class="tdwhiteM"><%=docHash.get("activity_type_name") %>
              <input type='hidden' name='relEcaActivityType' value='<%=docHash.get("activity_type")%>'>
              <input type='hidden' name='relEcaEpmDocType'  value='<%=docHash.get("obj_type") %>'>
              <input type='hidden' name='relObjOid'  value='<%=docHash.get("eca_obj_oid")%>'>
              <input type='hidden' name='relEcaWorkerOid'  value='<%=docHash.get("worker_id") %>' >
              <input type='hidden' name='relEcaLinkOid' value='<%=docHash.get("eca_obj_link_oid")%>'>
              <input type='hidden' name='relEcaDocOtherTypeDesc' value='<%=docHash.get("doc_type_desc") %>'>
              <input type='hidden' name='viewDocOid' value='<%=EcmUtil.getDocumentOid(docHash.get("eca_obj_no"), docHash.get("eca_obj_pre_rev"))%>'>
          </td>
          <td width="80" class="tdwhiteM"><%=docHash.get("doc_type_desc") %>&nbsp;</td>
          <td width="90" class="tdwhiteM"><input type="text" name="relEcaDocNo" value='<%=docHash.get("eca_obj_no") %>' class="txt_fieldRO"  style="width:90%" readonly></td>
          <td width="150" class="tdwhiteL"><input type="text" name="relEcaDocName" value="<%=docHash.get("eca_obj_name") %>" class="txt_fieldRO"  style="width:95%" readonly></td>
          <td width="50" class="tdwhiteM"><input type="text" name="relObjPreRev" value='<%=docHash.get("eca_obj_pre_rev") %>' class="txt_fieldCRO"  style="width:90%;cursor:hand" readonly onclick="javascript:viewRelDoc();"></td>
          <td width="50" class="tdwhiteM"><input type="text" name="relObjAfterRev" value='<%=docHash.get("eca_obj_after_rev") %>' class="txt_fieldCRO"  style="width:90%" readonly></td>
          <td width="182" class="tdwhiteM0">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="*"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelDoc('p');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01229") %><%--대상검색--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
                <td width="2">&nbsp;</td>
                <td width="*"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelDoc2('a');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01509") %><%--변경문서--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                </table></td>
                </tr>
            </table>
          </td>
        </tr>
        <%
            }
        }
        %>
      </table>
    </td>
  </tr>
</table>
</div>
