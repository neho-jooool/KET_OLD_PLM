<?xml version="1.0" encoding="UTF-8" ?>
<%
    response.setContentType("text/xml");
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.common.iba.IBAUtil"%>
<%@page
	import="ext.ket.part.util.PartSpecGetter,
          ext.ket.part.util.PartSpecEnum,
          e3ps.common.util.StringUtil"%>
<%@page
	import="wt.content.*,wt.fc.ObjectReference,wt.fc.PersistenceHelper,wt.fc.ReferenceFactory,wt.part.WTPart,wt.epm.EPMDocument"%>
<%@ page
	import="wt.vc.Versioned,wt.vc.Iterated,wt.vc.VersionControlHelper,wt.vc.config.LatestConfigSpec,wt.vc.wip.Workable,wt.clients.vc.CheckInOutTaskLogic,wt.epm.EPMDocumentMaster"%>
<%@page import="com.ptc.windchill.cadx.common.EPMDocumentUtilities"%>
<%@page import="e3ps.edm.*,e3ps.edm.beans.*,e3ps.edm.util.*"%>
<%@page import="e3ps.common.util.StringUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean"
	scope="session" />
<jsp:setProperty name="wtcontext" property="request"
	value="<%=request%>" />
<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />
<stdinfo> <results> <contents> <%
     String message = "false";
     String command = request.getParameter("command");
     if (command == null) {
 		command = "";
     }

     if ("searchCadAppType".equals(command)) {
 		String category = request.getParameter("category");
 		category = java.net.URLDecoder.decode(category == null ? "" : category, "euc-kr");

 		message = "true";

 		CADAppType cadAppTypeArr[] = null;
 		try {
 		    EDMProperties edmProperties = EDMProperties.getInstance();
 		    cadAppTypeArr = edmProperties.getCADAppTypeSet(category);
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <%
     for (int i = 0; i < cadAppTypeArr.length; i++) {
 		    CADAppType cadAppType = cadAppTypeArr[i];
 %> <l_display><![CDATA[<%=cadAppType.getDisplay(messageService.getLocale())%>]]></l_display>
<l_value><![CDATA[<%=cadAppType.toString()%>]]></l_value> <%
     }
 %> </data_info> <%
     } else if ("searchCategory".equals(command)) {
 		String devStage = request.getParameter("devStage");
 		String manageType = request.getParameter("manageType");
 		String isBatch = request.getParameter("isBatch");

 		devStage = java.net.URLDecoder.decode(devStage == null ? "" : devStage, "euc-kr");
 		manageType = java.net.URLDecoder.decode(manageType == null ? "" : manageType, "euc-kr");
 		isBatch = java.net.URLDecoder.decode(isBatch == null ? "false" : isBatch, "euc-kr");

 		message = "true";

 		ArrayList catArr = null;
 		try {
 		    EDMProperties edmProperties = EDMProperties.getInstance();

 		    if (Boolean.parseBoolean(isBatch)) {
 			catArr = edmProperties.getCADCatsSet(manageType, devStage, true);
 		    } else {
 			catArr = edmProperties.getCADCatsSet(manageType, devStage);
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <%
     if ((catArr != null) && (catArr.size() > 0)) {
 		    for (int i = 0; i < catArr.size(); i++) {
 			CADCategory cat = (CADCategory) catArr.get(i);
 %> <l_display><![CDATA[<%=cat.getDisplay(messageService.getLocale())%>]]></l_display>
<l_value><![CDATA[<%=cat.toString()%>]]></l_value> <%
     }
 		}
 %> </data_info> <%
     } else if ("CheckPartReference".equals(command)) {

 		String oid = StringUtil.trimToEmpty(request.getParameter("oid"));
 		String poid = StringUtil.trimToEmpty(request.getParameter("poid"));
 		String category = StringUtil.trimToEmpty(request.getParameter("category"));
 		String cadAppType = StringUtil.trimToEmpty(request.getParameter("cadAppType"));
 		String required = StringUtil.trimToEmpty(request.getParameter("required"));
 		String checkNumberParseRule = StringUtil.trimToEmpty(request.getParameter("checkNumberParseRule"));

 		Kogger.debug("EDMAjaxAction", null, poid, "##########   poid == " + poid);

 		message = "true";

 		String isValid = String.valueOf(true);
 		String isValidNumberParseRule = String.valueOf(true);

 		EDMProperties edmProperties = EDMProperties.getInstance();

 		WTPart part = null;
 		EPMDocument epm = null;

 		try {
 		    ReferenceFactory rf = new ReferenceFactory();

 		    part = (WTPart) rf.getReference(poid).getObject();

 		    Kogger.debug("EDMAjaxAction", null, null, "###########################  part :  " + part);

 		    if (oid.trim().length() > 0) {
 			epm = (EPMDocument) rf.getReference(oid).getObject();
 		    }
 		} catch (Exception e) {
 		    message = String.valueOf(false);
 		    Kogger.error("EDMAjaxAction", null, null, " Exception    message :  " + message, e);
 		}

 		if (Boolean.parseBoolean(checkNumberParseRule)) {
 		    try {
 			if (edmProperties.getConvertedNumber(part.getNumber(), category, cadAppType) == null) {
 			    isValid = String.valueOf(false);
 			    isValidNumberParseRule = String.valueOf(false);
 			}
 		    } catch (Exception e) {
 			Kogger.error(e);
 			message = String.valueOf(false);
 		    }
 		}

 		String referenceType = edmProperties.getReferenceType(category);
 		boolean goFlag = true;
 		try {
 		    if (!edmProperties.isCADCatsByEcad(category)) {//if(!"ECAD_DRAWING".equals(category)) {
 			boolean isExist = false;
 			if (referenceType.equals("CU_DRAWING")) {
 			    isExist = EDMHelper.isReferenceDocExist(part, epm, referenceType, -1);
 			    if (isExist) {
 				goFlag = false;
 				isValid = String.valueOf(false);
 			    }
 			} else {
 			    isExist = EDMHelper.isReferenceDocExist(part, referenceType, -1);
 			}
 			if (goFlag) {
 			    if (!isExist) {
 				isValid = String.valueOf(true);
 			    } else {
 				if ((epm == null)) {
 				    if (edmProperties.isOnlyRefedByCat(category)) {
 					isValid = String.valueOf(false);
 				    } else {
 					isValid = String.valueOf(true);
 				    }
 				} else {
 				    isValid = String.valueOf(EDMHelper.isReferenceDocExist(part, epm, referenceType, -1));
 				}
 			    }
 			}
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_valid><![CDATA[<%=isValid%>]]></l_valid>
<l_validParseRule><![CDATA[<%=isValidNumberParseRule%>]]></l_validParseRule>
<%
    if (part != null) {
%> <l_oid><![CDATA[<%=PersistenceHelper.getObjectIdentifier(part).getStringValue()%>]]></l_oid>
<l_number><![CDATA[<%=part.getNumber()%>]]></l_number> <l_name><![CDATA[<%=part.getName()%>]]></l_name>
<l_version><![CDATA[<%=StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision))%>]]></l_version>
<%
    }
%> </data_info> <%
     } else if ("CheckModelReference".equals(command)) {

 		String number = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("number")), "utf-8");
 		String oid = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("oid")), "utf-8");
 		String poid = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("poid")), "utf-8");
 		String modelOid = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("modelOid")), "utf-8");
 		String category = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("category")), "utf-8");
 		String cadAppType = java.net.URLDecoder.decode(StringUtil.trimToEmpty(request.getParameter("cadAppType")), "utf-8");

 		message = "true";

 		String isValid = String.valueOf(true);
 		String isModelReferencedBy = String.valueOf(false);
 		String fileName = "";
 		String fileSize = "";
 		String appDataOid = "";

 		boolean isNamingRule = true;

 		WTPart part = null;
 		EPMDocument epm = null;
 		EPMDocument model = null;

 		EDMProperties edmProperties = EDMProperties.getInstance();
 		String referenceTypeForModel = edmProperties.getReferenceTypeForModel(null);

 		try {
 		    ReferenceFactory rf = new ReferenceFactory();

 		    if (modelOid.trim().length() > 0) {
 			model = (EPMDocument) rf.getReference(modelOid).getObject();
 		    }
 		    if (oid.trim().length() > 0) {
 			epm = (EPMDocument) rf.getReference(oid).getObject();
 		    }
 		    if (poid.trim().length() > 0) {
 			part = (WTPart) rf.getReference(poid).getObject();
 		    }

 		    //모델 Naming Rule 체크
 		    /* =================================
 		     * 제품도면
 		     * 도면번호 기준 : 개발검토도면
 		     * 부품번호 기준 : 제품도면
 		     * =================================
 		     * 금형도면
 		     * 도면번호 : '부품번호' + '_PRT'
 		     * 예외) 사출금형SET도면 : '부품번호' + '???' +'_PRT'
 		     * =================================
 		     */

 		    CADManageType cmt = edmProperties.getManageType(category);
 		    String manageType = (cmt == null) ? "" : cmt.toString();
 		    if (isNamingRule) {
 			if ("PRODUCT_DRAWING".equals(manageType)) {
 			    if ("DEV_REVIEW_DRAWING".equals(category)) {
 				isNamingRule = (number + "_3D").equals(model.getNumber());
 			    } else {
 				String cnvNumber = StringUtil.trimToEmpty(edmProperties.convertPrefixNumber(part.getNumber(), category));
 				isNamingRule = (cnvNumber + "_3D").equals(model.getNumber());
 			    }
 			} else if ("MOLD_DRAWING".equals(manageType)) {
 			    String moldPartNumber = part.getNumber().toLowerCase();
 			    String moldModelNumber = model.getNumber().toLowerCase();

 			    if ("INJECTION_MOLD_SET_DRAWING".equals(category)) {
 				boolean bool0 = moldModelNumber.startsWith(moldPartNumber);
 				if (bool0) {
 				    isNamingRule = moldModelNumber.endsWith("_prt");
 				} else {
 				    isNamingRule = false;
 				}

 			    } else {
 				isNamingRule = (moldPartNumber + "_prt").equals(moldModelNumber);
 			    }
 			}
 		    }

 		    // 2011.01.07
 		    //*******************************************************************************************************************
 		    boolean isExist = EDMHelper.isReferenceEPMExist(model, -1);
 		    if (!isExist) {
 			isValid = String.valueOf(true);
 		    } else {
 			if (epm == null) {
 			    isValid = String.valueOf(false);
 			} else {
 			    isValid = String.valueOf(EDMHelper.isReferenceEPMExist(model, epm, -1));
 			    isModelReferencedBy = isValid;
 			}
 		    }
 		    /*
 		    if(!"DEV_REVIEW_DRAWING".equals(category)) {
 		      boolean isExist = EDMHelper.isReferencedByPartExist(model, referenceTypeForModel, -1);
 		      if(!isExist) {
 		        isValid = String.valueOf(true);
 		      } else {
 		        if(part == null) {
 		          isValid = String.valueOf(false);
 		        } else {
 		          isValid = String.valueOf(EDMHelper.isReferencedByPartExist(model, part, referenceTypeForModel, -1));
 		          isModelReferencedBy = isValid;
 		        }
 		      }
 		    } else {
 		      boolean isExist = EDMHelper.isReferenceEPMExist(model, EDMHelper.REQUIRED_REFERENCE_MODEL);
 		      if(!isExist) {
 		        isValid = String.valueOf(true);
 		      } else {
 		        if(epm == null) {
 		          isValid = String.valueOf(false);
 		        } else {
 		          isValid = String.valueOf(EDMHelper.isReferenceEPMExist(model, epm, EDMHelper.REQUIRED_REFERENCE_MODEL));
 		          isModelReferencedBy = isValid;
 		        }
 		      }
 		    }
 		     */
 		    //*******************************************************************************************************************

 		    if (model != null) {
 			ContentHolder holder = (ContentHolder) ContentHelper.service.getContents(model);
 			ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
 			if ((item != null) && (item instanceof ApplicationData)) {
 			    ApplicationData appData = (ApplicationData) item;

 			    fileName = wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument) holder, item);
 			    fileSize = appData.getFileSizeKB() + " KB";
 			    appDataOid = PersistenceHelper.getObjectIdentifier(appData).getStringValue();
 			}
 		    }

 		    if ("true".equals(isValid)) {
 			isValid = String.valueOf(isNamingRule);
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_valid><![CDATA[<%=isValid%>]]></l_valid>
<l_isNamingRule><![CDATA[<%=String.valueOf(isNamingRule)%>]]></l_isNamingRule>
<l_ReferencedBy><![CDATA[<%=isModelReferencedBy%>]]></l_ReferencedBy> <%
     if (model != null) {
 %> <l_oid><![CDATA[<%=PersistenceHelper.getObjectIdentifier(model).getStringValue()%>]]></l_oid>
<l_number><![CDATA[<%=model.getNumber()%>]]></l_number> <l_name><![CDATA[<%=model.getName()%>]]></l_name>
<l_version><![CDATA[<%=StringUtil.checkNull(IBAUtil.getAttrValue(model, EDMHelper.IBA_MANUFACTURING_VERSION))%>]]></l_version>
<l_fileName><![CDATA[<%=fileName%>]]></l_fileName> <l_fileSize><![CDATA[<%=fileSize%>]]></l_fileSize>
<l_appDataOid><![CDATA[<%=appDataOid%>]]></l_appDataOid> <%
     }
 %> </data_info> <%
     } else if ("inqDataForEPM".equals(command)) {
 		String oid = request.getParameter("oid");
 		String category = request.getParameter("category");

 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "euc-kr");
 		category = java.net.URLDecoder.decode(category == null ? "" : category, "euc-kr");

 		String fileName = "";
 		String fileSize = "";
 		String appDataOid = "";

 		String manageType = "";
 		//String category = "";
 		String devStage = "";
 		String cadAppType = "";
 		String manufacturingVersion = "";
 		String isDummyFile = "";

 		message = "true";
 		String isValid = String.valueOf(true);

 		ReferenceFactory rf = new ReferenceFactory();

 		EPMDocument epm = null;
 		WTPart part = null;

 		try {
 		    if (oid.trim().length() > 0) {
 			epm = (EPMDocument) rf.getReference(oid).getObject();
 		    }

 		    HashMap ibaValues = null;
 		    if (epm != null) {
 			ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);
 		    }

 		    if (ibaValues != null) {
 			if (ibaValues.containsKey(EDMHelper.IBA_CAD_MANAGE_TYPE)) {
 			    manageType = EDMEnumeratedTypeUtil.getCADManageType((String) ibaValues.get(EDMHelper.IBA_CAD_MANAGE_TYPE),
 				    Locale.KOREAN);
 			}
 			if (ibaValues.containsKey(EDMHelper.IBA_CAD_CATEGORY)) {
 			    category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),
 				    Locale.KOREAN);
 			}

 			if (ibaValues.containsKey(EDMHelper.IBA_DEV_STAGE)) {
 			    devStage = EDMEnumeratedTypeUtil.getDevStage((String) ibaValues.get(EDMHelper.IBA_DEV_STAGE), Locale.KOREAN);
 			}
 			if (ibaValues.containsKey(EDMHelper.IBA_CAD_APP_TYPE)) {
 			    cadAppType = EDMEnumeratedTypeUtil.getCADAppType((String) ibaValues.get(EDMHelper.IBA_CAD_APP_TYPE),
 				    Locale.KOREAN);
 			}
 			if (ibaValues.containsKey(EDMHelper.IBA_MANUFACTURING_VERSION)) {
 			    manufacturingVersion = (String) ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION);
 			}
 			if (ibaValues.containsKey(EDMHelper.IBA_DUMMY_FILE)) {
 			    isDummyFile = (String) ibaValues.get(EDMHelper.IBA_DUMMY_FILE);
 			}
 		    }

 		    if (epm != null) {
 			ContentHolder holder = (ContentHolder) ContentHelper.service.getContents(epm);
 			ContentItem item = ContentHelper.service.getPrimaryContent(ObjectReference.newObjectReference(holder));
 			if ((item != null) && (item instanceof ApplicationData)) {
 			    ApplicationData appData = (ApplicationData) item;

 			    fileName = wt.epm.util.EPMContentHelper.getContentDisplayName((wt.epm.EPMDocument) holder, item);
 			    fileSize = appData.getFileSizeKB() + " KB";
 			    appDataOid = PersistenceHelper.getObjectIdentifier(appData).getStringValue();
 			}
 		    }

 		    EDMProperties edmProperties = EDMProperties.getInstance();
 		    String referenceType = edmProperties.getReferenceType(category);

 		    ArrayList refedByParts = EDMHelper.getReferencedByParts(epm, referenceType, -1);
 		    if ((refedByParts != null) && (refedByParts.size() > 0)) {
 			Object[] oo = (Object[]) refedByParts.get(0);
 			part = (WTPart) oo[1];
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_valid><![CDATA[<%=isValid%>]]></l_valid>
<%
    if (epm != null) {
%> <l_oid><![CDATA[<%=PersistenceHelper.getObjectIdentifier(epm).getStringValue()%>]]></l_oid>
<l_number><![CDATA[<%=epm.getNumber()%>]]></l_number> <l_name><![CDATA[<%=epm.getName()%>]]></l_name>
<l_version><![CDATA[<%=StringUtil.checkNull(IBAUtil.getAttrValue(epm, EDMHelper.IBA_MANUFACTURING_VERSION))%>]]></l_version>
<l_fileName><![CDATA[<%=fileName%>]]></l_fileName> <l_fileSize><![CDATA[<%=fileSize%>]]></l_fileSize>
<l_appDataOid><![CDATA[<%=appDataOid%>]]></l_appDataOid> <l_manageType><![CDATA[<%=manageType%>]]></l_manageType>
<l_category><![CDATA[<%=category%>]]></l_category> <l_devStage><![CDATA[<%=devStage%>]]></l_devStage>
<l_cadAppType><![CDATA[<%=cadAppType%>]]></l_cadAppType> <l_manufacturingVersion><![CDATA[<%=manufacturingVersion%>]]></l_manufacturingVersion>
<l_isDummyFile><![CDATA[<%=isDummyFile%>]]></l_isDummyFile> <%
     }
 %> <%
     if (part != null) {
 %> <l_poid><![CDATA[<%=PersistenceHelper.getObjectIdentifier(part).getStringValue()%>]]></l_poid>
<l_pumber><![CDATA[<%=part.getNumber()%>]]></l_pumber> <l_pname><![CDATA[<%=part.getName()%>]]></l_pname>
<l_pversion><![CDATA[<%=StringUtil.checkNull(PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartRevision))%>]]></l_pversion>
<%
    }
%> </data_info> <%
     } else if ("SearchRelatedModel".equals(command)) {
 		String oid = request.getParameter("oid");
 		String epmoid = request.getParameter("epmoid");
 		String category = request.getParameter("category");

 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "euc-kr");
 		epmoid = java.net.URLDecoder.decode(epmoid == null ? "" : epmoid, "euc-kr");
 		category = java.net.URLDecoder.decode(category == null ? "" : category, "euc-kr");

 		message = "true";

 		ArrayList relateds = new ArrayList();
 		try {
 		    ReferenceFactory rf = new ReferenceFactory();

 		    WTPart part = (WTPart) rf.getReference(oid).getObject();

 		    EDMProperties edmProperties = EDMProperties.getInstance();

 		    /*
 		     * 사출금형SET도면인 경우 - 부품:모델 -- 1:N 임.
 		     *
 		     */
 		    if ("INJECTION_MOLD_SET_DRAWING".equals(category)) {

 			String referenceType = edmProperties.getReferenceType(category);

 			EPMDocument orgepm = null;
 			EPMDocument model = null;
 			if (epmoid.length() > 0) {
 			    orgepm = (EPMDocument) rf.getReference(epmoid).getObject();
 			}

 			if (orgepm != null) {
 			    Object[] mdObjs = EDMHelper.getAssociatedModelLinkObjs(orgepm, part, referenceType, -1);
 			    if (mdObjs != null) {
 				//[0]:EPMDocument - 도면
 				//[1]:EDMLink - 도면-부품 링크
 				//[2]:WTPart - 부품
 				//[3]:EDMLink - 부품-모델 링크
 				//[4]:EPMDocument - 모델
 				//[5]:ModelReferenceLink - 모델-도면 링크
 				model = (EPMDocument) mdObjs[4];
 				relateds.add(model);
 			    }
 			}
 		    } else {
 			relateds = EDMHelper.getReferenceDocs(part, edmProperties.getReferenceTypeForModel(null), -1);
 		    }

 		    if (relateds == null) {
 			relateds = new ArrayList();
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}

 		if (relateds.size() > 0) {
 %> <data_info> <%
     for (int i = 0; i < relateds.size(); i++) {
 			EPMDocument epm = (EPMDocument) ((Object[]) relateds.get(i))[1];
 %> <l_oid><![CDATA[<%=PersistenceHelper.getObjectIdentifier(epm).getStringValue()%>]]></l_oid>
<l_number><![CDATA[<%=epm.getNumber()%>]]></l_number> <l_name><![CDATA[<%=epm.getName()%>]]></l_name>
<l_version><![CDATA[<%=StringUtil.checkNull(IBAUtil.getAttrValue(epm, EDMHelper.IBA_MANUFACTURING_VERSION))%>]]></l_version>

<%
    }
%> </data_info> <%
     }
     } else if ("validateSave".equals(command)) {
 		String oid = request.getParameter("oid");
 		String number = request.getParameter("number");
 		String name = request.getParameter("name");
 		String manageType = request.getParameter("manageType");
 		String category = request.getParameter("category");
 		String cadAppType = request.getParameter("cadAppType");
 		String primary = request.getParameter("primary");
 		String file_pcb_primary = request.getParameter("file_pcb_primary");
 		String file_gerber_primary = request.getParameter("file_gerber_primary");
 		String file_sch_primary = request.getParameter("file_sch_primary");
 		String file_dwg_primary = request.getParameter("file_dwg_primary");
 		String number_pcb = request.getParameter("number_pcb");
 		String number_sch = request.getParameter("number_sch");
 		String number_dwg = request.getParameter("number_dwg");
 		String oid_pcb = request.getParameter("oid_pcb");
 		String oid_sch = request.getParameter("oid_sch");
 		String oid_dwg = request.getParameter("oid_dwg");

 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "utf-8");
 		number = java.net.URLDecoder.decode(number == null ? "" : number, "utf-8");
 		name = java.net.URLDecoder.decode(name == null ? "" : name, "utf-8");
 		manageType = java.net.URLDecoder.decode(manageType == null ? "" : manageType, "utf-8");
 		category = java.net.URLDecoder.decode(category == null ? "" : category, "utf-8");
 		cadAppType = java.net.URLDecoder.decode(cadAppType == null ? "" : cadAppType, "utf-8");
 		primary = java.net.URLDecoder.decode(primary == null ? "" : primary, "utf-8");
 		file_pcb_primary = java.net.URLDecoder.decode(file_pcb_primary == null ? "" : file_pcb_primary, "utf-8");
 		file_gerber_primary = java.net.URLDecoder.decode(file_gerber_primary == null ? "" : file_gerber_primary, "utf-8");
 		file_sch_primary = java.net.URLDecoder.decode(file_sch_primary == null ? "" : file_sch_primary, "utf-8");
 		file_dwg_primary = java.net.URLDecoder.decode(file_dwg_primary == null ? "" : file_dwg_primary, "utf-8");
 		number_pcb = java.net.URLDecoder.decode(number_pcb == null ? "" : number_pcb, "utf-8");
 		number_sch = java.net.URLDecoder.decode(number_sch == null ? "" : number_sch, "utf-8");
 		number_dwg = java.net.URLDecoder.decode(number_dwg == null ? "" : number_dwg, "utf-8");
 		oid_pcb = java.net.URLDecoder.decode(oid_pcb == null ? "" : oid_pcb, "utf-8");
 		oid_sch = java.net.URLDecoder.decode(oid_sch == null ? "" : oid_sch, "utf-8");
 		oid_dwg = java.net.URLDecoder.decode(oid_dwg == null ? "" : oid_dwg, "utf-8");

 		if (primary.lastIndexOf("\\") > 0) {
 		    primary = primary.substring(primary.lastIndexOf("\\") + 1);
 		}

 		if (file_pcb_primary.lastIndexOf("\\") > 0) {
 		    file_pcb_primary = file_pcb_primary.substring(file_pcb_primary.lastIndexOf("\\") + 1);
 		}

 		if (file_gerber_primary.lastIndexOf("\\") > 0) {
 		    file_gerber_primary = file_gerber_primary.substring(file_gerber_primary.lastIndexOf("\\") + 1);
 		}

 		if (file_sch_primary.lastIndexOf("\\") > 0) {
 		    file_sch_primary = file_sch_primary.substring(file_sch_primary.lastIndexOf("\\") + 1);
 		}

 		if (file_dwg_primary.lastIndexOf("\\") > 0) {
 		    file_dwg_primary = file_dwg_primary.substring(file_dwg_primary.lastIndexOf("\\") + 1);
 		}

 		primary = primary.toLowerCase();
 		file_pcb_primary = file_pcb_primary.toLowerCase();
 		file_gerber_primary = file_gerber_primary.toLowerCase();
 		file_sch_primary = file_sch_primary.toLowerCase();
 		file_dwg_primary = file_dwg_primary.toLowerCase();

 		message = "true";

 		String checkMsg = "";

 		boolean isValid = true;

 		boolean isNumber = true;
 		boolean isNamingRule = true;
 		boolean isFileExt = true;
 		boolean isFileName = true;

 		boolean iswgm = false;
 		try {
 		    ReferenceFactory rf = new ReferenceFactory();

 		    EDMProperties edmProperties = EDMProperties.getInstance();

 		    if (!iswgm) {
 			if (edmProperties.isCADCatsByEcad(category)) {

 			    EPMDocument epm_pcb = null;
 			    EPMDocument epm_sch = null;
 			    EPMDocument epm_dwg = null;

 			    if (oid_pcb.length() > 0) {
 				epm_pcb = (EPMDocument) rf.getReference(oid_pcb).getObject();
 			    }

 			    if (oid_sch.length() > 0) {
 				epm_sch = (EPMDocument) rf.getReference(oid_sch).getObject();
 			    }

 			    if (oid_dwg.length() > 0) {
 				epm_dwg = (EPMDocument) rf.getReference(oid_dwg).getObject();
 			    }

 			    //PCB 도면번호 체크
 			    boolean isCheckNumber = true;
 			    if (epm_pcb != null) {
 				String oldNumber = epm_pcb.getNumber();
 				isCheckNumber = !oldNumber.equals(number_pcb);
 			    }
 			    if (isCheckNumber && (number_pcb.length() > 0) && EPMDocumentUtilities.doesEPMDocumentExist(number_pcb)) {
 				isValid = false;
 				isNumber = false;
 			    }
 			    //SCH 도면번호 체크
 			    isCheckNumber = true;
 			    if (epm_sch != null) {
 				String oldNumber = epm_sch.getNumber();
 				isCheckNumber = !oldNumber.equals(number_sch);
 			    }
 			    if (isCheckNumber && (number_sch.length() > 0) && EPMDocumentUtilities.doesEPMDocumentExist(number_sch)) {
 				isValid = false;
 				isNumber = false;
 			    }
 			    //DWG 도면번호 체크
 			    isCheckNumber = true;
 			    if (epm_dwg != null) {
 				String oldNumber = epm_dwg.getNumber();
 				isCheckNumber = !oldNumber.equals(number_dwg);
 			    }
 			    if (isCheckNumber && (number_dwg.length() > 0) && EPMDocumentUtilities.doesEPMDocumentExist(number_dwg)) {
 				isValid = false;
 				isNumber = false;
 			    }

 			    String fext = "";
 			    String fname = "";
 			    String fnumber = "";
 			    int pidx = 0;
 			    //PCB 파일명/확장자 체크
 			    if ((number_pcb.length() > 0) && (file_pcb_primary.length() > 0)) {

 				fnumber = number_pcb.substring(0, number_pcb.lastIndexOf("_PCB"));
 				fname = "";
 				fext = "";
 				pidx = file_pcb_primary.lastIndexOf(".");
 				if (pidx > 0) {
 				    fname = file_pcb_primary.substring(0, pidx);
 				    fext = file_pcb_primary.substring(pidx + 1);
 				} else {
 				    fname = file_pcb_primary;
 				}
 				isFileName = fname.startsWith(fnumber.toLowerCase());//(name+"_"+number).equals( fname );
 				isFileExt = edmProperties.isFileExt(edmProperties.getAuthoringAppTypeByEcadPcb(), fext);//PADS
 			    }

 			    if ((number_pcb.length() > 0) && (file_gerber_primary.length() > 0)) {

 				fnumber = number_pcb.substring(0, number_pcb.lastIndexOf("_PCB"));
 				fname = "";
 				fext = "";
 				pidx = file_gerber_primary.lastIndexOf(".");
 				if (pidx > 0) {
 				    fname = file_gerber_primary.substring(0, pidx);
 				    fext = file_gerber_primary.substring(pidx + 1);
 				} else {
 				    fname = file_gerber_primary;
 				}
 				if (isFileName) {
 				    isFileName = fname.startsWith(fnumber.toLowerCase());//(name+"_"+number).equals( fname );
 				}

 				if (isFileExt) {
 				    isFileExt = "zip".equals(fext.toLowerCase());
 				}
 			    }

 			    //SCH 파일명/확장자 체크
 			    if ((number_sch.length() > 0) && (file_sch_primary.length() > 0)) {
 				fnumber = number_sch.substring(0, number_sch.lastIndexOf("_SCH"));
 				fname = "";
 				fext = "";
 				pidx = file_sch_primary.lastIndexOf(".");
 				if (pidx > 0) {
 				    fname = file_sch_primary.substring(0, pidx);
 				    fext = file_sch_primary.substring(pidx + 1);
 				} else {
 				    fname = file_sch_primary;
 				}

 				if (isFileName) {
 				    isFileName = fname.startsWith(fnumber.toLowerCase());//(name+"_"+number).equals( fname );
 				}

 				if (isFileExt) {
 				    isFileExt = edmProperties.isFileExt(edmProperties.getAuthoringAppTypeByEcadSch(), fext);//PADS_SCH
 				}
 			    }

 			    //DWG 파일명/확장자 체크
 			    if ((number_dwg.length() > 0) && (file_dwg_primary.length() > 0)) {
 				fnumber = number_dwg.substring(0, number_dwg.lastIndexOf("_DWG"));
 				fname = "";
 				fext = "";
 				pidx = file_dwg_primary.lastIndexOf(".");
 				if (pidx > 0) {
 				    fname = file_dwg_primary.substring(0, pidx);
 				    fext = file_dwg_primary.substring(pidx + 1);
 				} else {
 				    fname = file_dwg_primary;
 				}

 				if (isFileName) {
 				    isFileName = fname.startsWith(fnumber.toLowerCase());//(name+"_"+number).equals( fname );
 				}

 				if (isFileExt) {
 				    isFileExt = edmProperties.isFileExt(edmProperties.getAuthoringAppTypeByEcadAcad(), fext);//ACAD
 				}
 			    }
 			} else {
 			    EPMDocument epm = null;
 			    if (oid.length() > 0) {
 				epm = (EPMDocument) rf.getReference(oid).getObject();
 			    }

 			    if ((epm != null) && !edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
 				iswgm = true;
 			    }

 			    if (!iswgm && "MOLD_DRAWING".equals(manageType)) {
 				//금형 부품인 경우에 도면 번호의 Suffix check
 				//_PRT/_DWG/_PLS
 				String suffix = edmProperties.getDefedNumberSuffix(cadAppType);
 				if (suffix.length() > 0) {
 				    isNamingRule = (number.toLowerCase()).endsWith("_" + suffix.toLowerCase());
 				}
 			    }

 			    boolean isCheckNumber = true;
 			    if (epm != null) {
 				String oldNumber = epm.getNumber();
 				isCheckNumber = !oldNumber.equals(number);
 			    }

 			    if (isCheckNumber && EPMDocumentUtilities.doesEPMDocumentExist(number)) {
 				isValid = false;
 				isNumber = false;
 				checkMsg = messageService.getString("e3ps.message.ket_message", "02285")/*이미 동록된 도면번호 입니다*/;
 			    }

 			    if (primary.length() > 0) {
 				String fext = "";
 				String fname = "";
 				int pidx = primary.lastIndexOf(".");
 				if (pidx > 0) {
 				    fname = primary.substring(0, pidx);
 				    fext = primary.substring(pidx + 1);
 				} else {
 				    fname = primary;
 				}

 				/*
 				 * 금형 번호 : 100000_DWG
 				 * 파일 명 : 100000.dwg
 				 */

 				if ("MOLD_DRAWING".equals(manageType)) {
 				    isFileName = (number.toLowerCase()).startsWith(fname);
 				} else {
 				    isFileName = fname.startsWith(number.toLowerCase());//(name+"_"+number).equals( fname );
 				}
 				isFileExt = edmProperties.isFileExt(cadAppType, fext);
 			    }
 			}
 		    }

 		    if (isValid && (!isNumber || !isFileExt || !isFileName || !isNamingRule)) {
 			isValid = false;
 		    }
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_checkmsg><![CDATA[<%=checkMsg%>]]></l_checkmsg>
<l_valid><![CDATA[<%=isValid%>]]></l_valid> <l_Number><![CDATA[<%=isNumber%>]]></l_Number>
<l_NamingRule><![CDATA[<%=isNamingRule%>]]></l_NamingRule> <l_FileExt><![CDATA[<%=String.valueOf(isFileExt)%>]]></l_FileExt>
<l_FileName><![CDATA[<%=isFileName%>]]></l_FileName> </data_info> <%
     } else if ("ChangeState".equals(command)) {
 		String oid = request.getParameter("oid");
 		String state = request.getParameter("state");
 		String terminate = request.getParameter("terminate");

 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "euc-kr");
 		state = java.net.URLDecoder.decode(state == null ? "" : state, "euc-kr");
 		terminate = java.net.URLDecoder.decode(terminate == null ? "" : terminate, "euc-kr");

 		message = "true";

 		try {
 		    wt.lifecycle.LifeCycleManaged lcm = null;
 		    if (oid.length() > 0) {
 			ReferenceFactory rf = new ReferenceFactory();
 			lcm = (wt.lifecycle.LifeCycleManaged) rf.getReference(oid).getObject();
 		    }

 		    if ((lcm != null) && (state.trim().length() > 0)) {
 			if (terminate.trim().length() == 0) {
 			    terminate = "false";
 			}

 			lcm = wt.lifecycle.LifeCycleHelper.service.setLifeCycleState(lcm, wt.lifecycle.State.toState(state),
 			        Boolean.parseBoolean(terminate));
 		    }

 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_state><![CDATA[<%=state%>]]></l_state> <l_oid><![CDATA[<%=oid%>]]></l_oid>
</data_info> <%
     } else if ("numbering".equals(command)) {
 		String pnumber = request.getParameter("number");
 		String pname = request.getParameter("name");
 		String category = request.getParameter("category");
 		String cadAppType = request.getParameter("cadAppType");

 		/*
 		  try {
 		
 		  }
 		    Kogger.debug("pre decode name : " + name);
 		    Kogger.debug("after decode euc-kr : " + wt.httpgw.WTURLEncoder.decode(name==null?"":name,"euc-kr"));
 		    Kogger.debug("after decode utf-8 : " + wt.httpgw.WTURLEncoder.decode(name==null?"":name,"utf-8"));
 		    Kogger.debug("after decode euc-kr java decoder : " + java.net.URLDecoder.decode(name==null?"":name,"euc-kr"));
 		    Kogger.debug("after decode utf-8 java decoder : " + java.net.URLDecoder.decode(name==null?"":name,"utf-8"));
 		  }
 		  catch(Exception e) {
 		    e.printStackTrace();
 		  }
 		 */
 		pnumber = java.net.URLDecoder.decode(pnumber == null ? "" : pnumber, "utf-8");
 		pname = java.net.URLDecoder.decode(pname == null ? "" : pname, "utf-8");
 		category = java.net.URLDecoder.decode(category == null ? "" : category, "utf-8");
 		cadAppType = java.net.URLDecoder.decode(cadAppType == null ? "" : cadAppType, "utf-8");

 		String convertedNumber = pnumber;
 		String convertedName = pname;

 		boolean isValid = true;

 		message = "true";
 		try {
 		    EDMProperties edmProperties = EDMProperties.getInstance();
 		    convertedNumber = edmProperties.getConvertedNumber(pnumber, category, cadAppType);

 		    if (convertedNumber == null) {
 			isValid = false;
 			convertedNumber = pnumber;
 		    }

 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_number><![CDATA[<%=convertedNumber%>]]></l_number>
<l_name><![CDATA[<%=convertedName%>]]></l_name> <l_valid><![CDATA[<%=isValid%>]]></l_valid>
</data_info> <%
     } else if ("doRevise".equals(command)) {
 		String[] oids = request.getParameterValues("roid");

 		message = "true";
 		try {
 		    EDMServiceHelper.batchRevise(oids);
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
     } else if ("doReviseByView".equals(command)) {
 		String oid = request.getParameter("oid");
 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "euc-kr");

 		message = "true";
 		boolean isRevisable = true;
 		String newVersionOid = "";

 		ReferenceFactory rf = new ReferenceFactory();
 		EDMProperties edmProperties = EDMProperties.getInstance();
 		try {
 		    EPMDocument epm = (EPMDocument) rf.getReference(oid).getObject();

 		    ArrayList revisedObjs = new ArrayList();
 		    revisedObjs = EDMHelper.getAssociateDocsBy(epm);
 		    if (revisedObjs == null) {
 			revisedObjs = new ArrayList();
 		    }
 		    revisedObjs.add(0, epm);

 		    String[] revArrs = new String[revisedObjs.size()];
 		    for (int i = 0; i < revisedObjs.size(); i++) {
 			Object revObj = revisedObjs.get(i);
 			if (!(revObj instanceof EPMDocument)) {
 			    continue;
 			}

 			EPMDocument newVer = (EPMDocument) revObj;

 			boolean isLatestRevision = VersionHelper.isLatestRevision(newVer);
 			boolean isLatestIteration = VersionControlHelper.isLatestIteration((Iterated) newVer);
 			boolean isReleased = edmProperties.isReleasedState(newVer.getState().getState().toString());
 			boolean isCheckout = CheckInOutTaskLogic.isCheckedOut((Workable) newVer);

 			if (!isLatestRevision || !isLatestIteration || !isReleased || isCheckout) {
 			    isRevisable = false;
 			}

 			if (!isRevisable) {
 			    break;
 			}
 			revArrs[i] = rf.getReferenceString(newVer);
 		    }

 		    if (isRevisable) {
 			EDMServiceHelper.batchRevise(revArrs);

 			EPMDocument latest = (EPMDocument) VersionHelper.getLatestRevision(EPMDocument.class,
 			        (EPMDocumentMaster) epm.getMaster());
 			newVersionOid = rf.getReferenceString(latest);
 		    }

 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
 %> <data_info> <l_isRevisable><![CDATA[<%=String.valueOf(isRevisable)%>]]></l_isRevisable>
<l_newVersionOid><![CDATA[<%=newVersionOid%>]]></l_newVersionOid> </data_info> <%
     } else if ("doDelete".equals(command)) {
 		String oid = request.getParameter("oid");
 		oid = java.net.URLDecoder.decode(oid == null ? "" : oid, "euc-kr");

 		message = "true";
 		try {
 		    String[] oids = new String[1];
 		    oids[0] = oid;
 		    EDMServiceHelper.deleteObjects(oids);
 		} catch (Exception e) {
 		    Kogger.error(e);
 		    message = String.valueOf(false);
 		}
     }
 %> <message> <l_message><![CDATA[<%=message%>]]></l_message>
</message> <command>
        <l_command><![CDATA[<%=command%>]]></l_command>
      </command> </contents> </results> </stdinfo>
