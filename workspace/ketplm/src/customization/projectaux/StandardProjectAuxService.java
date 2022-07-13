/* bcwti????Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.????This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.????ecwti
 */

package customization.projectaux;

import customization.projectaux.ProjectAuxService;
import java.io.Serializable;
import java.lang.String;
import wt.services.StandardManager;
import wt.util.WTException;


/**
 *
 * <p>
 * Use the <code>newStandardProjectAuxService</code> static factory method(s),
 * not the <code>StandardProjectAuxService</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

public class StandardProjectAuxService extends StandardManager implements ProjectAuxService, Serializable {


   private static final String RESOURCE = "customization.projectaux.projectauxResource";
   private static final String CLASSNAME = StandardProjectAuxService.class.getName();



   /**
    * Returns the conceptual (modeled) name for the class.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated
    *
    * @return    String
    **/
   public String getConceptualClassname() {

      return CLASSNAME;
   }

   /**
    * Default factory for the class.
    *
    * @return    StandardProjectAuxService
    * @exception wt.util.WTException
    **/
   public static StandardProjectAuxService newStandardProjectAuxService()
            throws WTException {

      StandardProjectAuxService instance = new StandardProjectAuxService();
      instance.initialize();
      return instance;
   }

}
