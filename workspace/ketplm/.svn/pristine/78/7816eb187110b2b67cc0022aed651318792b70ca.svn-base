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

import e3ps.project.TemplateProject;
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
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newElectronTemplateProject</code> static factory method(s),
 * not the <code>ElectronTemplateProject</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=TemplateProject.class,
   properties={
   @GeneratedProperty(name="productType", type=int.class,
      javaDoc="백색:0 협피치:1")
   })
public class ElectronTemplateProject extends _ElectronTemplateProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ElectronTemplateProject
    * @exception wt.util.WTException
    **/
   public static ElectronTemplateProject newElectronTemplateProject()
            throws WTException {

      ElectronTemplateProject instance = new ElectronTemplateProject();
      instance.initialize();
      return instance;
   }

}
