<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>

<%
boolean isAdmin = CommonUtil.isAdmin();
%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bluebird/3.3.4/bluebird.min.js"></script>
<script type="text/javascript">

var repeat;

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
    
    
    if (mainSignal == "green") {
        
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
    
})

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

function loadData(page, type, oid){
    
    showProcessing();
    
    $("#page").val(page);
    $("a").css("color","");
    $("#page"+page).css("color","red");
    $("#pageNum").html(page);
    pageNum = page; 

    if(setPagingCheck == 0){
        $.ajax({
            url : "/plm/ext/sales/project/pageShowSalesCSPage.do?oid="+'${sales.oid}' ,
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
                        }
                        $('#rankName').html("<font color='#000000'>"+this.rankName+"</font>");
                        $('#projectNo').html("<font color='#000000'>"+this.projectNo+"</font>");
                        $('#projectName').html("<font color='#000000'>"+this.projectName+"</font>");
                        $('#mainPartClz').html("<font color='#000000'>"+this.mainPartClz+"</font>");
                        $('#customerName').html("<font color='#000000'>"+this.customerName+"</font>");
                        $('#pmdept').html("<font color='#000000'>"+this.pmdept+"</font>");
                        
                        $('#lastBuyerName').html("<font color='#000000'>"+this.lastBuyerName+"</font>");
                        $('#applyArea').html("<font color='#000000'>"+this.applyArea+"</font>");
                        $('#competitorName').html("<font color='#000000'>"+this.competitorName+"</font>");
                        $('#mainExpectSalesTotal').html("<font color='#000000'>"+this.expectSalesTotal+"</font>");
                        $('#mainExpectSalesYear').html("<font color='#000000'>"+this.expectSalesYear+"</font>");
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
                        $("#mainSignal").css("background-color",mainSignal);
                    }
                    
                    if (mainSignal == "green"  ) {
                        
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
                    
                    if(mainSignal == 'none' || typeof mainSignal == "undefined"){
                        $('#mainSignal').html("<div id='checkedPlanDiv'><font color='red'><b>※미설정</b></font></div>");
                        flashEventStart();
                    }else{
                        $('#mainSignal').html("<font color='#000000'><b>"+mainSignal+"</b></font>");
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
                                $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+planDate+"</b><div style='position:relative;'><img style='position:absolute; top:-57px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div>");
                            }else{
                                $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+planDate+"</b></font>");
                            }
                            $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'><b>"+resultDate+"</b></font>");
                            
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


function capture() {
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
}
var _promise = function (obj) {

    return new Promise(function (resolve, reject) {

        html2canvas(obj, {
            onrendered: function(canvas) {
            	var data = canvas.toDataURL('image/png');
                resolve(data);
            }
        });
    });
};

var _promise1 = function (data,i) {

    return new Promise(function (resolve, reject) {
        $("#csimgSrc").val(data);
        $("#degree").val(i);
        
        $.ajax({
            type: 'post',
            async: false,
            cache: false,
            url: '/plm/ext/sales/project/csMeetingDataCreate.do',
            data : $('[name=captureDataForm]').serialize(),
            success: function (data) {
                try{
                    if(data == 'S'){
                        resolve("test"+i+".png,");
                    }else{
                        
                    }
                    
                }catch(e){ }
            },
            fail : function(){
               
            }
        });
    });
};

var _promise2 = function (allcnt) {

    return new Promise(function (resolve, reject) {

        $("#taskEdit").val(allcnt);
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
    $("#allcapture").empty();
    $("#taskEdit").val('');
    $("#degree").val('');
    $("#csimgSrc").val('');
}


var _promiseloop = function (start,end) {

    return new Promise(function (resolve, reject) {

    	for ( var i=start; i <= end; i++ ) {
            oid = arr[i];
            loadData(i,false,oid);
            $('#allcapture').append($("#capture_content").clone(true));
            resolve(oid);
        }
    });
};

var start = 0;
var end = 14;
var checkcnt = 0;
var allcnt = 0;

function aa(){
    
    if(start == 0){
    	showProcessing();
    	alert("이미지 추출 및 PPT생성 작업을 시작합니다.\r\n\r\n완료 메세지가 뜰때까지 절대 화면을 이동하거나 닫지 마십시오.");
    }
    
    
    
/*     for ( var i=1; i <= groupPageCount; i++ ) {
        oid = arr[i-1];
        loadData(i,false,oid);
        $('#allcapture').append($("#capture_content").clone(true));
    } */
    
    _promiseloop(start,end)
    .then(function (screenShot) {
    
    	start = start+14;

        end = start+14;

        if(end > groupPageCount){
            end = groupPageCount-1;
        }
    
	    var allcapture = $("#allcapture").find('#capture_content');
	    var total = ($(allcapture).length)-1;
	    var imgfilename = "";
	    
	    $(allcapture).each(function(i){
	        //if ($(this).is("#capture_content")) {
	            //Promise 실행
	            _promise(this)
	            .then(function (screenShot) {
	            	$(this).remove();
	                _promise1(screenShot,allcnt)
	                .then(function (filename) {
	                    // png 성공시
	                    
	                    imgfilename += filename;
	                    console.log("png파일생성성공.."+imgfilename);
	                    checkcnt++;
	                    allcnt++;
	                    if(checkcnt == 14){
	                    	checkcnt = 0;
	                    	captureContentReset();
	                    	
	                    	aa();
	                    }
	                     if(groupPageCount-1 == allcnt){
	                        window.setTimeout(function () {//마지막 파일이 생성될때까지 2초 기다린다. 혹시나해서.
	                            _promise2(allcnt)
	                            .then(function (text) {
	                                // ppt 성공시
	                                console.log("ppt파일생성성공.."+i+" "+total);
	                                captureContentReset();
	                                oid = arr[0];
	                                loadData(1,false,oid);
	                                hideProcessing();
	                                alert("모든 작업이 끝났습니다.생성된 자료를 다운로드 합니다.");
	                                csPPTDown();
	                            });
	                            
	                        }, 2000);
	                    }
	                    
	                });
	            });
	        //}
	    });
    
    });
}



function csPPTDown (){
    
    /* var url = '/plm/ext/sales/project/csFileOpen.do';
    
    $('[name=csPPTdownload]').attr('action', url);
    $('[name=csPPTdownload]').attr('target', 'download');
    $('[name=csPPTdownload]').submit(); */
    //showProcessing();
    
    showProcessing();
    $.fileDownload('/plm/ext/sales/project/csFileOpen.do',{
         successCallback : function(){
             hideProcessing();   
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
<input type="hidden" name="degree" id="degree" />
<input type="hidden" name="taskEdit" id="taskEdit" />
</form>
<body style="overflow-Y:hideen" class="popup-background popup-space">
<div class="contents-wrap">
<div class="sub-title"><img src="/plm/portal/images/icon_3.png" />Project Card</div>
<table>
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
                                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:aa();" class="btn_blue">회의자료 생성</a></td>
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
<div id="capture_content" >
<table id="salesTargetTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
    <tbody>
    <tr id="captureTR" name="captureTR">
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></td>
                    
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
              <form name="salesCsviewForm" method="post" enctype="multipart/form-data">
              <input type="hidden" id="page" name="page" value="1"/>
              <input type="hidden" id="oid" name="oid" value="ext.ket.sales.entity.KETSalesProject:993231320"/>
              
             
              <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 -->
                  <colgroup>
                    <col width="6%">
                    <col width="9%">
                    <col width="20%">
                    <col width="26%">
                    <col width="15%">
                    <col width="15%">
                    <col width="13%">
                  </colgroup>
                  <tbody>
                  <tr>
                    <td id="mainSignal" name="mainSignal" class="tdwhiteM" rowspan="5" colspan="1" style="background-color:${sales.mainSignal}; text-align:center">
                    
                    <c:choose>  
                    <c:when test="${sales.mainSignal=='none'}">
                    <div id="checkedPlanDiv"><font color="red"><b>※미설정</b></font></div>
                    </c:when>
                    <c:otherwise>
                        <font color="#000000">
                        <b>
                        <c:if test="${sales.mainSignal=='green'}" >G</c:if>
                        <c:if test="${sales.mainSignal=='red'}" >R</c:if>
                        <c:if test="${sales.mainSignal=='yellow'}" >Y</c:if>
                        </b>
                        </font>
                    </c:otherwise>
                    </c:choose>
                    
                    
                    </td>
                    <td class="tdblueM">중요도</td>
                    <td class="tdblueM">프로젝트 코드</td>
                    <td class="tdblueM">프로젝트 명</td>
                    <td class="tdblueM">제품구분</td>
                    <td class="tdblueM">고객사</td>
                    <td class="tdblueM">영업팀</td>
                  </tr>
                  <tr>
                    <td id="rankName" name="rankName"  class="tdwhiteM">${sales.rankName}</td>
                    <td id="projectNo" name="projectNo"  class="tdwhiteM">${sales.projectNo}</td>
                    <!--<td class="tdwhiteL" style="text-overflow: ellipsis; overflow: hidden;"><span title='테스트'>테스트</span></td> //문자열이 테이블 width를 넘어서는 경우 잘라버리고 마우스 오버시 툴팁 제공 -->
                    <td id="projectName" name="projectName"  class="tdwhiteM">${sales.projectName}</td>
                    <td id="mainPartClz" name="mainPartClz"  class="tdwhiteM">${sales.mainPartClz}</td>
                    <td id="customerName" name="customerName"  class="tdwhiteM">${sales.customerName}</td>
                    <td id="pmdept" name="pmdept"  class="tdwhiteM">${sales.pmdept}</td>
                  </tr>
                  <tr>
                    <td rowspan='2' class="tdblueM">자동차사</td>
                    <td rowspan='2' class="tdblueM">적용부</td>
                    <td rowspan='2' class="tdblueM">경쟁사</td>
                    <td colspan='2' class="tdblueM">매출 [단위 : 백만원]</td>
                    <td rowspan='2' class="tdblueM">상태</td>
                  </tr>
                  <tr>
                    <td class="tdblueM">총 매출</td>
                    <td class="tdblueM">연간 매출</td>
                  </tr>
                  <tr>
                    <td id="lastBuyerName" name="lastBuyerName" class="tdwhiteM">${sales.lastBuyerName}</td>
                    <td id="applyArea" name="applyArea" class=tdwhiteM>${sales.applyArea}</td>
                    <td id="competitorName" name="competitorName" class="tdwhiteM">${sales.competitorName}</td>
                    <td id="mainExpectSalesTotal" name="mainExpectSalesTotal" class="tdwhiteM">${sales.mainExpectSalesTotal}</td>
                    <td id="mainExpectSalesYear" name="mainExpectSalesYear" class="tdwhiteM">${sales.mainExpectSalesYear}</td>
                    <td id="salesStateName" name="salesStateName" class="tdwhiteM">${sales.salesStateName}</td>
                  </tr>
                  <tr>
                    <td class="tdblueM">영업목표</td>
                    <td colspan="7" class="tdwhiteL">
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
                    <td class="tdblueM">Step</td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteM" style="background-color:${taskList.hex};">${status.index+1}</td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteM">${sales.taksLineUpList.size()+status.index}</td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdblueM">계획일</td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteM" name='planDate' id='planDate'><font color="#000000"><b>${taskList.planDate}</b><c:if test="${taskList.gubun=='BASIC'}" ><div style="position:relative;"><img style='position:absolute; top:-57px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div></c:if></td>
                    
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteM"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdblueM">완료일</td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteM" name='resultDate' id='resultDate'><font color="#000000"><b>${taskList.resultDate}</b></td>
                    
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteM"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdblueM">추진팀</td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteM" >${taskList.collaboTeam}</td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteM"></td>
                    </c:forEach>
                    </c:if>
                  </tr>
                  <tr>
                    <td class="tdblueM">Subject</td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <c:if test="${status.index<15}" >
                    <td class="tdwhiteM">${taskList.subject}</td>
                    </c:if>
                    </c:forEach>
                    <c:if test="${sales.taksLineUpList.size() < 16}" >
                    <c:forEach begin="1" end="${15-sales.taksLineUpList.size()}" varStatus="status">
                    <td class="tdwhiteM"></td>
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
                    <td class="tdblueM">문제점</td>
                    <td class="tdwhiteL">
                    <!-- 이노디터에서 작성된 내용을 보여주는 div Start -->
                    <div id="divView2" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    <!-- 이노디터에서 작성된 내용을 보여주는 div End -->
                    </td>
                    <td class="tdblueM">해결방안</td>
                    <td class="tdwhiteL">
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
                    <td class="tdblueM">진행현황</td>
                    <td class="tdwhiteL">
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