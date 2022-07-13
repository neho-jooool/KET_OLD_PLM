package ext.ket.shared.suggest.service;

import wt.services.ServiceFactory;

/**
 * @클래스명 : KETSuggestHelper
 * @작성자 : Administrator
 * @작성일 : 2014. 7. 1.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETSuggestHelper {

    public static final KETSuggestService service = ServiceFactory.getService(KETSuggestService.class);
}
