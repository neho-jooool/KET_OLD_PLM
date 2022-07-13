<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import="wt.fc.*,wt.lifecycle.*,
        wt.org.*,
        wt.query.*,
        wt.session.*,
        wt.util.*"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*,
        e3ps.common.code.*,
        e3ps.common.jdf.config.*,
        e3ps.common.code.GenNumberCode,
        e3ps.common.web.HtmlTagUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.web.ParamUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Locale"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="control" class="e3ps.common.web.PageControl" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html>
<%
  //Kogger.debug("###################################");

  HashMap map = new HashMap();

  String command = request.getParameter("command");
  String isSelect = request.getParameter("isSelect");
  String mode = request.getParameter("mode");
  String initType = request.getParameter("radio");




  if(initType==null) initType="3";
  String pjtType = "3";
  String tmpTitle = "";

  if(initType.equals("1")) {  // 개발검토 프로젝트
    tmpTitle = "개발검토";
      pjtType = "1";
  } else if(initType.equals("2")) {//제품 프로젝트
    tmpTitle = "제품";
      pjtType = "2";
  }else if(initType.equals("3")) {//견적 프로젝트
    tmpTitle = "금형";
      pjtType = "3";
  }

  //Kogger.debug("pjtType----->>>>" + pjtType);


  //검색 속성
  String pjtNo = request.getParameter("pjtNo");                 //pjtNo(프로젝트 NO)
  String pjtName = request.getParameter("pjtName");               //pjtName(프로젝트 명)
  String pjtState = request.getParameter("pjtState");             //pjtState(상태)

  String dieNo = request.getParameter("dieNo");
  String partNo = request.getParameter("partNo");
  String partName = request.getParameter("partName");
/*
  Kogger.debug("List----pjtNo---" + pjtNo);
  Kogger.debug("List----pjtName---" + pjtName);
  Kogger.debug("List----pjtState---" + pjtState);
  Kogger.debug("List----dieNo---" + dieNo);
  Kogger.debug("List----partNo---" + partNo);
  Kogger.debug("List----partName---" + partName);
  */





  if(command == null)
    command = "search";

  if(isSelect == null)
    isSelect = "";

  if(mode == null)
    mode = "";

  if (command.length() > 0) {
    map.put("command", command);
  }

  if (isSelect.length() > 0) {
    map.put("isSelect", isSelect);
  }


  if (mode.length() > 0) {
    map.put("mode", mode);
  }


  ////////////////////////////////////////////////////////////////
  // 검색
  ////////////////////////////////////////////////////////////////

  if (pjtNo != null && pjtNo.length() > 0) {
    map.put("pjtNo", pjtNo);
  }
  if (pjtName != null && pjtName.length() > 0) {
    map.put("pjtName", pjtName);
  }
  if (pjtState != null && pjtState.length() > 0 ) {
    map.put("pjtState", pjtState);
  }
  if (pjtType != null && pjtType.length() > 0) {
    map.put("pjtType", pjtType);
  }
  if (dieNo != null && dieNo.length() > 0) {
    map.put("dieNo", dieNo);
  }
  if (partNo != null && partNo.length() > 0) {
    map.put("partNo", partNo);
  }
  if (partName != null && partName.length() > 0) {
    map.put("partName", partName);
  }
  int psize = 5;
  int cpage = 1;
  int total = 0;
  int pageCount = 10;

  //page
  String sessionidstring = request.getParameter("sessionid");
  if (sessionidstring == null){
    sessionidstring = "0";
  }
  long sessionid = Long.parseLong(sessionidstring);
  String pagestring = request.getParameter("tpage");
  if (pagestring != null && pagestring.length() > 0) {
    cpage = Integer.parseInt(pagestring);
  }

  PagingQueryResult result = null;
  if (sessionid <= 0) {
    result = SearchPagingProjectHelper.openPagingSession(map, 0, psize);
  } else {
    result = PagingSessionHelper.fetchPagingSession((cpage - 1)  * psize, psize, sessionid);
  }

  if (result != null) {
    total = result.getTotalSize();
    sessionid = result.getSessionId();
  }


%>

<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03069") %><%--프로젝트 목록--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<script language=JavaScript>
<!--
    function gotoPage(p) {
      document.forms[0].command.value='search';
      document.forms[0].sessionid.value='<%=sessionid%>';
      document.forms[0].tpage.value=p;
      document.forms[0].submit();
    }

    function search() {
      onProgressBar();
      document.forms[0].command.value='search';
      document.forms[0].sessionid.value ="0";
      document.forms[0].tpage.value = "";

      document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/trySchdule/TryListProject.jsp?modelcode="+document.forms[0].modelcode.value+"&command=<%=command%>";
      document.forms[0].submit();
    }

    function viewProject(oid){
      parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid;
    }

    function checkAll() {
      form = document.forms[0];
      if(form.poid) {
        var chkLen = form.poid.length;
        if(chkLen) {
          for(var i = 0; i < chkLen; i++) {
            form.poid[i].checked = form.chkAll.checked;
          }
        }else{
          form.poid.checked = form.chkAll.checked;
        }
      }
    }

    function checkedCheck() {
      form = document.forms[0];
      if(form.poid) {
        var chkLen = form.poid.length;
        if(chkLen) {
          for(var i = 0; i < chkLen; i++) {
            if(form.poid[i].checked == true) {
              return true;
            }
          }
        }else{
          if(form.poid.checked == true) {
            return true;
          }
        }
      }
      return false;
    }

    function selectProject() {

      var sresult = new Array();
      if(document.forms[0].poid==null) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
        return;
      }
      var count = 0;
      if(document.forms[0].poid.length==null) {
        if(document.forms[0].poid.checked) {
          sresult[count++] = document.forms[0].poid.value;
        } else {
          alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
          return;
        }
      }

      for(var i=0; i< document.forms[0].poid.length; i++) {
        if(document.forms[0].poid[i].checked) {
          sresult[count++] = document.forms[0].poid[i].value;
        }
      }
      if(sresult.length==0) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01812") %><%--선택된 프로젝트가 없습니다--%>");
        return;
      }

      <%if("one".equals(mode)){%>
        opener.document.forms[0].projoid.value=sresult[0];
        opener.document.forms[0].submit();
        self.close();
      <%}else if("oneSelect".equals(mode)){%>
      resultArr = getCheckedData();
      opener.addProject(resultArr);
      self.close();
      <%}else{%>
      var addparts = "";
      for(var i=0; i< sresult.length; i++){
        addparts += "<input type=hidden name=projoid value='"+sresult[i]+"'>";
      }
      opener.document.all.addMPart.innerHTML = addparts;
      <%} %>
      <%if(!"oneSelect".equals(mode)){%>
      opener.document.forms[0].submit();
      <%} %>
    }

    function getCheckedData() {
      form = document.forms[0];
      if(form.poid == null) {
        //alert("검색 결과가 없습니다.");
        return;
      }

      var arr = new Array();
      var idx = 0;
      var chkLen = form.poid.length;
      if(chkLen) {
        for(var i = 0; i < chkLen; i++) {
          if(form.poid[i].checked == true) {
            rArr = new Array();
            rArr[0] = form.poid[i].value;
            rArr[1] = form.poid[i].pjtNo;
            rArr[2] = form.poid[i].pjtName;
            rArr[3] = form.poid[i].pjtProduct;
            rArr[4] = form.poid[i].pjtFabName;
            arr[idx++] = rArr;
          }
        }
      }else{
        if(form.poid.checked == true) {
          rArr = new Array();
          rArr[0] = form.poid.value;
          rArr[1] = form.poid.pjtNo;
          rArr[2] = form.poid.pjtName;
          rArr[3] = form.poid.pjtProduct;
          rArr[4] = form.poid.pjtFabName;
          arr[idx++] = rArr;
        }
      }
      return arr;
    }

  function addProcess(type, depth) {
    var form = document.ListProgram;

    var tmpType = "";
    if(type==("divisioncode")) {
      tmpType = "DIVISIONCODE";
    }

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }
    acceptProcess(returnValue, type);
  }
  function acceptProcess(arrObj, type){
    var tmpType = "";
    var tmpType1 = "";

    if(type==("divisioncode")) {
      tmpType = "divisioncodetable";
      tmpType1 = "divisioncodeoid";
    }

    var subArr;
    var form = document.forms[0];

      for(var i = 0; i < arrObj.length; i++) {
        subArr = arrObj[i];
        if(type==("divisioncode")) {
          form.divisioncode.value = subArr[1];
          form.divisioncode.name =subArr[2];
        } else if(type==("levelcode")) {
          form.levelcode.value = subArr[1];
          form.levelcode.name =subArr[2];
        } else if(type==("productcode")) {
          form.productcode.value =subArr[1];
          form.productcode.name =subArr[2];
        } else if(type==("customercode")) {
          form.customercode.value =subArr[1];
          form.customercode.name =subArr[2];
        } else if(type==("devcompanycode")) {
          form.devcompanycode.value =subArr[1];
          form.devcompanycode.name =subArr[2];
        } else if(type==("makecompanycode")) {
          form.makecompanycode.value =subArr[1];
          form.makecompanycode.name =subArr[2];
        } else if(type==("modelcode")) {
          form.modelcode.value =subArr[1];
          form.modelcode.name =subArr[2];
        }else if(type==("oemtypecode")) {
          form.oemtypecode.value =subArr[1];
          form.oemtypecode.name =subArr[2];
        }

      }
  }


    function onKeyPress() {
      var keycode;
      if (window.event) {
        keycode = window.event.keyCode;
      }else{
        return true;
      }

      if (keycode == 13) {    //엔터키를 눌렀을때
        search();
        return false
      }
      return true
    }
    document.onkeypress = onKeyPress;

//-->
</script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
}
-->
</style>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html"%>
<form>
<!-- Hidden Value -->

<input type="hidden" name="initType" value="<%=initType%>">
<input type="hidden" name="pjtType" value="<%=pjtType%>">
<input type='hidden' name="command"  value="<%=command%>">
<input type='hidden' name='sessionid' value="<%=sessionidstring %>">
<input type='hidden' name='tpage'>

<input type='hidden' name='mode' value="<%=mode%>">
<!-- //Hidden Value -->

        <!-- NEW Start -->
        <%
        if(initType.equals("3")){
            %>
              <table width="780" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                  <td>&nbsp;</td>
                  <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                      <td><a href="#"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:dosubmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:addSelectCode();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a></td>
                              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                          </table></td>
                    </table></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td class="space5"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td  class="tab_btm2"></td>
                </tr>
              </table>
              <table border="0" cellspacing="0" cellpadding="0" width="780">
                <tr>
                  <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></td>
                  <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                  <td width="120" class="tdblueM">Die No</td>
                          <td width="190" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02550") %><%--제품 프로젝트 명--%></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                  <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03118") %><%--프로젝트상태--%></td>
                </tr>
                 <%
                int listCount = result.getTotalSize();
            int count = result.getTotalSize();
            int sortcount = 0;
            int countAll = count;
            String checked = "checked";

            if(cpage > 1)
              sortcount = (15 * cpage) - 15;

            if(cpage > 1 &&  (countAll-sortcount) > 0)
              countAll = count - sortcount;
            boolean checkOut = false;

            if(listCount>0){
              while (result.hasMoreElements()) {
                Object[] obj = (Object[]) result.nextElement();
                E3PSProjectData data = new E3PSProjectData((E3PSProject) obj[0]);
                E3PSProject project = data.e3psProject;
                MoldProject moldProject = (MoldProject)CommonUtil.getObject(data.e3psPjtOID);
                MoldItemInfo moldInfo = moldProject.getMoldInfo();

                checkOut = project.isCheckOut();
                %>
                <tr>
                  <td class="tdwhiteM">
              <input type="checkbox" value="<%=data.e3psPjtOID%>" name="oid" codename='<%=moldInfo.getDieNo()%>' onClick="oneClick(this)" <%//=checked%>>
              </td>
                  <td width="60" class="tdwhiteM"><%=listCount %></td>
                  <td width="120" class="tdwhiteL"><!-- a href="javascript:parent.viewContentPage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=data.e3psPjtOID%>');"--><%=moldInfo.getDieNo()==null?"":moldInfo.getDieNo()%>&nbsp;<!--/a--></td>
                  <td width="220" class="tdwhiteL"><!--a href="javascript:parent.viewContentPage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=data.e3psPjtOID%>', '/plm/jsp/project/ProjectView.jsp?oid=<%=data.e3psPjtOID%>');"--><%=data.pjtName==null?"":data.pjtName%>&nbsp;<!-- /a--></td>
                  <td width="100" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanStartDate, "d")%>&nbsp;</td>
                  <td width="100" class="tdwhiteM"><%=DateUtil.getDateString(data.pjtPlanEndDate, "d") %>&nbsp;</td>
                  <td width="180" class="tdwhiteR"><%=data.getStateStr(data.stateKorea)%></td>
                </tr>
               <%
              }

            }else{
               %>
               <tr>
                  <td class='tdwhiteM0' align='center' colspan='6'> <%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%> </td>
                </tr>
                <%
            }
                %>
              </table>



            <%
            }
            %>
            <%
      int ksize = total / psize;
      int x = total % psize;
      if (x > 0)
        ksize++;
      int temp = cpage / pageCount;
      if ((cpage % pageCount) > 0)
        temp++;
      int start = (temp - 1) * pageCount + 1;

      int end = start + pageCount - 1;
      if (end > ksize) {
        end = ksize;
      }
    %>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <tr>
                <td class="small"><span class="small">(<%=messageService.getString("e3ps.message.ket_message", "02499") %><%--전체페이지--%> :<%=ksize%>)</span></td>
        <td>
        <table border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="40" align="center">
            <%
            if (start > 1) {
            %><a href="#" onclick="JavaScript:gotoPage(1)" class="small"><img src="/plm/portal/btn_arrow4.gif"></a>
            <%
            }
            %>
            </td>
            <td width="1" bgcolor="#dddddd"></td>
            <%
            if (start > 1) {
            %>
            <td width="60" class="quick" align="center"><a
              href="#" onclick="JavaScript:gotoPage(<%=start-1%>)" class="smallblue"><img src="/plm/portal/btn_arrow3.gif"></a></td>
            <td width="1" bgcolor="#dddddd"></td>
            <%
              }
              for (int i = start; i <= end; i++) {
            %>
            <td style="padding:2 8 0 7;cursor:hand"
              onMouseOver="this.style.background='#ECECEC'"
              OnMouseOut="this.style.background=''" class="nav_on">
            <%
            if (i == cpage) {
            %><b><%=i%>
            <%
            } else {
            %><a href="#" onclick="JavaScript:gotoPage(<%=i%>)"><%=i%></a>
            <%
            }
            %>
            </td>
            <%
              }
              if (end < ksize) {
            %>
            <td width="1" bgcolor="#dddddd"></td>
            <td width="60" align="center"><a
              href="#" onclick="JavaScript:gotoPage(<%=end+1%>)" class="smallblue"><img src="/plm/portal/btn_arrow1.gif"></a></td>
            <%
            }
            %>
            <td width="1" bgcolor="#dddddd"></td>
            <td width="45" align="center">
            <%
            if (end < ksize) {
            %><a href="#" onclick="JavaScript:gotoPage(<%=ksize%>)"
              class="small"><img src="/plm/portal/btn_arrow2.gif"></a>
            <%
            }
            %>
            </td>
          </tr>
        </table>
        </td>
                <td class="small" align="right">(<%=messageService.getString("e3ps.message.ket_message", "02496") %><%--전체개수--%>:<%=total%>)</td>
      </tr>
    </table>

</form>
</body>
</html>

<%!
  private boolean isProjectUser(WTUser wtuser, int memberType) {
    try {
      QuerySpec qs = new QuerySpec();
      Class peopleProjectLinkClass = ProjectMemberLink.class;
      int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

      qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);
      qs.appendAnd();
      long oidValue = CommonUtil.getOIDLongValue(wtuser);
      qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
      QueryResult queryresult = PersistenceHelper.manager.find(qs);
      if (queryresult != null && queryresult.size() > 0) {
        return true;
      }
    } catch (QueryException e) {
	Kogger.error(e);
    } catch (WTException e) {
	Kogger.error(e);
    }
    return false;
  }
%>
