/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.dms.service;

import e3ps.dms.service.KETDmsService;
import java.lang.String;


/**
 *
 * <BR><BR><B>Supported API: </B>false
 * <BR><BR><B>Extendable: </B>false
 *
 * @version   1.0
 **/

public class KETDmsHelper {


   private static final String RESOURCE = "e3ps.dms.service.serviceResource";
   private static final String CLASSNAME = KETDmsHelper.class.getName();

   /**
    * <BR><BR><B>Supported API: </B>false
    **/
   public static final KETDmsService service = wt.services.ServiceFactory.getService(KETDmsService.class);



}
