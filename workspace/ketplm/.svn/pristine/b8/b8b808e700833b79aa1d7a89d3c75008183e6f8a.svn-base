<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="toDay" class="java.util.Date" />
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<style>
.headerDiv{text-align:center;line-height:18px;}
</style>
<script type="text/javascript">
var mappingRow = null;
var delFlag = "1";
var searchIcon          = "/plm/portal/images/icon_5.png";

$(document).ready(function(e) {
	
	$(document).attr("title","커넥터 대체품 관리");
	$(document).bind("contextmenu", function(e){
        return false;
    });
	
	$('#pole').number( true );
	
    CommonUtil.singleSelect('companyCode',100);
    CommonUtil.singleSelect('waterProof',100);
    CommonUtil.singleSelect('mfType',100);
    
    if('${rival.newPartNo}' != ''){
    	$("#partNo").val('${rival.newPartNo}');	
    }
    
    $("#waterProof").val('${rival.waterProof}');
    $("#waterProof").multiselect('refresh');
    $("#mfType").val('${rival.mfType}');
    $("#mfType").multiselect('refresh');
    
    var grid = TreeGrid({
        Debug : 0,
        Upload : {
            Url : "/plm/ext/replacePart/saveReplacePart",
            Format : "JSON",
            Flags : "AllCols",
            Data : "treeData",
            Type : "Changes,Body",
            Param : {
                formdata : ""
            },
        },
        Data : {
            Url : '/plm/ext/replacePart/replacePartGridData',
            Method : 'POST',
            Format : 'JSON',
            Param : {
                //partListOid : "${partListOid}",
                DATATYPE : "REPLACEPARTLISTPOPUP",                
                partNo : $("#partNo").val(),
            }
        },
        Layout : {
            Data : {
                Cfg : {
                    id : "replacePartPopup",
                    Style : gridStyle,
                    SuppressCfg : cookiesType,
                    IdPrefix : "T",
                    IdChars : "0123456789",
                    NumberId : 1,
                    Undo : 1,
                    Sorting : 0,
                    Editing : 1,
                    Deleting : 1,
                    ShowDeleted : 0,
                    Selecting : 0,
                    CopySelected : 1,
                    CopyPasteTree : 1,
                    Pasting : 0,
                    Dragging : 1,
                    Dropping : 1,
                    ExportFormat : 1,
                    ExportType : "Filtered,Hidden,Strings,Dates",
                    SaveSession : 1,
                    UsePrefix : 1,
                    Alternate : 0,
                    MainCol : "name",
                    SuppressMessage : '1',
                    /* 
                    ConstHeight : '1',ConstWidth : '1',
                    MaxHeight : '1', MaxWidth : '1',
                    MinHeight : '100', MinTagHeight : '100'
                     */
                },
                Toolbar : { Visible : 0 }, //하단 도구 비표시 처리
                Panel : {
                    Visible : 1
                },
                
                Cols : [
                        {Name : 'deleteFlag', CanEdit : 0, Visible : 0},
                        {Name : 'ketPartOid',       CanEdit : 0, Visible : 0},
                        {Name : 'replaceGb',        Width:75,  Align : 'left', Type : "Enum", Enum : "|O|△|X", Range : 0 },
					    {Name : 'ketPartNo',        Width:75,  Align : 'Center', CanSort : '1', CanEdit : '0' ,CanEdit : '0'},
                        {Name : 'ketPartName',      Width:100, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
                        {Name : 'ketWaterProof',     Align : "Center" ,Width : 55, CanSort : '0' ,CanEdit : '0'},
                        {Name : 'classification',     Width:80,Align : "Center", CanSort : '0' ,CanEdit : '0'},
                        {Name : 'ketPole',         Align : "Center",Width : 40  , CanSort : '0' ,CanEdit : '0'},
                        {Name : 'ketSeries',    Width:80, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
                        {Name : 'ketMatchTerminal',   Width : 80    ,LEVEL : 1, Align : "Center" , CanSort : '0' ,CanEdit : '0'},
                        {Name : 'ketMatchConnector',      Width:80,     Align : "Center" , CanSort : '0' ,CanEdit : '0'},
                       ],
                       
                Head : [{
                        Kind : "Topbar",
                        Cells : "",
                        AddCopyMenu : "",
                        AddChildCopyMenu : "",
                        Styles : 2,
                        Calculated : 1,
                        Link : 0,
                    },{
                        Kind : "Header",
                        id : "Header",
                        RowSpan : 0,
                        Visible : 0
                    },{
                        Kind : "Header",
                        id : "Header1",
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        
                        replaceGb : "Replace\r\n(O /△ /X)",
                        ketPartNo : 'KET품번',
                        ketPartName : "KET품명",
                        ketWaterProof : "방수여부",
                        classification : "분류체계",    
                        ketPole : "극수\r\n(P)",  
                        ketSeries : "시리즈",
                        ketMatchTerminal : "매칭터미널",
                        ketMatchConnector : "매칭커넥터",
                    }
                ],
                Body : [ { Pos : 0 }],
                Lang : {
                    MenuCopy : {
                        CopyRow       : '위에 아이템 복사'
                        ,CopyRowBelow  : '아래에 아이템 복사'
                        ,CopyTree      : '위에 Tree 복사'
                        ,CopyTreeBelow : '아래에 Tree 복사'
                        ,AddRow        : '위에 신규 아이템 추가'
                        ,AddRowBelow   : '아래에 신규 아이템 추가'
                        ,AddChild      : '첫번째 하위 아이템 추가'
                        ,AddChildEnd   : '마지막 하위 아이템 추가'
                        <%-- 
                        CopyRow       : '<%=messageService.getString("e3ps.message.ket_message", "02230") %>'       //위에 아이템 복사
                        CopyRowBelow  : '<%=messageService.getString("e3ps.message.ket_message", "02070") %>'       //아래에 아이템 복사
                        CopyTree      : '<%=messageService.getString("e3ps.message.ket_message", "02231") %>'       //위에 Tree 복사
                        CopyTreeBelow : '<%=messageService.getString("e3ps.message.ket_message", "02071") %>'       //아래에 Tree 복사
                        AddRow        : '<%=messageService.getString("e3ps.message.ket_message", "02232") %>'       //위에 신규 아이템 추가
                        AddRowBelow   : '<%=messageService.getString("e3ps.message.ket_message", "02072") %>'       //아래에 신규 아이템 추가
                        AddChild      : '<%=messageService.getString("e3ps.message.ket_message", "02801") %>'       //첫번째 하위 아이템 추가
                        AddChildEnd   : '<%=messageService.getString("e3ps.message.ket_message", "01356") %>'       //마지막 하위 아이템 추가
                         --%>
                    },
                    Alert : {
                        DelRow : "부품을 삭제하시겠습니까?"        //아이템을 삭제하시겠습니까?
                        <%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"      //아이템을 삭제하시겠습니까? --%>
                    }
                }
            }
        }
    }, "listGrid");
});


window.addRow= function(){
	var G = Grids[0];
	var newrow = G.AddRow(null, null, true);
    G.SetValue(newrow, "ketPartNoIcon", searchIcon, 1);
    G.SetValue(newrow, "ketPartNoIconAlign", "Right", 1);
    G.SetValue(newrow, "ketPartNoOnClickSideIcon", "javascript:openPartPopup(Row);", 1);
    G.SetValue(newrow, "deleteFlag", false, 1);
    G.RefreshRow(newrow);
    
}

window.openPartPopup = function(row){
    mappingRow = row;
    //showProcessing();
    SearchUtil.selectOnePart('selectPartAfterFunc','pType=F,H');
    //SearchUtil.selectCostPartType(loadCostPartType,'onlyLeaf=Y&openAll=N');
}

window.selectPartAfterFunc = function(objArr ){
    //hideProcessing();
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    
    for(var i = 0; i < objArr.length; i++){
        window.console.log(objArr[i]);
        trArr = objArr[i];
    }
    
    
    var ketWaterProof = ""; 
    var classification = "";    
    var ketPole = "";  
    var ketSeries = "";
    var ketMatchTerminal = "";
    var ketMatchConnector = "";
    
    if(trArr[20] != null){
    	ketWaterProof = trArr[20];
    }
    
    if(trArr[21] != null){
    	classification = trArr[21];
    }
    
    if(trArr[22] != null){
    	ketPole = trArr[22];
    }
    
    if(trArr[23] != null){
    	ketSeries = trArr[23];
    }
    
    if(trArr[24] != null){
    	ketMatchTerminal = trArr[24];
    }
    
    if(trArr[25] != null){
    	ketMatchConnector = trArr[25];
    }
    
    Grids[0].SetValue(mappingRow, "ketPartNo", trArr[1], 1);
    Grids[0].SetValue(mappingRow, "ketPartName", trArr[2], 1);
    Grids[0].SetValue(mappingRow, "ketWaterProof", ketWaterProof, 1);
    Grids[0].SetValue(mappingRow, "classification", classification, 1);
    Grids[0].SetValue(mappingRow, "ketPole", ketPole, 1);
    Grids[0].SetValue(mappingRow, "ketSeries", ketSeries, 1);
    Grids[0].SetValue(mappingRow, "ketMatchTerminal", ketMatchTerminal, 1);
    Grids[0].SetValue(mappingRow, "ketMatchConnector", ketMatchConnector, 1);
}

Grids.OnAfterSave = function ( G, result, autoupdate){
    
    try{
        
         if( result == 0 ){
             
             alert('저장되었습니다');
             
         }else{
             
             alert("저장에 실패하였습니다\n관리자에게 문의하시기 바랍니다");
               //window.location.reload();
           }

       } catch (e) {
           alert(e.message);
       };
}


Grids.OnRowDelete = function(G, row, type) {

    try{
        G.SetValue(row, "deleteFlag", delFlag, 1);
    }catch(e){ alert(e.message);};
}

gridSave = function(){
	if(!confirm("저장하시겠습니까?")){
		return;
	}
    var G = Grids[0];
    G.Save();
}

Grids.OnSave = function(G, row, autoupdate) {
    
	if($("#partName").val() == ''){
		alert("PartName은 필수입력항목입니다.");
		$("#partName").focus();
		return true;
	}
	
	if($("#companyCode").val() == null){
        alert("경쟁사는 필수입력항목입니다.");
        $("#companyCode").focus();
        return true;
    }
	
	
	
     var rows = G.Rows;
     var rowKeys = Object.keys(rows);
     
     try{
    	 
          
         //autoupdate = false;
         
         var message = "";
         var dupRows = {};
         
         //색상 초기화
         for(var i = 0; i < rowKeys.length; i++){
             var aId = rowKeys[i];
             var aRow = rows[aId];
             if("Data" == aRow.Kind) G.SetAttribute(aRow, "ketPartNo", "Color", "", 1);
         }
         
         //전체 ROWS 체크
         F1 : for(var i = 0; i < rowKeys.length; i++){
             
             var aId = rowKeys[i];
             var aRow = rows[aId];
             
             if("Data" == aRow.Kind){
                 
                 if(aRow.deleteFlag != delFlag){//삭제되지 않은 행에 대해 필수입력 값 체크
                     
                     if ( aRow.ketPartNo == null || (aRow.ketPartNo + "").trim() == "" ) {
                         message = "KET품번을 입력하십시오.";
                         dupRows.aRow = aRow;
                         break;
                     }
                     
                     if ( aRow.replaceGb == null || (aRow.replaceGb + "").trim() == "" ) {
                    	 message = "Replace를 선택하십시오.";
                         dupRows.aRow = aRow;
                         break;
                     }
                     
                     for(var j = 0; j < rowKeys.length; j++){
                         
                         var bId = rowKeys[j];
                         var bRow = rows[bId];
                         
                         if("Data" == bRow.Kind && bRow.deleteFlag != delFlag && aId != bId){//삭제되지 않은 행에 대해 중복 체크
                             
                             aTemp = aRow.ketPartNo;
                             
                             bTemp = bRow.ketPartNo;
                             
                             if(aTemp == bTemp){
                                 dupRows.aRow = aRow;
                                 dupRows.bRow = bRow;
                                 message = "중복되는 행이 있습니다.";
                                 break F1;
                             }
                         }
                     }
                 }
             }
         }
         
         //오류 발생시 색상 표기 및 경고창 표시
         if(message != ""){
             G.SetAttribute(dupRows.aRow, "", "Color", "#FCD", 1);
             if(dupRows.bRow != null) G.SetAttribute(dupRows.bRow, "", "Color", "#FCD", 1);
             alert(message);
             return true;
         }
         
         G.Data.Upload.Param.formdata = formParamSet();
         
      }catch(e){ 
          alert(e.message); 
         //색상 초기화
         for(var i = 0; i < rowKeys.length; i++){
             var aId = rowKeys[i];
             var aRow = rows[aId];
             if("Data" == aRow.Kind) G.SetAttribute(aRow, "ketPartNo", "Color", "", 1);
         }
         return true;
      };
}

window.formParamSet = function(){
    var arr = $('[name=replacePartForm]').serializeArray();
    var paramObj = {};
    if (arr) {
        $.each(arr, function() {
            paramObj[this.name] = this.value;
        });
    }
    return(JSON.stringify(paramObj));
}

</script>
<c:if test="${not empty ERROR}">
<script>alert("${ERROR}");self.close();</script>
</c:if>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">커넥터 대체품 관리</td>
<!--                                 <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Dashboard
                                <img src="/plm/portal/images/icon_navi.gif">Report
                                <img src="/plm/portal/images/icon_navi.gif">프로젝트 주요일정</td> -->
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:gridSave();" class="btn_blue">저장</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
            </table>
            <!-- [END] button -->
            <!-- [START] search condition -->
            <form name="replacePartForm">
                <input type="hidden" name="rivalPartOid" id="rivalPartOid" value="${rival.rivalPartOid}">
                <!-- 검색영역 collapse/expand -->
                <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <colgroup>
                        <col width="6%" />
                        <col width="*" />
                        <col width="6%" />
                        <col width="*" />
                        <col width="6%" />
                        <col width="*" />
                        <col width="6%" />
                        <col width="*" />
                        <col width="6%" />
                        <col width="*" />
                    </colgroup>
                    <tr>
                        <td class="tdblueL" style="width: 90px;">경쟁사 PartNo</td>
                        <td class="tdwhiteL"><input type="text" name="partNo" id="partNo" class="txt_fieldRO" style="width:90%;" readonly value="${rival.partNo}"></td>
                        <td class="tdblueL" style="width: 90px;">경쟁사 PartName</td>
                        <td class="tdwhiteL"><input type="text" name="partName" id="partName" class="txt_field" style="width:90%;" value="${rival.partName}"></td>
                        <td class="tdblueL">경쟁사</td>
                        <td class="tdwhiteL">
                            <ket:select id="companyCode" name="companyCode" className="fm_jmp" style="width:130px;" codeType="SALESCOMPETITOR" multiple="multiple" useCodeValue="true" value="${rival.companyCode}"/>
                        </td>
                        <td class="tdblueL" style="width: 90px;">방수여부</td>
                        <td class="tdwhiteL">
                            <select id="waterProof" name="waterProof" class="fm_jmp" multiple='multiple'>
                            <option value="방수" >방수</option>
                            <option value="비방수" >비방수</option>
                            </select>
                        </td>
                        <td class="tdblueL" style="width: 90px;">Male/Female</td>
                        <td class="tdwhiteL">
                        <select id="mfType" name="mfType" class="fm_jmp" multiple='multiple'>
                            <option value="M" >M</option>
                            <option value="F" >F</option>
                            </select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL" style="width: 90px;">극수</td>
                        <td class="tdwhiteL"><input type="text" name="pole" id="pole" class="txt_field" style="width:70%;" value="${rival.pole}"></td>
                        <td class="tdblueL">시리즈</td>
						<td class="tdwhiteL"><input type="text" name="seriesName" id="seriesName" class="txt_fieldRO" style="width: 70%" readonly value="${rival.seriesName}">
						<input type="hidden" name="seriesCode" id="seriesCode" value="${rival.seriesCode}"> <a href="javascript:SearchUtil.selectOneSpSeries('seriesCode','seriesName');">
						<img src="/plm/portal/images/icon_5.png" border="0"></a> <a href="javascript:CommonUtil.deleteValue('series','seriesName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
						</td>
                        <td class="tdblueL" style="width: 90px;">Terminal PartNo</td>
                        <td class="tdwhiteL"><textarea name="matchTerminal" id="matchTerminal" style="width:100%" rows="5" class="fm_area">${rival.matchTerminal}</textarea></td>
                        <td class="tdwhiteL" colSpan="4"></td>
                    </tr>
                    <tr>
                        <td class="tdblueL" style="width: 90px;">비고</td>
                        <td class="tdwhiteL" colspan='10'><textarea name="bigo" id="bigo" style="width:100%" rows="7" class="fm_area">${rival.bigo}</textarea></td>
                    </tr>
                </table>
            </form>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                            <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                   <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                            onclick="javascript:addRow();" class="btn_blue">KET부품추가</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                   </tr>
                               </table>
                            </td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                         </div>
                         <!-- EJS TreeGrid End -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              