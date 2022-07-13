<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@page import="e3ps.common.util.CommonUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<c:if test="${authCheckFlag == false}" >
<script>
$(document).ready(function() {
    alert('<%=messageService.getString("e3ps.message.ket_message", "00990") %>');
    window.close();
});
</script>
</c:if>
<c:if test="${authCheckFlag == true}" >
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}


.pop-layer {position: absolute; width: 900px; height:200px;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}  
.pop-layer .pop-container {padding: 20px 25px;}
.pop-layer p.ctxt {color: #666; line-height: 25px;}
.pop-layer .btn-r {width: 100%; margin:10px 0 20px; padding-top: 10px; border-top: 1px solid #DDD; text-align:right;}

a.cbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;} 
a.cbtn:hover {border: 1px solid #091940; background-color:#1f326a; color:#fff;}

</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bluebird/3.3.5/bluebird.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.js"></script>
<script src="https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.js"></script> 
<script type="text/javascript">

var repeat;
var mainSignal_hex;
$(document).ready(function(){
    // 이노디터에서 작성된 내용을 전달
    var strContent = document.innoditorDataForm["webEditor"].value;

    var objView = document.getElementById("divView");
    objView.innerHTML = strContent;
    
    var strContent1 = document.innoditorDataForm1["propelwebEditor"].value;

    var objView1 = document.getElementById("divView1");
    objView1.innerHTML = strContent1;
    
    var strContent2 = document.innoditorDataForm2["problemwebEditor"].value;

    var objView2 = document.getElementById("divView2");
    objView2.innerHTML = strContent2;
    
    var strContent3 = document.innoditorDataForm3["planwebEditor"].value;

    var objView3 = document.getElementById("divView3");
    objView3.innerHTML = strContent3;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    var strBackgroundColor = document.innoditorDataForm["hdnBackgroundColor"].value;
    if("" != strBackgroundColor)
    {
        objView.style.backgroundColor = strBackgroundColor;
    }

    var strBackgroundImage = document.innoditorDataForm["hdnBackgroundImage"].value;
    if("" != strBackgroundImage)
    {
        var strCopyBackgroundImage = strBackgroundImage.toUpperCase();

        if("none" == strCopyBackgroundImage)
        {
            objView.style.backgroundImage = strBackgroundImage;
        }
        else
        {
            objView.style.backgroundImage = "url(" + strBackgroundImage + ")";
        }
    }

    var strBackgroundRepeat = document.innoditorDataForm["hdnBackgroundRepeat"].value;
    if("" != strBackgroundRepeat)
    {
        objView.style.backgroundRepeat = strBackgroundRepeat;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    var mainSignal = '${sales.mainSignal}';
    
    
    if (mainSignal == "green" ||mainSignal == "gray") {
        
        $("#propelTR").css("display","");
        $("#problemTR").css("display","none");
        
    }else if(mainSignal == "red" || mainSignal == "yellow") {
        
        $("#propelTR").css("display","none");
        $("#problemTR").css("display","");
    }
    var csYN = '${sales.csYN}';
    
    if(csYN == "Y"){
        loadData(1, true);
        
        setPagingCheck++;
    }
    
    if(mainSignal == 'none'){
        flashEventStart();
    }
    mainSignalHexSet(mainSignal);
})

function mainSignalHexSet(mainSignal){
    if(mainSignal == 'green'){
        mainSignal_hex = "#B0D148"; 
    }else if(mainSignal == 'yellow'){
        mainSignal_hex = "#F8D200"; 
    }else if(mainSignal == 'red'){
        mainSignal_hex = "#e42a3d"; 
    }else if(mainSignal == 'gray'){
        mainSignal_hex = "#e4e4e4"; 
    }
}

function flashEventStart(){
    var x;
    repeat = setInterval(function() {
        if(x == 0) {
            $('#checkedPlanDiv').hide();
            x = 1;
        } else  {
            $('#checkedPlanDiv').show();
            x = 0;
        }
    }, 600); // change the time if you want
}

function flashEventClose(){
    clearInterval(repeat);
}


var pageNum = 1; // 페이지 카운트 변수
var totalPageCount = 0; // 총페이지수
var groupPageCount = 20; // 한 화면에 보여줄 페이지수
var groupPageCal = 25; //한 화면에 보여줄  Row수
var groupCount = 1;
var pageCount =1;
var groupPageNum = 1;
var setPagingCheck = 0;
var arr = new Array();

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function loadData(page, type, oid){
    
    showProcessing();
    
    $("#page").val(page);
    $("a").css("color","");
    $("#page"+page).css("color","red");
    $("#pageNum").html(page);
    pageNum = page; 

    if(setPagingCheck == 0){
        $.ajax({
            url : "/plm/ext/sales/project/pageShowSalesCSPage.do?oid="+'${sales.oid}'+"&isFile=false" ,
            type : "POST",
            dataType : 'json',
            async : false,
            cache : false,
            success: function(data) {
                
                
                
                totalPageCount = data.length;
                
                $.each(data, function(i) {
                    //totalPageCount = this.totalPageCount;
                    arr[i] = this.oid;
                });
                
        
                groupPageCount=20;
                var divide = totalPageCount % groupPageCount
                var pagecount = totalPageCount / groupPageCal;
                if(divide < groupPageCount) {pagecount++;}
                else{pagecount;}
                
                totalPageCount = parseInt(pagecount);
                
                totalPageCount = data.length;
                
                if(type){pagingShow(arr);} 
                
                hideProcessing();
            },
            error    : function(xhr, status, error){
                         hideProcessing();
                         alert(xhr+"  "+status+"  "+error);
                         
            }
        });
    }


    if(setPagingCheck >= 1){
        var mainSignal = "";
        showProcessing();
        try{
            $.ajax({
                url : "/plm/ext/sales/project/getShowonlyOneSalesView.do?oid=ext.ket.sales.entity.KETSalesProject:"+oid+"&option=object" ,
                type : "POST",
                dataType : 'json',
                async : false,
                cache : false,
                success: function(data) {
                    
                    $.each(data, function(i) {
                        
                        if (typeof this.mainSignal == "undefined") {
                            mainSignal = 'none';
                        }else{
                            mainSignal = this.mainSignal;
                            mainSignalHexSet(mainSignal);
                        }
                        
                        $('#pjtOid').val(oid);
                        $('#centerName').html(this.centerName + " - "+this.pmdept);
                        $('#rankName').html("<font color='#000000'>"+this.rankName+"</font>");
                        $('#projectNo').html("<font color='#000000'>"+this.projectNo+"</font>");
                        $('#projectName').html("<font color='#000000'>"+this.projectName+"</font>");
                        $('#mainPartClz').html("<font color='#000000'>"+this.mainPartClz+"</font>");
                        $('#customerName').html("<font color='#000000'>"+this.customerName+"</font>");
                        $('#pmdept').html("<font color='#000000'>"+this.pmdept+"</font>");
                        
                        $('#lastBuyerName').html("<font color='#000000'>"+this.lastBuyerName+"</font>");
                        $('#applyArea').html("<font color='#000000'>"+this.applyArea+"</font>");
                        $('#competitorName').html("<font color='#000000'>"+this.competitorName+"</font>");
                        $('#mainExpectSalesTotal').html("<font color='#000000'>"+numberWithCommas(this.expectSalesTotal)+"</font>");
                        $('#mainExpectSalesYear').html("<font color='#000000'>"+numberWithCommas(this.expectSalesYear)+"</font>");
                        $('#salesStateName').html("<font color='#000000'>"+this.salesStateName+"</font>");
                        $('#bigo').text(this.bigo);
                        
                        
                        var objView = document.getElementById("divView");
                        objView.innerHTML = this.webEditor;
                        
                        var objView1 = document.getElementById("divView1");
                        objView1.innerHTML = this.propelwebEditor;
                        
                        var objView2 = document.getElementById("divView2");
                        objView2.innerHTML = this.problemwebEditor;
                        
                        var objView3 = document.getElementById("divView3");
                        objView3.innerHTML = this.planwebEditor;

                    });
                    
                    flashEventClose();
                    
                    if(mainSignal == 'none'){
                        $("#mainSignal").css("background-color",'');
                    }else{
                        $("#mainSignal").css("background-color",mainSignal_hex);
                    }
                    
                    if (mainSignal == "green" || mainSignal == "gray"  ) {
                        
                        $("#propelTR").css("display","");
                        $("#problemTR").css("display","none");
                        
                    }else if(mainSignal == "red" || mainSignal == "yellow" ) {
                        
                        $("#propelTR").css("display","none");
                        $("#problemTR").css("display","");
                    }
                    
                    if(mainSignal == 'red'){
                        mainSignal = 'R'
                    }
                    if(mainSignal == 'yellow'){
                        mainSignal = 'Y'
                    }
                    if(mainSignal == 'green'){
                        mainSignal = 'G'
                    }
                    if(mainSignal == 'gray'){
                        mainSignal = 'P'
                    }
                    
                    if(mainSignal == 'none' || typeof mainSignal == "undefined"){
                        $('#mainSignal').html("<div id='checkedPlanDiv'><font color='red'><b>※미설정</b></font></div>");
                        flashEventStart();
                    }else{
                        $('#mainSignal').html("<font color='#FFFFFF' size ='20'><b>"+mainSignal+"</b></font>");
                        flashEventClose();
                    }
                    
                    hideProcessing();
                },
                
                error    : function(xhr, status, error){
                             hideProcessing();
                             alert(xhr+"  "+status+"  "+error);
                             
                }
            });
        }catch(e){
            alert(e);
        }
        
        
        
        
        var cnt = 1;
        var hex;
        var resultDate;
        var collaboTeam;
        var subject;
        var re_cnt = 0;
        var tempTxt = '';
        var gubun = '';
        var planDate = '';
        var mainSignal;
        
        try{
            $.ajax({
                url : "/plm/ext/sales/project/getShowonlyOneSalesView.do?oid=ext.ket.sales.entity.KETSalesProject:"+oid+"&option=list" ,
                type : "POST",
                dataType : 'json',
                async : false,
                cache : false,
                success: function(data) {
                    
                    $.each(data, function(i) {
                        
                        /* if (typeof this.mainSignal == "undefined") {
                            mainSignal = 'none';
                        }else{
                            mainSignal = this.mainSignal;
                        } */
                        
                        hex = this.hex;
                        resultDate = this.resultDate;
                        if(resultDate == null){
                            resultDate = "";
                        }
                        planDate = this.planDate;
                        gubun = this.gubun;
                        
                        /* if(i<16){
                            $('#viewTable2').find('tr').each(function(j,val){
                                
                                if(j==0){
                                    if(i < 15){
                                        if(gubun == 'BASIC'){
                                            $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+resultDate+"</b><div style='position:relative;'><img style='position:absolute; top:-57px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div>");
                                        }else{
                                            $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+resultDate+"</b></font>");
                                        }
                                        $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+resultDate+"</b></font>");
                                        
                                        $('#viewTable2 tr:eq(0) td:eq(' + cnt + ')').css('background-color', hex);
                                        
                                        cnt++;
                                    }
                                }
                            });
                        } */
                        
                        if(i < 15){
                            if(gubun == 'BASIC'){
                                $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'>"+planDate+"</font><div style='position:relative;'><img style='position:absolute; top:-57px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div>");
                            }else{
                                $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'>"+planDate+"</font></font>");
                            }
                            $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'>"+resultDate+"</font></font>");
                            
                            $('#viewTable2 tr:eq(0) td:eq(' + cnt + ')').css('background-color', hex);
                            
                            cnt++;
                        }
                        
                        
                    });
                    
                    
                    if(cnt<16){
                        
                        re_cnt = 16-cnt;
                        
                        for(k=0; k<re_cnt; k++){
                            /* $('#viewTable2').find('tr').each(function(j,val){
                                if(j==1){
                                    $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b></b></font>");
                                    $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'><b></b></font>");
                                    $('#viewTable2 tr:eq(0) td:eq(' + cnt + ')').css('background-color', '');
                                    cnt++;
                                }
                            }); */
                            $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b></b></font>");
                            $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'><b></b></font>");
                            $('#viewTable2 tr:eq(0) td:eq(' + cnt + ')').css('background-color', '');
                            cnt++;
                            
                        }
                        
                    }
                    /* flashEventClose();
                    
                    if(mainSignal == 'none'){
                        $("#mainSignal").css("background-color",'');
                    }else{
                        $("#mainSignal").css("background-color",mainSignal);
                    }
                    
                    if (mainSignal == "green" || mainSignal == "yellow"  ) {
                        
                        $("#propelTR").css("display","");
                        $("#problemTR").css("display","none");
                        
                    }else if(mainSignal == "red") {
                        
                        $("#propelTR").css("display","none");
                        $("#problemTR").css("display","");
                    }
                    
                    if(mainSignal == 'red'){
                        mainSignal = 'R'
                    }
                    if(mainSignal == 'yellow'){
                        mainSignal = 'Y'
                    }
                    if(mainSignal == 'green'){
                        mainSignal = 'G'
                    }
                    
                    if(mainSignal == 'none' || typeof mainSignal == "undefined"){
                        $('#mainSignal').html("<div id='checkedPlanDiv'><font color='red'><b>※미설정</b></font></div>");
                        flashEventStart();
                    }else{
                        $('#mainSignal').html("<font color='#000000'><b>"+mainSignal+"</b></font>");
                        flashEventClose();
                    } */
                    
                    cnt=1;
                    re_cnt=0;
                    
                    $.each(data, function(i) {
                        collaboTeam = this.collaboTeam;
                        
                        /* if(i<16){
                            $('#viewTable2').find('tr').each(function(j,val){
                                if(j==3){
                                    if(i < 15){
                                        $('#viewTable2 tr:eq(3) td:eq(' + cnt + ')').html("<font color='#000000'>"+collaboTeam+"</font>");
                                        cnt++;
                                    }
                                }
                            });
                        } */
                        if(i < 15){
                            $('#viewTable2 tr:eq(3) td:eq(' + cnt + ')').html("<font color='#000000'>"+collaboTeam+"</font>");
                            cnt++;
                        }
                    });
                    
                    if(cnt<16){
                        
                        re_cnt = 16-cnt;
                        
                        for(k=0; k<re_cnt; k++){
                            /* $('#viewTable2').find('tr').each(function(j,val){
                                if(j==3){
                                    $('#viewTable2 tr:eq(3) td:eq(' + cnt + ')').html("<font color='#000000'></font>");
                                    cnt++;
                                }
                            }); */
                            $('#viewTable2 tr:eq(3) td:eq(' + cnt + ')').html("<font color='#000000'></font>");
                            cnt++;
                            
                        }
                        
                    }
                    
                    
                    cnt=1;
                    re_cnt=0;
                    
                    $.each(data, function(i) {
                        subject = this.subject;
                        
                        if(i<16){
                            /* $('#viewTable2').find('tr').each(function(j,val){
                                if(j==3){
                                    if(i < 15){
                                        $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'>"+subject+"</font>");
                                        cnt++;
                                    }
                                }
                            }); */
                            if(i < 15){
                                $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'>"+subject+"</font>");
                                cnt++;
                            }
                        }
                    });
                    
                    if(cnt<16){
                        
                        re_cnt = 16-cnt;
                        
                        for(k=0; k<re_cnt; k++){
                            /* $('#viewTable2').find('tr').each(function(j,val){
                                if(j==3){
                                    $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'></font>");
                                    cnt++;
                                }
                            }); */
                            $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'></font>");
                            cnt++;
                            
                        }
                        
                    }
                    
                },
                
                error    : function(xhr, status, error){
                             hideProcessing();
                             alert(xhr+"  "+status+"  "+error);
                             
                }
            });
        }catch(e){
            alert(e);
        }


        
        hideProcessing();
        
        
       
    }
    //alert('${sales.projectName}');
    
}

function pagingShow(arr){
    
    
    var html = '';
    var pagehtml = '';
       
    
    html  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
    html += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
        //html += "<span id=\"pageNum\">"+pageCount+"</span>/"+totalPageCount;
    
    if(totalPageCount <= groupPageCount ) {
        groupPageCount = totalPageCount
    }else{
        groupPageCount=20
    }
    var oid = "";
        
    for ( var i=1; i <= groupPageCount; i++ ) {
        oid = arr[i-1];
            if(i == 1){html += "<a href='javascript:loadData("+i+",false,"+oid+")' id='page"+i+"'; style='color:red'>"+i+"</a>&nbsp"}
            else{html += "<a href='javascript:loadData("+i+",false,"+oid+")' id='page"+i+"';>"+i+"</a>&nbsp"}
    }
    html += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    html += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
//  $("#totalPageDiv").html(html);
    $("#pagingDiv").html(html);
    
    
}
   
function beforePage(){
    var divide = pageNum;
    $("#page"+pageNum).css("color","");
    if(pageNum == 1) {pageNum}
    else {pageNum--};
    $("#pageNum").html(pageNum);
    $("#page"+pageNum).css("color","red");
    if(pageNum == Number(groupPageCount)){beforePageGroup();}
    else if(divide == pageNum) {}
    else{ loadData(pageNum,false,arr[pageNum-1]);}
}

function nextPage(){

    var divide = pageNum;
    $("#page"+pageNum).css("color","");
    if(pageNum == totalPageCount) {pageNum}
    else{pageNum++;}    
    $("#page"+pageNum).css("color","red");
    $("#pageNum").html(pageNum);
    if(pageNum == Number(groupPageCount+1)){nextPageGroup();}
    else if(divide == pageNum) {}
    else{ loadData(pageNum,false,arr[pageNum-1]);}
}


function beforePageGroup(){
    
     groupPageNum--;
    if(groupPageNum <= 0) { groupPageNum =1; }
    
    var offSet = groupPageCount * (groupPageNum - 1) + 1;
    if(offSet < 0) {offSet = 1;}
    else{offSet};
    var limit = offSet + (groupPageCount - 1);
    
    var pagehtml='';
    groupCount = offSet;
    pagehtml  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
    var oid = "";
    for(var i =offSet; offSet <= limit; offSet++){
        oid = arr[offSet-1];
        if(offSet == i){pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='color:red'>"+offSet+"</a>&nbsp"}
        else{pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"';>"+offSet+"</a>&nbsp"}
        
    }
    pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
    if(totalPageCount > 20){
    $("#pageNum").html(groupCount);
    $("#pagingDiv").html(pagehtml);
    
    loadData(groupCount,false,arr[groupCount-1]);
    }
}

function nextPageGroup(){
   
    
    groupPageNum++;
    var maxPageCount = parseInt(totalPageCount / groupPageCount);
    var plus = (groupPageCount > (totalPageCount % groupPageCount)) ?    1 : 0;

    if(groupPageNum >= parseInt(maxPageCount+plus)){groupPageNum = parseInt(maxPageCount+plus)}
    else{groupPageNum}
    
        
    var offSet = groupPageCount * (groupPageNum - 1) + 1;
    var limit = offSet + (groupPageCount - 1);
    if(limit > totalPageCount) {limit = totalPageCount;}
    else{limit};

    var pagehtml='';
    groupCount = offSet;
    pagehtml  = "<a href='javascript:beforePageGroup();'><img src='/plm/portal/images/ico_left_go.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:beforePage();'><img src='/plm/portal/images/ico_left_go2.gif' /></a>&nbsp;&nbsp&nbsp;&nbsp";
    var oid = "";
    for(var i =offSet; offSet <= limit; offSet++){
        oid = arr[offSet-1];
        if(offSet == i){pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='color:red'>"+offSet+"</a>&nbsp"}
        else{pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"';>"+offSet+"</a>&nbsp"}
        
    }
    pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
   if(totalPageCount > 20){
    $("#pageNum").html(groupCount);
    $("#pagingDiv").html(pagehtml);
    
    loadData(groupCount,false,arr[groupCount-1]);
   }
}


/* function capture() {
    alert("이미지 추출 및 PPT생성 작업을 시작합니다.완료 메세지가 뜰때까지 절대 화면을 이동하지마십시오.");
    for ( var i=1; i <= groupPageCount; i++ ) {
        oid = arr[i-1];
        loadData(i,false,oid);
        $('#allcapture').append($("#capture_content").clone(true));
    }
    var allcapture = $("#allcapture").children();
    var capturecnt = 0;
    var errcnt = 0;
    var filename = "";
    $(allcapture).each(function(i){
        if ($(this).is("#capture_content")) {
            
            html2canvas(this, {
                onrendered: function(canvas) {
                  //document.body.appendChild(canvas);
                  $("#csimgSrc").val(canvas.toDataURL("image/png"));
                  $("#degree").val(capturecnt);
                  for(var x=0;x<1000;x++){
                      
                  }
                  capturecnt++;
                  
                  $.ajax({
                      type: 'post',
                      async: true,
                      cache: false,
                      url: '/plm/ext/sales/project/csMeetingDataCreate.do',
                      data : $('[name=captureDataForm]').serialize(),
                      success: function (data) {
                          try{
                              if(data == 'S'){
                                  
                              }else{
                                  errcnt++;
                              }
                              
                          }catch(e){errcnt++; }
                      },
                      fail : function(){
                          errcnt++;
                      }
                  });
                  
                }
                
          
              });
            
        }
        
    });
    
    if(errcnt > 0){
        alert("오류 발생!");
    }else{
        errcnt = 0;
        
        $(allcapture).each(function(i){
            if ($(this).is("#capture_content")) {
                filename += "test"+i+".png,";
            }
        });
        
        $("#taskEdit").val(filename);
        $.ajax({
            type: 'post',
            async: true,
            cache: false,
            url: '/plm/ext/sales/project/csMeetingCreatePPTX.do',
            data : $('[name=captureDataForm]').serialize(),
            success: function (data) {
                try{
                    if(data == 'Fail'){
                        errcnt++;
                    }else{
                        alert("PPT생성완료!");
                        $("#allcapture").empty();
                        $("#taskEdit").val('');
                        $("#degree").val('');
                        $("#csimgSrc").val('');
                        
                        oid = arr[0];
                        loadData(1,false,oid);
                    }
                    
                }catch(e){errcnt++; }
            },
            fail : function(){
                errcnt++;
            }
        });
    }
} */
var _promiseCapture = function (obj) {//캡쳐
	showProcessing();
	$("#alramtd").html("<br><br><br><br>화면을 캡쳐하고 있습니다..");
    return new Promise(function (resolve, reject) {

        html2canvas(obj, {
            onrendered: function(canvas) {
                var data = canvas.toDataURL('image/png');
                resolve(data);
            }
        });
    });
};

var _promiseCreateImg = function (data,i) {//img 파일 생성
	showProcessing();
	$("#alramtd").html("<br><br><br><br>이미지 파일을 생성하고 있습니다..");
    return new Promise(function (resolve, reject) {
        $("#csimgSrc").val(data);
        $("#csimgCnt").val(i);
        
        $.ajax({
            type: 'post',
            async: false,
            cache: false,
            url: '/plm/ext/sales/project/csMeetingDataCreate.do',
            data : $('[name=captureDataForm]').serialize(),
            success: function (data) {
                try{
                    if(data == 'S'){
                        resolve("csMeeting"+i+".png,");
                    }else{
                        
                    }
                    
                }catch(e){ }
            },
            fail : function(){
               
            }
        });
    });
};

var _promiseCreatePPT = function (allcnt) {//ppt 파일 생성(이미지 삽입)
	showProcessing();
	$("#alramtd").html("<br><br><br><br>ppt파일을 생성하고 있습니다..");
    return new Promise(function (resolve, reject) {

        $("#csimgNaming").val(allcnt);
        $.ajax({
            type: 'post',
            async: false,
            cache: false,
            url: '/plm/ext/sales/project/csMeetingCreatePPTX.do',
            data : $('[name=captureDataForm]').serialize(),
            success: function (data) {
                try{
                    if(data == 'S'){
                        resolve(data);
                    }else if(data == 'Fail'){
                    }else{
                    }
                    
                }catch(e){}
            },
            fail : function(){
            }
        });
    });
};

function captureContentReset(){
	showProcessing();
    $("#allcapture").empty();
    $("#csimgNaming").val('');
    $("#degree").val('');
    $("#csimgSrc").val('');
}


var _promiseloop = function (start,end) {
console.log("start : "+start + " end : "+end);
showProcessing();
    return new Promise(function (resolve, reject) {

        for ( var i=start; i <= end; i++ ) {
            $('#testline').text(i);
            oid = arr[i];
            if(typeof oid == "undefined"){
            	continue;
            }
            loadData(i,false,oid);
            $('#allcapture').append($("#capture_content").clone(true));
            resolve(oid);
        }
    });
};


var _promiseCreateDir = function () {
	
	showProcessing();
    return new Promise(function (resolve, reject) {
        var csDir = $("#csDir").val();
        if(csDir == ''){
            $.ajax({
                type: 'post',
                async: false,
                cache: false,
                url: '/plm/ext/sales/project/csMeetingCreateDir.do',
                success: function (data) {
                    try{
                        $("#csDir").val(data);
                        resolve(data);
                        
                    }catch(e){}
                },
                fail : function(){
                }
            });
        }else{
            resolve(csDir);
        }
        
        
    });
};

function csDownLoad(type){
	var agent = navigator.userAgent.toLowerCase();
	
	if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {//ms 익스플로러 인지 확인
		alert("현재 사용하는 인터넷브라우저가 이미지 생성작업에\r\n최적화되어 있지 않습니다.\n\r변환 작업 후 파일에 문제가 있다면\r\n구글 크롬 브라우저에서 해당 기능을 다시 실행해보시기 바랍니다.");
	} 
	
	showProcessing();
	$("#csFileType").val(type);
	alert("이미지 추출 및 PPT생성 작업을 시작합니다.\r\n\r\n완료 메세지가 뜰때까지 절대 화면을 이동하거나 닫지 마십시오.");
	
    $("#parentAlram").css("display","");
	$("#alramtd").html("<br><br><br><br>CS회의 데이터를 가공중입니다..");
	
  	window.setTimeout(function () {
		capture();
    }, 1000);
}

var start = 0;
var end = 14;
var checkcnt = 0;
var allcnt = 0;

function capture(){
	
    if(start == 0){
        if(totalPageCount < 14){
            end = totalPageCount-1;
        }
    }
/*     if(arr.length > 9){
        end = 14;   
    }else{
        end = arr.length-1;
    } */
    
/*     for ( var i=1; i <= groupPageCount; i++ ) {
        oid = arr[i-1];
        loadData(i,false,oid);
        $('#allcapture').append($("#capture_content").clone(true));
    } */
    
    
    _promiseCreateDir()
    .then(function (dir) {
    	
        if(dir == 'Fail'){
            alert("폴더생성시 오류가 발생했습니다.\r\n관리자에게 문의하십시오.");
            return;
        }
        _promiseloop(start,end)
        .then(function (screenShot) {
        
            start = start+14;

            end = start+14;

            if(end > totalPageCount){
                end = totalPageCount-1;
            }
            
            
            //console.log(pjtOid+"의 index = "+arr.indexOf(pjtOid));
            
            var allcapture = $("#allcapture").find('#capture_content');
            var total = ($(allcapture).length)-1;
            
            $(allcapture).each(function(i){
                //if ($(this).is("#capture_content")) {
                    //Promise 실행
                    var pjtOid = this.children[0].children[0].value;
                    _promiseCapture(this)
                    .then(function (screenShot) {
                    	
                        $(this).remove();
                        _promiseCreateImg(screenShot,arr.indexOf(pjtOid))
                        .then(function (filename) {
                            // png 성공시
                            
                            console.log("png파일생성성공.."+filename);
                            
                            checkcnt++;
                            
                            if(totalPageCount-1 != 0){
                                allcnt++;
                            }
                            
                            if(checkcnt == 14){
                                checkcnt = 0;
                                captureContentReset();
                                
                                capture();
                            }
                             if(totalPageCount-1 == allcnt){
                                window.setTimeout(function () {//마지막 파일이 생성될때까지 2초 기다린다. 혹시나해서.
                                    _promiseCreatePPT(allcnt)
                                    .then(function (text) {
                                        // ppt 성공시
                                        console.log("ppt파일생성성공.."+i+" "+total);
                                        $("#alramtd").html("<br><br>파일 생성완료!");
                                        $("#alramtd").html("");
                                        $("#parentAlram").css("display","none")
                                        captureContentReset();
                                        oid = arr[0];
                                        loadData(1,false,oid);
                                        hideProcessing();
                                        alert("모든 작업이 끝났습니다.생성된 자료를 다운로드 합니다.");
                                        start = 0;
                                        end = 14;
                                        checkcnt = 0;
                                        allcnt = 0;
                                        csPPTDown();
                                    });
                                    
                                }, 2000);
                            }
                            
                        });
                    });
                //}
            });
        
        });
        
    });
    
    
}



function csPPTDown (){
    
    /* var url = '/plm/ext/sales/project/csFileOpen.do';
    
    $('[name=csPPTdownload]').attr('action', url);
    $('[name=csPPTdownload]').attr('target', 'download');
    $('[name=csPPTdownload]').submit(); */
    //showProcessing();
    csDir = $("#csDir").val();
    csFileName = $("#csFileName").val();
    csFileType = $("#csFileType").val();
    showProcessing();
    $.fileDownload('/plm/ext/sales/project/csFileOpen.do?csDir='+csDir+"&csFileName="+csFileName+"&csFileType="+csFileType,{
         successCallback : function(){
             hideProcessing();
             $("#csDir").val('');
         },
         failCallback: function(responseHtml, url) {
             hideProcessing();
         }
    });
}

</script>
<form name="innoditorDataForm" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="webEditor" rows="0" cols="0" style="display: none">${sales.webEditor}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<form name="innoditorDataForm1" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="propelwebEditor" rows="0" cols="0" style="display: none">${sales.propelwebEditor_1}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<form name="innoditorDataForm2" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="problemwebEditor" rows="0" cols="0" style="display: none">${sales.problemwebEditor_1}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<form name="innoditorDataForm3" method="post">
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form Start -->
    <textarea name="planwebEditor" rows="0" cols="0" style="display: none">${sales.planwebEditor_1}</textarea>

    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <input type="hidden" name="hdnBackgroundColor" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) #FFFFFF, rgb(0,0,0) -->
    <input type="hidden" name="hdnBackgroundImage" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) "/image/sample.gif" -->
    <input type="hidden" name="hdnBackgroundRepeat" value="" />
    <!-- DB에서 읽어온 정보 설정할 것 ex) repeat -->
    <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
    <!-- 이노디터에서 작성된 내용을 읽어온 후 내용을 가지고 있는 Form End -->
</form>
<form name="captureDataForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="csimgSrc" id="csimgSrc" />
<input type="hidden" name="csimgCnt" id="csimgCnt" />
<input type="hidden" name="csimgNaming" id="csimgNaming" />
<input type="hidden" name="csDir" id="csDir" />
<input type="hidden" name="csFileName" id="csFileName" value="${sales.csFileName}"/>
<input type="hidden" name="csFileType" id="csFileType"/>

</form>
<body style="overflow-Y:hideen" class="popup-background popup-space">
<div class="contents-wrap">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" />Project Card</div>
<table align="center" width="100%" height="100%">
<tr>
    <td id="parentAlram" name="parentAlram" style='display:none' align="center">
	    <div style='position:relative;'>
	        <div style='position:absolute; top:50px; left:170px'>
			    <b>
				    <span style="font-size:60px; color:red; font-family: NanumGothic, 나눔고딕, Nanumgo, Dotum; font-weight: bold; font-family: NanumBold !important;">
				        <!--<div id="alramtd" name="alramtd">얍얍얍</div>  -->
				        
				        <div class="pop-layer" id="alramtd">
						    <div class="pop-container">
						        <div class="pop-conts"><br><br>ddd</div>
						    </div>
					    </div>


				    </span>
			    </b>
		    </div>
		</div>
    </td>
</tr>



</table>
<table align="right">
    <tr>
    <td align="right">
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:csDownLoad('');" class="btn_blue">PPT 다운로드</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:csDownLoad('zip');" class="btn_blue">압축파일 다운로드</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                            </table>
                        </td>
                    </table>
                </td>
            </tr>
        </table>
    </td>
    </tr>
</table>

</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space5"></td>
    </tr> 
</tr>
</table>
<div id="capture_content">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <input type="hidden" id="pjtOid" name="pjtOid" value=""/>
    <tr>
    <td width="5">&nbsp;<br><br></td> 
    <td class="font_03" align="center" id="centerName" name="centerName">${sales.centerName} - ${sales.pmdept}</td>
    </tr>
</tr>
</table>

<table id="salesTargetTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
    <tbody>
    <tr>
        <td valign="top">    
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
              <form name="salesCsviewForm" method="post" enctype="multipart/form-data">
              <input type="hidden" id="page" name="page" value="1"/>
              
              
             
              <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 -->
                  <colgroup>
                    <col width="6%">
                    <col width="14%">
                    <col width="17%">
                    <col width="23%">
                    <col width="14%">
                    <col width="14%">
                    <col width="12%">
                  </colgroup>
                  <tbody>
                  <tr>
                    
                    <c:if test="${sales.mainSignal=='green'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="4" colspan="1" style="background-color:#b0d148; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='red'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="4" colspan="1" style="background-color:#e42a3d; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='yellow'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="4" colspan="1" style="background-color:#F8D200; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='gray'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="4" colspan="1" style="background-color:#e4e4e4; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='none'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="4" colspan="1" style="background-color:none; text-align:center">
                    </c:if>
                        
                        
                    <c:choose>  
                    <c:when test="${sales.mainSignal=='none'}">
                    <div id="checkedPlanDiv"><font color="red"><b>※미설정</b></font></div>
                    </c:when>
                    <c:otherwise>
                        <font color="#FFFFFF" size ="20">
                        <b>
                        <c:if test="${sales.mainSignal=='green'}" >G</c:if>
                        <c:if test="${sales.mainSignal=='red'}" >R</c:if>
                        <c:if test="${sales.mainSignal=='yellow'}" >Y</c:if>
                        <c:if test="${sales.mainSignal=='gray'}" >P</c:if>
                        </b>
                        </font>
                    </c:otherwise>
                    </c:choose>
                    </td>
                    <td class="tdgrayMdeep"><b>중요도</b></td>
                    <td class="tdgrayMdeep"><b>프로젝트 코드</b></td>
                    <td class="tdgrayMdeep"><b>프로젝트 명</b></td>
                    <td class="tdgrayMdeep"><b>제품구분</b></td>
                    <td class="tdgrayMdeep"><b>고객사</b></td>
                    <td class="tdgrayMdeep"><b>영업팀</b></td>
                  </tr>
                  <tr>
                    <td id="rankName" name="rankName"  class="tdwhiteMdeep">${sales.rankName}</td>
                    <td id="projectNo" name="projectNo"  class="tdwhiteMdeep">${sales.projectNo}</td>
                    <!--<td class="tdwhiteL" style="text-overflow: ellipsis; overflow: hidden;"><span title='테스트'>테스트</span></td> //문자열이 테이블 width를 넘어서는 경우 잘라버리고 마우스 오버시 툴팁 제공 -->
                    <td id="projectName" name="projectName"  class="tdwhiteMdeep">${sales.projectName}</td>
                    <td id="mainPartClz" name="mainPartClz"  class="tdwhiteMdeep">${sales.mainPartClz}</td>
                    <td id="customerName" name="customerName"  class="tdwhiteMdeep">${sales.customerName}</td>
                    <td id="pmdept" name="pmdept"  class="tdwhiteMdeep">${sales.pmdept}</td>
                  </tr>
                  <!-- <tr>
                    <td rowspan='2' class="tdgrayMdeep"><b>자동차사</b></td>
                    <td rowspan='2' class="tdgrayMdeep"><b>적용부</b></td>
                    <td rowspan='2' class="tdgrayMdeep"><b>경쟁사</b></td>
                    <td colspan='2' class="tdgrayMdeep"><b>매출 [단위 : 백만원]</b></td>
                    <td rowspan='2' class="tdgrayMdeep"><b>상태</b></td>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep"><b>총 매출</b></td>
                    <td class="tdgrayMdeep"><b>연간 매출</b></td>
                  </tr> -->
                  <tr>
                    <td class="tdgrayMdeep"><b>자동차사</b></td>
                    <td class="tdgrayMdeep"><b>적용부</b></td>
                    <td class="tdgrayMdeep"><b>경쟁사</b></td>
                    <td class="tdgrayMdeep"><b>총 매출 (백만원)</b></td>
                    <td class="tdgrayMdeep"><b>연간 매출 (백만원)</b></td>
                    <td class="tdgrayMdeep"><b>상태</b></td>
                  </tr>
                  <tr>
                    <td id="lastBuyerName" name="lastBuyerName" class="tdwhiteMdeep">${sales.lastBuyerName}</td>
                    <td id="applyArea" name="applyArea" class=tdwhiteMdeep>${sales.applyArea}</td>
                    <td id="competitorName" name="competitorName" class="tdwhiteMdeep">${sales.competitorName}</td>
                    <td id="mainExpectSalesTotal" name="mainExpectSalesTotal" class="tdwhiteMdeep"><fmt:formatNumber>${sales.mainExpectSalesTotal}</fmt:formatNumber></td>
                    <td id="mainExpectSalesYear" name="mainExpectSalesYear" class="tdwhiteMdeep"><fmt:formatNumber>${sales.mainExpectSalesYear}</fmt:formatNumber></td>
                    <td id="salesStateName" name="salesStateName" class="tdwhiteMdeep">${sales.salesStateName}</td>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep"><b>영업목표</b></td>
                    <td colspan="7" class="tdwhiteLdeep">
                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                    <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                  </tr>
                  </tbody>
              </table>
              <div style="width:100%"; overflow-x:scroll">
              <table id="viewTable2" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 적용 -->
                  <colgroup>
                    <col width="1.92%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                    <col width="2%">
                  </colgroup>
                  <tbody>
                  <tr>
                    <td class="tdgrayMdeep2"><b>Step</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdgrayMdeep2" style="background-color:${taskList.hex};">${status.index+1}</td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdgrayMdeep2">${sales.taksLineUpList.size()+status.index}</td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>계획일</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteMdeep2" name='planDate' id='planDate'><font color="#000000">${taskList.planDate}</font><c:if test="${taskList.gubun=='BASIC'}" ><div style="position:relative;"><img style='position:absolute; top:-42px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div></c:if></td>
                    
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteMdeep2"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>완료일</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteMdeep2" name='resultDate' id='resultDate'><font color="#000000">${taskList.resultDate}</font></td>
                    
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteMdeep2"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>추진팀</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteMdeep2" ><font color="#000000">${taskList.collaboTeam}</font></td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteMdeep2"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>Subject</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteMdeep2"><font color="#000000">${taskList.subject}</font></td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteMdeep2"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  </tbody>
              </table>
              </div>
              <table id="viewTable3" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 적용 -->
                <colgroup>
                <col width="6%">
                <col width="44%">
                <col width="6%">
                <col width="44%">
                </colgroup>
                <tr id="problemTR" style="display:none;">
                    <td class="tdgrayMdeep2"><b>문제점</b></td>
                    <td class="tdwhiteLdeep2">
                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                    <div id="divView2" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                    <td class="tdgrayMdeep2"><b>해결방안</b></td>
                    <td class="tdwhiteLdeep2">
                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                    <div id="divView3" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                </tr>
              </table>
              <table id="viewTable4" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 적용 -->
                <colgroup>
                <col width="6%">
                <col width="94%">
                </colgroup>
                <tr id="propelTR" style="display:none;">
                    <td class="tdgrayMdeep2"><b>진행현황</b></td>
                    <td class="tdwhiteLdeep2">
                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                    <div id="divView1" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                </tr>
              </table>
              <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
          </form>
          </td>
        </tr>
        </tbody>
</table>
</div>
<div class="b-space30"></div>
</body>
<div id="pagingDiv" align="center" style="display:none;"></div>
<div id="allcapture">   
</div>
<form name="csPPTdownload" action="post" target="download">
</form>
<iframe name="download"  style="display:none;"></iframe>
</c:if>