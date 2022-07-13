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

import e3ps.common.code.NumberCode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newProjectMasterCodeSubCodeLink</code> static factory method(s),
 * not the <code>ProjectMasterCodeSubCodeLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="projectId", type=String.class)
   },
   roleA=@GeneratedRole(name="subCode", type=NumberCode.class),
   roleB=@GeneratedRole(name="masterCode", type=NumberCode.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="ProjectMasterCodeSubCodeLink")
)
public class ProjectMasterCodeSubCodeLink extends _ProjectMasterCodeSubCodeLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     subCode
    * @param     masterCode
    * @return    ProjectMasterCodeSubCodeLink
    * @exception wt.util.WTException
    **/
   public static ProjectMasterCodeSubCodeLink newProjectMasterCodeSubCodeLink( NumberCode subCode, NumberCode masterCode )
            throws WTException {

      ProjectMasterCodeSubCodeLink instance = new ProjectMasterCodeSubCodeLink();
      instance.initialize( subCode, masterCode );
      return instance;
   }

}
