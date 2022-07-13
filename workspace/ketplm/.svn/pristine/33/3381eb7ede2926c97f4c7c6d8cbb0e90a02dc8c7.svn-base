package ext.ket.common.tag.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.common.tag.entity.KETTagLink;
import ext.ket.common.util.ObjectUtil;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sales.entity.KETSalesProjectDTO;
import ext.ket.sales.service.SalesHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;

public class TagUtil {
    
//    private static final Logger LOGGER = Logger.getLogger(TagUtil.class);

    public static final TagUtil manager = new TagUtil();
    
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 20. 오전 9:35:13
     * @method searchTagLinkProject
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception 
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> searchTagLinkProject(Map<String, Object> reqMap) throws Exception{
        
        KETMessageService messageService = KETMessageService.getMessageService();
        Map<String, Object> resMap = new HashMap<String, Object>();
        List<Map<String, Object>> pboList = new ArrayList<Map<String, Object>>();
        
        List<String> tagList = (List<String>) reqMap.get("tagList");
        
        List<NumberCode> tagCodeList = new ArrayList<NumberCode>();
        for(String id : tagList) {
            if(StringUtil.checkString(id)) tagCodeList.add((NumberCode) CommonUtil.getObject(id));
        }
        
        List<String> duplicateCheck = new ArrayList<String>();
        List<KETTagLink> links = getTagMasterLinkList(tagCodeList);
        F1 : for(KETTagLink link : links) {
            
            Persistable pbo = link.getPbo();
            
            if(pbo instanceof KETSalesProject) {
                
                
                String oid = CommonUtil.getOIDString(pbo);
                
                if(!duplicateCheck.contains(oid)) {
                    
                    KETSalesProjectDTO salesDTO = new KETSalesProjectDTO();
                    salesDTO.setOid(oid);
                    salesDTO = SalesHelper.service.viewSalesProjectForm(salesDTO);
                    
                    if("ok".equals(salesDTO.getBasicEditAuth()) || CommonUtil.isMember("영업CS관리")) {
                        
                        salesDTO = salesDTO.KETSalesDTOGrid((KETSalesProject) pbo, salesDTO, messageService, null, false, null);
                        
                        List<Map<String, String>> refTagList = salesDTO.getRefTagList();
                        
                        for(String tagOid : tagList) {
                            
                            boolean exist = false;
                            for(Map<String, String> refTag : refTagList) {
                                if(tagOid.equals(refTag.get("id"))) exist = true;
                            }
                            
                            if(!exist) continue F1;
                        }
                        
                        Map<String, Object> obj = ObjectUtil.manager.converObjectToMap(salesDTO);
                        pboList.add(obj);
                        
                        duplicateCheck.add(oid);
                    }
                }
            }
        }
        
        resMap.put("pboList", pboList);
        
        return resMap;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 20. 오전 11:49:20
     * @method getTagMasterLink
     * @param pbo
     * @param tag
     * @return KETTagLink
     * @throws Exception 
     * </pre>
     */
    public KETTagLink getTagMasterLink(Persistable pbo, NumberCode tag) throws Exception {
        
        KETTagLink link = null;
        QuerySpec qs = new QuerySpec(NumberCode.class, KETTagLink.class);
        qs.appendWhere(new SearchCondition(KETTagLink.class, WTAttributeNameIfc.ROLEB_OBJECT_ID, "=", CommonUtil.getOIDLongValue(tag)), new int[]{1});

        QueryResult qr = PersistenceHelper.manager.navigate(pbo, KETTagLink.TAG_ROLE , qs, false);
        
        if(qr.hasMoreElements()) link = (KETTagLink) qr.nextElement();
        
        return link;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 20. 오후 3:00:52
     * @method getTagMasterLinkList
     * @param obj
     * @return List<KETTagLink>
     * @throws Exception 
     * </pre>
     */
    public List<KETTagLink> getTagMasterLinkList(Persistable obj) throws Exception {
        
        List<KETTagLink> tmlList = new ArrayList<KETTagLink>();
        
        QueryResult qr = PersistenceHelper.manager.navigate(obj, KETTagLink.TAG_ROLE, KETTagLink.class, false);

        while (qr.hasMoreElements()) {
            KETTagLink link = (KETTagLink) qr.nextElement();
            
            tmlList.add(link);
        }
        
        return tmlList;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 7. 19. 오후 4:20:00
     * @method getTagMasterLinkList
     * @param tagCodeList
     * @return List<KETTagLink>
     * @throws Exception 
     * </pre>
     */
    public List<KETTagLink> getTagMasterLinkList(List<NumberCode> tagCodeList) throws Exception {
        
        List<KETTagLink> tmlList = new ArrayList<KETTagLink>();
        
        for(NumberCode code  : tagCodeList) {
            QueryResult qr = PersistenceHelper.manager.navigate(code, KETTagLink.PBO_ROLE, KETTagLink.class, false);

            while (qr.hasMoreElements()) {
                KETTagLink link = (KETTagLink) qr.nextElement();
                tmlList.add(link);
            }
        }
        
        return tmlList;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 20. 오후 2:59:01
     * @method getTagMasterList
     * @param obj
     * @return List<Map<String,String>>
     * @throws Exception 
     * </pre>
     */
    public List<Map<String, String>> getTagMasterList(Persistable obj) throws Exception {
        
        List<Map<String, String>> tmList = new ArrayList<Map<String, String>>();
        
        QueryResult qr = PersistenceHelper.manager.navigate(obj, KETTagLink.TAG_ROLE, KETTagLink.class, true);

        while (qr.hasMoreElements()) {
            NumberCode tag = (NumberCode) qr.nextElement();
            Map<String, String> tmMap = new HashMap<String, String>();
            
            tmMap.put("id", CommonUtil.getOIDString(tag));
            tmMap.put("code", tag.getCode());
            tmMap.put("name", tag.getName());
            
            tmList.add(tmMap);
        }
        
        return tmList;
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 20. 오후 1:35:52
     * @method checkDuplicateTagMaster
     * @param value
     * @return boolean
     * @throws Exception 
     * </pre>
     */
    public boolean checkDuplicateTagMaster(String value) throws Exception {
        
        Statement stat = null;
        ResultSet rs = null;
        MethodContext mContext = MethodContext.getContext();
        WTConnection conn = null;

        try {
            conn = (WTConnection) mContext.getConnection();
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT * FROM NUMBERCODE A0 WHERE A0.CODETYPE='KETTAG' AND A0.NAME='" + value + "'");
            stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = stat.executeQuery(sql.toString());

            return rs.next();

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
                MethodContext.getContext().freeConnection();
            }
        }
    }
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 6. 21. 오전 10:09:28
     * @method getNextTagMasterCode
     * @return String
     * @throws Exception 
     * </pre>
     */
    public String getNextTagMasterCode() throws Exception {
        
        Statement stat = null;
        ResultSet rs = null;
        MethodContext mContext = MethodContext.getContext();
        WTConnection conn = null;

        try {
            conn = (WTConnection) mContext.getConnection();
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT KET_TAG_SEQ.NEXTVAL FROM DUAL");
            stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = stat.executeQuery(sql.toString());

            if(rs.next()) return ("TAG" + rs.getLong("NEXTVAL"));
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
                MethodContext.getContext().freeConnection();
            }
        }
        throw new Exception("################################### GENERATE SEQUENCE ERROR ###################################");
    }
}
