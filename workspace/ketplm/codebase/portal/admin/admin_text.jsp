<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import="e3ps.common.util.WCUtil,
                e3ps.common.util.CommonUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>PLM System[Admin]</TITLE>
<LINK rel="stylesheet" type="text/css" href="../css/e3ps.css">
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>
</HEAD>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%
    Boolean login = (Boolean)session.getAttribute("isadmin");
    if(login==null || !login.booleanValue()){%>
    document.location.href="/plm/portal/admin/index.jsp";
<%}%>
    var selectObj;

    function trcolor(url, tr_id, tName) {
        //
        iframe.location = url;
        document.myform.myname.value = tName;
        if (tr_id != null) {
            var obj = document.getElementById(tr_id);
            //    setPosition(tName);

            if (selectObj != null) {
                selectObj.style.backgroundColor = ''
            }
            //  else if (selectObj==null)
            //{
            //  tr1.style.backgroundColor='#E7651A';
            //}
            selectObj = obj;
            selectObj.style.backgroundColor = '#E7651A';

        }
    }

    function trcolor1(url, tr_id, tName, oemtype) {
        alert(oemtype);
        iframe.document.location = url;
        ;
        document.myform.myname.value = tName;
        if (tr_id != null) {
            var obj = document.getElementById(tr_id);

            if (selectObj != null) {
                selectObj.style.backgroundColor = ''
            }

            selectObj = obj;
            selectObj.style.backgroundColor = '#E7651A';
        }
    }

    function onOver(obj) {
        if (obj != selectObj) {
            obj.style.backgroundColor = '#CCCC00';
        }
        //obj.style.cursor='hand';
    }
    function onOut(obj) {
        if (obj != selectObj) {
            obj.style.backgroundColor = ''
        }
    }
    
    function doReloadNumberCode(){

        $.ajax({
            url : "/plm/ext/code/doReloadNumberCode.do",
            type : "POST",
            dataType : 'json',
            async : true,
            success : function(flag) {
                if(flag) alert("reload ok");
                else alert("reload error");
            }
        });
    }
//-->
</SCRIPT>
<BODY>
  <form name=myform>
    <!-- <table width=100% height=100% cellpadding=0 cellspacing=0 border=0 background=images/top_back.gif> -->
    <table width="100%" height=100% cellpadding=0 cellspacing=0 border=0 style="background-image:url('images/top_back.gif'); background-repeat:no-repeat;">
      <tr background=images/top_back.gif>
        <TD height="45" vAlign=top width=120>
          <P>
            <a href="index.jsp" target=_top><IMG border=0 height="45" src="images/logo.gif"></a>
          </P>
        </TD>
        <td>
          <table border=0 cellpadding=0 cellspacing=0 width="900" height=45 background=images/top_back.gif>
            <tr align=center>
              <td width=1 bgcolor=white></td>
              <!--menu1
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr1' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/servlet/e3ps.groupware.folder.FolderTreeServlet','tr1','폴더관리')" id=white  onFocus="this.blur()">폴더관리</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <%
                  if (CommonUtil.isAdmin()) {
              %>
              <!--menu2//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr2' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/groupware/company/adminCompanyfrm.jsp','tr2','부서관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "01551")%><%--부서관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <%
                  }
              %>
              <!--menu3
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr3' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/wtcore/jsp/com/ptc/core/ca/web/gw/gw.jsp?alias=com.ptc.windchill.enterprise.org.users:main&containerRef=<%=e3ps.common.util.WCUtil.getWTContainerRef()%>','tr3','계정관리')" id=white  onFocus="this.blur()">계정관리</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <!--menu4//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr4' style="mouse: default, backgraoundColor=''">
                <!--a  href="javascript:trcolor('/plm/jsp/doc/doctype/ManageDocMain.jsp','tr4','문서분류체계')" id=white  onFocus="this.blur()">문서분류체계</a--> <a
                href="javascript:trcolor('/plm/jsp/dms/DocuCateMain.jsp','tr4','문서분류체계')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "01427")%><%--문서분류체계--%></a>
              </td>
              <td width=1 bgcolor=white></td>
              <!--menu5//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr5' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/dms/StandardDocMain.jsp','tr5','표준양식관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "03010")%><%--표준양식 관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr6' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/jsp/part/ptype/ManagePartMain.jsp','tr6','부품분류체계')" id=white  onFocus="this.blur()">부품분류체계</a>
        </td>
        <td width=1 bgcolor=white></td-->
              <!--td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr7' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/jsp/project/projectType/ManageProjectMain.jsp','tr7','산출물인증타입')" id=white  onFocus="this.blur()">산출물인증타입</a>
        </td>
        <td width=1 bgcolor=white></td-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr8' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/project/projectType/ManageOEMMain.jsp?codetype=CUSTOMEREVENT&oemtype=CUSTOMEREVENT','tr8','고객이벤트')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "00851")%><%--고객이벤트--%></a></td>
              <td width=1 bgcolor=white></td>
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr9' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/project/projectType/ManageOEMMain.jsp?codetype=CUSTOMEREVENT&oemtype=CARTYPE','tr9','차종관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "02749")%><%--차종관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr10' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/jsp/project/projectType/ManageOEMMain.jsp?codetype=SUBCONTRACTOR&oemtype=PAYCONTRACTOR','tr10','납입처')" id=white  onFocus="this.blur()">납입처</a>
        </td>
        <td width=1 bgcolor=white></td-->
              <!--menu8//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr11' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/common/code/ManageCodeMain.jsp','tr11','코드체계관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "02915")%><%--코드체계관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--menu9
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr12' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/jsp/auth/MainE3PSAuth.jsp','tr12','권한관리')" id=white  onFocus="this.blur()">권한관리</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <!--menu10
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr13' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/servlet/WindchillAuthGW/wt.enterprise.URLProcessor/URLTemplateAction?Action=ProcessAdmin&u8=1&containerOid=<%//=wt.inf.container.WTContainerHelper.getClassicRef()%>','tr13','프로세스관리자')" id=white  onFocus="this.blur()">프로세스관리자</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <!--menu11
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr14' style="mouse:default,backgraoundColor=''">
        <a  href="javascript:trcolor('/plm/wtcore/jsp/wvs/edrmain.jsp','tr14','Visualization')" id=white  onFocus="this.blur()">Visualization</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <!--menu12
        <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr15' style="mouse:default,backgraoundColor=''">
        <a href="javascript:trcolor('/plm/wtcore/jsp/wt/portal/dcasearch.jsp?body=sitemap.jsp','tr15','Windchill')" id=white  onFocus="this.blur()">Windchill</a>
        </td>
        <td width=1 bgcolor=white></td>//-->
              <!--menu13//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr16' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/project/material/MaterialMain.jsp','tr16','원재료관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "02220")%><%--원재료관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--menu14//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr17' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/jsp/project/machine/MachineMain.jsp','tr17','Machine관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "00309")%><%--Machine 관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--menu15//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr18' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/ext/part/classify/viewClazListForm.do','tr18','부품분류체계')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "03456")%><%--부품분류체계--%></a></td>
              <td width=1 bgcolor=white></td>
              <!--menu16//-->
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr19' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/ext/part/naming/viewPartNamingListForm.do','tr19','부품명관리')" id=white onFocus="this.blur()"
              ><%=messageService.getString("e3ps.message.ket_message", "06144")%><%--부품명관리--%></a></td>
              <td width=1 bgcolor=white></td>
              <td bgcolor="" onMouseover="onOver(this)" onMouseout="onOut(this)" id='tr19' style="mouse: default, backgraoundColor=''"><a
                href="javascript:trcolor('/plm/ext/common/dashboard/viewMailReceiveListForm.do','tr20','E-MAIL수신설정')" id=white onFocus="this.blur()"
              >E-MAIL수신설정<%--부품명관리--%></a></td>
              <td width=1 bgcolor=white></td>
            </tr>
            <tr>
              <td colspan=25 height=20></td>
            </tr>
          </table>
        </td>
        <td>
          <table border=0 cellpadding=0 cellspacing=0 width=60>
            <tr>
              <td width=60 align=center>
                <P>
                  <a href="index.jsp" target=_top><img src="images/logout.gif" width="55" height="21" border=0></a>
                </P>
              </td>
            </tr>
            <tr>
              <td height=20></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr height=100%>
        <td bgcolor=white background="images/sub_back1.gif" valign=top>
          <table border=0 cellpadding=0 cellspacing=0>
            <tr>
              <td><img src="images/admin_sub.gif" width="130" height="65" border=0></td>
            </tr>
            <tr>
              <td align=center><input type="text" name="myname" value="코드체계관리" style="font-weight: bold; color: rgb(231, 101, 26); border-style: none; text-align: center;" size=12></td>
            </tr>
            <tr>
            <td align=center><input type="button" name="reloadNumberCode" value="RELOAD" onclick="doReloadNumberCode()"></td>
            </tr>
          </table>
        </td>
        <td colspan=2><iframe src="/plm/jsp/common/code/ManageCodeMain.jsp" name="iframe" width=100% height=100%></iframe></td>
      </tr>
    </table>
  </form>
</BODY>
</HTML>
