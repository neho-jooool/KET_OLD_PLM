<html>
<head>
<link rel="SHORTCUT ICON" href="/plm/portal/icon/e3ps_icon.ico">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>e3ps PLM</title>
<LINK rel="stylesheet" type="text/css" href="css/e3ps.css">

<Script language=JavaScript>
<!--

	function focus()
	{
		document.login.id.focus();
	}

  function enter(){
  if(IsNullData(login.id.value)) {
		alert("아이디를 입력하세요");
		return;
	}
	if(IsNullData(login.pw.value)) {
		alert("비밀번호를 입력하세요");
		return;
	}

//	createId();
    login.submit();
  }
  function IsNullData(str)
{
	if(str.length == 0)
		return true;
	for(var i=0;i<str.length;i++)
		if(str.charCodeAt(i) != 32)return false;
	return true;
}
function onKeyPress() { 
	var keycode; 
	if (window.event) keycode = window.event.keyCode;
	else if ( e ) keycode = e.which;
	else return true;
	if (keycode == 13) {    //??????? ????;?? 
		enter();
	return false
	} 
	return true
}
document.onkeypress = onKeyPress; 

var cookieName = "eCarID=";
function readId() 
{
    id = document.cookie
    if(id.length>0 && id.indexOf( cookieName.substring(1, cookieName.length))>0) 
    {
        idLength = id.length;
        idEnd = id.indexOf(";");
        if(idEnd > 1) idLength = idEnd;
        id = id.substring( id.indexOf( cookieName )+ cookieName.length, idLength)

		if(id == "; eCarID=")
		{
			document.forms[0].id.value = "";
			document.forms[0].id.focus();
			document.forms[0].checkId.checked = false;
		}
		else
		{
			document.forms[0].id.value = id;
			document.forms[0].pw.focus();
			document.forms[0].checkId.checked = true;
		}
    } 
}
function createId()
{
     if(document.forms[0].checkId.checked == true)
     { 
         var date = new Date();
         document.cookie = cookieName+document.forms[0].id.value+";expires=Sun Jan 1 00:00:00 GMT " + (date.getFullYear() +1);
      }
}
 

//-->
</Script>
</head>

<body onLoad="javascript:focus();readId()">
<form name="login" action="/plm/logincheck.jsp" method="post">

<table width=100% height=100%>
<tr>
<td>

<%
	String imageLocale = "";
	if(!(request.getLocale().toString()).startsWith("ko"))
		imageLocale = "_" + (request.getLocale().toString()).substring(0,2);
%>

<table border="0" cellpadding="0" cellspacing="0" width="700" align="center">
    <tr>
        <td width="1230">
		<!--????????//-->
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
                <tr>
                    <!--td width="607"-->
					<td>
<!--                        <p><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://active.macromedia.com/flash4/cabs/swflash.cab#version=4,0,0,0" width="492" height="268" vspace="0" hspace="0">
                        <param name="movie" value="/images/img_login/login.swf">
                        <param name="play" value="true">
                        <param name="loop" value="true">
                        <param name="quality" value="high">
                        <embed src="images/ds_login.swf" play="true" loop="true" quality="high" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" width="492" height="268"></embed>
                        </object></p>-->
						<p><img src="/images/img_login/login_left<%=imageLocale%>.gif" width="700" height="268" border="0"></p>
                    </td>
                    <!--td width="607">
                        <p><img src="/images/img_login/login_right.gif" width="208" height="268" border="0"></p>
                    </td-->
                </tr>
            </table>
			<!--????????//-->
        </td>
    </tr>
    <tr>
        <td width="1230" background="/images/img_login/login_log.gif" height="155" valign="bottom">
            <table border="0" cellpadding="0" cellspacing="0" width="700" height="120">
                <tr>
                    <td width="700" valign=bottom align='right'>
					<!--????//-->
					<!--table 2//-->
								<table align="center" border="0" cellpadding="2" cellspacing="0" width="550" height="71">
									<tr>
										<td width="" valign=top>
											<!--table 3(????? ???? ?????????)//-->
											<table border=0 cellpadding=0 cellspacing=0 align=right>
												<tr>
													<td> ID     &nbsp;</td>
													<td><input type="text" name="id" size="12"></td>
												</tr>
												<tr>
													<td> PASSWD &nbsp;</td>
													<td><input type="password" name="pw" size="12"></td>
												</tr>
												<!--
												<tr>
													<td colspan=2 align=right>
													<input type=checkbox name=checkId ><img src="/images/img_login/id_save.gif">
													</td>
												</tr>
												-->
											</table>
											<!--table 3 end//-->
										</td>
										<td width="50" align=center>
										<p><a href="JavaScript:enter()"><img src="images/img_login/ds_login_button.gif" width="45" height="45" border="0"></a></p><br>
										</td>
									</tr>
								</table>
								<!--table 2 end//-->
					<!--????//-->
					</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="1230" background="/images/img_login/login_back.gif">
            <p align="right"><font color="#C1B7A3">Windchill PDMLink 9.1 F000&amp;E3PS eSolution eCar Template 9.1 F000 &nbsp;&nbsp;&nbsp;</font></p>
        </td>
    </tr>
    <tr>
        <td width="1230" background="/images/img_login/login_bottom.gif">
            <p><img src="/images/img_login/login_bottom.gif" width="700" height="10" border="0"></p>
        </td>
    </tr>
</table>


<br>

<!--??????? ?????//-->
<table width=700 height=70 cellpadding=0 cellspacing=0 border=0 align=center>
	<tr>
		<td width=133><img src="/images/img_login/boxline01.gif"></td>
		<td background="/images/img_login/boxline02.gif" align=right style="vertical-align:bottom">&nbsp;&nbsp;&nbsp
		<a  href="http://www.microsoft.com/downloads/details.aspx?displaylang=ko&FamilyID=1E1550CB-5E5D-48F5-B02B-20B602228DE6" target=_new><img src="/images/img_login/ie6_logo.gif" border=0></A>
		<br>
		</td>
		<td width=10><img src="/images/img_login/boxline03.gif"></td>
	</tr>
</table>
<table width=700 cellpadding=0 cellspacing=0 border=0 align=center bgcolor=ffffff>
	<tr>
	<td align=right><img src="/images/img_login/this_product.gif" border=0></td>
		</tr>
	<!--tr>
		<td align=right><img src="/images/img_login/copyright.gif"></td>
	</tr-->
</table>



<!--????///-->


</td>
</tr>
</table>


</form>
</body>
</html>
