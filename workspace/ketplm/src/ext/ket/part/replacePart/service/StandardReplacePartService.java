package ext.ket.part.replacePart.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.Files;

import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonSearchHelper;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ReviewProject;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostQuantity;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.PjtMasterPartListLink;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.entity.costAnalisysLink;
import ext.ket.cost.entity.costPartQtyLink;
import ext.ket.cost.util.CostPartImgUtil;
import ext.ket.cost.util.CostUtil;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.KETPartInfo;
import ext.ket.part.entity.RivalPartInfo;
import ext.ket.part.replacePart.util.ReplacePartImgUtil;
import ext.ket.part.replacePart.util.ReplacePartUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

public class StandardReplacePartService extends StandardManager implements ReplacePartService {
    private static final long serialVersionUID = 1L;
    
    public static StandardReplacePartService newStandardReplacePartService() throws WTException {
	StandardReplacePartService instance = new StandardReplacePartService();
	instance.initialize();
	return instance;
    }
    
    public PageControl getPageControl(Map<String, Object> reqMap, QuerySpec qs) throws Exception{
	
	PageControl pageControl = null;
	
	int formPage = StringUtil.getIntParameter((String) reqMap.get("formPage"), PageControl.FORMPAGE);
        int currentPage = Integer.parseInt(StringUtil.checkReplaceStr((String) reqMap.get("page"), "0"));
        
        
	if (currentPage == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	
	if (StringUtil.isEmpty(pageSessionId)) {
	    pageControl = CommonSearchHelper.find(formPage, formPage, qs);
	} else {
	    pageControl = CommonSearchHelper.find(formPage, formPage, currentPage + 1, pageSessionId);
	}
	
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	
	return pageControl; 
    }
    
    
    @Override
    public Map<String, Object> getReplacePartGridData(Map<String, Object> reqMap) throws Exception {
	
	String dataType = (String) reqMap.get("DATATYPE");

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	QueryResult qr = null;

	PageControl pageControl = null;
	if ("REPLACEPARTLIST".equals(dataType) || "REPLACEPARTLISTPOPUP".equals(dataType) ) {
	    
	    QuerySpec qs = ReplacePartUtil.manager.getReplacePartQuery(reqMap);   
	    
	    pageControl = this.getPageControl(reqMap, qs);
	    
	    qr = pageControl.getResult();
	    
	}
	
	if (qr != null) {

	    while (qr.hasMoreElements()) {
		Persistable obj = null;
		Map<String, Object> dataMap = null;
		if ("REPLACEPARTLIST".equals(dataType) || "REPLACEPARTLISTPOPUP".equals(dataType) ) {
		    Object[] o = (Object[]) qr.nextElement();
		    obj = (Persistable) o[0];
		    
		    if(obj != null){
			dataMap = ObjectUtil.manager.converObjectToMap(obj);
			dataMap.put("ketPartOid", CommonUtil.getOIDString(obj));
		    }else{
			dataMap = new HashMap<String, Object>();
			dataMap.put("replaceGb", "X");
		    }
		    
		    obj = (Persistable) o[1];
		    dataMap = ObjectUtil.manager.converObjectToMap2(obj,dataMap);
		    dataMap.put("rivalPartOid", CommonUtil.getOIDString(obj));
		    dataMap.put("deleteFlag", "0");
		    ReplacePartUtil.manager.DataMapSet(dataMap, "partNo", dataType);
		    ReplacePartUtil.manager.DataMapSet(dataMap, "ketPartNo", dataType);
		    
		}else{
		    obj = (Persistable) qr.nextElement();
		    dataMap = ObjectUtil.manager.converObjectToMap(obj);
		}
		
		dataMap.put("id", CommonUtil.getOIDString(obj));
		dataMap.put("deleteFlag", "0");
		
		dataList.add(dataMap);
		
	    }
	}
	
	
	return EjsConverUtil.convertToDto(dataList, pageControl);
	
    }
    
    @Override
    @Transactional
    public Map<String, Object> saveReplacePart(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();

	ObjectMapper mapper = new ObjectMapper();

	String treeDataStr = (String) reqMap.get("treeData");

	String formDataStr = (String) reqMap.get("formdata");
	
	RivalPartInfo part = null;
	
	try {

	    Map<String, Object> treeData = mapper.readValue(treeDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });

	    part = (RivalPartInfo) CommonUtil.getObject((String) formData.get("rivalPartOid"));
	    
	    if(part == null){
		part = ReplacePartUtil.manager.getRivalPart(formData);
	    }

	    if (part == null) {
		part = RivalPartInfo.newRivalPartInfo();
	    }

	    ObjectUtil.manager.convertMapToObject(formData, part);
	    part.setOwner(SessionHelper.manager.getPrincipalReference());
	    String companyName = "";
	    NumberCode nc = CodeHelper.manager.getNumberCode("SALESCOMPETITOR", part.getCompanyCode());
	    if(nc != null){
		companyName = nc.getName();
	    }
	    part.setCompanyName(companyName);

	    part = (RivalPartInfo) PersistenceHelper.manager.save(part);

	    if (treeData != null) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> changes = (List<Map<String, Object>>) treeData.get("Changes");

		for (Map<String, Object> data : changes) {

		    boolean added = false;

		    boolean deleted = false;

		    if (data.get("Added") != null)
			added = true;
		    if (data.get("Deleted") != null)
			deleted = true;

		    String id = (String) data.get("ketPartOid");

		    KETPartInfo ketPart = null;

		    if (id.indexOf(KETPartInfo.class.getName()) >= 0) {
			ketPart = (KETPartInfo) CommonUtil.getObject(id);
			formData.put("rivalPartOid", CommonUtil.getOIDString(ketPart.getRivalPart()));

		    } else {
			if (!deleted) {
			    ketPart = KETPartInfo.newKETPartInfo();
			}
		    }

		    if (!deleted) {

			ketPart.setRivalPart(part);
			ketPart.setOwner(SessionHelper.manager.getPrincipalReference());
			String ketPartNo = (String)data.get("ketPartNo");
			String replaceGb = (String)data.get("replaceGb");
			WTPart wtpart = PartBaseHelper.service.getLatestPart(ketPartNo);
			formData.put("partNo", ketPartNo);
			KETPartInfo KETPart = ReplacePartUtil.manager.getKETPart(formData);

			if (KETPart == null) {
			    KETPart = KETPart.newKETPartInfo();
			}

			String SpWaterProof = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpWaterProof);// 방수여부
			String SpNoOfPole = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpNoOfPole);// 극수
			String spMTerminal = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMTerminal);// 매칭터미널
			String spMConnector = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpMConnector);// 매칭커넥터
			String spSeries = PartSpecGetter.getPartSpec(wtpart, PartSpecEnum.SpSeries);// 시리즈
			NumberCode num = CodeHelper.manager.getNumberCode("SEALED", SpWaterProof);
			String sealed = "";
			if (num != null) {
			    sealed = num.getName();
			}
			String KetSeries = "";
			num = CodeHelper.manager.getNumberCode("SPECSERIES", spSeries);
			if (num != null) {
			    KetSeries = num.getName();
			}

			KETPartClassification claz = PartClazHelper.service.getPartClassification(wtpart);

			if (StringUtils.isNotEmpty(spMTerminal)) {

			    spMTerminal = spMTerminal.replaceAll(",", System.getProperty("line.separator"));
			}

			if (StringUtils.isNotEmpty(spMConnector)) {

			    spMConnector = spMConnector.replaceAll(",", System.getProperty("line.separator"));
			}

			KETPart.setKetPartNo(ketPartNo);
			KETPart.setKetPartName(wtpart.getName());
			KETPart.setKetWaterProof(sealed);
			KETPart.setClassification(claz.getClassKrName());
			KETPart.setKetPole(SpNoOfPole);
			KETPart.setKetMatchConnector(spMConnector);
			KETPart.setKetMatchTerminal(spMTerminal);
			KETPart.setRivalPart(part);
			KETPart.setReplaceGb(replaceGb);
			KETPart.setKetSeries(KetSeries);
			KETPart.setOwner(SessionHelper.manager.getPrincipalReference());
			KETPart = (KETPartInfo) PersistenceHelper.manager.save(KETPart);
		    }

		    if (ketPart != null && deleted) {
			PersistenceHelper.manager.delete(ketPart);
		    }

		}

	    }

	} catch (Exception e) {
	    throw e;
	}

	return resMap;
    }
    
    @Override
    public void deleteReplacePart(Map<String, Object> reqMap) throws Exception{
	
	
	Transaction transaction = new Transaction();
	
	try {
	    transaction.start();

	    String delsStr = (String) reqMap.get("dels");

	    ObjectMapper mapper = new ObjectMapper();
	    List<Map<String, Object>> dels = mapper.readValue(delsStr, new TypeReference<List<Map<String, Object>>>() {});

	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    
	    List<String> deletedList = new ArrayList<String>();
		
	    for (int i = 0; i < dels.size(); i++) {
		boolean delFlag = true;
		Map<String, Object> del = dels.get(i);
		String oid = (String) del.get("oid");
		
		for(String delOid : deletedList){
		    if(oid.equals(delOid)){
			delFlag = false;
		    }
		}
		if(!delFlag){
		    continue;
		}
		Object obj = CommonUtil.getObject(oid);

		if (obj != null && obj instanceof RivalPartInfo) {
		    paramMap.put("rivalPartOid", oid);
		    this.deleteKETPart(paramMap);
		    this.deleteRivalPart(paramMap);
		}

		if (obj != null && obj instanceof KETPartInfo) {
		    KETPartInfo part = (KETPartInfo) obj;
		    paramMap.put("partNo", part.getKetPartNo());
		    paramMap.put("rivalPartOid", CommonUtil.getOIDString(part.getRivalPart()));
		    this.deleteKETPart(paramMap);
		}
		
		deletedList.add(oid);
	    }
	    
	    transaction.commit();
	    transaction = null;
	    
	} catch (Exception e) {
	    
	    transaction.rollback();
	    throw e;
	    
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
	
    }

    
    public void deleteRivalPart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	
	String rivalPartOid = (String)reqMap.get("rivalPartOid");
	
	RivalPartInfo rivalPart = (RivalPartInfo)CommonUtil.getObject(rivalPartOid);
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(RivalPartInfo.class, true);

	qs.appendWhere(new SearchCondition(RivalPartInfo.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(rivalPart)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    RivalPartInfo part = (RivalPartInfo) o[0];
	    PersistenceHelper.manager.delete(part);
	}
    }

    
    public void deleteKETPart(Map<String, Object> reqMap) throws Exception {
	// TODO Auto-generated method stub
	
	String partNo = (String)reqMap.get("partNo");
	String rivalPartOid = (String)reqMap.get("rivalPartOid");
	
	RivalPartInfo rivalPart = (RivalPartInfo)CommonUtil.getObject(rivalPartOid);
	
	KETPartInfo part = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(KETPartInfo.class, true);
	
	if (StringUtils.isNotEmpty(rivalPartOid)){
	    qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.RIVAL_PART_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(rivalPart)), new int[] { idx });
	}

	if (StringUtils.isNotEmpty(partNo)){
	    if(qs.getConditionCount() > 0){
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(KETPartInfo.class, KETPartInfo.KET_PART_NO, SearchCondition.EQUAL, partNo),new int[] { idx });
	}
	
	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    part = (KETPartInfo) o[0];
	    PersistenceHelper.manager.delete(part);
	}
	
    }
    
    
    
    
    
}
