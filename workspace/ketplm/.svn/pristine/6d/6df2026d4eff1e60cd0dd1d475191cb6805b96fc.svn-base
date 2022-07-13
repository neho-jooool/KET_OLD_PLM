package ext.ket.sysif.solr.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

import com.infoengine.object.factory.Element;
import com.infoengine.object.factory.Group;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.impl.WCTypeIdentifier;
import com.ptc.windchill.enterprise.search.mvc.service.SearchResult;
import com.ptc.windchill.enterprise.search.mvc.service.SearchResultInputStream;
import com.ptc.windchill.enterprise.search.mvc.service.SearchResultItem;
import com.ptc.windchill.enterprise.search.mvc.service.WindchillSearchService;
import com.ptc.windchill.enterprise.search.server.SearchCriteriaInfo;
import com.ptc.windchill.enterprise.search.server.SearchInfo;

import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.sysif.solr.entity.IndexSearchDTO;

public class StandardIndexSearchService extends StandardManager implements IndexSearchService, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Default factory for the class.
     * 
     * @return
     * @throws WTException
     * @메소드명 : newStandardIndexSearchService
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static StandardIndexSearchService newStandardIndexSearchService() throws WTException {
	StandardIndexSearchService instance = new StandardIndexSearchService();
	instance.initialize();
	return instance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ext.ket.sysif.solr.service.IndexSearchService#query(java.lang.String)
     */
    @Override
    public List<String> query(IndexSearchDTO dto) throws Exception {
	SearchInfo si = new SearchInfo();
	si.setAccessControllers("");
	si.setAndOrOperator("");
	si.setApplyNullAndTerminalAndWorkingCriteria(true);
	si.setAppQueryLimit(-1);
	si.setIndexSearch(true);
	si.setIncludeShared(true);
	si.setIndexServerDown(false);
	si.setSearchInSubFolders(true);
	si.setLatestVersionControllerNeeded(true);
	si.setLocale(Locale.KOREAN);
	si.setMaxResults(-1); // default -1 //제한을 걸면 Result객체가 Element로 나옵니다. ㅠ.ㅠ
	si.setOrInObjRef(false);
	si.setOrInObjRefIndex("false");
	si.setOuterJoin(false);
	si.setResultItemUfidType(true);
	si.setShowWorkingCopy(false);
	si.setSortAtIndex(false);
	Map<String, String> hmGenAttrMap = new HashMap<String, String>();
	hmGenAttrMap.put("searchQueryServiceClassName", "");
	si.setHmGenSearchAttr(hmGenAttrMap);
	TypeIdentifier wtProjectTi = new WCTypeIdentifier("e3ps.project.E3PSProject");
	TypeIdentifier wtDevDocTi = new WCTypeIdentifier("e3ps.dms.entity.KETProjectDocument");
	TypeIdentifier wtDrawingTi = new WCTypeIdentifier("wt.epm.EPMDocument");
	TypeIdentifier wtPartTi = new WCTypeIdentifier("wt.part.WTPart");
	TypeIdentifier wtEcoTi = new WCTypeIdentifier("e3ps.ecm.entity.KETProdChangeOrder");
	TypeIdentifier wtDqmTi = new WCTypeIdentifier("ext.ket.dqm.entity.KETDqmRaise");
	TypeIdentifier wtSampleRequestTi = new WCTypeIdentifier("ext.ket.sample.entity.KETSampleRequest");

	TypeIdentifier[] ti = null;
	if (StringUtil.isEmpty(dto.getSearchCategory())) {
	    ti = new TypeIdentifier[] { wtProjectTi, wtDevDocTi, wtDrawingTi, wtPartTi, wtEcoTi, wtDqmTi, wtSampleRequestTi};
	} else {
	    List<TypeIdentifier> categoryList = new ArrayList<TypeIdentifier>();
	    String[] categoryArr = dto.getSearchCategory().split(",", -1);
	    for (int i = 0; i < categoryArr.length; i++) {
		if ("PROJECT".equals(categoryArr[i])) {
		    categoryList.add(wtProjectTi);
		}
		if ("DEVDOC".equals(categoryArr[i])) {
		    categoryList.add(wtDevDocTi);
		}
		if ("DRAWING".equals(categoryArr[i])) {
		    categoryList.add(wtDrawingTi);
		}
		if ("PART".equals(categoryArr[i])) {
		    categoryList.add(wtPartTi);
		}
		if ("ECO".equals(categoryArr[i])) {
		    categoryList.add(wtEcoTi);
		}
		if ("DQM".equals(categoryArr[i])) {
			Kogger.debug(getClass(), "통합검색 검색 ....DQM ");
		    categoryList.add(wtDqmTi);
		}
		if ("SAMPLE".equals(categoryArr[i])) {
		    categoryList.add(wtSampleRequestTi);
		}
	    }
	    if (categoryList.size() > 0) {
		ti = new TypeIdentifier[categoryList.size()];
		ti = categoryList.toArray(ti);
	    }
	}

	si.setTargetDataTypes(ti);
	si.setAttributeTypeContext(ti);
	si.setTaskFormGroup(new Group());
	si.setWarnIndexServerDown(false);

	SearchCriteriaInfo sci = new SearchCriteriaInfo();
	sci.setWhereClause("()");
	// sci.setArchiveSearchType("description");
	sci.setContainerCriteriaForIndexSearch(false);
	sci.setFilterLatestVersionForDesignatedTypes(false);
	sci.setIteration("LATEST");
	sci.setVersion("LATEST");
	sci.setKeyword(dto.getSearchKeyword());
	// sci.setSearchInUserOrg(false);
	// sci.setSearchWithinNonProjectContainer(false);
	// sci.setSearchWithinProject(false);
	si.setCriteriaInfo(sci);

	List<String> ufidList = new ArrayList<String>();
	WindchillSearchService wss = new WindchillSearchService();
	SessionContext context = SessionContext.newContext();
	Kogger.debug(getClass(), ">> 통합검색 시작");
	Kogger.debug(getClass(), "통합검색 검색 유형 >> " + dto.getSearchCategory());
	try {
	    SessionHelper.manager.setAdministrator();
	    SearchResult sr = wss.search(si);
	    SearchResultInputStream localSearchResultInputStream = sr.getInputStream();
	    if (localSearchResultInputStream.isItemUfid()) {
		while (true) {
		    SearchResultItem localSearchResultItem = localSearchResultInputStream.read();
		    if (localSearchResultItem == null) {
			break;
		    }
		    Object ret = (Object) localSearchResultItem.getItem();
		    if (ret instanceof String) {
			ufidList.add((String) ret);

		    } else if (ret instanceof Element) {
			ufidList.add(((Element) ret).getUfid());
		    }
		}
	    }
	    Kogger.debug(getClass(), "통합검색 결과 >> " + ufidList.size() + " 건");
	} catch (Exception e) {
	} finally {
	    SessionContext.setContext(context);
	}
	return ufidList;
    }
}
