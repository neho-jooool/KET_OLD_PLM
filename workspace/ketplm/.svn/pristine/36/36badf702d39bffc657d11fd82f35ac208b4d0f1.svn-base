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

package ext.ket.dqm.entity;

import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmHeader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETDqmTodoAtivity</code> static factory method(s), not
 * the <code>KETDqmTodoAtivity</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="taskName", type=String.class),
   @GeneratedProperty(name="taskCode", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="action", type=ext.ket.dqm.entity.KETDqmAction.class),
      myRole=@MyRole(name="todo", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="close", type=ext.ket.dqm.entity.KETDqmClose.class),
      myRole=@MyRole(name="todo", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="header", type=ext.ket.dqm.entity.KETDqmHeader.class),
      myRole=@MyRole(name="todo", cardinality=Cardinality.ZERO_TO_ONE))
   })
public class KETDqmTodoAtivity extends _KETDqmTodoAtivity {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETDqmTodoAtivity
    * @exception wt.util.WTException
    **/
   public static KETDqmTodoAtivity newKETDqmTodoAtivity()
            throws WTException {

      KETDqmTodoAtivity instance = new KETDqmTodoAtivity();
      instance.initialize();
      return instance;
   }

}
