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

package e3ps.ecm.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.change2.WTChangeRequest2;
import wt.lifecycle.State;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETMoldChangeRequest</code> static factory method(s),
 * not the <code>KETMoldChangeRequest</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTChangeRequest2.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="ecrId", type=String.class),
   @GeneratedProperty(name="ecrName", type=String.class),
   @GeneratedProperty(name="devYn", type=String.class),
   @GeneratedProperty(name="divisionFlag", type=String.class),
   @GeneratedProperty(name="projectOid", type=String.class),
   @GeneratedProperty(name="requestType", type=String.class),
   @GeneratedProperty(name="otherReqType", type=String.class),
   @GeneratedProperty(name="changeType", type=String.class),
   @GeneratedProperty(name="otherChangeDesc", type=String.class),
   @GeneratedProperty(name="completeReqDate", type=String.class),
   @GeneratedProperty(name="vendorFlag", type=String.class),
   @GeneratedProperty(name="prodVendor", type=String.class),
   @GeneratedProperty(name="processType", type=String.class),
   @GeneratedProperty(name="otherProcessDesc", type=String.class),
   @GeneratedProperty(name="ecrDesc", type=String.class),
   @GeneratedProperty(name="expectEffect", type=String.class),
   @GeneratedProperty(name="deptName", type=String.class),
   @GeneratedProperty(name="attribute1", type=String.class),
   @GeneratedProperty(name="attribute2", type=String.class),
   @GeneratedProperty(name="attribute3", type=String.class),
   @GeneratedProperty(name="attribute4", type=String.class),
   @GeneratedProperty(name="attribute5", type=String.class),
   @GeneratedProperty(name="attribute6", type=String.class),
   @GeneratedProperty(name="attribute7", type=String.class),
   @GeneratedProperty(name="attribute8", type=String.class),
   @GeneratedProperty(name="attribute9", type=String.class),
   @GeneratedProperty(name="attribute10", type=String.class),
   @GeneratedProperty(name="ecrStateState", type=State.class)
   })
public class KETMoldChangeRequest extends _KETMoldChangeRequest {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETMoldChangeRequest
    * @exception wt.util.WTException
    **/
   public static KETMoldChangeRequest newKETMoldChangeRequest()
            throws WTException {

      KETMoldChangeRequest instance = new KETMoldChangeRequest();
      instance.initialize();
      return instance;
   }

}
