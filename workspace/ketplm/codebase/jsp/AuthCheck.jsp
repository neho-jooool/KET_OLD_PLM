<%@ page contentType="text/html;charset=UTF8" session="true" %>

<%@ page import="wt.auth.*"%>
<%@ page import="wt.httpgw.*"%>
<%@ page import="wt.method.RemoteMethodServer"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="sun.misc.*"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>

<%!
	char pad = '=';
	char[] nibble2code=
    {
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
        'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
        'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/'
    };

	public String DrmEncode(String sParameter, String sKey)  throws Exception
    {
        sParameter = encode32(sParameter, null);
        StringBuffer outBuffer = new StringBuffer();
        int nKeyLength = sKey.length();
        int nParamLen = sParameter.length();
        for( int i = 0; i < nParamLen; i++ )
        {
            char p = sParameter.charAt(i);
            char k = sKey.charAt(i%nKeyLength);
            char r = (char)(p^k);

            outBuffer.append(r);
        }

        return encode(outBuffer.toString(), null);
    }

    public String encode(String s,String charEncoding) throws Exception
	{
        int nibble=0;
        byte[] bytes;

        if( charEncoding == null )
            bytes= s.getBytes();
        else
            bytes= s.getBytes(charEncoding);

        char[] encode = new char[bytes.length*2+4];

        int e=0;
        int n=0;

        for( int i=0; i < bytes.length; i++ )
        {
            byte b=bytes[i];
            switch(n++)
            {
              case 0:
                  nibble = b>>2;
                  nibble &= 0x3F;
                  //Kogger.debug("b="+b+" nibble = " + nibble);
                  encode[e++]=nibble2code[nibble];
                  nibble = (b&0x3)<<4;
                  break;

              case 1:
                  nibble += b>>4;
                  nibble &= 0x3F;
                  //Kogger.debug("b="+b+" nibble = " + nibble);
                  encode[e++]=nibble2code[nibble];
                  nibble = (b&0xf)<<2;
                  break;

              case 2:
              default:
                  n=0;
                  nibble += b>>6;
                  nibble &= 0x3F;
                  //Kogger.debug("b="+b+" nibble = " + nibble);
                  encode[e++]=nibble2code[nibble];
                  nibble = (b&0x3f);
                  encode[e++]=nibble2code[nibble];
                  break;
            }
        }

        switch(n++)
        {
          case 0:
              encode[e++]=pad;
              encode[e++]=pad;
              encode[e++]=pad;
              encode[e++]=pad;
              break;

          case 1:
              encode[e++]=nibble2code[nibble];
              encode[e++]=pad;
              encode[e++]=pad;
              encode[e++]=pad;
              break;

          case 2:
          default:
              encode[e++]=nibble2code[nibble];
              encode[e++]=pad;
        }

        return new String(encode,0,e);
    }

	public String encode32(String s, String charEncoding) throws Exception
	{
        int nibble=0;
		int idx=0;
        byte[] bytes;

		if (charEncoding==null)
			bytes= s.getBytes();
		else
			bytes= s.getBytes(charEncoding);

		char[] encode = new char[bytes.length*2+4];

		for (int i=0;i<bytes.length;i++)
		{
			byte b=bytes[i];

			nibble = b>>4 & 0x0F;
			encode[idx++] = nibble2code[nibble];
			nibble = b & 0x0F;
			encode[idx++] = nibble2code[nibble];
		}

		return new String(encode,0,idx);
    }
%>

<%
    String sso_id = request.getParameter("userId");
	String sso_pw = request.getParameter("passwd");
	String loginType = request.getParameter("mode");

    if( sso_id == null )
		sso_id = "";

    if( sso_pw == null )
		sso_pw = "";

    if( loginType == null )
		loginType = "";

Kogger.debug( "===> sso_id : " + sso_id );
Kogger.debug( "===> sso_pw : " + sso_pw );
Kogger.debug( "===> loginType : " + loginType );

	session.setAttribute("auth.uid", sso_id);
	session.setAttribute("auth.upw", sso_pw);
%>

<script language="JavaScript">
    function setCookie(name, value)
	{
		var today = new Date();
		today.setTime( today.getTime() );
		var expires = 7 * 1000 * 60 * 60 * 24;
		var expires_date = new Date( today.getTime() + (expires) );

        document.cookie = name + "=" + escape(value)
			                    + ";path=/plm"
			                    + ";expires=" + expires_date.toGMTString()
			                    + ";domain=ket.com";
		                        +";secure";
    }

    function getCookie(name)
	{
        var flag = document.cookie.indexOf(name + "=");

        if ( flag != -1 )
		{
            flag += name.length + 1;
            end = document.cookie.indexOf(";", flag);

            if ( end == -1 ) end = document.cookie.length;

            return unescape(document.cookie.substring(flag, end));
        }
    }

    function postLoginCallBackFunc()
	{
<%
        String sAuth = "";
        String userPassword = "";

		if( loginType.equals("cp") )
		{
			sAuth = "wcadmin";
			userPassword = "1";
		}
		else
		{
			sAuth = (String)session.getAttribute("auth.uid");
			userPassword = (String)session.getAttribute("auth.upw");
		}

Kogger.debug( "sAuth : " + sAuth );
Kogger.debug( "userPassword : " + userPassword );
Kogger.debug( "==============> postLoginCallBackFunc() Method Called!!!" );

		String authStr = com.infoengine.util.Base64.encode(sAuth + ":" + userPassword);

		// 암호화를 하기위해 SessionID는 앞의 30자리는 자른다.
		String sKey = java.net.URLEncoder.encode(session.getId()).substring(30);
%>
		setCookie("auth.uid" ,"<%=DrmEncode(sAuth, sKey)%>");
		setCookie("auth.string" ,"<%=DrmEncode(authStr, sKey)%>");
		str = getCookie("JSESSIONID");
		setCookie("OLD.SID", str);

<%
		if( loginType.equals("cp") )
		{
%>
			document.LoginForm.action = "/plm/jsp/groupware/company/plmPasswordChange.jsp?userId=<%=sso_id%>&passwd=<%=sso_pw%>";
			document.LoginForm.submit();
<%
		}
		else if( loginType.equals("ootb") || loginType.equals("wgm") )
		{
%>
			document.LoginForm.action = "/plm/servlet/WindchillGW";
			document.LoginForm.submit();
<%
		}
		else
		{
%>
			document.LoginForm.action = "/plm/portal/index2.html";
			document.LoginForm.submit();
<%
		}
%>

	}
</script>

<%
String onLoadCallScript = "postLoginCallBackFunc();";
%>

<HTML>
<BODY bgcolor="#F8F8F8" text="#000000" onLoad="javascript:postLoginCallBackFunc();">
<FORM name="LoginForm" method="post">
    <input type="hidden" name="forwardUrl" value="">
</FORM>
</BODY>
</HTML>
