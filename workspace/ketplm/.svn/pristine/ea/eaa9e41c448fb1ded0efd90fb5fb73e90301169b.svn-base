<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="wt.fc.*,wt.org.*, wt.part.*, wt.session.*,wt.lifecycle.*,wt.vc.*,wt.query.*,wt.epm.EPMDocument"%>
<%@ page import="e3ps.common.query.*,
                 e3ps.common.code.*,
                 e3ps.common.util.*,
                 e3ps.dms.entity.*,
                 e3ps.ecm.entity.*,
                 e3ps.edm.beans.*,
                 e3ps.bom.service.*,
                 e3ps.bom.dao.*,
                 e3ps.groupware.company.PeopleData,
                 e3ps.bom.common.iba.IBAUtil"%>
<%@ page import="ext.ket.part.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();
    String poid = request.getParameter("oid");      // oid
    String type = "";
    String partNo = "";
    String partRev = "";
    
    
    WTPart wt = null;
    if( poid != null && !poid.equals("") ) {
        wt = (WTPart) KETObjectUtil.getObject(poid);
        if (wt != null) {
            type = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpPartType);
            partNo = wt.getNumber();
            partRev = wt.getVersionIdentifier().getValue();
        }
    }

    ArrayList linkQrEco = null;      // 관련ECO

    linkQrEco = PartDao.searchRelatedEcoEco(partNo, partRev);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style type="text/css">
<!--
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript">
// 설계변경 상세조회 팝업
    function viewEcoPop(ecoOid, ecoType){
        var url = "";
        if (ecoType != null && ecoType == "P") {// 제품
            url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + ecoOid;
        }else if(ecoType != null && ecoType == "M"){
        	url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&oid=" + ecoOid;
        }else if(ecoType != null && ecoType == "MR"){
        	url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + ecoOid;
        }else if(ecoType != null && ecoType == "PR"){
        	url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=popupview&oid=" + ecoOid;
        }
        

        openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250,center=yes");
    }
</script>
</head>
    <body>
    
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td width="40" class="tdgrayM">No</td>
				<td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td width="100" class="tdgrayM">EO NO</td>
				<td width="*" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
				<td width="60" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
				<td width="60" class="tdgrayM">ECO<br>적용시점</td>
				<td width="80" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
				<td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
				<td width="110" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01498") %><%--변경 사유--%></td>
				
			</tr>
		<%      if( (linkQrEco == null) || (linkQrEco.size() == 0)) { %>
            <tr>
                <td class="tdwhiteM0" colspan="9"><%=messageService.getString("e3ps.message.ket_message", "00917") %><%--관련 설계변경이 없습니다--%></td>
            </tr>
	              
		<%      } else { %>
		<%
					String ecoOid = "";
					String linkOid = "";
					String ecoType = "";
					String strEcoReason = "";
					String ecoApplyPoint = "";
					KETProdChangeOrder pEco = null;
					KETMoldChangeOrder mEco = null;
					KETProdChangeRequest pEcr = null;
					KETMoldChangeRequest mEcr = null;
					Object resultObj = null;
					
					int ecoCount = linkQrEco.size();
					for(int inx = 0; inx < linkQrEco.size(); inx++) {
					    Hashtable<String, String> hash = (Hashtable<String, String>)linkQrEco.get(inx);
					    linkOid = hash.get("ecoOid");
					    resultObj = KETObjectUtil.getObject(linkOid);
					
					    if( resultObj instanceof KETProdChangeOrder ) {
					        pEco = (KETProdChangeOrder)resultObj;
					        ecoOid = KETObjectUtil.getOidString(pEco);
					        ecoType = "P";
					    } else if ( resultObj instanceof KETMoldChangeOrder ) {
					        mEco = (KETMoldChangeOrder)resultObj;
					        ecoOid = KETObjectUtil.getOidString(mEco);
					        ecoType = "M";
					    } else if ( resultObj instanceof KETMoldChangeRequest ) {
						    mEcr = (KETMoldChangeRequest)resultObj;
                            ecoOid = KETObjectUtil.getOidString(mEcr);
                            ecoType = "MR";
                        } else if ( resultObj instanceof KETProdChangeRequest ) {
                            pEcr = (KETProdChangeRequest)resultObj;
                            ecoOid = KETObjectUtil.getOidString(pEcr);
                            ecoType = "PR";
                        }
		%>
			<tr>
				<td class="tdwhiteM"><%=inx+1%></td>
				
				        <%
				        String idString0 = "";
				        String idString1 = "";
		                
				        String idString = "";
                        if("P".equals(ecoType)){
                            idString = pEco.getEcoId();
                        }else if("M".equals(ecoType)){
                            idString = mEco.getEcoId();
                        }else if("MR".equals(ecoType)){
                            idString = mEcr.getEcrId();
                        }else if("PR".equals(ecoType)){
                            idString = pEcr.getEcrId();
                        }
                        
                        idString0 = idString.substring(0, 3);
                        idString1 = idString.substring(4);
                        %>
                <td class="tdwhiteM"><%=idString0 %></td>
                <td class="tdwhiteM"><a href="javascript:viewEcoPop('<%=ecoOid%>','<%=ecoType%>');"><%=idString1%></a></td>
				
				    <%
                    String ecoName = "";
                    if("P".equals(ecoType)){
                	   ecoName = pEco.getEcoName();
                    }else if("M".equals(ecoType)){
                	   ecoName = mEco.getEcoName();
                    }else if("MR".equals(ecoType)){
                	   ecoName = mEcr.getEcrName();
                    }else if("PR".equals(ecoType)){
                	   ecoName = pEcr.getEcrName();
                    }
                    %>
                <td class="tdwhiteL" title="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(ecoName) %>"><div class="ellipsis" style="width:400px;"><nobr><%=ecoName%></nobr></div></td>
				
						<%
	                    String changeTypeName = "";
	                    if("P".equals(ecoType)){
	                	  changeTypeName = PartDao.getChangeTypeName( pEco.getDevYn(), "DEVYN" );
	                    }else if("M".equals(ecoType)){
	                	  changeTypeName = PartDao.getChangeTypeName( mEco.getDevYn(), "DEVYN" );
	                    }else if("MR".equals(ecoType)){
	                	  changeTypeName = PartDao.getChangeTypeName( mEcr.getDevYn(), "DEVYN" );
	                    }else if("PR".equals(ecoType)){
	                	  changeTypeName = PartDao.getChangeTypeName( pEcr.getDevYn(), "DEVYN" );
	                    }
	                    %>
	            <td class="tdwhiteM">
	                    <%=changeTypeName%>
				</td>
				        <%
                        String creatorFullName = "";
                        if("P".equals(ecoType)){
                            creatorFullName = pEco.getCreatorFullName();
                            ecoApplyPoint = StringUtil.checkNull(pEco.getEcoApplyPoint());
                        }else if("M".equals(ecoType)){
                            creatorFullName = mEco.getCreatorFullName();
                        }else if("MR".equals(ecoType)){
                            creatorFullName = mEcr.getCreatorFullName();
                        }else if("PR".equals(ecoType)){
                            creatorFullName = pEcr.getCreatorFullName();
                        }
                        %>
                <td class="tdwhiteM0" >
                    <%=ecoApplyPoint%>
                    </td>
                <td class="tdwhiteM">
                        <%=creatorFullName%>
                </td>
				        <%
                        String dateString = "";
                        if("P".equals(ecoType)){
                            dateString = DateUtil.getDateString(pEco.getCreateTimestamp(),"d");
                        }else if("M".equals(ecoType)){
                            dateString = DateUtil.getDateString(mEco.getCreateTimestamp(),"d");
                        }else if("MR".equals(ecoType)){
                            dateString = DateUtil.getDateString(mEcr.getCreateTimestamp(),"d");
                        }else if("PR".equals(ecoType)){
                            dateString = DateUtil.getDateString(pEcr.getCreateTimestamp(),"d");
                        }
                        %>
                <td class="tdwhiteM">
                        <%=dateString%>
                </td>
		<%
						if("P".equals(ecoType)){
						    strEcoReason = PartDao.getChangeReasonName( pEco.getChangeReason(), "PRODECOREASON" );
						}else if("M".equals(ecoType)){
						    strEcoReason = PartDao.getChangeReasonName( mEco.getChangeReason(), "CHANGEREASON" );
						}else if("MR".equals(ecoType)){
						    strEcoReason = "";
						}else if("PR".equals(ecoType)){
						    strEcoReason = PartDao.getChangeReasonName( pEcr.getChangeReason(), "CHANGEREASON" );
	                    }
		%>
				<td class="tdwhiteM0" title="<%=strEcoReason%>">
					<%=strEcoReason%>
	                </td>
	              </tr>
		<%
			    }
			}
		%>
		</table>
    
    </body>
</html>