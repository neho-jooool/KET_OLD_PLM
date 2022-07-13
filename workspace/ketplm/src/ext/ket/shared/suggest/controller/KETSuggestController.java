package ext.ket.shared.suggest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ext.ket.shared.suggest.entity.KETSuggestDTO;
import ext.ket.shared.suggest.service.KETSuggestHelper;

/**
 * <p>
 * Suggest controller
 * </p>
 * ※ 이미 OOTB에서 suggestController bean 이름을 사용하여 부득이 KET 접두사를 붙입니다.
 * 
 * @클래스명 : KETSuggestController
 * @작성자 : sw775.park
 * @작성일 : 2014. 7. 2.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/suggest/*")
public class KETSuggestController {

    @RequestMapping("/find")
    @ResponseBody
    public List<Map<String, String>> find(KETSuggestDTO ketSuggestDTO) throws Exception {
	return KETSuggestHelper.service.find(ketSuggestDTO);
    }
}
