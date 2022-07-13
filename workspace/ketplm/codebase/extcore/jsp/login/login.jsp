<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Cookie[] cookies = request.getCookies();

for(int i=0; i<cookies.length; i++){
    out.println("<b>쿠키이름 :</b> " + cookies[i].getName() + "   ");
    out.println("<b>쿠키값 :</b> " + cookies[i].getValue() + "<br><br>");
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>PLM System Login Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.6000.16981" name=GENERATOR></HEAD>

<script language="JavaScript">
	function devlogin() {
	
		if ( DevGateForm.j_username.value == "" ) {	
			alert("Input ID!");
			return;
		}

		if ( DevGateForm.j_password.value == "" ) {	
			alert("Input Password!");
			return;	
		}

		DevGateForm.action	= "j_security_check";
		DevGateForm.submit();
	}
	
	function checkEnterKey() {
		var eKey = window.event.keyCode;
		if (eKey == "13") {			// Enter Key 처리
			devlogin();
			return false;
		} 
		return true;
	}

	function setfocus() {
		DevGateForm.j_username.focus();
	}
</script>

<BODY onLoad="javascript:setfocus();"><BR><BR><BR><BR><BR><BR><BR><BR><BR>
<form name="DevGateForm" method="POST" action="j_security_check">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 width=400 align=center border=0>
        <TBODY>
        <TR>
          <TD><IMG height=99 src="/plm/extcore/image/login_01.gif" width=400></TD></TR>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 width=400 border=0>
              <TBODY>
              <TR>
                <TD><IMG height=53 src="/plm/extcore/image/login_02.gif" width=104></TD>
                <TD width=135 background=/plm/extcore/image/login_03.gif height=53>
                  <TABLE cellSpacing=0 cellPadding=0 width=100 align=center  border=0>
                    <TBODY>
						<TR>
							<TD height=23><INPUT class=input1 maxLength=20 size=13 name=j_username tabindex=1></TD>
						</TR>
						<TR>
							<TD height=23><INPUT class=input1 type=password maxLength=10 size=13  name=j_password tabindex=2 onKeyPress="return checkEnterKey();"></TD>
						</TR>
					</TBODY>
				  </TABLE>
				  <INPUT class=input1 type=hidden  name=mode value="form">
				</TD>
                <TD><A href="javascript:devlogin();" target=_parent><IMG height=53 src="/plm/extcore/image/login_04.gif" width=52 border=0 tabindex=3></A></TD>
                <TD><IMG height=53 src="/plm/extcore/image/login_05.gif" width=54 tabindex=4></TD>
                <TD><IMG height=53 src="/plm/extcore/image/login_06.gif" width=55></TD>
			  </TR>
			  </TBODY>
			</TABLE>
		   </TD>
		 </TR>
        <TR>
          <TD><IMG height=49 src="/plm/extcore/image/login_07.gif"  width=400></TD>
		</TR>
	  </TBODY>
	</TABLE>
   </TD>
   </TR>
  </TBODY>
</TABLE>
</form>
</BODY>
</HTML>