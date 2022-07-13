<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title></title>
<%@include file="/jsp/common/multicombo.jsp"%>
<form name="salesListForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="createDateFrom" value="${createDateFrom}">
<input type="hidden" name="salesStateName" value="${salesStateName}">
<input type="hidden" name="mainCategoryName" value="${mainCategoryName}">
<input type="hidden" name="nationName" value="${nationName}">
<input type="hidden" name="failtypecode" value="${failtypecode}">
<input type="hidden" name="obtainCompany" value="${obtainCompany}">
<input type="hidden" name="salesStateOid" value="${salesStateOid}">
</form>
<script type="text/javascript">
<!--
    
    var viewtype = "${viewtype}";
	
    window.location=false;
	
	if(viewtype == "DIVIDEND"){
		window.resizeTo(1024, 700);
	}else{
		window.resizeTo(1024, 768);
	}
	
	if(viewtype == "DQM"){
        var oid = "${dqmOid}";
        var dqmStateCode = "${dqmStateCode}";
        
        if(oid == ''){
            alert("존재하지 않는 개발품질문제입니다.");
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else{
        	
        	if(dqmStateCode == 'RAISEAPPROVED' || dqmStateCode == 'ACTIONINWORK'){
                window.open("/plm/ext/dqm/dqmMainForm.do?type=actionToGrid&oid="+oid, 'dqmMainFormPopup', 'width=1100,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            } else if(dqmStateCode == 'ACTIONAPPROVED' || dqmStateCode == 'END'){
                window.open("/plm/ext/dqm/dqmMainForm.do?type=closeToGrid&oid="+oid, 'dqmMainFormPopup', 'width=1100,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            } else{
            	window.open("/plm/ext/dqm/dqmMainForm.do?type=view&oid="+oid, 'dqmMainFormPopup', 'width=1100,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            }
        	
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }
    }
	
	
	if(viewtype == "ECO"){
		
		var EcoOid = "${EcoOid}";
		var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + EcoOid; 
		
		if(EcoOid.indexOf('KETMoldChangeOrder') != -1){
			url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + EcoOid;
		}
		
		//location.href = url;   
		
		window.open(url, '', 'width=1000,height=800,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
	}
	
	if(viewtype == "ECR"){
        var EcrOid = "${EcrOid}";
        location.href = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + EcrOid;
    }
	
	if(viewtype == "PROJECT"){
		var pjtNo = "${pjtNo}";
		window.open("/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo, '', 'width=1024,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
		//location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
		//새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
		window.opener='nothing';
		window.open('','_parent','');
		window.close();
	}
	
	if(viewtype == "PROJECT_ATFT"){
        var pjtNo = "${pjtNo}";
        window.open("/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=targetType&value=ATFT&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo, '', 'width=1024,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
    }
	
	if(viewtype == "ISSUE"){
        var pjtNo = "${pjtNo}";
        window.open("/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=targetType&value=ISSUE&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo, '', 'width=1024,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
    }
	
	if(viewtype == "PART"){
        var partNo = "${partNo}";
        location.href = "/plm/ext/part/base/viewPartByNo.do?partNumber="+ partNo;
    }
	
	if(viewtype == "DIVIDEND"){
        var emp_no = "${emp_no}";
        location.href = "/plm/ext/orther/dividend/viewDividendForm.do?emp_no="+ emp_no;
    }
	
	if(viewtype == "ECO_LIST"){
        var divisionFlag = "${divisionFlag}";
        if(divisionFlag == '2'){
        	divisionFlag = 'C';
        }
        if(divisionFlag == '4'){
        	divisionFlag = 'E';
        }
        
        window.open("/plm/ext/dashboard/ecoProjectListPopup2.do?divisionFlag="+divisionFlag, '', 'width=1024,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
    }
	
	if(viewtype == "INCOMPLETE_ISSUE"){//미완료 고객 이슈
        
        window.open("/plm/jsp/project/ProjectIssueTotalList.jsp?viewtype="+viewtype, '', 'width=1024,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
    }
	
    if(viewtype == "MODEL_PLAN"){
    	var modelPlanOid = "${modelPlanOid}";
    	var tabIndex = "${tabIndex}";
    	
    	if(modelPlanOid == ''){
    		alert("존재하지 않는 차종입니다.");
    		window.opener='nothing';
            window.open('','_parent','');
            window.close();
    	}else{
    		var addParam = "&isAEGIS=Y";
    		if(tabIndex == '1'){
    			addParam = "";
    		}
    		window.open("/plm/jsp/project/ViewProgram.jsp?oid="+modelPlanOid+addParam, '', 'width=850,height=770,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
    	}
    }
    
    if(viewtype == "SALES_LIST"){
    	
    	var paramCheck = "${paramCheck}";
    	
    	if(paramCheck != 'OK'){
    		alert("파라미터 정보가 올바르지 않습니다.\n관리자에게 문의바랍니다.");
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
    	}else{
    		$('[name=salesListForm]').attr('target', 'salesListFormPopup');
            $('[name=salesListForm]').attr('action', '/plm/ext/sales/project/salesProjectList.do');
            
            //getOpenWindow2('/plm/ext/sales/project/salesTaskCreateForm.do?planCheck='+planCheck+'&stepName=Step '+idx+'&idx='+idx,'salesTaskCreateFormPopup',1300,500);
            
            window.open("", "salesListFormPopup", "width=1280,height=768,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0");
            $('[name=salesListForm]').submit();
            
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
    	}
        
    }
    
    if(viewtype == "AEGIS_ISSUE"){
        var oid = "${oid}";
        
        if(oid == ''){
            alert("존재하지 않는 이슈입니다.");
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else{
            window.open("/plm/jsp/project/projectIssueView.jsp?oid="+oid, '', 'width=800,height=750,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }
    }
    
    if(viewtype == "SALES_PROJECT"){
        var oid = "${oid}";
        
        if(oid == ''){
            alert("존재하지 않는 프로젝트입니다.");
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else{
            window.open("/plm/ext/sales/project/viewSalesProjectForm.do?oid="+oid, '', 'width=1000,height=850,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }
    }
    
    
    if(viewtype == "DOCUMENT"){
        
        window.open("/plm/ext/dms/listProjectDocument.do", '', 'width=1280,height=770,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
        
    }
    
    if(viewtype == "EPM"){
        
        window.open("/plm/servlet/e3ps/EDMServlet?command=openSearch", '', 'width=1280,height=770,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
        
    }
    
    if(viewtype == "REPLACEPART"){
    	
    	if(window.opener){
    		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            var leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            var toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
                
            window.open("/plm/ext/replacePart/replacePartList.do", '', (opts + rest));
    	}else{
    		alert("EP를 통해 접속하여야합니다.");
    	}
        
    	//var url = "http://plmapdev.ket.com/plm/ext/replacePart/replacePartList.do";
    	
        window.opener='nothing';
        window.open('','_parent','');
        window.close();

        /* var leftpos = (screen.availWidth - screen.availWidth) / 2;
        var toppos = (screen.availHeight - screen.availHeight) / 2;
        var width =  screen.availWidth;
        var height = screen.availHeight;

        window.showModalDialog(url,window,"help=no; resizable=1; status=no; scroll=yes; center:yes; dialogWidth:"+width+"; dialogHeight:"+height+"; dialogLeft:"+leftpos+"; "+"dialogtop:"+toppos+";");
        window.opener='nothing';
        window.open('','_parent','');
        window.close(); */
        
    }
    
    
    if(viewtype == "DECRYPT"){
        window.open("/plm/ext/orther/file/decryptOldDrmFile.do", '', 'width=850,height=680,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
        //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
        //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
        window.opener='nothing';
        window.open('','_parent','');
        window.close();
    }
    
    
    if(viewtype == "AEGIS_INVEST"){
    	
        var oid = "${oid}";
        
        if(oid == ''){
            alert("존재하지 않는 고객투자비입니다.");
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else{
            
        	window.open("/plm/ext/invest/viewInvestPopup.do?type=view&oid="+oid, '', 'width=1280,height=800,toolbar=0,menubar=0,location=0,status=0,scrollbars=1,resizable=1,left=0,top=0');
            
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }
    }
	
	//getOpenWindow2(redirectURL, 'TodoTaskPopup', 1024, 768);
//-->
</script>
</head>
</html>