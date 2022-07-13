<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<%@include file="/jsp/project/template/ajaxProgress.html"%>
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
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
</style>

<script language='javascript'>
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

$(document).ready(function(){
    document.title = '구 (舊) 암호화 문서 변환';
    
    $("#input_file").bind('change', function() {
        selectFile(this.files);
        //this.files[0].size gets the size of your file.
        //alert(this.files[0].size);
    });
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
            if (files.length < 1) {
                /* alert("폴더 업로드 불가"); */
                console.log("폴더 업로드 불가");
                return;
            } else {
                selectFile(files);
            }
        } else {
            alert("ERROR");
        }
    });
    
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

//업로드 파일 삭제
function deleteFile(fIndex) {
    

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

function selectFile(fileObject) {
    var files = null;

    if (fileObject != null) {
        // 파일 Drag 이용하여 등록시
        files = fileObject;
    } else {
        // 직접 파일 등록시
        files = $('#multipaartFileList_' + fileIndex)[0].files;
    }
    
    var msg = '';

    // 다중파일 등록
    if (files != null) {
        
    	if (files != null && files.length > 0) {
            $("#fileDragDesc").hide(); 
            $("fileListTable").show();
        } else {
            $("#fileDragDesc").show(); 
            $("fileListTable").hide();
        }
        
        for (var i = 0; i < files.length; i++) {
            // 파일 이름
            var fileName = files[i].name;
            var fileNameArr = fileName.split("\.");
            // 확장자
            var ext = fileNameArr[fileNameArr.length - 1];
            
            var fileSize = files[i].size; // 파일 사이즈(단위 :byte)
            console.log("fileSize="+fileSize);
            if (fileSize <= 0) {
                console.log("0kb file return");
                continue;
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
            		msg += "이미 첨부된 파일입니다. : "+fileName+"\r\n";
            		isDup = true;
            		break;
            	}
            }
            
            if(isDup){
            	continue;
            }

            ext = ext.toUpperCase();
            
            if ($.inArray(ext, ['','INI','EGG','ALZ','ACE','ARC','ARJ','B64','BH ','BHX','BIN','BZ2','CAB','EAR','ENC','GZ' ,'HA' ,'HQX','ICE','IMG','JAR','LHA','MIM','PAK','RAR','SIT','TAR','TGZ','UUE','WAR','XXE','Z','ZIP','ZOO','001','WAV','WMA','MP3','MP4','MKV','AVI','FLV','MOV','OGG','FLAC','MID','AC3','AAC','RA','MPG','MPEG','ASF','ASX','WMV','MOV','SWF','RM','RAM','ICO','EXE','BAT','BIN','CPL','DAT','LNK','LST','DLL','REG','SCR','TTF','OTF','$$$','TMP']) > 0) {
                // 확장자 체크
                /* alert("등록이 불가능한 파일 입니다.");
                break; */
                //alert("등록이 불가능한 파일 입니다.("+fileName+")");
                msg += "변환 대상이 아닙니다.\r\n("+fileName+")\r\n\r\n";
                console.log("msg == "+msg);
                continue;
            } 
            /* else if (fileSizeMb > uploadSize) {
                // 파일 사이즈 체크
                alert("용량 초과\n업로드 가능 용량 : " + uploadSize + " MB");
                continue;
                
            } */ 
            
            else {
                // 전체 파일 사이즈
                totalFileSize += fileSizeMb;

                // 파일 배열에 넣기
                fileList[fileIndex] = files[i];

                // 파일 사이즈 배열에 넣기
                fileSizeList[fileIndex] = fileSizeMb;

                // 업로드 파일 목록 생성
                addFileList(fileIndex, fileName, fileSizeStr);

                // 파일 번호 증가
                fileIndex++;
            }
        }
    }else {
        alert("ERROR");
    }
        
    if(msg != ''){
        alert(msg);
    }
    var agent = navigator.userAgent.toLowerCase();

    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
    	$("#input_file").replaceWith( $("#input_file").clone(true) ); 
    } else { // other browser 일때 
    	$("#input_file").val(""); 
    }

}


//파일 등록
function uploadFile() {
    // 등록할 파일 리스트
    var uploadFileList = Object.keys(fileList);

    // 파일이 있는지 체크
    if (uploadFileList.length == 0) {
        // 파일등록 경고창
        alert("변환대상 파일이 첨부되지 않았습니다.");
        return;
    }

    // 용량을 500MB를 넘을 경우 업로드 불가
    if (totalFileSize > maxUploadSize) {
        // 파일 사이즈 초과 경고창
        alert("총 용량 초과\n총 업로드 가능 용량 : " + maxUploadSize + " MB");
        return;
    }

    if (confirm("변환 하시겠습니까?")) {
        // 등록할 파일 리스트를 formData로 데이터 입력
        //var form = $('#uploadForm');
        var formData = new FormData();
        for (var i = 0; i < uploadFileList.length; i++) {
            formData.append('files', fileList[uploadFileList[i]]);
        }

        ajaxCallServer("/plm/ext/orther/file/fileUpload", formData, function(data){
        	$("#download")[0].src = data.downloadLink;
        });
    }
}



</script>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
</head>
<html>
<body class="body">
    <form name="uploadForm" method="post" enctype="multipart/form-data">
        <table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="780" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><table width="780" height="28" border="0"
                                    cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01">구 (舊) 암호화 문서 변환</td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td class="head_line"></td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>


                    <table width="780" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>&nbsp;</td>
                            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:uploadFile();" class="btn_blue">변환</a></td>
                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                <td width="10"></td>
                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                           </tr>
                                        </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="780" >
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    
                    <div class="file_input">
                        <label>
                            파일찾기
                            <input type="file" id="input_file" multiple='multiple'>
                        </label>
                        <span id="decNotice">▶ 해당 문서변환은 문서 복호화가 아닙니다.문서 복호화는 전자결제의 문서 복호화를 이용하시기 바랍니다. </span>
                    </div>
                    <table border="0" cellspacing="0" cellpadding="0" width="780" >
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                            
                    <table border="0" cellspacing="0" cellpadding="0" width="780">
                        <tr>
                            <td class="space40"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="780">
                        <tr>
                            <td class="tdwhiteM">
                                <div id="dropZone" style="width: 100%; height: 500px;">
                                <table id="fileListTable" style="width: 100%; table-layout: fixed" border="0px">
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
            </tr>
        </table>
    </form>
</body>
</html>
<iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px;"></iframe>