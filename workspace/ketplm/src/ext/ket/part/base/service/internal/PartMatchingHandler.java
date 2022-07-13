package ext.ket.part.base.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartMatchingDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class PartMatchingHandler {

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private Map<String,String> uniqueMap = new HashMap<String,String>();

    public int getMatchingPartCount(String partOid) throws Exception {

	WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);

	if (wtPart == null) {
	    return 0;
	}

	String spMTerminal = null;
	String spMatchBulb = null;
	String spMConnector = null;
	String spMClip = null;
	String spMRH = null;
	String spMCover = null;
	String spMWireSeal = null;
	
	// 분류체계
	KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);

	if (claz == null) {
	    
	    spMTerminal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMTerminal);
	    spMatchBulb = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMatchBulb);
	    spMConnector = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMConnector);
	    spMClip = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMClip);
	    spMRH = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMRH);
	    spMCover = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMCover);
	    spMWireSeal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMWireSeal);
	    
	}else{

	    List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, claz);
	    for (KETPartClassAttrLink link : linkList) {
		KETPartAttribute attr = link.getAttr();
		if (PartSpecEnum.SpMTerminal.getAttrCode().equals(attr.getAttrCode())) {
		    spMTerminal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMTerminal);
		} else if (PartSpecEnum.SpMatchBulb.getAttrCode().equals(attr.getAttrCode())) {
		    spMatchBulb = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMatchBulb);
		} else if (PartSpecEnum.SpMConnector.getAttrCode().equals(attr.getAttrCode())) {
		    spMConnector = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMConnector);
		} else if (PartSpecEnum.SpMClip.getAttrCode().equals(attr.getAttrCode())) {
		    spMClip = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMClip);
		} else if (PartSpecEnum.SpMRH.getAttrCode().equals(attr.getAttrCode())) {
		    spMRH = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMRH);
		} else if (PartSpecEnum.SpMCover.getAttrCode().equals(attr.getAttrCode())) {
		    spMCover = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMCover);
		} else if (PartSpecEnum.SpMWireSeal.getAttrCode().equals(attr.getAttrCode())) {
		    spMWireSeal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMWireSeal);
		}
	    }
	}

	int count = getMatchingCount(spMTerminal);
	count = count + getMatchingCount(spMatchBulb);
	count = count + getMatchingCount(spMConnector);
	count = count + getMatchingCount(spMClip);
	count = count + getMatchingCount(spMRH);
	count = count + getMatchingCount(spMCover);
	count = count + getMatchingCount(spMWireSeal);

	return count;
    }

    private int getMatchingCount(String matchingPartNos) throws WTException {

	if (StringUtils.isEmpty(matchingPartNos)) {
	    return 0;
	}

	String[] strArray = null;
	if (matchingPartNos.indexOf(",") == -1) {
	    strArray = new String[] { matchingPartNos };
	} else {
	    strArray = matchingPartNos.split(",");
	}

	if (strArray == null || strArray.length == 0) {
	    return 0;
	}

	int count = 0;
	for (int k = strArray.length - 1; k >= 0; k--) {
	    String tempStr = strArray[k];
	    if (StringUtils.isNotEmpty(tempStr)) {
		WTPartMaster mast = PartBaseHelper.service.getMaster(tempStr.trim());
		if (mast != null) {
		    if(uniqueMap.containsKey(mast.getNumber())){
			continue;
		    }else{
			uniqueMap.put(mast.getNumber(), null);
			count = count + 1;
		    }
		}
	    }
	}

	return count;
    }

    public List<PartMatchingDTO> getMatchingPartList(String partOid) throws Exception {

	KETMessageService service = KETMessageService.getMessageService();
	Locale local = service.getLocale();

	List<PartMatchingDTO> returnList = new ArrayList<PartMatchingDTO>();

	WTPart wtPart = (WTPart) CommonUtil.getObject(partOid);

	if (wtPart == null) {
	    return returnList;
	}

	String spMTerminal = null;
	String spMatchBulb = null;
	String spMConnector = null;
	String spMClip = null;
	String spMRH = null;
	String spMCover = null;
	String spMWireSeal = null;
	
	// 분류체계
	KETPartClassification claz = PartClazHelper.service.getPartClassification(wtPart);

	if (claz == null) {
	    
	    spMTerminal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMTerminal);
	    spMatchBulb = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMatchBulb);
	    spMConnector = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMConnector);
	    spMClip = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMClip);
	    spMRH = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMRH);
	    spMCover = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMCover);
	    spMWireSeal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMWireSeal);
	    
	}else{

	    List<KETPartClassAttrLink> linkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class, KETPartClassification.class, claz);
	    for (KETPartClassAttrLink link : linkList) {
		KETPartAttribute attr = link.getAttr();
		if (PartSpecEnum.SpMTerminal.getAttrCode().equals(attr.getAttrCode())) {
		    spMTerminal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMTerminal);
		} else if (PartSpecEnum.SpMatchBulb.getAttrCode().equals(attr.getAttrCode())) {
		    spMatchBulb = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMatchBulb);
		} else if (PartSpecEnum.SpMConnector.getAttrCode().equals(attr.getAttrCode())) {
		    spMConnector = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMConnector);
		} else if (PartSpecEnum.SpMClip.getAttrCode().equals(attr.getAttrCode())) {
		    spMClip = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMClip);
		} else if (PartSpecEnum.SpMRH.getAttrCode().equals(attr.getAttrCode())) {
		    spMRH = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMRH);
		} else if (PartSpecEnum.SpMCover.getAttrCode().equals(attr.getAttrCode())) {
		    spMCover = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMCover);
		} else if (PartSpecEnum.SpMWireSeal.getAttrCode().equals(attr.getAttrCode())) {
		    spMWireSeal = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMWireSeal);
		}
	    }
	}

	List<PartMatchingDTO> listTermial = getMatchingPartListOne(spMTerminal, claz, PartSpecEnum.SpMTerminal, local);
	returnList.addAll(listTermial);
	List<PartMatchingDTO> listMatchBulb = getMatchingPartListOne(spMatchBulb, claz, PartSpecEnum.SpMatchBulb, local);
	returnList.addAll(listMatchBulb);
	List<PartMatchingDTO> listConnector = getMatchingPartListOne(spMConnector, claz, PartSpecEnum.SpMConnector, local);
	returnList.addAll(listConnector);
	List<PartMatchingDTO> listClip = getMatchingPartListOne(spMClip, claz, PartSpecEnum.SpMClip, local);
	returnList.addAll(listClip);
	List<PartMatchingDTO> listMRH = getMatchingPartListOne(spMRH, claz, PartSpecEnum.SpMRH, local);
	returnList.addAll(listMRH);
	List<PartMatchingDTO> listMCover = getMatchingPartListOne(spMCover, claz, PartSpecEnum.SpMCover, local);
	returnList.addAll(listMCover);
	List<PartMatchingDTO> listWireSeal = getMatchingPartListOne(spMWireSeal, claz, PartSpecEnum.SpMWireSeal, local);
	returnList.addAll(listWireSeal);

	return returnList;
    }

    private List<PartMatchingDTO> getMatchingPartListOne(String matchingPartNos, KETPartClassification claz, PartSpecEnum partSpecEnum, Locale local) throws Exception {

	List<PartMatchingDTO> returnList = new ArrayList<PartMatchingDTO>();

	if (StringUtils.isEmpty(matchingPartNos)) {
	    return returnList;
	}

	String[] strArray = null;
	if (matchingPartNos.indexOf(",") == -1) {
	    strArray = new String[] { matchingPartNos };
	} else {
	    strArray = matchingPartNos.split(",");
	}

	if (strArray == null || strArray.length == 0) {
	    return returnList;
	}

	for (int k = 0;  k < strArray.length; k++) {
	    
	    String tempStr = strArray[k];
	    
	    if (StringUtils.isNotEmpty(tempStr)) {
		
		WTPartMaster mast = PartBaseHelper.service.getMaster(tempStr.trim());
		if (mast != null) {

		    if(uniqueMap.containsKey(mast.getNumber())){
			
			continue;
			
		    }else{
			
			uniqueMap.put(mast.getNumber(), null);
			
			WTPart latestPart = PartBaseHelper.service.getLatestPart(mast);
			 
			PartMatchingDTO dto = new PartMatchingDTO();
			
			dto.setMatchPartClazOid(CommonUtil.getOIDString(latestPart));
			dto.setMatchPartType(PartUtil.getPartType(latestPart));
			dto.setMatchPartClazName(claz==null?"":claz.getClassKrName());
			dto.setMatchPartNumber(latestPart.getNumber());
			dto.setMatchPartName(latestPart.getName());
			String spPartRevision = PartSpecGetter.getPartSpec(latestPart, PartSpecEnum.SpPartRevision);
			dto.setMatchPartRev(StringUtil.checkNull(spPartRevision));
			dto.setMatchPartState(latestPart.getLifeCycleState().getDisplay(local));
			dto.setMatchPartAttrName(partSpecEnum.getAttrName());

			returnList.add(dto);
		    }
		}
	    }
	}
	
	return returnList;
    }
    
}
