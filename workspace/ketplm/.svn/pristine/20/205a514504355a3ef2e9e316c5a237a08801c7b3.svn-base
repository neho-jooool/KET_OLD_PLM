<%@page import="e3ps.project.MoldProject"%>
<%@page import="wt.org.*"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="java.io.File"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.Sheet"%>
<%@page import="jxl.Cell"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.groupware.company.People"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="java.util.Collections"%>
<%@page import="com.ptc.core.ocmp.framework.RoleComparator"%>
<%@page import="java.util.Locale"%>
<%@page import="wt.project.Role"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title>Insert title here</title>
</head>
<body>
<%!
public static WTUser getUser(String name)throws Exception{
    QuerySpec spec = new QuerySpec();
    Class peopleClass = People.class;
    int peopleClassPos = spec.addClassList(peopleClass, true);
    spec.appendWhere(new SearchCondition(People.class, People.NAME, "=", name), new int[]{0});
    QueryResult rs = PersistenceHelper.manager.find(spec);
    WTUser user = null;
    if(rs.hasMoreElements()){
        Object o[] = (Object[])rs.nextElement();
        People people = (People)o[0];
        user = people.getUser();
    }
    return user;
}
%>

<%
FormUploader uploader = FormUploader.newFormUploader(request);
Hashtable param = uploader.getFormParameters();
String oid = (String)param.get("oid");
E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);

Vector v = uploader.getFiles();

WBFile fileName = null;
if(v.get(0) != null){
    fileName = (WBFile)v.get(0);
}
File file  = fileName.getFile();
file = E3PSDRMHelper.upload(file, file.getName());

//Kogger.debug("file = " + file);

Workbook wb = Workbook.getWorkbook(file);

//Kogger.debug("wb = " + wb);

Sheet[] sheets = wb.getSheets();
Hashtable hash = new Hashtable();

String teamName = "";
if (project instanceof MoldProject) {
    teamName = ProjectHelper.getProjectTeam(3);
} else {
    teamName = ProjectHelper.getProjectTeam(project.getPjtType());
}

TeamTemplate tempTeam = null;

tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);

Vector vecTeamStd = new Vector();

if(tempTeam != null) {
    vecTeamStd = tempTeam.getRoles();
}

//Collections.sort(vecTeamStd, new RoleComparator());
Enumeration enm = null;
WTPrincipal prin = null;
WTPrincipalReference ref = null;
Hashtable roleHash = new Hashtable();
Hashtable roleHash_user = new Hashtable();
for (int i = 0; i < vecTeamStd.size(); i++) {

    Role role = (Role) vecTeamStd.get(i);
    enm = tempTeam.getPrincipalTarget(role);
    String roleName_en = role.toString();
    String displayName = role.getDisplay(Locale.KOREA);
    roleHash.put(displayName, role.toString());
    prin = null;
    ref = null;
    if (enm.hasMoreElements()) {
        ref = (WTPrincipalReference)enm.nextElement();
        prin = ref.getPrincipal();
        //roleHash_user.put(displayName, CommonUtil.getOIDString(prin));
    }


}


int rows = sheets[0].getRows();
String error = "";

HashMap  map = new HashMap();
map.put("oid", oid);

String key_role = "";
String key_user = "";
WTUser user = null;
for (int j = 1; j < rows; j++)
{
    Cell[] cell = sheets[0].getRow(j);

    for(int k = 0 ; k < cell.length ; k++){
        String name = e3ps.common.util.JExcelUtil.getContent(cell, k).trim();
        if(k % 2 == 1){
            String role = e3ps.common.util.JExcelUtil.getContent(cell, k - 1).trim();
            Kogger.debug(role + " <> " + name);
            hash.put(role, name);
            //Kogger.debug("role = " + role + "name = " + name);

            if(!roleHash.containsKey(role)){
                error = role + " " + messageService.getString("e3ps.message.ket_message", "00110")/*CFT Role 이 존재하지 않습니다*/;
                break;
            }


            key_user = (String)roleHash_user.get(role);//ootb에서 해당role에 담당자 가져오기

            if(key_user != null){
            	key_role = (String)roleHash.get(role);
            }else{
	            if(name != null && name.length() > 0){
	                user = getUser(name);
	                if(user == null){
	                    error = messageService.getString("e3ps.message.ket_message", "00042", name)/*{0}이(가) 존재하지 않습니다*/;
	                    break;
	                }
	                key_role = (String)roleHash.get(role);

	            }
            }


            Vector vv = new Vector();

			if(key_user != null || user != null){
            	if(key_user != null){
            		vv.add(key_user);
            		map.put(key_role, vv);
            	}else{
            		vv.add(CommonUtil.getOIDString(user));
            		map.put(key_role, vv);
            	}
            }

        }
    }
}

//Kogger.debug("error = " + error);
boolean flag = false;
if(error.length() > 0){

}else{

    flag = ProjectHelper.assignProjectMember(map);
}
//if(!flag) {

//  return;
//}


//map.put(role.toString(), roleVec);

%>

<script>
<%
if(!flag || error.length() > 0){
%>
    alert("<%=error %>");
<%
}else{
%>
    alert("<%=messageService.getString("e3ps.message.ket_message", "00303") %><%--Load에 성공하였습니다--%>");

    <%if(project.getPjtType() < 2){%>
        opener.document.forms[0].action="/plm/jsp/project/ReviewProjectView.jsp?radioValue=2";
        opener.document.forms[0].submit();
    <%}else{%>
        opener.location.reload();
    <%}%>
<%}%>
    self.close();
</script>


</body>
</html>
