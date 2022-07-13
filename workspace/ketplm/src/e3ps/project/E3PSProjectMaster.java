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

package e3ps.project;

import e3ps.project.ProjectMaster;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newE3PSProjectMaster</code> static factory method(s), not
 * the <code>E3PSProjectMaster</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=ProjectMaster.class)
public class E3PSProjectMaster extends _E3PSProjectMaster {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    E3PSProjectMaster
    * @exception wt.util.WTException
    **/
   public static E3PSProjectMaster newE3PSProjectMaster()
            throws WTException {

      E3PSProjectMaster instance = new E3PSProjectMaster();
      instance.initialize();
      return instance;
   }

}
