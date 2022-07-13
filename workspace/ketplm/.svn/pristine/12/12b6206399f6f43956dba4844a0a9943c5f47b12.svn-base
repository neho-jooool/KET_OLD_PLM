/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.sales.entity;

import ext.ket.sales.entity.KETSalesTask;
import ext.ket.sales.entity.KETSalesTaskHistory;
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
 * Use the <code>newKETSalesTaskHistoryLink</code> static factory method(s),
 * not the <code>KETSalesTaskHistoryLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="orgTask", type=KETSalesTask.class),
   roleB=@GeneratedRole(name="Taskhistory", type=KETSalesTaskHistory.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   tableProperties=@TableProperties(tableName="KETSalesTaskHistoryLink")
)
public class KETSalesTaskHistoryLink extends _KETSalesTaskHistoryLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     orgTask
    * @param     Taskhistory
    * @return    KETSalesTaskHistoryLink
    * @exception wt.util.WTException
    **/
   public static KETSalesTaskHistoryLink newKETSalesTaskHistoryLink( KETSalesTask orgTask, KETSalesTaskHistory Taskhistory )
            throws WTException {

      KETSalesTaskHistoryLink instance = new KETSalesTaskHistoryLink();
      instance.initialize( orgTask, Taskhistory );
      return instance;
   }

}
