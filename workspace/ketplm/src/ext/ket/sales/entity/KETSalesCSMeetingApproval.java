/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.sales.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.enterprise.Managed;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * 결재이력
 * <p>
 * Use the <code>newKETSalesCSMeetingApproval</code> static factory method(s),
 * not the <code>KETSalesCSMeetingApproval</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="meetingManageLinkInfo", type=String.class),
   @GeneratedProperty(name="deptSort", type=String.class,
      javaDoc="부서정보")
   })
public class KETSalesCSMeetingApproval extends _KETSalesCSMeetingApproval {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETSalesCSMeetingApproval
    * @exception wt.util.WTException
    **/
   public static KETSalesCSMeetingApproval newKETSalesCSMeetingApproval()
            throws WTException {

      KETSalesCSMeetingApproval instance = new KETSalesCSMeetingApproval();
      instance.initialize();
      return instance;
   }

}
