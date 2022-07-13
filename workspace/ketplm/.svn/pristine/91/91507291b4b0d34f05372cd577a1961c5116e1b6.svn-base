
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

//제품추가
function createProduct(objArr){
  var tBody = document.getElementById("productInfo");
  for(var i=0; i<objArr.length;i++){
      var rowId = genRowId();
      tableRows = tBody.rows.length;
      newTr = tBody.insertRow(tableRows);
      newTr.id = rowId;
     
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
