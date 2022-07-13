function lfn_setInfosRelatedProject(oid) {
    if(oid == null || oid == '') return;
    
    showProcessing();
    $.ajax({
        type: 'get',
        url: '/plm/ext/project/program/getProject.do',
        data: {
            projectOid : oid
        },
        success: function (data) {
            $("input[name='projectOid']").val(data.projectOid);
            $("input[name='projectNo']").val(data.projectNo);
            $("input[name='projectName']").val(data.projectName);
            // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
            if(data.projectInfos4ECM != null) {
                try {
                    // 제품정보를 위한 선처리
                    var arrParts = null;
                    
                    var projectInfos4ECM = data.projectInfos4ECM;
                    var arrPInfos = projectInfos4ECM.split("↕");
                    var arrPInfosLength = (arrPInfos != null) ? arrPInfos.length : 0;
                    for(var l=0; l < arrPInfosLength; l++) {
                        var arrPartInfo13 = null;
                        var arrP = arrPInfos[l].split("↔");
                        var arrPLength = (arrP != null) ? arrP.length : 0;
                        var partOk = 0;
                        // 개발단계 = 단계구분
                        if(l == 1 && arrPLength == 3) { 
                            resetSelectElement(document.getElementById('process'));                             

                            var devYnOid = arrP[0];
                            var devYnName = arrP[1];
                            var devYnCode = arrP[2];
                            /* 
                            Process
                            PC001 Proto 
                            PC002 Pilot 
                            PC003 T-CAR 
                            
                                 개발/양산구분
                            PROTO PROTO 
                            PILOT PILOT 
                            TCAR T-CAR 
                            PRODUCTION 양산 
                            */
                            var checkDevYnCode = "";
                            if(devYnCode == 'PC001') {
                                checkDevYnCode = "PROTO";
                            } else if(devYnCode == 'PC002') {
                                checkDevYnCode = "PILOT";
                            } else if(devYnCode == 'PC003') {
                                checkDevYnCode = "TCAR";
                            }
                            var processSize = $("#process option").size();
                            for(var p=0; p<processSize; p++){
                                if(devYnOid == $("#process option:eq("+p+")").val()){
                                    $("#process option").each(function(){
                                        $("#process option:eq("+p+")").attr('selected', true);
                                    });
                                }
                            }
                            $("#process").multiselect("refresh");
                        }
                        // 대표 차종
                        else if(l == 2 && arrPLength == 3) {
                            var carTypeOid = arrP[0];
                            var carTypeName = arrP[1];
                            var carTypeCode = arrP[2];
                            $("input[name='carTypeName']").val(carTypeName);
                            $("input[name='carType']").val(carTypeOid);
                        }
                        // 고객처
                        else if(l == 3 && arrPLength == 3) {
                            var subcontractorOid = arrP[0];
                            var subcontractorName = arrP[1];
                            var subcontractorCode = arrP[2];
                            $("select[name=customerDepartment] option").remove();
                            for(var s = 0; s < subcontractorOid.split(",").length; s++){
                                $("select[name=customerDepartment]").append("<option value='"+subcontractorOid.split(",")[s]+"'>"+subcontractorName.split(",")[s]+"</option>"); 
                            }
                        }
                        // 제품구분
                        else if(l >= 1 && arrPLength == 1) {
                        	var className = arrP[0];
                            $("#projectClassName").text(className);
                        }       
                        // 제품정보 
                        else if(l >= 4 && arrPLength == 4) {
                        	partOk = 1;
                            var relPartOid = arrP[0];
                            var pNum = arrP[1];
                            var pName = arrP[2];
                            var relPartVersion = arrP[3];
                            
                            arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
                            //alert('arrPartInfo13 is '+ arrPartInfo13);
                        }
                        if(arrPartInfo13 != null) {
                            if(arrParts == null) arrParts = new Array();
                            arrParts[arrParts.length] = arrPartInfo13;
                            //alert('arrParts['+ (arrParts.length - 1) +'] is '+ arrParts[ (arrParts.length - 1) ]);
                        }
                    }
                    
                    var title = $("#productInfo tr:first").clone();
                    $("#productInfo tr").remove();
                    $("#productInfo").append(title);
                    
                	if(partOk == 1){
	                    // 제품정보를 위한 후처리
	                    createProduct(arrParts);
                    }
                } catch(e) { alert(e); }
                hideProcessing();
            }
        },
        fail : function(){
            hideProcessing();
        }
    });
    hideProcessing();
}

function lfn_setInfosRelatedPart(oid) {
  if(oid == null || oid == '') return;
  showProcessing();
  $.ajax({
      type: 'get',
      url: '/plm/ext/arm/master/getPart.do',
      data: {
          partOid : oid
      },
      success: function (data) {
        try {
            // 제품정보를 위한 선처리
              var arrParts = null;
                var arrPInfos = data.split("↕");
                var arrPInfosLength = (arrPInfos != null) ? arrPInfos.length : 0;
                for(var l=0; l < arrPInfosLength; l++) {
                     var arrPartInfo13 = null;
                     var arrP = arrPInfos[l].split("↔");
                     var arrPLength = (arrP != null) ? arrP.length : 0;
                     var relPartOid = arrP[0];
                     var pNum = arrP[1]; 
                     var pName = arrP[2];
                     var relPartVersion = arrP[3];
                     
                     arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
    
                     if(arrPartInfo13 != null) {
                         if(arrParts == null) arrParts = new Array();
                         arrParts[arrParts.length] = arrPartInfo13;
                     }
                     
                }
                // 제품정보를 위한 후처리
                createProduct(arrParts);
        } catch(e) { alert(e); }
          hideProcessing();
      },
      fail : function(){
          hideProcessing();
      }
  });
  hideProcessing();
}

function genRowId(){
    sleep(1);
    return (new Date()).getTime();
}

function sleep(num){
    var now = new Date();
    var stop = now.getTime() + num;
    while(true){
        now = new Date();
        if(now.getTime() > stop) { return; }
    }
}

//제품추가
function createProduct(objArr){
    var tBody = document.getElementById("productInfo");
    for(var i=0; i<objArr.length;i++){
        var rowId = genRowId();
        tableRows = tBody.rows.length;
        newTr = tBody.insertRow(tableRows);
        newTr.id = rowId;

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        //newTd.width = "20";
        newTd.innerHTML = "<a href=\"javascript:;\" onclick=\"javascript:onDeleteTableRow2('productInfo','rowId','" + rowId + "');\"><img src='/plm/portal/images/b-minus.png' border='0'></a><input type='hidden' name='rowId' value='"+rowId+"'>";
       
        //제품번호
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        //newTd.width = "120";
        newTd.innerHTML = "<input type='hidden' name='pNum' value='"+objArr[i][1]+"' id='pNum"+rowId+"'>"+objArr[i][1];
        newTd.innerHTML += "<input type='hidden' name='pOid' id='reviewProjectNo"+rowId+"' value='"+objArr[i][0]+"'>";
        newTd.innerHTML += "<input type='hidden' name='reviewProjectNo' id='reviewProjectNo"+rowId+"' value=''>";
        newTd.innerHTML += "<input type='hidden' name='reviewSeqNo' id='reviewSeqNo"+rowId+"' value=''>";
        //newTd.innerHTML += "&nbsp;<a href=\"javascript:selectPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
        //newTd.innerHTML += "&nbsp;<a href=\"javascript:clearPartNumber('pNum', " + rowId + ");\"><img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\"></a>";

        //제품명
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        //newTd.width = "150";
        newTd.innerHTML = "<input type='hidden' name='pName' id='pName"+rowId+"' value='"+objArr[i][2]+"''>"+objArr[i][2];
        //REV
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        //newTd.width = "150";
        newTd.innerHTML = "<input type='hidden' name='pRev' id='pRev"+rowId+"' value='"+objArr[i][3]+"''>"+objArr[i][3];
    }
       
}

//제품삭제
function onDeleteTableRow2(tableid, chk_name, chk_value) {
    targetTable = document.getElementById(tableid);
    var chkTag = document.all.item(chk_name);

    var chkLen = chkTag.length;
    if ( chkLen ) {
        for ( var i = 0; i < chkLen; i++ ) {
            if ( chkTag[i].value == chk_value ) {
                pOidObj = document.all.item("pOid"+chk_value);
                if ( pOidObj ) {
                    if ( pOidObj.value != "" )
                        document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
                }
                targetTable.deleteRow(i+1);

                // partNo에 값입력
                partNoDuplicateCheck();

                return;
            }
        }
    }else {
        if ( chkTag.value == chk_value ) {
            pOidObj = document.all.item("pOid"+chk_value);
            if ( pOidObj ) {
                if ( pOidObj.value != "" )
                    document.forms[0].deletePOid.value = document.forms[0].deletePOid.value + "@" + pOidObj.value;
            }
            targetTable.deleteRow(1);

            // partNo에 값입력
            partNoDuplicateCheck();

            return;
        }
    }
}

function partNoDuplicateCheck() {
    var tBody = document.getElementById("productInfo");
    var tableRows = tBody.rows.length;

    // Table TR length
    var tableTrRows = tBody.childNodes.length;

    var newPartNo = new Array();
    /* for ( var i=2; i<tableRows; i++ ) {
        newPartNo.push(tBody.childNodes[0].childNodes[i].childNodes[0].childNodes[0].value);
    } */
    $('#pNum').each(function(){
        newPartNo.push($(this).val());  
    });
    
    newPartNo = newPartNo.sort();
    var partNoDuplicate = new Array();
    for ( var i=0; i<newPartNo.length; i++ ) {
        var checkDu = 0;
        for ( j=0; j<newPartNo.length; j++ ) {
            if ( newPartNo[i] != newPartNo[j] ) {
                continue;
            }
            else {
                checkDu++;
                if ( checkDu > 1 ) {
                    newPartNo.splice(j,1);
                }
            }
        }
    }
}

//의뢰처 검색화면 오픈하여 선택된 의뢰처를 입력한다.
function insertRequestBuyer() {

  var fm = document.forms[0];
  var pos = fm.customerDepartment.length;
  var arrObj;
  var subArr;

  var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=multi";

  arrObj = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
  if(typeof arrObj == "undefined" || arrObj == null) {
    return;
  }

  for(var i = 0; i < arrObj.length; i++) {
    subArr = arrObj[i];

    for(var j = 0; j < pos; j++) {
      if(fm.customerDepartment.options[j].value==subArr[0]){
        alert(subArr[2]+"는 이미 존재하는 의뢰처입니다");
        return;
      }
    }

    fm.customerDepartment.length = pos+i+1;
    fm.customerDepartment.options[pos+i].value= subArr[0];
    fm.customerDepartment.options[pos+i].text = subArr[2];
    fm.customerDepartment.options[pos+i].selected = true;
  }
}

//선택된 의뢰처를 삭제한다.
function deleteRequestBuyer() {
	$('[name=customerDepartment] option:selected').remove();
}

function resetSelectElement(selectElement) { 
    var options = selectElement.options; // Look for a default selected option 
    for (var i=0, iLen=options.length; i<iLen; i++) {
        if (options[i].defaultSelected) { 
            selectElement.selectedIndex = i; return; 
        }
    } // If no option is the default, select first or none as appropriate 
    selectElement.selectedIndex = 0; // or -1 for no option selected
}