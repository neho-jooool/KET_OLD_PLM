<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.QueryResult,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.PageControl,
                  e3ps.common.web.ParamUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String checkDept = StringUtil.checkNull(request.getParameter("checkDept"));

    ArrayList list = (ArrayList)request.getAttribute("moldList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    Hashtable moldProject = null;
    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    int p51 = 0;
    int p52 = 0;
    int p53 = 0;
    int p61 = 0;
    int p62 = 0;
    int p63 = 0;
    int p71 = 0;
    int p72 = 0;
    int p73 = 0;
    int p81 = 0;
    int p82 = 0;
    int p83 = 0;

    int m11 = 0;
    int m12 = 0;
    int m13 = 0;
    int m21 = 0;
    int m22 = 0;
    int m23 = 0;
    int m31 = 0;
    int m32 = 0;
    int m33 = 0;
    int m41 = 0;
    int m42 = 0;
    int m43 = 0;
    int m51 = 0;
    int m52 = 0;
    int m53 = 0;
    int m61 = 0;
    int m62 = 0;
    int m63 = 0;
    int m71 = 0;
    int m72 = 0;
    int m73 = 0;
    int m81 = 0;
    int m82 = 0;
    int m83 = 0;

    if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            moldProject = (Hashtable)list.get(inx);

            if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate")) ) {
                p11 = p11 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p12 = p12 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p13 = p13 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate")) ) {
                p21 = p21 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate"))   ) {
                p22 = p22 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p23 = p23 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate")) ) {
                p31 = p31 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p32 = p32 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p33 = p33 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p41 = p41 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                p42 = p42 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p43 = p43 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p51 = p51 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p52 = p52 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p53 = p53 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p61 = p61 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                p62 = p62 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p63 = p63 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p71 = p71 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                p72 = p72 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p73 = p73 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate"))  ) {
                p81 = p81 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                p82 = p82 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                p83 = p83 + Integer.parseInt((String)moldProject.get("count"));
            }
            else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m11 = m11 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m12 = m12 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m13 = m13 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m21 = m21 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                m22 = m22 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "지연".equals((String)moldProject.get("taskstate"))  && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m23 = m23 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산")  && "완료".equals((String)moldProject.get("taskstate")) ) {
                m31 = m31 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m32 = m32 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m33 = m33 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "완료".equals((String)moldProject.get("taskstate")) ) {
                  m41 = m41 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") ) && "진행".equals((String)moldProject.get("taskstate")) ) {
                m42 = m42 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("사내") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m43 = m43 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m51 = m51 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m52 = m52 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("시작") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m53 = m53 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m61 = m61 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") )  && "진행".equals((String)moldProject.get("taskstate")) ) {
                m62 = m62 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("시작Mo") ||  ((String)moldProject.get("name")).equals("시작Fa") ) && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m63 = m63 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "완료".equals((String)moldProject.get("taskstate"))  ) {
                m71 = m71 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "진행".equals((String)moldProject.get("taskstate")) ) {
                m72 = m72 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("name")).equals("양산") && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m73 = m73 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "완료".equals((String)moldProject.get("taskstate"))  ) {
                m81 = m81 + Integer.parseInt((String)moldProject.get("count"));
              }else if( "M".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )&& "진행".equals((String)moldProject.get("taskstate")) ) {
                m82 = m82 + Integer.parseInt((String)moldProject.get("count"));
            }else if( "O".equals( ((String)moldProject.get("pjtType")) ) && ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("making")).equals("외주") && ( ((String)moldProject.get("name")).equals("양산Mo") ||  ((String)moldProject.get("name")).equals("양산Fa") )  && "지연".equals((String)moldProject.get("taskstate")) && !"PMOINWORK".equals( (String)moldProject.get("statestate") ) && !"DEVASSIGN".equals(moldProject.get("statestate")) ){
                m83 = m83 + Integer.parseInt((String)moldProject.get("count"));
            }else{}
        }
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--

-->
</style>

<script language='javascript'>
<!--

    //Project Report 화면 Open
    function goView(cmd, rowcon1, rowcon2, colcon1, colcon2){
        var url = '/plm/jsp/project/TaskReportList.jsp'
                   + '?cmd=' + cmd + '&itemType=' + rowcon1 + '&statestate=' + rowcon2 + '&making=' + colcon1 + '&moldType=' + colcon2
                   + '&checkDept=<%=checkDept%>&year=<%=(String)condition.get("year")%>&dept=<%=(String)condition.get("dept")%>&dept1=<%=(String)condition.get("dept1")%>&dept2=<%=(String)condition.get("dept2")%>&dept3=<%=(String)condition.get("dept3")%>&creator=<%=(String)condition.get("creator")%>&mTaskName=<%=(String)condition.get("mTaskName")%>';
        openWindow(url, '',880,750);
    }

//-->
</script>

</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="780">
    <col width='43'><col width='92'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'>
  <tr>
    <td rowspan='2' colspan='2' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
    <td rowspan='2' class="tdblueMB"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td colspan='4' class="tdblueMB">Press</td>
    <td colspan='4' class="tdblueM0">Mold</td>
  </tr>
  <tr>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td class="tdblueMB"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>

    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>

  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%><br><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '사내', 'MAT001');"><b><%=p11+p12+p13+m11+m12+m13%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT001');"><b><%=p11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT001');"><b><%=p12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT001');"><b><font color="red"><%=p13%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT001');"><b><%=p11+p12+p13%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT001');"><b><%=m11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT001');"><b><%=m12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT001');"><b><font color="red"><%=m13%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT001');"><b><%=m11+m12+m13%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '사내', 'MAT003,MAT005');"><b><%=p21+p22+p23+m21+m22+m23%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT003,MAT005');"><b><%=p21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT003,MAT005');"><b><%=p22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT003,MAT005');"><b><font color="red"><%=p23%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT003,MAT005');"><b><%=p21+p22+p23%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT003,MAT005');"><b><%=m21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT003,MAT005');"><b><%=m22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT003,MAT005');"><b><font color="red"><%=m23%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT003,MAT005');"><b><%=m21+m22+m23%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '사내', 'MAT002');"><b><%=p31+p32+p33+m31+m32+m33%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT002');"><b><%=p31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT002');"><b><%=p32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT002');"><b><font color="red"><%=p33%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT002');"><b><%=p31+p32+p33%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT002');"><b><%=m31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT002');"><b><%=m32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT002');"><b><font color="red"><%=m33%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT002');"><b><%=m31+m32+m33%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '사내', 'MAT004,MAT006');"><b><%=p41+p42+p43+m41+m42+m43%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT004,MAT006');"><b><%=p41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT004,MAT006');"><b><%=p42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT004,MAT006');"><b><font color="red"><%=p43%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT004,MAT006');"><b><%=p41+p42+p43%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT004,MAT006');"><b><%=m41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT004,MAT006');"><b><%=m42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT004,MAT006');"><b><font color="red"><%=m43%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT004,MAT006');"><b><%=m41+m42+m43%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01913") %><%--소계--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '사내', '');"><b><%=p11+p12+p13+m11+m12+m13+p21+p22+p23+m21+m22+m23+p31+p32+p33+m31+m32+m33+p41+p42+p43+m41+m42+m43%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', '');"><b><%=p11+p21+p31+p41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', '');"><b><%=p12+p22+p32+p42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', '');"><b><font color="red"><%=p13+p23+p33+p43%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', '');"><b><%=p11+p12+p13+p21+p22+p23+p31+p32+p33+p41+p42+p43%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', '');"><b><%=m11+m21+m31+m41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', '');"><b><%=m12+m22+m32+m42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', '');"><b><font color="red"><%=m13+m23+m33+m43%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', '');"><b><%=m11+m12+m13+m21+m22+m23+m31+m32+m33+m41+m42+m43%></b></a></td>
  </tr>
  <tr>
    <td  colspan="17" class="tab_btm2"></td>
  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%><br><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '외주', 'MAT001');"><b><%=p51+p52+p53+m51+m52+m53%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT001');"><b><%=p51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT001');"><b><%=p52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT001');"><b><font color="red"><%=p53%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '외주', 'MAT001');"><b><%=p51+p52+p53%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT001');"><b><%=m51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT001');"><b><%=m52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT001');"><b><font color="red"><%=m53%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '외주', 'MAT001');"><b><%=m51+m52+m53%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '외주', 'MAT003,MAT005');"><b><%=p61+p62+p63+m61+m62+m63%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT003,MAT005');"><b><%=p61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT003,MAT005');"><b><%=p62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT003,MAT005');"><b><font color="red"><%=p63%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '외주', 'MAT003,MAT005');"><b><%=p61+p62+p63%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT003,MAT005');"><b><%=m61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT003,MAT005');"><b><%=m62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT003,MAT005');"><b><font color="red"><%=m63%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '외주', 'MAT003,MAT005');"><b><%=m61+m62+m63%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '외주', 'MAT002');"><b><%=p71+p72+p73+m71+m72+m73%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT002');"><b><%=p71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT002');"><b><%=p72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT002');"><b><font color="red"><%=p73%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '외주', 'MAT002');"><b><%=p71+p72+p73%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT002');"><b><%=m71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT002');"><b><%=m72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT002');"><b><font color="red"><%=m73%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '외주', 'MAT002');"><b><%=m71+m72+m73%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '외주', 'MAT004,MAT006');"><b><%=p81+p82+p83+m81+m82+m83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT004,MAT006');"><b><%=p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT004,MAT006');"><b><%=p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT004,MAT006');"><b><font color="red"><%=p83%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '외주', 'MAT004,MAT006');"><b><%=p81+p82+p83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT004,MAT006');"><b><%=m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT004,MAT006');"><b><%=m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT004,MAT006');"><b><font color="red"><%=m83%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '외주', 'MAT004,MAT006');"><b><%=m81+m82+m83%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01913") %><%--소계--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '외주', '');"><b><%=p51+p52+p53+m51+m52+m53+p61+p62+p63+m61+m62+m63+p71+p72+p73+m71+m72+m73+p81+p82+p83+m81+m82+m83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', '');"><b><%=p51+p61+p71+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', '');"><b><%=p52+p62+p72+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', '');"><b><font color="red"><%=p53+p63+p73+p83%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '외주', '');"><b><%=p51+p52+p53+p61+p62+p63+p71+p72+p73+p81+p82+p83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', '');"><b><%=m51+m61+m71+m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', '');"><b><%=m52+m62+m72+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', '');"><b><font color="red"><%=m53+m63+m73+m83%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '외주', '');"><b><%=m51+m52+m53+m61+m62+m63+m71+m72+m73+m81+m82+m83%></b></a></td>
  </tr>
  <tr>
    <td  colspan="17" class="tab_btm2"></td>
  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '', 'MAT001');"><b><%=p11+p12+p13+p51+p52+p53+m11+m12+m13+m51+m52+m53%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT001');"><b><%=p11+p51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT001');"><b><%=p12+p52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT001');"><b><font color="red"><%=p13+p53%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '', 'MAT001');"><b><%=p11+p12+p13+p51+p52+p53%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT001');"><b><%=m11+m52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT001');"><b><%=m12+m52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT001');"><b><font color="red"><%=m13+m53%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '', 'MAT001');"><b><%=m11+m12+m13+m51+m52+m53%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '', 'MAT003,MAT005');"><b><%=p21+p22+p23+p61+p62+p63+m21+m22+m23+m61+m62+m63%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT003,MAT005');"><b><%=p21+p61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT003,MAT005');"><b><%=p22+p62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT003,MAT005');"><b><font color="red"><%=p23+p63%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '', 'MAT003,MAT005');"><b><%=p21+p22+p23+p61+p62+p63%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT003,MAT005');"><b><%=m21+m62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT003,MAT005');"><b><%=m22+m62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT003,MAT005');"><b><font color="red"><%=m23+m63%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '', 'MAT003,MAT005');"><b><%=m21+m22+m23+m61+m62+m63%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '', 'MAT002');"><b><%=p31+p32+p33+p71+p72+p73+m31+m32+m33+m71+m72+m73%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT002');"><b><%=p31+p71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT002');"><b><%=p32+p72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT002');"><b><font color="red"><%=p33+p73%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '', 'MAT002');"><b><%=p31+p32+p33+p71+p72+p73%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT002');"><b><%=m31+m72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT002');"><b><%=m32+m72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT002');"><b><font color="red"><%=m33+m73%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '', 'MAT002');"><b><%=m31+m32+m33+m71+m72+m73%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '', 'MAT004,MAT006');"><b><%=p41+p42+p43+p81+p82+p83+m41+m42+m43+m81+m82+m83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT004,MAT006');"><b><%=p41+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT004,MAT006');"><b><%=p42+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT004,MAT006');"><b><font color="red"><%=p43+p83%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', '', '', 'MAT004,MAT006');"><b><%=p41+p42+p43+p81+p82+p83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT004,MAT006');"><b><%=m41+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT004,MAT006');"><b><%=m42+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT004,MAT006');"><b><font color="red"><%=m43+m83%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', '', '', 'MAT004,MAT006');"><b><%=m41+m42+m43+m81+m82+m83%></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', '', '', '', '');"><b><%=p11+p12+p13+p21+p22+p23+p31+p32+p33+p41+p42+p43+p51+p52+p53+p61+p62+p63+p71+p72+p73+p81+p82+p83+m11+m12+m13+m21+m22+m23+m31+m32+m33+m41+m42+m43+m51+m52+m53+m61+m62+m63+m71+m72+m73+m81+m82+m83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', '');"><b><%=p11+p21+p31+p41+p51+p61+p71+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', '');"><b><%=p12+p22+p32+p42+p52+p62+p72+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'D', '', '');"><b><font color="red"><%=p13+p23+p33+p43+p53+p63+p73+p83%></font></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'S', '', '');"><b><%=p11+p12+p13+p21+p22+p23+p31+p32+p33+p41+p42+p43+p51+p52+p53+p61+p62+p63+p71+p72+p73+p81+p82+p83%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', '');"><b><%=m11+m21+m31+m41+m51+m61+m71+m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', '');"><b><%=m12+m22+m32+m42+m52+m62+m72+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'D', '', '');"><b><font color="red"><%=m13+m23+m33+m43+m53+m63+m73+m83%></font></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'S', '', '');"><b><%=m11+m12+m13+m21+m22+m23+m31+m32+m33+m41+m42+m43+m51+m52+m53+m61+m62+m63+m71+m72+m73+m81+m82+m83%></b></a></td>
  </tr>
</table>
</body>
</html>
