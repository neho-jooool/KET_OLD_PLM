/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package e3ps.dms.service;

import java.io.Serializable;
import java.sql.Timestamp; // Preserved unmodeled dependency
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer; // Preserved unmodeled dependency
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.admin.AdminDomainRef; // Preserved unmodeled dependency
import wt.admin.DomainAdministered; // Preserved unmodeled dependency
import wt.admin.DomainAdministeredHelper; // Preserved unmodeled dependency
import wt.content.ApplicationData; // Preserved unmodeled dependency
import wt.content.ContentHelper; // Preserved unmodeled dependency
import wt.content.ContentHolder; // Preserved unmodeled dependency
import wt.content.ContentItem; // Preserved unmodeled dependency
import wt.content.ContentRoleType; // Preserved unmodeled dependency
import wt.content.FormatContentHolder; // Preserved unmodeled dependency
import wt.fc.PersistenceHelper; // Preserved unmodeled dependency
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult; // Preserved unmodeled dependency
import wt.fc.ReferenceFactory; // Preserved unmodeled dependency
import wt.fc.WTObject; // Preserved unmodeled dependency
import wt.folder.Folder; // Preserved unmodeled dependency
import wt.folder.FolderEntry; // Preserved unmodeled dependency
import wt.folder.FolderHelper; // Preserved unmodeled dependency
import wt.folder.IteratedFolderMemberLink;
import wt.folder.SubFolder; // Preserved unmodeled dependency
import wt.inf.container.WTContainerRef; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleHelper; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleManaged; // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleTemplate; // Preserved unmodeled dependency
import wt.org.WTUser; // Preserved unmodeled dependency
import wt.part.WTPart; // Preserved unmodeled dependency
import wt.pdmlink.PDMLinkProduct; // Preserved unmodeled dependency
import wt.pom.Transaction; // Preserved unmodeled dependency
import wt.query.ClassAttribute; // Preserved unmodeled dependency
import wt.query.ColumnExpression; // Preserved unmodeled dependency
import wt.query.ConstantExpression; // Preserved unmodeled dependency
import wt.query.OrderBy; // Preserved unmodeled dependency
import wt.query.QueryException; // Preserved unmodeled dependency
import wt.query.QuerySpec; // Preserved unmodeled dependency
import wt.query.SQLFunction; // Preserved unmodeled dependency
import wt.query.SearchCondition; // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionHelper; // Preserved unmodeled dependency
import wt.util.WTException;
import wt.util.WTPropertyVetoException; // Preserved unmodeled dependency
import wt.vc.VersionControlHelper; // Preserved unmodeled dependency
import wt.vc.Versioned; // Preserved unmodeled dependency
import wt.vc.wip.WorkInProgressHelper; // Preserved unmodeled dependency
import e3ps.common.content.ContentInfo; // Preserved unmodeled dependency
import e3ps.common.content.ContentUtil; // Preserved unmodeled dependency
import e3ps.common.content.E3PSContentHelper; // Preserved unmodeled dependency
import e3ps.common.content.uploader.WBFile; // Preserved unmodeled dependency
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil; // Preserved unmodeled dependency
import e3ps.common.util.DateUtil; // Preserved unmodeled dependency
import e3ps.common.util.KETObjectUtil; // Preserved unmodeled dependency
import e3ps.common.util.ManageSequence; // Preserved unmodeled dependency
import e3ps.common.util.StringUtil; // Preserved unmodeled dependency
import e3ps.common.util.WCUtil; // Preserved unmodeled dependency
import e3ps.dms.beans.DMSProperties; // Preserved unmodeled dependency
import e3ps.dms.entity.KETCarYearlyAmount; // Preserved unmodeled dependency
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETDocumentOutputLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETDocumentPartLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETDocumentProjectLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETFolderDocumentCategory; // Preserved unmodeled dependency
import e3ps.dms.entity.KETPartCarLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETRequestPartLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETRequestPartList; // Preserved unmodeled dependency
import e3ps.dms.entity.KETStandardTemplate;
import e3ps.dms.entity.KETTechnicalCategoryLink; // Preserved unmodeled dependency
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.groupware.company.CompanyState;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.PeopleData; // Preserved unmodeled dependency
import e3ps.project.E3PSProject; // Preserved unmodeled dependency
import e3ps.project.ProjectOutput; // Preserved unmodeled dependency
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectOutputHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.service.KETContentHelper;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
import ext.ket.shared.log.Kogger;

/**
 * <p>
 * Use the <code>newStandardKETDmsService</code> static factory method(s), not the <code>StandardKETDmsService</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * @version 1.0
 **/

public class StandardKETDmsService extends StandardManager implements KETDmsService, Serializable {

    private static final String RESOURCE = "e3ps.dms.service.serviceResource";
    private static final String CLASSNAME = StandardKETDmsService.class.getName();

    private String root = "ROOT";
    private String aRoot = DMSProperties.getInstance().getFolderPath("aRoot");
    private String eRoot = DMSProperties.getInstance().getFolderPath("eRoot");
    private String adRoot = DMSProperties.getInstance().getFolderPath("adRoot");
    private String aeRoot = DMSProperties.getInstance().getFolderPath("aeRoot");

    /**
     * Returns the conceptual (modeled) name for the class. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * @exception wt.services.ManagerException
     **/
    @Override
    protected void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

    }

    /**
     * Default factory for the class.
     * 
     * @return StandardKETDmsService
     * @exception wt.util.WTException
     **/
    public static StandardKETDmsService newStandardKETDmsService() throws WTException {

	StandardKETDmsService instance = new StandardKETDmsService();
	instance.initialize();
	return instance;
    }

    @Override
    public ArrayList selectDocCategoryForTree() throws WTException {

	ArrayList list = new ArrayList();

	try {
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", "ROOT"), new int[] { 0 });
	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), false), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(query);
	    if (!qr.hasMoreElements()) {
		makeRoot();
	    }

	    Object[] data = new Object[3];
	    data[1] = root; // child
	    data[2] = root; // child
	    list.add(data);
	    // Kogger.debug(getClass(), "nodecd ===>" + data[1]);
	    list = makeTree(root, list, true);

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return list;
    }

    @Override
    public ArrayList selectDocCategoryForTree(String categoryCode, boolean isSearch) throws WTException {

	ArrayList list = new ArrayList();

	try {
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), false), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(query);
	    if (!qr.hasMoreElements()) {
		makeRoot();
	    }

	    Object[] data = new Object[3];
	    data[1] = categoryCode; // child
	    data[2] = categoryCode; // child
	    list.add(data);
	    // Kogger.debug(getClass(), "nodecd ===>" + data[1]);
	    list = makeTree(categoryCode, list, isSearch);

	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return list;
    }

    @Override
    public ArrayList makeTree(String code, ArrayList list, boolean isSearch) throws Exception {

	QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	query.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", code), new int[] { 0 });
	// if(query.getConditionCount() > 0 ){
	// query.appendAnd();
	// }
	// query.appendOpenParen();
	// query.appendWhere(new SearchCondition(KETDocumentCategory.class, "isUsed", SearchCondition.IS_TRUE),new int[] { 0 });
	// query.appendCloseParen();

	if ((code.substring(2, 3).equals("1")) || (code.equals("ROOT"))) {
	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "sortNo"), false), new int[] { 0 });
	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), false), new int[] { 0 });
	} else if ((code.substring(2, 3).equals("2")) || (code.substring(2, 3).equals("3"))) {
	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryName"), false), new int[] { 0 });
	}

	QueryResult qr = PersistenceHelper.manager.find(query);
	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	boolean isDesignGroup = false;

	try {
	    isDesignGroup = CommonUtil.isMember("설계가이드등록", user) || CommonUtil.isMember("설계가이드관리", user) || CommonUtil.isAdmin();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	while (qr.hasMoreElements()) {
	    KETDocumentCategory element = (KETDocumentCategory) qr.nextElement();
	    Object[] d = new Object[5];
	    d[0] = code; // parent
	    d[1] = element.getCategoryCode(); // child

	    if (code.equals("ROOT")) {
		d[2] = element.getCategoryName() + "(" + element.getDocTypeCode() + ")"; // child
	    } else {
		d[2] = CodeHelper.manager.getCodeValue("DOCUMENTCATEGORY", element.getCategoryCode());// child
		d[3] = StringUtil.checkNull(element.getAttribute1()) == "" ? "N" : element.getAttribute1();// 설계가이드 문서여부
		if (StringUtils.isNotEmpty(element.getAttribute2()) && StringUtils.isEmpty(element.getAttribute3())) {
		    d[4] = "Y";
		} else {
		    d[4] = "N";
		}
	    }
	    if (!isSearch & "Y".equals(d[3]) && !isDesignGroup) {
		continue;
	    }

	    list.add(d);

	    // Kogger.debug(getClass(), "nodecd ===>" + element.getCategoryCode());
	    makeTree(element.getCategoryCode(), list, isSearch);

	}
	return list;

    }

    @Override
    public void makeRoot() throws WTException {
	KETDocumentCategory d = null;
	try {
	    d = KETDocumentCategory.newKETDocumentCategory();
	    d.setCategoryCode(root);
	    d.setCategoryName(root);

	    d = (KETDocumentCategory) PersistenceHelper.manager.save(d);

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	}

    }

    @Override
    public KETDocumentCategory selectDocCategory(String OID) throws WTException {
	KETDocumentCategory docCate = null;
	QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", OID), new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(query);

	while (qr.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr.nextElement();
	}

	return docCate;
    }

    @Override
    public KETDocumentCategory insertDocCategory(Hashtable hash) throws WTException {
	String categoryCode = null;
	String parentCategoryCode = null;
	String categoryPath = null;
	String categoryName = null;
	String docTypeCode = null;
	KETDocumentCategory docCate = null;
	String tmpStr = "";
	Integer tmpInt = 0;

	String numberingcode_levle_1 = null;
	String numberingcode_levle_2 = null;

	int iSort = 0;
	KETDocumentCategory d = null;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    categoryName = ((String) hash.get("categoryName")).trim();
	    docTypeCode = (String) hash.get("docTypeCode");
	    docTypeCode = docTypeCode.toUpperCase();
	    parentCategoryCode = ((String) hash.get("oid")).trim();

	    numberingcode_levle_1 = ((String) hash.get("numberingcode_levle_1")).trim();
	    numberingcode_levle_2 = ((String) hash.get("numberingcode_levle_2")).trim();

	    d = KETDocumentCategory.newKETDocumentCategory();
	    d.setParentCategoryCode(parentCategoryCode);
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", parentCategoryCode), new int[] { 0 });

	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), true), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find(query);

	    if (parentCategoryCode.equals("ROOT")) {

		categoryCode = docTypeCode + "100001";

	    } else {
		if (qr.hasMoreElements()) {

		    docCate = (KETDocumentCategory) qr.nextElement();
		    categoryCode = docCate.getCategoryCode();

		    if (categoryCode.substring(2, 3).equals("4")) {
			tmpInt = Integer.parseInt(categoryCode.substring(8, 11)) + 1;
			if (tmpInt < 10) {
			    categoryCode = categoryCode.substring(0, 8) + "00" + tmpInt.toString();
			} else if (tmpInt < 100) {
			    categoryCode = categoryCode.substring(0, 8) + "0" + tmpInt.toString();
			} else {
			    categoryCode = categoryCode.substring(0, 8) + tmpInt.toString();
			}

		    } else {

			tmpInt = Integer.parseInt(categoryCode.substring(5, 8)) + 1;
			if (tmpInt < 10) {
			    categoryCode = categoryCode.substring(0, 5) + "00" + tmpInt.toString();
			} else if (tmpInt < 100) {
			    categoryCode = categoryCode.substring(0, 5) + "0" + tmpInt.toString();
			} else {
			    categoryCode = categoryCode.substring(0, 5) + tmpInt.toString();
			}
		    }
		} else {
		    tmpInt = Integer.parseInt(parentCategoryCode.substring(2, 3)) + 1;
		    if (tmpInt == 4) {
			categoryCode = docTypeCode + tmpInt.toString().trim() + parentCategoryCode.substring(3, 8) + "001";
		    } else {
			categoryCode = docTypeCode + tmpInt.toString().trim() + parentCategoryCode.substring(6, 8) + "001";
		    }
		}
	    }
	    d.setCategoryCode(categoryCode);
	    d.setCategoryLevel(Integer.parseInt(categoryCode.substring(2, 3)));

	    d.setCategoryName(categoryName);
	    d.setDocTypeCode(((String) hash.get("docTypeCode")).trim());

	    tmpStr = (String) hash.get("sortNo");

	    d.setAttribute2(numberingcode_levle_1);
	    d.setAttribute3(numberingcode_levle_2);

	    try {
		iSort = Integer.parseInt(tmpStr);
	    } catch (NumberFormatException e) {
		iSort = 0;
	    }
	    d.setSortNo(iSort);

	    tmpStr = (String) hash.get("isUsed");
	    if (tmpStr.equals("true")) {
		d.setIsUsed(true);
	    } else {
		d.setIsUsed(false);
	    }
	    tmpStr = (String) hash.get("isAPQP");
	    if (tmpStr.equals("true")) {
		d.setIsAPQP(true);
	    } else {
		d.setIsAPQP(false);
	    }
	    tmpStr = (String) hash.get("isPSO10");
	    if (tmpStr.equals("true")) {
		d.setIsPSO10(true);
	    } else {
		d.setIsPSO10(false);
	    }
	    tmpStr = (String) hash.get("isESIR");
	    if (tmpStr.equals("true")) {
		d.setIsESIR(true);
	    } else {
		d.setIsESIR(false);
	    }
	    tmpStr = (String) hash.get("isISIRCar");
	    if (tmpStr.equals("true")) {
		d.setIsISIRCar(true);
	    } else {
		d.setIsISIRCar(false);
	    }
	    tmpStr = (String) hash.get("isISIRElec");
	    if (tmpStr.equals("true")) {
		d.setIsISIRElec(true);
	    } else {
		d.setIsISIRElec(false);
	    }
	    tmpStr = (String) hash.get("isANPQP");
	    if (tmpStr.equals("true")) {
		d.setIsANPQP(true);
	    } else {
		d.setIsANPQP(false);
	    }
	    tmpStr = (String) hash.get("isSYMC");
	    if (tmpStr.equals("true")) {
		d.setIsSYMC(true);
	    } else {
		d.setIsSYMC(false);
	    }
	    tmpStr = (String) hash.get("isDRCheckSheet");
	    if (tmpStr.equals("true")) {
		d.setIsDRCheckSheet(true);
	    } else {
		d.setIsDRCheckSheet(false);
	    }
	    tmpStr = (String) hash.get("isDesignSheet");
	    if (tmpStr.equals("true")) {
		d.setAttribute1("Y");
	    }
	    tmpStr = (String) hash.get("isSecurity");
	    if (tmpStr.equals("true")) {
		d.setAttribute4("Y");
	    }

	    tmpStr = (String) hash.get("isMatlDupCk");
	    if (tmpStr.equals("true")) {
		d.setAttribute5("Y");
	    }

	    tmpStr = (String) hash.get("isPrdRvsRqr");
	    if (tmpStr.equals("true")) {
		d.setAttribute6("Y");
	    }

	    //
	    d = (KETDocumentCategory) PersistenceHelper.manager.save(d);

	    //
	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    KETFolderDocumentCategory FDLink = null;

	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    categoryPath = selectCategoryPath(parentCategoryCode);
	    // , 2
	    Folder afolder = FolderHelper.service.createSubFolder(aRoot + categoryPath + "/" + categoryName, wtContainerRef);
	    FDLink = KETFolderDocumentCategory.newKETFolderDocumentCategory(afolder, d);
	    FDLink = (KETFolderDocumentCategory) PersistenceHelper.manager.save(FDLink);

	    Folder efolder = FolderHelper.service.createSubFolder(eRoot + categoryPath + "/" + categoryName, wtContainerRef);
	    // FDLink = KETFolderDocumentCategory.newKETFolderDocumentCategory(efolder, d);
	    // FDLink = (KETFolderDocumentCategory) PersistenceHelper.manager.save(FDLink);
	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;

    }

    @Override
    public KETDocumentCategory updateDocCategory(Hashtable hash) throws WTException {
	String categoryCode = null;
	String categoryPath = null;
	String categoryName = null;
	String numberingcode_levle_1 = null;
	String numberingcode_levle_2 = null;
	KETDocumentCategory docCate = null;
	String tmpStr = "";
	int iSort = 0;

	Transaction trx = new Transaction();
	//
	try {
	    trx.start();
	    categoryCode = ((String) hash.get("oid")).trim();

	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    Kogger.debug(getClass(), "queryS ===>" + query);
	    QueryResult qr = PersistenceHelper.manager.find(query);

	    if (qr.hasMoreElements()) {
		docCate = (KETDocumentCategory) qr.nextElement();

		categoryPath = selectCategoryPath(categoryCode);

		categoryName = ((String) hash.get("categoryName")).trim();
		docCate.setCategoryName(categoryName);

		tmpStr = (String) hash.get("sortNo");
		iSort = Integer.parseInt(tmpStr);
		docCate.setSortNo(iSort);

		tmpStr = (String) hash.get("isUsed");
		if (tmpStr.equals("true")) {
		    docCate.setIsUsed(true);
		} else {
		    docCate.setIsUsed(false);
		}
		tmpStr = (String) hash.get("isAPQP");
		if (tmpStr.equals("true")) {
		    docCate.setIsAPQP(true);
		} else {
		    docCate.setIsAPQP(false);
		}
		tmpStr = (String) hash.get("isPSO10");
		if (tmpStr.equals("true")) {
		    docCate.setIsPSO10(true);
		} else {
		    docCate.setIsPSO10(false);
		}
		tmpStr = (String) hash.get("isESIR");
		if (tmpStr.equals("true")) {
		    docCate.setIsESIR(true);
		} else {
		    docCate.setIsESIR(false);
		}
		tmpStr = (String) hash.get("isISIRCar");
		if (tmpStr.equals("true")) {
		    docCate.setIsISIRCar(true);
		} else {
		    docCate.setIsISIRCar(false);
		}
		tmpStr = (String) hash.get("isISIRElec");
		if (tmpStr.equals("true")) {
		    docCate.setIsISIRElec(true);
		} else {
		    docCate.setIsISIRElec(false);
		}
		tmpStr = (String) hash.get("isANPQP");
		if (tmpStr.equals("true")) {
		    docCate.setIsANPQP(true);
		} else {
		    docCate.setIsANPQP(false);
		}
		tmpStr = (String) hash.get("isSYMC");
		if (tmpStr.equals("true")) {
		    docCate.setIsSYMC(true);
		} else {
		    docCate.setIsSYMC(false);
		}
		tmpStr = (String) hash.get("isDRCheckSheet");
		if (tmpStr.equals("true")) {
		    docCate.setIsDRCheckSheet(true);
		} else {
		    docCate.setIsDRCheckSheet(false);
		}
		tmpStr = (String) hash.get("isDesignSheet");
		if (tmpStr.equals("true")) {
		    docCate.setAttribute1("Y");
		} else {
		    docCate.setAttribute1("");
		}
		tmpStr = (String) hash.get("isSecurity");
		if (tmpStr.equals("true")) {
		    docCate.setAttribute4("Y");
		} else {
		    docCate.setAttribute4("");
		}

		tmpStr = (String) hash.get("isMatlDupCk");
		if (tmpStr.equals("true")) {
		    docCate.setAttribute5("Y");
		} else {
		    docCate.setAttribute5("");
		}

		tmpStr = (String) hash.get("isPrdRvsRqr");
		if (tmpStr.equals("true")) {
		    docCate.setAttribute6("Y");
		} else {
		    docCate.setAttribute6("");
		}

		numberingcode_levle_1 = ((String) hash.get("numberingcode_levle_1")).trim();
		numberingcode_levle_2 = ((String) hash.get("numberingcode_levle_2")).trim();

		docCate.setAttribute2(numberingcode_levle_1);
		docCate.setAttribute3(numberingcode_levle_2);

		docCate = (KETDocumentCategory) PersistenceHelper.manager.save(docCate);

		PDMLinkProduct wtProduct = null;
		WTContainerRef wtContainerRef = null;

		QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
		SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
		qs.appendSearchCondition(sc1);
		QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
		if (results.hasMoreElements()) {
		    wtProduct = (PDMLinkProduct) results.nextElement();
		    wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
		}
		Folder afolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
		Folder efolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);
		AdminDomainRef domainRef = new AdminDomainRef();
		domainRef = DomainAdministeredHelper.getAdminDomainRef((DomainAdministered) afolder);
		afolder = FolderHelper.service.updateSubFolder((SubFolder) afolder, categoryName, domainRef, false);
		domainRef = DomainAdministeredHelper.getAdminDomainRef((DomainAdministered) efolder);
		efolder = FolderHelper.service.updateSubFolder((SubFolder) efolder, categoryName, domainRef, false);
	    }

	    trx.commit();
	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    docCate = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    docCate = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    docCate = null;
	    Kogger.error(getClass(), e);
	}

	return docCate;

    }

    @Override
    public String deleteDocCategory(String OID) throws WTException {
	String categoryCode = null;
	Transaction trx = new Transaction();

	try {
	    KETDocumentCategory docCate = null;

	    String categoryPath = null;
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", OID), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(query);
	    if (qr.hasMoreElements()) {
		docCate = (KETDocumentCategory) qr.nextElement();
		categoryCode = docCate.getCategoryCode();
		categoryPath = selectCategoryPath(categoryCode);

		// Folder cateFolder = null;
		// QueryResult q = PersistenceHelper.manager.navigate(docCate, "folder", KETFolderDocumentCategory.class, false);

		PersistenceHelper.manager.delete(docCate);

		PDMLinkProduct wtProduct = null;
		WTContainerRef wtContainerRef = null;

		QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
		SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
		qs.appendSearchCondition(sc1);
		QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
		if (results.hasMoreElements()) {
		    wtProduct = (PDMLinkProduct) results.nextElement();
		    wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
		}

		Folder afolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
		Folder efolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);

		PersistenceHelper.manager.delete(afolder);
		PersistenceHelper.manager.delete(efolder);

		// docCate = (KETDocumentCategory)PersistenceHelper.manager.refresh(docCate);
	    }
	    trx.commit();
	} catch (WTException wte) {
	    trx.rollback();
	    categoryCode = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    categoryCode = null;
	    Kogger.error(getClass(), e);
	}

	return categoryCode;
    }

    @Override
    public String selectCategoryPath(String OID) throws WTException {

	// .
	KETDocumentCategory docCate = null;
	String categoryName = null;
	String tmpStr = "";

	QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", OID), new int[] { 0 });
	QueryResult qr = PersistenceHelper.manager.find(query);

	if (qr.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr.nextElement();
	    if (!docCate.getCategoryName().equals("ROOT")) {

		categoryName = "/" + docCate.getCategoryName();
		tmpStr = docCate.getParentCategoryCode();
	    } else {
		categoryName = "";
		tmpStr = "";
	    }
	}

	QuerySpec query1 = new QuerySpec(KETDocumentCategory.class);
	query1.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", tmpStr), new int[] { 0 });
	QueryResult qr1 = PersistenceHelper.manager.find(query1);

	if (qr1.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr1.nextElement();
	    if (!docCate.getCategoryName().equals("ROOT")) {
		// ROOT categoryName
		categoryName = "/" + docCate.getCategoryName() + categoryName;
		// tmpStr
		tmpStr = docCate.getParentCategoryCode();
	    }
	}

	QuerySpec query2 = new QuerySpec(KETDocumentCategory.class);
	query2.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", tmpStr), new int[] { 0 });
	QueryResult qr2 = PersistenceHelper.manager.find(query2);

	if (qr2.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr2.nextElement();
	    if (!docCate.getCategoryName().equals("ROOT")) {
		categoryName = "/" + docCate.getCategoryName() + categoryName;
		tmpStr = docCate.getParentCategoryCode();
	    }
	}

	QuerySpec query3 = new QuerySpec(KETDocumentCategory.class);
	query3.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", tmpStr), new int[] { 0 });
	QueryResult qr3 = PersistenceHelper.manager.find(query3);

	if (qr3.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr3.nextElement();
	    if (!docCate.getCategoryName().equals("ROOT")) {
		categoryName = "/" + docCate.getCategoryName() + categoryName;
		tmpStr = docCate.getParentCategoryCode();
	    }
	}

	QuerySpec query4 = new QuerySpec(KETDocumentCategory.class);
	query4.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", tmpStr), new int[] { 0 });
	QueryResult qr4 = PersistenceHelper.manager.find(query4);

	if (qr4.hasMoreElements()) {
	    docCate = (KETDocumentCategory) qr4.nextElement();
	    if (!docCate.getCategoryName().equals("ROOT")) {
		categoryName = "/" + docCate.getCategoryName() + categoryName;
		tmpStr = docCate.getParentCategoryCode();
	    }
	}

	return categoryName;
    }

    @Override
    public ArrayList selectChildDocCategory(String code) throws WTException {

	ArrayList list = new ArrayList();
	Boolean isUse = false;
	try {
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);

	    if (code.substring(0, 1).equals("X")) {
		isUse = true;
		code = code.substring(1);
	    }

	    if (query.getConditionCount() > 0)
		query.appendAnd();
	    query.appendOpenParen();
	    StringTokenizer codeToken = new StringTokenizer(code, ",");
	    while (codeToken.hasMoreTokens()) {
		query.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", codeToken.nextToken()), new int[] { 0 });
		if (codeToken.hasMoreTokens())
		    query.appendOr();
	    }
	    query.appendCloseParen();

	    if (isUse) {
		query.appendAnd();
		query.appendOpenParen();
		query.appendWhere(new SearchCondition(KETDocumentCategory.class, "isUsed", SearchCondition.IS_TRUE), new int[] { 0 });
		query.appendCloseParen();
	    }

	    if ((code.substring(2, 3).equals("1")) || (code.equals("ROOT"))) {
		// 2 sortNo
		query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "sortNo"), false), new int[] { 0 });
		query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), false), new int[] { 0 });
	    } else if ((code.substring(2, 3).equals("2")) || (code.substring(2, 3).equals("3"))) {
		// 3
		query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryName"), false), new int[] { 0 });
		query.appendOrderBy(new OrderBy(new ClassAttribute(KETDocumentCategory.class, "categoryCode"), false), new int[] { 0 });
	    }

	    QueryResult qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		KETDocumentCategory element = (KETDocumentCategory) qr.nextElement();
		Object[] d = new Object[3];

		d[0] = element.getCategoryCode(); // child
		d[1] = element.getCategoryName(); // child
		d[2] = element.getIsDRCheckSheet().toString();
		list.add(d);
		// Kogger.debug(getClass(), "nodecd ===>" + element.getCategoryCode());
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return list;
    }

    public String getQulityDocuRule(String numberingCode1, String numberingCode2, String qualityNo) throws Exception {
	/*
	 * 채번규칙 1. 채번코드 레벨 1, 레벨2가 다 존재하면 documentNo = numberingCode1 + numberingCode2 + 시리얼 2. 채번코드 레벨 1 만 있으면 documentNo = numberingCode1
	 * + partNo1 3. 채번코드가 없으면 PD-년월-시리얼
	 */
	String documentNo = "";

	if (StringUtils.isNotEmpty(numberingCode1)) {
	    documentNo = numberingCode1;

	    if (StringUtils.isNotEmpty(numberingCode2)) {
		documentNo = documentNo += "-" + numberingCode2 + "-";
		documentNo += ManageSequence.getSeqNo(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber");
	    } else {
		documentNo += "-" + qualityNo;
	    }
	}

	return documentNo;
    }

    @Override
    public KETProjectDocument insertProjectDoc(Hashtable hash) throws WTException {
	String documentNo = null;
	String deptName = null;

	int dRCheckPoint = 0;

	String categoryCode = null;
	String categoryPath = null;
	String analysisCode = null;
	String analysisCodeTxt = null;
	String costComment = null;
	Vector partOids = null;
	Vector partNumber = null;
	String qualityNo = "";
	// String QpartNumber = "";

	Vector projectOids = null;
	String projectOid = null;
	String divisionCode = null;
	Integer tmpInt = 0;

	KETProjectDocument d = null;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    categoryCode = (String) hash.get("categoryCode");
	    analysisCode = (String) hash.get("analysisCode");
	    analysisCodeTxt = (String) hash.get("analysisCodeTxt");
	    costComment = StringUtil.checkNull((String) hash.get("costComment"));
	    qualityNo = (String) hash.get("relatedPart");

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    while (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    String numberingCode1 = docCate.getAttribute2();
	    String numberingCode2 = docCate.getAttribute3();

	    /*
	     * // 품질표준문서 if (hash.get("partNumber") instanceof Vector) { partNumber = (Vector) hash.get("partNumber"); if (partNumber.size()
	     * > 0) { QpartNumber = (String) partNumber.get(0); } } else { QpartNumber = (String) hash.get("partNumber"); }
	     * 
	     * if ("PD303066".equals(categoryCode)) { qualityNo = "DF-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303072".equals(categoryCode)) { qualityNo = "PF-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303001".equals(categoryCode)) { qualityNo = "KIS-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303017".equals(categoryCode)) { qualityNo = "CP-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303034".equals(categoryCode)) { qualityNo = "KEQ-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303046".equals(categoryCode)) { qualityNo = "KETS-" + StringUtil.checkNull(QpartNumber); } else if
	     * ("PD303047".equals(categoryCode)) { qualityNo = "PFC-" + StringUtil.checkNull(QpartNumber); }
	     */

	    categoryPath = selectCategoryPath(categoryCode);
	    d = KETProjectDocument.newKETProjectDocument();
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date currentTime = new java.util.Date();
	    String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
	    documentNo = categoryCode.substring(0, 2) + "-" + ymonth;
	    QuerySpec query = new QuerySpec(KETProjectDocument.class);
	    query.appendWhere(new SearchCondition(KETProjectDocument.class, "documentNo", SearchCondition.LIKE, documentNo + "%"), new int[] { 0 });
	    query.appendAnd();
	    query.appendWhere(VersionControlHelper.getSearchCondition(KETProjectDocument.class, true), new int[] { 0 });

	    query.appendOrderBy(new OrderBy(new ClassAttribute(KETProjectDocument.class, "documentNo"), true), new int[] { 0 });
	    Kogger.debug(getClass(), "query=====>" + query.toString());
	    QueryResult qr = PersistenceHelper.manager.find(query);

	    if (qr.hasMoreElements()) {
		KETProjectDocument projectDoc = (KETProjectDocument) qr.nextElement();
		documentNo = projectDoc.getDocumentNo();
		// (4)max +1
		tmpInt = Integer.parseInt(documentNo.substring(8, 12)) + 1;

		if (tmpInt < 10) {
		    documentNo = documentNo.substring(0, 8) + "000" + tmpInt.toString();
		} else if (tmpInt < 100) {
		    documentNo = documentNo.substring(0, 8) + "00" + tmpInt.toString();
		} else if (tmpInt < 1000) {
		    documentNo = documentNo.substring(0, 8) + "0" + tmpInt.toString();
		} else {
		    documentNo = documentNo.substring(0, 8) + tmpInt.toString();
		}
	    } else {
		// 0001
		documentNo = documentNo + "-0001";
	    }

	    /*
	     * 채번규칙 1. 채번코드 레벨 1, 레벨2가 다 존재하면 documentNo = numberingCode1 + numberingCode2 + 시리얼 2. 채번코드 레벨 1 만 있으면 documentNo =
	     * numberingCode1 + partNo1 3. 채번코드가 없으면 PD-년월-시리얼
	     */
	    if (StringUtils.isNotEmpty(numberingCode1)) {
		documentNo = this.getQulityDocuRule(numberingCode1, numberingCode2, qualityNo);
		d.setAttribute1(documentNo);
	    }

	    d.setAttribute2(analysisCode);
	    d.setAttribute3(analysisCodeTxt);
	    d.setDocumentNo(documentNo);
	    d.setNumber(documentNo);
	    d.setTitle((String) hash.get("documentName"));

	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData peoData = new PeopleData(user);
	    deptName = peoData.departmentName;

	    // d.setDepartment(peoData.department);
	    // deptName = DepartmentList.getDepartmentListDefault().getLongDescription();
	    d.setDeptName(deptName);
	    // LifeCycleHelper.setLifeCycle(d,
	    // LifeCycleHelper.service.getLifeCycleTemplate((String)hash.get("lifecycle"),WCUtil.getWTContainerRef()));

	    d.setFirstRegistrationStage(Integer.parseInt((String) hash.get("firstRegistrationStage")));
	    d.setIsBuyerSummit((String) hash.get("isBuyerSummit"));
	    d.setBuyerCode((String) hash.get("buyerCode"));
	    divisionCode = (String) hash.get("divisionCode");
	    d.setDivisionCode(divisionCode);
	    dRCheckPoint = Integer.parseInt((String) hash.get("dRCheckPoint"));
	    if (dRCheckPoint >= 0)
		d.setDRCheckPoint(dRCheckPoint);
	    d.setDocumentDescription((String) hash.get("documentDescription"));

	    d.setSecurity((String) hash.get("security"));

	    String isDeptACL = (String) hash.get("isDeptACL");
	    d.setAttribute7(isDeptACL);

	    if ("true".equals(isDeptACL)) {

		String devDeptCode = (String) hash.get("devDeptCode");
		if (StringUtil.checkString(devDeptCode) && devDeptCode.indexOf(Department.class.getName()) >= 0) {
		    Department dept = (Department) CommonUtil.getObject(devDeptCode);
		    devDeptCode = dept.getCode();
		}

		d.setAttribute4(devDeptCode);
		String dutyCode = (String) hash.get("duty");
		d.setAttribute5(dutyCode);

		if (StringUtil.checkString(dutyCode)) {
		    d.setAttribute6((String) CompanyState.dutyTable.get(dutyCode));
		}

	    } else {
		d.setAttribute4(null);
		d.setAttribute5(null);
		d.setAttribute6(null);
	    }

	    String pubDateYn = (String) hash.get("pubDateYn");
	    if ("Y".equals(pubDateYn)) {
		d.setAttribute8(pubDateYn);
		d.setAttribute9((String) hash.get("pubDate"));
		d.setAttribute10((String) hash.get("pubCycle"));
	    } else {
		d.setAttribute8(null);
		d.setAttribute9(null);
		d.setAttribute10(null);
	    }
	    d.setCostComment(costComment);
	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */

	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    // assignLocation.
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    }

	    FolderHelper.assignLocation((FolderEntry) d, cateFolder);

	    d.setName(docCate.getCategoryName());
	    d = (KETProjectDocument) PersistenceHelper.manager.save(d);

	    KETDocumentCategoryLink DCLink;
	    DCLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(docCate, d);
	    DCLink = (KETDocumentCategoryLink) PersistenceHelper.manager.save(DCLink);

	    if (StringUtils.isNotEmpty(numberingCode1) && StringUtils.isEmpty(numberingCode2)) {

		WTPart wtpart = PartBaseHelper.service.getLatestPart(qualityNo);

		KETDocumentPartLink DpLink;
		DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
		wt.fc.PersistenceServerHelper.manager.insert(DpLink);
	    }

	    if (hash.get("partOid") instanceof Vector) {
		ReferenceFactory rf = new ReferenceFactory();
		partOids = (Vector) hash.get("partOid");
		if (partOids.size() > 1) {
		    for (int i = 1; i < partOids.size(); i++) {
			String partOid = (String) partOids.get(i);
			WTPart wtpart = (WTPart) rf.getReference(partOid).getObject();
			// Kogger.debug(getClass(), "partno==========>"+wtpart.getNumber());
			KETDocumentPartLink DpLink;
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);

			wt.fc.PersistenceServerHelper.manager.insert(DpLink);
		    }
		}
	    }

	    KETDocumentProjectLink DPrLink;
	    if (hash.get("projectOid") instanceof Vector) {
		projectOids = (Vector) hash.get("projectOid");
		for (int i = 1; i < projectOids.size(); i++) {

		    String projOid = (String) projectOids.get(i);
		    E3PSProject pr = (E3PSProject) CommonUtil.getObject(projOid);

		    DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, pr);
		    wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
		}
	    }

	    // String outputOid = (String) hash.get("outputOid");
	    Object outputOidObj = hash.get("outputOid");
	    // if (!outputOid.equals("0")) {
	    if (outputOidObj != null) {
		if (outputOidObj instanceof String) {
		    String outputOid = (String) outputOidObj;
		    ProjectOutput po = (ProjectOutput) CommonUtil.getObject(outputOid);
		    KETDocumentOutputLink DoLink;
		    DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d, po);
		    wt.fc.PersistenceServerHelper.manager.insert(DoLink);
		    ProjectOutputHelper.manager.registryProjectOutput(po, d);
		} else if (outputOidObj instanceof Vector) {
		    @SuppressWarnings("unchecked")
		    Vector<String> outputOidVec = (Vector<String>) outputOidObj;
		    for (int i = 0; i < outputOidVec.size(); i++) {
			String outputOid = outputOidVec.elementAt(i);
			ProjectOutput po = (ProjectOutput) CommonUtil.getObject(outputOid);
			KETDocumentOutputLink DoLink;
			DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d, po);
			wt.fc.PersistenceServerHelper.manager.insert(DoLink);
			ProjectOutputHelper.manager.registryProjectOutput(po, d);
		    }
		}
	    }

	    trx.commit();

	    Kogger.debug(getClass(), "insertProjectDoc documentNo=====>" + d.getDocumentNo());

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

    @Override
    public KETProjectDocument updateProjectDoc(Hashtable hash) throws WTException {
	String docuOid = null;
	String documentNo = null;
	String categoryCode = null;
	String categoryPath = null;
	Vector partNumber = null;
	String QpartNumber = "";
	String qualityNo = "";
	String uQualityNo = "";
	String analysisCode = null;
	String analysisCodeTxt = null;
	String costComment = null;
	int dRCheckPoint = 0;

	KETProjectDocument d = null;
	Vector partOids = null;
	Hashtable docHash;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    docuOid = ((String) hash.get("docuOid")).trim();
	    d = (KETProjectDocument) CommonUtil.getObject(docuOid);

	    categoryCode = (String) hash.get("categoryCode");
	    categoryPath = selectCategoryPath(categoryCode);

	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    String divisionCode = d.getDivisionCode();

	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    }

	    // iterFolderMemberLink 가 삭제되었을 경우 방어 코딩 Start by 황정태 2019.08.02
	    IteratedFolderMemberLink iterFolderMemberLink = null;

	    QuerySpec qs2 = new QuerySpec();
	    int f_idx = qs2.addClassList(IteratedFolderMemberLink.class, true);
	    qs2.appendWhere(new SearchCondition(IteratedFolderMemberLink.class, "roleBObjectRef.key.branchId", SearchCondition.EQUAL, VersionControlHelper.getBranchIdentifier(d)), new int[] { f_idx });

	    results = (QueryResult) PersistenceHelper.manager.find(qs2);
	    if (results.hasMoreElements()) {
		Object[] obj = (Object[]) results.nextElement();
		iterFolderMemberLink = (IteratedFolderMemberLink) obj[0];
	    }

	    if (iterFolderMemberLink == null) {
		iterFolderMemberLink = IteratedFolderMemberLink.newIteratedFolderMemberLink(cateFolder, d);
		PersistenceHelper.manager.save(iterFolderMemberLink);
	    }
	    // iterFolderMemberLink 가 삭제되었을 경우 방어 코딩 End by 황정태 2019.08.02

	    Kogger.debug(getClass(), "updated ProjectDoc docoid=====>" + docuOid);

	    KETDocumentPartLink DpLink;
	    QueryResult r = PersistenceHelper.manager.navigate(d, "part", KETDocumentPartLink.class, false);
	    while (r.hasMoreElements()) {
		DpLink = (KETDocumentPartLink) r.nextElement();
		PersistenceHelper.manager.delete(DpLink);
	    }

	    KETDocumentProjectLink DPrLink;
	    QueryResult r2 = PersistenceHelper.manager.navigate(d, "project", KETDocumentProjectLink.class, false);
	    while (r2.hasMoreElements()) {
		DPrLink = (KETDocumentProjectLink) r2.nextElement();
		PersistenceHelper.manager.delete(DPrLink);
	    }

	    KETDocumentOutputLink DoLink;
	    QueryResult r3 = PersistenceHelper.manager.navigate(d, "output", KETDocumentOutputLink.class, false);
	    if (r3.hasMoreElements()) {
		DoLink = (KETDocumentOutputLink) r3.nextElement();
		PersistenceHelper.manager.delete(DoLink);
	    }

	    KETDocumentCategoryLink DCLink;
	    DCLink = new KETDocumentCategoryLink();
	    QueryResult r1 = PersistenceHelper.manager.navigate(d, "documentCategory", KETDocumentCategoryLink.class, false);
	    if (r1.hasMoreElements()) {
		DCLink = (KETDocumentCategoryLink) r1.nextElement();
		PersistenceHelper.manager.delete(DCLink);
	    }

	    // 이터레이션 발생없게 하기 위해 체크아웃 로직은 주석처리한다 start 2019.08.02 by 황정태
	    // docHash = KETObjectUtil.checkOut(docuOid);
	    // if (docHash == null) {
	    // return d;
	    // }
	    //
	    // d = (KETProjectDocument) docHash.get("WorkableObject");
	    // documentNo = d.getDocumentNo();

	    // 이터레이션 발생없게 하기 위해 체크아웃 로직은 주석처리한다 start 2019.08.02 by 황정태

	    // LifeCycleHelper.setLifeCycle((LifeCycleManaged) d, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,
	    // WCUtil.getWTContainerRef()));
	    d.setTitle((String) hash.get("documentName"));
	    d.setIsBuyerSummit((String) hash.get("isBuyerSummit"));
	    d.setBuyerCode((String) hash.get("buyerCode"));

	    if (StringUtil.checkString((String) hash.get("dRCheckPoint"))) {
		dRCheckPoint = Integer.parseInt((String) hash.get("dRCheckPoint"));
	    }
	    if (dRCheckPoint >= 0)
		d.setDRCheckPoint(dRCheckPoint);

	    d.setDocumentDescription(((String) hash.get("documentDescription")).trim());
	    d.setSecurity((String) hash.get("security"));

	    String isDeptACL = (String) hash.get("isDeptACL");
	    d.setAttribute7(isDeptACL);

	    if ("true".equals(isDeptACL)) {

		String devDeptCode = (String) hash.get("devDeptCode");
		if (StringUtil.checkString(devDeptCode) && devDeptCode.indexOf(Department.class.getName()) >= 0) {
		    Department dept = (Department) CommonUtil.getObject(devDeptCode);
		    devDeptCode = dept.getCode();
		}

		d.setAttribute4(devDeptCode);
		String dutyCode = (String) hash.get("duty");
		d.setAttribute5(dutyCode);

		if (StringUtil.checkString(dutyCode)) {
		    d.setAttribute6((String) CompanyState.dutyTable.get(dutyCode));
		}

	    } else {
		d.setAttribute4(null);
		d.setAttribute5(null);
		d.setAttribute6(null);
	    }

	    String pubDateYn = (String) hash.get("pubDateYn");
	    if ("Y".equals(pubDateYn)) {
		d.setAttribute8(pubDateYn);
		d.setAttribute9((String) hash.get("pubDate"));
		d.setAttribute10((String) hash.get("pubCycle"));
	    } else {
		d.setAttribute8(null);
		d.setAttribute9(null);
		d.setAttribute10(null);
	    }
	    costComment = StringUtil.checkNull((String) hash.get("costComment"));
	    d.setCostComment(costComment);

	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */

	    analysisCode = (String) hash.get("analysisCode");
	    analysisCodeTxt = (String) hash.get("analysisCodeTxt");

	    // 품질표준문서
	    uQualityNo = (String) hash.get("attribute1");
	    System.out.println("by hooni :::: StandardKETDmsService ::: " + uQualityNo);
	    if ("".equals(uQualityNo)) {
		if (hash.get("partNumber") instanceof Vector) {
		    partNumber = (Vector) hash.get("partNumber");
		    if (partNumber.size() > 0) {
			QpartNumber = (String) partNumber.get(0);
		    }
		} else {
		    QpartNumber = (String) hash.get("partNumber");
		}

		if ("PD303066".equals(categoryCode)) {
		    qualityNo = "DF-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303072".equals(categoryCode)) {
		    qualityNo = "PF-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303001".equals(categoryCode)) {
		    qualityNo = "KIS-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303017".equals(categoryCode)) {
		    qualityNo = "CP-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303034".equals(categoryCode)) {
		    qualityNo = "KEQ-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303046".equals(categoryCode)) {
		    qualityNo = "KETS-" + StringUtil.checkNull(QpartNumber);
		} else if ("PD303047".equals(categoryCode)) {
		    qualityNo = "PFC-" + StringUtil.checkNull(QpartNumber);
		} else {
		    qualityNo = "";
		}
	    } else {
		if ("PD303066".equals(categoryCode)) {
		    qualityNo = "DF-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303072".equals(categoryCode)) {
		    qualityNo = "PF-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303001".equals(categoryCode)) {
		    qualityNo = "KIS-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303017".equals(categoryCode)) {
		    qualityNo = "CP-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303034".equals(categoryCode)) {
		    qualityNo = "KEQ-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303046".equals(categoryCode)) {
		    qualityNo = "KETS-" + StringUtil.checkNull(uQualityNo);
		} else if ("PD303047".equals(categoryCode)) {
		    qualityNo = "PFC-" + StringUtil.checkNull(uQualityNo);
		} else {
		    qualityNo = "";
		}
	    }
	    d.setAttribute1(qualityNo);
	    d.setAttribute2(analysisCode);
	    d.setAttribute3(analysisCodeTxt);

	    // 이터레이션 발생없이 수정하도록 함 2019.08.02 by 황정태
	    // d = (KETProjectDocument) PersistenceHelper.manager.save(d);

	    PersistenceServerHelper.manager.update(d);
	    d = (KETProjectDocument) PersistenceHelper.manager.refresh(d);

	    // 이터레이션 발생없이 수정하도록 함 2019.08.02 by 황정태
	    // d = (KETProjectDocument) KETObjectUtil.checkIn(d);

	    if (hash.get("partOid") instanceof Vector) {
		ReferenceFactory rf = new ReferenceFactory();
		partOids = (Vector) hash.get("partOid");
		if (partOids.size() > 1) {
		    for (int i = 1; i < partOids.size(); i++) {
			String partOid = (String) partOids.get(i);
			// WTPart wtpart = (WTPart)rf.getReference(partOid).getObject();
			WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			wt.fc.PersistenceServerHelper.manager.insert(DpLink);
		    }
		}
	    }

	    Vector projectOids = null;
	    String projectOid = null;
	    if (hash.get("projectOid") instanceof Vector) {
		projectOids = (Vector) hash.get("projectOid");
		for (int i = 1; i < projectOids.size(); i++) {

		    String projOid = (String) projectOids.get(i);
		    E3PSProject pr = (E3PSProject) CommonUtil.getObject(projOid);

		    DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, pr);
		    wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
		}
	    }
	    String outputOid = (String) hash.get("outputOid");
	    if (!outputOid.equals("0")) {
		ProjectOutput po = (ProjectOutput) CommonUtil.getObject(outputOid);

		DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d, po);
		wt.fc.PersistenceServerHelper.manager.insert(DoLink);
	    }

	    FolderHelper.service.changeFolder((FolderEntry) d, cateFolder);

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    if (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    DCLink = new KETDocumentCategoryLink();
	    DCLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(docCate, d);
	    DCLink = (KETDocumentCategoryLink) PersistenceHelper.manager.save(DCLink);

	    String sfiles = (String) hash.get("isFileDel");
	    Kogger.debug(getClass(), "delfiles=====>" + sfiles);
	    if (sfiles.equals("0")) {

	    } else {

		sfiles = sfiles.substring(2);
		StringTokenizer st = new StringTokenizer(sfiles, "/");
		while (st.hasMoreTokens()) {

		    String delsFile = st.nextToken();
		    Kogger.debug(getClass(), "delfilesI=====>" + delsFile);
		    ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
		    ContentInfo info = ContentUtil.getContentInfo((ContentHolder) d, ctf);
		    String delsFileName = info.getName();
		    Kogger.debug(getClass(), "delsFileName=====>" + delsFileName);

		    QueryResult rs = ContentHelper.service.getContentsByRole(d, ContentRoleType.SECONDARY);

		    while (rs.hasMoreElements()) {
			ContentItem ctf1 = (ContentItem) rs.nextElement();
			ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) d, ctf1);

			String itemStr = info1.getName();
			Kogger.debug(getClass(), "itemStr=====>" + itemStr);
			if (itemStr.equals(delsFileName)) {
			    d = (KETProjectDocument) E3PSContentHelper.service.delete(d, ctf1);
			}
		    }
		}
	    }

	    if (hash.get("files") instanceof Vector) {

		Vector files = (Vector) hash.get("files");
		if (files != null) {

		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {

			    if (d instanceof FormatContentHolder) {
				FormatContentHolder holder = (FormatContentHolder) d;
				ContentInfo info = ContentUtil.getPrimaryContent(holder);
				ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

				E3PSContentHelper.service.delete((ContentHolder) d, ctf);
			    }
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(d, file, "", isPrimary);
		    }
		} else {

		}
	    }
	    trx.commit();
	    documentNo = d.getDocumentNo();
	    Kogger.debug(getClass(), "updated ProjectDoc documentNo=====>" + documentNo);

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

    @Override
    public String deleteProjectDoc(String OID) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();

	KETProjectDocument d = null;
	String docuOid = OID;
	Transaction trx = new Transaction();
	trx.start();

	try {
	    d = (KETProjectDocument) CommonUtil.getObject(docuOid);

	    /*
	     * ECO에 연관되어 있을 경우 삭제할 수 없다.
	     */
	    QueryResult linkResult = PersistenceHelper.manager.navigate(d, "prodECA", KETProdECADocLink.class, false);
	    int linkResultSize = (linkResult != null) ? linkResult.size() : 0;
	    if (linkResultSize > 0) {

		StringBuffer ecoId = new StringBuffer(")");
		while (linkResult.hasMoreElements()) {
		    KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) linkResult.nextElement();
		    KETProdChangeActivity eca = ketProdECADocLink.getProdECA();

		    QueryResult linkResult2 = PersistenceHelper.manager.navigate(eca, "prodECO", KETProdChangeActivityLink.class, false);
		    while (linkResult2.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) linkResult2.nextElement();
			KETProdChangeOrder eco = ketProdChangeActivityLink.getProdECO();

			ecoId.append(eco.getEcoId() + ", ");

		    }

		    ecoId.delete(0, (ecoId.lastIndexOf(", ") - 1));

		}
		throw new WTException(messageService.getString("e3ps.message.ket_message", "04280") + " " + ecoId);
	    }

	    boolean isCheckout = KETObjectUtil.isCheckout((WTObject) d);
	    if (isCheckout) {
		d = (KETProjectDocument) KETObjectUtil.checkIn(d);
		Kogger.debug(getClass(), "==========>Checkouted");
		return null;
		// return "isCheckout true";
	    }

	    // if (persistable instanceof LifeCycleManaged)
	    // E3PSWorkflowHelper.manager.deleteWfProcess((LifeCycleManaged) persistable);

	    d = (KETProjectDocument) KETContentHelper.service.delete(d);

	    PersistenceHelper.manager.delete(d);
	    trx.commit();

	    Kogger.debug(getClass(), "deleteProjectDoc docuOid=====>" + docuOid);

	} catch (WTException wte) {
	    trx.rollback();
	    docuOid = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    docuOid = null;
	    Kogger.error(getClass(), e);
	}

	return docuOid;
    }

    @Override
    public Vector searchProjectDoc(Hashtable hash) throws WTException {
	Vector docuOids = null;
	KETProjectDocument d = null;

	String documentNo = ((String) hash.get("documentNo")).trim();
	String divisionCode = ((String) hash.get("divisionCode")).trim();
	String categoryCode = ((String) hash.get("categoryCode")).trim();
	String documentName = ((String) hash.get("documentName")).trim();
	String authorStatus = ((String) hash.get("authorStatus")).trim();
	String creator = ((String) hash.get("creator")).trim();
	String predate = ((String) hash.get("predate")).trim();
	String postdate = ((String) hash.get("postdate")).trim();
	String isBuyerSummit = ((String) hash.get("isBuyerSummit")).trim();
	String buyerCodeStr = ((String) hash.get("buyerCodeStr")).trim();
	String islastversion = ((String) hash.get("islastversion")).trim();
	String sortKey = ((String) hash.get("sortKey")).trim();

	try {
	    // d.getIterationInfo().getIdentifier().getValue()

	    Class docuClass = KETProjectDocument.class;
	    QuerySpec qs = new QuerySpec();
	    qs.setAdvancedQueryEnabled(true);
	    int classIdx = qs.appendClassList(docuClass, true);
	    ColumnExpression ce;
	    SQLFunction sqlfunction;

	    qs.appendWhere(VersionControlHelper.getSearchCondition(docuClass, true), new int[] { classIdx });
	    Kogger.debug(getClass(), "QuerySpec0==========>" + qs.toString());

	    if (islastversion.equals("1")) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(docuClass, "iterationInfo.identifier.iterationId", "=", "0"), new int[] { classIdx });
		Kogger.debug(getClass(), "QuerySpec1==========>" + qs.toString());

	    }

	    if (documentNo.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(docuClass, "documentNo"));
		ce = ConstantExpression.newExpression("%" + documentNo.toUpperCase() + "%");
		qs.appendWhere(new SearchCondition(sqlfunction, SearchCondition.LIKE, ce), new int[] { classIdx });

		Kogger.debug(getClass(), "QuerySpec2==========>" + qs.toString());
	    }

	    if (divisionCode.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(docuClass, "divisionCode"));
		ce = ConstantExpression.newExpression(divisionCode.toUpperCase());
		qs.appendWhere(new SearchCondition(sqlfunction, SearchCondition.EQUAL, ce), new int[] { classIdx });

		Kogger.debug(getClass(), "QuerySpec3==========>" + qs.toString());
	    }

	    if (categoryCode.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		KETDocumentCategory docCate = selectDocCategory(categoryCode);
		int idx_codeLink = qs.appendClassList(KETDocumentCategoryLink.class, false);
		ClassAttribute ca = new ClassAttribute(docuClass, "thePersistInfo.key.id");
		ClassAttribute ca1 = new ClassAttribute(KETDocumentCategoryLink.class, "roleBObjectRef.key.id");
		SearchCondition sc = new SearchCondition(ca, "=", ca1);
		sc.setFromIndicies(new int[] { classIdx, idx_codeLink }, 0);
		sc.setOuterJoin(0);
		qs.appendWhere(sc, new int[] { classIdx, idx_codeLink });

		Kogger.debug(getClass(), "QuerySpec4==========>" + qs.toString());

		qs.appendAnd();
		qs.appendOpenParen();
		qs.appendWhere(new SearchCondition(KETDocumentCategoryLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, docCate.getPersistInfo().getObjectIdentifier().getId()),
		        new int[] { idx_codeLink });
		qs.appendCloseParen();

		Kogger.debug(getClass(), "QuerySpec5==========>" + qs.toString());
	    }

	    if (documentName.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(docuClass, "title"));
		ce = ConstantExpression.newExpression("%" + documentName.toUpperCase() + "%");
		qs.appendWhere(new SearchCondition(sqlfunction, SearchCondition.LIKE, ce), new int[] { classIdx });

		Kogger.debug(getClass(), "QuerySpec6==========>" + qs.toString());
	    }

	    if (creator.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();

		ReferenceFactory rf = new ReferenceFactory();
		WTUser user = null;
		if (creator.length() > 0) {
		    try {
			user = (WTUser) rf.getReference(creator).getObject();

		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}

		qs.appendWhere(new SearchCondition(docuClass, "iterationInfo.creator.key.id", "=", CommonUtil.getOIDLongValue(user)), new int[] { classIdx });
		Kogger.debug(getClass(), "QuerySpec5==========>" + qs.toString());
	    }

	    if (predate.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(docuClass, "thePersistInfo.createStamp", ">=", DateUtil.convertStartDate(predate.trim())), new int[] { classIdx });
		Kogger.debug(getClass(), "QuerySpec6==========>" + qs.toString());
	    }

	    if (postdate.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(docuClass, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate(postdate.trim())), new int[] { classIdx });
		Kogger.debug(getClass(), "QuerySpec7==========>" + qs.toString());
	    }

	    if (!isBuyerSummit.equals("0")) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(docuClass, "isBuyerSummit"));
		ce = ConstantExpression.newExpression(isBuyerSummit.toUpperCase());
		qs.appendWhere(new SearchCondition(sqlfunction, SearchCondition.EQUAL, ce), new int[] { classIdx });

		Kogger.debug(getClass(), "QuerySpec8==========>" + qs.toString());
	    }

	    if (buyerCodeStr.length() > 0) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(docuClass, "buyerCode"));
		ce = ConstantExpression.newExpression("%" + buyerCodeStr.toUpperCase() + "%");
		qs.appendWhere(new SearchCondition(sqlfunction, SearchCondition.LIKE, ce), new int[] { classIdx });

		Kogger.debug(getClass(), "QuerySpec9==========>" + qs.toString());
	    }

	    if (sortKey.equals("0")) {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(wt.doc.WTDocument.class, "title"), false), new int[] { 0 });
	    } else if (sortKey.equals("1")) {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(docuClass, "documentNo"), false), new int[] { 0 });
	    } else if (sortKey.equals("3")) {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(wt.doc.WTDocument.class, "title"), false), new int[] { 0 });
	    } else if (sortKey.equals("7")) {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(wt.fc.Persistable.class, "thePersistInfo.createStamp"), false), new int[] { 0 });
	    } else if (sortKey.equals("9")) {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(docuClass, "isBuyerSummit"), false), new int[] { 0 });
	    } else {
		qs.appendOrderBy(new OrderBy(new ClassAttribute(wt.doc.WTDocument.class, "title"), false), new int[] { 0 });
		qs.appendOrderBy(new OrderBy(new ClassAttribute(wt.fc.Persistable.class, "thePersistInfo.createStamp"), false), new int[] { 0 });
	    }

	    QueryResult qr = (QueryResult) PersistenceHelper.manager.find(qs);

	    docuOids = new Vector();
	    Object[] obj = null;
	    while (qr.hasMoreElements()) {

		obj = (Object[]) qr.nextElement();
		KETProjectDocument docu = (KETProjectDocument) obj[0];

		String docuOid = CommonUtil.getOIDString(docu);
		Kogger.debug(getClass(), "docu ===>" + docuOid);

		if (!authorStatus.equals("0")) {
		    authorStatus = "wt.lifecycle.State." + authorStatus;
		    Kogger.debug(getClass(), "authorStatus ===>" + authorStatus);
		    Kogger.debug(getClass(), "LifeCycleState ===>" + docu.getLifeCycleState().getStringValue());
		    if (docu.getLifeCycleState().getStringValue().equals(authorStatus)) {

			docuOids.addElement(docu);
		    }
		} else {
		    docuOids.addElement(docu);
		}
	    }
	} catch (WTPropertyVetoException wtpve) {

	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {

	    Kogger.error(getClass(), wte);
	} catch (Exception e) {

	    Kogger.error(getClass(), e);
	}

	return docuOids;

    }

    @Override
    public KETProjectDocument reviceProjectDoc(Hashtable hash) throws WTException {

	String docuOid = null;
	Vector partOids = null;
	String categoryCode = null;
	String categoryPath = null;
	KETProjectDocument d = null;
	Vector partNumber = null;
	String QpartNumber = "";
	String qualityNo = "";
	String uQualityNo = "";
	String analysisCode = null;
	String analysisCodeTxt = null;
	String costComment = null;
	String documentNo = null;
	int dRCheckPoint = 0;

	Transaction trx = new Transaction();
	trx.start();

	String numberingCode1 = "";
	String numberingCode2 = "";

	try {
	    docuOid = ((String) hash.get("docuOid")).trim();
	    Versioned newVs = null;
	    Versioned vs = (Versioned) CommonUtil.getObject(docuOid);

	    String lifecycle = ((LifeCycleManaged) vs).getLifeCycleName();
	    Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
	    newVs = VersionControlHelper.service.newVersion(vs);
	    FolderHelper.assignLocation((FolderEntry) newVs, folder);
	    d = (KETProjectDocument) newVs;

	    /*
	     * PDMLinkProduct wtProduct = null; WTContainerRef wtContainerRef = null; QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	     * SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	     * qs.appendSearchCondition(sc1); QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs); if
	     * (results.hasMoreElements()){ wtProduct = (PDMLinkProduct) results.nextElement(); wtContainerRef =
	     * WTContainerRef.newWTContainerRef(wtProduct); d.setContainer(wtProduct); LifeCycleHelper.setLifeCycle(d,
	     * LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, wtContainerRef)); //Lifecycle d =
	     * (KETProjectDocument)PersistenceHelper.manager.save(d); }else{ trx.rollback(); d = null; return d; }
	     */
	    // LifeCycleHelper.setLifeCycle((LifeCycleManaged) d, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,
	    // WCUtil.getWTContainerRef()));

	    LifeCycleHelper.setLifeCycle(d, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef())); // Lifecycle
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData peoData = new PeopleData(user);
	    String deptName = peoData.departmentName;
	    d.setDeptName(deptName);
	    d = (KETProjectDocument) PersistenceHelper.manager.save(d);

	    docuOid = CommonUtil.getOIDString(d);
	    Kogger.debug(getClass(), "reviced1 ProjectDoc docoid=====>" + docuOid);

	    KETDocumentPartLink DpLink;
	    // QueryResult r = PersistenceHelper.manager.navigate(d, "part", KETDocumentPartLink.class, false);
	    // while (r.hasMoreElements()) {
	    // DpLink = (KETDocumentPartLink) r.nextElement();
	    // PersistenceHelper.manager.delete(DpLink);
	    // }

	    KETDocumentProjectLink DPrLink;
	    // QueryResult r2 = PersistenceHelper.manager.navigate(d, "project", KETDocumentProjectLink.class, false);
	    // while (r2.hasMoreElements()) {
	    // DPrLink = (KETDocumentProjectLink) r2.nextElement();
	    // PersistenceHelper.manager.delete(DPrLink);
	    // }

	    KETDocumentOutputLink DoLink;
	    // QueryResult r3 = PersistenceHelper.manager.navigate(d, "output", KETDocumentOutputLink.class, false);
	    // if (r3.hasMoreElements()) {
	    // DoLink = (KETDocumentOutputLink) r3.nextElement();
	    // PersistenceHelper.manager.delete(DoLink);
	    // }

	    KETDocumentCategoryLink DCLink;
	    // DCLink = new KETDocumentCategoryLink();
	    // QueryResult r1 = PersistenceHelper.manager.navigate(d, "documentCategory", KETDocumentCategoryLink.class, false);
	    // if (r1.hasMoreElements()) {
	    // DCLink = (KETDocumentCategoryLink) r1.nextElement();
	    // PersistenceHelper.manager.delete(DCLink);
	    // }

	    documentNo = d.getDocumentNo();
	    d.setTitle((String) hash.get("documentName"));
	    d.setIsBuyerSummit((String) hash.get("isBuyerSummit"));
	    d.setBuyerCode((String) hash.get("buyerCode"));

	    dRCheckPoint = Integer.parseInt((String) hash.get("dRCheckPoint"));
	    if (dRCheckPoint > 0)
		d.setDRCheckPoint(dRCheckPoint);

	    d.setDocumentDescription(((String) hash.get("documentDescription")).trim());
	    d.setSecurity((String) hash.get("security"));

	    String isDeptACL = (String) hash.get("isDeptACL");
	    d.setAttribute7(isDeptACL);

	    if ("true".equals(isDeptACL)) {

		String devDeptCode = (String) hash.get("devDeptCode");
		if (StringUtil.checkString(devDeptCode) && devDeptCode.indexOf(Department.class.getName()) >= 0) {
		    Department dept = (Department) CommonUtil.getObject(devDeptCode);
		    devDeptCode = dept.getCode();
		}

		d.setAttribute4(devDeptCode);
		String dutyCode = (String) hash.get("duty");
		d.setAttribute5(dutyCode);

		if (StringUtil.checkString(dutyCode)) {
		    d.setAttribute6((String) CompanyState.dutyTable.get(dutyCode));
		}

	    } else {
		d.setAttribute4(null);
		d.setAttribute5(null);
		d.setAttribute6(null);
	    }

	    String pubDateYn = (String) hash.get("pubDateYn");
	    if ("Y".equals(pubDateYn)) {
		d.setAttribute8(pubDateYn);
		d.setAttribute9((String) hash.get("pubDate"));
		d.setAttribute10((String) hash.get("pubCycle"));
	    } else {
		d.setAttribute8(null);
		d.setAttribute9(null);
		d.setAttribute10(null);
	    }

	    categoryCode = (String) hash.get("categoryCode");
	    categoryPath = selectCategoryPath(categoryCode);

	    // 품질표준문서
	    uQualityNo = (String) hash.get("attribute1");
	    analysisCode = (String) hash.get("analysisCode");
	    analysisCodeTxt = (String) hash.get("analysisCodeTxt");

	    /*
	     * if ("".equals(uQualityNo)) { if (hash.get("partNumber") instanceof Vector) { partNumber = (Vector) hash.get("partNumber"); if
	     * (partNumber.size() > 0) { QpartNumber = (String) partNumber.get(0); } } else { QpartNumber = (String) hash.get("partNumber");
	     * }
	     * 
	     * if ("PD303066".equals(categoryCode)) { qualityNo = "DF-" + QpartNumber; } else if ("PD303072".equals(categoryCode)) {
	     * qualityNo = "PF-" + QpartNumber; } else if ("PD303001".equals(categoryCode)) { qualityNo = "KIS-" + QpartNumber; } else if
	     * ("PD303017".equals(categoryCode)) { qualityNo = "CP-" + QpartNumber; } else if ("PD303034".equals(categoryCode)) { qualityNo
	     * = "KEQ-" + QpartNumber; } else if ("PD303046".equals(categoryCode)) { qualityNo = "KETS-" + QpartNumber; } else if
	     * ("PD303047".equals(categoryCode)) { qualityNo = "PFC-" + QpartNumber; } else { qualityNo = ""; } } else { if
	     * ("PD303066".equals(categoryCode)) { qualityNo = "DF-" + uQualityNo; } else if ("PD303072".equals(categoryCode)) { qualityNo =
	     * "PF-" + uQualityNo; } else if ("PD303001".equals(categoryCode)) { qualityNo = "KIS-" + uQualityNo; } else if
	     * ("PD303017".equals(categoryCode)) { qualityNo = "CP-" + uQualityNo; } else if ("PD303034".equals(categoryCode)) { qualityNo =
	     * "KEQ-" + uQualityNo; } else if ("PD303046".equals(categoryCode)) { qualityNo = "KETS-" + uQualityNo; } else if
	     * ("PD303047".equals(categoryCode)) { qualityNo = "PFC-" + uQualityNo; } else { qualityNo = ""; } }
	     */

	    qualityNo = (String) hash.get("relatedPart");

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    if (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    numberingCode1 = docCate.getAttribute2();
	    numberingCode2 = docCate.getAttribute3();

	    if (hash.get("partOid") instanceof Vector) {

	    } else {
		d.setAttribute1(this.getQulityDocuRule(numberingCode1, numberingCode2, qualityNo));
	    }
	    // d.setAttribute1(qualityNo);
	    d.setAttribute2(analysisCode);
	    d.setAttribute3(analysisCodeTxt);

	    // Start [사업부에 따라 개정 권한을 제어하는 부분이 없어짐에 따라 개정시 로그인한 사용자의 소속사업부 정보로 업데이트] 전자사업부 박주미 요청 연구기획팀 합의 2015.06.29 by 황정태
	    String FromSessiondivisionCode = (CommonUtil.isMember("자동차사업부_영업") ? "CA" : (CommonUtil.isMember("전자사업부_영업") ? "ER" : null));
	    if (StringUtils.isNotEmpty(FromSessiondivisionCode)) {
		d.setDivisionCode(FromSessiondivisionCode);
	    }
	    // End [사업부에 따라 개정 권한을 제어하는 부분이 없어짐에 따라 개정시 로그인한 사용자의 소속사업부 정보로 업데이트] 전자사업부 박주미 요청 연구기획팀 합의 2015.06.29 by 황정태

	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setCostComment(costComment);
	    d = (KETProjectDocument) PersistenceHelper.manager.save(d);

	    if (hash.get("partOid") instanceof Vector) {
		ReferenceFactory rf = new ReferenceFactory();
		partOids = (Vector) hash.get("partOid");
		if (partOids.size() > 1) {
		    for (int i = 1; i < partOids.size(); i++) {
			String partOid = (String) partOids.get(i);
			WTPart wtpart = (WTPart) CommonUtil.getObject(partOid);
			DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
			wt.fc.PersistenceServerHelper.manager.insert(DpLink);
		    }
		}
	    }

	    Vector projectOids = null;
	    String projectOid = null;
	    if (hash.get("projectOid") instanceof Vector) {
		projectOids = (Vector) hash.get("projectOid");
		for (int i = 1; i < projectOids.size(); i++) {

		    String projOid = (String) projectOids.get(i);
		    E3PSProject pr = (E3PSProject) CommonUtil.getObject(projOid);

		    DPrLink = KETDocumentProjectLink.newKETDocumentProjectLink(d, pr);
		    wt.fc.PersistenceServerHelper.manager.insert(DPrLink);
		}
	    }
	    String outputOid = (String) hash.get("outputOid");
	    if (!outputOid.equals("0")) {
		ProjectOutput po = (ProjectOutput) CommonUtil.getObject(outputOid);

		DoLink = KETDocumentOutputLink.newKETDocumentOutputLink(d, po);
		wt.fc.PersistenceServerHelper.manager.insert(DoLink);
	    }

	    String divisionCode = d.getDivisionCode();

	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, WCUtil.getWTContainerRef());
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
	    }
	    FolderHelper.service.changeFolder((FolderEntry) d, cateFolder);

	    if (hash.get("partOid") instanceof Vector) {

	    } else {

		if (StringUtils.isNotEmpty(numberingCode1) && StringUtils.isEmpty(numberingCode2)) {

		    WTPart wtpart = PartBaseHelper.service.getLatestPart(qualityNo);

		    DpLink = KETDocumentPartLink.newKETDocumentPartLink(d, wtpart);
		    wt.fc.PersistenceServerHelper.manager.insert(DpLink);

		}
	    }

	    DCLink = new KETDocumentCategoryLink();
	    DCLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(docCate, d);
	    DCLink = (KETDocumentCategoryLink) PersistenceHelper.manager.save(DCLink);

	    String sfiles = (String) hash.get("isFileDel");
	    if (sfiles.equals("0")) {

	    } else {
		sfiles = sfiles.substring(2);
		StringTokenizer st = new StringTokenizer(sfiles, "/");
		while (st.hasMoreTokens()) {

		    String delsFile = st.nextToken();
		    Kogger.debug(getClass(), "delfilesI=====>" + delsFile);
		    ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
		    ContentInfo info = ContentUtil.getContentInfo((ContentHolder) d, ctf);
		    String delsFileName = info.getName();
		    Kogger.debug(getClass(), "delsFileName=====>" + delsFileName);

		    QueryResult rs = ContentHelper.service.getContentsByRole(d, ContentRoleType.SECONDARY);

		    while (rs.hasMoreElements()) {
			ContentItem ctf1 = (ContentItem) rs.nextElement();
			ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) d, ctf1);

			String itemStr = info1.getName();
			Kogger.debug(getClass(), "itemStr=====>" + itemStr);
			if (itemStr.equals(delsFileName)) {
			    d = (KETProjectDocument) E3PSContentHelper.service.delete(d, ctf1);
			}
		    }
		}
	    }

	    if (hash.get("files") instanceof Vector) {
		Vector files = (Vector) hash.get("files");
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {

			    if (d instanceof FormatContentHolder) {
				FormatContentHolder holder = (FormatContentHolder) d;
				ContentInfo info = ContentUtil.getPrimaryContent(holder);
				ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

				E3PSContentHelper.service.delete((ContentHolder) d, ctf);
			    }
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(d, file, "", isPrimary);
		    }
		}
	    }
	    documentNo = d.getDocumentNo();
	    Kogger.debug(getClass(), "updated ProjectDoc documentNo=====>" + documentNo);
	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

    @Override
    public KETDevelopmentRequest insertDevRequest(Hashtable hash) throws WTException {

	KETDevelopmentRequest dr = null;
	KETRequestPartList pl = null;
	KETRequestPartLink DPLink = null;
	KETCarYearlyAmount cy = null;
	KETPartCarLink PCLink = null;

	Timestamp tmpTime = null;
	Integer tmpInt = 0;

	String tmpStr;

	Transaction trx = new Transaction();
	trx.start();

	String flag = (String) hash.get("DevelopmentStep");

	try {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	    PeopleData peoData = new PeopleData(user);
	    String deptName = peoData.departmentName;
	    String requestNo = null;
	    String divisionCode = null;

	    dr = KETDevelopmentRequest.newKETDevelopmentRequest();

	    requestNo = StringUtil.checkNull((String) hash.get("DevelopmentStep"));

	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date currentTime = new java.util.Date();
	    String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
	    requestNo = requestNo + "-" + ymonth;

	    requestNo += ManageSequence.getSeqNo(requestNo, "000", "WTDocumentMaster", "WTDocumentNumber");

	    LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_DRR_LC", WCUtil.getPDMLinkProduct().getContainerReference());
	    LifeCycleHelper.setLifeCycle(dr, LCtemplate);

	    dr.setNumber(requestNo);
	    divisionCode = StringUtil.checkNull((String) hash.get("DivisionCode"));

	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }
	    String yyyy = formatter.format(currentTime).substring(0, 4);
	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(adRoot + "/" + yyyy, wtContainerRef);
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(aeRoot + "/" + yyyy, wtContainerRef);
	    } else {
		cateFolder = FolderHelper.service.getFolder(adRoot + "/" + yyyy, wtContainerRef);
	    }
	    FolderHelper.assignLocation((FolderEntry) dr, cateFolder);

	    dr.setName(StringUtil.checkNull((String) hash.get("DevProductName")));

	    dr.setDivisionCode(divisionCode);

	    dr.setReception(StringUtil.checkNull((String) hash.get("reception")));

	    dr.setDeptName(deptName);
	    dr.setDevelopmentStep(StringUtil.checkNull((String) hash.get("DevelopmentStep")));
	    dr.setProjectOID(StringUtil.checkNull((String) hash.get("ProjectOID")));

	    dr.setRequestBuyer(StringUtil.checkNull((String) hash.get("RequestBuyer")));
	    dr.setRequestBuyerManager(StringUtil.checkNull((String) hash.get("RequestBuyerManager")));
	    dr.setLastUsingBuyer(StringUtil.checkNull((String) hash.get("LastUsingBuyer")));
	    dr.setLastUsingBuyerManager(StringUtil.checkNull((String) hash.get("LastUsingBuyerManager")));

	    dr.setRepCarType(StringUtil.checkNull((String) hash.get("RepCarType")));

	    dr.setIsDRRequest(StringUtil.checkNull((String) hash.get("IsDRRequest")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("DrRequestDate")));
	    dr.setDrRequestDate(tmpTime);

	    dr.setIsProposalSubmit(StringUtil.checkNull((String) hash.get("IsProposalSubmit")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ProposalSubmitDate")));
	    dr.setProposalSubmitDate(tmpTime);
	    dr.setIsCostSubmit(StringUtil.checkNull((String) hash.get("IsCostSubmit")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("CostSubmitDate")));
	    dr.setCostSubmitDate(tmpTime);

	    dr.setDevProductName(StringUtil.checkNull((String) hash.get("DevProductName")));
	    dr.setDevReviewTypeCode(StringUtil.checkNull((String) hash.get("DevReviewTypeCode")));
	    dr.setDevReviewTypeEtc(StringUtil.checkNull((String) hash.get("DevReviewTypeEtc")));

	    dr.setDevReviewDetailComment(StringUtil.checkNull((String) hash.get("DevReviewDetailComment")));

	    dr.setProductSaleFirstYear(StringUtil.checkNull((String) hash.get("ProductSaleFirstYear")));

	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("InitialSampleSummitDate")));
	    dr.setInitialSampleSummitDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ESIRDate")));
	    dr.setESIRDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ISIRDate")));
	    dr.setISIRDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("KetMassProductionDate")));
	    dr.setKetMassProductionDate(tmpTime);

	    dr.setProductCategoryCode(StringUtil.checkNull((String) hash.get("ProductCategoryCode")));
	    dr.setProductTypeCode(StringUtil.checkNull((String) hash.get("ProductTypeCode")));

	    dr.setEtcSpecification(StringUtil.checkNull((String) hash.get("EtcSpecification")));
	    dr.setTabSize(StringUtil.checkNull((String) hash.get("TabSize")));
	    dr.setMaterialSubMaterial(StringUtil.checkNull((String) hash.get("MaterialSubMaterial")));
	    dr.setSurfaceTreatmentCode(StringUtil.checkNull((String) hash.get("SurfaceTreatmentCode")));
	    dr.setApplyedWire(StringUtil.checkNull((String) hash.get("ApplyedWire")));
	    dr.setPrimaryFunction(StringUtil.checkNull((String) hash.get("PrimaryFunction")));
	    dr.setOutlook(StringUtil.checkNull((String) hash.get("Outlook")));

	    dr.setMoldDepreciationTypeSale(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeSale")));
	    dr.setMoldDepreciationTypeGeneral(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeGeneral")));
	    dr.setMoldDepreciationTypePayment(StringUtil.checkNull((String) hash.get("MoldDepreciationTypePayment")));
	    dr.setMoldDepreciationTypePeriod(StringUtil.checkNull((String) hash.get("MoldDepreciationTypePeriod")));
	    dr.setMoldDepreciationTypeEtcInfo(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeEtcInfo")));

	    dr.setEquipDepreciationTypeSale(StringUtil.checkNull((String) hash.get("EquipDepreciationTypeSale")));
	    dr.setEquipDepreciationTypePayment(StringUtil.checkNull((String) hash.get("EquipDepreciationTypePayment")));
	    dr.setEquipDepreciationTypePeriod(StringUtil.checkNull((String) hash.get("EquipDepreciationTypePeriod")));
	    dr.setEquipDepreciationTypeEtcInfo(StringUtil.checkNull((String) hash.get("EquipDepreciationTypeEtcInfo")));

	    dr.setDeviceSpecification(StringUtil.checkNull((String) hash.get("DeviceSpecification")));
	    dr.setEnvironmentalRegulationItem(StringUtil.checkNull((String) hash.get("EnvironmentalRegulationItem")));
	    dr.setBuyerEtcRequirement(StringUtil.checkNull((String) hash.get("BuyerEtcRequirement")));
	    dr.setSalesAdditionalRequirement(StringUtil.checkNull((String) hash.get("SalesAdditionalRequirement")));
	    dr.setAttribute1(StringUtil.checkNull((String) hash.get("attribute1")));
	    dr.setAttribute2(StringUtil.checkNull((String) hash.get("attribute2")));
	    dr.setAttribute3(StringUtil.checkNull((String) hash.get("attribute3")));
	    dr.setAttribute4(StringUtil.checkNull((String) hash.get("attribute4")));
	    dr.setAttribute5(StringUtil.checkNull((String) hash.get("attribute5")));
	    dr.setAttribute6(StringUtil.checkNull((String) hash.get("attribute6")));
	    dr.setAttribute7(StringUtil.checkNull((String) hash.get("attribute7")));
	    dr.setAttribute8(StringUtil.checkNull((String) hash.get("attribute8")));
	    dr.setAttribute9(StringUtil.checkNull((String) hash.get("attribute9")));

	    dr.setAttribute10(StringUtil.checkNull((String) hash.get("attribute10")));
	    dr.setAttribute11(StringUtil.checkNull((String) hash.get("attribute11")));
	    dr.setScheduleName(StringUtil.checkNull((String) hash.get("scheduleName")));

	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("InitialSampleSummitDate2")));
	    dr.setInitialSampleSummitDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ESIRDate2")));
	    dr.setESIRDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ISIRDate2")));
	    dr.setISIRDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ProductSaleFirstYear2")));
	    dr.setProductSaleFirstYear2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("KetMassProductionDate2")));
	    dr.setKetMassProductionDate2(tmpTime);

	    dr = (KETDevelopmentRequest) PersistenceHelper.manager.save(dr);

	    Vector tId = null;
	    Vector PartName = null;
	    Vector AppliedRegion = null;
	    Vector CurrentApplyedPart = null;
	    Vector PackTypeCode = null;
	    Vector SummitDestination = null;
	    Vector BuyerAcceptPrice = null;
	    Vector KetTargetPrice = null;
	    Vector TargetEarningRate = null;
	    Vector TargetAverageRate = null;
	    Vector TargetInvestmentCost = null;
	    Vector IsEtcCarType = null;
	    Vector TargetTermRate = null;

	    Vector tId1 = null;
	    Vector carTypeCode = null;
	    Vector carType = null;
	    Vector yearAmount1 = null;
	    Vector yearAmount2 = null;
	    Vector yearAmount3 = null;
	    Vector yearAmount4 = null;
	    Vector yearAmount5 = null;
	    Vector yearAmount6 = null;
	    Vector applyedUsage = null;
	    Vector optionRate = null;

	    if (hash.get("tId") instanceof Vector) {
		tId = (Vector) hash.get("tId");
		PartName = (Vector) hash.get("partName");
		AppliedRegion = (Vector) hash.get("appliedRegion");
		CurrentApplyedPart = (Vector) hash.get("currentApplyPart");
		PackTypeCode = (Vector) hash.get("PackTypeCode");
		SummitDestination = (Vector) hash.get("summitDestination");
		BuyerAcceptPrice = (Vector) hash.get("buyerAcceptPrice");
		KetTargetPrice = (Vector) hash.get("ketTargetPrice");
		TargetEarningRate = (Vector) hash.get("targetEarningRate");
		if ("D".equals(flag)) {
		    TargetAverageRate = (Vector) hash.get("targetAverageRate");
		}
		TargetInvestmentCost = (Vector) hash.get("targetInvestmentCost");
		TargetTermRate = (Vector) hash.get("targetTermRate");
		// IsEtcCarType = (Vector)hash.get("IsEtcCarType");

		tId1 = (Vector) hash.get("tId1");
		carTypeCode = (Vector) hash.get("carTypeCode");
		carType = (Vector) hash.get("carTypeOid");
		yearAmount1 = (Vector) hash.get("y1");
		yearAmount2 = (Vector) hash.get("y2");
		yearAmount3 = (Vector) hash.get("y3");
		yearAmount4 = (Vector) hash.get("y4");
		yearAmount5 = (Vector) hash.get("y5");
		yearAmount6 = (Vector) hash.get("y6");
		applyedUsage = (Vector) hash.get("usage");
		optionRate = (Vector) hash.get("optRate");

		String sBuyerAcceptPrice = "";
		String sKetTargetPrice = "";
		String sTargetEarningRate = "";
		String sTargetAverageRate = "";
		String sTargetInvestmentCost = "";
		String sTargetTermRate = "";

		for (int i = 0; i < tId.size(); i++) {

		    pl = KETRequestPartList.newKETRequestPartList();

		    pl.setPartName((String) PartName.get(i));

		    pl.setAppliedRegion((String) AppliedRegion.get(i));
		    pl.setCurrentApplyedPart((String) CurrentApplyedPart.get(i));
		    pl.setPackTypeCode((String) PackTypeCode.get(i));
		    pl.setSummitDestination((String) SummitDestination.get(i));

		    sBuyerAcceptPrice = StringUtil.checkNull((String) BuyerAcceptPrice.get(i));
		    if (sBuyerAcceptPrice.equals(""))
			sBuyerAcceptPrice = "0";
		    sBuyerAcceptPrice = StringUtils.remove(sBuyerAcceptPrice, ",");
		    pl.setBuyerAcceptPrice(sBuyerAcceptPrice);

		    sKetTargetPrice = StringUtil.checkNull((String) KetTargetPrice.get(i));
		    if (sKetTargetPrice.equals(""))
			sKetTargetPrice = "0";
		    sKetTargetPrice = StringUtils.remove(sKetTargetPrice, ",");
		    pl.setKetTargetPrice(Double.valueOf(sKetTargetPrice).doubleValue());

		    sTargetEarningRate = StringUtil.checkNull((String) TargetEarningRate.get(i));
		    if (sTargetEarningRate.equals(""))
			sTargetEarningRate = "0";
		    sTargetEarningRate = StringUtils.remove(sTargetEarningRate, ",");
		    pl.setTargetEarningRate(Double.valueOf(sTargetEarningRate).doubleValue());

		    if ("D".equals(flag)) {
			sTargetAverageRate = StringUtil.checkNull((String) TargetAverageRate.get(i));
			if (sTargetAverageRate.equals(""))
			    sTargetAverageRate = "0";
			sTargetAverageRate = StringUtils.remove(sTargetAverageRate, ",");
			pl.setTargetAverageRate(Double.valueOf(sTargetAverageRate).doubleValue());
		    }

		    sTargetInvestmentCost = StringUtil.checkNull((String) TargetInvestmentCost.get(i));
		    if (sTargetInvestmentCost.equals(""))
			sTargetInvestmentCost = "0";
		    sTargetInvestmentCost = StringUtils.remove(sTargetInvestmentCost, ",");
		    pl.setTargetInvestmentCost(Double.valueOf(sTargetInvestmentCost).doubleValue());

		    sTargetTermRate = StringUtil.checkNull((String) TargetTermRate.get(i));
		    if (sTargetTermRate.equals(""))
			sTargetTermRate = "0";
		    sTargetTermRate = StringUtils.remove(sTargetTermRate, ",");
		    pl.setTargetTermRate(Double.valueOf(sTargetTermRate).doubleValue());

		    // pl.setIsEtcCarType((String)IsEtcCarType.get(i));

		    pl = (KETRequestPartList) PersistenceHelper.manager.save(pl);

		    DPLink = KETRequestPartLink.newKETRequestPartLink(dr, pl);
		    wt.fc.PersistenceServerHelper.manager.insert(DPLink);
		    // DPLink = (KETRequestPartLink)PersistenceHelper.manager.save(DPLink);

		    tmpStr = (String) tId.get(i);
		    for (int j = 0; j < tId1.size(); j++) {
			if (tmpStr.equals((String) tId1.get(j))) {
			    cy = KETCarYearlyAmount.newKETCarYearlyAmount();

			    cy.setCarTypeCode((String) carTypeCode.get(j));
			    cy.setCarType((String) carType.get(j));

			    cy.setYearAmount1(Double.valueOf((String) yearAmount1.get(j)).doubleValue());
			    cy.setYearAmount2(Double.valueOf((String) yearAmount2.get(j)).doubleValue());
			    cy.setYearAmount3(Double.valueOf((String) yearAmount3.get(j)).doubleValue());
			    cy.setYearAmount4(Double.valueOf((String) yearAmount4.get(j)).doubleValue());
			    cy.setYearAmount5(Double.valueOf((String) yearAmount5.get(j)).doubleValue());
			    cy.setYearAmount6(Double.valueOf((String) yearAmount6.get(j)).doubleValue());
			    cy.setApplyedUsage(Double.valueOf((String) applyedUsage.get(j)).doubleValue());
			    cy.setOptionRate(Double.valueOf((String) optionRate.get(j)).doubleValue());

			    cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			    PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			    wt.fc.PersistenceServerHelper.manager.insert(PCLink);
			}
		    }
		}
	    } else {

		String stId = (String) hash.get("tId");
		String sPartName = (String) hash.get("partName");
		String sAppliedRegion = (String) hash.get("appliedRegion");
		String sCurrentApplyedPart = (String) hash.get("currentApplyPart");
		String sPackTypeCode = (String) hash.get("PackTypeCode");
		String sSummitDestination = (String) hash.get("summitDestination");

		String sBuyerAcceptPrice = (String) hash.get("buyerAcceptPrice");
		if (sBuyerAcceptPrice.equals(""))
		    sBuyerAcceptPrice = "0";
		sBuyerAcceptPrice = StringUtils.remove(sBuyerAcceptPrice, ",");

		String sketTargetPrice = StringUtil.checkNull((String) hash.get("ketTargetPrice"));
		if (sketTargetPrice.equals(""))
		    sketTargetPrice = "0";

		sketTargetPrice = StringUtils.remove(sketTargetPrice, ",");
		Double sKetTargetPrice = Double.valueOf(sketTargetPrice).doubleValue();

		String stargetEarningRate = StringUtil.checkNull((String) hash.get("targetEarningRate"));
		if (stargetEarningRate.equals(""))
		    stargetEarningRate = "0";
		stargetEarningRate = StringUtils.remove(stargetEarningRate, ",");
		Double sTargetEarningRate = Double.valueOf(stargetEarningRate).doubleValue();

		Double sTargetAverageRate = null;
		if ("D".equals(flag)) {
		    String stargetAverageRate = StringUtil.checkNull((String) hash.get("targetAverageRate"));
		    if (stargetAverageRate.equals(""))
			stargetAverageRate = "0";
		    stargetAverageRate = StringUtils.remove(stargetAverageRate, ",");
		    sTargetAverageRate = Double.valueOf(stargetAverageRate).doubleValue();
		}

		String stargetInvestmentCost = StringUtil.checkNull((String) hash.get("targetInvestmentCost"));
		if (stargetInvestmentCost.equals(""))
		    stargetInvestmentCost = "0";
		stargetInvestmentCost = StringUtils.remove(stargetInvestmentCost, ",");
		Double sTargetInvestmentCost = Double.valueOf(stargetInvestmentCost).doubleValue();

		String stargetTermRate = StringUtil.checkNull((String) hash.get("targetTermRate"));
		if (stargetTermRate.equals(""))
		    stargetTermRate = "0";
		stargetTermRate = StringUtils.remove(stargetTermRate, ",");
		Double sTargetTermRate = Double.valueOf(stargetTermRate).doubleValue();

		pl = KETRequestPartList.newKETRequestPartList();

		pl.setPartName(sPartName);
		pl.setAppliedRegion(sAppliedRegion);
		pl.setCurrentApplyedPart(sCurrentApplyedPart);
		pl.setPackTypeCode(sPackTypeCode);
		pl.setSummitDestination(sSummitDestination);
		pl.setBuyerAcceptPrice(sBuyerAcceptPrice);
		pl.setKetTargetPrice(sKetTargetPrice);
		pl.setTargetEarningRate(sTargetEarningRate);
		pl.setTargetAverageRate(sTargetAverageRate);
		pl.setTargetInvestmentCost(sTargetInvestmentCost);
		pl.setTargetTermRate(sTargetTermRate);

		pl = (KETRequestPartList) PersistenceHelper.manager.save(pl);

		DPLink = KETRequestPartLink.newKETRequestPartLink(dr, pl);
		wt.fc.PersistenceServerHelper.manager.insert(DPLink);

		if (hash.get("tId1") instanceof Vector) {
		    tId1 = (Vector) hash.get("tId1");
		    carTypeCode = (Vector) hash.get("carTypeCode");
		    carType = (Vector) hash.get("carTypeOid");
		    yearAmount1 = (Vector) hash.get("y1");
		    yearAmount2 = (Vector) hash.get("y2");
		    yearAmount3 = (Vector) hash.get("y3");
		    yearAmount4 = (Vector) hash.get("y4");
		    yearAmount5 = (Vector) hash.get("y5");
		    yearAmount6 = (Vector) hash.get("y6");
		    applyedUsage = (Vector) hash.get("usage");
		    optionRate = (Vector) hash.get("optRate");
		    for (int i = 0; i < tId1.size(); i++) {
			if (stId.equals((String) tId1.get(i))) {
			    cy = KETCarYearlyAmount.newKETCarYearlyAmount();
			    cy.setCarTypeCode((String) carTypeCode.get(i));
			    cy.setCarType((String) carType.get(i));
			    cy.setYearAmount1(Double.valueOf((String) yearAmount1.get(i)).doubleValue());
			    cy.setYearAmount2(Double.valueOf((String) yearAmount2.get(i)).doubleValue());
			    cy.setYearAmount3(Double.valueOf((String) yearAmount3.get(i)).doubleValue());
			    cy.setYearAmount4(Double.valueOf((String) yearAmount4.get(i)).doubleValue());
			    cy.setYearAmount5(Double.valueOf((String) yearAmount5.get(i)).doubleValue());
			    cy.setYearAmount6(Double.valueOf((String) yearAmount6.get(i)).doubleValue());
			    cy.setApplyedUsage(Double.valueOf((String) applyedUsage.get(i)).doubleValue());
			    cy.setOptionRate(Double.valueOf((String) optionRate.get(i)).doubleValue());
			    cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			    PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			    wt.fc.PersistenceServerHelper.manager.insert(PCLink);
			}
		    }
		} else {
		    String stId1 = (String) hash.get("tId1");
		    String scarTypeCode = (String) hash.get("carTypeCode");
		    String scarType = (String) hash.get("carTypeOid");
		    Double syearAmount1 = Double.valueOf((String) hash.get("y1")).doubleValue();
		    Double syearAmount2 = Double.valueOf((String) hash.get("y2")).doubleValue();
		    Double syearAmount3 = Double.valueOf((String) hash.get("y3")).doubleValue();
		    Double syearAmount4 = Double.valueOf((String) hash.get("y4")).doubleValue();
		    Double syearAmount5 = Double.valueOf((String) hash.get("y5")).doubleValue();
		    Double syearAmount6 = Double.valueOf((String) hash.get("y6")).doubleValue();
		    Double sapplyedUsage = Double.valueOf((String) hash.get("usage")).doubleValue();
		    Double soptionRate = Double.valueOf((String) hash.get("optRate")).doubleValue();

		    if (stId.equals(stId1)) {
			cy = KETCarYearlyAmount.newKETCarYearlyAmount();

			cy.setCarTypeCode(scarTypeCode);
			cy.setCarType(scarType);
			cy.setYearAmount1(syearAmount1);
			cy.setYearAmount2(syearAmount2);
			cy.setYearAmount3(syearAmount3);
			cy.setYearAmount4(syearAmount4);
			cy.setYearAmount5(syearAmount5);
			cy.setYearAmount6(syearAmount6);
			cy.setApplyedUsage(sapplyedUsage);
			cy.setOptionRate(soptionRate);

			cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			wt.fc.PersistenceServerHelper.manager.insert(PCLink);
		    }
		}
	    }

	    String relatedSalesProjectOid = StringUtil.checkNull((String) hash.get("relatedSalesProjectOid"));
	    if (StringUtils.isNotEmpty(relatedSalesProjectOid)) {
		KETSalesProject salesProject = (KETSalesProject) CommonUtil.getObject(relatedSalesProjectOid);
		salesProject.setDevRequest(dr);
		// Iteration 증가 없이 속성 수정
		PersistenceServerHelper.manager.update(salesProject);

		salesProject = (KETSalesProject) PersistenceHelper.manager.refresh(salesProject);
	    }

	    trx.commit();
	    Kogger.debug(getClass(), "insertDevRequest drNo=====>" + dr.getDevProductName());

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), e);
	}

	return dr;

    }

    @Override
    public KETDevelopmentRequest updateDevRequest(Hashtable hash) throws WTException {

	KETDevelopmentRequest dr = null;
	KETRequestPartList pl = null;
	KETRequestPartLink DPLink = null;
	KETCarYearlyAmount cy = null;
	KETPartCarLink PCLink = null;

	Timestamp tmpTime = null;
	Integer tmpInt = 0;

	String tmpStr;
	String drOid;
	Hashtable drHash;

	Transaction trx = new Transaction();
	trx.start();

	String flag = (String) hash.get("DevelopmentStep");

	try {

	    drOid = (String) hash.get("drOid");
	    dr = (KETDevelopmentRequest) CommonUtil.getObject(drOid);
	    tmpStr = dr.getNumber();

	    QueryResult r = PersistenceHelper.manager.navigate(dr, "RequestPart", KETRequestPartLink.class);
	    while (r.hasMoreElements()) {
		pl = (KETRequestPartList) r.nextElement();
		QueryResult r1 = PersistenceHelper.manager.navigate(pl, "CarAmoumt", KETPartCarLink.class);
		while (r1.hasMoreElements()) {
		    cy = (KETCarYearlyAmount) r1.nextElement();
		    Kogger.debug(getClass(), "delete CarTypeCode 1 ===>" + cy.getCarTypeCode());
		    PersistenceHelper.manager.delete(cy);
		}
		Kogger.debug(getClass(), "delete partName 1 ===>" + pl.getPartName());
		PersistenceHelper.manager.delete(pl);
	    }

	    // drHash = KETObjectUtil.checkOut(drOid);
	    // dr = (KETDevelopmentRequest) drHash.get("WorkableObject");

	    if ("D".equals(flag)) {// 개발의뢰서의 경우 연계된 제품프로젝트의 개발의뢰 oid를 update해준다 (원가요청서 정합성 유지목적)

		QueryResult qrPrj = ProjectHelper.getDevRequestProject(dr);
		if (qrPrj != null && qrPrj.size() > 0) {
		    if (qrPrj.hasMoreElements()) {
			Object[] objArr2 = (Object[]) qrPrj.nextElement();
			E3PSProject proj = (E3PSProject) objArr2[0];

			proj.setDevRequest(dr);
			proj.setDevRequestNo(dr.getNumber());
			PersistenceHelper.manager.save(proj);

		    }
		}
	    }
	    dr.setProjectOID(StringUtil.checkNull((String) hash.get("ProjectOID")));
	    dr.setReception(StringUtil.checkNull((String) hash.get("reception")));
	    dr.setRequestBuyer(StringUtil.checkNull((String) hash.get("RequestBuyer")));
	    dr.setRequestBuyerManager(StringUtil.checkNull((String) hash.get("RequestBuyerManager")));
	    dr.setLastUsingBuyer(StringUtil.checkNull((String) hash.get("LastUsingBuyer")));
	    dr.setLastUsingBuyerManager(StringUtil.checkNull((String) hash.get("LastUsingBuyerManager")));

	    dr.setRepCarType(StringUtil.checkNull((String) hash.get("RepCarType")));

	    dr.setIsDRRequest(StringUtil.checkNull((String) hash.get("IsDRRequest")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("DrRequestDate")));
	    dr.setDrRequestDate(tmpTime);

	    dr.setIsProposalSubmit(StringUtil.checkNull((String) hash.get("IsProposalSubmit")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ProposalSubmitDate")));
	    dr.setProposalSubmitDate(tmpTime);
	    dr.setIsCostSubmit(StringUtil.checkNull((String) hash.get("IsCostSubmit")));
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("CostSubmitDate")));
	    dr.setCostSubmitDate(tmpTime);

	    dr.setDevProductName(StringUtil.checkNull((String) hash.get("DevProductName")));
	    dr.setDevReviewTypeCode(StringUtil.checkNull((String) hash.get("DevReviewTypeCode")));
	    dr.setDevReviewTypeEtc(StringUtil.checkNull((String) hash.get("DevReviewTypeEtc")));
	    dr.setDevReviewDetailComment(StringUtil.checkNull((String) hash.get("DevReviewDetailComment")));

	    dr.setProductSaleFirstYear(StringUtil.checkNull((String) hash.get("ProductSaleFirstYear")));

	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("InitialSampleSummitDate")));
	    dr.setInitialSampleSummitDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ESIRDate")));
	    dr.setESIRDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ISIRDate")));
	    dr.setISIRDate(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("KetMassProductionDate")));
	    dr.setKetMassProductionDate(tmpTime);

	    dr.setProductCategoryCode(StringUtil.checkNull((String) hash.get("ProductCategoryCode")));
	    dr.setProductTypeCode(StringUtil.checkNull((String) hash.get("ProductTypeCode")));

	    dr.setEtcSpecification(StringUtil.checkNull((String) hash.get("EtcSpecification")));
	    dr.setTabSize(StringUtil.checkNull((String) hash.get("TabSize")));
	    dr.setMaterialSubMaterial(StringUtil.checkNull((String) hash.get("MaterialSubMaterial")));
	    dr.setSurfaceTreatmentCode(StringUtil.checkNull((String) hash.get("SurfaceTreatmentCode")));
	    dr.setApplyedWire(StringUtil.checkNull((String) hash.get("ApplyedWire")));
	    dr.setPrimaryFunction(StringUtil.checkNull((String) hash.get("PrimaryFunction")));
	    dr.setOutlook(StringUtil.checkNull((String) hash.get("Outlook")));

	    dr.setMoldDepreciationTypeSale(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeSale")));
	    dr.setMoldDepreciationTypeGeneral(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeGeneral")));
	    dr.setMoldDepreciationTypePayment(StringUtil.checkNull((String) hash.get("MoldDepreciationTypePayment")));
	    dr.setMoldDepreciationTypePeriod(StringUtil.checkNull((String) hash.get("MoldDepreciationTypePeriod")));
	    dr.setMoldDepreciationTypeEtcInfo(StringUtil.checkNull((String) hash.get("MoldDepreciationTypeEtcInfo")));

	    dr.setEquipDepreciationTypeSale(StringUtil.checkNull((String) hash.get("EquipDepreciationTypeSale")));
	    dr.setEquipDepreciationTypePayment(StringUtil.checkNull((String) hash.get("EquipDepreciationTypePayment")));
	    dr.setEquipDepreciationTypePeriod(StringUtil.checkNull((String) hash.get("EquipDepreciationTypePeriod")));
	    dr.setEquipDepreciationTypeEtcInfo(StringUtil.checkNull((String) hash.get("EquipDepreciationTypeEtcInfo")));

	    dr.setDeviceSpecification(StringUtil.checkNull((String) hash.get("DeviceSpecification")));
	    dr.setEnvironmentalRegulationItem(StringUtil.checkNull((String) hash.get("EnvironmentalRegulationItem")));
	    dr.setBuyerEtcRequirement(StringUtil.checkNull((String) hash.get("BuyerEtcRequirement")));
	    dr.setSalesAdditionalRequirement(StringUtil.checkNull((String) hash.get("SalesAdditionalRequirement")));
	    dr.setAttribute1(StringUtil.checkNull((String) hash.get("attribute1")));
	    dr.setAttribute2(StringUtil.checkNull((String) hash.get("attribute2")));
	    dr.setAttribute3(StringUtil.checkNull((String) hash.get("attribute3")));
	    dr.setAttribute4(StringUtil.checkNull((String) hash.get("attribute4")));
	    dr.setAttribute5(StringUtil.checkNull((String) hash.get("attribute5")));
	    dr.setAttribute6(StringUtil.checkNull((String) hash.get("attribute6")));
	    dr.setAttribute7(StringUtil.checkNull((String) hash.get("attribute7")));
	    dr.setAttribute8(StringUtil.checkNull((String) hash.get("attribute8")));
	    dr.setAttribute9(StringUtil.checkNull((String) hash.get("attribute9")));

	    dr.setAttribute10(StringUtil.checkNull((String) hash.get("attribute10")));
	    dr.setAttribute11(StringUtil.checkNull((String) hash.get("attribute11")));
	    dr.setScheduleName(StringUtil.checkNull((String) hash.get("scheduleName")));

	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("InitialSampleSummitDate2")));
	    dr.setInitialSampleSummitDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ESIRDate2")));
	    dr.setESIRDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ISIRDate2")));
	    dr.setISIRDate2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("ProductSaleFirstYear2")));
	    dr.setProductSaleFirstYear2(tmpTime);
	    tmpTime = DateUtil.convertDate2(StringUtil.checkNull((String) hash.get("KetMassProductionDate2")));
	    dr.setKetMassProductionDate2(tmpTime);
	    // dr = (KETDevelopmentRequest) PersistenceHelper.manager.save(dr);
	    // Kogger.debug(getClass(), "12----->");

	    Vector tId = null;
	    Vector PartName = null;
	    Vector AppliedRegion = null;
	    Vector CurrentApplyedPart = null;
	    Vector PackTypeCode = null;
	    Vector SummitDestination = null;
	    Vector BuyerAcceptPrice = null;
	    Vector KetTargetPrice = null;
	    Vector TargetEarningRate = null;
	    Vector TargetAverageRate = null;
	    Vector TargetInvestmentCost = null;
	    Vector IsEtcCarType = null;
	    Vector TargetTermRate = null;

	    Vector tId1 = null;
	    Vector carTypeCode = null;
	    Vector carType = null;
	    Vector yearAmount1 = null;
	    Vector yearAmount2 = null;
	    Vector yearAmount3 = null;
	    Vector yearAmount4 = null;
	    Vector yearAmount5 = null;
	    Vector yearAmount6 = null;
	    Vector applyedUsage = null;
	    Vector optionRate = null;

	    if (hash.get("tId") instanceof Vector) {
		tId = (Vector) hash.get("tId");
		PartName = (Vector) hash.get("partName");
		AppliedRegion = (Vector) hash.get("appliedRegion");
		CurrentApplyedPart = (Vector) hash.get("currentApplyPart");
		PackTypeCode = (Vector) hash.get("PackTypeCode");
		SummitDestination = (Vector) hash.get("summitDestination");
		BuyerAcceptPrice = (Vector) hash.get("buyerAcceptPrice");
		KetTargetPrice = (Vector) hash.get("ketTargetPrice");
		TargetEarningRate = (Vector) hash.get("targetEarningRate");
		if ("D".equals(flag)) {
		    TargetAverageRate = (Vector) hash.get("targetAverageRate");
		}
		TargetInvestmentCost = (Vector) hash.get("targetInvestmentCost");
		TargetTermRate = (Vector) hash.get("targetTermRate");
		// IsEtcCarType = (Vector)hash.get("IsEtcCarType");

		tId1 = (Vector) hash.get("tId1");
		carTypeCode = (Vector) hash.get("carTypeCode");
		carType = (Vector) hash.get("carTypeOid");
		yearAmount1 = (Vector) hash.get("y1");
		yearAmount2 = (Vector) hash.get("y2");
		yearAmount3 = (Vector) hash.get("y3");
		yearAmount4 = (Vector) hash.get("y4");
		yearAmount5 = (Vector) hash.get("y5");
		yearAmount6 = (Vector) hash.get("y6");
		applyedUsage = (Vector) hash.get("usage");
		optionRate = (Vector) hash.get("optRate");

		String sBuyerAcceptPrice = "";
		String sKetTargetPrice = "";
		String sTargetEarningRate = "";
		String sTargetAverageRate = "";
		String sTargetInvestmentCost = "";
		String sTargetTermRate = "";

		for (int i = 0; i < tId.size(); i++) {
		    pl = KETRequestPartList.newKETRequestPartList();

		    pl.setPartName((String) PartName.get(i));
		    pl.setAppliedRegion((String) AppliedRegion.get(i));
		    pl.setCurrentApplyedPart((String) CurrentApplyedPart.get(i));
		    pl.setPackTypeCode((String) PackTypeCode.get(i));
		    pl.setSummitDestination((String) SummitDestination.get(i));

		    sBuyerAcceptPrice = StringUtil.checkNull((String) BuyerAcceptPrice.get(i));
		    if (sBuyerAcceptPrice.equals(""))
			sBuyerAcceptPrice = "0";
		    sBuyerAcceptPrice = StringUtils.remove(sBuyerAcceptPrice, ",");
		    pl.setBuyerAcceptPrice(sBuyerAcceptPrice);

		    sKetTargetPrice = StringUtil.checkNull((String) KetTargetPrice.get(i));
		    if (sKetTargetPrice.equals(""))
			sKetTargetPrice = "0";
		    sKetTargetPrice = StringUtils.remove(sKetTargetPrice, ",");
		    pl.setKetTargetPrice(Double.valueOf(sKetTargetPrice).doubleValue());
		    sTargetEarningRate = StringUtil.checkNull((String) TargetEarningRate.get(i));
		    if (sTargetEarningRate.equals(""))
			sTargetEarningRate = "0";
		    sTargetEarningRate = StringUtils.remove(sTargetEarningRate, ",");
		    pl.setTargetEarningRate(Double.valueOf(sTargetEarningRate).doubleValue());

		    if ("D".equals(flag)) {
			sTargetAverageRate = StringUtil.checkNull((String) TargetAverageRate.get(i));
			if (sTargetAverageRate.equals("")) {
			    sTargetAverageRate = "0";
			}
			sTargetAverageRate = StringUtils.remove(sTargetAverageRate, ",");
			pl.setTargetAverageRate(Double.valueOf(sTargetAverageRate).doubleValue());
		    }

		    sTargetInvestmentCost = StringUtil.checkNull((String) TargetInvestmentCost.get(i));
		    if (sTargetInvestmentCost.equals(""))
			sTargetInvestmentCost = "0";
		    sTargetInvestmentCost = StringUtils.remove(sTargetInvestmentCost, ",");
		    pl.setTargetInvestmentCost(Double.valueOf(sTargetInvestmentCost).doubleValue());

		    sTargetTermRate = StringUtil.checkNull((String) TargetTermRate.get(i));
		    if (sTargetTermRate.equals(""))
			sTargetTermRate = "0";
		    sTargetTermRate = StringUtils.remove(sTargetTermRate, ",");
		    pl.setTargetTermRate(Double.valueOf(sTargetTermRate).doubleValue());

		    // pl.setIsEtcCarType((String)IsEtcCarType.get(i));

		    pl = (KETRequestPartList) PersistenceHelper.manager.save(pl);

		    DPLink = KETRequestPartLink.newKETRequestPartLink(dr, pl);
		    wt.fc.PersistenceServerHelper.manager.insert(DPLink);
		    // DPLink = (KETRequestPartLink)PersistenceHelper.manager.save(DPLink);

		    tmpStr = (String) tId.get(i);
		    for (int j = 0; j < tId1.size(); j++) {
			if (tmpStr.equals((String) tId1.get(j))) {

			    cy = KETCarYearlyAmount.newKETCarYearlyAmount();

			    cy.setCarTypeCode((String) carTypeCode.get(j));
			    cy.setCarType((String) carType.get(j));

			    cy.setYearAmount1(Double.valueOf((String) yearAmount1.get(j)).doubleValue());
			    cy.setYearAmount2(Double.valueOf((String) yearAmount2.get(j)).doubleValue());
			    cy.setYearAmount3(Double.valueOf((String) yearAmount3.get(j)).doubleValue());
			    cy.setYearAmount4(Double.valueOf((String) yearAmount4.get(j)).doubleValue());
			    cy.setYearAmount5(Double.valueOf((String) yearAmount5.get(j)).doubleValue());
			    cy.setYearAmount6(Double.valueOf((String) yearAmount6.get(j)).doubleValue());
			    cy.setApplyedUsage(Double.valueOf((String) applyedUsage.get(j)).doubleValue());
			    cy.setOptionRate(Double.valueOf((String) optionRate.get(j)).doubleValue());

			    cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			    PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			    wt.fc.PersistenceServerHelper.manager.insert(PCLink);
			}
		    }
		}
	    } else {

		String stId = (String) hash.get("tId");
		String sPartName = (String) hash.get("partName");
		String sAppliedRegion = (String) hash.get("appliedRegion");
		String sCurrentApplyedPart = (String) hash.get("currentApplyPart");
		String sPackTypeCode = (String) hash.get("PackTypeCode");
		String sSummitDestination = (String) hash.get("summitDestination");

		String sBuyerAcceptPrice = (String) hash.get("buyerAcceptPrice");
		if (sBuyerAcceptPrice.equals(""))
		    sBuyerAcceptPrice = "0";
		sBuyerAcceptPrice = StringUtils.remove(sBuyerAcceptPrice, ",");
		String sketTargetPrice = StringUtil.checkNull((String) hash.get("ketTargetPrice"));
		if (sketTargetPrice.equals(""))
		    sketTargetPrice = "0";
		sketTargetPrice = StringUtils.remove(sketTargetPrice, ",");
		Double sKetTargetPrice = Double.valueOf(sketTargetPrice).doubleValue();

		String stargetEarningRate = StringUtil.checkNull((String) hash.get("targetEarningRate"));
		if (stargetEarningRate.equals(""))
		    stargetEarningRate = "0";
		stargetEarningRate = StringUtils.remove(stargetEarningRate, ",");
		Double sTargetEarningRate = Double.valueOf(stargetEarningRate).doubleValue();

		Double sTargetAverageRate = null;
		if ("D".equals(flag)) {
		    String stargetAverageRate = StringUtil.checkNull((String) hash.get("targetAverageRate"));
		    if (stargetAverageRate.equals(""))
			stargetAverageRate = "0";
		    stargetAverageRate = StringUtils.remove(stargetAverageRate, ",");
		    sTargetAverageRate = Double.valueOf(stargetAverageRate).doubleValue();
		}

		String stargetInvestmentCost = StringUtil.checkNull((String) hash.get("targetInvestmentCost"));
		if (stargetInvestmentCost.equals(""))
		    stargetInvestmentCost = "0";
		stargetInvestmentCost = StringUtils.remove(stargetInvestmentCost, ",");
		Double sTargetInvestmentCost = Double.valueOf(stargetInvestmentCost).doubleValue();

		String stargetTermRate = StringUtil.checkNull((String) hash.get("targetTermRate"));
		if (stargetTermRate.equals(""))
		    stargetTermRate = "0";
		stargetTermRate = StringUtils.remove(stargetTermRate, ",");
		Double sTargetTermRate = Double.valueOf(stargetTermRate).doubleValue();

		pl = KETRequestPartList.newKETRequestPartList();

		pl.setPartName(sPartName);
		pl.setAppliedRegion(sAppliedRegion);
		pl.setCurrentApplyedPart(sCurrentApplyedPart);
		pl.setPackTypeCode(sPackTypeCode);
		pl.setSummitDestination(sSummitDestination);
		pl.setBuyerAcceptPrice(sBuyerAcceptPrice);
		pl.setKetTargetPrice(sKetTargetPrice);
		pl.setTargetEarningRate(sTargetEarningRate);
		pl.setTargetAverageRate(sTargetAverageRate);
		pl.setTargetInvestmentCost(sTargetInvestmentCost);
		pl.setTargetTermRate(sTargetTermRate);

		pl = (KETRequestPartList) PersistenceHelper.manager.save(pl);

		DPLink = KETRequestPartLink.newKETRequestPartLink(dr, pl);
		wt.fc.PersistenceServerHelper.manager.insert(DPLink);

		if (hash.get("tId1") instanceof Vector) {
		    tId1 = (Vector) hash.get("tId1");
		    carTypeCode = (Vector) hash.get("carTypeCode");
		    carType = (Vector) hash.get("carTypeOid");
		    yearAmount1 = (Vector) hash.get("y1");
		    yearAmount2 = (Vector) hash.get("y2");
		    yearAmount3 = (Vector) hash.get("y3");
		    yearAmount4 = (Vector) hash.get("y4");
		    yearAmount5 = (Vector) hash.get("y5");
		    yearAmount6 = (Vector) hash.get("y6");
		    applyedUsage = (Vector) hash.get("usage");
		    optionRate = (Vector) hash.get("optRate");
		    for (int i = 0; i < tId1.size(); i++) {
			if (stId.equals((String) tId1.get(i))) {
			    cy = KETCarYearlyAmount.newKETCarYearlyAmount();
			    cy.setCarTypeCode((String) carTypeCode.get(i));
			    cy.setCarType((String) carType.get(i));
			    cy.setYearAmount1(Double.valueOf((String) yearAmount1.get(i)).doubleValue());
			    cy.setYearAmount2(Double.valueOf((String) yearAmount2.get(i)).doubleValue());
			    cy.setYearAmount3(Double.valueOf((String) yearAmount3.get(i)).doubleValue());
			    cy.setYearAmount4(Double.valueOf((String) yearAmount4.get(i)).doubleValue());
			    cy.setYearAmount5(Double.valueOf((String) yearAmount5.get(i)).doubleValue());
			    cy.setYearAmount6(Double.valueOf((String) yearAmount6.get(i)).doubleValue());
			    cy.setApplyedUsage(Double.valueOf((String) applyedUsage.get(i)).doubleValue());
			    cy.setOptionRate(Double.valueOf((String) optionRate.get(i)).doubleValue());
			    cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			    PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			    wt.fc.PersistenceServerHelper.manager.insert(PCLink);
			}
		    }
		} else {
		    String stId1 = (String) hash.get("tId1");
		    String scarTypeCode = (String) hash.get("carTypeCode");
		    String scarType = (String) hash.get("carTypeOid");
		    Double syearAmount1 = Double.valueOf((String) hash.get("y1")).doubleValue();
		    Double syearAmount2 = Double.valueOf((String) hash.get("y2")).doubleValue();
		    Double syearAmount3 = Double.valueOf((String) hash.get("y3")).doubleValue();
		    Double syearAmount4 = Double.valueOf((String) hash.get("y4")).doubleValue();
		    Double syearAmount5 = Double.valueOf((String) hash.get("y5")).doubleValue();
		    Double syearAmount6 = Double.valueOf((String) hash.get("y6")).doubleValue();
		    Double sapplyedUsage = Double.valueOf((String) hash.get("usage")).doubleValue();
		    Double soptionRate = Double.valueOf((String) hash.get("optRate")).doubleValue();

		    if (stId.equals(stId1)) {
			cy = KETCarYearlyAmount.newKETCarYearlyAmount();
			cy.setCarTypeCode(scarTypeCode);
			cy.setCarType(scarType);
			cy.setYearAmount1(syearAmount1);
			cy.setYearAmount2(syearAmount2);
			cy.setYearAmount3(syearAmount3);
			cy.setYearAmount4(syearAmount4);
			cy.setYearAmount5(syearAmount5);
			cy.setYearAmount6(syearAmount6);
			cy.setApplyedUsage(sapplyedUsage);
			cy.setOptionRate(soptionRate);

			cy = (KETCarYearlyAmount) PersistenceHelper.manager.save(cy);

			PCLink = KETPartCarLink.newKETPartCarLink(pl, cy);
			wt.fc.PersistenceServerHelper.manager.insert(PCLink);
		    }
		}
	    }

	    // dr = (KETDevelopmentRequest) PersistenceHelper.manager.save(dr);
	    // dr = (KETDevelopmentRequest) KETObjectUtil.checkIn(dr);

	    PersistenceServerHelper.manager.update(dr);

	    dr = (KETDevelopmentRequest) PersistenceHelper.manager.refresh(dr);

	    String sfiles = (String) hash.get("isFileDel");
	    if (sfiles.equals("0")) {

	    } else {
		sfiles = sfiles.substring(2);
		StringTokenizer st = new StringTokenizer(sfiles, "/");
		while (st.hasMoreTokens()) {

		    String delsFile = st.nextToken();
		    Kogger.debug(getClass(), "delfilesI=====>" + delsFile);
		    ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
		    ContentInfo info = ContentUtil.getContentInfo((ContentHolder) dr, ctf);
		    String delsFileName = info.getName();
		    Kogger.debug(getClass(), "delsFileName=====>" + delsFileName);

		    QueryResult rs = ContentHelper.service.getContentsByRole(dr, ContentRoleType.SECONDARY);

		    while (rs.hasMoreElements()) {
			ContentItem ctf1 = (ContentItem) rs.nextElement();
			ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) dr, ctf1);

			String itemStr = info1.getName();
			Kogger.debug(getClass(), "itemStr=====>" + itemStr);
			if (itemStr.equals(delsFileName)) {
			    dr = (KETDevelopmentRequest) E3PSContentHelper.service.delete(dr, ctf1);
			}
		    }
		}
	    }

	    if (hash.get("files") instanceof Vector) {
		Vector files = (Vector) hash.get("files");
		if (files != null) {

		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);
			E3PSContentHelper.service.attach(dr, file, "", false);
		    }
		}
	    }

	    trx.commit();
	    Kogger.debug(getClass(), "updateDevRequest drNo=====>" + dr.getDevProductName());

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    dr = null;
	    Kogger.error(getClass(), e);
	}

	return dr;
    }

    @Override
    public String deleteDevRequest(String OID) throws WTException {

	KETDevelopmentRequest dr = null;
	String drOid = OID;
	Transaction trx = new Transaction();
	trx.start();

	try {
	    dr = (KETDevelopmentRequest) CommonUtil.getObject(drOid);

	    boolean isCheckout = KETObjectUtil.isCheckout((WTObject) dr);
	    if (isCheckout) {
		dr = (KETDevelopmentRequest) KETObjectUtil.checkIn(dr);
		Kogger.debug(getClass(), "==========>Checkouted");
		return null;
		// return "isCheckout true";
	    }

	    // if (persistable instanceof LifeCycleManaged)
	    // E3PSWorkflowHelper.manager.deleteWfProcess((LifeCycleManaged) persistable);

	    PersistenceHelper.manager.delete(dr);
	    trx.commit();

	    Kogger.debug(getClass(), "deleteDevelopmentRequest drOid=====>" + drOid);

	} catch (WTException wte) {
	    trx.rollback();
	    drOid = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    drOid = null;
	    Kogger.error(getClass(), e);
	}

	return drOid;
    }

    @Override
    public KETStandardTemplate createStandardTemplate(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETStandardTemplate object
	KETStandardTemplate ketStandardTemplate = KETStandardTemplate.newKETStandardTemplate();

	try {
	    Kogger.debug(getClass(), "********************" + "save service start");

	    // //////////////////////////////////////////// ketStandardTemplate info setting start
	    // ///////////////////////////////////////////////

	    String stdNumber = "STD-" + DateUtil.getCurrentDateString("all").substring(2, 4) + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    stdNumber += ManageSequence.getSeqNo(stdNumber, "000", "WTDocumentMaster", "WTDocumentNumber");

	    ketStandardTemplate.setNumber(stdNumber);// ketStandardTemplate number
	    ketStandardTemplate.setName(stdNumber); // ketStandardTemplate name(ketStandardTemplate number)
	    ketStandardTemplate.setDivisionCode((String) hash.get("divisionCode")); // divisionCode
	    ketStandardTemplate.setDeptCode((String) hash.get("deptCode")); // deptCode
	    ketStandardTemplate.setTitle((String) hash.get("docName")); // document title
	    ketStandardTemplate.setTemplateDescription((String) hash.get("docDesc")); // document description

	    // //////////////////////////////////////////// ketStandardTemplate info setting end
	    // ///////////////////////////////////////////////

	    // //////////////////////////////////////////// folder setting start //////////////////////////////////////////////////////

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String folderPath = DMSProperties.getInstance().getString("dms.folder.standardDoc") + DateUtil.getThisYear();
	    Folder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) ketStandardTemplate, folder);

	    // //////////////////////////////////////////// folder setting end //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// ketStandardTemplate save start
	    // //////////////////////////////////////////////////////

	    ketStandardTemplate = (KETStandardTemplate) PersistenceHelper.manager.save(ketStandardTemplate);

	    // //////////////////////////////////////////// ketStandardTemplate save end
	    // ////////////////////////////////////////////////////////

	    // //////////////////////////////////////////// file save start //////////////////////////////////////////////////////

	    if (files != null) {
		E3PSContentHelper.service.attach(ketStandardTemplate, files);
	    }

	    // //////////////////////////////////////////// file save end //////////////////////////////////////////////////////

	    // Transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "save service end");

	    return ketStandardTemplate;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public KETStandardTemplate updateStandardTemplate(Hashtable hash, Vector files) throws WTException {

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	KETStandardTemplate ketStandardTemplate = null;

	ketStandardTemplate = (KETStandardTemplate) CommonUtil.getObject((String) hash.get("oid"));

	try {
	    Kogger.debug(getClass(), "********************" + "update service start");

	    // //////////////////////////////////////////// ketStandardTemplate save start
	    // //////////////////////////////////////////////////////

	    // object checkout
	    WorkInProgressHelper.service.checkout(ketStandardTemplate, WorkInProgressHelper.service.getCheckoutFolder(), "");
	    // create copy object
	    ketStandardTemplate = (KETStandardTemplate) WorkInProgressHelper.service.workingCopyOf(ketStandardTemplate);

	    // //////////////////////////////////////////// ketStandardTemplate info setting start
	    // //////////////////////////////////////////////////////

	    ketStandardTemplate.setDivisionCode((String) hash.get("divisionCode")); // divisionCode
	    ketStandardTemplate.setDeptCode((String) hash.get("deptCode")); // deptCode
	    ketStandardTemplate.setTitle((String) hash.get("docName")); // document title
	    ketStandardTemplate.setTemplateDescription((String) hash.get("docDesc")); // document description

	    // //////////////////////////////////////////// ketStandardTemplate info setting end
	    // //////////////////////////////////////////////////////

	    // ketStandardTemplate update
	    ketStandardTemplate = (KETStandardTemplate) PersistenceHelper.manager.modify(ketStandardTemplate);
	    // ketStandardTemplate refresh
	    ketStandardTemplate = (KETStandardTemplate) PersistenceHelper.manager.refresh(ketStandardTemplate);

	    // //////////////////////////////////////////// ketStandardTemplate save end
	    // //////////////////////////////////////////////////////

	    // //////////////////////////////////////////// file save start //////////////////////////////////////////////////////

	    // old file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketStandardTemplate, ContentRoleType.SECONDARY);

	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		ketStandardTemplate = (KETStandardTemplate) E3PSContentHelper.service.delete(ketStandardTemplate, fileContent);
	    }

	    // existing file save
	    Object secondaryFile = hash.get("secondaryDelFile");
	    ApplicationData oldFile = null;

	    if (secondaryFile instanceof String) {
		oldFile = (ApplicationData) CommonUtil.getObject((String) secondaryFile);
		E3PSContentHelper.service.attach(ketStandardTemplate, oldFile, false);
	    } else if (secondaryFile instanceof Vector) {
		Vector vecOld = (Vector) secondaryFile;
		for (int inx = 0; inx < vecOld.size(); inx++) {
		    oldFile = (ApplicationData) CommonUtil.getObject((String) vecOld.elementAt(inx));
		    E3PSContentHelper.service.attach(ketStandardTemplate, oldFile, false);
		}
	    }

	    // new file save
	    if (files != null) {
		E3PSContentHelper.service.attach(ketStandardTemplate, files);
	    }

	    // //////////////////////////////////////////// file save end //////////////////////////////////////////////////////

	    // ketStandardTemplate refresh
	    ketStandardTemplate = (KETStandardTemplate) PersistenceHelper.manager.refresh(ketStandardTemplate);

	    if (WorkInProgressHelper.isCheckedOut(ketStandardTemplate)) {
		// ketStandardTemplate checkin
		ketStandardTemplate = (KETStandardTemplate) WorkInProgressHelper.service.checkin(ketStandardTemplate, "");
	    }

	    // transaction end
	    trx.commit();

	    Kogger.debug(getClass(), "********************" + "update service end");

	    return ketStandardTemplate;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    Kogger.error(getClass(), wtpve);
	    return null;
	} catch (WTException wte) {
	    trx.rollback();
	    Kogger.error(getClass(), wte);
	    return null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    @Override
    public Boolean deleteStandardTemplate(Hashtable hash, Vector files) throws WTException {

	boolean flag = false;

	// transaction start
	Transaction trx = new Transaction();
	trx.start();

	// KETStandardTemplate object
	KETStandardTemplate ketStandardTemplate = (KETStandardTemplate) CommonUtil.getObject((String) hash.get("oid"));

	try {

	    Kogger.debug(getClass(), "********************" + "delete service start");

	    // file delete
	    QueryResult rs = ContentHelper.service.getContentsByRole(ketStandardTemplate, ContentRoleType.SECONDARY);
	    while (rs.hasMoreElements()) {
		ContentItem fileContent = (ContentItem) rs.nextElement();
		E3PSContentHelper.service.delete(ketStandardTemplate, fileContent);
	    }

	    // ketStandardTemplate object delete
	    PersistenceHelper.manager.delete(ketStandardTemplate);

	    // transaction end
	    trx.commit();

	    // success
	    flag = true;

	    Kogger.debug(getClass(), "********************" + "delete service end");

	} catch (Exception e) {
	    trx.rollback();
	    // fail
	    flag = false;
	    Kogger.error(getClass(), e);
	}

	return flag;
    }

    @Override
    public KETTechnicalDocument insertTechDoc(Hashtable hash) throws WTException {

	String documentNo = null;
	String deptName = null;
	String categoryCode = null;
	String categoryPath = null;

	String deptCode = null;
	String dutyCode = null;
	String dutyName = null;

	String divisionCode = null;
	Integer tmpInt = 0;

	KETTechnicalDocument d = null;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    categoryCode = (String) hash.get("categoryCode");
	    Kogger.debug(getClass(), "t categoryCode=====>" + categoryCode);

	    categoryPath = selectCategoryPath(categoryCode);
	    Kogger.debug(getClass(), "t categoryPath=====>" + categoryPath);

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    while (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    String numberingCode1 = docCate.getAttribute2();
	    String numberingCode2 = docCate.getAttribute3();

	    d = KETTechnicalDocument.newKETTechnicalDocument();

	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date currentTime = new java.util.Date();
	    String ymonth = formatter.format(currentTime).substring(2, 4) + formatter.format(currentTime).substring(5, 7);
	    documentNo = categoryCode.substring(0, 2) + "-" + ymonth;
	    documentNo += ManageSequence.getSeqNo(documentNo, "000", "WTDocumentMaster", "WTDocumentNumber");
	    Kogger.debug(getClass(), "t documentNo=====>" + documentNo);

	    if (StringUtils.isNotEmpty(numberingCode1)) {
		documentNo = numberingCode1 + "-";
		if (StringUtils.isNotEmpty(numberingCode2)) {
		    documentNo += numberingCode2 + "-";
		}
		documentNo += ManageSequence.getSeqNo(documentNo, "0000", "WTDocumentMaster", "WTDocumentNumber");
	    }

	    d.setNumber(documentNo);
	    d.setTitle((String) hash.get("documentName"));
	    d.setSecurity((String) hash.get("security"));

	    if ("Y".equals((String) hash.get("isDesign"))) {
		deptCode = (String) hash.get("techDeptCode");
		deptName = (String) hash.get("techDeptName");
		dutyCode = (String) hash.get("duty");

		System.out.println("techDeptCode ==> " + deptCode);
		System.out.println("dutyCode ==> " + dutyCode);
		d.setAttribute2(deptCode);
		d.setDeptName(deptName);
		d.setAttribute3(dutyCode);
		d.setAttribute4((String) CompanyState.dutyTable.get(dutyCode));
	    } else {
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		PeopleData peoData = new PeopleData(user);
		deptName = peoData.departmentName;
		Kogger.debug(getClass(), "t deptName=====>" + deptName);
		d.setDeptName(deptName);
	    }

	    divisionCode = (String) hash.get("divisionCode");
	    d.setDivisionCode(divisionCode);
	    d.setDocumentDescription((String) hash.get("documentDescription"));
	    Kogger.debug(getClass(), "t divisionCode=====>" + divisionCode);

	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */

	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;

	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }
	    Kogger.debug(getClass(), "2=====>");
	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    }
	    Kogger.debug(getClass(), "3=====>");
	    FolderHelper.assignLocation((FolderEntry) d, cateFolder);

	    Kogger.debug(getClass(), "4=====>");
	    d.setName((String) hash.get("documentName"));
	    d.setAttribute1((String) hash.get("isDesign") == "N" ? "" : (String) hash.get("isDesign"));
	    d = (KETTechnicalDocument) PersistenceHelper.manager.save(d);

	    KETTechnicalCategoryLink DCLink;
	    DCLink = KETTechnicalCategoryLink.newKETTechnicalCategoryLink(docCate, d);
	    DCLink = (KETTechnicalCategoryLink) PersistenceHelper.manager.save(DCLink);

	    trx.commit();
	    Kogger.debug(getClass(), "insertTechDoc documentNo=====>" + d.getNumber());

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

    @Override
    public KETTechnicalDocument updateTechDoc(Hashtable hash) throws WTException {

	String docuOid = null;
	String documentNo = null;
	String categoryCode = null;
	String categoryPath = null;

	KETTechnicalDocument d = null;
	Hashtable docHash;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    docuOid = ((String) hash.get("docuOid")).trim();
	    d = (KETTechnicalDocument) CommonUtil.getObject(docuOid);
	    Kogger.debug(getClass(), "updated TechDoc docoid=====>" + docuOid);

	    KETTechnicalCategoryLink DCLink;
	    DCLink = new KETTechnicalCategoryLink();
	    QueryResult r1 = PersistenceHelper.manager.navigate(d, "documentCategory", KETTechnicalCategoryLink.class, false);
	    if (r1.hasMoreElements()) {
		DCLink = (KETTechnicalCategoryLink) r1.nextElement();
		PersistenceHelper.manager.delete(DCLink);
	    }

	    docHash = KETObjectUtil.checkOut(docuOid);
	    if (docHash == null) {
		return d;
	    }

	    d = (KETTechnicalDocument) docHash.get("WorkableObject");
	    documentNo = d.getNumber();
	    d.setTitle((String) hash.get("documentName"));
	    d.setDocumentDescription(((String) hash.get("documentDescription")).trim());

	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setAttribute1((String) hash.get("isDesign") == "N" ? "" : (String) hash.get("isDesign"));
	    String deptCode = null;
	    String deptName = null;
	    String dutyCode = null;
	    if ("Y".equals((String) hash.get("isDesign"))) {
		deptCode = (String) hash.get("techDeptCode");
		deptName = (String) hash.get("techDeptName");
		dutyCode = (String) hash.get("duty");

		System.out.println("techDeptCode ==> " + deptCode);
		System.out.println("dutyCode ==> " + dutyCode);
		d.setAttribute2(deptCode);
		d.setDeptName(deptName);
		d.setAttribute3(dutyCode);
		d.setAttribute4((String) CompanyState.dutyTable.get(dutyCode));
	    }

	    d = (KETTechnicalDocument) PersistenceHelper.manager.save(d);
	    d = (KETTechnicalDocument) KETObjectUtil.checkIn(d);

	    categoryCode = (String) hash.get("categoryCode");
	    categoryPath = selectCategoryPath(categoryCode);

	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    String divisionCode = d.getDivisionCode();

	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, wtContainerRef);
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, wtContainerRef);
	    }
	    FolderHelper.service.changeFolder((FolderEntry) d, cateFolder);

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    if (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    DCLink = new KETTechnicalCategoryLink();
	    DCLink = KETTechnicalCategoryLink.newKETTechnicalCategoryLink(docCate, d);
	    DCLink = (KETTechnicalCategoryLink) PersistenceHelper.manager.save(DCLink);

	    String sfiles = (String) hash.get("isFileDel");
	    if (sfiles.equals("0")) {

	    } else {
		sfiles = sfiles.substring(2);
		StringTokenizer st = new StringTokenizer(sfiles, "/");
		while (st.hasMoreTokens()) {

		    String delsFile = st.nextToken();
		    Kogger.debug(getClass(), "delfilesI=====>" + delsFile);
		    ContentItem ctf = (ContentItem) CommonUtil.getObject(delsFile);
		    ContentInfo info = ContentUtil.getContentInfo((ContentHolder) d, ctf);
		    String delsFileName = info.getName();
		    Kogger.debug(getClass(), "delsFileName=====>" + delsFileName);

		    QueryResult rs = ContentHelper.service.getContentsByRole(d, ContentRoleType.SECONDARY);

		    while (rs.hasMoreElements()) {
			ContentItem ctf1 = (ContentItem) rs.nextElement();
			ContentInfo info1 = ContentUtil.getContentInfo((ContentHolder) d, ctf1);

			String itemStr = info1.getName();
			Kogger.debug(getClass(), "itemStr=====>" + itemStr);
			if (itemStr.equals(delsFileName)) {
			    d = (KETTechnicalDocument) E3PSContentHelper.service.delete(d, ctf1);
			}
		    }
		}
	    }

	    if (hash.get("files") instanceof Vector) {
		Vector files = (Vector) hash.get("files");
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {

			    if (d instanceof FormatContentHolder) {
				FormatContentHolder holder = (FormatContentHolder) d;
				ContentInfo info = ContentUtil.getPrimaryContent(holder);
				ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

				E3PSContentHelper.service.delete((ContentHolder) d, ctf);
			    }
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(d, file, "", isPrimary);
		    }
		}
	    }
	    trx.commit();
	    documentNo = d.getNumber();
	    Kogger.debug(getClass(), "updated TechDoc documentNo=====>" + documentNo);

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

    @Override
    public String deleteTechDoc(String OID) throws WTException {

	KETTechnicalDocument d = null;
	String docuOid = OID;
	Transaction trx = new Transaction();
	trx.start();

	try {
	    d = (KETTechnicalDocument) CommonUtil.getObject(docuOid);

	    boolean isCheckout = KETObjectUtil.isCheckout((WTObject) d);
	    if (isCheckout) {
		d = (KETTechnicalDocument) KETObjectUtil.checkIn(d);
		Kogger.debug(getClass(), "==========>Checkouted");
		return null;
		// return "isCheckout true";
	    }

	    d = (KETTechnicalDocument) KETContentHelper.service.delete(d);

	    PersistenceHelper.manager.delete(d);
	    trx.commit();

	    Kogger.debug(getClass(), "deleteTechDoc docuOid=====>" + docuOid);

	} catch (WTException wte) {
	    trx.rollback();
	    docuOid = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    docuOid = null;
	    Kogger.error(getClass(), e);
	}

	return docuOid;
    }

    @Override
    public KETTechnicalDocument reviceTechDoc(Hashtable hash) throws WTException {

	String docuOid = null;
	Vector partOids = null;
	String categoryCode = null;
	String categoryPath = null;
	KETTechnicalDocument d = null;
	String documentNo = null;

	Transaction trx = new Transaction();
	trx.start();

	try {
	    docuOid = ((String) hash.get("docuOid")).trim();
	    Versioned newVs = null;
	    Versioned vs = (Versioned) CommonUtil.getObject(docuOid);

	    String lifecycle = ((LifeCycleManaged) vs).getLifeCycleName();
	    Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);
	    newVs = VersionControlHelper.service.newVersion(vs);
	    FolderHelper.assignLocation((FolderEntry) newVs, folder);
	    d = (KETTechnicalDocument) newVs;

	    LifeCycleHelper.setLifeCycle(d, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef())); // Lifecycle
	    d = (KETTechnicalDocument) PersistenceHelper.manager.save(d);

	    // LifeCycleHelper.setLifeCycle((LifeCycleManaged) d, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,
	    // WCUtil.getWTContainerRef()));
	    KETTechnicalCategoryLink DCLink;
	    DCLink = new KETTechnicalCategoryLink();
	    QueryResult r1 = PersistenceHelper.manager.navigate(d, "documentCategory", KETTechnicalCategoryLink.class, false);
	    if (r1.hasMoreElements()) {
		DCLink = (KETTechnicalCategoryLink) r1.nextElement();
		PersistenceHelper.manager.delete(DCLink);
	    }

	    documentNo = d.getNumber();
	    d.setTitle((String) hash.get("documentName"));
	    d.setDocumentDescription(((String) hash.get("documentDescription")).trim());

	    /*
	     * Start [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */
	    d.setWebEditor((String) hash.get("webEditor"));
	    d.setWebEditorText((String) hash.get("webEditorText"));
	    /*
	     * End [PLM System 1????? ?????? : webEditor ??? column ???, 2013. 6. 3, ?????
	     */

	    d = (KETTechnicalDocument) PersistenceHelper.manager.save(d);

	    categoryCode = (String) hash.get("categoryCode");
	    categoryPath = selectCategoryPath(categoryCode);

	    String divisionCode = d.getDivisionCode();

	    Folder cateFolder = null;
	    if (divisionCode.equals("CA")) {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
	    } else if (divisionCode.equals("ER")) {
		cateFolder = FolderHelper.service.getFolder(eRoot + categoryPath, WCUtil.getWTContainerRef());
	    } else {
		cateFolder = FolderHelper.service.getFolder(aRoot + categoryPath, WCUtil.getWTContainerRef());
	    }
	    FolderHelper.service.changeFolder((FolderEntry) d, cateFolder);

	    KETDocumentCategory docCate = null;
	    QuerySpec q = new QuerySpec(KETDocumentCategory.class);
	    q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });
	    QueryResult qre = PersistenceHelper.manager.find(q);
	    if (qre.hasMoreElements()) {
		docCate = (KETDocumentCategory) qre.nextElement();
	    }

	    DCLink = new KETTechnicalCategoryLink();
	    DCLink = KETTechnicalCategoryLink.newKETTechnicalCategoryLink(docCate, d);
	    DCLink = (KETTechnicalCategoryLink) PersistenceHelper.manager.save(DCLink);

	    String sfiles = (String) hash.get("isFileDel");
	    if (sfiles.equals("0")) {

	    } else {
		sfiles = sfiles.substring(2);
		StringTokenizer st = new StringTokenizer(sfiles, "/");
		while (st.hasMoreTokens()) {
		    ContentItem ctf = (ContentItem) CommonUtil.getObject(st.nextToken());
		    E3PSContentHelper.service.delete((ContentHolder) d, ctf);
		}
	    }

	    if (hash.get("files") instanceof Vector) {
		Vector files = (Vector) hash.get("files");
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {

			    if (d instanceof FormatContentHolder) {
				FormatContentHolder holder = (FormatContentHolder) d;
				ContentInfo info = ContentUtil.getPrimaryContent(holder);
				ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());

				E3PSContentHelper.service.delete((ContentHolder) d, ctf);
			    }
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(d, file, "", isPrimary);
		    }
		}
	    }
	    documentNo = d.getNumber();
	    Kogger.debug(getClass(), "reviced TechDoc documentNo=====>" + documentNo);

	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wtpve);
	} catch (WTException wte) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    trx.rollback();
	    d = null;
	    Kogger.error(getClass(), e);
	}

	return d;
    }

}
