
//첨부파일 삭제
function deleteFile(obj) {
  var form01 = document.forms[0];
  var temp = obj.children.fileDelete.value;
 // for(var i=index; i>=1; i--) {
    if(temp != null) {
      var deleteFile = temp;
      //if(deleteFile != null) {
        var addFile = form01.deleteFiles.value;
        if(addFile == "") {
          form01.deleteFiles.value += deleteFile;
        } else {
          form01.deleteFiles.value += "*"+deleteFile;
        }
      }
     // fileTable.deleteRow(i+1);
    //}
  //}
}

var tempId = "";
var tempIndex = "";

// ==  [Start] 사람 검색  == //
function selectUserId(targetId) {
    selectUser(targetId, 3); // index=3: user id
}
function selectUserOid(targetId) {
    selectUser(targetId, 0); // index=0: wtuser oid
}
function selectUser(targetId, dataIndex) {
	
	tempId = targetId;
	tempIndex = dataIndex;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=acceptMember";
    
    var opts = "toolbar=0, location=0, directory=0, status=1, menubar=0, scrollbars=1, resizable=0";
	getOpenWindow2(url,"searchEpmDocument", '790', '610', opts);
	
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
}

function selectOneUserId(targetId) {
    selectOneUser(targetId, 3); // index=3: user id
}
function selectOneUserOid(targetId) {
    selectOneUser(targetId, 0); // index=0: wtuser oid
}
function selectOneUser(targetId, dataIndex) {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
        return;
    }
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
    var isAppend = "Y";
    acceptMember(targetId, attacheMembers, isAppend, dataIndex);
}

function acceptMember(arrObj) {
	var targetId = tempId;
	var dataIndex = tempIndex;
	var isAppend = "Y";
    var userData = new Array();     // Id
    var userName = new Array();   // Nmae
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        userData[i] = infoArr[dataIndex];
        userName[i] = infoArr[4];
    }

    var tmpId = new Array();
    var tmpName = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpId = $.merge($("#"+targetId).val().split(", "), userData);
        tmpId = $.unique(tmpId.sort());

        tmpName = $.merge($("#"+targetId+"Name").val().split(", "), userName);
        tmpName = $.unique(tmpName.sort());
    }
    else {
        tmpId = userData.sort();
        tmpName = userName.sort();
    }

    $("#"+targetId).val(tmpId.join(", "));
    $("#"+targetId+"Name").val(tmpName.join(", "));
}
function clearUser(targetId) {
    $("#"+targetId).val("");
    $("#"+targetId+"Name").val("");
}
// ==  [End] 사람 검색  == //

// Number Code Ajax
function boardNumCodeAjax(codeType, code, targetId, suntak, selectAll) {
    $.ajax( {
        url : "/plm/servlet/e3ps/NumberCodeAjax",
        type : "POST",
        data : {codeType:codeType, code:code},
        dataType : 'json',
        async : false,
        success: function(data) {
            var first = true;
            $.each(data.numCode, function() {
                if (suntak != null && first == true) {
                    $("#"+targetId).append("<option value=''>"+ suntak +"</option>");
                    first = false;
                }
                var str = "<option value='"+this.code+"'";
                if (selectAll != null && selectAll != undefined && selectAll == true) {
                    str = str + " selected";
                }
                str = str + ">"+ this.value +"</option>";
                $("#"+targetId).append(str);
            });
        }
    });
}
