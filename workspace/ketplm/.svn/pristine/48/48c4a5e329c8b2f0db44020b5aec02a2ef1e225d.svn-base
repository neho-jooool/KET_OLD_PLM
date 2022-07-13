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
import java.sql.Timestamp;
import wt.enterprise.Managed;
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
 * Engineering Change Notice
 * <p>
 * Use the <code>newKETChangeNotice</code> static factory method(s), not
 * the <code>KETChangeNotice</code> constructor, to construct instances
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
   @GeneratedProperty(name="ecnNumber", type=String.class,
      javaDoc="ECN Number : 예) ECN-2014-001"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,
      javaDoc="완료일")
   })
public class KETChangeNotice extends _KETChangeNotice {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETChangeNotice
    * @exception wt.util.WTException
    **/
   public static KETChangeNotice newKETChangeNotice()
            throws WTException {

      KETChangeNotice instance = new KETChangeNotice();
      instance.initialize();
      return instance;
   }

}
