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

package e3ps.edm;

import e3ps.project.ProjectMaster;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.epm.EPMDocument;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.ObjectToVersionLink;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newEPMDocProjectLink</code> static factory method(s), not
 * the <code>EPMDocProjectLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToVersionLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="initPjtVersion", type=String.class)
   },
   roleA=@GeneratedRole(name="pjtMaster", type=ProjectMaster.class),
   roleB=@GeneratedRole(name="pjtDoc", type=EPMDocument.class),
   tableProperties=@TableProperties(tableName="EPMDocProjectLink")
)
public class EPMDocProjectLink extends _EPMDocProjectLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     pjtMaster
    * @param     pjtDoc
    * @return    EPMDocProjectLink
    * @exception wt.util.WTException
    **/
   public static EPMDocProjectLink newEPMDocProjectLink( ProjectMaster pjtMaster, EPMDocument pjtDoc )
            throws WTException {

      EPMDocProjectLink instance = new EPMDocProjectLink();
      instance.initialize( pjtMaster, pjtDoc );
      return instance;
   }

}
