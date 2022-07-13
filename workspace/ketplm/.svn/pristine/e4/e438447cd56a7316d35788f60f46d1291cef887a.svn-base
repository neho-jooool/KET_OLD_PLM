package ext.ket.cost.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.manage.BomEditorACL;
import ext.ket.cost.entity.manage.BomEditorACLFromType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTHashSet;
import wt.query.QuerySpec;
import wt.query.SearchCondition;

public class BomEditorACLUtil {
    
    private static final Logger LOGGER = Logger.getLogger(BomEditorACLUtil.class);
    public static BomEditorACLUtil manager = new BomEditorACLUtil();

    public void deleteAll(String taskCode) throws Exception {

        QueryResult rs = getAcl(taskCode);
        // WTArrayList wlist = new WTArrayList();
        WTHashSet wSet = new WTHashSet();

        while (rs.hasMoreElements()) {
            BomEditorACL acl = (BomEditorACL) rs.nextElement();
            wSet.add(acl);
        }

        if (wSet.size() > 0) {
            PersistenceHelper.manager.delete(wSet);
        }

    }

    @SuppressWarnings("deprecation")
    public QueryResult getAcl(String taskCode) throws Exception {
        QuerySpec spec = new QuerySpec(BomEditorACL.class);
        spec.appendWhere(
                new SearchCondition(BomEditorACL.class, BomEditorACL.TASK_CODE, SearchCondition.EQUAL, taskCode),
                new int[] { 0 });

        QueryResult rs = PersistenceHelper.manager.find(spec);

        return rs;
    }

    /**
     * dhkim
     * 
     * @param taskCode
     * @return
     * @throws Exception
     */

    public Map<String, Object> getAclDataMap(String taskCode) throws Exception {

        Map<String, Object> dataMap = new HashMap<String, Object>();

        Map<String, BomEditorACL> aclMap = getAclMap(taskCode);

        Set<String> st = aclMap.keySet();
        Iterator<String> it = st.iterator();

        while (it.hasNext()) {

            String key = it.next();
            BomEditorACL acl = aclMap.get(key);
            Map<String, Object> data = CostUtil.manager.converObjectToMap(acl);
            dataMap.put(key, data);
        }

        return dataMap;
    }

    public Map<String, BomEditorACL> getAclMap(String taskCode) throws Exception {
        Map<String, BomEditorACL> map = new HashMap<String, BomEditorACL>();
        QueryResult rs = getAcl(taskCode);

        while (rs.hasMoreElements()) {
            BomEditorACL acl = (BomEditorACL) rs.nextElement();
            String key = acl.getColumnKey();
            map.put(key, acl);
        }

        return map;
    }

    public Map<String, BomEditorACL> getAclMap(String taskCode, boolean isEditor) throws Exception {
        Map<String, BomEditorACL> map = new HashMap<String, BomEditorACL>();
        QueryResult rs = getAcl(taskCode);

        while (rs.hasMoreElements()) {
            BomEditorACL acl = (BomEditorACL) rs.nextElement();
            String key = acl.getColumnKey();
            if (isEditor) {
                if (acl.isEditor()) {
                    map.put(key, acl);
                }

            } else {
                map.put(key, acl);
            }
        }
        return map;
    }

    public List<String> getEditorColumnKeys(String taskCode) throws Exception {
        List<String> keys = new ArrayList<String>();
        QueryResult rs = getAcl(taskCode);
        while (rs.hasMoreElements()) {
            BomEditorACL acl = (BomEditorACL) rs.nextElement();
            if (acl.isEditor()) {
                keys.add(acl.getColumnKey());
            }
        }
        return keys;
    }

    public void deleteAllForType(String typeId) throws Exception {

        QueryResult rs = getAclForType(typeId);
        // WTArrayList wlist = new WTArrayList();
        WTHashSet wSet = new WTHashSet();

        while (rs.hasMoreElements()) {
            BomEditorACLFromType acl = (BomEditorACLFromType) rs.nextElement();
            wSet.add(acl);
        }

        if (wSet.size() > 0) {
            PersistenceHelper.manager.delete(wSet);
        }

    }
    
    @SuppressWarnings("deprecation")
    public BomEditorACLFromType getAclForType(String typeId, String columnKey) throws Exception {
        
        QuerySpec spec = new QuerySpec(BomEditorACLFromType.class);
        
        SearchUtil.appendEQUAL(spec, BomEditorACLFromType.class, BomEditorACLFromType.TYPE_ID, typeId, 0);
        SearchUtil.appendEQUAL(spec, BomEditorACLFromType.class, BomEditorACLFromType.COLUMN_KEY, columnKey, 0);
        
        QueryResult rs = PersistenceHelper.manager.find(spec);

        if(rs.hasMoreElements()) {
            return (BomEditorACLFromType) rs.nextElement();
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public QueryResult getAclForType(String typeId) throws Exception {
        QuerySpec spec = new QuerySpec(BomEditorACLFromType.class);

        if (typeId != null && typeId.length() > 0) {
            spec.appendWhere(new SearchCondition(BomEditorACLFromType.class, BomEditorACLFromType.TYPE_ID,
                    SearchCondition.EQUAL, typeId), new int[] { 0 });
        }
        QueryResult rs = PersistenceHelper.manager.find(spec);

        return rs;
    }

    public Map<String, BomEditorACLFromType> getAclMapForType(String typeId) throws Exception {
        Map<String, BomEditorACLFromType> map = new HashMap<String, BomEditorACLFromType>();
        QueryResult rs = getAclForType(typeId);

        while (rs.hasMoreElements()) {
            BomEditorACLFromType acl = (BomEditorACLFromType) rs.nextElement();
            String key = acl.getColumnKey();
            map.put(key, acl);
        }

        return map;
    }

    public Map<String, Map<String, BomEditorACLFromType>> getAclMapForTypeAll(boolean isNessary) throws Exception {
        QueryResult rs = getAclForType("");

        Map<String, Map<String, BomEditorACLFromType>> map = new HashMap<String, Map<String, BomEditorACLFromType>>();
        while (rs.hasMoreElements()) {
            BomEditorACLFromType acl = (BomEditorACLFromType) rs.nextElement();
            String key = acl.getTypeId();
            Map<String, BomEditorACLFromType> columnMap = map.get(key);
            if (columnMap == null) {
                columnMap = new HashMap<String, BomEditorACLFromType>();
            }
            String columnKey = acl.getColumnKey();

            if (isNessary) {
                if (acl.isNecessary()) {
                    columnMap.put(columnKey, acl);
                }
            } else if (!isNessary) {
                columnMap.put(columnKey, acl);
            }

            map.put(key, columnMap);
        }

        return map;

    }

    /**
     * dhkim
     * 
     * @param list
     * @param taskTypeCode
     * @return
     * @throws Exception
     */
    public NessaryCheckResult checkFromPartType(List<CostPart> list, String taskTypeCode) throws Exception {
        Map<String, Map<String, BomEditorACLFromType>> typeMap = getAclMapForTypeAll(true);
        List<String> editColumnKeys = getEditorColumnKeys(taskTypeCode); // a,b
        // c,d
        int row = 1;
        NessaryCheckResult result = new NessaryCheckResult();
        result.setOk(true);
        for (CostPart part : list) {
            String partTypeOid = part.getPartType();
            String mfFactory1 = part.getMftFactory1();
            String mfFactory2 = part.getMftFactory2();

            String typeId = partTypeOid + "*" + CommonUtil.getOIDLongValue(mfFactory1) + "*" + CommonUtil.getOIDLongValue(mfFactory2);
            Map<String, BomEditorACLFromType> nsMap = typeMap.get(typeId);

            for (String columnKey : editColumnKeys) {
                boolean isOk = true;
                if (nsMap.containsKey(columnKey)) {
                    try {
                        Object obj = callGetter(part, columnKey);
                        if (obj == null || obj.toString().trim().length() == 0) {
                            isOk = false;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (!isOk) {
                    result.setOk(isOk);
                    result.setRow(row);
                    result.setPart(part);
                    result.setAclColumnInfo(nsMap.get(columnKey));
                    return result;
                }
            }

            row++;
        }

        return result;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 27. 오전 9:59:34
     * @method checkFromPartTypeResult
     * @param list
     * @param taskTypeCode
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> checkFromPartTypeResult(List<CostPart> list, String taskTypeCode) throws Exception {

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("checkResult", true);

        Map<String, Map<String, BomEditorACLFromType>> typeMap = getAclMapForTypeAll(true);
        List<String> editColumnKeys = getEditorColumnKeys(taskTypeCode); // a,b
        
        for (CostPart part : list) {
            String partNo = part.getPartNo();
            String partTypeOid = part.getPartType();
            String mfFactory1 = part.getMftFactory1();
            String mfFactory2 = part.getMftFactory2();
//            String moldMftDivision = part.getMoldMftDivision(); //금형 투자비 제작구분
//            String facMftDivision = part.getFacMftDivision(); //설비 투자비 제작구분
            

            if (!StringUtil.checkString(partTypeOid)) {
                String message = "부품 " + partNo + "의 제품정보 - 구분값을 입력하십시오";
                resMap.put("checkResult", false);
                resMap.put("message", message);

                return resMap;
                
            }else {
                
                //해당 태스크에서 표시되는 부품이 아닌 경우 스킵
                CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partTypeOid);
                
                String taskCode = cpType.getTaskCode();
                if(!StringUtil.checkString(taskCode) && taskCode.indexOf(taskTypeCode) < 0) {
                    continue;
                }
            }
            if (!StringUtil.checkString(partTypeOid)) {
                String message = "부품 " + partNo + "의 생산정보 - 생산국(입고처)값을 입력하십시오.";
                resMap.put("checkResult", false);
                resMap.put("message", message);
                return resMap;
            }
            if (!StringUtil.checkString(partTypeOid)) {
                String message = "부품 " + partNo + "의 생산정보 - 생산지(유/무상)값을 입력하십시오.";
                resMap.put("checkResult", false);
                resMap.put("message", message);
                return resMap;
            }
            
//            if("MDV001".equals(moldMftDivision) || "MDV002".equals(moldMftDivision) || "MDV003".equals(moldMftDivision)){
//        	String message = "";
//        	boolean isOk = true;
//        	
//		if ("MDV001".equals(moldMftDivision) || "MDV002".equals(moldMftDivision)) {
//		    if (StringUtils.isEmpty(part.getMoldNFactory())) {
//			isOk = false;
//			message = "투자비-금형-신규 제작처를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getMoldNIC(), "0"))) {
//			isOk = false;
//			message = "투자비-금형-신규 투자비를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getMoldNICMUnit())) {
//			isOk = false;
//			message = "투자비-금형-신규 투자비 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getMoldNPay(), "0"))) {
//			isOk = false;
//			message = "투자비-금형-신규 지급액을 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getMoldNPayMUnit())) {
//			isOk = false;
//			message = "투자비-금형-신규 지급액 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//		}
//		
//		if ("MDV003".equals(moldMftDivision) || "MDV002".equals(moldMftDivision)) {
//		    if (StringUtils.isEmpty(part.getMoldMPFactory())) {
//			isOk = false;
//			message = "투자비-금형-양산 제작처를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getMoldMPIC(), "0"))) {
//			isOk = false;
//			message = "투자비-금형-양산 투자비를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getMoldMPICMUnit())) {
//			isOk = false;
//			message = "투자비-금형-신규 양산 투자비 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getMoldMPQTotal(), "0"))) {
//			isOk = false;
//			message = "투자비-금형-양산수량Total 을 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getMoldMPQMax(), "0"))) {
//			isOk = false;
//			message = "투자비-금형-양산수량Max 를 입력하십시오.(" + partNo + ")";
//		    }
//		}
//        	
//		
//        	if(!isOk){
//        	    resMap.put("checkResult", false);
//                    resMap.put("message", message);
//                    return resMap;    
//        	}
//            }
            
            
//            if("FDV001".equals(facMftDivision) || "FDV002".equals(facMftDivision) || "FDV003".equals(facMftDivision)){
//        	String message = "";
//        	boolean isOk = true;
//        	
//		if ("FDV001".equals(moldMftDivision) || "FDV002".equals(moldMftDivision)) {
//		    if (StringUtils.isEmpty(part.getFacNFactory())) {
//			isOk = false;
//			message = "투자비-설비-신규 제작처를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getFacNIC(), "0"))) {
//			isOk = false;
//			message = "투자비-설비-신규 투자비를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getFacNICMUnit())) {
//			isOk = false;
//			message = "투자비-설비-신규 투자비 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getFacNPay(), "0"))) {
//			isOk = false;
//			message = "투자비-설비-신규 지급액을 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getFacNPayMUnit())) {
//			isOk = false;
//			message = "투자비-설비-신규 지급액 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//		}
//		
//		if ("FDV003".equals(moldMftDivision) || "FDV002".equals(moldMftDivision)) {
//		    if (StringUtils.isEmpty(part.getFacMPFactory())) {
//			isOk = false;
//			message = "투자비-설비-양산 제작처를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getFacMPIC(), "0"))) {
//			isOk = false;
//			message = "투자비-설비-양산 투자비를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if (StringUtils.isEmpty(part.getFacMPICMUnit())) {
//			isOk = false;
//			message = "투자비-설비-신규 양산 투자비 화폐단위를 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getFacMPQTotal(), "0"))) {
//			isOk = false;
//			message = "투자비-설비-양산수량Total 을 입력하십시오.(" + partNo + ")";
//		    }
//
//		    if ("0".equals(StringUtil.checkReplaceStr(part.getFacMPQMax(), "0"))) {
//			isOk = false;
//			message = "투자비-설비-양산수량Max 를 입력하십시오.(" + partNo + ")";
//		    }
//		}
//        	
//		
//        	if(!isOk){
//        	    resMap.put("checkResult", false);
//                    resMap.put("message", message);
//                    return resMap;    
//        	}
//            }

            String moldMftDivision = part.getMoldMftDivision();
            String facMftDivision = part.getFacMftDivision();
            
            boolean isMoldMass = "MDV003".equals(moldMftDivision);
            
            boolean isFacMass = "FDV003".equals(facMftDivision);
            
            boolean isMoldNotApply = "MDV006".equals(moldMftDivision); //해당없음
            boolean isFacNotApply = "FDV006".equals(facMftDivision); // 해당없음
            
            
            String typeId = CostPartType.class.getName() + ":" + partTypeOid + "*" + CommonUtil.getOIDLongValue(mfFactory1) + "*" + CommonUtil.getOIDLongValue(mfFactory2);
            
            Map<String, BomEditorACLFromType> nsMap = typeMap.get(typeId);
            
            if (nsMap != null) {
                for (String columnKey : editColumnKeys) {
                    boolean isOk = true;
                    
                    
                    if (nsMap.containsKey(columnKey)) {
                    	if("partTypeName".equals(columnKey)){ //partType
                    	    continue;
                    	}else if("estimated".equals(columnKey)){ //예상판매량
                    	    columnKey = "quantityTotal";
                    	}else if("quantity".equals(columnKey)){// 포장비
                    	    columnKey = "packUnitPriceSum";
                    	}
                	
                        Object obj = callGetter(part, columnKey);
                        if (obj == null || obj.toString().trim().length() == 0) {
                            isOk = false;
                        }
                    }
                    
                    if("prePlatingCost".equals(columnKey) || "prePlatingUnit".equals(columnKey)) {
                        //선도금 단가 Au도금,Ag도금,기타도금일 경우
                        if (("PTG003".equals(part.getPrePlatingSpec()) || "PTG004".equals(part.getPrePlatingSpec())
                                || "PTG005".equals(part.getPrePlatingSpec()))) {
                            Object obj = callGetter(part, columnKey);
                            if (obj == null || obj.toString().trim().length() == 0) {
                                isOk = false;
                            }
                        }
                    }
                    
                    if("apUnitCost".equals(columnKey) || "apMonetaryUnit".equals(columnKey)) {
                        //후도금사양 값이 있는 경우
                        if (StringUtil.checkString(part.getApPlatingSpec()) && !"PTG010".equals(part.getApPlatingSpec())){
                            Object obj = callGetter(part, columnKey);
                            if (obj == null || obj.toString().trim().length() == 0) {
                                isOk = false;
                            }
                        }
                    }
                    
                    if(isMoldMass || isMoldNotApply){//금형제작구분이 양산일때 or 해당없음일때 신규 투자비 관련 항목은 체크안함 
                	if("moldNFactory".equals(columnKey) || "moldNIC".equals(columnKey) || "moldNICMUnit".equals(columnKey) || "moldNPay".equals(columnKey) || "moldNPayMUnit".equals(columnKey)) {
                	    continue;
                	}
                    }
                    
                    if(!isMoldMass || isMoldNotApply){//금형제작구분이 양산이 아닐 때 or 해당없음일때 양산 투자비 관련 항목은 체크안함
                	if("moldMPFactory".equals(columnKey) || "moldMPIC".equals(columnKey) || "moldMPICMUnit".equals(columnKey) || "moldMPQTotal".equals(columnKey) || "moldMPQMax".equals(columnKey)) {
                	    continue;
                	}
                    }
                    
                    
                    if(isFacMass || isFacNotApply){//설비제작구분이 양산일때 or 해당없음일때 설비 투자비 관련 항목은 체크안함
                	if("facNFactory".equals(columnKey) || "facNIC".equals(columnKey) || "facNICMUnit".equals(columnKey) || "facNPay".equals(columnKey) || "facNPayMUnit".equals(columnKey)) {
                	    continue;
                	}
                    }
                    
                    if(!isFacMass || isFacNotApply){//설비제작구분이 양산이 아닐 때 or 해당없음일때 양산 투자비 관련 항목은 체크안함
                	if("facMPFactory".equals(columnKey) || "facMPIC".equals(columnKey) || "facMPICMUnit".equals(columnKey) || "facMPQTotal".equals(columnKey) || "facMPQMax".equals(columnKey)) {
                	    continue;
                	}
                    }
                    
                    if (!isOk) {
                        
                        BomEditorACLFromType columnType = getAclForType(typeId, columnKey);
                        
                        String columnDisplay = "[exception not found column Name.]";
                        if(columnType != null) columnDisplay = columnType.getDisplayName();
                        
                        String message = "부품 " + partNo + "의 \n" + columnDisplay + "값을 입력하십시오.";
                        resMap.put("checkResult", false);
                        resMap.put("message", message);
                        
                        return resMap;
                    }
                }
            }
        }
        
        return resMap;
    }

    /**
     * 
     * @param obj
     * @param fieldName
     * @param value
     */
    private Object callGetter(Object obj, String fieldName) throws Exception {

        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        return pd.getReadMethod().invoke(obj);

    }
}
