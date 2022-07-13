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
 * Use the <code>newMoldTemplateProject</code> static factory method(s),
 * not the <code>MoldTemplateProject</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=TemplateProject.class,
   properties={
   @GeneratedProperty(name="makeType", type=int.class,
      javaDoc="제작 타입")
   })
public class MoldTemplateProject extends _MoldTemplateProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    MoldTemplateProject
    * @exception wt.util.WTException
    **/
   public static MoldTemplateProject newMoldTemplateProject()
            throws WTException {

      MoldTemplateProject instance = new MoldTemplateProject();
      instance.initialize();
      return instance;
   }

}
