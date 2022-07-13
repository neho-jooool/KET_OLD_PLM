<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="wt.method.*,wt.fc.*,wt.query.*"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="wt.util.*,wt.org.*"%>
<%@ page import="com.infoengine.util.*"%>
<%@ page import="e3ps.common.util.*,e3ps.groupware.company.*,e3ps.common.content.*,e3ps.common.content.uploader.*"%>

<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<%@ taglib uri="http://www.ptc.com/infoengine/taglib/core" prefix="ie"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%= request %>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
  FileUploader uploader = FileUploader.newFileUploader(null, request, response);

  String oid = uploader.getFormParameter("oid");
  String name = uploader.getFormParameter("name");
  String email = uploader.getFormParameter("email");
//  String password = uploader.getFormParameter("password");
  String cellTel = uploader.getFormParameter("cellTel");
  String officeTel = uploader.getFormParameter("officeTel");
  String homeTel = uploader.getFormParameter("homeTel");
  String address = uploader.getFormParameter("address");
  String isDisable = uploader.getFormParameter("isDisable");
  String sortNum = uploader.getFormParameter("sortNum");
  String isChief = uploader.getFormParameter("isChief");

//    String tempField = "cn=" + uploader.getFormParameter("name") + ",mail=" + uploader.getFormParameter("email");

    // People 저장
    PeopleData data = new PeopleData(oid);
//  String tempId = "uid=" + data.id + "," + CompanyState.ldapDirectoryInfo;

  data.people.setHomeTel(homeTel);
  data.people.setCellTel(cellTel);
  data.people.setOfficeTel(officeTel);
  data.people.setAddress(address);
//  if(CommonUtil.isAdmin()) data.people.setSortNum(Integer.parseInt(sortNum));

  if ( isDisable != null ) {
    if ( isDisable.equals("0") ) data.people.setIsDisable(false);
    else if ( isDisable.equals("1") ) data.people.setIsDisable(true);
  }

  if ( isChief != null ) {
    data.people.setChief(data.people.getDepartment().getName());
  } else {
    data.people.setChief("");
  }
  data.people = (People)PersistenceHelper.manager.modify(data.people);

  Vector oldImageFile = ContentUtil.getSecondaryContents(data.people);
  Vector newImageFile = uploader.getFiles("imageFile");
  Vector newImageFileDesc = new Vector();
  newImageFileDesc.add("");
  if ( newImageFile.size() > 0 ) {
    if ( oldImageFile.size() > 0 ) { // 수정할 상태
      for ( int i = 0 ; i < oldImageFile.size() ; i++ ) {
	    Kogger.debug("peopleUpdate", null, null, "oldImageFile.get(i)"+oldImageFile.get(i));
        e3ps.common.content.ContentInfo tempInfo = (e3ps.common.content.ContentInfo)oldImageFile.get(i);
        E3PSContentHelper.service.delete(data.people,(wt.content.ContentItem)(e3ps.common.util.CommonUtil.getObject(tempInfo.getContentOid())));
      }
      E3PSContentHelper.service.attach(data.people,newImageFile);
    } else {  // 등록할 상태
      E3PSContentHelper.service.attach(data.people,newImageFile);
    }
  }


  // WTUser 저장
  data.wtuser.setFullName(name);
  data.wtuser.setEMail(email);
  OrganizationServicesHelper.manager.updatePrincipal(data.wtuser);

%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02513") %><%--정보 수정 중--%></title>
</head>
<body onLoad="javascript:goPage('<%=CommonUtil.getOIDString(data.people)%>')">
<script language="JavaScript">
<!--
  function goPage(v) {
    document.location.href="/plm/jsp/groupware/company/peopleView.jsp?oid=" + v;
  }
//-->
</script>
</body>
</html>
