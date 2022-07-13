package ext.ket.common.tag.service;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeDao;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.CommonUtil;
import ext.ket.common.tag.entity.KETTagLink;
import ext.ket.common.tag.util.TagUtil;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.util.WTException;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 6. 21. 오전 9:28:19
 * @Pakage ext.ket.common.tag.service
 * @class StandardTagService
 *********************************************************/
public class StandardTagService extends StandardManager implements TagService, Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(StandardTagService.class);

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2018. 6. 21. 오전 9:28:28
	 * @method newStandardTagService
	 * @return StandardTagService
	 * @throws WTException 
	 * </pre>
	 */
	public static StandardTagService newStandardTagService() throws WTException {
		StandardTagService instance = new StandardTagService();
		instance.initialize();
		return instance;
	}

	/**
	 * <pre>
	 * @description 
	 * @author dhkim
	 * @date 2018. 6. 21. 오전 10:21:59
	 * @method saveTagMaster
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@Override
	public Map<String, Object> saveTagMaster(Map<String, Object> reqMap) throws Exception {
	    
	    Map<String, Object> resMap = new HashMap<String, Object>();
	    
	    Transaction trx = new Transaction();
	    Registry registry = null;
        DBConnectionManager resource = null;
        Connection con = null;
        
        try {
            
            registry = Registry.getRegistry("e3ps.bom.bom");
            resource = DBConnectionManager.getInstance();
            con = resource.getConnection(registry.getString("plm"));
            
            trx.start();
            
            String value = (String) reqMap.get("value");
            String code = TagUtil.manager.getNextTagMasterCode();
            
    	    NumberCode nCode = NumberCode.newNumberCode();
            nCode.setCodeType(NumberCodeType.toNumberCodeType("KETTAG"));
            nCode.setCode(code);
            nCode.setName(value);
    
            nCode = (NumberCode) PersistenceHelper.manager.save(nCode);
            
            resMap.put("id", CommonUtil.getOIDString(nCode));
            resMap.put("name", value);
            
            NumberCodeDao dao = new NumberCodeDao(con);

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("codeType", "KETTAG");
            param.put("code", code);
            param.put("value", value);
            param.put("desc", "");
            param.put("abbr", "");
            param.put("lang", "ko");
            dao.createNumberCodeValue(param);
            param.put("lang", "en");
            dao.createNumberCodeValue(param);
            param.put("lang", "zh_CN");
            dao.createNumberCodeValue(param);
            
            trx.commit();
            con.close();
            trx = null;
            con = null;
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (trx != null) {
                trx.rollback();
                trx = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        }
	    
	    return resMap;
	}

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 7. 20. 오전 9:32:39
     * @method saveTagProjectLink
     * @param reqMap
     * @return
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveTagProjectLink(Map<String, Object> reqMap) throws Exception {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        Transaction trx = new Transaction();
        
        try {
            
            trx.start();
            
            String mode = (String) reqMap.get("mode");
            List<String> tagList = (List<String>) reqMap.get("tagList");
            List<String> projectList = (List<String>) reqMap.get("projectList");
            
            for(String projectOid : projectList) {
                
                Persistable pbo = CommonUtil.getObject(projectOid);
                
                for(String tagOid : tagList) {
                    NumberCode tag = (NumberCode) CommonUtil.getObject(tagOid);
                    KETTagLink link = TagUtil.manager.getTagMasterLink(pbo, tag);
                    
                    if("BD".equals(mode) && link != null){
                        PersistenceHelper.manager.delete(link);
                    }
                    
                    if("BR".equals(mode) && link == null){
                        link = KETTagLink.newKETTagLink(pbo, tag);
                        PersistenceHelper.manager.save(link);
                    }
                }
            }
            
            trx.commit();
            trx = null;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (trx != null) {
                trx.rollback();
                trx = null;
            }
        }
        
        return resMap;
    }
}
