<%@page contentType="text/html;charset=UTF-8" errorPage="ErrorPage.jsp" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02291") %><%--이미지 삽입--%></title>
<style>
body {
  background: threedface;
  color: windowtext;
  margin: 10px;
  border-style: none;
  font:9pt 돋움;
  text-align:center;
}
body, button, div, input, select, td, legend { font:9pt 돋움; }
input,select {color:highlight}
button {width:80px;}
fieldset { margin-bottom:5px;text-align:left;padding:5px }

</style>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
<!--
function insertImage()
{
    var str="";
    var f=document.tform;
    var src = f.userFile.value;
    if(!src.match(/\.(gif|jpg|png)$/i)) { alert("<%=messageService.getString("e3ps.message.ket_message", "03387") %><%--이미지파일을 첨부 해주세요!--%>"); return; }
    f.submit();
}
function viewImage(src)
{
    var str = "";
    var f=document.tform;
    if(!src.match(/\.(gif|jpg|png)$/i))  { alert("<%=messageService.getString("e3ps.message.ket_message", "03388") %><%--gif,jpg,png 파일만 첨부 가능합니다!--%>"); return; }
    if(window.showModalDialog)
    {
        var h_str=""; var height=0,width=0;
        var img = new Image(); img.src =src;
        f.width.value = img.width;
        f.height.value = img.height;
        if(img.height>150) h_str="150";
        if(img.height>150){
      h_str = "height='"+h_str+"'";
          str = "<img src='"+src+"' "+h_str+" />";
    }else{
          str = "<img src='"+src+"' height='"+img.height+"' />";
    }
    }
    else
        str = "<%=messageService.getString("e3ps.message.ket_message", "03306") %><%--미리보기 지원은 MS IE 에서만 가능!--%>";

  document.getElementById("td_img").innerHTML = str;
}
//-->
</script>
</head>

<body>
<form name="tform" method="post" enctype="multipart/form-data" action="/plm/portal/editor/upload_image_ok.jsp">

  <fieldset>
  <legend><%=messageService.getString("e3ps.message.ket_message", "01449") %><%--미리보기--%></legend>
  <table border=0 cellspacing=0 cellpadding=0 width="100%">
    <tr><td align="center" style="height:150px" id="td_img"></td></tr></table>
  </fieldset>

  <fieldset>
  <legend><%=messageService.getString("e3ps.message.ket_message", "02292") %><%--이미지 선택--%></legend>
  <input type="file" name="userFile" style="width:100%" onchange="viewImage(this.value)">
  </fieldset>

  <fieldset>
  <legend><%=messageService.getString("e3ps.message.ket_message", "02165") %><%--옵션--%></legend>
  <table border=0 cellspacing=6 cellpadding=0>
<!--    <tr>
    <td>정렬</td>
    <td>
    <select name="align">
    <option value="" selected>없음
    <option value="baseline">기준선</option>
    <option value="top">위쪽</option>
    <option value="middle">가운데</option>
    <option value="bottom">아래쪽</option>
    <option value="texttop">문자열 위쪽</option>
    <option value="absmiddle">선택 영역의 가운데</option>
    <option value="absbottom">선택 영역의 아래쪽</option>
    <option value="left">왼쪽</option>
    <option value="right">오른쪽</option>
    </select>
    </td>
    </tr>-->
    <tr>
    <td><%=messageService.getString("e3ps.message.ket_message", "00575") %><%--가로*세로--%></td>
    <td>
    <input type="text" name="width" value="0" size="3" maxlength=3> *
    <input type="text" name="height" value="0" size="3" maxlength=3>px
    </td>
    </tr>
    <tr>
    <td><%=messageService.getString("e3ps.message.ket_message", "01306") %><%--두께--%></td>
    <td>
    <input type="text" name="border" value="0" size="2" maxlength=3>px
    </td>
    </tr>
    </table>
  </fieldset>

    <button onclick="insertImage()"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></button>

</form>
</body>
</html>
