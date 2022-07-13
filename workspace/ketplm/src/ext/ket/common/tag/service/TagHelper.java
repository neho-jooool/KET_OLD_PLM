package ext.ket.common.tag.service;

import wt.services.ServiceFactory;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 6. 21. 오전 9:27:50
 * @Pakage ext.ket.common.tag.service
 * @class TagHelper
 *********************************************************/
public class TagHelper {
    public static final TagService service = ServiceFactory.getService(TagService.class);
}
