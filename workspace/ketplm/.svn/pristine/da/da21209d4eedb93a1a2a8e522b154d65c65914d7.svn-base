package ext.ket.common.tag.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.code.NumberCodeHelper;
import ext.ket.common.tag.service.TagHelper;
import ext.ket.common.tag.util.TagUtil;

@Controller
@RequestMapping("/common/tag/*")
public class TagController {
    
    private static final Logger LOGGER = Logger.getLogger(TagController.class);
    
    
    @ResponseBody
    @RequestMapping("/saveTagProjectLink")
    public Map<String, Object> saveTagProjectLink(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
            
            LOGGER.info("########################## saveTagProjectLink call ##############");
            
            resMap = TagHelper.service.saveTagProjectLink(reqMap);
            
            resMap.put("result", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    
    @ResponseBody
    @RequestMapping("/searchTagLinkProject")
    public Map<String, Object> searchTagLinkProject(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
            
            LOGGER.info("########################## searchTagLinkProject call ##############");
            
            resMap = TagUtil.manager.searchTagLinkProject(reqMap);
            
            resMap.put("result", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    /**
     * <pre>
     * @description 태그 마스터 저장
     * @author dhkim
     * @date 2018. 6. 20. 오후 1:36:17
     * @method saveTagMaster
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/saveTagMaster")
    public Map<String, Object> saveTagMaster(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
            LOGGER.info("########################## saveTagMaster call ##############");
            
            resMap = TagHelper.service.saveTagMaster(reqMap);
            
            resMap.put("result", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    /**
     * <pre>
     * @description 태그 마스터 중복 체크
     * @author dhkim
     * @date 2018. 6. 20. 오후 1:36:20
     * @method checkDuplicateTagMaster
     * @param reqMap
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping("/checkDuplicateTagMaster")
    public Map<String, Object> checkDuplicateTagMaster(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();
        
        String value = (String) reqMap.get("value");

        try {
            LOGGER.info("########################## checkDuplicateTagMaster call ##############");
            boolean isDuplicate = TagUtil.manager.checkDuplicateTagMaster(value);
            
            if(isDuplicate) throw new Exception("중복되는 태그가 있습니다.");
            
            resMap.put("result", true);
            
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    /**
     * <pre>
     * @description 태그 편집 화면
     *              MODE : MR-마스터 등록, BR-일괄 등록, BD-일괄 삭제, TL-태그 연결
     * @author dhkim
     * @date 2018. 6. 20. 오후 1:36:23
     * @method tagManagePopup
     * @param model
     * @param reqMap
     * @return Model
     * @throws Exception
     * </pre>
     */
    @RequestMapping("/tagManagePopup")
    public Model tagManagePopup(Model model, @RequestParam Map<String, String> reqMap) throws Exception {
        
        String mode = reqMap.get("mode");
        
        List<Map<String,String>> tagList = NumberCodeHelper.manager.getNumberCodeMapList("KETTAG");
        model.addAttribute("tagList", tagList);
        model.addAttribute("mode", mode);
        
        return model;
    }
    
}
