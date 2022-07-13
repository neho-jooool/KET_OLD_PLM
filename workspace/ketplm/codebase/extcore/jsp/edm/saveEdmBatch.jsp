<%@page import="java.util.*"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setDateHeader("Expires", 0); // Proxies.
%>
<link rel="stylesheet" href="/plm/extcore/css/custom-new.css" type="text/css">

<style>
.file_input label {
    position:relative;
    cursor:pointer;
    display:inline-block;
    vertical-align:middle;
    overflow:hidden;
    width:100px;
    height:30px;
    background:#777;
    color:#fff;
    text-align:center;
    line-height:30px;
    margin-right: 10px;
    float:left !important;
}
.file_input label input {
    position:absolute;
    width:0;
    height:0;
    overflow:hidden;
}
.file_input input[type=text] {
    vertical-align:middle;
    display:inline-block;
    width:400px;
    height:28px;
    line-height:28px;
    font-size:11px;
    padding:0;
    border:0;
    border:1px solid #777;
}
#fileDragDesc {
            width: 100%; 
            height: 100%; 
            margin-left: auto; 
            margin-right: auto;
            margin-top: auto;  
            padding: 5px; 
            text-align: center; 
            line-height: 450px; 
            vertical-align:middle;
            color: blue;
}

#decNotice {
            width: 100%; 
            height: 30%; 
            margin-left: auto; 
            margin-right: auto;
            margin-top: auto;  
            padding: 30px; 
            text-align: center; 
            line-height: 30px; 
            vertical-align:middle;
            color: red;
            font-size:13px;
            position : relative;
            z-index:-1;
}


</style>

<script type="text/javascript" src="/plm/extcore/js/invest/invest.js?ver=0.3"></script>
<script type = "text/javascript" src="/plm/portal/js/jsAjax.js"></script>
<script>
var categoryMapping1 = {};
categoryMapping1.code = "INJECTION_MOLD_DRAWING";
categoryMapping1.value = "UG|Unigraphics,ACAD|AutoCAD,EXCESS|EXCESS";

var categoryMapping2 = {};
categoryMapping2.code = "PRESS_MOLD_DRAWING";
categoryMapping2.value = "ACAD|AutoCAD,EXCESS|EXCESS";

var categoryMapping3 = {};
categoryMapping3.code = "INJECTION_MOLD_SET_DRAWING";
categoryMapping3.value = categoryMapping2.value;

var categoryMapping4 = {};
categoryMapping4.code = "PRESS_MOLD_SET_DRAWING";
categoryMapping4.value = categoryMapping2.value;


var categoryMappingArr = new Array();
categoryMappingArr.push(categoryMapping1);
categoryMappingArr.push(categoryMapping2);
categoryMappingArr.push(categoryMapping3);
categoryMappingArr.push(categoryMapping4);

//파일 리스트 번호
var fileIndex = 0;
// 등록할 전체 파일 사이즈
var totalFileSize = 0;
// 파일 리스트
var fileList = new Array();
// 파일 사이즈 리스트
var fileSizeList = new Array();
// 등록 가능한 파일 사이즈 MB
var uploadSize = 50;
// 등록 가능한 총 파일 사이즈 MB
var maxUploadSize = 1000;

var fileCheckMsg = "";

$(document).ready(function(){
    
/*  $("#input_file").bind('change', function() {
        publicFiles = this.files;
        fileAddByButton();
    }); */
    
    $("#input_file").change(function(){
        
        for (var i = 0; i < this.files.length; i++) {
            selectFile(this.files[i]);
        }
        if(fileCheckMsg != ""){
            alert(fileCheckMsg);
        }
       
        fileCheckMsg = "";
        FileAddend();
        setDropZoneHeight();
    });
    
    CommonUtil.singleSelect('businessType',150);
    CommonUtil.singleSelect('security',150);
    CommonUtil.singleSelect('devStage',150);
    CommonUtil.singleSelect('category',150);
    CommonUtil.singleSelect('cadAppType',150);
    $("#businessType").val("${businessType}");
    $("#businessType").multiselect('refresh');
    //$("#businessType").multiselect("disable");
    
    $("#category").change(function(){
        cadAppTypeSetting($(this).val());
    });
    
    
    window.cadAppTypeSetting = function(category){
        
        $("#cadAppType").multiselect("uncheckAll");
        $("#cadAppType").empty().data('options');
        $("#cadAppType").append("<option value=''>"+"<%=messageService.getString("e3ps.message.ket_message", "01802") %>"+"</option>");
        
        categoryMappingArr.forEach(function(item) {
            var code = item.code;
            
            if(code == category){
                var codeArr = item.value.split(",");
                
                codeArr.forEach(function(val) {
                    var codeVal = val.split("|");
                    $("#cadAppType").append("<option value='"+codeVal[0]+"'>"+codeVal[1]+"</option>");
                });
            }
            
        });
        $("#cadAppType").multiselect('refresh');
    }
});

$(function() {
    // 파일 드롭 다운
    fileDropDown();
});

// 파일 드롭 다운
function fileDropDown() {
    var dropZone = $("#dropZone");
    //Drag기능 
    dropZone.on('dragenter', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        //dropZone.css('background-color', '#E3F2FC');
    });
    dropZone.on('dragleave', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        //dropZone.css('background-color', '#FFFFFF');
    });
    dropZone.on('dragover', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        //dropZone.css('background-color', '#E3F2FC');
    });
    dropZone.on('drop', function(e) {
        e.preventDefault();
        // 드롭다운 영역 css
        //dropZone.css('background-color', '#FFFFFF');

        var files = e.originalEvent.dataTransfer.files;
        if (files != null) {
            
            addFiles(e.originalEvent.dataTransfer);
            
        } else {
            alert("ERROR");
        }
    });
    
}


var setDropZoneHeight = function(){
	var dropZoneDiv = document.getElementById("dropZone");
	    
	var fileListSize = document.getElementById("fileTable").rows.length;
	if(fileListSize > 17){
		var listHeight = fileListSize * 29;
		dropZoneDiv.style.height = listHeight + "px";
	}else{
		dropZoneDiv.style.height = "500px";
	}
}
// 업로드 파일 목록 생성
function addFileList(fIndex, fileName, fileSizeStr) {
    var innerRow = fileTable.insertRow();
    innerRow.id = 'fileTr_' + fIndex;
    innerRow.height = "27";
    var innerCell;

    for ( var k = 0; k < 2; k++) {
        innerCell = innerRow.insertCell();
        innerCell.height = "23";

        if (k == 0) {
            innerCell.className = "tdwhiteM";
            innerCell.innerHTML = "<a href='#' onclick='deleteFile(" + fIndex +");  return false;'><b><img src='/plm/portal/images/b-minus.png'></b></a>";
        } else if (k == 1) {
            innerCell.className = "tdwhiteL0";
            filestr = fileName + " (" + fileSizeStr +") " ;
            innerCell.innerHTML = "<input name='secondaryFiles' type='text' class='txt_fieldRO' style='width: 100%;' value='"+filestr+"' readonly>";
        }
    }
}

function removeAll(){
    
    var file_tab = document.getElementById("fileTable");
    var fileRows = file_tab.rows;
    var fileRowsLen = fileRows.length;
    
    var indexList = new Array();
    
    for(var i=0; i<fileRowsLen; i++){
        var fileId = fileRows[i].id;
        var index = fileId.substring(7);
        indexList.push(index);
    }
    
    indexList.forEach(function(element){deleteFile(element,true)});
    setDropZoneHeight();
}

//업로드 파일 삭제
function deleteFile(fIndex,isAll) {
    

    console.log("deleteFile.fIndex=" + fIndex);
    // 전체 파일 사이즈 수정
    totalFileSize -= fileSizeList[fIndex];

    // 파일 배열에서 삭제
    delete fileList[fIndex];

    // 파일 사이즈 배열 삭제
    delete fileSizeList[fIndex];

    // 업로드 파일 테이블 목록에서 삭제
   $("#fileTr_" + fIndex).remove();
   //obj.remove();
    
    console.log("totalFileSize="+totalFileSize);
    
    if (totalFileSize > 0) {
        $("#fileDragDesc").hide(); 
        $("fileListTable").show();
    } else {
        $("#fileDragDesc").show(); 
        $("fileListTable").hide();
    }
    
    if(!isAll){
    	setDropZoneHeight();
    }
}

window.addEventListener("dragover",function(e){
      e.preventDefault();
    },false);
    
window.addEventListener("drop",function(e){ 
    e.preventDefault();
       /* e = e || event; 
       if (e.target.tagName != "INPUT") {  
          // check which element is our target 
          e.preventDefault(); 
       }   */
    },false);
    

function addFiles(dataTransfer){
	
	var agent = navigator.userAgent.toLowerCase();
	var isIE = (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1);
    
    var isDirectory = false;
    
    if(isIE){
    	var files = dataTransfer.files;
        var length = files.length;
        isDirectory = length < 1 ? true : false;  
        for (var i = 0; i < length; i++) {
        	selectFile(files[i]);
        }
    }else{
    	var items = dataTransfer.items;
    	var length = items.length;
        
        for (var i = 0; i < length; i++) {
            var entry = items[i].webkitGetAsEntry();
            if (entry.isFile) {
                selectFile(items[i].getAsFile());
            } else if (entry.isDirectory) {
                isDirectory = true;
            }
        }
    }
    
    
    var msg = "";
    if (isDirectory) {
        msg = "폴더는 업로드 할 수 없습니다.\r\n\r\n";
    }
    
    msg += fileCheckMsg;
    
    if(msg != ""){
        alert(msg);
    }
   
    fileCheckMsg = "";
    FileAddend();
    setDropZoneHeight();
}

function FileAddend(){
    var agent = navigator.userAgent.toLowerCase();
    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
        
        $("#input_file").replaceWith( $("#input_file").clone(true) );
        
    } else { // other browser 일때
        
        $("#input_file").val("");
        
    }
}

function selectFile(fileObject) {
    
    var files = null;

    if (fileObject != null) {
        // 파일 Drag 이용하여 등록시
        files = fileObject[0] || fileObject;
    } else {
        // 직접 파일 등록시
        files = $('#multipaartFileList_' + fileIndex)[0].files;
    }
    
    var isValid = true;

    // 다중파일 등록
    if (files != null) {
        
        if (files != null) {
            $("#fileDragDesc").hide(); 
            $("fileListTable").show();
        } else {
            $("#fileDragDesc").show(); 
            $("fileListTable").hide();
        }
        
        // 파일 이름
        var fileName = files.name;
        var fileNameArr = fileName.split("\.");
        // 확장자
        var ext = fileNameArr[fileNameArr.length - 1];
        
        var fileSize = files.size; // 파일 사이즈(단위 :byte)
        console.log("fileSize="+fileSize);
        if (fileSize <= 0) {
            isValid = false;
            fileCheckMsg += '용량이 0인 파일은 첨부되지 않습니다.' + fileName+"\r\n";
            
            console.log("0kb file return");
        }
        
        var fileSizeKb = fileSize / 1024; // 파일 사이즈(단위 :kb)
        var fileSizeMb = fileSizeKb / 1024; // 파일 사이즈(단위 :Mb)
        
        var fileSizeStr = "";
        if ((1024*1024) <= fileSize) {  // 파일 용량이 1메가 이상인 경우 
            console.log("fileSizeMb="+fileSizeMb.toFixed(2));
            fileSizeStr = fileSizeMb.toFixed(2) + " Mb";
        } else if ((1024) <= fileSize) {
            console.log("fileSizeKb="+parseInt(fileSizeKb));
            fileSizeStr = parseInt(fileSizeKb) + " kb";
        } else {
            console.log("fileSize="+parseInt(fileSize));
            fileSizeStr = parseInt(fileSize) + " byte";
        }
        
        var isDup = false;
        
        for(var j=0; j<fileList.length; j++){
            if(fileList[j] != null && fileList[j].name+fileList[j].size == fileName+fileSize){
                console.log("이미 첨부된 파일 : "+fileName);
                fileCheckMsg += "이미 첨부된 파일입니다. : "+fileName+"\r\n";
                isDup = true;
                break;
            }
        }
        
        if(isDup){
            isValid = false;
        }
        
        ext = ext.toUpperCase();
        
        if ($.inArray(ext, ['','INI','EGG','ALZ','ACE','ARC','ARJ','B64','BH ','BHX','BIN','BZ2','CAB','EAR','ENC','GZ' ,'HA' ,'HQX','ICE','IMG','JAR','LHA','MIM','PAK','RAR','SIT','TAR','TGZ','UUE','WAR','XXE','Z','ZIP','ZOO','001','WAV','WMA','MP3','MP4','MKV','AVI','FLV','MOV','OGG','FLAC','MID','AC3','AAC','RA','MPG','MPEG','ASF','ASX','WMV','MOV','SWF','RM','RAM','ICO','EXE','BAT','BIN','CPL','DAT','LNK','LST','DLL','REG','SCR','TTF','OTF','$$$','TMP']) > 0) {
            // 확장자 체크
            /* alert("등록이 불가능한 파일 입니다.");
            break; */
            //alert("등록이 불가능한 파일 입니다.("+fileName+")");
            fileCheckMsg += "첨부 대상이 아닙니다.\r\n("+fileName+")\r\n\r\n";
            isValid = false;
        }
        /* else if (fileSizeMb > uploadSize) {
            // 파일 사이즈 체크
            alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
            continue;
            
        } */ 
        
        if(isValid) {
            // 전체 파일 사이즈
            totalFileSize += fileSizeMb;

            // 파일 배열에 넣기
            fileList[fileIndex] = files;

            // 파일 사이즈 배열에 넣기
            fileSizeList[fileIndex] = fileSizeMb;

            // 업로드 파일 목록 생성
            addFileList(fileIndex, fileName, fileSizeStr);

            // 파일 번호 증가
            fileIndex++;
        }
        
    }else {
        alert("ERROR");
    }

}




//파일 등록
function uploadFile() {
    
    if (!CommonUtil.checkEsseOk()) {
        return;
    }
    // 등록할 파일 리스트
    var uploadFileList = Object.keys(fileList);

    // 파일이 있는지 체크
    if (uploadFileList.length == 0) {
        // 파일등록 경고창
        alert("업로드 대상 파일이 첨부되지 않았습니다.");
        return;
    }

    // 용량을 500MB를 넘을 경우 업로드 불가
    if (totalFileSize > maxUploadSize) {
        // 파일 사이즈 초과 경고창
        //alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
        //return;
    }

    if (confirm("금형도면 일괄등록 하시겠습니까?")) {
        // 등록할 파일 리스트를 formData로 데이터 입력
        //var form = $('#uploadForm');
        var formData = new FormData();
        for (var i = 0; i < uploadFileList.length; i++) {
            formData.append('files', fileList[uploadFileList[i]]);
        }
        
        
        formData.append('templatefile', $('#templatefile')[0].files[0]);
        
        var arr = $('[name=uploadForm]').serializeArray();
        var paramObj = {};
        if (arr) {
            $.each(arr, function() {
                paramObj[this.name] = this.value;
            });
        }
        formData.append('jsonData',JSON.stringify(paramObj));
        
        ajaxCallServer("/plm/ext/edm/uploadBatchMold", formData, function(data){
        });
       
    }
}


function setProject(objArr) {

    if(objArr.length == 0) {
        return;
    }

    var trArr;
    var str = "";

    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        if(trArr[9] == "S") {
            
            $("#security").val("S2"); //대내비
            $("#security").multiselect('refresh');
            $("#security").multiselect("disable");
            
        }else{
            
            $("#security").multiselect('enable');
            $("#security").val("S1"); //대내비
            $("#security").multiselect('refresh');
            
            
        }
        document.forms[0].project.value = trArr[0];
        document.forms[0].projectNumber.value = trArr[1];
    }
    
}

</script>
<body class="popup-background popup-space">
<form enctype="multipart/form-data" name="uploadForm">
<input type="hidden" name="oid" id="oid" value="${imDTO.oid}"/>
<input type="hidden" id="oldRequestDate" name="oldRequestDate" value="${imDTO.requestDate }">
    <div class="contents-wrap">
        <div class="sub-title b-space5">
            <img src="/plm/portal/images/icon_3.png" />금형도면 일괄 등록
        </div>
        <div class="b-space5 float-r" style="text-align: right">
            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:uploadFile();" class="btn_blue">
                        <%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%>
                        </a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>

            <span class="in-block v-middle r-space7">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center">
                        <a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a>
                    </span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 float-l b-space5">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>기본정보
        </div>
        <div class="b-space5" >
            <table summary="" class="info">
                <colgroup>
                    <col width="15%" />
                    <col width="35%" />
                    <col width="15%" />
                    <col width="35%" />
                </colgroup>
                <tbody>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%><span class="red">*</span></td>
                        <td class="tdwhiteL"><ket:select id="businessType" name="businessType" className="fm_jmp" style="width: 170px;" multiple="multiple" codeType="DIVISIONTYPE" value="${divisionFlag }" esse="true" esseLabel="사업부"/>
                        </td>
                
                        <td class="tdblueL">보안등급<span class="red">*</span>
                        </td>
                        <td class="tdwhiteL">
                        <select id="security" name="security" class="fm_jmp" style="width: 150px;" esse="true" esseLabel="보안등급">
                            <option value="S1">대외비</option>
                            <option value="S2">대내비</option>
                        </select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01264") %><%--도면구분--%><span class="red">*</span></td>
                        <td class="tdwhiteL">
                        <select id="devStage" name="devStage" class="fm_jmp" style="width: 150px;" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "01264")%>">
                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                            <option value="DEVELOPMENT_STAGE">개발단계</option>
                            <option value="PRODUCTION_STAGE">양산단계</option>
                        </select>
                        </td>
                
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01288") %><%--도면유형--%><span class="red">*</span></td>
                        </td>
                        <td class="tdwhiteL">
                        <select id="category" name="category" class="fm_jmp" style="width: 150px;" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "01288")%>">
                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                            <option value="INJECTION_MOLD_DRAWING">사출금형도면</option>
                            <option value="PRESS_MOLD_DRAWING">프레스금형도면</option>
                            <option value="INJECTION_MOLD_SET_DRAWING">사출금형SET도면</option>
                            <option value="PRESS_MOLD_SET_DRAWING">프레스금형SET도면</option>
                        </select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00102") %><%--CAD종류--%><span class="red">*</span></td>
                        <td class="tdwhiteL">
                        <select id="cadAppType" name="cadAppType" class="fm_jmp" style="width: 150px;" esse="true" esseLabel="<%=messageService.getString("e3ps.message.ket_message", "00102")%>">
                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        </select>
                        </td>
                
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td class="tdwhiteL">
                        <input name="project" type="hidden" value="">
                        <input name="projectNumber" type="text" class="txt_fieldRO" style="width:240" value="" disabled>
                        &nbsp;<a href="javascript:;" onClick="javascript:SearchUtil.selectOneProjectPopUp('setProject','&type=M');"><img src="../../portal/images/icon_5.png" border="0"></a>&nbsp;&nbsp;<a href="javascript:;" onClick="javascript:doDeleteProject();"><img src="../../portal/images/icon_delete.gif" border="0"></a>
                    </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">금형도면 템플릿<span class="red">*</span>
                        <a href="/plm/extcore/jsp/bom/KETExcelTemplateDownload.jsp?filepath=MoldBOMTemplate.xls"><img src="/plm/portal/images/iocn_excel.png" alt="excel down" name="leftbtn_02" border="0"></a>
                        </td>
                        <td colspan="3" class="tdwhiteL">
                            <input name="templatefile" id="templatefile" type="file" class="txt_fieldRO" style="width: 100%" esse="true" esseLabel="금형도면 템플릿">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="b-space5" >
            <table border="0" cellspacing="0" cellpadding="0" width="90%" >
                <tr><td class="space5"></td></tr>
            </table>
                    
            <div class="file_input">
                <label>파일찾기<input type="file" id="input_file" multiple='multiple'></label>
                <label onClick="javascript:removeAll();">전체삭제</label>
                <span id="decNotice">▶ 업로드 대상 금형도면 파일을 선택하십시오. (다중선택 가능) </span>
            </div>

            <table border="0" cellspacing="0" cellpadding="0" >
                <tr><td class="space5"></td></tr>
            </table>
                            
            <table border="0" cellspacing="0" cellpadding="0">
                <tr><td class="space40"></td></tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdwhiteM">
                        <div id="dropZone" style="width: 90%; height: 500px;">
                            <table id="fileListTable" style="width: 90%; table-layout: fixed" border="0px">
                                <col width="20">
                                <col width="">
                                
                                <tbody id="fileTable">
                                    <div id="fileDragDesc"> 첨부파일을 마우스로 끌어 넣으세요. </div>
                                </tbody>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    

</form>
</body>