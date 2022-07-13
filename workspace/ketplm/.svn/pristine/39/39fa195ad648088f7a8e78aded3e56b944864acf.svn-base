<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.util.*"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.part.WTPart"%>
<%@page import="e3ps.edm.beans.EDMHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/prototype.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/scriptaculous.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/HTMLtemplateutil/submitFunctions.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/calendar.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/wtcore/js/com/ptc/core/ca/web/misc/content.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/feedback.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/cad.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/components/wizard.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/wtcore/js/com/ptc/core/components/menu.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/attachments/attachments.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/uwgm/cadx/openincadtool/openincadtool.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/uwgm/cadx/caddoc/contentcompare.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/htmlcomp/location/browseFoldersUtils.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/validate.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/firebug.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/components/driverAttributesSetup.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/htmlcomp/htmlcomp.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/htmlcomp/jstable/jstable.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/templates/htmlcomp/dynamicPopup.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/prototip.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/fckeditor/fckeditor.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->
<!-- <script type="text/javascript" SRC="/plm/wtcore/js/com/ptc/core/cancel/cancel.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->


<!-- <script type="text/javascript" SRC="/plm/netmarkets/javascript/util/main.js"></script><noscript>JavaScript에 액세스할 수 없습니다.</noscript> -->


<SCRIPT language="Javascript" SRC="/plm/templates/cadx/common/trlUtils.js"></SCRIPT>
<!-- <SCRIPT language="Javascript" SRC="/plm/wtcore/jsp/wvs/thumbviewer.js"></SCRIPT> -->
<!-- <SCRIPT language="Javascript" SRC="/plm/wtcore/jsp/wvs/visminithumb.js"></SCRIPT> -->

<SCRIPT Language="Javascript">
function createCDialogWindow(dialogURL, dialogName, w, h, statusBar, scrollBar) {
    if( typeof(_use_wvs_cookie) != "undefined" ) {
        var cookie_value = document.cookie;
        if( cookie_value != null ) {
            var loc = cookie_value.indexOf("wvs_ContainerOid=");
            if( loc >= 0 ) {
                var subp = cookie_value.substring(loc+4);
                loc = subp.indexOf(";");
                if( loc >= 0 ) subp = subp.substring(0, loc);
                dialogURL += "&" + subp;
            }
        }
    }else {
        var vm_url = "" + top.document.location;
        if( vm_url != null ) {
            var loc = vm_url.indexOf("ContainerOid=");
            if( loc < 0 ) {
                loc = vm_url.indexOf("/listFiles.jsp?oid=");
                if( loc >= 0 ) {
                    vm_url = "ContainerOid=" + vm_url.substring(loc+19);loc = 0;
                }
            }
            if( loc >= 0 ) {
                var subp = vm_url.substring(loc);
                loc = subp.indexOf("&");
                if( loc >= 0 ) subp = subp.substring(0, loc);
                if( dialogURL.indexOf("/blank.jsp?")>0 ) {
                    loc = dialogURL.indexOf("fname=");
                    if( loc >= 0 ) {
                        var fname = dialogURL.substring(loc+6);
                        loc = fname.indexOf("&");
                        if( loc >= 0 ) fname = fname.substring(0, loc);
                        try {
                            if( document.forms[fname].action.indexOf("ContainerOid=")<0 ) {
                                document.forms[fname].action += "&" + subp;
                            }
                        } catch(e) {}
                    }
                }
                dialogURL += "&" + subp;
            }
        }
    }
    createDialogWindow(dialogURL, dialogName, w, h, statusBar, scrollBar);
}
function createDialogWindow(dialogURL, dialogName, w, h, statusBar, scrollBar) {
    if( statusBar == null ) statusBar = 0;
    if( scrollBar == null ) scrollBar = 1;
    var opts = "toolbar=0,location=0,directory=0,status=" + statusBar + ",menubar=0,scrollbars="+scrollBar+",resizable=1,width=" + w + ",height=" + h;
    createDialogWindowOptions(dialogURL, dialogName, opts);
}
function createDialogWindowOptions(dialogURL, dialogName, opts) {
    if ( opts == "" ) {
        if (navigator.userAgent.indexOf("Mozilla/4.") >= 0 && navigator.userAgent.indexOf("MSIE") == -1) {
            opts = "toolbar=1,location=1,directory=1,status=1,menubar=1,scrollbars=1,resizable=1";
        }
    }
    if( dialogName.indexOf("VisNav") == 0 ) opts = opts + ",top=0,left=0";
    alert("<%=messageService.getString("e3ps.message.ket_message", "00990") %><%--권한이 없습니다--%>");
    //var newwin = wfWindowOpen(dialogURL, dialogName, opts);//trlUtils.js
    //if( newwin != null ) newwin.focus();

}
function closeDialogWindow() {
    wfWindowClose();//trlUtils.js
}
function evalJspResult(aEvent,aURL) {
    var mOid = null;
    var mContainerOid = null;
    var mRowOid = null;
    var mRowContainerOid = null;
    mOid = extractParamValue(window.location.href,"oid");
    if ((mOid == null) || (mOid.length < 1)) {
        mOid = extractParamValue(window.location.href,"objref");
    }
    mContainerOid = extractParamValue(window.location.href,"ContainerOid");
    var mEvent = (aEvent != null) ? aEvent : event;
    var mTarget = (mEvent.target) ? mEvent.target : mEvent.srcElement;
    if (mTarget) {
        var mTRnode = getParentNodeByTag(mTarget,"TR");
        if (mTRnode && (mTRnode.nodeName == "TR")) {
            var mAtt = mTRnode.getAttribute("onclick");
            if (mAtt) {
                var mOnclick = mAtt.toString();
                var mItems = mOnclick.split(",");
                for (var i = 0; (i < mItems.length) && ((mRowOid == null) || (mRowContainerOid == null)); i++) {
                    var mItem = trimString(mItems[i]);
                    if (mItem == "'oid'") {
                        i++;
                        var tmp = trimString(mItems[i]);
                        mRowOid = tmp.substring(1,tmp.length-1);
                    } else if (mItem == "'ContainerOid'") {
                        i++;
                        var tmp = trimString(mItems[i]);
                        mRowContainerOid = tmp.substring(1,tmp.length-1);
                    }
                }
            }
        }
    }
    var mDelim = "?";
    if (aURL.indexOf(mDelim) >= 0) {
        mDelim = "&";
    }
    if ((mOid != null) && (mOid.length > 0)) {
        aURL += mDelim + "oid=" + mOid;
        mDelim = "&";
    }
    if ((mContainerOid != null) && (mContainerOid.length > 0)) {
        aURL += mDelim + "ContainerOid=" + mContainerOid;mDelim = "&";
    }
    if ((mRowOid != null) && (mRowOid.length > 0)) {
        aURL += mDelim + "rowOid=" + mRowOid;mDelim = "&";
    }
    if ((mRowContainerOid != null) && (mRowContainerOid.length > 0)) {
        aURL += mDelim + "rowContainerOid=" + mRowContainerOid;mDelim = "&";
    }
    if (getMainForm()) {
        if(getMainForm().changeItemRef) {
            aURL += mDelim + "changeItem=" + getMainForm().changeItemRef.value;
            mDelim = "&";
        }
        if(getMainForm().changeableRef) {
            aURL += mDelim + "changeable=" + getMainForm().changeableRef.value;
            mDelim = "&";
        }
    }
    if (requestHandler) {
        var mOpts = {asynchronous: false};
        var mReq = requestHandler.doRequest(aURL,mOpts);
        if (mReq && mReq.responseText) {
            var mResp = trimString(mReq.responseText);
            if (mResp.length > 0) {
                eval(mResp);
            }
        }
    }
    return false;
}

/**
 * Deprecated, use requestHandler.doRequest instead
 * @deprecated
 */
function newHttp() {
      var xmlhttp = false;

      if (window.XMLHttpRequest) { // Mozilla, Safari, ...
         xmlhttp = new XMLHttpRequest();
         // If Pages are XHTML compliant, we should un-comment these lines so responseXML works in some older mozilla browsers
         //if (xmlhttp.overrideMimeType) {
         //   xmlhttp.overrideMimeType('text/xml');
         //}
      }
      else if (window.ActiveXObject) { // IE
         try { xmlhttp = new ActiveXObject("Msxml2.XMLHTTP.5.0"); } catch (e) {
            try { xmlhttp = new ActiveXObject("Msxml2.XMLHTTP.4.0"); } catch (e) {
               try {xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
               } catch (e) {
                  try {
                     xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                  } catch (e) {}
               }
            }
         }
      }
      return xmlhttp;
}


function copyToCommonClipboard(copyToClipboardURL){
    var ContainerOid = "";
    if (typeof(_use_wvs_cookie) != "undefined") {
        var documentCookie = document.cookie;
        if (documentCookie != null) {
            var start = documentCookie.indexOf("wvs_ContainerOid=");
            if (start >= 0) {
                start += 4;
                var end = documentCookie.indexOf(";",start + 1);
                ContainerOid = (end > start) ? documentCookie.substring(start,end) : cookie_value.substring(start);
            }
        }
    }
    var url = copyToClipboardURL;
    if (ContainerOid.length > 0) {
        url += "&" + ContainerOid;
    }
    var xmlhttp = newHttp();//main.js
    xmlhttp.open("GET", url, false);
    xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xmlhttp.send(null);
    if (xmlhttp.responseText && xmlhttp.responseText.length > 0) {
        var msg = trimString(xmlhttp.responseText);
        if (msg && msg.length > 0) {
            wfalert(msg);//trlUtils.js
        }
    }
}

function trimString(str) {
    str = this != window? this : str;return str.replace(/^\s+/g, '').replace(/\s+$/g, '');
}
</SCRIPT>


<%
    ReferenceFactory rf = new ReferenceFactory();

    String oid = request.getParameter("oid");

    Object o = rf.getReference(oid).getObject();
    if(o instanceof WTPart) {
        ArrayList refDocs = new ArrayList();

        //관련 모델 조회.
        refDocs = EDMHelper.getReferenceDocs((WTPart)o,EDMHelper.REFERENCE_TYPE_MODEL,-1);

        //대표 연관 도면
        if(refDocs.size() == 0) {
            refDocs = EDMHelper.getReferenceDocs((WTPart)o,EDMHelper.REFERENCE_TYPE_RELATED,EDMHelper.REQUIRED_STANDARD);
        }

        //관련 연관 도면
        if(refDocs.size() == 0) {
            refDocs = EDMHelper.getReferenceDocs((WTPart)o,EDMHelper.REFERENCE_TYPE_RELATED,EDMHelper.REQUIRED_RELATED);
        }

        if(refDocs.size() > 0) {
            oid = rf.getReferenceString((EPMDocument)((Object[])refDocs.get(0))[1]);
        }
    }

    boolean isSupport = true;

    o = rf.getReference(oid).getObject();
    if(o instanceof EPMDocument) {
        EPMDocument oo = (EPMDocument)o;
        String authoringApp = oo.getAuthoringApplication().toString();
        if( !("ACAD".equals(authoringApp) || "CATIAV5".equals(authoringApp) || "PROE".equals(authoringApp) || "UG".equals(authoringApp)) ) {
            isSupport = false;
        }
    }

    String[] sss =  com.ptc.wvs.server.ui.UIHelper.getDefaultVisualizationData(oid,false, Locale.KOREA);
%>

<table width="100%" height="100%" cellspacing="2" cellpadding="0">
    <tr>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor=black>
                <tr bgcolor=white>
                    <td align="center" valign="center">
                        <%if(isSupport){%><%=sss[1]%><%}else{%>
                        <img src="../../portal/images/gnome_window_close_48.png"><br><font color="brown"><b><%=messageService.getString("e3ps.message.ket_message", "00547") %><%--Viewer 지원안됨--%></b></font>
                        <%}%>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <%if(false){%>
    <tr>
        <td width="100%">
            <table width="100%" cellspacing="1" cellpadding="0" border="0" bgcolor=black>
                <colgroup span="3" width="33%" align="center" valign="middle" bgcolor=white>
                <tr>
                    <td width="33%">
                        <table class="vizBtns">
                            <tr>
                                <td>
                                    <%=sss[2]%>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="33%">
                        <table class="vizBtns">
                            <tr>
                                <td>
                                    <%=sss[3]%>
                                </td>
                            </tr>
                        </table>
                    </td>

                    <td width="33%">
                        <table class="vizBtns">
                            <tr>
                                <td>
                                    <%=sss[13]%>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <%}%>
</table>
