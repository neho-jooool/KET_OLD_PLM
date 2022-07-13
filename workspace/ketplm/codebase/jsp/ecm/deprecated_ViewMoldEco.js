 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
    openWindow(url, '',1050,800);
}

//탭전환
function clickTabBtn(tabId) {
    var tabBasic = document.getElementById("tabBasic");
    var tabActivity = document.getElementById("tabActivity");
    if(tabId == 1) {
        tabBasic.style.visibility = "visible";
        tabActivity.style.visibility = "hidden";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "block";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "none";
    } else {
        tabBasic.style.visibility = "hidden";
        tabActivity.style.visibility = "visible";

        var infoShow = document.getElementById("infoShow");
        infoShow.style.display = "none";
        var infoHide = document.getElementById("infoHide");
        infoHide.style.display = "block";
    }
}

//처리중 화면 전환
function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
}

function callUpdate(){
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "updateview";
    document.forms[0].target = "_self";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//목록
function callSearch(){
//	var url = "/plm/jsp/ecm/SearchEcoForm.jsp";
//	document.forms[0].cmd.value = "search";
//	document.forms[0].target = "contName";
//	document.forms[0].action = url;
//	document.forms[0].method = "post";
//	document.forms[0].submit();

     if( document.forms[0].prePage.value == 'Search' )
     {
        history.back();
    }
    else
    {
        var url = "/plm/jsp/ecm/SearchEcoForm.jsp";
        document.forms[0].cmd.value = "search";
        document.forms[0].target = "contName";
        document.forms[0].action = url;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
}

//자료 취소
function doCancel(){
    document.forms[0].reset();
}

//자료 취소
function doDelete(){
    if(!confirm("삭제 하시겠습니까?")) {
        return;
    }
    disabledAllBtn();
    showProcessing();
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "delete";
    document.forms[0].target = "download";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//부품 상세조회 팝업
function viewPart(v_poid){
    var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
    openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
    var url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + oid;
    openWindow2(url,"","800","680","scrollbars=auto,resizable=no,top=200,left=250,center=yes");
}

//제품ECO 상세조회 팝업
function viewRelProdEco(oid){
    var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","800","750","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//금형ECO 상세조회 팝업
function viewRelMoldEco(oid){
    var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
    openWindow(url,"","800","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function viewRelDoc(oid)
{
    var url = "/plm/jsp/dms/ViewDocumentPop.jsp?oid="+oid+"&isRefresh=N";
    openWindow(url,"","800","640","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

function viewEpmDocPopup(oid)
{
    var url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + oid;
    openWindow2(url,"","820","800","toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,status=yes,resizable=1,top=100,left=100");
}

//등록완료
function doComplete( flag ) {
    disabledAllBtn();
    showProcessing();
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    if( flag )
    {
        form.hasECA.value = 'Y';
    }
    else
    {
        form.hasECA.value = 'N';
    }
    form.cmd.value = "complete";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';
    form.submit();
}

function popupStdPart( docLinkOid, docNo )
{
   var form = document.forms[0];
    var url = "/plm/servlet/e3ps/MoldStdPartServlet?pop=Y&docLinkOid="+docLinkOid+"&docNo="+docNo+"&ecoOid="+form.oid.value+"&ecoNo="+form.ecoId.value;

    var height	= screen.height;
    var width	= screen.width;
    var leftpos = width/2 - 200/2;
    var toppos	= height/2 - 400/2;
    if(leftpos<0) leftpos=0;
    if(toppos<0) toppos=0;

    window.open('', "SEARCHREQ", "status=no,  width=650, height=500, resizable=no, scrollbars=no, statusbar=no, left="+leftpos+", top="+toppos);

    document.forms[0].action= url ;
    document.forms[0].target="SEARCHREQ";
    document.forms[0].method= "post"
    document.forms[0].submit();
}

function doEpmDocExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "epmExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}

function doBomExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "bomExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}

function doDocExcel()
{
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    var form = document.forms[0];

    form.cmd.value = "stdPartExcel";
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.submit();
}
