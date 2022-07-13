package ext.ket.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.util.CommonUtil;
import ext.ket.common.util.ObjectUtil;
import wt.fc.Persistable;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 8. 9. 오전 10:23:54
 * @Pakage ext.ket.common.controller
 * @class CommonController
 *********************************************************/
@Controller
@RequestMapping("/common/*")
public class CommonController {
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 9. 오전 10:26:40
     * @method getObjectData
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/getObjectData")
    public Map<String, Object> getObjectData(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            String oid = (String) reqMap.get("oid");
            
            Persistable obj = CommonUtil.getObject(oid);
            
            if(obj != null) {
                resMap = ObjectUtil.manager.converObjectToMap(obj);
            }
            
            resMap.put("result", true);

        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
}
