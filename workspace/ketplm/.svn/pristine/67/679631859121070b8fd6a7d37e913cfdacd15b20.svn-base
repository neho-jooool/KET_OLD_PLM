<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript">
<!--
    var oid = '${oid}';
    var redirectURL = '${redirectURL}';
    var todo = ${isTODO};
    
    if(todo){
    	window.resizeTo(1024,768);
    	location.href = redirectURL;
    	//getOpenWindow2(redirectURL, 'TodoTaskPopup', 1024, 768);
    }
    else{
        if(oid.indexOf("WorkItem") > 0){
        	//window.resizeTo(1024,900);
        	//location.href = '/plm/jsp/wfm/ReviewTask.jsp?oid=' + oid;
            //getOpenWindow2('/plm/jsp/wfm/ReviewTask.jsp?oid=' + oid, 'TaskViewPopup', 1024, 900);
            
        	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open('/plm/jsp/wfm/ReviewTask.jsp?oid=' + oid, '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
            
        }else if(oid.indexOf("KETDashBoardMailSetting") > 0){
        	window.resizeTo(1024,900);
            location.href = '/plm/ext/dashboard/teamWorkTimeReort.do?type=mail';
        }else if(oid == "KETMyWorkDelay"){

        	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
        	leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
        	window.open("/plm/ext/wfm/workflow/listWorkItem.do", '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
            
            //window.resizeTo(1024,900);
            //location.href = '/plm/ext/wfm/workflow/listWorkItem.do';
        }else if(oid == "KETMYTODO"){

            var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open("/plm/ext/wfm/workspace/listMyTodo.do", '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
            
            //window.resizeTo(1024,900);
            //location.href = '/plm/ext/wfm/workflow/listWorkItem.do';
        }else if(oid == "KETMYTASK"){

            var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open("/plm/ext/wfm/workspace/listMyTask.do", '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
            
            //window.resizeTo(1024,900);
            //location.href = '/plm/ext/wfm/workflow/listWorkItem.do';
        }else if(oid.indexOf("CostReport") > 0){
        	var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open(redirectURL, '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else if(oid.indexOf("KETInvestTask") > 0){
/*             var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
             */
            window.open(redirectURL, "", "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=1,resizable=1,width=900,height=720");
            
            //window.open(redirectURL, '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else if(oid.indexOf("KETProjectDocument") > 0){
            var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open(redirectURL, '', (opts + rest));
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
        }else if(oid == "KETRetireWorkDelay"){

            var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=1,";
            leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
            toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

            var rest = "width=" + (screen.availWidth * 0.9) + ",height=" + (screen.availHeight * 0.9) + ',left=' + leftpos + ',top=' + toppos;
            
            window.open("/plm/ext/wfm/workspace/viewListTotalWork.do", '', (opts + rest));
            //location.href = "/plm/jsp/common/loading2.jsp?url=" + "/plm/jsp/project/ProjectViewFrm.jsp&key=isPop&value=Y&key=popup&value=popup&key=pjtNo&value="+pjtNo;
            //새창을 띄우고 자기자신을 닫을 때 알람없이 닫기
            window.opener='nothing';
            window.open('','_parent','');
            window.close();
            
            //window.resizeTo(1024,900);
            //location.href = '/plm/ext/wfm/workflow/listWorkItem.do';
        }else{
            openView(oid,null,null,true);
        }
    }
    /* try {
        opener.close();
    } catch (e) {
    }
    window.close(); */
    //else if (oid.indexOf("WorkItem") > 0) {
    	//getOpenWindow2('/plm/jsp/wfm/ReviewTask.jsp?oid=' + oid, 'TaskViewPopup', 1024, 900);
    //}
    //else {
    //	openView(oid);
    //}
//-->
</script>
</head>
</html>