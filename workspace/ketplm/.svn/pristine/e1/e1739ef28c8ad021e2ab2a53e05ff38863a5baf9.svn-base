<%@ page contentType="text/html;charset=euc-kr"%>
<html>
<head>
<link rel="stylesheet" href="/Windchill/portal/css/e3ps.css">
<title>공지사항 상세화면</title>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="javascript">
function setCookie( name, value, expiredays )
{
     var todayDate = new Date();
     todayDate.setDate( todayDate.getDate() + expiredays );
     document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

function closeWin() {
    setCookie("costNotiPopup", "no" , 1);
    window.close();
}

function check()
{
	if (document.notifyview.SaleNotice5.checked ) {
		 setCookie("costNotiPopup", "no" , 1);
   		 window.close();
	}
}

</script>
</head>
<body bgcolor="white" text="black" link="blue" vlink="purple" alink="red" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name="notifyview" method="post">
<input type=hidden name=command>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01">공지사항 </td>
          </tr>
        </table></td>
        <td width="136" align="right"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
			<table width="770" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:parent.close();" class="btn_blue">닫기</a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
			<table border="0" cellspacing="0" cellpadding="0" width="770">
				<tr>
					<td class="space5"> </td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" width="770">
				<tr>
					<td class="tab_btm2"></td>
				</tr>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" width="770">
			<tr>
				<td width="130" class="tdblueL">제목</td>
				<td colspan="3" class="tdwhiteL0">원가프로그램 공지</td>
				</tr>
			<tr>
				<td class="tdblueL">내용</td>
				<td colspan="3" class="tdwhiteL0" style="height:200" valign=top>
				<br>
				공 지
				<br>
				<br>
<br>
서버 점검이 있을 예정입니다.
<br>
<br>
점검완료 후 프로그램에 접속하시기 바랍니다.
<br>
<br>
일 시 : 2013년 08월 23일 (금) 17시 ~ 2013년 08월 16일 (토)
<br>
<br>
* 점검시간은 변경될수있습니다 *
<br>
<br>
이용에 참고하시기 바랍니다.
				</td>
			</tr>
			<tr>
			<td colspan="4" height="38" align="center" valign="top" >
        			<input type="checkbox" name="SaleNotice5" value="checkbox" style="border:0" onClick="check();"><span style="font-size:9pt;">오늘 이창을 다시 열지 않음</span>
	 		</td>
			</tr>
		</table>
			</td>
			</tr>
		 </table>
</table>
</body>
</html>
