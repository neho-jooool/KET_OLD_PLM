/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.sales.entity;

import e3ps.project.E3PSProjectMaster;
import ext.ket.sales.entity.KETSalesProject;
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
 * Use the <code>newKETSalesProjectLink</code> static factory method(s),
 * not the <code>KETSalesProjectLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="salesProject", type=KETSalesProject.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="pjtMaster", type=E3PSProjectMaster.class),
   tableProperties=@TableProperties(tableName="KETSalesProjectLink")
)
public class KETSalesProjectLink extends _KETSalesProjectLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     salesProject
    * @param     pjtMaster
    * @return    KETSalesProjectLink
    * @exception wt.util.WTException
    **/
   public static KETSalesProjectLink newKETSalesProjectLink( KETSalesProject salesProject, E3PSProjectMaster pjtMaster )
            throws WTException {

      KETSalesProjectLink instance = new KETSalesProjectLink();
      instance.initialize( salesProject, pjtMaster );
      return instance;
   }

}
