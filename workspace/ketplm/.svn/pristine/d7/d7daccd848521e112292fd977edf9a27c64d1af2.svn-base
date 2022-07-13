<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.content.*,wt.fc.*,wt.util.*"%>
<%@page import = "wt.epm.EPMDocument"%>
<%@page import = "e3ps.common.content.ContentUtil,e3ps.common.content.ContentInfo"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    String cmd = request.getParameter("cmd");
    String oid = request.getParameter("oid");
    String isPrimary = request.getParameter("isPrimary");

    String primaryName = request.getParameter("primaryName");
    String secondaryName = request.getParameter("secondaryName");
    String delFileName = request.getParameter("delFileName");

    String width = request.getParameter("width");


    ContentHolder holder = null;
    ReferenceFactory rf = new ReferenceFactory();

    if( (oid != null) && (oid.trim().length() > 0) ) {
        try {
            holder = (ContentHolder)rf.getReference(oid).getObject();
            holder = (ContentHolder)ContentHelper.service.getContents(holder);
        }
        catch(Exception e) {
            Kogger.error(e);
        }
    }

    if( (cmd == null) || (cmd.trim().length() == 0) ) {
        cmd = "";
    }

    if( (primaryName == null) || (primaryName.trim().length() == 0) ) {
        primaryName = "primary";
    }

    if( (secondaryName == null) || (secondaryName.trim().length() == 0) ) {
        secondaryName = "secondary";
    }

    if( (width == null) || (width.trim().length() == 0) ) {
        width = "680";
    }

    int pwidth = Integer.parseInt(width);
    pwidth = pwidth + 10;
%>

<%
    if("view".equals(cmd)) {
%>
        <div width="<%=pwidth%>" style="border:0;padding:0;margin:0;"><ul style="margin:0;padding:0;list-style-type:none;">
<%
        int countsFile = 0;
        if( isPrimary.equals("true") ) {
            ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
            if(item != null) {
                ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                ++countsFile;
%>
                <li><%=cntInfo.getDownURLStr()%></li>
<%
            }
        } else if( isPrimary.equals("false") ) {
            QueryResult contents = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
            if(contents.size() > 0) {
                while(contents.hasMoreElements()) {
                    ContentItem item = (ContentItem)contents.nextElement();
                    if(item instanceof ApplicationData) {
                        ApplicationData appData = (ApplicationData)item;
                        if( (holder instanceof EPMDocument) && (appData.getDescription() != null) && "gerber".equals(appData.getDescription()) ) {
                            continue;
                        }
                        ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                        ++countsFile;
%>
                        <li><%=cntInfo.getDownURLStr()%></li>
<%
                    }
                }
            }
        } else if( isPrimary.equals("gerber") ) {
            QueryResult contents = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
            while(contents.hasMoreElements()) {
                ContentItem item = (ContentItem)contents.nextElement();
                if(item instanceof ApplicationData) {
                    ApplicationData appData = (ApplicationData)item;
                    if( (holder instanceof EPMDocument) && (appData.getDescription() != null) && "gerber".equals(appData.getDescription()) ) {
                        ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                        ++countsFile;
%>
                        <li><%=cntInfo.getDownURLStr()%></li>
<%
                    }
                }
            }

        } else {
            Vector cntFiles = ContentHelper.getContentList(holder);//ContentHelper.getContentListAll(holder);
            if(cntFiles.size() > 0) {
                for(int i = 0; i < cntFiles.size(); i++) {
                    ContentItem item = (ContentItem)cntFiles.get(i);
                    if(item instanceof ApplicationData) {
                        ApplicationData appData = (ApplicationData)item;
                        if( (holder instanceof EPMDocument) && (appData.getDescription() != null) && "gerber".equals(appData.getDescription()) ) {
                            continue;
                        }
                        ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                        ++countsFile;
%>
                        <li><%=cntInfo.getDownURLStr()%></li>
<%
                    }
                }
            }
        }
        if(countsFile == 0) {
            out.print("<li>&nbsp;</li>");
        }
%>
        </ul></div>
<%
    } else {
%>

        <%	if( isPrimary.equals(String.valueOf(true)) ) { %>
                <!-- Primary -->
                <!--
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                 -->
                <table cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td><input type="file" name="<%=primaryName%>" class="txt_field"  style="width:100%" id="primary"></td>
                    </tr>
                </table>
                <%	if(holder != null) { %>
                        <!--
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                         -->
                        <table cellpadding="0" cellspacing="0" width="100%" style="margin: 1 0;">
                            <tr>
                                <td>
                                    <div id="primaryContentDiv" class="table_border" style="padding:2px;text-decoration:none;">
                                        <%	holder = (ContentHolder)ContentHelper.service.getContents(holder);
                                            ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
                                            if( (item != null) && (item instanceof ApplicationData)) {
                                                ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item);
                                        %>
                                                <%=cntInfo.getDownURLStr()%>
                                        <%	}
                                        %>
                                    </div>
                                </td>
                            </tr>
                        </table>
                <%	} %>
                <script type="text/Javascript">
                <!--
                    /*****************************************************************************************
                     * Event ...
                     *****************************************************************************************/
                    function goDelMarking(_obj) {
                        if(_obj.id=='primary') {//if(_obj.name=='primary') {
                            //primaryContentDiv
                            var primaryCnt = document.getElementById("primaryContentDiv");
                            if(primaryCnt != null && primaryCnt != 'undefined') {
                                if(_obj.value.length > 0) {
                                    primaryCnt.style.textDecorationLineThrough=true;
                                    primaryCnt.style.color='red';
                                }else{
                                    primaryCnt.style.textDecorationLineThrough=false;
                                    primaryCnt.style.color='';
                                }
                            }

                        }
                    }

                    function goFileChangeEvent(oEvent) {
                        var oEvnetObj = oEvent.srcElement;
                        //var oEvnetObjName = oEvnetObj.name;
                        if(oEvnetObj.id=='primary') { //if(oEvnetObjName == 'primary') {
                            goDelMarking(oEvnetObj);
                        }
                    }

                    function fileChangeIE(){ // Explorer Key Event
                        if(event.type=='change') {
                            goFileChangeEvent(event);
                        }
                    }

                    //document.forms[0].primary.onchange=fileChangeIE;
                    document.getElementById("<%=primaryName%>").onchange=fileChangeIE;

                //  -->
                </script>
        <%	} else {%>
                <!-- Secondary -->
                <!--
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                 -->
                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:insertFileLine('secondFileTboty','<%=secondaryName%>','chksec');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="5">&nbsp;</td>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:deleteFileLine('secondFileTboty','<%=secondaryName%>','chksec');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <!--
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space5"></td>
                    </tr>
                </table>
                 -->
                <table width="100%" cellpadding="0" cellspacing="0" class="table_border" style="margin: 1 0;">
                    <tbody id="secondFileTboty"><!-- 부 첨부파일 --> 
                    </tbody>
                </table>
                <script type="text/Javascript">
                <!--
                    function insertFileLine(_tbodyid,_fname,_chkname) {
                        var tBody = document.getElementById(_tbodyid);
                        var innerRow = tBody.insertRow();

                        newTd = innerRow.insertCell();//delete
                        newTd.className = "tdwhiteM0";
                        newTd.width = "30";
                        newTd.innerHTML = "<input type='checkbox' name='"+_chkname+"' value='' />";

                        newTd = innerRow.insertCell();//file
                        newTd.className = "tdwhiteL0";
                        newTd.width = "<%=(pwidth-30)%>";
                        newTd.innerHTML = "<input type='file' name='"+_fname+"' class='txt_field' value='' style='width:99%;' />";
                    }

                    function deleteFileLine(_tbodyid,_fname,_chkname) {
                        var tBody = document.getElementById(_tbodyid);
                        if (tBody.rows.length == 0){ return; }

                        var file_checks = document.getElementsByName(_chkname);//eval("document.forms[0]."+_chkname);

                        for (var i = tBody.rows.length; i > 0; i--) {
                            if (file_checks[i-1].checked){ tBody.deleteRow(i - 1); }
                        }

                    }
                    
                    // 첫행을 추가한다. - 라인을 맞추기위해 script로 처리함.
                    insertFileLine('secondFileTboty','<%=secondaryName%>','chksec');
                    
                // -->
                </script>

                <%	if(holder != null) { %>
                        <!--
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                         -->
                        <table width="100%" cellpadding="0" cellspacing="0" class="table_border" style="margin: 1 0;padding:2px;">
                        <%	holder = (ContentHolder)ContentHelper.service.getContents(holder);
                            QueryResult contents = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
                            while(contents.hasMoreElements()) {
                                ContentItem item0 = (ContentItem)contents.nextElement();
                                if(item0 instanceof ApplicationData) {
                                    ContentInfo cntInfo = ContentUtil.getContentInfo(holder, item0);
                        %>
                                    <tr>
                                        <td width="50" class="tdwhiteM0">
                                            <div id="delete_<%=cntInfo.getContentOid()%>" style="display:;">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doDelFile('<%=cntInfo.getContentOid()%>','true','delete_<%=cntInfo.getContentOid()%>','cancel_<%=cntInfo.getContentOid()%>','<%=delFileName%>');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></font></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="cancel_<%=cntInfo.getContentOid()%>" style="display:none;">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:;" onClick="javascript:doDelFile('<%=cntInfo.getContentOid()%>','false','cancel_<%=cntInfo.getContentOid()%>','delete_<%=cntInfo.getContentOid()%>','<%=delFileName%>');" class="btn_blue"><font size='1'><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></font></a></td>
                                                        <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </td>
                                        <td width="<%=(pwidth-50)%>" class="tdwhiteL0">
                                            <div id="<%=cntInfo.getContentOid()%>" style="text-decoration:none;"><%=cntInfo.getDownURLStr()%></div>
                                        </td>
                                    </tr>
                        <%
                                }
                            }
                        %>
                        </table>
                        <script type="text/Javascript">
                        <!--
                            function doDelFile(_key,_flag,_hidden,_display,_delFieldName) {
                                var delFileDiv = document.getElementById(_key);
                                var btnHidden = document.getElementById(_hidden);
                                var btnDisplay = document.getElementById(_display);

                                if(delFileDiv != null && delFileDiv != 'undefined') {
                                    if(_flag == 'true') {
                                        delFileDiv.style.textDecorationLineThrough=true;
                                        delFileDiv.style.color='red';
                                    }else{
                                        delFileDiv.style.textDecorationLineThrough=false;
                                        delFileDiv.style.color='';
                                    }

                                    if(_flag == 'true') {
                                        var newObjFile = document.createElement('INPUT');
                                        newObjFile.type='hidden';
                                        newObjFile.name=_delFieldName;
                                        newObjFile.value = _key;
                                        delFileDiv.insertAdjacentElement('beforeEnd',newObjFile);

                                    } else {
                                        var chNodes = delFileDiv.children.tags('INPUT');
                                        for(var i = 0; i < chNodes.length; i++) {
                                            chNode = chNodes[i];
                                            if(chNode.name = _delFieldName) {
                                                delFileDiv.removeChild(chNode);
                                                break;
                                            }
                                        }
                                    }
                                }

                                btnHidden.style.display='none';
                                btnDisplay.style.display='';
                            }
                        // -->
                        </script>
                <%	} %>
        <%	} %>
        <!--
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
         -->
<%	} %>

