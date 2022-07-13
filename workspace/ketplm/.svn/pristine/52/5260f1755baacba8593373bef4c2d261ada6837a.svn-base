/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.sales.entity;

import ext.ket.sales.entity.KETSalesTask;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.Persistable;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETSalesTaskHistory</code> static factory method(s),
 * not the <code>KETSalesTaskHistory</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=KETSalesTask.class,
   properties={
   @GeneratedProperty(name="taskVersion", type=String.class)
   })
public class KETSalesTaskHistory extends _KETSalesTaskHistory {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETSalesTaskHistory
    * @exception wt.util.WTException
    **/
   public static KETSalesTaskHistory newKETSalesTaskHistory()
            throws WTException {

      KETSalesTaskHistory instance = new KETSalesTaskHistory();
      instance.initialize();
      return instance;
   }

}
