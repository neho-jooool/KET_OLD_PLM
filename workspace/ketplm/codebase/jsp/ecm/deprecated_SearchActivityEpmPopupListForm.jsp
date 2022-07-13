<%@page import="e3ps.common.util.VersionUtil"%>
<%@page import="e3ps.common.util.KETObjectUtil"%>
<%@page import="e3ps.ecm.beans.EcmUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*"%>
<%@ page import="wt.iba.value.IBAHolder,wt.inf.container.WTContainerRef"%>
<%@ page import="wt.session.SessionHelper,wt.org.WTUser,wt.htmlutil.HtmlUtil"%>
<%@ page import="e3ps.edm.*,e3ps.edm.util.*,e3ps.edm.beans.*"%>
<%@ page import="e3ps.common.code.*,
                wt.epm.EPMDocument,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.util.WCUtil,
                e3ps.common.util.StringUtil
                ,e3ps.common.web.ParamUtil
                ,java.util.ArrayList"%>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
                
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    EDMProperties edmProperties = EDMProperties.getInstance();
    EDMAttributes edmAttributes = EDMAttributes.getInstance();

    String prodMoldCls = ParamUtil.getParameter(request, "prodMoldCls");

    WTContainerRef wtContainerRef = EDMUtil.getWTContainerRef(edmProperties.getContainer());
    WTUser currentUser = (WTUser)SessionHelper.manager.getPrincipal();
    String webAppName = CommonUtil.getWebAppName();
    String[] definedListCounts = edmProperties.getDefinedListCounts();

    String userDivision = KETObjectUtil.getCurrentUserGroup();
    NumberCode numCode = null;

    if( userDivision.equals("자동차사업부") )
    {
        numCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", "C");
    }
    else if( userDivision.equals("전자사업부") )
    {
        numCode = NumberCodeHelper.manager.getNumberCode("DIVISIONFLAG", "E");
    }

    //condions
    String command = StringUtil.defaultIfEmpty(request.getParameter("command"), "search");
    String number = StringUtil.trimToEmpty(request.getParameter("docNumber"));
    String name = StringUtil.trimToEmpty(request.getParameter("docName"));
    String partNumber = "";
//	String partNumber = StringUtil.trimToEmpty(request.getParameter("partNumber"));

	String state = request.getParameter("state");
    //String state = "APPROVED";
    String latest = StringUtil.trimToEmpty(request.getParameter("latest"));

    String businessType = StringUtil.trimToEmpty(request.getParameter("businessType"));

    businessType = numCode.getPersistInfo().getObjectIdentifier().getStringValue();

    String devStage = StringUtil.trimToEmpty(request.getParameter("devStage"));
    String category = StringUtil.trimToEmpty(request.getParameter("category"));
    String cadAppType = StringUtil.trimToEmpty(request.getParameter("cadAppType"));
    String isDummyFile = StringUtil.trimToEmpty(request.getParameter("isDummyFile"));

    String createStart = StringUtil.trimToEmpty(request.getParameter("create_start"));
    String createEnd = StringUtil.trimToEmpty(request.getParameter("create_end"));
    String modifyStart = StringUtil.trimToEmpty(request.getParameter("modify_start"));
    String modifyEnd = StringUtil.trimToEmpty(request.getParameter("modify_end"));

    String creator = StringUtil.trimToEmpty(request.getParameter("creator"));
    String modifier = StringUtil.trimToEmpty(request.getParameter("modifier"));

    String edmCreateDeptName = StringUtil.trimToEmpty(request.getParameter("edmCreateDeptName"));
    String edmModifyDeptName = StringUtil.trimToEmpty(request.getParameter("edmModifyDeptName"));

    String descending = StringUtil.trimToEmpty(request.getParameter("descending"));
    String sortColumn = StringUtil.trimToEmpty(request.getParameter("sortColumn"));
    String sortClass = StringUtil.trimToEmpty(request.getParameter("sortClass"));

    if(latest.length() == 0) { latest = "true";	}
    if(descending.length() == 0) { descending = "true";	}
//	if(sortColumn.length() == 0) { sortColumn = "thePersistInfo.createStamp"; }
//	sortColumn = "thePersistInfo.createStamp";
    sortColumn = "number";
//	if(sortClass.length() == 0) { sortClass = wt.epm.EPMDocument.class.getName(); }
  if(sortClass.length() == 0) { sortClass = wt.epm.EPMDocumentMaster.class.getName(); }

    //페이지 관련 Parameter
    String psizeStr = StringUtil.defaultIfEmpty(request.getParameter("psize"), "10000");
    String cpageStr = StringUtil.defaultIfEmpty(request.getParameter("cpage"), "1");

    if(psizeStr.length() == 0) { psizeStr = definedListCounts[0];	}

    //페이지 처리
    int psize = 10;
    int cpage = 1;
    int total = 0;
    int pageCount = 5;


    long sessionId = 0L;

    if(psizeStr.trim().length() > 0) {
        psize = Integer.parseInt(psizeStr);
    }

    if(cpageStr.trim().length() > 0) {
        cpage = Integer.parseInt(cpageStr);
    }


    int listStart = (cpage-1)*psize+1;
    int listEnd = listStart+psize;

    //QueryResult result = null;
    PagingQueryResult result = null;
    if("search".equals(command)) {
        try {
            if(sessionId > 0L) {
                result = EDMQueryHelper.fetchPagingSession(listStart-1,psize,sessionId);
            } else {
                HashMap map = new HashMap();
                map.put("command",command);
                map.put("number",number);
                map.put("name",name);
                map.put("partNumber",partNumber);
                map.put("state",state);
                map.put("latest",latest);

                map.put("businessType",businessType);

                map.put("create_start",createStart);
                map.put("create_end",createEnd);
                map.put("modify_start",modifyStart);
                map.put("modify_end",modifyEnd);

                map.put("creator",creator);
                map.put("modifier",modifier);

                map.put("edmCreateDeptName",edmCreateDeptName);
                map.put("edmModifyDeptName",edmModifyDeptName);

                map.put("className",wt.epm.EPMDocument.class.getName());

                //IBA 값 처리
                if(devStage.length() > 0) {
                    map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
                }
                if(category.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
                }
                if(cadAppType.length() > 0) {
                    map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
                }
                if(isDummyFile.length() > 0) {
                    map.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
                }

                Vector sorts = new Vector();
                Object[] so = new Object[3];
                so[0] = sortColumn;
                so[1] = Class.forName(sortClass);//wt.epm.EPMDocumentMaster.class;
//				so[2] = new Boolean("true".equals(descending.toLowerCase()));
                so[2] = new Boolean("false".equals(descending.toLowerCase()));
                sorts.add(so);
                map.put("sort",sorts);

                result = EDMQueryHelper.openPagingSession(map,listStart-1,psize);

                sessionId = result.getSessionId();
                total = result.getTotalSize();
            }
        }
        catch(Exception e) {
            Kogger.error(e);
        }
        finally {
            if(sessionId != 0L) {
                PagingSessionHelper.closePagingSession(sessionId);
            }
        }
    }

%>
<script language="javascript">
var arr = new Array();
var idx = 0;
parent.deleteEpmAllList();
    <%	if( (result == null) || (result.size() == 0)) { %>
parent.addEpmNotFound();
    <%	} else { %>
            <%	ReferenceFactory rf = new ReferenceFactory();
                int rowOrderNumber = listStart;

                WTUser creator0 = null;

                Object oo[] = null;
                String epmType = "";
                EPMDocument epm = null;
                ArrayList ecadList = null;
                while(result.hasMoreElements())
                {
                    oo = (Object[])result.nextElement();
                    String nr = (String)oo[1];
                    String nm = (String)oo[2];
                    String nv = (String)oo[3];
                    String estate = (String)oo[4];
                    String noid = (String)oo[0];

                    epm = (EPMDocument)WCUtil.getObject(noid);
                    if("PLMSYSTEM".equals(epm.getOwnerApplication().toString())) {
                        epmType = "2D";
                    } else {
                        epmType = "3D";
                    }

                    Timestamp createStamp = (Timestamp)oo[5];

                    HashMap ibaValues = null;

                    try {
                        creator0 = (WTUser)rf.getReference((String)oo[7]).getObject();

                        ibaValues = edmAttributes.getIBAValues((IBAHolder)rf.getReference(noid).getObject(), Locale.KOREAN);
                    }
                    catch(Exception e) {
                	    Kogger.error(e);
                        creator0= null;
                    }

                    String _dummy = "";
                    if(ibaValues.get(EDMHelper.IBA_DUMMY_FILE) != null) {
                        _dummy = (String)ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
                    }




                    /* 
                    if( !EcmUtil.existEcaEpmDocLink(epm) )
                    {
                    */    
            %>
                       rArr = new Array();
                       rArr[0] = '<%=noid%>';
                       rArr[1] = "<%=HtmlUtil.escapeFormattedHTMLContent(nr)%>";
                       rArr[2] = "<%=HtmlUtil.escapeFormattedHTMLContent(nm)%>";
                       rArr[3] = '<%=creator0.getFullName()%>';
                       rArr[4] = '<%=nv%>';
                       rArr[5] = '<%=DateUtil.getDateString(createStamp,"d")%>';
                       rArr[6] = '<%=epmType%>';
                       
                       // 상태
                       rArr[7] = '<%=epm.getLifeCycleState().getDisplay() %>';
            <%
                    ArrayList<wt.change2.WTChangeOrder2> relatedEcoList = EcmUtil.getECOexistEcaEpmDocLink(epm);
                    int relatedEcoListSize = (relatedEcoList != null) ? relatedEcoList.size() : 0;
                    
                    
                    
                    if(relatedEcoListSize > 0) {
            %>
                       rArr2 = new Array();
            <%          
                       for(int i=0; i < relatedEcoListSize; i++) {
                           String ecoId = "";
                           wt.change2.WTChangeOrder2 wtChangeOrder2 = relatedEcoList.get(i);
                           if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETProdChangeOrder) {
                               e3ps.ecm.entity.KETProdChangeOrder eco = (e3ps.ecm.entity.KETProdChangeOrder) wtChangeOrder2;
                               ecoId = eco.getEcoId();
                           } else if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETMoldChangeOrder) {
                               e3ps.ecm.entity.KETMoldChangeOrder eco = (e3ps.ecm.entity.KETMoldChangeOrder) wtChangeOrder2;
                               ecoId = eco.getEcoId();
                           }
            %>
                           rArr2[<%=i %>] = '<%=ecoId %>';
            <%          
                       }
            %>
                        // 연관 ECO
                        rArr[8] = rArr2;
            <%
                    }   

            %>
                       arr[idx++] = rArr;
            <%
                    /* 
                    }
                    */
                                           
                    
                    
                    
                if( prodMoldCls.equals("1") )
                {
                    ecadList = EDMChangeHelper.getAssociatedDocsByECAD(epm);
                    EPMDocument ecad = null;

                    for(int i = 0; i < ecadList.size(); i++)
                    {
                        ecad = (EPMDocument)ecadList.get(i);

                        if("PLMSYSTEM".equals(ecad.getOwnerApplication().toString()))
                        {
                            epmType = "2D";
                        }
                        else
                        {
                            epmType = "3D";
                        }
             %>
                     rArr = new Array();
                    rArr[0] = '<%=KETObjectUtil.getOidString(ecad)%>';
                    rArr[1] = "<%=HtmlUtil.escapeFormattedHTMLContent( ecad.getNumber() )%>";
                    rArr[2] = "<%=HtmlUtil.escapeFormattedHTMLContent( ecad.getName() )%>";
                    rArr[3] = '<%=ecad.getCreatorFullName()%>';
                    rArr[4] = '<%=VersionUtil.getMajorVersion( ecad )%>';
                    rArr[5] = '<%=DateUtil.getDateString(ecad.getCreateTimestamp(),"d")%>';
                    rArr[6] = '<%=epmType%>';
                    arr[idx++] = rArr;
             <%
                    }
                }
            }
            %>
            
            if(arr != null && arr.length > 0) {
                parent.addEpmAllList(arr);
            } else {
            	parent.addEpmNotFound();
            }
    <%} %>

</script>
