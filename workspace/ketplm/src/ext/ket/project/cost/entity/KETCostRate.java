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

package ext.ket.project.cost.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import wt.content.ContentItem;
import wt.content.DataFormatReference;
import wt.content.FormatContentHolder;
import wt.content.HttpContentOperation;
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
 *
 * <p>
 * Use the <code>newKETCostRate</code> static factory method(s), not the
 * <code>KETCostRate</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={WTContained.class, FormatContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="reviewCompleteDate", type=Timestamp.class,
      javaDoc="검토완료일"),
   @GeneratedProperty(name="reviewStep", type=String.class,
      javaDoc="검토단계"),
   @GeneratedProperty(name="partName", type=String.class,
      javaDoc="Part Name"),
   @GeneratedProperty(name="salesTargetCost", type=String.class,
      javaDoc="판매목표가(원)"),
   @GeneratedProperty(name="classification", type=String.class,
      javaDoc="구분"),
   @GeneratedProperty(name="totalCost", type=String.class,
      javaDoc="총원가(원)"),
   @GeneratedProperty(name="profitRate", type=String.class,
      javaDoc="수익율(%)"),
   @GeneratedProperty(name="mainIssue", type=String.class,
      javaDoc="주요 Issue 사항"),
   @GeneratedProperty(name="revision", type=String.class,
      javaDoc="차수")
   })
public class KETCostRate extends _KETCostRate {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETCostRate
    * @exception wt.util.WTException
    **/
   public static KETCostRate newKETCostRate()
            throws WTException {

      KETCostRate instance = new KETCostRate();
      instance.initialize();
      return instance;
   }

}
