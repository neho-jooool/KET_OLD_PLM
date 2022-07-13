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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
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
 * 성과관리≪≫Performance≪≫목표≪≫planQuality
 * <p>
 * Use the <code>newPerformance</code> static factory method(s), not the
 * <code>Performance</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="planQuality", type=String.class),
   @GeneratedProperty(name="planCost", type=String.class),
   @GeneratedProperty(name="planDelivery1", type=String.class),
   @GeneratedProperty(name="planDelivery2", type=String.class),
   @GeneratedProperty(name="planDelivery3", type=String.class),
   @GeneratedProperty(name="planTechnical", type=String.class),
   @GeneratedProperty(name="resultQuality", type=String.class),
   @GeneratedProperty(name="resultCost", type=String.class),
   @GeneratedProperty(name="resultDelivery1", type=String.class),
   @GeneratedProperty(name="resultDelivery2", type=String.class),
   @GeneratedProperty(name="resultDelivery3", type=String.class),
   @GeneratedProperty(name="resultTechnical", type=String.class),
   @GeneratedProperty(name="scoreQuality", type=String.class),
   @GeneratedProperty(name="scoreCost", type=String.class),
   @GeneratedProperty(name="scoreDelivery1", type=String.class),
   @GeneratedProperty(name="scoreDelivery2", type=String.class),
   @GeneratedProperty(name="scoreDelivery3", type=String.class),
   @GeneratedProperty(name="scoreTechnical", type=String.class),
   @GeneratedProperty(name="evaluateQuality", type=String.class),
   @GeneratedProperty(name="evaluateCost", type=String.class),
   @GeneratedProperty(name="evaluateDelivery1", type=String.class),
   @GeneratedProperty(name="evaluateDelivery2", type=String.class),
   @GeneratedProperty(name="evaluateDelivery3", type=String.class),
   @GeneratedProperty(name="evaluateTechnical", type=String.class),
   @GeneratedProperty(name="descMsg", type=String.class,
      constraints=@PropertyConstraints(upperLimit=6000)),
   @GeneratedProperty(name="keyNo", type=String.class),
   @GeneratedProperty(name="rankOne", type=String.class),
   @GeneratedProperty(name="rankTwo", type=String.class)
   })
public class Performance extends _Performance {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    Performance
    * @exception wt.util.WTException
    **/
   public static Performance newPerformance()
            throws WTException {

      Performance instance = new Performance();
      instance.initialize();
      return instance;
   }

}
