function doDelete(oid,msg)
{
//	if(oid.indexOf("E3PSProject")>0 || oid.indexOf("E3PSTask")>0 )
//	{
//		alert("Can't delete!!");
//		return;
//	}
//	else
//	{
		var url = deleteURL(oid);
		if(url.length == 0)
		{
			alert(unescape("%uC0AD%uC81C%uD560%20%uC218%20%uC5C6%uB294%20%uACB0%uC7AC%uC785%uB2C8%uB2E4 "));
			return;
		}
		
		
//		if(url!=null)
//		{
			if(!confirm(unescape(msg))) return;
		disabledAllBtn();
		showProcessing();
			document.forms[0].action = url;
			document.forms[0].method='post';
			document.forms[0].submit();
//		}
//		else
//		{
//			alert("Failed to delete!!"); 
//		 	return;
//		}
//	}

}

function deleteURL(oid)
{
	var url = "";
	if( oid.indexOf("EPMDocument") > 0)
	{
		return "";
//		url = "/plm/jsp/part/ViewEPM.jsp?oid="+oid;
	}
	else if( oid.indexOf("Drawing") > 0)
	{
		url = "/plm/servlet/e3ps.drawing.servlet.DrawingDocServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("Document") > 0)
	{
		url = "/plm/servlet/e3ps.doc.servlet.DocumentServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("EChangeRequest") > 0 )
	{
		url = "/plm/servlet/e3ps.change.servlet.EcrServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("EChangeOrder") > 0 )
	{
		url = "/plm/servlet/e3ps.change.servlet.EcoServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("EChangeNotify") > 0 )
	{
		url = "/plm/servlet/e3ps.change.servlet.EcnServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("WorkProcessForm") > 0 ){
		url = "/plm/servlet/e3ps.groupware.workprocess.servlet.ManagerFormServlet?cmd=delete&oid="+oid+"&from=participant";
	}
	else if( oid.indexOf("DistributeWorkProcess") > 0 ){
		url = "/plm/servlet/e3ps.distribute.servlet.DistributeWorkProcessServlet?cmd=delete&oid="+oid;
	}
	return url;
}
