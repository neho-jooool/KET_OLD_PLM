﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<c:if test="${authCheckFlag == true && csEmpty == true}" >
<script>
$(document).ready(function() {
    alert('지정된 CS회의가 없습니다.');
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

</style>
<script type="text/javascript">

var repeat;
var manageOid = "";
var mainSignal_hex = "";
var isPresentCondition = "";
$(document).ready(function(){

	var taskList = '${sales.taksLineUpList.size()}';
	
	stepVisibleSet(taskList);
	
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
    
    var csEmpty = '${csEmpty}';
    
    if (mainSignal == "green" || mainSignal == "gray") {
    	
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
    }else{
    	
    	if(!csEmpty){
    		$('#pagingDiv').css("display","none");
            $('#presentCondition').css("display","none");
            $('#cs_meeting_tb').css("display","");
            $('#presentTd').html("");	
    	}
    	
    }
    
    if(mainSignal == 'none'){
    	flashEventStart();
    }
    mainSignalHexSet();
    
    
    $(function(){
        $('html').keydown(function(e){
            
            var key = e.which;
            var direction;
            switch(key){
                case 38:
                    direction = "up";
                    break;
                case 37:
                    direction = 'left';
                    break;
                case 39:
                    direction = 'right';
                    break;
                case 40:
                    direction = 'down';
                    break;
                default:
                    direction = 'none';
                    break;
            }
            if(direction == 'left'){
            	beforePage();
            }
            if(direction == 'right'){
                nextPage();
            }
            
        });
    });
    
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

function stepVisibleSet(taskList){
	
    if(taskList < 16){
        $("#viewTable2").css("display","");
        $("#cnt_exceed").css("display","none");
        tableId = $('#viewTable2');
    }else{
        $("#cnt_exceed").css("display","");
        $("#viewTable2").css("display","none");
        tableId = $('#viewTable2-1');
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
var cs_total_arr = new Array();

function pageReset(page){
	
	$("#page").val(page);
    $("a").not($(".notSelect")).css("color","");
    $("a").not($(".notSelect")).css("font-size","17px");
    $("a").not($(".notSelect")).css("font-weight","");
    
    $("#page"+page).css("color","red");
    $("#page"+page).css("font-size","22px");
    $("#page"+page).css("font-weight","bold");
    $("#pageNum").html(page);
    pageNum = page; 
}

function viewTable2Insert(data){
	var addContent1 = "<tr><td class=tdgrayMdeep2><span style='padding-left:24px'><b>Step</b><span style='padding-right:22px'></td>";
	
	var addContent2 = "<tr><td class=tdgrayMdeep2><b>계획일</td>";
	
	var addContent3 = "<tr><td class=tdgrayMdeep2><b>완료일</b></td>";
	
	var addContent4 = "<tr><td class=tdgrayMdeep2><b>추진팀</b></td>";
	
	var addContent5 = "<tr><td class=tdgrayMdeep2><b>Subject</b></td>";
	
	$.each(data, function(i) {
		addContent1 +="<td class='tdwhiteMdeep2' style='background-color:"+this.hex+"'><span style='padding-left:34px'>"+eval(i+1)+"<span style='padding-right:31px'></td>";
		
		if(this.gubun=='BASIC'){
			addContent2 +="<td class='tdwhiteMdeep2' name='planDate' id='planDate'><font color='#000000'>"+this.planDate+"</font><div style='position:relative;'><img style='position:absolute; top:-42px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div></td>";
		}else{
			addContent2 +="<td class='tdwhiteMdeep2' name='planDate' id='planDate'><font color='#000000'>"+this.planDate+"</font></td>";  
		}
		
		addContent3 += "<td class='tdwhiteMdeep2' name='resultDate' id='resultDate'><font color='#000000'>"+this.resultDate+"</font></td>";
		addContent4 += "<td class='tdwhiteMdeep2' >"+this.collaboTeam+"</td>";
		addContent5 += "<td class='tdwhiteMdeep2' >"+this.subject+"</td>";
		
		
	});
	
	addContent1 += "</tr>";
	addContent2 += "</tr>";
	addContent3 += "</tr>";
	addContent4 += "</tr>";
	addContent5 += "</tr>";
	
    $('#viewTable2-1 > tbody:last').append(addContent1);
    $('#viewTable2-1 > tbody:last').append(addContent2);
    $('#viewTable2-1 > tbody:last').append(addContent3);
    $('#viewTable2-1 > tbody:last').append(addContent4);
    $('#viewTable2-1 > tbody:last').append(addContent5);
	
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


var firstFileOid = "";

function loadData(page, type, oid){
	
	pageReset(page);
    
    
	showProcessing();
    if(setPagingCheck == 0){
    	manageOid = '${manageOid}';
        $.ajax({
            url : "/plm/ext/sales/project/pageShowSalesCSPage.do?oid="+'${sales.oid}'+"&isFile=true" ,
            type : "POST",
            dataType : 'json',
            async : false,
            cache : false,
            success: function(data) {
                
                
                
                totalPageCount = data.length;
                
                $.each(data, function(i) {
                    //totalPageCount = this.totalPageCount;
                    arr[i] = this.oid;
                    cs_total_arr[i] = this.oid;
                    if(this.firstFile != '' && typeof this.firstFile != "undefined"){
                    	firstFileOid = this.firstFile
                    }
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
                        
                        $('#hasCheckedNewEvent').html(this.hasCheckedNewEvent);
                        
                        
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
                    
                    if (mainSignal == "green" || mainSignal == "gray") {
                        
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
        
        
        
        var stepCnt = 0;
        
        try{
            $.ajax({
                url : "/plm/ext/sales/project/getShowonlyOneSalesView.do?oid=ext.ket.sales.entity.KETSalesProject:"+oid+"&option=list" ,
                type : "POST",
                dataType : 'json',
                async : false,
                cache : false,
                success: function(data) {
                	
                	$("#viewTable2-1 tr").remove();
                	stepCnt = data.length;
                	stepVisibleSet(stepCnt);
                	
                	if(stepCnt > 15){
                		viewTable2Insert(data);
                	}else{
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
                            
                            //if(i < 15){
                            if(data.length < 16){   
                                if(gubun == 'BASIC'){
                                    $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'>"+planDate+"<div style='position:relative;'><img style='position:absolute; top:-42px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div>");
                                }else{
                                    $('#viewTable2 tr:eq(1) td:eq(' + cnt + ')').html("<font color='#000000'>"+planDate+"</font>");
                                }
                                
                                $('#viewTable2 tr:eq(2) td:eq(' + cnt + ')').html("<font color='#000000'>"+resultDate+"</font>");
                                
                                $('#viewTable2 tr:eq(0) td:eq(' + cnt + ')').css('background-color', hex);
                                
                                cnt++;
                            }
                            //}
                            
                            
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
                            //if(i < 15){
                                $('#viewTable2 tr:eq(3) td:eq(' + cnt + ')').html("<font color='#000000'>"+collaboTeam+"</font>");
                                cnt++;
                            //}
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
                            
                           // if(i<16){
                                /* $('#viewTable2').find('tr').each(function(j,val){
                                    if(j==3){
                                        if(i < 15){
                                            $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'>"+subject+"</font>");
                                            cnt++;
                                        }
                                    }
                                }); */
                                //if(i < 15){
                                    $('#viewTable2 tr:eq(4) td:eq(' + cnt + ')').html("<font color='#000000'>"+subject+"</font>");
                                    cnt++;
                                //}
                            //}
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


    }
    
    hideProcessing();

    if(page == 1 && firstFileOid != ''){
    	loadAppicationData(page,'',firstFileOid);
    }

    scrollControl();
    //alert('${sales.projectName}');
    
}
function loadAppicationData(page,type,oid){
	
	if(isPresentCondition == 'NO'){
		pageReset(page);
        oid = "wt.content.ApplicationData:"+oid;
        var downUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:ext.ket.sales.entity.KETSalesCSMeetingManage:"+manageOid+"&cioids=OR:"+oid+"&role=SECONDARY&ketCustomFlag=Y>";
        window.open(downUrl,"download","width=0, height=0");
        scrollControl();
	}

	
}

function cs_paging_reset(option,type,dept,parent){

	var csEmpty = '${csEmpty}';
	if(csEmpty){
		alert("지정된 CS회의가 없습니다.");
	}else{
		var a_val = $('#page1').attr("href");
	    isPresentCondition = "OK";
	    showProcessing();
	    if(option == 1){
	        isPresentCondition = "NO";
	        //pagingShow(cs_total_arr);
	        $('#pagingDiv').css("display","");
	        $('#presentCondition').css("display","none");
	        $('#cs_meeting_tb').css("display","");
	        
	        if(a_val.indexOf("loadAppicationData") != -1){
	            loadAppicationData(1,false,cs_total_arr[0]);
	        }else{
	            loadData(1,false,cs_total_arr[0]);
	        }
	    }else if(option == 2){
	        pageReset(1);
	        
	        totalPageCount = cs_total_arr.length;
	        groupPageCount=20;
	        var divide = totalPageCount % groupPageCount
	        var pagecount = totalPageCount / groupPageCal;
	        if(divide < groupPageCount) {pagecount++;}
	        else{pagecount;}
	        
	        totalPageCount = parseInt(pagecount);
	        
	        totalPageCount = cs_total_arr.length;
	        arr = cs_total_arr;
	        
	        pagingShow(cs_total_arr);
	        $('#pagingDiv').css("display","none");
	        $('#presentCondition').css("display","");
	        $('#cs_meeting_tb').css("display","none");
	    }else if(option == 3){
	        cs_detail_paging_reset(type,dept,parent);
	        
	    }
	    hideProcessing();
	}
	
}


function cs_detail_paging_reset(type,dept,parent){

	
	pageReset(1);
	arr = new Array();
	
	$.ajax({
        url : "/plm/ext/sales/project/pageShowSalesCSDetailPage.do?type="+type+"&dept="+dept+"&parent="+parent,
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
            
            pagingShow(arr);
        },
        error    : function(xhr, status, error){
                     hideProcessing();
                     alert(xhr+"  "+status+"  "+error);
                     
        }
    });
	
	loadData(1,false,arr[0]);
	
	$('#pagingDiv').css("display","");
    $('#presentCondition').css("display","none");
    $('#cs_meeting_tb').css("display","");
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
            if(i == 1){
            	html += "<a href='javascript:loadData("+i+",false,"+oid+")' id='page"+i+"'; style='color:red; font-size:22px; font-family:Malgun Gothic; font-weight:bold'>"+i+"</a>&nbsp";
            }else{
            	if(oid.indexOf("ApplicationData") != -1){
            		oid = oid.substr(oid.indexOf(":")+1);
            		html += "<a href='javascript:loadAppicationData("+i+",false,"+oid+")' id='page"+i+"'; style='font-family:Malgun Gothic;'>"+i+"</a>&nbsp";
            	}else{
            		html += "<a href='javascript:loadData("+i+",false,"+oid+")' id='page"+i+"'; style='font-family:Malgun Gothic;'>"+i+"</a>&nbsp";
            	}
            	
            	
            	
/*             	if(oid.indexOf("ApplicationData") != -1){
            		
            		html += "<a target=download href=/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:ext.ket.sales.entity.KETSalesCSMeetingManage:993376017&cioids=OR:"+oid+"&role=SECONDARY&ketCustomFlag=Y>"+i+"</a>&nbsp";
                    
            	}else{
            		html += "<a href='javascript:loadData("+i+",false,"+oid+")' id='page"+i+"';>"+i+"</a>&nbsp";
            	} */
            	
            }
            //if(i == 1){html += "<a href='http://plm.ket.com/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:ext.ket.edm.entity.KETStampingItem:1090043892&cioids=OR:wt.content.ApplicationData:1090043896&role=SECONDARY' target='_blank'>"+i+"</a>&nbsp"}
            
            
    }
    html += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    html += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
//  $("#totalPageDiv").html(html);
    $("#pagingDiv").html(html);
    
    
}

var groupPageCount2 = 20;

function groupPageSet(pageNum){

	groupPageCount2 = 20;
	
    if(pageNum > 39){
    	
    	var lastChar = pageNum.toString().substr(0,pageNum.toString().length - 1);
    	
    	if(parseInt(lastChar) % 2 == 0){
    		lastChar = lastChar + '0';
    		groupPageCount2 = parseInt(lastChar);
    	}

        
    }
    
/*     if(pageNum > 59){
        groupPageCount2 = 60;
    }
    
    if(pageNum > 79){
        groupPageCount2 = 80;
    }
    
    if(pageNum > 99){
        groupPageCount2 = 100;
    }
    
    if(pageNum > 119){
        groupPageCount2 = 120;
    } */
}
   
function beforePage(){
	groupPageSet(parseInt(pageNum)-1);
    var divide = pageNum;
    $("#page"+pageNum).css("color","");
    $("#page"+pageNum).css("font-size","17px");
    $("#page"+pageNum).css("font-weight","");
    if(pageNum == 1) {pageNum}
    else {pageNum--};
    $("#pageNum").html(pageNum);
    $("#page"+pageNum).css("color","red");
    $("#page"+pageNum).css("font-size","22px");
    $("#page"+pageNum).css("font-weight","bold");
    if(pageNum == Number(groupPageCount2)){beforePageGroup();}
    else if(divide == pageNum) {}
    else{ 
    	var oid = arr[pageNum-1];
    	if(oid.indexOf("ApplicationData") != -1){
            oid = oid.substr(oid.indexOf(":")+1);
            loadAppicationData(pageNum,false,oid);
        }else{
        	loadData(pageNum,false,oid);
        }
    
    }
}
function scrollControl(){
    //스크롤 맨위로
    $('div').scrollTop( 0 );
    $('div').scrollLeft( 0 );
}

function nextPage(){
	groupPageSet(pageNum);
	
    var divide = pageNum;
    $("#page"+pageNum).css("color","");
    $("#page"+pageNum).css("font-family","Malgun Gothic");
    $("#page"+pageNum).css("font-size","17px");
    $("#page"+pageNum).css("font-weight","");
    if(pageNum == totalPageCount) {pageNum}
    else{pageNum++;}    
    $("#page"+pageNum).css("color","red");
    $("#page"+pageNum).css("font-family","Malgun Gothic");
    $("#page"+pageNum).css("font-size","22px");
    $("#page"+pageNum).css("font-weight","bold");
    $("#pageNum").html(pageNum);

    
    if(pageNum == Number(groupPageCount2+1)){
    	
    	nextPageGroup();
    }else if(divide == pageNum) {
    	
    }else{ 
    	
    	var oid = arr[pageNum-1];
        if(oid.indexOf("ApplicationData") != -1){
            oid = oid.substr(oid.indexOf(":")+1);
            loadAppicationData(pageNum,false,oid);
        }else{
            loadData(pageNum,false,oid);
        }
    }
   
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
        if(offSet == i){
        	if(oid.indexOf("ApplicationData") != -1){
        		oid = oid.substr(oid.indexOf(":")+1);
        		pagehtml += "<a href='javascript:loadAppicationData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='color:red; font-size:22px; font-family:Malgun Gothic; font-weight:bold'>"+offSet+"</a>&nbsp"
        	}else{
        		pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='color:red; font-size:22px; font-family:Malgun Gothic; font-weight:bold'>"+offSet+"</a>&nbsp"
        	}
        	
        }else{
        	if(oid.indexOf("ApplicationData") != -1){
                oid = oid.substr(oid.indexOf(":")+1);
                pagehtml += "<a href='javascript:loadAppicationData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='font-family:Malgun Gothic;'>"+offSet+"</a>&nbsp"
            }else{
            	pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"'; style='font-family:Malgun Gothic;'>"+offSet+"</a>&nbsp"
            }
        	
        }
        
    }
    pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
    pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
    
    if(totalPageCount > 20){
	    $("#pageNum").html(groupCount);
	    $("#pagingDiv").html(pagehtml);
	    
	    oid = arr[groupCount-1];
        if(oid.indexOf("ApplicationData") != -1){
            oid = oid.substr(oid.indexOf(":")+1);
            loadAppicationData(groupCount,false,oid);
        }else{
        	loadData(groupCount,false,oid);
        }
    }
}

function nextPageGroup(){
    
	if(totalPageCount == pageNum && pageNum.toString().length > 1){
    	var firtstChar = pageNum.toString().substr(1,1);
    	
    	if(Number(firtstChar) == 1){
    		pageNum--;
    	}
    }   
    
    if(totalPageCount == pageNum){
    	
    }else{
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
            if(offSet == i){
                
                if(oid.indexOf("ApplicationData") != -1){
                    oid = oid.substr(oid.indexOf(":")+1);
                    pagehtml += "<a href='javascript:loadAppicationData("+offSet+",false,"+oid+")' id='page"+offSet+"' style='font-family:Malgun Gothic';>"+offSet+"</a>&nbsp";
                }else{
                    pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"' style='color:red; font-size:22px; font-family:Malgun Gothic; font-weight:bold';>"+offSet+"</a>&nbsp";
                }
                
                
            }else{
                
                if(oid.indexOf("ApplicationData") != -1){
                    oid = oid.substr(oid.indexOf(":")+1);
                    pagehtml += "<a href='javascript:loadAppicationData("+offSet+",false,"+oid+")' id='page"+offSet+"'  style='font-family:Malgun Gothic';>"+offSet+"</a>&nbsp";
                }else{
                    pagehtml += "<a href='javascript:loadData("+offSet+",false,"+oid+")' id='page"+offSet+"'  style='font-family:Malgun Gothic';>"+offSet+"</a>&nbsp";
                }
                
                
            }
            
        }
        
        pagehtml += "&nbsp;&nbsp&nbsp;&nbsp;<a href='javascript:nextPage();'><img src='/plm/portal/images/ico_right_go2.gif' /></a>&nbsp&nbsp";
        pagehtml += "<a href='javascript:nextPageGroup();'><img src='/plm/portal/images/ico_right_go.gif' /></a>&nbsp&nbsp";
        
       if(totalPageCount > 20){
            $("#pageNum").html(groupCount);
            $("#pagingDiv").html(pagehtml);
            oid = arr[groupCount-1];
            if(oid.indexOf("ApplicationData") != -1){
                oid = oid.substr(oid.indexOf(":")+1);
                loadAppicationData(groupCount,false,oid);
            }else{
                loadData(groupCount,false,oid);
            }
            
            
       }
    }
} 

function csMeetingImgView(){
    getOpenWindow2("/plm/ext/sales/project/viewsalesCSMeetingImgForm.do?oid=&csYN=Y",'viewsalesCSMeetingImgFormFormPopup',1300,800);
}

function openIssueList(){
	getOpenWindow2("/plm/ext/issue/csIssueList?oid=${sales.oid}",'csIssueList',1280, 720);
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

<body style="overflow-Y:hideen" class="popup-space">
<div class="contents-wrap">
<!-- <div class="sub-title"><img src="/plm/portal/images/icon_3.png" />Project Card</div> -->

<table id="presentCondition" width="1218px" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
    <tr>
        <td valign="top">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class=space20></td>
                  </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="font_04" align="left" width="10%" style='color: #000000; font-weight: bold; font-family: Malgun Gothic;'><a href="javascript:cs_paging_reset(1);" class="notSelect">회의진행</a></td>
                    <td class="font_04" align="center" width="80%">영업수주프로젝트 현황</td>
                    <td class="font_04" align="right" width="10%"></td>
                </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class='space20'></td>
                  </tr>
            </table>
<!--             <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class='space20' style='color: #000000; font-weight: bold; font-family: Malgun Gothic;'><a href="javascript:cs_paging_reset(1);" class="notSelect">회의진행</a></td>
                  </tr>
            </table> -->
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"> <!--테이블 속성에 자동줄바꿈 -->
	            <colgroup>
		            <col width="59" /> 
		            <col width="85" /> 
		            <col width="37" span="15" /> 
		            <col width="41" span="9" /> 
	            </colgroup>
	            <tbody>
	               <tr>  
	                   <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" rowspan="3" colspan="2"><b>구분</b></td>
	                   <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" colspan="6"><b>${sales.year}년 누적</b></td>
	                   <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" colspan="18"><b>금주 (${sales.year} 년 ${sales.month} 월 ${sales.degree} 차수)</b></td>
	               </tr>
     
                   <tr>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" rowspan="2"><b>신규</b></td>
                       <td class="tdgrayMdeep4" colspan="3" style="border-bottom : 0px none;"><b>완료</b></td>
                       <td class="tdgrayMdeep4" colspan="2" style="border-bottom : 0px none;"></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" rowspan="2"><b>신규</b></td>
                       <td class="tdgrayMdeep4" colspan="4" style="border-bottom : 0px none;"><b>완료</b></td>
                       <td class="tdgrayMdeep4" colspan="3" style="border-bottom : 0px none; border-right : 0px none;"><b>진행 (상태)</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 0px none; border-left : 0px none; border-right : 0px none;"></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" colspan="9" style="border-left : 0px none;"><b>진행 (제품군)</b></td>
                   </tr>
     
                   <tr>  
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>수주</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>실패</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>취소</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>진행</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>소계</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>수주</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>실패</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><b>취소</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>소계</b></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><img src="/plm/extcore/image/ico_red.png"></img></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><img src="/plm/extcore/image/ico_yellow.png"></img></td>
                       <td class="tdgrayMdeep4" style="border-top : 1px solid #CCCCCC; border-bottom : 1px solid #CCCCCC;"><img src="/plm/extcore/image/ico_green.png"></img></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>소계</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>친환경</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>PCB</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>방수</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>비방수</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>Fuse</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>W/H</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>기타</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>JOINT</b></td>
                       <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;"><b>I/O</b></td>
                  </tr>
                  <c:set var="THIS_YEAR_NEW_TOTAL_SUM" value="0"></c:set>
                  <c:set var="THIS_YEAR_NEW_S_CNT_SUM" value="0"></c:set>
                  <c:set var="THIS_YEAR_NEW_F_CNT_SUM" value="0"></c:set>
                  <c:set var="THIS_YEAR_NEW_C_CNT_SUM" value="0"></c:set>
                  <c:set var="THIS_YEAR_NEW_I_CNT_SUM" value="0"></c:set>
                  <c:set var="THIS_YEAR_NEW_SFC_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_NEW_TOTAL_SUM" value="0"></c:set>
                  <c:set var="CS_NEW_S_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_NEW_F_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_NEW_C_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_NEW_SFC_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_ANDON_RED_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_ANDON_YELLOW_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_ANDON_GREEN_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_ANDON_RYG_CNT_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_1_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_2_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_3_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_4_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_5_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_6_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_7_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_8_SUM" value="0"></c:set>
                  <c:set var="CS_MAIN_CATEGORY_CNT_9_SUM" value="0"></c:set>
                  
                  <c:forEach items="${sales.salesPresentList}" var="presentList" varStatus="status">
                        <c:if test="${presentList.CENTERNAME == '소계'}" >
                        <c:set var="THIS_YEAR_NEW_TOTAL_SUM"    value="${THIS_YEAR_NEW_TOTAL_SUM + presentList.THIS_YEAR_NEW_TOTAL}"></c:set>
                        <c:set var="THIS_YEAR_NEW_S_CNT_SUM"    value="${THIS_YEAR_NEW_S_CNT_SUM + presentList.THIS_YEAR_NEW_S_CNT}"></c:set>
	                    <c:set var="THIS_YEAR_NEW_F_CNT_SUM"    value="${THIS_YEAR_NEW_F_CNT_SUM + presentList.THIS_YEAR_NEW_F_CNT}"></c:set>
	                    <c:set var="THIS_YEAR_NEW_C_CNT_SUM"    value="${THIS_YEAR_NEW_C_CNT_SUM + presentList.THIS_YEAR_NEW_C_CNT}"></c:set>
	                    <c:set var="THIS_YEAR_NEW_I_CNT_SUM"    value="${THIS_YEAR_NEW_I_CNT_SUM + presentList.THIS_YEAR_NEW_I_CNT}"></c:set>
	                    <c:set var="THIS_YEAR_NEW_SFC_CNT_SUM"  value="${THIS_YEAR_NEW_SFC_CNT_SUM + presentList.THIS_YEAR_NEW_SFC_CNT}"></c:set>
	                    <c:set var="CS_NEW_TOTAL_SUM"           value="${CS_NEW_TOTAL_SUM + presentList.CS_NEW_TOTAL}"></c:set>
	                    <c:set var="CS_NEW_S_CNT_SUM"           value="${CS_NEW_S_CNT_SUM + presentList.CS_NEW_S_CNT}"></c:set>
	                    <c:set var="CS_NEW_F_CNT_SUM"           value="${CS_NEW_F_CNT_SUM + presentList.CS_NEW_F_CNT}"></c:set>
	                    <c:set var="CS_NEW_C_CNT_SUM"           value="${CS_NEW_C_CNT_SUM + presentList.CS_NEW_C_CNT}"></c:set>
	                    <c:set var="CS_NEW_SFC_CNT_SUM"         value="${CS_NEW_SFC_CNT_SUM + presentList.CS_NEW_SFC_CNT}"></c:set>
	                    <c:set var="CS_ANDON_RED_CNT_SUM"       value="${CS_ANDON_RED_CNT_SUM + presentList.CS_ANDON_RED_CNT}"></c:set>
	                    <c:set var="CS_ANDON_YELLOW_CNT_SUM"    value="${CS_ANDON_YELLOW_CNT_SUM + presentList.CS_ANDON_YELLOW_CNT}"></c:set>
	                    <c:set var="CS_ANDON_GREEN_CNT_SUM"     value="${CS_ANDON_GREEN_CNT_SUM + presentList.CS_ANDON_GREEN_CNT}"></c:set>
	                    <c:set var="CS_ANDON_RYG_CNT_SUM"       value="${CS_ANDON_RYG_CNT_SUM + presentList.CS_ANDON_RYG_CNT}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_1_SUM" value="${CS_MAIN_CATEGORY_CNT_1_SUM + presentList.CS_MAIN_CATEGORY_CNT_1}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_2_SUM" value="${CS_MAIN_CATEGORY_CNT_2_SUM + presentList.CS_MAIN_CATEGORY_CNT_2}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_3_SUM" value="${CS_MAIN_CATEGORY_CNT_3_SUM + presentList.CS_MAIN_CATEGORY_CNT_3}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_4_SUM" value="${CS_MAIN_CATEGORY_CNT_4_SUM + presentList.CS_MAIN_CATEGORY_CNT_4}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_5_SUM" value="${CS_MAIN_CATEGORY_CNT_5_SUM + presentList.CS_MAIN_CATEGORY_CNT_5}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_6_SUM" value="${CS_MAIN_CATEGORY_CNT_6_SUM + presentList.CS_MAIN_CATEGORY_CNT_6}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_7_SUM" value="${CS_MAIN_CATEGORY_CNT_7_SUM + presentList.CS_MAIN_CATEGORY_CNT_7}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_8_SUM" value="${CS_MAIN_CATEGORY_CNT_8_SUM + presentList.CS_MAIN_CATEGORY_CNT_8}"></c:set>
	                    <c:set var="CS_MAIN_CATEGORY_CNT_9_SUM" value="${CS_MAIN_CATEGORY_CNT_9_SUM + presentList.CS_MAIN_CATEGORY_CNT_9}"></c:set>
                        </c:if>
                        <tr>
                            <c:if test="${status.index == 0}" >
                            <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" rowspan="4"><b>영업본부</b></td>
                            </c:if>
                            <c:if test="${status.index == 4}" >
                            <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" rowspan="${fn:length(sales.salesPresentList)-4}"><b>Global<br>사업부</b></td>
                            </c:if>
                            <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC; height: 35px;"><b>${presentList.CENTERNAME}</b></td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.THIS_YEAR_NEW_TOTAL > 0}" >
	                               <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_TOTAL','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
	                           </c:if>${presentList.THIS_YEAR_NEW_TOTAL}
	                           <c:if test="${presentList.THIS_YEAR_NEW_TOTAL > 0}" >
	                               </a>
	                           </c:if>
	                           </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.THIS_YEAR_NEW_S_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_S_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.THIS_YEAR_NEW_S_CNT}
                               <c:if test="${presentList.THIS_YEAR_NEW_S_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.THIS_YEAR_NEW_F_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_F_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.THIS_YEAR_NEW_F_CNT}
                               <c:if test="${presentList.THIS_YEAR_NEW_F_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.THIS_YEAR_NEW_C_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_C_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.THIS_YEAR_NEW_C_CNT}
                               <c:if test="${presentList.THIS_YEAR_NEW_C_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
                               <b>
                               <c:if test="${presentList.THIS_YEAR_NEW_I_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_I_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.THIS_YEAR_NEW_I_CNT}
                               <c:if test="${presentList.THIS_YEAR_NEW_I_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
                            </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.THIS_YEAR_NEW_SFC_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_SFC_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.THIS_YEAR_NEW_SFC_CNT}
                               <c:if test="${presentList.THIS_YEAR_NEW_SFC_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
	                           <b>
	                           <c:if test="${presentList.CS_NEW_TOTAL > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_NEW_TOTAL','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_NEW_TOTAL}
                               <c:if test="${presentList.CS_NEW_TOTAL > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_NEW_S_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_NEW_S_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_NEW_S_CNT}
                               <c:if test="${presentList.CS_NEW_S_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_NEW_F_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_NEW_F_CNT','${presentList.CENTERCODE},'${presentList.PARENTCODE}'');" class="notSelect">
                               </c:if>${presentList.CS_NEW_F_CNT}
                               <c:if test="${presentList.CS_NEW_F_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_NEW_C_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_NEW_C_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_NEW_C_CNT}
                               <c:if test="${presentList.CS_NEW_C_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
	                           <b>
	                           <c:if test="${presentList.CS_NEW_SFC_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_NEW_SFC_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_NEW_SFC_CNT}
                               <c:if test="${presentList.CS_NEW_SFC_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_ANDON_RED_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_ANDON_RED_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_ANDON_RED_CNT}
                               <c:if test="${presentList.CS_ANDON_RED_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_ANDON_YELLOW_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_ANDON_YELLOW_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_ANDON_YELLOW_CNT}
                               <c:if test="${presentList.CS_ANDON_YELLOW_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_ANDON_GREEN_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_ANDON_GREEN_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_ANDON_GREEN_CNT}
                               <c:if test="${presentList.CS_ANDON_GREEN_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
	                           <b>
	                           <c:if test="${presentList.CS_ANDON_RYG_CNT > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_ANDON_RYG_CNT','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_ANDON_RYG_CNT}
                               <c:if test="${presentList.CS_ANDON_RYG_CNT > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_1 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_1','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_1}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_1 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_2 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_2','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_2}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_2 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_3 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_3','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_3}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_3 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_4 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_4','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_4}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_4 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_5 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_5','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_5}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_5 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_6 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_6','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_6}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_6 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_7 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_7','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_7}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_7 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_8 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_8','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_8}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_8 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
	                        <td class="tdwhiteMdeep">
	                           <b>
	                           <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_9 > 0}" >
                                   <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_9','${presentList.CENTERCODE}','${presentList.PARENTCODE}');" class="notSelect">
                               </c:if>${presentList.CS_MAIN_CATEGORY_CNT_9}
                               <c:if test="${presentList.CS_MAIN_CATEGORY_CNT_9 > 0}" >
                                   </a>
                               </c:if>
                               </b>
	                        </td>
                        </tr>
                  </c:forEach>
                 <tr>
				    <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;" colspan="2"><b>합계</b></td>
				    <td class="tdwhiteMdeep">
					    <c:if test="${THIS_YEAR_NEW_TOTAL_SUM > 0}" >
					        <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_TOTAL_SUM','','');" class="notSelect">
	                    </c:if><b>${THIS_YEAR_NEW_TOTAL_SUM}</b>
	                    <c:if test="${THIS_YEAR_NEW_TOTAL_SUM > 0}" >
	                        </a>
	                    </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${THIS_YEAR_NEW_S_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_S_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${THIS_YEAR_NEW_S_CNT_SUM}</b>
                        <c:if test="${THIS_YEAR_NEW_S_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${THIS_YEAR_NEW_F_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_F_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${THIS_YEAR_NEW_F_CNT_SUM}</b>
                        <c:if test="${THIS_YEAR_NEW_F_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${THIS_YEAR_NEW_C_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_C_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${THIS_YEAR_NEW_C_CNT_SUM}</b>
                        <c:if test="${THIS_YEAR_NEW_C_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
                        <c:if test="${THIS_YEAR_NEW_I_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_I_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${THIS_YEAR_NEW_I_CNT_SUM}</b>
                        <c:if test="${THIS_YEAR_NEW_I_CNT_SUM > 0}" >
                            </a>
                        </c:if>
                    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${THIS_YEAR_NEW_SFC_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'THIS_YEAR_NEW_SFC_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${THIS_YEAR_NEW_SFC_CNT_SUM}</b>
                        <c:if test="${THIS_YEAR_NEW_SFC_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
				        <c:if test="${CS_NEW_TOTAL_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_NEW_TOTAL_SUM','','');" class="notSelect">
                        </c:if><b>${CS_NEW_TOTAL_SUM}</b>
                        <c:if test="${CS_NEW_TOTAL_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_NEW_S_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_NEW_S_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_NEW_S_CNT_SUM}</b>
                        <c:if test="${CS_NEW_S_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_NEW_F_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_NEW_F_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_NEW_F_CNT_SUM}</b>
                        <c:if test="${CS_NEW_F_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_NEW_C_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_NEW_C_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_NEW_C_CNT_SUM}</b>
                        <c:if test="${CS_NEW_C_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
				        <c:if test="${CS_NEW_SFC_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_NEW_SFC_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_NEW_SFC_CNT_SUM}</b>
                        <c:if test="${CS_NEW_SFC_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_ANDON_RED_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_ANDON_RED_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_ANDON_RED_CNT_SUM}</b>
                        <c:if test="${CS_ANDON_RED_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_ANDON_YELLOW_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_ANDON_YELLOW_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_ANDON_YELLOW_CNT_SUM}</b>
                        <c:if test="${CS_ANDON_YELLOW_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_ANDON_GREEN_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_ANDON_GREEN_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_ANDON_GREEN_CNT_SUM}</b>
                        <c:if test="${CS_ANDON_GREEN_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdgrayMdeep4" style="border-bottom : 1px solid #CCCCCC;">
				        <c:if test="${CS_ANDON_RYG_CNT_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_ANDON_RYG_CNT_SUM','','');" class="notSelect">
                        </c:if><b>${CS_ANDON_RYG_CNT_SUM}</b>
                        <c:if test="${CS_ANDON_RYG_CNT_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_1_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_1_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_1_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_1_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_2_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_2_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_2_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_2_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_3_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_3_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_3_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_3_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_4_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_4_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_4_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_4_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_5_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_5_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_5_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_5_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_6_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_6_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_6_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_6_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_7_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_7_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_7_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_7_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_8_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_8_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_8_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_8_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
				    <td class="tdwhiteMdeep">
				        <c:if test="${CS_MAIN_CATEGORY_CNT_9_SUM > 0}" >
                            <a href="javascript:cs_paging_reset(3,'CS_MAIN_CATEGORY_CNT_9_SUM','','');" class="notSelect">
                        </c:if><b>${CS_MAIN_CATEGORY_CNT_9_SUM}</b>
                        <c:if test="${CS_MAIN_CATEGORY_CNT_9_SUM > 0}" >
                            </a>
                        </c:if>
				    </td>
                </tr>
	            </tbody>
            </table>
        </td>
    </tr>

    
</table>
   
<table id="cs_meeting_tb" width="1218px" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed; display:none;"  border='1'>
    <tr>
        <td valign="top">    
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <!-- <td width="20"><img src="/plm/portal/images/icon_4.png"></td> -->
                    <td class="font_04" align="left" width="10%" style='color: #000000; font-weight: bold; font-family: Malgun Gothic;'><a href="javascript:cs_paging_reset(2);" class="notSelect" id="presentTd">현황</a></td>
                    <td class="font_04" align="center" width="80%" id="centerName" name="centerName">${sales.centerName} - ${sales.pmdept}</td>
                    <td class="font_04" align="right" width="10%" id="hasCheckedNewEvent" style='color: #1165BC; font-weight: bold; font-family: NanumBold;'>${hasCheckedNewEvent}</td>
                    
<!--                     <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                   <td class="btn_blue"
                                                       background="/plm/portal/images/btn_bg1.gif"><a
                                                       href="javascript:window.close();"
                                                       class="btn_blue">닫기</a></td>
                                                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>

                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td> -->
                    
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class="space5"></td>
                  </tr>
              </table> 
<%--               <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td style='color: #000000; font-weight: bold; font-family: Malgun Gothic;'><a href="javascript:cs_paging_reset(2);" class="notSelect">현황판</a></td>
            
                      <td id="hasCheckedNewEvent" class="space5" align='right' style='color: #1165BC; font-weight: bold; font-family: NanumBold;'>${hasCheckedNewEvent}</td>
                  </tr>
              </table> --%>
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td  class="tab_btm2"></td>
                  </tr>
              </table>
              <form name="salesCsviewForm" method="post" enctype="multipart/form-data">
              <input type="hidden" id="page" name="page" value="1"/>
              <input type="hidden" id="oid" name="oid" value="ext.ket.sales.entity.KETSalesProject:993231320"/>
              
              
              <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;"> <!--테이블 속성에 자동줄바꿈 -->
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
	                <c:set var="signalRowSpan" value="4"></c:set>
<%-- 	                <c:if test="${empty issueLinkColor}">
	                   <c:set var="signalRowSpan" value="4"></c:set>
	                </c:if> --%>
	                
	                <c:if test="${sales.mainSignal=='green'}" >
	                <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="${signalRowSpan }" colspan="1" style="background-color:#b0d148; text-align:center">
	                </c:if>
                    <c:if test="${sales.mainSignal=='red'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="${signalRowSpan }" colspan="1" style="background-color:#e42a3d; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='yellow'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="${signalRowSpan }" colspan="1" style="background-color:#F8D200; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='gray'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="${signalRowSpan }" colspan="1" style="background-color:#e4e4e4; text-align:center">
                    </c:if>
                    <c:if test="${sales.mainSignal=='none'}" >
                    <td id="mainSignal" name="mainSignal" class="tdgrayMdeep" rowspan="${signalRowSpan }" colspan="1" style="background-color:none; text-align:center">
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
<%-- 	                <c:if test="${not empty issueLinkColor}">
	                <td class="tdgrayMdeep" style="cursor:pointer;background-color:${issueLinkColor};" onclick="javascript:openIssueList()"><b>요구사항</b></td>
	                </c:if> --%>
	                <td id="lastBuyerName" name="lastBuyerName" class="tdwhiteMdeep">${sales.lastBuyerName}</td>
	                <td id="applyArea" name="applyArea" class=tdwhiteMdeep>${sales.applyArea}</td>
	                <td id="competitorName" name="competitorName" class="tdwhiteMdeep">${sales.competitorName}</td>
	                <td id="mainExpectSalesTotal" name="mainExpectSalesTotal" class="tdwhiteMdeep"><fmt:formatNumber>${sales.mainExpectSalesTotal}</fmt:formatNumber></td>
	                <td id="mainExpectSalesYear" name="mainExpectSalesYear" class="tdwhiteMdeep"><fmt:formatNumber>${sales.mainExpectSalesYear}</fmt:formatNumber></td>
	                <td id="salesStateName" name="salesStateName" class="tdwhiteMdeep">${sales.salesStateName}</td>
	              </tr>
<!--                   <tr>
	                <td class="tdgrayMdeep">영업목표</td>
	                <td colspan="7" class="tdwhiteL">
	                <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
	                </td>
	              </tr> -->
	              </tbody>
	          </table>    
              
             <!-- <table id="viewTableGoal" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;">
              
               <tr>
                    <td class="tdgrayMdeep" width="72px"><table border="0" cellspacing="0" cellpadding="0" width="100%" ><tr><td>영업목표</td></tr></table></td>
                    <td colspan="7" class="tdwhiteL">
                    <div style="width:100%; height:180px; overflow:auto;">
                    <table><tr><td>
                    <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    </td></tr></table></div>
                    </td>
                  </tr>
              
              </table> -->   
            <div style="width:1218px; height:475px; overflow-x:hidden; overflow-y:auto;">
            <table id="viewTableGoal" border="0" cellspacing="0" cellpadding="0" width="100%" style="word-break: break-all;">
                <tr>
                    <td class="tdgrayMdeep" width="72px"><b>영업목표</b></td>
                    <td colspan="7" class="tdwhiteLdeep">
                    <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;" class="outline"></div>
                    </td>
                </tr>
            </table>
            <table id="viewTable2" border="0" cellspacing="0" cellpadding="0" width="100%" style="display: none"> <!--style="word-break: break-all;"--><!--테이블 속성에 자동줄바꿈 적용 -->
                <colgroup>
                <col width="6.05%">
                <c:forEach begin="1" end="15" varStatus="status">
                <col width="6.25%">
                </c:forEach>
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
                        <td class="tdwhiteMdeep2" name='planDate' id='planDate'><font color='#000000'>${taskList.planDate}</font><c:if test="${taskList.gubun=='BASIC'}" ><div style="position:relative;"><img style='position:absolute; top:-47px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div></c:if></td>
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
                        <td class="tdwhiteMdeep2" name='resultDate' id='resultDate'><font color='#000000'>${taskList.resultDate}</font></td>
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
                        <td class="tdwhiteMdeep2" ><font color='#000000'>${taskList.collaboTeam}</font></td>
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
                        <td class="tdwhiteMdeep2"><font color='#000000'>${taskList.subject}</font></td>
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
            
            <div id='cnt_exceed' style="width:1218px; height:150px; overflow-x:auto; overflow-y:hidden; display: none"><!-- step이 15개 초과시 가로스크롤을 만들어준다 1218 * 720 본사 1층 중회의실 해상도에 맞게 최적화함. -->
              <table id="viewTable2-1" border="0" cellspacing="0" cellpadding="0" width="100%"> <!--style="word-break: break-all;"--><!--테이블 속성에 자동줄바꿈 적용 -->
                  <tbody>
                  <tr>
                    <td class="tdgrayMdeep2"><span style="padding-left:24px"><b>Step</b><span style="padding-right:22px"></td>                    
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <td class="tdwhiteMdeep2" style="background-color:${taskList.hex};"><span style="padding-left:34px">${status.index+1}</span><span style="padding-right:31px"></td>
                    </c:forEach>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>계획일</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <td class="tdwhiteMdeep2" name='planDate' id='planDate'><font color="#000000">${taskList.planDate}</font><c:if test="${taskList.gubun=='BASIC'}" ><div style="position:relative;"><img style='position:absolute; top:-42px; left:50px' alt='현재날짜' id='today' src='/plm/extcore/image/arrow_mark.png'/></div></c:if></td>
                    </c:forEach>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>완료일</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <td class="tdwhiteMdeep2" name='resultDate' id='resultDate'><font color="#000000">${taskList.resultDate}</font></td>
                    </c:forEach>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>추진팀</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <td class="tdwhiteMdeep2" ><font color='#000000'>${taskList.collaboTeam}</font></td>
                    </c:forEach>
                  </tr>
                  <tr>
                    <td class="tdgrayMdeep2"><b>Subject</b></td>
                    <c:forEach items="${sales.taksLineUpList}" var="taskList" varStatus="status">
                    <td class="tdwhiteMdeep2"><font color='#000000'>${taskList.subject}</font></td>
                    </c:forEach>
                  </tr>
                  </tbody>
              </table>
              </div>
              
              <table id="viewTable3" border="0" cellspacing="0" cellpadding="0" width="1218px" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 적용 -->
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
              
              <table id="viewTable4" border="0" cellspacing="0" cellpadding="0" width="1218px" style="word-break: break-all;"><!--테이블 속성에 자동줄바꿈 적용 -->
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
    </div>
    </form>
    </td>
    </tr>
    </table>
    <div class="b-space30"></div>
 </div>
 </body>

 <div id="pagingDiv" align="center" style="display : none"></div>
 </c:if>