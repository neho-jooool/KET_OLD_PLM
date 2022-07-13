<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="ext.ket.bom.query.KETProjectBomQueryBean"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.Writer"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="ext.ket.shared.util.EjsConverUtil"%>
<%@page import="ext.ket.bom.query.KETBOMQueryBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="e3ps.common.util.DateUtil"%>
<%@ page import="e3ps.common.util.StringUtil"%>
<%@ page import="e3ps.common.web.PageControl"%>
<%@ page import="e3ps.common.util.PropertiesUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    response.addHeader("Cache-Control", "max-age=1, must-revalidate");   

    String pagingList = PropertiesUtil.getSearchPagingSizeList();
    String pagingNameList = PropertiesUtil.getSearchPagingSizeNameList();
    String gridStyle = PropertiesUtil.getSearchGridStyle();
    String cookiesType = PropertiesUtil.getSearchGridCookiesType();
    String alternateType = PropertiesUtil.getSearchGridAlternateType();
    String maxSort = PropertiesUtil.getSearchGridMaxSort();
    String sortIcons = PropertiesUtil.getSearchGridSortIcons();
    String colMinWidth = PropertiesUtil.getSearchColMinWidth();
    String pageSize = PropertiesUtil.getSearchPagingSizeDefault();
    long currentTime = DateUtil.getCurrentTimestamp().getTime();
    String week = "30";
    long weeklater = currentTime-(Long.parseLong(week)*(1000*60*60*24));
    Timestamp weekTime = new Timestamp(weeklater);
    String postdate = DateUtil.getCurrentDateString("d");
    String predate = DateUtil.getDateString(weekTime, "d");
    String invokeMethod = StringUtil.checkNull(request.getParameter("invokeMethod"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>자부품 추가</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
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
<script type="text/javascript">
//전역변수 설정
var pagingList = '<%=pagingList%>';
var pagingNameList = '<%=pagingNameList%>';
var gridStyle = '<%=gridStyle%>';
var cookiesType = '<%=cookiesType%>';
var alternateType = '<%=alternateType%>';
var maxSort = '<%=maxSort%>';
var sortIcons = '<%=sortIcons%>';
var colMinWidth = '<%=colMinWidth%>';
var pageSize = '<%=pageSize%>';
var locale = '<%=messageService.getLocale()%>';
var predate = '<%=predate%>';
var postdate = '<%=postdate%>';
</script>
</head>
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<%
String[] pNums = request.getParameterValues("pNums");
String param = "";
for(int i=0;i<pNums.length;i++){    
    param += "&pNums="+pNums[i];
}
%>
<script type="text/javascript">
function selectPart(){
	var G = Grids[0];
	var arr = new Array();
    var subArr = new Array();
    var idx = 0;
	if( G != null ){
        var R = G.GetSelRows();
        if( R.length == 0 ){
            alert('선택된 부품이 없습니다.');
            return;
        }
        
        for ( var i = 0; i < R.length; i++) {
            subArr = new Array();
            subArr[0] = R[i].oid;
            subArr[1] = R[i].partType;//부품유형
            subArr[2] = R[i].partNo;
            subArr[3] = R[i].partName;
            subArr[4] = R[i].rev;
            subArr[5] = R[i].state; //status
            subArr[6] = R[i].isNewItem; //신규/공용('Y':신규, 'N':공용")            
            subArr[7] = R[i].partTypeCode; //부품유형 코드            
            arr[idx++] = subArr;
        }
    }
	<%  if ( invokeMethod.length() == 0 ) {  %>
	    //modal dialog
	    selectModalDialog(arr);
	<%  } else {  %>
	    //open window
	    selectOpenWindow(arr);
	<%  }  %>

}
function selectModalDialog(arrObj) {
    window.returnValue= arrObj;
    window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function selectOpenWindow(arrObj) {
    //...이하 원하는 스크립트를 만들어서 작업...
    if(opener) {
        if(opener.<%=invokeMethod%>) {
            opener.<%=invokeMethod%>(arrObj);
        }
    }else if(parent.opener) {
        if(parent.opener.<%=invokeMethod%>) {
            parent.opener.<%=invokeMethod%>(arrObj);
        }
    }
    
    self.close();
}

<%  }  %>

function search(perPage){
    if(!perPage) perPage = Grids[0].Source.Layout.Data.Cfg.PageLength;
    Grids[0].Source.Layout.Data.Cfg.PageLength=perPage;
    Grids[0].Source.Data.Url = '/plm/ext/project/product/getChildPartByProducts.do?1=1<%=param%>';
    Grids[0].Source.Data.Params = decodeURIComponent($('[name=ProgramSearchForm]').serialize());
    Grids[0].Source.Data.Param.formPage=perPage
    Grids[0].Reload();
}

$(document).ready(function(){
	var G = TreeGrid(CommonGrid.getGridConfig({
        id : 'ProgramEventGrid',
        Sort : 'productCode',
        perPageOnChange : 'search(Value)',
        Data : {
            Url : '/plm/ext/project/product/getChildPartByProducts.do?1=1<%=param%>',
            Method : 'POST',
            Format : 'Form',
            Param : {
                formPage : (Grids[0])?Grids[0].PageLength:CommonGrid.pageSize
            }
        },
        Cols : [
            {Name : 'productCode', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
            {Name : 'partType', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
            {Name : 'partNo', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
            {Name : 'partName', Width:100, RelWidth : 50, Align : 'Left', CanSort : '1', CanEdit : '0'},
            {Name : 'rev', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'},
            {Name : 'state', Width:80, Align : 'Center', CanSort : '1', CanEdit : '0'}
        ],Header :{
            CanDelete : '0', Align : 'Center', NoColor : '2',
            productCode : '제품품번',//제품품번
            partType : LocaleUtil.getMessage('01595'),//부품유형
            partNo : LocaleUtil.getMessage('01569'),//부품번호
            partName : LocaleUtil.getMessage('01586'),//부품명
            rev : 'Rev',
            state : LocaleUtil.getMessage('00771')//결재상태
        },
        SelectingSingle : '0',
        Panel : {
            Width : '21', Visible : '1',CanHide : '0'
        }
    }),'listGrid');
	
	Grids.OnRenderFinish = function(){
		for ( var tRow = G.GetFirstVisible(); tRow; tRow = G.GetNextVisible(tRow) ) {
			G.SetAttribute(tRow, 'productCode','NoColor','2',1,0);
			G.SetAttribute(tRow, 'partType','NoColor','2',1,0);
			G.SetAttribute(tRow, 'partNo','NoColor','2',1,0);
			G.SetAttribute(tRow, 'partName','NoColor','2',1,0);
			G.SetAttribute(tRow, 'rev','NoColor','2',1,0);
			G.SetAttribute(tRow, 'state','NoColor','2',1,0);
        }
    }
});

</script>
<body>
<%@include file="/jsp/common/processing.html" %>    
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">자부품 추가</td>
                          </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="space5"></td>
    </tr>
    <tr>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;"
                        onClick="javascript:selectPart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="5">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;"
                        onClick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="space5"></td>
    </tr>
    <tr>
        <td>
            <!-- EJS TreeGrid Start -->
            <div class="content-main">
                <div class="container-fluid">
                    <div class="row-fluid">
                        <div id="listGrid" style="WIDTH: 100%; HEIGHT: 520px"></div>
                    </div>
                </div>
            </div>
            <!-- EJS TreeGrid End -->
        </td>
    </tr>
</table>    
</body>
</html>