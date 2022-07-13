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

package ext.ket.wfm.entity;

import e3ps.groupware.company.People;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.workflow.work.WorkItem;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETTodoDelegateHistory</code> static factory method(s),
 * not the <code>KETTodoDelegateHistory</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTObject.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="reason", type=String.class,
      javaDoc="사유",
      constraints=@PropertyConstraints(upperLimit=4000))
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETDelegateHistoryWorkItemLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="workitem", type=wt.workflow.work.WorkItem.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="delegateHostory", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(name="KETDelegateHistoryPBOLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="pbo", type=wt.fc.Persistable.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="delegateHistory", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(name="KETDelegateHistoryFromUserLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="fromUser", type=e3ps.groupware.company.People.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="delegateHistory", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETDelegateHistoryToUserLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="toUser", type=e3ps.groupware.company.People.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="delegateHistory", cardinality=Cardinality.ONE))
   })
public class KETTodoDelegateHistory extends _KETTodoDelegateHistory {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETTodoDelegateHistory
    * @exception wt.util.WTException
    **/
   public static KETTodoDelegateHistory newKETTodoDelegateHistory()
            throws WTException {

      KETTodoDelegateHistory instance = new KETTodoDelegateHistory();
      instance.initialize();
      return instance;
   }

}
