<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@ page import="javax.naming.Context,javax.naming.directory.InitialDirContext" %>
<%@ page import="javax.naming.directory.DirContext,javax.naming.directory.Attributes" %>
<%@ page import="javax.naming.NamingException"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
  java.net.URL codebase;
  public void init() throws ServletException
  {
    try {
      codebase = wt.util.WTProperties.getServerCodebase();
    }
    catch(java.io.IOException ex)
    {
      ex.printStackTrace();
    }
  }
%>
<%
	boolean isadmin = CommonUtil.isAdmin();
	if(isadmin) session.setAttribute("isadmin",new Boolean(true));
%>
<%--

   String auth = request.getHeader("authorization");

   auth = auth.substring( "Basic ".length() );
   String authDecoded = com.infoengine.util.Base64.decode(auth);
   String usr = authDecoded.substring( 0, authDecoded.indexOf(":") );
   String pwd = authDecoded.substring( authDecoded.indexOf(":")+1 );

  String newopen = request.getParameter("u");
  if("39".equals(newopen))session.setAttribute("isadmin",new Boolean(false));
  Boolean login = (Boolean)session.getAttribute("isadmin");
  if(login==null)login = new Boolean(false);
  String name = request.getParameter("name");
  String pass = request.getParameter("pass");
  if(pass!=null&& name!=null && name.equals(usr) && pass.equals(pwd)){
    session.setAttribute("isadmin",new Boolean(true));
    login = new Boolean(true);
  }
  else{
    session.setAttribute("isadmin",new Boolean(false));
    login = new Boolean(false);
  }
  boolean isadmin = login.booleanValue();
--%>
<!-- saved from url=(0058)http://adp-pdm.adp.com/plm/portal/admin/index.html -->
<HTML><HEAD><TITLE><%=messageService.getString("e3ps.message.ket_message", "00951") %><%--관리자모드--%></TITLE>
<LINK rel="stylesheet" type="text/css" href="../css/e3ps.css">

<SCRIPT language=JavaScript>
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function focus(){
  document.forms[0].name.focus();
}
function enter(){
  if(IsNullData(document.forms[0].name.value)) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00254") %><%--id를 입력해주세요--%>");
    return;
  }
  if(IsNullData(document.forms[0].pass.value)) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00352") %><%--password를 입력하세요--%>");
    return;
  }
    document.forms[0].submit();
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
  if (keycode == 13) {    //엔터키를 눌렀을때
    enter();
  return false
  }
  return true
}
document.onkeypress = onKeyPress;
//-->
</SCRIPT>

<META content="MSHTML 5.00.3502.5390" name=GENERATOR></HEAD>
<BODY leftMargin=0 topMargin=0 marginheight="0" marginwidth="0" onload="document.forms[0].name.focus()">
<TABLE border=0 cellPadding=0 cellSpacing=0 height="100%" width="100%">
  <TBODY>
    <TR>
      <TD height="100%" width="100%">
        <TABLE border=0 cellPadding=0 cellSpacing=0 height="100%" width="100%">
          <TBODY>
            <TR>
              <TD bgColor=#cccccc height="45" vAlign=top width=120>
                <P><IMG border=0 height="45" src="images/logo.gif" useMap=#Map width=130><MAP name=Map><AREA coords="16,1,112,38" href="/plm/portal/admin/index.jsp" shape="rect"></MAP></P>
              </TD>
              <TD background=images/top_back.gif height="45" width="100%" valign="top">
                <table border=0 cellpadding=0 cellspacing=0 width="780" height=45  background=images/top_back.gif>
                  <tr align=center>
                    <td width=1 bgcolor=white></td>
                    <!--menu1//-->
                    <td id=white  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "02998") %><%--폴더 &amp; Cabinet만들기--%></a></td>
                    <td width=1 bgcolor=white></td>
                    <!--menu2//-->
                    <td id=white  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "01554") %><%--부서만들기--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu3//-->
                    <td  id=white  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00797") %><%--계정관리--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu4//--
                    <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr4' style="mouse:default,backgraoundColor=''">
                    <a  href="4.sjp"  onclick="javascript:trcolor('tr4')" id=white  onFocus="this.blur()"> 4</a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu5//--
                    <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr5' style="mouse:default,backgraoundColor=''">
                    <a  href="5.sjp"  onclick="javascript:trcolor('tr5')" id=white  onFocus="this.blur()">5</a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu6//--
                    <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr6' style="mouse:default,backgraoundColor=''">
                    <a  href="http://empas.com"  onclick="javascript:trcolor('tr6')" id=white  onFocus="this.blur()"> 6</a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu7//-->
                    <td  id=white  onFocus="this.blur()"> <%=messageService.getString("e3ps.message.ket_message", "01435") %><%--문서타입 관리--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu8//-->
                    <td  id=white  onFocus="this.blur()"> <%=messageService.getString("e3ps.message.ket_message", "02915") %><%--코드체계관리--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu9//-->
                    <td  id=white  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "00989") %><%--권한관리--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu10//-->
                    <td  id=white  onFocus="this.blur()"><%=messageService.getString("e3ps.message.ket_message", "03045") %><%--프로세스관리자--%></a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu11//-->
                    <td  id=white  onFocus="this.blur()">Visualization</a>
                    </td>
                    <td width=1 bgcolor=white></td>
                    <!--menu12//-->
                    <td  id=white  onFocus="this.blur()">Windchill</a>
                    </td>
                    <td width=1 bgcolor=white></td>
                  </tr>
                  <tr><td colspan=12 height=20></td></tr>
                </table>
              </TD>
            </TR>
            <TR>
              <TD background="images/sub_back0.gif" bgColor=#cccccc vAlign=top width=120>
                <P><IMG border=0 height="186" src="images/sub0.gif" width=130></P>
              </TD>
              <TD vAlign=top width="100%">
                <P align=center>&nbsp;</P>
                <P align=center>&nbsp;</P>
                <%if(isadmin){%>
                <script>location.href="/plm/portal/admin/admin_text.jsp";</script>
                <%}else{%>
                <FORM method=post>
                  <TABLE align=center border=0 cellPadding=0 cellSpacing=0 height=245 width=631>
                    <TBODY>
                      <TR>
                        <TD width=149>
                          <P>
                            <IMG border=0 height=245 src="images/login_1.gif" width=149>
                          </P>
                        </TD>
                        <TD background='images/login_2.gif' width=285 style="background-repeat:no-repeat;">
                          <P align=right>&nbsp;</P>
                          <P align=right>&nbsp;</P>
                          <DIV align=right>
                            <TABLE border=0 cellPadding=0 cellSpacing=0 width=200>
                              <TBODY>
                                <TR>
                                  <TD height=30 width=275>
                                    <P align=center>
                                      <INPUT type='text' name='name' value='' style='width:70%'>
                                    </P>
                                  </TD>
                                </TR>
                                <TR>
                                  <TD height=30 width=275>
                                    <P align=center>
                                      <INPUT type='password' name='pass' value='' style='width:70%'>
                                    </P>
                                  </TD>
                                </TR>
                              </TBODY>
                            </TABLE>
                          </DIV>
                        </TD>
                        <TD background='images/login_3.gif' width=197 style="background-repeat:no-repeat;">
                          <P>&nbsp;</P>
                          <P>&nbsp;</P>
                          <P>
                            <a href="JavaScript:enter()">
                              <IMG border=0 height=56 src="images/login_icon.gif" width=56>
                            </a>
                          </P>
                        </TD>
                      </TR>
                    </TBODY>
                  </TABLE>
                </FORM>
                <%}%>
              </TD>
            </TR>
          </TBODY>
        </TABLE>
      </TD>
    </TR>
    <TR>
      <TD bgColor=lightgrey height=21 width="100%">
        <P align=center><font color=666666>COPYRIGHT 2010 BY KET MECHATRONICS. ALL RIGHT RESERVED.</font></P>
      </TD>
    </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
